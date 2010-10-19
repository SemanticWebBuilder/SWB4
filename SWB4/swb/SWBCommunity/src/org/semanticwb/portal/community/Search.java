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

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.larq.IndexLARQ;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.util.StringUtils;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.indexer.searcher.SearchDocument;
import org.semanticwb.portal.indexer.searcher.SearchQuery;
import org.semanticwb.portal.indexer.searcher.SearchResults;
import org.semanticwb.portal.indexer.searcher.SearchTerm;

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
            //Assert query string
            String q = request.getParameter("q");
            if (q == null) return;

            String what = request.getParameter("what");
            if (what == null) what = "";

            String cat = request.getParameter("scategory");
            if (cat == null) cat = "";

            if (what.trim().equalsIgnoreCase("All")) {
                solutions = search4Members(q, language);
                solutions.addAll(executeSearch(q, "", cat, paramRequest.getUser()));
            } else if (what.trim().equalsIgnoreCase("Member")) {
                solutions=search4Members(q, language);
            } else {
                solutions = executeSearch(q, what, cat, paramRequest.getUser());
            }

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

    private ArrayList<SemanticObject> executeSearch(String q, String cls, String category, User user) {
        ArrayList<SemanticObject> res = new ArrayList<SemanticObject>();

        String scope = getResourceBase().getAttribute("modelScope", "");
        //System.out.println("---Buscando " + q);
        SearchQuery query=new SearchQuery();
        SearchQuery tquery=new SearchQuery(SearchQuery.OPER_AND);
        query.addQuery(tquery);
        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_TITLE, q, SearchTerm.OPER_OR));
        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_DESCRIPTION, q, SearchTerm.OPER_OR));
        tquery.addTerm(new SearchTerm(SWBIndexer.ATT_TAGS, q, SearchTerm.OPER_OR));

        if (!cls.trim().equalsIgnoreCase("")) {
            //System.out.println("---Filtrando por clase " + cls);
            query.addTerm(new SearchTerm(SWBIndexer.ATT_CLASS, cls, SearchTerm.OPER_AND));
        }

        if (!category.trim().equals("")) {
            //System.out.println("---Filtrando por categoría " + category);
            query.addTerm(new SearchTerm(SWBIndexer.ATT_CATEGORY, category, SearchTerm.OPER_AND));
        }

        if(!scope.trim().equals("")) {
            //System.out.println("---Filtrando por categoría " + category);
            query.addTerm(new SearchTerm(SWBIndexer.ATT_MODEL, scope, SearchTerm.OPER_AND));
        }

        SearchResults sres = SWBPortal.getIndexMgr().getDefaultIndexer().search(query, user);

        Iterator<SearchDocument> docs = sres.listDocuments();
        while(docs.hasNext()) {
            SearchDocument doc = docs.next();
            if (doc.getSearchable() != null) {
                res.add(doc.getSearchable().getSemanticObject());
            }
        }
        
        return res;
    }

    public ArrayList<SemanticObject> search4Members(String q, String lang) {
        ArrayList<SemanticObject> res = new ArrayList<SemanticObject>();

        //Assert query string
        if(q.trim().equals("")) return null;

        String [] words = getSnowballForm(q, lang, stopWords).trim().split(" ");


        q = "    {\n" +
            "        ?user swb:usrFirstName ?fname.\n" +
            "        FILTER regex(?fname, \"" + words[0] + "\", \"i\")\n" +
            "        OPTIONAL {" +
            "            ?usr swb:usrLastName ?lname.\n" +
            "            FILTER regex(?lname, \"" + words[0] + "\", \"i\")\n" +
            "        }\n" +
            "    }\n";
        
        for (int i = 1; i < words.length; i++) {
            q += " UNION\n" +
            "    {\n" +
            "        ?user swb:usrFirstName ?fname.\n" +
            "        FILTER regex(?fname, \"" + words[i] + "\", \"i\")\n" +
            "        OPTIONAL {" +
            "            ?usr swb:usrLastName ?lname.\n" +
            "            FILTER regex(?lname, \"" + words[i] + "\", \"i\")\n" +
            "        }\n" +
            "    }\n";
        }

        /*q = "";
        for (String s : words) {
            q = q + "+" + s + " ";
        }*/

        //Build query to return classes with literal values in their properties
        String queryString = "PREFIX swb:     <http://www.semanticwebbuilder.org/swb4/ontology#>"
                    +" PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                    +" PREFIX rdfs:    <http://www.w3.org/2000/01/rdf-schema#>"
                    +" PREFIX owl:     <http://www.w3.org/2002/07/owl#>"
                    +" PREFIX swbcomm: <http://www.semanticwebbuilder.org/swb4/community#>"
                    +" SELECT DISTINCT ?user WHERE {"
                    +"    ?obj a swbcomm:Member."
                    +"    ?obj swbcomm:memUser ?user."
                    +q
                    +"}";

        /*System.out.println("---------------------------------");
        System.out.println(queryString);
        System.out.println("---------------------------------");*/

        res = executeSparQlQuery(queryString, "user");
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

    public ArrayList<SemanticObject> executeSparQlQuery(String query, String objName) {
        ArrayList<SemanticObject> res = new ArrayList<SemanticObject>();

        try {
            Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
            SemanticModel mod = new SemanticModel("local", model);
            QueryExecution qexec = mod.sparQLQuery(query);

            ResultSet rs = qexec.execSelect();

            //If there are results
            if (rs != null && rs.hasNext()) {
                //Get next result set
                while (rs.hasNext()) {
                    //Get next solution of the result set (var set)
                    QuerySolution qs = rs.nextSolution();

                    //Get node object from solution
                    RDFNode x = qs.get(objName);

                    //If node is not null
                    if (x != null && x.isResource()) {
                        res.add(SemanticObject.createSemanticObject(x.asNode().getURI()));
                    }
                }
            }
            return res;
        } catch (Exception e) {
            log.error(e);
        }
        
        return null;
    }
}
