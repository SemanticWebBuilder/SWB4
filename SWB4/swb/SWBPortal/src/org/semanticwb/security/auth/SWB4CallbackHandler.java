/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.security.auth;

import java.io.IOException;
import java.io.Serializable;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBInstance;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Sergio Martínez  sergio.martinez@acm.org
 */
public class SWB4CallbackHandler implements CallbackHandler, Serializable {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String authType;

    public SWB4CallbackHandler() {
        this.request = null;
        this.response = null;
        this.authType = (String) SWBInstance.getServletContext().getAttribute("authType");
    }

    public SWB4CallbackHandler(HttpServletRequest request, HttpServletResponse response, String authType) {
        this.request = request;
        this.response = response;
        this.authType = authType;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        if ("BASIC".equalsIgnoreCase(authType)) {
            getBasicCredentials(callbacks);
        }
    }

    private void getBasicCredentials(Callback[] callbacks) {
        String login = "";
        String password = "";
        String header = request.getHeader("Authorization");
        if (null != header && !"".equals(header)) {
            header = header.substring(6).trim();
            String[] datos = SWBUtils.TEXT.decodeBase64(header).split(":");
            try {
                login = datos[0].trim();
                password = datos[1].trim();
            } catch (Exception ie) {
            } //Ignored Exception, asume no login - password

        }
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback nameCallback = (NameCallback) callbacks[i];
                nameCallback.setName(login);

            } else if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback passwordCallback = (PasswordCallback) callbacks[i];
                passwordCallback.setPassword(password.toCharArray());
            }
        }
    }
    
    /**
     * Construlle la solicitud para la autenticación Basica
     * @param realm Sitio al que se ingresará
     * @param response HttpServletResponse para enviar la respuesta
     */
    public static void basicChallenge(String realm, HttpServletResponse response){
        StringBuffer header= new StringBuffer();
		header.append("Basic realm=\"");
		header.append(realm);
		header.append("\"");
		response.setHeader("WWW-Authenticate",header.toString());
		response.setHeader("Cache-Control", "no-cache=\"Authorization\"");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}