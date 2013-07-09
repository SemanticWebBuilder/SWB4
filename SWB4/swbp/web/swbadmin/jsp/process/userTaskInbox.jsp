<%-- 
    Document   : userTaskInbox
    Created on : 2/08/2011, 10:10:59 AM
    Author     : Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
--%>

<%@page import="java.util.Date"%>
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

boolean allowForward = false;
if (request.getAttribute("allowForward") != null) {
    allowForward = (Boolean) request.getAttribute("allowForward");
}
int maxPages = (Integer) request.getAttribute("maxPages");
int pageNum = 1;
boolean applyFilter = true;

if (user.getLanguage() != null) {
    lang = user.getLanguage();
}
if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(request.getParameter("page"));
    if (pageNum > maxPages) {
        pageNum = maxPages;
    }
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

ArrayList<FlowNodeInstance> tinstances = (ArrayList<FlowNodeInstance>) request.getAttribute("instances");
SWBResourceURL optsUrl = paramRequest.getRenderUrl();
optsUrl.setParameter("sort", sortType);
if (applyFilter) {
    optsUrl.setParameter("pFilter", pFilter);
}
optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_PROCESSING));
SWBResourceURL createPiUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
createPiUrl.setMode(UserTaskInboxResource.MODE_CREATEPI);

if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
%>
<div class="lateral">
    <p class="lat-iniciar"><a onclick="showDialog('<%=createPiUrl%>','Crear instancia'); return false;" href="">Iniciar proceso</a></p>
    <ul class="lat-lista1">
        <li class="lat-pend"><a href="<%=optsUrl%>">Tareas pendientes</a></li>
        <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_CLOSED));%>
        <li class="lat-term"><a href="<%=optsUrl%>">Tareas terminadas</a></li>
        <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_ABORTED));%>
        <li class="lat-term"><a href="<%=optsUrl%>">Tareas abortadas</a></li>
    </ul>
