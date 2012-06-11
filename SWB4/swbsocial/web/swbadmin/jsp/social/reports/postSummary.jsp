<%-- 
    Document   : postSummary
    Created on : 12-mar-2012, 20:41:26
    Author     : carlos.ramos
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="static org.semanticwb.social.resources.reports.PostSummary.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    WebSite wsite = paramRequest.getWebPage().getWebSite();
    
//////////////////////
int ipage;
try {
    ipage = Integer.parseInt(request.getParameter("ipage"));
}catch (NumberFormatException nfe) {
    ipage = 1;
}
Iterator<PostIn> itposts = PostIn.ClassMgr.listPostIns(wsite);
long el = SWBUtils.Collections.sizeOf(itposts);
long paginas = el / PAGE_SIZE;
if(el % PAGE_SIZE != 0) {
    paginas++;
}
long inicio = 0;
long fin = PAGE_SIZE;

inicio = (ipage * PAGE_SIZE) - PAGE_SIZE;
fin = (ipage * PAGE_SIZE);

if(ipage < 1 || ipage > paginas) {
    ipage = 1;
}
if(inicio < 0) {
    inicio = 0;
}
if(fin < 0) {
    fin = PAGE_SIZE;
}
if(fin > el) {
    fin = el;
}
if(inicio > fin) {
    inicio = 0;
    fin = PAGE_SIZE;
}
if(fin - inicio > PAGE_SIZE) {
    inicio = 0;
    fin = PAGE_SIZE;
}
inicio++;
//////////////////////


        SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
        out.println("<script type=\"text/javascript\">");
        //out.println(" dojo.require('dijit.dijit');");
        out.println(" dojo.require('dojox.grid.DataGrid');");
        out.println(" dojo.require('dojo.data.ItemFileReadStore');");
        //out.println(" dojo.require('dojo.parser');");
out.println("dojox.grid.cells.dijit");
        
        out.println(" function fillGrid(grid, uri) {");
        out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri+'?ipage="+ipage+"'});");
        out.println("   grid._refresh();");
        out.println(" }");
        
out.println("function feelingIcon(value) {");
out.println(" if(value==2)");
out.println("  return '<img src=\"/swbadmin/images/feelneg.png\" />';");
out.println(" else if(value==1)");
out.println("  return '<img src=\"/swbadmin/images/feelpos.png\" />';");
out.println(" else");
out.println("  return '--';");
out.println("}");

out.println("function emotIcon(value) {");
out.println(" if(value==2) {");
out.println("  return '<img src=\"/swbadmin/images/emoneg.png\" />';");
out.println(" }else if(value==1) {");
out.println("  return '<img src=\"/swbadmin/images/emopos.png\" />';");
out.println(" }else {");
out.println("  return '--';");
out.println(" }");
out.println("}");
out.println("formatNumber = function(value) {");
out.println(" return isNaN(value)?'...':dojo.number.format(value, {pattern:\"#,###\"});");
out.println("}");
        
        out.println(" var layout= null;");
        out.println(" var jStrMaster = null;");
        out.println(" var gridMaster = null;");
        out.println(" var gridResources = null;");

        out.println(" dojo.addOnLoad(function() {");
        out.println("   layout= [");
        out.println("      { field:'fl',  width:'5%', name:'Num' },");
        out.println("      { field:'cta', width:'9%', name:'Cuenta', editable:'true' },");
        out.println("      { field:'sn',  width:'5%',name:'Red social' },");
        out.println("      { field:'date',width:'9%',name:'Fecha' },");
        out.println("      { field:'msg', width:'37%',name:'Mensaje' },");
        out.println("      { field:'feel',width:'5%', name:'Sentimiento', formatter:function(value){var artf=feelingIcon(value);return artf;} },");
        out.println("      { field:'eicon', width:'5%', name:'Emoticon', formatter:function(value){var artf=emotIcon(value);return artf;} },");
        out.println("      { field:'int', width:'5%', name:'Intensidad' },");
        out.println("      { field:'user',width:'9%',name:'Usuario' },");
        out.println("      { field:'fllwrs', width:'5%', name:'Seguidores'},");
        out.println("      { field:'frds',width: '5%',name:'Amigos' }");
        out.println("   ];");

        out.println("   gridMaster = new dojox.grid.DataGrid({");
        //out.println("      escapeHTMLInData: 'true',");     
        //out.println("      preload: 'true',");
        out.println("      id: 'gridMaster',");
        out.println("      structure: layout,");
        out.println("      rowSelector: '10px',");
        out.println("      rowsPerPage: '25'");
        out.println("   }, 'gridMaster');");
        out.println("   gridMaster.startup();");
        
        out.println("   fillGrid(gridMaster, '"+url.setMode(Mode_JSON)+"');");
        out.println(" });");
        out.println("</script>");
        out.println("<div id=\"ctnergrid\" style=\"height:598px; width:99%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
        out.println("  <p>Total: "+el+"</p>");
        out.println("  <div id=\"gridMaster\"></div>");
        out.println("  <p>Total: "+el+"</p>");
        out.println("</div>");
        
// paginación
if(paginas > 1) {
    SWBResourceURL pagURL = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW);
    StringBuilder html = new StringBuilder();
    html.append("<div class=\"\"  style=\"text-align:center\">");
    String nextURL = "#";
    String previusURL = "#";
    if (ipage < paginas) {
        nextURL = paramRequest.getRenderUrl().setParameter("ipage", Integer.toString(ipage+1)).toString();
    }
    if (ipage > 1) {
        previusURL = paramRequest.getRenderUrl().setParameter("ipage", Integer.toString(ipage-1)).toString();
    }
    if (ipage > 1) {
        html.append("<a class=\"amfr-pagi\" href=\""+previusURL+"\">Anterior</a>&nbsp;");
    }
    for (int i=ipage-2<=0?1:ipage-2; i<=ipage+2; i++)
    {
        if(i==ipage)
            html.append("<strong>");
        else
            html.append("<a class=\"amfr-pagi\" href=\""+pagURL.setParameter("ipage", Integer.toString(i))+"\">");
        html.append(i);
        if(i==ipage)
            html.append("</strong>&nbsp;");
        else
            html.append("</a>&nbsp;");
    }
    if (ipage != paginas)
    {
        html.append("<a class=\"amfr-pagi\" href=\""+nextURL+"\">Siguiente</a>");
    }
    html.append("</div>");
    out.println(html);
}
%>