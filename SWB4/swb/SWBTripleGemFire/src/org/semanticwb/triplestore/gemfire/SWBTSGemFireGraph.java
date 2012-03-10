/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.triplestore.gemfire;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.internal.ResultsBag;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author jei
 */
public class SWBTSGemFireGraph extends GraphBase implements RGraph
{
    private static Logger log = SWBUtils.getLogger(SWBTSGemFireGraph.class);

    private String name;
    private int id;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;


    public SWBTSGemFireGraph(int id, String name)
    {
        this.id=id;
        this.name=name;
        pmap=new SWBTSGemFirePrefixMapping(this);
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        return new SWBTSGemFireIterator(this, tm);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void performAdd(Triple t)
    {
        performAdd(t,null);
    }    

    @Override
    public void performAdd(Triple t, Long id)
    {
        try
        {
            String subj=SWBTSGemFireUtil.node2String(t.getSubject());
            String hsubj=SWBTSGemFireUtil.getHashText(subj);
            String prop=SWBTSGemFireUtil.node2String(t.getPredicate());
            String hprop=SWBTSGemFireUtil.getHashText(prop);
            String obj=SWBTSGemFireUtil.node2String(t.getObject());
            String hobj=SWBTSGemFireUtil.getHashText(obj);
            
            //if(subj==null || prop==null || obj==null)return;

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
            
            Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
            Region<String,SWBTSGemFireTriple> graph= db.getSubregion("swb_graph_ts"+getId());
            if(graph==null)
            {
                graph=db.createSubregion("swb_graph_ts"+getId(), db.getAttributes());
            }
            
            SWBTSGemFireTriple tp=new SWBTSGemFireTriple(subj, prop, obj, sext);
            graph.put(tp.getId(), tp);
        } catch (Exception e2)
        {
            log.error(e2);
        }
    }

    @Override
    public void performDelete(Triple t)
    {
        performDelete(t,null);
    }    

    @Override
    public void performDelete(Triple t, Long id)
    {
        try
        {
            String subj=SWBTSGemFireUtil.node2HashString(t.getMatchSubject(),"lgs");
            String prop=SWBTSGemFireUtil.node2HashString(t.getMatchPredicate(),"lgp");
            String obj=SWBTSGemFireUtil.node2HashString(t.getMatchObject(),"lgo");

            //System.out.println("performDelete:"+subj+" "+prop+" "+obj);
            
            Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
            Region<String,SWBTSGemFireTriple> graph= db.getSubregion("swb_graph_ts"+getId());
            if(graph==null)
            {
                graph=db.createSubregion("swb_graph_ts"+getId(), db.getAttributes());
            }
            
            Iterator<SWBTSGemFireTriple> it=find(graph, subj, prop, obj).iterator();
            while (it.hasNext())
            {
                SWBTSGemFireTriple tp = it.next();
                graph.destroy(tp.getId());
            }
        } catch (Exception e2)
        {
            log.error(e2);
        }
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
    
    
    public ResultsBag find(Region store, String t_subj, String t_prop, String t_obj)
    {
        ArrayList<SWBTSGemFireTriple> ret=new ArrayList<SWBTSGemFireTriple>();
        ArrayList<String> arr=new ArrayList<String>();
        try
        {
            int x=0;
            String subj="";
            if(t_subj!=null)
            {
                arr.add(t_subj);
                x++;
                subj="subj=$"+x;
            }
            String prop="";
            if(t_prop!=null)
            {
                arr.add(t_prop);
                x++;
                prop="prop=$"+x;
            }
            String obj="";
            if(t_obj!=null)
            {
                arr.add(t_obj);
                x++;
                obj="obj=$"+x;
            }
            String query=subj;
            query+=(subj.length()>0 && prop.length()>0)?" and ":"";
            query+=prop;
            query+=(query.length()>0 && obj.length()>0)?" and ":"";
            query+=obj;

            if(query.length()>0)
            {
                query="SELECT * FROM /"+store.getParentRegion().getName()+"/"+store.getName()+" WHERE "+query;
            }else
            {
                query="SELECT * FROM /"+store.getParentRegion().getName()+"/"+store.getName();
            }

            QueryService queryService = SWBTSGemFire.getCache().getQueryService();
            Query q = queryService.newQuery(query);
            
            
            ResultsBag r=(ResultsBag)q.execute(arr.toArray());
            
            //System.out.println("Query:"+query+" ("+arr+")"+" "+r.size());
            
//            SWBTSGemFireTriple[] r=(SWBTSGemFireTriple[])q.execute(arr.toArray());      
//            for(int i=0;i<r.length;i++)
//            {
//                ret.add(r[i]);
//            }

            return r;
            
            //return store.query(query);      

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    

}
