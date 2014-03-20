/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.social.Stream;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.UserGroupRef;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Kloutable;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.SocialUserExtAttributes;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.Youtube;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 * 
 * Clase que muestra todos los mensajes nuevos que tenga un usurio admistrador (que se encuentre en sesión en ese momento)
 * en las diferentes marcas/temas.
 * Los mensajes que se mustran son aquellos que concuerdan con los grupos de usuario que tenga asignados el 
 * dicho usuario y que dichos grupos estes asignados a los temas (Pestaña Grupos).
 */
public class AllNewPostInToUser extends GenericResource {

    /**
     * The log.
     */
    private Logger log = SWBUtils.getLogger(SocialTopicInBox.class);
    /**
     * The webpath.
     */
    String webpath = SWBPlatform.getContextPath();
    /**
     * The distributor.
     */
    String distributor = SWBPlatform.getEnv("wb/distributor");
    /**
     * The Mode_ action.
     */
    //String Mode_Action = "paction";
    String Mode_PFlowMsg = "doPflowMsg";
    String Mode_PreView = "preview";
    private static final int RECPERPAGE = 20; //Number of records by Page, could be dynamic later
    private static final int PAGES2VIEW = 15; //Number of pages 2 display in pagination.

    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public AllNewPostInToUser() {
    }
    public static final String Mode_RECLASSBYTOPIC = "reclassByTopic";
    public static final String Mode_RESPONSE = "response";
    public static final String Mode_PRINCIPAL = "afterChooseSite";
    public static final String Mode_ShowUsrHistory = "showUsrHistory";
    public static final String Mode_PREVIEW = "preview";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_RECLASSBYTOPIC.equals(mode)) {
            doReClassifyByTopic(request, response, paramRequest);
        } else if (Mode_RESPONSE.equals(mode)) {
            doResponse(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        } else if (Mode_PRINCIPAL.equals(mode)) {
            doEdit(request, response, paramRequest);
        } else if (Mode_ShowUsrHistory.equals(mode)) {
            doShowUserHistory(request, response, paramRequest);
        } else if (Mode_PREVIEW.equals(mode)) {
            doPreview(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * User view of the resource, this call to a doEdit() mode.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView(SWBAWebPageContents...)");
        String jspPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/socialSites.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspPath);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * User edit view of the resource, this show a list of contents related to a
     * webpage, user can add, remove, activate, deactivate contents.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String lang = paramRequest.getUser().getLanguage();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        User user = paramRequest.getUser();

        String socialSiteUri = request.getParameter("socialSite");
        if (socialSiteUri == null) {
            return;
        }
        
        SemanticObject semObj = SemanticObject.getSemanticObject(socialSiteUri);
        if (semObj == null) {
            return;
        }
        WebSite wsite = (WebSite) semObj.createGenericInstance();
        
        PrintWriter out = response.getWriter();
        
        out.println("<style type=\"text/css\">");
        out.println(".spanFormat");
        out.println("{");
        out.println("  text-align: right;");
        out.println("  display: table-cell;");
        out.println("  min-width: 10px;");
        out.println("  padding-right: 10px;");
        out.println("}");
        out.println("</style>");
        

        if (request.getParameter("statusMsg") != null) {
            out.println("<script type=\"javascript\">");
            if(request.getParameter("dialog")!=null && request.getParameter("dialog").equals("close"))
            {
                out.println(" hideDialog(); ");
            }
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");

            /*
            out.println(" delay(100000); ");
            
            out.println("function delay(milisegundos)");
            out.println("{");
            out.println("   for(i=0;i<=milisegundos;i++)");
            out.println("   {");
            out.println("       setTimeout('return 0',1);");
            out.println("   }");
            out.println("}");
            */
            
            out.println("</script>");
        }

     
        SWBResourceURL urls = paramRequest.getRenderUrl();
        urls.setParameter("act", "");
        urls.setParameter("suri", wsite.getId());

        out.println("<div class=\"swbform\">");
        
        out.println("<fieldset>");
        out.println("<table class=\"tabla1\" >");
        out.println("<thead>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("action"));
        out.println("</th>");


        out.println("<th>");
        out.println(paramRequest.getLocaleString("post"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("postType"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("network"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("topic"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("created"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("sentiment"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("intensity"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("emoticon"));
        out.println("</th>");


        out.println("<th>");
        out.println(paramRequest.getLocaleString("replies"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("user"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("followers"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("friends"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("klout"));
        out.println("</th>");

        out.println("<th>");
        out.println(paramRequest.getLocaleString("place"));
        out.println("</th>");
        
        out.println("<th>");
        out.println(paramRequest.getLocaleString("prioritary"));
        out.println("</th>");


        out.println("</thead>");
        out.println("<tbody>");

        Iterator<PostIn> itposts = null;
        Iterator<UserGroupRef> itUserGroupRef = UserGroupRef.ClassMgr.listUserGroupRefs(wsite);
        while (itUserGroupRef.hasNext()) {
            UserGroupRef userGroupRef = itUserGroupRef.next();
            if (userGroupRef.getUserGroup().hasUser(user)) {
                Iterator<SocialTopic> itSocialTopics = SocialTopic.ClassMgr.listSocialTopicByUserGroupRef(userGroupRef);
                while (itSocialTopics.hasNext()) {
                    SocialTopic socialTopic = itSocialTopics.next();
                    //Extraigo cuales son los mensajes de un SocialTopic
                    itposts = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic);
                }
            }
        }

        int p = 0, x = 0, l = 0, ps = 0;

        if (itposts != null && itposts.hasNext()) {

            Set<PostIn> setso = sortByCreatedSet(itposts, false);
            itposts = null;
            
            long nRec = setso.size();
            

            //int recPerPage = 20;//if(resBase.getItemsbyPage()>0) recPerPage=resBase.getItemsbyPage();            
            int nPage;
            try {
                nPage = Integer.parseInt(request.getParameter("page"));
            } catch (Exception ignored) {
                nPage = 1;
            }
            boolean paginate = false;
            
            
             //Manejo de permisos
            SocialUserExtAttributes socialUserExtAttr=SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
            boolean userCanRemoveMsg=false;
            boolean userCanRetopicMsg=false;
            boolean userCanRevalueMsg=false;
            boolean userCanRespondMsg=false;
            if(socialUserExtAttr!=null)
            {
                userCanRemoveMsg=socialUserExtAttr.isUserCanRemoveMsg();
                userCanRetopicMsg=socialUserExtAttr.isUserCanReTopicMsg();
                userCanRevalueMsg=socialUserExtAttr.isUserCanReValueMsg();
                userCanRespondMsg=socialUserExtAttr.isUserCanRespondMsg();
            }
            UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
            boolean userCandoEveryThing=user.hasUserGroup(userSuperAdminGrp);
            
            
            itposts = setso.iterator();
            while (itposts.hasNext()) {
                PostIn postIn = itposts.next();
                Stream stream=postIn.getPostInStream();
                int streamKloutValue=stream.getStream_KloutValue();
                
                /*
                nRec++;
                if ((nRec > (nPage - 1) * recPerPage) && (nRec <= (nPage) * recPerPage)) {
                paginate = true;
                * */

                String sClass="";
                if(postIn.isIsPrioritary()) sClass="class=\"msj-cont msj-prior\"";
                out.println("<tr id=\"" + postIn.getURI() + "/stIn\" "+sClass+">");

                //Show Actions
                out.println("<td class=\"accion\">");

                //Remove
                if(userCanRemoveMsg || userCandoEveryThing)
                {
                    SWBResourceURL urlr = paramRequest.getActionUrl();
                    urlr.setParameter("wsite", wsite.getURI());
                    urlr.setParameter("postUri", postIn.getURI());
                    urlr.setParameter("page", "" + p);
                    urlr.setAction(SWBResourceURL.Action_REMOVE);

                    out.println("<a href=\"#\" class=\"eliminar\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " "
                            + SWBUtils.TEXT.scape4Script(postIn.getMsg_Text()) + "?'))" + "{ submitUrl('" + urlr + "',this); } else { return false;}\">"
                            + "</a>");
                }
                
                SWBResourceURL urlPrev = paramRequest.getRenderUrl().setMode(Mode_PREVIEW).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("previewdocument") + "\" class=\"ver\" onclick=\"showDialog('" + urlPrev + "','" + paramRequest.getLocaleString("previewdocument")
                + "'); return false;\"></a>");

                //ReClasifyByTpic
                if(userCanRevalueMsg || userCandoEveryThing)
                {
                    SWBResourceURL urlreClasifybyTopic = paramRequest.getRenderUrl().setMode(Mode_RECLASSBYTOPIC).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI()).setParameter("fromStream", "/stIn");
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reclasifyByTopic") + "\" class=\"retema\" onclick=\"showDialog('" + urlreClasifybyTopic + "','"
                    + paramRequest.getLocaleString("reclasifyByTopic") + "'); return false;\"></a>");
                }
                
                //Respond
                if(userCanRespondMsg || userCandoEveryThing)
                {
                    if (postIn.getSocialTopic() != null) {
                        SWBResourceURL urlresponse = paramRequest.getRenderUrl().setMode(Mode_RESPONSE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                        out.println("<a href=\"#\" class=\"answ\" title=\"" + paramRequest.getLocaleString("respond") + "\" onclick=\"showDialog('" + urlresponse + "','" + paramRequest.getLocaleString("respond")
                        + "'); return false;\"></a>");
                    }
                }

                out.println("</td>");

                //Show Message
                out.println("<td class=\"mensaje\">");
                if (postIn.getMsg_Text() != null) {
                    if (postIn.getMsg_Text().length() > 200) {
                        String msg2Show = postIn.getMsg_Text().substring(0, 200);
                        msg2Show = SWBSocialResUtil.Util.createHttpLink(msg2Show);
                        out.println(msg2Show);                
                    } else {
                        out.println(postIn.getMsg_Text());
                    }
                } /*else if (postIn.getDescription() != null) {
                    if (postIn.getDescription().length() > 200) {
                        out.println(postIn.getDescription().substring(0, 200));
                    } else {
                        out.println(postIn.getDescription());
                    }
                } */else if (postIn.getTags() != null) {
                    if (postIn.getTags().length() > 200) {
                            out.println(postIn.getTags().substring(0, 200));
                    } else {
                        out.println(postIn.getTags());
                    }
                } else {
                    out.println("---");
                }
                out.println("</td>");


                //Show PostType
                out.println("<td>");
                //out.println(postIn instanceof MessageIn ? paramRequest.getLocaleString("message") : postIn instanceof PhotoIn ? paramRequest.getLocaleString("photo") : postIn instanceof VideoIn ? paramRequest.getLocaleString("video") : "---");
                out.println(postIn instanceof MessageIn ? "<img title=\"Texto\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-txt.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("message") + "  \">" : postIn instanceof PhotoIn ? "<img title=\"Imagen\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-img.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("photo") + "  \">" : postIn instanceof VideoIn ? "<img title=\"Video\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/tipo-vid.jpg\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("video") + "  \">" : "---");
                out.println("</td>");

               
                 //SocialNetwork
                out.println("<td>");
                out.println(postIn.getPostInSocialNetwork().getDisplayTitle(lang));
                if(postIn.getPostInSocialNetwork() instanceof Youtube){
                    out.println("</br><img class=\"swbIconYouTube\" src=\"/swbadmin/js/dojo/dojo/resources/blank.gif\"/>");
                }else{
                    out.println("</br><img class=\"swbIcon" + postIn.getPostInSocialNetwork().getClass().getSimpleName() + "\" src=\"/swbadmin/js/dojo/dojo/resources/blank.gif\"/>");
                }
                out.println("</td>");
                
                
                //SocialTopic
                out.println("<td>");
                if (postIn.getSocialTopic() != null) {
                    out.println(postIn.getSocialTopic().getDisplayTitle(lang));
                } else {
                    out.println("---");
                }
                out.println("</td>");


                //Show Creation Time
                out.println("<td>");
                //System.out.println("FechaTimeAgo:"+postIn.getPi_created());
                if(postIn.getPi_createdInSocialNet()!=null)
                {
                    out.println(SWBUtils.TEXT.getTimeAgo(postIn.getPi_createdInSocialNet(), lang));
                }else{
                    out.println(SWBUtils.TEXT.getTimeAgo(new Date(), lang));
                }
                out.println("</td>");

                //Sentiment
                out.println("<td align=\"center\">");
                if (postIn.getPostSentimentalType() == 0) {
                    out.println("---");
                } else if (postIn.getPostSentimentalType() == 1) {
                    out.println("<img title=\"Positivo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/pos.png" + "\">");
                } else if (postIn.getPostSentimentalType() == 2) {
                    out.println("<img title=\"Negativo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/neg.png" + "\">");
                }
                out.println("</td>");

                //Intensity
                out.println("<td>");
                out.println(postIn.getPostIntesityType() == 0 ? "<img title=\"Baja\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ibaja.png\" border=\"0\" alt=\"  " + paramRequest.getLocaleString("low") + "  \">" : postIn.getPostIntesityType() == 1 ? "<img title=\"Media\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/imedia.png\" border=\"0\" title=\"  " + paramRequest.getLocaleString("medium") + "  \">" : postIn.getPostIntesityType() == 2 ? "<img alt=\"Alta\" src=\" " + SWBPlatform.getContextPath() + " /swbadmin/css/images/ialta.png\" border=\"0\" alt=\" " + paramRequest.getLocaleString("high") + "  \">" : "---");
                out.println("</td>");

                //Emoticon
                out.println("<td align=\"center\">");
                if (postIn.getPostSentimentalEmoticonType() == 1) {
                    out.println("<img title=\"Positivo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emopos.png" + "\"/>");
                } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                    out.println("<img title=\"Negativo\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emoneg.png" + "\"/>");
                } else if (postIn.getPostSentimentalEmoticonType() == 0) {
                    out.println("<img title=\"Neutro\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emoneu.png" + "\"/>");
                }
                out.println("</td>");


                //Replicas
                out.println("<td align=\"center\">");
                out.println(postIn.getPostShared());
                out.println("</td>");


                //Nunca debería un PostIn no tener un usuario, porque obvio las redes sociales simpre tienen un usuario que escribe los mensajes
                //User
                out.println("<td>");
                SWBResourceURL urlshowUsrHistory = paramRequest.getRenderUrl().setMode(Mode_ShowUsrHistory).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("suri", stream.getURI());
                out.println(postIn.getPostInSocialNetworkUser() != null ? "<a href=\"#\" onclick=\"showDialog('" + urlshowUsrHistory.setParameter("swbSocialUser", postIn.getPostInSocialNetworkUser().getURI()) + "','" + paramRequest.getLocaleString("userHistory") + "'); return false;\">" + postIn.getPostInSocialNetworkUser().getSnu_name() + "</a>" : paramRequest.getLocaleString("withoutUser"));        
                out.println("</td>");

                //Followers
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFollowers() : paramRequest.getLocaleString("withoutUser"));
                out.println("</td>");

                //Friends
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser() != null ? postIn.getPostInSocialNetworkUser().getFriends() : paramRequest.getLocaleString("withoutUser"));
                out.println("</td>");


                //Retrieving user Klout data
                //Klout
                out.println("<td align=\"center\">");
                int userKloutScore=0;
                SocialNetworkUser socialNetUser=postIn.getPostInSocialNetworkUser();
                if(socialNetUser!=null)
                {
                    //System.out.println("checkKlout--J:"+checkKlout);
                    //Looking for user klout
                    if(postIn.getPostInSocialNetwork().getSemanticObject().getSemanticClass().isSubClass(Kloutable.social_Kloutable) && socialNetUser.getSnu_klout()<streamKloutValue)
                    {
                        //System.out.println("checkKlout--J1:"+checkKlout+",socialNetUser:"+socialNetUser+",id:"+socialNetUser.getId()+",socialNetUser:"+socialNetUser.getSnu_id());
                        HashMap userKloutDat=SWBSocialUtil.Classifier.classifybyKlout(postIn.getPostInSocialNetwork(), stream, socialNetUser, socialNetUser.getSnu_id(), true);
                        userKloutScore=((Integer)userKloutDat.get("userKloutScore")).intValue();
                        socialNetUser.setSnu_klout(userKloutScore);
                        if(userKloutScore<streamKloutValue) //El klout del postIn es menor al del stream, talvez cuando entro el postIn el stream tenía definido un valor de filtro de klout menor al de este momento.
                        {
                            out.println("<font color=\"#FF6600\">"+userKloutScore+"</font>");
                        }else{
                            out.println(userKloutScore);
                        }
                        //System.out.println("checkKlout--J2:"+userKloutScore+", NO VOLVERA A PONER KLOUT PARA ESTE USER:"+socialNetUser);
                    }else{
                        out.println(socialNetUser.getSnu_klout());
                    }
                }
                else{
                    out.println(paramRequest.getLocaleString("withoutUser"));
                }

                //Place
                out.println("<td>");
                out.println(postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());        
                out.println("</td>");

                //Priority
                out.println("<td align=\"center\">");
                out.println(postIn.isIsPrioritary() ? "SI" : "NO");
                out.println("</td>");


                out.println("</tr>");
                
            }

       
        out.println("</tbody>");
        out.println("</table>");
        out.println("</fieldset>");

        if (nRec > 0) {
            int totalPages=1;
            if(nRec>RECPERPAGE)
            {
                totalPages=Double.valueOf(nRec/20).intValue();
                if((nRec % RECPERPAGE)>0){
                    totalPages=Double.valueOf(nRec/20).intValue()+1;
                }
            }
            //System.out.println("StreamInBox/totalPages:"+totalPages);
            
            out.println("<div id=\"page\">");
            out.println("<div id=\"pagSumary\">"+paramRequest.getLocaleString("page")+":"+nPage+" "+paramRequest.getLocaleString("of") +" "+totalPages+"</div>");
            
            SWBResourceURL pageURL = paramRequest.getRenderUrl();
            //pageURL.setParameter("page", "" + (countPage));
            /*
            pageURL.setParameter("suri", id);
            pageURL.setParameter("search", (searchWord.trim().length() > 0 ? searchWord : ""));
            pageURL.setParameter("swbSocialUser", swbSocialUser);
            if (request.getParameter("orderBy") != null) {
                pageURL.setParameter("orderBy", request.getParameter("orderBy"));
            }*/
            //out.println(SWBSocialUtil.Util.getContentByPage(totalPages, nPage, PAGES2VIEW, paramRequest.getLocaleString("pageBefore"), paramRequest.getLocaleString("pageNext"), pageURL));
            out.println(SWBSocialResUtil.Util.getContentByPage(totalPages, nPage, PAGES2VIEW, pageURL));
            out.println("</div>");
        }
        }

      out.println("</div>");
    }

    private void doReClassifyByTopic(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/classifybyTopic.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postUri", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    private void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/postInResponse.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                request.setAttribute("postUri", semObject);
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/typeOfContent.jsp");
        request.setAttribute("contentType", request.getParameter("valor"));
        request.setAttribute("wsite", request.getParameter("wsite"));
        request.setAttribute("objUri", request.getParameter("objUri"));
        request.setAttribute("paramRequest", paramRequest);

        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a typeOfContent.jsp " + ex.getMessage());
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final Resource base = getResourceBase();
        String action = response.getAction();
        if (action.equals("changeSocialTopic")) {
            if (request.getParameter("postUri") != null && request.getParameter("newSocialTopic") != null && request.getParameter("wsite")!=null) {
                SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("postUri"));
                Post post = (Post) semObj.createGenericInstance();
                if (request.getParameter("newSocialTopic").equals("none")) {
                    post.setSocialTopic(null);
                } else {
                    SemanticObject semObjSocialTopic = SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if (semObjSocialTopic != null) {
                        SocialTopic socialTopic = (SocialTopic) semObjSocialTopic.createGenericInstance();
                        post.setSocialTopic(socialTopic);
                    }
                }
                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("statusMsg", response.getLocaleString("msgTopicChanged"));
                response.setRenderParameter("socialSite", request.getParameter("wsite"));
            }
        } else if (action.equals("postMessage")) {
            if (request.getParameter("objUri") != null && request.getParameter("wsite")!=null) {
                PostIn postIn = (PostIn) SemanticObject.getSemanticObject(request.getParameter("objUri")).createGenericInstance();
                SocialNetwork socialNet = (SocialNetwork) SemanticObject.getSemanticObject(request.getParameter("socialNetUri")).createGenericInstance();
                ArrayList aSocialNets = new ArrayList();
                aSocialNets.add(socialNet);

                WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));

                String socialFlow = request.getParameter("socialFlow");
                SocialPFlow socialPFlow = null;
                if (socialFlow != null && socialFlow.trim().length() > 0) {
                    socialPFlow = (SocialPFlow) SemanticObject.createSemanticObject(socialFlow).createGenericInstance();
                }

                SWBSocialUtil.PostOutUtil.sendNewPost(postIn, postIn.getSocialTopic(), socialPFlow, aSocialNets, wsite, request.getParameter("toPost"), request, response);

                response.setMode(SWBActionResponse.Mode_EDIT);
                response.setRenderParameter("dialog", "close");
                response.setRenderParameter("statusMsg", response.getLocaleString("msgResponseCreated"));
                response.setRenderParameter("socialSite", wsite.getURI());
            }
        } else if (SWBResourceURL.Action_EDIT.equals(action)) {
            WebSite wsite = base.getWebSite();
            try {
                String[] phrases = request.getParameter("fw").split(";");
                int nv = Integer.parseInt(request.getParameter("nv"));
                int dpth = Integer.parseInt(request.getParameter("dpth"));
                SentimentalLearningPhrase slp;
                for (String phrase : phrases) {
                    phrase = phrase.toLowerCase().trim();
                    slp = SentimentalLearningPhrase.getSentimentalLearningPhrasebyPhrase(phrase, wsite);
                    if (slp == null) {
                        phrase = SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
                        phrase = SWBSocialUtil.Classifier.getRootWord(phrase);
                        phrase = SWBSocialUtil.Classifier.phonematize(phrase);
                        slp = SentimentalLearningPhrase.ClassMgr.createSentimentalLearningPhrase(wsite);
                        slp.setPhrase(phrase);
                        slp.setSentimentType(nv);
                        slp.setIntensityType(dpth);
                    } else {
                        slp.setSentimentType(nv);
                        slp.setIntensityType(dpth);
                    }
                }
                response.setRenderParameter("alertmsg", "Revaluación correcta");
            } catch (Exception e) {
                response.setRenderParameter("alertmsg", "Inténtalo de nuevo");
                log.error(e);
            }
        } else if (action.equals(SWBActionResponse.Action_REMOVE)) {
            if (request.getParameter("wsite") != null && request.getParameter("postUri") != null) {
                SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("postUri"));
                if (semObj != null) {
                    PostIn postIn = (PostIn) semObj.createGenericInstance();
                    postIn.remove();

                    response.setMode(SWBActionResponse.Mode_EDIT);
                    response.setRenderParameter("socialSite", request.getParameter("wsite"));
                    response.setRenderParameter("statusMsg", response.getLocaleString("postDeleted"));
                }
            }
        }
    }

    /**
     * Sort by created set.
     * 
     * @param it the it
     * @param ascendente the ascendente
     * @return the sets the
     */
    public static Set sortByCreatedSet(Iterator it, boolean ascendente) {
        TreeSet set = null;
        if(it==null)
        {
            return null;
        }
        if (ascendente) {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    Date d1 = null;
                    Date d2 = null;
                    if(o1 instanceof PostIn)
                    {
                        d1 = ((PostIn)o1).getPi_created();
                        d2 = ((PostIn)o2).getPi_created();
                    }
                    
                    if(d1==null && d2!=null)
                    {
                        return -1;
                    }
                    if(d1!=null && d2==null)
                    {
                        return 1;
                    }
                    if(d1==null && d2==null)
                    {
                        return -1;
                    }
                    else
                    {                    
                        int ret = d1.getTime()>d2.getTime()? 1 : -1;
                        return ret;
                    }
                }
            });
        } else {
            set = new TreeSet(new Comparator() {
                public int compare(Object o1, Object o2) {
                    Date d1 = null;
                    Date d2 = null;
                    if(o1 instanceof PostIn)
                    {
                        d1 = ((PostIn)o1).getPi_created();
                        d2 = ((PostIn)o2).getPi_created();
                    }
                    if(d1==null && d2!=null)
                    {
                        return -1;
                    }
                    if(d1!=null && d2==null)
                    {
                        return 1;
                    }
                    if(d1==null && d2==null)
                    {
                        return -1;
                    }
                    else
                    {
                        int ret = d1.getTime()>d2.getTime()? -1 : 1;
                        return ret;
                    }
                    
                }
            });
        }

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set;
    }
    
    private void doShowUserHistory(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/userHistory.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        if (dis != null) {
            try {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("swbSocialUser"));
                request.setAttribute("swbSocialUser", semObject);
                request.setAttribute("suri", request.getParameter("suri"));
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    
    
    /**
     * Shows the preview of the content.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPreview(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String postUri = request.getParameter("postUri");
        try {
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/showPostIn.jsp";
            if (request != null) {
                RequestDispatcher dis = request.getRequestDispatcher(path);
                if (dis != null) {
                    try {
                        SemanticObject semObject = SemanticObject.createSemanticObject(postUri);
                        request.setAttribute("postIn", semObject);
                        request.setAttribute("paramRequest", paramRequest);
                        dis.include(request, response);
                    } catch (Exception e) {
                        log.error(e);
                        e.printStackTrace(System.out);
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error while getting content string ,id:" + postUri, e);
        }
    }
}
