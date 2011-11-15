package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;

/**
 *
 * @author serch
 */
public class SWBRTSModelMaker {

    private static Logger log=SWBUtils.getLogger(SWBRTSModelMaker.class);
    HashMap<String,Model> models=new HashMap();
    

    public SWBRTSModelMaker()
    {
        try {
            /*
             * swb/tripleremoteserver=192.168.6.139
             * swb/tripleremoteport=6666
             */
            int port = Integer.parseInt(SWBPlatform.getEnv("swb/tripleremoteport", "6666"));
            String address = SWBPlatform.getEnv("swb/tripleremoteserver",null);
            SWBRTSUtil.initPool(InetAddress.getByName(address), port);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public synchronized Iterator<String> listModelNames()
    {
        Iterator<String> ret = null;
        try {
            
            String params[]={Command.LIST_MODEL_NAMES};
            SWBRTSUtil util = new SWBRTSUtil(params);
            ret=util.call().iterator();
        } catch (Exception e)
        {
            log.error(e);
        }
        return ret;
    }

    public Model getModel(String name)
    {
        Model ret=models.get(name);
        if(ret==null)
        {
            try {
                String params[]={Command.GET_MODEL,name};
                SWBRTSUtil util = new SWBRTSUtil(params);
                List<String> l=util.call();

                if (l.size()>0)
                {
                    ret = new ModelCom(new SWBRTSGraph(name));
                }

            } catch (Exception e)
            {
                log.error(e);
            }
            models.put(name, ret);
        }
        return ret;
    }

    public Model createModel(String name)
    {
        Model model=getModel(name);
        if(model==null)
        {
            try {

                String params[]={Command.CREATE_MODEL,name};
                SWBRTSUtil util = new SWBRTSUtil(params);
                List<String> l=util.call();
                if (l.size()>0)
                {
                    model = new ModelCom(new SWBRTSGraph(name));
                }
            } catch (Exception e)
            {
                log.error(e);
            }
        }
        models.put(name, model);
        return model;
    }

    public synchronized void removeModel(String name)
    {
        try {
            String params[]={Command.REMOVE_MODEL,name};
            SWBRTSUtil util = new SWBRTSUtil(params);
            List<String> l=util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
        models.remove(name);
    }

   
}
