package org.semanticwb.triplestore.ext;

import org.semanticwb.triplestore.*;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;

/**
 *
 * @author jei
 */
public class SWBTripleStoreExt implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(SWBTripleStoreExt.class);

    SWBTSModelMaker maker;

    public void init()
    {
        log.event("SWBTripleStore2 Initializing...");
        maker=new SWBTSModelMakerExt();
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
    
    public Model getModel(String name) 
    {
        return maker.getModel(name);
    }    

    public void close()
    {
        //Nothing
    }

    public Dataset getDataset()
    {
        return null;
    }

    public SWBTSModelMaker getMaker()
    {
        return maker;
    }
}
