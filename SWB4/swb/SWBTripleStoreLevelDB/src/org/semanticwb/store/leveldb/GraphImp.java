/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.leveldb;

import com.google.common.primitives.Longs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.ReadOptions;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.semanticwb.store.Graph;
import org.semanticwb.store.SObject;
import org.semanticwb.store.SObjectIterator;
import org.semanticwb.store.Triple;
import org.semanticwb.store.utils.Utils;

/**
 *
 * @author javier.solis.g
 */
public class GraphImp extends Graph
{
    DB db_bk=null;
    DB db_bs=null;
    DB db_s=null;
    DB db_p=null;
    DB db_o=null;
    
    ThreadIndex ti_s=null;
    ThreadIndex ti_p=null;
    ThreadIndex ti_o=null;
    ThreadIndex ti_bk=null;
    ThreadIndex ti_bs=null;
    
    boolean closed=false;
    
    Iq80DBFactory factory=Iq80DBFactory.factory;
    
    Long counter=0L;
    final byte COUNTER_KEY[]=factory.bytes("_c_");
    
    ReadOptions ropt=new ReadOptions();

    public GraphImp(String name, Map params) throws IOException
    {
        super(name, params);
        String path=getParam("path");
        
        //setEncodeURIs(false);
        
        if(!path.endsWith("/"))path=path+"/";
        path=path+name;
        File dir=new File(path);
        dir.mkdirs();
        

        Options options = new Options();
        //options.compressionType(CompressionType.NONE);
        options.createIfMissing(true);
        //options.cacheSize(15 * 1048576); // 10MB cache
        
        db_bk = factory.open(new File(path+"/"+name+"_bk"), options);
        db_bs = factory.open(new File(path+"/"+name+"_bs"), options);
        
        options = new Options();
        //options.compressionType(CompressionType.NONE);
        if(false)
        {
            System.out.println("blockSize:"+options.blockSize());
            System.out.println("blockRestartInterval:"+options.blockRestartInterval());
            System.out.println("cacheSize:"+options.cacheSize());
            System.out.println("compressionType:"+options.compressionType());
            System.out.println("maxOpenFiles:"+options.maxOpenFiles());
            System.out.println("paranoidChecks:"+options.paranoidChecks());
            System.out.println("verifyChecksums:"+options.verifyChecksums());
            System.out.println("writeBufferSize:"+options.writeBufferSize());
        }
        //options.blockSize(1024 * 16);
        options.createIfMissing(true);
//        options.cacheSize(32 * 1048576); // 100MB cache
        options.writeBufferSize(16 * 1048576); //8 megas
        //options.verifyChecksums(false);
        
        db_s = factory.open(new File(path+"/"+name+"_s"), options);
        db_p = factory.open(new File(path+"/"+name+"_p"), options);
        db_o = factory.open(new File(path+"/"+name+"_o"), options);
        
        DBIterator it = db_bk.iterator();
        while (it.hasNext())
        {
            Map.Entry<byte[], byte[]> entry = it.next();
            addNameSpace2Cache(factory.asString(entry.getKey()),factory.asString(entry.getValue()));
        }
        it.close();
        
        ti_s=new ThreadIndex(db_s);
        ti_p=new ThreadIndex(db_p);
        ti_o=new ThreadIndex(db_o);
        ti_bk=new ThreadIndex(db_bk);
        ti_bs=new ThreadIndex(db_bs);
        
//        ti_s.start();
//        ti_p.start();
//        ti_o.start();
//        ti_bk.start();
//        ti_bs.start();
        
        ropt.verifyChecksums(false);
        ropt.fillCache(true);
        ropt.snapshot(null);
    }
    
    @Override
    public boolean contains(Triple triple)
    {
        String t[]=encTriple(triple);
        String key=t[0]+(char)separator+t[1]+(char)separator+t[2];
        return db_s.get(factory.bytes(key),ropt)!=null;
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
        //System.out.println("close Graph:"+getName());
        //new Exception().printStackTrace();
        ti_s.close();
        ti_p.close();
        ti_o.close();
        ti_bk.close();
        ti_bs.close();
        
        try
        {
            while(ti_s.isRunning() || ti_p.isRunning() || ti_o.isRunning() || ti_bk.isRunning() || ti_bs.isRunning())
            {
                Thread.sleep(10);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
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
        long ret=0;
        DBIterator it=db_s.iterator();
        while (it.hasNext())
        {
            Map.Entry<byte[], byte[]> entry = it.next();
            ret++;
        }
        return ret;
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
                    byte b[]=db_bk.get(COUNTER_KEY);
                    if(b!=null)counter=Longs.fromByteArray(b);
                    else counter=0L;
                }
                counter++;
                db_bk.put(COUNTER_KEY, Longs.toByteArray(counter));        
            }            
            prefix=Utils.encodeLong(counter);
        }
        //"ns"+db_bk.increment("_c_",1);
        //System.out.println("addNameSpace:"+prefix+" "+ns);
        addNameSpace2Cache(prefix,ns);
        
