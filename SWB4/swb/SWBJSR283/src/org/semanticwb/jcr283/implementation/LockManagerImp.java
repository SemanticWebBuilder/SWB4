/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.lock.LockManager;

/**
 *
 * @author victor.lorenzana
 */
public class LockManagerImp implements LockManager {

    private final NodeManager nodeManager;
    private final SessionImp session;
    public LockManagerImp(SessionImp session, NodeManager nodeManager)
    {
        this.nodeManager=nodeManager;
        this.session=session;
    }
    public void addLockToken(String lockToken) throws LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Lock getLock(String absPath) throws PathNotFoundException, LockException, AccessDeniedException, RepositoryException
    {
        NodeImp node=nodeManager.getNode(absPath);
        if(node==null)
        {
             throw new PathNotFoundException("The path "+absPath+" was not found");
        }
        return new LockImp(session,node,this);
    }

    public String[] getLockTokens() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean holdsLock(String absPath) throws PathNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Lock lock(String absPath, boolean isDeep, boolean isSessionScoped, long timeoutHint, String ownerInfo) throws LockException, PathNotFoundException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        NodeStatus status=nodeManager.getNodeStatus(absPath);
        if(status==null)
        {
            throw new PathNotFoundException("The path "+absPath+" was not found");
        }
        if(status.isLocked())
        {
            throw new InvalidItemStateException("Tne node is already locked");
        }
        status.lock();
        return new LockImp(session, status.getNode(), this);
    }

    public boolean isLocked(String absPath) throws PathNotFoundException, RepositoryException
    {
        NodeStatus status=nodeManager.getNodeStatus(absPath);
        if(status==null)
        {
            throw new PathNotFoundException("The path "+absPath+" was not found");
        }
        return status.isLocked();
    }

    public void removeLockToken(String lockToken) throws LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unlock(String absPath) throws PathNotFoundException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        NodeStatus status=nodeManager.getNodeStatus(absPath);
        if(status==null)
        {
            throw new PathNotFoundException("The path "+absPath+" was not found");
        }
        status.unlock();
    }

}
