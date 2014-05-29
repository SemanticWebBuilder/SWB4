<%-- 
    Document   : listFacebookPages
    Created on : 29/05/2014, 05:53:18 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.apache.commons.validator.UrlValidator"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>


<%
    try{
        HashMap<String, String> params = new HashMap<String, String>(2);
        
        //Access token de Facebook (normal)        
        params.put("access_token", "CAAIhP9SXDVgBAPXsemvmlXZBJVQZBu0MYLOc3JWjH91l6Q9NXGZAE9TpPxXRiowHIZBR8qsnm3Kds3D2I4A3k4YGZASYj8lch7p0eXHDZAoW3bApH47McsjvdX0QQZBpIZAGuULUAypOfGNIVqRdIZBJUWeJuY77azOJoM27n08tEwOtSpMgWVJR1YJNT7UIXHbEZD");
        String pageAccessToken = postRequest(params, "https://graph.facebook.com/me/accounts",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        System.out.println("pageAccessToken:" + pageAccessToken + "\n\n");
        
        out.println("THE RESPONSE:" + pageAccessToken);
        

%>

    <%
        }catch(Exception e){
        out.print("Problem displaying News feed: " + e.getMessage());
    }   
%>