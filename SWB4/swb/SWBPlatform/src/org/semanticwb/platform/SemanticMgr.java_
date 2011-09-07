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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.platform;

import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.rdf.AbstractStore;
import org.semanticwb.rdf.GraphCached;
import org.semanticwb.rdf.RDBStore;
import org.semanticwb.rdf.RemoteGraph;
import org.semanticwb.rdf.SDBStore;
import org.semanticwb.rdf.TDBStore;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticMgr.
 * 
 * @author Jei
 */
public class SemanticMgr implements SWBInstanceObject
{
    
    /** The NULL. */
    private static String NULL="__NULL__";

    /** The use cache. */
    private boolean useCache=false;

    /**
     * The Enum ModelSchema.
     */
    public enum ModelSchema {

        /** The OW l_ me m_. */
        OWL_MEM,
        /** The OW l_ me m_ tran s_ inf. */
        OWL_MEM_TRANS_INF,
        /** The OW l_ lit e_ me m_ rdf s_ inf. */
        OWL_LITE_MEM_RDFS_INF,
        /** The OW l_ me m_ min i_ rul e_ inf. */
        OWL_MEM_MINI_RULE_INF,
        /** The RDF s_ me m_ rdf s_ inf. */
        RDFS_MEM_RDFS_INF,
        /** The DAM l_ me m_ rdf s_ inf. */
        DAML_MEM_RDFS_INF,
        /** The OW l_ d l_ me m_ rdf s_ inf. */
        OWL_DL_MEM_RDFS_INF,
        
        /** The OW l_ me m_ rdf s_ inf. */
        OWL_MEM_RDFS_INF;
    }
    /** The model schema. */
    private static ModelSchema modelSchema = ModelSchema.OWL_MEM_TRANS_INF;

