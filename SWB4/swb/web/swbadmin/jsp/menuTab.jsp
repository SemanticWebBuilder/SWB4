<%@page contentType="text/html"%><%@page pageEncoding="ISO-8859-1"%><%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%><%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    WebPage obj=(WebPage)ont.getGenericObject(id);
    if(obj==null)return;
    //System.out.println(obj.getUrl());
    //String title=obj.getSemanticObject().getProperty(SWBContext.getVocabulary().title);

    out.println("<div id=\""+obj.getURI()+"/menu"+"\" dojoType=\"dijit.layout.ContentPane\" title=\""+obj.getDisplayName(lang)+"\" refreshOnShow=\""+"false"+"\" href=\""+obj.getUrl()+"\" _onLoad=\"alert('test');\">");
    //request.getRequestDispatcher((url+"?"+params).substring(4)).include(request, response);
    out.println("</div>");

%>
<iframe_ dojoType_="dijit.layout.ContentPane" src="<%=obj.getUrl()%>" width="100%" height="100%" frameborder="0" scrolling="yes"></iframe_>