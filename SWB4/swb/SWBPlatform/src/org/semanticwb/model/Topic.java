package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Date;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBContext;
import org.semanticwb.platform.SWBVocabulary;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class Topic 
{
    private static Logger log = SWBUtils.getLogger(Topic.class);
    
    Resource m_res=null;
    
    public Topic(String uri, Model model)
    {
        m_res=model.createResource(uri);
    }
    
    public Topic(Resource res)
    {
        this.m_res=res;
    }
    
    public String getURI()
    {
        return m_res.getURI();
    }
    
    public String getName()
    {
        return m_res.getLocalName();
    }    
    
    public TopicClass getTopicClass()
    {
        Statement stm=m_res.getProperty(m_res.getModel().getProperty(SWBVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            try
            {
                return SWBContext.getSemanticMgr().getVocabulary().getTopicClass(stm.getResource().getURI());
            }catch(Exception e){log.error(e);}
        }
        return null;
    }
    
    /**
     * Regresa el Modelo de del Topico
     * @return
     */
    public Model getModel()
    {
        return m_res.getModel();
    }
    
    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return Topic para cascada
     */
    public Topic setProperty(TopicProperty prop, String value)
    {
        Property iprop=prop.getRDFProperty();
        if(value==null)
        {
            m_res.removeAll(iprop);
        }else
        {
            Statement stm=m_res.getProperty(iprop);
            if(stm!=null)
            {
                stm.changeObject(value);
            }else
            {
                m_res.addProperty(iprop, value);
            }
        }
        return this;
    }
    
    public Topic removeProperty(TopicProperty prop)
    {
        Property iprop=prop.getRDFProperty();
        m_res.removeAll(iprop);
        return this;
    }    
    
    /**
     * Regresa valor de la Propiedad especificada
     * @param prop
     * @return valor de la propiedad, si no existe la propiedad regresa null
     */
    public String getProperty(TopicProperty prop)
    {
        return getProperty(prop,null);
    }
    
    public String getProperty(TopicProperty prop, String defValue)
    {
        String ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getString();
        }
        return ret;
    }
    
    public int getIntProperty(TopicProperty prop)
    {
        return getIntProperty(prop,0);
    }
    
    public int getIntProperty(TopicProperty prop, int defValue)
    {
        int ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
        if(stm!=null)
        {
            ret=stm.getInt();
        }
        return ret;
    }   
    
    public long getLongProperty(TopicProperty prop)
    {    
        return getLongProperty(prop,0L);
    }
    
    public long getLongProperty(TopicProperty prop, long defValue)
    {
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
     * @return Topic para cascada
     */
    public Topic setLongProperty(TopicProperty prop, long value)
    {
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

    public float getFloatProperty(TopicProperty prop)
    {
        return getFloatProperty(prop, 0F);
    }
    
    public float getFloatProperty(TopicProperty prop, float defValue)
    {
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
     * @return Topic para cascada
     */
    public Topic setFloatProperty(TopicProperty prop, float value)
    {
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
    
    public double getDoubleProperty(TopicProperty prop)
    {
        return getDoubleProperty(prop, 0D);
    }   
    
    public double getDoubleProperty(TopicProperty prop, double defValue)
    {
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
     * @return Topic para cascada
     */
    public Topic setDoubleProperty(TopicProperty prop, double value)
    {
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
    
    public boolean getBooleanProperty(TopicProperty prop)
    {
        return getBooleanProperty(prop, false);
    }   
    
    public boolean getBooleanProperty(TopicProperty prop, boolean defValue)
    {
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
     * @return Topic para cascada
     */
    public Topic setBooleanProperty(TopicProperty prop, boolean value)
    {
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
    
    public Date getDateProperty(TopicProperty prop)
    {
        return getDateProperty(prop, null);
    }   
    
    public Date getDateProperty(TopicProperty prop, Date defValue)
    {
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
     * @return Topic para cascada
     */
    public Topic setDateProperty(TopicProperty prop, Date value)
    {
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
    
    public Resource getRDFResource()
    {
        return m_res;
    }
    
    @Override
    public String toString()
    {
        return m_res.toString();
    }

    @Override
    public int hashCode() 
    {
        return m_res.hashCode();
    }

    @Override
    public boolean equals(Object obj) 
    {
        return hashCode()==obj.hashCode();
    }
}

