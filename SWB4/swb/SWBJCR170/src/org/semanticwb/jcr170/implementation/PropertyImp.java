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
import org.semanticwb.SWBException;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public final class PropertyImp implements Property
{

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private boolean isNew = true;
    private boolean isModified = true;
    private static final ValueFactoryImp factory = new ValueFactoryImp();
    private final String name;
    private final PropertyDefinitionImp propertyDefinition;
    private ArrayList<Value> values = new ArrayList<Value>();
    private String path;
    private final SimpleNode parent;
    private final SemanticClass clazz;

    PropertyImp(SimpleNode parent, SemanticClass clazz, String name, PropertyDefinitionImp propertyDefinition)
    {
        if ( name == null )
        {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.propertyDefinition = propertyDefinition;
        path = parent.getSimplePath() + "/" + name;
        this.parent = parent;
        this.clazz = clazz;
    }

    public SemanticClass getSemanticClass()
    {
        return clazz;
    }

    public void setValue(Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {value};
        setValue(values);

    }

    public void setValueInternal(String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        this.values.clear();
        this.values.add(factory.createValue(value));
    }

    public void setValueInternal(boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        this.values.clear();
        this.values.add(factory.createValue(value));
    }

    public void setValue(Value[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        if ( !getDefinition().isMultiple() && value.length > 1 )
        {
            throw new ValueFormatException("The property " + this.name + "  is not multiple");
        }
        else if ( getDefinition().isProtected() )
        {
            throw new ValueFormatException("The property " + this.name + " is protected");
        }
        else
        {
            int type = PropertyType.UNDEFINED;
            int requiredType = getDefinition().getRequiredType();
            boolean errorRequiredType = false;
            for ( Value ovalue : value )
            {
                type = ovalue.getType();
                if ( requiredType == 0 )
                {
                    type = requiredType;
                }
                if ( type != requiredType )
                {
                    errorRequiredType = true;
                    break;
                }
            }
            if ( errorRequiredType )
            {
                throw new ValueFormatException("A value is " + PropertyType.nameFromValue(type) + " and the property is defined as " + PropertyType.nameFromValue(requiredType));
            }
            for ( Value ovalue : value )
            {
                this.values.clear();
                this.values.add(ovalue);
                isModified = true;
            }
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
        setValue(factory.createValue(node));
    }

    public Value getValue() throws ValueFormatException, RepositoryException
    {
        if ( values.size() == 0 && parent.node != null )
        {
            loadPropertiesFromDataBase();
        }
        if ( values.size() > 0 )
        {
            return values.get(0);
        }
        return null;
    }

    private void loadPropertiesFromDataBase()
    {
        BaseNode node = parent.node;
        if ( node.existsProperty(node.getSemanticObject().getSemanticClass(), name) )
        {
            SemanticProperty property = node.getSemanticProperty(name, clazz);
            if ( property.isDataTypeProperty() )
            {
                Iterator<SemanticLiteral> literals = node.getSemanticObject().listLiteralProperties(property);
                while (literals.hasNext())
                {
                    values.add(factory.createValue(literals.next().getString()));
                }
            }
            else
            {
                Iterator<SemanticObject> value = node.getSemanticObject().listObjectProperties(property);
            // TODO
            }

        }
    }

    public Value[] getValues() throws ValueFormatException, RepositoryException
    {
        if ( values.size() == 0 && parent.node != null )
        {
            loadPropertiesFromDataBase();
        }
        return values.toArray(new Value[values.size()]);
    }

    public String getString() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if ( value != null )
        {
            return value.getString();
        }
        return null;
    }

    public InputStream getStream() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public long getLong() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if ( value == null )
        {
            throw new ValueFormatException();
        }
        return value.getLong();
    }

    public double getDouble() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if ( value == null )
        {
            throw new ValueFormatException();
        }
        return value.getDouble();
    }

    public Calendar getDate() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if ( value == null )
        {
            return null;
        }
        return value.getDate();
    }

    public boolean getBoolean() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if ( value == null )
        {
            throw new ValueFormatException();
        }
        return value.getBoolean();
    }

    public Node getNode() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public long getLength() throws ValueFormatException, RepositoryException
    {
        return values.size();
    }

    public long[] getLengths() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public PropertyDefinition getDefinition() throws RepositoryException
    {
        return propertyDefinition;
    }

    public int getType() throws RepositoryException
    {
        return getDefinition().getRequiredType();
    }

    public String getPath() throws RepositoryException
    {
        return path;
    }

    public String getName() throws RepositoryException
    {
        return name;
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

    public void setNew(boolean isNew)
    {
        this.isNew = isNew;
    }

    public void setModified(boolean isModified)
    {
        this.isModified = isModified;
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

    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        parent.save();
    }

    public void refresh(boolean arg0) throws InvalidItemStateException, RepositoryException
    {
        values.clear();
        loadPropertiesFromDataBase();
    }

    public void remove() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
