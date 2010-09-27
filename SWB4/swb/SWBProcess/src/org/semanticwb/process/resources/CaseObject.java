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
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;

import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.process.kpi.CaseProcessInstance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.SWBException;
import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.kpi.CaseProcessObject;

/**
 *
 * @author Sergio Téllez
 */
public class CaseObject extends GenericResource {

    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#92C2DF", "#BDDDE4", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if ("add".equals(paramRequest.getAction()) || "edit".equals(paramRequest.getAction()))
            doAdminCase(request, response, paramRequest);
        else
            doAdminResume(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        response.getWriter().print("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        response.getWriter().println("  <ul>");
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator <org.semanticwb.process.model.ProcessWebPage>itProcessWebPages = ProcessWebPage.ClassMgr.listProcessWebPages(site);
            while(itProcessWebPages.hasNext()) {
                ProcessWebPage pwp = (ProcessWebPage)itProcessWebPages.next();
                org.semanticwb.process.model.Process process = SWBProcessMgr.getProcess(pwp);
                if("Soporte Técnico".equals(process.getTitle())) {
                    response.getWriter().println("<li>Total presupuesto de incidente: " + Ajax.notNull(CaseProcessObject.sum(process, "Incidente", "budget")) + "</li>");
                    response.getWriter().println("<li>Presupuesto promedio de incidente: " + CaseProcessObject.average(process, "Incidente", "budget") + "</li>");
                    response.getWriter().println("<li>Presupuesto máximo de incidente: " + Ajax.notNull(CaseProcessObject.maximum(process, "Incidente", "budget")) + "</li>");
                    response.getWriter().println("<li>Presupuesto mínimo de incidente: " + Ajax.notNull(CaseProcessObject.minimum(process, "Incidente", "budget")) + "</li>");
                    response.getWriter().println("<li>Presupuestos distintos de incidente: " + CaseProcessObject.distincts(process, "Incidente", "budget").size() + "</li>");
                }
            }
        }
        response.getWriter().println("      </ul>");
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().print("</div>\n");*/
        response.getWriter().println("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        doGraph(request, response, paramRequest);
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().println("</div>\n");
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
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

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
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
        out.println("<div id=\"title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id=\"instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("object_property","")) ? getObjectTitle(paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println(Ajax.getChartScript());
        out.println("<div id=\"title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var bargraph = new Grafico.BarGraph($('instances'), " + getData(process) + ",\n");
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
        out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("object_property","")) ? getObjectTitle(paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println(Ajax.getChartScript());
        out.println("<div id=\"title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
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
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")))
            out.println("           markers :             \"value\",");
        out.println("           meanline :            false,");
        out.println("           draw_axis :           false,\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           datalabels :          {workload: '" + getResourceBase().getAttribute("measurement_unit","") + "'}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("object_property","")) ? getObjectTitle(paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</label></div>\n");
    }

    public void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(paramRequest.Mode_ADMIN);
        url.setAction("edit");
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
        out.print(paramRequest.getLocaleString("CASE_OBJECT"));
        out.print("  </fieldset>\n");
        out.print("  <form id=\"case\" name=\"case\" action=" + url.toString() + " method=\"post\">\n");
        out.print("      <fieldset>\n");
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
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("OBJECT_PROPERTY") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"object_property\" name=\"object_property\">\n");
        ProcessInstance pinst = getProcessInstance(getResourceBase().getAttribute("process",""));
        if (null != pinst)
            selectObjectProperty(pinst, out, paramRequest);
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
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
        out.print("          <legend>" + paramRequest.getLocaleString("MEASUREMENT_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"measurement_unit\" type=\"text\" name=\"measurement_unit\" value=\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("measurement_unit","")) ? getResourceBase().getAttribute("measurement_unit") : "") + "\"></td>\n");
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
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("CASE_OBJECT"));
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
        out.print("     <legend>" + paramRequest.getLocaleString("OBJECT_PROPERTY") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("object_property","")) ? getObjectTitle(paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
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
        out.print("          <legend>" + paramRequest.getLocaleString("MEASUREMENT_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("measurement_unit","")) ? getResourceBase().getAttribute("measurement_unit","") : "") + "</td>\n");
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
        out.print("</div>\n");
    }

    private void updateAttributes(HttpServletRequest request) {
        try {
            if (null != request.getParameter("process")) {
                getResourceBase().setAttribute("process", request.getParameter("process"));
                getResourceBase().setAttribute("object_property", request.getParameter("object_property"));
                setProcessObject(request.getParameter("object_property"));
                getResourceBase().setAttribute("plot", request.getParameter("plot"));
                getResourceBase().setAttribute("plot_theme", request.getParameter("plot_theme"));
                getResourceBase().setAttribute("measurement_unit", request.getParameter("measurement_unit"));
                getResourceBase().setAttribute("display_totals", request.getParameter("display_totals"));
                getResourceBase().updateAttributesToDB();
            }
        }catch (SWBException swbe) {
            swbe.printStackTrace();
        }
    }

    private ProcessInstance getProcessInstance(String processId) {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if (processId.equalsIgnoreCase(process.getId()))
                    return org.semanticwb.process.kpi.CaseProcessInstance.pop(process);
            }
        }
        return null;
    }

    private String getProcessTitle(String processId) {
        Process process = getProcess(processId);
        if (null != process)
            return process.getTitle();
        else
            return "";
    }

    private void selectObjectProperty(ProcessInstance pinst, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList pobjs = new ArrayList();
        getObjectsFromInstance(pinst, pobjs);
        Iterator<ProcessObject> objit = pobjs.iterator();
        while(objit.hasNext()) {
            ProcessObject pobj =  objit.next();
            Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
            while(spit.hasNext()) {
                SemanticProperty sp = spit.next();
                out.print("                  <option value=\"" +  sp.getPropId() + "\"" + (getResourceBase().getAttribute("object_property","").equalsIgnoreCase(sp.getPropId()) ? " selected" : "") + " >" + getTitle(sp,paramRequest) +  "</option>\n");
            }
        }
    }

    private void setProcessObject(String propId) {
        ArrayList pobjs = new ArrayList();
        Process process = getProcess(getResourceBase().getAttribute("process", ""));
        ProcessInstance pinst = CaseProcessInstance.pop(process);
        if (null != propId && !"".equals(propId)) {
            getObjectsFromInstance(pinst, pobjs);
            Iterator<ProcessObject> objit = pobjs.iterator();
            while(objit.hasNext()) {
                ProcessObject pobj =  objit.next();
                Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (propId.equalsIgnoreCase(sp.getPropId()))
                        getResourceBase().setAttribute("process_object", pobj.getSemanticObject().getSemanticClass().getName());
                }
            }
        }
    }
    
    private Process getProcess(String processId) {
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
    }

    private void getObjectsFromInstance(ProcessInstance pinst, ArrayList pobjs) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if (!pobjs.contains(obj))
                pobjs.add(obj);
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                getObjectsFromInstance((SubProcessInstance)flobin, pobjs);
        }
    }

    private void getObjectsFromInstance(SubProcessInstance spinst, ArrayList pobjs) {
        Iterator<ProcessObject> objit = spinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if (!pobjs.contains(obj))
                pobjs.add(obj);
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                getObjectsFromInstance((SubProcessInstance)flobin, pobjs);
        }
    }

    private void setPlotTheme() {
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
    }

    private String getData(Process process) {
        Object minimum = null;
        Object average = null;
        Object maximum = null;
        StringBuilder data = new StringBuilder();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("object_property", ""))) {
            minimum = Ajax.notNull(CaseProcessObject.minimum(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property")), "0.0");
            average = Ajax.notNull(CaseProcessObject.average(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property")), "0.0");
            maximum = Ajax.notNull(CaseProcessObject.maximum(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property")), "0.0");
        }else
            minimum = average = maximum = 0;
        data.append("[" + minimum + "," + average + "," + maximum + "]");
        return data.toString();
    }

    private String getTitles(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + "', '" + paramRequest.getLocaleString("average") + "','" + paramRequest.getLocaleString("maximum") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getDataPie(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        Object minimum = null;
        Object average = null;
        Object maximum = null;
        StringBuilder data = new StringBuilder();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("object_property", ""))) {
            minimum = CaseProcessObject.minimum(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property"));
            average = CaseProcessObject.average(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property"));
            maximum = CaseProcessObject.maximum(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property"));
        }else
            minimum = average = maximum = 0;
        data.append("{y: " + minimum);
        data.append(", text: \"" + paramRequest.getLocaleString("minimum") + "\", color: \"" + colours[0] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: " + getToolTips(paramRequest, "minimum", minimum) : "") + "},");
        data.append("{y: " + average);
        data.append(", text: \"" + paramRequest.getLocaleString("average") + "\", color: \"" + colours[1] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: " + getToolTips(paramRequest, "average", average) : "") + "},");
        data.append("{y: " + maximum);
        data.append(", text: \"" + paramRequest.getLocaleString("maximum") + "\", color: \"" + colours[2] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: " + getToolTips(paramRequest, "maximum", maximum) : "") + "}");
        return data.toString();
    }

    private String getLabels(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        Object min = null;
        Object avg = null;
        Object max = null;
        StringBuilder labels = new StringBuilder();
        StringBuilder minimum = new StringBuilder();
        StringBuilder average = new StringBuilder();
        StringBuilder maximum = new StringBuilder();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("object_property", ""))) {
            min = Ajax.notNull(CaseProcessObject.minimum(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property")), "0.0");
            avg = Ajax.notNull(CaseProcessObject.average(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property")), "0.0");
            max = Ajax.notNull(CaseProcessObject.maximum(process, getResourceBase().getAttribute("process_object"), getResourceBase().getAttribute("object_property")), "0.0");
        }else
            min = avg = max = 0;
        minimum.append("" + min + " " + getResourceBase().getAttribute("measurement_unit"));
        average.append("" + avg + " " + getResourceBase().getAttribute("measurement_unit"));
        maximum.append("" + max + " " + getResourceBase().getAttribute("measurement_unit"));
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? minimum : "") + "', '" + paramRequest.getLocaleString("average") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? average : "") + "','" + paramRequest.getLocaleString("maximum") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? maximum : "") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getToolTips(SWBParamRequest paramRequest, String function, Object value) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        StringBuilder total = new StringBuilder();
        total.append(" " + value);
        total.append(" " + getResourceBase().getAttribute("measurement_unit", ""));
        labels.append("\"" + getObjectTitle(paramRequest) + " " + paramRequest.getLocaleString(function) + total.toString() + "\"");
        return labels.toString();
    }

    private String getObjectTitle(SWBParamRequest paramRequest) {
        ArrayList pobjs = new ArrayList();
        Process process = getProcess(getResourceBase().getAttribute("process", ""));
        ProcessInstance pinst = CaseProcessInstance.pop(process);
        getObjectsFromInstance(pinst, pobjs);
        StringBuilder propertyname = new StringBuilder();
        Iterator<ProcessObject> objit = pobjs.iterator();
        while(objit.hasNext()) {
            ProcessObject pobj =  objit.next();
            Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
            while(spit.hasNext()) {
                SemanticProperty sp = spit.next();
                if (getResourceBase().getAttribute("object_property","").equalsIgnoreCase(sp.getPropId()))
                    propertyname.append(getTitle(sp, paramRequest));
            }
        }
        return propertyname.toString();
    }

    private String getTitle(SemanticProperty sp, SWBParamRequest paramRequest) {
        if (null != sp) {
            if (null != sp.getDisplayName(paramRequest.getUser().getLanguage()))
                return sp.getDisplayName(paramRequest.getUser().getLanguage());
            else
                return sp.getName();
        }else
            return "";
    }
}
