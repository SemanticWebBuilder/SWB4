/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/

package org.semanticwb.nlp;

/**
 * Word tag similar to a POS Tag. Etiqueta para una palabra similar a un POS tag.
 * <p>
 * In the analysis stage of natural language processing, a tag (POS tag) is
 * associated to words, which marks the word's function in the sentence.
 * Generally the tag encodes additional lexical and morfological information,
 * useful for disambiguation tasks.
 *<p>
 * (Specific SemanticWebBuilder information) The tag for each {@link Word} is
 * composed by its sintactic function ({@code OBJ}, {@code PREC}, {@code PRED},
 * etc.), the class type of the associated object in the ontology (swb:{@link WebPage},
 * swbx:Form, etc.), the name of the {@link SemanticClass} associated to the word,
 * its ID and range (if it is an {@link ObjectProperty}).
 * <p>
 * En procesamiento del lenguaje natural, durante el análisis, se asocia una
 * etiqueta a las palabras (POS Tag) que indica su función dentro de una oración.
 * La etiqueta generalmente incorpora información léxica y morfológica útil para
 * tareas de desambiguación.
 * <p>
 * (Información específica de SemanticWebBuilder) La etiqueta de cada palabra se
 * compone de su función sintáctica ({@code OBJ}, {@code PREC}, {@code PRED},
 * etc.), el tipo de clase que representa la palabra en la ontología (swb:{@link WebPage},
 * swbx:Form, etc.), el nombre de la clase semántica asociada a la palabra, su
 * id y su rango (en caso de tratarse de una propiedad de tipo {@link ObjectProperty}).
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
    private String displayName;

    /**
     * Creates a new instance of a {@link WordTag} with the given label, TagType,
     * class name, class id and range class name.
     * <p>
     * Crea una nueva instancia de un {@link WordTag} con la forma léxica, tipo,
     * nombre de clase, id de clase y rango especificados.
     *
     * @param label     tag for the {@link Word}. Etiqueta de la palabra.
     * @param typ       class type of the object in the ontology associated to
     *                  the word. Tipo de dato en la ontología para el objeto
     *                  asociado a la palabra.
     * @param cName     class name of the object in the ontology associated to
     *                  the word. Nombre del objeto en la ontología asociado a
     *                  la palabra.
     * @param cId       ID of the object in the ontology associated to the word.
     *                  Identificador del objeto en la ontología asociado a la palabra.
     * @param rang      name of the range class of the object associated to the
     *                  word. Nombre de la clase rango asociada a la palabra.
     */
    public WordTag (String label, String display, String typ, String cName, String cId, String rang)
    {
        lbl = label;
        displayName = display;
        TagType = typ;
        name = cName;
        rangeName = rang;
        oId = cId;
    }

    /**
     * Gets the range class name of this {@link WordTag}.
     * <p>
     * Obtiene el nombre del rango de la etiqueta.
     *
     * @return name of the range class in this tag.
     */
    public String getRangeClassId() {
        return rangeName;
    }

    /**
     * Sets the range class name of this {@link WordTag}.
     * <p>
     * Establece el nombre del rango de la etiqueta.
     *
     * @param rangeName new range class name. Nuevo nombre de la clase rango.
     */
    public void setRangeClassId(String rangeName) {
        this.rangeName = rangeName;
    }

    /**
     * Sets the class ID of this {@link WordTag}.
     * <p>
     * Establece el ID de la clase de la etiqueta.
     *
     * @param oId new ID. Nuevo identificador de la clase.
     */
    public void setObjId(String oId) {
        this.oId = oId;
    }

    /**
     * Gets the class ID of this {@link WordTag}.
     * <p>
     * Obtiene el ID de la clase de la etiqueta.
     *
     * @return  ID of the object associated to this tag. ID del objeto asociado
     *          a la etiqueta.
     */
    public String getObjId() {
        return oId;
    }

    /**
     * Gets the label of this {@link WordTag}.
     * <p>
     * Obtiene la etiqueta de la palabra en el {@link WordTag}.
     *
     * @return word tag label. Texto de la etiqueta.
     */
    public String getTag()
    {
        return lbl;
    }

    /**
     * Gets the type of this {@link WordTag}.
     * <p>
     * Obtiene el tipo de clase del {@link WordTag}.
     *
     * @return tag type. Tipo de la clase asociada a la etiqueta.
     */
    public String getType()
    {
        return TagType;
    }

    /**
     * Sets the label of this {@link WordTag}.
     * <p>
     * Establece la etiqueta del {@link WordTag}
     *
     * @param t new label for the {@link WordTag}. Nueva etiqueta para el {@link WordTag}.
     */
    public void setTagLabel(String t)
    {
        this.lbl = t;
    }

    /**
     * Sets the type of this {@link WordTag}.
     * <p>
     * Establece el tipo de clase en el {@link WordTag}.
     *
     * @param tt {@link WordTag} type. Tipo de la clase asociada con la etiqueta.
     */
    public void setType(String tt)
    {
        this.TagType = tt;
    }

    /**
     * Gets the display name of this {@link WordTag}.
     * Obtiene el display name del objeto asociado al {@link WordTag}.
     *
     * @return  display name of the object associated to the {@link WordTag}.
     *          Display name del objeto asociado al {@link WordTag}.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name of this {@link WordTag}.
     * <p>
     * Establece el display name del objeto asociado al {@link WordTag}.
     *
     * @param   display name of the object associated to the {@link WordTag}.
     *          display name del objeto asociado con el {@link WordTag}.
     */
    public void setDisplayName(String display) {
        this.displayName = display;
    }

    /*public String getWClassName() {
        return name;
    }
    
    public void setWClassName(String newName) {
        this.name = newName;
    }*/
}