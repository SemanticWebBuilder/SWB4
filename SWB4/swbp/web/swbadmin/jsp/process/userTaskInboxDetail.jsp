<%-- 
    Document   : userTaskInboxDetail
    Created on : 4/07/2013, 10:14:08 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.SWBProcessMgr"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="utf-8" src="/swbadmin/jsp/process/userTaskInboxUtils.js"></script>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
ArrayList<ProcessInstance> tinstances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();

String lang = "es";
String pNum = request.getParameter("page");
String suri = request.getParameter("suri");
int maxPages = (Integer) request.getAttribute("maxPages");
int pageNum = 1;
int aborted = (Integer)request.getAttribute("aborted");
int processing = (Integer)request.getAttribute("processing");
int closed = (Integer)request.getAttribute("closed");
int total = aborted+processing+closed;

long minTime = (Long)request.getAttribute("minTime");
long maxTime = (Long)request.getAttribute("maxTime");
long avgTime = (Long)request.getAttribute("avgTime");

int ontime = (Integer)request.getAttribute("ontime");
int delayed = (Integer)request.getAttribute("delayed");

if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(request.getParameter("page"));
    if (pageNum > maxPages) {
        pageNum = maxPages;
    }
}

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}

SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW);
SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

SWBResourceURL createPiUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
createPiUrl.setMode(UserTaskInboxResource.MODE_CREATEPI);

