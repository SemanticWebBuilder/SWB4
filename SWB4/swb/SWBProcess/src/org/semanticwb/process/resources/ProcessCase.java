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

import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBException;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessSite;

import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.utils.Restriction;

import org.semanticwb.process.kpi.CaseCountSys;
import org.semanticwb.process.kpi.ProcessCaseCount;

import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import java.io.PrintWriter;

/**
 *
 * @author Sergio Téllez
 */
public class ProcessCase extends GenericResource {

    private static Logger log = SWBUtils.getLogger(ProcessCase.class);

    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#1589FF", "#0F79A7", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL edit = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print("    <a href=\"" + edit + "\">" + paramRequest.getLocaleString("config") + "</a>");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("    <legend>" + paramRequest.getLocaleString("PROCESS_INSTANCES") + "</legend>\n");
        doGraph(request, response, paramRequest);
        out.print("  </fieldset>\n");
        out.print("</div>\n");
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.Action_EDIT.equals(paramRequest.getAction()))
            doAdminCase(response, paramRequest);
        else
            doAdminResume(request, response, paramRequest);
    }

    private void doAdminCase(HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        url.setAction("properties");
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <form id=\"case\" name=\"case\" action=" + url.toString() + " method=\"post\">\n");
        out.print("      <fieldset>\n");
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
        out.print("          <legend>" + paramRequest.getLocaleString("title") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"status\" type=\"radio\" name=\"status\" value=\"" + Instance.STATUS_INIT + "\"" + (String.valueOf(Instance.STATUS_INIT).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("totals") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"status\" type=\"radio\" name=\"status\" value=\"" + Instance.STATUS_PROCESSING + "\"" + (String.valueOf(Instance.STATUS_PROCESSING).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("STATUS_PROCESSING") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"status\" type=\"radio\" name=\"status\" value=\"" + Instance.STATUS_CLOSED + "\"" + (String.valueOf(Instance.STATUS_CLOSED).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("STATUS_CLOSED") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"status\" type=\"radio\" name=\"status\" value=\"" + Instance.STATUS_ABORTED + "\"" + (String.valueOf(Instance.STATUS_ABORTED).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("STATUS_ABORTED") + "</td>\n");
        out.print("             </tr>\n");
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
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >"+paramRequest.getLocaleString("apply")+"</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
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
        out.print("          <legend>" + paramRequest.getLocaleString("title") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("             <tr>\n");
        out.print("                  <td>" + (String.valueOf(Instance.STATUS_INIT).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? paramRequest.getLocaleString("totals") : "") + "</td>\n");
        out.print("             </tr>\n");
        out.print("             <tr>\n");
        out.print("                  <td>" + (String.valueOf(Instance.STATUS_PROCESSING).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? paramRequest.getLocaleString("STATUS_PROCESSING") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + (String.valueOf(Instance.STATUS_CLOSED).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? paramRequest.getLocaleString("STATUS_CLOSED") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + (String.valueOf(Instance.STATUS_ABORTED).equalsIgnoreCase(getResourceBase().getAttribute("status","")) ? paramRequest.getLocaleString("STATUS_ABORTED") : "") + "</td>\n");
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
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList<String> elements = new ArrayList();
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
            doBars(request, response, paramRequest, elements);
        else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
            doArea(request, response, paramRequest, elements);
        else
            doPie(request, response, paramRequest);
    }

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
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
        out.println("           font: \"normal normal bold 8pt Tahoma\",");
        out.println("           fontColor: \"white\",");
        out.println("           labelOffset: 20,");
        out.println("           radius: 120");
        out.println("       });");
        out.println("       chart.addSeries(\"CaseResponseTime\", [");
        out.println("           " + getDataPie(paramRequest));
        out.println("       ]);");
        out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\")");
        out.println("       var b = new dojox.charting.action2d.Highlight(chart, \"default\", {highlight: \"#6698FF\"});");
        out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
        out.println("       chart.render();");
        out.println("   };");
        out.println("   dojo.addOnLoad(makeObjects);");
        out.println("</script>");
        out.println("<div id=\"instances\" style=\"width: 400px; height: 300px;\"></div>");
        //out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("PROCESS_INSTANCES") + " " + getStatus(paramRequest) + "</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, ArrayList<String> elements) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        String data = getData(elements, paramRequest);
        out.println("<div id='instances' style='width:450px; height:300px;'></div>\n");
        if (data.length() > 2) {
            out.println(Ajax.getChartScript());
            out.println("<script type=\"text/javascript\">\n");
            out.println("    var bargraph = new Grafico.BarGraph($('instances'), " + data + ",\n");
            out.println("        {\n");
            //out.println("           labels :			  " + getTitles(elements) + ",\n");
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
            out.println("           datalabels :          {one: " + getLabels(elements) + "}\n");
            out.println("        }\n");
            out.println("    );\n");
            out.println("</script>\n");
        }
        //out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("PROCESS_INSTANCES") + " " + getStatus(paramRequest) + "</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, ArrayList<String> elements) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        String data = getData(elements, paramRequest);
        out.println("<div id='instances' style='width:450px; height:300px;'></div>\n");
        if (data.length() > 2) {
            out.println(Ajax.getChartScript());
            out.println("<script type=\"text/javascript\">\n");
            out.println("    var areagraph = new Grafico.AreaGraph($('instances'), { workload: " + data + " },");
            out.println("        {");
            out.println("           grid :                false,");
            out.println("           area_opacity :        " + opacity + ",");
            out.println("           plot_padding :        50,");
            out.println("           font_size :           10,");
            out.println("           colors :              { workload: '" + colour + "' },");
            //out.println("           background_color :	  '#EFF5FB',");
            out.println("           label_color :         \"#348781\",");
            out.println("           label_rotation :      -10,");
            if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")))
                out.println("           markers :             \"value\",");
            out.println("           meanline :            false,");
            out.println("           draw_axis :           false,\n");
            out.println("           labels :			  " +  getTitles(elements) + ",\n");
            out.println("           datalabels :          {workload: '" + paramRequest.getLocaleString("instances") + "'}\n");
            out.println("        }\n");
            out.println("    );\n");
            out.println("</script>\n");
        }
        //out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("PROCESS_INSTANCES") + " " + getStatus(paramRequest) + "</label></div>\n");
    }

    private void updateAttributes(HttpServletRequest request) {
        try {
            getResourceBase().setAttribute("plot", request.getParameter("plot"));
            getResourceBase().setAttribute("status", request.getParameter("status"));
            getResourceBase().setAttribute("plot_theme", request.getParameter("plot_theme"));
            getResourceBase().setAttribute("display_totals", request.getParameter("display_totals"));
            getResourceBase().updateAttributesToDB();
        }catch (SWBException swbe) {
            log.error(swbe);
        }
    }

    private String getData(ArrayList<String> elements, SWBParamRequest paramRequest) {
        int total = 0;
        StringBuilder label = null;
        StringBuilder data = new StringBuilder();
        data.append("[");
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> it = site.listProcesses();
            while (it.hasNext()) {
                label = new StringBuilder();
                Process process = it.next();
                ProcessCaseCount pcc = new ProcessCaseCount(process.getURI());
                if (String.valueOf(Instance.STATUS_PROCESSING).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
                    pcc.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
                else if (String.valueOf(Instance.STATUS_CLOSED).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
                    pcc.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_CLOSED),null));
                else if (String.valueOf(Instance.STATUS_ABORTED).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
                    pcc.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_ABORTED),null));
                total = pcc.totalProcessInstance();
                if (total > 0) {
                    if (null != process.getTitle(paramRequest.getUser().getLanguage()))
                        label.append(process.getTitle(paramRequest.getUser().getLanguage()));
                    else
                        label.append(process.getTitle());
                    if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")))
                        label.append(" " + total);
                    elements.add(label.toString());
                    data.append(total + ",");
                }
            }
        }
        if (data.length() > 1) data.delete(data.length()-1, data.length());
        data.append("]");
        return data.toString();
    }

    private String getDataPie(SWBParamRequest paramRequest) throws SWBResourceException{
        int i = 0;
        int total = 0;
        StringBuilder label = new StringBuilder();
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> it = site.listProcesses();
            while (it.hasNext()) {
                Process process = it.next();
                ProcessCaseCount pcc = new ProcessCaseCount(process.getURI());
                if (String.valueOf(Instance.STATUS_PROCESSING).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
                    pcc.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
                else if (String.valueOf(Instance.STATUS_CLOSED).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
                    pcc.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_CLOSED),null));
                else if (String.valueOf(Instance.STATUS_ABORTED).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
                    pcc.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_ABORTED),null));
                total = pcc.totalProcessInstance();
                if (total > 0) {
                    label.append("{y: " + total + ", text: \"" + process.getTitle() + "\", color: \"" + colours[getDigit(i)] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + getToolTips(paramRequest, total) + "\"" : "") + "},");
                    i++;
                }
            }
        }
        if (label.length() > 1) label.delete(label.length()-1, label.length());
        return label.toString();
    }

    private String getToolTips(SWBParamRequest paramRequest, int total) throws SWBResourceException{
        StringBuilder labels = new StringBuilder();
        labels.append(paramRequest.getLocaleString("instances") + " " + total);
        return labels.toString();
    }

    private String getLabels(ArrayList<String> elements) throws SWBResourceException{
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        for (int i=0; i<elements.size(); i++) {
            labels.append("'" + elements.get(i) + "',");
        }
        if (labels.length() > 1) labels.delete(labels.length()-1, labels.length());
        labels.append("]");
        return labels.toString();
    }

    private String getTitles(ArrayList<String> elements) throws SWBResourceException{
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        for (int i=0; i<elements.size(); i++) {
            if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals",""))) {
                String label = (String)elements.get(i);
                int index = label.lastIndexOf(" ");
                labels.append("'" + label.substring(0, index) + "',");
            }else
                labels.append("'" + elements.get(i) + "',");
        }
        if (labels.length() > 1) labels.delete(labels.length()-1, labels.length());
        labels.append("]");
        return labels.toString();
    }

    public int getDigit(int size) {
        if (size < 10)
            return size;
        else
            return getDigit(size-10);
    }

    private String getTheme(int size) {
        StringBuilder theme = new StringBuilder();
        theme.append("           colorArray:[");
        if (size > 0) {
            for (int i=0; i<size; i++)
                theme.append("'" + colours[getDigit(i)] + "',");
            theme.delete(theme.length()-1, theme.length());
        }
        theme.append("],\n");
        return theme.toString();
    }

    private String getHighLight(int size) {
        StringBuilder theme = new StringBuilder();
        theme.append("           highlightColorArray:[");
        if (size > 0) {
            for (int i=0; i<size; i++)
                theme.append("'" + highColours[getDigit(i)] + "',");
            theme.delete(theme.length()-1, theme.length());
        }
        theme.append("],\n");
        return theme.toString();
    }

    private void setPlotTheme() {
        String[] blueTheme = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
        String[] redTheme = {"#FF0000","#E42217","#E41B17","#F62817","#F62217","#E42217","#F80000","#C80000","#B80000","#900000"};
        String[] greenTheme = {"#4AA02C","#57E964","#59E817","#4CC552","#4CC417","#52D017","#41A317","#3EA99F","#348781","#387C44"};
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
    }

    /*private String getStatus(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder status = new StringBuilder();
        if (String.valueOf(Instance.STATUS_INIT).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
            status.append(paramRequest.getLocaleString("totals"));
        else if (String.valueOf(Instance.STATUS_PROCESSING).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
            status.append(paramRequest.getLocaleString("STATUS_PROCESSING"));
        else if (String.valueOf(Instance.STATUS_CLOSED).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
            status.append(paramRequest.getLocaleString("STATUS_CLOSED"));
        else if (String.valueOf(Instance.STATUS_ABORTED).equalsIgnoreCase(getResourceBase().getAttribute("status","")))
            status.append(paramRequest.getLocaleString("STATUS_ABORTED"));
        return status.toString();
    }

    public void doChart(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, ArrayList<String> elements) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/swbadmin/jsp/charts/jsxgraph.css\" />\n");
        out.println("<script type=\"text/javascript\" src=\"/swbadmin/jsp/charts/jsxgraphcore.js\"></script>\n");
        out.println("<div id=\"instances\" style=\"width:400px; height:300px;\"></div>\n");
    	out.println("<script type=\"text/javascript\">\n");
        out.println("   var dataArr = " + getData(elements, paramRequest) + ";\n");
        out.println("   var board = JXG.JSXGraph.initBoard('instances', {showNavigation:false, showCopyright:false, originX: 50, originY: 210, unitX: 30, unitY: 30});\n");
        out.println("   board.suspendUpdate();\n");
        out.println("   var a = board.createElement('chart', dataArr,\n");
		out.println("       {\n");
		out.println("           chartStyle:'pie',\n");
        out.println(getTheme(elements.size()));
        out.println("           fillOpacity:" + opacity + ", center:[5,2], strokeColor:'white', highlightStrokeColor:'white', strokeWidth:1,\n");
		out.println("           labelArray:" + getLabels(elements) + ",\n");
        out.println(getHighLight(elements.size()));
        out.println("           highlightOnSector:true,\n");
		out.println("           highlightBySize:true\n");
		out.println("       }\n");
        out.println("   );\n");
        out.println("   board.unsuspendUpdate();\n");
        out.println("</script>\n");
        out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }*/
}