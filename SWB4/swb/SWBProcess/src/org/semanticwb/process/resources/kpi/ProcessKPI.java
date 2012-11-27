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
package org.semanticwb.process.resources.kpi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.FlowNode;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.Task;
import org.semanticwb.process.model.WrapperProcessWebPage;

public class ProcessKPI extends org.semanticwb.process.resources.kpi.base.ProcessKPIBase 
{
    private static Logger log=SWBUtils.getLogger(ProcessKPI.class);
    public static final int TIMEUNIT_SECONDS=1;
    public static final int TIMEUNIT_MINUTES=2;
    public static final int TIMEUNIT_HOURS=3;
    public static final int TYPE_BARS=1;
    public static final int TYPE_AREA=2;
    public static final int TYPE_PIE=3;
    public static final int THEME_BLUE=1;
    public static final int THEME_RED=2;
    public static final int THEME_GREEN=3;
    public static final int PERIOD_LASTWEEK=1;
    public static final int PERIOD_LASTMONTH=2;
    public static final int PERIOD_LASTYEAR=3;
    String theme = "dojox.charting.themes.PlotKit.blue";
    
    public ProcessKPI() {}

   /**
   * Constructs a ResponseCase with a SemanticObject
   * @param base The SemanticObject with the properties for the ResponseCase
   */
    public ProcessKPI(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("adminCase")) {
            doAdminCase(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action.equals("adminCase")) {
            String pid = request.getParameter("pid");
            
            SWBFormMgr mgr = new SWBFormMgr(getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
            try {
                mgr.processForm(request);
            } catch (FormValidateException e) {
                log.error(e);
            }
            response.setRenderParameter("pid", pid);
            response.setMode(response.Mode_VIEW);
        } else {
            super.processAction(request, response);
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        Process process = getProcess(request, paramRequest);
        String lang = "es";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        
        String pid = request.getParameter("pid");
        if (process != null) {
            Iterator<ProcessInstance> pi = process.listProcessInstances();
            if (pi.hasNext()) {
                SWBResourceURL adminUrl = paramRequest.getRenderUrl().setMode("adminCase");

                if (pid != null && !pid.trim().equals("")) {
                    adminUrl.setParameter("pid", pid);
                } else {
                    adminUrl.setParameter("pid", process.getId());
                }
                String timeTitle = paramRequest.getLocaleString("timeTitle") + " (" + getTimeUnit(paramRequest) + ")";
                ArrayList<DataSerie> logSeries = null;
                ArrayList<DataSerie> taskSeries = null;
                ArrayList<DataSerie> overviewSeries = null;
                int timeType = getResponseGraphType();
                int insType = getInstancesGraphType();
                int period = getResponsePeriodicity();

                //Obtener datos para gráficas de desempeño general
                overviewSeries = getOverviewTimeData(process, paramRequest);

                //Obtener datos para histórico de tiempos de respuesta
                if (isResponseShowLog()) {
                    if (period == PERIOD_LASTWEEK) {
                        logSeries = getLogDaylyData(process, 7, paramRequest);
                    } else if (period == PERIOD_LASTMONTH) {
                        logSeries = getLogDaylyData(process, 30, paramRequest);
                    } else if (period == PERIOD_LASTYEAR) {
                        logSeries = getLogMonthlyData(process, 12, paramRequest);
                    }
                }

                //Obtener datos para gráficas de desempeño por tarea
                if (isPerformanceShowTaskData()) {
                    taskSeries = getLogTaskData(process, paramRequest);
                }

                //Establecer el tema de las gráficas
                setTheme();

    //            if (!(paramRequest.getWebPage() instanceof ProcessWebPage)) {
    //                out.println("<div class=\"swbform\">");
    //                out.println("  <form action=\"#\">");
    //                out.println("    <label for=\"pid\">Proceso:</label>");
    //                out.println("    <select name=\"pid\" onchange=\"this.form.submit();\">");
    //                Iterator<Process> processes = Process.ClassMgr.listProcesses(paramRequest.getWebPage().getWebSite());
    //                while (processes.hasNext()) {
    //                    Process process1 = processes.next();
    //                    out.println("      <option value=\"" + process1.getId() + "\">" + process1.getDisplayTitle(lang) + "</option>");
    //                }
    //                out.println("    </select>");
    //                out.println("  </form>");
    //                out.println("</div>");
    //            }

                //out.println("<div id=\"properties\" class=\"swbform\">");
                out.println("<h2>Desempe&ntilde;o del proceso " + process.getDisplayTitle(lang)+"</h2>");
                //out.println("  <fieldset>");
                out.println("    <table>");
                out.println("      <tbody>");
                if (process != null && isResponseShowLog()) {
                    out.println("        <tr>");
                    out.println("          <td>");
                    out.println(plotLog(logSeries, "timeLog", timeTitle));
                    out.println("          </td>");
                    out.println("        </tr>");
                }

                if (process != null && isPerformanceShowTaskData()) {
                    out.println("        <tr>");
                    out.println("          <td>");
                    out.println(plotTaskGraph(taskSeries, "taskTime", paramRequest.getLocaleString("taskTimeTitle")));
                    out.println("          </td>");
                    out.println("        </tr>");
                }

                if (process != null) {
                    out.println("        <tr>");
                    out.println("          <td>");
                    out.println(plotGraph(overviewSeries.get(0), "oTime", timeTitle, timeType));
                    out.println("          </td>");
                    out.println("        </tr>");
                }

                if (process != null) {
                    out.println("        <tr>");
                    out.println("          <td>");
                    out.println(plotGraph(overviewSeries.get(1), "oInstances", paramRequest.getLocaleString("instanceTitle"), insType));
                    out.println("          </td>");
                    out.println("        </tr>");
                }
                out.println("      </tbody>");
                out.println("    </table>");
                //out.println("  </fieldset>");
                //out.println("  <fieldset>\n");
                out.println("    <a href=\"" + adminUrl + "\">" + paramRequest.getLocaleString("configGraphs") + "</a>");
                //out.println("  </fieldset>");
                //out.println("</div>");
            } else {
               //out.println("<div id=\"properties\" class=\"swbform\">\n"
                        out.println("  <fieldset>\n"
                        + "    <table>\n"
                        + "      <tbody>\n"
                        + "        <tr><td>" + paramRequest.getLocaleString("MsgError1") + "</td></tr>"
                        + "      </tbody>\n"
                        + "    </table>\n"
                        + "  </fieldset>\n");
              //          + "</div>\n");
            }
        } else {
            //out.println("<div id=\"properties\" class=\"swbform\">\n"
                        out.println("  <fieldset>\n"
                        + "    <table>\n"
                        + "      <tbody>\n"
                        + "        <tr><td>" + paramRequest.getLocaleString("MsgError2") + "</td></tr>"
                        + "      </tbody>\n"
                        + "    </table>\n"
                        + "  </fieldset>\n");
              //          + "</div>\n");
        }
    }
    
    private String plotTaskGraph(ArrayList<DataSerie> series, String id, String title) {
        String ret = "";
        if (series != null) {
            ret += "<script type=\"text/javascript\">\n"
            + "   dojo.require(\"dojox.charting.Chart2D\");\n"
            + "   dojo.require(\"" + theme + "\");\n"
            + "   dojo.require(\"dojox.charting.action2d.Tooltip\");\n"
            + "   dojo.require(\"dojox.charting.widget.Legend\");\n"
            + "   makeObjects = function(){\n"
            + "       var chart = new dojox.charting.Chart2D(\"" + id + "_taskTime\");\n"
            + "       chart.setTheme(" + theme + ");\n"
            + "       chart.addPlot(\"default\", {\n"
            + "           type: \"Lines\",\n"
            + "           markers: true,\n"
            + "       });\n"
            + "       chart.addAxis(\"x\", {\n"
            + "           majorTicks: true,\n"
            + "           majorLabels: false,\n"
            + "           minorTicks: true,\n"
            + "           minorLabels: false,\n"
            + "           min: 0,\n"
            + "       });\n"
            + "       chart.addAxis(\"y\", {vertical:true});\n"
            + "       chart.addSeries(\"" + series.get(0).getSerieLabel() + "\", " + getDojoSeriesDefinition(series.get(0), -1) + ");\n"
            + "       chart.addSeries(\"" + series.get(1).getSerieLabel() + "\", " + getDojoSeriesDefinition(series.get(1), -1) + ");\n"
            + "       chart.addSeries(\"" + series.get(2).getSerieLabel() + "\", " + getDojoSeriesDefinition(series.get(2), -1) + ");\n"
            + "       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");\n"
            + "       chart.render();\n"
            + "       var legend = new dojox.charting.widget.Legend({ chart: chart }, \"" + id + "_taskTimeSeries\");\n"
            + "   };\n"
            + "   dojo.addOnLoad(makeObjects);\n"
            + "</script>\n"
            + "<div class=\"mgraf\">\n"
            + "<h3 class=\"mgraf-tit\">" + title + "</h3>\n"
            + "<div id=\"" + id + "_taskTime\" style=\"width: 700px; height: 300px;\"></div>\n"
            + "<div id=\"" + id + "_taskTimeSeries\"></div>\n"
            + "</div>\n";
            //+ "<div id=\"stageLog\" style=\"width:700px; height:50px; text-align:center;\"><label>" + title + "</label></div>\n";
        }
        return ret;
    }
    
    /**
     * Obtiene la cadena de despliegue de una gráfica con los tiempos de 
     * respuesta del proceso en un intervalo definido.
     * @param request the request.
     * @param paramRequest the paramRequest.
     * @return Cadena HTML de despliegue de la gráfica de historial de tiempos
     * de respuesta del proceso.
     * @throws SWBResourceException 
     */
    private String plotLog(ArrayList<DataSerie> series, String id, String title) {
        String ret = "";
        
        if (series != null) {
            ret += "<script type=\"text/javascript\">\n"
            + "   dojo.require(\"dojox.charting.Chart2D\");\n"
            + "   dojo.require(\"" + theme + "\");\n"
            + "   dojo.require(\"dojox.charting.action2d.Tooltip\");\n"
            + "   dojo.require(\"dojox.charting.widget.Legend\");\n"
            + "   makeObjects = function(){\n"
            + "       var chart = new dojox.charting.Chart2D(\"" + id + "_timelog\");\n"
            + "       chart.setTheme(" + theme + ");\n"
            + "       chart.addPlot(\"default\", {\n"
            + "           type: \"Lines\",\n"
            + "           markers: true,\n"
            + "       });\n"
            + "       chart.addAxis(\"x\", {\n"
            + "           majorTicks: true,\n"
            + "           majorLabels: false,\n"
            + "           minorTicks: true,\n"
            + "           minorLabels: false,\n"
            + "       });\n"
            + "       chart.addAxis(\"y\", {vertical:true});\n"
            + "       chart.addSeries(\"" + series.get(0).getSerieLabel() + "\", " + getDojoSeriesDefinition(series.get(0), -1) + ");\n"
            + "       chart.addSeries(\"" + series.get(1).getSerieLabel() + "\", " + getDojoSeriesDefinition(series.get(1), -1) + ");\n"
            + "       chart.addSeries(\"" + series.get(2).getSerieLabel() + "\", " + getDojoSeriesDefinition(series.get(2), -1) + ");\n"
            + "       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");\n"
            + "       chart.render();\n"
            + "       var legend = new dojox.charting.widget.Legend({ chart: chart }, \"" + id + "_timelogSeries\");\n"
            + "   };\n"
            + "   dojo.addOnLoad(makeObjects);\n"
            + "</script>\n"
            + "<div class=\"mgraf\">\n"
            + "<h3 class=\"mgraf-tit\">" + title + "</h3>\n"
            + "<div id=\"" + id + "_timelog\" style=\"width: 700px; height: 300px;\"></div>\n"
            + "<div id=\"" + id + "_timelogSeries\"></div>\n"
            //+ "<div id=\"stageLog\" style=\"width:700px; height:50px; text-align:center;\"><label>" + title + "</label></div>\n";
            + "</div>\n";
        }
        //System.out.println(ret);
        return ret;
    }
    
