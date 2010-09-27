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

import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBException;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.kpi.CaseCountSys;

import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.utils.Restriction;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import java.io.PrintWriter;

/**
 *
 * @author Sergio Téllez
 */
public class SystemCase extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SystemCase.class);

    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#92C2DF", "#BDDDE4", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if ("add".equals(paramRequest.getAction()) || "edit".equals(paramRequest.getAction()))
            doAdminCase(response, paramRequest);
        else 
            doAdminResume(request, response, paramRequest);
    }

    private void doAdminCase(HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(paramRequest.Mode_ADMIN);
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
        out.print("                  <td><input id=\"totals\" type=\"checkbox\" name=\"totals\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("totals","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("totals") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"processing\" type=\"checkbox\" name=\"processing\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("processing","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("STATUS_PROCESSING") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"closed\" type=\"checkbox\" name=\"closed\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("closed","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("STATUS_CLOSED") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"aborted\" type=\"checkbox\" name=\"aborted\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("aborted","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("STATUS_ABORTED") + "</td>\n");
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
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
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
        if ("".equals(getResourceBase().getAttribute("totals","")) && "".equals(getResourceBase().getAttribute("processing","")) && "".equals(getResourceBase().getAttribute("closed","")) && "".equals(getResourceBase().getAttribute("aborted",""))) {
            out.print("              <tr>\n");
            out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
            out.print("              </tr>\n");
        }else {
            out.print("              <tr>\n");
            out.print("                  <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("totals","")) ? paramRequest.getLocaleString("totals") : "") + "</td>\n");
            out.print("              </tr>\n");
            out.print("              <tr>\n");
            out.print("                  <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("processing","")) ? paramRequest.getLocaleString("STATUS_PROCESSING") : "") + "</td>\n");
            out.print("              </tr>\n");
            out.print("              <tr>\n");
            out.print("                  <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("closed","")) ? paramRequest.getLocaleString("STATUS_CLOSED") : "") + "</td>\n");
            out.print("              </tr>\n");
            out.print("              <tr>\n");
            out.print("                  <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("aborted","")) ? paramRequest.getLocaleString("STATUS_ABORTED") : "") + "</td>\n");
            out.print("             </tr>\n");
        }
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
        out.print("</div>\n");
    }

    private void updateAttributes(HttpServletRequest request) {
        try {
            getResourceBase().setAttribute("plot", request.getParameter("plot"));
            getResourceBase().setAttribute("totals", request.getParameter("totals"));
            getResourceBase().setAttribute("processing", request.getParameter("processing"));
            getResourceBase().setAttribute("closed", request.getParameter("closed"));
            getResourceBase().setAttribute("aborted", request.getParameter("aborted"));
            getResourceBase().setAttribute("plot_theme", request.getParameter("plot_theme"));
            getResourceBase().setAttribute("display_totals", request.getParameter("display_totals"));
            getResourceBase().updateAttributesToDB();
        }catch (SWBException swbe) {
            swbe.printStackTrace();
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*CaseCountSys sys = new CaseCountSys();
        response.getWriter().println("<div class=\"swbform\">\n");
        response.getWriter().println("  <fieldset>\n");
        response.getWriter().println("      <ul><li>Número total de instancias de procesos: " + sys.totalProcessInstance()+"</li>");
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
        response.getWriter().println("      <li>Número total de instancias de procesos en ejecución: " + sys.totalProcessInstance()+"</li>");
        sys.clear();
        sys.addRestriction(new Restriction(CaseCountSys.USER,"admin",null));
        response.getWriter().println("     <li>Número total de instancias de procesos del usuario admin: " + sys.totalProcessInstance()+"</li></ul>");
        response.getWriter().println("  </fieldset>\n");
        response.getWriter().println("</div>\n");*/
        response.getWriter().print("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        doGraph(request, response, paramRequest);
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().println("</div>\n");
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList<String> elements = getElements();
        int total, processing, closed, aborted = 0;
        CaseCountSys sys = new CaseCountSys();
        total = sys.totalProcessInstance();
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
        processing = sys.totalProcessInstance();
        sys.clear();
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_CLOSED),null));
        closed = sys.totalProcessInstance();
        aborted = total - (closed + processing);
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
            doBars(request, response, paramRequest, elements, total, processing, closed, aborted);
        else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
            doArea(request, response, paramRequest, elements, total, processing, closed, aborted);
        else
            doPie(request, response, paramRequest, elements, total, processing, closed, aborted);
    }

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, ArrayList<String> elements, int total, int processing, int closed, int aborted) throws SWBResourceException, IOException {
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
        out.println("       chart.addSeries(\"CaseResponseTime\", [");
        out.println("           " + getDataPie(total, processing, closed, aborted, elements, paramRequest));
        out.println("       ]);");
        out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\")");
        out.println("       var b = new dojox.charting.action2d.Highlight(chart, \"default\", {highlight: \"#6698FF\"});");
        out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
        out.println("       chart.render();");
        out.println("   };");
        out.println("   dojo.addOnLoad(makeObjects);");
        out.println("</script>");
        out.println("<div id=\"instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, ArrayList<String> elements, int total, int processing, int closed, int aborted) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println(Ajax.getChartScript());
        out.println("<div id='instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var bargraph = new Grafico.BarGraph($('instances'), " + getData(total, processing, closed, aborted, elements) + ",\n");
        out.println("        {\n");
        out.println("           labels :			  " + getTitles(elements, paramRequest) + ",\n");
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
        out.println("           datalabels :          {one: " + getLabels(total, processing, closed, aborted, elements, paramRequest) + "}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, ArrayList<String> elements, int total, int processing, int closed, int aborted) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println(Ajax.getChartScript());
        out.println("<div id='instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var areagraph = new Grafico.AreaGraph($('instances'), { workload: " + getData(total, processing, closed, aborted, elements) + " },");
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
        out.println("           labels :			  " + getTitles(elements, paramRequest) + ",\n");
        out.println("           datalabels :          {workload: '" + paramRequest.getLocaleString("instances") + "'}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }

    private ArrayList<String> getElements() {
        ArrayList<String> elements = new ArrayList();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("totals","")))
            elements.add("totals");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("processing","")))
            elements.add("processing");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("closed","")))
            elements.add("closed");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("aborted","")))
            elements.add("aborted");
        return elements;
    }

    private String getData(int total, int processing, int closed, int aborted, ArrayList<String> elements) {
        StringBuilder data = new StringBuilder();
        data.append("[");
        if (elements.size() == 0)
            data.append(processing + "," + closed + "," + aborted);
        else {
            if (elements.contains("totals"))
                data.append(total + ",");
            if (elements.contains("processing"))
                data.append(processing + ",");
            if (elements.contains("closed"))
                data.append(closed + ",");
            if (elements.contains("aborted"))
                data.append(aborted + ",");
            data.delete(data.length()-1, data.length());
        }
        data.append("]");
        return data.toString();
    }

    private String getDataPie(int total, int processing, int closed, int aborted, ArrayList<String> elements, SWBParamRequest paramRequest) throws SWBResourceException{
        StringBuilder label = new StringBuilder();
        if (elements.size() == 0) {
            label.append("{y: " + processing + ", text: \"" + paramRequest.getLocaleString("STATUS_PROCESSING") + "\", color: \"" + colours[0] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + processing + "\"" : "") + "},");
            label.append("{y: " + closed + ", text: \"" + paramRequest.getLocaleString("STATUS_CLOSED") + "\", color: \"" + colours[1] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + closed + "\"" : "") + "},");
            label.append("{y: " + aborted + ", text: \"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\", color: \"" + colours[2] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + aborted + "\"" : "") + "},");
        }else {
            if (elements.contains("totals"))
                label.append("{y: " + total + ", text: \"" + paramRequest.getLocaleString("totals") + "\", color: \"" + colours[0] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + total + "\"" : "") + "},");
            if (elements.contains("processing"))
                label.append("{y: " + processing + ", text: \"" + paramRequest.getLocaleString("STATUS_PROCESSING") + "\", color: \"" + colours[1] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + processing + "\"" : "") + "},");
            if (elements.contains("closed"))
                label.append("{y: " + closed + ", text: \"" + paramRequest.getLocaleString("STATUS_CLOSED") + "\", color: \"" + colours[2] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + closed + "\"" : "") + "},");
            if (elements.contains("aborted"))
                label.append("{y: " + aborted + ", text: \"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\", color: \"" + colours[2] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + aborted + "\"" : "") + "},");
        }
        if (label.length() > 1) label.delete(label.length()-1, label.length());
        return label.toString();
    }

    private String getLabels(int totals, int processing, int closed, int aborted, ArrayList<String> elements, SWBParamRequest paramRequest) throws SWBResourceException{
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        if (elements.size() == 0)
            labels.append("'" + paramRequest.getLocaleString("STATUS_PROCESSING") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? processing : "") + "', '" + paramRequest.getLocaleString("STATUS_CLOSED") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? closed : "") + "','" + paramRequest.getLocaleString("STATUS_ABORTED") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? aborted : "") + "'");
        else {
            if (elements.contains("totals"))
                labels.append("'" + paramRequest.getLocaleString("totals") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? totals : "") + "',");
            if (elements.contains("processing"))
                labels.append("'" + paramRequest.getLocaleString("STATUS_PROCESSING") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? processing : "") + "',");
            if (elements.contains("closed"))
                labels.append("'" + paramRequest.getLocaleString("STATUS_CLOSED") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? closed : "") + "',");
            if (elements.contains("aborted"))
                labels.append("'" + paramRequest.getLocaleString("STATUS_ABORTED") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? aborted : "") + "',");
            labels.delete(labels.length()-1, labels.length());
        }
        if (labels.length() > 1) labels.append("]");
        return labels.toString();
    }

    private String getTitles(ArrayList<String> elements, SWBParamRequest paramRequest) throws SWBResourceException{
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        if (elements.size() == 0)
            labels.append("'" + paramRequest.getLocaleString("STATUS_PROCESSING") + "', '" + paramRequest.getLocaleString("STATUS_CLOSED") + "','" + paramRequest.getLocaleString("STATUS_ABORTED") + "'");
        else {
            if (elements.contains("totals"))
                labels.append("'" + paramRequest.getLocaleString("totals") + "',");
            if (elements.contains("processing"))
                labels.append("'" + paramRequest.getLocaleString("STATUS_PROCESSING") + "',");
            if (elements.contains("closed"))
                labels.append("'" + paramRequest.getLocaleString("STATUS_CLOSED") + "',");
            if (elements.contains("aborted"))
                labels.append("'" + paramRequest.getLocaleString("STATUS_ABORTED") + "',");
            labels.delete(labels.length()-1, labels.length());
        }
        labels.append("]");
        return labels.toString();
    }

    private String getTheme(int size) {
        StringBuilder theme = new StringBuilder();
        theme.append("           colorArray:[");
        if (size == 0) {
            for (int i=0; i<3; i++)
                theme.append("'" + colours[i] + "',");
        }else {
            for (int i=0; i<size; i++)
                theme.append("'" + colours[i] + "',");
        }
        theme.delete(theme.length()-1, theme.length());
        theme.append("],\n");
        return theme.toString();
    }

    private String getHighLight(int size) {
        StringBuilder theme = new StringBuilder();
        theme.append("           highlightColorArray:[");
        if (size == 0)
            theme.append("'#E46F6A','#F9DF82','#F7FA7B'");
        else {
            for (int i=0; i<size; i++)
                theme.append("'" + highColours[i] + "',");
            theme.delete(theme.length()-1, theme.length());
        }
        theme.append("],\n");
        return theme.toString();
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

    public void doChart(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, ArrayList<String> elements, int total, int processing, int closed, int aborted) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/swbadmin/jsp/charts/jsxgraph.css\" />\n");
        out.println("<script type=\"text/javascript\" src=\"/swbadmin/jsp/charts/jsxgraphcore.js\"></script>\n");
        out.println("<div id=\"instances\" style=\"width:400px; height:300px;\"></div>\n");
    	out.println("<script type=\"text/javascript\">\n");
        out.println("   var dataArr = " + getData(total, processing, closed, aborted, elements) + ";\n");
        out.println("   var board = JXG.JSXGraph.initBoard('instances', {showNavigation:false, showCopyright:false, originX: 50, originY: 210, unitX: 30, unitY: 30});\n");
        out.println("   board.suspendUpdate();\n");
        out.println("   var a = board.createElement('chart', dataArr,\n");
		out.println("       {\n");
		out.println("           chartStyle:'pie',\n");
        out.println(getTheme(elements.size()));
        out.println("           fillOpacity:" + opacity + ", center:[5,2], strokeColor:'white', highlightStrokeColor:'white', strokeWidth:1,\n");
		out.println("           labelArray:" + getLabels(total, processing, closed, aborted, elements, paramRequest) + ",\n");
        out.println(getHighLight(elements.size()));
        out.println("           highlightOnSector:true,\n");
		out.println("           highlightBySize:true\n");
		out.println("       }\n");
        out.println("   );\n");
        out.println("   board.unsuspendUpdate();\n");
        out.println("</script>\n");
        out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }
}