/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author javier.solis
 */
public class SWBSessionUser extends User
{
    public SWBSessionUser(UserRepository rep)
    {
        super(new SemanticObject(rep.getSemanticObject().getModel(),User.swb_User));
    }

    public void updateUser(User user)
    {
        m_obj=user.getSemanticObject();
    }

}
