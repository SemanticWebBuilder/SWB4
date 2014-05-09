/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Message;
import org.semanticwb.social.Photo;
import org.semanticwb.social.Post;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;

import org.semanticwb.social.Video;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.Youtube;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SocialLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//This is a comment, why this is not working!!
/**
 *
 * @author francisco.jimenez
 */
public class YoutubeWall extends GenericResource{

    private static Logger log = SWBUtils.getLogger(YoutubeWall.class);
    
    /*variables used to define the id of '<div>' for the fields of information, favorite and reweet.
     Each link is in a different '<div>' and it's updated individually*/
    public static String INFORMATION = "/inf";
    public static String LIKE = "/like";
    public static String DISLIKE = "/unlike";
    public static String TOPIC ="/topic";
    
    /*Additionally every div has a suffix to identify if the status is inside the tab*/ 
    public static String HOME_TAB = "/myvideos";
    public static String DISCOVER_TAB ="/discover";
    public static String CONEXION ="/conexion";
    
    public static int DEFAULT_VIDEO_COMMENTS = 5;
    public static String BASE_VIDEO_URL = "http://www.youtube.com/v/";
    public static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = (String) request.getParameter("suri");
        String contentTabId = (String) request.getParameter("contentTabId");
        Youtube youtube = (Youtube) SemanticObject.createSemanticObject(objUri).createGenericInstance();
        if(!youtube.isSn_authenticated() || youtube.getAccessToken() == null){
            out.println("<div id=\"configuracion_redes\">");
            out.println("<div id=\"autenticacion\">");
            out.println("<p>      La cuenta no ha sido autenticada correctamente</p>");
            out.println("</div>");
            out.println("</div>");
            return;
        }
        //System.out.println("suriReceived in YoutubeWall:" + objUri);
        if(contentTabId == null){//The resource is loaded for the first time and it needs to display the tabs
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeTabs1.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            }catch (Exception e) {
                //System.out.println("Error loading the Youtube Tabs " + e);
                log.error("Error loading Youtube tabs", e);
            }
            return;
        }
        
        String jspResponse = "";
        //Each one of the tabs is loaded once
        if(contentTabId != null && contentTabId.equals(HOME_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeVideos.jsp";
        }else if(contentTabId != null && contentTabId.equals(DISCOVER_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeDiscover.jsp";
        }else if(contentTabId != null && contentTabId.equals(CONEXION)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeConexion.jsp";
        }
        
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }
    }
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        System.out.println("\n\n\nModo: " + mode);        
        String objUri = request.getParameter("suri");        
        //System.out.println("suri in processRequest:" + objUri);
        PrintWriter out = response.getWriter();
        if(mode!= null && mode.equals("commentVideoSent")){//Feedback of commented video
            //response.getWriter().print("Comment sent");
            out.println("<script type=\"text/javascript\">");
            out.println("   hideDialog();");
            out.println("   showStatus('Comment sent successfully');");
            out.println("</script>");
        }else if(mode!= null && mode.equals("likeSent")){//Feedback of liked video
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("suri", request.getParameter("suri"));
            String videoId = request.getParameter("videoId");
            String action = request.getParameter("action");
            String htmlImage = "";
            //System.out.println("LIKE SENT:" + request.getParameter("suri") + videoId + action);
            String actionTitle = "";
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        
            try {
                HashMap<String, String> paramsVideo = new HashMap<String, String>(3);
                paramsVideo.put("v", "2");
                paramsVideo.put("fields", "yt:statistics,yt:rating,published");
                String videoInfo= getRequest(paramsVideo, "https://gdata.youtube.com/feeds/api/videos/" + videoId,
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", semanticYoutube.getAccessToken());
                //System.out.println("video INFO:" + videoInfo);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                builder = factory.newDocumentBuilder();
                Document xmlDoc = builder.parse(new InputSource(new StringReader(videoInfo)));
                xmlDoc.getDocumentElement().normalize();
                NodeList rootNode = xmlDoc.getDocumentElement().getChildNodes();
                String favCount = "0";
                
                for( int tmp = 0; tmp < rootNode.getLength(); tmp++){
                    Node nNode= rootNode.item(tmp);
                    if(nNode.getNodeName().equals("published")){
                        System.out.println("published:" + nNode.getTextContent());
                        Date date = formatter.parse(nNode.getTextContent());
                        out.write("<em>" + humanFriendlyDate(date, paramRequest) + "</em>");
                    }else if(nNode.getNodeName().equals("yt:statistics")){                        
                        out.println( paramRequest.getLocaleString("views") +":" + nNode.getAttributes().getNamedItem("viewCount").getTextContent() + " ");
                        System.out.println(nNode.getAttributes().getNamedItem("viewCount").getTextContent());
                        favCount = nNode.getAttributes().getNamedItem("favoriteCount").getTextContent();
                    }else if(nNode.getNodeName().equals("yt:rating")){
                        //System.out.println("yt:rating" + nNode.getNodeValue());
                        //System.out.println(nNode.getAttributes().getNamedItem("numDislikes").getTextContent());
                        //System.out.println(nNode.getAttributes().getNamedItem("numLikes").getTextContent());
                        
                        out.write(" <strong><span> " + paramRequest.getLocaleString("likes") + ": </span>");
                        out.write(nNode.getAttributes().getNamedItem("numLikes").getTextContent() + " ");
                        
                        out.write(" " + paramRequest.getLocaleString("dislikes") + ": ");
                        out.println(nNode.getAttributes().getNamedItem("numDislikes").getTextContent() + " ");
                        out.println(" " + paramRequest.getLocaleString("favorites") + ": " +  favCount);
                        out.write("</strong>");
                    }
                }                

                if(action.equals("doLike")){
                    actionURL.setAction("doDislike");
                    action = "dislike";
                    actionTitle = paramRequest.getLocaleString("dislike");
                    htmlImage = "class=\"nolike\"";
                }else if(action.equals("doDislike")){
                    actionURL.setAction("doLike");
                    action = "like";
                    actionTitle = paramRequest.getLocaleString("like");
                    htmlImage= "class=\"like\"";
                }
                
                /* updates only the DOM of the 'Like/Dislike' message and change URL also*/
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   var spanId = dijit.byId('" + semanticYoutube.getId() + videoId + LIKE + "');");
                out.println("   spanId.attr('content', '" + "<a href=\"\" " + htmlImage + " onclick=\"try{dojo.byId(this.parentNode).innerHTML = \\'<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>\\';}catch(noe){} postSocialHtml(\\'" + actionURL.setParameter("videoId", videoId).setParameter("action", action) + "\\',\\'" + semanticYoutube.getId() + videoId + INFORMATION + "\\');return false;" +"\"><span>" + actionTitle + "</span></a>" +"')");
                out.println("   showStatus('" + paramRequest.getLocaleString("actionExecuted") + "');");
                out.println("</script>");
                out.println("</span>");
            } catch (Exception ex) {
                log.error("Error when trying to like/dislike ", ex);
            }
            //response.getWriter().print("like / unlike sent");
        }else if(mode!= null && mode.equals("commentVideo")){//Displays dialog to create a comment
            SocialNetwork socialNetwork = null;
            String videoId = request.getParameter("videoId");

            try {
                socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            }catch(Exception e){
                //System.out.println("Error getting the SocialNetwork " + e);
                log.error("Error getting the social Network", e);
                return;
            }
            
            try {
                SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                
                PostIn postIn = PostIn.getPostInbySocialMsgId(model, videoId);
                
                if(postIn == null){//Responding for the first time, save the post
                    HashMap<String, String> paramsVideo = new HashMap<String, String>(3);
                    paramsVideo.put("v", "2");            
                    paramsVideo.put("alt", "json");//https://gdata.youtube.com/feeds/api/videos/videoid?v=2
                    String ytVideo= getRequest(paramsVideo, "https://gdata.youtube.com/feeds/api/videos/" + videoId,
                                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", ((Youtube)socialNetwork).getAccessToken());
                    JSONObject jsonVideo = new JSONObject(ytVideo);

                    String title = "";
                    String description = "";
                    String creatorName = "";
                    String creatorId =  "";
                    String created = "";

                    if(jsonVideo.has("entry")){
                        if(jsonVideo.getJSONObject("entry").has("title")){//Title
                            title = jsonVideo.getJSONObject("entry").getJSONObject("title").getString("$t");
                        }

                        if(jsonVideo.getJSONObject("entry").has("media$group")){//Desc
                            if(jsonVideo.getJSONObject("entry").getJSONObject("media$group").has("media$description")){
                                description = jsonVideo.getJSONObject("entry").getJSONObject("media$group").getJSONObject("media$description").getString("$t");
                            }
                        }

                        if(jsonVideo.getJSONObject("entry").has("author")){//User
                            if(jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).has("name")){
                                creatorName = jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t");
                            }

                            if(jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).has("yt$userId")){
                                creatorId = jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t");
                            }
                        }
                        
                        if(jsonVideo.getJSONObject("entry").has("published")){//Title
                            created = jsonVideo.getJSONObject("entry").getJSONObject("published").getString("$t");
                        }
                    }

                    //System.out.println("-" + title + "-" + description +"-" + creatorName + "-" + creatorId );
                    SocialNetworkUser socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(creatorId, socialNetwork, model);

                    postIn=VideoIn.ClassMgr.createVideoIn(model);
                    postIn.setSocialNetMsgId(videoId);
                    postIn.setMsg_Text(title + (description.isEmpty()? "" : " / " + description));
                    postIn.setPostInSocialNetwork(socialNetwork);
                    postIn.setPostInStream(null);                    
                    Date postTime = formatter.parse(created);
                    if(postTime.after(new Date())){
                        postIn.setPi_createdInSocialNet(new Date());
                    }else{
                        postIn.setPi_createdInSocialNet(postTime);
                    }
                    postIn.setMsg_url("https://www.youtube.com/watch?v=" + videoId + "&feature=youtube_gdata");
                    Calendar calendario = Calendar.getInstance();
                    postIn.setPi_created(calendario.getTime());
                    postIn.setPi_type(SWBSocialUtil.POST_TYPE_VIDEO);

                    VideoIn videoIn=(VideoIn)postIn;
                    videoIn.setVideo(BASE_VIDEO_URL + videoId);

                     if(socialNetUser == null){//User does not exist                    
                        System.out.println("USUARIO NO EXISTE EN EL SISTEMA");
                        socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                        socialNetUser.setSnu_id(creatorId);
                        socialNetUser.setSnu_name((creatorName.isEmpty()) ? creatorId : creatorName);
                        socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());                    
                        socialNetUser.setCreated(new Date());
                        socialNetUser.setUserUrl("https://www.youtube.com/" + creatorId);
                        socialNetUser.setFollowers(0);
                        socialNetUser.setFriends(0);
                    }else{
                        System.out.println("YA EXISTE EN EL SISTEMA:" + socialNetUser);
                    }

                    postIn.setPostInSocialNetworkUser(socialNetUser);

                    SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
                    if(defaultSocialTopic != null){
                        postIn.setSocialTopic(defaultSocialTopic);//Asigns socialTipic
                        System.out.println("Setting social topic:" + defaultSocialTopic);
                    }else{
                        postIn.setSocialTopic(null);
                        System.out.println("Setting to null");
                    }
                }
                
                response.setContentType("text/html; charset=ISO-8859-1");
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Pragma", "no-cache");
                //The post in has been created
                final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/postInResponse.jsp";
                RequestDispatcher dis = request.getRequestDispatcher(path);
                if (dis != null) {
                    try {
                        request.setAttribute("postUri", SemanticObject.createSemanticObject(postIn.getURI()));
                        request.setAttribute("paramRequest", paramRequest);
                        dis.include(request, response);
                    } catch (Exception e) {
                        log.error(e);
                    }
                }

                System.out.println("POST CREADO CORRECTAMENTE: " + postIn.getId() + " ** " + postIn.getSocialNetMsgId());
            }catch(Exception e){
                System.out.println("Error trying to setSocialTopic");
                log.error("ERROR:", e);
            }            
            
            /*SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("videoId", request.getParameter("videoId"));
            actionURL.setParameter("suri", request.getParameter("suri"));

            out.println("<form type=\"dijit.form.Form\" id=\"createComment\" action=\"" +  actionURL.setAction("createCommentVideo") + "\" method=\"post\" onsubmit=\"submitForm('createComment'); try{document.getElementById('csLoading').style.display='inline';}catch(noe){}; return false;\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>"); 
            out.println("   <td>");
            out.println("       <textarea type=\"dijit.form.Textarea\" name=\"comment\" id=\"comment\" rows=\"4\" cols=\"50\"></textarea>");
            out.println("   </td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Comment</button></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");*/
        } else if (mode != null && mode.equals("commentComment")) {//Displays dialog to create a comment
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("videoId", request.getParameter("videoId"));
            actionURL.setParameter("suri", request.getParameter("suri"));
            actionURL.setParameter("commentId", request.getParameter("commentId"));

            out.println("<form type=\"dijit.form.Form\" id=\"commentCommentForm\" action=\"" + actionURL.setAction("createCommentComment") + "\" method=\"post\" onsubmit=\"submitForm('commentCommentForm'); try{document.getElementById('csLoading').style.display='inline';}catch(noe){}; return false;\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>");
            out.println("   <td>");
            out.println("       <textarea type=\"dijit.form.Textarea\" name=\"commentComment\" id=\"commentComment\" rows=\"4\" cols=\"50\"></textarea>");
            out.println("   </td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Comentar</button></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
        } else if(mode != null && mode.equals("getMoreComments")){
            doGetMoreComments(request, response, paramRequest);
        }else if(mode.equals("doShowTopic")){
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/assignTopic.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            if (dis != null) {
                try {
                    request.setAttribute("suri", objUri);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error("Error on doShowTopic: " + e);
                }
            }
        }else if(mode.equals("doReclassifyTopic")){
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/classifybyTopic.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            if (dis != null) {
                try {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    request.setAttribute("postUri", semObject);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error("Error on doReclassifyTopic: " + e);
                }
            }
        }else if(mode.equals("assignedPost")){
            String id = request.getParameter("id");
            String currentTab = request.getParameter("currentTab");
            String postUri = request.getParameter("postUri");
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", id).setParameter("postUri", postUri).setParameter("currentTab", currentTab);
            
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Youtube semYoutube = (Youtube) semanticObject.createGenericInstance();
            
            String url= "<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("reclassify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','" + paramRequest.getLocaleString("reclassify") +"'); return false;\"></a>";
            out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
            out.println("<script type=\"dojo/method\">");
            out.println("   hideDialog(); ");            
            out.println("   try{");
            out.println("   var spanId = dijit.byId('" + semYoutube.getId() +  id + TOPIC + "');");            
            out.println("   spanId.attr('content', '" + url.replace("'", "\\'") +"');");           
            out.println("   }catch(noe){alert('Error:' + noe);}");
            out.println("   showStatus('" +paramRequest.getLocaleString("assignedTopicOk") + "');");
            out.println("</script>");
            out.println("</span>");
        }else if(mode.equals("reAssignedPost")){
            out.println("<script type=\"javascript\">");
            out.println("   hideDialog(); ");
            out.println("   showStatus('" + paramRequest.getLocaleString("changedTopicOk") + "');");
            out.println("</script>");
        }else if(mode.equals("getMoreVideos")){
            doGetMoreVideos(request, response, paramRequest);
        }else if(mode!=null && mode.equals("displayVideo")){
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/playVideo.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error in displayVideo() for requestDispatcher" , e);
            }
        }else if(mode.equals("showUserProfile")){
            RequestDispatcher dis = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeUserProfile.jsp");
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error in processRequest() for requestDispatcher" , e);
            }
        }else if(mode!=null && mode.equals("editVideo")){
            response.setContentType("text/html; charset=ISO-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/youtubeEditVideo.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error in editVideo() for requestDispatcher" , e);
            }
        }else if(mode != null && mode.equals("videoUpdated")){
            try{
                String videoId = (String) request.getParameter("videoId");
                if(videoId == null || objUri == null){
                    return;
                }

                SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
                Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();

                String video = getFullVideoFromId(videoId, semanticYoutube.getAccessToken());
                JSONObject videoResp = new JSONObject(video);

                String title = "";
                String description = "";
                String privacy = "";
                String thumbnail ="";
                JSONObject information = videoResp.getJSONObject("entry");
                JSONArray accessControl = null;

                if(!information.isNull("yt$accessControl")){
                    accessControl = information.getJSONArray("yt$accessControl");
                }

                if(!information.isNull("title")){
                    title = information.getJSONObject("title").getString("$t");
                    title = SWBUtils.TEXT.encode(title, "UTF-8");
                }
                if(!information.isNull("media$group")){
                    if(!information.getJSONObject("media$group").isNull("media$description")){
                        if(!information.getJSONObject("media$group").getJSONObject("media$description").isNull("$t")){
                            description = information.getJSONObject("media$group").getJSONObject("media$description").getString("$t");                
                            description = SWBUtils.TEXT.encode(description, "UTF-8");
                        }
                    }

                    if(!information.getJSONObject("media$group").isNull("yt$private")){//If video is private
                        privacy = "PRIVATE";
                    }else{//If is not private check if public or unlisted
                        for(int i = 0; i < accessControl.length(); i++){
                            JSONObject control = accessControl.getJSONObject(i);
                            if(!control.isNull("action") && !control.isNull("permission")){
                                if(control.getString("action").equalsIgnoreCase("list")){
                                    if(control.getString("permission").equalsIgnoreCase("allowed")){
                                        privacy = "PUBLIC";
                                    }else if(control.getString("permission").equalsIgnoreCase("denied")){
                                        privacy = "NOT_LISTED";
                                    }
                                }
                            }
                        }
                    }
                    
                    if(!information.getJSONObject("media$group").isNull("media$thumbnail")){
                        JSONArray thumbArray = information.getJSONObject("media$group").getJSONArray("media$thumbnail");
                        for(int i = 0; i < thumbArray.length(); i++){
                            JSONObject entryThumb = thumbArray.getJSONObject(i);
                            if(entryThumb.has("yt$name")){
                                if(entryThumb.getString("yt$name").equals("hqdefault")){
                                    thumbnail = entryThumb.getString("url");
                                    break;
                                }
                            }
                        }
                    }
                    
                    if(!information.getJSONObject("media$group").getJSONObject("media$description").isNull("$t")){
                        description = information.getJSONObject("media$group").getJSONObject("media$description").getString("$t");                
                        description = SWBUtils.TEXT.encode(description, "UTF-8");
                    }
                }
                StringBuilder output = new StringBuilder();                
                
                output.append("<p>");
                output.append(title);
                output.append("</p>");

                output.append("<div class=\"timelineimg\">");
                output.append(" <span>");                                

                output.append("      <span id=\"img" + semanticYoutube.getId() + videoId + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    output.append("      <a href=\"#\" onclick=\"showDialog(\\'"+ paramRequest.getRenderUrl().setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode("http://www.youtube.com/v/" + videoId, "UTF-8")) +
                            "\\',\\'" + title + "\\'); return false;\"><img src=\"" + thumbnail + "\" style=\"position: relative;\" onerror=\"this.src =\\'" + thumbnail + "\\'\" onload=\"imageLoad(" + "this, \\'img" + semanticYoutube.getId() + videoId + "\\');\"/></a>");
                    output.append("      </span>");
                output.append(" </span>");
                
                output.append("<p class=\"imgtitle\">");
                output.append(  title);
                output.append("</p>");
                
                output.append("<p class =\"imgdesc\">");
                output.append( description.isEmpty() ?  "&nbsp;" : description.replace("\n", "</br>"));
                output.append("</p>");
                output.append("</div>");//End First section
                
                out.println("<script type=\"javascript\">");
                out.println("   var edited = document.getElementById('" + semanticYoutube.getId() + "/" + videoId + "/detail" +"');");                
                out.println("   edited.innerHTML='" + output.toString() + "'");
                out.println("   showStatus('" + paramRequest.getLocaleString("videoEdited") + "');");
                out.println("   hideDialog(); ");
                out.println("</script>");
            }catch(Exception e){
                log.error("Error al traer datos del video ", e);
            }
        }else if(mode != null && mode.equals("videoDeleted")){
            String videoId = request.getParameter("videoId");

            if((videoId == null || videoId.trim().isEmpty()) ||
                    (objUri == null || objUri.trim().isEmpty())){
                log.error("Problem updating video information");
                return;
            }
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
            //System.out.println("VIDEO ID:" + videoId);
            System.out.println("SURI:" + objUri);
            out.println("<script type=\"javascript\">");
            out.println("   showStatus('" + paramRequest.getLocaleString("videoDeleted") +"');");
            out.println("   var deleted = document.getElementById('" + semanticYoutube.getId() + "/" + videoId +"');");
            out.println("   deleted.style.display='none';");
            out.println("   deleted.innerHTML=''");
            //out.println("   spanId.attr('content', 'PACONE');");
            //out.println("   spanId.domNode.style.visibility = 'hidden';");
            out.println("</script>");
        }else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        }else{
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doGetMoreComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String videoId = request.getParameter("videoId");
        String startIndex = request.getParameter("startIndex");
        String totalComments = request.getParameter("totalComments");
        String objUri = request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        System.out.println("videoId:" +videoId + "--startIndex:" + startIndex  + "--totalComments:" + totalComments);
        
        try{
            HashMap<String, String> paramsUsr = new HashMap<String, String>(3);
            paramsUsr.put("v", "2");
            paramsUsr.put("fields", "media:thumbnail");
            paramsUsr.put("alt", "json");
        
            HashMap<String, String> paramsComments = new HashMap<String, String>(3);
            paramsComments.put("v", "2");
            paramsComments.put("max-results", "10");
            paramsComments.put("start-index", (Integer.parseInt(startIndex) + 1) + "");
            paramsComments.put("alt", "json");
            String ytComments= getRequest(paramsComments, "https://gdata.youtube.com/feeds/api/videos/" + videoId + "/comments",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
            JSONObject jsonComments = new JSONObject(ytComments);
            JSONArray arrayComments = null;
            if(!jsonComments.isNull("feed")){
                if(!jsonComments.getJSONObject("feed").isNull("entry")){
                    arrayComments = jsonComments.getJSONObject("feed").getJSONArray("entry");
                }
            }
            
            if(arrayComments != null && arrayComments.length() > 0){//Only print <li></li> because the HTML will be returned inside <ul></ul
                int commentCounter = 0;
                for(int c = 0; c < arrayComments.length(); c++){
                    commentCounter++;
                    JSONObject comment = arrayComments.getJSONObject(c);
                    JSONObject usrCommentProfile = null;
                    if(!comment.isNull("author")){
                        if(!comment.getJSONArray("author").getJSONObject(0).isNull("yt$userId")){
                            if(comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t").equalsIgnoreCase("__NO_YOUTUBE_ACCOUNT__")){
                                continue;
                            }
                            String commentProfile = getRequest(paramsUsr, "http://gdata.youtube.com/feeds/api/users/" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t"),
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
                            usrCommentProfile = new JSONObject(commentProfile);
                        }
                    }
                    out.write("<li>");
                    out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\"><img src=\"" + usrCommentProfile.getJSONObject("entry").getJSONObject("media$thumbnail").getString("url") + "\" width=\"50\" height=\"50\"/></a>");

                    out.write("<p>");
                    out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\">" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t") + "</a>:");
                    out.write(       comment.getJSONObject("content").getString("$t").replace("\n", "</br>"));
                    out.write("</p>");

                    //Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));

                    out.write("<p class=\"timelinedate\">");
                    //out.write("<span dojoType=\"dojox.layout.ContentPane\">");
                    out.write("<span>");
                    Date date = formatter.parse(comment.getJSONObject("published").getString("$t"));
                    out.write("<em>" + humanFriendlyDate(date, paramRequest) +  "</em>");
                    out.write("</span>");
                    String comentarioId = comment.getJSONObject("id").getString("$t");
                    out.write("   <span class=\"inline\">");
                    out.write(" <a href=\"\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("commentComment").setParameter("suri", objUri).setParameter("videoId",videoId).setParameter("commentId", comentarioId.substring(comentarioId.indexOf("comment") + 8)) + "','Comment to " + comment.getJSONObject("content").getString("$t").replace("\n", "</br>") + "');return false;\">Comentar</a>");
                    out.write("   </span>");
                    out.write("</p>");
                    out.write("</li>");
                }
                System.out.println("SE OBTUVIERON :" + commentCounter + " COMENTARIOS");
                if((Integer.parseInt(startIndex) + commentCounter) < Integer.parseInt(totalComments) ){//Link to get more comments
                    out.write("<li class=\"timelinemore\">");
                    out.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + paramRequest.getRenderUrl().setMode("getMoreComments").setParameter("videoId", videoId).setParameter("startIndex", (commentCounter + Integer.parseInt(startIndex)) +"").setParameter("totalComments", totalComments+"").setParameter("suri", objUri)
                            + "','" + semanticYoutube.getId() + videoId +"/comments', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\"><span>+</span>" + paramRequest.getLocaleString("moreComments") +"</a></label>");
                    out.write("</li>");
                }
            }
        }catch(Exception e){
            //System.out.println("ERROR GETTING MORE COMMENTS");
            log.error("Problem getting more comments", e);
        }
    }

    
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        
        if(action != null && (action.equals("doLike") || action.equals("doDislike"))){//Do a Like
            System.out.println("Doing a like");
            response.setRenderParameter("videoId", request.getParameter("videoId"));                                       //Id of original status
            response.setRenderParameter("suri", request.getParameter("suri"));
            response.setRenderParameter("action", action);
            doLikeDislike(request);
            response.setMode("likeSent");
        }else if(action != null && action.equals("createCommentVideo")){
            System.out.println("Commenting a video");
            doCommentVideo(request);
            response.setMode("commentVideoSent");
        }else if (action != null && action.equals("createCommentComment")) {
            System.out.println("Commenting commenting a video");
            doCommentComment(request);
            response.setMode("commentVideoSent");
        }else if(action.equals("setSocialTopic")){
            SocialNetwork socialNetwork = null;
            String videoId = request.getParameter("id");
            String objUri = request.getParameter("suri");

            try {
                socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            }catch(Exception e){
                //System.out.println("Error getting the SocialNetwork " + e);
                log.error("Error getting the social Network", e);
                return;
            }
            
            try {
                HashMap<String, String> paramsVideo = new HashMap<String, String>(3);
                paramsVideo.put("v", "2");            
                paramsVideo.put("alt", "json");//https://gdata.youtube.com/feeds/api/videos/videoid?v=2
                String ytVideo= getRequest(paramsVideo, "https://gdata.youtube.com/feeds/api/videos/" + videoId,
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", ((Youtube)socialNetwork).getAccessToken());
                JSONObject jsonVideo = new JSONObject(ytVideo);

                String title = "";
                String description = "";
                String creatorName = "";
                String creatorId =  "";
                String created = "";
                if(jsonVideo.has("entry")){
                    if(jsonVideo.getJSONObject("entry").has("title")){//Title
                        title = jsonVideo.getJSONObject("entry").getJSONObject("title").getString("$t");
                    }
                    
                    if(jsonVideo.getJSONObject("entry").has("media$group")){//Desc
                        if(jsonVideo.getJSONObject("entry").getJSONObject("media$group").has("media$description")){
                            description = jsonVideo.getJSONObject("entry").getJSONObject("media$group").getJSONObject("media$description").getString("$t");
                        }
                    }

                    if(jsonVideo.getJSONObject("entry").has("author")){//User
                        if(jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).has("name")){
                            creatorName = jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t");
                        }
                        
                        if(jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).has("yt$userId")){
                            creatorId = jsonVideo.getJSONObject("entry").getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t");
                        }
                    }
                    if(jsonVideo.getJSONObject("entry").has("published")){//Title
                        created = jsonVideo.getJSONObject("entry").getJSONObject("published").getString("$t");
                    }
                }

                //System.out.println("-" + title + "-" + description +"-" + creatorName + "-" + creatorId );
                SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                SocialNetworkUser socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(creatorId, socialNetwork, model);
                                
                PostIn postIn = PostIn.getPostInbySocialMsgId(model, videoId);
                if(postIn == null){
                    postIn=VideoIn.ClassMgr.createVideoIn(model);
                    postIn.setSocialNetMsgId(videoId);
                    postIn.setMsg_Text(title + (description.isEmpty()? "" : " / " + description));
                    postIn.setPostInSocialNetwork(socialNetwork);
                    postIn.setPostInStream(null);
                    Date postTime = formatter.parse(created);
                    if(postTime.after(new Date())){
                        postIn.setPi_createdInSocialNet(new Date());
                    }else{
                        postIn.setPi_createdInSocialNet(postTime);
                    }
                    postIn.setMsg_url("https://www.youtube.com/watch?v=" + videoId + "&feature=youtube_gdata");
                    Calendar calendario = Calendar.getInstance();
                    postIn.setPi_created(calendario.getTime());
                    postIn.setPi_type(SWBSocialUtil.POST_TYPE_VIDEO);

                    VideoIn videoIn=(VideoIn)postIn;
                    videoIn.setVideo(BASE_VIDEO_URL + videoId);

                     if(socialNetUser == null){//User does not exist                    
                        System.out.println("USUARIO NO EXISTE EN EL SISTEMA");
                        socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                        socialNetUser.setSnu_id(creatorId);
                        socialNetUser.setSnu_name((creatorName.isEmpty()) ? creatorId : creatorName);
                        socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());
                        socialNetUser.setUserUrl("https://www.youtube.com/" + creatorId);
                        socialNetUser.setCreated(new Date());
                        socialNetUser.setFollowers(0);
                        socialNetUser.setFriends(0);
                    }else{
                        System.out.println("YA EXISTE EN EL SISTEMA:" + socialNetUser);
                    }

                    postIn.setPostInSocialNetworkUser(socialNetUser);
                }else{
                    log.error("The post with id :" + postIn.getSocialNetMsgId() + " already exists, making another response");
                }
                
                if(request.getParameter("newSocialTopic").equals("none")){
                    postIn.setSocialTopic(null);
                }else {
                    SemanticObject semObjSocialTopic=SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if(semObjSocialTopic!=null)
                    {
                        SocialTopic socialTopic=(SocialTopic)semObjSocialTopic.createGenericInstance();
                        postIn.setSocialTopic(socialTopic);//Asigns socialTipic
                    }
                }
                
                response.setRenderParameter("postUri", postIn.getURI());

                System.out.println("POST CREADO CORRECTAMENTE: " + postIn.getId() + " ** " + postIn.getSocialNetMsgId());
            }catch(Exception e){
                //System.out.println("Error trying to setSocialTopic");
                log.error("ERROR:", e);
            }
            response.setRenderParameter("suri", socialNetwork.getURI());
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            response.setRenderParameter("id", videoId);            
            response.setMode("assignedPost");
        }else if(action.equals("changeSocialTopic"))
        {
            if(request.getParameter("postUri")!=null && request.getParameter("newSocialTopic")!=null){
                SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("postUri"));
                Post post=(Post)semObj.createGenericInstance();
                if(request.getParameter("newSocialTopic").equals("none")){
                    post.setSocialTopic(null);
                }else{
                    SemanticObject semObjSocialTopic=SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if(semObjSocialTopic!=null){
                        SocialTopic socialTopic=(SocialTopic)semObjSocialTopic.createGenericInstance();
                        post.setSocialTopic(socialTopic);
                    }
                }
            }
            response.setMode("reAssignedPost");
        }else if(action.equals("doUpdateVideo")){
            doUpdateVideo(request);            
            response.setRenderParameter("videoId", request.getParameter("videoId"));
            response.setRenderParameter("suri", request.getParameter("suri"));
            response.setMode("videoUpdated");
        }else if (action.equals("doDeleteVideo")){
            doDeleteVideo(request);
            response.setRenderParameter("videoId", request.getParameter("videoId"));
            response.setRenderParameter("suri", request.getParameter("suri"));
            response.setMode("videoDeleted");
        }else if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) {
            //System.out.println("Entra a InBox_processAction-2:"+request.getParameter("objUri"));
            if (request.getParameter("objUri") != null) {
                //System.out.println("Entra a InBox_processAction-3");
                PostIn postIn = (PostIn) SemanticObject.getSemanticObject(request.getParameter("objUri")).createGenericInstance();
                SocialTopic stOld = postIn.getSocialTopic();
                ///
                WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
                String socialUri = "";
                int j = 0;
                Enumeration<String> enumParams = request.getParameterNames();
                while (enumParams.hasMoreElements()) {
                    String paramName = enumParams.nextElement();
                    if (paramName.startsWith("http://")) {//get param name starting with http:// -> URIs
                        if (socialUri.trim().length() > 0) {
                            socialUri += "|";
                        }
                        socialUri += paramName;
                        j++;
                    }
                }
                
                ArrayList aSocialNets = new ArrayList();//Social nets where the post will be published
                String[] socialUris = socialUri.split("\\|");  //Dividir valores
                if( j > 0 && wsite != null){
                    for (int i = 0; i < socialUris.length; i++) {
                        String tmp_socialUri = socialUris[i];
                        SemanticObject semObject = SemanticObject.createSemanticObject(tmp_socialUri, wsite.getSemanticModel());
                        SocialNetwork socialNet = (SocialNetwork) semObject.createGenericInstance();
                        //Se agrega la red social de salida al post
                        aSocialNets.add(socialNet);
                    }
                }
                                
                String toPost = request.getParameter("toPost");
                String socialFlow = request.getParameter("socialFlow");
                SocialPFlow socialPFlow = null;
                if (socialFlow != null && socialFlow.trim().length() > 0) {
                    socialPFlow = (SocialPFlow) SemanticObject.createSemanticObject(socialFlow).createGenericInstance();
                    //Revisa si el flujo de publicación soporte el tipo de postOut, de lo contrario, asinga null a spflow, para que no 
                    //asigne flujo al mensaje de salida., Esto también esta validado desde el jsp typeOfContent
                    if ((toPost.equals("msg") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Message.sclass))
                            || (toPost.equals("photo") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Photo.sclass))
                            || (toPost.equals("video") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Video.sclass))) {
                        socialPFlow = null;
                    }
                }

                //System.out.println("Entra a InBox_processAction-4");
                SWBSocialUtil.PostOutUtil.sendNewPost(postIn, postIn.getSocialTopic(), socialPFlow, aSocialNets, wsite, toPost, request, response);
                response.setMode("commentVideoSent");
            }
        }
    }
    
    private void doDeleteVideo(HttpServletRequest request){
        String videoId = request.getParameter("videoId");
        String objUri = request.getParameter("suri");
        
        if((videoId == null || videoId.trim().isEmpty()) ||
                (objUri == null || objUri.trim().isEmpty())){
            log.error("Problem updating video information");
            return;
        }
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        if(!semanticYoutube.validateToken()){
            log.error("Unable to update the access token inside delete Video!");
            return;
        }
        
        String urlVideo = "http://gdata.youtube.com/feeds/api/users/default/uploads/" + videoId;
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlVideo);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
            conn.setUseCaches(false);
            conn.setRequestProperty("Host", "gdata.youtube.com");
            conn.setRequestProperty("Content-Type", "application/atom+xml");
            conn.setRequestProperty("Authorization", "Bearer " + semanticYoutube.getAccessToken());
            conn.setRequestProperty("GData-Version", "2");
            conn.setRequestProperty("X-GData-Key", "key=" + semanticYoutube.getDeveloperKey());
            conn.connect();
            BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String docxml = readerl.readLine();
            //System.out.println("THE READ:" + docxml);

        }catch(Exception ex){
            log.error("ERROR deleting video", ex);
            //ex.printStackTrace();
        }
    }
    
    private void doUpdateVideo(HttpServletRequest request) {
        String videoId = request.getParameter("videoId");
        String objUri = request.getParameter("suri");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String keywords = request.getParameter("keywords");
        String privacy = request.getParameter("privacy");
        System.out.println("THE PRIVACY:" + privacy);
        System.out.println("SURI:" + objUri);
        
        if((videoId == null || videoId.trim().isEmpty()) || (title == null || title.trim().isEmpty()) ||
                (objUri == null || objUri.trim().isEmpty()) || (description == null || description.trim().isEmpty())
                || (category == null || category.trim().isEmpty())){
            log.error("Problem updating video information, missing fields.");
            return;
        }
        if(keywords == null || keywords.trim().isEmpty()){keywords = "";}
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        if(!semanticYoutube.validateToken()){
            log.error("Unable to update the access token inside update Video!");
            return;
        }
        
        String urlVideo = "http://gdata.youtube.com/feeds/api/users/default/uploads/" + videoId;
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlVideo);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setUseCaches(false);
            conn.setRequestProperty("Host", "gdata.youtube.com");
            conn.setRequestProperty("Content-Type", "application/atom+xml");
            conn.setRequestProperty("Authorization", "Bearer " + semanticYoutube.getAccessToken());
            conn.setRequestProperty("GData-Version", "2");
            conn.setRequestProperty("X-GData-Key", "key=" + semanticYoutube.getDeveloperKey());

            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            String listControl = "";
            if(privacy.equals("NOT_LISTED")){
                listControl = "<yt:accessControl action=\"list\" permission=\"denied\"/> \n\r";
            }else{
                listControl = "<yt:accessControl action=\"list\" permission=\"allowed\"/> \n\r";
            }
            String xml = "<?xml version=\"1.0\"?> \n\r"
                + "<entry xmlns=\"http://www.w3.org/2005/Atom\" \n\r"
                + "xmlns:media=\"http://search.yahoo.com/mrss/\" \n\r"
                + "xmlns:yt=\"http://gdata.youtube.com/schemas/2007\"> \n\r"
                + "<media:group> \n\r"
                + "<media:title type=\"plain\">" + title + "</media:title> \n\r"
                + "<media:description type=\"plain\">" + description + "</media:description> \n\r"
                + "<media:category scheme=\"http://gdata.youtube.com/schemas/2007/categories.cat\">" + category + "</media:category> \n\r"
                + "<media:keywords>" + keywords + "</media:keywords> \n\r"
                + (privacy.equals("PRIVATE") ? "<yt:private/> \n\r" :"")//Add this tag to make a video PRIVATE
                + "</media:group> \n\r"
                + "<yt:accessControl action=\"comment\" permission=\"allowed\"/> \n\r"
                + "<yt:accessControl action=\"commentVote\" permission=\"allowed\"/> \n\r"
                + "<yt:accessControl action=\"rate\" permission=\"allowed\"/> \n\r"
                + listControl
                + "<yt:accessControl action=\"embed\" permission=\"allowed\"/> \n\r"
                + "<yt:accessControl action=\"syndicate\" permission=\"allowed\"/> \n\r"                
                + "</entry>\n\r";
            writer.write(xml.getBytes("UTF-8"));
            writer.flush();
            writer.close();                        
            BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String docxml = readerl.readLine();
            System.out.println("THIS IS WHAT I READ:" + docxml);
            //System.out.println("THE READ:" + docxml);

        }catch(Exception ex){
            log.error("ERROR", ex);
            //ex.printStackTrace();
        }
    }
    
    private void doLikeDislike(HttpServletRequest request) {
        String action = request.getParameter("action");
        String videoId = request.getParameter("videoId");
        String objUri = request.getParameter("suri");
        if((action == null || action.isEmpty()) || (videoId == null || videoId.isEmpty()) ||
                (objUri == null || objUri.isEmpty())){
            log.error("Problema ejecutando accion Like/Dislike");
            return;
        }

        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        if(!semanticYoutube.validateToken()){
            log.error("Unable to update the access token!");
            return;
        }
        
        String url1 = "http://gdata.youtube.com/feeds/api/videos/" + videoId + "/ratings";
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(url1);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Host", "gdata.youtube.com");
            conn.setRequestProperty("Content-Type", "application/atom+xml");
            conn.setRequestProperty("Authorization", "Bearer " + semanticYoutube.getAccessToken());
            conn.setRequestProperty("GData-Version", "2");
            conn.setRequestProperty("X-GData-Key", "key=" + semanticYoutube.getDeveloperKey());

            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());                        
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
            " <entry xmlns=\"http://www.w3.org/2005/Atom\"\r\n" +
            " xmlns:yt=\"http://gdata.youtube.com/schemas/2007\">\r\n" +
            " <yt:rating value=\"" + action + "\"/>\r\n" +
            "</entry>\r\n";
            writer.write(xml.getBytes("UTF-8"));
            writer.flush();
            writer.close();                        
            BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String docxml = readerl.readLine();
            System.out.println("--Ejecuted Like/disLike:" + docxml);
        }catch(Exception ex){
            //System.out.println("ERROR" + ex.toString());
            log.error("Error making like/dislike", ex);
        }
    }

    private void doCommentVideo(HttpServletRequest request) {
        String videoId = request.getParameter("videoId");
        String objUri = request.getParameter("suri");
        String comment = request.getParameter("comment");
        
        if((videoId == null || videoId.isEmpty()) || (comment == null || comment.isEmpty()) ||
                (objUri == null || objUri.isEmpty())){
            log.error("Problema ejecutando el posteo del comentario");
            return;
        }
        
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        if(!semanticYoutube.validateToken()){
            log.error("Unable to update the access token inside post Comment!");
            return;
        }
        
        String urlComment = "http://gdata.youtube.com/feeds/api/videos/" + videoId + "/comments";
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlComment);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Host", "gdata.youtube.com");
            conn.setRequestProperty("Content-Type", "application/atom+xml");
            conn.setRequestProperty("Authorization", "Bearer " + semanticYoutube.getAccessToken());
            conn.setRequestProperty("GData-Version", "2");
            conn.setRequestProperty("X-GData-Key", "key=" + semanticYoutube.getDeveloperKey());

            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());                        
            String xml = "<?xml version=\"1.0\"?>"
                + "<entry xmlns=\"http://www.w3.org/2005/Atom\""
                + " xmlns:yt=\"http://gdata.youtube.com/schemas/2007\">"
                + "<content>" + comment + "</content>"
                + "</entry>";
            writer.write(xml.getBytes("UTF-8"));
            writer.flush();
            writer.close();                        
            BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String docxml = readerl.readLine();
            System.out.println("--docxml en post Comment----" + docxml);               
        }catch(Exception ex){
            //System.out.println("ERROR" + ex.toString());            
            log.error(ex);
        }
    }
    
     private void doCommentComment(HttpServletRequest request) {
        String videoId = request.getParameter("videoId");
        String commentId = request.getParameter("commentId");
        String objUri = request.getParameter("suri");
        String comment = request.getParameter("commentComment");
        System.out.println("videoId:" + videoId);
        System.out.println("commentId:" + commentId);
        System.out.println("objUri:" + objUri);
        System.out.println("comment:" + comment);
        

        if ((videoId == null || videoId.isEmpty()) || (comment == null || comment.isEmpty())
                || (objUri == null || objUri.isEmpty()) || ( commentId == null || commentId.isEmpty())) {
            log.error("Problema ejecutando el posteo del comentario hacia un comentario");
            return;
        }

        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        if (!semanticYoutube.validateToken()) {
            log.error("Unable to update the access token inside post Comment!");
            return;
        }

        String urlComment = "http://gdata.youtube.com/feeds/api/videos/" + videoId + "/comments";
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlComment);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Host", "gdata.youtube.com");
            conn.setRequestProperty("Content-Type", "application/atom+xml");
            conn.setRequestProperty("Authorization", "Bearer " + semanticYoutube.getAccessToken());
            conn.setRequestProperty("GData-Version", "2");
            conn.setRequestProperty("X-GData-Key", "key=" + semanticYoutube.getDeveloperKey());

            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            String xml = "<?xml version=\"1.0\"?>"
                    + "<entry xmlns=\"http://www.w3.org/2005/Atom\""
                    + " xmlns:yt=\"http://gdata.youtube.com/schemas/2007\">"
                    + "<link rel=\"http://gdata.youtube.com/schemas/2007#in-reply-to\""
                    + " type=\"application/atom+xml\" "
                    + " href=\"http://gdata.youtube.com/feeds/api/videos/" + videoId + "/comments/" + commentId + "\" />   "
                    + "<content>" + comment + "</content>"
                    + "</entry>";
            System.out.println("XML" + xml);
            writer.write(xml.getBytes("UTF-8"));
            writer.flush();
            writer.close();
            BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String docxml = readerl.readLine();
            System.out.println("--docxml en post Comment----" + docxml);
        } catch (Exception ex) {
            System.out.println("ERROR" + ex.toString());
            //ex.printStackTrace();
        }
    }
     
     public static void doPrintVideo(HttpServletRequest request, HttpServletResponse response, 
             SWBParamRequest paramRequest, java.io.Writer out, String postURI, JSONObject video, boolean userCanDoEverything, boolean userCanRetopicMsg, boolean userCanRespondMsg, boolean userCanRemoveMsg) throws SWBResourceException, IOException {
        //out.write("VIDEO:" + video);
        HashMap<String, String> paramsComments = new HashMap<String, String>(3);
        paramsComments.put("v", "2");
        paramsComments.put("max-results", "5");
        paramsComments.put("start-index", "1");
        paramsComments.put("alt", "json");
        
        HashMap<String, String> paramsUsr = new HashMap<String, String>(3);
        paramsUsr.put("v", "2");
        paramsUsr.put("fields", "media:thumbnail");
        paramsUsr.put("alt", "json");
        
        String objUri = request.getParameter("suri");
        SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
        SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();        
        try{
            out.write("<div id=\"" + semanticYoutube.getId() +"/" + video.getString("id") + "\" class=\"timeline timelinefacebook\" dojoType=\"dojox.layout.ContentPane\">");
                //Username and story
                out.write("<div id=\"" + semanticYoutube.getId() + "/" + video.getString("id") + "/detail" + "\">");
                out.write("<p>");
                out.write(video.getString("title"));
                out.write("</p>");

                out.write("<div class=\"timelineimg\">");
                out.write(" <span>");
                String imgPath = "";
                if(video.has("thumbnail")){
                    if(video.getJSONObject("thumbnail").has("hqDefault")){
                        imgPath = video.getJSONObject("thumbnail").getString("hqDefault");
                    }else if(video.getJSONObject("thumbnail").has("sqDefault")){
                        imgPath = video.getJSONObject("thumbnail").getString("sqDefault");
                    }
                }

                out.write("      <span id=\"img" + semanticYoutube.getId() + video.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    out.write("      <a href=\"#\" onclick=\"showDialog('"+ paramRequest.getRenderUrl().setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode("http://www.youtube.com/v/" +video.getString("id"), "UTF-8")) +
                            "','" + video.getString("title") + "'); return false;\"><img src=\"" + imgPath + "\" style=\"position: relative;\" onerror=\"this.src ='" + imgPath + "'\" onload=\"imageLoad(" + "this, 'img" + semanticYoutube.getId() + video.getString("id") + "');\"/></a>");
                    out.write("      </span>");
                out.write(" </span>");
                
                out.write("<p class=\"imgtitle\">");
                out.write(  video.getString("title"));
                out.write("</p>");
                
                out.write("<p class =\"imgdesc\">");
                out.write( video.isNull("description") ?  "&nbsp;" : video.getString("description").replace("\n", "</br>"));
                out.write("</p>");
                out.write("</div>");//End First section
                out.write("</div>");
                
                out.write("<div class=\"clear\"></div>");//Clear
                
                
                //With the changes of November 2013
                //I can not longer request the comments of a private video
                boolean isPrivate = false;
                if(!video.isNull("privacy") && video.getString("privacy").equalsIgnoreCase("private")){
                    isPrivate = true;
                }
                
                //Comments,start
                String ytComments = "";
                if(!video.isNull("commentCount") && video.getInt("commentCount")>0 && !isPrivate){
                    //System.out.println("URL for comments:" );
                    //System.out.println("token:" + semanticYoutube.getAccessToken());
                    ytComments= getRequest(paramsComments, "https://gdata.youtube.com/feeds/api/videos/" + video.getString("id") + "/comments",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", semanticYoutube.getAccessToken());
                    JSONObject jsonComments = new JSONObject(ytComments);
                    JSONArray arrayComments = null;
                    if(!jsonComments.isNull("feed")){
                        if(!jsonComments.getJSONObject("feed").isNull("entry")){
                            arrayComments = jsonComments.getJSONObject("feed").getJSONArray("entry");
                        }
                    }                    
                    if(arrayComments != null && arrayComments.length() > 0){
                        out.write("<ul id=\"" + semanticYoutube.getId() + video.getString("id") + "/comments\">");
                        int totalComments = 0;
                        for(int c = 0; c < arrayComments.length(); c++){                            
                            totalComments++;
                            JSONObject comment = arrayComments.getJSONObject(c);
                            JSONObject usrCommentProfile = null;
                            if(!comment.isNull("author")){
                                if(!comment.getJSONArray("author").getJSONObject(0).isNull("yt$userId")){
                                    if(comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t").equalsIgnoreCase("__NO_YOUTUBE_ACCOUNT__")){
                                        continue;
                                    }
                                    String commentProfile = getRequest(paramsUsr, "http://gdata.youtube.com/feeds/api/users/" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t"),
                                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
                                    usrCommentProfile = new JSONObject(commentProfile);
                                    
                                }
                            }
                            out.write("<li>");
                            out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\"><img src=\"" + usrCommentProfile.getJSONObject("entry").getJSONObject("media$thumbnail").getString("url") + "\" width=\"50\" height=\"50\"/></a>");

                            out.write("<p>");
                            out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t")) + "','" + paramRequest.getLocaleString("viewProfile") + "'); return false;\">" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t") + "</a>:");
                            out.write(       comment.getJSONObject("content").getString("$t").replace("\n", "</br>"));
                            out.write("</p>");

                            out.write("<p class=\"timelinedate\">");
                            //out.write("<span dojoType=\"dojox.layout.ContentPane\">");
                            out.write("<span class=\"inline\">");

                            Date date = formatter.parse(comment.getJSONObject("published").getString("$t"));
                            out.write("<em>" + humanFriendlyDate(date, paramRequest) +  "</em>");
                            out.write("</span>");
                            String comentarioId = comment.getJSONObject("id").getString("$t");
                            out.write("   <span class=\"inline\">");
                            out.write(" <a href=\"\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("commentComment").setParameter("suri", objUri).setParameter("videoId", video.getString("id")).setParameter("commentId", comentarioId.substring(comentarioId.indexOf("comment") + 8)) + "','Comment to " + comment.getJSONObject("content").getString("$t").replace("\n", "</br>") + "');return false;\">Comentar</a>");
                            out.write("   </span>");
                            out.write("</p>");
                            out.write("</li>");
                        }
                        if(!video.isNull("commentCount") && video.getInt("commentCount") > DEFAULT_VIDEO_COMMENTS && totalComments == DEFAULT_VIDEO_COMMENTS){//Link to get more comments
                            //getMoreComments(video.getString("id"), out);
                            out.write("<li class=\"timelinemore\">");
                            out.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + paramRequest.getRenderUrl().setMode("getMoreComments").setParameter("videoId", video.getString("id")).setParameter("startIndex", totalComments + "").setParameter("totalComments",video.getInt("commentCount")+"").setParameter("suri", objUri)
                                    + "','" + semanticYoutube.getId() + video.getString("id") +"/comments', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild( this.parentNode.parentNode );}catch(noe){}; return false;\"><span>+</span>" + paramRequest.getLocaleString("moreComments") +"</a></label>");
                            out.write("</li>");
                        }
                        out.write("</ul>");
                    }
                }
                //Comments

                out.write("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");//timelineresume
                out.write("<span id=\"" + semanticYoutube.getId() + video.getString("id") + INFORMATION + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                Date date = formatter.parse(video.getString("uploaded"));
                out.write("<em>" + humanFriendlyDate(date, paramRequest) + "</em>");
                
                
                if(video.has("viewCount")){
                    out.write(paramRequest.getLocaleString("views") + ":" + video.getInt("viewCount") + " ");
                }
                out.write(" <strong><span> " + paramRequest.getLocaleString("likes") + ": </span>");
                if(video.has("likeCount")){
                    out.write(video.getInt("likeCount") +" ");           
                }else{
                    out.write("0 ");
                }
                
                out.write(" " + paramRequest.getLocaleString("dislikes") + ": ");
                if(video.has("likeCount") && video.has("ratingCount")){
                    out.write(video.getInt("ratingCount") - video.getInt("likeCount") + " ");
                }else{
                    out.write("0 ");
                }
                
                if(video.has("favoriteCount")){
                    out.write(" " + paramRequest.getLocaleString("favorites") + ": " + video.getInt("favoriteCount"));
                }
                
                out.write("</strong>");
                out.write("</span>");
                
                if(userCanRespondMsg || userCanDoEverything){
                    out.write("   <span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                    out.write(" <a class=\"answ\" href=\"#\" title=\"Comentar\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("commentVideo").setParameter("suri", objUri).setParameter("videoId", video.getString("id")) + "','Comment to " + video.getString("title") + "');return false;\"></a>  ");                    
                    out.write("   </span>");
                }

                postURI = null;
                PostIn post = PostIn.getPostInbySocialMsgId(model, video.getString("id"));
                if(post != null){
                    postURI = post.getURI();
                }
                
                if(userCanRetopicMsg || userCanDoEverything){
                    out.write("   <span class=\"inline\" id=\"" + semanticYoutube.getId() + video.getString("id") + TOPIC  + "\" dojoType=\"dojox.layout.ContentPane\">");
                    if(userCanRetopicMsg || userCanDoEverything){
                        if(postURI != null){//If post already exists
                            SWBResourceURL clasifybyTopic = paramRequest.getRenderUrl().setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("videoId", video.getString("id")).setParameter("postUri", postURI).setParameter("suri", objUri);
                            out.write("<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("reclassify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                + paramRequest.getLocaleString("reclassify") + "'); return false;\"></a>");
                        }else{//If posts does not exists 
                            SWBResourceURL clasifybyTopic = paramRequest.getRenderUrl().setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", video.getString("id")).setParameter("postUri", postURI).setParameter("suri", objUri);
                            out.write("<a href=\"#\" class=\"clasifica\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("classify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                + paramRequest.getLocaleString("classify") +"'); return false;\"></a>");
                        }
                    }else{
                        out.write("&nbsp;");
                    }
                    out.write("   </span>");
                }
                
                out.write("   <span id=\"" + semanticYoutube.getId() + video.getString("id") +  LIKE + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.write("<a href=\"#\" class=\"like\" title=\"" + paramRequest.getLocaleString("like") + "\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + paramRequest.getActionUrl().setAction("doLike").setParameter("suri", objUri).setParameter("action", "like").setParameter("videoId", video.getString("id")) + "','" + semanticYoutube.getId() +  video.getString("id") + INFORMATION + "'); return false;\"></a>");
                out.write("   </span>");

                if(userCanRetopicMsg || userCanRemoveMsg || userCanDoEverything){
                    out.write("   <span id=\"" + semanticYoutube.getId() + video.getString("id") +  "/edit" + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                    SWBResourceURL editVideo = paramRequest.getRenderUrl().setMode("editVideo").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("videoId", video.getString("id")).setParameter("suri", objUri);
                    out.write("<a href=\"#\" class=\"editarYoutube\" title=\"" + paramRequest.getLocaleString("edit") + "\" onclick=\"showDialog('" + editVideo + "','"
                        + paramRequest.getLocaleString("edit") +"'); return false;\"></a>");
                    out.write("   </span>");
                }
                if(userCanRemoveMsg || userCanDoEverything){
                    out.write("   <span id=\"" + semanticYoutube.getId() + video.getString("id") +  "/delete" + "\" class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                    out.write("<a href=\"#\" class=\"eliminarYoutube\" title=\"" + paramRequest.getLocaleString("deleteVideo") +"\"onclick=\"if(confirm('" + paramRequest.getLocaleString("confirmDelete") + "')){ postSocialHtml('" + paramRequest.getActionUrl().setAction("doDeleteVideo").setParameter("suri", objUri).setParameter("videoId", video.getString("id")) + "','" + semanticYoutube.getId() + "/" +  video.getString("id") + "');} return false;\"></a>");
                    out.write("   </span>");
                }
                
                out.write("</div>");//timelineresume
                out.write("</div>");
        }catch(Exception e){
            log.error("Problema imprimiendo video ", e);
        }
         
     }
     
    public static String getRequest(Map<String, String> params, String url,
            String userAgent, String accessToken) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" +  paramString);       
        //System.out.println("URL:" +  serverUrl);
        
        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;
       
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);                
            }
            ///Validate if i am looking for the default user or another
            if(accessToken != null){
                conex.setRequestProperty("Authorization", "Bearer " + accessToken);
            }
            ///
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod("GET");
            conex.setDoOutput(true);
            conex.connect();
            in = conex.getInputStream();
            response = getResponse(in);
            //System.out.println("RESPONSE:" + response);
                        
        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                response = getResponse(conex.getErrorStream());
                //System.out.println("\n\n\nERROR:" +   response);
                log.error("ERROR creating connection:", ioe);
            }
            //ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }
    
    public void doGetMoreVideos(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try{
            PrintWriter out = response.getWriter();
            String maxVideoId = request.getParameter("maxVideoId");
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("v", "2");
            params.put("alt","jsonc");
            params.put("start-index",Integer.parseInt(maxVideoId)+1+"");
            String objUri = (String)request.getParameter("suri");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
            int videosInChannel = 0 ;
            if(!semanticYoutube.validateToken()){//If was unable to refresh the token
                //System.out.println("unable to refresh the token!");
                log.error("Unable to refresh the access token!");
                return;
            }
            //Validate token from youtube and pass it as param.
            String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/default/uploads",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", semanticYoutube.getAccessToken());

            JSONObject jsonResponse = new JSONObject(ytResponse);
            JSONArray videosArray = null;
            if(jsonResponse.has("data")){
                if(jsonResponse.getJSONObject("data").has("items")){
                    videosArray = jsonResponse.getJSONObject("data").getJSONArray("items");
                }
                if(jsonResponse.getJSONObject("data").has("totalItems")){
                    videosInChannel = jsonResponse.getJSONObject("data").getInt("totalItems");
                }
            }


            HashMap<String, String> paramsComments = new HashMap<String, String>(3);
            paramsComments.put("v", "2");
            paramsComments.put("max-results", "5");
            paramsComments.put("start-index", "1");
            paramsComments.put("alt", "json");

            HashMap<String, String> paramsUsr = new HashMap<String, String>(3);
            paramsUsr.put("v", "2");
            paramsUsr.put("fields", "media:thumbnail");
            paramsUsr.put("alt", "json");

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

            SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());                
            String postURI = null;
            org.semanticwb.model.User user = paramRequest.getUser();
            HashMap<String, SemanticProperty> mapa = new HashMap<String, SemanticProperty>();
            Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialUserExtAttributes").listProperties();
            while (list.hasNext()) {
                SemanticProperty sp = list.next();
                mapa.put(sp.getName(),sp);
            }
            boolean userCanRetopicMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanReTopicMsg"))).booleanValue();                
            boolean userCanRespondMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRespondMsg"))).booleanValue();
            boolean userCanRemoveMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRemoveMsg"))).booleanValue();

            UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");                            
            //THE INFO OF THE USER SHOULD BE DISPLAYED AT TOP
            int totalVideos = 0;
            
            if(videosArray != null){
                for(int i = 0; i < videosArray.length(); i++ ){
                    doPrintVideo(request, response, paramRequest, out, postURI, videosArray.getJSONObject(i), user.hasUserGroup(userSuperAdminGrp), userCanRetopicMsg, userCanRespondMsg, userCanRemoveMsg);
                    totalVideos++;
                }
                //System.out.println("Videos recibidos:" + totalVideos);

                if(totalVideos + Integer.parseInt(maxVideoId) < videosInChannel){
                    out.write("<div align=\"center\">");
                    out.write("<label id=\"" + objUri + "/moreVideosLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + paramRequest.getRenderUrl().setMode("getMoreVideos").setParameter("maxVideoId", (totalVideos + Integer.parseInt(maxVideoId))+"").setParameter("suri", objUri) + "','" + objUri + "/getMoreVideos', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild( this.parentNode.parentNode );}catch(noe){}; return false;\">More Videos</a></label>");
                    out.write("</div>");
                }
            }
        }catch(Exception e){
            log.error("Problem getting more videos", e);
        }
    }

    public static CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer
                = new StringBuilder(64);
	boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries ) {
            if (notFirst) {
                buffer.append(delimiter);
            } else {
                notFirst = true;
            }
            CharSequence value = entry.getValue();
            buffer.append(entry.getKey());
            buffer.append(equals);
            buffer.append(doEncode ? encode(value) : value);
        }
        return buffer;
    }
    
    /**
     * Codifica el valor de {@code target} de acuerdo al c&oacute;digo de caracteres UTF-8
     * @param target representa el texto a codificar
     * @return un {@code String} que representa el valor de {@code target} de acuerdo al c&oacute;digo de caracteres UTF-8
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en la codificaci&oacute;n a UTF-8
     */
    private static String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }
    
    public static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }
    
    public static void close( Closeable c ) {
        if ( c != null ) {
            try {
                c.close();
            }
            catch ( IOException ex ) {             
            }
        }
    }
    
    public static String humanFriendlyDate(Date created, SWBParamRequest paramRequest){
        Date today = new Date();
        Long duration = today.getTime() - created.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        String date = "";
        try{
            if (duration < second * 7) {//Less than 7 seconds
                date = paramRequest.getLocaleString("rightNow");
            }else if (duration < minute) {
                int n = (int) Math.floor(duration / second);
                date = n + " " + paramRequest.getLocaleString("secondsAgo");
            }else if (duration < minute * 2) {//Less than 2 minutes
                date = paramRequest.getLocaleString("about") + " 1 " + paramRequest.getLocaleString("minuteAgo");
            }else if (duration < hour) {
                int n = (int) Math.floor(duration / minute);
                date = n + " " + paramRequest.getLocaleString("minutesAgo");
            }else if (duration < hour * 2) {//Less than 1 hour
                date = paramRequest.getLocaleString("about") + " 1 " + paramRequest.getLocaleString("hourAgo");
            }else if (duration < day) {
                int n = (int) Math.floor(duration / hour);
                date = n + " " + paramRequest.getLocaleString("hoursAgo");
            }else  if (duration > day && duration < day * 2) {
                date = paramRequest.getLocaleString("yesterday");
            }else{
                int n = (int) Math.floor(duration / day);
                date = n + " " + paramRequest.getLocaleString("daysAgo");
            }
        }catch(Exception e){
            log.error("Problem found computing time of post. ", e);
        }        
        return date;
    }
    
    
    public static String getFullVideoFromId(String id, String accessToken){
        HashMap<String, String> params = new HashMap<String, String>(3);    
        params.put("v", "2");
        params.put("alt","json");
    
        String response = null;
        try{
         response = getRequest(params, "https://gdata.youtube.com/feeds/api/videos/" + id,
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", accessToken);

        }catch(Exception e){
            //System.out.println("Error getting video information"  + e.getMessage());
            log.error("Error getting video information", e);
        }
        return response;
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
}
