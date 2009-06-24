/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import java.util.HashMap;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author Jei
 */
public interface GenericObject 
{
    /**
     * Regresa uri del objeto
     * @return
     */
    public String getURI();

    /**
     * Regresa Identificador del objeto
     * @return
     */
    public String getId();

    /**
     * Segresa el objeto semantico que contiene la informacion delobjeto
     * @return
     */
    public SemanticObject getSemanticObject();    
    
    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public GenericObject setProperty(String prop, String value);

    /**
     * Elimina una propiedad del objeto
     * @param prop
     * @return
     */
    public GenericObject removeProperty(String prop);

    /**
     * Regresa ruta de trabajo del objeto relativa al directorio work
     * ejemplo: /sep/Template/1
     *          /dominio/Objeto/id
     * 
     * @return String con la ruta relativa al directorio work
     */    
    public String getWorkPath();

    /**
     * Se invoca cuando la instancia del objeto es eliminado de cache
     */
    public void dispose();

}
