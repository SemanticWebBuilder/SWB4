/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.virtuoso;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.sparql.ARQInternalErrorException;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.Transform;
import com.hp.hpl.jena.sparql.algebra.TransformCopy;
import com.hp.hpl.jena.sparql.algebra.Transformer;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import com.hp.hpl.jena.sparql.engine.Plan;
import com.hp.hpl.jena.sparql.engine.QueryEngineFactory;
import com.hp.hpl.jena.sparql.engine.QueryEngineRegistry;
import com.hp.hpl.jena.sparql.engine.QueryIterator;
import com.hp.hpl.jena.sparql.engine.iterator.QueryIteratorBase;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.main.QueryEngineMain;
import com.hp.hpl.jena.sparql.serializer.SerializationContext;
import com.hp.hpl.jena.sparql.util.Context;
import com.hp.hpl.jena.sparql.util.Utils;
import java.sql.Connection;
import java.sql.Statement;
import org.openjena.atlas.io.IndentedWriter;


/**
 *
 * @author javier.solis.g
 */
public class SWBVirtuosoQueryEngine extends QueryEngineMain
{
    private Query eQuery = null;

    
    public SWBVirtuosoQueryEngine(Query query, DatasetGraph dataset, Binding initial, Context context)
    {
        super(query, dataset, initial, context) ;
        eQuery = query;
    }

    public SWBVirtuosoQueryEngine(Query query, DatasetGraph dataset)
    { 
        this(query, dataset, null, null) ;
    }

    @Override
    public QueryIterator eval(Op op, DatasetGraph dsg, Binding initial, Context context)
    {
        // Extension point: access possible to all the parameters for execution.
        // Be careful to deal with initial bindings.
        Transform transform = new VirtTransform() ;
        op = Transformer.transform(transform, op) ;
        
        SWBVirtGraph vg = (SWBVirtGraph)dsg.getDefaultGraph();
        String query = fixQuery(eQuery.toString(), initial, vg);
	
        //System.out.println("eval:"+query);
	try
	{
            Connection con=vg.m_transactionHandler.getConnection();
	    java.sql.Statement  stmt = vg.createStatement(con);
	    java.sql.ResultSet rs = stmt.executeQuery(query);
	    return (QueryIterator)new VQueryIterator(vg, rs, stmt);
	}
	catch(Exception e)
	{
            throw new JenaException("Can not create QueryIterator.:"+e);
	}
    }
    

    private String substBindings(String query, Binding args) 
    {
      if (args == null)
        return query;
      
      StringBuffer buf = new StringBuffer();
      String delim = " ,)(;.";
      int i = 0;
      char ch;
      int qlen = query.length();
      while( i < qlen) {
        ch = query.charAt(i++);
        if (ch == '\\') {
	  buf.append(ch);
          if (i < qlen)
            buf.append(query.charAt(i++)); 

        } else if (ch == '"' || ch == '\'') {
          char end = ch;
      	  buf.append(ch);
      	  while (i < qlen) {
            ch = query.charAt(i++);
            buf.append(ch);
            if (ch == end)
              break;
      	  }
        } else  if ( ch == '?' ) {  //Parameter
      	  String varData = null;
      	  int j = i;
      	  while(j < qlen && delim.indexOf(query.charAt(j)) < 0) j++;
      	  if (j != i) {
            String varName = query.substring(i, j);
            Node val = args.get(Var.alloc(varName));
            if (val != null) {
              varData = SWBVirtGraph.Node2Str(val);
              i=j;
            }
          }
          if (varData != null)
            buf.append(varData);
          else
            buf.append(ch);
	} else {
      	  buf.append(ch);
    	}
      }
      return buf.toString();
    }

    
    private String fixQuery(String query, Binding args, SWBVirtGraph vg)
    {
	StringBuffer sb = new StringBuffer("sparql\n ");

	if (vg.getRuleSet()!=null)
          sb.append(" define input:inference '"+vg.getRuleSet()+"'\n ");

        if (vg.getSameAs())
          sb.append(" define input:same-as \"yes\"\n ");

        if (!vg.getReadFromAllGraphs())
	  sb.append(" define input:default-graph-uri <" + vg.getGraphName() + "> \n");

        sb.append(substBindings(query, args));

      	return sb.toString();
    }


    @Override
    protected Op modifyOp(Op op)
    {
        // Extension point: possible place to alter the algebra expression.
        // Alternative to eval(). 
        op = super.modifyOp(op) ;
        return op ;
    }
    
    // ---- Registration of the factory for this query engine class. 
    
    // Query engine factory.
    // Call VirtQueryEngine.register() to add to the global query engine registry. 

    static QueryEngineFactory factory = new SWBVirtQueryEngineFactory() ;

