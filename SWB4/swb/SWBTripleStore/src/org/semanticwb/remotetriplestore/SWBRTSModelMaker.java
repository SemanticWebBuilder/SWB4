package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.remotetriplestore.protocol.Command;
import org.semanticwb.remotetriplestore.protocol.Response;
import org.semanticwb.remotetriplestore.protocol.SWBRTSCmd;

/**
 *
 * @author serch
 */
public class SWBRTSModelMaker {

    private static Logger log=SWBUtils.getLogger(SWBRTSModelMaker.class);
    private SWBRTSThreadPool pool;
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
            pool = new SWBRTSThreadPool(InetAddress.getByName(address), port);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public synchronized Iterator<String> listModelNames()
    {
        Iterator<String> ret = null;
        try {
            
            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.LIST_MODEL_NAMES;
            cmd.paramNumber=0;
            SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            //Future<Response> future = pool.getPool().submit(util);
            //Response resp = future.get();
            Response resp = util.call();
            ArrayList<String> list = (ArrayList<String>) resp.data;
            ret = list.iterator();
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
                SWBRTSCmd cmd = new SWBRTSCmd();
                cmd.cmd = Command.GET_MODEL;
                cmd.paramNumber=1;
                SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
                util.setCommand(cmd);
                String[] params = {name};
                util.setParams(params);
                //Future<Response> future = pool.getPool().submit(util);
                //Response resp = future.get();
                Response resp = util.call();
                if (null!=resp.data)
                {
                    ret = new ModelCom(new SWBRTSGraph(name, pool));
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

            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.CREATE_MODEL;
            cmd.paramNumber=1;
            SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {name};
            util.setParams(params);
            //Future<Response> future = pool.getPool().submit(util);
            //Response resp = future.get();
            Response resp = util.call();
            if (null!=resp.data)
            {
                model = new ModelCom(new SWBRTSGraph(name, pool));
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
            SWBRTSCmd cmd = new SWBRTSCmd();
            cmd.cmd = Command.REMOVE_MODEL;
            cmd.paramNumber=1;
            SWBRTSUtil util =  SWBRTSUtil.getInstance(pool.getAddress(), pool.getPort());
            util.setCommand(cmd);
            String[] params = {name};
            util.setParams(params);
            //Future<Response> future = pool.getPool().submit(util);
            //Response resp = future.get();
            Response resp = util.call();
        } catch (Exception e)
        {
            log.error(e);
        }
        models.remove(name);
    }

   
}
