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

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;
import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author jei
 */
public class BigdataGraph extends GraphBase implements RGraph
{
    private static Logger log=SWBUtils.getLogger(BigdataGraph.class);

    private BigdataSail sail;
    //private BigdataSailRepository repo;
    private boolean inference=false;
    private PrefixMapping pmap;
    private BigdataTransactionHandler trans;

    private boolean debug=false;

    public BigdataGraph(BigdataSail sail)
    {
        this(sail, false);
    }

    public BigdataGraph(BigdataSail sail, boolean inference)
    {
        this.inference=inference;
        this.sail=sail;
        try
        {
            sail.initialize();
        } catch (SailException ex)
        {
            throw new RuntimeException("Error Initializing BigData Sail", ex);
        }
        pmap=new BigdataPrefixMapping(sail);
        trans=new BigdataTransactionHandler(sail);
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        if(debug)System.out.println("graphBaseFind:"+tm);
        try
        {
            return new BigdataIterator(this, sail.getDatabase().getStatements((Resource)SesameUtil.node2Value(tm.getMatchSubject()), (URI)SesameUtil.node2Value(tm.getMatchPredicate()), SesameUtil.node2Value(tm.getMatchObject())));
        }catch(RuntimeException e)
        {
            System.out.println(tm);
            throw e;
        }
//        trans.begin();
//        SailConnection con=trans.getConnection();
//        try
//        {
//            CloseableIteration close = con.getStatements((Resource) SesameUtil.node2Value(tm.getMatchSubject()), (URI) SesameUtil.node2Value(tm.getMatchPredicate()), SesameUtil.node2Value(tm.getMatchObject()), inference);
//            ExtendedIterator<Triple> res=new SesameIterator(close);
//            return res;
//        } catch (SailException ex)
//        {
//            throw new RuntimeException("Error finding Triple", ex);
//        }
    }
    
    @Override
    public void performAdd(Triple t)
    {
        performAdd(t,null);
    }    

    public void performAdd(Triple t, Long id)
    {
        if(debug)System.out.println("performAdd:"+t);
        //if(t.getObject()==null)return;
        //sail.getDatabase().addStatement((Resource)SesameUtil.node2Value(t.getSubject()), (URI)SesameUtil.node2Value(t.getPredicate()), SesameUtil.node2Value(t.getObject()));
        SailConnection con=trans.getConnection(id);
        //System.out.println("performAdd:"+id+" "+con);
        if(con==null)  //is not over transaction
        {
            trans.begin();
            con=trans.getConnection();
            try
            {
                con.addStatement((Resource) SesameUtil.node2Value(t.getSubject()), (URI) SesameUtil.node2Value(t.getPredicate()), SesameUtil.node2Value(t.getObject()));
                trans.commit();
            } catch (SailException ex)
            {
                log.error(ex);
                try
                {
                    trans.abort();
                }catch(Exception noe){}
            }
        }else
        {
            try
            {
                con.addStatement((Resource) SesameUtil.node2Value(t.getSubject()), (URI) SesameUtil.node2Value(t.getPredicate()), SesameUtil.node2Value(t.getObject()));
            } catch (SailException ex)
            {
                throw new RuntimeException("Error adding statement", ex);
            }
        }
    }

    @Override
    public void performDelete(Triple t)
    {
        performDelete(t, null);
    }
    
    public void performDelete(Triple t, Long id)
    {
        if(debug)System.out.println("performDelete:"+t);
        //sail.getDatabase().removeStatements((Resource)SesameUtil.node2Value(t.getSubject()), (URI)SesameUtil.node2Value(t.getPredicate()), SesameUtil.node2Value(t.getObject()));
        SailConnection con=trans.getConnection(id);
        if(con==null)  //is not over transaction
        {
            trans.begin();
            con=trans.getConnection();
            try
            {
                con.removeStatements((Resource) SesameUtil.node2Value(t.getSubject()), (URI) SesameUtil.node2Value(t.getPredicate()), SesameUtil.node2Value(t.getObject()));
                trans.commit();
            } catch (SailException ex)
            {
                log.error(ex);
                try
                {
                    trans.abort();
                }catch(Exception noe){}
            }
        }else
        {
            try
            {
                con.removeStatements((Resource) SesameUtil.node2Value(t.getSubject()), (URI) SesameUtil.node2Value(t.getPredicate()), SesameUtil.node2Value(t.getObject()));
            } catch (SailException ex)
            {
                throw new RuntimeException("Error adding statement", ex);
            }
        }
    }

    @Override
    public QueryHandler queryHandler()
    {
        return super.queryHandler();
    }

    @Override
    public void close()
    {
        super.close();
        try
        {
            sail.shutDown();
        } catch (SailException ex)
        {
            throw new RuntimeException("Error Shuting Down BigData Sail", ex);
        }
    }

    @Override
    public PrefixMapping getPrefixMapping()
    {
        return pmap;
    }

    @Override
    public TransactionHandler getTransactionHandler()
    {
        return trans;
    }

    @Override
    protected int graphBaseSize()
    {
        return (int)sail.getDatabase().getStatementCount();
    }

    @Override
    public BulkUpdateHandler getBulkUpdateHandler()
    {
        if (bulkHandler == null) bulkHandler = new BigdataBulkUpdateHandler( this );
        return bulkHandler;
    }

    public BigdataSail getSail()
    {
        return sail;
    }


    public TupleQueryResult executeQuery(String query, QueryLanguage ql, boolean inference)
    {
        TupleQueryResult result=null;

        /*
         * With MVCC, you read from a historical state to avoid blocking and
         * being blocked by writers.  BigdataSailRepository.getQueryConnection
         * gives you a view of the repository at the last commit point.
         */
        RepositoryConnection cxn=null;
        try {
            cxn = (new BigdataSailRepository(getSail())).getReadOnlyConnection();
            final TupleQuery tupleQuery = cxn.prepareTupleQuery(ql, query);
            tupleQuery.setIncludeInferred(inference);
            result = tupleQuery.evaluate();
        }catch(Exception e)
        {
            log.error(e);
        } finally {
            try
            {
                if(cxn!=null)cxn.close();
            } catch (RepositoryException ex)
            {
                log.error(ex);
            }
        }
        return result;

    }

    @Override
    public int getId()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String encodeSubject(Node n)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String encodeProperty(Node n)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String encodeObject(Node n)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node decodeSubject(String sub, String ext)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node decodeProperty(String sub, String ext)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node decodeObject(String sub, String ext)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
