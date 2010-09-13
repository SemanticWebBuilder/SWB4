/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.util.HashSet;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class Attribute.
 * 
 * @author victor.lorenzana
 */
public class Attribute {
    
    /** The name. */
    private String name;
    
    /** The values. */
    private HashSet<String> values=new HashSet<String>();
    
    /**
     * Instantiates a new attribute.
     * 
     * @param name the name
     * @param value the value
     */
    public Attribute(String name,String value)
    {
        this.name=name;
        StringTokenizer st=new StringTokenizer(value," ");
        while(st.hasMoreTokens())
        {
            String nvalue=st.nextToken().trim();            
            values.add(nvalue);
        }
    }
    
    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the values.
     * 
     * @return the values
     */
    public String[] getValues()
    {
        return values.toArray(new String[values.size()]);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Attribute other = (Attribute) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    

}
