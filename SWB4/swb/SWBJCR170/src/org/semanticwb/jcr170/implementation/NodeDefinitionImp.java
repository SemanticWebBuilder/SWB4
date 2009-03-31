/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.OnParentVersionAction;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.repository.BaseNode;
import org.semanticwb.repository.ChildNodeDefinition;

/**
 *
 * @author victor.lorenzana
 */
public class NodeDefinitionImp implements NodeDefinition
{

    private ArrayList<NodeType> requiredPrimaryTypes = new ArrayList<NodeType>();
    private NodeTypeImp defaultPrimaryType = null;
    private boolean allowsSameNameSiblings = true;
    private NodeTypeImp declaringNodeType;
    private String name = "*";
    private int onParentVerion = OnParentVersionAction.VERSION;
    private boolean autocreated,  mandatory,  isProtected;

    NodeDefinitionImp(SemanticObject object, SessionImp session)
    {
        BaseNode node = new BaseNode(object);

        declaringNodeType = new NodeTypeImp(object.getSemanticClass(), session);
        name = node.getName();
        if (node.getParent() != null)
        {
            BaseNode parent = node.getParent();
            SemanticObject childNodeDefinition = parent.getChildNodeDefinition(parent.getSemanticObject().getSemanticClass(), node.getName());
            String sdefaultPrimaryType = childNodeDefinition.getProperty(ChildNodeDefinition.jcr_defaultPrimaryType);
            if (sdefaultPrimaryType != null)
            {
                try
                {
                    SemanticClass classdefaultPrimaryType = parent.getSemanticClass(sdefaultPrimaryType);
                    defaultPrimaryType = new NodeTypeImp(classdefaultPrimaryType, session);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            autocreated = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_autoCreated, false);
            mandatory = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_mandatory, false);
            String sOnParentVersion = childNodeDefinition.getProperty(ChildNodeDefinition.jcr_onParentVersion);
            if (sOnParentVersion != null)
            {
                onParentVerion = OnParentVersionAction.valueFromName(sOnParentVersion);
            }
            isProtected = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_protected, false);
            allowsSameNameSiblings = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_sameNameSiblings, false);
            Iterator<SemanticLiteral> values = childNodeDefinition.getSemanticClass().listRequiredProperties(ChildNodeDefinition.jcr_requiredPrimaryTypes);
            while (values.hasNext())
            {
                String value = values.next().getString();
                try
                {
                    SemanticClass clazz = node.getSemanticClass(value);
                    requiredPrimaryTypes.add(new NodeTypeImp(clazz, session));
                }
                catch (Exception e)
                {
                }
            }
        }
    }

    public NodeType[] getRequiredPrimaryTypes()
    {
        return requiredPrimaryTypes.toArray(new NodeTypeImp[requiredPrimaryTypes.size()]);
    }

    public NodeType getDefaultPrimaryType()
    {
        return defaultPrimaryType;
    }

    public boolean allowsSameNameSiblings()
    {
        return allowsSameNameSiblings;
    }

    public NodeType getDeclaringNodeType()
    {
        return declaringNodeType;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public boolean isAutoCreated()
    {
        return autocreated;
    }

    public boolean isMandatory()
    {
        return mandatory;
    }

    public int getOnParentVersion()
    {
        return onParentVerion;
    }

    public boolean isProtected()
    {
        return isProtected;
    }
}
