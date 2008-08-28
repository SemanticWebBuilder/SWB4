/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericObject;

/**
 *
 * @author Jei
 */
public class SemanticModel 
{
    private Model m_model;
    private String m_name;
    private String m_nameSpace;
    private SemanticObject m_modelObject;

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
    
    public Model getRDFModel()
    {
        return m_model;
    }
    
    public SemanticClass getSemanticObjectClass(Resource res)
    {
        Statement stm=res.getRequiredProperty(res.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
        }
        return null;
    }    
    
    public SemanticClass getSemanticObjectClass(String uri)
    {
        SemanticClass ret=null;
        Resource res=m_model.getResource(uri);
        if(res!=null)
        {
            ret=getSemanticObjectClass(res);
        }
        return ret;
    }
    
    public SemanticObject getSemanticObject(String uri)
    {
//        Resource res=m_model.getResource(uri);
//        SemanticClass cl=getSemanticObjectClass(res);
//        return cl.newInstance(res);
        SemanticObject ret=null;
        Resource res=m_model.getResource(uri);
        if(m_model.containsResource(res))
        {
            ret=new SemanticObject(res);
        }
        return ret;
    }

//    public SemanticObject getSemanticObject(String uri, SemanticClass cl)
//    {
//        SemanticObject ret=null;
//        Resource res=m_model.getResource(uri);
//        if(res!=null)
//        {
//            ret=cl.newInstance(res);
//        }
//        return ret;
////        SemanticObject ret=null;
////        Resource res=m_model.getResource(uri);
////        if(res!=null)ret=new SemanticObject(res);
////        return ret;
//    }

    public SemanticObject createSemanticObject(String uri, SemanticClass cls)
    {
        Resource res=m_model.createResource(uri);
        res.addProperty(m_model.getProperty(SemanticVocabulary.RDF_TYPE), cls.getOntClass());
        return cls.newInstance(res);
    }    
    
    public GenericObject getGenericObject(String uri)
    {
        GenericObject ret=null;
        SemanticClass cl=getSemanticObjectClass(uri);
        SemanticObject obj=getSemanticObject(uri);
        if(cl!=null && obj!=null)
        {
            ret=cl.newGenericInstance(obj);
        }
        return ret;
    }    
    
    public GenericObject getGenericObject(String uri, SemanticClass cls)
    {    
        GenericObject ret=null;
        SemanticObject obj=getSemanticObject(uri);
        if(obj!=null)
        {
            ret=cls.newGenericInstance(obj);
        }
        return ret;
    }
    
    public GenericObject createGenericObject(String uri, SemanticClass cls)
    {
        GenericObject ret=null;
        SemanticObject obj=createSemanticObject(uri,cls);
        if(obj!=null)
        {
            ret=cls.newGenericInstance(obj);
        }
        return ret;
    }    
    
    public void removeSemanticObject(String uri)
    {
        Resource res=m_model.getResource(uri);
        if(res!=null)
        {
            m_model.remove(res,null,null);
        }
    }  
    
    public void removeSemanticObject(SemanticObject obj)
    {
        Resource res=obj.getRDFResource();
        if(res!=null)
        {
            m_model.remove(res,null,null);
        }
    }     
    
    public void removeGenericObject(GenericObject obj)
    {
        removeSemanticObject(obj.getSemanticObject());
    }       
    
    public String getNameSpace() 
    {
        if(m_nameSpace==null)
        {
            this.m_nameSpace=m_model.getNsPrefixURI(m_name);
        }
        return m_nameSpace;
    }    
    
    public String getObjectUri(String id, SemanticClass cls)
    {
        return getNameSpace()+"/"+cls.getName()+"#"+id;
    }
    
    public SemanticObject getModelObject()
    {
        if(m_modelObject==null)
        {
            m_modelObject=getSemanticObject(getName());
        }
        return m_modelObject;
    }
}
