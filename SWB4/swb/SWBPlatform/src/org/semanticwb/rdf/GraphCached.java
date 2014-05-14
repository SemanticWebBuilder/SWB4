/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.rdf;

import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphListener;
import com.hp.hpl.jena.graph.TransactionHandler;
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
        super.getBulkUpdateHandler().add(base);
        this.base=base;
        getEventManager().register(this);
    }

    /**
     * Notify add triple.
     * 
     * @param g the g
     * @param t the t
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    
    public Graph getGraphBase()
    {
        return base;
    }
    
//    static long c=0;
//    @Override
//    protected ExtendedIterator<Triple> graphBaseFind(Node s, Node p, Node o)
//    {
//        System.out.println(c+" s:"+s+" p:"+p+" o:"+o);
//        c++;
//        return super.graphBaseFind(s, p, o);
//    }

    @Override
    public TransactionHandler getTransactionHandler()
    {
        TransactionHandler ret=base.getTransactionHandler();
        if(ret==null)ret=super.getTransactionHandler(); //To change body of generated methods, choose Tools | Templates.
        return ret;
    }

    @Override
    public BulkUpdateHandler getBulkUpdateHandler()
    {
        BulkUpdateHandler ret=base.getBulkUpdateHandler();
        if(ret==null)ret=super.getBulkUpdateHandler(); //To change body of generated methods, choose Tools | Templates.
        return ret;
    }
}
