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
<script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/modeler/toolkit.js"></script>
<script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/modeler/modeler.js"></script>
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
                <svg id="modeler" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="100" height="100" class="modeler">
        <style type="text/css"><![CDATA[
            /*.resizeBox {
                stroke:#008000;
                fill:url(#linearGradientStartEvent);
                stroke-width:1.5;  
            }*/
            .task {
                stroke:#79adc8;
                fill:url(#linearGradientTask);
                stroke-width:2;
                cursor:pointer;
            }
            .task_o {
                stroke:#2cff20;
                fill:url(#linearGradientTask);
                stroke-width:2;
                cursor:pointer;
            }
            .callactivity {
                stroke:#79adc8;
                fill:url(#linearGradientTask);
                stroke-width:4;
                cursor:pointer;
            }

            .callactivity_o {
                stroke:#2cff20;
                fill:url(#linearGradientTask);
                stroke-width:4;
                cursor:pointer;
            }
            .eventsubTask {
                stroke:#79adc8;
                fill:url(#linearGradientTask);
                stroke-width:2;
                cursor:pointer;
                stroke-dasharray:6,4;
            }

            .eventsubTask_o {
                stroke:#2cff20;
                fill:url(#linearGradientTask);
                stroke-width:2;
                cursor:pointer;
                stroke-dasharray:6,4;
            }
            .startEvent
            {
                stroke:#008000;
                fill:url(#linearGradientStartEvent);
                stroke-width:1.5;
                cursor:pointer;
            }

            .startEvent_o
            {
                stroke:#2cff20;
                fill:url(#linearGradientStartEvent);
                stroke-width:1.5;
                cursor: pointer;
            }
 
            .intermediateInterruptingEvent
            {
                stroke:#2c5aa0;
                fill:url(#linearGradientIntermediateEvent);
                fill-opacity:1;
                stroke-width:1.5;
                stroke-dasharray: 3,3;
                /*stroke-dashoffset: 5;*/
            }
            
            .intermediateInterruptingEvent_o
            {
                stroke:#2cff20;
                fill:url(#linearGradientIntermediateEvent);
                fill-opacity:1;
                stroke-width:1.5;
                stroke-dasharray: 3,3;
                /*stroke-dashoffset: 5;*/
            }
            
            .intermediateEvent
            {
                stroke:#2c5aa0;
                fill:url(#linearGradientIntermediateEvent);
                fill-opacity:1;
                stroke-width:1.5;
                cursor:pointer;
            }

            .intermediateEvent_o
            {
                stroke:#2cff20;
                fill:url(#linearGradientIntermediateEvent);
                fill-opacity:1;
                stroke-width:1.5;
            cursor:pointer;
            }
            .endEvent
            {
                stroke:#550000;
                fill:url(#linearGradientEndEvent);
                stroke-width:2.5;
                cursor:pointer;
            }

            .endEvent_o
            {
                stroke:#2cff20;
                fill:url(#linearGradientEndEvent);
                stroke-width:2.5;
                cursor:pointer;
            }    

            .gateway
            {
                stroke:#d4aa00;
                fill:url(#linearGradientGateway);
                stroke-width:2;
                cursor:pointer;
            }

            .gateway_o
            {
                stroke:#2cff20;
                fill:url(#linearGradientGateway);
                stroke-width:2;
                cursor:pointer;
            }
            
            .sequenceFlowSubLine {
                fill:none;
                stroke:#ffffff;
                stroke-opacity:0.1;
                stroke-width:8;
                cursor: pointer;
            }

            .sequenceFlowSubLine_o {
                fill:none;
                stroke:#2cff20;
                stroke-width:8;
                cursor:pointer;
            }
            
            .swimlane
            {
                fill: #E8E8FF;
                stroke-width:2;
                stroke: #ADADAE;
                fill-opacity:1;
                cursor:pointer;
            }

            .swimlane_o
            {
                stroke:#2cff20;
                fill: #E8E8FF;
                stroke-width:2;
                fill-opacity:1;
                cursor: pointer;
            }
            
            .sequenceFlowLine {
                fill: none;
                stroke-width: 2;
                stroke: #000000;
                cursor:pointer;
            }
            
            .sequenceFlowLine_o {
                fill: none;
                stroke-width: 2;
                stroke: #2cff20;
                cursor:pointer;
            }

            .intermediateEvent1
            {
                stroke:#2c5aa0;
                fill:none;
                stroke-width:1;
            }
            
            .itemAware {
                fill:url(#linearGradientDataObject);
                stroke:#666666;
                stroke-width:2;
                cursor:pointer;
            }

            .itemAware_o {
                fill:url(#linearGradientDataObject);
                stroke:#2cff20;
                stroke-width:2;
                cursor:pointer;
            }
            
            .transactionSquare {
                stroke:#2c5aa0;
                cursor:pointer;
                fill:none;
                stroke-width:1.5;
            }

            .group {
                stroke:#2c5aa0;
                cursor:pointer;
                fill:none;
                stroke-width:1.5;
            }

            .annotationArtifact {
                fill:none;
                stroke:#000000;
                stroke-width:1.5px;
                cursor:pointer;
            }

            .annotationArtifactRect {
                fill:none;
                stroke:none;
                fill:#E6E6E6;
                fill-opacity:0.2;
                cursor:pointer;
            }

            .annotationArtifactRect_o {
                fill:none;
                fill:#E6E6E6;
                stroke:#2cff20;
                fill-opacity:0.2;
                cursor:pointer;
            }

            .group_o {
                stroke:#2cff20;
                cursor:pointer;
                fill:none;
                stroke-width:1.5;
            }

            .taskMarker
            {
                stroke:#2c5aa0;
                cursor:pointer;
            }
            
            .pathMarker{
                fill:none;
                stroke-width:1;
            }
            
            .startMarker{
                stroke:#008000;
                fill:none;
                stroke-width:2.5;                        
            }
            
            .startMarker{
                stroke:#008000;
                fill:none;
                stroke-width:2.5;                        
            }
            
            .startFilledMarker{
                fill:#008000;
                stroke:none;
                stroke-width:1;
            }
            
            .intermediateMarker{
                stroke:#2c5aa0;
                fill:#ffffff;
                fill-opacity:0.1;
                stroke-width:2.5;
                cursor:pointer;
            }
            
            .intermediateFilledMarker {
                fill:#2c5aa0;
                stroke:#2c5aa0;
                stroke-width:1;
                cursor:pointer;
            }
            
            .endFilledMarked
            {
                fill:#550000;   
                stroke:#550000;
                stroke-width:1;
                cursor:pointer;
            }
            
            .navPath {
                fill:#e7e7e7;
                stroke:gray;
                stroke-width:1;
            }

            .navPathHidden {
                fill:#e7e7e7;
                stroke:gray;
                stroke-width:1;
                display:none;
            }
        ]]></style>
        <defs id="globalDef">
            <!--Definición de gradientes para las figuras-->
            <linearGradient id="linearGradientStartEvent" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="10%" style="stop-color:#ffffff;stop-opacity:1" />
                <stop offset="90%" style="stop-color:#ccffaa;stop-opacity:1" />
            </linearGradient>
            <linearGradient id="linearGradientEndEvent" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="10%" style="stop-color:#ffffff;stop-opacity:1" />
                <stop offset="90%" style="stop-color:#e7c1c1;stop-opacity:1" />
            </linearGradient>
            <linearGradient id="linearGradientTask" x1="100%" y1="0%" x2="100%" y2="100%">
                <stop offset="10%" style="stop-color:#ffffff;stop-opacity:1" />
                <stop offset="100%" style="stop-color:#c1d3e1;stop-opacity:1" />
            </linearGradient>
            <linearGradient id="linearGradientIntermediateEvent" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="10%" style="stop-color:#ffffff;stop-opacity:1" />
                <stop offset="100%" style="stop-color:#87aade;stop-opacity:1" />
            </linearGradient>
            <linearGradient id="linearGradientGateway" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="10%" style="stop-color:#FFFDE2;stop-opacity:1" />
                <stop offset="100%" style="stop-color:#FFFAA6;stop-opacity:1" />
            </linearGradient>
            <linearGradient id="linearGradientDataObject" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="10%" style="stop-color:#ffffff;stop-opacity:1" />
                <stop offset="100%" style="stop-color:#afbac7;stop-opacity:1" />
            </linearGradient>
            <circle id="resizeBox" cx="0" cy="0" r="5" class="resizeBox"/>
            <!--definición de marcadores para las figuras-->
            <path id="errorMarker" d="m 0.5,1051.8622 17.0774,-39.6847 15.0444,21.9792 19.5171,-27.474 L 34.8582,1048.199 19.8137,1029.6795 0.5,1051.8622 z" transform="scale(0.35,0.35) translate(-26, -1030)" />
            <g id="ruleMarker">
                <path d="m 0,0 0,43.9063 46.75,0 0,-43.9063 z m 4.25,9.875 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z" transform="scale(0.32,0.32) translate(-23.5,-23.5)"/>
            </g>
            <g id="taskRuleMarker" class="intermediateFilledMarker">
                <path d="m 0,0 0,43.9063 46.75,0 0,-43.9063 z m 4.25,9.875 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z" transform="scale(0.32,0.32) translate(-23.5,-23.5)"/>
            </g>
            <path id="multipleMarker" d="m 0,0 -33.73973,0.37343 -10.78131,-31.973 27.07653,-20.13383 27.51552,19.52961 z" transform="scale(0.35,0.35) translate(17,23)" />
            <path id="parallelMarker" d="m 0,0 0,19.4788 -19.45519,0 0,10.5627 19.45519,0 0,19.4787 10.58626,0 0,-19.4787 19.47871,0 0,-10.5627 -19.47871,0 0,-19.4788 z" transform="scale(0.35,0.35) translate(-5, -25)"/>
            <path id="linkMarker" d="m 0,0 -20.432,-24.2007 0,16.9761 -25.0594,0 0,14.1289 25.0594,0 0,17.332 z" transform="scale(0.35,0.35) translate(23,0)"/>
            <path id="complexMarker" d="m 0,0 0,15.875 -11.25,-11.2187 -2.34375,2.3125 11.25,11.25 -15.90625,0 0,3.3125 15.90625,0 -11.25,11.2187 2.34375,2.3438 11.25,-11.2188 0,15.875 3.28125,0 0,-15.9062 11.25,11.25 2.3125,-2.3438 -11.21875,-11.2187 15.875,0 0,-3.3125 -15.875,0 11.21875,-11.2188 -2.3125,-2.3437 -11.25,11.25 0,-15.9063 z" transform="translate(-1, -20)"/>
            <path id="signalMarker" d="m 0,0 -23.59924,0 -23.59925,0 11.79962,-20.43755 11.79963,-20.43755 11.79962,20.43755 z" transform="scale(0.35,0.35) translate(24,14)"/>
            <path id="scalationMarker" transform="scale(0.35,0.35) translate(0,-26)" d="m 0,0 -21.34041,47.167 21.34041,-17.3811 21.340402,17.3811 -21.340402,-47.167 z" />
            <path id="cancelMarker" d="m 0,0 -18.3627,18.3627 -18.3848004,-18.3848 -3.3366,3.3367 18.3847004,18.3847 -18.3847004,18.3848 3.3366,3.3367 18.3848004,-18.3848 18.3627,18.3627 3.3366,-3.3367 -18.3626,-18.3627 18.3626,-18.3626 z" transform="scale(0.35,0.35) translate(18, -22)"/>
            <g id="manualMarker" class="taskMarker" transform="scale(0.65)">
                <path d="m 0,0 c -0.27155,0 -0.5225,0.072 -0.75179,0.1792 -0.007,0 -0.0189,0 -0.0259,0 -1.25518,0.3798 -2.16186,4.1013 -2.9035,6.1425 -0.22877,0.5536 -0.36294,1.1549 -0.36294,1.7916 l 0,4.7861 c 0,2.6228 2.13932,4.7348 4.79597,4.7348 l 5.0552,0 5.69034,0 7.72539,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -3.11089,0 c 0.0118,-0.043 0.0283,-0.085 0.0389,-0.128 l 4.16081,0 c 0.97664,0 1.76285,-0.7762 1.76285,-1.7404 0,-0.9642 -2.73949,-1.7404 -1.76285,-1.7404 l -4.01823,0 0,-0.064 5.23667,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -5.39221,0 c -0.0116,-0.046 -0.0132,-0.095 -0.0259,-0.1408 l 7.56984,0 c 0.97663,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9642 -0.78621,-1.7404 -1.76284,-1.7404 l -12.18433,0 -0.92031,0 c 0.69854,-0.2311 1.20547,-0.8686 1.20547,-1.638 0,-0.9642 -0.7862,-1.7404 -1.76284,-1.7404 l -10.01967,0 z" class="pathMarker"/>
            </g>
            <g id="messageThrowMarker" transform="scale(1.3,1.3) translate(-7.5,-4.5)" >
                <path d="m 0,0 0,9.175062 14.84706,0 0,-9.175062 -0.0245,0 -7.40716,4.648917 -7.41535,-4.648917 z" style="stroke:#ffffff;stroke-opacity:0.4"/>
                <path d="m 0.6,0 6.88839,4.318911 6.88838,-4.318911" style="stroke:#ffffff;stroke-opacity:0.4"/>
            </g>
            <g id="taskMessageThrowMarker" transform="scale(1.3,1.3) translate(-7.5,-4.5)" class="intermediateFilledMarker">
                <path d="m 0,0 0,9.175062 14.84706,0 0,-9.175062 -0.0245,0 -7.40716,4.648917 -7.41535,-4.648917 z" style="stroke:#ffffff;stroke-opacity:0.4"/>
                <path d="m 0.6,0 6.88839,4.318911 6.88838,-4.318911" style="stroke:#ffffff;stroke-opacity:0.4"/>
            </g>
            <g id="messageCatchMarker" transform="scale(0.35,0.35) translate(-27,-17)" >
                <rect width="52.704407" height="32.573116" x="0" y="0"/>
                <path d="m 0,0 26.30906,16.49536 26.30905,-16.49536"/>
            </g>
            <g id="taskMessageCatchMarker" transform="scale(0.35,0.35) translate(-27,-17)" class="intermediateMarker">
                <rect width="52.704407" height="32.573116" x="0" y="0"/>
                <path d="m 0,0 26.30906,16.49536 26.30905,-16.49536"/>
            </g>
            <g id="compensaMarker" transform="scale(0.35,0.35) translate(-2,-13)">
                <path d="m 0,0 -28.36636,0 14.18318,-24.56599 z" transform="matrix(0,-1,1,0,0,0)"/>
                <path d="m 0,0 -28.36636,0 14.18318,-24.56599 z" transform="matrix(0,-1,1,0,23,0)"/>
            </g>
            <filter id="dropshadow" height="130%">
                <feGaussianBlur in="SourceAlpha" stdDeviation="3"/> 
                <feOffset dx="0" dy="0" result="offsetblur"/>
                <feComponentTransfer>
                    <feFuncA type="linear" slope="0.5"/>
                </feComponentTransfer>
                <feMerge> 
                    <feMergeNode/>
                    <feMergeNode in="SourceGraphic"/> 
                </feMerge>
            </filter>
            <g id="timerMarker" transform="scale(0.35,0.35) translate(-396,-219)" >
                <path d="m 232.18784,217.1541 a 40.089989,40.089989 0 1 1 -80.17997,0 40.089989,40.089989 0 1 1 80.17997,0 z" transform="matrix(0.6346099,0,0,0.6346099,274.19065,81.345956)"/>
                <path d="m 421.49964,219.1541 -8.76968,0"/>
                <path d="m 396.40906,193.78618 -0.0964,8.76915"/>
                <path d="m 370.76392,218.75252 8.76858,0.13882"/>
                <path d="m 396.04976,244.52342 0.0278,-8.76963"/>
                <path d="m 408.75323,219.1541 -12.86938,0"/>
                <path d="m 401.39526,204.36536 -4.8964,14.70678"/>
                <path d="m 414.26521,201.41346 -6.26889,6.13256"/>
                <path d="m 413.87083,237.28912 -6.13256,-6.26889"/>
                <path d="m 377.99517,236.89474 6.26889,-6.13256"/>
                <path d="m 378.38955,201.01908 6.13256,6.26889"/>
            </g>
            <g id="userMarker" class="taskMarker" transform="scale(0.8) translate(0,-1042)">
                <path d="m 0,1039 c -3.3158023,0 -6,2.8058 -6,6.25 0,2.0045 0.9288788,3.7654 2.34375,4.9062 -3.3002786,1.7745 -5.6842907,5.8793 -6,10.75 l 19.3125,0 c -0.3158218,-4.8705 -2.7074728,-8.9755 -6,-10.75 C 5.0711212,1049.0154 6,1047.2545 6,1045.25 6,1041.8058 3.3158023,1039 0,1039 z" class="pathMarker" />
                <path d="m -377.4453,473.79996 a 9.3826418,9.3826418 0 0 1 -12.52292,0.66553" transform="matrix(0.6398828,0,0,0.66465394,245.76439,734.73647)" class="pathMarker" />
                <path d="m -5.1190626,1060.732 0,-3.8413" class="pathMarker"/>
                <path d="m 5.1190626,1060.732 0,-3.8413" class="pathMarker" />
                <path d="m 0.06204402,1039.6046 c -3.10856302,0 -5.66539592,2.4352 -5.68305502,5.5312 0.8963285,-1.0836 1.7094089,-1.6888 3.0835319,-1.6888 2.07094865,0 4.4437605,1.0758 6.2188589,0.7688 0.570252,-0.099 1.2624582,-0.3804 1.939646,-0.7272 -0.6073258,-2.271 -3.0493799,-3.884 -5.55898178,-3.884 z" style="fill:#2c5aa0;stroke-width:1;" />
            </g>
            <g id="serviceMarker" class="taskMarker" transform="scale(0.8)">
                <path d="m 0,0 1.478093,-2.5912 c -0.762317,-0.4662 -1.601697,-0.8229 -2.495483,-1.0366 l -0.767843,2.8793 -2.495483,0 -0.787044,-2.8793 c -0.895236,0.2142 -1.733065,0.5691 -2.495483,1.0366 l 1.487693,2.5721 -1.766033,1.7659 -2.581869,-1.478 c -0.467511,0.7623 -0.822527,1.6003 -1.036583,2.4952 l 2.86981,0.7678 0,2.5048 -2.860209,0.7775 c 0.21606,0.8875 0.562993,1.7267 1.026982,2.4856 l 2.562669,-1.478" class="pathMarker" />
                <path d="m 0,0 c -0.989621,0.2366 -1.915788,0.629 -2.758588,1.1457 l 1.644541,2.8433 -1.952235,1.9521 -2.85408,-1.6338 c -0.516803,0.8427 -0.909247,1.769 -1.145882,2.7583 l 3.172388,0.8488 0,2.769 -3.161774,0.8593 c 0.238849,0.9811 0.622356,1.9088 1.135268,2.7477 l 2.832859,-1.6337 1.952235,1.952 -1.633935,2.8539 c 0.84654,0.5219 1.774518,0.918 2.769203,1.1564 l 0.848804,-3.1828 2.758588,0 0.870025,3.1828 c 0.988288,-0.238 1.914849,-0.6289 2.758588,-1.1459 l -1.644542,-2.8431 1.952236,-1.9521 2.864694,1.6337 c 0.515612,-0.8389 0.908032,-1.7642 1.145874,-2.7477 l -3.182994,-0.8593 0,-2.769 3.182994,-0.87 c -0.237717,-0.9884 -0.628065,-1.9158 -1.145874,-2.7583 l -2.854088,1.655 -1.952235,-1.9521 1.633935,-2.8645 c -0.842691,-0.5152 -1.77056,-0.9096 -2.758588,-1.1457 l -0.848804,3.1828 -2.758588,0 z m 2.238708,5.697 c 1.994007,0 3.607392,1.6133 3.607392,3.6072 0,1.9938 -1.613385,3.6176 -3.607392,3.6176 -1.994007,0 -3.607392,-1.6238 -3.607392,-3.6176 0,-1.9939 1.613385,-3.6072 3.607392,-3.6072 z" class="pathMarker" />
            </g>
            <g id="scriptMarker" class="taskMarker"  transform="scale(0.7)">
                <path d="m 0,0 c 3.5628793,2.98 3.5212524,7.9199 0,10.8403 l 0,0.1186 13.5952063,-10e-4 c 4.246065,-3.0502 4.698752,-8.6843 0.03211,-12.4597 -1.719778,-1.441 -2.097829,-3.2691 -2.297848,-5.5453 0,-1.9186 0.795633,-3.6321 2.05209,-4.7945 l -9.1791045,0 c -7.65536286,-0.3702 -8.7288909,8.3174 -4.2024826,11.8414 z" class="pathMarker" />
                <path d="m 0,-8 9.5315769,0" class="pathMarker" />
                <path d="m -0.5,-4.1568 9.5315769,0" class="pathMarker" />
                <path d="m 2.5,0 9.5315769,0" class="pathMarker" />
                <path d="m 4.5,4 9.5315768,0" class="pathMarker" />
                <path d="m 4,8 9.5315768,0" class="pathMarker" />
            </g>
            <g id="subProcessMarker" class="taskMarker">
                <rect x="-7" y="-7" width="14" height="14" style="fill:#ffffff;fill-opacity:0.1;"/>
            <path d="M-5 0 L5 0 M0 -5 L0 5"/>
            </g>
            <marker id="sequenceArrow" viewBox="0 0 12 12" refX="0" refY="5" markerUnits="userSpaceOnUse" markerWidth="10" markerHeight="10" orient="auto" fill="black" stroke="none" stroke-dasharray="0">
                <path d="M 0 0 L 10 5 L 0 10"/>
            </marker>
            <marker id="messageArrow" viewBox="0 0 12 12" refX="7" refY="5" markerUnits="userSpaceOnUse" markerWidth="10" markerHeight="10" orient="auto" fill="none" stroke="black" stroke-width="1.5" stroke-dasharray="0">
                <path d="M 0 0 L 10 5 L 0 10"/>
            </marker>
            <marker id="conditionTail" viewBox="-6 -6 12 12" refX="3" refY="0" markerUnits="userSpaceOnUse" markerWidth="12" markerHeight="12" orient="auto" fill="none" stroke="black" stroke-width="1.5" stroke-dasharray="0">
                <rect x="-3" y="-3" width="6" height="6" transform="rotate(-45)"/>
            </marker>
            <marker id="defaultTail" viewBox="0 0 12 12" refX="-3" refY="5" markerUnits="userSpaceOnUse" markerWidth="10" markerHeight="10" orient="auto" fill="none" stroke="black" stroke-width="2" stroke-dasharray="0">
                <path d="M 5 0 L 0 10"/>
            </marker>
            <marker id="messageTail" viewBox="-5 -5 12 12" refX="3" refY="0" markerUnits="userSpaceOnUse" markerWidth="10" markerHeight="10" orient="auto" fill="none" stroke="black" stroke-width="1.5" stroke-dasharray="0">
                <circle r="3" />
            </marker>
            <!--Definición de eventos iniciales-->
            <circle id="startEvent" r="15" bclass="startEvent" oclass="startEvent_o"/>
            <g id="messageStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#messageCatchMarker" x="0" y="0" class="startMarker"/>
            </g>
            <g id="timerStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#timerMarker" x="0" y="0" class="startMarker"/>
            </g>
            <g id="ruleStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#ruleMarker" x="0" y="0" class="startFilledMarker" transform="scale(1.1)"/>
            </g>
            <g id="signalStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#signalMarker" x="0" y="0" class="startMarker"/>
            </g>
            <g id="multipleStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#multipleMarker" x="0" y="0" class="startMarker"/>
            </g>
            <g id="parallelStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#parallelMarker" x="0" y="0" class="startMarker"/>
            </g>
            <g id="scalationStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#scalationMarker" x="0" y="0" class="startMarker"/>
            </g>
            <g id="errorStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#errorMarker" x="0" y="0" class="startMarker"/>
            </g>
            <g id="compensationStartEvent" bclass="startEvent" oclass="startEvent_o">
                <use xlink:href="#startEvent" x="0" y="0"/>
                <use xlink:href="#compensaMarker" x="0" y="0" class="startMarker"/>
            </g>
            <!--Definición de eventos intermedios-->
            <g id="intermediateEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <circle r="15"/>
                <circle r="12" class="intermediateEvent1"/>
            </g>
            <g id="messageIntermediateCatchEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#messageCatchMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="messageIntermediateThrowEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#messageThrowMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="timerIntermediateEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#timerMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="errorIntermediateEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#errorMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="cancelIntermediateEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#cancelMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="compensationIntermediateCatchEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#compensaMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="compensationIntermediateThrowEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#compensaMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="ruleIntermediateEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#ruleMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="linkIntermediateCatchEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#linkMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="linkIntermediateThrowEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#linkMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="signalIntermediateCatchEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#signalMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="signalIntermediateThrowEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#signalMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="multipleIntermediateCatchEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#multipleMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="multipleIntermediateThrowEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#multipleMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="scalationIntermediateCatchEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#scalationMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <g id="scalationIntermediateThrowEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#scalationMarker" x="0" y="0" class="intermediateFilledMarker"/>
            </g>
            <g id="parallelIntermediateEvent" bclass="intermediateEvent" oclass="intermediateEvent_o">
                <use xlink:href="#intermediateEvent" x="0" y="0"/>
                <use xlink:href="#parallelMarker" x="0" y="0" class="intermediateMarker"/>
            </g>
            <!--Definición de eventos finales-->
            <circle id="endEvent" r="15" bclass="endEvent" oclass="endEvent_o"/>

            <g id="messageEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <use xlink:href="#messageThrowMarker" x="0" y="0" class="endFilledMarked"/>
            </g>
            <g id="signalEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <use xlink:href="#signalMarker" x="0" y="0" class="endFilledMarked"/>
            </g>
            <g id="scalationEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <use xlink:href="#scalationMarker" x="0" y="0" class="endFilledMarked"/>
            </g>
            <g id="errorEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <use xlink:href="#errorMarker" x="0" y="0" class="endFilledMarked"/>
            </g>
            <g id="multipleEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <use xlink:href="#multipleMarker" x="0" y="0" class="endFilledMarked"/>
            </g>
            <g id="cancelationEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <use xlink:href="#cancelMarker" x="0" y="0" class="endFilledMarked"/>
            </g>
            <g id="terminationEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <circle r="8" class="endFilledMarked"/>
            </g>
            <g id="compensationEndEvent" bclass="endEvent" oclass="endEvent_o">
                <use xlink:href="#endEvent" x="0" y="0"/>
                <use xlink:href="#compensaMarker" x="0" y="0" class="endFilledMarked"/>
            </g>
            <!--Definición de compuertas-->
            <rect id="gateway" x="-17.5" y="-17.5" width="35" height="35" bclass="gateway" oclass="gateway_o" transform="rotate(45,0,0)"/>
            <g id="exclusiveDataGateway" bclass="gateway" oclass="gateway_o" >
                <use xlink:href="#gateway" x="0" y="0"/>
                <use xlink:href="#cancelMarker" x="0" y="0" style="stroke:none;fill:#d4aa00" transform="scale(1.3,1.3)"/>
            </g>
            <g id="inclusiveDataGateway" bclass="gateway" oclass="gateway_o" >
                <use xlink:href="#gateway" x="0" y="0"/>
                <circle r="14" style="fill:none;stroke:#d4aa00;stroke-width:2;"/>
            </g>
            <g id="exclusiveStartGateway" bclass="gateway" oclass="gateway_o" >
                <use xlink:href="#gateway" x="0" y="0"/>
                <circle r="14" style="fill:none;stroke:#d4aa00;stroke-width:2;"/>
                <use xlink:href="#multipleMarker" x="0" y="0" style="fill:none;stroke:#d4aa00;stroke-width:4.5;" transform="scale(0.9,0.9)"/>
            </g>
            <g id="eventGateway" bclass="gateway" oclass="gateway_o" >
                <use xlink:href="#gateway" x="0" y="0"/>
                <circle r="14" style="fill:none;stroke:#d4aa00;stroke-width:2;"/>
                <circle r="10" style="fill:none;stroke:#d4aa00;stroke-width:2;"/>
                <use xlink:href="#multipleMarker" x="0" y="0" style="fill:none;stroke:#d4aa00;stroke-width:6;" transform="scale(0.75,0.75)"/>
            </g>
            <g id="parallelGateway" bclass="gateway" oclass="gateway_o" >
                <use xlink:href="#gateway" x="0" y="0"/>
                <use xlink:href="#parallelMarker" x="0" y="0" style="stroke:none;fill:#d4aa00" transform="scale(1.5,1.5)"/>
            </g>
            <g id="parallelStartGateway" bclass="gateway" oclass="gateway_o" >
                <use xlink:href="#gateway" x="0" y="0"/>
                <circle r="14" style="fill:none;stroke:#d4aa00;stroke-width:2;"/>
                <use xlink:href="#parallelMarker" x="0" y="0" style="stroke:#d4aa00;fill:none;stroke-width:3.5;"/>
            </g>
            <g id="complexGateway" bclass="gateway" oclass="gateway_o" >
                <use xlink:href="#gateway" x="0" y="0"/>
                <use xlink:href="#complexMarker" x="0" y="0" style="stroke:none;fill:#d4aa00" transform="scale(0.7,0.7)"/>
            </g>
            <!--Definición de objetos de datos-->
            <g id="data" bclass="itemaware" oclass="itemaware_o">
                <path transform="scale(0.7,0.7) translate(-3,-10)" d="m -25,-25 0,74.61135 58.22841,0 0,-55.2509 -19.88426,-19.36045 -38.34415,0 z"/>
                <path transform="scale(0.7,0.7) translate(-2,-10)" d="m 12.5,-24 0,19.5625 20.0601,0"/>
            </g>
            <g id="dataStore" bclass="itemaware" oclass="itemaware_o" transform="translate(-12,-10)">
                <path d="m 0,0 c -27.75868,0 -50.28125,5.6228 -50.28125,12.5625 0,0.1516 0.0412,0.2871 0.0625,0.4375 l -0.0625,0 0,61.5624 0,0.3125 0.0312,0 c 0.68314,6.7909 22.92187,12.25 50.25,12.25 27.3249498,0 49.53067,-5.4602 50.21875,-12.25 l 0.0312,0 0,-0.3125 0,-61.5624 -0.0312,0 c 0.0212,-0.1501 0.0312,-0.2862 0.0312,-0.4375 0,-6.9397 -22.4913202,-12.5625 -50.25,-12.5625 z" transform="scale(0.6,0.6) translate(22,-24)"/>
                <path d="m 0,0 c 0,6.9397 -22.5028602,12.5654 -50.26153,12.5654 -27.39179,0 -49.73975,-5.4833 -50.25272,-12.33" transform="scale(0.6,0.6) translate(72,-12)"/>
            </g>	
            <g id="dataInput" bclass="itemaware" oclass="itemaware_o">
                <use xlink:href="#data" x="0" y="0"/>
                <use xlink:href="#linkMarker" x="-12" y="-17" style="fill:none;stroke:#666666;stroke-width:2.5;" transform="scale(0.8,0.8)"/>
            </g>
            <g id="dataOutput" bclass="itemaware" oclass="itemaware_o">
                <use xlink:href="#data" x="0" y="0"/>
                <use xlink:href="#linkMarker" x="-12" y="-17" style="fill:#666666;stroke:none;stroke-width:2.5;" transform="scale(0.8,0.8)"/>
            </g>
            <!--Definición de tareas-->
            <g id="task" styled="stroke:#2c5aa0">
                <rect x="-50" y="-30" rx="10" ry="10" width="100" height="60" class="task"/>
            </g>
            <g id="userTask" styled="stroke:#2c5aa0">
                <use xlink:href="#task" x="0" y="0"/>
                <use xlink:href="#userMarker" transform="scale(0.8,0.8) translate(-45, -30)"/>
            </g>
            <g id="serviceTask" styled="stroke:#2c5aa0">
                <use xlink:href="#task" x="0" y="0"/>
                <use xlink:href="#serviceMarker" transform="scale(0.8,0.8) translate(-43, -27)"/>
            </g>
            <g id="scriptTask" styled="stroke:#2c5aa0">
                <use xlink:href="#task" x="0" y="0"/>
                <use xlink:href="#scriptMarker" transform="scale(0.7,0.7) translate(-60, -24)"/>
            </g>
            <g id="ruleTask" styled="stroke:#2c5aa0">
                <use xlink:href="#task" x="0" y="0"/>
                <use xlink:href="#ruleMarker" transform="translate(-35, -17)" style="fill:#2c5aa0;"/>
            </g>
            <g id="sendTask" styled="stroke:#2c5aa0">
                <use xlink:href="#task" x="0" y="0"/>
                <use xlink:href="#messageThrowMarker" transform="translate(-35, -19)" style="fill:#2c5aa0;"/>
            </g>
            <g id="receiveTask" styled="stroke:#2c5aa0">
                <use xlink:href="#task" x="0" y="0"/>
                <use xlink:href="#messageCatchMarker" transform="translate(-35, -19)" style="fill:none;stroke:#2c5aa0;stroke-width:3;"/>
            </g>
            <g id="manualTask" styled="stroke:#2c5aa0">
                <use xlink:href="#task" x="0" y="0"/>
                <use xlink:href="#manualMarker" transform="scale(0.7,0.7) translate(-60, -35)" style="fill:none;stroke:#2c5aa0;stroke-width:4;"/>
            </g>
            <!--Definición de swimlanes-->
            <g id="pool" bclass="swimlane" oclass="swimlane_o">
                <rect width="600" x="-300" y="-100" height="200" style="fill:#E8E8FF;stroke-width:2"/>
                <path d="m -280,-100 l 0,200" style="fill:none;stroke-width:2;"/>
            </g>
        </defs>
    </svg>
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
    
    Modeler.init('modeler','view', callbackHandler);
    
    function callbackHandler() {
        var strJSON = '{"nodes":[{"isInterrupting":false,"class":"Pool","parent":"","uri":"http://www.eworkplace.swb#swp_Pool:163","isForCompensation":false,"h":668,"w":1411,"title":"INFOTEC","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":368,"x":768},{"isInterrupting":true,"index":0,"class":"Lane","parent":"http://www.eworkplace.swb#swp_Pool:163","uri":"http://www.eworkplace.swb#swp_Lane:340","isForCompensation":false,"h":668,"w":1411,"title":"Analista de Recursos Humanos","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":368,"x":788},{"isInterrupting":false,"class":"DataStore","parent":"","uri":"http://www.eworkplace.swb#swp_DataStore:902","isForCompensation":false,"h":50,"w":60,"title":"Contador Contrato","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":825,"x":975},{"isInterrupting":false,"class":"DataStore","parent":"","uri":"http://www.eworkplace.swb#swp_DataStore:903","isForCompensation":false,"h":50,"w":60,"title":"Datos Contrato","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":975},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2261","isForCompensation":false,"h":55,"w":50,"title":"Documentos probatorios","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":868,"x":1368},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:596","isForCompensation":false,"h":60,"w":100,"title":"7 Traspasa variables","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":1293},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:1928","isForCompensation":false,"h":55,"w":50,"title":"Solicitud de promoción intra","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":468,"x":1593},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:1929","isForCompensation":false,"h":55,"w":50,"title":"Contratacion directa","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":768,"x":893},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:887","isForCompensation":false,"h":50,"w":50,"title":"c ¿Error en Directorio Activo?","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":1293},{"isInterrupting":false,"class":"SubProcess","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_SubProcess:350","isForCompensation":false,"h":60,"w":100,"title":"6 Manejo del Directorio Activo","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":1293},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:644","isForCompensation":false,"h":60,"w":100,"title":"6.1 Genera correo a MSI para el manejo de DA","container":"http://www.eworkplace.swb#swp_SubProcess:350","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":418},{"isInterrupting":true,"class":"EndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_EndEvent:635","isForCompensation":false,"h":30,"w":30,"title":"Fin normal","container":"http://www.eworkplace.swb#swp_SubProcess:350","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":893},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1795","isForCompensation":false,"h":60,"w":100,"title":"6.2  Muestra información","container":"http://www.eworkplace.swb#swp_SubProcess:350","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":593},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:1020","isForCompensation":false,"h":60,"w":100,"title":"6.3 Envía correo","container":"http://www.eworkplace.swb#swp_SubProcess:350","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":768},{"isInterrupting":true,"class":"StartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_StartEvent:522","isForCompensation":false,"h":30,"w":30,"title":"Inicio normal","container":"http://www.eworkplace.swb#swp_SubProcess:350","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":268},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2759","isForCompensation":false,"h":55,"w":50,"title":"Error DA","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":525,"x":1450},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1743","isForCompensation":false,"h":60,"w":100,"title":"1 Se recibe la información de la Solicitud de Promoción","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":593,"x":598},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1742","isForCompensation":false,"h":60,"w":100,"title":"1 Se recibe la información de la Solicitud de recurso","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":443},{"isInterrupting":true,"class":"MessageStartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_MessageStartEvent:37","isForCompensation":false,"h":30,"w":30,"title":"Solicitud de contratación directa","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":143},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1741","isForCompensation":false,"h":60,"w":100,"title":"1 Se recibe la información de la solicitud de Contratación Directa","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":418},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:915","isForCompensation":false,"h":50,"w":50,"title":"c Requiere actualizar datos ","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":968},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:914","isForCompensation":false,"h":50,"w":50,"title":"c Agrega contrato promoción ","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":818},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2389","isForCompensation":false,"h":55,"w":50,"title":"Seguimiento solicitud","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":1225},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2386","isForCompensation":false,"h":55,"w":50,"title":"Solicitud de recurso","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":825,"x":1225},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:642","isForCompensation":false,"h":60,"w":100,"title":"5.1 Alta en el Directorio Activo","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":818},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:641","isForCompensation":false,"h":60,"w":100,"title":"5.2 Actualización en el Directorio Activo","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":968},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:891","isForCompensation":false,"h":50,"w":50,"title":"c fin","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":393,"x":1293},{"isInterrupting":true,"class":"EndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_EndEvent:637","isForCompensation":false,"h":30,"w":30,"title":"Fin normal","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":1415},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2293","isForCompensation":false,"h":55,"w":50,"title":"Documentacion completa","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":868,"x":1593},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:730","isForCompensation":false,"h":50,"w":50,"title":"c agrega contrato SR y CD","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":568},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2179","isForCompensation":false,"h":55,"w":50,"title":"Jefe Inmediato","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":1518},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2173","isForCompensation":false,"h":55,"w":50,"title":"Área de adscripción","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":368,"x":1593},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:890","isForCompensation":false,"h":50,"w":50,"title":"c servicios","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":293,"x":1118},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2417","isForCompensation":false,"h":55,"w":50,"title":"DocValida","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":825,"x":1375},{"isInterrupting":true,"class":"MessageEndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_MessageEndEvent:92","isForCompensation":false,"h":30,"w":30,"title":"Genera solicitud de kit","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":1418},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2277","isForCompensation":false,"h":55,"w":50,"title":"Asunto correo","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":1375},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2278","isForCompensation":false,"h":55,"w":50,"title":"Contenido correo","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":825,"x":1450},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2279","isForCompensation":false,"h":55,"w":50,"title":"Destinatario correo","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":1525},{"isInterrupting":false,"class":"SubProcess","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_SubProcess:330","isForCompensation":false,"h":60,"w":100,"title":"2 Agrega contrato Promoción","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":593,"x":818},{"isInterrupting":true,"class":"EndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_EndEvent:603","isForCompensation":false,"h":30,"w":30,"title":"Fin normal","container":"http://www.eworkplace.swb#swp_SubProcess:330","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":218,"x":943},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1747","isForCompensation":false,"h":60,"w":100,"title":"2.1 Selecciona tipo de promocion y registra datos faltantes","container":"http://www.eworkplace.swb#swp_SubProcess:330","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":218,"x":393},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1746","isForCompensation":false,"h":60,"w":100,"title":"2.2 Agrega Contrato firmado","container":"http://www.eworkplace.swb#swp_SubProcess:330","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":218,"x":743},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:648","isForCompensation":false,"h":60,"w":100,"title":"2.1.1 Genera estructura de reporte","container":"http://www.eworkplace.swb#swp_SubProcess:330","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":218,"x":568},{"isInterrupting":true,"class":"StartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_StartEvent:486","isForCompensation":false,"h":30,"w":30,"title":"Inicio normal","container":"http://www.eworkplace.swb#swp_SubProcess:330","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":218,"x":218},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:743","isForCompensation":false,"h":50,"w":50,"title":"4 ¿Es nuevo ingreso?","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":818},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2188","isForCompensation":false,"h":55,"w":50,"title":"Nombre solicitante","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":425,"x":1450},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2189","isForCompensation":false,"h":55,"w":50,"title":"Archivo solicitante","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":325,"x":1450},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:561","isForCompensation":false,"h":60,"w":100,"title":"0.1 Carga de datos de contratación directa","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":268},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:560","isForCompensation":false,"h":60,"w":100,"title":"0.3 Carga de datos de solicitud de promoción","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":593,"x":418},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2823","isForCompensation":false,"h":55,"w":50,"title":"Adscripción solicitante","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":1075},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2473","isForCompensation":false,"h":55,"w":50,"title":"tipo solicitud","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":68,"x":1593},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2726","isForCompensation":false,"h":55,"w":50,"title":"Solicitante","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":168,"x":1518},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2727","isForCompensation":false,"h":55,"w":50,"title":"Recurso contratado","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":168,"x":1593},{"isInterrupting":true,"class":"MessageStartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_MessageStartEvent:83","isForCompensation":false,"h":30,"w":30,"title":"Solicitud de promoción","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":593,"x":143},{"isInterrupting":true,"class":"MessageStartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_MessageStartEvent:82","isForCompensation":false,"h":30,"w":30,"title":"Solicitud de recurso","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":143},{"isInterrupting":false,"class":"SubProcess","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_SubProcess:314","isForCompensation":false,"h":60,"w":100,"title":"2 Solicita documentación","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":568},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:522","isForCompensation":false,"h":60,"w":100,"title":"2.3.1 Inicializa correo Nomina","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":418,"x":343},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:521","isForCompensation":false,"h":60,"w":100,"title":"2.2.1 Inicia correo SerProf","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":243,"x":368},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:504","isForCompensation":false,"h":60,"w":100,"title":"2.6.1 Inicializa correo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":368,"x":1018},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:500","isForCompensation":false,"h":60,"w":100,"title":"2.5.1 Inicializa correo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":1018},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:930","isForCompensation":false,"h":60,"w":100,"title":"2.6.2 Envía correo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":568,"x":1018},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1688","isForCompensation":false,"h":60,"w":100,"title":"2.1 Genera mensaje de Doc solicitados Eventual","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":518},{"isInterrupting":true,"class":"StartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_StartEvent:462","isForCompensation":false,"h":30,"w":30,"title":"Inicio normal","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":193,"x":93},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1690","isForCompensation":false,"h":60,"w":100,"title":"2.3 Genera mensaje de Doc Nomina","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":418,"x":493},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1691","isForCompensation":false,"h":60,"w":100,"title":"2.2 Genera mensaje de Doc Servicios profesionales","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":243,"x":518},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1693","isForCompensation":false,"h":60,"w":100,"title":"2.4 Recibe documentos","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":243,"x":893},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1695","isForCompensation":false,"h":60,"w":100,"title":"2.5 Cita para cotejo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":1193},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:518","isForCompensation":false,"h":60,"w":100,"title":"2.1.1 Inicializa correo Eventual","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":368},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1699","isForCompensation":false,"h":60,"w":100,"title":"2.6 Solicita al candidato documentos faltantes y cita para cotejo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":468,"x":1018},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:817","isForCompensation":false,"h":50,"w":50,"title":"Exclusiva (datos)","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":243,"x":793},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:816","isForCompensation":false,"h":50,"w":50,"title":"06_TipoSol","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":193,"x":218},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:819","isForCompensation":false,"h":50,"w":50,"title":"2.4 La documentación esta completa","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":243,"x":1018},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:922","isForCompensation":false,"h":60,"w":100,"title":"2.3.2 Envía correo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":418,"x":643},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:924","isForCompensation":false,"h":60,"w":100,"title":"2.1.2 Envía correo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":643},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:923","isForCompensation":false,"h":60,"w":100,"title":"2.2.2 Envia correo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":243,"x":643},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:926","isForCompensation":false,"h":60,"w":100,"title":"2.5.2 Envía correo","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":243,"x":1193},{"isInterrupting":true,"class":"EndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_EndEvent:585","isForCompensation":false,"h":30,"w":30,"title":"Fin normal","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":668,"x":1018},{"isInterrupting":true,"class":"EndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_EndEvent:583","isForCompensation":false,"h":30,"w":30,"title":"Fin normal","container":"http://www.eworkplace.swb#swp_SubProcess:314","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":343,"x":1193},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2295","isForCompensation":false,"h":55,"w":50,"title":"Es nuevo ingreso","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":668,"x":1593},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2401","isForCompensation":false,"h":55,"w":50,"title":"Seguimiento promocion","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":1300},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2172","isForCompensation":false,"h":55,"w":50,"title":"Proyecto","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":1593},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2817","isForCompensation":false,"h":55,"w":50,"title":"Requiere actualizar datos","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":1450},{"isInterrupting":false,"class":"DataObject","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_DataObject:2711","isForCompensation":false,"h":55,"w":50,"title":"RFC","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":68,"x":1518},{"isInterrupting":false,"class":"SubProcess","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_SubProcess:300","isForCompensation":false,"h":60,"w":100,"title":"3 Coteja Documentos","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":393,"x":568},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1751","isForCompensation":false,"h":60,"w":100,"title":"3.1 Carga documentos","container":"http://www.eworkplace.swb#swp_SubProcess:300","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":368},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:815","isForCompensation":false,"h":60,"w":100,"title":"3.1.1 Carga documentos en repositorio","container":"http://www.eworkplace.swb#swp_SubProcess:300","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":518},{"isInterrupting":true,"class":"StartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_StartEvent:434","isForCompensation":false,"h":30,"w":30,"title":"Inicio normal","container":"http://www.eworkplace.swb#swp_SubProcess:300","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":218},{"isInterrupting":true,"class":"EndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_EndEvent:539","isForCompensation":false,"h":30,"w":30,"title":"Fin normal","container":"http://www.eworkplace.swb#swp_SubProcess:300","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":1068},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1524","isForCompensation":false,"h":60,"w":100,"title":"3.2 Coteja Documentos","container":"http://www.eworkplace.swb#swp_SubProcess:300","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":668},{"isInterrupting":false,"class":"ExclusiveGateway","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ExclusiveGateway:748","isForCompensation":false,"h":50,"w":50,"title":"3.2 ¿la documentación es valida?","container":"http://www.eworkplace.swb#swp_SubProcess:300","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":793},{"isInterrupting":true,"class":"ErrorEndEvent","parent":"","uri":"http://www.eworkplace.swb#swp_ErrorEndEvent:8","isForCompensation":false,"h":30,"w":30,"title":"Fin con error","container":"http://www.eworkplace.swb#swp_SubProcess:300","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":350,"x":1000},{"isInterrupting":false,"class":"SubProcess","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_SubProcess:303","isForCompensation":false,"h":60,"w":100,"title":"4 Agrega contrato","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":268,"x":693},{"isInterrupting":true,"class":"StartEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_StartEvent:436","isForCompensation":false,"h":30,"w":30,"title":"Inicio normal","container":"http://www.eworkplace.swb#swp_SubProcess:303","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":193},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:632","isForCompensation":false,"h":60,"w":100,"title":"4.1.2 Exporta datos a un archivo de excel","container":"http://www.eworkplace.swb#swp_SubProcess:303","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":618},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:286","isForCompensation":false,"h":60,"w":100,"title":"4.1.1 Carga datos","container":"http://www.eworkplace.swb#swp_SubProcess:303","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":318},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1531","isForCompensation":false,"h":60,"w":100,"title":"4.1 Selecciona Tipo de ingreso a Infotec y registra datos faltantes","container":"http://www.eworkplace.swb#swp_SubProcess:303","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":468},{"isInterrupting":true,"class":"EndEvent","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_EndEvent:541","isForCompensation":false,"h":30,"w":30,"title":"Fin normal","container":"http://www.eworkplace.swb#swp_SubProcess:303","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":918},{"isInterrupting":false,"class":"UserTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_UserTask:1530","isForCompensation":false,"h":60,"w":100,"title":"4.2 Agrega Contrato firmado","container":"http://www.eworkplace.swb#swp_SubProcess:303","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":143,"x":768},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2771","isForCompensation":false,"h":55,"w":50,"title":"Mensaje de Dir Activo","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":625,"x":1450},{"isInterrupting":false,"class":"ScriptTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ScriptTask:554","isForCompensation":false,"h":73,"w":115,"title":"0.2 Carga datos de Solictud de recurso","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":493,"x":293},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2468","isForCompensation":false,"h":55,"w":50,"title":"Tipo contrato recurso","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":825,"x":1150},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2467","isForCompensation":false,"h":55,"w":50,"title":"Número del recurso","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":825,"x":1075},{"isInterrupting":false,"class":"ServiceTask","parent":"http://www.eworkplace.swb#swp_Lane:340","uri":"http://www.eworkplace.swb#swp_ServiceTask:984","isForCompensation":false,"h":60,"w":100,"title":"5.3 Notifica kit servicio","container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":118,"x":1118},{"isInterrupting":false,"class":"DataObject","parent":"","uri":"http://www.eworkplace.swb#swp_DataObject:2469","isForCompensation":false,"h":55,"w":50,"title":"Rol / función","isCollection":false,"container":"","description":"","isMultiInstance":false,"labelSize":10,"isLoop":false,"isSequentialMultiInstance":false,"y":725,"x":1150},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:596","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2939","end":"http://www.eworkplace.swb#swp_MessageEndEvent:92"},{"title":"Si","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:887","connectionPoints":"","action":"Si","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:798","end":"http://www.eworkplace.swb#swp_SubProcess:350"},{"title":"No","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:887","connectionPoints":"","action":"No","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:799","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:891"},{"title":"","start":"http://www.eworkplace.swb#swp_SubProcess:350","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3044","end":"http://www.eworkplace.swb#swp_EndEvent:637"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:644","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3041","end":"http://www.eworkplace.swb#swp_UserTask:1795"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1795","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3042","end":"http://www.eworkplace.swb#swp_ServiceTask:1020"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:1020","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3043","end":"http://www.eworkplace.swb#swp_EndEvent:635"},{"title":"","start":"http://www.eworkplace.swb#swp_StartEvent:522","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3040","end":"http://www.eworkplace.swb#swp_ScriptTask:644"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1743","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2893","end":"http://www.eworkplace.swb#swp_SubProcess:330"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1742","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2874","end":"http://www.eworkplace.swb#swp_SubProcess:314"},{"title":"","start":"http://www.eworkplace.swb#swp_MessageStartEvent:37","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2746","end":"http://www.eworkplace.swb#swp_ScriptTask:561"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1741","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2875","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:730"},{"title":"No","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:915","connectionPoints":"","action":"No","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:811","end":"http://www.eworkplace.swb#swp_ScriptTask:596"},{"title":"Si","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:915","connectionPoints":"","action":"Si","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:812","end":"http://www.eworkplace.swb#swp_ScriptTask:641"},{"title":"Por defecto","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:914","connectionPoints":"","class":"DefaultFlow","uri":"http://www.eworkplace.swb#swp_DefaultFlow:252","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:915"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:642","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3039","end":"http://www.eworkplace.swb#swp_ServiceTask:984"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:641","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3045","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:890"},{"title":"Por defecto","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:891","connectionPoints":"","class":"DefaultFlow","uri":"http://www.eworkplace.swb#swp_DefaultFlow:241","end":"http://www.eworkplace.swb#swp_ScriptTask:596"},{"title":"Por defecto","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:730","connectionPoints":"","class":"DefaultFlow","uri":"http://www.eworkplace.swb#swp_DefaultFlow:204","end":"http://www.eworkplace.swb#swp_SubProcess:303"},{"title":"Por defecto","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:890","connectionPoints":"","class":"DefaultFlow","uri":"http://www.eworkplace.swb#swp_DefaultFlow:240","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:887"},{"title":"","start":"http://www.eworkplace.swb#swp_SubProcess:330","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2892","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:914"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1747","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3047","end":"http://www.eworkplace.swb#swp_ScriptTask:648"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1746","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3106","end":"http://www.eworkplace.swb#swp_EndEvent:603"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:648","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3096","end":"http://www.eworkplace.swb#swp_UserTask:1746"},{"title":"","start":"http://www.eworkplace.swb#swp_StartEvent:486","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3136","end":"http://www.eworkplace.swb#swp_UserTask:1747"},{"title":"Si","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:743","connectionPoints":"","action":"Si","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:616","end":"http://www.eworkplace.swb#swp_ScriptTask:642"},{"title":"No","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:743","connectionPoints":"","action":"No","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:619","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:914"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:561","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2877","end":"http://www.eworkplace.swb#swp_UserTask:1741"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:560","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2889","end":"http://www.eworkplace.swb#swp_UserTask:1743"},{"title":"","start":"http://www.eworkplace.swb#swp_MessageStartEvent:83","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2745","end":"http://www.eworkplace.swb#swp_ScriptTask:560"},{"title":"","start":"http://www.eworkplace.swb#swp_MessageStartEvent:82","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2888","end":"http://www.eworkplace.swb#swp_ScriptTask:554"},{"title":"","start":"http://www.eworkplace.swb#swp_SubProcess:314","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2689","end":"http://www.eworkplace.swb#swp_SubProcess:300"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:522","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2750","end":"http://www.eworkplace.swb#swp_UserTask:1690"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:521","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2752","end":"http://www.eworkplace.swb#swp_UserTask:1691"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:504","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2721","end":"http://www.eworkplace.swb#swp_UserTask:1699"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:500","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2704","end":"http://www.eworkplace.swb#swp_UserTask:1695"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:930","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2707","end":"http://www.eworkplace.swb#swp_EndEvent:585"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1688","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2702","end":"http://www.eworkplace.swb#swp_ServiceTask:924"},{"title":"","start":"http://www.eworkplace.swb#swp_StartEvent:462","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2695","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:816"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1690","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2698","end":"http://www.eworkplace.swb#swp_ServiceTask:922"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1691","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2701","end":"http://www.eworkplace.swb#swp_ServiceTask:923"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1693","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2706","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:819"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1695","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2703","end":"http://www.eworkplace.swb#swp_ServiceTask:926"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:518","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2751","end":"http://www.eworkplace.swb#swp_UserTask:1688"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1699","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2708","end":"http://www.eworkplace.swb#swp_ServiceTask:930"},{"title":"Por defecto","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:817","connectionPoints":"","class":"DefaultFlow","uri":"http://www.eworkplace.swb#swp_DefaultFlow:205","end":"http://www.eworkplace.swb#swp_UserTask:1693"},{"title":"Nomina","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:816","connectionPoints":"","action":"Nomina","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:750","end":"http://www.eworkplace.swb#swp_ScriptTask:522"},{"title":"Servicios Profesionales","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:816","connectionPoints":"","action":"Servicios Profesionales","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:751","end":"http://www.eworkplace.swb#swp_ScriptTask:521"},{"title":"Eventual","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:816","connectionPoints":"","action":"Eventual","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:752","end":"http://www.eworkplace.swb#swp_ScriptTask:518"},{"title":"Si","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:819","connectionPoints":"","action":"Si","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:753","end":"http://www.eworkplace.swb#swp_ScriptTask:500"},{"title":"No","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:819","connectionPoints":"","action":"No","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:754","end":"http://www.eworkplace.swb#swp_ScriptTask:504"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:922","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2699","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:817"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:924","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2697","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:817"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:923","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2700","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:817"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:926","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2705","end":"http://www.eworkplace.swb#swp_EndEvent:583"},{"title":"","start":"http://www.eworkplace.swb#swp_SubProcess:300","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2244","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:730"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1751","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2905","end":"http://www.eworkplace.swb#swp_ServiceTask:815"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:815","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2716","end":"http://www.eworkplace.swb#swp_UserTask:1524"},{"title":"","start":"http://www.eworkplace.swb#swp_StartEvent:434","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2906","end":"http://www.eworkplace.swb#swp_UserTask:1751"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1524","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2262","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:748"},{"title":"Si","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:748","connectionPoints":"","action":"Si","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:627","end":"http://www.eworkplace.swb#swp_EndEvent:539"},{"title":"No","start":"http://www.eworkplace.swb#swp_ExclusiveGateway:748","connectionPoints":"","action":"No","class":"ConditionalFlow","uri":"http://www.eworkplace.swb#swp_ConditionalFlow:628","end":"http://www.eworkplace.swb#swp_ErrorEndEvent:8"},{"title":"","start":"http://www.eworkplace.swb#swp_SubProcess:303","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2246","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:743"},{"title":"","start":"http://www.eworkplace.swb#swp_StartEvent:436","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3141","end":"http://www.eworkplace.swb#swp_ScriptTask:286"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:632","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2269","end":"http://www.eworkplace.swb#swp_UserTask:1530"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:286","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3095","end":"http://www.eworkplace.swb#swp_UserTask:1531"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1531","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2718","end":"http://www.eworkplace.swb#swp_ScriptTask:632"},{"title":"","start":"http://www.eworkplace.swb#swp_UserTask:1530","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:3140","end":"http://www.eworkplace.swb#swp_EndEvent:541"},{"title":"","start":"http://www.eworkplace.swb#swp_ScriptTask:554","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2890","end":"http://www.eworkplace.swb#swp_UserTask:1742"},{"title":"","start":"http://www.eworkplace.swb#swp_ServiceTask:984","connectionPoints":"","class":"SequenceFlow","uri":"http://www.eworkplace.swb#swp_SequenceFlow:2882","end":"http://www.eworkplace.swb#swp_ExclusiveGateway:890"}],"uri":"test"}';
        Modeler.loadProcess(strJSON);
        
        //Si viene suri
        //var obj = Modeler.getGraphElementByURI(null, "http://www.eworkplace.swb#swp_SubProcess:303");
        //ToolKit.setLayer(obj.subLayer);
    }
</script>
<%} else {
            out.println("<h1>" + paramRequest.getLocaleString("hereDoc") + "</h1>");
        }
    }%>