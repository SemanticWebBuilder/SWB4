/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import javax.jcr.Value;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.OnParentVersionAction;

/**
 *
 * @author victor.lorenzana
 */
public class DefaultPropertyDefinition implements PropertyDefinition
{
    private final String name;
    DefaultPropertyDefinition(String name)
    {
        this.name=name;
    }
    public int getRequiredType()
    {
        return javax.jcr.PropertyType.UNDEFINED;
    }

    public String[] getValueConstraints()
    {
        return null;
    }

    public Value[] getDefaultValues()
    {
        return null;
    }

    public boolean isMultiple()
    {
        return true;
    }

    public NodeType getDeclaringNodeType()
    {
        return null;
    }

    public String getName()
    {
        return name;
    }

    public boolean isAutoCreated()
    {
        return false;
    }

    public boolean isMandatory()
    {
        return false;
    }

    public int getOnParentVersion()
    {
        return OnParentVersionAction.COPY;
    }

    public boolean isProtected()
    {
        return false;
    }
}
