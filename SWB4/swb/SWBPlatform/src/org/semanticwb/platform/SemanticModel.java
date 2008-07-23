/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 *
 * @author Jei
 */
public class SemanticModel 
{
    private Model m_model;
    private String m_name;

    public SemanticModel(String name, Model model)
    {
        this.m_model=model;
        this.m_name=name;
        init();
    }
    
    private void init()
    {
        
    }

    public String getName() {
        return m_name;
    }
    
    public SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=null;
        Resource res=m_model.getResource(uri);
        if(res!=null)ret=new SemanticObject(res);
        return ret;
    }
    
    public SemanticObject createSemanticObject(String uri, SemanticClass cls)
    {
        Resource res=m_model.createResource(uri);
        res.addProperty(m_model.getProperty(SemanticVocabulary.RDF_TYPE), cls.getURI());
        return new SemanticObject(res);
    }    
}
