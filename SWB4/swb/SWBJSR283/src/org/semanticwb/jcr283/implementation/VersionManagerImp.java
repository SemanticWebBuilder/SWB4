/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.MergeException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFactory;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionManager;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public final class VersionManagerImp implements VersionManager
{

    private static final String JCR_ISCHECKEDOUT = "jcr:isCheckedOut";
    private static final String JCR_BASE_VERSION = "jcr:baseVersion";
    private final static Logger log = SWBUtils.getLogger(VersionManagerImp.class);
    private final NodeImp versionStorage;
    private final SessionImp session;
    private final NodeManager nodeManager;
    private final ValueFactory valueFactory;

    public VersionManagerImp(SessionImp session, NodeImp versionStorage)
    {
        this.session = session;
        valueFactory=session.getValueFactoryImp();
        this.versionStorage = versionStorage;
        this.nodeManager = session.getWorkspaceImp().getNodeManager();
    }

    public NodeImp getVersionStorage()
    {
        return versionStorage;
    }

    @SuppressWarnings(value = "deprecation")
    public Version checkin(String absPath) throws VersionException, UnsupportedRepositoryOperationException, InvalidItemStateException, LockException, RepositoryException
    {
        NodeImp node = nodeManager.getNode(absPath);
        if (node == null)
        {
            throw new RepositoryException("The node " + absPath + " was not found");
        }
        if (!node.isVersionable())
        {
            throw new UnsupportedRepositoryOperationException("The node is not versionable");
        }
        if (!node.isCheckedOut())
        {
            throw new InvalidItemStateException("The node is not chekedout");
        }
        if (node.isNew() || node.isModified())
        {
            throw new UnsupportedRepositoryOperationException("The node must be saved before, because has changes or is new");
        }
        VersionHistoryImp history = getVersionHistoryImp(node.path);
        VersionImp obaseVersion = getBaseVersionImp(node);

        float versionnumber = 1.0f;
        if (!obaseVersion.getName().equals("jcr:rootVersion"))
        {
            try
            {
                versionnumber = Float.parseFloat(obaseVersion.getName());
                versionnumber += 0.1f;
            }
            catch (NumberFormatException nfe)
            {
                log.debug(nfe);
            }
        }
        VersionImp version = (VersionImp) history.insertVersionNode(String.valueOf(versionnumber));
        history.saveData();
        PropertyImp baseVersion = nodeManager.getProtectedProperty(node.getPathFromName(JCR_BASE_VERSION));
        baseVersion.set(valueFactory.createValue(version));
        baseVersion.saveData();
        PropertyImp jcr_checkout = nodeManager.getProtectedProperty(node.getPathFromName(JCR_ISCHECKEDOUT));
        jcr_checkout.set(valueFactory.createValue(false));
        jcr_checkout.saveData();
        node.isModified = false;
        return version;

    }

    @SuppressWarnings(value = "deprecation")
    public void checkout(String absPath) throws UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        NodeImp node = nodeManager.getNode(absPath);
        node.checkout();
    }

    public Version checkpoint(String absPath) throws VersionException, UnsupportedRepositoryOperationException, InvalidItemStateException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCheckedOut(String absPath) throws RepositoryException
    {
        NodeImp node = nodeManager.getNode(absPath);
        if (node == null)
        {
            throw new RepositoryException("The node " + absPath + " was not found");
        }
        if (!node.isVersionable())
        {
            throw new RepositoryException("The node is not versionable");
        }
        return node.isCheckedOut();
    }

    public VersionHistoryImp getVersionHistoryImp(String absPath) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (!ItemImp.isValidAbsPath(absPath))
        {
            throw new RepositoryException("The path is not absolute: " + absPath);
        }
        if (!session.getWorkspaceImp().getNodeManager().hasNode(absPath))
        {
            throw new RepositoryException("the node " + absPath + " was not found");
        }
        NodeImp node = session.getWorkspaceImp().getNodeManager().getNode(absPath);
        if (!node.isVersionable())
        {
            throw new UnsupportedRepositoryOperationException("The node is not versionable");
        }
        return getBaseVersionImp(node).getContainingHistoryImp();
    }

    public VersionHistory getVersionHistory(String absPath) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return getVersionHistoryImp(absPath);
    }

    public VersionImp getBaseVersionImp(NodeImp node) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (!node.isVersionable())
        {
            throw new UnsupportedRepositoryOperationException("The node is not versionable");
        }
        PropertyImp jcr_baseVersion = nodeManager.getProtectedProperty(node.getPathFromName("jcr:baseVersion"));
        VersionImp version = (VersionImp) jcr_baseVersion.getNode();
        return version;
    }

    public Version getBaseVersion(String absPath) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return getBaseVersionImp(absPath);
    }

    public VersionImp getBaseVersionImp(String absPath) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (!ItemImp.isValidAbsPath(absPath))
        {
            throw new RepositoryException("The path is not absolute: " + absPath);
        }
        if (!session.getWorkspaceImp().getNodeManager().hasNode(absPath))
        {
            throw new RepositoryException("the node " + absPath + " was not found");
        }
        NodeImp node = session.getWorkspaceImp().getNodeManager().getNode(absPath);
        if (!node.isVersionable())
        {
            throw new UnsupportedRepositoryOperationException("The node is not versionable");
        }
        return getBaseVersionImp(node);
    }

    public void restore(Version[] versions, boolean removeExisting) throws ItemExistsException, UnsupportedRepositoryOperationException, VersionException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restore(String absPath, String versionName, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restore(Version version, boolean removeExisting) throws VersionException, ItemExistsException, InvalidItemStateException, UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restore(String absPath, Version version, boolean removeExisting) throws PathNotFoundException, ItemExistsException, VersionException, ConstraintViolationException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restoreByLabel(String absPath, String versionLabel, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator merge(String absPath, String srcWorkspace, boolean bestEffort) throws NoSuchWorkspaceException, AccessDeniedException, MergeException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator merge(String absPath, String srcWorkspace, boolean bestEffort, boolean isShallow) throws NoSuchWorkspaceException, AccessDeniedException, MergeException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doneMerge(String absPath, Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelMerge(String absPath, Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node createConfiguration(String absPath) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node setActivity(Node activity) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node getActivity() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node createActivity(String title) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeActivity(Node activityNode) throws UnsupportedRepositoryOperationException, VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator merge(Node activityNode) throws VersionException, AccessDeniedException, MergeException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