    /**
     * Obtiene una cadena con la definición de los datos de una serie para 
     * una gráfica hecha con Dojo.
     * @param serie Serie de datos de la cual se obtendrá la definición.
     * @param paramRequest the paramRequest.
     * @return Cadena de definición de la serie en sintaxis de Dojo.
     * @throws SWBResourceException 
     */
    private String getDojoSeriesDefinition(DataSerie serie, int plotType) {
        String ret = "[";
        
        Iterator<Long> data = serie.listData();
        Iterator<String> labels = serie.listDataLabels();
        Iterator<String> xLabels = serie.listxAxisLabels();
        int c = 0;
        while(data.hasNext()) {
            c++;
            long dat = data.next();
            String datL = labels.next();
            String yLabel = xLabels.next();
            if (plotType == TYPE_AREA || plotType == TYPE_BARS) {
                ret += String.valueOf(dat);
            } else if (plotType == TYPE_PIE) {
                ret+= "{y: "+ dat +", text: \"" + yLabel + "\", tooltip:\"" + datL + "\"}";
            } else {
                ret += "{x:" + c + ", y:" + dat + ", tooltip:\"" + datL + "\"}";
            }
            if (data.hasNext()) ret += ",";
        }
        ret += "]\n";
        return ret;
    }
    
    private String getDojoXAxisLabels(DataSerie serie) {
        String ret = "labels: [\n";
        Iterator<String> labels = serie.listxAxisLabels();
        int c = 0;
        while(labels.hasNext()) {
            c++;
            String label = labels.next();
            ret += "{value:" + c + ", text:\"" + label + "\"}";
            if (labels.hasNext()) ret += ",";
            ret += "\n";
        }
        ret += "],\n";
        return ret;
    }
    
