<%-- 
    Document   : processIndicator
    Created on : 13/10/2011, 05:12:15 PM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>

<%!
private String getProcessStatus(ProcessInstance pInstance, int wdays, int adays) {
    String ret = "STATUS_OK";
    //System.out.println(pInstance.getProcessType().getTitle());
    Iterator<FlowNodeInstance> fnInstances = pInstance.listAllFlowNodeInstance();
    while(fnInstances.hasNext()) {
        FlowNodeInstance fnInstance = fnInstances.next();
        if (fnInstance.getFlowNodeType() instanceof UserTask) {
            Calendar c1 = GregorianCalendar.getInstance();
            Calendar c2 = GregorianCalendar.getInstance();
            
            c1.setTime(fnInstance.getCreated());
            //c1.set(2011, 10, 1);
            c2.setTimeInMillis(System.currentTimeMillis());
            long diff = c2.getTimeInMillis() - c1.getTimeInMillis();
            //System.out.println(pInstance.getProcessType().getTitle()+"--diferencia: " + diff/(24*60*60*1000));
            if ((diff/(24*60*60*1000)) > wdays) {
                ret = "STATUS_WARNING";
            }
            if ((diff/(24*60*60*1000)) > adays) {
                ret = "STATUS_ALERT";
            }
        }
    }
    return ret;
}

%>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
//String iStatus = (String) request.getAttribute("iStatus");
//String pGroup = (String) request.getAttribute("pGroup");
User user = paramRequest.getUser();
//Map args = (Map) request.getAttribute("args");
Map args = (Map) paramRequest.getArguments();
String iStatus = "STATUS_UNDEFINED";
String pgId = "";
String label = "";
String lang = "es";
String statusClass = ""; 
ProcessGroup pGroup = null;
int activeInstances = 0;
int closedInstances = 0;
int abortedInstances = 0;
int totalInstances = 0;
int wdays = 15;
int adays = 30;

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
    try {
        adays = Integer.parseInt(_adays);
        wdays = Integer.parseInt(_wdays);
    } catch (NumberFormatException e) {
        System.out.println("ProcessIndicator: Error al convertir a entero"+e);
    }
    //System.out.println("adays: "+adays+", wdays: "+wdays);
    
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
                iStatus = getProcessStatus(processInstance, wdays, adays);
                //System.out.println("--"+getProcessStatus(processInstance, wdays, adays));
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
    <li <%=statusClass%>><a title="<%=activeInstances%> en proceso, <%=closedInstances%> terminados, <%=abortedInstances%> abortados, TOTAL: <%=totalInstances%>" href="/es/<%=paramRequest.getWebPage().getWebSiteId()%>/Monitoreo?gFilter=<%=pGroup.getId()%>&sFilter=1"><%=label%> (<%=activeInstances%>)</a></li>
    <%
}
%>