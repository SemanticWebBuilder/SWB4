/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.util.Map;
import org.semanticwb.store.Graph;
import org.semanticwb.store.SObject;
import org.semanticwb.store.SObjectIterator;
import org.semanticwb.store.utils.Utils;

/**
 *
 * @author javier.solis.g
 */
public class GraphImp extends Graph
{    
    boolean closed=false;
    
    Long counter=0L;
    final String COUNTER_KEY="_c_";
    
    private MongoClient mongo;    
    private DB db;    
    private DBCollection coll;    
    private DBCollection ns_coll;    
    
    public MongoClient getMongo()
    {
        if(mongo==null)
        { 
            try
            {
                //TODO: db.authenticate(SWBPlatform.getEnv("swb/mongodbuser"), SWBPlatform.getEnv("swb/mongodbpasswd").toCharArray());
                //mongo = new MongoClient();
                mongo = new MongoClient( getParam("swb/tripleremoteserver", "localhost" ), Integer.parseInt(getParam("swb/tripleremoteport", "27017" )));
            }catch(Exception e)
            {
                e.printStackTrace();
            }        
        }
        return mongo;
    }    
    

    public GraphImp(String name, Map params) throws IOException
    {
        super(name, params);
        
        //setEncodeURIs(false);
        
        db = getMongo().getDB(getParam("swb/mongodbname","swb"));
        //db = getMongo().getDB("swb");
        if(!db.collectionExists("swb_graph_ts_"+name))
        {
            coll = db.getCollection("swb_graph_ts_"+name);
            BasicDBObject index=new BasicDBObject("subj", -1);
            index.put("prop", 1);
            index.put("obj", -1);
            coll.createIndex(index);                                
            
            index=new BasicDBObject("prop", 1);
            index.put("obj", -1);
            coll.createIndex(index);
            
            index=new BasicDBObject("obj", -1);
            index.put("subj", -1);
            coll.createIndex(index); 
                        
            ns_coll = db.getCollection("swb_graph_ns_"+name);
        }else
        {
            coll = db.getCollection("swb_graph_ts_"+name);
            ns_coll = db.getCollection("swb_graph_ns_"+name);
        }
        
        DBCursor cur=ns_coll.find();            
        while(cur.hasNext())
        {
            DBObject dbobj=cur.next();
            addNameSpace2Cache(dbobj.get("prefix").toString(),dbobj.get("ns").toString());
            //System.out.println(dbobj.get("prefix").toString()+" "+dbobj.get("ns").toString());
        }
        cur.close();
    }
    
    @Override
    public void begin()
    {
        if(isTransactionEnabled())
        {
//            db_s.begin_transaction(false);
//            db_p.begin_transaction(false);
//            db_o.begin_transaction(false);
        }
    }

    @Override
    public void commit()
    {
        if(isTransactionEnabled())
        {
//            db_s.end_transaction(true);
//            db_p.end_transaction(true);
//            db_o.end_transaction(true);
        }
    }

    @Override
    public void rollback()
    {
        if(isTransactionEnabled())
        {
//            db_s.end_transaction(false);
//            db_p.end_transaction(false);
//            db_o.end_transaction(false);
        }
    }

    @Override
    public void close()
    {
        System.out.println("close Graph:"+getName());
        //new Exception().printStackTrace();
        if(mongo!=null)mongo.close();
        closed=true;
    }

    @Override
    public boolean isClosed()
    {
        return closed;
    }        

    @Override
    public long count()
    {
//        DB db = getMongo().getDB(SWBPlatform.getEnv("swb/mongodbname","swb"));
//        DBCollection coll = db.getCollection("swb_graph_ts_"+getName());
        return coll.count();
    }
    
