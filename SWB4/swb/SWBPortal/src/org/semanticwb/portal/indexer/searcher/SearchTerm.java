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
package org.semanticwb.portal.indexer.searcher;

// TODO: Auto-generated Javadoc
/**
 * Generic term. A term is a field/text pair used for keyword based search over
 * indexed documents.
 * <p>
 * Término genérico. Un teŕmino es un par campo/texto usado para hacer búsquedas
 * por palabra clave en documentos indexados.
 * 
 * @author javier.solis
 */
public class SearchTerm {
    
    /** The Constant OPER_OR. */
    public static final int OPER_OR=1;
    
    /** The Constant OPER_AND. */
    public static final int OPER_AND=2;
    
    /** The Constant OPER_NOT. */
    public static final int OPER_NOT=3;

    /** The index field to search in. */
    private String field=null;
    
    /** The text to search in the index field. */
    private String text=null;
    
    /** The operation type. */
    private int operation = 1;

    /**
     * Instantiates a new search term.
     * <p>
     * Crea una instancia de un término de búsqueda.
     * 
     * @param field the name of the field to search in. El nombre del campo de
     *              búsqueda.
     * @param text  the text to search for. El texto a buscar en el campo.
     * @param oper  the operation type. Tipo de operación aplicada al término.
     */
    public SearchTerm(String field, String text, int oper) {
        this.field=field;
        this.text=text;
        this.operation=oper;
    }

    /**
     * Gets the field's name of the term.
     * <p>
     * Obtiene el nombre del campo en el término de búsqueda.
     * 
     * @return the field's name. Nombre del campo.
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the field's name of the term.
     * <p>
     * Establece el nombre del campo en el término de búsqueda.
     * 
     * @param field the field's name to set. Nombre del campo.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Gets the text of the term.
     * <p>
     * Obtiene el texto de búsqueda del término.
     * 
     * @return the text of the term. Texto del término.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the field. Establece el texto de búsqueda del término.
     * 
     * @param text the text to set. Texto del término.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the operation applied on the term.
     * <p>
     * Obtiene el tipo de operación aplicada al término en la búsqueda.
     * 
     * @return the operation. Tipp de operación.
     */
    public int getOperation() {
        return operation;
    }

    /**
     * Sets the operation applied on the term.
     * <p>
     * Establece el tipo de operación aplicada al término en la búsqueda.
     * 
     * @param operation the operation to set. Tipo de operación.
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }
}
