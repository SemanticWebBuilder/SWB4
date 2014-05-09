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
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Message;
import org.semanticwb.social.Photo;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.PostOutNet;
import org.semanticwb.social.PostOutPrivacyRelation;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.Video;
import org.semanticwb.social.Youtube;
import org.semanticwb.social.util.SocialLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBDocumentsToAuthorize.
 *
 * @author Jorge.Jiménez
 * @author Francisco.Jiménez
 */
public class SocialDocumentsToAuhorize extends GenericResource {

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(SocialDocumentsToAuhorize.class);
    public static final String Mode_SOURCE = "source";
    public static final String Mode_AUTH_OR_REJ = "authOrReject";
    public static final String Mode_RELOAD = "reload";
    private static final String Mode_ShowMoreNets = "showMoreNets";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("Entrando al process Request:" + paramRequest.getMode());
        final String mode = paramRequest.getMode();
        if (Mode_SOURCE.equals(mode)) {
            doShowSource(request, response, paramRequest);
        } else if (Mode_ShowMoreNets.equals(mode)) {
            doShowMoreNets(request, response, paramRequest);
        } else if(Mode_AUTH_OR_REJ.equals(mode)){
            doAcceptOrReject(request, response, paramRequest);
        }else if(Mode_RELOAD.equals(mode)){
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("   hideDialog();");
            out.println("   var objid= '" + paramRequest.getWebPage().getURI() +"/tab';");
            out.println("   var tab = dijit.byId(objid);");
            out.println("   if(tab){");
            out.println("       tab.refresh()");
            out.println("   }");
            out.println("   showStatus('" + paramRequest.getLocaleString("updatedMessage") +  "');");
            out.println("</script>");
        }else{
            super.processRequest(request, response, paramRequest);
        }
    }


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("**************************processAction:");
        System.out.println(request.getParameter("site"));
        System.out.println(request.getParameter("res"));
        System.out.println(request.getParameter("wbaction"));
        System.out.println(request.getParameter("firstLoad"));
        System.out.println(request.getParameter("msg"));
        User user = response.getUser();
        response.setRenderParameter("site", request.getParameter("site"));
        response.setRenderParameter("firstLoad", request.getParameter("firstLoad"));
        response.setMode(Mode_RELOAD);
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
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String lang = paramRequest.getUser().getLanguage();
        int show = 1;
        String firstLoad = request.getParameter("firstLoad");
        System.out.println("Entrando al doView");
        System.out.println("First Load:" + request.getParameter("firstLoad"));
        System.out.println("show:" + request.getParameter("show"));
        System.out.println("site:" + request.getParameter("site"));
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
        if(firstLoad == null){
            System.out.println("This is the first time of doView");
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
        }else{
                System.out.println("THIS IS ANOTHER CALL (=" + firstLoad);
        }

        out.println("<div class=\"swbform\">");
        out.println("<div id=\"pub-detalle\">");
        out.println("</br>");
        out.println("<form type=\"dijit.form.Form\" id=\"frmseecontentsToAuthorize\" name=\"frmseecontentsToAuthorize\" action=\"" + paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT) + "\" method=\"post\">");
        //out.println("<fieldset>");
        out.println("<input type='hidden' name='firstLoad' value='false'></input>");
        out.println( paramRequest.getLocaleString("selectABrand") + ":");
        out.println("<select name='site' onchange=\"submitForm(\'frmseecontentsToAuthorize\')\">");
        sites = SWBContext.listWebSites();
        while (sites.hasNext()) {
            WebSite site = sites.next();
            if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR)) && site.isValid() && site instanceof SocialSite) {
                if (sitetoShow.getId().equals(site.getId())) {
                    out.println("<option selected=\"true\" value='" + site.getId() + "'>" + site.getTitle() + "</option>");
                } else {
                    out.println("<option value='" + site.getId() + "'>" + site.getTitle() + "</option>");
                }
            }
        }
        out.println("</select>");
        String selected = "";
        if (show == 1) {
            selected = "checked";
        }
        out.println("<input " + selected + " onClick=\"submitForm(\'frmseecontentsToAuthorize\')\" dojoType=\"dijit.form.RadioButton\" type='radio' id='show1' name='show' value='1'/>" + paramRequest.getLocaleString("all") + "");
        selected = "";
        if (show == 2) {
            selected = "checked";
        }
        out.println("<input " + selected + " onClick=\"submitForm(\'frmseecontentsToAuthorize\')\" dojoType=\"dijit.form.RadioButton\" type='radio' id='show2'  name='show' value='2'/>" + paramRequest.getLocaleString("mydocuments") + "");
        selected = "";
        if (show == 3) {
            selected = "checked";
        }

        out.println("<input " + selected + " onClick=\"submitForm(\'frmseecontentsToAuthorize\')\" dojoType=\"dijit.form.RadioButton\" type='radio' id='show3' name='show' value='3'/>" + paramRequest.getLocaleString("forauthorize") + "");
        //out.println("</fieldset>");
        out.println("</form>");          
        out.println("</div>");
        out.println("</div>");
        if (sitetoShow != null) {
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
                out.println("<form class=\"swbform\" method='post' action='#'>");
                out.println("<fieldset>");

                out.println("<table class=\"tabla1\">");
                out.println("<tr>");
                out.println("<th class=\"accion\"> ");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("action"), "UTF-8"));
                out.println("</th>");
                out.println("<th>");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("type"), "UTF-8"));
                out.println("</th>");
                out.println("<th>");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("title"), "UTF-8"));
                out.println("</th>");
                out.println("<th>");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("topic"), "UTF-8"));
                out.println("</th>");
                out.println("<th>");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("flow"), "UTF-8"));
                out.println("</th>");
                out.println("<th>");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("step"), "UTF-8"));
                out.println("</th>");
                out.println("<th>");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("socialNet"), "UTF-8"));
                out.println("</th>");
                out.println("<th>");
                out.println(SWBUtils.TEXT.encode(paramRequest.getLocaleString("source"), "UTF-8"));
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
                        out.println("<a class=\"ver\" title=\"" + paramRequest.getLocaleString("properties") + "\" onclick=\"showDialog('" + urlpreview + "','" + paramRequest.getLocaleString("postOutMsg") + "'); return false;\" href=\"#\"></a>");
                        if (SocialLoader.getPFlowManager().isReviewer(resource, user)) {
                            //out.println("<a title=\"" + paramRequest.getLocaleString("edit") + "\" href=\"#\" onclick=\"parent.selectTab('" + resource.getURI() + "','" + SWBPortal.getContextPath() + "/swbadmin/jsp/objectTab.jsp','" + "TEST" + "','bh_AdminPorltet');return false;\"><img  src=\"" + imgedit + "\"></a>");
                            out.println("<a class=\"editar\" title=\"" + paramRequest.getLocaleString("edit") + "\" href=\"#\" onclick=\"parent.selectTab('" + resource.getURI() + "','" + SWBPortal.getContextPath() + "/swbadmin/jsp/objectTab.jsp','" + "TEST" + "','bh_AdminPorltet');return false;\"></a>");
                            //out.println("<a title=\"" + paramRequest.getLocaleString("authorize") + "\" href=\"#\" onclick=\"showAuthorize('" + resource.getURI() + "')\"><img  src=\"" + imgauthorize + "\"></a>");
                            out.println("<a class=\"autorizar\" title=\"" + paramRequest.getLocaleString("authorize") + "\" href=\"#\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode(Mode_AUTH_OR_REJ).setParameter("site", sitetoShow.getId()).setParameter("resourceId", resource.getURI()).setParameter("wbaction", "a") + "','" + paramRequest.getLocaleString("authorize") + "'); return false;\"></a>");
                            //out.println("<a title=\"" + paramRequest.getLocaleString("reject") + "\" href=\"#\" onclick=\"showReject('" + resource.getURI() + "')\"><img  src=\"" + imgreject + "\"></a>");
                            out.println("<a class=\"rechazar\" title=\"" + paramRequest.getLocaleString("reject") + "\" href=\"#\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode(Mode_AUTH_OR_REJ).setParameter("site", sitetoShow.getId()).setParameter("resourceId", resource.getURI()).setParameter("wbaction", "r") + "','" + paramRequest.getLocaleString("reject") + "'); return false;\"></a>");
                            //out.println("<a class=\"eliminar\" title=\"" + paramRequest.getLocaleString("reject") + "\" href=\"#\" onclick=\"showReject('" + resource.getURI() + "')\"></a>");
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }

                    out.println("</td>");

                    out.println("<td width='10%'>");
                    out.println(resource instanceof Message ? "<img title=\"Texto\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-txt.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("message") + "  \">" : resource instanceof Photo ? "<img title=\"Imagen\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-img.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("photo") + "  \">" : resource instanceof Video ? "<img title=\"Video\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-vid.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("video") + "  \">" : "---");
                    if(resource.getSocialTopic()!=null)
                    {
                        boolean classifyBySentiment = resource.getSocialTopic().isCheckSentPostSentiment();
                        if (classifyBySentiment) {
                        out.println("(");    
                        //Sentiment
                        //out.println("<td align=\"center\">");
                        if (resource.getPostSentimentalType() == 0) {
                            out.println("---");
                        } else if (resource.getPostSentimentalType() == 1) {
                            out.println("<img alt=\"Positivo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/pos.png" + "\">");
                        } else if (resource.getPostSentimentalType() == 2) {
                            out.println("<img alt=\"Negativo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/neg.png" + "\">");
                        }
                        //out.println("</td>");

                        //Intensity
                        //out.println("<td>");
                        out.println(resource.getPostIntesityType() == 0 ? "<img alt=\"Baja\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ibaja.png\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("low") + "  \">" : resource.getPostIntesityType() == 1 ? "<img alt=\"Media\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/imedia.png\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("medium") + "  \">" : resource.getPostIntesityType() == 2 ? "<img alt=\"Alta\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ialta.png\" border=\"0\" alt=\" " + paramRequest.getLocaleString("high") + "  \">" : "---");
                        //out.println("</td>");
                        out.println(")");
                        }   
                    }
                    
                    out.println("</td>");


                    out.println("<td width='40%'>");
                    out.println(resource.getMsg_Text() != null ? SWBUtils.TEXT.encode(resource.getMsg_Text(),"UTF-8") : "");
                    out.println("</td>");
                    out.println("<td width='10%'>");
                    out.println(resource.getSocialTopic().getTitle() != null ? SWBUtils.TEXT.encode(resource.getSocialTopic().getTitle(),"UTF-8") : "");
                    out.println("</td>");
                    out.println("<td width='10%'>");
                    out.println(SWBUtils.TEXT.encode(resource.getPflowInstance().getPflow().getDisplayTitle(lang),"UTF-8"));
                    out.println("</td>");
                    out.println("<td width='10%'>");
                    out.println(SWBUtils.TEXT.encode(resource.getPflowInstance().getStep(),"UTF-8"));
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
                        String netIcon = "";
                        //System.out.println("socialNet-2:"+sSocialNet);
                        if(socialNet instanceof Youtube){
                            netIcon = "<img class=\"swbIconYouTube\" src=\"/swbadmin/js/dojo/dojo/resources/blank.gif\"/>";
                        }else{
                            netIcon = "<img class=\"swbIcon" + socialNet.getClass().getSimpleName() + "\" src=\"/swbadmin/js/dojo/dojo/resources/blank.gif\"/>";
                        }
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
                                nets = "<p>" + netIcon +  sSocialNet + "(" + sPrivacy + ")" + "</p>";
                            } else {//Nunca entraría aquí con lo que se determinó, de solo mostrar la primera red social y un "ver mas", en caso de haber mas, se deja este códigp por si cambia esta regla en lo futuro.
                                nets += "<p>" + sSocialNet + "(" + sPrivacy + ")" + "</p>";
                            }
                        }
                    }
                    out.println(nets);

                    if (cont > 1) {
                        out.println("<p><a title=\"" + SWBUtils.TEXT.encode(paramRequest.getLocaleString("watchMore"),"UTF-8") + "\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode(Mode_ShowMoreNets).setParameter("postUri", resource.getURI()) + "','" + SWBUtils.TEXT.encode(paramRequest.getLocaleString("watchMore"), "UTF-8") + "'); return false;\" href=\"#\">" + SWBUtils.TEXT.encode(paramRequest.getLocaleString("watchMore"),"UTF-8") + "</p></a>");
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

                        String imgviewSource = SWBPortal.getContextPath() + "/swbadmin/css/images/ico-origen.png";
                        //out.println("<a title=\"" + paramRequest.getLocaleString("properties") + "\" onclick=\"view('" + urlpreviewPostIn + "','" + idPreSource + "')\" href=\"#\"><img src=\"" + imgviewSource + "\" alt=\"" + paramRequest.getLocaleString("source") + "\">ver 2</a>");
                        ///-Call show Dialog and don't use iframe
                        out.println("<a title=\"" + paramRequest.getLocaleString("properties") + "\" onclick=\"parent.showDialog('" + urlpreviewPostIn + "','" + paramRequest.getLocaleString("postInMsg") + "'); return false;\" href=\"#\"><img src=\"" + imgviewSource + "\" alt=\"" + paramRequest.getLocaleString("source") + "\"></a>");
                        //onclick=\"showDialog('" + clasifybyTopic + "','" + paramRequest.getLocaleString("reclassify") + " post'); return false;\"
                    } else {
                        out.println("---");
                    }
                    out.println("</td>");

                    out.println("</tr>");

                }

                out.println("</table>");
                out.println("<fieldset>");
                out.println("</form>");
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

    public void doAcceptOrReject(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String resourceId = request.getParameter("resourceId");
        String site = request.getParameter("site");
        String wbaction = request.getParameter("wbaction");
        WebSite sitetoShow = null;
        
        if (site != null) {
            sitetoShow = SWBContext.getWebSite(site);
        }
        
        String btnMessage = "";//wbaction.equals("a") ? paramRequest.getLocaleString("authorize") :
        if(wbaction.equals("a")){
            btnMessage = paramRequest.getLocaleString("authorize");
        }else if(wbaction.equals("r")){
            btnMessage = paramRequest.getLocaleString("reject");
        }

        SWBResourceURL actionUrl = paramRequest.getActionUrl().setParameter("site", sitetoShow.getId()).setParameter("wbaction", wbaction).setParameter("res", resourceId);
        out.println("<script type=\"text/javascript\">");
        out.println("function getSelectedSite()");
        out.println("{");
        out.println("   document.swbfrmResourcesAuthorize.site.value=document.frmseecontentsToAuthorize.site.value;");
        out.println("}");
        out.println("</script>");
        out.println("<form type=\"dijit.form.Form\" name=\"swbfrmResourcesAuthorize\" id=\"swbfrmResourcesAuthorize\" class=\"swbform\" method=\"post\" action=\"" + actionUrl + "\">");
        out.println("<input type='hidden' name='site' value=''></input>");
        out.println("<input type='hidden' name='firstLoad' value='false'></input>");
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
        out.println("<button onClick=\"getSelectedSite(); submitForm(\'swbfrmResourcesAuthorize\')\" dojoType=\"dijit.form.Button\" type=\"button\">" + btnMessage + "</button>");
        out.println("&nbsp;&nbsp;&nbsp;&nbsp;<button onClick=\"hideDialog();\" dojoType=\"dijit.form.Button\" type=\"button\">" + paramRequest.getLocaleString("cancel") + "</button>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");
        //out.println("</div>");

    }
}