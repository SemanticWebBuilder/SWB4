/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

/**
 * En procesamiento del lenguaje natural, durante el análisis, se asocia una
 * etiqueta a las palabras (POS Tag) que indica su función dentro de una oración.
 * La etiqueta generalmente incorpora información léxica y morfológica adicional
 * útil para tareas de desambiguación.
 *
 * (Información específica de SemanticWebBuilder) La etiqueta de cada palabra se
 * compone de su función sintáctica (OBJ, PREC, PRED, etc.), el tipo de clase
 * que representa la palabra en la ontología (swb:WebPage, swbx:Form, etc), el
 * nombre de la clase asociada a la palabra, su id y su rango (en caso de
 * tratarse de una propiedad de tipo ObjectProperty).
 *
 * In the analysis stage of natural language processing, a tag (POS tag) is
 * associated to words, which marks the word's function in the sentence.
 * Generally the tag encodes additional lexical and morfo information, useful
 * for disambiguation tasks.
 *
 * (Specific SemanticWebBuilder information) The tag for each {@link Word} is
 * formed with its sintactic function (OBJ, PREC, PRED, etc.), the class type
 * of the associated object in the ontology (swb:WebPage, swbx:Form, etc.), the
 * name of the class associated to the word, its ID and range (if it is an
 * ObjectProperty).
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class WordTag {
    /**
     * POS tag for the word.
     * Etiqueta de la palabra a nivel sintáctico.
     */
    private String lbl; //Etiqueta de la palabra a nivel sintáctico : OBJ, PREC, PRED, etc.
    /**
     * Class type of the object in the ontology associated to the word.
     * Tipo de dato en la ontología para el objeto asociado a la palabra.
     */
    private String TagType; //tipo de dato en RDF : swb:WebPage, swbxf:Form, etc.
    /**
     * Class name of the object in the ontology associated to the word.
     * Nombre del objeto en la ontología asociado a la palabra.
     */
    private String name;   //nombre de la clase que representa la palabra.
    /**
     * ID of the object in the ontology associated to the word.
     * Identificador del objeto en la ontología asociado a la palabra.
     */
    private String oId;    //Id de la clase que representa la palabra
    private String rangeName;
    private String snowballForm;

    /**
     * Creates a new instance of a {@link WordTag} with the given label, TagType,
     * class name, class id and range class name.
     *
     * Crea una nueva instancia de un {@link WordTag} con la forma léxica, tipo,
     * nombre de clase, id de clase y rango especificados.
     * @param label Tag for the {@link Word}. Etiqueta de la palabra.
     * @param typ Class type of the object in the ontology associated to the word.
     * Tipo de dato en la ontología para el objeto asociado a la palabra.
     * @param cName Class name of the object in the ontology associated to the word.
     * Nombre del objeto en la ontología asociado a la palabra.
     * @param cId ID of the object in the ontology associated to the word.
     * Identificador del objeto en la ontología asociado a la palabra.
     * @param rang
     */
    public WordTag(String label, String snowball, String typ, String cName, String cId, String rang)
    {
        lbl = label;
        snowballForm = snowball;
        TagType = typ;
        name = cName;
        rangeName = rang;
        oId = cId;
    }

    /**
     * Gets the range class name of the tag.
     * Obtiene el nombre del rango de la etiqueta.
     * @return
     */
    public String getRangeClassId() {
        return rangeName;
    }

    /**
     * Sets the range class name of the tag.
     * Establece el nombre del rango de la etiqueta.
     * @param rangeName New range class name. Nuevo nombre de la clase rango.
     */
    public void setRangeClassId(String rangeName) {
        this.rangeName = rangeName;
    }

    /**
     * Sets the class ID of the tag.
     * Establece el ID de la clase de la etiqueta.
     * @param oId
     */
    public void setObjId(String oId) {
        this.oId = oId;
    }

    /**
     * Gets the class ID of the tag.
     * Obtiene el ID de la clase de la etiqueta.
     * @return
     */
    public String getObjId() {
        return oId;
    }

    /**
     * Gets the label of the current {@link WordTag}.
     * Obtiene la etiqueta de la palabra en el {@link WordTag}.
     */
    public String getTag()
    {
        return lbl;
    }

    /**
     * Gets the type of the current {@link WordTag}.
     * Obtiene el tipo de clase en el {@link WordTag}.
     */
    public String getType()
    {
        return TagType;
    }

    /**
     * Sets the label of the current {@link WordTag}.
     * Establece el nombre de la etiqueta del {@link WordTag}
     */
    public void setTagLabel(String t)
    {
        this.lbl = t;
    }

    /**
     * Sets the type of the current {@link WordTag}.
     * Establece el tipo de clase en el {@link WordTag}.
     */
    public void setType(String tt)
    {
        this.TagType = tt;
    }

    public String getSnowballForm() {
        return snowballForm;
    }

    public void setSnowballForm(String snowballForm) {
        this.snowballForm = snowballForm;
    }
    
    public String getWClassName() {
        return name;
    }

    public void setClassName(String newName) {
        this.name = newName;
    }
}