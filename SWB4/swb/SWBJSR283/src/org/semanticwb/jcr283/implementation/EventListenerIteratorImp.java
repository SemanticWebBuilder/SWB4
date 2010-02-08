/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.EventListenerIterator;


/**
 *
 * @author victor.lorenzana
 */
public class EventListenerIteratorImp implements EventListenerIterator {

    private final Iterator<EventListener> it;
    private final long size;
    private long position = 0;
    public EventListenerIteratorImp(Set<EventListener> events)
    {
        ArrayList<EventListener> oevents = new ArrayList<EventListener>();
        oevents.addAll(events);
        it=oevents.iterator();
        size=oevents.size();
    }
    public EventListener nextEventListener()
    {
        EventListener node=it.next();
        if(node!=null)
            position++;
        return node;
    }

    public void skip(long skipNum)
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
        return it.hasNext();
    }

    public Object next()
    {
        return this.nextEventListener();
    }

    public void remove()
    {
        it.remove();
    }

}
