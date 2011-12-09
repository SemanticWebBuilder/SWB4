/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bigdata;

import com.bigdata.rdf.sail.BigdataSail;
import com.hp.hpl.jena.graph.impl.TransactionHandlerBase;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RTransactionHandler;

/**
 *
 * @author jei
 */
public class BigdataTransactionHandler extends TransactionHandlerBase implements RTransactionHandler
{
    public static Logger log=SWBUtils.getLogger(BigdataTransactionHandler.class);
    private BigdataSail sail;
    private ConcurrentHashMap<Long,SailConnection> conmap;

    public BigdataTransactionHandler(BigdataSail sail)
    {
        conmap=new ConcurrentHashMap();
        this.sail=sail;
    }


    public boolean transactionsSupported()
    {
        return true;
    }
    
    public void begin()
    {
        begin(null);
    }

    public void begin(Long id)
    {
        //System.out.println("begin:"+id);
        SailConnection con=null;
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        con=conmap.get(id);
        if(con!=null)
        {
            try
            {
                con.close();
            } catch (SailException ex)
            {
                log.error("Error closing connection", ex);
            }
            log.event("BigData Connection was not closed...");
        }
        try
        {
            con = sail.getConnection();
            conmap.put(id, con);
        } catch (SailException ex)
        {
            throw new RuntimeException("Error initializing transaction", ex);
        }
    }
    
    public void abort()
    {
        abort(null);
    }

    public void abort(Long id)
    {
        //System.out.println("abort:"+id);
        SailConnection con=null;
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        con=conmap.get(id);        
        
        if(con!=null)
        {
            try
            {
                con.rollback();
            } catch (SailException ex)
            {
                throw new RuntimeException("Error rollingback transaction", ex);
            }finally
            {
                conmap.remove(id);
                try
                {
                    con.close();
                } catch (SailException ex)
                {
                    throw new RuntimeException("Error closing transaction", ex);
                }
            }
        }
    }
    
    public void commit()
    {
        commit(null);
    }

    public void commit(Long id)
    {
        //System.out.println("commit:"+id);
        SailConnection con=null;
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        con=conmap.get(id);
        {
            try
            {
                con.commit();
            } catch (SailException ex)
            {
                throw new RuntimeException("Error commiting transaction", ex);
            }finally
            {
                conmap.remove(id);
                try
                {
                    con.close();
                } catch (SailException ex)
                {
                    throw new RuntimeException("Error closing transaction", ex);
                }
            }
        }
    }

    /**
     * Return the SailConnection associated to the current transaction and thread
     * @return SailConnection
     */
    public SailConnection getConnection()
    {
        return getConnection(Thread.currentThread().getId());
    }
    
    /**
     * Return the SailConnection associated to the current transaction and thread
     * @return SailConnection
     */
    public SailConnection getConnection(Long id)
    {
        if(id==null)
        {
            id=Thread.currentThread().getId();
        }
        //System.out.println("getConnection("+id+")");
        return conmap.get(id);
    }

}
