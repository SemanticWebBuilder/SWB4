package org.semanticwb;

import java.sql.Timestamp;
import org.semanticwb.model.*;
import org.semanticwb.portal.SWBDBAdmLog;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.portal.SWBResourceMgr;
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
    private static SWBMonitor monitor=null;    
    private static SWBResourceMgr resmgr=null;    

    static public synchronized SWBPortal createInstance() {
        //System.out.println("Entra a createInstance");
        if (instance == null) {
            instance = new SWBPortal();
        }
        return instance;
    }

    private SWBPortal() {
        log.event("Initializing SemanticWebBuilder Portal...");
       init();
    }
    
     //Initialize context
    private void init()
    {
        //Check for GlobalWebSite
        WebSite site=SWBContext.getGlobalWebSite();
        if(site==null)
        {
            log.event("Creating Global WebSite...");
            site=SWBContext.createWebSite(SWBContext.WEBSITE_GLOBAL, "http://org.semanticwb.globalws");
            site.setTitle("Global");
            site.setDescription("Global WebSite");
            //Crear lenguajes por defecto
            Language lang=site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang=site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            //Create HomePage
            WebPage home=site.createWebPage("home");
            site.setHomePage(home);
            //Create DNS
            Dns dns=site.createDns("localhost");
            dns.setTitle("localhost");
            dns.setDescription("DNS por default","es");
            dns.setDescription("Default DNS","en");
            dns.setDnsDefault(true);
            dns.setWebPage(home);
        }
        //Check for GlobalWebSite
        UserRepository urep=SWBContext.getDefaultRepository();
        if(urep==null)
        {
            log.event("Creating Default User Repository...");
            urep=SWBContext.createUserRepository(SWBContext.USERREPOSITORY_DEFAULT, "http://org.semanticwb.userrep");
            urep.setTitle("Default UserRepository");
            urep.setDescription("Default UserRpository");
            site.setUserRepository(urep);
            //Create User
            User user=urep.createUser();
            user.setUsrLogin("admin");
            user.setUsrPassword("webbuilder");
            user.setUsrEmail("admin@semanticwebbuilder.org");
            user.setUsrFirstName("Admin");
        }
        
        usrMgr=new SWBUserMgr();
        usrMgr.init();
        
        monitor=new SWBMonitor();
        monitor.init();        
        
        resmgr=new SWBResourceMgr();
        resmgr.init();
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
    
    public static SWBMonitor getMonitor() 
    {
        return monitor;
    }   
    
    public static SWBResourceMgr getResourceMgr()
    {
        return resmgr;
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