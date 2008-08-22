/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model.base;

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
    
    public String getId()
    {
        String uri=getURI();
        int x=uri.indexOf('#');
        if(x>-1)return uri.substring(x+1);
        return uri;
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
        return hashCode()==obj.hashCode();
    }

}
