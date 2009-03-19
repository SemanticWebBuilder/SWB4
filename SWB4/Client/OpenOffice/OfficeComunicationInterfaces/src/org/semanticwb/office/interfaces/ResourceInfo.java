/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class ResourceInfo {
    public String id;    
    public String title;
    public String description;
    public boolean active;
    public String version;
    public PageInfo page;
    @Override
    public String toString()
    {
        return title;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ResourceInfo other = (ResourceInfo) obj;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}
