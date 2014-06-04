<%-- 
    Document   : testSearch
    Created on : 21-jul-2013, 2:34:39
    Author     : javier.solis.g
--%>

<%@page import="java.io.FileInputStream"%>
<%@page import="com.hp.hpl.jena.rdf.model.RDFNode"%>
<%@page import="java.util.Random"%>
<%@page import="com.hp.hpl.jena.rdf.model.Model"%>
<%@page import="com.hp.hpl.jena.graph.impl.LiteralLabelFactory"%>
<%@page import="com.hp.hpl.jena.datatypes.RDFDatatype"%>
<%@page import="com.hp.hpl.jena.graph.Node"%>
<%@page import="com.hp.hpl.jena.vocabulary.RDF"%>
<%@page import="com.hp.hpl.jena.graph.Triple"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.base.WebPageBase"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.platform.SemanticModel"%>
<%@page import="org.semanticwb.model.base.SWBContextBase"%>
<%@page import="org.semanticwb.platform.SemanticSearch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <pre>
<%

    
    Random ran=new Random();
    WebSite site=SWBContext.getWebSite("demo");
    Model model=site.getSemanticModel().getRDFModel();
    
    if(request.getParameter("load")!=null)
    {
        System.out.println("Load...");
        
        
        
        long time=System.currentTimeMillis();
        //FileManager.get().readModel( tdbModel, dbdump1, "N-TRIPLES");
        model.read(new FileInputStream("/data/bench/infoboxes-fixed.nt"),null,"N-TRIPLES");
        System.out.println("time loading infoboxes-fixed.nt:"+time);
        time=System.currentTimeMillis();
        
        model.read(new FileInputStream("/data/bench/geocoordinates-fixed.nt"),null,"N-TRIPLES");
        System.out.println("time loading geocoordinates-fixed.nt:"+time);
        time=System.currentTimeMillis();
        
        
        model.read(new FileInputStream("/data/bench/homepages-fixed.nt"),null,"N-TRIPLES");
        System.out.println("time loading homepages-fixed.nt:"+time);
        time=System.currentTimeMillis();
    }
     
%>        
        </pre>
    </body>
</html>
