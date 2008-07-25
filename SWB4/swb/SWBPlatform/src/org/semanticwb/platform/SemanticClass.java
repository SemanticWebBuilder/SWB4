/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.*;

/**
 *
 * @author Jei
 */
public class SemanticClass 
{
    private OntClass m_class;
    private HashMap<String,SemanticProperty> m_props;
    private Boolean m_isSWBClass=null;
    private Boolean m_isSWBInterface=null;
    
    public SemanticClass(OntClass oclass)
    {
        this.m_class=oclass;
        init();
    }
    
    public SemanticClass(String classuri) throws SWBException
    {
        this.m_class=SWBInstance.getSemanticMgr().getOntClass(classuri);
        if(this.m_class==null) throw new SWBException("OntClass Not Found");
        init();
    }
    
    private void init()
    {
        m_props=new HashMap();
        // super-classes
        for (Iterator i = m_class.listDeclaredProperties(false); i.hasNext(); ) 
        {
            Property prop=(Property)i.next();
            SemanticProperty p=new SemanticProperty(prop);
            m_props.put( p.getName(), p);
        }
    }
    
    public String getName()
    {
        return m_class.getLocalName();
    }
    
    public String getURI()
    {
        return m_class.getURI();
    }
    
    public String getLabel(String lang)
    {
        return m_class.getLabel(lang);
    }

    public Iterator<SemanticObject> listInstances()
    {
        return listInstances(false);
    }
    
    public Iterator<SemanticObject> listInstances(boolean direct)
    {
        return new SemanticObjectIterator(m_class.listInstances(direct));
    }
    
    public SemanticProperty getProperty(String name)
    {
        return m_props.get(name);
    }
    
    public Iterator<SemanticProperty> listProperties()
    {
        return m_props.values().iterator();
    }
    
    public OntClass getOntClass()
    {
        return m_class;
    }
    
    
    public boolean isSuperClass(SemanticClass cls)
    {
        return cls.isSubClass(this);
    }    
    
    public boolean isSubClass(SemanticClass cls)
    {
        boolean ret=false;
        Iterator it=m_class.listSuperClasses(false);
        while(it.hasNext())
        {
            OntClass cl=(OntClass)it.next();
            if(cl.equals(cls.getOntClass()))
            {
                ret=true;
                break;
            }
        }
        return ret;
    }

    public Iterator<SemanticClass> listSubClasses()
    {
        return listSubClasses(false);
    }
    
    public Iterator<SemanticClass> listSubClasses(boolean direct)
    {
        return new SemanticClassIterator(m_class.listSubClasses(direct));
    }    
    
    public Iterator<SemanticClass> listSuperClasses()
    {
        return listSuperClasses(false);
    }
    
    public Iterator<SemanticClass> listSuperClasses(boolean direct)
    {
        return new SemanticClassIterator(m_class.listSuperClasses(direct));
    }     
    
    @Override
    public String toString()
    {
        return m_class.toString();
    }

    @Override
    public int hashCode() 
    {
        return m_class.hashCode();
    }

    @Override
    public boolean equals(Object obj) 
    {
        boolean ret=false;
        if(obj!=null)
        {
            ret=(hashCode()==obj.hashCode());
        }
        return ret;
    }    
    
    public boolean isSWBClass()
    {
        if(m_isSWBClass==null)
        {
            m_isSWBClass=false;
            for (Iterator i = m_class.listRDFTypes(false); i.hasNext(); ) 
            {
                Resource res=(Resource)i.next();
                if(res.getURI().equals(SemanticVocabulary.SWB_CLASS))
                {
                    m_isSWBClass = true;
                }
            }        
        }
        return m_isSWBClass.booleanValue();
    }
    
    public boolean isSWBInterface()
    {
        if(m_isSWBInterface==null)
        {
            m_isSWBInterface=false;
            for (Iterator i = m_class.listRDFTypes(false); i.hasNext(); ) 
            {
                Resource res=(Resource)i.next();
                if(res.getURI().equals(SemanticVocabulary.SWB_INTERFACE))
                {
                    m_isSWBClass = true;
                }
            }     
        }
        return m_isSWBInterface.booleanValue();
    }
    
}
