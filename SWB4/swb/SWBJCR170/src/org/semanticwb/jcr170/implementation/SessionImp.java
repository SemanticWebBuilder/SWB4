/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;
import java.util.Hashtable;
import javax.jcr.AccessDeniedException;
import javax.jcr.Credentials;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.NamespaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;
import org.semanticwb.SWBException;
import org.semanticwb.model.SWBContext;
import org.semanticwb.repository.BaseNode;
import org.semanticwb.repository.LockUserComparator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author victor.lorenzana
 */
public class SessionImp implements Session
{

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private final RepositoryImp repository;
    private final WorkspaceImp workspace;
    private final SimpleCredentials credentials;
    private final String workspaceName;
    private final Hashtable<Node, LockImp> locksSessions = new Hashtable<Node, LockImp>();
    private SimpleLockUserComparator simpleLockUserComparator=new SimpleLockUserComparator();
    public LockUserComparator getLockUserComparator()
    {
        return simpleLockUserComparator;
    }
    SessionImp(RepositoryImp repository, String workspaceName, SimpleCredentials credentials)
    {
        if ( repository == null || workspaceName == null || credentials == null )
        {
            throw new IllegalArgumentException("The repository is null or workspace is null or credentials is null");
        }
        this.repository = repository;
        this.workspaceName = workspaceName;
        this.credentials = credentials;
        this.workspace = new WorkspaceImp(this, workspaceName);
    }

    void addLockSession(LockImp lock)
    {
        locksSessions.put(lock.getNode(), lock);
    }

    void removeLockSession(LockImp lock)
    {
        if ( lock != null )
        {
            locksSessions.remove(lock.getNode());
        }
    }

    LockImp getLock(Node node)
    {
        LockImp getLock = null;
        if ( node != null )
        {
            getLock = locksSessions.get(node);
        }
        return getLock;
    }

    public Repository getRepository()
    {
        return repository;
    }

    public String getUserID()
    {
        return credentials.getUserID();
    }

    public Object getAttribute(String arg0)
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public String[] getAttributeNames()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Workspace getWorkspace()
    {
        return this.workspace;
    }

    public Session impersonate(Credentials credentials) throws LoginException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Node getRootNode() throws RepositoryException
    {
        return new NodeImp(SWBContext.getWorkspace(this.workspace.getName()).getRoot(), this);
    }

    public Node getNodeByUUID(String uuid) throws ItemNotFoundException, RepositoryException
    {
        Query query = workspace.getQueryManager().createQuery("//.[jcr:uuid='" + uuid + "']", Query.XPATH);
        QueryResult result = query.execute();
        NodeIterator iterator = result.getNodes();
        if ( iterator.hasNext() )
        {
            return iterator.nextNode();
        }
        else
        {
            throw new ItemExistsException("The node with uuid " + uuid + " was not found");
        }
    }

    public Item getItem(String absPath) throws PathNotFoundException, RepositoryException
    {
        Item getItem = null;
        org.semanticwb.repository.Workspace ws = SWBContext.getWorkspace(workspace.getName());
        BaseNode rootBasenode = ws.getRoot();
        NodeImp root = new NodeImp(rootBasenode, this);
        String parentPath = root.getParentPath(absPath);
        BaseNode parent = root.getBaseNode(parentPath);

        if ( parent == null )
        {
            parent = rootBasenode;
        }
        String name = root.getNodeName(absPath);
        BaseNode child = NodeImp.getChildBaseNode(name, parent);
        if ( child == null )
        {
            // is a property
            NodeImp parentNode = new NodeImp(parent, this);
            getItem = parentNode.getProperty(name);
        }
        else
        {
            getItem = new NodeImp(child, this);
        }
        return getItem;
    }

    public boolean itemExists(String abspath) throws RepositoryException
    {
        boolean itemExists = false;
        try
        {
            Item item = this.getItem(abspath);
            if ( item != null )
            {
                itemExists = true;
            }
        }
        catch ( PathNotFoundException pnfe )
        {
            itemExists = false;
        }
        return itemExists;
    }

    public void move(String srcAbsPath, String destAbsPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        getRootNode().save();
    }

    public void refresh(boolean keepChanges) throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public boolean hasPendingChanges() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public ValueFactory getValueFactory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return new ValueFactoryImp();
    }

    public void checkPermission(String arg0, String arg1) throws AccessControlException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public ContentHandler getImportContentHandler(String parentAbsPath, int arg1) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void importXML(String parentAbsPath, InputStream in, int arg2) throws IOException, PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportSystemView(String absPath, ContentHandler contentHandler, boolean binaryAsLink, boolean noRecurse) throws PathNotFoundException, SAXException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportSystemView(String absPath, OutputStream out, boolean binaryAsLink, boolean noRecurse) throws IOException, PathNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportDocumentView(String absPath, ContentHandler contentHandler, boolean binaryAsLink, boolean noRecurse) throws PathNotFoundException, SAXException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportDocumentView(String absPath, OutputStream out, boolean binaryAsLink, boolean noRecurse) throws IOException, PathNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void setNamespacePrefix(String prefix, String uri) throws NamespaceException, RepositoryException
    {
        throw new NamespaceException("This operation is not supported");
    }

    public String[] getNamespacePrefixes() throws RepositoryException
    {
        NamespaceRegistryImp registry = new NamespaceRegistryImp();
        return registry.getPrefixes();
    }

    public String getNamespaceURI(String prefix) throws NamespaceException, RepositoryException
    {
        NamespaceRegistryImp registry = new NamespaceRegistryImp();
        return registry.getURI(prefix);
    }

    public String getNamespacePrefix(String uri) throws NamespaceException, RepositoryException
    {
        NamespaceRegistryImp registry = new NamespaceRegistryImp();
        return registry.getPrefix(uri);
    }

    private void releaseLockSessions()
    {
        for ( LockImp lock : locksSessions.values() )
        {
            if ( lock.isSessionScoped() )
            {
                try
                {
                    lock.getLockBaseNode().unLock(credentials.getUserID(),this.getLockUserComparator());
                }
                catch ( SWBException swbe )
                {

                }
            }
        }
    }

    public void logout()
    {
        releaseLockSessions();
    }

    public boolean isLive()
    {
        return true;
    }

    public void addLockToken(String arg0)
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public String[] getLockTokens()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void removeLockToken(String arg0)
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    BaseNode getRootBaseNode()
    {
        return SWBContext.getWorkspace(this.workspace.getName()).getRoot();
    }
}
