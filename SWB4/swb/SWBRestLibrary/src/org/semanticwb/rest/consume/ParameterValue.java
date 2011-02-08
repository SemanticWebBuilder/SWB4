/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.consume;

/**
 *
 * @author victor.lorenzana
 */
public class ParameterValue {

    private final String name;
    private Object value;
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
    public void setValue(Object value)
    {
        this.value=value;
    }
    public Object getValue()
    {
        return value;
    }
}
