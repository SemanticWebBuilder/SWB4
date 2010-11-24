/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.resources.rest;

/**
 *
 * @author victor.lorenzana
 */
public class ParameterValue {

    private final String name;
    private final Object value;
    public ParameterValue(String name,Object value)
    {
        this.name=name;
        this.value=value;
    }
    public ParameterValue(Parameter parameter,Object value)
    {
        this.name=parameter.getName();
        this.value=value;
    }
    public String getName()
    {
        return name;
    }
    public Object getValue()
    {
        return value;
    }
}
