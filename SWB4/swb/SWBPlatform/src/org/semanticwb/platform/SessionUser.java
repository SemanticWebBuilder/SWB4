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
    private static long req=0;

    public SessionUser(Principal user)
    {
        this.user=user;
        req++;
    }

    public Principal getUser()
    {
        return user;
    }

    public void setUser(Principal user) {
        this.user = user;
        req++;
    }

    public long geRequestID()
    {
        return req;
    }

}
