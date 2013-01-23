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
 * Esta clase se encarga de obtener el permiso de acceso a una cuenta en Twitter para
 * posteriormente almacenar dichas autorizaciones en objetos de tipo Twitter
 */
public class TwitterAuthenticator extends GenericResource {

    Logger log = SWBUtils.getLogger(TwitterAuthenticator.class);
    /**
     * Objeto twitter que permitir&aacute; tener acceso a los metodos para
     * poder autenticarse
     */
    private twitter4j.Twitter twitter;
    /**
     * Objeto que se encargará de hacer una petici&oacute;n al sitio de Twitter para generar
     * tokens temporales que permitiran autenticar un usuario
     */
    private RequestToken requestToken;
    /**
     * Url a la que redireccionará el sitio Twitter una vez que el usuario haya aceptado
     * dar el acceso de su cuenta a SWBSocial
     */
    private String urlCallback = "http://swbsocial.mx:8080/firmaTwitter";
    /**
     * Objeto que se encargará de hacer una petici&oacute;n al sitio de Twitter para generar
     * tokens permanentes que permitiran autenticar un usuario
     */
    AccessToken accessToken;
    /**
     * Uri del objeto Twitter que se ha creado para almacenar las credenciales autorizadas
     * por el usuario.
     */
    String uriTwitter = "";

    /**
     * Env&iacute;a a la vista que mostrar&aacute; la opci&oacute;n para obtener las
     * credenciales de  una cuenta en Twitter
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */
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
    /**
     * Procesa las distintas vistas para la obtención de credenciales de un usuario. Valida
     * si en el llamado al recurso vienen adicionalmente los parámetros de que el usuario
     * ha aceptado que la aplicaci&oacute;n utilize su cuenta y almacena los tokens permanentes
     * en el objeto Twitter
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */
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

    /**
     * Env&iacute;a un redireccionamiento al sitio de Twitter que har&aacute; la petici&oacute;
     * de un token temporal para poder obtener las credenciales del usuario. Si todo esta
     * correcto se mostrar&aacute; la ventana de Twitter para que el usuario autorize
     * a la aplicaci&oacute;n de usar su cuenta
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */
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
