package org.semanticwb.portal.resources.sem.favoriteWebPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

public class SWBFavoriteWebPagesResource extends org.semanticwb.portal.resources.sem.favoriteWebPages.base.SWBFavoriteWebPagesResourceBase
{

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
        if (paramRequest.getMode().equals("edit"))
        {
            doEditWebPage(request, response, paramRequest);
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
            GenericIterator<WebPage> pages = swbfav.listWebPages();
            while (pages.hasNext())
            {
                WebPage page = pages.next();
                _pages.add(page);
            }
        }

        ArrayList<WebPage> favpages = new ArrayList<WebPage>();
        favpages.addAll(_pages);
        return favpages;
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
                    fav.addWebPage(page);
                    add=true;

                }
                if(!add)
                {
                    SWBFavoriteWebPage fav=SWBFavoriteWebPage.ClassMgr.createSWBFavoriteWebPage(paramRequest.getWebPage().getWebSite());
                    fav.setUser(user);
                    fav.addWebPage(page);
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

    public void doRemove(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String id = request.getParameter("id");
        WebPage page = paramRequest.getWebPage().getWebSite().getWebPage(id);
        if (page != null)
        {
            boolean exists = false;
            User user = paramRequest.getUser();
            ArrayList<WebPage> pages = getFavWebPages(user, paramRequest.getWebPage().getWebSite());
            if (pages.contains(page))
            {
                Iterator<SWBFavoriteWebPage> favs = SWBFavoriteWebPage.ClassMgr.listSWBFavoriteWebPageByUser(user, paramRequest.getWebPage().getWebSite());
                while (favs.hasNext())
                {
                    SWBFavoriteWebPage fav = favs.next();
                    fav.removeWebPage(page);
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
        User user = paramRequest.getUser();


        String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + this.getClass().getSimpleName() + "/";
        String path = basePath + "view.jsp";
        String mode = paramRequest.getArgument("mode");

        if (mode != null && "edit".equals(mode))
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

    }
}
