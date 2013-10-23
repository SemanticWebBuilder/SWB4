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
import com.hp.hpl.jena.rdf.model.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticVocabulary.
 * 
 * @author Jei
 */
public class SemanticVocabulary
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SemanticVocabulary.class);
    //RDF
    /** The Constant RDF_URI. */
    public static final String RDF_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    
    /** The Constant RDF_TYPE. */
    public static final String RDF_TYPE = RDF_URI + "type";
    
    /** The Constant RDF_PROPERTY. */
    public static final String RDF_PROPERTY = RDF_URI + "Property";
    
    /** The Constant RDF_XMLLITERAL. */
    public static final String RDF_XMLLITERAL = RDF_URI + "XMLLiteral";
    
    /** The Constant RDFS_URI. */
    public static final String RDFS_URI = "http://www.w3.org/2000/01/rdf-schema#";

    /** The Constant RDFS_RESOURCE. */
    public static final String RDFS_RESOURCE = RDFS_URI+"Resource";
    
    /** The Constant RDFS_LABEL. */
    public static final String RDFS_LABEL = RDFS_URI + "label";
    
    /** The Constant RDFS_COMMENT. */
    public static final String RDFS_COMMENT = RDFS_URI + "comment";
    
    /** The Constant RDFS_DOMAIN. */
    public static final String RDFS_DOMAIN = RDFS_URI + "domain";
    
    /** The Constant RDFS_RANGE. */
    public static final String RDFS_RANGE = RDFS_URI + "range";
    
    /** The Constant RDFS_SUBPROPERTYOF. */
    public static final String RDFS_SUBPROPERTYOF = RDFS_URI + "subPropertyOf";
    
    /** The Constant RDFS_SUBCLASSOF. */
    public static final String RDFS_SUBCLASSOF = RDFS_URI + "subClassOf";
    
    /** The Constant XMLS_URI. */
    public static final String XMLS_URI = "http://www.w3.org/2001/XMLSchema#";
    
    /** The Constant XMLS_DATETIME. */
    public static final String XMLS_DATETIME = XMLS_URI + "dateTime";
    
    /** The Constant XMLS_DATE. */
    public static final String XMLS_DATE = XMLS_URI + "date";
    
    /** The Constant XMLS_BOOLEAN. */
    public static final String XMLS_BOOLEAN = XMLS_URI + "boolean";
    
    /** The Constant XMLS_STRING. */
    public static final String XMLS_STRING = XMLS_URI + "string";
    
    /** The Constant XMLS_INT. */
    public static final String XMLS_INT = XMLS_URI + "int";

    /** The Constant XMLS_INTEGERS. */
    public static final String XMLS_INTEGER = XMLS_URI + "integer";
    
    /** The Constant XMLS_FLOAT. */
    public static final String XMLS_FLOAT = XMLS_URI + "float";
    
    /** The Constant XMLS_DOUBLE. */
    public static final String XMLS_DOUBLE = XMLS_URI + "double";
    
    /** The Constant XMLS_LONG. */
    public static final String XMLS_LONG = XMLS_URI + "long";
    
    /** The Constant XMLS_SHORT. */
    public static final String XMLS_SHORT = XMLS_URI + "short";
    
    /** The Constant XMLS_BYTE. */
    public static final String XMLS_BYTE = XMLS_URI + "byte";
    
    /** The Constant XMLS_BYTE. */
    public static final String XMLS_DECIMAL = XMLS_URI + "decimal";    
    
    /** The Constant XMLS_BASE64BINARY. */
    public static final String XMLS_BASE64BINARY = XMLS_URI + "base64Binary";
    
    /** The Constant OWL_URI. */
    public static final String OWL_URI = "http://www.w3.org/2002/07/owl#";
    
    /** The Constant OWL_CLASS. */
    public static final String OWL_CLASS = OWL_URI + "Class";
    
    /** The Constant OWL_DATATYPEPROPERTY. */
    public static final String OWL_DATATYPEPROPERTY = OWL_URI + "DatatypeProperty";
    
    /** The Constant OWL_OBJECTPROPERTY. */
    public static final String OWL_OBJECTPROPERTY = OWL_URI + "ObjectProperty";
    //General
    /** The Constant URI. */
    public static final String URI = "http://www.semanticwebbuilder.org/swb4/ontology#";

    /** The Constant PROCESS_URI. */
    public static final String PROCESS_URI = "http://www.semanticwebbuilder.org/swb4/process#";

    /** The Constant PROCESS_CLASS. */
    public static final String PROCESS_CLASS = PROCESS_URI + "ProcessClass";
    
    /** The Constant SWBXF_URI. */
    public static final String SWBXF_URI = "http://www.semanticwebbuilder.org/swb4/xforms/ontology#";
    
    /** The Constant SWB_SWBCLASS. */
    public static final String SWB_SWBCLASS = URI + "SWBClass";
    
    /** The Constant SWB_CLASS. */
    public static final String SWB_CLASS = URI + "Class";
    
    /** The Constant SWB_INTERFACE. */
    public static final String SWB_INTERFACE = URI + "Interface";
    
    /** The Constant SWB_MODEL. */
    public static final String SWB_MODEL = URI + "Model";
    
    /** The Constant SWB_FORMELEMENT. */
    public static final String SWB_FORMELEMENT = URI + "FormElement";

    /** The Constant SWB_SWBFORMELEMENT. */
    public static final String SWB_SWBFORMELEMENT = URI + "SWBFormElement";
    
    /** The Constant SWB_SEMANTICRESOURCE. */
    public static final String SWB_SEMANTICRESOURCE = URI + "SemanticResource";
    
    /** The Constant SWB_PROP_VALUE. */
    public static final String SWB_PROP_VALUE = URI + "value";
    
    /** The Constant SWB_PROP_HASCLASS. */
    public static final String SWB_PROP_HASCLASS = URI + "hasClass";
    
    /** The Constant SWB_PROP_LOCALEABLE. */
    public static final String SWB_PROP_LOCALEABLE = URI + "localeable";
    
    /** The Constant SWB_PROP_REQUIRED. */
    public static final String SWB_PROP_REQUIRED = URI + "required";
    
    /** The Constant SWB_PROP_DEFAULTVALUE. */
    public static final String SWB_PROP_DEFAULTVALUE = URI + "defaultValue";
    
    /** The Constant SWB_PROP_DISPLAYPROPERTY. */
    public static final String SWB_PROP_DISPLAYPROPERTY = URI + "displayProperty";
    
    /** The Constant SWB_PROP_DISPLAYOBJECT. */
    public static final String SWB_PROP_DISPLAYOBJECT = URI + "displayObject";
    //public static final String SWB_PROP_DISPLAYCLASS=URI+"displayClass";
    /** The Constant SWB_PROP_EXTERNALINVOCATION. */
    public static final String SWB_PROP_EXTERNALINVOCATION = URI + "externalInvocation";
    
    /** The Constant SWB_PROP_HERARQUICALRELATION. */
    public static final String SWB_PROP_HERARQUICALRELATION = URI + "herarquicalRelation";
    
    public static final String SWB_PROP_HERARQUICALRELATIONFILTERCLASS = URI + "herarquicalRelationFilterClass";
    
    /** The Constant SWB_PROP_REMOVEDEPENDENCY. */
    public static final String SWB_PROP_REMOVEDEPENDENCY = URI + "removeDependency";
    
    /** The Constant SWB_PROP_CLONEDEPENDENCY. */
    public static final String SWB_PROP_CLONEDEPENDENCY = URI + "cloneDependency";
    
    /** The Constant SWB_PROP_HASHERARQUICALNODE. */
    public static final String SWB_PROP_HASHERARQUICALNODE = URI + "hasHerarquicalNode";
