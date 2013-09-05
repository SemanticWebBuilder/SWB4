<%@page import="org.semanticwb.process.model.AnnotationArtifact"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.model.DataStore"%>
<%@page import="org.semanticwb.process.model.Pool"%>
<%@page import="org.semanticwb.process.model.DataObject"%>
<%@page import="org.semanticwb.process.model.ServiceTask"%>
<%@page import="org.semanticwb.process.model.MessageEndEvent"%>
<%@page import="org.semanticwb.process.model.MessageIntermediateThrowEvent"%>
<%@page import="org.semanticwb.process.model.MessageIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.RuleIntermediateCatchEvent"%>
<%@page import="org.semanticwb.process.model.RuleStartEvent"%>
<%@page import="org.semanticwb.process.model.MessageStartEvent"%>
<%@page import="org.semanticwb.process.model.StartEventNode"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.semanticwb.portal.SWBResourceMgr"%>
<%@page import="org.semanticwb.process.model.ConnectionObject"%>
<%@page import="org.semanticwb.process.model.ActionCodeable"%>
<%@page import="org.semanticwb.process.model.ItemAware"%>
<%@page import="org.semanticwb.process.model.ManualTask"%>
<%@page import="org.semanticwb.process.model.ScriptTask"%>
<%@page import="org.semanticwb.process.model.SignalEndEvent"%>
<%@page import="org.semanticwb.process.model.EndEventNode"%>
<%@page import="org.semanticwb.process.model.EndEvent"%>
<%@page import="org.semanticwb.process.model.StartEvent"%>
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
<%@page import="org.semanticwb.process.model.StartEvent"%>
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
String pFilter = request.getParameter("pFilter");
if (pFilter == null || pFilter.trim().equals("")) pFilter="_all_";

if (user != null && user.getLanguage() != null) {
    lang = user.getLanguage();
}
String gFilter = request.getParameter("gFilter");
if (gFilter == null || gFilter.trim().equals("")) {
    gFilter = "_all_";
}
SWBResourceURL renderUrl = paramRequest.getRenderUrl();
%>
<div class="bandeja-combo">
    <ul>
        <!--li>
                Grupo:
                <select id="pGroupId" onchange="window.location='<%=renderUrl%>?pFilter=' + pFilter + '&gFilter='+document.getElementById('pGroupId').value;">
                    <option value="_all_" <%=gFilter.equals("_all_")?"selected":""%>>Ninguno</option>
                    <%
                    Iterator<ProcessGroup> groups = ProcessGroup.ClassMgr.listProcessGroups(paramRequest.getWebPage().getWebSite());
                    groups = SWBComparator.sortByDisplayName(groups, lang);
                    while (groups.hasNext()) {
                        ProcessGroup group = groups.next();
                        String selected = "";
                        if (gFilter.equals(group.getId())) selected = "selected";
                        %>
                        <option value="<%=group.getId()%>" <%=selected%>><%=group.getDisplayTitle(lang)%></option>
                        <%
                    }
                    %>
                </select>
            </li-->
        <li>Proceso:
            <select id="processId">
                <option value="_all_" <%=pFilter.equals("_all_")?"selected":""%>>Ninguno</option>
                <%
                Iterator<Process> processes = null;
                if (gFilter.equals("_all_")) {
                    processes = SWBComparator.sortByDisplayName(Process.ClassMgr.listProcesses(site), lang);
                } else {
                    ProcessGroup pg = ProcessGroup.ClassMgr.getProcessGroup(gFilter, paramRequest.getWebPage().getWebSite());
                    processes = SWBComparator.sortByDisplayName(pg.listProcesses(), lang);
                }
                while(processes != null && processes.hasNext()) {
                    Process process = processes.next();
                    %>
                    <option value="<%=process.getId()%>" <%=pFilter.equals(process.getId())?"selected":""%>><%=process.getDisplayTitle(lang)%></option>
                    <%
                }
                %>
            </select>
        </li>
        <li>
            <input type="button" onclick="window.location='<%=renderUrl%>?pFilter='+document.getElementById('processId').value;" value="Generar reporte"/>
        </li>
    </ul>
</div>

