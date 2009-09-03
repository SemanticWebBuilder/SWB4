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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        //int  page = 1;
        //if (request.getParameter("p") != null) page = Integer.valueOf(request.getParameter("p"));

        String path = "/swbadmin/jsp/microsite/Search/Search.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            if (solutions != null && solutions.size() > 0) {
                request.setAttribute("results", solutions.iterator());
            }
            request.setAttribute("paramRequest", paramsRequest);
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
        q = (q == null ? "" : q.trim());

        //Build query to return classes with literal values in their properties
        String queryString = StringUtils.join("\n", new String[]{
                    "PREFIX xsd:     <http://www.w3.org/2001/XMLSchema#>",
                    "PREFIX pf:      <http://jena.hpl.hp.com/ARQ/property#>",
                    "PREFIX swb:     <http://www.semanticwebbuilder.org/swb4/ontology#>",
                    "PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
                    "PREFIX owl:     <http://www.w3.org/2002/07/owl#>",
                    "PREFIX swbcomm: <http://www.semanticwebbuilder.org/swb4/community#>",
                    "SELECT ?obj {",
                    "    ?lit pf:textMatch '" + q + "'.",
                    "    ?obj swb:title ?lit.",
                    "    ?obj a swb:WebPage.",
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

                System.out.println("...sol");
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
}
