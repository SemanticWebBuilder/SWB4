/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;

/**
 *
 * @author victor.lorenzana
 */
public class NodeTypeIteratorImp implements NodeTypeIterator {

    private Iterator<NodeTypeImp> it;
    private final long size;
    private long position=0;
    public NodeTypeIteratorImp(HashSet<NodeTypeImp> nodetypes)
    {
        it=nodetypes.iterator();
        size=nodetypes.size();
    }
    public NodeTypeIteratorImp(Collection<NodeTypeImp> nodetypes)
    {
        it=nodetypes.iterator();
        size=nodetypes.size();
    }
    public NodeType nextNodeType()
    {
        NodeType nodeType=it.next();
        if(nodeType!=null)
            position++;
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
        
    }

}
