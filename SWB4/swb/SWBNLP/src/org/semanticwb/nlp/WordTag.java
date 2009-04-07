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
    private String name;   //nombre de la clase que representa la palabra.
    private String oId;    //Id de la clase que representa la palabra

    public void setObjId(String oId) {
        this.oId = oId;
    }
    public String getObjId() {
        return oId;
    }
    /**
     * Creates a new instance of a WordTag with the given label and TagType.
     * @param t Tag for the compound Tag.
     * @param tt TagType for the compound Tag.
     */
    public WordTag(String label, String typ, String cName, String cId)
    {
        lbl = label;
        TagType = typ;
        name = cName;
        oId = cId;
    }

    /**
     * Gets the label of the current WordTag.
     */
    public String getTag()
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

    public String getWClassName() {
        return name;
    }

    public void setClassName(String newName) {
        name = newName;
    }
}