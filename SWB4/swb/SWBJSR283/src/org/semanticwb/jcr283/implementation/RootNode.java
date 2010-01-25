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
public class RootNode extends NodeImp{

    RootNode(Base base,SessionImp session)
    {
        super(base, null, 0, "/", 0, session,false);
    }
}
