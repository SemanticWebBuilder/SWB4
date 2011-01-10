/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.resources;

/**
 *
 * @author victor.lorenzana
 */
public class RPCException extends Exception {

    public RPCException(String message)
    {
        super(message);
    }
    public RPCException(Throwable e)
    {
        super(e);
    }
    public RPCException(String message,Throwable cause)
    {
        super(message, cause);
    }
}
