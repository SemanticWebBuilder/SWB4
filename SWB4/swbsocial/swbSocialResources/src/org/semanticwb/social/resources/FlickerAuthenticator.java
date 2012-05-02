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
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Flicker;
import org.semanticwb.social.SWBFlickrOauth;

/**
 *
 * @author martha.jimenez
 */
public class FlickerAuthenticator extends GenericResource {

    Logger log = SWBUtils.getLogger(FlickerAuthenticator.class);
    SWBFlickrOauth oauthConnection = new SWBFlickrOauth();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/social/authentication/autFlickr.jsp");
            rd.include(request, response);
        } catch(Exception e) {
            log.error(e);
        }
    }

    public void doAuthorization(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(request.getParameter("uri") != null) {
            String uri = request.getParameter("uri");
            SemanticObject semObj = SemanticObject.createSemanticObject(URLDecoder.decode(uri));
            Flicker flicker = (Flicker)semObj.createGenericInstance();
            WebPage wp = paramRequest.getWebPage();

            String port = "";
            if (request.getServerPort() != 80)
            {
                port = ":" + request.getServerPort();
            }
            String host = request.getServerName();
            String scheme = request.getScheme() + "://" + host + port;

            String url2 = flicker.getCredentials(flicker.getURI(), wp, scheme);
            oauthConnection.setUriFlicker(flicker.getURI());
            response.sendRedirect(url2);
        } else {
            try {
                request.setAttribute("paramRequest", paramRequest);
                RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/social/authentication/FlickerAuth.jsp");
                rd.include(request, response);
            } catch(Exception e) {
                log.error(e);
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("firstStepAuth")){
            doAuthorization(request, response, paramRequest);
        } else if(request.getParameter("oauth_token") != null && request.getParameter("oauth_verifier") != null){
            
                Flicker flicker = (Flicker) SemanticObject.createSemanticObject(oauthConnection.getUriFlicker()).createGenericInstance();
                String tok_Ou = request.getParameter("oauth_token");
                String tok_Ver = request.getParameter("oauth_verifier");
                if(tok_Ou.length() > 1 && tok_Ver.length() > 1) {
                    oauthConnection.setKey(flicker.getAppKey());
                    oauthConnection.setSecret(flicker.getSecretKey());
                    oauthConnection.setOauthToken(tok_Ou);
                    oauthConnection.setOauthVerifier(tok_Ver);
                    oauthConnection.sendAccessToken(flicker);
                }
                response.sendRedirect(request.getRequestURL().toString());
        } else {
            super.processRequest(request, response, paramRequest);
        }

    }

}