    /**
     * Obtiene la cadena de despliegue de la gráfica adecuada para los datos
     * generales de tiempo de respuesta del proceso.
     * @param paramRequest the paramRequest
     * @return Cadena HTML de despliegue de la gráfica.
     * @throws SWBResourceException 
     */
    private String plotGraph(DataSerie data, String id, String title, int type) {
        String ret = "";
                
        if (type == TYPE_AREA) ret = plotArea (data, id, title);
        if (type == TYPE_BARS) ret = plotBars (data, id, title);
        if (type == TYPE_PIE) ret = plotPie (data, id, title);
        //System.out.println(ret);
        return ret;
    }
    
    /**
     * Establece el tema de colores de las gráficas.
     */
    public void setTheme() {
        int them = getResponseGraphTheme();
        if (them == THEME_BLUE) theme = "dojox.charting.themes.PlotKit.blue";
        if (them == THEME_RED) theme = "dojox.charting.themes.PlotKit.red";
        if (them == THEME_GREEN) theme = "dojox.charting.themes.PlotKit.green";
    }
    
    /**
     * Obtiene la cadena de despliegue de una gráfica de pastel con los datos 
     * generales de tiempo de respuesta del proceso.
     * @param data Serie de datos de tiempo de respuesta.
     * @param paramRequest the paramRequest
     * @return Cadena HTML de despliegue de la gráfica de pastel.
     * @throws SWBResourceException 
     */
    public String plotPie(DataSerie data, String id, String title) {
        String ret = "";
        ret = "<script type=\"text/javascript\">\n"
        + "   dojo.require(\"dojox.charting.Chart2D\");\n"
        + "   dojo.require(\"" + theme + "\");\n"
        + "   dojo.require(\"dojox.charting.action2d.MoveSlice\");\n"
        + "   dojo.require(\"dojox.charting.action2d.Tooltip\");\n"
        + "   dojo.require(\"dojox.charting.action2d.Highlight\");\n"
        + "   makeObjects = function() {\n"
        + "       var chart = new dojox.charting.Chart2D(\"" + id + "_instances\");\n"
        + "       chart.setTheme(" + theme + ");\n"
        + "       chart.addPlot(\"default\", {\n"
        + "           type: \"Pie\",\n"
        + "           labelOffset: 40,\n"
        + "           fontColor: \"white\",\n"
        + "           radius: 120\n"
        + "       });\n"
        + "       chart.addSeries(\""+ data.getSerieLabel() +"\", " + getDojoSeriesDefinition(data, TYPE_PIE) + ");"
        + "       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\");"
        + "       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");"
        + "       chart.render();"
        + "   };"
        + "   dojo.addOnLoad(makeObjects);"
        + "</script>"
        + "<div class=\"mgraf\">\n"
        + "<h3 class=\"mgraf-tit\">" + title + "</h3>\n"
        + "<div id=\"" + id + "_instances\" style=\"width: 400px; height: 300px;\"></div>"
        //+ "<div id=\"title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + title + "</label></div>\n";
        + "</div>\n";
        return ret;
    }
    
    /**
     * Obtiene la cadena de despliegue de una gráfica de área con los datos 
     * generales de tiempo de respuesta del proceso.
     * @param data Serie de datos de tiempo de respuesta.
     * @param paramRequest the paramRequest
     * @return Cadena HTML de despliegue de la gráfica de área.
     * @throws SWBResourceException 
     */
    public String plotArea(DataSerie data, String id, String title) {
        String ret = "";
        if (data != null) {
            ret += "<script type=\"text/javascript\">\n"
            + "   dojo.require(\"dojox.charting.Chart2D\");\n"
            + "   dojo.require(\"" + theme + "\");\n"
            + "   dojo.require(\"dojox.charting.action2d.Tooltip\");\n"
            + "   makeObjects = function(){\n"
            + "       var chart = new dojox.charting.Chart2D(\"" + id + "_instances\");\n"
            + "       chart.setTheme(" + theme + ");\n"
            + "       chart.addPlot(\"default\", {\n"
            + "           type: \"StackedAreas\",\n"
            + "           markers: true,\n"
            + "       });\n"
            + "       chart.addAxis(\"x\", {\n"
            + getDojoXAxisLabels(data)
            + "           majorTicks: true,\n"
            + "           majorLabels: true,\n"
            + "           minorTicks: true,\n"
            + "           minorLabels: true,\n"
            + "       });\n"
            + "       chart.addAxis(\"y\", {vertical:true});\n"
            + "       chart.addSeries(\"" + data.getSerieLabel() + "\", " + getDojoSeriesDefinition(data, TYPE_AREA) + ");\n"
            + "       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");\n"
            + "       chart.render();\n"
            + "   };\n"
            + "   dojo.addOnLoad(makeObjects);\n"
            + "</script>\n"
            + "<div class=\"mgraf\">\n"
            + "<h3 class=\"mgraf-tit\">" + title + "</h3>\n"
            + "<div id=\"" + id + "_instances\" style=\"width: 400px; height: 300px;\"></div>\n"
            //+ "<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + title + "</label></div>\n";
            + "</div>\n";
        }
        return ret;
    }
    
    /**
     * Obtiene la cadena de despliegue de una gráfica de barras con los datos 
     * generales de tiempo de respuesta del proceso.
     * @param data Serie de datos de tiempo de respuesta.
     * @param paramRequest the paramRequest
     * @return Cadena HTML de despliegue de la gráfica de barras.
     * @throws SWBResourceException 
     */
    public String plotBars(DataSerie data, String id, String title) {
        String ret = "";
        
        if (data != null) {
            ret += "<script type=\"text/javascript\">"
            + "   dojo.require(\"dojox.charting.Chart2D\");"
            + "   dojo.require(\"" + theme + "\");"
            + "   dojo.require(\"dojox.charting.action2d.Tooltip\");"
            + "   makeObjects = function(){"
            + "       var chart = new dojox.charting.Chart2D(\"" + id + "_instances\");"
            + "       chart.setTheme(" + theme + ");"
            + "       chart.addPlot(\"default\", {"
            + "           type: \"Columns\","
            + "           markers: true,"
            + "           gap: 7"
            + "       });"
            + "       chart.addAxis(\"x\", {"
            + getDojoXAxisLabels(data)
            + "           majorTicks: true,"
            + "           majorLabels: true,"
            + "           minorTicks: true,"
            + "           minorLabels: true,"
            + "       });"
            + "       chart.addAxis(\"y\", {vertical:true, min:0});"
            + "       chart.addSeries(\"" + data.getSerieLabel() + "\", " + getDojoSeriesDefinition(data, TYPE_BARS) + ");"
            + "       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");"
            + "       chart.render();"
            + "   };"
            + "   dojo.addOnLoad(makeObjects);"
            + "</script>"
            + "<div class=\"mgraf\">\n"
            + "<h3 class=\"mgraf-tit\">" + title + "</h3>\n"
            + "<div id=\"" + id + "_instances\" style=\"width: 400px; height: 300px;\"></div>"
            //+ "<div id=\"stage\" style=\"width:700px; height:50px; text-align:center;\"><label>" + title + "</label></div>\n";
            + "</div>\n";
        }
        return ret;
    }
    
    public void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Process process = getProcess(request, paramRequest);
        if (process != null) {
            SWBFormMgr mgr = new SWBFormMgr(getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
            mgr.addHiddenParameter("pid", request.getParameter("pid"));
            mgr.addButton(SWBFormButton.newSaveButton());
            mgr.addButton(SWBFormButton.newBackButton());
            mgr.setType(SWBFormMgr.TYPE_DOJO);
            mgr.setAction(paramRequest.getActionUrl().setAction("adminCase").toString());
            out.print(mgr.renderForm(request));
        }
    }
    
    /**
     * Obtiene los datos de la serie de tiempo mínimo, máximo y promedio de la
     * gráfica general de los procesos terminados.
     * @param process Proceso del cual se extraerá la serie.
     * @param timeunit Unidad de tiempo de despliegue de los datos.
     * @return Arreglo con los datos de la serie.
     */
    private ArrayList<DataSerie> getOverviewTimeData(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        return getOverviewTimeDataByStatus(process, ProcessInstance.STATUS_CLOSED, true, paramRequest);
    }
    
    /**
     * Obtiene los datos de la serie de tiempo mínimo, máximo y promedio de la
     * gráfica general del proceso.
     * @param process Proceso del cual se extraerá la serie.
     * @param status Estado que los procesos deben tener para agregar
     * sus datos a la serie.
     * @param timeunit Unidad de tiempo de despliegue de los datos.
     * @return Arreglo con los datos de la serie.
     */
    private ArrayList<DataSerie> getOverviewTimeDataByStatus(Process process, int status, boolean collectInstanceData, SWBParamRequest paramRequest) throws SWBResourceException {
        ArrayList<DataSerie> ret = new ArrayList<DataSerie>();
        int divisor = 86400000;
        int timeunit = getResponseTimeUnit();
        String unit = getTimeUnit(paramRequest);
        long max_time = 0;
        long min_time = 0;
        long sum_time = 0;
        int activeInstances = 0;
        int closedInstances = 0;
        int abortedInstances = 0;
        Iterator<Instance> instances = process.listProcessInstances();
        if (instances.hasNext()) {
            int c_instances = 0;
            while(instances.hasNext()) {
                Instance instance = instances.next();
                if (collectInstanceData) {
                    if (instance.getStatus() == ProcessInstance.STATUS_OPEN || instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                        activeInstances++;
                    }
                    if (instance.getStatus() == ProcessInstance.STATUS_CLOSED) closedInstances++;
                    if (instance.getStatus() == ProcessInstance.STATUS_ABORTED) abortedInstances++;
                }
                if (instance.getStatus() == status) {
                    c_instances++;
                    long instanceTime = instance.getEnded().getTime() - instance.getCreated().getTime();
                    sum_time += instanceTime;
                    if (instanceTime > max_time) max_time = instanceTime;
                    if (c_instances == 1 || instanceTime < min_time) min_time = instanceTime;
                }
            }
            if (c_instances != 0) {
                if (timeunit == TIMEUNIT_SECONDS) divisor = 1000;
                if (timeunit == TIMEUNIT_MINUTES) divisor = 60000;
                if (timeunit == TIMEUNIT_HOURS) divisor = 3600000;
                sum_time = (sum_time / c_instances);
            }
        }
                
        DataSerie timeSerie = new DataSerie("oTime", "ResponseCase");
        timeSerie.addXAxisLabel(paramRequest.getLocaleString("minimum"));
        timeSerie.addXAxisLabel(paramRequest.getLocaleString("average"));
        timeSerie.addXAxisLabel(paramRequest.getLocaleString("maximum"));
        
        long minTime = min_time / divisor;
        long avgTime = sum_time / divisor;
        long maxTime = max_time / divisor;
        timeSerie.addData(minTime, paramRequest.getLocaleString("minTime") + ": " + minTime + " " + unit);
        timeSerie.addData(avgTime, paramRequest.getLocaleString("avgTime") + ": " + avgTime + " " + unit);
        timeSerie.addData(maxTime, paramRequest.getLocaleString("maxTime") + ": " + maxTime + " " + unit);
        ret.add(timeSerie);
        
        if (collectInstanceData) {
            DataSerie instanceSerie = new DataSerie("oInstances", "ProcessInstances");
            instanceSerie.addXAxisLabel(paramRequest.getLocaleString("active"));
            instanceSerie.addXAxisLabel(paramRequest.getLocaleString("closed"));
            instanceSerie.addXAxisLabel(paramRequest.getLocaleString("aborted"));
            instanceSerie.addData(activeInstances, paramRequest.getLocaleString("active") + ": " + String.valueOf(activeInstances));
            instanceSerie.addData(closedInstances, paramRequest.getLocaleString("closed") + ": " + String.valueOf(closedInstances));
            instanceSerie.addData(abortedInstances, paramRequest.getLocaleString("aborted") + ": " + String.valueOf(abortedInstances));
            ret.add(instanceSerie);
        }
        return ret;
    }
    
    /**
     * Obtiene las series de datos de los tiempos mínimo, promedio y máximo de 
     * una instancia del proceso, desde hace daysFromNow días hasta el día de hoy.
     * @param process Proceso del cual se extraerán las series.
     * @param daysFromNow Intervalo inferior de días a obtener.
     * @param paramRequest the paramRequest
     * @return ArrayList con las series de datos del proceso.
     */
    private ArrayList<DataSerie> getLogDaylyData (Process process, int daysFromNow, SWBParamRequest paramRequest) throws SWBResourceException {
        TreeMap<Date, long[]> countData = new TreeMap<Date, long[]>();
        ArrayList<DataSerie> data = new ArrayList<DataSerie>();
        
        Calendar today = GregorianCalendar.getInstance();
        today.setTime(new Date(System.currentTimeMillis()));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        Calendar dat = GregorianCalendar.getInstance();
        dat.setTime(today.getTime());
        for (int i = -(daysFromNow - 1); i <= 0; i++) {
            dat.setTime(today.getTime());
            dat.add(Calendar.DATE, i);
            long [] t = {0,0,0,0};
            countData.put(dat.getTime(), t); //sumatoria de tiempos, total de procesos, mínimo, máximo
        }
        
        Iterator<ProcessInstance> instances = process.listProcessInstances();
        while(instances.hasNext()) {
            ProcessInstance instance = instances.next();
            Calendar ended = GregorianCalendar.getInstance();
            
            if (instance.getEnded() != null) {
                ended.setTime(instance.getEnded());
                ended.set(Calendar.HOUR_OF_DAY, 0);
                ended.set(Calendar.MINUTE, 0);
                ended.set(Calendar.SECOND, 0);
                ended.set(Calendar.MILLISECOND, 0);
            }
            
            if (instance.getStatus() == ProcessInstance.STATUS_PROCESSING) {
                
            }
            
            if (instance.getStatus() == ProcessInstance.STATUS_CLOSED) {
                if (countData.containsKey(ended.getTime())) {
                    long instanceTime = instance.getEnded().getTime() - instance.getCreated().getTime();
                    long [] temp = countData.get(ended.getTime());
                    temp[0] += instanceTime;
                    temp[1] += 1;
                    if (instanceTime > temp[3]) temp[3] = instanceTime;
                    if (temp[1] == 1 || instanceTime < temp[2]) temp[2] = instanceTime;
                    countData.put(ended.getTime(), temp);
                }
            }
        }
        
        Iterator<Date> keys = countData.keySet().iterator();
        DataSerie min = new DataSerie("tMin", paramRequest.getLocaleString("minimum"));
        DataSerie avg = new DataSerie("tAvg", paramRequest.getLocaleString("average"));
        DataSerie max = new DataSerie("tMax", paramRequest.getLocaleString("maximum"));
        
        int divisor = 86400000;
        int timeunit = getResponseTimeUnit();
        if (timeunit == TIMEUNIT_SECONDS) divisor = 1000;
        if (timeunit == TIMEUNIT_MINUTES) divisor = 60000;
        if (timeunit == TIMEUNIT_HOURS) divisor = 3600000;
        
        while(keys.hasNext()) {
            Date key = keys.next();
            long[] temp = countData.get(key);
            long mit = temp[2] / divisor;
            long mat = temp[3] / divisor;
            long avt = ((temp[1] > 0) ? temp[0] / temp[1] : 0) / divisor;
            
            String dateText = SWBUtils.TEXT.getStrDate(key, "es", "dd-Month");
            dateText = dateText.substring(0, dateText.indexOf("-") + 4);
            String unit = getTimeUnit(paramRequest);
            
            avg.addXAxisLabel(dateText);
            min.addXAxisLabel(dateText);
            max.addXAxisLabel(dateText);
            
            avg.addData(avt, dateText + ": " + avt + " " + unit);
            min.addData(mit, dateText + ": " + mit + " " + unit);
            max.addData(mat, dateText + ": " + mat + " " + unit);
        }
        data.add(min);
        data.add(avg);
        data.add(max);
        return data;
    }
    
    /**
     * Obtiene las series de datos de los tiempos mínimo, promedio y máximo de 
     * una instancia del proceso, desde hace monthsFromNow meses hasta el mes 
     * actual.
     * @param process Proceso del cual se extraerán las series.
     * @param monthsFromNow Intervalo inferior de meses a obtener.
     * @param paramRequest the paramRequest
     * @return ArrayList con las series de datos del proceso.
     */
    private ArrayList<DataSerie> getLogMonthlyData (Process process, int monthsFromNow, SWBParamRequest paramRequest) throws SWBResourceException {
        TreeMap<Date, long[]> countData = new TreeMap<Date, long[]>();
        ArrayList<DataSerie> data = new ArrayList<DataSerie>();
        
        Calendar today = GregorianCalendar.getInstance();
        today.setTime(new Date(System.currentTimeMillis()));
        today.set(Calendar.DATE, 1);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        Calendar dat = GregorianCalendar.getInstance();
        dat.setTime(today.getTime());
        for (int i = -(monthsFromNow - 1); i <= 0; i++) {
            dat.setTime(today.getTime());
            dat.add(Calendar.MONTH, i);
            long [] t = {0,0,0,0};
            countData.put(dat.getTime(), t); //sumatoria de tiempos, total de procesos, mínimo, máximo
        }
        
        Iterator<ProcessInstance> instances = SWBComparator.sortByCreated(process.listProcessInstances(), true);
        while(instances.hasNext()) {
            ProcessInstance instance = instances.next();
            Calendar ended = GregorianCalendar.getInstance();
            
            if (instance.getEnded() != null) {
                ended.setTime(instance.getEnded());
                ended.set(Calendar.DATE, 1);
                ended.set(Calendar.HOUR_OF_DAY, 0);
                ended.set(Calendar.MINUTE, 0);
                ended.set(Calendar.SECOND, 0);
                ended.set(Calendar.MILLISECOND, 0);

                if (countData.containsKey(ended.getTime())) {
                    long instanceTime = instance.getEnded().getTime() - instance.getCreated().getTime();
                    long [] temp = countData.get(ended.getTime());
                    temp[0] += instanceTime;
                    temp[1] += 1;
                    if (instanceTime > temp[3]) temp[3] = instanceTime;
                    if (temp[1] == 1 || instanceTime < temp[2]) temp[2] = instanceTime;
                    countData.put(ended.getTime(), temp);
                }
            }
        }
        
        Iterator<Date> keys = countData.keySet().iterator();
        DataSerie min = new DataSerie("tMin", paramRequest.getLocaleString("minimum"));
        DataSerie avg = new DataSerie("tAvg", paramRequest.getLocaleString("average"));
        DataSerie max = new DataSerie("tMax", paramRequest.getLocaleString("maximum"));
        
        int divisor = 86400000;
        int timeunit = getResponseTimeUnit();
        if (timeunit == TIMEUNIT_SECONDS) divisor = 1000;
        if (timeunit == TIMEUNIT_MINUTES) divisor = 60000;
        if (timeunit == TIMEUNIT_HOURS) divisor = 3600000;
        
        while(keys.hasNext()) {
            Date key = keys.next();
            long[] temp = countData.get(key);
            long avt = ((temp[1] > 0) ? temp[0] / temp[1] : 0) / divisor;
            long mit = temp[2] / divisor;
            long mat = temp[3] / divisor;
            
            String dateText = SWBUtils.TEXT.getStrDate(key, "es", "yyyy-Month");
            dateText = dateText.substring(0, dateText.indexOf("-") + 4);
            
            String unit = getTimeUnit(paramRequest);
            
            avg.addXAxisLabel(dateText);
            min.addXAxisLabel(dateText);
            max.addXAxisLabel(dateText);
            
            avg.addData(avt, dateText + ": " + avt + " " + unit);
            min.addData(mit, dateText + ": " + mit + " " + unit);
            max.addData(mat, dateText + ": " + mat + " " + unit);
        }
        data.add(min);
        data.add(avg);
        data.add(max);
        return data;
    }
    
    private ArrayList<DataSerie> getLogTaskData (Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        HashMap<GraphicalElement, Long[]> countData = new HashMap<GraphicalElement, Long[]>();
        ArrayList<DataSerie> data = new ArrayList<DataSerie>();
        
        Iterator<ProcessInstance> instances = process.listProcessInstances();
        while(instances.hasNext()) {
            ProcessInstance instance = instances.next();

            if (instance.getEnded() != null) {
                Iterator<FlowNodeInstance> fn_instances = instance.listAllFlowNodeInstance();
                while(fn_instances.hasNext()) {
                    FlowNodeInstance fn_Instance = fn_instances.next();
                    FlowNode fn = fn_Instance.getFlowNodeType();
                    if (fn instanceof Task) {
                        Task tsk = (Task) fn;
                        long taskTime = fn_Instance.getEnded().getTime() - fn_Instance.getCreated().getTime();
                        Long []temp = null;
                        if (countData.containsKey(tsk) && fn_Instance.getEnded() != null && fn_Instance.getCreated() != null) {
                            temp = countData.get(tsk);                            
                        } else {
                            temp = new Long[4];
                            temp[0] = temp[1] = temp[2] = temp[3] = 0L;
                        }
                        temp[0] += taskTime;
                        temp[1] += 1;
                        if (taskTime > temp[3]) temp[3] = taskTime;
                        if (temp[1] == 1 || taskTime < temp[2]) temp[2] = taskTime;
                        countData.put(tsk, temp);
                    }
                }
            }
        }
        
        Iterator<GraphicalElement> keys = countData.keySet().iterator();
        DataSerie min = new DataSerie("taskTmin", paramRequest.getLocaleString("minimum"));
        DataSerie avg = new DataSerie("taskTAvg", paramRequest.getLocaleString("average"));
        DataSerie max = new DataSerie("taskTMax", paramRequest.getLocaleString("maximum"));
        
        int divisor = 86400000;
        int timeunit = getResponseTimeUnit();
        if (timeunit == TIMEUNIT_SECONDS) divisor = 1000;
        if (timeunit == TIMEUNIT_MINUTES) divisor = 60000;
        if (timeunit == TIMEUNIT_HOURS) divisor = 3600000;
        String unit = getTimeUnit(paramRequest);
        
        while(keys.hasNext()) {
            GraphicalElement key = keys.next();
            Long[] temp = countData.get(key);
            long avt = ((temp[1] > 0) ? temp[0] / temp[1] : 0) / divisor;
            long mat = temp[3] / divisor;
            long mit = temp[2] / divisor;
            
            String tskText = key.getTitle();
            avg.addData(avt, tskText + ": " + avt + " " + unit);
            min.addData(mit, tskText + ": " + mit + " " + unit);
            max.addData(mat, tskText + ": " + mat + " " + unit);
           
            avg.addXAxisLabel(tskText);
            min.addXAxisLabel(tskText);
            max.addXAxisLabel(tskText);
        }
        data.add(min);
        data.add(avg);
        data.add(max);
        return data;
    }
    
    /**
     * Obtiene el proceso asociado con la página Web de Procesos actual o con
     * el ID pasado por parámetro.
     * @param request the request
     * @param paramRequest the paramRequest
     * @return Proceso asociado con la página Web de Procesos actual o con el 
     * ID pasado por parámetro o null si el proceso no existe.
     */
    private Process getProcess(HttpServletRequest request, SWBParamRequest paramRequest) {
        //ProcessWebPage wp = null;
        Process process = null;
        WebPage wp = paramRequest.getWebPage();
        String pid = request.getParameter("pid");
        if(wp instanceof WrapperProcessWebPage) {
            process = ((WrapperProcessWebPage)wp).getProcess();
        } else if (pid != null && !pid.trim().equals("")) {
            process = Process.ClassMgr.getProcess(pid, wp.getWebSite());
        }
        return process;
    }
    
    /**
     * Obtiene el nombre de la unidad de tiempo de despliegue de las gráficas
     * de tiempo de respuesta del proceso.
     * @param paramRequest the paramRequest
     * @return Nombre de la unidad de despliegue.
     * @throws SWBResourceException 
     */
    public String getTimeUnit(SWBParamRequest paramRequest) throws SWBResourceException {
        String ret = paramRequest.getLocaleString("unitDays");
        int time_unit = getResponseTimeUnit();
        if (time_unit == TIMEUNIT_SECONDS) ret = paramRequest.getLocaleString("unitSeconds");
        if (time_unit == TIMEUNIT_MINUTES) ret = paramRequest.getLocaleString("unitMinutes");
        if (time_unit == TIMEUNIT_HOURS) ret = paramRequest.getLocaleString("unitHours");
        return ret;
    }
}
