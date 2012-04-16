package org.semanticwb.triplestore.gemfire;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;

/**
 *
 * @author jei
 */
public class SWBTSGemFire implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(SWBTSGemFire.class);

    SWBTSGemFireModelMaker maker;
    
    private static Cache cache;
    
        public static Cache getCache()
    {
        if(cache==null)
        { 
            log.event("Initializing GemFire Connection...");
            try
            {
                CacheFactory cf=new CacheFactory().set("cache-xml-file", "xml/gemfire.xml").set("mcast-port", "0");
                if(SWBPlatform.getEnv("swb/gemfire_locators")!=null)
                {
                    cf.set("locators", SWBPlatform.getEnv("swb/gemfire_locators"));
                }
                
                if(SWBPlatform.getEnv("swb/gemfire_license-application-cache")!=null)
                {
                    cf.set("license-application-cache", SWBPlatform.getEnv("swb/gemfire_license-application-cache"));
                }
                
                if(SWBPlatform.getEnv("swb/gemfire_license-data-management")!=null)
                {
                    cf.set("license-data-management", SWBPlatform.getEnv("swb/gemfire_license-data-management"));
                }
                
                cache = cf.create();
            }catch(Exception e)
            {
                log.error(e);
            }        
        }
        return cache;
    }
    
    @Override
    public void init()
    {
        log.event("SWBTSGemFire Initializing...");
        maker=new SWBTSGemFireModelMaker();
    }

    @Override
    public void removeModel(String name)
    {
        maker.removeModel(name);
    }

    @Override
    public Model loadModel(String name)
    {
        return maker.createModel(name);
    }

    @Override
    public Iterator<String> listModelNames()
    {
        return maker.listModelNames();
    }
    
    @Override
    public Model getModel(String name) 
    {
        return maker.getModel(name);
    }    

    @Override
    public void close()
    {
        if(cache!=null)
        {
            cache.close();
            cache=null;
        }
    }

    @Override
    public Dataset getDataset()
    {
        return null;
    }

    public SWBTSGemFireModelMaker getMaker()
    {
        return maker;
    }
}
