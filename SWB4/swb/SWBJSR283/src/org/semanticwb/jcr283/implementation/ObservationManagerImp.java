/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import java.util.Hashtable;
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

    private Hashtable<EventListener,EventListenerInfo> registeredEventListeners=new Hashtable<EventListener, EventListenerInfo>();
    private final SessionImp session;
    public ObservationManagerImp(SessionImp session)
    {
        this.session=session;
    }
    public void addEventListener(EventListener listener, int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName, boolean noLocal) throws RepositoryException
    {
        if(!registeredEventListeners.containsKey(listener))
        {
            EventListenerInfo info=new EventListenerInfo();
            registeredEventListeners.put(listener, info);
        }
        else
        {
            throw new RepositoryException("The listener was already registered");
        }
    }

    public void removeEventListener(EventListener listener) throws RepositoryException
    {
        registeredEventListeners.remove(listener);
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
