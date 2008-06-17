/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

/**
 *
 * @author victor.lorenzana
 */
public class WBOfficeException extends WBException
{
    /**
     * Constructor with message and a cause an the Office Error
     * @param message Message riginal
     * @param cause Cause in the Open Office Application
     */
    public WBOfficeException(String message, com.sun.star.uno.Exception cause)
    {
        super(message, cause);
    }
}
