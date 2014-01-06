/*
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
 */
package org.semanticwb;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
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
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBUtils.IO;
import org.semanticwb.css.parser.Attribute;
import org.semanticwb.css.parser.CSSParser;
import org.semanticwb.css.parser.Selector;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.PFlowManager;
import org.semanticwb.portal.SWBAdminFilterMgr;
import org.semanticwb.platform.SWBMessageCenter;
import org.semanticwb.portal.*;
import org.semanticwb.portal.access.SWBAccessIncrement;
import org.semanticwb.portal.access.SWBAccessLog;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.db.SWBDBAdmLog;
import org.semanticwb.portal.indexer.SWBIndexMgr;
import org.semanticwb.portal.resources.ImageGallery;
import org.semanticwb.portal.services.SWBCloud;
import org.semanticwb.portal.util.CaptchaUtil;
import org.semanticwb.portal.util.ContentStyles;
import org.semanticwb.repository.Workspace;
import org.semanticwb.util.JarFile;
import org.semanticwb.util.db.GenericDB;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * Executes initialization operations for the environment necesary to provide
 * Portal services, so users, templates, publication flows, resources, services,
 * indexes, and access and data bases can be managed.
 * <p>Realiza operaciones de inicializaci&oacute;n del ambiente necesario para proporcionar
 * servicios de Portal, a fin de poder administrar usuarios, plantillas, flujos de
 * publicaci&oacute;n, recursos, servicios, indizado, así como de bitácoras de acceso
 * y de base de datos.</p>
 * @author Javier Sol&iacute;s
 * @author Jorge Jim&eacute;nez
 * @version 1.0
 */
public class SWBPortal
{

    /**
     * Holds the names and values for the variables declared in {@literal web.properties} file.
     * <p>Almacena los nombres y valores de las variables declaradas en el archivo {@literal web.properties}.</p>
     */
    private static Properties props = null;

    /**
     * Holds the names and values for the variables declared in {@literal security.properties} file.
     * <p>Almacena los nombres y valores de las variables declaradas en el archivo {@literal security.properties}.</p>
     */
    private static Properties secProps = null;

    

    /**
     * Stores the context path's value configured for this portal instance.
     * <p>Almacena el valor de la ruta de contexto configurada para esta instancia de portal.</p>
     */
    private String contextPath = "/";
    
    /** Stores the work directory's web path value <p>Almacena el valor de la ruta web del directorio de trabajo</p>. */
    private static String webWorkPath = "";

    /** The WORKPAT h_ relative. */
    public static String WORKPATH_RELATIVE="relative";
    
    /** The WORKPAT h_ absolute. */
    public static String WORKPATH_ABSOLUTE="absolute";
    
    /** The WORKPAT h_ remote. */
    public static String WORKPATH_REMOTE="remote";

    /**
     * Stores the work directory's path.
     * <p>Almacena la ruta del directorio de trabajo.</p>
     */
    private static String workPath = "";

    /** Store the work path type Almcaena el tipo de ruta de trabajo. */
    private static String workPathType = WORKPATH_RELATIVE;

    /**
     * Holds a reference to the {@code ServletContext} used by this web application.
     * <p>Mantiene una referencia hacia el objeto {@code ServletContext} utilizado
     * por esta aplicaci&oacute;n.</p>
     */
    private static ServletContext servletContext = null;
    /**
     * Holds a reference to the {@code Filter} used by this web application.
     * <p>Mantiene una referencia hacia el objeto {@code Filter} utilizado 
     * por esta aplicaci&oacute;n.</p>
     */
    private static Filter virtualHostFilter = null;
    /**
     * Indicates whether this instance of portal has an existing database related to it.
     * <p>Indica si esta instancia de portal tiene relacionada una base de datos existente.</p>
     */
    private static boolean haveDB = false;
    /**
     * Indicates whether the database related to this portal instance has the
     * necessary tables to work with. <p>Indica si la base de datos relacionada
     * a esta instancia de portal tiene las tablas necesarias para trabajar.</p>
     */
    private static boolean haveDBTables = false;
    /**
     * Indicates whether or not to use a database.
     * <p>Indica si usar o no una base de datos.</p>
     */
    private static boolean useDB = true;
    /**
     * Indicates if this portal instance is client of another one.
     * <p>Indica si esta instancia de portal es o no cliente de otra.</p>
     */
    private static boolean client = false;
    /**
     * Indicates if this portal instance is stand alone.
     * <p>Indica si esta instancia de portal esta sola (no en balanceo).</p>
     */
    private static boolean standalone = true;
    /**
     * Creates the messages to the log files.
     * <p>Crea los mensajes hacia los archivos de bit&aacute;cora.</p>
     */
    private static Logger log = SWBUtils.getLogger(SWBPortal.class);
    //private static Logger log = null;//SWBUtils.getLogger(SWBPortal.class);
    /**
     * Holds the only one instance of this object in the application.
     * <p>Mantiene la &uacute;nica instancia de este objeto en la aplicaci&oacute;n</p>
     */
    private static SWBPortal instance = null;
    /**
     * Contains all the anchors found in the HTML code analized.
     * <p>Contiene todas las anclas encontradas en el c&oacute;digo HTML analizado.</p>
     */
    private static HashMap hAnchors = null;
    /**
     * Contains the names and content of all files within SWBAdmin.jar and dojo.jar files.
     * <p>Contiene los nombres y el contenido de todos los archivos dentro de los
     * archivos SWBAdmin.jar y dojo.jar.</p>
     */
    private static ConcurrentHashMap admFiles = null;
    /**
     * Is the manager for the user objects in this portal.
     * <p>Es el administrador de los objetos de usuarios en este portal.</p>
     */
    private static SWBUserMgr usrMgr;
    /**
     * Is the manager for the publication flows in this portal.
     * <p>Es el administrador de los flujos de publicaci&oacute;n en este portal.</p>
     */
    private static PFlowManager pflowMgr;
    /**
     * Is the monitor in this portal.
     * <p>Es el monitor en este portal.</p>
     */
    private static SWBMonitor monitor = null;
    /**
     * Is the manager for the resource objects in this portal.
     * <p>Es el administrador de los objetos de recursos en este portal.</p>
     */
    private static SWBResourceMgr resmgr = null;
    /**
     * Is the manager for the template objects in this portal.
     * <p>Es el administrador de los objetos de plantillas en este portal.</p>
     */
    private static SWBTemplateMgr templatemgr = null;
    /**
     * Is the manager for the service objects in this portal.
     * <p>Es el administrador de los objetos de servicios en este portal.</p>
     */
    private static SWBServiceMgr servicemgr = null;
    /**
     * Is the manager for the admin filters.
     * <p>Es el administrador de los filtros de administracion.</p>
     */
    private static SWBAdminFilterMgr adminfiltermgr = null;
    /**
     * Is the manager for the database log objects in this portal.
     * <p>Es el administrador de los objetos de bit&aacute;coras de base de datos en este portal.</p>
     */
    private static SWBDBAdmLog admlog = null;
        
    /**
     * Is the message center in this portal.
     * <p>Es el centro de mensajes en este portal.</p>
     */
    private static SWBMessageCenter msgcenter = null;
    
    /** The procesor. */
    private static SWBMessageProcesor procesor = null;    
    
    /**
     * Is the manager for the access logs in this portal.
     * <p>Es el administrador de las bit&aacute;coras de acceso en este portal.</p>
     */
    private static SWBAccessLog acclog = null;
    /**
     * Is the manager for the access increment object in this portal.
     * <p>Es el administrador del incremento de accesos a objetos en este portal.</p>
     */
    private static SWBAccessIncrement accInc = null;
    /**
     * Is the manager for the index objects in this portal.
     * <p>Es el administrador de los objetos de indizado en este portal.</p>
     */
    private static SWBIndexMgr indmgr = null;

    /**
     * GeoIp Service Interface
     */
    private static com.maxmind.geoip.LookupService geoip = null;
    
    /**
     * Link to AWS Services
     */
    private static SWBCloud cloudServices = null;

    /**
     * Creates an object of this class, calling method {@code createInstance(ServletContext, Filter) with.
     * 
     * @param servletContext the {@code ServletContex} agregated to the new instance
     * @return SWBPortal     the new instance of this component
     * {@code Filter}'s value equal to {@code null}.
     * <p>Crea un objeto de esta clase, llamando al m&eacute;todo {@code createInstance(ServletContext, Filter)
     * con el valor de {@code Filter} igual a {@code null}.</p>
     */
    static public synchronized SWBPortal createInstance(ServletContext servletContext)
    {
        //new Exception().printStackTrace();
        return createInstance(servletContext, null);
    }

