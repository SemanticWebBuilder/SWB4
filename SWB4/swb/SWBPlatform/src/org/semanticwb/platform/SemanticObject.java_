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

package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBResourceNotFound;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.GenericObject;
import org.w3c.dom.Document;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticObject.
 *
 * @author Javier Solis Gonzalez
 */
public class SemanticObject
{
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SemanticObject.class);

    /** The Constant ACT_CREATE. */
    public static final String ACT_CREATE="CREATE";

    /** The Constant ACT_REMOVE. */
    public static final String ACT_REMOVE="REMOVE";

    /** The Constant ACT_ADD. */
    public static final String ACT_ADD="ADD";

    /** The Constant ACT_SET. */
    public static final String ACT_SET="SET";

    /** The Constant ACT_CLONE. */
    public static final String ACT_CLONE="CLONE";

    /** The m_objs. */
    private static Map<String, SemanticObject>m_objs=new ConcurrentHashMap<String, SemanticObject>();

    /** The m_genobj. */
    private GenericObject m_genobj=null;

    /** The m_res. */
    private Resource m_res = null;

    /** The m_model. */
    private SemanticModel m_model = null;
    //Virtual properties
    /** The m_cls. */
    private SemanticClass m_cls = null;

    /** The m_virtual. */
    private boolean m_virtual = false;

    /** The m_virtprops. */
    private HashMap m_virtprops;

    /** The m_cacheprops. */
    private Map m_cacheprops;                           //Cache de propiedades

    /** The m_cachepropsrel. */
    private Map m_cachepropsrel;                        //Cache de objetos relacionados a la propiedad

    /** The NULL. */
    private static String NULL="__NULL__";

    /** The has cache. */
    private static boolean hasCache=true;

    /** The has property cache. */
    private static boolean hasPropertyCache=true;

    private static boolean hasPropertyCacheOnInit=false;

    //No cambian
    /** The ext get methods. */
    private static HashMap<String, Method> extGetMethods=new HashMap();

    /** The ext set methods. */
    private static HashMap<String, Method> extSetMethods=new HashMap();

    /** The lastaccess. */
    private long lastaccess=System.currentTimeMillis();

    private boolean isNew=false;

    /** The wrapper to primitive. */
    private static HashMap<Class,Class> wrapperToPrimitive = new HashMap();
	static {
		wrapperToPrimitive.put( Boolean.class, Boolean.TYPE );
		wrapperToPrimitive.put( Byte.class, Byte.TYPE );
		wrapperToPrimitive.put( Short.class, Short.TYPE );
		wrapperToPrimitive.put( Character.class, Character.TYPE );
		wrapperToPrimitive.put( Integer.class, Integer.TYPE );
		wrapperToPrimitive.put( Long.class, Long.TYPE );
		wrapperToPrimitive.put( Float.class, Float.TYPE );
		wrapperToPrimitive.put( Double.class, Double.TYPE );
	}

    /**
     * Instantiates a new semantic object.
     *
     * @param res the res
     */
    private SemanticObject(Resource res)
    {
        this(res, null, null);
    }

   /**
     * Instantiates a new semantic object.
     *
     * @param res the res
     */
    protected SemanticObject(Resource res, SemanticModel model, SemanticClass cls)
    {
        this(res, model, cls, false);
    }

    /**
     * Instantiates a new semantic object.
     *
     * @param res the res
     */
    protected SemanticObject(Resource res, SemanticModel model, SemanticClass cls, boolean isNew)
    {
        //System.out.println("SemanticObject:"+res+" "+model+" "+cls+" "+isNew);
        if(res==null)
        {
            throw new NullPointerException("Resource is Null...");
        }
        this.isNew=isNew;
        m_cacheprops=new ConcurrentHashMap(); //MAPS74 //Collections.synchronizedMap(new HashMap());
        m_cachepropsrel=new ConcurrentHashMap(); //MAPS74 //Collections.synchronizedMap(new HashMap());
        this.m_res = res;
        this.m_model=model;
        this.m_cls=cls;
        if(model==null)validateModel();
        //System.out.println("SemanticObject:"+res);
    }

    /**
     * Contruye un SemanticObject virtual.
     *
     */
    public SemanticObject()
    {
        this((SemanticModel)null, (SemanticClass)null);
    }

    /**
     * Regresa instancia del SemanticObject si existe en Cache, de lo contrario
     * regresa null.
     *
     * @param uri the uri
     * @return the semantic object
     * @return
     */
    public static SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=null;
        if(hasCache && null!=uri)ret=m_objs.get(uri);
        return ret;
    }

    /**
     * Regresa tiempo en milisegundos de la ultima consulta del objeto.
     *
     * @return the last access
     * @return
     */
    public long getLastAccess()
    {
        return lastaccess;
    }

    /**
     * Regresa una instancia del GenericObject asociado
     * Si ya existe una instancia la regresa, de lo contrario la crea.
     *
     * @return the generic object
     * @return
     */
    public GenericObject createGenericInstance()
    {
        GenericObject gen=getGenericInstance();
        if(gen==null)
        {
            synchronized(this)
            {
                gen=getGenericInstance();
                if(gen==null)
                {
                    gen=createNewGenericIntance();
                    if(gen!=null)setGenericInstance(gen);
                }
            }
        }
        return gen;
    }

    /**
     * Crea una nueva instancia del GenericObject asociado
     * @return
     */
    public GenericObject createNewGenericIntance()
    {
        GenericObject gen=null;
        SemanticClass clazz=getSemanticClass();
        if(clazz==null)
        {
            log.error("SemanticObject("+this+") without SemanticClass...");
        }else
        {
            if(clazz.isSWBInterface())
            {
                Iterator<SemanticClass> classes=listSemanticClasses();
                while(classes.hasNext())
                {
                    SemanticClass tempClazz=classes.next();
                    if(tempClazz.isSWBClass())
                    {
                        clazz=tempClazz;
                        break;
                    }
                }
            }
            gen=clazz.construcGenericInstance(this);
        }
        return gen;
    }

    /**
     * Regresa una instancia del GenericObject asociado
     * Si ya existe una instancia la regresa, de lo contrario regresa null.
     *
     * @return the generic instance
     * @return
     */
    public GenericObject getGenericInstance()
    {
        lastaccess=System.currentTimeMillis();
        return m_genobj;
    }

    /**
     * Asigna una instancia GenericObject del objeto.
     *
     * @param gen the new generic instance
     * @return
     */
    private void setGenericInstance(GenericObject gen)
    {
        m_genobj=gen;
    }

