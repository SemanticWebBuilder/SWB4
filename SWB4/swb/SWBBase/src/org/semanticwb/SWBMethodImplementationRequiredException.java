/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb;

/**
 *
 * @author Jei
 */
public class SWBMethodImplementationRequiredException extends SWBRuntimeException
{
    public SWBMethodImplementationRequiredException()
    {
        super();
    }
    
    public SWBMethodImplementationRequiredException(String msg)
    {
        super(msg);
    }

    public SWBMethodImplementationRequiredException(String msg, Exception e)
    {
        super(msg,e);
    }  
}
