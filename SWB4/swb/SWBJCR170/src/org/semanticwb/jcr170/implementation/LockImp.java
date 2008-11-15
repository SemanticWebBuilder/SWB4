/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class LockImp implements Lock
{

    private final BaseNode node;
    private final BaseNode nodeLock;
    private final SessionImp session;
    private final String lockowner;
    private final boolean isDeep;
    private final boolean isSessionScoped;
    LockImp(BaseNode node, SessionImp session)
    {
        this(node, session,false);
    }
    LockImp(BaseNode node, SessionImp session,boolean isSessionScoped)
    {
        if ( !(node.isLockable() && node.isLocked()) )
        {
            throw new IllegalArgumentException("The node " + node.getName() + " is not lockable or is not locked");
        }
        this.node = node;
        this.session = session;
        nodeLock = node.getLockBaseNode();
        lockowner = node.getLockOwner();
        isDeep = node.isDeepLock();
        this.isSessionScoped=isSessionScoped;
    }

    public BaseNode getLockBaseNode()
    {
        return nodeLock;
    }

    public String getLockOwner()
    {
        return lockowner;
    }

    public boolean isDeep()
    {
        return isDeep;
    }

    public Node getNode()
    {
        return new NodeImp(nodeLock, session);
    }

    public String getLockToken()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLive() throws RepositoryException
    {
        boolean isLive=false;
        if(nodeLock.isLocked())
        {
            isLive=true;
        }
        return isLive;
    }

    public boolean isSessionScoped()
    {
        return isSessionScoped;
    }

    public void refresh() throws LockException, RepositoryException
    {

    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if ( obj instanceof LockImp )
        {
            equals = (( LockImp ) obj).nodeLock.equals(this.nodeLock);
        }
        return equals;
    }

    @Override
    public int hashCode()
    {
        return nodeLock.hashCode();
    }
}
