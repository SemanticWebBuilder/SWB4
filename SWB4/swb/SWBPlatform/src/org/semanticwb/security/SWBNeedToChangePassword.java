/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.security;

/**
 *
 * @author serch
 */
public class SWBNeedToChangePassword extends RuntimeException{

    public SWBNeedToChangePassword(String message)
    {
        super(message);
    }

}
