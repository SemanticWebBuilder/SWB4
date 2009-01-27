/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;

/**
 *
 * @author victor.lorenzana
 */
public class EventIteratorImp implements EventIterator{
    
    private Iterator<Event> events;
    private int size;
    private int position=0;
    public EventIteratorImp(ArrayList<Event> events)
    {
        this.size=events.size();
        this.events=events.iterator();
    }
    public Event nextEvent()
    {
        position++;
        return events.next();
    }

    public void skip(long skip)
    {
        for(int i=0;i<skip;i++)
        {
            events.next();
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
        return events.hasNext();
    }

    public Object next()
    {
        return events.next();
    }

    public void remove()
    {
        events.remove();
    }

}
