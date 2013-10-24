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
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.semanticwb.model.User;
import org.semanticwb.nlp.SWBDictionary;
import org.semanticwb.nlp.SWBLocaleLexicon;
//import org.semanticwb.nlp.translation.SWBSparqlTranslator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/**
 * Recurso para consulta en lenguaje natural acotado a la base de datos de
 * Jena. Utiliza un traductor de lenguaje natural a SparQl.
 * <p>
 * Resource to query the jena database using restricted natural language. Uses
 * a natural language to SparQl translator.
 * 
 * @author Hasdai Pacheco {haxdai(at)gmail.com}
 */
public class SWBADBNatural extends GenericResource {

//    /** The lang. */
//    private String lang = "x-x";
//    
//    /** The lex. */
//    private SWBDictionary lex = null;
//    
//    /** The tr. */
//    private SWBSparqlTranslator tr;
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
//     */
//    @Override
//    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        String mode = paramRequest.getMode();
//        if (mode.equals("SUGGEST")) {
//            doSuggest(request, response, paramRequest);
//        } else {
//            super.processRequest(request, response, paramRequest);
//        }
//    }
//
//   /* (non-Javadoc)
//    * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
//    */
//   @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        SWBResourceURL rUrl = paramRequest.getRenderUrl();
//        SWBResourceURL aUrl = paramRequest.getActionUrl();
//        String query = request.getParameter("naturalQuery");
//        String errCount = request.getParameter("errCode");
//        String sparqlQuery = request.getParameter("sparqlQuery");
//        String dym = request.getParameter("didYouMean");
//        User user = paramRequest.getUser();
//        StringBuffer sbf = new StringBuffer();
//
//        /*response.setContentType("text/html; charset=iso-8859-1");
//        response.setHeader("Cache-control", "no-cache");
//        response.setHeader("Pragma", "no-cache");*/
//
//        //Create lexicon for NLP
//        lex = SWBDictionary.getInstance();
//
//        //Get user language if any
//        if (user != null) {
//            if (!lang.equals(user.getLanguage())) {
//                lang = user.getLanguage();
//                SWBLocaleLexicon l = new SWBLocaleLexicon(lang, SWBDictionary.getLanguageName(lang));
//                l.buildLexicon();
//                lex.addLexicon(l);
//                lex.setLocale(lang);
//            }
//        } else {
//            if (!lang.equals("es")) {
//                lang = "es";
//                lex.setLocale("es");
//            }
//        }        
//
//        //Assert query string
//        query = (query == null?"":query.trim());
//
//        //Assert suggested query
//        if (dym == null || dym.equals("")) {
//            dym = "";
//        } else {
//            dym = "<b>" + paramRequest.getLocaleString("didYouMean") + "</b> " + dym.trim();
//        }
//
//        //Set URL call method to call_DIRECT to make an AJAX call
//        rUrl.setCallMethod(rUrl.Call_DIRECT);
//        rUrl.setMode("SUGGEST");
//
//        //Add necesary scripting
//        sbf.append("<script type=\"text/javascript\">\n" +
//                "dojo.require(\"dijit.form.Form\");\n" +
//                "dojo.require(\"dijit.form.Button\");\n" +
//                "</script>\n");
//        sbf.append("<script type=\"text/javascript\">\n" +
//                "dojo.addOnLoad(function () {\n" +
//                "dojo.connect(dojo.byId('naturalQuery'), 'onkeydown', 'queryOnKeyDown');\n" +
//                "dojo.connect(dojo.byId('naturalQuery'), 'onkeyup', 'queryOnKeyUp');\n" +
//                "dojo.connect(dojo.byId('naturalQuery'), 'onblur', function () {\n" +
//                "clearSuggestions();\n" +
//                "});\n" +
//                "});\n" +
//                "var source =\"" + rUrl + "\";\n" +
//                "var lang =\"" + lang + "\";\n" +
//                "var displayed;\n" +
//                "var pdisplayed;\n" +
//                "</script>\n");
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
//                "dojo.byId('results').innerHTML = '<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\" width=\"20\" height=\"20\"/>" + paramRequest.getLocaleString("loading") + "...';" +
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
//                "var txt = dojo.byId('naturalQuery').value;" +
//                "var cPos = word.startP;" +
//                "var wd = null;" +
//                "var wo = \"undefined\";" +
//                "var found = false;" +
//                "for (var i = cPos; i >= 0 && !found; i--) {" +
//                "if (txt.charAt(i) == ']') {" +
//                "prevBrk = i;" +
//                "found = true;" +
//                "}" +
//                "}" +
//                "found = false;" +
//                "for (i = prevBrk; i > 0 && !found; i--) {" +
//                "if (txt.charAt(i) == '[') {" +
//                "firstBrk = i;" +
//                "found = true;" +
//                "}" +
//                "}" +
//                "if (prevBrk == -1) {" +
//                "return wo;" +
//                "}" +
//                "firstBrk++;" +
//                "wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);" +
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
//                "newText = prep + \"[\" + newText + \"]\";" +
//                "var valText = dojo.byId('naturalQuery').value;" +
//                "dojo.byId('naturalQuery').value = valText.substring(0, word.startP) +" +
//                "newText + valText.substring(word.endP, valText.length);" +
//                "}");
//        sbf.append("</script>");
//
//        sbf.append("<form id=\"" + getResourceBase().getId() + "/natural\" dojoType=\"dijit.form.Form\" class=\"swbform\" " +
//                   "action=\"" + aUrl + "\" method=\"post\" >\n" +
//        //sbf.append("onsubmit=\"submitForm('" + getResourceBase().getId() + "/natural'); return false;\">");
//                   "  <fieldset>\n" + paramRequest.getLocaleString("lblExamples") +
//                   "      <PRE>\n" +
//                   "1. Usuario con activo = true, [Primer Apellido]\n" +
//                   "2. Activo, idioma de usuario con [nombre(s)]=\"Admin\"\n" +
//                   //"3. Creación, [Correo Electrónico] de usuario con [nombre(s)] = \"Admin\"\n" +
//                   "4. todo de usuario con creación < \"2009-04-02T13:36:21.409\"\n" +
//                   "5. todo de [Página Web] con [Usuario Creador] con [nombre(s)] = \"Admin\"\n" +
//                   paramRequest.getLocaleString("prompt") +
//                   "      </PRE>\n" +
//                   paramRequest.getLocaleString("lblPromptQuery") + "<BR>\n" +
//                   "    <textarea id=\"naturalQuery\" name=\"naturalQuery\" rows=5 cols=70 >" +
//                   query + "</textarea>\n" +
//                   "    <div id=\"results\"></div>\n" +
//                   "    <div>" + dym + "</div>" +
//                   "  </fieldset>" +
//                   "  <fieldset>" +
//                   "  <button dojoType='dijit.form.Button' type=\"submit\">" +
//                        paramRequest.getLocaleString("send") +
//                     "</button>\n" +
//                   "  </fieldset>");
//
//        //If no translation errors, execute SparQl query
//        if (errCount != null) {
//            if (Integer.parseInt(errCount) == 0) {
//                /*sbf.append("<fieldset>");
//                sbf.append("<textarea rows=5 cols=70>");
//                sbf.append(request.getParameter("sparqlQuery"));
//                sbf.append("</textarea>");
//                sbf.append("</fieldset>");*/
//
//                try {
//                    Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
//                    SemanticModel mod = new SemanticModel("local", model);
//                    QueryExecution qexec = mod.sparQLQuery(sparqlQuery);
//                    long time = System.currentTimeMillis();
//
//                    try {
//                        sbf.append("<fieldset>\n" +
//                                   "  <legend>" + paramRequest.getLocaleString("resTitle") + "</legend>");
//                        sbf.append("<table>");
//                        ResultSet rs = qexec.execSelect();
//                        sbf.append("<thead>");
//                        sbf.append("<tr>");
//
//                        if (rs.hasNext()) {
//                            Iterator<String> itcols = rs.getResultVars().iterator();
//                            while (itcols.hasNext()) {
//                                sbf.append("<th>");
//                                sbf.append(itcols.next());
//                                sbf.append("</th>");
//                            }
//                            sbf.append("</tr>");
//                            sbf.append("</thead>");
//                            sbf.append("<tbody>");
//
//                            boolean odd = true;
//                            while (rs.hasNext()) {
//                                odd = !odd;
//                                QuerySolution rb = rs.nextSolution();
//
//                                if (odd) {
//                                    sbf.append("<tr bgcolor=\"#EFEDEC\">");
//                                } else {
//                                    sbf.append("<tr>");
//                                }
//
//                                boolean first = true;
//                                for (String name : (List<String>)rs.getResultVars()) {
//                                    RDFNode x = rb.get(name);
//                                    sbf.append("<td >");
//
//                                    //Check if it is a literal node
//                                    if (x != null && x.isLiteral()) {
//                                        sbf.append(x.asNode().getLiteral().getLexicalForm());
//                                    } else {
//                                        //Not a literal node, try to get a semantic object
//                                        SemanticObject so = SemanticObject.createSemanticObject(x.toString());
//                                        if (so != null) {
//                                            if (first) {
//                                                sbf.append("<a href=\"#\" onclick=\"parent.addNewTab('" + so.getURI() + "', '" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp', '" + so.getDisplayName(lang) + "');\">" + so.getDisplayName(lang) + "</a>");
//                                                first = false;
//                                            } else {
//                                                SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(so.getURI());
//                                                if (sc != null) {
//                                                    sbf.append(sc.getDisplayName(lang));
//                                                } else {
//                                                    SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(so.getURI());
//                                                    if (sp != null) {
//                                                        sbf.append(sp.getDisplayName(lang));
//                                                    } else {
//                                                        sbf.append(x.toString());
//                                                    }
//                                                }
//                                            }
//                                        } else {
//                                            //Not a semantic object, that is, not a semantic class nor a semantic property
//                                            sbf.append(x);
//                                        }
//                                    }
//                                    sbf.append("</td>");
//                                }
//                                sbf.append("</tr>");
//                            }
//                        } else {
//                            sbf.append("<font color='red'>" + paramRequest.getLocaleString("nofound") + "</font>");
//                            sbf.append("</tr>");
//                            sbf.append("</thead>");
//                        }
//                        sbf.append("</tbody>");
//                        sbf.append("</table>");
//                        sbf.append("</fieldset>");
//                        sbf.append("<fieldset>");
//                        sbf.append(paramRequest.getLocaleString("exectime") + " " + (System.currentTimeMillis() - time) + "ms.");
//                        sbf.append("</fieldset>");
//                    } finally {
//                        qexec.close();
//                    }
//                } catch (Exception e) {
//                    if (tr.getErrCode() != 0) {
//                        sbf.append("<script language=\"javascript\" type=\"text/javascript\">alert('" + paramRequest.getLocaleString("failmsg") + "');</script>");
//                    }
//                }
//            } else {
//                sbf.append("<script language=\"javascript\" type=\"text/javascript\">");
//                sbf.append("alert(\"" + tr.getErrors().replace("\n", "\\n") + "\");");
//                sbf.append("</script>");
//            }
//        }
//        sbf.append("</form>");
//        response.getWriter().print(sbf.toString());
//    }
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
//     */
//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        String query = request.getParameter("naturalQuery");
//        String queryString = "";
//        String dym = "";
//
//        //Assert query string
//        query = (query == null ? "" : query.trim());
//
//        if (!query.equals("")) {
//            //Create SparQl translator
//            tr = new SWBSparqlTranslator(lex);
//            queryString = lex.getLexicon(lang).getPrefixString() + "\n" + tr.translateSentence(query, false);
//            //System.out.println("--->Query String:");
//            //System.out.println(queryString);
//            //dym = tr.didYouMean(query);
//
//            //If no different suggestion
//            //if (query.toLowerCase().equals(dym.toLowerCase())) {
//            //    dym = "";
//            //}
//
//            response.setRenderParameter("errCode", Integer.toString(tr.getErrCode()));
//            response.setRenderParameter("sparqlQuery", queryString);
//            response.setRenderParameter("naturalQuery", query);
//            response.setRenderParameter("didYouMean", dym);
//        } else {
//            response.setRenderParameter("errCode", Integer.toString(1));
//            response.setRenderParameter("sparqlQuery", "");
//            response.setRenderParameter("naturalQuery", query);
//            response.setRenderParameter("didYouMean", dym);
//        }
//        response.setMode(response.Mode_VIEW);
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
//        String tempcDn = "";
//        boolean lPar = false;
//        boolean rPar = false;
//        int idCounter = 0;
//
//        //System.out.println("--Suggesting " + (props?" properties":"") + " for word " + word);
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
//        //No properties queried, user searching for objects or properties
//        if (!props) {
//            Iterator<SemanticClass> cit = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
//
//            while (cit.hasNext()) {
//                SemanticClass tempc = cit.next();
//                tempcDn = tempc.getDisplayName(lang);
//
//                if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
//                    objOptions.add(tempcDn);
//                }
//
//                Iterator<SemanticProperty> sit = tempc.listProperties();
//                while (sit.hasNext()) {
//                    SemanticProperty tempp = sit.next();
//                    tempcDn = tempp.getDisplayName(lang);
//
//                    if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
//                        proOptions.add(tempcDn);
//                    }
//                }
//            }
//
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
//            }
//        } else { //User has queried property of an object
//            String tag = lex.getLexicon(lang).getWord(word, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_ID);
//            //String tag = lex.getObjWordTag(word).getObjId();
//
//            sbf.append("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
//                    "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
//                    "200px;width:300px;border:1px solid #a0a0ff;\">");
//
//            if (!tag.equals("")) {
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
//            } else {
//                tag = lex.getLexicon(lang).getWord(word, false).getSelectedTag().getTagInfoParam(SWBLocaleLexicon.PARAM_ID);
//                SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(tag);
//                tag = sc.getPrefix() + ":" + sc.getName();
//                //tag = lex.getPropWordTag(word).getRangeClassId();
//                if (!tag.equals("")) {
//                    sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
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
//                }
//            }
//            sbf.append("</ul>");
//        }
//        out.println(sbf.toString());
//    }
}