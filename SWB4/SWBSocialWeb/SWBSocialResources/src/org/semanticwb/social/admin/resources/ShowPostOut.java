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
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PhotoIn;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.VideoIn;

/**
 *
 * @author jorge.jimenez
 */
public class ShowPostOut extends GenericResource {
    
    private static Logger log = SWBUtils.getLogger(ShowPostOut.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(request.getParameter("wsite")==null || request.getParameter("postOut")==null) return;
        PrintWriter out = response.getWriter();
        WebSite wsite=WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
        PostOut postOut=PostOut.ClassMgr.getPostOut(request.getParameter("postOut"), wsite); 
        
        final String myPath = SWBPlatform.getContextPath() + "/work/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/socialTopicMsgIn.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(myPath);
        if (dis != null) {
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("wsite", wsite);
                request.setAttribute("postOut", postOut);
                dis.include(request, response);
            } catch (Exception ex) {
                log.error(ex);
                ex.printStackTrace(System.out);
            }
        }
    }

  
    
}
