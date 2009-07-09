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
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.PFlowManager;
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
        if (request.getParameter("msg") != null && request.getParameter("site") != null && request.getParameter("wbaction") != null && request.getParameter("res") != null)
        {
            WebSite site = SWBContext.getWebSite(request.getParameter("site"));            
            if (site != null)
            {
                Resource resource = site.getResource(request.getParameter("res"));
                System.out.println("res "+request.getParameter("res"));
                System.out.println("resource "+resource.getId());
                if (resource != null && SWBPortal.getPFlowManager().isReviewer(resource, user))
                {

                    String msg = request.getParameter("msg");
                    System.out.println("msg "+msg);
                    if (!msg.trim().equals(""))
                    {
                        String action = request.getParameter("wbaction");
                        System.out.println("wbaction "+action);
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
                else
                {
                    System.out.println("is not reviewer");
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
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR)))
            {
                if (sitetoShow == null)
                {
                    sitetoShow = site;

                }
            }
        }
        if (sitetoShow != null)
        {
            out.println("<form class=\"swbform\" name='frmseecontentsToAuthorize' action='" + paramRequest.getRenderUrl() + "' method='post'>");
            out.println("<fieldset>");
            out.println("<select name='site' dojoType=\"dojox.form.DropDownSelect\" autocomplete=\"false\">");
            sites = SWBContext.listWebSites();
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
            out.println("<input " + selected + " onClick='frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show1' name='show' value='1'>" + paramRequest.getLocaleString("all") + "");
            selected = "";
            if (show == 2)
            {
                selected = "checked";
            }
            out.println("<input " + selected + " onClick='frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show2'  name='show' value='2'>" + paramRequest.getLocaleString("mydocuments") + "");
            selected = "";
            if (show == 3)
            {
                selected = "checked";
            }

            out.println("<input " + selected + "  onClick='frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show3' name='show' value='3'>" + paramRequest.getLocaleString("forauthorize") + "");
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


            if (resources.length > 0)
            {
                out.println("<form class=\"swbform\" name='swbfrmResourcesAuhotrize' method='post' action='" + paramRequest.getActionUrl() + "'>");
                out.println("<fieldset>");
                out.println("<input type='hidden' name='wbaction' value=''></input>");
                out.println("<input type='hidden' name='site' value='" + sitetoShow.getId() + "'></input>");
                out.println("<table width=\"100%\">");
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
                    out.println("<td width='10%'>");
                    PFlowManager manager=new PFlowManager();
                    out.println("<input type=\"radio\" onClick=\"javascript:habilita("+ manager.isReviewer(resource, user) +")\" name=\"res\" value=\"" + resource.getId() + "\"></input>");
                    out.println("</td>");
                    out.println("<td width='30%'>");
                    out.println(resource.getTitle());
                    out.println("</td>");
                    out.println("<td width='30%'>");
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
                    out.println("<td width='30%'>");
                    out.println(resource.getPflowInstance().getStep());
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("<tr>");
                out.println("<td>");
                out.println("&nbsp;");
                out.println("</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td colspan='4'>");
                out.println("Mensaje:<textarea rows='6' cols='30' name=\"msg\">");
                out.println("</textarea>");
                out.println("</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td colspan='4'>");
                out.println("<button onClick='authorize();' dojoType=\"dijit.form.Button\" name='authorize' id='authorize' type='button'>"+ paramRequest.getLocaleString("authorize") +"</button>");
                out.println("<button onClick='reject();' dojoType=\"dijit.form.Button\" name='reject' id='reject' type='button'>"+ paramRequest.getLocaleString("reject") +"</button>");
                out.println("<button onClick='view();' dojoType=\"dijit.form.Button\" name='view' id='view' type='button'>Ver contenido</button>");

               
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<fieldset>");
                out.println("</form>");

                out.println("<script type=\"text/javascript\">");
                out.println("dojo.require(\"dijit.form.Button\");");
                out.println("dojo.require(\"dijit.form.Textarea\");");
                out.println("dojo.require(\"dojox.form.DropDownSelect\");");
                out.println("function authorize()");
                out.println("{");
                out.println("   if(swbfrmResourcesAuhotrize.msg.value=='')");
                out.println("   {");
                out.println("       alert('" + paramRequest.getLocaleString("messageRequired") + "');");
                out.println("       return;");
                out.println("   }");
                out.println("   swbfrmResourcesAuhotrize.wbaction.value='a';");                
                out.println("   swbfrmResourcesAuhotrize.submit();");
                out.println("}");
                out.println("function view()");
                out.println("{");
                out.println("}");
                out.println("function habilita(valor)");
                out.println("{");
                out.println("   if(valor)");
                out.println("   {");
                out.println("       swbfrmResourcesAuhotrize.authorize.enabled=true;");
                out.println("       swbfrmResourcesAuhotrize.reject.enabled=false;");
                out.println("   }");
                out.println("   else");
                out.println("   {");
                out.println("       swbfrmResourcesAuhotrize.authorize.enabled=false;");
                out.println("       swbfrmResourcesAuhotrize.reject.enabled=true;");
                out.println("   }");
                out.println("}");                
                out.println("function reject()");
                out.println("{");
                out.println("   if(swbfrmResourcesAuhotrize.msg.value=='')");
                out.println("   {");
                out.println("       alert('" + paramRequest.getLocaleString("messageRequired") + "');");
                out.println("       return;");
                out.println("   }");
                out.println("   swbfrmResourcesAuhotrize.wbaction.value='r';");                
                out.println("   swbfrmResourcesAuhotrize.submit();");
                out.println("}");
                out.println("</script>");
            }
            else
            {
                out.println("<div class=\"swbform\">");
                out.println("<p>"+paramRequest.getLocaleString("messageNoContents")+"</p>");
                out.println("</div>");

            }
        }
        else
        {
            out.println("<div class=\"swbform\">");
            out.println("<p>" + paramRequest.getLocaleString("messageNoSites") + "</p>");
            out.println("</div>");
        }
        out.println("<div id='previewconten'>");
        out.println("</div>");
        out.close();
    }
}
