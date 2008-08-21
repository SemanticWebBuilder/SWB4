package org.semanticwb;

import java.sql.Timestamp;
import org.semanticwb.portal.SWBDBAdmLog;
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
    private static String workPath = "";

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
        workPath = (String) SWBInstance.getEnv("swb/workPath");
    }
    
    /** Getter for property workPath.
     * @return Value of property workPath.
     */
    public static String getWorkPath()
    {
        return workPath;
    }

    public SWBServices getSWBServices() {
        SWBServices swbServices = new SWBServices();
        return swbServices;
    }

    public DnsSrv getDnsSrv() {
        DnsSrv dnsSrv = new DnsSrv();
        return dnsSrv;
    }

    public DeviceSrv getDeviceSrv() {
        DeviceSrv deviceSrv = new DeviceSrv();
        return deviceSrv;
    }

    public CalendarSrv getCalendarSrv() {
        CalendarSrv calendarSrv = new CalendarSrv();
        return calendarSrv;
    }

    public GroupSrv getGrouprv() {
        GroupSrv groupSrv = new GroupSrv();
        return groupSrv;
    }

    public ResourceSrv getResourcerv() {
        ResourceSrv resSrv = new ResourceSrv();
        return resSrv;
    }

    public IPFilterSrv getIPFilterSrv() {
        IPFilterSrv iPFilterSrv = new IPFilterSrv();
        return iPFilterSrv;
    }

    public RoleSrv getRoleSrv() {
        RoleSrv roleSrv = new RoleSrv();
        return roleSrv;
    }

    public RuleSrv getRuleSrv() {
        RuleSrv ruleSrv = new RuleSrv();
        return ruleSrv;
    }

    public TemplateSrv getTemplateSrv() {
        TemplateSrv templateSrv = new TemplateSrv();
        return templateSrv;
    }

    public WebPageSrv getWebPageSrv() {
        WebPageSrv webPageSrv = new WebPageSrv();
        return webPageSrv;
    }

    public WebSiteSrv getWebSiteSrv() {
        WebSiteSrv webSiteSrv = new WebSiteSrv();
        return webSiteSrv;
    }

    public LanguageSrv getLanguageSrv() {
        LanguageSrv langSrv = new LanguageSrv();
        return langSrv;
    }

    public CampSrv getCampSrv() {
        CampSrv campSrv = new CampSrv();
        return campSrv;
    }

    public PFlowSrv getPFlowSrv() {
        PFlowSrv pFlowSrv = new PFlowSrv();
        return pFlowSrv;
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
    public void log(String userID, String action, String uri, String object, String description, Timestamp date)
    {
        SWBDBAdmLog swbAdmLog = new SWBDBAdmLog(userID, action, uri, object, description, date);
        try {
            swbAdmLog.create();
        } catch (Exception e) {
            log.error(e);
        }
    }
}