<%-- 
    Document   : Documentation
    Created on : 8/08/2013, 06:04:05 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.process.model.Containerable"%>
<%@page import="org.apache.velocity.runtime.directive.Break"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.process.model.SubProcess"%>
<%@page import="org.semanticwb.process.model.Event"%>
<%@page import="org.semanticwb.process.model.Gateway"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.Lane"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.process.model.GraphicalElement"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.model.ProcessElement"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
<link href="/swbadmin/jsp/process/documentation/styles/css/process.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/swbadmin/jsp/process/documentation/styles/css/processimprimir.css" type="text/css" media="print"/>
<link rel="stylesheet" href="/swbadmin/jsp/process/formsBuilder.css" type="text/css"/>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL urlDocumentation = paramRequest.getRenderUrl().setMode("viewDocumentation");
    SWBResourceURL urlExport = paramRequest.getRenderUrl().setMode("doExportDocument");
    String suri = request.getAttribute("suri") != null ? request.getAttribute("suri").toString() : "";
    ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
    ArrayList lane = new ArrayList();
    ArrayList activity = new ArrayList();
    ArrayList gateway = new ArrayList();
    ArrayList event = new ArrayList();
    Iterator<GraphicalElement> iterator = null;
    GraphicalElement ge = null;
    org.semanticwb.process.model.Process process = null;
    SubProcess subProcess = null;
    Containerable con = null;
    String path = "";
    if (pe != null) {
        if (pe instanceof org.semanticwb.process.model.Process) {
            process = (org.semanticwb.process.model.Process) pe;
            iterator = process.listContaineds();
        }
        if (pe instanceof SubProcess) {
            subProcess = (SubProcess) pe;
            iterator = subProcess.listContaineds();
            con = subProcess.getContainer();
            while (con != null) {
                path = ((ProcessElement) con).getURI() + "|" + path;
                if (con instanceof SubProcess) {
                    con = ((SubProcess) con).getContainer();
                } else {
                    con = null;
                }
            }
            path = pe.getURI() + "|" + path;
        }
        while (iterator.hasNext()) {
            ge = iterator.next();
            if (ge instanceof Lane) {
                lane.add(ge);
            }
            if (ge instanceof Activity) {
                activity.add(ge);
            }
            if (ge instanceof Gateway) {
                gateway.add(ge);
            }
            if (ge instanceof Event) {
                event.add(ge);
            }
        }
%>
<!--<div id="toast" style="color: white; right: 60%; top: 5%; display: none; position: fixed; border: 1px solid #CCCCCC;background-color: green;padding: 10px 10px ;text-align:center;opacity: 0.1;border-radius:8px 8px 8px 8px;-webkit-transition: opacity 0.1s ease-out;  /* Saf3.2+, Chrome */-moz-transition: opacity 0.1s ease-out;  /* FF4+ */-ms-transition: opacity 0.1s ease-out;  /* IE10? */-o-transition: opacity 0.1s ease-out;  /* Opera 10.5+ */transition: opacity 0.5s ease-out;"></div>-->
<div id="toast" style="background: #B40404; color: white; padding: 10px 40px 10px 40px; right: 40%; top: 5%; display: none; position: fixed; text-align:center; border-radius:8px 8px 8px 8px;"></div>
<div id="contenedor">
    <div id="header" title="<%out.print(pe.getTitle());%>"><%out.print(pe.getTitle());%><img src="/swbadmin/jsp/process/documentation/styles/css/images/logoprocess.png"></div>
    <div id="menu">
        <!--ACTIVIDADES-->
        <ul>
            <li><a href="#ruta" title="Inicio">Inicio</a></li>
                <%
                    iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                    if (lane.size() > 0) {
                        out.print("<li class=\"activity\" title=\"Lane\">Lane</li>");
                    }
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                %>
            <li><a <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setMode("viewDocumentation").setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%> title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                    iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                    if (activity.size() > 0) {
                        out.print("<li class=\"activity\" title=\"Activity\">Activity</li>");
                    }
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                %>
            <li><a style="cursor: pointer;" <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%> title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                    iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                    if (gateway.size() > 0) {
                        out.print("<li class=\"activity\" title=\"Gateway\">Gateway</li>");
                    }
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                %>
            <li><a href="#<%=ge.getURI()%>" title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                    iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                    if (event.size() > 0) {
                        out.print("<li class=\"activity\" title=\"Event\">Event</li>");
                    }
                    while (iterator.hasNext()) {

                        ge = iterator.next();
                %>
            <li><a href="#<%=ge.getURI()%>" title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                %>
        </ul>
    </div>
    <div id="contenido">
        <div id="ruta">
            <label>Estás en:</label>
            <%
                String[] urls = path.split("\\|");
                int cont = urls.length;
                if (urls.length > 1) {
                    for (int i = 0; i < urls.length; i++) {
                        ProcessElement peAux = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urls[i]);
                        String title = peAux.getTitle();
            %>
            <a style="cursor: pointer;" onclick="window.location = '<%=urlDocumentation.setParameter("suri", peAux.getURI())%>';" title="<%=title%>"><%out.println(title);%></a>
            <% if ((i + 1) < cont) {
                        out.println("<label> | </label>");
                    }
                }
            } else {%>
            <a style="cursor: pointer;" href="#">
                <%out.println(pe.getTitle());%></a><%
                    }
                %>
        </div>
        <a onclick="exportDocument('<%=urlExport%>', 'html');" style="cursor: pointer;" title="HTML" id="html">HTML</a>
        <a onclick="exportDocument('<%=urlExport%>', 'pdf');" style="cursor: pointer;" title="PDF" id="pdf" >PDF</a>
        <a title="imprimir" id="imprimir" href="javascript:print()">Imprimir</a>
        <div >
            <%String data = "";
                if (process != null) {
                    data = process.getData() != null ? process.getData() : "No se ha generado imagen";
                } else {
                    data = subProcess.getData() != null ? subProcess.getData() : "No se ha generado imagen";
                }
                out.println(data);%>
            <!--<img src="/work/models/SWBAdmin/images/image.svg" width="100%" height="100%"/>-->
        </div>
        <div id="texto"> 
            <%
                out.print(pe.getDocumentation().getText());
                iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                if (lane.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"Lane\"><strong>Lane</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                }
                iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                if (activity.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"Avtivity\"><strong>Activity</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                }
                iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                if (gateway.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"Gateway\"><strong>Gateway</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                }
                iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                if (event.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"Event\"><strong>Event</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                }
            %>
            <!-- <br><br>
           AQUI ACABA EL TEXTO<BR>-->
        </div>

    </div>
    <div class="clear"> </div>
</div>
<%}
%>
<script type="text/javascript">
                function exportDocument(url, form) {
                    //alert("Se envía información " + form + url);
                    dojo.xhrPost({
                        url: url,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        content: {format: form, suri: '<%=suri%>'},
                        load: function(response, ioArgs)
                        {
                            //alert('se exportó');
                            showToast("Se generó " + form);
                            return response;
                        },
                        error: function(response, ioArgs) {
                            alert('error al exportar');
                            return response;
                        },
                        handleAs: "text"
                    });
                }
                var intCounterToast = 0;
                function showToast(msg) {
                    var toast = document.getElementById("toast");
                    toast.style.display = "block";
                    //toast.style.opacity = 0.5;
                    toast.style.visibility = "visible";
                    toast.innerHTML = "<h3>" + msg + "</h3>";
                    intCounterToast = setInterval("hideToast()", 2500);
                }
                function hideToast() {
                    var toast = document.getElementById("toast");
                    toast.style.display = "none";
                    clearInterval(intCounterToast);
                    //toast.style.opacity = 0;
                }
</script>