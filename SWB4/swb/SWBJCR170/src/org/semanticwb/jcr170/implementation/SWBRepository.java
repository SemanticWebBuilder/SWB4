/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.security.Principal;
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
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.repository.Unstructured;
import org.semanticwb.repository.Workspace;
import org.semanticwb.security.auth.SWB4CallbackHandlerGateWayOffice;

/**
 *
 * @author victor.lorenzana
 */
public final class SWBRepository implements Repository
{

    static Logger log = SWBUtils.getLogger(SWBRepository.class);
    private static Hashtable<String, String> descriptors = new Hashtable<String, String>();


    static
    {
        descriptors.put(OPTION_VERSIONING_SUPPORTED, "true");
        descriptors.put(SPEC_VERSION_DESC, "1.0");
        descriptors.put(SPEC_NAME_DESC, "Content Repository for Java Technology API");
        descriptors.put(REP_VENDOR_DESC, "Semantic INFOTEC WebBuilder 4.0");
        descriptors.put(REP_VENDOR_URL_DESC, "http://www.webbuilder.org.mx");
        descriptors.put(REP_NAME_DESC, "Semantic INFOTEC WebBuilder 4.0 Repository");
        descriptors.put(REP_VERSION_DESC, "1.0.0.0");
        descriptors.put(LEVEL_1_SUPPORTED, "true");
        descriptors.put(LEVEL_2_SUPPORTED, "false");
        descriptors.put(OPTION_TRANSACTIONS_SUPPORTED, "false");
        descriptors.put(OPTION_OBSERVATION_SUPPORTED, "false");
        descriptors.put(OPTION_LOCKING_SUPPORTED, "true");
        descriptors.put(OPTION_QUERY_SQL_SUPPORTED, "false");
        descriptors.put(QUERY_XPATH_POS_INDEX, "false");
        descriptors.put(QUERY_XPATH_DOC_ORDER, "true");

    }
    private String defaultWorkspaceName = "defaultWorkspace";
    private static final String namespace = "http://www.semanticwb.org/repository#";
    private ObservationManagerImp observationManager;

    public SWBRepository() throws RepositoryException
    {

        log.event("Initializing repository with namespace " + namespace + " ...");
        observationManager = new ObservationManagerImp();
        boolean exists = false;
        for (String name : listWorkspaces())
        {
            if (name.equals(defaultWorkspaceName))
            {
                exists = true;
                break;
            }
        }
        if (!exists)
        {
            createWorkspace(defaultWorkspaceName);
        }

    }

    public SWBRepository(String defaultWorkspaceName) throws RepositoryException
    {
        this();
        this.defaultWorkspaceName = defaultWorkspaceName;
        boolean exists = false;
        for (String name : listWorkspaces())
        {
            if (name.equals(defaultWorkspaceName))
            {
                Workspace ws = SWBContext.createWorkspace(name, namespace);
                if (ws.getRoot() == null)
                {
                    Unstructured root = ws.createUnstructured();
                    root.setName("jcr:root");
                    ws.setRoot(root);
                }
                exists = true;
                break;
            }
        }
        if (!exists)
        {
            createWorkspace(defaultWorkspaceName);
        }
    }

    public ObservationManagerImp getObservationManagerImp()
    {
        return observationManager;
    }

    public void recreateDefaultWorkspace() throws RepositoryException
    {
        for (String name : listWorkspaces())
        {
            if (name.equals(defaultWorkspaceName))
            {
                String uri = SWBContext.getWorkspace(defaultWorkspaceName).getURI();
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
            String uri = it.next().getURI();
            if (uri.indexOf("#") != -1)
            {
                int pos = uri.indexOf("#");
                uri = uri.substring(pos + 1);
            }
            names.add(uri);
        }
        return names.toArray(new String[names.size()]);
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

    public static void deleteWorkspace(String name) throws RepositoryException
    {
        SWBContext.removeWorkspace(name);
    }
    public static void createWorkspace(String name) throws RepositoryException
    {
        if (name == null || name.trim().equals(""))
        {
            throw new RepositoryException("The name of repository can not be null or empty");
        }
        log.debug("Creating workspace " + name + " ...");
        Workspace ws = SWBContext.createWorkspace(name, namespace);
        Unstructured root = ws.createUnstructured();
        root.setName("jcr:root");
        root.setPath("/");
        ws.setRoot(root);
    }

    private Principal authenticate(String pUserName, String pPassword)
    {
        UserRepository ur = SWBContext.getAdminWebSite().getUserRepository();
        String context = ur.getProperty(UserRepository.SWBUR_LoginContext);
        Subject subject = new Subject();
        LoginContext lc;
        try
        {
            SWB4CallbackHandlerGateWayOffice callbackHandler = new SWB4CallbackHandlerGateWayOffice(pUserName, pPassword);
            lc = new LoginContext(context, subject, callbackHandler);
            lc.login();
            Principal principal=subject.getPrincipals().iterator().next();
            return principal;
        }
        catch (Exception e)
        {
            log.debug("Can't log User", e);
        }
        return null;
    }

    public Session login(Credentials credentials, String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException
    {
        if (credentials == null)
        {
            throw new LoginException("The credentials are null");
        }
        if (workspaceName == null)
        {
            workspaceName = defaultWorkspaceName;
        }
        Workspace workspace = SWBContext.getWorkspace(workspaceName);
        if (workspace != null)
        {
            if (credentials instanceof SimpleCredentials)
            {
                SimpleCredentials simpleCredentials = (SimpleCredentials) credentials;
                Principal principal=authenticate(simpleCredentials.getUserID(),new String(simpleCredentials.getPassword()));
                if(principal==null)
                {
                    throw new LoginException("The user can not be authenticated");
                }
                return new SessionImp(this, workspaceName, principal);
            }
            else if (credentials instanceof SWBCredentials)
            {
                return new SessionImp(this, workspaceName, ((SWBCredentials)credentials).getPrincipal());
            }            
            else
            {
                throw new LoginException("The credentials are not valid");
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
