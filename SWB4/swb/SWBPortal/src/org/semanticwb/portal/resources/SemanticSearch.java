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

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
//import org.semanticwb.nlp.translation.SWBSparqlTranslator;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.nlp.SWBDictionary;
import org.semanticwb.nlp.SWBLocaleLexicon;
import org.semanticwb.nlp.spell.SWBSpellChecker;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.admin.resources.SWBAListRelatedObjects;
import org.semanticwb.portal.api.GenericAdmResource;

// TODO: Auto-generated Javadoc
/**
 * The Class SemanticSearch.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SemanticSearch extends GenericAdmResource {
//
//    /** The log. */
//    private Logger log = SWBUtils.getLogger(SWBAListRelatedObjects.class);
//    
//    /** The tr. */
//    private SWBSparqlTranslator tr = null;
//    
//    /** The lex. */
//    private SWBDictionary lex = null;
//    
//    /** The lang. */
//    private String lang = "x-x";
//
//    /* (non-Javadoc)
//     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
//     */
//    @Override
//    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        String mode = paramRequest.getMode();
//        if (mode.equals("SUGGEST")) {
//            doSuggest(request, response, paramRequest);
//        } else if (mode.equals("SHOWRES")) {
//            doShowResults(request, response, paramRequest);
//        } else {
//            super.processRequest(request, response, paramRequest);
//        }
//    }
//
//    /**
//     * Do view.
//     * 
//     * @param request the request
//     * @param response the response
//     * @param paramRequest the param request
//     * @throws IOException Signals that an I/O exception has occurred.
//     * @throws SWBResourceException the sWB resource exception
//     */
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        PrintWriter out = response.getWriter();
//        StringBuffer sbf = new StringBuffer();
//        SWBResourceURL rUrl = paramRequest.getRenderUrl();
//        String query = request.getParameter(createId("naturalQuery"));
//        User user = paramRequest.getUser();
//
//        response.setContentType("text/html; charset=ISO-8859-1");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Pragma", "no-cache");
//
//        //Get user language if any and create lexicon and translator acordingly
//        if (user != null) {
//            if (!lang.equals(paramRequest.getUser().getLanguage())) {
//                lang = paramRequest.getUser().getLanguage();
//                SWBLocaleLexicon l = new SWBLocaleLexicon(lang, SWBDictionary.getLanguageName(lang));
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
//        SWBSpellChecker speller = new SWBSpellChecker(lex.getSpellDictPath());
//
//        //Assert query string
//        query = (query == null?"":query.trim());
//
//        rUrl.setMode("SUGGEST");
//        rUrl.setParameter("prefixes", "swb,swbc");
//        rUrl.setCallMethod(rUrl.Call_DIRECT);
//
//        sbf.append("      <script type=\"text/javascript\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad: true, isDebug: false\"></script>\n" +
//                   "      <script type=\"text/javascript\">\n" +
//                   "        dojo.require(\"dijit.form.Form\");\n" +
//                   "        dojo.require(\"dijit.form.Button\");\n" +
//                   "        dojo.require(\"dijit.form.TextBox\");\n" +
//                   "      </script>\n" +
//                   "      <script type=\"text/javascript\">\n" +
//                   "        dojo.addOnLoad(function () {\n" +
//                   "          dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onkeydown', 'queryOnKeyDown');\n" +
//                   "          dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onkeyup', 'queryOnKeyUp');\n" +
//                   "          dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onblur', function () {\n" +
//                   "            clearSuggestions();\n" +
//                   "          });\n" +
//                   "        });\n" +
//                   "        var source =\"" + rUrl + "\";\n" +
//                   "        var lang =\"" + lang + "\";\n" +
//                   "        var displayed;\n" +
//                   "        var pdisplayed;\n" +
//                   "      </script>\n" +
//                   "      <script type=\"text/javascript\" charset=\"utf-8\" src=\"" +
//                            SWBPlatform.getContextPath() + "/swbadmin/js/swb_admin.js\" ></script>\n\n");
//
//        /**
//         * Clears the suggestions list and gives focus to the textarea.
//         */
//        sbf.append("      <script type=\"text/javascript\">\n" +
//                   "        function clearSuggestions() {\n" +
//                   "          if (dojo.byId('" + createId("busca-ayuda-ok") + "')) {\n" +
//                   "            dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML = \"\";\n" +
//                   "          }\n" +
//                   "          displayed = false;\n" +
//                   "          curSelected = 0;\n" +
//                   "          dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
//                   "        }\n\n" +
//                   "        function queryOnKeyDown (evt) {\n" +
//                   "          var wd = getCurrentWord('" + createId("naturalQuery") + "');\n" +
//                   "          if (evt.target.value == '' || wd.word.length < 3) {\n" +
//                   "            clearSuggestions();\n" +
//                   "            return;\n" +
//                   "          }\n" +
//                //CTRL+SHIFT+SPACE
//                   "          if (evt.ctrlKey && evt.shiftKey && evt.keyCode == dojo.keys.SPACE) {\n" +
//                   "            getSuggestions(wd, source, true, false);\n" +
//                   "            dojo.stopEvent(evt);\n" +
//                   "          } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
//                   "            setSelection(curSelected, (wd.word == \"" + paramRequest.getLocaleString("with").toLowerCase() + "\")?\"" + paramRequest.getLocaleString("with").toLowerCase() + " \":\"\");\n" +
//                   "            clearSuggestions();\n" +
//                   "            pdisplayed = false;\n" +
//                "        dojo.stopEvent(evt);\n" +
//                "      }\n" +
//                "  }\n\n" +
//                "  function queryOnKeyUp (evt) {\n" +
//                "    var wd = getCurrentWord('" + createId("naturalQuery") + "');\n" +
//                "    if (evt.target.value == '' || wd.word.length < 3) {\n" +
//                "      clearSuggestions();\n" +
//                "        return;\n" +
//                "      }\n" +
//                "    if((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
//                "    } else if (!displayed && !pdisplayed && wd.word == \"" + paramRequest.getLocaleString("with").toLowerCase() + "\") {\n" +
//                "      var pwd = getPreviousName(wd);\n" +
//                "      if (pwd != \"undefined\") {\n" +
//                "        getSuggestions(getPreviousName(wd), source, true, true);\n" +
//                "          pdisplayed = true;\n" +
//                "      }\n" +
//                "        dojo.stopEvent(evt);\n" +
//                "    } else if (!displayed && pdisplayed && wd.word != \"" + paramRequest.getLocaleString("with").toLowerCase() + "\") {\n" +
//                "      clearSuggestions();\n" +
//                "      pdisplayed = false;\n" +
//                "    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {\n" +
//                "      clearSuggestions();\n" +
//                "      pdisplayed = false;\n" +
//                "    }" +
//                //UP_ARROW
//                "    else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {\n" +
//                "      dojo.query('.resultEntry').style('background', 'white');\n" +
//                "      curSelected--;\n" +
//                "      if (curSelected < 0) {\n" +
//                "        curSelected = 0;\n" +
//                "      }" +
//                //                   "      console.log(curSelected);\n" +
//                "      highLightSelection(curSelected, true);\n" +
//                "      dojo.byId('" + createId("resultlist") + "').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
//                "      dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
//                "      dojo.stopEvent(evt);\n" +
//                //DOWN_ARROW
//                "    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {\n" +
//                "      dojo.query('.resultEntry').style('background', 'white');\n" +
//                "      curSelected++;\n" +
//                "      if (curSelected > dojo.byId('" + createId("resultlist") + "').childNodes.length - 1) {\n" +
//                "        curSelected = dojo.byId('" + createId("resultlist") + "').childNodes.length - 1;\n" +
//                "      }\n" +
//                //                   "      console.log(curSelected);\n" +
//                "      highLightSelection(curSelected, true);\n" +
//                "      dojo.byId('" + createId("resultlist") + "').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
//                "      dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
//                "      dojo.stopEvent(evt);\n" +
//                "    }\n" +
//                "}\n\n");
//
//        /**
//         * Gets the word at current cursor position in a textarea.
//         * @param elm textarea to extract word from.
//         */
//        sbf.append("  function getCurrentWord(elm) {\n" +
//                "    var cPos = getCaretPos(elm);\n" +
//                "    var txt = dojo.byId(elm).value;\n" +
//                "    var prevBlank = -1;\n" +
//                "    var aftBlank = -1;\n" +
//                "    var found = false;\n" +
//                "    var wd = null;\n" +
//                "    var wo = \"undefined\";\n" +
//                "    var delimiters = \"\\n\\t \";\n" +
//                "    if (txt != '') {\n" +
//                "      for (var i = 0; i < txt.length; i++) {\n" +
//                "        if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {\n" +
//                "          prevBlank = i;\n" +
//                "        }\n" +
//                "      }\n" +
//                "      for (i = cPos; i < txt.length && !found; i++) {\n" +
//                "        if (delimiters.indexOf(txt.charAt(i)) != -1) {\n" +
//                "          aftBlank = i;\n" +
//                "          found = true;\n" +
//                "        }\n" +
//                "      }\n" +
//                "      if (prevBlank == -1) {\n" +
//                "        if (aftBlank == -1) {\n" +
//                "          wd = txt;\n" +
//                "          wo = {\n" +
//                "            word: wd,\n" +
//                "            startP: 0,\n" +
//                "            endP: txt.length\n" +
//                "          };\n" +
//                "        } else {\n" +
//                "          wd = txt.substring(0, aftBlank);\n" +
//                "          wo = {\n" +
//                "            word: wd,\n" +
//                "            startP: 0,\n" +
//                "            endP: aftBlank\n" +
//                "          };\n" +
//                "        }\n" +
//                "      } else if (aftBlank == -1) {\n" +
//                "        wd = txt.substring(prevBlank + 1, txt.length);\n" +
//                "        wo = {\n" +
//                "          word: wd,\n" +
//                "          startP: prevBlank + 1,\n" +
//                "          endP: txt.length\n" +
//                "        };\n" +
//                "      } else {\n" +
//                "        wd = txt.substring(prevBlank + 1, aftBlank);\n" +
//                "        wo = {\n" +
//                "          word: wd,\n" +
//                "          startP: prevBlank + 1,\n" +
//                "          endP: aftBlank\n" +
//                "        };\n" +
//                "      }\n" +
//                "    }\n" +
//                "    return wo;\n" +
//                "  }\n\n");
//
//        /**
//         * Gets cursor current position in a textarea.
//         * @param elm textarea to calculate caret position from.
//         */
//        sbf.append("  function getCaretPos(elm) {\n" +
//                "    var pos;\n" +
//                "    if (dojo.doc.selection) {\n" +
//                "      var Sel = document.selection.createRange();\n" +
//                "      var SelLength = document.selection.createRange().text.length;\n" +
//                "      Sel.moveStart ('character', -dojo.byId(elm).value.length);\n" +
//                "      pos = Sel.text.length - SelLength;\n" +
//                "    } else if (typeof dojo.byId(elm).selectionStart != undefined) {\n" +
//                "      pos = dojo.byId(elm).selectionStart;\n" +
//                "    }\n" +
//                "    return pos;\n" +
//                "  }\n\n");
//
//        /**
//         * Creates a suggestion list based on word at current cursor position.
//         * @param word word at cursor position
//         * @param src rUrl of dataStore
//         * @param clear wheter or not to clear previous list
//         * @param props wheter or not to get properties of the current word as SemanticClass
//         */
//        sbf.append("  function getSuggestions(word, src, clear, props) {\n" +
//                "    if (clear) {\n" +
//                "      clearSuggestions();\n" +
//                "    }\n" +
//                "    if(word.word == '') {\n" +
//                "      return;\n" +
//                "    }\n" +
//                "    if (dojo.byId('" + createId("busca-ayuda-ok") + "') && word.word != '') {\n" +
//                "      dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML = '<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\" width=\"20\" height=\"20\"/>" + paramRequest.getLocaleString("loading") + "...';\n" +
//                "    }\n" +
//                "    getHtml(src + \"?word=\" + word.word + \"&lang=\" + lang + \"&props=\" + props, '" + createId("busca-ayuda-ok") + "');\n" +
//                "    displayed = true;\n" +
//                "    highLightSelection(0, true);\n" +
//                "  }\n\n");
//
//        /**
//         * Highlights an option in the suggestions list.
//         * @param id identifier of the list item to highlight
//         * @param high toggles highlight color.
//         */
//        sbf.append("  function highLightSelection(id, high) {\n" +
//                "    var ele = dojo.byId('id' + id);\n" +
//                "    console.log('buscando id' + id);\n" +
//                "    if (high) {\n" +
//                "      dojo.style(ele, {\n" +
//                "        \"background\":\"LightBlue\"\n" +
//                "      });\n" +
//                "    } else {\n" +
//                "      dojo.style(ele, {\n" +
//                "        \"background\":\"white\"\n" +
//                "      });\n" +
//                "    }\n" +
//                "  }\n\n");
//
//        sbf.append("  function getPreviousName (word) {\n" +
//                "    var pName = \"\";\n" +
//                "    var prevBrk = -1;\n" +
//                "    var firstBrk = -1;\n" +
//                "    var txt = dojo.byId('" + createId("naturalQuery") + "').value;\n" +
//                "    var cPos = word.startP;\n" +
//                "    var wd = null;\n" +
//                "    var wo = \"undefined\";\n" +
//                "    var found = false;\n" +
//                "    for (var i = cPos; i >= 0 && !found; i--) {\n" +
//                "      if (txt.charAt(i) == ']') {\n" +
//                "        prevBrk = i;\n" +
//                "        found = true;\n" +
//                "      }\n" +
//                "    }\n" +
//                "    found = false;\n" +
//                "    for (i = prevBrk; i > 0 && !found; i--) {\n" +
//                "      if (txt.charAt(i) == '[') {\n" +
//                "        firstBrk = i;\n" +
//                "        found = true;\n" +
//                "      }\n" +
//                "    }\n" +
//                "    if (prevBrk == -1) {\n" +
//                "      return wo;\n" +
//                "    }\n" +
//                "    firstBrk++;\n" +
//                "    wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);\n" +
//                "    wo = {\n" +
//                "      word: wd,\n" +
//                "      startP: firstBrk,\n" +
//                "      endP: prevBrk\n" +
//                "    };\n" +
//                "    return wo;\n" +
//                "  }\n\n");
//        /**
//         * Replaces the current word in the textarea with the selected word from the
//         * suggestions list.
//         * @param selected index of current selected item
//         * @param prep
//         */
//        sbf.append("  function setSelection(selected, prep) {\n" +
//                "    var word = getCurrentWord('" + createId("naturalQuery") + "');\n" +
//                "    var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\");\n" +
//                "    newText = prep + \"[\" + newText + \"]\";\n" +
//                "    var valText = dojo.byId('" + createId("naturalQuery") + "').value;\n" +
//                "    dojo.byId('" + createId("naturalQuery") + "').value = valText.substring(0, word.startP) +\n" +
//                "    newText + valText.substring(word.endP, valText.length);\n" +
//                "  }\n\n");
//
//        sbf.append("  function submitParams(url) {\n" +
//                "    var loc = url+'&" + createId("naturalQuery") + "=' + encodeUrlAccents(dijit.byId('" + createId("naturalQuery") + "').value);" +
//                "    var h_wnd = window.open(loc,'" + paramRequest.getLocaleString("sResults") + "','width=800,height=600,menubar=yes');\n" +
//                "    dojo.byId('" + createId("natural") + "').target=h_wnd;" +
//                "    dojo.byId('" + createId("natural") + "').submit();" +
//                "  }\n\n" +
//                "  function getHtml(url, tagid) {\n" +
//                "    dojo.xhrGet({\n" +
//                "    url: url,\n" +
//                "    load: function(response)\n" +
//                "    {\n" +
//                "      var tag=dojo.byId(tagid);\n" +
//                "      if(tag) {\n" +
//                "        var pan=dijit.byId(tagid);\n" +
//                "        if(pan && pan.attr) {\n" +
//                "          pan.attr('content',response);\n" +
//                "        } else {\n" +
//                "          tag.innerHTML = response;\n" +
//                "        }\n" +
//                "      } else {\n" +
//                "        alert(\"No existe ningún elemento con id \" + tagid);\n" +
//                "      }\n" +
//                "      return response;\n" +
//                "    },\n" +
//                "    error: function(response) {\n" +
//                "      if(dojo.byId(tagid)) {\n" +
//                "        dojo.byId(tagid).innerHTML = \"<p>Ocurrió un error con respuesta:<br />\" + response + \"</p>\";\n" +
//                "      } else {\n" +
//                "        alert(\"No existe ningún elemento con id \" + tagid);\n" +
//                "      }\n" +
//                "      return response;\n" +
//                "    },\n" +
//                "    handleAs: \"text\"\n" +
//                "  });\n" +
//                "}\n" +
//                "function openMap(loc, title, args) {\n" +
//                "  window.open(loc, '', args);" +
//                "}\n" +
//                "</script>");
//
//        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
//            String url = getResourceBase().getAttribute("destUrl");
//
//            sbf.append("<form id=\"" + createId("natural") + "\" " +
//                "action=\"" + url + "\" method=\"post\">\n" +
//                "  <input type=\"text\" id=\"" + createId("naturalQuery") + "\" " +
//                "name=\"" + createId("naturalQuery") + "\" class=\"txt-busca\" value=\"" + query + "\" />\n" +
//                "  <input type=\"submit\" value=\"Buscar\" class=\"btn-busca\">\n" +
//                "</form>\n" +
//                "  <div id=\"" + createId("busca-ayuda-ok") + "\"></div>\n");
//        } else {
//            doShowResults(request, response, paramRequest);
//        }
//        out.print(sbf.toString());
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
//        SortedSet objOptions = new TreeSet();
//        SortedSet proOptions = new TreeSet();
//        String word = request.getParameter("word");
//        String lan = request.getParameter("lang");
//        boolean props = Boolean.parseBoolean(request.getParameter("props"));
//        String tempcDn = "";
//        boolean lPar = false;
//        boolean rPar = false;
//        int idCounter = 0;
//        //SWBLexicon lex = new SWBLexicon(lan);
//        StringBuffer sbf = new StringBuffer();
//
//        String px = request.getParameter("prefixes");
//        if (px == null) {
//            px = "";
//        }
//
//        ArrayList<String> lprex = null;
//
//        if (!px.equals("")) {
//            lprex = new ArrayList(Arrays.asList(px.trim().split(",")));
//        }
//
//        response.setContentType("text/html; charset=utf-8");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Pragma", "no-cache");
//
//        if (lan == null || lan.equals("")) {
//            lan = "es";
//        }
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
//        if (!props) {
//            Iterator<SemanticClass> cit = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
//
//            while (cit.hasNext()) {
//                SemanticClass tempc = cit.next();
//                tempcDn = tempc.getDisplayName(lan);
//
//                if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
//                    if (lprex.contains(tempc.getPrefix())) {
//                        objOptions.add(tempcDn);
//                    }
//                }
//
//                Iterator<SemanticProperty> sit = tempc.listProperties();
//                while (sit.hasNext()) {
//                    SemanticProperty tempp = sit.next();
//                    tempcDn = tempp.getDisplayName(lan);
//
//                    if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
//                        if (lprex.contains(tempc.getPrefix())) {
//                            proOptions.add(tempcDn);
//                        }
//                    }
//                }
//            }
//
//            if (proOptions.size() != 0 || objOptions.size() != 0) {
//                idCounter = 0;
//                int index;
//                Iterator<String> rit = objOptions.iterator();
//
//                sbf.append("<ul id=\"" + createId("resultlist") + "\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
//                        "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
//                        "200px;width:300px;border:1px solid #a0a0ff;\">\n");
//                while (rit.hasNext()) {
//                    String tempi = (String) rit.next();
//                    index = tempi.toLowerCase().indexOf(word.toLowerCase());
//
//                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
//                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
//                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
//                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
//                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
//                            "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;pdisplayed=false\">" + (lPar ? "(" : "") +
//                            "<font color=\"red\">" + tempi + "</font>" +
//                            (lPar ? ")" : "") + "</li>\n");
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
//                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
//                            "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;pdisplayed=false;\">" + (lPar ? "(" : "") +
//                            "<font color=\"blue\">" + tempi + "</font>" +
//                            (lPar ? ")" : "") + "</li>\n");
//                    idCounter++;
//                }
//                sbf.append("</ul>\n");
//            }
//        } else {
//            String tag = lex.getLexicon(lang).getWord(word, true).getTags().get(0).getTagInfoParam(SWBLocaleLexicon.PARAM_ID);
//            //String tag = lex.getObjWordTag(word.toLowerCase()).getObjId();
//
//            sbf.append("<ul id=\"" + createId("resultlist") + "\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
//                    "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
//                    "200px;width:300px;border:1px solid #a0a0ff;\">\n");
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
//                            "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
//                            "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;\">" + (lPar ? "(" : "") +
//                            "<font color=\"red\">" + t.getDisplayName(lan) + "</font>" +
//                            (lPar ? ")" : "") + "</li>\n");
//                    idCounter++;
//                }
//            } else {
//                tag = lex.getLexicon(lang).getWord(word, false).getSelectedTag().getTagInfoParam(SWBLocaleLexicon.PARAM_URI);
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
//                                "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
//                                "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;\">" + (lPar ? "(" : "") +
//                                "<font color=\"red\">" + t.getDisplayName(lan) + "</font>" +
//                                (lPar ? ")" : "") + "</li>\n");
//                        idCounter++;
//                    }
//                }
//            }
//            sbf.append("</ul>\n");
//        }
//        out.println(sbf.toString());
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
//        PrintWriter out = response.getWriter();
//        StringBuffer sbf = new StringBuffer();
//        SWBResourceURL rUrl = paramRequest.getRenderUrl();
//        String query = request.getParameter(createId("naturalQuery"));
//        User user = paramRequest.getUser();
//        SemanticProperty so_lat = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/emexcatalog.owl#latitude");
//        SemanticProperty so_long = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/emexcatalog.owl#longitude");
//        SemanticProperty so_home = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/emexcatalog.owl#wpUrl");
//        SemanticProperty so_name = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/emexcatalog.owl#name");
//        SemanticProperty so_address = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/emexcatalog.owl#address");
//        SemanticObject so2;
//
//        //Get user language if any and create lexicon and translator acordingly
//        if (user != null) {
//            if (!lang.equals(paramRequest.getUser().getLanguage())) {
//                lang = paramRequest.getUser().getLanguage();
//                SWBLocaleLexicon l = new SWBLocaleLexicon(lang, SWBDictionary.getLanguageName(lang));
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
//        rUrl.setMode("SUGGEST");
//        rUrl.setCallMethod(rUrl.Call_DIRECT);
//
//        HashMap<String, String> dbNames = new HashMap<String, String>();
//        dbNames.put("Miguel Hidalgo", "Miguel_Hidalgo,_D.F.");
//        dbNames.put("Tamaulipas", "Tamaulipas");
//        dbNames.put("México, D.F.", "México_city");
//        dbNames.put("Veracruz", "Veracruz");
//        dbNames.put("Tamaulipas", "Tamaulipas");
//        dbNames.put("Alvaro Obregón", "Álvaro_Obregón,_D.F.");
//        dbNames.put("Xochimilco", "Xochimilco");
//        dbNames.put("Tlalpan", "Tlalpan");
//        dbNames.put("Altamira", "Altamira,_Tamaulipas");
//        dbNames.put("Aldama", "Aldama,_Tamaulipas");
//        dbNames.put("Abasolo", "");
//        dbNames.put("Burgos", "");
//        dbNames.put("Aguascalientes", "Aguascalientes,_Aguascalientes");
//        dbNames.put("Asientos", "Asientos,_Aguascalientes");
//        dbNames.put("Calvillo", "Calvillo,_Aguascalientes");
//        dbNames.put("Cosío", "Cosío,_Aguascalientes");
//        dbNames.put("Actopan", "Actopan,_Veracruz");
//        dbNames.put("Atoyac", "");
//        dbNames.put("Córdoba", "Córdoba,_Veracruz");
//        dbNames.put("Las minas", "");
//
//        sbf.append("<form id=\"" + createId("natural") + "\" " +
//                "action=\"" + getResourceBase().getAttribute("destUrl") + "\" >\n" +
//                "  <textarea id=\"" + createId("naturalQuery") + "\" rows=5 cols=70 " +
//                "name=\"" + createId("naturalQuery") + "\" class=\"txt-busca\">" + query + "</textarea>\n" +
//                "  <div id=\"" + createId("busca-ayuda-ok") + "\"></div>\n" +
//                "  <input type=\"submit\" value=\"Buscar\" class=\"btn-busca\">\n" +
//                "</form>\n");
//
//        tr = new SWBSparqlTranslator(lex);
//        long time = System.currentTimeMillis();
//        String sparqlQuery = lex.getLexicon(lang).getPrefixString() + "\n" + tr.translateSentence(query, false);
//        //System.out.println("\n+++Tiempo de traducción: " + String.valueOf(System.currentTimeMillis() - time) + "milisegundos");
//        //System.out.println(sparqlQuery);
//        String errCount = Integer.toString(tr.getErrCode());
//
//        if (errCount != null) {
//            if (Integer.parseInt(errCount) == 0) {
//                String patternStr = "\"[a-zA-Z]+\"";
//                String mname = "";
//                String dbName = "";
//
//                // Compile and use regular expression
//                Pattern pattern = Pattern.compile(patternStr);
//                Matcher matcher = pattern.matcher(sparqlQuery);
//                boolean matchFound = matcher.find();
//
//                if (matchFound) {
//                    // Get all groups for this match
//                    for (int i = 0; i <= matcher.groupCount(); i++) {
//                        // Get the group's indices
//                        int groupStart = matcher.start(i);
//                        int groupEnd = matcher.end(i);
//
//                        // groupStr is equivalent to
//                        mname = sparqlQuery.subSequence(groupStart, groupEnd).toString();
//                    }
//                }
//
//                if (dbNames.containsKey(mname.replace("\"", ""))) {
//                    dbName = dbNames.get(mname.replace("\"", ""));
//                }
//
//                try {
//                       // System.out.println("Obteniendo información de la dbpedia");
//                        time = System.currentTimeMillis();
//                        if (!dbName.equals("")) {
//                            String dbPediaQuery =
//                                    "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
//                                    "PREFIX dbprop: <http://dbpedia.org/property/>\n" +
//                                    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"+
//                                    "SELECT ?desc ?lat ?long ?page\n" +
//                                    "WHERE {\n" +
//                                    "<http://dbpedia.org/resource/" + dbName + "> dbprop:abstract ?desc.\n" +
//                                    "FILTER (lang(?desc) = \"" + lang + "\").\n" +
//                                    "<http://dbpedia.org/resource/" + dbName + "> geo:lat ?lat.\n" +
//                                    "<http://dbpedia.org/resource/" + dbName + "> geo:long ?long.\n" +
//                                    "<http://dbpedia.org/resource/" + dbName + "> foaf:page ?page.\n" +
//                                    "}\n";
//                            //System.out.println("<<<<<" + dbPediaQuery);
//                            //Query dbpedia
//                            SemanticModel dbpModel = SWBPlatform.getSemanticMgr().getModel("DBPedia");
//                            QueryExecution dbQexec = dbpModel.sparQLQuery(dbPediaQuery);
//
//                            //Get dbPedia INFO
//                            ResultSet dbrs = dbQexec.execSelect();
//
//                            QuerySolution dbrb = dbrs.nextSolution();
//                            RDFNode desc_node = dbrb.get("desc");
//                            RDFNode lat_node = dbrb.get("lat");
//                            RDFNode long_node = dbrb.get("long");
//                            RDFNode home_node = dbrb.get("page");
//
//                            String mapUrl = getResourceBase().getAttribute("mapUrl") +
//                                    "?lat=" + lat_node.asNode().getLiteral().getLexicalForm() +
//                                    "&long="+ long_node.asNode().getLiteral().getLexicalForm() +
//                                    "&wikiUrl=" + home_node.toString() +
//                                    "&info=" + dbName.replace("\"", "\\\"");
//                            sbf.append("<table cellpadding=10 cellspacing=10>\n" +
//                                    "  <thead>\n" +
//                                    "    <tr>\n" +
//                                    "      <th>" + paramRequest.getLocaleString("infoAbout") + " " +  "<a href=\"#\" " +
//                                           "onclick=\"openMap('" + home_node.toString() + "','','');\">" + dbName + "</a></th>\n" +
//                                    "    </tr>\n" +
//                                    "  </thead>\n" +
//                                    "  <tbody>\n" +
//                                    "    <tr>\n" +
//                                    "      <td>" + desc_node.asNode().getLiteral().getLexicalForm() + "</td>\n" +
//                                    "    </tr>\n" +
//                                    "  </tbody>\n" +
//                                    "</table><br>\n" +
//                                    "<p><a href=\"#\" onClick=\"openMap('" + mapUrl.replace(" ", "%20") + "','" +
//                                    paramRequest.getLocaleString("mapAbout").replace(" ", "%20") + "%20" + dbName.replace(" ", "%20") +
//                                    "','menubar=0,width=420,height=420');\">" +
//                                    paramRequest.getLocaleString("mapAbout") + " " + dbName + "</a></p>" +
//                                    "<hr><br>");
//                        }
//                        sbf.append("<br>" + paramRequest.getLocaleString("msgSearch") + ": " + query + ".<br>");
//                    //System.out.println("+++Tiempo de consulta dbPedia: " + String.valueOf(System.currentTimeMillis() - time) + "milisegundos");
//                    Model model = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel();
//                    SemanticModel mo = new SemanticModel("test", model);
//                    //Query squery = QueryFactory.create(sparqlQuery);
//                   // System.out.println("------------------------------");
//                  // System.out.println(sparqlQuery);
//                  // System.out.println("------------------------------");
//
//                    //squery.serialize();
//                    QueryExecution qexec = mo.sparQLQuery(sparqlQuery);
//
//                    try {
//                        time = System.currentTimeMillis();
//                        int hitsCount = 0;
//                        sbf.append("<div>\n" +
//                                   "  <table cellspacing=7>\n");
//                        ResultSet rs = qexec.execSelect();
//                        sbf.append("  <thead>\n" +
//                                   "    <tr>\n");
//                        if (rs.hasNext()) {
//                            for (String icol : (List<String>) rs.getResultVars()) {
//                                sbf.append("      <th>" + icol + "</th>\n");
//                            }
//                            sbf.append("    </tr>\n");
//                            sbf.append("  </thead>\n");
//                            sbf.append("  <tbody>\n");
//
//
//                            boolean odd = true;
//                            while (rs.hasNext()) {
//                                odd = !odd;
//                                QuerySolution rb = rs.nextSolution();
//                                if (odd) {
//                                    sbf.append("    <tr bgcolor=\"#EFEDEC\">\n");
//                                } else {
//                                    sbf.append("    <tr>\n");
//                                }
//                                //Iterator<String> it = rs.getResultVars().iterator();
//                                for (String name : (List<String>) rs.getResultVars()) {
//                                    hitsCount++;
//                                //while (it.hasNext()) {
//                                  //  String name = it.next();
//
//                                    RDFNode x = rb.get(name);
//                                    sbf.append("      <td>");
//                                    if (x != null) {
//                                        SemanticClass org = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/emexcatalog.owl#Organisation");
//                                        if (x.isLiteral()) {
//                                            /*if (name.equals(so_name.getDisplayName(lang2))) {
//                                                so2 = getOrganizationByName(x.asNode().getLiteral().getLexicalForm());
//                                                System.out.println(">>>Name " + so2.getProperty(so_name));
//                                                //System.out.println(">>> name: " + x.asNode().getLiteral().getLexicalForm());
//                                                sbf.append(x.asNode().getLiteral().getLexicalForm());
//                                            } else {*/
//                                                sbf.append(x.asNode().getLiteral().getLexicalForm());
//                                            //}
//                                        } else {
//                                            //System.out.println(">obteniendo objeto semántico");
//                                            SemanticObject so = SemanticObject.createSemanticObject(x.toString());
//
//                                            if (so != null) {
//                                                SemanticClass tt = so.getSemanticClass();
//                                                if (tt != null) {
//                                                    if (so.instanceOf(org)) {
//                                                        String mapUrl = getResourceBase().getAttribute("mapUrl").replace(" ", "%20") +
//                                                        "?lat=" + so.getProperty(so_lat) +
//                                                        "&long="+ so.getProperty(so_long) +
//                                                        "&wikiUrl=" + (so.getProperty(so_home) == null?"#":so.getProperty(so_home).replace(" ","%20")) +
//                                                        "&info=" + so.getProperty(so_name).replace("\"", "").replace(" ", "%20");
//                                                        sbf.append("<a href=\"#\" onclick=\"openMap('" + mapUrl + "','" +
//                                                        paramRequest.getLocaleString("mapAbout").replace(" ", "%20") + "%20" + tt.getDisplayName(lang).replace(" ","%20") +
//                                                        "','menubar=0,width=420,height=420');\">" + so.getProperty(so_name).replace("\"", "") + "</a>");
//                                                    } else {
//                                                        sbf.append(tt.getDisplayName(lang));
//                                                    }
//                                                } else {
//                                                    SemanticProperty stt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(so.getURI());
//                                                    sbf.append(stt.getDisplayName(lang));
//                                                }
//                                            } else {
//                                                //System.out.println(">el dato no tiene objeto semántico");
//                                                if (x != null) {
//                                                    //System.out.println(">escribiendo valor");
//                                                    sbf.append(x);
//                                                } else {
//                                                    //System.out.println(">el campo es nulo");
//                                                    sbf.append(" - ");
//                                                }
//                                            }
//                                        }
//                                    }
//                                    sbf.append("</td>\n");
//                                }
//                                sbf.append("    </tr>\n");
//                            }
//                        } else {
//                            sbf.append("      <th><font color='red'>" + paramRequest.getLocaleString("nofound") + "</font></th>");
//                            sbf.append("    </tr>\n");
//                            sbf.append("  </thead>\n");
//                            sbf.append("  <tbody>\n");
//                        }
//                        sbf.append("  </tbody>\n");
//                        sbf.append("</table>\n");
//                        sbf.append("<p aling=\"center\">\n" + hitsCount + " coincidencias en " + paramRequest.getLocaleString("exectime") + ": " + (System.currentTimeMillis() - time) + "ms." + "</p>\n");
//                        //System.out.println("+++Tiempo de consulta modelo local: " + String.valueOf(System.currentTimeMillis() - time) + "milisegundos");
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
//        sbf.append("</div>\n");
//        out.print(sbf.toString());
//    }
//
//    /**
//     * Creates the id.
//     * 
//     * @param suffix the suffix
//     * @return the string
//     */
//    private String createId(String suffix) {
//        return getResourceBase().getId() + "/" + suffix;
//    }
//
//    /**
//     * Gets the organization by name.
//     * 
//     * @param name the name
//     * @return the organization by name
//     */
//    private SemanticObject getOrganizationByName(String name) {
//        String sparqlQuery = "PREFIX emex: <http://www.semanticwebbuilder.org/emexcatalog.owl#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "SELECT DISTINCT ?org\n" +
//                "WHERE {\n" +
//                "  ?org rdf:type emex:Organisation.\n" +
//                "  ?org emex:name  \"" + name + "\".\n" +
//                "}\n";
//        //System.out.println(sparqlQuery);
//        Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
//        Query squery = QueryFactory.create(sparqlQuery);
//        squery.serialize();
//        QueryExecution qexec = QueryExecutionFactory.create(squery, model);
//
//        try {
//            ResultSet rs = qexec.execSelect();
//            if (rs.hasNext()) {
//                while (rs.hasNext()) {
//                    QuerySolution rb = rs.nextSolution();
//                    for (String fname : (List<String>) rs.getResultVars()) {
//                        RDFNode x = rb.get(fname);
//
//                        if (x != null && !x.isLiteral()) {
//                            return SemanticObject.createSemanticObject(x.toString());
//                        } else {
//                            return null;
//                        }
//                    }
//                }
//            }
//            return null;
//        } finally {
//            qexec.close();
//        }
//    }
}