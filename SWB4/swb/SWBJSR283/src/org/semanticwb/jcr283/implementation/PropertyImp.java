/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.jcr.Binary;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.VersionException;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyImp  extends ItemImp implements Property {

    
    private final PropertyDefinitionImp propertyDefinitionImp;    
    public PropertyImp(SemanticProperty prop,NodeImp parent) throws RepositoryException
    {
        super(prop,parent);
        NodeTypeImp nodeType=NodeTypeManagerImp.loadNodeType(prop.getDomainClass());
        propertyDefinitionImp=new PropertyDefinitionImp(prop.getSemanticObject(), nodeType);    
    }
    public void setValue(Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(Value[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(String[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(Binary value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(BigDecimal value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(Node value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Value getValue() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Value[] getValues() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getString() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public InputStream getStream() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Binary getBinary() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getLong() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getDouble() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BigDecimal getDecimal() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Calendar getDate() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean getBoolean() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node getNode() throws ItemNotFoundException, ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

    public Property getProperty() throws ItemNotFoundException, ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getLength() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long[] getLengths() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyDefinition getDefinition() throws RepositoryException
    {
        return propertyDefinitionImp;
    }

    public int getType() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isMultiple() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public boolean isNode()
    {
        return false;
    }

}
