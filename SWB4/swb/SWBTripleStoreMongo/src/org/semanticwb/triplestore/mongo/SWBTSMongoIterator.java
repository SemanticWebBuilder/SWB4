/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.triplestore.mongo;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class SWBTSMongoIterator implements ExtendedIterator<Triple>
{

    private static Logger log=SWBUtils.getLogger(SWBTSMongoIterator.class);

    private SWBTSMongoGraph graph=null;
    private TripleMatch tm=null;

    private DBCursor cur;

    private Triple actual=null;
    private Triple next=null;

    private boolean closed=false;

    private static int counter=0;

    public SWBTSMongoIterator(SWBTSMongoGraph graph, TripleMatch tm)
    {
        counter++;
        //System.out.println("SWBTSMongoIterator:"+counter+" tm:"+tm+" "+graph.getName());
        
        this.graph=graph;
        this.tm=tm;

        String subj=SWBTSMongoUtil.node2HashString(tm.getMatchSubject(),"lgs");
        String prop=SWBTSMongoUtil.node2HashString(tm.getMatchPredicate(),"lgp");
        String obj=SWBTSMongoUtil.node2HashString(tm.getMatchObject(),"lgo");
        
        //System.out.println("subj:"+subj+" prop:"+prop+" obj:"+obj);

        try
        {
            DB db = SWBTSMongo.getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
            if(SWBPlatform.getEnv("swb/mongodbuser")!=null && SWBPlatform.getEnv("swb/mongodbpasswd")!=null)
            {
                db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
            }
            
            DBCollection coll = db.getCollection("swb_graph_ts"+graph.getId());
            
            BasicDBObject doc = new BasicDBObject();
            
            if(subj!=null)doc.put("subj", subj);
            if(prop!=null)doc.put("prop", prop);
            if(obj!=null)doc.put("obj", obj);
            
            cur=coll.find(doc);
            
            if(cur.hasNext())
            {
                DBObject dbobj=cur.next();
                
                String ext=(String)dbobj.get("ext");
                next = new Triple(SWBTSMongoUtil.string2Node((String)dbobj.get("subj"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("prop"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("obj"),ext));
            }else
            {
                close();
            }
        }catch(MongoException e)
        {
            log.error(e);
        }

        //Thread.currentThread().dumpStack();
    }

    @Override
    public Triple removeNext()
    {
        Triple tp=next();
        remove();
        return tp;
    }

    @Override
    public <X extends Triple> ExtendedIterator<Triple> andThen(Iterator<X> other)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExtendedIterator<Triple> filterKeep(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExtendedIterator<Triple> filterDrop(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <U> ExtendedIterator<U> mapWith(Map1<Triple, U> map1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Triple> toList()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Triple> toSet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close()
    {
        if(!closed)
        {
            closed=true;
            try
            {
                counter--;
                if(cur!=null)cur.close();
            } catch (Exception ex)
            {
                log.error(ex);
            }
        }
    }

    public boolean hasNext()
    {
        return next!=null;
    }

    public Triple next()
    {
        actual=next;
        next=null;
        try
        {
            if(cur.hasNext())
            {
                DBObject dbobj=cur.next();
                String ext=(String)dbobj.get("ext");
                next = new Triple(SWBTSMongoUtil.string2Node((String)dbobj.get("subj"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("prop"),ext), SWBTSMongoUtil.string2Node((String)dbobj.get("obj"),ext));
            }else
            {
                close();
            }

        }catch(MongoException e)
        {
            log.error(e);
        }
        return actual;
    }

    public void remove()
    {
        graph.performDelete(actual);
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