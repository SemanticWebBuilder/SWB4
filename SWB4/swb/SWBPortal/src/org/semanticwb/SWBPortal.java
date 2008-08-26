package org.semanticwb;

import java.io.File;
import java.sql.Timestamp;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.platform.SWBInstanceObject;
import org.semanticwb.portal.SWBDBAdmLog;
import org.semanticwb.portal.SWBUserMgr;
import org.semanticwb.portal.services.CalendarSrv;
import org.semanticwb.portal.services.CampSrv;
import org.semanticwb.portal.services.DeviceSrv;
import org.semanticwb.portal.services.DnsSrv;
import org.semanticwb.portal.services.GroupSrv;
import org.semanticwb.portal.services.IPFilterSrv;
import org.semanticwb.portal.services.LanguageSrv;
import org.semanticwb.portal.services.PFlowSrv;
import org.semanticwb.portal.services.ResourceSrv;
import org.semanticwb.portal.services.RoleSrv;
import org.semanticwb.portal.services.RuleSrv;
import org.semanticwb.portal.services.SWBServices;
import org.semanticwb.portal.services.TemplateSrv;
import org.semanticwb.portal.services.WebPageSrv;
import org.semanticwb.portal.services.WebSiteSrv;

public class SWBPortal {

    private static Logger log = SWBUtils.getLogger(SWBPortal.class);
    private static SWBPortal instance = null;
    
    private static SWBUserMgr usrMgr;

    static public synchronized SWBPortal createInstance() {
        System.out.println("Entra a createInstance");
        if (instance == null) {
            instance = new SWBPortal();
        }
        return instance;
    }

    private SWBPortal() {
        log.event("Initialize Semantic WebBuilder Portal...");
       init();
    }
    
     //Initialize context
    private void init()
    {
        
        usrMgr=new SWBUserMgr();
        usrMgr.init();
    }

    public static SWBServices getSWBServices() {
        SWBServices swbServices = new SWBServices();
        return swbServices;
    }

    public static DnsSrv getDnsSrv() {
        DnsSrv dnsSrv = new DnsSrv();
        return dnsSrv;
    }

    public static DeviceSrv getDeviceSrv() {
        DeviceSrv deviceSrv = new DeviceSrv();
        return deviceSrv;
    }

    public static CalendarSrv getCalendarSrv() {
        CalendarSrv calendarSrv = new CalendarSrv();
        return calendarSrv;
    }

    public static GroupSrv getGrouprv() {
        GroupSrv groupSrv = new GroupSrv();
        return groupSrv;
    }

    public static ResourceSrv getResourcerv() {
        ResourceSrv resSrv = new ResourceSrv();
        return resSrv;
    }

    public static IPFilterSrv getIPFilterSrv() {
        IPFilterSrv iPFilterSrv = new IPFilterSrv();
        return iPFilterSrv;
    }

    public static RoleSrv getRoleSrv() {
        RoleSrv roleSrv = new RoleSrv();
        return roleSrv;
    }

    public static RuleSrv getRuleSrv() {
        RuleSrv ruleSrv = new RuleSrv();
        return ruleSrv;
    }

    public static TemplateSrv getTemplateSrv() {
        TemplateSrv templateSrv = new TemplateSrv();
        return templateSrv;
    }

    public static WebPageSrv getWebPageSrv() {
        WebPageSrv webPageSrv = new WebPageSrv();
        return webPageSrv;
    }

    public static WebSiteSrv getWebSiteSrv() {
        WebSiteSrv webSiteSrv = new WebSiteSrv();
        return webSiteSrv;
    }

    public static LanguageSrv getLanguageSrv() {
        LanguageSrv langSrv = new LanguageSrv();
        return langSrv;
    }

    public static CampSrv getCampSrv() {
        CampSrv campSrv = new CampSrv();
        return campSrv;
    }

    public static PFlowSrv getPFlowSrv() {
        PFlowSrv pFlowSrv = new PFlowSrv();
        return pFlowSrv;
    }
    
    
    public static SWBUserMgr getUserMgr()
    {
        return usrMgr;
    }
    
    
    /**
     * Logeo de acciones
     * @param userID
     * @param action
     * @param uri
     * @param object
     * @param description
     * @param date
     */
    public static void log(String userID, String action, String uri, String object, String description, Timestamp date)
    {
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(userID, action, uri, object, description, date);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            log.error(e);
        }
    }
}