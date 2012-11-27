/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
