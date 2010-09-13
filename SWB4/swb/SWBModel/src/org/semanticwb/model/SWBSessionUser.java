/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBSessionUser.
 * 
 * @author javier.solis
 */
public class SWBSessionUser extends User
{
    
    /**
     * Instantiates a new sWB session user.
     * 
     * @param rep the rep
     */
    public SWBSessionUser(UserRepository rep)
    {
        super(new SemanticObject(rep.getSemanticObject().getModel(),User.swb_User));
    }

    /**
     * Update user.
     * 
     * @param user the user
     */
    public void updateUser(User user)
    {
        m_obj=user.getSemanticObject();
    }

}
