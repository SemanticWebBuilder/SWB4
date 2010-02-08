/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.security.Principal;

/**
 *
 * @author victor.lorenzana
 */
public final class TrustedPrincipal implements Principal {

    String name;
    public TrustedPrincipal(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }

}
