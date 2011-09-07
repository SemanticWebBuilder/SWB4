package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;

/**
 *
 * @author serch
 */
public class SWBRemoteTripleStore implements AbstractStore {

    private static Logger log=SWBUtils.getLogger(SWBRemoteTripleStore.class);

    SWBRTSModelMaker maker;

    public void init()
    {
        log.event("SWBRemoteTripleStore Initializing...");
        maker=new SWBRTSModelMaker();
    }

    public void removeModel(String name)
    {
        maker.removeModel(name);
    }

    public Model loadModel(String name)
    {
        return maker.createModel(name);
    }

    public Iterator<String> listModelNames()
    {
        return maker.listModelNames();
    }

    public void close()
    {
        //Nothing
    }

    public Dataset getDataset()
    {
        return null;
    }

    public Model getModel(String name) {
        return maker.getModel(name);
    }

}
