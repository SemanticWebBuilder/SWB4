/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import javax.jcr.Value;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.PropertyDefinition;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class NodeTypeImp implements NodeType
{
    private final SemanticClass clazz;
    private final BaseNode node;
    NodeTypeImp(BaseNode node,SemanticClass clazz)
    {
        this.clazz=clazz;
        this.node=node;
    }
    public String getName()
    {
        return clazz.getName();
    }

    public boolean isMixin()
    {
        return node.isMixIn(clazz);
    }

    public boolean hasOrderableChildNodes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPrimaryItemName()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType[] getSupertypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType[] getDeclaredSupertypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isNodeType(String arg0)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyDefinition[] getPropertyDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyDefinition[] getDeclaredPropertyDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeDefinition[] getChildNodeDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeDefinition[] getDeclaredChildNodeDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canSetProperty(String arg0, Value arg1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canSetProperty(String arg0, Value[] arg1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canAddChildNode(String arg0)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canAddChildNode(String arg0, String arg1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canRemoveItem(String arg0)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
