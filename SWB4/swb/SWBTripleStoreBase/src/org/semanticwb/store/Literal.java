/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.store;

/**
 *
 * @author javier.solis.g
 */
public class Literal extends Node
{
    private String value;
    private String lang;
    private String type;
    private Integer hash;

    public Literal(String value, String lang, String type)
    {
        this.value = value;
        this.lang = lang;
        this.type = type;
    }

    public void setValue(String value)
    {
        hash=null;
        this.value = value;
    }

    @Override
    public String getValue()
    {
        return value;
    }

    public void setLang(String lang)
    {
        hash=null;
        this.lang = lang;
    }

    public String getLang()
    {
        return lang;
    }

    public void setType(String type)
    {
        hash=null;
        this.type = type;
    }
    
    public Resource getTypeResource()
    {
        if(type==null)return null;
        return new Resource(type);
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        String ret="\""+value+"\"";
        if(lang!=null)ret=ret+"@"+lang;
        if(type!=null)ret=ret+"^^"+type;
        return ret;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean ret=true;
        if(obj instanceof Literal)
        {
            Literal l=(Literal)obj;
            if(!value.equals(l.value))ret=false;
            if(ret && lang!=null && !lang.equals(l.lang))ret=false;
            if(ret && type!=null && !type.equals(l.type))ret=false;
        }else ret=false;
        return ret;
    }

    @Override
    public int hashCode()
    {
        if(hash==null)hash=toString().hashCode();
        return hash;
    }
    

   
}
