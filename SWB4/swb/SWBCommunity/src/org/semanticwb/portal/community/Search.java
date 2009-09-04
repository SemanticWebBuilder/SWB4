/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.community;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.larq.IndexBuilderString;
import com.hp.hpl.jena.query.larq.IndexLARQ;
import com.hp.hpl.jena.query.larq.LARQ;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.resources.AdvancedSearch;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Search extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(AdvancedSearch.class);
    private IndexLARQ index;
    private Model smodel;
    private ArrayList<String> solutions = null;

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        try {
            //smodel = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
            smodel = base.getSemanticObject().getModel().getRDFOntModel();
            index = buildIndex();
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();

        if (action.equals("search")) {
            String q = request.getParameter("q");
            if (q != null && !q.trim().equals(""))
            solutions = performQuery(q.trim());
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals(paramRequest.Mode_VIEW)) {
            doView(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/microsite/Search/Search.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        ArrayList<String> pageData = new ArrayList<String>();
        int max = Integer.valueOf(getResourceBase().getAttribute("maxResults", "10"));
        int page = 1;
        int _start = 0;
        int _end = 0;

        //Get resource url
        String url = "";
        Resourceable rsa = getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
            url = ((WebPage) rsa).getUrl();
        }

        if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
        //Assert query string
        String q = request.getParameter("q");
        if (q == null) q = "";        

        //Get page number
        if (request.getParameter("p") == null) {
            solutions = null;
            solutions = performQuery(q);
        } else {
            page = Integer.valueOf(request.getParameter("p"));
        }

        //Get pagination        
        /*if (solutions != null && solutions.size() > 0) {
            int step = max;
            _start = step * (page - 1);
            _end = _start + step - 1;
            if (_end > solutions.size() - 1) {
                _end = solutions.size() - 1;
            }

            //sbf.append("<tr><td>Mostrando resultados " + (_start + 1) + " - " + (_end + 1) + " de " + solutions.size() + "<br>" +
            //      "          <hr color=\"#16458D\" width=\"100%\" size=\"1\" /><BR/></td></tr>\n");

            for (int i = _start; i <= _end; i++) {
                pageData.add(solutions.get(i));
                System.out.println("---Paginado " + solutions.get(i));
            }

            //sbf.append("<tr><td align=\"center\" colspan=\"2\"><hr width=\"100%\" size=\"1\" /><br/>\n");
            double pages = Math.ceil((double) solutions.size() / (double) step);
            /*for (int i = 1; i <= pages; i++) {
            _start = step * (i - 1);
            if ((_start + step) - 1 > results.size() - 1) {
            _end = results.size() - 1;
            } else {
            _end = (_start + step) - 1;
            }
            if (Integer.valueOf(page) == i) {
            sbf.append("<span>" + i + "</span> ");
            } else {
            rUrl = paramRequest.getRenderUrl().setMode("PAGE");
            rUrl.setParameter("p", String.valueOf(i));
            sbf.append("<a href =\"" + rUrl + "\">" + i + "</a> ");
            }
            }

            if (Integer.valueOf(page) < pages) {
            rUrl = paramRequest.getRenderUrl().setMode("PAGE");
            rUrl.setParameter("p", String.valueOf(Integer.valueOf(page) + 1));
            sbf.append("<a href=" + rUrl + ">" + paramRequest.getLocaleString("lblNext") + "</a>\n");
            }
            sbf.append("</td></tr>");
            }
         System.out.println("start2: " + _start + ", end2: " + _end + ", total2: " + solutions.size());
        }*/
        }

        try {
            //if (pageData != null && pageData.size() > 0) {
            if (solutions != null && solutions.size() > 0) {
                request.setAttribute("results", solutions.iterator());
                //System.out.println(":::::::Sending data");
                //request.setAttribute("results", pageData.iterator());
                //request.setAttribute("s", _start);
                //request.setAttribute("e", _end);
                //request.setAttribute("t", solutions.size());
            }
            request.setAttribute("rUrl", url);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Builds an index to perform searchs.
     */
    public IndexLARQ buildIndex() {
        // ---- Read and index all literal strings.
        IndexBuilderString larqBuilder = new IndexBuilderString();

        // ---- Alternatively build the index after the model has been created.
        larqBuilder.indexStatements(smodel.listStatements());

        // ---- Finish indexing
        larqBuilder.closeWriter();

        // ---- Create the access index
        index = larqBuilder.getIndex();
        
        return index;
    }

    public ArrayList<String> performQuery(String q) {
        ArrayList<String> res = new ArrayList<String>();

        //Assert query string
        if (q.trim().equals("")) return null;

        //Build query to return classes with literal values in their properties
        String queryString = StringUtils.join("\n", new String[]{
                    "PREFIX xsd:     <http://www.w3.org/2001/XMLSchema#>",
                    "PREFIX pf:      <http://jena.hpl.hp.com/ARQ/property#>",
                    "PREFIX swb:     <http://www.semanticwebbuilder.org/swb4/ontology#>",
                    "PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
                    "PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>",
                    "PREFIX owl:     <http://www.w3.org/2002/07/owl#>",
                    "PREFIX swbcomm: <http://www.semanticwebbuilder.org/swb4/community#>",
                    "SELECT * WHERE {",
                    "    ?lit pf:textMatch '" + q.trim() + "'.",
                    "    ?obj a swbcomm:DirectoryObject.",
                    //"    OPTIONAL{?obj a swbcomm:Organization}.",
                    "    ?obj swb:title ?lit.",
                    "    OPTIONAL{?obj swb:description ?lit}.",
                    "    OPTIONAL{?obj swb:tags ?lit}.",
                    "}"
                });

        System.out.println("....................");
        System.out.println(queryString);
        System.out.println("....................");

        // Make globally available
        LARQ.setDefaultIndex(index);
        Query query = QueryFactory.create(queryString);

        QueryExecution qExec = QueryExecutionFactory.create(query, smodel);
        ResultSet rs = qExec.execSelect();
        //ResultSetFormatter.out(System.out, rs, query);

        //If there are results
        if (rs != null && rs.hasNext()) {
            //Get next result set
            while (rs.hasNext()) {

                //Get next solution of the result set (var set)
                QuerySolution qs = rs.nextSolution();

                //Get node object from solution
                RDFNode x = qs.get("obj");

                //If node is not null
                if (x != null && x.isResource()) {
                    res.add(x.asNode().getURI());
                    System.out.println("---Agregado " + x.toString());
                }
            }
        }
        qExec.close();
        return res;
    }

    /*public ArrayList<String> getPage(ArrayList<String> results, int page, int total) {
        ArrayList<String> r = new ArrayList<String>();

        if (results != null && results.size() > 0) {
                int step = total;
                int _start = 0;
                int _end = 0;

                _start = step * (page - 1);
                _end = _start + step - 1;
                if (_end > results.size() - 1) {
                    _end = results.size() - 1;
                }

                //System.out.println("page: " + page + ", start: " + _start + ", end: " + _end);

                //sbf.append("<tr><td>Mostrando resultados " + (_start + 1) + " - " + (_end + 1) + " de " + solutions.size() + "<br>" +
                  //      "          <hr color=\"#16458D\" width=\"100%\" size=\"1\" /><BR/></td></tr>\n");

                for (int i = _start; i <= _end; i++) {
                    r.add(results.get(i));
                }

                //sbf.append("<tr><td align=\"center\" colspan=\"2\"><hr width=\"100%\" size=\"1\" /><br/>\n");
                double pages = Math.ceil((double) results.size() / (double) step);
                for (int i = 1; i <= pages; i++) {
                    _start = step * (i - 1);
                    if ((_start + step) - 1 > results.size() - 1) {
                        _end = results.size() - 1;
                    } else {
                        _end = (_start + step) - 1;
                    }
                    if (Integer.valueOf(page) == i) {
                        sbf.append("<span>" + i + "</span> ");
                    } else {
                        rUrl = paramRequest.getRenderUrl().setMode("PAGE");
                        rUrl.setParameter("p", String.valueOf(i));
                        sbf.append("<a href =\"" + rUrl + "\">" + i + "</a> ");
                    }
                }

                if (Integer.valueOf(page) < pages) {
                    rUrl = paramRequest.getRenderUrl().setMode("PAGE");
                    rUrl.setParameter("p", String.valueOf(Integer.valueOf(page) + 1));
                    sbf.append("<a href=" + rUrl + ">" + paramRequest.getLocaleString("lblNext") + "</a>\n");
                }
                sbf.append("</td></tr>");
            }

        return r;
    }*/
}
