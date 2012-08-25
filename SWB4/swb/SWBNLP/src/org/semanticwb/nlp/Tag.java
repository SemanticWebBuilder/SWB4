/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.util.HashMap;

/**
 * Tag object for POS-Tagging.
 * <p>
 * Objeto que representa una etiqueta para un POS-Tagging.
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class Tag {
    private String label;                     //Label for the tag
    private double probability;               //Probability of the tag.
    private HashMap<String, String> tagInfo;  //Hash of additional information for the tag.
//    private String URI;
//    private String clsId;
//    private String rangeURI;

    /**
     * Constructor.
     * Creates a new instance of a {@link Tag}.
     * <p>
     * Crea una nueva instancia de un {@link Tag}.
     */
    public Tag () {
        label = "";
        tagInfo = new HashMap<String, String>();
    }

    /**
     * Constructor.
     * Creates a new instance of a {@link Tag} with the specified label.
     * <p>
     * Crea una nueva instancia de un {@link Tag} con la etiqueta especificada.
     * @param t Label.<p>Etiqueta.
     */
    public Tag (String t) {
        label = t;
        tagInfo = new HashMap<String, String>();
    }

    /**
     * Sets the label of the {@link Tag}.
     * <p>
     * Establece la etiqueta del {@link Tag}.
     * @param t Label.<p>. Etiqueta.
     */
    public void setLabel (String t) {
        label = t;
    }    

    /**
     * Gets the label of the {@link Tag}.
     * Obtiene la etiqueta del {@link Tag}.
     * @return The label.<p>La etiqueta.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets a parameter value from the tagInfo hash.
     * <p>
     * Obtiene el valor de un parámetro del has de información del tag.
     * @param param Parameter.<p> Parámetro.
     * @return Parameter value.<p>Valor del parámetro.
     */
    public String getTagInfoParam(String param) {
        String ret = tagInfo.get(param);
        if ( ret == null) {
            ret = "";
        }
        return ret;
    }
    
    public void setTagInfoParam(String param, String val) {
        if (tagInfo.get(param) == null) {
            tagInfo.put(param, val);
        }
    }
    
    public HashMap<String, String> getTagInfo() {
        return tagInfo;
    }
  
    public double getProbability() {
        return probability;
    }

    public boolean equals(Tag tag) {
        boolean ret = false;
        if (tag.getLabel().equalsIgnoreCase(label) && tag.getProbability() == probability) {
            ret = true;
        }
        return ret;
    }    
}
