/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.AccessDeniedException;
import javax.jcr.Binary;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.VersionException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyImp extends ItemImp implements Property
{
    private final static Logger log = SWBUtils.getLogger(PropertyImp.class);
    private static final ValueFactoryImp valueFactoryImp = new ValueFactoryImp();
    private final PropertyDefinitionImp propertyDefinitionImp;
    private ArrayList<Value> values = new ArrayList<Value>();
    private SemanticProperty prop;

    public PropertyImp(SemanticProperty prop, NodeImp parent, String path, SessionImp session) throws RepositoryException
    {
        super(prop, parent, path, parent.getDepth() + 1, session);
        this.prop = prop;
        NodeTypeImp nodeType = NodeTypeManagerImp.loadNodeType(prop.getDomainClass());
        propertyDefinitionImp = new PropertyDefinitionImp(prop.getSemanticObject(), nodeType);
        this.isNew=false;
    }

    private void loadValues()
    {
        SemanticObject obj = parent.getSemanticObject();
        if (obj != null && prop != null && !isModified())
        {
            Iterator<SemanticLiteral> lvalues = obj.listLiteralProperties(prop);
            while (lvalues.hasNext())
            {
                SemanticLiteral literal = lvalues.next();
                String value = literal.getString();
                try
                {
                    values.add(transformValue(valueFactoryImp.createValue(value), propertyDefinitionImp.getRequiredType()));
                }
                catch (Exception e)
                {
                }
            }
        }

    }

    public void setValue(Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(new Value[]
                {
                    value
                });
    }

    private Value transformValue(Value value, int reqValue) throws ValueFormatException, RepositoryException
    {
        ValueImp newValue=new ValueImp(value,reqValue);
        return newValue;
    }
    void set(Value[] values) throws ValueFormatException, VersionException, LockException, RepositoryException
    {
        if (values.length > 1 && !propertyDefinitionImp.isMultiple())
        {
            throw new ConstraintViolationException("The property is not multiple");
        }
        int reqType = propertyDefinitionImp.getRequiredType();
        //Validate Values
        HashSet<Value> newValues = new HashSet<Value>();
        for (Value value : values)
        {
            newValues.add(transformValue(value, reqType));
        }
        this.values.addAll(newValues);
        this.isModified = true;
        parent.isModified = true;
    }
    public void setValue(Value[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        if(this.getDefinition().isProtected())
        {
            throw new ConstraintViolationException("The property is protected");
        }
        set(values);
    }
    public void set(Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        set(new Value[]{value});
    }

    public void setValue(String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(String[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        HashSet<Value> ovalues = new HashSet<Value>();
        for (String value : values)
        {
            ovalues.add(valueFactoryImp.createValue(value));
        }
        setValue(ovalues.toArray(new Value[ovalues.size()]));
    }

    public void setValue(InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(Binary value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(BigDecimal value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public void setValue(Node value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(valueFactoryImp.createValue(value));
    }

    public Value getValue() throws ValueFormatException, RepositoryException
    {
        if (this.isMultiple())
        {
            throw new ValueFormatException("The property is multivalued");
        }
        return getCopy(values.get(0));
    }

    public Value[] getValues() throws ValueFormatException, RepositoryException
    {
        if (values.size() == 0)
        {
            loadValues();
        }
        HashSet<Value> ovalues = new HashSet<Value>();
        for (Value value : values)
        {
            ovalues.add(getCopy(value));
        }
        return ovalues.toArray(new Value[ovalues.size()]);
    }

    public String getString() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getString();
    }

    public InputStream getStream() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getStream();
    }

    public Binary getBinary() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getBinary();
    }

    public long getLong() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getLong();
    }

    public double getDouble() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getDouble();
    }

    public BigDecimal getDecimal() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getDecimal();
    }

    public Calendar getDate() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getDate();
    }

    public boolean getBoolean() throws ValueFormatException, RepositoryException
    {
        return this.getValue().getBoolean();
    }

    public Node getNode() throws ItemNotFoundException, ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Property getProperty() throws ItemNotFoundException, ValueFormatException, RepositoryException
    {
        if (this.isMultiple())
        {
            throw new ValueFormatException("The property is multivalued");
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getLength() throws ValueFormatException, RepositoryException
    {
        if (this.isMultiple())
        {
            throw new ValueFormatException("The property is multivalued");
        }
        return getLength(values.get(0));
    }

    private long getLength(Value value)
    {
        return -1;
    }

    public long[] getLengths() throws ValueFormatException, RepositoryException
    {
        long[] getLengths = new long[values.size()];
        int index = 0;
        for (Value value : values)
        {
            getLengths[index] = getLength(value);
            index++;
        }
        return getLengths;
    }

    public PropertyDefinition getDefinition() throws RepositoryException
    {
        return propertyDefinitionImp;
    }

    public int getType() throws RepositoryException
    {
        int type = PropertyType.UNDEFINED;
        if (values.size() > 0)
        {
            type = values.get(0).getType();
        }
        return type;
    }

    public boolean isMultiple() throws RepositoryException
    {
        return values.size() == 0 ? false : true;
    }

    public boolean isNode()
    {
        return false;
    }

    private Value getCopy(Value value) throws RepositoryException
    {
        // TODO:
        Value copyValue = null;
        int type = value.getType();
        switch (type)
        {
            case PropertyType.BINARY:
                Binary ovalue = value.getBinary();
                copyValue = valueFactoryImp.createValue(ovalue);
                break;
            default:
                String svalue = value.getString();
                copyValue = valueFactoryImp.createValue(svalue, type);

        }
        return copyValue;
    }

    public void saveData() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (parent.getSemanticObject() == null)
        {
            //TODO:ERROR
        }
        if (this.isModified)
        {
            if (prop == null)
            {
                //TODO: create Semantic Property
            }
            SemanticObject obj = parent.getSemanticObject();
            for (Value value : this.values)
            {
                int type = value.getType();
                switch (type)
                {
                    case PropertyType.BINARY:
                        try
                        {
                            obj.setInputStreamProperty(prop, value.getBinary().getStream(), prop.getName());
                        }
                        catch (Exception e)
                        {
                            throw new RepositoryException(e);
                        }
                        break;
                    case PropertyType.BOOLEAN:
                        obj.setBooleanProperty(prop, value.getBoolean());
                        break;
                    case PropertyType.DATE:
                        obj.setDateProperty(prop, value.getDate().getTime());
                        break;
                    case PropertyType.DECIMAL:
                        obj.setDoubleProperty(prop, value.getDecimal().doubleValue());
                        break;
                    case PropertyType.DOUBLE:
                        obj.setDoubleProperty(prop, value.getDouble());
                        break;
                    case PropertyType.LONG:
                        obj.setLongProperty(prop, value.getLong());
                        break;
                    default:
                        obj.setProperty(prop, value.getString());
                }
            }
        }
        this.isModified=false;
        this.isNew=false;
    }
}
