/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventJournal;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.EventListenerIterator;
import javax.jcr.observation.ObservationManager;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class ObservationManagerImp implements ObservationManager
{

    static Logger log = SWBUtils.getLogger(ObservationManagerImp.class);
    private Hashtable<EventListener, EventListenerInfo> registeredEventListeners = new Hashtable<EventListener, EventListenerInfo>();
    private final SessionImp session;
    private String userData;

    public ObservationManagerImp(SessionImp session)
    {
        this.session = session;
    }

    public void addEventListener(EventListener listener, int eventTypes, String absPath, boolean isDeep, String[] uuid, String[] nodeTypeName, boolean noLocal) throws RepositoryException
    {
        if (!registeredEventListeners.containsKey(listener))
        {
            EventListenerInfo info = new EventListenerInfo(eventTypes, absPath, isDeep, uuid, nodeTypeName, noLocal);
            registeredEventListeners.put(listener, info);
        }
        else
        {
            throw new RepositoryException("The listener was already registered");
        }
    }

    private void fileEvent(ItemImp item, int eventType)
    {
        String path = item.path;
        String id = item.id;
        for (EventListener listener : registeredEventListeners.keySet())
        {
            Collection<Event> events = new HashSet<Event>();
            EventListenerInfo info = registeredEventListeners.get(listener);
            if ((info.getEventTypes() | eventType) == eventType)
            {
                if (info.getAbsPath() != null && path.equals(info.getAbsPath()))
                {
                    EventImp event = new EventImp(EventImp.NODE_ADDED, path, session.getUserID(), id, userData);
                    events.add(event);
                }
                else if (info.getUuid() != null)
                {
                    try
                    {
                        if (item instanceof NodeImp && ((NodeImp) item).isNodeType("mix:referenceable"))
                        {
                            for (String uuid : info.getUuid())
                            {
                                try
                                {
                                    if (uuid.equals(((NodeImp) item).getUUID()))
                                    {
                                        EventImp event = new EventImp(eventType, path, session.getUserID(), id, userData);
                                        events.add(event);
                                    }
                                }
                                catch (Exception e)
                                {
                                    log.error(e);
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
                else if (info.getNodeTypeName() != null)
                {
                    for (String nodeTyeName : info.getNodeTypeName())
                    {
                        try
                        {
                            if (item instanceof NodeImp && ((NodeImp) item).isNodeType(nodeTyeName))
                            {
                                EventImp event = new EventImp(eventType, path, session.getUserID(), id, userData);
                                events.add(event);
                            }
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }
                    }
                }
                else if (info.isDeep() && info.getAbsPath() != null)
                {
                    NodeImp parent = session.getWorkspaceImp().getNodeManager().getNode(info.getAbsPath());
                    if (parent != null)
                    {
                        if (path.startsWith(parent.path))
                        {
                            EventImp event = new EventImp(eventType, path, session.getUserID(), id, userData);
                            events.add(event);
                        }
                    }
                }
                listener.onEvent(new EventIteratorImp(events));
            }

        }
    }

    public void nodeAdded(NodeImp node)
    {
        fileEvent(node, Event.NODE_ADDED);
    }

    public void nodeRemoved(NodeImp node)
    {
        fileEvent(node, Event.NODE_REMOVED);
    }

    public void nodeMoved(NodeImp node)
    {
        fileEvent(node, Event.NODE_MOVED);
    }

    public void propertyAdded(PropertyImp prop)
    {
        fileEvent(prop, Event.PROPERTY_ADDED);
    }

    public void propertyRemoved(PropertyImp prop)
    {
        fileEvent(prop, Event.PROPERTY_REMOVED);
    }

    public void propertyChanged(PropertyImp prop)
    {
        fileEvent(prop, Event.PROPERTY_CHANGED);
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
        this.userData = userData;
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
