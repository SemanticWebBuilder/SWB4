<%-- 
    Document   : acTextAreaStore
    Created on : 3/03/2009, 08:31:45 PM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.base.util.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.*"%>

<%
SortedSet objOptions = new TreeSet();
SortedSet proOptions = new TreeSet();
String word = request.getParameter("word");
String lang = request.getParameter("lang");
String tempcDn = "";
boolean lPar = false;
boolean rPar = false;

if (lang == null || lang == "") {
    lang = "es";
}
//TODO: pasar el lenguaje en la cadena de llamada
if (word.indexOf("(") != -1) {
    lPar = true;
    word = word.replace("(", "");
}
if (word.indexOf(")") != -1) {
    rPar = true;
    word = word.replace(")", "");
}
word = word.trim();

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
    int idCounter = 0;
    int index;
    Iterator<String> rit = objOptions.iterator();

    out.println("<ul id=\"resultlist\" class=\"resultlist\" style=\"list-style-type:none;" +
                    "position:absolute;margin:0;padding:0;overflow:auto;height:300px;max-height:" +
                    "200px;width:300px;border:1px solid #a0a0ff;\">");
    while (rit.hasNext()) {
        String tempi = (String) rit.next();
        index = tempi.toLowerCase().indexOf(word.toLowerCase());

        out.print("<li id=\"id"+ idCounter + "\" class=\"resultEntry\" " +
                "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                "highLightSelection("+ idCounter +",true); curSelected = "+ idCounter +";console.log('curSelected: '+" + idCounter +");\" " +
                "onmouseout=\"highLightSelection("+ idCounter +",false);\" "+
                "onmousedown=\"setSelection("+ idCounter +");dojo.byId('results').innerHTML='';"+
                "dojo.byId('queryText').focus();displayed=false;\">" + tempi + "</li>");
        idCounter++;
    }

    rit = proOptions.iterator();
    while (rit.hasNext()) {
        String tempi = (String) rit.next();
        index = tempi.toLowerCase().indexOf(word.toLowerCase());

        out.print("<li id=\"id"+ idCounter + "\" class=\"resultEntry\" " +
                "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                "highLightSelection("+ idCounter +",true); curSelected = "+ idCounter +";console.log('curSelected: '+" + idCounter +");\" " +
                "onmouseout=\"highLightSelection("+ idCounter +",false);\" "+
                "onmousedown=\"setSelection("+ idCounter +");dojo.byId('results').innerHTML='';"+
                "dojo.byId('queryText').focus();displayed=false;\">" + tempi + "</li>");
        idCounter++;
    }
    out.println("</ul>");
}
%>