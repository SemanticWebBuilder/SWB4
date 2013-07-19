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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.regex.*;
import javax.servlet.RequestDispatcher;
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
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceURL;

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
    public static String REPLY ="/rep";
    
    /*Additionally every div has a suffix to identify if the status is inside the tab of
     home, mentions, favorites or myTweets */ 
    public static String NEWS_FEED_TAB = "/newsfeed";
    public static String WALL_TAB = "/wall";
    public static String MEDIA_TAB = "/media";
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String objUri = (String) request.getParameter("suri");
        System.out.println("SURI:" + objUri);
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebookBean = (Facebook)semanticObject.createGenericInstance();
        String contentTabId = request.getParameter("contentTabId");
        
        if(contentTabId == null){
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/facebookTabs.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            }catch (Exception e) {
                System.out.println("Error loading the Twitter Tabs " + e);
            }
            return;
        }
        
        String jspResponse = "";
        if(contentTabId != null && contentTabId.equals("wall")){        
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/facebookWall.jsp";
        }else if(contentTabId != null && contentTabId.equals("newsFeed")){        
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/facebookNewsFeed.jsp";
        }else if(contentTabId != null && contentTabId.equals("pictures")){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/facebookPictures.jsp";
        }else if(contentTabId != null && contentTabId.equals("videos")){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/facebookVideos.jsp";
        }
        
        //HttpSession session = request.getSession(true);
        //session.setAttribute("since", "0");// since param used to get newer post
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("facebookBean", facebookBean);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }
    }
    
    /**
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por alg&uacute;n m&eacute;todo en particular
     * @param params contiene los par&aacute;metros a enviar a Facebook para realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea interactuar
     * @param userAgent indica el navegador de Internet utilizado en la petici&oacute;n a realizar
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido por Facebook para realizar
     *          una operaci&oacute;n, como: {@literal POST}, {@literal DELETE} o {@literal GET}
     * @return un {@code String} que representa la respuesta generada por el grafo de Facebook
     * @throws IOException en caso de que se produzca un error al generar la petici&oacute;n
     *          o recibir la respuesta del grafo de Facebook
     */
    public static String postRequest(Map<String, String> params, String url,
        String userAgent, String method) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" +  paramString);
        
        //URL serverUrl = new URL(url);
        System.out.println("URL:" +  serverUrl);
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
                System.out.println("\n\n\nERROR:" +   response);
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
        URL serverUrl = new URL(url + "?" +  paramString);       
        System.out.println("URL:" +  serverUrl);
        
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
            System.out.println("RESPONSE:" + response);
                        
        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                System.out.println("ERROR:" +   getResponse(conex.getErrorStream()));
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
                log.error("Error at closing object: " + c.getClass().getName(),
                        ex);
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if(action.equals("doLike")){//Like works for post from your friends and for posts from users you gave likes
            System.out.println("LIKE-Start");
            String objUri = (String) request.getParameter("suri");
            String commentID = (String)request.getParameter("commentID");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook)semanticObject.createGenericInstance();
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + commentID + "/likes",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
            if(!fbResponse.equals("true")){//If the response is not true, there was an error
                try {
                    JSONObject likeResponse = new JSONObject(fbResponse);
                    if(likeResponse.has("error")){
                        response.setRenderParameter("error", likeResponse.getJSONObject("error").getString("message"));
                    }
                } catch (JSONException ex) {
                    log.error("Error doing like action " +  ex );
                }
            }
            //System.out.println("Response: " + fbResponse);
            response.setRenderParameter("commentID", commentID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            response.setRenderParameter("liked", "ok");
            System.out.println("LIKE-End");
            response.setMode("likeSent"); //show Like Message and update div
            
        }else if(action.equals("doUnlike")){    //If you liked a post from your friends you can do unlike simply
            System.out.println("UNLIKE-Start"); //If you liked a post from a user you gave like, you cannot give unlike
            String objUri = (String) request.getParameter("suri");
            String commentID = (String)request.getParameter("commentID");
            
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook)semanticObject.createGenericInstance();
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            System.out.println("\n\nComment ID:" + commentID.substring(commentID.indexOf('_') + 1));
            String fbResponse ="";
            fbResponse= postRequest(params, "https://graph.facebook.com/" + commentID + "/likes" ,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "DELETE");
            if(!fbResponse.equals("true")){//If the response is not true, there was an error
                System.out.println("ERROR TRYING TO UNLIKE ID:" + fbResponse);                
                    try {
                        JSONObject likeResponse = new JSONObject(fbResponse);
                        if(likeResponse.has("error")){                            
                            response.setRenderParameter("error", likeResponse.getJSONObject("error").getString("message"));
                        }
                    } catch (JSONException ex) {
                        log.error("Error doing like action " +  ex );
                    }            
            }
            
            response.setRenderParameter("commentID", commentID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            response.setRenderParameter("unliked", "ok");
            System.out.println("UNLIKE-End");
            response.setMode("unlikeSent"); //show Like Message and update div
        }else if(action.equals("sendReply")){
            try {
                String answer = request.getParameter("replyText");
                System.out.println("Answer Text:" + answer);
                    System.out.println("Reply-Start"); //If you liked a post from a user you gave like, you cannot give unlike
                    String objUri = (String) request.getParameter("suri");
                    String commentID = (String)request.getParameter("commentID");
                    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
                    Facebook facebook = (Facebook)semanticObject.createGenericInstance();
                    HashMap<String, String> params = new HashMap<String, String>(2);
                    params.put("access_token", facebook.getAccessToken());
                    params.put("message", answer);
                    System.out.println("\n\nComment ID:" + commentID);
                    String fbResponse = postRequest(params, "https://graph.facebook.com/" + commentID + "/comments" ,
                                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                    System.out.println("Response: " + fbResponse);
                    response.setRenderParameter("commentID", commentID);
                    response.setRenderParameter("suri", objUri);
                    System.out.println("Reply-End");
                //twitter.updateStatus(new StatusUpdate(answer).inReplyToStatusId(id));
                response.setRenderParameter("repliedPost", "ok");
                response.setMode("postSent");                
            } catch (Exception ex) {
                log.error("Error when trying to reply ", ex);
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        String currentTab = request.getParameter("currentTab");
        actionURL.setParameter("suri", request.getParameter("suri"));
        renderURL.setParameter("suri",request.getParameter("suri"));
        System.out.println("suri:" + request.getParameter("suri"));
        System.out.println("mode:" + mode);
        PrintWriter out = response.getWriter();
        if(mode!= null && mode.equals("getMorePosts")){//Gets older posts
            System.out.println("brings more POSTS - OLDER");
            doGetMorePosts(request, response, paramRequest);
        }else if(mode!= null && mode.equals("newPostsAvailable")){//Gets the number of new posts if available
            System.out.println("brings more posts - NUMBER OF NEW POSTS");
            doAskIfNewPosts(request, response, paramRequest);
        }else if(mode!= null && mode.equals("doGetStreamUser")){//Gets the new posts of the user and don't reload the page
            System.out.println("brings more posts - NEWER");
            doGetNewPosts(request, response, paramRequest);
        }else if(mode!= null && mode.equals("getMorePictures")){//Gets older pictures
            System.out.println("brings more PICTURES - OLDER");
            doGetMorePictures(request, response, paramRequest);
        }else if(mode!= null && mode.equals("likeSent") || mode.equals("unlikeSent")){//Displays updated data of liked/unliked status
            String commentID = request.getParameter("commentID");
            String objUri = request.getParameter("suri");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook)semanticObject.createGenericInstance();
            
            try {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("access_token", facebook.getAccessToken());
                params.put("fields","id,from,likes");
                String fbResponse = getRequest(params, "https://graph.facebook.com/" + commentID + "/" ,
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            
                System.out.println("Facebook response:" + fbResponse);
                JSONObject likeResp = new JSONObject(fbResponse);
                String facebookDate = likeResp.getString("created_time");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
                formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));  

                Date likeTime = formatter.parse(facebookDate);
                out.write(facebookHumanFriendlyDate(likeTime));
                //IMPRIMIR EL CREATED Y LOS NUEVOS LIKES
                boolean iLikedPost = false;
                if(likeResp.has("likes")){
                    JSONArray likes = likeResp.getJSONObject("likes").getJSONArray("data");
                    out.println(" Likes: <b>" + likeResp.getJSONObject("likes").getLong("count") );
                    for (int k = 0; k < likes.length(); k++) {
                        //out.write(likes.getJSONObject(k).getString("name") + ", ");
                        if(likes.getJSONObject(k).getString("id").equals(facebook.getFacebookUserId())){
                            //My User id is in 'the likes' of this post
                            iLikedPost = true;
                        }
                    }
                    out.write("</b>");
                }
                
                //MOSTRAR SOLO EL LIKE                
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   var spanId = dijit.byId('" + facebook.getId() + commentID + LIKE + currentTab +  "');");
                String likeStatus = null;
                if(iLikedPost){
                    likeStatus =" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("commentID", likeResp.getString("id")).setParameter("currentTab", currentTab)+ "','" + facebook.getId() + commentID + INFORMATION + currentTab + "');return false;" +"\">Unlike</a>";
                    if(request.getParameter("error") != null){
                        out.println("   showStatus('ERROR: " +request.getParameter("error") +"');");
                    }else{
                        out.println("   showStatus('Post liked successfully');");
                    }
                }else{
                    likeStatus =" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("commentID", likeResp.getString("id")).setParameter("currentTab", currentTab) + "','" + facebook.getId() + commentID + INFORMATION + currentTab + "');return false;" +"\">Like</a>";
                    if(request.getParameter("error") != null){
                        out.println("   showStatus('ERROR: " +request.getParameter("error") +"');");
                    }else{
                        out.println("   showStatus('Post unliked successfully');");
                    }
                }
                out.println("   spanId.attr('content', '" + likeStatus.replace("'", "\\'") +"');");
                out.println("</script>");
                out.println("</span>");                
            } catch (Exception ex) {
                log.error("Error when trying to like/unlike post ", ex);
            }
        }else if(mode!= null && mode.equals("replyPost")){//Displays dialog to create post
            actionURL.setParameter("commentID", request.getParameter("commentID"));
            actionURL.setParameter("suri", request.getParameter("suri"));

            out.println("<form type=\"dijit.form.Form\" id=\"createPost\" action=\"" +  actionURL.setAction("sendReply") + "\" method=\"post\" onsubmit=\"submitForm('createPost'); try{document.getElementById('csLoading').style.display='inline';}catch(noe){}; return false;\">");            
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>"); 
            out.println("   <td>");
            out.println("       <textarea type=\"dijit.form.Textarea\" name=\"replyText\" id=\"replyText\" rows=\"4\" cols=\"50\"></textarea>");
            out.println("   </td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Reply</button></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
        }else if(mode!= null && mode.equals("postSent")){//Hides dialog used to create Post
            if(request.getParameter("repliedPost") != null && request.getParameter("repliedPost").equals("ok")){
                out.println("<script type=\"text/javascript\">");
                out.println("   hideDialog();");
                out.println("   showStatus('Post sent');");
                out.println("</script>");
            }
        }else if(mode!=null && mode.equals("displayPicture")){
            //out.println("<div align=\"center\" id=\"" +request.getParameter("id") +"\" dojoType=\"dijit.layout.ContentPane\" style=\"width: 800px; height: 600px; border: thick #666666; overflow: hidden; position: relative;\">");
            //out.println("      <img src=\"" + request.getParameter("picture") + "\" style=\"position: relative;\" onload=\"loadLargeImage(" + "this, " + request.getParameter("id") + ");\"/>");
            /*out.println("<div align=\"center\" dojoType=\"dijit.layout.ContentPane\" style=\"border: thick #666666; overflow: hidden; position: relative;\">");
            out.println("      <img src=\"" + request.getParameter("picture") + "\" style=\"position: relative;\"/>");
            out.println("</div>");*/
            out.println("<div style=\"width: 640px; height: 480px; border: thick solid #F88D38; overflow: hidden; position: relative; background-color:#CDD0D1;\">");
            out.println("    <img src=\"" +  URLDecoder.decode(request.getParameter("pictureUrl"),"UTF-8") + "\"style=\"position: absolute;\" onload=\"showFullImage(this);\"/>");
            out.println("</div>");
        }else if(mode!=null && mode.equals("displayVideo")){
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/playVideo.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error in displayVideo() for requestDispatcher" , e);
            }
        }else if(mode.equals("fullProfile")){//Show user or page profile in dialog
            String profileType =request.getParameter("type") == null ? "" : (String)request.getParameter("type");
            String objUri = (String) request.getParameter("suri");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook)semanticObject.createGenericInstance();
            String jspResponse;
            
            if(profileType.equals("noType")) {
                try{
                    JSONObject profile = new JSONObject(getProfileFromId(request.getParameter("id"), facebook));
                    profile = profile.getJSONArray("data").getJSONObject(0);
                    profileType=profile.getString("type");
                }catch(JSONException jsone){
                    log.error("Error getting profile information" + jsone);
                    return;
                }
            }
            if(profileType.equals("user")){
                jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/facebookUserProfile.jsp";
            }else if (profileType.equals("page")){
                jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/facebookPageProfile.jsp";
            }else{ 
                return;
            }

            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            }catch (Exception e) {
                System.out.println("Error displaying user profile");
            }
        }
        else {
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
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        System.out.println("\n\n\nTHE CURRENT TAB:" + currentTab);
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("access_token", facebook.getAccessToken());
        params.put("limit", "5");
        params.put("fields", "id,from,to,message,message_tags,story,story_tags,picture,caption,link,object_id,application,source,name,description,properties,icon,actions,privacy,type,status_type,created_time,likes,comments.limit(5),place");
        params.put("until", request.getParameter("until"));
        System.out.println("Get the next 25 Posts!!");
        String fbResponse = "";
        if(scope.equals("newsFeed")){
            fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        }else if(scope.equals("wall")){
            fbResponse = postRequest(params, "https://graph.facebook.com/" + facebook.getFacebookUserId() +"/feed",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        }
        String untilPost = parseResponse(fbResponse, out, false, request, paramRequest, currentTab);
        
        //CAMBIAR EL ID DEL DIV dependiendo de donde sea llamado
        if(scope.equals("newsFeed")){
            out.println("<label id=\"" +objUri + "morePostsLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("scope", scope).setParameter("currentTab", currentTab) + "','" + objUri +"getMorePosts','bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">More posts</a></label>");
            //<label id="<%=objUri%>morePostsLabel"><a href="#"                  onclick="appendHtmlAt('<%=renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("suri", objUri).setParameter("scope", "newsFeed").setParameter("currentTab", NEWS_FEED_TAB)%>','<%=objUri%>getMorePosts', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More posts</a></label>
        }else if(scope.equals("wall")){
            out.println("<label id=\"" +objUri + "morePostsWallLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("scope", scope).setParameter("currentTab", currentTab) + "','" + objUri +"getMorePostsWall','bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">More posts</a></label>");
            //<label id="<%=objUri%>morePostsWallLabel"><               a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("suri", objUri).setParameter("scope", "wall").setParameter("currentTab", WALL_TAB)%>','<%=objUri%>getMorePostsWall', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More posts</a></label>
        }
    }
    
    
    public void doGetMorePictures(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String createdTimeParam = request.getParameter("createdTime");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        if(objUri!= null){
            renderURL.setParameter("suri", objUri);
        }
        System.out.println("\n\n\nTHE CURRENT TAB:" + currentTab);        
        System.out.println("Get the next 25 Posts!!");
        String fbResponse = "";
        
        HashMap<String, String> params = new HashMap<String, String>(2);
        //TODO: it seems than 'likes' is deprecated and it must be replaced with like_info
        params.put("q", "SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comments FROM stream WHERE filter_key IN ( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Photos') AND created_time < " + createdTimeParam +" LIMIT 10");
        params.put("access_token", facebook.getAccessToken());
    
        fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        String createdTime = picture(fbResponse, out, true, request, paramRequest);
        out.println("<label id=\"" +objUri + "morePicturesLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMorePictures").setParameter("createdTime", createdTime) + "','" + objUri +"getMorePictures','bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">More Pictures</a></label>");
        /*<div align="center" id="<%=objUri%>getMorePictures" dojoType="dijit.layout.ContentPane">
                            <label id="<%=objUri%>morePicturesLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMorePictures").setParameter("createdTime", createdTime).setParameter("currentTab", MEDIA_TAB)%>','<%=objUri%>getMorePictures', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More pictures</a></label>
</div>*/
    }
    
    
    /**
     * Analiza la respuesta de Facebook y obtiene la informaci&oacute;n de los mensajes publicados en el muro del usuario actual
     * Con base en la estructura del objeto JSON devuelto por Facebook, se revisa que la respuesta cuenta con información 
     * de los posts recibidos para posteriormente desplegarlos uno a uno en el objeto de salida indicado.
     * Se obtienen también el valor del parámetro usado para traer el siguiente bloque de posts.
     * @param response representa la respuesta obtenida de Facebook en formato JSON
     * @param out Salida estándar en donde se imprime el contenido del post
     * @param includeSinceParam valor que define si se incluye en la variable de sesi&oacute;n el valor del último post
     *        recibido para posteriormente preguntar a Facebook si hay nuevos posts.
     * @param request est&aacute;ndar HttpServletRequest
     * @param paramRequest est&aacute;ndar SWBParamRequest
     * @return 
     */
    public static String parseResponse(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix) {
        
        String  until="";
        String  since="";
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        
        try {
                JSONObject phraseResp = new JSONObject(response);
                System.out.println("Tamanio del arreglo:" + phraseResp.length());                       
                    int cont = 0;
                    JSONArray postsData = phraseResp.getJSONArray("data");
                    System.out.println("ARREGLO DE DATOS:" + postsData.length());
                    for (int k = 0; k < postsData.length(); k++) {
                        cont++;
                        System.out.println("\n\nPost de FACEBOOOK*********: " + postsData.getJSONObject(k));
                        doPrintPost(out,  postsData.getJSONObject(k), request, paramRequest, tabSuffix, facebook);
                    }
                    if(phraseResp.has("paging")){ 
                        JSONObject pagingData = phraseResp.getJSONObject("paging");
                        String nextPage = pagingData.getString("next"); // get until param to get OLDER posts
                        Pattern pattern = Pattern.compile("until=[0-9]+");
                        Matcher matcher = pattern.matcher(nextPage);
                        String untilParam = "";
                        if(matcher.find()){
                            untilParam = matcher.group();
                        }
                        if(!untilParam.isEmpty()){
                            until = untilParam.substring(untilParam.indexOf("=") + 1);//gets only the value of until param in paging object
                        }
                        
                        if(includeSinceParam){//Include value of since param when the tab loaded and when GetNewPost link is clicked
                            String previousPage = pagingData.getString("previous"); // get since param to get NEWER posts
                            pattern = Pattern.compile("since=[0-9]+");
                            matcher = pattern.matcher(previousPage);
                            String sinceParam = "";
                            
                            if(matcher.find()){
                                sinceParam = matcher.group();
                            }
                            if(!sinceParam.isEmpty()){
                                since = sinceParam.substring(sinceParam.indexOf("=") + 1);//gets only the value of since param in paging object
                                HttpSession session = request.getSession(true);
                                session.setAttribute("since", since);
                            }
                        }
                    }
        } catch (JSONException jsone) {
            
            System.out.println("Problemas al parsear respuesta de Facebook" + jsone);
        }
        return until;
    }
    
    public static String picture(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest) {
        
        String  createdTime="";
        
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        
        try {
            JSONObject phraseResp = new JSONObject(response);
            System.out.println("Tamanio del arreglo:" + phraseResp.length());                       
            int cont = 0;
            JSONArray postsData = phraseResp.getJSONArray("data");
            System.out.println("ARREGLO DE DATOS:" + postsData.length());
            for (int k = 0; k < postsData.length(); k++) {
                cont++;
                createdTime = printPicture(out,  postsData.getJSONObject(k), request, paramRequest, MEDIA_TAB, facebook );
            }
            
            System.out.println("TOTAL PICTURE POSTS RECEIVED:" + postsData.length());
        } catch (JSONException jsone) {
            System.out.println("Problemas al parsear respuesta de Facebook" + jsone);
        }
        return createdTime;
    }
    
    public static String getTagsFromPost(JSONObject objectTags, String postContent, SWBResourceURL renderURL){
        String postContentWithUrl = postContent;
        Iterator<?> keyTags = objectTags.keys();
        try{
            while( keyTags.hasNext() ){
                String key = (String)keyTags.next();
                if( objectTags.get(key) instanceof JSONArray ){
                    JSONArray tag = objectTags.getJSONArray(key);
                    String userUrl = "";
                    userUrl = "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id")+"") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a>";
                    postContentWithUrl = postContentWithUrl.replace(tag.getJSONObject(0).getString("name"), userUrl);
                }
            }
        }catch(JSONException jSonException){
            System.out.println("Problem parsing associated users");
            return postContent;
        }
        return postContentWithUrl;    
    }
    
    public static String getTagsFromPostArray(JSONObject objectTags, String postContent, SWBResourceURL renderURL){
        String postContentWithUrl = postContent;
        try{
                    String userUrl = "";
                    userUrl = "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", objectTags.getString("type")).setParameter("id", objectTags.getLong("id")+"") + "','" + objectTags.getString("name") + "'); return false;\">" + objectTags.getString("name") + "</a>";
                    postContentWithUrl = postContentWithUrl.replace(objectTags.getString("name"), userUrl);

        }catch(JSONException jSonException){
            System.out.println("Problem parsing associated users");
        }
        return postContentWithUrl;    
    }
    
    public static JSONObject getLikedPostFromId(String postId, String fields, Facebook facebook){
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        params.put("fields", fields);
        
        JSONObject jsonObject = null;
        try{
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + postId.substring(postId.lastIndexOf("_") +1),
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            if(!fbResponse.isEmpty()){
                jsonObject = new JSONObject(fbResponse);                
            }
        }catch(IOException ieo){
            System.out.println("Error getting post that user liked:" + ieo.getMessage());
        }catch(JSONException jsone){
            System.out.println("Error parsing information from string recieved:" + jsone.getMessage());
        }
        return jsonObject;
    }
    
    public static void doPrintPost(Writer writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook){
        try{
            SWBResourceURL actionURL = paramRequest.getActionUrl();                        
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            actionURL.setParameter("suri",request.getParameter("suri"));
            renderURL.setParameter("suri",request.getParameter("suri"));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            String postType = postsData.getString("type");
            
            String story = "";
            String message = "";
            String caption = "";
            boolean isAPhotoLike = false;
            boolean isALinkLike = false;
            JSONObject photoLike = null;
            JSONObject linkLike = null;
            
            
            
            if(postsData.isNull("actions")){//You can't like or respond this post
                return;                     //Must NOT be shown in wall
            }
            if(postType.equals("photo")){
                if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                }

                /*if(!postsData.isNull("status_type") &&
                        (postsData.getString("status_type").equals("shared_story") || postsData.getString("status_type").equals("tagged_in_photo"))){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                }*/
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }
                if(!postsData.isNull("caption")){
                    caption = postsData.getString("caption").replace("\n", "</br>");
                }
                //Story or message or both!!
                //or "status_type": "shared_story", tagged_in_photo
                
            }else if(postType.equals("link")){
                //"status_type": "app_created_story",
                if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                }
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }                
            }else if(postType.equals("status")){
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                }else if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = getTagsFromPost(storyTags, story, renderURL);
                    }
                    if(story.contains("likes a photo")){
                        photoLike = getLikedPostFromId(postsData.getString("id"), "id,from,name,name_tags,picture,source,link,tags", facebook);
                        isAPhotoLike = true;
                        //System.out.println("THE RECOVERED OBJECT:" + jSONObject);
                    }else if(story.contains("likes a link")){
                        linkLike = getLikedPostFromId(postsData.getString("id"), "id,from,name,picture,link,tags,message", facebook);
                        isALinkLike = true;
                    }
                }else{
                     return;
                }
            }else if(postType.equals("video")){
                if(!postsData.isNull("message")){
                    message = postsData.getString("message") + " THIS IS A VIDEO!!";
                }
            }
            
            if(postsData.has("place")){
                if(postsData.getJSONObject("place").has("name")){
                message = message + " AT " + "<a href=\"http://facebook.com/" + postsData.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + postsData.getJSONObject("place").getString("name") + "</a>";
                }
                
            }
            
            //JSONObject profile = new JSONObject(getProfileFromId(postsData.getJSONObject("from").getString("id")+"", facebook));
            //profile = profile.getJSONArray("data").getJSONObject(0);
            writer.write("<fieldset>");
            writer.write("<table style=\"width: 100%; border: 0px\">");
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" +renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id")+"")  + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\">" + postsData.getJSONObject("from").getString("name") + "</a> " + story);                            
            
            writer.write("   </td>");
            writer.write("</tr>");
            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">");
            writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id")+"") + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + postsData.getJSONObject("from").getLong("id") +"/picture\"/></a>");

            writer.write("   </td>");
            writer.write("   <td width=\"90%\">");
            writer.write("MESSAGE:");
            if(message.isEmpty()){
                writer.write("&nbsp;");
            }else{                
                writer.write(message.replace("\n", "</br>"));
            }

            writer.write("   </td>");
            writer.write("</tr>");
            
            
            //Picture if exists, start
            //if(postType.equals("link") && postsData.has("picture")){
            if(postsData.has("picture") || isAPhotoLike || isALinkLike){
                String picture = "";
                if(isAPhotoLike){
                    if(photoLike.has("source")){
                        picture = photoLike.getString("source");
                    }
                }else if(isALinkLike){
                    if(photoLike.has("picture")){
                        picture = linkLike.getString("picture");                    
                    }
                }else{
                    picture = postsData.getString("picture").replace("_s.", "_n.");
                }
                writer.write("<tr>");
                writer.write("   <td  width=\"10%\">"); 
                writer.write("       LINKPIC&nbsp;");
                writer.write("   </td>");
                
                writer.write("   <td width=\"90%\">");
                writer.write("   <table>");
                writer.write("   <tr>");
                writer.write("   <td rowspan=\"3\">");
                if(postType.equals("video") || postType.equals("swf")){
                    
                    writer.write("      <div id=\"vid" + tabSuffix + facebook.getId() +postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    writer.write("      <a href=\"#\" onclick=\"showSocialDialog('"+ renderURL.setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode(postsData.getString("source"), "UTF-8")) +
                            "','Video from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onload=\"imageLoad(" + "this, 'vid" + tabSuffix + facebook.getId() + postsData.getString("id") + "');\"/></a>");
                    writer.write("      </div>");
                }else{
                    writer.write("      <div id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    writer.write("      <a href=\"#\" onclick=\"showSocialDialog('" + renderURL.setMode("displayPicture").setParameter("pictureUrl", URLEncoder.encode(picture, "UTF-8")) +
                            "','Picture from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onload=\"imageLoad(" + "this, 'img" + tabSuffix +facebook.getId() + postsData.getString("id") + "');\"/></a>");
                    writer.write("      </div>");
                }
                writer.write("  </td>");
                writer.write("</tr>");
                
                writer.write("<tr>");
                writer.write("  <td>");
                if(postsData.has("link") && postsData.has("name")){
                    writer.write(    "<a href=\"" + postsData.getString("link") + "\">" +  postsData.getString("name") + "WHATSTHIS" + "</a>");
                }else if(isALinkLike){
                    if(linkLike.has("link")){
                        writer.write(    "<a href=\"" + linkLike.getString("link") + "\">" +  linkLike.getString("name") + "</a>");
                    }                    
                }else{ 
                    writer.write("&nbsp;");
                }
                
                writer.write("  </td>");
                writer.write("</tr>");                

                writer.write("<tr>");
                writer.write("<td>");
                if(isAPhotoLike){
                    writer.write(photoLike.has("name") ? photoLike.getString("name") : "&nbsp;");
                }else if(isALinkLike){
                    writer.write(linkLike.has("message") ? linkLike.getString("message") : "&nbsp;");
                }else{
                    writer.write( postsData.has("description") ?  postsData.getString("description") :"&nbsp;");
                }
                writer.write("</td>");
                writer.write("</tr>");                                
                writer.write("  </table>");
                writer.write("  </td>");
                writer.write("</tr>");
                if(!caption.isEmpty()){
                    writer.write("<tr>");
                    writer.write("   <td width=\"10%\">"); 
                    writer.write("       &nbsp;");
                    writer.write("   </td>");
                    writer.write("   <td width=\"90%\">");
                    writer.write(caption);
                    writer.write("   </td>");
                    writer.write("</tr>");
                }
            }
            //Picture if exists, end
            
            
            //Comments,start
            if(postsData.has("comments")){
                if(postsData.getJSONObject("comments").has("data")){
                    writer.write("<tr>");
                    writer.write("   <td  width=\"10%\">"); 
                    writer.write("      &nbsp;");
                    writer.write("   </td>");
                    writer.write("   <td width=\"90%\">");

                    JSONArray comments = postsData.getJSONObject("comments").getJSONArray("data");
                    writer.write("<table>");
                    for (int k = 0; k < comments.length(); k++){
                        if(k==5)break;
                        writer.write("<tr>");
                        writer.write("  <td rowspan=\"3\">");
                        //JSONObject commentProfile = new JSONObject(getProfileFromId(comments.getJSONObject(k).getJSONObject("from").getString("id")+"", facebook));
                        //commentProfile = commentProfile.getJSONArray("data").getJSONObject(0);
                        //writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", commentProfile.getString("type")).setParameter("id", commentProfile.getLong("id")+"") + "','" + commentProfile.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + commentProfile.getLong("id") +"/picture?width=30&height=30\"/></a>");
                        writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id",comments.getJSONObject(k).getJSONObject("from").getLong("id")+"") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + comments.getJSONObject(k).getJSONObject("from").getLong("id") +"/picture?width=30&height=30\"/></a>");
                        
                        writer.write("  </td>");
                        writer.write("</tr>");
                        
                        writer.write("<tr>");
                        writer.write("  <td>");
                        writer.write("<b><a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id", comments.getJSONObject(k).getJSONObject("from").getLong("id")+"") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\">" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "</a></b>:");
                        writer.write(       comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br>");
                        writer.write("  </td>");
                        writer.write("</tr>");

                        Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));
                        
                        writer.write("<tr>");
                        writer.write("<td>");
                        writer.write("<div id=\"" +facebook.getId() + comments.getJSONObject(k).getString("id") + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write(facebookHumanFriendlyDate(commentTime));
                        if(comments.getJSONObject(k).has("like_count")){
                            writer.write(" Likes: " + comments.getJSONObject(k).getInt("like_count") );
                        }
                        writer.write("</div>");
                        writer.write("</td>");
                        writer.write("</tr>");
                    }
                    writer.write("   </table>");
                    
                    writer.write("   </td>");
                    writer.write("</tr>");
                }
            }
            //Comments, end

            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            ///////writer.write("<div id=\"" + postsData.getString("id") + postsData.getString("created_time") + "\" dojoType=\"dijit.layout.ContentPane\">");
            //writer.write("<div id=\"" + postsData.getString("id") + "\" dojoType=\"dijit.layout.ContentPane\">");
            Date postTime = formatter.parse(postsData.getString("created_time"));
            
            writer.write("<div dojoType=\"dijit.layout.ContentPane\">");
            
            writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + INFORMATION + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write(facebookHumanFriendlyDate(postTime));
            boolean iLikedPost = false;
            if(postsData.has("likes")){                
                JSONArray likes = postsData.getJSONObject("likes").getJSONArray("data");
                writer.write(" Likes: <b>" + "NUMER");
                for (int k = 0; k < likes.length(); k++) {
                    writer.write(likes.getJSONObject(k).getString("name") + ", ");
                    if(likes.getJSONObject(k).getString("id").equals(facebook.getFacebookUserId())){
                        //My User id is in 'the likes' of this post
                        iLikedPost = true;
                    }
                }
                writer.write("</b>");
            }
            writer.write("   </span>");
            
            //Show like/unlike and reply (comment)
            JSONArray actions = postsData.has("actions") ? postsData.getJSONArray("actions") : null;
            if(actions != null && actions.length() > 0){//Available actions for the post
                for (int i = 0; i < actions.length(); i++) {
                    if(actions.getJSONObject(i).getString("name").equals("Comment")){//I can comment
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                            writer.write(" <a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("commentID", postsData.getString("id")) + "','Reply to " + postsData.getJSONObject("from").getString("name") + "');return false;\">Reply</a>  ");
                        writer.write("   </span>");
                    }else if(actions.getJSONObject(i).getString("name").equals("Like")){//I can like
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + LIKE + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");                        
                        if(iLikedPost){
                            writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("commentID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() +  postsData.getString("id") + INFORMATION + tabSuffix + "');return false;" +"\">Unlike</a>");
                        }else{
                            writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("commentID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() + postsData.getString("id") + INFORMATION + tabSuffix + "');return false;" +"\">Like</a>");
                        }
                        writer.write("   </span>");
                    }else{//Other unknown action
                        writer.write("other:" + actions.getJSONObject(i).getString("name"));
                    }
                }
            }

            writer.write("   </div>");
            writer.write("   </td>");

            writer.write("</tr>");          
            writer.write("</table>");
            writer.write("</fieldset>");               
        }catch(Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                
            }            
        }
    }
    
    public static String printPicture(Writer writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook){
        String createdTime="";
        
        try{
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("suri",request.getParameter("suri"));
            
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            renderURL.setParameter("suri",request.getParameter("suri"));
            
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            
            //System.out.println("POST:")
            //Get profile information of a user or page 
            //TODO: id = A 64-bit int representing the user, group, page, event, or application ID
            JSONObject profile = new JSONObject(getProfileFromId(postsData.getLong("actor_id")+"", facebook));            
            profile = profile.getJSONArray("data").getJSONObject(0);
            JSONArray media = postsData.getJSONObject("attachment").getJSONArray("media");
            
            writer.write("<fieldset>");
            writer.write("<table style=\"width: 100%; border: 0px\">");
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            writer.write("      <a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", profile.getString("type")).setParameter("id", profile.getLong("id")+"") + "','" + profile.getString("name") + "'); return false;\">" + profile.getString("name") + "</a>");
            writer.write("   </td>");
            writer.write("</tr>");

            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">"); 
            writer.write("      <a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", profile.getString("type")).setParameter("id", profile.getLong("id")+"") + "','" + profile.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + profile.getLong("id") +"/picture\"/></a>");
            writer.write("   </td>");
            writer.write("   <td width=\"90%\">");
            if(postsData.getInt("type") == 247){//Photo posted
                writer.write(postsData.getString("message"));
                //writer.write(":Photos posted");
            }else if(postsData.getInt("type") == 65){//Tagged photo
                if(!postsData.isNull("description_tags")){//Users tagged in story
                    JSONObject descriptionData = postsData.optJSONObject("description_tags");
                    if(descriptionData != null){
                        writer.write(getTagsFromPost(postsData.getJSONObject("description_tags"), postsData.getString("description"), renderURL));
                    }else{
                        writer.write(getTagsFromPostArray(postsData.getJSONArray("description_tags").getJSONArray(0).getJSONObject(0), postsData.getString("description"), renderURL));
                    }
                }
                //writer.write(":Tagged photo");
            }else if(postsData.getInt("type") == 373){//Cover update                
                writer.write(profile.getString("name") + " has updated cover photo");
                //writer.write(":updated cover photo");
            }else{
                writer.write("&nbsp;");
            }
            
            if(!postsData.getJSONObject("attachment").isNull("fb_object_type") && postsData.getJSONObject("attachment").getString("fb_object_type").equals("album")){
                //writer.write(postsData.getString("description"));
                if(!postsData.getJSONObject("attachment").getString("name").isEmpty())
                    writer.write(profile.getString("name") + " has added " + media.length() + " photos to the album " + postsData.getJSONObject("attachment").getString("name") );
            }
            
            writer.write("  </td>");
            writer.write("</tr>");
            //White space at left
            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">"); 
            writer.write("  LINKPIC&nbsp;");
            writer.write("   </td>");
            
            writer.write("   <td  width=\"90%\">"); 
            writer.write("   <table>");
            writer.write("   <tr>");
            writer.write("   <td rowspan=\"3\">");                       
            //for (int k = 0; k < media.length(); k++) {
            for (int k = 0; k < 1; k++) {
                //writer.write("   <td  width=\"5%\">"); 
                writer.write("      <div id=\"img" + media.getJSONObject(k).getString("src") + "\" style=\"width: 150px; height: 150px; border: thick #666666; overflow: hidden; position: relative;\">");
                writer.write("      <a href=\"#\" onclick=\"showSocialDialog('" + renderURL.setMode("displayPicture").setParameter("pictureUrl", media.getJSONObject(k).getString("src").replace("_s.", "_n.")) +
                        "','Picture from " + "" + "'); return false;\"><img src=\"" + media.getJSONObject(k).getString("src") + "\" style=\"position: relative;\" onload=\"imageLoad(" + "this, 'img" + media.getJSONObject(k).getString("src") + "');\"/></a>");
                writer.write("      </div>");
                //writer.write("   </td>"); 
                //writer.write("<img src=\"" + media.getJSONObject(k).get("src") + "\"/>");                
            }
            writer.write("   </td>");
            writer.write("</tr>");
            
            writer.write("<tr>");
            writer.write("  <td>");
            writer.write(" LINK??");
            writer.write("  </td>");
            writer.write("</tr>"); 
            
            writer.write("<tr>");
            writer.write("  <td>");
            writer.write(postsData.isNull("description") ? "DESCRIPTION" : postsData.getString("description"));
            writer.write("  </td>");
            writer.write("</tr>"); 
                
            writer.write("</table>");
            writer.write("   </td>");
            writer.write("</tr>");
            
            
            //Comments,start
            if(postsData.has("comments")){
                if(postsData.getJSONObject("comments").has("comment_list")){
                    writer.write("<tr>");
                    writer.write("   <td  width=\"10%\">"); 
                    writer.write("      &nbsp;");
                    writer.write("   </td>");
                    writer.write("   <td width=\"90%\">");

                    JSONArray comments = postsData.getJSONObject("comments").getJSONArray("comment_list");
                    if(comments.length()>0)
                    writer.write("<table>");
                    for (int k = 0; k < comments.length(); k++){
                        writer.write("<tr>");
                        writer.write("  <td rowspan=\"3\">");
                        JSONObject commentProfile = new JSONObject(getProfileFromId(comments.getJSONObject(k).getLong("fromid")+"", facebook));                
                        commentProfile = commentProfile.getJSONArray("data").getJSONObject(0);
                        writer.write("      <a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", commentProfile.getString("type")).setParameter("id", commentProfile.getLong("id")+"") + "','" + commentProfile.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + commentProfile.getLong("id") +"/picture?width=30&height=30\"/></a>");
                        writer.write("  </td>");
                        writer.write("</tr>");
                        
                        writer.write("<tr>");
                        writer.write("  <td>");
                        writer.write("      <b><a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", commentProfile.getString("type")).setParameter("id", commentProfile.getLong("id")+"") + "','" + commentProfile.getString("name") + "'); return false;\">" + commentProfile.getString("name") + "</a></b>:");
                        writer.write(       comments.getJSONObject(k).getString("text").replace("\n", "</br>") + "</br>");
                        writer.write("  </td>");
                        writer.write("</tr>");

                        Date commentTime = new java.util.Date((long)comments.getJSONObject(k).getLong("time")*1000);
                        
                        writer.write("<tr>");
                        writer.write("<td>");
                        writer.write("<div id=\"" + comments.getJSONObject(k).getString("id") + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write(facebookHumanFriendlyDate(commentTime));
                        //writer.write("<a href=\"\" onMouseOver=\"dijit.Tooltip.defaultPosition=['above', 'below']\" id=\"TooltipButton\" onclick=\"return false;\"> LIKE/UNLIKE</a>");
                        //writer.write("<div class=\"dijitHidden\"><span data-dojo-type=\"dijit.Tooltip\" data-dojo-props=\"connectId:'TooltipButton'\">I am <strong>above</strong> the button</span></div>");
                        if(comments.getJSONObject(k).has("like_count")){
                            writer.write(" Likes: " + comments.getJSONObject(k).getJSONObject("likes").getInt("count") );
                        }
                        writer.write("</div>");
                        //writer.write("<div dojoType=\"dijit.Tooltip\" connectId=\"ss\" position=\"below\">");
                        writer.write("</td>");
                        writer.write("</tr>");
                        //writer.write("<div style=\"float:left\"><b>" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "</b>: </div>");
                        //writer.write(comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br><span>Hora y Fecha</span></br></br>");                        
                    }
                    if(comments.length()>0)
                    writer.write("   </table>");

                    writer.write("   </td>");
                    writer.write("</tr>");
                }
            }
            
            
            
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            ///////writer.write("<div id=\"" + postsData.getString("id") + postsData.getString("created_time") + "\" dojoType=\"dijit.layout.ContentPane\">");
            //writer.write("<div id=\"" + postsData.getString("id") + "\" dojoType=\"dijit.layout.ContentPane\">");
            
            Date postTime = new java.util.Date((long)postsData.getLong("created_time")*1000);
            createdTime = String.valueOf(postsData.getLong("created_time"));
            writer.write("<div id=\"" + createdTime + "\" dojoType=\"dijit.layout.ContentPane\">");
            
            writer.write("   <span class=\"inline\" id=\"" + postsData.getString("post_id") + INFORMATION + MEDIA_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write(facebookHumanFriendlyDate(postTime));

            if(postsData.has("like_info")){
                JSONObject likeInfo = postsData.getJSONObject("like_info");
                writer.write(" Likes: <b>");
                writer.write(likeInfo.getLong("like_count")+"");
                writer.write("</b>");
                writer.write("   </span>");
                
                writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + LIKE + MEDIA_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");                        
                if(likeInfo.getBoolean("can_like")){
                    if(likeInfo.getBoolean("user_likes")){
                        writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("commentID", postsData.getString("post_id")).setParameter("currentTab", MEDIA_TAB) + "','" + postsData.getString("post_id") + INFORMATION + MEDIA_TAB + "');return false;" +"\">Unlike</a>");
                    }else{
                        writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("commentID", postsData.getString("post_id")).setParameter("currentTab", MEDIA_TAB) + "','" + postsData.getString("post_id") + INFORMATION + MEDIA_TAB + "');return false;" +"\">Like</a>");
                    }
                }else{
                    writer.write("CAN'T LIKE");
                }
                writer.write("   </span>");
            }else{
                writer.write("   </span>");
            }
            
            if(postsData.has("comments")){
                JSONObject comments = postsData.getJSONObject("comments");
                
                if(comments.getBoolean("can_post")){
                    writer.write("   <span class=\"inline\" id=\"" + postsData.getString("post_id") + REPLY + MEDIA_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write(" <a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("commentID", postsData.getString("post_id")) + "','Reply to " + "" + "');return false;\">Reply</a>  ");
                    writer.write("   </span>");
                }else{
                    writer.write("CAN'T POST");
                }
            }
                   
            writer.write("   </div>");
            writer.write("   </td>");

            writer.write("</tr>");
            writer.write("</table>");
            writer.write("</fieldset>");               
        }catch(Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
        return createdTime;
    }

    public void doAskIfNewPosts(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String currenTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        HttpSession session = request.getSession(true);
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        System.out.println("Value from since var:" +  session.getAttribute("since") + "\n\n");
        params.put("since", session.getAttribute("since").toString());// since param used to get newer post
        String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        try{
            JSONObject phraseResp = new JSONObject(fbResponse);
            JSONArray postsData = phraseResp.getJSONArray("data");
            System.out.println("ARREGLO DE DATOS:" + postsData.length());
            if(postsData.length()>0){
                out.println("<a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("doGetStreamUser").setParameter("currentTab", currenTab) + "','" + objUri + "facebookStream','top'); try{dojo.byId(this.parentNode.id).innerHTML = '';}catch(noe){}; return false;\">You have <b>" + postsData.length() +  "</b> new post" + (postsData.length() > 1 ? "s" : "") +  "</a>");
            }
        } catch (JSONException jsone) {
            System.out.println("Problemas al parsear respuesta de Facebook al preguntar si hay nuevos posts" + jsone);
        }
    }
    
    public void doGetNewPosts(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        String currentTab = request.getParameter("currentTab") == null ? "" : request.getParameter("currentTab");
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        HttpSession session = request.getSession(true);
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        System.out.println("Value from since var:" +  session.getAttribute("since") + "\n\n");
        params.put("since", session.getAttribute("since").toString());// since param used to get newer post
        String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        String untilPost = parseResponse(fbResponse, out, true, request, paramRequest, currentTab);
        
    }
        

    //SELECT id, name, type FROM profile where id =
    public static String getProfileFromId(String id, Facebook facebook){
        HashMap<String, String> params1 = new HashMap<String, String>(3);    
        params1.put("q", "SELECT id, name, type FROM profile where id = " + id);
        params1.put("access_token", facebook.getAccessToken());
    
        String fbResponse = null;
        try{
         fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        }catch(Exception e){
            System.out.println("Error getting user information"  + e.getMessage());
        }
        return fbResponse;
    }
            
    public static String getUserInfoFromId(String id, Facebook facebook){
        HashMap<String, String> params1 = new HashMap<String, String>(3);    
        params1.put("q", "SELECT uid, name, first_name, middle_name, last_name FROM user WHERE uid = " + id);
        params1.put("access_token", facebook.getAccessToken());
    
        String fbResponse = null;
        try{
         fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        }catch(Exception e){
            System.out.println("Error getting user information"  + e.getMessage());
        }
        return fbResponse;
    }
    
    public static String getPageInfoFromId(String id, Facebook facebook){
        HashMap<String, String> params1 = new HashMap<String, String>(3);    
        params1.put("q", "SELECT page_id, name , type FROM page where page_id = " + id);
        params1.put("access_token", facebook.getAccessToken());
    
        String fbResponse = null;
        try{
         fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        }catch(Exception e){
            System.out.println("Error getting user information"  + e.getMessage());
        }
        return fbResponse;
    }
    
     public static String facebookHumanFriendlyDate(Date created){
        Date today = new Date();
        Long duration = today.getTime() - created.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        String date = "";

        if (duration < second * 7) {//Less than 7 seconds
            date = "right now";
        }else if (duration < minute) {
            int n = (int) Math.floor(duration / second);
            date = n + " seconds ago";
        }else if (duration < minute * 2) {//Less than 2 minutes
            date = "about 1 minute ago";
        }else if (duration < hour) {
            int n = (int) Math.floor(duration / minute);
            date = n + " minutes ago";
        }else if (duration < hour * 2) {//Less than 1 hour
            date = "about 1 hour ago";
        }else if (duration < day) {
            int n = (int) Math.floor(duration / hour);
            date = n + " hours ago";
        }else  if (duration > day && duration < day * 2) {
            date = "yesterday";
        }else{
            int n = (int) Math.floor(duration / day);
            date = n + " days ago";
        }
        return date;
    }
}
