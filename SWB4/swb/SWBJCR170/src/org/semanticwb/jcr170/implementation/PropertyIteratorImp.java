/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.Iterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyIteratorImp implements PropertyIterator
{

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    SimpleNode parent;
    Iterator<PropertyImp> properties;
    private long position;
    private int size;
    PropertyIteratorImp(Iterator<PropertyImp> properties, SimpleNode parent,int size)
    {
        if ( parent == null )
        {
            throw new IllegalArgumentException();
        }
        this.parent = parent;        
        this.properties = properties;
        this.size=size;
    }

    public Property nextProperty()
    {        
        return properties.next();        
    }

    public void skip(long skip)
    {
        for ( int i = 1; i <= skip; i++ )
        {
            this.nextProperty();
        }
    }

    public long getSize()
    {
        return size;
    }

    public long getPosition()
    {
        return position;
    }

    public boolean hasNext()
    {
        return properties.hasNext();
    }

    public Object next()
    {
        return this.nextProperty();
    }

    public void remove()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }
}
