/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;

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
    
    public SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=null;
        Resource res=m_ontology.getResource(uri);
        if(res!=null)ret=new SemanticObject(res);
        return ret;
    }    
}
