<%-- 
    Document   : timeline
    Created on : 21/03/2013, 01:55:45 PM
    Author     : francisco.jimenez
--%>
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
<%@page import="twitter4j.FilterQuery"%>
<%@page import="twitter4j.Query"%>
<%@page import="twitter4j.Status"%>
<%@page import="twitter4j.StatusListener"%>
<%@page import="twitter4j.StatusUpdate"%>
<%@page import="twitter4j.TwitterException"%>
<%@page import="twitter4j.TwitterFactory"%>
<%@page import="twitter4j.TwitterStream"%>
<%@page import="twitter4j.TwitterStreamFactory"%>
<%@page import="twitter4j.auth.AccessToken"%>
<%@page import="twitter4j.auth.RequestToken"%>
<%@page import="twitter4j.conf.Configuration"%>
<%@page import="twitter4j.conf.ConfigurationBuilder"%>
<%@page import="twitter4j.URLEntity"%>
<%@page import="twitter4j.MediaEntity"%>
<%@page import="twitter4j.HashtagEntity"%>
<%@page import="twitter4j.UserMentionEntity"%>
<%@page import="twitter4j.Paging"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="twitterBean" scope="request" type="twitter4j.Twitter"/>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%    
    String objUri = (String) request.getParameter("suri");
    //SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    //Twitter semanticTwitter = (Twitter) semanticObject.createGenericInstance();
    SWBResourceURL actionURL = paramRequest.getActionUrl().setParameter("suri", objUri);
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri);
    Date currentDate;
    long maxTweetID = 0L;
%>
    <div class="swbform">
        <script type="text/javascript">
             /*$(window).scroll(function() {
                 alert('mov');
                if($(window).scrollTop() + $(window).height() == $(document).height()) {
                    alert("bottom!");
                }
             });
             var scrollingDetector = (function() {
    var max = calculateScrollHeight();

    return function(){
        if (max < window.pageYOffset) {   
            max = calculateScrollHeight();
            alert('ok');
        }
    }
    function calculateScrollHeight(){
        return (document.documentElement.scrollHeight - document.documentElement.clientHeight) - 80; 
    }
})();   

setInterval(scrollingDetector, 500);
var objDiv = document.getElementById("swbform");
objDiv.scrollTop = objDiv.scrollHeight; 
*/
        </script>
<%
    try {
            //getHome(twit);
            //gets Twitter4j instance with account credentials
            //twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
            System.out.println("Showing @" + twitterBean.getScreenName() +  "'s home timeline.");
            out.println("<h2>Showing @" + twitterBean.getScreenName() +  "'s home timeline. </h2><br/>");
            Paging paging = new Paging(); //used to set maxId and count
            paging.count(25);            
            int i = 0;
            for (Status status : twitterBean.getHomeTimeline(paging)){
                maxTweetID = status.getId();                
%>                
                <fieldset>
                <table style="width: 100%; border: 0px">
                <tr>
                   <td colspan="2">
                       <%=status.getUser().getName()%> <b><%=status.getUser().getScreenName()%></b>
                   </td>
                </tr>
                <tr>
                   <td width="10%">
                       <img src="<%=status.getUser().getProfileImageURL()%>"/>
                   </td>
<%                    
                String statusText = status.getText(); // It's necessary to include URL for media, hash tags and usernames
                
                URLEntity urlEnts[] = status.getURLEntities();
                if(urlEnts!=null && urlEnts.length >0){
                    for(URLEntity urlEnt: urlEnts){
                        statusText=statusText.replace(urlEnt.getURL(), "<a target=\"_blank\" href=\"" + urlEnt.getURL() +  "\">" + urlEnt.getURL() +"</a>");
                    }
                }
                
                MediaEntity mediaEnts[] = status.getMediaEntities();
                if(mediaEnts!=null && mediaEnts.length >0){
                    for(MediaEntity mediaEnt: mediaEnts){
                        statusText=statusText.replace(mediaEnt.getURL(), "<a target=\"_blank\" href=\"" + mediaEnt.getURL() +  "\">" + mediaEnt.getURL() +"</a>");
                    }
                }

                HashtagEntity htEnts[] = status.getHashtagEntities(); //Probably it would be better to look for HTs with regex
                if(htEnts!=null && htEnts.length >0){
                    for(HashtagEntity htEnt: htEnts){
                        statusText=statusText.replace("#" + htEnt.getText(), "<a target=\"_blank\" href=\"https://twitter.com/search?q=%23" + htEnt.getText() +  "&src=hash\">#"+ htEnt.getText() +"</a>");
                    }
                }
                
                UserMentionEntity usrEnts[] = status.getUserMentionEntities(); //Probably it would be better to look for User Mentions with regex 
                if(usrEnts!=null && usrEnts.length >0){
                    for(UserMentionEntity usrEnt: usrEnts){
                        statusText=statusText.replace("@" + usrEnt.getScreenName(), "<a target=\"_blank\" href=\"https://twitter.com/" + usrEnt.getScreenName() +  "\">@"+ usrEnt.getScreenName() +"</a>");
                    }
                }
%>
                    <td width="90%"> 
                        <%=statusText%>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
<%                
                try{
%>
                        <div id="<%=status.getId()%>" dojoType="dijit.layout.ContentPane">
                            Created:<b><%=(int)((new Date().getTime()/60000) - (status.getCreatedAt().getTime()/60000))%></b> minutes ago
                        - - Retweeted: <b><%=status.getRetweetCount()%></b> times
                        <a href="" onclick="showDialog('<%=renderURL.setMode("replyTweet").setParameter("id", status.getId()+"").setParameter("userName", "@" + status.getUser().getScreenName())%> ','Reply to @<%=status.getUser().getScreenName()%>');return false;">Reply</a>
<%                    
                    if(status.isRetweetedByMe()){
                        actionURL.setAction("undoRT");
                        //out.println("<a href=\"\"  onclick=\"submitUrl('" + action.setParameter("id", status.getId()+"").toString() + "',this);return false;" +"\">Undo Retweet</a>");
                        out.println("Undo Retweet");
                    }else{
                        if(status.getUser().getId() != twitterBean.getId()){
                            actionURL.setAction("doRT");
                            //renderURL.setMode("RT");
                            //out.println("<a href=\"\"  onclick=\"submitUrl('" + action.setParameter("id", status.getId()+"").toString() + "',this);return false;" +"\">Retweet</a>");
                            out.println("<a href=\"#\"  onclick=\"postHtml('" + actionURL.setParameter("id", status.getId()+"") + "','" + status.getId() + "'); return false;" +"\">Retweet</a>");
                        }
                    }
                }catch(Exception e){
                    System.out.println("ERROR");
                }
%>                
                    </div>
                    </td>
                </tr>
                </table>
                </fieldset>
<%
                i++;
            }
            System.out.println("Total tweets:" + i);
        } catch (Exception te) {
            System.out.println("Se presento un error!!");
            te.printStackTrace();
        }
%>    
<div align="center" id="getMoreTweets" dojoType="dijit.layout.ContentPane">
    <label id="moreTwitLabel"><a href="#" onclick="appendHtmlTmp('<%=renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"")%>','getMoreTweets');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;">More tweets</a></label>
</div>
</div>
