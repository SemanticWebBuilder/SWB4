/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
package org.semanticwb;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import com.hp.hpl.jena.rdf.model.Model;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.base.db.DBConnectionPool;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.PFlowManager;
import org.semanticwb.portal.SWBMessageCenter;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.portal.SWBResourceMgr;
import org.semanticwb.portal.SWBServiceMgr;
import org.semanticwb.portal.SWBTemplateMgr;
import org.semanticwb.portal.SWBUserMgr;
import org.semanticwb.portal.access.SWBAccessIncrement;
import org.semanticwb.portal.access.SWBAccessLog;
import org.semanticwb.portal.db.SWBDBAdmLog;
import org.semanticwb.portal.indexer.SWBIndexMgr;
import org.semanticwb.portal.resources.ImageGallery;
import org.semanticwb.portal.util.ContentStyles;
import org.semanticwb.util.JarFile;
import org.semanticwb.util.db.GenericDB;

/**
 * Clase que contiene utilerias para manejo de portal (SemanticWebBuilder)
 * Class that contains utils for management of portal (SemanticWebBuilder)
 * javier Solís, Jorge jiménez
 * @version 1.0
 */
public class SWBPortal
{
    private static Properties props=null;
    private String contextPath="/";
    private static String webWorkPath = "";
    private static String workPath = "";
    private static ServletContext servletContext=null;
    private static Filter virtualHostFilter=null;

    private static boolean haveDB=false;
    private static boolean haveDBTables=false;
    private static boolean useDB=true;

    private static boolean client=false;

    private static Logger log = SWBUtils.getLogger(SWBPortal.class);
    private static SWBPortal instance = null;
    private static HashMap hAnchors = null;
    private static HashMap admFiles = new HashMap();
    private static SWBUserMgr usrMgr;
    private static PFlowManager pflowMgr;
    private static SWBMonitor monitor = null;
    private static SWBResourceMgr resmgr = null;
    private static SWBTemplateMgr templatemgr = null;
    private static SWBServiceMgr servicemgr = null;
    private static SWBDBAdmLog admlog = null;
    private static SWBMessageCenter msgcenter = null;
    private static SWBAccessLog acclog = null;
    private static SWBAccessIncrement accInc = null;
    private static SWBIndexMgr indmgr = null;

    static public synchronized SWBPortal createInstance(ServletContext servletContext)
    {
        //new Exception().printStackTrace();
        return createInstance(servletContext, null);
    }

    /** Create Instance.
     * @param servletContext
     * @return  SWBContext*/
    static public synchronized SWBPortal createInstance(ServletContext servletContext, Filter filter)
    {
        if (instance == null)
        {
            SWBPortal.virtualHostFilter=filter;
            instance = new SWBPortal();
            SWBPortal.servletContext=servletContext;

        }
        return instance;
    }

//    private void createInstance(String clsname)
//    {
//        try
//        {
//            Class cls = Class.forName(clsname);
//            Method createInstance = cls.getMethod("createInstance", (Class[])null);
//            createInstance.invoke(null, (Object[])null);
//        }catch(ClassNotFoundException e)
//        {
//            log.warn(clsname+" not found...");
//        }catch(Exception e)
//        {
//            log.error("Error initializing "+clsname+"..",e);
//        }
//    }

    private SWBPortal() {
        log.event("Initializing SemanticWebBuilder Portal...");
        init();
    }
    //Initialize context

