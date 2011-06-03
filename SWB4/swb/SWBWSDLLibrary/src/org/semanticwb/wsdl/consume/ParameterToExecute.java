/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.wsdl.consume;

/**
 *
 * @author victor.lorenzana
 */
public class ParameterToExecute {

    private final Parameter parameter;
    private final ParameterDefinition definition;
    public ParameterToExecute(Parameter parameter,ParameterDefinition definition)
    {
        this.parameter=parameter;
        this.definition=definition;
    }
    public Parameter getParameter()
    {
        return parameter;
    }
    public ParameterDefinition getDefinition()
    {
        return definition;
    }
}
