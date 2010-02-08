/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import atlas.event.Event;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
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
    private String userData;
    public ObservationManagerImp(SessionImp session)
    {
        this.session=session;
    }
    public void addEventListener(EventListener listener, int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName, boolean noLocal) throws RepositoryException
    {
        if(!registeredEventListeners.containsKey(listener))
        {
            EventListenerInfo info=new EventListenerInfo(eventTypes, absPath, isDeep, uuid, nodeTypeName, noLocal);
            registeredEventListeners.put(listener, info);
        }
        else
        {
            throw new RepositoryException("The listener was already registered");
        }
    }

    public void nodeAdded(NodeImp node)
    {
        String path=node.path;
        String id=node.id;
        for(EventListener listener : registeredEventListeners.keySet())
        {
            EventListenerInfo info =registeredEventListeners.get(listener);
            if((info.getEventTypes() | EventImp.NODE_ADDED)==EventImp.NODE_ADDED)
            {
                EventImp event=new EventImp(EventImp.NODE_ADDED, path, session.getUserID(),id , userData);
            }
        }
    }
    public void nodeRemoved(NodeImp node)
    {

    }
    public void nodeMoved(NodeImp node)
    {

    }
    public void propertyAdded(PropertyImp prop)
    {

    }
    public void propertyRemoved(PropertyImp prop)
    {

    }
    public void propertyMoved(PropertyImp prop)
    {

    }

    public void removeEventListener(EventListener listener) throws RepositoryException
    {
        registeredEventListeners.remove(listener);
    }

    public EventListenerIterator getRegisteredEventListeners() throws RepositoryException
    {
        return new EventListenerIteratorImp(registeredEventListeners.keySet());
    }
    public String getUserData()
    {
        return userData;
    }
    public void setUserData(String userData) throws RepositoryException
    {
        this.userData=userData;
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
