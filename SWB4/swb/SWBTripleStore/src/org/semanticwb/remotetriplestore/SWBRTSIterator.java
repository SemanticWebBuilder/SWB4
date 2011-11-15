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

    public SWBRTSIterator(SWBRTSGraph graph, TripleMatch tm)
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
            String params[]={Command.GRAPH_BASE_FIND,graph.getName(),subj,prop,obj};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
            ArrayList<TripleString> list = new ArrayList();
            for(int x=0;x<l.size();x+=3)
            {
                TripleString ts=new TripleString();
                ts.subj=l.get(x);
                ts.prop=l.get(x+1);
                ts.obj=l.get(x+2);
                list.add(ts);
            }
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
