/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        Resource base = getResourceBase();
        int numrec = Integer.parseInt(base.getAttribute("numrec","10"));
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();

        CommunityActivityUtil cau = new CommunityActivityUtil();
        Iterator<CommunityActivity> itca = cau.getMemberActivities(user);
        out.println("<div id=\"contactos\">");
        out.println("<h2>Actividades</h2>");
        out.println("<ul>");
        CommunityActivity ca = null;
        MicroSiteElement mse = null;
        MicroSite ms = null;
        if (itca.hasNext()) {
            int num = 0;
            while (itca.hasNext()) {
                num++;
                if (num > numrec) {
                    break;
                }
                ca = itca.next();
                user = ca.getUser();
                mse = ca.getElement();
                ms = ca.getCommunity();
                out.println("<li><a class=\"contactos_nombre\" href=\""+mse.getURL()+"\">" + mse.getDisplayTitle(user.getLanguage()) + "");
                out.println("("+mse.getSemanticObject().getSemanticClass().getName()+")</a>");
                out.println("<a class=\"contactos_nombre\" href=\"#\">"+SWBUtils.TEXT.getTimeAgo(mse.getUpdated(),user.getLanguage()) + "</a></li>");
            }
        } else {
            out.println("<li>No hay actividades que reportar.</li>");
        }
        out.println("</ul>");
        out.println("</div>");
        

    }

}
