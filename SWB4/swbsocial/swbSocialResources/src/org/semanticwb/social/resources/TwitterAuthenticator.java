/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.resources;

import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
//import twitter4j.Twitter;
import org.semanticwb.portal.api.SWBResourceURL;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
//import org.semanticwb.social.Twitter;

/**
 *
 * @author martha.jimenez
 */
public class TwitterAuthenticator extends GenericResource {

    Logger log = SWBUtils.getLogger(TwitterAuthenticator.class);
    private twitter4j.Twitter twitter;
    private RequestToken requestToken;
    private String urlCallback = "http://swbsocial.mx:8080/swb/Sitio_Social/Autenticacion_con_Twitter";
    AccessToken accessToken;
    String uriTwitter = "";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            //request.setAttribute("requestToken", request);
            request.setAttribute("paramRequest", paramRequest);
            String path = "/swbadmin/jsp/social/authentication/TwitterAuth.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Exception Twitter Authenticator: " + e);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("firstStepAuth")){
            doAuthorization(request, response, paramRequest);
        } else if(request.getParameter("oauth_verifier") != null){
            String verifier = request.getParameter("oauth_verifier");
            try {

                accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                SemanticObject semObj = SemanticObject.createSemanticObject(URLDecoder.decode(uriTwitter));
                org.semanticwb.social.Twitter twitterObj = (org.semanticwb.social.Twitter)semObj.createGenericInstance();
                twitterObj.setAccessToken(accessToken.getToken());
                twitterObj.setAccessTokenSecret(accessToken.getTokenSecret());
                SWBResourceURL posted = paramRequest.getRenderUrl().setMode("view");
                response.sendRedirect(request.getContextPath() + posted);
            } catch (TwitterException ex) {
                log.error("Error in: " + ex);
            }
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doAuthorization(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(request.getParameter("uri") != null) {
            String uri = request.getParameter("uri");
            uriTwitter = uri;
            SemanticObject semObj = SemanticObject.createSemanticObject(URLDecoder.decode(uri));
            org.semanticwb.social.Twitter twitterObj = (org.semanticwb.social.Twitter)semObj.createGenericInstance();
            try {
                twitter = new TwitterFactory().getInstance();
                twitter.setOAuthConsumer(twitterObj.getAppKey(), twitterObj.getSecretKey());
                requestToken = twitter.getOAuthRequestToken(urlCallback);
                request.getSession().setAttribute("requestToken", requestToken);
                response.sendRedirect(requestToken.getAuthorizationURL());
            } catch (TwitterException ex) {
                log.error("Exception in: " + ex);
            }
        }
    }
}
