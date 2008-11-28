/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import javax.jcr.Node;
import javax.jcr.NodeIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class NodeIteratorImp implements NodeIterator
{

    private final SessionImp session;    
    private ArrayList<SimpleNode> nodes=new ArrayList<SimpleNode>();
    private int position = 0;    

    
    NodeIteratorImp(SessionImp session, ArrayList<SimpleNode> nodes)
    {
        this.session = session;
        this.nodes=nodes;        
    }
    NodeIteratorImp(SessionImp session, SimpleNode[] nodes,int size)
    {
        this.session = session;
        for(SimpleNode node : nodes)
        {
            this.nodes.add(node);
        }
    }

    public Node nextNode()
    {        
        SimpleNode nextNode = nodes.get(position);
        position++;
        return nextNode;
    }

    public void skip(long skipNum)
    {
        for ( int i = 1; i <= skipNum; i++ )
        {
            this.nextNode();
        }
    }

    public long getSize()
    {
        return this.nodes.size();
    }

    public long getPosition()
    {
        return position;
    }

    public boolean hasNext()
    {
        if(nodes.size()>position)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Object next()
    {
        return this.nextNode();
    }

    public void remove()
    {
        SimpleNode node=nodes.get(position);
        if(node!=null)
        {
            nodes.remove(node);
        }
    }
}
