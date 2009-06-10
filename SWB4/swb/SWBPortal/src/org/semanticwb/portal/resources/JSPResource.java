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
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if (dispatcher != null) {
                //System.out.println("forward:"+getResourceBase().getAttribute("forward"));
                if (getResourceBase().getAttribute("forward") != null) {
                    dispatcher.forward(request, response);
                } else {
                    dispatcher.include(request, response);
                }
                //new Exception().printStackTrace();
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
