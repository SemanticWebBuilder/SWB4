/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;




/**
 *
 * @author javier.solis.g
 */
public class SWBIteratorCache
{
    private static HashMap<String, IteratorCacheNode> map=new HashMap();
    
    public static Iterator getIterator(String id, GetIterator git, long time)
    {
        IteratorCacheNode node=map.get(id);
        if(node==null)
        {
            synchronized(map)
            {
                node=map.get(id);
                if(node==null)
                {
                    node=new IteratorCacheNode(git, time);
                    map.put(id, node);
                }
            }
            return node.getList().iterator();
        }else
        {
            return node.getList().iterator();
        }
    }
}
class IteratorCacheNode
{
    private GetIterator git;
    private volatile List list;
    private long endTime;
    private long time;

    public IteratorCacheNode(GetIterator git, long time)
    {
        this.git=git;
        this.time=time;
    }

    public List getList()
    {
        List ret=list;
        if(list==null)
        {
            synchronized(this)
            {
                if(list==null)
                {
                    ArrayList arr=new ArrayList();
                    Iterator it=git.getIterator();
                    while (it.hasNext())
                    {
                        Object object = it.next();
                        arr.add(object);
                    }
                    list=arr;                    
                    endTime=System.currentTimeMillis()+time;
                }
                ret=list;
            }
        }else
        {
            if(System.currentTimeMillis()>endTime)
            {
                list=null;
            }
        }
        return ret;
    }
}
