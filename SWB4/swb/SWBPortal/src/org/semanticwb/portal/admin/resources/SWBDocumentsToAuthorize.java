/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import com.sun.org.apache.regexp.internal.REUtil;
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
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author victor.lorenzana
 */
public class SWBDocumentsToAuthorize extends GenericResource
{

    private static Logger log = SWBUtils.getLogger(SWBDocumentsToAuthorize.class);

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        User user = response.getUser();
        response.setMode(response.Mode_VIEW);
        if (request.getParameter("msg") != null && request.getParameter("site") != null && request.getParameter("action") != null && request.getParameter("res") != null)
        {
            WebSite site = SWBContext.getWebSite(request.getParameter("site"));
            if (site != null)
            {
                Resource resource = site.getResource(request.getParameter("res"));
                if (resource != null && SWBPortal.getPFlowManager().isReviewer(resource, user))
                {
                    String msg = request.getParameter("msg");
                    if (!msg.trim().equals(""))
                    {
                        String action = request.getParameter("action");
                        if (action.equals("a"))
                        {
                            SWBPortal.getPFlowManager().approveResource(resource, user, msg);
                        }
                        if (action.equals("r"))
                        {
                            SWBPortal.getPFlowManager().rejectResource(resource, user, msg);
                        }
                    }
                }
            }
        }
    }

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
        /*out.println("<script type=\"text/javascript\">\n" +
                "           dojo.require(\"dojo.parser\");\n" +
                //"                   dojo.require(\"dijit.layout.ContentPane\");\n" +
                "                   dojo.require(\"dojox.form.DropDownSelect\");\n" +
                //"                   dojo.require(\"dijit.form.RadioButton\");\n" +
                "                   dojo.require(\"dijit.form.Textarea\");\n" +
                "        </script>\n");*/
        out.println("<form class=\"swbform\" method='post'>");
        out.println("<fieldset>");
        out.println("<select name='site' dojoType=\"dojox.form.DropDownSelect\" autocomplete=\"false\">");
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR)))
            {
                if (sitetoShow == null)
                {
                    sitetoShow = site;
                    out.println("<option selected value='" + site.getId() + "'>" + site.getTitle() + "</option>");
                }
                else
                {
                    if (sitetoShow.getId().equals(site.getId()))
                    {
                        out.println("<option selected value='" + site.getId() + "'>" + site.getTitle() + "</option>");
                    }
                    else
                    {
                        out.println("<option value='" + site.getId() + "'>" + site.getTitle() + "</option>");
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
        out.println("<label for=\"id='show1'\">"+paramRequest.getLocaleString("mydocuments")+"</label><input " + selected + " dojoType=\"dijit.form.RadioButton\" type='radio' id='show1' name='show' value='1'>" + paramRequest.getLocaleString("all") + "");
        selected = "";
        if (show == 2)
        {
            selected = "checked";
        }
        out.println("<label for=\"id='show2'\">"+paramRequest.getLocaleString("mydocuments")+"</label><input " + selected + " dojoType=\"dijit.form.RadioButton\" type='radio' id='show2'  name='show' value='2'>" + paramRequest.getLocaleString("mydocuments") + "");
        selected = "";
        if (show == 3)
        {
            selected = "checked";
        }
        
        out.println("<label for=\"id='show3'\">"+paramRequest.getLocaleString("mydocuments")+"</label><input " + selected + " dojoType=\"dijit.form.RadioButton\" type='radio' id='show3' name='show' value='3'>" + paramRequest.getLocaleString("forauthorize") + "");
        out.println("<label for=\"id='s'\">"+paramRequest.getLocaleString("see")+"</label><button dojoType=\"dijit.form.Button\" type='submit' id='s' name='s'>"+paramRequest.getLocaleString("see")+"</button>");
        out.println("</fieldset>");
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
        
        out.println("<form class=\"swbform\" name='swbfrmResourcesAuhotrize' method='post' action='" + paramRequest.getActionUrl() + "'>");
        out.println("<fieldset>");
        out.println("<input type='hidden' name='action' value=''></input>");
        out.println("<input type='hidden' name='site' value='" + sitetoShow.getId() + "'></input>");
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
            out.println("<input dojoType=\"dijit.form.RadioButton\" type=\"radio\" name=\"res\" value\"" + resource.getId() + "\">"+resource.getId()+"</input>");
            out.println("</td>");
            out.println("<td>");
            out.println(resource.getTitle());
            out.println("</td>");
            out.println("<td>");
            Iterator<Resourceable> resourceables = resource.listResourceables();
            while (resourceables.hasNext())
            {
                Resourceable resourceable = resourceables.next();
                if (resourceable instanceof WebPage)
                {
                    out.println(((WebPage) resourceable).getTitle());
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
        out.println("<td colspan='3'>");
        out.println("<textarea dojoType=\"dijit.form.Textarea\" name=\"msg\" rows=\"2\" cols=\"50\">");
        out.println("</textarea>");
        out.println("</td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td>");
        out.println("<button dojoType=\"dijit.form.Button\" OnClick=\"authorize()\">"+paramRequest.getLocaleString("authorize")+"</button>");
        out.println("<button dojoType=\"dijit.form.Button\" OnClick=\"reject()\">"+paramRequest.getLocaleString("reject")+"</button>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<fieldset>");
        out.println("</form>");
        

        out.println("<script>");
        out.println("function authorize()");
        out.println("{");
        out.println("   if(swbfrmResourcesAuhotrize.msg.value=='')");
        out.println("   {");
        out.println("       alert('" + paramRequest.getLocaleString("messageRequired") + "');");
        out.println("       return;");
        out.println("   }");
        out.println("   swbfrmResourcesAuhotrize.action.value='a';");
        out.println("   swbfrmResourcesAuhotrize.submit();");
        out.println("}");
        out.println("</script>");

        out.println("<script>");
        out.println("function reject()");
        out.println("{");
        out.println("   if(swbfrmResourcesAuhotrize.msg.value=='')");
        out.println("   {");
        out.println("       alert('" + paramRequest.getLocaleString("messageRequired") + "');");
        out.println("       return;");
        out.println("   }");
        out.println("   swbfrmResourcesAuhotrize.action.value='r';");
        out.println("   swbfrmResourcesAuhotrize.submit();");
        out.println("}");
        out.println("</script>");

        out.close();
    }
}
