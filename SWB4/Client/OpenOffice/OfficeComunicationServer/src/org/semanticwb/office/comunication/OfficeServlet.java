/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.*;
import java.net.*;

import java.util.Hashtable;
import java.util.Set;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XMLRPCServlet;
import sun.net.www.MimeEntry;
import sun.net.www.MimeTable;
import static org.semanticwb.office.comunication.Base64.*;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeServlet extends XMLRPCServlet
{

    private Hashtable<String, Repository> repositories = new Hashtable<String, Repository>();
    private static String realm = "Secure Area";
    private static String prefixBasic = "Basic ";

    @Override
    public void init() throws ServletException
    {

        File fileConfig = new File("C:\\repositorio\\wbrepository.xml");
        try
        {
            InputStream in = new FileInputStream(fileConfig);
            RepositoryConfig repositoryConfig = RepositoryConfig.create(in, "C:\\repositorio\\");
            Repository rep = RepositoryImpl.create(repositoryConfig);
            repositories.put("wbrepository", rep);

        }
        catch ( RepositoryException ce )
        {
            ce.printStackTrace(System.out);
        }
        catch ( FileNotFoundException fnfe )
        {
            fnfe.printStackTrace(System.out);
        }
        addMappingType("OfficeDocument", OfficeDocument.class);
        addMappingType("OfficeApplication", OfficeApplication.class);        
        MimeEntry me = new MimeEntry("application/msword");
        me.setExtensions(".doc,.docx");
        MimeTable.getDefaultTable().add(me);

    }
   
    
    @Override
    protected void finalize() throws Throwable
    {
        for ( Repository rep : repositories.values() )
        {
            if ( rep != null && rep instanceof RepositoryImpl )
            {
                (( RepositoryImpl ) rep).shutdown();
            }
        }
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

    private void checkSecurity(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String pUserName = null;
            String pPassword = null;
            String authorization = request.getHeader("Authorization");
            if ( authorization == null || authorization.equals("") )
            {
                response.setHeader("WWW-Authenticate", prefixBasic + "realm=\"" + realm + "\"");
                response.setStatus(response.SC_UNAUTHORIZED);
                return;
            }
            else
            {
                if ( authorization.startsWith(prefixBasic) )
                {
                    String userpassEncoded = authorization.substring(6);
                    String userpassDecoded = new String(decode(userpassEncoded));
                    pUserName = getUserName(userpassDecoded);
                    pPassword = getPassword(userpassDecoded);
                    if ( !this.isAuthenticate(pUserName, pPassword) )
                    {
                        response.sendError(response.SC_FORBIDDEN);
                    }
                }
                else
                {
                    response.sendError(response.SC_FORBIDDEN);
                }
            }
        }
        catch ( IOException ioe )
        {

        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        checkSecurity(request, response);
        super.doPost(request, response);
    }

    public boolean isAuthenticate(String pUserName, String pPassword)
    {
        return true;
    }

    @Override
    protected void beforeExecute(Object objToExecute, Set<Part> parts) throws Exception
    {
        super.beforeExecute(objToExecute, parts);
        if ( objToExecute instanceof RepositorySupport )
        {
            RepositorySupport obj = ( RepositorySupport ) objToExecute;
            if ( !obj.hasListOfRepositories() )
            {
                obj.setRepositories(repositories);
            }
        }
    }
}
