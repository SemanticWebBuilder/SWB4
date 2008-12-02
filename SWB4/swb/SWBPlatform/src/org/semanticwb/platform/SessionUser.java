/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import java.security.Principal;

/**
 *
 * @author Jei
 */
public class SessionUser {

    private Principal user;

    public SessionUser(Principal user)
    {
        this.user=user;
    }

    public Principal getUser()
    {
        return user;
    }

    public void setUser(Principal user) {
        this.user = user;
    }

}
