/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.base.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author javier.solis.g
 */
public class HashMapCache<K extends Object, V extends Object> implements Map<K,V>
{
    private int maxSize;
    ConcurrentHashMap<K,V> map;
    LinkedList<K> linked;
    
    public HashMapCache(int maxSize)
    {
        map=new ConcurrentHashMap();
        linked= new LinkedList();
        this.maxSize=maxSize;
    }

    /**
     * @return the maxSize
     */
    public int getMaxSize()
    {
        return maxSize;
    }

    /**
     * @param maxSize the maxSize to set
     */
    public void setMaxSize(int maxSize)
    {
        this.maxSize = maxSize;
    }

    @Override
    public int size()
    {
        return map.size();
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object obj)
    {
        return map.containsKey(obj);
    }

    @Override
    public boolean containsValue(Object obj)
    {
        return map.containsValue(obj);
    }

    @Override
    public V get(Object o)
    {
        return map.get(o);
    }

    @Override
    public V put(K k, V v)
    {
        V r=map.put(k, v);
        linked.add(k);
        if(map.size()>maxSize)
        {
            crop();
        }  
        return r;
    }
    
    private synchronized void crop()
    {
        while(!linked.isEmpty() && map.size()>maxSize)
        {
            Object obj=linked.poll();
            map.remove(obj);
        }          
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map)
    {
        this.map.putAll(map);
    }

    @Override
    public void clear()
    {
        map.clear();
        linked.clear();
    }

    @Override
    public Set<K> keySet()
    {
        return map.keySet();
    }

    @Override
    public Collection<V> values()
    {
        return map.values();
    }

    @Override
    public Set<Entry<K,V>> entrySet()
    {
        return map.entrySet();
    }

    @Override
    public V remove(Object o)
    {
        return map.remove(o);
    }
    
}