    private void init() 
    {
        props=SWBUtils.TEXT.getPropertyFile("/web.properties");

        workPath = getEnv("swb/workPath");

        SWBUtils.EMAIL.setSMTPServer(getEnv("swb/smtpServer"));
        SWBUtils.EMAIL.setSMTPUser(getEnv("swb/smtpUser"));
        SWBUtils.EMAIL.setSMTPPassword(getEnv("swb/smtpPassword"));
        SWBUtils.EMAIL.setAdminEmail(getEnv("af/adminEmail"));

        try {
            //TODO:revisar sincronizacion
            //if (confCS.equalsIgnoreCase("Client")) remoteWorkPath = (String) AFUtils.getInstance().getEnv("swb/remoteWorkPath");

            workPath = (String) getEnv("swb/workPath","/work");
            if (workPath.startsWith("file:"))
            {
                workPath = (new File(workPath.substring(5))).toString();
            } else if (workPath.startsWith("http://"))
            {
                workPath = (URLEncoder.encode(workPath));
            } else {
                workPath = SWBUtils.getApplicationPath()+workPath;
            }
        } catch (Exception e) {
            log.error("Can't read the context path...",e);
            workPath = "";
        }
        //System.out.println("workPath:"+workPath);

        //Insercion de parametros a Platform
        SWBPlatform platform=SWBPlatform.createInstance();
        platform.setPlatformWorkPath(workPath);
        platform.setStatementsCache(getEnv("swb/ts_statementsCache","false").equals("false"));
        String persistType=getEnv("swb/triplepersist",SWBPlatform.PRESIST_TYPE_DEFAULT);
        platform.setPersistenceType(persistType);
        platform.setAdminDev(getEnv("swb/adminDev", "false").equalsIgnoreCase("true"));
        platform.setAdminFile(getEnv("swb/adminFile", "/swbadmin/rdf/SWBAdmin.nt"));
        platform.setOntEditFile(getEnv("swb/ontEditFile", "/swbadmin/rdf/SWBOntEdit.nt"));
        platform.setProperties(props);

        try
        {
            //Runtime.getRuntime().gc();
//            long inimem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            Connection con=SWBUtils.DB.getDefaultConnection();
            if(con!=null)
            {
                log.info("-->Database: "+SWBUtils.DB.getDatabaseName());
                haveDB=true;
                //Si no existen las tablas, se crean automaticamente
//                PreparedStatement sr=con.prepareStatement("select * from swb_counter");
//                try
//                {
//                    ResultSet rs=sr.executeQuery();
//                    if(rs!=null)
//                    {
                        haveDBTables=true;
//                        rs.close();
//                    }
//                }catch(Exception noe){log.debug("Table swbcounter not found...",noe);}
//                sr.close();
                con.close();
            }

            if(!haveDB())
            {
                log.warn("-->Default SemanticWebBuilder database not found...");
                return;
            }
            if(!haveDBTables())
            {
                log.warn("-->Default SemanticWebBuilder database tables not found...");
                return;
            }

            try
            {
                System.setProperty("sun.net.client.defaultConnectTimeout", getEnv("swb/defaultConnectTimeout","5000"));
                System.setProperty("sun.net.client.defaultReadTimeout", getEnv("swb/defaultReadTimeout","30000"));
            }catch(Exception e)
            {
                log.error("Error seting defaultConnectTimeout, defaultReadTimeout");
            }

            //TODO: agregar modelos
            //semanticMgr.init(this);

//            Timestamp initime = null;
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_InstanceConfiguration") + util.getEnv("wb/clientServer"), true);
//            cpus = getNumProcessors();
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_ProcessorNumber") + cpus, true);
//            util.log("IP:\t\t\t"+java.net.InetAddress.getLocalHost(),true);
//
//            try
//            {
//                UpdateVersion updater = new UpdateVersion(version);
//                updater.update();
//            } catch (Exception e)
//            {
//                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_WBLoader_Init_versioneerror"), true);
//            }
//
            if (!"ignore".equals(getEnv("swb/security.auth.login.config","ignore"))) {
                System.setProperty("java.security.auth.login.config",SWBUtils.getApplicationPath()+"/WEB-INF/classes"+getEnv("swb/security.auth.login.config","/wb_jaas.config"));
                log.info("-->Login Config:"+System.getProperty("java.security.auth.login.config"));
            }
//            String dbsync = util.getEnv("wb/DBSync","false");

//            if (wbutil.isClient() || dbsync.trim().equalsIgnoreCase("true"))
//            {
//                initime = DBDbSync.getInstance().getLastUpdate();
//                objs.add(DBDbSync.getInstance());
//            }
//
//            //if admin
//            if (!wbutil.isClient())
//            {
//                objs.add(DBResHits.getInstance());
//                objs.add(DBAdmLog.getInstance());
//                objs.add(WBAccessLog.getInstance());
//                objs.add(WBIndexMgr.getInstance());
//                //objs.add(WBFileReplicatorSrv.getInstance());
//            } else
//            {
//                util.log("remoteWorkPath --> " + wbutil.getRemoteWorkPath(), true);
//            }
//
//            if (wbutil.isClient() || dbsync.trim().equalsIgnoreCase("true"))
//            {
//                timer = WBDBSyncTimer.getInstance();
//                timer.init();
//                objs.add(timer);
//            }
//
            //cargando clases de startup.properties
            SWBStartup startup = new SWBStartup(new ArrayList()); //objs);
            startup.load("startup.properties");
//
//            Runtime.getRuntime().gc();
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_totalMemory") + Runtime.getRuntime().totalMemory(), true);
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_FreeMemory") + "\t\t" + Runtime.getRuntime().freeMemory(), true);
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_MemoryUsed") + "\t\t" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()), true);
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_MemoryUsedbyWb") + "\t" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - inimem), true);
//
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_WbInicializing"), true);
        } catch (Throwable e)
        {
            log.error("Error loading SemanticWebBuilder Instance...", e);
        }

        loadSemanticModels();

        WebSite site = SWBContext.getAdminWebSite();
        if (site == null) {
            log.event("Creating Admin WebSite...");
            SWBPlatform.getSemanticMgr().createModel(SWBContext.WEBSITE_ADMIN, "http://www.semanticwb.org/SWBAdmin#");
        }

        site = SWBContext.getOntEditor();
        if (site == null) {
            log.event("Creating Ontology Editor WebSite...");
            SWBPlatform.getSemanticMgr().createModel(SWBContext.WEBSITE_ONTEDITOR, "http://www.semanticwb.org/SWBOntEdit#");
        }

        UserRepository urep = SWBContext.getAdminRepository();
        if (urep == null) {
            log.event("Creating Admin User Repository...");
            urep = SWBContext.createUserRepository(SWBContext.USERREPOSITORY_ADMIN, "http://www.semanticwb.org/uradm#");
            urep.setTitle("Admin User Repository");
            urep.setDescription("Admin User Repository");
            //TODO: cambiar a semantic prop
//            urep.setProperty(UserRepository.SWBUR_AuthMethod, "FORM"); //BASIC
//            urep.setProperty(UserRepository.SWBUR_LoginContext, "swb4TripleStoreModule");
//            urep.setProperty(UserRepository.SWBUR_CallBackHandlerClassName, "org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
            //MAPS74 - Cambiado a semantic prop
            urep.setAuthMethod("FORM");
            urep.setLoginContext("swb4TripleStoreModule");
            urep.setCallBackHandlerClassName("org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");

            urep.setUndeleteable(true);
            //site.setUserRepository(urep);
            //Create User
            User user = urep.createUser();
            user.setLogin("admin");
            user.setPassword("webbuilder");
            user.setEmail("admin@semanticwb.org");
            user.setFirstName("Admin");
            user.setLanguage("es");
            user.setActive(true);

            UserGroup grp1 = urep.createUserGroup("admin");
            grp1.setTitle("Administrator", "en");
            grp1.setTitle("Administrador", "es");
            grp1.setUndeleteable(true);
            UserGroup grp2 = urep.createUserGroup("su");
            grp2.setTitle("Super User", "en");
            grp2.setTitle("Super Usuario", "es");
            grp2.setUndeleteable(true);
            grp2.setParent(grp1);
            user.addUserGroup(grp1);
        }

        //Check for GlobalWebSite
        site = SWBContext.getGlobalWebSite();
        if (site == null) {
            log.event("Creating Global WebSite...");
            site = SWBContext.createWebSite(SWBContext.WEBSITE_GLOBAL, "http://www.semanticwb.org/wsglobal#");
            site.setTitle("Global");
            site.setDescription("Global WebSite");
            site.setActive(true);
            site.setUndeleteable(true);
            //Crear lenguajes por defecto
            Language lang = site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang = site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
        //Create HomePage
//            WebPage home = site.createWebPage("home");
//            site.setHomePage(home);
//            home.setActive(true);
        //Create DNS
//            Dns dns = site.createDns("localhost");
//            dns.setTitle("localhost");
//            dns.setDescription("DNS por default", "es");
//            dns.setDescription("Default DNS", "en");
//            dns.setDefault(true);
//            dns.setWebPage(home);
        }

        urep = SWBContext.getDefaultRepository();
        if (urep == null) {
            log.event("Creating Default User Repository...");
            urep = SWBContext.createUserRepository(SWBContext.USERREPOSITORY_DEFAULT, "http://www.semanticwb.org/urswb#");
            urep.setTitle("Default UserRepository");
            urep.setDescription("Default UserRpository");
//            urep.setProperty(UserRepository.SWBUR_AuthMethod, "FORM"); //BASIC
//            urep.setProperty(UserRepository.SWBUR_LoginContext, "swb4TripleStoreModule");
//            urep.setProperty(UserRepository.SWBUR_CallBackHandlerClassName, "org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
            //MAPS74 - Cambiado a semantic prop
            urep.setAuthMethod("FORM");
            urep.setLoginContext("swb4TripleStoreModule");
            urep.setCallBackHandlerClassName("org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
            site.setUserRepository(urep);
        }


        //Crear tablas LOGS
        try {
            Connection con = SWBUtils.DB.getDefaultConnection();
            Statement st = con.createStatement();
            try {
                ResultSet rs = st.executeQuery("select count(*) from swb_admlog");
                if (rs.next()) {
                    int x = rs.getInt(1);
                }
                rs.close();
            } catch (SQLException ne) {
                //ne.printStackTrace();
                log.event("Creating Logs Tables...");
                GenericDB db = new GenericDB();
                String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_logs.xml");
                db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), null);
            }
            st.close();
            con.close();
        } catch (SQLException e) {
            log.error(e);
        }

