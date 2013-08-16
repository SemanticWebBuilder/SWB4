<%-- 
    Document   : userTaskInboxDetail
    Created on : 4/07/2013, 10:14:08 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.model.Resource"%>
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
WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();
Resource base = (Resource) request.getAttribute("base");

String lang = "es";
String pNum = request.getParameter("page");
String suri = request.getParameter("suri");
boolean showGraphs = false;
if (base != null && base.getAttribute(UserTaskInboxResource.ATT_SHOWPERFORMANCE,"").equals("yes")) {
    showGraphs = true;
}

boolean showParticipation  = false;
if (base != null && base.getAttribute(UserTaskInboxResource.ATT_PARTGRAPH,"").equals("use")) {
    showParticipation = true;
}

String engine = "google";
if (base != null && base.getAttribute(UserTaskInboxResource.ATT_GRAPHSENGINE, "").equals("d3")) {
    engine = "d3";
}

int pageNum = 1;

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
        <p class="lat-iniciar"><a onclick="showDialog('<%=createPiUrl%>','<%=paramRequest.getLocaleString("createCase")%>'); return false;" href=""><%=paramRequest.getLocaleString("createCase")%></a></p>
        <ul class="lat-lista1">
            <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_PROCESSING));%>
            <li class="lat-pend"><a href="<%=optsUrl%>"><%=paramRequest.getLocaleString("pendingTasks")%></a></li>
            <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_CLOSED));%>
            <li class="lat-term"><a href="<%=optsUrl%>"><%=paramRequest.getLocaleString("closedTasks")%></a></li>
            <%optsUrl.setParameter("sFilter", String.valueOf(FlowNodeInstance.STATUS_ABORTED));
            
            optsUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(UserTaskInboxResource.MODE_GETDATA);
            optsUrl.setParameter("suri", suri);
            optsUrl.setParameter("tu", "min");
            %>
            <li class="lat-term"><a href="<%=optsUrl%>"><%=paramRequest.getLocaleString("abortedTasks")%></a></li>
        </ul>
    </div>
    <%
} else {
    ArrayList<ProcessInstance> tinstances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
    int maxPages = (Integer) request.getAttribute("maxPages");
    
    if (pNum != null && !pNum.trim().equals("")) {
        pageNum = Integer.valueOf(request.getParameter("page"));
        if (pageNum > maxPages) {
            pageNum = maxPages;
        }
    }
    
    String processInfo = (String)request.getAttribute("participation");
    Process p = (Process)ont.getGenericObject(suri);
    if (p == null) {%>
        <script>
            window.location='<%=viewUrl%>';
        </script>
    <%} else {%>
        <h1><%=p.getTitle()%>&nbsp;<a href="<%=viewUrl%>"><img alt="volver" src="/work/models/<%=site.getId()%>/css/images/icono-atras.png"/></a></h1>
        <p><%=p.getDescription()==null?"":p.getDescription()%></p>
        <p>&nbsp;</p> 
        <%if (tinstances != null && !tinstances.isEmpty()) {
            if (showGraphs) {
                %><div class="bandeja-combo"><strong><span style="font-size: medium"><%=paramRequest.getLocaleString("lblPerformance")%></span></strong></div><%
                
                if (engine.equals("google")) {
                    %><jsp:include page="/swbadmin/jsp/process/userTaskInboxGoogleGraphs.jsp" flush="true"/><%
                } else {
                    %><jsp:include page="/swbadmin/jsp/process/userTaskInboxD3Graphs.jsp" flush="true"/><%
                }
            
                if (showParticipation) {%>
                    <div class="processChartPie" id="participationGraph"></div>
                    <script src="/swbadmin/jsp/process/d3.v3.min.js" charset="utf-8"></script>
                    <script type="text/javascript">
                        var theJson = JSON.parse('<%=processInfo%>');
                        var w = d3.select("#participationGraph").style("width").replace("px","");
                        var h = d3.select("#participationGraph").style("height").replace("px","");

                        function updateD3Chart () {
                            var nodes = flatten(root);
                            var links = d3.layout.tree().links(nodes);
                            var max = root['max'];

                            root.fixed = true;
                            root.x = w/2;
                            root.y = h/2;

                            var linkScale = d3.scale.linear()
                                .domain([1,max])
                                .range([1,5]);

                            force.nodes(nodes)
                                .links(links)
                                .start();

                            var link = vis.selectAll("line.link")
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

                            var node = vis.selectAll("g.node")
                                .data(nodes, function(d) {
                                    return d.id;
                                });

                            var nodeEnter = node.enter().append("svg:g")
                                .attr("class", "node")
                                .attr("transform", function(d) {
                                    return "translate(" + d.x + "," + d.y + ")";
                                })
                                .call(force.drag);

                            /*nodeEnter.append("svg:circle")
                                .attr("r", function(d) {
                                    return  d.size;
                                })
                                .on("click", click)
                                .on("mouseover", function(d) {
                                    d3.select(this)
                                    //.transition()
                                    .attr("r", function(d) {
                                        console.log("on selected "+d.size);
                                        return d.size * 2;
                                    });
                                    console.log("over "+d.name);
                                })
                                .on("mouseout", function(d) {
                                    d3.select(this)
                                    .attr("r", function(d) {
                                        console.log("on unselected "+d.size);
                                        return d.size;
                                    });
                                    console.log("out "+d.name);
                                })
                                .style("fill", color);*/

                            nodeEnter.append("svg:image")
                                .attr("xlink:href", function(d) {
                                    if (d.type && d.type==="process") {
                                        return "<%="/work/models/"+site.getId()+"/css/images/icono-iniciado.gif"%>";
                                    } else {
                                        return "<%="/work/models/"+site.getId()+"/css/images/colaborador.png"%>";
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

                            nodeEnter.append("svg:title")
                                .attr("class", "nodetext")
                                .attr("text-anchor", "middle")
                                .attr("dy", ".15em")
                                .text(function(d,i) {
                                    if (d.participa && d.participa !== null) {
                                        return d.participa +" <%=paramRequest.getLocaleString("lblPartTooltip")%>";
                                    } else {
                                        return d.name;
                                    }
                                });

                            node.exit().remove();

                            link = vis.selectAll("line.link");
                            node = vis.selectAll("g.node");

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
                        }

                        function click(d) {
                            if (d.children) {
                                d._children = d.children;
                                d.children = null;
                            } else {
                                d.children = d._children;
                                d._children = null;
                            }
                            updateD3Chart();
                        }

                        function flatten(root) {
                            var nodes = [], i = 0;
                            function recurse(node) {
                                if (node.children)
                                    node.children.forEach(recurse);
                                if (!node.id)
                                    node.id = ++i;
                                nodes.push(node);
                            }
                            recurse(root);
                            return nodes;
                        }

                        var force = d3.layout.force()
                            .charge(-60)
                            .distance(80)
                            .gravity(.05)
                            //.linkDistance(50)
                            .size([w, h]);

                        var vis = d3.select("#participationGraph").append("svg:svg")
                            .attr("width", w)
                            .attr("height", h);

                        vis.append("svg:text")
                            .text("<%=paramRequest.getLocaleString("lblParticipation")%>")
                            .attr("x","67")
                            .attr("y","22.85")
                            .attr("font-family", "Arial")
                            .attr("font-size", "11")
                            .attr("font-weight", "bold")
                            .attr("stroke", "none")
                            .attr("fill", "#000000");

                        var root = theJson;
                        updateD3Chart();
                    </script>
                <%
                }
            }
            %>
            <div class="bandeja-combo"><strong><span style="font-size: medium"><%=paramRequest.getLocaleString("lblInstances")%></span></strong></div>
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

                            if (pi.getStatus() == ProcessInstance.STATUS_PROCESSING) status = paramRequest.getLocaleString("pStatusPending");
                            if (pi.getStatus() == ProcessInstance.STATUS_ABORTED) status = paramRequest.getLocaleString("pStatusAborted");
                            if (pi.getStatus() == ProcessInstance.STATUS_CLOSED) status = paramRequest.getLocaleString("pStatusClosed");
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
                <div class="pagtotal"><%=paramRequest.getLocaleString("pagPage")%> <%=pageNum%> <%=paramRequest.getLocaleString("pagDelim")%> <%=maxPages%></div>
                <div class="pagLista">
                <%if (pageNum-1 > 0) {
                    SWBResourceURL back = paramRequest.getRenderUrl();
                    back.setParameter("page", String.valueOf(pageNum-1));
                    back.setParameter("suri", suri);
                    %><span class="pagant"><a href="<%=back%>"><%=paramRequest.getLocaleString("pagPrev")%></a></span><%
                }
                if (pageNum+1 <= maxPages) {
                    SWBResourceURL forward = paramRequest.getRenderUrl();
                    forward.setParameter("page", String.valueOf(pageNum+1));
                    forward.setParameter("suri", suri);
                    %><span class="pagsig"><a href="<%=forward%>"><%=paramRequest.getLocaleString("pagNext")%></a></span><%
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