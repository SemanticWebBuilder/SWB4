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
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        
        if (mode.equals(paramRequest.Mode_VIEW)) {
            doView(request, response, paramRequest);
        } else if (mode.equals("slice")) {
            doSlice(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doSlice(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/microsite/Search/Search.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        ArrayList<String> pageData = null;

        int page = 1;
        if (request.getParameter("p") != null) {
            page = Integer.valueOf(request.getParameter("p"));
        }

        int maxr = Integer.valueOf(getResourceBase().getAttribute("maxResults", "10"));

        //Get resource url
        String url = "";
        Resourceable rsa = getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
            url = ((WebPage) rsa).getUrl();
        }

        if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
            //Get slice for page
            if (solutions != null && solutions.size() > 0)
                pageData = getSlice(page, maxr);
        }

        try {
            if (pageData != null && pageData.size() > 0) {
                request.setAttribute("results", pageData);
                request.setAttribute("t", solutions.size());
            }
            request.setAttribute("rUrl", url);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        int maxr = Integer.valueOf(getResourceBase().getAttribute("maxResults", "10"));
        String path = "/swbadmin/jsp/microsite/Search/Search.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        ArrayList<String> pageData = new ArrayList<String>();
        String lang = "es";
        int page = 1;
        
        if (paramRequest.getUser() != null)
            lang = paramRequest.getUser().getLanguage();

        if (request.getParameter("p") != null)
            page = Integer.valueOf(request.getParameter("p"));

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

            if (solutions != null && solutions.size() > 0)
                pageData = getSlice(page, maxr);
        }

        try {
            if (pageData != null && pageData.size() > 0) {
                request.setAttribute("results", pageData);
                request.setAttribute("t", solutions.size());
            }
            request.setAttribute("rUrl", url);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Builds an index to perform searches.
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

    /**
     * Execute a SparQl query to find directory objects. Uses LARQ to perform
     * a free-text search into a snow-balled terms index.
     * @param q Query string.
     * @param lang Language for snowball analyzer.
     * @return list of URIs matching the query.
     */
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
                    //System.out.println("---Agregado " + x.toString());
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

    /**
     * Used for paging. Gets a slice of information from the search results.
     * @param page Number of slice to get (page).
     * @param max Elements per slice.
     * @return Set of max elements corresponding to slice page.
     */
    public ArrayList<String> getSlice (int page, int max) {
        ArrayList<String> pageData = new ArrayList<String>();
        int offset = (page - 1) * max;

        for (int i = 0; i < max; i++) {
            if ((offset + i) < solutions.size())
                pageData.add(solutions.get(offset + i));
        }
        return pageData;
    }
}
