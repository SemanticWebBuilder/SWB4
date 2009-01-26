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
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;

/**
 *
 * @author Jei
 */
public class SemanticOntology
{
    private static Logger log=SWBUtils.getLogger(SemanticOntology.class);


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

    //TODO: Mejorar performance
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
        if(ret==null)
        {
            Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
            Resource res=model.getResource(uri);
            Property type=model.getProperty(SemanticVocabulary.RDF_TYPE);
            if(model.contains(res, type))
            {
                ret=res;
            }
        }
        //if(ret==null)log.warn("Uri not Found:"+uri,new AssertionError());
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
        SemanticObject ret=SemanticObject.createSemanticObject(uri);
        return ret;        
    }

    
    public GenericObject getGenericObject(String uri)
    {
        SemanticObject sobj=getSemanticObject(uri);
        SemanticClass cls=sobj.getSemanticClass();
        return cls.newGenericInstance(sobj);
    }    
    
    public GenericObject getGenericObject(String uri, SemanticClass cls)
    {
        GenericObject ret=null;
        SemanticObject obj=getSemanticObject(uri);
        if(obj!=null)ret=cls.newGenericInstance(obj);
        return ret;
    }
    
//    public SemanticClass createSemanticClass(String uri)
//    {
//        OntModel m=m_ontology;
//        m.createStatement(m.getResource(uri), m.getProperty(SemanticVocabulary.RDF_TYPE), m.getResource(SemanticVocabulary.OWL_CLASS));
//        OntClass ontcls=m_ontology.getOntClass(uri);
//        return new SemanticClass(ontcls);
//    }

    public Iterator<SemanticObject> listInstancesOfClass(SemanticClass cls)
    {
        Property rdf=m_ontology.getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=m_ontology.listStatements(null, rdf, cls.getOntClass());
        return new SemanticIterator(stit, true);
    }


    public SemanticProperty getSemanticProperty(String uri)
    {
        return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
    }
    

}
