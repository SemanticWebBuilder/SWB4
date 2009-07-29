/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class ElementInfo {
    public String id;    
    public String title;
    public boolean active;
    public String type;

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
        final ElementInfo other = (ElementInfo) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id))
        {
            return false;
        }
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }
    

    @Override
    public String toString()
    {
        return title.toString();
    }

}
