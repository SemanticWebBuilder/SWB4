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


/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBPlatform 
{
    private static Logger log=SWBUtils.getLogger(SWBPlatform.class);
    private static SWBPlatform instance=null;

    private static Properties props=null;

    private static String contextPath = "";
    private static String workPath = "";
    private static boolean statementsCache=true;

    private static boolean adminDev=false;
    private static String adminFile="/swbadmin/rdf/SWBAdmin.nt";
    private static String ontEditFile="/swbadmin/rdf/SWBOntEdit.nt";

    public final static String PRESIST_TYPE_DEFAULT="default";
    public final static String PRESIST_TYPE_SDB="sdb";
    public final static String PRESIST_TYPE_TDB="tdb";

    private static String persistenceType=PRESIST_TYPE_DEFAULT;

    private static IDGenerator idgen=null;

    private static SemanticMgr semanticMgr=null;

    /**
     * 
     */
    protected static final String version = "4.0.1.1";
    
    private SWBPlatform()
    {
        log.event("Initializing SemanticWebBuilder Platform...");
        init();
    }

    static public synchronized SWBPlatform createInstance() {
        if (instance == null) {
            instance = new SWBPlatform();
        }
        return instance;
    }
    
    //Initialize context
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
    
    public void setProperties(Properties props)
    {
        SWBPlatform.props=props;
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

    public static IDGenerator getIDGenerator()
    {
        return idgen;
    }
    
    public static SemanticMgr getSemanticMgr()
    {
        return semanticMgr;
    }

    public static String getVersion() {
        return version;
    }

    public static String getContextPath()
    {
        return contextPath;
    }

    public void setContextPath(String contextPath)
    {
        SWBPlatform.contextPath=contextPath;
    }


    public String getPlatformWorkPath()
    {
        return workPath;
    }

    public void setPlatformWorkPath(String workPath)
    {
        SWBPlatform.workPath=workPath;
    }
    
    public boolean getStatementsCache()
    {
        return statementsCache;
    }

    public void setStatementsCache(boolean statementsCache)
    {
        SWBPlatform.statementsCache=statementsCache;
    }

    public String getPersistenceType() {
        return persistenceType;
    }

    public void setPersistenceType(String persistenceType) {
        SWBPlatform.persistenceType = persistenceType;
    }

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


    public void removeFileFromPlatformWorkPath(String path)
    {
        //TOTO:Impementar Replicacion de archivos
        File file=new File(getPlatformWorkPath() + path);
        file.delete();
    }

    /**
     * @param path
     * @throws AFException
     * @return  */
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
     * @param path
     * @throws AFException
     * @return  */
    public String readFileFromPlatformWorkPath(String path) throws IOException, SWBException {
        return SWBUtils.IO.readInputStream(getFileFromPlatformWorkPath(path));
    }

    public boolean isAdminDev() {
        return adminDev;
    }

    public void setAdminDev(boolean adminDev) {
        SWBPlatform.adminDev = adminDev;
    }

    public String getAdminFile() {
        return adminFile;
    }

    public void setAdminFile(String adminFile) {
        SWBPlatform.adminFile = adminFile;
    }

    public String getOntEditFile() {
        return ontEditFile;
    }

    public void setOntEditFile(String ontEditFile) {
        SWBPlatform.ontEditFile = ontEditFile;
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
