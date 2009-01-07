/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository.jackrabbit;

import org.semanticwb.repository.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;

/**
 *
 * @author victor.lorenzana
 */
public final class JackRabbitRepositoryManager implements RepositoryListener
{

    private static String DEFAULT_REPOSITORY_CONFIGURATION;
    private static HashSet<File> nodeDefinitions = new HashSet<File>();
    private static byte[] buffer = new byte[2048];
    private static JackRabbitRepositoryManager instance = new JackRabbitRepositoryManager();

    static
    {
        DEFAULT_REPOSITORY_CONFIGURATION = loadResourceAsString(JackRabbitRepositoryManager.class, "jackRabbitDefaultConfig.txt");        
    }

    public void onShutDown(Repository repository)
    {
        if ( repository != null && repository instanceof RepositoryImpl )
        {
            (( RepositoryImpl ) repository).shutdown();
        }

    }
    public void onOpenSession(Session session)
    {
        if ( session.getRepository() instanceof RepositoryImpl )
        {
            for ( File nodeDefinition : nodeDefinitions )
            {
                try
                {
                    registerCustomNodeTypes(session.getWorkspace(), nodeDefinition);
                }
                catch ( Exception e )
                {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    private JackRabbitRepositoryManager()
    {
        RepositoryManager.addRepositoryListener(this);
    }

    public static JackRabbitRepositoryManager getInstance()
    {
        if ( instance == null )
        {
            instance = new JackRabbitRepositoryManager();
        }
        return instance;
    }

    public static void addCustomNodeDefinitionFile(File cndFileName) throws Exception
    {
        nodeDefinitions.add(cndFileName);
    }

    private static void registerCustomNodeTypes(Workspace ws, File cndFileName) throws Exception
    {
        // Read in the CND file
        FileReader fileReader = new FileReader(cndFileName);

        // Create a CompactNodeTypeDefReader
        CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(fileReader, cndFileName.getAbsolutePath());

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

    private static String loadResourceAsString(Class clazz, String resource)
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            InputStream in = clazz.getResourceAsStream(resource);
            int read = in.read(buffer);
            while (read != -1)
            {
                builder.append(new String(buffer, 0, read, "UTF-8"));
                read = in.read(buffer);
            }
            in.close();
        }
        catch ( IOException ioe )
        {
            ioe.printStackTrace(System.out);
        }
        return builder.toString();
    }

    public static void addRepositoryUsingDefaultConfiguration(File directory, String name) throws Exception
    {
        File fileConfig = File.createTempFile("wbrepository", name);
        FileOutputStream out = new FileOutputStream(fileConfig);
        out.write(DEFAULT_REPOSITORY_CONFIGURATION.getBytes("UTF-8"));
        out.close();
        addRepository(fileConfig, directory, name);
        fileConfig.delete();
    }

    public static void addRepository(File fileConfig, File directory, String name) throws Exception
    {
        if ( directory.isFile() )
        {
            throw new Exception("The path " + directory.getAbsolutePath() + " is not a directory");
        }
        try
        {
            Repository rep = createRepository(fileConfig, directory);
            RepositoryManager.addRepository(rep, name);
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

    private static Repository createRepository(File fileConfig, File directory) throws RepositoryException, FileNotFoundException
    {
        InputStream in = new FileInputStream(fileConfig);
        RepositoryConfig repositoryConfig = RepositoryConfig.create(in, directory.getAbsolutePath());
        Repository rep = RepositoryImpl.create(repositoryConfig);
        return rep;
    }
}
