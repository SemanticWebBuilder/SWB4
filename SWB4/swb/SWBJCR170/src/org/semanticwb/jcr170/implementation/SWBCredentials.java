/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation;

import java.security.Principal;
import javax.jcr.Credentials;

/**
 *
 * @author victor.lorenzana
 */
public class SWBCredentials implements Credentials {
    private Principal principal;
    public SWBCredentials(Principal principal)
    {
        this.principal=principal;
    }
    public Principal getPrincipal()
    {
        return principal;
    }
}
