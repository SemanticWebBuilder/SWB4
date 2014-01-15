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

<style type="text/css">
    div.bar{
      background-color: #F5F5F5;
      border-top: 1px solid #DDDDDD;
      box-shadow: 0 3px 8px rgba(0, 0, 0, 0.05) inset;
      cursor: pointer;
      display: block;
      font-size: 13px;
      font-weight: normal;
      padding: 10px 1px;
      position: relative;
      text-align: center;          
    }
    span.inline { display:inline; }
</style>    

<%
    try{
        String objUri = (String) request.getParameter("suri");
        String username;
        HashMap<String, String> params = new HashMap<String, String>(2);
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
<div class="swbform">
<div align="center"><h2><%=username%> - <%=paramRequest.getLocaleString("newsFeed")%></h2><br/></div>
<div class="bar" id="<%=objUri%>newPostsAvailable" dojoType="dojox.layout.ContentPane"></div>
<div id="<%=objUri%>facebookStream" dojoType="dojox.layout.ContentPane"></div>
<%
    SWBModel model=WebSite.ClassMgr.getWebSite(facebookBean.getSemanticObject().getModel().getName());
    
    params.put("limit", "50");
    params.put("fields", "id,from,to,message,message_tags,story,story_tags,picture,caption,link,object_id,application,source,name,description,properties,icon,actions,privacy,type,status_type,created_time,likes.summary(true),comments.limit(5).summary(true),place,icon");

    //SELECT status_id, time, source, message FROM status WHERE uid = me() Filtering status only
    //POSTS WITH LOCATION https://graph.facebook.com/me/home?with=location
    String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
    
    String untilPost = parseResponse(fbResponse, out, true, request, paramRequest, NEWS_FEED_TAB, model);//Gets the newest post and saves the ID of the last one    
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri).setParameter("currentTab", NEWS_FEED_TAB);
    String since = (String)session.getAttribute(objUri + NEWS_FEED_TAB + "since");
    System.out.println("\n\n\nsession.getAttribute(since):" + session.getAttribute(objUri + NEWS_FEED_TAB +  "since"));
%>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        <%
            System.out.println("SINCE: " +  since);
            
            if(since != null){//Check what this is for
                String intervalId = (String)session.getAttribute(objUri + "pooling");
                System.out.println("\n\nSESSION VAR FOR CURRENT FACEBOOK TAB:" + intervalId);
        
                if(intervalId == null){
        %>
                    var interval = setInterval(function(){ postSocialHtmlListeners('<%=renderURL.setMode("newPostsAvailable")%>','<%=objUri%>newPostsAvailable'); },20000);
                    console.log('Value of FB Interval:' + interval);
                    saveInterval('<%=paramRequest.getRenderUrl().setMode("storeInterval").setParameter("suri", objUri)+"&interval="%>' + interval);
        <%
                }else{
        %>
                    console.log('Stoping interval for current uri ' + ':' + <%=intervalId%>);
                    clearInterval(<%=intervalId%>);
                    var interval = setInterval(function(){ postSocialHtmlListeners('<%=renderURL.setMode("newPostsAvailable")%>','<%=objUri%>newPostsAvailable'); },20000);
                    console.log('Value of a NEW FB Interval:' + interval);
                    saveInterval('<%=paramRequest.getRenderUrl().setMode("storeInterval").setParameter("suri", objUri)+"&interval="%>' + interval);
        <%
                }
                //In both cases an 'interval' variable is defined and is assigned to the onClose event
        %>
                //Change the default onClose method of the parent Tab
                var tabId =  '<%=objUri%>' + '/tab';
                var cPane = dijit.byId(tabId);        
                cPane.attr('onClose', function callStopListener(){ clearInterval(interval); return true;});
        <%
            }
        %>
   </script>
</div>
<div id="<%=objUri%>getMorePosts" dojoType="dijit.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>morePostsLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("scope", "newsFeed")%>','<%=objUri%>getMorePosts', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("getMorePosts")%></a></label>
    </div>
</div>
</div>
    <%
        }catch(Exception e){
        out.print("Problem displaying News feed: " + e.getMessage());
    }
%>