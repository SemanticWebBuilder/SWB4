/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.Hashtable;
import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Unstructured;
import org.semanticwb.jcr283.repository.model.Workspace;


/**
 *
 * @author victor.lorenzana
 */
public class SWBRepository implements Repository {
    private static final String JCR_ROOT = "jcr:root";

    private static Logger log = SWBUtils.getLogger(SWBRepository.class);
    private static Hashtable<String, Value> descriptors = new Hashtable<String, Value>();
    public static final String DEFAULT_WORKSPACE="default";
    private static NodeTypeManagerImp NodeTypeManagerImp;
    static
    {

        log.event("Initializing SWBRepository ...");
        new NodeTypeManagerImp();
        descriptors.put(OPTION_VERSIONING_SUPPORTED, new ValueImp(false));
        descriptors.put(SPEC_VERSION_DESC, new ValueImp("2.0"));
        descriptors.put(SPEC_NAME_DESC, new ValueImp("Content Repository for Java Technology API"));
        descriptors.put(REP_VENDOR_DESC, new ValueImp("Semantic INFOTEC WebBuilder 4.0"));
        descriptors.put(REP_VENDOR_URL_DESC, new ValueImp("http://www.webbuilder.org.mx"));
        descriptors.put(REP_NAME_DESC, new ValueImp("Semantic INFOTEC WebBuilder 4.0 Repository"));
        descriptors.put(REP_VERSION_DESC, new ValueImp("1.0.0.0"));        
        descriptors.put(OPTION_TRANSACTIONS_SUPPORTED, new ValueImp(false));
        descriptors.put(OPTION_OBSERVATION_SUPPORTED, new ValueImp(false));
        descriptors.put(OPTION_LOCKING_SUPPORTED, new ValueImp(true));        
        checkDefaultWorkspace();
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
            ws=org.semanticwb.jcr283.repository.model.Workspace.ClassMgr.createWorkspace(DEFAULT_WORKSPACE, "http://www.semanticwb.org.mx/jcr283/default#");
            ws.setName(DEFAULT_WORKSPACE);
        }
        if(ws.getRoot()==null)
        {
            Unstructured root=Unstructured.ClassMgr.createUnstructured( JCR_ROOT, ws);
            root.setName(JCR_ROOT);
            ws.setRoot(root);
        }
        else
        {
            if(ws.getRoot().getName()==null)
            {
                ws.getRoot().setName(JCR_ROOT);
            }
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Value[] getDescriptorValues(String key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDescriptor(String key)
    {
        try
        {
            return descriptors.get(key).getString();
        }
        catch(Exception e){
            return null;        
        }
    }

    public Session login(Credentials credentials, String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException
    {
        if(workspaceName==null)
        {
            workspaceName=DEFAULT_WORKSPACE;
        }
        SessionImp session=new SessionImp("",this);
        Workspace ws=Workspace.ClassMgr.getWorkspace(workspaceName);
        if(ws==null)
        {
            throw new NoSuchWorkspaceException("The workspace "+workspaceName+" was not found");
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
