/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semantic.blogger.interfaces;

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
import org.semanticwb.blogger.MetaWeblogImp;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XMLRPCServlet;

/**
 *
 * @author victor.lorenzana
 */
public class MetaWebBlogServlet extends XMLRPCServlet {
    
    private Hashtable<String, Repository> repositories = new Hashtable<String, Repository>();
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
        addMappingType("blogger", MetaWeblogImp.class);
        addMappingType("metaWeblog", MetaWeblogImp.class);
        
    }    
    @Override
    protected void beforeExecute(Object objToExecute, Set<Part> parts) throws Exception
    {
        super.beforeExecute(objToExecute, parts);
        if ( objToExecute instanceof RepositorySupport )
        {
            RepositorySupport obj = ( RepositorySupport ) objToExecute;
            //if ( !obj.hasListOfRepositories() )
            //{
                obj.setRepositories(repositories);
            //}
        }
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
    
}
