/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleSiteMap.
 * 
 * @author serch
 */
public class RobotFile implements InternalServlet {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(RobotFile.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing InternalServlet robot.txt...");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest req, HttpServletResponse resp, DistributorParams dparams) throws IOException, ServletException
    {
        String modelid=dparams.getModelId();
        String ret=SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath()+"/models/"+modelid+"/config/robots.txt");
        if(ret==null)
        {
            ret=SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath()+"/config/robots.txt");
        }
        if(ret!=null)
        {
            resp.setContentType("text/plain");
            resp.getWriter().print(ret);
        }else
        {
            resp.sendError(404);
        }
    }

}
