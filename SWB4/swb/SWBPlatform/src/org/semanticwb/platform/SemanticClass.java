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

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.shared.PropertyNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.*;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import static org.semanticwb.SWBUtils.TEXT.*;
// TODO: Auto-generated Javadoc
/**
 * The Class SemanticClass.
 * 
 * @author Jei
 */
public class SemanticClass
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SemanticClass.class);

    /** The m_class. */
    private OntClass m_class;                           //clase ontologia schema
    
    /** The m_ontclass. */
    private OntClass m_ontclass;                        //clase ontologia global
    
    /** The m_props. */
    private HashMap<String,SemanticProperty> m_props;
    
    /** The m_is swb class. */
    private Boolean m_isSWBClass=null;

    /** No hay codigo generado de la clase */
    private Boolean m_isSWBVirtClass=null;
    
    /** The m_is swb interface. */
    private Boolean m_isSWBInterface=null;
    
    /** The m_is swb model. */
    private Boolean m_isSWBModel=null;
    
    /** The m_is swb form element. */
    private Boolean m_isSWBFormElement=null;
    
    /** The m_is swb semantic resource. */
    private Boolean m_isSWBSemanticResource=null;
    
    /** The m_class code name. */
    private String m_classCodeName=null;
    
    /** The m_class code package. */
    private String m_classCodePackage=null;
    
    /** The m_autogen id. */
    private Boolean m_autogenId=null;

    /** The m_autogen id. */
    private Boolean m_disableCache=null;

    /** The m_autogen id. */
    private Boolean m_notClassCodeGeneration=null;
    
    /** The m_cls. */
    private Class m_cls=null;
    
    /** The m_constructor. */
    private Constructor m_constructor=null;
    
    /** The display name property. */
    private SemanticProperty displayNameProperty;
    
    /** The herarquical props. */
    List<SemanticProperty> herarquicalProps;
    
    /** The inverse herarquical props. */
    List<SemanticProperty> inverseHerarquicalProps;
    
    /** The m_class group id. */
    private String m_classGroupId=null;
    
    /** The m_is class group id check. */
    private boolean m_isClassGroupIdCheck=false;

