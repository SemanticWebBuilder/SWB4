package org.semanticwb.portal.resources.sem.favoriteWebPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

public class SWBFavoriteWebPagesResource extends org.semanticwb.portal.resources.sem.favoriteWebPages.base.SWBFavoriteWebPagesResourceBase
{
    public static final String Mode_DETAIL = "dtl";

    private static final Logger log = SWBUtils.getLogger(SWBFavoriteWebPagesResource.class);

    public SWBFavoriteWebPagesResource()
    {
    }

    /**
     * Constructs a SWBFavoriteWebPagesResource with a SemanticObject
     * @param base The SemanticObject with the properties for the SWBFavoriteWebPagesResource
     */
    public SWBFavoriteWebPagesResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        final String mode = paramRequest.getMode();
        if(SWBResourceURL.Action_EDIT.equals(mode)) {
            doEditWebPage(request, response, paramRequest);
        }else if(SWBResourceURL.Action_ADD.equals(mode)) {
            doAdd(request, response, paramRequest);
        }else if(SWBResourceURL.Action_REMOVE.equals(mode)) {
            doRemove(request, response, paramRequest);
        }else if(Mode_DETAIL.equals(mode)) {
            doDetail(request, response, paramRequest);
        }
        
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    public static ArrayList<WebPage> getFavWebPages(User user, WebSite site)
    {
        HashSet<WebPage> _pages = new HashSet<WebPage>();
        Iterator<SWBFavoriteWebPage> favs = SWBFavoriteWebPage.ClassMgr.listSWBFavoriteWebPageByUser(user, site);
        while (favs.hasNext())
        {
            SWBFavoriteWebPage swbfav = favs.next();
            _pages.add(swbfav.getFavorite());
//            GenericIterator<WebPage> pages = swbfav.getFavorite();
//            while (pages.hasNext())
//            {
//                WebPage page = pages.next();
//                _pages.add(page);
//            }
        }

        ArrayList<WebPage> favpages = new ArrayList<WebPage>();
        favpages.addAll(_pages);
        return favpages;
    }
    
    public static List<WebPage> getFavorites(User user, WebSite site) {
        TreeSet<SWBFavoriteWebPage> favpages = new TreeSet<SWBFavoriteWebPage>();
        Iterator<SWBFavoriteWebPage> favs = SWBFavoriteWebPage.ClassMgr.listSWBFavoriteWebPageByUser(user, site);
        while (favs.hasNext()) {
            favpages.add(favs.next());
        }
        
        ArrayList<WebPage> _pages = new ArrayList<WebPage>();
        favs = favpages.descendingIterator();
        while(favs.hasNext()) {
            _pages.add(favs.next().getFavorite());
        }
        return _pages;
    }

    public void doEditWebPage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String id = request.getParameter("id");
        String mode=request.getParameter("mode");
        if(mode==null)
        {
            return;
        }
        if("remove".equals(mode))
        {
            doRemove(request, response, paramRequest);
            return;
        }
        WebPage page = paramRequest.getWebPage().getWebSite().getWebPage(id);
        if (page != null)
        {
            User user = paramRequest.getUser();

            boolean exists = false;
            ArrayList<WebPage> pages = getFavWebPages(user, paramRequest.getWebPage().getWebSite());
            if (pages.contains(page))
            {
                exists = true;
            }
            if (!exists)
            {
                Iterator<SWBFavoriteWebPage> favs = SWBFavoriteWebPage.ClassMgr.listSWBFavoriteWebPageByUser(user, paramRequest.getWebPage().getWebSite());
                boolean add=false;
                while (favs.hasNext())
                {
                    SWBFavoriteWebPage fav = favs.next();
                    fav.setFavorite(page);
                    add=true;

                }
                if(!add)
                {
                    SWBFavoriteWebPage fav=SWBFavoriteWebPage.ClassMgr.createSWBFavoriteWebPage(paramRequest.getWebPage().getWebSite());
                    fav.setUser(user);
                    fav.setFavorite(page);
                }
            }

            String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
            String path = basePath + "edit.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            try
            {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("this", this);
                request.setAttribute("pages", getFavWebPages(user, paramRequest.getWebPage().getWebSite()));
                dis.include(request, response);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        Resource base = this.getResourceBase();
        User user = paramRequest.getUser();
        
        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        String path = basePath + "view.jsp";
        String mode = paramRequest.getArgument("mode");
        
        //if(base.getWebSite().getHomePage()!=paramRequest.getWebPage())
        if(mode != null && SWBResourceURL.Action_EDIT.equalsIgnoreCase(mode))
        {
            path = basePath + "edit.jsp";
        }        
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("mode", mode);            
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("this", this);
            request.setAttribute("pages", getFavWebPages(user, paramRequest.getWebPage().getWebSite()));
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
        
/*User user = paramRequest.getUser();
Iterator<SWBFavoriteWebPage> it = SWBFavoriteWebPage.ClassMgr.listSWBFavoriteWebPageByUser(user, paramRequest.getWebPage().getWebSite());
List<SWBFavoriteWebPage> list = SWBUtils.Collections.copyIterator(it);
Collections.sort(list, new RecentFavorites());
Collections.reverse(list);*/        
        
    }
    
    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        out.println("<input type=\"button\" value=\"agregar\" title=\"Agregar a Favoritos - Se desplegará en la página principal según tu perfil\" onClick=\"postHtml('<%=url%>','bm')\" />");
        out.close();
        out.flush();
    }
    
    public void doRemove(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        out.println("<input type=\"button\" value=\"quitar\" title=\"Eliminar de Favoritos\" onClick=\"postHtml('<%=url%>','bm')\" />");
        out.close();
        out.flush();
    }
    
    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        final String path = basePath + "detail.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        final String act = response.getAction();
        final User user = response.getUser();
        final WebPage thisPage = response.getWebPage();
        
        response.setCallMethod(SWBResourceURL.Call_DIRECT);
        if(SWBResourceURL.Action_ADD.equals(act) && user.isSigned()) {
            ArrayList<WebPage> myFavorites = getFavWebPages(user, base.getWebSite());
            if(!myFavorites.contains(thisPage)) {
                SWBFavoriteWebPage fav = SWBFavoriteWebPage.ClassMgr.createSWBFavoriteWebPage(response.getWebPage().getId(), base.getWebSite());
                fav.setFavorite(thisPage);
                fav.setSubscription(new Date());
                fav.setUser(user);
            }else {
            }
            response.setMode(SWBResourceURL.Action_REMOVE);
        }else if(SWBResourceURL.Action_REMOVE.equals(act) && user.isSigned()) {
            final String id = response.getWebPage().getId();
            ArrayList myFavorites = getFavWebPages(user, base.getWebSite());
            if(myFavorites.contains(thisPage)) {
                SWBFavoriteWebPage.ClassMgr.removeSWBFavoriteWebPage(id, base.getWebSite());
            }
            response.setMode(SWBResourceURL.Action_ADD);
        }
    }



static class RecentFavorites implements Comparator<SWBFavoriteWebPage> {
    public int compare(SWBFavoriteWebPage fav1, SWBFavoriteWebPage fav2) {
        return fav1.getSubscription().compareTo( fav2.getSubscription() );
    }
}
    

}
