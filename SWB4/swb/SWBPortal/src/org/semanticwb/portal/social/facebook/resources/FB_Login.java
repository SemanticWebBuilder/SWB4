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
package org.semanticwb.portal.social.facebook.resources;

import com.google.code.facebookapi.FacebookXmlRestClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * Resource that manage the integration between facebook and semanticwebbuilder (facebook java api).
 * 
 * @author Jorge Jiménez
 */
public class FB_Login extends GenericAdmResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(FB_Login.class);
    
    /** The AP i_ key. */
    private static String API_KEY = "";
    
    /** The SECRE t_ key. */
    private static String SECRET_KEY = "";
    
    /** The redirect. */
    private static String redirect = "";
    
    /** The Constant FACEBOOK_USER_CLIENT. */
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";

    /**
     * Sets the resource base.
     * 
     * @param base the new resource base
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            API_KEY=base.getAttribute("api_key");
            SECRET_KEY=base.getAttribute("secret_key");
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        redirect=paramRequest.getResourceBase().getAttribute("redirect",paramRequest.getWebPage().getUrl());
        
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("API_KEY", API_KEY);
        request.setAttribute("SECRET_KEY", SECRET_KEY);
        request.setAttribute("redirect", redirect);
        RequestDispatcher rd = request.getRequestDispatcher("/swbadmin/jsp/facebook/fb_Login.jsp");
        
        String sessionKey = "";
        HttpSession session = request.getSession(true);
        try {
            if (request.getParameter("swb") == null && getUserClient(session, redirect) == null) {
                request.setAttribute("step", "1");
                rd.include(request, response);
            } else if (request.getParameter("session") == null) {
                if(request.getParameter("swb")==null) {
                    request.setAttribute("step", "2");
                    rd.include(request, response);
                }                 
            } else {
                request.setAttribute("step", "3");
                rd.include(request, response);
                HashMap hParams = new HashMap();
                String sessionJ = request.getParameter("session");
                if (sessionJ != null) {
                    hParams = getParams(sessionJ);
                }
                sessionKey = (String) hParams.get("session_key");
                FacebookXmlRestClient userClient = new FacebookXmlRestClient(API_KEY, SECRET_KEY, sessionKey);
                session.setAttribute(FACEBOOK_USER_CLIENT, userClient);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        }
    }

    /**
     * Gets the user client.
     * 
     * @param session the session
     * @param redirect the redirect
     * @return the user client
     */
    static FacebookXmlRestClient getUserClient(HttpSession session, String redirect) {
        return (FacebookXmlRestClient) session.getAttribute(FACEBOOK_USER_CLIENT);
    }

    /**
     * Gets the params.
     * 
     * @param param the param
     * @return the params
     */
    private HashMap getParams(String param) {
        HashMap hParams = new HashMap();
        param = SWBUtils.TEXT.findInterStr(param, "{", "}").next();
        if (param != null) {
            StringTokenizer strTokens = new StringTokenizer(param, ",");
            while (strTokens.hasMoreTokens()) {
                String token = strTokens.nextToken();
                if (token == null) {
                    continue;
                }
                String key = null;
                String value = null;
                int cont = 0;
                StringTokenizer strToken2 = new StringTokenizer(token, ":");
                while (strToken2.hasMoreTokens()) {
                    String token2 = strToken2.nextToken();
                    if (token2 == null) {
                        continue;
                    }
                    cont++;
                    if (cont == 1) {
                        key = token2;
                    } else if (cont == 2) {
                        value = token2;
                    }
                }
                key = key.substring(1, key.lastIndexOf("\""));
                if (value.startsWith("\"")) {
                    value = value.substring(1, value.lastIndexOf("\""));
                }
                hParams.put(key, value);
            }
        }
        return hParams;
    }
}