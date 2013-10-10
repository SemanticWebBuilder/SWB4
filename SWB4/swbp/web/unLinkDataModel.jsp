<%-- 
    Document   : removeDataModel
    Created on : 10-oct-2013, 13:39:16
    Author     : javier.solis.g
--%>

<%@page import="org.semanticwb.process.model.ProcessDataInstanceModel"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.model.base.SWBContextBase"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Desvincular Data Model...</h1>
<%
    String name=request.getParameter("name");
    if(name!=null)
    {
        ProcessSite site=(ProcessSite)SWBContext.getWebSite(name);

        ProcessDataInstanceModel ret=site.getProcessDataInstanceModel();

        if(ret!=null)
        {
            site.setProcessDataInstanceModel(null);
            site.removeSubModel(ret);
            out.println("Desvinculado...");
        }   
    }
%>        
    </body>
</html>
