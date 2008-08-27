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
    
    private static Hashtable<String, Repository> repositories = new Hashtable<String, Repository>();
    private static File REPOSITORY_CONFIG = new File("C:\\repositorio\\wbrepository.xml");
    private static String PATH_REPOSITORY = "C:\\repositorio\\";
    private static String REPOSITORY_NAME = "wbrepository";
    @Override
    public void init() throws ServletException
    {
        addRepository(REPOSITORY_CONFIG,PATH_REPOSITORY,REPOSITORY_NAME);
        addMappingType("blogger", MetaWeblogImp.class);
        addMappingType("metaWeblog", MetaWeblogImp.class);        
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
