<%--
     SemanticWebBuilder Process (SWBP) es una plataforma para la gestión de procesos de negocio mediante el uso de 
     tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
     de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.

     Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
     alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
     un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
     y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
     Información y Documentación para la Industria INFOTEC.

     INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
     al público (?open source?), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
     lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
     modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
     condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 

     INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
     explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
     riesgos que puedan derivar de la misma. 

     Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
     siguiente dirección electrónica: 
     http://www.semanticwebbuilder.org.mx

    Document   : businessControlPanel
    Created on : 5/07/2011, 04:14:49 PM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>
<%@page import="org.semanticwb.process.model.IntermediateCatchEvent"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.RoleRef"%>
<%@page import="org.semanticwb.model.Role"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.SubProcessInstance"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.Instance"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.ProcessGroup"%>
<%@page import="org.semanticwb.process.model.SWBProcessMgr"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.Math"%>
<%@page import="java.util.ArrayList"%>

<%!
    public String getStatusInstances(ProcessInstance pi, int status) {
        String ret = "";
        if (pi != null) {
            Iterator<FlowNodeInstance> actit = SWBComparator.sortByCreated(pi.listFlowNodeInstances());
            while (actit.hasNext()) {
                FlowNodeInstance obj = actit.next();
                ret += _getStatusInstances(obj, status);
            }
        }
        return (ret);
    }
    
    public String _getStatusInstances(FlowNodeInstance fi, int status) {
        String ret = "";
        if (fi instanceof SubProcessInstance) {
            SubProcessInstance pi = (SubProcessInstance) fi;
            Iterator<FlowNodeInstance> acit = pi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    ret += _getStatusInstances(actinst, status);
                }
            }
        } else if (fi.getFlowNodeType() instanceof Activity && fi.getStatus() == status) {
            ret += fi.getFlowNodeType().getURI() + "|";
        }
        return ret;
    }

    /*public String _getStatusInstances(FlowNodeInstance fi, int status) {
        String ret = "";
        if (fi instanceof SubProcessInstance) {
            SubProcessInstance pi = (SubProcessInstance) fi;
            Iterator<FlowNodeInstance> acit = pi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    ret += _getStatusInstances(actinst, status);
                }
            }
        } else if (fi.getFlowNodeType() instanceof Activity && fi.getStatus() == status) {
            Iterator<FlowNodeInstance> fnii = fi.getFlowNodeType().listFlowObjectInstances();
            int c = 0;
            while (fnii.hasNext()) {
                c++;
                fnii.next();
            }
            ret += fi.getFlowNodeType().getURI() + "(" + c + ")|";
        }
        return ret;
    }*/
%>

<script type="text/javascript">
    function loadPageUrl(url, paramName, paramValue) {
        var dest = url;
        if (paramName != null && paramValue != null) {
            dest+="&"+paramName+"="+paramValue;
        }
        window.location = dest;
    }
</script>

<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute("paramRequest");
WebPage statusWp = (WebPage)request.getAttribute("statusWp");
WebPage detailWp = (WebPage)request.getAttribute("trackWp");
User user = paramRequest.getUser();
WebSite ws = paramRequest.getWebPage().getWebSite();
String baseimg = SWBPortal.getWebWorkPath() + "/models/" + ws.getId() + "/css/images/";
String sortType = request.getParameter("sort");
String gFilter = request.getParameter("gFilter");
String sFilter = request.getParameter("sFilter");
String displayCols = (String) request.getAttribute("displayCols");
String lang = "es";
int pageNum = 1;
int maxPages = (Integer) request.getAttribute("maxPages");

//WebPage detailWp = paramRequest.getWebPage().getWebSite().getWebPage("Seguimiento");

if (user.getLanguage() != null) {
    lang = user.getLanguage();
}
if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
    pageNum = Integer.valueOf(request.getParameter("page"));
}
if (sortType != null && !sortType.trim().equals("")) {
    sortType = sortType.trim();
} else {
    sortType = "date";
}

if (gFilter == null || gFilter.trim().equals("")) {
    gFilter = "";
}

