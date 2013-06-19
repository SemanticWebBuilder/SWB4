<%-- 
    Document   : twitterMyTweets
    Created on : 30/05/2013, 04:17:04 PM
    Author     : francisco.jimenez
--%>

<%@page import="static org.semanticwb.social.admin.resources.Timeline.lookForEntities"%>
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
    SWBResourceURL actionURL = paramRequest.getActionUrl();
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
    String suri = request.getParameter("suri");
 
    actionURL.setParameter("suri", suri);
%>
<div class="swbform">
<%
    try {
            System.out.println("Showing @" + twitterBean.getScreenName() +  "'s Tweets.");
            out.println("<div align=\"center\"><h2>Showing @" + twitterBean.getScreenName() +  "'s Tweets. </h2><br/></div>");
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(20);//Gets a number of tweets of timeline. Max value is 100           
            int i = 0;
            
            for (Status status : twitterBean.getUserTimeline(paging)){
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
                        long minutes = (long)(new Date().getTime()/60000) - (status.getCreatedAt().getTime()/60000);
                        out.println("Created:<b>" + (int)minutes + "</b> minutes ago - - Retweeted: <b>" + status.getRetweetCount() + "</b> times ");
                        out.println("   </td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</fieldset>");             
                } catch (Exception te) {
                    System.out.println("Error when getting user tweets " + te);
                }        
                i++;
            }
            System.out.println("\n\nTotal tweets" + i);
        } catch (Exception te) {
            System.out.println("Se presento un error en User Tweets Tweets!!");
            te.printStackTrace();
        }
%>
</div>