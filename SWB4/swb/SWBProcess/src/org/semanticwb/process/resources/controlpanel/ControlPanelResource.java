package org.semanticwb.process.resources.controlpanel;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;

/***
 * Recurso Panel de Control para monitoreo de instancias de procesos.
 * @author hasdai
 */
public class ControlPanelResource extends org.semanticwb.process.resources.controlpanel.base.ControlPanelResourceBase 
{
    private static Logger log = SWBUtils.getLogger(ControlPanelResource.class);
    public static final int SORT_DATE = 1;
    public static final int SORT_NAME = 2;

    public ControlPanelResource()
    {
    }

   /**
   * Constructs a ControlPanelResource with a SemanticObject
   * @param base The SemanticObject with the properties for the ControlPanelResource
   */
    public ControlPanelResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String jsp = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/businessControlPanel.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);

        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("config")) {
            doConfig(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action.equals("setConfig")) {

        } else {
            super.processAction(request, response);
        }
    }

    public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/businessControlPanel.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);

        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
