/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.model.Resource;

/**
 * Patr&oacute;n de diseño Decorador (Decorador). 
 * Mantiene una referencia al componente asociado. 
 * Implementa la interfaz de la superclase Componente delegando en el componente asociado.
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class Decorator extends ComponentMap {

    /**
     * Componente que ser&aacute; utilizado como base para decorarlo
     */
    private ComponentMap _componente;

    /**
     * Construye una instancia de tipo {@code Decorator}
     * @param _componente 
     */
    public Decorator(ComponentMap _componente) {
        this._componente = _componente;
    }

    /**
     * Se encarga de poder heredar el m&eacute;todo que construye el Mapa Estrat&eacute;gico
     * @param bsc Elemento de tipo BalanScoreCard, se refiere al BSC seleccionado actualmente
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado actualmente 
     *              en el sitio BSC
     * @param base Elemento de tipo Resource, utilizado para obtener las configuraciones del recurso
     * @return El elemento base con la funcionalidad para heredar y poder agregarle m&aacute;s cosas 
     * a la vista
     */
    @Override
    public StringBuilder draw(BSC bsc, Period period, Resource base) {
        return this._componente.draw(bsc, period, base);
    }

}
