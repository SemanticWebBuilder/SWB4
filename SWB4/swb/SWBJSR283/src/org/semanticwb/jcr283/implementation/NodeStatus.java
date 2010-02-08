/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.Calendar;
import javax.jcr.RepositoryException;

/**
 *
 * @author victor.lorenzana
 */
public final class NodeStatus
{

    private LockImp lock;
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
        return lock == null ? true : false;
    }

    public void unlock(String userid) throws RepositoryException
    {
        if (lock != null)
        {

            PropertyImp jcr_lock = session.getWorkspaceImp().getNodeManager().getProtectedProperty(node.getPathFromName("jcr:lockIsDeep"));
            jcr_lock.set(session.getValueFactory().createValue(false));

            PropertyImp jcr_lockOwner = session.getWorkspaceImp().getNodeManager().getProtectedProperty(node.getPathFromName("jcr:lockOwner"));
            jcr_lockOwner.remove();

        }
        lock = null;
    }

    public LockImp getLock()
    {
        return lock;
    }

    public LockImp lock(boolean isDeep, boolean isSessionScoped, String owner, long timeoutHint) throws RepositoryException
    {
        Calendar expiration=null;
        if (timeoutHint > 0 && timeoutHint<Long.MAX_VALUE)
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, (int)timeoutHint);
        }
        lock = new LockImp(session, node, owner, isDeep, isSessionScoped,expiration);

        PropertyImp jcr_lock = session.getWorkspaceImp().getNodeManager().getProtectedProperty(node.getPathFromName("jcr:lockIsDeep"));
        jcr_lock.set(session.getValueFactory().createValue(isDeep));

        PropertyImp jcr_lockOwner = session.getWorkspaceImp().getNodeManager().getProtectedProperty(node.getPathFromName("jcr:lockOwner"));
        jcr_lockOwner.set(session.getValueFactory().createValue(owner));

        if (!isSessionScoped)
        {
            jcr_lock.saveData();
            jcr_lockOwner.saveData();
        }
        return lock;

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
