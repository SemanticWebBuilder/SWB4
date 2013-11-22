/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.platform;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author javier.solis.g
 */
public class SemanticQueryIterator<T extends SemanticObject> implements Iterator
{
    private static Logger log= SWBUtils.getLogger(SemanticQueryIterator.class);    
    private SemanticModel model;
    RGraph graph;
    private Connection con;
    private ResultSet rs;
    
    boolean next=false;
    SemanticObject act=null;
    
    public SemanticQueryIterator(SemanticModel model, ResultSet rs, Connection con)
    {
        this.model=model;
        graph=(RGraph)model.getRDFModel().getGraph();
        this.rs=rs;
        this.con=con;
        try
        {
            next=rs.next();
            if(!next)
            {
                close();
            }           
        }catch(Exception e)
        {
            log.error(e);
        }
    }
    

    @Override
    public boolean hasNext()
    {
        return next;
    }

    @Override
    public T next()
    {
        T ret=null;
        try
        {
            String subj=rs.getString(1);
            String ext=null;
            InputStream sext=rs.getAsciiStream(2);
            if(sext!=null)ext=SWBUtils.IO.readInputStream(sext);

            String uri=graph.decodeSubject(subj,ext).getURI();
            ret=(T)SemanticObject.createSemanticObject(uri);
        }catch(Exception e)
        {
            log.error(e);
        }
        
        try
        {
            next=rs.next();
            if(!next)
            {
                close();
            }
        }catch(Exception e)
        {
            log.error(e);
        }        
        return ret;
    }

    @Override
    public void remove()
    {
        if(act!=null)act.remove();
    }
    
    public void close()
    {
        try
        {
            rs.close();
            con.close();
        }catch(Exception e)
        {
            log.error(e);
        }          
    }
    
}
