/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.office;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portlet.office.PPTPortlet;

/**
 *
 * @author victor.lorenzana
 */
public class PowerPointResource extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(PowerPointResource.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        if (this.getResourceBase() instanceof PPTPortlet)
        {
            PPTPortlet portlet = (PPTPortlet) this.getResourceBase();
            String version = portlet.getVersionToShow();
            String contentId = portlet.getContent();
            String repositoryName = portlet.getRepositoryName();
            OfficeDocument document = new OfficeDocument();
            try
            {
                PrintWriter out = response.getWriter();
                String file = document.getContentFile(repositoryName, contentId, version);
                if (file != null)
                {
                    String path = SWBPlatform.getWebWorkPath();
                    if (path.endsWith("/"))
                    {
                        path = path.substring(0, path.length() - 1);
                        path += getResourceBase().getWorkPath() + "\\" + "frame.html";
                    }
                    else
                    {
                        path += getResourceBase().getWorkPath() + "\\" + "frame.html";
                    }

                    out.println("<iframe frameborder=\"0\" src=\""+ path +"\" width=\"100%\" height=\"300\">");
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }
}
