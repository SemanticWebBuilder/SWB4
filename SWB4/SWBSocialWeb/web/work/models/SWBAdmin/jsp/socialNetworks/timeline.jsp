<%-- 
    Document   : timeline
    Created on : 21/03/2013, 01:55:45 PM
    Author     : francisco.jimenez
--%>
<%@page import="org.semanticwb.social.SocialUserExtAttributes"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.social.SocialNetworkUser"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.social.admin.resources.SocialUserStreamListener"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
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
    String objUri = (String) request.getParameter("suri");
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri);
    long maxTweetID = 0L;
%>

<div class="swbform">
<%
    try {
            //gets Twitter4j instance with account credentials
            System.out.println( paramRequest.getLocaleString("showing") + " @" + twitterBean.getScreenName() + " " +  paramRequest.getLocaleString("timeline"));
            out.println("<div align=\"center\"><h2>" + paramRequest.getLocaleString("showing") +" @" + twitterBean.getScreenName() +  " " + paramRequest.getLocaleString("timeline") + "</h2><br/></div>");
            out.println("<div class=\"bar\" id=\"" + objUri + "/newTweetsAvailable\" dojoType=\"dojox.layout.ContentPane\"></div>");
            out.println("<div id=\"" + objUri + "/stream\" dojoType=\"dojox.layout.ContentPane\"></div>");           
            
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(20);//Gets a number of tweets of timeline. Max value is 200           
            int i = 0;
            System.out.println("consumer K:" + twitterBean.getConfiguration().getOAuthConsumerKey());
            System.out.println("consumer S:" + twitterBean.getConfiguration().getOAuthConsumerSecret());
            System.out.println("Access T:" + twitterBean.getConfiguration().getOAuthAccessToken());
            System.out.println("Access S:" + twitterBean.getConfiguration().getOAuthAccessTokenSecret());
            SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());

            out.println("<div dojoType=\"dojox.layout.ContentPane\">");
            String postURI = null;
            org.semanticwb.model.User user = paramRequest.getUser();
            SocialUserExtAttributes socialUserExtAttr = null;
            if(user.isSigned()){
                System.out.println("logeado pero??:");
                socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
            }
            System.out.println("SocialUserExtAttributes:" + socialUserExtAttr);
            for (Status status : twitterBean.getHomeTimeline(paging)){
                postURI = null;
                maxTweetID = status.getId();
                PostIn post = PostIn.getPostInbySocialMsgId(model, status.getId()+"");
                if(post != null){
                    postURI = post.getURI();
                }
                
                doPrintTweet(request, response, paramRequest, status, twitterBean, out, null,HOME_TAB, postURI, socialUserExtAttr);
                i++;
            }
            out.println("</div>");
            System.out.println("Total tweets:" + i);
        }catch (TwitterException te) {
            if(te.getErrorCode() == 88){
                out.println("<div align=\"center\"><h2>RATE LIMIT REACHED</h2><br/></div>");
            }else{
                out.println("<div align=\"center\"><h2>" + te.getErrorMessage() + "</h2><br/></div>");
            }
            System.out.println("Error displaying timeline:" + te.getErrorMessage());
            te.printStackTrace();
        }catch(Exception e){
            System.out.println("Error displaying timeline" + e.getMessage());
            e.printStackTrace();
        }
%>    
<div id="<%=objUri%>/getMoreTweets" dojoType="dojox.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>/moreTwitLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"")%>','<%=objUri%>' + '/getMoreTweets', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("moreTweets")%></a></label>
    </div>
</div>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
    <%        
        String intervalId = (String)session.getAttribute(objUri + "pooling");
        System.out.println("\n\nSESSION VAR FOR CURRENT TAB:" + intervalId);
        
        if(intervalId == null){
    %>
            var interval = setInterval(function(){ postSocialHtmlListeners('<%=renderURL.setMode("newTweets")%>','<%=objUri%>' + '/newTweetsAvailable'); },10000);
            console.log('Value of Interval:' + interval);
            saveInterval('<%=paramRequest.getRenderUrl().setMode("storeInterval").setParameter("suri", objUri)+"&interval="%>' + interval);
    <%
        }else{
    %>
            console.log('Stoping interval for current uri ' + ':' + <%=intervalId%>);
            clearInterval(<%=intervalId%>);
            var interval = setInterval(function(){ postSocialHtmlListeners('<%=renderURL.setMode("newTweets")%>','<%=objUri%>' + '/newTweetsAvailable'); },10000);
            console.log('Value of a NEW Interval:' + interval);
            saveInterval('<%=paramRequest.getRenderUrl().setMode("storeInterval").setParameter("suri", objUri)+"&interval="%>' + interval);
    <%
        }
        //In both cases an 'interval' variable is defined and is assigned to the onClose event
        %>

        //Change the default onClose method of the parent Tab
        var tabId =  '<%=objUri%>' + '/tab';
        var cPane = dijit.byId(tabId);        
        cPane.attr('onClose', function callStopListener(){ clearInterval(interval); stopListener('<%=renderURL.setMode("stopListener")%>'); return true;});
   </script>
</div>
   
</div>

