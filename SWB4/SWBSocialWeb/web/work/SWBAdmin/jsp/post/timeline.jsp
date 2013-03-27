<%-- 
    Document   : timeline
    Created on : 21/03/2013, 01:55:45 PM
    Author     : francisco.jimenez
--%>
<%@page import="org.semanticwb.social.admin.resources.Timeline"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.social.Twitter"%>
<%@page import="org.semanticwb.social.base.TwitterBase"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="twitter4j.FilterQuery"%>
<%@page import="twitter4j.Query"%>
<%@page import="twitter4j.Status"%>
<%@page import="twitter4j.StatusListener"%>
<%@page import="twitter4j.StatusUpdate"%>
<%@page import="twitter4j.TwitterException"%>
<%@page import="twitter4j.TwitterFactory"%>
<%@page import="twitter4j.TwitterStream"%>
<%@page import="twitter4j.TwitterStreamFactory"%>
<%@page import="twitter4j.auth.AccessToken"%>
<%@page import="twitter4j.auth.RequestToken"%>
<%@page import="twitter4j.conf.Configuration"%>
<%@page import="twitter4j.conf.ConfigurationBuilder"%>
<%@page import="twitter4j.URLEntity"%>
<%@page import="twitter4j.MediaEntity"%>
<%@page import="twitter4j.HashtagEntity"%>
<%@page import="twitter4j.UserMentionEntity"%>
<%@page import="twitter4j.Paging"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="twitterBean" scope="request" type="twitter4j.Twitter"/>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%    
    String objUri = (String) request.getParameter("suri");
    //SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    //Twitter semanticTwitter = (Twitter) semanticObject.createGenericInstance();
    SWBResourceURL actionURL = paramRequest.getActionUrl().setParameter("suri", objUri);
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri);
    Date currentDate;
    long maxTweetID = 0L;
%>

    <div class="swbform">
<%
    try {
            //gets Twitter4j instance with account credentials
            System.out.println("Showing @" + twitterBean.getScreenName() +  "'s home timeline.");
            out.println("<h2>Showing @" + twitterBean.getScreenName() +  "'s home timeline. </h2><br/>");
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(10);            
            int i = 0;
            for (Status status : twitterBean.getHomeTimeline(paging)){
                maxTweetID = status.getId();
                doPrintTweet(request, response, paramRequest, status, out);
                i++;
            }
            System.out.println("Total tweets:" + i);
        } catch (Exception te) {
            System.out.println("Se presento un error!!");
            te.printStackTrace();
        }
%>    
<div align="center" id="getMoreTweets" dojoType="dijit.layout.ContentPane">
    <label id="moreTwitLabel"><a href="#" onclick="appendHtmlTmp('<%=renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"")%>','getMoreTweets');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More tweets</a></label>
</div>
</div>
