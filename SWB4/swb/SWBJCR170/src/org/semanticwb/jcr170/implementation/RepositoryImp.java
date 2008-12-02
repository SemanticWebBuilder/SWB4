/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import org.semanticwb.model.SWBContext;
import org.semanticwb.repository.Unstructured;
import org.semanticwb.repository.Workspace;

/**
 *
 * @author victor.lorenzana
 */
public final class RepositoryImp implements Repository
{

    private static Hashtable<String, String> descriptors = new Hashtable<String, String>();

    static
    {
        descriptors.put("OPTION_VERSIONING_SUPPORTED", "true");
        descriptors.put("SPEC_VERSION_DESC", "1.0");
        descriptors.put("SPEC_NAME_DESC", "Content Repository for Java Technology API");
        descriptors.put("REP_VENDOR_DESC", "Semantic INFOTEC WebBuilder 4.0");
        descriptors.put("REP_VENDOR_URL_DESC", "http://www.webbuilder.org.mx");
        descriptors.put("REP_NAME_DESC", "Semantic INFOTEC WebBuilder 4.0 Repository");
        descriptors.put("REP_VERSION_DESC", "1.0.0.0");
        descriptors.put("LEVEL_1_SUPPORTED", "true");
        descriptors.put("LEVEL_2_SUPPORTED", "false");
        descriptors.put("OPTION_TRANSACTIONS_SUPPORTED", "false");
        descriptors.put("OPTION_OBSERVATION_SUPPORTED", "false");
        descriptors.put("OPTION_LOCKING_SUPPORTED", "true");
        descriptors.put("OPTION_QUERY_SQL_SUPPORTED", "false");
        descriptors.put("QUERY_XPATH_POS_INDEX", "false");
        descriptors.put("UERY_XPATH_DOC_ORDER", "true");

    }
    private String defaultWorkspaceName = "defaultWorkspace";
    private static final String namespace = "http://www.semanticwb.org/repository/workspace/default";

    public RepositoryImp() throws RepositoryException
    {
        boolean exists = false;
        for ( String name : listWorkspaces() )
        {
            if ( name.equals(defaultWorkspaceName) )
            {
                Workspace ws = SWBContext.createWorkspace(name, namespace);
                if ( ws.getRoot() == null )
                {
                    Unstructured root = ws.createUnstructured();
                    root.setName("jcr:root");                    
                    ws.setRoot(root);
                }
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            createWorkspace(defaultWorkspaceName);
        }

    }

    public RepositoryImp(String defaultWorkspaceName) throws RepositoryException
    {
        this();
        this.defaultWorkspaceName = defaultWorkspaceName;
        boolean exists = false;
        for ( String name : listWorkspaces() )
        {
            if ( name.equals(defaultWorkspaceName) )
            {
                Workspace ws = SWBContext.createWorkspace(name, namespace);
                if ( ws.getRoot() == null )
                {
                    Unstructured root = ws.createUnstructured();
                    root.setName("jcr:root");                    
                    ws.setRoot(root);
                }
                exists = true;
                break;
            }
        }
        if ( !exists )
        {
            createWorkspace(defaultWorkspaceName);
        }
    }
    public void recreateDefaultWorkspace() throws RepositoryException
    {
        for ( String name : listWorkspaces() )
        {
            if ( name.equals(defaultWorkspaceName) )
            {                
                String uri=SWBContext.getWorkspace(defaultWorkspaceName).getURI();
                SWBContext.removeWorkspace(uri);                
                createWorkspace(defaultWorkspaceName);
                break;
            }
        }
    }
    public String[] listWorkspaces()
    {
        HashSet<String> names = new HashSet<String>();
        Iterator<org.semanticwb.repository.Workspace> it = SWBContext.listWorkspaces();
        int size = 0;
        while (it.hasNext())
        {
            size++;
            names.add(it.next().getURI());
        }
        return names.toArray(new String[size]);
    }

    public void setDefaultWorspaceName(String defaultWorkspaceName)
    {
        this.defaultWorkspaceName = defaultWorkspaceName;
    }

    public String getDefaultWorspaceName()
    {
        return defaultWorkspaceName;
    }

    public String[] getDescriptorKeys()
    {
        return descriptors.keySet().toArray(new String[descriptors.keySet().size()]);
    }

    public String getDescriptor(String descriptor)
    {
        return descriptors.get(descriptor);
    }

    public static void createWorkspace(String name) throws RepositoryException
    {
        if ( name == null || name.trim().equals("") )
        {
            throw new RepositoryException("The name of repository can not be null or empty");
        }
        Workspace ws = SWBContext.createWorkspace(name, namespace);
        Unstructured root = ws.createUnstructured();
        root.setName("jcr:root");        
        root.setPath("/");
        ws.setRoot(root);
    }

    public Session login(Credentials credentials, String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException
    {
        if ( credentials == null )
        {
            throw new LoginException("The simple credential is null");
        }
        if ( workspaceName == null )
        {
            workspaceName = defaultWorkspaceName;
        }
        Workspace workspace = SWBContext.getWorkspace(workspaceName);
        if ( workspace != null )
        {
            if ( credentials instanceof SimpleCredentials )
            {
                SimpleCredentials simpleCredentials = ( SimpleCredentials ) credentials;
                return new SessionImp(this, workspace.getURI(), simpleCredentials);
            }
            else
            {
                throw new LoginException("The credentials are not SimpleCredentials");
            }
        }
        else
        {
            throw new NoSuchWorkspaceException("The workspace " + workspaceName + " is not valid or does not exist");
        }
    }

    public Session login(Credentials credentials) throws LoginException, RepositoryException
    {
        return login(credentials, null);
    }

    public Session login(String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException
    {
        return login(null, workspaceName);
    }

    public Session login() throws LoginException, RepositoryException
    {
        return login(null, null);
    }
}
