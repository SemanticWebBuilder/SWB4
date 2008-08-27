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
import static org.semanticwb.office.comunication.Base64.*;

/**
 *
 * @author victor.lorenzana
 */
public abstract class OfficeServlet extends XMLRPCServlet
{

    private static Hashtable<String, Repository> repositories = new Hashtable<String, Repository>();
    private static String REALM = "Secure Area";
    private static String PREFIX_BASIC = "Basic ";
    private static File REPOSITORY_CONFIG = new File("C:\\repositorio\\wbrepository.xml");
    private static String PATH_REPOSITORY = "C:\\repositorio\\";
    private static String REPOSITORY_NAME = "wbrepository";

    @Override
    public void init() throws ServletException
    {
        addRepository(REPOSITORY_CONFIG,PATH_REPOSITORY,REPOSITORY_NAME);
        addMappingType("OfficeDocument", OfficeDocument.class);
        addMappingType("OfficeApplication", OfficeApplication.class);
    }
    public static void addRepository(File fileConfig, String path,String name)
    {        
        try
        {
            Repository rep=createRepository(fileConfig,path);            
            repositories.put(name, rep);
        }
        catch ( RepositoryException ce )
        {
            ce.printStackTrace(System.out);
        }
        catch ( FileNotFoundException fnfe )
        {
            fnfe.printStackTrace(System.out);
        }
    }
    private static Repository createRepository(File fileConfig, String path) throws RepositoryException, FileNotFoundException
    {
        InputStream in = new FileInputStream(fileConfig);
        RepositoryConfig repositoryConfig = RepositoryConfig.create(in, PATH_REPOSITORY);
        Repository rep = RepositoryImpl.create(repositoryConfig);
        return rep;
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

    private void checkSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try
        {
            String pUserName = null;
            String pPassword = null;
            String authorization = request.getHeader("Authorization");
            if ( authorization == null || authorization.equals("") )
            {
                response.setHeader("WWW-Authenticate", PREFIX_BASIC + "realm=\"" + REALM + "\"");
                response.setStatus(response.SC_UNAUTHORIZED);
                return;
            }
            else
            {
                if ( authorization.startsWith(PREFIX_BASIC) )
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
            throw ioe;
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        checkSecurity(request, response);
        super.doPost(request, response);

    }

    public abstract boolean isAuthenticate(String pUserName, String pPassword);

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
