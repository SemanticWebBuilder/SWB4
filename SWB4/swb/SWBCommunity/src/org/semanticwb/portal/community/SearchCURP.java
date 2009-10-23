/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
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
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.integration.lucene.analyzer.LocaleAnalyzer;

/**
 *
 * @author hasdai
 */
public class SearchCURP extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(SearchCURP.class);
    private ArrayList<SemanticObject> solutions = null;
    public static int SORT_NOSORT = 0;
    public static int SORT_BYDATE = 1;
    public static int SORT_BYNAME = 2;
    private String language = "es";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, CorruptIndexException {
        int maxr = Integer.valueOf(getResourceBase().getAttribute("maxResults", "10"));
        String path = getResourceBase().getAttribute("viewJSP","/swbadmin/jsp/microsite/Search/SearchCURP.jsp");
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
            String council = request.getParameter("q");
            if (council == null) return;

            String state = request.getParameter("state");
            if (state == null) return;
            //---- if you want to perform search using LARQ
            //String indexpath = SWBPortal.getIndexMgr().getDefaultIndexer().getIndexPath();
            //IndexReader reader = IndexReader.open(indexpath);
            //index = new IndexLARQ(reader);
            //solutions = performQuery(q, lang);

            System.out.println("State: " + state + " Council: " + council);
            //---- if you want to perform search using lucene index
            solutions = performQuery(state, council);

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

        ArrayList<DirectoryObject> sorted = new ArrayList<DirectoryObject>();
        Iterator<SemanticObject> soit = solutions.iterator();
        while (soit.hasNext()) {
            SemanticObject o = soit.next();
            DirectoryObject dob =(DirectoryObject) o.createGenericInstance();
            if (dob != null)
                sorted.add(dob);
        }

        //Sort objects
        if (sortType != SORT_NOSORT) {
            if (sortType == SORT_BYDATE) {
                results = SWBComparator.sortByCreatedSet(sorted.iterator(), false);
            } else if (sortType == SORT_BYNAME) {
                results = SWBComparator.sortByDisplayNameSet(sorted.iterator(), language);
            }

            //Copy sort results
            Iterator<DirectoryObject> soit2 = results.iterator();
            solutions = new ArrayList<SemanticObject>();
            while (soit2.hasNext()) {
                DirectoryObject o = soit2.next();
                solutions.add(o.getSemanticObject());
            }
        }
        
        for (int i = 0; i < max; i++) {
            if ((offset + i) < solutions.size())
                pageData.add(solutions.get(offset + i));
        }        
        return pageData;
    }

    public ArrayList<SemanticObject> performQuery(String state, String council) throws CorruptIndexException, IOException {
        ArrayList<SemanticObject> res = new ArrayList<SemanticObject>();
        String indexPath = SWBPortal.getIndexMgr().getDefaultIndexer().getIndexPath();
        try {
            IndexSearcher searcher = new IndexSearcher(indexPath);

            QueryParser qp = new QueryParser("cityCouncil", new LocaleAnalyzer());
            org.apache.lucene.search.Query q = qp.parse(council);

            qp = new QueryParser("state", new LocaleAnalyzer());
            org.apache.lucene.search.Query q2 = qp.parse(state);

            qp = new QueryParser("types", new LocaleAnalyzer());
            org.apache.lucene.search.Query q3 = qp.parse("CURPModule");

            BooleanQuery bq = new BooleanQuery();
            bq.add(q, Occur.MUST);
            bq.add(q2, Occur.MUST);

            CachingWrapperFilter filter = new CachingWrapperFilter(new QueryWrapperFilter(q3));
            Hits hits = searcher.search(bq, filter);

            for (int i =0; i<hits.length(); i++) {
                Document doc = hits.doc(i);
                String uri = doc.get("uri");
                if (uri != null && !uri.equals("null")) {
                    SemanticObject so = SemanticObject.createSemanticObject(uri);
                    if (so != null) {
                        res.add(so);
                        //System.out.println("...Agregando " + so.getURI());
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return res;
    }
}