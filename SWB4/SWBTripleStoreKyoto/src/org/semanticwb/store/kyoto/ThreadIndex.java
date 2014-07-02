/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.kyoto;

import java.util.concurrent.ConcurrentLinkedQueue;
import kyotocabinet.DB;


/**
 *
 * @author javier.solis.g
 */
public class ThreadIndex extends Thread
{
    ConcurrentLinkedQueue<NodeIndex>add=new ConcurrentLinkedQueue();
    ConcurrentLinkedQueue<NodeIndex>rem=new ConcurrentLinkedQueue();
    DB db;
    boolean closed=false;
    transient boolean empty=true;
    long synchTime=60000*5;
    long time;
    boolean synch=true;
    transient boolean running=false;
    
    
    public ThreadIndex(DB db)
    {
        this.db=db;
        time=System.currentTimeMillis();
    }
    
    public void add(String key, String value)
    {
        if(running)
        {
            NodeIndex node=new NodeIndex(key,value);
            add.add(node);
        }else
        {
            db.add(key, value);
        }
    }
    
    public void remove(String key)
    {
        if(running)
        {
            NodeIndex node=new NodeIndex(key,null);
            rem.add(node);
        }else
        {
            db.remove(key);
        }
    }
    
    @Override
    public void run()
    {
        running=true;
        try
        {
            while(!closed || !add.isEmpty() || !rem.isEmpty())
            {
                try
                {
                    while(!add.isEmpty() || !rem.isEmpty())
                    {
                        empty=false;
                        if(!add.isEmpty())
                        {
                            int s=add.size()*2;
                            if(s>100000)s=100000;
                            byte[][] recary = new byte[s][];
                            for (int x=0;x<s;) 
                            {
                                NodeIndex t=add.poll();
                                recary[x++] = t.key.getBytes("UTF-8");
                                recary[x++] = t.value.getBytes("UTF-8");
                            }
                            db.set_bulk(recary, false);

    //                        NodeIndex t=null;
    //                        while((t=add.poll())!=null)
    //                        {
    //                            db.add(t.key, t.value);
    //                        }
                            time=System.currentTimeMillis();
                        }

                        if(!rem.isEmpty())
                        {
                            int s=rem.size()*2;
                            if(s>100000)s=100000;
                            byte[][] recary = new byte[s][];
                            for (int x=0;x<s;) 
                            {
                                NodeIndex t=rem.poll();
                                recary[x++] = t.key.getBytes("UTF-8");
                                recary[x++] = t.value.getBytes("UTF-8");
                            }
                            db.remove_bulk(recary, false);

    //                        NodeIndex t=null;
    //                        while((t=rem.poll())!=null)
    //                        {
    //                            db.remove(t.key);
    //                        }
                            time=System.currentTimeMillis();
                        }      
                    }
                    empty=true;

                    if(System.currentTimeMillis()-time>synchTime)
                    {
                        if(synch)
                        {
                            System.out.println("start synch:"+db);
                            db.synchronize(false, null);
                            System.out.println("end synch:"+db);
                        }
                        time=System.currentTimeMillis();
                    }

                    sleep(100);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }finally
        {
            try
            {
                db.close();
            }finally
            {
                running=false;
            }
        }
    }
    
    public boolean isEmpty()
    {
        //if(!empty)System.out.println("isSynch:"+add.size()+" "+rem.size());
        return empty;
    }
    
    public long size()
    {
        return add.size()+rem.size();
    }

    public void close()
    {
        if(!running && !closed)
        {
            try
            {
                db.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }        
        closed = true;
    }
    
    public void synch(boolean synch)
    {
        this.synch=synch;
    }
    
    public boolean isRunning()
    {
        return running;
    }
}
class NodeIndex
{
    String key;
    String value;

    public NodeIndex(String key, String value)
    {
        this.key = key;
        this.value = value;
    }
    
    
}