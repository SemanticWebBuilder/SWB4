<%-- 
    Document   : userTaskInbox
    Created on : 2/08/2011, 10:10:59 AM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>

<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.StartEvent"%>
<%@page import="org.semanticwb.process.model.Instance"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.SubProcessInstance"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
    
    /*public String _getStatusInstances(FlowNodeInstance fi, int status) {
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
    }*/

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
            Iterator<FlowNodeInstance> fnii = fi.getFlowNodeType().listFlowObjectInstances();
            int c = 0;
            while (fnii.hasNext()) {
                c++;
                fnii.next();
            }
            ret += fi.getFlowNodeType().getURI() + "(" + c + ")|";
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
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
WebPage statusWp = (WebPage) request.getAttribute("statusWp");
String lang = user.getLanguage();
String sortType = request.getParameter("sort");
String pFilter = request.getParameter("pFilter");
String sFilter = request.getParameter("sFilter");
String pNum = request.getParameter("page");
String itemsPerPage = (String) String.valueOf(request.getAttribute("itemsPerPage"));
String displayCols = (String) request.getAttribute("displayCols");
String baseimg = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";
int maxPages = (Integer) request.getAttribute("maxPages");
int pageNum = 1;

if (user.getLanguage() != null) {
    lang = user.getLanguage();
}
if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(request.getParameter("page"));
}
if (sortType != null && !sortType.trim().equals("")) {
    sortType = sortType.trim();
} else {
    sortType = "date";
}
if (pFilter == null || pFilter.trim().equals("")) {
    pFilter = "";
}
if (sFilter == null || sFilter.trim().equals("")) {
    sFilter = String.valueOf(ProcessInstance.STATUS_PROCESSING);
}
if (itemsPerPage == null || itemsPerPage.trim().equals("")) {
    itemsPerPage = "5";
}

String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + SWBPortal.getContextPath() + paramRequest.getWebPage().getWebSiteId();

