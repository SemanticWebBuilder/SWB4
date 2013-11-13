/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.accessory.Grapher;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.bsc.utils.InappropriateFrequencyException;
import org.semanticwb.bsc.utils.UndefinedFrequencyException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author ana.garcias
 */
public class GraphGeneration extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        //super.doView(request, response, paramsRequest); //To change body of generated methods, choose Tools | Templates.
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semanticObj = SemanticObject.createSemanticObject(suri);

        if (semanticObj != null) {
            GenericObject genericObj = semanticObj.createGenericInstance();
            if (genericObj instanceof Indicator) {
                //Recuperar los datos del graficador
                Iterator<Grapher> itGraph = new GenericIterator<Grapher>(
                        semanticObj.listObjectProperties(Indicator.bsc_hasGrapher));
                //Valida que tenga gurdados datos de configuracion de grafica
                if (itGraph.hasNext()) {
                    Grapher grapher = itGraph.next();
                    Series serieGraph = grapher.getSerieGraph();
                    //Valida que la serie guardada este activa
                    if (serieGraph.isActive() && serieGraph.isValid()) {
                        Iterator<Period> measurablesPeriods = null;
                        try {
                            measurablesPeriods = serieGraph.getIndicator().listMeasurablesPeriods();
                        } catch (UndefinedFrequencyException ex) {
                            out.println("<div class=\"swbform\">");
                            out.println("<fieldset>");
                            out.println("</fieldset>");
                            out.println("<p>" + paramsRequest.getLocaleString("msgUndefinedFrequencyException") + "</p>");
                            out.println("</div>");
                            out.flush();
                            out.close();
                        } catch (InappropriateFrequencyException ex) {
                            out.println("<div class=\"swbform\">");
                            out.println("<fieldset>");
                            out.println("</fieldset>");
                            out.println("<p>" + paramsRequest.getLocaleString("msgInappropriateFrequencyException") + "</p>");
                            out.println("</div>");
                            out.flush();
                            out.close();
                        }
                        Period period;
                        //Valida que la serie guardada contenga periodos asignados
                        if (measurablesPeriods.hasNext()) {
                            //Genera la grafica
                            out.println("<div id=\"chart1\">");
                            out.println("<h4 align=\"center\">" + grapher.getTitleGraph());
                            out.println("<svg></svg>");
                            out.println("</h4>");
                            out.println("</div>");
                            out.println("<script type=\"text/javascript\">");
                            out.println("historicalBarChart = [");
                            out.println("{");
                            out.println("key: \"Cumulative Return\",");
                            out.println("values: [");
                            //Recorre los periodos y sus valores para graficarlos
                            while (measurablesPeriods.hasNext()) {
                                period = measurablesPeriods.next();
                                Measure measure = serieGraph.getMeasure(period);
                                out.println("{");
                                out.println("\"label\" : \"" + period.getTitle() + "\" ,");
                                out.println("\"value\" : " + measure.getValue() + "");
                                out.println("} ,");
                            }

                            out.println(" ]");
                            out.println("}");
                            out.println("];");
                            out.println("nv.addGraph(function() {");
                            out.println("var chart = nv.models.discreteBarChart()");
                            out.println(".x(function(d) { return d.label })");
                            out.println(".y(function(d) { return d.value })");
                            out.println(".staggerLabels(true)");
                            out.println(".tooltips(false)");
                            out.println(".showValues(true)");
                            out.println(".transitionDuration(250);");
                            out.println("chart.xAxis");
                            out.println(".axisLabel(\"" + grapher.getTitleX() + "\");");
                            out.println("chart.yAxis");
                            out.println(".axisLabel(\"" + grapher.getTitleY() + "\");");
                            out.println("chart.showXAxis(true);");
                            out.println("chart.showYAxis(true);");
                            out.println("d3.select('#chart1 svg')");
                            out.println(".datum(historicalBarChart)");
                            out.println(".call(chart);");
                            out.println("nv.utils.windowResize(chart.update);");
                            out.println("return chart;");
                            out.println("});");
                            out.println("</script>");

                        } else {
                            out.println("<div class=\"swbform\">");
                            out.println("<fieldset>");
                            out.println("</fieldset>");
                            out.println("<p>" + paramsRequest.getLocaleString("msgNotPeriods") + "</p>");
                            out.println("</div>");
                            out.flush();
                            out.close();
                        }
                    } else {
                        out.println("<div class=\"swbform\">");
                        out.println("<fieldset>");
                        out.println("</fieldset>");
                        out.println("<p>" + paramsRequest.getLocaleString("msgNotSerieAct") + "</p>");
                        out.println("</div>");
                        out.flush();
                        out.close();
                    }
                } else {
                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("</fieldset>");
                    out.println("<p>" + paramsRequest.getLocaleString("msgNotConfiguration") + "</p>");
                    out.println("</div>");
                    out.flush();
                    out.close();
                }

            }

        }

    }
}
