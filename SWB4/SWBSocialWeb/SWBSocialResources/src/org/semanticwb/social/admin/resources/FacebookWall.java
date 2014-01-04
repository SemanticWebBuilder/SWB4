/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
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
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.regex.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.Facebook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Message;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.Photo;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.Post;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.SocialUserExtAttributes;
import org.semanticwb.social.Video;
import org.semanticwb.social.VideoIn;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SocialLoader;

/**
 *
 * @author francisco.jimenez
 */
public class FacebookWall extends GenericResource {

    private static Logger log = SWBUtils.getLogger(FacebookWall.class);
    //public static Facebook facebook;
    /*variables used to define the id of '<div>' for the fields of information, favorite and reweet.
     Each link is in a different '<div>' and it's updated individually*/
    public static String INFORMATION = "/inf";
    public static String LIKE = "/lik";
    public static String REPLY = "/rep";
    public static String TOPIC = "/topic";
    /*Additionally every div has a suffix to identify if the status is inside the tab of
     newsfeed, wall, pictures */
    public static String NEWS_FEED_TAB = "/newsfeed";
    public static String WALL_TAB = "/wall";
    public static String PICTURES_TAB = "/media";
    public static String VIDEOS_TAB = "/videos";
    public static String CONEXION = "/conexion";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String objUri = (String) request.getParameter("suri");
        System.out.println("SURI:" + objUri);
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebookBean = (Facebook) semanticObject.createGenericInstance();
        String contentTabId = request.getParameter("contentTabId");

        if (contentTabId == null) {
            String jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookTabs.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error loading the Twitter Tabs ", e);
            }
            return;
        }

        String jspResponse = "";
        if (contentTabId != null && contentTabId.equals(NEWS_FEED_TAB)) {
            jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookNewsFeed.jsp";
        } else if (contentTabId != null && contentTabId.equals(WALL_TAB)) {
            jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookWall.jsp";
        } else if (contentTabId != null && contentTabId.equals(PICTURES_TAB)) {
            jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookPictures.jsp";
        } else if (contentTabId != null && contentTabId.equals(VIDEOS_TAB)) {
            jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookVideos.jsp";
        } else if (contentTabId != null && contentTabId.equals(CONEXION)) {
            jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookConexion.jsp";
        }

        //HttpSession session = request.getSession(true);
        //session.setAttribute("since", "0");// since param used to get newer post
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("facebookBean", facebookBean);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher", e);
        }
    }

    /**
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por
     * alg&uacute;n m&eacute;todo en particular
     *
     * @param params contiene los par&aacute;metros a enviar a Facebook para
     * realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea
     * interactuar
     * @param userAgent indica el navegador de Internet utilizado en la
     * petici&oacute;n a realizar
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido
     * por Facebook para realizar una operaci&oacute;n, como:
     * {@literal POST}, {@literal DELETE} o {@literal GET}
     * @return un {@code String} que representa la respuesta generada por el
     * grafo de Facebook
     * @throws IOException en caso de que se produzca un error al generar la
     * petici&oacute;n o recibir la respuesta del grafo de Facebook
     */
    public static String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" + paramString);

        //URL serverUrl = new URL(url);
        System.out.println("URL:" + serverUrl);
        //System.out.println("paramString:" + paramString);

        HttpURLConnection conex = null;
        OutputStream out = null;
        InputStream in = null;
        String response = null;

        if (method == null) {
            method = "POST";
        }
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod(method);
            conex.setDoOutput(true);
            conex.connect();

            //System.out.println("CONNECT:" + conex);
            //   out = conex.getOutputStream();
            //   out.write(paramString.toString().getBytes("UTF-8"));
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                //System.out.println("ERROR:" +   getResponse(conex.getErrorStream()));
                response = getResponse(conex.getErrorStream());
                log.error("\n\nERROR:" + response);
            }
            //ioe.printStackTrace();
        } finally {
            close(in);
            //close(out);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    public static String getRequest(Map<String, String> params, String url,
            String userAgent) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" + paramString);
        System.out.println("URL:" + serverUrl);

        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;

        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
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
                log.error("\n\n\nERROR:" + response);
            }
            ioe.printStackTrace();
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

    public static CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer = new StringBuilder(64);
        boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries) {
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
     * Codifica el valor de {@code target} de acuerdo al c&oacute;digo de
     * caracteres UTF-8
     *
     * @param target representa el texto a codificar
     * @return un {@code String} que representa el valor de {@code target} de
     * acuerdo al c&oacute;digo de caracteres UTF-8
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en
     * la codificaci&oacute;n a UTF-8
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

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
                log.error("Error at closing object: " + c.getClass().getName(),
                        ex);
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action.equals("doLike")) {//Like works for post from your friends and for posts from users you gave likes
            System.out.println("LIKE-Start");
            String objUri = (String) request.getParameter("suri");
            String postID = (String) request.getParameter("postID");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook) semanticObject.createGenericInstance();
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + postID + "/likes",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
            if (!fbResponse.equals("true")) {//If the response is not true, there was an error
                try {
                    JSONObject likeResponse = new JSONObject(fbResponse);
                    if (likeResponse.has("error")) {
                        response.setRenderParameter("error", likeResponse.getJSONObject("error").getString("message"));
                    }
                } catch (JSONException ex) {
                    log.error("Error doing like action " + ex);
                }
            }
            //System.out.println("Response: " + fbResponse);
            response.setRenderParameter("postID", postID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            response.setRenderParameter("liked", "ok");
            System.out.println("LIKE-End");
            response.setMode("likeSent"); //show Like Message and update div

        } else if (action.equals("doUnlike")) {    //If you liked a post from your friends you can do unlike simply
            System.out.println("UNLIKE-Start"); //If you liked a post from a user you gave like, you cannot give unlike
            String objUri = (String) request.getParameter("suri");
            String postID = (String) request.getParameter("postID");

            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook) semanticObject.createGenericInstance();
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            System.out.println("\n\nComment ID:" + postID.substring(postID.indexOf('_') + 1));
            String fbResponse = "";
            fbResponse = postRequest(params, "https://graph.facebook.com/" + postID + "/likes",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "DELETE");
            if (!fbResponse.equals("true")) {//If the response is not true, there was an error
                System.out.println("ERROR TRYING TO UNLIKE ID:" + fbResponse);
                try {
                    JSONObject likeResponse = new JSONObject(fbResponse);
                    if (likeResponse.has("error")) {
                        response.setRenderParameter("error", likeResponse.getJSONObject("error").getString("message"));
                    }
                } catch (JSONException ex) {
                    log.error("Error doing like action " + ex);
                }
            }

            response.setRenderParameter("postID", postID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            response.setRenderParameter("unliked", "ok");
            System.out.println("UNLIKE-End");
            response.setMode("unlikeSent"); //show Like Message and update div
        } else if (action.equals("sendReply")) {
            try {
                String answer = request.getParameter("replyText");
                System.out.println("Answer Text:" + answer);
                System.out.println("Reply-Start"); //If you liked a post from a user you gave like, you cannot give unlike
                String objUri = (String) request.getParameter("suri");
                String postID = (String) request.getParameter("postID");
                SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
                Facebook facebook = (Facebook) semanticObject.createGenericInstance();
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("access_token", facebook.getAccessToken());
                params.put("message", answer);
                System.out.println("\n\nComment ID:" + postID);
                String fbResponse = postRequest(params, "https://graph.facebook.com/" + postID + "/comments",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                System.out.println("Response: " + fbResponse);
                response.setRenderParameter("postID", postID);
                response.setRenderParameter("suri", objUri);
                System.out.println("Reply-End");
                //twitter.updateStatus(new StatusUpdate(answer).inReplyToStatusId(id));
                response.setRenderParameter("repliedPost", "ok");
                response.setMode("postSent");
            } catch (Exception ex) {
                log.error("Error when trying to reply ", ex);
            }//**ini
        } else if (action.equals("setSocialTopic")) {
            System.out.println("SOCIAL NETWORK IN setSocialTopic: " + request.getParameter("suri"));

            Facebook facebook = null;
            String idPost = request.getParameter("id");
            String objUri = request.getParameter("suri");
            try {
                facebook = (Facebook) SemanticObject.getSemanticObject(objUri).getGenericInstance();
            } catch (Exception e) {
                log.error("Error getting the SocialNetwork " + e);
                return;
            }

            SocialNetwork socialNetwork = null;
            try {
                socialNetwork = (SocialNetwork) SemanticObject.getSemanticObject(objUri).getGenericInstance();
            } catch (Exception e) {
                log.error("Error getting the SocialNetwork " + e);
                return;
            }
            SocialNetworkUser socialNetUser = null;

            SWBModel model = WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());

            try {

                JSONObject postData = getPostFromFullId(idPost, facebook);
                System.out.println("This is the post: " + postData);
                socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(postData.getJSONObject("from").getString("id"), socialNetwork, model);

                if (socialNetUser == null) {
                    System.out.println("\n\nEL USUARIO NO EXISTE");
                } else {
                    System.out.println("\n\nEL USUARIO EXISTE: " + socialNetUser.getSnu_id());
                }

                if (socialNetUser == null) {
                    socialNetUser = SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                    socialNetUser.setSnu_id(postData.getJSONObject("from").getString("id"));
                    socialNetUser.setSnu_name(postData.getJSONObject("from").getString("name"));
                    socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());
                    socialNetUser.setSnu_photoUrl("https://graph.facebook.com/" + postData.getJSONObject("from").getString("id") + "/picture?width=150&height=150");
                    socialNetUser.setCreated(new Date());
                    socialNetUser.setFollowers(0);
                    socialNetUser.setFriends(0);
                    System.out.println("YA SE CREO O SOBRE ESC el usuario");
                }
                PostIn postIn = PostIn.getPostInbySocialMsgId(model, postData.getString("id"));
                if (postIn == null) {
                    String postType = "";
                    if (postData.has("type")) {
                        postType = postData.getString("type");
                    } else if (postData.has("picture") && postData.has("name") && postData.has("link") && postData.has("description")) {
                        postType = "link";
                    }
                    String message = "";
                    String story = "";

                    if (postType.equals("status") || postType.equals("link") || postType.equals("checkin")) {
                        postIn = MessageIn.ClassMgr.createMessageIn(model);
                        postIn.setPi_type(SWBSocialUtil.POST_TYPE_MESSAGE);
                        if (postType.equals("status")) {
                            if (!postData.isNull("message")) {
                                message = postData.getString("message");
                            } else if (!postData.isNull("story")) {
                                story = (!postData.isNull("story")) ? postData.getString("story") : "";
                                story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                                /*
                                 if(story.contains("likes a photo")){
                                 photoLike = getPostFromId(postData.getString("id"), "id,from,name,name_tags,picture,source,link,tags", facebook);
                                 isAPhotoLike = true;
                                 //System.out.println("THE RECOVERED OBJECT:" + jSONObject);
                                 }else if(story.contains("likes a link")){
                                 linkLike = getPostFromId(postData.getString("id"), "id,from,name,picture,link,tags,message", facebook);
                                 System.out.println("\n\n\nLINKED LIKED:" +  linkLike);
                                 isALinkLike = true;
                                 }
                                 if(story.contains("likes a status")){
                                 statusLike = getPostFromId(postData.getString("id"), null, facebook);
                                 isAStatusLike = true;
                                 System.out.println("\n\n\nSTATUS LIKED:" +  statusLike);
                                 if(statusLike.has("message")){
                                 message = statusLike.getString("message");
                                 }
                                 }*/
                            }
                            if (!message.isEmpty()) {
                                postIn.setMsg_Text(message);
                            } else if (!story.isEmpty()) {
                                postIn.setMsg_Text(story);
                            } else {
                                postIn.setMsg_Text("");
                            }

                            System.out.println("********************STATUS guardado OK");
                        } else if (postType.equals("link")) {
                            if (!postData.isNull("story")) {
                                story = (!postData.isNull("story")) ? postData.getString("story") : "";
                                story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                            }
                            if (!postData.isNull("message")) {
                                message = postData.getString("message");
                            }
                            /*
                             if(postData.has("link") && postData.has("name")){
                             System.out.println("<a href=\"" + postData.getString("link") + "\" target=\"_blank\">" + postData.getString("name") + "</a>");
                             postIn.setTitle("<a href=\"" + postData.getString("link") + "\" target=\"_blank\">" + postData.getString("name") + "</a>");//Link o Title
                             if(!story.isEmpty())
                             story = story + ": " + "<a href=\"" + postData.getString("link") + "\" target=\"_blank\">" + postData.getString("name") + "</a>";                            
                             if(!message.isEmpty())
                             message = message + ":" + "<a href=\"" + postData.getString("link") + "\" target=\"_blank\">" + postData.getString("name") + "</a>";

                             }*/
                            /*
                             if(postData.has("description")){
                             postIn.setDescription(postData.getString("description"));
                             }*/

                            if (!message.isEmpty()) {
                                postIn.setMsg_Text(message);
                            } else if (!story.isEmpty()) {
                                postIn.setMsg_Text(story);
                            } else {
                                postIn.setMsg_Text("");
                            }
                            System.out.println("********************LINK guardado OK");
                        }

                        //Information of post IN
                        postIn.setSocialNetMsgId(postData.getString("id"));
                        postIn.setPostInSocialNetwork(socialNetwork);
                        postIn.setPostInStream(null);
                        postIn.setPostInSocialNetworkUser(socialNetUser);
                        Calendar calendario = Calendar.getInstance();
                        postIn.setPi_created(calendario.getTime());
                        //Sets the social topic
                        if (request.getParameter("newSocialTopic").equals("none")) {
                            postIn.setSocialTopic(null);
                        } else {
                            SemanticObject semObjSocialTopic = SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                            if (semObjSocialTopic != null) {
                                SocialTopic socialTopic = (SocialTopic) semObjSocialTopic.createGenericInstance();
                                postIn.setSocialTopic(socialTopic);//Asigns socialTipic
                            }
                        }
                        response.setRenderParameter("postUri", postIn.getURI());
                    } else if (postType.equals("video") || postType.equals("swf")) {
                        postIn = VideoIn.ClassMgr.createVideoIn(model);
                        postIn.setPi_type(SWBSocialUtil.POST_TYPE_VIDEO);
                        //Get message and/or story
                        if (!postData.isNull("message")) {
                            message = postData.getString("message");
                        } else if (!postData.isNull("story")) {
                            story = (!postData.isNull("story")) ? postData.getString("story") : "";
                            story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                        }

                        /*if(postData.has("source") && postData.has("name")){
                         if(!story.isEmpty())
                         story = story + ": " + "<a href=\"" + postData.getString("source") + "\" target=\"_blank\">" + postData.getString("name") + "</a>";
                         if(!message.isEmpty())
                         message = message + ": " + "<a href=\"" + postData.getString("source") + "\" target=\"_blank\">" + postData.getString("name") + "</a>";
                         }else if(postData.has("source") && !postData.has("name")){
                         if(!story.isEmpty())
                         story = story + ": " + "<a href=\"" + postData.getString("source") + "\" target=\"_blank\">View video</a>";
                         if(!message.isEmpty())
                         message = message + ": " + "<a href=\"" + postData.getString("source") + "\" target=\"_blank\">View video</a>";
                         }*/
                        /*
                         if(postData.has("description")){
                         postIn.setDescription(postData.getString("description"));
                         }*/

                        System.out.println("THE MESSAGE******\n" + message);
                        System.out.println("THE STORY******\n" + story);
                        if (!message.isEmpty()) {
                            System.out.println("SETTING MESSAGE");
                            postIn.setMsg_Text(message);
                        } else if (!story.isEmpty()) {
                            System.out.println("SETTING STORY");
                            postIn.setMsg_Text(story);
                        } else {
                            System.out.println("SETTING ONLY THE NAME!!!!!");
                            postIn.setMsg_Text("<a href=\"" + postData.getString("source") + "\" target=\"_blank\">" + postData.getString("name") + "</a>");
                        }

                        if (postData.has("source")) {
                            System.out.println("Setting the VIDEO");
                            VideoIn videoIn = (VideoIn) postIn;
                            videoIn.setVideo(postData.getString("source"));
                        }

                        //Information of post IN
                        postIn.setSocialNetMsgId(postData.getString("id"));
                        postIn.setPostInSocialNetwork(socialNetwork);
                        postIn.setPostInStream(null);
                        postIn.setPostInSocialNetworkUser(socialNetUser);
                        Calendar calendario = Calendar.getInstance();
                        postIn.setPi_created(calendario.getTime());
                        //Sets the social topic
                        if (request.getParameter("newSocialTopic").equals("none")) {
                            postIn.setSocialTopic(null);
                        } else {
                            SemanticObject semObjSocialTopic = SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                            if (semObjSocialTopic != null) {
                                SocialTopic socialTopic = (SocialTopic) semObjSocialTopic.createGenericInstance();
                                postIn.setSocialTopic(socialTopic);//Asigns socialTipic
                            }
                        }
                        System.out.println("********************VIDEO guardado OK");
                        response.setRenderParameter("postUri", postIn.getURI());
                    } else if (postType.equals("photo")) {
                        postIn = PhotoIn.ClassMgr.createPhotoIn(model);
                        postIn.setPi_type(SWBSocialUtil.POST_TYPE_PHOTO);
                        //Get message and/or story
                        if (!postData.isNull("message")) {
                            message = postData.getString("message");
                        } else if (!postData.isNull("story")) {
                            story = (!postData.isNull("story")) ? postData.getString("story") : "";
                            story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                        }

                        /*if(postData.has("picture") && postData.has("name")){
                         if(!story.isEmpty())
                         story = story + ": " + "<a href=\"" + postData.getString("picture").replace("_s.", "_n.") + "\" target=\"_blank\">" + postData.getString("name") + "</a>";
                         if(!message.isEmpty())
                         message = message + ": " + "<a href=\"" + postData.getString("picture").replace("_s.", "_n.") + "\" target=\"_blank\">" + postData.getString("name") + "</a>";
                         }else if(postData.has("picture") && !postData.has("name")){
                         if(!story.isEmpty())
                         story = story + ": " + "<a href=\"" + postData.getString("picture").replace("_s.", "_n.") + "\" target=\"_blank\">View picture</a>";
                         if(!message.isEmpty())
                         message = message + ": " + "<a href=\"" + postData.getString("picture").replace("_s.", "_n.") + "\" target=\"_blank\">View picture</a>";
                         }*/


                        /*
                         if(postData.has("description")){
                         postIn.setDescription(postData.getString("description"));
                         }*/
                        System.out.println("\tMESSAGE:" + message);
                        System.out.println("\tSTORY:" + story);
                        if (!message.isEmpty()) {
                            postIn.setMsg_Text(message);
                        } else if (!story.isEmpty()) {
                            postIn.setMsg_Text(story);
                        } else {
                            postIn.setMsg_Text("");
                        }

                        if (postData.has("picture")) {
                            String photo = postData.getString("picture");
                            PhotoIn photoIn = (PhotoIn) postIn;
                            photoIn.addPhoto(photo);
                        }

                        //Information of post IN
                        postIn.setSocialNetMsgId(postData.getString("id"));
                        postIn.setPostInSocialNetwork(socialNetwork);
                        postIn.setPostInStream(null);
                        postIn.setPostInSocialNetworkUser(socialNetUser);
                        Calendar calendario = Calendar.getInstance();
                        postIn.setPi_created(calendario.getTime());
                        //Sets the social topic
                        if (request.getParameter("newSocialTopic").equals("none")) {
                            postIn.setSocialTopic(null);
                        } else {
                            SemanticObject semObjSocialTopic = SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                            if (semObjSocialTopic != null) {
                                SocialTopic socialTopic = (SocialTopic) semObjSocialTopic.createGenericInstance();
                                postIn.setSocialTopic(socialTopic);//Asigns socialTipic
                            }
                        }
                        System.out.println("********************STATUS guardado OK");
                        response.setRenderParameter("postUri", postIn.getURI());
                    }
                } else {
                    log.debug("The post exists, creating another response");
                    //Sets the social topic
                    if (request.getParameter("newSocialTopic").equals("none")) {
                        postIn.setSocialTopic(null);
                    } else {
                        SemanticObject semObjSocialTopic = SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                        if (semObjSocialTopic != null) {
                            SocialTopic socialTopic = (SocialTopic) semObjSocialTopic.createGenericInstance();
                            postIn.setSocialTopic(socialTopic);//Asigns socialTipic
                        }
                    }
                    response.setRenderParameter("postUri", postIn.getURI());
                }
            } catch (Exception e) {
                log.error("Error trying to setSocialTopic:", e);
            }

            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            response.setRenderParameter("id", idPost);
            response.setRenderParameter("fbid", facebook.getId());

            response.setMode("assignedPost");
        } else if (action.equals("changeSocialTopic")) {
            if (request.getParameter("postUri") != null && request.getParameter("newSocialTopic") != null) {
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
            }
            response.setMode("reAssignedPost");
        } else if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) {
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
                if (j > 0 && wsite != null) {
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

                response.setRenderParameter("repliedPost", "ok");
                response.setMode("postSent");
            }
        }
    }
