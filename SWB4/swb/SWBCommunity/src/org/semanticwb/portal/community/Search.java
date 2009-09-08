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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Search extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(Search.class);
    private IndexLARQ index;
    private Model smodel;
    private ArrayList<String> solutions = null;
    private HashMap<String, String> langCodes;
    private String[] stopWords = {"a", "ante", "bajo", "cabe", "con",
        "contra", "de", "desde", "durante",
        "en", "entre", "hacia", "hasta",
        "mediante", "para", "por", "según",
        "sin", "sobre", "tras", "el", "la",
        "los", "las", "ellos", "ellas", "un",
        "uno", "unos", "una", "unas", "y", "o",
        "pero", "si", "no", "como", "que", "su",
        "sus", "esto", "eso", "esta", "esa",
        "esos", "esas", "del"
    };

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        try {
            smodel = base.getSemanticObject().getModel().getRDFOntModel();
            index = buildIndex();
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
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        String lang = "es";
        if (response.getUser() != null)
            lang = response.getUser().getLanguage();

        if (action.equals("search")) {
            String q = request.getParameter("q");
            if (q != null && !q.trim().equals(""))
            solutions = performQuery(q.trim(), lang);
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
        String lang = "es";
        
        if (paramRequest.getUser() != null)
            lang = paramRequest.getUser().getLanguage();

        //Get resource url
        String url = "";
        Resourceable rsa = getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
            url = ((WebPage) rsa).getUrl();
        }

        if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
            //Assert query string
            String q = request.getParameter("q");
            if (q == null) return;

            //Perform search query
            solutions = performQuery(q, lang);
        }

        try {
            if (solutions != null && solutions.size() > 0) {
                request.setAttribute("results", solutions.iterator());
            }
            request.setAttribute("rUrl", url);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
eso es facil     * Builds an index to perform searches.
     */
    public IndexLARQ buildIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
        // ---- Create in-memory lucene directory
        RAMDirectory rd = new RAMDirectory();

        // ---- Create new indexwriter with snowball analyzer
        IndexWriter writer = new IndexWriter(rd, new SnowballAnalyzer("Spanish"));

        // ---- Read and index all literal strings.
        IndexBuilderString larqBuilder = new IndexBuilderString(writer);

        // ---- Alternatively build the index after the model has been created.
        larqBuilder.indexStatements(smodel.listStatements());

        // ---- Finish indexing
        larqBuilder.closeWriter();

        // ---- Create the access index
        index = larqBuilder.getIndex();        
        return index;
    }

    public ArrayList<String> performQuery(String q, String lang) {
        ArrayList<String> res = new ArrayList<String>();

        //Assert query string
        if (q.trim().equals("")) return null;

        String [] words = getSnowballForm(q, lang, stopWords).trim().split(" ");

        q = "";
        for (String s : words) {
            q = q + "+" + s + " ";
        }

        //Build query to return classes with literal values in their properties
        String queryString = StringUtils.join("\n", new String[]{
                    "PREFIX xsd:     <http://www.w3.org/2001/XMLSchema#>",
                    "PREFIX pf:      <http://jena.hpl.hp.com/ARQ/property#>",
                    "PREFIX swb:     <http://www.semanticwebbuilder.org/swb4/ontology#>",
                    "PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
                    "PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>",
                    "PREFIX owl:     <http://www.w3.org/2002/07/owl#>",
                    "PREFIX swbcomm: <http://www.semanticwebbuilder.org/swb4/community#>",
                    "SELECT DISTINCT ?obj WHERE {",
                    "    ?lit pf:textMatch '" + q.trim() + "'.",
                    "    ?obj a swbcomm:DirectoryObject.",
                    "    {?obj swb:title ?lit}",
                    "    UNION {?obj swb:tags ?lit}",
                    "    UNION {?obj swb:description ?lit}",
                    "}"
                });

//        System.out.println("....................");
//        System.out.println(queryString);
//        System.out.println("....................");

        // Make globally available
        LARQ.setDefaultIndex(index);
        Query query = QueryFactory.create(queryString);

        QueryExecution qExec = QueryExecutionFactory.create(query, smodel);
        ResultSet rs = qExec.execSelect();

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

    /**
     * Gets the snowball-ed form of an input text. Applies the snowball algorithm
     * to each word in the text to get its root or stem.
     *
     * Obtiene el lexema o raiz de una palabra mediante el algoritmo de snowball.
     * @param input Text to stem.
     * @param language Language code for snowball analyzer.
     * @param stopWords Stop words for snowball analyzer.
     * @return Root of the input.
     */
    public String getSnowballForm(String input, String language, String [] stopWords) {
        String res = "";
        Analyzer SnballAnalyzer = null;

        //Create snowball analyzer
        if (stopWords != null && stopWords.length > 0)
            SnballAnalyzer = new SnowballAnalyzer(langCodes.get(language), stopWords);
        else
            SnballAnalyzer = new SnowballAnalyzer(langCodes.get(language));

        //Create token stream for prhase composition
        TokenStream ts = SnballAnalyzer.tokenStream("sna", new StringReader(input));

        //Build the result string with the analyzed tokens
        try {
            Token tk;
            while ((tk = ts.next()) != null) {
                res = res + tk.termText() + " ";
            }
            ts.close();
        } catch (Exception ex) {
            log.error(ex);
        }
        return res.trim();
    }
}
