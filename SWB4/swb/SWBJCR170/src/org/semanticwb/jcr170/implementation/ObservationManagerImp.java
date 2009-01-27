/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.HashMap;

import javax.jcr.RepositoryException;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.EventListenerIterator;
import javax.jcr.observation.ObservationManager;
import javax.jcr.observation.Event;
import org.semanticwb.jcr170.implementation.EventListenerConfiguration;

/**
 *
 * @author victor.lorenzana
 */
public class ObservationManagerImp implements ObservationManager
{

    HashMap<EventListener, EventListenerConfiguration> listeners = new HashMap<EventListener, EventListenerConfiguration>();

    public void addEventListener(EventListener listener, int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName, boolean noLocal) throws RepositoryException
    {

        EventListenerConfiguration conf = new EventListenerConfiguration();
        conf.eventTypes = eventTypes;
        conf.absPath = absPath;
        conf.isDeep = isDeep;
        conf.noLocal = noLocal;
        conf.nodeTypeName = nodeTypeName;
        conf.uuid = uuid;
        listeners.put(listener, conf);
    }

    public void removeEventListener(EventListener listener) throws RepositoryException
    {
        listeners.remove(this);
    }

    public EventListenerIterator getRegisteredEventListeners() throws RepositoryException
    {
        return new EventListenerIteratorImp(listeners.keySet());
    }

    public void onNodeRemoved(SimpleNode newNode, SessionImp session, String nodeType)
    {
        try
        {
            EventImp evt = new EventImp(Event.NODE_ADDED, newNode.getPath(), session.getUserID());
            fireEvent(evt, newNode.getUUID(), nodeType);
        }
        catch (RepositoryException re)
        {
        }
    }
    public void onAddNode(SimpleNode newNode, SessionImp session, String nodeType)
    {
        try
        {
            EventImp evt = new EventImp(Event.NODE_ADDED, newNode.getPath(), session.getUserID());
            fireEvent(evt, newNode.getUUID(), nodeType);
        }
        catch (RepositoryException re)
        {
        }
    }

    public boolean isEventType(int type, int eventTypes)
    {
        int res = eventTypes % Event.NODE_ADDED;
        if (res == Event.NODE_ADDED)
        {
            return true;
        }
        res = eventTypes % Event.NODE_REMOVED;
        if (res == Event.NODE_REMOVED)
        {
            return true;
        }
        res = eventTypes % Event.PROPERTY_ADDED;
        if (res == Event.PROPERTY_ADDED)
        {
            return true;
        }
        res = eventTypes % Event.PROPERTY_CHANGED;
        if (res == Event.PROPERTY_CHANGED)
        {
            return true;
        }
        res = eventTypes % Event.PROPERTY_REMOVED;
        if (res == Event.PROPERTY_REMOVED)
        {
            return true;
        }
        return false;
    }

    private boolean checkNodeType(EventListenerConfiguration conf, String nodeType) throws RepositoryException
    {
        boolean checkNodeType=false;
        if(conf.nodeTypeName==null)
        {
            checkNodeType=true;
        }
        else
        {
            for(String nodeTypeToListener : conf.nodeTypeName)
            {
                if(nodeTypeToListener.equals(nodeType))
                {
                    checkNodeType=true;
                    break;
                }
            }
        }
        return checkNodeType;
    }
    private boolean checkUUID(EventListenerConfiguration conf, String uuid) throws RepositoryException
    {
        boolean checkUUID = false;
        if (conf.uuid == null)
        {
            checkUUID = true;
        }
        else
        {
            for (String uuidToListener : conf.uuid)
            {
                if (uuid.equals(uuidToListener))
                {
                    checkUUID=true;
                    break;
                }
            }
        }
        return checkUUID;
    }

    private boolean checkPath(EventListenerConfiguration conf, EventImp event) throws RepositoryException
    {
        boolean checkPath = false;
        if (conf.isDeep)
        {
            if (conf.absPath != null && conf.absPath.startsWith(event.getPath()))
            {
                return true;
            }
        }
        else
        {
            if (conf.absPath != null && conf.absPath.equals(event.getPath()))
            {
                return true;
            }
        }
        return checkPath;
    }

    public void fireEvent(EventImp event, String uuid, String nodeType)
    {
        for (EventListener listener : this.listeners.keySet())
        {
            EventListenerConfiguration conf = this.listeners.get(listener);
            boolean fire = false;
            try
            {
                if (isEventType(event.getType(), conf.eventTypes))
                {
                    if (checkPath(conf, event) && checkUUID(conf, uuid) && checkNodeType(conf, nodeType))
                    {
                        fire = true;
                    }                    
                }
            }
            catch (Exception e)
            {
            }
            if (fire)
            {
                ArrayList<Event> events = new ArrayList<Event>();
                listener.onEvent(new EventIteratorImp(events));
            }
        }

    }
}