        ti_bk.add(prefix, ns);
        ti_bs.add(ns, prefix);
        
        return prefix;
    }
    
    @Override
    public void removeNameSpace(String prefix)
    {
        String ns=getNameSpace(prefix);
        ti_bk.remove(prefix);
        ti_bs.remove(ns);        
        removeNameSpace2Cache(prefix);
    }    

    @Override
    public void synchDB()
    {
        try
        {
            while(!ti_s.isEmpty()||!ti_p.isEmpty()||!ti_o.isEmpty()||!ti_bk.isEmpty()||!ti_bs.isEmpty())
            {
                System.out.println("s:"+ti_s.size()+" p:"+ti_p.size()+" o:"+ti_o.size());
                Thread.sleep(500);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void load(String fileName, long from, long to) throws FileNotFoundException
    {
        ti_s.synch(false);
        ti_p.synch(false);
        ti_o.synch(false);
        ti_bk.synch(false);
        ti_bs.synch(false);
        super.load(fileName, from, to); 
        ti_s.synch(true);
        ti_p.synch(true);
        ti_o.synch(true);
        ti_bk.synch(true);
        ti_bs.synch(true);
    }

    @Override
    protected SObjectIterator findSObjects(final SObject obj, boolean reverse)
    {
        //System.out.println("find:"+obj);
        //new Exception().printStackTrace();
        DB ind=db_s;
        int idx=0;
        boolean full=false;
        final String s = obj.s;
        final String p = obj.p;
        final String o = obj.o;
        
        String txt="";
        
        if(s!=null)
        {
            ind=db_s;
            idx=0;
            txt=s+(char)separator;
            if(p!=null)
            {
                txt=txt+p+(char)separator;
                if(o!=null)
                {
                    txt=txt+o;
                    full=true;
                }
            }else if(o!=null)
            {
                ind=db_o;
                idx=2;
                txt=o+(char)separator+s+(char)separator;
            }
        }else if(p!=null)
        {
            ind=db_p;
            idx=1;
            txt=p+(char)separator;
            if(o!=null)
            {
                txt=txt+o+(char)separator;
            }
        }else if(o!=null)
        {
            ind=db_o;
            idx=2;
            txt=o+(char)separator;
        }
        
        SObjectIterator ret=null;
        
        if(full)
        {
            final String key=txt;
            final String val=factory.asString(ind.get(factory.bytes(txt),ropt));
            
            ret = new SObjectIterator()
            {                
                boolean next=false;
                
                @Override
                public boolean hasNext()
                {
                    return !next && val!=null;
                }

                @Override
                public SObject next()
                {
                    next=true;
                    return obj;
                }

                @Override
                public void remove()
                {
                    removeSObject(obj);
                }
                
                @Override
                public void close()
                {                    
                }

                @Override
                public boolean isClosed()
                {
                    return next;
                }

                @Override
                public long count()
                {
                    return hasNext()?1:0;
                }
            };
        }else
        {
            final DBIterator cur=ind.iterator(ropt);
//            System.out.println(txt);
            final byte key[]=factory.bytes(txt);
            final int fidx=idx;

//            if(!reverse)
//            {            
                if(key.length>0)cur.seek(key);
                
                ret = new SObjectIterator()
                {                                
                    long c=0;
                    Map.Entry<byte[], byte[]> tmp=null;

                    Map.Entry<byte[], byte[]> act=null;
                    boolean closed=false;

                    @Override
                    public boolean hasNext()
                    {          
                        if(tmp==null && cur.hasNext())tmp=cur.next();                    
                        boolean ret=tmp!=null && Utils.startWidth(tmp.getKey(), key);
                        if(!ret)this.close();
                        return ret;
                    }

                    @Override
                    public SObject next()
                    {
                        SObject ret=null;
                        if(!closed)
                        {                    
                            c++;
                            ret=new SObject(factory.asString(tmp.getKey()), factory.asString(tmp.getValue()), fidx);
                            act=tmp;
                            if(cur.hasNext())tmp=cur.next();
                            else tmp=null;
                        }
                        //System.out.println("next:"+ret);
                        return ret;
                    }

                    @Override
                    public void remove()
                    {
                        String t[]=factory.asString(act.getKey()).split(""+separator);

                        String ts=null,tp=null,to=null;

                        if(fidx==0)
                        {
                            ts=t[0];
                            tp=t[1];
                            to=t[2];
                        }else if(fidx==1)
                        {
                            ts=t[2];
                            tp=t[0];
                            to=t[1];
                        }else if(fidx==2)
                        {
                            ts=t[1];
                            tp=t[2];
                            to=t[0];
                        }                    
                        removeSObject(new SObject(ts,tp,to,null));                    
                    }

                    @Override
                    public void close()
                    {
                        if(!closed)
                        {
                            try
                            {
                                cur.close();;
                            }catch(Throwable e)
                            {
                                e.printStackTrace();
                            }
                            closed=true;
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
                        while(cur.hasNext())
                        {
                            Map.Entry<byte[], byte[]> tmp=cur.next();
                            if(!Utils.startWidth(tmp.getKey(), key))break;
                            c++;
                        }
                        this.close();
                        return c;
                    }                
                };
//            }else
//            {
//                if(key.length>0)cur.seek(factory.bytes(txt+(char)1));
//                ret = new SObjectIterator()
//                {                                
//                    long c=0;
//                    Map.Entry<byte[], byte[]> tmp=null;
//
//                    Map.Entry<byte[], byte[]> act;
//                    boolean closed=false;
//
//                    @Override
//                    public boolean hasNext()
//                    {          
//                        if(tmp==null && cur.hasPrev())tmp=cur.prev();                    
//                        boolean ret=tmp!=null && Utils.startWidth(tmp.getKey(), key);
//                        if(!ret)this.close();
//                        return ret;
//                    }
//
//                    @Override
//                    public SObject next()
//                    {
//                        SObject ret=null;
//                        if(!closed)
//                        {                    
//                            c++;
//                            ret=new SObject(factory.asString(tmp.getKey()), factory.asString(tmp.getValue()), fidx);
//                            act=tmp;
//                            if(cur.hasPrev())tmp=cur.prev();
//                            else tmp=null;
//                        }
//                        return ret;
//                    }
//
//                    @Override
//                    public void remove()
//                    {
//                        String t[]=factory.asString(tmp.getKey()).split(""+separator);
//
//                        String ts=null,tp=null,to=null;
//
//                        if(fidx==0)
//                        {
//                            ts=t[0];
//                            tp=t[1];
//                            to=t[2];
//                        }else if(fidx==1)
//                        {
//                            ts=t[2];
//                            tp=t[0];
//                            to=t[1];
//                        }else if(fidx==2)
//                        {
//                            ts=t[1];
//                            tp=t[2];
//                            to=t[0];
//                        }                    
//                        removeSObject(new SObject(ts,tp,to,null));                    
//                    }
//
//                    @Override
//                    public void close()
//                    {
//                        if(!closed)
//                        {
//                            try
//                            {
//                                cur.close();;
//                            }catch(Throwable e)
//                            {
//                                e.printStackTrace();
//                            }
//                            closed=true;
//                        }
//                    }
//
//                    @Override
//                    public boolean isClosed()
//                    {
//                        return closed;
//                    }
//
//                    @Override
//                    public long count()
//                    {               
//                        while(cur.hasPrev())
//                        {
//                            Map.Entry<byte[], byte[]> tmp=cur.prev();
//                            if(!Utils.startWidth(tmp.getKey(), key))break;
//                            c++;
//                        }
//                        this.close();
//                        return c;
//                    }                
//                };                
//            }
                
            if(reverse)
            {
                final ArrayList<SObject> arr=new ArrayList();
                while (ret.hasNext())
                {
                    SObject sObject = ret.next();
                    arr.add(sObject);
                }
                
                final ListIterator<SObject> it=arr.listIterator(arr.size());
                
                return new SObjectIterator()
                {
                    SObject tmp=null;

                    @Override
                    public boolean hasNext()
                    {
                        return it.hasPrevious();
                    }

                    @Override
                    public SObject next()
                    {
                        tmp=it.previous();
                        return tmp;
                    }

                    @Override
                    public long count()
                    {
                        return (long)arr.size();
                    }

                    @Override
                    public void remove()
                    {
                        removeSObject(tmp);
                    }
                };
            }
        }
        return ret;
    }

    @Override
    protected boolean removeSObject(SObject obj)
    {
        //System.out.println("remove:"+obj);
        try
        {
            db_s.delete(factory.bytes(obj.s+(char)separator+obj.p+(char)separator+obj.o));
            db_p.delete(factory.bytes(obj.p+(char)separator+obj.o+(char)separator+obj.s));
            db_o.delete(factory.bytes(obj.o+(char)separator+obj.s+(char)separator+obj.p));        
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean addSObject(SObject obj, boolean thread)
    {
//        System.out.println("add:"+obj);
        boolean s=true;
        
        String data[]=new String[3];
        data[0]=obj.s+(char)separator+obj.p+(char)separator+obj.o;
        data[1]=obj.p+(char)separator+obj.o+(char)separator+obj.s;
        data[2]=obj.o+(char)separator+obj.s+(char)separator+obj.p;
        
        //System.out.println("addTriple:"+data[0]);
        db_s.put(factory.bytes(data[0]), factory.bytes(obj.e));
        if(thread)
        {
            //ti_s.add(data[0],t[3]);
            ti_p.add(data[1],obj.e);
            ti_o.add(data[2],obj.e);
        }else
        {
            //s=db_s.add(data[0], t[3]);
            if(s)
            {
                db_p.put(factory.bytes(data[1]), factory.bytes(obj.e));
                db_o.put(factory.bytes(data[2]), factory.bytes(obj.e));
            }
        }
        return s;
        
    }

    
}
