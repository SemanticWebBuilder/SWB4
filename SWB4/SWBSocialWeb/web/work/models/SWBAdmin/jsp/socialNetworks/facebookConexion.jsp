<%-- 
    Document   : facebookNewsFeed
    Created on : 26/06/2013, 04:34:46 PM
    Author     : francisco.jimenez
--%>

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
                fbResponse = getRequest(params1, "https://graph.facebook.com/me/friends?access_token=" + facebook.getAccessToken() + "&limit=30",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            } else {
                fbResponse = getRequest(params1, "https://graph.facebook.com/me/subscribers?access_token=" + facebook.getAccessToken() + "&limit=30",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            }
        } catch (Exception e) {
            System.out.println("Error getting user information" + e.getMessage());
        }
        return fbResponse;
    }

%>

<%

    String objUri = (String) request.getParameter("suri");
    System.out.println("objUri" + objUri);

    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    System.out.println("semanticObject" + semanticObject);
    Facebook facebook = (Facebook) semanticObject.createGenericInstance();

    String usrProfile = getFullUserProfileFromId("friends", facebook);

    JSONObject usrResp = new JSONObject(usrProfile);
    System.out.println("usrResp" + usrResp);
    JSONArray usrData = usrResp.getJSONArray("data");


    JSONObject object = new JSONObject();
    String nextPage = null;

%>

<table>
    <tr>
        <td>
            <div >     
                <h3>  Amigos</h3>
                <%
                    String image = "";
                    String name = "";
                    for (int k = 0; k < usrData.length(); k++) {
                        object = (JSONObject) usrData.get(k);
                        image = object.getString("id");
                        name = object.getString("name");

                %>

                <img src="https://graph.facebook.com/<%=image%>/picture?width=150&height=150" width="150" height="150"/>
                <%=name%>
                <br>

                <%
                    }
                %>

                <%
                    if (usrResp.has("paging")) {
                        nextPage = usrResp.getJSONObject("paging").getString("next");
                %>
                
                <a href="#" onclick="javascript:postHtml('<%=paramRequest.getRenderUrl().setMode("more").setParameter("type", "friends").setParameter("suri", "http://www.Prueba.swb#social_Facebook:1").setParameter("nextPage", nextPage)%>', 'moreFriends<%=facebook%>');" >Mas amigos</a>
                <%   }%>
            </div>
            <div id="moreFriends<%=facebook%>" dojoType="dijit.layout.ContentPane">
            </div>

        </td>
        <td>


            <%

                String usrFollower = getFullUserProfileFromId("subscriber", facebook);

                JSONObject usrFollow = new JSONObject(usrFollower);
                System.out.println("usrFollow" + usrFollow);
                JSONArray usrDataFollow = usrFollow.getJSONArray("data");

                JSONObject objectFollow = new JSONObject();
                String nextPageFollow = null;


            %>




            <div >  
                <h3>Seguidores</h3>
                <%
                    String imageFollow = "";
                    String nameFollow = "";
                    for (int k = 0; k < usrDataFollow.length(); k++) {
                        object = (JSONObject) usrDataFollow.get(k);
                        imageFollow = object.getString("id");
                        nameFollow = object.getString("name");

                %>

                <img src="https://graph.facebook.com/<%=imageFollow%>/picture?width=150&height=150" width="150" height="150"/>
                <%=nameFollow%>
                <br>

                <%
                    }
                %>

                <%
                    if (usrFollow.has("paging")) {
                        nextPage = usrFollow.getJSONObject("paging").getString("next");
                %>

                <a href="#" onclick="javascript:postHtml('<%=paramRequest.getRenderUrl().setMode("more").setParameter("type", "subscriber").setParameter("suri", "http://www.Prueba.swb#social_Facebook:1").setParameter("nextPage", nextPage)%>', 'moreSubscriber<%=facebook%>');" >Mas seguidores</a>
                <%   }%>

            </div>                   
            <div id="moreSubscriber<%=facebook%>" dojoType="dijit.layout.ContentPane">
            </div>
        </td>
    </tr>

</table>





