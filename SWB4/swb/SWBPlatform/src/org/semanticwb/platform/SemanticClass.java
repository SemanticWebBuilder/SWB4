/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.shared.PropertyNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.semanticwb.*;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;

/**
 *
 * @author Jei
 */
public class SemanticClass 
{
    private static Logger log=SWBUtils.getLogger(SemanticClass.class);
    
    private OntClass m_class;
    private HashMap<String,SemanticProperty> m_props;
    private Boolean m_isSWBClass=null;
    private Boolean m_isSWBInterface=null;
    private Boolean m_isSWBModel=null;
    private String m_className=null;
    private Boolean m_autogenId=null;
    private Class m_cls=null;
    private Constructor m_constructor=null;
    private SemanticProperty displayNameProperty;

    public SemanticClass(OntClass oclass)
    {
        this.m_class=oclass;
        init();
    }
    
    public SemanticClass(String classuri) throws SWBException
    {
        this.m_class=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getOntClass(classuri);
        if(this.m_class==null) throw new SWBException("OntClass Not Found");
        init();
    }
    
    private void init()
    {
        m_props=new HashMap();
        // super-classes
        //System.out.println("m_class:"+m_class);
        for (Iterator i = m_class.listDeclaredProperties(false); i.hasNext(); ) 
        {
            Property prop=(Property)i.next();
            SemanticProperty p=new SemanticProperty(prop);
            if(p.isUsedAsName())displayNameProperty=p;
            //System.out.println("p.getName():"+p.getName()+" "+p);
            m_props.put( p.getName(), p);
        }
    }
    
    public String getName()
    {
        return m_class.getLocalName();
    }
    
    public String getClassName()
    {
        if(m_className==null)
        {
            try
            {
                Property prop=m_class.getModel().getProperty(SemanticVocabulary.SWB_ANNOT_CLASSNAME);
                //System.out.println("Class:"+m_class+" ->"+className);
                m_className=m_class.getRequiredProperty(prop).getString();
                //System.out.println("Class:"+m_class+" ->"+className);
                if(m_className==null)m_className=SemanticObject.class.getName();
            } catch (Exception pnf){
                m_className=getName();
            }
        }
        log.trace("getClassName:"+m_className);
        return m_className;
    }   
    
    public boolean isAutogenId()
    {
        if(m_autogenId==null)
        {
            Property prop=m_class.getModel().getProperty(SemanticVocabulary.SWB_ANNOT_AUTOGENID);
            //System.out.println("Class:"+m_class+" ->"+className);
            try
            {
                m_autogenId=m_class.getRequiredProperty(prop).getBoolean();
            }catch(PropertyNotFoundException noe)
            {
                m_autogenId=false;
            }
        }
        //log.trace("isAutogenId:"+m_autogenId);
        return m_autogenId;
    }
    
    
    
    /**
     * Lista las clases relacionadas a esta clase del tipo modelo con la propiedad hasClass
     * Solo si isSWBModel = true
     * @return clases relacionadas a esta clase del tipo modelo con la propiedad hasClass
     */
    public Iterator<SemanticClass> listModelClasses()
    {
        Iterator ret=null;
        if(isSWBModel()==true)
        {
            Property prop=m_class.getModel().getProperty(SemanticVocabulary.SWB_PROP_HASCLASS);
            ret=new SemanticClassIterator(m_class.listProperties(prop));
        }else
        {
            ret=(new Vector()).iterator();
        }
        return ret;        
    }
    
    public Iterator<SemanticClass> listOwnerModels()
    {
        ArrayList ret=new ArrayList();
        if(isSWBModel()==false)
        {
            Property prop=m_class.getModel().getProperty(SemanticVocabulary.SWB_PROP_HASCLASS);
            StmtIterator it=m_class.getModel().listStatements(null, prop, m_class);
            //System.out.println("listOwnerModels:"+prop+"-"+m_class);
            while(it.hasNext())
            {
                Statement stmt=it.nextStatement();
                ret.add(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stmt.getSubject().getURI()));
            }
        }
        return ret.iterator();        
    }
    
    
    public Constructor getConstructor()
    {
        if(m_constructor==null)
        {
            try
            {
                //m_constructor=getObjectClass().getDeclaredConstructor(Resource.class);
                m_constructor=getObjectClass().getDeclaredConstructor(SemanticObject.class);
            }
            catch(NoSuchMethodException nsme)
            {
                new IllegalArgumentException(nsme);
            }        
        }
        return m_constructor;
        
    }
    
    public SemanticObject newInstance(String uri)
    {
        Resource res=m_class.getModel().getResource(uri);
        return newInstance(res);
    }
    
    public SemanticObject newInstance(Resource res)
    {
//        try
//        {
            //return (SemanticObject)getConstructor().newInstance(res);
            return new SemanticObject(res);
//        }
//        catch(Exception ie)
//        {
//            throw new AssertionError(ie.getMessage());        
//        }        
    }    
    
    public GenericObject newGenericInstance(Resource res)
    {
        return newGenericInstance(newInstance(res));
    }     
    
    public GenericObject newGenericInstance(SemanticObject obj)
    {
        try
        {
            return (GenericObject)getConstructor().newInstance(obj);
        }
        catch(Exception ie)
        {
            throw new AssertionError(ie.getMessage());        
        }        
    }       
    
    public Class getObjectClass()
    {
        if(m_cls==null)
        {
            try
            {
                m_cls=Class.forName(getClassName());
            }catch(Exception e){log.error(e);}
        }
        return m_cls;
    }
    
    public String getURI()
    {
        return m_class.getURI();
    }
    
    /**
     * Regresa URI codificado para utilizar en ligas de html
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }     
    
    public String getLabel(String lang)
    {
        return m_class.getLabel(lang);
    }
    
//    public boolean isSuperClass(SemanticClass cls)
//    {
//        return m_class.hasSuperClass(cls.getOntClass(),false);
//    }
//    
//    public boolean isSubClass(SemanticClass cls)
//    {
//        return m_class.hasSubClass(cls.getOntClass(),false);
//    }    

    public Iterator listInstances()
    {
        return listInstances(false);
    }
    
    public Iterator listInstances(boolean direct)
    {
        //return new SemanticIterator(this,m_class.listInstances(direct));
        return new SemanticObjectIterator(m_class.listInstances(direct));
    }
    
    public Iterator listGenericInstances()
    {
        return listGenericInstances(false);
    }
    
    public Iterator listGenericInstances(boolean direct)
    {
        return new GenericIterator(this,m_class.listInstances(direct));
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
                    m_isSWBInterface = true;
                }
            }     
        }
        return m_isSWBInterface.booleanValue();
    }
    
    public boolean isSWBModel()
    {
        if(m_isSWBModel==null)
        {
            m_isSWBModel=false;
            for (Iterator i = m_class.listRDFTypes(false); i.hasNext(); ) 
            {
                Resource res=(Resource)i.next();
                if(res.getURI().equals(SemanticVocabulary.SWB_MODEL))
                {
                    m_isSWBModel = true;
                }
            }     
        }
        return m_isSWBModel.booleanValue();
    }    
    
    public SemanticProperty getDisplayNameProperty() {
        return displayNameProperty;
    }  
}
