/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.Property;
import javax.jcr.nodetype.ItemDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.OnParentVersionAction;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class ItemDefinitionImp implements ItemDefinition {

    private final String name;
    private final boolean isMandatory;
    private final boolean isProtected;
    private final int onParentVersion;
    private final NodeTypeImp nodeType;
    private final boolean isAutoCreated;

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ItemDefinitionImp other = (ItemDefinitionImp) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    public ItemDefinitionImp(SemanticObject obj,NodeTypeImp nodeType)
    {
        this.nodeType=nodeType;
        SemanticProperty prop=NodeTypeImp.getSemanticProperty(Property.JCR_NAME);
        SemanticLiteral value=obj.getLiteralProperty(prop);
        if(value!=null)
        {
            name=value.getString();
        }
        else
        {            
            name=obj.getPrefix()+":"+obj.getRDFName();
        }
        prop= NodeTypeImp.getSemanticProperty(Property.JCR_MANDATORY);
        value=obj.getLiteralProperty(prop);
        if(value!=null)
        {
            isMandatory=value.getBoolean();
        }
        else
        {
            isMandatory=false;
        }
        prop= SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#protected");
        value=obj.getLiteralProperty(prop);
        if(value!=null)
        {
            isProtected=value.getBoolean();
        }
        else
        {
            isProtected=false;
        }
        prop= SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#onParentVersion");
        value=obj.getLiteralProperty(prop);
        if(value!=null)
        {
            onParentVersion= OnParentVersionAction.valueFromName(value.getString());
        }
        else
        {
            onParentVersion=OnParentVersionAction.IGNORE;
        }
        prop= SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#autoCreated");
        value=obj.getLiteralProperty(prop);
        if(value!=null)
        {
            isAutoCreated=value.getBoolean();
        }
        else
        {
            isAutoCreated=false;
        }
    }
    public NodeType getDeclaringNodeType()
    {
        return nodeType;
    }

    public String getName()
    {
        return name;
    }

    public boolean isAutoCreated()
    {
        return isAutoCreated;
    }

    public boolean isMandatory()
    {
        return isMandatory;
    }

    public int getOnParentVersion()
    {
        return onParentVersion;
    }

    public boolean isProtected()
    {
        return isProtected;
    }

}
