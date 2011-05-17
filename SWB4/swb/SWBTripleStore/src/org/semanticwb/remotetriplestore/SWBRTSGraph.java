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
    private int id;
    private SWBRTSThreadPool pool;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;


    public SWBRTSGraph(int id, String name, SWBRTSThreadPool pool)
    {
        this.id=id;
        this.name=name;
        this.pool=pool;
        pmap=new SWBRTSPrefixMapping(this, pool);
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        //System.out.println("graphBaseFind:"+tm);
        return new SWBRTSIterator(this, tm, pool);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void performAdd(Triple t)
    {
       // System.out.println("graphperformAdd:"+t);
        try
        {
//            Connection con = SWBUtils.DB.getDefaultConnection();
//            PreparedStatement ps = null;

            String subj=SWBTSUtil.node2String(t.getSubject());
            String hsubj=SWBTSUtil.getHashText(subj);
            String prop=SWBTSUtil.node2String(t.getPredicate());
            String hprop=SWBTSUtil.getHashText(prop);
            String obj=SWBTSUtil.node2String(t.getObject());
            String hobj=SWBTSUtil.getHashText(obj);

            String sext="";
            if(hsubj!=null)
            {
                sext+="|subj|"+subj.length()+"|"+subj;
                subj="lgs|"+hsubj;
            }
            if(hprop!=null)
            {
                sext+="|prop|"+prop.length()+"|"+prop;
                prop="lgp|"+hprop;
            }
            if(hobj!=null)
            {
                sext+="|obj|"+obj.length()+"|"+obj;
                obj="lgo|"+hobj;
            }

            try {

                SWBRTSCmd cmd = new SWBRTSCmd();
                cmd.cmd = Command.GRAPH_ADD;
                cmd.paramNumber=5;
                SWBRTSUtil util =  new SWBRTSUtil(pool.getAddress(), pool.getPort());
                util.setCommand(cmd);
                String[] params = {getName(),subj,prop,obj,sext};
                util.setParams(params);
                Future<Response> future = pool.getPool().submit(util);
                Response resp = future.get();
            } catch (Exception e)
            {
                log.error(e);
            }


//            if(sext.length()==0)
//            {
//                ps=con.prepareStatement("INSERT INTO swb_graph_ts"+getId()+" (subj, prop, obj) VALUES (?, ?, ?)");
//            }else
//            {
//                ps=con.prepareStatement("INSERT INTO swb_graph_ts"+getId()+" (subj, prop, obj, ext) VALUES (?, ?, ?, ?)");
//            }
//
//            ps.setString(1, subj);
//            ps.setString(2, prop);
//            ps.setString(3, obj);
//            if(sext.length()>0)ps.setAsciiStream(4, SWBUtils.IO.getStreamFromString(sext),sext.length());
//
//            ps.executeUpdate();
//            ps.close();
//            con.close();
        } catch (Exception e2)
        {
            log.error(e2);
        }
    }

    @Override
    public void performDelete(Triple t)
    {
      //  System.out.println("graphperformDelete:"+t);
//        try
//        {
//            Connection con = SWBUtils.DB.getDefaultConnection();

            String subj=SWBTSUtil.node2HashString(t.getMatchSubject(),"lgs");
            String prop=SWBTSUtil.node2HashString(t.getMatchPredicate(),"lgp");
            String obj=SWBTSUtil.node2HashString(t.getMatchObject(),"lgo");


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
            //System.out.println("performDelete:"+subj+" "+prop+" "+obj);

//            String query="delete from swb_graph_ts"+getId();
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
//            PreparedStatement ps=con.prepareStatement(query);
//            int i=1;
//            if(subj!=null)ps.setString(i++, subj);
//            if(prop!=null)ps.setString(i++, prop);
//            if(obj!=null)ps.setString(i++, obj);
//            ps.executeUpdate();
//            ps.close();
//            con.close();
//        } catch (Exception e2)
//        {
//            log.error(e2);
//        }
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
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
