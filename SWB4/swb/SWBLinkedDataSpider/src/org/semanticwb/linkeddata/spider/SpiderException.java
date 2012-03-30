/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.linkeddata.spider;

/**
 *
 * @author victor.lorenzana
 */
public class SpiderException extends Exception {

    public SpiderException(String message)
    {
        super(message);
    }

    public SpiderException(String message,Throwable cause)
    {
        super(message,cause);
    }

     public SpiderException(Throwable cause)
    {
        super(cause);
    }
}
