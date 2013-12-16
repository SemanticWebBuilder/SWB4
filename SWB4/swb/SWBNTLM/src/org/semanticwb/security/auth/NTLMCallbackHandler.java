/*
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
 */
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
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import waffle.util.AuthorizationHeader;
import waffle.util.NtlmServletRequest;

/**
 *
 * @author serch
 */
public class NTLMCallbackHandler extends SWB4CallbackHandler {

  /** The log. */
private static Logger log = SWBUtils.getLogger(NTLMCallbackHandler.class);

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 12896L;

    /** The request. */
    private HttpServletRequest request;

    /** The response. */
    private HttpServletResponse response;

    /** The auth type. */
    private String authType;

    /** The website. */
    private String website;

    /**
     * Instantiates a new sW b4 callback handler login password imp.
     */
    public NTLMCallbackHandler() {
        log.trace("Simple CallbackHandler");
        this.request = null;
        this.response = null;
        this.authType = (String) SWBPortal.getServletContext().getAttribute("authType");
        this.website = null;
    }

    /**
     * Instantiates a new sW b4 callback handler login password imp.
     *
     * @param request the request
     * @param response the response
     * @param authType the auth type
     * @param website the website
     */
    public NTLMCallbackHandler(HttpServletRequest request, HttpServletResponse response, String authType, String website) {
        log.trace("Complete CallbackHandler");
        this.request = request;
        this.response = response;
        this.authType = authType;
        this.website = website;
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {
        AuthorizationHeader authorizationHeader = new AuthorizationHeader(request);
        String login = NtlmServletRequest.getConnectionId(request);


        String password = authorizationHeader.getToken();

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

}
