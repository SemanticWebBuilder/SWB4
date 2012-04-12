/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.networks;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author adriana.rodriguez
 */
public class TwitterResource extends GenericAdmResource {

    AccessToken accessToken;
    private Twitter twitter;
    private String urlCallback = "http://adriana.mx:8080/swb/demo/TwitterResource";//adriana.mx/swb/demo/TwitterResource
    private String customerKey="AmXwyqAVw0pyhQf6q4ojHg";
    private String customerSecret="yCjLHWzujB5C0TJIjnxTeVRNF8UNw1IBQ9NyrsfeXk";
    
    private RequestToken requestToken;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("requestToken", request);
            request.setAttribute("paramRequest", paramsRequest);
            String path = SWBPortal.getWebWorkPath() + "/models/" + paramsRequest.getWebPage().getWebSiteId() + "/jsp/TestSocialResource.jsp";
            System.out.println("PATH:       " + path);
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doAutorizacion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String verifier = request.getParameter("oauth_verifier");
        if (verifier == null) {
            try {
                twitter = new TwitterFactory().getInstance();
                twitter.setOAuthConsumer(customerKey, customerSecret);
                requestToken = twitter.getOAuthRequestToken(urlCallback);
                request.getSession().setAttribute("requestToken", requestToken);
                response.sendRedirect(requestToken.getAuthorizationURL());
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                SWBResourceURL posted = paramRequest.getRenderUrl().setMode("view");
                response.sendRedirect(request.getContextPath() + posted);
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void updateStatus(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
        twitter.setOAuthAccessToken(accessToken);
        String estado = request.getParameter("estado");
        String img = request.getParameter("img");
        try {
            twitter.setOAuthAccessToken(accessToken);
            StatusUpdate sup = new StatusUpdate(new String(estado.getBytes(), "utf-8"));           
            if (img != null && !img.equals("")) {
            sup.setMedia(new File(img));
            }
            twitter.updateStatus(sup);
        } catch (TwitterException ex) {
            if (401 == ex.getStatusCode()) {
                getResourceBase().setAttribute("oauth", null);
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (accessToken != null) {
            doView(request, response, paramRequest);
        } else {
            doAutorizacion(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        updateStatus(request);
    }
}
