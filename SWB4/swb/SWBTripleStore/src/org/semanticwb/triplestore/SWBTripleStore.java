/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.triplestore;

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
public class SWBTripleStore implements AbstractStore
{
    private static Logger log=SWBUtils.getLogger(SWBTripleStore.class);

    SWBTSModelMaker maker;

    public void init()
    {
        log.event("SWBTripleStore Initializing...");
        maker=new SWBTSModelMaker();
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

}
