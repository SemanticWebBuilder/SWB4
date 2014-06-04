<%-- 
    Document   : sparqlquery
    Created on : Apr 3, 2014, 7:03:40 AM
    Author     : javier.solis.g
--%>
<%@page import="com.hp.hpl.jena.rdf.model.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.hp.hpl.jena.query.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>        
        <h1>SparQL Query</h1>
        <H2>Query</H2>
        <form action="sparqlquery.jsp" method="post">
            <select name="model">
<%
    String smodel=request.getParameter("model");
    if(smodel==null)smodel="demo";
    {
        Iterator<SWBModel> it=SWBContext.listSWBModels(false);
        String selected="";
        while(it.hasNext())
        {
            SWBModel model=it.next();
            if(model.getId().equals(smodel))selected="selected";else selected="";
%>                
                <option value="<%=model.getId()%>" <%=selected%>><%=model.getId()%></option>
<%
        }
    }
%>                
            </select>
            <br/>
            <textarea id="sparqlquery" name="sparqlquery" rows="15" cols="111">
<%
    String query=request.getParameter("sparqlquery");
    out.println(query);
%>
            </textarea>
            <br/>
            <input type="submit" value="Query"/>
        </form>        
<%
    try
    {
        SWBModel model=SWBContext.getSWBModel(smodel);
        
        long time=System.currentTimeMillis();
        out.println("<table border='1'>");
        
        
        //QueryExecution qe=new SWBQueryExecution(site.getSemanticModel().getRDFModel(), query);
        QueryExecution qe=model.getSemanticModel().sparQLQuery(query);
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
                RDFNode node=qs.get(name);
                String val="";
                if(node!=null&&node.isLiteral())val=node.asLiteral().getLexicalForm();
                else if(node!=null&&node.isResource())val=node.asResource().getURI();
                out.println(val);
                out.println("</td>");
            }
            out.println("</tr>");
        }
        out.println("</table>");
                
        out.println("Time:"+(System.currentTimeMillis()-time));
        
    }catch(Exception e)
    {
        e.printStackTrace();
    }
%>        

    </body>
</html>