        usrMgr = new SWBUserMgr();
        usrMgr.init();

        monitor = new SWBMonitor();
        monitor.init();

        resmgr = new SWBResourceMgr();
        resmgr.init();

        pflowMgr = new PFlowManager();
        pflowMgr.init();

        templatemgr = new SWBTemplateMgr();
        templatemgr.init();

        servicemgr = new SWBServiceMgr();
        servicemgr.init();

        admlog = new SWBDBAdmLog();
        admlog.init();

        msgcenter = new SWBMessageCenter();
        msgcenter.init();

        acclog = new SWBAccessLog();
        acclog.init();

        accInc = new SWBAccessIncrement();
        accInc.init();

        indmgr = new SWBIndexMgr();
        indmgr.init();

        //Inicializa el RuleMgr
        Rule.getRuleMgr();

        try {
            log.debug("Loading admin Files from: /WEB-INF/lib/SWBAdmin.jar");
            String zipPath = SWBUtils.getApplicationPath() + "/WEB-INF/lib/SWBAdmin.jar";
            ZipFile zf = new ZipFile(zipPath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();
                log.debug("/" + ze.getName() + ", " + ze.getSize() + ", " + ze.getTime());
                admFiles.put("/" + ze.getName(), new JarFile(ze, zipPath));
            }
            zf.close();
        //log.event("-->Admin Files in Memory:\t" + admFiles.size());
        } catch (Exception e) {
            log.warn("Error loading files for Webbuilder Administration:" + SWBUtils.getApplicationPath() + "/WEB-INF/lib/SWBAdmin.jar", e);
        }

        try {
            log.debug("Loading admin Files from: /WEB-INF/lib/dojo.jar");
            String zipPath = SWBUtils.getApplicationPath() + "/WEB-INF/lib/dojo.jar";
            ZipFile zf = new ZipFile(zipPath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) e.nextElement();
                log.debug("/" + ze.getName() + ", " + ze.getSize() + ", " + ze.getTime());
                admFiles.put("/" + ze.getName(), new JarFile(ze, zipPath));
            }
            zf.close();
            log.event("-->Admin Files in Memory:\t" + admFiles.size());
        } catch (Exception e) {
            log.warn("Error loading files for Webbuilder Administration:" + SWBUtils.getApplicationPath() + "/WEB-INF/lib/dojo.zip", e);
        }
    }

//    private void createAdminSite()
//    {
//        WebSite site = SWBContext.getAdminWebSite();
//        if (site == null)
//        {
//            log.event("Creating Admin WebSite...");
//            site = SWBContext.createWebSite(SWBContext.WEBSITE_ADMIN, "http://www.semanticwb.org/SWBAdmin#");
//            site.setTitle("Admin");
//            site.setDescription("Admin WebSite");
//            site.setActive(true);
//            //Crear lenguajes por defecto
//            Language lang = site.createLanguage("es");
//            lang.setTitle("Español", "es");
//            lang.setTitle("Spanish", "en");
//            lang = site.createLanguage("en");
//            lang.setTitle("Ingles", "es");
//            lang.setTitle("English", "en");
//            //Create HomePage
//            WebPage home = site.createWebPage("home");
//            site.setHomePage(home);
//            home.setActive(true);
//            //Create DNS
//            Dns dns = site.createDns();
//            dns.setDns("localhost");
//            //dns.setDescription("DNS por default", "es");
//            //dns.setDescription("Default DNS", "en");
//            dns.setDefault(true);
//            dns.setWebPage(home);
//        }
//    }


    public void loadSemanticModels()
    {
        log.event("Loading SemanticModels...");

        String owlPath=getEnv("swb/ontologyFiles","/WEB-INF/owl/swb.owl");

        SWBPlatform.getSemanticMgr().initializeDB();

        //Load Ontology from file
        StringTokenizer st=new StringTokenizer(owlPath,",;");
        while(st.hasMoreTokens())
        {
            String file=st.nextToken();
            String swbowl="file:"+SWBUtils.getApplicationPath()+file;
            SemanticModel model=SWBPlatform.getSemanticMgr().addBaseOntology(swbowl);
            log.debug("Loading Model:"+model.getName());
        }

        SWBPlatform.getSemanticMgr().loadBaseVocabulary();


//        ontoModel.loadImports();
//        ontoModel.getDocumentManager().addAltEntry(source,"file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/swb.owl" );
//        ontoModel.read(source);

//        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
//        reasoner = reasoner.bindSchema(swbSquema);
//        InfModel infmodel = ModelFactory.createInfModel(reasoner, adminModel);
        //adminModel.createResource("http://www.sep.gob.mx/site/WBAdmin",ontoModel.getResource("http://www.semanticwebbuilder.org/swb4/ontology#WebSite"));
        //adminModel.createResource(ontoModel.getResource("http://www.semanticwebbuilder.org/swb4/ontology#WebPage"));

        //debugClasses(ontoModel);
        //debugModel(ontoModel);

        //load SWBSystem Model
//        log.debug("Loading DBModel:"+"SWBSystem");
//        m_system=new SemanticModel("SWBSystem",loadRDFDBModel("SWBSystem"));
////        debugModel(m_system);
//        if(SWBPlatform.isUseDB())m_ontology.addSubModel(m_system,false);

        SWBPlatform.getSemanticMgr().loadDBModels();

        if(getEnv("swb/addModel_DBPedia","false").equals("true"))
        {
            SWBPlatform.getSemanticMgr().loadRemoteModel("DBPedia", "http://dbpedia.org/sparql", "http://dbpedia.org/resource/",false);
        }
//        if(SWBPlatform.getEnv("swb/addOnt_DBPedia","false").equals("true"))
//        {
//            loadRemoteModel("Books", "http://www.sparql.org/books", "http://example.org/book/",false);
//        }

        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }



    /*
     * Regresa ContextPath
     * Ejemplo:
     * "/swb" o "/"
     */
    /**
     *
     * @return
     */
    public static String getContextPath() {
        if(instance!=null)
        {
            return instance.contextPath;
        }
        return "";
    }

    /*
     * Asignar ContextPath
     * Ejemplo: "/swb" o "/"
     * @param contextPath String
     */
    /**
     *
     * @param contextPath
     */
    public void setContextPath(String contextPath)
    {
        log.event("ContextPath:"+contextPath);
        this.contextPath = contextPath;

        try {
            if (contextPath.endsWith("/")) {
                webWorkPath = contextPath +getEnv("swb/webWorkPath").substring(1);
            } else {
                webWorkPath = contextPath + getEnv("swb/webWorkPath");
            }
        } catch (Exception e) {
            log.error("Can't read the context path...", e);
            webWorkPath = "";
        }
        SWBPlatform.createInstance().setContextPath(contextPath);
    }

    /**
     * Regresa ruta del directorio de trabajo
     * Ejemplo: /opt/swb/work
     * @return String con la ruta del directorio de trabajo
     */
    public static String getWorkPath()
    {
        return workPath;
    }

    /**
     * Regresa ruta web del directorio work
     * Ejemplo: /[context]/work
     * @return String con la ruta web del directorio work
     */
    public static String getWebWorkPath()
    {
        return webWorkPath;
    }

    /** Obtiene valor de variable de ambiente declarada en web.xml o web.properties.
     * @param name String nombre de la variable
     * @return
     */
    public static String getEnv(String name)
    {
        return getEnv(name, null);
    }

    /** Obtiene valor de variable de ambiente declarada en web.xml o web.properties.
     * @param name String nombre de la variable
     * @param defect String valor por defecto
     * @return
     */
    public static String getEnv(String name, String defect)
    {
        String obj = null;

        obj=System.getProperty("swb.web."+name);
        if(obj!=null)return obj;
        if(props!=null)
        {
            obj = props.getProperty(name);
        }
        if (obj == null) return defect;
        return obj;
    }

    public static ServletContext getServletContext()
    {
        return servletContext;
    }

    public static Filter getVirtualHostFilter()
    {
        return virtualHostFilter;
    }

    public static Properties getWebProperties()
    {
        return props;
    }

    /**
     * Getter for property haveDB.
     * @return Value of property haveDB.
     */
    public static boolean haveDB()
    {
        return haveDB;
    }

    /**
     * Getter for property haveDBTables.
     * @return Value of property haveDBTables.
     */
    public static boolean haveDBTables()
    {
        return haveDBTables;
    }

    public static boolean isUseDB()
    {
        return useDB;
    }

    public static void setUseDB(boolean useDB)
    {
        SWBPortal.useDB = useDB;
    }

    public static void removeFileFromWorkPath(String path)
    {
        //TOTO:Impementar Replicacion de archivos
        File file=new File(getWorkPath() + path);
        file.delete();
    }

    /**
     * @param path
     * @throws AFException
     * @return  */
    public static InputStream getFileFromWorkPath(String path) throws SWBException {
        InputStream ret = null;
        //TOTO:Impementar Replicacion de archivos
        try {
//            String confCS = (String) getEnv("swb/clientServer");
//
//            if(confCS.equalsIgnoreCase("ClientFR")) {
//                try {
//                    ret = new FileInputStream(getWorkPath() + path);
//                }catch(FileNotFoundException fnfe) {
//                    //ret=getFileFromAdminWorkPath(path);
//                    DownloadDirectory downdir=new DownloadDirectory(AFUtils.getEnv("wb/serverURL"),getWorkPath(),"workpath");
//                    downdir.download(path);
//                    ret = new FileInputStream(getWorkPath() + path);
//                }
//            }else if(confCS.equalsIgnoreCase("Client")) {
//                ret=getFileFromAdminWorkPath(path);
//            } else {
                ret = new FileInputStream(getWorkPath() + path);
//            }
        } catch (Exception e) {
            throw new SWBException(e.getMessage(), e);
        }
        return ret;
    }

      //TOTO:Impementar Replicacion de archivos
