/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.graph.GraphEvents;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.impl.SimpleBulkUpdateHandler;
import com.hp.hpl.jena.shared.JenaException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author javier.solis.g
 */
public class SWBVirtBulkUpdateHandler extends SimpleBulkUpdateHandler
{

    public SWBVirtBulkUpdateHandler(SWBVirtGraph parent) {
	super(parent);
    }

//--java5 or newer    @Override
    public void add( Triple [] triples ) { 
        System.out.println("add triples:"+triples.length);
        addIterator(Arrays.asList(triples).iterator(), false);
        manager.notifyAddArray( graph, triples );
    }

//--java5 or newer    @Override
    protected void add(List<Triple> triples, boolean notify) {
        addIterator(triples.iterator(), false);
        if (notify)
        	manager.notifyAddList( graph, triples );
    }

//--java5 or newer    @Override
    public void addIterator(Iterator<Triple> it, boolean notify) {
        System.out.println("add iterator");
	SWBVirtGraph _graph=(SWBVirtGraph)this.graph;
	List list = notify ? new ArrayList() : null;

	try {
//                _graph.m_transactionHandler.startTransaction();
//                Connection con=_graph.m_transactionHandler.getConnection();
//		boolean autoCommit = con.getAutoCommit();
//	        if (autoCommit)
//		    con.setAutoCommit(false);
		_graph.add(it, list);
//	        if (autoCommit)  {
//		    con.commit();
//		    con.setAutoCommit(true);
//		}
//                _graph.m_transactionHandler.endTransaction();
//                _graph.m_transactionHandler.closeTransaction();
	} catch (Exception e) {
		throw new JenaException("Couldn't create transaction:"+e);
	}
	if (notify)
		manager.notifyAddIterator( graph, list);
    }
    

    @Override
    public void delete( Triple [] triples ) { 
	deleteIterator(Arrays.asList(triples).iterator(), false);
        manager.notifyDeleteArray( graph, triples );
    }
    

    @Override
    protected void delete( List<Triple> triples, boolean notify ) { 
        deleteIterator(triples.iterator(), false);
        if (notify)
        	manager.notifyDeleteList( graph, triples );
    }
    

    @Override
    public void deleteIterator( Iterator<Triple> it, boolean notify ) { 
	SWBVirtGraph _graph=(SWBVirtGraph)this.graph;
	List list = notify ? new ArrayList() : null;

	try {
//            _graph.m_transactionHandler.startTransaction();
//                Connection con=_graph.m_transactionHandler.getConnection();
//		boolean autoCommit = con.getAutoCommit();
//		if (autoCommit) 
//		    con.setAutoCommit(false);
		_graph.delete(it, list);
//		if (autoCommit) {
//		    con.commit();
//		    con.setAutoCommit(true);
//		}
//                _graph.m_transactionHandler.endTransaction();
//                _graph.m_transactionHandler.closeTransaction();
	} catch (Exception e) {
		throw new JenaException("Couldn't create transaction:"+e);
	}
        if (notify) 
        	manager.notifyDeleteIterator( graph, list);
    }
    
    @Override
    public void removeAll() { 
	SWBVirtGraph _graph=(SWBVirtGraph)this.graph;
        _graph.clearGraph(_graph.getGraphName()); 
        notifyRemoveAll(); 
    }

    @Override
    public void remove( Node s, Node p, Node o ) { 
	SWBVirtGraph _graph=(SWBVirtGraph)this.graph;
        _graph.delete_match(Triple.createMatch(s, p, o));
        manager.notifyEvent( graph, GraphEvents.remove( s, p, o ) ); 
    }
}