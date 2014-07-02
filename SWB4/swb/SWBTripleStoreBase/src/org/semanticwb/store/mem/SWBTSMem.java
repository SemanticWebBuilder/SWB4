/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store.mem;

import com.hp.hpl.jena.mem.faster.GraphMemFaster;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.store.jenaimp.SWBTSModelMaker;
import org.semanticwb.store.jenaimp.SWBTStore;

/**
 *
 * @author javier.solis.g
 */
public class SWBTSMem extends SWBTStore
{
    private static Logger log=SWBUtils.getLogger(SWBTStore.class);    

    @Override
    public void init()
    {
        log.event("SWBTSMem initialized...");
        setMaker(new SWBTSModelMaker("org.semanticwb.store.mem.GraphImp"));
    }
    
}
