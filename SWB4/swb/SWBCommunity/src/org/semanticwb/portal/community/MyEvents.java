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
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class MyEvents extends GenericResource {

    private static Logger log = SWBUtils.getLogger(MyEvents.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getArgument("mode");
        String action = request.getParameter("act");
        if (action == null) action = "view";
        String path = SWBPlatform.getContextPath() + "/swbadmin/jsp/microsite/perfil/myEvents.jsp";
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            if (mode != null && mode.equals("calendar")) {
                path = SWBPlatform.getContextPath() + "/swbadmin/jsp/microsite/perfil/myEventsCalendar.jsp";
            } else if (action.equals("dayli")) {
                path = SWBPlatform.getContextPath() + "/swbadmin/jsp/microsite/perfil/myDailyEvents.jsp";
            }
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
