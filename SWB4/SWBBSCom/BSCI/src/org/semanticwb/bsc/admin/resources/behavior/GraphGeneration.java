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
                    Series serieGraph2 = grapher.getSerieGraph2();
                    //Valida que la serie guardada este activa
                    if (serieGraph.isActive() && serieGraph.isValid() && serieGraph2.isActive() && serieGraph2.isValid()) {
                        Iterator<Period> measurablesPeriods1 = null;
                        Iterator<Period> measurablesPeriods2 = null;
                        try {
                            measurablesPeriods1 = serieGraph.getIndicator().listMeasurablesPeriods();
                            measurablesPeriods2 = serieGraph2.getIndicator().listMeasurablesPeriods();
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
                        Period period, period2;
                        //Valida que la serie1 y la serie2 contengan periodos asignados
                        if (measurablesPeriods1.hasNext() && measurablesPeriods2.hasNext()) {
                            //Genera la grafica
                            out.println("<div id=\"chart1\" class=\'with-3d-shadow with-transitions\'>");
                            out.println("<h4 align=\"center\">" + grapher.getTitleGraph());
                            out.println("<svg></svg>");
                            out.println("</h4>");
                            out.println("</div>");
                            out.println("<script type=\"text/javascript\">");
                            out.println("long_short_data = [");
                            out.println("{");
                            out.println("key: \"" + grapher.getSerieGraph().getTitle() + "\" ,");
                            out.println("color: '#d62728',");
                            out.println("values: [");
                            //Recorre los periodos y valores de la serie1 para graficarlos
                            while (measurablesPeriods1.hasNext()) {
                                period = measurablesPeriods1.next();
                                Measure measure = serieGraph.getMeasure(period);
                                out.println("{");
                                out.println("\"label\" : \"" + period.getTitle() + "\" ,");
                                try {
                                    out.println("\"value\" : " + measure.getValue() + "");
                                } catch (Exception e) {
                                    out.println("\"value\" : 0.0 ");
                                }
                                out.println("},");
                                }
                                out.println(" ]");
                                out.println("},");
                                //segunda serie a graficar
                                out.println("{");
                                out.println("key: \""  + grapher.getSerieGraph2().getTitle() + "\" ,");
                                out.println("color: '#1f77b4',");
                                out.println("values: [");
                                //Recorre los periodos y valores de la serie2 para graficarlos
                                while (measurablesPeriods2.hasNext()) {
                                period2 = measurablesPeriods2.next();
                                Measure measure2 = serieGraph2.getMeasure(period2);
                                out.println("{");
                                out.println("\"label\" : \"" + period2.getTitle() + "\" ,");
                                try {
                                    out.println("\"value\" : " + measure2.getValue() + "");
                                } catch (Exception e) {
                                    out.println("\"value\" : 0.0 ");
                                }
                                out.println("},");
                                }
                                
                                out.println("]");
                                out.println(" }");
                                out.println("];");
                                out.println("var chart;");
                                out.println("nv.addGraph(function() {");
                                out.println("chart = nv.models.multiBarHorizontalChart()");
                                out.println(".x(function(d) { return d.label })");
                                out.println(".y(function(d) { return d.value })");
                                out.println(".margin({top: 30, right: 20, bottom: 50, left: 175})");
                                out.println(".transitionDuration(250)");
                                out.println(".showControls(false);");
                                out.println("chart.yAxis");
                                out.println(".tickFormat(d3.format(',.2f'));");
                                out.println("d3.select('#chart1 svg')");
                                out.println(".datum(long_short_data)");
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
