package org.semanticwb.portal.resources.projectdriver;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.SWBContextBase;

public class SWBContext extends SWBContextBase
{
    private static Logger log=SWBUtils.getLogger(SWBContext.class);
    private static SWBContext instance=null;
    static public synchronized SWBContext createInstance()
    {
        if (instance == null)
        {
            instance = new SWBContext();
        }
        return instance;
    }
    private SWBContext()
    {
        log.event("Initializing SemanticWebBuilder Context...");
    }
}