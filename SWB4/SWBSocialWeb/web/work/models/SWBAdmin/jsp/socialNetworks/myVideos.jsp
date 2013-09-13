<%-- 
    Document   : myVideos
    Created on : 12/09/2013, 07:37:35 PM
    Author     : francisco.jimenez
--%>

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
        String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/" + "xxxreckoningxxx" + "/uploads" ,
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
        
        out.println("tyResponse:" + ytResponse);
        JSONObject jsonResponse = new JSONObject(ytResponse);
        JSONArray videosArray = null;
        if(jsonResponse.has("data")){
            if(jsonResponse.getJSONObject("data").has("items")){
                videosArray = jsonResponse.getJSONObject("data").getJSONArray("items");
            }
        }
        if(videosArray != null){
            for(int i = 0; i < videosArray.length(); i++ ){                
                out.println("<embed src=" + "http://www.youtube.com/v/" +videosArray.getJSONObject(i).getString("id") + " width=\"250\" height=\"195\" autostart=\"false\" type=\"application/x-shockwave-flash\">");
                out.println("Title:" + videosArray.getJSONObject(i).getString("title") + "</br>");
                out.println("Description:" + videosArray.getJSONObject(i).getString("description") + "</br>");
                out.println("Uploader:" + videosArray.getJSONObject(i).getString("uploader") + "</br>");
                out.println("Uploaded:" + videosArray.getJSONObject(i).getString("uploaded") + "</br>");                
                if(videosArray.getJSONObject(i).has("viewCount")){
                    out.println("View Count:" + videosArray.getJSONObject(i).getInt("viewCount") + "</br>");
                }
                if(videosArray.getJSONObject(i).has("favoriteCount")){
                    out.println("Favorite Count:" + videosArray.getJSONObject(i).getInt("favoriteCount") + "</br>");
                }
                //
                //
                out.println("Comment Count:" + videosArray.getJSONObject(i).getInt("commentCount") + "</br>");
                out.println("</br></br>");
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
