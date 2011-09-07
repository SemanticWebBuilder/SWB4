/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bigdata;

import com.hp.hpl.jena.rdf.model.Model;
import java.io.File;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;
import com.hp.hpl.jena.query.Dataset;

/**
 *
 * @author jei
 */
public class BigdataStore implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(BigdataStore.class);

    BigdataModelMaker maker;

    public void init()
    {
        String path=SWBPlatform.createInstance().getPlatformWorkPath() + "/data";
        log.info("Bigdata Detected...," + path);
        maker=new BigdataModelMaker(path);
    }

    public void removeModel(String name)
    {
        String path=SWBPlatform.createInstance().getPlatformWorkPath() + "/data";
        File journal = new File(path+"/"+name+".jnl");
        journal.delete();
    }

    public Model loadModel(String name)
    {
        return maker.createModel(name, false);
    }

    public Iterator<String> listModelNames()
    {
        return maker.listModelNames();
    }
    

    public void close()
    {
        //set.close();
    }

    public Dataset getDataset()
    {
        return null;
    }

    public Model getModel(String name) 
    {
        return maker.getModel(name, false);
    }    
}
