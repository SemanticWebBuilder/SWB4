/**
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
 **/

package org.semanticwb.process.resources;

import java.util.Iterator;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;

import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.kpi.CaseResponseTime;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Sergio Téllez
 */
public class ResponseCase extends GenericResource {

    private static Logger log = SWBUtils.getLogger(ResponseCase.class);

    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#92C2DF", "#BDDDE4", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.Action_EDIT.equals(paramRequest.getAction()))
            doAdminCase(request, response, paramRequest);
        else
            doAdminResume(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL edit = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        edit.setParameter("suri", request.getParameter("suri"));
        out.println("<div id=\"properties\" class=\"swbform\">");
        out.println("  <fieldset>\n");
        out.println("    <a href=\"" + edit + "\">" + paramRequest.getLocaleString("config") + "</a>");
        out.println("  </fieldset>");
        out.println("  <fieldset>");
        out.println("    <legend>" + paramRequest.getLocaleString("title") + "</legend>");
        if (null != request.getParameter("suri"))
            doGraph(request, response, paramRequest, request.getParameter("suri"));
        out.println("  </fieldset>");
        out.println("</div>");
    }

    private void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        url.setAction("properties");
        url.setParameter("suri", suri);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <form id=\"case\" name=\"case\" action=" + url.toString() + " method=\"post\">\n");
        /*out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"process\" name=\"process\">\n");
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
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");*/
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
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("blue") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("green") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("red") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("seconds") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("minutes") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("hours") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"4\"" + ("4".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("days") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(suri,"display_totals")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >"+paramRequest.getLocaleString("apply")+"</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        url.setParameter("suri", suri);
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute(encode(suri),"")) ? getProcessTitle(suri) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
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
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? paramRequest.getLocaleString("blue") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? paramRequest.getLocaleString("green") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? paramRequest.getLocaleString("red") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("seconds") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("minutes") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("hours") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("4".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("days") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        if ("1".equalsIgnoreCase(getAttribute(suri,"display_totals")))
            out.print("                  <td>" + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        else
            out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("config")+"</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }

    /*private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("process","")) ? getProcessTitle(getResourceBase().getAttribute("process")) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
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
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("config")+"</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }*/

    private void updateAttributes(HttpServletRequest request) {
        try {
            getResourceBase().setAttribute(encode(request.getParameter("suri")),getConfig(request.getParameter("plot"), request.getParameter("plot_theme"), request.getParameter("time_unit"), request.getParameter("display_totals")));
            /*getResourceBase().setAttribute("process", request.getParameter("process"));
            getResourceBase().setAttribute("plot", request.getParameter("plot"));
            getResourceBase().setAttribute("plot_theme", request.getParameter("plot_theme"));
            getResourceBase().setAttribute("time_unit", request.getParameter("time_unit"));
            getResourceBase().setAttribute("display_totals", request.getParameter("display_totals"));*/
            getResourceBase().updateAttributesToDB();
        }catch (SWBException swbe) {
            log.error(swbe);
        }
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        Process process = getProcess(suri);
        if ("1".equalsIgnoreCase(getAttribute(suri,"plot")))
            doBars(request, response, paramRequest, process);
        else if ("3".equalsIgnoreCase(getAttribute(suri,"plot")))
            doArea(request, response, paramRequest, process);
        else
            doPie(request, response, paramRequest, process);
    }

    /*public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
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
    }*/

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        out.println("<script type=\"text/javascript\">");
        out.println("   dojo.require(\"dojox.charting.Chart2D\");");
        out.println("   dojo.require(\"dojox.charting.themes.PlotKit.blue\");");
        out.println("   dojo.require(\"dojox.charting.action2d.MoveSlice\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Highlight\");");
        out.println("   makeObjects = function(){");
        out.println("       var chart = new dojox.charting.Chart2D(\"instances\");");
        out.println("       chart.setTheme(dojox.charting.themes.PlotKit.blue);");
        out.println("       chart.addPlot(\"default\", {");
        out.println("           type: \"Pie\",");
        out.println("           font: \"normal normal bold 10pt Tahoma\",");
        out.println("           fontColor: \"white\",");
        out.println("           labelOffset: 40,");
        out.println("           radius: 120");
        out.println("       });");
        out.println("       chart.addSeries(\"CaseResponseTime\", [");
        out.println("           " + getDataPie(process, paramRequest));
        out.println("       ]);");
        out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\")");
        out.println("       var b = new dojox.charting.action2d.Highlight(chart, \"default\", {highlight: \"#6698FF\"});");
        out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
        out.println("       chart.render();");
        out.println("   };");
        out.println("   dojo.addOnLoad(makeObjects);");
        out.println("</script>");
        out.println("<div id=\"instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
    }
    
    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        String data = getData(process);
        out.println("<div id=\"title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='instances' style='width:400px; height:300px;'></div>\n");
        if (data.length() > 2) {
            out.println(Ajax.getChartScript());
            out.println("<script type=\"text/javascript\">\n");
            out.println("    var bargraph = new Grafico.BarGraph($('instances'), " + data + ",\n");
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
        }
        //out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getURI(), "plot_theme"));
        out.println(Ajax.getChartScript());
        out.println("<div id='instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var areagraph = new Grafico.AreaGraph($('instances'), { workload: " + getData(process) + " },");
        out.println("        {");
        out.println("           grid :                false,");
        out.println("           area_opacity :        " + opacity + ",");
        out.println("           plot_padding :        10,");
        out.println("           font_size :           10,");
        out.println("           colors :              { workload: '" + colour + "' },");
        //out.println("           background_color :	  '#EFF5FB',");
        out.println("           label_color :         \"#348781\",");
        if ("1".equalsIgnoreCase(getAttribute(process.getURI(), "display_totals")))
            out.println("           markers :             \"value\",");
        out.println("           meanline :            false,");
        out.println("           draw_axis :           false,\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           datalabels :          {workload: '" + paramRequest.getLocaleString("time") + "'}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        //out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }

    /*private String getTheme() {
        StringBuilder theme = new StringBuilder();
        theme.append("           colorArray:[");
        for (int i=0; i<3; i++)
            theme.append("'" + colours[i] + "',");
        theme.delete(theme.length()-1, theme.length());
        theme.append("],\n");
        return theme.toString();
    }

    private String getHighLight() {
        StringBuilder theme = new StringBuilder();
        theme.append("           highlightColorArray:[");
        for (int i=0; i<3; i++)
            theme.append("'" + highColours[i] + "',");
        theme.delete(theme.length()-1, theme.length());
        theme.append("],\n");
        return theme.toString();
    }*/
    
    private void setPlotTheme(String plot_theme) {
        String[] blueTheme = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
        String[] redTheme = {"#FF0000","#E42217","#E41B17","#F62817","#F62217","#E42217","#F80000","#C80000","#B80000","#900000"};
        String[] greenTheme = {"#4AA02C","#57E964","#59E817","#4CC552","#4CC417","#52D017","#41A317","#3EA99F","#348781","#387C44"};
        if ("1".equalsIgnoreCase(plot_theme)) {
            opacity = "0.4";
            colour = "#3090C7";
            colours = blueTheme;
        }else if ("2".equalsIgnoreCase(plot_theme)) {
            opacity = "0.4";
            colour = "#4CC552";
            colours = greenTheme;
        }else if ("3".equalsIgnoreCase(plot_theme)) {
            opacity = "0.7";
            colour = "#FF0000";
            colours = redTheme;
        }
    }

    private String getData(Process process) {
        StringBuilder data = new StringBuilder();
        CaseResponseTime crt = new CaseResponseTime();
        String time_unit = getAttribute(process.getURI(), "time_unit");
        data.append("[");
        if ("1".equalsIgnoreCase(time_unit))
            data.append(crt.getMinimumProcessInstance(process)/1000 + "," + crt.getAverageProcessInstances(process)/1000 + "," + crt.getMaximumProcessInstance(process)/1000);
        else if ("2".equalsIgnoreCase(time_unit))
            data.append(crt.getMinimumProcessInstance(process)/60000 + "," + crt.getAverageProcessInstances(process)/60000 + "," + crt.getMaximumProcessInstance(process)/60000);
        else if ("3".equalsIgnoreCase(time_unit))
            data.append(crt.getMinimumProcessInstance(process)/3600000 + "," + crt.getAverageProcessInstances(process)/3600000 + "," + crt.getMaximumProcessInstance(process)/3600000);
        else 
            data.append(crt.getMinimumProcessInstance(process)/86400000 + "," + crt.getAverageProcessInstances(process)/86400000 + "," + crt.getMaximumProcessInstance(process)/86400000);
        data.append("]");
        return data.toString();
    }

    private String getDataPie(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder data = new StringBuilder();
        CaseResponseTime crt = new CaseResponseTime();
        String time_unit = getAttribute(process.getURI(), "time_unit");
        String display_totals = getAttribute(process.getURI(), "display_totals");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(time_unit))
            data.append(crt.getMinimumProcessInstance(process)/1000);
        else if ("2".equalsIgnoreCase(time_unit))
            data.append(crt.getMinimumProcessInstance(process)/60000);
        else if ("3".equalsIgnoreCase(time_unit))
            data.append(crt.getMinimumProcessInstance(process)/3600000);
        else
            data.append(crt.getMinimumProcessInstance(process)/86400000);
        data.append(", text: \"" + paramRequest.getLocaleString("minimum") + "\", color: \"" + colours[0] + "\"" + (!"".equalsIgnoreCase(display_totals) ? ", tooltip: " + getToolTips(process, paramRequest, time_unit, "minimum") : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(time_unit))
            data.append(crt.getAverageProcessInstances(process)/1000);
        else if ("2".equalsIgnoreCase(time_unit))
            data.append(crt.getAverageProcessInstances(process)/60000);
        else if ("3".equalsIgnoreCase(time_unit))
            data.append(crt.getAverageProcessInstances(process)/3600000);
        else
            data.append(crt.getAverageProcessInstances(process)/86400000);
        data.append(", text: \"" + paramRequest.getLocaleString("average") + "\", color: \"" + colours[1] + "\"" + (!"".equalsIgnoreCase(display_totals) ? ", tooltip: " + getToolTips(process, paramRequest, time_unit, "average") : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(time_unit))
            data.append(crt.getMaximumProcessInstance(process)/1000);
        else if ("2".equalsIgnoreCase(time_unit))
            data.append(crt.getMaximumProcessInstance(process)/60000);
        else if ("3".equalsIgnoreCase(time_unit))
            data.append(crt.getMaximumProcessInstance(process)/3600000);
        else
            data.append(crt.getMaximumProcessInstance(process)/86400000);
        data.append(", text: \"" + paramRequest.getLocaleString("maximum") + "\", color: \"" + colours[2] + "\"" + (!"".equalsIgnoreCase(display_totals) ? ", tooltip: " + getToolTips(process, paramRequest, time_unit, "maximum") : "") + "}");
        return data.toString();
    }

    private String getLabels(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        StringBuilder minimum = new StringBuilder();
        StringBuilder average = new StringBuilder();
        StringBuilder maximum = new StringBuilder();
        CaseResponseTime crt = new CaseResponseTime();
        String time_unit = getAttribute(process.getURI(), "time_unit");
        String display_totals = getAttribute(process.getURI(), "display_totals");
        if ("1".equalsIgnoreCase(time_unit)) {
            minimum.append("" + crt.getMinimumProcessInstance(process)/1000 + " " + paramRequest.getLocaleString("seconds"));
            average.append("" + crt.getAverageProcessInstances(process)/1000 + " " + paramRequest.getLocaleString("seconds"));
            maximum.append("" + crt.getMaximumProcessInstance(process)/1000 + " " + paramRequest.getLocaleString("seconds"));
        }else if ("2".equalsIgnoreCase(time_unit)) {
            minimum.append("" + crt.getMinimumProcessInstance(process)/60000 + " " + paramRequest.getLocaleString("minutes"));
            average.append("" + crt.getAverageProcessInstances(process)/60000 + " " + paramRequest.getLocaleString("minutes"));
            maximum.append("" + crt.getMaximumProcessInstance(process)/60000 + " " + paramRequest.getLocaleString("minutes"));
        }else if ("3".equalsIgnoreCase(time_unit)) {
            minimum.append("" + crt.getMinimumProcessInstance(process)/3600000 + " " + paramRequest.getLocaleString("hours"));
            average.append("" + crt.getAverageProcessInstances(process)/3600000 + " " + paramRequest.getLocaleString("hours"));
            maximum.append("" + crt.getMaximumProcessInstance(process)/3600000 + " " + paramRequest.getLocaleString("hours"));
        }else {
            minimum.append("" + crt.getMinimumProcessInstance(process)/86400000 + " " + paramRequest.getLocaleString("days"));
            average.append("" + crt.getAverageProcessInstances(process)/86400000 + " " + paramRequest.getLocaleString("days"));
            maximum.append("" + crt.getMaximumProcessInstance(process)/86400000 + " " + paramRequest.getLocaleString("days"));
        }
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + " " + ("1".equalsIgnoreCase(display_totals) ? minimum : "") + "', '" + paramRequest.getLocaleString("average") + " " + ("1".equalsIgnoreCase(display_totals) ? average : "") + "','" + paramRequest.getLocaleString("maximum") + " " + ("1".equalsIgnoreCase(display_totals) ? maximum : "") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getToolTips(Process process, SWBParamRequest paramRequest, String timeUnit, String function) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        StringBuilder total = new StringBuilder();
        CaseResponseTime crt = new CaseResponseTime();
        if ("1".equalsIgnoreCase(timeUnit)) {
            if ("minimum".equalsIgnoreCase(function))
                total.append(" " + crt.getMinimumProcessInstance(process)/1000);
            else if ("average".equalsIgnoreCase(function))
                total.append(" " + crt.getAverageProcessInstances(process)/1000);
            else
                total.append(" " + crt.getMaximumProcessInstance(process)/1000);
            total.append(" " + paramRequest.getLocaleString("seconds"));
        }else if ("2".equalsIgnoreCase(timeUnit)) {
            if ("minimum".equalsIgnoreCase(function))
                total.append(" " + crt.getMinimumProcessInstance(process)/60000);
            else if ("average".equalsIgnoreCase(function))
                total.append(" " + crt.getAverageProcessInstances(process)/60000);
            else
                total.append(" " + crt.getMaximumProcessInstance(process)/60000);
            total.append(" " + paramRequest.getLocaleString("minutes"));
        }else if ("3".equalsIgnoreCase(timeUnit)) {
            if ("minimum".equalsIgnoreCase(function))
                total.append(" " + crt.getMinimumProcessInstance(process)/3600000);
            else if ("average".equalsIgnoreCase(function))
                total.append(" " + crt.getAverageProcessInstances(process)/3600000);
            else
                total.append(" " + crt.getMaximumProcessInstance(process)/3600000);
            total.append(" " + paramRequest.getLocaleString("hours"));
        }else {
            if ("minimum".equalsIgnoreCase(function))
                total.append(" " + crt.getMinimumProcessInstance(process)/86400000);
            else if ("average".equalsIgnoreCase(function))
                total.append(" " + crt.getAverageProcessInstances(process)/86400000);
            else
                total.append(" " + crt.getMaximumProcessInstance(process)/86400000);
            total.append(" " + paramRequest.getLocaleString("days"));
        }
        labels.append("\"" + paramRequest.getLocaleString("time") + " " + paramRequest.getLocaleString(function) + total.toString() + "\"");
        return labels.toString();
    }

    private String getTitles(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + "', '" + paramRequest.getLocaleString("average") + "','" + paramRequest.getLocaleString("maximum") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getProcessTitle(String suri) {
        String title = "";
        if (null != suri) {
            Process process = getProcess(suri);
            if (null != process)
                title = process.getTitle();
        }
        return title;
    }

    private Process getProcess(String suri) {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if (suri.equalsIgnoreCase(process.getURI()))
                    return process;
            }
        }
        return null;
    }

    private String getConfig(String plot, String colours, String time, String totals) {
        StringBuilder config = new StringBuilder();
        config.append(plot).append(colours).append(time);
        if ("1".equals(totals)) config.append(totals); else config.append("0");
        return config.toString();
    }

    private String getAttribute(String suri, String title) {
        String attribute = "";
        String config = getResourceBase().getAttribute(encode(suri), "");
        if ("plot".equals(title)) {
            if (config.length() > 0) attribute = config.substring(0, 1); else attribute = "2";
        }else if ("plot_theme".equals(title)) {
            if (config.length() > 1) attribute = config.substring(1, 2); else attribute = "1";
        }else if ("time_unit".equals(title)) {
            if (config.length() > 2) attribute = config.substring(2, 3); else attribute = "1";
        }else if ("display_totals".equals(title) && config.length() > 3)
            attribute = config.substring(3, 4);
        return attribute;
    }

    private String encode(String suri) {
        return java.net.URLEncoder.encode(suri).replaceAll("%", "100");
    }
}