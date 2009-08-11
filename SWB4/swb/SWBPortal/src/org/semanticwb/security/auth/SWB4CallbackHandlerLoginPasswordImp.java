/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
package org.semanticwb.security.auth;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;

/**
 * CallbackHandler for login password credentials
 * CallbackHandler para credenciales login password
 * @author Sergio Martínez  sergio.martinez@acm.org
 */
public class SWB4CallbackHandlerLoginPasswordImp extends SWB4CallbackHandler {
private static Logger log = SWBUtils.getLogger(SWB4CallbackHandlerLoginPasswordImp.class);
    private static final long serialVersionUID = 12896L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String authType;
    private String website;

    public SWB4CallbackHandlerLoginPasswordImp() {
        log.trace("Simple CallbackHandler");
        this.request = null;
        this.response = null;
        this.authType = (String) SWBPlatform.getServletContext().getAttribute("authType");
        this.website = null;
    }

    public SWB4CallbackHandlerLoginPasswordImp(HttpServletRequest request, HttpServletResponse response, String authType, String website) {
        log.trace("Complete CallbackHandler");
        this.request = request;
        this.response = response;
        this.authType = authType;
        this.website = website;
    }

    /**
     * Manejo de los parámetros para enviarlos al JAAS
     * parameter management as required by JAAS
     * @param callbacks
     * @throws java.io.IOException
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        log.trace("Tipo de Autenticacion: " + authType);
        if ("BASIC".equalsIgnoreCase(authType)) {
            getBasicCredentials(callbacks);
        }
        if ("FORM".equalsIgnoreCase(authType)) {
            getFormCredentials(callbacks);
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
            } else if (callbacks[i] instanceof TextInputCallback) {
                TextInputCallback textInputCallback = (TextInputCallback) callbacks[i];
                if (null != website) {
                    textInputCallback.setText(website);
                } else {
                    textInputCallback.setText(SWBContext.getGlobalWebSite().getId());
                }
            }
        }
    }

    private void getFormCredentials(Callback[] callbacks) {
        String login = request.getParameter("wb_username");
        String password = request.getParameter("wb_password");
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback nameCallback = (NameCallback) callbacks[i];
                nameCallback.setName(login);

            } else if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback passwordCallback = (PasswordCallback) callbacks[i];
                passwordCallback.setPassword(password == null ? null : password.toCharArray());
            } else if (callbacks[i] instanceof TextInputCallback) {
                TextInputCallback textInputCallback = (TextInputCallback) callbacks[i];
                textInputCallback.setText(website);
            }
        }
    }
}
