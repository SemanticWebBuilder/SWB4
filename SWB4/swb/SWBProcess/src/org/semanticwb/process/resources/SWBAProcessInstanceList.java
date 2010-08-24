/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.SubProcessInstance;

/**
 *
 * @author juan.fernandez
 */
public class SWBAProcessInstanceList extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri"); // del process
        String page = request.getParameter("page");
        org.semanticwb.process.model.Process process = null;

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(id);

        String action = request.getParameter("act");
        if (action == null) {
            action = "";
        }

        if (gobj instanceof org.semanticwb.process.model.Process) {
            process = (org.semanticwb.process.model.Process) gobj;
        } else {
            return;
        }

        ProcessSite site = process.getProcessSite();

        if (null != process) {

            if ("".equals(action)) {
                SWBResourceURL urlact = paramRequest.getActionUrl();
                urlact.setParameter("suri", id);

                SWBResourceURL urlnpi = paramRequest.getActionUrl();
                urlnpi.setParameter("suri", id);
                urlnpi.setParameter("act", "cpi");

                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>");
                out.println("Lista de instancias del proceso.");
                out.println("</legend>");
                //out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlnpi + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btnNewProcessInst") + "</button>"); //

                out.println("<table width=\"100%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>");
                out.println("Acción");
                out.println("</th>");
                out.println("<th>");
                out.println("Identificador");
                out.println("</th>");
                out.println("<th>");
                out.println("Titulo");
                out.println("</th>");
                out.println("<th>");
                out.println("Artefactos");
                out.println("</th>");
                out.println("<th>");
                out.println("Detalle proceso");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");

                out.println("<tbody>");
                Iterator<ProcessInstance> pit = process.listProcessInstances();
                while (pit.hasNext()) {
                    ProcessInstance pi = pit.next();

                    SWBResourceURL urlrem = paramRequest.getActionUrl();
                    urlrem.setParameter("suri", id);
                    urlrem.setParameter("suripi", pi.getId());
                    urlrem.setParameter("act", "rpi");

                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " " + SWBUtils.TEXT.scape4Script(pi.getSemanticObject().getDisplayName(user.getLanguage())) + "?')){ submitUrl('" + urlrem + "',this); } else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("remove") + "\"></a>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(pi.getId());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(pi.getProcessType().getTitle());
                    out.println("</td>");

                    //liga para ver artefactos asociados a esta instancia de proceso.
                    SWBResourceURL urlart = paramRequest.getRenderUrl();
                    urlart.setParameter("suri", id);
                    urlart.setParameter("suripi", pi.getId());
                    urlart.setParameter("act", "artifacts");

                    out.println("<td>");
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("showartifacts") + "\" onclick=\"submitUrl('" + urlart + "',this); return false;\">" + paramRequest.getLocaleString("msgview") + "</a>");
                    out.println("</td>");

                    //liga para ver el detalle de esta instancia del proceso.
                    SWBResourceURL urlpd = paramRequest.getRenderUrl();
                    urlpd.setParameter("suri", id);
                    urlpd.setParameter("suripi", pi.getId());
                    urlpd.setParameter("act", "pidetail");

                    out.println("<td>");
                    out.println("<a href=\"#\" title=\"" + paramRequest.getLocaleString("procInstDetail") + "\" onclick=\"submitUrl('" + urlpd + "',this); return false;\">" + paramRequest.getLocaleString("msgview") + "</a>");
                    //out.println("Detalle proceso");
                    out.println("</td>");
                    out.println("</tr>");

                }


                out.println("</tbody>");
                out.println("<tfoot>");
                out.println("<tr>");
                out.println("<td colspan=\"5\">");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlnpi + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btnNewProcessInst") + "</button>"); //
                out.println("</td>");
                out.println("</tr>");
                out.println("</tfoot>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</div>");
            } else if ("artifacts".equals(action)) {

                String pinsturi = request.getParameter("suripi");

                ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(pinsturi, site);

                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>");
                out.println("Artefactos");
                out.println("</legend>");

                out.println("<ul>");
                Iterator<ProcessObject> objit = pi.listAllProcessObjects();
                while (objit.hasNext()) {
                    ProcessObject obj = objit.next();
                    out.println("<li>Object Instance:" + obj.getURI() + " ");
                    out.println("<a href=\"#\"  onclick=\"addNewTab('" + obj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + SWBUtils.TEXT.cropText(SWBUtils.TEXT.scape4Script(obj.getSemanticObject().getDisplayName()),25) + "');return false;\">" + obj.getSemanticObject().getDisplayName() + "</a>");
                    out.println("</li>");
                }
                out.println("</ul>");
                SWBResourceURL urlbck = paramRequest.getRenderUrl();
                urlbck.setParameter("suri", id);
                urlbck.setParameter("act", "");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlbck + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btnBack") + "</button>"); //

                out.println("</div>");
            } else if ("pidetail".equals(action)) {

                String pinsturi = request.getParameter("suripi");

                ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(pinsturi, site);

                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>");
                out.println("Detalle de Proceso");
                out.println("</legend>");

                out.println("<ul>");
                Iterator<FlowNodeInstance> actit = pi.listFlowNodeInstances();
                while (actit.hasNext()) {
                    FlowNodeInstance obj = actit.next();
                    printActivityInstance(obj, out);
                }
                out.println("</ul>");
                SWBResourceURL urlbck = paramRequest.getRenderUrl();
                urlbck.setParameter("suri", id);
                urlbck.setParameter("act", "");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlbck + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btnBack") + "</button>"); //
                out.println("</div>");
            }


