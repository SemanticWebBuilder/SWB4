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

import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBException;

import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import java.io.PrintWriter;
import java.util.Iterator;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.Process;

/**
 *
 * @author Sergio Téllez
 */
public class SystemCase extends GenericResource {
    private static Logger log = SWBUtils.getLogger(SystemCase.class);
    String theme = "dojox.charting.themes.PlotKit.blue";

    /*@Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if ("add".equals(paramRequest.getAction()) || "edit".equals(paramRequest.getAction()))
            doAdminCase(response, paramRequest);
        else 
            doAdminResume(request, response, paramRequest);
    }*/

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
        out.print("     <fieldset>\n");
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("config")+"</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL edit = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print("    <legend>" + paramRequest.getLocaleString("title") + "</legend>\n");
        doGraph(request, response, paramRequest);
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("    <a href=\"" + edit + "\">" + paramRequest.getLocaleString("config") + "</a>");
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
            log.error(swbe.toString());
        }
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
            //CaseCountSys sys = new CaseCountSys();
            //total = sys.totalProcessInstance();
            //sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
            //processing = sys.totalProcessInstance();
            //sys.clear();
            //sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_CLOSED),null));
            //closed = sys.totalProcessInstance();
            //aborted = total - (closed + processing);
            if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
                doBars(request, response, paramRequest);
            else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
                doArea(request, response, paramRequest);
            else
                doPie(request, response, paramRequest);
    }

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
            PrintWriter out = response.getWriter();
            setPlotTheme();
            out.println("<script type=\"text/javascript\">");
            out.println("   dojo.require(\"dojox.charting.Chart2D\");");
            out.println("   dojo.require(\"" + theme + "\");");
            out.println("   dojo.require(\"dojox.charting.action2d.MoveSlice\");");
            out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
            out.println("   makeObjects = function(){");
            out.println("       var chart = new dojox.charting.Chart2D(\"instances\");");
            out.println("       chart.setTheme(" + theme + ");");
            out.println("       chart.addPlot(\"default\", {");
            out.println("           type: \"Pie\",");
            out.println("           fontColor: \"white\",");
            out.println("           labelOffset: 40,");
            out.println("           radius: 120");
            out.println("       });");
            out.println("       chart.addSeries(\"SystemCaseCount\", [");
            out.println("           " + getDataPie(paramRequest));
            out.println("       ]);");
            out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\");");
            out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
            out.println("       chart.render();");
            out.println("   };");
            out.println("   dojo.addOnLoad(makeObjects);");
            out.println("</script>");
            out.println("<div id=\"instances\" style=\"width: 400px; height: 300px;\"></div>");
            out.println("<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        long lid = System.currentTimeMillis();
        setPlotTheme();
        String elements = getElements().toLowerCase();
        Integer [] _data = getSystemCaseTimeData();
        if (_data != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   dojo.require(\"dojox.charting.Chart2D\");");
            out.println("   dojo.require(\"" + theme + "\");");
            out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
            out.println("   makeObjects = function(){");
            if (elements.contains("totals"))
                out.println("       var chartData = [" + _data[3] +"," + _data[0] + "," + _data[1] +"," + _data[2] + "];");
            else
                out.println("       var chartData = [" + _data[0] +"," + _data[1] + "," + _data[2] + "];");
            out.println("       var chart = new dojox.charting.Chart2D(\""+lid+"_instances\");");
            out.println("       chart.setTheme(" + theme + ");");
            out.println("       chart.addPlot(\"default\", {");
            out.println("           type: \"Columns\",");
            out.println("           markers: true,");
            out.println("           gap: 5");
            out.println("       });");
            out.println("       chart.addAxis(\"x\", {");
            out.println("           labels:[");
            if (elements.contains("totals")) {
                out.println("               {value:1, text:\""+ paramRequest.getLocaleString("totals") +"\"},");
                out.println("               {value:2, text:\""+ paramRequest.getLocaleString("STATUS_PROCESSING") +"\"},");
                out.println("               {value:3, text:\"" + paramRequest.getLocaleString("STATUS_CLOSED") + "\"},");
                out.println("               {value:4, text:\"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\"}");
            } else {
                out.println("               {value:1, text:\""+ paramRequest.getLocaleString("STATUS_PROCESSING") +"\"},");
                out.println("               {value:2, text:\"" + paramRequest.getLocaleString("STATUS_CLOSED") + "\"},");
                out.println("               {value:3, text:\"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\"}");
            }
            out.println("           ],");
            out.println("           rotation: 30,");
            out.println("           majorTicks: true,");
            out.println("           majorLabels: true,");
            out.println("           minorTicks: true,");
            out.println("           minorLabels: true,");
            out.println("       });");
            out.println("       chart.addAxis(\"y\", {vertical:true, min:0, max:" + _data[3] + ", fixLower:\"minor\", fixUpper:\"major\"});");
            out.println("       chart.addSeries(\"CaseCountSys\", chartData);");
            out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
            out.println("       chart.render();");
            out.println("   };");
            out.println("   dojo.addOnLoad(makeObjects);");
            out.println("</script>");
            out.println("<div id=\""+lid+"_instances\" style=\"width: 700px; height: 300px;\"></div>");
            out.println("<div id=\"stage\" style=\"width:700px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
        }
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        long lid = System.currentTimeMillis();
        setPlotTheme();
        String elements = getElements().toLowerCase();
        Integer [] _data = getSystemCaseTimeData();
        if (_data != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   dojo.require(\"dojox.charting.Chart2D\");");
            out.println("   dojo.require(\"" + theme + "\");");
            out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
            out.println("   makeObjects = function(){");
            if (elements.contains("totals"))
                out.println("       var chartData = [" + _data[3] +"," + _data[0] + "," + _data[1] +"," + _data[2] + "];");
            else
                out.println("       var chartData = [" + _data[0] +"," + _data[1] + "," + _data[2] + "];");
            out.println("       var chart = new dojox.charting.Chart2D(\""+lid+"_instances\");");
            out.println("       chart.setTheme(" + theme + ");");
            out.println("       chart.addPlot(\"default\", {");
            out.println("           type: \"StackedAreas\",");
            out.println("           markers: true,");
            out.println("           fontColor: \"white\",");
            out.println("       });");
            out.println("       chart.addAxis(\"x\", {");
            out.println("           labels:[");
            if (elements.contains("totals")) {
                out.println("               {value:1, text:\"" + paramRequest.getLocaleString("totals") + "\"},");
                out.println("               {value:2, text:\""+ paramRequest.getLocaleString("STATUS_PROCESSING") +"\"},");
                out.println("               {value:3, text:\""+ paramRequest.getLocaleString("STATUS_CLOSED") +"\"},");
                out.println("               {value:4, text:\"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\"},");
            } else {
                out.println("               {value:1, text:\""+ paramRequest.getLocaleString("STATUS_PROCESSING") +"\"},");
                out.println("               {value:2, text:\""+ paramRequest.getLocaleString("STATUS_CLOSED") +"\"},");
                out.println("               {value:3, text:\"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\"}");
            }
            out.println("           ],");
            out.println("           majorTicks: true,");
            out.println("           majorLabels: true,");
            out.println("           minorTicks: true,");
            out.println("           minorLabels: true");
            out.println("       });");
            out.println("       chart.addAxis(\"y\", {vertical:true, min:0, max:" + _data[3] + "});");
            out.println("       chart.addSeries(\"SystemCaseCount\", chartData);");
            out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
            out.println("       chart.render();");
            out.println("   };");
            out.println("   dojo.addOnLoad(makeObjects);");
            out.println("</script>");
            out.println("<div id=\""+lid+"_instances\" style=\"width: 700px; height: 300px;\"></div>");
            out.println("<div id=\"stage\" style=\"width:700px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "</label></div>\n");
        }
    }

    private String getElements() {
        String elements = "";
        //ArrayList<String> elements = new ArrayList();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("totals","")))
            elements += "totals|";
            //elements.add("totals");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("processing","")))
            elements += "processing|";
            //elements.add("processing");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("closed","")))
            elements += "closed|";
            //elements.add("closed");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("aborted","")))
            elements += "aborted";
            //elements.add("aborted");
        return elements;
    }

    private String getDataPie(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder ret = new StringBuilder();
        String elements = getElements().toLowerCase();
        Integer[] _data = getSystemCaseTimeData();
        
        if (_data != null) {
            if (elements.isEmpty()) {
                ret.append("{y: " + _data[0] + ", text: \"" + paramRequest.getLocaleString("STATUS_PROCESSING") + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + _data[0] + "\"" : "") + "},");
                ret.append("{y: " + _data[1] + ", text: \"" + paramRequest.getLocaleString("STATUS_CLOSED") + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + _data[1] + "\"" : "") + "},");
                ret.append("{y: " + _data[2] + ", text: \"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + _data[2] + "\"" : "") + "}");
            } else {
                if (elements.contains("totals"))
                    ret.append("{y: " + _data[3] + ", text: \"" + paramRequest.getLocaleString("totals") + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + _data[3] + "\"" : "") + "},");
                if (elements.contains("processing"))
                    ret.append("{y: " + _data[0] + ", text: \"" + paramRequest.getLocaleString("STATUS_PROCESSING") + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + _data[0] + "\"" : "") + "},");
                if (elements.contains("closed"))
                    ret.append("{y: " + _data[1] + ", text: \"" + paramRequest.getLocaleString("STATUS_CLOSED") + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + _data[1] + "\"" : "") + "},");
                if (elements.contains("aborted"))
                    ret.append("{y: " + _data[2] + ", text: \"" + paramRequest.getLocaleString("STATUS_ABORTED") + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: \"" + _data[2] + "\"" : "") + "}");
            }
            //if (ret.length() > 1) ret.delete(ret.length()-1, ret.length());
        }
        return ret.toString();
    }

    private void setPlotTheme() {
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
            theme = "dojox.charting.themes.PlotKit.blue";
        }else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
            theme = "dojox.charting.themes.PlotKit.green";
        }else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
            theme = "dojox.charting.themes.PlotKit.red";
        }
    }

    private Integer[] getSystemCaseTimeData() {
        Integer[] ret = null;
        int c_instances = 0;
        int closed = 0;
        int processing = 0;
        int aborted = 0;
        
        Iterator<Process> processes = Process.ClassMgr.listProcesses();
        while (processes.hasNext()) {
            Process process = processes.next();
            Iterator<ProcessInstance> instances = process.listProcessInstances();
            while(instances.hasNext()) {
                c_instances++;
                ProcessInstance instance = instances.next();
                if (instance.getStatus() == Instance.STATUS_PROCESSING) {
                    processing++;
                }
                if (instance.getStatus() == Instance.STATUS_CLOSED) {
                    closed++;
                }
                if (instance.getStatus() == Instance.STATUS_ABORTED) {
                    aborted++;
                }
            }
        }
        
        ret = new Integer[4];
        ret[0] = processing;
        ret[1] = closed;
        ret[2] = aborted;
        ret[3] = c_instances;
        return ret;
    }
}