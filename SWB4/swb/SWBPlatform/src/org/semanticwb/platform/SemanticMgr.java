/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.db.impl.Driver_MySQL_SWB;
import com.hp.hpl.jena.db.impl.IRDBDriver;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.StoreDesc;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.sql.SDBConnection_SWB;
import com.hp.hpl.jena.util.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.db.DBConnectionPool;

/**
 *
 * @author Jei
 */
public class SemanticMgr implements SWBInstanceObject
{
    private static Logger log = SWBUtils.getLogger(SemanticMgr.class);

    public final static String SWB_OWL_PATH=SWBPlatform.getEnv("swb/ontologyFiles","/WEB-INF/owl/swb.owl");
    public final static String SWB_PERSIST=SWBPlatform.getEnv("swb/triplepersist","default");
    public final static String SWBSystem="SWBSystem";
    public final static String SWBAdmin="SWBAdmin";
    
    private SWBPlatform m_context;
    
    private SemanticOntology m_ontology;
//    private SemanticModel m_system;
    //private HashMap<String,SemanticModel> m_schemas;
    private SemanticOntology m_schema;
    private HashMap<String,SemanticModel>m_models=null;
    private HashMap<String,SemanticModel>m_nsmodels=null;
    private HashMap<Model,SemanticModel>m_imodels=null;

    private IDBConnection conn;
    private ModelMaker maker;
    private Store store;
    
    private SemanticVocabulary vocabulary;

    private ArrayList<SemanticObserver> m_observers=null;

    public void init(SWBPlatform context) 
    {
        log.event("Initializing SemanticMgr...");
        this.m_context=context;
        
        m_models=new HashMap();                     //Arreglo de SemanticModel por name
        m_nsmodels=new HashMap();                   //Arreglo de SemanticModel por NS
        m_imodels=new HashMap();                    //Arreglo de RDFModel
        //m_schemas=new HashMap();
        m_observers=new ArrayList();

        DBConnectionPool pool=SWBUtils.DB.getDefaultPool();
        String M_DB_URL         = pool.getURL();
        String M_DB_USER        = pool.getUser();
        String M_DB_PASSWD      = pool.getPassword();
        String M_DB             = SWBUtils.DB.getDatabaseType(pool.getName());

        if(SWB_PERSIST.equalsIgnoreCase("sdb"))
        {
            StoreDesc sd=new StoreDesc("layout2", M_DB);
            //SDBConnection con=new SDBConnection(M_DB_URL, M_DB_USER, M_DB_PASSWD);
            SDBConnection con=new SDBConnection(SWBUtils.DB.getDefaultPool().newAutoConnection());
            //SDBConnection con=new SDBConnection_SWB();
            store = SDBFactory.connectStore(con,sd);
            //Revisar si las tablas existen
            List list=store.getConnection().getTableNames();
            if(!list.contains("nodes") && !list.contains("triples") && !list.contains("quads"))
            {
                store.getTableFormatter().create();
            }            
        }else
        {

//        // create a database connection
//        conn = new DBConnection(M_DB_URL, M_DB_USER, M_DB_PASSWD, M_DB);

            // Create database connection
            conn = new DBConnection(SWBUtils.DB.getDefaultPool().newAutoConnection(), M_DB);

            if(M_DB.equals("MySQL"))
            {
                IRDBDriver driver=new Driver_MySQL_SWB();
                driver.setConnection(conn);
                conn.setDriver(driver);
            }
            conn.getDriver().setTableNamePrefix("swb_");
            conn.getDriver().setDoDuplicateCheck(false);
            maker = ModelFactory.createModelRDBMaker(conn);
        }

        //Create Schema
        m_schema = new SemanticOntology("SWBSquema",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_TRANS_INF));
        //Create Ontology
        m_ontology = new SemanticOntology("SWBOntology",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM));
        
        //Load Ontology from file
        StringTokenizer st=new StringTokenizer(SWB_OWL_PATH,",;");
        while(st.hasMoreTokens())
        {
            String file=st.nextToken();
            String swbowl="file:"+SWBUtils.getApplicationPath()+file;
            File owlf=new File(swbowl);
            log.debug("Loading Model:"+owlf.getName());
            Model model=loadRDFFileModel(swbowl);
            SemanticModel smodel=new SemanticModel(owlf.getName(),model);
//            m_models.put(owlf.getName(),smodel);
//            m_imodels.put(model, smodel);
            m_schema.addSubModel(smodel,false);
            m_ontology.addSubModel(smodel,false);
            //debugModel(swbSquema);
        }

        //Agrega ontologia a los modelos
        SemanticModel ontModel=new SemanticModel("swb_ontology", m_ontology.getRDFOntModel());
