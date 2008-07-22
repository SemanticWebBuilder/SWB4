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
public class TopicModel 
{
    private Model m_model;
    private String m_name;

    public TopicModel(String name, Model model)
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
    
    public Topic getTopic(String uri)
    {
        Topic ret=null;
        Resource res=m_model.getResource(uri);
        if(res!=null)ret=new Topic(res);
        return ret;
    }
    
    public Topic createTopic(String uri, TopicClass cls)
    {
        Resource res=m_model.createResource(uri);
        res.addProperty(m_model.getProperty(SWBVocabulary.RDF_TYPE), cls.getURI());
        return new Topic(res);
    }    
}
