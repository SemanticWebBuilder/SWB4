/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.resources.sem.favoriteWebPages.base.SWBFavoriteWebPagesResourceBase;

public class SWBFavoriteWebPagesResource extends SWBFavoriteWebPagesResourceBase
{
//    public static final String Mode_DETAIL = "dtl";
//    public static final String Mode_REMOVE = "rem";
    private static final Logger log = SWBUtils.getLogger(SWBFavoriteWebPagesResource.class);

    public SWBFavoriteWebPagesResource()
    {
    }

    public SWBFavoriteWebPagesResource(SemanticObject base)
    {
    super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        final String mode = paramRequest.getMode();
//        if(Mode_REMOVE.equals(mode))
//            doRemove(request, response, paramRequest);
//        else
            super.processRequest(request, response, paramRequest);
    }


    public static ArrayList<WebPage> getFavWebPages(User user, WebSite site)
    {
        HashSet _pages = new HashSet();
        Iterator favs = SWBFavoriteWebPage.ClassMgr.listSWBFavoriteWebPageByUser(user, site);
        while (favs.hasNext())
        {
            SWBFavoriteWebPage swbfav = (SWBFavoriteWebPage)favs.next();
            _pages.add(swbfav.getFavorite());
        }
        ArrayList favpages = new ArrayList();
        favpages.addAll(_pages);
        return favpages;
    }

    public static List<WebPage> getFavorites(User user, WebSite site) {
        TreeSet<SWBFavoriteWebPage> favs = new TreeSet();
        Iterator<SWBFavoriteWebPage> ifavs = SWBFavoriteWebPage.ClassMgr.listSWBFavoriteWebPageByUser(user, site);
        favs.addAll(SWBUtils.Collections.copyIterator(ifavs));

        ArrayList<WebPage> _pages = new ArrayList();
        ifavs = favs.descendingIterator();
        while(ifavs.hasNext()) {
            _pages.add(((SWBFavoriteWebPage)ifavs.next()).getFavorite());
        }
        return _pages;
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        User user = paramRequest.getUser();
        if(user.isSigned()) {
            final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + getClass().getSimpleName() + "/";
            String path = basePath + "view.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("pages", getFavWebPages(user, paramRequest.getWebPage().getWebSite()));
                dis.include(request, response);
            }catch (Exception e) {
                log.error(e);
            }
            /*
            WebPage page = paramRequest.getWebPage();
//            ArrayList<WebPage> pages = getFavWebPages(user, paramRequest.getWebPage().getWebSite());
//            if(!pages.contains(page)) {
//                final WebSite model = paramRequest.getWebPage().getWebSite();
//                final String favId = model.getId()+"_"+user.getId()+"_"+page.getId();
//                SWBFavoriteWebPage fav = SWBFavoriteWebPage.ClassMgr.createSWBFavoriteWebPage(favId, model);
//                fav.setUser(user);
//                fav.setFavorite(page);
                
                PrintWriter out = response.getWriter();
                SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_REMOVE).setCallMethod(SWBResourceURL.Call_DIRECT);
                out.println("<a href=\"javascript:postHtml('"+url+"','fv_"+page.getId()+"')\" title=\"Quitar de mi lista de favoritos\">Borrar de favoritos<a/>");
//            }
                */
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");

        User user = paramRequest.getUser();
        final String basePath = "/work/models/" + paramRequest.getWebPage().getWebSite().getId() + "/jsp/" + getClass().getSimpleName() + "/";
        String path;
        if(paramRequest.getCallMethod() == SWBResourceURL.Call_STRATEGY) {
            path = basePath + "edit.jsp";
            String mode = paramRequest.getArgument("mode");
            if("edit".equalsIgnoreCase(mode)) {
                path = basePath + "list.jsp";
            }
        }
        else if(paramRequest.getCallMethod() == SWBResourceURL.Call_CONTENT) {
            path = basePath + "detail.jsp";
        }
        else {
            path = basePath + "view.jsp";
        }
        
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
//            request.setAttribute("this", this);
            request.setAttribute("pages", getFavWebPages(user, paramRequest.getWebPage().getWebSite()));
            dis.include(request, response);
        }catch (Exception e) {
            log.error(e);
        }
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        User user = response.getUser();
        if(!user.isSigned())
            return;
        
        Resource base = getResourceBase();
        final String action = response.getAction();
        if(SWBResourceURL.Action_ADD.equals(action)) {
            response.setMode(SWBResourceURL.Mode_EDIT);
            final WebPage page = response.getWebPage();
            final WebSite model = base.getWebSite();
            final String favId = model.getId()+"_"+user.getId()+"_"+page.getId();
            if(!SWBFavoriteWebPage.ClassMgr.hasSWBFavoriteWebPage(favId, model)) {
                SWBFavoriteWebPage fav = SWBFavoriteWebPage.ClassMgr.createSWBFavoriteWebPage(favId, model);
                fav.setFavorite(page);
                fav.setUser(user);
                fav.setSubscription(new Date());
            }
        }else if(SWBResourceURL.Action_REMOVE.equals(action)) {
            response.setMode(SWBResourceURL.Mode_EDIT);
            final WebPage page = response.getWebPage();
            final WebSite model = base.getWebSite();
            final String favId = model.getId()+"_"+user.getId()+"_"+page.getId();
//            ArrayList<WebPage> pages = getFavWebPages(user, model);
            if(SWBFavoriteWebPage.ClassMgr.hasSWBFavoriteWebPage(favId, model)) {
//                SWBFavoriteWebPage favp = SWBFavoriteWebPage.ClassMgr.getSWBFavoriteWebPage(favId, model);
                SWBFavoriteWebPage.ClassMgr.removeSWBFavoriteWebPage(favId, model);
            }
        }
    }

//    public void doRemove(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        response.setContentType("text/html; charset=ISO-8859-1");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Pragma", "no-cache");
//        
//        User user = paramRequest.getUser();
//        if(user.isSigned()) {
//            PrintWriter out = response.getWriter();
//            SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD).setCallMethod(SWBResourceURL.Call_DIRECT);
//            out.println("<a href=\"javascript:postHtml('"+url+"','fv_"+paramRequest.getWebPage().getId()+"')\" title=\"Agregar a mi lista de favoritos\">Agregar como favoritos<a/>");
//            
//        }
//    }

    static class RecentFavorites implements Comparator<SWBFavoriteWebPage>
    {
        @Override
        public int compare(SWBFavoriteWebPage fav1, SWBFavoriteWebPage fav2)
        {
            return fav1.getSubscription().compareTo(fav2.getSubscription());
        }
    }
}