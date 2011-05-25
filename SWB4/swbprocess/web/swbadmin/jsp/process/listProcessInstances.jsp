<%@page import="org.semanticwb.process.model.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    WebPage topic = paramRequest.getWebPage();
    ProcessSite site = (ProcessSite) paramRequest.getWebPage().getWebSite();
    String lang = user.getLanguage();
    org.semanticwb.process.model.Process process = SWBProcessMgr.getProcess(topic);
%>

<div class="post">
    <h2 class="title">Instancias del Proceso <%=process.getDisplayTitle(user.getLanguage())%></h2>
    <%
    Iterator<ProcessInstance> it = SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
    if (it.hasNext()) {
        %>
        <h3>Tareas del usuario (<%=user.getFullName()%>)</h3>
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
                        Creaci&oacute;n - Proceso
                    </th>
                    <th align="center">
                        Creador - Proceso
                    </th>
                    <th align="center">
                        Creaci&oacute;n - Tarea
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
        while (it.hasNext()) {
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
        }
        %>
        </tbody>
    </table>
        <%
    } else {
        %><h3>No hay instancias del proceso</h3><%
    }
    %>
</div>