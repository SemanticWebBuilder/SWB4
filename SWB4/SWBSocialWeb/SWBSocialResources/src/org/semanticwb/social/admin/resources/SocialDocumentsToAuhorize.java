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
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.PostOutNet;
import org.semanticwb.social.PostOutPrivacyRelation;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.util.SocialLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBDocumentsToAuthorize.
 *
 * @author Jorge.Jiménez
 */
public class SocialDocumentsToAuhorize extends GenericResource {

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(SocialDocumentsToAuhorize.class);
    public static final String Mode_SOURCE = "source";
    private static final String Mode_ShowMoreNets = "showMoreNets";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_SOURCE.equals(mode)) {
            doShowSource(request, response, paramRequest);
        } else if (Mode_ShowMoreNets.equals(mode)) {
            doShowMoreNets(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        User user = response.getUser();
        response.setMode(response.Mode_VIEW);
        if (request.getParameter("msg") != null && request.getParameter("site") != null && request.getParameter("wbaction") != null && request.getParameter("res") != null) {
            WebSite site = SWBContext.getWebSite(request.getParameter("site"));
            if (site != null) {
                SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("res"));
                if (semObj == null) {
                    return;
                }
                if (!(semObj.createGenericInstance() instanceof PostOut)) {
                    return;
                }
                PostOut postOut = (PostOut) semObj.createGenericInstance();
                if (postOut != null && SocialLoader.getPFlowManager().isReviewer(postOut, user)) {
                    String msg = request.getParameter("msg");
                    if (!msg.trim().equals("")) {
                        String action = request.getParameter("wbaction");
                        if (action.equals("a")) {
                            SocialLoader.getPFlowManager().approveResource(postOut, user, msg);
                        }
                        if (action.equals("r")) {
                            SocialLoader.getPFlowManager().rejectResource(postOut, user, msg);
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
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String lang = paramRequest.getUser().getLanguage();
        int show = 2;
        if (request.getParameter("show") != null) {
            try {
                show = Integer.parseInt(request.getParameter("show"));
            } catch (Exception e) {
                log.event(e);
            }
        }
        WebSite sitetoShow = null;
        if (request.getParameter("site") != null) {
            sitetoShow = SWBContext.getWebSite(request.getParameter("site"));
        }
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext()) {
            WebSite site = sites.next();
            if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR))) {
                if (sitetoShow == null) {
                    sitetoShow = site;

                }
            }
        }
        out.println("<style type=\"text/css\">");
        out.println("@import \"/swbadmin/js/dojo/dojo/resources/dojo.css\";");
        out.println("@import \"/swbadmin/js/dojo/dijit/themes/soria/soria.css\";");
        out.println("@import \"/swbadmin/css/swb.css\";");
        out.println("@import \"/swbadmin/css/swbsocial.css\";");
        out.println("@import \"/swbadmin/js/dojo/dojox/grid/resources/soriaGrid.css\";");
        out.println("@import \"/swbadmin/js/dojo/dojox/grid/resources/Grid.css\";");
        out.println("");
        out.println("html, body, #main{");
        out.println("overflow: auto;");
        out.println("}");
        out.println("</style>  ");
        out.println("");
        out.println("");

        if (sitetoShow != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("dojo.require(\"dijit.Dialog\");");
            out.println("dojo.require(\"dijit.form.Button\");");
            out.println("dojo.require(\"dijit.form.Textarea\");");
            out.println("dojo.require(\"dojox.form.DropDownSelect\");");
            out.println("</script>");

            //Jorge
            out.println("<script type=\"text/javascript\" src=\"/swbadmin/js/dojo/dojo/dojo.js\" ></script>");
            out.println("<script type=\"text/javascript\" charset=\"utf-8\" src=\"/swbadmin/js/swb.js\"></script>");
            out.println("<script type=\"text/javascript\" charset=\"utf-8\" src=\"/swbadmin/js/swb_admin.js\"></script>");
            out.println("<script type=\"text/javascript\" charset=\"utf-8\" src=\"/work/models/SWBAdmin/js/swbsocial.js\" ></script>");
            //Jorge


            out.println("<form class=\"swbform\" name='frmseecontentsToAuthorize' action='" + paramRequest.getRenderUrl() + "' method='post'>");
            out.println("<fieldset>");
            out.println("<select name='site' dojoType=\"dojox.form.DropDownSelect\" autocomplete=\"false\">");
            sites = SWBContext.listWebSites();
            while (sites.hasNext()) {
                WebSite site = sites.next();
                if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR)) && site.isValid()) {
                    /*
                     if (sitetoShow == null)
                     {
                     sitetoShow = site;
                     out.println("<option selected value='" + site.getId() + "'>" + site.getTitle() + "</option>");
                     }
                     else
                     {*/
                    if (sitetoShow.getId().equals(site.getId())) {
                        out.println("<option selected=\"true\" value='" + site.getId() + "'>" + site.getTitle() + "</option>");
                    } else {
                        out.println("<option value='" + site.getId() + "'>" + site.getTitle() + "</option>");
                    }

                    //}
                }
            }
            out.println("</select>");
            String selected = "";
            if (show == 1) {
                selected = "checked";
            }
            out.println("<input " + selected + " onClick='document.frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show1' name='show' value='1'>" + paramRequest.getLocaleString("all") + "");
            selected = "";
            if (show == 2) {
                selected = "checked";
            }
            out.println("<input " + selected + " onClick='document.frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show2'  name='show' value='2'>" + paramRequest.getLocaleString("mydocuments") + "");
            selected = "";
            if (show == 3) {
                selected = "checked";
            }

            out.println("<input " + selected + "  onClick='document.frmseecontentsToAuthorize.submit();' dojoType=\"dijit.form.RadioButton\" type='radio' id='show3' name='show' value='3'>" + paramRequest.getLocaleString("forauthorize") + "");
            out.println("</fieldset>");
            out.println("</form>");

            PostOut[] resources;
            if (show == 1) {
                resources = SocialLoader.getPFlowManager().getContentsAtFlowAll(sitetoShow);
            } else if (show == 3) {
                resources = SocialLoader.getPFlowManager().getContentsAtFlow(user, sitetoShow);
            } else {
                resources = SocialLoader.getPFlowManager().getContentsAtFlowOfUser(user, sitetoShow);
            }


            if (resources.length > 0) {
                // create dialog
                for (PostOut resource : resources) {

                    String id = resource.getEncodedURI().replace('%', '_').replace(':', '_').replace('/', '_');
                    String resTitle = resource.getMsg_Text() != null ? resource.getMsg_Text().replace('"', '\'') : "";
                    out.println("<div dojoType=\"dijit.Dialog\" id=\"" + id + "\" title=\"" + paramRequest.getLocaleString("properties") + " (" + resTitle + ")\">");
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
                    Iterator<SemanticProperty> props = resource.getSemanticObject().getSemanticClass().listProperties();
                    while (props.hasNext()) {
                        SemanticProperty prop = props.next();
                        if (prop.getDisplayProperty() != null) {
                            String label = prop.getDisplayName(paramRequest.getUser().getLanguage());
                            String value = null;
                            if (prop.isDataTypeProperty()) {
                                value = resource.getSemanticObject().getProperty(prop);
                            } else if (prop.isObjectProperty()) {
                                SemanticObject ovalue = resource.getSemanticObject().getObjectProperty(prop);
                                if (ovalue != null) {
                                    value = ovalue.getDisplayName(paramRequest.getUser().getLanguage());
                                }
                            }
                            if (value == null) {
                                value = paramRequest.getLocaleString("novalue");
                            }

                            out.println("<tr>");
                            out.println("<td>" + label + "</td>");
                            out.println("<td>" + value + "</td>");
                            out.println("</tr>");
                        }
                    }
                    if (resource.getPflowInstance() != null) {
                        out.println("<tr>");
                        out.println("<td>" + paramRequest.getLocaleString("flow") + "</td>");
                        out.println("<td>" + resource.getPflowInstance().getPflow().getDisplayTitle(lang) + "</td>");
                        out.println("</tr>");
                    }
                    if (resource.getSocialTopic() != null) {
                        out.println("<tr>");
                        out.println("<td>" + paramRequest.getLocaleString("topic") + "</td>");
                        out.println("<td>" + resource.getSocialTopic().getDisplayTitle(lang) + "</td>");
                        out.println("</tr>");
                    }


                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("</form>");
                    out.println("</div>");

                    //Jorge
                    out.println("<div dojoType=\"dijit.Dialog\" id=\"" + id + "_watchMoreNets\"  title=\"" + paramRequest.getLocaleString("associatedSocialNets") + " (" + resTitle + ")\">");
                    int cont = 1;
                    String nets = "";
                    Iterator<SocialNetwork> itPostSocialNets = resource.listSocialNetworks();
                    while (itPostSocialNets.hasNext()) {
                        SocialNetwork socialNet = itPostSocialNets.next();
                        //System.out.println("socialNet-1:"+socialNet);
                        String sSocialNet = socialNet.getDisplayTitle(user.getLanguage());
                        //System.out.println("socialNet-2:"+sSocialNet);
                        if (sSocialNet != null && sSocialNet.trim().length() > 0) {
                            //System.out.println("socialNet-3:"+sSocialNet);
                            //Sacar privacidad
                            String sPrivacy = null;
                            try {
                                //Si es necesario, cambiar esto por querys del Jei despues.
                                Iterator<PostOutPrivacyRelation> itpostOutPriRel = PostOutPrivacyRelation.ClassMgr.listPostOutPrivacyRelationByPopr_postOut(resource, sitetoShow);
                                while (itpostOutPriRel.hasNext()) {
                                    PostOutPrivacyRelation poPrivRel = itpostOutPriRel.next();
                                    if (poPrivRel.getPopr_socialNetwork().getURI().equals(socialNet.getURI())) {
                                        sPrivacy = poPrivRel.getPopr_privacy().getTitle(user.getLanguage());
                                    }
                                }
                                if (sPrivacy == null) {
                                    Iterator<PostOutNet> itpostOutNet = PostOutNet.ClassMgr.listPostOutNetBySocialPost(resource, sitetoShow);
                                    while (itpostOutNet.hasNext()) {
                                        PostOutNet postOutnet = itpostOutNet.next();
                                        if (postOutnet.getSocialNetwork().getURI().equals(socialNet.getURI()) && postOutnet.getPo_privacy() != null) {
                                            sPrivacy = postOutnet.getPo_privacy().getTitle(user.getLanguage());
                                        }
                                    }
                                }
                            } catch (Exception ignored) {
                            }

                            if (sPrivacy == null) {
                                sPrivacy = paramRequest.getLocaleString("public");
                            }

                            //Termina privacidad
                            if (cont == 1) {
                                nets = "<li>" + sSocialNet + "(" + sPrivacy + ")" + "</li>";
                                cont++;
                            } else {
                                nets += "<li>" + sSocialNet + "(" + sPrivacy + ")" + "</li>";
                            }
                        }
                    }
                    out.println(nets);
                    out.println("</div>");
                    //Jorge
                }
                if (resources.length > 0) {

                    //Jorge
                    out.println("");
                    out.println("");
                    out.println("");
                    out.println("");

                    out.println("<div dojoType=\"dijit.Dialog\" id=\"dialogautorize\" title=\"" + paramRequest.getLocaleString("authorize") + "\">");
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
                    out.println("<button onClick='authorize();' dojoType=\"dijit.form.Button\" name='authorize' id='authorize' type='button'>" + paramRequest.getLocaleString("authorize") + "</button>");
                    out.println("&nbsp;&nbsp;&nbsp;&nbsp;<button onClick=\"closeAuthorize();\" dojoType=\"dijit.form.Button\" name=\"cancel1\" id=\"cancel1\" type=\"button\">" + paramRequest.getLocaleString("cancel") + "</button>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</form>");
                    out.println("</div>");

                    out.println("<div dojoType=\"dijit.Dialog\" id=\"dialogreject\" title=\"" + paramRequest.getLocaleString("reject") + "\">");
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
                    out.println("<button onClick='reject();' dojoType=\"dijit.form.Button\" name='reject' id='reject' type='button'>" + paramRequest.getLocaleString("reject") + "</button>");
                    out.println("&nbsp;&nbsp;&nbsp;&nbsp;<button onClick=\"closeReject();\" dojoType=\"dijit.form.Button\" name=\"cancel2\" id=\"cancel2\" type=\"button\">" + paramRequest.getLocaleString("cancel") + "</button>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</form>");
                    out.println("</div>");
                }

//                out.println("<style type=\"text/css\">");
//                out.println("@import \"/swbadmin/js/dojo/dojo/resources/dojo.css\";");
//                out.println("@import \"/swbadmin/js/dojo/dijit/themes/soria/soria.css\";");
//                out.println("@import \"/swbadmin/css/swb.css\";");
//                out.println("@import \"/swbadmin/css/swbsocial.css\";");
//                out.println("@import \"/swbadmin/js/dojo/dojox/grid/resources/soriaGrid.css\";");
//                out.println("@import \"/swbadmin/js/dojo/dojox/grid/resources/Grid.css\";");
//                out.println("");
//                out.println("html, body, #main{");
//                out.println("overflow: auto;");
//                out.println("}");
//                out.println("</style>  ");
//                out.println("");
//                out.println("");













                out.println("<form class=\"swbform\" method='post' action='#'>");
                out.println("<fieldset>");

                out.println("<table class=\"tabla1\">");
                out.println("<tr>");
                out.println("<th class=\"accion\"> ");
                out.println(paramRequest.getLocaleString("action"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("title"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("topic"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("flow"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("step"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("socialNet"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramRequest.getLocaleString("source"));
                out.println("</th>");
                out.println("</tr>");
                for (PostOut resource : resources) {
                    out.println("<tr>");


                    WebSite wsite = SWBContext.getAdminWebSite();

                    WebPage wpShowPostOut = wsite.getWebPage("ShowPostOut");
                    Resource resrPostOut = wsite.getResource("143");
                    request.setAttribute("postOut", resource.getId());

                    SWBParamRequestImp paramreq = new SWBParamRequestImp(request, resrPostOut, wpShowPostOut, user);
                    SWBResourceURL urlpreview = paramreq.getRenderUrl().setCallMethod(SWBParamRequestImp.Call_DIRECT);
                    urlpreview.setParameter("postOut", resource.getURI());
                    urlpreview.setParameter("wsite", resource.getSemanticObject().getModel().getName());

                    out.println("<td class=\"accion\">");
                    try {
                        String id = resource.getEncodedURI().replace('%', '_').replace(':', '_').replace('/', '_');
                        //String imgview = SWBPortal.getContextPath() + "/swbadmin/icons/preview.gif";
                        //out.println("<a class=\"ver\" title=\""+ paramRequest.getLocaleString("properties") +"\" onclick=\"view('"+urlpreview+"','"+ id +"')\" href=\"#\"><img src=\""+imgview+"\" alt=\""+paramRequest.getLocaleString("properties")+"\"></a>");
                        out.println("<a class=\"ver\" title=\"" + paramRequest.getLocaleString("properties") + "\" onclick=\"view('" + urlpreview + "','" + id + "')\" href=\"#\"></a>");
                        if (SocialLoader.getPFlowManager().isReviewer(resource, user)) {
                            String imgedit = SWBPortal.getContextPath() + "/swbadmin/icons/editar_1.gif";
                            //out.println("<a title=\"" + paramRequest.getLocaleString("edit") + "\" href=\"#\" onclick=\"parent.selectTab('" + resource.getURI() + "','" + SWBPortal.getContextPath() + "/swbadmin/jsp/objectTab.jsp','" + "TEST" + "','bh_AdminPorltet');return false;\"><img  src=\"" + imgedit + "\"></a>");
                            out.println("<a class=\"editar\" title=\"" + paramRequest.getLocaleString("edit") + "\" href=\"#\" onclick=\"parent.selectTab('" + resource.getURI() + "','" + SWBPortal.getContextPath() + "/swbadmin/jsp/objectTab.jsp','" + "TEST" + "','bh_AdminPorltet');return false;\"></a>");
                            String imgauthorize = SWBPortal.getContextPath() + "/swbadmin/icons/activa.gif";
                            //out.println("<a title=\"" + paramRequest.getLocaleString("authorize") + "\" href=\"#\" onclick=\"showAuthorize('" + resource.getURI() + "')\"><img  src=\"" + imgauthorize + "\"></a>");
                            out.println("<a class=\"autorizar\" title=\"" + paramRequest.getLocaleString("authorize") + "\" href=\"#\" onclick=\"showAuthorize('" + resource.getURI() + "')\"></a>");
                            String imgreject = SWBPortal.getContextPath() + "/swbadmin/images/delete.gif";
                            //out.println("<a title=\"" + paramRequest.getLocaleString("reject") + "\" href=\"#\" onclick=\"showReject('" + resource.getURI() + "')\"><img  src=\"" + imgreject + "\"></a>");
                            out.println("<a class=\"eliminar\" title=\"" + paramRequest.getLocaleString("reject") + "\" href=\"#\" onclick=\"showReject('" + resource.getURI() + "')\"></a>");
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }

                    out.println("</td>");



                    out.println("<td width='40%'>");
                    out.println(resource.getMsg_Text() != null ? resource.getMsg_Text() : "");
                    out.println("</td>");
                    out.println("<td width='10%'>");
                    out.println(resource.getSocialTopic().getTitle() != null ? resource.getSocialTopic().getTitle() : "");
                    out.println("</td>");
                    out.println("<td width='10%'>");
                    out.println(resource.getPflowInstance().getPflow().getDisplayTitle(lang));
                    out.println("</td>");
                    out.println("<td width='10%'>");
                    out.println(resource.getPflowInstance().getStep());
                    out.println("</td>");
                    //Redes Sociales a las que esta dirigido el mensaje
                    out.println("<td width='10%'>");
                    //out.println(resource.getSocialNetwork()!=null?resource.getSocialNetwork().getDisplayTitle(lang):"---");
                    int cont = 0;
                    String nets = "";
                    Iterator<SocialNetwork> itPostSocialNets = resource.listSocialNetworks();
                    while (itPostSocialNets.hasNext()) {
                        cont++;
                        if (cont > 1) {
                            break; //Determinamos que solo se mostrara una y se mostrara un "ver mas" en dado caso que fueran mas redes sociales.
                        }
                        SocialNetwork socialNet = itPostSocialNets.next();
                        //System.out.println("socialNet-1:"+socialNet);
                        String sSocialNet = socialNet.getDisplayTitle(lang);
                        //System.out.println("socialNet-2:"+sSocialNet);
                        if (sSocialNet != null && sSocialNet.trim().length() > 0) {
                            //System.out.println("socialNet-3:"+sSocialNet);
                            //Sacar privacidad
                            String sPrivacy = null;
                            //Si es necesario, cambiar esto por querys del Jei despues.
                            Iterator<PostOutPrivacyRelation> itpostOutPriRel = PostOutPrivacyRelation.ClassMgr.listPostOutPrivacyRelationByPopr_postOut(resource, sitetoShow);
                            while (itpostOutPriRel.hasNext()) {
                                PostOutPrivacyRelation poPrivRel = itpostOutPriRel.next();
                                if (poPrivRel.getPopr_socialNetwork().getURI().equals(socialNet.getURI())) {
                                    sPrivacy = poPrivRel.getPopr_privacy().getTitle(lang);
                                }
                            }
                            if (sPrivacy == null) {
                                Iterator<PostOutNet> itpostOutNet = PostOutNet.ClassMgr.listPostOutNetBySocialPost(resource, sitetoShow);
                                while (itpostOutNet.hasNext()) {
                                    PostOutNet postOutnet = itpostOutNet.next();
                                    if (postOutnet.getSocialNetwork().getURI().equals(socialNet.getURI()) && postOutnet.getPo_privacy() != null) {
                                        sPrivacy = postOutnet.getPo_privacy().getTitle(lang);
                                    }
                                }
                            }
                            if (sPrivacy == null) {
                                sPrivacy = paramRequest.getLocaleString("public");
                            }

                            //Termina privacidad
                            if (cont == 1) {
                                nets = "<p>" + sSocialNet + "(" + sPrivacy + ")" + "</p>";
                            } else {//Nunca entraría aquí con lo que se determinó, de solo mostrar la primera red social y un "ver mas", en caso de haber mas, se deja este códigp por si cambia esta regla en lo futuro.
                                nets += "<p>" + sSocialNet + "(" + sPrivacy + ")" + "</p>";
                            }
                        }
                    }
                    out.println(nets);

                    if (cont > 1) {
                        //SWBResourceURL urlshowmoreNets = paramRequest.getRenderUrl().setMode(Mode_ShowMoreNets).setCallMethod(SWBResourceURL.Call_DIRECT); 
                        String id = resource.getEncodedURI().replace('%', '_').replace(':', '_').replace('/', '_');
                        //out.println("<p><a href=\"#\" onclick=\"showDialog('" + urlshowmoreNets.setParameter("postUri", resource.getURI()) + "','" + paramRequest.getLocaleString("associatedSocialNets") + "'); return false;\">"+paramRequest.getLocaleString("watchMore")+"</a></p>");
                        out.println("<p><a title=\"" + paramRequest.getLocaleString("watchMore") + "\" onclick=\"viewMoreNets('" + id + "_watchMoreNets')\" href=\"#\">" + paramRequest.getLocaleString("watchMore") + "</p></a>");
                    }

                    out.println("</td>");



                    //PostIn Source 
                    out.println("<td width='10%'>");
                    if (resource.getPostInSource() != null) {
                        WebPage wpShowPostIn = wsite.getWebPage("ShowPostIn");
                        Resource resrPostIn = wsite.getResource("150");

                        SWBParamRequestImp paramreqPostIn = new SWBParamRequestImp(request, resrPostIn, wpShowPostIn, user);
                        SWBResourceURL urlpreviewPostIn = paramreqPostIn.getRenderUrl().setCallMethod(SWBParamRequestImp.Call_DIRECT);
                        urlpreviewPostIn.setParameter("wsite", wsite.getId());
                        urlpreviewPostIn.setParameter("postIn", resource.getPostInSource().getURI());

                        String idPreSource = resource.getEncodedURI().replace('%', '_').replace(':', '_').replace('/', '_');
                        String imgviewSource = SWBPortal.getContextPath() + "/swbadmin/css/images/ico-origen.png";
                        out.println("<a title=\"" + paramRequest.getLocaleString("properties") + "\" onclick=\"view('" + urlpreviewPostIn + "','" + idPreSource + "')\" href=\"#\"><img src=\"" + imgviewSource + "\" alt=\"" + paramRequest.getLocaleString("source") + "\"></a>");
                    } else {
                        out.println("---");
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

                out.println("function viewMoreNets(id)");
                out.println("{");
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
            } else {
                out.println("<div class=\"swbform\">");
                out.println("<p>" + paramRequest.getLocaleString("messageNoContents") + "</p>");
                out.println("</div>");

            }

        } else {
            out.println("<div class=\"swbform\">");
            out.println("<p>" + paramRequest.getLocaleString("messageNoSites") + "</p>");
            out.println("</div>");
        }
        out.println("<div id='previewcontent'>");
        out.println("</div>");


        if (request.getParameter("previewSource") != null && request.getParameter("previewSource").equals("true")) {
            if (request.getParameter("sval") != null) {
                try {
                    doShowSource(request, response, paramRequest);
                } catch (Exception e) {
                    out.println("Preview not available...");
                }
            } else {
                out.println("Preview not available...");
            }
        }



        out.close();
    }

    /*
     * Show the source message of One PostOut that comes as a parameter "postUri"
     */
    public void doShowSource(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPostIn.jsp";
        if (request != null) {
            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if (dis != null) {
                try {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    request.setAttribute("postIn", semObject);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    /*
     * Muestra todas las redes sociales a las que se envío el mensaje de salida
     */
    private void doShowMoreNets(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showMoreNets.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postOut", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
}
