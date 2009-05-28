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
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.comm.MicroSite;
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
        User user = paramsRequest.getUser();
        String contID = base.getAttribute("containerID","Comunidades");
        if(null==contID)return;
        WebSite ws = paramsRequest.getTopic().getWebSite();
        WebPage wpCont = ws.getWebPage(contID);
        WebPage wp = paramsRequest.getTopic();
        PrintWriter out = response.getWriter();
        //out.println("<ul class=\"comunidades\">");
        Iterator<WebPage> itwp = wpCont.listChilds();
        //out.println("<li>Tiene childs: "+itwp.hasNext()+"</li>"); //debug
        while(itwp.hasNext())
        {
            WebPage wpo = itwp.next();
            //out.println("<li>debug: "+wpo.getURI()+"</li>");
            if(wpo instanceof MicroSite)
            {

                MicroSite wpr = (MicroSite)wpo;
                //out.println("<li>debug OrganizationComm: "+wpr.getTitle()+"</li>");
                WebPage wpAbout = wpr.getAbout();
                if(wpAbout!=null&&wpAbout.equals(wp))
                {
                    //out.println("<li>debug OrganizationComm belong: "+wpr.getId()+"</li>");
                    out.println("<li><a href=\""+wpr.getUrl()+"\">"+wpr.getDisplayName()+"</a></li>");
                }
            }
        }
        out.println("</ul>");
    }
}