<%
if (!pFilter.equals("_all_")) {
    Process process = Process.ClassMgr.getProcess(pFilter, site);
    %>
    <h1>Configuraci&oacute;n de elementos del proceso "<%=process.getDisplayTitle(lang)%>"</h1><hr/><br>
    <%
    
    Iterator<GraphicalElement> gElements = SWBComparator.sortByDisplayName(process.listContaineds(),lang);
    while (gElements.hasNext()) {
        GraphicalElement gElement = gElements.next();
        String actCode = "";
        String title = "";
        String description = "";
        String type = "";
        String scriptCode = "";
        
        if (gElement instanceof DataObject || gElement instanceof Pool || gElement instanceof DataStore || gElement instanceof AnnotationArtifact) continue;
        
        //Obtener etiquetas para eventos iniciales
        if (gElement instanceof SignalStartEvent) type="Evento de inicio por se&ntilde;al";
        if (gElement instanceof StartEvent) type="Evento de inicio normal";
        if (gElement instanceof TimerStartEvent) type="Evento de inicio temporizado";
        if (gElement instanceof MessageStartEvent) type="Evento de inicio por mensaje";
        if (gElement instanceof RuleStartEvent) type="Evento de inicio por regla de negocio";
        
        //Obtener etiquetas para eventos intermedios receptores
        if (gElement instanceof SignalIntermediateCatchEvent) type="Evento intermedio receptor de se&ntilde;al";
        if (gElement instanceof RuleIntermediateCatchEvent) type="Evento intermedio de regla de negocio";
        if (gElement instanceof MessageIntermediateCatchEvent) type="Evento intermedio receptor de mensaje";
        if (gElement instanceof TimerIntermediateCatchEvent) type="Evento intermedio temporizado";
        
        //Obtener etiquetas para eventos intermedios disparadores
        if (gElement instanceof SignalIntermediateThrowEvent) type="Evento intermedio disparador de se&ntilde;al";
        if (gElement instanceof MessageIntermediateThrowEvent) type="Evento intermedio disparador de mensaje";
        
        //Obtener etiquetas para eventos finales
        if (gElement instanceof SignalEndEvent) type="Evento de fin con se&ntilde;al";
        if (gElement instanceof EndEvent) type="Evento de fin normal";
        if (gElement instanceof MessageEndEvent) type="Evento de fin con mensaje";
        
        //Tareas de script y usuario
        if (gElement instanceof ScriptTask) type = "Tarea de Script";
        if (gElement instanceof UserTask) type = "Tarea de Usuario";
        if (gElement instanceof ServiceTask) type = "Tarea de Servicio";
        
        //Lanes
        if (gElement instanceof Lane) type = "Lane";
        
        //Objetos de datos
        if (gElement instanceof DataObject) type = "Producto";
        
        if (gElement instanceof Descriptiveable) {
            title = gElement.getDisplayTitle(lang);
            description = gElement.getDescription();
            if (description == null || description.trim().equals("")) description = "Ninguna";
            %>
            <h2><%=title%> (<%=type%>)</h2>
            <p>
                <b>Descripci&oacute;n:</b> <%=description%>
            </p>
            <%
        }
        
        if (gElement instanceof ActionCodeable) {
            ActionCodeable acc = (ActionCodeable) gElement;
            actCode = acc.getActionCode();
            %>
            <p>
                <b>C&oacute;digo de la acci&oacute;n:</b> <%=actCode%>
            </p>
            <%
        }
        
        if (gElement instanceof ScriptTask) {
            ScriptTask stask = (ScriptTask)gElement;
            scriptCode = stask.getScriptCode();
            if (scriptCode == null || scriptCode.trim().equals("")) scriptCode = "Ninguno";
            
            %>
            <p>
                <b>C&oacute;digo del Script:</b> <%=scriptCode%>
            </p>
            <%
        }
        
        if (gElement instanceof Activity) {
            Activity activity = (Activity) gElement;
            ArrayList<ItemAware> itemsAware = new ArrayList<ItemAware>();
            
            itemsAware.addAll(activity.listHerarquicalRelatedItemAware());
            Iterator<ConnectionObject> cobs = activity.listInputConnectionObjects();
            while(cobs.hasNext()) {
                ConnectionObject cob = cobs.next();
                if (cob.getSource() instanceof ItemAware) {
                    itemsAware.add((ItemAware)cob.getSource());
                }
            }

            cobs = activity.listOutputConnectionObjects();
            while(cobs.hasNext()) {
                ConnectionObject cob = cobs.next();
                if (cob.getTarget() instanceof ItemAware) {
                    itemsAware.add((ItemAware)cob.getTarget());
                }
            }

            Iterator<ItemAware> items = itemsAware.iterator();
            if (items.hasNext()) {
            %>
                <p>
                    <b>Productos relacionados:</b>
                </p>
                <ul>
                    <%
                    while(items.hasNext()) {
                        ItemAware item = items.next();
                        %>
                        <li><%=item.getDisplayTitle(lang)%></li>
                        <%
                    }
                    %>
                </ul>
            <%
            }
            
            if (gElement instanceof UserTask) {
                UserTask utask = (UserTask)gElement;
                Iterator<RoleRef> roles = utask.listRoleRefs();
                
                if (roles.hasNext()) {
                    %>
                    <p>
                        <b>Roles asignados:</b>
                        <ul>
                            <%
                            while(roles.hasNext()) {
                                RoleRef role = roles.next();
                                %>
                                <li><%=role.getRole().getDisplayTitle(lang)%></li>
                                <%
                            }
                            %>
                        </ul>
                    </p>
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
                        <b>Contenidos HTML:</b>
                    </p>
                    <%
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
            }
        }
        %>
        <hr/><br/><br/>
        <%
    }
}
%>