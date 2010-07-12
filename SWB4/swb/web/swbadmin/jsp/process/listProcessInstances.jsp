<%@page import="org.semanticwb.process.model.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public void printActivityInstance(FlowNodeInstance ai, JspWriter out) throws IOException
    {
        out.println("<li>");
        out.println("Activity: "+ai.getFlowNodeType().getTitle()+" "+ai.getId());
        out.println("Status:"+ai.getStatus());
        out.println("</li>");
        if(ai instanceof SubProcessInstance)
        {
            SubProcessInstance pi=(SubProcessInstance)ai;
            Iterator<FlowNodeInstance> acit=pi.listFlowNodeInstances();
            if(acit.hasNext())
            {
                out.println("<ul>");
                while(acit.hasNext())
                {
                    FlowNodeInstance actinst =  acit.next();
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
    org.semanticwb.process.model.Process process=SWBProcessMgr.getProcess(topic);
%>
    <h2>Instancias del Proceso <%=process.getDisplayTitle(user.getLanguage())%></h2>
<%
    Iterator<ProcessInstance> it=SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
    while(it.hasNext())
    {
        ProcessInstance pi =  it.next();
%>
        <h3><%=pi.getProcessType().getTitle()%> <%=pi.getId()%></h3>
        <h3>Tareas del usuario (<%=user.getFullName()%>)</h3>
<%
            out.println("<ul>");
            Iterator<FlowNodeInstance> utkit=SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
            while(utkit.hasNext())
            {
                FlowNodeInstance tkinst =  utkit.next();
                UserTask task=(UserTask)tkinst.getFlowNodeType();
    %>
                <li>
                    User Task: <%=task.getDisplayTitle(user.getLanguage())%> <%=tkinst.getId()%> Status:<%=tkinst.getStatus()%>
                  <a href="<%=task.getTaskWebPage().getUrl()%>?suri=<%=tkinst.getEncodedURI()%>">ver</a>
                </li>
    <%
            }
            out.println("</ul>");
%>
        <h3>Artefactos</h3>
<%
            out.println("<ul>");
            Iterator<ProcessObject> objit=pi.listAllProcessObjects();
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
            Iterator<FlowNodeInstance> actit=pi.listFlowNodeInstances();
            while(actit.hasNext())
            {
                FlowNodeInstance obj =  actit.next();
                printActivityInstance(obj, out);
            }
            out.println("</ul>");
        }
%>
