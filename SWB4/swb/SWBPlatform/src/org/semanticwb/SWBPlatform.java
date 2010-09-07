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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.IDGenerator;
import org.semanticwb.platform.SemanticVocabulary;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBPlatform.
 * 
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBPlatform 
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBPlatform.class);
    
    /** The instance. */
    private static SWBPlatform instance=null;

    /** The props. */
    private static Properties props=null;

    /** The context path. */
    private static String contextPath = "";
    
    /** The work path. */
    private static String workPath = "";
    
    /** The statements cache. */
    private static boolean statementsCache=true;

    /** The admin dev. */
    private static boolean adminDev=false;
    
    /** The admin file. */
    private static String adminFile="/swbadmin/rdf/SWBAdmin.nt";
    
    /** The ont edit file. */
    private static String ontEditFile="/swbadmin/rdf/SWBOntEdit.nt";

    /** The Constant PRESIST_TYPE_DEFAULT. */
    public final static String PRESIST_TYPE_DEFAULT="default";
    
    /** The Constant PRESIST_TYPE_SDB. */
    public final static String PRESIST_TYPE_SDB="sdb";
    
    /** The Constant PRESIST_TYPE_TDB. */
    public final static String PRESIST_TYPE_TDB="tdb";

    /** The persistence type. */
    private static String persistenceType=PRESIST_TYPE_DEFAULT;

    /** The idgen. */
    private static IDGenerator idgen=null;

    /** The semantic mgr. */
    private static SemanticMgr semanticMgr=null;

    /** The Constant version. */
    protected static final String version = "4.0.1.8";
    
    /**
     * Instantiates a new sWB platform.
     */
    private SWBPlatform()
    {
        log.event("Initializing SemanticWebBuilder Platform...");
        init();
    }

    /**
     * Creates the instance.
     * 
     * @return the sWB platform
     */
    static public synchronized SWBPlatform createInstance() {
        if (instance == null) {
            instance = new SWBPlatform();
        }
        return instance;
    }
    
    //Initialize context
    /**
     * Inits the.
     */
    private void init()
    {
        log.event("-->SemanticWebBuilder Version: " + version);
        log.event("-->Java Version: " + System.getProperty("java.runtime.version"));
        log.event("-->Java Memory: " + Runtime.getRuntime().totalMemory());

        try
        {
            semanticMgr=new SemanticMgr();
            semanticMgr.init();
        } catch (Throwable e)
        {
            log.error("Error loading SemanticWebBuilder Instance...", e);
        }

        idgen=new IDGenerator();
    }
    
    /**
     * Sets the properties.
     * 
     * @param props the new properties
     */
    public void setProperties(Properties props)
    {
        SWBPlatform.props=props;
    }

    /**
     * Obtiene valor de variable de ambiente declarada en web.xml o web.properties.
     * 
     * @param name String nombre de la variable
     * @return the env
     * @return
     */
    public static String getEnv(String name)
    {
        return getEnv(name, null);
    }

    /**
     * Obtiene valor de variable de ambiente declarada en web.xml o web.properties.
     * 
     * @param name String nombre de la variable
     * @param defect String valor por defecto
     * @return the env
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

    /**
     * Gets the iD generator.
     * 
     * @return the iD generator
     */
    public static IDGenerator getIDGenerator()
    {
        return idgen;
    }
    
    /**
     * Gets the semantic mgr.
     * 
     * @return the semantic mgr
     */
    public static SemanticMgr getSemanticMgr()
    {
        return semanticMgr;
    }

    /**
     * Gets the version.
     * 
     * @return the version
     */
    public static String getVersion() {
        return version;
    }

    /**
     * Gets the context path.
     * 
     * @return the context path
     */
    public static String getContextPath()
    {
        return contextPath;
    }

    /**
     * Sets the context path.
     * 
     * @param contextPath the new context path
     */
    public void setContextPath(String contextPath)
    {
        SWBPlatform.contextPath=contextPath;
    }


    /**
     * Gets the platform work path.
     * 
     * @return the platform work path
     */
    public String getPlatformWorkPath()
    {
        return workPath;
    }

    /**
     * Sets the platform work path.
     * 
     * @param workPath the new platform work path
     */
    public void setPlatformWorkPath(String workPath)
    {
        SWBPlatform.workPath=workPath;
    }
    
    /**
     * Gets the statements cache.
     * 
     * @return the statements cache
     */
    public boolean getStatementsCache()
    {
        return statementsCache;
    }

    /**
     * Sets the statements cache.
     * 
     * @param statementsCache the new statements cache
     */
    public void setStatementsCache(boolean statementsCache)
    {
        SWBPlatform.statementsCache=statementsCache;
    }

    /**
     * Gets the persistence type.
     * 
     * @return the persistence type
     */
    public String getPersistenceType() {
        return persistenceType;
    }

    /**
     * Sets the persistence type.
     * 
     * @param persistenceType the new persistence type
     */
    public void setPersistenceType(String persistenceType) 
    {
        if(PRESIST_TYPE_DEFAULT.equalsIgnoreCase(persistenceType))
        {
            SWBPlatform.persistenceType = PRESIST_TYPE_DEFAULT;
        }else if(PRESIST_TYPE_SDB.equalsIgnoreCase(persistenceType))
        {
            SWBPlatform.persistenceType = PRESIST_TYPE_SDB;
        }else if(PRESIST_TYPE_TDB.equalsIgnoreCase(persistenceType))
        {
            SWBPlatform.persistenceType = PRESIST_TYPE_TDB;
        }else
        {
            SWBPlatform.persistenceType=persistenceType;
        }
    }

    /**
     * Write file to platform work path.
     * 
     * @param path the path
     * @param in the in
     * @throws SWBException the sWB exception
     */
    public void writeFileToPlatformWorkPath(String path, InputStream in) throws SWBException {
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
                File file=new File(getPlatformWorkPath() + path);
                file.getParentFile().mkdirs();
                FileOutputStream out=new FileOutputStream(file);
                SWBUtils.IO.copyStream(in,out);
//            }
        } catch (Exception e) {
            throw new SWBException(e.getMessage(), e);
        }
    }


    /**
     * Removes the file from platform work path.
     * 
     * @param path the path
     */
    public void removeFileFromPlatformWorkPath(String path)
    {
        //TOTO:Impementar Replicacion de archivos
        File file=new File(getPlatformWorkPath() + path);
        file.delete();
    }

    /**
     * Gets the file from platform work path.
     * 
     * @param path the path
     * @return the file from platform work path
     * @throws SWBException the sWB exception
     * @return
     */
    public InputStream getFileFromPlatformWorkPath(String path) throws SWBException {
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
                ret = new FileInputStream(getPlatformWorkPath() + path);
//            }
        } catch (Exception e) {
            throw new SWBException(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * Read file from platform work path.
     * 
     * @param path the path
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBException the sWB exception
     * @return
     */
    public String readFileFromPlatformWorkPath(String path) throws IOException, SWBException {
        return SWBUtils.IO.readInputStream(getFileFromPlatformWorkPath(path));
    }

    /**
     * Checks if is admin dev.
     * 
     * @return true, if is admin dev
     */
    public boolean isAdminDev() {
        return adminDev;
    }

    /**
     * Sets the admin dev.
     * 
     * @param adminDev the new admin dev
     */
    public void setAdminDev(boolean adminDev) {
        SWBPlatform.adminDev = adminDev;
    }

    /**
     * Gets the admin file.
     * 
     * @return the admin file
     */
    public String getAdminFile() {
        return adminFile;
    }

    /**
     * Sets the admin file.
     * 
     * @param adminFile the new admin file
     */
    public void setAdminFile(String adminFile) {
        SWBPlatform.adminFile = adminFile;
    }

    /**
     * Gets the ont edit file.
     * 
     * @return the ont edit file
     */
    public String getOntEditFile() {
        return ontEditFile;
    }

    /**
     * Sets the ont edit file.
     * 
     * @param ontEditFile the new ont edit file
     */
    public void setOntEditFile(String ontEditFile) {
        SWBPlatform.ontEditFile = ontEditFile;
    }

    /**
     * The Class JENA_UTIL.
     */
    public static class JENA_UTIL
    {
        //public static String PATH_VIEW=SWBPlatform.getContextPath()+"/swbadmin/jsp/resourceTab.jsp";

        /**
         * Gets the text localed.
         * 
         * @param res the res
         * @param label the label
         * @return the text localed
         */
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

        /**
         * Gets the id.
         * 
         * @param res the res
         * @return the id
         */
        public static String getId(Resource res)
        {
            if(res==null)return null;
            String ret="";
            ret+=res.getModel().getNsURIPrefix(res.getNameSpace());
            ret+=":";
            ret+=res.getLocalName();
            return ret;
        }

        /**
         * Gets the id.
         *
         * @param res the res
         * @return the id
         */
        public static Resource getResourceFromId(Model model, String id)
        {
            Resource ret=null;
            String p="";
            String name="";
            int i=id.indexOf(":");
            if(i>-1)
            {
                p=id.substring(0,i-1);
                name=id.substring(i);
            }
            String base=model.getNsPrefixURI(p);
            String url=base+name;
            ret=model.getResource(url);
            return ret;
        }

        /**
         * Gets the link.
         * 
         * @param res the res
         * @param pathView the path view
         * @return the link
         */
        public static String getLink(Resource res, String pathView)
        {
            return getLink(res,null, pathView);
        }

        /**
         * Gets the link.
         * 
         * @param res the res
         * @param text the text
         * @param pathView the path view
         * @return the link
         */
        public static String getLink(Resource res, String text, String pathView)
        {
            String val=text;
            if(val==null)val=getId(res);
            return "<a href=\"#\" onClick=\"addNewTab('"+res.getURI()+"', '"+pathView+"', '"+val+"');\">"+val+"</a>";
        }

        /**
         * Gets the object id.
         * 
         * @param res the res
         * @param ptype the ptype
         * @param model the model
         * @return the object id
         */
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

        /**
         * Gets the object link.
         * 
         * @param res the res
         * @param ptype the ptype
         * @param model the model
         * @param pathView the path view
         * @return the object link
         */
        public static String getObjectLink(Resource res, Property ptype, Model model, String pathView)
        {
            return getObjectLink(res, ptype, model, null, pathView);
        }

        /**
         * Gets the object link.
         * 
         * @param res the res
         * @param ptype the ptype
         * @param model the model
         * @param text the text
         * @param pathView the path view
         * @return the object link
         */
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

        /**
         * Checks if is in base model.
         * 
         * @param res the res
         * @param ont the ont
         * @return true, if is in base model
         */
        public static boolean isInBaseModel(Resource res, OntModel ont)
        {
            Property type=ont.getProperty(SemanticVocabulary.RDF_TYPE);
            return ont.getBaseModel().contains(res, type);
        }

        /**
         * Adds the filtered class.
         * 
         * @param cls the cls
         * @param arr the arr
         */
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

        /**
         * Gets the class properties.
         * 
         * @param res the res
         * @param model the model
         * @return the class properties
         */
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

        /**
         * Gets the base model resource.
         * 
         * @param uri the uri
         * @param ont the ont
         * @return the base model resource
         */
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
                Iterator<OntModel> it=ont.listSubModels();
                while(it.hasNext())
                {
                    OntModel model=it.next();
                    Model base2=model.getBaseModel();
                    //System.out.println("sub:"+model.getGraph().size());
                    if(base2!=base)
                    {
                        if(base2.contains(res, type))
                        {
                            res=base2.getResource(uri);
                            break;
                        }
                    }
                }
            }
            return res;
        }
    }

    public static boolean isTDB()
    {
        return persistenceType==PRESIST_TYPE_TDB;
    }

    public static boolean isSDB()
    {
        return persistenceType==PRESIST_TYPE_SDB;
    }

    public static boolean isRDB()
    {
        return persistenceType==PRESIST_TYPE_DEFAULT;
    }

}
