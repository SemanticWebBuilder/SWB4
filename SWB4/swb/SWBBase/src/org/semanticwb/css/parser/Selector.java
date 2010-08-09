/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class Selector {

    private String name;
    private Set<Attribute> atts=new HashSet<Attribute>();
    Selector(String name,Set<Attribute> atts)
    {
        this.name=name;
        this.atts=atts;
    }
    public Attribute[] getAttributes()
    {
        return atts.toArray(new Attribute[atts.size()]);
    }
    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

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

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    
}
