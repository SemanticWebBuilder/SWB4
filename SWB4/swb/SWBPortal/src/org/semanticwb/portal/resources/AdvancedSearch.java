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
package org.semanticwb.portal.resources;

import com.hp.hpl.jena.query.Query;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Resource;
//import org.semanticwb.nlp.translation.SWBSparqlTranslator;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.nlp.SWBDictionary;
import org.semanticwb.nlp.SWBLocaleLexicon;
import org.semanticwb.nlp.Word;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericAdmResource;

// TODO: Auto-generated Javadoc
/**
 * The Class AdvancedSearch.
 * 
 * @author Hasdai Pacheco {haxdai(at)gmail.com}
 */
public class AdvancedSearch extends GenericAdmResource {
//
//    /** The log. */
//    private static Logger log = SWBUtils.getLogger(AdvancedSearch.class);
//    
//    /** The lang. */
//    private String lang = "x-x";
//    
//    /** The lex. */
//    private SWBDictionary lex;
//    
//    /** The tr. */
//    private SWBSparqlTranslator tr;
//    
//    /** The so_lat. */
//    private SemanticProperty so_lat = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/emexcatalog.owl#latitude");
//    
//    /** The so_long. */
//    private SemanticProperty so_long = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/emexcatalog.owl#longitude");
//    
//    /** The org. */
//    private SemanticClass org = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/emexcatalog.owl#Organisation");
//    
//    /** The solutions. */
//    private ArrayList<String> solutions = null;
//    
//    /** The query string. */
//    private String queryString = "";
//    
//    /** The dym. */
//    private String dym = "";
//    
//    /** The index. */
//    private IndexLARQ index;
//    
//    /** The smodel. */
//    private Model smodel;
//
//    /**
//     * Instantiates a new advanced search.
//     */
//    public AdvancedSearch() {
//    }
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
//     */
//    @Override
//    public void setResourceBase(Resource base) throws SWBResourceException {
//        super.setResourceBase(base);
//        try {
//            smodel = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
//            index = buildIndex();
//        }catch (Exception e) {
//            log.error(e);
//        }
//    }    
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
//     */
//    @Override
//    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        String mode = paramRequest.getMode();
//        if (mode.equals("SUGGEST")) {
//            doSuggest(request, response, paramRequest);
//        } else if (mode.equals("PAGE")) {
//            doShowPage(request, response, paramRequest);
//        } else {
//            super.processRequest(request, response, paramRequest);
//        }
//    }
//
//    /**
//     * Execute a query and gather the results. A natural language query is done
//     * in first place. If there is no results a second query (using lucene) is
//     * executed.
//     * 
//     * @param query the query
//     * @return the results
//     */
//    public ArrayList<String> getResults(String query) {
//        ArrayList<String> res = new ArrayList<String>();
//
//        //Assert query string
//        query = (query == null ? "" : query.trim());
//
//        //Get google maps API key
//        String mapKey = getResourceBase().getAttribute("mapKey");
//        mapKey = (mapKey == null ? "" : mapKey);
//
//        //Create SparQl Translator
//        tr = new SWBSparqlTranslator(lex);
//
//        //Translate query to SparQl
//        String sparqlQuery = lex.getLexicon(lang).getPrefixString() + "\n" + tr.translateSentence(query, true);
//        //dym = tr.didYouMean(query);
//        //dym = (dym.equalsIgnoreCase(query) ? "" : dym);
//
//        //System.out.println(sparqlQuery);
//
//        //If no errors and query is not empty, show results
//        if (tr.getErrCode() == 0 && !tr.isEmptyQuery()) {
//
//            //Get model to query
//            SemanticModel model = new SemanticModel("model", SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel());
//
//            //Execute select query
//            QueryExecution qexec = model.sparQLQuery(sparqlQuery);
//
//            //Get results of query as a result set
//            ResultSet rs = qexec.execSelect();
//
//            //If there are results
//            if (rs != null && rs.hasNext()) {
//                
//                //Get next result set
//                while (rs.hasNext()) {
//                    String mapbox = "";
//                    boolean requiresMap = false;
//                    boolean first = true;
//
//                    //Get next solution of the result set (var set)
//                    QuerySolution qs = rs.nextSolution();
//                    StringBuffer segment = new StringBuffer();
//                    segment.append("<tr><td>");
//
//                    //For each variable
//                    for (String vName : (List<String>) rs.getResultVars()) {
//                        //Get node
//                        RDFNode x = qs.get(vName);
//
//                        //If node is not null
//                        if (x != null) {
//                            //If node is the first in the solution (there is always a subject),
//                            //display node in bold
//                            if (first) {
//                                //Get SemanticObject of current node
//                                SemanticObject so = SemanticObject.createSemanticObject(x.toString());
//                                if (so != null) {
//                                    //Get url for viewing the object detail
//                                    String dUrl = getResourceBase().getAttribute("detailUrl") + "?uri=" + URLEncoder.encode(so.getURI());
//                                    //If object is an organization
//                                    //TODO: compare to GeoTaggable instead of org
//                                    if (so.instanceOf(org)) {
//                                        //Get url for viewing maps
//                                        String r = getResourceBase().getAttribute("mapUrl");
//                                        if (r == null) {
//                                            r = "#";
//                                        }
//
//                                        //Build map url
//                                        String mapUrl = r.replace(" ", "%20") +
//                                                "?lat=" + so.getProperty(so_lat) + "&long=" + so.getProperty(so_long);
//
//                                        //Create static minimap
//                                        requiresMap = true;
//                                        mapbox = mapbox + "<a href=\"#\" onclick=\"openMap('" + mapUrl +
//                                                "','','menubar=0,width=420,height=420');\"><img src=\"http://maps.google.com/staticmap?center=" +
//                                                so.getProperty(so_lat) + "," + so.getProperty(so_long) + "&zoom=12&size=100x100&markers=" +
//                                                so.getProperty(so_lat) + "," + so.getProperty(so_long) + ",blues&key=" +
//                                                mapKey + "\"></a>";
//
//                                        //Add detail link in object title
//                                        segment.append("<a href=\"" + dUrl + "\">" + "<b><font size=\"2\" face=\"verdana\">" +
//                                                so.getDisplayName(lang) + "(" + so.getSemanticClass().getDisplayName(lang) + ")</b></font></a><br>");                                        
//                                    } else { //Not an organization
//                                        segment.append("<a href=\"" + dUrl + "\">" + "<b><font size=\"2\" face=\"verdana\">" +
//                                                so.getDisplayName(lang) + "</b></font></a><br>");                                        
//                                    }
//                                    //If only one object requested, build object abstract
//                                    if (rs.getResultVars().size() == 1) {
//                                        segment.append(buildAbstract(so));
//                                    }
//                                } else {
//                                    segment.append("<b><font size=\"2\" face=\"verdana\">" +
//                                            lex.getLexicon(lang).getWord(vName, true).getLemma());
//                                            //lex.getObjWordTag(vName).getDisplayName() + "</b></font>" + "<br>");
//                                }
//                                first = false;
//                            } else { //Not a semantic object
//                                //If node is a literal, display a name, value pair
//                                if (x.isLiteral()) {
//                                    segment.append("<font size=\"2\" face=\"verdana\">" +
//                                            vName + ":<i>" + x.asNode().getLiteral().getLexicalForm() + "</i></font>" + "<br>");
//                                }
//                            }
//                        } else { //If empty node
//                            segment.append("<font size=\"2\" face=\"verdana\">" + vName + ": </font>" + "-<br>");
//                        }
//                    }
//                    segment.append("<br></td>");
//                    if (requiresMap) {
//                        segment.append("<td height=\"115\"> " + mapbox + "</td>");
//                    }
//                    segment.append("</tr>");
//                    res.add(segment.toString());
//                }
//                qexec.close();
//            } else { //If no results in natural language query, try open-text search
//                res = getOpenTextResults(query);
//            }            
//        } else {  //If natural language query could not be processed, try open-text search
//            res = getOpenTextResults(query);
//        }
//        return res;
//    }
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
//     */
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        SWBResourceURL rUrl = paramRequest.getRenderUrl();
//        User user = paramRequest.getUser();
//        StringBuffer sbf = new StringBuffer();
//        
//        //Get user language if any
//        if (user != null) {
//            if (!lang.equals(user.getLanguage())) {
//                lang = user.getLanguage();
//                SWBLocaleLexicon l = new SWBLocaleLexicon(lang, SWBDictionary.getLanguageName(lang));
//                lex.addLexicon(l);
//                lex.setLocale(lang);
//            }
//        } else {
//            if (!lang.equals("es")) {
//                lang = "es";
//            }
//        }        
//
//        //Create lexicon for NLP
//        String pf = getResourceBase().getAttribute("prefixFilter");
//        if (pf == null) {
//            pf = "";
//        }
//
//        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
//            //Set URL call method to call_DIRECT to make an AJAX call
//            rUrl.setCallMethod(rUrl.Call_DIRECT);
//            rUrl.setMode("SUGGEST");
//
//            //Add necesary scripting
//            sbf.append("<script type=\"text/javascript\">\n" +
//                "  dojo.addOnLoad(function () {\n" +
//                "  dojo.connect(dojo.byId('naturalQuery'), 'onkeydown', 'queryOnKeyDown');\n" +
//                "  dojo.connect(dojo.byId('naturalQuery'), 'onkeyup', 'queryOnKeyUp');\n" +
//                "  dojo.connect(dojo.byId('naturalQuery'), 'onblur', function () {\n" +
//                "      clearSuggestions();\n" +
//                "    });\n" +
//                "  });\n" +
//                "var source =\"" + rUrl + "\";\n" +
//                "var lang =\"" + lang + "\";\n" +
//                "var displayed;\n" +
//                "var pdisplayed;\n" +
//                "</script>\n" +
//                "<script type=\"text/javascript\" charset=\"utf-8\" src=\"" +
//                SWBPlatform.getContextPath() + "/swbadmin/js/swb_admin.js\" ></script>\n\n");
//
//        /**
//         * Clears the suggestions list and gives focus to the textarea.
//         */
//        sbf.append("<script type=\"text/javascript\">" +
//                "function clearSuggestions() {\n" +
//                "if (dojo.byId('results')) {\n" +
//                "dojo.byId('results').innerHTML = \"\";\n" +
//                "}\n" +
//                "displayed = false;\n" +
//                "curSelected = 0;\n" +
//                "dojo.byId('naturalQuery').focus();\n" +
//                "}\n");
//
//        sbf.append("function queryOnKeyDown (evt) {\n" +
//                "var wd = getCurrentWord('naturalQuery');\n" +
//                "if (evt.target.value == '' || wd.word.length < 3) {\n" +
//                "clearSuggestions();\n" +
//                "return;\n" +
//                "}\n" +
//                //CTRL+SHIFT+SPACE
//                "if (evt.ctrlKey && evt.shiftKey && evt.keyCode == dojo.keys.SPACE) {\n" +
//                "getSuggestions(wd, source, true, false);\n" +
//                "dojo.stopEvent(evt);\n" +
//                "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
//                "setSelection(curSelected, (wd.word == \"con\")?\"con \":\"\");\n" +
//                "clearSuggestions();\n" +
//                "pdisplayed = false;\n" +
//                "dojo.stopEvent(evt);\n" +
//                "}\n" +
//                "}\n");
//
//        sbf.append("function queryOnKeyUp (evt) {\n" +
//                "var wd = getCurrentWord('naturalQuery');\n" +
//                "if (evt.target.value == '' || wd.word.length < 3) {\n" +
//                "clearSuggestions();\n" +
//                "return;\n" +
//                "}\n" +
//                "if((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
//                "} else if (!displayed && !pdisplayed && wd.word == \"con\") {\n" +
//                "var pwd = getPreviousName(wd);\n" +
//                "if (pwd != \"undefined\") {\n" +
//                "getSuggestions(getPreviousName(wd), source, true, true);\n" +
//                "pdisplayed = true;\n" +
//                "}\n" +
//                "} else if (!displayed && pdisplayed && wd.word != \"con\") {\n" +
//                "clearSuggestions();\n" +
//                "pdisplayed = false;\n" +
//                "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {\n" +
//                "clearSuggestions();\n" +
//                "pdisplayed = false;\n" +
//                "}" +
//                //UP_ARROW
//                "else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {\n" +
//                "dojo.query('.resultEntry').style('background', 'white');\n" +
//                "curSelected--;\n" +
//                "if (curSelected < 0) {\n" +
//                "curSelected = 0;\n" +
//                "}" +
//                "console.log(curSelected);\n" +
//                "highLightSelection(curSelected, true);\n" +
//                "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
//                "dojo.byId('naturalQuery').focus();\n" +
//                "dojo.stopEvent(evt);\n" +
//                //DOWN_ARROW
//                "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {\n" +
//                "dojo.query('.resultEntry').style('background', 'white');\n" +
//                "curSelected++;\n" +
//                "if (curSelected > dojo.byId('resultlist').childNodes.length - 2) {\n" +
//                "curSelected = dojo.byId('resultlist').childNodes.length - 2;\n" +
//                "}\n" +
//                "console.log(curSelected);\n" +
//                "highLightSelection(curSelected, true);\n" +
//                "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id'+curSelected)).t;\n" +
//                "dojo.byId('naturalQuery').focus();\n" +
//                "dojo.stopEvent(evt);\n" +
//                "}\n" +
//                "}\n");
//
//        /**
//         * Gets the word at current cursor position in a textarea.
//         * @param elm textarea to extract word from.
//         */
//        sbf.append("function getCurrentWord(elm) {" +
//                "var cPos = getCaretPos(elm);" +
//                "var txt = dojo.byId(elm).value;" +
//                "var prevBlank = -1;" +
//                "var aftBlank = -1;" +
//                "var found = false;" +
//                "var wd = null;" +
//                "var wo = \"undefined\";" +
//                "var delimiters = \"\\n\\t \";" +
//                "if (txt != '') {" +
//                "for (var i = 0; i < txt.length; i++) {" +
//                "if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {" +
//                "prevBlank = i;" +
//                "}" +
//                "}" +
//                "for (i = cPos; i < txt.length && !found; i++) {" +
//                "if (delimiters.indexOf(txt.charAt(i)) != -1) {" +
//                "aftBlank = i;" +
//                "found = true;" +
//                "}" +
//                "}" +
//                "if (prevBlank == -1) {" +
//                "if (aftBlank == -1) {" +
//                "wd = txt;" +
//                "wo = {" +
//                "word: wd," +
//                "startP: 0," +
//                "endP: txt.length" +
//                "};" +
//                "} else {" +
//                "wd = txt.substring(0, aftBlank);" +
//                "wo = {" +
//                "word: wd," +
//                "startP: 0," +
//                "endP: aftBlank" +
//                "};" +
//                "}" +
//                "} else if (aftBlank == -1) {" +
//                "wd = txt.substring(prevBlank + 1, txt.length);" +
//                "wo = {" +
//                "word: wd," +
//                "startP: prevBlank + 1," +
//                "endP: txt.length" +
//                "};" +
//                "} else {" +
//                "wd = txt.substring(prevBlank + 1, aftBlank);" +
//                "wo = {" +
//                "word: wd," +
//                "startP: prevBlank + 1," +
//                "endP: aftBlank" +
//                "};" +
//                "}" +
//                "}" +
//                "return wo;" +
//                "}");
//
//        /**
//         * Gets cursor current position in a textarea.
//         * @param elm textarea to calculate caret position from.
//         */
//        sbf.append("function getCaretPos(elm) {" +
//                "var pos;" +
//                "if (dojo.doc.selection) {" +
//                "var Sel = document.selection.createRange();" +
//                "var SelLength = document.selection.createRange().text.length;" +
//                "Sel.moveStart ('character', -dojo.byId(elm).value.length);" +
//                "pos = Sel.text.length - SelLength;" +
//                "} else if (typeof dojo.byId(elm).selectionStart != undefined) {" +
//                "pos = dojo.byId(elm).selectionStart;" +
//                "}" +
//                "return pos;" +
//                "}");
//
//        /**
//         * Creates a suggestion list based on word at current cursor position.
//         * @param word word at cursor position
//         * @param src url of dataStore
//         * @param clear wheter or not to clear previous list
//         * @param props wheter or not to get properties of the current word as SemanticClass
//         */
//        sbf.append("function getSuggestions(word, src, clear, props) {" +
//                "if (clear) {" +
//                "clearSuggestions();" +
//                "}" +
//                "if(word.word == '') {" +
//                "return;" +
//                "}" +
//                "if (dojo.byId('results') && word.word != '') {" +
//                "dojo.byId('results').innerHTML = '<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\" width=\"20\" height=\"20\"/>loading...';" +
//                "}" +
//                "getHtml(src + \"?word=\" + escape(word.word) + \"&lang=\" + lang + \"&props=\" + props, 'results');" +
//                "displayed = true;" +
//                "highLightSelection(0, true);" +
//                "}");
//
//        /**
//         * Highlights an option in the suggestions list.
//         * @param id identifier of the list item to highlight
//         * @param high toggles highlight color.
//         */
//        sbf.append("function highLightSelection(id, high) {" +
//                "var ele = dojo.byId('id' + id);" +
//                "console.log('buscando id' + id);" +
//                "if (high) {" +
//                "dojo.style(ele, {" +
//                "\"background\":\"LightBlue\"" +
//                "});" +
//                "} else {" +
//                "dojo.style(ele, {" +
//                "\"background\":\"white\"" +
//                "});" +
//                "}" +
//                "}");
//
//        sbf.append("function getPreviousName (word) {" +
//                "var pName = \"\";" +
//                "var prevBrk = -1;" +
//                "var firstBrk = -1;" +
//                "var brkFound=false;" +
//                "var txt = dojo.byId('naturalQuery').value;" +
//                "var cPos = word.startP;" +
//                "var wd = null;" +
//                "var wo = \"undefined\";" +
//                "console.log(txt, cPos);" +
//                "var found = false;" +
//                "for(var i = cPos; i >= 0 && !found; i--) {" +
//                "  if (txt.charAt(i) == ']') {" +
//                "     brkFound = true;" +
//                "     prevBrk = i;" +
//                "  }" +
//                "}" +
//                "if (!brkFound) {" +
//                "  prevBrk = cPos - 1;" +
//                "}" +
//
//                
//                "console.log('prev:' + prevBrk);" +
//                "if (prevBrk == -1) {" +
//                "  prevBrk = cPos - 1;" +
//                "}" +
//                "found = false;" +
//                "if (brkFound) {" +
//                "  for (i = prevBrk - 1; i >= 0 && !found; i--) {" +
//                "    if (txt.charAt(i) == '[') {" +
//                "      firstBrk = i;" +
//                "      found = true;" +
//                "    }" +
//                "}" +
//                "} else {" +
//                "  for (i = prevBrk - 1; i >= 0 && !found; i--) {" +
//                "    if (txt.charAt(i) == ' ') {" +
//                "      firstBrk = i;" +
//                "      found = true;" +
//                "    }" +
//                "  }" +
//                "}" +
//                "if (prevBrk == -1) {" +
//                "return wo;" +
//                "}" +
//                "if (!found) {" +
//                "  firstBrk = 0;" +
//                "}" +
//                //"firstBrk++;" +
//                "wd = txt.substring(firstBrk, prevBrk);" +
//                "wo = {" +
//                "word: wd," +
//                "startP: firstBrk," +
//                "endP: prevBrk" +
//                "};" +
//                "return wo;" +
//                "}");
//        /**
//         * Replaces the current word in the textarea with the selected word from the
//         * suggestions list.
//         * @param selected index of current selected item
//         * @param prep
//         */
//        sbf.append("function setSelection(selected, prep) {" +
//                "var word = getCurrentWord('naturalQuery');" +
//                "var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\");" +
//                "        var word_array = newText.split(\" \");" +
//                "        if (word_array.length > 1) {\n" +
//                "          newText = prep + \"[\" + newText + \"]\";\n" +
//                "        } else {\n" +
//                "          newText = prep + newText;\n" +
//                "        }" +
//                "var valText = dojo.byId('naturalQuery').value;" +
//                "dojo.byId('naturalQuery').value = valText.substring(0, word.startP) +" +
//                "newText + valText.substring(word.endP, valText.length);" +
//                "}\n\n" +
//                    "      function getHtml(url, tagid) {\n" +
//                    "        dojo.xhrGet({\n" +
//                    "          url: url,\n" +
//                    "          load: function(response)\n" +
//                    "          {\n" +
//                    "            var tag=dojo.byId(tagid);\n" +
//                    "            if(tag) {\n" +
//                    "              var pan=dijit.byId(tagid);\n" +
//                    "              if(pan && pan.attr) {\n" +
//                    "                pan.attr('content',response);\n" +
//                    "              } else {\n" +
//                    "                tag.innerHTML = response;\n" +
//                    "              }\n" +
//                    "            } else {\n" +
//                    "              alert(\"No existe ning√∫n elemento con id \" + tagid);\n" +
//                    "            }\n" +
//                    "            return response;\n" +
//                    "          },\n" +
//                    "          error: function(response) {\n" +
//                    "            if(dojo.byId(tagid)) {\n" +
//                    "              dojo.byId(tagid).innerHTML = \"<p>Ocurrió un error con respuesta:<br />\" + response + \"</p>\";\n" +
//                    "            } else {\n" +
//                    "              alert(\"No existe ning√∫n elemento con id \" + tagid);\n" +
//                    "            }\n" +
//                    "              return response;\n" +
//                    "          },\n" +
//                    "          handleAs: \"text\"\n" +
//                    "        });\n" +
//                    "      }\n" +
//                    "    </script>");
//
//            String url = "";
//            Resourceable rsa = getResourceBase().getResourceable();
//            if (rsa != null && rsa instanceof WebPage) {
//                url = ((WebPage) rsa).getUrl();
//            }
//            sbf.append("    <form id=\"" + getResourceBase().getId() + "/natural\" " +
//                    "action=\"" + url + "\" method=\"post\" >\n" +
//                    "      <input type=\"text\" id=\"naturalQuery\" name=\"q\" autocomplete=\"off\">\n" +
//                    "    </form>" +
//                    "    <div id=\"results\"></div>\n");
//            response.getWriter().print(sbf.toString());
//        } else {
//            doShowResults(request, response, paramRequest);
//        }
//    }
//
//    /**
//     * Do suggest.
//     * 
//     * @param request the request
//     * @param response the response
//     * @param paramRequest the param request
//     * @throws SWBResourceException the sWB resource exception
//     * @throws IOException Signals that an I/O exception has occurred.
//     */
//    public void doSuggest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        PrintWriter out = response.getWriter();
//        StringBuffer sbf = new StringBuffer();
//        SortedSet objOptions = new TreeSet();
//        SortedSet proOptions = new TreeSet();
//        String word = request.getParameter("word");
//        boolean props = Boolean.parseBoolean(request.getParameter("props"));
//        boolean lPar = false;
//        boolean rPar = false;
//        int idCounter = 0;
//
//        word = URLDecoder.decode(word, "iso-8859-1");
//
//        response.setContentType("text/html; charset=iso-8859-1");
//        response.setHeader("Cache-control", "no-cache");
//        response.setHeader("Pragma", "no-cache");
//
//        if (word.indexOf("(") != -1) {
//            lPar = true;
//            word = word.replace("(", "");
//        }
//        if (word.indexOf(")") != -1) {
//            rPar = true;
//            word = word.replace(")", "");
//        }
//
//        word = word.replace("[", "");
//        word = word.replace("]", "");
//        word = word.trim();
//
//        //Create prefixs list
//        ArrayList<String> preflist = null;
//
//        //If asking for class name
//        if (!props) {
//            //Assert prefixes list
//            String pf = getResourceBase().getAttribute("prefixFilter");
//            if (pf == null) {
//                pf = "";
//            }
//
//            if (!pf.trim().equals("")) {
//                preflist = new ArrayList<String>(Arrays.asList(pf.split(",")));
//            }
//
//            //Filter semantic classes
//            Iterator<SemanticClass> cit = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
//            while (cit.hasNext()) {
//                SemanticClass sc = cit.next();
//                String tempc = sc.getDisplayName(lang);
//                if (tempc.toLowerCase().indexOf(word.toLowerCase()) != -1) {
//                        objOptions.add(tempc);
//                }
//            }
//
//            //Add all properties
//            Iterator<Word> sit = lex.getLexicon(lang).listPropertyWords();
//            while (sit.hasNext()) {
//                Word tempp = sit.next();
//                if (tempp.getLexicalForm().toLowerCase().indexOf(word.toLowerCase()) != -1) {
//                    proOptions.add(tempp);
//                }
//            }
//
//            //If there are suggestions
//            if (proOptions.size() != 0 || objOptions.size() != 0) {
//                idCounter = 0;
//                int index;
//                Iterator<String> rit = objOptions.iterator();
//
//                sbf.append("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
//                        "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
//                        "200px;width:300px;border:1px solid #a0a0ff;\">");
//                while (rit.hasNext()) {
//                    String tempi = (String) rit.next();
//                    index = tempi.toLowerCase().indexOf(word.toLowerCase());
//
//                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
//                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
//                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
//                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
//                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('results').innerHTML='';" +
//                            "dojo.byId('naturalQuery').focus();displayed=false;pdisplayed=false\">" + (lPar ? "(" : "") +
//                            "<font color=\"red\">" + tempi + "</font>" +
//                            (lPar ? ")" : "") + "</li>");
//                    idCounter++;
//                }
//
//                rit = proOptions.iterator();
//                while (rit.hasNext()) {
//                    String tempi = (String) rit.next();
//                    index = tempi.toLowerCase().indexOf(word.toLowerCase());
//
//                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
//                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
//                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
//                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
//                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('results').innerHTML='';" +
//                            "dojo.byId('naturalQuery').focus();displayed=false;pdisplayed=false;\">" + (lPar ? "(" : "") +
//                            "<font color=\"blue\">" + tempi + "</font>" +
//                            (lPar ? ")" : "") + "</li>");
//                    idCounter++;
//                }
//                sbf.append("</ul>");
//            } else {
//                sbf.append("<font size=\"2\" face=\"verdana\" color=\"red\">" + paramRequest.getLocaleString("msgNoSuggestions") + "</font>");
//            }
//        } else {
//            //System.out.println("Suggesting for " + word);
//            String tag = lex.getLexicon(lang).getWord(word, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_ID);
//            //String tag = lex.getObjWordTag(word).getObjId();
//
//            if (!tag.equals("")) {
//                sbf.append("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
//                    "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
//                    "200px;width:300px;border:1px solid #a0a0ff;\">");
//                SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
//                idCounter = 0;
//                Iterator<SemanticProperty> sit = sc.listProperties();
//                while (sit.hasNext()) {
//                    SemanticProperty t = sit.next();
//                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
//                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
//                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
//                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
//                            "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('results').innerHTML='';" +
//                            "dojo.byId('naturalQuery').focus();displayed=false;\">" + (lPar ? "(" : "") +
//                            "<font color=\"red\">" + t.getDisplayName(lang) + "</font>" +
//                            (lPar ? ")" : "") + "</li>");
//                    idCounter++;
//                }
//                sbf.append("</ul>");
//            } else {
//                tag = lex.getLexicon(lang).getWord(word, false).getSelectedTag().getTagInfoParam(SWBLocaleLexicon.PARAM_URI);
//                //tag = lex.getPropWordTag(word).getRangeClassId();
//                if (!tag.equals("")) {
//                    sbf.append("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
//                    "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
//                    "200px;width:300px;border:1px solid #a0a0ff;\">");
//                    SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
//                    idCounter = 0;
//                    Iterator<SemanticProperty> sit = sc.listProperties();
//                    while (sit.hasNext()) {
//                        SemanticProperty t = sit.next();
//                        sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
//                                "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
//                                "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
//                                "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
//                                "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('results').innerHTML='';" +
//                                "dojo.byId('naturalQuery').focus();displayed=false;\">" + (lPar ? "(" : "") +
//                                "<font color=\"red\">" + t.getDisplayName(lang) + "</font>" +
//                                (lPar ? ")" : "") + "</li>");
//                        idCounter++;
//                    }
//                    sbf.append("</ul>");
//                } else {
//                    sbf.append("<font size=\"2\" face=\"verdana\" color=\"red\">" + paramRequest.getLocaleString("msgNoSuggestions") + "</font>");
//                }
//            }
//        }
//        out.println(sbf.toString());
//    }
//
//    /**
//     * Do show page.
//     * 
//     * @param request the request
//     * @param response the response
//     * @param paramRequest the param request
//     * @throws SWBResourceException the sWB resource exception
//     * @throws IOException Signals that an I/O exception has occurred.
//     */
//    public void doShowPage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        SWBResourceURL rUrl = paramRequest.getRenderUrl().setMode("PAGE");
//        PrintWriter out = response.getWriter();
//        StringBuffer sbf = new StringBuffer();
//
//        if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
//            sbf.append("<script type=\"text/javascript\">\n" +
//                    "function openMap(loc, title, args) {\n" +
//                    "  window.open(loc, '', args);" +
//                    "}\n" +
//                    "</script>");
//
//            sbf.append("    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n" +
//                    "      <tr><td>\n" +
//                    "          <font size=\"2\" face=\"verdana\">" +
//                    paramRequest.getLocaleString("msgResults") +
//                    "<b><font color=\"#0000FF\"> " + queryString + "</font></b><br/></font></td></tr>\n");
//
//
//            if (solutions != null && solutions.size() > 0) {
//                int step = 10;
//                String page = request.getParameter("p");
//                int _start = 0;
//                int _end = 0;
//
//                if (page == null) {
//                    page = "1";
//                }
//
//                _start = step * (Integer.valueOf(page) - 1);
//                _end = _start + step - 1;
//                if (_end > solutions.size() - 1) {
//                    _end = solutions.size() - 1;
//                }
//
//                //System.out.println("page: " + page + ", start: " + _start + ", end: " + _end);
//
//                sbf.append("<tr><td>Mostrando resultados " + (_start + 1) + " - " + (_end + 1) + " de " + solutions.size() + "<br>" +
//                        "          <hr color=\"#16458D\" width=\"100%\" size=\"1\" /><BR/></td></tr>\n");
//
//                for (int i = _start; i <= _end; i++) {
//                    sbf.append(solutions.get(i));
//                }
//
//                sbf.append("<tr><td align=\"center\" colspan=\"2\"><hr width=\"100%\" size=\"1\" /><br/>\n");
//                double pages = Math.ceil((double) solutions.size() / (double) step);
//                for (int i = 1; i <= pages; i++) {
//                    _start = step * (i - 1);
//                    if ((_start + step) - 1 > solutions.size() - 1) {
//                        _end = solutions.size() - 1;
//                    } else {
//                        _end = (_start + step) - 1;
//                    }
//                    if (Integer.valueOf(page) == i) {
//                        sbf.append("<span>" + i + "</span> ");
//                    } else {
//                        rUrl = paramRequest.getRenderUrl().setMode("PAGE");
//                        rUrl.setParameter("p", String.valueOf(i));
//                        sbf.append("<a href =\"" + rUrl + "\">" + i + "</a> ");
//                    }
//                }
//
//                if (Integer.valueOf(page) < pages) {
//                    rUrl = paramRequest.getRenderUrl().setMode("PAGE");
//                    rUrl.setParameter("p", String.valueOf(Integer.valueOf(page) + 1));
//                    sbf.append("<a href=" + rUrl + ">" + paramRequest.getLocaleString("lblNext") + "</a>\n");
//                }
//                sbf.append("</td></tr>");
//            } else {
//                sbf.append("          <td align=\"center\" colspan=\"2\"><tr><hr color=\"#16458D\" width=\"100%\" size=\"1\" /><BR/>\n");
//                sbf.append("<font size=\"2\" face=\"verdana\" color=\"red\"><b>" +
//                        paramRequest.getLocaleString("msgNoResults") + "</b><br/></font>" +
//                        (dym.equals("") ? "" : "<font size=\"2\" face=\"verdana\">" +
//                        paramRequest.getLocaleString("msgDidYouMean") + " </font><b>" + dym + "</b><br/>" +
//                        "    <hr width=\"100%\" size=\"1\" /><br/></td></tr>\n"));
//            }
//            sbf.append("    </table>\n" +
//                    "    <BR/>\n");
//            out.println(sbf.toString());
//        } else {
//            doView(request, response, paramRequest);
//        }
//    }
//
//    /**
//     * Do show results.
//     * 
//     * @param request the request
//     * @param response the response
//     * @param paramRequest the param request
//     * @throws SWBResourceException the sWB resource exception
//     * @throws IOException Signals that an I/O exception has occurred.
//     */
//    public void doShowResults(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        queryString = request.getParameter("q");
//
//        //Assert query string
//        queryString = (queryString == null ? "" : queryString.trim());
//
//        if (!queryString.equals("")) {
//            solutions = getResults(queryString);
//                doShowPage(request, response, paramRequest);
//        }
//    }
//
//    /**
//     * Gathers information of a Semantic Object.
//     * 
//     * @param o Semantic Object to gather information from.
//     * @return the string
//     */
//    public String buildAbstract(SemanticObject o) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//        StringBuffer res = new StringBuffer();
//        HashMap properties = new HashMap<String, String>();
//        //Get list of object properties
//        Iterator<SemanticProperty> pit = o.listProperties();
//        while (pit.hasNext()) {
//            //Get next property
//            SemanticProperty sp = pit.next();
//            //Do not display rdf and owl properties
//            if (!sp.isObjectProperty() && (!sp.getPrefix().equals("rdf") && !sp.getPrefix().equals("owl") && !sp.getPrefix().equals("rdfs"))) {
//                //Get property value, if any, and display it
//                if (sp != null) {
//                    String val = "";
//
//                    /*if (sp.isDate()) {
//                        val = dateFormat.format(SWBUtils.TEXT.iso8601DateFormat(o.getDateProperty(sp)));
//                    } else if (sp.isDateTime()) {
//                        System.out.println("......its a datetime");
//                        Timestamp dt = o.getDateTimeProperty(sp);
//                        if (dt != null) {
//                            val = timeFormat.format(dt);
//                        }
//                    } else {*/
//                        val = o.getProperty(sp);
//                    //}
//                    if (val != null && !val.equals("")) {
//
//                        properties.put(sp.getDisplayName(lang).toUpperCase(), o.getProperty(sp));
//                    }
//                }
//            } else if (sp.isObjectProperty()) {
//                SemanticObject rg = o.getObjectProperty(sp);
//                if (rg != null) {
//                    properties.put(sp.getDisplayName(lang).toUpperCase(), rg.getDisplayName(lang));
//                }
//            }
//        }
//        Map sorted = new TreeMap(properties);
//        Set names = sorted.keySet();
//        Iterator<String> it = names.iterator();
//        while (it.hasNext()) {
//            String name = it.next();
//            String value = (String)sorted.get(name);
//            res.append("<font size=\"2\" face=\"verdana\">" +
//                  name + ": <i>" + value + "</i></font>" + "<br>");
//        }
//        return res.toString();
//    }    
//
//    /**
//     * Builds an index to perform searchs.
//     * 
//     * @return the index larq
//     */
//    public IndexLARQ buildIndex()
//    {
//        // ---- Read and index all literal strings.
//        IndexBuilderString larqBuilder = new IndexBuilderString() ;
//
//        // ---- Alternatively build the index after the model has been created.
//        larqBuilder.indexStatements(smodel.listStatements()) ;
//
//        // ---- Finish indexing
//        larqBuilder.closeWriter() ;
//
//        // ---- Create the access index
//        index = larqBuilder.getIndex() ;
//        return index ;
//    }
//
//    /**
//     * Gets the open text results.
//     * 
//     * @param q the q
//     * @return the open text results
//     */
//    public ArrayList<String> getOpenTextResults(String q) {
//        ArrayList<String> res = new ArrayList<String>();
//
//        //Assert query string
//        q = (q == null ? "" : q.trim());
//
//        String mapKey = getResourceBase().getAttribute("mapKey");
//        mapKey = (mapKey == null ? "" : mapKey);
//
//        //Build query to return classes with literal values in their properties
//        String queryString = "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
//                    "PREFIX pf:     <http://jena.hpl.hp.com/ARQ/property#>\n" +
//                    "PREFIX swb:    <http://www.semanticwebbuilder.org/swb4/ontology#>\n" +
//                    "PREFIX rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                    "PREFIX owl:    <http://www.w3.org/2002/07/owl#>\n" +
//                    "SELECT ?obj ?prop ?lit {\n" +
//                    "    ?lit pf:textMatch '" + q + "'.\n" +
//                    "    ?obj ?prop ?lit.\n" +
//                    "    ?obj rdf:type ?type.\n" +
//                    "    ?type rdf:type swb:Class\n" +
//                    "}\n";
//
//        // Make globally available
//        LARQ.setDefaultIndex(index);
//        Query query = QueryFactory.create(queryString);
//
//        QueryExecution qExec = QueryExecutionFactory.create(query, smodel);
//        ResultSet rs = qExec.execSelect();
//        //ResultSetFormatter.out(System.out, rs, query);
//
//        //If there are results
//        if (rs != null && rs.hasNext()) {
//            //Get next result set
//            while (rs.hasNext()) {
//                String mapbox = "";
//                boolean requiresMap = false;
//
//                //Get next solution of the result set (var set)
//                QuerySolution qs = rs.nextSolution();
//                StringBuffer segment = new StringBuffer();
//                segment.append("<tr><td>");
//
//                //Get node object from solution
//                RDFNode x = qs.get("obj");
//
//                //If node is not null
//                if (x != null) {
//                    //Get SemanticObject of current node
//                    SemanticObject so = SemanticObject.createSemanticObject(x.toString());
//                    if (so != null) {
//                        String dUrl = getResourceBase().getAttribute("detailUrl") + "?uri=" + URLEncoder.encode(so.getURI());
//                        //If object is an organization
//                        //TODO: compare to GeoTaggable instead of org
//                        if (so.instanceOf(org)) {
//                            String r = getResourceBase().getAttribute("mapUrl");
//                            if (r == null) {
//                                r = "#";
//                            }
//
//                            String mapUrl = r.replace(" ", "%20") +
//                                    "?lat=" + so.getProperty(so_lat) + "&long=" + so.getProperty(so_long);
//
//                            requiresMap = true;
//                            mapbox = mapbox + "<a href=\"#\" onclick=\"openMap('" + mapUrl +
//                                    "','','menubar=0,width=420,height=420');\"><img src=\"http://maps.google.com/staticmap?center=" +
//                                    so.getProperty(so_lat) + "," + so.getProperty(so_long) + "&zoom=12&size=100x100&markers=" +
//                                    so.getProperty(so_lat) + "," + so.getProperty(so_long) + ",blues&key=" +
//                                    mapKey + "\"></a>";
//
//                            segment.append("<a href=\"" + dUrl + "\">" + "<b><font size=\"2\" face=\"verdana\">" +
//                                    so.getDisplayName(lang) + "(" + so.getSemanticClass().getDisplayName(lang) + ")</b></font></a><br>");
//                            if (rs.getResultVars().size() == 1) {
//                                segment.append(buildAbstract(so));
//                            }
//                        } else { //Not an organization
//                            segment.append("<a href=\"" + dUrl + "\">" + "<b><font size=\"2\" face=\"verdana\">" +
//                                    so.getDisplayName(lang) + "</b></font></a><br>");                            
//                        }
//                        //Append object abstract
//                        segment.append(buildAbstract(so));
//                    }
//                }
//                segment.append("<br></td>");
//                if (requiresMap) {
//                    segment.append("<td height=\"115\"> " + mapbox + "</td>");
//                }
//                segment.append("</tr>");
//                res.add(segment.toString());
//            }
//        }
//        qExec.close();
//        return res;
//    }
}