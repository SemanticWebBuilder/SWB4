/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation;

import java.security.Principal;
import javax.jcr.Credentials;
import org.semanticwb.model.User;

/**
 *
 * @author victor.lorenzana
 */
public class SWBCredentials implements Credentials {
    private Principal principal;
    public SWBCredentials(User principal)
    {
        this.principal=principal;
    }
    public Principal getPrincipal()
    {
        return principal;
    }
}
