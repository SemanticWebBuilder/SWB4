package org.semanticwb;

import org.semanticwb.model.IPFilter;
import org.semanticwb.portal.services.*;

public class SWBPortal 
{
    private static Logger log=SWBUtils.getLogger(SWBPortal.class);
    private static SWBPortal instance=null;
    static public synchronized SWBPortal createInstance()
    {
        System.out.println("Entra a createInstance");
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
    
    public static WebSiteSrv getWebSiteSrv()
    {
        WebSiteSrv webSiteSrv=new WebSiteSrv();
        return webSiteSrv;
    }  
    
    public static WebPageSrv getWebPageSrv()
    {
        WebPageSrv webPageSrv=new WebPageSrv();
        return webPageSrv;
    } 
    
    public static DeviceSrv getDeviceSrv()
    {
        DeviceSrv deviceSrv=new DeviceSrv();
        return deviceSrv;
    } 
    
    public static DnsSrv getDnsSrv()
    {
        DnsSrv dnsSrv=new DnsSrv();
        return dnsSrv;
    } 
    
    public static IPFilterSrv getIPFilterSrv()
    {
        IPFilterSrv ipFilterSrv=new IPFilterSrv();
        return ipFilterSrv;
    } 
    
}