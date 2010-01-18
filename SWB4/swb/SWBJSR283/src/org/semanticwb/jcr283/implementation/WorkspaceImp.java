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
import org.semanticwb.jcr283.repository.model.Unstructured;
import org.xml.sax.ContentHandler;

/**
 *
 * @author victor.lorenzana
 */
public class WorkspaceImp  implements Workspace {

    private final SessionImp session;
    private final NodeImp root;
    private final String name;
    private final NodeTypeManagerImp nodeTypeManagerImp=new NodeTypeManagerImp();
    public WorkspaceImp(SessionImp session,org.semanticwb.jcr283.repository.model.Workspace ws)
    {
        if(ws.getRoot()==null)
        {
            Unstructured newroot=Unstructured.ClassMgr.createUnstructured("jcr:root", ws);
            ws.setRoot(newroot);
        }
        root=new NodeImp(ws.getRoot(),null,0,"/",0,session);
        name=ws.getTitle();
        this.session=session;
        session.setWorkspace(this);
    }
    public Session getSession()
    {
        return session;
    }
    public NodeImp getRoot()
    {
        return root;
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

    public void restore(Version[] versions, boolean removeExisting) throws ItemExistsException, UnsupportedRepositoryOperationException, VersionException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LockManager getLockManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public QueryManager getQueryManager() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NamespaceRegistry getNamespaceRegistry() throws RepositoryException
    {
        return new NamespaceRegistryImp();
    }

    public NodeTypeManager getNodeTypeManager() throws RepositoryException
    {
        return nodeTypeManagerImp;
    }

    public ObservationManager getObservationManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionManager getVersionManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
        org.semanticwb.jcr283.repository.model.base.WorkspaceBase.ClassMgr.createWorkspace(name,"");
    }

    public void createWorkspace(String name, String srcWorkspace) throws AccessDeniedException, UnsupportedRepositoryOperationException, NoSuchWorkspaceException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteWorkspace(String name) throws AccessDeniedException, UnsupportedRepositoryOperationException, NoSuchWorkspaceException, RepositoryException
    {
        org.semanticwb.jcr283.repository.model.base.WorkspaceBase.ClassMgr.createWorkspace(name,"");
    }

}
