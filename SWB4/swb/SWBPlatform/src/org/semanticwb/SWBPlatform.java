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

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.base.util.SWBProperties;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.platform.IDGenerator;
import org.semanticwb.platform.SemanticVocabulary;


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
    private static boolean useDB=true;

    private static IDGenerator idgen=null;

    private static boolean client=false;

    private static SemanticMgr semanticMgr=null;

    /**
     * 
     */
    protected static String version = "4.0.0.1";
    
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

        props=SWBUtils.TEXT.getPropertyFile("/web.properties");

        workPath = SWBPlatform.getEnv("swb/workPath");

        SWBUtils.EMAIL.setSMTPServer(getEnv("swb/smtpServer"));
        SWBUtils.EMAIL.setSMTPUser(getEnv("swb/smtpUser"));
        SWBUtils.EMAIL.setSMTPPassword(getEnv("swb/smtpPassword"));
        SWBUtils.EMAIL.setAdminEmail(getEnv("af/adminEmail"));

        try {
            //TODO:revisar sincronizacion
            //if (confCS.equalsIgnoreCase("Client")) remoteWorkPath = (String) AFUtils.getInstance().getEnv("swb/remoteWorkPath");

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
        //System.out.println("workPath:"+workPath);

        
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

        idgen=new IDGenerator();
    }

    public static IDGenerator getIDGenerator()
    {
        return idgen;
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

    public static SemanticMgr getSemanticMgr()
    {
        return semanticMgr;
    }

    public static String getVersion() {
        return version;
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
    
    public static Properties getWebProperties()
    {
        return props;
    }
    
    public static boolean isUseDB() {
        return useDB;
    }

    public static void setUseDB(boolean useDB) {
        SWBPlatform.useDB = useDB;
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

    //TODO:validar client
    public static boolean isClient()
    {
        return client;
    }

    public static class JENA_UTIL
    {
        //public static String PATH_VIEW=SWBPlatform.getContextPath()+"/swbadmin/jsp/resourceTab.jsp";

        public static String getTextLocaled(Resource res, Property label)
        {
            String ret="";
            Iterator<Statement> it=res.listProperties(label);
            while(it.hasNext())
            {
                Statement st=it.next();
                ret=ret+st.getString();
                if(st.getLanguage()!=null)ret+="("+st.getLanguage()+")";
                if(it.hasNext())ret+=", ";
            }
            return ret;
        }

        public static String getId(Resource res)
        {
            String ret="";
            ret+=res.getModel().getNsURIPrefix(res.getNameSpace());
            ret+=":";
            ret+=res.getLocalName();
            return ret;
        }

        public static String getLink(Resource res, String pathView)
        {
            return getLink(res,null, pathView);
        }

        public static String getLink(Resource res, String text, String pathView)
        {
            String val=text;
            if(val==null)val=getId(res);
            return "<a href=\"#\" onClick=\"addNewTab('"+res.getURI()+"', '"+pathView+"', '"+val+"');\">"+val+"</a>";
        }

        public static String getObjectId(Resource res, Property ptype, Model model)
        {
            String type="";
            Statement stm=res.getProperty(ptype);
            if(stm!=null)
            {
                type=model.getNsURIPrefix(stm.getResource().getNameSpace())+":"+stm.getResource().getLocalName();
            }
            return type;
        }

        public static String getObjectLink(Resource res, Property ptype, Model model, String pathView)
        {
            return getObjectLink(res, ptype, model, null, pathView);
        }

        public static String getObjectLink(Resource res, Property ptype, Model model, String text, String pathView)
        {
            String val=text;
            if(val==null)val=getObjectId(res, ptype, model);
            Statement stm=res.getProperty(ptype);
            if(stm!=null)
            {
                Resource res2=stm.getResource();
                return "<a href=\"#\" onClick=\"addNewTab('"+res2.getURI()+"', '"+pathView+"', '"+val+"');\">"+val+"</a>";
            }
            return val;
        }

        public static boolean isInBaseModel(Resource res, OntModel ont)
        {
            Property type=ont.getProperty(SemanticVocabulary.RDF_TYPE);
            return ont.getBaseModel().contains(res, type);
        }



        public static void addFilteredClass(OntClass cls, ArrayList arr)
        {
            boolean filtered=false;
            Iterator<OntClass> it=arr.iterator();
            //System.out.println("filter:"+cls);
            while(it.hasNext())
            {
                OntClass aux=it.next();
                if(aux.equals(cls))
                {
                    filtered=true;
                }else if(cls.hasSuperClass(aux))
                {
                    //System.out.println("remove:"+aux);
                    it.remove();
                }else if(aux.hasSubClass(cls))
                {
                    filtered=true;
                }
            }
            if(!filtered)
            {
                arr.add(cls);
                //System.out.println("add:"+cls);
            }
        }

        public static Iterator<Property> getClassProperties(Resource res, OntModel model)
        {
            OntResource ores=model.getOntResource(res);
            ArrayList<Property> arr=new ArrayList();
            //Se agregan Propiedades de defautl
            Property ptype=model.getProperty(SemanticVocabulary.RDF_TYPE);
            if(ores!=null && ores.isClass())
            {
                arr.add(model.getProperty(SemanticVocabulary.RDFS_LABEL));
                arr.add(model.getProperty(SemanticVocabulary.RDFS_COMMENT));
                arr.add(model.getProperty(SemanticVocabulary.RDFS_SUBCLASSOF));
                arr.add(model.getProperty(SemanticVocabulary.OWL_URI+"equivalentClass"));
                arr.add(model.getProperty(SemanticVocabulary.OWL_URI+"disjointWith"));
            }else if(ores!=null && ores.isProperty())
            {
                arr.add(model.getProperty(SemanticVocabulary.RDFS_LABEL));
                arr.add(model.getProperty(SemanticVocabulary.RDFS_COMMENT));
                arr.add(model.getProperty(SemanticVocabulary.RDFS_SUBPROPERTYOF));
                arr.add(model.getProperty(SemanticVocabulary.RDFS_DOMAIN));
                arr.add(model.getProperty(SemanticVocabulary.RDFS_RANGE));
                arr.add(model.getProperty(SemanticVocabulary.OWL_URI+"equivalentProperty"));
                arr.add(model.getProperty(SemanticVocabulary.OWL_URI+"inverseOf"));
            }
            //System.out.println("ptype:"+ptype);

            Iterator<Statement> it=res.listProperties(ptype);
            while(it.hasNext())
            {
                Statement st=it.next();
                //System.out.println("res:"+st.getSubject()+" "+st.getPredicate()+" "+st.getObject());
                OntClass ocls=model.getOntClass(st.getResource().getURI());
                //System.out.println("cls:"+ocls);
                //System.out.println("ocls1:"+ocls);

                if(ocls!=null)
                {
                    //addFilteredClass(ocls, types);
                    //System.out.println("ocls2:"+ocls);

                    //SemanticClass cls=new SemanticClass(ocls);
                    Iterator<OntProperty> it2=ocls.listDeclaredProperties();
                    while(it2.hasNext())
                    {
                        OntProperty prop=it2.next();
                        if(!arr.contains(prop))
                        {
                            OntResource rd=prop.getDomain();
                            if(rd!=null)
                            {
                                OntClass dom=rd.asClass();
                                if(dom!=null && (dom.equals(ocls) || dom.hasSuperClass(ocls)))
                                {
                                    arr.add(prop);
                                    //System.out.println("prop:"+prop);
                                }
                            }
                        }

                    }
                }
            }

            //Se agregan propiedades adicionales
            Iterator<Statement> itp=res.listProperties();
            while(itp.hasNext())
            {
                Statement stmt=itp.next();
                Property p=stmt.getPredicate();
                if(!arr.contains(p))
                {
                    arr.add(p);
                    //System.out.println("prop:"+prop);
                }
            }
            arr.remove(ptype);
            arr.add(ptype);

            return arr.iterator();
        }

        public static Resource getBaseModelResource(String uri, OntModel ont)
        {
            Resource res=ont.getResource(uri);
            Property type=ont.getProperty(SemanticVocabulary.RDF_TYPE);
            Model base=ont.getBaseModel();
            if(base.contains(res, type))
            {
                res=base.getResource(uri);
            }else
            {
                Iterator<Model> it=ont.listSubModels();
                while(it.hasNext())
                {
                    Model model=it.next();
                    if(model instanceof OntModel)model=((OntModel)model).getBaseModel();
                    //System.out.println("sub:"+model.getGraph().size());
                    if(model!=base)
                    {
                        if(model.contains(res, type))
                        {
                            res=model.getResource(uri);
                            break;
                        }
                    }
                }
            }
            return res;
        }

    }

}
