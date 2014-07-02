/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.kyoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import kyotocabinet.Cursor;
import kyotocabinet.DB;
import org.semanticwb.store.Graph;
import org.semanticwb.store.SObject;
import org.semanticwb.store.SObjectIterator;
import org.semanticwb.store.Triple;
import org.semanticwb.store.TripleIterator;
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

    public GraphImp(String name, Map params)
    {
        super(name, params);
        String path=getParam("path");
        
        if(!path.endsWith("/"))path=path+"/";
        path=path+name;
        File dir=new File(path);
        dir.mkdirs();
        
        //String argsk="#opts=l#bnum=2000000#msiz=100m";
        //String argst="#opts=l#bnum=20000000#msiz=2g#pccap=400m";
        String argsk="#pccap=20m";
        String argst="#pccap=200m";
        db_bk = new DB();
        db_bs = new DB();
        db_s = new DB();
        db_p = new DB();
        db_o = new DB();
        if (
            !db_bk.open(path+"/"+name+"_bk.kch"+argsk, DB.OWRITER | DB.OCREATE) ||
            !db_bs.open(path+"/"+name+"_bs.kch"+argsk, DB.OWRITER | DB.OCREATE) ||
            !db_s.open(path+"/"+name+"_s.kct"+argst, DB.OWRITER | DB.OCREATE) ||   
            !db_p.open(path+"/"+name+"_p.kct"+argst, DB.OWRITER | DB.OCREATE) ||   
            !db_o.open(path+"/"+name+"_o.kct"+argst, DB.OWRITER | DB.OCREATE)    
//            !db_bk.open("*"+argsk, DB.OWRITER | DB.OCREATE) ||
//            !db_bs.open("*"+argsk, DB.OWRITER | DB.OCREATE) ||
//            !db_s.open("%"+argst, DB.OWRITER | DB.OCREATE) ||   
//            !db_p.open("%"+argst, DB.OWRITER | DB.OCREATE) ||   
//            !db_o.open("%"+argst, DB.OWRITER | DB.OCREATE)    
        )  //DB.OAUTOTRAN DB.OAUTOSYNC
        {
            throw new RuntimeException("Error openin Store:"+name);
        }
        
        Cursor cur = db_bk.cursor();
        cur.jump();
        String rec[];
        while ((rec = cur.get_str(true)) != null)
        {
            addNameSpace2Cache(rec[0],rec[1]);
            //System.out.println("ns:"+rec[0] + ":" + rec[1]);
        }
        cur.disable();
        
        ti_s=new ThreadIndex(db_s);
        ti_p=new ThreadIndex(db_p);
        ti_o=new ThreadIndex(db_o);
        ti_bk=new ThreadIndex(db_bk);
        ti_bs=new ThreadIndex(db_bs);
        
        ti_s.start();
        ti_p.start();
        ti_o.start();
        ti_bk.start();
        ti_bs.start();
    }
    
    
    @Override
    public void begin()
    {
        if(isTransactionEnabled())
        {
            db_s.begin_transaction(false);
            db_p.begin_transaction(false);
            db_o.begin_transaction(false);
        }
    }

    @Override
    public void commit()
    {
        if(isTransactionEnabled())
        {
            db_s.end_transaction(true);
            db_p.end_transaction(true);
            db_o.end_transaction(true);
        }
    }

    @Override
    public void rollback()
    {
        if(isTransactionEnabled())
        {
            db_s.end_transaction(false);
            db_p.end_transaction(false);
            db_o.end_transaction(false);
        }
    }

    @Override
    public void close()
    {
        System.out.println("close Graph:"+getName());
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
        return db_s.count();
    }
    
    @Override
    public String addNameSpace(String prefix, String ns)
    {
        if(prefix==null)prefix=Utils.encodeLong(db_bk.increment("_c_",1,0));//"ns"+db_bk.increment("_c_",1);
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
            final String val=ind.get(txt);
            
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
                    SObject ret=new SObject(key, val, 0);
                    next=true;
                    return ret;
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
            final Cursor cur=ind.cursor();
            final String key=txt;
            final int fidx=idx;
            if(key.length()>0)cur.jump(key);

            ret = new SObjectIterator()
            {
                long c=0;
                String tmp[]=cur.get_str(true);
                String act[];
                boolean closed=false;
                
//                public void TripleIterator()
//                {
//                    tmp=cur.get_str(true);
//                }
                
                @Override
                public boolean hasNext()
                {          
                    boolean ret=tmp!=null && tmp[0].startsWith(key);
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
                        ret=new SObject(tmp[0], tmp[1], fidx);
                        act=tmp;
                        tmp=cur.get_str(true);
                    }
                    return ret;
                }
                
                @Override
                public void remove()
                {
                    String t[]=act[0].split(""+separator);
//                    StringTokenizer st=new StringTokenizer(act[0],""+separator);
//
//                    String t1=st.nextToken();
//                    String t2=st.nextToken();
//                    String t3=st.nextToken();

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
                            cur.disable();
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
                    String txt=cur.get_key_str(false);
                    while(txt!=null && txt.startsWith(key))
                    {
                        c++;
                        txt=cur.get_key_str(true);
                    }
                    this.close();
                    return c;
                }                
            };
        }
        return ret;        
    }

    @Override
    protected boolean removeSObject(SObject obj)
    {
        boolean ret=db_s.remove(obj.s+(char)separator+obj.p+(char)separator+obj.o);
        if(ret)
        {
            db_p.remove(obj.p+(char)separator+obj.o+(char)separator+obj.s);
            db_o.remove(obj.o+(char)separator+obj.s+(char)separator+obj.p);
        }
        return ret;
    }

    @Override
    protected boolean addSObject(SObject obj, boolean thread)
    {
        boolean s=true;
        
        String data[]=new String[3];
        data[0]=obj.s+(char)separator+obj.p+(char)separator+obj.o;
        data[1]=obj.p+(char)separator+obj.o+(char)separator+obj.s;
        data[2]=obj.o+(char)separator+obj.s+(char)separator+obj.p;
        
        //System.out.println(data[0]+" "+data[3]);
        s=db_s.add(data[0], obj.e);
        if(s && thread)
        {
            //ti_s.add(data[0],t[3]);
            ti_p.add(data[1],obj.e);
            ti_o.add(data[2],obj.e);
        }else if(s)
        {
            //s=db_s.add(data[0], t[3]);
            if(s)
            {
                boolean p=db_p.add(data[1], obj.e);
                boolean o=db_o.add(data[2], obj.e);
            }
        }
        return s;
    }

    
}