ArrayList<FlowNodeInstance> tinstances = (ArrayList<FlowNodeInstance>) request.getAttribute("instances");
SWBResourceURL configUrl = paramRequest.getRenderUrl().setMode("config");
if (paramRequest.getMode().equals(paramRequest.Mode_VIEW)) {
    if (paramRequest.getAction().equals("createCase")) {
        %><h3>Iniciar un proceso</h3><%
        Map<String, ArrayList<Process>> groups = new TreeMap<String, ArrayList<Process>>();
        ArrayList<Process> pccs = null;
        //Obtener los eventos de inicio
        Iterator<StartEvent> startEvents = StartEvent.ClassMgr.listStartEvents(paramRequest.getWebPage().getWebSite());
        SWBResourceURL createUrl = paramRequest.getActionUrl().setAction("CREATE");
        while(startEvents.hasNext()) {
            StartEvent sevt = startEvents.next();
            //Si el usuario tiene permisos en el evento
            if (user.haveAccess(sevt)) {
                Process itp = sevt.getProcess();
                //Si el proceso al que pertenece el evento y es válido
                if (itp != null && itp.isValid()) {
                    if(itp.getProcessGroup() != null) {
                        String pg = itp.getProcessGroup().getDisplayTitle(lang);
                        //Si ya existe el grupo de procesos en el treemap
                        if(groups.get(pg) != null) {
                            pccs = groups.get(pg);
                            if (!pccs.contains(itp)) {
                                pccs.add(itp);
                            }
                            groups.put(pg, pccs);
                        } else { //Si no existe el grupo de procesos en el treemap
                            pccs = new ArrayList<Process>();
                            pccs.add(itp);
                            groups.put(pg, pccs);
                        }
                    }
                }
            }
        }

        Iterator<String> keys = groups.keySet().iterator();
        if (keys.hasNext()) {
        %>
            <div class="bandeja-combo">
                <ul>
                    <li>
                        Proceso: 
                        <select id="processId">
                            <%
                            while(keys.hasNext()) {
                                String key = keys.next();
                                %>
                                <optgroup label="<%=key%>">
                                    <%
                                    Iterator<Process> it_pccs = SWBComparator.sortByDisplayName(groups.get(key).iterator(), lang);
                                    while(it_pccs.hasNext()) {
                                        Process pcc = it_pccs.next();
                                        %>
                                        <option value="<%=pcc.getId()%>"><%=pcc.getDisplayTitle(lang)%></option>
                                        <%
                                    }
                                    %>
                                </optgroup>
                                <%
                            }
                            %>
                        </select>
                    </li>
                    <li>
                        <input type="button" value="Iniciar proceso" onclick="window.location='<%=createUrl%>?pid='+document.getElementById('processId').value;"/>
                        <input type="button" value="Regresar" onclick="window.location='<%=paramRequest.getRenderUrl().toString()%>'"/>
                    </li>
                </ul>
            </div>
            <%
        }
    } else {
        SWBResourceURL optsUrl = paramRequest.getRenderUrl();
        optsUrl.setParameter("pFilter", pFilter);
        optsUrl.setParameter("sFilter", sFilter);
        %>
        <h2>Mis Tareas</h2>
        <div class="bandeja-combo">
            <ul>
                <li>
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sort', this.options[this.selectedIndex].value)">
                        <option value="date" <%=sortType.equals("date")?"selected":""%>>Por fecha</option>
                        <option value="name" <%=sortType.equals("name")?"selected":""%>>Por proceso</option>
                    </select>
                </li>
                <li>
                    <%
                    optsUrl = paramRequest.getRenderUrl();
                    optsUrl.setParameter("sort", sortType);
                    optsUrl.setParameter("sFilter", sFilter);
                    %>
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'pFilter', this.options[this.selectedIndex].value)">
                        <option value="" <%=pFilter.equals("")?"selected":""%>>Todos los procesos</option>
                        <%
                        Iterator<ProcessGroup> itgroups = SWBComparator.sortByDisplayName(ProcessGroup.ClassMgr.listProcessGroups(paramRequest.getWebPage().getWebSite()), lang);
                        while (itgroups.hasNext()) {
                            ProcessGroup pgroup = itgroups.next();
                            Iterator<Process> processes = SWBComparator.sortByDisplayName(pgroup.listProcesses(), lang);
                            ArrayList<Process> alProcesses = new ArrayList<Process>();

                            while (processes.hasNext()) {
                                Process process = processes.next();
                                if (process.isValid()) {
                                    alProcesses.add(process);
                                }
                            }

                            if (!alProcesses.isEmpty()) {
                                processes = alProcesses.iterator();
                                %>
                                <optgroup label="<%=pgroup.getDisplayTitle(lang)%>">
                                    <%
                                    while (processes.hasNext()) {
                                        Process process = processes.next();
                                        String selected = "";
                                        if (pFilter.equals(process.getId())) selected = "selected";
                                        %>
                                        <option value="<%=process.getId()%>" <%=selected%>><%=process.getDisplayTitle(lang)%></option>
                                        <%
                                    }
                                    %>
                                </optgroup>
                                <%
                            }
                        }
                        %>
                    </select>
                </li>
                <li>
                    <%
                    optsUrl = paramRequest.getRenderUrl();
                    optsUrl.setParameter("sort", sortType);
                    optsUrl.setParameter("pFilter", pFilter);
                    %>
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sFilter', this.options[this.selectedIndex].value)">
                        <option value="-1" <%=sFilter.equals("-1")?"selected":""%>>Todas las tareas</option>
                        <option value="<%=ProcessInstance.STATUS_PROCESSING%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_PROCESSING))?"selected":""%>>Tareas Pendientes</option>
                        <option value="<%=ProcessInstance.STATUS_CLOSED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_CLOSED))?"selected":""%>>Tareas Terminadas</option>
                        <option value="<%=ProcessInstance.STATUS_ABORTED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_ABORTED))?"selected":""%>>Tareas Abortadas</option>
                    </select>
                </li>
                <li>
                    <%
                    SWBResourceURL createUrl = paramRequest.getRenderUrl().setAction("createCase");
                    %>
                    <input type="button" value="Iniciar proceso" onclick="window.location='<%=createUrl%>'"/>
                </li>
            </ul>
        </div>

        <%
        if (tinstances != null && tinstances.size() > 0) {
            %>
            <div class="bandeja-combo">
                <table class="tabla-bandeja">
                    <thead>
                        <tr>
                            <%
                            if (displayCols.contains("idCol")) {
                                %><th class="tban-id">Caso</th><%
                            }
                            if (displayCols.contains("pnameCol")) {
                                %><th class="tban-proces">Proceso</th><%
                            }
                            if (displayCols.contains("nameCol")) {
                                %><th class="tban-tarea">Tarea</th><%
                            }
                            if (displayCols.contains("sdateCol")) {
                                %><th class="tban-inicia">Iniciada</th><%
                            }
                            if (displayCols.contains("edateCol")) {
                                %><th class="tban-cerrada">Cerrada</th><%
                            }
                            if (displayCols.contains("actionsCol")) {
                                %><th class="tban-id">Acciones</th><%
                            }
                            %>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        Iterator<FlowNodeInstance> instances = tinstances.iterator();
                        while(instances.hasNext()) {
                            FlowNodeInstance instance = instances.next();
                            WebPage pwp = instance.getProcessWebPage();
                            String status = "<img src=\""+baseimg;
                            String Id = instance.getProcessInstance().getId();
                            String pName = instance.getFlowNodeType().getProcess().getDisplayTitle(lang);
                            if(pwp != null) {
                                pName = "<a href=\"" + pwp.getUrl() + "\">" + pName + "</a>";
                            }
                            String tName = instance.getFlowNodeType().getDisplayTitle(lang);
                            String pCreated = SWBUtils.TEXT.getStrDate(instance.getCreated(), lang, "dd/mm/yy - hh:%m");
                            String pClosed = "--";

                            if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) status += "icon_pending.png\">";
                            if (instance.getStatus() == ProcessInstance.STATUS_CLOSED || instance.getStatus() == Instance.STATUS_ABORTED) {
                                status += "icon_closed.png\">";
                                pClosed = SWBUtils.TEXT.getStrDate(instance.getEnded(), lang, "dd/mm/yy - hh:%m");
                            }
                            if (instance.getStatus() == ProcessInstance.STATUS_ABORTED) status += "icon_aborted.png\">";
                            %>
                            <tr>
                                <%
                                if (displayCols.contains("idCol")) {
                                    %><td class="tban-id"><%=Id%></td><%
                                }
                                if (displayCols.contains("pnameCol")) {
                                    %><td class="tban-proces"><%=pName%></td><%
                                }
                                if (displayCols.contains("nameCol")) {
                                    %><td class="tban-tarea"><%=tName%></td><%
                                }
                                if (displayCols.contains("sdateCol")) {
                                    %><td class="tban-inicia"><%=pCreated%></td><%
                                }
                                if (displayCols.contains("edateCol")) {
                                    %><td class="tban-cerrada"><%=pClosed%></td><%
                                }
                                if (displayCols.contains("actionsCol")) {
                                    UserTask utask = (UserTask) instance.getFlowNodeType();

                                    %>
                                    <td class="tban-accion">
                                        <%
                                        if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                            %><a class="acc-atender" href="<%=utask.getTaskWebPage().getUrl()%>?suri=<%=instance.getEncodedURI()%>">Atender</a><%
                                        }
                                        if (statusWp != null) {
                                            String acts = getStatusInstances(instance.getProcessInstance(), ProcessInstance.STATUS_PROCESSING);
                                            if (acts != null && !acts.trim().equals("")) {
                                                acts = "&currentActivities=" + URLEncoder.encode(acts);
                                            }
                                            %>
                                            <!--a class="acc-mapa" target="_new" href="<%=statusWp.getUrl()%>?suri=<%=instance.getFlowNodeType().getProcess().getEncodedURI()%>&mode=view<%=acts%>&tp=<%=URLEncoder.encode(baseUrl+"/Bandeja")%>&rp=<%=URLEncoder.encode(baseUrl+"/Activos_de_Pocesos")%>">Ver mapa</a-->
                                            <a class="acc-mapa" href="<%=statusWp.getUrl()%>?suri=<%=instance.getFlowNodeType().getProcess().getEncodedURI()%>&mode=view<%=acts%>">Ver mapa</a>
                                            <%
                                        }
                                        %>
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
            </div>
                <div class="paginado">
                    <table class="tabla-bandeja">
                        <tbody>
                            <tr>
                                <td style="width:90%;">
                                    P&aacute;gina: <%=pageNum%> de <%=maxPages%>
                                </td>
                                <td>
                                    <%
                                    if (pageNum-1 > 0) {
                                        SWBResourceURL back = paramRequest.getRenderUrl();
                                        back.setParameter("pFilter", pFilter);
                                        back.setParameter("sFilter", sFilter);
                                        back.setParameter("sort", sortType);
                                        back.setParameter("page", String.valueOf(pageNum-1));
                                        %><a href="<%=back%>">Anterior</a><%
                                    }
                                    if (pageNum+1 <= maxPages) {
                                        SWBResourceURL forward = paramRequest.getRenderUrl();
                                        forward.setParameter("pFilter", pFilter);
                                        forward.setParameter("sFilter", sFilter);
                                        forward.setParameter("sort", sortType);
                                        forward.setParameter("page", String.valueOf(pageNum+1));
                                        %><a href="<%=forward%>">Siguiente</a><%
                                    }
                                    %>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <%
        } else {
            %>No tiene tareas pendientes<%
        }
    }
}

if (request.getParameter("msg") != null && request.getParameter("msg").startsWith("OK")) {
    String id = request.getParameter("msg").substring(2);
    %>
    <script type="text/javascript">
        alert("Se ha creado un nuevo caso con ID <%=id%>");
    </script>
    <%
}
%>

