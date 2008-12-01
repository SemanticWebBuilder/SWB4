
package org.semanticwb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.base.util.SWBProperties;
import org.semanticwb.base.util.URLEncoder;


/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBPlatform 
{
    private static Logger log=SWBUtils.getLogger(SWBPlatform.class);
    private static SWBPlatform instance=null;
    private static Properties props=null;
    private String contextPath="/";
    private static String workPath = "";
    private static String webWorkPath = "";
    
    
    private static ServletContext servletContext=null;

    private static boolean haveDB=false;
    private static boolean haveDBTables=false;
    

    private static SemanticMgr semanticMgr=null;

    /**
     * 
     */
    protected static String version = "4.0.0.0";
    
    private SWBPlatform()
    {
        log.event("Initializing SemanticWebBuilder Platform...");
        init();
    }
    
    //Initialize context
    private void init()
    {
        log.event("-->SemanticWebBuilder Version: " + version);
        log.event("-->Java Version: " + System.getProperty("java.runtime.version"));
        log.event("-->Java Memory: " + Runtime.getRuntime().totalMemory());
        
        InputStream in = getClass().getResourceAsStream("/web.properties");
        props = new SWBProperties();
        try
        {
            props.load(in);
            log.info("-->File web.properties found...");
        } catch (Exception e)
        {
            log.warn("-->File web.properties not found...");
        }        
        
        try
        {
            //Runtime.getRuntime().gc();
            long inimem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

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

            
            
            semanticMgr=new SemanticMgr();
            semanticMgr.init(this);
            
            createInstance("org.semanticwb.model.SWBContext");
            createInstance("org.semanticwb.SWBPortal");
            
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
//
//            if (wbutil.isClient() || dbsync.trim().equalsIgnoreCase("true"))
//            {
//                initime = DBDbSync.getInstance().getLastUpdate();
//                objs.add(DBDbSync.getInstance());
//            }
//            
//            objs.add(TopicMgr.getInstance(util.getLog()));
//            objs.add(DBTopicMap.getInstance());
//            
//            objs.add(DBCatalogs.getInstance());
//            objs.add(WBVirtualHostMgr.getInstance());
//            objs.add(RuleMgr.getInstance());
//            objs.add(DBRule.getInstance());
//            objs.add(RoleMgr.getInstance());
//            objs.add(DBRole.getInstance());
//            objs.add(WBUserMgr.getInstance());
//            objs.add(DBUser.getInstance());
//            objs.add(AdmFilterMgr.getInstance());
//            
//            objs.add(DBAdmFilter.getInstance());
//            objs.add(DBResourceType.getInstance());
//            objs.add(CampMgr.getInstance());
//            objs.add(ResourceMgr.getInstance());
//            objs.add(DBResource.getInstance());
//            objs.add(TemplateMgr.getInstance());
//            objs.add(DBTemplate.getInstance());
//            objs.add(PFlowMgr.getInstance());
//            objs.add(DBPFlow.getInstance());
//            objs.add(DBGenericMgr.getInstance());
//            objs.add(ScheduleMgr.getInstance());
//
////            if(AFUtils.getEnv("workflow/clientServer", "Server").equalsIgnoreCase("Server"))
////            {
////              objs.add(com.infotec.workflow.manager.ProcessManager.getInstance());
////              objs.add(com.infotec.workflow.runtime.ProcessAnalyzer.getInstance());
////            }
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
//            objs.add(WBMessageCenter.getInstance());
//            objs.add(WBTopicAccessIncrement.getInstance());
//            objs.add(WBMonitor.getInstance());
//
//            //cargando clases de startup.properties
//            WBStartup startup = new WBStartup(objs);
//            startup.load("startup.properties");
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
        
        workPath = SWBPlatform.getEnv("swb/workPath");
        try {
            //TODO:revisar sincronizacion
            //if (confCS.equalsIgnoreCase("Client")) remoteWorkPath = (String) AFUtils.getInstance().getEnv("wb/remoteWorkPath");

            workPath = (String) SWBPlatform.getEnv("swb/workPath","/work");
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
    
    /** Create Instance.
     * @param servletContext 
     * @return  SWBContext*/
    static public synchronized SWBPlatform createInstance(ServletContext servletContext)
    {
        if (instance == null)
        {
            instance = new SWBPlatform();
            SWBPlatform.servletContext=servletContext;
        }
        return instance;
    }
    
    
    private void createInstance(String clsname)
    {
        try
        {
            Class cls = Class.forName(clsname);
            Method createInstance = cls.getMethod("createInstance", (Class[])null);
            createInstance.invoke(null, (Object[])null);
        }catch(ClassNotFoundException e)
        {
            log.warn(clsname+" not found...");
        }catch(Exception e)
        {
            log.error("Error initializing "+clsname+"..",e);
        }
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
                webWorkPath = contextPath +SWBPlatform.getEnv("swb/webWorkPath").substring(1);
            } else {
                webWorkPath = contextPath + SWBPlatform.getEnv("swb/webWorkPath");
            }
        } catch (Exception e) {
            log.error("Can't read the context path...", e);
            webWorkPath = "";
        }           
    }
    
    /** Getter for property workPath.
     * @return Value of property workPath.
     */
    public static String getWorkPath()
    {
        return workPath;
    }
    
    /** Getter for property workPath.
     * @return Value of property workPath.
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

    public static SemanticMgr getSemanticMgr() 
    {
        return semanticMgr;
    }    
    
    public static String getVersion() {
        return version;
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
    
    public static Properties getWebProperties()
    {
        return props;
    }
    
//    /** La instancia de WB esta configurada como cliente?.
//     * @return Value of property client.
//     */
//    public boolean isClient() {
//        return client;
//    }
//    
//    /** Getter for property remoteWorkPath.
//     * @return Value of property remoteWorkPath.
//     */
//    public String getRemoteWorkPath() {
//        return remoteWorkPath;
//    }    
    
}
