/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import javax.jcr.RepositoryException;

/**
 *
 * @author victor.lorenzana
 */
public final class NodeStatus
{

    private boolean locked;
    private boolean deleted;
    private final NodeImp node;
    private boolean allchildLoaded = false;
    private final SessionImp session;

    public NodeStatus(NodeImp node, SessionImp session)
    {
        this.deleted = false;
        this.node = node;
        this.session = session;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public void unlock()
    {
        locked = true;
    }

    public void lock(boolean isDeep, boolean sessionScope, String owner) throws RepositoryException
    {
        locked = true;
        PropertyImp jcr_lock = session.getWorkspaceImp().getNodeManager().getProtectedProperty(node.getPathFromName("jcr:lockIsDeep"));
        jcr_lock.set(session.getValueFactory().createValue(isDeep));

        PropertyImp jcr_lockOwner = session.getWorkspaceImp().getNodeManager().getProtectedProperty(node.getPathFromName("jcr:lockOwner"));
        jcr_lockOwner.set(session.getValueFactory().createValue(owner));

        if (!sessionScope)
        {
            jcr_lock.saveData();
            jcr_lockOwner.saveData();
        }

    }

    public boolean getAddChildLoaded()
    {
        return allchildLoaded;
    }

    public void allChildLoaded()
    {
        allchildLoaded = true;
    }

    public NodeImp getNode()
    {
        return node;
    }

    public void delete()
    {
        this.deleted = true;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final NodeStatus other = (NodeStatus) obj;
        if (this.node != other.node && (this.node == null || !this.node.equals(other.node)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + (this.node != null ? this.node.hashCode() : 0);
        return hash;
    }

    public void restore()
    {
        this.deleted = false;
    }

    public boolean isDeleted()
    {
        return deleted;
    }
}
