/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb;

/**
 *
 * @author Jei
 */
public class SWBRuntimeException extends RuntimeException
{
    public SWBRuntimeException()
    {
        super();
    }
    
    public SWBRuntimeException(String msg)
    {
        super(msg);
    }

    public SWBRuntimeException(String msg, Exception e)
    {
        super(msg,e);
    }    

}
