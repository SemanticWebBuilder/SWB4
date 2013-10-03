/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.model.Resource;

/**
 * Patrón de diseño Decorador (Componente). 
 * Define la interfaz para los objetos que pueden tener responsabilidades añadidas.
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class ComponentMap {

    /**
     * M&eacute;todo que permite dibujar un mapa estrat&eacute;gico como base
     * @param bsc Elemento de tipo BalanScoreCard, se refiere al BSC seleccionado actualmente
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado actualmente 
     *              en el sitio BSC
     * @param base Elemento de tipo Resource, utilizado para obtener las configuraciones del recurso
     * @return La base del componente en este caso un Mapa Estrat&eacute;gico
     */
    public abstract StringBuilder draw (BSC bsc, Period period, Resource base);
}
