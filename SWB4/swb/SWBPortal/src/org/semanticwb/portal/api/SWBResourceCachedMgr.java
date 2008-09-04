/*
 * SWBResourceCacheMgr.java
 *
 * Created on 5 de noviembre de 2002, 18:01
 */

package org.semanticwb.portal.api;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;

/** Objeto: Se encarga de manejar los objetos SWBResourceCached disponibles en memoria.
 *
 * Object: It is in chatge of manage the SWBResourceCached objects availables in memory.
 *
 * @author Javier Solis Gonzalez
 */
public class SWBResourceCachedMgr
{
    private static Logger log = SWBUtils.getLogger(SWBResourceCachedMgr.class);

    private Hashtable cache;
    private LinkedList mrulist;
    private int cachesize;
    
    private long resourceHits;
    private long cacheHits;
    private long cacheLoadHits;

    /** Creates a new instance of SWBResourceCacheMgr
     * @param max  */
    public SWBResourceCachedMgr(int max)
    {
        log.event("Initializing SWBResourceCachedMgr: " + max);
        cache = new Hashtable();
        mrulist = new LinkedList();
        cachesize = max;
    }

    /**
     * @param res
     * @return  */
    public SWBResource getResource(SWBResource res, HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        //System.out.println("getCache:"+res.getResourceBase().getId());
        //System.out.println("cachesize:"+mrulist.size());
        SWBResource ret = res;
        
        String key=getKey(res.getResourceBase());
        
        if(res instanceof SWBResourceCache)
        {
            try
            {
                key = ((SWBResourceCache)res).getResourceCacheID(request, paramsRequest);
            }catch(Exception e)
            {
                log.error(e);
            }
        }
        
        if(key!=null)
        {
            if ((ret=(SWBResource)cache.get(key))==null)
            {
                //System.out.println("cache not found:"+key);
                synchronized (mrulist)
                {
                    if (mrulist.size() >= cachesize)
                    {
                        cache.remove(mrulist.getLast());
                        mrulist.removeLast();
                    }
                    mrulist.addFirst(key);
                }
                ret = new SWBResourceCached(res);
                cache.put(key, ret);
            } else
            {
                //System.out.println("cache found:"+key);
                synchronized (mrulist)
                {
                    //ret = (SWBResource) cache.get(key);
                    mrulist.remove(key);
                    mrulist.addFirst(key);
                }
            }
        }
        return ret;
    }

    public void removeResource(Portlet base)
    {
        String mkey=getKey(base);
        Enumeration en=cache.keys();
        while(en.hasMoreElements())
        {
            String key=(String)en.nextElement();
            if(key.startsWith(mkey))
            {
                mrulist.remove(key);
                cache.remove(key);
            }
        }
    }
    
    public static String getKey(Portlet base)
    {
        return base.getURI();
    }

    /**
     * Getter for property resHits.
     * @return Value of property resHits.
     */
    public long getResourceHits()
    {
        return resourceHits;
    }
    
    /**
     * Setter for property cacheLoadHits.
     * @param cacheLoadHits New value of property cacheLoadHits.
     */
    public void incResourceHits()
    {
        this.resourceHits++;;
    }     
    
    /**
     * Getter for property cacheHits.
     * @return Value of property cacheHits.
     */
    public long getCacheHits()
    {
        return cacheHits;
    }
    
    /**
     * Setter for property cacheLoadHits.
     * @param cacheLoadHits New value of property cacheLoadHits.
     */
    public void incCacheHits()
    {
        this.cacheHits++;;
    }      
    
    /**
     * Getter for property cacheLoadHits.
     * @return Value of property cacheLoadHits.
     */
    public long getCacheLoadHits()
    {
        return cacheLoadHits;
    }
    
    /**
     * Setter for property cacheLoadHits.
     * @param cacheLoadHits New value of property cacheLoadHits.
     */
    public void incCacheLoadHits()
    {
        this.cacheLoadHits++;;
    }
    
}
