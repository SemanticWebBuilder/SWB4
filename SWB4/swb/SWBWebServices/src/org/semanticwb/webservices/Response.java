/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices;

/**
 *
 * @author victor.lorenzana
 */
public interface Response
{
    public boolean hasDefinition();
    public ParameterDefinition[] getDefinitions();
}
