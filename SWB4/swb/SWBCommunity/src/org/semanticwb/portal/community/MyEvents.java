/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class MyEvents extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(MyEvents.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //String mode = paramRequest.getArgument("mode");
        String action = request.getParameter("act");
        String mode = request.getParameter("mode");
        String url = "";
        Resourceable rsa = paramRequest.getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
                url = ((WebPage) rsa).getUrl();
                System.out.println("url on resource.."+url);
        }
        String path = SWBPlatform.getContextPath() + "/swbadmin/jsp/microsite/perfil/myEvents.jsp";
        if (action == null) action = "view";
        
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
            path = SWBPlatform.getContextPath() + "/swbadmin/jsp/microsite/perfil/myEventsCalendar.jsp";
        } else if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
            if (mode != null && mode.equals("calendar")) {
                path = SWBPlatform.getContextPath() + "/swbadmin/jsp/microsite/perfil/myEventsCalendar.jsp";
            }
        }
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("admurl", url);
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        } catch (Exception e) {
            log.error("MyEvents say " + e);
        }        
    }
}
