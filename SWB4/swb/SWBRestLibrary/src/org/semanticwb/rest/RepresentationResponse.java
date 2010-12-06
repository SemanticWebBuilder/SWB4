/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author victor.lorenzana
 */
public interface RepresentationResponse extends Representation {
        
    public Object getResponse() throws RestException;    
    public int getStatus();
    public ParameterDefinition[] getParameterDefinitions();
    public void process(HttpURLConnection con) throws ExecutionRestException;
    public void setMethod(Method method);
    public Method getMethod();
    public void setStatus(int status);
    public void setURL(URL url);
}
