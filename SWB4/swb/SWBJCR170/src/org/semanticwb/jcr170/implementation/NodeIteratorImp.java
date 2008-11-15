/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public final class NodeIteratorImp implements NodeIterator
{

    private final SessionImp session;    
    private final Iterator<BaseNode> nodes;
    private int size = 0;
    private int position = 0;    

    NodeIteratorImp(SessionImp session, NodeImp root,int size)
    {        
        this(session,root.getBaseNode().listNodes(),size);
    }
    NodeIteratorImp(SessionImp session, ArrayList<BaseNode> nodes)
    {
        this(session,nodes.iterator(),nodes.size());
    }
    NodeIteratorImp(SessionImp session, Iterator<BaseNode> nodes,int size)
    {
        this.session = session;
        this.nodes=nodes;
        this.size=size;                      
    }

    public Node nextNode()
    {
        BaseNode basenextNode=nodes.next();
        NodeImp nextNode = new NodeImp(basenextNode, session,0);
        position++;
        return nextNode;
    }

    public void skip(long skipNum)
    {
        for ( int i = 1; i <= skipNum; i++ )
        {
            nodes.next();
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
        return nodes.hasNext();
    }

    public Object next()
    {
        return this.nextNode();
    }

    public void remove()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
