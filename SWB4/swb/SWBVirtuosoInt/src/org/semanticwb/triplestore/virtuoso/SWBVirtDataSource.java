/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.DataSource;
import com.hp.hpl.jena.query.LabelExistsException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.sparql.util.Context;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author javier.solis.g
 */
public class SWBVirtDataSource extends SWBVirtGraph implements DataSource {

    /**
     * Default model - may be null - according to Javadoc
     */
    Model defaultModel = null;


    public SWBVirtDataSource()
    {
        super(null);
    }

    protected SWBVirtDataSource(SWBVirtGraph g)
    {
        super(g.getGraphName());
      this.graphName = g.getGraphName();
      setReadFromAllGraphs(g.getReadFromAllGraphs());
      setFetchSize(g.getFetchSize());
    }

    /** Set the background graph.  Can be set to null for none.  */
    @Override
    public void setDefaultModel(Model model) 
    {
      if (!(model instanceof SWBVirtDataSource))
        throw new IllegalArgumentException("VirtDataSource supports only VirtModel as default model");
      defaultModel = model;
    }

    /** Set a named graph. */
    @Override
    public void addNamedModel(String name, Model model)	throws LabelExistsException 
    {
      String query = "select count(*) from (sparql select * where { graph `iri(??)` { ?s ?p ?o }})f";
      ResultSet rs = null;
      int ret = 0;

      checkOpen();
      try {
          Connection con=m_transactionHandler.getConnection();
        java.sql.PreparedStatement ps = prepareStatement(con,query);
        ps.setString(1, name);
        rs = ps.executeQuery();
        if (rs.next())
          ret = rs.getInt(1);
        rs.close();
        ps.close();
        con.close();
      } catch (Exception e) {
        throw new JenaException(e);
      }

      try {
        if (ret != 0)
          throw new LabelExistsException("A model with ID '" + name
					+ "' already exists.");
        Graph g = model.getGraph();
        int count = 0;
        Connection con=m_transactionHandler.getConnection();
        java.sql.PreparedStatement ps = prepareStatement(con,sinsert);

        for (Iterator i = g.find(Node.ANY, Node.ANY, Node.ANY); i.hasNext();) 
        {
          Triple t = (Triple)i.next();

          ps.setString(1, name);
          bindSubject(ps, 2, t.getSubject());
          bindPredicate(ps, 3, t.getPredicate());
          bindObject(ps, 4, t.getObject());
          ps.addBatch();
          count++;
          if (count > BATCH_SIZE) {
            ps.executeBatch();
            ps.clearBatch();
            count = 0;
          }
        }
        if (count > 0) {
          ps.executeBatch();
          ps.clearBatch();
        }
        ps.close();
        con.close();
      } catch (Exception e) {
        throw new JenaException(e);
      }
    }


    /** Remove a named graph. */
    @Override
    public void removeNamedModel(String name) 
    {
      String exec_text ="sparql clear graph <"+ name + ">";

      checkOpen();
      try {
          Connection con=m_transactionHandler.getConnection();
        java.sql.Statement stmt = createStatement(con);
        stmt.executeQuery(exec_text);
        stmt.close();
        con.close();
      } catch (Exception e) {
	throw new JenaException(e);
      }
    }


    /** Change a named graph for another uisng the same name */
    @Override
    public void replaceNamedModel(String name, Model model) 
    {
        m_transactionHandler.startTransaction();
      try {
          Connection con=m_transactionHandler.getConnection();
        con.setAutoCommit(false);
        removeNamedModel(name);
        con.close();
        addNamedModel(name, model);
        con=m_transactionHandler.getConnection();
        con.commit();
        con.setAutoCommit(true);
        con.close();
      } catch (Exception e) {
         try {
           m_transactionHandler.getConnection().rollback();
         } catch (Exception e2) {
           throw new JenaException(
                  "Could not replace model, and could not rollback!", e2);
         }
           throw new JenaException("Could not replace model:", e);
      }
      m_transactionHandler.endTransaction();
      m_transactionHandler.closeTransaction();
    }


    /** Get the default graph as a Jena Model */
    @Override
    public Model getDefaultModel() {
      return defaultModel;
    }


