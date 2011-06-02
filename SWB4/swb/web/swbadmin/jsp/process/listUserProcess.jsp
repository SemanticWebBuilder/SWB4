<%@page import="org.semanticwb.process.model.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    ProcessSite site = (ProcessSite) paramRequest.getWebPage().getWebSite();
    WebPage wp = paramRequest.getWebPage();
    String lang = user.getLanguage();
    int cuantos=0;
    int numpen=0;
%>
<h2>Procesos</h2>
<ul>
<%
    String styleclass = "";
    Iterator<Process> itpro = site.listProcesses();
    while (itpro.hasNext()) {
        numpen=0;
        styleclass = "t1";
        Process process = itpro.next();
        WebPage ppage= process.getProcessWebPage();
        Iterator<ProcessInstance> itprocins = SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
        while (itprocins.hasNext()) {
            ProcessInstance procins = itprocins.next();
            List<FlowNodeInstance> lfnins = SWBProcessMgr.getUserTaskInstances(procins, user);
            if(lfnins.size()>0)
            {
                numpen++;
                cuantos++;
            }
        }
        if(numpen>0&&ppage!=null)
        {
            if(wp.getURI().equals(ppage.getURI())) styleclass = "t2-sel";
            %>
            <li class="<%=styleclass%>"><%=process.getDisplayTitle(lang)%>(<a href="<%=ppage.getUrl()%>"><%=numpen%></a>)</li>
            <%
        }
    }
    if(cuantos==0) {
        %>
        <li class="t1">No hay tareas pendientes</li>
        <%
    }
        %>
</ul>