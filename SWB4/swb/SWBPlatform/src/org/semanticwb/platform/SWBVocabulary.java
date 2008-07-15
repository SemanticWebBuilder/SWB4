/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.model.TopicClass;
import org.semanticwb.model.TopicProperty;

/**
 *
 * @author Jei
 */
public class SWBVocabulary 
{
    //RDF
    public final String RDF_URI="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public final String RDF_TYPE=RDF_URI+"type";
    //General
    public final String URI="http://www.semanticwebbuilder.org/swb4/ontology#";
    
    public HashMap<String, TopicClass> classes;
    public HashMap<String, TopicProperty> properties;
    
    public SWBVocabulary()
    {
        classes=new HashMap();
        properties=new HashMap();
    }
    
    public void init()
    {
        
    }
    
    void addTopicClass(TopicClass tpc)
    {
        classes.put(tpc.getURI(), tpc);
    }
    
    public Iterator<TopicClass> listTopicClasses()
    {
        return classes.values().iterator();
    }
    
    public TopicClass getTopicClass(String uri)
    {
        return classes.get(uri);
    }
    
    void addTopicProperty(TopicProperty tpp)
    {
        if(!properties.containsKey(tpp.getURI()))
        {
            properties.put(tpp.getURI(), tpp);
        }
    }
    
    public Iterator<TopicProperty> listTopicProperties()
    {
        return properties.values().iterator();
    }
    
    public TopicProperty getTopicProperty(String uri)
    {
        return properties.get(uri);
    }    
    
    
}
