/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.ItemVisitor;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.VersionException;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public final class PropertyImp implements Property
{

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private final NodeImp parent;
    private final BaseNode node;
    private boolean isNew = false;
    private boolean isModified = false;
    private final SemanticProperty property;
    private static final ValueFactoryImp factory = new ValueFactoryImp();

    PropertyImp(NodeImp parent, SemanticProperty property)
    {
        if ( parent == null || property == null )
        {
            throw new IllegalArgumentException();
        }
        this.parent = parent;
        this.node = parent.getBaseNode();
        this.property = property;

    }

    public void setValue(Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {value};
        setValue(values);

    }

    public void setValue(Value[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        for ( Value ovalue : value )
        {
            node.getSemanticObject().setProperty(property, ovalue.getString());
        }

    }

    public void setValue(String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(String[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        for ( String svalue : value )
        {
            setValue(factory.createValue(svalue));
        }
    }

    public void setValue(InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void setValue(long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Value getValue() throws ValueFormatException, RepositoryException
    {

        if ( !property.isObjectProperty() )
        {
            String value = node.getSemanticObject().getProperty(property);
            if ( value != null )
            {
                return factory.createValue(value);
            }
            else
            {
                throw new ValueFormatException();
            }
        }
        else
        {
            throw new ValueFormatException();
        }
    }

    public Value[] getValues() throws ValueFormatException, RepositoryException
    {
        ArrayList<Value> avalues=new ArrayList<Value>();
        Iterator<SemanticLiteral> values=node.getSemanticObject().listLiteralProperties(property);
        while(values.hasNext())
        {
            SemanticLiteral value=values.next();
            if(value!=null && value.getString()!=null)
            {
                avalues.add(factory.createValue(value.getString()));
            }
        }
        return avalues.toArray(new Value[avalues.size()]);
    }

    public String getString() throws ValueFormatException, RepositoryException
    {
        return getValue().getString();
    }

    public InputStream getStream() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public long getLong() throws ValueFormatException, RepositoryException
    {
        return getValue().getLong();
    }

    public double getDouble() throws ValueFormatException, RepositoryException
    {
        return getValue().getDouble();
    }

    public Calendar getDate() throws ValueFormatException, RepositoryException
    {
        return getValue().getDate();
    }

    public boolean getBoolean() throws ValueFormatException, RepositoryException
    {
        return getValue().getBoolean();
    }

    public Node getNode() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public long getLength() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public long[] getLengths() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public PropertyDefinition getDefinition() throws RepositoryException
    {
       return new SemanticPropertyDefinition(property, node);
    }

    public int getType() throws RepositoryException
    {
        int type = PropertyType.UNDEFINED;
        if ( property.isDataTypeProperty() )
        {
            if ( property.isBoolean() )
            {
                type = PropertyType.BOOLEAN;
            }
            else if ( property.isDate() || property.isDateTime() )
            {
                type = PropertyType.DATE;
            }
            else if ( property.isFloat() )
            {
                type = PropertyType.DOUBLE;
            }
            else if ( property.isInt() || property.isLong() )
            {
                type = PropertyType.LONG;
            }
            else
            {
                type = PropertyType.UNDEFINED;
            }
        }
        else if ( property.isObjectProperty() )
        {
            type = PropertyType.REFERENCE;
        }
        else
        {
            type = PropertyType.UNDEFINED;
        }
        return type;
    }

    public String getPath() throws RepositoryException
    {
        return parent.getPath() + "/" + this.getName();
    }

    public String getName() throws RepositoryException
    {
        return property.getName();
    }

    public Item getAncestor(int arg0) throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Node getParent() throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        return parent;
    }

    public int getDepth() throws RepositoryException
    {
        int depth = 0;
        Item thisNode = this;
        Item parentNode = this.getParent();
        while (parentNode == null)
        {
            thisNode = parentNode;
            parentNode = thisNode.getParent();
            depth++;
        }
        return depth;
    }

    public Session getSession() throws RepositoryException
    {
        return parent.getSession();
    }

    public boolean isNode()
    {
        return false;
    }

    public boolean isNew()
    {
        return isNew;
    }

    public boolean isModified()
    {
        return isModified;
    }

    public boolean isSame(Item arg0) throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void accept(ItemVisitor arg0) throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void refresh(boolean arg0) throws InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void remove() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }
}