//    public static final String SWB_PROP_DRAGSUPPORT=URI+"dragSupport";
    /** The Constant SWB_PROP_NOTOBSERVABLE. */
    public static final String SWB_PROP_NOTOBSERVABLE = URI + "notObservable";
    
    /** The Constant SWB_PROP_NOTCODEGENERATION. */
    public static final String SWB_PROP_NOTCODEGENERATION = URI + "notCodeGeneration";
    
    /** The Constant SWB_PROP_INHERITPROPERTY. */
    public static final String SWB_PROP_INHERITPROPERTY = URI + "inheritProperty";
//    public static final String SWB_PROP_PROPGROUP=URI+"propGroup";
//    public static final String SWB_PROP_PROPINDEX=URI+"propIndex";
    //public static final String SWB_ANNOT_CLASSNAME=URI+"className";
    /** The Constant SWB_ANNOT_PROPERTYCODENAME. */
    public static final String SWB_ANNOT_PROPERTYCODENAME = URI + "propertyCodeName";
    
    /** The Constant SWB_ANNOT_CLASSCODENAME. */
    public static final String SWB_ANNOT_CLASSCODENAME = URI + "classCodeName";
    
    /** The Constant SWB_ANNOT_CLASSCODEPACKAGE. */
    public static final String SWB_ANNOT_CLASSCODEPACKAGE = URI + "classCodePackage";
    
    /** The Constant SWB_ANNOT_AUTOGENID. */
    public static final String SWB_ANNOT_AUTOGENID = URI + "autogenId";
    
    /** The Constant SWB_ANNOT_CANUSEDASNAME. */
    public static final String SWB_ANNOT_CANUSEDASNAME = URI + "canUsedAsName";
    
    /** The Constant SWB_PROP_CLASSGROUPID. */
    public static final String SWB_PROP_CLASSGROUPID = URI + "classGroupId";

    /** The Constant SWB_PROP_DISABLECACHE. */
    public static final String SWB_PROP_DISABLECACHE = URI + "disableCache";

    /** The Constant SWB_PROP_NOTCLASSCODEGENERATION. */
    public static final String SWB_PROP_NOTCLASSCODEGENERATION = URI + "notClassCodeGeneration";


    /** The Constant SWB_PROP_PREFIX. */
    public static final String SWB_PROP_PREFIX = URI + "prefix";
    
    /** The Constant SWB_PROP_PACKAGE. */
    public static final String SWB_PROP_PACKAGE = URI + "package";
    
    /** The Constant SWB_ANNOT_FORMELEMENTRANGE. */
    public static final String SWB_ANNOT_FORMELEMENTRANGE = URI + "formElementRange";
    
    //public static final String SWB_NS="swbns";
    /** The classes. */
    public ConcurrentHashMap<String, SemanticClass> classes;
    
    /** The properties. */
    public ConcurrentHashMap<String, SemanticProperty> properties;
    
    /** The clsbyid. */
    public ConcurrentHashMap<String, SemanticClass> clsbyid;
    
    /** The clsbyname. */
    public ConcurrentHashMap<String, SemanticClass> clsbyname;
    
    /** The clsbyname. */
    public ConcurrentHashMap<String, SemanticClass> clsbyVirtualName;    

    /**
     * Instantiates a new semantic vocabulary.
     */
    public SemanticVocabulary()
    {
        classes = new ConcurrentHashMap();
        clsbyid = new ConcurrentHashMap();
        clsbyname = new ConcurrentHashMap();
        clsbyVirtualName = new ConcurrentHashMap();
        properties = new ConcurrentHashMap();
    }

    /**
     * Inits the.
     */
    public void init()
    {
        //SWBClass=getSemanticClass(URI+"SWBClass");
        //SWBInterface=getSemanticClass(URI+"SWBInterface");
        //filterProperties();
    }

    /**
     * Filter properties.
     * 
     */
    public void filterProperties()
    {
        //System.out.println("filterProperties");
        Iterator<SemanticClass> tpcit=listSemanticClasses();
        while(tpcit.hasNext())
        {
            SemanticClass tpc=tpcit.next();
            filterProperties(tpc);
        }
    }


    /**
     * Filter properties.
     * 
     * @param tpc the tpc
     */
    private void filterProperties(SemanticClass tpc)
    {
        //System.out.println("filterProperties");
        Iterator<SemanticProperty> tppit = tpc.listProperties();
        while (tppit.hasNext())
        {
            SemanticProperty tpp = tppit.next();
            //System.out.println("Prop:"+tpp+"\t"+tpp.getDomainClass()+"\t"+tpc+"\t"+tpc.isSubClass(tpp.getDomainClass()));
            if (tpp.getDomainClass() == null || (tpp.hasInverse() && !(tpc.equals(tpp.getDomainClass()) || tpc.isSubClass(tpp.getDomainClass()))))
            {
                //System.out.println("Remove:"+tpp.getName());
                tppit.remove();
                tpc.herarquicalProps.remove(tpp);
                tpc.inverseHerarquicalProps.remove(tpp);
            }
        }
    }

    /**
     * Adds the semantic class.
     * 
     * @param tpc the tpc
     */
    void addSemanticClass(SemanticClass tpc)
    {
        classes.put(tpc.getURI(), tpc);
        String clsid = tpc.getClassId();
        if (clsid != null)
        {
            clsbyid.put(clsid, tpc);
        }
        String clsname = tpc.getClassName();
        if (clsname != null)
        {
            clsbyname.put(clsname, tpc);
        }
        clsname = tpc.getVirtualClassName();
        if (clsname != null)
        {
            clsbyVirtualName.put(tpc.getVirtualClassName(), tpc);
        }    
        //System.out.println("addSemanticClass:"+clsname+" "+tpc.getURI());
    }

    /**
     * List semantic classes.
     * 
     * @return the iterator
     */
    public Iterator<SemanticClass> listSemanticClasses()
    {
        return new ArrayList(classes.values()).iterator();
    }

    /**
     * List semantic classes as semantic objects.
     * 
     * @return the iterator
     */
    public Iterator<SemanticClass> listSemanticClassesAsSemanticObjects()
    {
        ArrayList arr = new ArrayList();
        Iterator<SemanticClass> it = classes.values().iterator();
        while (it.hasNext())
        {
            arr.add(it.next().getSemanticObject());
        }
        return arr.iterator();
    }

    /**
     * Gets the semantic class.
     * 
     * @param uri the uri
     * @return the semantic class
     */
    public SemanticClass getSemanticClass(String uri)
    {
        if (uri == null)
        {
            return null;
        }
        SemanticClass cls = classes.get(uri);
        if (cls == null)
        {
            OntModel ont = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel();
            try
            {
                OntClass c = ont.getOntClass(uri);
                if (c != null)
                {
                    cls = new SemanticClass(c);
                    registerClass(cls);
                }
            }
            catch (Exception e)
            {
                log.warn(uri, e);
            }
        }
        //System.out.println("getSemanticClass:"+uri+" "+cls);
        //new Exception().printStackTrace();
        return cls;
    }

    /**
     * Gets the semantic class by id.
     * 
     * @param classId the class id
     * @return the semantic class by id
     */
    public SemanticClass getSemanticClassById(String classId)
    {
        if(classId==null)return null;
        return clsbyid.get(classId);
    }

    /**
     * Gets the semantic class by java name.
     * 
     * @param className the class name
     * @return the semantic class by java name
     */
    public SemanticClass getSemanticClassByJavaName(String className)
    {
        if(className==null)return null;
        return clsbyname.get(className);
    }
    
    /**
     * Gets the semantic class by java name.
     * 
     * @param className the class name
     * @return the semantic class by java name
     */
    public SemanticClass getSemanticClassByVirtualJavaName(String className)
    {
        if(className==null)return null;
        return clsbyVirtualName.get(className);
    }    

    /**
     * Adds the semantic property.
     * 
     * @param tpp the tpp
     */
    void addSemanticProperty(SemanticProperty tpp)
    {
        if (!properties.containsKey(tpp.getURI()))
        {
            properties.put(tpp.getURI(), tpp);
        }
    }

    /**
     * List semantic properties.
     * 
     * @return the iterator
     */
    public Iterator<SemanticProperty> listSemanticProperties()
    {
        return properties.values().iterator();
    }

    /**
     * Gets the semantic property by id.
     * 
     * @param propId the prop id
     * @return the semantic property by id
     */
    public SemanticProperty getSemanticPropertyById(String propId)
    {
        int i=propId.indexOf(":");
        if(i>0)
        {
            String pref=propId.substring(0,i);
            String name=propId.substring(i+1);
            //System.out.println(pref+" "+name);
            String uri=getNsPrefixMap().get(pref)+name;
            //System.out.println(uri);
            return getSemanticProperty(uri);
        }
        return null;
    }

    /**
     * Gets the semantic property.
     * 
     * @param property the property
     * @return the semantic property
     */
    public SemanticProperty getSemanticProperty(Property property)
    {
        SemanticProperty prop = properties.get(property.getURI());
        if (prop == null)
        {
            prop = new SemanticProperty(property);
            addSemanticProperty(prop);
        }
        return prop;
    }

    /**
     * Gets the ns prefix map.
     * 
     * @return the ns prefix map
     */
    public HashMap<String,String> getNsPrefixMap()
    {
        HashMap<String,String> getNamespaces=new HashMap<String, String>();
        for(String key : SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getNsPrefixMap().keySet())
        {
            String value=SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getNsPrefixMap().get(key);
            getNamespaces.put(key, value);
        }
        return getNamespaces;
    }

    /**
     * Gets the semantic property.
     * 
     * @param uri the uri
     * @return the semantic property
     */
    public SemanticProperty getSemanticProperty(String uri)
    {
        SemanticProperty prop = properties.get(uri);
        if (prop == null)
        {
            OntModel ont = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel();
            
            try
            {
                Property p = ont.getProperty(uri);
                if (p != null)
                {
                    prop = new SemanticProperty(p);
                    addSemanticProperty(prop);
                }
            }
            catch (Exception e)
            {
                log.warn(uri, e);
            }
        }
        return prop;
    }

    /**
     * Register class.
     * 
     * @param cls the cls
     */
    public void registerClass(SemanticClass cls)
    {
        registerClass(cls, true);
    }    
    
    /**
     * Register class.
     * 
     * @param cls the cls
     */
    public void registerClass(SemanticClass cls, boolean filterProps)
    {
        if(cls==null || cls.getURI()==null)return;
        if (!classes.containsKey(cls.getURI()))
        {
            log.trace("Registering SemanticClass:" + cls + " --> " + cls.getClassName());
            addSemanticClass(cls);
            Iterator<SemanticProperty> propit = cls.listProperties();
            while (propit.hasNext())
            {
                SemanticProperty prop = propit.next();
                //if(tpc.equals(tpp.getDomainClass()) || tpp.hasInverse())
                {
                    addSemanticProperty(prop);
                }
            }
            if(filterProps)filterProperties(cls);
        }
    }
}
