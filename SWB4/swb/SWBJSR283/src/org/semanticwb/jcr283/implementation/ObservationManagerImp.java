/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.RepositoryException;
import javax.jcr.observation.EventJournal;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.EventListenerIterator;
import javax.jcr.observation.ObservationManager;

/**
 *
 * @author victor.lorenzana
 */
public class ObservationManagerImp implements ObservationManager{

    private final SessionImp session;
    public ObservationManagerImp(SessionImp session)
    {
        this.session=session;
    }
    public void addEventListener(EventListener listener, int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName, boolean noLocal) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeEventListener(EventListener listener) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventListenerIterator getRegisteredEventListeners() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setUserData(String userData) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventJournal getEventJournal() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventJournal getEventJournal(int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
