/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model.base;

import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author Jei
 */
public class GenericObjectBase implements GenericObject
{
    SemanticObject m_obj;
    
    public GenericObjectBase(SemanticObject obj)
    {
        this.m_obj=obj;
    }
    
    public String getURI()
    {
        return m_obj.getURI();
    }
    
    /**
     * Regresa URI codificado para utilizar en ligas de html
     * @return URI Codificado
     */
    public String getEncodedURI()
    {
        return URLEncoder.encode(getURI());
    }     
    
    public String getId()
    {
        return m_obj.getId();
    }
    
    public SemanticObject getSemanticObject()
    {
        return m_obj;
    }
    
    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public GenericObject setProperty(String prop, String value)
    {
        m_obj.setProperty(_getProperty(prop), value);
        return this;
    }    
    
    public GenericObject removeProperty(String prop)
    {
        m_obj.removeProperty(_getProperty(prop));
        return this;
    }      

    public String getProperty(String prop)
    {
        return  getProperty(prop, null);
    }
    
    public String getProperty(String prop, String defValue)
    {
        return getSemanticObject().getProperty(_getProperty(prop), defValue);
    }
    
    private SemanticProperty _getProperty(String prop)
    {
        return new SemanticProperty(m_obj.getModel().getRDFModel().createProperty(m_obj.getModel().getNameSpace()+"/property#"+prop));
    }
    
    @Override
    public String toString()
    {
        return m_obj.toString();
    }

    @Override
    public int hashCode() 
    {
        return m_obj.hashCode();
    }

    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }
    
    /**
     * Regresa ruta de trabajo del objeto relativa al directorio work
     * ejemplo: /sep/Template/1
     *          /dominio/Objeto/id
     * 
     * @return String con la ruta relativa al directorio work
     */
    public String getWorkPath()
    {
        return "/"+getSemanticObject().getModel().getName()+"/"+getSemanticObject().getSemanticClass().getName()+"/"+getId();
    }
}
