/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.community.utilresources.CommunityActivity;
import org.semanticwb.portal.community.utilresources.CommunityActivityUtil;

/**
 *
 * @author juan.fernandez
 */
public class UserActivitiesResource extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(UserActivitiesResource.class);
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        Resource base = getResourceBase();
        int numrec = Integer.parseInt(base.getAttribute("numrec","10"));
        User user = paramRequest.getUser();
        CommunityActivityUtil cau = new CommunityActivityUtil();
        Iterator<CommunityActivity> activities = cau.getMemberActivities(user);
        String path = "/swbadmin/jsp/microsite/UserActivitiesResource/UserActivitiesResource.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("numrec", numrec);
            request.setAttribute("activities", activities);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

}
