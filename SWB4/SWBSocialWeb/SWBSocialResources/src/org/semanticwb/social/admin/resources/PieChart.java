/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jorge.jimenez
 */
public class PieChart extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(PieChart.class);

     @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       if(request.getParameter("doView")==null) {
           doEdit(request, response, paramRequest);
           return;
       }
       final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/pieChart.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }
     
     @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       PrintWriter out=response.getWriter();
       out.println("<iframe width=\"100%\" height=\"100%\" src=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW).setParameter("doView", "1").setParameter("suri", request.getParameter("suri")) +"\"></iframe> ");
    }
    
}
