<%-- 
    Document   : testsparql
    Created on : 13-sep-2013, 11:54:22
    Author     : javier.solis.g
--%>

<%@page import="com.hp.hpl.jena.query.QueryExecution"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.hp.hpl.jena.query.QuerySolution"%>
<%@page import="com.hp.hpl.jena.query.ResultSet"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.base.SWBContextBase"%>
<%@page import="org.semanticwb.rdf.sparql.SWBQueryExecution"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
<%
    String query=request.getParameter("query");
    if(query==null)
    {
          query=
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX prop: <http://www.swb.com/prop#>\n" +
                    "PREFIX onto: <http://www.swb.com/ontology#>\n" +
                    "\n" +
//                    "select ?nombre ?telefono ?estado ?nempresa ?telempresa\n" +
//                    "select (count(?s) as ?c)\n" +
                    "select *\n" +
                    "where {\n" +
                    "  ?s prop:nombre ?nombre.\n" +
                    "  ?s a onto:Persona.\n" +
                    "  ?s prop:telefono ?telefono.\n" +
                    "  ?s prop:direccion ?direccion.\n" +
                    "  ?direccion prop:estado ?estado.\n" +
                    "  OPTIONAL {\n" +
                    "     ?s prop:empresa ?empresa .\n" +
                    "     ?empresa prop:nombre ?nempresa .\n" +
                    "     ?empresa prop:fake ?fake. \n" +
                    "     ?empresa prop:telefono ?telempresa.\n" +
                    "  }\n" +
                    "} \n" +
                    "ORDER BY desc(?nombre) \n" +
//                    "ORDER BY DESC (?telefono) ?nombre \n" +
                    "OFFSET 10 \n" +
                    "LIMIT 100";   
//                    "";        
    }
    
    
%>        
        <form>
            Query:<br>
            <textarea rows="20" cols="80" name="query"><%=query%></textarea><br>
            <input type="submit">
        </form>
        
        <table border="1">
<%
    try
    {
        WebSite site=SWBContext.getWebSite("demo");
        
        long time=System.currentTimeMillis();
        //QueryExecution qe=new SWBQueryExecution(site.getSemanticModel().getRDFModel(), query);
        QueryExecution qe=site.getSemanticModel().sparQLQuery(query);
        ResultSet rs=qe.execSelect();
        
        {
            out.println("<tr>");
            Iterator<String> it=rs.getResultVars().iterator();
            while(it.hasNext())
            {
                String name=it.next();
                out.println("<th>");
                out.println(name);
                out.println("</th>");
            }
            out.println("</tr>");                
        }
        
        while(rs.hasNext())
        {
            QuerySolution qs=rs.next();
            out.println("<tr>");
            Iterator<String> it=rs.getResultVars().iterator();
            while(it.hasNext())
            {
                String name=it.next();
                out.println("<td>");
                out.println(qs.get(name));
                out.println("</td>");
            }
            out.println("</tr>");
        }
        out.println("Time:"+(System.currentTimeMillis()-time));
        
    }catch(Exception e)
    {
        e.printStackTrace();
    }
        
        
%>        
        </table>
    </body>
</html>
