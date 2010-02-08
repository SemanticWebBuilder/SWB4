/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.jcr.Node;
import javax.jcr.NodeIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class NodeIteratorImp implements NodeIterator
{

    private final Iterator<NodeImp> it;
    private final long size;
    private long position = 0;

    public NodeIteratorImp(Collection<NodeImp> nodes)
    {
        ArrayList<NodeImp> values = new ArrayList<NodeImp>();
        values.addAll(nodes);
        it = values.iterator();
        size = values.size();
    }

    public Node nextNode()
    {
        NodeImp node = it.next();
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
        return this.nextNode();
    }

    public void remove()
    {
        it.remove();
    }
}
