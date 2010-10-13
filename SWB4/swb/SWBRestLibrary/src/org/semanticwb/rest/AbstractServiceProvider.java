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
public abstract class AbstractServiceProvider implements ServiceProvider {

    protected final ServiceInfo serviceInfo;   
    public AbstractServiceProvider(ServiceInfo serviceInfo)
    {
        this.serviceInfo=serviceInfo;
    }
    public ServiceInfo getServiceInfo()
    {
        return serviceInfo;
    }

    public abstract  Document executeGET(Set<RestParameter> parameters) throws RestException;
    

    public abstract void executeDelete(Set<RestParameter> parameters) throws RestException;
    

    public abstract void executePUT(Set<RestParameter> parameters, Document data) throws RestException;
    

    public abstract void executePOST(Set<RestParameter> parameters, Document data) throws RestException;
    

}
