/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.mem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
    HashMap<String, String> db_bk=null;
    HashMap<String, String> db_bs=null;
    TreeMap<String, String> db_s=null;
    TreeMap<String, String> db_p=null;
    TreeMap<String, String> db_o=null;
    
    boolean closed=false;
    
    Long counter=0L;
    final String COUNTER_KEY="_c_";

    public GraphImp(String name, Map params) throws IOException
    {
        super(name, params);
        
        //setEncodeURIs(false);
        
//        String path=getParam("path");
//        
//        if(!path.endsWith("/"))path=path+"/";
//        path=path+name;
//        File dir=new File(path);
//        dir.mkdirs();
        

        db_bk = new HashMap();
        db_bs = new HashMap();
        
        db_s = new TreeMap();
        db_p = new TreeMap();
        db_o = new TreeMap();
    }
    
    
    @Override
    public SObjectIterator findSObjects(final SObject obj, final boolean reverse)
    {
        TreeMap<String, String> ind=db_s;
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
            Set set=null;
            if(reverse)
            {
                set=ind.descendingMap().subMap(txt+(char)1, txt).entrySet();
            }else
            {
                set=ind.subMap(txt, txt+(char)1).entrySet();
            }
                
            final Iterator<Map.Entry<String,String>> it=set.iterator();
            
            final int fidx=idx;
            ret = new SObjectIterator()
            {                                
                long c=0;
                Map.Entry<String,String> tmp;
                
                @Override
                public boolean hasNext()
                {          
                    return it.hasNext();
                }

                @Override
                public SObject next()
                {
                    tmp=it.next();                    
                    c++;             
                    return new SObject(tmp.getKey(), tmp.getValue(), fidx);
                }
                
                @Override
                public void remove()
                {
                    String t[]=tmp.getKey().split(""+separator);

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
                    while(it.hasNext())
                    {
                        it.next();
                        c++;
                    }
                    this.close();
                    return c;
                }                
            };
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
        db_s.put(data[0], obj.e);
        db_p.put(data[1], obj.e);
        db_o.put(data[2], obj.e);
        return s;
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
        return db_s.size();
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
                    String b=db_bk.get(COUNTER_KEY);
                    if(b!=null)counter=Long.parseLong(b);
                    else counter=0L;
                }
                counter++;
                db_bk.put(COUNTER_KEY, ""+counter);        
            }            
            prefix=Utils.encodeLong(counter);
        }
        addNameSpace2Cache(prefix,ns);
        
        db_bk.put(prefix, ns);
        db_bs.put(ns, prefix);
        
        return prefix;
    }
    
    @Override
    public void removeNameSpace(String prefix)
    {
        String ns=getNameSpace(prefix);
        db_bk.remove(prefix);
        db_bs.remove(ns);        
        removeNameSpace2Cache(prefix);
    }    

    @Override
    public void synchDB()
    {
    }

    @Override
    protected boolean removeSObject(SObject obj)
    {
        db_s.remove(obj.s+(char)separator+obj.p+(char)separator+obj.o);
        db_p.remove(obj.p+(char)separator+obj.o+(char)separator+obj.s);
        db_o.remove(obj.o+(char)separator+obj.s+(char)separator+obj.p);
        return true;    
    }

}
