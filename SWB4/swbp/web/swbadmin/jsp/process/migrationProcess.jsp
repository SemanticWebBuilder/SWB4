<%-- 
    Document   : migrationProcess
    Created on : 2/12/2011, 05:13:52 PM
    Author     : hasdai
--%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
WebSite site = paramRequest.getWebPage().getWebSite();
String oldPropertyName = "http://www.semanticwebbuilder.org/swb4/process#status";
Iterator<ProcessInstance> instances = ProcessInstance.ClassMgr.listProcessInstances(paramRequest.getWebPage().getWebSite());
String _start = request.getParameter("start");

if (_start != null && _start.equals("true")) {
    System.out.println("Iniciando migración de sitio Web " + paramRequest.getWebPage().getWebSite().getTitle());
    System.out.println("Eliminando páginas web de procesos...");
    Iterator<WebPage> pwps = site.listWebPages();
    while (pwps.hasNext())  {
        WebPage pwp = pwps.next();
        //if (pwp instanceof ProcessWebPage) {
        //    site.removeWebPage(pwp.getId());
        //}
    }
    System.out.println("Se eliminaron las páginas web de procesos.");
         
    System.out.println("Cambiando la propiedad status de los procesos...");
    while(instances.hasNext()) {
        ProcessInstance pi = instances.next();
        //System.out.println("Migrando instancia " + pi.getId() + " - " + pi.getProcessType().getTitle());
        SemanticProperty status = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(oldPropertyName);
        int stat = pi.getSemanticObject().getIntProperty(status);
        //System.out.println("  La propiedad tiene valor " + stat);
        pi.setStatus(stat);
    }
    System.out.println("Cambiada la propiedad status de los procesos.");
}
SWBResourceURL startUrl = paramRequest.getRenderUrl();
%>

<h1>Migración de sitio de procesos</h1>
<p>Presione el siguiente bot&oacute;n para iniciar el proceso.</p>
<form action="<%=startUrl%>" method="get">
    <input type="hidden" name="start" value="true"/>
    <input type="submit" value="Iniciar"/>
</form>