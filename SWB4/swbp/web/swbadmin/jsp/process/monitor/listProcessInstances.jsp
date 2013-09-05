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

<%@page import="org.semanticwb.process.resources.tracer.ProcessTracer"%>
<%@page import="org.semanticwb.process.model.Process"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.process.model.Instance"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="org.semanticwb.process.model.FlowNode"%>
<%@page import="org.semanticwb.process.model.ProcessInstance"%>
<%@page import="org.semanticwb.process.model.SubProcessInstance"%>
<%@page import="org.semanticwb.process.model.WrapperProcessWebPage"%>
<%@page import="org.semanticwb.process.model.SWBProcessMgr"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.*"%>
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
        String tCreator = "--";
        
        if (ai.getCreator() != null && ai.getCreator().getFullName() != null) {
            tCreator = ai.getCreator().getFullName();
        }

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
            stat = "<img title=\"Completada\" src=\"" + baseimg + "icono-terminado.gif\">";
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
            stat = "<img src=\"" + baseimg + "icono-iniciado.gif\">";
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
                + "<td class=\"tban-tarea\">" + tCreator + "</td>"
                + "<td class=\"tban-inicia\">" + SWBUtils.TEXT.getStrDate(ai.getCreated(), "es", "dd/mm/yyyy - hh:mm:ss") + "</td>");
                if (ai.getStatus() == Instance.STATUS_CLOSED) {
                    out.println("<td class=\"tban-cerrada\">" + SWBUtils.TEXT.getStrDate(ai.getEnded(), "es", "dd/mm/yyyy - hh:mm:ss") + "</td>");
                } else {
                    out.println("<td class=\"tban-cerrada\">-</td>");
                }
                out.println("<td class=\"tban-tarea\">" + tOwner + "</td>");
                //out.println("<td class=\"tban-accion\">" + actions + "</td>");
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

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
//WebPage statusWp = (WebPage) request.getAttribute("statusWP");
int mode = (Integer) request.getAttribute("viewMode");
ArrayList<ProcessInstance> instances = (ArrayList<ProcessInstance>) request.getAttribute("instances");
WebPage topic = paramRequest.getWebPage();

Iterator<ProcessInstance> it = null;
if (instances != null) it = instances.iterator();

//String baseimg = SWBPortal.getWebWorkPath() + "/models/" + topic.getWebSiteId() + "/css/images/";
if (mode == ProcessTracer.MODE_TRACKING) {
    if (it != null && it.hasNext()) {
        while (it.hasNext()) {
            ProcessInstance pi = it.next();
            %>
            <table class="tabla-bandeja">
                <thead>
                    <th class="tban-id">Estatus</th>
                    <th class="tban-tarea">Tarea</th>
                    <th class="tban-tarea">Creador</th>
                    <th class="tban-inicia">Iniciado</th>
                    <th class="tban-cerrada">Terminado</th>
                    <th class="tban-tarea">Responsable</th>
                </thead>
                <tbody>
                    <%
                        Iterator<FlowNodeInstance> actit = SWBComparator.sortByCreated(pi.listFlowNodeInstances());
                        while (actit.hasNext()) {
                            FlowNodeInstance obj = actit.next();
                            if (obj.getStatus() == Instance.STATUS_PROCESSING)
                            printActivityInstance(topic, obj, out);
                        }
                    %>
                </tbody>
            </table>
                <a href="#" onclick="history.go(-1);">Regresar</a>
            <%
        }
    }
} else {
    System.out.println("Modo overview");
}
    %>
<!--meta http-equiv="refresh" content="20"/-->