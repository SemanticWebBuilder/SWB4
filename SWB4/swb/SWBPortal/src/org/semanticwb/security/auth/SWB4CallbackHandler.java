package org.semanticwb.security.auth;

import java.io.Serializable;
import javax.security.auth.callback.CallbackHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.servlet.internal.DistributorParams;

/**
 * CallBackHandler abstracto del Semantic WebBuilder, forza las necesidades especiíficas para elp roceso de autenticación
 * Abstract CallBackHandler for Semantic WebBuilder, it enforces the specific needs for authentication process
 * @author Sergio Martínez  sergio.martinez@acm.org
 */
public abstract class SWB4CallbackHandler implements CallbackHandler, Serializable {

    private static Logger log = SWBUtils.getLogger(SWB4CallbackHandler.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String authType;
    private DistributorParams dparams;

    /**
     * Constructor por defecto, inicializa el modo de autenticación por defecto
     * Default constructor, initialize using the default autentication type
     */
    public SWB4CallbackHandler() {
        this.request = null;
        this.response = null;
        this.dparams = null;
        this.authType = (String) SWBPlatform.getServletContext().getAttribute("authType");
    }

    /**
     * Constructor recomendado, recibe la petición, el punto de respuesta, el modo de autenticación y el contexto de operación
     * Recommended Constructor, receibes the request, the response, the autentication type and the operational context
     * @param request Petición
     * @param response punto de respuesta
     * @param authType modo de autenticacion (BASIC - FORM)
     * @param dparams Contexto de operación
     */
    public SWB4CallbackHandler(HttpServletRequest request, HttpServletResponse response, String authType, DistributorParams dparams) {
        this.request = request;
        this.response = response;
        this.authType = authType;
        this.dparams = dparams;
    }

    /**
     * Obtiene el request relacionado a este handler
     * obtains related request to this handler
     * @return request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Relaciona un request a este handler y en su caso crea un contexto
     * relates a request to this handler and if needed creates a context
     * @param request
     */
    public void setRequest(HttpServletRequest request) {
        log.trace("Setting Request");
        this.request = request;
        if (null == dparams) {
            dparams = new DistributorParams(request, request.getRequestURI());
        }
    }

    /**
     * Obtiene el response relacionado a este handler
     * obtains related response to this handler
     * @return response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Relaciona un response a este handler
     * relates a response to this handler
     * @param response
     */
    public void setResponse(HttpServletResponse response) {
        log.trace("Setting Response");
        this.response = response;
    }
}