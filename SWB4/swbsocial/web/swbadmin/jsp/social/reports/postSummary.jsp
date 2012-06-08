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
        out.println(" dojo.require('dijit.layout.ContentPane');");
        out.println(" dojo.require('dijit.dijit');");
        out.println(" dojo.require('dojox.grid.DataGrid');");
        out.println(" dojo.require('dojo.data.ItemFileReadStore');");
        out.println(" dojo.require('dojo.parser');");
        
        out.println(" function fillGrid(grid, uri) {");
        out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri+'?ipage="+ipage+"'});");
        out.println("   grid._refresh();");
        out.println(" }");
        
out.println("function x_(value) {");
out.println(" if(value==2)");
out.println("  return '/work/positivo.jpg';");
out.println(" else if(value==1)");
out.println("  return '/work/negativo.jpg';");
out.println(" else");
out.println("  return '/work/neutro.jpg';");
out.println("}");
        
        out.println(" function apply() {");
        out.println("   var grid = dijit.byId('gridMaster');");
        out.println("   fillGrid(grid, '"+url.setMode(Mode_JSON)+"');");
        out.println("  ");
        out.println(" }");
        
        out.println(" var layout= null;");
        out.println(" var jStrMaster = null;");
        out.println(" var gridMaster = null;");
        out.println(" var gridResources = null;");

        out.println(" dojo.addOnLoad(function() {");
        out.println("   layout= [");
        out.println("      { field:'fl',  width:'50px', name:'Num' },");
        out.println("      { field:'cta', width:'50px', name:'Cuenta' },");
        out.println("      { field:'sn',  width:'100px',name:'Red social' },");
        out.println("      { field:'date',width:'150px',name:'Fecha' },");
        out.println("      { field:'msg', width:'300px',name:'Mensaje' },");
        out.println("      { field:'feel',width:'80px', name:'Sentimiento', formatter:function(value){var src=x_(value);return '<img src=\"'+src+'\" />';} },");
        out.println("      { field:'int', width:'50px', name:'Intensidad' },");
        out.println("      { field:'user',width:'100px',name:'Usuario' },");
        out.println("      { field:'fllwrs', width:'80px', name:'Seguidores' },");
        out.println("      { field:'frds',width: '50px',name:'Amigos' }");
        out.println("   ];");

        out.println("   gridMaster = new dojox.grid.DataGrid({");
        out.println("      escapeHTMLInData: 'true',");     
        out.println("      preload: 'true',");     
        out.println("      id: 'gridMaster',");
        out.println("      structure: layout,");
        out.println("      rowSelector: '10px',");
        out.println("      rowsPerPage: '25'");
        out.println("   }, 'gridMaster');");
        out.println("   gridMaster.startup();");
        
        out.println("   fillGrid(gridMaster, '"+url.setMode(Mode_JSON)+"');");
        out.println(" });");
        out.println("</script>");
        out.println("<div id=\"ctnergrid\" style=\"height:600px; width:99%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
        out.println("   <div id=\"gridMaster\"></div>");
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