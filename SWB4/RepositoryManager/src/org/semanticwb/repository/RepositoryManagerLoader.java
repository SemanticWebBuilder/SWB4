/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
   
    public void createWorkspace(String repositoryName,String workspace,String title,String description) throws Exception
    {
        repositoryManagers.get(repositoryName).createWorkspace(workspace,title,description);
    }
    public void deleteWorkspace(String repositoryName,String workspace,String title,String description) throws Exception
    {
        repositoryManagers.get(repositoryName).createWorkspace(workspace,title,description);
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
