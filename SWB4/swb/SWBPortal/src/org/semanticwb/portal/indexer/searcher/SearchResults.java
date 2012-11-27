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
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.indexer.parser.GenericParser;

// TODO: Auto-generated Javadoc
/**
 * Class that holds the results of the execution of a search query.
 * <p>
 * Clase que contiene los resultados de la ejecución de una búsqueda.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SearchResults {
    
    /** The list of {@link SearchDocument}s obtained by the execution of the query. */
    private TreeSet<SearchDocument> docs = null;
    
    /** The {@link User}. */
    private User m_user = null;

    /** The page length. */
    private int pageLength=10;
    
    /**
     * Instantiates a new {@link SearchResults} object.
     * <p>
     * Crea una nueva instancia del objeto {@link SearchResults}.
     * 
     * @param user the {@link User}. El objeto {@link User}.
     */
    public SearchResults (User user) {
        this(user,null);
    }
    
    /**
     * Instantiates a new {@link SearchResults} object.
     * <p>
     * Crea una nueva instancia del objeto {@link SearchResults}.
     * 
     * @param user          the {@link User}. El objeto {@link User}.
     * @param comparator    a comparator to sort results. Un comparador para 
     *                      ordenar los resultados.
     */
    public SearchResults (User user, Comparator comparator) {
        m_user=user;
        if(comparator!=null) {
            docs=new TreeSet(comparator);
        } else {
            docs=new TreeSet();
        }
    }

    /**
     * Adds a {@link SearchDocument} to the results.
     * <p>
     * Agrega un {@link SearchDocument} a los resultados.
     *
     * @param doc the {@link SearchDocument}. El objeto {@link SearchDocument}.
     */
    public void add(SearchDocument doc) {
        Searchable sdoc=doc.getSearchable();
        if(sdoc!=null) {
            //TODO:Traer idexador del modelo
            SWBIndexer index=SWBPortal.getIndexMgr().getDefaultIndexer();
            if(index!=null)
            {
                GenericParser parser=index.getParser(sdoc);
                if(parser.canUserView(sdoc, m_user)) {
                 docs.add(doc);
                }
            }
        }
    }

    /**
     * Gets the size of the results (number of {@link SearchDocument}s in the result).
     * <p>
     * Obtiene el tamaño de los resultados (el número de documentos obtenidos).
     * 
     * @return  the number of {@link SearchDocument}s retrieved by the query
     *          execution. El número de documentos obtenidos por la búsqueda.
     */
    public int size() {
        return docs.size();
    }

    /**
     * Lists the {@link SearchDocument}s retrieved by the execution of the query.
     * <p>
     * Lista los documentos obtenidos por la ejecución de la búsqueda.
     * 
     * @return  the iterator to the list of {@link SearchDocument}s. Iterador a
     *          la lista de documentos.
     */
    public Iterator<SearchDocument> listDocuments () {
        return docs.iterator();
    }

    /**
     * Lists the {@link SearchDocument}s in the specified page.
     * <p>
     * Lista los documentos en la página especificada.
     * 
     * @param page  the page. Página.
     * @return      the iterator to the list of {@link SearchDocument}s.
     *              Iterador a la lista de documentos.
     */
    public Iterator<SearchDocument> listDocuments (int page) {
        ArrayList arr=new ArrayList();
        int size=docs.size();
        int index=page*pageLength;
        if(index<size) {
            int x=0;
            Iterator<SearchDocument> it=docs.iterator();
            while (it.hasNext()) {
                SearchDocument searchDocument = it.next();
                if(x>=index+pageLength) {
                    break;
                } else if(x>=index) {
                    arr.add(searchDocument);
                }
                x++;
            }
        }
        return arr.iterator();
    }


    /**
     * Gets the number of results ({@link SearchDocument}s) per page when results
     * are paginated.
     * <p>
     * Obtiene el número de documentos por página cuando los resultados están
     * paginados.
     *
     * @return  Number of {@link SearchDocument}s per page. Número de resultados
     *          ({@link SearchDocument}s) por página.
     */
    public int getPageLength() {
        return pageLength;
    }

    /**
     * Sets the nomber of results ({@link SearchDocument}s) per page when results
     * are paginated.
     * <p>
     * Establece el número de documentos por página cuando los resultados están
     * paginados.
     * 
     * @param pageLength the new page length
     */
    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }
}
