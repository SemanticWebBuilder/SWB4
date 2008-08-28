/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import java.net.URI;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Workspace;

/**
 *
 * @author victor.lorenzana
 */
public final class RepositoryManager
{

    private static Hashtable<String, Repository> repositories = new Hashtable<String, Repository>();
    private static Hashtable<String, URI> namespaces = new Hashtable<String, URI>();
    private static RepositoryManager instance = new RepositoryManager();
    private static HashSet<RepositoryListener> listeners = new HashSet<RepositoryListener>();

    private RepositoryManager()
    {
    }

    public static RepositoryManager getInstance()
    {
        if ( instance == null )
        {
            instance = new RepositoryManager();
        }
        return instance;
    }

    @Override
    protected void finalize() throws Throwable
    {
        for ( Repository repository : repositories.values() )
        {
            for ( RepositoryListener listener : listeners )
            {
                listener.onShutDown(repository);
            }
        }
        namespaces.clear();
        listeners.clear();
        repositories.clear();
    }

    public static void removeRepositoryListener(RepositoryListener listener)
    {
        listeners.remove(listener);
    }

    public static void addRepositoryListener(RepositoryListener listener)
    {
        listeners.add(listener);
    }

    public static void addNamespace(String prefix, URI uri)
    {
        namespaces.put(prefix, uri);
    }

    private static void registerNamespaces(Workspace ws, Map<String, URI> namespaces) throws Exception
    {
        for ( String prefixToRegister : namespaces.keySet() )
        {            
            boolean exist = false;
            for ( String prefix : ws.getNamespaceRegistry().getPrefixes() )
            {
                if ( prefix.equalsIgnoreCase(prefixToRegister) )
                {
                    exist = true;
                    break;
                }
            }
            if ( !exist )
            {
                try
                {
                    ws.getNamespaceRegistry().registerNamespace(prefixToRegister, namespaces.get(prefixToRegister).toString());
                }
                catch ( Exception e )
                {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    public static Session openSession(String repositoryName, String user, String password) throws Exception
    {
        if ( repositories.get(repositoryName) != null )
        {
            Session session = repositories.get(repositoryName).login(new SimpleCredentials(user, password.toCharArray()));
            registerNamespaces(session.getWorkspace(), namespaces);
            for ( RepositoryListener listener : listeners )
            {
                listener.onOpenSession(session);
            }
            return session;
        }
        throw new Exception("The repository: " + repositoryName + " does not exist");
    }

    public static Set<String> getRepositoryNames()
    {
        return repositories.keySet();
    }

    public static void addRepository(Repository repository, String name) throws Exception
    {
        if ( RepositoryManager.repositories.containsKey(name) )
        {
            throw new Exception("The repository " + name + " already exists");
        }
        RepositoryManager.repositories.put(name, repository);
    }

    public static void addRepositories(Hashtable<String, Repository> repositories) throws Exception
    {
        for ( String key : repositories.keySet() )
        {
            if ( RepositoryManager.repositories.containsKey(key) )
            {
                throw new Exception("The repository " + key + " already exists");
            }
        }
        for ( String key : repositories.keySet() )
        {
            RepositoryManager.repositories.put(key, repositories.get(key));
        }
    }
}
