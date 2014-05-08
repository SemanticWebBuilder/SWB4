<%-- 
    Document   : twitterMentions
    Created on : 10/05/2013, 01:55:42 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.model.UserGroup"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
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
    long maxTweetID = 0L;
    boolean gotError = false;
%>

<div class="timelineTab" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
<%
    try {
            //gets Twitter4j instance with account credentials
            out.println("<div class=\"swbform\">");
            //System.out.println(paramRequest.getLocaleString("showing") + " @" + twitterBean.getScreenName() +  "'s Mentions.");
            out.println("<div class=\"timelineTab-title\"><p><strong>" + paramRequest.getLocaleString("mentionsLabel") + "</strong> @" + twitterBean.getScreenName() + "</p></div>");

	    //out.println("<div align=\"center\"><h2>" + "@" + twitterBean.getScreenName() + " - " + paramRequest.getLocaleString("mentionsLabel") + "</h2><br/></div>");
            out.println("<div class=\"bar\" id=\"" + objUri + "/newMentionsAvailable\" dojoType=\"dojox.layout.ContentPane\"></div>");
            out.println("<div id=\"" + objUri + "/mentionsStream\" dojoType=\"dojox.layout.ContentPane\"></div>");

            Paging paging = new Paging(); //used to set maxId and count
            paging.count(20);//Gets a number of tweets of timeline. Max value is 200           
            int i = 0;
            org.semanticwb.model.User user = paramRequest.getUser();
            HashMap<String, SemanticProperty> mapa = new HashMap<String, SemanticProperty>();
            Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialUserExtAttributes").listProperties();
            while (list.hasNext()) {
                SemanticProperty sp = list.next();
                mapa.put(sp.getName(),sp);
            }
            boolean userCanRetopicMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanReTopicMsg"))).booleanValue();                
            boolean userCanRespondMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRespondMsg"))).booleanValue();
            boolean userCanRemoveMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRemoveMsg"))).booleanValue();
            SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
            String postURI = null;
            UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
            for (Status status : twitterBean.getMentionsTimeline(paging)){
                postURI = null;
                maxTweetID = status.getId();
                PostIn post = PostIn.getPostInbySocialMsgId(model, maxTweetID+"");
                if(post != null){
                    postURI = post.getURI();
                }
                doPrintTweet(request, response, paramRequest, status, twitterBean, out,null,MENTIONS_TAB, null,user.hasUserGroup(userSuperAdminGrp), userCanRetopicMsg, userCanRespondMsg, userCanRemoveMsg);
                i++;
            }
            //System.out.println("Total Mentions:" + i);
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
            System.out.println("Error displaying mentions:" + te.getErrorMessage());
            te.printStackTrace();
            gotError = true;
        }catch(Exception e){
            System.out.println("Error displaying mentions" + e.getMessage());
            e.printStackTrace();
            gotError = true;
        }
            out.println("</div>");
            if(gotError){
                out.println("</div>");
            }else{
%>    
<div id="<%=objUri%>/getMoreMentions" dojoType="dojox.layout.ContentPane">
    <div align="center" style="margin-bottom: 10px;">
        <label id="<%=objUri%>/moreMentionLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMoreMentions").setParameter("maxTweetID", maxTweetID+"")%>','<%=objUri%>/getMoreMentions', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;"><%=paramRequest.getLocaleString("moreMentions")%></a></label>
    </div>
</div>
</div>
    
<%
            }
%>
