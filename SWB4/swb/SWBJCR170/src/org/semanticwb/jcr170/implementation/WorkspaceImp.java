/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.observation.ObservationManager;
import javax.jcr.query.QueryManager;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import org.semanticwb.model.SWBContext;
import org.xml.sax.ContentHandler;

/**
 *
 * @author victor.lorenzana
 */
public final class WorkspaceImp implements Workspace
{

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private final SessionImp session;
    private final String workspaceName;

    WorkspaceImp(SessionImp session, String workspaceName)
    {
        this.session = session;
        this.workspaceName = workspaceName;
    }

    public Session getSession()
    {
        return session;
    }

    public String getName()
    {
        return workspaceName;
    }

    public void copy(String arg0, String arg1) throws ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void copy(String arg0, String arg1, String arg2) throws NoSuchWorkspaceException, ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void clone(String arg0, String arg1, String arg2, boolean arg3) throws NoSuchWorkspaceException, ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void move(String srcAbsPath, String destAbsPath) throws ConstraintViolationException, VersionException, AccessDeniedException, PathNotFoundException, ItemExistsException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void restore(Version[] arg0, boolean arg1) throws ItemExistsException, UnsupportedRepositoryOperationException, VersionException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public QueryManager getQueryManager() throws RepositoryException
    {
        return new QueryManagerImp(session, workspaceName);
    }

    public NamespaceRegistry getNamespaceRegistry() throws RepositoryException
    {
        return new NamespaceRegistryImp();
    }

    public NodeTypeManager getNodeTypeManager() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public ObservationManager getObservationManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public String[] getAccessibleWorkspaceNames() throws RepositoryException
    {
        ArrayList<String> workspacesNames=new ArrayList<String>();
        Iterator<org.semanticwb.repository.Workspace> workspaces=SWBContext.listWorkspaces();
        while(workspaces.hasNext())
        {
            org.semanticwb.repository.Workspace workspace=workspaces.next();
            workspacesNames.add(workspace.getURI());
        }
        return workspacesNames.toArray(new String[workspacesNames.size()]);
    }    
    public ContentHandler getImportContentHandler(String arg0, int arg1) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void importXML(String parentAbsPath, InputStream in, int arg2) throws IOException, PathNotFoundException, ItemExistsException, ConstraintViolationException, InvalidSerializedDataException, LockException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }
}
