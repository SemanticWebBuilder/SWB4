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
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author hasdai
 */
public class EventsGeneralCalendar extends GenericResource {
    private static Logger log = SWBUtils.getLogger(EventsGeneralCalendar.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/microsite/EventResource/allEventsCalendar.jsp";
        String action = request.getParameter("act");

        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
            path = "/swbadmin/jsp/microsite/EventResource/allEventsCalendar.jsp";
        }
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }


}
