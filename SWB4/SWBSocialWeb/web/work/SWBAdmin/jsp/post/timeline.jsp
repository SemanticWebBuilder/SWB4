<%-- 
    Document   : timeline
    Created on : 21/03/2013, 01:55:45 PM
    Author     : francisco.jimenez
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.social.admin.resources.SocialUserStreamListener"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
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
<%@page import="twitter4j.*"%>
<%@page import="twitter4j.conf.ConfigurationBuilder"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="twitterBean" scope="request" type="twitter4j.Twitter"/>
<jsp:useBean id="tweetsListener" scope="request" type="org.semanticwb.social.admin.resources.SocialUserStreamListener"/>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<script type="text/javascript">
    dojo.require("dojox.socket");
    // Create a socket instance
    var socket = new WebSocket('http://localhost:8080');
    alert('socket');
// Open the socket
    socket.onopen = function(event) {
	
	// Send an initial message
	socket.send('I am the client and I\'m listening!');
	
	// Listen for messages
	socket.onmessage = function(event) {
		console.log('Client received a message',event);
	};
	
	// Listen for socket closes
	socket.onclose = function(event) {
		console.log('Client notified socket has closed',event);
	};
	
	// To close the socket....
	//socket.close()
	
};    
</script>
<%!
    ConfigurationBuilder configureOAuth(Twitter twitter){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("V5Xp0RYFuf3N0WsHkqSOIQ")
          .setOAuthConsumerSecret("4DZ9UrE4X5VavUjXzBcGFTvEsHVsCGOgIuLVSZMA8")
          .setOAuthAccessToken("1137512760-v65LXmL07hgaOzZPGN6xlSiJGPNCx3BkipAuvnZ")
          .setOAuthAccessTokenSecret("F4H9ruXp8YReBG28OTQyeEkHkHudm7IzMIbP8Ep8bzw");
        return cb;
    }     
%>
<%    
    String objUri = (String) request.getParameter("suri");
    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    Twitter semanticTwitter = (Twitter) semanticObject.createGenericInstance();
    SWBResourceURL actionURL = paramRequest.getActionUrl().setParameter("suri", objUri);
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri);
    Date currentDate;
    long maxTweetID = 0L;
    //TwitterStream twitterStream = new TwitterStreamFactory(configureOAuth(semanticTwitter).build()).getInstance();
    //SocialUserStreamListener tweetsListener = new SocialUserStreamListener(statuses,out);
    //twitterStream.addListener(tweetsListener);//Saving statuses in statuses
    // user() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
    //twitterStream.user();
    renderURL.setMode("doGetStreamUser");
    //request.setAttribute("tweetsListener", tweetsListener);
    session.setAttribute("tweetsListener", tweetsListener);    
%>
<div><a href="#" onclick="appendHtmlAtTop('<%=renderURL.toString()%>','stream'); return false;">Show new status</a></div>

    <div class="swbform">
<%
    try {
            //gets Twitter4j instance with account credentials
            System.out.println("Showing @" + twitterBean.getScreenName() +  "'s home timeline.");
            out.println("<h2>Showing @" + twitterBean.getScreenName() +  "'s home timeline. </h2><br/>");
            out.println("<div id=\"stream\"></div>");
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
    <label id="moreTwitLabel"><a href="#" onclick="appendHtmlAtBottom('<%=renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"")%>','getMoreTweets');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More tweets</a></label>
</div>
</div>
