
package org.semanticwb;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.semanticwb.platform.SWBMonitor;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.base.util.SWBProperties;
import org.semanticwb.platform.SemanticModel;

/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBInstance 
{
    private static Logger log=SWBUtils.getLogger(SWBInstance.class);
    private static SWBInstance instance=null;
    private static Properties props=null;
    private String contextPath="/";
    
    private static ServletContext servletContext=null;

    private static boolean haveDB=false;
    private static boolean haveDBTables=false;
    
    private static SWBMonitor monitor=null;
    private static SemanticMgr semanticMgr=null;

    /**
     * 
     */
    protected String version = "4.0.0.0";
    
    private SWBInstance()
    {
        log.event("Initialize Semantic WebBuilder Instance...");
        init();
    }
    
    //Initialize context
    private void init()
    {
        log.event("SemanticWebBuilder Version: " + version);
        log.event("Java Version: " + System.getProperty("java.runtime.version"));
        
        InputStream in = getClass().getResourceAsStream("/web.properties");
        props = new SWBProperties();
        try
        {
            props.load(in);
            log.info("File web.properties found...");
        } catch (Exception e)
        {
            log.warn("File web.properties not found...");
        }        
        
        try
        {
            //Runtime.getRuntime().gc();
            long inimem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            Connection con=SWBUtils.DB.getDefaultConnection();
            if(con!=null)
            {
                log.info("Database: "+SWBUtils.DB.getDatabaseName());
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
                log.warn("Default SemanticWebBuilder database not found...");
                return;
            }
            if(!haveDBTables())
            {
                log.warn("Default SemanticWebBuilder database tables not found...");
                return;
            }           
            
            try
            {
                System.setProperty("sun.net.client.defaultConnectTimeout", getEnv("swb/defaultConnectTimeout","5000"));
                System.setProperty("sun.net.client.defaultReadTimeout", getEnv("swb/defaultReadTimeout","30000"));
            }catch(Exception e)
            {
                System.err.println("Error seting defaultConnectTimeout, defaultReadTimeout");            
            }                

            
            
            semanticMgr=new SemanticMgr();
            semanticMgr.init(this);
            
            createInstance("org.semanticwb.model.SWBContext");
            
            monitor=new SWBMonitor();
            monitor.init(this);
            
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
                log.info("Login Config:"+System.getProperty("java.security.auth.login.config"));
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
    static public synchronized SWBInstance createInstance(ServletContext servletContext)
    {
        if (instance == null)
        {
            instance = new SWBInstance();
            instance.servletContext=servletContext;
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
        }catch(Exception e)
        {
            log.error("Error initializing "+clsname+"..");
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

    public static SWBMonitor getMonitor() 
    {
        return monitor;
    }    

    public static SemanticMgr getSemanticMgr() 
    {
        return semanticMgr;
    }    
    
    /**
     * Regresa contador en base a la cadena <i>name</i>, sin incrementar el valor del mismo
     */
    public static synchronized long getCounterValue(String name)
    {
        SemanticModel model=getSemanticMgr().getSystemModel();
        Resource res=model.getRDFModel().createResource(name);
        Property prop=model.getRDFModel().createProperty("swb:count");
        StmtIterator it=model.getRDFModel().listStatements(res, prop, (String)null);
        if(it.hasNext())
        {
            Statement stmt=it.nextStatement();
            return stmt.getLong();
        }
        return 0;
//        Statement stmt=model.getRDFModel().createStatement(res, prop, res)
//        
//        
//        long ret=0;
//        Connection con=SWBUtils.DB.getDefaultConnection();
//        if (con != null)
//        {
//            try
//            {
//                PreparedStatement pst = con.prepareStatement("select countvalue from swbcounter where name=?");
//                pst.setString(1,name);
//                ResultSet rs = pst.executeQuery();
//                if(rs.next()) ret=rs.getLong("countvalue");
//                rs.close();
//                pst.close();
//                con.close();
//            }catch(Exception e){
//                log.error(e);
//            }
//        }
//        return ret;
    }
    
    /**
     * Asigna el valor <i>val</a> al contador de nombre <i>name</i>
     */
    public static synchronized void setCounterValue(String name, long val)
    {
        SemanticModel model=getSemanticMgr().getSystemModel();
        Resource res=model.getRDFModel().createResource(name);
        Property prop=model.getRDFModel().createProperty("swb:count");
        StmtIterator it=model.getRDFModel().listStatements(res, prop, (String)null);
        if(it.hasNext())
        {
            Statement stmt=it.nextStatement();
            stmt.changeLiteralObject(val);
        }else
        {
            Statement stmt=model.getRDFModel().createLiteralStatement(res, prop, val);
            model.getRDFModel().add(stmt);
        }
        
//        long ret=0;
//        Connection con=SWBUtils.DB.getDefaultConnection();
//        if (con != null)
//        {
//            try
//            {
//                PreparedStatement pst = con.prepareStatement("select countvalue from swbcounter where name=?");
//                pst.setString(1,name);
//                ResultSet rs = pst.executeQuery();
//                if(rs.next())
//                {
//                    ret=rs.getLong("countvalue");
//                    PreparedStatement pst2 = con.prepareStatement("UPDATE swbcounter SET countvalue=? WHERE name=?");
//                    pst2.setLong(1,val);
//                    pst2.setString(2,name);
//                    pst2.executeUpdate();
//                    pst2.close();                    
//                }else
//                {
//                    PreparedStatement pst2 = con.prepareStatement("INSERT INTO swbcounter (name, countvalue) VALUES (?, ?)");
//                    pst2.setString(1,name);
//                    pst2.setLong(2,val);
//                    pst2.executeUpdate();
//                    pst2.close();                    
//                }
//                rs.close();
//                pst.close();
//                con.close();
//            }catch(Exception e){
//                log.error(e);
//                throw new SWBException("Error seting counter "+name,e);
//            }
//        }        
    }
    
    
    /**
     * Regresa contador en base a la cadena <i>name</i>, e incrementa el valor en uno
     */
    public static synchronized long getCounter(String name)
    {
        long ret=getCounterValue(name);
        ret++;
        setCounterValue(name, ret);
//        Connection con=SWBUtils.DB.getDefaultConnection();
//        if (con != null)
//        {
//            try
//            {
//                PreparedStatement pst = con.prepareStatement("select countvalue from swbcounter where name=?");
//                pst.setString(1,name);
//                ResultSet rs = pst.executeQuery();
//                if(rs.next())
//                {
//                    ret=rs.getLong("countvalue");
//                    ret++;
//                    PreparedStatement pst2 = con.prepareStatement("UPDATE swbcounter SET countvalue=? WHERE name=?");
//                    pst2.setLong(1,ret);
//                    pst2.setString(2,name);
//                    pst2.executeUpdate();
//                    pst2.close();                    
//                }else
//                {
//                    ret++;
//                    PreparedStatement pst2 = con.prepareStatement("INSERT INTO swbcounter (name, countvalue) VALUES (?, ?)");
//                    pst2.setString(1,name);
//                    pst2.setLong(2,ret);
//                    pst2.executeUpdate();
//                    pst2.close();                    
//                }
//                rs.close();
//                pst.close();
//                con.close();
//            }catch(Exception e){
//                AFUtils.log(e);
//                throw new AFException("Error geting counter "+name,"DBCounter.getCounter",e);
//            }
//        }
        return ret;
    }
    
    public static synchronized void deleteCounterValue(String name)
    {
        SemanticModel model=getSemanticMgr().getSystemModel();
        Resource res=model.getRDFModel().createResource(name);
        Property prop=model.getRDFModel().createProperty("swb:count");
        model.getRDFModel().remove(res, prop, null);
//        Connection con=AFUtils.getDBConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"),"DBCounter");
//        if (con != null)
//        {
//            try
//            {
//                PreparedStatement pst = con.prepareStatement("DELETE FROM swbcounter WHERE name=?");
//                pst.setString(1,name);
//                pst.executeUpdate();
//                pst.close();
//                con.close();
//            }catch(Exception e){
//                AFUtils.log(e);
//            }
//        }

    }
    
}
