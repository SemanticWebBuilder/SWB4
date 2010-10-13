/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.Set;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public interface ServiceProvider {

    public ServiceInfo getServiceInfo();
    public Document executeGET(Set<RestParameter> parameters) throws RestException;
    public void executeDelete(Set<RestParameter> parameters) throws RestException;
    public void executePUT(Set<RestParameter> parameters,Document data) throws RestException;
    public void executePOST(Set<RestParameter> parameters,Document data) throws RestException;
    
}
