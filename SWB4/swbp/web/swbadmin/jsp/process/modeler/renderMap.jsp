<%@page import="org.semanticwb.process.model.Containerable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.process.model.Lane"%>
<%@page import="org.semanticwb.process.model.Pool"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.TransactionSubProcess"%>
<%@page import="org.semanticwb.process.model.SubProcess"%>
<%@page import="org.semanticwb.process.model.ReceiveTask"%>
<%@page import="org.semanticwb.process.model.SendTask"%>
<%@page import="org.semanticwb.process.model.BusinessRuleTask"%>
<%@page import="org.semanticwb.process.model.ManualTask"%>
<%@page import="org.semanticwb.process.model.ScriptTask"%>
<%@page import="org.semanticwb.process.model.ServiceTask"%>
<%@page import="org.semanticwb.process.model.EventBasedGateway"%>
<%@page import="org.semanticwb.process.model.ComplexGateway"%>
<%@page import="org.semanticwb.process.model.ParallelStartEventGateway"%>
<%@page import="org.semanticwb.process.model.ParallelGateway"%>
<%@page import="org.semanticwb.process.model.ExclusiveStartEventGateway"%>
<%@page import="org.semanticwb.process.model.InclusiveGateway"%>
<%@page import="org.semanticwb.process.model.ExclusiveIntermediateEventGateway"%>
<%@page import="org.semanticwb.process.model.ExclusiveGateway"%>
<%@page import="org.semanticwb.process.model.Gateway"%>
<%@page import="org.semanticwb.process.model.TerminationEndEvent"%>
<%@page import="org.semanticwb.process.model.ParallelStartEvent"%>
<%@page import="org.semanticwb.process.model.ScalationEndEvent"%>
<%@page import="org.semanticwb.process.model.ScalationStartEvent"%>
<%@page import="org.semanticwb.process.model.ScalationIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.MultipleIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.MultipleEndEvent"%>
<%@page import="org.semanticwb.process.model.MultipleStartEvent"%>
<%@page import="org.semanticwb.process.model.SignalIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.SignalEndEvent"%>
<%@page import="org.semanticwb.process.model.SignalStartEvent"%>
<%@page import="org.semanticwb.process.model.RuleStartEvent"%>
<%@page import="org.semanticwb.process.model.LinkIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.CompensationEndEvent"%>
<%@page import="org.semanticwb.process.model.CompensationStartEvent"%>
<%@page import="org.semanticwb.process.model.CompensationIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.CancelationEndEvent"%>
<%@page import="org.semanticwb.process.model.ErrorStartEvent"%>
<%@page import="org.semanticwb.process.model.ErrorEndEvent"%>
<%@page import="org.semanticwb.process.model.TimerStartEvent"%>
<%@page import="org.semanticwb.process.model.EndEventNode"%>
<%@page import="org.semanticwb.process.model.MessageEndEvent"%>
<%@page import="org.semanticwb.process.model.IntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.MessageIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.StartEventNode"%>
<%@page import="org.semanticwb.process.model.MessageStartEvent"%>
<%@page import="org.semanticwb.process.model.ParallelIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.ScalationIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.MultipleIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.SignalIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.RuleIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.LinkIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.CompensationIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.CancelationIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.ErrorIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.MessageIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.Event"%>
<%@page import="org.semanticwb.process.model.SequenceFlow"%>
<%@page import="org.semanticwb.process.utils.Point"%>
<%@page import="org.semanticwb.process.model.TimerIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.ConnectionObject"%>
<%@page import="org.semanticwb.process.model.IntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.EndEvent"%>
<%@page import="org.semanticwb.process.model.StartEvent"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.GraphicalElement"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.Instance"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.SWBProcessMgr"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.process.model.FlowNode"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.admin.resources.SWBATemplateEdit"%>
<%@page import="org.semanticwb.platform.SemanticOntology"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<html>
    <body>
<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
String suri = request.getParameter("suri");
String pId = request.getParameter("pId");

