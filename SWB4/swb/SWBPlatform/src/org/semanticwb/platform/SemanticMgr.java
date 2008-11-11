/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class SemanticMgr implements SWBInstanceObject
{
    private static Logger log = SWBUtils.getLogger(SemanticMgr.class);

    public final static String SWB_OWL_PATH=SWBPlatform.getEnv("swb/ontologyFile","/WEB-INF/owl/swb.owl");
    public final static String SWBSystem="SWBSystem";
    public final static String SWBAdmin="SWBAdmin";
    
    private SWBPlatform m_context;
    
    private SemanticOntology m_ontology;
    private SemanticModel m_system;
    private SemanticModel m_schema;
    private HashMap <String,SemanticModel>m_models=null;
    private HashMap <Model,SemanticModel>m_imodels=null;

    private IDBConnection conn;
    private ModelMaker maker;
    
    private SemanticVocabulary vocabulary;

    public void init(SWBPlatform context) 
    {
        log.event("Initializing SemanticMgr...");
        this.m_context=context;
        
        m_models=new HashMap();
        m_imodels=new HashMap();
        
        // Create database connection
        conn = new DBConnection(SWBUtils.DB.getDefaultConnection(), SWBUtils.DB.getDatabaseName());
        conn.getDriver().setTableNamePrefix("swb_");
        conn.getDriver().setDoDuplicateCheck(false);
        maker = ModelFactory.createModelRDBMaker(conn);
        
        //load SWBSystem Model
        log.debug("Loading DBModel:"+"SWBSystem");
        m_system=new SemanticModel("SWBSystem",loadRDFDBModel("SWBSystem"));
//        debugModel(m_system);

        //Load Ontology from file
        String swbowl="file:"+SWBUtils.getApplicationPath()+SWB_OWL_PATH;
        log.debug("Loading Model:"+swbowl);
        m_schema=new SemanticModel("SWBSchema",loadRDFFileModel(swbowl));
//        debugModel(swbSquema);
        
        OntModelSpec spec = OntModelSpec.OWL_MEM_RDFS_INF;
//        OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_MEM );
//        spec.setBaseModelMaker(maker);
//        spec.setImportModelMaker(maker);
        
        //Create Omtology
        m_ontology = new SemanticOntology("SWB",ModelFactory.createOntologyModel(spec,m_schema.getRDFModel()));
        //m_ontology.addSubModel(m_schema,false);
        m_ontology.addSubModel(m_system,false);
        
        //Agrega ontologia a los modelos
        SemanticModel ontModel=new SemanticModel("swb_ontology", m_ontology.getRDFOntModel());
        m_models.put("swb_ontology", ontModel);
        m_imodels.put(ontModel.getRDFModel(), ontModel);
        
        //Create Vocabulary
        vocabulary=new SemanticVocabulary();
        SemanticClassIterator tpcit=new SemanticClassIterator(m_ontology.getRDFOntModel().listClasses());
        while(tpcit.hasNext())
        {
            SemanticClass cls=tpcit.nextSemanticClass();
            if(cls!=null && cls.getName()!=null)
            {
                vocabulary.registerClass(cls);
            }
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

        loadDBModels();
        m_ontology.rebind();
    }
    
    private Model loadRDFFileModel(String path)
    {
        return FileManager.get().loadModel(path);
    }
    
    private Model loadRDFDBModel(String name)
    {
        // create or open the default model
        return maker.openModel(name);
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

    public SemanticModel getModel(Model model)
    {
        return m_imodels.get(model);
    }
    
    private void loadDBModels()
    {
        //LoadModels
        log.debug("loadDBModels");
        Iterator tpit=maker.listModels();
        while(tpit.hasNext())
        {
            String name=(String)tpit.next();
            log.trace("LoadingModel:"+name);
            loadDBModel(name);
        }    
    }
    
    /**
     * Load a Model, if the model don't exist, it will be created 
     * @param name
     * @return
     */
    private SemanticModel loadDBModel(String name)
    {
        SemanticModel m=new SemanticModel(name, loadRDFDBModel(name));
        m_models.put(name, m);
        m_imodels.put(m.getRDFModel(), m);
        //System.out.println("addModel:"+name+" hash:"+m.getRDFModel().toString());
        m_ontology.addSubModel(m,false);
        return m;
    }    
    
    public SemanticModel createModel(String name, String nameSpace)
    {
        SemanticModel ret=loadDBModel(name);
        Model model=ret.getRDFModel();
//        model.setNsPrefix(name+"_"+SemanticVocabulary.SWB_NS, nameSpace);
//        model.setNsPrefix(name, SemanticVocabulary.URI+SemanticVocabulary.SWB_NS);
        model.setNsPrefix(name, nameSpace);
        return ret;
    }
     
    public void removeModel(String name)
    {
        SemanticModel model=m_models.get(name);
        m_models.remove(name);
        m_imodels.remove(model.getRDFModel());
        maker.removeModel(name);
    }
        
    
    public SemanticOntology getOntology() 
    {
        return m_ontology;
    }
    
    public SemanticModel getSchemaModel() 
    {
        return m_schema;
    }    
    
    public SemanticModel getSystemModel() 
    {
        return m_system;
    }
    
//    public OntClass getOntClass(String uri)
//    {
//        return m_ontology.getRDFOntModel().getOntClass(uri);
//    }
    
    public SemanticVocabulary getVocabulary() {
        return vocabulary;
    }
    
    /**
     * Regresa contador en base a la cadena <i>name</i>, sin incrementar el valor del mismo
     */
    public synchronized long getCounterValue(String name)
    {
        SemanticModel model=getSystemModel();
        Resource res=model.getRDFModel().createResource(name);
        Property prop=model.getRDFModel().createProperty("swb:count");
        StmtIterator it=model.getRDFModel().listStatements(res, prop, (String)null);
        if(it.hasNext())
        {
            Statement stmt=it.nextStatement();
            return stmt.getLong();
        }
        return 0;
    }
    
    /**
     * Asigna el valor <i>val</a> al contador de nombre <i>name</i>
     */
    public synchronized void setCounterValue(String name, long val)
    {
        SemanticModel model=getSystemModel();
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
    }
    
    
    /**
     * Regresa contador en base a la cadena <i>name</i>, e incrementa el valor en uno
     */
    public synchronized long getCounter(String name)
    {
        long ret=getCounterValue(name);
        ret++;
        setCounterValue(name, ret);
        return ret;
    }
    
    public synchronized void deleteCounterValue(String name)
    {
        SemanticModel model=getSystemModel();
        Resource res=model.getRDFModel().createResource(name);
        Property prop=model.getRDFModel().createProperty("swb:count");
        model.getRDFModel().remove(res, prop, null);
    }    
}
