<%-- 
    Document   : twitterDM
    Created on : 13/05/2013, 09:50:57 AM
    Author     : francisco.jimenez
--%>

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
    long maxTweetID = 0L;
    boolean gotError = false;
%>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        resetTabTitle('<%=objUri%>','<%=DIRECT_MESSAGES_TAB%>', 'Direct Messages');
   </script>
</div>

<div class="swbform" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
<%
    try {
            //gets Twitter4j instance with account credentials
            out.println("<div class=\"swbform\">");
            System.out.println( paramRequest.getLocaleString("showing") + " @" + twitterBean.getScreenName() +  "'s Direct Messages.");
            out.println("<div align=\"center\"><h2>" + "@" + twitterBean.getScreenName() + " - " + paramRequest.getLocaleString("directMLabel") + "</h2><br/></div>");
            out.println("<div class=\"bar\" id=\"" + objUri + "/newDirectMessagesAvailable\" dojoType=\"dojox.layout.ContentPane\"></div>");
            out.println("<div id=\"" + objUri + "/directMessagesStream\" dojoType=\"dojox.layout.ContentPane\"></div>");
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(20);//Gets a number of tweets of timeline. Max value is 200           
            int i = 0;
            for (DirectMessage directMsg : twitterBean.getDirectMessages(paging)){                
                maxTweetID = directMsg.getId();
                doPrintDM(request, response, paramRequest, directMsg, twitterBean.getId(), out);
                i++;
            }
            System.out.println("Total DM:" + i);
        } catch (TwitterException te) {
            if(te.getErrorCode() == 88){
                out.println("<div align=\"center\"><h2>YOU HAVE REACHED YOUR RATE LIMIT FOR THIS RESOURCE</h2><br/></div>");
            }else{
                String message = te.getErrorMessage();
                if(message == null){
                    message = "Network problem?";
                }
                out.println("<div align=\"center\"><h2>" + message + "</h2><br/></div>");
            }
            System.out.println("Error displaying DMs" + te.getErrorMessage());
            te.printStackTrace();
            gotError = true;
        }catch(Exception e){
            System.out.println("Error displaying DMs" + e.getMessage());
            e.printStackTrace();
            gotError = true;
        }
            out.println("</div>");
            if(gotError){
                out.println("</div>");
            }else{
%>    
<div id="<%=objUri%>/getMoreDM" dojoType="dojox.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>/moreDMLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreDM").setParameter("maxTweetID", maxTweetID+"")%>','<%=objUri%>/getMoreDM', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("moreDMs")%></a></label>
    </div>
</div>
</div>
<%
   }
%>