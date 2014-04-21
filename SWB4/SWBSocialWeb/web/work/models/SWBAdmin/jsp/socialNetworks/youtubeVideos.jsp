<%-- 
    Document   : myVideos
    Created on : 12/09/2013, 07:37:35 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.model.UserGroup"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="org.xml.sax.InputSource"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>

<%@page import="java.io.StringReader"%>
<%@page import="java.io.StringReader"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.social.SocialUserExtAttributes"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
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
<%@page import="static org.semanticwb.social.admin.resources.YoutubeWall.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<!DOCTYPE html>

<style type="text/css">
    span.inline { display:inline; }
</style>


<div class="timelineTab" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
        <%
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("v", "2");
        params.put("alt","jsonc");
        String objUri = (String)request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
        //semanticYoutube.setRefreshToken("1/EBI7ANgfHcp7CHm3acP5hGoFZ29XhZzIzT2jv_h-3so");
        System.out.println("ACCESS TOKEN:" + semanticYoutube.getAccessToken());
        if(!semanticYoutube.validateToken()){//If was unable to refresh the token
            System.out.println("unable to refresh the token!");
            out.println("Problem refreshing access token");
            return;
        }
        out.println("<div class=\"timelineTab-title\" style=\"width: 620px !important;\"><p style=\"width:620px\"><strong>" + "Mis Videos" + "</strong>" + semanticYoutube.getTitle() + "</p></div>");
        
        //Validate token from youtube and pass it as param.
                //String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/" + "xxxreckoningxxx" + "/uploads" ,blackcat060406
                //String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/" + "unam" + "/uploads" ,
        //String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/" + "unam" + "/uploads" ,
        //        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", null);
        //out.println("ytResponse:" + ytResponse);
        String ytResponse = getRequest(params, "http://gdata.youtube.com/feeds/api/users/default/uploads" ,
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", semanticYoutube.getAccessToken());
        JSONObject jsonResponse = new JSONObject(ytResponse);
        JSONArray videosArray = null;
        if(jsonResponse.has("data")){
            if(jsonResponse.getJSONObject("data").has("items")){
                videosArray = jsonResponse.getJSONObject("data").getJSONArray("items");
            }
        }
        
                
        SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
        SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
        String postURI = null;
        org.semanticwb.model.User user = paramRequest.getUser();
        SocialUserExtAttributes socialUserExtAttr = null;
        if(user.isSigned()){
            socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
        }

        UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");                            
        //THE INFO OF THE USER SHOULD BE DISPLAYED AT TOP
        int totalVideos = 0;
        if(videosArray != null){
            for(int i = 0; i < videosArray.length(); i++ ){
                totalVideos++;
                doPrintVideo(request, response, paramRequest, out, postURI, socialUserExtAttr, videosArray.getJSONObject(i), user.hasUserGroup(userSuperAdminGrp));
            }
            
            if(totalVideos >= 25){
    %>
                <div id="<%=objUri%>/getMoreVideos" dojoType="dojox.layout.ContentPane">
                    <div align="center" style="margin-bottom: 10px;">
                        <label id="<%=objUri%>/moreVideosLabel"><a href="#" onclick="appendHtmlAt('<%=paramRequest.getRenderUrl().setMode("getMoreVideos").setParameter("maxVideoId", totalVideos+"").setParameter("suri", objUri)%>','<%=objUri%>' + '/getMoreVideos', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;">Mas Videos</a></label>
                    </div>
                </div>
    <%
            }
        }
    %>
    </div>