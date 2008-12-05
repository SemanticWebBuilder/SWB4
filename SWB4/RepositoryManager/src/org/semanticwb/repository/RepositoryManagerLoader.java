/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import javax.jcr.Session;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class RepositoryManagerLoader
{

    private static Logger log = SWBUtils.getLogger(RepositoryManagerLoader.class);
    private static Hashtable<String, RepositoryManager> repositoryManagers = new Hashtable<String, RepositoryManager>();
    private static RepositoryManagerLoader instance = new RepositoryManagerLoader();

    private RepositoryManagerLoader()
    {
        log.event("Initializing RepositoryManagerLoader ...");
        String repConfig = SWBPlatform.getEnv("swb/repositoryManager", "");
        String[] repositoriesClasess = repConfig.split(",");
        for (String clazz : repositoriesClasess)
        {
            if (!clazz.equals(""))
            {
                try
                {
                    log.event("Adding RepositoryManager with class " + clazz + " ...");
                    RepositoryManager manager = (RepositoryManager) Class.forName(clazz).newInstance();
                    if (!repositoryManagers.containsKey(manager.getName()))
                    {
                        manager.init();
                        repositoryManagers.put(manager.getName(), manager);
                    }
                }
                catch (Throwable e)
                {
                    log.error(e);
                }
            }
        }
    }

    public static RepositoryManagerLoader getInstance()
    {
        return instance;
    }

    public String[] getWorkspacesForOffice()
    {
        ArrayList<String> workspaces = new ArrayList<String>();
        for (RepositoryManager manager : repositoryManagers.values())
        {
            if (manager.isUsedForOffice())
            {
                for (String workspace : manager.getWorkspaces())
                {
                    workspaces.add(workspace + "@" + manager.getName());
                }
            }
        }
        return workspaces.toArray(new String[workspaces.size()]);
    }

    public String[] getWorkspaces()
    {
        ArrayList<String> workspaces = new ArrayList<String>();
        for (RepositoryManager manager : repositoryManagers.values())
        {
            for (String workspace : manager.getWorkspaces())
            {
                workspaces.add(workspace + "@" + manager.getName());
            }
        }
        return workspaces.toArray(new String[workspaces.size()]);
    }

    public Session openSession(String workspaceId, String id, String password) throws Exception
    {
        String[] values = workspaceId.split("@");
        if (values.length == 2)
        {
            String workspace = values[0];
            String repository = values[1];
            return repositoryManagers.get(repository).openSession(workspace, id, password);
        }
        else
        {
            throw new IllegalArgumentException("The workspaceid is invalid");
        }
    }
}
