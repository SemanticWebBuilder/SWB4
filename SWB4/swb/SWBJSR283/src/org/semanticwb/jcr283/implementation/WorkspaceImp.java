/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.io.IOException;
import java.io.InputStream;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.ItemExistsException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.lock.LockManager;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.observation.ObservationManager;
import javax.jcr.query.QueryManager;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionManager;
import org.xml.sax.ContentHandler;

/**
 *
 * @author victor.lorenzana
 */
public final class WorkspaceImp  implements Workspace {

    private final SessionImp session;    
    private final String name;    
    private final NodeManager nodeManager;
    private final NodeTypeManagerImp nodeTypeManager=new NodeTypeManagerImp();
    private final VersionManagerImp versionManagerImp;
    private final QueryManagerImp queryManagerImp;
    private final LockManagerImp lockManagerImp;
    private final ObservationManagerImp observationManagerImp;
    public WorkspaceImp(SessionImp session,org.semanticwb.jcr283.repository.model.Workspace ws) throws RepositoryException
    {
        this.session=session;
        session.setWorkspace(this);
        name=ws.getName();        
        nodeManager=new NodeManager(session);
        queryManagerImp=new QueryManagerImp(session);
        lockManagerImp=new LockManagerImp(session);
        nodeManager.loadRoot(ws, session);
        String path="/jcr:system/jcr:versionStorage";
        NodeImp versionSotrage=nodeManager.getNode(path);
        if(versionSotrage==null)
        {
            throw new RepositoryException("The node "+path+" was not found");
        }
        versionManagerImp=new VersionManagerImp(session, versionSotrage);
        observationManagerImp=new ObservationManagerImp(session);
    }
    
    public NodeManager getNodeManager()
    {
        return this.nodeManager;
    }
    public Session getSession()
    {
        return session;
    }
    public NodeImp getRoot()
    {
        return nodeManager.getRoot();
    }
    public String getName()
    {
        return name;
    }

    public void copy(String srcAbsPath, String destAbsPath) throws ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void copy(String srcWorkspace, String srcAbsPath, String destAbsPath) throws NoSuchWorkspaceException, ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clone(String srcWorkspace, String srcAbsPath, String destAbsPath, boolean removeExisting) throws NoSuchWorkspaceException, ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void move(String srcAbsPath, String destAbsPath) throws ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Deprecated
    public void restore(Version[] versions, boolean removeExisting) throws ItemExistsException, UnsupportedRepositoryOperationException, VersionException, LockException, InvalidItemStateException, RepositoryException
    {
        versionManagerImp.restore(versions, removeExisting);
    }

    public LockManager getLockManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return lockManagerImp;
    }

    public LockManagerImp getLockManagerImp()
    {
        return lockManagerImp;
    }

    public QueryManager getQueryManager() throws RepositoryException
    {
        return queryManagerImp;
    }

    public NamespaceRegistry getNamespaceRegistry() throws RepositoryException
    {
        return SWBRepository.getNamespaceRegistryImp();
    }

    public NodeTypeManager getNodeTypeManager() throws RepositoryException
    {
        return this.nodeTypeManager;
    }
    public NodeTypeManagerImp getNodeTypeManagerImp()
    {
        return nodeTypeManager;
    }

    public ObservationManager getObservationManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return observationManagerImp;
    }
    public ObservationManagerImp getObservationManagerImp()
    {
        return observationManagerImp;
    }


    public VersionManager getVersionManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return versionManagerImp;
    }

    public VersionManagerImp getVersionManagerImp()
    {
        return versionManagerImp;
    }

    public String[] getAccessibleWorkspaceNames() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ContentHandler getImportContentHandler(String parentAbsPath, int uuidBehavior) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void importXML(String parentAbsPath, InputStream in, int uuidBehavior) throws IOException, VersionException, PathNotFoundException, ItemExistsException, ConstraintViolationException, InvalidSerializedDataException, LockException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void createWorkspace(String name) throws AccessDeniedException, UnsupportedRepositoryOperationException, RepositoryException
    {
        org.semanticwb.jcr283.repository.model.Workspace ws=org.semanticwb.jcr283.repository.model.base.WorkspaceBase.ClassMgr.getWorkspace(name);
        if(ws==null)
        {
            throw new UnsupportedRepositoryOperationException("The workspace "+name+" already exists");
        }
        org.semanticwb.jcr283.repository.model.base.WorkspaceBase.ClassMgr.createWorkspace(name,SWBRepository.DEFAULT_URI_WORKSPACES);
    }

    public void createWorkspace(String name, String srcWorkspace) throws AccessDeniedException, UnsupportedRepositoryOperationException, NoSuchWorkspaceException, RepositoryException
    {
        org.semanticwb.jcr283.repository.model.Workspace ws=org.semanticwb.jcr283.repository.model.base.WorkspaceBase.ClassMgr.getWorkspace(name);
        if(ws==null)
        {
            throw new UnsupportedRepositoryOperationException("The workspace "+name+" already exists");
        }
        ws=org.semanticwb.jcr283.repository.model.base.WorkspaceBase.ClassMgr.createWorkspace(name,SWBRepository.DEFAULT_URI_WORKSPACES);
        // clone de workspace
        
        
    }

    public void deleteWorkspace(String name) throws AccessDeniedException, UnsupportedRepositoryOperationException, NoSuchWorkspaceException, RepositoryException
    {
        org.semanticwb.jcr283.repository.model.Workspace ws=org.semanticwb.jcr283.repository.model.base.WorkspaceBase.ClassMgr.getWorkspace(name);
        if(ws==null)
        {
            throw new NoSuchWorkspaceException();
        }
        if(ws.getName().equals(SWBRepository.DEFAULT_WORKSPACE))
        {
            throw new AccessDeniedException("The default workspace can not be deleted");
        }
        ws.remove();
    }

}
