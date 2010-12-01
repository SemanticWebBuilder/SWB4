/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import bsh.Interpreter;
import java.net.HttpURLConnection;

/**
 *
 * @author victor.lorenzana
 */
public interface RepresentationResponse extends Representation {
    
    public Interpreter getInterpreter() throws bsh.EvalError,RestException;
    public ClassLoader getClassLoader() throws bsh.EvalError;
    //public Object getObject() throws bsh.EvalError,RestException;
    public Object getResponse() throws RestException;
    public Object[] getValues(ParameterDefinition definition) throws RestException;    
    public Object getValue(ParameterDefinition definition) throws RestException;
    public int getStatus();
    public ParameterDefinition[] getParameterDefinitions();
    public void process(HttpURLConnection con) throws ExecutionRestException;
}
