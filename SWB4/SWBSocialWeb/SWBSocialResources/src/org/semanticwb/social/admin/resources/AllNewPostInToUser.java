/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroupRef;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SentimentalLearningPhrase;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.VideoIn;
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

    /**
     * Creates a new instance of SWBAWebPageContents.
     */
    public AllNewPostInToUser() {
    }
    public static final String Mode_RECLASSBYTOPIC = "reclassByTopic";
    public static final String Mode_RESPONSE = "response";
    public static final String Mode_PRINCIPAL = "afterChooseSite";

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
        out.println("<table width=\"98%\" >");
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

            Set<PostIn> setso = SWBComparator.sortByCreatedSet(itposts, false);
            itposts = null;

            int recPerPage = 20;//if(resBase.getItemsbyPage()>0) recPerPage=resBase.getItemsbyPage();            
            int nRec = 0;
            int nPage;
            try {
                nPage = Integer.parseInt(request.getParameter("page"));
            } catch (Exception ignored) {
                nPage = 1;
            }
            boolean paginate = false;


            itposts = setso.iterator();

            
            while (itposts.hasNext()) {
                PostIn postIn = itposts.next();
                
                nRec++;
                if ((nRec > (nPage - 1) * recPerPage) && (nRec <= (nPage) * recPerPage)) {
                paginate = true;

                
                out.println("<tr>");

                //Show Actions
                out.println("<td>");

                //Remove
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("wsite", wsite.getURI());
                urlr.setParameter("postUri", postIn.getURI());
                urlr.setParameter("page", "" + p);
                urlr.setAction(SWBResourceURL.Action_REMOVE);

                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " "
                        + SWBUtils.TEXT.scape4Script(postIn.getMsg_Text()) + "?'))" + "{ submitUrl('" + urlr + "',this); } else { return false;}\">"
                        + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("remove") + "\"></a>");


                //ReClasifyByTpic
                SWBResourceURL urlreClasifybyTopic = paramRequest.getRenderUrl().setMode(Mode_RECLASSBYTOPIC).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("reclasifyByTopic") + "\" onclick=\"showDialog('" + urlreClasifybyTopic + "','"
                        + paramRequest.getLocaleString("reclasifyByTopic") + "'); return false;\">ReT</a>");


                //Respond
                if (postIn.getSocialTopic() != null) {
                    SWBResourceURL urlresponse = paramRequest.getRenderUrl().setMode(Mode_RESPONSE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("postUri", postIn.getURI());
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("respond") + "\" onclick=\"showDialog('" + urlresponse + "','" + paramRequest.getLocaleString("respond")
                            + "'); return false;\">R</a>");
                }

                out.println("</td>");

                //Show Message
                out.println("<td>");
                out.println(postIn.getMsg_Text());
                out.println("</td>");


                //Show PostType
                out.println("<td>");
                out.println(postIn instanceof MessageIn ? paramRequest.getLocaleString("message") : postIn instanceof PhotoIn ? paramRequest.getLocaleString("photo") : postIn instanceof VideoIn ? paramRequest.getLocaleString("video") : "---");
                out.println("</td>");

                //SocialNetwork
                out.println("<td>");
                out.println(postIn.getPostInSocialNetwork().getDisplayTitle(lang));
                out.println("</td>");

                //SocialTopic
                out.println("<td>");
                if (postIn.getSocialTopic() != null) {
                    out.println(postIn.getSocialTopic().getDisplayTitle(lang));
                } else {
                    out.println("---");
                }
                out.println("</td>");

                //Creation Time
                out.println("<td>");
                out.println(SWBUtils.TEXT.getTimeAgo(postIn.getPi_created(), lang));
                out.println("</td>");

                //Sentiment
                out.println("<td>");
                out.println(postIn.getPostSentimentalType() == 0 ? paramRequest.getLocaleString("neutral") : postIn.getPostSentimentalType() == 1 ? paramRequest.getLocaleString("positive") : postIn.getPostSentimentalType() == 2 ? paramRequest.getLocaleString("negative") : "---");
                out.println("</td>");

                //Intensity
                out.println("<td>");
                out.println(postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostSentimentalType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostSentimentalType() == 2 ? paramRequest.getLocaleString("high") : "---");
                out.println("</td>");

                //Emoticon
                out.println("<td>");
                if (postIn.getPostSentimentalEmoticonType() == 1) {
                    out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emopos.png" + "/>");
                } else if (postIn.getPostSentimentalEmoticonType() == 2) {
                    out.println("<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/css/images/emoneg.png" + "/>");
                } else if (postIn.getPostSentimentalEmoticonType() == 0) {
                    out.println("Neutro");
                }
                out.println("</td>");


                //Replicas
                out.println("<td align=\"center\">");
                out.println(postIn.getPostShared());
                out.println("</td>");


                //User
                out.println("<td>");
                out.println(postIn.getPostInSocialNetworkUser().getSnu_name());
                out.println("</td>");

                //Followers
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser().getFollowers());
                out.println("</td>");

                //Friends
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser().getFriends());
                out.println("</td>");

                //Klout
                out.println("<td align=\"center\">");
                out.println(postIn.getPostInSocialNetworkUser().getSnu_klout());
                out.println("</td>");

                //Place
                out.println("<td>");
                out.println(postIn.getPostPlace() == null ? "---" : postIn.getPostPlace());
                out.println("</td>");


                out.println("</tr>");
                
            }

        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</fieldset>");

        if (paginate) {
            out.println("<div id=\"pagination\">");
            out.println("<span>P&aacute;ginas:</span>");
            for (int countPage = 1; countPage < (Math.ceil((double) nRec / (double) recPerPage) + 1); countPage++) {
                SWBResourceURL pageURL = paramRequest.getRenderUrl();
                pageURL.setParameter("page", "" + (countPage));
                pageURL.setParameter("socialSite", socialSiteUri);
                
                //pageURL.setParameter("suri", id);
                //pageURL.setParameter("search", (searchWord.trim().length() > 0 ? searchWord : ""));
                //pageURL.setParameter("swbSocialUser", swbSocialUser);
                /*
                if (request.getParameter("orderBy") != null) {
                    pageURL.setParameter("orderBy", request.getParameter("orderBy"));
                }*/
                if (countPage != nPage) {
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + pageURL + "',this); return false;\">" + countPage + "</a> ");
                } else {
                    out.println(countPage + " ");
                }
            }
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
}
