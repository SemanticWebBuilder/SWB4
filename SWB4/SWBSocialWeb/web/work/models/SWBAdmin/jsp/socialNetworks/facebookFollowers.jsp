<%-- 
    Document   : facebookFollowers
    Created on : 25/03/2014, 07:09:42 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.model.User"%>
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
<%@page import="org.semanticwb.social.SocialTopic"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="facebookBean" scope="request" type="org.semanticwb.social.Facebook"/>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>


<%!
    public static String getFullUserProfileFromId(String more, Facebook facebook) {
        HashMap<String, String> params1 = new HashMap<String, String>(3);
        params1.put("access_token", facebook.getAccessToken());

        String fbResponse = null;
        try {
            if (more.equals("friends")) {
                params1.put("limit", "20");
                fbResponse = getRequest(params1, "https://graph.facebook.com/me/friends",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            } else {
                params1.put("limit", "30");
                fbResponse = getRequest(params1, "https://graph.facebook.com/me/subscribers",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            }
        } catch (Exception e) {
        }
        return fbResponse;
    }

%>

<%
    
    String objUri = (String) request.getParameter("suri");

    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    Facebook facebook = (Facebook) semanticObject.createGenericInstance();
    
    String username;
    HashMap<String, String> paramsUsr = new HashMap<String, String>(2);
    paramsUsr.put("access_token", facebookBean.getAccessToken());

    String user = postRequest(paramsUsr, "https://graph.facebook.com/me",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");    
    JSONObject userObj = new JSONObject(user);
    if(!userObj.isNull("name")){
        username = userObj.getString("name");
    }else{
        username = facebookBean.getTitle();
    }

    JSONObject object = new JSONObject();
    String usrFollower = getFullUserProfileFromId("subscriber", facebook);
    JSONObject usrFollow = new JSONObject(usrFollower);
    JSONArray usrDataFollow = usrFollow.getJSONArray("data");
%>


<div class="timelineTab" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
    <div class="timelineTab-title"><p><strong><%=username%></strong><%="Seguidores"%></p></div>
        
            <%
                String imageFollow = "";
                String nameFollow = "";
                String nextpageFollow = "";
                
                for (int k = 0; k < usrDataFollow.length(); k++) {
                    object = (JSONObject) usrDataFollow.get(k);
                    imageFollow = object.getString("id");
                    nameFollow = object.getString("name");
    

            %>
            <div class="timeline timelinetweeter">
                <p class="tweet">
                    <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("fullProfile").setParameter("suri", objUri).setParameter("type", "noType").setParameter("id", imageFollow).setParameter("targetUser", nameFollow)%>','<%= nameFollow + " - " + nameFollow%>'); return false;" href="#"><%=nameFollow%></a>
                </p>
                <p class="tweet">
                    <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("fullProfile").setParameter("suri", objUri).setParameter("type", "noType").setParameter("id", imageFollow).setParameter("targetUser", imageFollow)%>','<%= imageFollow + " - " + imageFollow%>'); return false;" href="#">
                        <img src="https://graph.facebook.com/<%=imageFollow%>/picture?width=150&height=150" width="150" height="150"/>
                    </a>
                </p>
            </div>
            <%
                }     
            %>

            <%

                if (usrFollow.has("paging")) {
                    nextpageFollow = usrFollow.getJSONObject("paging").getString("next");
                    int position = nextpageFollow.indexOf("after");
                    String nextpageFollowSend = nextpageFollow.substring(position + 6, nextpageFollow.length());
                    //   String afterId = "";
                    // System.out.println("2nextpage :"+nextpageFollow);
                    //position = nextpageFollow.indexOf("__after_id");
                    //afterId = nextpageFollow.substring(position+11, nextpageFollow.length());
                    position = nextpageFollow.indexOf("limit");
                    String offset = nextpageFollow.substring(position + 6, position + 8);
            %>

            </br></br>
            
            
            <div id="<%=objUri%>/getMoreSubscribers" dojoType="dojox.layout.ContentPane">
                <div align="center" style="margin-bottom: 10px;">
                    <label>
                        <a href="#" onclick="appendHtmlAt('<%=paramRequest.getRenderUrl().setMode("more").setParameter("type", "subscriber").setParameter("suri", facebook.getURI()).setParameter("nextPage", nextpageFollowSend).setParameter("offsetFollow", offset)%>', '<%=objUri%>/getMoreSubscribers', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;">Mas seguidores</a>
                    </label>
                </div>
            </div>
            <%}%>
</div>
