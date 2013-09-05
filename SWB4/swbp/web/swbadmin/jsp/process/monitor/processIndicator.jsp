<%-- 
    Document   : processIndicator
    Created on : 13/10/2011, 05:12:15 PM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.ProcessWebPage"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>

<%!
public static final int WARNING_DAYS=15;
public static final int ALERT_DAYS=30;
%>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
//String iStatus = (String) request.getAttribute("iStatus");
//String pGroup = (String) request.getAttribute("pGroup");
User user = paramRequest.getUser();
Map args = (Map) request.getAttribute("args");
String iStatus = "STATUS_UNDEFINED";
String pgId = "";
String label = "";
String lang = "es";
String statusClass = "";
int wdays = 15;
int adays = 30;
ProcessGroup pGroup = null;
int activeInstances = 0;
int closedInstances = 0;
int abortedInstances = 0;
int totalInstances = 0;

if (user.getLanguage() != null) {
    lang = user.getLanguage();
}

if (args != null) {
    pgId = (String) args.get("pgroup");
    if (pgId != null && !pgId.trim().equals("")) {
        pGroup = ProcessGroup.ClassMgr.getProcessGroup(pgId, paramRequest.getWebPage().getWebSite());
    }
    String _adays = (String) args.get("adays");
    String _wdays = (String) args.get("wdays");
    System.out.println("adays:"+ _adays+", wdays"+_wdays);
    
    label = (String) args.get("label");
}

if (pGroup != null) {
    if(label == null || label.trim().equals("")) {
        label = pGroup.getDisplayTitle(lang);
    }
    
    Iterator<Process> processes = pGroup.listProcesses();
    while (processes.hasNext()) {
        Process process = processes.next();
        Iterator<ProcessInstance> instances = process.listProcessInstances();
        while (instances.hasNext()) {
            ProcessInstance processInstance = instances.next();
            totalInstances++;
            int pIStatus = processInstance.getStatus();
            if (pIStatus == ProcessInstance.STATUS_PROCESSING || pIStatus == ProcessInstance.STATUS_OPEN) {
                activeInstances++;
                iStatus = "STATUS_OK";
            } else if (pIStatus == ProcessInstance.STATUS_CLOSED) {
                iStatus = "STATUS_OK";
                closedInstances++;
            } else if (pIStatus == ProcessInstance.STATUS_ABORTED) {
                abortedInstances++;
            }
        }
    }
    
    if (iStatus.equals("STATUS_OK")) {
        statusClass = "class=\"statusOk\"";
    } else if (iStatus.equals("STATUS_WARNING")) {
        statusClass = "class=\"statusWarning\"";
    } else if (iStatus.equals("STATUS_ALERT")) {
        statusClass = "class=\"statusAlert\"";
    } else if (iStatus.equals("STATUS_UNDEFINED")) {
        statusClass = "class=\"statusUndefined\"";
    }
    
    %>
    <li <%=statusClass%>><a href="/es/CFE/Monitoreo?gFilter=<%=pGroup.getId()%>&sFilter=1"><%=label%> (<%=activeInstances%>)</a></li>
    <%
}
%>