//                out.println("<h3>Tareas del usuario (" + user.getFullName() + ")</h3>");
//                out.println("<ul>");
//                Iterator<FlowNodeInstance> utkit = SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
//                while (utkit.hasNext()) {
//                    FlowNodeInstance tkinst = utkit.next();
//                    out.println("<li>User Task: " + tkinst.getFlowNodeType().getTitle() + " " + tkinst.getId() + " Status: " + tkinst.getStatus() + " <a href=\"?id=" + tkinst.getId() + "&act=accept&user=" + user.getLogin() + "\">accept</a> <a href=\"?id=" + tkinst.getId() + "&act=reject&user=" + user.getLogin() + "\">reject</a></li>");
//                }
//                out.println("</ul>");


//            out.println("<h3>Process Observer</h3>");
//            out.println("<h4>SignalObserverInstances</h4>");
//
//            out.println("<ul>");
//            Iterator<FlowNodeInstance> actit = site.getProcessObserver().listSignalObserverInstances();
//            while (actit.hasNext()) {
//                FlowNodeInstance obj = actit.next();
//                printActivityInstance(obj, out);
//            }
//            out.println("</ul>");
//
//            out.println("<h4>SignalObserverNodes</h4>");
//
//            out.println("<ul>");
//            Iterator<StartEvent> sigit = site.getProcessObserver().listSignalObserverNodes();
//            while (sigit.hasNext()) {
//                StartEvent obj = sigit.next();
//                out.println("StartEvent:" + obj + "<BR>");
//            }
//            out.println("</ul>");
//
//            out.println("<h4>TimeObserverInstances</h4>");
//
//            out.println("<ul>");
//            Iterator<FlowNodeInstance> timit = site.getProcessObserver().listTimeObserverInstances();
//            while (timit.hasNext()) {
//                FlowNodeInstance obj = timit.next();
//                printActivityInstance(obj, out);
//            }
//            out.println("</ul>");
//
//            out.println("<h4>TimeObserverNodes</h4>");
//
//            out.println("<ul>");
//            Iterator<StartEvent> timnit = site.getProcessObserver().listTimeObserverNodes();
//            while (timnit.hasNext()) {
//                StartEvent obj = timnit.next();
//                out.println("StartEvent:" + obj + "<BR>");
//            }
//            out.println("</ul>");


        }
    }

    public void printActivityInstance(FlowNodeInstance ai, PrintWriter out) throws IOException {
        out.println("<li>");
        out.println("Activity: " + ai.getFlowNodeType().getTitle() + " " + ai.getId());
        out.println("Status:" + ai.getStatus());
        out.println("Action:" + ai.getAction());
        out.println("<a href=\"#\"  onclick=\"addNewTab('" + ai.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + SWBUtils.TEXT.cropText(SWBUtils.TEXT.scape4Script(ai.getSemanticObject().getDisplayName()),25) + "');return false;\">" + ai.getSemanticObject().getDisplayName() + "</a>");
        out.println("</li>");
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance pi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = pi.listFlowNodeInstances();
            if (acit.hasNext()) {
                out.println("<ul>");
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    printActivityInstance(actinst, out);
                }
                out.println("</ul>");
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        User user = response.getUser();
        String id = request.getParameter("suri");
        String act = request.getParameter("act");
        org.semanticwb.process.model.Process process = null;
        ProcessSite site = null;

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(id);

        if (act == null) {
            act = "";
        }

        if (gobj instanceof org.semanticwb.process.model.Process) {
            process = (org.semanticwb.process.model.Process) gobj;
        } else {
            return;
        }

        site = process.getProcessSite();

        if (act != null) {
            if (act.equals("rpi")) {

                String pinst = request.getParameter("suripi");
                ProcessInstance inst = ProcessInstance.ClassMgr.getProcessInstance(pinst, site);
                inst.remove();
            }
            if (act.equals("cpi")) {

                process = (org.semanticwb.process.model.Process) SemanticObject.createSemanticObject(id).createGenericInstance();
                SWBProcessMgr.createProcessInstance(process, user);
            }
            if (act.equals("accept") || act.equals("reject")) {

                FlowNodeInstance inst = FlowNodeInstance.ClassMgr.getFlowNodeInstance(id, site);
                inst.close(user, act);
            }
        }
        if (id != null) {
            response.setRenderParameter("suri", id);
        }
    }
}
