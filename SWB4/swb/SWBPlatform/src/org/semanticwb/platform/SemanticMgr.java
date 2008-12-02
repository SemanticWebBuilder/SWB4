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
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
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

    public final static String SWB_OWL_PATH=SWBPlatform.getEnv("swb/ontologyFiles","/WEB-INF/owl/swb_base.owl,/WEB-INF/owl/swb_model.owl");
    public final static String SWBSystem="SWBSystem";
    public final static String SWBAdmin="SWBAdmin";
    
    private SWBPlatform m_context;
    
    private SemanticOntology m_ontology;
    private SemanticModel m_system;
    private HashMap<String,SemanticModel> m_schemas;
    private HashMap<String,SemanticModel>m_models=null;
    private HashMap<Model,SemanticModel>m_imodels=null;

    private IDBConnection conn;
    private ModelMaker maker;
    
    private SemanticVocabulary vocabulary;

    private HashMap<String,SessionUser> m_sessions;

    public void init(SWBPlatform context) 
    {
        log.event("Initializing SemanticMgr...");
        this.m_context=context;
        
        m_models=new HashMap();
        m_imodels=new HashMap();
        m_schemas=new HashMap();

        m_sessions=new HashMap();
        
        // Create database connection
        conn = new DBConnection(SWBUtils.DB.getDefaultConnection(), SWBUtils.DB.getDatabaseName());
        conn.getDriver().setTableNamePrefix("swb_");
        //conn.getDriver().setDoDuplicateCheck(false);
        maker = ModelFactory.createModelRDBMaker(conn);
        
        //Load Ontology from file
        StringTokenizer st=new StringTokenizer(SWB_OWL_PATH,",;");
        while(st.hasMoreTokens())
        {
            String file=st.nextToken();
            String swbowl="file:"+SWBUtils.getApplicationPath()+file;
            File owlf=new File(swbowl);
            log.debug("Loading Model:"+owlf.getName());
            m_schemas.put(owlf.getName(),new SemanticModel("SWBSchema",loadRDFFileModel(swbowl)));
            //debugModel(swbSquema);
        }
        
        OntModelSpec spec = OntModelSpec.OWL_MEM;
//        OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_MEM );
//        spec.setBaseModelMaker(maker);
//        spec.setImportModelMaker(maker);
        
        //Create Omtology
        m_ontology = new SemanticOntology("SWB",ModelFactory.createOntologyModel(spec));
        Iterator<SemanticModel> it=m_schemas.values().iterator();
        while(it.hasNext())
        {
            SemanticModel model=it.next();
            m_ontology.addSubModel(model,false);
            //m_ontology.getRDFOntModel().add(model.getRDFModel());
        }
        
        //Agrega ontologia a los modelos
        SemanticModel ontModel=new SemanticModel("swb_ontology", m_ontology.getRDFOntModel());
        m_models.put("swb_ontology", ontModel);
        m_imodels.put(ontModel.getRDFModel(), ontModel);
        
        //Create Vocabulary
        vocabulary=new SemanticVocabulary();
        Iterator<SemanticClass> tpcit=new SemanticClassIterator(m_ontology.getRDFOntModel().listClasses(),true);
        while(tpcit.hasNext())
        {
            SemanticClass cls=tpcit.next();
            //if(cls.getName()!=null)
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
        
        //load SWBSystem Model
        log.debug("Loading DBModel:"+"SWBSystem");
        m_system=new SemanticModel("SWBSystem",loadRDFDBModel("SWBSystem"));
//        debugModel(m_system);
        m_ontology.addSubModel(m_system,false);

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
    
    public SemanticModel getSchemaModel(String name)
    {
        return m_schemas.get(name);
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

    public void addSessionUser(Principal user)
    {
        if(user!=null)
        {
            SessionUser sess=m_sessions.get(Thread.currentThread().getName());
            if(sess==null)m_sessions.put(Thread.currentThread().getName(),new SessionUser(user));
            else sess.setUser(user);
        }
    }

    public Principal getSessionUser()
    {
        Principal user=null;
        SessionUser sess=m_sessions.get(Thread.currentThread().getName());
        if(sess!=null)user=sess.getUser();
        return user;
    }

}
