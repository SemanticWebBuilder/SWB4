/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import bsh.Interpreter;

/**
 *
 * @author victor.lorenzana
 */
public interface RepresentationResponse extends Representation {
    
    public Interpreter getInterpreter() throws bsh.EvalError,RestException;
    public ClassLoader getClassLoader() throws bsh.EvalError;
    public Object getObject() throws bsh.EvalError,RestException;
}
