/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.nlp.Lexicon;
import org.semanticwb.nlp.translation.SWBSparqlTranslator;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.admin.resources.SWBAListRelatedObjects;

/**
 *
 * @author hasdai
 */
public class SemanticSearch extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBAListRelatedObjects.class);
    private SWBSparqlTranslator tr = null;
    private Lexicon lex = null;
    private boolean langChanged = false;
    private String lang2 = "x-x";
    private String lang = "x-x";

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        String query = request.getParameter(createId("naturalQuery"));
        User user = response.getUser();

        if (action.equals("SEARCH")) {
            if (query == null || query.equals("")) {
                response.setMode(SWBResourceURL.Mode_VIEW);
                return;
            }

            //Get user language if any and create lexicon and translator acordingly
            if (user != null) {
                if (!lang.equals(user.getLanguage())) {
                    lang = user.getLanguage();
                    lex = new Lexicon(lang);
                }
            } else {
                if (!lang.equals("es")) {
                    lang = "es";
                    lex = new Lexicon(lang);
                }
            }

            if (request.getParameter(createId("showInfo")) != null) {
                response.setRenderParameter(createId("showInfo"), request.getParameter(createId("showInfo")));
            } else {
                response.setRenderParameter(createId("showInfo"), "noshow");
            }

            //Lexicon dict = new Lexicon(request.getParameter("lang"));
            tr = new SWBSparqlTranslator(lex);
            String queryString = lex.getPrefixString() + "\n" + tr.translateSentence(query);

            response.setRenderParameter("errCode", Integer.toString(tr.getErrCode()));
            response.setRenderParameter("sparqlQuery", queryString);
            response.setRenderParameter(createId("naturalQuery"), query);
            response.setMode("SHOWRES");
            
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("SUGGEST")) {
            doSuggest(request, response, paramRequest);
        } else if (mode.equals("SHOWRES")) {
            doShowResults(request, response, paramRequest);
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
        StringBuffer sbf = new StringBuffer();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();        
        String query = request.getParameter(createId("naturalQuery"));
        String checked = request.getParameter(createId("showInfo"));
        User user = paramRequest.getUser();

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        //Get user language if any
        if (user != null) {
                lang = user.getLanguage();
        } else {
                lang = "es";
        }        

        //Assert query string
        if (query == null) {
            query = "";
        } else {
            query = query.trim();
        }

        //Assert checkbox value
        if (checked == null) {
            checked = "noshow";
        }

        rUrl.setMode("SUGGEST");
        rUrl.setCallMethod(rUrl.Call_DIRECT);

        sbf.append("<script type=\"text/javascript\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad: true, isDebug: false\"></script>");
        sbf.append("<script type=\"text/javascript\">\n" +
                "  dojo.require(\"dijit.form.Form\");\n" +
                "  dojo.require(\"dijit.form.Button\");\n" +
                "  dojo.require(\"dijit.form.TextBox\");\n" +
                "</script>\n");
        sbf.append("<script type=\"text/javascript\">\n" +
                "  dojo.addOnLoad(function () {\n" +
                "    dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onkeydown', 'queryOnKeyDown');\n" +
                "    dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onkeyup', 'queryOnKeyUp');\n" +
                "    dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onblur', function () {\n" +
                "      clearSuggestions();\n" +
                "    });\n" +
                "  });\n" +
                "  var source =\"" + rUrl + "\";\n" +
                "  var lang =\"" + lang + "\";\n" +
                "  var displayed;\n" +
                "  var pdisplayed;\n" +
                "</script>\n");
        sbf.append("<script type=\"text/javascript\" charset=\"utf-8\" src=\"/swb/swbadmin/js/swb_admin.js\" ></script>");

        /**
         * Clears the suggestions list and gives focus to the textarea.
         */
        sbf.append("<script type=\"text/javascript\">\n" +
                "  function clearSuggestions() {\n" +
                "    if (dojo.byId('" + createId("busca-ayuda-ok") + "')) {\n" +
                "      dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML = \"\";\n" +
                "    }\n" +
                "    displayed = false;\n" +
                "    curSelected = 0;\n" +
                "    dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
                "  }\n\n" +
                "  function queryOnKeyDown (evt) {\n" +
                "    var wd = getCurrentWord('" + createId("naturalQuery") + "');\n" +
                "      if (evt.target.value == '' || wd.word.length < 3) {\n" +
                "        clearSuggestions();\n" +
                "        return;\n" +
                "      }\n" +
                //CTRL+SHIFT+SPACE
                "      if (evt.ctrlKey && evt.shiftKey && evt.keyCode == dojo.keys.SPACE) {\n" +
                "        getSuggestions(wd, source, true, false);\n" +
                "        dojo.stopEvent(evt);\n" +
                "      } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                "        setSelection(curSelected, (wd.word == \"" + paramRequest.getLocaleString("with").toLowerCase() + "\")?\"" + paramRequest.getLocaleString("with").toLowerCase() + " \":\"\");\n" +
                "        clearSuggestions();\n" +
                "        pdisplayed = false;\n" +
                "        dojo.stopEvent(evt);\n" +
                "      }\n" +
                "  }\n\n" +
                "  function queryOnKeyUp (evt) {\n" +
                "    var wd = getCurrentWord('" + createId("naturalQuery") + "');\n" +
                "    if (evt.target.value == '' || wd.word.length < 3) {\n" +
                "      clearSuggestions();\n" +
                "        return;\n" +
                "      }\n" +
                "    if((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                "    } else if (!displayed && !pdisplayed && wd.word == \"" + paramRequest.getLocaleString("with").toLowerCase() + "\") {\n" +
                "      var pwd = getPreviousName(wd);\n" +
                "      if (pwd != \"undefined\") {\n" +
                "        getSuggestions(getPreviousName(wd), source, true, true);\n" +
                "          pdisplayed = true;\n" +
                "      }\n" +
                "        dojo.stopEvent(evt);\n" +
                "    } else if (!displayed && pdisplayed && wd.word != \"" + paramRequest.getLocaleString("with").toLowerCase() + "\") {\n" +
                "      clearSuggestions();\n" +
                "      pdisplayed = false;\n" +
                "    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {\n" +
                "      clearSuggestions();\n" +
                "      pdisplayed = false;\n" +
                "    }" +
                //UP_ARROW
                "    else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {\n" +
                "      dojo.query('.resultEntry').style('background', 'white');\n" +
                "      curSelected--;\n" +
                "      if (curSelected < 0) {\n" +
                "        curSelected = 0;\n" +
                "      }" +
                //                   "      console.log(curSelected);\n" +
                "      highLightSelection(curSelected, true);\n" +
                "      dojo.byId('" + createId("resultlist") + "').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
                "      dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
                "      dojo.stopEvent(evt);\n" +
                //DOWN_ARROW
                "    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {\n" +
                "      dojo.query('.resultEntry').style('background', 'white');\n" +
                "      curSelected++;\n" +
                "      if (curSelected > dojo.byId('" + createId("resultlist") + "').childNodes.length - 1) {\n" +
                "        curSelected = dojo.byId('" + createId("resultlist") + "').childNodes.length - 1;\n" +
                "      }\n" +
                //                   "      console.log(curSelected);\n" +
                "      highLightSelection(curSelected, true);\n" +
                "      dojo.byId('" + createId("resultlist") + "').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
                "      dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
                "      dojo.stopEvent(evt);\n" +
                "    }\n" +
                "}\n\n");

        /**
         * Gets the word at current cursor position in a textarea.
         * @param elm textarea to extract word from.
         */
        sbf.append("  function getCurrentWord(elm) {\n" +
                "    var cPos = getCaretPos(elm);\n" +
                "    var txt = dojo.byId(elm).value;\n" +
                "    var prevBlank = -1;\n" +
                "    var aftBlank = -1;\n" +
                "    var found = false;\n" +
                "    var wd = null;\n" +
                "    var wo = \"undefined\";\n" +
                "    var delimiters = \"\\n\\t \";\n" +
                "    if (txt != '') {\n" +
                "      for (var i = 0; i < txt.length; i++) {\n" +
                "        if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {\n" +
                "          prevBlank = i;\n" +
                "        }\n" +
                "      }\n" +
                "      for (i = cPos; i < txt.length && !found; i++) {\n" +
                "        if (delimiters.indexOf(txt.charAt(i)) != -1) {\n" +
                "          aftBlank = i;\n" +
                "          found = true;\n" +
                "        }\n" +
                "      }\n" +
                "      if (prevBlank == -1) {\n" +
                "        if (aftBlank == -1) {\n" +
                "          wd = txt;\n" +
                "          wo = {\n" +
                "            word: wd,\n" +
                "            startP: 0,\n" +
                "            endP: txt.length\n" +
                "          };\n" +
                "        } else {\n" +
                "          wd = txt.substring(0, aftBlank);\n" +
                "          wo = {\n" +
                "            word: wd,\n" +
                "            startP: 0,\n" +
                "            endP: aftBlank\n" +
                "          };\n" +
                "        }\n" +
                "      } else if (aftBlank == -1) {\n" +
                "        wd = txt.substring(prevBlank + 1, txt.length);\n" +
                "        wo = {\n" +
                "          word: wd,\n" +
                "          startP: prevBlank + 1,\n" +
                "          endP: txt.length\n" +
                "        };\n" +
                "      } else {\n" +
                "        wd = txt.substring(prevBlank + 1, aftBlank);\n" +
                "        wo = {\n" +
                "          word: wd,\n" +
                "          startP: prevBlank + 1,\n" +
                "          endP: aftBlank\n" +
                "        };\n" +
                "      }\n" +
                "    }\n" +
                "    return wo;\n" +
                "  }\n\n");

        /**
         * Gets cursor current position in a textarea.
         * @param elm textarea to calculate caret position from.
         */
        sbf.append("  function getCaretPos(elm) {\n" +
                "    var pos;\n" +
                "    if (dojo.doc.selection) {\n" +
                "      var Sel = document.selection.createRange();\n" +
                "      var SelLength = document.selection.createRange().text.length;\n" +
                "      Sel.moveStart ('character', -dojo.byId(elm).value.length);\n" +
                "      pos = Sel.text.length - SelLength;\n" +
                "    } else if (typeof dojo.byId(elm).selectionStart != undefined) {\n" +
                "      pos = dojo.byId(elm).selectionStart;\n" +
                "    }\n" +
                "    return pos;\n" +
                "  }\n\n");

        /**
         * Creates a suggestion list based on word at current cursor position.
         * @param word word at cursor position
         * @param src rUrl of dataStore
         * @param clear wheter or not to clear previous list
         * @param props wheter or not to get properties of the current word as SemanticClass
         */
        sbf.append("  function getSuggestions(word, src, clear, props) {\n" +
                "    if (clear) {\n" +
                "      clearSuggestions();\n" +
                "    }\n" +
                "    if(word.word == '') {\n" +
                "      return;\n" +
                "    }\n" +
                "    if (dojo.byId('" + createId("busca-ayuda-ok") + "') && word.word != '') {\n" +
                "      dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML = '<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\" width=\"20\" height=\"20\"/>" + paramRequest.getLocaleString("loading") + "...';\n" +
                "    }\n" +
                "    getHtml(src + \"?word=\" + word.word + \"&lang=\" + lang + \"&props=\" + props, '" + createId("busca-ayuda-ok") + "');\n" +
                "    displayed = true;\n" +
                "    highLightSelection(0, true);\n" +
                "  }\n\n");

        /**
         * Highlights an option in the suggestions list.
         * @param id identifier of the list item to highlight
         * @param high toggles highlight color.
         */
        sbf.append("  function highLightSelection(id, high) {\n" +
                "    var ele = dojo.byId('id' + id);\n" +
                "    console.log('buscando id' + id);\n" +
                "    if (high) {\n" +
                "      dojo.style(ele, {\n" +
                "        \"background\":\"LightBlue\"\n" +
                "      });\n" +
                "    } else {\n" +
                "      dojo.style(ele, {\n" +
                "        \"background\":\"white\"\n" +
                "      });\n" +
                "    }\n" +
                "  }\n\n");

        sbf.append("  function getPreviousName (word) {\n" +
                "    var pName = \"\";\n" +
                "    var prevBrk = -1;\n" +
                "    var firstBrk = -1;\n" +
                "    var txt = dojo.byId('" + createId("naturalQuery") + "').value;\n" +
                "    var cPos = word.startP;\n" +
                "    var wd = null;\n" +
                "    var wo = \"undefined\";\n" +
                "    var found = false;\n" +
                "    for (var i = cPos; i >= 0 && !found; i--) {\n" +
                "      if (txt.charAt(i) == ']') {\n" +
                "        prevBrk = i;\n" +
                "        found = true;\n" +
                "      }\n" +
                "    }\n" +
                "    found = false;\n" +
                "    for (i = prevBrk; i > 0 && !found; i--) {\n" +
                "      if (txt.charAt(i) == '[') {\n" +
                "        firstBrk = i;\n" +
                "        found = true;\n" +
                "      }\n" +
                "    }\n" +
                "    if (prevBrk == -1) {\n" +
                "      return wo;\n" +
                "    }\n" +
                "    firstBrk++;\n" +
                "    wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);\n" +
                "    wo = {\n" +
                "      word: wd,\n" +
                "      startP: firstBrk,\n" +
                "      endP: prevBrk\n" +
                "    };\n" +
                "    return wo;\n" +
                "  }\n\n");
        /**
         * Replaces the current word in the textarea with the selected word from the
         * suggestions list.
         * @param selected index of current selected item
         * @param prep
         */
        sbf.append("  function setSelection(selected, prep) {\n" +
                "    var word = getCurrentWord('" + createId("naturalQuery") + "');\n" +
                "    var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\");\n" +
                "    newText = prep + \"[\" + newText + \"]\";\n" +
                "    var valText = dojo.byId('" + createId("naturalQuery") + "').value;\n" +
                "    dojo.byId('" + createId("naturalQuery") + "').value = valText.substring(0, word.startP) +\n" +
                "    newText + valText.substring(word.endP, valText.length);\n" +
                "  }\n\n");

        sbf.append("  function submitParams(url) {\n" +
                "    var loc = url+'&" + createId("naturalQuery") + "=' + encodeUrlAccents(dijit.byId('" + createId("naturalQuery") + "').value);" +
                "    var h_wnd = window.open(loc,'" + paramRequest.getLocaleString("sResults") + "','width=800,height=600,menubar=yes');\n" +
                "    dojo.byId('" + createId("natural") + "').target=h_wnd;" +
                "    dojo.byId('" + createId("natural") + "').submit();" +
                "  }\n\n" +
                "  function getHtml(url, tagid) {\n" +
                "    dojo.xhrGet({\n" +
                "    url: url,\n" +
                "    load: function(response)\n" +
                "    {\n" +
                "      var tag=dojo.byId(tagid);\n" +
                "      if(tag) {\n" +
                "        var pan=dijit.byId(tagid);\n" +
                "        if(pan && pan.attr) {\n" +
                "          pan.attr('content',response);\n" +
                "        } else {\n" +
                "          tag.innerHTML = response;\n" +
                "        }\n" +
                "      } else {\n" +
                "        alert(\"No existe ningún elemento con id \" + tagid);\n" +
                "      }\n" +
                "      return response;\n" +
                "    },\n" +
                "    error: function(response) {\n" +
                "      if(dojo.byId(tagid)) {\n" +
                "        dojo.byId(tagid).innerHTML = \"<p>Ocurrió un error con respuesta:<br />\" + response + \"</p>\";\n" +
                "      } else {\n" +
                "        alert(\"No existe ningún elemento con id \" + tagid);\n" +
                "      }\n" +
                "      return response;\n" +
                "    },\n" +
                "    handleAs: \"text\"\n" +
                "  });\n" +
                "}");
        sbf.append("  function encodeUrlAccents (str) {\n" +
                "    var res = str.replace(\"ó\", \"%F3\");\n" +
                "    return res;" +
                "  }\n" +
                "</script>\n\n");


        //Set url to show results
//        rUrl = paramRequest.getRenderUrl().setMode("SHOWRES").setCallMethod(rUrl.Call_DIRECT);
  //      rUrl.setParameter("lang", lang);
        
        sbf.append("<script type=\"text/javascript\">\n" +
                   "  function doSubmit(url) {\n" +
                   "    loc = url;\n" +                   
                   "    loc = loc + \"&" + createId("naturalQuery") + "=\" + dojo.byId('"+ createId("naturalQuery")+"').value;\n" +
                   "    if (dojo.byId('" + createId("showInfo") + "').checked) {\n" +
                   "      loc = loc + '&" + createId("showInfo") + "=show';" +
                   "    }\n" +
                   "    window.open(loc, '');" +
                   "  }\n" +
                   "</script>\n");

        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
            String url = "/swb/swb/site1/resultados";

            sbf.append("<form id=\"" + createId("natural") + "\" dojoType=\"dijit.form.Form\" " +
                "action=\"" + url + "\" method=\"post\">\n" +
                "  <textarea id=\"" + createId("naturalQuery") + "\" " +
                "name=\"" + createId("naturalQuery") + "\" class=\"txt-busca\" >" + query + "</textarea>\n" +
                "  <div id=\"" + createId("busca-ayuda-ok") + "\"></div>\n" +
                /*"  <input type=\"checkbox\" name=\"" + createId("showInfo") + "\" " +
                "value=\"show\" id=\"" + createId("showInfo") + "\">" +
                paramRequest.getLocaleString("msgShowInfo") + "<br>\n" +*/
                "  <input type=\"submit\" value=\"Buscar\" class=\"btn-busca\">\n" +
                "</form>\n");
        } else {
            doShowResults(request, response, paramRequest);
        }
        out.print(sbf.toString());
    }

    public void doSuggest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SortedSet objOptions = new TreeSet();
        SortedSet proOptions = new TreeSet();
        String word = request.getParameter("word");
        String lan = request.getParameter("lang");
        boolean props = Boolean.parseBoolean(request.getParameter("props"));
        String tempcDn = "";
        boolean lPar = false;
        boolean rPar = false;
        int idCounter = 0;
        //Lexicon lex = new Lexicon(lan);
        StringBuffer sbf = new StringBuffer();

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if (lan == null || lan.equals("")) {
            lan = "es";
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
                tempcDn = tempc.getDisplayName(lan);

                if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
                    objOptions.add(tempcDn);
                }

                Iterator<SemanticProperty> sit = tempc.listProperties();
                while (sit.hasNext()) {
                    SemanticProperty tempp = sit.next();
                    tempcDn = tempp.getDisplayName(lan);

                    if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
                        proOptions.add(tempcDn);
                    }
                }
            }

            if (proOptions.size() != 0 || objOptions.size() != 0) {
                idCounter = 0;
                int index;
                Iterator<String> rit = objOptions.iterator();

                sbf.append("<ul id=\"" + createId("resultlist") + "\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
                        "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
                        "200px;width:300px;border:1px solid #a0a0ff;\">");
                while (rit.hasNext()) {
                    String tempi = (String) rit.next();
                    index = tempi.toLowerCase().indexOf(word.toLowerCase());

                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
                            "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;pdisplayed=false\">" + (lPar ? "(" : "") +
                            "<font color=\"red\">" + tempi + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }

                rit = proOptions.iterator();
                while (rit.hasNext()) {
                    String tempi = (String) rit.next();
                    index = tempi.toLowerCase().indexOf(word.toLowerCase());

                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
                            "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;pdisplayed=false;\">" + (lPar ? "(" : "") +
                            "<font color=\"blue\">" + tempi + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }
                sbf.append("</ul>");
            }
        } else {
            String tag = lex.getObjWordTag(word).getObjId();

            sbf.append("<ul id=\"" + createId("resultlist") + "\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
                    "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
                    "200px;width:300px;border:1px solid #a0a0ff;\">");

            if (!tag.equals("")) {
                SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
                idCounter = 0;
                Iterator<SemanticProperty> sit = sc.listProperties();
                while (sit.hasNext()) {
                    SemanticProperty t = sit.next();
                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
                            "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;\">" + (lPar ? "(" : "") +
                            "<font color=\"red\">" + t.getDisplayName(lan) + "</font>" +
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
                        sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                                "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                                "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                                "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                                "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML='';" +
                                "dojo.byId('" + createId("naturalQuery") + "').focus();displayed=false;\">" + (lPar ? "(" : "") +
                                "<font color=\"red\">" + t.getDisplayName(lan) + "</font>" +
                                (lPar ? ")" : "") + "</li>");
                        idCounter++;
                    }
                }
            }
            sbf.append("</ul>");
        }
        out.println(sbf.toString());
    }

    public void doShowResults(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String query = request.getParameter(createId("naturalQuery"));
        String checked = request.getParameter(createId("showInfo"));
        User user = paramRequest.getUser();

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //Get user language if any and create lexicon and translator acordingly
        if (user != null) {
            if (!lang2.equals(paramRequest.getUser().getLanguage())) {
                lang2 = paramRequest.getUser().getLanguage();
                lex = new Lexicon(lang2);
            }
        } else {
            if (!lang2.equals("es")) {
                lang2 = "es";
                lex = new Lexicon(lang2);
            }
        }

        //Assert query string
        if (query == null) {
            query = "";
        } else {
            query = query.trim();
        }

        //Assert checkbox value
        if (checked == null) {
            checked = "noshow";
        }

        rUrl.setMode("SUGGEST");
        rUrl.setCallMethod(rUrl.Call_DIRECT);
        
        sbf.append("<script type=\"text/javascript\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad: true, isDebug: false\"></script>");
        sbf.append("<script type=\"text/javascript\">\n" +
                "  dojo.require(\"dijit.form.Form\");\n" +
                "  dojo.require(\"dijit.form.Button\");\n" +
                "  dojo.require(\"dijit.form.TextBox\");\n" +
                "</script>\n");

        sbf.append("<script src=\"http://maps.google.com/maps?file=api&v=2&sensor=false&key=ABQIAAAAOJNnlv7XtimNAEXtmyrRcBSvBiWx47Sg3GqjNr4y4ou_VmvayxTQVYbEJHhu9GaB8tmww2NjFuv0vA\"" +
                  " type=\"text/javascript\"></script>");

        sbf.append("<script type=\"text/javascript\">\n" +
                "  dojo.addOnLoad(function () {\n" +
                "    dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onkeydown', 'queryOnKeyDown');\n" +
                "    dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onkeyup', 'queryOnKeyUp');\n" +
                "    dojo.connect(dojo.byId('" + createId("naturalQuery") + "'), 'onblur', function () {\n" +
                "      clearSuggestions();\n" +
                "    });\n" +
                "  });\n" +
                "  var source =\"" + rUrl + "\";\n" +
                "  var lang =\"" + lang2 + "\";\n" +
                "  var displayed;\n" +
                "  var pdisplayed;\n" +
                "</script>\n");
        sbf.append("<script type=\"text/javascript\" charset=\"utf-8\" src=\"/swb/swbadmin/js/swb_admin.js\" ></script>");

        /**
         * Clears the suggestions list and gives focus to the textarea.
         */
        sbf.append("<script type=\"text/javascript\">\n" +
                "  function clearSuggestions() {\n" +
                "    if (dojo.byId('" + createId("busca-ayuda-ok") + "')) {\n" +
                "      dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML = \"\";\n" +
                "    }\n" +
                "    displayed = false;\n" +
                "    curSelected = 0;\n" +
                "    dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
                "  }\n\n" +
                "  function queryOnKeyDown (evt) {\n" +
                "    var wd = getCurrentWord('" + createId("naturalQuery") + "');\n" +
                "      if (evt.target.value == '' || wd.word.length < 3) {\n" +
                "        clearSuggestions();\n" +
                "        return;\n" +
                "      }\n" +
                //CTRL+SHIFT+SPACE
                "      if (evt.ctrlKey && evt.shiftKey && evt.keyCode == dojo.keys.SPACE) {\n" +
                "        getSuggestions(wd, source, true, false);\n" +
                "        dojo.stopEvent(evt);\n" +
                "      } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                "        setSelection(curSelected, (wd.word == \"" + paramRequest.getLocaleString("with").toLowerCase() + "\")?\"" + paramRequest.getLocaleString("with").toLowerCase() + " \":\"\");\n" +
                "        clearSuggestions();\n" +
                "        pdisplayed = false;\n" +
                "        dojo.stopEvent(evt);\n" +
                "      }\n" +
                "  }\n\n" +
                "  function queryOnKeyUp (evt) {\n" +
                "    var wd = getCurrentWord('" + createId("naturalQuery") + "');\n" +
                "    if (evt.target.value == '' || wd.word.length < 3) {\n" +
                "      clearSuggestions();\n" +
                "        return;\n" +
                "      }\n" +
                "    if((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                "    } else if (!displayed && !pdisplayed && wd.word == \"" + paramRequest.getLocaleString("with").toLowerCase() + "\") {\n" +
                "      var pwd = getPreviousName(wd);\n" +
                "      if (pwd != \"undefined\") {\n" +
                "        getSuggestions(getPreviousName(wd), source, true, true);\n" +
                "          pdisplayed = true;\n" +
                "      }\n" +
                "        dojo.stopEvent(evt);\n" +
                "    } else if (!displayed && pdisplayed && wd.word != \"" + paramRequest.getLocaleString("with").toLowerCase() + "\") {\n" +
                "      clearSuggestions();\n" +
                "      pdisplayed = false;\n" +
                "    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {\n" +
                "      clearSuggestions();\n" +
                "      pdisplayed = false;\n" +
                "    }" +
                //UP_ARROW
                "    else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {\n" +
                "      dojo.query('.resultEntry').style('background', 'white');\n" +
                "      curSelected--;\n" +
                "      if (curSelected < 0) {\n" +
                "        curSelected = 0;\n" +
                "      }" +
                //                   "      console.log(curSelected);\n" +
                "      highLightSelection(curSelected, true);\n" +
                "      dojo.byId('" + createId("resultlist") + "').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
                "      dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
                "      dojo.stopEvent(evt);\n" +
                //DOWN_ARROW
                "    } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {\n" +
                "      dojo.query('.resultEntry').style('background', 'white');\n" +
                "      curSelected++;\n" +
                "      if (curSelected > dojo.byId('" + createId("resultlist") + "').childNodes.length - 1) {\n" +
                "        curSelected = dojo.byId('" + createId("resultlist") + "').childNodes.length - 1;\n" +
                "      }\n" +
                //                   "      console.log(curSelected);\n" +
                "      highLightSelection(curSelected, true);\n" +
                "      dojo.byId('" + createId("resultlist") + "').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
                "      dojo.byId('" + createId("naturalQuery") + "').focus();\n" +
                "      dojo.stopEvent(evt);\n" +
                "    }\n" +
                "}\n\n");

        /**
         * Gets the word at current cursor position in a textarea.
         * @param elm textarea to extract word from.
         */
        sbf.append("  function getCurrentWord(elm) {\n" +
                "    var cPos = getCaretPos(elm);\n" +
                "    var txt = dojo.byId(elm).value;\n" +
                "    var prevBlank = -1;\n" +
                "    var aftBlank = -1;\n" +
                "    var found = false;\n" +
                "    var wd = null;\n" +
                "    var wo = \"undefined\";\n" +
                "    var delimiters = \"\\n\\t \";\n" +
                "    if (txt != '') {\n" +
                "      for (var i = 0; i < txt.length; i++) {\n" +
                "        if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {\n" +
                "          prevBlank = i;\n" +
                "        }\n" +
                "      }\n" +
                "      for (i = cPos; i < txt.length && !found; i++) {\n" +
                "        if (delimiters.indexOf(txt.charAt(i)) != -1) {\n" +
                "          aftBlank = i;\n" +
                "          found = true;\n" +
                "        }\n" +
                "      }\n" +
                "      if (prevBlank == -1) {\n" +
                "        if (aftBlank == -1) {\n" +
                "          wd = txt;\n" +
                "          wo = {\n" +
                "            word: wd,\n" +
                "            startP: 0,\n" +
                "            endP: txt.length\n" +
                "          };\n" +
                "        } else {\n" +
                "          wd = txt.substring(0, aftBlank);\n" +
                "          wo = {\n" +
                "            word: wd,\n" +
                "            startP: 0,\n" +
                "            endP: aftBlank\n" +
                "          };\n" +
                "        }\n" +
                "      } else if (aftBlank == -1) {\n" +
                "        wd = txt.substring(prevBlank + 1, txt.length);\n" +
                "        wo = {\n" +
                "          word: wd,\n" +
                "          startP: prevBlank + 1,\n" +
                "          endP: txt.length\n" +
                "        };\n" +
                "      } else {\n" +
                "        wd = txt.substring(prevBlank + 1, aftBlank);\n" +
                "        wo = {\n" +
                "          word: wd,\n" +
                "          startP: prevBlank + 1,\n" +
                "          endP: aftBlank\n" +
                "        };\n" +
                "      }\n" +
                "    }\n" +
                "    return wo;\n" +
                "  }\n\n");

        /**
         * Gets cursor current position in a textarea.
         * @param elm textarea to calculate caret position from.
         */
        sbf.append("  function getCaretPos(elm) {\n" +
                "    var pos;\n" +
                "    if (dojo.doc.selection) {\n" +
                "      var Sel = document.selection.createRange();\n" +
                "      var SelLength = document.selection.createRange().text.length;\n" +
                "      Sel.moveStart ('character', -dojo.byId(elm).value.length);\n" +
                "      pos = Sel.text.length - SelLength;\n" +
                "    } else if (typeof dojo.byId(elm).selectionStart != undefined) {\n" +
                "      pos = dojo.byId(elm).selectionStart;\n" +
                "    }\n" +
                "    return pos;\n" +
                "  }\n\n");

        /**
         * Creates a suggestion list based on word at current cursor position.
         * @param word word at cursor position
         * @param src rUrl of dataStore
         * @param clear wheter or not to clear previous list
         * @param props wheter or not to get properties of the current word as SemanticClass
         */
        sbf.append("  function getSuggestions(word, src, clear, props) {\n" +
                "    if (clear) {\n" +
                "      clearSuggestions();\n" +
                "    }\n" +
                "    if(word.word == '') {\n" +
                "      return;\n" +
                "    }\n" +
                "    if (dojo.byId('" + createId("busca-ayuda-ok") + "') && word.word != '') {\n" +
                "      dojo.byId('" + createId("busca-ayuda-ok") + "').innerHTML = '<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\" width=\"20\" height=\"20\"/>" + paramRequest.getLocaleString("loading") + "...';\n" +
                "    }\n" +
                "    getHtml(src + \"?word=\" + word.word + \"&lang=\" + lang + \"&props=\" + props, '" + createId("busca-ayuda-ok") + "');\n" +
                "    displayed = true;\n" +
                "    highLightSelection(0, true);\n" +
                "  }\n\n");

        /**
         * Highlights an option in the suggestions list.
         * @param id identifier of the list item to highlight
         * @param high toggles highlight color.
         */
        sbf.append("  function highLightSelection(id, high) {\n" +
                "    var ele = dojo.byId('id' + id);\n" +
                "    console.log('buscando id' + id);\n" +
                "    if (high) {\n" +
                "      dojo.style(ele, {\n" +
                "        \"background\":\"LightBlue\"\n" +
                "      });\n" +
                "    } else {\n" +
                "      dojo.style(ele, {\n" +
                "        \"background\":\"white\"\n" +
                "      });\n" +
                "    }\n" +
                "  }\n\n");

        sbf.append("  function getPreviousName (word) {\n" +
                "    var pName = \"\";\n" +
                "    var prevBrk = -1;\n" +
                "    var firstBrk = -1;\n" +
                "    var txt = dojo.byId('" + createId("naturalQuery") + "').value;\n" +
                "    var cPos = word.startP;\n" +
                "    var wd = null;\n" +
                "    var wo = \"undefined\";\n" +
                "    var found = false;\n" +
                "    for (var i = cPos; i >= 0 && !found; i--) {\n" +
                "      if (txt.charAt(i) == ']') {\n" +
                "        prevBrk = i;\n" +
                "        found = true;\n" +
                "      }\n" +
                "    }\n" +
                "    found = false;\n" +
                "    for (i = prevBrk; i > 0 && !found; i--) {\n" +
                "      if (txt.charAt(i) == '[') {\n" +
                "        firstBrk = i;\n" +
                "        found = true;\n" +
                "      }\n" +
                "    }\n" +
                "    if (prevBrk == -1) {\n" +
                "      return wo;\n" +
                "    }\n" +
                "    firstBrk++;\n" +
                "    wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);\n" +
                "    wo = {\n" +
                "      word: wd,\n" +
                "      startP: firstBrk,\n" +
                "      endP: prevBrk\n" +
                "    };\n" +
                "    return wo;\n" +
                "  }\n\n");
        /**
         * Replaces the current word in the textarea with the selected word from the
         * suggestions list.
         * @param selected index of current selected item
         * @param prep
         */
        sbf.append("  function setSelection(selected, prep) {\n" +
                "    var word = getCurrentWord('" + createId("naturalQuery") + "');\n" +
                "    var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\");\n" +
                "    newText = prep + \"[\" + newText + \"]\";\n" +
                "    var valText = dojo.byId('" + createId("naturalQuery") + "').value;\n" +
                "    dojo.byId('" + createId("naturalQuery") + "').value = valText.substring(0, word.startP) +\n" +
                "    newText + valText.substring(word.endP, valText.length);\n" +
                "  }\n\n");

        sbf.append("  function submitParams(url) {\n" +
                "    var loc = url+'&" + createId("naturalQuery") + "=' + encodeUrlAccents(dijit.byId('" + createId("naturalQuery") + "').value);" +
                "    var h_wnd = window.open(loc,'" + paramRequest.getLocaleString("sResults") + "','width=800,height=600,menubar=yes');\n" +
                "    dojo.byId('" + createId("natural") + "').target=h_wnd;" +
                "    dojo.byId('" + createId("natural") + "').submit();" +
                "  }\n\n" +
                "  function getHtml(url, tagid) {\n" +
                "    dojo.xhrGet({\n" +
                "    url: url,\n" +
                "    load: function(response)\n" +
                "    {\n" +
                "      var tag=dojo.byId(tagid);\n" +
                "      if(tag) {\n" +
                "        var pan=dijit.byId(tagid);\n" +
                "        if(pan && pan.attr) {\n" +
                "          pan.attr('content',response);\n" +
                "        } else {\n" +
                "          tag.innerHTML = response;\n" +
                "        }\n" +
                "      } else {\n" +
                "        alert(\"No existe ningún elemento con id \" + tagid);\n" +
                "      }\n" +
                "      return response;\n" +
                "    },\n" +
                "    error: function(response) {\n" +
                "      if(dojo.byId(tagid)) {\n" +
                "        dojo.byId(tagid).innerHTML = \"<p>Ocurrió un error con respuesta:<br />\" + response + \"</p>\";\n" +
                "      } else {\n" +
                "        alert(\"No existe ningún elemento con id \" + tagid);\n" +
                "      }\n" +
                "      return response;\n" +
                "    },\n" +
                "    handleAs: \"text\"\n" +
                "  });\n" +
                "}");
        sbf.append("  function encodeUrlAccents (str) {\n" +
                "    var res = str.replace(\"ó\", \"%F3\");\n" +
                "    return res;" +
                "  }\n" +
                "</script>\n\n");


        HashMap<String, String> dbNames = new HashMap<String, String>();
        dbNames.put("Miguel Hidalgo", "Miguel_Hidalgo,_D.F.");
        dbNames.put("Tamaulipas", "Tamaulipas");
        dbNames.put("México, D.F.", "México_city");
        dbNames.put("Veracruz", "Veracruz");
        dbNames.put("Tamaulipas", "Tamaulipas");
        dbNames.put("Alvaro Obregón", "Álvaro_Obregón,_D.F.");
        dbNames.put("Xochimilco", "Xochimilco");
        dbNames.put("Tlalpan", "Tlalpan");
        dbNames.put("Altamira", "Altamira,_Tamaulipas");
        dbNames.put("Aldama", "Aldama,_Tamaulipas");
        dbNames.put("Abasolo", "");
        dbNames.put("Burgos", "");
        dbNames.put("Aguascalientes", "Aguascalientes,_Aguascalientes");
        dbNames.put("Asientos", "Asientos,_Aguascalientes");
        dbNames.put("Calvillo", "Calvillo,_Aguascalientes");
        dbNames.put("Cosío", "Cosío,_Aguascalientes");
        dbNames.put("Actopan", "Actopan,_Veracruz");
        dbNames.put("Atoyac", "");
        dbNames.put("Córdoba", "Córdoba,_Veracruz");
        dbNames.put("Las minas", "");

        //Add language parameter to the action url string
        aUrl.setAction("SEARCH");
        aUrl.setParameter("lang", lang2);

        sbf.append("<form id=\"" + createId("natural") + "\" dojoType=\"dijit.form.Form\" " +
                "action=\"" + aUrl + "\" >\n" +
                "  <textarea id=\"" + createId("naturalQuery") + "\" rows=1 cols=70" +
                "name=\"" + createId("naturalQuery") + "\" class=\"txt-busca\" >" + query + "</textarea>\n" +
                "  <div id=\"" + createId("busca-ayuda-ok") + "\"></div>\n" +
                "  <input type=\"submit\" value=\"Buscar\" class=\"btn-busca\">\n" +
                "</form>\n");

        tr = new SWBSparqlTranslator(lex);
        String sparqlQuery = lex.getPrefixString() + "\n" + tr.translateSentence(query);
        System.out.println(sparqlQuery);
        String errCount = Integer.toString(tr.getErrCode());

        if (errCount != null) {
            if (Integer.parseInt(errCount) == 0) {
                String patternStr = "\"[a-zA-Z]+\"";
                String mname = "";
                String dbName = "";

                // Compile and use regular expression
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(sparqlQuery);
                boolean matchFound = matcher.find();

                if (matchFound) {
                    // Get all groups for this match
                    for (int i = 0; i <= matcher.groupCount(); i++) {
                        // Get the group's indices
                        int groupStart = matcher.start(i);
                        int groupEnd = matcher.end(i);

                        // groupStr is equivalent to
                        mname = sparqlQuery.subSequence(groupStart, groupEnd).toString();
                    }
                }

                if (dbNames.containsKey(mname.replace("\"", ""))) {
                    dbName = dbNames.get(mname.replace("\"", ""));
                }

                try {

                    sbf.append("<br><br><h2>Resultados de la búsqueda</h2>\n");
                        System.out.println("Obteniendo información de la dbpedia");
                        if (!dbName.equals("")) {
                            String dbPediaQuery =
                                    "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                                    "PREFIX dbprop: <http://dbpedia.org/property/>\n" +
                                    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"+
                                    "SELECT ?desc ?lat ?long ?page\n" +
                                    "WHERE {\n" +
                                    "<http://dbpedia.org/resource/" + dbName + "> dbprop:abstract ?desc.\n" +
                                    "FILTER (lang(?desc) = \"" + lang2 + "\").\n" +
                                    "<http://dbpedia.org/resource/" + dbName + "> geo:lat ?lat.\n" +
                                    "<http://dbpedia.org/resource/" + dbName + "> geo:long ?long.\n" +
                                    "<http://dbpedia.org/resource/" + dbName + "> foaf:page ?page\n" +
                                    "}\n";
                            //System.out.println("<<<<<" + dbPediaQuery);
                            //Query dbpedia
                            SemanticModel dbpModel = SWBPlatform.getSemanticMgr().getModel("DBPedia");
                            QueryExecution dbQexec = dbpModel.sparQLQuery(dbPediaQuery);

                            //Get dbPedia INFO
                            ResultSet dbrs = dbQexec.execSelect();
                            QuerySolution dbrb = dbrs.nextSolution();
                            RDFNode desc_node = dbrb.get("desc");
                            RDFNode lat_node = dbrb.get("lat");
                            RDFNode long_node = dbrb.get("long");
                            //RDFNode home_node = dbrb.get("page");

                            /*System.out.println("Información obtenida");
                            System.out.println("-->" + desc_node.asNode().getLiteral().getLexicalForm());
                            System.out.println("-->" + lat_node.asNode().getLiteral().getLexicalForm());
                            System.out.println("-->" + long_node.asNode().getLiteral().getLexicalForm());*/
                            //System.out.println("-->" + home_node.asNode().getLiteral().getLexicalForm());

                    /*sbf.append("<script type=\"text/javascript\" " +
                            "src=\"http://maps.google.com/maps?file=api&v=2&key=ABQIAAAAOJNnlv7XtimNAEXtmyrRcBTb-vLQlFZmc2N8bgWI8YDPp5FEVBSt-jSEUxX5Zuafs_gGbS--6HOwVw&sensor=true\"></script>");*/

                            String mapUrl = "/swb/swb/site1/googleMap?lat=" + lat_node.asNode().getLiteral().getLexicalForm() + "&long="+ long_node.asNode().getLiteral().getLexicalForm();
                            sbf.append("<table cellpadding=10 cellspacing=10>\n" +
                                    "  <thead>\n" +
                                    "    <tr>\n" +
                                    "      <th>" + paramRequest.getLocaleString("infoAbout") + " " + //" <a href=\"" +
                                            /*home_node.asNode().getLiteral().getLexicalForm() + "\">" + */dbName + "</th>\n" +
                                    "    </tr>\n" +
                                    "  </thead>\n" +
                                    "  <tbody>\n" +
                                    "    <tr>\n" +
                                    "      <td>" + desc_node.asNode().getLiteral().getLexicalForm() + "</td>\n" +
                                    "    </tr>\n" +
                                    "  </tbody>\n" +
                                    "</table><br>\n" +
                                    "<p><a href=\"#\" onClick=\"window.open('" + mapUrl + "','" +
                                    paramRequest.getLocaleString("mapAbout") + " " + dbName + "','menubar=0, width=400, height=300');return false;\">" + paramRequest.getLocaleString("mapAbout") + " " + dbName + "</a></p>" +
                                    "<hr><br>" +
                                    query + ":<br>");
                        }

                    Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
                    Query squery = QueryFactory.create(sparqlQuery);
//                    System.out.println("------------------------------");
//                    System.out.println(sparqlQuery);
//                    System.out.println("------------------------------");

                    squery.serialize();
                    QueryExecution qexec = QueryExecutionFactory.create(squery, model);
                    long time = System.currentTimeMillis();

                    try {
                        sbf.append("<div>");
                        sbf.append("<table cellspacing=7>");
                        ResultSet rs = qexec.execSelect();
                        sbf.append("<thead>");
                        sbf.append("<tr>");

                        if (rs.hasNext()) {
                            Iterator<String> itcols = rs.getResultVars().iterator();
                            while (itcols.hasNext()) {
                                sbf.append("<th>");
                                sbf.append(itcols.next());
                                sbf.append("</th>");
                            }
                            sbf.append("</tr>");
                            sbf.append("</thead>");
                            sbf.append("<tbody>");

                            boolean odd = true;
                            while (rs.hasNext()) {
                                odd = !odd;
                                QuerySolution rb = rs.nextSolution();

                                if (odd) {
                                    sbf.append("<tr bgcolor=\"#EFEDEC\">");
                                } else {
                                    sbf.append("<tr>");
                                }

                                Iterator<String> it = rs.getResultVars().iterator();
                                while (it.hasNext()) {
                                    String name = it.next();
                                    RDFNode x = rb.get(name);
                                    sbf.append("<td >");
                                    if (x != null) {
                                        if (x.isLiteral()) {

                                            sbf.append(x.asNode().getLiteral().getLexicalForm());
                                        } else {
                                            //System.out.println(">obteniendo objeto semántico");
                                            SemanticObject so = SemanticObject.createSemanticObject(x.toString());

                                            if (so != null) {
                                                //System.out.println(">objeto semántico obtenido con éxito");
                                                //SemanticClass tt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(so.getURI());
                                                //System.out.println(">Obteniendo clase semántica del objeto semántico");
                                                SemanticClass tt = so.getSemanticClass();
                                                if (tt != null) {
                                                    //System.out.println(">>Clase semántica obtenida con éxito");
                                                    sbf.append(tt.getDisplayName(lang2));
                                                } else {
                                                    //System.out.println(">>No se pudo obtener clase semántica");
                                                    //System.out.println(">>Obteniendo propiedad semántica del objeto semántico");
                                                    SemanticProperty stt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(so.getURI());
                                                    sbf.append(stt.getDisplayName(lang2));
                                                }
                                            } else {
                                                //System.out.println(">el dato no tiene objeto semántico");
                                                if (x != null) {
                                                    //System.out.println(">escribiendo valor");
                                                    sbf.append(x);
                                                } else {
                                                    //System.out.println(">el campo es nulo");
                                                    sbf.append(" - ");
                                                }
                                            }
                                        }
                                    }
                                    sbf.append("</td>");
                                }
                                sbf.append("</tr>");
                            }
                        } else {
                            sbf.append("<font color='red'>" + paramRequest.getLocaleString("nofound") + "</font>");
                            sbf.append("</tr>");
                            sbf.append("</thead>");
                        }
                        sbf.append("</tbody>");
                        sbf.append("</table>");
                        sbf.append("<p aling=\"center\">" + paramRequest.getLocaleString("exectime") + ": " + (System.currentTimeMillis() - time) + "ms." + "</p>");
                    } finally {
                        qexec.close();
                    }
                } catch (Exception e) {
                    if (tr.getErrCode() != 0) {
                        sbf.append("<script language=\"javascript\" type=\"text/javascript\">alert('" + paramRequest.getLocaleString("failmsg") + "');</script>");
                    }
                }
            } else {
                sbf.append("<script language=\"javascript\" type=\"text/javascript\">");
                sbf.append("alert(\"" + tr.getErrors().replace("\n", "\\n") + "\");");
                sbf.append("</script>");
            }
        }
        sbf.append("</div>");
        out.print(sbf.toString());
    }

    private String createId(String suffix) {
        return getResourceBase().getId() + "/" + suffix;
    }

    private String decodeUrlAccents(String str) {
        String res = "";

        res = str.replace("%F3", "ó");
        return res;
    }
}
