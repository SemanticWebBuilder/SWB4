<%--
    Document   : businessControlPanel
    Created on : 5/07/2011, 04:14:49 PM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>
<%@page import="org.semanticwb.process.model.IntermediateCatchEvent"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.RoleRef"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.ProcessWebPage"%>
<%@page import="org.semanticwb.process.model.SubProcessInstance"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.Instance"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.SWBProcessMgr"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.Math"%>
<%@page import="java.util.ArrayList"%>

<%!
    public String getStatusInstances(ProcessInstance pi, int status) {
        String ret = "";
        if (pi != null) {
            Iterator<FlowNodeInstance> actit = SWBComparator.sortByCreated(pi.listFlowNodeInstances());
            while (actit.hasNext()) {
                FlowNodeInstance obj = actit.next();
                ret += _getStatusInstances(obj, status);
            }
        }
        return (ret);
    }

    public String _getStatusInstances(FlowNodeInstance fi, int status) {
        String ret = "";
        if (fi instanceof SubProcessInstance) {
            SubProcessInstance pi = (SubProcessInstance) fi;
            Iterator<FlowNodeInstance> acit = pi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    ret += _getStatusInstances(actinst, status);
                }
            }
        } else if (fi.getFlowNodeType() instanceof Activity && fi.getStatus() == status) {
            ret += fi.getFlowNodeType().getURI() + "|";
        }
        return ret;
    }
%>

<script type="text/javascript">
    function loadPageUrl(url, paramName, paramValue) {
        var dest = url;
        if (paramName != null && paramValue != null) {
            dest+="&"+paramName+"="+paramValue;
        }
        window.location = dest;
    }
</script>

<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
WebPage statusWp = (WebPage)request.getAttribute("statusWp");
User user = paramRequest.getUser();
WebSite ws = paramRequest.getWebPage().getWebSite();
String baseimg = SWBPortal.getWebWorkPath() + "/models/" + ws.getId() + "/css/images/";
String sortType = request.getParameter("sort");
String gFilter = request.getParameter("gFilter");
String sFilter = request.getParameter("sFilter");
String displayCols = (String) request.getAttribute("displayCols");
String lang = "es";
int pageNum = 1;
int maxPages = (Integer) request.getAttribute("maxPages");

if (user.getLanguage() != null) {
    lang = user.getLanguage();
}
if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
    pageNum = Integer.valueOf(request.getParameter("page"));
}
if (sortType != null && !sortType.trim().equals("")) {
    sortType = sortType.trim();
} else {
    sortType = "date";
}

if (gFilter == null || gFilter.trim().equals("")) {
    gFilter = "";
}

