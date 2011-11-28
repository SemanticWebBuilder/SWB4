package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;
import org.semanticwb.triplestore.SWBTSUtil;

/**
 *
 * @author serch
 */
class SWBRTSGraph extends GraphBase{

    private static Logger log = SWBUtils.getLogger(SWBRTSGraph.class);

    private String name;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;
    private SWBRTSTransactionHandler trans;

    public SWBRTSGraph(String name)
    {
        this.name=name;
        pmap=new SWBRTSPrefixMapping(this);
        trans=new SWBRTSTransactionHandler(this);
        //System.out.println("name:"+name+" "+getTransactionHandler().transactionsSupported());        
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
//        if(tm.getMatchSubject()!=null && (tm.getMatchSubject().toString().equals(" ") ||
//            tm.getMatchSubject().toString().equals("generico")))
//        {
//            System.out.println("graphBaseFind:"+tm);
//            new Exception().printStackTrace();
//        }
        return new SWBRTSIterator(this, tm);
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
                String params[]={Command.GRAPH_ADD,getName(),subj,prop,obj,""+Thread.currentThread().getId()};
                SWBRTSUtil util = new SWBRTSUtil(params);
                util.call();
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
            String[] params = {Command.GRAPH_REMOVE,getName(),subj,prop,obj,""+Thread.currentThread().getId()};
            SWBRTSUtil util = new SWBRTSUtil(params);
            util.call();
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
        //System.out.println("Close");
        //new Exception().printStackTrace();
        //Thread.currentThread().dumpStack();
        //super.close();
    }

    @Override
    public PrefixMapping getPrefixMapping()
    {
        return pmap;
    }

    @Override
    public TransactionHandler getTransactionHandler() 
    {
        return trans;
    }
    
    
    
    

}