    /**
     * Sets the schema model.
     * 
     * @param modelSchema the new schema model
     */
    public static void setSchemaModel(ModelSchema modelSchema) {
        SemanticMgr.modelSchema = modelSchema;
    }
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SemanticMgr.class);
    /** The Constant SWBSystem. */
    public final static String SWBSystem = "SWBSystem";
    /** The Constant SWBAdmin. */
    public final static String SWBAdmin = "SWBAdmin";
    public final static String SWBAdminURI = "http://www.semanticwb.org/SWBAdmin#";
    /** The Constant SWBOntEdit. */
    public final static String SWBOntEdit = "SWBOntEdit";
    /** The m_ontology. */
    private SemanticOntology m_ontology;
    //private SemanticModel m_system;
    //private HashMap<String,SemanticModel> m_schemas;
    /** The m_schema. */
    private SemanticOntology m_schema;
    
    /** The models. */
    private HashMap<String, SemanticModel> m_models = null;
    /** The namespace related models. */
    private HashMap<String, SemanticModel> m_nsmodels = null;
    /** The interenal related models. */
    private HashMap<Model, SemanticModel> m_imodels = null;
    
    /** The Base Models. */
    private HashMap<String, SemanticModel> m_bmodels = null;

    /** The vocabulary. */
    private SemanticVocabulary vocabulary;
    /** The m_observers. */
    private List<SemanticObserver> m_observers = null;
    /** The codepkg. */
    private CodePackage codepkg = null;

    /** The Store **/
    private AbstractStore store=null;

    /* (non-Javadoc)
     * @see org.semanticwb.platform.SWBInstanceObject#init()
     */

    /**
     * Gets the model spec.
     * 
     * @return the model spec
     */
    public OntModelSpec getModelSpec()
    {
        OntModelSpec modelSpec = OntModelSpec.OWL_MEM_TRANS_INF;
        //Create Schema
        switch (modelSchema) {
            case OWL_MEM:
                modelSpec = OntModelSpec.OWL_MEM;
                break;
            case OWL_DL_MEM_RDFS_INF:
                modelSpec = OntModelSpec.OWL_DL_MEM_RDFS_INF;
                log.event("ModelSpecification: OWL_DL_MEM_RDFS_INF");
                break;
            case OWL_MEM_TRANS_INF:
                modelSpec = OntModelSpec.OWL_MEM_TRANS_INF;
                log.event("ModelSpecification: OWL_MEM_TRANS_INF");
                break;
            case OWL_MEM_MINI_RULE_INF:
                modelSpec = OntModelSpec.OWL_MEM_MINI_RULE_INF;
                log.event("ModelSpecification: OWL_MEM_MINI_RULE_INF");
                break;
            case OWL_MEM_RDFS_INF:
                modelSpec = OntModelSpec.OWL_MEM_RDFS_INF;
                log.event("ModelSpecification: OWL_MEM_RDFS_INF");
                break;
            case RDFS_MEM_RDFS_INF:
                modelSpec = OntModelSpec.RDFS_MEM_RDFS_INF;
                log.event("ModelSpecification: RDFS_MEM_RDFS_INF");
                break;
            case DAML_MEM_RDFS_INF:
                modelSpec = OntModelSpec.DAML_MEM_RDFS_INF;
                log.event("ModelSpecification: DAML_MEM_RDFS_INF");
                break;
            case OWL_LITE_MEM_RDFS_INF:
                modelSpec = OntModelSpec.OWL_LITE_MEM_RDFS_INF;
                log.event("ModelSpecification: OWL_LITE_MEM_RDFS_INF");
                break;
            default:
                modelSpec = OntModelSpec.OWL_MEM_TRANS_INF;
                log.event("ModelSpecification: OWL_MEM_TRANS_INF");
        }
        return modelSpec;
    }


    /* (non-Javadoc)
     * @see org.semanticwb.platform.SWBInstanceObject#init()
     */
    public void init() {
        log.event("Initializing SemanticMgr...");

        codepkg = new CodePackage();

        m_models = new HashMap();                     //Arreglo de SemanticModel por name
        m_nsmodels = new HashMap();                   //Arreglo de SemanticModel por NS
        m_imodels = new HashMap();                    //Arreglo de RDFModel
        m_bmodels = new HashMap();                    //Arreglo de RDFModel
        //m_schemas=new HashMap();
        m_observers = Collections.synchronizedList(new ArrayList());

        OntModelSpec modelSpec = getModelSpec();

        //Create Schema
        m_schema = new SemanticOntology("SWBSquema", ModelFactory.createOntologyModel(modelSpec));
        //System.out.println("p2");

        //Create Ontology
        m_ontology = new SemanticOntology("SWBOntology", ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF));
        //m_ontology = new SemanticOntology("SWBOntology",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RDFS_INF));
        //System.out.println("p3");

        //Agrega ontologia a los modelos
        SemanticModel ontModel = new SemanticModel("swb_ontology", m_ontology.getRDFOntModel());
        m_imodels.put(ontModel.getRDFModel(), ontModel);
        //System.out.println("p4");

        //Agrega squema a los modelos
        SemanticModel ontSchemaModel = new SemanticModel("swb_schema", m_schema.getRDFOntModel());
        //para busqueda inversa
        m_imodels.put(ontSchemaModel.getRDFModel(), ontSchemaModel);
        //System.out.println("p5");
    }

    /**
     * Initialize db.
     */
    public void initializeDB() {

        useCache=Boolean.parseBoolean(SWBPlatform.getEnv("swb/tripleFullCache","false"));
        log.event("TripleFullCache:"+useCache);

        String clsname="org.semanticwb.rdf.RDBStore";
        if (SWBPlatform.isSDB()) 
        {
            clsname="org.semanticwb.rdf.SDBStore";
        } else if (SWBPlatform.isTDB())
        {
            clsname="org.semanticwb.rdf.TDBStore";
        } else if (SWBPlatform.isBigdata())
        {
            clsname="org.semanticwb.bigdata.BigdataStore";
        } else if (SWBPlatform.isRemotePlatform())
        {
            clsname="org.semanticwb.remotetriplestore.SWBRemoteTripleStore";
        } else if (SWBPlatform.isSWBTripleStore())
        {
            clsname="org.semanticwb.triplestore.SWBTripleStore";
        }

        try
        {
            Class cls=Class.forName(clsname);
            store=(AbstractStore)cls.newInstance();
        }catch(Exception e){log.error("Error Initializing Store",e);}
        //System.out.println("End initializeDB");
        store.init();
    }

    /**
     * Adds the base ontology.
     * 
     * @param owlPath the owl path
     * @return the semantic model
     */
    public SemanticModel addBaseOntology(String owlPath) {
        Model model = SWBPlatform.getSemanticMgr().loadRDFFileModel(owlPath);
        SemanticModel smodel = new SemanticModel(new File(owlPath).getName(), model);
        getSchema().addSubModel(smodel, false);
        getOntology().addSubModel(smodel, false);
        m_bmodels.put(smodel.getName(), smodel);

        //agregar todos los NS del schema
        Iterator it = smodel.getRDFModel().getNsPrefixMap().values().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            m_nsmodels.put(key, m_imodels.get(getSchema().getRDFOntModel()));
            log.debug("Add NS:" + key + " " + getSchema().getName());
        }

        //debugModel(swbSquema);
        return smodel;
    }

    /**
     * Load base vocabulary.
     */
    public void loadBaseVocabulary() {
        //Create Vocabulary
        //System.out.println("Loading voc...");
        vocabulary = new SemanticVocabulary();

        //m_schema.getRDFOntModel().listStatements(null, RDF.type, RDFS.Class);

        Iterator<SemanticClass> tpcit = new SemanticClassIterator(m_schema.getRDFOntModel().listClasses());
        while (tpcit.hasNext()) {
            SemanticClass cls = tpcit.next();
            //System.out.println("register class:"+cls);
            vocabulary.registerClass(cls);
        }
        //System.out.println("voc ini");
        vocabulary.init();
    }

    /**
     * Load rdf remote model.
     * 
     * @param uri the uri
     * @return the model
     */
    public Model loadRDFRemoteModel(String uri) {
        Model model = null;
        try {
            URLConnection u = new URL(uri).openConnection();
            u.connect();
            model = ModelFactory.createModelForGraph(new RemoteGraph(uri));
            log.info("-->Loading Remote Model:" + uri);
        } catch (Exception e) {
            log.warn("-->Can´t create remote model:" + uri);
            //log.error("-->"+e.getMessage());
        }
        return model;
    }

    /**
     * Read remote RDFa Model from webpage.
     * 
     * @param url = url of remote HTML webpage
     * @return RDF Model
     */
    public Model loadRDFaRemoteModel(String url) {
        return loadRDFaRemoteModel(url, "HTML");
    }

    /**
     * Read remote RDFa Model from webpage.
     * 
     * @param url = url of remote webpage
     * @param lang = "HTML" or "XHTML", default HTML
     * @return RDF Model
     */
    public Model loadRDFaRemoteModel(String url, String lang) {
        Model model = null;
        try {
            Class.forName("net.rootdev.javardfa.RDFaReader");
            model = ModelFactory.createDefaultModel();
            if (lang == null) {
                lang = "HTML";
            }
            model.read(url, lang);
            log.info("-->Loading Remote RDFa Model:" + url);
        } catch (Exception e) {
            log.warn("-->Can´t create remote RDFa model:" + url);
        }
        return model;
    }

    /**
     * Load remote model.
     * 
     * @param name the name
     * @param uri the uri
     * @param baseNS the base ns
     * @param add2Ontology the add2 ontology
     * @return the semantic model
     */
    public SemanticModel loadRemoteModel(String name, String uri, String baseNS, boolean add2Ontology) {
        SemanticModel m = null;
        Model model = loadRDFRemoteModel(uri);
        if (model != null) {
            m = new SemanticModel(name, model);
            m.setNameSpace(baseNS);
            //TODO:notify this
            addModel(m, add2Ontology);
        }
        return m;
    }

    /**
     * Load remote rd fa model.
     * 
     * @param name the name
     * @param url the url
     * @param lang the lang
     * @param add2Ontology the add2 ontology
     * @return the semantic model
     */
    public SemanticModel loadRemoteRDFaModel(String name, String url, String lang, boolean add2Ontology) {
        SemanticModel m = null;
        Model model = loadRDFaRemoteModel(url, lang);
        if (model != null) {
            m = new SemanticModel(name, model);
            //TODO
//            m.setNameSpace(baseNS);
//            //TODO:notify this
//            m_models.put(name, m);
//            m_nsmodels.put(baseNS, m);
//            log.debug("Add NS:"+baseNS+" "+m.getName());
//            m_imodels.put(m.getRDFModel(), m);
//            //System.out.println("addModel:"+name+" hash:"+m.getRDFModel().toString());
//            if(add2Ontology)m_ontology.addSubModel(m,false);
        }
        return m;
    }

    /**
     * Load rdf file model.
     * 
     * @param path the path
     * @return the model
     */
    public Model loadRDFFileModel(String path) {
        return FileManager.get().loadModel(path);
    }

    /**
     * Load rdf file model.
     * 
     * @param path the path
     * @param baseUri the base uri
     * @return the model
     */
    public Model loadRDFFileModel(String path, String baseUri) {
        return FileManager.get().loadModel(path, baseUri, null);
    }

    /**
     * Read rdf file.
     * 
     * @param name the name
     * @param path the path
     * @return the semantic model
     */
    public SemanticModel readRDFFile(String name, String path) {
        SemanticModel ret = null;
        Model m = loadRDFFileModel(path);
        if (m != null) {
            ret = new SemanticModel(name, m);
        }
        return ret;
    }

    /**
     * Load rdfdb model.
     * 
     * @param name the name
     * @return the model
     */
    private Model loadRDFDBModel(String name) {
        return store.loadModel(name);
    }

