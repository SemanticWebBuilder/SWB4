<%-- 
    Document   : facebookPosts
    Created on : 10/06/2013, 11:50:27 AM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="facebookBean" scope="request" type="org.semanticwb.social.Facebook"/>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<div class="swbform">
<div align="center"><h2>Showing <%=facebookBean.getTitle()%> Posts.</h2><br/></div>
<div class="bar" id="newPostAvailable" dojoType="dojox.layout.ContentPane"></div>
<div id="postsStream" dojoType="dojox.layout.ContentPane"></div>
<%
    String objUri = (String) request.getParameter("suri");
    String since = (String)session.getAttribute("since");
    
    HashMap<String, String> params1 = new HashMap<String, String>(3);    
    params1.put("q", "SELECT actor_id, created_time, likes, post_id, attachment, message, comments FROM stream WHERE filter_key IN ( SELECT filter_key FROM stream_filter WHERE uid = me() AND NOT (name='Photos') AND NOT (name='Video')) LIMIT 10");
    params1.put("access_token", facebookBean.getAccessToken());
    
    String fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
    //String untilPost = picture(fbResponse, out, true, request, paramRequest);//Gets the newest post and saves the ID of the last one
    System.out.println("fbresponse:" + fbResponse);
%>
</div>