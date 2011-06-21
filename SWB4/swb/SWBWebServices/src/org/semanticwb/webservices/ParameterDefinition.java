/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices;

import org.semanticwb.webservices.util.SchemaClass;

/**
 *
 * @author victor.lorenzana
 */
public interface ParameterDefinition
{
    public String getName();
    public boolean isBasic();
    public boolean isMultiple();
    public String getDefinitionType();
    public String getClassType();
    public boolean isRequired();
    public ParameterDefinition[] getProperties();
    public SchemaClass getDefinitionClass() throws ServiceException;
}
