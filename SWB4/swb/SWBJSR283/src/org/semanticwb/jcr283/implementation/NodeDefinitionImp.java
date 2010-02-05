/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.Property;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import org.semanticwb.SWBPlatform;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class NodeDefinitionImp extends ItemDefinitionImp implements NodeDefinition
{

    private final NodeTypeImp defaultPrimaryType;
    private final boolean allowsSameNameSiblings;
    private final HashSet<NodeTypeImp> requiredPrimaryTypes = new HashSet<NodeTypeImp>();

    public NodeDefinitionImp(String name, boolean isMandatory, boolean isProtected, int onParentVersion, NodeTypeImp nodeType, boolean isAutoCreated, boolean allowsSameNameSiblings, NodeTypeImp defaultPrimaryType)
    {
        super(name, isMandatory, isProtected, onParentVersion, nodeType, isAutoCreated);
        this.allowsSameNameSiblings = allowsSameNameSiblings;
        this.defaultPrimaryType = defaultPrimaryType;
        requiredPrimaryTypes.add(defaultPrimaryType);
    }

    public NodeDefinitionImp(SemanticObject obj, NodeTypeImp nodeType)
    {
        super(obj, nodeType);
        SemanticProperty prop = NodeTypeImp.getSemanticProperty(Property.JCR_DEFAULT_PRIMARY_TYPE);
        SemanticLiteral value = obj.getLiteralProperty(prop);
        if (value != null)
        {
            NodeTypeImp temp = null;
            String name = value.getString();
            Iterator<SemanticClass> classes = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
            while (classes.hasNext())
            {
                SemanticClass clazz = classes.next();
                String onametest = clazz.getPrefix() + ":" + clazz.getName();
                if (onametest.equals(name))
                {
                    temp = NodeTypeManagerImp.loadNodeType(clazz);
                    break;
                }
            }
            defaultPrimaryType = temp;
        }
        else
        {
            defaultPrimaryType = null;
        }

        prop = NodeTypeImp.getSemanticProperty(Property.JCR_SAME_NAME_SIBLINGS);
        value = obj.getLiteralProperty(prop);
        if (value != null)
        {
            allowsSameNameSiblings = value.getBoolean();
        }
        else
        {
            allowsSameNameSiblings = false;
        }


        prop = NodeTypeImp.getSemanticProperty(Property.JCR_REQUIRED_PRIMARY_TYPES);
        Iterator<SemanticLiteral> values = obj.listLiteralProperties(prop);
        while (values.hasNext())
        {
            SemanticLiteral ovalue = values.next();
            String name = ovalue.getString();
            Iterator<SemanticClass> classes = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
            while (classes.hasNext())
            {
                SemanticClass clazz = classes.next();
                String onametest = clazz.getPrefix() + ":" + clazz.getName();
                if (onametest.equals(name))
                {
                    requiredPrimaryTypes.add(NodeTypeManagerImp.loadNodeType(clazz));
                }
            }
        }
    }

    public NodeType[] getRequiredPrimaryTypes()
    {
        return requiredPrimaryTypes.toArray(new NodeType[requiredPrimaryTypes.size()]);
    }

    public NodeTypeImp[] getRequiredPrimaryTypesImp()
    {
        return requiredPrimaryTypes.toArray(new NodeTypeImp[requiredPrimaryTypes.size()]);
    }

    public void removeNodeType(NodeTypeImp nodeType)
    {
    }

    public String[] getRequiredPrimaryTypeNames()
    {
        HashSet<String> requiredPrimaryTypeNames = new HashSet<String>();
        for (NodeType nodeType : getRequiredPrimaryTypes())
        {
            requiredPrimaryTypeNames.add(nodeType.getName());
        }
        return requiredPrimaryTypeNames.toArray(new String[requiredPrimaryTypeNames.size()]);
    }

    public NodeType getDefaultPrimaryType()
    {
        return defaultPrimaryType;
    }

    public NodeTypeImp getDefaultPrimaryTypeImp()
    {
        return defaultPrimaryType;
    }

    public String getDefaultPrimaryTypeName()
    {
        if (defaultPrimaryType == null)
        {
            return null;
        }
        else
        {
            return defaultPrimaryType.getName();
        }
    }

    public boolean allowsSameNameSiblings()
    {
        return allowsSameNameSiblings;
    }

    public static NodeDefinitionImp getNodeDefinition(String name, NodeImp parent, NodeTypeImp nodeType)
    {
        NodeDefinitionImp childDefinition = null;
        for (NodeDefinitionImp childNodeDefinition : parent.nodeType.getChildNodeDefinitionsImp())
        {
            if (childNodeDefinition.getName().equals(name))
            {
                if (nodeType == null)
                {
                    childDefinition = childNodeDefinition;
                    break;
                }
                else
                {
                    for (NodeTypeImp requiredNodeType : childDefinition.getRequiredPrimaryTypesImp())
                    {
                        if (nodeType.isNodeType(requiredNodeType.getName()))
                        {
                            childDefinition = childNodeDefinition;
                            break;
                        }
                    }
                }

            }
        }
        if (childDefinition == null)
        {
            for (NodeDefinitionImp childNodeDefinition : parent.nodeType.getChildNodeDefinitionsImp())
            {
                if (childNodeDefinition.getName().equals("*"))
                {
                    if (nodeType == null)
                    {
                        childDefinition = childNodeDefinition;
                        break;
                    }
                    else
                    {
                        for (NodeTypeImp requiredNodeType : childDefinition.getRequiredPrimaryTypesImp())
                        {
                            if (nodeType.isNodeType(requiredNodeType.getName()))
                            {
                                childDefinition = childNodeDefinition;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return childDefinition;
    }

    public static NodeDefinitionImp getNodeDefinition(Base base, NodeImp parent,NodeTypeImp nodeType)
    {
        return getNodeDefinition(base.getName(), parent,nodeType);
    }
}
