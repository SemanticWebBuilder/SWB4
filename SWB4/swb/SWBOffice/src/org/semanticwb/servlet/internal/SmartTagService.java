/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.office.comunication.OfficeSmartTagServlet;
import org.semanticwb.office.interfaces.IOfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class SmartTagService implements InternalServlet
{

    private static final String title = "Gateway de Comunicación para etiquetas inteligentes";
    private static Logger log = SWBUtils.getLogger(SmartTagService.class);
    private OfficeSmartTagServlet officeServlet = new OfficeSmartTagServlet()
    {

        public boolean isAuthenticate(String pUserName, String pPassword)
        {
            return true;
        }
    };

    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing SmartTagService...");
        officeServlet.init();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        if (request.getMethod().toLowerCase().equals("post"))
        {
            officeServlet.doPost(request, response);
        }
        else
        {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + title + "</h1>");            
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}
