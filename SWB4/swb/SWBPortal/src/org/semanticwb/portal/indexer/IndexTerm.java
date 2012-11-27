/*
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
 */
package org.semanticwb.portal.indexer;

// TODO: Auto-generated Javadoc
/**
 * Index term wrapper. A term is a field-text pair which will be stored or
 * indexed in a search index in order to execute search queries.
 * <p>
 * Wraper para un término a indexar. Un término es un par campo-texto que será
 * almacenado o indexado para poder hacer búsquedas sobre su texto.
 * 
 * @author javier.solis
 */
public class IndexTerm
{
    
    /** The field. */
    private String field=null;
    
    /** The text. */
    private String text=null;
    
    /** The stored. */
    private boolean stored=false;
    
    /** The indexed. */
    private int indexed=1;

    /** Term is not indexed. */
    public static int INDEXED_NO=0;                     //no indexado
    
    /** Term is indexed, tokenized and analyzed. */
    public static int INDEXED_ANALYZED=1;               //tokenizado y analizado
    
    /** Term is indexed, tokenized but not analyzed. */
    public static int INDEXED_TOKENIZED_NO_ANALYZED=2;  //tokenizado y no analizado
    
    /** Term is indexed, but not tokenized nor analyzed. */
    public static int INDEXED_NO_ANALYZED=3;            //no tokenizado y no analizado

    /**
     * Creates a new instance of an index term without text for the field.
     * <p>
     * Crea una nueva instancia de un término de búsqueda con el texto vacío.
     * @param field     Field name. Nombre del campo para el término.
     * @param stored    Determines if the term will be stored in the index.
     *                  Indica si el término se almacenará en el índice.
     * @param indexed   Determines if the term will be indexed (it can be
     *                  searched). Indica si el campo se indexará (se podrán 
     *                  hacer búsquedas sobre él).
     */
    public IndexTerm(String field, boolean stored, int indexed) {
        this(field, null, stored, indexed);
    }

    /**
     * Creates a new instance of an index term.
     * <p>
     * Crea una nueva instancia de un término de búsqueda.
     * @param field     Field name. Nombre del campo para el término.
     * @param text      Text for the term. Texto del campo.
     * @param stored    Determines if the term will be stored in the index.
     *                  Indica si el término se almacenará en el índice.
     * @param indexed   Determines if the term will be indexed (it can be
     *                  searched). Indica si el campo se indexará (se podrán
     *                  hacer búsquedas sobre él).
     */
    public IndexTerm(String field, String text, boolean stored, int indexed) {
        this.field=field;
        this.text=text;
        this.stored=stored;
        this.indexed=indexed;        
        if(indexed==INDEXED_NO_ANALYZED)
        {
            //Se agrega este término a la lista de términos no analizados del indexador.
            //This term is added to the list of not analyzed terms of the indexer.
            SWBIndexer.addNoAnalyzedIndexTerm(this);
        }
    }

    /**
     * Gets the name of the field from the term.
     * <p>
     * Obtiene el nombre del campo en el término.
     * @return Field name. Nombre del campo.
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the name of the field of the term.
     * <p>
     * Establece el nombre del campo en el término.
     * @param field Field name to set. Nombre del campo.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Gets the text of the field from the term.
     * <p>
     * Obtiene el texto del campo en el término.
     * @return Text. Texto del campo.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the field of the term.
     * <p>
     * Establece el texto del campo en el término.
     * @param text Text to set. Texto del campo.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the index mode of the term.
     * <p>
     * Obtiene el modo en el que el término será indexado.
     * 
     * @return  Index mode. Posible values are:
     * 
     * -{@code INDEXED_NO}: Term is not indexed.
     * -{@code INDEXED_ANALYZED}: Term is indexed, tokenized and analyzed.
     * -{@code INDEXED_TOKENIZED_NO_ANALYZED}: Term is indexed, tokenized but not analyzed.
     * -{@code INDEXED_NO_ANALYZED}: Term is indexed, but not tokenized nor analyzed.
     */
    public int getIndexed() {
        return indexed;
    }

    /**
     * Sets the index mode of the term.
     * <p>
     * Establece el modo en el que el término será indexado.
     * 
     * @param indexed the new indexed
     */
    public void setIndexed(int indexed) {
        this.indexed = indexed;
        if(indexed==INDEXED_NO_ANALYZED)
        {
            SWBIndexer.addNoAnalyzedIndexTerm(this);
        }else
        {
            SWBIndexer.removeNoAnalyzedIndexTerm(field);
        }
    }

    /**
     * Gets a {@code boolean} value indicating if this term will be stored
     * in the index.
     * <p>
     * Obtiene un {@code boolean} indicando si el término será almacenado en
     * el índice.
     * @return {@code true} if the term is going to be stored, false otherwise.
     */
    public boolean getStored() {
        return stored;
    }

    /**
     * Sets this term to be stored in the index.
     * <p>
     * Establece que el término será almacenado en el índice.
     * @param stored {@code true} if the term is going to be stored, false otherwise.
     */
    public void setStored(boolean stored) {
        this.stored = stored;
    }
}
