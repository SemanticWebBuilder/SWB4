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
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.YoutubeGC;
import org.semanticwb.social.SocialAdmin;

/**
 *
 * @author jorge.jimenez
 */
public class YoutubeGCResource extends GenericResource{
    
    private static Logger log = SWBUtils.getLogger(YoutubeGCResource.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        String suri=request.getParameter("suri");
        
        out.println("<script type=\"javascript\">");
        if (request.getParameter("statusMsg") != null) {
            out.println("   showStatus('" + request.getParameter("statusMsg") + "');");
        }
        if (request.getParameter("reloadTap") != null) {
            out.println(" reloadTab('" + suri + "'); ");
        }
        out.println("</script>");

        
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/oauth/youtubegc.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
                ex.printStackTrace(System.out);
            }
        }
    }
    
      @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
          
        setProperties(request, response);

        response.setRenderParameter("statusMsg", response.getLocaleString("saveUserExtAttr"));
        response.setRenderParameter("suri", request.getParameter("suri"));
        response.setRenderParameter("reloadTap", request.getParameter("suri"));
        response.setMode(response.Mode_VIEW);
        //response.setCallMethod(response.Call_CONTENT);
    }
      
      
    private void setProperties(HttpServletRequest request, SWBActionResponse response) {
        //User user=response.getUser();
        //Tomando en cuenta que todos los usuarios que se modificaran en sus propiedades extendidas, sean usuarios del repositorio de Admin.
        SocialAdmin wsite=(SocialAdmin)SWBContext.getAdminWebSite(); 

        if(request.getParameter("appKey")!=null && !request.getParameter("appKey").isEmpty() && request.getParameter("secretKey")!=null && !request.getParameter("secretKey").isEmpty() && request.getParameter("devKey")!=null && !request.getParameter("devKey").isEmpty())
        {
           if(request.getParameter("youtubeGCid")!=null)
           {
               YoutubeGC yourubeGC=YoutubeGC.ClassMgr.getYoutubeGC(request.getParameter("youtubeGCid"), wsite);
               if(yourubeGC!=null)
               {
                   wsite.setAdm_youtubegc(null);
                   yourubeGC.remove();
               }
           } 
           YoutubeGC yourubeGC=YoutubeGC.ClassMgr.createYoutubeGC(wsite);
           yourubeGC.setAppKey(request.getParameter("appKey"));
           yourubeGC.setSecretKey(request.getParameter("secretKey"));
           yourubeGC.setDeveloperKey(request.getParameter("devKey"));
           wsite.setAdm_youtubegc(yourubeGC);
        }else if(request.getParameter("youtubeGCid")!=null) {
               YoutubeGC yourubeGC=YoutubeGC.ClassMgr.getYoutubeGC(request.getParameter("youtubeGCid"), wsite);
               if(yourubeGC!=null)
               {
                   wsite.setAdm_youtubegc(null);
                   yourubeGC.remove();
               }
        } 
    }  
}