//    private Boolean isdragSupport=null;

    /** The disp object. */
    private boolean dispObject=false;
    
    /** The display object. */
    private SemanticObject displayObject=null;
    
    /** The m_class name. */
    private String m_className=null;
    
    /** The m_class name. */
    private String m_virtualClassName=null;    

    /** The m_observers. */
    private List<SemanticObserver> m_observers = null;

    /**
     * Instantiates a new semantic class.
     * 
     * @param oclass the oclass
     */
    public SemanticClass(OntClass oclass)
    {
        this.m_class=oclass;
        init();
    }

    /**
     * Instantiates a new semantic class.
     * 
     * @param classuri the classuri
     * @throws SWBException the sWB exception
     */
    public SemanticClass(String classuri) throws SWBException
    {
        this.m_class=SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getOntClass(classuri);
        if(this.m_class==null) throw new SWBException("OntClass Not Found");
        init();
    }

    /**
     * Gets the semantic object.
     * 
     * @return the semantic object
     */
    public SemanticObject getSemanticObject()
    {
        return SemanticObject.createSemanticObject(getURI());
    }

    /**
     * Inits the.
     */
    private void init()
    {
        //TODO
//        m_ontclass=m_class;
//        if(m_class.getURI()!=null)
//        {
//            m_ontclass=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getOntClass(m_class.getURI());
//        }
        //System.out.println(m_ontclass+" "+m_ontclass);
        m_props=new HashMap();
        herarquicalProps=new ArrayList();
        inverseHerarquicalProps=new ArrayList();
        m_observers=Collections.synchronizedList(new ArrayList());
        
        // super-classes
        //System.out.println("m_class1:"+m_class);
        //Thread.currentThread().dumpStack();
        int x=0;
        for (Iterator i = m_class.listDeclaredProperties(false); i.hasNext(); )
        {
            Property prop=(Property)i.next();
            x++;
            SemanticProperty p=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(prop);
            //System.out.println("cls:"+this+" prop:"+prop+" name:"+p.isUsedAsName()+" he:"+p.isHeraquicalRelation()+" inv:"+p.isInverseHeraquicalRelation());
            if(p.isUsedAsName())displayNameProperty=p;
            if(p.isHeraquicalRelation())herarquicalProps.add(p);
            if(p.isInverseHeraquicalRelation())inverseHerarquicalProps.add(p);
            //System.out.println("p.getName():"+x+" "+p.getName()+" "+p);
            m_props.put( p.getName(), p);
        }
        //System.out.println("m_props:"+m_props.size());
        log.trace("SemanticClass:"+getName()+" "+getClassCodeName()+" "+m_class.getNameSpace()+" "+getPrefix());
        //System.out.println("Name:"+getName()+" "+getClassName()+" "+m_class.getNameSpace()+" "+getPrefix());
    }

    /**
     * regresa nombre local de la clase.
     * 
     * @return the name
     * @return
     */
    public String getName()
    {
        return m_class.getLocalName();
    }

    /**
     * Regresa Prefijo de la clase en base al NS de la ontologia.
     * 
     * @return the prefix
     */
    public String getPrefix()
    {
        //System.out.println("getPrefix:"+m_class+" "+m_class.getNameSpace()+" "+m_class.getOntModel().getNsURIPrefix(m_class.getNameSpace()));
        return m_class.getOntModel().getNsURIPrefix(m_class.getNameSpace());
    }

    /**
     * Regresa nombre de la clase con paquete, siempre y cuendo sea del tipo swb:Class.
     * 
     * @return the class name
     * @return
     */
    public String getClassName()
    {
        if(m_className==null)
        {
            SemanticClass cls=this;
            while(cls!=null)
            {
                if(cls.isSWB())
                {
                    m_className=cls.getClassCodeName();
                    if(cls.getCodePackage()!=null)                    
                    {
                        m_className=cls.getCodePackage()+"."+m_className;
                    }    
                    break;
                }else
                {
                    Iterator<SemanticClass> it=cls.listSuperClasses(true);
                    cls=null;
                    SemanticClass acls=null;
                    while(it.hasNext())
                    {
                        SemanticClass aux=it.next();
                        if(aux.isSWBClass())cls=aux;
                        if(!aux.isSWB())acls=aux;
                    }
                    if(cls==null)cls=acls;
                }
            }
        }
        return m_className;
    }
    
    /**
     * Regresa nombre de la clase con paquete, siempre y cuendo sea del tipo virtualClass.
     * 
     * @return the class name
     * @return
     */
    public String getVirtualClassName()
    {
        if(m_virtualClassName==null)
        {
            SemanticClass cls=this;
            while(cls!=null)
            {
                if(cls.isSWB() || cls.isSWBVirtualClass())
                {
                    m_virtualClassName=cls.getClassCodeName();
                    if(cls.getCodePackage()!=null)                    
                    {
                        m_virtualClassName=cls.getCodePackage()+"."+m_virtualClassName;
                    }    
                    break;
                }else
                {
                    Iterator<SemanticClass> it=cls.listSuperClasses(true);
                    cls=null;
                    SemanticClass acls=null;
                    while(it.hasNext())
                    {
                        SemanticClass aux=it.next();
                        if(aux.isSWBClass())cls=aux;
                        if(!aux.isSWB())acls=aux;
                    }
                    if(cls==null)cls=acls;
                }
            }
        }
        return m_virtualClassName;
    }    

    /**
     * Regresa paquete de la clase generica java definido den la ontologia.
     * 
     * @return the code package
     * @return
     */
    public String getCodePackage()
    {
        if(m_classCodePackage==null)
        {
            try
            {
                Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_CLASSCODEPACKAGE).getRDFProperty();
                m_classCodePackage=m_class.getRequiredProperty(prop).getString();
            } catch (Exception pnf){
                m_classCodePackage=SWBPlatform.getSemanticMgr().getCodePackage().getPackage(getPrefix());
            }
        }
        return m_classCodePackage;
    }

    /**
     * Gets the name in plural.
     * 
     * @return the name in plural
     */
    public String getNameInPlural()
    {
        String name=getUpperClassName();
        return getPlural(name);
    }

    
    
    
    /**
     * Gets the canonical name.
     * 
     * @return the canonical name
     */
    public String getCanonicalName()
    {
       String spackage=getCodePackage();
       if(spackage==null)
       {
           spackage="";
       }
       else
       {
           spackage=spackage+".";
       }
       if(getClassCodeName()!=null)
       {
            spackage+=toUpperCase(getClassCodeName());
       }
       else
       {
           spackage+=getNameInPlural();
       }
       return spackage;
    }
    
    /**
     * Gets the upper class name.
     * 
     * @return the upper class name
     */
    public String getUpperClassName()
    {
        if(getClassCodeName()!=null)
        {
            return toUpperCase(getClassCodeName());
        }
        else
        {
            return toUpperCase(getName());
        }
    }


    /**
     * Regresa nombre de la clase definida por la ontologia
     * Nombre de la clase sin paquete.
     * 
     * @return the class code name
     * @return
     */
    public String getClassCodeName()
    {
        if(m_classCodeName==null)
        {
            try
            {
                Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_CLASSCODENAME).getRDFProperty();
                //System.out.println("Class:"+m_class+" ->"+className);
                m_classCodeName=m_class.getRequiredProperty(prop).getString();
                //System.out.println("Class:"+m_class+" ->"+className);
                //if(m_classCodeName==null)m_classCodeName=SemanticObject.class.getName();
            } catch (Exception pnf){
                m_classCodeName=getName();
            }
            //TODO:corregir modelo para no hacer esto
            try
            {
                if(m_classCodeName!=null)
                {
                    m_classCodeName=(""+m_classCodeName.charAt(0)).toUpperCase()+m_classCodeName.substring(1);
                }
            }
            catch(IndexOutOfBoundsException iobe)
            {
                log.error("Class error definition "+this,iobe);
            }
            //log.trace("getClassCodeName:"+m_classCodeName);
        }
        return m_classCodeName;
    }

    /**
     * Checks if is autogen id.
     * 
     * @return true, if is autogen id
     */
    public boolean isAutogenId()
    {
        if(m_autogenId==null)
        {
            Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_AUTOGENID).getRDFProperty();
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
     * Checks if disble cache.
     *
     * @return true, if is disable cache
     */
    public boolean isDisableCache()
    {
        if(m_disableCache==null)
        {
            Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_DISABLECACHE).getRDFProperty();
            //System.out.println("Class:"+m_class+" ->"+className);
            try
            {
                m_disableCache=m_class.getRequiredProperty(prop).getBoolean();
            }catch(PropertyNotFoundException noe)
            {
                m_disableCache=false;
            }
        }
        //log.trace("isDisableCache:"+m_disableCache);
        return m_disableCache;
    }

    /**
     * Checks if disble cache.
     *
     * @return true, if is disable cache
     */
    public boolean isNotClassCodeGeneration()
    {
        if(m_notClassCodeGeneration==null)
        {
            Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_NOTCLASSCODEGENERATION).getRDFProperty();
            //System.out.println("Class:"+m_class+" ->"+className);
            try
            {
                m_notClassCodeGeneration=m_class.getRequiredProperty(prop).getBoolean();
            }catch(PropertyNotFoundException noe)
            {
                m_notClassCodeGeneration=false;
            }
        }
        //log.trace("isDisableCache:"+m_notClassCodeGeneration);
        return m_notClassCodeGeneration;
    }


