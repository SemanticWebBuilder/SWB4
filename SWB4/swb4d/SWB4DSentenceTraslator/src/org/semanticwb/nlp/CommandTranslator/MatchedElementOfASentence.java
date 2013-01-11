/*
 * Estructura de datos para almacenar un elemento encontrado dentro de una 
 * cadena:ElementSimilar, la subcadena que tuvo correspondencia con dicho
 * elemento:SubsetSimilar y el grado de similaridad de ambos elementos.
 */
package org.semanticwb.nlp.CommandTranslator;

/**
 * @author vieyra
 * samuel.vieyra@infotec.com.mx
 */
public class MatchedElementOfASentence {
    private String subsetSimilar;
    private String elementSimilar;
    private double similarity;
    
    public MatchedElementOfASentence(String subsetSimilar, String elementSimilar, double similarity) {
        this.subsetSimilar = subsetSimilar;
        this.elementSimilar = elementSimilar;
        this.similarity = similarity;
    }

    public String getSubsetSimilar() {
        return subsetSimilar;
    }

    public String getElementSimilar() {
        return elementSimilar;
    }

    public double getSimilarity() {
        return similarity;
    }
    
}
