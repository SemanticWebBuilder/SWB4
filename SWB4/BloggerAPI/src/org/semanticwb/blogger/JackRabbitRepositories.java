/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.blogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.semanticwb.blogger.MetaWebBlogServlet;

/**
 *
 * @author victor.lorenzana
 */
public class JackRabbitRepositories {
    
    private static File REPOSITORY_CONFIG = new File("C:\\repositorio\\wbrepository.xml");
    private static String PATH_REPOSITORY = "C:\\repositorio\\";
    private static String REPOSITORY_NAME = "wbrepository";
    private static Hashtable<String, Repository> repositories = new Hashtable<String, Repository>();    
    static
    {
        addRepository(REPOSITORY_CONFIG,PATH_REPOSITORY,REPOSITORY_NAME);
        MetaWebBlogServlet.addRepositories(repositories);
        
    }
    public void shutDown()
    {
        for ( Repository rep : repositories.values() )
        {
            if ( rep != null && rep instanceof RepositoryImpl )
            {
                (( RepositoryImpl ) rep).shutdown();
            }
        }
    }
    public static Hashtable<String, Repository> getRepositories()
    {
        return repositories;
    }
    private static void addRepository(File fileConfig, String path,String name)
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
}
