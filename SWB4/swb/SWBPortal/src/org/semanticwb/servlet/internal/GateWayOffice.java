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
//import org.semanticwb.office.comunication.OfficeServlet;

/**
 *
 * @author victor.lorenzana
 */
public class GateWayOffice implements InternalServlet
{
    private static final String title="Gateway de Comunicación con Office INFOTEC Semantic WebBuilder 4";
    static Logger log = SWBUtils.getLogger(GateWayOffice.class);
//    OfficeServlet officeServlet = new OfficeServlet()
//    {
//
//        public boolean isAuthenticate(String pUserName, String pPassword)
//        {
//            //Todo:
//            return true;
//        }
//    };

    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing GatewayOffice...");
//        officeServlet.init();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {        
        if (request.getMethod().toLowerCase().equals("post"))
        {
//            officeServlet.doPost(request, response);
        }
        else
        {

            PrintWriter out=response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>"+ title +"</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>"+ title +"</h1>");
            out.println("</body>");
            out.println("</html>");
            out.close();

        }
    }
}
