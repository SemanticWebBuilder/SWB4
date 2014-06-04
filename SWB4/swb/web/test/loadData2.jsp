<%-- 
    Document   : testSearch
    Created on : 21-jul-2013, 2:34:39
    Author     : javier.solis.g
--%>

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
        int pers=100000;
        int dirs=pers/4;
        int emps=1000;
        //model.getGraph().delete(new Triple(Node.ANY, Node.createURI("http://www.swb.com/prop#direccion"), Node.ANY));
        //model.getGraph().delete(new Triple(Node.ANY, Node.createURI("http://www.swb.com/prop#empresa"), Node.ANY));
        
        for(int d=0;d<dirs;d++)
        {
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), RDF.type, model.createResource("http://www.swb.com/ontology#Direccion")));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), model.createProperty("http://www.swb.com/prop#id"), ""+d));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), model.createProperty("http://www.swb.com/prop#calle"), "calle "+ran.nextInt(300)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), model.createProperty("http://www.swb.com/prop#colonia"), "colonia "+ran.nextInt(100)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), model.createProperty("http://www.swb.com/prop#delegacion"), "delegacion "+ran.nextInt(50)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), model.createProperty("http://www.swb.com/prop#estado"), "estado "+ran.nextInt(30)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), model.createProperty("http://www.swb.com/prop#pais"), "pais "+ran.nextInt(10)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/direccion:"+d), model.createProperty("http://www.swb.com/prop#cp"), "cp "+ran.nextInt(200)));
            if(d%100==0)System.out.print("x:"+d+"\r");
        }
        System.out.println("End Dirs...");
        
        for(int d=0;d<emps;d++)
        {
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/empresa:"+d), RDF.type, model.createResource("http://www.swb.com/ontology#Empresa")));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/empresa:"+d), model.createProperty("http://www.swb.com/prop#nombre"), "nombre "+d));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/empresa:"+d), model.createProperty("http://www.swb.com/prop#direccion"), model.createResource("http://www.swb.com/inst/direccion:"+ran.nextInt(dirs))));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/empresa:"+d), model.createProperty("http://www.swb.com/prop#telefono"), "telefono "+ran.nextInt(emps)));
            if(d%100==0)System.out.print("x:"+d+"\r");
        }
        System.out.println("End Emps...");
        
        for(int d=0;d<pers;d++)
        {
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/persona:"+d), RDF.type, model.createResource("http://www.swb.com/ontology#Persona")));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/persona:"+d), model.createProperty("http://www.swb.com/prop#nombre"), "nombre "+d));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/persona:"+d), model.createProperty("http://www.swb.com/prop#direccion"), model.createResource("http://www.swb.com/inst/direccion:"+ran.nextInt(dirs))));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/persona:"+d), model.createProperty("http://www.swb.com/prop#telefono"), "telefono "+ran.nextInt(pers)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/persona:"+d), model.createProperty("http://www.swb.com/prop#nss"), "nss "+ran.nextInt(pers)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/persona:"+d), model.createProperty("http://www.swb.com/prop#rfc"), "rfc "+ran.nextInt(pers)));
            model.add(model.createStatement(model.createResource("http://www.swb.com/inst/persona:"+d), model.createProperty("http://www.swb.com/prop#empresa"), model.createResource("http://www.swb.com/inst/empresa:"+ran.nextInt(emps))));
            if(d%100==0)System.out.print("x:"+d+"\r");
        }                
        System.out.println("End Pers...");
 
    }else
    {
        /*
        ArrayList query=new ArrayList();    
        //query.add(Triple.create(Node.ANY, RDF.type.asNode(), WebPage.sclass.getOntClass().asNode()));
        //query.add(Triple.create(Node.ANY, WebPage.swb_active.getRDFProperty().asNode(), Node.createLiteral(LiteralLabelFactory.create(Boolean.valueOf(false)))));
        //query.add(Triple.create(Node.ANY, WebPage.swb_tags.getRDFProperty().asNode(), Node.createLiteral("Hola")));
        //Iterator<SemanticObject> it=SemanticSearch.search(site.getSemanticModel(), query,WebPage.sclass.getClassGroupId(),null,null,WebPage.swb_views.getRDFProperty(), true);
        Iterator<SemanticObject> it=SemanticSearch.search(site.getSemanticModel(), query, null, null, null, null, true);
        while(it!=null && it.hasNext())
        {
            SemanticObject obj=it.next();
            out.println(obj);
        }
        */
    }
     
%>        
        </pre>
    </body>
</html>
