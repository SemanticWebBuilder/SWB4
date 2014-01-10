<%-- 
    Document   : facebookNewsFeed
    Created on : 3/01/2014, 04:34:46 PM
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
        String fbResponse = null;
        try {
            if (more.equals("friends")) {
                fbResponse = getRequest(params1, "https://graph.facebook.com/me/friends?access_token=" + facebook.getAccessToken() + "&limit=20",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            } else {
                fbResponse = getRequest(params1, "https://graph.facebook.com/me/subscribers?access_token=" + facebook.getAccessToken() + "&limit=30",
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
    SWBModel model = WebSite.ClassMgr.getWebSite(facebook.getSemanticObject().getModel().getName());
    SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
    String usrProfile = getFullUserProfileFromId("friends", facebook);

    JSONObject usrResp = new JSONObject(usrProfile);
    JSONArray usrData = usrResp.getJSONArray("data");


    JSONObject object = new JSONObject();
    String nextPage = null;

%>




<div>
    <div style="width:50%;float:left;display:inline-block;">
        <div class="swbform" id="gaby">
            <%

                out.println("<div class=\"swbform\">");
                out.println("<div align=\"center\"><h2>" + "@" + facebook + " " + "</br> Amigos" + "</h2><br/></div>");

                String image = "";
                String name = "";
                for (int k = 0; k < usrData.length(); k++) {
                    object = (JSONObject) usrData.get(k);

                    image = object.getString("id");
                    name = object.getString("name");

            %>
            <div class="timeline timelinetweeter">
                <p class="tweeter">
                    <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("fullProfile").setParameter("suri", objUri).setParameter("type", "noType").setParameter("id", image).setParameter("targetUser", name)%>','<%= name + " - " + name%>'); return false;" href="#"><%=name%></a>
                </p>
                <p class="tweet">
                    <img src="https://graph.facebook.com/<%=image%>/picture?width=150&height=150" width="150" height="150"/>                   
                </p>
                <div class="timelineresume">
                    <span class="inline" id="sendTweet/<%=name%>" dojoType="dojox.layout.ContentPane">
                        <a class="clasifica" href="#" onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("createPost").setParameter("suri", defaultSocialTopic.getURI()).setParameter("netSuri", facebook.getURI() ).setParameter("username", name).setParameter("network", "facebook").setParameter("idUser", image) %>','Enviar mensaje a <%=name%>');return false;">Enviar Mensaje</a>
                    </span>
                    <span class="inline" id="sendDM/<%=name%>" dojoType="dojox.layout.ContentPane">
                        <a class="clasifica" href="#" onclick="">Enviar Mensaje Directo</a>
                    </span> 

                </div>
            </div>
            <%
                }
                out.println("</div>");
            %>

            <%

                if (usrResp.has("paging")) {
                    nextPage = usrResp.getJSONObject("paging").getString("next");
            %>

            <div id="<%=objUri%>/getMoreFriendsFacebook" dojoType="dojox.layout.ContentPane">
                <div align="center">
                    <label id="<%=objUri%>/moreFriendsLabel">
                        <a href="#" onclick="appendHtmlAt('<%=paramRequest.getRenderUrl().setMode("more").setParameter("type", "friends").setParameter("suri", "http://www.Prueba.swb#social_Facebook:1").setParameter("nextPage", nextPage)%>','<%=objUri%>/getMoreFriendsFacebook', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;">Mas amigos</a>
                    </label>
                </div>
            </div>
            <%   }%>
        </div>
    </div>






    <%

        String usrFollower = getFullUserProfileFromId("subscriber", facebook);

        JSONObject usrFollow = new JSONObject(usrFollower);
        JSONArray usrDataFollow = usrFollow.getJSONArray("data");

        JSONObject objectFollow = new JSONObject();
        String nextPageFollow = null;


    %>
    <div style="margin-left:50%;">
        <div class="swbform">
            <%
                out.println("<div class=\"swbform\">");
                out.println("<div align=\"center\"><h2>" + "" + facebook + " " + "</br>Seguidores" + "</h2><br/></div>");

                String imageFollow = "";
                String nameFollow = "";
                for (int k = 0; k < usrDataFollow.length(); k++) {
                    object = (JSONObject) usrDataFollow.get(k);
                    imageFollow = object.getString("id");
                    nameFollow = object.getString("name");

            %>
            <div class="timeline timelinetweeter">
                <p class="tweet">
                    <img src="https://graph.facebook.com/<%=imageFollow%>/picture?width=150&height=150" width="150" height="150"/>
                    <%=nameFollow%>
                </p>
            </div>

            <%
                }
            %>

            <%
                out.println("</div>");

                if (usrFollow.has("paging")) {
                    nextPage = usrFollow.getJSONObject("paging").getString("next");
            %>


            <div id="<%=objUri%>/getMoreSubscribers" dojoType="dojox.layout.ContentPane">
                <div align="center">
                    <label>
                        <a href="#" onclick="appendHtmlAt('<%=paramRequest.getRenderUrl().setMode("more").setParameter("type", "subscriber").setParameter("suri", "http://www.Prueba.swb#social_Facebook:1").setParameter("nextPage", nextPage)%>', '<%=objUri%>/getMoreSubscribers', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;">Mas seguidores</a>
                    </label>
                    <%   }%>

                </div>
            </div>
        </div>    

    </div>
</div>
