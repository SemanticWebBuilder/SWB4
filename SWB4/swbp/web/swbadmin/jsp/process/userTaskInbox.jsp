<%-- 
    Document   : userTaskInbox
    Created on : 2/08/2011, 10:10:59 AM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>

<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.process.model.MessageStartEvent"%>
<%@page import="org.semanticwb.process.model.StartEventNode"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.SWBPlatform"%>
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
    var intCounterToast = 0;
    function loadPageUrl(url, paramName, paramValue) {
        var dest = url;
        if (paramName != null && paramValue != null && paramValue != "") {
            dest+="&"+paramName+"="+paramValue;
        }
        window.location = dest;
    }
    
    function showToast(msg) {
        var toast = document.getElementById("toast");
        toast.style.opacity = 0.7;
        toast.style.visibility = "visible";
        toast.innerHTML = "<b>"+msg+"</b>";
        intCounterToast = setInterval("hideToast()", 2000);
    }
    
    function hideToast() {
        var toast = document.getElementById("toast");
        toast.style.display = "none";
        toast.style.opacity = 0;
        clearInterval(intCounterToast);
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
Resource base = (Resource) request.getAttribute("base");

boolean showPwpLink = false;
if (request.getAttribute("showPWpLink") != null) {
    showPwpLink = (Boolean) request.getAttribute("showPWpLink");
}
String baseimg = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";
int maxPages = (Integer) request.getAttribute("maxPages");
int pageNum = 1;
boolean applyFilter = true;

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
    applyFilter = false;
}
if (sFilter == null || sFilter.trim().equals("")) {
    sFilter = String.valueOf(ProcessInstance.STATUS_PROCESSING);
}

ArrayList<String> cols = new ArrayList<String>();
int i = 1;
while(!base.getAttribute(UserTaskInboxResource.ATT_COLS+i, "").equals("")) {
    String val = base.getAttribute(UserTaskInboxResource.ATT_COLS+i);
    cols.add(val);
    i++;
}

String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + SWBPortal.getContextPath() + paramRequest.getWebPage().getWebSiteId();

