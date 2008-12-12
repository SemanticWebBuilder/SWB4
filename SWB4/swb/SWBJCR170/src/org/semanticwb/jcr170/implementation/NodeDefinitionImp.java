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

/**
 *
 * @author victor.lorenzana
 */
public class NodeDefinitionImp implements NodeDefinition
{
    private ArrayList<NodeType> requiredPrimaryTypes=new ArrayList<NodeType>();
    private NodeType defaultPrimaryType=null;
    private boolean allowsSameNameSiblings=true;
    private NodeType declaringNodeType;
    private String name="*";
    private int onParentVerion=OnParentVersionAction.VERSION;
    private boolean autocreated,mandatory,isProtected;
    NodeDefinitionImp(SemanticObject object,SessionImp session)
    {
        BaseNode node=new BaseNode(object);
        name=object.getProperty(BaseNode.vocabulary.jcr_name);
        autocreated=object.getBooleanProperty(BaseNode.vocabulary.jcr_autoCreated, false);
        mandatory=object.getBooleanProperty(BaseNode.vocabulary.jcr_mandatory, false);
        String sOnParentVersion=object.getProperty(BaseNode.vocabulary.jcr_onParentVersion);
        if(sOnParentVersion!=null)
        {
            onParentVerion=OnParentVersionAction.valueFromName(sOnParentVersion);
        }
        isProtected=object.getBooleanProperty(BaseNode.vocabulary.jcr_protected, false);
        Iterator<SemanticLiteral> values= object.getSemanticClass().listRequiredProperties(BaseNode.vocabulary.jcr_requiredPrimaryTypes);
        while(values.hasNext())
        {
            String value=values.next().getString();
            try
            {
                SemanticClass clazz=node.getSemanticClass(value);
                requiredPrimaryTypes.add(new NodeTypeImp(clazz, session));
            }
            catch(Exception e)
            {

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
