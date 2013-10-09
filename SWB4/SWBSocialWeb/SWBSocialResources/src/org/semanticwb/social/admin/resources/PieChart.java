/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
                request.setAttribute("suri", request.getParameter("suri"));
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

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("InfographBar")) {
            doPreview(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("showGraphBar") ||  paramRequest.getMode().equals("anioMes")) {
            showGraphBar(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }    
    }
    
    private void doPreview(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/graphBarFilter.jsp");
        request.setAttribute("suri", request.getParameter("suri"));
        request.setAttribute("selected", request.getParameter("selected"));
        request.setAttribute("selectAnio2", request.getParameter("selectAnio2"));
        request.setAttribute("selectMes", request.getParameter("selectMes"));
        request.setAttribute("paramRequest", paramRequest);        
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error  " + ex.getMessage());
        }
    }

    private void showGraphBar(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException, SWBResourceException {
        PrintWriter out=response.getWriter();
             
        if (request.getParameter("doViewGraph") == null) {
            doEditGraph(request, response, paramRequest);
            return;
        }
        
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/showGraphBar.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("suri", request.getParameter("suri"));
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }
    
     public void doEditGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       PrintWriter out=response.getWriter();

         String selectedanio = request.getParameter("selectedAnio")== null ? "": request.getParameter("selectedAnio");
         String selectAnio = request.getParameter("selectAnio")== null ? "": request.getParameter("selectAnio");
         String selectMes = request.getParameter("selectMes")== null ? "": request.getParameter("selectMes");
         out.println("<iframe  id=\"inneriframe\" src=\""+paramRequest.getRenderUrl().setMode("showGraphBar").setParameter("doViewGraph", "1").setParameter("suri", request.getParameter("suri")).setParameter("selectedAnio", selectedanio).setParameter("selectAnio", selectAnio).setParameter("selectMes", selectMes) +"\"  frameborder=\"0\" width=\"100%\"   marginheight=\"0\" marginwidth=\"0\"  scrolling=\"no\"></iframe>"); //frameborder=\"0\" style=\"overflow:hidden;overflow-x:hidden;overflow-y:hidden;height:100%;width:100%;position:absolute;top:0px;left:0px;right:0px;bottom:0px\" height=\"100%\" width=\"100%\" ></iframe> ");
                                                                                                                                                                                                                                                                                                                                                                     

     }

     
     
    
}
