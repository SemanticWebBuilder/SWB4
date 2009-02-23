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
import org.semanticwb.portlet.office.ExcelPortlet;

/**
 *
 * @author victor.lorenzana
 */
public class ExcelResource extends GenericAdmResource{

    private static Logger log = SWBUtils.getLogger(ExcelResource.class);
    public static final String WITH="100%"; // VALUE WIDTH  BY DEFAULT
    public static final String HEIGHT="500"; // VALUE HEIGHT BY DEFAULT
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        if (this.getResourceBase() instanceof ExcelPortlet)
        {
            ExcelPortlet portlet = (ExcelPortlet) this.getResourceBase();
            String version = portlet.getVersionToShow();
            String contentId = portlet.getContent();
            String repositoryName = portlet.getRepositoryName();
            OfficeDocument document = new OfficeDocument();            
            try
            {
                PrintWriter out = response.getWriter();
                String file = document.getContentFile(repositoryName, contentId, version,paramReq.getUser());
                if (file != null)
                {

                    file = file.replace(".xls", ".html");
                    String path = SWBPlatform.getWebWorkPath();
                    if (path.endsWith("/"))
                    {
                        path = path.substring(0, path.length() - 1);
                        path += getResourceBase().getWorkPath() + "/" + file;
                    }
                    else
                    {
                        path += getResourceBase().getWorkPath() + "/" + file;
                    }

                    String with=WITH;
                    String height=HEIGHT;
                    out.println("<iframe frameborder=\"0\" src=\""+ path +"\" width=\""+with+"\" height=\""+height+"\"></iframe>");
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }

        }
    }
}
