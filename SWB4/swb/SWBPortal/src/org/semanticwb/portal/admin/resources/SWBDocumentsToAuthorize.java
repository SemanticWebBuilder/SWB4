/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class SWBDocumentsToAuthorize extends GenericResource
{

    private Logger log = SWBUtils.getLogger(SWBDocumentsToAuthorize.class);


    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

    }
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        int show = 2;
        if (request.getParameter("show") != null)
        {
            try
            {
                show = Integer.parseInt(request.getParameter("show"));
            }
            catch (Exception e)
            {
                log.event(e);
            }
        }
        WebSite sitetoShow = null;
        if (request.getParameter("site") != null)
        {
            sitetoShow = SWBContext.getWebSite(request.getParameter("site"));
        }
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        out.println("<form method='post'>");
        out.println("<select name='site'>");
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR)))
            {
                if (sitetoShow == null)
                {
                    sitetoShow = site;
                    out.println("<option selected value='" + site.getId() + "'>" + site.getTitle(user.getLanguage()) + "</option>");
                }
                else
                {
                    if (sitetoShow.getId().equals(site.getId()))
                    {
                        out.println("<option selected value='" + site.getId() + "'>" + site.getTitle(user.getLanguage()) + "</option>");
                    }
                    else
                    {
                        out.println("<option value='" + site.getId() + "'>" + site.getTitle(user.getLanguage()) + "</option>");
                    }

                }

            }
        }
        out.println("</select>");
        String selected = "";
        if (show == 1)
        {
            selected = "checked";
        }
        out.println("<input " + selected + " type='radio' name='show' value='1'>Todos</input>");
        if (show == 2)
        {
            selected = "checked";
        }
        out.println("<input " + selected + " type='radio' name='show' value='2'>Mis documentos</input>");
        if (show == 3)
        {
            selected = "checked";
        }
        out.println("<input " + selected + " type='radio' name='show' value='3'>Por autorizar</input>");
        out.println("</form>");

        Resource[] resources;
        if (show == 1)
        {
            resources = SWBPortal.getPFlowManager().getContentsAtFlowAll(sitetoShow);
        }
        else if (show == 3)
        {
            resources = SWBPortal.getPFlowManager().getContentsAtFlow(user, sitetoShow);
        }
        else
        {
            resources = SWBPortal.getPFlowManager().getContentsAtFlowOfUser(user, sitetoShow);
        }

        out.println("<form method='post' action='"+ paramRequest.getActionUrl()  +"'>");
        out.println("<input type='hidden' name='action' value=''></input>");
        out.println("<input type='hidden' name='site' value='"+ sitetoShow.getId() +"'></input>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>");
        out.println(paramRequest.getLocaleString("select"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramRequest.getLocaleString("title"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramRequest.getLocaleString("page"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramRequest.getLocaleString("step"));
        out.println("</th>");
        out.println("</tr>");
        for (Resource resource : resources)
        {
            out.println("<tr>");
            out.println("<td>");
            out.println("<input type='radio' name='res' value=" + resource.getId() + ">");
            out.println("</td>");
            out.println("<td>");
            out.println(resource.getTitle(user.getLanguage()));
            out.println("</td>");
            out.println("<td>");
            Iterator<Resourceable> resourceables = resource.listResourceables();
            while (resourceables.hasNext())
            {
                Resourceable resourceable = resourceables.next();
                if (resourceable instanceof WebSite)
                {
                    out.println(((WebSite) resourceable).getTitle(user.getLanguage()));
                    break;
                }
            }
            out.println("</td>");
            out.println("<td>");
            out.println(resource.getPflowInstance().getStep());
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("<tr>");
        out.println("<td>");
        out.println("<input type='button' value='"+paramRequest.getLocaleString("authorize")+"' OnClick=''></input>");
        out.println("<input type='button' value='"+paramRequest.getLocaleString("reject")+"' OnClick=''></input>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");
        out.close();
    }
}
