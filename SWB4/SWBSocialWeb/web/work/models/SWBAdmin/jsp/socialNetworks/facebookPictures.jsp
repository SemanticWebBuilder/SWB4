<%-- 
    Document   : facebookPictures
    Created on : 7/06/2013, 06:54:00 PM
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
    try{
        String objUri = (String) request.getParameter("suri");
        String username;
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("access_token", facebookBean.getAccessToken());
        String user = postRequest(params, "https://graph.facebook.com/me",
                                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        System.out.println("user:"+ user);
        JSONObject userObj = new JSONObject(user);
        if(!userObj.isNull("name")){
            username = userObj.getString("name");
        }else{
            username = facebookBean.getTitle();
        }
%>
<div class="timelineTab" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
<div class="timelineTab-title"><p><strong><%=username%></strong><%=paramRequest.getLocaleString("myImages")%></p></div>
<div class="bar" id="<%=objUri%>newPicturesAvailable" dojoType="dojox.layout.ContentPane"></div>
<div id="<%=objUri%>picturesStream" dojoType="dojox.layout.ContentPane"></div>
<%
    System.out.println("SURI jsp: " + paramRequest.getMode().toString());
    SWBModel model=WebSite.ClassMgr.getWebSite(facebookBean.getSemanticObject().getModel().getName());

    params.put("q", "{\"pictures\": \"SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comment_info FROM stream WHERE filter_key IN " + 
                "( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Photos') ORDER BY created_time DESC LIMIT 50\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT actor_id FROM #pictures)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT actor_id FROM #pictures)\"}");
    
    //params1.put("access_token", "CAACEdEose0cBAKyWLxR6XedK1KrfMDVmqUQshOoZA2vGCnuqIyrekZCGQ9HZBc0FWKIXfNMexJGxxinNvtcvEnHGkBLpCCmEuPVgmUAddZCxcDWc1KigZCrYaDCSSoEUHIhda1G3y4tCZBq4ripHKZAw1steVi0NGYZD");    
    String fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
    String createdTime = picture(fbResponse, out, true, request, paramRequest, model);//Gets the newest post and saves the ID of the last one
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri).setParameter("currentTab", PICTURES_TAB);
    
    /*
I have listed down type codes I have found:
11 - Group created.
12 - Event created.
46 - Status update.
56 - Post on Timeline from another user.
60 - changed profile picture.
65 - Tagged photo.
66 - Note created.
80 - Link posted ( shared Photo/Link).
128 - Video posted.
161 - Likes*.
245 - Likes a photo/Link*.
247 - Photos posted.
237 - App/Games story.
257 - Comment created.
272 - App story.
283 - Likes a Link*. 
285 - Checkin to a place.
295 - Post to Timeline of a friend from other friend.
308 - Post in Group.
347 - Likes a Link*.
373 - updated cover photo.
    */
%>
<div id="<%=objUri%>getMorePictures" dojoType="dijit.layout.ContentPane">
    <div align="center" style="margin-bottom: 10px;">
        <label id="<%=objUri%>morePicturesLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMorePictures").setParameter("createdTime", createdTime).setParameter("currentTab", PICTURES_TAB)%>','<%=objUri%>getMorePictures', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("getMoreImages")%></a></label>
    </div>
</div>
</div>
<%
        }catch(Exception e){
        out.print("Problem displaying Facebook Pictures: " + e.getMessage());
    }
%>