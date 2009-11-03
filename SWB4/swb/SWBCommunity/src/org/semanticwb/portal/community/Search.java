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
package org.semanticwb.portal.community;

import com.hp.hpl.jena.query.larq.IndexLARQ;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.QueryWrapperFilter;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.integration.lucene.analyzer.LocaleAnalyzer;

/**
 * Search resource for communities. Searchs for {@link DirectoryObject}s and
 * {@link WebPage}s in the jena database by executing a LARQ query.
 * <p>
 * Recurso para búsqueda en comunidades. Busca {@link DirectoryObject}s y
 * {@link WebPage}s en la base de datos de jena ejecutando una consulta con LARQ.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Search extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(Search.class);
    private IndexLARQ index;
    private Model smodel;
    private HashMap<String, String> langCodes;
    private ArrayList<SemanticObject> solutions = null;
    public static int SORT_NOSORT = 0;
    public static int SORT_BYDATE = 1;
    public static int SORT_BYNAME = 2;
    public static int SORT_BYPRICE = 3;
    private String language = "es";
    
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        try {
            smodel = base.getSemanticObject().getModel().getRDFOntModel();
            //buildIndex();
            langCodes = new HashMap<String, String>();
            langCodes.put("es", "Spanish");
            langCodes.put("en", "English");
            langCodes.put("de", "Dutch");
            langCodes.put("pt", "Portuguese");
            langCodes.put("ru", "Russian");
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, CorruptIndexException {
        int maxr = Integer.valueOf(getResourceBase().getAttribute("maxResults", "10"));
        String path = getResourceBase().getAttribute("viewJSP","/swbadmin/jsp/microsite/Search/Search.jsp");
        RequestDispatcher dis = request.getRequestDispatcher(path);
        ArrayList<SemanticObject> pageData = new ArrayList<SemanticObject>();
        int sort = SORT_NOSORT;
        int page = 1;

        //Get user language
        if (paramRequest.getUser() != null) {
            language = paramRequest.getUser().getLanguage();
        }

        //Get page number
        if (request.getParameter("p") != null)
            page = Integer.valueOf(request.getParameter("p"));

        //Get order criteria
        if (request.getParameter("o") != null)
            sort = Integer.valueOf(request.getParameter("o"));

        //Get resource url
        String url = "";
        Resourceable rsa = getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
            url = ((WebPage) rsa).getUrl();
        }

        if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
            //Assert council string
            String q = request.getParameter("q");
            if (q == null) return;

            String what = request.getParameter("what");
            if (what == null) what = "";
            //---- if you want to perform search using LARQ
            //String indexpath = SWBPortal.getIndexMgr().getDefaultIndexer().getIndexPath();
            //IndexReader reader = IndexReader.open(indexpath);
            //index = new IndexLARQ(reader);
            //solutions = performQuery(q, lang);

            //---- if you want to perform search using lucene index
            solutions = performQuery(q, what);

            if (solutions != null && solutions.size() > 0)
                pageData = getSlice(page, maxr, sort);
        }

        try {
            if (pageData != null && pageData.size() > 0) {
                request.setAttribute("results", pageData);
                request.setAttribute("t", solutions.size());
                request.setAttribute("allRes", solutions);
            }
            request.setAttribute("rUrl", url);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Executes a free-text lucene search query. Search for words in <b>title</b>,
     * <b>description</b> and <b>tags</b> fields in the index. Additionally,
     * searches the field <b>types</b> only for Web Pages and types defined by
     * <b>what</b> argument.
     * <p>
     * Ejecuta una búsqueda a texto abierto con lucene. Busca coincidencias con
     * las palabras en los campos <b>title</b>, <b>description</b> y <b>tags</b>
     * del índice de documentos. Adicionalmente busca en el campo <b>types</b>
     * las palabras WebPage y el tipo definido en el argumento <b>what</b>.
     *
     * @param   query the query. La consulta.
     * @param   what types to search for (WebPage, Clasified, etc.). Tipos de
     *          objeto a buscar (WebPage, Clasified, etc.).
     * @return  set of URIs of the objects that match the search criteria.
     *          Conjunto de URIs de los objetos que cumplen con los criterios de
     *          búsqueda.
     */
    public ArrayList<SemanticObject> performQuery(String query, String what) {
        ArrayList<SemanticObject> res = new ArrayList<SemanticObject>();
        String indexPath = SWBPortal.getIndexMgr().getDefaultIndexer().getIndexPath();

        try {
            IndexSearcher searcher = new IndexSearcher(indexPath);

            //Create query for 'title' field
            QueryParser qp = new QueryParser("title", new LocaleAnalyzer());
            org.apache.lucene.search.Query q = qp.parse(query);

            //Create query for 'description' field
            qp = new QueryParser("description", new LocaleAnalyzer());
            org.apache.lucene.search.Query q2 = qp.parse(query);

            //Create query for 'tags' field
            qp = new QueryParser("tags", new LocaleAnalyzer());
            org.apache.lucene.search.Query q3 = qp.parse(query);

            //Create boolean query
            BooleanQuery bq = new BooleanQuery();
            bq.add(q, Occur.SHOULD);
            bq.add(q2, Occur.SHOULD);
            bq.add(q3, Occur.SHOULD);

            Hits hits = null;

            //Add queries to search for a specific type
            if (!what.equals("")) {
                //Create query for 'types' field (what to search for)
                qp = new QueryParser("types", new LocaleAnalyzer());
                org.apache.lucene.search.Query q4 = qp.parse(what);

                //Create filter to query by types
                CachingWrapperFilter filter = new CachingWrapperFilter(new QueryWrapperFilter(q4));
                hits = searcher.search(bq, filter);
            } else {
                hits = searcher.search(bq);
            }

            for (int i =0; i<hits.length(); i++) {
                Document doc = hits.doc(i);
                String uri = doc.get("uri");
                if (uri != null && !uri.equals("null")) {
                    SemanticObject so = SemanticObject.createSemanticObject(uri);
                    if (so != null) {
                        res.add(so);
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return res;
    }

    /**
     * Gets a slice of information from the search results. Used for paging.
     * <p>
     * Obtiene un fragmento de información de los resultados de búsqueda. Usado
     * para paginación.
     *
     * @param page      number of slice to get (page). Número de la página a obtener.
     * @param max       elements per slice. Elementos por página.
     * @param sortType  order criteria. Criterio de ordenamiento.
     *
     * @return      set of <b>max</b> elements corresponding to slice <b>page</b>.
     *              Conjunto de <b>max</b> elementos pertenecientes a la página
     *              <b>page</b>.
     */
    public ArrayList<SemanticObject> getSlice (int page, int max, int sortType) {
        ArrayList<SemanticObject> pageData = new ArrayList<SemanticObject>();
        int offset = (page - 1) * max;
        Set results = null;

        //Sort objects
        if (sortType != SORT_NOSORT) {
            if (sortType == SORT_BYDATE) {
                //Sort manually by date because SWBComparator fails launching a
                //ClassCast exception
                results = new TreeSet(new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                        Traceable ob1 = (Traceable) ((SemanticObject)o1).createGenericInstance();
                        Traceable ob2 = (Traceable) ((SemanticObject)o2).createGenericInstance();
                        int ret = ob1.getCreated().after(ob2.getCreated())?-1:1;
                        return ret;
                    }
                });

                Iterator<SemanticObject> soit = solutions.iterator();
                while (soit.hasNext()) {
                    SemanticObject o = soit.next();
                    results.add(o);
                }
                
            } else if (sortType == SORT_BYNAME) {
                results = SWBComparator.sortByDisplayNameSet(solutions.iterator(), language);
            } else if (sortType == SORT_BYPRICE) {
                //TODO
            }

            //Copy sort results
            Iterator<SemanticObject> soit = results.iterator();
            solutions = new ArrayList<SemanticObject>();
            while (soit.hasNext()) {
                solutions.add(soit.next());
            }
        }

        for (int i = 0; i < max; i++) {
            if ((offset + i) < solutions.size())
                pageData.add(solutions.get(offset + i));
        }
        return pageData;
    }
}