/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import javax.jcr.Value;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.PropertyDefinition;
import org.semanticwb.SWBException;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class NodeTypeImp implements NodeType
{

    private static final String DEFAULT_PRIMARY_NODE_TYPE_NAME = "nt:unstructured";
    private final SemanticClass clazz;
    private final SessionImp session;

    NodeTypeImp(SemanticClass clazz, SessionImp session)
    {
        this.clazz = clazz;
        this.session = session;
    }

    public String getName()
    {
        return clazz.getName();
    }

    public boolean isMixin()
    {
        return session.getRootBaseNode().isMixIn(clazz);
    }

    public boolean hasOrderableChildNodes()
    {
        return session.getRootBaseNode().hasOrderableChildNodes(clazz);
    }

    public String getPrimaryItemName()
    {
        return session.getRootBaseNode().getPrimaryItemName(clazz);
    }

    public NodeType[] getSupertypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType[] getDeclaredSupertypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isNodeType(String nodeTypeName)
    {
        boolean isNodeType = false;
        try
        {
            session.getRootBaseNode().getSemanticClass(nodeTypeName);
            isNodeType = true;
        }
        catch ( SWBException e )
        {

        }
        return isNodeType;
    }

    public PropertyDefinition[] getPropertyDefinitions()
    {
        ArrayList<PropertyDefinitionImp> propertyDefinitions = new ArrayList<PropertyDefinitionImp>();
        Iterator<SemanticProperty> properties = session.getRootBaseNode().listSemanticProperties(clazz);
        while (properties.hasNext())
        {
            SemanticProperty prop = properties.next();
            propertyDefinitions.add(new PropertyDefinitionImp(session, prop));
        }
        return propertyDefinitions.toArray(new PropertyDefinitionImp[propertyDefinitions.size()]);
    }

    public PropertyDefinition[] getDeclaredPropertyDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeDefinition[] getChildNodeDefinitions()
    {
        ArrayList<NodeDefinitionImp> definitions = new ArrayList<NodeDefinitionImp>();
        for ( SemanticObject object : session.getRootBaseNode().getChildNodeDefinition(clazz) )
        {
            definitions.add(new NodeDefinitionImp(object));
        }
        return definitions.toArray(new NodeDefinitionImp[definitions.size()]);
    }

    public NodeDefinition[] getDeclaredChildNodeDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canSetProperty(String propertyName, Value value)
    {
        Value[] values = {value};
        return canSetProperty(propertyName, values);
    }

    public boolean canSetProperty(String propertyName, Value[] value)
    {
        boolean canSetProperty = false;
        if ( session.getRootBaseNode().canAddAnyProperty(clazz) )
        {
            canSetProperty = true;
        }
        else
        {
            for ( PropertyDefinition propdef : this.getPropertyDefinitions() )
            {
                if ( propdef.getName().equals(propertyName))
                {
                    if(propdef.isMultiple() || value.length==1)
                    {
                        canSetProperty = true;
                        break;
                    }
                }
            }
        }
        return canSetProperty;
    }

    public boolean canAddChildNode(String childNodeName)
    {
        return canAddChildNode(childNodeName, DEFAULT_PRIMARY_NODE_TYPE_NAME);
    }

    public boolean canAddChildNode(String childNodeName, String nodeTypeName)
    {
        boolean canAddChildNode = false;
        try
        {
            SemanticClass clazzToInsert = session.getRootBaseNode().getSemanticClass(nodeTypeName);
            return session.getRootBaseNode().canAddNode(clazzToInsert,clazz);
        }
        catch ( SWBException swbe )
        {

        }
        return canAddChildNode;
    }

    public boolean canRemoveItem(String arg0)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