if (suri == null) {
    %>
    <script type="text/javascript">
        alert('Ha ocurrido un problema al obtener la instancia');
    </script>
    <%
} else {
    %>
    <script type="text/javascript" src="/swbadmin/jsp/process/modeler/helper_functions.js"></script>
    <script type="text/javascript" src="/swbadmin/jsp/process/modeler/textFlow.js"></script>
    <%
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    ProcessInstance pInstance = (ProcessInstance) ont.getGenericObject(suri);
    FlowNode subProcess = (FlowNode) ont.getGenericObject(pId);
    //System.out.println("ID:"+pId);
    //System.out.println("SubProceso:"+subProcess);
    
    if (pInstance != null) {
        double maxwidth = 0;
        double maxheight = 0;
        Iterator<GraphicalElement> elements = pInstance.getProcessType().listAllContaineds();
        while (elements.hasNext()) {
            GraphicalElement ele = elements.next();
            if (ele.getX()+ele.getWidth() > maxwidth) {
                maxwidth = ele.getX()+ele.getWidth();
            }
            if (ele.getY()+ele.getHeight() > maxheight) {
                maxheight = ele.getY()+ele.getWidth();
            }
        }
        HashMap<FlowNode, FlowNodeInstance> activeTasks = new HashMap<FlowNode, FlowNodeInstance>();
        Iterator<FlowNodeInstance> activeInstances = pInstance.listAllFlowNodeInstance();
        while (activeInstances.hasNext()) {
            FlowNodeInstance ai = activeInstances.next();
            if (ai.getStatus() == Instance.STATUS_OPEN || ai.getStatus() == Instance.STATUS_PROCESSING) {
                if (ai.getFlowNodeType() instanceof Activity) {
                    //TODO: Qué pasará cuando haya más de una instancia del tipo?
                    activeTasks.put(ai.getFlowNodeType(), ai);
                }
            }
        }
        
        ArrayList<String> navPath = new ArrayList<String>();
        SWBResourceURL navUrl = paramRequest.getRenderUrl().setParameter("suri", suri);
        if (subProcess != null) {
            %><a href="<%=navUrl%>">Principal</a>&nbsp;|&nbsp;<%
            Containerable parent = subProcess.getContainer();
            while (parent != null) {
                if (parent instanceof Process) {
                    parent = null;
                } else if (parent instanceof FlowNode) {
                    FlowNode temp = (FlowNode) parent;
                    navUrl.setParameter("pId", parent.getURI());
                    navPath.add("<a href=\""+navUrl.toString()+"\">"+temp.getTitle()+"</a>");
                    parent = ((FlowNode)parent).getContainer();
                }
            }
            for (int i = navPath.size()-1; i>=0; i--) {
                %><%=navPath.get(i)%><%
                %>&nbsp;|&nbsp;<%
            }
            %><b><%=subProcess.getTitle()%></b><%
        }
        %>
        <svg id="modeler" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="<%=maxwidth%>" height="<%=maxheight%>">
            <defs id="globalDef">
                <style type="text/css"><![CDATA[
                    
                    .resizeBox {
                        stroke:#008000;
                        fill:url(#linearGradientStartEvent);
                        fill-opacity:1;
                        stroke-width:1.5;  
                    }
                    
                    .task {
                        stroke:#2c5aa0;
                        fill:url(#linearGradientIntermediateEvent);
                        fill-opacity:1;
                        stroke-width:2;
                    }
                    
                    .taskEvent {
                        stroke:#2c5aa0;
                        fill:url(#linearGradientIntermediateEvent);
                        stroke-dasharray: 3,3;
                        fill-opacity:1;
                        stroke-width:2;
                    }
                    
                    .taskCalled {
                        stroke:#2c5aa0;
                        fill:url(#linearGradientIntermediateEvent);
                        fill-opacity:1;
                        stroke-width:3;
                    }
                    
                    .task_o {
                        stroke:#2cff20;
                        fill:url(#linearGradientIntermediateEvent);
                        fill-opacity:1;
                        stroke-width:2;
                    }                    
                    
                    .startEvent
                    {
                        stroke:#008000;
                        fill:url(#linearGradientStartEvent);
                        fill-opacity:1;
                        stroke-width:1.5;                        
                    }
                    
                    .startEvent_o
                    {
                        stroke:#2cff20;
                        fill:url(#linearGradientStartEvent);
                        fill-opacity:1;
                        stroke-width:1.5;                        
                    }                    
                    
                    .intermediateEvent
                    {
                        stroke:#2c5aa0;
                        fill:url(#linearGradientIntermediateEvent);
                        fill-opacity:1;
                        stroke-width:1.5;
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
                    
                    .intermediateEvent_o
                    {
                        stroke:#2cff20;
                        fill:url(#linearGradientIntermediateEvent);
                        fill-opacity:1;
                        stroke-width:1.5;
                    }                    
                    
                    .intermediateEvent1
                    {
                        stroke:#2c5aa0;
                        fill:none;
                        stroke-width:1;
                    } 
                    
                    .endEvent
                    {
                        stroke:#550000;
                        fill:url(#linearGradientEndEvent);
                        stroke-width:2.5;
                    }
                    
                    .endEvent_o
                    {
                        stroke:#2cff20;
                        fill:url(#linearGradientEndEvent);
                        stroke-width:2.5;
                    }    
                    
                    .gateway
                    {
                        stroke:#d4aa00;
                        fill:url(#linearGradientGateway);
                        fill-opacity:1;
                        stroke-width:2;
                        stroke-opacity:1;
                        stroke-dasharray:none;
                    }
                    
                    .gateway_o
                    {
                        stroke:#2cff20;
                        fill:url(#linearGradientGateway);
                        fill-opacity:1;
                        stroke-width:2;
                        stroke-opacity:1;
                        stroke-dasharray:none;
                    }                    
                    
                    .selectBox
                    {
                        fill:#f0f0f0;
                        fill-opacity:.5;
                        stroke-width:2;
                        stroke-opacity:1;
                        stroke:#2c5aa0                        
                    }
                    
                    .taskMarker
                    {
                        stroke:#2c5aa0;
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
                        fill:none;
                        stroke-width:2.5;
                    }      
                    .intermediateFilledMarker{
                        fill:#2c5aa0;
                        stroke:#ffffff;
                        stroke-width:1;
                    }    
                    .endFilledMarked
                    {
                        fill:#550000;   
                        stroke:#ffffff;
                        stroke-width:1;
                    }
                    
                ]]></style>
                <!--Definición de gradientes para las figuras-->
                <linearGradient id="linearGradientStartEvent" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="10%" style="stop-color:#ffffff;stop-opacity:1" />
                    <stop offset="90%" style="stop-color:#ccffaa;stop-opacity:1" />
                </linearGradient>
                <linearGradient id="linearGradientEndEvent" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="10%" style="stop-color:#ffffff;stop-opacity:1" />
                    <stop offset="90%" style="stop-color:#f4d7d7;stop-opacity:1" />
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
                
                <rect id="resizeBox" x="-5" y="-5" width="10" height="10" class="resizeBox"/>
                
                <!--definición de marcadores para las figuras-->
                <g id="subProcessMarker" transform="scale(0.5, 0.5)" class="taskMarker">
                    <rect width="20" height="20" x="0" y="0" style="fill:#ffffff;fill-opacity:.2"/>
                    <path d="M3 10 L17 10"/>
                    <path d="M10 3 L 10 17" />
                </g>
                <path id="errorMarker" d="m 0.5,1051.8622 17.0774,-39.6847 15.0444,21.9792 19.5171,-27.474 L 34.8582,1048.199 19.8137,1029.6795 0.5,1051.8622 z" transform="scale(0.35,0.35) translate(-26, -1030)" />
                <path id="ruleMarker" d="m 0,0 0,43.9063 46.75,0 0,-43.9063 z m 4.25,9.875 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z" transform="scale(0.35,0.35) translate(-23.5,-23.5)"/>
                <path id="multipleMarker" d="m 0,0 -33.73973,0.37343 -10.78131,-31.973 27.07653,-20.13383 27.51552,19.52961 z" transform="scale(0.35,0.35) translate(17,23)" />
                <path id="parallelMarker" d="m 0,0 0,19.4788 -19.45519,0 0,10.5627 19.45519,0 0,19.4787 10.58626,0 0,-19.4787 19.47871,0 0,-10.5627 -19.47871,0 0,-19.4788 z" transform="scale(0.35,0.35) translate(-5, -25)"/>
                <path id="linkMarker" d="m 0,0 -20.432,-24.2007 0,16.9761 -25.0594,0 0,14.1289 25.0594,0 0,17.332 z" transform="scale(0.35,0.35) translate(23,0)"/>
                <path id="complexMarker" d="m 0,0 0,15.875 -11.25,-11.2187 -2.34375,2.3125 11.25,11.25 -15.90625,0 0,3.3125 15.90625,0 -11.25,11.2187 2.34375,2.3438 11.25,-11.2188 0,15.875 3.28125,0 0,-15.9062 11.25,11.25 2.3125,-2.3438 -11.21875,-11.2187 15.875,0 0,-3.3125 -15.875,0 11.21875,-11.2188 -2.3125,-2.3437 -11.25,11.25 0,-15.9063 z" transform="translate(-1, -20)"/>
                <path id="signalMarker" d="m 0,0 -23.59924,0 -23.59925,0 11.79962,-20.43755 11.79963,-20.43755 11.79962,20.43755 z" transform="scale(0.35,0.35) translate(24,14)"/>
                <path id="scalationMarker" transform="scale(0.35,0.35) translate(0,-26)" d="m 0,0 -21.34041,47.167 21.34041,-17.3811 21.340402,17.3811 -21.340402,-47.167 z" />
                <path id="cancelMarker" d="m 0,0 -18.3627,18.3627 -18.3848004,-18.3848 -3.3366,3.3367 18.3847004,18.3847 -18.3847004,18.3848 3.3366,3.3367 18.3848004,-18.3848 18.3627,18.3627 3.3366,-3.3367 -18.3626,-18.3627 18.3626,-18.3626 z" transform="scale(0.35,0.35) translate(18, -22)"/>
                <path id="manualMarker" transform="scale(0.7,0.7)" d="m 0,0 c -0.27155,0 -0.5225,0.072 -0.75179,0.1792 -0.007,0 -0.0189,0 -0.0259,0 -1.25518,0.3798 -2.16186,4.1013 -2.9035,6.1425 -0.22877,0.5536 -0.36294,1.1549 -0.36294,1.7916 l 0,4.7861 c 0,2.6228 2.13932,4.7348 4.79597,4.7348 l 5.0552,0 5.69034,0 7.72539,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -3.11089,0 c 0.0118,-0.043 0.0283,-0.085 0.0389,-0.128 l 4.16081,0 c 0.97664,0 1.76285,-0.7762 1.76285,-1.7404 0,-0.9642 -2.73949,-1.7404 -1.76285,-1.7404 l -4.01823,0 0,-0.064 5.23667,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -5.39221,0 c -0.0116,-0.046 -0.0132,-0.095 -0.0259,-0.1408 l 7.56984,0 c 0.97663,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9642 -0.78621,-1.7404 -1.76284,-1.7404 l -12.18433,0 -0.92031,0 c 0.69854,-0.2311 1.20547,-0.8686 1.20547,-1.638 0,-0.9642 -0.7862,-1.7404 -1.76284,-1.7404 l -10.01967,0 z" class="pathMarker" />
                <g id="messageThrowMarker" transform="scale(1.3,1.3) translate(-7.5,-4.5)" >
                    <path d="m 0,0 0,9.175062 14.84706,0 0,-9.175062 -0.0245,0 -7.40716,4.648917 -7.41535,-4.648917 z" style="stroke:#ffffff;stroke-opacity:0.4"/>
                    <path d="m 0.6,0 6.88839,4.318911 6.88838,-4.318911" style="stroke:#ffffff;stroke-opacity:0.4"/>
                </g>
                <g id="messageCatchMarker" transform="scale(0.35,0.35) translate(-27,-17)" >
                    <rect width="52.704407" height="32.573116" x="0" y="0"/>
                    <path d="m 0,0 26.30906,16.49536 26.30905,-16.49536" id="path2824-1"/>
                </g>
                <g id="compensaMarker" transform="scale(0.35,0.35) translate(-2,-13)">
                    <path d="m 0,0 -28.36636,0 14.18318,-24.56599 z" transform="matrix(0,-1,1,0,0,0)"/>
                    <path d="m 0,0 -28.36636,0 14.18318,-24.56599 z" transform="matrix(0,-1,1,0,23,0)"/>
                </g>
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
                <g id="userMarker" class="taskMarker" transform="scale(0.8, 0.8) translate(0,-1042)">
                    <path d="m 0,1039 c -3.3158023,0 -6,2.8058 -6,6.25 0,2.0045 0.9288788,3.7654 2.34375,4.9062 -3.3002786,1.7745 -5.6842907,5.8793 -6,10.75 l 19.3125,0 c -0.3158218,-4.8705 -2.7074728,-8.9755 -6,-10.75 C 5.0711212,1049.0154 6,1047.2545 6,1045.25 6,1041.8058 3.3158023,1039 0,1039 z" class="pathMarker" />
                    <path d="m -377.4453,473.79996 a 9.3826418,9.3826418 0 0 1 -12.52292,0.66553" transform="matrix(0.6398828,0,0,0.66465394,245.76439,734.73647)" class="pathMarker" />
                    <path d="m -5.1190626,1060.732 0,-3.8413" class="pathMarker"/>
                    <path d="m 5.1190626,1060.732 0,-3.8413" class="pathMarker" />
                    <path d="m 0.06204402,1039.6046 c -3.10856302,0 -5.66539592,2.4352 -5.68305502,5.5312 0.8963285,-1.0836 1.7094089,-1.6888 3.0835319,-1.6888 2.07094865,0 4.4437605,1.0758 6.2188589,0.7688 0.570252,-0.099 1.2624582,-0.3804 1.939646,-0.7272 -0.6073258,-2.271 -3.0493799,-3.884 -5.55898178,-3.884 z" style="fill:#2c5aa0;stroke-width:1;" />
                </g>
                <g id="serviceMarker" transform="scale(0.9,0.9)" class="taskMarker">
                    <path d="m 0,0 1.478093,-2.5912 c -0.762317,-0.4662 -1.601697,-0.8229 -2.495483,-1.0366 l -0.767843,2.8793 -2.495483,0 -0.787044,-2.8793 c -0.895236,0.2142 -1.733065,0.5691 -2.495483,1.0366 l 1.487693,2.5721 -1.766033,1.7659 -2.581869,-1.478 c -0.467511,0.7623 -0.822527,1.6003 -1.036583,2.4952 l 2.86981,0.7678 0,2.5048 -2.860209,0.7775 c 0.21606,0.8875 0.562993,1.7267 1.026982,2.4856 l 2.562669,-1.478" class="pathMarker" />
                    <path d="m 0,0 c -0.989621,0.2366 -1.915788,0.629 -2.758588,1.1457 l 1.644541,2.8433 -1.952235,1.9521 -2.85408,-1.6338 c -0.516803,0.8427 -0.909247,1.769 -1.145882,2.7583 l 3.172388,0.8488 0,2.769 -3.161774,0.8593 c 0.238849,0.9811 0.622356,1.9088 1.135268,2.7477 l 2.832859,-1.6337 1.952235,1.952 -1.633935,2.8539 c 0.84654,0.5219 1.774518,0.918 2.769203,1.1564 l 0.848804,-3.1828 2.758588,0 0.870025,3.1828 c 0.988288,-0.238 1.914849,-0.6289 2.758588,-1.1459 l -1.644542,-2.8431 1.952236,-1.9521 2.864694,1.6337 c 0.515612,-0.8389 0.908032,-1.7642 1.145874,-2.7477 l -3.182994,-0.8593 0,-2.769 3.182994,-0.87 c -0.237717,-0.9884 -0.628065,-1.9158 -1.145874,-2.7583 l -2.854088,1.655 -1.952235,-1.9521 1.633935,-2.8645 c -0.842691,-0.5152 -1.77056,-0.9096 -2.758588,-1.1457 l -0.848804,3.1828 -2.758588,0 z m 2.238708,5.697 c 1.994007,0 3.607392,1.6133 3.607392,3.6072 0,1.9938 -1.613385,3.6176 -3.607392,3.6176 -1.994007,0 -3.607392,-1.6238 -3.607392,-3.6176 0,-1.9939 1.613385,-3.6072 3.607392,-3.6072 z" class="pathMarker" />
                </g>
                <g id="scriptMarker" transform="scale(0.7,0.7)" class="taskMarker">
                    <path d="m 0,0 c 3.5628793,2.98 3.5212524,7.9199 0,10.8403 l 0,0.1186 13.5952063,-10e-4 c 4.246065,-3.0502 4.698752,-8.6843 0.03211,-12.4597 -1.719778,-1.441 -2.097829,-3.2691 -2.297848,-5.5453 0,-1.9186 0.795633,-3.6321 2.05209,-4.7945 l -9.1791045,0 c -7.65536286,-0.3702 -8.7288909,8.3174 -4.2024826,11.8414 z" class="pathMarker" />
                    <path d="m 0,-8 9.5315769,0" class="pathMarker" />
                    <path d="m -0.5,-4.1568 9.5315769,0" class="pathMarker" />
                    <path d="m 2.5,0 9.5315769,0" class="pathMarker" />
                    <path d="m 4.5,4 9.5315768,0" class="pathMarker" />
                    <path d="m 4,8 9.5315768,0" class="pathMarker" />
                </g>
                <marker id="Triangle" viewBox="0 0 10 10" refX="0" refY="5" markerUnits="strokeWidth" markerWidth="6" markerHeight="5" orient="auto">
                    <path d="M 0 0 L 10 5 L 0 10" style="fill:#2c5aa0"/>
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
                    <use xlink:href="#ruleMarker" x="0" y="0" class="startFilledMarker"/>
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
                <g id="data" styled="stroke:#666666">
                    <path transform="scale(0.7,0.7)" d="m -25,-25 0,74.61135 58.22841,0 0,-55.2509 -19.88426,-19.36045 -38.34415,0 z" id="rect6567" style="fill:url(#linearGradientDataObject);stroke-width:2.5;" />
                    <path transform="scale(0.7,0.7)" d="m 12.5,-24 0,19.5625 20.0601,0" id="rect6569" style="fill:none;stroke-width:2.5;" />
                </g>
                <g id="dataStore" styled="stroke:#666666;">
                    <path d="m 0,0 c -27.75868,0 -50.28125,5.6228 -50.28125,12.5625 0,0.1516 0.0412,0.2871 0.0625,0.4375 l -0.0625,0 0,61.5624 0,0.3125 0.0312,0 c 0.68314,6.7909 22.92187,12.25 50.25,12.25 27.3249498,0 49.53067,-5.4602 50.21875,-12.25 l 0.0312,0 0,-0.3125 0,-61.5624 -0.0312,0 c 0.0212,-0.1501 0.0312,-0.2862 0.0312,-0.4375 0,-6.9397 -22.4913202,-12.5625 -50.25,-12.5625 z" style="fill:url(#linearGradientDataObject);stroke-width:2.5;" transform="scale(0.6,0.6) translate(22,-24)"/>
                    <path d="m 0,0 c 0,6.9397 -22.5028602,12.5654 -50.26153,12.5654 -27.39179,0 -49.73975,-5.4833 -50.25272,-12.33" style="fill:none;stroke-width:2.5;" transform="scale(0.6,0.6) translate(72,-12)"/>
                </g>	
                <g id="dataInput" styled="stroke:#666666">
                    <use xlink:href="#data" x="0" y="0"/>
                    <use xlink:href="#linkMarker" x="-5" y="-8" style="fill:none;stroke:#666666;stroke-width:2.5;" transform="scale(0.8,0.8)"/>
                </g>
                <g id="dataOutput" styled="stroke:#666666">
                    <use xlink:href="#data" x="0" y="0"/>
                    <use xlink:href="#linkMarker" x="-5" y="-8" style="fill:#666666;stroke:none;stroke-width:2.5;" transform="scale(0.8,0.8)"/>
                </g>
                <!--Definición de tareas-->
                <g id="task" style="stroke:#2c5aa0">
                    <rect x="-50" y="-30" rx="10" ry="10" width="100" height="60" class="task"/>
                </g>
                <g id="userTask" style="stroke:#2c5aa0">
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
                <g id="pool" styled="stroke:#ADADAE;">
                    <rect width="600" x="-300" y="-100" height="200" style="fill:#E8E8FF;stroke-width:2"/>
                    <path d="m -280,-100 l 0,200" style="fill:none;stroke-width:2;"/>
                </g>
            </defs>
            <g id="viewport">
        <%
        elements = pInstance.getProcessType().listAllContaineds();
        while (elements.hasNext()) {
            GraphicalElement ele = elements.next();
            
            if (ele instanceof Pool) {
                int w = ele.getWidth();
                int h = ele.getHeight();
                int x = ele.getX();
                int y = ele.getY();
                int realx = x - (w/2);
                int realy = y - (h/2);
                %>
                <g id="<%=ele.getURI()%>">
                    <rect width="<%=w%>" x="<%=realx%>" y="<%=realy%>" height="<%=h%>" style="fill:#E8E8FF;stroke-width:2;stroke:#ADADAE;"/>
                    <path d="M<%=realx+20%> <%=realy%> L<%=realx+20%> <%=realy+h%>" style="fill:none;stroke-width:2;stroke:#ADADAE;"/>
                    <text font-size="11" font-family="Verdana, Geneva, sans-serif" id="<%=ele.getURI()%>/text" text-anchor="middle"/>
                </g>
                <%
            }
        }
        
        elements = pInstance.getProcessType().listAllContaineds();
        while (elements.hasNext()) {
            GraphicalElement ele = elements.next();
            if ((ele.getContainer().equals(subProcess) || (subProcess == null && ele.getContainer() == pInstance.getProcessType())) && !(ele instanceof Pool)) {
                int w = ele.getWidth();
                int h = ele.getHeight();
                int x = ele.getX();
                int y = ele.getY();
                int realx = x - (w/2);
                int realy = y - (h/2);
                String title = ele.getTitle();
                
                if (ele instanceof Activity) {
                    String cssClass = "task";
                    if (activeTasks.containsKey(ele)) {
                        cssClass = "task_o";
                    }
                    %>
                    <g id="<%=ele.getURI()%>">
                        <title><%=title%></title>
                        <%
                        if (activeTasks.containsKey(ele) && ele instanceof UserTask) {
                            String url = activeTasks.get(ele).getUserTaskUrl();
                            %>
                            <a xlink:href="<%=url%>">
                            <%
                        }
                        %>
                        <rect x="<%=realx%>" y="<%=realy%>" rx="10" ry="10" width="<%=w%>" height="<%=h%>" class="<%=cssClass%>"/>
                        <%
                        if (ele instanceof UserTask) {
                            %>
                            <use xlink:href="#userMarker" x="<%=realx+12%>" y="<%=realy+8%>" class="task"/>
                            <%
                        } else if (ele instanceof ServiceTask) {
                            %>
                            <use xlink:href="#serviceMarker" x="<%=realx+13%>" y="<%=realy+9%>" class="task"/>
                            <%
                        } else if (ele instanceof ScriptTask) {
                            %>
                            <use xlink:href="#scriptMarker" x="<%=realx+9%>" y="<%=realy+14%>" class="task"/>
                            <%
                        } else if (ele instanceof ManualTask) {
                            %>
                            <use xlink:href="#manualMarker" x="<%=realx+10%>" y="<%=realy+6%>" class="task"/>
                            <%
                        } else if (ele instanceof BusinessRuleTask) {
                            %>
                            <use xlink:href="#ruleMarker" x="<%=realx+14%>" y="<%=realy+14%>" class="intermediateFilledMarker"/>
                            <%
                        } else if (ele instanceof SendTask) {
                            %>
                            <use xlink:href="#messageThrowMarker" x="<%=realx+14%>" y="<%=realy+12%>" class="intermediateFilledMarker"/>
                            <%
                        } else if (ele instanceof ReceiveTask) {
                            %>
                            <use xlink:href="#messageCatchMarker" x="<%=realx+14%>" y="<%=realy+12%>" class="intermediateMarker"/>
                            <%
                        } else if (ele instanceof TransactionSubProcess) {
                            %>
                            <rect x="<%=realx+3%>" y="<%=realy+3%>" rx="8" ry="8" width="<%=w-6%>" height="<%=h-6%>" class="<%=cssClass%>"/>
                            <%
                        }

                        if (ele instanceof SubProcess || ele instanceof TransactionSubProcess) {
                            SWBResourceURL url = paramRequest.getRenderUrl().setParameter("suri", suri).setParameter("pId", ele.getURI());
                            %>
                            <a xlink:href="<%=url.toString()%>">
                                <use xlink:href="#subProcessMarker" x="<%=x-5%>" y="<%=y+h/2-15%>" class="intermediateMarker"/>
                            </a>
                            <%
                        }
                        if (activeTasks.containsKey(ele) && ele instanceof UserTask) {
                            %>
                            </a>
                            <%
                        }
                        %>
                        <text x="<%=x%>" y="<%=y%>" font-size="11" font-family="Verdana, Geneva, sans-serif" id="<%=ele.getURI()%>/text" text-anchor="middle"/>
                        <%
                        if (activeTasks.containsKey(ele)) {
                            %>
                            <animate id="anim1" attributeName="opacity" from="0" begin="0s; anim2.end" to="1" dur="0.7s"/>
                            <animate id="anim2" attributeName="opacity" from="1" to="0" begin="anim1.end" dur="0.7s"/>
                            <%
                        }
                        %>
                    </g>
                    <%
                } else if (ele instanceof Event) {
                    String ref = "event";
                    String cssClass = "";
                    String mcssClass = "";
                    if (ele instanceof StartEventNode) {
                        ref = "startEvent";
                        cssClass = "startEvent";
                        mcssClass = "startMarker";
                        if (ele instanceof RuleStartEvent) {
                            mcssClass = "startFilledMarker";
                        }
                    } else if (ele instanceof EndEventNode) {
                        ref = "endEvent";
                        cssClass = "endEvent";
                        mcssClass = "endFilledMarked";
                    } else if (ele instanceof IntermediateCatchEvent) {
                        IntermediateCatchEvent ice = (IntermediateCatchEvent) ele;
                        //System.out.println(ice.getTitle() + (ice.isInterruptor()?" es interruptor":" no es interruptor"));
                        ref = "intermediateEvent";
                        cssClass = ice.isInterruptor()?"intermediateEvent":"intermediateInterruptingEvent";
                        mcssClass = "intermediateMarker";
                        if (ele instanceof RuleIntermediateCatchEvent) {
                            mcssClass = "intermediateFilledMarker";
                        }
                    } else if (ele instanceof IntermediateThrowEvent) {
                        ref = "intermediateEvent";
                        cssClass = "intermediateEvent";
                        mcssClass = "intermediateFilledMarker";
                    }
                    %>
                    <g id="<%=ele.getURI()%>">
                        <use xlink:href="#<%=ref%>" x="<%=x%>" y="<%=y%>" class="<%=cssClass%>"/>
                        <%
                        if (ele instanceof MessageStartEvent || ele instanceof MessageIntermediateCatchEvent || ele instanceof MessageIntermediateThrowEvent || ele instanceof MessageEndEvent) {
                            %>
                            <use xlink:href="#messageCatchMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof TimerIntermediateCatchEvent || ele instanceof TimerStartEvent) {
                            %>
                            <use xlink:href="#timerMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof ErrorIntermediateCatchEvent || ele instanceof ErrorEndEvent || ele instanceof ErrorStartEvent) {
                            %>
                            <use xlink:href="#errorMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof CancelationIntermediateCatchEvent || ele instanceof CancelationEndEvent) {
                            %>
                            <use xlink:href="#cancelMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof CompensationIntermediateCatchEvent || ele instanceof CompensationIntermediateThrowEvent || ele instanceof CompensationStartEvent || ele instanceof CompensationEndEvent) {
                            %>
                            <use xlink:href="#compensaMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof LinkIntermediateCatchEvent || ele instanceof LinkIntermediateThrowEvent) {
                            %>
                            <use xlink:href="#linkMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof RuleIntermediateCatchEvent || ele instanceof RuleStartEvent) {
                            %>
                            <use xlink:href="#ruleMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof SignalIntermediateCatchEvent || ele instanceof SignalIntermediateThrowEvent || ele instanceof SignalStartEvent || ele instanceof SignalEndEvent) {
                            %>
                            <use xlink:href="#signalMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof MultipleIntermediateCatchEvent || ele instanceof MultipleIntermediateThrowEvent || ele instanceof MultipleStartEvent || ele instanceof MultipleEndEvent) {
                            %>
                            <use xlink:href="#multipleMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof ScalationIntermediateCatchEvent || ele instanceof ScalationIntermediateThrowEvent || ele instanceof ScalationStartEvent || ele instanceof ScalationEndEvent) {
                            %>
                            <use xlink:href="#scalationMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof ParallelIntermediateCatchEvent || ele instanceof ParallelStartEvent) {
                            %>
                            <use xlink:href="#parallelMarker" x="<%=x%>" y="<%=y%>" class="<%=mcssClass%>"/>
                            <%
                        } else if (ele instanceof TerminationEndEvent) {
                            System.out.println("termination");
                            %>
                            <circle r="8" cx="<%=x%>" cy="<%=y%>" class="endFilledMarked"/>
                            <%
                        }
                        %>
                        <text x="<%=x%>" y="<%=y%>" font-size="11" font-family="Verdana, Geneva, sans-serif" id="<%=ele.getURI()%>/text" text-anchor="middle"/>
                    </g>
                    <%
                } else if (ele instanceof Gateway) {
                    if (ele instanceof ExclusiveGateway) {
                        %>
                        <use xlink:href="#exclusiveDataGateway" x="<%=x%>" y="<%=y%>" class="gateway"/>
                        <%
                    } else if (ele instanceof ExclusiveIntermediateEventGateway) {
                        %>
                        <use xlink:href="#eventGateway" x="<%=x%>" y="<%=y%>" class="gateway"/>
                        <%
                    } else if (ele instanceof InclusiveGateway) {
                        %>
                        <use xlink:href="#inclusiveDataGateway" x="<%=x%>" y="<%=y%>" class="gateway"/>
                        <%
                    } else if (ele instanceof ExclusiveStartEventGateway) {
                        %>
                        <use xlink:href="#exclusiveStartGateway" x="<%=x%>" y="<%=y%>" class="gateway"/>
                        <%
                    } else if (ele instanceof ParallelGateway) {
                        %>
                        <use xlink:href="#parallelGateway" x="<%=x%>" y="<%=y%>" class="gateway"/>
                        <%
                    } else if (ele instanceof ParallelStartEventGateway) {
                        %>
                        <use xlink:href="#parallelStartGateway" x="<%=x%>" y="<%=y%>" class="gateway"/>
                        <%
                    } else if (ele instanceof ComplexGateway) {
                        %>
                        <use xlink:href="#complexGateway" x="<%=x%>" y="<%=y%>" class="gateway"/>
                        <%
                    }
                    %>
                    <text x="<%=x%>" y="<%=y%>" font-size="11" font-family="Verdana, Geneva, sans-serif" id="<%=ele.getURI()%>/text" text-anchor="middle"/>
                    <%
                }

                Iterator<ConnectionObject> conns = ele.listOutputConnectionObjects();
                while (conns.hasNext()) {
                    ConnectionObject con = conns.next();
                    String handles[] = con.getConnectionPoints().split("\\|");
                    String arrow = "Triangle";
                    if (con instanceof SequenceFlow) {
                        arrow = "Triangle";
                    }

                    Point startPoint = con.getSartConnectionPoint(ele, con.getTarget());
                    Point endPoint = con.getEndConnectionPoint(con.getTarget(), ele);
                    double dx = 0;
                    double dy = 0;
                    double inter1x = con.getInter1ConnectionX(ele, con.getTarget(), startPoint, endPoint);
                    double inter1y = con.getInter1ConnectionY(ele, con.getTarget(), startPoint, endPoint);
                    double inter2x = con.getInter2ConnectionX(ele, con.getTarget(), startPoint, endPoint);
                    double inter2y = con.getInter2ConnectionY(ele, con.getTarget(), startPoint, endPoint);

                    if (inter2y == endPoint.getY()) {
                        dx = dx-6;
                    }

                    if(inter2x == endPoint.getX()) {
                        dy = -6;
                    }

                    if (con.getConnectionPoints() == null || con.getConnectionPoints().trim().length() == 0) {
                        %>
                        <path d="M<%=startPoint.getX()%> <%=startPoint.getY()%> L<%=inter1x%> <%=inter1y%> L<%=inter2x%> <%=inter2y%> L<%=endPoint.getX()+dx%> <%=endPoint.getY()+dy%>" style="stroke-width:2;fill:none;stroke:#2c5aa0" marker-end="url(#<%=arrow%>)"/>
                        <%
                    } else {
                        //TODO: recalcular el punto final dado el último punto de los handles y la ubicación del target
                        String pathData = "M"+startPoint.getX()+" "+startPoint.getY();
                        for (int i = 0; i < handles.length; i++) {
                            Point p = Point.fromString(handles[i]);
                            if (p != null) {
                                pathData += " L" + p.getX()+" "+p.getY();
                            }
                        }
                        pathData += " L"+(endPoint.getX()+dx)+" "+(endPoint.getY()+dy);
                        %>
                        <path d="<%=pathData%>" style="stroke-width:2;fill:none;stroke:#2c5aa0" marker-end="url(#<%=arrow%>)"/>
                        <%
                    }
                }
            }
        }
        %>
            </g>
        </svg>
        <script type="text/javascript">
            var myFlowText;
            var dy;
            var ref;
            var parent;
            var textBbox;
            var textWidth;
            var textHeight;
            var eleWidth;
            var eleHeight;
            var eleX;
            var eleY;
            <%
            elements = pInstance.getProcessType().listAllContaineds();
            while (elements.hasNext()) {
                GraphicalElement ele = elements.next();
                String title = ele.getTitle().trim();
                if (ele instanceof Event || ele instanceof Gateway) {
                    %>
                    eleWidth = 80;
                    eleHeight = 80;
                    <%
                } else {
                    %>
                    eleWidth = <%=ele.getWidth()%>;
                    eleHeight = <%=ele.getHeight()%>;
                    <%
                }
                if (ele instanceof ExclusiveStartEventGateway || ele instanceof EventBasedGateway || ele instanceof ParallelGateway || ele instanceof ParallelStartEventGateway) {
                    title = "";
                }
                
                if (title != null && title.length() > 0) {
                    %>
                    ref = document.getElementById('<%=ele.getURI()%>/text');
                    parent = document.getElementById('<%=ele.getURI()%>');
                    if (ref != null && ref != 'undefined') {
                        myFlowText = "<%=title%>";
                        <%
                        if (ele instanceof Pool || ele instanceof Lane) {
                            %>
                            dy = textFlow(myFlowText,ref,eleHeight,<%=ele.getX() - ele.getWidth() / 2 + 10%>,11,false);
                            <%
                        } else {
                            %>
                            dy = textFlow(myFlowText,ref,eleWidth,<%=ele.getX()%>,11,false);
                            <%
                        }
                        %>
                        textBbox = ref.getBBox();
                        textWidth = textBbox.width;
                        textHeight = textBbox.height;
                        eleX = <%=ele.getX() - ele.getWidth() / 2%>;
                        eleY = <%=ele.getY() - ele.getHeight() / 2%>;
                        <%
                        if (ele instanceof Event) {
                            %>
                            eleY += 6;
                            <%
                        } else if (ele instanceof Gateway) {
                            %>
                            eleY += 24;
                            <%
                        }
                        if (ele instanceof Pool || ele instanceof Lane) {
                            %>
                            console.log(textBbox);
                            ref.setAttribute("x", (eleX - textWidth / 2) + 10);
                            ref.setAttribute("y", eleY + Math.abs((eleHeight - textHeight)) / 2 + 10);
                            ref.setAttribute("transform", "rotate(-90 "+(parseFloat(ref.getAttribute("x"))+textWidth/2 - textHeight/2 + 10)+" "+ (eleY + Math.abs((eleHeight - textHeight)) / 2 +10) +")");
                            console.log(textBbox);
                            <%
                        } else {
                            %>
                            ref.setAttribute("x", (eleX - ((eleWidth - textWidth) / 2)));
                            ref.setAttribute("y", eleY + (eleHeight - textHeight) / 2 + 8);
                            <%
                        }
                        %>
                    }
                    <%
                }
            }
            %>
        </script>
        <%
    }
}
%>
    </body>
<html>