<%-- 
    Document   : migrationProcess
    Created on : 2/12/2011, 05:13:52 PM
    Author     : hasdai
--%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
String oldPropertyName = "http://www.semanticwebbuilder.org/swb4/process#status";
Iterator<ProcessInstance> instances = ProcessInstance.ClassMgr.listProcessInstances(paramRequest.getWebPage().getWebSite());
String _start = request.getParameter("start");

if (_start != null && _start.equals("true")) {
    //System.out.println("Iniciando migración de propiedad status");
    while(instances.hasNext()) {
        ProcessInstance pi = instances.next();
        //System.out.println("Migrando instancia " + pi.getId() + " - " + pi.getProcessType().getTitle());
        SemanticProperty status = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(oldPropertyName);
        int stat = pi.getSemanticObject().getIntProperty(status);
        //System.out.println("  La propiedad tiene valor " + stat);
        pi.setStatus(stat);
    }
}
%>
