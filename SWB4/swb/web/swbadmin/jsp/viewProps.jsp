<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*"%>
<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("id");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    com.hp.hpl.jena.rdf.model.Resource res=ont.getResource(id);
    if(res==null)return;
    SemanticClass cls=ont.getSemanticObjectClass(res);
    GenericObject obj=ont.getGenericObject(id,cls);
    String title=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().title);
    String description=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().description);
    String active=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().active);
    String sortname=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().webPageSortName);
%>
<table class="admViewProperties">
    <caption>Propiedades</caption>
    <tr><th>Propiedad</th><th>Valor</th></tr>
    <tr><td>Id</td><td><%=obj.getId()%></td></tr>
    <tr><td>Title</td><td><%=title%></td></tr>
    <%if(sortname!=null){%><tr><td>SortName</td><td><%=sortname%></td></tr><%}%>
    <%if(active!=null){%><tr><td>Active</td><td><%=active%></td></tr><%}%>
    <tr><td>Type</td><td><%=cls.getName()%></td></tr>
    <%if(description!=null){%><tr><td>Desc</td><td><%=description%></td></tr><%}%>
    
</table>