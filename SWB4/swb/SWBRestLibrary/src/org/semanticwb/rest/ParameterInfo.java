/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

/**
 *
 * @author victor.lorenzana
 */
public class ParameterInfo {

    private final String name;
    private final String description;
    public ParameterInfo(String name,String description)
    {
        if(name==null)
        {
            throw new NullPointerException();
        }
        this.name=name;
        this.description=description;
    }
    public String getParameterName()
    {
        return name;
    }
    public String getParameterDescription()
    {
        return description;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ParameterInfo other = (ParameterInfo) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return name;
    }

}
