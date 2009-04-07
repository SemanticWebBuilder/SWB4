/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 * Entry of a Lexicon and its {@link WordTag}.
 * @author hasdai
 */
public class Word {
    private String label=""; //DisplayName
    private WordTag wTag;   //POS-TAG

    /**
     * Creates a new instance of a Word with the given label.
     * @param lbl Word label.
     */
    public Word(String lbl) {
        label = lbl;
        wTag = new WordTag("", "", "", "");
    }

    /**
     * Creates a new instance of a Word with the given label and {@link WordTag}.
     * @param lbl Word label.
     * @param wt WordTag for the Word..
     */
    public Word(String lbl, WordTag wt) {
        label = lbl;
        wTag = wt;
    }

    /**
     * Gets the label of the current Word instance.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the current Word instance.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the WordTag of the current Word instance.
     */
    public WordTag getTag() {
        return wTag;
    }

    /**
     * Sets the WordTag of the current Word instance.
     */
    public void setTag(WordTag tag) {
        wTag = tag;
    }
}