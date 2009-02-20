/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 *
 * @author hasdai
 */
public class WordTag {
    private String lbl; //Etiqueta de la palabra a nivel sintáctico : OBJ, PREC, PRED, etc.
    private String TagType; //tipo de dato en RDF : swb:WebPage, swbxf:Form, etc.

    public WordTag(String t, String tt)
    {
        lbl = t;
        TagType = tt;
    }

    public String getLabel()
    {
        return lbl;
    }

    public String getType()
    {
        return TagType;
    }

    public void setLabel(String t)
    {
        lbl = t;
    }

    public void setType(String tt)
    {
        TagType = tt;
    }
}