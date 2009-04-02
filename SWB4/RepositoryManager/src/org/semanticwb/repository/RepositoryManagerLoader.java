/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import javax.jcr.Session;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.office.interfaces.RepositoryInfo;

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
        String repConfig = SWBPlatform.getEnv("swbrep/repositoryManager", "");
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

    public RepositoryInfo[] getWorkspacesForOffice()
    {
        ArrayList<RepositoryInfo> workspaces = new ArrayList<RepositoryInfo>();
        for (RepositoryManager manager : repositoryManagers.values())
        {
            OfficeManager officemanager=manager.getOfficeManager();
            if (officemanager!=null)
            {
                Collection<RepositoryInfo> officeworkspaces=officemanager.getWorkspaces();
                workspaces.addAll(officeworkspaces);
            }
        }
        return workspaces.toArray(new RepositoryInfo[workspaces.size()]);
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

    public Session openSession(String workspaceId, User user) throws Exception
    {
        String[] values = workspaceId.split("@");
        if (values.length == 2)
        {
            String workspace = values[0];
            String repository = values[1];
            return repositoryManagers.get(repository).openSession(workspace, user);
        }
        else
        {
            throw new WorkspaceNotFoudException(workspaceId);
        }
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
            throw new WorkspaceNotFoudException(workspaceId);
        }
    }
   
    public void createWorkspace(String repositoryName,String workspace) throws Exception
    {
        repositoryManagers.get(repositoryName).createWorkspace(workspace);
    }
    public void deleteWorkspace(String repositoryName,String workspace) throws Exception
    {
        repositoryManagers.get(repositoryName).createWorkspace(workspace);
    }
    public String[] getRepositoryNames()
    {
        return repositoryManagers.keySet().toArray(new String[repositoryManagers.keySet().size()]);
    }

    public OfficeManager getOfficeManager(String workspaceId)
    {
        String[] values = workspaceId.split("@");
        if (values.length == 2)
        {
            String repository = values[1];
            return repositoryManagers.get(repository).getOfficeManager();
        }
        else
        {
            throw new IllegalArgumentException("The workspaceid is invalid");
        }
    }

}
