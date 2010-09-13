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

// TODO: Auto-generated Javadoc
/**
 * The Class GraphCached.
 * 
 * @author javier.solis
 */
public class GraphCached extends GraphMemFaster implements GraphListener
{

    /** The base. */
    private Graph base=null;           //Modelo base

    /**
     * Instantiates a new graph cached.
     * 
     * @param base the base
     */
    public GraphCached(Graph base)
    {
        super();
        getBulkUpdateHandler().add(base);
        this.base=base;
        getEventManager().register(this);
    }

    /**
     * Notify add triple.
     * 
     * @param g the g
     * @param t the t
     */
    public void notifyAddTriple(Graph g, Triple t) {
        //System.out.println("notifyAddTriple");
        base.add(t);
    }

    /**
     * Notify add array.
     * 
     * @param g the g
     * @param triples the triples
     */
    public void notifyAddArray(Graph g, Triple[] triples) {
        //System.out.println("notifyAddArray");
        base.getBulkUpdateHandler().add(triples);
    }

    /**
     * Notify add list.
     * 
     * @param g the g
     * @param triples the triples
     */
    public void notifyAddList(Graph g, List<Triple> triples) {
        //System.out.println("notifyAddList");
        base.getBulkUpdateHandler().add(triples);
    }

    /**
     * Notify add iterator.
     * 
     * @param g the g
     * @param it the it
     */
    public void notifyAddIterator(Graph g, Iterator<Triple> it) {
        //System.out.println("notifyAddIterator");
        base.getBulkUpdateHandler().add(it);
    }

    /**
     * Notify add graph.
     * 
     * @param g the g
     * @param added the added
     */
    public void notifyAddGraph(Graph g, Graph added) {
        //System.out.println("notifyAddGraph");
        base.getBulkUpdateHandler().add(added);
    }

    /**
     * Notify delete triple.
     * 
     * @param g the g
     * @param t the t
     */
    public void notifyDeleteTriple(Graph g, Triple t) {
        //System.out.println("notifyDeleteTriple");
        base.delete(t);
    }

    /**
     * Notify delete list.
     * 
     * @param g the g
     * @param L the l
     */
    public void notifyDeleteList(Graph g, List<Triple> L) {
        //System.out.println("notifyDeleteList");
        base.getBulkUpdateHandler().delete(L);
    }

    /**
     * Notify delete array.
     * 
     * @param g the g
     * @param triples the triples
     */
    public void notifyDeleteArray(Graph g, Triple[] triples) {
        //System.out.println("notifyDeleteArray");
        base.getBulkUpdateHandler().delete(triples);
    }

    /**
     * Notify delete iterator.
     * 
     * @param g the g
     * @param it the it
     */
    public void notifyDeleteIterator(Graph g, Iterator<Triple> it) {
        //System.out.println("notifyDeleteIterator");
        base.getBulkUpdateHandler().delete(it);
    }

    /**
     * Notify delete graph.
     * 
     * @param g the g
     * @param removed the removed
     */
    public void notifyDeleteGraph(Graph g, Graph removed) {
        //System.out.println("notifyDeleteGraph");
        base.getBulkUpdateHandler().delete(removed);
    }

    /**
     * Notify event.
     * 
     * @param source the source
     * @param value the value
     */
    public void notifyEvent(Graph source, Object value) {
        //System.out.println("notifyEvent");
    }

    /**
     * Gets the prefix mapping.
     * 
     * @return the prefix mapping
     */
    @Override
    public PrefixMapping getPrefixMapping() {
        return base.getPrefixMapping();
    }




}