</div>
<%
} else {
%>
    <div id="toast" style="border: 1px solid #CCCCCC;background-color: #FFF49C;padding: 10px 0 ;text-align:center;opacity: 0.9;border-radius:5px 5px 5px 5px;-webkit-transition: opacity 0.5s ease-out;  /* Saf3.2+, Chrome */-moz-transition: opacity 0.5s ease-out;  /* FF4+ */-ms-transition: opacity 0.5s ease-out;  /* IE10? */-o-transition: opacity 0.5s ease-out;  /* Opera 10.5+ */transition: opacity 0.5s ease-out;"></div>
    <div class="bandeja-combo">
        <ul>
            <li>
                <%optsUrl = paramRequest.getRenderUrl();
                if (applyFilter) {
                    optsUrl.setParameter("pFilter", pFilter);
                }
                optsUrl.setParameter("sFilter", sFilter);%>
                <b>Ordenamiento&nbsp;</b>
                <select onchange="loadPageUrl('<%=optsUrl%>', 'sort', this.options[this.selectedIndex].value)">
                    <option value="date" <%=sortType.equals("date")?"selected":""%>>Por fecha</option>
                    <option value="name" <%=sortType.equals("name")?"selected":""%>>Por proceso</option>
                </select>
            </li>
            <li>
                <b>Filtrado&nbsp;</b>
                <%optsUrl = paramRequest.getRenderUrl(); optsUrl.setParameter("sort", sortType); optsUrl.setParameter("sFilter", sFilter);%>
                <select onchange="loadPageUrl('<%=optsUrl%>', 'pFilter', this.options[this.selectedIndex].value)">
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
                <select onchange="loadPageUrl('<%=optsUrl%>', 'sFilter', this.options[this.selectedIndex].value)">
                    <option value="<%=ProcessInstance.STATUS_PROCESSING%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_PROCESSING))?"selected":""%>>Tareas Pendientes</option>
                    <option value="<%=ProcessInstance.STATUS_CLOSED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_CLOSED))?"selected":""%>>Tareas Terminadas</option>
                    <option value="<%=ProcessInstance.STATUS_ABORTED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_ABORTED))?"selected":""%>>Tareas Abortadas</option>
                </select>
            </li>
        </ul>
    </div>
    <% if (tinstances != null && tinstances.size() > 0) { %>
        <div>
            <table class="tabla-bandeja">
                <thead>
                    <tr>
                        <%Iterator<String> itCols = cols.iterator();
                        while(itCols.hasNext()) {
                            String val = itCols.next();
                            String []conf = val.split("\\|");
                            if (conf.length == 2) {
                                %><th class="tban-id"><%=conf[1]%></th><%
                            }
                        }
                        %>
                    </tr>
                </thead>
                <tbody>
                    <%Iterator<FlowNodeInstance> instances = tinstances.iterator();
                    while(instances.hasNext()) {
                        FlowNodeInstance instance = instances.next();
                        if (instance.getProcessInstance().getCreator() != null) {
                            if (instance.getProcessInstance().getStatus() == Instance.STATUS_PROCESSING) {
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
                                                SWBResourceURL detailUrl = paramRequest.getRenderUrl().setMode(UserTaskInboxResource.MODE_PROCESSDETAIL);
                                                detailUrl.setParameter("suri", instance.getFlowNodeType().getProcess().getURI());
                                                if(showPwpLink) {
                                                    pName = "<a href=\"" + detailUrl + "\">" + pName + "</a>";
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
                                                    if (creator.getFullName() != null && !creator.getFullName().trim().equals("")) {
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
                                                    if (creator.getFullName() != null && !creator.getFullName().trim().equals("")) {
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
                                            } else if (conf[0].equals(UserTaskInboxResource.COL_ACTIONS)) {%>
                                                <td class="tban-accion">
                                                    <%UserTask utask = (UserTask) instance.getFlowNodeType();
                                                    if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {%>
                                                        <a class="acc-atender" href="<%=utask.getTaskWebPage().getUrl()%>?suri=<%=instance.getEncodedURI()%>">Atender</a>
                                                        <%if (allowForward && instance.getAssignedto() != null) {
                                                            SWBResourceURL forward = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(UserTaskInboxResource.MODE_FWD);
                                                            %><a class="acc-delegar" onclick="showDialog('<%=forward%>?suri=<%=instance.getEncodedURI()%>', 'Reasignar tarea'); return false;" href="">Reasignar</a><%
                                                        }
                                                    }
                                                    if (statusWp != null) {%>
                                                        <a class="acc-mapa" href="<%=statusWp.getUrl()%>?suri=<%=instance.getProcessInstance().getProcessType().getEncodedURI()%>">Ver mapa</a><%
                                                    }%>
                                                </td>
                                                <%
                                            } else if (conf[0].equals(UserTaskInboxResource.COL_TASKSUBJECT)) {
                                                String subject = "--";
                                                if (instance.getSubject() != null) {
                                                    subject = instance.getSubject();
                                                }
                                                %><td class="tban-tarea"><%=subject%></td><%
                                            } else if (conf[0].equals(UserTaskInboxResource.COL_FLAGTASK)) {
                                                UserTask utask = (UserTask)instance.getFlowNodeType();
                                                int delay = utask.getNotificationTime();
                                                String delayed = "/work/models/"+paramRequest.getWebPage().getWebSiteId()+"/css/images/icono-retraso1.png";
                                                
                                                if (delay > 0) {
                                                    long today = System.currentTimeMillis();
                                                    long cr = instance.getCreated().getTime();
                                                    if (today - cr > (1000*60*delay)) {
                                                        delayed = "/work/models/"+paramRequest.getWebPage().getWebSiteId()+"/css/images/icono-retraso2.png";
                                                    }
                                                }
                                                %><td class="tban-inicia"><img src="<%=delayed%>"/></td><%
                                            }
                                        }
                                    }
                                    %>
                                </tr>
                            <%
                            }
                        }
                    }%>
                </tbody>
            </table>
        </div>
        <div class="paginado">
            <div class="pagtotal">P&aacute;gina <%=pageNum%> de <%=maxPages%></div>
            <div class="pagLista">
            <%if (pageNum-1 > 0) {
                SWBResourceURL back = paramRequest.getRenderUrl();
                if (applyFilter) {
                    back.setParameter("pFilter", pFilter);
                }
                back.setParameter("sFilter", sFilter);
                back.setParameter("sort", sortType);
                back.setParameter("page", String.valueOf(pageNum-1));
                %><span class="pagant"><a href="<%=back%>">Anterior</a></span><%
            }
            if (pageNum+1 <= maxPages) {
                SWBResourceURL forward = paramRequest.getRenderUrl();
                if (applyFilter) {
                    forward.setParameter("pFilter", pFilter);
                }
                forward.setParameter("sFilter", sFilter);
                forward.setParameter("sort", sortType);
                forward.setParameter("page", String.valueOf(pageNum+1));
                %><span class="pagsig"><a href="<%=forward%>">Siguiente</a></span><%
            }%>
            </div>
        </div>
    <%} else {
        %>No hay tareas<%
    }%>
<%if (null != request.getSession(true).getAttribute("msg")) {
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
}
%>