package org.semanticwb.process.resources.tracer;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;

public class ProcessTracer extends org.semanticwb.process.resources.tracer.base.ProcessTracerBase 
{
    public static Logger log = SWBUtils.getLogger(ProcessTracer.class);
    
    public ProcessTracer() {}

   /**
   * Constructs a ProcessTracer with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessTracer
   */
    public ProcessTracer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/tracer/listProcessInstances.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("statusWP", getDisplayMapPage());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ProcessTracer: Error including view JSP " + jsp, e);
        }
    }
}
