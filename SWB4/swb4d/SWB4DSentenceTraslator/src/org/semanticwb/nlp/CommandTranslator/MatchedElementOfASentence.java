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
    private String SubsetSimilar;
    private String ElementSimilar;
    private double similarity;
    
    public MatchedElementOfASentence(String SubsetSimilar, String ElementSimilar, double similarity) {
        this.SubsetSimilar = SubsetSimilar;
        this.ElementSimilar = ElementSimilar;
        this.similarity = similarity;
    }

    public String getSubsetSimilar() {
        return SubsetSimilar;
    }

    public String getElementSimilar() {
        return ElementSimilar;
    }

    public double getSimilarity() {
        return similarity;
    }
    
}
