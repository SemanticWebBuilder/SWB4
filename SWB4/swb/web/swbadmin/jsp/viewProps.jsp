<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*"%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("id");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(id);
    GenericObject gen=obj.createGenericInstance();
    SemanticClass cls=obj.getSemanticClass();
    String title=obj.getDisplayName(lang);
    String url=null;
    if(gen instanceof WebPage)url=((WebPage)gen).getUrl();
    String description=obj.getProperty(Descriptiveable.swb_description);
    String active=obj.getProperty(Activeable.swb_active);
    String sortname=obj.getProperty(WebPage.swb_webPageSortName);
%>
<table>
    <!--<caption>Propiedades</caption>-->
    <tr><th>Propiedad</th><th>Valor</th></tr>
<%
    if(url!=null)
    {
%>
        <tr><td>Id</td><td><a href="<%=url%>" onclick="dojo.byId('swbPreviewFrame').src='<%=url%>';dijit.byId('tabs').selectChild(dijit.byId('swbPreviewTab'));return false;"><%=obj.getId()%></a></td></tr>
<%
    }else
    {
%>
        <tr><td>Id</td><td><%=obj.getId()%></td></tr>
<%
    }
%>
    <tr><td>Class</td><td><%=cls.getName()%></td></tr>
    <tr><td><%=cls.getDisplayNameProperty().getDisplayName(lang)%></td><td><%=title%></td></tr>
    <%if(sortname!=null){%><tr><td><%=WebPage.swb_webPageSortName.getDisplayName(lang)%></td><td><%=sortname%></td></tr><%}%>
    <%if(active!=null){%><tr><td><%=Activeable.swb_active.getDisplayName(lang)%></td><td><%=active%></td></tr><%}%>
    <%if(description!=null){%><tr><td><%=Descriptiveable.swb_description.getDisplayName(lang)%></td><td><%=description%></td></tr><%}%>
</table>