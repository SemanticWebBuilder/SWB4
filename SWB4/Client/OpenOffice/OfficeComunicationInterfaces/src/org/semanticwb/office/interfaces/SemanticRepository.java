/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class SemanticRepository {
    public String name;
    public String resid;    
    public String pageid;
    public String uri;
    

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
        final SemanticRepository other = (SemanticRepository) obj;
        if ((this.uri == null) ? (other.uri != null) : !this.uri.equals(other.uri))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + (this.uri != null ? this.uri.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return name.toString();
    }
    
}
