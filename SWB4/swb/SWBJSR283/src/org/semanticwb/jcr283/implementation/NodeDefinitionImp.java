/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.Property;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class NodeDefinitionImp extends ItemDefinitionImp implements NodeDefinition  {

    
    
    public NodeDefinitionImp(SemanticObject obj,NodeTypeImp nodeType)
    {
        super(obj, nodeType);

    }
    public NodeType[] getRequiredPrimaryTypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getRequiredPrimaryTypeNames()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType getDefaultPrimaryType()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDefaultPrimaryTypeName()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean allowsSameNameSiblings()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }    

}
