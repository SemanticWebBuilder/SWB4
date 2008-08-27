/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.blogger;

import org.semantic.blogger.interfaces.*;
import java.io.*;
import java.net.*;

import java.util.Hashtable;
import java.util.Set;
import javax.jcr.Repository;
import javax.servlet.*;
import javax.servlet.http.*;
import org.semanticwb.blogger.JackRabbitRepositories;
import org.semanticwb.blogger.MetaWeblogImp;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XMLRPCServlet;

/**
 *
 * @author victor.lorenzana
 */
public class MetaWebBlogServlet extends XMLRPCServlet {
    
    private static Hashtable<String, Repository> repositories = new Hashtable<String, Repository>();    
    private static JackRabbitRepositories rep;
    static            
    {
        rep=new JackRabbitRepositories();
    }
    @Override
    public void init() throws ServletException
    {          
        addMappingType("blogger", MetaWeblogImp.class);
        addMappingType("metaWeblog", MetaWeblogImp.class);        
    }    
    public static void addRepositories(Hashtable<String, Repository> repositories)
    {
        MetaWebBlogServlet.repositories=repositories;
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
        rep.shutDown();
    } 
}
