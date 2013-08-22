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
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.SocialNetworkUser;

/**
 *
 * @author jorge.jimenez
 */
public class StreamMap extends GenericAdmResource{
    
    private static Logger log = SWBUtils.getLogger(StreamMap.class);
    public static final String Mode_SHOWMARKERDETAILS = "showMarkDet";
    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if (Mode_SHOWMARKERDETAILS.equals(mode)) {
            doShowDetails(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    
     @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       if(request.getParameter("doView")==null) {
           doEdit(request, response, paramRequest);
           return;
       }
       final String myPath = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/stream/streamMap.jsp";
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
     
    /*
     * Muestra detalles de un marcador
     */ 
    public void doShowDetails(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       try {
           String postUri=request.getParameter("postUri");
           if(postUri==null) return;
           SemanticObject semObj=SemanticObject.getSemanticObject(postUri);
           PostIn postIn=(PostIn)semObj.getGenericInstance();
           SocialNetworkUser swbSocialUser=postIn.getPostInSocialNetworkUser();
           if(swbSocialUser==null) return;
           final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/review/userHistory.jsp";
           if (request != null) {
               RequestDispatcher dis = request.getRequestDispatcher(path);
               if (dis != null) {
                   try {
                       request.setAttribute("swbSocialUser", swbSocialUser.getSemanticObject());
                       request.setAttribute("paramRequest", paramRequest);
                       dis.include(request, response);
                   } catch (Exception e) {
                       log.error(e);
                       e.printStackTrace(System.out);
                   }
               }
           }

       } catch (Exception e) {
           log.error(e);
       }
   }
   
    
}