    /**
     * Creates an object of this class with the {@code ServletContext} and {@code Filter} indicated.
     * <p>Crea un objeto de esta clase con el {@code ServletContext} y {@code Filter} indicados.</p>
     * @param servletContext the {@code ServletContex} agregated to the new instance
     * @param filter         the {@code Filter} assigned to the new instance's virtual host filter
     * @return SWBPortal     the new instance of this component
     */
    static public synchronized SWBPortal createInstance(ServletContext servletContext, Filter filter)
    {
        if (instance == null)
        {
            SWBPortal.virtualHostFilter = filter;
            instance = new SWBPortal();
            SWBPortal.servletContext = servletContext;
            String dbfile = SWBUtils.getApplicationPath()+"/swbadmin/geoip/GeoIP.dat";
            try {
                SWBPortal.geoip = new com.maxmind.geoip.LookupService(dbfile, com.maxmind.geoip.LookupService.GEOIP_MEMORY_CACHE);
                Runtime.getRuntime().addShutdownHook(new Thread(){@Override
                public void run(){SWBPortal.geoip.close();}});
            } catch (IOException ioe) {
                log.warn("can't find GeoIP.dat file", ioe);
                SWBPortal.geoip = null;
            }

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
    /**
 * Constructor that initializes the environment of its new instance by calling method {@code init()}
 * <p>Constructor que inicia el ambiente de su nueva instancia llamando al m&eacute;todo {@code init()}</p>.
 */
    private SWBPortal()
    {
        log.event("Initializing SemanticWebBuilder Portal...");
        init();
    }

    /**
     * Prepares the environment necessary to provide site and content management services.
     * This involves the following operations:<ul>
     * <li>configuring the values for workpath and SMTPServer environment variables,
     * and some other variables from SWBPlatform</li>
     * <li>verifing the existence of the database and its tables</li>
     * <li>loading of classes indicated in startup.properties file</li>
     * <li>executing method {@code loadSemanticModels}</li>
     * <li>creating the administration site, if it does not exist yet</li>
     * <li>creating the onthology editor site, if it does not exist yet</li>
     * <li>creating the administration user repository, if it does not exist yet</li>
     * <li>creating the global site, if it does not exist yet</li>
     * <li>creating the default user repository, if it does not exist yet</li>
     * <li>creating the log tables</li>
     * <li>initializing the objects: {@code SWBUserMgr}, {@code SWBMonitor}, {@code SWBResourceMgr},
     * {@code PFlowManager}, {@code SWBTemplateMgr}, {@code SWBServiceMgr}, {@code SWBDBAdmLog},
     * {@code SWBMessageCenter}, {@code SWBAccessLog}, {@code SWBAccessIncrement} and {@code SWBIndexMgr},
     * wich can be accesed directly from this class, and {@code RuleMgr}</li>
     * <li>loading of files in SWBAdmin.jar and dojo.jar</li>
     * </ul>
     * <p>Prepara el ambiente necesario para proveer servicios de administraci&oacute;n
     * de sitios y contenido. Esto involucra las siguientes operaciones: <ul>
     *
     * <li>configurar valores de variables de ambiente de SWBPortal y {@link SWBPlatform}
     * como: la ruta del directorio de trabajo o el dominio de servidor SMTP</li>
     * <li>verificar la existencia de una base de datos y sus tablas</li>
     * <li>cargar las clases indicadas en el archivo startup.properties</li>
     * <li>executar el m&eacute;todo {@code loadSemanticModels}</li>
     * <li>crear el sitio de administraci&oacute;n, si a&uacute;n no existe</li>
     * <li>crear el sitio de edici&oacute;n de ontolog&iacute;as, si a&uacute;n no existe</li>
     * <li>crear el repositorio de usuarios del sitio de administraci&oacute;n, si a&uacute;n no existe</li>
     * <li>crear el sitio global, si a&uacute;n no existe</li>
     * <li>crear el repositorio de usuarios por defecto, si a&uacute;n no existe</li>
     * <li>crear las tablas de base de datos para la bit&aacute;cora</li>
     * <li>inicializar los objetos: {@code SWBUserMgr}, {@code SWBMonitor}, {@code SWBResourceMgr},
     * {@code PFlowManager}, {@code SWBTemplateMgr}, {@code SWBServiceMgr}, {@code SWBDBAdmLog},
     * {@code SWBMessageCenter}, {@code SWBAccessLog}, {@code SWBAccessIncrement} y {@code SWBIndexMgr},
     * que se pueden acceder directamente de esta clase, y {@code RuleMgr}</li>
     * <li>cargar los archivos contenidos en SWBAdmin.jar y dojo.jar</li>
     * </ul>
     * </p>
     */
    private void init()
    {
        props = SWBUtils.TEXT.getPropertyFile("/web.properties");
        try {
        secProps = SWBUtils.TEXT.getPropertyFile("/security.properties");
        } catch (NullPointerException npe) {
            secProps = null;
        }

        workPath = getEnv("swb/workPath");

        SWBUtils.EMAIL.setSMTPSsl(Boolean.parseBoolean(getEnv("swb/smtpSsl","false")));
        SWBUtils.EMAIL.setSMTPServer(getEnv("swb/smtpServer"));
        SWBUtils.EMAIL.setSMTPPort(Integer.parseInt(getEnv("swb/smtpPort","0")));
        SWBUtils.EMAIL.setSMTPTls(Boolean.parseBoolean(getEnv("swb/smtpTls","false")));
        SWBUtils.EMAIL.setSMTPUser(getEnv("swb/smtpUser"));
        SWBUtils.EMAIL.setSMTPPassword(getEnv("swb/smtpPassword"));
        SWBUtils.EMAIL.setAdminEmail(getEnv("af/adminEmail"));

        standalone=getEnv("swb/clientServer","SASC").equalsIgnoreCase("SASC");
        client=!getEnv("swb/clientServer","SASC").equalsIgnoreCase("Server") && !getEnv("swb/clientServer","SASC").equalsIgnoreCase("SAS") && !standalone;

        try
        {
            //TODO:revisar sincronizacion
            //if (confCS.equalsIgnoreCase("Client")) remoteWorkPath = (String) AFUtils.getInstance().getEnv("swb/remoteWorkPath");

            workPath = (String) getEnv("swb/workPath", "/work");
            if (workPath.startsWith("file:"))
            {
                workPath = (new File(workPath.substring(5))).toString();
                workPathType=WORKPATH_ABSOLUTE;
            }
            else if (workPath.startsWith("http://"))
            {
                workPath = (URLEncoder.encode(workPath));
                workPathType=WORKPATH_REMOTE;
            }
            else
            {
                workPath = SWBUtils.getApplicationPath() + workPath;
                workPathType=WORKPATH_RELATIVE;
            }
        }
        catch (Exception e)
        {
            log.error("Can't read the context path...", e);
            workPath = "";
        }
        //System.out.println("workPath:"+workPath);

        //Insercion de parametros a Platform
        SWBPlatform platform = SWBPlatform.createInstance();
        SWBPlatform.setPortalLoaded(true);
        platform.setPlatformWorkPath(workPath);
        platform.setStatementsCache(getEnv("swb/ts_statementsCache", "false").equals("false"));
        String persistType = getEnv("swb/triplepersist", SWBPlatform.PRESIST_TYPE_DEFAULT);
        platform.setPersistenceType(persistType);
        platform.setAdminDev(getEnv("swb/adminDev", "false").equalsIgnoreCase("true"));
        platform.setAdminFile(getEnv("swb/adminFile", "/swbadmin/rdf/SWBAdmin.nt"));
        platform.setOntEditFile(getEnv("swb/ontEditFile", "/swbadmin/rdf/SWBOntEdit.nt"));
        platform.setProperties(props);
        platform.setSecurityProperties(secProps);

        try
        {
            //Runtime.getRuntime().gc();
//            long inimem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            Connection con = SWBUtils.DB.getDefaultConnection();
            if (con != null)
            {
                log.info("-->Database: " + SWBUtils.DB.getDatabaseName());
                  if (SWBUtils.DB.getDatabaseName().startsWith("HSQL")) checkHSQLHack(con); //MAPS74
                haveDB = true;
                //Si no existen las tablas, se crean automaticamente
//                PreparedStatement sr=con.prepareStatement("select * from swb_counter");
//                try
//                {
//                    ResultSet rs=sr.executeQuery();
//                    if(rs!=null)
//                    {
                haveDBTables = true;
//                        rs.close();
//                    }
//                }catch(Exception noe){log.debug("Table swbcounter not found...",noe);}
//                sr.close();
                con.close();
            }

            if (!haveDB())
            {
                log.warn("-->Default SemanticWebBuilder database not found...");
                return;
            }
            if (!haveDBTables())
            {
                log.warn("-->Default SemanticWebBuilder database tables not found...");
                return;
            }

            try
            {
                System.setProperty("sun.net.client.defaultConnectTimeout", getEnv("swb/defaultConnectTimeout", "5000"));
                System.setProperty("sun.net.client.defaultReadTimeout", getEnv("swb/defaultReadTimeout", "30000"));
            }
            catch (Exception e)
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
            if (!"ignore".equals(getEnv("swb/security.auth.login.config", "ignore")))
            {
                System.setProperty("java.security.auth.login.config", SWBUtils.getApplicationPath() + "/WEB-INF/classes" + getEnv("swb/security.auth.login.config", "/wb_jaas.config"));
                log.info("-->Login Config:" + System.getProperty("java.security.auth.login.config"));
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
//
//            Runtime.getRuntime().gc();
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_totalMemory") + Runtime.getRuntime().totalMemory(), true);
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_FreeMemory") + "\t\t" + Runtime.getRuntime().freeMemory(), true);
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_MemoryUsed") + "\t\t" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()), true);
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_MemoryUsedbyWb") + "\t" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - inimem), true);
//
//            util.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_WBLoader_Init_WbInicializing"), true);
        }
        catch (Throwable e)
        {
            log.error("Error loading SemanticWebBuilder Instance...", e);
        }

        loadSemanticModels();

        //System.out.println("Checking Predef Sites...");

        loadAdminFiles();

        resmgr = new SWBResourceMgr();

        try
        {

            WebSite site = SWBContext.getAdminWebSite();
            if (site == null)
            {
                log.event("Creating Admin WebSite...");
                SWBPlatform.getSemanticMgr().createModel(SWBContext.WEBSITE_ADMIN, "http://www.semanticwb.org/SWBAdmin#");
                //MAPS74
                SWBPlatform.getSemanticMgr().createKeyPair();
            }

            //System.out.println("Checking OntEditor...");

//            site = SWBContext.getOntEditor();
//            if (site == null)
//            {
//                log.event("Creating Ontology Editor WebSite...");
//                SWBPlatform.getSemanticMgr().createModel(SWBContext.WEBSITE_ONTEDITOR, "http://www.semanticwb.org/SWBOntEdit#");
//            }

            UserRepository urep = SWBContext.getAdminRepository();
            if (urep == null)
            {
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
                user.setCreated(new Date());
                user.setUpdated(new Date());
                user.setActive(true);

                UserGroup grp1 = urep.createUserGroup("admin");
                grp1.setTitle("Administrator", "en");
                grp1.setTitle("Administrador", "es");
                grp1.setCreated(new Date());
                grp1.setUpdated(new Date());
                grp1.setCreator(user);
                grp1.setModifiedBy(user);
                grp1.setUndeleteable(true);
                UserGroup grp2 = urep.createUserGroup("su");
                grp2.setTitle("Super User", "en");
                grp2.setTitle("Super Usuario", "es");
                grp2.setCreated(new Date());
                grp2.setUpdated(new Date());
                grp2.setCreator(user);
                grp2.setModifiedBy(user);
                grp2.setUndeleteable(true);
                grp2.setParent(grp1);
                user.addUserGroup(grp1);

                try
                {
                    site = SWBContext.getWebSite("demo");
                    if (site == null)
                    {
                        File file=new File(getWorkPath() + "/sitetemplates/demo.zip");
                        if(file.exists())
                        {
                            log.event("Creating Demo WebSite...");
                            UTIL.InstallZip(file);
                        }
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }

            
            //Check for GlobalWebSite
            site = SWBContext.getGlobalWebSite();
            if (site == null)
            {
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
            if (urep == null)
            {
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
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //System.out.println("Checking Database...");
        //Crear tablas LOGS
        try
        {
            Connection con = SWBUtils.DB.getDefaultConnection();
            //Statement st = con.createStatement();
            
//Connection con = DriverManager.getConnection(...);
//OracleConnection ocon = (OracleConnection)con;
//// Disable TABLE_REMARKS columns
//ocon.setRemarksReporting(false);
//DatabaseMetaData dmd = ocd.getMetaData();
//ResultSet res = dmd.getTables(...);
            
            
            try
            {
                //String tabla[] ={"TABLE"};
                //ResultSet rs = con.getMetaData().getTables(null, null, "swb_admlog", tabla);
                ResultSet rs = con.getMetaData().getTables(null, null, "%", null);
                boolean hasTable=false;
                while(rs.next())
                {
                    if(rs.getString(3).toLowerCase().indexOf("swb_admlog")>-1)hasTable=true;
                }
                if(!hasTable)
                {
                    log.event("Creating Logs Tables... "+SWBUtils.DB.getDatabaseName());
                    GenericDB db = new GenericDB();
                    String xml = SWBUtils.IO.getFileFromPath(SWBUtils.getApplicationPath() + "/WEB-INF/xml/swb_logs.xml");
                    db.executeSQLScript(xml, SWBUtils.DB.getDatabaseName(), null);
                }
                rs.close();
            }
            catch (SQLException e)
            {
                //ne.printStackTrace();
                log.error(e);
            }
            //st.close();
            con.close();
        }
        catch (SQLException e)
        {
            log.error(e);
        }

        usrMgr = new SWBUserMgr();
        usrMgr.init();

        monitor = new SWBMonitor();
        monitor.init();

        resmgr.init();

        pflowMgr = new PFlowManager();
        pflowMgr.init();

        templatemgr = new SWBTemplateMgr();
        templatemgr.init();

        admlog = new SWBDBAdmLog();
        admlog.init();

        msgcenter = new SWBMessageCenter();
        msgcenter.init();        
        platform.setMessageCenter(msgcenter);
        
        procesor = new SWBMessageProcesor(msgcenter);
        procesor.init();        

        acclog = new SWBAccessLog();
        acclog.init();

        accInc = new SWBAccessIncrement();
        accInc.init();

        adminfiltermgr = new SWBAdminFilterMgr();
        adminfiltermgr.init();

        servicemgr = new SWBServiceMgr();
        servicemgr.init();

        indmgr = new SWBIndexMgr();
        indmgr.init();
        
        log.event("Loading Friendly Urls...");
        FriendlyURL.refresh();

        //Inicializa el RuleMgr
        Rule.getRuleMgr();
        
        if (!"ignore".equals(getEnv("swb/cloudImplementation", "ignore"))){
            String cloudClass = getEnv("swb/cloudImplementation");
            try {
                Class clazz = Class.forName(cloudClass);
                cloudServices = (SWBCloud)clazz.newInstance();
                new Thread(){
                        public void run() {
                            cloudServices.launch();
                        }
                    }.start();
                log.event("CloudServices enabled...");
            } catch (Exception roe){ //catching Reflection and Thread Exceptions
                log.event("Can't instantiate cloud services", roe);
                assert false;
            }
        }

        try
        {
            //cargando clases de startup.properties
            SWBStartup startup = new SWBStartup(new ArrayList()); //objs);
            startup.load("startup.properties");
        }catch (Exception e)
        {
            e.printStackTrace();
        }                    
    }

    /**
     * Load admin files.
     */
    public void loadAdminFiles()
    {
        admFiles = new ConcurrentHashMap();
        try
        {
            log.debug("Loading admin Files from: /WEB-INF/lib/SWBAdmin.jar");
            String zipPath = SWBUtils.getApplicationPath() + "/WEB-INF/lib/SWBAdmin.jar";
            ZipFile zf = new ZipFile(zipPath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements())
            {
                ZipEntry ze = (ZipEntry) e.nextElement();
                log.debug("/" + ze.getName() + ", " + ze.getSize() + ", " + ze.getTime());
                admFiles.put("/" + ze.getName(), new JarFile(ze, zipPath));
            }
            zf.close();
            //log.event("-->Admin Files in Memory:\t" + admFiles.size());
        }
        catch (Exception e)
        {
            log.warn("Error loading files for Webbuilder Administration:" + SWBUtils.getApplicationPath() + "/WEB-INF/lib/SWBAdmin.jar", e);
        }

        try
        {
            log.debug("Loading admin Files from: /WEB-INF/lib/dojo.jar");
            String zipPath = SWBUtils.getApplicationPath() + "/WEB-INF/lib/dojo.jar";
            ZipFile zf = new ZipFile(zipPath);
            Enumeration e = zf.entries();
            while (e.hasMoreElements())
            {
                ZipEntry ze = (ZipEntry) e.nextElement();
                log.debug("/" + ze.getName() + ", " + ze.getSize() + ", " + ze.getTime());
                admFiles.put("/" + ze.getName(), new JarFile(ze, zipPath));
            }
            zf.close();
            log.event("-->Admin Files in Memory:\t" + admFiles.size());
        }
        catch (Exception e)
        {
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
    /**
     * Loads the model discribed by each onthology indicated in {@code swb/ontologyFiles}
     * environment variable. <p>Carga el modelo descrito para cada ontolog&iacute;a
     * indicada en {@code swb/ontologyFiles}.</p>
     */
    public void loadSemanticModels()
    {
        log.event("Loading SemanticModels...");

        String owlPath = getEnv("swb/ontologyFiles", "/WEB-INF/owl/swb.owl");

        SWBPlatform.getSemanticMgr().initializeDB();

        //Load Ontology from file
        StringTokenizer st = new StringTokenizer(owlPath, ",;");
        while (st.hasMoreTokens())
        {
            String file = st.nextToken();
            String swbowl = "file:" + SWBUtils.getApplicationPath() + file;
            log.info("Loading OWL:" + swbowl);
            //System.out.println("Loading OWL:"+ swbowl);
            SemanticModel model = SWBPlatform.getSemanticMgr().addBaseOntology(swbowl);
        }
        //System.out.println("End OWLs");

        SWBPlatform.getSemanticMgr().loadBaseVocabulary();

        //System.out.println("End Vocabulary");

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

        if (getEnv("swb/addModel_DBPedia", "false").equals("true"))
        {
            SWBPlatform.getSemanticMgr().loadRemoteModel("DBPedia", "http://dbpedia.org/sparql", "http://dbpedia.org/resource/", false);
        }
//        if(SWBPlatform.getEnv("swb/addOnt_DBPedia","false").equals("true"))
//        {
//            loadRemoteModel("Books", "http://www.sparql.org/books", "http://example.org/book/",false);
//        }

        //TODO agregar RDFa
        //System.out.println("end loadDBModels");

        SWBPlatform.getSemanticMgr().rebind();
        //System.out.println("End loadSemanticModels");
    }

    /**
     * Gets the context path for this resource's instance. For example: {@literal /swb}, or {@literal /} if
     * the context is the root context.
     * <p>Obtiene la ruta del contexto para esta instancia de portal. <br/>Por ejemplo:
     * 
     * @return the string representing the context path for this resource's instance.
     * {@literal /swb}, o {@literal /} si es el contexto ra&iacute;z</p>
     */
    public static String getContextPath()
    {
        if (instance != null)
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
     * Sets the value for this portal instance's context path.
     * <p>Fija el valor de la ruta del contexto de esta instancia de portal</p>
     * @param contextPath the string representing the new path for the portal context.
     *        If the string ends with a {@literal /}, the value stored won't contain it.
     *        This is: <br/>value received {@literal /swb/} -> value stored {@literal /swb}
     */
    public void setContextPath(String contextPath)
    {
        log.event("ContextPath:" + contextPath);
        this.contextPath = contextPath;

        try
        {
            if(getEnv("swb/webWorkPath","/work").startsWith("/"))
            {
                if (contextPath.endsWith("/"))
                {
                    webWorkPath = contextPath + getEnv("swb/webWorkPath","/work").substring(1);
                }
                else
                {
                    webWorkPath = contextPath + getEnv("swb/webWorkPath","/work");
                }
            }else
            {
                webWorkPath = getEnv("swb/webWorkPath","/work");
            }            
        }
        catch (Exception e)
        {
            log.error("Can't read the context path...", e);
            webWorkPath = "";
        }
        SWBPlatform.createInstance().setContextPath(contextPath);
    }

    /**
     * Gets this portal work directory's physical path. For example:<br/>/opt/swb/work
     * <p>Obtiene la ruta f&iacute;sica del directorio de trabajo del portal
     * Ejemplo: /opt/swb/work</p>
     * @return a string representing the work directory's physical path
     */
    public static String getWorkPath()
    {
        return workPath;
    }

    /**
     * Gets the work path type.
     * 
     * @return the work path type
     */
    public static String getWorkPathType()
    {
        return workPathType;
    }

    /**
     * Gets the web path for this portal <strong>work</strong> directory. <br/>For example: /[context]/work
     * <p>Obtiene ruta web del directorio de trabajo del portal
     * Ejemplo: /[context]/work </p>
     * @return a string representing the work directory's web path
     */
    public static String getWebWorkPath()
    {
        return webWorkPath;
    }

    /**
     * Retrieves the value of the environment variable whose name matches {@code name}.
     * The variable to look for might be declared into either web.xml or web.properties files.
     * <p>Obtiene el valor de una variable de ambiente cuyo nombre coincida con el valor
     * contenido en {@code name}. La variable a buscar puede estar declarada en los archivos
     * web.xml o web.properties.</p>
     * @param name a string representing the environment variable's name to find
     * @return a string representing the environment variable's value. In case the
     *         indicated variable is not found, {@code null} will be returned.
     */
    public static String getEnv(String name)
    {
        return getEnv(name, null);
    }

    /**
     * Retrieves the environment variable's value whose name matches the value of.
     * 
     * @param name un string que indica el nombre de la variable a buscar
     * @param defect un string con el valor a devolder por defecto, en caso de no encontrar la variable.
     * @return un string con el valor de la variable indicada por {@code name}, si existe,
     * en caso contrario devuelve el valor de {@code defect}.
     * {@code name}, or returns the value of {@code defect}. The searched variable
     * might be declared in web.xml or web.properties files.
     * <p>Obtiene el valor de la variable de ambiente cuyo nombre coincida con el valor
     * contenido en {@code name}, o devuelve el valor de {@code defect}. La variable
     * a buscar puede estar declarada en los archivos web.xml o web.properties.</p>
     */
    public static String getEnv(String name, String defect)
    {
        String obj = null;

        obj = System.getProperty("swb.web." + name);
        if (obj != null)
        {
            return obj;
        }
        if (props != null)
        {
            obj = props.getProperty(name);
        }
        if (obj == null)
        {
            return defect;
        }
        return obj;
    }

    /**
     * Retrieves the security environment variable's value whose name matches the value of.
     *
     * @param name un string que indica el nombre de la variable a buscar
     * @param defect un string con el valor a devolder por defecto, en caso de no encontrar la variable.
     * @return un string con el valor de la variable indicada por {@code name}, si existe,
     * en caso contrario devuelve el valor de {@code defect}.
     * {@code name}, or returns the value of {@code defect}. The searched variable
     * might be declared in  security.properties files.
     * <p>Obtiene el valor de la variable de ambiente cuyo nombre coincida con el valor
     * contenido en {@code name}, o devuelve el valor de {@code defect}. La variable
     * a buscar puede estar declarada en el archivo security.properties.</p>
     */
    public static String getSecEnv(String name, String defect)
    {
        String obj = null;

        if (secProps != null)
        {
            obj = secProps.getProperty(name);
        }
        if (obj == null)
        {
            return defect;
        }
        return obj;
    }

    /**
     * Gets a reference to the servletContext associated to this object
     * <p>Obtiene el servletContext asociado a este recurso.</p>
     * @return the servletContext used in this object's creation
     */
    public static ServletContext getServletContext()
    {
        return servletContext;
    }

    /**
     * Gets a reference to the filter associated to this resource.
     * <p>Obtiene el filter asociado a este recurso.</p>
     * @return the filter used in this object creation, {@code null} if no filter 
     *         was specified.
     */
    public static Filter getVirtualHostFilter()
    {
        return virtualHostFilter;
    }

    /**
     * Gets a reference to the object holding the properties declared in {@literal web.properties} file.
     * <p>Obtiene la referencia a todas las propiedades declaradas en el archivo web.properties.</p>
     * @return the properties object wich stores the configured properties in {@literal web.properties} file.
     */
    public static Properties getWebProperties()
    {
        return props;
    }

    /**
     * Indicates if this instance of portal has a database related to it.
     * <p>Indica si esta instancia de portal tiene relacionada una base de datos.</p>
     * @return the boolean indicating if a database existis for this instance of portal.
     */
    public static boolean haveDB()
    {
        return haveDB;
    }

    /**
     * Indicates if this instance of portal has database tables to work with.
     * <p>Indica si esta instancia de portal tiene las tablas de base de datos
     * necesarias para trabajar.</p>
     * @return the boolean indicating if this instance of portal has database tables to work with.
     */
    public static boolean haveDBTables()
    {
        return haveDBTables;
    }

    /**
     * Indicates if this instance of portal is working with a database.
     * <p>Indica si esta instancia de portal est&aacute; trabajanso con una base de datos.</p>
     * @return the boolean indicating if this instance of portal is working with a database.
     */
    public static boolean isUseDB()
    {
        return useDB;
    }

    /**
     * Modifies the indicator of this instance of portal to work with a database.
     * <p>Modifica el indicador de esta instancia de portal para trabajar con una base de datos.</p>
     * @param useDB a boolean that indicates to this instance of portal if it has to use a database.
     */
    public static void setUseDB(boolean useDB)
    {
        SWBPortal.useDB = useDB;
    }

    /**
     * Deletes a file whose representing path is within the work directory of this 
     * portal's instance. <p>Elimina un archivo cuya ruta se encuentra dentro del
     * directorio de trabajo de esta instancia de portal.</p>
     * @param path the string representing the file to delete, which has to be within the work directory.
     */
    public static void removeFileFromWorkPath(String path)
    {
        //TOTO:Impementar Replicacion de archivos
        File file = new File(getWorkPath() + path);
        file.delete();
    }

    /**
     * Reads a file whose path is located within this portal's work directory.
     * <p>Lee un archivo cuya ruta est&aacute; ubicada dentro del directorio de
     * trabajo de este portal.</p>
     * 
     * @param path the string representing the file's path to read, which has to
     * be within the work directory.
     * @return an inputStream with the indicated file's content
     * @throws SWBException if the file path indicated cannot be located within the
     * work directory or is equal to {@code null}
     */
    public static InputStream getFileFromWorkPath(String path) throws SWBException
    {
        //System.out.println("getFileFromWorkPath:"+path);
        InputStream ret = null;
        //TOTO:Impementar Replicacion de archivos
        try
        {
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
        }
        catch (Exception e)
        {
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
    /**
     * Creates a file to the path and with the content indicated.
     * <p>Crea un archivo en la ruta y con el contenido indicados.</p>
     * 
     * @param path a string representing the path within the work directory to create the file
     * @param in   an input stream with the new file's content
     * @param user the user
     * @throws org.semanticwb.SWBException if the path specified cannot be created or is {@code null}
     * @throws SWBException the sWB exception
     */
    public static void writeFileToWorkPath(String path, InputStream in, User user) throws SWBException
    {
        //System.out.println("writeFileToWorkPath:"+path);
        //TOTO:Impementar Replicacion de archivos
        try
        {
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
            File file = new File(getWorkPath() + path);
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            SWBUtils.IO.copyStream(in, out);
//            }
        }
        catch (Exception e)
        {
            throw new SWBException(e.getMessage(), e);
        }
    }

    /**
     * Reads a file whose path exists within the work directory and puts its content into a string.
     * <p>Lee un archivo cuya ruta existe dentro del directorio de trabajo ({@literal work})
     * y coloca su contenido en una string.</p>
     * 
     * @param path the string representing the file's path within the work directory
     * @return a stream with the indicated file's content
     * @throws IOException if the file specified cannot be read.
     * @throws SWBException the sWB exception
     */
    public static String readFileFromWorkPath(String path) throws IOException, SWBException
    {
        return SWBUtils.IO.readInputStream(getFileFromWorkPath(path));
    }

    /**
     * Reads a file whose path is located within this portal's work directory using a
     * specified character code. It uses the method {@code getFileFromWorkPath(String)}'s services.
     * <p>Lee un archivo cuya ruta se ubica dentro del directorio de trabajo ({@code work})
     * de este portal utilizando el c&oacute;digo de caracteres especificado.
     * Utiliza los servicios del m&eacute;todo {@code getFileFromWorkPath(String)}.</p>
     * 
     * @param path a string representing the file's path to read, which has to be
     * within the work directory.
     * @param encode a string representing the character code to use while reading the file's content.
     * @return a stream with the indicated file's content
     * @throws org.semanticwb.SWBException if there's a problem while reading the file's content.
     * @throws SWBException the sWB exception
     */
    public static InputStreamReader getFileFromWorkPath(String path, String encode) throws SWBException
    {
        InputStreamReader ret = null;
        try
        {
            ret = new InputStreamReader(getFileFromWorkPath(path), encode);
        }
        catch (Exception e)
        {
            throw new SWBException(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * Reads a file whose path exists within the work directory and puts the file's
     * content into a string. <p>Lee un archivo cuya ruta existe dentro del directorio {@code work}
     * y coloca el contenido del archivo en una string.</p>
     * @param path the string representing the file's path within the work directory
     * @param encode a string representing the character code to use while reading the file's content.
     * @return a string representing the specified file's content
     * @throws SWBException if there's a problem while reading the file's content.
     */
    public static String readFileFromWorkPath(String path, String encode) throws SWBException
    {
        StringBuilder ret = new StringBuilder(IO.getBufferSize());
        try
        {
            InputStreamReader file = getFileFromWorkPath(path, encode);
            char[] bfile = new char[SWBUtils.IO.getBufferSize()];
            int x;
            while ((x = file.read(bfile, 0, SWBUtils.IO.getBufferSize())) > -1)
            {
                ret.append(bfile, 0, x);
            }
            file.close();
        }
        catch (Exception e)
        {
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
    /**
     * Indicates if the SemanticWebBuilder instance is configured as a client.
     * <p>Indica si esta instancia de SemanticWebBuilder (SWB) est&aacute;
     * configurada como cliente.</p>
     * @return a boolean that indicates if the SWB instance is configured as a client.
     */
    public static boolean isClient()
    {
        return client;
    }

    //TODO:
    /**
     * Indicates if the SemanticWebBuilder instance is configured as standalone.
     * <p>Indica si esta instancia de SemanticWebBuilder (SWB) est&aacute;
     * configurada como una sola maquina.</p>
     * @return a boolean that indicates if the SWB instance is configured as a stand alone.
     */
    public static boolean isStandAlone()
    {
        return standalone;
    }

    /**
     * Gets the distributor path in this instance of SWB.
     * <p>Obtiene la ruta del distribuidor de esta instancia de SWB</p>
     * @return a string representing the distributor path within the context of this instance of SWB.
     */
    public static String getDistributorPath()
    {
        return getContextPath() + "/" + getEnv("swb/distributor", "swb");
    }

    /**
     * Gets a reference to the user manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de usuarios de esta instancia de SWB</p>
     * @return a SWBUserMgr object for this instance of SWB
     */
    public static SWBUserMgr getUserMgr()
    {
        return usrMgr;
    }

    /**
     * Gets a reference to the monitor for this instance of SWB.
     * <p>Obtiene una referencia al monitor de esta instancia de SWB</p>
     * @return a SWBMonitor object for this instance of SWB.
     */
    public static SWBMonitor getMonitor()
    {
        return monitor;
    }

    /**
     * Gets a reference to the resource manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de recursos de esta instancia de SWB</p>
     * @return a SWBResourceMgr object for this instance of SWB.
     */
    public static SWBResourceMgr getResourceMgr()
    {
        return resmgr;
    }

    /**
     * Gets a reference to the template manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de plantillas de esta instancia de SWB</p>
     * @return a SWBTemplateMgr object for this instance of SWB.
     */
    public static SWBTemplateMgr getTemplateMgr()
    {
        return templatemgr;
    }

    /**
     * Gets a reference to the service manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de servicios de esta instancia de SWB</p>
     * @return a SWBServiceMgr object for this instance of SWB.
     */
    public static SWBServiceMgr getServiceMgr()
    {
        return servicemgr;
    }

    /**
     * Gets a reference to the service manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de servicios de esta instancia de SWB</p>
     * @return a SWBServiceMgr object for this instance of SWB.
     */
    public static SWBAdminFilterMgr getAdminFilterMgr()
    {
        return adminfiltermgr;
    }

    /**
     * Gets a reference to the database administration log for this instance of SWB.
     * <p>Obtiene una referencia a la bit&aacute;cora de administraci&oacute;n de
     * base de datos de esta instancia de SWB</p>
     * @return a SWBDBAdmLog object for this instance of SWB.
     */
    public static SWBDBAdmLog getDBAdmLog()
    {
        return admlog;
    }

    /**
     * Gets a reference to the message center for this instance of SWB.
     * <p>Obtiene una referencia al centro de mensajes de esta instancia de SWB</p>
     * @return a SWBMessageCenter object for this instance of SWB.
     */
    public static SWBMessageCenter getMessageCenter()
    {
        return msgcenter;
    }

    /**
     * Gets a reference to the access log for this instance of SWB.
     * <p>Obtiene una referencia a la bit&aacute;cora de accesos de esta instancia de SWB</p>
     * @return a SWBAccessLog object for this instance of SWB.
     */
    public static SWBAccessLog getAccessLog()
    {
        return acclog;
    }

    /**
     * Gets a reference to the publication flow manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de flujos de publicaci&oacute;n
     * de esta instancia de SWB</p>
     * @return a PFlowManager object for this instance of SWB.
     */
    public static PFlowManager getPFlowManager()
    {
        return pflowMgr;
    }

    /**
     * Gets a reference to the access incrementor for this instance of SWB.
     * <p>Obtiene una referencia al incrementador de accesos de esta instancia de SWB</p>
     * @return a SWBAccessIncrement object for this instance of SWB.
     */
    public static SWBAccessIncrement getAccessIncrement()
    {
        return accInc;
    }

    /**
     * Gets a reference to the index manager for this instance of SWB.
     * <p>Obtiene una referencia al administrador de &iacute;ndices de esta instancia de SWB.</p>
     * @return a SWBIndexMgr object for this instance of SWB.
     */
    public static SWBIndexMgr getIndexMgr()
    {
        return indmgr;
    }

    /**
     * Gets a file from the .jar files loaded at initialization of SWB. The requested
     * file's path has to be within the structure directory of SWBAdmin.jar or dojo.jar.
     * <p>Extrae un archivo de los archivos .jar cargados en la inicializaci&oacute;n de
     * SWB. La ruta de archivo solicitada debe existir en la estructura de directorios
     * de SWBAdmin.jar o dojo.jar.</p>
     * @param path a string representing an existing path within SWBAdmin.jar or dojo.jar files.
     * @return a jarFile object with the specified file's content.
     */
    public static JarFile getAdminFile(String path)
    {
        JarFile f = (JarFile) admFiles.get(path);
        if (f != null)
        {
            JarFile aux=null;

            if(f.isReplaced())
            {
                aux=new JarFile(path);
            }else
            {
                aux=f;
            }

            if(f.isExpired())
            {
                JarFile aux2=new JarFile(path);
                if(aux2.exists())
                {
                    f.setReplaced(true);
                    aux=aux2;
                }else
                {
                    if(f.isReplaced())
                    {
                        f.setReplaced(false);
                    }
                }
                f.touch();
            }
            f=aux;
        }else
        {
           f = new JarFile(path);
        }
        return f;
    }

    /**
     * Gets a file from the .jar files loaded at initialization of SWB. The requested
     * file's path has to be within the structure directory of SWBAdmin.jar or dojo.jar.
     * <p>Extrae un archivo de los archivo .jar cargados al inicializar SWB.
     * La ruta de archivo solicitada debe existir en la estructura de directorios de
     * SWBAdmin.jar o dojo.jar.</p>
     * @param path a string representing an existing path within SWBAdmin.jar or dojo.jar files.
     * @return an input stream with the specified file's content.
     */
    public static InputStream getAdminFileStream(String path)
    {
        JarFile f = (JarFile) admFiles.get(path);
        if (f == null)
        {
            f = new JarFile(path);
        }
        if (!f.exists())
        {
            return null;
        }
        return f.getInputStream();
    }

    /**
     * Retrieves all languages from all sites in this instance of SWB.
     * <p>Obtiene todos los lenguajes de todos los sitios en esta instancia de SWB</p>
     * @return an arrayList with all languages available in all sites
     */
    public static ArrayList getAppLanguages()
    {
        ArrayList languages = new ArrayList();
        Iterator<WebSite> it = SWBContext.listWebSites();
        while (it.hasNext())
        {
            WebSite site = it.next();
            Iterator<Language> itLang = site.listLanguages();
            while (itLang.hasNext())
            {
                Language lang = itLang.next();
                if (!languages.contains(lang.getId()))
                {
                    languages.add(lang.getId());
                }
            }
        }
        return languages;
    }
    
    /**
     * Gets the cloud services interface
     * @return 
     */
    public final static SWBCloud getCloud() {
        return cloudServices;
    }

    /**
     * Combines general usefull operations for the portal platform, such as:
     * relative path replacement within HTML code, search of anchors in HTML code,
     * captcha like image generation, HTML in-code referenced file listing.
     * <p>Conjunta operaciones &uacute;tiles a nivel general para la plataforma de
     * portal, como: sustituci&oacute;n de rutas locales en c&oacute;digo HTML,
     * b&uacute;squeda de anclas en HTML, generaci&oacute;n de im&aacute;genes tipo
     * captcha, listado de archivos referenciados por c&oacute;digo HTML.</p>
     */
    public static class UTIL
    {

        /**
         * a string that contains all characters in the english alphabet and the 10 digits. <p>
         * una string que contiene todos los caracteres del alfabeto ingl&eacute;s
         * y los 10 d&iacute;gitos del sistema decimal.</p>
         * Used to generate strings with characters selected randomly.
         */
        private final static String ALPHABETH = "ABCDEFGHiJKLMNPQRSTUVWXYZ123456789+=?";

        /**
         * Looks for relative paths in the attributes of HTML tags and replaces
         * their values with {@code ruta}, the content will be displayed in one single page.
         * The HTML tags and elements evaluated are the same as for the {@code parseHTML(String, String, int)} method.
         * <p>Busca rutas relativas en los atributos de tags de HTML y remplaza
         * sus valores con {@code ruta}, el contenido se desplegar&aacute; en una sola
         * p&aacute;gina. Los tags y atributos de HTML a evaluar son los mismos que
         * para el m&eacute;todo {@code parseHTML(String, String, int)}.</p>
         * @param datos the string with the HTML code to parse.
         * @param ruta the string with the new path to write to {@code datos}.
         * @return a string with the HTML code with relative paths modified.
         */
        public static String parseHTML(String datos, String ruta)
        {
            return parseHTML(datos, ruta, 0);
        }

        /**
         * Looks for relative paths in the attributes of HTML tags and replaces
         * their values with {@code ruta}, indicating the number of {@code pages} into which the content is divided.
         * The HTML tags it evaluates are: {@code img}, {@code applet}, {@code script}, {@code table},
         * 
         * @param datos the string with the HTML code to parse.
         * @param ruta the string with the new path to write to {@code datos}.
         * @param pages the number of pages which the content is divided into.
         * @return a string with the HTML code with relative paths modified.
         * {@code tr}, {@code td}, {@code body}, {@code form}, {@code input}, {@code a}, {@code area}
         * {@code meta}, {@code bca}, {@code link}, {@code param}, {@code embed}, {@code iframe} and {@code frame} and
         * the attributes it evaluates are: {@code src}, {@code href}, {@code background}, {@code codebase}, {@code value},
         * {@code onmouseover}, {@code onload}, {@code onmouseout} and {@code onclick}.
         * <p>Busca rutas de archivo relativas en los atributos de tags de HTML y
         * remplaza sus valores con la recibida en {@code ruta}, indicando el n&uacute;mero
         * de p&aacute;ginas (con {@code pages}) en que se divide el contenido.
         * Los tags de HTML a evaluar son: {@code img}, {@code applet}, {@code script},
         * {@code table}, {@code tr}, {@code td}, {@code body}, {@code form}, {@code input},
         * {@code a}, {@code area}, {@code meta}, {@code bca}, {@code link}, {@code param},
         * {@code embed}, {@code iframe} y {@code frame}; los atributos que eval&uacute;a son:
         * {@code src}, {@code href}, {@code background}, {@code codebase}, {@code value},
         * {@code onmouseover}, {@code onload}, {@code onmouseout} y {@code onclick}</p>
         * @author Jorge Jim&eacute;nez
         */
        public static String parseHTML(String datos, String ruta, int pages)
        {

            hAnchors = new HashMap();
            //detección de si el contenido es de word
            boolean iswordcontent = false;
            int posword = -1;
            posword = datos.toLowerCase().indexOf("name=generator content=\"microsoft word");
            if (posword > -1)
            {
                iswordcontent = true;
            }
            else
            {
                posword = datos.toLowerCase().indexOf("name=\"generator\" content=\"microsoft word");
                if (posword > -1)
                {
                    iswordcontent = true;
                }
            }
            //termina detección de si es contenido de word

            HtmlTag tag = new HtmlTag();
            int pos = -1; 
            //int pos1 = -1;  // no usada
            StringBuilder ret = new StringBuilder();
            try
            {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
                {
                    int ttype = tok.getTokenType();
                    //System.out.println("tok:"+tok.getTokenType()+" "+tok.getRawString()+" -->" +tok.getStringValue());
                    //if (ttype==HtmlStreamTokenizer.TT_COMMENT) continue;
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                    {
                        if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->"))
                        {
                            continue;
                        }
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if"))
                        {
                            continue;
                        }
                        if (iswordcontent && tag.getTagString().toLowerCase().equals("object") && !tag.isEndTag())
                        {
                            boolean isFlash = false;
                            Enumeration en = tag.getParamNames();
                            while (en.hasMoreElements())
                            {
                                String name = (String) en.nextElement();
                                if (name != null && name.toLowerCase().equals("classid") && tag.getParam(name) != null && tag.getParam(name).toLowerCase().equals("clsid:D27CDB6E-AE6D-11cf-96B8-444553540000".toLowerCase()))
                                {
                                    isFlash = true;
                                    break;
                                }
                            }
                            if (isFlash)
                            {
                                HtmlTag embed = new HtmlTag();
                                embed.setTag("embed");
                                if (tag.getParam("width") != null && !tag.getParam("width").equals(""))
                                {
                                    embed.setParam("width", tag.getParam("width"));
                                }
                                if (tag.getParam("height") != null && !tag.getParam("height").equals(""))
                                {
                                    embed.setParam("height", tag.getParam("height"));
                                }
                                while (!(tag.isEndTag() && tag.getTagString().equals("object")))
                                {
                                    ttype = tok.nextToken();
                                    if (ttype == HtmlStreamTokenizer.TT_TAG)
                                    {
                                        tok.parseTag(tok.getStringValue(), tag);
                                        if (tag.getTagString().equals("param") && !tag.isEndTag())
                                        {
                                            String name = tag.getParam("name");
                                            if (name != null && name.toLowerCase().equals("movie") && tag.getParam("value") != null && !tag.getParam("value").equals(""))
                                            {
                                                String filename = findFileName(tag.getParam("value"));
                                                embed.setParam("id", filename);
                                                embed.setParam("name", filename);
                                                String sruta = ruta + filename;
                                                embed.setParam("src", sruta);
                                            }
                                            if (name != null && name.toLowerCase().equals("quality") && tag.getParam("value") != null && !tag.getParam("value").equals(""))
                                            {
                                                embed.setParam("quality", tag.getParam("value"));
                                            }
                                        }
                                    }

                                }


                                embed.setParam("pluginspage", "http://www.macromedia.com/go/getflashplayer");
                                embed.setParam("type", "application/x-shockwave-flash");
                                ret.append("\r\n<script type=\"text/javascript\">\r\n<!--\r\ndocument.write('").append(embed.toString()).append("</embed>');\r\n-->\r\n</script>");
                                ttype = tok.nextToken();
                                while (!(ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT))
                                {
                                    ttype = tok.nextToken();
                                }
                                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                                {
                                    if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->"))
                                    {
                                        continue;
                                    }
                                }
                                tok.parseTag(tok.getStringValue(), tag);

                            }
                        }
                        if ((tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("tr") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("form") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("meta") || tag.getTagString().toLowerCase().equals("bca") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed") || tag.getTagString().toLowerCase().equals("iframe") || tag.getTagString().toLowerCase().equals("frame")) && !tok.getRawString().startsWith("<!--"))
                        {
                            if (!tag.isEndTag())
                            {
                                ret.append("<");
                                ret.append(tag.getTagString());
                                ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements())
                                {
                                    boolean bwritestyle = true;
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);

                                    //revisar si no tiene repercuciones
                                    value = value.replace('\\', '/');
                                    value = SWBUtils.TEXT.replaceAll(value, "%3f", "?");
                                    value = SWBUtils.TEXT.replaceAll(value, "%3F", "?");


                                    String sruta = null;
                                    if (!value.toLowerCase().startsWith("wbrelpath://") && name.toLowerCase().equals("src") && tag.getTagString().toLowerCase().equals("img")) // imagenes deben siempre parsearse, agregado por Víctor Lorenzana
                                    {
                                        sruta = ruta;
                                        value = findFileName(value);
                                    }
                                    else if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.toLowerCase().startsWith("wbrelpath://") && !value.startsWith("https://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("../") && !value.startsWith("{"))
                                    { //Comentado Jorge Jiménez y Vic Lorenzana (30/07/2009)
                                        if (!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".") > -1)
                                        {
                                            sruta = ruta;
                                        }
                                        //poner solo archivo
                                        if (((pos = value.indexOf("/")) > -1) || (pos = value.indexOf("\\")) > -1)
                                        {
                                            value = findFileName(value);
                                        }
                                    }
                                    else if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) &&  !value.startsWith("http://") && !value.toLowerCase().startsWith("wbrelpath://") && !value.startsWith("https://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("{") && !value.startsWith("../") && value.indexOf("/work/sites/")!=-1 && value.indexOf(".")!=-1) // es version 3.2 debe corregir ruta
	                            {
                                        sruta = ruta;
                                        value = findFileName(value);
                                    }
                                    else if (value.startsWith("wbrelpath://"))
                                    {
                                        value = value.substring("wbrelpath://".length());
                                        value = "./" + value;
                                    }
                                    else if (name.toLowerCase().equals("href") && value.startsWith("../"))
                                    {
                                        sruta = ruta; //Agregado 29/07/2009 (Jorge Jiménez-Vic Lorenzana)
                                        value = findFileName(value);
                                    }
                                    else if (name.toLowerCase().equals("href") && value.startsWith("#_") && pages > 1)
                                    { //Es un ancla
                                        int page = findAnchorInContent(datos, value, pages);
                                        if (page > 0)
                                        {
                                            value = "?page=" + page + "&" + value;
                                        }
                                    }
                                    else if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick"))
                                    {
                                        String out = findImagesInScript(value, ".gif'", ruta);
                                        out = findImagesInScript(out, ".jpg'", ruta);
                                        //ret.append(ruta);
                                        if (!out.equals(""))
                                        {
                                            value = out;
                                        }
                                    }
                                    else if (tag.getTagString().toLowerCase().equals("body") && iswordcontent && (name.equals("link") || name.equals("vlink")))
                                    { //elimina los liks
                                        bwritestyle = false;
                                    }
                                    if (bwritestyle)
                                    {
                                        ret.append(name);
                                        ret.append("=\"");
                                        if (sruta != null)
                                        {
                                            ret.append(sruta);
                                        }
                                        ret.append(value);
                                        ret.append("\" ");
                                    }
                                }
                                if (tag.isEmpty())
                                {
                                    ret.append("/");
                                }
                                ret.append(">");
                                if (tag.getTagString().toLowerCase().equals("form"))
                                {
                                    ret.append(actionval);
                                }
                            }
                            else
                            {
                                ret.append(tok.getRawString());
                            }
                        }
                        else
                        {
                            ret.append(tok.getRawString());
                        }
                    }
                    else if (ttype == HtmlStreamTokenizer.TT_TEXT)
                    {
                        ret.append(tok.getRawString());
                    }else
                    {
                        ret.append(tok.getRawString());
                    }
                }
            }
            catch (NumberFormatException f)
            {
                log.error(f);
            }
            catch (Exception e)
            {
                log.error(e);
            }
            return ret.toString();
        }
        

        /**
         * Retrieves a file name from a string representing a relative path.
         * <p>Obtiene un nombre de archivo de una string que representa una ruta relativa.</p>
         * @param value a string representing a relative path.
         * @return a string with the file name found. If no file name is found
         *         {@code value} is returned intact.
         */
        private static String findFileName(String value)
        {
            String out = "";
            if (value.startsWith("../"))
            {
                out = takeOffString(value, "../");
                if (!out.equals(""))
                {
                    value = out;
                }
            }
            int x = value.lastIndexOf(".");
            if (x > -1)
            {
                int y = value.lastIndexOf("\\", x);
                if (y > -1)
                {
                    value = value.substring(y + 1);
                }
                y = value.lastIndexOf("/", x);
                if (y > -1)
                {
                    value = value.substring(y + 1);
                }
            }
            return value;
        }

        /**
         * Eliminates a leading substring from a string. For example: <br/>
         * if {@code value = "../images/favicon.ico"} <br/>
         * and {@code takeOff = ".."} <br/>
         * 
         * @param value the string that contains the substring to eliminate
         * @param takeOff the substring to eliminate
         * @return the string whithout {@code takeOff}
         * {@code takeOffString(value, takeOff) = "/images/favicon.ico"}
         * <p>Elimina una subcadena precedente de una string, como se ejemplifica arriba.</p>
         */
        public static String takeOffString(String value, String takeOff)
        {
            int pos = -1;
            do
            {
                pos = value.indexOf(takeOff);
                if (pos != -1)
                {
                    value = value.substring(pos + takeOff.length());
                }
            }
            while (pos != -1);
            return value;
        }

        /**
         * Finds the number page in which an anchor name exists in {@code content}.
         * <p>Encuentra el n&uacute;mero de p&aacute;gina en que un ancla existe en {@code content}.</p>
         * @param content the string in which the anchor name will be searched
         * @param ancla the string to search in {@code content}
         * @param pages the number of pages {@code content} is divided into
         * @return the page number in which {@code ancla} was found
         */
        private static int findAnchorInContent(String content, String ancla, int pages)
        {
            ancla = ancla.substring(1);
            Integer page = (Integer) hAnchors.get(ancla);
            if (page != null)
            { //existe en hash de anclas
                return page.intValue();
            }
            else
            {
                for (int i = 0; i <= pages; i++)
                {
                    if (findAnchorInContentPage(content, ancla, i, pages))
                    {
                        return i;
                    }
                }
            }
            return 0;
        }

        /**
         * Indicates if an anchor name exists in the page indicated by {@code page}
         * within the content of {@code datos}. <p>Indica si un nombre de ancla existe
         * en la p&aacute;gina del n&uacute;mero indicado por {@code page}, dentro
         * del contenido de {@code datos}.</p>
         * @param datos the string in which the anchor name will be searched
         * @param ancla the string representing the anchor name to search in {@code datos}
         * @param page the page number that is going to be analized
         * @param itpages the total number of pages in {@code datos}
         * @return a boolean indicating whether the anchor was found or not
         */
        public static boolean findAnchorInContentPage(String datos, String ancla, int page, int itpages)
        {
            HtmlTag tag = new HtmlTag();
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            try
            {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new java.io.ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
                {
                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                    {
                        tok.parseTag(tok.getStringValue(), tag);
                        if (tok.getRawString().toLowerCase().startsWith("<!--[if"))
                        {
                            continue;
                        }
                        if (tag.getTagString().toLowerCase().equals("div"))
                        {
                            flag1 = true;
                            if (!tag.isEndTag())
                            {
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                while (en.hasMoreElements())
                                {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    if (name.toLowerCase().equals("class"))
                                    {
                                        if (value.toLowerCase().equals("section" + page))
                                        {
                                            flag = true;
                                        }
                                    }
                                    else if (flag)
                                    {
                                        flag2 = true;
                                    }
                                }
                            }
                            else
                            {
                                if (flag && !flag2)
                                {
                                    //entra a este if y se rompe el ciclo solo si la página actual es menos al total de páginas encontradas en el documento,
                                    //si es igual, entonces no lo rompe y se termina hasta que se acaba el html, para que funcionen los pie de página si existen
                                    //al final del dicumento
                                    if (page < itpages)
                                    {
                                        break;
                                    }
                                }
                                else if (flag && flag2)
                                {
                                    flag2 = false;
                                }
                            }
                        }
                        else if (flag1 && flag)
                        {
                            if (tag.getTagString().toLowerCase().equals("a"))
                            {
                                if (!tag.isEndTag())
                                {
                                    Enumeration en = tag.getParamNames();
                                    String name = "";
                                    String value = "";
                                    String actionval = "";
                                    while (en.hasMoreElements())
                                    {
                                        name = (String) en.nextElement();
                                        value = tag.getParam(name);
                                        if (name.toLowerCase().equals("name") && value.equals(ancla))
                                        { //encontrado
                                            hAnchors.put(value, new Integer(page));
                                            return true;
                                        }
                                        else if (name.toLowerCase().equals("name") && value.startsWith("_"))
                                        { //es una ancla, guardarla en hash de anclas
                                            if (hAnchors.get(value) == null)
                                            {
                                                hAnchors.put(value, new Integer(page));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
            return false;
        }

        /**
         * Finds file paths in a string and replaces them with the path indicated in {@code ruta}.
         * It searches for the file paths whose file extension equals {@code ext}.
         * <p>Encuentra rutas de arhcivos en una string y las remplaza con la ruta indicada
         * en {@code ruta}. Busca las rutas de los archivos cuya extensi&oacute; equivale a {@code ext}.</p>
         * @param value the string containing the text to look into
         * @param ext the string representing the file extension to look for
         * @param ruta the string with the new path that will replace the found ones
         * @return a string like {@code value} but with the file paths replaced with {@code ruta}
         *         for the file paths containing the file extension {@code ext}
         */
        private static String findImagesInScript(String value, String ext, String ruta)
        {
            StringBuilder aux = new StringBuilder(value.length());
            int off = 0;
            int f = 0;
            int i = 0;
            int j = 0;
            do
            {
                f = value.indexOf(ext, off);
                i = value.lastIndexOf("'", f);
                j = value.lastIndexOf("/", f);
                if (f > 0 && i >= 0)
                {
                    i++;
                    if (value.startsWith("/", i) || value.startsWith("http://", i))
                    {
                        aux.append(value.substring(off, f + ext.length()));
                    }
                    else if (j > -1)
                    {
                        aux.append(value.substring(off, i)).append(ruta).append(value.substring(j + 1, f + ext.length()));
                    }
                    else
                    {
                        aux.append(value.substring(off, i)).append(ruta).append(value.substring(i, f + ext.length()));
                    }
                    off = f + ext.length();
                }
            }
            while (f > 0 && i > 0);
            aux.append(value.substring(off));
            return aux.toString();
        }

        /**
         * Looks for relative paths in the attributes of HTML tags and replaces
         * their values with {@code ruta}.
         * The HTML tags it evaluates are: {@code img}, {@code applet}, {@code script}, {@code table},
         * 
         * @param datos the string containing the HTML code to parse
         * @param ruta the string representing the replacing path
         * @return a string with the HTML code with relative paths modified.
         * {@code td}, {@code body}, {@code form}, {@code input}, {@code a},
         * {@code meta}, {@code bca}, {@code link}, {@code param}, {@code embed}, {@code iframe} and {@code frame} and
         * the attributes it evaluates are: {@code src}, {@code href}, {@code background}, {@code codebase}, {@code value},
         * {@code onmouseover}, {@code onload}, {@code onmouseout} and {@code onclick}.
         * <p>Busca rutas relativas en los atributos de los tags de HTML y remplaza
         * sus valores por el de {@code ruta}. Los tags de HTML que eval&uacute;a son:
         * {@code img}, {@code applet}, {@code script}, {@code table}, {@code td},
         * {@code body}, {@code form}, {@code input}, {@code a}, {@code meta}, {@code bca},
         * {@code link}, {@code param}, {@code embed}, {@code iframe} y {@code frame}
         * y los atributos que eval&uacute;a son: {@code src}, {@code href}, {@code background},
         * {@code codebase}, {@code value}, {@code onmouseover}, {@code onload},
         * {@code onmouseout} and {@code onclick}.</p>
         */
        public static String parseXsl(String datos, String ruta)
        {
            HtmlTag tag = new HtmlTag();
            int pos = -1;
            int pos1 = -1;
            StringBuilder ret = new StringBuilder();
            try
            {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
                {
                    int ttype = tok.getTokenType();
                    //if (ttype==HtmlStreamTokenizer.TT_COMMENT) continue;
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                    {
                        if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->"))
                        {
                            continue;
                        }
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if"))
                        {
                            continue;
                        }
                        //if (tag.getTagString().toLowerCase().startsWith("o:")){System.out.println("o:Salto");   continue;}
                        if (tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("form") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("meta") || tag.getTagString().toLowerCase().equals("bca") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed") || tag.getTagString().toLowerCase().equals("iframe") || tag.getTagString().toLowerCase().equals("frame"))
                        {

                            if (!tag.isEndTag())
                            {
                                ret.append("<");
                                ret.append(tag.getTagString());
                                ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                String actionval = "";
                                while (en.hasMoreElements())
                                {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    ret.append(name);
                                    ret.append("=\"");
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("{"))
                                    {
                                        if (!tag.getTagString().toLowerCase().equals("input") && !value.toLowerCase().equals("true") && !value.toLowerCase().equals("false") && value.indexOf(".") > -1)
                                        {
                                            ret.append(ruta);
                                        }
                                        //poner solo archivo
                                        if (((pos = value.indexOf("/")) > -1) || (pos = value.indexOf("\\")) > -1)
                                        {
                                            value = findFileName(value);
                                        }
                                    }
                                    if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick"))
                                    {
                                        String out = findImagesInScript(value, ".gif'", ruta);
                                        out = findImagesInScript(out, ".jpg'", ruta);
                                        //ret.append(ruta);
                                        if (!out.equals(""))
                                        {
                                            value = out;
                                        }
                                    }
                                    ret.append(value);
                                    ret.append("\" ");
                                }
                                //if(tag.getTagString().toLowerCase().equals("img") && tok.getStringValue().toString().endsWith("/")) {
                                if (tag.isEmpty())
                                {
                                    ret.append("/");
                                }

                                ret.append(">");

                                if (tag.getTagString().toLowerCase().equals("form"))
                                {
                                    ret.append(actionval);
                                }
                            }
                            else
                            {
                                ret.append(tok.getRawString());
                            }
                        }
                        else
                        {
                            ret.append(tok.getRawString());
                        }
                    }
                    else if (ttype == HtmlStreamTokenizer.TT_TEXT)
                    {
                        ret.append(tok.getRawString());
                    }
                }
            }
            catch (NumberFormatException f)
            {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_decodifica"), f);
            }
            catch (Exception e)
            {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_IOHTML"), e);
            }
            return ret.toString();
        }
        
        /**
         * Find attaches from css.
         * 
         * @param cssbody the cssbody
         * @return the string[]
         */
        public static String[] findAttachesFromCss(String cssbody)
        {
            HashSet<String> findAttachesFromCss=new HashSet<String>();
            try
            {
                CSSParser p=new CSSParser(cssbody);
                for (Selector selector : p.getSelectors())
                {
                    for (Attribute att : selector.getAttributes())
                    {
                        if (att.getName().equals("background-image") || att.getName().equals("background") || att.getName().equals("list-style"))
                        {
                            for (String value : att.getValues())
                            {
                                if (value.startsWith("url("))
                                {
                                    value = value.substring(4);
                                    int pos = value.indexOf(")");
                                    if (pos != -1)
                                    {
                                        value = value.substring(0, pos).trim();

                                        if (value.indexOf(".") != -1 && !value.startsWith("http://") && !value.toLowerCase().startsWith("wbrelpath://") && !value.startsWith("https://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("rtsp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("../"))
                                        {
                                            if(value.startsWith("\"") && value.endsWith("\""))
                                            {
                                                value=value.substring(1,value.length()-1);
                                            }
                                            if(value.startsWith("'") && value.endsWith("'"))
                                            {
                                                value=value.substring(1,value.length()-1);
                                            }
                                            findAttachesFromCss.add(value);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch(Throwable e)
            {
                log.error(e);
            }
            
            return findAttachesFromCss.toArray(new String[findAttachesFromCss.size()]);
        }

        /**
         * Detects the relative file paths included in {@code datos} and returns them in
         * a semicolon separated string. The file paths are searched for in the following tags:
         * 
         * @param datos the string representing the HTML code to analize.
         * @return a semicolon separated string with the file paths retrieved
         * {@code img}, {@code applet}, {@code script}, {@code td}, {@code table}, {@code body}, {@code input},
         * {@code a}, {@code area}, {@code link}, {@code param}, {@code embed} and in the attributes:
         * {@code src}, {@code href}, {@code background}, {@code codebase}, {@code value} and {@code onmouseover}.
         * <p>Detecta las rutas relativas inclu&iacute;das en {@code datos} y las
         * devuelve en una string separada por punto y comas (;). Las rutas de archivo
         * son buscadas en los siguientes tags: {@code img}, {@code applet}, {@code script},
         * {@code td}, {@code table}, {@code body}, {@code input}, {@code a}, {@code area}, {@code link},
         * {@code param}, {@code embed}; y en los atributos: {@code src}, {@code href},
         * {@code background}, {@code codebase}, {@code value} and {@code onmouseover}.</p>
         */
        public static String FindAttaches(String datos)
        {
            HtmlTag tag = new HtmlTag();
            StringBuilder ret = new StringBuilder();
            ArrayList<String> vvector = new ArrayList<String>();
            try
            {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
                {

                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                    {
                        tok.parseTag(tok.getStringValue(), tag);

                        if (tok.getRawString().toLowerCase().startsWith("<!--[if"))
                        {
                            continue;
                        }
                        //if (tag.getTagString().toLowerCase().startsWith("o:")){System.out.println("o:Salto");   continue;}
                        if (tag.getTagString().toLowerCase().equals("img") || tag.getTagString().toLowerCase().equals("applet") || tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("td") || tag.getTagString().toLowerCase().equals("table") || tag.getTagString().toLowerCase().equals("body") || tag.getTagString().toLowerCase().equals("input") || tag.getTagString().toLowerCase().equals("a") || tag.getTagString().toLowerCase().equals("area") || tag.getTagString().toLowerCase().equals("link") || tag.getTagString().toLowerCase().equals("param") || tag.getTagString().toLowerCase().equals("embed"))
                        {
                            if (!tag.isEndTag())
                            {
                                //ret.append("<");
                                //ret.append(tag.getTagString());
                                //ret.append(" ");
                                Enumeration en = tag.getParamNames();
                                String name = "";
                                String value = "";
                                while (en.hasMoreElements())
                                {
                                    name = (String) en.nextElement();
                                    value = tag.getParam(name);
                                    String out = null;
                                    if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && !value.startsWith("{"))
                                    {
                                        String stype = "";
                                        if (tag.getTagString().toLowerCase().equals("input"))
                                        {
                                            stype = tag.getParam("type").toLowerCase();
                                        }
                                        if (!value.startsWith("http://") && !value.startsWith("https://") && (!tag.getTagString().toLowerCase().equals("input") || (tag.getTagString().toLowerCase().equals("input") && stype.equals("image"))))
                                        {
                                            if (value.toLowerCase().endsWith(".js") || value.toLowerCase().endsWith(".png") || value.toLowerCase().endsWith(".gif") || value.toLowerCase().endsWith(".jpg") || value.toLowerCase().endsWith(".jpeg") || value.toLowerCase().endsWith(".bmp")
                                                    || value.toLowerCase().endsWith(".doc") || value.toLowerCase().endsWith(".htm") || value.toLowerCase().endsWith(".html") || value.toLowerCase().endsWith(".zip")
                                                    || value.toLowerCase().endsWith(".txt") || value.toLowerCase().endsWith(".pdf") || value.toLowerCase().endsWith(".xls") || value.toLowerCase().endsWith(".ppt")
                                                    || value.toLowerCase().endsWith(".xsl") || value.toLowerCase().endsWith(".xslt") || value.toLowerCase().endsWith(".bin") || value.toLowerCase().endsWith(".tar") || value.toLowerCase().endsWith(".css"))
                                            {
                                                out = value;
                                            }
                                        }
                                    }
                                    else if ((name.toLowerCase().equals("src") || name.toLowerCase().equals("href") || name.toLowerCase().equals("background") || name.toLowerCase().equals("codebase") || name.toLowerCase().equals("value")) && !value.startsWith("http://") && !value.startsWith("mailto:") && !value.startsWith("javascript:") && !value.startsWith("ftp:") && !value.startsWith("telnet:") && !value.startsWith("#") && !value.startsWith("/") && value.startsWith("{"))
                                    {
                                        int pos = -1;
                                        pos = value.indexOf("}");
                                        if (pos != -1)
                                        {
                                            out = value.substring(pos + 1);
                                        }
                                    }
                                    else if (name.toLowerCase().equals("href") && value.startsWith("/"))
                                    {
                                        out = value;
                                    }
                                    else if (name.toLowerCase().equals("onmouseover"))
                                    {
                                        //if(!value.startsWith("http://") && !value.startsWith("https://"))
                                        int pos = -1, pos1 = -1;
                                        pos = value.indexOf("http://");
                                        pos1 = value.indexOf("https://");
                                        if (pos < 0 && pos1 < 0)
                                        {
                                            out = findImageInScript1(value, ".gif'", "");
                                            out = findImageInScript1(out, ".jpg'", "");
                                        }
                                    }
                                    if (out != null)
                                    {
                                        boolean flag = false;
                                        for (int i = 0; i < vvector.size(); i++)
                                        {
                                            if (out.equals((String) vvector.get(i)))
                                            {
                                                flag = true;
                                            }
                                        }
                                        if (!flag)
                                        {
                                            vvector.add(out);
                                        }
                                    }

                                    //ret.append("\" ");
                                }
                                //ret.append(">");
                                //if(tag.getTagString().toLowerCase().equals("form")) ret.append(actionval);
                            }
                            else
                            {
                                //ret.append(tok.getRawString());
                            }
                        }
                        else
                        {
                            //ret.append(tok.getRawString());
                        }
                    }
                    else if (ttype == HtmlStreamTokenizer.TT_TEXT)
                    {
                        //ret.append(tok.getRawString());
                    }
                }
                for (int i = 0; i < vvector.size(); i++)
                {
                    ret.append((String) vvector.get(i)).append(";");
                }
            }
            catch (NumberFormatException f)
            {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_decodifica"), f);
            }
            catch (Exception e)
            {
                log.error(SWBUtils.TEXT.getLocaleString("locale_swb_util", "error_WBUtils_IOHTML"), e);
            }
            return ret.toString();
        }

        /**
         * Finds a file path in a string whose file extension equals {@code ext}.
         * If there's no file path in {@code value} with the specified extension,
         * or if {@code value} starts with {@literal /} or {@literal http}, then
         * 
         * @param value a string that might contain a file path
         * @param ext a string representing a file extension to look for
         * @param ruta a string that is not used in this method, so don't bother
         * @return a string with the local file path found with extension {@code ext}
         * {@code value} is returned intact. <p>Encuentra una ruta de archivo cuya
         * extensi&oacute;n equivalga a {@code ext}. Si {@code value} no contiene
         * una ruta con la extensi&oacute;n de archivo especificada o si {@code value}
         * inicia con {@literal /} o {@literal http}, entonces el valor de {@code value}
         * regresa intacto.</p>
         */
        private static String findImageInScript1(String value, String ext, String ruta)
        {
            int f = value.indexOf(ext);
            int i = value.lastIndexOf("'", f);
            int j = value.lastIndexOf("'");
            if (f > 0 && i >= 0)
            {
                i++;
                if (value.startsWith("/", i) || value.startsWith("http://", i))
                {
                    return value;
                }
                else
                {
                    return value.substring(i, j);
                }
            }
            return value;
        }

        /**
         * Generates a string of {@code size} characters selected in a random basis.
         * <p>Genera una string con {@code size} caracteres seleccionados en orden aleatorio.</p>
         * @param size the number of characters the resulting string will contain
         * @return a string with {@code size} characters selected in a random basis.
         *         If {@code size} equals zero the returning string will be empty,
         *         and if {@code size} is less than zero an exception will be thrown.
         * @throws NegativeArraySizeException if the {@code size} argument is less than zero.
         */
        public static String getRandString(int size)
        {
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++)
            {
                sb.append(ALPHABETH.charAt((int) (Math.random() * ALPHABETH.length())));
            }
            return sb.toString();
        }
        
        /**
         * Generates a string of {@code size} characters selected in a random basis.
         * <p>Genera una string con {@code size} caracteres seleccionados en orden aleatorio.</p>
         * @param size the number of characters the resulting string will contain
         * @param alphabeth set of characters the resulting string will contain
         * @return a string with {@code size} characters selected in a random basis.
         *         If {@code size} equals zero the returning string will be empty,
         *         and if {@code size} is less than zero an exception will be thrown.
         * @throws NegativeArraySizeException if the {@code size} argument is less than zero.
         */
        public static String getRandString(int size, final String alphabeth)
        {
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++)
            {
                sb.append(alphabeth.charAt((int) (Math.random() * alphabeth.length())));
            }
            return sb.toString();
        }

        /**
         * Generates an image representing a string and embeds it in a {@code HttpServletResponse}.
         * The represented string can be provided by the {@code cad} argument or if
         * this argument is {@code null} the string is randomly generated. Additionally,
         * a request attribute with name {@code swb_valCad} is generated and its value
         * is equal to the string represented in the image embedded in the HTTP response.
         * <p>Genera una imagen representando una string y la incrusta en una {@code HttpServletResponse}.
         * La string representada puede ser proporcionada mediante el argumento {@code cad}, o
         * si este argumento es {@code null} la string se genera aleatoriamente. Adicionalmente,
         * genera un atributo de petici&oacute;n -en {@code request}- con nombre
         * 
         * @param request a HTTP request to respond
         * @param response the HTTP response that will contain the generated image
         * @param size the number of characters the generated string will contain, if {@code cad} is {@code null}
         * @param cad the string to represent in an image
         * @throws ServletException the servlet exception
         * @throws IOException Signals that an I/O exception has occurred.
         * {@code swb_valCad} y con valor igual a la string representada por la imagen.</p>
         */
        public static void sendValidateImage(HttpServletRequest request, HttpServletResponse response, int size, String cad) throws ServletException, IOException
        {
            sendValidateImage(request, response, null, size, cad);
        }



        /**
         * Generates an image representing a string and embeds it in a {@code HttpServletResponse}.
         * The represented string can be provided by the {@code cad} argument or if
         * this argument is {@code null} the string is randomly generated. Additionally,
         * a request attribute with name determined by the {@code attributeName} argument
         * is generated and its value is equal to the string represented in the image
         * embedded in the HTTP response. <p>Genera una imagen que representa una string
         * y la inserta en una {@code HttpServletResponse}. La string representada
         * puede ser proporcionada mediante el argumento {@code cad} o si este argumento
         * es {@code null} los caracteres que conforman la string son seleccionados
         * aleatoriamente. Adicionalmente, genera un atributo de petici&oacute;n
         * en {@code request} con el nombre determinado por el argumento
         * 
         * @param request a HTTP request to respond
         * @param response the HTTP response that will contain the generated image
         * @param attributeName a string with the name the request attribute is going to be
         * @param size the number of characters the generated string will contain, if {@code cad} is {@code null}
         * @param cad the string to represent in an image
         * @throws ServletException the servlet exception
         * @throws IOException Signals that an I/O exception has occurred.
         * {@code attributeName} y su valor es igual a la cadena representada en la imagen.</p>
         */
        public static void sendValidateImage(HttpServletRequest request, HttpServletResponse response, String attributeName, int size, String cad) throws ServletException, IOException
        {

            String cadena = null;
            if (null == cad)
            {
                cadena = getRandString(size);
            }
            else
            {
                cadena = cad;
            }
            if (null == attributeName)
            {
                attributeName = "swb_valCad";
            }

            request.getSession(true).setAttribute(attributeName, cadena);
            response.setContentType("image/png");
            CaptchaUtil.writeCaptcha(cadena, response.getOutputStream());
        }

      
        /**
         * Render gallery.
         * 
         * @param imgurl the imgurl
         * @return the string
         */
        public static String renderGallery(String... imgurl)
        {
            ImageGallery ig = new ImageGallery();
            return ig.renderGallery(imgurl);
        }

        /**
         * Install zip.
         * 
         * @param zipFile the zip file
         * @return the web site
         */
        public static WebSite InstallZip(File zipFile)
        {
            return InstallZip(zipFile, null, null, null);
        }

        /**
         * Install zip.
         * 
         * @param zipFile the zip file
         * @param file2read the file2read
         * @return the web site
         */
        public static WebSite InstallZip(File zipFile, String file2read)
        {
            return InstallZip(zipFile, file2read, null, null);
        }

        /**
         * Install zip.
         * 
         * @param zipFile the zip file
         * @param file2read the file2read
         * @param newWebSiteid the new web siteid
         * @param newWebSiteTitle the new web site title
         * @return the web site
         */
        public static WebSite InstallZip(File zipFile, String file2read, String newWebSiteid, String newWebSiteTitle)
        {
            try
            {
                String modelspath = SWBPortal.getWorkPath() + "/models/";
                if (file2read == null)
                {
                    file2read = "siteInfo.xml";
                }
                String siteInfo = SWBUtils.IO.readFileFromZipAsString(zipFile.getAbsolutePath(), file2read);
                String oldIDModel = null, oldNamespace = null, oldTitle = null, oldDescription = null;
                Document dom = SWBUtils.XML.xmlToDom(siteInfo);
                Node nodeModel = dom.getFirstChild();
                if (nodeModel.getNodeName().equals("model"))
                {
                    HashMap smodels = new HashMap();
                    NodeList nlChilds = nodeModel.getChildNodes();
                    for (int i = 0; i < nlChilds.getLength(); i++)
                    {
                        Node node = nlChilds.item(i);
                        if (node.getNodeName().equals("id"))
                        {
                            oldIDModel = node.getFirstChild().getNodeValue();
                        }
                        if (node.getNodeName().equals("namespace"))
                        {
                            oldNamespace = node.getFirstChild().getNodeValue();
                        }
                        if (node.getNodeName().equals("title"))
                        {
                            oldTitle = node.getFirstChild().getNodeValue();
                        }
                        if (node.getNodeName().equals("description"))
                        {
                            oldDescription = node.getFirstChild().getNodeValue();
                        }
                        if (node.getNodeName().equals("model"))
                        { //Tiene submodelos - un submodelo puede inclusive tener submodelos, esto tiene que ser iterativo
                            iteraModels(node, smodels);
                        }
                    }

                    String newId = newWebSiteid;
                    if (newId == null)
                    {
                        newId = oldIDModel;
                    }
                    String newTitle = newWebSiteTitle;
                    if (newTitle == null)
                    {
                        newTitle = oldTitle;
                        newWebSiteTitle = oldTitle;
                    }
                    java.io.File extractTo = new File(modelspath + newId);
                    //Descomprimir zip
                    org.semanticwb.SWBUtils.IO.unzip(zipFile, extractTo);
                    //Mover directorios de modelos a directorio work leyendo rdfs
                    File[] fieldsUnziped = extractTo.listFiles();
                    for (int i = 0; i < fieldsUnziped.length; i++)
                    {
                        File file = fieldsUnziped[i];
                        if (file.isDirectory())
                        { //
                            if (file.getName().equals(oldIDModel))
                            { //Es la carpeta del modelo principal a cargar
                                SWBUtils.IO.copyStructure(file.getAbsolutePath() + "/", extractTo.getAbsolutePath() + "/");
                                SWBUtils.IO.removeDirectory(file.getAbsolutePath());
                            }
                            else
                            {
                                if (file.getName().endsWith("_usr") || file.getName().endsWith("_rep"))
                                {
                                    //las carpetas de los submodelos, predefinidos en wb
                                    String wbmodelType = "";
                                    if (file.getName().endsWith("_usr"))
                                    {
                                        wbmodelType = "_usr";
                                    }
                                    if (file.getName().endsWith("_rep"))
                                    {
                                        wbmodelType = "_rep";
                                    }

                                    SWBUtils.IO.copyStructure(file.getAbsolutePath(), extractTo.getAbsolutePath() + wbmodelType + "/");
                                    SWBUtils.IO.removeDirectory(file.getAbsolutePath());
                                }
                                else
                                { //Puede ser un submodelo tipo sitio
                                    //TODO
                                }
                            }
                        }
                        else
                        { //TODO:Archivos rdf(modelos) y xml (siteinfo) y readme, eliminarlos
                            String fileName = file.getName();
                            if (fileName.equalsIgnoreCase(file2read) || fileName.equals("readme.txt"))
                            { //Archivo siteinfo
                                file.delete();
                            }
                        }
                    }
                    //Parseo de nombre de NameSpace anteriores por nuevos
                    String newNs = "http://www." + newId + ".swb#";
                    File fileModel = new File(modelspath + newId + "/" + oldIDModel + ".nt");
                    FileInputStream frdfio = new FileInputStream(fileModel);
                    String rdfcontent = SWBUtils.IO.readInputStream(frdfio);
                    fileModel.delete();

                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, oldNamespace, newNs); //Reemplazar namespace anterior x nuevo
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, newNs + oldIDModel, newNs + newId); //Reempplazar namespace y id anterior x nuevos

                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, "<topicmap id=\\\"" + oldIDModel + "\\\">", "<topicmap id=\\\"" + newId + "\\\">"); // Rempalzar el tag: <topicmap id=\"[oldIDModel]\"> del xml de filtros de recursos
                    //Reemplaza ids de repositorios de usuarios y documentos x nuevos
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, oldIDModel + "_usr", newId + "_usr");
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, "http://user." + oldIDModel + ".swb#", "http://user." + newId + ".swb#");
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, oldIDModel + "_rep", newId + "_rep");
                    rdfcontent = SWBUtils.TEXT.replaceAll(rdfcontent, "http://repository." + oldIDModel + ".swb#", "http://repository." + newId + ".swb#");

                    //rdfcontent = SWBUtils.TEXT.replaceAllIgnoreCase(rdfcontent, oldName, newName); //Reemplazar nombre anterior x nuevo nombre
                    //rdfcontent = parseRdfContent(rdfcontent, oldTitle, newTitle, oldIDModel, newId, newNs);

                    //Mediante inputStream creado generar sitio
                    InputStream io = SWBUtils.IO.getStreamFromString(rdfcontent);
                    SemanticModel model = SWBPlatform.getSemanticMgr().createDBModelByRDF(newId, newNs, io, "N-TRIPLE");
                    WebSite website = SWBContext.getWebSite(model.getName());
                    website.setTitle(newTitle);
                    website.setDescription(oldDescription);
                    String xmodelID = null;
                    Iterator smodelsKeys = smodels.keySet().iterator();
                    while (smodelsKeys.hasNext())
                    { // Por c/submodelo que exista
                        String key = (String) smodelsKeys.next();
                        HashMap smodelValues = (HashMap) smodels.get(key);
                        Iterator itkVaues = smodelValues.keySet().iterator();
                        while (itkVaues.hasNext())
                        {
                            String kvalue = (String) itkVaues.next();
                            if (kvalue.equals("id"))
                            {
                                xmodelID = (String) smodelValues.get(kvalue);
                            }
                        }
                        //Buscar rdf del submodelo
                        fileModel = new File(modelspath + newId + "/" + xmodelID + ".nt");
                        if (fileModel != null && fileModel.exists())
                        {
                            frdfio = new FileInputStream(fileModel);
                            String rdfmodel = SWBUtils.IO.readInputStream(frdfio);
                            if (key.endsWith("_usr"))
                            { //Para los submodelos de usuarios
                                int pos = xmodelID.lastIndexOf("_usr");
                                if (pos > -1)
                                {
                                    xmodelID = xmodelID.substring(0, pos);
                                    rdfmodel = SWBUtils.TEXT.replaceAll(rdfmodel, xmodelID, newId);
                                    io = SWBUtils.IO.getStreamFromString(rdfmodel);
                                    SemanticModel usermodel = SWBPlatform.getSemanticMgr().createDBModelByRDF(newId + "_usr", "http://user." + newId + ".swb#", io, "N-TRIPLE");
                                    if (usermodel != null)
                                    {
                                        UserRepository userRep = SWBContext.getUserRepository(usermodel.getName());
                                        userRep.setTitle("Repositorio de Usuarios (" + newWebSiteTitle + ")", "es");
                                        userRep.setTitle("Users Repository (" + newWebSiteTitle + ")", "en");
                                    }
                                }
                            }
                            if (key.endsWith("_rep"))
                            { //Para los submodelos de dosumentos
                                int pos = xmodelID.lastIndexOf("_rep");
                                if (pos > -1)
                                {
                                    xmodelID = xmodelID.substring(0, pos);
                                    rdfmodel = SWBUtils.TEXT.replaceAll(rdfmodel, xmodelID, newId);
                                    io = SWBUtils.IO.getStreamFromString(rdfmodel);
                                    SemanticModel repomodel = SWBPlatform.getSemanticMgr().createDBModelByRDF(newId + "_rep", "http://repository." + newId + ".swb#", io, "N-TRIPLE");
                                    if (repomodel != null)
                                    {
                                        Workspace repo = SWBContext.getWorkspace(repomodel.getName());
                                        repo.setTitle("Repositorio de Documentos (" + newWebSiteTitle + ")", "es");
                                        repo.setTitle("Documents Repository (" + newWebSiteTitle + ")", "en");
                                    }
                                }
                            }
                            fileModel.delete();
                        }
                    }

                    if(website!=null)
                    {
                        Iterator<ResourceType> it=website.listResourceTypes();
                        while (it.hasNext())
                        {
                            ResourceType resourceType = it.next();
                            if(resourceType!=null)
                            {
                                try
                                {
                                    getResourceMgr().loadResourceTypeModel(resourceType);
                                }catch(Exception e){log.error(""+resourceType,e);}
                            }
                        }
                        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
                        it=website.listResourceTypes();
                        while (it.hasNext())
                        {
                            ResourceType resourceType = it.next();
                            if(resourceType!=null)
                            {
                                try
                                {
                                    //Runtime.getRuntime().loadLibrary(oldTitle)
                                    Class cls=SWBPortal.getResourceMgr().createSWBResourceClass(resourceType.getResourceClassName());
                                    if(cls!=null)
                                    {
                                        SWBResource res=((SWBResource)SWBPortal.getResourceMgr().convertOldWBResource(cls.newInstance()));
                                        if(res!=null)
                                        {
                                            res.install(resourceType);
                                        }
                                    }
                                }catch(Exception e){log.error(""+resourceType,e);}
                            }
                        }
                    }
                    return website;
                }
            }
            catch (Exception e)
            {
                log.error("Check if you have the necesary owl files in the owl directory", e);
            }
            return null;
        }

        public static String getIPCountryCode(String ip){
            String ret = null;
            if (ip.indexOf(":")>-1)
            {
                ret = SWBPortal.geoip.getCountryV6(ip).getCode();
            }else
            {
                ret = SWBPortal.geoip.getCountry(ip).getCode();
            }
            return ret;
        }

        public static String getIPCountryName(String ip){
            String ret = null;
            if (ip.indexOf(":")>-1)
            {
                ret = SWBPortal.geoip.getCountryV6(ip).getName();
            }else
            {
                ret = SWBPortal.geoip.getCountry(ip).getName();
            }
            return ret;
        }

        public static com.maxmind.geoip.Country getIPCountry(String ip){
            com.maxmind.geoip.Country ret = null;
            if (ip.indexOf(":")>-1)
            {
                ret = SWBPortal.geoip.getCountryV6(ip);
            }else
            {
                ret = SWBPortal.geoip.getCountry(ip);
            }
            return ret;
        }
    }

    /**
     * Removes from the HTML code in {@code content}, the heading and body tags and
     * replaces the existing styles. The CSS styles in {@code content} are replaced
     * with those belonging to the set of name equal to {@code tmid} whose detail
     * is contained in {@code hTMhStyleObjs}. <p>Elimina del c&oacute;digo HTML en
     * 
     * @param content a string with the HTML code to modify
     * @param tmid a string representing the name of the style set to use
     * @param hTMhStyleObjs the set of CSS styles available
     * @return a string similar to content but without the heading and body tags
     * and the styles modified according to the arguments received. If
     * {@code content}, los tags de encabezado y cuerpo del documento y remplaza
     * los estilos existentes. Los estilos CSS en {@code content} se remplazan con
     * los que pertenecen al conjunto de nombre igual a {@code tmid} cuyo detalle
     * lo contiene {@code hTMhStyleObjs}.</p>
     * {@code content} equals {@code null}, the returned value is an empty string.
     */
    public static String removeStylesOutDivs(String content, String tmid, HashMap hTMhStyleObjs)
    {
        StringBuilder ret = new StringBuilder();
        try
        {
            boolean title = false;
            boolean style = false;
            HtmlTag tag = new HtmlTag();
            HtmlStreamTokenizer tok = new HtmlStreamTokenizer(SWBUtils.IO.getStreamFromString(content));
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
            {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_COMMENT || ttype == HtmlStreamTokenizer.TT_TEXT)
                {
                    if (!title)
                    {
                        if (style)
                        {
                            ret.append(parseStyles2(tok.getRawString(), tmid, hTMhStyleObjs));
                        }
                        else
                        {
                            ret.append(tok.getRawString());
                        }
                    }
                }
                else if (ttype == HtmlStreamTokenizer.TT_TAG)
                {
                    tok.parseTag(tok.getStringValue(), tag);

                    String tagname = tag.getTagString();
                    String tname = tag.getTagString().toLowerCase();


                    if (true) //validar si se requiere eliminar tags de
                    {
                        if (tname.equals("title"))
                        {
                            if (tag.isEndTag())
                            {
                                title = false;
                            }
                            else
                            {
                                title = true;
                            }
                        }
                        if (tname.equals("html") || tname.equals("title") || tname.equals("head") || tname.equals("meta") || tname.equals("link") || tname.startsWith("o:") || tname.startsWith("st1:"))
                        {
                            continue;
                        }

                        if (tname.equals("body"))
                        {
                            if (!tag.isEndTag())
                            {
                                tagname = "DIV";
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

                    if (tag.isEndTag())
                    {
                        ret.append(tok.getRawString());
                        if (tname.equals("style"))
                        {
                            style = false;
                        }
                    }
                    else
                    {
                        ret.append("<");
                        ret.append(tagname);

                        if (tname.equals("style"))
                        {
                            tag.setParam("type", "text/css");
                            style = true;
                        }
                        else if (tname.equals("img"))
                        {
                            if (!tag.hasParam("alt"))
                            {
                                tag.setParam("alt", "");
                            }
                            if (!tag.hasParam("longdesc"))
                            {
                                tag.setParam("longdesc", "#");
                            }
                            tag.removeParam("v:shapes");
                        }

                        Enumeration en = tag.getParamNames();
                        while (en.hasMoreElements())
                        {
                            String name = (String) en.nextElement();
                            String value = tag.getParam(name);
                            if (name.toLowerCase().equals("style"))
                            {
                                ret.append(" ").append(name).append("='");
                                StringTokenizer st = new StringTokenizer(value, ";");
                                while (st.hasMoreTokens())
                                {
                                    String token = st.nextToken();
                                    String aux = token.toLowerCase().trim();
                                    if (!(aux.startsWith("font:") || aux.startsWith("font-")))// || aux.startsWith("color")))
                                    {
                                        ret.append(token).append(";");
                                    }
                                }
                                ret.append("\'");
                            }
                            else
                            {
                                ret.append(" ").append(name).append("=\"").append(value).append("\"");
                            }
                        }
                        ret.append(">");
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return ret.toString();
    }

    /**
     * Modifies some CSS styles in {@code styles} for those specified in.
     * 
     * @param styles a string containing the CSS styles to modify
     * @param tmid a string representing the identifier for the style set to use
     * @param hTMhStyleObjs a hash map containing the set of styles available
     * @return a string with the CSS style code in {@code styles} but whith the CSS styles modified.
     * The styles modified will be those defined for the font type used in {@literal },
     * {@code hTMhStyleObjs} whose identifier equals {@code tmid}. <p>Modifica
     * algunos estilos CSS en {@code styles} por los que est&aacute;n especificados
     * en {@code hTMhStyleObjs} cuyo identificador lo define {@code tmid}.</p>
     * {@literal <li>} and {@literal <div>} HTML tags according to the styles
     * contained in {@code hTMhStyleObjs}.
     */
    private static String parseStyles2(String styles, String tmid, HashMap hTMhStyleObjs)
    {
        styles = parseStyles(styles, tmid, hTMhStyleObjs);

        HashMap hStyleObjs = (HashMap) hTMhStyleObjs.get(tmid);
        if (hStyleObjs != null)
        {
            ContentStyles contentStyle = (ContentStyles) hStyleObjs.get("MsoNormal");
            if (contentStyle != null)
            {
                Iterator it = SWBUtils.TEXT.findInterStr(styles, "p.", ", ");
                while (it.hasNext())
                {
                    String name = ((String) it.next()).trim();
                    if (name.indexOf('\n') == -1)
                    {
                        if (!hStyleObjs.containsKey(name))
                        {
                            styles = findStyles(styles, name, contentStyle.getFont(), contentStyle.getSize(), contentStyle.getColor());
                        }
                    }
                }
            }
        }
        return styles;
    }

    /**
     * Replaces from {@code content} the styles defined for the {@literal <p>},.
     * 
     * @param content the string representing the CSS styles to be modified
     * @param tmid a string representing the set of styles in {@code hTMhStyleObjs} to use
     * @param hTMhStyleObjs a hash map containing the set of styles available
     * @return a string similar to {@code content} but with some styles modified.
     * The styles modified will be those defined in {@code content} for the {@literal },
     * {@literal <li>} and {@literal <div>} HTML tags with those specified in {@code hTMhStyleObjs}.
     * <p>Remplaza de {@code content} los estilos definidos para los tags de HTML: {@literal <p>},
     * {@literal <li>} and {@literal <div>} con los especificados en {@code hTMhStyleObjs}.</p>
     * {@literal <li>} and {@literal <div>} HTML tags according to the styles
     * contained in {@code hTMhStyleObjs}.
     */
    private static String parseStyles(String content, String tmid, HashMap hTMhStyleObjs)
    {

        HashMap hStyleObjs = (HashMap) hTMhStyleObjs.get(tmid);
        if (hStyleObjs != null && hStyleObjs.size() > 0)
        { //encontro estilos
            Iterator iStyles = hStyleObjs.keySet().iterator();
            while (iStyles.hasNext())
            {
                String styleName = (String) iStyles.next();
                if (styleName != null && styleName.trim().length() > 0)
                {
                    ContentStyles contentStyle = (ContentStyles) hStyleObjs.get(styleName);
                    if (contentStyle.getName() != null && (contentStyle.getFont() != null || contentStyle.getSize() != null || contentStyle.getColor() != null))
                    {
                        String sname = styleName;             //contentStyle.getName(); //cambio para soportar multiples nombres
                        if (!sname.equals("h1") && !sname.equals("h2") && !sname.equals("h3"))
                        {
                            sname = "p." + sname + ", li." + sname + ", div." + sname;
                        }
                        content = findStyles(content, sname, contentStyle.getFont(),
                                contentStyle.getSize(), contentStyle.getColor());
                    }
                }
            }
        }
        return content;
    }

    /**
     * Replaces the font format styles (font type, size and color) found in {@code content}
     * with the arguments' values received. <p>Busca los estilos para el formato
     * de letra (fuente, tama&ntilde;o y color) en una cadena y si los encuentra
     * los remplaza por los valores de los argumentos recibidos.</p>
     * @param content a string with the text to modify
     * @param word a string representing a style class name
     * @param font a string representing the font type to replace the one found
     * @param size a string representing the font size to replace the one found
     * @param color a string representing the font color to replace the one found
     * @return a string like {@code content} but with the font format styles related
     *         to the style class defined by {@code word} replaced with the arguments' values
     */
    public static String findStyles(String content, String word, String font, String size, String color)
    {
        int pos = -1;
        pos = content.indexOf(word);
        if (pos > -1)
        {
            int pos1 = -1;
            pos1 = content.indexOf("{", pos);
            if (pos1 > -1 && font != null)
            {
                int ifont = -1;
                ifont = content.indexOf("font-family:", pos1);
                int fintot = content.indexOf("}", pos1);
                if (ifont > -1)
                { //cambio de font
                    int fin = -1;
                    fin = content.indexOf(";", ifont);
                    if (fin > -1)
                    {
                        content = content.substring(0, ifont + 12) + font + content.substring(fin);
                    }
                }
            }
            if (pos1 > -1 && size != null)
            {
                int isize = -1;
                isize = content.indexOf("font-size:", pos1);
                int fintot = content.indexOf("}", pos1);
                if (isize > -1 && isize < fintot)
                { //cambio de size
                    int fin = -1;
                    fin = content.indexOf(";", isize);
                    if (fin > -1)
                    {
                        content = content.substring(0, isize + 10) + size + content.substring(fin);
                    }
                }
            }
            if (pos1 > -1 && color != null)
            {
                int icolor = -1;
                icolor = content.indexOf("color:", pos1);
                int fintot = content.indexOf("}", pos1);
                if (icolor > -1 && icolor < fintot)
                { //cambio de color
                    int fin = -1;
                    fin = content.indexOf(";", icolor);
                    if (fin > -1)
                    {
                        content = content.substring(0, icolor + 6) + color + content.substring(fin);
                    }
                }
                else if (icolor == -1 || (icolor > -1 && icolor > fintot))
                { //cambio de color
                    content = content.substring(0, fintot) + "color:" + color + ";" + content.substring(fintot);
                }
            }
        }
        return content;
    }

    /**
     * Metodo sobrado en este momento, pero servira para cuando un submodelo (sitio), tenga mas submodelos (sitios,repositorios).
     * 
     * @param node the node
     * @param smodels the smodels
     */
    private static void iteraModels(Node node, HashMap smodels)
    {
        HashMap submodel = new HashMap();
        NodeList nlChildModels = node.getChildNodes();
        for (int j = 0; j < nlChildModels.getLength(); j++)
        {
            Node nodeSModel = nlChildModels.item(j);
            if (nodeSModel.getNodeName().equals("type"))
            { //Revisar si existe el submodel en la instancia a importar
                NodeList nlSite = node.getChildNodes();
                for (int k = 0; k < nlSite.getLength(); k++)
                {
                    if (nlSite.item(k).getNodeName().equals("id"))
                    {
                        smodels.put(nlSite.item(k).getFirstChild().getNodeValue(), submodel);
                    }
                }
            }
            else if (nodeSModel.getNodeName().equals("id"))
            {
                submodel.put("id", nodeSModel.getFirstChild().getNodeValue());
            }
            else if (nodeSModel.getNodeName().equals("namespace"))
            {
                submodel.put("namespace", nodeSModel.getFirstChild().getNodeValue());
            }
            else if (nodeSModel.getNodeName().equals("title"))
            {
                submodel.put("title", nodeSModel.getFirstChild().getNodeValue());
            }
            else if (nodeSModel.getNodeName().equals("description"))
            {
                submodel.put("description", nodeSModel.getFirstChild().getNodeValue());
            }
        }
    }

//    /**
//     * Gets the file upload ctrl string.
//     * 
//     * @param name the name
//     * @param request the request
//     * @return the file upload ctrl string
//     */
//    public static String getFileUploadCtrlString(String name, HttpServletRequest request)
//    {
//        String ret = "";
//        String attchMsg = "";
//        if (name != null && request.getAttribute("attachCount_" + name) != null)
//        {
//            attchMsg = "Archivo(s) existentes:<br/>";
//            int count = Integer.parseInt((String) request.getAttribute("attachCount_" + name));
//            for (int i = 1; i <= count; i++)
//            {
//                String fileName = (String) request.getAttribute("attach_" + name + "_" + i);
//                int pos = fileName.lastIndexOf("/");
//                if (pos > -1)
//                {
//                    fileName = fileName.substring(pos + 1);
//                }
//                String target = "";
//                if (request.getAttribute("attachTarget_" + name + "_" + i) != null)
//                {
//                    target = (String) request.getAttribute("attachTarget_" + name + "_" + i);
//                }
//
//                if (request.getAttribute("attachRemovePath_" + name + "_" + i) != null)
//                {
//                    attchMsg += "<a href=\"" + request.getAttribute("attachRemovePath_" + name + "_" + i) + "\">X</a> ";
//                }
//                attchMsg += i + ")<a href=\"" + request.getAttribute("attach_" + name + "_" + i) + "\" target=\"" + target + "\">" + fileName + "</a>";
//                attchMsg += "<br/>";
//            }
//        }
//
//        String formName = (String) request.getAttribute("formName");
//        //Página ejemplo de implementación:http://blog.tremend.ro/2007/03/01/ajax-file-upload-monitoring-monitor-your-file-upload-with-dwr-and-commons-fileupload/
//        //Fecha de implemetación:26/Febrero/2009
//        //TODO:Haecer que este Bloque solo sea puesto una vez, independientemente de cuantos fileuploads tiene mi forma
//        ret = "<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"" + SWBPlatform.getContextPath() + "/swbadmin/css/upload/upload.css\"/>\n"
//                + "<script type='text/javascript' src=\"" + SWBPlatform.getContextPath() + "/dwr/util.js\"></script>\n"
//                + "<script type='text/javascript' src=\"" + SWBPlatform.getContextPath() + "/dwr/engine.js\"></script>\n"
//                + "<script type=\"text/javascript\" src=\"" + SWBPlatform.getContextPath() + "/dwr/interface/uploadProxy.js\"></script>\n"
//                + "<script type='text/javascript' src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/upload/upload.js\"></script>\n";
//        //TODO:Haecer que esta linea solo sea puesta una vez, independientemente de cuantos fileuploads tiene mi forma
//        ret += "<iframe id='target_upload_" + name + "' name='target_upload_" + name + "' src='' style='display: none'></iframe><br/>" + //
//                attchMsg
//                + "<input id=\"" + name + "\" name=\"" + name + "\" type=\"file\" onChange=\"javascript:if(uploadjs_" + name + "(document.getElementById('" + formName + "'))) {return startUploadMonitoring('" + name + "');}\"> <br/>"
//                + "<div id=\"uploadStatus_" + name + "\" style=\"width:230px\">\n"
//                + "   <div id=\"uploadProgressBar_" + name + "\" style=\"width:200px; height: 2px; border: 0px solid #BBB; text-align: center; float: left;\">\n"
//                + "       <div id=\"uploadIndicator_" + name + "\" style=\" height: 1px; position: relative; margin: 0px; padding: 1px; background: #9DC0F4; width: 0; float: left;\"></div>\n"
//                + "   </div>\n"
//                + "   <div id=\"uploadPercentage_" + name + "\" style=\"width:5px; float: right;\"></div>\n"
//                + "</div>\n";
//
//        ret += "<script type=\"text/javascript\">\n"
//                + "function uploadjs_" + name + "(forma){\n"
//                + "if(forma." + name + ".value==''){alert('El campo archivo no debe estar vacio');forma." + name + ".focus(); return false;}" + //TODO:Internacionalizar
//                "  var encoding=forma.encoding;\n"
//                + "  forma.encoding='multipart/form-data';\n"
//                + "  var method=forma.method;\n"
//                + "  forma.method='post';\n"
//                + "  var action=forma.action;\n"
//                + "  forma.action='" + SWBPlatform.getContextPath() + "/Upload';\n"
//                + "  var target=forma.target;\n"
//                + "  forma.target='target_upload_" + name + "';\n"
//                + "  forma.submit();\n"
//                + "  forma.encoding=encoding;\n"
//                + "  forma.method=method;\n"
//                + "  forma.action=action;\n"
//                + "  forma.target=target;\n"
//                + "  return true;\n"
//                + "}\n"
//                + "</script>\n";
//        return ret;
//    }

    /**
     * Check hsqlh ack.
     * 
     * @param con the con
     */
    private void checkHSQLHack(Connection con)
    {
        try
        {
            boolean fix = false;
            String base = "";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select obj from SWB_SYS_STMT where prop='Uv::http://jena.hpl.hp.com/2003/04/DB#EngineType'");
            while (rs.next())
            {
                base = rs.getString("obj");
                if (!base.equals("Lv:0::HSQLDB"))
                {
                    fix = true;
                }
                //  System.out.println("Subj"+rs.getString("subj"));
                //  System.out.println("Prop"+rs.getString("prop"));
                //System.out.println("Obj" + rs.getString("obj"));
                //System.out.println();
            }
            rs.close();
            if (fix){
                st.executeUpdate("update SWB_SYS_STMT set obj='Lv:0::HSQLDB' where prop='Uv::http://jena.hpl.hp.com/2003/04/DB#EngineType'");
                st.close();
            }
        }
        catch (Exception noe)
        {
            //log.error("Problem Updating HSQL Header", e);
        }
    }
    
}