ArrayList<FlowNodeInstance> tinstances = (ArrayList<FlowNodeInstance>) request.getAttribute("instances");
//SWBResourceURL configUrl = paramRequest.getRenderUrl().setMode("config");
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
            if (sevt.getContainer() != null && sevt.getContainer() instanceof Process && user.haveAccess(sevt)) {
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
        if (applyFilter) {
            optsUrl.setParameter("pFilter", pFilter);
        }
        optsUrl.setParameter("sFilter", sFilter);
        %>
        <div id="toast" style="border: 1px solid #CCCCCC;background-color: #FFF49C;padding: 10px 0 ;text-align:center;opacity: 0.9;border-radius:5px 5px 5px 5px;-webkit-transition: opacity 0.5s ease-out;  /* Saf3.2+, Chrome */-moz-transition: opacity 0.5s ease-out;  /* FF4+ */-ms-transition: opacity 0.5s ease-out;  /* IE10? */-o-transition: opacity 0.5s ease-out;  /* Opera 10.5+ */transition: opacity 0.5s ease-out;"></div>
        <div class="bandeja-combo">
            <ul>
                <li>
                    <%
                    SWBResourceURL createUrl = paramRequest.getRenderUrl().setAction("createCase");
                    %>
                    <a onclick="window.location='<%=createUrl%>'"><img src="<%=baseimg%>newProcess.png" alt="Iniciar nuevo Caso" title="Iniciar nuevo Caso"></a><!--input type="button" value="Iniciar proceso" onclick="window.location='<%=createUrl%>'"/-->
                </li>
                <li>
                    <b>Ordenamiento&nbsp;</b>
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sort', this.options[this.selectedIndex].value)">
                        <option value="date" <%=sortType.equals("date")?"selected":""%>>Por fecha</option>
                        <option value="name" <%=sortType.equals("name")?"selected":""%>>Por proceso</option>
                    </select>
                </li>
                <li>
                    <b>Filtrado&nbsp;</b>
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
                    </select>&nbsp;
                    <%
                    optsUrl = paramRequest.getRenderUrl();
                    optsUrl.setParameter("sort", sortType);
                    if (applyFilter) {
                        optsUrl.setParameter("pFilter", pFilter);
                    }
                    %>
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sFilter', this.options[this.selectedIndex].value)">
                        <option value="<%=ProcessInstance.STATUS_PROCESSING%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_PROCESSING))?"selected":""%>>Tareas Pendientes</option>
                        <option value="<%=ProcessInstance.STATUS_CLOSED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_CLOSED))?"selected":""%>>Tareas Terminadas</option>
                        <option value="<%=ProcessInstance.STATUS_ABORTED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_ABORTED))?"selected":""%>>Tareas Abortadas</option>
                    </select>
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
                            Iterator<String> itCols = cols.iterator();
                            while(itCols.hasNext()) {
                                String val = itCols.next();
                                String []conf = val.split("\\|");
                                if (conf.length == 2) {
                                    %>
                                    <th class="tban-id"><%=conf[1]%></th>
                                    <%
                                }
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
                            String status = "--";
                            %>
                            <tr>
                                <%
                                itCols = cols.iterator();
                                while(itCols.hasNext()) {
                                    String val = itCols.next();
                                    String []conf = val.split("\\|");
                                    if (conf.length == 2) {
                                        if (conf[0].equals(UserTaskInboxResource.COL_IDPROCESS)) {
                                            %><td class="tban-id"><%=instance.getProcessInstance().getId()%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_IDTASK)) {
                                            %><td class="tban-id"><%=instance.getId()%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_NAMEPROCESS)) {
                                            String pName = instance.getFlowNodeType().getProcess().getDisplayTitle(lang);
                                            if(pwp != null && showPwpLink) {
                                                pName = "<a href=\"" + pwp.getUrl() + "\">" + pName + "</a>";
                                            }
                                            %><td class="tban-tarea"><%=pName%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_NAMETASK)) {
                                            %><td class="tban-tarea"><%=instance.getFlowNodeType().getDisplayTitle(lang)%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_STARTPROCESS)) {
                                            %><td class="tban-inicia"><%=SWBUtils.TEXT.getStrDate(instance.getProcessInstance().getCreated(), lang, "dd/mm/yy - hh:%m")%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_STARTTASK)) {
                                            %><td class="tban-inicia"><%=SWBUtils.TEXT.getStrDate(instance.getCreated(), lang, "dd/mm/yy - hh:%m")%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_ENDPROCESS)) {
                                            String ended = "--";
                                            if (instance.getProcessInstance().getStatus() == Instance.STATUS_CLOSED || instance.getProcessInstance().getStatus() == Instance.STATUS_ABORTED) {
                                                if (instance.getProcessInstance().getEnded() != null) {
                                                    ended = SWBUtils.TEXT.getStrDate(instance.getProcessInstance().getEnded(), lang, "dd/mm/yy - hh:%m");
                                                }
                                            }
                                            %><td class="tban-cerrada"><%=ended%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_ENDTASK)) {
                                            String ended = "--";
                                            if (instance.getStatus() == Instance.STATUS_CLOSED || instance.getStatus() == Instance.STATUS_ABORTED) {
                                                if (instance.getEnded() != null) {
                                                    ended = SWBUtils.TEXT.getStrDate(instance.getEnded(), lang, "dd/mm/yy - hh:%m");
                                                }
                                            }
                                            %><td class="tban-cerrada"><%=ended%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_CREATORPROCESS)) {
                                            String screator = "Creado automáticamente";
                                            User creator = instance.getProcessInstance().getCreator();
                                            if (creator != null) {
                                                if (creator.getFullName() != null && creator.getFullName().trim().equals("")) {
                                                    screator = creator.getFullName();
                                                } else {
                                                    screator = creator.getLogin();
                                                }
                                            }
                                            %><td class="tban-tarea"><%=screator%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_CREATORTASK)) {
                                            String screator = "Creado automáticamente";
                                            User creator = instance.getCreator();
                                            if (creator != null) {
                                                if (creator.getFullName() != null && creator.getFullName().trim().equals("")) {
                                                    screator = creator.getFullName();
                                                } else {
                                                    screator = creator.getLogin();
                                                }
                                            }
                                            %><td class="tban-tarea"><%=screator%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_STATUSPROCESS)) {
                                            int st = instance.getProcessInstance().getStatus();
                                            if (st == Instance.STATUS_ABORTED) {
                                                status = "Abortado";
                                            } else if (st == Instance.STATUS_CLOSED) {
                                                status = "Cerrado";
                                            } if (st == Instance.STATUS_INIT) {
                                                status = "Iniciado";
                                            } if (st == Instance.STATUS_OPEN || st == Instance.STATUS_PROCESSING) {
                                                status = "En proceso";
                                            } if (st == Instance.STATUS_STOPED) {
                                                status = "Detenido";
                                            }
                                            %><td class="tban-tarea"><%=status%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_STATUSTASK)) {
                                            int st = instance.getStatus();
                                            if (st == Instance.STATUS_ABORTED) {
                                                status = "Abortada";
                                            } else if (st == Instance.STATUS_CLOSED) {
                                                status = "Cerrada";
                                            } if (st == Instance.STATUS_INIT) {
                                                status = "Iniciada";
                                            } if (st == Instance.STATUS_OPEN || st == Instance.STATUS_PROCESSING) {
                                                status = "En proceso";
                                            } if (st == Instance.STATUS_STOPED) {
                                                status = "Detenida";
                                            }
                                            %><td class="tban-tarea"><%=status%><%
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_ACTIONS)) {
                                            UserTask utask = (UserTask) instance.getFlowNodeType();
                                            if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                                %>
                                                <td class="tban-accion"><a class="acc-atender" href="<%=utask.getTaskWebPage().getUrl()%>?suri=<%=instance.getEncodedURI()%>">Atender</a>
                                                <%
                                                if (instance.getAssignedto() != null) {
                                                    SWBResourceURL forward = paramRequest.getRenderUrl().setMode("forward");
                                                    %><a class="acc-delegar" href="<%=forward%>?suri=<%=instance.getEncodedURI()%>">Reasignar</a><%
                                                }
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
                                        } else if (conf[0].equals(UserTaskInboxResource.COL_TASKSUBJECT)) {
                                            String subject = "--";
                                            if (instance.getSubject() != null) {
                                                subject = instance.getSubject();
                                            }
                                            %><td class="tban-tarea"><%=subject%><%
                                        }
                                        %>
                                        </td>
                                        <%
                                    }
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
                                <td style="width:80%;">
                                    P&aacute;gina: <%=pageNum%> de <%=maxPages%>
                                </td>
                                <td style="text-align:right;">
                                    <%
                                    if (maxPages > 1) {
                                        SWBResourceURL first = paramRequest.getRenderUrl();
                                        if (applyFilter) {
                                            first.setParameter("pFilter", pFilter);
                                        }
                                        first.setParameter("sFilter", sFilter);
                                        first.setParameter("sort", sortType);
                                        first.setParameter("page", "1");
                                        %><a href="<%=first%>">Primer p&aacute;gina</a><%
                                    }
                                    if (pageNum-1 > 0) {
                                        SWBResourceURL back = paramRequest.getRenderUrl();
                                        if (applyFilter) {
                                            back.setParameter("pFilter", pFilter);
                                        }
                                        back.setParameter("sFilter", sFilter);
                                        back.setParameter("sort", sortType);
                                        back.setParameter("page", String.valueOf(pageNum-1));
                                        %><a href="<%=back%>">Anterior</a><%
                                    }
                                    if (pageNum+1 <= maxPages) {
                                        SWBResourceURL forward = paramRequest.getRenderUrl();
                                        if (applyFilter) {
                                            forward.setParameter("pFilter", pFilter);
                                        }
                                        forward.setParameter("sFilter", sFilter);
                                        forward.setParameter("sort", sortType);
                                        forward.setParameter("page", String.valueOf(pageNum+1));
                                        %><a href="<%=forward%>">Siguiente</a><%
                                    }
                                    if (maxPages > 1 && pageNum < maxPages) {
                                        SWBResourceURL last = paramRequest.getRenderUrl();
                                        if (applyFilter) {
                                            last.setParameter("pFilter", pFilter);
                                        }
                                        last.setParameter("sFilter", sFilter);
                                        last.setParameter("sort", sortType);
                                        last.setParameter("page", String.valueOf(maxPages));
                                        %><a href="<%=last%>">&Uacute;ltima p&aacute;gina</a><%
                                    }
                                    %>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <%
        } else {
            %>No hay tareas<%
        }
    }
}

if (null != request.getSession(true).getAttribute("msg")) {
    String message = (String) request.getSession(true).getAttribute("msg");
    if (message.startsWith("OK")) {
        String id = message.substring(2);
        %>
        <script type="text/javascript">
            var toast = document.getElementById("toast");
            showToast("Se ha creado un nuevo caso con ID <%=id%>");
            //alert("Se ha creado un nuevo caso con ID <%=id%>");
        </script>
        <%
    }
    request.getSession(true).removeAttribute("msg");
} else {
    %>
    <script type="text/javascript">
    hideToast();
    </script>
    <%
}
%>

