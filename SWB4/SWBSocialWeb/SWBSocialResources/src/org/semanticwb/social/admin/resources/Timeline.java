/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
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
        if (request.getParameter("jspResponse") != null) {
            jspResponse = request.getParameter("jspResponse");
        }
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {        
        String action = response.getAction();
        String objUri = request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Long id = Long.parseLong(request.getParameter("id"));
        Twitter twit = (Twitter) semanticObject.createGenericInstance();        
        
        if(action.equals("RT")){
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();            
            try {
                Status st = twitter.retweetStatus(id); // El id de st Deberá ser guardado para poder deshacer el RT
                System.out.println("Retwit!");
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(Timeline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(action.equals("undoRT")){
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();            
            try {
                System.out.println("Undo Retwit!");
                List<Status> retweets = twitter.getRetweetsOfMe();
                for(Status rt : retweets){//Va a ser necesario guardar los ID de los RT's hechos.
                    if(id == rt.getId()){
                        System.out.println("RT Found!");
                        twitter.destroyStatus(id);
                        
                    }
                }
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(Timeline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(action.equals("reply")){
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth(twit).build()).getInstance();            
            try {
                String answer = request.getParameter("replyText");
                System.out.println("Answer tweet!" + id);
                twitter.updateStatus(new StatusUpdate(answer).inReplyToStatusId(id));                                
            } catch (Exception ex) {
                System.out.println("Error in reply");
                java.util.logging.Logger.getLogger(Timeline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        response.setRenderParameter("suri", objUri);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.getWriter().println("<h1> CONTENT OF DIALOG!!");
    }                    
    
}
