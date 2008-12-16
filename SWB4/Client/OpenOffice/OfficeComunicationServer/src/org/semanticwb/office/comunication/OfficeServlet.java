/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.servlet.*;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.xmlrpc.XMLRPCServlet;
import static org.semanticwb.office.comunication.Base64.*;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeServlet extends XMLRPCServlet
{

    private static final String title = "Gateway de Comunicación con Office INFOTEC Semantic WebBuilder 4";
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

    private void checkSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try
        {
            String pUserName = null;
            String pPassword = null;
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
                    pUserName = getUserName(userpassDecoded);
                    pPassword = getPassword(userpassDecoded);
                    if (!this.isAuthenticate(pUserName, pPassword))
                    {
                        response.sendError(response.SC_FORBIDDEN);
                    }
                    else
                    {
                        request.setAttribute("user", pUserName);
                        request.setAttribute("password", pPassword);
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

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        checkSecurity(request, response);
        super.doPost(request, response);

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        checkSecurity(request, response);
        String contentId = request.getParameter("contentId");
        String versionName = request.getParameter("versionName");
        String repositoryName = request.getParameter("repositoryName");
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
            /*try
            {
                Session session = loader.openSession(repositoryName, "", "");
                Node contentNode = session.getNodeByUUID(contentId);
                VersionHistory history = contentNode.getVersionHistory();
                Version versiontoReturn = history.getVersion(versionName);
                if (versiontoReturn != null)
                {
                    Node frozenNode = versiontoReturn.getNode("jcr:frozenNode");
                    String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
                    String file = frozenNode.getProperty(cm_file).getString();
                    Node resNode = frozenNode.getNode("jcr:content");
                    InputStream in = resNode.getProperty("jcr:data").getStream();
                    File fileTemp=File.createTempFile("", "");
                    FileOutputStream writer=new FileOutputStream(fileTemp);
                    byte[] buffer=new byte[2048];

                    int read=in.read(buffer);
                    while(read!=-1)
                    {
                        writer.write(buffer,0,read);
                        read=in.read(buffer);
                    }
                    ZipFile zip=new ZipFile(fileTemp);
                    Enumeration<ZipEntry> entries=zip.entries();
                    while(entries.hasMoreElements())
                    {

                    }
                    writer.close();
                    PrintWriter out=response.getWriter();
                    out.close();
                }
                else
                {
                    throw new Exception("The version " + versionName + " does not exist");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace(response.getWriter());
            }*/
        }

    }

    public abstract boolean isAuthenticate(String pUserName, String pPassword);
}
