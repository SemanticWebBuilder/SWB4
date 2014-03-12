<%-- 
    Document   : userTaskInboxD3Graphs
    Created on : 30/07/2013, 12:57:38 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.process.resources.taskinbox.UserTaskInboxResource"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
Resource base = paramRequest.getResourceBase();
String processInfo = (String)request.getAttribute("participation");

int aborted = (Integer)request.getAttribute("aborted");
int processing = (Integer)request.getAttribute("processing");
int closed = (Integer)request.getAttribute("closed");
int total = aborted+processing+closed;

long minTime = (Long)request.getAttribute("minTime");
long maxTime = (Long)request.getAttribute("maxTime");
long avgTime = (Long)request.getAttribute("avgTime");

int ontime = (Integer)request.getAttribute("ontime");
int delayed = (Integer)request.getAttribute("delayed");

boolean showInstances = "use".equals(base.getAttribute(UserTaskInboxResource.ATT_INSTANCEGRAPH));
if (showInstances) {
    if (processing == 0 && closed == 0 && aborted == 0) {
        showInstances = false;
    }
}

boolean showResponse = "use".equals(base.getAttribute(UserTaskInboxResource.ATT_RESPONSEGRAPH));
if (showResponse) {
    if (minTime == 0 && maxTime == 0 && avgTime == 0) {
        showResponse = false;
    }
}

boolean showStatus = "use".equals(base.getAttribute(UserTaskInboxResource.ATT_STATUSGRAPH));
if (showStatus) {
    if (delayed == 0 && ontime == 0) {
        showStatus = false;
    }
}

boolean showParticipation  = "use".equals(base.getAttribute(UserTaskInboxResource.ATT_PARTGRAPH));
if (showParticipation) {
    if (processInfo == null) {
        showParticipation = false;
    }
}

