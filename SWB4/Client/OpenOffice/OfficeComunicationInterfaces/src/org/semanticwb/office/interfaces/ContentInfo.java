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
public class ContentInfo {
    public String id;
    public String title;
    public String descripcion;
    public String categoryId;
    public String categoryTitle;
    public Date created;

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
        final ContentInfo other = (ContentInfo) obj;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    @Override
    public String toString()
    {
        return title;
    }
}
