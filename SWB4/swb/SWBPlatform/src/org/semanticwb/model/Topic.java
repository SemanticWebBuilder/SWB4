/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBContext;

/**
 *
 * @author Jei
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
    
    
    public TopicClass getTopicClass()
    {
        Statement stm=m_res.getProperty(m_res.getModel().getProperty(SWBContext.getSemanticMgr().getVocabulary().RDF_TYPE));
        if(stm!=null)
        {
            try
            {
                return new TopicClass(stm.getResource().getURI());
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
    
}
