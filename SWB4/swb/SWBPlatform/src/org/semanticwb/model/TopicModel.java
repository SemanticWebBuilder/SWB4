/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 *
 * @author Jei
 */
public class TopicModel 
{
    private Model m_model;
    
    public TopicModel(Model model)
    {
        this.m_model=model;
        init();
    }
    
    public void init()
    {
        
    }

    public Topic getTopic(String uri)
    {
        Topic ret=null;
        Resource res=m_model.getResource(uri);
        if(res!=null)ret=new Topic(res);
        return ret;
    }
    
    
}
