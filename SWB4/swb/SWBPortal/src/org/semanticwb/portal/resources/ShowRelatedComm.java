/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.comm.OrganizationComm;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class ShowRelatedComm extends GenericAdmResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        String contID = base.getAttribute("containerID","Comunidades");
        if(null==contID)return;
        WebSite ws = paramsRequest.getTopic().getWebSite();
        WebPage wpCont = ws.getWebPage(contID);
        WebPage wp = paramsRequest.getTopic();
        PrintWriter out = response.getWriter();
        out.println("<ul class=\"comunidades\">");
        Iterator<GenericObject> itwp = wpCont.listRelatedObjects();
        while(itwp.hasNext())
        {
            GenericObject go = itwp.next();
            if(go instanceof OrganizationComm)
            {
                OrganizationComm wpr = (OrganizationComm)go;
                if(wpr.getAbout().getId().equals(wp.getId()))
                {
                    out.println("<li><a href=\""+wpr.getUrl()+"\">"+wpr.getDisplayName()+"</a></li>");
                }
            }
        }
        out.println("</ul>");
    }
}
