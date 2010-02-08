/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

/**
 *
 * @author victor.lorenzana
 */
public final class PropertyStatus
{

    private boolean deleted;
    private final PropertyImp property;

    public PropertyStatus(PropertyImp property, boolean deleted)
    {
        this.deleted = deleted;
        this.property = property;
    }

    public PropertyStatus(PropertyImp property)
    {
        this.deleted = false;
        this.property = property;
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
        final PropertyStatus other = (PropertyStatus) obj;
        if (this.property != other.property && (this.property == null || !this.property.equals(other.property)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + (this.property != null ? this.property.hashCode() : 0);
        return hash;
    }

    public PropertyImp getProperty()
    {
        return property;
    }

    public void delete()
    {
        this.deleted = true;
    }

    public void restore()
    {
        this.deleted = false;
    }

    public boolean isDeleted()
    {
        return deleted;
    }
}
