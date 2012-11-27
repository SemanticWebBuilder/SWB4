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

import java.util.ArrayList;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * Wraps a search query.
 * <p>
 * Clase que representa una consulta.
 * 
 * @author javier.solis
 */
public class SearchQuery
{
    
    /** The Constant OPER_OR. */
    public static final int OPER_OR=1;
    
    /** The Constant OPER_AND. */
    public static final int OPER_AND=2;
    
    /** The Constant OPER_NOT. */
    public static final int OPER_NOT=3;

    /** The list of search {@link SearchTerms} that the query contains. */
    private ArrayList<SearchTerm> terms=new ArrayList();
    
    /** The querys that the query wraps. */
    private ArrayList<SearchQuery> querys=new ArrayList();
    
    /** The operation applied to the entire query. */
    private int operation=0;

    /**
     * Instantiates a new search query.
     * <p>
     * Crea una nueva instancia de una consulta.
     */
    public SearchQuery() { }

    /**
     * Instantiates a new search query.
     * <p>
     * Crea una nueva instancia de una consulta.
     * 
     * @param operation the operation. El tipo de operación aplicado a la consulta.
     */
    public SearchQuery(int operation) {
        this.operation=operation;
    }

    /**
     * Adds a {@link SearchTerm} to the query.
     * <p>
     * Agrega un término de búsqueda ({@link SearchTerm}) a la consulta.
     * 
     * @param term the {@link SearchTerm}. El objeto {@link SearchTerm}.
     */
    public void addTerm(SearchTerm term) {
        terms.add(term);
    }

    /**
     * Lists the {@link SearchTerm}s of the query.
     * <p>
     * Lista los términos de búsqueda ({@link SearchTerm}) de la consulta.
     * 
     * @return the iterator
     */
    public Iterator<SearchTerm> listSearchTerms() {
        return terms.iterator();
    }

    /**
     * Adds a nested search query.
     * <p>
     * Agrega una consulta anidada.
     * 
     * @param query the query. La consulta.
     */
    public void addQuery(SearchQuery query) {
        querys.add(query);
    }

    /**
     * Lists the nested queries.
     * <p>
     * Lista las consultas anidadas.
     * 
     * @return the iterator to the nested queries. Iterador a la lista de consultas.
     */
    public Iterator<SearchQuery> listQueries() {
        return querys.iterator();
    }

    /**
     * Gets the operation applied to the query.
     * <p>
     * Obtiene el tipo de operación aplicada a la consulta.
     * 
     * @return the operation. El tipo de operación.
     */
    public int getOperation() {
        return operation;
    }
}
