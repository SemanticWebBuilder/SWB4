/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bigdata;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphEvents;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.impl.GraphWithPerform;
import com.hp.hpl.jena.graph.impl.SimpleBulkUpdateHandler;
import java.util.List;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;

/**
 *
 * @author jei
 */
public class BigdataBulkUpdateHandler extends SimpleBulkUpdateHandler
{
    private boolean debug=false;

    public BigdataBulkUpdateHandler( GraphWithPerform graph )
    {
        super(graph);
    }

    @Override
    public void add(Triple[] triples)
    {
        if(debug)System.out.println("add:"+triples);
        BigdataTransactionHandler trans=((BigdataTransactionHandler)graph.getTransactionHandler());
        SailConnection con=trans.getConnection();
        if(con==null)  //is not over transaction
        {
            trans.begin();
            for (int i = 0; i < triples.length; i += 1) graph.performAdd( triples[i] );
            trans.commit();
        }else
        {
            for (int i = 0; i < triples.length; i += 1) graph.performAdd( triples[i] );
        }
        manager.notifyAddArray( graph, triples );
    }

    @Override
    protected void add(List<Triple> triples, boolean notify)
    {
        if(debug)System.out.println("add:"+triples+" "+notify);
        BigdataTransactionHandler trans=((BigdataTransactionHandler)graph.getTransactionHandler());
        SailConnection con=trans.getConnection();
        if(con==null)  //is not over transaction
        {
            trans.begin();
            for (int i = 0; i < triples.size(); i += 1) graph.performAdd( triples.get(i) );
            trans.commit();
        }else
        {
            for (int i = 0; i < triples.size(); i += 1) graph.performAdd( triples.get(i) );
        }
        if (notify) manager.notifyAddList( graph, triples );
    }

    @Override
    public void delete(Triple[] triples)
    {
        if(debug)System.out.println("delete:"+triples);
        BigdataTransactionHandler trans=((BigdataTransactionHandler)graph.getTransactionHandler());
        SailConnection con=trans.getConnection();
        if(con==null)  //is not over transaction
        {
            trans.begin();
            for (int i = 0; i < triples.length; i += 1) graph.performDelete( triples[i] );
            trans.commit();
        }else
        {
            for (int i = 0; i < triples.length; i += 1) graph.performDelete( triples[i] );
        }
        manager.notifyDeleteArray( graph, triples );
    }

    @Override
    public void delete(List<Triple> triples, boolean notify )
    {
        if(debug)System.out.println("delete:"+triples+" "+notify);
        BigdataTransactionHandler trans=((BigdataTransactionHandler)graph.getTransactionHandler());
        SailConnection con=trans.getConnection();
        if(con==null)  //is not over transaction
        {
            trans.begin();
            for (int i = 0; i < triples.size(); i += 1) graph.performDelete( triples.get(i) );
            trans.commit();
        }else
        {
            for (int i = 0; i < triples.size(); i += 1) graph.performDelete( triples.get(i) );
        }
        if (notify) manager.notifyDeleteList( graph, triples );
    }

    @Override
    public void removeAll()
    {
        if(debug)System.out.println("removeAll:");
        BigdataTransactionHandler trans=((BigdataTransactionHandler)graph.getTransactionHandler());
        SailConnection con=trans.getConnection();
        if(con==null)  //is not over transaction
        {
            trans.begin();
            con=trans.getConnection();
            try
            {
                con.removeStatements(null,null,null);
                trans.commit();
            } catch (SailException ex)
            {
                trans.abort();
            }
        }else
        {
            try
            {
                con.removeStatements(null,null,null);
            } catch (SailException ex)
            {
                throw new RuntimeException("Error adding statement", ex);
            }
        }
        notifyRemoveAll();
    }

    @Override
    public void remove(Node s, Node p, Node o)
    {
        BigdataTransactionHandler trans=((BigdataTransactionHandler)graph.getTransactionHandler());
        SailConnection con=trans.getConnection();
        if(con==null)  //is not over transaction
        {
            trans.begin();
            con=trans.getConnection();
            try
            {
                con.removeStatements((Resource) SesameUtil.node2Value(s), (URI) SesameUtil.node2Value(p), SesameUtil.node2Value(o));
                trans.commit();
            } catch (SailException ex)
            {
                trans.abort();
            }
        }else
        {
            try
            {
                con.removeStatements((Resource) SesameUtil.node2Value(s), (URI) SesameUtil.node2Value(p), SesameUtil.node2Value(o));
            } catch (SailException ex)
            {
                throw new RuntimeException("Error adding statement", ex);
            }
        }
        manager.notifyEvent( graph, GraphEvents.remove( s, p, o ) );
    }

}
