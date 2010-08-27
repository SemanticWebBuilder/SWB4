/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/** Objeto: Se encarga de manejar los objetos SWBResourceCached disponibles en memoria.
 *
 * Object: It is in chatge of manage the SWBResourceCached objects availables in memory.
 *
 * @author Javier Solis Gonzalez
 */
public class SWBResourceCachedMgr
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBResourceCachedMgr.class);

    /** The cache. */
    private Hashtable cache;
    
    /** The mrulist. */
    private LinkedList mrulist;
    
    /** The cachesize. */
    private int cachesize;
    
    /** The resource hits. */
    private long resourceHits;
    
    /** The cache hits. */
    private long cacheHits;
    
    /** The cache load hits. */
    private long cacheLoadHits;

    /**
     * Creates a new instance of SWBResourceCacheMgr.
     * 
     * @param max the max
     */
    public SWBResourceCachedMgr(int max)
    {
        log.event("Initializing SWBResourceCachedMgr: " + max);
        cache = new Hashtable();
        mrulist = new LinkedList();
        cachesize = max;
    }

    /**
     * Gets the resource.
     * 
     * @param res the res
     * @param request the request
     * @param paramsRequest the params request
     * @return the resource
     * @return
     */
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

    /**
     * Removes the resource from cache
     * 
     * @param base the base
     */
    public void removeResource(Resource base)
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
    
    /**
     * Gets the key.
     * 
     * @param base the base
     * @return the key
     */
    public static String getKey(Resource base)
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
     * 
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
     * 
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
     * 
     */
    public void incCacheLoadHits()
    {
        this.cacheLoadHits++;;
    }

    public synchronized void clearCache(){
        cache.clear();
        mrulist.clear();
    }
    
}
