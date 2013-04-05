/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Twitter;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import twitter4j.conf.ConfigurationBuilder;
/**
 *
 * @author francisco.jimenez
 */
public class Timeline extends GenericResource{
    private static Logger log = SWBUtils.getLogger(Timeline.class);
    private twitter4j.Twitter twitter;
    
    private ConfigurationBuilder configureOAuth(org.semanticwb.social.Twitter tw){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(tw.getAppKey())
          .setOAuthConsumerSecret(tw.getSecretKey())
          .setOAuthAccessToken(tw.getAccessToken())
          .setOAuthAccessTokenSecret(tw.getAccessTokenSecret());        
        return cb;
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {       
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Twitter semanticTwitter = (Twitter) semanticObject.createGenericInstance();
        twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
        
        //Start the listener
        TwitterStream twitterStream = null;             //The stream
        SocialUserStreamListener tweetsListener = null; //The listener reads tweets received in the home timeline
        HttpSession session = request.getSession(true);
        //for(Enumeration e = session.getAttributeNames(); e.hasMoreElements(); ){
        //        String attName = (String)e.nextElement();
        //        System.out.println(attName  + ":" +session.getAttribute(attName));
        //}
        if((session.getAttribute("twitterStream") == null && session.getAttribute("tweetsListener") == null) ||
                ((SocialUserStreamListener)session.getAttribute("tweetsListener")).streamActive == false){ //If no stream found for the current session, create one.
            twitterStream = new TwitterStreamFactory(configureOAuth(semanticTwitter).build()).getInstance();
            tweetsListener = new SocialUserStreamListener(twitterStream);
            twitterStream.addListener(tweetsListener);//Saving statuses in local variable of the listener
            twitterStream.user();//This method internally starts a thread
            session.setAttribute("tweetsListener", tweetsListener);
            session.setAttribute("twitterStream", twitterStream);
            System.out.println("Listener started!");
        }else{
             if(session.getAttribute("tweetsListener") != null){//If the tab is refreshed, clean all 'new' statuses in ArrayList
                 ((SocialUserStreamListener)session.getAttribute("tweetsListener")).socialStatus.clear();
             }
        }
        
        String jspResponse = SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/timeline.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("twitterBean", twitter);            
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }                
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {        
        String action = response.getAction();
        String objUri = request.getParameter("suri");
        Long id = Long.parseLong(request.getParameter("id"));

        if(action.equals("doRT")){ //Makes the retweet
            try {
                Status st = twitter.retweetStatus(id); // El id de st Deberá ser guardado para poder deshacer el RT
                response.setRenderParameter("id", id+"");
                response.setRenderParameter("suri", objUri);
                response.setRenderParameter("retweeted", "ok");
                System.out.println("Retwit!");
                response.setMode("retweetSent"); //show RT Message and update div
            } catch (TwitterException ex) {
                log.error("Error when trying to retweet ", ex);
            }
        }else if(action.equals("undoRT")){
            try {
                System.out.println("Undo Retwit!");
                twitter.destroyStatus(id); //Va a ser necesario guardar los ID de los RT's hechos
                /*
                List<Status> retweets = twitter.getRetweetsOfMe();
                for(Status rt : retweets){//Va a ser necesario guardar los ID de los RT's hechos.
                    System.out.println("My Rts, ID:" + rt.getId() + " txt:" + rt.getText() );
                    if(id == rt.getId()){
                        System.out.println("RT Found!");
                        twitter.destroyStatus(id);
                        
                    }
                }*/
            } catch (TwitterException ex) {
                log.error("Error when trying to undo retweet ", ex);
            }
        }else if(action.equals("sendReply")){
            try {
                String answer = request.getParameter("replyText");
                System.out.println("Answer Text:" + answer);
                twitter.updateStatus(new StatusUpdate(answer).inReplyToStatusId(id));
                response.setRenderParameter("repliedTweet", "ok");
                response.setMode("tweetSent");                
            } catch (Exception ex) {
                log.error("Error when trying to reply ", ex);
            }
        }
    }
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        PrintWriter out = response.getWriter();
        if(mode!= null && mode.equals("getMoreTweets")){//Gets more Tweets
            System.out.println("brings more tweets");
            doGetMoreTweets(request, response, paramRequest);
        }else if(mode!= null && mode.equals("retweetSent")){//Displays updated data of retweeted tweet
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            String objUri = request.getParameter("suri");
            Long id = Long.parseLong(request.getParameter("id"));
            
            try {
                Status originalStatus = twitter.showStatus(id);
                long minutes = (long)(new Date().getTime()/60000) - (originalStatus.getCreatedAt().getTime()/60000);
                out.print("Created:<b>" + (int)minutes + "</b> minutes ago - - Retweeted: <b>" + originalStatus.getRetweetCount() + "</b> times ");
                out.print("<a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("replyTweet").setParameter("id", originalStatus.getId()+"").setParameter("userName", "@" +
                        originalStatus.getUser().getScreenName()).setParameter("suri", objUri) +
                        "','Reply to @"  + originalStatus.getUser().getScreenName() + "');return false;\">Reply</a> ");
                out.println("Undo Retweet");
                
                if(request.getParameter("retweeted") != null && request.getParameter("retweeted").equals("ok")){
                    out.println("<script type=\"text/javascript\">");
                    out.println("   showStatus('Retweeted sent successfully');");
                    out.println("</script>");
                }
            } catch (TwitterException ex) {
                log.error("Error when trying to retweet ", ex);
            }
        }else if(mode!= null && mode.equals("tweetSent")){//Hides dialog used to create tweet and shows status
            if(request.getParameter("repliedTweet") != null && request.getParameter("repliedTweet").equals("ok")){
                out.println("<script type=\"text/javascript\">");
                out.println("   hideDialog();");
                out.println("   showStatus('Tweet sent');");
                out.println("</script>");
            }
        }else if(mode!= null && mode.equals("replyTweet")){//Displays dialog to create tweet
            String userName = request.getParameter("userName");
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("id", request.getParameter("id"));
            actionURL.setParameter("suri", request.getParameter("suri"));

            out.println("<form type=\"dijit.form.Form\" id=\"createTweet\" action=\"" +  actionURL.setAction("sendReply") + "\" method=\"post\" onsubmit=\"submitForm('createTweet'); try{document.getElementById('csLoading').style.display='inline';}catch(noe){}; return false;\">");            
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>"); 
            out.println("   <td>");
            out.println("       <textarea type=\"dijit.form.Textarea\" name=\"replyText\" id=\"replyText\" rows=\"4\" cols=\"50\">" + userName + "</textarea>");
            out.println("   </td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Reply</button></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
        }else if(mode.equals("doGetStreamUser")){//Displays new statuses obtained with the stream
            HttpSession session = request.getSession(true);
            if(session.getAttribute("tweetsListener")!=null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute("tweetsListener");
                if(tweetsListener.socialStatus.size() > 0){
                   //System.out.println("EXISTEN REGISTROS: " + tweetsListener.socialStatus.size());
                   int i;
                   for(i = tweetsListener.socialStatus.size()-1 ; i >= 0 ; i-- ){//Most recent status first
                       try{
                       doPrintTweet(request, response, paramRequest, tweetsListener.socialStatus.get(i), twitter.getId(), response.getWriter());
                       }catch(TwitterException te){
                           log.error("Error when printing tweet:" , te);
                       }
                   }
                   //As user has requested the tweets in ArrayList
                   //Restart the timer to keep thread alive
                   tweetsListener.startTime = System.currentTimeMillis();
                   tweetsListener.socialStatus.clear();
                }
            }
        }else if(mode.equals("newTweets")){//Displays a status message when new tweets received from stream
            HttpSession session = request.getSession(true);
            if(session.getAttribute("tweetsListener")!=null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute("tweetsListener");
                int noOfNewTweets = tweetsListener.socialStatus.size();
                if(noOfNewTweets > 0){
                   out.println("<a href=\"#\" onclick=\"appendHtmlAt('" + paramRequest.getRenderUrl().setMode("doGetStreamUser") + "','stream','top'); try{dojo.byId(this.parentNode.id).innerHTML = '';}catch(noe){}; return false;\">You have <b>" + noOfNewTweets +  "</b> new tweet" + (noOfNewTweets > 1 ? "s" : "") +  "</a>");
                }
            }
        }else{
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doGetMoreTweets(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        Long maxTweetID;
        try{
            maxTweetID = Long.parseLong(request.getParameter("maxTweetID"));
        }catch(NumberFormatException nfe){
            maxTweetID =0L;
        }
        
        try {
            System.out.println("Get the next 25 tweets!!");
            Paging paging = new Paging(); //used to set maxId, count
            paging.count(20);
            if(maxTweetID >0L){
                paging.setMaxId(maxTweetID-1);
            }
            int i = 0;
            for (Status status : twitter.getHomeTimeline(paging)) {
                maxTweetID = status.getId();
                doPrintTweet(request, response, paramRequest, status, twitter.getId(),response.getWriter());
                i++;
            }
            out.println("<label id=\"moreTwitLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"") + "','getMoreTweets','bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">More tweets</a></label>");
            System.out.println("Total tweets:" + i);
        } catch (Exception te) {
            log.error("Error when getting timeline: ", te);
        }
    }
    
    public static void doPrintTweet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Status status, Long currenUser, java.io.Writer writer) throws SWBResourceException, IOException {
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        Long maxTweetID;
        try{
            maxTweetID = Long.parseLong(request.getParameter("maxTweetID"));
        }catch(NumberFormatException nfe){
            maxTweetID =0L;
        }
        try {            
                maxTweetID = status.getId();
                writer.write("<fieldset>");
                writer.write("<table style=\"width: 100%; border: 0px\">");
                writer.write("<tr>");
                writer.write("   <td colspan=\"2\">");
                writer.write("   " + status.getUser().getName() + " <b>" + status.getUser().getScreenName()+ "</b>");
                writer.write("   </td>");
                writer.write("</tr>");
                writer.write("<tr>");
                writer.write("   <td  width=\"10%\">");
                writer.write("       <img src=\"" + status.getUser().getProfileImageURL() + "\"/>");
                writer.write("   </td>");
                writer.write("   <td width=\"90%\">");                
                String statusText = status.getText(); // It's necessary to include the URL for media, hash tags and usernames
                
                URLEntity urlEnts[] = status.getURLEntities();
                if(urlEnts!=null && urlEnts.length >0){
                    for(URLEntity urlEnt: urlEnts){                       
                        statusText=statusText.replace(urlEnt.getURL(), "<a href=\"" + urlEnt.getURL() +  "\" target=\"_blank\">" + urlEnt.getURL() +"</a>");
                    }
                }
                
                MediaEntity mediaEnts[] = status.getMediaEntities();
                if(mediaEnts!=null && mediaEnts.length >0){
                    for(MediaEntity mediaEnt: mediaEnts){       
                        statusText=statusText.replace(mediaEnt.getURL(), "<a href=\"" + mediaEnt.getURL() +  "\" target=\"_blank\">" + mediaEnt.getURL() +"</a>");
                    }
                }

                HashtagEntity htEnts[] = status.getHashtagEntities(); //Probably it would be better to look for HTs with regex
                if(htEnts!=null && htEnts.length >0){
                    for(HashtagEntity htEnt: htEnts){       
                        statusText=statusText.replace("#" + htEnt.getText(), "<a href=\"https://twitter.com/search?q=%23" + htEnt.getText() +  "&src=hash\" target=\"_blank\">#"+ htEnt.getText() +"</a>");
                    }
                }
                
                UserMentionEntity usrEnts[] = status.getUserMentionEntities(); //Probably it would be better to look for User Mentions with regex 
                if(usrEnts!=null && usrEnts.length >0){
                    for(UserMentionEntity usrEnt: usrEnts){
                        statusText=statusText.replace("@" + usrEnt.getScreenName(), "<a href=\"https://twitter.com/" + usrEnt.getScreenName() +  "\" target=\"_blank\">@"+ usrEnt.getScreenName() +"</a>");
                    }
                }
                
                writer.write(        statusText);
                writer.write("   </td>");
                writer.write("</tr>");
                writer.write("<tr>");
                writer.write("   <td colspan=\"2\">");
                
                writer.write("<div id=\"" + status.getId() + "\" dojoType=\"dijit.layout.ContentPane\">");
                long minutes = (long)(new Date().getTime()/60000) - (status.getCreatedAt().getTime()/60000);
                writer.write("Created:<b>" + (int)minutes + "</b> minutes ago - - Retweeted: <b>" + status.getRetweetCount() + "</b> times ");                    
                writer.write("<a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyTweet").setParameter("id", status.getId()+"").setParameter("userName", "@" + status.getUser().getScreenName()) + "','Reply to @" + status.getUser().getScreenName() + "');return false;\">Reply</a>  ");
                if(status.isRetweetedByMe()){
                    actionURL.setAction("undoRT");
                    //writer.write("<a href=\"\"  onclick=\"submitUrl('" + action.setParameter("id", status.getId()+"").toString() + "',this);return false;" +"\">Undo Retweet</a>");
                    writer.write("Undo Retweet");
                }else if(status.getUser().getId() == currenUser){ //User cannot retweet its own tweet
                    writer.write("My tweet");
                }else{
                    actionURL.setAction("doRT");
                    writer.write("<a href=\"#\"  onclick=\"postHtml('" + actionURL.setParameter("id", status.getId()+"") + "','" + status.getId() + "'); return false;" +"\">Retweet</a>");
                }
                writer.write("   </div>");
                writer.write("   </td>");
                writer.write("</tr>");          
                writer.write("</table>");
                writer.write("</fieldset>");             
        } catch (Exception te) {
            log.error("Error when getting timeline: ", te);
        }        
    }
}
