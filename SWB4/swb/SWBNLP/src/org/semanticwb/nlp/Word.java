/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 * Palabra del diccionario con una etiqueta compuesta.
 * @author hasdai
 */
public class Word {
    private String label=""; //DisplayName
    private WordTag wTag;   //POS-TAG
    private int position;   //Posición en la oración

    /**
     * Constructor.
     * @param lbl etiqueta de la palabra.
     */
    public Word(String lbl) {
        label = lbl;
        wTag = new WordTag("", "");
    }

    /**
     * Constructor.
     * @param lbl etiqueta de la palabra.
     * @param wt tag de la palabra.
     */
    public Word(String lbl, WordTag wt) {
        label = lbl;
        wTag = wt;
    }

    /**
     * Devuelve la etiqueta de la palabra.
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Establece la etiqueta de la palabra.
     * @param label etiqueta (palabra).
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Obtiene el tag compuesto de la palabra.
     * @return Tag de la palabra.
     */
    public WordTag getTag() {
        return wTag;
    }

    /**
     * Establece el tag compuesto (tag, tipo) de la palabra.
     * @param tag Etiqueta compuesta para la palabra.
     */
    public void setTag(WordTag tag) {
        wTag = tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        position = pos;
    }
}