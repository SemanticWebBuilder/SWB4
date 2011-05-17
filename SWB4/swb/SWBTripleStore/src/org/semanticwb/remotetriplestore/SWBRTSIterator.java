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
import java.util.concurrent.Future;
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

//    private Connection con;
//    private PreparedStatement ps;
//    private ResultSet rs;

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

        String subj=SWBTSUtil.node2HashString(tm.getMatchSubject(),"lgs");
        String prop=SWBTSUtil.node2HashString(tm.getMatchPredicate(),"lgp");
        String obj=SWBTSUtil.node2HashString(tm.getMatchObject(),"lgo");
    //    System.out.println("subj:"+subj+" prop:"+prop+" obj:"+obj);

        try {

            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.GRAPH_BASE_FIND;
            cmd.paramNumber=4;
            SWBRTSUtil util =  new SWBRTSUtil(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {graph.getName(),subj,prop,obj};
            util.setParams(params);
            Future<Response> future = pool.getPool().submit(util);
            Response resp = future.get();
            ArrayList<TripleString> list = (ArrayList<TripleString>) resp.data;
            iterData = list.iterator();
        } catch (Exception e)
        {
            log.error(e);
        }

//        try
//        {
//            con=SWBUtils.DB.getDefaultConnection();

//            String query="select * from swb_graph_ts"+graph.getId();
//            String query2="";
//            if(subj!=null)query2+=" subj=?";
//            if(prop!=null)
//            {
//                if(query2.length()>0)query2 +=" and";
//                query2 += " prop=?";
//            }
//            if(obj!=null)
//            {
//                if(query2.length()>0)query2 +=" and";
//                query2 += " obj=?";
//            }
//
//            if(query2.length()>0)query+=" where"+query2;
//
//            ps=con.prepareStatement(query);
//            int i=1;
//            if(subj!=null)ps.setString(i++, subj);
//            if(prop!=null)ps.setString(i++, prop);
//            if(obj!=null)ps.setString(i++, obj);
//            rs=ps.executeQuery();

//            if(rs.next())
//            {
//                String ext=null;
//                InputStream sext=rs.getAsciiStream("ext");
//                try
//                {
//                    if(sext!=null)ext=SWBUtils.IO.readInputStream(sext);
//                }catch(Exception e){log.error(e);}
//                next = new Triple(SWBTSUtil.string2Node(rs.getString("subj"),ext), SWBTSUtil.string2Node(rs.getString("prop"),ext), SWBTSUtil.string2Node(rs.getString("obj"),ext));
//            }else
//            {
//                close();
//            }
//        }catch(SQLException e)
//        {
//            log.error(e);
//        }

        //Thread.currentThread().dumpStack();

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
                //System.out.println("SWBTSIterator Closed:"+counter+" tm:"+tm+" "+graph.getName());
//                if(rs!=null)rs.close();
//                if(ps!=null)ps.close();
//                if(con!=null)con.close();
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
        actual= new Triple(SWBTSUtil.string2Node(mpt.subj,mpt.ext), SWBTSUtil.string2Node(mpt.prop,mpt.ext), SWBTSUtil.string2Node(mpt.obj,mpt.ext));
//        next=null;
    //        try
    //        {
    //            if(rs.next())
    //            {
    //                String ext=null;
    //                InputStream sext=rs.getAsciiStream("ext");
    //                try
    //                {
    //                    if(sext!=null)ext=SWBUtils.IO.readInputStream(sext);
    //                }catch(Exception e){log.error(e);}
    //                next = new Triple(SWBTSUtil.string2Node(rs.getString("subj"),ext), SWBTSUtil.string2Node(rs.getString("prop"),ext), SWBTSUtil.string2Node(rs.getString("obj"),ext));
    //            }else
    //            {
    //                close();
    //            }
    //
    //        }catch(SQLException e)
    //        {
    //            log.error(e);
    //        }
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
