<%-- 
    Document   : userTaskInbox
    Created on : 2/08/2011, 10:10:59 AM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>

<%@page import="org.semanticwb.process.model.base.StartEventBase"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.StartEvent"%>
<%@page import="java.util.Date"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.SubProcessInstance"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.model.UserGroup"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
private ArrayList<Integer> getIsntanceYears(Iterator<FlowNodeInstance> instances) {
    ArrayList<Integer> years = new ArrayList<Integer>();
    while(instances.hasNext()) {
        FlowNodeInstance instance = instances.next();
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(instance.getCreated());
        if (!years.contains(cal.get(Calendar.YEAR))) {
            years.add(cal.get(Calendar.YEAR));
        }
    }
    
    return years;
}
%>

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
//UserGroup usrGroup = user.getUserGroup();
WebPage statusWp = (WebPage) request.getAttribute("statusWp");
//Calendar now = GregorianCalendar.getInstance();
//now.setTime(new Date(System.currentTimeMillis()));
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
//int sYear = now.get(Calendar.YEAR);

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
String [] months = {"ENE","FEB","MAR","ABR","MAY","JUN","JUL","AGO","SEP","OCT","NOV","DIC"};

ArrayList<FlowNodeInstance> tinstances = (ArrayList<FlowNodeInstance>) request.getAttribute("instances");
//ArrayList<Integer> years = getIsntanceYears(tinstances.iterator());
SWBResourceURL configUrl = paramRequest.getRenderUrl().setMode("config");
if (paramRequest.getMode().equals(paramRequest.Mode_VIEW)) {
    SWBResourceURL optsUrl = paramRequest.getRenderUrl();
    optsUrl.setParameter("pFilter", pFilter);
    optsUrl.setParameter("sFilter", sFilter);
    %>
    <h2>Bandeja de tareas</h2>
    <div class="bandeja-combo">
        <ul>
            <li>Ordenar:
                <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sort', this.options[this.selectedIndex].value)">
                    <option value="date" <%=sortType.equals("date")?"selected":""%>>Por fecha</option>
                    <option value="name" <%=sortType.equals("name")?"selected":""%>>Por proceso</option>
                <!--option value="priority" <%=sortType.equals("priority")?"selected":""%>>Por prioridad</option-->
                </select>
            </li>
            <li>
                <%
                optsUrl = paramRequest.getRenderUrl();
                optsUrl.setParameter("sort", sortType);
                optsUrl.setParameter("sFilter", sFilter);
                %>
                Proceso:
                <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'pFilter', this.options[this.selectedIndex].value)">
                    <option value="" <%=pFilter.equals("")?"selected":""%>>Todos</option>
                    <%
                    Iterator<Process> processes = Process.ClassMgr.listProcesses(paramRequest.getWebPage().getWebSite());
                    processes = SWBComparator.sortByDisplayName(processes, lang);
                    while (processes.hasNext()) {
                        Process process = processes.next();
                        String selected = "";
                        if (pFilter.equals(process.getId())) selected = "selected";
                        %>
                        <option value="<%=process.getId()%>" <%=selected%>><%=process.getDisplayTitle(lang)%></option>
                        <%
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
                Estado:
                <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sFilter', this.options[this.selectedIndex].value)">
                    <option value="-1" <%=sFilter.equals("-1")?"selected":""%>>Todos</option>
                    <option value="<%=ProcessInstance.STATUS_PROCESSING%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_PROCESSING))?"selected":""%>>Pendientes</option>
                    <option value="<%=ProcessInstance.STATUS_CLOSED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_CLOSED))?"selected":""%>>Terminadas</option>
                    <option value="<%=ProcessInstance.STATUS_ABORTED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_ABORTED))?"selected":""%>>Abortadas</option>
                </select>
            </li>
            <!--li>
                <%
                optsUrl = paramRequest.getActionUrl().setAction("setPageItems");
                optsUrl.setParameter("sort", sortType);
                optsUrl.setParameter("pFilter", pFilter);
                optsUrl.setParameter("sFilter", sFilter);
                %>
                Mostrar:
                <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'ipp', this.options[this.selectedIndex].value)">
                    <option value="5" <%=itemsPerPage.equals("5")?"selected":""%>>5</option>
                    <option value="10" <%=itemsPerPage.equals("10")?"selected":""%>>10</option>
                    <option value="15" <%=itemsPerPage.equals("15")?"selected":""%>>15</option>
                    <option value="20" <%=itemsPerPage.equals("20")?"selected":""%>>20</option>
                </select>
            </li-->
            <!--li>
                <a href="<%=configUrl%>">Configurar despliegue</a>
            </li-->
        </ul>
    </div>
    <div class="bandeja-combo">
        <ul>
            <li>
                <select id="processId">
                    <%
                    Iterator<StartEvent> startEvents = StartEvent.ClassMgr.listStartEvents(paramRequest.getWebPage().getWebSite());
                    SWBResourceURL createUrl = paramRequest.getActionUrl().setAction("CREATE");
                    while(startEvents.hasNext()) {
                        StartEvent sevt = startEvents.next();
                        if (user.haveAccess(sevt)) {
                            Process itp = sevt.getProcess();
                            if (itp != null) {
                                %>
                                <option value="<%=itp.getId()%>"><%=itp.getTitle()%></option>
                                <%
                            }
                        }
                    }
                    %>
                </select>
            </li>
            <li>
                <input type="button" value="Iniciar" onclick="window.location='<%=createUrl%>?pid='+document.getElementById('processId').value;"/>
            </li>
        </ul>
    </div>
        <%
        if (tinstances != null && tinstances.size() > 0) {
            %>
            <table class="tabla-bandeja">
                <thead>
                    <tr>
                        <%
                        if (displayCols.contains("idCol")) {
                            %><th class="tban-id">ID</th><%
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
                            %><th class="tban-accion">Acciones</th><%
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
                        String Id = instance.getId();
                        String pName = instance.getFlowNodeType().getProcess().getDisplayTitle(lang);
                        if(pwp != null) {
                            pName = "<a href=\"" + pwp.getUrl() + "\">" + pName + "</a>";
                        }
                        String tName = instance.getFlowNodeType().getDisplayTitle(lang);
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
                                        %><a class="acc-atender" target="_new" href="<%=utask.getTaskWebPage().getUrl()%>?suri=<%=instance.getEncodedURI()%>">Atender</a><%
                                    }
                                    if (statusWp != null) {
                                        String acts = getStatusInstances(instance.getProcessInstance(), ProcessInstance.STATUS_PROCESSING);
                                        if (acts != null && !acts.trim().equals("")) {
                                            acts = "&currentActivities=" + URLEncoder.encode(acts);
                                        }
                                        %>
                                        <a class="acc-mapa" target="_new" href="<%=statusWp.getUrl()%>?suri=<%=instance.getFlowNodeType().getProcess().getEncodedURI()%>&mode=view<%=acts%>">Ver mapa</a>
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
            <div class="paginado">
                P&aacute;gina:
            <%
            for (int i = 1; i <= maxPages; i++) {
                if (pageNum == i) {
                    %><strong><%=i%></strong> <%
                } else {
                    SWBResourceURL pUrl = paramRequest.getRenderUrl();
                    pUrl.setParameter("pFilter", pFilter);
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

