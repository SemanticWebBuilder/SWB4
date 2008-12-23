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
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.base.GenericObjectBase;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SemanticObject
{
    private static Logger log = SWBUtils.getLogger(SemanticObject.class);

    private static HashMap<String, SemanticObject>m_objs=new HashMap();

    private Resource m_res = null;
    private SemanticModel m_model = null;
    //Virtual properties
    private SemanticClass m_cls = null;
    private boolean m_virtual = false;
    private HashMap m_virtprops;

    private HashMap m_cacheprops;

    private static String NULL="__NULL__";

    private static boolean hasCache=true;
    private static boolean hasPropertyCache=true;

    private SemanticObject(Resource res)
    {
        m_cacheprops=new HashMap();
        this.m_res = res;
        validateModel();
        //System.out.println("SemanticObject:"+res);
    }

    public static SemanticObject getSemanticObject(String uri)
    {
        SemanticObject ret=null;
        if(hasCache)ret=m_objs.get(uri);
        return ret;
    }


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
        SemanticObject ret=getSemanticObject(res.getURI());
        if(ret==null)
        {
            if(hasCache)
            {
                if(res.getModel()==SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel())
                {
                    res=SWBPlatform.getSemanticMgr().getOntology().getResource(res.getURI());
                }
            }
            ret=new SemanticObject(res);
            m_objs.put(res.getURI(), ret);
        }
        return ret;
    }

    public static void removeCache(String uri)
    {
        m_objs.remove(uri);
    }

    private void setPropertyValueCache(SemanticProperty prop, String lang, Object value)
    {
        if(value==null)value=NULL;
        if(value!=null)
            m_cacheprops.put(prop.getURI()+"|"+lang, value);
    }

    private Object getPropertyValueCache(SemanticProperty prop, String lang)
    {
        Object ret=null;
        if(hasPropertyCache)ret=m_cacheprops.get(prop.getURI()+"|"+lang);
        //if(ret==NULL)ret=null;
        return ret;
    }

    private void clearPropertyValueCache(SemanticProperty prop, String lang)
    {
        m_cacheprops.remove(prop.getURI()+"|"+lang);
    }

    private void validateModel()
    {
        String ns = getModel().getNameSpace();
        if (ns != null && !m_res.getURI().startsWith(ns))
        {
            m_res = SWBPlatform.getSemanticMgr().getOntology().getResource(m_res.getURI());
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
        if (m_virtual)
        {
            return null;
        }
        String uri = getURI();
        int x = uri.indexOf('#');
        if (x > -1)
        {
            return uri.substring(x + 1);
        }
        return uri;
    }

    /**
     * Regreasa SemanticID compuesto por /[model name]/[cls name]/[id]
     * @return
     */
    public String getSID()
    {
        return "/" + getModel().getName() + "/" + getSemanticClass().getName() + "/" + getId();
    }

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
            StmtIterator stmit = m_res.listProperties(m_res.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
            while(stmit.hasNext())
            {
                Statement stm=stmit.nextStatement();
                try
                {
                    m_cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
                    if(m_cls!=null)break;
                }
                catch (Exception e)
                {
                    log.error(e);
                }
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
        return new SemanticClassIterator<SemanticClass>(m_res.listProperties(m_res.getModel().getProperty(SemanticVocabulary.RDF_TYPE)));
    }

    public void addSemanticClass(SemanticClass cls)
    {
        if (m_virtual)
        {
            //TODO:
        }
        else
        {
            m_res.addProperty(m_res.getModel().getProperty(SemanticVocabulary.RDF_TYPE), cls.getOntClass());
        }
    }

    public SemanticObject removeSemanticClass(SemanticClass cls)
    {
        if (m_virtual)
        {
            //TODO:
        }
        else if (m_res != null)
        {
            StmtIterator stit = m_res.listProperties(m_res.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
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
    }

    public SemanticObject removeProperty(SemanticProperty prop)
    {
        if (!m_virtual)
        {
            try
            {
                if (prop.isBinary())
                {
                    String value = getProperty(prop);
                    if (value != null)
                    {
                        GenericObjectBase obj = new GenericObjectBase(this);
                        String workPath = obj.getWorkPath();
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
        clearPropertyValueCache(prop, null);
        return this;
    }

    public SemanticObject removeProperty(SemanticProperty prop, String lang)
    {
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
        clearPropertyValueCache(prop, lang);
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
        setPropertyValueCache(prop, null, object);
        return this;
    }

    public SemanticObject addObjectProperty(SemanticProperty prop, SemanticObject object)
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
        return this;
    }

    public SemanticObject removeObjectProperty(SemanticProperty prop, SemanticObject object)
    {
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

        Iterator ret = null;
        if (!prop.hasInverse())
        {
            ret = new SemanticIterator(m_res.listProperties(prop.getRDFProperty()));
        }
        else
        {
            ret = new SemanticIterator(getModel().getRDFModel().listStatements(null, prop.getInverse().getRDFProperty(), getRDFResource()), true);
        }
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
        Object ret = null;
        if (!m_virtual)
        {
            GenericObject obj = getSemanticClass().newGenericInstance(this);
            Class cls = obj.getClass();
            String name = prop.getLabel();
            if (name == null)
            {
                name = prop.getName();
            }
            name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            try
            {
                Method method = cls.getMethod(name);
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

    public void remove()
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
        Resource res=getRDFResource();
        if(res!=null)
        {
            SemanticModel model=getModel();
            model.getRDFModel().removeAll(res,null,null);
            model.getRDFModel().removeAll(null,null,res);
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
        setProperty(prop, value,null);
        return this;
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value, String lang)
    {
        setLiteralProperty(prop, new SemanticLiteral(value, lang));
        return this;
    }

    /**
     * Regresa valor de la Propiedad especificada
     * @param prop
     * @return valor de la propiedad, si no existe la propiedad regresa null
     */
    public String getProperty(SemanticProperty prop)
    {
        return getProperty(prop, null);
    }

    public String getProperty(SemanticProperty prop, String defValue)
    {
        String ret = null;
        if (prop.isExternalInvocation())
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
        return getIntProperty(prop, 0);
    }

    public int getIntProperty(SemanticProperty prop, int defValue)
    {
        Integer ret=defValue;
        if (prop.isExternalInvocation())
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
        setLiteralProperty(prop, new SemanticLiteral(new Integer(value)));
        return this;
    }

    public long getLongProperty(SemanticProperty prop)
    {
        return getLongProperty(prop, 0L);
    }


    public long getLongProperty(SemanticProperty prop, long defValue)
    {
        Long ret=defValue;
        if (prop.isExternalInvocation())
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
        GenericObjectBase obj = new GenericObjectBase(this);
        String workPath = obj.getWorkPath();
        if (!(workPath.endsWith("\\") || workPath.equals("/")))
        {
            workPath += "/" + name;
        }
        SWBPlatform.writeFileToWorkPath(workPath, value, "");
        return this.setProperty(prop, name);
    }

    public InputStream getInputStreamProperty(SemanticProperty prop) throws SWBException
    {
        String value = getProperty(prop);
        GenericObjectBase obj = new GenericObjectBase(this);
        String workPath = obj.getWorkPath();
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
        setLiteralProperty(prop, new SemanticLiteral(new Long(value)));
        return this;
    }

    public float getFloatProperty(SemanticProperty prop)
    {
        return getFloatProperty(prop, 0F);
    }

    public float getFloatProperty(SemanticProperty prop, float defValue)
    {
        Float ret=defValue;
        if (prop.isExternalInvocation())
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
        setLiteralProperty(prop, new SemanticLiteral(new Float(value)));
        return this;
    }

    public double getDoubleProperty(SemanticProperty prop)
    {
        return getDoubleProperty(prop, 0D);
    }

    public double getDoubleProperty(SemanticProperty prop, double defValue)
    {
        Double ret=defValue;
        if (prop.isExternalInvocation())
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
        setLiteralProperty(prop, new SemanticLiteral(new Double(value)));
        return this;
    }

    public boolean getBooleanProperty(SemanticProperty prop)
    {
        return getBooleanProperty(prop, false);
    }

    public boolean getBooleanProperty(SemanticProperty prop, boolean defValue)
    {
        Boolean ret=defValue;
        if (prop.isExternalInvocation())
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
        setLiteralProperty(prop, new SemanticLiteral(new Boolean(value)));
        return this;
    }


    public java.util.Date getDateProperty(SemanticProperty prop)
    {
        return getSQLDateProperty(prop, null);
    }

    public java.util.Date getDateProperty(SemanticProperty prop, java.util.Date defValue)
    {
        java.util.Date ret=defValue;
        if (prop.isExternalInvocation())
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
        setLiteralProperty(prop, new SemanticLiteral(new Timestamp(value.getTime())));
        return this;
    }


    public Date getSQLDateProperty(SemanticProperty prop)
    {
        return getSQLDateProperty(prop, null);
    }

    public Date getSQLDateProperty(SemanticProperty prop, Date defValue)
    {
        Date ret=defValue;
        if (prop.isExternalInvocation())
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
        setLiteralProperty(prop, new SemanticLiteral(value));
        return this;
    }

    public Timestamp getDateTimeProperty(SemanticProperty prop)
    {
        return getDateTimeProperty(prop, null);
    }

    public Timestamp getDateTimeProperty(SemanticProperty prop, Timestamp defValue)
    {
        Timestamp ret=defValue;
        if (prop.isExternalInvocation())
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
        setLiteralProperty(prop, new SemanticLiteral(value));
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
        if (cls != null)
        {
            SemanticProperty prop = cls.getDisplayNameProperty();
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
                    ret=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getNsURIPrefix(getModel().getNameSpace()); //prefijo
                    ret+=":"+getId();
                }
            }
        }
        else
        {
            log.warn("Object does not have cls:" + getURI());
        }
        return ret;
    }

    public SemanticProperty transformToSemanticProperty()
    {
        SemanticProperty ret = null;
        Property pro = getModel().getRDFModel().getProperty(getURI());
        if (pro != null)
        {
            ret = new SemanticProperty(pro);
        }
        return ret;
    }

    public Iterator<SemanticObject> listRelatedObjects()
    {
        StmtIterator stit = getModel().getRDFModel().listStatements(null, null, getRDFResource());
        return new SemanticIterator(stit, true);
    }

}

