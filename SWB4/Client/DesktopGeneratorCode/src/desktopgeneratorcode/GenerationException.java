/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopgeneratorcode;

/**
 *
 * @author victor.lorenzana
 */
public class GenerationException extends Exception{
    public GenerationException(String message)
    {
        super(message);
    }
    public GenerationException(String message,Throwable cause)
    {
        super(message,cause);
    }
}
