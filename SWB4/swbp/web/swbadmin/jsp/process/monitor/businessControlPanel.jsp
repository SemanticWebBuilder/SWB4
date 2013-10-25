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
     al público ("open source"), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
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
    Author     : Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
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
        if (paramName !== null && paramValue !== null) {
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
WebSite site = paramRequest.getWebPage().getWebSite();
String sortType = request.getParameter("sort");
String gFilter = request.getParameter("gF");
String sFilter = request.getParameter("sF");
String displayCols = (String) request.getAttribute("displayCols");
String lang = "es";

String pNum = request.getParameter("p");
int maxPages = (Integer) request.getAttribute("maxPages");
int pageNum = 1;

//WebPage detailWp = paramRequest.getWebPage().getWebSite().getWebPage("Seguimiento");

if (user.getLanguage() != null) {
    lang = user.getLanguage();
}
if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(pNum);
    if (pageNum > maxPages) {
        pageNum = maxPages;
    }
}

if (sortType != null && !sortType.trim().equals("")) {
    sortType = sortType.trim();
} else {
    sortType = "1";
}

if (gFilter == null || gFilter.trim().equals("")) {
    gFilter = "";
}

if (sFilter == null || sFilter.trim().equals("")) {
    sFilter = String.valueOf(ProcessInstance.STATUS_PROCESSING);
}
ArrayList<ProcessInstance> pinstances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
if (!user.isSigned()) {
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
        %>
        <div class="alert alert-block alert-danger fade in">
            <h4><span class="fa fa-ban"></span> <%=paramRequest.getLocaleString("msgNoAccessTitle")%></h4>
            <p><%=paramRequest.getLocaleString("msgNoAccess")%></p>
            <p>
                <a class="btn btn-default" href="/login/<%=site.getId()%>/<%=paramRequest.getWebPage().getId()%>"><%=paramRequest.getLocaleString("btnLogin")%></a>
            </p>
        </div>
        <%
    }
} else {
if (paramRequest.getMode().equals(paramRequest.Mode_VIEW)) {
    SWBResourceURL optsUrl = paramRequest.getRenderUrl();
    %>
    <!--h2><%--paramRequest.getLocaleString("titleMonitor")--%></h2-->
    <ul class="list-unstyled list-inline">
        <li>
            <div class="dropdown">
                <a class="btn btn-default" data-toggle="dropdown">
                    <span class="fa fa-sort-amount-asc"></span> <%=paramRequest.getLocaleString("sortLabel")%> <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header" role="menuitem"><%=paramRequest.getLocaleString("sortDate")%></li>
                    <%
                        optsUrl.setParameter("gF", gFilter);
                        optsUrl.setParameter("sF", sFilter);
                    %>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sort", "1") %>"><span class="fa fa-sort-numeric-asc"></span> <%=sortType.equals("1")?"<strong>":""%><%=paramRequest.getLocaleString("sortLatest")%><%=sortType.equals("1")?"</strong>":""%></a>
                    </li>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sort", "2") %>"><span class="fa fa-sort-numeric-desc"></span> <%=sortType.equals("2")?"<strong>":""%><%=paramRequest.getLocaleString("sortOldest")%><%=sortType.equals("2")?"</strong>":""%></a>
                    </li>
                    <li class="divider" role="menuitem"></li>
                    <li class="dropdown-header" role="menuitem"><%=paramRequest.getLocaleString("sortProcess")%></li>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sort", "3") %>"><span class="fa fa-sort-alpha-asc"></span> <%=sortType.equals("3")?"<strong>":""%><%=paramRequest.getLocaleString("sortNameAsc")%><%=sortType.equals("3")?"</strong>":""%></a>
                    </li>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sort", "4") %>"><span class="fa fa-sort-alpha-desc"></span> <%=sortType.equals("4")?"<strong>":""%><%=paramRequest.getLocaleString("sortNameDes")%><%=sortType.equals("4")?"</strong>":""%></a>
                    </li>
                </ul>
            </div>
        </li>
        <li>
            <div class="dropdown">
                <a class="btn btn-default" data-toggle="dropdown">
                    <span class="fa fa-filter"></span> <%=paramRequest.getLocaleString("filteringLabel")%> <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li class="dropdown-header" role="menuitem"><%=paramRequest.getLocaleString("sortStatus")%></li>
                    <%
                        optsUrl = paramRequest.getRenderUrl();
                        optsUrl.setParameter("sort", sortType);
                        optsUrl.setParameter("gF", gFilter);
                    %>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sF", "-1")%>"><span class="fa fa-reorder"></span> <%=sFilter.equals("-1")?"<strong>":""%><%=paramRequest.getLocaleString("allStatus")%><%=sFilter.equals("-1")?"</strong>":""%></a>
                    </li>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sF", String.valueOf(FlowNodeInstance.STATUS_PROCESSING))%>"><span class="fa fa-flag-checkered"></span> <%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_PROCESSING))?"<strong>":""%><%=paramRequest.getLocaleString("lblProcessing")%><%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_PROCESSING))?"</strong>":""%></a>
                    </li>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sF", String.valueOf(FlowNodeInstance.STATUS_CLOSED))%>"><span class="fa fa-flag"></span> <%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_CLOSED))?"<strong>":""%><%=paramRequest.getLocaleString("lblClosed")%><%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_CLOSED))?"</strong>":""%></a>
                    </li>
                    <li role="menuitem">
                        <a href="<%=optsUrl.setParameter("sF", String.valueOf(FlowNodeInstance.STATUS_ABORTED))%>"><span class="fa fa-flag-o"></span> <%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_ABORTED))?"<strong>":""%><%=paramRequest.getLocaleString("lblAborted")%><%=sFilter.equals(String.valueOf(FlowNodeInstance.STATUS_ABORTED))?"</strong>":""%></a>
                    </li>
                </ul>
            </div>
        </li>
        <li>
            <%
            optsUrl = paramRequest.getRenderUrl(); 
            optsUrl.setParameter("sort", sortType);
            optsUrl.setParameter("sF", sFilter);
            %>
            <select class="form-control" onchange="loadPageUrl('<%=optsUrl.toString()%>', 'gF', this.options[this.selectedIndex].value);">
                <option value="" <%=gFilter.equals("")?"selected":""%>><%=paramRequest.getLocaleString("allGroups")%></option>
                <%
                Iterator<ProcessGroup> groups = ProcessGroup.ClassMgr.listProcessGroups(site);
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
    </ul>
    <%
    if (pinstances != null && !pinstances.isEmpty()) {
        %>
        <div class="table-responsive">
            <table class="table table-hover swbp-table">
                <thead>
                    <tr>
                        <%
                        if (displayCols.contains("idCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColID")%></th><%
                        }
                        if (displayCols.contains("priorityCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColPriority")%></th><%
                        }
                        if (displayCols.contains("nameCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColProcessName")%></th><%
                        }
                        if (displayCols.contains("sdateCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColStarted")%></th><%
                        }
                        if (displayCols.contains("edateCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColEnded")%></th><%
                        }
                        if (displayCols.contains("pendingCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColActivities")%></th><%
                        }
                        if (displayCols.contains("rolesCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColRoles")%></th><%
                        }
                        if (displayCols.contains("actionsCol")) {
                            %><th><%=paramRequest.getLocaleString("lblColActions")%></th><%
                        }
                        %>
                    </tr>
                </thead>
                <tbody>
                    <%
                    Iterator<ProcessInstance> instances = pinstances.iterator();
                    while(instances.hasNext()) {
                        ProcessInstance instance = instances.next();
                        String Id = instance.getId();
                        String pName = instance.getProcessType().getDisplayTitle(lang);
                        String pCreated = SWBUtils.TEXT.getStrDate(instance.getCreated(), lang, "dd/mm/yy - hh:%m");
                        String pClosed = "--";

                        if (instance.getStatus() == ProcessInstance.STATUS_CLOSED) {
                            pClosed = SWBUtils.TEXT.getStrDate(instance.getEnded(), lang, "dd/mm/yy - hh:%m");
                        }
                        %>
                        <tr>
                            <%
                            if (displayCols.contains("idCol")) {
                                %><td><%=Id%></td><%
                            }
                            if (displayCols.contains("priorityCol")) {
                                %><td><%=instance.getPriority()%></td><%
                            }
                            if (displayCols.contains("nameCol")) {
                                %><td><%=pName%></td><%
                            }
                            if (displayCols.contains("sdateCol")) {
                                %><td><%=pCreated%></td><%
                            }
                            if (displayCols.contains("edateCol")) {
                                %><td><%=pClosed%></td><%
                            }
                            if (displayCols.contains("pendingCol")) {
                                %>
                                <td>
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
                            if (displayCols.contains("rolesCol")) {
                                %>
                                <td>
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
                                <td>
                                    <%
                                    String acts = getStatusInstances(instance, ProcessInstance.STATUS_PROCESSING);
                                    if (acts != null && !acts.trim().equals("")) {
                                        acts = "&currentActivities=" + URLEncoder.encode(acts);
                                    }

                                    if (statusWp != null) {
                                    %>
                                        <a href="<%=statusWp.getUrl()%>?suri=<%=instance.getProcessInstance().getProcessType().getEncodedURI()%>" class="btn btn-default" title="<%=paramRequest.getLocaleString("actMap")%>"><span class="fa fa-cogs"></span></a>
                                    <%
                                    }
                                    if (detailWp != null) {
                                    %>
                                        <a href="<%=detailWp.getUrl()%>?pid=<%=instance.getProcessType().getId()%>" class="btn btn-default" title="<%=paramRequest.getLocaleString("actDetail")%>"><span class="fa fa-bar-chart-o"></span></a>
                                    <%
                                    }
                                    Role processAdmRole = instance.getProcessType().getAdministrationRole();
                                    if (processAdmRole != null && user.hasRole(processAdmRole)) {
                                        SWBResourceURL delUrl = paramRequest.getActionUrl().setAction(paramRequest.Action_REMOVE);
                                        delUrl.setParameter("pid", instance.getId());
                                        %>
                                        <a class="acc-eliminar" href="<%=delUrl%>" onclick ="if (!confirm('¿Seguro que desea eliminar la instancia del proceso con ID <%=instance.getId()%>?')) return false;">Eliminar</a>
                                        <%
                                    }

                                    SWBResourceURL docsUrl = paramRequest.getRenderUrl().setMode("showFiles");
                                    docsUrl.setParameter("pid", instance.getId());
                                    docsUrl.setParameter("gF", request.getParameter("gF"));
                                    docsUrl.setParameter("sF", request.getParameter("sF"));
                                    docsUrl.setParameter("sort", request.getParameter("sort"));
                                    %>
                                    <a href="<%=docsUrl%>" class="btn btn-default" title="<%=paramRequest.getLocaleString("actDocs")%>"><span class="fa fa-folder-open-o"></span></a>
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
        <div class="swbp-pagination">
            <span class="swbp-pagination-info pull-left"><%=paramRequest.getLocaleString("pagPage")%> <%=pageNum%> <%=paramRequest.getLocaleString("pagDelim")%> <%=maxPages%></span>
            <%if (maxPages > 1) {%>
                <div class="swbp-pagination-nav pull-right">
                    <ul class="pagination pagination-sm">
                      <%
                        int pagSlice = 5;
                        int sliceIdx = 1;
                        int start = 1;
                        int end = pagSlice * sliceIdx;

                        if (pageNum > end) {
                            do {
                                sliceIdx++;
                                end = pagSlice * sliceIdx;
                            } while(pageNum > end);
                        }
                        end = pagSlice * sliceIdx;

                        if (end > maxPages) {
                            end = maxPages;
                        }

                        start = (end-pagSlice)+1;
                        if (start < 1) {
                            start = 1;
                        }

                        SWBResourceURL nav = paramRequest.getRenderUrl();
                        nav.setParameter("sF", sFilter);
                        nav.setParameter("sort", sortType);

                        if (sliceIdx != 1) {
                            nav.setParameter("p", String.valueOf(pageNum-1));
                            %><li><a href="<%=nav%>">&laquo;</a></li><%
                        }

                        for(int k = start; k <= end; k++) {
                            nav.setParameter("p", String.valueOf(k));
                            %>
                            <li <%=(k==pageNum?"class=\"active\"":"")%>><a href="<%=nav%>"><%=k%></a></li>
                            <%
                        }

                        if (end < maxPages) {
                            nav.setParameter("p", String.valueOf(pageNum+1));
                            %><li><a href="<%=nav%>">&raquo;</a></li><%
                        }
                    }%>
                  </ul>
              </div>
        </div>
    <%
    } else {
        %>
        <div class="alert alert-warning">
            <p><%=paramRequest.getLocaleString("msgNoInfo")%></p
        </div>
        <%
    }
}
}
%>