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
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<script type="text/javascript" id="appends">
    appendHtmlAt = function(url, tagid, location){
     dojo.xhrPost({
        url: url,
        load: function(response)
        {
            var tag=dojo.byId(tagid);
            if(tag){
                var pan=dijit.byId(tagid);
                if(pan && pan.attr)
                {
                    if(location == "bottom"){
                       pan.attr('content', tag.innerHTML + response);
                    }else if(location == "top"){
                       pan.attr('content', response + tag.innerHTML);
                    }
                }else
                {
                    if(location == "bottom"){
                       tag.innerHTML = tag.innerHTML + response;
                    }else if(location == "top"){
                       tag.innerHTML = response + tag.innerHTML;
                    }
                }
            }else {
                //alert("No existe ningun elemento con id " + tagid);
            }
            return response;
        },
        error: function(response)
        {
            if(dojo.byId(tagid)) {
                dojo.byId(tagid).innerHTML = "<p>Ocurrio un error con respuesta:<br />" + response + "</p>";
            }else {
                //alert("No existe ningun elemento con id " + tagid);
            }
            return response;
        },
        handleAs: "text"
    });
    }
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
    //Twitter semanticTwitter = (Twitter) semanticObject.createGenericInstance();
    //SWBResourceURL actionURL = paramRequest.getActionUrl().setParameter("suri", objUri);
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri);
    //Date currentDate;
    long maxTweetID = 0L;
    //TwitterStream twitterStream = new TwitterStreamFactory(configureOAuth(semanticTwitter).build()).getInstance();
    //SocialUserStreamListener tweetsListener = new SocialUserStreamListener(statuses,out);
    //twitterStream.addListener(tweetsListener);//Saving statuses in statuses
    // user() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
    //twitterStream.user();    
    //request.setAttribute("tweetsListener", tweetsListener);
    //session.setAttribute("tweetsListener", tweetsListener);
    
%>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
      setInterval(function(){ postHtml('<%=renderURL.setMode("newTweets")%>','newTweetsAvailable'); },5000);
      eval(document.getElementById("appends").innerHTML);
   </script>
</div>
   
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
    </style>

    <div class="swbform">
<%
    try {
            //gets Twitter4j instance with account credentials
            System.out.println("Showing @" + twitterBean.getScreenName() +  "'s home timeline.");
            out.println("<div align=\"center\"><h2>Showing @" + twitterBean.getScreenName() +  "'s home timeline. </h2><br/></div>");
            out.println("<div class=\"bar\" id=\"newTweetsAvailable\" dojoType=\"dojox.layout.ContentPane\"></div>");
            out.println("<div id=\"stream\"></div>");
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(20);//Gets a number of tweets of timeline. Max value is 200           
            int i = 0;
            for (Status status : twitterBean.getHomeTimeline(paging)){
                maxTweetID = status.getId();
                doPrintTweet(request, response, paramRequest, status, twitterBean.getId(), out);
                i++;
            }
            System.out.println("Total tweets:" + i);
        } catch (Exception te) {
            System.out.println("Se presento un error!!");
            te.printStackTrace();
        }
%>    
<div align="center" id="getMoreTweets" dojoType="dijit.layout.ContentPane">
    <label id="moreTwitLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"")%>','getMoreTweets', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More tweets</a></label>
</div>
</div>
