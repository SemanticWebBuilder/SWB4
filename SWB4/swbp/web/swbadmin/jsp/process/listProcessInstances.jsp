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
     al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
     lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
     modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
     condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 

     INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
     explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
     riesgos que puedan derivar de la misma. 

     Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
     siguiente dirección electrónica: 
     http://www.semanticwebbuilder.org.mx
--%>

<%@page import="org.semanticwb.process.model.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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

<%!    public void printActivityInstance(WebPage page, FlowNodeInstance ai, JspWriter out) throws IOException {
        String baseimg = SWBPortal.getWebWorkPath() + "/models/" + page.getWebSiteId() + "/css/images/";
        if (!(ai.getFlowNodeType() instanceof Activity)) {
            return;
        }
        
        String stat = "";
        String color = "";
        String actions="";
        String tOwner = "--";

        if (ai.getFlowNodeType() instanceof UserTask) {
            UserTask tsk = (UserTask) ai.getFlowNodeType();
            Iterator<RoleRef> roles = tsk.listRoleRefs();
            if (roles.hasNext()) tOwner = roles.next().getRole().getDisplayTitle("lang");
            //tOwner = tsk.getRole().getDisplayTitle("es");
        }

        if (ai.getStatus() == Instance.STATUS_INIT) {
            stat = "Iniciada";
        }
        if (ai.getStatus() == Instance.STATUS_ABORTED) {
            stat = "Abortada";
        }
        if (ai.getStatus() == Instance.STATUS_CLOSED) {
            stat = "<img title=\"Completada\" src=\"" + baseimg + "icon_completed.png\">";
            color = "color=\"#50b050\"";
            if (ai.getFlowNodeType() instanceof UserTask) {
                //actions="<a href=\"\"><img alt=\"Detalle\" src=\""+baseimg+"detail_icon.gif\"/></a>";
                actions="--";
            } else {
                actions="-";
            }
        }

        if (ai.getStatus() == Instance.STATUS_OPEN) {
            stat = "Abierta";
        }
        if (ai.getStatus() == Instance.STATUS_PROCESSING) {
            stat = "<img src=\"" + baseimg + "icon_pending.png\">";
            color = "color=\"red\"";
            if (ai.getFlowNodeType() instanceof UserTask) {
                actions = "<a class=\"acc-atender\" href=\"" + ((UserTask)ai.getFlowNodeType()).getTaskWebPage().getUrl() + "?suri=" + ai.getEncodedURI()+ "\">Atender</a>";
                //actions="<a target=\"_new\" href=\"\"><img alt=\"Detalle\" src=\""+baseimg+"detail_icon.gif\"/></a>" +
                //        " <a target=\"_new\" href=\"/swb/process/Diagrama_de_estado/_rid/196/_mto/3/_mod/applet?suri="+ ai.getProcessInstance().getProcessType().getEncodedURI() +"&mode=view&pending="+URLEncoder.encode(getStatusInstances(ai.getProcessInstance(), Instance.STATUS_PROCESSING))+"\"><img src=\""+baseimg + "diagram_icon.jpg\"></a>";
            } else {
                actions="-";
            }
        }
        if (ai.getStatus() == Instance.STATUS_STOPED) {
            stat = "Detenida";
        }
        out.println("<tr><td class=\"tban-id\">" + stat + "</td>");
        out.println("<td class=\"tban-tarea\">");
        if (ai.getStatus() == Instance.STATUS_PROCESSING) {
            out.println("<font " + color + ">");
        }
        out.println(ai.getFlowNodeType().getTitle());
        if (ai.getStatus() == Instance.STATUS_PROCESSING) {
            out.println("</font>");
        }
        out.println("</td>"
                + "<td class=\"tban-tarea\">" + ai.getCreator().getFullName() + "</td>"
                + "<td class=\"tban-inicia\">" + SWBUtils.TEXT.getStrDate(ai.getCreated(), "es", "dd/mm/yyyy - hh:mm:ss") + "</td>");
                if (ai.getStatus() == Instance.STATUS_CLOSED) {
                    out.println("<td class=\"tban-cerrada\">" + SWBUtils.TEXT.getStrDate(ai.getEnded(), "es", "dd/mm/yyyy - hh:mm:ss") + "</td>");
                } else {
                    out.println("<td class=\"tban-cerrada\">-</td>");
                }
                out.println("<td class=\"tban-tarea\">" + tOwner + "</td>");
                out.println("<td class=\"tban-accion\">" + actions + "</td>");
                out.println("</tr>");
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance pi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = pi.listFlowNodeInstances();
            if (acit.hasNext()) {
                //out.println("<tr><td style=\"border-bottom:1px solid #666666\">");
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    printActivityInstance(page, actinst, out);
                }
            }
        }
    }