//    public InputStream getFileFromAdminWorkPath(String path) throws MalformedURLException, IOException {
//        InputStream ret = null;
//        //String str = getRemoteWorkPath() + path;
//        String servlet=DownloadDirectory.SERVDWN;
//        String str = getEnv("swb/serverURL")+servlet+ "?workpath=" + path;
//
//        str = str.substring(0, str.lastIndexOf("/") + 1) + com.infotec.appfw.util.URLEncoder.encode(str.substring(str.lastIndexOf("/") + 1));
//        ret = new java.net.URL(str).openStream();
//        return ret;
//    }

    public static void writeFileToWorkPath(String path, InputStream in, String userid) throws SWBException {
        //System.out.println("writeFileToWorkPath:"+path);
        //TOTO:Impementar Replicacion de archivos
        try {
//            String confCS = (String) AFUtils.getInstance().getEnv("wb/clientServer");
//
//            //System.out.println("clientServer:"+confCS);
//            if(confCS.equalsIgnoreCase("ClientFR")||confCS.equalsIgnoreCase("Client")) {
//                String str = AFUtils.getEnv("wb/serverURL")+DownloadDirectory.SERVUP;
//                URL url=new URL(str);
//                //System.out.println("url:"+url);
//                URLConnection urlconn=url.openConnection();
//                //if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
//                urlconn.setRequestProperty("type","workpath");
//                urlconn.setRequestProperty("path",path);
//                urlconn.setRequestProperty("user",userid);
//                urlconn.setDoOutput(true);
//                AFUtils.copyStream(in,urlconn.getOutputStream());
//                //System.out.println("copyStream");
//                String ret=AFUtils.getInstance().readInputStream(urlconn.getInputStream());
//                //System.out.println("ret:"+ret);
//            }else {
                File file=new File(getWorkPath() + path);
                file.getParentFile().mkdirs();
                FileOutputStream out=new FileOutputStream(file);
                SWBUtils.IO.copyStream(in,out);
//            }
        } catch (Exception e) {
            throw new SWBException(e.getMessage(), e);
        }
    }

    /**
     * @param path
     * @throws AFException
     * @return  */
    public static String readFileFromWorkPath(String path) throws IOException, SWBException {
        return SWBUtils.IO.readInputStream(getFileFromWorkPath(path));
    }

    /**
     * @param path
     * @param encode
     * @throws AFException
     * @return  */
    public static InputStreamReader getFileFromWorkPath(String path, String encode) throws SWBException {
        InputStreamReader ret = null;
        try {
            ret = new InputStreamReader(getFileFromWorkPath(path), encode);
        } catch (Exception e) {
            throw new SWBException(e.getMessage(),e);
        }
        return ret;
    }

    /**
     * @param path
     * @param encode
     * @throws AFException
     * @return  */
    public static String readFileFromWorkPath(String path, String encode) throws SWBException {
        StringBuffer ret = new StringBuffer(SWBUtils.IO.getBufferSize());
        try {
            InputStreamReader file = getFileFromWorkPath(path, encode);
            char[] bfile = new char[SWBUtils.IO.getBufferSize()];
            int x;
            while ((x = file.read(bfile, 0, SWBUtils.IO.getBufferSize())) > -1) {
                ret.append(bfile, 0, x);
            }
            file.close();
        } catch (Exception e) {
            throw new SWBException(e.getMessage(), e);
        }
        return ret.toString();
    }

