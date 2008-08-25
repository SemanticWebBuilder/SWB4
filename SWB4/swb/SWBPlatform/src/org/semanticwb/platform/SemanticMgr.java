/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.GraphRDB;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    private IDBConnection conn;
    private ModelMaker maker;
    
    private SemanticVocabulary vocabulary;

    public void init(SWBPlatform context) 
    {
        log.event("SemanticMgr initialized...");
        this.m_context=context;
        
        m_models=new HashMap();
        
        // Create database connection
        conn = new DBConnection(SWBUtils.DB.getDefaultConnection(), SWBUtils.DB.getDatabaseName());
        conn.getDriver().setTableNamePrefix("swb_");
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
        
        //Create Omtology
        m_ontology = new SemanticOntology("SWB",ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,m_schema.getRDFModel()));
        //m_ontology.addSubModel(m_schema,false);
        m_ontology.addSubModel(m_system,false);
        
        //Create Vocabulary
        vocabulary=new SemanticVocabulary();
        SemanticClassIterator tpcit=new SemanticClassIterator(m_ontology.getRDFOntModel().listClasses());
        while(tpcit.hasNext())
        {
            SemanticClass tpc=tpcit.nextSemanticClass();
            if(tpc!=null && tpc.getName()!=null)
            {
                log.trace("Registering SemanticClass:"+tpc+" --> "+tpc.getClassName());
                vocabulary.addSemanticClass(tpc);
                Iterator<SemanticProperty> tppit=tpc.listProperties();
                while(tppit.hasNext())
                {
                    SemanticProperty tpp=tppit.next();
                    if(tpc.equals(tpp.getDomainClass()))
                    {
                        vocabulary.addSemanticProperty(tpp);
                    }
                }
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
        String name=model.getNsURIPrefix(SemanticVocabulary.URI+SemanticVocabulary.SWB_NS);
        System.out.println("name:"+name);
        return m_models.get(name);
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
        //System.out.println("addModel:"+name+" hash:"+m.getRDFModel().toString());
        m_ontology.addSubModel(m,false);
        return m;
    }    
    
    public SemanticModel createModel(String name, String nameSpace)
    {
        SemanticModel ret=loadDBModel(name);
        Model model=ret.getRDFModel();
        model.setNsPrefix(name+"_"+SemanticVocabulary.SWB_NS, nameSpace);
        model.setNsPrefix(name, SemanticVocabulary.URI+SemanticVocabulary.SWB_NS);
        return ret;
    }
     
    public void removeModel(String name)
    {
        SemanticModel model=m_models.get(name);
        m_models.remove(name);
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
}
