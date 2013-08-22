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
<link href="/swbadmin/jsp/process/nv.d3.css" rel="stylesheet">
<script src="/swbadmin/jsp/process/d3.v3.min.js" charset="utf-8"></script>
<script src="/swbadmin/jsp/process/nv.d3.js" charset="utf-8"></script>
<script type="text/javascript">
    var d3Color = d3.scale.category10();
</script>
<%if (showInstances) {%>
    <div class="processChartPie" id="performanceGraph"><svg></svg></div>
    <script>
        nv.addGraph(function() {
            var w = d3.select("#performanceGraph").style("width").replace("px","");
            var h = d3.select("#performanceGraph").style("height").replace("px","");
            var data = [
                {
                    key:"<%=paramRequest.getLocaleString("lblInstances")%>",
                    values: [
                        {
                            "label":"<%=paramRequest.getLocaleString("lblProcessing")%>",
                            "value":<%=processing%>
                        },
                        {
                            "label":"<%=paramRequest.getLocaleString("lblClosed")%>",
                            "value":<%=closed%>
                        },
                        {
                            "label":"<%=paramRequest.getLocaleString("lblAborted")%>",
                            "value":<%=aborted%>
                        }
                    ]
                }
            ];
        
            var chart = nv.models.pieChart()
                .x(function(d) { return d.label; })
                .y(function(d) { return d.value; })
                .showLabels(false)
                .showLegend(false)
                .color(function(d,i){
                    return d3Color(i);
                });

            d3.select("#performanceGraph svg")
                .attr("width", w)
                .attr("height", h)
                .datum(data)
                .transition().duration(1200)
                .call(chart);

            d3.select("#performanceGraph svg")
                .append("svg:text")
                .text("<%=paramRequest.getLocaleString("lblInstances")%> (<%=total%>)")
                .attr("x",function() {
                    return ""+(w/2);
                })
                .attr("text-anchor","middle")
                .attr("y","22.85")
                .attr("font-family", "Arial")
                .attr("font-size", "11")
                .attr("font-weight", "bold")
                .attr("stroke", "none")
                .attr("fill", "#000000");

            return chart;
        });
    </script>
<%
}

if (showResponse) {%>
    <div class="processChartPie" id="responseTime"><svg></svg></div>
    <script>
        nv.addGraph(function() {
            var w = d3.select("#responseTime").style("width").replace("px","");
            var h = d3.select("#responseTime").style("height").replace("px","");
            var data = [
                {
                    key:"<%=paramRequest.getLocaleString("lblResponsetime")%>",
                    values: [
                        {
                            "label":"<%=paramRequest.getLocaleString("lblMin")%>",
                            "value":<%=minTime%>
                        },
                        {
                            "label":"<%=paramRequest.getLocaleString("lblMax")%>",
                            "value":<%=maxTime%>
                        },
                        {
                            "label":"<%=paramRequest.getLocaleString("lblAvg")%>",
                            "value":<%=avgTime%>
                        }
                    ]
                }
            ];
        
            var chart = nv.models.pieChart()
                .x(function(d) { return d.label; })
                .y(function(d) { return d.value; })
                .showLabels(false)
                .showLegend(false)
                .color(function(d,i){
                    return d3Color(i);
                });

            d3.select("#responseTime svg")
                .attr("width", w)
                .attr("height", h)
                .datum(data)
                .transition().duration(1200)
                .call(chart);

            d3.select("#responseTime svg")
                .append("svg:text")
                .text("<%=paramRequest.getLocaleString("lblResponsetime")%> (<%=paramRequest.getLocaleString("lblMinutes")%>)")
                .attr("x",function() {
                    return ""+(w/2);
                })
                .attr("text-anchor","middle")
                .attr("y","22.85")
                .attr("font-family", "Arial")
                .attr("font-size", "11")
                .attr("font-weight", "bold")
                .attr("stroke", "none")
                .attr("fill", "#000000");

            return chart;
        });
    </script>
<%
}

if (showStatus) {%>
    <div class="processChartPie" id="overdueGraph"><svg></svg></div>
    <script>
        nv.addGraph(function() {
            var w = d3.select("#overdueGraph").style("width").replace("px","");
            var h = d3.select("#overdueGraph").style("height").replace("px","");
            var data = [
                {
                    key:"<%=paramRequest.getLocaleString("lblOverdue")%>",
                    values: [
                        {
                            "label":"<%=paramRequest.getLocaleString("lblOntime")%>",
                            "value":<%=ontime%>
                        },
                        {
                            "label":"<%=paramRequest.getLocaleString("lblDelayed")%>",
                            "value":<%=delayed%>
                        }
                    ]
                }
            ];
        
            var chart = nv.models.pieChart()
                .x(function(d) { return d.label; })
                .y(function(d) { return d.value; })
                .showLabels(false)
                .showLegend(false)
                .color(function(d,i){
                    return d3Color(i);
                });

            d3.select("#overdueGraph svg")
                .attr("width", w)
                .attr("height", h)
                .datum(data)
                .transition().duration(1200)
                .call(chart);

            d3.select("#overdueGraph svg")
                .append("svg:text")
                .text("<%=paramRequest.getLocaleString("lblOverdue")%>")
                .attr("x",function() {
                    return ""+(w/2);
                })
                .attr("text-anchor","middle")
                .attr("y","22.85")
                .attr("font-family", "Arial")
                .attr("font-size", "11")
                .attr("font-weight", "bold")
                .attr("stroke", "none")
                .attr("fill", "#000000");

            return chart;
        });
    </script>
<%}
%>