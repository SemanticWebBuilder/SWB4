<%-- 
    Document   : Documentation
    Created on : 8/08/2013, 06:04:05 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.process.model.SequenceFlow"%>
<%@page import="org.semanticwb.process.model.ConnectionObject"%>
<%@page import="org.semanticwb.process.model.DataObject"%>
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
    urlExport.setParameter("suri", suri);
    ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
    if (pe.listDocumentations().hasNext()) {
        ArrayList lane = new ArrayList();
        ArrayList activity = new ArrayList();
        ArrayList gateway = new ArrayList();
        ArrayList event = new ArrayList();
        ArrayList dataob = new ArrayList();
        String laneT = paramRequest.getLocaleString("lane") != null ? paramRequest.getLocaleString("lane") : "Lane";
        String activityT = paramRequest.getLocaleString("activity") != null ? paramRequest.getLocaleString("activity") : "Activity";
        String gatewayT = paramRequest.getLocaleString("gateway") != null ? paramRequest.getLocaleString("gateway") : "Gateway";
        String eventT = paramRequest.getLocaleString("event") != null ? paramRequest.getLocaleString("event") : "Event";
        String dataOBT = paramRequest.getLocaleString("data") != null ? paramRequest.getLocaleString("data") : "Data";
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
                path = pe.getURI() + "|" + path;
                while (con != null) {
                    path = ((ProcessElement) con).getURI() + "|" + path;
                    if (con instanceof SubProcess) {
                        con = ((SubProcess) con).getContainer();
                    } else {
                        con = null;
                    }
                }
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
                if (ge instanceof DataObject) {
                    dataob.add(ge);
                }
            }
%>
<div id="toast" style="background: #B40404; color: white; padding: 10px 40px 10px 40px; right: 40%; top: 5%; display: none; position: fixed; text-align:center; border-radius:8px 8px 8px 8px;"></div>
<div id="contenedor">
    <div id="header" title="<%out.print(pe.getTitle());%>"><%out.print(pe.getTitle());%><img src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/documentation/styles/css/images/logoprocess.png"%>"></div>
    <div id="menu">
        <!--ACTIVIDADES-->
        <ul>
            <li><a href="#ruta" title="<%=paramRequest.getLocaleString("home")%>"><%out.println(paramRequest.getLocaleString("home"));%></a></li>
                <%
                    iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                    if (lane.size() > 0) {
                        out.print("<li class=\"activity\" title=\"" + laneT + "\">" + laneT + "</li>");
                    }
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                %>
            <li><a <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setMode("viewDocumentation").setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%> title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                    iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                    if (activity.size() > 0) {
                        out.print("<li class=\"activity\" title=\"" + activityT + "\">" + activityT + "</li>");
                    }
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                %>
            <li><a style="cursor: pointer;" <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%> title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                    iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                    if (gateway.size() > 0) {
                        out.print("<li class=\"activity\" title=\"" + gatewayT + "\">" + gatewayT + "</li>");
                    }
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                        //Connections
                        Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                        while (itConObj.hasNext()) {
                            ConnectionObject connectionObj = itConObj.next();
                            if (connectionObj instanceof SequenceFlow) {%>
            <li><a href="#<%=connectionObj.getURI()%>" title="<%=connectionObj.getTitle()%>"><%out.print(connectionObj.getTitle());%></a></li>
                <%}
                    }%>
            <li><a href="#<%=ge.getURI()%>" title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                    iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                    if (event.size() > 0) {
                        out.print("<li class=\"activity\" title=\"" + eventT + "\">" + eventT + "</li>");
                    }
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                %>
            <li><a href="#<%=ge.getURI()%>" title="<%=ge.getTitle()%>"><%out.print(ge.getTitle());%></a></li>
                <%
                    }
                    iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                    if (dataob.size() > 0) {
                        out.print("<li class=\"activity\" title=\"" + dataOBT + "\">" + dataOBT + "</li>");
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
            <label><%out.print(paramRequest.getLocaleString("theseIn"));%>:</label>
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
        <!--<a onclick="exportDocument('//urlExport%>', 'html');" style="cursor: pointer;" title="HTML" id="html">HTML</a>-->
        <a href="<%=urlExport.setParameter("format", "html")%>" style="cursor: pointer;" title="HTML" id="html">HTML</a>
        <!--<a href="<%//urlExport.setParameter("format", "pdf")%>" style="cursor: pointer;" title="PDF" id="pdf" >PDF</a>-->
        <a title="<%=paramRequest.getLocaleString("print")%>" id="imprimir" href="javascript:print()"><%out.print(paramRequest.getLocaleString("print"));%></a>
        <div >
            <%String data = "";
                if (process != null) {
                    data = process.getData() != null ? process.getData() : paramRequest.getLocaleString("noImage");
                } else {
                    data = subProcess.getData() != null ? subProcess.getData() : paramRequest.getLocaleString("noImage");
                }
                out.println(data);%>
        </div>
        <div id="texto"> 
            <%
                out.print(pe.getDocumentation().getText());
                iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                if (lane.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"" + laneT + "\"><strong>" + laneT + "</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                }
                iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                if (activity.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"" + activityT + "\"><strong>" + activityT + "</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                }
                iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                if (gateway.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"" + gatewayT + "\"><strong>" + gatewayT + "</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                    //Connections
                    Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                    while (itConObj.hasNext()) {
                        ConnectionObject connectionObj = itConObj.next();
                        if (connectionObj instanceof SequenceFlow) {
                            out.println("<h3 id=\"" + connectionObj.getURI() + "\" title=\"" + connectionObj.getTitle() + "\">" + connectionObj.getTitle() + "</h3>");
                            out.println(connectionObj.getDocumentation().getText());
                        }
                    }
                }
                iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                if (event.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"" + eventT + "\"><strong>" + eventT + "</strong></div>");
                }
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    out.println("<h3 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h3>");
                    out.println(ge.getDocumentation().getText());
                }
                iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                if (dataob.size() > 0) {
                    out.print("<div class=\"bandeja-combo\" title=\"" + dataOBT + "\"><strong>" + dataOBT + "</strong></div>");
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
                function exportDocument(url, format) {
                    dojo.xhrPost({
                        url: url,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        content: {format: format, suri: '<%=suri%>'},
                        load: function(response, ioArgs)
                        {
                            showToast("<%=paramRequest.getLocaleString("save")%> " + format);
                            return response;
                        },
                        error: function(response, ioArgs) {
                            showToast("<%=paramRequest.getLocaleString("error")%> " + format);
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
<%} else {
        out.println("<h1>" + paramRequest.getLocaleString("hereDoc") + "</h1>");
    }%>