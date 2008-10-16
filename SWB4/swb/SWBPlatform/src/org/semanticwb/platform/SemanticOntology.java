/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.Model;
import java.util.Iterator;
import java.util.Map.Entry;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericObject;

/**
 *
 * @author Jei
 */
public class SemanticOntology
{
    private OntModel m_ontology;
    private String m_name;

    public SemanticOntology(String name, OntModel ontology)
    {
        this.m_ontology=ontology;
        this.m_name=name;
        init();
    }    
    
    private void init()
    {
        
    }

    public String getName() {
        return m_name;
    }
    
    public OntModel getRDFOntModel()
    {
        return m_ontology;
    }
    
    public void addSubModel(SemanticModel model, boolean rebind)
    {
        m_ontology.addSubModel(model.getRDFModel(),rebind);
    }
    
    public void rebind()
    {
        m_ontology.rebind();
    }
    
    public Resource getResource(String uri)
    {
        Resource ret=null;
        Iterator<Entry<String, SemanticModel>> it=SWBPlatform.getSemanticMgr().getModels().iterator();
        while(it.hasNext())
        {
            Entry<String, SemanticModel> ent=it.next();
            SemanticModel model=ent.getValue();
            String ns=model.getNameSpace();
            if(ns!=null && uri.startsWith(ns))
            {
                Resource res=model.getRDFModel().getResource(uri);
                Property type=model.getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
                if(model.getRDFModel().contains(res, type))
                {
                    ret=res;
                }
            }
        }
        if(ret==null)
        {
            it=SWBPlatform.getSemanticMgr().getModels().iterator();
            while(it.hasNext())
            {
                Entry<String, SemanticModel> ent=it.next();
                SemanticModel model=ent.getValue();
                if(model.getRDFModel()!=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel())
                {
                    Resource res=model.getRDFModel().getResource(uri);
                    Property type=model.getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
                    if(model.getRDFModel().contains(res, type))
                    {
                        ret=res;
                    }
                    if(ret!=null)break;
                }
            }
        }
        return ret;
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
    
    public SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=null;
//        OntResource res=m_ontology.getOntResource(uri);
//        if(m_ontology.containsResource(res))
//        {
//            ret=new SemanticObject(res);
//        }
//        return ret;        
        Resource res=getResource(uri);
        if(res!=null)ret=new SemanticObject(res);
        return ret;        
    }
    
    public GenericObject getGenericObject(String uri)
    {
        SemanticClass cls=getSemanticObjectClass(getResource(uri));
        return getGenericObject(uri, cls);
    }    
    
    public GenericObject getGenericObject(String uri, SemanticClass cls)
    {
        return cls.newGenericInstance(getSemanticObject(uri));        
    }
    
    public SemanticClass createSemanticClass(String uri)
    {
        OntModel m=m_ontology;
        m.createStatement(m.getResource(uri), m.getProperty(SemanticVocabulary.RDF_TYPE), m.getResource(SemanticVocabulary.OWL_CLASS));
        OntClass ontcls=m_ontology.getOntClass(uri);
        return new SemanticClass(ontcls);    
    }    
}
