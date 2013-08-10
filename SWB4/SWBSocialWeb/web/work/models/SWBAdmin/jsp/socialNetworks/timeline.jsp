<%-- 
    Document   : timeline
    Created on : 21/03/2013, 01:55:45 PM
    Author     : francisco.jimenez
--%>
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


<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        var interval = setInterval(function(){ postSocialHtml('<%=renderURL.setMode("newTweets")%>','<%=objUri%>' + '/newTweetsAvailable'); },10000);
        console.log('Value of Interval:' + interval);

        //Change the default onClose method of the parent Tab
        var tabId =  '<%=objUri%>' + '/tab';
        var cPane = dijit.byId(tabId);
        cPane.attr('onClose', function callStopListener(){ clearInterval(interval); stopListener('<%=renderURL.setMode("stopListener")%>'); return true;});
   </script>
</div>   

<div class="swbform">
<%
    try {
            //gets Twitter4j instance with account credentials
            System.out.println("Showing @" + twitterBean.getScreenName() +  "'s home timeline.");
            out.println("<div align=\"center\"><h2>Showing Showing @" + twitterBean.getScreenName() +  "'s home timeline. </h2><br/></div>");
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
            SocialNetworkUser socialNetUser = null;// = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(""+status.getUser().getId(), socialNetwork, model);
            out.println("<div dojoType=\"dojox.layout.ContentPane\">");
            String creatorId="";
            String postURI = null;
            for (Status status : twitterBean.getHomeTimeline(paging)){
                postURI = null;
                maxTweetID = status.getId();                                    
                //doPrintTweet(request, response, paramRequest, status, twitterBean, out, recoverConversations(maxTweetID, twitterBean));                
                /*creatorId = status.getUser().getId() + "";
                socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(creatorId, socialNetwork, model);
                if(socialNetUser != null){//User exists and might have posts or not
                    Iterator<PostIn> userPosts = PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetUser, model);
                    while(userPosts.hasNext()){
                        PostIn post = userPosts.next();
                        if(post.getSocialNetMsgId().equals(status.getId()+"")){//Post have been saved before
                            System.out.println( post.getSocialNetMsgId() +" = " + status.getId());
                            postURI = post.getURI();
                            break;
                        }
                    }
                }*/
                PostIn post = PostIn.getPostInbySocialMsgId(model, status.getId()+"");
                if(post != null){
                    postURI = post.getURI();
                }
                
                doPrintTweet(request, response, paramRequest, status, twitterBean, out, null,HOME_TAB, postURI);
                i++;
            }
            out.println("</div>");
            System.out.println("Total tweets:" + i);
        } catch (Exception te) {
            System.out.println("Se presento un error al mostrar el Timeline!!");
            te.printStackTrace();
        }
%>    
<div align="center" id="<%=objUri%>/getMoreTweets" dojoType="dojox.layout.ContentPane">
    <label id="<%=objUri%>/moreTwitLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"")%>','<%=objUri%>' + '/getMoreTweets', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More tweets</a></label>
</div>
</div>
