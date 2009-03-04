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
String start = request.getParameter("start");
String end = request.getParameter("end");
String tempcDn = "";
boolean lPar = false;
boolean rPar = false;

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
    tempcDn = tempc.getDisplayName("es");

    if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
        objOptions.add(tempcDn);
    }

    Iterator<SemanticProperty> sit = tempc.listProperties();
    while (sit.hasNext()) {
        SemanticProperty tempp = sit.next();
        tempcDn = tempp.getDisplayName("es");

        if (tempcDn.toLowerCase().indexOf(word.toLowerCase()) != -1) {
            proOptions.add(tempcDn);
        }
    }
}

if (proOptions.size() != 0 || objOptions.size() != 0) {
    int idCounter = 0;
    int index;
    Iterator<String> rit = objOptions.iterator();

    out.println("<ul id=\"suggestionsList\" class=\"resultlist\">");
    while (rit.hasNext()) {
        String tempi = (String) rit.next();
        index = tempi.toLowerCase().indexOf(word.toLowerCase());

        out.print("<li id=\"id"+ idCounter + "\" class=\"link\" " +
                "onmouseover=\"dojo.style(dojo.byId('id"+idCounter+"'), 'background', 'LightBlue');\" " +
                "onmouseout=\"dojo.style(dojo.byId('id"+idCounter+"'), 'background', 'white');\" " +
                "onmousedown=\"replaceText(dojo.byId('query'),"+
                    start + "," + end + "," + "\'" + (lPar?"(":"") + tempi +
                    (rPar?")":"") + "\'" +"); displayed=false; dojo.byId('query').focus();curSelected=0;\">" +
                    tempi.substring(0, index) + "<font color=\"blue\">" +
                    tempi.substring(index, index + word.length()) + "</font>" +
                    tempi.substring(index + word.length(), tempi.length()) + "</li>");
        idCounter++;
    }

    rit = proOptions.iterator();
    while (rit.hasNext()) {
        String tempi = (String) rit.next();
        index = tempi.toLowerCase().indexOf(word.toLowerCase());

        out.print("<li id=\"id"+ idCounter + "\" class=\"link\" " +
                "onmouseover=\"dojo.style(dojo.byId('id"+idCounter+"'), 'background', 'LightBlue');\" " +
                "onmouseout=\"dojo.style(dojo.byId('id"+idCounter+"'), 'background', 'white');\" " +
                "onmousedown=\"replaceText(dojo.byId('query'),"+
                    start + "," + end + "," + "\'" + (lPar?"(":"") + tempi + (rPar?")":"") + "\'" +"); displayed=false; dojo.byId('query').focus();curSelected=0;\">" +
                    tempi.substring(0, index) + "<font color=\"blue\">" +
                    tempi.substring(index, index + word.length()) + "</font>" +
                    tempi.substring(index + word.length(), tempi.length()) + "</li>");
        idCounter++;
    }
    out.println("</ul>");
}
%>