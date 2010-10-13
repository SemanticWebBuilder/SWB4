/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public abstract class AbstractServiceInfo implements ServiceInfo {

    protected final String name;
    protected final Set<ParameterInfo> parameters;
    public AbstractServiceInfo(String name,Set<ParameterInfo> parameters)
    {
        this.name=name;
        this.parameters=parameters;
    }
    public String getName()
    {
        return name;
    }

    public abstract Set<ParameterInfo> getParameters();


}
