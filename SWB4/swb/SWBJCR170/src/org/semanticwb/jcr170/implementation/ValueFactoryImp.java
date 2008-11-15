/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.InputStream;
import java.util.Calendar;
import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;

/**
 *
 * @author victor.lorenzana
 */
public class ValueFactoryImp implements ValueFactory
{

    public Value createValue(String value)
    {
        return new ValueImp(value, PropertyType.STRING);
    }

    public Value createValue(String value, int type) throws ValueFormatException
    {
        return new ValueImp(value,type);
    }

    public Value createValue(long value)
    {
        return new ValueImp(value, PropertyType.LONG);
    }

    public Value createValue(double value)
    {
        return new ValueImp(value, PropertyType.DOUBLE);
    }

    public Value createValue(boolean value)
    {
        return new ValueImp(value, PropertyType.BOOLEAN);
    }

    public Value createValue(Calendar value)
    {
        return new ValueImp(value, PropertyType.DATE);
    }

    public Value createValue(InputStream value)
    {
        return new ValueImp(value, PropertyType.BINARY);
    }

    public Value createValue(Node value) throws RepositoryException
    {
        return new ValueImp(value, PropertyType.REFERENCE);
    }
}
