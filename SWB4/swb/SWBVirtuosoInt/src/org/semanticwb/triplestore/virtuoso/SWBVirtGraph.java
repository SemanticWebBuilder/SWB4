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
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.GraphEvents;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.shared.AddDeniedException;
import com.hp.hpl.jena.shared.DeleteDeniedException;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.NiceIterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.triplestore.ext.GraphExt;
import virtuoso.sql.ExtendedString;
import virtuoso.sql.RdfBox;

/**
 *
 * @author jei
 */
public class SWBVirtGraph extends GraphBase implements GraphExt
{
    private static Logger log = SWBUtils.getLogger(SWBVirtGraph.class);

    static
    {
        SWBVirtuosoQueryEngine.register();
    }
    static public final String DEFAULT = "virt:DEFAULT";
    protected String graphName;
    protected boolean readFromAllGraphs = false;
    //protected String url_hostlist;
    //protected String user;
    //protected String password;
    //protected boolean roundrobin = false;
    protected int prefetchSize = 200;
    //protected Connection connection = null;
    protected String ruleSet = null;
    protected boolean useSameAs = false;
    protected int queryTimeout = 0;
    static final String sinsert = "sparql insert into graph iri(??) {`iri(??)` `iri(??)` `bif:__rdf_long_from_batch_params(??,??,??)`}";
    static final String sdelete = "sparql delete from graph iri(??) {`iri(??)` `iri(??)` `bif:__rdf_long_from_batch_params(??,??,??)`}";
    static final int BATCH_SIZE = 5000;
    static final String utf8 = "charset=utf-8";
    static final String charset = "UTF-8";
    
    protected SWBVirtPrefixMapping m_prefixMapping = null;
    protected SWBVirtTransactionHandler m_transactionHandler = new SWBVirtTransactionHandler(this);
    

    //private VirtuosoConnectionPoolDataSource pds = new VirtuosoConnectionPoolDataSource();
    //private VirtuosoDataSource ds;
    //private boolean isDSconnection = false;
    public SWBVirtGraph(String graphName)
    {
        this.graphName = graphName;
        if (this.graphName == null)
        {
            this.graphName = DEFAULT;
        }

        ModelCom m = new ModelCom(this); //don't drop is it needed for initialize internal Jena classes
        TypeMapper tm = TypeMapper.getInstance();
    }

    public String getGraphName()
    {
        return this.graphName;
    }
    
    public int getFetchSize()
    {
        return this.prefetchSize;
    }

    public void setFetchSize(int sz)
    {
        this.prefetchSize = sz;
    }

    public int getQueryTimeout()
    {
        return this.queryTimeout;
    }

    public void setQueryTimeout(int seconds)
    {
        this.queryTimeout = seconds;
    }

    public int getCount()
    {
        return size();
    }

    public void remove(List triples)
    {
        delete(triples.iterator(), null);
    }

    public void remove(Triple t)
    {
        delete(t);
    }

    public boolean getReadFromAllGraphs()
    {
        return readFromAllGraphs;
    }

    public void setReadFromAllGraphs(boolean val)
    {
        readFromAllGraphs = val;
    }

    public String getRuleSet()
    {
        return ruleSet;
    }

    public void setRuleSet(String _ruleSet)
    {
        ruleSet = _ruleSet;
    }

    public boolean getSameAs()
    {
        return useSameAs;
    }

    public void setSameAs(boolean _sameAs)
    {
        useSameAs = _sameAs;
    }

