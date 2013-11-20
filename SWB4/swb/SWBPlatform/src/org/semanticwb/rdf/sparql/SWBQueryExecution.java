/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rdf.sparql;

import com.hp.hpl.jena.datatypes.BaseDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.impl.LiteralLabelFactory;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import com.hp.hpl.jena.sparql.util.Context;
import com.hp.hpl.jena.util.FileManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author javier.solis.g
 */
public class SWBQueryExecution implements QueryExecution
{
    private static Logger log=SWBUtils.getLogger(SWBQueryExecution.class);
    
    private Model model;
    private String query;
    private QuerySolution m_arg = null;
    private SparqlProcessor processor=null;

    public SWBQueryExecution(Model model, String query)
    {
        this.model=model;
        this.query = query;
        
        processor=new SparqlProcessor();
        processor.process(model, query);
    }
    
    
    public String getInternalQuery()
    {
        return processor.getIternalQuery(""+((RGraph)model.getGraph()).getId());
    }    
    
    @Override
    public ResultSet execSelect()
    {
        String q=processor.getIternalQuery(""+((RGraph)model.getGraph()).getId());
        
        //debug
        {
            String iquery=q.replace('?', '&');        
            Iterator<ParamNode> it=processor.getSQLParams().iterator();
            while (it.hasNext())
            {
                QueryNode node = it.next().getNode();
                if(node.isIRI())
                {
                    iquery=iquery.replaceFirst("&", "'uri|"+node.getValue().substring(1,node.getValue().length()-1)+"'");
                }
                else if(node.isString())
                {
                    iquery=iquery.replaceFirst("&", "'lit|_|_|"+node.getValue()+"'");
                }
                else
                {
                    iquery=iquery.replaceFirst("&", "'lit|"+node.getNodeType().replace("xsd:", "http://www.w3.org/2001/XMLSchema#")+"|_|"+node.getValue()+"'");
                }
            }  
            System.out.println(iquery);
        }        
        
        return execInternalQuery(q);
    }       
    
    public ResultSet execInternalQuery(String q)
    {
        Connection con=SWBUtils.DB.getDefaultConnection();
        try
        {
            PreparedStatement ps=con.prepareStatement(q);
            Iterator<ParamNode> it=processor.getSQLParams().iterator();
            int x=1;
            while (it.hasNext())
            {
                ParamNode n = it.next();
                Node node=null;
                
                if(n.getNode().isIRI())node=Node.createURI(n.getNode().getValue().substring(1,n.getNode().getValue().length()-1));
                else if(n.getNode().isString())node=Node.createLiteral(n.getNode().getValue());
                else node=Node.createLiteral(n.getNode().getValue(),null, new BaseDatatype(n.getNode().getNodeType().replace("xsd:", "http://www.w3.org/2001/XMLSchema#")));
                
                if(n.getType().equals("subj"))
                {
                    ps.setString(x, ((RGraph)model.getGraph()).encodeSubject(node));
//                    System.out.println("param:"+x+" "+((RGraph)model.getGraph()).encodeSubject(node));
                }else if(n.getType().equals("prop"))
                {
                    ps.setString(x, ((RGraph)model.getGraph()).encodeProperty(node));
//                    System.out.println("param:"+x+" "+((RGraph)model.getGraph()).encodeProperty(node));
                }else if(n.getType().equals("obj"))
                {
                    ps.setString(x, ((RGraph)model.getGraph()).encodeObject(node));
//                    System.out.println("param:"+x+" "+((RGraph)model.getGraph()).encodeObject(node));
                }
                
                x++;
            }
            java.sql.ResultSet rs=ps.executeQuery();
            return new SWBResultSet(model, rs, ps);
        }catch(Exception e)
        {
            log.error(e);
        }
        return null;
    }

    @Override
    public void setFileManager(FileManager arg)
    {
        throw new JenaException("UnsupportedMethodException");
    }

    @Override
    public void setInitialBinding(QuerySolution arg)
    {
        m_arg = arg;
    }

    @Override
    public Dataset getDataset()
    {
        //return new VirtDataSource(graph);
        return null;
    }

    @Override
    public Context getContext()
    {
        return null;
    }

    @Override
    public Model execConstruct()
    {
        return execConstruct(ModelFactory.createDefaultModel());
    }

    @Override
    public Model execConstruct(Model model)
    {
//        try
//        {
//            Connection con = graph.m_transactionHandler.getConnection();
//            stmt = graph.createStatement(con);
//            java.sql.ResultSet rs = stmt.executeQuery(getQuery());
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//            while (rs.next())
//            {
//                Node s = SWBVirtGraph.Object2Node(rs.getObject(1));
//                Node p = SWBVirtGraph.Object2Node(rs.getObject(2));
//                Node o = SWBVirtGraph.Object2Node(rs.getObject(3));
//                com.hp.hpl.jena.rdf.model.Statement st = ModelUtils.tripleToStatement(model, new Triple(s, p, o));
//                if (st != null)
//                {
//                    model.add(st);
//                }
//            }
//            rs.close();
//            stmt.close();
//            stmt = null;
//            con.close();
//        } catch (Exception e)
//        {
//            throw new JenaException("Convert results are FAILED.:" + e);
//        }
        return model;
    }

