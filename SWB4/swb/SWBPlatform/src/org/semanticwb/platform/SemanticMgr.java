/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBContext;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class SemanticMgr implements SWBContextObject
{
    private static Logger log = SWBUtils.getLogger(SemanticMgr.class);

    public final static String SWB_OWL_PATH=SWBContext.getEnv("swb/ontologyFile","/WEB-INF/owl/swb.owl");
    public final static String SWBSystem="SWBSystem";
    public final static String SWBAdmin="SWBAdmin";
    
    private SWBContext m_context;
    
    private OntModel m_ontology;
    private Model m_system;
    private HashMap <String,SemanticModel>m_models=null;

    private IDBConnection conn;
    
    private SemanticVocabulary vocabulary;

    public void init(SWBContext context) 
    {
        log.event("SemanticMgr initialized...");
        this.m_context=context;
        
        m_models=new HashMap();
        
        // Create database connection
        conn = new DBConnection(SWBUtils.DB.getDefaultConnection(), SWBUtils.DB.getDatabaseName());
        conn.getDriver().setTableNamePrefix("swb_");
        
        //load SWBSystem Model
        log.debug("Loading DBModel:"+"SWBSystem");
        m_system=loadDBModel("SWBSystem");
//        debugModel(m_system);

        //Load Ontology from file
        String swbowl="file:"+SWBUtils.getApplicationPath()+SWB_OWL_PATH;
        log.debug("Loading Model:"+swbowl);
        Model swbSquema=loadModel(swbowl);
//        debugModel(swbSquema);
        
        //Create Omtology
        m_ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RDFS_INF, swbSquema);
        m_ontology.addSubModel(m_system);
        
        //Create Vocabulary
        vocabulary=new SemanticVocabulary();
        SemanticClassIterator tpcit=new SemanticClassIterator(m_ontology.listClasses());
        while(tpcit.hasNext())
        {
            SemanticClass tpc=tpcit.nextSemanticClass();
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
        vocabulary.init();
        
        //LoadModels
        SemanticClass cls=getVocabulary().getSemanticClass(SemanticVocabulary.SWB_CLASS_SWBModel);
        Iterator<SemanticObject> tpit=cls.listInstances();
        while(tpit.hasNext())
        {
            SemanticObject tp=tpit.next();
            String value=tp.getProperty(getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_VALUE));
            log.debug("Loading Model:"+value);
            Model m=loadDBModel(value);
            m_models.put(value, new SemanticModel(value, m));
        }
        
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

    }
    
    private Model loadModel(String path)
    {
        return FileManager.get().loadModel(path);
    }
    
    private Model loadDBModel(String name)
    {
        ModelMaker maker = ModelFactory.createModelRDBMaker(conn);
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
    
    
    public OntModel getOntology() 
    {
        return m_ontology;
    }
    
    public Model getSystemModel() 
    {
        return m_system;
    }
    
    public OntClass getOntClass(String uri)
    {
        return m_ontology.getOntClass(uri);
    }
    
//    public SemanticClass getSemanticClass(String uri)
//    {
//        OntClass cls=m_ontology.getOntClass(uri);
//        SemanticClass tpcls=null;
//        if(cls!=null)
//        {
//            tpcls=new SemanticClass(cls);
//        }
//        return tpcls;
//    }
    
    public SemanticVocabulary getVocabulary() {
        return vocabulary;
    }
}
