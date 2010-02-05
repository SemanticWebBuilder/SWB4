/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;

/**
 *
 * @author victor.lorenzana
 */
public class LockImp implements Lock{

    private final SessionImp session;
    private final NodeImp node;
    private final LockManagerImp lockManager;
    public LockImp(SessionImp session,NodeImp node,LockManagerImp lockManager)
    {
        this.session=session;
        this.node=node;
        this.lockManager=lockManager;
    }
    public String getLockOwner()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDeep()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node getNode()
    {
        return node;
    }

    public String getLockToken()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getSecondsRemaining() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLive() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSessionScoped()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLockOwningSession()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void refresh() throws LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
