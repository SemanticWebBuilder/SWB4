/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class ContentType {
    public String id;
    public String title;
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
        final ContentType other = (ContentType) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + (this.title != null ? this.title.hashCode() : 0);
        return hash;
    }
}
