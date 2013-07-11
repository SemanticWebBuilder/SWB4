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
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 * 
 * Clase que se utiliza para publicar en las redes sociales de manera Generica desde el menu superior --"Publicar/En Redes Sociales"--
 * 
 */
public class CreateGenericPost extends CreatePost { 
    
    private static Logger log = SWBUtils.getLogger(CreateGenericPost.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out=response.getWriter();
        if(request.getParameter("statusMsg")!=null)
        {
            out.println("<script type=\"text/javascript\">");
            out.println("   showStatus('"+request.getParameter("statusMsg")+"');");
            /*
            if(request.getParameter("reloadTab")!=null)
            {
                out.println(" reloadTab('" + request.getParameter("reloadTab") + "');");//so
            } */           
            out.println("</script>");
            return;
        }
        
        String basePath=SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/";
        String jspResponse = basePath+"socialSites.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("afterChooseSite")) {
            doSocialTopics(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("genericPost")) {
            doCreateGenericPost(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }


     public void doSocialTopics(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {        
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/socialTopics.jsp");
        request.setAttribute("socialSite", request.getParameter("socialSite"));
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a socialTopis.jsp " + ex.getMessage());
        }
     }
     
     public void doCreateGenericPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {    
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/createPost.jsp");
        request.setAttribute("suri", request.getParameter("suri"));
        request.setAttribute("source", "RedSocial");
        request.setAttribute("paramRequest", paramRequest);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a createPost.jsp " + ex.getMessage());
        }
     }
   
}