%>

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
WebPage statusWp = (WebPage) request.getAttribute("statusWP");
User user = paramRequest.getUser();
WebPage topic = paramRequest.getWebPage();
ProcessSite site = (ProcessSite) paramRequest.getWebPage().getWebSite();
String lang = user.getLanguage();
org.semanticwb.process.model.Process process = SWBProcessMgr.getProcess(topic);
String baseimg = SWBPortal.getWebWorkPath() + "/models/" + topic.getWebSiteId() + "/css/images/";
%>
    <h2>Seguimiento del proceso (<%=process.getDisplayTitle(lang)%>)</h2>
        <%
        Iterator<ProcessInstance> it = SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                ProcessInstance pi = it.next();
                %>
                    <table class="tabla-bandeja">
                        <thead>
                            <th class="tban-id">Estado</th>
                            <th class="tban-id">Factor Cr&iacute;tico</th>
                            <th class="tban-id">Creador</th>
                            <th class="tban-id">Inicio</th>
                            <th class="tban-id">T&eacute;rmino</th>
                            <th class="tban-id">Rol responsable</th>
                            <th class="tban-id">Acciones</th>
                        </thead>
                        <tbody>
                            <%
                                Iterator<FlowNodeInstance> actit = SWBComparator.sortByCreated(pi.listFlowNodeInstances());
                                while (actit.hasNext()) {
                                    FlowNodeInstance obj = actit.next();
                                    printActivityInstance(topic, obj, out);
                                }
                            %>
                        </tbody>
                    </table>
                <!--h3>Tareas del usuario (<%=user.getFullName()%>) <a style="text-decoration: none" onclick="javascript:expande('tasks<%=process.getId()%>')"><img style="text-decoration:none" src="<%=baseimg + "icon_show.gif"%>"> </a><a style="text-decoration: none" onclick="javascript:colapsa('tasks<%=process.getId()%>')"><img src="<%=baseimg + "icon_hide.gif"%>"></a></h3>
                <div id="tasks<%=process.getId()%>">
                    <table>
                        <thead>
                            <tr>
                                <th>Proceso</th>
                                <th>Tarea</th>
                                <th>Estado</th>
                                <th>Creación - Proceso</th>
                                <th>Creador - Proceso</th>
                                <th>Creación - Tarea</th>
                                <th>Creador - Tarea</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            Iterator<FlowNodeInstance> utkit = SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
                            if (utkit.hasNext()) {
                                while (utkit.hasNext()) {
                                    FlowNodeInstance tkinst = utkit.next();
                                    FlowNode _task = tkinst.getFlowNodeType();
                                    if (_task instanceof UserTask) {
                                        UserTask task = (UserTask) _task;
                                        %>
                                        <tr>
                                            <td align="center"><%=pi.getId()%></td>
                                            <td align="center"><%=task.getDisplayTitle(lang)%></td>
                                            <td align="center"><%=tkinst.getStatus()%></td>
                                            <td align="center"><%=SWBUtils.TEXT.getStrDate(pi.getCreated(), lang, "dd/mm/yyyy")%></td>
                                            <td align="center"><%=pi.getCreator().getFullName()%></td>
                                            <td align="center"><%=SWBUtils.TEXT.getStrDate(tkinst.getCreated(), lang, "dd/mm/yyyy")%></td>
                                            <td align="center"><%=tkinst.getCreator().getFullName()%></td>
                                            <td align="center"><a href="<%=task.getTaskWebPage().getUrl()%>?suri=<%=tkinst.getEncodedURI()%>">Ver</a></td>
                                        </tr>
                                        <%
                                    }
                                }
                            }
                            %>
                        </tbody>
                    </table>
                </div -->
                <!--script type="text/javascript">
                    colapsa('tasks<%=process.getId()%>');
                </script-->
                <%
            }
        } else {
            %>
            <br />
            <%if (statusWp != null) {%>
                <img width="20" height="20" src="<%=baseimg + "Process-Info.png"%>"> <a target="_new" href="<%=statusWp.getUrl()%>?suri=<%=process.getEncodedURI()%>&mode=view">Mapa de proceso</a>
            <%}%>
            <br />
            <br />
            <%
        }
        %>

<!--script type="text/javascript">
    colapsa('objetivos');
    colapsa('insumos');
    colapsa('proposito');
    colapsa('dap');
    colapsa('productos');
    colapsa('tracking');
</script-->

<!--meta http-equiv="refresh" content="20"/-->