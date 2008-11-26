/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;

/**
 *
 * @author victor.lorenzana
 */
public class LockImp implements Lock
{

    private final SimpleNode nodeLock;
    private final String lockowner;
    private final boolean isDeep;
    private final boolean isSessionScoped;
    LockImp(SimpleNode node) throws RepositoryException
    {
        this(node, false);
    }
    LockImp(SimpleNode node,boolean isSessionScoped) throws RepositoryException
    {
        if ( !(node.isLockable() && node.isLocked()) )
        {
            throw new IllegalArgumentException("The node " + node.getName() + " is not lockable or is not locked");
        }        
        nodeLock = node.getLockBaseNode();
        lockowner = node.getLockOwner();
        isDeep = node.isDeepLock();
        this.isSessionScoped=isSessionScoped;
    }

    public SimpleNode getLockBaseNode()
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
        return nodeLock;
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
