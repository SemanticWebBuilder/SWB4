<%-- 
    Document   : youtubeConexion
    Created on : 9/01/2014, 05:14:46 PM
    Author     : gabriela.rosales
--%>

<%@page import="org.semanticwb.social.Youtube"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="static org.semanticwb.social.admin.resources.YoutubeWall.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>



<%@page import="java.util.HashMap"%>
<%!    
    public static String getFullUserProfileFromId(String more, Youtube youtube, String url) {
       
        HashMap<String, String> paramsVideo = new HashMap<String, String>(3);
        paramsVideo.put("v", "2");
        paramsVideo.put("alt", "json");//https://gdata.youtube.com/feeds/api/videos/videoid?v=2
        String fbResponse = null;
        try {
            
            fbResponse = getRequest(paramsVideo, "" + url + "",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", youtube.getAccessToken());
            
        } catch (Exception e) {
        }
        return fbResponse;
    }

%>


<%    
    String uri = (String) request.getParameter("suri");
    SemanticObject semanticObject = SemanticObject.createSemanticObject(uri);
    Youtube youtube = (Youtube) semanticObject.createGenericInstance();    
    if(!youtube.validateToken()){//If was unable to refresh the token
        System.out.println("unable to refresh the token!");
        out.println("Problem refreshing access token");
        return;
    }
    String url = "https://gdata.youtube.com/feeds/api/users/default/subscriptions";
    
    String usrProfile = getFullUserProfileFromId("friends", youtube, url);
    JSONObject usrResp = new JSONObject(usrProfile);
    JSONArray usrData = usrResp.getJSONObject("feed").getJSONArray("entry");   
    
    
    JSONObject object = new JSONObject();
    JSONObject objectID = new JSONObject();
    
%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<div class="timelineTab" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
            <%                                
                //out.println("<div align=\"center\"><h2>  </br> Siguiendo</h2><br/></div>");
                out.println("<div class=\"timelineTab-title\"><p><strong>" + "Conexiones" + "</strong>" + "Personas que sigo" + "</p></div>");
                JSONObject name = new JSONObject();
                for (int k = 0; k < usrData.length(); k++) {
                    object = (JSONObject) usrData.get(k);
                    objectID = (JSONObject) object.get("yt$channelId");
                    name = (JSONObject) object.get("yt$username");
                    
            %>
            <div class="timeline timelinetweeter">
                <p class="tweeter">
                    <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", objectID.getString("$t"))%>', '<%=name.getString("display")%>'); return false;"  href="#"><%=name.getString("display")%></a>
                </p>
                <p class="tweeter">
                    <a href="#"  onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("id", objectID.getString("$t"))%>', '<%=name.getString("display")%>'); return false;">
                        <img src=" <%=object.getJSONObject("media$thumbnail").getString("url")%> "  width="88"  height="88"/>
                    </a>
                </p>


                <p class="tweet">                    
                </p>                
            </div>
            <%                    
                }
            %>
</div>
