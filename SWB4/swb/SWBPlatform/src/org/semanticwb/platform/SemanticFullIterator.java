/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.platform;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author javier.solis.g
 */
public class SemanticFullIterator implements Iterator<SemanticObject>
{
    private static Logger log= SWBUtils.getLogger(SemanticFullIterator.class);    
    private SemanticModel model;
    RGraph graph;
    private Connection con;
    private ResultSet rs;
    private Statement st;
    
    //private boolean hasNext=false;
    private SemanticObject actObj=null;
    private SemanticObject lastObj=null;
    
    public SemanticFullIterator(SemanticModel model, ResultSet rs, Connection con)
    {
        this.model=model;
        graph=(RGraph)model.getRDFModel().getGraph();
        this.rs=rs;
        this.con=con;
        try
        {
            if(rs.next())
            {
                st=getStatement();
                findNext();
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
    
    @Override
    public boolean hasNext()
    {
        return actObj!=null;
        //return hasNext;
    }
    
    private Statement getStatement()
    {
//        Resource s = new ResourceImpl( t.getSubject(), eg );
//        Property p = new PropertyImpl( t.getPredicate(), eg );
//        RDFNode o = n.isLiteral() ? (RDFNode) new LiteralImpl( n, g ) : new ResourceImpl( n, g );
//        return new StatementImpl( s, p, o, eg );                
        Triple t=null;
        String ext=null;
        try
        {
            InputStream sext=rs.getAsciiStream("ext");
            if(sext!=null)ext=SWBUtils.IO.readInputStream(sext);
            t=new Triple(graph.decodeSubject(rs.getString("subj"),ext), graph.decodeSubject(rs.getString("prop"),ext), graph.decodeSubject(rs.getString("obj"),ext));
        }catch(Exception e){log.error(e);}
        if(t!=null)return model.getRDFModel().asStatement(t);
        return null;
    }
    
    private void findNext()
    {
        actObj=null;
        if(st!=null)
        {
            Resource res=st.getSubject();
            ArrayList<Statement> arr=new ArrayList();
            arr.add(st);
            st=null;
            try
            {
                while(rs.next())
                {
                    st=getStatement();
                    if(st.getSubject().equals(res))
                    {
                        arr.add(st);              
                        st=null;
                    }else
                    {
                        break;
                    }
                }
                SemanticObject obj=new SemanticObject(model, res, arr.iterator());  
                if(st==null)
                {
                    close();
                }
                
                if(obj.getSemanticClass()==null)
                {
                    if(st!=null)
                    {
                        findNext();
                    }                    
                }else
                {
                    actObj=obj;
                }
            }catch(Exception e)
            {
                log.error(e);
            }
        }        
    }
    

    @Override
    public SemanticObject next()
    {
        lastObj=actObj;
        findNext();
        return lastObj;
    }

    @Override
    public void remove()
    {
        lastObj.remove();
    }
    
    public void close()
    {
        try
        {
            rs.close();
            if(con!=null)con.close();
        }catch(Exception e)
        {
            log.error(e);
        }
    }
    
}
