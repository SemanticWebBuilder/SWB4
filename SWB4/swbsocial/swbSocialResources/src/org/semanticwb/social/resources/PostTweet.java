/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.social.twitter.TwitterResource;
import org.semanticwb.social.Message;
import org.semanticwb.social.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author adriana.rodriguez
 */
public class PostTweet extends GenericResource {

    /*La url en donde se encuentra el recurso*/
    String urlCallback;
    AccessToken accessToken;
    Twitter tweet;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jspResponse = "/swbadmin/jsp/twitter/TwitterPost.jsp";

        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (accessToken == null) {
            doAutorizacion(request, response, paramRequest);
        } else {
            doView(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        
        
        tweet.postMsg(null, request, response);
        
    }
        

    public void doAutorizacion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String verifier = request.getParameter("oauth_verifier");
        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
        RequestToken requestToken = null;
        if (verifier == null) {
            try {
                requestToken = twitter.getOAuthRequestToken(urlCallback);
                request.getSession().setAttribute("requestToken", requestToken);
                response.sendRedirect(requestToken.getAuthorizationURL());
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                WebPage currentPage = paramRequest.getWebPage();
                WebSite wsite = currentPage.getWebSite();
                tweet = Twitter.ClassMgr.getTwitter("", wsite);
                tweet.setSecreatKey(accessToken.getToken() + "|" + accessToken.getTokenSecret());
                SWBResourceURL posted = paramRequest.getRenderUrl().setMode("view");
                response.sendRedirect(request.getContextPath() + posted);
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