//    /**
//     * si el objeto relacionado soporta drag and drop
//     * @return
//     */
//    public boolean isdragSupport()
//    {
//        if(isdragSupport==null)
//        {
//            isdragSupport=false;
//            Statement st=m_class.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DRAGSUPPORT));
//            if(st!=null)
//            {
//                isdragSupport=st.getBoolean();
//            }
//        }
//        return isdragSupport;
//    }

    /**
 * Regresa prefix:name.
 * 
 * @return the class id
 * @return
 */
    public String getClassId()
    {
        return getPrefix()+":"+getName();
    }

    /**
     * Usado para generar el identificador (uri) de las instancias de la clase,
     * asi como de sus subclases, tambien se usa para identificar al objeto en el URL (webpage:home).
     * 
     * @return the class group id
     * @return
     */
    public String getClassGroupId()
    {
        if(!m_isClassGroupIdCheck)
        {
            SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_CLASSGROUPID);

            SemanticClass cls=this;
            while(cls!=null)
            {
                SemanticLiteral lit=cls.getRequiredProperty(prop);
                if(lit!=null)m_classGroupId=lit.getString();

                if(m_classGroupId!=null)break;
                Iterator<SemanticClass> it=cls.listSuperClasses(true);
                cls=null;
                while(it.hasNext())
                {
                    SemanticClass aux=it.next();
                    if(aux.isSWBClass())cls=aux;
                }
            }

            m_isClassGroupIdCheck=true;
            if(m_classGroupId==null)
            {
                m_classGroupId=getPrefix()+"_"+getName();
            }
        }
        return m_classGroupId;
    }

    /**
     * Gets the required property.
     * 
     * @param prop the prop
     * @return the required property
     */
    public SemanticLiteral getRequiredProperty(SemanticProperty prop)
    {
        SemanticLiteral ret=null;
        Property iprop=prop.getRDFProperty();
        try
        {
            ret=new SemanticLiteral(m_class.getRequiredProperty(iprop).getLiteral());
        }catch(PropertyNotFoundException noe){}
        return ret;
    }

    /**
     * List required properties.
     * 
     * @param prop the prop
     * @return the iterator
     */
    public Iterator<SemanticLiteral> listRequiredProperties(SemanticProperty prop)
    {
        ArrayList<SemanticLiteral> literals=new ArrayList<SemanticLiteral>();
        Property iprop=prop.getRDFProperty();
        try
        {
             StmtIterator it=m_class.listProperties(iprop);
             while(it.hasNext())
             {
                 Statement statement=it.nextStatement();
                 literals.add(new SemanticLiteral(statement));
             }
             it.close();
        }catch(PropertyNotFoundException noe){}
        return literals.iterator();
    }

    /**
     * List object required properties.
     * 
     * @param prop the prop
     * @return the iterator
     */
    public Iterator<SemanticObject> listObjectRequiredProperties(SemanticProperty prop)
    {
        ArrayList<SemanticObject> objects=new ArrayList<SemanticObject>();
        Property iprop=prop.getRDFProperty();
        try
        {
             StmtIterator it=m_class.listProperties(iprop);
             while(it.hasNext())
             {
                 Statement statement=it.nextStatement();
                 Resource res=statement.getResource();
                 SemanticObject object=SemanticObject.createSemanticObject(res);
                 objects.add(object);
             }
             it.close();
        }catch(PropertyNotFoundException noe){}
        return objects.iterator();
    }





    /**
     * Lista las clases relacionadas a esta clase del tipo modelo con la propiedad hasClass
     * Solo si isSWBModel = true.
     * 
     * @return clases relacionadas a esta clase del tipo modelo con la propiedad hasClass
     */
    public Iterator<SemanticClass> listModelClasses()
    {
        Iterator ret=(new ArrayList()).iterator();
        if(isSWBModel()==true)
        {
            Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_HASCLASS).getRDFProperty();
            ret=new SemanticClassIterator(m_class.listProperties(prop));
        }
        return ret;
    }

    /**
     * Lista los nodos a mostrar en el arbol de SWB.
     * 
     * @return the iterator
     */
    public Iterator<SemanticObject> listHerarquicalNodes()
    {
        Iterator ret=(new ArrayList()).iterator();
        if(isSWBModel()==true)
        {
            Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_HASHERARQUICALNODE).getRDFProperty();
            ret=new SemanticIterator(m_class.listProperties(prop));
        }
        return ret;
    }


    /**
     * List owner models.
     * 
     * @return the iterator
     */
    public Iterator<SemanticClass> listOwnerModels()
    {
        ArrayList ret=new ArrayList();
        if(isSWBModel()==false)
        {
            Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_HASCLASS).getRDFProperty();
            StmtIterator it=m_class.getModel().listStatements(null, prop, m_class);
            //System.out.println("listOwnerModels:"+prop+"-"+m_class);
            while(it.hasNext())
            {
                Statement stmt=it.nextStatement();
                ret.add(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stmt.getSubject().getURI()));
            }
            it.close();
        }
        return ret.iterator();
    }


    /**
     * Gets the constructor.
     * 
     * @return the constructor
     */
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
               throw new IllegalArgumentException(nsme); //MAPS74 faltaba el throw
            }
        }
        return m_constructor;

    }

    /**
     * New instance.
     * 
     * @param uri the uri
     * @return the semantic object
     */
    public SemanticObject newInstance(String uri)
    {
        return SemanticObject.createSemanticObject(uri);
    }

    /**
     * New instance.
     * 
     * @param res the res
     * @return the semantic object
     */
    public SemanticObject newInstance(Resource res)
    {
        return SemanticObject.createSemanticObject(res);
    }

    /**
     * New generic instance.
     * 
     * @param res the res
     * @return the generic object
     */
    public GenericObject newGenericInstance(Resource res)
    {
        return SemanticObject.createSemanticObject(res).createGenericInstance();
    }

    /**
     * New generic instance.
     * 
     * @param obj the obj
     * @return the generic object
     */
    public GenericObject newGenericInstance(SemanticObject obj)
    {
        return obj.createGenericInstance();
    }

    /**
     * Crea una nueva instancia del Objeto Generico (no cache).
     * 
     * @param obj the obj
     * @return the generic object
     * @return
     */
    GenericObject construcGenericInstance(SemanticObject obj)
    {
        try
        {
            //System.out.print(getURI()+" ");
            //System.out.print(getClassName()+" ");
            //System.out.println(getConstructor());
            return (GenericObject)getConstructor().newInstance(obj);
        }
        catch(Exception ie)
        {
            log.event("Error creating object "+obj, ie);
            Throwable th=ie.getCause();
            int cause=0;
            while (null!=th) { 
                cause++;
                log.event("Cause "+cause+":", th);
                th = th.getCause();
            }
            log.event("Aborting....... "+th.getMessage());
            throw new AssertionError(ie);
        }
    }


    /**
     * Gets the object class.
     * 
     * @return the object class
     */
    public Class getObjectClass()
    {
        if(m_cls==null)
        {
            if(isSWBVirtualClass())
            {
                try
                {
                    m_cls=Class.forName(getVirtualClassName(), false, SWBPlatform.getSemanticMgr().getClassLoader());
                }catch(Exception noe){}
                //System.out.println("VirtualClass:"+m_cls);
            }
            
            if(m_cls==null)
            {
                try
                {
                    m_cls=Class.forName(getClassName());
                    //System.out.println("createClass:"+getClassName()+" "+m_cls);
                }catch(Exception e){log.error(e);}
                //System.out.println("NoVirtualClass:"+m_cls);
            }            
        }
        return m_cls;
    }

    /**
     * Gets the uRI.
     * 
     * @return the uRI
     */
    public String getURI()
    {
        return m_class.getURI();
    }

    /**
     * Regresa URI codificado para utilizar en ligas de html.
     * 
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }

    /**
     * Regresa el valor de la propiedad rdf:label de la clase.
     * 
     * @param lang the lang
     * @return the label
     */
    public String getLabel(String lang)
    {
        return m_class.getLabel(lang);
    }
    
    /**
     * Regresa el valor de la propiedad rdf:comment de la clase.
     * 
     * @param lang Language of the comment
     * @return The comment in the ontology
     */

    public String getComment(String lang)
    {
        return m_class.getComment(lang);
    }

    /**
     * Regresa el valor de la propiedad rdf:comment de la clase.
     * 
     * @return The comment in the ontology
     */

    public String getComment()
    {
        return m_class.getComment(null);
    }

    /**
     * Gets the display name.
     * 
     * @param lang the lang
     * @return the display name
     */
    public String getDisplayName(String lang)
    {
        String ret=null;
        SemanticObject obj=getDisplayObject();
        if(obj!=null)
        {
            if(lang!=null)
            {
                ret=obj.getProperty(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.RDFS_LABEL),null,lang);
            }
            if(ret==null)ret=obj.getProperty(obj.getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL));
        }
        if(ret==null && lang!=null)ret=getLabel(lang);
        if(ret==null)ret=getLabel(null);
        if(ret==null)ret=getPrefix()+":"+getName();
        return ret;
    }
    
    /**
     * Gets the display object.
     * 
     * @return the display object
     */
    public SemanticObject getDisplayObject()
    {
        if(!dispObject)
        {
            Statement st=m_class.getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYOBJECT));
            if(st!=null)
            {
                displayObject=SemanticObject.createSemanticObject(st.getResource());
                dispObject=true;
            }
        }
        return displayObject;
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

    /**
     * List instances.
     *
     * @return the iterator
     */
    public Iterator<SemanticObject> listInstances()
    {
        return listInstances(false);
    }

    /**
     * List instances.
     * 
     * @param direct the direct
     * @return the iterator
     */
    public Iterator<SemanticObject> listInstances(boolean direct)
    {
        //System.out.println("listInstances:"+this);
        //TODO: concat iterators
        ArrayList<SemanticObject> arr=new ArrayList();
        //Iterar sobre modelos de datos
        Iterator<Map.Entry<String,SemanticModel>> it2=SWBPlatform.getSemanticMgr().getModels().iterator();
        while (it2.hasNext())
        {
            Map.Entry<String, SemanticModel> entry = it2.next();
            Iterator<SemanticObject> it=entry.getValue().listInstancesOfClass(this,!direct);
            while (it.hasNext())
            {
                SemanticObject semanticObject = it.next();
                arr.add(semanticObject);
                //System.out.println(entry.getKey()+" "+semanticObject);
            }
        }        
        //Iterar sobre schema
        Iterator<SemanticObject> it3=new SemanticObjectIterator(m_class.listInstances(direct));
        while (it3.hasNext())
        {
            SemanticObject semanticObject = it3.next();
            arr.add(semanticObject);
            //System.out.println("schema "+semanticObject);
        }   
        return arr.iterator();
//        //return SWBPlatform.getSemanticMgr().getOntology().listInstancesOfClass(this);
//        if(m_ontclass!=null)
//        {
//            return new SemanticObjectIterator(m_ontclass.listInstances(direct));
//        }else
//        {
//            return new SemanticObjectIterator(m_class.listInstances(direct));
//        }
    }

    /**
     * List schema instances.
     * 
     * @return the iterator
     */
    public Iterator<SemanticObject> listSchemaInstances()
    {
        return listSchemaInstances(false);
    }

    /**
     * List schema instances.
     * 
     * @param direct the direct
     * @return the iterator
     */
    public Iterator<SemanticObject> listSchemaInstances(boolean direct)
    {
        return new SemanticObjectIterator(m_class.listInstances(direct));
    }

    /**
     * List generic instances.
     * 
     * @return the iterator
     */
    public Iterator listGenericInstances()
    {
        return listGenericInstances(false);
    }

    /**
     * List generic instances.
     * 
     * @param direct the direct
     * @return the iterator
     */
    public Iterator listGenericInstances(boolean direct)
    {
        return new GenericIterator(listInstances(direct));
    }

    /**
     * List schema generic instances.
     * 
     * @return the iterator
     */
    public Iterator listSchemaGenericInstances()
    {
        return listSchemaGenericInstances(false);
    }

    /**
     * List schema generic instances.
     * 
     * @param direct the direct
     * @return the iterator
     */
    public Iterator listSchemaGenericInstances(boolean direct)
    {
        return new GenericIterator(listSchemaInstances(direct));
    }

    /**
     * Gets the property.
     * 
     * @param name the name
     * @return the property
     */
    public SemanticProperty getProperty(String name)
    {
        return m_props.get(name);
    }

    /**
     * Checks for property.
     * 
     * @param name the name
     * @return true, if successful
     */
    public boolean hasProperty(String name)
    {
        return m_props.containsKey(name);
    }

    /**
     * List properties.
     * 
     * @return the iterator
     */
    public Iterator<SemanticProperty> listProperties()
    {
        return m_props.values().iterator();
    }

    /**
     * List sort properties.
     * 
     * @return the iterator
     */
    public Iterator<SemanticProperty> listSortProperties()
    {
        final SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");

        TreeSet<SemanticProperty> props=new TreeSet(new Comparator(){
            public int compare(Object o1, Object o2){
                SemanticObject sobj1=((SemanticProperty)o1).getDisplayProperty();
                SemanticObject sobj2=((SemanticProperty)o2).getDisplayProperty();
                int v1=999999999;
                int v2=999999999;
                if(sobj1!=null)v1=sobj1.getIntProperty(swb_index);
                if(sobj2!=null)v2=sobj2.getIntProperty(swb_index);
                return v1<v2?-1:1;
            }
        });

        Iterator<SemanticProperty> it=listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            props.add(prop);
        }
        return props.iterator();
    }

    /**
     * Gets the ont class.
     * 
     * @return the ont class
     */
    public OntClass getOntClass()
    {
        return m_class;
    }

    /**
     * Checks if is super class.
     * 
     * @param cls the cls
     * @return true, if is super class
     */
    public boolean isSuperClass(SemanticClass cls)
    {
        return isSuperClass(cls, false);
    }

    /**
     * Checks if is super class.
     * 
     * @param cls the cls
     * @param direct the direct
     * @return true, if is super class
     */
    public boolean isSuperClass(SemanticClass cls, boolean direct)
    {
        return cls.isSubClass(this, direct);
    }

    /**
     * Checks if is sub class.
     * 
     * @param cls the cls
     * @return true, if is sub class
     */
    public boolean isSubClass(SemanticClass cls)
    {
        return isSubClass(cls, false);
    }

    /**
     * Checks if is sub class.
     * 
     * @param cls the cls
     * @param direct the direct
     * @return true, if is sub class
     */
    public boolean isSubClass(SemanticClass cls, boolean direct)
    {
        boolean ret=false;
        Iterator it=m_class.listSuperClasses(direct);
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

    /**
     * List sub classes.
     * 
     * @return the iterator
     */
    public Iterator<SemanticClass> listSubClasses()
    {
        return listSubClasses(false);
    }

    /**
     * List sub classes.
     * 
     * @param direct the direct
     * @return the iterator
     */
    public Iterator<SemanticClass> listSubClasses(boolean direct)
    {
        return new SemanticClassIterator(m_class.listSubClasses(direct));
    }

    /**
     * List super classes.
     * 
     * @return the iterator
     */
    public Iterator<SemanticClass> listSuperClasses()
    {
        return listSuperClasses(false);
    }

    /**
     * List super classes.
     * 
     * @param direct the direct
     * @return the iterator
     */
    public Iterator<SemanticClass> listSuperClasses(boolean direct)
    {
        return new SemanticClassIterator(m_class.listSuperClasses(direct));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return m_class.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return m_class.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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

    /**
     * Check type.
     */
    private void checkType()
    {
        m_isSWBClass=false;
        m_isSWBVirtClass=false;
        m_isSWBInterface=false;
        m_isSWBModel=false;
        m_isSWBFormElement=false;
        m_isSWBSemanticResource=false;
        for (Iterator i = m_class.listRDFTypes(false); i.hasNext(); )
        {
            Resource res=(Resource)i.next();
            String uri=res.getURI();
            if(uri.equals(SemanticVocabulary.SWB_MODEL))
            {
                m_isSWBModel = true;
                break;
            }else if(uri.equals(SemanticVocabulary.SWB_CLASS))
            {

                if(isNotClassCodeGeneration())
                {
                    m_isSWBVirtClass = true;
                }
                else
                {
                    m_isSWBClass = true;
                }
                break;
            }else if(uri.equals(SemanticVocabulary.SWB_INTERFACE))
            {
                m_isSWBInterface = true;
                break;
            }else if(uri.equals(SemanticVocabulary.SWB_FORMELEMENT))
            {
                m_isSWBFormElement = true;
                break;
            }else if(uri.equals(SemanticVocabulary.SWB_SEMANTICRESOURCE))
            {
                m_isSWBSemanticResource = true;
                break;
            }
        }
    }

    /**
     * Checks if is sWB class.
     * 
     * @return true, if is sWB class
     */
    public boolean isSWBClass()
    {
        if(m_isSWBClass==null)
        {
            checkType();
        }
        return m_isSWBClass.booleanValue();
    }

    /**
     * Checks if is sWB class.
     *
     * @return true, if is sWB class
     */
    public boolean isSWBVirtualClass()
    {
        if(m_isSWBVirtClass==null)
        {
            checkType();
        }
        return m_isSWBVirtClass.booleanValue();
    }

    /**
     * Checks if is sWB interface.
     * 
     * @return true, if is sWB interface
     */
    public boolean isSWBInterface()
    {
        if(m_isSWBInterface==null)
        {
            checkType();
        }
        return m_isSWBInterface.booleanValue();
    }

    /**
     * Checks if is sWB model.
     * 
     * @return true, if is sWB model
     */
    public boolean isSWBModel()
    {
        if(m_isSWBModel==null)
        {
            checkType();
        }
        return m_isSWBModel.booleanValue();
    }

    /**
     * Checks if is sWB form element.
     * 
     * @return true, if is sWB form element
     */
    public boolean isSWBFormElement()
    {
        if(m_isSWBFormElement==null)
        {
            checkType();
        }
        return m_isSWBFormElement.booleanValue();
    }

    /**
     * Checks if is sWB semantic resource.
     * 
     * @return true, if is sWB semantic resource
     */
    public boolean isSWBSemanticResource()
    {
        if(m_isSWBSemanticResource==null)
        {
            checkType();
        }
        return m_isSWBSemanticResource.booleanValue();
    }

    /**
     * Gets the display name property.
     * 
     * @return the display name property
     */
    public SemanticProperty getDisplayNameProperty() {
        return displayNameProperty;
    }

    /**
     * Checks for herarquical properties.
     * 
     * @return true, if successful
     */
    public boolean hasHerarquicalProperties()
    {
        return !herarquicalProps.isEmpty();
    }

    /**
     * List herarquical properties.
     * 
     * @return the iterator
     */
    public Iterator<SemanticProperty> listHerarquicalProperties()
    {
        return herarquicalProps.iterator();
    }

    /**
     * Checks for inverse herarquical properties.
     * 
     * @return true, if successful
     */
    public boolean hasInverseHerarquicalProperties()
    {
        return !inverseHerarquicalProps.isEmpty();
    }

    /**
     * List inverse herarquical properties.
     * 
     * @return the iterator
     */
    public Iterator<SemanticProperty> listInverseHerarquicalProperties()
    {
        return inverseHerarquicalProps.iterator();
    }

    /**
     * Adds the super class.
     * 
     * @param cls the cls
     */
    public void addSuperClass(SemanticClass cls)
    {
        SemanticObject obj=SWBPlatform.getSemanticMgr().getSchema().getSemanticObject(getURI());
        Resource res=obj.getRDFResource();
        res.addProperty(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.RDFS_SUBCLASSOF).getRDFProperty(), cls.getOntClass());
    }

    /**
     * Identifica si el tipo de clase es del algun tipo de SWBClass, SWBModel,
     * SWBFormElement, SWBInterface, SWBSemanticResource
     * excepto SWBVirtualClass (notClassCodeGeneration)
     * @return true, if is sWB
     */
    public boolean isSWB()
    {
        return isSWBClass() || isSWBModel() || isSWBFormElement() || isSWBInterface() || isSWBSemanticResource();
    }

    /**
     * Gets the root class.
     * 
     * @return the root class
     */
    public SemanticClass getRootClass()
    {
        SemanticClass ret=this;
        if(isSWBClass())
        {
            SemanticClass swbcls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(SemanticVocabulary.SWB_SWBCLASS);
            if(this==swbcls || this.isSubClass(swbcls, true))
            {
                ret=this;
            }else
            {
                Iterator<SemanticClass> it=listSuperClasses();
                while(it.hasNext())
                {
                    SemanticClass cls=it.next();
                    if(cls.isSubClass(swbcls,true))
                    {
                        ret=cls;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Regresa nivel de subclase o -1 si no es subclase.
     * 
     * @param cls the cls
     * @return the sub class level
     * @return
     */
    public int getSubClassLevel(SemanticClass cls)
    {
        return getSubClassLevel(cls,0);
    }

   
    /**
     * Gets the sub class level.
     * 
     * @param cls the cls
     * @param l the l
     * @return the sub class level
     */
    private int getSubClassLevel(SemanticClass cls, int l)
    {
        //System.out.println("ini:"+l+" root:"+this+" cls:"+cls);
        int r=-1;
        if(this==cls)return l;
        Iterator<SemanticClass> it=listSubClasses(true);
        while (it.hasNext())
        {
           SemanticClass c = it.next();
           r=c.getSubClassLevel(cls,l+1);
           if(r>-1)break;
        }
        //System.out.println("end:"+l+" r:"+r+" root:"+this+" cls:"+cls);
        return r;
    }

    /**
     * Register observer.
     *
     * @param obs the obs
     */
    public void registerObserver(SemanticObserver obs) {
        m_observers.add(obs);
    }

    /**
     * Removes the observer.
     *
     * @param obs the obs
     */
    public void removeObserver(SemanticObserver obs) {
        m_observers.remove(obs);
    }

    /**
     * Notify change.
     *
     * @param obj the obj
     * @param prop the prop
     * @param lang the lang
     * @param action the action
     */
    public void notifyChange(SemanticObject obj, Object prop, String lang, String action)
    {
        //log.trace("notifyChange: obj:" + obj + " prop:" + prop + " " + action);
        Iterator it = m_observers.iterator();
        while (it.hasNext()) {
            SemanticObserver obs = (SemanticObserver) it.next();
            try {
                obs.notify(obj, prop, lang, action);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

}