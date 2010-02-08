/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class NodeTypeIteratorImp implements NodeTypeIterator
{

    private final Iterator<NodeTypeImp> it;
    private final long size;
    private long position = 0;
    

    public NodeTypeIteratorImp(Collection<NodeTypeImp> nodeTypes)
    {
        ArrayList<NodeTypeImp> values = new ArrayList<NodeTypeImp>();
        values.addAll(nodeTypes);
        it = values.iterator();
        size = values.size();
    }

    public NodeType nextNodeType()
    {
        NodeType nodeType = it.next();
        if (nodeType != null)
        {
            position++;
        }
        return nodeType;
    }

    public void skip(long skipNum)
    {
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
        return this.nextNodeType();
    }

    public void remove()
    {
        it.remove();
    }
}
