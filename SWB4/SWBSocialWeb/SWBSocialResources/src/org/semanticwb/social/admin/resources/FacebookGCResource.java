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
import org.semanticwb.social.SocialAdmin;
import org.semanticwb.social.FacebookGC;

/**
 *
 * @author jorge.jimenez
 */
public class FacebookGCResource extends GenericResource{
    
    /*
    private static Logger log = SWBUtils.getLogger(FacebookGCResource.class);
    
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

        
        final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/oauth/facebookgc.jsp";
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

        if(request.getParameter("appKey")!=null && !request.getParameter("appKey").isEmpty() && request.getParameter("secretKey")!=null && !request.getParameter("secretKey").isEmpty())
        {
           if(request.getParameter("facebookGCid")!=null)
           {
               FacebookGC facebookGC=FacebookGC.ClassMgr.getFacebookGC(request.getParameter("facebookGCid"), wsite);
               if(facebookGC!=null)
               {
                   wsite.setAdm_facebookgc(null);
                   facebookGC.remove();
               }
           } 
           FacebookGC facebookGC=FacebookGC.ClassMgr.createFacebookGC(wsite);
           facebookGC.setAppKey(request.getParameter("appKey"));
           facebookGC.setSecretKey(request.getParameter("secretKey"));
           wsite.setAdm_facebookgc(facebookGC);
        }else if(request.getParameter("facebookGCid")!=null) {
               FacebookGC facebookGC=FacebookGC.ClassMgr.getFacebookGC(request.getParameter("facebookGCid"), wsite);
               if(facebookGC!=null)
               {
                   wsite.setAdm_facebookgc(null);
                   facebookGC.remove();
               }
        } 
    }  */
}
