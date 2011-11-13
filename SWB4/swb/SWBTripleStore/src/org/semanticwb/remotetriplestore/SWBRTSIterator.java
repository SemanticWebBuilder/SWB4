package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;
import org.semanticwb.remotetriplestore.protocol.TripleString;
import org.semanticwb.remotetriplestore.protocol.Response;
import org.semanticwb.remotetriplestore.protocol.SWBRTSCmd;
import org.semanticwb.triplestore.SWBTSUtil;

/**
 *
 * @author serch
 */
class SWBRTSIterator implements ExtendedIterator<Triple>
{

    private static Logger log=SWBUtils.getLogger(SWBRTSIterator.class);

    private SWBRTSGraph graph=null;
    private TripleMatch tm=null;

    private Triple actual=null;
    private Triple next=null;

    private boolean closed=false;

    private static int counter=0;
    private Iterator<TripleString> iterData;

    public SWBRTSIterator(SWBRTSGraph graph, TripleMatch tm, SWBRTSThreadPool pool)
    {
        counter++;
        //System.out.println("SWBTSIterator:"+counter+" tm:"+tm+" "+graph.getName());

        this.graph=graph;
        this.tm=tm;

        String subj=SWBTSUtil.node2String(tm.getMatchSubject());
        String prop=SWBTSUtil.node2String(tm.getMatchPredicate());
        String obj=SWBTSUtil.node2String(tm.getMatchObject());        
        //System.out.println("subj:"+subj+" prop:"+prop+" obj:"+obj);

        try {

            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.GRAPH_BASE_FIND;
            cmd.paramNumber=4;
            SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {graph.getName(),subj,prop,obj};
            util.setParams(params);
            //Future<Response> future = pool.getPool().submit(util);
            //Response resp = future.get();
            Response resp = util.call();
            ArrayList<TripleString> list = (ArrayList<TripleString>) resp.data;
            //log.debug(""+list);
            iterData = list.iterator();
        } catch (Exception e)
        {
            log.error(e);
        }

    }

    public Triple removeNext()
    {
        Triple tp=next();
        remove();
        return tp;
    }

    public <X extends Triple> ExtendedIterator<Triple> andThen(Iterator<X> other)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ExtendedIterator<Triple> filterKeep(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ExtendedIterator<Triple> filterDrop(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <U> ExtendedIterator<U> mapWith(Map1<Triple, U> map1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Triple> toList()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Set<Triple> toSet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close()
    {
        if(!closed)
        {
            closed=true;
            try
            {
                counter--;
            } catch (Exception ex)
            {
                log.error(ex);
            }
        }
    }

    public boolean hasNext()
    {
        return iterData.hasNext();
    }

    public Triple next()
    {
        TripleString mpt = iterData.next();
        actual= new Triple(SWBTSUtil.string2Node(mpt.subj,null), SWBTSUtil.string2Node(mpt.prop,null), SWBTSUtil.string2Node(mpt.obj,null));
        return actual;
    }

    public void remove()
    {
        graph.performDelete(actual);
        iterData.remove();
    }

    @Override
    protected void finalize() throws Throwable
    {
        if(!closed)
        {
            log.warn("Iterator is not closed, "+tm);
            close();
        }
    }

}
