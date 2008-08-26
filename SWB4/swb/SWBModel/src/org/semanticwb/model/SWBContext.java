package org.semanticwb.model;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.SWBContextBase;

public class SWBContext extends SWBContextBase
{
    private static Logger log=SWBUtils.getLogger(SWBContext.class);
    
    public static String WEBSITE_ADMIN="SWBAdmin";
    public static String WEBSITE_GLOBAL="SWBGlobal";
    
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
        log.event("Initialize Semantic WebBuilder Context...");
    }
    
    public static WebSite getAdminWebSite()
    {
        return getWebSite(WEBSITE_ADMIN);
    }
    
    public static WebSite getGlobalWebSite()
    {
        return getWebSite(WEBSITE_GLOBAL);
    }    
}