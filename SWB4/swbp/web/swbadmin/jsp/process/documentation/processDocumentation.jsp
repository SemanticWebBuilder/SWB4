<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.process.model.ParallelStartEventGateway"%>
<%@page import="org.semanticwb.process.model.ComplexGateway"%>
<%@page import="org.semanticwb.process.model.ParallelStartEvent"%>
<%@page import="org.semanticwb.process.model.ParallelGateway"%>
<%@page import="org.semanticwb.process.model.ExclusiveStartEventGateway"%>
<%@page import="org.semanticwb.process.model.EventBasedGateway"%>
<%@page import="org.semanticwb.process.model.InclusiveGateway"%>
<%@page import="org.semanticwb.process.model.ExclusiveGateway"%>
<%@page import="org.semanticwb.process.model.BusinessRuleTask"%>
<%@page import="org.semanticwb.process.model.ReceiveTask"%>
<%@page import="org.semanticwb.process.model.SendTask"%>
<%@page import="org.semanticwb.process.model.ServiceTask"%>
<%@page import="org.semanticwb.process.model.ScriptTask"%>
<%@page import="org.semanticwb.process.model.ManualTask"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.DataStore"%>
<%@page import="java.io.DataOutput"%>
<%@page import="java.io.DataInput"%>
<%@page import="org.semanticwb.process.model.ItemAware"%>
<%@page import="org.semanticwb.process.model.ItemAwareReference"%>
<%@page import="org.semanticwb.process.model.DataObject"%>
<%@page import="org.semanticwb.process.model.ProcessRuleRef"%>
<%@page import="org.semanticwb.process.model.ConditionalFlow"%>
<%@page import="org.semanticwb.process.model.ConnectionObject"%>
<%@page import="org.semanticwb.model.RoleRefable"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.process.model.GraphicalElement"%>
<%@page import="org.semanticwb.process.model.FlowNode"%>
<%@page import="org.semanticwb.process.model.Gateway"%>
<%@page import="org.semanticwb.process.model.ProcessRule"%>
<%@page import="org.semanticwb.model.Rule"%>
<%@page import="org.semanticwb.model.RoleRef"%>
<%@page import="org.semanticwb.process.model.Task"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="org.semanticwb.process.model.SubProcess"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.process.model.WrapperProcessWebPage"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%@page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@page import="org.semanticwb.process.model.Process" %>

<%
    
    
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
String pid = request.getParameter("pid");
WebPage wp = paramRequest.getWebPage();
Process process = null;
if (wp instanceof WrapperProcessWebPage) {
    process = ((WrapperProcessWebPage)wp).getProcess();
} else if (pid != null && !pid.trim().equals("")) {
    process = Process.ClassMgr.getProcess(pid, wp.getWebSite());
}

