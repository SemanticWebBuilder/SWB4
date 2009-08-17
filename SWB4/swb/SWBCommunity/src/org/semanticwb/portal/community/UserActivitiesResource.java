/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm");
        Resource base = getResourceBase();
        int numrec = Integer.parseInt(base.getAttribute("numrec","10"));
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();

        CommunityActivityUtil cau = new CommunityActivityUtil();
        Iterator<CommunityActivity> itca = cau.getMemberActivities(user);
        out.println("<div id=\"UserActivitiesResource\">");
        out.println("<p>");
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
                out.println("El elemento <strong>" + mse.getDisplayTitle(null) + "</strong>");
                out.println(" de la comunidad ");
                out.println("<strong>" + ms.getDisplayTitle(null) + "</strong>");
                out.println(" fue modificado el día <strong>" + sdf.format(mse.getUpdated()) + "</strong>");
                if (itca.hasNext() && num <= numrec) {
                    out.println("<br/>");
                }
            }
        } else {
            out.println("No hay actividades que reportar.");
        }
        out.println("</p>");
        out.println("</div>");
        

    }

}
