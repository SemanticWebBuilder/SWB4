/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.css.parser;

import java.util.HashSet;
import java.util.StringTokenizer;

/**
 *
 * @author victor.lorenzana
 */
public class Attribute {
    private String name;
    private HashSet<String> values=new HashSet<String>();
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
    public String getName()
    {
        return name;
    }
    public String[] getValues()
    {
        return values.toArray(new String[values.size()]);
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
        final Attribute other = (Attribute) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    

}
