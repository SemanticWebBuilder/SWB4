<%@page import="java.io.File"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.semanticwb.portal.SWBResourceMgr"%>
<%@page import="org.semanticwb.process.model.ConnectionObject"%>
<%@page import="org.semanticwb.process.model.ItemAware"%>
<%@page import="org.semanticwb.process.model.ManualTask"%>
<%@page import="org.semanticwb.process.model.ScriptTask"%>
<%@page import="org.semanticwb.process.model.SignalEndEvent"%>
<%@page import="org.semanticwb.process.model.EndEventNode"%>
<%@page import="org.semanticwb.process.model.SignalIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.IntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.TimerIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.SignalIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.TimerStartEvent"%>
<%@page import="org.semanticwb.process.model.SignalStartEvent"%>
<%@page import="org.semanticwb.process.model.IntermediateCatchEvent"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.resources.sem.HTMLContent"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.RoleRef"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.SubProcessInstance"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.StartEventNode"%>
<%@page import="org.semanticwb.process.model.Lane"%>
<%@page import="org.semanticwb.process.model.Instance"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.SWBProcessMgr"%>
<%@page import="org.semanticwb.process.model.GraphicalElement"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<script type="text/javascript">
    dojo.require("dojo.fx");
    dojo.require("dijit.dijit");
    function expande(elementId) {
        var el = document.getElementById(elementId);
        var anim1 = dojo.fx.wipeIn({node:el, duration:500 });
        var anim2 = dojo.fadeIn({node:el, duration:500 });
        dojo.fx.combine([anim1,anim2]).play();
    }

    function colapsa(elementId) {
        var el = document.getElementById(elementId);
        var anim1 = dojo.fx.wipeOut({node:el, duration:500 });
        var anim2 = dojo.fadeOut({node:el, duration:500 });
        dojo.fx.combine([anim1,anim2]).play();
    }
</script>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();
String lang = "es";
String pFilter = request.getParameter("pid");
if (pFilter == null || pFilter.trim().equals("")) pFilter="filter_all";

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}

%>
<form method="post" action="">
    <label for="pid">Seleccione un proceso</label>
    <select name="pid" onchange="this.form.submit()">
        <option value="filter_all" <%=pFilter.equals("filter_all")?"selected":""%>>Ninguno</option>
        <%
        Iterator<Process> processes = SWBComparator.sortByDisplayName(Process.ClassMgr.listProcesses(site), lang);
        while(processes.hasNext()) {
            Process process = processes.next();
            %><option value="<%=process.getId()%>" <%=pFilter.equals(process.getId())?"selected":""%>><%=process.getDisplayTitle(lang)%></option><%
        }
        %>
    </select>
</form>