    public void createRuleSet(String ruleSetName, String uriGraphRuleSet)
    {
        //checkOpen();

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.Statement st = createStatement(con);
            st.execute("rdfs_rule_set('" + ruleSetName + "', '" + uriGraphRuleSet + "')");
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

    public void removeRuleSet(String ruleSetName, String uriGraphRuleSet)
    {
        //checkOpen();

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.Statement st = createStatement(con);
            st.execute("rdfs_rule_set('" + ruleSetName + "', '" + uriGraphRuleSet + "', 1)");
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

    private static String escapeString(String s)
    {
        return encodeText(s, true);
//        
//        StringBuffer buf = new StringBuffer(s.length());
//        int i = 0;
//        char ch;
//        while (i < s.length())
//        {
//            ch = s.charAt(i++);
//            if (ch == '\'')
//            {
//                buf.append('\\');
//            }
//            buf.append(ch);
//        }
//        return buf.toString();
    }

    protected java.sql.Statement createStatement(Connection connection) throws java.sql.SQLException
    {
        java.sql.Statement st = connection.createStatement();
        if (queryTimeout > 0)
        {
            st.setQueryTimeout(queryTimeout);
        }
        st.setFetchSize(prefetchSize);
        return st;
    }

    protected java.sql.PreparedStatement prepareStatement(Connection connection, String sql) throws java.sql.SQLException
    {
        java.sql.PreparedStatement st = connection.prepareStatement(sql);
        if (queryTimeout > 0)
        {
            st.setQueryTimeout(queryTimeout);
        }
        st.setFetchSize(prefetchSize);
        return st;
    }

// GraphBase overrides
    public static String Node2Str(Node n)
    {
        if (n.isURI())
        {
            return "<" + n + ">";
        } else if (n.isBlank())
        {
            return "<_:" + n + ">";
        } else if (n.isLiteral())
        {
            String s;
            StringBuffer sb = new StringBuffer();
            sb.append("'");
            sb.append(escapeString(n.getLiteralValue().toString()));
            sb.append("'");

            s = n.getLiteralLanguage();
            if (s != null && s.length() > 0)
            {
                sb.append("@");
                sb.append(s);
            }
            s = n.getLiteralDatatypeURI();
            if (s != null && s.length() > 0)
            {
                sb.append("^^<");
                sb.append(s);
                sb.append(">");
            }
            return sb.toString();
        } else
        {
            return "<" + n + ">";
        }
    }

    void bindSubject(PreparedStatement ps, int col, Node n) throws SQLException
    {
        if (n == null)
        {
            return;
        }
        if (n.isURI())
        {
            ps.setString(col, n.toString());
        } else if (n.isBlank())
        {
            ps.setString(col, "_:" + n.toString());
        } else
        {
            throw new SQLException("Only URI or Blank nodes can be used as subject");
        }
    }

    void bindPredicate(PreparedStatement ps, int col, Node n) throws SQLException
    {
        if (n == null)
        {
            return;
        }
        if (n.isURI())
        {
            ps.setString(col, n.toString());
        } else
        {
            throw new SQLException("Only URI nodes can be used as predicate");
        }
    }

    void bindObject(PreparedStatement ps, int col, Node n) throws SQLException
    {
        if (n == null)
        {
            return;
        }
        if (n.isURI())
        {
            ps.setInt(col, 1);
            ps.setString(col + 1, n.toString());
            ps.setNull(col + 2, java.sql.Types.VARCHAR);
        } else if (n.isBlank())
        {
            ps.setInt(col, 1);
            ps.setString(col + 1, "_:" + n.toString());
            ps.setNull(col + 2, java.sql.Types.VARCHAR);
        } else if (n.isLiteral())
        {
            String llang = n.getLiteralLanguage();
            String ltype = n.getLiteralDatatypeURI();
            if (llang != null && llang.length() > 0)
            {
                ps.setInt(col, 5);
                ps.setString(col + 1, encodeText(n.getLiteralValue().toString()));
                ps.setString(col + 2, n.getLiteralLanguage());
            } else if (ltype != null && ltype.length() > 0)
            {
                ps.setInt(col, 4);
                ps.setString(col + 1, n.getLiteralValue().toString());
                ps.setString(col + 2, n.getLiteralDatatypeURI());
            } else
            {
                ps.setInt(col, 3);
                ps.setString(col + 1, encodeText(n.getLiteralValue().toString()));
                ps.setNull(col + 2, java.sql.Types.VARCHAR);
            }
        } else
        {
            ps.setInt(col, 3);
            ps.setString(col + 1, encodeText(n.toString()));
            ps.setNull(col + 2, java.sql.Types.VARCHAR);
        }
    }

    @Override
    public void performAdd(Triple t)
    {
        if(m_transactionHandler.batchAddTransaction(t))return;
        java.sql.PreparedStatement ps;

        System.out.println("performAdd:"+t);
        try
        {
            Connection con=m_transactionHandler.getConnection();
            ps = prepareStatement(con, sinsert);
            ps.setString(1, this.graphName);
            bindSubject(ps, 2, t.getSubject());
            bindPredicate(ps, 3, t.getPredicate());
            bindObject(ps, 4, t.getObject());

            ps.execute();
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new AddDeniedException(e.toString());
        }
    }

    public void performDelete(Triple t)
    {
        if(m_transactionHandler.batchDeleteTransaction(t))return;
        java.sql.PreparedStatement ps;

        try
        {
            Connection con=m_transactionHandler.getConnection();
            ps = prepareStatement(con,sdelete);
            ps.setString(1, this.graphName);
            bindSubject(ps, 2, t.getSubject());
            bindPredicate(ps, 3, t.getPredicate());
            bindObject(ps, 4, t.getObject());

            ps.execute();
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new DeleteDeniedException(e.toString());
        }
    }

    /**
     * more efficient
     */
//--java5 or newer    @Override
    @Override
    protected int graphBaseSize()
    {
        StringBuffer sb = new StringBuffer("select count(*) from (sparql define input:storage \"\" ");

        if (ruleSet != null)
        {
            sb.append(" define input:inference '" + ruleSet + "'\n ");
        }

        if (useSameAs)
        {
            sb.append(" define input:same-as \"yes\"\n ");
        }

        if (readFromAllGraphs)
        {
            sb.append(" select * where {?s ?p ?o })f");
        } else
        {
            sb.append(" select * where { graph `iri(??)` { ?s ?p ?o }})f");
        }

        ResultSet rs = null;
        int ret = 0;

        //checkOpen();

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.PreparedStatement ps = prepareStatement(con,sb.toString());

            if (!readFromAllGraphs)
            {
                ps.setString(1, graphName);
            }

            rs = ps.executeQuery();
            if (rs.next())
            {
                ret = rs.getInt(1);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
        return ret;
    }

    /**
     * maybe more efficient than default impl
     *
     */
//--java5 or newer    @Override
    protected boolean graphBaseContains(Triple t)
    {
        ResultSet rs = null;
        String S, P, O;
        StringBuffer sb = new StringBuffer("sparql define input:storage \"\" ");
        String exec_text;

        //checkOpen();

        S = " ?s ";
        P = " ?p ";
        O = " ?o ";

        if (!Node.ANY.equals(t.getSubject()))
        {
            S = Node2Str(t.getSubject());
        }

        if (!Node.ANY.equals(t.getPredicate()))
        {
            P = Node2Str(t.getPredicate());
        }

        if (!Node.ANY.equals(t.getObject()))
        {
            O = Node2Str(t.getObject());
        }

        if (ruleSet != null)
        {
            sb.append(" define input:inference '" + ruleSet + "'\n ");
        }

        if (useSameAs)
        {
            sb.append(" define input:same-as \"yes\"\n ");
        }

        if (readFromAllGraphs)
        {
            sb.append(" select * where { " + S + " " + P + " " + O + " } limit 1");
        } else
        {
            sb.append(" select * where { graph <" + graphName + "> { " + S + " " + P + " " + O + " }} limit 1");
        }

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.Statement stmt = createStatement(con);
            rs = stmt.executeQuery(sb.toString());
            boolean ret = rs.next();
            rs.close();
            stmt.close();
            con.close();
            return ret;
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

//--java5 or newer    @Override
    public ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        String S, P, O;
        StringBuffer sb = new StringBuffer("sparql ");

        //checkOpen();

        S = " ?s ";
        P = " ?p ";
        O = " ?o ";

        if (tm.getMatchSubject() != null)
        {
            S = Node2Str(tm.getMatchSubject());
        }

        if (tm.getMatchPredicate() != null)
        {
            P = Node2Str(tm.getMatchPredicate());
        }

        if (tm.getMatchObject() != null)
        {
            O = Node2Str(tm.getMatchObject());
        }

        if (ruleSet != null)
        {
            sb.append(" define input:inference '" + ruleSet + "'\n ");
        }

        if (useSameAs)
        {
            sb.append(" define input:same-as \"yes\"\n ");
        }

        if (readFromAllGraphs)
        {
            sb.append(" select * where { " + S + " " + P + " " + O + " }");
        } else
        {
            sb.append(" select * from <" + graphName + "> where { " + S + " " + P + " " + O + " }");
        }

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = prepareStatement(con,sb.toString());
            return new SWBVirtResSetIter(this, stmt.executeQuery(), stmt, tm);
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

//--java5 or newer    @Override
    public void close()
    {
        try
        {
            super.close(); // will set closed = true
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

// Extra functions
    public void clear()
    {
        clearGraph(this.graphName);
        getEventManager().notifyEvent(this, GraphEvents.removeAll);
    }

    public void read(String url, String type)
    {
        String exec_text;

        exec_text = "sparql load \"" + url + "\" into graph <" + graphName + ">";

        checkOpen();
        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.Statement stmt = createStatement(con);
            stmt.execute(exec_text);
            stmt.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

//--java5 or newer    @SuppressWarnings("unchecked")
    void add(Iterator<Triple> it, List<Triple> list)
    {
        System.out.println("add:"+list.size());
        try
        {
            Connection con=m_transactionHandler.getConnection();
            PreparedStatement ps = prepareStatement(con,sinsert);
            int count = 0;

            while (it.hasNext())
            {
                Triple t = (Triple) it.next();

                if (list != null)
                {
                    list.add(t);
                }

                ps.setString(1, this.graphName);
                bindSubject(ps, 2, t.getSubject());
                bindPredicate(ps, 3, t.getPredicate());
                bindObject(ps, 4, t.getObject());
                ps.addBatch();
                count++;

                if (count > BATCH_SIZE)
                {
                    ps.executeBatch();
                    ps.clearBatch();
                    count = 0;
                }
            }

            if (count > 0)
            {
                ps.executeBatch();
                ps.clearBatch();
            }
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

    void delete(Iterator<Triple> it, List<Triple> list)
    {
        try
        {
            Connection con=m_transactionHandler.getConnection();
            PreparedStatement ps = prepareStatement(con,sdelete);
            int count = 0;

            while (it.hasNext())
            {
                Triple t = (Triple) it.next();

                if (list != null)
                {
                    list.add(t);
                }

                ps.setString(1, this.graphName);
                bindSubject(ps, 2, t.getSubject());
                bindPredicate(ps, 3, t.getPredicate());
                bindObject(ps, 4, t.getObject());
                ps.addBatch();
                count++;

                if (count > BATCH_SIZE)
                {
                    ps.executeBatch();
                    ps.clearBatch();
                    count = 0;
                }
            }

            if (count > 0)
            {
                ps.executeBatch();
                ps.clearBatch();
            }
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

    void delete_match(TripleMatch tm)
    {
        String S, P, O;
        Node nS, nP, nO;

        checkOpen();

        S = "?s";
        P = "?p";
        O = "?o";

        nS = tm.getMatchSubject();
        nP = tm.getMatchPredicate();
        nO = tm.getMatchObject();

        try
        {
            if (nS == null && nP == null && nO == null)
            {

                clearGraph(this.graphName);

            } else if (nS != null && nP != null && nO != null)
            {
                Connection con=m_transactionHandler.getConnection();
                java.sql.PreparedStatement ps;
                ps = prepareStatement(con,sdelete);
                ps.setString(1, this.graphName);
                bindSubject(ps, 2, nS);
                bindPredicate(ps, 3, nP);
                bindObject(ps, 4, nO);

                ps.execute();
                ps.close();
                con.close();
            } else
            {

                if (nS != null)
                {
                    S = Node2Str(nS);
                }

                if (nP != null)
                {
                    P = Node2Str(nP);
                }

                if (nO != null)
                {
                    O = Node2Str(nO);
                }

                String query = "sparql delete from graph <" + this.graphName
                        + "> { " + S + " " + P + " " + O + " } from <" + this.graphName
                        + "> where { " + S + " " + P + " " + O + " }";

                Connection con=m_transactionHandler.getConnection();
                java.sql.Statement stmt = createStatement(con);
                stmt.execute(query);
                stmt.close();
                con.close();
            }
        } catch (Exception e)
        {
            throw new DeleteDeniedException(e.toString());
        }
    }

    void clearGraph(String name)
    {
        String query = "sparql clear graph iri(??)";

        checkOpen();

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.PreparedStatement ps = prepareStatement(con,query);
            ps.setString(1, name);
            ps.execute();
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }

    @Override
    public ExtendedIterator reifierTriples(TripleMatch m)
    {
        return NiceIterator.emptyIterator();
    }

    @Override
    public int reifierSize()
    {
        return 0;
    }

//--java5 or newer    @Override
    @Override
    public TransactionHandler getTransactionHandler()
    {
        return m_transactionHandler;        
    }

//--java5 or newer    @Override
    @Override
    public BulkUpdateHandler getBulkUpdateHandler()
    {
        if (bulkHandler == null)
        {
            bulkHandler = new SWBVirtBulkUpdateHandler(this);
        }
        return bulkHandler;
    }

    @Override
    public PrefixMapping getPrefixMapping()
    {
        if (m_prefixMapping == null)
        {
            m_prefixMapping = new SWBVirtPrefixMapping(this);
        }
        return m_prefixMapping;
    }

    public static Node Object2Node(Object o)
    {
        //System.out.print("Object2Node:"+o);
        if (o == null)
        {
            return null;
        }

        //System.out.println(" "+o.getClass());        
        if (o instanceof ExtendedString)
        {
            ExtendedString vs = (ExtendedString) o;

            if (vs.getIriType() == ExtendedString.IRI && (vs.getStrType() & 0x01) == 0x01)
            {
                if (vs.toString().indexOf("_:") == 0)
                {
                    return Node.createAnon(AnonId.create(vs.toString().substring(2))); // _:
                } else
                {
                    return Node.createURI(vs.toString());
                }

            } else if (vs.getIriType() == ExtendedString.BNODE)
            {
                return Node.createAnon(AnonId.create(vs.toString().substring(9))); // nodeID://

            } else
            {
                return Node.createLiteral(decodeText(vs.toString()));
            }

        } else if (o instanceof RdfBox)
        {

            RdfBox rb = (RdfBox) o;
            String rb_type = rb.getType();
            RDFDatatype dt = null;

            if (rb_type != null)
            {
                dt = TypeMapper.getInstance().getSafeTypeByName(rb_type);
            }
            //System.out.println("-->"+rb.toString()+" "+rb.getLang()+" "+dt);
            return Node.createLiteral(decodeText(rb.toString()), rb.getLang(), dt);

        } else if (o instanceof java.lang.Integer)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#integer");
            return Node.createLiteral(o.toString(), null, dt);

        } else if (o instanceof java.lang.Short)
        {

            RDFDatatype dt = null;
//      dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#short");
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#integer");
            return Node.createLiteral(o.toString(), null, dt);

        } else if (o instanceof java.lang.Float)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#float");
            return Node.createLiteral(o.toString(), null, dt);

        } else if (o instanceof java.lang.Double)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#double");
            return Node.createLiteral(o.toString(), null, dt);

        } else if (o instanceof java.math.BigDecimal)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#decimal");
            return Node.createLiteral(o.toString(), null, dt);

        } else if (o instanceof java.sql.Blob)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#hexBinary");
            return Node.createLiteral(o.toString(), null, dt);

        } else if (o instanceof java.sql.Date)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#date");
            return Node.createLiteral(o.toString(), null, dt);

        } else if (o instanceof java.sql.Timestamp)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#dateTime");
            return Node.createLiteral(Timestamp2String((java.sql.Timestamp) o), null, dt);

        } else if (o instanceof java.sql.Time)
        {

            RDFDatatype dt = null;
            dt = TypeMapper.getInstance().getSafeTypeByName("http://www.w3.org/2001/XMLSchema#time");
            return Node.createLiteral(o.toString(), null, dt);

        } else
        {            
            return Node.createLiteral(decodeText(o.toString()));
        }
    }

    private static String Timestamp2String(java.sql.Timestamp v)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(v);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int nanos = v.getNanos();

        String yearS;
        String monthS;
        String dayS;
        String hourS;
        String minuteS;
        String secondS;
        String nanosS;
        String zeros = "000000000";
        String yearZeros = "0000";
        StringBuffer timestampBuf;

        if (year < 1000)
        {
            yearS = "" + year;
            yearS = yearZeros.substring(0, (4 - yearS.length())) + yearS;
        } else
        {
            yearS = "" + year;
        }

        if (month < 10)
        {
            monthS = "0" + month;
        } else
        {
            monthS = Integer.toString(month);
        }

        if (day < 10)
        {
            dayS = "0" + day;
        } else
        {
            dayS = Integer.toString(day);
        }

        if (hour < 10)
        {
            hourS = "0" + hour;
        } else
        {
            hourS = Integer.toString(hour);
        }

        if (minute < 10)
        {
            minuteS = "0" + minute;
        } else
        {
            minuteS = Integer.toString(minute);
        }

        if (second < 10)
        {
            secondS = "0" + second;
        } else
        {
            secondS = Integer.toString(second);
        }

        if (nanos == 0)
        {
            nanosS = "0";
        } else
        {
            nanosS = Integer.toString(nanos);

            // Add leading 0
            nanosS = zeros.substring(0, (9 - nanosS.length())) + nanosS;

            // Truncate trailing 0
            char[] nanosChar = new char[nanosS.length()];
            nanosS.getChars(0, nanosS.length(), nanosChar, 0);
            int truncIndex = 8;
            while (nanosChar[truncIndex] == '0')
            {
                truncIndex--;
            }
            nanosS = new String(nanosChar, 0, truncIndex + 1);
        }

        timestampBuf = new StringBuffer();
        timestampBuf.append(yearS);
        timestampBuf.append("-");
        timestampBuf.append(monthS);
        timestampBuf.append("-");
        timestampBuf.append(dayS);
        timestampBuf.append("T");
        timestampBuf.append(hourS);
        timestampBuf.append(":");
        timestampBuf.append(minuteS);
        timestampBuf.append(":");
        timestampBuf.append(secondS);
        timestampBuf.append(".");
        timestampBuf.append(nanosS);

        return (timestampBuf.toString());
    }

    @Override
    public long count(TripleMatch tm, String stype)
    {
        StringBuffer sb = new StringBuffer("select count(*) from (sparql define input:storage \"\" ");

        if (ruleSet != null)
        {
            sb.append(" define input:inference '" + ruleSet + "'\n ");
        }

        if (useSameAs)
        {
            sb.append(" define input:same-as \"yes\"\n ");
        }
        
        String S = "?s";
        String P = "?p";
        String O = "?o";

        if (tm.getMatchSubject() != null)
        {
            S = Node2Str(tm.getMatchSubject());
        }

        if (tm.getMatchPredicate() != null)
        {
            P = Node2Str(tm.getMatchPredicate());
        }

        if (tm.getMatchObject() != null)
        {
            O = Node2Str(tm.getMatchObject());
        }        

        if (readFromAllGraphs)
        {
            sb.append(" select * where { "+S+" "+P+" "+O+" })f");
        } else
        {
            sb.append(" select * where { graph `iri(??)` { "+S+" "+P+" "+O+" }})f");
        }

        ResultSet rs = null;
        int ret = 0;

        //checkOpen();

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.PreparedStatement ps = prepareStatement(con,sb.toString());

            if (!readFromAllGraphs)
            {
                ps.setString(1, graphName);
            }

            rs = ps.executeQuery();
            if (rs.next())
            {
                ret = rs.getInt(1);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
        return ret;
    }

    @Override
    public ExtendedIterator<Triple> find(TripleMatch tm, String stype, Long limit, Long offset, String sortby)
    {
        String S, P, O;
        StringBuffer sb = new StringBuffer("sparql ");

        //checkOpen();

        S = " ?s ";
        P = " ?p ";
        O = " ?o ";

        if (tm.getMatchSubject() != null)
        {
            S = Node2Str(tm.getMatchSubject());
        }

        if (tm.getMatchPredicate() != null)
        {
            P = Node2Str(tm.getMatchPredicate());
        }

        if (tm.getMatchObject() != null)
        {
            O = Node2Str(tm.getMatchObject());
        }

        if (ruleSet != null)
        {
            sb.append(" define input:inference '" + ruleSet + "'\n ");
        }

        if (useSameAs)
        {
            sb.append(" define input:same-as \"yes\"\n ");
        }

        if (readFromAllGraphs)
        {
            sb.append(" select * where { " + S + " " + P + " " + O + " }");
        } else
        {
            sb.append(" select * from <" + graphName + "> where { " + S + " " + P + " " + O + " }");
        }
        
        if(limit!=null)sb.append(" LIMIT "+limit);
        if(offset!=null)sb.append(" OFFSET "+offset);

        try
        {
            Connection con=m_transactionHandler.getConnection();
            java.sql.PreparedStatement stmt;
            stmt = prepareStatement(con,sb.toString());
            return new SWBVirtResSetIter(this, stmt.executeQuery(), stmt, tm);
        } catch (Exception e)
        {
            throw new JenaException(e);
        }
    }
    
    
    //Methods to codify the DB
    private static String encodeText(String str)
    {
        return encodeText(str,false);
    }    
    
    //Methods to codify the DB
    private static String encodeText(String str, boolean escapeStr)
    {
        if(str==null)return "";
        StringBuffer ret = new StringBuffer();
        for (int x = 0; x < str.length(); x++)
        {
            char ch = str.charAt(x);
            if (escapeStr && ch == '\'')
            {
                ret.append('\\');
                ret.append(ch);
            }else if (ch == '&')
            {
                ret.append("&#38;");
            }else if (ch > 126 || ch < 32)
            {
                ret.append("&#" + (int) ch + ";");
            } else
            {
                ret.append(ch);
            }
        }
        return ret.toString();
    }

    private static String decodeText(String str)
    {
        if(str==null)return "";
        StringBuffer ret = new StringBuffer();
        int l = str.length();
        for (int x = 0; x < l; x++)
        {
            char ch = str.charAt(x);
            boolean addch = false;
            if (ch == '&' && (x + 1) < l && str.charAt(x + 1) == '#')
            {
                int i = str.indexOf(";", x);
                if (i > x)
                {
                    try
                    {
                        int v = Integer.parseInt(str.substring(x + 2, i));
                        ret.append((char) v);
                        x = i;
                        addch = true;
                    } catch (NumberFormatException e)
                    { //Si no se puede parsear no se agrega
                    }
                }
            }
            if (!addch)
            {
                ret.append(ch);
            }
        }
        return ret.toString();
    }    
    
}
