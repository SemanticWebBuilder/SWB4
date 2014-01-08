<%-- 
    Document   : twitterUserProfile
    Created on : 14/05/2013, 12:00:27 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.lookForEntities"%>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.twitterHumanFriendlyDate"%>
<%@page import="org.semanticwb.portal.api.SWBResourceException"%>
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
    String targetUser = (String)request.getParameter("targetUser");
    User twitterUser = twitterBean.showUser(targetUser);
    Relationship relationship = twitterBean.showFriendship(twitterBean.getScreenName(), targetUser);
    boolean isFollowedByMe = relationship.isTargetFollowedBySource();
    String relationshipStatus="";
    SWBResourceURL actionURL = paramRequest.getActionUrl();
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
    String suri = request.getParameter("suri");
    Twitter twitter = (Twitter)SemanticObject.createSemanticObject(suri).createGenericInstance();
    SWBModel model=WebSite.ClassMgr.getWebSite(twitter.getSemanticObject().getModel().getName());
    SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);      
    
    renderURL.setParameter("suri", suri);
    actionURL.setParameter("targetUser", targetUser);
    actionURL.setParameter("suri", suri);
    
    if(isFollowedByMe){
        relationshipStatus = paramRequest.getLocaleString("unfollow");
        actionURL.setAction("undoFollow");
    }else{
        relationshipStatus = paramRequest.getLocaleString("follow");
        actionURL.setAction("doFollow");
    }
%>
<div class="swbform" style="width: 500px">
    <div align="center"><img src="<%=twitterUser.getBiggerProfileImageURL()%>" width="73" height="73"/></div>
    <table style="width: 100%">
        <tr>
            <td align="center" colspan="4">
                <%=twitterUser.getDescription()%>
            </td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td align="center">
                Tweets: <b><%=twitterUser.getStatusesCount()%></b>
            </td>
            <td align="center">
                Followers: <b><%=twitterUser.getFollowersCount()%></b>
            </td>
            <td align="center">
                Following: <b><%=twitterUser.getFriendsCount()%></b>
            </td>          
            <td align="center">
                Tweets: <b><%=twitterUser.getStatusesCount()%></b>
            </td>
        </tr>
        
        <tr>
            <td align="center" colspan="4">
                <div class="timelineresume">
                    <span class="inline" id="<%=targetUser + "/relStat"%>" dojoType="dijit.layout.ContentPane">
                    <%if(!targetUser.equals(twitterBean.getScreenName())){//Only if the user is not me%>
                         <b><a class="clasifica" href='#' onclick="try{dojo.byId(this.parentNode.parentNode).innerHTML = '<img src=<%=SWBPlatform.getContextPath()%>/swbadmin/icons/loading.gif>';}catch(noe){} postHtml('<%=actionURL%>','<%=targetUser + "/relStat"%>'); return false;"><%=relationshipStatus%></a></b>
                    <%}%>                    
                    </span>
                    <span class="inline">
                        <a class="clasifica" href="#" onclick="hideDialog(); showDialog('<%=paramRequest.getRenderUrl().setMode("createTweet").setParameter("suri",defaultSocialTopic.getURI()).setParameter("netSuri",suri).setParameter("username",twitterUser.getScreenName())%>','Enviar mensaje a @<%=twitterUser.getScreenName()%>');return false;">Enviar Mensaje</a>
                    </span>
                    <span class="inline">
                        <a class="clasifica" href="#" onclick="hideDialog(); showDialog('<%=paramRequest.getRenderUrl().setMode("createNewDM").setParameter("suri",suri).setParameter("userId", twitterUser.getId()+"")%>','DM to @<%=twitterUser.getScreenName()%>');return false;">Enviar Mensaje Directo</a>
                    </span> 
                </div>
            </td>            
        </tr>        
    </table>
<%
    try {
            out.println("<div align=\"center\"><h2>@" + targetUser +  " Timeline. </h2></div>");
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(3);//Gets a number of tweets of timeline. Max value is 100           
            int i = 0;
            
            for (Status status : twitterBean.getUserTimeline(targetUser, paging)){
                String objUri = request.getParameter("suri");
              
                try {
                        out.println("<fieldset>");
                        out.println("<table border: 0px\">");
                        out.println("<tr>");
                        out.println("   <td colspan=\"2\">");
                        out.println("   " + status.getUser().getName() + " <b>" + status.getUser().getScreenName()+ "</b>");
                        out.println("   </td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("   <td  width=\"10%\">");
                        out.println("       <img src=\"" + status.getUser().getProfileImageURL() + "\"/>");
                        out.println("   </td>");
                        out.println("   <td width=\"90%\">");                                        
                        String statusText = lookForEntities(status.getText(), renderURL, status.getURLEntities(), status.getMediaEntities(), status.getHashtagEntities(),status.getUserMentionEntities()); 

                        out.println(        statusText);
                        out.println("   </td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("   <td colspan=\"2\">");
                        out.println("<b>" + twitterHumanFriendlyDate(status.getCreatedAt(), paramRequest) + "</b>");                    
                        out.println("   </td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</fieldset>");             
                } catch (Exception te) {
                    System.out.println("Error when getting timeline: " + te);
                }        
                i++;
            }
        } catch (Exception te) {
            System.out.println("Se presento un error en User Profile!!");            
            te.printStackTrace();
        }
%>
</div>