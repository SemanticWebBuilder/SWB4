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
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.community.utilresources.CommunityActivity;
import org.semanticwb.portal.community.utilresources.CommunityActivityUtil;

/**
 *
 * @author juan.fernandez
 */
public class CommunityActivitiesResource extends GenericAdmResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm");
        Resource base = getResourceBase();
        int numrec = Integer.parseInt(base.getAttribute("numrec","10"));
        PrintWriter out = response.getWriter();
        WebPage wp = paramRequest.getWebPage();
        if(wp instanceof MicroSite)
        {
            MicroSite ms = (MicroSite)wp;
            CommunityActivityUtil cau = new CommunityActivityUtil();
            Iterator<CommunityActivity> itca = cau.getCommunityActivities(ms);
            out.println("<div id=\"actividades\">");
            out.println("<h2>Actividades</h2>");

            out.println("<ul>");
            CommunityActivity ca = null;
            User user = null;
            MicroSiteElement mse = null;
            if(itca.hasNext())
            {
                int num = 0;
                while(itca.hasNext())
                {
                    num++;
                    if (num > numrec) {
                        break;
                    }

                    ca = itca.next();
                    user = ca.getUser();
                    mse = ca.getElement();
                    out.println("<li>El elemento " + mse.getDisplayTitle(null) );
                    out.println(" fué modificado por ");
                    out.println("" + user.getFullName() + "");
                    out.println(" el día " + sdf.format(mse.getUpdated()) + "</li>");
                    
                }
            }
            else
                out.println("<li>No hay actividades.</li>");
            out.println("</ul>");
            out.println("</div>");
        }

    }

}
