/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice;

/**
 * Exception base for all WebBuilder Exceptions
 * @author victor.lorenzana
 */
public class WBException extends Exception
{
    /**
     * Contructor with a message
     * @param message Message to display
     */
    public WBException(String message)
    {        
        super(message);
    }
    /**
     * Contructor with a message and cause
     * @param message Message
     * @param cause Cause
     */
    public WBException(String message,Throwable cause)
    {
        super(message,cause);
    }

}
