/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.util.CollectionUtils;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.rdf.RemoteGraph;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticModel.
 * 
 * @author Jei
 */
public class SemanticModel 
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SemanticModel.class);
    
    /** The m_model. */
    private Model m_model;
    
    /** The m_ont. */
    OntModel m_ont;
    
    /** The dataset. */
    Dataset dataset=null;

    /** The m_name. */
    private String m_name;
    
    /** The m_name space. */
    private String m_nameSpace;
    
    /** The m_model object. */
    private SemanticObject m_modelObject;
    
    /** The m_trace. */
    private boolean m_trace=true;

    /** The m_classes. */
    private List m_classes=null;

    /**
     * Instantiates a new semantic model.
     * 
     * @param name the name
     * @param model the model
     */
    public SemanticModel(String name, Model model)
    {
        this.m_model=model;
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
     * Define si el modelo loggeara los cambios hechos por el usuario.
     * 
     * @return true, if is traceable
     * @return
     */
    public boolean isTraceable()
    {
        return m_trace;
    }

    /**
     * Activa o desactiva el logger de cambios en el modelo.
     * 
     * @param trace the new traceable
     */
    public void setTraceable(boolean trace)
    {
        m_trace=trace;
    }

    /**
     * List subjects.
     * 
     * @param prop the prop
     * @param value the value
     * @return the iterator
     */
    public Iterator<SemanticObject> listSubjects(SemanticProperty prop,String value)
    {
        SemanticIterator<SemanticObject> it = new SemanticIterator(getRDFModel().listStatements(null, prop.getRDFProperty(), value), true);
        return it;
    }

    /**
     * List subjects.
     * 
     * @param prop the prop
     * @param obj the obj
     * @return the iterator
     */
    public Iterator<SemanticObject> listSubjects(SemanticProperty prop,SemanticObject obj)
    {
        SemanticIterator<SemanticObject> it = new SemanticIterator(getRDFModel().listStatements(null, prop.getRDFProperty(), obj.getRDFResource()), true);
        return it;
    }

    /**
     * List subjects by class.
     * 
     * @param prop the prop
     * @param obj the obj
     * @param cls the cls
     * @return the iterator
     */
    public Iterator<SemanticObject> listSubjectsByClass(SemanticProperty prop,SemanticObject obj, SemanticClass cls)
    {
        ArrayList ret=new ArrayList();
        SemanticIterator<SemanticObject> it = new SemanticIterator(getRDFModel().listStatements(null, prop.getRDFProperty(), obj.getRDFResource()), true);
        //Filter by class
        while (it.hasNext())
        {
            SemanticObject semanticObject = it.next();
            if(semanticObject.instanceOf(cls))
            {
                ret.add(semanticObject);
            }
        }
        return ret.iterator();
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
     * Gets the rDF model.
     * 
     * @return the rDF model
     */
    public Model getRDFModel()
    {
        return m_model;
    }

    /**
     * Gets the rDF ont model.
     * 
     * @return the rDF ont model
     */
    public OntModel getRDFOntModel()
    {
        if(m_ont==null)
        {
            m_ont=ModelFactory.createOntologyModel(SWBPlatform.getSemanticMgr().getModelSpec(),m_model);
            Iterator<SemanticModel> it=SWBPlatform.getSemanticMgr().listBaseModels();
            while (it.hasNext()) {
                SemanticModel model = it.next();
                m_ont.addSubModel(model.getRDFModel());
            }
        }
        return m_ont;
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
    
    /**
 * Gets the semantic object.
 * 
 * @param uri the uri
 * @return the semantic object
 */
public SemanticObject getSemanticObject(String uri)
    {
        Property type=m_model.getProperty(SemanticVocabulary.RDF_TYPE);
        SemanticObject ret=SemanticObject.getSemanticObject(uri);
        if(ret==null)
        {
            Resource res=m_model.createResource(uri);
            //System.out.println("getSemanticObject:"+res+" "+type);
            if(m_model.contains(res,type))
            {
                ret=SemanticObject.createSemanticObject(res);
            }
        }
        return ret;
    }

    /**
     * Creates the semantic object by id.
     * 
     * @param id the id
     * @param cls the cls
     * @return the semantic object
     */
    public SemanticObject createSemanticObjectById(String id, SemanticClass cls)
    {
        return createSemanticObject(getObjectUri(id, cls), cls);
    }

    /**
     * Creates the semantic object.
     * 
     * @param uri the uri
     * @param cls the cls
     * @return the semantic object
     */
    public SemanticObject createSemanticObject(String uri, SemanticClass cls)
    {
        Resource res=m_model.createResource(uri);
        res.addProperty(m_model.getProperty(SemanticVocabulary.RDF_TYPE), cls.getOntClass());
        SemanticObject ret=cls.newInstance(res);
        SWBPlatform.getSemanticMgr().notifyChange(ret, null,null, "CREATE");
        //Default Values
        Iterator<SemanticProperty> it=cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            String def=prop.getDefaultValue();
            //System.out.print("\nprop:"+prop+" def:"+def);
            if(def!=null)
            {
                SemanticLiteral lit=SemanticLiteral.valueOf(prop,def);
                //System.out.print(" lit:"+lit.getValue());
                ret.setLiteralProperty(prop, lit);
            }
        }
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
        SemanticObject obj=getSemanticObject(uri);
        SemanticClass cl=obj.getSemanticClass();
        if(cl!=null && obj!=null)
        {
            ret=cl.newGenericInstance(obj);
        }
        return ret;
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
        if(obj!=null)
        {
            ret=cls.newGenericInstance(obj);
        }
        return ret;
    }
    
    /**
     * Creates the generic object.
     * 
     * @param uri the uri
     * @param cls the cls
     * @return the generic object
     */
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
    
    /**
     * Removes the semantic object.
     * 
     * @param uri the uri
     */
    public void removeSemanticObject(String uri)
    {
        SemanticObject obj=getSemanticObject(uri);
        if(obj!=null)obj.remove();
    }  
    
    /**
     * Removes the semantic object.
     * 
     * @param obj the obj
     */
    public void removeSemanticObject(SemanticObject obj)
    {
        obj.remove();
    }     
    
    /**
     * Removes the generic object.
     * 
     * @param obj the obj
     */
    public void removeGenericObject(GenericObject obj)
    {
        removeSemanticObject(obj.getSemanticObject());
    }

    /**
     * Sets the name space.
     * 
     * @param ns the new name space
     */
    public void setNameSpace(String ns)
    {
        m_nameSpace=ns;
    }

    /**
     * Gets the name space.
     * 
     * @return the name space
     */
    public String getNameSpace() 
    {
        if(m_nameSpace==null)
        {
            this.m_nameSpace=m_model.getNsPrefixURI(m_name);

            if(m_nameSpace==null)
            {
                //System.out.println("getNameSpace");
                Iterator<Statement> it=m_model.listStatements(null,m_model.getProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),m_model.getResource("http://www.w3.org/2002/07/owl#Ontology"));
                while(it.hasNext())
                {
                    Statement st=it.next();
                    //System.out.println("sub:"+st.getSubject().getURI());
                    m_nameSpace=st.getSubject().getURI();
                    break;
                }
            }
        }
        return m_nameSpace;
    }    
    
    /**
     * Gets the object uri.
     * 
     * @param id the id
     * @param cls the cls
     * @return the object uri
     */
    public String getObjectUri(String id, SemanticClass cls)
    {
        String ret=getNameSpace();
        if(cls!=null && !cls.isSWBModel())
        {
            //cls=cls.getRootClass(); //busca la clase raiz
            ret+=cls.getClassGroupId()+":"+id;
        }else
        {
            ret+=id;
        }
        //System.out.println("uri:"+ret);
        return ret;
    }
    
    /**
     * Gets the model object.
     * 
     * @return the model object
     */
    public SemanticObject getModelObject()
    {
        if(m_modelObject==null)
        {
            m_modelObject=getSemanticObject(getObjectUri(getName(), null));
        }
        return m_modelObject;
    }

    /**
     * List instances of class.
     * 
     * @param cls the cls
     * @return the iterator
     */
    public Iterator<SemanticObject> listInstancesOfClass(SemanticClass cls)
    {
        return listInstancesOfClass(cls,true);
    }

    /**
     * List instances of class.
     * 
     * @param cls the cls
     * @param subclasses the subclasses
     * @return the iterator
     */
    public Iterator<SemanticObject> listInstancesOfClass(SemanticClass cls, boolean subclasses)
    {
        Property rdf=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty();

        StmtIterator stit=getRDFModel().listStatements(null, rdf, cls.getOntClass());
        Iterator<SemanticObject> ret=new SemanticIterator(stit, true);

        if(subclasses)
        {
            ArrayList arr=new ArrayList();
            while(ret.hasNext())
            {
                arr.add(ret.next());
            }
            Iterator<SemanticClass> clsit=cls.listSubClasses(false);
            while(clsit.hasNext())
            {
                SemanticClass scls=clsit.next();
                Iterator sit=listInstancesOfClass(scls,false);
                while(sit.hasNext())
                {
                    arr.add(sit.next());
                }
            }
            ret=arr.iterator();
        }
        return ret;
    }
    
    /**
     * Creates the semantic property.
     * 
     * @param uri the uri
     * @param cls the cls
     * @param uriType the uri type
     * @param uriRang the uri rang
     * @return the semantic property
     */
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
    
    /**
     * Creates the semantic class.
     * 
     * @param uri the uri
     * @return the semantic class
     */
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
    
    /**
     * Register class.
     * 
     * @param uri the uri
     * @return the semantic class
     */
    public SemanticClass registerClass(String uri)
    {
        OntModel ont=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
        OntClass ontcls=ont.getOntClass(uri);
        SemanticClass cls=new SemanticClass(ontcls);
        SWBPlatform.getSemanticMgr().getVocabulary().registerClass(cls);
        return cls;
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
        write(out,null);
    }


    /**
     * <p>Write a serialization of this model as an XML document.
     * </p>
     * <p>The language in which to write the model is specified by the
     * <code>lang</code> argument.  Predefined values are "RDF/XML",
     * "RDF/XML-ABBREV", "N-TRIPLE" and "N3".  The default value is
     * represented by <code>null</code> is "RDF/XML".</p>
     * 
     * @param out The output stream to which the XML will be written
     * @param lang the lang
     * @return This model
     */
    public void write(OutputStream out, String lang)
    {
        if(m_model instanceof OntModel)
        {
            ((OntModel)m_model).writeAll(out, lang, null);
        }else
        {
            m_model.write(out,lang);
        }
    }

    /**
     * Regresa contador en base a la cadena <i>name</i>, sin incrementar el valor del mismo.
     * 
     * @param name the name
     * @return the counter value
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
     * Asigna el valor <i>val</a> al contador de nombre <i>name</i>.
     * 
     * @param name the name
     * @param val the val
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
     * Regresa contador en base a la cadena <i>name</i>, e incrementa el valor en uno.
     * 
     * @param cls the cls
     * @return the counter
     */
    public synchronized long getCounter(SemanticClass cls)
    {
        //System.out.println("cls:"+cls+" "+cls.getRootClass());
        return getCounter(cls.getClassGroupId());
    }

    /**
     * Regresa contador en base a la cadena <i>name</i>, e incrementa el valor en uno.
     * 
     * @param name the name
     * @return the counter
     */
    public synchronized long getCounter(String name)
    {
        //System.out.println("counter:"+name);
        long ret=getCounterValue(name);
        ret++;
        setCounterValue(name, ret);
        return ret;
    }

    /**
     * Delete counter value.
     * 
     * @param name the name
     */
    public synchronized void deleteCounterValue(String name)
    {
        String uri=getNameSpace()+"counter";
        Resource res=getRDFModel().createResource(uri+":"+name);
        Property prop=getRDFModel().createProperty(uri);
        getRDFModel().remove(res, prop, null);
    }

    /**
     * Spar ql query.
     * 
     * @param queryString the query string
     * @return the query execution
     */
    public QueryExecution sparQLQuery(String queryString)
    {
        log.debug("sparQLQuery:"+queryString);
        QueryExecution ret=null;
        Query query = QueryFactory.create(queryString);
        if(m_model.getGraph() instanceof RemoteGraph)
        {
            ret=QueryExecutionFactory.sparqlService(((RemoteGraph)m_model.getGraph()).getUri(), query);
        }else
        {
            ret=QueryExecutionFactory.create(query, m_model);
        }
        return ret;
    }
    
    /**
     * Spar ql ontology query.
     * 
     * @param queryString the query string
     * @return the query execution
     */
    public QueryExecution sparQLOntologyQuery(String queryString)
    {
        log.debug("sparQLOntologyQuery:"+queryString);
        Query query = QueryFactory.create(queryString);
        return QueryExecutionFactory.create(query, getRDFOntModel());
    }

    /**
     * Lista las clases relacionadas al modelo y sus superclases con la propiedad hasClass.
     * 
     * @return clases relacionadas al modelo con la propiedad hasClass
     */
    public Iterator<SemanticClass> listModelClasses()
    {
        SemanticClass cls=getModelObject().getSemanticClass();
        if(m_classes==null)
        {
            m_classes=SWBUtils.Collections.copyIterator(cls.listModelClasses());
            Iterator<SemanticClass> it=cls.listSuperClasses();
            while (it.hasNext())
            {
                SemanticClass semanticClass = it.next();
                if(semanticClass.isSWBModel())
                {
                    m_classes.addAll(SWBUtils.Collections.copyIterator(semanticClass.listModelClasses()));
                }
            }

        }
        return m_classes.iterator();
    }

    /**
     * Checks for model class.
     * 
     * @param cls the cls
     * @return true, if successful
     */
    public boolean hasModelClass(SemanticClass cls)
    {
        if(m_classes==null)
        {
            listModelClasses();
        }
        return m_classes.contains(cls);
    }

}
