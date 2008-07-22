/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.ontology.OntClass;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.*;

/**
 *
 * @author Jei
 */
public class TopicClass 
{
    private OntClass m_class;
    private HashMap<String,TopicProperty> m_props;
    private Boolean isSWBClass=null;
    private Boolean isSWBInterface=null;
    
    public TopicClass(OntClass oclass)
    {
        this.m_class=oclass;
        init();
    }
    
    public TopicClass(String classuri) throws SWBException
    {
        this.m_class=SWBContext.getSemanticMgr().getOntClass(classuri);
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
            TopicProperty p=new TopicProperty(prop);
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

    public Iterator<Topic> listInstances()
    {
        return listInstances(false);
    }
    
    public Iterator<Topic> listInstances(boolean direct)
    {
        return new TopicIterator(m_class.listInstances(direct));
    }
    
    public TopicProperty getProperty(String name)
    {
        return m_props.get(name);
    }
    
    public Iterator<TopicProperty> listProperties()
    {
        return m_props.values().iterator();
    }
    
    public OntClass getOntClass()
    {
        return m_class;
    }
    
    
    public boolean isSuperClass(TopicClass cls)
    {
        boolean ret=false;
        ExtendedIterator it=m_class.listSubClasses(false);
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
    
    public boolean isSubClass(TopicClass cls)
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

    public Iterator<TopicClass> listSubClasses()
    {
        return listSubClasses(false);
    }
    
    public Iterator<TopicClass> listSubClasses(boolean direct)
    {
        return new TopicClassIterator(m_class.listSubClasses(direct));
    }    
    
    public Iterator<TopicClass> listSuperClasses()
    {
        return listSuperClasses(false);
    }
    
    public Iterator<TopicClass> listSuperClasses(boolean direct)
    {
        return new TopicClassIterator(m_class.listSuperClasses(direct));
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
        if(isSWBClass==null)
        {
            isSWBClass = Boolean.valueOf(isSubClass(SWBContext.getSemanticMgr().getVocabulary().SWBClass));    
        }
        return isSWBClass.booleanValue();
        
        
    }
    
    public boolean isSWBInterface()
    {
        if(isSWBInterface==null)
        {
            isSWBInterface = Boolean.valueOf(!isSWBClass() && isSubClass(SWBContext.getSemanticMgr().getVocabulary().SWBInterface));    
        }
        return isSWBInterface.booleanValue();
    }
    
}
