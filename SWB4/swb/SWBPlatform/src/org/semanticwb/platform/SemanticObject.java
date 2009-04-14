package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.GenericObject;
import org.w3c.dom.Document;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SemanticObject
{
    private static Logger log = SWBUtils.getLogger(SemanticObject.class);

    private static HashMap<String, SemanticObject>m_objs=new HashMap();
    private GenericObject m_genobj=null;

    private Resource m_res = null;
    private SemanticModel m_model = null;
    //Virtual properties
    private SemanticClass m_cls = null;
    private boolean m_virtual = false;
    private HashMap m_virtprops;

    private HashMap m_cacheprops;
    private HashMap m_cachepropsrel;

    private static String NULL="__NULL__";

    private static boolean hasCache=true;
    private static boolean hasPropertyCache=true;

    private static HashMap<String, Method> extGetMethods=new HashMap();
    private static HashMap<String, Method> extSetMethods=new HashMap();

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

    private SemanticObject(Resource res)
    {
        if(res==null)throw new NullPointerException("Resource is Null...");
        m_cacheprops=new HashMap();
        m_cachepropsrel=new HashMap();
        this.m_res = res;
        validateModel();
        //System.out.println("SemanticObject:"+res);
    }

    /**
     * Regresa instancia del SemanticObject si existe en Cache, de lo contrario
     * regresa null
     * @param uri
     * @return
     */
    public static SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=null;
        if(hasCache)ret=m_objs.get(uri);
        return ret;
    }

    public GenericObject createGenericInstance()
    {
        if(m_genobj==null)
        {
            m_genobj=getSemanticClass().construcGenericInstance(this);
        }
        return m_genobj;
    }