//        m_models.put("swb_ontology", ontModel);
        m_imodels.put(ontModel.getRDFModel(), ontModel);

        //Agrega squema a los modelos
        SemanticModel ontSchemaModel=new SemanticModel("swb_schema", m_schema.getRDFOntModel());
//        m_models.put("swb_schema", ontSchemaModel);
        //para busqueda inversa
        m_imodels.put(ontSchemaModel.getRDFModel(), ontSchemaModel);
        //agregar todos los NS del schema
        Iterator it=ontSchemaModel.getRDFModel().getNsPrefixMap().values().iterator();
        while(it.hasNext())
        {
            String key=(String)it.next();
            m_nsmodels.put(key, ontSchemaModel);
            log.debug("Add NS:"+key+" "+ontSchemaModel.getName());
        }
        
        //Create Vocabulary
        vocabulary=new SemanticVocabulary();
        Iterator<SemanticClass> tpcit=new SemanticClassIterator(m_schema.getRDFOntModel().listClasses());
        while(tpcit.hasNext())
        {
            SemanticClass cls=tpcit.next();
            vocabulary.registerClass(cls);
        }
        vocabulary.init();
        
        
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

        loadDBModels();
        m_ontology.rebind();
    }
    
    public Model loadRDFFileModel(String path)
    {
        return FileManager.get().loadModel(path);
    }

    public Model loadRDFFileModel(String path, String baseUri)
    {
        return FileManager.get().loadModel(path,baseUri,null);
    }

    public SemanticModel readRDFFile(String name, String path)
    {
        SemanticModel ret=null;
        Model m=loadRDFFileModel(path);
        if(m!=null)
        {
            ret=new SemanticModel(name, m);
        }
        return ret;
    }
    
    private Model loadRDFDBModel(String name)
    {
        Model ret=null;
        // create or open the default model
        if(SWB_PERSIST.equals("sdb"))
        {
            ret=SDBFactory.connectNamedModel(store, name);
        }else
        {
            ret=maker.openModel(name);
        }
        return ret;
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
    
    
    
    @Override
    public void finalize()
    {
        log.event("SemanticMgr stoped...");
        if(conn!=null)
        {
            try
            {
                //Close the database connection        
                conn.close();
            }catch(Exception e){log.error(e);}        
        }
    }
    
    public Set<Entry<String, SemanticModel>> getModels()
    {
        return m_models.entrySet();
    }
    
    public SemanticModel getModel(String name)
    {
        return m_models.get(name);
    }

    public SemanticModel getModelByNS(String nameSpace)
    {
        return m_nsmodels.get(nameSpace);
    }

    public SemanticModel getModel(Model model)
    {
        return m_imodels.get(model);
    }
    
    private void loadDBModels()
    {
        log.debug("loadDBModels");
        //LoadModels
        if(SWB_PERSIST.equalsIgnoreCase("sdb"))
        {
            Dataset set=SDBFactory.connectDataset(store);
            Iterator<String>it=set.listNames();
            while(it.hasNext())
            {
                String name=it.next();
                log.trace("LoadingModel:"+name);
                loadDBModel(name);
            }
        }else
        {
            Iterator tpit=maker.listModels();
            while(tpit.hasNext())
            {
                String name=(String)tpit.next();
                log.trace("LoadingModel:"+name);
                loadDBModel(name);
            }
        }
    }
    
    /**
     * Load a Model, if the model don't exist, it will be created 
     * @param name
     * @return
     */
    private SemanticModel loadDBModel(String name)
    {
        Model model=loadRDFDBModel(name);
        if(name.equals(SWBAdmin) && !SWBPlatform.getEnv("swb/adminDev", "false").equalsIgnoreCase("true"))
        {
            System.out.println("Loading Admin...");
            OntModel omodel=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,model);
            try
            {
                Model m = ModelFactory.createDefaultModel() ;
                FileInputStream in=new FileInputStream(SWBUtils.getApplicationPath()+SWBPlatform.getEnv("swb/adminFile", "/swbadmin/rdf/SWBAdmin.rdf"));
                m.read(in, null);
                omodel.addSubModel(m,true);
            }catch(Exception e){log.warn(e.getMessage());}
            model=omodel;
        }else if(name.equals(SWBAdmin))
        {
            NsIterator it=model.listNameSpaces();
            if(!it.hasNext())
            {
                System.out.println("Importing Admin...");
                it.close();
                try
                {
                    FileInputStream in=new FileInputStream(SWBUtils.getApplicationPath()+SWBPlatform.getEnv("swb/adminFile", "/swbadmin/rdf/SWBAdmin.rdf"));
                    model.read(in, null);
                }catch(Exception e){log.warn(e.getMessage());}
            }
        }
        SemanticModel m=new SemanticModel(name, model);
        //TODO:notify this
        m_models.put(name, m);
        m_nsmodels.put(m.getNameSpace(), m);
        log.debug("Add NS:"+m.getNameSpace()+" "+m.getName());
        m_imodels.put(m.getRDFModel(), m);
        //System.out.println("addModel:"+name+" hash:"+m.getRDFModel().toString());
        if(SWBPlatform.isUseDB())m_ontology.addSubModel(m,false);
        return m;
    }    
    
    public SemanticModel createModel(String name, String nameSpace)
    {
        //System.out.println("createModel:"+name+" "+nameSpace);
        SemanticModel ret=loadDBModel(name);
        Model model=ret.getRDFModel();
//        model.setNsPrefix(name+"_"+SemanticVocabulary.SWB_NS, nameSpace);
//        model.setNsPrefix(name, SemanticVocabulary.URI+SemanticVocabulary.SWB_NS);
        model.setNsPrefix(name, nameSpace);
        //model.setNsPrefixes(m_schema.getRDFOntModel().getNsPrefixMap());
        return ret;
    }

    public SemanticModel createModelByRDF(String name, String namespace, InputStream in)
    {
        SemanticModel ret=createModel(name, namespace);
        ret.getRDFModel().read(in, "");
        return ret;
    }
     
    public void removeModel(String name)
    {
        SemanticModel model=m_models.get(name);
        //TODO:notify this
        m_models.remove(name);
        m_nsmodels.remove(model.getNameSpace());
        m_imodels.remove(model.getRDFModel());
        m_ontology.removeSubModel(model,true);
        maker.removeModel(name);
    }
        
    
    public SemanticOntology getOntology() 
    {
        return m_ontology;
    }
    
    public SemanticOntology getSchema()
    {
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
    
    public SemanticVocabulary getVocabulary() {
        return vocabulary;
    }

    public void registerObserver(SemanticObserver obs)
    {
        m_observers.add(obs);
    }

    public void removeObserver(SemanticObserver obs)
    {
        m_observers.remove(obs);
    }

    public void notifyChange(SemanticObject obj, Object prop, String action)
    {
        log.trace("obj:"+obj+" prop:"+prop+" "+action);
        if(obj.getURI()!=null)
        {
            if(prop !=null && prop instanceof SemanticProperty)
            {
                if(((SemanticProperty)prop).isNoObservable())return; //No es observable
            }
            Iterator it=m_observers.iterator();
            while(it.hasNext())
            {
                SemanticObserver obs=(SemanticObserver)it.next();
                try
                {
                    obs.notify(obj, prop, action);
                }catch(Exception e){log.error(e);}
            }
        }
    }
}
