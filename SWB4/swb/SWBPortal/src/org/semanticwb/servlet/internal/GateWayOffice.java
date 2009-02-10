/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.office.comunication.OfficeServlet;
import org.semanticwb.portlet.office.OfficePortlet;

/**
 *
 * @author victor.lorenzana
 */
public class GateWayOffice implements InternalServlet
{

    private static final String title = "Gateway de Comunicación con Office INFOTEC Semantic WebBuilder 4";
    static Logger log = SWBUtils.getLogger(GateWayOffice.class);
    private static String REALM = "Secure Area";
    private static String PREFIX_BASIC = "Basic ";
    private OfficeServlet officeServlet = new OfficeServlet()
    {

        public boolean isAuthenticate(String pUserName, String pPassword)
        {
            //Todo:
            return true;
        }
    };

    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing GatewayOffice...");
        officeServlet.init();
    }

    private static String getPassword(String userpassDecoded) throws IOException
    {
        String password = "";
        String[] values = userpassDecoded.split(":");
        password = values[1];
        return password;
    }

    private static String getUserName(String userpassDecoded) throws IOException
    {
        String userName = "";
        String[] values = userpassDecoded.split(":");
        userName = values[0];
        return userName;
    }

    

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        if (request.getMethod().toLowerCase().equals("post"))
        {
            officeServlet.doPost(request, response);
        }
        else
        {

            //officeServlet.doGet(request, response);
            String contentId = request.getParameter("contentId");
            String versionName = request.getParameter("versionName");
            String repositoryName = request.getParameter("repositoryName");
            String dir = request.getParameter("name");
            if (contentId == null || versionName == null || repositoryName == null)
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
            else
            {
                officeServlet.checkSecurity(request, response);
                OfficeDocument doc = new OfficeDocument();
                doc.setUser("");
                doc.setPassword("");
                dir="/"+dir;
                try
                {                    
                    String file = doc.getContentFile(repositoryName, contentId, versionName);
                    String type = doc.getContentType(repositoryName, contentId, versionName);
                    if (file != null)
                    {
                        if(type.equalsIgnoreCase("word"))
                        {
                            file = file.replace(".doc", ".html");                            
                        }
                        String path = SWBPlatform.getWorkPath()+dir + "\\" + file;
                        StringBuffer html = new StringBuffer();
                        File filecontent = new File(path);
                        if (filecontent.exists())
                        {
                            FileInputStream inFile = new FileInputStream(path);
                            byte[] buffer = new byte[1024*8];
                            int read = inFile.read(buffer);
                            while (read != -1)
                            {
                                html.append(new String(buffer, 0, read));
                                read = inFile.read(buffer);
                            }
                            inFile.close();
                            if(!dir.endsWith("/"))
                            {
                                dir+="/";
                            }
                            String workpath = SWBPlatform.getWebWorkPath()+dir;
                            String htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                            PrintWriter out = response.getWriter();
                            out.write(htmlOut);
                            out.close();
                        }
                        else
                        {
                            //log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + portlet.getContent() + "@" + portlet.getRepositoryName());
                        }
                    }                    
                }
                catch (Exception e)
                {
                    PrintWriter out = response.getWriter();
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>" + title + "</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>" + e.getLocalizedMessage() + "</h1>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                }
                finally
                {
                   // OfficePortlet.clean(dir);
                }
            }

        }
    }
}
