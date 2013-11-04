<%-- 
    Document   : xx
    Created on : 02-nov-2013, 15:58:44
    Author     : javier.solis.g
--%>

<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.io.File"%>
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
            WebSite model=SWBContext.getWebSite("demo");
            SemanticObject sobj=model.getHomePage().getSemanticObject();            
            
            String turi=sobj.getURI()+"_v2";
            turi=turi.substring(turi.lastIndexOf(':')+1);
            
            File of = new File(SWBPortal.getWorkPath() + sobj.getWorkPath());
            String op=of.getCanonicalPath();
            String np=of.getParentFile().getCanonicalPath() + "/"+turi;
            
            out.println("op:"+op);
            out.println("np:"+np);
            
            if (of.exists())
            {
                out.println("clone");
                SWBUtils.IO.copyStructure(op+"/", np+"/");
            }

        %>        
    </body>
</html>
