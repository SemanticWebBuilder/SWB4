package org.semanticwb.security.auth;

import java.io.IOException;
import java.io.Serializable;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
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
import org.semanticwb.servlet.internal.DistributorParams;

/**
 *
 * @author Sergio Martínez  sergio.martinez@acm.org
 */
public abstract class SWB4CallbackHandler implements CallbackHandler, Serializable {

    private static Logger log = SWBUtils.getLogger(SWB4CallbackHandler.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String authType;
    private DistributorParams dparams;

    public SWB4CallbackHandler() {
        this.request = null;
        this.response = null;
        this.dparams = null;
        this.authType = (String) SWBPlatform.getServletContext().getAttribute("authType");
    }

    public SWB4CallbackHandler(HttpServletRequest request, HttpServletResponse response, String authType, DistributorParams dparams) {
        this.request = request;
        this.response = response;
        this.authType = authType;
        this.dparams = dparams;
    }

    public abstract HttpServletRequest getRequest();

    public abstract void setRequest(HttpServletRequest request);

    public abstract HttpServletResponse getResponse();

    public abstract void setResponse(HttpServletResponse response);

}