/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class TDBStore implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(TDBStore.class);

    /** The Dataset */
    private Dataset set;
    /** The timer. */
    private Timer timer;                        //Commiter


    public void init()
    {
        log.info("TDB Detected...," + SWBPlatform.createInstance().getPlatformWorkPath() + "/data");
        TDB.getContext().set(TDB.symUnionDefaultGraph,true);
        set = TDBFactory.createDataset(SWBPlatform.createInstance().getPlatformWorkPath() + "/data");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                commit();
            }
        }, 60000, 30000);
    }

    public void removeModel(String name)
    {
        Model model=loadModel(name);
        if(model!=null)model.removeAll();
    }

    public Model loadModel(String name)
    {
        return set.getNamedModel(name);
        //return SDBFactory.connectNamedModel(store, name);
    }

    public Iterator<String> listModelNames()
    {
        return set.listNames();
    }
    
    public Model getModel(String name) 
    {
        Iterator<String> it=listModelNames();
        while (it.hasNext()) {
            String mname = it.next();
            if(mname.equals(name))
            {
                return loadModel(name);
            }
        }
        return null;
    }    

    /**
     * Commit all models.
     */
    private void commit()
    {
        log.trace("ServerMgr.Commit()");
        try {
            Iterator<String> it = listModelNames();
            while (it.hasNext()) {
                Model model = loadModel(it.next());
                model.commit();
            }
        } catch (ConcurrentModificationException noe) {
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void close()
    {
        //System.out.println("ServerMgr.Close()");
        timer.cancel();
        timer=null;
        commit();
        set.close();
    }

    public Dataset getDataset()
    {
        return set;
    }
    
}
