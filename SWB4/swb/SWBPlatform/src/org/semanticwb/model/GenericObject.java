/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author Jei
 */
public interface GenericObject 
{
    public String getURI();
    
    public String getId();
    
    public SemanticObject getSemanticObject();    
    
    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public GenericObject setProperty(String prop, String value);
    
    public GenericObject removeProperty(String prop);

    /**
     * Regresa ruta de trabajo del objeto relativa al directorio work
     * ejemplo: /sep/Template/1
     *          /dominio/Objeto/id
     * 
     * @return String con la ruta relativa al directorio work
     */    
    public String getWorkPath();
}
