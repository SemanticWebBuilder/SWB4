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
        if ((this.nameOfVersion == null) ? (other.nameOfVersion != null) : !this.nameOfVersion.equals(other.nameOfVersion))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 73 * hash + (this.nameOfVersion != null ? this.nameOfVersion.hashCode() : 0);
        return hash;
    }

    

    @Override
    public String toString()
    {
        if(nameOfVersion.equals("*"))
        {
            return "Mostrar la última version";
        }
        return nameOfVersion;
    }

    public boolean published;
    public String contentId;    
    public String nameOfVersion;
    public Date created;
    public String user;
}