if (sFilter == null || sFilter.trim().equals("")) {
    sFilter = String.valueOf(ProcessInstance.STATUS_PROCESSING);
}
ArrayList<ProcessInstance> pinstances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
SWBResourceURL configUrl = paramRequest.getRenderUrl().setMode("config");
if (paramRequest.getMode().equals(paramRequest.Mode_VIEW)) {
    SWBResourceURL optsUrl = paramRequest.getRenderUrl();
    optsUrl.setParameter("gFilter", gFilter);
    optsUrl.setParameter("sFilter", sFilter);
    %>
    <h2>Monitor de procesos</h2>
    <div class="bandeja-combo">
            <ul>
                <li>
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sort', this.options[this.selectedIndex].value)">
                        <option value="date" <%=sortType.equals("date")?"selected":""%>>Por fecha</option>
                        <option value="name" <%=sortType.equals("name")?"selected":""%>>Por proceso</option>
                        <option value="priority" <%=sortType.equals("priority")?"selected":""%>>Por prioridad</option>
                    <!--option value="priority" <%=sortType.equals("priority")?"selected":""%>>Por prioridad</option-->
                    </select>
                </li>
                <li>
                    <%
                    optsUrl = paramRequest.getRenderUrl();
                    optsUrl.setParameter("sort", sortType);
                    optsUrl.setParameter("sFilter", sFilter);
                    %>
                    <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'gFilter', this.options[this.selectedIndex].value)">
                        <option value="" <%=gFilter.equals("")?"selected":""%>>Todos los grupos</option>
                        <%
                        Iterator<ProcessGroup> groups = ProcessGroup.ClassMgr.listProcessGroups(ws);
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
                </li>
            <li>
                <%
                optsUrl = paramRequest.getRenderUrl();
                optsUrl.setParameter("sort", sortType);
                optsUrl.setParameter("gFilter", gFilter);
                %>                
                <select onchange="loadPageUrl('<%=optsUrl.toString()%>', 'sFilter', this.options[this.selectedIndex].value)">
                    <option value="-1" <%=sFilter.equals("-1")?"selected":""%>>Todos los procesos</option>
                    <option value="<%=ProcessInstance.STATUS_PROCESSING%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_PROCESSING))?"selected":""%>>Pendientes</option>
                    <option value="<%=ProcessInstance.STATUS_CLOSED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_CLOSED))?"selected":""%>>Terminados</option>
                    <option value="<%=ProcessInstance.STATUS_ABORTED%>" <%=sFilter.equals(String.valueOf(ProcessInstance.STATUS_ABORTED))?"selected":""%>>Abortados</option>
                </select>
            </li>
            <!--li>
                <a href="<%=configUrl%>">Configurar despliegue</a>
            </li-->
        </ul>
    </div>
    <%
    if (pinstances != null && !pinstances.isEmpty()) {
        %>
        <div class="bandeja-combo">
        <table class="tabla-bandeja">
            <thead>
                <tr>
                    <%
                    if (displayCols.contains("idCol")) {
                        %><th class="tban-id">ID</th><%
                    }
                    if (displayCols.contains("priorityCol")) {
                        %><th class="tban-id">Prioridad</th><%
                    }
                    if (displayCols.contains("nameCol")) {
                        %><th class="tban-proces">Proceso</th><%
                    }
                    if (displayCols.contains("sdateCol")) {
                        %><th class="tban-inicia">Iniciado</th><%
                    }
                    if (displayCols.contains("edateCol")) {
                        %><th class="tban-cerrada">Cerrado</th><%
                    }
                    if (displayCols.contains("pendingCol")) {
                        %><th class="tban-tarea">Actividades pendientes</th><%
                    }
                    if (displayCols.contains("rolesCol")) {
                        %><th class="tban-tarea">Responsables</th><%
                    }
                    if (displayCols.contains("actionsCol")) {
                        %><th class="tban-accion">Acciones</th><%
                    }
                    %>
                </tr>
            </thead>
            <tbody>
                <%
                Iterator<ProcessInstance> instances = pinstances.iterator();
                while(instances.hasNext()) {
                    ProcessInstance instance = instances.next();
                    String status = "<img src=\""+baseimg;
                    String Id = instance.getId();
                    String pName = instance.getProcessType().getDisplayTitle(lang);
                    String pCreated = SWBUtils.TEXT.getStrDate(instance.getCreated(), lang, "dd/mm/yy - hh:%m");
                    String pClosed = "--";
                    
                    if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) status += "icon_pending.png\">";
                    if (instance.getStatus() == ProcessInstance.STATUS_CLOSED) {
                        status += "icon_closed.png\">";
                        pClosed = SWBUtils.TEXT.getStrDate(instance.getEnded(), lang, "dd/mm/yy - hh:%m");
                    }
                    if (instance.getStatus() == ProcessInstance.STATUS_ABORTED) status += "icon_aborted.png\">";
                    %>
                    <tr>
                        <%
                        if (displayCols.contains("idCol")) {
                            %><td class="tban-id"><%=Id%></td><%
                        }
                        if (displayCols.contains("priorityCol")) {
                            %><td class="tban-id"><%=instance.getPriority()%></td><%
                        }
                        if (displayCols.contains("nameCol")) {
                            %><td class="tban-proces"><%=pName%></td><%
                        }
                        if (displayCols.contains("sdateCol")) {
                            %><td class="tban-inicia"><%=pCreated%></td><%
                        }
                        if (displayCols.contains("edateCol")) {
                            %><td class="tban-cerrada"><%=pClosed%></td><%
                        }
                        if (displayCols.contains("pendingCol")) {
                            %>
                            <td class="tban-tarea">
                                <%
                                Iterator<FlowNodeInstance> tasks = instance.listFlowNodeInstances();
                                if (tasks.hasNext() && instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                    boolean hasTasks = false;
                                    %>
                                    <ul>
                                        <%
                                        while (tasks.hasNext()) {
                                            FlowNodeInstance task = tasks.next();
                                            if (task.getFlowNodeType() instanceof UserTask) {
                                                if (task.getStatus() == task.STATUS_PROCESSING) {
                                                    hasTasks = true;
                                                    %>
                                                        <b><%=task.getFlowNodeType().getDisplayTitle(lang)%></b><br/>
                                                    <%
                                                }
                                            }
                                        }
                                        if (!hasTasks) {
                                            %>Proceso en espera<%
                                        }
                                        %>
                                    </ul>
                                    <%
                                } else {
                                    %>--<%
                                }
                                %>
                            </td>
                            <%
                        }
                        if (displayCols.contains("rolesCol")) {
                            %>
                            <td class="tban-tarea">
                                <%
                                Iterator<FlowNodeInstance> tasks = instance.listFlowNodeInstances();
                                if (tasks.hasNext() && instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                                    boolean hasTasks = false;
                                    %>
                                    <ul>
                                        <%
                                        while (tasks.hasNext()) {
                                            FlowNodeInstance task = tasks.next();
                                            if (task.getFlowNodeType() instanceof UserTask) {
                                                if (task.getStatus() == task.STATUS_PROCESSING) {
                                                    hasTasks = true;
                                                    UserTask utask = (UserTask)task.getFlowNodeType();
                                                    Iterator<RoleRef> roles = utask.listRoleRefs();
                                                    while(roles.hasNext()) {
                                                        RoleRef roleRef = roles.next();
                                                        String role = roleRef.getRole().getDisplayTitle(lang);
                                                        %>
                                                        <li>
                                                            <b><%=role%></b>
                                                        </li>
                                                        <%
                                                    }
                                                }
                                            }
                                        }
                                        if (!hasTasks) {
                                            %>--<%
                                        }
                                        %>
                                    </ul>
                                    <%
                                } else {
                                    %>--<%
                                }
                                %>
                            </td>
                            <%
                        }
                        if (displayCols.contains("actionsCol")) {
                            %>
                            <td class="tban-accion">
                                <%
                                String acts = getStatusInstances(instance, ProcessInstance.STATUS_PROCESSING);
                                if (acts != null && !acts.trim().equals("")) {
                                    acts = "&currentActivities=" + URLEncoder.encode(acts);
                                }

                                if (instance.getStatus() == ProcessInstance.STATUS_CLOSED) {
                                    WebPage pwp = instance.getProcessType().getProcessWebPage();
                                    if (pwp != null) {
                                    %><a class="acc-desempeno" href="<%=pwp.getUrl()%>">Desempe&ntilde;o</a><%
                                    }
                                }
                                
                                if (statusWp != null) {
                                %>
                                    <a class="acc-mapa" href="<%=statusWp.getUrl()%>?suri=<%=instance.getProcessType().getEncodedURI()%>&mode=view<%=acts%>">Ver mapa</a>
                                <%
                                }
                                if (detailWp != null) {
                                %>
                                    <a class="acc-detalle" href="<%=detailWp.getUrl()%>?pid=<%=instance.getProcessType().getId()%>">Seguimiento</a>
                                <%
                                }
                                Role processAdmRole = instance.getProcessType().getAdministrationRole();
                                if (processAdmRole != null) {
                                    if (user.hasRole(processAdmRole)) {
                                        SWBResourceURL delUrl = paramRequest.getActionUrl().setAction(paramRequest.Action_REMOVE);
                                        delUrl.setParameter("pid", instance.getId());
                                        %>
                                        <a class="acc-eliminar" href="<%=delUrl%>" onclick ="if (!confirm('¿Seguro que desea eliminar la instancia del proceso con ID <%=instance.getId()%>?')) return false;">Eliminar</a>
                                        <%
                                    }
                                }
                                SWBResourceURL docsUrl = paramRequest.getRenderUrl().setMode("showFiles");
                                docsUrl.setParameter("pid", instance.getId());
                                docsUrl.setParameter("gFilter", request.getParameter("gFilter"));
                                docsUrl.setParameter("sFilter", request.getParameter("sFilter"));
                                docsUrl.setParameter("sort", request.getParameter("sort"));
                                %>
                                <a class="acc-docs" href="<%=docsUrl%>">Documentos</a>
                            </td>
                            <%
                        }
                        %>
                    </tr>
                    <%
                }
                %>
            </tbody>
        </table>
        </div>
        <div class="paginado">
        <table class="tabla-bandeja">
            <tbody>
                <tr>
                    <td style="width:80%;">
                        P&aacute;gina: <%=pageNum%> de <%=maxPages%>
                    </td>
                    <td style="text-align:right;">
                        <%
                        if (maxPages > 1) {
                            SWBResourceURL first = paramRequest.getRenderUrl();
                            first.setParameter("sFilter", sFilter);
                            first.setParameter("sort", sortType);
                            first.setParameter("page", "1");
                            %><a href="<%=first%>">Primer p&aacute;gina</a><%
                        }
                        if (pageNum-1 > 0) {
                            SWBResourceURL back = paramRequest.getRenderUrl();
                            back.setParameter("sFilter", sFilter);
                            back.setParameter("sort", sortType);
                            back.setParameter("page", String.valueOf(pageNum-1));
                            %><a href="<%=back%>">Anterior</a><%
                        }
                        if (pageNum+1 <= maxPages) {
                            SWBResourceURL forward = paramRequest.getRenderUrl();
                            forward.setParameter("sFilter", sFilter);
                            forward.setParameter("sort", sortType);
                            forward.setParameter("page", String.valueOf(pageNum+1));
                            %><a href="<%=forward%>">Siguiente</a><%
                        }
                        if (maxPages > 1 && pageNum < maxPages) {
                            SWBResourceURL last = paramRequest.getRenderUrl();
                            last.setParameter("sFilter", sFilter);
                            last.setParameter("sort", sortType);
                            last.setParameter("page", String.valueOf(maxPages));
                            %><a href="<%=last%>">&Uacute;ltima p&aacute;gina</a><%
                        }
                        %>
                    </td>
                </tr>
            </tbody>
        </table>
        </div>
        <%
    } else {
        %><p>No hay procesos actualmente en ejecuci&oacute;n</p><%
    }
}
%>