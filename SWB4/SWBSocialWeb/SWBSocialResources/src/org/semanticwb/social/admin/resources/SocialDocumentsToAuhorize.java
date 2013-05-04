/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.Versionable;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.PFlowManager;
import org.semanticwb.portal.admin.resources.SWBDocumentsToAuthorize;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.GenericSemResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.PostOut;

/**
 *
 * @author jorge.jimenez
 */
public class SocialDocumentsToAuhorize extends GenericResource
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBDocumentsToAuthorize.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
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
                if (resource != null && SWBPortal.getPFlowManager().isReviewer(resource, user))
                {
                    String msg = request.getParameter("msg");                    
                    if (!msg.trim().equals(""))
                    {
                        String action = request.getParameter("wbaction");                        
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

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
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
            out.println("<script type=\"text/javascript\">");            
            out.println("dojo.require(\"dijit.Dialog\");");
            out.println("dojo.require(\"dijit.form.Button\");");
            out.println("dojo.require(\"dijit.form.Textarea\");");
            out.println("dojo.require(\"dojox.form.DropDownSelect\");");            
            out.println("</script>");

            

            

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
            out.println("<input " + selected + " onClick='document.frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show1' name='show' value='1'>" + paramRequest.getLocaleString("all") + "");
            selected = "";
            if (show == 2)
            {
                selected = "checked";
            }
            out.println("<input " + selected + " onClick='document.frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show2'  name='show' value='2'>" + paramRequest.getLocaleString("mydocuments") + "");
            selected = "";
            if (show == 3)
            {
                selected = "checked";
            }

            out.println("<input " + selected + "  onClick='document.frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show3' name='show' value='3'>" + paramRequest.getLocaleString("forauthorize") + "");
            out.println("</fieldset>");
            out.println("</form>");

            PostOut[] postOuts;
            if (show == 1)
            {
                //postOuts = SWBPortal.getPFlowManager().getContentsAtFlowAll(sitetoShow);
            }
            else if (show == 3)
            {
                //resources = SWBPortal.getPFlowManager().getContentsAtFlow(user, sitetoShow);
            }
            else
            {
                //resources = SWBPortal.getPFlowManager().getContentsAtFlowOfUser(user, sitetoShow);
            }

/*
            if (resources.length > 0)
            {
                // create dialog
                for (Resource resource : resources)
                {
                    GenericObject go=null;
                    boolean semanresource=false;
                    if(resource.getResourceData()!=null)
                    {
                        go=resource.getResourceData().createGenericInstance();
                        semanresource=go instanceof GenericSemResource;
                    }
                    if(semanresource)
                    {
                        String id=resource.getEncodedURI().replace('%', '_').replace(':', '_').replace('/', '_');

                        out.println("<div dojoType=\"dijit.Dialog\" id=\""+id+"\" title=\""+paramRequest.getLocaleString("properties")+" ("+resource.getTitle().replace('"','\'')+")\">");
                        out.println("<form class=\"swbform\">");
                        out.println("<fieldset>");
                        out.println("<table>");
                        out.println("<tr>");
                        out.println("<th>");
                        out.println(paramRequest.getLocaleString("propiedad_label"));
                        out.println("</th>");
                        out.println("<th>");
                        out.println(paramRequest.getLocaleString("valor_value"));
                        out.println("</th>");
                        out.println("</tr>");
                        Iterator<SemanticProperty> props=resource.getResourceData().getSemanticClass().listProperties();
                        while(props.hasNext())
                        {
                            SemanticProperty prop=props.next();
                            if(prop.getDisplayProperty()!=null)
                            {
                                String label=prop.getDisplayName(paramRequest.getUser().getLanguage());
                                String value=null;
                                if(prop.isDataTypeProperty())
                                {
                                    value=go.getSemanticObject().getProperty(prop);
                                }
                                else if(prop.isObjectProperty())
                                {
                                    SemanticObject ovalue=go.getSemanticObject().getObjectProperty(prop);
                                    if(ovalue!=null)
                                    {
                                        value=ovalue.getDisplayName(paramRequest.getUser().getLanguage());
                                    }
                                }
                                if(value==null)
                                {
                                    value=paramRequest.getLocaleString("novalue");
                                }
                               
                                out.println("<tr>");
                                out.println("<td>"+ label +"</td>");
                                out.println("<td>"+value+"</td>");
                                out.println("</tr>");
                            }
                        }
                        out.println("</table>");
                        out.println("</fieldset>");
                        out.println("</form>");
                        out.println("</div>");
                    }
                }
                if(resources.length>0)
                {
                    out.println("<div dojoType=\"dijit.Dialog\" id=\"dialogautorize\" title=\""+paramRequest.getLocaleString("authorize")+"\">");
                    out.println("<form name='swbfrmResourcesAuhotrize' class=\"swbform\" method='post' action='" + paramRequest.getActionUrl() + "'>");
                    out.println("<input type='hidden' name='wbaction' value='a'></input>");
                    out.println("<input type='hidden' name='res' value=''></input>");
                    out.println("<input type='hidden' name='site' value='" + sitetoShow.getId() + "'></input>");
                    out.println("<table>");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(paramRequest.getLocaleString("msg"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<textarea rows='6' cols='30' name=\"msg\">");
                    out.println("</textarea>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td align='center' colspan='2'>");
                    out.println("<button onClick='authorize();' dojoType=\"dijit.form.Button\" name='authorize' id='authorize' type='button'>"+ paramRequest.getLocaleString("authorize") +"</button>");
                    out.println("&nbsp;&nbsp;&nbsp;&nbsp;<button onClick=\"closeAuthorize();\" dojoType=\"dijit.form.Button\" name=\"cancel1\" id=\"cancel1\" type=\"button\">"+ paramRequest.getLocaleString("cancel") +"</button>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</form>");
                    out.println("</div>");

                    out.println("<div dojoType=\"dijit.Dialog\" id=\"dialogreject\" title=\""+paramRequest.getLocaleString("reject")+"\">");
                    out.println("<form name='swbfrmResourcesReject' class=\"swbform\" method='post' action='" + paramRequest.getActionUrl() + "'>");
                    out.println("<input type='hidden' name='wbaction' value='r'></input>");
                    out.println("<input type='hidden' name='res' value=''></input>");
                    out.println("<input type='hidden' name='site' value='" + sitetoShow.getId() + "'></input>");
                    out.println("<table>");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(paramRequest.getLocaleString("msg"));
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<textarea rows='6' cols='30' name=\"msg\">");
                    out.println("</textarea>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td align='center' colspan='2'>");
                    out.println("<button onClick='reject();' dojoType=\"dijit.form.Button\" name='reject' id='reject' type='button'>"+ paramRequest.getLocaleString("reject") +"</button>");
                    out.println("&nbsp;&nbsp;&nbsp;&nbsp;<button onClick=\"closeReject();\" dojoType=\"dijit.form.Button\" name=\"cancel2\" id=\"cancel2\" type=\"button\">"+ paramRequest.getLocaleString("cancel") +"</button>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</form>");
                    out.println("</div>");
                }
                out.println("<form class=\"swbform\" method='post' action='#'>");
                out.println("<fieldset>");                                
                
                out.println("<table width=\"100%\">");
                out.println("<tr>");                
                out.println("<th>");
                out.println(paramRequest.getLocaleString("title"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("page"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("step"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("action"));
                out.println("</th>");
                out.println("</tr>");
                for (Resource resource : resources)
                {
                    out.println("<tr>");
                    //out.println("<td width='10%'>");
                    PFlowManager manager=new PFlowManager();                    
                    SWBParamRequestImp  paramreq=new SWBParamRequestImp(request, resource, paramRequest.getWebPage(), user);
                    SWBResourceURL urlpreview=paramreq.getRenderUrl().setCallMethod(SWBParamRequestImp.Call_DIRECT);
                    if(resource.getResourceData().createGenericInstance() instanceof Versionable)
                    {
                        Versionable v=(Versionable)resource.getResourceData().createGenericInstance();
                        if(v!=null && v.getLastVersion()!=null)
                        {
                            urlpreview.setParameter("numversion", String.valueOf(v.getLastVersion().getVersionNumber()));
                        }
                    }
                    //out.println("<input type=\"radio\" onClick=\"javascript:habilita("+ manager.isReviewer(resource, user) +",'"+urlpreview+"')\" name=\"res\" value=\"" + resource.getId() + "\"></input>");

                    //out.println("</td>");
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
                    out.println("<td width='20%'>");
                    out.println(resource.getPflowInstance().getStep());
                    out.println("</td>");
                    out.println("</td>");
                    out.println("<td width='20%'>");
                    boolean semanresource=false;
                    if(resource.getResourceData()!=null)
                    {
                        GenericObject go=resource.getResourceData().createGenericInstance();
                        semanresource=go instanceof GenericSemResource;
                    }
                    if(semanresource)
                    {
                        String id=resource.getEncodedURI().replace('%', '_').replace(':', '_').replace('/', '_');
                        String imgview=SWBPortal.getContextPath()+"/swbadmin/icons/preview.gif";                        
                        out.println("<a title=\""+ paramRequest.getLocaleString("properties") +"\" onclick=\"view('"+urlpreview+"','"+ id +"')\" href=\"#\"><img src=\""+imgview+"\" alt=\""+paramRequest.getLocaleString("properties")+"\"></a>");
                    }   
                    String imgedit=SWBPortal.getContextPath()+"/swbadmin/icons/editar_1.gif";
                    out.println("<a title=\""+paramRequest.getLocaleString("edit")+"\" href=\"#\" onclick=\"parent.selectTab('"+ resource.getURI() +"','"+ SWBPortal.getContextPath() +"/swbadmin/jsp/objectTab.jsp','"+ resource.getTitle() +"','bh_AdminPorltet');return false;\"><img  src=\""+imgedit+"\"></a>");
                    if(manager.isReviewer(resource, user))
                    {                        
                        String imgauthorize=SWBPortal.getContextPath()+"/swbadmin/icons/activa.gif";
                        out.println("<a title=\""+paramRequest.getLocaleString("authorize")+"\" href=\"#\" onclick=\"showAuthorize('"+ resource.getId() +"')\"><img  src=\""+imgauthorize+"\"></a>");
                        String imgreject=SWBPortal.getContextPath()+"/swbadmin/images/delete.gif";
                        out.println("<a title=\""+paramRequest.getLocaleString("reject")+"\" href=\"#\" onclick=\"showReject('"+ resource.getId() +"')\"><img  src=\""+imgreject+"\"></a>");
                    }
                    
                    out.println("</td>");
                    out.println("</tr>");
                }                
                out.println("</table>");
                out.println("<fieldset>");
                out.println("</form>");

                out.println("<script type=\"text/javascript\">");                

                out.println("function closeAuthorize()");
                out.println("{");
                out.println("   var dialog=dijit.byId('dialogautorize');");
                out.println("   dialog.hide();");
                out.println("}");

                out.println("function showAuthorize(id)");
                out.println("{");
                out.println("   document.swbfrmResourcesAuhotrize.res.value=id;");
                out.println("   document.swbfrmResourcesAuhotrize.msg.value='';");
                out.println("   var dialog=dijit.byId('dialogautorize');");
                out.println("   dialog.show();");
                out.println("}");

                out.println("function closeReject()");
                out.println("{");
                out.println("   var dialog=dijit.byId('dialogreject');");
                out.println("   dialog.hide();");
                out.println("}");

                out.println("function showReject(id)");
                out.println("{");
                out.println("   document.swbfrmResourcesReject.res.value=id;");
                out.println("   document.swbfrmResourcesReject.msg.value='';");
                out.println("   var dialog=dijit.byId('dialogreject');");
                out.println("   dialog.show();");
                out.println("}");

                out.println("function authorize()");
                out.println("{");
                out.println("   if(document.swbfrmResourcesAuhotrize.msg.value=='')");
                out.println("   {");
                out.println("       alert('" + paramRequest.getLocaleString("messageRequired") + "');");
                out.println("       return;");
                out.println("   }");
                out.println("   var dialog=dijit.byId('dialogautorize');");
                out.println("   dialog.hide();");
                out.println("   document.swbfrmResourcesAuhotrize.submit();");                
                out.println("}");

                out.println("function view(url,id)");
                out.println("{");
                out.println("   var tDiv = document.getElementById(\"previewcontent\");");                                
                out.println("   tDiv.innerHTML=\"<iframe width='100%' height='500' src='\"+ url +\"'></iframe>\";");
                out.println("   dijit.byId(id).show()");
                out.println("}");                
                 
                out.println("function reject()");
                out.println("{");
                out.println("   if(document.swbfrmResourcesReject.msg.value=='')");
                out.println("   {");
                out.println("       alert('" + paramRequest.getLocaleString("messageRequired") + "');");
                out.println("       return;");
                out.println("   }");
                out.println("   var dialog=dijit.byId('dialogreject');");
                out.println("   dialog.hide();");
                out.println("   document.swbfrmResourcesReject.submit();");                
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
        out.println("<div id='previewcontent'>");
        out.println("</div>");
        out.close();
    }**/
}
    }
}