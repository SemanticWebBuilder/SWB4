/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import com.hp.hpl.jena.sparql.engine.iterator.QueryIterConcat;
import com.hp.hpl.jena.sparql.util.Context;
import com.hp.hpl.jena.sparql.util.ModelUtils;
import com.hp.hpl.jena.util.FileManager;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author javier.solis.g
 */
public class SWBVirtuosoQueryExecution implements QueryExecution
{

    private QueryIterConcat output = null;
    private String virt_graph = null;
    private SWBVirtGraph graph;
    private String virt_query;
    private QuerySolution m_arg = null;
    private java.sql.Statement stmt = null;

    public SWBVirtuosoQueryExecution(String query, SWBVirtGraph _graph)
    {
        //System.out.println("query:"+query);
        graph = _graph;
        virt_graph = graph.getGraphName();
        virt_query = query;
    }

    public ResultSet execSelect()
    {
        ResultSet ret = null;

        try
        {
            Connection con = graph.m_transactionHandler.getConnection();
            stmt = graph.createStatement(con);
            java.sql.ResultSet rs = stmt.executeQuery(getQuery());
            return new VResultSet(graph, rs, stmt);
        } catch (Exception e)
        {
            throw new JenaException("Can not create ResultSet.:" + e);
        }
    }

    public void setFileManager(FileManager arg)
    {
        throw new JenaException("UnsupportedMethodException");
    }

    public void setInitialBinding(QuerySolution arg)
    {
        m_arg = arg;
    }

    public Dataset getDataset()
    {
        //return new VirtDataSource(graph);
        return null;
    }

    public Context getContext()
    {
        return null;
    }

    public Model execConstruct()
    {
        return execConstruct(ModelFactory.createDefaultModel());
    }

