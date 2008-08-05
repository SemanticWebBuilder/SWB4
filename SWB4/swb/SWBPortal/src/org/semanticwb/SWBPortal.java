package org.semanticwb;

public class SWBPortal 
{
    private static Logger log=SWBUtils.getLogger(SWBPortal.class);
    private static SWBPortal instance=null;
    static public synchronized SWBPortal createInstance()
    {
        if (instance == null)
        {
            instance = new SWBPortal();
        }
        return instance;
    }
    
    private SWBPortal()
    {
        log.event("Initialize Semantic WebBuilder Portal...");
    }
}