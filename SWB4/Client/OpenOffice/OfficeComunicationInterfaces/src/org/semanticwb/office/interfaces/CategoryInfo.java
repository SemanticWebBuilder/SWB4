/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class CategoryInfo {    
    public String UDDI;
    public String title;
    public String description;
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
        final CategoryInfo other = (CategoryInfo) obj;
        if ((this.UDDI == null) ? (other.UDDI != null) : !this.UDDI.equals(other.UDDI))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + (this.UDDI != null ? this.UDDI.hashCode() : 0);
        return hash;
    }
}
