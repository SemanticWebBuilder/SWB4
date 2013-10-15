/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.utils;

import java.util.Comparator;
import org.semanticwb.platform.SemanticProperty;

/**
 * Comparador de propiedades semanticas, a fin de obtener listados de estos elementos
 * ordenados por su etiqueta o nombre, en caso de no tener etiquetas.
 * @author jose.jimenez
 */
public class PropertiesComparator implements Comparator {
    
    /**
     * Compara las etiquetas (o los nombres de las propiedades en su defecto) 
     * @param prop1 una propiedad semantica a ordenar de acuerdo a su etiqueta o nombre
     * @param prop2 una segunda propiedad semantica a ordenar de acuerdo a su etiqueta o nombre
     * @return un entero negativo, cero o un entero positivo de acuerdo a si la etiqueta
     *         o nombre de la segunda propiedad especificada se puede ordenar antes, en la 
     *         misma ubicacion o despues que la primera.
     */
    @Override
    public int compare(Object prop1, Object prop2) {

        int comparacion;
        String s1 = new String();
        String s2 = new String();
        if (prop1 instanceof SemanticProperty) {
            if (prop1 != null && ((SemanticProperty) prop1).getDisplayName() != null) {
                s1 = ((SemanticProperty) prop1).getDisplayName();
            }
        }
        if (prop2 instanceof SemanticProperty) {
            if (prop2 != null && ((SemanticProperty) prop2).getDisplayName() != null) {
                s2 = ((SemanticProperty) prop2).getDisplayName();
            }
        }
        comparacion = s1.compareTo(s2);
        return comparacion;
    }
}