//    /** Getter for property remoteWorkPath.
//     * @return Value of property remoteWorkPath.
//     */
//    public String getRemoteWorkPath() {
//        return remoteWorkPath;
//    }

    //TODO:validar client
    /** La instancia de WB esta configurada como cliente?.
     * @return Value of property client.
     */
    public static boolean isClient()
    {
        return client;
    }

    public static String getDistributorPath() {
        return getContextPath() + "/" + getEnv("swb/distributor", "swb");
    }

    public static SWBUserMgr getUserMgr() {
        return usrMgr;
    }

    public static SWBMonitor getMonitor() {
        return monitor;
    }

    public static SWBResourceMgr getResourceMgr() {
        return resmgr;
    }

    public static SWBTemplateMgr getTemplateMgr() {
        return templatemgr;
    }

    public static SWBServiceMgr getServiceMgr() {
        return servicemgr;
    }

    public static SWBDBAdmLog getDBAdmLog() {
        return admlog;
    }

    public static SWBMessageCenter getMessageCenter() {
        return msgcenter;
    }

    public static SWBAccessLog getAccessLog() {
        return acclog;
    }

    public static PFlowManager getPFlowManager() {
        return pflowMgr;
    }

    public static SWBAccessIncrement getAccessIncrement() {
        return accInc;
    }

    public static SWBIndexMgr getIndexMgr() {
        return indmgr;
    }

    public static JarFile getAdminFile(String path) {
        JarFile f = new JarFile(path);
        if (!f.exists()) {
            JarFile aux = (JarFile) admFiles.get(path);
            if (aux != null) {
                f = aux;
            }
        }
//        JarFile f = (JarFile) admFiles.get(path);
//        if (f == null) {
//            f = new JarFile(path);
//        }
        return f;
    }

    public static InputStream getAdminFileStream(String path) {
        JarFile f = (JarFile) admFiles.get(path);
        if (f == null) {
            f = new JarFile(path);
        }
        if (!f.exists()) {
            return null;
        }
        return f.getInputStream();
    }

    /**
     * @return an ArrayList with all languages available in all sites
     */
    public static ArrayList getAppLanguages() {
        ArrayList languages = new ArrayList();
        Iterator<WebSite> it = SWBContext.listWebSites();
        while (it.hasNext()) {
            WebSite site = it.next();
            Iterator<Language> itLang = site.listLanguages();
            while (itLang.hasNext()) {
                Language lang = itLang.next();
                if (!languages.contains(lang.getId())) {
                    languages.add(lang.getId());
                }
            }
        }
        return languages;
    }


    public static class UTIL {

        public static String parseHTML(String datos, String ruta) {
            return parseHTML(datos, ruta, 0);
        }

        /**
         * replace tags and values in Html (parser)
         * @param datos
         * @param ruta
         * @Autor Jorge Jiménez
         * @return  */
        public static String parseHTML(String datos, String ruta, int pages) {
            hAnchors = new HashMap();
            //detección de si el contenido es de word
            boolean iswordcontent = false;
            int posword = -1;
            posword = datos.toLowerCase().indexOf("name=generator content=\"microsoft word");
            if (posword > -1) {
                iswordcontent = true;
            }
            //termina detección de si es contenido de word

            HtmlTag tag = new HtmlTag();
            int pos = -1;
            int pos1 = -1;
            StringBuffer ret = new StringBuffer();
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                    int ttype = tok.getTokenType();
                    //if (ttype==HtmlStreamTokenizer.TT_COMMENT) continue;
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->")) {
                            continue;
                        }
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        if ((tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("tr") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("form") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("meta") || tag.getTagString().toLowerCase().equals("bca") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed") || tag.getTagString().toLowerCase().equals("iframe") || tag.getTagString().toLowerCase().equals("frame")) && !tok.getRawString().startsWith("<!--")) {
                            if (!tag.isEndTag()) {
                                ret.append("<");
                                ret.append(tag.getTagString());
                                ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements()) {
                                    boolean bwritestyle = true;
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);

                                    //revisar si no tiene repercuciones
                                    value = value.replace('\\', '/');
                                    value = SWBUtils.TEXT.replaceAll(value, "%3f", "?");
                                    value = SWBUtils.TEXT.replaceAll(value, "%3F", "?");

                                    String sruta = null;
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("https://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("../") && !value.startsWith("{")) { //Comentado Jorge Jiménez y Vic Lorenzana (30/07/2009)
                                        if (!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".") > -1) {
                                            sruta = ruta;
                                        }
                                        //poner solo archivo
                                        if (((pos = value.indexOf("/")) > -1) || (pos = value.indexOf("\\")) > -1) {
                                            value = findFileName(value);
                                        }
                                    } else if (name.toLowerCase().equals("href") && value.startsWith("../")) {
                                        sruta = ruta; //Agregado 29/07/2009 (Jorge Jiménez-Vic Lorenzana)
                                        value = findFileName(value); 
                                    } else if (name.toLowerCase().equals("href") && value.startsWith("#_") && pages > 1) { //Es un ancla
                                        int page = findAnchorInContent(datos, value, pages);
                                        if (page > 0) {
                                            value = "?page=" + page + "&" + value;
                                        }
                                    } else if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick")) {
                                        String out = findImagesInScript(value, ".gif'", ruta);
                                        out = findImagesInScript(out, ".jpg'", ruta);
                                        //ret.append(ruta);
                                        if (!out.equals("")) {
                                            value = out;
                                        }
                                    } else if (tag.getTagString().toLowerCase().equals("body") && iswordcontent && (name.equals("link") || name.equals("vlink"))) { //elimina los liks
                                        bwritestyle = false;
                                    }
                                    if (bwritestyle) {
                                        ret.append(name);
                                        ret.append("=\"");
                                        if (sruta != null) {
                                            ret.append(sruta);
                                        }
                                        ret.append(value);
                                        ret.append("\" ");
                                    }
                                }
                                if (tag.isEmpty()) {
                                    ret.append("/");
                                }
                                ret.append(">");
                                if (tag.getTagString().toLowerCase().equals("form")) {
                                    ret.append(actionval);
                                }
                            } else {
                                ret.append(tok.getRawString());
                            }
                        } else {
                            ret.append(tok.getRawString());
                        }
                    } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                        ret.append(tok.getRawString());
                    }
                }
            } catch (NumberFormatException f) {
                log.error(f);
            } catch (Exception e) {
                log.error(e);
            }
            return ret.toString();
        }

        /**
         * Finds filename in string (relative paths)
         * @param value
         * @return
         */
        private static String findFileName(String value) {
            String out = "";
            if (value.startsWith("../")) {
                out = takeOffString(value, "../");
                if (!out.equals("")) {
                    value = out;
                }
            }
            int x = value.lastIndexOf(".");
            if (x > -1) {
                int y = value.lastIndexOf("\\", x);
                if (y > -1) {
                    value = value.substring(y + 1);
                }
                y = value.lastIndexOf("/", x);
                if (y > -1) {
                    value = value.substring(y + 1);
                }
            }
            return value;
        }

        public static String takeOffString(String value, String takeOff) {
            int pos = -1;
            do {
                pos = value.indexOf(takeOff);
                if (pos != -1) {
                    value = value.substring(pos + takeOff.length());
                }
            } while (pos != -1);
            return value;
        }

        /**
         * Finds anchors in string
         * @param content
         * @param ancla
         * @param pages
         * @return
         */
        private static int findAnchorInContent(String content, String ancla, int pages) {
            ancla = ancla.substring(1);
            Integer page = (Integer) hAnchors.get(ancla);
            if (page != null) { //existe en hash de anclas
                return page.intValue();
            } else {
                for (int i = 0; i <= pages; i++) {
                    if (findAnchorInContentPage(content, ancla, i, pages)) {
                        return i;
                    }
                }
            }
            return 0;
        }

        /**
         * @param datos
         * @param ruta
         */
        public static boolean findAnchorInContentPage(String datos, String ancla, int page, int itpages) {
            HtmlTag tag = new HtmlTag();
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new java.io.ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        tok.parseTag(tok.getStringValue(), tag);
                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        if (tag.getTagString().toLowerCase().equals("div")) {
                            flag1 = true;
                            if (!tag.isEndTag()) {
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                while (en.hasMoreElements()) {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    if (name.toLowerCase().equals("class")) {
                                        if (value.toLowerCase().equals("section" + page)) {
                                            flag = true;
                                        }
                                    } else if (flag) {
                                        flag2 = true;
                                    }
                                }
                            } else {
                                if (flag && !flag2) {
                                    //entra a este if y se rompe el ciclo solo si la página actual es menos al total de páginas encontradas en el documento,
                                    //si es igual, entonces no lo rompe y se termina hasta que se acaba el html, para que funcionen los pie de página si existen
                                    //al final del dicumento
                                    if (page < itpages) {
                                        break;
                                    }
                                } else if (flag && flag2) {
                                    flag2 = false;
                                }
                            }
                        } else if (flag1 && flag) {
                            if (tag.getTagString().toLowerCase().equals("a")) {
                                if (!tag.isEndTag()) {
                                    Enumeration en = tag.getParamNames();
                                    String name = "";
                                    String value = "";
                                    String actionval = "";
                                    while (en.hasMoreElements()) {
                                        name = (String) en.nextElement();
                                        value = tag.getParam(name);
                                        if (name.toLowerCase().equals("name") && value.equals(ancla)) { //emcontrado
                                            hAnchors.put(value, new Integer(page));
                                            return true;
                                        } else if (name.toLowerCase().equals("name") && value.startsWith("_")) { //es una ancla, guardarla en hash de anclas
                                            if (hAnchors.get(value) == null) {
                                                hAnchors.put(value, new Integer(page));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
            return false;
        }

        /**
         * Finds images in a javascript
         * @param value
         * @param ext
         * @param ruta
         * @return
         */
        private static String findImagesInScript(String value, String ext, String ruta) {
            StringBuffer aux = new StringBuffer(value.length());
            int off = 0;
            int f = 0;
            int i = 0;
            int j = 0;
            do {
                f = value.indexOf(ext, off);
                i = value.lastIndexOf("'", f);
                j = value.lastIndexOf("/", f);
                if (f > 0 && i >= 0) {
                    i++;
                    if (value.startsWith("/", i) || value.startsWith("http://", i)) {
                        aux.append(value.substring(off, f + ext.length()));
                    } else if (j > -1) {
                        aux.append(value.substring(off, i) + ruta + value.substring(j + 1, f + ext.length()));
                    } else {
                        aux.append(value.substring(off, i) + ruta + value.substring(i, f + ext.length()));
                    }
                    off = f + ext.length();
                }
            } while (f > 0 && i > 0);
            aux.append(value.substring(off));
            return aux.toString();
        }

        /**
         * @param datos
         * @param ruta
         * @return  */
        public static String parseXsl(String datos, String ruta) {
            HtmlTag tag = new HtmlTag();
            int pos = -1;
            int pos1 = -1;
            StringBuffer ret = new StringBuffer();
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
                    int ttype = tok.getTokenType();
                    //if (ttype==HtmlStreamTokenizer.TT_COMMENT) continue;
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->")) {
                            continue;
                        }
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        //if (tag.getTagString().toLowerCase().startsWith("o:")){System.out.println("o:Salto");   continue;}
                        if (tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("form") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("meta") || tag.getTagString().toLowerCase().equals("bca") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed") || tag.getTagString().toLowerCase().equals("iframe") || tag.getTagString().toLowerCase().equals("frame")) {

                            if (!tag.isEndTag()) {
                                ret.append("<");
                                ret.append(tag.getTagString());
                                ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements()) {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    ret.append(name);
                                    ret.append("=\"");
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("{")) {
                                        if (!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".") > -1) {
                                            ret.append(ruta);
                                        }
                                        //poner solo archivo
                                        if (((pos = value.indexOf("/")) > -1) || (pos = value.indexOf("\\")) > -1) {
                                            value = findFileName(value);
                                        }
                                    }
                                    if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick")) {
                                        String out = findImagesInScript(value, ".gif'", ruta);
                                        out = findImagesInScript(out, ".jpg'", ruta);
                                        //ret.append(ruta);
                                        if (!out.equals("")) {
                                            value = out;
                                        }
                                    }
                                    ret.append(value);
                                    ret.append("\" ");
                                }
                                //if(tag.getTagString().toLowerCase().equals("img") && tok.getStringValue().toString().endsWith("/")) {
                                if (tag.isEmpty()) {
                                    ret.append("/");
                                }

                                ret.append(">");

                                if (tag.getTagString().toLowerCase().equals("form")) {
                                    ret.append(actionval);
                                }
                            } else {
                                ret.append(tok.getRawString());
                            }
                        } else {
                            ret.append(tok.getRawString());
                        }
                    } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                        ret.append(tok.getRawString());
                    }
                }
            } catch (NumberFormatException f) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_decodifica"), f);
            } catch (Exception e) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_IOHTML"), e);
            }
            return ret.toString();
        }

        /**
         * Finds attaches in a string
         * @param datos
         * @param ruta
         * @return a string with sttaches
         */
        public static String FindAttaches(String datos) {
            HtmlTag tag = new HtmlTag();
            StringBuffer ret = new StringBuffer();
            Vector vvector = new Vector();
            try {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {

                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT) {
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if")) {
                            continue;
                        }
                        //if (tag.getTagString().toLowerCase().startsWith("o:")){System.out.println("o:Salto");   continue;}
                        if (tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed")) {
                            if (!tag.isEndTag()) {
                                //ret.append("<");
                                //ret.append(tag.getTagString());
                                //ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                while (en.hasMoreElements()) {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    String out = null;
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("{")) {
                                        String stype = "";
                                        if (tag.getTagString().toLowerCase().equals("input")) {
                                            stype = tag.getParam("type").toLowerCase();
                                        }
                                        if (!value.startsWith("http://") && !value.startsWith("https://") && (!tag.getTagString().toLowerCase().equals("input") || (tag.getTagString().toLowerCase().equals("input") && stype.equals("image")))) {
                                            if (value.toLowerCase().endsWith(".gif") || value.toLowerCase().endsWith(".jpg") || value.toLowerCase().endsWith(".jpeg") || value.toLowerCase().endsWith(".bmp") ||
                                                    value.toLowerCase().endsWith(".doc") || value.toLowerCase().endsWith(".htm") || value.toLowerCase().endsWith(".html") || value.toLowerCase().endsWith(".zip") ||
                                                    value.toLowerCase().endsWith(".txt") || value.toLowerCase().endsWith(".pdf") || value.toLowerCase().endsWith(".xls") || value.toLowerCase().endsWith(".ppt") ||
                                                    value.toLowerCase().endsWith(".xsl") || value.toLowerCase().endsWith(".xslt") || value.toLowerCase().endsWith(".bin") || value.toLowerCase().endsWith(".tar") || value.toLowerCase().endsWith(".css")) {
                                                out = value;
                                            }
                                        }
                                    } else if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && value.startsWith("{")) {
                                        int pos = -1;
                                        pos = value.indexOf("}");
                                        if (pos != -1) {
                                            out = value.substring(pos + 1);
                                        }
                                    } else if (name.toLowerCase().equals("href") && value.startsWith("/")) {
                                        out = value;
                                    } else if (name.toLowerCase().equals("onmouseover")) {
                                        //if(!value.startsWith("http://") && !value.startsWith("https://"))
                                        int pos = -1, pos1 = -1;
                                        pos = value.indexOf("http://");
                                        pos1 = value.indexOf("https://");
                                        if (pos < 0 && pos1 < 0) {
                                            out = findImageInScript1(value, ".gif'", "");
                                            out = findImageInScript1(out, ".jpg'", "");
                                        }
                                    }
                                    if (out != null) {
                                        boolean flag = false;
                                        for (int i = 0; i < vvector.size(); i++) {
                                            if (out.equals((String) vvector.elementAt(i))) {
                                                flag = true;
                                            }
                                        }
                                        if (!flag) {
                                            vvector.addElement(out);
                                        }
                                    }

                                //ret.append("\" ");
                                }
                            //ret.append(">");
                            //if(tag.getTagString().toLowerCase().equals("form")) ret.append(actionval);
                            } else {
                                //ret.append(tok.getRawString());
                            }
                        } else {
                            //ret.append(tok.getRawString());
                        }
                    } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                        //ret.append(tok.getRawString());
                    }
                }
                for (int i = 0; i < vvector.size(); i++) {
                    ret.append((String) vvector.elementAt(i) + ";");
                }
            } catch (NumberFormatException f) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_decodifica"), f);
            } catch (Exception e) {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_IOHTML"), e);
            }
            return ret.toString();
        }

        private static String findImageInScript1(String value, String ext, String ruta) {
            int f = value.indexOf(ext);
            int i = value.lastIndexOf("'", f);
            int j = value.lastIndexOf("'");
            if (f > 0 && i >= 0) {
                i++;
                if (value.startsWith("/", i) || value.startsWith("http://", i)) {
                    return value;
                } else {
                    return value.substring(i, j);
                }
            }
            return value;
        }
        private final static String ALPHABETH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        private String getRandString(int size) {
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                sb.append(ALPHABETH.charAt((int) (Math.random() * ALPHABETH.length())));
            }
            return sb.toString();
        }

        public void sendValidateImage(HttpServletRequest request, HttpServletResponse response, int size, String cad) throws ServletException, IOException {
            String cadena = null;
            if (null == cad) {
                cadena = getRandString(size);
            } else {
                cadena = cad;
            }
            request.getSession(true).setAttribute("swb_valCad", cadena);
            BufferedImage buffer = new BufferedImage(150, 40, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffer.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setBackground(new Color(255, 255, 255));
            g.clearRect(0, 0, 150, 40);
            g.setColor(new Color(0, 0, 0));
            Font f = new Font("Serif", Font.BOLD, 25);
            //g.setFont(g.getFont().deriveFont(Font.ROMAN_BASELINE, 25.0f));
            g.setFont(f);
            g.drawString(cadena, 15, 30);

            for (int i = 0; i <= 150; i += 10) {
                g.drawLine(i, 0, i, 39);
            }
            for (int i = 0; i <= 40; i += 10) {
                g.drawLine(0, i, 149, i);
            }

            /*try {
            org.semanticwb.base.util.GIFEncoder encoder = new org.semanticwb.base.util.GIFEncoder(buffer);
            response.setContentType("image/gif");
            encoder.Write(response.getOutputStream());
            } catch (AWTException e) {
            log.error(e);
            }*/

            try {
                response.setContentType("image/png");
                javax.imageio.ImageIO.write(buffer, "png", response.getOutputStream());
            } catch (IOException e) {
                log.error(e);
            }
        }

        public void sendValidateImage(HttpServletRequest request, HttpServletResponse response, String attributeName, int size, String cad) throws ServletException, IOException {
            final int width = 150;
            final int height = 60;

            String cadena = null;
            if (null == cad) {
                cadena = getRandString(size);
            } else {
                cadena = cad;
            }
            if (null == attributeName) {
                attributeName = "swb_valCad";
            }
            request.getSession(true).setAttribute(attributeName, cadena);
            BufferedImage buffer = new BufferedImage(150, 40, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffer.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setBackground(new Color(255, 255, 255));
            g.clearRect(0, 0, width, height);
            g.setColor(new Color(0, 0, 0));
            Font f = new Font("Serif", Font.BOLD, 25);
            g.setFont(f);
            g.drawString(cadena, 15, 30);

            for (int i = 0; i <= width; i += 17) {
                g.drawLine(i, 0, i, height);
            }
            for (int i = 0; i <= height; i += 19) {
                g.drawLine(0, i, width-1, i);
            }

            try {
                response.setContentType("image/png");
                javax.imageio.ImageIO.write(buffer, "png", response.getOutputStream());
            } catch (IOException e) {
                log.error(e);
            }
        }

        public static String getGalleryScript(String... imgurl) {
            ImageGallery ig = new ImageGallery();
            return ig.getGalleryScript(imgurl);
        }
    }

    /**
     * Remueve estilos del contenido, como parte del cambio de los mismos de acuerdo a
     * archivo de configuración Content.properties en el servidor
     */
    public static String removeStylesOutDivs(String content, String tmid, HashMap hTMhStyleObjs)
    {
        StringBuffer ret = new StringBuffer();
        try {
            boolean title=false;
            boolean style=false;
            HtmlTag tag = new HtmlTag();
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(SWBUtils.IO.getStreamFromString(content));
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
            {
                int ttype = tok.getTokenType();
                if(ttype == HtmlStreamTokenizer.TT_COMMENT || ttype == HtmlStreamTokenizer.TT_TEXT)
                {
                    if(!title)
                    {
                        if(style)
                        {
                            ret.append(parseStyles2(tok.getRawString(),tmid, hTMhStyleObjs));
                        }else
                        {
                            ret.append(tok.getRawString());
                        }
                    }
                } else if (ttype == HtmlStreamTokenizer.TT_TAG)
                {
                    tok.parseTag(tok.getStringValue(), tag);

                    String tagname=tag.getTagString();
                    String tname=tag.getTagString().toLowerCase();


                    if(true)   //validar si se requiere eliminar tags de
                    {
                        if(tname.equals("title"))
                        {
                            if(tag.isEndTag())title=false;
                            else title=true;
                        }
                        if(tname.equals("html")
                            || tname.equals("title")
                            || tname.equals("head")
                            || tname.equals("meta")
                            || tname.equals("link")
                            || tname.startsWith("o:")
                            || tname.startsWith("st1:")
                            )
                            continue;

                        if(tname.equals("body"))
                        {
                            if(!tag.isEndTag())
                            {
                                tagname="DIV";
                                tag.removeParam("link");
                                tag.removeParam("vlink");
                                tag.removeParam("alink");
                            }
                            else
                            {
                                ret.append("</DIV>");
                                continue;
                            }
                        }
                    }

                    if(tag.isEndTag())
                    {
                        ret.append(tok.getRawString());
                        if(tname.equals("style"))
                        {
                            style=false;
                        }
                    }else
                    {
                        ret.append("<");
                        ret.append(tagname);

                        if(tname.equals("style"))
                        {
                            tag.setParam("type","text/css");
                            style=true;
                        }else if(tname.equals("img"))
                        {
                            if(!tag.hasParam("alt"))tag.setParam("alt","");
                            if(!tag.hasParam("longdesc"))tag.setParam("longdesc","#");
                            tag.removeParam("v:shapes");
                        }

                        Enumeration en = tag.getParamNames();
                        while (en.hasMoreElements())
                        {
                            String name = (String) en.nextElement();
                            String value = tag.getParam(name);
                            if(name.toLowerCase().equals("style"))
                            {
                                ret.append(" "+name+"='");
                                StringTokenizer st=new StringTokenizer(value,";");
                                while(st.hasMoreTokens())
                                {
                                    String token=st.nextToken();
                                    String aux=token.toLowerCase().trim();
                                    if(!(aux.startsWith("font:") || aux.startsWith("font-")))// || aux.startsWith("color")))
                                    {
                                        ret.append(token+";");
                                    }
                                }
                                ret.append("\'");
                            }else
                            {
                                ret.append(" "+name+"=\""+value+"\"");
                            }
                        }
                        ret.append(">");
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return ret.toString();
    }

    /**
     * Parsea el contenido, de acuerdo a los tipos indicados en los objetos de estilo
     */
    private static String parseStyles2(String styles, String tmid, HashMap hTMhStyleObjs)
    {
        styles=parseStyles(styles,tmid, hTMhStyleObjs);

        HashMap hStyleObjs=(HashMap)hTMhStyleObjs.get(tmid);
        if(hStyleObjs!=null)
        {
            ContentStyles contentStyle=(ContentStyles)hStyleObjs.get("MsoNormal");
            if(contentStyle!=null)
            {
                Iterator it=SWBUtils.TEXT.findInterStr(styles,"p.",", ");
                while(it.hasNext())
                {
                    String name=((String)it.next()).trim();
                    if(name.indexOf('\n')==-1)
                    {
                        if(!hStyleObjs.containsKey(name))
                        {
                            styles=findStyles(styles,name,contentStyle.getFont(),contentStyle.getSize(),contentStyle.getColor());
                        }
                    }
                }
            }
        }
        return styles;
    }

    /**
     * Parsea el contenido, de acuerdo a los tipos indicados en los objetos de estilo
     */
    private static String parseStyles(String content,String tmid, HashMap hTMhStyleObjs)
    {
        HashMap hStyleObjs=(HashMap)hTMhStyleObjs.get(tmid);
        if(hStyleObjs!=null && hStyleObjs.size()>0){ //encontro estilos
            Iterator iStyles=hStyleObjs.keySet().iterator();
            while(iStyles.hasNext()){
                String styleName=(String)iStyles.next();
                if(styleName!=null && styleName.trim().length()>0){
                    ContentStyles contentStyle=(ContentStyles)hStyleObjs.get(styleName);
                    if(contentStyle.getName()!=null && (contentStyle.getFont()!=null || contentStyle.getSize()!=null || contentStyle.getColor()!=null))
                    {
                        String sname=styleName;             //contentStyle.getName(); //cambio para soportar multiples nombres
                        if(!sname.equals("h1") && !sname.equals("h2") && !sname.equals("h3")) {
                            sname="p."+sname+", li."+sname+", div."+sname;
                        }
                        content=findStyles(content,sname,contentStyle.getFont(),contentStyle.getSize(),contentStyle.getColor());
                    }
                }
            }
        }
        return content;
    }

    /**
     * Busca los estilos en el documento y si los encuentra los remplaza de acuerdo a
     * archivo de configuración
     * @param content
     * @param word
     * @param font
     * @param size
     * @param color
     * @return
     */
    public static String findStyles(String content,String word,String font,String size,String color){
        int pos=-1;
        pos=content.indexOf(word);
        if(pos>-1){
            int pos1=-1;
            pos1=content.indexOf("{",pos);
            if(pos1>-1 && font!=null){
                int ifont=-1;
                ifont=content.indexOf("font-family:",pos1);
                int fintot=content.indexOf("}",pos1);
                if(ifont>-1 ){ //cambio de font
                    int fin=-1;
                    fin=content.indexOf(";",ifont);
                    if(fin>-1){
                        content=content.substring(0,ifont+12)+font+content.substring(fin);
                    }
                }
            }
            if(pos1>-1 && size!=null){
                int isize=-1;
                isize=content.indexOf("font-size:",pos1);
                int fintot=content.indexOf("}",pos1);
                if(isize>-1 && isize<fintot){ //cambio de size
                    int fin=-1;
                    fin=content.indexOf(";",isize);
                    if(fin>-1){
                        content=content.substring(0,isize+10)+size+content.substring(fin);
                    }
                }
            }
            if(pos1>-1 && color!=null){
                int icolor=-1;
                icolor=content.indexOf("color:",pos1);
                int fintot=content.indexOf("}",pos1);
                if(icolor>-1 && icolor<fintot){ //cambio de color
                    int fin=-1;
                    fin=content.indexOf(";",icolor);
                    if(fin>-1){
                        content=content.substring(0,icolor+6)+color+content.substring(fin);
                    }
                }
                else if(icolor==-1 || (icolor>-1 && icolor>fintot)){ //cambio de color
                    content=content.substring(0,fintot)+"color:"+color+";"+content.substring(fintot);
                }
            }
        }
        return content;
    }
}