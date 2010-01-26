/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import org.semanticwb.jcr283.repository.model.Base;

/**
 *
 * @author victor.lorenzana
 */
public class RootNodeImp extends NodeImp {

    public RootNodeImp(Base base,SessionImp session)
    {
        super(new RootNodeDefinition(NodeTypeManagerImp.loadNodeType(base.getSemanticObject().getSemanticClass()), NodeTypeManagerImp.loadNodeType(base.getSemanticObject().getSemanticClass())),base, null, 0, "/", 0, session);
    }
}
