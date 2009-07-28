/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.social.twitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import twitter4j.Status;
import twitter4j.Twitter;

/**
 *
 * @author Jorge Jiménez
 */
public class TwitterResource extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(PruebaTwitter.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out=response.getWriter();
            Twitter twitter = new Twitter("george190475@hotmail.com","george24");
            List<Status> statuses = twitter.getFriendsTimeline();
            out.println("Showing friends timeline.");
            for (Status status : statuses) {
                out.println(status.getUser().getName() + ":" +
                                   status.getText()+"<br>");
            }
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/swbadmin/jsp/twitter/twitter.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        try {
            Twitter twitter = new Twitter("george190475@hotmail.com", "george24");
            twitter.updateStatus(request.getParameter("status"));
        } catch (Exception e) {
            log.error(e);
        }
    }
}
