<%-- 
    Document   : myVideos
    Created on : 12/09/2013, 07:37:35 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.social.Youtube"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.Closeable"%>
<%@page import="java.util.Collection"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%!
//Favorites Add-> Add as favorites
//Like
//Don't like
//Comment

//This view displays the videos of a Single user. The current user (default) or
//another user.

public static String getRequest(Map<String, String> params, String url,
            String userAgent, String token) throws IOException {
        
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
            ///Validate if i am looking for the default user or another
            if(token != null){
                conex.setRequestProperty("Authorization", "Bearer " + "ya29.AHES6ZQJgECzLX3Y5hbAkVMn7qCupPdOJ6RR9d4tO0pEhUziVlAdFwKL4A");
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
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=x-iso-8859-11">
    </head>
    <body>
        <h1>My Videos</h1>
        
        <%
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("v", "2");
        params.put("alt","jsonc");
        String objUri = (String)request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        //Validate token from youtube and pass it as param.
        String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/" + "xxxreckoningxxx" + "/uploads" ,
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
        //String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/default/uploads" ,
        //                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "token");
        
        //out.println("tyResponse:" + ytResponse);
        JSONObject jsonResponse = new JSONObject(ytResponse);
        JSONArray videosArray = null;
        if(jsonResponse.has("data")){
            if(jsonResponse.getJSONObject("data").has("items")){
                videosArray = jsonResponse.getJSONObject("data").getJSONArray("items");
            }
        }
        
        
        HashMap<String, String> paramsComments = new HashMap<String, String>(3);
        paramsComments.put("v", "2");
        paramsComments.put("max-results", "10");
        paramsComments.put("start-index", "1");
        paramsComments.put("alt", "json");
        
        HashMap<String, String> paramsUsr = new HashMap<String, String>(3);
        paramsUsr.put("v", "2");
        paramsUsr.put("fields", "media:thumbnail");
        paramsUsr.put("alt", "json");
        
        //THE INFO OF THE USER SHOULD BE DISPLAYED AT TOP
        if(videosArray != null){
            for(int i = 0; i < videosArray.length(); i++ ){
                String usrProfile = getRequest(paramsUsr, "http://gdata.youtube.com/feeds/api/users/" + videosArray.getJSONObject(i).getString("uploader"),
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);           

                JSONObject usrJson = new JSONObject(usrProfile);
                if(!usrJson.isNull("entry")){
                    if(!usrJson.getJSONObject("entry").isNull("media$thumbnail")){
                        if(!usrJson.getJSONObject("entry").getJSONObject("media$thumbnail").isNull("url")){
                            out.print("Picture: <img src=\"" + usrJson.getJSONObject("entry").getJSONObject("media$thumbnail").getString("url") + "\" width=\"50\" height=\"50\"/>");
                        }
                    }
                }
                
                out.print("<div class=\"timeline timelinefacebook\">");
                //Username and story
                out.print("<p>");
                out.print(videosArray.getJSONObject(i).getString("title"));
                out.print("</p>");
                
                out.print("<div class=\"timelineembed\">");
                out.print(" <span>");
                out.print("  <embed src=" + "http://www.youtube.com/v/" +videosArray.getJSONObject(i).getString("id") + " width=\"250\" height=\"195\" autostart=\"false\" type=\"application/x-shockwave-flash\">");
                out.print(" </span>");
                
                out.print("<p class=\"imgtitle\">");
                out.print(  videosArray.getJSONObject(i).getString("title"));
                out.print("</p>");
                
                out.print("<p class =\"imgdesc\">");
                out.print( videosArray.getJSONObject(i).isNull("description") ?  "&nbsp;" : videosArray.getJSONObject(i).getString("description"));
                out.print("</p>");
                out.print("</div>");//End First section
                
                
                out.print("<div class=\"clear\"></div>");//Clear
                
                //Comments,start
                String ytComments = "";
                if(!videosArray.getJSONObject(i).isNull("commentCount") && videosArray.getJSONObject(i).getInt("commentCount")>0){
                    ytComments= getRequest(paramsComments, "https://gdata.youtube.com/feeds/api/videos/" + videosArray.getJSONObject(i).getString("id") + "/comments",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
                    JSONObject jsonComments = new JSONObject(ytComments);
                    JSONArray arrayComments = null;
                    if(!jsonComments.isNull("feed")){
                        if(!jsonComments.getJSONObject("feed").isNull("entry")){
                            arrayComments = jsonComments.getJSONObject("feed").getJSONArray("entry");
                        }
                    }
                    
                    if(arrayComments != null && arrayComments.length() > 0){
                        out.print("<ul>");
                        for(int c = 0; c < arrayComments.length(); c++){
                            JSONObject comment = arrayComments.getJSONObject(c);
                            JSONObject usrCommentProfile = null;
                            if(!comment.isNull("author")){
                                if(!comment.getJSONArray("author").getJSONObject(0).isNull("yt$userId")){
                                    String commentProfile = getRequest(paramsUsr, "http://gdata.youtube.com/feeds/api/users/" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t"),
                                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
                                    usrCommentProfile = new JSONObject(commentProfile);
                                    
                                }
                            }
                            out.print("<li>");
                            out.print("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + "#" + "','" + "source" + "'); return false;\"><img src=\"" + usrCommentProfile.getJSONObject("entry").getJSONObject("media$thumbnail").getString("url") + "\" width=\"50\" height=\"50\"/></a>");

                            out.print("<p>");
                            out.print("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + "/work/models/SWBAdmin/jsp/socialNetworks/youtubeUserProfile.jsp?suri=null&id=" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("yt$userId").getString("$t") + "','" + "Ver perfil" + "'); return false;\">" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t") + "</a>:");
                            //out.print("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + "#" + "','" + "source" + "'); return false;\">" + comment.getJSONArray("author").getJSONObject(0).getJSONObject("name").getString("$t") + "</a>:");
                            //out.println("Uploader: <a href=\"#\" onclick=\"showDialog('" + "/work/models/SWBAdmin/jsp/socialNetworks/youtubeUserProfile.jsp" + "','Ver Perfil'); return false;\">" + videosArray.getJSONObject(i).getString("uploader") + "</a></br>");
                            out.print(       comment.getJSONObject("content").getString("$t").replace("\n", "</br>") + "</br>");
                            out.print("</p>");

                            //Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));

                            out.print("<p class=\"timelinedate\">");
                            out.print("<span dojoType=\"dojox.layout.ContentPane\">");

                            out.print("<em>" + "creado el" + "</em>");                            
                            out.print("</span>");
                            out.print("</p>");
                            out.print("</li>");
                        }
                        
                        if(!videosArray.getJSONObject(i).isNull("commentCount") && videosArray.getJSONObject(i).getInt("commentCount") > 10){//Link to get more comments
                            out.print("<li class=\"timelinemore\">");
                            out.print("<label><a href=\"#\" onclick=\"appendHtmlAt('" + "url"
                                    + "','" + "" +"/comments', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\"><span>+</span>View more comments</a></label>");
                            out.print("</li>");
                        }
                        out.print("</ul>");
                    }
                }
                //Comments
                
                out.print("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");//timelineresume
                out.print("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.print("<em>" + videosArray.getJSONObject(i).getString("uploaded") + "</em>");                
                if(videosArray.getJSONObject(i).has("viewCount")){
                    out.println("Views:" + videosArray.getJSONObject(i).getInt("viewCount"));
                }
                out.print("<strong><span> Likes: </span>");
                if(videosArray.getJSONObject(i).has("likeCount")){
                    out.print(videosArray.getJSONObject(i).getInt("likeCount"));           
                }else{
                    out.print("0");
                }
                
                out.print(" Dislikes: ");
                if(videosArray.getJSONObject(i).has("likeCount") && videosArray.getJSONObject(i).has("ratingCount")){
                    out.println(videosArray.getJSONObject(i).getInt("ratingCount") - videosArray.getJSONObject(i).getInt("likeCount"));
                }else{
                    out.print("0");
                }
                
                if(videosArray.getJSONObject(i).has("favoriteCount")){
                    out.println(" Favorites: " + videosArray.getJSONObject(i).getInt("favoriteCount") + "</br>");
                }
                
                out.print("</strong>");
                out.print("</span>");
                out.print("</div>");//timelineresume
                ///////out.print("ytComments:" +ytComments + "\n\n");
                out.println("Uploader: <a href=\"#\" onclick=\"showDialog('" + "/work/models/SWBAdmin/jsp/socialNetworks/youtubeUserProfile.jsp" + "','Ver Perfil'); return false;\">" + videosArray.getJSONObject(i).getString("uploader") + "</a></br>");
                
                if(videosArray.getJSONObject(i).has("rating")){
                    out.println("Rating:" + videosArray.getJSONObject(i).getDouble("rating") + "</br>");
                }                
                out.println("</br></br>");
                out.print("</div>");
            }
        }
        /*
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        builder = factory.newDocumentBuilder();
        Document xmlDoc = builder.parse(new InputSource(new StringReader(ytResponse)));
        xmlDoc.getDocumentElement().normalize();
        NodeList rootNode = xmlDoc.getDocumentElement().getChildNodes();
        
        for( int tmp = 0; tmp < rootNode.getLength(); tmp++){
            Node nNode= rootNode.item(tmp);
            System.out.println("\nCurrent element:" + nNode.getNodeName());
            
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element)nNode;
            }
        }
    */
        
        %>
    </body>
</html>
