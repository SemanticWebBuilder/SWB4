/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.nlp.Lexicon;
import org.semanticwb.nlp.tTranslator;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author Hasdai Pacheco {haxdai(at)gmail.com}
 */
public class SWBADBNatural extends GenericResource {
    private Logger log = SWBUtils.getLogger(SWBAListRelatedObjects.class);

    private tTranslator tr;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("SUGGEST")) {
            doSuggest(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer ret = new StringBuffer();
        SWBResourceURL url = paramRequest.getRenderUrl();
        String query = request.getParameter("naturalQuery");
        String errCount = request.getParameter("errCode");
        String sparqlQuery = request.getParameter("sparqlQuery");
        String lang = paramRequest.getUser().getLanguage();

        if (query == null) {
            query = "";
        } else {
            query = query.trim();
        }

        response.setContentType("text/html; charset=ISO-8859-1");
        url.setMode("SUGGEST");
        ret.append("<script type=\"text/javascript\">\n" +
                "dojo.require(\"dijit.form.Form\");\n" +
                "dojo.require(\"dijit.form.Button\");\n" +
                "</script>\n");
        ret.append("<script type=\"text/javascript\">\n" +
                "dojo.addOnLoad(function () {\n" +
                "dojo.connect(dojo.byId('naturalQuery'), 'onkeydown', 'queryOnKeyDown');\n" +
                "dojo.connect(dojo.byId('naturalQuery'), 'onkeyup', 'queryOnKeyUp');\n" +
                "dojo.connect(dojo.byId('naturalQuery'), 'onblur', function () {\n" +
                "clearSuggestions();\n" +
                "});\n" +
                "});\n" +
                "var source =\"" + url.toString() + "\";\n" +
                "var lang =\"" + paramRequest.getUser().getLanguage() + "\";\n" +
                "var displayed;\n" +
                "var pdisplayed;\n" +
                "</script>\n");
        /**
         * Clears the suggestions list and gives focus to the textarea.
         */
        ret.append("<script type=\"text/javascript\">" +
                "function clearSuggestions() {\n" +
                    "if (dojo.byId('results')) {\n" +
                        "dojo.byId('results').innerHTML = \"\";\n" +
                    "}\n" +
                    "displayed = false;\n" +
                    "curSelected = 0;\n" +
                    "dojo.byId('naturalQuery').focus();\n" +
                "}\n");

        ret.append("function queryOnKeyDown (evt) {\n" +
                        "var wd = getCurrentWord('naturalQuery');\n" +
                        "if (evt.target.value == '' || wd.word.length < 3) {\n" +
                            "clearSuggestions();\n" +
                            "return;\n" +
                        "}\n" +
                        //CTRL+SHIFT+SPACE
                        "if (evt.ctrlKey && evt.shiftKey && evt.keyCode == dojo.keys.SPACE) {\n" +
                            "getSuggestions(wd, source, true, false);\n" +
                            "dojo.stopEvent(evt);\n" +
                        "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                            "setSelection(curSelected, (wd.word == \"con\")?\"con \":\"\");\n" +
                            "clearSuggestions();\n" +
                            "pdisplayed = false;\n" +
                            "dojo.stopEvent(evt);\n" +
                        "}\n" +
                    "}\n");

        ret.append("function queryOnKeyUp (evt) {\n" +
                        "var wd = getCurrentWord('naturalQuery');\n" +
                        "if (evt.target.value == '' || wd.word.length < 3) {\n" +
                            "clearSuggestions();\n" +
                            "return;\n" +
                        "}\n" +
                        "if((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                        "} else if (!displayed && !pdisplayed && wd.word == \"con\") {\n" +
                            "var pwd = getPreviousName(wd);\n" +
                            "if (pwd != \"undefined\") {\n" +
                                "getSuggestions(getPreviousName(wd), source, true, true);\n" +
                                "pdisplayed = true;\n" +
                            "}\n" +
                        "} else if (!displayed && pdisplayed && wd.word != \"con\") {\n" +
                            "clearSuggestions();\n" +
                            "pdisplayed = false;\n" +
                        "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {\n" +
                            "clearSuggestions();\n" +
                            "pdisplayed = false;\n" +
                        "}" +
                        //UP_ARROW
                        "else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {\n" +
                            "dojo.query('.resultEntry').style('background', 'white');\n" +
                            "curSelected--;\n" +
                            "if (curSelected < 0) {\n" +
                                "curSelected = 0;\n" +
                            "}" +
                            "console.log(curSelected);\n" +
                            "highLightSelection(curSelected, true);\n" +
                            "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
                            "dojo.byId('naturalQuery').focus();\n" +
                            "dojo.stopEvent(evt);\n" +
                        //DOWN_ARROW
                        "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {\n" +
                            "dojo.query('.resultEntry').style('background', 'white');\n" +
                            "curSelected++;\n" +
                            "if (curSelected > dojo.byId('resultlist').childNodes.length - 2) {\n" +
                                "curSelected = dojo.byId('resultlist').childNodes.length - 2;\n" +
                            "}\n" +
                            "console.log(curSelected);\n" +
                            "highLightSelection(curSelected, true);\n" +
                            "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id'+curSelected)).t;\n" +
                            "dojo.byId('naturalQuery').focus();\n" +
                            "dojo.stopEvent(evt);\n" +
                        "}\n" +
                    "}\n");

                    /**
                    * Gets the word at current cursor position in a textarea.
                    * @param elm textarea to extract word from.
                    */
        ret.append("function getCurrentWord(elm) {" +
                        "var cPos = getCaretPos(elm);" +
                        "var txt = dojo.byId(elm).value;" +
                        "var prevBlank = -1;" +
                        "var aftBlank = -1;" +
                        "var found = false;" +
                        "var wd = null;" +
                        "var wo = \"undefined\";" +
                        "var delimiters = \"\\n\\t \";" +
                        "if (txt != '') {" +
                            "for (var i = 0; i < txt.length; i++) {" +
                                "if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {" +
                                    "prevBlank = i;" +
                                "}" +
                        "}" +
                        "for (i = cPos; i < txt.length && !found; i++) {" +
                            "if (delimiters.indexOf(txt.charAt(i)) != -1) {" +
                                "aftBlank = i;" +
                                "found = true;" +
                            "}" +
                        "}" +
                        "if (prevBlank == -1) {" +
                            "if (aftBlank == -1) {" +
                            "wd = txt;" +
                            "wo = {" +
                                "word: wd," +
                                "startP: 0," +
                                "endP: txt.length" +
                            "};" +
                        "} else {" +
                            "wd = txt.substring(0, aftBlank);" +
                            "wo = {" +
                                "word: wd," +
                                "startP: 0," +
                                "endP: aftBlank" +
                            "};" +
                        "}" +
                    "} else if (aftBlank == -1) {" +
                        "wd = txt.substring(prevBlank + 1, txt.length);" +
                            "wo = {" +
                                "word: wd," +
                                "startP: prevBlank + 1," +
                                "endP: txt.length" +
                            "};" +
                    "} else {" +
                        "wd = txt.substring(prevBlank + 1, aftBlank);" +
                        "wo = {" +
                            "word: wd," +
                            "startP: prevBlank + 1," +
                            "endP: aftBlank" +
                        "};" +
                    "}" +
                "}" +
                "return wo;" +
            "}");

        /**
         * Gets cursor current position in a textarea.
         * @param elm textarea to calculate caret position from.
         */
        ret.append("function getCaretPos(elm) {" +
                        "var pos;" +
                        "if (dojo.doc.selection) {" +
                            "var Sel = document.selection.createRange();" +
                            "var SelLength = document.selection.createRange().text.length;" +
                            "Sel.moveStart ('character', -dojo.byId(elm).value.length);" +
                            "pos = Sel.text.length - SelLength;" +
                        "} else if (typeof dojo.byId(elm).selectionStart != undefined) {" +
                            "pos = dojo.byId(elm).selectionStart;" +
                        "}" +
                        "return pos;" +
                    "}");

        /**
         * Creates a suggestion list based on word at current cursor position.
         * @param word word at cursor position
         * @param src url of dataStore
         * @param clear wheter or not to clear previous list
         * @param props wheter or not to get properties of the current word as SemanticClass
         */
        ret.append("function getSuggestions(word, src, clear, props) {" +
                        "if (clear) {" +
                            "clearSuggestions();" +
                        "}" +
                        "if(word.word == '') {" +
                           "return;" +
                        "}" +
                        "if (dojo.byId('results') && word.word != '') {" +
                            "dojo.byId('results').innerHTML = '<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\" width=\"20\" height=\"20\"/>" + paramRequest.getLocaleString("loading") + "...';" +
                        "}" +
                        "getHtml(src + \"?word=\" + word.word + \"&lang=\" + lang + \"&props=\" + props, 'results');" +
                        "displayed = true;" +
                        "highLightSelection(0, true);" +
                    "}");

        /**
         * Highlights an option in the suggestions list.
         * @param id identifier of the list item to highlight
         * @param high toggles highlight color.
        */
        ret.append("function highLightSelection(id, high) {" +
                        "var ele = dojo.byId('id' + id);" +
                        "console.log('buscando id' + id);"+
                        "if (high) {" +
                            "dojo.style(ele, {" +
                                "\"background\":\"LightBlue\"" +
                            "});" +
                        "} else {" +
                            "dojo.style(ele, {" +
                                "\"background\":\"white\"" +
                            "});" +
                        "}" +
                    "}");

        ret.append("function getPreviousName (word) {" +
                        "var pName = \"\";" +
                        "var prevBrk = -1;" +
                        "var firstBrk = -1;" +
                        "var txt = dojo.byId('naturalQuery').value;" +
                        "var cPos = word.startP;" +
                        "var wd = null;" +
                        "var wo = \"undefined\";" +
                        "var found = false;" +
                        "for (var i = cPos; i >= 0 && !found; i--) {" +
                            "if (txt.charAt(i) == ']') {" +
                                "prevBrk = i;" +
                                "found = true;" +
                            "}" +
                        "}" +
                        "found = false;" +
                        "for (i = prevBrk; i > 0 && !found; i--) {" +
                            "if (txt.charAt(i) == '[') {" +
                                "firstBrk = i;" +
                                "found = true;" +
                            "}" +
                        "}" +
                        "if (prevBrk == -1) {" +
                            "return wo;" +
                        "}" +
                        "firstBrk++;" +
                        "wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);" +
                        "wo = {" +
                            "word: wd," +
                            "startP: firstBrk," +
                            "endP: prevBrk" +
                        "};" +
                        "return wo;" +
                    "}");
        /**
         * Replaces the current word in the textarea with the selected word from the
         * suggestions list.
         * @param selected index of current selected item
         * @param prep
         */
        ret.append("function setSelection(selected, prep) {" +
                        "var word = getCurrentWord('naturalQuery');" +
                        "var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\");" +
                        "newText = prep + \"[\" + newText + \"]\";" +
                        "var valText = dojo.byId('naturalQuery').value;" +
                        "dojo.byId('naturalQuery').value = valText.substring(0, word.startP) +" +
                            "newText + valText.substring(word.endP, valText.length);" +
                    "}");
        ret.append("</script>");

        url = paramRequest.getActionUrl();
        url.setParameter("lang", lang);
        ret.append("<form id=\"" + getResourceBase().getId() + "/natural\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        ret.append("action=\"" + url.toString() + "\" method=\"post\" ");
        ret.append("onsubmit=\"submitForm('" + getResourceBase().getId() + "/natural'); return false;\">");
        ret.append("<fieldset>Natural Language Query Examples");
        ret.append("<PRE>");
        ret.append("1. [User] con [Activo]=true, [Primer Apellido]\n");
        ret.append("2. [Activo], [Contraseña] de [User] con [Usuario]=\"admin\"\n");
        ret.append("3. [Creación], [Correo Electrónico] de [User] con [Usuario] = \"admin\"\n");
        ret.append("4. todo de [User] con [Creación] < \"2009-04-02T13:36:21.409\"\n");
        ret.append("5. todo de [Página Web] con [Usuario Creador] con [Usuario] = \"admin\"\n");
        ret.append(paramRequest.getLocaleString("prompt"));
        ret.append("</PRE>");
        ret.append("Natural Language Query:<BR>");
        ret.append("<textarea id=\"naturalQuery\" name=\"naturalQuery\" rows=5 cols=70>");
        ret.append(query);
        ret.append("</textarea><div id=\"results\"></div>");
        ret.append("</fieldset>");
        ret.append("<fieldset>");
        ret.append("<button dojoType='dijit.form.Button' type=\"submit\">"+paramRequest.getLocaleString("send")+"</button>\n");
        ret.append("</fieldset>");

        if (errCount != null) {
            if (Integer.parseInt(errCount) == 0) {
                /*ret.append("<fieldset>");
                ret.append("<textarea rows=5 cols=70>");
                ret.append(request.getParameter("sparqlQuery"));
                ret.append("</textarea>");
                ret.append("</fieldset>");*/

                try {
                    Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
                    Query squery = QueryFactory.create(sparqlQuery);
                    squery.serialize();
                    QueryExecution qexec = QueryExecutionFactory.create(squery, model);
                    long time = System.currentTimeMillis();

                    try {
                        ret.append("<fieldset>");
                        ret.append("<table>");
                        ResultSet rs = qexec.execSelect();
                        ret.append("<thead>");
                        ret.append("<tr>");

                        if (rs.hasNext()) {
                            Iterator<String> itcols = rs.getResultVars().iterator();
                            while (itcols.hasNext()) {
                                ret.append("<th>");
                                ret.append(itcols.next());
                                ret.append("</th>");
                            }
                            ret.append("</tr>");
                            ret.append("</thead>");
                            ret.append("<tbody>");

                            boolean odd = true;
                            while (rs.hasNext()) {
                                odd = !odd;
                                QuerySolution rb = rs.nextSolution();

                                if (odd) {
                                    ret.append("<tr bgcolor=\"#EFEDEC\">");
                                } else {
                                    ret.append("<tr>");
                                }

                                Iterator<String> it = rs.getResultVars().iterator();
                                boolean first = true;
                                while (it.hasNext()) {
                                    String name = it.next();
                                    RDFNode x = rb.get(name);
                                    ret.append("<td >");
                                    SemanticObject so = SemanticObject.createSemanticObject(x.toString());

                                    if (so != null) {
                                        if (first) {
                                            ret.append("<a href=\"#\" onclick=\"parent.addNewTab('" + so.getURI() + "', '" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp', '" + so.getDisplayName(lang) + "');\">" + so.getDisplayName(lang) + "</a>");
                                            first = false;
                                        } else {
                                            SemanticClass tt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(so.getURI());
                                            if (tt != null) {
                                                ret.append(tt.getDisplayName(lang));
                                            } else {
                                                SemanticProperty stt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(so.getURI());
                                                ret.append(stt.getDisplayName(lang));
                                            }
                                        }
                                    } else {
                                        if (x != null) {
                                            ret.append(x);
                                        } else {
                                            ret.append(" - ");
                                        }
                                    }
                                    ret.append("</td>");
                                }
                                ret.append("</tr>");
                            }
                        } else {
                            ret.append("<font color='red'>"+ paramRequest.getLocaleString("nofound") +"</font>");
                            ret.append("</tr>");
                            ret.append("</thead>");
                        }
                        ret.append("</tbody>");
                        ret.append("</table>");
                        ret.append("</fieldset>");
                        ret.append("<fieldset>");
                        ret.append("<p aling=\"center\">" + paramRequest.getLocaleString("exectime") + (System.currentTimeMillis() - time) + "ms." + "</p>");
                        ret.append("</fieldset>");
                    } finally {
                        qexec.close();
                    }
                } catch (Exception e) {
                    if (tr.getErrCode() != 0) {
                        ret.append("<script language=\"javascript\" type=\"text/javascript\">alert('"+ paramRequest.getLocaleString("failmsg") +"');</script>");
                    }
                }
            } else {
                ret.append("<script language=\"javascript\" type=\"text/javascript\">");
                ret.append("alert(\"" + tr.getErrors().replace("\n", "\\n") + "\");");
                ret.append("</script>");
            }
        }
        ret.append("</form>");
        response.getWriter().print(ret.toString());
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String query = request.getParameter("naturalQuery");

        if (query == null || query.equals("")) {
            response.setMode(SWBResourceURL.Mode_VIEW);
            return;
        }

        Lexicon dict = new Lexicon(request.getParameter("lang"));
        tr = new tTranslator(dict);
        String queryString = dict.getPrefixString() + "\n" + tr.translateSentence(query);

        response.setRenderParameter("errCode", Integer.toString(tr.getErrCode()));
        response.setRenderParameter("sparqlQuery", queryString);
        response.setRenderParameter("naturalQuery", query);
        response.setMode(SWBResourceURL.Mode_VIEW);
    }

    public void doSuggest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SortedSet objOptions = new TreeSet();
        SortedSet proOptions = new TreeSet();
        String word = request.getParameter("word");
        String lang = request.getParameter("lang");
        boolean props = Boolean.parseBoolean(request.getParameter("props"));
        String tempcDn = "";
        boolean lPar = false;
        boolean rPar = false;
        int idCounter = 0;
        Lexicon lex = new Lexicon(lang);

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if (lang == null || lang.equals("")) {
            lang = "es";
        }
        if (word.indexOf("(") != -1) {
            lPar = true;
            word = word.replace("(", "");
        }
        if (word.indexOf(")") != -1) {
            rPar = true;
            word = word.replace(")", "");
        }

        word = word.replace("[", "");
        word = word.replace("]", "");
        word = word.trim();

        if (!props) {
            Iterator<SemanticClass> cit = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();

            while (cit.hasNext()) {
                SemanticClass tempc = cit.next();
                tempcDn = tempc.getDisplayName(lang);

                if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
                    objOptions.add(tempcDn);
                }

                Iterator<SemanticProperty> sit = tempc.listProperties();
                while (sit.hasNext()) {
                    SemanticProperty tempp = sit.next();
                    tempcDn = tempp.getDisplayName(lang);

                    if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
                        proOptions.add(tempcDn);
                    }
                }
            }

            if (proOptions.size() != 0 || objOptions.size() != 0) {
                idCounter = 0;
                int index;
                Iterator<String> rit = objOptions.iterator();

                out.println("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
                        "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
                        "200px;width:300px;border:1px solid #a0a0ff;\">");
                while (rit.hasNext()) {
                    String tempi = (String) rit.next();
                    index = tempi.toLowerCase().indexOf(word.toLowerCase());

                    out.print("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('results').innerHTML='';" +
                            "dojo.byId('naturalQuery').focus();displayed=false;pdisplayed=false\">" + (lPar ? "(" : "") +
                            "<font color=\"red\">" + tempi + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }

                rit = proOptions.iterator();
                while (rit.hasNext()) {
                    String tempi = (String) rit.next();
                    index = tempi.toLowerCase().indexOf(word.toLowerCase());

                    out.print("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('results').innerHTML='';" +
                            "dojo.byId('naturalQuery').focus();displayed=false;pdisplayed=false;\">" + (lPar ? "(" : "") +
                            "<font color=\"blue\">" + tempi + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }
                out.println("</ul>");
            }
        } else {
            String tag = lex.getObjWordTag(word).getObjId();

            out.println("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
                    "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
                    "200px;width:300px;border:1px solid #a0a0ff;\">");

            if (!tag.equals("")) {
                SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
                idCounter = 0;
                Iterator<SemanticProperty> sit = sc.listProperties();
                while (sit.hasNext()) {
                    SemanticProperty t = sit.next();
                    out.print("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('results').innerHTML='';" +
                            "dojo.byId('naturalQuery').focus();displayed=false;\">" + (lPar ? "(" : "") +
                            "<font color=\"red\">" + t.getDisplayName(lang) + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }
            } else {
                tag = lex.getPropWordTag(word).getRangeClassId();
                if (!tag.equals("")) {
                    SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
                    idCounter = 0;
                    Iterator<SemanticProperty> sit = sc.listProperties();
                    while (sit.hasNext()) {
                        SemanticProperty t = sit.next();
                        out.print("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                                "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                                "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                                "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                                "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('results').innerHTML='';" +
                                "dojo.byId('naturalQuery').focus();displayed=false;\">" + (lPar ? "(" : "") +
                                "<font color=\"red\">" + t.getDisplayName(lang) + "</font>" +
                                (lPar ? ")" : "") + "</li>");
                        idCounter++;
                    }
                }

            }
            out.println("</ul>");
        }
    }
}