<%
if (!pFilter.equals("filter_all")) {
    Process process = Process.ClassMgr.getProcess(pFilter, site);
    %><h1>Configuraci&oacute;n de elementos del proceso "<%=process.getDisplayTitle(lang)%>"</h1><%
    Iterator<GraphicalElement> gElements = process.listContaineds();
    while (gElements.hasNext()) {
        GraphicalElement gElement = gElements.next();
        if (gElement instanceof StartEventNode) {
            String type = "Evento de Inicio Normal";
            StartEventNode sevt = (StartEventNode)gElement;
            String desc = sevt.getDescription();
            String actCode = sevt.getActionCode();

            if (sevt instanceof SignalStartEvent) type="Evento de inicio de Señal";
            if (sevt instanceof TimerStartEvent) type="Evento de inicio temporizado";

            if (desc == null || desc.trim().equals("")) desc = "Ninguna";
            %>
                <h2><%=sevt.getDisplayTitle(lang)%> (<%=type%>)</h2><hr/>
                <p>
                    <b>Descripci&oacute;n</b>: <%=desc%>
                </p>
                <p>
                    <b>C&oacute;digo de acci&oacute;n</b>: <%=actCode%>
                </p>
            <%
        } else if (gElement instanceof IntermediateCatchEvent) {
            String type = "Evento Intermedio";
            IntermediateCatchEvent sevt = (IntermediateCatchEvent)gElement;
            String desc = sevt.getDescription();
            String actCode = sevt.getActionCode();

            if (sevt instanceof SignalIntermediateCatchEvent) type="Evento Intermedio receptor de Señal";
            if (sevt instanceof TimerIntermediateCatchEvent) type="Evento intermedio temporizado";

            if (desc == null || desc.trim().equals("")) desc = "Ninguna";
            %>
                <h2><%=sevt.getDisplayTitle(lang)%> (<%=type%>)</h2><hr/>
                <p>
                    <b>Descripci&oacute;n</b>: <%=desc%>
                </p>
                <p>
                    <b>C&oacute;digo de acci&oacute;n</b>: <%=actCode%>
                </p>
            <%
        } else if (gElement instanceof IntermediateThrowEvent) {
            String type = "Evento Intermedio";
            IntermediateThrowEvent sevt = (IntermediateThrowEvent)gElement;
            String desc = sevt.getDescription();
            String actCode = sevt.getActionCode();

            if (sevt instanceof SignalIntermediateThrowEvent) type="Evento Intermedio disparador de Señal";

            if (desc == null || desc.trim().equals("")) desc = "Ninguna";
            %>
                <h2><%=sevt.getDisplayTitle(lang)%> (<%=type%>)</h2><hr/>
                <p>
                    <b>Descripci&oacute;n</b>: <%=desc%>
                </p>
                <p>
                    <b>C&oacute;digo de acci&oacute;n</b>: <%=actCode%>
                </p>
            <%
        } else if (gElement instanceof EndEventNode) {
            String type = "Evento Final Normal";
            EndEventNode sevt = (EndEventNode)gElement;
            String desc = sevt.getDescription();
            String actCode = sevt.getActionCode();

            if (sevt instanceof SignalEndEvent) type="Evento final con Señal";

            if (desc == null || desc.trim().equals("")) desc = "Ninguna";
            %>
                <h2><%=sevt.getDisplayTitle(lang)%> (<%=type%>)</h2><hr/>
                <p>
                    <b>Descripci&oacute;n</b>: <%=desc%>
                </p>
                <p>
                    <b>C&oacute;digo de acci&oacute;n</b>: <%=actCode%>
                </p>
            <%
        } else if (gElement instanceof ScriptTask) {
            String type = "Tarea de Script";
            ScriptTask stask = (ScriptTask)gElement;
            String desc = stask.getDescription();
            String actCode = stask.getScriptCode();
            if (actCode == null || actCode.trim().equals("")) actCode = "ninguno";

            ArrayList<ItemAware> itemsAware = new ArrayList<ItemAware>();
            itemsAware.addAll(stask.listHerarquicalRelatedItemAware());
            Iterator<ConnectionObject> cobs = stask.listInputConnectionObjects();
            while(cobs.hasNext()) {
                ConnectionObject cob = cobs.next();
                if (cob.getSource() instanceof ItemAware) {
                    itemsAware.add((ItemAware)cob.getSource());
                }
            }

            cobs = stask.listOutputConnectionObjects();
            while(cobs.hasNext()) {
                ConnectionObject cob = cobs.next();
                if (cob.getTarget() instanceof ItemAware) {
                    itemsAware.add((ItemAware)cob.getTarget());
                }
            }
            
            if (desc == null || desc.trim().equals("")) desc = "Ninguna";
                %>
                <h2><%=stask.getDisplayTitle(lang)%> (<%=type%>)</h2><hr/>
                <p>
                    <b>Descripci&oacute;n</b>: <%=desc%>
                </p>
                <p>
                    <b>C&oacute;digo del script</b>: <%=actCode%>
                </p>
                <%
                Iterator<ItemAware> items = itemsAware.iterator();
                if (items.hasNext()) {
                    %>
                    <p>
                        <b>Objetos de datos relacionados</b>:
                    </p>
                    <ul>
                        <%
                        while(items.hasNext()) {
                            ItemAware item = items.next();
                            %><li><%=item.getDisplayTitle(lang)%></li><%
                        }
                        %>
                    </ul>
                    <%
                }
        } else if (gElement instanceof UserTask) {
            String type = "Tarea de Usuario";
            UserTask utask = (UserTask)gElement;
            String desc = utask.getDescription();

            ArrayList<ItemAware> itemsAware = new ArrayList<ItemAware>();
            itemsAware.addAll(utask.listHerarquicalRelatedItemAware());
            Iterator<ConnectionObject> cobs = utask.listInputConnectionObjects();
            while(cobs.hasNext()) {
                ConnectionObject cob = cobs.next();
                if (cob.getSource() instanceof ItemAware) {
                    itemsAware.add((ItemAware)cob.getSource());
                }
            }

            cobs = utask.listOutputConnectionObjects();
            while(cobs.hasNext()) {
                ConnectionObject cob = cobs.next();
                if (cob.getTarget() instanceof ItemAware) {
                    itemsAware.add((ItemAware)cob.getTarget());
                }
            }
                        
            if (desc == null || desc.trim().equals("")) desc = "Ninguna";
                %>
                <h2><%=utask.getDisplayTitle(lang)%> (<%=type%>)</h2><hr/>
                <p>
                    <b>Descripci&oacute;n</b>: <%=desc%>
                </p>
                <p>
                    <b>Roles asignados</b>:
                </p>
                <ul>
                    <%
                    Iterator<RoleRef> roles = utask.listRoleRefs();
                    while(roles.hasNext()) {
                        RoleRef role = roles.next();
                        %><li><%=role.getRole().getDisplayTitle(lang)%></li><%
                    }
                    %>
                </ul>
                <%
                Iterator<ItemAware> items = itemsAware.iterator();
                if (items.hasNext()) {
                    %>
                    <p>
                        <b>Objetos de datos relacionados</b>:
                    </p>
                    <ul>
                        <%
                        while(items.hasNext()) {
                            ItemAware item = items.next();
                            %><li><%=item.getDisplayTitle(lang)%></li><%
                        }
                        %>
                    </ul>
                    <%
                }
                ArrayList<HTMLContent> contents = new ArrayList<HTMLContent>();
                Iterator<Resource> resources = utask.listResources();
                
                while(resources.hasNext()) {
                    Resource res = resources.next();
                    if (res.getResourceType().getTitle().equals("HTMLContent")) {
                        HTMLContent cont = (HTMLContent)SWBPortal.getResourceMgr().getResource(res);
                        contents.add(cont);
                    }
                }

                Iterator<HTMLContent> it_contents = contents.iterator();
                if (it_contents.hasNext()) {
                    %>
                    <p>
                        <b>Contenidos HTML</b>:
                    </p><%
                    while(it_contents.hasNext()) {
                        HTMLContent content = it_contents.next();
                        String id = content.getSemanticObject().getSemanticClass().getName() + content.getId();

                        String fPath = SWBPortal.getWorkPath()+content.getWorkPath()+"/"+content.getActualVersion().getVersionNumber()+"/"+content.getActualVersion().getVersionFile();
                        String ret=SWBUtils.IO.getFileFromPath(fPath);
                        %>
                        <h3><%=content.getResource().getDisplayTitle(lang)%> - <a href="#<%=id%>" onclick="expande('<%=id%>');">Mostrar</a> <a href="#<%=id%>" onclick="colapsa('<%=id%>')">Ocultar</a></h3>
                        <div id="<%=id%>" style="border:1px solid black;">
                         <%=ret%>
                        </div>
                        <%
                    }
                }
        } else if (gElement instanceof Lane) {
            String type = "Lane";
            Lane lane = (Lane)gElement;
            String desc = lane.getDescription();
            
            if (desc == null || desc.trim().equals("")) desc = "Ninguna";
            %>
                <h2><%=lane.getDisplayTitle(lang)%> (<%=type%>)</h2><hr/>
                <p>
                    <b>Descripci&oacute;n</b>: <%=desc%>
                </p>
            <%
        }
    }
}
%>