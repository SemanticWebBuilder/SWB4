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
 
/*
 * JSPResource.java
 *
 * Created on 21 de noviembre de 2004, 12:39 PM
 */
package com.infotec.wb.resources;

import com.infotec.wb.lib.WBParamRequestImp;
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
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.lib.SWBResponse;

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
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = getResourceBase().getAttribute("jsppath");
        try {
            request.setAttribute("paramRequest", new WBParamRequestImp((SWBParamRequestImp)paramRequest));
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

    @Override
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
                dispatcher.forward(request, new SWBResponse());
            } else {
                dispatcher.include(request, new SWBResponse());
            }
        } catch (Exception e) {
            log.error(actionResponse.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }
}
