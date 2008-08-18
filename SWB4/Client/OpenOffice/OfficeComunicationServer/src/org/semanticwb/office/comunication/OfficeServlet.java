/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;

import java.io.*;
import java.net.*;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Workspace;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XMLRPCServlet;
import sun.net.www.MimeEntry;
import sun.net.www.MimeTable;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeServlet extends XMLRPCServlet {
   
    public static final String URI_NODEDEF = "http://www.semanticwb.org.mx/model/content/1.0";
    public static final String NODEDEF_PREFIX = "swb";
    public static final String URI_CONTENT = "http://www.semanticwb.org.mx/model/content/1.0/cm";
    public static final String CONTENT_PREFIX = "cm";    
    private Hashtable<String,Repository> repositories=new Hashtable<String, Repository>();
    
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
        MimeEntry me=new MimeEntry("application/msword");
        me.setExtensions(".doc,.docx");
        MimeTable.getDefaultTable().add(me);

    }
    @Override
    protected void finalize() throws Throwable
    {
        for(Repository rep : repositories.values())
        {
            if ( rep != null && rep instanceof RepositoryImpl)
            {
                (( RepositoryImpl ) rep).shutdown();
            }
        }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {        
        addMappingType("OfficeDocument", OfficeDocument.class);
        addMappingType("OfficeApplication", OfficeApplication.class);
        super.doPost(request, response);
    }

    public boolean isAuthenticate(String pUserName, String pPassword)
    {       
        return true;
    }
    @Override
    protected void beforeExecute(Object objToExecute, Set<Part> parts, String user, String password) throws Exception
    {
        super.beforeExecute(objToExecute, parts, user, password);
        if ( objToExecute instanceof RepositorySupport )
        {
            RepositorySupport obj = ( RepositorySupport ) objToExecute;                        
            obj.setRepositories(repositories);
        }
    }
    
    public static void RegisterCustomNodeTypes(Workspace ws, String cndFileName) throws Exception
    {

        boolean exists = false;
        for ( String uri : ws.getNamespaceRegistry().getURIs() )
        {
            if ( uri.equals(URI_NODEDEF) )
            {
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            try
            {

                ws.getNamespaceRegistry().registerNamespace(NODEDEF_PREFIX, URI_NODEDEF);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }
        exists = false;
        for ( String uri : ws.getNamespaceRegistry().getURIs() )
        {
            if ( uri.equals(URI_CONTENT) )
            {
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            try
            {
                ws.getNamespaceRegistry().registerNamespace(CONTENT_PREFIX, URI_CONTENT);
            }
            catch ( Exception e )
            {
                e.printStackTrace(System.out);
            }
        }    
        
        
        // Read in the CND file
        FileReader fileReader = new FileReader(cndFileName);

        // Create a CompactNodeTypeDefReader
        CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(fileReader, cndFileName);

        // Get the List of NodeTypeDef objects
        List ntdList = cndReader.getNodeTypeDefs();

        // Get the NodeTypeManager from the Workspace.
        // Note that it must be cast from the generic JCR NodeTypeManager to the
        // Jackrabbit-specific implementation.        
        NodeTypeManagerImpl ntmgr = ( NodeTypeManagerImpl ) ws.getNodeTypeManager();

        // Acquire the NodeTypeRegistry
        NodeTypeRegistry ntreg = ntmgr.getNodeTypeRegistry();

        // Loop through the prepared NodeTypeDefs
        for ( Iterator i = ntdList.iterator(); i.hasNext();)
        {
            // Get the NodeTypeDef...
            NodeTypeDef ntd = ( NodeTypeDef ) i.next();
            try
            {
                if ( !ntreg.isRegistered(ntd.getName()) )
                {
                    ntreg.registerNodeType(ntd);
                }
            }
            catch ( InvalidNodeTypeDefException e )
            {
                System.out.println(e.getLocalizedMessage());
            }
        }                
    }
}
