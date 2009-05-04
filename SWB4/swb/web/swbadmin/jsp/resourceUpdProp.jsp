<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,java.util.*,org.semanticwb.base.util.*,com.hp.hpl.jena.ontology.*,com.hp.hpl.jena.rdf.model.*"%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String suri=request.getParameter("suri");
    String prop=request.getParameter("prop");
    String hash=request.getParameter("hash");
    String val=request.getParameter("val");
    SemanticOntology ont=(SemanticOntology)session.getAttribute("ontology");
    OntResource res=ont.getRDFOntModel().getOntResource(suri);
    OntProperty p=ont.getRDFOntModel().getOntProperty(prop);
    res.setComment(val, "es");

    System.out.println("suri:"+suri);
    System.out.println("prop:"+prop);
    System.out.println("hash:s"+hash);
    System.out.println("val:"+val);
%>
OK