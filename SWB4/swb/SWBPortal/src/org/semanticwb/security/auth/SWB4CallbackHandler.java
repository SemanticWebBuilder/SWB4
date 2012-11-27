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

import javax.security.auth.callback.CallbackHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.servlet.internal.DistributorParams;

// TODO: Auto-generated Javadoc
/**
 * CallBackHandler abstracto del Semantic WebBuilder, forza las necesidades especiíficas para elp roceso de autenticación
 * Abstract CallBackHandler for Semantic WebBuilder, it enforces the specific needs for authentication process.
 * 
 * @author Sergio Martínez  sergio.martinez@acm.org
 */
public abstract class SWB4CallbackHandler implements CallbackHandler {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWB4CallbackHandler.class);
    
    /** The request. */
    private HttpServletRequest request;
    
    /** The response. */
    private HttpServletResponse response;
    
    /** The auth type. */
    private String authType;
    
    /** The website. */
    private String website;

    /**
     * Constructor por defecto, inicializa el modo de autenticación por defecto
     * Default constructor, initialize using the default autentication type.
     */
    public SWB4CallbackHandler() {
        this.request = null;
        this.response = null;
        this.website = null;
        this.authType = (String) SWBPortal.getServletContext().getAttribute("authType");
    }

    /**
     * Constructor recomendado, recibe la petición, el punto de respuesta, el modo de autenticación y el contexto de operación
     * Recommended Constructor, receibes the request, the response, the autentication type and the operational context.
     * 
     * @param request Petición
     * @param response punto de respuesta
     * @param authType modo de autenticacion (BASIC - FORM)
     * @param website the website
     */
    public SWB4CallbackHandler(HttpServletRequest request, HttpServletResponse response, String authType, String website) {
        this.request = request;
        this.response = response;
        this.authType = authType;
        this.website = website;
    }

    /**
     * Obtiene el request relacionado a este handler
     * obtains related request to this handler.
     * 
     * @return request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Relaciona un request a este handler y en su caso crea un contexto
     * relates a request to this handler and if needed creates a context.
     * 
     * @param request the new request
     */
    public void setRequest(HttpServletRequest request) {
        log.trace("Setting Request");
        this.request = request;
        if (null == website) {
            DistributorParams dparams = new DistributorParams(request, request.getRequestURI());
            website = dparams.getWebPage().getWebSiteId();
        }
    }

    /**
     * Obtiene el response relacionado a este handler
     * obtains related response to this handler.
     * 
     * @return response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Relaciona un response a este handler
     * relates a response to this handler.
     * 
     * @param response the new response
     */
    public void setResponse(HttpServletResponse response) {
        log.trace("Setting Response");
        this.response = response;
    }
}