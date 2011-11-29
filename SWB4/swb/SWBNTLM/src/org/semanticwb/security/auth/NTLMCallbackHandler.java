/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
