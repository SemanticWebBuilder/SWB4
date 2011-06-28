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

/**
 *
 * @author jei
 */
public class BigdataTransactionHandler extends TransactionHandlerBase
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
        //System.out.println("begin:"+Thread.currentThread().getId());
        SailConnection con=conmap.get(Thread.currentThread().getId());
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
            conmap.put(Thread.currentThread().getId(), con);
        } catch (SailException ex)
        {
            throw new RuntimeException("Error initializing transaction", ex);
        }
    }

    public void abort()
    {
        //System.out.println("abort:"+Thread.currentThread().getId());
        SailConnection con=conmap.get(Thread.currentThread().getId());
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
                conmap.remove(Thread.currentThread().getId());
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
        //System.out.println("commit:"+Thread.currentThread().getId());
        SailConnection con=conmap.get(Thread.currentThread().getId());
        if(con!=null)
        {
            try
            {
                con.commit();
            } catch (SailException ex)
            {
                throw new RuntimeException("Error commiting transaction", ex);
            }finally
            {
                conmap.remove(Thread.currentThread().getId());
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
        return conmap.get(Thread.currentThread().getId());
    }

}
