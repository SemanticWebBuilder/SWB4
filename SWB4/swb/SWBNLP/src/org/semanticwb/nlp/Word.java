/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * Entry of a Lexicon and its {@link WordTag}.
 * @author hasdai
 */
public class Word {
    private SemanticProperty prop;
    private SemanticClass cls;
    private String label=""; //DisplayName
    private WordTag wTag;   //POS-TAG
    private boolean isClass = true;

    /**
     * Creates a new instance of a Word with the given label.
     * @param lbl Word label.
     */
    public Word(String lbl) {
        label = lbl;
        wTag = new WordTag("", "", "", "", "");
    }

    public Word(SemanticClass cl) {
        isClass = true;
        cls = cl;
    }

    public Word (SemanticProperty p) {
        isClass = false;
        prop = p;
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