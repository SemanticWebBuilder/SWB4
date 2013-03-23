<%-- 
    Document   : timeline
    Created on : 21/03/2013, 01:55:45 PM
    Author     : francisco.jimenez
--%>
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
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<%!
private ConfigurationBuilder configureOAuth(org.semanticwb.social.Twitter tw){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(tw.getAppKey())
          .setOAuthConsumerSecret(tw.getSecretKey())
          .setOAuthAccessToken(tw.getAccessToken())
          .setOAuthAccessTokenSecret(tw.getAccessTokenSecret());        
        return cb;
    }

private void getHome(Twitter twit){
    try{    
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();
            twitter4j.User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getHomeTimeline();            
            System.out.println("\n\n\t\tShowing @" + user.getScreenName() + "'s home timeline.\n\n");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        }catch(Exception e){
        System.out.println("ERRRRROR!!!!");
        e.printStackTrace();
    }
}

%>
<%    
    String objUri = (String) request.getParameter("suri");
    System.out.println("SURI recibida: " + objUri);
    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    Twitter twit = (Twitter) semanticObject.createGenericInstance();
    SWBResourceURL action = paramRequest.getActionUrl().setParameter("suri", objUri);   
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
            // gets Twitter instance with default credentials
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();
            System.out.println("Showing @" + twitter.getScreenName() +  "'s home timeline.");
            out.println("<h2>Showing @" + twitter.getScreenName() +  "'s home timeline. </h2><br/>");
            Paging p = new Paging();
            p.count(10);
            int i = 0;
            for (Status status : twitter.getHomeTimeline(p)) {
                //System.out.println("STATUS: " + status.getText() + "\n");
                out.println("<fieldset>");
                out.println("<table style=\"width: 100%; border: 0px\">");
                out.println("<tr>");
                out.println("   <td colspan=\"2\">");
                out.println("   <b>" + status.getUser().getName() + "<b> " + status.getUser().getScreenName());
                out.println("   </td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("   <td>");
                out.println("       <img src=\"" + status.getUser().getProfileImageURL() + "\"/>");
                out.println("   </td>");
                out.println("   <td>");                
                String text = status.getText();
                //out.println(        text + "<br></br>"); 
                URLEntity urlEnts[] = status.getURLEntities();
                if(urlEnts!=null && urlEnts.length >0){
                    //System.out.println("hay url Entities!");
                    for(URLEntity urlEnt: urlEnts){       
                        //System.out.println("url:" + urlEnt.getDisplayURL() + " - " + urlEnt.getURL());                        
                        text=text.replace(urlEnt.getURL(), "<a href=\"" + urlEnt.getURL() +  "\">" + urlEnt.getURL() +"</a>");
                    }
                }
                
                MediaEntity mediaEnts[] = status.getMediaEntities();
                if(mediaEnts!=null && mediaEnts.length >0){
                    //System.out.println("hay Media Entities!");
                    for(MediaEntity mediaEnt: mediaEnts){       
                        //System.out.println("url:" + mediaEnt.getDisplayURL() + " - " + mediaEnt.getURL());                        
                        text=text.replace(mediaEnt.getURL(), "<a href=\"" + mediaEnt.getURL() +  "\">" + mediaEnt.getURL() +"</a>");
                    }
                }

                HashtagEntity htEnts[] = status.getHashtagEntities();                
                if(htEnts!=null && htEnts.length >0){
                    //System.out.println("hay HT Entities!");
                    for(HashtagEntity htEnt: htEnts){       
                        //System.out.println("url:" + htEnt.getText() + " - " + htEnt.getText());                        
                        text=text.replace("#" + htEnt.getText(), "<a href=\"https://twitter.com/search?q=%23" + htEnt.getText() +  "&src=hash\">#"+ htEnt.getText() +"</a>");
                    }
                }
                
                UserMentionEntity usrEnts[] = status.getUserMentionEntities();                
                if(usrEnts!=null && usrEnts.length >0){
                    //System.out.println("hay User Entities!");
                    for(UserMentionEntity usrEnt: usrEnts){
                        //System.out.println("User" + usrEnt.getScreenName() + " - " + usrEnt.getScreenName());
                        text=text.replace("@" + usrEnt.getScreenName(), "<a href=\"https://twitter.com/" + usrEnt.getScreenName() +  "\">@"+ usrEnt.getScreenName() +"</a>");
                    }
                }
                
                out.println(        text); 
                out.println("   </td>");                
                out.println("</tr>");
                out.println("<tr>");
                out.println("   <td colspan=\"2\">");
                //out.println("<a href=\"#\"  onclick=\"addNewTab('" + res.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + res.getDisplayTitle(usr.getLanguage()) + "');return false;\" >" + res.getDisplayTitle(usr.getLanguage()) + "</a>");                
                try{
                out.println("   Created: "  + status.getCreatedAt());
                out.println("- - Retweeted: <b>" + status.getRetweetCount() + "<b> times ");
                action.setAction("reply");
                out.println("<a href=\"\"  onclick=\"submitUrl('" + action.setParameter("id", status.getId()+"").setParameter("replyText", "@Leon_Krauze answer!").toString() + "',this);return false;" +"\">Reply</a>");
                if(status.isRetweetedByMe()){
                    action.setAction("undoRT");
                    out.println("<a href=\"\"  onclick=\"submitUrl('" + action.setParameter("id", status.getId()+"").toString() + "',this);return false;" +"\">Undo Retweet</a>");
                }else{
                    action.setAction("RT");
                    out.println("<a href=\"\"  onclick=\"submitUrl('" + action.setParameter("id", status.getId()+"").toString() + "',this);return false;" +"\">Retweet</a>");
                }
                               }catch(Exception e){System.out.println("ERROR");}
                
                out.println("<a href=\"\"  onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT) + "','title');return false;" +"\">Dialog</a>");
                out.println("   </td>");
                out.println("</tr>");          
                out.println("</table>");
                out.println("</fieldset>");
                i++;
            }
            System.out.println("Total tweets:" + i);
        } catch (Exception te) {
            System.out.println("Se presento un error!!");
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());            
        }
%>    
</div>
