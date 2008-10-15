/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */
/*
 * JSPResource.java
 *
 * Created on 21 de noviembre de 2004, 12:39 PM
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.lib.WBResponse;

/** Este recurso permite ejecutar un archivo jsp, dando la ruta del archivo a
 * ejecutar dentro del sitio, pudiendose presentar como contenido o como un
 * recurso.
 *
 * This resource allows to execute a file jsp, giving the file path to execute
 * within the site, being able to present like content or like resource.
 *
 * @author Javier Solis Gonzalez
 */
public class JSPResource extends GenericAdmResource 
{
    private static Logger log = SWBUtils.getLogger(JSPResource.class);
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = getResourceBase().getAttribute("jsppath");
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if (dispatcher != null) {
                //System.out.println("forward:"+getResourceBase().getAttribute("forward"));
                if (getResourceBase().getAttribute("forward") != null) {
                    dispatcher.forward(request, response);
                } else {
                    dispatcher.include(request, response);
                }
            }
        } catch (Exception e) {
            log.error(paramRequest.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }

    public void processAction(HttpServletRequest request, SWBActionResponse actionResponse) throws SWBResourceException, IOException
    {            
        String path = getResourceBase().getAttribute("jspactpath");
        if (path == null) {
            return;
        }
        try {
            request.setAttribute("actionResponse", actionResponse);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if (getResourceBase().getAttribute("forward") != null) {
                dispatcher.forward(request, new WBResponse());
            } else {
                dispatcher.include(request, new WBResponse());
            }
        } catch (Exception e) {
            log.error(actionResponse.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }
}
