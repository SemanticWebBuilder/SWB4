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
import org.semanticwb.model.Resource;
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
    //private String urlCallback = "http://swbsocial.mx:8080/firmaTwitter";             
    private String urlCallback;
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
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.render(request, response, paramRequest);
        //urlCallback = "http://"+request.getServerName()+":"+request.getServerPort()+"/"+paramRequest.getUser().getLanguage()+"/"+paramRequest.getResourceBase().getWebSiteId()+"/"+paramRequest.getWebPage().getId()+"/_rid/"+paramRequest.getResourceBase().getId()+"/_mod"+paramRequest.getMode()+"/_lang/"+paramRequest.getUser().getLanguage();        
        //urlCallback = "http://miwebsite.mx:8088/firmaTwitter";
    }
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
System.out.println("\n\n\n\n\ndoView.................");
System.out.println("urlCallback="+urlCallback);
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
System.out.println("\nprocessRequest......");
        if(paramRequest.getMode().equals("firstStepAuth")){
            doAuthorization(request, response, paramRequest);
        }else if(request.getParameter("oauth_verifier") != null) {
System.out.println("modo="+paramRequest.getMode());
            String verifier = request.getParameter("oauth_verifier");
System.out.println("verifier="+verifier);
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                //SemanticObject semObj = SemanticObject.createSemanticObject(URLDecoder.decode(uriTwitter,"UTF-8"));
                SemanticObject semObj = SemanticObject.createSemanticObject(uriTwitter);
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
System.out.println("doAuthorization..........");
        if(request.getParameter("uri") != null) {
            String uri = request.getParameter("uri");
System.out.println("uri="+uri);
            uriTwitter = URLDecoder.decode(uri,"UTF-8");
System.out.println("uriTwitter="+uriTwitter);
            SemanticObject semObj = SemanticObject.createSemanticObject(uriTwitter);
            org.semanticwb.social.Twitter twitterObj = (org.semanticwb.social.Twitter)semObj.createGenericInstance();
System.out.println("twitterObj="+twitterObj);
            try {
                twitter = new TwitterFactory().getInstance();
System.out.println("AppKey="+twitterObj.getAppKey()+", SecretKey="+twitterObj.getSecretKey());                
                twitter.setOAuthConsumer(twitterObj.getAppKey(), twitterObj.getSecretKey());                
                
                String urlCallback_ = "http://miwebsite.mx:"+request.getServerPort()+"/"+paramRequest.getUser().getLanguage()+"/"+paramRequest.getResourceBase().getWebSiteId()+"/"+paramRequest.getWebPage().getId()+"/_rid/"+paramRequest.getResourceBase().getId()+"/_mod/edit/_lang/"+paramRequest.getUser().getLanguage();
                
System.out.println("urlCallback_="+urlCallback_);
                requestToken = twitter.getOAuthRequestToken(urlCallback_);
                request.getSession().setAttribute("requestToken", requestToken);
System.out.println("requestToken.getAuthorizationURL()="+requestToken.getAuthorizationURL());
                response.sendRedirect(requestToken.getAuthorizationURL());
            } catch (TwitterException ex) {
                log.error("Exception in: " + ex);
            }
        }
    }
}
