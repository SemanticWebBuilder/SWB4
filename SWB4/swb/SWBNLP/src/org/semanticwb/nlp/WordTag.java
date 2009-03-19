/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 * Compound tag for a Word.
 * @author hasdai
 */
public class WordTag {
    private String lbl; //Etiqueta de la palabra a nivel sintáctico : OBJ, PREC, PRED, etc.
    private String TagType; //tipo de dato en RDF : swb:WebPage, swbxf:Form, etc.

    /**
     * Creates a new instance of a WordTag with the given label and TagType.
     * @param t Tag for the compound Tag.
     * @param tt TagType for the compound Tag.
     */
    public WordTag(String t, String tt)
    {
        lbl = t;
        TagType = tt;
    }

    /**
     * Gets the label of the current WordTag.
     */
    public String getLabel()
    {
        return lbl;
    }

    /**
     * Sets the type of the current WordTag.
     */
    public String getType()
    {
        return TagType;
    }

    /**
     * Sets the label of the current WordTag.
     */
    public void setLabel(String t)
    {
        lbl = t;
    }

    /**
     * Sets the type of the current WordTag.
     */
    public void setType(String tt)
    {
        TagType = tt;
    }
}