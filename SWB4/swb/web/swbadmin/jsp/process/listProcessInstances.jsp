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
        if(!(ai.getFlowNodeType() instanceof Activity))return;
        String stat="";
        String color="";
        if(ai.getStatus()==Instance.STATUS_INIT)stat="Iniciada";
        if(ai.getStatus()==Instance.STATUS_ABORTED)stat="Abortada";
        if(ai.getStatus()==Instance.STATUS_CLOSED)
        {
            stat="Concluida";
            color="color=\"#50b050\"";
        }
        if(ai.getStatus()==Instance.STATUS_OPEN)stat="Abierta";
        if(ai.getStatus()==Instance.STATUS_PROCESSING)stat="En ejecución";
        if(ai.getStatus()==Instance.STATUS_STOPED)stat="Detenida";
        out.println("<li>");
        out.println("<font "+ color +">");
        out.println(ai.getFlowNodeType().getTitle()+": ");//+ai.getId()
        out.println(stat);
        out.println("</font>");
        //out.println("Action:"+ai.getAction());
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
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    WebPage topic = paramRequest.getWebPage();
    ProcessSite site = (ProcessSite) paramRequest.getWebPage().getWebSite();
    String lang = user.getLanguage();
    org.semanticwb.process.model.Process process = SWBProcessMgr.getProcess(topic);
%>

<div class="post">
    <!--<h2 class="title">Instancias del Proceso <%=process.getDisplayTitle(user.getLanguage())%></h2>-->
    <%
    Iterator<ProcessInstance> it = SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
    if (it.hasNext()) {
        %>
        <h3>Tareas del usuario (<%=user.getFullName()%>)</h3>
        <%
        while (it.hasNext()) {
%>
        <table>
            <thead>
                <tr>
                    <th align="center">
                        Proceso
                    </th>
                    <th align="center">
                        Tarea
                    </th>
                    <th align="center">
                        Estado
                    </th>
                    <th align="center">
                        Creación - Proceso
                    </th>
                    <th align="center">
                        Creador - Proceso
                    </th>
                    <th align="center">
                        Creación - Tarea
                    </th>
                    <th align="center">
                        Creador - Tarea
                    </th>
                    <th align="center">
                        Acciones
                    </th>
                </tr>
            </thead>
            <tbody>
<%

            ProcessInstance pi = it.next();
            Iterator<FlowNodeInstance> utkit = SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
            if (utkit.hasNext()) {
                while (utkit.hasNext()) {
                    FlowNodeInstance tkinst = utkit.next();
                    Task _task = (UserTask) tkinst.getFlowNodeType();

                    if (_task instanceof UserTask) {
                        UserTask task = (UserTask) _task;
                        %>
                            <tr>
                                <td align="center">
                                    <%=pi.getId()%>
                                </td>
                                <td align="center">
                                    <%=task.getDisplayTitle(lang)%>
                                </td>
                                <td align="center">
                                    <%=tkinst.getStatus()%>
                                </td>
                                <td align="center">
                                    <%=SWBUtils.TEXT.getStrDate(pi.getCreated(), lang)%>
                                </td>
                                <td align="center">
                                    <%=pi.getCreator().getFullName()%>
                                </td>
                                <td align="center">
                                    <%=SWBUtils.TEXT.getStrDate(tkinst.getCreated(), lang)%>
                                </td>
                                <td align="center">
                                    <%=tkinst.getCreator().getFullName()%>
                                </td>
                                <td align="center">
                                    <a href="<%=task.getTaskWebPage().getUrl()%>?suri=<%=tkinst.getEncodedURI()%>">Ver</a>
                                </td>
                            </tr>
                        <%
                    }
                }
            }
%>
        </tbody>
    </table>
        <br/><br/>
        <h3>Detalle de Proceso</h3>
<%
            out.println("<ul>");
            Iterator<FlowNodeInstance> actit=SWBComparator.sortByCreated(pi.listFlowNodeInstances());
            while(actit.hasNext())
            {
                FlowNodeInstance obj =  actit.next();
                printActivityInstance(obj, out);
            }
            out.println("</ul>");
%>
<%
        }
    } else {
        %><h3>No hay instancias del proceso</h3><%
    }
    %>
</div>