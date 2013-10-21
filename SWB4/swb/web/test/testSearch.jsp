<%-- 
    Document   : testSearch
    Created on : 21-jul-2013, 2:34:39
    Author     : javier.solis.g
--%>

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

    
    
    WebSite site=SWBContext.getWebSite("demo");
    
    if(request.getParameter("load")!=null)
    {
        site.getSemanticModel().setTraceable(false);
        for(int x=0;x<100;x++)
        {
            WebPage wp=site.createWebPage("wpx"+x);
            wp.setParent(site.getHomePage());
            for(int y=0;y<10;y++)
            {
                WebPage wpy=site.createWebPage("wpy"+x+"_"+y);
                wpy.setParent(wp);
            }
            System.out.println(x);
        }    
        site.getSemanticModel().setTraceable(true);
    }
    
    
/*    
    long time=System.currentTimeMillis();
    Iterator<WebPage> it=new GenericIterator(SemanticSearch.listInstances(site.getSemanticModel(), WebPage.sclass));
    out.println(System.currentTimeMillis()-time);
    time=System.currentTimeMillis();
    while (it.hasNext())
    {
        out.println(it.next()+" ");    
    }
    out.println(System.currentTimeMillis()-time);
    time=System.currentTimeMillis();
    
    it=WebPage.ClassMgr.listWebPages(site);
    out.println(System.currentTimeMillis()-time);
    time=System.currentTimeMillis();
    while (it.hasNext())
    {
        out.println(it.next());    
    }
    out.println(System.currentTimeMillis()-time);
    time=System.currentTimeMillis();
*/
    ArrayList query=new ArrayList();    
    //query.add(Triple.create(Node.ANY, RDF.type.asNode(), WebPage.sclass.getOntClass().asNode()));
    //query.add(Triple.create(Node.ANY, WebPage.swb_active.getRDFProperty().asNode(), Node.createLiteral(LiteralLabelFactory.create(Boolean.valueOf(false)))));
    //query.add(Triple.create(Node.ANY, WebPage.swb_tags.getRDFProperty().asNode(), Node.createLiteral("Hola")));
    //Iterator<SemanticObject> it=SemanticSearch.search(site.getSemanticModel(), query,WebPage.sclass.getClassGroupId(),null,null,WebPage.swb_views.getRDFProperty(), true);
    Iterator<SemanticObject> it=SemanticSearch.search(site.getSemanticModel(), query,WebPage.sclass.getClassGroupId(),null,null,null, true);
    while(it!=null && it.hasNext())
    {
        SemanticObject obj=it.next();
        out.println(obj);
    }
%>        
        </pre>
    </body>
</html>
