/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.utils;

/**
 *
 * @author carlos.ramos
 */
public class UndefinedFrequencyException extends Exception{
    public UndefinedFrequencyException() {
    }
    
    public UndefinedFrequencyException(String message) {
        super(message);
    }
}