//**fin

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        String currentTab = request.getParameter("currentTab");
        actionURL.setParameter("suri", request.getParameter("suri"));
        renderURL.setParameter("suri", request.getParameter("suri"));
        System.out.println("suri:" + request.getParameter("suri"));
        System.out.println("mode:" + mode);
        PrintWriter out = response.getWriter();
        if (mode != null && mode.equals("getMorePosts")) {//Gets older posts
            System.out.println("brings more POSTS - OLDER");
            doGetMorePosts(request, response, paramRequest);
        } else if (mode.equals("moreComments")) {
            doGetMoreComments(request, response, paramRequest);
        } else if (mode != null && mode.equals("newPostsAvailable")) {//Gets the number of new posts if available
            System.out.println("brings more posts - NUMBER OF NEW POSTS");
            doAskIfNewPosts(request, response, paramRequest);
        } else if (mode != null && mode.equals("doGetStreamUser")) {//Gets the new posts of the user and don't reload the page
            System.out.println("brings more posts - NEWER");
            doGetNewPosts(request, response, paramRequest);
        } else if (mode != null && mode.equals("doGetStreamPictures")) {//Gets the new posts of the user and don't reload the page
            System.out.println("brings more PICTURES - NEWER");
            doGetNewPictures(request, response, paramRequest);
        } else if (mode != null && mode.equals("getMorePictures")) {//Gets older pictures
            System.out.println("brings more PICTURES - OLDER");
            doGetMorePictures(request, response, paramRequest);
        } else if (mode != null && mode.equals("getMoreVideos")) {
            System.out.println("brings more VIDEOS - OLDER");
            doGetMoreVideos(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        } else if (mode != null && mode.equals("likeSent") || mode.equals("unlikeSent")) {//Displays updated data of liked/unliked status
            String postID = request.getParameter("postID");
            String objUri = request.getParameter("suri");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook) semanticObject.createGenericInstance();
            System.out.println("ENTRANDO AL LIKE SENT!");
            try {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("access_token", facebook.getAccessToken());
                params.put("fields", "id,from,likes.summary(true)");
                String fbResponse = getRequest(params, "https://graph.facebook.com/" + postID + "/",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

                System.out.println("Facebook response:" + fbResponse);
                JSONObject likeResp = new JSONObject(fbResponse);
                String facebookDate = likeResp.getString("created_time");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));

                Date likeTime = formatter.parse(facebookDate);

                out.write("<em>" + facebookHumanFriendlyDate(likeTime, paramRequest) + "</em>");
                //IMPRIMIR EL CREATED Y LOS NUEVOS LIKES
                boolean iLikedPost = false;
                if (likeResp.has("likes")) {
                    JSONArray likes = likeResp.getJSONObject("likes").getJSONArray("data");
                    out.write("<strong><span> Likes: </span>");
                    int postLikes = 0;
                    if (!likeResp.getJSONObject("likes").isNull("summary")) {
                        if (!likeResp.getJSONObject("likes").getJSONObject("summary").isNull("total_count")) {
                            postLikes = likeResp.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                        }
                    }
                    out.println(String.valueOf(postLikes));
                    for (int k = 0; k < likes.length(); k++) {
                        if (likes.getJSONObject(k).getString("id").equals(facebook.getFacebookUserId())) {
                            //My User id is in 'the likes' of this post
                            iLikedPost = true;
                        }
                    }

                    if ((likes.length() < postLikes) && (iLikedPost == false)) {
                        System.out.println("Look for postLike!!!");
                        params.clear();
                        params.put("q", "SELECT post_id FROM like WHERE user_id=me() AND post_id=\"" + postID + "\"");
                        params.put("access_token", facebook.getAccessToken());
                        String fbLike = null;

                        try {
                            fbLike = getRequest(params, "https://graph.facebook.com/fql",
                                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                            System.out.println("fbLike:" + fbLike);
                            JSONObject likeRespCounter = new JSONObject(fbLike);
                            if (likeRespCounter.has("data")) {
                                JSONArray likesArray = likeRespCounter.getJSONArray("data");
                                if (likesArray.length() == 1) {//There is one result, I liked this post
                                    iLikedPost = true;
                                }
                            }
                        } catch (Exception e) {
                            log.error("Error getting like information for Facebook post " + postID, e);
                        }
                    }
                    out.write("</strong>");
                } else {
                    out.write("<strong><span> Likes: </span>");
                    out.write("0");
                    out.write("</strong>");
                }
                //out.write("</strong>");

                //MOSTRAR SOLO EL LIKE                
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   var spanId = dijit.byId('" + facebook.getId() + postID + LIKE + currentTab + "');");
                String likeStatus = null;
                if (iLikedPost) {
                    likeStatus = " <a href=\"#\" class=\"nolike\" onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("postID", likeResp.getString("id")).setParameter("currentTab", currentTab) + "','" + facebook.getId() + postID + INFORMATION + currentTab + "');return false;" + "\"><span>" + paramRequest.getLocaleString("undoLike") + "</span></a>";
                    if (request.getParameter("error") != null) {
                        out.println("   showStatus('ERROR: " + request.getParameter("error") + "');");
                    } else {
                        out.println("   showStatus('Post liked successfully');");
                    }
                } else {
                    likeStatus = " <a href=\"#\" class=\"like\" onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("postID", likeResp.getString("id")).setParameter("currentTab", currentTab) + "','" + facebook.getId() + postID + INFORMATION + currentTab + "');return false;" + "\"><span>" + paramRequest.getLocaleString("like") + "</span></a>";
                    if (request.getParameter("error") != null) {
                        out.println("   showStatus('ERROR: " + request.getParameter("error") + "');");
                    } else {
                        out.println("   showStatus('Post unliked successfully');");
                    }
                }
                out.println("   spanId.attr('content', '" + likeStatus.replace("'", "\\'") + "');");
                out.println("</script>");
                out.println("</span>");
            } catch (Exception ex) {
                log.error("Error when trying to like/unlike post ", ex);
            }
        } else if (mode != null && mode.equals("replyPost")) {//Displays dialog to create post
            System.out.println("SOCIAL NETWORK IN setSocialTopic: " + request.getParameter("suri"));

            Facebook facebook = null;
            String idPost = request.getParameter("postID");
            String objUri = request.getParameter("suri");
            try {
                facebook = (Facebook) SemanticObject.getSemanticObject(objUri).getGenericInstance();
            } catch (Exception e) {
                log.error("Error getting the SocialNetwork " + e);
                return;
            }

            SocialNetwork socialNetwork = null;
            try {
                socialNetwork = (SocialNetwork) SemanticObject.getSemanticObject(objUri).getGenericInstance();
            } catch (Exception e) {
                log.error("Error getting the SocialNetwork " + e);
                return;
            }
            SocialNetworkUser socialNetUser = null;

            SWBModel model = WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
            PostIn postIn = null;
            try {
                postIn = PostIn.getPostInbySocialMsgId(model, idPost);

                if (postIn == null) {
                    JSONObject postData = getPostFromFullId(idPost, facebook);
                    System.out.println("This is the post: " + postData);
                    socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(postData.getJSONObject("from").getString("id"), socialNetwork, model);

                    if (socialNetUser == null) {
                        System.out.println("\n\nEL USUARIO NO EXISTE");
                    } else {
                        System.out.println("\n\nEL USUARIO EXISTE: " + socialNetUser.getSnu_id());
                    }

                    if (socialNetUser == null) {
                        socialNetUser = SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                        socialNetUser.setSnu_id(postData.getJSONObject("from").getString("id"));
                        socialNetUser.setSnu_name(postData.getJSONObject("from").getString("name"));
                        socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());
                        socialNetUser.setSnu_photoUrl("https://graph.facebook.com/" + postData.getJSONObject("from").getString("id") + "/picture?width=150&height=150");
                        socialNetUser.setCreated(new Date());
                        //TODO: Llamar al getUserInfoById
                        socialNetUser.setFollowers(0);
                        socialNetUser.setFriends(0);
                        System.out.println("YA SE CREO EL USUARIO!!!");
                    }

                    String postType = "";
                    if (postData.has("type")) {
                        postType = postData.getString("type");
                    } else if (postData.has("picture") && postData.has("name") && postData.has("link") && postData.has("description")) {
                        postType = "link";
                    }
                    String message = "";
                    String story = "";

                    if (postType.equals("status") || postType.equals("link") || postType.equals("checkin")) {
                        postIn = MessageIn.ClassMgr.createMessageIn(model);
                        postIn.setPi_type(SWBSocialUtil.POST_TYPE_MESSAGE);
                        if (postType.equals("status")) {
                            if (!postData.isNull("message")) {
                                message = postData.getString("message");
                            } else if (!postData.isNull("story")) {
                                story = (!postData.isNull("story")) ? postData.getString("story") : "";
                                story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                            }
                            if (!message.isEmpty()) {
                                postIn.setMsg_Text(message);
                            } else if (!story.isEmpty()) {
                                postIn.setMsg_Text(story);
                            } else {
                                postIn.setMsg_Text("");
                            }

                            System.out.println("********************STATUS guardado OK");
                        } else if (postType.equals("link")) {
                            if (!postData.isNull("story")) {
                                story = (!postData.isNull("story")) ? postData.getString("story") : "";
                                story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                            }
                            if (!postData.isNull("message")) {
                                message = postData.getString("message");
                            }

                            if (!message.isEmpty()) {
                                postIn.setMsg_Text(message);
                            } else if (!story.isEmpty()) {
                                postIn.setMsg_Text(story);
                            } else {
                                postIn.setMsg_Text("");
                            }
                            System.out.println("********************LINK guardado OK");
                        }

                        //Information of post IN
//                        postIn.setSocialNetMsgId(postData.getString("id"));
//                        postIn.setPostInSocialNetwork(socialNetwork);
//                        postIn.setPostInStream(null);
//                        postIn.setPostInSocialNetworkUser(socialNetUser);
//                        Calendar calendario = Calendar.getInstance();
//                        postIn.setPi_created(calendario.getTime());

                    } else if (postType.equals("video") || postType.equals("swf")) {
                        postIn = VideoIn.ClassMgr.createVideoIn(model);
                        postIn.setPi_type(SWBSocialUtil.POST_TYPE_VIDEO);
                        //Get message and/or story
                        if (!postData.isNull("message")) {
                            message = postData.getString("message");
                        } else if (!postData.isNull("story")) {
                            story = (!postData.isNull("story")) ? postData.getString("story") : "";
                            story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                        }

                        System.out.println("THE MESSAGE******\n" + message);
                        System.out.println("THE STORY******\n" + story);
                        if (!message.isEmpty()) {
                            System.out.println("SETTING MESSAGE");
                            postIn.setMsg_Text(message);
                        } else if (!story.isEmpty()) {
                            System.out.println("SETTING STORY");
                            postIn.setMsg_Text(story);
                        } else {
                            System.out.println("SETTING ONLY THE NAME!!!!!");
                            postIn.setMsg_Text("<a href=\"" + postData.getString("source") + "\" target=\"_blank\">" + postData.getString("name") + "</a>");
                        }

                        if (postData.has("source")) {
                            System.out.println("Setting the VIDEO");
                            VideoIn videoIn = (VideoIn) postIn;
                            videoIn.setVideo(postData.getString("source"));
                        }

                        //Information of post IN
//                        postIn.setSocialNetMsgId(postData.getString("id"));
//                        postIn.setPostInSocialNetwork(socialNetwork);
//                        postIn.setPostInStream(null);
//                        postIn.setPostInSocialNetworkUser(socialNetUser);
//                        Calendar calendario = Calendar.getInstance();
//                        postIn.setPi_created(calendario.getTime());

                        System.out.println("********************VIDEO guardado OK");
                        //response.setRenderParameter("postUri", postIn.getURI());
                    } else if (postType.equals("photo")) {
                        postIn = PhotoIn.ClassMgr.createPhotoIn(model);
                        postIn.setPi_type(SWBSocialUtil.POST_TYPE_PHOTO);
                        //Get message and/or story
                        if (!postData.isNull("message")) {
                            message = postData.getString("message");
                        } else if (!postData.isNull("story")) {
                            story = (!postData.isNull("story")) ? postData.getString("story") : "";
                            story = getTagsFromPost(postData.getJSONObject("story_tags"), story);
                        }

                        System.out.println("\tMESSAGE:" + message);
                        System.out.println("\tSTORY:" + story);
                        if (!message.isEmpty()) {
                            postIn.setMsg_Text(message);
                        } else if (!story.isEmpty()) {
                            postIn.setMsg_Text(story);
                        } else {
                            postIn.setMsg_Text("");
                        }

                        if (postData.has("picture")) {
                            String photo = postData.getString("picture");
                            PhotoIn photoIn = (PhotoIn) postIn;
                            photoIn.addPhoto(photo);
                        }

//                        //Information of post IN
//                        postIn.setSocialNetMsgId(postData.getString("id"));
//                        postIn.setPostInSocialNetwork(socialNetwork);
//                        postIn.setPostInStream(null);
//                        postIn.setPostInSocialNetworkUser(socialNetUser);
//                        Calendar calendario = Calendar.getInstance();
//                        postIn.setPi_created(calendario.getTime());                        
                        System.out.println("********************STATUS guardado OK");
                        //response.setRenderParameter("postUri", postIn.getURI());
                    }

                    //Information of post IN
                    postIn.setSocialNetMsgId(postData.getString("id"));
                    postIn.setPostInSocialNetwork(socialNetwork);
                    postIn.setPostInStream(null);
                    postIn.setPostInSocialNetworkUser(socialNetUser);
                    Calendar calendario = Calendar.getInstance();
                    postIn.setPi_created(calendario.getTime());

                    SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
                    if (defaultSocialTopic != null) {
                        postIn.setSocialTopic(defaultSocialTopic);//Asigns socialTipic
                        System.out.println("Setting social topic:" + defaultSocialTopic);
                    } else {
                        postIn.setSocialTopic(null);
                        System.out.println("Setting to null");
                    }
                }
            } catch (Exception e) {
                log.error("Error trying to setSocialTopic: ", e);
            }

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
            //Post saved
        } else if (mode != null && mode.equals("postSent")) {//Hides dialog used to create Post
            if (request.getParameter("repliedPost") != null && request.getParameter("repliedPost").equals("ok")) {
                out.println("<script type=\"text/javascript\">");
                out.println("   hideDialog();");
                out.println("   showStatus('Post sent');");
                out.println("</script>");
            }
        } else if (mode != null && mode.equals("displayPicture")) {
            out.println("<div style=\"width: 640px; height: 480px; border: thick solid #F88D38; overflow: hidden; position: relative; background-color:#CDD0D1;\">");
            out.println("    <img src=\"" + URLDecoder.decode(request.getParameter("pictureUrl"), "UTF-8") + "\"style=\"position: absolute;\" onload=\"showFullImage(this);\"/>");
            out.println("</div>");
        } else if (mode != null && mode.equals("displayVideo")) {
            String jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/playVideo.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error in displayVideo() for requestDispatcher", e);
            }
        } else if (mode.equals("fullProfile")) {//Show user or page profile in dialog
            String profileType = request.getParameter("type") == null ? "" : (String) request.getParameter("type");
            String objUri = (String) request.getParameter("suri");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook) semanticObject.createGenericInstance();
            String jspResponse;

            if (profileType.equals("noType")) {
                try {
                    JSONObject profile = new JSONObject(getProfileFromId(request.getParameter("id"), facebook));
                    profile = profile.getJSONArray("data").getJSONObject(0);
                    profileType = profile.getString("type");
                } catch (JSONException jsone) {
                    log.error("Error getting profile information" + jsone);
                    return;
                }
            }
            if (profileType.equals("user")) {
                jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookUserProfile.jsp";
            } else if (profileType.equals("page")) {
                jspResponse = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialNetworks/facebookPageProfile.jsp";
            } else {
                return;
            }

            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                System.out.println("Error displaying user profile");
            }
        } else if (mode.equals("doShowTopic")) {//**ini
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/assignTopic.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            String objUri = (String) request.getParameter("suri");
            if (dis != null) {
                try {
                    request.setAttribute("suri", objUri);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error("Error on doShowTopic: ", e);
                }
            }
        } else if (mode.equals("doReclassifyTopic")) {
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
        } else if (mode.equals("assignedPost")) {
            String id = request.getParameter("id");
            String fbid = request.getParameter("fbid");
            String postUri = request.getParameter("postUri");
            SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", id).setParameter("postUri", postUri).setParameter("currentTab", currentTab);
            String url = "<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("reclassify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','" + paramRequest.getLocaleString("reclassify") + " post'); return false;\"><span>" + paramRequest.getLocaleString("reclassify") + "</span></a>";
            out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
            out.println("<script type=\"dojo/method\">");
            out.println("   hideDialog(); ");
            out.println("   try{");///////////Falta poner el id de FACEBOOK
            out.println("   var spanId = dijit.byId('" + fbid + id + TOPIC + currentTab + "');");
            out.println("   spanId.attr('content', '" + url.replace("'", "\\'") + "');");
            out.println("   }catch(noe){alert('Error:' + noe);}");
            out.println("   showStatus('Tema asociado correctamente');");
            out.println("</script>");
            out.println("</span>");



            //response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            //response.setRenderParameter("id", idStatus);
        } else if (mode.equals("reAssignedPost")) {
            out.println("<script type=\"javascript\">");
            out.println("   hideDialog(); ");
            out.println("   showStatus('El tema fue cambiado correctamente');");
            out.println("</script>");
        } else if (mode.equals("storeInterval")) {//Storing the interval for the current uri
            String objUri = request.getParameter("suri");
            System.out.println("suri:" + objUri + "---interval:" + request.getParameter("interval"));
            if (request.getParameter("interval") != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute(objUri + "pooling", request.getParameter("interval"));
            }
        } else if (mode.equals("more")) {
            try {
                moreView(request, response, paramRequest);
            } catch (JSONException ex) {
                log.error(ex);
            }
        } else {//**fin
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doGetMorePosts(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String scope = request.getParameter("scope") == null ? "" : request.getParameter("scope");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();
        SWBModel model = WebSite.ClassMgr.getWebSite(facebook.getSemanticObject().getModel().getName());
        if (objUri != null) {
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        System.out.println("\n\n\nTHE CURRENT TAB:" + currentTab);
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("access_token", facebook.getAccessToken());
        params.put("limit", "50");
        params.put("fields", "id,from,to,message,message_tags,story,story_tags,picture,caption,link,object_id,application,source,name,description,properties,icon,actions,privacy,type,status_type,created_time,likes.summary(true),comments.limit(5).summary(true),place,icon");
        params.put("until", request.getParameter("until"));
        System.out.println("Get the next 50 Posts!!");
        String fbResponse = "";
        String untilPost = "";
        try {
            if (scope.equals("newsFeed")) {
                fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            } else if (scope.equals("wall")) {
                fbResponse = postRequest(params, "https://graph.facebook.com/" + facebook.getFacebookUserId() + "/feed",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            }
            untilPost = parseResponse(fbResponse, out, false, request, paramRequest, currentTab, model);
            if (untilPost == null) {//If not exception was thrown but the value is null
                untilPost = request.getParameter("until");//return the original value
            }
        } catch (Exception e) {
            untilPost = request.getParameter("until");//return the original value on exception
            log.error("Problem recovering more posts", e);
        }
        //CAMBIAR EL ID DEL DIV dependiendo de donde sea llamado
        out.println("<div align=\"center\">");
        if (scope.equals("newsFeed")) {
            out.println("<label id=\"" + objUri + "morePostsLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("scope", scope).setParameter("currentTab", currentTab) + "','" + objUri + "getMorePosts','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">More posts</a></label>");

        } else if (scope.equals("wall")) {
            out.println("<label id=\"" + objUri + "morePostsWallLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("scope", scope).setParameter("currentTab", currentTab) + "','" + objUri + "getMorePostsWall','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">More posts</a></label>");
        }
        out.println("</div>");
    }

    public void doGetMorePictures(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String createdTimeParam = request.getParameter("createdTime");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();
        SWBModel model = WebSite.ClassMgr.getWebSite(facebook.getSemanticObject().getModel().getName());
        if (objUri != null) {
            renderURL.setParameter("suri", objUri);
        }
        System.out.println("\n\n\nTHE CURRENT TAB:" + currentTab);
        System.out.println("Get the next 25 Posts!!");
        String fbResponse = "";

        HashMap<String, String> params = new HashMap<String, String>(2);
        //TODO: it seems than 'likes' is deprecated and it must be replaced with like_info
        //params.put("q", "SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comments FROM stream WHERE filter_key IN ( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Photos') AND created_time < " + createdTimeParam +" LIMIT 10");
        params.put("q", "{\"pictures\": \"SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comments FROM stream WHERE filter_key IN "
                + "( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Photos') AND created_time < " + createdTimeParam + " LIMIT 25\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT actor_id FROM #pictures)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT actor_id FROM #pictures)\"}");
        params.put("access_token", facebook.getAccessToken());

        fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        String createdTime = picture(fbResponse, out, false, request, paramRequest, model);
        if (createdTime == null) {//A problem was found, recover the original value of the param
            createdTime = createdTimeParam;
        }
        out.println("<div align=\"center\">");
        out.println("<label id=\"" + objUri + "morePicturesLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMorePictures").setParameter("createdTime", createdTime) + "','" + objUri + "getMorePictures','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">More Pictures</a></label>");
        out.println("</div>");
    }

    public void doGetMoreVideos(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String createdTimeParam = request.getParameter("createdTime");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();
        SWBModel model = WebSite.ClassMgr.getWebSite(facebook.getSemanticObject().getModel().getName());
        if (objUri != null) {
            renderURL.setParameter("suri", objUri);
        }
        System.out.println("\n\n\nTHE CURRENT TAB:" + currentTab);
        System.out.println("Get the next 25 VIDEOS!!");
        String fbResponse = "";

        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("q", "{\"videos\": \"SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comments FROM stream WHERE filter_key IN "
                + "( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Video') AND created_time < " + createdTimeParam + " LIMIT 25\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT actor_id FROM #videos)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT actor_id FROM #videos)\"}");
        params.put("access_token", facebook.getAccessToken());

        fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        String createdTime = video(fbResponse, out, false, request, paramRequest, model);
        if (createdTime == null) {//A problem was found, recover the original value of the param
            createdTime = createdTimeParam;
        }
        out.println("<div align=\"center\">");
        out.println("<label id=\"" + objUri + "moreVideosLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreVideos").setParameter("createdTime", createdTime) + "','" + objUri + "getMoreVideos','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">More Videos</a></label>");
        out.println("</div>");
    }

    //get the next comments of a post
    public void doGetMoreComments(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String postId = request.getParameter("postId");
        String objUri = request.getParameter("suri");
        String after = request.getParameter("after");
        String currentTab = request.getParameter("currentTab");
        PrintWriter out = response.getWriter();

        if (postId == null || objUri == null || after == null || objUri == null) {//If error don't show 'View more comments'
            return;
        }

        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        renderURL.setParameter("suri", objUri);

        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();

        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("access_token", facebook.getAccessToken());
        params.put("after", after);
        params.put("limit", "10");

        String fbResponse = postRequest(params, "https://graph.facebook.com/" + postId + "/comments",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");

        try {
            JSONObject commentsObj = new JSONObject(fbResponse);

            if (commentsObj.has("data")) {
                JSONArray comments = commentsObj.getJSONArray("data");

                for (int k = 0; k < comments.length(); k++) {
                    out.write("<li>");
                    out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", comments.getJSONObject(k).getJSONObject("from").getLong("id") + "") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + comments.getJSONObject(k).getJSONObject("from").getLong("id") + "/picture?width=30&height=30\" width=\"30\" height=\"30\"/></a>");

                    out.write("<p>");
                    out.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", comments.getJSONObject(k).getJSONObject("from").getLong("id") + "") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\">" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "</a>:");
                    out.write(comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br>");
                    out.write("</p>");

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
                    Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));

                    //out.write("<div id=\"" + facebook.getId() + comments.getJSONObject(k).getString("id") + "_" + postId + "\" dojoType=\"dojox.layout.ContentPane\">");
                    out.write("<p class=\"timelinedate\">");
                    out.write("<span dojoType=\"dojox.layout.ContentPane\">");

                    out.write("<em>" + facebookHumanFriendlyDate(commentTime, paramRequest) + "</em>");
                    if (comments.getJSONObject(k).has("like_count")) {
                        out.write("<strong>");
                        out.write("<span> Likes: " + comments.getJSONObject(k).getInt("like_count") + "</span>");
                        out.write("</strong>");
                    }
                    out.write("</span>");
                    out.write("</li>");
                }

                if (commentsObj.has("paging")) {//get link to view additional comments if available
                    JSONObject pagingComments = commentsObj.getJSONObject("paging");
                    if (pagingComments.has("cursors") && pagingComments.has("next")) {
                        out.write("<li class=\"timelinemore\">");
                        SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode("moreComments").setParameter("suri", request.getParameter("suri")).setParameter("postId", postId);
                        commentsURL = commentsURL.setParameter("after", pagingComments.getJSONObject("cursors").getString("after")).setParameter("currentTab", currentTab);
                        out.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + commentsURL
                                + "','" + facebook.getId() + postId + currentTab + "/comments', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\"><span>+</span>" + paramRequest.getLocaleString("moreComments") + "</a></a></label>");
                        out.write("</li>");
                    }
                }
            }
        } catch (Exception e) {//If an exception is thrown return the original message
            out.write("<div align=\"left\" id=\"" + postId + "/" + currentTab + "/comments\" dojoType=\"dojox.layout.ContentPane\">");
            SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode("moreComments").setParameter("suri", request.getParameter("suri")).setParameter("postId", postId);
            commentsURL = commentsURL.setParameter("after", after).setParameter("currentTab", currentTab);
            out.write("<label id=\"morePostsLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + commentsURL
                    + "','" + postId + "/" + currentTab + "/comments', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreComments") + "</a></label>");
            out.write("</div>");

            log.error("Unable to get additional comments: ", e);
        }
    }

    /**
     * Analiza la respuesta de Facebook y obtiene la informaci&oacute;n de los
     * mensajes publicados en el muro del usuario actual Con base en la
     * estructura del objeto JSON devuelto por Facebook, se revisa que la
     * respuesta cuenta con información de los posts recibidos para
     * posteriormente desplegarlos uno a uno en el objeto de salida indicado. Se
     * obtienen también el valor del parámetro usado para traer el siguiente
     * bloque de posts.
     *
     * @param response representa la respuesta obtenida de Facebook en formato
     * JSON
     * @param out Salida estándar en donde se imprime el contenido del post
     * @param includeSinceParam valor que define si se incluye en la variable de
     * sesi&oacute;n el valor del último post recibido para posteriormente
     * preguntar a Facebook si hay nuevos posts.
     * @param request est&aacute;ndar HttpServletRequest
     * @param paramRequest est&aacute;ndar SWBParamRequest
     * @return
     */
    public static String parseResponse(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, SWBModel model) {

        String until = null;
        String since = "";
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();

        try {
            JSONObject phraseResp = new JSONObject(response);
            int cont = 0;
            JSONArray postsData = phraseResp.getJSONArray("data");
            System.out.println("ARREGLO DE DATOS:" + postsData.length());

            org.semanticwb.model.User user = paramRequest.getUser();
            SocialUserExtAttributes socialUserExtAttr = null;
            if (user.isSigned()) {
                socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
            }

            for (int k = 0; k < postsData.length(); k++) {
                cont++;
                //System.out.println("\n\nPost de FACEBOOOK*********: " + postsData.getJSONObject(k));
                doPrintPost(out, postsData.getJSONObject(k), request, paramRequest, tabSuffix, facebook, model, socialUserExtAttr);
            }
            if (phraseResp.has("paging")) {
                JSONObject pagingData = phraseResp.getJSONObject("paging");
                String nextPage = pagingData.getString("next"); // get until param to get OLDER posts
                Pattern pattern = Pattern.compile("until=[0-9]+");
                Matcher matcher = pattern.matcher(nextPage);
                String untilParam = "";
                if (matcher.find()) {
                    untilParam = matcher.group();
                }
                if (!untilParam.isEmpty()) {
                    until = untilParam.substring(untilParam.indexOf("=") + 1);//gets only the value of until param in paging object
                }
                if (includeSinceParam) {//Include value of since param when the tab is loaded and when GetNewPost link is clicked
                    String previousPage = pagingData.getString("previous"); // get since param to get NEWER posts
                    pattern = Pattern.compile("since=[0-9]+");
                    matcher = pattern.matcher(previousPage);
                    String sinceParam = "";

                    if (matcher.find()) {
                        sinceParam = matcher.group();
                    }
                    if (!sinceParam.isEmpty()) {
                        since = sinceParam.substring(sinceParam.indexOf("=") + 1);//gets only the value of since param in paging object
                        HttpSession session = request.getSession(true);
                        System.out.println("\n\n\n\t\tReemplazando viejo parametro FEED:" + session.getAttribute(objUri + tabSuffix + "since"));
                        session.setAttribute(objUri + tabSuffix + "since", since);
                    }
                }
            }
        } catch (JSONException jsone) {
            log.error("Problemas al parsear respuesta de Facebook", jsone);
        }
        return until;
    }

    public static String picture(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest, SWBModel model) {

        String createdTime = null;

        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();

        try {
            JSONObject phraseResp = new JSONObject(response);
            System.out.println("Tamanio del arreglo:" + phraseResp.length());
            int cont = 0;

            JSONArray postsData = null;
            JSONArray userData = null;
            JSONArray pageData = null;

            for (int i = 0; i < phraseResp.getJSONArray("data").length(); i++) {
                if (phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pictures")) {//All the posts
                    postsData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                } else if (phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pages")) {//All the pages
                    pageData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                } else if (phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("usernames")) {//All the users
                    userData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }
            }

            for (int k = 0; k < postsData.length(); k++) {
                cont++;
                JSONObject profileID = null;
                JSONObject postComments = null;
                for (int userCount = 0; userCount < userData.length(); userCount++) {
                    if (userData.getJSONObject(userCount).getLong("uid") == postsData.getJSONObject(k).getLong("actor_id")) {
                        profileID = userData.getJSONObject(userCount);
                        break;
                    }
                }
                if (profileID == null) { //If the 'id' was not found inside the Users, checkPages
                    for (int pageCount = 0; pageCount < pageData.length(); pageCount++) {
                        if (pageData.getJSONObject(pageCount).getLong("page_id") == postsData.getJSONObject(k).getLong("actor_id")) {
                            profileID = pageData.getJSONObject(pageCount);
                            break;
                        }
                    }
                }
                if (profileID == null) {
                    System.out.println("\n\n\n\nTHIS IS NOT SUPOSSED TO HAPPEN!!!!!!!!!!!!!!!!!" + postsData.getJSONObject(k).getLong("actor_id"));
                    return null;
                }

                if (postsData.getJSONObject(k).has("comment_info")) {
                    if (postsData.getJSONObject(k).getJSONObject("comment_info").has("comment_count")) {
                        if (postsData.getJSONObject(k).getJSONObject("comment_info").getLong("comment_count") > 0) {//If post has are comments
                            //make te request for comments of the current post
                            HashMap<String, String> params = new HashMap<String, String>(3);
                            params.put("access_token", facebook.getAccessToken());
                            params.put("limit", "5");
                            try {
                                String fbResponse = postRequest(params, "https://graph.facebook.com/" + postsData.getJSONObject(k).getString("post_id") + "/comments",
                                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
                                postComments = new JSONObject(fbResponse);
                            } catch (Exception e) {
                                System.out.println("Error getting comments of post " + e);
                            }
                        }
                    }
                }

                createdTime = printPicture(out, postsData.getJSONObject(k), postComments, profileID, request, paramRequest, PICTURES_TAB, facebook, model);

                //Only include the param in session when the page loads the first time and when 
                if (includeSinceParam && k == 0) {//Only save the most recent picture id(the first), then use this id to ask if new pictures available
                    HttpSession session = request.getSession(true);
                    System.out.println("\n\n\n\t\tReemplazando viejo parametro PICTURE:" + session.getAttribute(objUri + PICTURES_TAB + "since"));
                    session.setAttribute(objUri + PICTURES_TAB + "since", createdTime);
                }
            }

            System.out.println("TOTAL PICTURE POSTS RECEIVED:" + postsData.length());
        } catch (Exception jsone) {
            log.error("Problemas al parsear respuesta de Facebook", jsone);
            createdTime = null;
        }
        return createdTime;
    }

    public static String video(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest, SWBModel model) {

        String createdTime = null;

        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();

        try {
            JSONObject phraseResp = new JSONObject(response);
            System.out.println("Tamanio del arreglo:" + phraseResp.length());
            int cont = 0;

            JSONArray postsData = null;
            JSONArray userData = null;
            JSONArray pageData = null;

            for (int i = 0; i < phraseResp.getJSONArray("data").length(); i++) {
                if (phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("videos")) {//All the posts
                    postsData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                } else if (phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pages")) {//All the pages
                    pageData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                } else if (phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("usernames")) {//All the users
                    userData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }
            }

            for (int k = 0; k < postsData.length(); k++) {
                cont++;
                JSONObject profileID = null;
                JSONObject postComments = null;
                for (int userCount = 0; userCount < userData.length(); userCount++) {
                    if (userData.getJSONObject(userCount).getLong("uid") == postsData.getJSONObject(k).getLong("actor_id")) {
                        profileID = userData.getJSONObject(userCount);
                        break;
                    }
                }
                if (profileID == null) { //If the 'id' was not found inside the Users, checkPages
                    for (int pageCount = 0; pageCount < pageData.length(); pageCount++) {
                        if (pageData.getJSONObject(pageCount).getLong("page_id") == postsData.getJSONObject(k).getLong("actor_id")) {
                            profileID = pageData.getJSONObject(pageCount);
                            break;
                        }
                    }
                }
                if (profileID == null) {
                    System.out.println("\n\n\n\nTHIS IS NOT SUPOSSED TO HAPPEN!!!!!!!!!!!!!!!!!" + postsData.getJSONObject(k).getLong("actor_id"));
                    return null;
                }

                if (postsData.getJSONObject(k).has("comment_info")) {
                    if (postsData.getJSONObject(k).getJSONObject("comment_info").has("comment_count")) {
                        if (postsData.getJSONObject(k).getJSONObject("comment_info").getLong("comment_count") > 0) {//If post has are comments
                            //make te request for comments of the current post
                            HashMap<String, String> params = new HashMap<String, String>(3);
                            params.put("access_token", facebook.getAccessToken());
                            params.put("limit", "5");
                            try {
                                String fbResponse = postRequest(params, "https://graph.facebook.com/" + postsData.getJSONObject(k).getString("post_id") + "/comments",
                                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
                                postComments = new JSONObject(fbResponse);
                            } catch (Exception e) {
                                log.error("Error getting comments of post ", e);
                            }
                        }
                    }
                }

                String createdTimeTmp = doPrintVideo(out, postsData.getJSONObject(k), postComments, profileID, request, paramRequest, PICTURES_TAB, facebook, model);
                if (createdTimeTmp != null) {
                    createdTime = createdTimeTmp;
                }
                //Only include the param in session when the page loads the first time and when 
                if (includeSinceParam && k == 0) {//Only save the most recent picture id(the first), then use this id to ask if new pictures available
                    HttpSession session = request.getSession(true);
                    System.out.println("\n\n\n\t\tReemplazando viejo parametro VIDEO:" + session.getAttribute(objUri + VIDEOS_TAB + "since"));
                    session.setAttribute(objUri + VIDEOS_TAB + "since", createdTime);
                }
            }

            System.out.println("TOTAL VIDEO POSTS RECEIVED:" + postsData.length());
        } catch (Exception e) {
            log.error("Problemas al parsear respuesta de Facebook-video", e);
            e.printStackTrace();
            createdTime = null;
        }
        return createdTime;
    }

    public static String getTagsFromPost(JSONObject objectTags, String postContent, SWBResourceURL renderURL) {
        String postContentWithUrl = postContent;
        Iterator<?> keyTags = objectTags.keys();
        try {
            while (keyTags.hasNext()) {
                String key = (String) keyTags.next();
                if (objectTags.get(key) instanceof JSONArray) {
                    JSONArray tag = objectTags.getJSONArray(key);
                    String userUrl = "";
                    if (tag.getJSONObject(0).has("type")) {
                        userUrl = "<a href=\"#\" title=\"" + "View profile" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id") + "") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a> ";
                    } else {
                        userUrl = "<a href=\"#\" title=\"" + "View profile" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", tag.getJSONObject(0).getLong("id") + "") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a> ";
                    }
                    //userUrl = "<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id")+"") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a>";
                    postContentWithUrl = postContentWithUrl.replace(tag.getJSONObject(0).getString("name"), userUrl);
                }
            }
        } catch (JSONException jSonException) {
            log.error("Problem parsing associated users:" + objectTags + "\n\n" + postContent);
            jSonException.printStackTrace();
            return postContent;
        }
        return postContentWithUrl;
    }

    public static String getTagsFromPost(JSONObject objectTags, String postContent) {
        String postContentWithUrl = postContent;
        Iterator<?> keyTags = objectTags.keys();
        try {
            while (keyTags.hasNext()) {
                String key = (String) keyTags.next();
                if (objectTags.get(key) instanceof JSONArray) {
                    JSONArray tag = objectTags.getJSONArray(key);
                    String userUrl = "";
                    userUrl = "<a href=\"http://www.facebook.com/" + tag.getJSONObject(0).getString("id") + "\" target=\"_blank\">" + tag.getJSONObject(0).getString("name") + "</a> ";
                    postContentWithUrl = postContentWithUrl.replace(tag.getJSONObject(0).getString("name"), userUrl);
                }
            }
        } catch (JSONException jSonException) {
            log.error("Problem parsing associated users", jSonException);
            return postContent;
        }
        return postContentWithUrl;
    }

    public static String getTagsFromPostArray(JSONObject objectTags, String postContent, SWBResourceURL renderURL) {
        String postContentWithUrl = postContent;
        try {
            String userUrl = "";
            userUrl = "<a href=\"#\" title=\"" + "View profile" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", objectTags.getString("type")).setParameter("id", objectTags.getLong("id") + "") + "','" + objectTags.getString("name") + "'); return false;\">" + objectTags.getString("name") + "</a>";
            postContentWithUrl = postContentWithUrl.replace(objectTags.getString("name"), userUrl);

        } catch (JSONException jSonException) {
            log.error("Problem parsing associated users", jSonException);
        }
        return postContentWithUrl;
    }

    public static JSONObject getPostFromId(String postId, String fields, Facebook facebook) {
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        if (fields != null) {
            params.put("fields", fields);
        }

        JSONObject jsonObject = null;
        try {
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + postId.substring(postId.lastIndexOf("_") + 1),
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            if (!fbResponse.isEmpty()) {
                jsonObject = new JSONObject(fbResponse);
            }
        } catch (IOException ieo) {
            log.error("Error getting post that user liked:", ieo);
        } catch (JSONException jsone) {
            log.error("Error parsing information from string recieved:", jsone);
        }
        return jsonObject;
    }

    public static JSONObject getPostFromFullId(String postId, Facebook facebook) {
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());

        JSONObject jsonObject = null;
        try {
            String fbResponse = "";
            boolean failed = false;
            try {
                System.out.println("Making the request/1");
                fbResponse = getRequest(params, "https://graph.facebook.com/" + postId + "/",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                System.out.println("Making the request/2");
            } catch (IOException ioe) {
                System.out.println("Making the request/1 Prim Exception");
                System.out.println("la peticion fallo con ID:" + postId);
                failed = true;
            }
            if (!fbResponse.isEmpty() || failed) {
                jsonObject = new JSONObject(fbResponse);
                if (jsonObject.has("error")) {//The request with fullId triggers an error
                    System.out.println("Making the request 2/1");
                    fbResponse = getRequest(params, "https://graph.facebook.com/" + postId.substring(postId.lastIndexOf("_") + 1) + "/",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                    if (!fbResponse.isEmpty()) {
                        jsonObject = new JSONObject(fbResponse);
                    }
                }
            }
        } catch (IOException ieo) {
            log.error("Error getting post from post with, tested both methods:", ieo);
        } catch (JSONException jsone) {
            log.error("Error parsing information from response recieved:", jsone);
        }
        return jsonObject;
    }

    public static void doPrintPost(Writer writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook, SWBModel model, SocialUserExtAttributes socialUserExtAttr) {
        try {
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            actionURL.setParameter("suri", request.getParameter("suri"));
            renderURL.setParameter("suri", request.getParameter("suri"));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            String postType = postsData.getString("type");

            String story = "";
            String message = "";
            String caption = "";
            boolean isAPhotoLike = false;
            boolean isALinkLike = false;
            boolean isAPostLike = false;
            boolean isAppCreated = false;
            boolean isAStatusLike = false;
            boolean isFoursquare = false;
            //TODO: FALTA COMMENTED ON A PHOTO
            JSONObject postLike = null;
            JSONObject photoLike = null;
            JSONObject linkLike = null;
            JSONObject applicationCreated = null;
            JSONObject foursquareLink = null;
            JSONObject statusLike = null;



            if (postsData.isNull("actions")) {//You can't like or respond this post
                return;                     //Must NOT be shown in wall
            }

            //If the posts is empty and is application-created don't show it
            if (postsData.isNull("message") && postsData.isNull("story") && postsData.isNull("name") && postsData.isNull("picture")
                    && postsData.isNull("link") && postsData.isNull("description") && !postsData.isNull("application")) {
                return;
            }

            if (postType.equals("photo")) {
                if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                }

                if (!postsData.isNull("message")) {
                    message = postsData.getString("message");
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }
                if (!postsData.isNull("caption")) {
                    caption = postsData.getString("caption").replace("\n", "</br>");
                }
                if (postsData.has("application")) {
                    if (postsData.getJSONObject("application").getString("name").equals("Foursquare")) {
                        return;
                        /*foursquareLink = getPostFromId(postsData.getString("id"), null, facebook);
                         isFoursquare = true;
                         System.out.println("\n\n\nFOURSQUARE CREATED:" +  foursquareLink);
                         message = "Checked in";
                         */
                    }
                }
                //Story or message or both!!
                //or "status_type": "shared_story", tagged_in_photo

            } else if (postType.equals("link")) {
                //"status_type": "app_created_story",
                if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                    if (story.contains("is going to an event") && postsData.has("link")) {//If the link is an event
                        return;
                        //message = "<a href=\"" + postsData.getString("link") + "\" target=\"_blank\">View event</a>";
                    }
                    if (story.contains("likes a photo")) {
                        return;
                    } else if (story.contains("likes a link")) {
                        return;
                    } else if (story.contains("likes a status")) {
                        return;
                    } else if (story.contains("commented on")) {
                        return;
                    } else if (story.contains("likes")) {
                        return;
                    } else if (story.contains("is going to")) {
                        return;
                    } else if (story.contains("created an event")) {
                        return;
                    }
                }
                if (!postsData.isNull("message")) {
                    message = postsData.getString("message");
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }

                if (postsData.has("application")) {
                    //return;
                    /*
                     if(postsData.getJSONObject("application").getString("name").equals("Instagram")){
                     applicationCreated = getPostFromId(postsData.getString("id"), null, facebook);
                     isAppCreated = true;
                     System.out.println("\n\n\nAPPLICATION CREATED:" +  applicationCreated);
                     message = "Liked a picture in Instagram";
                     }*/
                }
            } else if (postType.equals("status")) {

                if (postsData.has("story")) {//Do not print the posts when 'User X likes a post'
                    if (postsData.getString("story").contains("likes a post")) {
                        return;
                    }
                }
                if (!postsData.isNull("status_type")) {
                    if (postsData.getString("status_type").equals("wall_post")) {
                        JSONObject toUser = null;
                        if (postsData.has("to")) {
                            toUser = postsData.getJSONObject("to").getJSONArray("data").getJSONObject(0);
                            story = " to " + "<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", toUser.getLong("id") + "") + "','" + toUser.getString("name") + "'); return false;\">" + toUser.getString("name") + "</a>";
                        }
                    }
                }
                if (!postsData.isNull("message")) {
                    message = postsData.getString("message");
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                } else if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = getTagsFromPost(storyTags, story, renderURL);
                    }
                    if (story.contains("likes a photo")) {
                        /*photoLike = getPostFromId(postsData.getString("id"), "id,from,name,name_tags,picture,source,link,tags", facebook);
                         isAPhotoLike = true;*/
                        return;
                        //System.out.println("THE RECOVERED OBJECT:" + jSONObject);
                    } else if (story.contains("likes a link")) {//Do not print the posts when 'User X likes a link'
                        /*linkLike = getPostFromId(postsData.getString("id"), "id,from,name,picture,link,tags,message", facebook);
                         System.out.println("\n\n\nLINKED LIKED:" +  linkLike);
                         isALinkLike = true;*/
                        return;
                    } else if (story.contains("likes a status")) {
                        /*
                         statusLike = getPostFromId(postsData.getString("id"), null, facebook);
                         isAStatusLike = true;
                         System.out.println("\n\n\nSTATUS LIKED:" +  statusLike);
                         if(statusLike.has("message")){
                         message = statusLike.getString("message");
                         }*/
                        return;
                    } else if (story.contains("commented on")) {
                        return;
                    } else if (story.contains("likes")) {//USER likes PAGE
                        return;
                    } else if (story.contains("is going to")) {//events
                        return;
                    } else if (story.contains("created an event")) {
                        return;
                    }
                } else {//Status must have message OR Story
                    return;
                }
            } else if (postType.equals("video")) {
                if (!postsData.isNull("message")) {
                    message = postsData.getString("message");
                }

                if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = getTagsFromPost(storyTags, story, renderURL);
                    }
                }
            } else if (postType.equals("checkin")) {

                if (!postsData.isNull("message")) {
                    message = postsData.getString("message");
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                } else {
                    message = postsData.getJSONObject("from").getString("name") + " checked in ";
                }
            } else if (postType.equals("swf")) {
                if (!postsData.isNull("message")) {
                    message = postsData.getString("message");
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                }
            }

            if (postsData.has("place") && !postsData.isNull("place")) {
                if (postsData.getJSONObject("place").has("name")) {
                    message = message + " at " + "<a href=\"http://facebook.com/" + postsData.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + postsData.getJSONObject("place").getString("name") + "</a>";
                }
            }

            if (isFoursquare) {
                if (foursquareLink.has("place")) {
                    if (foursquareLink.getJSONObject("place").has("name")) {
                        message = message + "by Foursquare AT " + "<a href=\"http://facebook.com/" + foursquareLink.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + foursquareLink.getJSONObject("place").getString("name") + "</a>";
                    }
                }
            }

            //JSONObject profile = new JSONObject(getProfileFromId(postsData.getJSONObject("from").getString("id")+"", facebook));
            //profile = profile.getJSONArray("data").getJSONObject(0);
            writer.write("<div class=\"timeline timelinefacebook\">");
            //Username and story
            writer.write("<p>");
            writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id") + "") + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\">" + postsData.getJSONObject("from").getString("name") + "</a> " + story);
            writer.write("</p>");

            //User image and message
            writer.write("<div class=\"timelineusr\">");
            writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id") + "") + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + postsData.getJSONObject("from").getLong("id") + "/picture\"/></a>");

            writer.write("<p>");
            if (message.isEmpty()) {
                writer.write("&nbsp;");
            } else {
                writer.write(message.replace("\n", "</br>"));
            }
            writer.write("</p>");
            writer.write("</div>");

            //Picture if exists, start
            if (postsData.has("picture") || isAPhotoLike || isALinkLike || isAppCreated) {
                String picture = "";
                if (isAPhotoLike) {
                    if (photoLike.has("source")) {
                        picture = photoLike.getString("source");
                    }
                } else if (isALinkLike) {
                    if (linkLike.has("picture")) {
                        picture = linkLike.getString("picture");
                    }
                } else if (isAPostLike) {
                    if (postLike.has("picture")) {
                        picture = postLike.getString("picture");
                    }
                } else if (isAppCreated) {
                    if (applicationCreated.has("data")) {
                        if (applicationCreated.getJSONObject("data").has("object")) {
                            picture = applicationCreated.getJSONObject("data").getJSONObject("object").optString("url") + "media";
                        }
                    }
                } else {
                    picture = postsData.getString("picture").replace("_s.", "_n.");
                }
                //Post image
                writer.write("<div class=\"timelineimg\">");
                if (postType.equals("video") || postType.equals("swf")) {
                    writer.write("      <span id=\"vid" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    writer.write("      <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode(postsData.getString("source"), "UTF-8"))
                            + "','Video from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'vid" + tabSuffix + facebook.getId() + postsData.getString("id") + "');\"/></a>");
                    writer.write("      </span>");
                } else {
                    if (isALinkLike) {//If the post is a link -> it has link and name
                        if (linkLike.has("link") && linkLike.has("picture")) {
                            writer.write("      <span id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                            writer.write("      <a href=\"" + linkLike.getString("link") + "\" target=\"_blank\">" + "<img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix + facebook.getId() + postsData.getString("id") + "');\"/></a>");
                            writer.write("      </span>");
                        }
                    } else if (postType.equals("link")) {//If the post is a link -> it has link and name
                        if (postsData.has("name") && postsData.has("link")) {
                            writer.write("      <span id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                            writer.write("      <a href=\"" + postsData.getString("link") + "\" target=\"_blank\">" + "<img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix + facebook.getId() + postsData.getString("id") + "');\"/></a>");
                            writer.write("      </span>");
                        }
                    } else {
                        writer.write("      <span id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                        writer.write("      <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("displayPicture").setParameter("pictureUrl", URLEncoder.encode(picture, "UTF-8"))
                                + "','Picture from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix + facebook.getId() + postsData.getString("id") + "');\"/></a>");
                        writer.write("      </span>");
                    }
                }

                writer.write("<p class=\"imgtitle\">");
                if (postsData.has("link") && postsData.has("name")) {
                    writer.write("<a href=\"" + postsData.getString("link") + "\" target=\"_blank\">" + postsData.getString("name") + "</a>");
                } else if (isALinkLike) {
                    if (linkLike.has("link")) {
                        writer.write("<a href=\"" + linkLike.getString("link") + "\" target=\"_blank\">" + linkLike.getString("name") + "</a>");
                    }
                } else if (isAPostLike) {
                    if (postLike.has("link")) {
                        writer.write("<a href=\"" + postLike.getString("link") + "\" target=\"_blank\">" + postLike.getString("name") + "</a>");
                    }
                } else {
                    writer.write("&nbsp;");
                }
                writer.write("</p>");

                writer.write("<p class =\"imgdesc\">");
                if (isAPhotoLike) {
                    writer.write(photoLike.has("name") ? photoLike.getString("name") : "&nbsp;");
                } else if (isALinkLike) {
                    writer.write(linkLike.has("message") ? linkLike.getString("message") : "&nbsp;");
                } else {
                    writer.write(postsData.has("description") ? postsData.getString("description") : "&nbsp;");
                }
                writer.write("</p>");

                if (!caption.isEmpty()) {
                    writer.write("<p class=\"imgfoot\">");
                    writer.write(caption);
                    writer.write("</p>");
                }
                writer.write("<div class=\"clear\"></div>");
                writer.write("</div>");
            }
            //Picture if exists, end


            //Comments,start
            if (postsData.has("comments")) {
                if (postsData.getJSONObject("comments").has("data")) {
                    JSONArray comments = postsData.getJSONObject("comments").getJSONArray("data");
                    if (comments.length() > 0) {
                        writer.write("<ul id=\"" + facebook.getId() + postsData.getString("id") + tabSuffix + "/comments\">");
                    }
                    for (int k = 0; k < comments.length(); k++) {
                        if (k == 5) {
                            break;
                        }
                        writer.write("<li>");
                        writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", comments.getJSONObject(k).getJSONObject("from").getLong("id") + "") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + comments.getJSONObject(k).getJSONObject("from").getLong("id") + "/picture?width=30&height=30\" width=\"30\" height=\"30\"/></a>");

                        writer.write("<p>");
                        writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", comments.getJSONObject(k).getJSONObject("from").getLong("id") + "") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\">" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "</a>:");
                        writer.write(comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br>");
                        writer.write("</p>");

                        Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));

                        writer.write("<p class=\"timelinedate\">");
                        //writer.write("<span id=\"" +facebook.getId() + comments.getJSONObject(k).getString("id") + "_" + postsData.getString("id") + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write("<span dojoType=\"dojox.layout.ContentPane\">");

                        writer.write("<em>" + facebookHumanFriendlyDate(commentTime, paramRequest) + "</em>");
                        if (comments.getJSONObject(k).has("like_count")) {
                            writer.write("<strong>");
                            writer.write("<span>Likes:</span> " + comments.getJSONObject(k).getInt("like_count"));
                            writer.write("</strong>");
                        }
                        writer.write("</span>");
                        writer.write("</p>");
                        writer.write("</li>");
                    }

                    if (postsData.getJSONObject("comments").has("paging")) {//Link to get more comments
                        JSONObject pagingComments = postsData.getJSONObject("comments").getJSONObject("paging");

                        if (pagingComments.has("next") && pagingComments.has("cursors")) {
                            writer.write("<li class=\"timelinemore\">");
                            //writer.write("<div id=\"" + facebook.getId() + postsData.getString("id") + tabSuffix + "/comments\" dojoType=\"dojox.layout.ContentPane\">");
                            SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode("moreComments").setParameter("suri", request.getParameter("suri")).setParameter("postId", postsData.getString("id"));
                            commentsURL = commentsURL.setParameter("after", pagingComments.getJSONObject("cursors").getString("after")).setParameter("currentTab", tabSuffix);
                            writer.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + commentsURL
                                    + "','" + facebook.getId() + postsData.getString("id") + tabSuffix + "/comments', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild( this.parentNode.parentNode );}catch(noe){}; return false;\"><span>+</span>" + paramRequest.getLocaleString("moreComments") + "</a></label>");
                            //writer.write("</div>"); 
                            writer.write("</li>");
                        }
                    }
                    if (comments.length() > 0) {
                        writer.write("</ul>");
                    }
                }
            }

            //writer.write("<span id=\"" + facebook.getId() + postsData.getString("id") + tabSuffix + "/comments\" dojoType=\"dojox.layout.ContentPane\">");
            //writer.write("</span>"); 
            //Comments, end
            writer.write("<div class=\"clear\"></div>");
            Date postTime = formatter.parse(postsData.getString("created_time"));

            writer.write("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");
            if (!postsData.isNull("icon")) {
                writer.write("<img src=\"" + postsData.getString("icon") + "\"/>");
            }
            writer.write("<span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + INFORMATION + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write("<em>" + facebookHumanFriendlyDate(postTime, paramRequest) + "</em>");
            boolean iLikedPost = false;
            writer.write("<strong><span> Likes: </span>");
            if (postsData.has("likes")) {
                JSONArray likes = postsData.getJSONObject("likes").getJSONArray("data");
                int postLikes = 0;
                if (!postsData.getJSONObject("likes").isNull("summary")) {
                    if (!postsData.getJSONObject("likes").getJSONObject("summary").isNull("total_count")) {
                        postLikes = postsData.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                    }
                }

                writer.write(String.valueOf(postLikes));

                for (int k = 0; k < likes.length(); k++) {
                    if (likes.getJSONObject(k).getString("id").equals(facebook.getFacebookUserId())) {
                        //My User id is in 'the likes' of this post
                        iLikedPost = true;
                    }
                }

                if ((likes.length() < postLikes) && (iLikedPost == false)) {
                    System.out.println("Look for postLike!!!");
                    HashMap<String, String> params = new HashMap<String, String>(3);
                    params.put("q", "SELECT post_id FROM like WHERE user_id=me() AND post_id=\"" + postsData.getString("id") + "\"");
                    params.put("access_token", facebook.getAccessToken());
                    String fbLike = null;

                    try {
                        fbLike = getRequest(params, "https://graph.facebook.com/fql",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                        System.out.println("fbLike:" + fbLike);
                        JSONObject likeResp = new JSONObject(fbLike);
                        if (likeResp.has("data")) {
                            JSONArray likesArray = likeResp.getJSONArray("data");

                            if (likesArray.length() == 1) {//There is one result, I liked this post
                                iLikedPost = true;
                            }
                        }
                    } catch (Exception e) {
                        log.error("Error getting like information for Facebook post " + postsData.getString("id"), e);
                    }
                }
            } else {
                writer.write("0");
            }
            writer.write("</strong>");
            writer.write("</span>");

            //Show like/unlike and reply (comment)
            JSONArray actions = postsData.has("actions") ? postsData.getJSONArray("actions") : null;
            if (actions != null && actions.length() > 0) {//Available actions for the post
                for (int i = 0; i < actions.length(); i++) {
                    if (actions.getJSONObject(i).getString("name").equals("Comment") && socialUserExtAttr.isUserCanRespondMsg()) {//I can comment                        
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write(" <a class=\"clasifica\" href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("postID", postsData.getString("id")) + "','Reply to " + postsData.getJSONObject("from").getString("name") + "');return false;\"><span>Reply</span></a>  ");
                        writer.write("   </span>");

                        if (linkLike != null) {
                            /*writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                             writer.write(" <a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("postID", linkLike.getString("id")) + "','Reply to " + postsData.getJSONObject("from").getString("name") + "');return false;\"><span>Reply</span></a>  ");
                             writer.write("   </span>");*/
                        }

                        ///////////////////////If I can post I can Classify it to answer it later
                        PostIn post = PostIn.getPostInbySocialMsgId(model, postsData.getString("id"));
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + TOPIC + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                        if (socialUserExtAttr.isUserCanReTopicMsg()) {
                            if (post != null) {
                                String socialT = "";
                                if (post.getSocialTopic() != null) {
                                    socialT = post.getSocialTopic().getTitle();
                                }
                                SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("id")).setParameter("postUri", post.getURI()).setParameter("currentTab", tabSuffix);
                                writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + "Tema actual: " + socialT + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                        + paramRequest.getLocaleString("reclassify") + " post'); return false;\"><span>" + paramRequest.getLocaleString("reclassify") + "</span></a>");
                            } else {
                                SWBResourceURL clasifybyTopic = renderURL.setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("id")).setParameter("currentTab", tabSuffix);
                                writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("classify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                        + paramRequest.getLocaleString("classify") + " Post'); return false;\"><span>" + paramRequest.getLocaleString("classify") + "</span></a>");
                            }
                        } else {
                            writer.write("&nbsp;");
                        }
                        writer.write("   </span>");

                    } else if (actions.getJSONObject(i).getString("name").equals("Like")) {//I can like
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + LIKE + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                        if (iLikedPost) {
                            writer.write(" <a href=\"#\" class=\"nolike\" onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("postID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() + postsData.getString("id") + INFORMATION + tabSuffix + "');return false;" + "\"><span>" + paramRequest.getLocaleString("undoLike") + "</span></a>");
                        } else {
                            writer.write(" <a href=\"#\" class=\"like\" onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("postID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() + postsData.getString("id") + INFORMATION + tabSuffix + "');return false;" + "\"><span>" + paramRequest.getLocaleString("like") + "</span></a>");
                        }
                        writer.write("   </span>");
                    } else {//Other unknown action
                        //writer.write("other:" + actions.getJSONObject(i).getString("name"));
                    }
                }
            }

            writer.write("  </div>");
            writer.write("</div>");
        } catch (Exception e) {
            log.error("Error printing post:", e);
            e.printStackTrace();
        }
    }

    public static String printPicture(Writer writer, JSONObject postsData, JSONObject commentsData, JSONObject profileData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook, SWBModel model) {
        String createdTime = "";

        try {
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("suri", request.getParameter("suri"));

            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            renderURL.setParameter("suri", request.getParameter("suri"));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            long id = 0L;
            if (profileData.has("uid")) {
                id = profileData.getLong("uid");
            } else if (profileData.has("page_id")) {
                id = profileData.getLong("page_id");
            } else {
                return null;
            }

            //TODO: id = A 64-bit int representing the user, group, page, event, or application ID
            JSONArray media = postsData.getJSONObject("attachment").getJSONArray("media");

            writer.write("<div class=\"timeline timelinefacebook\">");
            writer.write("<p>");
            writer.write("      <a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", id + "") + "','" + profileData.getString("name") + "'); return false;\">" + profileData.getString("name") + "</a>");
            writer.write("</p>");

            writer.write("<div class=\"timelineusr\">");
            writer.write("      <a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", id + "") + "','" + profileData.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + id + "/picture\"/></a>");

            if (postsData.getInt("type") == 247) {//Photo posted
                writer.write(postsData.getString("message"));
                //writer.write(":Photos posted");
            } else if (postsData.getInt("type") == 65) {//Tagged photo
                if (!postsData.isNull("description_tags")) {//Users tagged in story
                    JSONObject descriptionData = postsData.optJSONObject("description_tags");
                    if (descriptionData != null) {
                        writer.write(getTagsFromPost(postsData.getJSONObject("description_tags"), postsData.getString("description"), renderURL));
                    } else {
                        writer.write(getTagsFromPostArray(postsData.getJSONArray("description_tags").getJSONArray(0).getJSONObject(0), postsData.getString("description"), renderURL));
                    }
                }
                //writer.write(":Tagged photo");
            } else if (postsData.getInt("type") == 373) {//Cover update                
                writer.write(profileData.getString("name") + " has updated cover photo");
                //writer.write(":updated cover photo");
            } else {
                writer.write("&nbsp;");
            }

            if (!postsData.getJSONObject("attachment").isNull("fb_object_type") && postsData.getJSONObject("attachment").getString("fb_object_type").equals("album")) {
                if (!postsData.getJSONObject("attachment").getString("name").isEmpty()) {
                    writer.write(profileData.getString("name") + " has added " + media.length() + " photos to the album " + postsData.getJSONObject("attachment").getString("name"));
                }
            }

            writer.write("</div>");


            writer.write("<div class=\"timelineimg\">");
            for (int k = 0; k < 1; k++) {
                writer.write("      <span id=\"img" + facebook.getId() + media.getJSONObject(k).getString("src") + "\" style=\"width: 150px; height: 150px; border: thick #666666; overflow: hidden; position: relative;\">");
                writer.write("      <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("displayPicture").setParameter("pictureUrl", media.getJSONObject(k).getString("src").replace("_s.", "_n."))
                        + "','Picture from " + "" + "'); return false;\"><img src=\"" + media.getJSONObject(k).getString("src") + "\" style=\"position: relative;\" onload=\"imageLoad(" + "this, 'img" + facebook.getId() + media.getJSONObject(k).getString("src") + "');\"/></a>");
                writer.write("      </span>");
            }

            writer.write("<p class=\"imgtitle\">&nbsp;</p>");

            writer.write("<p class=\"imgdesc\">");
            writer.write(postsData.isNull("description") ? "" : postsData.getString("description"));
            writer.write("</p>");
            writer.write("<p class=\"imgfoot\">");
            writer.write("&nbsp;");
            writer.write("</p>");

            writer.write("<div class=\"clear\"></div>");
            writer.write("</div>");

            //Comments,start
            if (commentsData != null) {
                if (commentsData.has("data") && commentsData.getJSONArray("data").length() > 0) {


                    JSONArray comments = commentsData.getJSONArray("data");
                    if (comments.length() > 0) {
                        writer.write("<ul id=\"" + facebook.getId() + postsData.getString("post_id") + tabSuffix + "/comments\">");
                    }
                    for (int k = 0; k < comments.length(); k++) {
                        JSONObject commentProfile = comments.getJSONObject(k).getJSONObject("from");
                        writer.write("<li>");
                        writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", commentProfile.getLong("id") + "") + "','" + commentProfile.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + commentProfile.getLong("id") + "/picture?width=30&height=30\" width=\"30\" height=\"30\"/></a>");

                        writer.write("<p>");
                        writer.write("      <b><a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", commentProfile.getLong("id") + "") + "','" + commentProfile.getString("name") + "'); return false;\">" + commentProfile.getString("name") + "</a></b>:");
                        writer.write(comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br>");
                        writer.write("</p>");

                        Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));

                        writer.write("<p class=\"timelinedate\">");
                        //writer.write("<span id=\"" + comments.getJSONObject(k).getString("id") + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write("<span dojoType=\"dojox.layout.ContentPane\">");
                        writer.write("<em>" + facebookHumanFriendlyDate(commentTime, paramRequest) + "</em>");
                        //writer.write("<a href=\"\" onMouseOver=\"dijit.Tooltip.defaultPosition=['above', 'below']\" id=\"TooltipButton\" onclick=\"return false;\"> LIKE/UNLIKE</a>");
                        //writer.write("<div class=\"dijitHidden\"><span data-dojo-type=\"dijit.Tooltip\" data-dojo-props=\"connectId:'TooltipButton'\">I am <strong>above</strong> the button</span></div>");
                        if (comments.getJSONObject(k).has("like_count")) {
                            writer.write("<strong>");
                            writer.write("<span>Likes:</span> " + comments.getJSONObject(k).getInt("like_count"));
                            writer.write("</strong>");
                        }
                        writer.write("</span>");

                        writer.write("</p>");
                        writer.write("</li>");
                    }

                    if (commentsData.has("paging")) {//Link to get more comments
                        JSONObject pagingComments = commentsData.getJSONObject("paging");

                        if (pagingComments.has("next") && pagingComments.has("cursors")) {
                            writer.write("<li class=\"timelinemore\">");
                            SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode("moreComments").setParameter("suri", request.getParameter("suri")).setParameter("postId", postsData.getString("post_id"));
                            commentsURL = commentsURL.setParameter("after", pagingComments.getJSONObject("cursors").getString("after")).setParameter("currentTab", tabSuffix);
                            writer.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + commentsURL
                                    + "','" + facebook.getId() + postsData.getString("post_id") + tabSuffix + "/comments', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild( this.parentNode.parentNode );}catch(noe){}; return false;\"><span>+</span>" + paramRequest.getLocaleString("moreComments") + "</a></a></label>");
                            writer.write("</li>");
                        }
                    }

                    if (comments.length() > 0) {
                        writer.write("   </ul>");
                    }
                }
            }


            Date postTime = new java.util.Date((long) postsData.getLong("created_time") * 1000);
            createdTime = String.valueOf(postsData.getLong("created_time"));
            boolean canLike = false;
            JSONObject likeInfo = null;
            writer.write("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");

            writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + INFORMATION + PICTURES_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write("<em>" + facebookHumanFriendlyDate(postTime, paramRequest) + "</em>");

            if (postsData.has("like_info")) {
                likeInfo = postsData.getJSONObject("like_info");
                writer.write("<strong>");
                writer.write("<span>Likes:</span> " + likeInfo.getLong("like_count"));
                writer.write("</strong>");
                writer.write("</span>");
                if (!likeInfo.isNull("can_like")) {
                    if (likeInfo.getBoolean("can_like")) {
                        canLike = true;
                    }
                }
            } else {
                writer.write("   </span>");
            }

            if (postsData.has("comment_info")) {
                JSONObject comments = postsData.getJSONObject("comment_info");

                if (comments.getBoolean("can_comment")) {
                    writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                    writer.write(" <a class=\"clasifica\" href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("postID", postsData.getString("post_id")) + "','Reply to " + profileData.getString("name") + "');return false;\"><span>Reply</span></a>  ");
                    writer.write("   </span>");

                    ///////////////////////If I can post I can Classify it to answer it later
                    PostIn post = PostIn.getPostInbySocialMsgId(model, postsData.getString("post_id"));
                    writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + TOPIC + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                    if (post != null) {
                        String socialT = "";
                        if (post.getSocialTopic() != null) {
                            socialT = post.getSocialTopic().getTitle();
                        }
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("post_id")).setParameter("postUri", post.getURI()).setParameter("currentTab", tabSuffix);
                        writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + "Tema actual: " + socialT + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                + paramRequest.getLocaleString("reclassify") + " post'); return false;\"><span>" + paramRequest.getLocaleString("reclassify") + "</span></a>");
                    } else {
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("post_id")).setParameter("currentTab", tabSuffix);
                        writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("classify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                + paramRequest.getLocaleString("classify") + " Post'); return false;\"><span>" + paramRequest.getLocaleString("classify") + "</span></a>");
                    }
                    writer.write("   </span>");

                }
            }

            if (canLike) {
                writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + LIKE + PICTURES_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
                if (likeInfo != null && likeInfo.getBoolean("user_likes")) {
                    writer.write(" <a href=\"#\" class=\"nolike\" onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("postID", postsData.getString("post_id")).setParameter("currentTab", PICTURES_TAB) + "','" + facebook.getId() + postsData.getString("post_id") + INFORMATION + PICTURES_TAB + "');return false;" + "\"><span>" + paramRequest.getLocaleString("undoLike") + "</span></a>");
                } else {
                    writer.write(" <a href=\"#\" class=\"like\" onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("postID", postsData.getString("post_id")).setParameter("currentTab", PICTURES_TAB) + "','" + facebook.getId() + postsData.getString("post_id") + INFORMATION + PICTURES_TAB + "');return false;" + "\"><span>" + paramRequest.getLocaleString("like") + "</span></a>");
                }
                writer.write("   </span>");
            }
            writer.write("   </div>");
            writer.write("   </div>");
        } catch (Exception e) {
            log.error("ERROR printing Picture", e);
        }
        return createdTime;
    }

    public static String doPrintVideo(Writer writer, JSONObject postsData, JSONObject commentsData, JSONObject profileData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook, SWBModel model) {
        String createdTime = "";
        try {
            if (postsData.getInt("type") != 128) {//Only print published videos
                return null;
            }

            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("suri", request.getParameter("suri"));

            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            renderURL.setParameter("suri", request.getParameter("suri"));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            long id = 0L;
            if (profileData.has("uid")) {
                id = profileData.getLong("uid");
            } else if (profileData.has("page_id")) {
                id = profileData.getLong("page_id");
            } else {
                return null;
            }

            JSONArray media = null;
            writer.write("<div class=\"timeline timelinefacebook\">");
            writer.write("<p>");
            writer.write("      <a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", id + "") + "','" + profileData.getString("name") + "'); return false;\">" + profileData.getString("name") + "</a>");
            writer.write("</p>");

            writer.write("<div class=\"timelineusr\">");
            writer.write("      <a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", id + "") + "','" + profileData.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + id + "/picture\"/></a>");

            if (postsData.getInt("type") == 128) {//Video posted
                writer.write(postsData.getString("message"));
            } else {
                writer.write("&nbsp;");
            }

            if (!postsData.isNull("attachment")) {
                if (!postsData.getJSONObject("attachment").isNull("media")) {
                    media = postsData.getJSONObject("attachment").getJSONArray("media");
                }
            }
            if (media == null) {
                return null;
            }
            writer.write("</div>");


            writer.write("<div class=\"timelineimg\">");
            for (int k = 0; k < 1; k++) {
                writer.write("      <span id=\"vid" + tabSuffix + facebook.getId() + postsData.getString("post_id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                writer.write("      <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode(media.getJSONObject(k).getJSONObject("video").getString("source_url"), "UTF-8"))
                        + "','Video'); return false;\"><img src=\"" + media.getJSONObject(k).getString("src") + "\" style=\"position: relative;\" '\" onload=\"imageLoad(" + "this, 'vid" + tabSuffix + facebook.getId() + postsData.getString("post_id") + "');\"/></a>");
                writer.write("      </span>");
            }
            writer.write("<p class=\"imgtitle\">&nbsp;</p>");

            writer.write("<p class=\"imgdesc\">");
            writer.write(postsData.isNull("description") ? "" : postsData.getString("description"));
            writer.write("</p>");

            writer.write("<p class=\"imgfoot\">");
            writer.write("&nbsp;");
            writer.write("</p>");

            writer.write("<div class=\"clear\"></div>");
            writer.write("</div>");

            //Comments,start
            if (commentsData != null) {
                if (commentsData.has("data") && commentsData.getJSONArray("data").length() > 0) {


                    JSONArray comments = commentsData.getJSONArray("data");
                    if (comments.length() > 0) {
                        writer.write("<ul id=\"" + facebook.getId() + postsData.getString("post_id") + tabSuffix + "/comments\">");
                    }
                    for (int k = 0; k < comments.length(); k++) {
                        JSONObject commentProfile = comments.getJSONObject(k).getJSONObject("from");
                        writer.write("<li>");
                        writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", commentProfile.getLong("id") + "") + "','" + commentProfile.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + commentProfile.getLong("id") + "/picture?width=30&height=30\" width=\"30\" height=\"30\"/></a>");

                        writer.write("<p>");
                        writer.write("      <b><a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", commentProfile.getLong("id") + "") + "','" + commentProfile.getString("name") + "'); return false;\">" + commentProfile.getString("name") + "</a></b>:");
                        writer.write(comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br>");
                        writer.write("</p>");

                        Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));

                        writer.write("<p class=\"timelinedate\">");
                        //writer.write("<span id=\"" + comments.getJSONObject(k).getString("id") + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write("<span dojoType=\"dojox.layout.ContentPane\">");
                        writer.write("<em>" + facebookHumanFriendlyDate(commentTime, paramRequest) + "</em>");

                        if (comments.getJSONObject(k).has("like_count")) {
                            writer.write("<strong>");
                            writer.write("<span>Likes:</span> " + comments.getJSONObject(k).getInt("like_count"));
                            writer.write("</strong>");
                        }
                        writer.write("</span>");

                        writer.write("</p>");
                        writer.write("</li>");
                    }

                    if (commentsData.has("paging")) {//Link to get more comments
                        JSONObject pagingComments = commentsData.getJSONObject("paging");

                        if (pagingComments.has("next") && pagingComments.has("cursors")) {
                            writer.write("<li class=\"timelinemore\">");
                            SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode("moreComments").setParameter("suri", request.getParameter("suri")).setParameter("postId", postsData.getString("post_id"));
                            commentsURL = commentsURL.setParameter("after", pagingComments.getJSONObject("cursors").getString("after")).setParameter("currentTab", tabSuffix);
                            writer.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + commentsURL
                                    + "','" + facebook.getId() + postsData.getString("post_id") + tabSuffix + "/comments', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild( this.parentNode.parentNode );}catch(noe){}; return false;\"><span>+</span>" + paramRequest.getLocaleString("moreComments") + "</a></a></label>");
                            writer.write("</li>");
                        }
                    }

                    if (comments.length() > 0) {
                        writer.write("   </ul>");
                    }
                }
            }

            Date postTime = new java.util.Date((long) postsData.getLong("created_time") * 1000);
            createdTime = String.valueOf(postsData.getLong("created_time"));
            boolean canLike = false;
            JSONObject likeInfo = null;
            writer.write("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");

            writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + INFORMATION + VIDEOS_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write("<em>" + facebookHumanFriendlyDate(postTime, paramRequest) + "</em>");

            if (!postsData.isNull("like_info")) {
                likeInfo = postsData.getJSONObject("like_info");
                writer.write("<strong>");
                writer.write("<span>Likes:</span> " + likeInfo.getLong("like_count"));
                writer.write("</strong>");
                writer.write("</span>");
                if (!likeInfo.isNull("can_like")) {
                    if (likeInfo.getBoolean("can_like")) {
                        canLike = true;
                    }
                }

            } else {
                writer.write("   </span>");
            }

            if (postsData.has("comment_info")) {
                JSONObject comments = postsData.getJSONObject("comment_info");

                if (comments.getBoolean("can_comment")) {
                    writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                    writer.write(" <a class=\"clasifica\" href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("postID", postsData.getString("post_id")) + "','Reply to " + profileData.getString("name") + "');return false;\"><span>Reply</span></a>  ");
                    writer.write("   </span>");

                    PostIn post = PostIn.getPostInbySocialMsgId(model, postsData.getString("post_id"));
                    writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + TOPIC + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                    System.out.println("POST:" + post);
                    if (post != null) {
                        String socialT = "";
                        if (post.getSocialTopic() != null) {
                            socialT = post.getSocialTopic().getTitle();
                        }
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("post_id")).setParameter("postUri", post.getURI()).setParameter("currentTab", tabSuffix);
                        writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + "Tema actual: " + socialT + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                + paramRequest.getLocaleString("reclassify") + " post'); return false;\"><span>" + paramRequest.getLocaleString("reclassify") + "</span></a>");
                    } else {
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("post_id")).setParameter("currentTab", tabSuffix);
                        writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("classify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                + paramRequest.getLocaleString("classify") + " Post'); return false;\"><span>" + paramRequest.getLocaleString("classify") + "</span></a>");
                    }
                    writer.write("   </span>");

                }
            }

            if (canLike) {
                writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + LIKE + VIDEOS_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
                if (likeInfo != null && likeInfo.getBoolean("user_likes")) {
                    writer.write(" <a href=\"#\" class=\"nolike\" onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("postID", postsData.getString("post_id")).setParameter("currentTab", VIDEOS_TAB) + "','" + facebook.getId() + postsData.getString("post_id") + INFORMATION + VIDEOS_TAB + "');return false;" + "\"><span>" + paramRequest.getLocaleString("undoLike") + "</span></a>");
                } else {
                    writer.write(" <a href=\"#\" class=\"like\" onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("postID", postsData.getString("post_id")).setParameter("currentTab", VIDEOS_TAB) + "','" + facebook.getId() + postsData.getString("post_id") + INFORMATION + VIDEOS_TAB + "');return false;" + "\"><span>" + paramRequest.getLocaleString("like") + "</span></a>");
                }
                writer.write("   </span>");
            }
            writer.write("   </div>");
            writer.write("   </div>");
        } catch (Exception e) {
            log.error("ERROR printing Video", e);
        }
        return createdTime;
    }

    public void doAskIfNewPosts(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();
        if (objUri != null) {
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }

        //SI ESTÁ HACIENDO ALGO EL "doGetStreamUser" no debe mostrar el mensaje hasta que termine

        HttpSession session = request.getSession(true);
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        System.out.println("CURRENTTAB:" + currentTab);
        System.out.println("Value from since var:" + session.getAttribute(objUri + currentTab + "since") + "\n\n");
        params.put("since", session.getAttribute(objUri + currentTab + "since").toString());// since param used to get newer post
        String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        String processing = (String) session.getAttribute(objUri + currentTab + "processing");
        if (processing != null && processing.equals("true")) {
            return;//Don't refresh messages
        }
        try {
            JSONObject phraseResp = new JSONObject(fbResponse);
            if (phraseResp.has("data")) {
                JSONArray postsData = phraseResp.getJSONArray("data");
                int postsToRemove = 0;//Remove all te posts that are not relevant:likes a photo, commented on a photo, likes his/her own link, etc

                for (int i = 0; i < postsData.length(); i++) {
                    String postType = postsData.getJSONObject(i).getString("type");

                    if (postType.equals("photo")) {
                        if (postsData.getJSONObject(i).has("application")) {
                            postsToRemove++;
                        }
                    } else if (postType.equals("link") || postType.equals("status")) {
                        String story;
                        if (!postsData.getJSONObject(i).isNull("story")) {
                            story = (!postsData.getJSONObject(i).isNull("story")) ? postsData.getJSONObject(i).getString("story") : "";
                            if (story.contains("is going to an event") && postsData.getJSONObject(i).has("link")) {//If the link is an event
                                postsToRemove++;
                            }
                            if (story.contains("likes a photo")) {
                                postsToRemove++;
                            } else if (story.contains("likes a link")) {
                                postsToRemove++;
                            } else if (story.contains("likes a status")) {
                                postsToRemove++;
                            } else if (story.contains("commented on")) {
                                postsToRemove++;
                            } else if (story.contains("likes")) {
                                postsToRemove++;
                            } else if (story.contains("is going to")) {
                                postsToRemove++;
                            } else if (story.contains("created an event")) {
                                postsToRemove++;
                            }
                        }
                    }
                }
                System.out.println("ARREGLO DE DATOS NEWSFEED:" + postsData.length());
                //if(postsData.length()>0){
                if ((postsData.length() - postsToRemove) > 0) {
                    out.println("<a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("doGetStreamUser").setParameter("currentTab", currentTab) + "','" + objUri + "facebookStream','top'); try{dojo.byId(this.parentNode.id).innerHTML = '';}catch(noe){}; return false;\">You have <b>" + (postsData.length() - postsToRemove) + "</b> new post" + ((postsData.length() - postsToRemove) > 1 ? "s" : "") + "</a>");
                }
            }
        } catch (JSONException jsone) {
            log.error("Problemas al parsear respuesta de Facebook al preguntar si hay nuevos posts" + jsone);
        }
        //params.put("since", session.getAttribute(objUri));
        String sinceIdWall = (String) session.getAttribute(objUri + WALL_TAB + "since");
        if (sinceIdWall != null) {
            params.put("since", sinceIdWall);
            String fbResponseWall = postRequest(params, "https://graph.facebook.com/me/feed",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            try {
                JSONObject phraseResp = new JSONObject(fbResponseWall);
                if (phraseResp.has("data")) {
                    JSONArray postsData = phraseResp.getJSONArray("data");
                    int postsToRemove = 0;//Remove all te posts that are not relevant:likes a photo, commented on a photo, likes his/her own link, etc

                    for (int i = 0; i < postsData.length(); i++) {
                        String postType = postsData.getJSONObject(i).getString("type");

                        if (postType.equals("photo")) {
                            if (postsData.getJSONObject(i).has("application")) {
                                postsToRemove++;
                            }
                        } else if (postType.equals("link") || postType.equals("status")) {
                            String story;
                            if (!postsData.getJSONObject(i).isNull("story")) {
                                story = (!postsData.getJSONObject(i).isNull("story")) ? postsData.getJSONObject(i).getString("story") : "";
                                if (story.contains("is going to an event") && postsData.getJSONObject(i).has("link")) {//If the link is an event
                                    postsToRemove++;
                                }
                                if (story.contains("likes a photo")) {
                                    postsToRemove++;
                                } else if (story.contains("likes a link")) {
                                    postsToRemove++;
                                } else if (story.contains("likes a status")) {
                                    postsToRemove++;
                                } else if (story.contains("commented on")) {
                                    postsToRemove++;
                                } else if (story.contains("likes")) {
                                    postsToRemove++;
                                } else if (story.contains("is going to")) {
                                    postsToRemove++;
                                } else if (story.contains("created an event")) {
                                    postsToRemove++;
                                }
                            }
                        }
                    }

                    System.out.println("ARREGLO DE DATOS IN WALL:" + postsData.length());
                    if ((postsData.length() - postsToRemove) > 0) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("   var tabId = '" + objUri + WALL_TAB + "';");
                        out.println("   var pane = dijit.byId(tabId);");
                        out.println("   try{");
                        out.println("       var aux='New posts (" + (postsData.length() - postsToRemove) + ")';");
                        out.println("       pane.title = aux;");
                        out.println("       pane.controlButton.containerNode.innerHTML = aux;");
                        out.println("   }catch(noe){");
                        out.println("       alert('Error setting title: ' + noe);");
                        out.println("   }");

                        out.println("   var wall = '" + objUri + "newPostsWallAvailable';");
                        out.println("   var hrefVal='<a href=\"#\" onclick=\"appendHtmlAt(\\'" + renderURL.setMode("doGetStreamUser").setParameter("suri", objUri).setParameter("currentTab", WALL_TAB)
                                + "\\',\\'" + objUri + "facebookWallStream\\',\\'top\\'); try{dojo.byId(this.parentNode.id).innerHTML = \\'\\';}catch(noe){}; resetTabTitle(\\'" + objUri + "\\', \\'" + WALL_TAB + "\\', \\'Wall\\'); return false;\">You have <b>"
                                + (postsData.length() - postsToRemove) + "</b> new post" + ((postsData.length() - postsToRemove) > 1 ? "s" : "") + "</a>';");
                        out.println("   try{");
                        out.println("      document.getElementById(wall).innerHTML = hrefVal;");
                        out.println("   }catch(noe){}");
                        out.println("</script>");
                    }
                }
            } catch (JSONException jsone) {
                log.error("Problemas al parsear respuesta de Facebook al preguntar si hay nuevos posts" + jsone);
            }
        }
        //session.setAttribute(objUri + tabSuffix + "since", since);
        String sinceIdPicture = (String) session.getAttribute(objUri + PICTURES_TAB + "since");
        if (sinceIdPicture != null) {
            params.put("since", sinceIdPicture);
            params.put("q", "{\"pictures\": \"SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comments FROM stream WHERE filter_key IN "
                    + "( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Photos') AND created_time >" + sinceIdPicture + " LIMIT 50\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT actor_id FROM #pictures)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT actor_id FROM #pictures)\"}");
            String fbResponsePic = getRequest(params, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            try {
                JSONObject phraseResp = new JSONObject(fbResponsePic);
                JSONArray postsData = null;

                //Get only the size of pictures array
                for (int i = 0; i < phraseResp.getJSONArray("data").length(); i++) {
                    if (phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pictures")) {//All the posts
                        postsData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                        break;
                    }
                }

                if (postsData != null && postsData.length() > 0) {
                    System.out.println("hay posts in PICTURE:" + postsData.length());
                    out.println("<script type=\"text/javascript\">");
                    out.println("   var tabId = '" + objUri + PICTURES_TAB + "';");
                    out.println("   var pane = dijit.byId(tabId);");
                    out.println("   try{");
                    out.println("       var aux='New pictures (" + postsData.length() + ")';");
                    out.println("       pane.title = aux;");
                    out.println("       pane.controlButton.containerNode.innerHTML = aux;");
                    out.println("   }catch(noe){");
                    out.println("       alert('Error setting title: ' + noe);");
                    out.println("   }");

                    out.println("   var wall = '" + objUri + "newPicturesAvailable';");
                    out.println("   var hrefVal='<a href=\"#\" onclick=\"appendHtmlAt(\\'" + renderURL.setMode("doGetStreamPictures").setParameter("suri", objUri).setParameter("currentTab", PICTURES_TAB)
                            + "\\',\\'" + objUri + "picturesStream\\',\\'top\\'); try{dojo.byId(this.parentNode.id).innerHTML = \\'\\';}catch(noe){}; resetTabTitle(\\'" + objUri + "\\', \\'" + PICTURES_TAB + "\\', \\'Media\\'); return false;\">You have <b>"
                            + postsData.length() + "</b> new picture" + (postsData.length() > 1 ? "s" : "") + "</a>';");
                    out.println("   try{");
                    out.println("      document.getElementById(wall).innerHTML = hrefVal;");
                    out.println("   }catch(noe){}");
                    out.println("</script>");
                }
            } catch (JSONException jsone) {
                log.error("Problemas al parsear respuesta de Facebook al preguntar si hay nuevos posts" + jsone);
            }
        }


    }

    public void doGetNewPosts(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();
        SWBModel model = WebSite.ClassMgr.getWebSite(facebook.getSemanticObject().getModel().getName());
        if (objUri != null) {
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(objUri + currentTab + "processing", "true");
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        System.out.println("Value from since var:" + session.getAttribute(objUri + currentTab + "since") + "\n\n");
        params.put("since", session.getAttribute(objUri + currentTab + "since").toString());// since param used to get newer post
        String fbResponse = "";
        if (currentTab.equals(NEWS_FEED_TAB)) {
            fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        } else if (currentTab.equals(WALL_TAB)) {
            fbResponse = postRequest(params, "https://graph.facebook.com/me/feed",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        }
        String untilPost = parseResponse(fbResponse, out, true, request, paramRequest, currentTab, model);
        session.setAttribute(objUri + currentTab + "processing", "false");
    }

    public void doGetNewPictures(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();
        SWBModel model = WebSite.ClassMgr.getWebSite(facebook.getSemanticObject().getModel().getName());
        if (objUri != null) {
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(objUri + currentTab + "processing", "true");
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());

        String sinceIdPicture = (String) session.getAttribute(objUri + PICTURES_TAB + "since");
        if (sinceIdPicture != null) {
            params.put("since", sinceIdPicture);
            params.put("q", "{\"pictures\": \"SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comments FROM stream WHERE filter_key IN "
                    + "( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Photos') AND created_time >" + sinceIdPicture + " LIMIT 50\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT actor_id FROM #pictures)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT actor_id FROM #pictures)\"}");
            String fbResponsePic = getRequest(params, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            String createdTime = picture(fbResponsePic, out, true, request, paramRequest, model);//Gets the newest post and saves the ID of the last one
        }
    }

    //SELECT id, name, type FROM profile where id =
    //Only need the id, name and user type from 'id' 
    //The ID of the user, page, group, or event that published the post
    public static String getProfileFromId(String id, Facebook facebook) {
        HashMap<String, String> params1 = new HashMap<String, String>(3);
        params1.put("q", "SELECT id, name, type FROM profile where id = " + id);
        params1.put("access_token", facebook.getAccessToken());

        String fbResponse = null;
        try {
            fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        } catch (Exception e) {
            log.error("Error getting user information", e);
        }
        return fbResponse;
    }

    public static String getUserInfoFromId(String id, Facebook facebook) {
        HashMap<String, String> params1 = new HashMap<String, String>(3);
        params1.put("q", "SELECT uid, name, first_name, middle_name, last_name FROM user WHERE uid = " + id);
        params1.put("access_token", facebook.getAccessToken());

        String fbResponse = null;
        try {
            fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        } catch (Exception e) {
            log.error("Error getting user information", e);
        }
        return fbResponse;
    }

    public static String getPageInfoFromId(String id, Facebook facebook) {
        HashMap<String, String> params1 = new HashMap<String, String>(3);
        params1.put("q", "SELECT page_id, name , type FROM page where page_id = " + id);
        params1.put("access_token", facebook.getAccessToken());

        String fbResponse = null;
        try {
            fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        } catch (Exception e) {
            log.error("Error getting page information", e);
        }
        return fbResponse;
    }

    public static String facebookHumanFriendlyDate(Date created, SWBParamRequest paramRequest) {
        Date today = new Date();
        Long duration = today.getTime() - created.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        String date = "";
        try {
            if (duration < second * 7) {//Less than 7 seconds
                date = paramRequest.getLocaleString("rightNow");
            } else if (duration < minute) {
                int n = (int) Math.floor(duration / second);
                date = n + " " + paramRequest.getLocaleString("secondsAgo");
            } else if (duration < minute * 2) {//Less than 2 minutes
                date = paramRequest.getLocaleString("about") + " 1 " + paramRequest.getLocaleString("minuteAgo");
            } else if (duration < hour) {
                int n = (int) Math.floor(duration / minute);
                date = n + " " + paramRequest.getLocaleString("minutesAgo");
            } else if (duration < hour * 2) {//Less than 1 hour
                date = paramRequest.getLocaleString("about") + " 1 " + paramRequest.getLocaleString("hourAgo");
            } else if (duration < day) {
                int n = (int) Math.floor(duration / hour);
                date = n + " " + paramRequest.getLocaleString("hoursAgo");
            } else if (duration > day && duration < day * 2) {
                date = paramRequest.getLocaleString("yesterday");
            } else {
                int n = (int) Math.floor(duration / day);
                date = n + " " + paramRequest.getLocaleString("daysAgo");
            }
        } catch (Exception e) {
            log.error("Problem found computing time of post. ", e);
        }
        return date;
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

    private void moreView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException, JSONException {
 
        String url = request.getParameter("nextPage");
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        String objUri = request.getParameter("suri");
        String fbResponse = "";
        renderURL.setParameter("suri", objUri);
        PrintWriter out = response.getWriter();

        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();

        SWBModel model = WebSite.ClassMgr.getWebSite(facebook.getSemanticObject().getModel().getName());
        if (objUri != null) {
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }

        fbResponse = postRequest(url,
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        System.out.println(" /n/n/n/n/n EN EL RECURSO : "+fbResponse);
        JSONObject phraseResp = new JSONObject(fbResponse);
        int cont = 0;
        JSONArray postsData = phraseResp.getJSONArray("data");
        JSONObject object = new JSONObject();
        String next = "";
        String image = "";
        String name = "";
        for (int k = 0; k < postsData.length(); k++) {
            cont++;
            object = (JSONObject) postsData.get(k);
            //System.out.println("\n\nPost de FACEBOOOK*********: " + postsData.getJSONObject(k));

           
            image = object.getString("id");
            name = object.getString("name");
            out.println("<img src=\"https://graph.facebook.com/" + image + "/picture?width=150&height=150\" width=\"150\" height=\"150\" />");
            out.println("" + name + "");
             out.println("<br>");


        }

        if (phraseResp.has("paging")) {
            if(phraseResp.getJSONObject("paging").has("next")){
            next = phraseResp.getJSONObject("paging").getString("next");
            }
            if(request.getParameter("type").equals("friends")){
            out.println("<a href=\"#\" onclick=\"javascript:postHtml('" + paramRequest.getRenderUrl().setAction("more").setParameter("type", "friends").setParameter("suri", "http://www.Prueba.swb#social_Facebook:1").setParameter("nextPage", next) + " ', 'moreFriends"+facebook+"')\" >Mas amigos");
            out.println("</a>");
            }else if(request.getParameter("type").equals("subscriber")) {
             out.println("<a href=\"#\" onclick=\"javascript:postHtml('" + paramRequest.getRenderUrl().setAction("more").setParameter("type", "subscriber").setParameter("suri", "http://www.Prueba.swb#social_Facebook:1").setParameter("nextPage", next) + " ', 'moreSubscriber"+facebook+"')\" >Mas seguidores");
            out.println("</a>");
            }
        }


    }

    public static String postRequest(String url,
            String userAgent, String method) throws IOException {

        // CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url);

        //URL serverUrl = new URL(url);
        System.out.println("URL:" + serverUrl);
        //System.out.println("paramString:" + paramString);

        HttpURLConnection conex = null;
        OutputStream out = null;
        InputStream in = null;
        String response = null;

        if (method == null) {
            method = "POST";
        }
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod(method);
            conex.setDoOutput(true);
            conex.connect();

            //System.out.println("CONNECT:" + conex);
            //   out = conex.getOutputStream();
            //   out.write(paramString.toString().getBytes("UTF-8"));
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                //System.out.println("ERROR:" +   getResponse(conex.getErrorStream()));
                response = getResponse(conex.getErrorStream());
                log.error("\n\nERROR:" + response);
            }
            //ioe.printStackTrace();
        } finally {
            close(in);
            //close(out);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }
}