    /** Get a graph by name as a Jena Model */
    @Override
    public Model getNamedModel(String name) 
    {
      try {
	    return new SWBVirtModel(new SWBVirtGraph(name));
      } catch (Exception e) {
	throw new JenaException(e);
      }
    }

    /** Does the dataset contain a model with the name supplied? */ 
    @Override
    public boolean containsNamedModel(String name) 
    {
        //System.out.println("containsNamedModel:"+name);
        String query = "select count(*) from (sparql select * where { graph `iri(??)` { ?s ?p ?o }})f";
        ResultSet rs = null;
        int ret = 0;

        checkOpen();
        try {
            Connection con=m_transactionHandler.getConnection();
          java.sql.PreparedStatement ps = prepareStatement(con,query);
          ps.setString(1, name);
          rs = ps.executeQuery();
          if (rs.next())
            ret = rs.getInt(1);
          rs.close();
          ps.close();
          con.close();
        } catch (Exception e) {
          throw new JenaException(e);
        }
        return (ret!=0);
    }


    /** List the names */
    @Override
    public Iterator<String> listNames() 
    {
      String exec_text = "DB.DBA.SPARQL_SELECT_KNOWN_GRAPHS()";
      ResultSet rs = null;
      int ret = 0;

      checkOpen();
      try {
        List<String> names=new LinkedList(); 
        Connection con=m_transactionHandler.getConnection();
        java.sql.Statement stmt = createStatement(con);
        rs = stmt.executeQuery(exec_text);
        while(rs.next())
        {
            String name=rs.getString(1);
            names.add(name);
            //System.out.println("listNames:"+name);
        }
        rs.close();
        stmt.close();
        con.close();
        return names.iterator();
      } catch (Exception e) {
        throw new JenaException(e);
      }
    }


    Lock lock = null ;

    /** Get the lock for this dataset */
    @Override
    public Lock getLock() 
    {
      if (lock == null)
        lock = new com.hp.hpl.jena.shared.LockNone();
      return lock;
    }


    /** Get the dataset in graph form */
    @Override
    public DatasetGraph asDatasetGraph() 
    {
      return new SWBVirtDataSetGraph(this);
    }


    public class SWBVirtDataSetGraph implements DatasetGraph 
    {

      SWBVirtDataSource vd = null;

      public SWBVirtDataSetGraph(SWBVirtDataSource vds) 
      {
        vd = vds;
      }

      @Override
      public Graph getDefaultGraph() {
        return vd;
      }

      @Override
      public Graph getGraph(Node graphNode) {
        try {
          return new SWBVirtGraph(graphNode.toString());
	} catch (Exception e) {
	  throw new JenaException(e);
        }
      }

      @Override
      public boolean containsGraph(Node graphNode) {
        return containsNamedModel(graphNode.toString());
      }

      @Override
      public Iterator<Node> listGraphNodes() 
      {
        String exec_text = "DB.DBA.SPARQL_SELECT_KNOWN_GRAPHS()";
        ResultSet rs = null;
        int ret = 0;

        vd.checkOpen();
        try {
	  List<Node> names=new LinkedList();
          Connection con=vd.m_transactionHandler.getConnection();
  	  java.sql.Statement stmt = vd.createStatement(con);
	  rs = stmt.executeQuery(exec_text);
	  while(rs.next())
	    names.add(Node.createURI(rs.getString(1)));
          rs.close();
          stmt.close();
          con.close();
	  return names.iterator();
	} catch (Exception e) {
	  throw new JenaException(e);
        }
      }

      @Override
      public Lock getLock() 
      {
        return vd.getLock();
      }

      @Override
      public long size() 
      {
        return vd.size();
      }

      @Override
      public void close() 
      {
        vd.close();
      }

        @Override
        public void setDefaultGraph(Graph g)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addGraph(Node graphName, Graph graph)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeGraph(Node graphName)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void add(Quad quad)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void delete(Quad quad)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void deleteAny(Node g, Node s, Node p, Node o)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Iterator<Quad> find(Quad quad)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Iterator<Quad> find(Node g, Node s, Node p, Node o)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean contains(Node g, Node s, Node p, Node o)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean contains(Quad quad)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isEmpty()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Context getContext()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    }

}
