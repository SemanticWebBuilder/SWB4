/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;
import java.util.Date;
/**
 *
 * @author victor.lorenzana
 */
public class SemanticFileRepository {
    public String title;
    public String name;
    public String uuid;
    public Date date;
    

    @Override
    public String toString()
    {
        return title.toString();
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
        final SemanticFileRepository other = (SemanticFileRepository) obj;
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
        hash = 19 * hash + (this.uuid != null ? this.uuid.hashCode() : 0);
        return hash;
    }
}
