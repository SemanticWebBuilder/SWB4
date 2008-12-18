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
public class VersionInfo {

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
        final VersionInfo other = (VersionInfo) obj;
        return true;
    }

    @Override
    public String toString()
    {
        return nameOfVersion;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + (this.nameOfVersion != null ? this.nameOfVersion.hashCode() : 0);
        return hash;
    }
    public String contentId;    
    public String nameOfVersion;
    public Date created;
    public String user;
}
