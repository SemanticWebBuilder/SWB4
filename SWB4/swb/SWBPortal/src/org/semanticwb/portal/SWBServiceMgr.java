/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

/**
 *
 * @author javier.solis
 */
public class SWBServiceMgr implements SemanticObserver
{
    private static Logger log=SWBUtils.getLogger(SWBServiceMgr.class);

    public void notify(SemanticObject obj, Object prop, String action) {
        System.out.println("obj:"+obj+" prop:"+prop+" action:"+action+" "+Thread.currentThread().getName());
    }

    public void init()
    {
        log.event("Initializing SWBServiceMgr...");
        SWBPlatform.getSemanticMgr().registerObserver(this);
    }

}
