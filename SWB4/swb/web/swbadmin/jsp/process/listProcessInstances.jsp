<%@page import="org.semanticwb.process.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public void printActivityInstance(FlowObjectInstance ai, JspWriter out) throws IOException
    {
        out.println("<li>");
        out.println("Activity: "+ai.getFlowObjectType().getTitle()+" "+ai.getId());
        out.println("Status:"+ai.getStatus());
        out.println("</li>");
        if(ai instanceof ProcessInstance)
        {
            ProcessInstance pi=(ProcessInstance)ai;
            Iterator<FlowObjectInstance> acit=pi.listFlowObjectInstances();
            if(acit.hasNext())
            {
                out.println("<ul>");
                while(acit.hasNext())
                {
                    FlowObjectInstance actinst =  acit.next();
                    printActivityInstance(actinst,out);
                }
                out.println("</ul>");
            }
        }
    }
%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage topic=paramRequest.getWebPage();
    ProcessSite site=(ProcessSite)paramRequest.getWebPage().getWebSite();
    String url=paramRequest.getRenderUrl().setParameter("act", "cpi").toString();
    org.semanticwb.process.Process process=SWBProcessMgr.getProcess(topic);
%>
    <h2>Instancias del Proceso <%=process.getDisplayTitle(user.getLanguage())%></h2>
<%
    Iterator<ProcessInstance> it=SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
    while(it.hasNext())
    {
        ProcessInstance pi =  it.next();
%>
        <h3><%=pi.getFlowObjectType().getTitle()%> <%=pi.getId()%></h3>
        <h3>Tareas del usuario (<%=user.getFullName()%>)</h3>
<%
            out.println("<ul>");
            Iterator<FlowObjectInstance> utkit=SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
            while(utkit.hasNext())
            {
                FlowObjectInstance tkinst =  utkit.next();
                UserTask task=(UserTask)tkinst.getFlowObjectType();
    %>
                <li>
                    User Task: <%=task.getDisplayName(user.getLanguage())%> <%=tkinst.getId()%> Status:<%=tkinst.getStatus()%>
                  <a href="<%=task.getUrl()%>?suri=<%=tkinst.getEncodedURI()%>">ver</a>
                </li>
    <%
            }
            out.println("</ul>");
%>
        <h3>Artefactos</h3>
<%
            out.println("<ul>");
            Iterator<ProcessObject> objit=pi.getAllProcessObjects().iterator();
            while(objit.hasNext())
            {
                ProcessObject obj =  objit.next();
    %>
                <li>Object Instance:<%=obj.getURI()%></li>
    <%
                //SWBFormMgr mgr=new SWBFormMgr(obj.getSemanticObject(),null,SWBFormMgr.MODE_EDIT);
                //out.println(mgr.renderForm(request));
            }
            out.println("</ul>");

%>
        <h3>Detalle de Proceso</h3>
<%
            out.println("<ul>");
            Iterator<FlowObjectInstance> actit=pi.listFlowObjectInstances();
            while(actit.hasNext())
            {
                FlowObjectInstance obj =  actit.next();
                printActivityInstance(obj, out);
            }
            out.println("</ul>");
        }
%>
