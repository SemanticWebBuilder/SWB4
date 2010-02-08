/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class PropertyIteratorImp implements PropertyIterator
{

    private final Iterator<PropertyImp> it;
    private final long size;
    private long position = 0;
    private final ArrayList<PropertyImp> nodes = new ArrayList<PropertyImp>();

    public PropertyIteratorImp(Set<PropertyImp> properties)
    {
        for (PropertyImp node : nodes)
        {
            this.nodes.add(node);
        }
        it = this.nodes.iterator();
        size = this.nodes.size();
    }

    public Property nextProperty()
    {
        PropertyImp node = it.next();
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
        return this.nextProperty();
    }

    public void remove()
    {
        it.remove();
    }
}
