/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.Calendar;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;

/**
 *
 * @author victor.lorenzana
 */
public final class LockImp implements Lock
{

    private final SessionImp session;
    private final NodeImp node;
    private final String lockOwner;
    private final boolean isDeep;
    private final boolean isSessionScoped;
    private final Calendar expiration;

    public LockImp(SessionImp session, NodeImp node, String lockOwner, boolean isDeep, boolean isSessionScoped, Calendar expiration)
    {
        this.session = session;
        this.node = node;
        this.lockOwner = lockOwner;
        this.isDeep = isDeep;
        this.isSessionScoped = isSessionScoped;
        this.expiration = expiration;
    }

    public String getLockOwner()
    {
        return lockOwner;
    }

    public boolean isDeep()
    {
        return isDeep;
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
        if (expiration != null)
        {
            Calendar now = Calendar.getInstance();
            long millis = expiration.getTimeInMillis() - now.getTimeInMillis();
            long seconds = millis / 1000;
            return seconds;
        }
        else
        {
            return Long.MAX_VALUE;
        }
    }

    public boolean isLive() throws RepositoryException
    {
        return getSecondsRemaining() > 0 || getSecondsRemaining() == Long.MAX_VALUE ? true : false;
    }

    public boolean isSessionScoped()
    {
        return isSessionScoped;
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
