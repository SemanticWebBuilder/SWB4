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
