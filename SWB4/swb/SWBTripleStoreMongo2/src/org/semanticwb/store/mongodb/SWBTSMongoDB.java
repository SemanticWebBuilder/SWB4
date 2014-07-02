/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.mongodb;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.store.jenaimp.SWBTStore;

/**
 *
 * @author javier.solis.g
 */
public class SWBTSMongoDB extends SWBTStore
{
    private static Logger log=SWBUtils.getLogger(SWBTStore.class);    

    @Override
    public void init()
    {
        log.event("SWBTSMongoDB initialized...");
        setMaker(new SWBTSModelMaker("org.semanticwb.store.mongodb.GraphImp"));
    }
    
}
