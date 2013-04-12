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
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
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
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String objUri = (String) request.getParameter("suri");
        System.out.println("SURI:" + objUri);
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        String jspResponse = SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/wall.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("facebookBean", facebook);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }                        
    }
    
    public static String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" +  paramString);
        //URL serverUrl = new URL(url);
        //System.out.println("URL:" +  serverUrl);
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
             
                //System.out.println("ERROR < 400:" + getResponse(conex.getInputStream()));
                System.out.println("CONNECT:" + conex);
                 //   out = conex.getOutputStream();
                 //   out.write(paramString.toString().getBytes("UTF-8"));
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
    
    private static String getRequest(Map<String, String> params, String url,
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
            System.out.println("CONNECT:" + conex);
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
            System.out.println("Response: " + fbResponse);
            response.setRenderParameter("commentID", commentID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("liked", "ok");
            response.setRenderParameter("access_token", facebook.getAccessToken());
            System.out.println("LIKE-End");
            response.setMode("likeSent"); //show RT Message and update div
        }else if(action.equals("doUnlike")){    //If you liked a post from your friends you can do unlike simply
            System.out.println("UNLIKE-Start"); //If you liked a post from a user you gave like, you cannot give unlike
            String objUri = (String) request.getParameter("suri");
            String commentID = (String)request.getParameter("commentID");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook)semanticObject.createGenericInstance();
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            System.out.println("\n\nComment ID:" + commentID.substring(commentID.indexOf('_') + 1));
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + commentID.substring(commentID.indexOf('_') + 1) + "/likes" ,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "DELETE");
            System.out.println("Response: " + fbResponse);
            response.setRenderParameter("commentID", commentID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("unliked", "ok");
            response.setRenderParameter("access_token", facebook.getAccessToken());
            System.out.println("UNLIKE-End");
            response.setMode("unlikeSent"); //show RT Message and update div
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        actionURL.setParameter("suri", request.getParameter("suri"));        
        PrintWriter out = response.getWriter();
        if(mode!= null && mode.equals("getMorePosts")){//Gets more Posts
            System.out.println("brings more posts");
            doGetMorePosts(request, response, paramRequest);
        }else if(mode!= null && mode.equals("likeSent") || mode.equals("unlikeSent")){//Displays updated data of liked status
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            String commentID = request.getParameter("commentID");
            String accessToken = request.getParameter("access_token");
            
            try {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("access_token", accessToken);
                String fbResponse = getRequest(params, "https://graph.facebook.com/" + commentID,
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            
                System.out.println("Facebook response:" + fbResponse);
                JSONObject likeResp = new JSONObject(fbResponse);
                String facebookDate = likeResp.getString("created_time");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
                formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));  
                Date localDateTime = formatter.parse(facebookDate);

                long minutes = (long)(new Date().getTime()/60000) - (localDateTime.getTime()/60000);
                out.write("Created:<b>" + (int)minutes + "</b> minutes");

                boolean iLikedPost = false;
                if(likeResp.has("likes")){
                    out.write(" Likes: <b>");
                    JSONArray likes = likeResp.getJSONObject("likes").getJSONArray("data");
                    for (int k = 0; k < likes.length(); k++) {
                        out.write(likes.getJSONObject(k).getString("name") + "(" + likes.getJSONObject(k).getString("id") +"), " );
                        if(likes.getJSONObject(k).getString("id").equals("100000536460020")){
                            //My User id is in 'the likes' of this post
                            iLikedPost = true;
                        }
                    }
                    out.write("</b>");
                }
                if(iLikedPost){
                    out.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doUnlike").setParameter("commentID", likeResp.getString("id")).toString() + "',this);return false;" +"\">Unlike</a>");
                }else{
                    out.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doLike").setParameter("commentID", likeResp.getString("id")).toString() + "',this);return false;" +"\">Like</a>");
                }
                
                if(request.getParameter("liked") != null && request.getParameter("liked").equals("ok")){
                    out.println("<script type=\"text/javascript\">");
                    out.println("   showStatus('Post liked successfully');");
                    out.println("</script>");
                }else if(request.getParameter("unliked") != null && request.getParameter("unliked").equals("ok")){
                    out.println("<script type=\"text/javascript\">");
                    out.println("   showStatus('Post unliked successfully');");
                    out.println("</script>");
                }                
            } catch (Exception ex) {
                log.error("Error when trying to like/unlike post ", ex);
            }
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    public void doGetMorePosts(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("access_token", facebook.getAccessToken());
        params.put("limit", "6");
        params.put("until", request.getParameter("until"));
        System.out.println("Get the next 25 Posts!!");
        String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        String untilPost = parseResponse(fbResponse, out, request, paramRequest);
    
        out.println("<label id=\"morePostsLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMorePosts").setParameter("until", untilPost) + "','getMorePosts','bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">More posts</a></label>");
    }
    
    public static String parseResponse(String response, Writer out, HttpServletRequest request, SWBParamRequest paramRequest) {
        
        String  until="";
        
        try {           
                boolean isResponseEmpty = false;
                //JSONObject phraseResp = mainObject.getJSONObject(j);
                JSONObject phraseResp = new JSONObject(response);
                System.out.println("Tamanio del arreglo:" + phraseResp.length());                       
                    int cont = 0;
                    JSONArray postsData = phraseResp.getJSONArray("data");
                    System.out.println("ARREGLO DE DATOS:" + postsData.length());
                    for (int k = 0; k < postsData.length(); k++) {
                        cont++;
                        imprime(out,  postsData.getJSONObject(k), request, paramRequest);                                
                    }
                    if(phraseResp.has("paging")){
                        JSONObject pagingData = phraseResp.getJSONObject("paging");
                        String nextPage = pagingData.getString("next");
                        System.out.println("The next link = " + nextPage);
                        Pattern pattern = Pattern.compile("until=[0-9]+");
                        Matcher matcher = pattern.matcher(nextPage);
                        String untilParam = "";
                        if(matcher.find()){
                            untilParam = matcher.group();
                        }
                        
                        if(!untilParam.isEmpty()){
                            System.out.println("Value of until: " + untilParam.substring(untilParam.indexOf("=") + 1));
                            until = untilParam.substring(untilParam.indexOf("=") + 1);//gets only the value of until param in paging object
                        }
                    }
        } catch (JSONException jsone) {
            
            System.out.println("Problemas al parsear respuesta de Facebook" + jsone);
        }
        return until;
    }
    
    public static void imprime(Writer writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest){
        try{
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("suri",request.getParameter("suri"));


            writer.write("<fieldset>");
            writer.write("<table style=\"width: 100%; border: 0px\">");
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            writer.write("      <a href=\"http://facebook.com/" + postsData.getJSONObject("from").getString("id") + "\" target=\"_blank\">" + postsData.getJSONObject("from").getString("name") + "</a>  ");
            writer.write("   </td>");
            writer.write("</tr>");
            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">"); 
            writer.write("       <img src=\"http://graph.facebook.com/" + postsData.getJSONObject("from").getString("id") +"/picture\"/>");
            writer.write("   </td>");
            writer.write("   <td width=\"90%\">");                
            String story = postsData.has("story") ? postsData.getString("story") : ""; // It's necessary to include the URL for media, hash tags and usernames
            String message = postsData.has("message") ? postsData.getString("message") : ""; // It's necessary to include the URL for media, hash tags and usernames
            writer.write(story + message);        
            writer.write("   </td>");
            writer.write("</tr>");
            //Picture if exists, start
            if(postsData.has("picture")){
            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">"); 
            writer.write("       &nbsp;");
            writer.write("   </td>");
            writer.write("   <td width=\"90%\">");
            writer.write("      <div id=\"img" + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
            writer.write("      <img src=\"" + postsData.getString("picture").replace("_s.", "_n.") + "\" style=\"position: absolute;\" onload=\"imageLoad(" + "this, img" + postsData.getString("id") + ");\"/>");
            //writer.write("      <div class=\"imageContainer\">");
            //writer.write("       <img class=\"imagePost\" src=\"" + postsData.getString("picture") + "\"/>");
            writer.write("      </div>");
            writer.write("   </td>");
            writer.write("</tr>");
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
                    for (int k = 0; k < comments.length(); k++) {
                        writer.write("<img src=\"http://graph.facebook.com/" + comments.getJSONObject(k).getJSONObject("from").getString("id") +"/picture?width=30&height=30\"/> ");
                        writer.write(comments.getJSONObject(k).getJSONObject("from").getString("name") + ": ");
                        writer.write(comments.getJSONObject(k).getString("message") + "</br> ");
                    }

                    writer.write("   </td>");
                    writer.write("</tr>");
                }
            }
            //Comments, end
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            writer.write("<div id=\"" + postsData.getString("id") + "\" dojoType=\"dijit.layout.ContentPane\">");

            String facebookDate = postsData.getString("created_time");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));  
            Date localDateTime = formatter.parse(facebookDate);

            long minutes = (long)(new Date().getTime()/60000) - (localDateTime.getTime()/60000);
            writer.write("Created:<b>" + (int)minutes + "</b> minutes");

            boolean iLikedPost = false;
            if(postsData.has("likes")){
                writer.write(" Likes: <b>");
                JSONArray likes = postsData.getJSONObject("likes").getJSONArray("data");
                for (int k = 0; k < likes.length(); k++) {
                    writer.write(likes.getJSONObject(k).getString("name") + "(" + likes.getJSONObject(k).getString("id") + "), ");
                    if(likes.getJSONObject(k).getString("id").equals("100000536460020")){
                        //My User id is in 'the likes' of this post
                        iLikedPost = true;
                    }
                }
                writer.write("</b>");
            }
            if(iLikedPost){
                writer.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doUnlike").setParameter("commentID", postsData.getString("id")).toString() + "',this);return false;" +"\">Unlike</a>");
            }else{
                writer.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doLike").setParameter("commentID", postsData.getString("id")).toString() + "',this);return false;" +"\">Like</a>");
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
    }
}
