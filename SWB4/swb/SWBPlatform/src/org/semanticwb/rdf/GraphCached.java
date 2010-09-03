/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphListener;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.mem.faster.GraphMemFaster;
import com.hp.hpl.jena.shared.PrefixMapping;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author javier.solis
 */
public class GraphCached extends GraphMemFaster implements GraphListener
{

    private Graph base=null;           //Modelo base

    public GraphCached(Graph base)
    {
        super();
        getBulkUpdateHandler().add(base);
        this.base=base;
        getEventManager().register(this);
    }

    public void notifyAddTriple(Graph g, Triple t) {
        //System.out.println("notifyAddTriple");
        base.add(t);
    }

    public void notifyAddArray(Graph g, Triple[] triples) {
        //System.out.println("notifyAddArray");
        base.getBulkUpdateHandler().add(triples);
    }

    public void notifyAddList(Graph g, List<Triple> triples) {
        //System.out.println("notifyAddList");
        base.getBulkUpdateHandler().add(triples);
    }

    public void notifyAddIterator(Graph g, Iterator<Triple> it) {
        //System.out.println("notifyAddIterator");
        base.getBulkUpdateHandler().add(it);
    }

    public void notifyAddGraph(Graph g, Graph added) {
        //System.out.println("notifyAddGraph");
        base.getBulkUpdateHandler().add(added);
    }

    public void notifyDeleteTriple(Graph g, Triple t) {
        //System.out.println("notifyDeleteTriple");
        base.delete(t);
    }

    public void notifyDeleteList(Graph g, List<Triple> L) {
        //System.out.println("notifyDeleteList");
        base.getBulkUpdateHandler().delete(L);
    }

    public void notifyDeleteArray(Graph g, Triple[] triples) {
        //System.out.println("notifyDeleteArray");
        base.getBulkUpdateHandler().delete(triples);
    }

    public void notifyDeleteIterator(Graph g, Iterator<Triple> it) {
        //System.out.println("notifyDeleteIterator");
        base.getBulkUpdateHandler().delete(it);
    }

    public void notifyDeleteGraph(Graph g, Graph removed) {
        //System.out.println("notifyDeleteGraph");
        base.getBulkUpdateHandler().delete(removed);
    }

    public void notifyEvent(Graph source, Object value) {
        //System.out.println("notifyEvent");
    }

    @Override
    public PrefixMapping getPrefixMapping() {
        return base.getPrefixMapping();
    }




}
