/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;


/**
 *
 * @author victor.lorenzana
 */
public class PropertyInfo {
    public String title;
    public String type;
    public boolean isRequired;
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
        final PropertyInfo other = (PropertyInfo) obj;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    public String id;
}
