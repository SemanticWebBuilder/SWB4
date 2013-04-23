/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.xmlrpc;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.servlet.internal.DistributorParams;
import org.semanticwb.servlet.internal.InternalServlet;
import org.semanticwb.xmlrpc.XMLRPCServlet;

/**
 *
 * @author victor.lorenzana
 */
public class XMLRPCProcessInternalServlet implements InternalServlet
{

    static Logger log = SWBUtils.getLogger(XMLRPCProcessInternalServlet.class);
    ProcessServlet servletContext = new ProcessServlet();

    @Override
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing XMLRPCProcessServlet...");
        servletContext.init("org.semanticwb.process.xmlrpc.");
        ProcessServlet.addMappingType("RPCProcess", RPCProcessImp.class);
    }

    @Override
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        if (request.getMethod().toLowerCase().equals("post"))
        {
            servletContext.doPost(request, response);
        }
        else
        {
            if (request.getParameter("wsdl") != null)
            {
                servletContext.doWDSL(request, response);

                return;
            }
        }
    }
}