    public Model execConstruct(Model model)
    {
        try
        {
            Connection con = graph.m_transactionHandler.getConnection();
            stmt = graph.createStatement(con);
            java.sql.ResultSet rs = stmt.executeQuery(getQuery());
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next())
            {
                Node s = SWBVirtGraph.Object2Node(rs.getObject(1));
                Node p = SWBVirtGraph.Object2Node(rs.getObject(2));
                Node o = SWBVirtGraph.Object2Node(rs.getObject(3));
                com.hp.hpl.jena.rdf.model.Statement st = ModelUtils.tripleToStatement(model, new Triple(s, p, o));
                if (st != null)
                {
                    model.add(st);
                }
            }
            rs.close();
            stmt.close();
            stmt = null;
            con.close();
        } catch (Exception e)
        {
            throw new JenaException("Convert results are FAILED.:" + e);
        }
        return model;
    }

    public Model execDescribe()
    {
        return execDescribe(ModelFactory.createDefaultModel());
    }

    public Model execDescribe(Model model)
    {
        try
        {
            Connection con = graph.m_transactionHandler.getConnection();
            stmt = graph.createStatement(con);
            java.sql.ResultSet rs = stmt.executeQuery(getQuery());
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next())
            {
                Node s = SWBVirtGraph.Object2Node(rs.getObject(1));
                Node p = SWBVirtGraph.Object2Node(rs.getObject(2));
                Node o = SWBVirtGraph.Object2Node(rs.getObject(3));

                com.hp.hpl.jena.rdf.model.Statement st = ModelUtils.tripleToStatement(model, new Triple(s, p, o));
                if (st != null)
                {
                    model.add(st);
                }
            }
            rs.close();
            stmt.close();
            stmt = null;
            con.close();

        } catch (Exception e)
        {
            throw new JenaException("Convert results are FAILED.:" + e);
        }
        return model;
    }

    public boolean execAsk()
    {
        boolean ret = false;

        try
        {
            Connection con = graph.m_transactionHandler.getConnection();
            stmt = graph.createStatement(con);
            java.sql.ResultSet rs = stmt.executeQuery(getQuery());
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next())
            {
                if (rs.getInt(1) == 1)
                {
                    ret = true;
                }
            }
            rs.close();
            stmt.close();
            stmt = null;
            con.close();
        } catch (Exception e)
        {
            throw new JenaException("Convert results are FAILED.:" + e);
        }
        return ret;
    }

    public void abort()
    {
        if (stmt != null)
        {
            try
            {
                stmt.cancel();
            } catch (Exception e)
            {
            }
        }
    }

    public void close()
    {
        if (stmt != null)
        {
            try
            {
                stmt.cancel();
                stmt.close();
            } catch (Exception e)
            {
            }
        }
    }

    private String substBindings(String query)
    {
        if (m_arg == null)
        {
            return query;
        }

        StringBuffer buf = new StringBuffer();
        String delim = " ,)(;.";
        int i = 0;
        char ch;
        int qlen = query.length();
        while (i < qlen)
        {
            ch = query.charAt(i++);
            if (ch == '\\')
            {
                buf.append(ch);
                if (i < qlen)
                {
                    buf.append(query.charAt(i++));
                }

            } else if (ch == '"' || ch == '\'')
            {
                char end = ch;
                buf.append(ch);
                while (i < qlen)
                {
                    ch = query.charAt(i++);
                    buf.append(ch);
                    if (ch == end)
                    {
                        break;
                    }
                }
            } else if (ch == '?')
            {  //Parameter
                String varData = null;
                int j = i;
                while (j < qlen && delim.indexOf(query.charAt(j)) < 0)
                {
                    j++;
                }
                if (j != i)
                {
                    String varName = query.substring(i, j);
                    RDFNode val = m_arg.get(varName);
                    if (val != null)
                    {
                        varData = SWBVirtGraph.Node2Str(val.asNode());
                        i = j;
                    }
                }
                if (varData != null)
                {
                    buf.append(varData);
                } else
                {
                    buf.append(ch);
                }
            } else
            {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    private String getQuery()
    {
        StringBuffer sb = new StringBuffer("sparql\n ");

        if (graph.getRuleSet() != null)
        {
            sb.append(" define input:inference '" + graph.getRuleSet() + "'\n");
        }

        if (graph.getSameAs())
        {
            sb.append(" define input:same-as \"yes\"\n");
        }

        if (!graph.getReadFromAllGraphs())
        {
            sb.append(" define input:default-graph-uri <" + graph.getGraphName() + "> \n");
        }

        sb.append(substBindings(virt_query));

        return sb.toString();
    }

    ///=== Inner class ===========================================
    public class VResultSet implements com.hp.hpl.jena.query.ResultSet
    {

        ResultSetMetaData rsmd;
        java.sql.ResultSet rs;
        java.sql.Statement st;
        boolean v_finished = false;
        boolean v_prefetched = false;
        SWBVirtModel m;
        Binding v_row;
        List<String> resVars = new LinkedList();
        int row_id = 0;

        protected VResultSet(SWBVirtGraph _g, java.sql.ResultSet _rs, java.sql.Statement _st)
        {
            rs = _rs;
            st = _st;
            m = new SWBVirtModel(_g);

            try
            {
                rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    resVars.add(rsmd.getColumnLabel(i));
                }

                if (virt_graph != null && !virt_graph.equals("virt:DEFAULT"))
                {
                    resVars.add("graph");
                }
            } catch (Exception e)
            {
                throw new JenaException("ViruosoResultBindingsToJenaResults is FAILED.:" + e);
            }
        }

        public boolean hasNext()
        {
            if (!v_finished && !v_prefetched)
            {
                moveForward();
            }
            return !v_finished;
        }

        public QuerySolution next()
        {
            Binding binding = nextBinding();

            if (v_finished)
            {
                throw new NoSuchElementException();
            }

            return new ResultBinding(m, binding);
        }

        public QuerySolution nextSolution()
        {
            return next();
        }

        public Binding nextBinding()
        {
            if (!v_finished && !v_prefetched)
            {
                moveForward();
            }

            v_prefetched = false;

            if (v_finished)
            {
                throw new NoSuchElementException();
            }

            return v_row;
        }

        public int getRowNumber()
        {
            return row_id;
        }

        public List<String> getResultVars()
        {
            return resVars;
        }

        public Model getResourceModel()
        {
            return m;
        }

        protected void finalize() throws Throwable
        {
            if (!v_finished)
            {
                try
                {
                    close();
                } catch (Exception e)
                {
                }
            }
        }

        protected void moveForward() throws JenaException
        {
            try
            {
                if (!v_finished && rs.next())
                {
                    extractRow();
                    v_prefetched = true;
                } else
                {
                    close();
                }
            } catch (Exception e)
            {
                throw new JenaException("Convert results are FAILED.:" + e);
            }
        }

        protected void extractRow() throws Exception
        {
            v_row = new BindingMap();
            row_id++;

            try
            {
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    Node n = SWBVirtGraph.Object2Node(rs.getObject(i));
                    if (n != null)
                    {
                        v_row.add(Var.alloc(rsmd.getColumnLabel(i)), n);
                    }
                }

                if (virt_graph != null && !virt_graph.equals("virt:DEFAULT"))
                {
                    v_row.add(Var.alloc("graph"), Node.createURI(virt_graph));
                }
            } catch (Exception e)
            {
                throw new JenaException("ViruosoResultBindingsToJenaResults is FAILED.:" + e);
            }
        }

        public void remove() throws java.lang.UnsupportedOperationException
        {
            throw new UnsupportedOperationException(this.getClass().getName() + ".remove");
        }

        private void close()
        {
            //System.out.println("rs.close():"+v_finished);
            if (!v_finished)
            {
                if (rs != null)
                {
                    try
                    {
                        //System.out.println("rs.close2():"+v_finished+" "+st.getConnection());
                        Connection con = st.getConnection();
                        rs.close();
                        rs = null;
                        st.close();
                        con.close();

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            v_finished = true;
        }
    }
}