if (sFilter == null || sFilter.trim().equals("")) {
    sFilter = "";
}
ArrayList<ProcessInstance> pinstances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
SWBResourceURL configUrl = paramRequest.getRenderUrl().setMode("config");
if (paramRequest.getMode().equals(paramRequest.Mode_VIEW)) {
    SWBResourceURL optsUrl = paramRequest.getRenderUrl();
    optsUrl.setParameter("gFilter", gFilter);
    optsUrl.setParameter("sFilter", sFilter);
    %>
    <h2>Monitor de procesos</h2>
    <div class="bandeja-combo">
        <ul>
            <li>Ordenamiento:
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sort', this.options[this.selectedIndex].value)">
                        <option value="date" <%=sortType.equals("date")?"selected":""%>>Por fecha</option>
                        <option value="name" <%=sortType.equals("name")?"selected":""%>>Por proceso</option>
                        <option value="priority" <%=sortType.equals("priority")?"selected":""%>>Por prioridad</option>
                    <!--option value="priority" <%=sortType.equals("priority")?"selected":""%>>Por prioridad</option-->
                    </select>
            </li>
            <li>
                <%
                optsUrl = paramRequest.getRenderUrl();
                optsUrl.setParameter("sort", sortType);
                optsUrl.setParameter("sFilter", sFilter);
                %>
                Grupo:
                <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'gFilter', this.options[this.selectedIndex].value)">
                    <option value="" <%=gFilter.equals("")?"selected":""%>>Todos</option>
                    <%
                    Iterator<ProcessGroup> groups = ProcessGroup.ClassMgr.listProcessGroups(ws);
                    groups = SWBComparator.sortByDisplayName(groups, lang);
                    while (groups.hasNext()) {
                        ProcessGroup group = groups.next();
                        String selected = "";
                        if (gFilter.equals(group.getId())) selected = "selected";
                        %>
                        <option value="<%=group.getId()%>" <%=selected%>><%=group.getDisplayTitle(lang)%></option>
                        <%
                    }
                    %>
                </select>
            </li>
            <li>
                <%
                optsUrl = paramRequest.getRenderUrl();
                optsUrl.setParameter("sort", sortType);
                optsUrl.setParameter("gFilter", gFilter);
                %>
                Estado:
                <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sFilter', this.options[this.selectedIndex].value)">
                    <option value="" <%=sFilter.equals("")?"selected":""%>>Todos</option>
                    <option value="<%=ProcessInstance.STATUS_PROCESSING%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_PROCESSING))?"selected":""%>>Pendientes</option>
                    <option value="<%=ProcessInstance.STATUS_CLOSED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_CLOSED))?"selected":""%>>Terminados</option>
                    <option value="<%=ProcessInstance.STATUS_ABORTED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_ABORTED))?"selected":""%>>Abortados</option>
                </select>
            </li>
            <li>
                <a href="<%=configUrl%>">Configurar despliegue</a>
            </li>
        </ul>
    </div>
    <%
        Iterator<Process> p_it = Process.ClassMgr.listProcesses(ws);
        if (p_it.hasNext()) {
            SWBResourceURL createUrl = paramRequest.getActionUrl().setAction("create");
            %>
            <div class="bandeja-combo">
                <ul>
                    <li>
                        <select id="processId">
                            <%
                            while(p_it.hasNext()) {
                                Process ip = p_it.next();
                                %><option value="<%=ip.getId()%>"><%=ip.getDisplayTitle(lang)%></option><%
                            }
                            %>
                        </select>
                    </li>
                    <li>
                        <input type="button" value="Crear caso" onclick="loadPageUrl('<%=createUrl.toString()%>', 'pid', document.getElementById('processId').options[document.getElementById('processId').selectedIndex].value)"/>
                    </li>
                </ul>
            </div>
            <%
        }
    if (pinstances != null && pinstances.size() > 0) {
        %>        
        <br>
        <table class="tabla-bandeja">
            <thead>
                <tr>
                    <%
                    if (displayCols.contains("idCol")) {
                        %><th class="tban-id">ID</th><%
                    }
                    if (displayCols.contains("priorityCol")) {
                        %><th class="tban-id">Prioridad</th><%
                    }
                    if (displayCols.contains("nameCol")) {
                        %><th class="tban-proces">Proceso</th><%
                    }
                    if (displayCols.contains("sdateCol")) {
                        %><th class="tban-inicia">Iniciado</th><%
                    }
                    if (displayCols.contains("edateCol")) {
                        %><th class="tban-cerrada">Cerrado</th><%
                    }
                    if (displayCols.contains("pendingCol")) {
                        %><th class="tban-tarea">Actividades pendientes</th><%
                    }
                    if (displayCols.contains("rolesCol")) {
                        %><th class="tban-tarea">Responsables</th><%
                    }
                    if (displayCols.contains("actionsCol")) {
                        %><th class="tban-accion">Acciones</th><%
                    }
                    %>
                </tr>
            </thead>
            <tbody>
                <%
                Iterator<ProcessInstance> instances = pinstances.iterator();
                while(instances.hasNext()) {
                    ProcessInstance instance = instances.next();
                    String status = "<img src=\""+baseimg;
                    String Id = instance.getId();
                    String pName = instance.getProcessType().getDisplayTitle(lang);
                    String pCreated = SWBUtils.TEXT.getStrDate(instance.getCreated(), lang, "dd/mm/yy - hh:mm");
                    String pClosed = "--";
                    
                    if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) status += "icon_pending.png\">";
                    if (instance.getStatus() == ProcessInstance.STATUS_CLOSED) {
                        status += "icon_closed.png\">";
                        pClosed = SWBUtils.TEXT.getStrDate(instance.getEnded(), lang, "dd/mm/yy - hh:mm");
                    }
                    if (instance.getStatus() == ProcessInstance.STATUS_ABORTED) status += "icon_aborted.png\">";
                    %>
                    <tr>
                        <%
                        if (displayCols.contains("idCol")) {
                            %><td class="tban-id"><%=Id%></td><%
                        }
                        if (displayCols.contains("priorityCol")) {
                            %><td class="tban-id"><%=instance.getPriority()%></td><%
                        }
                        if (displayCols.contains("nameCol")) {
                            %><td class="tban-proces"><%=pName%></td><%
                        }
                        if (displayCols.contains("sdateCol")) {
                            %><td class="tban-inicia"><%=pCreated%></td><%
                        }
                        if (displayCols.contains("edateCol")) {
                            %><td class="tban-cerrada"><%=pClosed%></td><%
                        }
                        if (displayCols.contains("pendingCol")) {
                            %>
                            <td class="tban-tarea">
                                <%
                                Iterator<FlowNodeInstance> tasks = instance.listFlowNodeInstances();
                                if (tasks.hasNext() && instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                    boolean hasTasks = false;
                                    %>
                                    <ul>
                                        <%
                                        while (tasks.hasNext()) {
                                            FlowNodeInstance task = tasks.next();
                                            if (task.getFlowNodeType() instanceof UserTask) {
                                                if (task.getStatus() == task.STATUS_PROCESSING) {
                                                    hasTasks = true;
                                                    %>
                                                    <li>
                                                        <b><%=task.getFlowNodeType().getDisplayTitle(lang)%></b>
                                                    </li>
                                                    <%
                                                }
                                            }
                                        }
                                        if (!hasTasks) {
                                            %>Proceso en espera<%
                                        }
                                        %>
                                    </ul>
                                    <%
                                } else {
                                    %>--<%
                                }
                                %>
                            </td>
                            <%
                        }
                        if (displayCols.contains("rolesCol")) {
                            %>
                            <td class="tban-tarea">
                                <%
                                Iterator<FlowNodeInstance> tasks = instance.listFlowNodeInstances();
                                if (tasks.hasNext() && instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                    boolean hasTasks = false;
                                    %>
                                    <ul>
                                        <%
                                        while (tasks.hasNext()) {
                                            FlowNodeInstance task = tasks.next();
                                            if (task.getFlowNodeType() instanceof UserTask) {
                                                if (task.getStatus() == task.STATUS_PROCESSING) {
                                                    hasTasks = true;
                                                    UserTask utask = (UserTask)task.getFlowNodeType();
                                                    Iterator<RoleRef> roles = utask.listRoleRefs();
                                                    while(roles.hasNext()) {
                                                        RoleRef roleRef = roles.next();
                                                        String role = roleRef.getRole().getDisplayTitle(lang);
                                                        %>
                                                        <li>
                                                            <b><%=role%></b>
                                                        </li>
                                                        <%
                                                    }
                                                }
                                            }
                                        }
                                        if (!hasTasks) {
                                            %>--<%
                                        }
                                        %>
                                    </ul>
                                    <%
                                } else {
                                    %>--<%
                                }
                                %>
                            </td>
                            <%
                        }
                        if (displayCols.contains("actionsCol")) {
                            %>
                            <td class="tban-accion">
                                <%
                                String acts = getStatusInstances(instance, ProcessInstance.STATUS_PROCESSING);
                                if (acts != null && !acts.trim().equals("")) {
                                    acts = "&currentActivities=" + URLEncoder.encode(acts);
                                }

                                if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                    ProcessWebPage pwp = instance.getProcessType().getProcessWebPage();
                                    if (pwp != null) {
                                        %><a class="acc-atender" target="_new" href="<%=pwp.getUrl()%>">Detalle</a><%
                                    }
                                }
                                
                                if (statusWp != null) {
                                %>
                                    <a class="acc-comentar" target="_new" href="<%=statusWp.getUrl()%>?suri=<%=instance.getProcessType().getEncodedURI()%>&mode=view<%=acts%>">Ver mapa</a>
                                <%}%>
                                <a class="acc-eliminar" href="#">Eliminar</a>
                            </td>
                            <%
                        }
                        %>
                    </tr>
                    <%
                }
                %>
            </tbody>
        </table>
        <div class="paginado">
            P&aacute;gina:
        <%
        for (int i = 1; i <= maxPages; i++) {
            if (pageNum == i) {
                %><strong><%=i%></strong> <%
            } else {
                SWBResourceURL pUrl = paramRequest.getRenderUrl();
                pUrl.setParameter("gFilter", gFilter);
                pUrl.setParameter("sFilter", sFilter);
                pUrl.setParameter("sort", sortType);
                pUrl.setParameter("page", String.valueOf(i));
                %><a href="<%=pUrl%>"><%=i%></a> <%
            }
        }
        %>
        </div>
        <%
    }
}
%>