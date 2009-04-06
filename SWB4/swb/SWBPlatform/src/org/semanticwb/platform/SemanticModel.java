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
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
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

    public Iterator<SemanticObject> listSubjects(SemanticProperty prop,String value)
    {
        SemanticIterator<SemanticObject> it = new SemanticIterator(getRDFModel().listStatements(null, prop.getRDFProperty(), value), true);
        return it;
    }

    public String getName() {
        return m_name;
    }
    
    public Model getRDFModel()
    {
        return m_model;
    }
    
//    public SemanticClass getSemanticObjectClass(Resource res)
//    {
//        Statement stm=res.getProperty(res.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
//        if(stm!=null)
//        {
//            return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
//        }
//        return null;
//    }
    
//    public SemanticClass getSemanticObjectClass(String uri)
//    {
//        SemanticClass ret=null;
//        Resource res=m_model.getResource(uri);
//        if(res!=null)
//        {
//            ret=getSemanticObjectClass(res);
//        }
//        return ret;
//    }
    
    public SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=SemanticObject.getSemanticObject(uri);
        if(ret==null)
        {
            Resource res=m_model.getResource(uri);
            if(m_model.containsResource(res))
            {
                ret=SemanticObject.createSemanticObject(res);
            }
        }
        return ret;
    }

    public SemanticObject createSemanticObjectById(String id, SemanticClass cls)
    {
        return createSemanticObject(getObjectUri(id, cls), cls);
    }

    public SemanticObject createSemanticObject(String uri, SemanticClass cls)
    {
        Resource res=m_model.createResource(uri);
        res.addProperty(m_model.getProperty(SemanticVocabulary.RDF_TYPE), cls.getOntClass());
        SemanticObject ret=cls.newInstance(res);
        SWBPlatform.getSemanticMgr().notifyChange(ret, null, "CREATE");
        return ret;
    }    
    
    public GenericObject getGenericObject(String uri)
    {
        GenericObject ret=null;
        SemanticObject obj=getSemanticObject(uri);
        SemanticClass cl=obj.getSemanticClass();
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
        SemanticObject obj=getSemanticObject(uri);
        if(obj!=null)obj.remove();
    }  
    
    public void removeSemanticObject(SemanticObject obj)
    {
        obj.remove();
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
        String ret=getNameSpace();
        if(cls!=null && !cls.isSWBModel())
        {
            cls=cls.getRootClass(); //busca la clase raiz
            ret+=cls.getClassId()+":"+id;
        }else
        {
            ret+=id;
        }
        //System.out.println("uri:"+ret);
        return ret;
    }
    
    public SemanticObject getModelObject()
    {
        if(m_modelObject==null)
        {
            m_modelObject=getSemanticObject(getObjectUri(getName(), null));
        }
        return m_modelObject;
    }

    public Iterator<SemanticObject> listInstancesOfClass(SemanticClass cls)
    {
        return listInstancesOfClass(cls,true);
    }

    public Iterator<SemanticObject> listInstancesOfClass(SemanticClass cls, boolean subclasses)
    {
        Iterator<SemanticObject>ret=null;
        Property rdf=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty();
//        Iterator clsit=cls.listSubClasses();
//        if(subclasses && clsit.hasNext())
//        {
//            ArrayList<Statement> arr=new ArrayList();
//            StmtIterator stit=getRDFModel().listStatements(null, rdf, cls.getOntClass());
//            //TODO
//        }else
//        {
            StmtIterator stit=getRDFModel().listStatements(null, rdf, cls.getOntClass());
            ret=new SemanticIterator(stit, true);
//        }
        return ret;
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
        
        cls=new SemanticClass(cls.getOntClass());                         //actualizar clase
        SWBPlatform.getSemanticMgr().getVocabulary().registerClass(cls);  //
        //TODO:notify this
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
        //TODO:notify this
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
        return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
    }

    /**
     * <p>Write a serialization of this model as an XML document.
     * </p>
     * <p>The language in which to write the model is specified by the
     * <code>lang</code> argument.  Predefined values are "RDF/XML",
     * "RDF/XML-ABBREV", "N-TRIPLE" and "N3".  The default value is
     * represented by <code>null</code> is "RDF/XML".</p>
     * @param out The output stream to which the XML will be written
     * @return This model
     */
    public void write(OutputStream out)
    {
        if(m_model instanceof OntModel)
        {
            ((OntModel)m_model).writeAll(out, null, null);
        }else
        {
            m_model.write(out);
        }
    }

    /**
     * Regresa contador en base a la cadena <i>name</i>, sin incrementar el valor del mismo
     */
    public synchronized long getCounterValue(String name)
    {
        String uri=getNameSpace()+"counter";
        Resource res=getRDFModel().createResource(uri+":"+name);
        Property prop=getRDFModel().createProperty(uri);
        StmtIterator it=getRDFModel().listStatements(res, prop, (String)null);
        if(it.hasNext())
        {
            Statement stmt=it.nextStatement();
            return stmt.getLong();
        }
        return 0;
    }

    /**
     * Asigna el valor <i>val</a> al contador de nombre <i>name</i>
     */
    public synchronized void setCounterValue(String name, long val)
    {
        String uri=getNameSpace()+"counter";
        Resource res=getRDFModel().createResource(uri+":"+name);
        Property prop=getRDFModel().createProperty(uri);
        StmtIterator it=getRDFModel().listStatements(res, prop, (String)null);
        if(it.hasNext())
        {
            Statement stmt=it.nextStatement();
            stmt.changeLiteralObject(val);
        }else
        {
            Statement stmt=getRDFModel().createLiteralStatement(res, prop, val);
            getRDFModel().add(stmt);
        }
    }

    /**
     * Regresa contador en base a la cadena <i>name</i>, e incrementa el valor en uno
     */
    public synchronized long getCounter(SemanticClass cls)
    {
        //System.out.println("cls:"+cls+" "+cls.getRootClass());
        return getCounter(cls.getRootClass().getClassId());
    }

    /**
     * Regresa contador en base a la cadena <i>name</i>, e incrementa el valor en uno
     */
    public synchronized long getCounter(String name)
    {
        //System.out.println("counter:"+name);
        long ret=getCounterValue(name);
        ret++;
        setCounterValue(name, ret);
        return ret;
    }

    public synchronized void deleteCounterValue(String name)
    {
        String uri=getNameSpace()+"counter";
        Resource res=getRDFModel().createResource(uri+":"+name);
        Property prop=getRDFModel().createProperty(uri);
        getRDFModel().remove(res, prop, null);
    }

}
