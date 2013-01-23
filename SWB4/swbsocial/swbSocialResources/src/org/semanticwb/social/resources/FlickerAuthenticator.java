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
 * Esta clase se encarga de obtener el permiso de acceso a una cuenta en Flickr para
 * posteriormente almacenar dichas autorizaciones en objetos de tipo Flicker
 */
public class FlickerAuthenticator extends GenericResource {

    Logger log = SWBUtils.getLogger(FlickerAuthenticator.class);
    /**
     * Se crea una instancia de tipo SWBFlickrOauth, la cual permite tener acceso a los metodos
     * que se encargaran de conectarse al sitio para obtener los permisos del usuario
     */
    SWBFlickrOauth oauthConnection = new SWBFlickrOauth();

    /**
     * Env&iacute;a a la vista que mostrar&aacute; la opci&oacute;n para obtener las
     * credenciales de  una cuenta en Flickr
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/social/authentication/FlickerAuth.jsp");
            rd.include(request, response);
        } catch(Exception e) {
            log.error(e);
        }
    }

    /**
     * Env&iacute;a un redireccionamiento al sitio de Flickr que har&aacute; la petici&oacute;
     * de un token temporal para poder obtener las credenciales del usuario. Si todo esta
     * correcto se mostrar&aacute; la ventana de Flickr para que el usuario autorize
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

    /**
     * Procesa las distintas vistas para la obtención de credenciales de un usuario. Valida
     * si en el llamado al recurso vienen adicionalmente los parámetros de que el usuario
     * ha aceptado que la aplicaci&oacute;n utilize su cuenta y almacena los tokens permanentes
     * en el objeto Flickr
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
