<%-- 
    Document   : twitterMyTweets
    Created on : 30/05/2013, 04:17:04 PM
    Author     : francisco.jimenez
--%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.social.SocialUserExtAttributes"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.UserGroup"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
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
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="twitterBean" scope="request" type="twitter4j.Twitter"/>
        
<%
    SWBResourceURL actionURL = paramRequest.getActionUrl();
    SWBResourceURL renderURL = paramRequest.getRenderUrl();
    String objUri = request.getParameter("suri");
 
    actionURL.setParameter("suri", objUri);
%>
<div class="timelineTab" style="padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;">
<%
    try {            
            out.println("<div class=\"timelineTab-title\"><p><strong>" + "Mis tweets" + "</strong> @" + twitterBean.getScreenName() + "</p></div>");
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(20);//Gets a number of tweets of timeline. Max value is 100           
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
            
            for (Status status : twitterBean.getUserTimeline(paging)){
                try {
                    postURI = null;
                    PostIn post = PostIn.getPostInbySocialMsgId(model,status.getId()+"");
                    if(post != null){
                        postURI = post.getURI();
                    }
                    doPrintTweet(request, response, paramRequest, status, twitterBean, out,null,USER_TWEETS_TAB, null,user.hasUserGroup(userSuperAdminGrp), userCanRetopicMsg, userCanRespondMsg, userCanRemoveMsg);
                    } catch (Exception te) {
                        System.out.println("Error when getting user tweets " + te);
                    }        
                    i++;
            }
            //System.out.println("\n\nTotal tweets" + i);
        } catch (Exception te) {
            //System.out.println("Se presento un error en User Tweets Tweets!!");
            te.printStackTrace();
        }
%>
</div>