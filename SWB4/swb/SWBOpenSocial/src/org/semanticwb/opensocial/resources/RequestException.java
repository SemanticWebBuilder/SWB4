/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.resources;

/**
 *
 * @author victor.lorenzana
 */
public class RequestException extends Exception {

    private int code=500;
    public RequestException(String message,int code)
    {
        super(message);
        this.code=code;
    }
    public RequestException(Throwable e,int code)
    {
        super(e);
        this.code=code;
    }
    public int getCode()
    {
        return code;
    }
}
