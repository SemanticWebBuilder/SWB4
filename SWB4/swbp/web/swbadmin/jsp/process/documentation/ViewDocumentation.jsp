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
<!--link rel="stylesheet" href="/swbadmin/jsp/process/formsBuilder.css" type="text/css"/-->
<!--IMPORT BOOTSTRAP-->
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/utils/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/utils/fontawesome/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/taskInbox/css/swbp.css" rel="stylesheet">
<script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/utils/jquery/jquery.min.js"></script>
<script src="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/utils/bootstrap/bootstrap.min.js"></script>
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/documentation/css/style.css" rel="stylesheet">
<script type='text/javascript'> //Activate tooltips
    $(document).ready(function() {
        if ($("[data-toggle=tooltip]").length) {
            $("[data-toggle=tooltip]").tooltip();
        }
        $('body').off('.data-api');
    });
</script>
<!-- END IMPORT BOOTSTRAP -->
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL urlDocumentation = paramRequest.getRenderUrl().setMode("viewDocumentation");
    SWBResourceURL urlExport = paramRequest.getRenderUrl().setMode("doExportDocument");
    String suri = request.getAttribute("suri") != null ? request.getAttribute("suri").toString() : "";
    urlExport.setParameter("suri", suri);

    ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
    if (paramRequest.getUser() != null && paramRequest.getUser().isSigned()) {
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
            String data = paramRequest.getLocaleString("noImage");
            Iterator<GraphicalElement> iterator = null;
            GraphicalElement ge = null;
            org.semanticwb.process.model.Process process = null;
            SubProcess subProcess = null;
            Containerable con = null;
            String path = "";
            if (pe != null) {
                if (pe instanceof org.semanticwb.process.model.Process) {
                    process = (org.semanticwb.process.model.Process) pe;
                    if (process.getData() != null) {
                        data = process.getData();
                    }
                    iterator = process.listContaineds();
                }
                if (pe instanceof SubProcess) {
                    subProcess = (SubProcess) pe;
                    if (subProcess.getData() != null) {
                        data = subProcess.getData();
                    }
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
<div class="swbp-content-wrapper">
    <div class="row swbp-header hidden-xs">
        <a href="#">
            <div class="swbp-brand"></div>
        </a>
    </div>
    <nav class="swbp-toolbar" role="navigation">
        <div style="text-align: center;">
            <ul class="swbp-nav">
                <li><h2><i class="icon-gears" style="width: auto;"></i> <%=pe.getTitle()%></h2></li>
            </ul>
        </div>
    </nav>
    <div class="swbp-user-menu">
        <div class="row">
            <div class="col-lg-11 col-md-10 col-sm-10">
                <ul class="breadcrumb">
                    <%
                        String[] urls = path.split("\\|");
                        int cont = 0;
                        if (urls.length > 1) {
                            for (int i = 0; i < urls.length; i++) {
                                ProcessElement peAux = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urls[i]);
                                String title = peAux.getTitle();
                                cont++;
                    %>
                    <%if (urls.length > cont) {%>
                    <li>
                        <a href="#" onclick="window.location = '<%=urlDocumentation.setParameter("suri", peAux.getURI())%>';" title="<%=title%>"><%out.println(title);%></a>
                        <span class="divider"></span>
                    </li>
                    <%} else {%>
                    <li class="active"><%=title%></li>
                        <%}
                            }
                        } else {%>
                    <li class="active">
                        <%out.println(pe.getTitle());%></li><%
                            }
                        %>
                </ul>
            </div>
            <%if (pe instanceof org.semanticwb.process.model.Process) {%>
            <div class="col-lg-1 col-md-2 col-sm-2">
                <a href="javascript:print()" class="btn btn-default btn-sm icon-print" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("print")%>"></a>
                <a href="<%=urlExport.setParameter("format", "html")%>" class="btn btn-default btn-sm icon-file-text-alt" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("export")%>"></a>
            </div>
            <%}%>
        </div>
    </div>
    <!---------------BEGIN MENÚ--------------->
    <div class="col-lg-2 col-md-2 col-sm-4 hidden-xs">
        <div class="swbp-left-menu swbp-left-menu-doc">
            <ul class="nav nav-pills nav-stacked">
                <%if (lane.size() > 0) {//Lane%>
                <li class="active"><a style="width: 100%;" href="#lane" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=laneT + " " + lane.size()%>" class=""><%=laneT%> <span class="badge pull-right"><%=lane.size()%></span></a></li>
                        <%iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                        %>
                <li><a style="cursor: pointer;" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=ge.getTitle()%>" <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setMode("viewDocumentation").setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%>><%=ge.getTitle()%></a></li>
                    <%}
                        }
                        if (activity.size() > 0) {//Activity%>
                <li class="active"><a style="width: 100%;" href="#activity" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=activityT + " " + activity.size()%>"><%=activityT%> <span class="badge pull-right"><%=activity.size()%></span></a></li>
                        <%iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                        %>
                <li><a style="cursor: pointer;" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=ge.getTitle()%>" <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setMode("viewDocumentation").setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%>><%=ge.getTitle()%></a></li>
                    <%}
                        }
                        if (gateway.size() > 0) {//Gateway
                            iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());%>
                <li class="active"><a style="width: 100%;" href="#gateway" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=gatewayT + " " + gateway.size()%>"><%=gatewayT%> <span class="badge pull-right"><%=gateway.size()%></span></a></li>
                        <%while (iterator.hasNext()) {
                                ge = iterator.next();%>
                <li><a style="cursor: pointer;" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=ge.getTitle()%>" href="#<%=ge.getURI()%>" title=""><%=ge.getTitle()%></a></li>
                    <%
                        /*//Connections
                         Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                         while (itConObj.hasNext()) {
                         ConnectionObject connectionObj = itConObj.next();
                         if (connectionObj instanceof SequenceFlow) {//SequenceFlow%>
                <!--li><a class="icon-arrow-right" data-placement="bottom" data-toggle="tooltip" data-original-title="<%//connectionObj.getTitle()%>" href="#<%//connectionObj.getURI()%>"><%//connectionObj.getTitle()%></a></li-->
                <%}
                             }*/
                        }
                    }
                    if (event.size() > 0) {//Event%>
                <li class="active"><a style="width: 100%;" href="#event" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=eventT + " " + event.size()%>"><%=eventT%> <span class="badge pull-right"><%=event.size()%></span></a></li>
                        <%iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                        %>
                <li><a style="cursor: pointer;" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=ge.getTitle()%>" <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setMode("viewDocumentation").setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%>><%=ge.getTitle()%></a></li>
                    <%}
                        }
                        if (dataob.size() > 0) {//Event%>
                <li class="active"><a style="width: 100%;" href="#dataob" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=dataOBT + " " + dataob.size()%>"><%=dataOBT%> <span class="badge pull-right"><%=dataob.size()%></span></a></li>
                        <%iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                            while (iterator.hasNext()) {
                                ge = iterator.next();
                        %>
                <li><a style="cursor: pointer;" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=ge.getTitle()%>" <%if (ge instanceof SubProcess) {%> onclick="window.location = '<%=urlDocumentation.setMode("viewDocumentation").setParameter("suri", ge.getURI())%>';"<%} else {%> href="#<%=ge.getURI()%>" <%}%>><%=ge.getTitle()%></a></li>
                    <%}
                        }
                    %>
            </ul>
        </div>
    </div>
    <!---------------END MENÚ--------------->
    <!---------------BEGIN CONTENT--------------->
    <div class="col-lg-10 col-md-10 col-sm-8 col-xs" role="main">
        <div class="contenido">
            <div id="ruta" style="width: 100%; text-align: center; vertical-align: middle;">
                <%
                    //out.print(data);
                %>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-title"><strong><%=paramRequest.getLocaleString("docFromPro") + " " + pe.getTitle()%></strong></div>
                </div>
                <div class="panel-body">
                    <%out.print(pe.getDocumentation().getText());%>
                </div>
            </div>
            <%if (lane.size() > 0) {//Lane
%><div class="panel panel-default"><%
                iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                out.print("<div id=\"lane\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + laneT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>");
                %><div class="panel-body"><%
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                        out.println("<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>");
                        out.println(ge.getDocumentation().getText());
                    }
                    %></div></div><%
                        }
                        if (activity.size() > 0) {//Activity
%><div class="panel panel-default"><%
                        iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                        out.print("<div id=\"activity\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + activityT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>");
                %><div class="panel-body"><%
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                        out.println("<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>");
                        out.println(ge.getDocumentation().getText());
                    }
                    %></div></div><%
                        }
                        if (gateway.size() > 0) {//Gateway
%><div class="panel panel-default"><%
                        iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                        out.print("<div id=\"gateway\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + gatewayT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>");
                %><div class="panel-body"><%
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                        out.println("<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>");
                        out.println(ge.getDocumentation().getText());
                        /* //Connections
                         Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                         while (itConObj.hasNext()) {
                         ConnectionObject connectionObj = itConObj.next();
                         if (connectionObj instanceof SequenceFlow) {
                         out.println("<i class=\"icon-arrow-right\"></i><h4 id=\"" + connectionObj.getURI() + "\" title=\"" + connectionObj.getTitle() + "\">" + connectionObj.getTitle() + "</h4>");
                         out.println(connectionObj.getDocumentation().getText());
                         }
                         }*/
                    }
                    %></div></div><%
                        }
                        if (event.size() > 0) {//Event
%><div class="panel panel-default"><%
                        iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                        out.print("<div id=\"event\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + eventT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>");
                %><div class="panel-body"><%
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                        out.println("<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>");
                        out.println(ge.getDocumentation().getText());
                    }
                    %></div></div><%
                        }
                        if (dataob.size() > 0) {//DataObject
%><div class="panel panel-default"><%
                        iterator = SWBComparator.sortByDisplayName(dataob.iterator(), paramRequest.getUser().getLanguage());
                        out.print("<div id=\"dataob\" class=\"panel-heading\"><div class=\"panel-title\"><strong>" + dataOBT + "</strong><a href=\"#ruta\" style=\"cursor: pointer; text-decoration:none;\" class=\"pull-right icon-level-up\"></a></div></div>");
                %><div class="panel-body"><%
                    while (iterator.hasNext()) {
                        ge = iterator.next();
                        out.println("<h4 id=\"" + ge.getURI() + "\" title=\"" + ge.getTitle() + "\">" + ge.getTitle() + "</h4>");
                        out.println(ge.getDocumentation().getText());
                    }
                    %></div></div><%
                        }
                    %>
        </div>
    </div>
</div>
<!---------------END CONTENT--------------->
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
        toast.innerHTML = "<h4>" + msg + "</h4>";
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
        }
    }%>