if (showInstances) {%>
    <div class="col-xs-6 col-sm-6 col-md-3">
        <div id="performanceGraph"></div>
    </div>
    <script>
        var data = [{"label":"<%=paramRequest.getLocaleString("lblProcessing")%>","value":<%=processing%>},
            {"label":"<%=paramRequest.getLocaleString("lblClosed")%>","value":<%=closed%>},
            {"label":"<%=paramRequest.getLocaleString("lblAborted")%>","value":<%=aborted%>}
        ];
        updateChart("#performanceGraph", "<%=paramRequest.getLocaleString("lblInstances")%>(<%=total%>)", data);
    </script>
<%
}
if (showResponse) {%>
    <div class="col-xs-6 col-sm-6 col-md-3">
        <div class="processChartPie" id="responseTime"></div>
    </div>
    <script>
        var data2 = [{"label":"<%=paramRequest.getLocaleString("lblMin")%>","value":<%=minTime%>},
            {"label":"<%=paramRequest.getLocaleString("lblMax")%>","value":<%=maxTime%>},
            {"label":"<%=paramRequest.getLocaleString("lblAvg")%>","value":<%=avgTime%>}
        ];
        updateChart("#responseTime", "<%=paramRequest.getLocaleString("lblResponsetime")%>", data2);
    </script>
<%
}
if (showStatus) {%>
    <div class="col-xs-6 col-sm-6 col-md-3">
        <div class="processChartPie" id="overdueGraph"></div>
    </div>
    <script>
        var data3 = [{"label":"<%=paramRequest.getLocaleString("lblOntime")%>","value":<%=ontime%>},
                    {"label":"<%=paramRequest.getLocaleString("lblDelayed")%>","value":<%=delayed%>}
        ];
        updateChart("#overdueGraph", "<%=paramRequest.getLocaleString("lblOverdue")%>", data3);
    </script>
<%
}
if (showParticipation) {%>
    <div class="col-xs-6 col-sm-6 col-md-3">
        <div class="processChartPie" id="participationGraph"></div>
    </div>
    <script>
        var theJson = JSON.parse('<%=processInfo%>');
        var root = theJson;
        var w=200, h=200;
        
        function flatten(root) {
            var nodes = [], i = 0;
            function recurse(node) {
                if (node.children) node.children.forEach(recurse);
                if (!node.id) node.id = ++i;
                nodes.push(node);
            }
            recurse(root);
            return nodes;
        }
        
        function updatePartChart(chartContainer) {
            var nodes = flatten(root);
            var links = d3.layout.tree().links(nodes);
            var max = root['max'];

            w = h = $(chartContainer).parent().width();

            root.fixed = true;
            root.x = w/2;
            root.y = h/2;

            var force = d3.layout.force()
                .size([w,h])
                .charge(-60)
                .linkDistance(80)
                .gravity(.05);

            $(chartContainer).html("<svg xmlns='http://www.w3.org/2000/svg'></svg>");

            var svg = d3.select(chartContainer+" svg")
                .attr("width", w)
                .attr("height", h)
                .append("g");

            var linkScale = d3.scale.linear()
                .domain([1,max])
                .range([1,5]);

            force.nodes(nodes)
                .links(links)
                .start();

            var link = svg.selectAll("line.link")
                .data(links, function(d) {
                    return d.target.id;
                });

            link.enter().insert("svg:line", ".node")
                .attr("class", "link")
                .attr("x1", function(d) {
                    return d.source.x;
                })
                .attr("y1", function(d) {
                    return d.source.y;
                })
                .attr("x2", function(d) {
                    return d.target.x;
                })
                .attr("y2", function(d) {
                    return d.target.y;
                })
                .attr("stroke-width", function(d) {
                    return linkScale(d.target.participa)+"px";
                })
                .attr("stroke", "#BFBFCF")
                .attr("fill", "none");

            link.exit().remove();

            var node = svg.selectAll("g.node")
                .data(nodes, function(d) {
                    return d.id;
                });

            var nodeEnter = node.enter().append("svg:g")
                .attr("class", "node")
                .attr("transform", function(d) {
                    return "translate(" + d.x + "," + d.y + ")";
                })
                .call(force.drag);

            nodeEnter.append("svg:image")
                .attr("xlink:href", function(d) {
                    if (d.type && d.type==="process") {
                        return "<%=SWBPlatform.getContextPath()%>/work/models/<%=paramRequest.getWebPage().getWebSiteId()%>/css/images/icono-iniciado.gif";
                    } else {
                        return "<%=SWBPlatform.getContextPath()%>/work/models/<%=paramRequest.getWebPage().getWebSiteId()%>/css/images/colaborador.png";
                    }
                })
                .attr("x", -10)
                .attr("y", -10)
                .attr("width", 20)
                .attr("height", 20);

            nodeEnter.append("svg:text")
                .attr("font-family", "Arial")
                .attr("font-size", "11")
                .attr("stroke", "none")
                .attr("fill", "#000000")
                .attr("text-anchor", "middle")
                .attr("dy", "2em")
                .text(function(d) {
                    return d.name;
                });

            node.exit().remove();

            link = svg.selectAll("line.link");
            node = svg.selectAll("g.node");

            force.on("tick", function() {
                link.attr("x1", function(d) {
                    return d.source.x;
                })
                .attr("y1", function(d) {
                    return d.source.y;
                })
                .attr("x2", function(d) {
                    return d.target.x;
                })
                .attr("y2", function(d) {
                    return d.target.y;
                });

                node.attr("transform", function(d) {
                    return "translate(" + d.x + "," + d.y + ")";
                });
            });

            svg.append("svg:text")
                .text("<%=paramRequest.getLocaleString("lblParticipation")%>")
                .attr("x",w/2)
                .attr("y","22.85")
                .style("text-anchor","middle")
                .style("fill","black")
                .style("font-size","10pt")
                .style("font-weight","bold")
        }
        updatePartChart("#participationGraph");
    </script>
<%
}
%>
<script>
    function updateCharts() {
        <%if (showInstances) {%>
        updateChart("#performanceGraph", "Proces instances", data);
        <%}
        if (showResponse) {%>
        updateChart("#responseTime", "Tiempos de respuesta", data2);
        <%}
        if (showStatus) {%>
        updateChart("#overdueGraph", "Estatus de instancias", data3);
        <%}
        if (showParticipation) {%>
        updatePartChart("#participationGraph");
        <%}%>
    }
</script>