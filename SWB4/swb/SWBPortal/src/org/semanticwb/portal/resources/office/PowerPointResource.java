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
import org.semanticwb.model.User;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


/**
 *
 * @author victor.lorenzana
 */
public class PowerPointResource extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(PowerPointResource.class);
    

    protected void beforePrintDocument(org.semanticwb.resource.office.PPTResource porlet, PrintWriter out)
    {
        
    }

    protected void afterPrintDocument(org.semanticwb.resource.office.PPTResource porlet, PrintWriter out)
    {
        
    }

    protected void printDocument(org.semanticwb.resource.office.PPTResource porlet, PrintWriter out, String path,String workpath,String html,SWBParamRequest paramReq)
    {
        try
        {
            out.write("<iframe src=\"" + path + "\">"+paramReq.getLocaleString("frameNotsupport")+"</iframe>");
        }
        catch(Exception e)
        {
            out.write("<iframe src=\"" + path + "\">This navigator does not support iframe</iframe>");
        }
    }

    @Override
    public final void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        if (this.getResourceBase() instanceof org.semanticwb.resource.office.PPTResource)
        {
            org.semanticwb.resource.office.PPTResource portlet = (org.semanticwb.resource.office.PPTResource) this.getResourceBase();
            String version = portlet.getVersionToShow();
            String contentId = portlet.getContent();
            String repositoryName = portlet.getRepositoryName();
            OfficeDocument document = new OfficeDocument();
            try
            {
                User user = paramReq.getUser();
                String file = document.getContentFile(repositoryName, contentId, version, user);
                if (file != null)
                {
                    String path = SWBPlatform.getWebWorkPath();
                    if (path.endsWith("/"))
                    {
                        path = path.substring(0, path.length() - 1);
                        path += getResourceBase().getWorkPath() + "/" + "frame.html";
                    }
                    else
                    {
                        path += getResourceBase().getWorkPath() + "/" + "frame.html";
                    }                    
                    PrintWriter out = response.getWriter();
                    beforePrintDocument(portlet, out);
                    String workpath = SWBPlatform.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                    printDocument(portlet, out, path,workpath,"",paramReq);
                    afterPrintDocument(portlet, out);
                    out.close();
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }
}
