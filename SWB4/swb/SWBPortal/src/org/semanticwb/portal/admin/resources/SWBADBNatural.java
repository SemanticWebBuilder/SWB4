/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.nlp.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 *
 * @author hasdai
 */
public class SWBADBNatural extends GenericResource {

    private Translator tr;

    /** Creates a new instance of SWBADBNatural */
    public SWBADBNatural()
    {
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String _query = request.getParameter("queryText");
        String lang = paramRequest.getUser().getLanguage();

        if (_query == null) {
            _query="";
        } else {
            _query=_query.trim();
        }

        //out.println("<script src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/acTextArea.js\"></script>");
        out.println("<script type=\"text/javascript\">" +
                "dojo.require(\"dijit.form.Textarea\");" +
                "dojo.require(\"dijit.form.Form\");" +
                "dojo.require(\"dijit.form.Button\");" +
                "</script>");
        out.println("<script type=\"text/javascript\">" +
                "dojo.addOnLoad(function () {" +
                    "dojo.connect(dojo.byId('queryText'), 'onkeydown', 'queryOnKeyDown');" +
                    "dojo.connect(dojo.byId('queryText'), 'onkeydown', 'queryOnKeyUp');" +
                    "dojo.connect(dojo.byId('queryText'), 'onblur', function () {" +
                        "clearSuggestions();" +
                    "});" +
                "});" +
            "</script>");

        out.println("<script type=\"text/javascript\">" +
                "function clearSuggestions() {" +
                    "if (dojo.byId('results')) {" +
                        "dojo.byId('results').innerHTML = \"\"; " +
                     "}" +
                     "displayed = false;" +
                     "curSelected = 0;" +
                     "dojo.byId('queryText').focus();" +
                "}" +
                //Establece el elemento seleccionado
                "function setSelection(selected) {" +
                    "var word = getCurrentWord('queryText');" +
                    "var valText = dojo.byId('queryText').value;" +
                        "dojo.byId('queryText').value = valText.substring(0, word.startP) +" +
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
                    "var word = getCurrentWord('queryText');" +
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
                    "dojo.byId('queryText').focus();" +
                "}" +
            "}" +
             "</script>");

        out.println("<div class=\"swbform\">");
        out.println("<form dojoType=\"dijit.form.Form\" id=\""+getResourceBase().getId()+"/natsparql\" action=\""+paramRequest.getRenderUrl()+"\" method=\"post\" onsubmit=\"submitForm('"+getResourceBase().getId()+"/natsparql'); return false;\">");
        out.println("<fieldset>");
        out.println("<table border=\"0\" cellspacing=\"2\" cellpadding=\"0\" >");
        out.println("<tr><td >");
        out.println("Natural Language Query Example:");
        out.println("</td></tr>");
        out.println("<tr><td ><PRE >");
        out.println("->10 User con Activo=true, Primer Apellido");
        out.println("->WebSite con Activo=true");
        out.println("*Type a word and use CTRL + SPACE to show suggestions, ESC to hide suggestions.");
        out.println("</PRE></td></tr>");
        out.println("<tr><td class=\"tabla\">");
        out.println("Natural Language Query:");
        out.println("</td></tr>");
        out.println("<tr><td>");
        out.print("<textarea id=\"queryText\" name=\"queryText\" rows=5 cols=70>");
        out.print(_query);
        out.println("</textarea><div id=\"results\"></div>");
        out.println("</td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >"+paramRequest.getLocaleString("send")+"</button>");
        out.println("</fieldset>");

        long time=System.currentTimeMillis();
        try
        {
            if (_query.length() > 0)
            {
                out.println("<fieldset>");
                out.println("<table border=0>");

                Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
                List prefixes = new ArrayList();
                List namespaces = new ArrayList();

                //Create a new word dictionary instance
                Lexicon dict = new Lexicon();
                Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();

                //Traverse the ontology model to fill the dictionary
                while(its.hasNext()) {
                    SemanticClass sc = its.next();

                    if(!prefixes.contains(sc.getPrefix()))
                        prefixes.add(sc.getPrefix());
                    if(!namespaces.contains(sc.getOntClass().getNameSpace()))
                        namespaces.add(sc.getOntClass().getNameSpace());

                    dict.addWord(new Word(sc.getDisplayName(lang),
                            new WordTag("OBJ", sc.getPrefix() + ":" + sc.getName())));

                    Iterator<SemanticProperty> ip = sc.listProperties();

                    while(ip.hasNext()) {
                        SemanticProperty prop = ip.next();

                        if(!prefixes.contains(prop.getPrefix()))
                            prefixes.add(prop.getPrefix());
                        if(!namespaces.contains(prop.getRDFProperty().getNameSpace()))
                            namespaces.add(prop.getRDFProperty().getNameSpace());

                        dict.addWord(new Word(prop.getDisplayName(lang),
                                new WordTag("PRO", prop.getPropId())));
                    }
                }

                //Get the SPARQL query prefixes
                String prex = "";
                prex += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
                prex += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
                for(int i = 0; i < prefixes.size(); i++) {
                    prex = prex + "PREFIX " + prefixes.get(i) + ": " + "<" + namespaces.get(i) + ">\n";
                }

                //Translate the Natural Language Sentence to SPARQL
                //out.println(dict.toString());
                tr = new Translator(dict);
                String queryString = prex + "\n" + tr.translateSentence(_query);

                System.out.println(queryString);
                out.println("<textarea cols=80>" + queryString + "</textarea>");
                Query query = QueryFactory.create(queryString);
                query.serialize(); //new IndentedWriter(response.getOutputStream(),true)) ;
                // Create a single execution of this query, apply to a model
                // which is wrapped up as a Dataset
                QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
                // Or QueryExecutionFactory.create(queryString, model) ;
                
                try {
                    // Assumption: it's a SELECT query.
                    ResultSet rs = qexec.execSelect() ;
                    int col = rs.getResultVars().size();
                    out.println("<thead>");
                    out.println("<tr>");

                    Iterator<String> itcols=rs.getResultVars().iterator();
                    while(itcols.hasNext())
                    {
                        out.println("<th>");
                        out.println(itcols.next());
                        out.println("</th>");
                    }
                    out.println("</tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                    int ch=0;
                    // The order of results is undefined.
                    for ( ; rs.hasNext() ; )
                    {
                        QuerySolution rb = rs.nextSolution() ;
                        if(ch==0)
                        {
                            ch=1;
                            out.println("<tr bgcolor=\"#EFEDEC\">");
                        }
                        else
                        {
                            ch=0;
                            out.println("<tr>");
                        }

                        Iterator<String> it=rs.getResultVars().iterator();

                        boolean first = true;
                        while(it.hasNext())
                        {
                            String name=it.next();
                            RDFNode x = rb.get(name) ;
                            out.println("<td >");
                            SemanticObject so = SemanticObject.createSemanticObject(x.toString());

                            if(so!=null) {
                                System.out.println(so.getDisplayName());
                                if(first) {
                                    out.println("<a href=\"#\" onclick=\"parent.addNewTab('" + so.getURI()
                                            + "', '" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp', '"
                                            + so.getDisplayName() + "');\">" + so.getDisplayName() + "</a>");
                                    first = false;
                                } else {
                                    out.println(so.getDisplayName());
                                }
                            } else {
                                if (x!=null) {
                                    out.println(x);
                                } else {
                                  out.println(" - ");
                                }
                            }
                            //out.println(x!=null?SemanticObject.createSemanticObject(x.toString()).getDisplayName(lang):" - ");
                            out.println("</td>");
                        }
                        out.println("</tr>");
                    }
                    out.println("</tbody>");
                }
                finally
                {
                    // QueryExecution objects should be closed to free any system resources

                    qexec.close() ;
                }
                out.println("</table>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<p aling=\"center\">Execution Time:"+(System.currentTimeMillis()-time)+"</p>");
                out.println("</fieldset>");
            }
        }catch(Exception e) {
            out.println("<fieldset>");
            out.println("Error: <BR>");
            if(tr.getErrCode() != 0) {
                out.println("La consulta no pudo ser traducida.");
            } else {
                out.println("La consulta no pudo ser procesada por Jena.");
            }

            out.println("</fieldset>");
        }
        out.println("</form>");
        out.println("</div>");
    }
}