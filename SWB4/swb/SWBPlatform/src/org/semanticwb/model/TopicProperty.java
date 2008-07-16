/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import org.semanticwb.platform.SWBVocabulary;
import org.semanticwb.SWBContext;

/**
 *
 * @author Jei
 */
public class TopicProperty 
{
    Property m_prop;
    
    public TopicProperty(Property prop)
    {
        this.m_prop=prop;
    }
    
    public String getName()
    {
        return m_prop.getLocalName();
    }
    
    public String getURI()
    {
        return m_prop.getURI();
    }
    
    public Property getRDFProperty()
    {
        return m_prop;
    }
    
    @Override
    public String toString()
    {
        return m_prop.toString();
    }

    @Override
    public int hashCode() 
    {
        return m_prop.hashCode();
    }

    @Override
    public boolean equals(Object obj) 
    {
        return hashCode()==obj.hashCode();
    }       
    
    public TopicClass getDomainClass()
    {
        TopicClass ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SWBVocabulary.RDFS_DOMAIN));
        if(stm!=null)
        {
            ret=SWBContext.getSemanticMgr().getVocabulary().getTopicClass(stm.getResource().getURI());
        }
        return ret;    
    }
    
    public TopicClass getRangeClass()
    {
        TopicClass ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SWBVocabulary.RDFS_RANGE));
        if(stm!=null)
        {
            ret=SWBContext.getSemanticMgr().getVocabulary().getTopicClass(stm.getResource().getURI());
        }
        return ret;    
    }
    
    public Topic getRangeDataType()
    {
        Topic ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SWBVocabulary.RDFS_RANGE));
        if(stm!=null)
        {
            ret=new Topic(stm.getResource());
        }
        return ret;    
    }    
    
    public Resource getRange()
    {
        Resource ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SWBVocabulary.RDFS_RANGE));
        if(stm!=null)
        {
            ret=stm.getResource();
        }
        return ret;          
    }
    
    public boolean isObjectProperty()
    {
        boolean ret=false;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SWBVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            ret=SWBVocabulary.OWL_OBJECTPROPERTY.equals(stm.getResource().getURI());
        }
        return ret;      
    }
    
    public boolean isDataTypeProperty()
    {
        boolean ret=false;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SWBVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            ret=SWBVocabulary.OWL_DATATYPEPROPERTY.equals(stm.getResource().getURI());
        }
        return ret;
    }
}
