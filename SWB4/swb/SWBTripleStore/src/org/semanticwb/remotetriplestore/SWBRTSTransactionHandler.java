/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.impl.TransactionHandlerBase;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;

/**
 *
 * @author jei
 */
public class SWBRTSTransactionHandler extends TransactionHandlerBase
{
    public static Logger log=SWBUtils.getLogger(SWBRTSTransactionHandler.class);
    private SWBRTSGraph graph;
    
    public static long base=System.currentTimeMillis();

    public SWBRTSTransactionHandler(SWBRTSGraph graph)
    {
        this.graph=graph;
    }

    public boolean transactionsSupported()
    {
        return true;
    }

    public void begin()
    {
        //System.out.println("begin:"+Thread.currentThread().getId());
        try {
            String params[]={Command.TRANS_BEGIN, graph.getName(), ""+(Thread.currentThread().getId()+base)};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }        
    }

    public void abort()
    {
        //System.out.println("abort:"+Thread.currentThread().getId());
        
        try {
            String params[]={Command.TRANS_ABORT, graph.getName(), ""+(Thread.currentThread().getId()+base)};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    public void commit()
    {
        //System.out.println("commit:"+Thread.currentThread().getId());        
        try {
            String params[]={Command.TRANS_COMMINT, graph.getName(), ""+(Thread.currentThread().getId()+base)};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

}