if (process != null) {
    HashMap<String, SubProcess> subProcesses = new HashMap<String, SubProcess>();
    HashMap<String, Role> roles = new HashMap<String, Role>();
    HashMap<String, Task> tasks = new HashMap<String, Task>();
    HashMap<String, ProcessRule> rules = new HashMap<String, ProcessRule>();
    HashMap<String, Gateway> gateways = new HashMap<String, Gateway>();
    HashMap<String, ItemAware> objects = new HashMap<String, ItemAware>();
    
    String puri = process.getEncodedURI();
    String pName = process.getTitle();
    String desc = process.getDescription();
    String mapURL = paramRequest.getWebPage().getWebSite().getWebPage("StatusMap").getUrl() + "?suri=" + puri + "&mode=view";
    
    Iterator<GraphicalElement> nodes = process.listAllContaineds();
    while(nodes.hasNext()) {
        GraphicalElement node = nodes.next();
        
        if (node instanceof RoleRefable) {
            RoleRefable rref = (RoleRefable) node;
            Iterator<RoleRef> refs = rref.listRoleRefs();
            while(refs.hasNext()) {
                RoleRef ref = refs.next();
                if (ref.getRole() != null) {
                    roles.put(ref.getRole().getSemanticObject().getSemanticClass().getClassName() + ref.getRole().getId(), ref.getRole());
                }
            }
        }
        
        if (node instanceof ItemAware) {
            ItemAware iaware = (ItemAware) node;
            if (!iaware.listInputConnectionObjects().hasNext() && !iaware.listOutputConnectionObjects().hasNext()) {
                objects.put(iaware.getSemanticObject().getSemanticClass().getName() + iaware.getId(), iaware);
            }
        }
        
        if (node instanceof SubProcess) {
            SubProcess subp = (SubProcess) node;
            subProcesses.put(subp.getSemanticObject().getSemanticClass().getName() + subp.getId(), subp);
        }
        
        if (node instanceof Task) {
            Task tsk = (Task) node;
            tasks.put(tsk.getSemanticObject().getSemanticClass().getName() + tsk.getId(), tsk);
        }
        
        if (node instanceof Gateway) {
            Gateway gtw = (Gateway) node;
            gateways.put(gtw.getSemanticObject().getSemanticClass().getName() + gtw.getId(), gtw);
        }
        
        Iterator<ConnectionObject> conns = node.listOutputConnectionObjects();
        while (conns.hasNext()) {
            ConnectionObject conn = conns.next();
            if (conn instanceof ConditionalFlow) {
                ConditionalFlow cf = (ConditionalFlow) conn;
                Iterator<ProcessRuleRef> crulerefs = cf.listProcessRuleRefs();
                while(crulerefs.hasNext()) {
                    ProcessRuleRef cruleref = crulerefs.next();
                    if (cruleref.getProcessRule() != null) {
                        rules.put(cruleref.getProcessRule().getSemanticObject().getSemanticClass().getName() + cruleref.getProcessRule().getId(), cruleref.getProcessRule());
                    }
                }
            }
        }
    }
    
    %>
    <h1><%=pName%></h1>
    <%
    if (desc != null && !desc.equals("")) {
        %>
        <p>
            <%=desc%>
            <a href="<%=mapURL%>">Ver diagrama</a>
        </p>
        <%
    }
    %><br/><%
    if (!roles.isEmpty()) {
        %>
        <div class="bandeja-combo"><b>Roles del proceso</b></div>
        <div class="bandeja-combo">
            <table class="tabla-bandeja">
                <thead>
                    <th style="text-align:left;">Rol</th>
                    <th style="text-align:left;">Descripci&oacute;n</th>
                </thead>
                <tbody>
                    <%
                    Iterator<String> keys = roles.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Role rol = roles.get(key);
                        String rdesc = rol.getDescription();
                        
                        if (rdesc == null || rdesc.equals("")) rdesc = "Sin descripción.";
                        %>
                        <tr id="<%=key%>">
                            <td>
                                <%=rol.getTitle()%>
                            </td>
                            <td>
                                <%=rdesc%>
                            </td>
                        </tr>
                        <%
                    }
                    %>
                </tbody>
            </table>
        </div><br/>
    <%
    }
    if (!rules.isEmpty()) {
        %>
        <div class="bandeja-combo"><b>Reglas de Negocio del proceso</b></div>
        <div class="bandeja-combo">
            <table class="tabla-bandeja">
                <thead>
                    <th style="text-align:left;">Regla de negocio</th>
                    <th style="text-align:left;">Descripci&oacute;n</th>
                    <th style="text-align:left;">Condici&oacute;n</th>
                </thead>
                <tbody>
                    <%
                    Iterator<String> keys = rules.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        ProcessRule rul = rules.get(key);
                        String rdesc = rul.getDescription();
                        String cond = rul.getRuleCondition();
                        
                        if (rdesc == null || rdesc.equals("")) rdesc = "Sin descripci&oacute;n.";
                        if (cond == null || cond.equals("")) cond = "Sin condici&oacute;n.";
                        %>
                        <tr id="<%=key%>">
                            <td>
                                <%=rul.getTitle()%>
                            </td>
                            <td>
                                <%=rdesc%>
                            </td>
                            <td>
                                <%=cond%>
                            </td>
                        </tr>
                        <%
                    }
                    %>
                </tbody>
            </table>
        </div><br/>
    <%
    }
    if (!objects.isEmpty()) {
        %>
        <div class="bandeja-combo"><b>Objetos de Datos del proceso</b></div>
        <div class="bandeja-combo">
            <table class="tabla-bandeja">
                <thead>
                    <th style="text-align:left;">Objeto de Datos</th>
                    <th style="text-align:left;">Tipo</th>
                    <th style="text-align:left;">Descripci&oacute;n</th>
                    <th style="text-align:left;">Clase asociada</th>
                </thead>
                <tbody>
                    <%
                    Iterator<String> keys = objects.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        ItemAware iaw = objects.get(key);
                        String rdesc = iaw.getDescription();
                        String relClass = "Sin clase relacionada.";
                        String dataType = "No definido.";
                        
                        if (rdesc == null || rdesc.equals("")) rdesc = "Sin descripci&oacute;n.";
                        if (iaw.getItemSemanticClass() != null) {
                            relClass = iaw.getItemSemanticClass().getName();
                        }
                        
                        if (iaw instanceof DataObject) dataType = "Dato.";
                        if (iaw instanceof DataInput) dataType = "Dato de entrada.";
                        if (iaw instanceof DataOutput) dataType = "Dato de salida.";
                        if (iaw instanceof DataStore) dataType = "Almac&eacute;n de datos.";
                        %>
                        <tr id="<%=key%>">
                            <td>
                                <%=iaw.getTitle()%>
                            </td>
                            <td>
                                <%=dataType%>
                            </td>
                            <td>
                                <%=rdesc%>
                            </td>
                            <td>
                                <%=relClass%>
                            </td>
                        </tr>
                        <%
                    }
                    %>
                </tbody>
            </table>
        </div><br/>
    <%
    }
    if (!subProcesses.isEmpty()) {
        %>
        <div class="bandeja-combo"><b>SubProcesos</b></div>
        <div class="bandeja-combo">
            <table class="tabla-bandeja">
                <thead>
                    <th style="text-align:left;">SubProceso</th>
                    <th style="text-align:left;">Descripci&oacute;n</th>
                    <th style="text-align:left;">Actividades asociadas</th>
                </thead>
                <tbody>
                    <%
                    Iterator<String> keys = subProcesses.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        SubProcess subp = subProcesses.get(key);
                        String rdesc = subp.getDescription();
                        
                        String related = "Ninguna.";
                        Iterator<GraphicalElement> snodes = subp.listAllContaineds();
                        if (snodes.hasNext()) {
                            related = "";
                        }

                        boolean hasActivity = false;
                        while (snodes.hasNext()) {
                            GraphicalElement ele = snodes.next();
                            if (ele instanceof Activity) {
                                if (!hasActivity) {
                                    related = "";
                                    hasActivity = true;
                                }
                                String id = ele.getSemanticObject().getSemanticClass().getName() + ele.getId();
                                related += "<a href=\"#" + id + "\">" + ele.getTitle() + "</a>";
                                if (snodes.hasNext()) related += ", ";
                            }
                        }
                        
                        if (rdesc == null || rdesc.equals("")) rdesc = "Sin descripci&oacute;n.";
                        %>
                        <tr id="<%=key%>">
                            <td>
                                <%=subp.getTitle()%>
                            </td>
                            <td>
                                <%=rdesc%>
                            </td>
                            <td>
                                <%=related%>
                            </td>
                        </tr>
                        <%
                    }
                    %>
                </tbody>
            </table>
        </div><br/>
    <%
    }
    if (!tasks.isEmpty()) {
        %>
        <div class="bandeja-combo"><b>Tareas</b></div>
        <div class="bandeja-combo">
            <table class="tabla-bandeja">
                <thead>
                    <th style="text-align:left;">Tarea</th>
                    <th style="text-align:left;">Tipo</th>
                    <th style="text-align:left;">Descripci&oacute;n</th>
                    <th style="text-align:left;">Roles asociados</th>
                </thead>
                <tbody>
                    <%
                    Iterator<String> keys = tasks.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Task task = tasks.get(key);
                        String rdesc = task.getDescription();
                        String tskType = "No definido.";
                        String sroles = "Ninguno.";
                        
                        boolean hasRoles = false;
                        if (task instanceof RoleRefable) {
                            RoleRefable rref = (RoleRefable) task;
                            Iterator<RoleRef> refs = rref.listRoleRefs();
                            while(refs.hasNext()) {
                                RoleRef ref = refs.next();
                                if (ref.getRole() != null) {
                                    if (!hasRoles) {
                                        sroles = "";
                                        hasRoles = true;
                                    }
                                    sroles += ref.getRole().getTitle();
                                    if (refs.hasNext()) sroles += ", ";
                                }
                            }
                        }
                        
                        if (rdesc == null || rdesc.equals("")) rdesc = "Sin descripci&oacute;n.";
                        if (task instanceof UserTask) tskType = "Tarea de Usuario.";
                        if (task instanceof ManualTask) tskType = "Tarea Manual.";
                        if (task instanceof ScriptTask) tskType = "Tarea de Script.";
                        if (task instanceof ServiceTask) tskType = "Tarea de Servicio.";
                        if (task instanceof SendTask) tskType = "Tarea de Env&iacute;o.";
                        if (task instanceof ReceiveTask) tskType = "Tarea de Recepci&oacute;n.";
                        if (task instanceof BusinessRuleTask) tskType = "Tarea de Regla de Negocio.";
                        
                        %>
                        <tr id="<%=key%>">
                            <td>
                                <%=task.getTitle()%>
                            </td>
                            <td>
                                <%=tskType%>
                            </td>
                            <td>
                                <%=rdesc%>
                            </td>
                            <td>
                                <%=sroles%>
                            </td>
                        </tr>
                        <%
                    }
                    %>
                </tbody>
            </table>
        </div><br/>
    <%
    }
    if (!gateways.isEmpty()) {
        %>
        <div class="bandeja-combo"><b>Compuertas</b></div>
        <div class="bandeja-combo">
            <table class="tabla-bandeja">
                <thead>
                    <th style="text-align:left;">Compuerta</th>
                    <th style="text-align:left;">Tipo</th>
                    <th style="text-align:left;">Descripci&oacute;n</th>
                    <th style="text-align:left;">Reglas asociadas</th>
                </thead>
                <tbody>
                    <%
                    Iterator<String> keys = gateways.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Gateway gtw = gateways.get(key);
                        String rdesc = gtw.getDescription();
                        String gtwType = "No definido.";
                        String srules = "Ninguna.";
                        
                        boolean hasRules = false;
                        
                        if (rdesc == null || rdesc.equals("")) rdesc = "Sin descripci&oacute;n.";
                        if (gtw instanceof ExclusiveGateway) gtwType = "Exclusiva (Datos).";
                        if (gtw instanceof InclusiveGateway) gtwType = "Inclusiva (Datos).";
                        if (gtw instanceof EventBasedGateway) gtwType = "Exclusiva (Eventos).";
                        if (gtw instanceof ExclusiveStartEventGateway) gtwType = "Exclusiva de inicio.";
                        if (gtw instanceof ParallelGateway) gtwType = "Paralela.";
                        if (gtw instanceof ParallelStartEventGateway) gtwType = "Paralela de inicio.";
                        if (gtw instanceof ComplexGateway) gtwType = "Compleja.";
                        
                        if (gtw instanceof ExclusiveGateway || gtw instanceof InclusiveGateway) {
                            Iterator<ConnectionObject> conns = gtw.listOutputConnectionObjects();
                            while (conns.hasNext()) {
                                ConnectionObject conn = conns.next();
                                if (conn instanceof ConditionalFlow) {
                                    ConditionalFlow cf = (ConditionalFlow) conn;
                                    
                                    Iterator<ProcessRuleRef> crulerefs = cf.listProcessRuleRefs();
                                    while(crulerefs.hasNext()) {
                                        ProcessRuleRef cruleref = crulerefs.next();
                                        if (!hasRules) {
                                            srules = "";
                                            hasRules = true;
                                        }
                                        if (cruleref.getProcessRule() != null) {
                                            String id = cruleref.getProcessRule().getSemanticObject().getSemanticClass().getName() + cruleref.getProcessRule().getId();
                                            srules += "<a href=\"#" + id + "\">" + cruleref.getProcessRule().getTitle() + "</a>,";
                                        }
                                    }
                                }
                            }
                        }
                        
                        srules = srules.substring(0, srules.length()-1);
                        
                        %>
                        <tr id="<%=key%>">
                            <td>
                                <%=gtw.getTitle()%>
                            </td>
                            <td>
                                <%=gtwType%>
                            </td>
                            <td>
                                <%=rdesc%>
                            </td>
                            <td>
                                <%=srules%>
                            </td>
                        </tr>
                        <%
                    }
                    %>
                </tbody>
            </table>
        </div><br/>
    <%
    }
} else {
    %>No se ha podido generar la documentaci&oacute;n<%
}
%>