/**
 * Regrea una instancia del SemanticObject en base al URI dado.
 * Si el recurso no existe regresa null
 * @param uri
 * @return
 */
    public static SemanticObject createSemanticObject(String uri)
    {
        SemanticObject ret=getSemanticObject(uri);
        if(ret==null)
        {
            Resource res=SWBPlatform.getSemanticMgr().getOntology().getResource(uri);
            if(res!=null)
            {
                ret=new SemanticObject(res);
                m_objs.put(uri, ret);
            }
        }
        return ret;
    }

    public static SemanticObject createSemanticObject(Resource res)
    {
        String id=res.getURI();
        if(id==null)id=res.getId().toString();
        SemanticObject ret=getSemanticObject(id);
        if(ret==null)
        {
            //System.out.println("res1:"+res+" "+id);
            //if(hasCache)
            {
                if(res.getURI()!=null && (res.getModel()==SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel() || res.getModel()==SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel()))
                {
                    res=SWBPlatform.getSemanticMgr().getOntology().getResource(res.getURI());
                }
            }
            //System.out.println("res2:"+res+" "+id);
            ret=new SemanticObject(res);
            m_objs.put(id, ret);
        }
        return ret;
    }

    /**
     * elimina cache
     */
    public void resetCache()
    {
        if(!m_virtual)
        {
            m_cls = null;
            m_cacheprops=new HashMap();
            m_cachepropsrel=new HashMap();
        }
    }

    public static void removeCache(String uri)
    {
        m_objs.remove(uri);
    }

    private void setPropertyValueCache(SemanticProperty prop, String lang, Object value)
    {
        if(value==null)value=NULL;
        if(value!=null)
        {
            m_cacheprops.put(prop.getURI()+"|"+lang, value);
            m_cachepropsrel.remove(prop.getURI());
        }
    }

    private void addPropertyValueCache(SemanticProperty prop, String lang, Object value)
    {
        removePropertyValueCache(prop, lang);
        if(prop.isInverseOf() && value instanceof SemanticObject)
        {
            ((SemanticObject)value).removePropertyValueCache(prop.getInverse(), lang);
        }
    }

    private Object getPropertyValueCache(SemanticProperty prop, String lang)
    {
        Object ret=null;
        if(hasPropertyCache)ret=m_cacheprops.get(prop.getURI()+"|"+lang);
        return ret;
    }

    private void removePropertyValueCache(SemanticProperty prop, String lang)
    {
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
                    Iterator it=((ArrayList)aux).iterator();
                    while(it.hasNext())
                    {
                        SemanticObject obj=(SemanticObject)it.next();
                        obj.m_cacheprops.remove(inv.getURI()+"|"+"list");
                    }
                }else
                {
                    SemanticObject obj=(SemanticObject)aux;
                    obj.m_cacheprops.remove(inv.getURI()+"|"+null);
                }
            }
        }
        m_cacheprops.remove(prop.getURI()+"|"+lang);
        m_cachepropsrel.remove(prop.getURI());
    }

    private Iterator<SemanticObject> getListObjectPropertyCache(SemanticProperty prop)
    {
        Iterator it=null;
        ArrayList arr=(ArrayList)m_cacheprops.get(prop.getURI()+"|list");
        if(arr!=null)it=arr.iterator();
        return it;
    }

    private Boolean hasObjectPropertyCache(SemanticProperty prop, SemanticObject obj)
    {
        Boolean ret=null;
        ArrayList arr=(ArrayList)m_cacheprops.get(prop.getURI()+"|list");
        if(arr!=null)
        {
//            Iterator it=arr.iterator();
//            while(it.hasNext())
//            {
//                System.out.println("Ite:"+it.next());
//            }
            ret=arr.contains(obj);
        }
//        System.out.println("hasObjectPropertyCache:"+this+" prop:"+prop+" obj:"+obj+" "+ret);
        return ret;
    }
    

    private Iterator<SemanticObject> setListObjectPropertyCache(SemanticProperty prop, Iterator<SemanticObject> list)
    {
        //System.out.println("setListObjectPropertyCache:"+this+" "+prop);
        ArrayList arr=new ArrayList();
        while(list.hasNext())
        {
            SemanticObject obj=list.next();
            arr.add(obj);
            //System.out.println("-->add:"+obj);
        }
        m_cacheprops.put(prop.getURI()+"|list", arr);
        return arr.iterator();
    }

    private void validateModel()
    {
        String ns = getModel().getNameSpace();
        if (ns != null &&  m_res.getURI() !=null && !m_res.getURI().startsWith(ns))
        {
            //System.out.println("ns:"+ns+" "+m_res.getURI());
            Resource aux=SWBPlatform.getSemanticMgr().getOntology().getResource(m_res.getURI());
            if(aux==null)throw new NullPointerException("Resource not Found:"+m_res.getURI());
            m_res = aux;
            m_model = null;
        }
    }

    public boolean isVirtual()
    {
        return m_virtual;
    }

    public void setRDFResource(Resource res)
    {
        this.m_model = null;
        this.m_virtual = false;
        this.m_res = res;
        validateModel();
    }

    /**
     * Contruye un SemanticObject virtual
     *
     * @param model
     */
    public SemanticObject()
    {
        this(null, null);
    }

    /**
     * Contruye un SemanticObject virtual relacionado al Model y al tipo de elemento
     * 
     * @param model
     */
    public SemanticObject(SemanticModel model, SemanticClass cls)
    {
        m_model = model;
        m_cls = cls;
        m_virtual = true;
        m_virtprops = new HashMap();
        m_cacheprops=new HashMap();
        m_cachepropsrel=new HashMap();
    }

    public String getURI()
    {
        if (m_virtual)
        {
            return null;
        }
        return m_res.getURI();
    }

    public String getId()
    {
        String id=null;
        if (m_virtual)
        {
            return null;
        }
        id = getURI();
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
        return id;
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
     * Regresa URI codificado para utilizar en ligas de html
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }

    public String getRDFName()
    {
        if (m_virtual)
        {
            return null;
        }
        return m_res.getLocalName();
    }

    public SemanticClass getSemanticClass()
    {
        //System.out.print("getSemanticClass:"+getURI());
        if (m_cls == null)
        {
            Statement stm = m_res.getProperty(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty());
            if(stm!=null)
            {
                m_cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
            }
        }
        //System.out.println(" m_cls:"+m_cls);
        return m_cls;
    }

    public Iterator<SemanticClass> listSemanticClasses()
    {
        if (m_virtual)
        {
            //TODO:
            return null;//m_virtclass;
        }
        return new SemanticClassIterator<SemanticClass>(m_res.listProperties(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty()));
    }

    public void addSemanticClass(SemanticClass cls)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, cls, "ADD");
        if (m_virtual)
        {
            //TODO:
        }
        else
        {
            m_res.addProperty(getModel().getSemanticProperty(SemanticVocabulary.RDF_TYPE).getRDFProperty(), cls.getOntClass());
        }
    }

    public SemanticObject removeSemanticClass(SemanticClass cls)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, cls, "REMOVE");
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
        }
        return this;
    }

    /**
     * Regresa el Modelo de del SemanticObject
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

    public Resource getRDFResource()
    {
        if (m_virtual)
        {
            return null;
        }
        return m_res;
    }

    @Override
    public String toString()
    {
        if (m_virtual)
        {
            return super.toString();
        }
        return m_res.toString();
    }

    @Override
    public int hashCode()
    {
        if (m_virtual)
        {
            return super.hashCode();
        }
        return m_res.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return hashCode() == obj.hashCode();
    }

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

    public Document getDomProperty(SemanticProperty prop)
    {
        Document dom=(Document)m_cachepropsrel.get(prop.getURI());
        if(dom==null)
        {
            String xml=getProperty(prop);
            if(xml!=null)
            {
                dom=SWBUtils.XML.xmlToDom(xml);
            }else
            {
                dom=SWBUtils.XML.getNewDocument();
            }
            m_cachepropsrel.put(prop.getURI(), dom);
        }
        return dom;
    }


    public SemanticLiteral getLiteralProperty(SemanticProperty prop)
    {
        return getLiteralProperty(prop, null);
    }

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
                if(lang!=null)
                {
                    stm=getLocaleStatement(prop,lang);
                }else
                {
                    stm = m_res.getProperty(prop.getRDFProperty());
                }
                if (stm != null)
                {
                    ret = new SemanticLiteral(stm);
                }
                setPropertyValueCache(prop, lang, ret);
            }
        }
        return ret;
    }

    public void setLiteralProperty(SemanticProperty prop, SemanticLiteral literal)
    {
        if(!m_virtual)
        {
            Object obj=literal.getValue();
            String lang=literal.getLanguage();
            Statement stm = null;
            if(lang!=null)
            {
                stm=getLocaleStatement(prop,lang);
            }else
            {
                stm = m_res.getProperty(prop.getRDFProperty());
            }
            if (stm != null)
            {
                if(obj instanceof String)
                {
                    if(lang!=null)
                    {
                        stm.changeObject((String)obj,lang);
                    }else
                    {
                        stm.changeObject((String)obj);
                    }
                }else if(obj instanceof Boolean)
                {
                    stm.changeLiteralObject((Boolean)obj);
                }else if(obj instanceof Character)
                {
                    stm.changeLiteralObject((Character)obj);
                }else if(obj instanceof Double)
                {
                    stm.changeLiteralObject((Double)obj);
                }else if(obj instanceof Float)
                {
                    stm.changeLiteralObject((Float)obj);
                }else if(obj instanceof Integer)
                {
                    stm.changeLiteralObject((Integer)obj);
                }else if(obj instanceof Long)
                {
                    stm.changeLiteralObject((Long)obj);
                }else if(obj instanceof java.util.Date)
                {
                    stm.changeObject(SWBUtils.TEXT.iso8601DateFormat((java.util.Date)obj));
                }
            }
            else
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
        setPropertyValueCache(prop, literal.getLanguage(), literal);
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, "SET");
    }

    public SemanticObject removeProperty(SemanticProperty prop)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, "REMOVE");
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
                        SWBPlatform.removeFileFromWorkPath(workPath);
                    }
                }
            }catch(Exception e){log.error(e);}

            Property iprop = prop.getRDFProperty();
            m_res.removeAll(iprop);
        }
        removePropertyValueCache(prop, null);
        return this;
    }

    public SemanticObject removeProperty(SemanticProperty prop, String lang)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, "REMOVE");
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
        }
        removePropertyValueCache(prop, lang);
        return this;
    }

    public SemanticObject setObjectProperty(SemanticProperty prop, SemanticObject object)
    {
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
        if (stm != null)
        {
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
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, "SET");
        return this;
    }

    public SemanticObject addObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, "ADD");
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
        return this;
    }

    public SemanticObject removeObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        SWBPlatform.getSemanticMgr().notifyChange(this, prop, "REMOVE");
        if (m_virtual)
        {
            ArrayList list = (ArrayList) m_virtprops.get(prop.getURI());
            if (list != null)
            {
                list.remove(object);
            }
            return this;
        }
        StmtIterator it = m_res.listProperties(prop.getRDFProperty());
        while (it.hasNext())
        {
            Statement stmt = it.nextStatement();
            if (object.getRDFResource().equals(stmt.getResource()))
            {
                stmt.remove();
            }
        }
        removePropertyValueCache(prop, "list");
        return this;
    }

    public Iterator<SemanticLiteral> listLiteralProperties(SemanticProperty prop)
    {
        if (m_virtual)
        {
            //TODO:
            return null;
        }
        return new SemanticLiteralIterator(m_res.listProperties(prop.getRDFProperty()));
    }

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

    private Iterator<SemanticObject> filterValidObjects(Iterator<SemanticObject> it)
    {
        SemanticClass cls=null;
        SemanticProperty valid=null;
        ArrayList list=new ArrayList();
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            boolean add=true;
            if(cls==null)
            {
                cls=obj.getSemanticClass();
                valid=cls.getProperty("valid");
            }
            if(valid!=null)
            {
                if(!obj.getBooleanProperty(valid))add=false;
            }
            if(add)list.add(obj);
        }
        return list.iterator();
    }

    /**
     * Regresa lista de objetos activos y no borrados relacionados por la propiedad
     * Si no encuentra en el objeto busca en los padres
     * @param prop
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

    public SemanticObject getObjectProperty(SemanticProperty prop)
    {
        return getObjectProperty(prop, null);
    }

    public SemanticObject getObjectProperty(SemanticProperty prop, SemanticObject defValue)
    {
        SemanticObject ret = defValue;
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
            Statement stm = m_res.getProperty(prop.getRDFProperty());
            if (stm != null)
            {
                try
                {
                    ret = SemanticObject.createSemanticObject(stm.getResource());
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            setPropertyValueCache(prop, null, ret);
        }
        return ret;
    }


    private Statement getLocaleStatement(SemanticProperty prop, String lang)
    {
        StmtIterator stit = m_res.listProperties(prop.getRDFProperty());
        Statement st = null;
        while (stit.hasNext())
        {
            Statement staux = stit.nextStatement();
            String lg = staux.getLanguage();
            if (lg != null && lg.equals(lang))
            {
                st = staux;
                break;
            }
        }
        return st;
    }

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
                String name = prop.getLabel();
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

    private Object externalInvokerSet(SemanticProperty prop, Object... values)
    {
        //System.out.println("externalInvokerSet:"+prop+" "+values);
        Object ret = null;
        if (!m_virtual)
        {
            GenericObject obj = this.createGenericInstance();
            Class cls = obj.getClass();
            Method method=extSetMethods.get(cls.getName()+"-"+prop.getURI());
            if(method==null)
            {
                String name = prop.getLabel();
                if (name == null)
                {
                    name = prop.getName();
                }
                name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                try
                {
                    Class types[]=new Class[values.length];
                    for(int x=0;x<values.length;x++)
                    {
                        types[x]=values[x].getClass();
                        Class pri=wrapperToPrimitive.get(types[x]);
                        if(pri!=null)
                        {
                            types[x]=pri;
                        }
                    }
                    //System.out.println("getMethod:"+name+" "+types);
                    method = cls.getMethod(name,types);
                    extSetMethods.put(cls.getName()+"-"+prop.getURI(), method);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            try
            {
                ret = method.invoke(obj,values);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        //System.out.println("externalInvoker:"+ret);
        }
        return ret;
    }

    public void removeDependencies()
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
                            dep.remove();
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
                            dep.remove();
                        }catch(Exception e){log.error(e);}
                    }
                }
            }
        }
    }

    public void remove()
    {
        if(getModel().getModelObject().equals(this))    //es un modelo
        {
            removeDependencies();
            SWBPlatform.getSemanticMgr().removeModel(getId());
            SWBPlatform.getSemanticMgr().notifyChange(this, null, "REMOVE");
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
            SWBPlatform.getSemanticMgr().notifyChange(this, null, "REMOVE");

            //TODO:mejorar
            Iterator<SemanticObject> rel=listRelatedObjects();
            while(rel.hasNext())
            {
                SemanticObject obj=rel.next();
                obj.resetCache();
            }

            //Eliminar dependencias
            removeDependencies();

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

/******************************************************************************************************************/

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value, boolean evalExtInvo)
    {
        return setProperty(prop, value,null,evalExtInvo);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value, String lang)
    {
        return setProperty(prop, value, lang, true);
    }


    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
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
     * Regresa valor de la Propiedad especificada
     * @param prop
     * @return valor de la propiedad, si no existe la propiedad regresa null
     */
    public String getProperty(SemanticProperty prop)
    {
        return getProperty(prop, true);
    }

    /**
     * Regresa valor de la Propiedad especificada
     * @param prop
     * @return valor de la propiedad, si no existe la propiedad regresa null
     */
    public String getProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getProperty(prop, null,evalExtInvo);
    }


    public String getProperty(SemanticProperty prop, String defValue)
    {
        return getProperty(prop,defValue, true);
    }

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
            SemanticLiteral lit=getLiteralProperty(prop);
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

    public int getIntProperty(SemanticProperty prop)
    {
        return getIntProperty(prop, true);
    }

    public int getIntProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getIntProperty(prop, 0, evalExtInvo);
    }

    public int getIntProperty(SemanticProperty prop, int defValue)
    {
        return getIntProperty(prop, defValue, true);
    }

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setIntProperty(SemanticProperty prop, int value)
    {
        return setIntProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
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

    public long getLongProperty(SemanticProperty prop)
    {
        return getLongProperty(prop, true);
    }

    public long getLongProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getLongProperty(prop, 0L, evalExtInvo);
    }

    public long getLongProperty(SemanticProperty prop, long defValue)
    {
        return getLongProperty(prop, defValue, true);
    }

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

    public SemanticObject setInputStreamProperty(SemanticProperty prop, InputStream value, String name) throws SWBException
    {
        String workPath = this.getWorkPath();
        if (!(workPath.endsWith("\\") || workPath.equals("/")))
        {
            workPath += "/" + name;
        }
        setProperty(prop, name);
        SWBPlatform.writeFileToWorkPath(workPath, value, "");
        return this;
    }

    public InputStream getInputStreamProperty(SemanticProperty prop) throws SWBException
    {
        String value = getProperty(prop);
        String workPath = this.getWorkPath();
        if (!(workPath.endsWith("\\") || workPath.equals("/")))
        {
            workPath += "/" + value;
        }
        return SWBPlatform.getFileFromWorkPath(workPath);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setLongProperty(SemanticProperty prop, long value)
    {
        return setLongProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
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

    public float getFloatProperty(SemanticProperty prop)
    {
        return getFloatProperty(prop, true);
    }

    public float getFloatProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getFloatProperty(prop, 0F, evalExtInvo);
    }

    public float getFloatProperty(SemanticProperty prop, float defValue)
    {
        return getFloatProperty(prop, defValue, true);
    }

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setFloatProperty(SemanticProperty prop, float value)
    {
        return setFloatProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
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

    public double getDoubleProperty(SemanticProperty prop)
    {
        return getDoubleProperty(prop, true);
    }

    public double getDoubleProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getDoubleProperty(prop, 0D,evalExtInvo);
    }

    public double getDoubleProperty(SemanticProperty prop, double defValue)
    {
        return getDoubleProperty(prop, defValue, true);
    }

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDoubleProperty(SemanticProperty prop, double value)
    {
        return setDoubleProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
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

    public boolean getBooleanProperty(SemanticProperty prop)
    {
        return getBooleanProperty(prop, true);
    }

    public boolean getBooleanProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getBooleanProperty(prop, false, evalExtInvo);
    }

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setBooleanProperty(SemanticProperty prop, boolean value)
    {
        return setBooleanProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
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


    public java.util.Date getDateProperty(SemanticProperty prop)
    {
        return getDateProperty(prop, true);
    }

    public java.util.Date getDateProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getDateProperty(prop, null, evalExtInvo);
    }

    public java.util.Date getDateProperty(SemanticProperty prop, java.util.Date defValue)
    {
        return getDateProperty(prop, defValue, true);
    }

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateProperty(SemanticProperty prop, java.util.Date value)
    {
        return setDateProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateProperty(SemanticProperty prop, java.util.Date value, boolean evalExtInvo)
    {
        if (evalExtInvo && prop.isExternalInvocation())
        {
            externalInvokerSet(prop,value);
        }else
        {
            setLiteralProperty(prop, new SemanticLiteral(new Timestamp(value.getTime())));
        }
        return this;
    }


    public Date getSQLDateProperty(SemanticProperty prop)
    {
        return getSQLDateProperty(prop, true);
    }

    public Date getSQLDateProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getSQLDateProperty(prop, null, evalExtInvo);
    }

    public Date getSQLDateProperty(SemanticProperty prop, Date defValue)
    {
        return getSQLDateProperty(prop, defValue, true);
    }

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setSQLDateProperty(SemanticProperty prop, Date value)
    {
        return setSQLDateProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
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

    public Timestamp getDateTimeProperty(SemanticProperty prop)
    {
        return getDateTimeProperty(prop, true);
    }

    public Timestamp getDateTimeProperty(SemanticProperty prop, boolean evalExtInvo)
    {
        return getDateTimeProperty(prop, null, evalExtInvo);
    }

    public Timestamp getDateTimeProperty(SemanticProperty prop, Timestamp defValue)
    {
        return getDateTimeProperty(prop, defValue, true);
    }

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
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateTimeProperty(SemanticProperty prop, Timestamp value)
    {
        return setDateTimeProperty(prop, value, true);
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateTimeProperty(SemanticProperty prop, Timestamp value, boolean evalExtInvo)
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

    public String getDisplayName()
    {
        return getDisplayName(null);
    }

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
                ret = obj.getDisplayName(lang);
            }
        }
        else
        {
            ret = getLocaleProperty(getModel().getSemanticProperty(SemanticVocabulary.RDFS_LABEL), lang);
            if(ret==null)
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
            }
        }
        return ret;
    }

    public SemanticProperty transformToSemanticProperty()
    {
        SemanticProperty ret = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(getURI());
        return ret;
    }

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

    public Iterator<SemanticObject> listHerarquicalChilds()
    {
        ArrayList<SemanticObject> list=new ArrayList();
        Iterator<SemanticProperty> it=getSemanticClass().listHerarquicalProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            Iterator<SemanticObject> it2=listObjectProperties(prop);
            while(it2.hasNext())
            {
                SemanticObject ch=it2.next();
                list.add(ch);
            }
        }
        return list.iterator();
    }

    public Iterator<SemanticObject> listHerarquicalParents()
    {
        ArrayList<SemanticObject> list=new ArrayList();
        Iterator<SemanticProperty> it=getSemanticClass().listInverseHerarquicalProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            Iterator<SemanticObject> it2=listObjectProperties(prop);
            while(it2.hasNext())
            {
                SemanticObject ch=it2.next();
                list.add(ch);
            }
        }
        return list.iterator();
    }
    
    public SemanticObject getHerarquicalParent()
    {
        SemanticObject ret=null;
        Iterator<SemanticObject> it=listHerarquicalParents();
        if(it.hasNext())ret=it.next();
        return ret;
    }

    public String getWorkPath()
    {
        return "/models/"+getModel().getName()+"/"+getSemanticClass().getClassId()+"/"+getId();
    }

}

