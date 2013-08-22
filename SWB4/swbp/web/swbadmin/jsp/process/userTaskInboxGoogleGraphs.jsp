<%-- 
    Document   : userTaskInboxGoogleGraphs
    Created on : 30/07/2013, 12:57:38 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
Resource base = (Resource) request.getAttribute("base");

int aborted = (Integer)request.getAttribute("aborted");
int processing = (Integer)request.getAttribute("processing");
int closed = (Integer)request.getAttribute("closed");
int total = aborted+processing+closed;

long minTime = (Long)request.getAttribute("minTime");
long maxTime = (Long)request.getAttribute("maxTime");
long avgTime = (Long)request.getAttribute("avgTime");

int ontime = (Integer)request.getAttribute("ontime");
int delayed = (Integer)request.getAttribute("delayed");

boolean showInstances = base.getAttribute(UserTaskInboxResource.ATT_INSTANCEGRAPH,"").equals("use");
if (showInstances) {
    if (processing == 0 && closed == 0 && aborted == 0) {
        showInstances = false;
    }
}

boolean showResponse = base.getAttribute(UserTaskInboxResource.ATT_RESPONSEGRAPH,"").equals("use");
if (showResponse) {
    if (minTime == 0 && maxTime == 0 && avgTime == 0) {
        showResponse = false;
    }
}

boolean showStatus = base.getAttribute(UserTaskInboxResource.ATT_STATUSGRAPH,"").equals("use");
if (showResponse) {
    if (delayed == 0 && ontime == 0) {
        showResponse = false;
    }
}
%>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
        var options = {
          title: '<%=paramRequest.getLocaleString("lblInstances")%> (<%=total%>)',
          backgroundColor: {fill:'none'}
        };

        <%
        if (showInstances) {
            %>
            var data = google.visualization.arrayToDataTable([
                ['Estatus', 'Unidades'],
                ['<%=paramRequest.getLocaleString("lblProcessing")%>',     <%=processing%>],
                ['<%=paramRequest.getLocaleString("lblClosed")%>',     <%=closed%>],
                ['<%=paramRequest.getLocaleString("lblAborted")%>',      <%=aborted%>]
            ]);
            var chart = new google.visualization.PieChart(document.getElementById('performanceGraph'));
            chart.draw(data, options);
        <%
        }

        if (showResponse) {
            %>
            var data2 = google.visualization.arrayToDataTable([
                ['Tiempo de respuesta', 'Horas'],
                ['<%=paramRequest.getLocaleString("lblMin")%>',     <%=minTime%>],
                ['<%=paramRequest.getLocaleString("lblMax")%>',     <%=maxTime%>],
                ['<%=paramRequest.getLocaleString("lblAvg")%>',      <%=avgTime%>]
            ]);

            var chart2 = new google.visualization.PieChart(document.getElementById('responseTime'));
            options.title = "<%=paramRequest.getLocaleString("lblResponsetime")%> (<%=paramRequest.getLocaleString("lblMinutes")%>)";
            options.pieSliceText = "value";
            chart2.draw(data2, options);
            <%
        }

        if (showStatus) {
            %>
            var data3 = google.visualization.arrayToDataTable([
                ['Estado', 'Valor'],
                ['<%=paramRequest.getLocaleString("lblDelayed")%>', <%=delayed%>],
                ['<%=paramRequest.getLocaleString("lblOntime")%>', <%=ontime%>]
            ]);

            var chart3 = new google.visualization.PieChart(document.getElementById('overdueGraph'));
            options.title = "<%=paramRequest.getLocaleString("lblOverdue")%>";
            options.pieSliceText = "percent";
            chart3.draw(data3, options);
            <%
        }
        %>
    }
</script>
<%if (showInstances) {%>
    <div class="processChartPie" id="performanceGraph"></div>
<%}
if (showResponse) {%>
    <div class="processChartPie" id="responseTime"></div>
<%}
if (showStatus) {%>
    <div class="processChartPie" id="overdueGraph"></div>
<%}
%>