    static public QueryEngineFactory getFactory() { return factory ; } 
    static public void register()       { QueryEngineRegistry.addFactory(factory) ; }
    static public void unregister()     { QueryEngineRegistry.removeFactory(factory) ; }
    



    private class VirtTransform extends TransformCopy
    {
      // Example, do nothing tranform. 
      @Override
      public Op transform(OpBGP opBGP) { return opBGP; }
    }




    private static class SWBVirtQueryEngineFactory implements QueryEngineFactory
    {
      // Accept any dataset for query execution 
      @Override
      public boolean accept(Query query, DatasetGraph dataset, Context context) 
      { 
        //System.out.println("accept:"+dataset.getDefaultGraph().getClass());
        if ( dataset instanceof SWBVirtDataSource.SWBVirtDataSetGraph )
          return true ;
        if (dataset.getDefaultGraph() instanceof SWBVirtGraph )
          return true ;
        return false ;
      }

      @Override
      public Plan create(Query query, DatasetGraph dataset, Binding initial, Context context)
      {
          //System.out.println("create:"+query+" "+dataset+" "+initial+" "+context);
        if (!(dataset instanceof SWBVirtDataSource.SWBVirtDataSetGraph)) {
          if (!(dataset.getDefaultGraph() instanceof SWBVirtGraph ))
            throw new ARQInternalErrorException("VirtQueryEngineFactory: only factory VirtuosoDatasetGraph is supported") ;
        }
        // Create a query engine instance.
        SWBVirtuosoQueryEngine engine = new SWBVirtuosoQueryEngine(query, dataset, initial, context);
        //System.out.println("create ret:"+engine);
        return engine.getPlan() ;
      }

      @Override
      public boolean accept(Op op, DatasetGraph dataset, Context context)
      {  
          //System.out.println("accept2");
          // Refuse to accept algebra expressions directly.
        return false ;
      }

      @Override
      public Plan create(Op op, DatasetGraph dataset, Binding inputBinding, Context context)              
      {   // Shodul notbe called because acceept/Op is false
        throw new ARQInternalErrorException("VirtQueryEngineFactory: factory calleddirectly with an algebra expression") ;
      }
    } 



    
    protected class VQueryIterator extends QueryIteratorBase {
      java.sql.ResultSetMetaData rsmd;
      java.sql.ResultSet rs;
      java.sql.Statement stmt;
      SWBVirtGraph    vg;
      boolean	 v_finished = false;
      boolean	 v_prefetched = false;
      Binding 	 v_row;
      String virt_graph = null;


      protected VQueryIterator(SWBVirtGraph _g, java.sql.ResultSet _rs, Statement _stmt) 
      {
        rs = _rs;
        vg = _g;
        stmt=_stmt;
        virt_graph = vg.getGraphName ();

        try {
          rsmd = rs.getMetaData();
        } catch(Exception e) {
          throw new JenaException("VQueryIterator is FAILED.:"+e);
        }

      }


      @Override
      public void output(IndentedWriter out, SerializationContext sCxt)
      {
        out.print(Utils.className(this)) ;
      }

      @Override
      protected boolean hasNextBinding()
      {
        if (!v_finished && !v_prefetched) 
          moveForward();
        return !v_finished;
      }

      @Override
      protected Binding moveToNextBinding()
      {
        if (!v_finished && !v_prefetched)
          moveForward();

        v_prefetched = false;

        if (v_finished)
          return null;

        return v_row;
      }

    
      @Override
      protected void closeIterator()
      {
        if (!v_finished) {
          if (rs != null) {
  	    try {
              rs.close();
              stmt.close();
              stmt.getConnection().close();
              rs = null;
            } catch (Exception e) { }
          }
        }
        v_finished = true;
      }


      protected void moveForward() throws JenaException
      {
        try {
          if (!v_finished && rs.next()) {
            extractRow();
            v_prefetched = true;
          } else
            closeIterator();
        } catch (Exception e) {
          throw new JenaException("Convert results are FAILED.:"+e);
        }
      }

      protected void extractRow() throws Exception 
      {
        v_row = new BindingMap();

        try {
          for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            Node n = SWBVirtGraph.Object2Node(rs.getObject(i));
            if (n != null)
              v_row.add(Var.alloc(rsmd.getColumnLabel(i)), n);
          }

          if (virt_graph != null && !virt_graph.equals("virt:DEFAULT"))
	    v_row.add(Var.alloc("graph"), Node.createURI(virt_graph));
        } 
        catch(Exception e) {
          throw new JenaException("extractRow is FAILED.:"+e);
        }
      }


      @Override
      protected void finalize() throws Throwable
      {
        if (!v_finished) 
          try {
            close();
          } catch (Exception e) {}
      }
  
    }

}
