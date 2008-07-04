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
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.Iterator;
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
    
    private SWBContext m_context;
    
    private OntModel m_ontology;
    private Model m_admin;

    private IDBConnection conn;
    
    public void init(SWBContext context) 
    {
        this.m_context=context;
        log.event("SemanticMgr initialized...");
        
        // Create database connection
        conn = new DBConnection(SWBUtils.DB.getDefaultConnection(), SWBUtils.DB.getDatabaseName());
        conn.getDriver().setTableNamePrefix("swb_");
        
        //load SWBAdmin Model
        log.debug("Loading DBModel:"+"SWBAdmin");
        m_admin=loadDBModel("SWBAdmin");
        debugModel(m_admin);
        
        //Load Ontology from file
        String swbowl="file:"+SWBUtils.getApplicationPath()+"/WEB-INF/owl/swb.owl";
        log.debug("Loading Model:"+swbowl);
        Model swbSquema=loadModel(swbowl);
        debugModel(swbSquema);
        
        //Create Omtology
        m_ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, swbSquema);
        m_ontology.addSubModel(m_admin);
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
                log.debug("-->inst:"+it.next());
            }
            
        }        
    }
    
    public void debugModel(Model model)
    {
        log.debug("**************************** debugModel ********************************");
        StmtIterator i = model.listStatements();
        while(i.hasNext()) 
        {
            Statement stm=i.nextStatement();
            log.debug("cls:"+stm.getSubject()+" "+stm.getPredicate()+" "+stm.getObject());
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
    
    public OntModel getOntology() {
        return m_ontology;
    }
    
    public Model getAdminModel() {
        return m_admin;
    }
}
