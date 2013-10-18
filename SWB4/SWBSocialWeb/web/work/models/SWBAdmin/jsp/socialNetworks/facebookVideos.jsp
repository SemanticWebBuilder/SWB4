<%-- 
    Document   : facebookVideos
    Created on : 10/06/2013, 09:50:18 AM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="facebookBean" scope="request" type="org.semanticwb.social.Facebook"/>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%
    String objUri = (String) request.getParameter("suri");
%>
<div class="swbform">
<div align="center"><h2><%=facebookBean.getTitle()%> Videos.</h2><br/></div>
<%
    System.out.println("SURI jsp: " + paramRequest.getMode().toString());
    SWBModel model=WebSite.ClassMgr.getWebSite(facebookBean.getSemanticObject().getModel().getName());
    HashMap<String, String> params = new HashMap<String, String>(3);//SELECT uid, name, first_name, middle_name, last_name FROM user WHERE uid = 1921576442
    //TODO: it seems than 'likes' is deprecated and it must be replaced with like_info
    params.put("q", "{\"videos\": \"SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comment_info FROM stream WHERE filter_key IN " + 
                "( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Video') ORDER BY created_time DESC LIMIT 50\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT actor_id FROM #videos)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT actor_id FROM #videos)\"}");
    params.put("access_token", facebookBean.getAccessToken());
    
    //params1.put("access_token", "CAACEdEose0cBAKyWLxR6XedK1KrfMDVmqUQshOoZA2vGCnuqIyrekZCGQ9HZBc0FWKIXfNMexJGxxinNvtcvEnHGkBLpCCmEuPVgmUAddZCxcDWc1KigZCrYaDCSSoEUHIhda1G3y4tCZBq4ripHKZAw1steVi0NGYZD");    
    String fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
    //out.println(fbResponse);
    String createdTime = video(fbResponse, out, true, request, paramRequest, model);//Gets the newest post and saves the ID of the last one
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri).setParameter("currentTab", VIDEOS_TAB);
%>
<div id="<%=objUri%>getMoreVideos" dojoType="dijit.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>moreVideosLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreVideos").setParameter("createdTime", createdTime).setParameter("currentTab", VIDEOS_TAB)%>','<%=objUri%>getMorePictures', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;">More videos</a></label>
    </div>
</div>
</div>
