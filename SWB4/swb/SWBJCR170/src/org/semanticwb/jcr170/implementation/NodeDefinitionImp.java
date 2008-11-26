/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.OnParentVersionAction;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class NodeDefinitionImp implements NodeDefinition
{
    private NodeType[] getRequiredPrimaryTypes=null;
    private NodeType getDefaultPrimaryType=null;
    private boolean allowsSameNameSiblings=true;
    private NodeType getDeclaringNodeType;
    private String name;
    private int onParentVerion=OnParentVersionAction.VERSION;
    private boolean autocreated,mandatory,isProtected;
    NodeDefinitionImp(SemanticObject object)
    {
        name=object.getProperty(BaseNode.vocabulary.jcr_name);
        autocreated=object.getBooleanProperty(BaseNode.vocabulary.jcr_autoCreated, false);
        mandatory=object.getBooleanProperty(BaseNode.vocabulary.jcr_mandatory, false);
        String sOnParentVersion=object.getProperty(BaseNode.vocabulary.jcr_onParentVersion);
        if(sOnParentVersion!=null)
        {
            onParentVerion=OnParentVersionAction.valueFromName(sOnParentVersion);
        }
        isProtected=object.getBooleanProperty(BaseNode.vocabulary.jcr_protected, false);
    }
    public NodeType[] getRequiredPrimaryTypes()
    {
        return getRequiredPrimaryTypes;
    }

    public NodeType getDefaultPrimaryType()
    {
        return getDefaultPrimaryType;
    }

    public boolean allowsSameNameSiblings()
    {
        return allowsSameNameSiblings;
    }

    public NodeType getDeclaringNodeType()
    {
        return getDeclaringNodeType;
    }

    public String getName()
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
