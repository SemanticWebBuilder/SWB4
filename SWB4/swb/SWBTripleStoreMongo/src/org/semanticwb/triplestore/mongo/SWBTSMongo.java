package org.semanticwb.triplestore.mongo;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.mongodb.Mongo;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;

/**
 *
 * @author jei
 */
public class SWBTSMongo implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(SWBTSMongo.class);

    SWBTSMongoModelMaker maker;
    
    private static Mongo mongo;
    
    public static Mongo getMongo()
    {
        if(mongo==null)
        { 
            log.event("Initializing MongoDB Connection to:"+SWBPlatform.getEnv("swb/tripleremoteserver", "localhost" )+":"+SWBPlatform.getEnv("swb/tripleremoteport", "27017" ));
            try
            {
                mongo = new Mongo( SWBPlatform.getEnv("swb/tripleremoteserver", "localhost" ), Integer.parseInt(SWBPlatform.getEnv("swb/tripleremoteport", "27017" )));
            }catch(Exception e)
            {
                log.error(e);
            }        
        }
        return mongo;
    }
    
    @Override
    public void init()
    {
        log.event("SWBTSMongo Initializing...");
        maker=new SWBTSMongoModelMaker();
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
        if(mongo!=null)
        {
            mongo.close();
            mongo=null;
        }
    }

    @Override
    public Dataset getDataset()
    {
        return null;
    }

    public SWBTSMongoModelMaker getMaker()
    {
        return maker;
    }
}
