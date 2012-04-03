/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.triplestore.mongo;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.sql.Connection;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.RGraph;

/**
 *
 * @author jei
 */
public class SWBTSMongoGraph extends GraphBase implements RGraph
{
    private static Logger log = SWBUtils.getLogger(SWBTSMongoGraph.class);

    private String name;
    private int id;

    private PrefixMapping pmap;
    //private BigdataTransactionHandler trans;


    public SWBTSMongoGraph(int id, String name)
    {
        this.id=id;
        this.name=name;
        pmap=new SWBTSMongoPrefixMapping(this);
    }

    @Override
    protected ExtendedIterator<Triple> graphBaseFind(TripleMatch tm)
    {
        return new SWBTSMongoIterator(this, tm);
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
            String subj=SWBTSMongoUtil.node2String(t.getSubject());
            String hsubj=SWBTSMongoUtil.getHashText(subj);
            String prop=SWBTSMongoUtil.node2String(t.getPredicate());
            String hprop=SWBTSMongoUtil.getHashText(prop);
            String obj=SWBTSMongoUtil.node2String(t.getObject());
            String hobj=SWBTSMongoUtil.getHashText(obj);
            
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
            
            //System.out.println("performAdd:"+subj+" "+prop+" "+obj);
            //new Exception().printStackTrace();
            
            DB db = SWBTSMongo.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
            
            if(SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
            {
                db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
            }
            
            DBCollection coll = db.getCollection("swb_graph_ts"+getId());
            
            BasicDBObject doc = new BasicDBObject();
            doc.put("subj", subj);
            doc.put("prop", prop);
            doc.put("obj", obj);
            if(sext.length()>0)
            {
                doc.put("ext", sext);
            }
            coll.insert(doc);

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
            String subj=SWBTSMongoUtil.node2HashString(t.getMatchSubject(),"lgs");
            String prop=SWBTSMongoUtil.node2HashString(t.getMatchPredicate(),"lgp");
            String obj=SWBTSMongoUtil.node2HashString(t.getMatchObject(),"lgo");

            //System.out.println("performDelete:"+subj+" "+prop+" "+obj);
            
            DB db = SWBTSMongo.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
            if(SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
            {
                db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
            }            
            
            DBCollection coll = db.getCollection("swb_graph_ts"+getId());
            
            BasicDBObject doc = new BasicDBObject();
            
            if(subj!=null)doc.put("subj", subj);
            if(prop!=null)doc.put("prop", prop);
            if(obj!=null)doc.put("obj", obj);
            
            coll.findAndRemove(doc);
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

}
