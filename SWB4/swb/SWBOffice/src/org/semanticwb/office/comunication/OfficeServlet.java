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
package org.semanticwb.office.comunication;

import java.io.*;
import java.util.UUID;
import javax.servlet.*;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.resource.office.sem.OfficeResource;
import org.semanticwb.xmlrpc.XMLRPCServlet;
import static org.semanticwb.office.comunication.Base64.*;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeServlet extends XMLRPCServlet
{

    private static final String title = "Gateway de Comunicacion con Office INFOTEC Semantic WebBuilder 4";
    static Logger log = SWBUtils.getLogger(OfficeServlet.class);
    private static String REALM = "Secure Area";
    private static String PREFIX_BASIC = "Basic ";
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();

    @Override
    public void init() throws ServletException
    {
        log.event("Adding mappingType OfficeDocument...");
        addMappingType("OfficeDocument", OfficeDocument.class);
        log.event("Adding mappingType OfficeApplication...");
        addMappingType("OfficeApplication", OfficeApplication.class);
        RepositoryManagerLoader.getInstance();
        OfficeDocument.registerContents();
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

    public boolean  checkSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        boolean checkSecurity=false;
        try
        {
            String pUserName = null;
            String pPassword = null;
            String authorization = request.getHeader("Authorization");
            if (authorization == null || authorization.equals(""))
            {
                response.setHeader("WWW-Authenticate", PREFIX_BASIC + "realm=\"" + REALM + "\"");
                response.setStatus(response.SC_UNAUTHORIZED);
                return checkSecurity;
            }
            else
            {
                if (authorization.startsWith(PREFIX_BASIC))
                {
                    String userpassEncoded = authorization.substring(6);
                    String userpassDecoded = new String(decode(userpassEncoded));
                    pUserName = getUserName(userpassDecoded);
                    pPassword = getPassword(userpassDecoded);
                    if (!this.isAuthenticate(pUserName, pPassword))
                    {
                        response.sendError(response.SC_FORBIDDEN);
                        return checkSecurity;
                    }
                    else
                    {
                        request.setAttribute("user", pUserName);
                        request.setAttribute("password", pPassword);
                        checkSecurity=true;
                        return checkSecurity;
                    }
                }
                else
                {
                    response.sendError(response.SC_FORBIDDEN);
                }
            }
        }
        catch (IOException ioe)
        {
            throw ioe;
        }
        return checkSecurity;

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(checkSecurity(request, response))
        {
            super.doPost(request, response);
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        checkSecurity(request, response);
        String contentId = request.getParameter("contentId");
        String versionName = request.getParameter("versionName");
        String repositoryName = request.getParameter("repositoryName");
        String type = request.getParameter("type");
        if (contentId == null || versionName == null || repositoryName == null || type == null)
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
            OfficeDocument doc = new OfficeDocument();
            doc.setUser("");
            doc.setPassword("");
            try
            {
                InputStream in = doc.getContent(repositoryName, contentId, versionName);
                String name=UUID.randomUUID().toString();
                String dir=SWBPlatform.getWorkPath()+"/"+name;
                OfficeResource.loadContent(in, dir,type);
                String file = doc.getContentFile(repositoryName, contentId, versionName);
                if (file != null)
                {

                    file = file.replace(".doc", ".html");
                    file = file.replace(".odt", ".html");
                    String path = dir+ "\\" + file;
                    StringBuffer html = new StringBuffer();
                    File filecontent = new File(path);
                    if (filecontent.exists())
                    {
                        FileInputStream inFile = new FileInputStream(path);
                        byte[] buffer = new byte[2048];
                        int read = inFile.read(buffer);
                        while (read != -1)
                        {
                            html.append(new String(buffer, 0, read));
                            read = inFile.read(buffer);
                        }
                        inFile.close();
                        String workpath = dir + "/";
                        //String htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                        //PrintWriter out = response.getWriter();
                        //out.write(htmlOut);
                        //out.close();
                    }
                    else
                    {
                        //log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + resource.getContent() + "@" + resource.getRepositoryName());
                    }
                }

                in.close();
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
        }

    }

    public abstract boolean isAuthenticate(String pUserName, String pPassword);
}
