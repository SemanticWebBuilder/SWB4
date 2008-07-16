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
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBContext;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;

/**
 *
 * @author Jei
 */
public class SemanticMgr implements SWBContextObject
{
    private static Logger log = SWBUtils.getLogger(SemanticMgr.class);

    public final static String SWB_OWL_PATH="/WEB-INF/owl/swb.owl";
    public final static String SWBSystem="SWBSystem";
    public final static String SWBAdmin="SWBAdmin";
    
    private SWBContext m_context;
    
    private OntModel m_ontology;
    private Model m_system;
    private HashMap <String,Model>m_models=null;

    private IDBConnection conn;
    
    private Vocabulary vocabulary;

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
        debugModel(m_system);

        //Load Ontology from file
        String swbowl="file:"+SWBUtils.getApplicationPath()+SWB_OWL_PATH;
        log.debug("Loading Model:"+swbowl);
        Model swbSquema=loadModel(swbowl);
        debugModel(swbSquema);
        
        //Create Omtology
        m_ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, swbSquema);
        m_ontology.addSubModel(m_system);
        
        //Create Vocabulary
        vocabulary=new Vocabulary();
        TopicClassIterator tpcit=new TopicClassIterator(m_ontology.listClasses());
        while(tpcit.hasNext())
        {
            TopicClass tpc=tpcit.nextTopicClass();
            ((SWBVocabulary)(vocabulary)).addTopicClass(tpc);
            Iterator<TopicProperty> tppit=tpc.listProperties();
            while(tppit.hasNext())
            {
                TopicProperty tpp=tppit.next();
                if(tpc.equals(tpp.getDomainClass()))
                {
                    ((SWBVocabulary)(vocabulary)).addTopicProperty(tpp);
                }
            }
        }
        vocabulary.init();
        
        //LoadModels
        TopicClass cls=getVocabulary().RDFModel;
        TopicIterator tpit=cls.listInstances();
        while(tpit.hasNext())
        {
            Topic tp=tpit.nextTopic();
            String value=tp.getProperty(getVocabulary().value);
            log.debug("Model value:"+value);
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
    
    
    public void debugClasses(OntModel model)
    {
        log.debug("**************************** debugClasses ********************************");
        for (Iterator i = model.listClasses();  i.hasNext(); ) {
            // now list the classes
            OntClass cls=(OntClass)i.next();
            log.debug("cls:"+cls.getLocalName());
            ExtendedIterator it=cls.listInstances();
            while(it.hasNext())
            {
                log.trace("-->inst:"+it.next());
            }
            
            log.debug("  is a Declared Propertie of " );
            for (Iterator it2 = cls.listDeclaredProperties(false); it2.hasNext();) 
            {
                Property prop=(Property)it2.next();
                log.trace("---->prop:"+prop);
            }            
        }        
    }
    
    public void debugResource(Resource res)
    {
        log.debug("**************************** debugModel ********************************");
        StmtIterator i = res.listProperties();
        while(i.hasNext()) 
        {
            Statement stm=i.nextStatement();
            log.trace("stmt:"+stm.getSubject()+" "+stm.getPredicate()+" "+stm.getObject());
        }        
    }    
    
    
    public void debugModel(Model model)
    {
        log.debug("**************************** debugModel ********************************");
        StmtIterator i = model.listStatements();
        while(i.hasNext()) 
        {
            Statement stm=i.nextStatement();
            log.trace("stmt:"+stm.getSubject()+" "+stm.getPredicate()+" "+stm.getObject());
        }        
    }    
    
    
    
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
    
    public Set<Entry<String, Model>> getModels()
    {
        return m_models.entrySet();
    }
    
    public Model getModel(String name)
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
    
    public TopicClass getTopicClass(String uri)
    {
        OntClass cls=getOntClass(uri);
        TopicClass tpcls=null;
        if(cls!=null)
        {
            tpcls=new TopicClass(cls);
        }
        return tpcls;
    }
    
    public Vocabulary getVocabulary() {
        return vocabulary;
    }
}
