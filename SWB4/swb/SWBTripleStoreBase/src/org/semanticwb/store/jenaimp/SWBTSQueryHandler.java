/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.jenaimp;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.graph.query.SimpleQueryHandler;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 *
 * @author javier.solis.g
 */
public class SWBTSQueryHandler extends SimpleQueryHandler implements QueryHandler
{
    public SWBTSQueryHandler(Graph graph)
    {
        super(graph) ;
    }

    // ----------------------
    @Override
    public boolean containsNode(Node n)
    {
        return super.containsNode(n) ;
    }

    @Override
    public ExtendedIterator<Node> subjectsFor(Node p, Node o)
    {
        System.out.println("subjectsFor:"+p+" "+o);
        return super.subjectsFor(p, o) ;
    }

    @Override
    public ExtendedIterator<Node> predicatesFor(Node s, Node o)
    {
        System.out.println("predicatesFor:"+s+" "+o);
        return super.predicatesFor(s, o) ;
    }

    @Override
    public ExtendedIterator<Node> objectsFor(Node s, Node p)
    {
        System.out.println("objectsFor:"+s+" "+p);
        return super.objectsFor(s, p) ;
    }
}
