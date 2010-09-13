/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.office.comunication.OfficeServlet;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.security.auth.SWB4CallbackHandlerGateWayOffice;
import static org.semanticwb.office.comunication.Base64.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GateWayOffice.
 * 
 * @author victor.lorenzana
 */
public class GateWayOffice implements InternalServlet
{

    /** The REALM. */
    private static String REALM = "Secure Area";
    
    /** The PREFI x_ basic. */
    private static String PREFIX_BASIC = "Basic ";
    
    /** The Constant title. */
    private static final String title = "Gateway de Comunicación con Office INFOTEC Semantic WebBuilder 4";
    
    /** The log. */
    static Logger log = SWBUtils.getLogger(GateWayOffice.class);
    
    /** The office servlet. */
    private OfficeServlet officeServlet = new OfficeServlet()
    {

        public boolean isAuthenticate(String pUserName, String pPassword)
        {
            UserRepository ur = SWBContext.getAdminWebSite().getUserRepository();
            //String context = ur.getProperty(UserRepository.SWBUR_LoginContext);
            //MAPS74 cambiando propiedades a semantic prop
            String context = ur.getLoginContext();
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

    /**
     * Inits the.
     * 
     * @param config the config
     * @throws ServletException the servlet exception
     */
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing GatewayOffice...");
        officeServlet.init();
    }

    /**
     * Show excel content.
     * 
     * @param out the out
     * @param file the file
     * @param dir the dir
     * @param contentId the content id
     * @param repositoryName the repository name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void showExcelContent(PrintWriter out, String file, String dir, String contentId, String repositoryName) throws IOException
    {
        file = file.replace(".xls", ".html");
        String path = SWBPortal.getWebWorkPath();
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

    /**
     * Show ppt content.
     * 
     * @param out the out
     * @param file the file
     * @param dir the dir
     * @param contentId the content id
     * @param repositoryName the repository name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void showPPTContent(PrintWriter out, String file, String dir, String contentId, String repositoryName) throws IOException
    {
        String path = SWBPortal.getWebWorkPath();
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

    /**
     * Show word content.
     * 
     * @param out the out
     * @param file the file
     * @param dir the dir
     * @param contentId the content id
     * @param repositoryName the repository name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void showWordContent(PrintWriter out, String file, String dir, String contentId, String repositoryName) throws IOException
    {
        file = file.replace(".doc", ".html");
        String path = SWBPortal.getWorkPath() + dir + "\\" + file;
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
                read =inFile.read(buffer);
            }

            inFile.close();
            if (!dir.endsWith("/"))
            {
                dir += "/";
            }

            String workpath = SWBPortal.getWebWorkPath() + dir;
            String htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);

            out.write(htmlOut);
            out.close();
        }
        else
        {
            log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + contentId + "@" + repositoryName);
        }

    }

    /**
     * Gets the password.
     * 
     * @param userpassDecoded the userpass decoded
     * @return the password
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static String getPassword(String userpassDecoded) throws IOException
    {
        String password = "";
        String[] values = userpassDecoded.split(":");
        password =
                values[1];
        return password;
    }

    /**
     * Gets the user name.
     * 
     * @param userpassDecoded the userpass decoded
     * @return the user name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static String getUserName(String userpassDecoded) throws IOException
    {
        String userName = "";
        String[] values = userpassDecoded.split(":");
        userName =
                values[0];
        return userName;
    }

    /**
     * Do process.
     * 
     * @param request the request
     * @param response the response
     * @param dparams the dparams
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
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
                out.println("<h2>Version API: " + IOfficeApplication.version + "</h2>");
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
                        dir ="/" + dir;
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
                        out.println("<h2>Version API: " + IOfficeApplication.version + "</h2>");
                        out.println("</body>");
                        out.println("</html>");
                        out.close();
                    }
                }
            }
        }
    }

    
}
