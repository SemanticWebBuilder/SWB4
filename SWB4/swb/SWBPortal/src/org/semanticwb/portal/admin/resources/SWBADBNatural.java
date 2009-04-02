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

        ret.append("<script type=\"text/javascript\">" +
                "dojo.require(\"dojo.parser\");" +
                "dojo.require(\"dijit.layout.ContentPane\");\n"+
                "dojo.require(\"dijit.form.Form\");" +
                "dojo.require(\"dijit.form.Button\");" +
                "</script>");
        ret.append("<script src=\"/swb/swbadmin/js/acTextArea.js\"></script>");
        ret.append("<script type=\"text/javascript\">" +
                "dojo.addOnLoad(function () {" +
                    "dojo.connect(dojo.byId('naturalQuery'), 'onkeydown', 'queryOnKeyDown');" +
                    "dojo.connect(dojo.byId('naturalQuery'), 'onkeyup', 'queryOnKeyUp');" +
//                    "dojo.connect(dojo.byId('naturalQuery'), 'onkeypress', 'queryOnKeyPress');" +
                    "dojo.connect(dojo.byId('naturalQuery'), 'onblur', function () {" +
                        "clearSuggestions();" +
                    "});" +
                "});" +
                "var source =\"" + SWBPlatform.getContextPath() + "/swbadmin/jsp/acTextAreaStore.jsp\";" +
                "var lang =\"" + paramRequest.getUser().getLanguage() + "\";" +
            "</script>");

        /*out.println("<script type=\"text/javascript\">" +
                "function clearSuggestions() {" +
                    "if (dojo.byId('results')) {" +
                        "dojo.byId('results').innerHTML = \"\"; " +
                     "}" +
                     "displayed = false;" +
                     "curSelected = 0;" +
                     "dojo.byId('naturalQuery').focus();" +
                "}" +
                //Establece el elemento seleccionado
                "function setSelection(selected) {" +
                    "var word = getCurrentWord('naturalQuery');" +
                    "var valText = dojo.byId('naturalQuery').value;" +
                        "dojo.byId('naturalQuery').value = valText.substring(0, word.startP) +" +
                        "dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\") +" +
                        "valText.substring(word.endP, valText.length);" +
                "}" +
                //Reemplaza el texto del textArea con el seleccionado de la lista
                "function replaceText(elm, start, end, txt) {" +
                    "var valText = elm.value;" +
                    "elm.value = valText.substring(0, start) + txt + valText.substring(end, valText.length);" +
                "}" +
                //Resalta una opción de la lista de sugerencias
                "function highLightSelection(id, high) {" +
                    "var ele = dojo.byId('id' + id);" +
                    "if (high) {" +
                            "dojo.style(ele, {" +
                            "\"background\":\"LightBlue\"" +
                        "});" +
                    "} else {" +
                        "dojo.style(ele, {" +
                        "\"background\":\"white\"" +
                        "});" +
                    "}" +
                 "}" +
                 //Invoca una página web via AJAX
                 "function getHtml (url, tagid) {" +
                    "dojo.xhrGet ({" +
                        "url: url," +
                        "load: function(response, ioArgs) {" +
                            "var tag = dojo.byId(tagid);" +
                            "if (tag) {" +
                                "tag.innerHTML = response;" +
                            "} else {" +
                                "alert('No existe ningún elemento con id ' + tagid);" +
                            "}" +
                            "highLightSelection(0, true);" +
                            "return response;" +
                        "}," +
                        "error: function(response, ioArgs) {" +
                            "if (dojo.byId(tagid)) {" +
                                "dojo.byId(tagid).innerHTML = "+
                            "\"<p style='background:white;color:red;'>" +
                            "Cannot load suggestions, try again<br></p>\";" +
                        "} else {" +
                            "alert('No existe ningún elemento con id ' + tagid);" +
                        "}" +
                        "return response;" +
                    "}," +
                    "handleAs: 'text'," +
                    "timeout: 5000" +
                "});" + "}" +
                "function getSuggestions(clear) {" +
                    "var word = getCurrentWord('naturalQuery');" +
                    "if (clear) {" +
                        "clearSuggestions();" +
                    "}" +
                    "if(word.word == '') {" +
                        "return;" +
                    "}" +
                    "if (dojo.byId('results') && word.word != '') {" +
                        "dojo.byId('results').innerHTML = '<font color=\"Red\">Loading...</font>';" +
                    "}" +
                    "getHtml(\"" + SWBPlatform.getContextPath() +
                        "/swbadmin/jsp/acTextAreaStore.jsp?word=\" + word.word + \"&lang=" + lang +
                        "\", \"results\");" +
                    "displayed = true;" +
                    "}" +
                    "function getCaretPos(elm) {" +
                        "var pos;" +
                        //Si es IExplorer (selectionStart y selectionEnd no definidas)
                        "if (dojo.doc.selection) {" +
                            "var Sel = document.selection.createRange();" +
                            "var SelLength = document.selection.createRange().text.length;" +
                            "Sel.moveStart ('character', -dojo.byId(elm).value.length);" +
                            "pos = Sel.text.length - SelLength;" +
                        //Si es Gecko
                        "} else if (typeof dojo.byId(elm).selectionStart != undefined) {" +
                            "pos = dojo.byId(elm).selectionStart;" +
                        "}" +
                        "return pos;" +
                    "}" +
                    "function getCurrentWord(elm) {" +
                        "var cPos = getCaretPos(elm);" +
                        "var txt = dojo.byId(elm).value;" +
                        "var prevBlank = -1;" +
                        "var aftBlank = -1;" +
                        "var found = false;" +
                        "var wd = null;" +
                        "var wo = \"undefined\";" +
                        "var delimiters = '\\n\\t ';" +
                        //Seleccionar la palabra actual en el elemento
                        "if (txt != '') {" +
                            //encontrar el primer espacio en blanco a la izquierda del cursor
                            "for (var i = 0; i < txt.length; i++) {" +
                                "if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {" +
                                "prevBlank = i;" +
                            "}" +
                        "}" +
                        //encontrar el primer espacio en blanco a la derecha del cursor
                        "for (i = cPos; i < txt.length && !found; i++) {" +
                            "if (delimiters.indexOf(txt.charAt(i)) != -1) {" +
                                "aftBlank = i;" +
                                "found = true;" +
                            "}" +
                        "}" +
                        "if (prevBlank == -1) {" +
                            "if (aftBlank == -1) {" +
                                "wd = txt.substring(0, txt.length);" +
                                "wo = {word: wd, startP: 0, endP: txt.length};" +
                            "} else {" +
                                "wd = txt.substring(0, aftBlank);" +
                                "wo = {word: wd, startP: 0, endP: aftBlank};" +
                            "}" +
                        "} else if (aftBlank == -1) {" +
                            "wd = txt.substring(prevBlank + 1, txt.length);" +
                            "wo = {word: wd, startP: prevBlank + 1, endP: txt.length};" +
                        "} else {" +
                            "wd = txt.substring(prevBlank + 1, aftBlank);" +
                            "wo = {word: wd, startP: prevBlank + 1, endP: aftBlank};" +
                        "}" +
                    "}" +
                    "return wo;" +
                "}" +
                "function queryOnKeyUp (evt) {" +
                    "if (evt.target.value == '') {" +
                        "clearSuggestions();" +
                        "return;" +
                "}" +
                //Flecha ARRIBA
                "if (displayed && evt.keyCode == dojo.keys.UP_ARROW) {" +
                    "dojo.query('.resultEntry').style('background', 'white');" +
                    "curSelected--;" +
                    "if (curSelected < 0) {" +
                        "curSelected = 0;" +
                    "}" +
                    "highLightSelection(curSelected, true);" +
                    "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;" +
                    "dojo.stopEvent(evt);" +
                    //Flecha ABAJO
                "} else if (displayed && evt.keyCode == dojo.keys.DOWN_ARROW) {" +
                    "dojo.query('.resultEntry').style('background', 'white');" +
                    "curSelected++;" +
                    "if (curSelected > dojo.byId('resultlist').childNodes.length - 2) {" +
                        "curSelected = dojo.byId('resultlist').childNodes.length - 2;" +
                    "}" +
                    "highLightSelection(curSelected, true);" +
                    "dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id'+curSelected)).t;" +
                    "dojo.stopEvent(evt);" +
                "}" +
                //Tecla ENTER
                "else if (displayed && evt.keyCode == dojo.keys.ENTER) {" +
                    "setSelection(curSelected);" +
                    "clearSuggestions();" +
                    "dojo.stopEvent(evt);" +
                "}" +
            "}" +
            "function queryOnKeyDown (evt) {" +
                "if (evt.target.value == '') {" +
                    "clearSuggestions();" +
                    "return;" +
                "}" +
                //Tecla CTRL+SPACE
                "if (evt.ctrlKey && evt.keyCode == dojo.keys.SPACE) {" +
                    "getSuggestions(true);" +
                    "dojo.stopEvent(evt);" +
                //Tecla ESCAPE
                "} else if (displayed && evt.keyCode == dojo.keys.ESCAPE) {" +
                    "clearSuggestions();" +
                    "dojo.stopEvent(evt);" +
                //Cualquier otra tecla
                "} else if (displayed && (evt.keyCode != dojo.keys.CTRL && " +
                    "evt.keyCode != dojo.keys.UP_ARROW && " +
                    "evt.keyCode != dojo.keys.DOWN_ARROW && evt.keyCode != dojo.keys.ESC &&" +
                    "evt.keyCode != dojo.keys.ENTER)) {" +
                    "getSuggestions(false);" +
                    "dojo.byId('naturalQuery').focus();" +
                "}" +
            "}" +
             "</script>\n");*/

        ret.append("<form id=\"" + getResourceBase().getId() + "/natural\" dojoType=\"dijit.form.Form\" class=\"swbform\" ");
        ret.append("action=\"" + url + "\" method=\"POST\"");
        ret.append("onSubmit=\"submitForm('" + getResourceBase().getId() + "/natural'); return false;\" method=\"POST\">");
        ret.append("<fieldset>Natural Language Query Examples");
        ret.append("<PRE>");
        ret.append("1. [User] con [Activo]=true, [Primer Apellido]\n");
        ret.append("2. [Activo], [Contraseña] de [User] con [Usuario]=\"admin\"\n");
        ret.append("3. [Creación], [Correo Electrónico] de [User] con [Usuario] = \"admin\"\n");
        ret.append("4. todo de [User] con [Creación] < \"2009-04-02T13:36:21.409\"");
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
                ret.append("<fieldset>");
                ret.append("<textarea rows=5 cols=70>");
                ret.append(request.getParameter("sparqlQuery"));
                ret.append("</textarea>");
                ret.append("</fieldset>");

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
                    } else {
                        ret.append("<script language=\"javascript\" type=\"text/javascript\">alert('La consulta no pudo ser procesada por Jena.');</script>");
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