/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import org.semanticwb.SWBPlatform;

/**
 *
 * @author Jei
 */
public class SemanticProperty 
{
    OntProperty m_prop;
    SemanticProperty m_inverse;
    
    public SemanticProperty(Property prop)
    {
        this.m_prop=(OntProperty)prop;
        if(hasInverse())
        {
            m_inverse=new SemanticProperty(m_prop.getInverse());
        }
    }
    
    public String getName()
    {
        return m_prop.getLocalName();
    }
    
    public String getURI()
    {
        return m_prop.getURI();
    }
    
    public OntProperty getRDFProperty()
    {
        return m_prop;
    }
    
    public boolean isLocaleable()
    {
        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_LOCALEABLE));
        if(st!=null)
        {
            return st.getBoolean();
        }
        return false;
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
    
    public SemanticClass getDomainClass()
    {
        if(hasInverse())
        {
            return m_inverse.getRangeClass();
        }
        SemanticClass ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_DOMAIN));
        if(stm!=null)
        {
            ret=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
        }
        return ret;    
    }
    
    public SemanticClass getRangeClass()
    {
        if(hasInverse())
        {
            return m_inverse.getDomainClass();
        }        
        SemanticClass ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_RANGE));
        if(stm!=null)
        {
            ret=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
        }
        return ret;    
    }
    
    public SemanticObject getRangeDataType()
    {
        SemanticObject ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_RANGE));
        if(stm!=null)
        {
            ret=new SemanticObject(stm.getResource());
        }
        return ret;    
    }    
    
    public Resource getRange()
    {
        Resource ret=null;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDFS_RANGE));
        if(stm!=null)
        {
            ret=stm.getResource();
        }
        return ret;          
    }
    
    public int getCardinality()
    {
        return 0;
    }
    
    public boolean isObjectProperty()
    {
        boolean ret=false;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            ret=SemanticVocabulary.OWL_OBJECTPROPERTY.equals(stm.getResource().getURI());
        }
        return ret;      
    }
    
    public boolean isDataTypeProperty()
    {
        boolean ret=false;
        Statement stm=m_prop.getProperty(m_prop.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            ret=SemanticVocabulary.OWL_DATATYPEPROPERTY.equals(stm.getResource().getURI());
        }
        return ret;
    }
    
    public boolean hasInverse()
    {
        return m_prop.hasInverse();
    }
    
    public SemanticProperty getInverse()
    {
        return m_inverse;
    }
}
