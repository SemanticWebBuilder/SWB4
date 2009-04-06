/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.office.comunication.OfficeServlet;
import org.semanticwb.security.auth.SWB4CallbackHandlerGateWayOffice;
import static org.semanticwb.office.comunication.Base64.*;

/**
 *
 * @author victor.lorenzana
 */
public class GateWayOffice implements InternalServlet
{

    private static String REALM = "Secure Area";
    private static String PREFIX_BASIC = "Basic ";
    private static final String title = "Gateway de Comunicación con Office INFOTEC Semantic WebBuilder 4";
    static Logger log = SWBUtils.getLogger(GateWayOffice.class);
    private OfficeServlet officeServlet = new OfficeServlet()
    {

        public boolean isAuthenticate(String pUserName, String pPassword)
        {
            UserRepository ur = SWBContext.getAdminWebSite().getUserRepository();
            String context = ur.getProperty(UserRepository.SWBUR_LoginContext);
            Subject subject = new Subject();
            LoginContext lc;
            try
            {
                SWB4CallbackHandlerGateWayOffice callbackHandler = new SWB4CallbackHandlerGateWayOffice(pUserName, pPassword);
                lc = new LoginContext(context, subject, callbackHandler);
                lc.login();
                return true;
            }
            catch (Exception e)
            {
                log.debug("Can't log User", e);
            }
            return false;
        }
    };

    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing GatewayOffice...");
        officeServlet.init();
    }

    private void showExcelContent(PrintWriter out, String file, String dir, String contentId, String repositoryName) throws IOException
    {
        file = file.replace(".xls", ".html");
        String path = SWBPlatform.getWebWorkPath();
        if (path.endsWith("/"))
        {
            path = path.substring(0, path.length() - 1);
            path +=
                    dir + "/" + file;
        }
        else
        {
            path += dir + "/" + file;
        }

        String with = "100%";
        String height = "500";
        out.println("<html>");
        out.println("<body>");
        out.println("<iframe frameborder=\"0\" src=\"" + path + "\" width=\"" + with + "\" height=\"" + height + "\">Este navegador no soporta ifrae</iframe>");
        out.println("</body>");
        out.println("</html>");
        out.close();

    }

    private void showPPTContent(PrintWriter out, String file, String dir, String contentId, String repositoryName) throws IOException
    {
        String path = SWBPlatform.getWebWorkPath();
        if (path.endsWith("/"))
        {
            path = path.substring(0, path.length() - 1);
            path +=
                    dir + "/" + "frame.html";
        }
        else
        {
            path += dir + "/" + "frame.html";
        }

        String with = "100%";
        String height = "500";
        out.println("<html>");
        out.println("<body>");
        out.println("<iframe frameborder=\"0\" src=\"" + path + "\" width=\"" + with + "\" height=\"" + height + "\">Este navegador no soporta iframe</iframe>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    private void showWordContent(PrintWriter out, String file, String dir, String contentId, String repositoryName) throws IOException
    {
        file = file.replace(".doc", ".html");
        String path = SWBPlatform.getWorkPath() + dir + "\\" + file;
        StringBuffer html = new StringBuffer();
        File filecontent = new File(path);
        if (filecontent.exists())
        {
            FileInputStream inFile = new FileInputStream(path);
            byte[] buffer = new byte[1024 * 8];
            int read = inFile.read(buffer);
            while (read != -1)
            {
                html.append(new String(buffer, 0, read));
                read =
                        inFile.read(buffer);
            }

            inFile.close();
            if (!dir.endsWith("/"))
            {
                dir += "/";
            }

            String workpath = SWBPlatform.getWebWorkPath() + dir;
            String htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);

            out.write(htmlOut);
            out.close();
        }
        else
        {
            log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + contentId + "@" + repositoryName);
        }

    }

    private static String getPassword(String userpassDecoded) throws IOException
    {
        String password = "";
        String[] values = userpassDecoded.split(":");
        password =
                values[1];
        return password;
    }

    private static String getUserName(String userpassDecoded) throws IOException
    {
        String userName = "";
        String[] values = userpassDecoded.split(":");
        userName =
                values[0];
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
            String contentId = request.getParameter("contentId");
            String versionName = request.getParameter("versionName");
            String repositoryName = request.getParameter("repositoryName");
            String dir = request.getParameter("name");
            if (contentId == null || versionName == null || repositoryName == null || dir == null)
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
                String authorization = request.getHeader("Authorization");
                if (authorization == null || authorization.equals(""))
                {
                    response.setHeader("WWW-Authenticate", PREFIX_BASIC + "realm=\"" + REALM + "\"");
                    response.setStatus(response.SC_UNAUTHORIZED);
                    return;

                }
                else
                {
                    if (authorization.startsWith(PREFIX_BASIC))
                    {
                        String userpassEncoded = authorization.substring(6);
                        String userpassDecoded = new String(decode(userpassEncoded));
                        String pUserName = getUserName(userpassDecoded);
                        String pPassword = getPassword(userpassDecoded);
                        officeServlet.checkSecurity(request, response);
                        OfficeDocument doc = new OfficeDocument();
                        doc.setUser(pUserName);
                        doc.setPassword(pPassword);
                        dir =
                                "/" + dir;
                        PrintWriter out = response.getWriter();

                        try
                        {
                            String file = doc.getContentFile(repositoryName, contentId, versionName);
                            String type = doc.getContentType(repositoryName, contentId);
                            if (file != null)
                            {
                                if (type.equalsIgnoreCase("word"))
                                {
                                    showWordContent(out, file, dir, contentId, repositoryName);
                                }
                                else if (type.equalsIgnoreCase("excel"))
                                {
                                    showExcelContent(out, file, dir, contentId, repositoryName);
                                }
                                else if (type.equalsIgnoreCase("ppt"))
                                {
                                    showPPTContent(out, file, dir, contentId, repositoryName);
                                }
                                else
                                {
                                }
                            }
                        }
                        catch (Exception e)
                        {

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
        }
    }
}
