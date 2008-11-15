/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.Iterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyIteratorImp implements PropertyIterator
{

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    NodeImp parent;
    Iterator<SemanticProperty> properties;
    private long position;

    PropertyIteratorImp(NodeImp parent)
    {
        if ( parent == null )
        {
            throw new IllegalArgumentException();
        }
        this.parent = parent;
        properties = parent.getBaseNode().getSemanticObject().getSemanticClass().listProperties();
    }

    public Property nextProperty()
    {
        PropertyImp propertyToReturn = null;
        SemanticProperty property = properties.next();
        if ( property != null )
        {
            position++;
            propertyToReturn = new PropertyImp(parent, property);
        }
        return propertyToReturn;
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
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public long getPosition()
    {
        return position;
    }

    public boolean hasNext()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Object next()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void remove()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }
}
