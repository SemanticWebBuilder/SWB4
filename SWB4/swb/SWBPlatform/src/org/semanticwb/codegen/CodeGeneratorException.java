/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.codegen;

/**
 *
 * @author victor.lorenzana
 */
public class CodeGeneratorException extends Exception
{
    public CodeGeneratorException(String message)
    {
        super(message);
    }
    public CodeGeneratorException(String message,Throwable cause)
    {
        super(message,cause);
    }
}