    @Override
    public String addNameSpace(String prefix, String ns)
    {
        if(prefix==null)
        {
            synchronized(COUNTER_KEY)
            {
                if(counter==0)
                {
                    BasicDBObject doc = new BasicDBObject();
                    doc.put("prefix", COUNTER_KEY);                    
                    DBCursor cur=ns_coll.find(doc);            
                    if(cur.hasNext())
                    {
                        DBObject dbobj=cur.next();
                        counter=Long.parseLong(dbobj.get("ns").toString());
                    }
                }
                counter++;
                BasicDBObject doc1 = new BasicDBObject();
                doc1.put("prefix", COUNTER_KEY);
                BasicDBObject doc2 = new BasicDBObject();
                doc2.put("prefix", COUNTER_KEY);
                doc2.put("ns", ""+counter);
                ns_coll.findAndModify(doc1, doc2);
            }            
            prefix=Utils.encodeLong(counter);
        }
        //"ns"+db_bk.increment("_c_",1);
        //System.out.println("addNameSpace:"+prefix+" "+ns);
        addNameSpace2Cache(prefix,ns);
        
        BasicDBObject doc = new BasicDBObject();
        doc.put("prefix", prefix);
        doc.put("ns", ns);
        ns_coll.insert(doc);         
        
        return prefix;
    }
    
    @Override
    public void removeNameSpace(String prefix)
    {
        //String ns=getNameSpace(prefix);
        BasicDBObject doc = new BasicDBObject();
        doc.put("prefix", prefix);
        //doc.put("ns", ns);
        ns_coll.findAndRemove(doc);        
        removeNameSpace2Cache(prefix);
    }    

    @Override
    public void synchDB()
    {
    }

    @Override
    protected SObjectIterator findSObjects(SObject obj, boolean reverse)
    {
        SObjectIterator ret=null;        
        {
            BasicDBObject doc = new BasicDBObject();
            if(obj.s!=null)doc.put("subj", obj.s);
            if(obj.p!=null)doc.put("prop", obj.p);
            if(obj.o!=null)doc.put("obj", obj.o);
            
//            System.out.println("FindDoc:"+doc);
            
            final DBCursor cur=coll.find(doc);            
            
//            System.out.println("res size:"+cur.count());
            
            ret = new SObjectIterator()
            {                   
                boolean closed=false;
                
                SObject tmp=null;
                
                @Override
                public boolean hasNext()
                {   boolean ret=!closed && cur.hasNext();
                    return ret;
                }

                @Override
                public SObject next()
                {
                    DBObject dbobj=cur.next();
                    String ts=(String)dbobj.get("subj");
                    String tp=(String)dbobj.get("prop");
                    String to=(String)dbobj.get("obj");
                    String te=(String)dbobj.get("ext");
                    
                    tmp= new SObject(ts,tp,to,te);    
                    if(!cur.hasNext())close();
                    return tmp;
                }
                
                @Override
                public void remove()
                {
                    removeSObject(tmp);                    
                }

                @Override
                public void close()
                {
                    if(!closed)
                    {
                        closed=true;
                        cur.close();
                    }
                }

                @Override
                public boolean isClosed()
                {
                    return closed;
                }
                
                @Override
                public long count()
                {   
                    long r=cur.count();
                    close();
                    return r;
                }                
            };
        }
        return ret;        
    }

    @Override
    protected boolean removeSObject(SObject obj)
    {
        try
        {
            BasicDBObject doc = new BasicDBObject();
            if(obj.s!=null)doc.put("subj", obj.s);
            if(obj.p!=null)doc.put("prop", obj.p);
            if(obj.o!=null)doc.put("obj", obj.o);
            coll.findAndRemove(doc);        
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean addSObject(SObject obj, boolean thread)
    {
        try
        {
            BasicDBObject doc = new BasicDBObject();
            if(obj.s!=null)doc.put("subj", obj.s);
            if(obj.p!=null)doc.put("prop", obj.p);
            if(obj.o!=null)doc.put("obj", obj.o);
            if(obj.e!=null)doc.put("ext", obj.e);
            
            //System.out.println("addTriple:"+doc);
            coll.insert(doc);        
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    
    
    
}
