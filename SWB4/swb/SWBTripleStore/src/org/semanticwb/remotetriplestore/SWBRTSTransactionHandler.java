/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.impl.TransactionHandlerBase;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.logging.Level;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;
import org.semanticwb.remotetriplestore.protocol.OOK;
import org.semanticwb.remotetriplestore.protocol.Response;
import org.semanticwb.remotetriplestore.protocol.SWBRTSCmd;

/**
 *
 * @author jei
 */
public class SWBRTSTransactionHandler extends TransactionHandlerBase
{
    public static Logger log=SWBUtils.getLogger(SWBRTSTransactionHandler.class);
    private SWBRTSThreadPool pool;
    private SWBRTSGraph graph;
    
    

    public SWBRTSTransactionHandler(SWBRTSGraph graph, SWBRTSThreadPool pool)
    {
        this.pool=pool;
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
            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.TRANS_BEGIN;
            cmd.paramNumber=2;
            SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {graph.getName(), ""+Thread.currentThread().getId()};
            util.setParams(params);
            //Future<Response> future = pool.getPool().submit(util);
            //Response resp = future.get();
            Response resp = util.call();
        } catch (Exception e)
        {
            log.error(e);
        }        
    }

    public void abort()
    {
        //System.out.println("abort:"+Thread.currentThread().getId());
        
        try {
            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.TRANS_ABORT;
            cmd.paramNumber=2;
            SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {graph.getName(), ""+Thread.currentThread().getId()};
            util.setParams(params);
            //Future<Response> future = pool.getPool().submit(util);
            //Response resp = future.get();
            Response resp = util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    public void commit()
    {
        //System.out.println("commit:"+Thread.currentThread().getId());        
        try {
            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.TRANS_COMMINT;
            cmd.paramNumber=2;
            SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {graph.getName(), ""+Thread.currentThread().getId()};
            util.setParams(params);
            //Future<Response> future = pool.getPool().submit(util);
            //Response resp = future.get();
            Response resp = util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

}