/**
 * Regrea una instancia del SemanticObject en base al URI dado.
 * Si el recurso no existe regresa null
 *
 * @param uri the uri
 * @return the semantic object
 * @return
 */
    public static SemanticObject createSemanticObject(String uri)
    {
        SemanticObject ret=getSemanticObject(uri);
        if(ret==null)
        {
            synchronized(SemanticObject.class)
            {
                ret=getSemanticObject(uri);
                if(ret==null)
                {
                    Resource res=SWBPlatform.getSemanticMgr().getOntology().getResource(uri);
                    if(res!=null)
                    {
                        ret=new SemanticObject(res);
                        m_objs.put(uri, ret);
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Creates the semantic object.
     *
     * @param res the res
     * @return the semantic object
     */
    public static SemanticObject createSemanticObject(Resource res)
    {
        return createSemanticObject(res, null, null);
    }

    /**
     * Creates the semantic object.
     *
     * @param res the res
     * @return the semantic object
     */
    public static SemanticObject createSemanticObject(Resource res, SemanticModel model, SemanticClass cls)
    {
        return createSemanticObject(res, model, cls, false);
    }

    /**
     * Creates the semantic object.
     *
     * @param res the res
     * @return the semantic object
     */
    public static SemanticObject createSemanticObject(Resource res, SemanticModel model, SemanticClass cls, boolean isNew)
    {
        String id=res.getURI();
        if(id==null)id=res.getId().toString();
        SemanticObject ret=getSemanticObject(id);
        if(ret==null)
        {
            synchronized(SemanticObject.class)
            {
                ret=getSemanticObject(id);
                if(ret==null)
                {
                    //System.out.println("res1:"+res+" "+id);
        //            //if(hasCache)
        //            {
        //                if(res.getURI()!=null && (res.getModel()==SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel() || res.getModel()==SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel()))
        //                {
        //                    res=SWBPlatform.getSemanticMgr().getOntology().getResource(res.getURI());
        //                }
        //            }
        //            //System.out.println("res2:"+res+" "+id);
                    ret=new SemanticObject(res,model,cls,isNew);
                    m_objs.put(id, ret);
                }
            }
        }
        return ret;
    }

    /**
     * Elimina cache de propiedades del objeto.
     */
    public void resetCache()
    {
        if(!m_virtual)
        {
            m_cls = null;
            m_cacheprops=new ConcurrentHashMap(); //MAPS74
            m_cachepropsrel=new ConcurrentHashMap(); //MAPS74
        }
    }

    /**
     * Elimina el SemanticObject del cache
     * param uri del SemanticObject a eliminar del cache.
     *
     * @param uri the uri
     */
    public static void removeCache(String uri)
    {
        //System.out.println("removeCache:"+uri);
        m_objs.remove(uri);
    }


    /**
     * Elimina todos los SemanticObject del cache.
     */
    public static void clearCache()
    {
        m_objs.clear();
    }

    /**
     * Regresa numero de elementos en cache.
     *
     * @return the cache size
     */
    public static int getCacheSize()
    {
        return m_objs.size();
    }


    /**
     * Sets the property value cache.
     *
     * @param prop the prop
     * @param lang the lang
     * @param value the value
     */
    private void setPropertyValueCache(SemanticProperty prop, String lang, Object value)
    {
        //System.out.println("set:"+this+" "+prop+" "+value);
        if(value==null)value=NULL;
        if(value!=null)
        {
            m_cacheprops.put(prop.getURI()+"|"+lang, value);
            m_cachepropsrel.remove(prop.getURI());
        }
    }

    /**
     * Adds the property value cache.
     *
     * @param prop the prop
     * @param lang the lang
     * @param value the value
     */
    private void addPropertyValueCache(SemanticProperty prop, String lang, Object value)
    {
        //System.out.println("add:"+this+" "+prop+" "+value);
        removePropertyValueCache(prop, lang);
        if(prop.isInverseOf() && value instanceof SemanticObject)
        {
            ((SemanticObject)value).removePropertyValueCache(prop.getInverse(), lang);
        }
    }

    /**
     * Gets the property value cache.
     *
     * @param prop the prop
     * @param lang the lang
     * @return the property value cache
     */
    public Object getPropertyValueCache(SemanticProperty prop, String lang)
    {
        //System.out.println("get:"+this+" "+prop);
        Object ret=null;
        if(hasPropertyCache)
        {
            ret=m_cacheprops.get(prop.getURI()+"|"+lang);
        }
        return ret;
    }

    /**
     * Removes the property value cache.
     *
     * @param prop the prop
     * @param lang the lang
     */
    public void removePropertyValueCache(SemanticProperty prop, String lang)
    {
        //System.out.println("remove:"+this+" "+prop);
        //System.out.println("removePropertyValueCache:"+this+" "+prop+" "+lang);
        if(prop.isInverseOf())
        {
            Object aux=m_cacheprops.get(prop.getURI()+"|"+lang);
            //System.out.println("removePropertyValueCache2:"+aux);
            if(aux!=null && aux!=NULL)
            {
                SemanticProperty inv=prop.getInverse();
                //System.out.println("removePropertyValueCache3:"+aux+" "+inv+" "+inv.getCardinality());
                if(aux instanceof ArrayList)
                {
                    Iterator it=new SemanticIterator(((ArrayList)aux).iterator());
                    while(it.hasNext())
                    {
                        SemanticObject obj=(SemanticObject)it.next();
//                        if(inv.getCardinality()==1)
                        {
                            obj.m_cacheprops.remove(inv.getURI()+"|"+null);
//                        }else
//                        {
                            obj.m_cacheprops.remove(inv.getURI()+"|"+"list");
                        }
                    }
                }else
                {
                    SemanticObject obj=(SemanticObject)aux;
//                    if(inv.getCardinality()==1)
                    {
                        obj.m_cacheprops.remove(inv.getURI()+"|"+null);
//                    }else
//                    {
                        obj.m_cacheprops.remove(inv.getURI()+"|"+"list");
                    }
                }
            }
        }
        m_cacheprops.remove(prop.getURI()+"|"+lang);
        m_cachepropsrel.remove(prop.getURI());
    }

    /**
     * Gets the list object property cache.
     *
     * @param prop the prop
     * @return the list object property cache
     */
    private Iterator<SemanticObject> getListObjectPropertyCache(SemanticProperty prop)
    {
        //System.out.println("getList:"+this+" "+prop);
        Iterator it=null;
        ArrayList arr=(ArrayList)m_cacheprops.get(prop.getURI()+"|list");
        if(arr!=null)it=new SemanticIterator(arr.iterator());
        return it;
    }

    /**
     * Checks for object property cache.
     *
     * @param prop the prop
     * @param obj the obj
     * @return the boolean
     */
    private Boolean hasObjectPropertyCache(SemanticProperty prop, SemanticObject obj)
    {
        //System.out.println("hasObject:"+this+" "+prop+" "+obj);
        if(obj==null)return false;
        Boolean ret=null;
        ArrayList arr=(ArrayList)m_cacheprops.get(prop.getURI()+"|list");
        if(arr!=null)
        {
//            Iterator it=arr.iterator();
//            while(it.hasNext())
//            {
//                System.out.println("Ite:"+it.next());
//            }
            ret=arr.contains(obj.getURI());
        }
//        System.out.println("hasObjectPropertyCache:"+this+" prop:"+prop+" obj:"+obj+" "+ret);
        return ret;
    }

    /**
     * Checks for object property cache.
     *
     * @param prop the prop
     * @return the boolean
     */
    private Boolean hasObjectPropertyCache(SemanticProperty prop)
    {
        //System.out.println("hasObject:"+this+" "+prop);
        Boolean ret=null;
        ArrayList arr=(ArrayList)m_cacheprops.get(prop.getURI()+"|list");
        if(arr!=null)
        {
//            Iterator it=arr.iterator();
//            while(it.hasNext())
//            {
//                System.out.println("Ite:"+it.next());
//            }
            ret=!arr.isEmpty();
        }
        //System.out.println("hasObjectPropertyCache:"+this+" prop:"+prop+" "+ret);
        return ret;
    }



    /**
     * Sets the list object property cache.
     *
     * @param prop the prop
     * @param list the list
     * @return the iterator
     */
    private Iterator<SemanticObject> setListObjectPropertyCache(SemanticProperty prop, Iterator<SemanticObject> list)
    {
        //System.out.println("setListObjectPropertyCache:"+this+" "+prop);
        ArrayList arr=new ArrayList();
        while(list.hasNext())
        {
            SemanticObject obj=list.next();
            arr.add(obj.getURI());
            //System.out.println("-->add:"+obj);
        }
        m_cacheprops.put(prop.getURI()+"|list", arr);
        return new SemanticIterator(arr.iterator());
    }

    /**
     * Validate model.
     */
    private void validateModel()
    {
        String ns = getModel().getNameSpace();
        if (ns != null &&  m_res.getURI() !=null && !m_res.getURI().startsWith(ns) && !m_res.getURI().equals(SemanticVocabulary.RDFS_RESOURCE))
        {
            //System.out.println("ns:"+ns+" "+m_res.getURI());
            Resource aux=SWBPlatform.getSemanticMgr().getOntology().getResource(m_res.getURI());
            if(aux==null)throw new SWBResourceNotFound("Resource not Found:"+m_res.getURI());
            m_res = aux;
            m_model = null;
        }
    }

    /**
     * Checks if is virtual.
     *
     * @return true, if is virtual
     */
    public boolean isVirtual()
    {
        return m_virtual;
    }

    /**
     * Sets the rDF resource.
     *
     * @param res the new rDF resource
     */
    public void setRDFResource(Resource res)
    {
        this.m_model = null;
        this.m_virtual = false;
        this.m_res = res;
        validateModel();
    }

    /**
     * Contruye un SemanticObject virtual relacionado al Model y al tipo de elemento.
     *
     * @param model the model
     * @param cls the cls
     */
    public SemanticObject(SemanticModel model, SemanticClass cls)
    {
        m_model = model;
        m_cls = cls;
        m_virtual = true;
        m_virtprops = new HashMap();
        m_cacheprops=new ConcurrentHashMap(); //MAPS74 //new HashMap();
        m_cachepropsrel=new ConcurrentHashMap(); //MAPS74 //new HashMap();
    }

    /**
     * Gets the uRI.
     *
     * @return the uRI
     */
    public String getURI()
    {
        if (m_virtual)
        {
            return null;
        }
        return m_res.getURI();
    }

    public static String shortToFullURI(String shorturi)
    {
        int pos=shorturi.indexOf("#");
        if(pos!=-1)
        {
            throw new IllegalArgumentException();
        }
        pos=shorturi.indexOf(":");
        if(pos!=-1)
        {
            String idmodel=shorturi.substring(0,pos);
            SemanticModel model=SWBPlatform.getSemanticMgr().getModel(idmodel);
            if(model!=null)
            {
                return model.getNameSpace()+shorturi.substring(pos+1);
            }
            throw new IllegalArgumentException("The model was not found "+idmodel);
        }
        else
        {
            throw new IllegalArgumentException("The separator ':' was not found in shorturi "+shorturi);
        }
    }

    public String getShortURI()
    {
        String getShortURI=getModel().getModelObject().getId();
        if(getURI()==null)
        {
            throw new IllegalArgumentException();
        }
        int pos=getURI().indexOf("#");
        if(pos!=-1)
        {
            getShortURI=getShortURI+":"+getURI().substring(pos+1);
        }
        else
        {
            throw new IllegalArgumentException();
        }
        return getShortURI;
    }
    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId()
    {
        String id=null;
        if (m_virtual)
        {
            return null;
        }
        id = getURI();
        if(id!=null)
        {
            int x = id.indexOf('#');
            if (x > -1)
            {
                id=id.substring(x + 1);
                x = id.indexOf(':');
                if (x > -1)
                {
                    id=id.substring(x + 1);
                }
            }
        }
        return id;
    }

    /**
     * Gets the prefix.
     *
     * @return the prefix
     */
    public String getPrefix()
    {
        if (m_virtual)
        {
            return null;
        }
        return m_res.getModel().getNsURIPrefix(m_res.getNameSpace());
    }

    /**
     * Gets the res id.
     *
     * @return the res id
     */
    public String getResId()
    {
        if (m_virtual)
        {
            return null;
        }
        String ret=null;
        String pref=getPrefix();
        if(pref!=null)
        {
            ret=pref+":"+m_res.getLocalName();
        }else
        {
            ret=m_res.getLocalName();
        }
        if(ret==null || ret.length()==0)ret=getId();
        if(ret==null)ret=m_res.toString();
        return ret;
    }

//    /**
//     * Regreasa SemanticID compuesto por /[model name]/[cls name]/[id]
//     * @return
//     */
//    public String getSID()
//    {
//        //return "/" + getModel().getName() + "/" + getSemanticClass().getClassId() + ":" + getId();
//        return "/" + getModel().getName() + "/" + getRDFName();
//    }

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
     * Gets the rDF name.
     *
     * @return the rDF name
     */
    public String getRDFName()
    {
        if (m_virtual)
        {
            return null;
        }
        return m_res.getLocalName();
    }

    /**
     * Gets the semantic class.
     *
     * @return the semantic class
     */
    public SemanticClass getSemanticClass()
    {
        //System.out.print("getSemanticClass:"+getURI());
        if (m_cls == null)
        {
            //TODO: Cuando se crea instancia con modelo y clase no entra a este statement
            //Validar si se requiere carga inicial de propiedades
            if(hasPropertyCacheOnInit)
            {
                //System.out.print("Loading Init Properties:"+getURI());
                resetCache();
                boolean classfound=false;
                StmtIterator it=m_res.listProperties();
                while (it.hasNext())
                {
                    Statement st = it.next();
                    //System.out.println(st);
                    SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(st.getPredicate());
                    if(prop.getURI().equals(SemanticVocabulary.RDF_TYPE))
                    {
                        if(!classfound)
                        {
                            m_cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(st.getResource().getURI());
                            if(m_cls!=null && m_cls.isSWBClass())
                            {
                                classfound=true;
                                //System.out.println("ClassFound");
                            }
                            //System.out.println("Class:"+m_cls);
                        }
                    }else
                    {
                        if(st.getObject().isLiteral())
                        {
                            SemanticLiteral l=new SemanticLiteral((st.getObject().asLiteral()));
                            //System.out.println("setLiteral:"+l);
                            if(prop.getCardinality()==1)
                            {
                                setPropertyValueCache(prop, l.getLanguage(), l);
                            }else
                            {
                                addPropertyValueCache(prop, l.getLanguage(), l);
                            }
                        }else if(st.getObject().isResource())
                        {
                            SemanticObject obj=SemanticObject.createSemanticObject(st.getObject().asResource().getURI());
                            //System.out.println("setObj:"+obj);
                            if(prop.getCardinality()==1)
                            {
                                setPropertyValueCache(prop, null, obj);
                            }else
                            {
                                addPropertyValueCache(prop, "list", obj);
                            }
                        }
                    }
                }
                it.close();
            }else
            {
                StmtIterator it=m_res.listProperties(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty());
                while(it.hasNext())
                {
                    Statement stm=it.next();
                    //Statement stm = m_res.getProperty(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty());
                    if(stm!=null)
                    {
                        m_cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
                        if(m_cls!=null && m_cls.isSWBClass())
                        {
                            break;
                        }
                    }
                }
                it.close();
            }
        }
        //System.out.println(" m_cls:"+m_cls);
        return m_cls;
    }

    /**
     * List semantic classes.
     *
     * @return the iterator
     */
    public Iterator<SemanticClass> listSemanticClasses()
    {
        if (m_virtual)
        {
            //TODO:
            return null;//m_virtclass;
        }
        Iterator it=new SemanticClassIterator<SemanticClass>(m_res.listProperties(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty()));
        return SWBUtils.Collections.copyIterator(it).iterator();
    }

    /**
     * Adds the semantic class.
     *
     * @param cls the cls
     */
    public void addSemanticClass(SemanticClass cls)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, cls, null, ACT_ADD);
        if (m_virtual)
        {
            //TODO:
        }
        else
        {
            m_res.addProperty(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty(), cls.getOntClass());
        }
    }

    /**
     * Removes the semantic class.
     *
     * @param cls the cls
     * @return the semantic object
     */
    public SemanticObject removeSemanticClass(SemanticClass cls)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, cls, null, ACT_REMOVE);
        if (m_virtual)
        {
            //TODO:
        }
        else if (m_res != null)
        {
            StmtIterator stit = m_res.listProperties(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty());
            while (stit.hasNext())
            {
                Statement staux = stit.nextStatement();
                if (staux.getResource().getURI().equals(cls.getURI()))
                {
                    stit.remove();
                }
            }
            stit.close();
        }
        return this;
    }

    /**
     * Regresa el Modelo de del SemanticObject.
     *
     * @return the model
     * @return
     */
    public SemanticModel getModel()
    {
        if (m_model == null)
        {
            m_model = SWBPlatform.getSemanticMgr().getModel(m_res.getModel());
        }
        return m_model;
    }

    /**
     * Gets the rDF resource.
     *
     * @return the rDF resource
     */
    public Resource getRDFResource()
    {
        if (m_virtual)
        {
            return null;
        }
        return m_res;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        if (m_virtual)
        {
            return super.toString();
        }
        return m_res.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        if (m_virtual)
        {
            return super.hashCode();
        }
        return m_res.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        return hashCode() == obj.hashCode();
    }

    /**
     * Instance of.
     *
     * @param cls the cls
     * @return true, if successful
     */
    public boolean instanceOf(SemanticClass cls)
    {
        boolean ret = false;
        SemanticClass cl = getSemanticClass();
        if (cl != null && (cl.equals(cls) || cl.isSubClass(cls)))
        {
            ret = true;
        }
        return ret;
    }


//***********************************************************************************************************************/

    /**
 * Gets the dom property.
 *
 * @param prop the prop
 * @return the dom property
 */
public Document getDomProperty(SemanticProperty prop)
    {
        Document dom=(Document)m_cachepropsrel.get(prop.getURI());
        if(dom==null)
        {
            String xml=getProperty(prop);
            if(xml!=null)
            {
                dom=SWBUtils.XML.xmlToDom(xml);
            }
            if(dom==null)
            {
                dom=SWBUtils.XML.getNewDocument();
            }
            m_cachepropsrel.put(prop.getURI(), dom);
        }
        return dom;
    }


    /**
     * Gets the literal property.
     *
     * @param prop the prop
     * @return the literal property
     */
    public SemanticLiteral getLiteralProperty(SemanticProperty prop)
    {
        SemanticLiteral ret = null;
        if (m_virtual)
        {
            ret=(SemanticLiteral)getPropertyValueCache(prop, null);
        }else
        {
            Object aux=getPropertyValueCache(prop, null);
            if(aux==NULL)return null;
            ret=(SemanticLiteral)aux;
            if(ret==null)
            {
                Statement stm = null;
                stm = m_res.getProperty(prop.getRDFProperty());
                if (stm != null)
                {
                    ret = new SemanticLiteral(stm);
                }
                setPropertyValueCache(prop, null, ret);
            }
        }
        return ret;
    }

    /**
     * Gets the literal property.
     *
     * @param prop the prop
     * @param lang the lang
     * @return the literal property
     */
    public SemanticLiteral getLiteralProperty(SemanticProperty prop, String lang)
    {
        SemanticLiteral ret = null;
        if (m_virtual)
        {
            ret=(SemanticLiteral)getPropertyValueCache(prop, lang);
        }else
        {
            Object aux=getPropertyValueCache(prop, lang);
            if(aux==NULL)return null;
            ret=(SemanticLiteral)aux;
            if(ret==null)
            {
                Statement stm = null;
                stm=getLocaleStatement(prop,lang);
                if (stm != null)
                {
                    ret = new SemanticLiteral(stm);
                }
                setPropertyValueCache(prop, lang, ret);
            }
        }
        return ret;
    }

    /**
     * Adds the literal property.
     *
     * @param prop the prop
     * @param literal the literal
     */
    public void addLiteralProperty(SemanticProperty prop, SemanticLiteral literal)
    {
        setLiteralProperty(prop, literal, false);
    }

    /**
     * Sets the literal property.
     *
     * @param prop the prop
     * @param literal the literal
     */
    public void setLiteralProperty(SemanticProperty prop, SemanticLiteral literal)
    {
        setLiteralProperty(prop, literal, !isNew);
    }

    /**
     * Sets the literal property.
     *
     * @param prop the prop
     * @param literal the literal
     * @param replace the replace
     */
    protected void setLiteralProperty(SemanticProperty prop, SemanticLiteral literal, boolean replace)
    {
        //System.out.println(prop+" "+literal.getValue());
        //Thread.currentThread().dumpStack();
        if(!m_virtual)
        {
            Object obj=literal.getValue();
            String lang=literal.getLanguage();
            Statement stm = null;
//            if(lang!=null)
//            {
            if(replace)
            {
                if(!prop.isLocaleable())
                {
                    //System.out.println("borrar:"+prop.getRDFProperty());
                    m_res.removeAll(prop.getRDFProperty());
                }else
                {
                    ArrayList arr=null;
                    if(SWBPlatform.isTDB())
                    {
                        arr=new ArrayList();
                    }
                    StmtIterator stit = m_res.listProperties(prop.getRDFProperty());
                    while (stit.hasNext())
                    {
                        Statement staux = stit.nextStatement();
                        String lg = staux.getLanguage();
                        if(lg!=null && lg.length()==0)lg=null;
                        if ((lang==null && lg==null) || (lg != null && lg.equals(lang)))
                        {
                            if(SWBPlatform.isTDB())
                            {
                                arr.add(staux);
                            }else
                            {
                                stit.remove();
                            }
                        }
                    }
                    stit.close();
                    if(arr!=null)
                    {
                        Iterator<Statement> it=arr.iterator();
                        while (it.hasNext()) {
                            Statement statement = it.next();
                            statement.remove();
                        }
                    }
                    //stm=getLocaleStatement(prop,lang);
                }
            }
//            if(obj==null)
//            {
//                if(stm!=null)
//                {
//                    stm.remove();
//                }
//            }else if (stm != null)
//            {
//                if(obj instanceof String)
//                {
//                    if(lang!=null)
//                    {
//                        stm.changeObject((String)obj,lang);
//                    }else
//                    {
//                        stm.changeObject((String)obj);
//                    }
//                }else if(obj instanceof Boolean)
//                {
//                    stm.changeLiteralObject((Boolean)obj);
//                }else if(obj instanceof Character)
//                {
//                    stm.changeLiteralObject((Character)obj);
//                }else if(obj instanceof Double)
//                {
//                    stm.changeLiteralObject((Double)obj);
//                }else if(obj instanceof Float)
//                {
//                    stm.changeLiteralObject((Float)obj);
//                }else if(obj instanceof Integer)
//                {
//                    stm.changeLiteralObject((Integer)obj);
//                }else if(obj instanceof Long)
//                {
//                    stm.changeLiteralObject((Long)obj);
//                }else if(obj instanceof java.util.Date)
//                {
//                    stm.changeObject(SWBUtils.TEXT.iso8601DateFormat((java.util.Date)obj));
//                }
//            }
//            else
            {
                if(obj instanceof String)
                {
                    if(lang!=null)
                    {
                        m_res.addProperty(prop.getRDFProperty(), (String)obj,literal.getLanguage());
                    }else
                    {
                        m_res.addProperty(prop.getRDFProperty(), (String)obj);
                    }
                }else if(obj instanceof Boolean)
                {
                    m_res.addLiteral(prop.getRDFProperty(), (Boolean)obj);
                }else if(obj instanceof Character)
                {
                    m_res.addLiteral(prop.getRDFProperty(), (Character)obj);
                }else if(obj instanceof Double)
                {
                    m_res.addLiteral(prop.getRDFProperty(), (Double)obj);
                }else if(obj instanceof Float)
                {
                    m_res.addLiteral(prop.getRDFProperty(), (Float)obj);
                }else if(obj instanceof Integer)
                {
                    m_res.addLiteral(prop.getRDFProperty(), (Integer)obj);
                }else if(obj instanceof Long)
                {
                    m_res.addLiteral(prop.getRDFProperty(), (Long)obj);
                }else if(obj instanceof java.util.Date)
                {
                    m_res.addProperty(prop.getRDFProperty(), SWBUtils.TEXT.iso8601DateFormat((java.util.Date)obj));
                }
            }
        }
        if(literal.getValue()==null)
        {
            if(!isNew)removePropertyValueCache(prop, literal.getLanguage());
        }else if(replace)
        {
            setPropertyValueCache(prop, literal.getLanguage(), literal);
            SWBPlatform.getSemanticMgr().notifyChange(this, prop, literal.getLanguage(), ACT_SET);
        }else
        {
            addPropertyValueCache(prop, literal.getLanguage(), literal);
            SWBPlatform.getSemanticMgr().notifyChange(this, prop, literal.getLanguage(), ACT_ADD);
        }
    }

    /**
     * Removes the property.
     *
     * @param prop the prop
     * @return the semantic object
     */
    public SemanticObject removeProperty(SemanticProperty prop)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, null, ACT_REMOVE);
        if (!m_virtual)
        {
            try
            {
                if (prop.isBinary())
                {
                    String value = getProperty(prop);
                    if (value != null)
                    {
                        String workPath = this.getWorkPath();
                        if (!(workPath.endsWith("\\") || workPath.equals("/")))
                        {
                            workPath += "/" + value;
                        }
                        SWBPlatform.createInstance().removeFileFromPlatformWorkPath(workPath);
                    }
                }
            }catch(Exception e){log.error(e);}

            Property iprop = prop.getRDFProperty();
            m_res.removeAll(iprop);
        }
        removePropertyValueCache(prop, null);
        removePropertyValueCache(prop, "list");
        return this;
    }

    /**
     * Removes the property.
     *
     * @param prop the prop
     * @param lang the lang
     * @return the semantic object
     */
    public SemanticObject removeProperty(SemanticProperty prop, String lang)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, lang, ACT_REMOVE);
        if (!m_virtual)
        {
            StmtIterator stit = m_res.listProperties(prop.getRDFProperty());
            while (stit.hasNext())
            {
                Statement staux = stit.nextStatement();
                String lg = staux.getLanguage();
                if (lg != null && lg.equals(lang))
                {
                    stit.remove();
                }
            }
            stit.close();
        }
        removePropertyValueCache(prop, lang);
        return this;
    }

    /**
     * Sets the object property.
     *
     * @param prop the prop
     * @param object the object
     * @return the semantic object
     */
    public SemanticObject setObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        //System.out.println("setObjectProperty:"+prop+" "+object);
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list == null)
            {
                list = new ArrayList();
                m_virtprops.put(prop.getURI(), list);
            }
            list.clear();
            list.add(object);
            return this;
        }
        Object old=getPropertyValueCache(prop, null);
        Property iprop = prop.getRDFProperty();
        Statement stm = m_res.getProperty(iprop);
        //System.out.println("stm:"+stm);
        if (stm != null)
        {
            //System.out.println("object:"+object.getRDFResource());
            stm.changeObject(object.getRDFResource());
        }
        else
        {
            m_res.addProperty(iprop, object.getRDFResource());
        }
        removePropertyValueCache(prop, null);
        setPropertyValueCache(prop, null, object);
        if(prop.isInverseOf())
        {
            SemanticProperty inv=prop.getInverse();
            if(inv.getCardinality()==1)
            {
                object.removePropertyValueCache(prop.getInverse(), NULL);
                if(old!=null && old instanceof SemanticObject)((SemanticObject)old).removePropertyValueCache(prop.getInverse(), NULL);
            }else
            {
                object.removePropertyValueCache(prop.getInverse(), "list");
                if(old!=null && old instanceof SemanticObject)((SemanticObject)old).removePropertyValueCache(prop.getInverse(), "list");
            }
        }
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, null, ACT_SET);
        return this;
    }


     /**
     * Adds the object property.
     *
     * @param prop the prop
     * @param object the object
     * @return the semantic object
     */
    public SemanticObject addObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        return addObjectProperty(prop, object, true);
    }



    /**
     * Adds the object property.
     *
     * @param prop the prop
     * @param object the object
     * @return the semantic object
     */
    public SemanticObject addObjectProperty(SemanticProperty prop, SemanticObject object, boolean notify)
    {
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list == null)
            {
                list = new ArrayList();
                m_virtprops.put(prop.getURI(), list);
            }
            list.add(object);
            return this;
        }
        Property iprop = prop.getRDFProperty();
        m_res.addProperty(iprop, object.getRDFResource());
        addPropertyValueCache(prop, "list",object);
        if(notify)SWBPlatform.getSemanticMgr().notifyChange(this, prop, "list", ACT_ADD);
        return this;
    }



    /**
     * Removes the object property.
     *
     * @param prop the prop
     * @param object the object
     * @return the semantic object
     */
    public SemanticObject removeObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        return removeObjectProperty(prop, object, true);
    }

     /**
     * Removes the object property.
     *
     * @param prop the prop
     * @param object the object
     * @return the semantic object
     */
    public SemanticObject removeObjectProperty(SemanticProperty prop, SemanticObject object, boolean notify)
    {
        if(notify) SWBPlatform.getSemanticMgr().notifyChange(this, prop, "list", ACT_REMOVE);
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list != null)
            {
                list.remove(object);
            }
            return this;
        }
        //listObjectProperties(prop);

        ArrayList arr=null;
        if(SWBPlatform.isTDB())
        {
            arr=new ArrayList();
        }

        StmtIterator it = m_res.listProperties(prop.getRDFProperty());
        while (it.hasNext())
        {
            Statement stmt = it.nextStatement();
            if (object.getRDFResource().equals(stmt.getResource()))
            {
                if(SWBPlatform.isTDB())
                {
                    arr.add(stmt);
                }else
                {
                    it.remove();
                }
            }
        }
        it.close();

        if(arr!=null)
        {
            Iterator<Statement> it2=arr.iterator();
            while (it2.hasNext()) {
                Statement statement = it2.next();
                statement.remove();
            }
        }

        removePropertyValueCache(prop, "list");
        if(prop.isInverseOf())
        {
            SemanticProperty inv=prop.getInverse();
            if(inv.getCardinality()==1)
            {
                object.removePropertyValueCache(prop.getInverse(), NULL);
            }else
            {
                object.removePropertyValueCache(prop.getInverse(), "list");
            }
        }                
        
        return this;
    }







    /**
     * List literal properties.
     *
     * @param prop the prop
     * @return the iterator
     */
    public Iterator<SemanticLiteral> listLiteralProperties(SemanticProperty prop)
    {
        if (m_virtual)
        {
            //TODO:
            return new ArrayList().iterator();
        }
        return new SemanticLiteralIterator(m_res.listProperties(prop.getRDFProperty()));
    }

    /**
     * Removes the object property.
     *
     * @param prop the prop
     * @param object the object
     * @return the semantic object
     */
    public SemanticObject removeLiteralProperty(SemanticProperty prop, SemanticLiteral lit)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, lit.getLanguage(), ACT_REMOVE);
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list != null)
            {
                list.remove(lit);
            }
            return this;
        }
        //listObjectProperties(prop);

        ArrayList arr=null;
        if(SWBPlatform.isTDB())
        {
            arr=new ArrayList();
        }

        StmtIterator it = m_res.listProperties(prop.getRDFProperty());
        while (it.hasNext())
        {
            Statement stmt = it.nextStatement();
            if (lit.getValue().equals(stmt.getLiteral().getValue()))
            {
                if(SWBPlatform.isTDB())
                {
                    arr.add(stmt);
                }else
                {
                    it.remove();
                }
            }
        }
        it.close();

        if(arr!=null)
        {
            Iterator<Statement> it2=arr.iterator();
            while (it2.hasNext()) {
                Statement statement = it2.next();
                statement.remove();
            }
        }

        removePropertyValueCache(prop, lit.getLanguage());
        return this;
    }

    /**
     * List properties.
     *
     * @return the iterator
     */
    public Iterator<SemanticProperty> listProperties()
    {
        HashSet<SemanticProperty> properties=new HashSet<SemanticProperty>();
        StmtIterator props=this.m_res.listProperties();
        while(props.hasNext())
        {
            Statement stmt=props.nextStatement();
            Property prop=stmt.getPredicate();
            properties.add(getModel().getSemanticProperty(prop.getURI()));
        }
        props.close();
        return properties.iterator();
    }

    /**
     * List object properties.
     *
     * @param prop the prop
     * @return the iterator
     */
    public Iterator<SemanticObject> listObjectProperties(SemanticProperty prop)
    {
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list != null)
            {
                return list.iterator();
            }
            else
            {
                return new ArrayList().iterator();
            }
        }

        Iterator ret = getListObjectPropertyCache(prop);
        if(ret==null)
        {
            if (!prop.hasInverse())
            {
                ret = new SemanticIterator(m_res.listProperties(prop.getRDFProperty()));
            }
            else
            {
                ret = new SemanticIterator(getModel().getRDFModel().listStatements(null, prop.getInverse().getRDFProperty(), getRDFResource()), true);
            }
            ret=setListObjectPropertyCache(prop, ret);
        }
        return ret;
    }

    /**
     * Filter valid objects.
     *
     * @param it the it
     * @return the iterator
     */
    private Iterator<SemanticObject> filterValidObjects(Iterator<SemanticObject> it)
    {
        SemanticClass cls=null;
        SemanticProperty valid=null;
        ArrayList list=new ArrayList();
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            boolean add=true;
            //System.out.println("obj:"+obj);
            if(cls==null)
            {
                cls=obj.getSemanticClass();
                valid=cls.getProperty("valid");
            }
            //System.out.println("cls:"+cls+" valid:"+valid);
            if(valid!=null)
            {
                if(!obj.getBooleanProperty(valid))add=false;
            }
            //System.out.println("add:"+add);
            if(add)list.add(obj);
        }
        return list.iterator();
    }

    /**
     * Regresa lista de objetos activos y no borrados relacionados por la propiedad
     * Si no encuentra en el objeto busca en los padres.
     *
     * @param prop the prop
     * @return the iterator
     * @return
     */
    public Iterator<SemanticObject> listInheritProperties(SemanticProperty prop)
    {
        Iterator it=listObjectProperties(prop);
        if(prop.isInheritProperty())
        {
            it=filterValidObjects(it);
            if(!it.hasNext())
            {
                SemanticObject parent=getHerarquicalParent();
                if(parent!=null)
                {
                    it=parent.listInheritProperties(prop);
                }
            }
        }
        return it;
    }

    /**
     * Regresa lista de objetos activos y no borrados relacionados por la propiedad.
     *
     * @param prop the prop
     * @return the iterator
     * @return
     */
    public Iterator<SemanticObject> listValidObjectProperties(SemanticProperty prop)
    {
        Iterator it=filterValidObjects(listObjectProperties(prop));
        return it;
    }

    /**
     * Checks for object property.
     *
     * @param prop the prop
     * @param obj the obj
     * @return true, if successful
     */
    public boolean hasObjectProperty(SemanticProperty prop, SemanticObject obj)
    {

        //System.out.println("hasObjectProperty:"+this+" prop:"+prop+" obj:"+obj);
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list != null)
            {
                return list.contains(obj);
            }
            else
            {
                return false;
            }
        }

        Boolean ret = hasObjectPropertyCache(prop,obj);
        if(ret==null)
        {
            if(hasPropertyCache)
            {
                listObjectProperties(prop);  //Cachar lista
                ret=hasObjectPropertyCache(prop, obj);
            }else
            {
                ret=getRDFResource().hasProperty(prop.getRDFProperty(), obj.getRDFResource());
            }
        }
        if(ret==null)ret=false;
        return ret;
    }

    /**
     * Checks for object property.
     *
     * @param prop the prop
     * @return true, if successful
     */
    public boolean hasObjectProperty(SemanticProperty prop)
    {

        //System.out.println("hasObjectProperty:"+this+" prop:"+prop+" obj:"+obj);
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list != null)
            {
                return !list.isEmpty();
            }
            else
            {
                return false;
            }
        }

        Boolean ret = hasObjectPropertyCache(prop);
        if(ret==null)
        {
            if(hasPropertyCache)
            {
                listObjectProperties(prop);  //Cachar lista
                ret=hasObjectPropertyCache(prop);
            }else
            {
                ret=getRDFResource().hasProperty(prop.getRDFProperty());
            }
        }
        if(ret==null)ret=false;
        return ret;
    }


    /**
     * Gets the object property.
     *
     * @param prop the prop
     * @return the object property
     */
    public SemanticObject getObjectProperty(SemanticProperty prop)
    {
        return getObjectProperty(prop, null);
    }

    /**
     * Gets the object property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the object property
     */
    public SemanticObject getObjectProperty(SemanticProperty prop, SemanticObject defValue)
    {
        SemanticObject ret = defValue;
        if(prop.getCardinality()!=1)
        {
            Iterator<SemanticObject> it=listObjectProperties(prop);
            if(it!=null && it.hasNext())ret=it.next();
            return ret;
        }
        if (m_virtual)
        {
            ArrayList<SemanticObject> arr = ((ArrayList) m_virtprops.get(prop.getURI()));
            if (arr != null && !arr.isEmpty())
            {
                ret = (SemanticObject) arr.get(0);
            }
            return ret;
        }
        Object aux=getPropertyValueCache(prop, null);
        if(aux==NULL)return defValue;
        ret=(SemanticObject)aux;
        if(ret==null)
        {

            if (!prop.hasInverse())
            {
                Statement stm = m_res.getProperty(prop.getRDFProperty());
                if (stm != null)
                {
                    try
                    {
                        ret = SemanticObject.createSemanticObject(stm.getResource());
                    }
                    catch (SWBResourceNotFound noe)
                    {
                        //Recurso no encontrado
                        log.warn(noe.getMessage()+":"+stm.getResource());
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            }
            else
            {
                Iterator<SemanticObject> it = new SemanticIterator(getModel().getRDFModel().listStatements(null, prop.getInverse().getRDFProperty(), getRDFResource()), true);
                if(it.hasNext())
                {
                    ret=it.next();
                }
            }

            setPropertyValueCache(prop, null, ret);
        }
        return ret;
    }


    /**
     * Gets the locale statement.
     *
     * @param prop the prop
     * @param lang the lang
     * @return the locale statement
     */
    private Statement getLocaleStatement(SemanticProperty prop, String lang)
    {
        //System.out.println(m_res+" "+prop+" "+lang);
        StmtIterator stit = m_res.listProperties(prop.getRDFProperty());
        //System.out.println("->"+m_res.getProperty(prop.getRDFProperty()));
        Statement st = null;
        while (stit.hasNext())
        {
            Statement staux = stit.nextStatement();
            String lg = staux.getLanguage();
            if(lg!=null && lg.length()==0)lg=null;
            //System.out.println("-->"+lang+" "+lg+" "+staux);
            if ((lang==null && lg==null) || (lg != null && lg.equals(lang)))
            {
                st = staux;
                break;
            }
        }
        stit.close();
        return st;
    }

    /**
     * External invoker get.
     *
     * @param prop the prop
     * @return the object
     */
    private Object externalInvokerGet(SemanticProperty prop)
    {
        //System.out.println("externalInvokerGet:"+prop);
        Object ret = null;
        if (!m_virtual)
        {
            GenericObject obj = createGenericInstance();
            Class cls = obj.getClass();
            Method method=extGetMethods.get(cls.getName()+"-"+prop.getURI());
            if(method==null)
            {
                String pre="get";
                if(prop.isBoolean())pre="is";
                String name = prop.getPropertyCodeName();
                if (name == null)
                {
                    name = prop.getName();
                }
                name = pre + name.substring(0, 1).toUpperCase() + name.substring(1);
                try
                {
                    method = cls.getMethod(name);
                    extGetMethods.put(cls.getName()+"-"+prop.getURI(), method);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
                //System.out.println(obj+" "+name+" "+cls+" "+method);
            }
            try
            {
                ret = method.invoke(obj);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        //System.out.println("externalInvoker:"+ret);
        }
        return ret;
    }

    /**
     * External invoker set.
     *
     * @param prop the prop
     * @param values the values
     * @return the object
     */
    private Object externalInvokerSet(SemanticProperty prop, Object... values)
    {
//        System.out.println("externalInvokerSet:"+prop+" "+values);
        Object ret = null;
        if (!m_virtual)
        {
            Object vals[]=null;
            GenericObject obj = this.createGenericInstance();
            Class cls = obj.getClass();
            Method method=extSetMethods.get(cls.getName()+"-"+prop.getURI()+"-"+values.length);
            if(method==null)
            {
                String name = prop.getPropertyCodeName();
                if (name == null)
                {
                    name = prop.getName();
                }
                name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
//                System.out.println("name:"+name);
                try
                {
                    Class types[]=null;
                    if(prop.isLocaleable())
                    {
                        types=new Class[values.length];
                        //vals=values;
                    }else
                    {
                        types=new Class[1];
                        //vals=new Object[1];
                        //vals[0]=values[0];
                    }

                    Object o=values[0];
                    if(o!=null)types[0]=o.getClass();
                    else
                    {
                        if(prop.isString())types[0]=String.class;
                        else if(prop.isDate())types[0]=Date.class;
                        else if(prop.isObjectProperty())types[0]=prop.getDomainClass().getObjectClass();
                    }
                    Class pri=wrapperToPrimitive.get(types[0]);
                    if(pri!=null)
                    {
                        types[0]=pri;
                    }

                    if(prop.isLocaleable() && values.length>1)
                    {
                        o=values[1];
                        if(o==null)types[1]=String.class;
                        else types[1]=o.getClass();
                    }
//                    System.out.println("getMethod:"+name+" "+types);
                    method = cls.getMethod(name,types);
                    extSetMethods.put(cls.getName()+"-"+prop.getURI()+"-"+values.length, method);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }  // Movi la generación de "vals" a esta posición
            // por que si esta en caché se pasaba un null al invoke - MAPS
            if (prop.isLocaleable())
            {
                vals = values;
            } else
            {
                vals = new Object[1];
                vals[0] = values[0];
            }

            try
            {
                ret = method.invoke(obj,vals);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        //System.out.println("externalInvoker:"+ret);
        }
        return ret;
    }

    /**
     * Removes the dependencies.
     *
     * @param stack the stack
     */
    public void removeDependencies(ArrayList<SemanticObject> stack)
    {
        Iterator<SemanticProperty> itp=getSemanticClass().listProperties();
        while(itp.hasNext())
        {
            SemanticProperty prop=itp.next();
            if(prop.isRemoveDependency())
            {
                //System.out.println(prop+" "+prop.isRemoveDependency());
                if(prop.getCardinality()==1)
                {
                    SemanticObject dep=getObjectProperty(prop);
                    if(dep!=null)
                    {
                        //System.out.println(dep);
                        try
                        {
                            if(!stack.contains(dep))dep.remove(stack);
                        }catch(Exception e){log.error(e);}
                    }
                }else
                {
                    Iterator<SemanticObject> it=listObjectProperties(prop);
                    while(it.hasNext())
                    {
                        SemanticObject dep=it.next();
                        //System.out.println(dep);
                        try
                        {
                            if(stack!=null && !stack.contains(dep))dep.remove(stack);
                        }catch(Exception e){log.error(e);}
                    }
                }
            }
        }
    }

    /**
     * Reset relateds cache.
     */
    public void resetRelatedsCache()
    {
        //TODO:mejorar
        Iterator<SemanticObject> rel=listRelatedObjects();
        while(rel.hasNext())
        {
            SemanticObject obj=rel.next();
            obj.resetCache();
        }
    }

    /**
     * Dispose.
     */
    public void dispose()
    {
        try
        {
            GenericObject gen=getGenericInstance();
            if(gen!=null)gen.dispose();
        }catch(Exception e){log.error(e);}

        resetRelatedsCache();

        removeCache(getURI());
    }

    /**
     * Removes the.
     */
    public void remove()
    {
        remove(new ArrayList());
    }

    /**
     * Removes the.
     *
     * @param stack the stack
     */
    public void remove(ArrayList<SemanticObject> stack)
    {
        stack.add(this);
        if(getModel().getModelObject().equals(this))    //es un modelo
        {
            removeDependencies(stack);
            SWBPlatform.getSemanticMgr().removeModel(getId());
            SWBPlatform.getSemanticMgr().notifyChange(this, null, null, ACT_REMOVE);
        }else                                           //es un objeto
        {
            //TODO:revisar esto de vic
            Iterator<SemanticProperty> properties = this.getSemanticClass().listProperties();
            while (properties.hasNext())
            {
                SemanticProperty prop = properties.next();
                if (prop.isBinary())
                {
                    // removida manualmente por ser binaria
                    removeProperty(prop);
                }
            }
            SWBPlatform.getSemanticMgr().notifyChange(this, null, null, ACT_REMOVE);

            resetRelatedsCache();

            //Eliminar dependencias
            removeDependencies(stack);

            //Borrar objeto
            Resource res=getRDFResource();
            if(res!=null)
            {
                SemanticModel model=getModel();
                //System.out.println("remove1:"+res+" model:"+model);
                Iterator<Entry<String,SemanticModel>> it=SWBPlatform.getSemanticMgr().getModels().iterator();
                while(it.hasNext())
                {
                    Entry<String,SemanticModel> ent=it.next();
                    SemanticModel m=ent.getValue();
                    //System.out.println("remove2:"+res+" model:"+m);
                    m.getRDFModel().removeAll(null,null,res);
                }
                model.getRDFModel().removeAll(res,null,null);
                //model.getRDFModel().removeAll(null,null,res);
            }
        }
        removeCache(getURI());
    }

    /**
     * Removes the properties.
     */
    public void removeProperties()
    {
        m_res.removeProperties();
        removeCache(this.getURI());
    }

/**
 * ***************************************************************************************************************.
 *
 * @param prop the prop
 * @param value the value
 * @return the semantic object
 */

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value)
    {
        return setProperty(prop, value,null);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value, boolean evalExtInvo)
    {
        return setProperty(prop, value,null,evalExtInvo);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param lang the lang
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value, String lang)
    {
        return setProperty(prop, value, lang, true);
    }


    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param lang the lang
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value, String lang, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value,lang);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(value, lang));
        }
        return this;
    }

    /**
     * Regresa valor de la Propiedad especificada.
     *
     * @param prop the prop
     * @return valor de la propiedad, si no existe la propiedad regresa null
     */
    public String getProperty(SemanticProperty prop)
    {
        return getProperty(prop, true);
    }

    /**
     * Regresa valor de la Propiedad especificada.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return valor de la propiedad, si no existe la propiedad regresa null
     */
    public String getProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getProperty(prop, null,evalExtInvo);
    }


    /**
     * Gets the property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the property
     */
    public String getProperty(SemanticProperty prop, String defValue)
    {
        return getProperty(prop,defValue, true);
    }

    /**
     * Gets the property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the property
     */
    public String getProperty(SemanticProperty prop, String defValue, boolean evalExtInvo)
    {
        String ret = null;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            Object aux = externalInvokerGet(prop);
            if (aux!=null)
            {
                ret = "" + aux;
            }
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop,null);
            if(lit!=null)
            {
                ret=lit.getString();
            }
        }
        if (ret == null)
        {
            ret = defValue;
        }
        return ret;
    }

    /**
     * Gets the property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param lang the lang
     * @return the property
     */
    public String getProperty(SemanticProperty prop, String defValue, String lang)
    {
        String ret=null;
        SemanticLiteral lit=getLiteralProperty(prop,lang);
        if(lit!=null)
        {
            ret=lit.getString();
        }
        if (ret == null)
        {
            ret = defValue;
        }
        return ret;
    }

    /**
     * Gets the locale property.
     *
     * @param prop the prop
     * @param lang the lang
     * @return the locale property
     */
    public String getLocaleProperty(SemanticProperty prop, String lang)
    {
        String ret = null;
        if (lang == null)
        {
            ret = getProperty(prop);
        }else
        {
            ret= getProperty(prop, null, lang);
            if(ret==null)
            {
                ret=getProperty(prop);
            }
        }
        return ret;
    }

    /**
     * Gets the int property.
     *
     * @param prop the prop
     * @return the int property
     */
    public int getIntProperty(SemanticProperty prop)
    {
        return getIntProperty(prop, true);
    }

    /**
     * Gets the int property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the int property
     */
    public int getIntProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getIntProperty(prop, 0, evalExtInvo);
    }

    /**
     * Gets the int property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the int property
     */
    public int getIntProperty(SemanticProperty prop, int defValue)
    {
        return getIntProperty(prop, defValue, true);
    }

    /**
     * Gets the int property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the int property
     */
    public int getIntProperty(SemanticProperty prop, int defValue, boolean evalExtInvo)
    {
        Integer ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(Integer)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getInt();
            }
        }
        return ret;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setIntProperty(SemanticProperty prop, int value)
    {
        return setIntProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setIntProperty(SemanticProperty prop, int value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(new Integer(value)));
        }
        return this;
    }

    /**
     * Gets the long property.
     *
     * @param prop the prop
     * @return the long property
     */
    public long getLongProperty(SemanticProperty prop)
    {
        return getLongProperty(prop, true);
    }

    /**
     * Gets the long property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the long property
     */
    public long getLongProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getLongProperty(prop, 0L, evalExtInvo);
    }

    /**
     * Gets the long property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the long property
     */
    public long getLongProperty(SemanticProperty prop, long defValue)
    {
        return getLongProperty(prop, defValue, true);
    }

    /**
     * Gets the long property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the long property
     */
    public long getLongProperty(SemanticProperty prop, long defValue, boolean evalExtInvo)
    {
        Long ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(Long)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getLong();
            }
        }
        return ret;
    }

    /**
     * Sets the input stream property.
     *
     * @param prop the prop
     * @param value the value
     * @param name the name
     * @return the semantic object
     * @throws SWBException the sWB exception
     */
    public SemanticObject setInputStreamProperty(SemanticProperty prop, InputStream value, String name) throws SWBException
    {
        String workPath = this.getWorkPath();
        if (!(workPath.endsWith("\\") || workPath.equals("/")))
        {
            workPath += "/" + name;
        }
        setProperty(prop, name);
        SWBPlatform.createInstance().writeFileToPlatformWorkPath(workPath, value);
        return this;
    }

    /**
     * Gets the input stream property.
     *
     * @param prop the prop
     * @return the input stream property
     * @throws SWBException the sWB exception
     */
    public InputStream getInputStreamProperty(SemanticProperty prop) throws SWBException
    {
        String value = getProperty(prop);
        String workPath = this.getWorkPath();
        if (!(workPath.endsWith("\\") || workPath.equals("/")))
        {
            workPath += "/" + value;
        }
        return SWBPlatform.createInstance().getFileFromPlatformWorkPath(workPath);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setLongProperty(SemanticProperty prop, long value)
    {
        return setLongProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setLongProperty(SemanticProperty prop, long value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(new Long(value)));
        }
        return this;
    }

    /**
     * Gets the float property.
     *
     * @param prop the prop
     * @return the float property
     */
    public float getFloatProperty(SemanticProperty prop)
    {
        return getFloatProperty(prop, true);
    }

    /**
     * Gets the float property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the float property
     */
    public float getFloatProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getFloatProperty(prop, 0F, evalExtInvo);
    }

    /**
     * Gets the float property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the float property
     */
    public float getFloatProperty(SemanticProperty prop, float defValue)
    {
        return getFloatProperty(prop, defValue, true);
    }

    /**
     * Gets the float property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the float property
     */
    public float getFloatProperty(SemanticProperty prop, float defValue, boolean evalExtInvo)
    {
        Float ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(Float)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getFloat();
            }
        }
        return ret;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setFloatProperty(SemanticProperty prop, float value)
    {
        return setFloatProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setFloatProperty(SemanticProperty prop, float value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(new Float(value)));
        }
        return this;
    }

    /**
     * Gets the double property.
     *
     * @param prop the prop
     * @return the double property
     */
    public double getDoubleProperty(SemanticProperty prop)
    {
        return getDoubleProperty(prop, true);
    }

    /**
     * Gets the double property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the double property
     */
    public double getDoubleProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getDoubleProperty(prop, 0D,evalExtInvo);
    }

    /**
     * Gets the double property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the double property
     */
    public double getDoubleProperty(SemanticProperty prop, double defValue)
    {
        return getDoubleProperty(prop, defValue, true);
    }

    /**
     * Gets the double property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the double property
     */
    public double getDoubleProperty(SemanticProperty prop, double defValue, boolean evalExtInvo)
    {
        Double ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(Double)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getDouble();
            }
        }
        return ret;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDoubleProperty(SemanticProperty prop, double value)
    {
        return setDoubleProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setDoubleProperty(SemanticProperty prop, double value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(new Double(value)));
        }
        return this;
    }

    /**
     * Gets the boolean property.
     *
     * @param prop the prop
     * @return the boolean property
     */
    public boolean getBooleanProperty(SemanticProperty prop)
    {
        return getBooleanProperty(prop, true);
    }

    /**
     * Gets the boolean property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the boolean property
     */
    public boolean getBooleanProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getBooleanProperty(prop, false, evalExtInvo);
    }

    /**
     * Gets the boolean property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the boolean property
     */
    public boolean getBooleanProperty(SemanticProperty prop, boolean defValue, boolean evalExtInvo)
    {
        Boolean ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(Boolean)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getBoolean();
            }
        }
        return ret;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setBooleanProperty(SemanticProperty prop, boolean value)
    {
        return setBooleanProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setBooleanProperty(SemanticProperty prop, boolean value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(new Boolean(value)));
        }
        return this;
    }


    /**
     * Gets the date property.
     *
     * @param prop the prop
     * @return the date property
     */
    public java.util.Date getDateProperty(SemanticProperty prop)
    {
        return getDateProperty(prop, true);
    }

    /**
     * Gets the date property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the date property
     */
    public java.util.Date getDateProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getDateProperty(prop, null, evalExtInvo);
    }

    /**
     * Gets the date property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the date property
     */
    public java.util.Date getDateProperty(SemanticProperty prop, java.util.Date defValue)
    {
        return getDateProperty(prop, defValue, true);
    }

    /**
     * Gets the date property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the date property
     */
    public java.util.Date getDateProperty(SemanticProperty prop, java.util.Date defValue, boolean evalExtInvo)
    {
        java.util.Date ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(java.util.Date)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getDateTime();
            }
        }
        return ret;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateProperty(SemanticProperty prop, java.util.Date value)
    {
        return setDateProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateProperty(SemanticProperty prop, java.util.Date value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            if(value!=null)
            {
                setLiteralProperty(prop, new SemanticLiteral(new Timestamp(value.getTime())));
            }else
            {
                removeProperty(prop);
            }
        }
        return this;
    }


    /**
     * Gets the sQL date property.
     *
     * @param prop the prop
     * @return the sQL date property
     */
    public Date getSQLDateProperty(SemanticProperty prop)
    {
        return getSQLDateProperty(prop, true);
    }

    /**
     * Gets the sQL date property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the sQL date property
     */
    public Date getSQLDateProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getSQLDateProperty(prop, null, evalExtInvo);
    }

    /**
     * Gets the sQL date property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the sQL date property
     */
    public Date getSQLDateProperty(SemanticProperty prop, Date defValue)
    {
        return getSQLDateProperty(prop, defValue, true);
    }

    /**
     * Gets the sQL date property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the sQL date property
     */
    public Date getSQLDateProperty(SemanticProperty prop, Date defValue, boolean evalExtInvo)
    {
        Date ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(Date)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getDate();
            }
        }
        return ret;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setSQLDateProperty(SemanticProperty prop, Date value)
    {
        return setSQLDateProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setSQLDateProperty(SemanticProperty prop, Date value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(value));
        }
        return this;
    }

    /**
     * Gets the date time property.
     *
     * @param prop the prop
     * @return the date time property
     */
    public Timestamp getDateTimeProperty(SemanticProperty prop)
    {
        return getDateTimeProperty(prop, true);
    }

    /**
     * Gets the date time property.
     *
     * @param prop the prop
     * @param evalExtInvo the eval ext invo
     * @return the date time property
     */
    public Timestamp getDateTimeProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getDateTimeProperty(prop, null, evalExtInvo);
    }

    /**
     * Gets the date time property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @return the date time property
     */
    public Timestamp getDateTimeProperty(SemanticProperty prop, Timestamp defValue)
    {
        return getDateTimeProperty(prop, defValue, true);
    }

    /**
     * Gets the date time property.
     *
     * @param prop the prop
     * @param defValue the def value
     * @param evalExtInvo the eval ext invo
     * @return the date time property
     */
    public Timestamp getDateTimeProperty(SemanticProperty prop, Timestamp defValue, boolean evalExtInvo)
    {
        Timestamp ret=defValue;
        if (evalExtInvo && prop.isExternalInvocation())
        {
            ret=(Timestamp)externalInvokerGet(prop);
        }else
        {
            SemanticLiteral lit=getLiteralProperty(prop);
            if(lit!=null)
            {
                ret=lit.getDateTime();
            }
        }
        return ret;
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateTimeProperty(SemanticProperty prop, Timestamp value)
    {
        return setDateTimeProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado.
     *
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @param evalExtInvo the eval ext invo
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateTimeProperty(SemanticProperty prop, Timestamp value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            if(value!=null)
            {
                setLiteralProperty(prop, new SemanticLiteral(value));
            }else
            {
                removeProperty(prop);
            }
        }
        return this;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName()
    {
        return getDisplayName(null);
    }

    /**
     * Gets the display name.
     *
     * @param lang the lang
     * @return the display name
     */
    public String getDisplayName(String lang)
    {
        String ret = null;
        SemanticClass cls = getSemanticClass();
        SemanticProperty prop=null;
        if (cls != null)
        {
             prop= cls.getDisplayNameProperty();
        }
        if (prop != null)
        {
            if (prop.isDataTypeProperty())
            {
                ret = getLocaleProperty(prop, lang);
            }
            else if (prop.isObjectProperty())
            {
                SemanticObject obj = getObjectProperty(prop);
                if(obj!=null)
                {
                    ret = obj.getDisplayName(lang);
                }
            }
        }
        if(ret==null)
        {
            ret = getLabel(lang);
            if(ret==null)
            {
                ret=cls.getName()+":"+getId();
                /*
                if(getURI()!=null)
                {
                    
                    int x=getURI().indexOf('#');
                    if(x>0)
                    {
                        ret=getURI().substring(0,x+1);
                        ret=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getNsURIPrefix(ret);
                        if(ret!=null)
                        {
                            ret+=":"+getId();
                        }else
                        {
                            ret=getId();
                        }
                    }else
                    {
                        ret=getId();
                    }
                }else
                {
                    ret=getResId();
                }
                */
            }
        }
        return ret;
    }

    /**
     * Transform to semantic property.
     *
     * @return the semantic property
     */
    public SemanticProperty transformToSemanticProperty()
    {
        SemanticProperty ret = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(getURI());
        return ret;
    }

    /**
     * Transform to semantic class.
     *
     * @return the semantic class
     */
    public SemanticClass transformToSemanticClass()
    {
        SemanticClass ret = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(getURI());
        return ret;
    }

    /**
     * List related objects.
     *
     * @return the iterator
     */
    public Iterator<SemanticObject> listRelatedObjects()
    {
        ArrayList arr=new ArrayList();
        StmtIterator stit = getModel().getRDFModel().listStatements(null, null, getRDFResource());
        Iterator it=new SemanticIterator(stit, true);
        while(it.hasNext())
        {
            arr.add(it.next());
        }
        SemanticClass cls=getSemanticClass();
        it=cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=(SemanticProperty)it.next();
            if(prop.isObjectProperty() && prop.isInverseOf())
            {
               SemanticObject obj=getObjectProperty(prop);
               if(obj!=null)arr.add(obj);
            }
        }
        return arr.iterator();
    }

    /**
     * List herarquical childs.
     *
     * @return the iterator
     */
    public Iterator<SemanticObject> listHerarquicalChilds()
    {
        ArrayList<SemanticObject> list=new ArrayList();
        SemanticClass cls=getSemanticClass();
        if(cls!=null)
        {
            Iterator<SemanticProperty> it=cls.listHerarquicalProperties();
            while(it.hasNext())
            {
                SemanticProperty prop=it.next();
                SemanticClass hfcls=prop.getHerarquicalRelationFilterClass();
                Iterator<SemanticObject> it2=listObjectProperties(prop);
                while(it2.hasNext())
                {
                    SemanticObject ch=it2.next();
                    if(hfcls!=null)
                    {
                        //System.out.println(this+" "+cls+" "+ch+" "+hfcls);
                        if(!ch.instanceOf(hfcls))continue;
                    }
                    list.add(ch);
                }
            }
        }
        return list.iterator();
    }

    /**
     * Checks for herarquical parents.
     *
     * @return true, if successful
     */
    public boolean hasHerarquicalParents()
    {
        boolean ret=false;
        Iterator<SemanticProperty> it=getSemanticClass().listInverseHerarquicalProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            if(hasObjectProperty(prop))
            {
                ret=true;
                break;
            }
        }
        return ret;
    }

    /**
     * List herarquical parents.
     *
     * @return the iterator
     */
    public Iterator<SemanticObject> listHerarquicalParents()
    {
        HashSet<SemanticObject> list=new HashSet();
        Iterator<SemanticProperty> it=getSemanticClass().listInverseHerarquicalProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            Iterator<SemanticObject> it2=listObjectProperties(prop);
            while(it2.hasNext())
            {
                SemanticObject ch=it2.next();
                if(!ch.equals(this))list.add(ch);
            }
        }
        return list.iterator();
    }

    /**
     * Clona un objeto y sus dependencias, el objeto hash sirve para
     * almacenar las dependencias clonadas.
     *
     * @return the semantic object
     * @return
     */
    public SemanticObject cloneObject()
    {
        //Get URI
        String id=null;
        if(getSemanticClass().isAutogenId())
        {
            id=""+getModel().getCounter(getSemanticClass());
        }else
        {
            int x=1;
            do
            {
                x++;
                id=getId()+x;
            }while(createSemanticObject(id)!=null);
        }
        String uri=getModel().getObjectUri(id, getSemanticClass());
        Resource res=getModel().getRDFModel().createResource(uri);

        //Get Herarquical properties
        ArrayList<SemanticProperty> hp=new ArrayList();
        Iterator<SemanticProperty> ithp=getSemanticClass().listHerarquicalProperties();
        while(ithp.hasNext())
        {
            hp.add(ithp.next());
        }
        ithp=getSemanticClass().listInverseHerarquicalProperties();
        while(ithp.hasNext())
        {
            hp.add(ithp.next());
        }

        //res.addProperty(getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE), m_cls.getOntClass());
        Iterator<Statement> it=m_res.listProperties();
        while(it.hasNext())
        {
            Statement st=it.next();
            Property prop=st.getPredicate();
            SemanticProperty sprop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(prop.getURI());
            if(sprop==null || !sprop.isRemoveDependency())
            {
                Statement nst=new StatementImpl(res, prop, st.getObject());
                getModel().getRDFModel().add(nst);
            }else
            {
                System.out.println("Remove dependency prop:"+prop);
            }
        }

        SemanticObject ret=SemanticObject.createSemanticObject(res);
        SWBPlatform.getSemanticMgr().notifyChange(ret, null, null, ACT_CLONE);
        return ret;
    }

    /**
     * Gets the herarquical parent.
     *
     * @return the herarquical parent
     */
    public SemanticObject getHerarquicalParent()
    {
        SemanticObject ret=null;
        Iterator<SemanticObject> it=listHerarquicalParents();
        if(it.hasNext())ret=it.next();
        return ret;
    }

    /**
     * Gets the work path.
     *
     * @return the work path
     */
    public String getWorkPath()
    {
        SemanticClass cls=getSemanticClass();
        if(cls==null)System.out.println("Class null:"+getURI());
        return "/models/"+getModel().getName()+"/"+cls.getClassGroupId()+"/"+getId();
    }

    /**
     * Regresa todos los valores de la propiedad sin importar el idioma
     * Utilizado para la indexación del objeto.
     *
     * @param prop the prop
     * @return the property index data
     * @return
     */
    public String getPropertyIndexData(SemanticProperty prop)
    {
        String ret="";
        StmtIterator stit = m_res.listProperties(prop.getRDFProperty());
        while(stit.hasNext())
        {
            Statement st=stit.nextStatement();
            ret=ret+st.getString()+"\n";
        }
        stit.close();
        return ret;
    }


    /**
     * Regresa el valor de la propiedad rdfs:label del objeto.
     *
     * @return the label
     */
    public String getLabel()
    {
        return getLabel(null);
    }

    /**
     * Regresa el valor de la propiedad rdfs:label del objeto.
     *
     * @param lang the lang
     * @return the label
     */
    public String getLabel(String lang)
    {
        return getLocaleProperty(getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL), lang);
    }

    /**
     * Regresa el valor de la propiedad rdfs:comment del objeto.
     *
     * @return the label
     */
    public String getComment()
    {
        return getComment(null);
    }

    /**
     * Regresa el valor de la propiedad rdfs:comment del objeto.
     *
     * @param lang the lang
     * @return the label
     */
    public String getComment(String lang)
    {
        return getLocaleProperty(getModel().getSemanticProperty(SemanticVocabulary.RDFS_COMMENT), lang);
    }

}