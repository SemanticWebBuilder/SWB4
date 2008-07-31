/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import org.semanticwb.SWBInstance;

/**
 *
 * @author Jei
 */
public class SemanticOntology
{
    private OntModel m_ontology;
    private String m_name;

    public SemanticOntology(String name, OntModel ontology)
    {
        this.m_ontology=ontology;
        this.m_name=name;
        init();
    }    
    
    private void init()
    {
        
    }

    public String getName() {
        return m_name;
    }
    
    public OntModel getRDFOntModel()
    {
        return m_ontology;
    }
    
    public void addSubModel(SemanticModel model, boolean rebind)
    {
        //m_ontology.add(model.getRDFModel());
        m_ontology.addSubModel(model.getRDFModel(),rebind);
    }
    
    public void rebind()
    {
        m_ontology.rebind();
    }
    
    public SemanticClass getSemanticObjectClass(Resource res)
    {
        Statement stm=res.getProperty(res.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            return SWBInstance.getSemanticMgr().getVocabulary().getSemanticClass(stm.getObject().toString());
        }
        return null;
    }    
    
    public SemanticClass getSemanticObjectClass(String uri)
    {
        Resource res=m_ontology.getResource(uri);
        return getSemanticObjectClass(res);
    }
    
    
    public <T extends SemanticObject> T getSemanticObject(String uri)
    {
        SemanticObject ret=null;
        Resource res=m_ontology.getResource(uri);
        SemanticClass cl=getSemanticObjectClass(res);
        return (T)cl.newInstance(res);        
//        if(res!=null)ret=new SemanticObject(res);
//        return (T)ret;
    }    
    
    public <T extends SemanticObject> T getSemanticObject(String uri, SemanticClass cls)
    {
        SemanticObject ret=null;
        Resource res=m_ontology.getResource(uri);
        return (T)cls.newInstance(res);        
    }     
}
