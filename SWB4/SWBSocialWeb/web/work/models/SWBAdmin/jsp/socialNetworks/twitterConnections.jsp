<%-- 
    Document   : twitterConnections
    Created on : Jan 5, 2014, 5:31:06 PM
    Author     : pacone
--%>

<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.social.SocialUserExtAttributes"%>
<%@page import="org.semanticwb.social.SocialUserExtAttributes"%>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>
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

<%    
    String objUri = (String) request.getParameter("suri");
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri);
    long friendsCursor = -1;
    long followersCursor = -1;
    
%>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        resetTabTitle('<%=objUri%>','<%=CONNECTIONS_TAB%>', 'Connections' );
   </script>
</div>

<div>
    <div style="width:50%;float:left;display:inline-block;">
<div class="swbform" id="paco">
<%
    try {
            out.println("<div class=\"swbform\">");
            out.println("<div align=\"center\"><h2>" + "@" + twitterBean.getScreenName() + " "  + "</br> Personas que sigo" + "</h2><br/></div>");
            
            PagableResponseList<User> friends;
            //do {
            friends = twitterBean.getFriendsList(twitterBean.getId(), friendsCursor);
            //out.println("-----" + "/nCursor:" + friends.getNextCursor());
            Twitter twitter = (Twitter)SemanticObject.createSemanticObject(objUri).createGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(twitter.getSemanticObject().getModel().getName());
            SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
            for(int i = 0; i < friends.size(); i++){
                User user = friends.get(i);
                //out.println("THE FRIEND:" + "<img src=\"" + user.getBiggerProfileImageURL() + "\">" + friends.get(i).getScreenName() + "</br>");                
                
%>
<div class="timeline timelinetweeter">
    <p class="tweeter">
        <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName())%>','<%= "@" + user.getScreenName() + " - " + user.getName()%>'); return false;" href="#"><%= "@" + user.getScreenName()%></a>  <%=user.getName()%>
    </p>
    <p class="tweet">
        <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName())%>','<%= "@" + user.getScreenName() + " - " + user.getName()%>');return false;" href="#">
            <img width="48" height="48" src="<%=user.getBiggerProfileImageURL()%>"/></a>
        <%=user.getDescription()%>
    </p>
    <div class="timelineresume">
        <span class="inline" id="sendTweet/<%=user.getId()%>" dojoType="dojox.layout.ContentPane">
            <a class="clasifica" href="#" onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("createTweet").setParameter("suri",defaultSocialTopic.getURI()).setParameter("netSuri",objUri).setParameter("username",user.getScreenName())%>','Enviar mensaje a @<%=user.getScreenName()%>');return false;">Enviar Mensaje</a>
        </span>
        <span class="inline" id="sendDM/<%=user.getId()%>" dojoType="dojox.layout.ContentPane">
            <a class="clasifica" href="#" onclick="">Enviar Mensaje Directo</a>
        </span> 

    </div>
</div>
<%
            }
            friendsCursor = friends.getNextCursor();
        }catch (TwitterException te) {
            if(te.getErrorCode() == 88){
                out.println("<div align=\"center\"><h2>YOU HAVE REACHED YOUR RATE LIMIT FOR THIS RESOURCE</h2><br/></div>");
            }else{
                out.println("<div align=\"center\"><h2>" + te.getErrorMessage() + "</h2><br/></div>");
            }
            System.out.println("Error displaying friends" + te.getErrorMessage());
            te.printStackTrace();
        }catch(Exception e){
            System.out.println("Error displaying friends" + e.getMessage());
            e.printStackTrace();
        }
            out.println("</div>");
%>    
<div id="<%=objUri%>/getMoreFriends" dojoType="dojox.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>/moreFriendsLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreFriends").setParameter("friendsCursor", friendsCursor+"")%>','<%=objUri%>/getMoreFriends', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("moreFriends")%></a></label>
    </div>
</div>
</div>
    
    </div>
    <div style="margin-left:50%;">
<div class="swbform">
    </br>
<%
    try {
            out.println("<div class=\"swbform\">");
            out.println("<div align=\"center\"><h2>" + "@" + twitterBean.getScreenName() + " "  + "</br> Personas que me siguen" + "</h2><br/></div>");
            
            PagableResponseList<User> followers;
            //do {
            followers = twitterBean.getFollowersList(twitterBean.getId(), followersCursor);
            //out.println("-----" + "/nCursor:" + friends.getNextCursor());
            for(int i = 0; i < followers.size(); i++){
                User user = followers.get(i);
                //out.println("THE FOLLOWER:" + "<img src=\"" + user.getBiggerProfileImageURL() + "\">" + followers.get(i).getScreenName() + "</br>");
%>
                <div class="timeline timelinetweeter">
                    <p class="tweeter">
                        <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName())%>','<%= "@" + user.getScreenName() + " - " + user.getName()%>'); return false;" href="#"><%= "@" + user.getScreenName()%></a>  <%=user.getName()%>
                    </p>
                    <p class="tweet">
                        <a onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName())%>','<%= "@" + user.getScreenName() + " - " + user.getName()%>');return false;" href="#">
                            <img width="48" height="48" src="<%=user.getBiggerProfileImageURL()%>"></a>
                        <%=user.getDescription()%>
                    </p>
                </div>

<%
            }
            followersCursor = followers.getNextCursor();
        }catch (TwitterException te) {
            if(te.getErrorCode() == 88){
                out.println("<div align=\"center\"><h2>YOU HAVE REACHED YOUR RATE LIMIT FOR THIS RESOURCE</h2><br/></div>");
            }else{
                out.println("<div align=\"center\"><h2>" + te.getErrorMessage() + "</h2><br/></div>");
            }
            System.out.println("Error displaying followers" + te.getErrorMessage());
            te.printStackTrace();
        }catch(Exception e){
            System.out.println("Error displaying followers" + e.getMessage());
            e.printStackTrace();
        }
            out.println("</div>");
%>    
<div id="<%=objUri%>/getMoreFollowers" dojoType="dojox.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>/moreFollowersLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreFollowers").setParameter("followersCursor", followersCursor+"")%>','<%=objUri%>/getMoreFollowers', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("moreFollowers")%></a></label>
    </div>
</div>
</div>    
        
    </div>
</div>