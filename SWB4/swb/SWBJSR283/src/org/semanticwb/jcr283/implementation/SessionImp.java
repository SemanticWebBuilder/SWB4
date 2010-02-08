/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;
import java.security.Principal;
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
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.retention.RetentionManager;
import javax.jcr.security.AccessControlManager;
import javax.jcr.version.VersionException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author victor.lorenzana
 */
public class SessionImp implements Session
{

    private WorkspaceImp workspace;    
    private Principal principal;
    private final SWBRepository repository;
    private boolean isLive=false;
    private final ValueFactoryImp valueFactory=new ValueFactoryImp();
    public SessionImp(SWBRepository repository,Principal principal)
    {
        this.principal = principal;
        this.repository=repository;
    }

    public WorkspaceImp getWorkspaceImp()
    {
        return workspace;
    }



    public void setWorkspace(WorkspaceImp workspace)
    {
        this.workspace = workspace;

    }

    public Repository getRepository()
    {
        return repository;
    }

    public SWBRepository getRepositoryImp()
    {
        return repository;
    }

    public String getUserID()
    {
        return principal.getName();
    }

    public String[] getAttributeNames()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getAttribute(String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Workspace getWorkspace()
    {
        return workspace;
    }

    public Node getRootNode() throws RepositoryException
    {
        return workspace.getRoot();
    }

    public Session impersonate(Credentials credentials) throws LoginException, RepositoryException
    {
        return repository.login(credentials);
    }
    @Deprecated
    public Node getNodeByUUID(String uuid) throws ItemNotFoundException, RepositoryException
    {
        return workspace.getNodeManager().getNodeByIdentifier(uuid,this);
    }

    public Node getNodeByIdentifier(String id) throws ItemNotFoundException, RepositoryException
    {
        return workspace.getNodeManager().getNodeByIdentifier(id,this);
    }

    private static boolean isValidAbsPath(String absPath)
    {
        return true;
    }

    private ItemImp getItemImp(String absPath) throws PathNotFoundException, RepositoryException
    {
        if (!isValidAbsPath(absPath))
        {
            throw new RepositoryException("The path is not a valid absolute path");
        }
        try
        {
            return getNodeImp(absPath);
        }
        catch (PathNotFoundException pnfe)
        {
            return getPropertyImp(absPath);
        }
    }
    public Item getItem(String absPath) throws PathNotFoundException, RepositoryException
    {
        return getItemImp(absPath);
    }

    public Node getNode(String absPath) throws PathNotFoundException, RepositoryException
    {
        return getNodeImp(absPath);
    }
    private NodeImp getNodeImp(String absPath) throws PathNotFoundException, RepositoryException
    {
        NodeImp node = workspace.getNodeManager().getNode(absPath,this);
        if (node == null)
        {
            throw new PathNotFoundException();
        }
        return node;
    }

    public Property getProperty(String absPath) throws PathNotFoundException, RepositoryException
    {
        return getPropertyImp(absPath);
    }
    public PropertyImp getPropertyImp(String absPath) throws PathNotFoundException, RepositoryException
    {
        PropertyImp property = workspace.getNodeManager().getProperty(absPath);
        if (property == null)
        {
            throw new PathNotFoundException();
        }
        return property;
    }

    public boolean itemExists(String absPath) throws RepositoryException
    {
        if(!nodeExists(absPath))
        {
            return propertyExists(absPath);
        }
        else
        {
            return true;
        }
    }

    public boolean nodeExists(String absPath) throws RepositoryException
    {
        return workspace.getNodeManager().hasNode(absPath);
    }

    public boolean propertyExists(String absPath) throws RepositoryException
    {
        return workspace.getNodeManager().hasProperty(absPath);
    }

    public void move(String srcAbsPath, String destAbsPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        ItemImp srcAbsPathItem =getItemImp(srcAbsPath);
        ItemImp destAbsPathItem =getItemImp(destAbsPath);
        String oldpath=srcAbsPathItem.getPath();
        String name=ItemImp.extractName(srcAbsPathItem.getPath());
        String path=destAbsPathItem.getPathFromName(name);
        if(this.itemExists(path))
        {
            throw new ItemExistsException();
        }
        if(destAbsPathItem instanceof NodeImp)
        {
            workspace.getNodeManager().move(oldpath, path, (NodeImp)destAbsPathItem);
        }        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeItem(String absPath) throws VersionException, LockException, ConstraintViolationException, AccessDeniedException, RepositoryException
    {
        
    }

    public void save() throws AccessDeniedException, ItemExistsException, ReferentialIntegrityException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        workspace.getNodeManager().validate();
        workspace.getNodeManager().save();
    }

    public void refresh(boolean keepChanges) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasPendingChanges() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ValueFactory getValueFactory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return valueFactory;
    }
    public ValueFactoryImp getValueFactoryImp()
    {
        return valueFactory;
    }

    public boolean hasPermission(String absPath, String actions) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void checkPermission(String absPath, String actions) throws AccessControlException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasCapability(String methodName, Object target, Object[] arguments) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ContentHandler getImportContentHandler(String parentAbsPath, int uuidBehavior) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void importXML(String parentAbsPath, InputStream in, int uuidBehavior) throws IOException, PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void exportSystemView(String absPath, ContentHandler contentHandler, boolean skipBinary, boolean noRecurse) throws PathNotFoundException, SAXException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void exportSystemView(String absPath, OutputStream out, boolean skipBinary, boolean noRecurse) throws IOException, PathNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void exportDocumentView(String absPath, ContentHandler contentHandler, boolean skipBinary, boolean noRecurse) throws PathNotFoundException, SAXException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void exportDocumentView(String absPath, OutputStream out, boolean skipBinary, boolean noRecurse) throws IOException, PathNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNamespacePrefix(String prefix, String uri) throws NamespaceException, RepositoryException
    {
        workspace.getNamespaceRegistry().registerNamespace(prefix, uri);
    }

    public String[] getNamespacePrefixes() throws RepositoryException
    {
        return workspace.getNamespaceRegistry().getPrefixes();
    }

    public String getNamespaceURI(String prefix) throws NamespaceException, RepositoryException
    {
        return workspace.getNamespaceRegistry().getURI(prefix);
    }

    public String getNamespacePrefix(String uri) throws NamespaceException, RepositoryException
    {
        return workspace.getNamespaceRegistry().getPrefix(uri);
    }

    public void logout()
    {
        isLive=false;        
    }

    public boolean isLive()
    {
        return isLive;
    }
    @Deprecated
    public void addLockToken(String lt)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Deprecated
    public String[] getLockTokens()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Deprecated
    public void removeLockToken(String lt)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public AccessControlManager getAccessControlManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RetentionManager getRetentionManager() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
