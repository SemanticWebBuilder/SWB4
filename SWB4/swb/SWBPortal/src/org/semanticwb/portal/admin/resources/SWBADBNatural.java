/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
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
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author juan.fernandez
 */
public class SWBADBNatural extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBADBSparql.class);
    private tTranslator tr;

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
        SWBResourceURL url = paramRequest.getActionUrl();
        String query = request.getParameter("naturalQuery");
        String errCount = request.getParameter("errCode");
        String sparqlQuery = request.getParameter("sparqlQuery");
        String lang = paramRequest.getUser().getLanguage();

        if (query == null) {
            query = "";
        } else {
            query = query.trim();
        }

        url.setParameter("lang", lang);

        ret.append("<script type=\"text/javascript\">\n" +
                "dojo.require(\"dojo.parser\");\n" +
                "dojo.require(\"dijit.layout.ContentPane\");\n"+
                "dojo.require(\"dijit.form.Form\");\n" +
                "dojo.require(\"dijit.form.Button\");\n" +
                "</script>\n");
        ret.append("<script src=\"" + SWBPlatform.getContextPath() +"/swbadmin/js/acTextArea.js\"></script>");
        ret.append("<script type=\"text/javascript\">\n" +
                "dojo.addOnLoad(function () {\n" +
                    "dojo.connect(dojo.byId('naturalQuery'), 'onkeydown', 'queryOnKeyDown');\n" +
                    "dojo.connect(dojo.byId('naturalQuery'), 'onkeyup', 'queryOnKeyUp');\n" +
                    "dojo.connect(dojo.byId('naturalQuery'), 'onblur', function () {\n" +
                        "clearSuggestions();\n" +
                    "});\n" +
                "});\n" +
                "var source =\"" + SWBPlatform.getContextPath() + "/swbadmin/jsp/acTextAreaStore.jsp\";\n" +
                "var lang =\"" + paramRequest.getUser().getLanguage() + "\";\n" +
            //    "var displayed = false;\n" +
          //      "var pdisplayed = false;\n" +
            "</script>\n");
 
        /*ret.append("<script type=\"text/javascript\"> function clearSuggestions() {\n" +
                        "if (dojo.byId('results')) {\n" +
                            "dojo.byId('results').innerHTML = \"\";\n" +
                        "}\n" +
                        "displayed = false;\n" +
                        "curSelected = 0;\n" +
                        "dojo.byId('naturalQuery').focus();\n" +
                    "}\n");

        ret.append("function setSelection(selected, prep) {\n" +
                        "var word = getCurrentWord('naturalQuery');\n" +
                        "var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\");\n" +
                        "newText = prep + \"[\" + newText + \"]\";\n" +
                        "var valText = dojo.byId('naturalQuery').value;\n" +
                        "dojo.byId('naturalQuery').value = valText.substring(0, word.startP) + \n" +
                        "newText + valText.substring(word.endP, valText.length);\n" +
                    "}\n");

        ret.append("function highLightSelection(id, high) {\n" +
                        "var ele = dojo.byId('id' + id);\n" +
                        "if (high) {\n" +
                            "dojo.style(ele, {\n" +
                                "\"background\":\"LightBlue\"\n" +
                            "});\n" +
                        "} else {\n" +
                            "dojo.style(ele, {\n" +
                            "\"background\":\"white\"\n" +
                            "});\n" +
                        "}\n" +
                    "}\n");

        ret.append("function getHtml (url, tagid) {\n" +
                        "dojo.xhrGet ({\n" +
                            "url: url,\n" +
                            "load: function(response, ioArgs) {\n" +
                            "var tag = dojo.byId(tagid);\n" +
                            "if (tag) {\n" +
                                "tag.innerHTML = response;\n" +
                            "} else {\n" +
                                "alert('No existe ningún elemento con id ' + tagid);\n" +
                            "}\n" +
                            "highLightSelection(0, true);\n" +
                            "return response;\n" +
                        "},\n" +
                        "error: function(response, ioArgs) {\n" +
                            "if (dojo.byId(tagid)) {\n" +
                                "dojo.byId(tagid).innerHTML =\n" +
                                "\"<font color='red'>Cannot load suggestions, try again</font>\";\n" +
                            "} else {\n" +
                                "alert('No existe ningún elemento con id ' + tagid);\n" +
                            "}\n" +
                            "return response;\n" +
                            "},\n" +
                                "handleAs: \"text\",\n" +
                                "timeout: 5000\n" +
                        "});\n" +
                    "}\n");

        ret.append("function getSuggestions(word, src, clear, props) {\n" +
                        "if (clear) {\n" +
                            "clearSuggestions();\n" +
                        "}\n" +
                        "if(word.word == '') {\n" +
                            "return;\n" +
                        "}\n" +
                        "if (dojo.byId('results') && word.word != '') {\n" +
                                "dojo.byId('results').innerHTML = \"<font color='Green'>Loading...</font>\";" +
                        "}\n" +
                        "getHtml(src + \"?word=\" + word.word + \"&lang=\" + lang + \"&props=\" + props, 'results');\n" +
                        "displayed = true;\n" +
                    "}\n");

        ret.append("function getCaretPos(elm) {\n" +
                        "var pos;\n" +
                         "if (dojo.doc.selection) {\n" +
                                "var Sel = document.selection.createRange();\n" +
                                "var SelLength = document.selection.createRange().text.length;\n" +
                                "Sel.moveStart ('character', -dojo.byId(elm).value.length);\n" +
                                "pos = Sel.text.length - SelLength;\n" +
                         "} else if (typeof dojo.byId(elm).selectionStart != undefined) {\n" +
                                "pos = dojo.byId(elm).selectionStart;\n" +
                         "}\n" +
                        "return pos;\n" +
                    "}\n");
                    
        ret.append("function getCurrentWord(elm) {\n" +
                        "var cPos = getCaretPos(elm);\n" +
                        "var txt = dojo.byId(elm).value;\n" +
                        "var prevBlank = -1;\n" +
                        "var aftBlank = -1;\n" +
                        "var found = false;\n" +
                        "var wd = null;\n" +
                        "var wo = \"undefined\";\n" +
                        "var delimiters = \"\\n\\t \";" +
                        "if (txt != '') {\n" +
                            "for (var i = 0; i < txt.length; i++) {\n" +
                                "if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {\n" +
                                    "prevBlank = i;\n" +
                                "}\n" +
                        "}\n" +
                        "for (i = cPos; i < txt.length && !found; i++) {\n" +
                            "if (delimiters.indexOf(txt.charAt(i)) != -1) {\n" +
                                "aftBlank = i;\n" +
                                "found = true;\n" +
                            "}\n" +
                        "}\n" +
                        "if (prevBlank == -1) {\n" +
                            "if (aftBlank == -1) {\n" +
                                "wd = txt;\n" +
                                "wo = {\n" +
                                "word: wd,\n" +
                                "startP: 0,\n" +
                                "endP: txt.length\n" +
                            "};\n" +
                        "} else {\n" +
                            "wd = txt.substring(0, aftBlank);\n" +
                            "wo = {\n" +
                                "word: wd,\n" +
                                "startP: 0,\n" +
                                "endP: aftBlank\n" +
                            "};\n" +
                        "}\n" +
                    "} else if (aftBlank == -1) {\n" +
                        "wd = txt.substring(prevBlank + 1, txt.length);\n" +
                         "wo = {\n" +
                            "word: wd,\n" +
                            "startP: prevBlank + 1,\n" +
                            "endP: txt.length\n" +
                        "};\n" +
                    "} else {\n" +
                        "wd = txt.substring(prevBlank + 1, aftBlank);\n" +
                        "wo = {\n" +
                            "word: wd,\n" +
                            "startP: prevBlank + 1,\n" +
                            "endP: aftBlank\n" +
                        "};\n" +
                    "}\n" +
                "}\n" +
                "return wo;\n" +
            "}\n");

        ret.append("function queryOnKeyUp (evt) {\n" +
                        "var wd = getCurrentWord('naturalQuery');\n" +
                        "if (evt.target.value == '' || wd.word.length < 3) {\n" +
                            "clearSuggestions();\n" +
                            "return;\n" +
                        "}\n" +
                        "if (!displayed && !pdisplayed && wd.word == \"con\") {\n" +
                            "var pwd = getPreviousName(wd);\n" +
                            "if (pwd != \"undefined\") {\n" +
                                "getSuggestions(getPreviousName(wd), source, true, true);\n" +
                                "pdisplayed = true;\n" +
                            "} else if (!displayed && pdisplayed && wd.word != \"con\") {\n" +
                                "clearSuggestions();\n" +
                                "pdisplayed = false;\n" +
                            "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {\n" +
                                "clearSuggestions();\n" +
                                "pdisplayed = false;\n" +
                            "}\n" +
                            "else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {\n" +
                                "dojo.query('.resultEntry').style('background', 'white');\n" +
                                "curSelected--;\n" +
                                "if (curSelected < 0) {\n" +
                                    "curSelected = 0;\n" +
                                "}\n" +
                                "highLightSelection(curSelected, true);\n" +
                                "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
                                "dojo.byId('naturalQuery').focus();\n" +
                                "dojo.stopEvent(evt);\n" +
                            "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {\n" +
                                "dojo.query('.resultEntry').style('background', 'white');\n" +
                                "curSelected++;\n" +
                                "if (curSelected > dojo.byId('resultlist').childNodes.length - 2) {\n" +
                                    "curSelected = dojo.byId('resultlist').childNodes.length - 2;\n" +
                                "}\n" +
                                "highLightSelection(curSelected, true);\n" +
                                "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id'+curSelected)).t;\n" +
                                "dojo.byId('naturalQuery').focus();\n" +
                                "dojo.stopEvent(evt);\n" +
                            "}\n" +
                        "}}\n");

        ret.append("function queryOnKeyDown (evt) {\n" +
                        "var wd = getCurrentWord('naturalQuery');\n" +
                        "console.log('down');\n" +
                        "if (evt.target.value == '' || wd.word.length < 3) {\n" +
                            "clearSuggestions();\n" +
                            "return;\n" +
                        "}\n" +
                        "if (evt.ctrlKey && evt.keyCode == dojo.keys.SPACE) {\n" +
                            "getSuggestions(wd, source, true, false);\n" +
                            "dojo.stopEvent(evt);\n" +
                        "} else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                            "setSelection(curSelected, (wd.word == \"con\")?\"con \":\"\");\n" +
                            "clearSuggestions();\n" +
                            "pdisplayed = false;\n" +
                            "dojo.stopEvent(evt);\n" +
                        "}\n" +
                    "}\n");

        ret.append("function getPreviousName (word) {\n" +
                        "var pName = \"\";\n" +
                        "var prevBrk = -1;\n" +
                        "var firstBrk = -1;\n" +
                        "var txt = dojo.byId('naturalQuery').value;\n" +
                        "var cPos = word.startP;\n" +
                        "var wd = null;\n" +
                        "var wo = \"undefined\";\n" +
                        "var found = false;\n" +

                        "for (var i = cPos; i >= 0 && !found; i--) {\n" +
                            "if (txt.charAt(i) == ']') {\n" +
                                "prevBrk = i;\n" +
                                "found = true;\n" +
                            "}\n" +
                        "}\n" +
                        "found = false;\n" +
                        "for (i = prevBrk; i > 0 && !found; i--) {\n" +
                            "if (txt.charAt(i) == '[') {\n" +
                                "firstBrk = i;\n" +
                                "found = true;\n" +
                        "}\n" +
                    "}\n" +
                    "if (prevBrk == -1) {\n" +
                        "return wo;\n" +
                    "}\n" +
                    "firstBrk++;\n" +
                    "wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);\n" +
                    "wo = {\n" +
                        "word: wd,\n" +
                        "startP: firstBrk,\n" +
                        "endP: prevBrk\n" +
                    "};\n" +
                    "return wo;\n" +
                "}\n");
        ret.append("</script>");*/

        ret.append("<form id=\"" + getResourceBase().getId() + "/natural\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        ret.append("action=\"" + url + "\" method=\"POST\"");
        ret.append("onSubmit=\"submitForm('" + getResourceBase().getId() + "/natural'); return false;\" method=\"POST\">");
        ret.append("<fieldset>Natural Language Query Examples");
        ret.append("<PRE>");
        ret.append("1. [User] con [Activo]=true, [Primer Apellido]\n");
        ret.append("2. [Activo], [Contraseña] de [User] con [Usuario]=\"admin\"\n");
        ret.append("3. [Creación], [Correo Electrónico] de [User] con [Usuario] = \"admin\"\n");
        ret.append("4. todo de [User] con [Creación] < \"2009-04-02T13:36:21.409\"\n");
        ret.append("5. todo de [Página Web] con [Usuario Creador] con [Usuario] = \"admin\"\n");
        ret.append("*Type a word and use CTRL + SPACE to show suggestions, ESC to hide suggestions.");
        ret.append("</PRE>");
        ret.append("Natural Language Query:<BR>");
        ret.append("<textarea id=\"naturalQuery\" name=\"naturalQuery\" rows=5 cols=70>");
        ret.append(query);
        ret.append("</textarea><div id=\"results\"></div>");
        ret.append("</fieldset>");
        ret.append("<fieldset>");
        ret.append("<button dojoType='dijit.form.Button' type=\"submit\">Enviar</button>\n");
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
                            ret.append("<font color='red'>No se encontraron coincidencias</font>");
                            ret.append("</tr>");
                            ret.append("</thead>");
                        }
                        ret.append("</tbody>");
                        ret.append("</table>");
                        ret.append("</fieldset>");
                        ret.append("<fieldset>");
                        ret.append("<p aling=\"center\">Execution Time:" + (System.currentTimeMillis() - time) + "ms." + "</p>");
                        ret.append("</fieldset>");
                    } finally {
                        qexec.close();
                    }
                } catch (Exception e) {
                    if (tr.getErrCode() != 0) {
                        ret.append("<script language=\"javascript\" type=\"text/javascript\">alert('La consulta no pudo ser traducida.');</script>");
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
}