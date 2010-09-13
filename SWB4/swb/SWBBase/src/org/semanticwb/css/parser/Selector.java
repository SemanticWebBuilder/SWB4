/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class Selector.
 * 
 * @author victor.lorenzana
 */
public class Selector {

    /** The name. */
    private String name;
    
    /** The atts. */
    private Set<Attribute> atts=new HashSet<Attribute>();
    
    /**
     * Instantiates a new selector.
     * 
     * @param name the name
     * @param atts the atts
     */
    Selector(String name,Set<Attribute> atts)
    {
        this.name=name;
        this.atts=atts;
    }
    
    /**
     * Gets the attributes.
     * 
     * @return the attributes
     */
    public Attribute[] getAttributes()
    {
        return atts.toArray(new Attribute[atts.size()]);
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
        final Selector other = (Selector) obj;
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
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    
}
