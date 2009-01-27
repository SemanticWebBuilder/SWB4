/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation;

import java.util.Iterator;
import java.util.Set;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.EventListenerIterator;

/**
 *
 * @author victor.lorenzana
 */
public class EventListenerIteratorImp implements EventListenerIterator {

    Iterator<EventListener> listeners;
    int size=0;
    int position=0;
    EventListenerIteratorImp(Set<EventListener> listeners)
    {
        this.listeners=listeners.iterator();
    }
    public EventListener nextEventListener()
    {
        return listeners.next();
    }

    public void skip(long skip)
    {
        for(int i=0;i<skip;i++)
        {
            listeners.next();
        }
    }

    public long getSize()
    {
        return size;
    }

    public long getPosition()
    {
        return position;
    }

    public boolean hasNext()
    {
        return listeners.hasNext();
    }

    public Object next()
    {
        return listeners.next();
    }

    public void remove()
    {
        listeners.remove();
    }

}
