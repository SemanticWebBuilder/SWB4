/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.version.OnParentVersionAction;

/**
 *
 * @author victor.lorenzana
 */
public final class RootNodeDefinition extends NodeDefinitionImp  {

    public RootNodeDefinition(NodeTypeImp nodeType,NodeTypeImp defaultPrimaryType)
    {
        super("", true, false, OnParentVersionAction.VERSION, nodeType, true, false, defaultPrimaryType);
    }
}
