/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.util.Iterator;
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
    public SemanticObject createSemanticObjectById(String id, SemanticClass cls)
    {
        return createSemanticObject(getObjectUri(id, cls), cls);
    }

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
            m_model.removeAll(res,null,null);
            m_model.removeAll(null,null,res);
        }
    }  
    
    public void removeSemanticObject(SemanticObject obj)
    {
        Resource res=obj.getRDFResource();
        if(res!=null)
        {
            m_model.removeAll(res,null,null);
            m_model.removeAll(null,null,res);
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
    
    public Iterator<SemanticObject> listInstancesOfClass(SemanticClass cls)
    {
        Property rdf=getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getRDFModel().listStatements(null, rdf, cls.getOntClass());
        return new SemanticIterator(stit, true);
    }
    
    public SemanticProperty createSemanticProperty(String uri, SemanticClass cls, String uriType, String uriRang)
    {
        Model m=getRDFModel();

        OntModel ont=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
        OntProperty ontprop=null;
        if (SemanticVocabulary.OWL_DATATYPEPROPERTY.equals(uriType)){
            ontprop=ont.createDatatypeProperty(uri);
        } else if (SemanticVocabulary.OWL_OBJECTPROPERTY.equals(uriType)){
            ontprop=ont.createObjectProperty(uri);
        }
        ontprop.setDomain(m.getResource(cls.getURI()));
        ontprop.setRange(m.getResource(uriRang));
        
        StmtIterator sit = ont.listStatements(m.getResource(ontprop.getURI()), null, (RDFNode)null);
        m.add(sit);
        
        cls=new SemanticClass(cls.getOntClass());
        SWBPlatform.getSemanticMgr().getVocabulary().registerClass(cls);
        return new SemanticProperty(ontprop);
    }    
    
    public SemanticClass createSemanticClass(String uri)
    {
        Model m=getRDFModel();
        OntModel ont=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
        Resource res = ont.getResource(uri);
        Statement st = m.getProperty(res, m.getProperty(SemanticVocabulary.RDF_TYPE));
        if (null==st)
        {
            st = m.createStatement(res, m.getProperty(SemanticVocabulary.RDF_TYPE), m.getResource(SemanticVocabulary.OWL_CLASS));
            m.add(st);
        }
        return registerClass(uri);
    }    
    
    public SemanticClass registerClass(String uri)
    {
        OntModel ont=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
        OntClass ontcls=ont.getOntClass(uri);
        SemanticClass cls=new SemanticClass(ontcls);
        SWBPlatform.getSemanticMgr().getVocabulary().registerClass(cls);
        return cls;
    }   
    
    public SemanticProperty getSemanticProperty(String uri)
    {
        SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
        if(prop==null)
        {
            OntModel ont=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
            Property p=ont.getProperty(uri);
            if(p!=null)
            {
                prop=new SemanticProperty(p);
                SWBPlatform.getSemanticMgr().getVocabulary().addSemanticProperty(prop);
            }
        }
        return prop;
    }
    
}
