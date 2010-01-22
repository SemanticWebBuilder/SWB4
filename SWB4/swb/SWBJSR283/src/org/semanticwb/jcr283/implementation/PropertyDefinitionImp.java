/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.NamespaceRegistry;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.Value;
import javax.jcr.nodetype.PropertyDefinition;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyDefinitionImp extends ItemDefinitionImp implements PropertyDefinition
{

    private final boolean isMultiple;
    private final boolean isFullTextSearchable;
    private final boolean isQueryOrderable;
    private int requiredType;
    private final HashSet<Value> values = new HashSet<Value>();
    private final HashSet<String> valueConstrains = new HashSet<String>();
    private SemanticProperty semanticProperty;
    public PropertyDefinitionImp(SemanticObject obj, NodeTypeImp nodeType)
    {
        super(obj, nodeType);
        SemanticProperty prop = NodeTypeImp.getSemanticProperty(Property.JCR_MULTIPLE);
        SemanticLiteral value = obj.getLiteralProperty(prop);
        if (value != null)
        {
            isMultiple = value.getBoolean();
        }
        else
        {
            isMultiple = false;
        }
        prop = NodeTypeImp.getSemanticProperty(Property.JCR_REQUIRED_TYPE);
        value = obj.getLiteralProperty(prop);
        requiredType=PropertyType.UNDEFINED;
        if (value != null && value.getString()!=null)
        {

            String srequiredType = value.getString().toLowerCase();
            if(srequiredType.length()>0)
            {
                srequiredType=srequiredType.substring(0,1).toUpperCase()+srequiredType.substring(1);
            }
            try
            {
                requiredType=PropertyType.valueFromName(srequiredType);
            }
            catch(IllegalArgumentException iae)
            {
                requiredType=PropertyType.UNDEFINED;
            }
        }
        else
        {
            requiredType = PropertyType.UNDEFINED;
        }

        prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(NamespaceRegistry.NAMESPACE_JCR+"#isFullTextSearchable");
        value = obj.getLiteralProperty(prop);
        if (value != null)
        {
            isFullTextSearchable = value.getBoolean();
        }
        else
        {
            isFullTextSearchable = false;
        }
        prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(NamespaceRegistry.NAMESPACE_JCR+"#isQueryOrderable");
        value = obj.getLiteralProperty(prop);
        if (value != null)
        {
            isQueryOrderable = value.getBoolean();
        }
        else
        {
            isQueryOrderable = false;
        }

        prop = NodeTypeImp.getSemanticProperty(Property.JCR_DEFAULT_VALUES);
        Iterator<SemanticLiteral> ovalues = obj.listLiteralProperties(prop);
        ValueFactoryImp vf = new ValueFactoryImp();
        while (ovalues.hasNext())
        {
            String svalue = ovalues.next().getString();
            values.add(vf.createValue(svalue));
        }

        prop = NodeTypeImp.getSemanticProperty(Property.JCR_VALUE_CONSTRAINTS);
        ovalues = obj.listLiteralProperties(prop);
        while (ovalues.hasNext())
        {
            String svalue = ovalues.next().getString();
            valueConstrains.add(svalue);
        }
    }

    public PropertyDefinitionImp(SemanticProperty property,NodeTypeImp nodeType)
    {
        this(property.getSemanticObject(), nodeType);
        this.semanticProperty=property;
        if (property.isBinary())
        {
            requiredType = PropertyType.BINARY;
        }
        else if (property.isString())
        {
            requiredType = PropertyType.STRING;
        }
        else if (property.isBoolean())
        {
            requiredType = PropertyType.BOOLEAN;
        }
        else if (property.isByte())
        {
            requiredType = PropertyType.LONG;
        }
        else if (property.isInt())
        {
            requiredType = PropertyType.LONG;
        }
        else if (property.isDate())
        {
            requiredType = PropertyType.DATE;
        }
        else if (property.isDateTime())
        {
            requiredType = PropertyType.DATE;
        }
        else if (property.isDouble())
        {
            requiredType = PropertyType.DOUBLE;
        }
        else if (property.isObjectProperty())
        {
            requiredType = PropertyType.REFERENCE;
        }
        else if (property.isLong())
        {
            requiredType = PropertyType.LONG;
        }
        else
        {
            requiredType = PropertyType.UNDEFINED;
        }
    }
    public PropertyDefinitionImp(SemanticProperty property)
    {
        this(property,NodeTypeManagerImp.loadNodeType(property.getDomainClass()));
    }
    public SemanticProperty getSemanticProperty()
    {
        return semanticProperty;
    }
    public int getRequiredType()
    {
        return requiredType;
    }

    public String[] getValueConstraints()
    {
        return valueConstrains.toArray(new String[valueConstrains.size()]);
    }

    public Value[] getDefaultValues()
    {
        return values.toArray(new Value[values.size()]);
    }

    public boolean isMultiple()
    {
        return isMultiple;
    }

    public String[] getAvailableQueryOperators()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isFullTextSearchable()
    {
        return isFullTextSearchable;
    }

    public boolean isQueryOrderable()
    {
        return isQueryOrderable;
    }
}
