/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.wsdl.consume;

/**
 *
 * @author victor.lorenzana
 */
public class Parameter {

    private final String name;
    private final Object value;
    private ParameterDefinition definition;
    public Parameter(String name,Object value)
    {
        this.name=name;
        this.value=value;
    }
    public Parameter(String name,Object value,ParameterDefinition definition)
    {
        this.name=name;
        this.value=value;
    }
    public ParameterDefinition getDefinition()
    {
        return definition;
    }
    public String getName()
    {
        return name;
    }
    public Object getValue()
    {
        return value;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Parameter other = (Parameter) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return name.toString();
    }

    
}
