<%-- 
    Document   : postActions
    Created on : 7/10/2013, 12:31:40 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.xml.sax.InputSource"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.Closeable"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.IOException"%>
<%@page import="twitter4j.Status"%>
<%@page import="twitter4j.conf.ConfigurationBuilder"%>
<%@page import="twitter4j.TwitterFactory"%>
<%@page import="twitter4j.TwitterException"%>
<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Collection"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>
<%!

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
            
            in = conex.getInputStream();
            response = getResponse(in);
                        
        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
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
        //System.out.println("URL:" +  serverUrl);
        
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
                System.out.println("\n\n\nERROR:" +   response);
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
        while (charsRead >= 0){
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
                System.out.println("Error at closing object: " + c.getClass().getName());
            }
        }
    }

    public ConfigurationBuilder configureOAuth(org.semanticwb.social.Twitter tw){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(tw.getAppKey())
          .setOAuthConsumerSecret(tw.getSecretKey())
          .setOAuthAccessToken(tw.getAccessToken())
          .setOAuthAccessTokenSecret(tw.getAccessTokenSecret());       
        return cb;
    }
    
    public String doRT(PostIn postIn){
        String retweetId = null;
        try {
            System.out.println("Doing Retweet!!");
            SocialNetwork postInSN = postIn.getPostInSocialNetwork();
            twitter4j.Twitter twitter = null;
            Twitter semanticTwitter = (Twitter)postInSN;
            twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
            //System.out.println("MESSAGE ID:" + postIn.getSocialNetMsgId());
            Long id = Long.parseLong(postIn.getSocialNetMsgId());
            //System.out.println("Tweet to RT in doRT:" + id);
            retweetId = "" + twitter.retweetStatus(id).getId();
            System.out.println("Retweet!");
        } catch (TwitterException ex){
            System.out.println("Error when trying to retweet " + ex.getMessage());
        }
        return retweetId;
    }
    public boolean undoRT(PostIn postIn, Long retweetId){
        try {
            System.out.println("Undoing Retweet");
            SocialNetwork postInSN = postIn.getPostInSocialNetwork();
            twitter4j.Twitter twitter = null;
            Twitter semanticTwitter = (Twitter)postInSN;
            twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
            //System.out.println("Tweet to UNDORT " + retweetId);
            twitter.destroyStatus(retweetId); //Destroy Tweet generated when you Retweeted
            System.out.println("UNDO Retweet!");
            return true;
        } catch (TwitterException ex) {
            System.out.println("Error when trying to undo retweet:" + ex.getMessage());
        }
        return false;
    }
    
    public boolean doFavorite(PostIn postIn){
        try{
            SocialNetwork postInSN = postIn.getPostInSocialNetwork();
            twitter4j.Twitter twitter = null;
            
            Twitter semanticTwitter = (Twitter)postInSN;
            twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
            Long id = Long.parseLong(postIn.getSocialNetMsgId());
            System.out.println("Doing FAVORITE:" + id);
            twitter.createFavorite(id);
            System.out.println("Favorited!");           
        }catch(TwitterException ex) {
            if(ex.getErrorCode() == 139){//You have already favorited this status
                System.out.println("You have already favorited this status");
            }
        }
        return true;
    }
    
    public boolean undoFavorite(PostIn postIn){
        try {
            SocialNetwork postInSN = postIn.getPostInSocialNetwork();
            twitter4j.Twitter twitter = null;
            Twitter semanticTwitter = (Twitter)postInSN;
            twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
            Long id = Long.parseLong(postIn.getSocialNetMsgId());
            System.out.println("UNDOING FAVORITE:" + id);
            twitter.destroyFavorite(id);
        } catch (TwitterException ex) { 
            System.out.println("You have already UNFAVORITED this status ");
        }
        return true;
    }
    
    public boolean doFBLike(PostIn postIn){
        System.out.println("LIKE-Start");
        try{
            String postID = postIn.getSocialNetMsgId();        
            SocialNetwork postInSN = postIn.getPostInSocialNetwork();        
            Facebook facebook = (Facebook)postInSN;

            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + postID + "/likes",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
            if(!fbResponse.equals("true")){//If the response is not true, there was an error
                try {
                    JSONObject likeResponse = new JSONObject(fbResponse);
                    if(likeResponse.has("error")){
                        System.out.println(likeResponse.getJSONObject("error").getString("message"));
                        return false;
                    }
                } catch (JSONException ex) {
                    System.out.println("Error doing like action " +  ex.getMessage());
                    return false;
                }
            }
            System.out.println("LIKE-End");
            return true;
        }catch(Exception e){
            System.out.println("Error:" +  e.getMessage());
            return false;
        }
    }

    public boolean doFBDislike(PostIn postIn){
        System.out.println("DISLIKE-Start"); //If you liked a post from a user you gave like, you cannot give unlike            
        try{
            String postID = postIn.getSocialNetMsgId();
            SocialNetwork postInSN = postIn.getPostInSocialNetwork();
            Facebook facebook = (Facebook)postInSN;
            
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            //System.out.println("\n\nComment ID:" + postID.substring(postID.indexOf('_') + 1));
            String fbResponse ="";
            fbResponse= postRequest(params, "https://graph.facebook.com/" + postID + "/likes" ,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "DELETE");
            if(!fbResponse.equals("true")){//If the response is not true, there was an error
                System.out.println("ERROR TRYING TO UNLIKE ID:" + fbResponse);                
                    try {
                        JSONObject likeResponse = new JSONObject(fbResponse);
                        if(likeResponse.has("error")){                            
                            System.out.println(likeResponse.getJSONObject("error").getString("message"));
                        }
                    } catch (JSONException ex) {
                        System.out.println("Error doing like action " +  ex.getMessage() );
                    }            
            }
            System.out.println("DISLIKE-End");
            return true;
        }catch(Exception e){
            System.out.println("ERROR:" + e.getMessage());
            return false;
        }
    }

    public boolean doYTLikeDislike(PostIn postIn, String action){
        String videoId = postIn.getSocialNetMsgId();
        SocialNetwork postInSN = postIn.getPostInSocialNetwork();        
        Youtube semanticYoutube = (Youtube) postInSN;
        if(!semanticYoutube.validateToken()){
            System.out.println("Unable to update the access token!");
            return false;
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
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));            
            StringBuilder likeInfo = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
               likeInfo.append(line);
            }
            //System.out.println("--Ejecuted Like/disLike:" + likeInfo.toString());
        }catch(Exception ex){
            System.out.println("ERROR" + ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public String doYTFavorite(PostIn postIn){
        String videoId = postIn.getSocialNetMsgId();
        SocialNetwork postInSN = postIn.getPostInSocialNetwork();        
        Youtube semanticYoutube = (Youtube) postInSN;
        String favoriteId = null;
        if(!semanticYoutube.validateToken()){
            System.out.println("Unable to update the access token!");
            return null;
        }
        
        String url1 = "http://gdata.youtube.com/feeds/api/users/default/favorites";
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
            " <entry xmlns=\"http://www.w3.org/2005/Atom\">\r\n" +
            " <id>" + videoId + "</id>\r\n" +
            "</entry>\r\n";
            writer.write(xml.getBytes("UTF-8"));
            writer.flush();
            writer.close();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));            
            StringBuilder favoriteInfo = new StringBuilder();
            String line;
            while( (line = reader.readLine()) != null) {
               favoriteInfo.append(line);
            }

            //System.out.println("favoriteInfo-->" + favoriteInfo + "<--\n");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document xmlDoc = builder.parse(new InputSource(new StringReader(favoriteInfo.toString())));
            xmlDoc.getDocumentElement().normalize();
            NodeList rootNode = xmlDoc.getDocumentElement().getChildNodes();

            for( int tmp = 0; tmp < rootNode.getLength(); tmp++){
                Node nNode= rootNode.item(tmp);
                if(nNode.getNodeName().equals("yt:favoriteId")){                    
                    favoriteId = nNode.getTextContent();
                    //System.out.println("yt:favoriteId-->" + favoriteId);
                }
            }
        }catch(Exception ex){
            System.out.println("ERROR->" + ex.toString());
            //ex.printStackTrace();
            return null;
        }
        return favoriteId;
    }

    public String undoYTFavorite(PostIn postIn, String favoriteId){       
        SocialNetwork postInSN = postIn.getPostInSocialNetwork();        
        Youtube semanticYoutube = (Youtube) postInSN;
        if(!semanticYoutube.validateToken()){
            System.out.println("Unable to update the access token!");
            return null;
        }
        
        String url1 = "http://gdata.youtube.com/feeds/api/users/default/favorites/" + favoriteId;
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(url1);
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
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));            
            StringBuilder favoriteInfo = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
               favoriteInfo.append(line);
            }
        }catch(Exception ex){
            System.out.println("ERROR->" + ex.toString());
            return favoriteId; //On error return the same id
        }
        //The server does not return info when the favorite is deleted.
        return null;
    }
