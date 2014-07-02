/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store;

/**
 *
 * @author javier.solis.g
 */
public class Resource extends Node
{
    private String value;

    public Resource(String value)
    {
        this.value = value;
    }
    
    public String getUri()
    {
        if(hasUri())return value.substring(1,value.length()-1);
        return null;
    }

    public String getId()
    {
        if(hasId())return value;
        return null;
    }

    @Override
    public String getValue()
    {
        return value;
    }
    
    @Override
    public String toString()
    {
        return value;
    }
    
    public boolean hasUri()
    {
        return (value!=null && value.charAt(0)=='<');
    }
    
    public boolean hasId()
    {
        return (value!=null && value.charAt(0)!='<');
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Resource)
        {
            Resource r=(Resource)obj;
            return (r.getValue().equals(value));
        }
        return false;
    }  

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
    
    
    
}
