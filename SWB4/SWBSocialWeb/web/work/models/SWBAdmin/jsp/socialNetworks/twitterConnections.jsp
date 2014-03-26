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
    boolean gotError = false;
%>

<div class="timelineTab" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
<%
    Twitter twitter = (Twitter)SemanticObject.createSemanticObject(objUri).createGenericInstance();
    SWBModel model=WebSite.ClassMgr.getWebSite(twitter.getSemanticObject().getModel().getName());
    SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
    try {
	    out.println("<div class=\"timelineTab-title\"><p><strong>" + " Personas que sigo" + "</strong> @" + twitterBean.getScreenName() + "</p></div>");

            //out.println("<div align=\"center\"><h2>" + "@" + twitterBean.getScreenName() + " "  + "</br> Personas que sigo" + "</h2><br/></div>");
            
            PagableResponseList<User> friends;
            //do {
            friends = twitterBean.getFriendsList(twitterBean.getId(), friendsCursor);
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
        <span class="inline">
            <a class="clasifica" href="#" onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("createTweet").setParameter("suri",defaultSocialTopic.getURI()).setParameter("netSuri",objUri).setParameter("username",user.getScreenName())%>','Enviar mensaje a @<%=user.getScreenName()%>');return false;">Enviar Mensaje</a>
        </span>
        <span class="inline">
            <a class="clasifica" href="#" onclick="showDialog('<%=paramRequest.getRenderUrl().setMode("createNewDM").setParameter("suri",objUri).setParameter("userId", user.getId()+"")%>','DM to @<%=user.getScreenName()%>');return false;">Enviar Mensaje Directo</a>
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
                String message = te.getErrorMessage();
                if(message == null){
                    message = "Network problem?";
                }
                out.println("<div align=\"center\"><h2>" + message + "</h2><br/></div>");
            }
            System.out.println("Error displaying friends" + te.getErrorMessage());
            te.printStackTrace();
            gotError = true;
        }catch(Exception e){
            System.out.println("Error displaying friends" + e.getMessage());
            e.printStackTrace();
            gotError = true;
        }
            //out.println("</div>");
    
        if(gotError){
            out.println("</div>");
        }else{
%>    
<div id="<%=objUri%>/getMoreFriends" dojoType="dojox.layout.ContentPane">
    <div align="center" style="margin-bottom: 10px;">
        <label id="<%=objUri%>/moreFriendsLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreFriends").setParameter("friendsCursor", friendsCursor+"")%>','<%=objUri%>/getMoreFriends', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("moreFriends")%></a></label>
    </div>
</div>
</div>
    
<%
        }   
%>
