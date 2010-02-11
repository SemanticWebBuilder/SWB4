/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;

/**
 *
 * @author victor.lorenzana
 */
public class EventIteratorImp implements EventIterator
{

    private final Iterator<Event> it;
    private final long size;
    private long position = 0;

    public EventIteratorImp(Collection<Event> events)
    {
        ArrayList<Event> oevents = new ArrayList<Event>();
        oevents.addAll(events);
        it = oevents.iterator();
        size = oevents.size();
    }

    public Event nextEvent()
    {
        Event node = it.next();
        if (node != null)
        {
            position++;
        }
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
        return this.nextEvent();
    }

    public void remove()
    {
        it.remove();
    }
}
