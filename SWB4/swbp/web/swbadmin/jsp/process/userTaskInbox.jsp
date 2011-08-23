<%-- 
    Document   : userTaskInbox
    Created on : 2/08/2011, 10:10:59 AM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>

<%@page import="java.util.Date"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.*"%>
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

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
Calendar now = GregorianCalendar.getInstance();
now.setTime(new Date(System.currentTimeMillis()));
String lang = user.getLanguage();
String sortType = request.getParameter("sort");
String pFilter = request.getParameter("pFilter");
String sFilter = request.getParameter("sFilter");
String displayCols = (String) request.getAttribute("displayCols");
String baseimg = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";
int maxPages = (Integer) request.getAttribute("maxPages");
int pageNum = 1;
int sYear = now.get(Calendar.YEAR);

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
if (pFilter == null || pFilter.trim().equals("")) {
    pFilter = "";
}
if (sFilter == null || sFilter.trim().equals("")) {
    sFilter = "";
}
String [] months = {"ENE","FEB","MAR","ABR","MAY","JUN","JUL","AGO","SEP","OCT","NOV","DIC"};

ArrayList<FlowNodeInstance> tinstances = (ArrayList<FlowNodeInstance>) request.getAttribute("instances");
ArrayList<Integer> years = getIsntanceYears(tinstances.iterator());
SWBResourceURL configUrl = paramRequest.getRenderUrl().setMode("config");
if (paramRequest.getMode().equals(paramRequest.Mode_VIEW)) {
    %>
    <h2>Bandeja de tareas</h2>
        <table>
            <tbody>
                <tr>
                    <td>
                        <form action="<%=paramRequest.getRenderUrl()%>" method="get">
                            <label for="sort">Ordenamiento: </label>
                            <input type="hidden" name="pFilter" value="<%=pFilter%>">
                            <input type="hidden" name="sFilter" value="<%=sFilter%>">
                            <select name="sort" onchange="this.form.submit()">
                                <option value="date" <%=sortType.equals("date")?"selected":""%>>Por fecha</option>
                                <option value="name" <%=sortType.equals("name")?"selected":""%>>Por proceso</option>
                                <!--option value="priority" <%=sortType.equals("priority")?"selected":""%>>Por prioridad</option-->
                            </select>
                        </form>
                    </td>
                    <td>
                        <form action="<%=paramRequest.getRenderUrl()%>" method="get">
                            <label for="gFilter">Proceso: </label>
                            <input type="hidden" name="sort" value="<%=sortType%>">
                            <input type="hidden" name="sFilter" value="<%=sFilter%>">
                            <select name="pFilter" onchange="this.form.submit()">
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
                        </form>
                    </td>
                    <td>
                        <form action="<%=paramRequest.getRenderUrl()%>" method="get">
                            <label for="sFilter">Estado: </label>
                            <input type="hidden" name="sort" value="<%=sortType%>">
                            <input type="hidden" name="pFilter" value="<%=pFilter%>">
                            <select name="sFilter" onchange="this.form.submit()">
                                <option value="" <%=sFilter.equals("")?"selected":""%>>Todos</option>
                                <option value="<%=ProcessInstance.STATUS_PROCESSING%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_PROCESSING))?"selected":""%>>Activos</option>
                                <option value="<%=ProcessInstance.STATUS_CLOSED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_CLOSED))?"selected":""%>>Cerrados</option>
                                <option value="<%=ProcessInstance.STATUS_ABORTED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_ABORTED))?"selected":""%>>Abortados</option>
                            </select>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td><a href="<%=configUrl%>">Configurar despliegue</a></td>
                </tr>
            </tbody>
        </table>
        <%
        if (tinstances != null && tinstances.size() > 0) {
            %>
            <br>
            <table>
                <thead>
                    <tr>
                        <%
                        if (displayCols.contains("idCol")) {
                            %><th align="center">ID</th><%
                        }
                        if (displayCols.contains("pnameCol")) {
                            %><th align="center">Proceso</th><%
                        }
                        if (displayCols.contains("nameCol")) {
                            %><th align="center">Tarea</th><%
                        }
                        if (displayCols.contains("sdateCol")) {
                            %><th align="center">Iniciada</th><%
                        }
                        if (displayCols.contains("edateCol")) {
                            %><th align="center">Cerrada</th><%
                        }
                        if (displayCols.contains("actionsCol")) {
                            %><th align="center">Acciones</th><%
                        }
                        %>
                    </tr>
                </thead>
                <tbody>
                    <%
                    Iterator<FlowNodeInstance> instances = tinstances.iterator();
                    while(instances.hasNext()) {
                        FlowNodeInstance instance = instances.next();
                        String status = "<img src=\""+baseimg;
                        String Id = instance.getId();
                        String pName = instance.getFlowNodeType().getProcess().getDisplayTitle(lang);
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
                                %><td align="center"><%=Id%></td><%
                            }
                            if (displayCols.contains("pnameCol")) {
                                %><td align="center"><%=pName%></td><%
                            }
                            if (displayCols.contains("nameCol")) {
                                %><td align="center"><%=tName%></td><%
                            }
                            if (displayCols.contains("sdateCol")) {
                                %><td align="center"><%=pCreated%></td><%
                            }
                            if (displayCols.contains("edateCol")) {
                                %><td align="center"><%=pClosed%></td><%
                            }
                            if (displayCols.contains("actionsCol")) {
                                UserTask utask = (UserTask) instance.getFlowNodeType();

                                %>
                                <td align="center">
                                    <%
                                    if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                        %><a target="_new" href="<%=utask.getTaskWebPage().getUrl()%>?suri=<%=instance.getEncodedURI()%>">Atender</a><%
                                    } else {
                                        %>--<%
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
            <p>
                P&aacute;gina:
            <%
            for (int i = 1; i <= maxPages; i++) {
                if (pageNum == i) {
                    %><%=i%> <%
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
            </p>
            <%
    }
}
%>

