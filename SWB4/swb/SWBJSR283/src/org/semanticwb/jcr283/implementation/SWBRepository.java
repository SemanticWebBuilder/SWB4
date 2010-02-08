/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.security.Principal;
import java.util.Hashtable;
import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Workspace;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.security.auth.SWB4CallbackHandlerGateWayOffice;


/**
 *
 * @author victor.lorenzana
 */
public final class SWBRepository implements Repository {
    public static final String DEFAULT_URI_WORKSPACES = "http://www.semanticwb.org.mx/jcr283/workspace#";
    
    private static Logger log = SWBUtils.getLogger(SWBRepository.class);
    private static Hashtable<String, Value[]> descriptors = new Hashtable<String, Value[]>();
    public static final String DEFAULT_WORKSPACE="default";
    private static NodeTypeManagerImp NodeTypeManagerImp;
    private static final NamespaceRegistryImp namespaceRegistryImp=new NamespaceRegistryImp();
    static
    {

        log.event("Initializing SWBRepository ...");
        new NodeTypeManagerImp();
        descriptors.put(OPTION_VERSIONING_SUPPORTED, new Value[]{new ValueImp(false)});
        descriptors.put(SPEC_VERSION_DESC, new Value[]{new ValueImp("2.0")});
        descriptors.put(SPEC_NAME_DESC, new Value[]{new ValueImp("Content Repository for Java Technology API")});
        descriptors.put(REP_VENDOR_DESC, new Value[]{new ValueImp("Semantic INFOTEC WebBuilder 4.0")});
        descriptors.put(REP_VENDOR_URL_DESC, new Value[]{new ValueImp("http://www.webbuilder.org.mx")});
        descriptors.put(REP_NAME_DESC, new Value[]{new ValueImp("Semantic INFOTEC WebBuilder 4.0 Repository")});
        descriptors.put(REP_VERSION_DESC, new Value[]{new ValueImp("1.0.0.0")});
        descriptors.put(OPTION_TRANSACTIONS_SUPPORTED, new Value[]{new ValueImp(false)});
        descriptors.put(OPTION_OBSERVATION_SUPPORTED, new Value[]{new ValueImp(false)});
        descriptors.put(OPTION_LOCKING_SUPPORTED, new Value[]{new ValueImp(true)});
        checkDefaultWorkspace();
    }
    public static NamespaceRegistryImp getNamespaceRegistryImp()
    {
        return namespaceRegistryImp;
    }
    public static NodeTypeManagerImp getNodeTypeManagerImp()
    {
        if(NodeTypeManagerImp==null)
        {
            NodeTypeManagerImp=new NodeTypeManagerImp();
        }
        return NodeTypeManagerImp;
    }
    private static void checkDefaultWorkspace()
    {
        org.semanticwb.jcr283.repository.model.Workspace ws=org.semanticwb.jcr283.repository.model.Workspace.ClassMgr.getWorkspace(DEFAULT_WORKSPACE);
        if(ws==null)
        {
            ws=org.semanticwb.jcr283.repository.model.Workspace.ClassMgr.createWorkspace(DEFAULT_WORKSPACE, DEFAULT_URI_WORKSPACES);
            ws.setName(DEFAULT_WORKSPACE);
        }        
    }
    public String[] getDescriptorKeys()
    {
        return descriptors.keySet().toArray(new String[descriptors.keySet().size()]);
    }

    public boolean isStandardDescriptor(String key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSingleValueDescriptor(String key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Value getDescriptorValue(String key)
    {
        return descriptors.get(key)[0];
    }

    public Value[] getDescriptorValues(String key)
    {
        return descriptors.get(key);
    }

    public String getDescriptor(String key)
    {
        try
        {
            return descriptors.get(key)[0].getString();
        }
        catch(Exception e){
            return null;        
        }
    }

    private Principal authenticate(String pUserName, String pPassword)
    {
        boolean trusted = false;
        try
        {
            trusted = Boolean.parseBoolean(SWBPlatform.getEnv("swbrep/repositoryTrusted", "false"));
        }
        catch (Exception e)
        {
            log.error(e);
        }
        if (trusted)
        {
            return new TrustedPrincipal(pUserName);
        }
        UserRepository ur = SWBContext.getAdminRepository();
        String context = ur.getLoginContext();
        Subject subject = new Subject();
        LoginContext lc;
        try
        {
            SWB4CallbackHandlerGateWayOffice callbackHandler = new SWB4CallbackHandlerGateWayOffice(pUserName, pPassword);
            lc = new LoginContext(context, subject, callbackHandler);
            lc.login();
            Principal principal = subject.getPrincipals().iterator().next();
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
        if(workspaceName==null)
        {
            workspaceName=DEFAULT_WORKSPACE;
        }
        Workspace ws=Workspace.ClassMgr.getWorkspace(workspaceName);
        if(ws==null)
        {
            throw new NoSuchWorkspaceException("The workspace "+workspaceName+" was not found");
        }        
        SessionImp session=null;
        if (credentials instanceof SimpleCredentials)
            {
                SimpleCredentials simpleCredentials = (SimpleCredentials) credentials;
                Principal principal = authenticate(simpleCredentials.getUserID(), new String(simpleCredentials.getPassword()));
                if (principal == null)
                {
                    throw new LoginException("The user can not be authenticated");
                }
                session=new SessionImp(this, principal);
            }
            else if (credentials instanceof SWBCredentials)
            {
                session= new SessionImp(this,((SWBCredentials) credentials).getPrincipal());
            }
            else
            {
                throw new LoginException("The credentials are not valid");
            }
        
    
        WorkspaceImp workspaceImp=new WorkspaceImp(session,ws);
        session.setWorkspace(workspaceImp);
        return session;
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
        return login(null,null);
    }
    
}