//    public void debugClasses(OntModel model)
//    {
//        log.debug("**************************** debugClasses ********************************");
//        for (Iterator i = model.listClasses();  i.hasNext(); ) {
//            // now list the classes
//            OntClass cls=(OntClass)i.next();
//            log.debug("cls:"+cls.getLocalName());
//            ExtendedIterator it=cls.listInstances();
//            while(it.hasNext())
//            {
//                log.trace("-->inst:"+it.next());
//            }
//            
//            log.debug("  is a Declared Propertie of " );
//            for (Iterator it2 = cls.listDeclaredProperties(false); it2.hasNext();) 
//            {
//                Property prop=(Property)it2.next();
//                log.trace("---->prop:"+prop);
//            }            
//        }        
//    }
//    public void debugResource(Resource res)
//    {
//        log.debug("**************************** debugModel ********************************");
//        StmtIterator i = res.listProperties();
//        while(i.hasNext()) 
//        {
//            Statement stm=i.nextStatement();
//            log.trace("stmt:"+stm.getSubject()+" "+stm.getPredicate()+" "+stm.getObject());
//        }        
//    }    
//    
//    public void debugModel(Model model)
//    {
//        log.debug("**************************** debugModel ********************************");
//        StmtIterator i = model.listStatements();
//        while(i.hasNext()) 
//        {
//            Statement stm=i.nextStatement();
//            log.trace("stmt:"+stm.getSubject()+" "+stm.getPredicate()+" "+stm.getObject());
//        }        
//    }    
    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    public void finalize() throws Throwable
    {
        super.finalize();
        if(store!=null)
        {
            store.close();
        }
        log.event("SemanticMgr stoped...");
    }

    /**
     * Gets the models.
     * 
     * @return the models
     */
    public Set<Entry<String, SemanticModel>> getModels() {
        return m_models.entrySet();
    }

    /**
     * Gets the model.
     * 
     * @param name the name
     * @return the model
     */
    public SemanticModel getModel(String name) {
        //System.out.println("getModel:"+name+" "+m_models.get(name));
        return m_models.get(name);
    }

    /**
     * Gets the model by ns.
     * 
     * @param nameSpace the name space
     * @return the model by ns
     */
    public SemanticModel getModelByNS(String nameSpace) {
        return m_nsmodels.get(nameSpace);
    }

    /**
     * Gets the model.
     * 
     * @param model the model
     * @return the model
     */
    public SemanticModel getModel(Model model) {
        return m_imodels.get(model);
    }

    /**
     * List base models.
     * 
     * @return the iterator
     */
    public Iterator<SemanticModel> listBaseModels()
    {
        return m_bmodels.values().iterator();
    }

    /**
     * Load db models.
     */
    public void loadDBModels()
    {
        log.debug("loadDBModels");
        //LoadModels
        Iterator<String> it = store.listModelNames();
        while (it.hasNext()) {
            String name = it.next();
            log.trace("LoadingModel:" + name);
            SemanticModel model = loadDBModel(name);
            model.setDataset(store.getDataset());
        }
    }

    /**
     * Load a Model, if the model don't exist, it will be created.
     *
     * @param name the name
     * @return the semantic model
     * @return
     */
    private SemanticModel loadDBModel(String name) {
        return loadDBModel(name, useCache);
    }

    /**
     * Load a Model, if the model don't exist, it will be created.
     * 
     * @param name the name
     * @param cached the cached
     * @return the semantic model
     * @return
     */
    private SemanticModel loadDBModel(String name, boolean cached) {
        //new Exception().printStackTrace();
        Model model = loadRDFDBModel(name);
        if (cached) {
            //System.out.println("Cache:"+name);
            model = new ModelCom(new GraphCached((model.getGraph())));
        }
        if (name.equals(SWBAdmin) && !SWBPlatform.createInstance().isAdminDev()) {
            //System.out.println(model);
            log.info("Loading SWBAdmin...");
            OntModel omodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
            try {
                Model m = ModelFactory.createDefaultModel();
                FileInputStream in = new FileInputStream(SWBUtils.getApplicationPath() + SWBPlatform.createInstance().getAdminFile());
                m.read(in, null, "N-TRIPLE");
                omodel.addSubModel(m, true);
                in.close();
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
            model = omodel;
        } else if (name.equals(SWBAdmin)) {
            NsIterator it = model.listNameSpaces();
            if (!it.hasNext()) {
                log.info("Importing SWBAdmin...");
                it.close();
                try {
                    FileInputStream in = new FileInputStream(SWBUtils.getApplicationPath() + SWBPlatform.createInstance().getAdminFile());
                    if (model instanceof ModelRDB) {
                        ModelRDB m = (ModelRDB) model;
                        try {
                            //m.setDoDuplicateCheck( false );
                            m.begin();
                            m.read(in, null, "N-TRIPLE");
                            in.close();
                        } catch (Exception e) {
                            log.error(e);
                        } finally {
                            m.commit();
                            //m.setDoDuplicateCheck(true);
                        }
                    } else {
                        model.read(in, null, "N-TRIPLE");
                        in.close();
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        } else if (name.equals(SWBOntEdit) && !SWBPlatform.createInstance().isAdminDev()) {
            log.info("Loading SWBOntEdit...");
            OntModel omodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
            try {
                Model m = ModelFactory.createDefaultModel();
                FileInputStream in = new FileInputStream(SWBUtils.getApplicationPath() + SWBPlatform.createInstance().getOntEditFile());
                m.read(in, null, "N-TRIPLE");
                omodel.addSubModel(m, true);
                in.close();
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
            model = omodel;
        } else if (name.equals(SWBOntEdit)) {
            NsIterator it = model.listNameSpaces();
            if (!it.hasNext()) {
                log.info("Importing SWBOntEdit...");
                it.close();
                try {
                    FileInputStream in = new FileInputStream(SWBUtils.getApplicationPath() + SWBPlatform.createInstance().getOntEditFile());
                    if (model instanceof ModelRDB) {
                        ModelRDB m = (ModelRDB) model;
                        try {
                            //m.setDoDuplicateCheck( false );
                            m.begin();
                            m.read(in, null, "N-TRIPLE");
                            in.close();
                        } catch (Exception e) {
                            log.error(e);
                        } finally {
                            m.commit();
                            //m.setDoDuplicateCheck(true);
                        }
                    } else {
                        model.read(in, null, "N-TRIPLE");
                        in.close();
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }
        SemanticModel m = null;
        //Verificar si es una ontologia
        Resource res=model.getResource(model.getNsPrefixURI(name)+name);
        //System.out.println("uri:"+model.getNsPrefixURI(name)+name);
        StmtIterator it=res.listProperties(RDF.type);
        while(it.hasNext())
        {
            Statement stm=it.next();
            Resource type=stm.getResource();
            //System.out.println("Type:"+type);
            if(type!=null && type.getLocalName().equals("Ontology"))
            {
                model=new ModelCom(new GraphCached((model.getGraph())));
                m = new SemanticModel(name, model);
                //System.out.println("cache ontology:"+m);
            }
        }
        it.close();

        if(m==null) //No es una ontologia
        {
            m = new SemanticModel(name, model);
        }

        //TODO:notify this
        //System.out.println("Model:"+name);
        addModel(m, true);
        return m;
    }

    public void addModel(SemanticModel model, boolean add2Ontology)
    {
        m_models.put(model.getName(), model);
        m_nsmodels.put(model.getNameSpace(), model);
        log.debug("Add NS:" + model.getNameSpace() + " " + model.getName());
        m_imodels.put(model.getRDFModel(), model);
        if (add2Ontology) {
            m_ontology.addSubModel(model, false);
        }
    }

    /**
     * Creates the model.
     *
     * @param name the name
     * @param nameSpace the name space
     * @return the semantic model
     */
    public SemanticModel createModel(String name, String nameSpace) {
        return createDBModel(name, nameSpace, useCache);
    }

    /**
     * Creates the model.
     * 
     * @param name the name
     * @param nameSpace the name space
     * @param cached the cached
     * @return the semantic model
     */
    public SemanticModel createDBModel(String name, String nameSpace, boolean cached) {
        //System.out.println("createModel:"+name+" "+nameSpace);
        Model model = loadRDFDBModel(name);
        model.setNsPrefix(name, nameSpace);
        model.close();

        SemanticModel ret = loadDBModel(name, cached);
        model = ret.getRDFModel();
//        model.setNsPrefix(name+"_"+SemanticVocabulary.SWB_NS, nameSpace);
//        model.setNsPrefix(name, SemanticVocabulary.URI+SemanticVocabulary.SWB_NS);
        //model.setNsPrefixes(m_schema.getRDFOntModel().getNsPrefixMap());
        return ret;
    }

    /**
     * Creates the model by rdf.
     * 
     * @param name the name
     * @param namespace the namespace
     * @param in the in
     * @return the semantic model
     */
    public SemanticModel createDBModelByRDF(String name, String namespace, InputStream in) {
        return createDBModelByRDF(name, namespace, in, null);
    }

    /**
     * Creates the model by rdf.
     * 
     * @param name the name
     * @param namespace the namespace
     * @param in the in
     * @param lang the lang
     * @return the semantic model
     */
    public SemanticModel createDBModelByRDF(String name, String namespace, InputStream in, String lang) {
        SemanticModel ret = createModel(name, namespace);
        Model model = ret.getRDFModel();
        try {
            if (model.supportsTransactions()) {
                try {
                    //m.setDoDuplicateCheck( false );
                    model.begin();
                    model.read(in, null, lang);
                    in.close();
                } catch (Exception e) {
                    log.error(e);
                } finally {
                    model.commit();
                    //m.setDoDuplicateCheck(true);
                }
            } else {
                model.read(in, null, lang);
                in.close();
            }
//        ret.getRDFModel().read(in, null,lang);
//        try
//        {
//            in.close();
        } catch (Exception e) {
            log.error(e);
        }
        return ret;
    }

    /**
     * Removes the model.
     * 
     * @param name the name
     */
    public void removeModel(String name) {
        SemanticModel model = m_models.get(name);
        //TODO:notify this
        m_models.remove(name);
        m_nsmodels.remove(model.getNameSpace());
        m_imodels.remove(model.getRDFModel());
        m_ontology.removeSubModel(model, true);

        store.removeModel(name);
    }

    /**
     * Gets the ontology.
     * 
     * @return the ontology
     */
    public SemanticOntology getOntology() {
        return m_ontology;
    }

    /**
     * Gets the schema.
     * 
     * @return the schema
     */
    public SemanticOntology getSchema() {
        return m_schema;
    }

//    public SemanticModel getSchemaModel(String name)
//    {
//        return m_schemas.get(name);
//    }
//    public SemanticModel getSystemModel()
//    {
//        return m_system;
//    }
//    public OntClass getOntClass(String uri)
//    {
//        return m_ontology.getRDFOntModel().getOntClass(uri);
//    }
    /**
     * Gets the vocabulary.
     *
     * @return the vocabulary
     */
    public SemanticVocabulary getVocabulary() {
        return vocabulary;
    }

    /**
     * Register observer.
     * 
     * @param obs the obs
     */
    public void registerObserver(SemanticObserver obs) {
        m_observers.add(obs);
    }

    /**
     * Removes the observer.
     * 
     * @param obs the obs
     */
    public void removeObserver(SemanticObserver obs) {
        m_observers.remove(obs);
    }

    /**
     * Notify change.
     * 
     * @param obj the obj
     * @param prop the prop
     * @param lang the lang
     * @param action the action
     */
    public void notifyChange(SemanticObject obj, Object prop, String lang, String action) {
        log.trace("notifyChange: obj:" + obj + " prop:" + prop + " " + action);
        if (obj.getURI() != null) {
            if (prop != null && prop instanceof SemanticProperty) {
                if (((SemanticProperty) prop).isNotObservable()) {
                    return; //No es observable
                }
            }
            Iterator it = m_observers.iterator();
            while (it.hasNext()) {
                SemanticObserver obs = (SemanticObserver) it.next();
                try {
                    obs.notify(obj, prop, lang, action);
                } catch (Exception e) {
                    log.error(e);
                }
            }
            SemanticClass cls=obj.getSemanticClass();
            if(cls!=null)
            {
                try {
                    cls.notifyChange(obj, prop, lang, action);
                } catch (Exception e) {
                    log.error(e);
                }
            }
            if(prop!=null && prop instanceof SemanticProperty)
            {
                try {
                    ((SemanticProperty) prop).notifyChange(obj, prop, lang, action);
                } catch (Exception e) {
                    log.error(e);
                }
            }

        }
    }

    /**
     * Notify external change.
     * 
     * @param uri the uri
     * @param puri the puri
     * @param lang the lang
     * @param action the action
     */
    public void processExternalChange(String uri, String puri, String lang, String action)
    {
        //System.out.println("processExternalChange");
        SemanticProperty prop=null;
        if(puri!=null)prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(puri);

        log.trace("notifyExternalChange: obj:" + uri + " prop:" + prop + " " + action);
        SemanticObject obj=SemanticObject.getSemanticObject(uri);
        if(prop!=null && prop.isInverseOf() && obj==null)obj=SemanticObject.createSemanticObject(uri);
        if (obj != null)
        {
            if(prop!=null)           //Is a property
            {
                if(prop.isObjectProperty())
                {
                    if(prop.isInverseOf())
                    {
                        SemanticObject object=null;
                        Object aux=obj.getPropertyValueCache(prop, null);
                        if(aux!=null && aux!=NULL)object=(SemanticObject)aux;
                        if(object!=null)
                        {
                            SemanticProperty inv=prop.getInverse();
                            if(inv.getCardinality()==1)
                            {
                                object.removePropertyValueCache(prop.getInverse(), NULL);
                                //if(old!=null && old instanceof SemanticObject)((SemanticObject)old).removePropertyValueCache(prop.getInverse(), NULL);
                            }else
                            {
                                object.removePropertyValueCache(prop.getInverse(), "list");
                                //if(old!=null && old instanceof SemanticObject)((SemanticObject)old).removePropertyValueCache(prop.getInverse(), "list");
                            }
                        }
                    }
                }
                obj.removePropertyValueCache((SemanticProperty)prop, lang);
                if(prop.isObjectProperty())
                {
                    if(prop.isInverseOf())
                    {
                        SemanticObject object=obj.getObjectProperty(prop);
                        if(object!=null)
                        {
                            SemanticProperty inv=prop.getInverse();
                            if(inv.getCardinality()==1)
                            {
                                object.removePropertyValueCache(prop.getInverse(), NULL);
                                //if(old!=null && old instanceof SemanticObject)((SemanticObject)old).removePropertyValueCache(prop.getInverse(), NULL);
                            }else
                            {
                                object.removePropertyValueCache(prop.getInverse(), "list");
                                //if(old!=null && old instanceof SemanticObject)((SemanticObject)old).removePropertyValueCache(prop.getInverse(), "list");
                            }
                        }
                    }
                }
            }else
            {
                if(puri!=null)                                           //add or remove class
                {
                    obj.resetCache();
                }else                                                    //is a object
                {
                    if(action.equals(SemanticObject.ACT_REMOVE))
                    {
                        obj.resetRelatedsCache();
                        SemanticObject.removeCache(uri);
                    }
                }
            }
        }
    }


    /**
     * Gets the code package.
     * 
     * @return the code package
     * @return
     */
    public CodePackage getCodePackage() {
        return codepkg;
    }

    /**
     * Close all models.
     */
    public void close() {
        //System.out.println("ServerMgr.Close()");
        store.close();
        store=null;
    }

    /**
     * Destroy method for this filter.
     */
    public void destroy() {
        close();
    }

    /**
     * Creates the key pair.
     */
    public void createKeyPair() {
        SemanticModel model = getModel(SWBAdmin);
        if (null!=model && null!=model.getModelObject()){
            String[] llaves = SWBUtils.CryptoWrapper.storableKP();
            SemanticProperty priv = model.createSemanticProperty(SWBAdminURI + "/PrivateKey", model.getModelObject().getSemanticClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_STRING);
            SemanticProperty publ = model.createSemanticProperty(SWBAdminURI + "/PublicKey", model.getModelObject().getSemanticClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_STRING);
            model.getModelObject().setProperty(priv, llaves[0]);
            model.getModelObject().setProperty(publ, llaves[1]);
        }
    }

    public AbstractStore getSWBStore(){
        return store;
    }

}
