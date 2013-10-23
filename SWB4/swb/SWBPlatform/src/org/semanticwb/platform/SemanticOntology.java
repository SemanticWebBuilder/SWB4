/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticOntology.
 * 
 * @author Jei
 */
public class SemanticOntology
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SemanticOntology.class);


    /** The m_ontology. */
    private OntModel m_ontology;
    
    /** The m_name. */
    private String m_name;
    
    /** The sub models. */
    private ArrayList<SemanticModel> subModels=new ArrayList();
    
    private HashMap<String, SemanticModel> ontologys = new HashMap();

    /**
     * Instantiates a new semantic ontology.
     * 
     * @param name the name
     * @param ontology the ontology
     */
    public SemanticOntology(String name, OntModel ontology)
    {
        this.m_ontology=ontology;
        this.m_name=name;
        init();
    }    
    
    /**
     * Inits the.
     */
    private void init()
    {
        
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return m_name;
    }
    
    /**
     * Gets the rDF ont model.
     * 
     * @return the rDF ont model
     */
    public OntModel getRDFOntModel()
    {
        return m_ontology;
    }
    
    /**
     * Adds the sub model.
     * 
     * @param model the model
     * @param rebind the rebind
     */
    public void addSubModel(SemanticModel model, boolean rebind)
    {
        subModels.add(model);                
        m_ontology.addSubModel(model.getRDFModel(),rebind);
    }
    
    /**
     * Adds the sub model join.
     * 
     * @param model the model
     * @param rebind the rebind
     */
    public boolean addOWLModel(String owl, SemanticModel model, boolean rebind)
    {
        if(!ontologys.containsKey(owl))
        {
            ontologys.put(owl, model);
            
//            subModels.add(model);                
//            m_ontology.addSubModel(model.getRDFModel(),rebind);
            
            subModels.add(model);                            
            m_ontology.add(model.getRDFModel());
            m_ontology.setNsPrefixes(model.getRDFModel().getNsPrefixMap());
            if(rebind)m_ontology.rebind();
            return true;
        }
        return false;
    }    

    /**
     * Removes the sub model.
     * 
     * @param model the model
     * @param rebind the rebind
     */
    public void removeSubModel(SemanticModel model, boolean rebind)
    {
        subModels.remove(model);
        m_ontology.removeSubModel(model.getRDFModel(),rebind);
    }

    /**
     * List sub models.
     * 
     * @return the iterator
     */
    public Iterator<SemanticModel> listSubModels()
    {
        return subModels.iterator();
    }
    
    /**
     * Rebind.
     */
    public void rebind()
    {
        m_ontology.rebind();
    }

    //TODO: Mejorar performance
    /**
     * Gets the resource.
     * 
     * @param uri the uri
     * @return the resource
     */
    public Resource getResource(String uri)
    {
        log.debug("getResource:"+uri);
        //new Exception().printStackTrace();
        if(uri==null || uri.length()==0)return null;
        Resource ret=null;
        Property type=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty();
        int i=uri.indexOf('#');
        if(i==-1)i=uri.lastIndexOf('/');
        if(i>0)
        {
            String base=uri.substring(0,i+1);
            log.trace("getResource in Model(1):"+uri+" "+base);
            SemanticModel model=SWBPlatform.getSemanticMgr().getModelByNS(base);
            if(model!=null)
            {
                Resource res=model.getRDFModel().getResource(uri);
                if(model.getRDFModel().contains(res, type))
                {
                    ret=res;
                }
            }
        }

        if(ret==null)
        {
            log.trace("getResource in Schema(2):");
            //new Exception().printStackTrace();
            Model model=SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel();
            Resource res=model.getResource(uri);
            if(model.contains(res, type))
            {
                ret=res;
            }
        }

        if(ret==null)
        {
            log.trace("getResource in All Model(3):");
            //new Exception().printStackTrace();
            Iterator<Entry<String, SemanticModel>> it=SWBPlatform.getSemanticMgr().getModels().iterator();
            while(it.hasNext())
            {
                Entry<String, SemanticModel> ent=it.next();
                SemanticModel model=ent.getValue();
                Resource res=model.getRDFModel().getResource(uri);
                if(model.getRDFModel().contains(res, type))
                {
                    ret=res;
                }
                if(ret!=null)break;
            }
        }
        if(ret==null)
        {
            log.trace("getResource in Ontology(4):");
            //new Exception().printStackTrace();
//            Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
//            Resource res=model.getResource(uri);
//            if(model.contains(res, type))
//            {
//                ret=res;
//            }
        }
        //System.out.println("ret:"+ret);
        //if(ret==null)log.warn("Uri not Found:"+uri,new AssertionError());
        return ret;
    }
    
//    public SemanticClass getSemanticObjectClass(Resource res)
//    {
//        Statement stm=res.getRequiredProperty(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty());
//        if(stm!=null)
//        {
//            return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
//        }
//        return null;
//    }
    
    /**
     * Gets the semantic object.
     *
     * @param uri the uri
     * @return the semantic object
     */
    public SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=SemanticObject.createSemanticObject(uri);
        return ret;        
    }

    
    /**
     * Gets the generic object.
     * 
     * @param uri the uri
     * @return the generic object
     */
    public GenericObject getGenericObject(String uri)
    {
        GenericObject ret=null;
        SemanticObject sobj=getSemanticObject(uri);
        if(sobj!=null)ret=sobj.createGenericInstance();
        return ret;
        //SemanticClass cls=sobj.getSemanticClass();
        //return cls.newGenericInstance(sobj);
    }    
    
    /**
     * Gets the generic object.
     * 
     * @param uri the uri
     * @param cls the cls
     * @return the generic object
     */
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

    /**
 * List instances of class.
 * 
 * @param cls the cls
 * @return the iterator
 */
public Iterator<SemanticObject> listInstancesOfClass(SemanticClass cls)
    {
        Property rdf=m_ontology.getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=m_ontology.listStatements(null, rdf, cls.getOntClass());
        return new SemanticIterator(stit, true);
    }


    /**
     * Gets the semantic property.
     * 
     * @param uri the uri
     * @return the semantic property
     */
    public SemanticProperty getSemanticProperty(String uri)
    {
        return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
    }
    

}
