package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBInstance;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SemanticObject
{
    private static Logger log = SWBUtils.getLogger(SemanticObject.class);
    
    Resource m_res=null;
    SemanticModel m_model=null;
    
    public SemanticObject(String uri, SemanticModel model)
    {
        m_res=model.getRDFModel().createResource(uri);
        m_model=model;
    }
    
    public SemanticObject(Resource res)
    {
        this.m_res=res;
        m_model=SWBInstance.getSemanticMgr().getModel(res.getModel());
    }
    
    public String getURI()
    {
        return m_res.getURI();
    }
    
    public String getName()
    {
        return m_res.getLocalName();
    }    
    
    public SemanticClass getSemanticClass()
    {
        Statement stm=m_res.getProperty(m_res.getModel().getProperty(SemanticVocabulary.RDF_TYPE));
        if(stm!=null)
        {
            try
            {
                return SWBInstance.getSemanticMgr().getVocabulary().getSemanticClass(stm.getResource().getURI());
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
    
    public SemanticObject removeProperty(SemanticProperty prop)
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
    public String getProperty(SemanticProperty prop)
    {
        return getProperty(prop,null);
    }
    
    public String getProperty(SemanticProperty prop, String defValue)
    {
        String ret=defValue;
        Statement stm=m_res.getProperty(prop.getRDFProperty());
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
    
    public SemanticObject addObjectProperty(SemanticProperty prop, SemanticObject object)
    {
        Property iprop=prop.getRDFProperty();
        m_res.addProperty(iprop, object.getRDFResource());
        return this;
    }    
    
    public SemanticObject removeObjectProperty(SemanticProperty prop, SemanticObject object)
    {
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
        return new SemanticObjectIterator(m_res.listProperties(prop.getRDFProperty()));
    }
    
    public SemanticObject getObjectProperty(SemanticProperty prop)
    {
        return getObjectProperty(prop,null);
    }
    
    public SemanticObject getObjectProperty(SemanticProperty prop, SemanticObject defValue)
    {
        SemanticObject ret=defValue;
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

