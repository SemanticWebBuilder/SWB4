package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.concurrent.Future;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;
import org.semanticwb.remotetriplestore.protocol.Response;
import org.semanticwb.remotetriplestore.protocol.SWBRTSCmd;
import org.semanticwb.triplestore.SWBTSUtil;

/**
 *
 * @author serch
 */
class SWBRTSGraph extends GraphBase{

    private static Logger log = SWBUtils.getLogger(SWBRTSGraph.class);

    private String name;
    private SWBRTSThreadPool pool;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;


    public SWBRTSGraph(String name, SWBRTSThreadPool pool)
    {
        this.name=name;
        this.pool=pool;
        pmap=new SWBRTSPrefixMapping(this, pool);
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        System.out.println("graphBaseFind:"+tm);
        return new SWBRTSIterator(this, tm, pool);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void performAdd(Triple t)
    {
        try
        {

            String subj=SWBTSUtil.node2String(t.getSubject());
            String prop=SWBTSUtil.node2String(t.getPredicate());
            String obj=SWBTSUtil.node2String(t.getObject());

            try {

                SWBRTSCmd cmd = new SWBRTSCmd();
                cmd.cmd = Command.GRAPH_ADD;
                cmd.paramNumber=4;
                SWBRTSUtil util =  new SWBRTSUtil(pool.getAddress(), pool.getPort());
                util.setCommand(cmd);
                String[] params = {getName(),subj,prop,obj};
                util.setParams(params);
                Future<Response> future = pool.getPool().submit(util);
                Response resp = future.get();
            } catch (Exception e)
            {
                log.error(e);
            }

        } catch (Exception e2)
        {
            log.error(e2);
        }
    }

    @Override
    public void performDelete(Triple t)
    {
        String subj=SWBTSUtil.node2String(t.getSubject());
        String prop=SWBTSUtil.node2String(t.getPredicate());
        String obj=SWBTSUtil.node2String(t.getObject());

        try {

            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.GRAPH_REMOVE;
            cmd.paramNumber=4;
            SWBRTSUtil util =  new SWBRTSUtil(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {getName(),subj,prop,obj};
            util.setParams(params);
            Future<Response> future = pool.getPool().submit(util);
            Response resp = future.get();
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void close()
    {
        //Thread.currentThread().dumpStack();
        super.close();
    }

    @Override
    public PrefixMapping getPrefixMapping()
    {
        return pmap;
    }

}
