/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.Value;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.PropertyDefinition;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyDefinitionImp implements PropertyDefinition {

    private final SemanticProperty property;
    public PropertyDefinitionImp(SemanticProperty property)
    {
        this.property=property;
    }
    public int getRequiredType()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getValueConstraints()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Value[] getDefaultValues()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isMultiple()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getAvailableQueryOperators()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isFullTextSearchable()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isQueryOrderable()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType getDeclaringNodeType()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName()
    {
        return property.getPrefix()+":"+property.getName();
    }

    public boolean isAutoCreated()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isMandatory()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getOnParentVersion()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isProtected()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