    @Override
    public Model execDescribe()
    {
        return execDescribe(ModelFactory.createDefaultModel());
    }

    @Override
    public Model execDescribe(Model model)
    {
//        try
//        {
//            Connection con = graph.m_transactionHandler.getConnection();
//            stmt = graph.createStatement(con);
//            java.sql.ResultSet rs = stmt.executeQuery(getQuery());
//            ResultSetMetaData rsmd = rs.getMetaData();
//            while (rs.next())
//            {
//                Node s = SWBVirtGraph.Object2Node(rs.getObject(1));
//                Node p = SWBVirtGraph.Object2Node(rs.getObject(2));
//                Node o = SWBVirtGraph.Object2Node(rs.getObject(3));
//
//                com.hp.hpl.jena.rdf.model.Statement st = ModelUtils.tripleToStatement(model, new Triple(s, p, o));
//                if (st != null)
//                {
//                    model.add(st);
//                }
//            }
//            rs.close();
//            stmt.close();
//            stmt = null;
//            con.close();
//
//        } catch (Exception e)
//        {
//            throw new JenaException("Convert results are FAILED.:" + e);
//        }
        return model;
    }

    @Override
    public boolean execAsk()
    {
        boolean ret = false;

//        try
//        {
//            Connection con = graph.m_transactionHandler.getConnection();
//            stmt = graph.createStatement(con);
//            java.sql.ResultSet rs = stmt.executeQuery(getQuery());
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//            while (rs.next())
//            {
//                if (rs.getInt(1) == 1)
//                {
//                    ret = true;
//                }
//            }
//            rs.close();
//            stmt.close();
//            stmt = null;
//            con.close();
//        } catch (Exception e)
//        {
//            throw new JenaException("Convert results are FAILED.:" + e);
//        }
        return ret;
    }

    @Override
    public void abort()
    {
//        if (stmt != null)
//        {
//            try
//            {
//                stmt.cancel();
//            } catch (Exception e)
//            {
//            }
//        }
    }

    @Override
    public void close()
    {
//        if (stmt != null)
//        {
//            try
//            {
//                stmt.cancel();
//                stmt.close();
//            } catch (Exception e)
//            {
//            }
//        }
    }

    ///=== Inner class ===========================================
    public class SWBResultSet implements com.hp.hpl.jena.query.ResultSet
    {

        ResultSetMetaData rsmd;
        java.sql.ResultSet rs;
        java.sql.Statement st;
        boolean v_finished = false;
        boolean v_prefetched = false;
        Model m;
        Binding v_row;
        List<String> resVars = new LinkedList();
        int row_id = 0;

        protected SWBResultSet(Model m, java.sql.ResultSet _rs, java.sql.Statement _st)
        {
            rs = _rs;
            st = _st;
            this.m=m;

            try
            {
                rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                {
                    String name=rsmd.getColumnLabel(i);
                    if(!name.startsWith("__ext__"))
                    {
                        resVars.add(name);
                        //System.out.println("meta:"+rsmd.getColumnLabel(i));
                    }
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        }

        @Override
        public boolean hasNext()
        {
            if (!v_finished && !v_prefetched)
            {
                moveForward();
            }
            return !v_finished;
        }

        @Override
        public QuerySolution next()
        {
            Binding binding = nextBinding();

            if (v_finished)
            {
                throw new NoSuchElementException();
            }

            return new ResultBinding(m, binding);
        }

        @Override
        public QuerySolution nextSolution()
        {
            return next();
        }

        @Override
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

        @Override
        public int getRowNumber()
        {
            return row_id;
        }

        @Override
        public List<String> getResultVars()
        {
            return resVars;
        }

        @Override
        public Model getResourceModel()
        {
            return m;
        }

        @Override
        protected void finalize() throws Throwable
        {
            try
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
            } finally
            {
                super.finalize();
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
                    String name=rsmd.getColumnLabel(i);
                    if(!name.startsWith("__ext__"))
                    {
                        String ext=null;
//                        InputStream sext=rs.getAsciiStream("__ext__"+name);
//                        try
//                        {
//                            if(sext!=null)ext=SWBUtils.IO.readInputStream(sext);
//                        }catch(Exception e){log.error(e);}
                        Node n = null;
                        Object obj=rs.getObject(name);
                        if(obj instanceof Integer)
                        {
                            n=Node.createLiteral(LiteralLabelFactory.create(rs.getInt(name)));
                        }else
                        {
                            if(obj!=null)
                            {
                                n = ((RGraph)model.getGraph()).decodeObject(obj.toString(), ext);
                            }
                        }
                        if (n != null)
                        {
                            v_row.add(Var.alloc(rsmd.getColumnLabel(i)), n);
                            //System.out.println("col:"+n);
                        }                        
                    }
                }                
//                if (virt_graph != null && !virt_graph.equals("virt:DEFAULT"))
//                {
//                    v_row.add(Var.alloc("graph"), Node.createURI(virt_graph));
//                }
            } catch (Exception e)
            {
                log.error(e);
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