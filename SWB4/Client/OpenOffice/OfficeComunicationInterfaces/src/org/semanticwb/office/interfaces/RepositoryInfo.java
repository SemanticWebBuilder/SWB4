/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public class RepositoryInfo {

    public RepositoryInfo(String name)
    {
        this.name=name;
    }
    public RepositoryInfo()
    {

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
        final RepositoryInfo other = (RepositoryInfo) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    public String name;
    public boolean exclusive;
    public SiteInfo siteInfo;

    @Override
    public String toString()
    {
        return name.toString();
    }


}
