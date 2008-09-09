package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SemanticObject
{
    private static Logger log = SWBUtils.getLogger(SemanticObject.class);
    
    Resource m_res=null;
    SemanticModel m_model=null;
    
    //Virtual properties
    private SemanticClass m_virtclass=null;
    private boolean m_virtual=false;
    private HashMap m_virtprops;
    
//    public SemanticObject(String uri, SemanticModel model)
//    {
//        m_res=model.getRDFModel().createResource(uri);
//        m_model=model;
//    }
    
    
    public SemanticObject(Resource res)
    {
        this.m_res=res;
        validateModel();
    }
    
    private void validateModel()
    {
        String ns=getModel().getNameSpace();
        if(ns!=null && !m_res.getURI().startsWith(ns))
        {
            m_res=SWBPlatform.getSemanticMgr().getOntology().getResource(m_res.getURI());
            m_model=null;
        }
    }
    
    public boolean isVirtual()
    {
        return m_virtual;
    }
    
    /**
     * Contruye un SemanticObject virtual relacionado al Model y al tipo de elemento
     * 
     * @param model
     */
    public SemanticObject(SemanticModel model, SemanticClass cls)
    {
        m_model=model;
        m_virtclass=cls;
        m_virtual=true;
        m_virtprops=new HashMap();
    }    
    
    public String getURI()
    {
        if(m_virtual)return null;
        return m_res.getURI();
    }
    
    public String getRDFName()
    {
        if(m_virtual)return null;
        return m_res.getLocalName();
    }    
    
    public SemanticClass getSemanticClass()
    {
        if(m_virtual)
        {
            return m_virtclass;
        }        
        Statement stm=m_res.getProperty(m_res.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            try
            {
                return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
            }catch(Exception e){log.error(e);}
        }
        return null;
    }
    
    /**
     * Regresa el Modelo de del SemanticObject
     * @return
     */
    public SemanticModel getModel()
    {
        if(m_model==null)
        {
            m_model=SWBPlatform.getSemanticMgr().getModel(m_res.getModel());
        }
        return m_model;
    }
    
    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setProperty(SemanticProperty prop, String value)
    {
        if(m_virtual)
        {
            m_virtprops.put(prop.getURI(), value);
            return this;
        }
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            stm.changeObject(value);
        }else
        {
            m_res.addProperty(prop.getRDFProperty(), value);
        }
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
        if(m_virtual)
        {
            m_virtprops.put(prop.getURI()+"|"+lang, value);
            return this;
        }        
        Statement stm=getLocaleStatement(prop, lang);
        if(stm!=null)
        {
            stm.changeObject(value,lang);
        }else
        {
            m_res.addProperty(prop.getRDFProperty(), value, lang);
        }        
        return this;
    }    
    
    private Statement getLocaleStatement(SemanticProperty prop, String lang)
    {
        StmtIterator stit=m_res.listProperties(prop.getRDFProperty());
        Statement st=null;
        while(stit.hasNext())
        {
            Statement staux=stit.nextStatement();
            if(staux.getLanguage().equals(lang))
            {
                st=staux;
                break;
            }
        }       
        return st;
    }
    
    public SemanticObject removeProperty(SemanticProperty prop)
    {
        if(m_virtual)
        {
            m_virtprops.remove(prop.getURI());
            return this;
        }        
        if(m_res==null)return this;        
        Property iprop=prop.getRDFProperty();
        m_res.removeAll(iprop);
        return this;
    }    
    
    public SemanticObject removeProperty(SemanticProperty prop, String lang)
    {
        if(m_virtual)
        {
            m_virtprops.remove(prop.getURI()+"|"+lang);
            return this;
        }        StmtIterator stit=m_res.listProperties(prop.getRDFProperty());
        while(stit.hasNext())
        {
            Statement staux=stit.nextStatement();
            if(staux.getLanguage().equals(lang))
            {
                stit.remove();
            }
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
        return getProperty(prop,null);
    }
    
    public String getProperty(SemanticProperty prop, String defValue)
    {
        if(m_virtual)
        {
            String ret=(String)m_virtprops.get(prop.getURI());
            if(ret==null)ret=defValue;
            return ret;
        }        
        String ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getString();
        }
        return ret;
    }
    
    public String getProperty(SemanticProperty prop, String defValue, String lang)
    {
        if(m_virtual)
        {
            String ret=(String)m_virtprops.get(prop.getURI()+"|"+lang);
            if(ret==null)ret=defValue;
            return ret;
        }        
        String ret=defValue;
        Statement stm=getLocaleStatement(prop, lang);
        if(stm!=null)
        {
            ret=stm.getString();
        }
        return ret;
    }
    
    public int getIntProperty(SemanticProperty prop)
    {
        return getIntProperty(prop,0);
    }
    
    public int getIntProperty(SemanticProperty prop, int defValue)
    {
        if(m_virtual)
        {
            Integer ret=(Integer)m_virtprops.get(prop.getURI());
            if(ret==null)ret=defValue;
            return ret;
        }        
        int ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getInt();
        }
        return ret;
    }   
    
    public long getLongProperty(SemanticProperty prop)
    {    
        return getLongProperty(prop,0L);
    }
    
    public long getLongProperty(SemanticProperty prop, long defValue)
    {
        if(m_virtual)
        {
            Long ret=(Long)m_virtprops.get(prop.getURI());
            if(ret==null)ret=defValue;
            return ret;
        }        
        long ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getLong();
        }
        return ret;
    }    
    
    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setLongProperty(SemanticProperty prop, long value)
    {
        if(m_virtual)
        {
            m_virtprops.put(prop.getURI(), (Long)value);
            return this;
        }            
        Property iprop=prop.getRDFProperty();
        Statement stm=m_res.getProperty(iprop);
        if(stm!=null)
        {
            stm.changeLiteralObject(value);
        }else
        {
            m_res.addLiteral(iprop, value);
        }
        return this;
    }     

    public float getFloatProperty(SemanticProperty prop)
    {
        return getFloatProperty(prop, 0F);
    }
    
    public float getFloatProperty(SemanticProperty prop, float defValue)
    {
        if(m_virtual)
        {
            Float ret=(Float)m_virtprops.get(prop.getURI());
            if(ret==null)ret=defValue;
            return ret;
        }        
        float ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getFloat();
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
        if(m_virtual)
        {
            m_virtprops.put(prop.getURI(), (Float)value);
            return this;
        }           
        Property iprop=prop.getRDFProperty();
        Statement stm=m_res.getProperty(iprop);
        if(stm!=null)
        {
            stm.changeLiteralObject(value);
        }else
        {
            m_res.addLiteral(iprop, value);
        }
        return this;
    }     
    
    public double getDoubleProperty(SemanticProperty prop)
    {
        return getDoubleProperty(prop, 0D);
    }   
    
    public double getDoubleProperty(SemanticProperty prop, double defValue)
    {
        if(m_virtual)
        {
            Double ret=(Double)m_virtprops.get(prop.getURI());
            if(ret==null)ret=defValue;
            return ret;
        }             
        double ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getDouble();
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
        if(m_virtual)
        {
            m_virtprops.put(prop.getURI(), (Double)value);
            return this;
        }           
        Property iprop=prop.getRDFProperty();
        Statement stm=m_res.getProperty(iprop);
        if(stm!=null)
        {
            stm.changeLiteralObject(value);
        }else
        {
            m_res.addLiteral(iprop, value);
        }
        return this;
    }       
    
    public boolean getBooleanProperty(SemanticProperty prop)
    {
        return getBooleanProperty(prop, false);
    }   
    
    public boolean getBooleanProperty(SemanticProperty prop, boolean defValue)
    {
        if(m_virtual)
        {
            Boolean ret=(Boolean)m_virtprops.get(prop.getURI());
            if(ret==null)ret=defValue;
            return ret;
        }             
        boolean ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getBoolean();
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
        if(m_virtual)
        {
            m_virtprops.put(prop.getURI(), (Boolean)value);
            return this;
        }           
        Property iprop=prop.getRDFProperty();
        Statement stm=m_res.getProperty(iprop);
        if(stm!=null)
        {
            stm.changeLiteralObject(value);
        }else
        {
            m_res.addLiteral(iprop, value);
        }
        return this;
    }     
    
    public Date getDateProperty(SemanticProperty prop)
    {
        return getDateProperty(prop, null);
    }   
    
    public Date getDateProperty(SemanticProperty prop, Date defValue)
    {
        if(m_virtual)
        {
            Date ret=(Date)m_virtprops.get(prop.getURI());
            if(ret==null)ret=defValue;
            return ret;
        }             
        Date ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            try
            {
                ret=SWBUtils.TEXT.iso8601DateParse(stm.getString());
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }    
    
    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public SemanticObject setDateProperty(SemanticProperty prop, Date value)
    {
        if(m_virtual)
        {
            m_virtprops.put(prop.getURI(), (Date)value);
            return this;
        }           
        Property iprop=prop.getRDFProperty();
        Statement stm=m_res.getProperty(iprop);
        if(stm!=null)
        {
            stm.changeObject(SWBUtils.TEXT.iso8601DateFormat(value));
        }else
        {
            m_res.addProperty(iprop, SWBUtils.TEXT.iso8601DateFormat(value));
        }
        return this;
    }      
    
    public SemanticObject setObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        if(m_virtual)
        {
            ArrayList list=(ArrayList)m_virtprops.get(prop.getURI());
            if(list==null)
            {
                list=new ArrayList();
                m_virtprops.put(prop.getURI(),list);
            }
            list.clear();
            list.add(object);
            return this;
        }           
        Property iprop=prop.getRDFProperty();
        Statement stm=m_res.getProperty(iprop);
        if(stm!=null)
        {
            stm.changeObject(object.getRDFResource());
        }else
        {
            m_res.addProperty(iprop, object.getRDFResource());
        }
        return this;        
    }    
    
    public SemanticObject addObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        if(m_virtual)
        {
            ArrayList list=(ArrayList)m_virtprops.get(prop.getURI());
            if(list==null)
            {
                list=new ArrayList();
                m_virtprops.put(prop.getURI(),list);
            }
            list.add(object);
            return this;
        }           
        Property iprop=prop.getRDFProperty();
        m_res.addProperty(iprop, object.getRDFResource());
        return this;
    }    
    
    public SemanticObject removeObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        if(m_virtual)
        {
            ArrayList list=(ArrayList)m_virtprops.get(prop.getURI());
            if(list!=null)
            {
                list.remove(object);
            }
            return this;
        }          
        StmtIterator it=m_res.listProperties(prop.getRDFProperty());
        while(it.hasNext())
        {
            Statement stmt=it.nextStatement();
            if(object.getRDFResource().equals(stmt.getResource()))
            {
                stmt.remove();
            }
        }
        return this;
    }    
    
    public Iterator<SemanticObject> listObjectProperties(SemanticProperty prop)
    {
        if(m_virtual)
        {
            ArrayList list=(ArrayList)m_virtprops.get(prop.getURI());
            if(list!=null)
            {
                return list.iterator();
            }
            else
            {
                return new ArrayList().iterator();
            }
        }            
        return new SemanticObjectIterator(m_res.listProperties(prop.getRDFProperty()));
    }
    
    public SemanticObject getObjectProperty(SemanticProperty prop)
    {
        return getObjectProperty(prop,null);
    }
    
    public SemanticObject getObjectProperty(SemanticProperty prop, SemanticObject defValue)
    {
        SemanticObject ret=defValue;
        if(m_virtual)
        {
            ArrayList<SemanticObject> arr=((ArrayList)m_virtprops.get(prop.getURI()));
            if(!arr.isEmpty())
            {
                ret=(SemanticObject)arr.get(0);
            }
            return ret;
        }             
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            try
            {
                ret=new SemanticObject(stm.getResource());
            }catch(Exception e){log.error(e);}
        }
        return ret;
    }    
    
    
    public Resource getRDFResource()
    {
        if(m_virtual)return null;
        return m_res;
    }
    
    @Override
    public String toString()
    {
        if(m_virtual)return super.toString();
        return m_res.toString();
    }

    @Override
    public int hashCode() 
    {
        if(m_virtual)return super.hashCode();
        return m_res.hashCode();
    }

    @Override
    public boolean equals(Object obj) 
    {
        return hashCode()==obj.hashCode();
    }
    
    public boolean instanceOf(SemanticClass cls)
    {
        boolean ret=false;
        SemanticClass cl=getSemanticClass();
        if(cl!=null && (cl.equals(cls) || cl.isSubClass(cls)))
        {
            ret=true;
        }
        return ret;
    }    
    
}

