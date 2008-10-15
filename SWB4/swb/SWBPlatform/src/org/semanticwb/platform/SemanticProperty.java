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
import org.semanticwb.base.util.URLEncoder;

/**
 *
 * @author Jei
 */
public class SemanticProperty 
{
    Property m_prop;
    SemanticProperty m_inverse;
    
    public SemanticProperty(Property prop)
    {
        this.m_prop=prop;
        if(hasInverse())
        {
            if(m_prop instanceof OntProperty)
            {
                m_inverse=new SemanticProperty(((OntProperty)m_prop).getInverse());
            }
        }
    }
    
    public String getName()
    {
        return m_prop.getLocalName();
    }
    
    public String getLabel()
    {
        String ret=null;
        if(m_prop instanceof OntProperty)
        {
            ret=((OntProperty)m_prop).getLabel(null);
        }
        return ret;
    }
    
    public String getURI()
    {
        return m_prop.getURI();
    }
    
    /**
     * Regresa URI codificado para utilizar en ligas de html
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }     
    
    public Property getRDFProperty()
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
    
    public boolean isUsedAsName()
    {
        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_CANUSEDASNAME));
        if(st!=null)
        {
            return st.getBoolean();
        }
        return false;
    }    
    
    public String getCaption()
    {
        String ret=null;
        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_PROPCAPTION));
        if(st!=null)
        {
            return st.getString();
        }
        return ret;
    }    
    
    public String getViewGroup()
    {
        String ret=null;
        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_PROPGROUP));
        if(st!=null)
        {
            return st.getString();
        }
        return ret;
    }       
    
    public int getSortIndex()
    {
        int ret=99999999;
        Statement st=m_prop.getProperty(SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_ANNOT_PROPINDEX));
        if(st!=null)
        {
            return st.getInt();
        }
        return ret;
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
        boolean ret=false;
        if(m_prop instanceof OntProperty)
        {
            ret=((OntProperty)m_prop).hasInverse();
        }        
        return ret;
    }
    
    public SemanticProperty getInverse()
    {
        return m_inverse;
    }
    
    public boolean isBoolean()
    {
        boolean ret=false;
        if(getRange().getURI().equals("http://www.w3.org/2001/XMLSchema#boolean"))ret=true;
        return ret;        
    }
    
    public boolean isInt()
    {
        boolean ret=false;
        if(getRange().getURI().equals("http://www.w3.org/2001/XMLSchema#int"))ret=true;
        return ret;        
    }    
    
    public boolean isDate()
    {
        boolean ret=false;
        if(getRange().getURI().equals("http://www.w3.org/2001/XMLSchema#date"))ret=true;
        return ret;        
    }    
    
    public boolean isDateTime()
    {
        boolean ret=false;
        if(getRange().getURI().equals("http://www.w3.org/2001/XMLSchema#dateTime"))ret=true;
        return ret;        
    }       
    
    public boolean isString()
    {
        boolean ret=false;
        if(getRange().getURI().equals("http://www.w3.org/2001/XMLSchema#string"))ret=true;
        return ret;        
    }       
    
    public boolean isFloat()
    {
        boolean ret=false;
        if(getRange().getURI().equals("http://www.w3.org/2001/XMLSchema#float"))ret=true;
        return ret;        
    }       
    
    
}
