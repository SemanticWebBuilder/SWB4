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
import jena.query;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.WebPage;
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
        ArrayList<String> pageData = new ArrayList<String>();

        //Define path to the results view jsp
        String path = "/swbadmin/jsp/microsite/Search/Search.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);

        //Get number of results to show from the resource base configuration
        int maxr = Integer.valueOf(getResourceBase().getAttribute("maxResults", "10"));
        int page = 1;

        //---- Get user language (if any). Uncomment this lines if you are to
        //---- execute a search using LARQ.
        /*String lang = "es";
        if (paramRequest.getUser() != null)
            lang = paramRequest.getUser().getLanguage();*/

        //Get page number
        if (request.getParameter("p") != null)
            page = Integer.valueOf(request.getParameter("p"));

        //Get resource url
        String url = "";
        Resourceable rsa = getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
            url = ((WebPage) rsa).getUrl();
        }

        //If resource is called as content
        if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {

            //Assert query string
            String q = request.getParameter("q");
            if (q == null) return;

            //Get what to search
            String wh = "";
            if (request.getParameter("what") != null && !request.getParameter("what").equals("Todo")) {
                wh = request.getParameter("what");
            }

            // ---- Perform search query using LARQ
            //String indexpath = SWBPortal.getIndexMgr().getDefaultIndexer().getIndexPath();
            //IndexReader reader = IndexReader.open(indexpath);
            //index = new IndexLARQ(reader);
            //solutions = performQuery(q, lang, wh);

            // ---- Perform search query using lucene index
            solutions = performQuery(q, wh);

            //Get page of results
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

    /*
     * Builds an index to perform searches.
     */
//    public void buildIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
//        // ---- Create in-memory lucene directory
//        RAMDirectory rd = new RAMDirectory();
//
//        // ---- Create new indexwriter with snowball analyzer
//        IndexWriter writer = new IndexWriter(rd, new SnowballAnalyzer("Spanish"));
//
//        // ---- Read and index all literal strings.
//        IndexBuilderString larqBuilder = new IndexBuilderString(writer);
//
//        // ---- Alternatively build the index after the model has been created.
//        larqBuilder.indexStatements(smodel.listStatements());
//
//        // ---- Finish indexing
//        larqBuilder.closeWriter();
//
//        // ---- Create the access index
//        index = larqBuilder.getIndex();
//    }


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
    public ArrayList<String> performQuery(String query, String what) {
        ArrayList<String> res = new ArrayList<String>();
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
            bq.add(q, Occur.MUST);
            bq.add(q2, Occur.SHOULD);
            bq.add(q3, Occur.SHOULD);

            //Add queries to search for a specific type
            if (!what.equals("")) {
                //Create query for 'types' field (search for WebPages)
                qp = new QueryParser("types", new LocaleAnalyzer());
                org.apache.lucene.search.Query q4 = qp.parse("WebPage");

                //Create query for 'types' field (what to search for?)
                org.apache.lucene.search.Query q5 = qp.parse(what);

                bq.add(q4, Occur.SHOULD);
                bq.add(q5, Occur.MUST);
            }
            //System.out.println("[Searching for \"" + query +"\" in \"" + fName + "\"] -> " + q.toString());

            //Get search results
            Hits hits = searcher.search(bq);
            //System.out.println("..."+hits.length() + " hits");

            for (int i =0; i<hits.length(); i++) {
                Document doc = hits.doc(i);
                res.add(doc.get("uri"));
            }
        } catch (Exception ex) {
            log.error(ex);
        }

        return res;
    }

    /**
     * Executes a SparQl query to find directory objects. Uses LARQ to perform
     * a free-text search into a snow-balled terms index.
     * <p>
     * Ejecuta una consulta en SparQl para buscar DirectoryObjects. Usa LARQ
     * para realizar una búsqueda a texto abierto sobre un indide de lucene.
     * 
     * @param q     query string. Cadena de consulta.
     * @param lang  language for snowball analyzer. Idioma del analizador
     *              snowball.
     * @return      list of URIs matching the query. Lista de URIs que satisfacen
     *              los criterios de búsqueda.
     */
    public ArrayList<String> performQuery(String q, String lang, String what) {
        ArrayList<String> res = new ArrayList<String>();

        //Assert query string
        if (q.trim().equals("")) return null;

        //Get what to search
        String wh = "    {?obj a swbcomm:DirectoryObject}";
        if(what != null && !what.equals("")) {
            wh = "{?obj a swbcomm:" + what + "}";
        }
        
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
                    wh,//"    {?obj a swbcomm:DirectoryObject}",
                    "    UNION {?obj a swb:WebPage}.",
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
        index.close();
        return res;
    }

    /**
     * Gets the snowball-ed form of an input text. Applies the snowball algorithm
     * to each word in the text to get its root or stem.
     * <p>
     * Obtiene el lexema o raiz de un conjunto de palabras mediante el algoritmo
     * de snowball.
     *
     * @param input     text to stem. Texto a procesar
     * @param language  language code for snowball analyzer. Código del lenguaje
     *                  para el analizador snowball.
     * @param stopWords stop words for snowball analyzer. Palabras a omitir en
     *                  el análisis.
     * @return          root of the input. Cadena con las raíces de las palabras.
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
                res = res + new String(tk.termBuffer(), 0, tk.termLength()) + " ";//tk.termText() + " ";
            }
            ts.close();
        } catch (Exception ex) {
            log.error(ex);
        }
        return res.trim();
    }

    /**
     * Gets a slice of information from the search results. Used for paging.
     * <p>
     * Obtiene un fragmento de información de los resultados de búsqueda. Usado
     * para paginación.
     * 
     * @param page  number of slice to get (page). Número de la página a obtener.
     * @param max   elements per slice. Elementos por página.
     *
     * @return      set of <b>max</b> elements corresponding to slice <b>page</b>.
     *              Conjunto de <b>max</b> elementos pertenecientes a la página
     *              <b>page</b>.
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