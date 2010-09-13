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
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.resource.office.sem.ExcelResource;
import org.semanticwb.resource.office.sem.OfficeResource;
import org.semanticwb.resource.office.sem.PPTResource;
import org.semanticwb.resource.office.sem.WordResource;
import org.semanticwb.xmlrpc.XMLRPCServlet;
import static org.semanticwb.office.comunication.Base64.*;

// TODO: Auto-generated Javadoc
/**
 * The Class OfficeServlet.
 * 
 * @author victor.lorenzana
 */
public abstract class OfficeServlet extends XMLRPCServlet
{

    /** The Constant title. */
    private static final String title = "Gateway de Comunicacion con Office INFOTEC Semantic WebBuilder 4";
    
    /** The log. */
    static Logger log = SWBUtils.getLogger(OfficeServlet.class);
    
    /** The REALM. */
    private static String REALM = "Secure Area";
    
    /** The PREFI x_ basic. */
    private static String PREFIX_BASIC = "Basic ";
    
    /** The Constant loader. */
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();

    /**
     * Inits the.
     * 
     * @throws ServletException the servlet exception
     */
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
        password = values[1];
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
        userName = values[0];
        return userName;
    }

    /**
     * Check security.
     * 
     * @param request the request
     * @param response the response
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
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

    /**
     * Do post.
     * 
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(checkSecurity(request, response))
        {
            super.doPost(request, response);
        }

    }

    /**
     * Do get.
     * 
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        checkSecurity(request, response);
        String pUserName = null;
        final String authorization = request.getHeader("Authorization");
        if (!(authorization == null || authorization.equals("")) && authorization.startsWith(PREFIX_BASIC))
        {            
            final String userpassEncoded = authorization.substring(6);
            final String userpassDecoded = new String(decode(userpassEncoded));
            pUserName = getUserName(userpassDecoded);            
        }
        if(pUserName==null)
        {
            response.sendError(response.SC_FORBIDDEN);
            return;
        }


        String contentId = request.getParameter("contentId");
        String versionName = request.getParameter("versionName");
        String repositoryName = request.getParameter("repositoryName");
        String type = request.getParameter("type");
        if (contentId == null || versionName == null || repositoryName == null || type == null)
        {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + " version "+ OfficeApplication.version +"</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + title + " version "+ OfficeApplication.version +"</h1>");
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
                final String name=UUID.randomUUID().toString();
                final String dir=SWBPortal.getWorkPath()+"/"+name;
                final org.semanticwb.model.User wbuser=SWBContext.getAdminRepository().getUserByLogin(pUserName);
                OfficeResource.loadContentPreview(in, dir,type,wbuser);
                String file = doc.getContentFile(repositoryName, contentId, versionName);
                if (file != null)
                {
                    PrintWriter out = response.getWriter();
                    File content=new File(dir+"/"+file);
                    if(type.equals("word"))
                    {
                        out.println(WordResource.getHTML(content));
                    }
                    else if(type.equals("excel"))
                    {
                        out.println(ExcelResource.getHTML(content));
                    }
                    else
                    {
                        out.println(PPTResource.getHTML(content));
                    }                    
                }
                in.close();
            }
            catch (Exception e)
            {
                final PrintWriter out = response.getWriter();
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

    /**
     * Checks if is authenticate.
     * 
     * @param pUserName the user name
     * @param pPassword the password
     * @return true, if is authenticate
     */
    public abstract boolean isAuthenticate(String pUserName, String pPassword);
}
