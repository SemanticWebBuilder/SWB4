/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.resources;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;

//import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.kpi.ResponseTimeStages;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Sergio Téllez
 */
public class ResponseTime extends GenericResource {

    private static Logger log = SWBUtils.getLogger(ResponseTime.class);
    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#92C2DF", "#BDDDE4", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.Action_EDIT.equals(paramRequest.getAction())) {
            doAdminCase(request, response, paramRequest);
        } else {
            doAdminResume(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");


        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL edit = paramRequest.getRenderUrl();
        edit.setMode(SWBResourceURL.Mode_EDIT);
        edit.setParameter("suri", suri);

        Enumeration enuparam = request.getParameterNames();
        while (enuparam.hasMoreElements()) {
            String sparam = (String) enuparam.nextElement();
            //System.out.println("param: " + sparam + " (" + request.getParameter(sparam) + ")");
        }

        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print("    <button  dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + edit.toString() + "',this.domNode);return false;\">" + paramRequest.getLocaleString("config") + "</button>");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("    <legend>" + paramRequest.getLocaleString("RESPONSE_TIME") + "</legend>\n");
        if (null != suri) {
            doGraph(request, response, paramRequest, suri);
        }
        out.print("  </fieldset>\n");
        out.print("</div>\n");
    }

    public void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        url.setAction("edit");
        url.setParameter("suri", suri);
        updateAttributes(request);
        out.print("<script>\n");
        out.print(" function stage(form) {\n");
        out.print("     form.action=\"" + url.toString() + "\";\n");
        out.print("     form.submit();\n");
        out.print(" }\n");
        out.print("</script>\n");
        url.setAction("resume");
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("TIME_STAGE"));
        out.print("  </fieldset>\n");
        out.print("  <form id=\"" + getResourceBase().getId() + "/restime\" name=\"restime\" action=" + url.toString() + " method=\"post\" onsubmit=\"submitForm('" + getResourceBase().getId() + "/restime'); return false;\">\n");
        /*out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("          <table border=\"0\"  width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"process\" name=\"process\" onchange=\"stage(form);\">\n");
        //out.print("                          <option value=\"\">&nbsp;</option>\n");
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
        ProcessSite site = (ProcessSite)isites.next();
        Iterator<Process> itprocess = site.listProcesses();
        while (itprocess.hasNext()) {
        Process process = itprocess.next();
        out.print("                  <option value=\"" +  process.getId() + "\"" + (getResourceBase().getAttribute("process","").equalsIgnoreCase(process.getId()) ? " selected" : "") + " >" + process.getTitle() +  "</option>\n");
        }
        }
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");*/
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("INIT_STAGE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"init_stage\" name=\"init_stage\" onchange=\"stage(form);\">\n");
        out.print("                          <option value=\"\">Seleccione</option>\n");
        Process process = getProcess(suri);
        //ProcessInstance pinst = getProcessInstance(getResourceBase().getAttribute("process",""));
        ProcessInstance pinst = process.getProcessInstance(); //getProcessInstance(process.getId());
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while (flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                selectInitStage(obj, out, paramRequest, suri);
            }
        }
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("FINAL_STAGE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"final_stage\" name=\"final_stage\">\n");
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while (flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                selectFinalStage(obj, out, paramRequest, suri);
            }
        }
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("bars") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("pie") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("area") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("blue") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("green") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("red") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("seconds") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("minutes") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("hours") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"4\"" + ("4".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("days") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri, "display_totals")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        /*out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"1\"" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("bars") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"2\"" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + ("".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("pie") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"3\"" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("area") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"1\"" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + ("".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("blue") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"2\"" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("green") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"3\"" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("red") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"1\"" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + ("".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("seconds") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"2\"" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("minutes") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"3\"" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("hours") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"4\"" + ("4".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("days") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");*/
        out.print("     <fieldset>\n");

        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >" + paramRequest.getLocaleString("apply") + "</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        url.setParameter("suri", suri);

        out.print("         <button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + url.toString() + "',this.domNode);return false;\">" + paramRequest.getLocaleString("return") + "</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_EDIT);
        url.setParameter("suri", suri);
        updateAttributes(request);
        Process process = getProcess(suri);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("TIME_STAGE"));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute(encode(suri), "")) ? getProcessTitle(suri) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        //out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("process","")) ? getProcessTitle(getResourceBase().getAttribute("process")) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("INIT_STAGE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getAttribute(suri, "init_stage")) ? getStageTitle(process.getId(), getAttribute(suri, "init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        //out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("FINAL_STAGE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getAttribute(suri, "final_stage")) ? getStageTitle(process.getId(), getAttribute(suri, "final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        //out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        /*out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? paramRequest.getLocaleString("bars") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? paramRequest.getLocaleString("pie") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? paramRequest.getLocaleString("area") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? paramRequest.getLocaleString("blue") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? paramRequest.getLocaleString("green") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? paramRequest.getLocaleString("red") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("seconds") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("minutes") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("hours") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("4".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("days") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")))
        out.print("                  <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? paramRequest.getLocaleString("DISPLAY_TOTALS") : "") + "</td>\n");
        else
        out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");*/
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + ("1".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("bars") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("pie") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("area") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? paramRequest.getLocaleString("blue") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? paramRequest.getLocaleString("green") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri, "plot_theme")) ? paramRequest.getLocaleString("red") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("seconds") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("minutes") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("hours") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("4".equalsIgnoreCase(getAttribute(suri, "time_unit")) ? paramRequest.getLocaleString("days") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        if ("1".equalsIgnoreCase(getAttribute(suri, "display_totals"))) {
            out.print("                  <td>" + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        } else {
            out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
        }
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");

        SWBResourceURL edit = paramRequest.getRenderUrl();
        edit.setMode(SWBResourceURL.Mode_EDIT);
        edit.setParameter("suri", suri);

        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + edit.toString() + "',this.domNode); return false;\">" + paramRequest.getLocaleString("config") + "</button>");
        url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + url.toString() + "',this.domNode); return false;\">" + paramRequest.getLocaleString("return") + "</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        Process process = getProcess(suri);
        if ("1".equalsIgnoreCase(getAttribute(suri, "plot"))) {
            doBars(request, response, paramRequest, process);
        } else if ("3".equalsIgnoreCase(getAttribute(suri, "plot"))) {
            doArea(request, response, paramRequest, process);
        } else {
            doPie(request, response, paramRequest, process);
        }
    }

    /*public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
    Iterator isites = ProcessSite.ClassMgr.listProcessSites();
    while (isites.hasNext()) {
    ProcessSite site = (ProcessSite)isites.next();
    Iterator<Process> it = site.listProcesses();
    while (it.hasNext()) {
    Process process = it.next();
    if (process.getId().equals(getResourceBase().getAttribute("process",""))) {
    if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
    doBars(request, response, paramRequest, process);
    else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
    doArea(request, response, paramRequest, process);
    else
    doPie(request, response, paramRequest, process);
    }
    }
    }
    }

    private void updateAttributes(HttpServletRequest request) {
    try {
    if (null != request.getParameter("process")) {
    getResourceBase().setAttribute("process", request.getParameter("process"));
    getResourceBase().setAttribute("init_stage", request.getParameter("init_stage"));
    getResourceBase().setAttribute("final_stage", request.getParameter("final_stage"));
    getResourceBase().setAttribute("plot", request.getParameter("plot"));
    getResourceBase().setAttribute("plot_theme", request.getParameter("plot_theme"));
    getResourceBase().setAttribute("time_unit", request.getParameter("time_unit"));
    getResourceBase().setAttribute("display_totals", request.getParameter("display_totals"));
    getResourceBase().updateAttributesToDB();
    }
    }catch (SWBException swbe) {
    swbe.printStackTrace();
    }
    }*/
    private void updateAttributes(HttpServletRequest request) {
        try {
            if (null != request.getParameter("init_stage")) {
                getResourceBase().setAttribute(encode(request.getParameter("suri")), getConfig(request.getParameter("init_stage"), request.getParameter("final_stage"), request.getParameter("plot"), request.getParameter("plot_theme"), request.getParameter("time_unit"), request.getParameter("display_totals"), request.getParameter("suri")));
                getResourceBase().updateAttributesToDB();
            }
        } catch (SWBException swbe) {
            log.error(swbe);
        }
    }

    private String getConfig(String init_stage, String final_stage, String plot, String colours, String time, String totals, String suri) {
        StringBuilder config = new StringBuilder();
        if (null != plot && !"".equals(plot)) {
            config.append(plot);
        } else {
            config.append(getAttribute(suri, "plot"));
        }
        if (null != colours && !"".equals(colours)) {
            config.append(colours);
        } else {
            config.append(getAttribute(suri, "plot_theme"));
        }
        if (null != time && !"".equals(time)) {
            config.append(time);
        } else {
            config.append(getAttribute(suri, "time_unit"));
        }
        if (null != totals && !"".equals(totals)) {
            config.append(totals);
        } else {
            config.append(getAttribute(suri, "display_totals"));
        }
        if (null != init_stage && !"".equals(init_stage)) {
            config.append("INIT_STAGE" + init_stage + "INIT_STAGE");
        } else {
            if (!"".equals(getAttribute(suri, "init_stage"))) {
                config.append(getAttribute(suri, "init_stage"));
            }
        }
        if (null != final_stage && !"".equals(final_stage)) {
            config.append("FINAL_STAGE" + final_stage + "FINAL_STAGE");
        } else {
            if (!"".equals(getAttribute(suri, "final_stage"))) {
                config.append(getAttribute(suri, "final_stage"));
            }
        }
        return config.toString();
    }

    private String getAttribute(String suri, String title) {
        String attribute = "";
        String config = getResourceBase().getAttribute(encode(suri), "");
        if ("plot".equals(title)) {
            if (config.length() > 0) {
                attribute = config.substring(0, 1);
            } else {
                attribute = "2";
            }
        } else if ("plot_theme".equals(title)) {
            if (config.length() > 1) {
                attribute = config.substring(1, 2);
            } else {
                attribute = "1";
            }
        } else if ("time_unit".equals(title)) {
            if (config.length() > 2) {
                attribute = config.substring(2, 3);
            } else {
                attribute = "1";
            }
        } else if ("display_totals".equals(title)) {
            if (config.length() > 3) {
                attribute = config.substring(3, 4);
            } else {
                attribute = "0";
            }
        } else if ("init_stage".equals(title) && config.indexOf("INIT_STAGE") > -1) {
            attribute = config.substring(4 + 10, config.lastIndexOf("INIT_STAGE"));
        } else if ("final_stage".equals(title) && config.indexOf("FINAL_STAGE") > -1) {
            attribute = config.substring(config.indexOf("FINAL_STAGE") + 11, config.lastIndexOf("FINAL_STAGE"));
        }
        return attribute;
    }

    private ProcessInstance getProcessInstance(String processId) {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite) isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if (processId.equalsIgnoreCase(process.getId())) {
                    return org.semanticwb.process.kpi.CaseProcessInstance.pop(process);
                }
            }
        }
        return null;
    }

    private void selectInitStage(FlowNodeInstance ai, PrintWriter out, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        if (!"".equals(getTitle(ai, paramRequest)) && !"org.semanticwb.process.model.EndEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()) && !"org.semanticwb.process.model.TimerIntermediateCatchEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName())) {
            out.print("                  <option value=\"" + ai.getFlowNodeType().hashCode() + "\"" + (getAttribute(suri, "init_stage").equalsIgnoreCase(("" + ai.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(ai, paramRequest) + "</option>\n");
        }
        //out.print("                  <option value=\"" +  ai.getFlowNodeType().hashCode() + "\"" + (getResourceBase().getAttribute("init_stage","").equalsIgnoreCase(("" + ai.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(ai,paramRequest) +  "</option>\n");
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    selectInitStage(actinst, out, paramRequest, suri);
                }
            }
        }
    }

    private GenericIterator<FlowNodeInstance> getTargetStage(FlowNodeInstance ai, String suri) {
        GenericIterator<FlowNodeInstance> targetInstances = null;
        //if (("" + ai.getFlowNodeType().hashCode()).equals(getResourceBase().getAttribute("init_stage","")))
        if (("" + ai.getFlowNodeType().hashCode()).equals(getAttribute(suri, "init_stage"))) {
            targetInstances = ai.listTargetInstances();
        } else if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    getTargetStage(actinst, suri);
                }
            }
        }
        return targetInstances;
    }

    private void selectFinalStage(FlowNodeInstance ai, PrintWriter out, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        ArrayList<FlowNodeInstance> stage = new ArrayList();
        setReachableStage(getTargetStage(ai, suri), stage);
        Iterator<FlowNodeInstance> targetStage = stage.listIterator();
        while (targetStage.hasNext()) {
            FlowNodeInstance fni = targetStage.next();
            if (!"".equals(getTitle(fni, paramRequest))) {
                out.print("                  <option value=\"" + fni.getFlowNodeType().hashCode() + "\"" + (getAttribute(suri, "final_stage").equalsIgnoreCase(("" + fni.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(fni, paramRequest) + "</option>\n");
            }
            //out.print("                  <option value=\"" +  fni.getFlowNodeType().hashCode() + "\"" + (getResourceBase().getAttribute("final_stage","").equalsIgnoreCase(("" + fni.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(fni, paramRequest) +  "</option>\n");
        }
    }

    private void setReachableStage(GenericIterator<FlowNodeInstance> targetInstances, ArrayList<FlowNodeInstance> stage) {
        if (null != targetInstances) {
            while (targetInstances.hasNext()) {
                FlowNodeInstance fni = targetInstances.next();
                stage.add(fni);
                setReachableStage(fni.listTargetInstances(), stage);
            }
        }
    }

    private String getStageTitle(FlowNodeInstance ai, String stageId, SWBParamRequest paramRequest) {
        String title = null;
        if (stageId.equalsIgnoreCase("" + ai.getFlowNodeType().hashCode())) {
            title = getTitle(ai, paramRequest);
        }
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance) ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while (acit.hasNext()) {
                    FlowNodeInstance actinst = acit.next();
                    getStageTitle(actinst, stageId, paramRequest);
                }
            }
        }
        return title;
    }

    private String getStageTitle(String processId, String stageId, SWBParamRequest paramRequest) {
        String title = null;
        ProcessInstance pinst = getProcessInstance(processId);
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while (flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                if (null != getStageTitle(obj, stageId, paramRequest)) {
                    title = getStageTitle(obj, stageId, paramRequest);
                }
            }
        }
        return title;
    }

    private Process getProcess(String suri) {
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(suri);
        Process process = null;
        if (gobj instanceof org.semanticwb.process.model.Process) {
            process = (org.semanticwb.process.model.Process) gobj;
        }


//        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
//        while (isites.hasNext()) {
//            ProcessSite site = (ProcessSite)isites.next();
//
//
//
//            Iterator<Process> itprocess = site.listProcesses();
//
//
//
//            while (itprocess.hasNext()) {
//                Process process = itprocess.next();
//                System.out.println("suri: "+suri);
//                System.out.println("Process uri: "+process.getURI());
//                if (suri.equalsIgnoreCase(process.getURI()))
//                    return process;
//            }
//        }
        return process;
    }

    private void setPlotTheme(String plot_theme) {
        String[] blueTheme = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
        String[] redTheme = {"#FF0000", "#E42217", "#E41B17", "#F62817", "#F62217", "#E42217", "#F80000", "#C80000", "#B80000", "#900000"};
        String[] greenTheme = {"#4AA02C", "#57E964", "#59E817", "#4CC552", "#4CC417", "#52D017", "#41A317", "#3EA99F", "#348781", "#387C44"};
        if ("1".equalsIgnoreCase(plot_theme)) {
            opacity = "0.4";
            colour = "#3090C7";
            colours = blueTheme;
        } else if ("2".equalsIgnoreCase(plot_theme)) {
            opacity = "0.4";
            colour = "#4CC552";
            colours = greenTheme;
        } else if ("3".equalsIgnoreCase(plot_theme)) {
            opacity = "0.7";
            colour = "#FF0000";
            colours = redTheme;
        }
    }

    /*private void setPlotTheme() {
    String[] blueTheme = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] redTheme = {"#FF0000","#E42217","#E41B17","#F62817","#F62217","#E42217","#F80000","#C80000","#B80000","#900000"};
    String[] greenTheme = {"#4CC417","#57E964","#59E817","#4CC552","#4AA02C","#52D017","#41A317","#3EA99F","#348781","#387C44"};
    if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
    opacity = "0.4";
    colour = "#3090C7";
    colours = blueTheme;
    }else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
    opacity = "0.4";
    colour = "#4CC552";
    colours = greenTheme;
    }else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
    opacity = "0.7";
    colour = "#FF0000";
    colours = redTheme;
    }
    }*/
    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        String init_stage = getAttribute(process.getURI(), "init_stage");
        String final_stage = getAttribute(process.getURI(), "final_stage");

        long lid = System.currentTimeMillis();

        out.println("<script type=\"text/javascript\">");
        out.println("   dojo.require(\"dojox.charting.Chart2D\");");
        out.println("   dojo.require(\"dojox.charting.themes.PlotKit.blue\");");
        out.println("   dojo.require(\"dojox.charting.action2d.MoveSlice\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Highlight\");");
        out.println("   makeObjects = function(){");
        out.println("       var chart = new dojox.charting.Chart2D(\"" + lid + "_instances\");");
        out.println("       chart.setTheme(dojox.charting.themes.PlotKit.blue);");
        out.println("       chart.addPlot(\"default\", {");
        out.println("           type: \"Pie\",");
        out.println("           font: \"normal normal bold 10pt Tahoma\",");
        out.println("           fontColor: \"white\",");
        out.println("           labelOffset: 40,");
        out.println("           radius: 120");
        out.println("       });");
        out.println("       chart.addSeries(\"ResponseTime\", [");
        out.println("           " + getDataPie(process, paramRequest));
        out.println("       ]);");
        out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\")");
        out.println("       var b = new dojox.charting.action2d.Highlight(chart, \"default\", {highlight: \"#6698FF\"});");
        out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
        out.println("       chart.render();");
        out.println("   };");
        out.println("   dojo.addOnLoad(makeObjects);");
        out.println("</script>");
        out.println("<div id=\"" + lid + "_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id=\"" + lid + "_instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\"" + lid + "_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(init_stage) ? getStageTitle(process.getId(), init_stage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(final_stage) ? getStageTitle(process.getId(), final_stage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
        //out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) +"</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        //out.println(Ajax.getChartScript());
        long lid = System.currentTimeMillis();
        String init_stage = getAttribute(process.getURI(), "init_stage");
        String final_stage = getAttribute(process.getURI(), "final_stage");
        out.println("<div id=\"" + lid + "_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='" + lid + "_instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var areagraph = new Grafico.AreaGraph($('" + lid + "_instances'), { workload: " + getData(process) + " },");
        out.println("        {");
        out.println("           grid :                false,");
        out.println("           area_opacity :        " + opacity + ",");
        out.println("           plot_padding :        10,");
        out.println("           font_size :           10,");
        out.println("           colors :              { workload: '" + colour + "' },");
        //out.println("           background_color :	  '#EFF5FB',");
        out.println("           label_color :         \"#348781\",");
        //if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")))
        if ("1".equalsIgnoreCase(getAttribute(process.getURI(), "display_totals"))) {
            out.println("           markers :             \"value\",");
        }
        out.println("           meanline :            false,");
        out.println("           draw_axis :           false,\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           datalabels :          {workload: '" + paramRequest.getLocaleString("time") + "'}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"" + lid + "_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(init_stage) ? getStageTitle(process.getId(), init_stage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(final_stage) ? getStageTitle(process.getId(), final_stage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
        //out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) +"</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        long lid = System.currentTimeMillis();
        //out.println(Ajax.getChartScript());
        String init_stage = getAttribute(process.getURI(), "init_stage");
        String final_stage = getAttribute(process.getURI(), "final_stage");
        out.println("<div id=\"" + lid + "_title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='" + lid + "_instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var bargraph = new Grafico.BarGraph($('" + lid + "_instances'), " + getData(process) + ",\n");
        out.println("        {\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           color :				  '" + colour + "',\n");
        //out.println("           background_color :	  '#EFF5FB',\n");
        out.println("           meanline :		      false,\n");
        out.println("           grid :                false,\n");
        out.println("           draw_axis :           false,\n");
        out.println("           label_rotation :	  -30,\n");
        //out.println("           vertical_label_unit : \"%\",\n");
        //out.println("           bargraph_lastcolor :  \"#666666\",\n");
        out.println("           label_color :         \"#348781\",\n");
        out.println("           hover_color :         \"#6698FF\",\n");
        out.println("           datalabels :          {one: " + getLabels(process, paramRequest) + "}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"" + lid + "_stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(init_stage) ? getStageTitle(process.getId(), init_stage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(final_stage) ? getStageTitle(process.getId(), final_stage, paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    private String getTitles(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + "', '" + paramRequest.getLocaleString("average") + "','" + paramRequest.getLocaleString("maximum") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getData(Process process) {
        long minimum = 0;
        long average = 0;
        long maximum = 0;
        StringBuilder data = new StringBuilder();
        String init_stage = getAttribute(process.getURI(), "init_stage");
        String final_stage = getAttribute(process.getURI(), "final_stage");
        String time_unit = getAttribute(process.getURI(), "time_unit");
        if (!"".equalsIgnoreCase(init_stage) && !"".equalsIgnoreCase(final_stage)) {
            minimum = ResponseTimeStages.getMinimumTimeStages(process, init_stage, final_stage);
            average = ResponseTimeStages.getAverageTimeStages(process, init_stage, final_stage);
            maximum = ResponseTimeStages.getMaximumTimeStages(process, init_stage, final_stage);
        }
        data.append("[");
        if ("1".equalsIgnoreCase(time_unit)) {
            data.append(minimum / 1000 + "," + average / 1000 + "," + maximum / 1000);
        } else if ("2".equalsIgnoreCase(time_unit)) {
            data.append(minimum / 60000 + "," + average / 60000 + "," + maximum / 60000);
        } else if ("3".equalsIgnoreCase(time_unit)) {
            data.append(minimum / 3600000 + "," + average / 3600000 + "," + maximum / 3600000);
        } else {
            data.append(minimum / 86400000 + "," + average / 86400000 + "," + maximum / 86400000);
        }
        data.append("]");
        return data.toString();
    }

    private String getDataPie(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        long minimum = 0;
        long average = 0;
        long maximum = 0;
        StringBuilder data = new StringBuilder();
        String init_stage = getAttribute(process.getURI(), "init_stage");
        String final_stage = getAttribute(process.getURI(), "final_stage");
        String time_unit = getAttribute(process.getURI(), "time_unit");
        String display_totals = getAttribute(process.getURI(), "display_totals");
        if (!"".equalsIgnoreCase(init_stage) && !"".equalsIgnoreCase(final_stage)) {
            minimum = ResponseTimeStages.getMinimumTimeStages(process, init_stage, final_stage);
            average = ResponseTimeStages.getAverageTimeStages(process, init_stage, final_stage);
            maximum = ResponseTimeStages.getMaximumTimeStages(process, init_stage, final_stage);
        }
        data.append("{y: ");
        if ("1".equalsIgnoreCase(time_unit)) {
            data.append(minimum / 1000);
        } else if ("2".equalsIgnoreCase(time_unit)) {
            data.append(minimum / 60000);
        } else if ("3".equalsIgnoreCase(time_unit)) {
            data.append(minimum / 3600000);
        } else {
            data.append(minimum / 86400000);
        }
        data.append(", text: \"" + paramRequest.getLocaleString("minimum") + "\", color: \"" + colours[0] + "\"" + ("1".equalsIgnoreCase(display_totals) ? ", tooltip: " + getToolTips(paramRequest, time_unit, "minimum", minimum) : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(time_unit)) {
            data.append(average / 1000);
        } else if ("2".equalsIgnoreCase(time_unit)) {
            data.append(average / 60000);
        } else if ("3".equalsIgnoreCase(time_unit)) {
            data.append(average / 3600000);
        } else {
            data.append(average / 86400000);
        }
        data.append(", text: \"" + paramRequest.getLocaleString("average") + "\", color: \"" + colours[1] + "\"" + ("1".equalsIgnoreCase(display_totals) ? ", tooltip: " + getToolTips(paramRequest, time_unit, "average", average) : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(time_unit)) {
            data.append(maximum / 1000);
        } else if ("2".equalsIgnoreCase(time_unit)) {
            data.append(maximum / 60000);
        } else if ("3".equalsIgnoreCase(time_unit)) {
            data.append(maximum / 3600000);
        } else {
            data.append(maximum / 86400000);
        }
        data.append(", text: \"" + paramRequest.getLocaleString("maximum") + "\", color: \"" + colours[2] + "\"" + ("1".equalsIgnoreCase(display_totals) ? ", tooltip: " + getToolTips(paramRequest, time_unit, "maximum", maximum) : "") + "}");
        return data.toString();
    }

    private String getLabels(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        long min = 0;
        long avg = 0;
        long max = 0;
        StringBuilder labels = new StringBuilder();
        StringBuilder minimum = new StringBuilder();
        StringBuilder average = new StringBuilder();
        StringBuilder maximum = new StringBuilder();
        String init_stage = getAttribute(process.getURI(), "init_stage");
        String final_stage = getAttribute(process.getURI(), "final_stage");
        String time_unit = getAttribute(process.getURI(), "time_unit");
        String display_totals = getAttribute(process.getURI(), "display_totals");
        if (!"".equalsIgnoreCase(init_stage) && !"".equalsIgnoreCase(final_stage)) {
            min = ResponseTimeStages.getMinimumTimeStages(process, init_stage, final_stage);
            avg = ResponseTimeStages.getAverageTimeStages(process, init_stage, final_stage);
            max = ResponseTimeStages.getMaximumTimeStages(process, init_stage, final_stage);
        }
        if ("1".equalsIgnoreCase(time_unit)) {
            minimum.append("" + min / 1000 + " " + paramRequest.getLocaleString("seconds"));
            average.append("" + avg / 1000 + " " + paramRequest.getLocaleString("seconds"));
            maximum.append("" + max / 1000 + " " + paramRequest.getLocaleString("seconds"));
        } else if ("2".equalsIgnoreCase(time_unit)) {
            minimum.append("" + min / 60000 + " " + paramRequest.getLocaleString("minutes"));
            average.append("" + avg / 60000 + " " + paramRequest.getLocaleString("minutes"));
            maximum.append("" + max / 60000 + " " + paramRequest.getLocaleString("minutes"));
        } else if ("3".equalsIgnoreCase(time_unit)) {
            minimum.append("" + min / 3600000 + " " + paramRequest.getLocaleString("hours"));
            average.append("" + avg / 3600000 + " " + paramRequest.getLocaleString("hours"));
            maximum.append("" + max / 3600000 + " " + paramRequest.getLocaleString("hours"));
        } else {
            minimum.append("" + min / 86400000 + " " + paramRequest.getLocaleString("days"));
            average.append("" + avg / 86400000 + " " + paramRequest.getLocaleString("days"));
            maximum.append("" + max / 86400000 + " " + paramRequest.getLocaleString("days"));
        }
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + " " + ("1".equalsIgnoreCase(display_totals) ? minimum : "") + "', '" + paramRequest.getLocaleString("average") + " " + ("1".equalsIgnoreCase(display_totals) ? average : "") + "','" + paramRequest.getLocaleString("maximum") + " " + ("1".equalsIgnoreCase(display_totals) ? maximum : "") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getToolTips(SWBParamRequest paramRequest, String timeUnit, String function, long time) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        StringBuilder total = new StringBuilder();
        if ("1".equalsIgnoreCase(timeUnit)) {
            total.append(" " + time / 1000);
            total.append(" " + paramRequest.getLocaleString("seconds"));
        } else if ("2".equalsIgnoreCase(timeUnit)) {
            total.append(" " + time / 60000);
            total.append(" " + paramRequest.getLocaleString("minutes"));
        } else if ("3".equalsIgnoreCase(timeUnit)) {
            total.append(" " + time / 3600000);
            total.append(" " + paramRequest.getLocaleString("hours"));
        } else {
            total.append(" " + time / 86400000);
            total.append(" " + paramRequest.getLocaleString("days"));
        }
        labels.append("\"" + paramRequest.getLocaleString("time") + " " + paramRequest.getLocaleString(function) + total.toString() + "\"");
        return labels.toString();
    }

    /*private Process getProcess(String processId) {
    Iterator isites = ProcessSite.ClassMgr.listProcessSites();
    while (isites.hasNext()) {
    ProcessSite site = (ProcessSite)isites.next();
    Iterator<Process> itprocess = site.listProcesses();
    while (itprocess.hasNext()) {
    Process process = itprocess.next();
    if (processId.equalsIgnoreCase(process.getId()))
    return process;
    }
    }
    return null;
    }*/

    /*private String getProcessTitle(String processId) {
    Process process = getProcess(processId);
    if (null != process)
    return process.getTitle();
    else
    return "";
    }*/
    private String getProcessTitle(String suri) {
        String title = "";
        if (null != suri) {
            Process process = getProcess(suri);
            if (null != process) {
                title = process.getTitle();
            }
        }
        return title;
    }

    private String getTitle(FlowNodeInstance fni, SWBParamRequest paramRequest) {
        if (null != fni) {
            if (null != fni.getFlowNodeType().getTitle(paramRequest.getUser().getLanguage())) {
                return fni.getFlowNodeType().getTitle(paramRequest.getUser().getLanguage());
            } else {
                return fni.getFlowNodeType().getTitle();
            }
        } else {
            return "";
        }
    }

    private String encode(String suri) {
        return java.net.URLEncoder.encode(suri).replaceAll("%", "100");
    }

    /*public void doChart(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
    Iterator isites = ProcessSite.ClassMgr.listProcessSites();
    String pathFile = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/images";
    File filex = new File(pathFile);
    if (!filex.exists())
    filex.mkdirs();
    while (isites.hasNext()) {
    ProcessSite site = (ProcessSite)isites.next();
    Iterator<Process> it = site.listProcesses();
    while (it.hasNext()) {
    Process process = it.next();
    if ("Soporte Técnico".equalsIgnoreCase(process.getTitle()) && ResponseTimeStages.getMinimumTimeStages(process,"Start Event","Solucion") < ResponseTimeStages.getMaximumTimeStages(process,"Start Event","Solucion")) {
    Function2D normal = new NormalDistributionFunction2D(ResponseTimeStages.getAverageTimeStages(process,"Start Event","Solucion")/1000, 1.0);
    XYDataset dataset = DatasetUtilities.sampleFunction2D(normal, ResponseTimeStages.getMinimumTimeStages(process,"Start Event","Solucion")/1000, ResponseTimeStages.getMaximumTimeStages(process,"Start Event","Solucion")/1000, 100, "Escenario Inicio-Solución");
    JFreeChart chart = ChartFactory.createXYLineChart(process.getTitle(), paramRequest.getLocaleString("seconds"), paramRequest.getLocaleString("NORMAL_DISTRIBUTION"), dataset, PlotOrientation.VERTICAL, true, true, false);
    try {
    ChartUtilities.saveChartAsPNG(new File(pathFile + "/" + process.getId() + "_stage.png"), chart, 500, 300);
    response.getWriter().println("<div style=\"background-image: url(" + SWBPortal.getWebWorkPath() + getResourceBase().getWorkPath() + "/images/" + process.getId() + "_stage.png); height: 300px; width: 500px; border: 0px solid black;\"> </div>");
    }catch (Exception e) {
    log.error(e);
    }
    }
    }
    }
    }*/
}