%>

<%
    String postUri = request.getParameter("postUri");
    String action = request.getParameter("action");
    System.out.println("Action to be executed: " +  action);
    
    SemanticObject semObject = SemanticObject.createSemanticObject(postUri);
    PostIn postIn = (PostIn) semObject.getGenericInstance();
    SocialNetwork postInSN = postIn.getPostInSocialNetwork();
    
    if(action == null){//Displays user actions available for each social network
        System.out.println("\n\t\tDisplays user actions available for each social network");
        out.println("<b><font color=\"#CC6600\">Available actions for</font></b></br>");
        out.print("<style type=\"text/css\">");
        out.print(" span.inline { display:inline; }");
        out.print("</style>");
        if(postInSN instanceof Twitter){//Displays RT, Favorite
            out.println("<b><font color=\"#CC6600\">Twitter</font></b></br>");
            twitter4j.Twitter twitter = null;
            Twitter semanticTwitter = (Twitter)postInSN;
            try{
                twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
                if(twitter == null){
                    return;
                }
                Long id = Long.parseLong(postIn.getSocialNetMsgId());
                //Due to the limitation on the API side, isRetweetedByMe() works only with timeline methods.
                Status st = twitter.showStatus(id);

                if(st.isRetweetedByMe()){
                    //System.out.println("Retwited by me!");
                    out.print("<span class=\"inline\"><a href=\"#\" onclick=\"return false;" +"\">UNDO RT</a></span> ");
                }else{
                    //System.out.println("NOT Retwited by me!");
                    out.print("<span class=\"inline\" id=\"" + postIn.getURI() + "/RT\" dojoType=\"dojox.layout.ContentPane\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=RT" + "','" + postIn.getURI() +  "/RT'); return false;" +"\">RT</a></span> ");
                }

                if(st.isFavorited()){
                    out.print("<span class=\"inline\" id=\"" + postIn.getURI() + "/FAV\" dojoType=\"dojox.layout.ContentPane\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=UNDOFAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">UNDO FAVORITE</a></span> ");
                }else{
                    out.print("<span class=\"inline\" id=\"" + postIn.getURI() + "/FAV\" dojoType=\"dojox.layout.ContentPane\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=FAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">FAVORITE</a></span> ");
                }
            }catch(Exception e){
                out.println("This tweet might not exist anymore");
                System.out.println("Error getting tweet information"  + e.getMessage());
            }
        }else if(postInSN instanceof Facebook){//Displays Like
            out.println("<b><font color=\"#CC6600\">Facebook</font></b></br>");
            String postID = postIn.getSocialNetMsgId();
            Facebook facebook = (Facebook)postInSN;
            
            HashMap<String, String> params = new HashMap<String, String>(3);    
            params.put("q", "SELECT post_id FROM like WHERE user_id=me() AND post_id=\"" + postID + "\"");
            params.put("access_token", facebook.getAccessToken());
            String fbResponse = null;
            
            try{
                fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                           "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                JSONObject likeResp = new JSONObject(fbResponse);
                if(likeResp.has("data")){
                    JSONArray likes = likeResp.getJSONArray("data");

                    if(likes.length() == 1){//There is one result, I liked this post
                        out.print("<span class=\"inline\" id=\"" + postIn.getURI() + "/LIKE\" dojoType=\"dojox.layout.ContentPane\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=DISLIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">DISLIKE</a></span> ");
                    }else{
                        out.print("<span class=\"inline\" id=\"" + postIn.getURI() + "/LIKE\" dojoType=\"dojox.layout.ContentPane\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=LIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">LIKE</a></span> ");
                    }
                }else{
                    out.print("<span class=\"inline\" id=\"" + postIn.getURI() + "/LIKE\" dojoType=\"dojox.layout.ContentPane\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=LIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">LIKE</a></span> ");
                }
            }catch(Exception e){
                out.println("This post might not exist anymore");
                System.out.println("Error getting like information for Facebook post"  + e.getMessage());
            }
        }else if(postInSN instanceof Youtube){//Displays Like, Favorite
            out.println("<b><font color=\"#CC6600\">Youtube</font></b></br>");
            out.print("<span id=\"" + postIn.getURI() + "/LIKE\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=LIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">LIKE</a></span> ");
            out.print("<span class=\"inline\" id=\"" + postIn.getURI() + "/FAV\" dojoType=\"dojox.layout.ContentPane\"><a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=FAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">FAVORITE</a></span> ");
        }
    }else{//We received an action
        if(action.equalsIgnoreCase("RT")){//Only for Twitter
            String retweetId = doRT(postIn);
            if(retweetId != null){//The retweet was sent correctly
                out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=UNDORT&retweetId=" + retweetId + "','" + postIn.getURI() +  "/RT'); return false;" +"\">UNDO RT</a> ");
            }else{
                out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=RT" + "','" + postIn.getURI() +  "/RT'); return false;" +"\">RT</a> ");
            }
            
        }else if(action.equalsIgnoreCase("UNDORT")){//For Twitter and Youtube
            String retweetId = request.getParameter("retweetId");
            try{
                if(undoRT(postIn, Long.parseLong(retweetId))){
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=RT" + "','" + postIn.getURI() +  "/RT'); return false;" +"\">RT</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=UNDORT&retweetId=" + retweetId + "','" + postIn.getURI() +  "/RT'); return false;" +"\">UNDO RT</a> ");
                }
            }catch(NumberFormatException nfe){
                System.out.println("Error undoing retweet:" + nfe.getMessage());
            }
        }else if(action.equalsIgnoreCase("FAV")){//For Twitter and Youtube
            if(postInSN instanceof Twitter){
                if(doFavorite(postIn)){//Action executed correctly
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=UNDOFAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">UNDO FAVORITE</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=FAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">FAVORITE</a> ");
                }
            }else if(postInSN instanceof Youtube){
                String favoriteId = doYTFavorite(postIn);
                if(favoriteId != null){//Action executed correctly
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=UNDOFAV&favoriteId=" + favoriteId + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">UNDO FAVORITE</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=FAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">FAVORITE</a> ");
                }
            }
            //out.println("Doing FAV");
        }else if(action.equalsIgnoreCase("UNDOFAV")){//For Twitter and Youtube
            if(postInSN instanceof Twitter){
                if(undoFavorite(postIn)){//Action executed correctly
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=FAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">FAVORITE</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=UNDOFAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">UNDO FAVORITE</a> ");
                }
            }else if(postInSN instanceof Youtube){
                String favoriteId = request.getParameter("favoriteId");
                if(undoYTFavorite(postIn, favoriteId) == null){//Action executed correctly
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=FAV" + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">FAVORITE</a> ");                    
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=UNDOFAV&favoriteId=" + favoriteId + "','" + postIn.getURI() +  "/FAV'); return false;" +"\">UNDO FAVORITE</a> ");
                }
            }
            //out.println("UNDOING FAV");
        }else if(action.equalsIgnoreCase("LIKE")){//LIKE Only for Youtube and Facebook
            if(postInSN instanceof Facebook){//Do Like and shows 'Dislike'
                if(doFBLike(postIn)){//Action executed correctly
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=DISLIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">DISLIKE</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=LIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">LIKE</a> ");
                }
            }else if(postInSN instanceof Youtube){                
                if(doYTLikeDislike(postIn, "like")){
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=DISLIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">DISLIKE</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=LIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">LIKE</a> ");
                }
            }            
        }else if(action.equalsIgnoreCase("DISLIKE")){//DISLIKE Only for Youtube and Facebook
            if(postInSN instanceof Facebook){//Do Dislike and shows 'Like'
                if(doFBDislike(postIn)){//Action executed correctly
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=LIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">LIKE</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=DISLIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">DISLIKE</a> ");
                }
            }else if(postInSN instanceof Youtube){
                if(doYTLikeDislike(postIn, "dislike")){
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=LIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">LIKE</a> ");
                }else{
                    out.print("<a href=\"#\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + "/work/models/SWBAdmin/jsp/post/postActions.jsp?postUri=" + postIn.getEncodedURI() + "&action=DISLIKE" + "','" + postIn.getURI() +  "/LIKE'); return false;" +"\">DISLIKE</a> ");
                }
            }
        }
    }
%>