SWBResourceURL optsUrl = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW);
if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {%>
    <div class="lateral">
        <p class="lat-iniciar"><a onclick="showDialog('<%=createPiUrl%>','Crear instancia'); return false;" href="">Iniciar proceso</a></p>
        <ul class="lat-lista1">
            <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_PROCESSING));%>
            <li class="lat-pend"><a href="<%=optsUrl%>">Tareas pendientes</a></li>
            <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_CLOSED));%>
            <li class="lat-term"><a href="<%=optsUrl%>">Tareas terminadas</a></li>
            <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_ABORTED));
            
            optsUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(UserTaskInboxResource.MODE_GETDATA);
            optsUrl.setParameter("suri", suri);
            optsUrl.setParameter("tu", "min");
            %>
            <li class="lat-term"><a href="<%=optsUrl%>">Tareas abortadas</a></li>
        </ul>
    </div>
    <%
} else {
    Process p = (Process)ont.getGenericObject(suri);
    if (p == null) {%>
        <script>
            window.location='<%=viewUrl%>';
        </script>
    <%} else {%>
        <h1><%=p.getTitle()%>&nbsp;<a href="<%=viewUrl%>"><img alt="volver" src="/work/models/<%=site.getId()%>/css/images/icono-atras.png"/></a></h1>
        <p><%=p.getDescription()==null?"":p.getDescription()%></p>
        <p>&nbsp;</p> 
        <div class="bandeja-combo"><strong><span style="font-size: medium">Desempe&ntilde;o</span></strong></div>
        <%if (tinstances != null && !tinstances.isEmpty()) {%>
            <script type="text/javascript" src="https://www.google.com/jsapi"></script>
            <script type="text/javascript">
                google.load("visualization", "1", {packages:["corechart"]});
                google.setOnLoadCallback(drawChart);
                function drawChart() {
                    var data = google.visualization.arrayToDataTable([
                        ['Estatus', 'Unidades'],
                        ['En proceso',     <%=processing%>],
                        ['Cerrados',     <%=closed%>],
                        ['Abortados',      <%=aborted%>]
                    ]);

                    var data2 = google.visualization.arrayToDataTable([
                        ['Tiempo de respuesta', 'Horas'],
                        ['Mínimo',     <%=minTime%>],
                        ['Máximo',     <%=maxTime%>],
                        ['Promedio',      <%=avgTime%>]
                    ]);

                    var data3 = google.visualization.arrayToDataTable([
                        ['Estado', 'Valor'],
                        ['Retrasadas', <%=delayed%>],
                        ['A tiempo', <%=ontime%>]
                    ]);

                    var options = {
                      title: 'Instancias de proceso (<%=total%>)',
                      backgroundColor: {fill:'none'}
                    };

                    var chart = new google.visualization.PieChart(document.getElementById('performanceGraph'));
                    var chart2 = new google.visualization.PieChart(document.getElementById('responseTime'));
                    var chart3 = new google.visualization.PieChart(document.getElementById('overdueGraph'));
                    chart.draw(data, options);

                    options.title = "Tiempo de respuesta (min)";
                    options.pieSliceText = "value";
                    chart2.draw(data2, options);

                    options.title = "Estatus de ejecución";
                    options.pieSliceText = "percent";
                    chart3.draw(data3, options);
                }
            </script>
            <div class="processChartPie" id="performanceGraph"></div>
            <div class="processChartPie" id="responseTime"></div>
            <div class="processChartPie" id="overdueGraph"></div>
            <div class="bandeja-combo"><strong><span style="font-size: medium">Instancias del proceso</span></strong></div>
            <div>
                <table class="tabla-bandeja">
                    <thead>
                        <tr>
                            <th class="tban-id">ID</th>
                            <th class="tban-id">Creador</th>
                            <th class="tban-id">Estatus</th>
                            <th class="tban-id">Iniciado</th>
                            <th class="tban-id">Cerrado</th>
                            <th class="tban-id">Tareas activas</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%Iterator<ProcessInstance> it = tinstances.iterator();
                        while (it.hasNext()) {
                            ProcessInstance pi = it.next();
                            String status = "--";

                            if (pi.getStatus() == ProcessInstance.STATUS_PROCESSING) status = "En proceso";
                            if (pi.getStatus() == ProcessInstance.STATUS_ABORTED) status = "Abortado";
                            if (pi.getStatus() == ProcessInstance.STATUS_CLOSED) status = "Cerrado";
                            %>
                            <tr>
                                <td class="tban-id"><%=pi.getId()%></td>
                                <td class="tban-tarea"><%=pi.getCreator()==null?"--":pi.getCreator().getFullName()%></td>
                                <td class="tban-tarea"><%=status%></td>
                                <td class="tban-inicia"><%=SWBUtils.TEXT.getStrDate(pi.getCreated(), lang, "dd/mm/yy - hh:%m:ss")%></td>
                                <td class="tban-cerrada"><%=pi.getEnded()==null?"--":SWBUtils.TEXT.getStrDate(pi.getEnded(), lang, "dd/mm/yy - hh:%m:ss")%></td>
                                <td class="tban-tarea">
                                    <%
                                    if (pi.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                        Iterator<FlowNodeInstance> actit = pi.listAllFlowNodeInstance();
                                        ArrayList<FlowNodeInstance> activities = new ArrayList<FlowNodeInstance>();
                                        if (actit.hasNext()) {
                                            while(actit.hasNext()) {
                                                FlowNodeInstance fni = actit.next();
                                                if (fni.getFlowNodeType() instanceof Activity && fni.getStatus() == FlowNodeInstance.STATUS_PROCESSING) {
                                                    activities.add(fni);
                                                }
                                            }
                                        }

                                        if (!activities.isEmpty()) {
                                            actit = activities.iterator();
                                            %>
                                            <ul>
                                                <%while(actit.hasNext()) {
                                                    FlowNodeInstance fni = actit.next();
                                                    %><li><%=fni.getFlowNodeType().getTitle()%></li><%
                                                }
                                                %>
                                            </ul>
                                          <%
                                        } else {
                                            %>--<%
                                        }
                                    } else {
                                        %>--<%
                                    }
                                    %>
                                </td>
                            </tr>
                            <%
                        } 
                        %>
                    </tbody>
                </table>
            </div>
            <div class="paginado">
                <div class="pagtotal">P&aacute;gina <%=pageNum%> de <%=maxPages%></div>
                <div class="pagLista">
                <%if (pageNum-1 > 0) {
                    SWBResourceURL back = paramRequest.getRenderUrl();
                    back.setParameter("page", String.valueOf(pageNum-1));
                    back.setParameter("suri", suri);
                    %><span class="pagant"><a href="<%=back%>">Anterior</a></span><%
                }
                if (pageNum+1 <= maxPages) {
                    SWBResourceURL forward = paramRequest.getRenderUrl();
                    forward.setParameter("page", String.valueOf(pageNum+1));
                    forward.setParameter("suri", suri);
                    %><span class="pagsig"><a href="<%=forward%>">Siguiente</a></span><%
                }%>
                </div>
            </div>
        <%
        }
    }
    %>
    <script type="text/javascript">
        hideDialog();
    </script>
    <%
}
%>