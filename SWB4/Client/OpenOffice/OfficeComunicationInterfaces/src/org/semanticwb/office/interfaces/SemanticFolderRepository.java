/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class SemanticFolderRepository {
    public String name;
    public String uuid;
    public boolean haschilds;
    @Override
    public String toString()
    {
        return name.toString();
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
        final SemanticFolderRepository other = (SemanticFolderRepository) obj;
        if ((this.uuid == null) ? (other.uuid != null) : !this.uuid.equals(other.uuid))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + (this.uuid != null ? this.uuid.hashCode() : 0);
        return hash;
    }

    
}
