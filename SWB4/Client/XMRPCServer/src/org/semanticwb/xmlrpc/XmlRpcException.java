/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

/**
 *
 * @author victor.lorenzana
 */
public class XmlRpcException extends Exception {

    public XmlRpcException(String message)
    {
        super(message);
    }
    public XmlRpcException(Throwable cause)
    {
        super(cause);
    }
    public XmlRpcException(String message,Throwable cause)
    {
        super(message,cause);
    }
}
