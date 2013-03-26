/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.StatusUpdate;

/**
 *
 * @author francisco.jimenez
 */
public class Timeline extends GenericResource{
    private static Logger log = SWBUtils.getLogger(Timeline.class);
    
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
        String jspResponse = SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/timeline.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
//            request.setAttribute("", new TwitterFactory());
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {        
        String action = response.getAction();
        String objUri = request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Long id = Long.parseLong(request.getParameter("id"));
        Twitter twit = (Twitter) semanticObject.createGenericInstance();        

        if(action.equals("doRT")){ //Makes the retweet
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();            
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
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();            
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
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();            
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
        
        if(mode!= null && mode.equals("retweetSent")){//Displays updated data of retweeted tweet
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            String objUri = request.getParameter("suri");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Long id = Long.parseLong(request.getParameter("id"));
            Twitter twit = (Twitter) semanticObject.createGenericInstance();
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();
            
            try {
                Status originalStatus = twitter.showStatus(id);
                long minutes = (long)(new Date().getTime()/60000) - (originalStatus.getCreatedAt().getTime()/60000);
                out.print("Created:<b>" + (int)minutes + "</b> minutes ago - - Retweeted: <b>" + originalStatus.getRetweetCount() + "</b> times ");                                                
                out.print("<a target=\"_blank\" href=\"#\" onclick=\"showDialog('" + renderURL.setMode("replyTweet").setParameter("id", originalStatus.getId()+"").setParameter("userName", "@" + originalStatus.getUser().getScreenName()).setParameter("suri", objUri) + "','Responder a @"  + originalStatus.getUser().getScreenName() + "');return false;\">Responder</a> ");
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
                out.println("   showStatus('Tweet enviado');");
                out.println("</script>");
            }
        }else if(mode!= null && mode.equals("replyTweet")){//Displays dialog to create tweet
            String userName = request.getParameter("userName");
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("id", request.getParameter("id"));
            actionURL.setParameter("suri", request.getParameter("suri"));

            out.println("<form type=\"dijit.form.Form\" id=\"createTweet\" action=\"" +  actionURL.setAction("sendReply") + "\" method=\"post\" onsubmit=\"submitForm('createTweet'); return false;\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>"); 
            out.println("   <td>");
            out.println("       <textarea type=\"dijit.form.Textarea\" name=\"replyText\" id=\"replyText\" rows=\"4\" cols=\"50\">" + userName + "</textarea>");
            out.println("   </td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Twittear</button></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
        }else{
            super.processRequest(request, response, paramRequest);
        }
    }
}
