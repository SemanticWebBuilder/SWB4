/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class NodeDefinitionImp implements NodeDefinition
{
    private final BaseNode node;
    NodeDefinitionImp(BaseNode node)
    {
        this.node=node;
    }
    public NodeType[] getRequiredPrimaryTypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType getDefaultPrimaryType()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean allowsSameNameSiblings()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType getDeclaringNodeType()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName()
    {
        return node.getSemanticObject().getSemanticClass().getName();
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
