/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.String;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.ComponentExportable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.catalogs.Format;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.bsc.utils.InappropriateFrequencyException;
import org.semanticwb.bsc.utils.UndefinedFrequencyException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.util.UploaderFileCacheUtils;
import org.w3c.dom.Document;

/**
 * Genera el c&oacute;digo HTML para presentar una gr&aacute;fica con los datos
 * de las series activas del indicador cuyo uri se recibe.
 *
 * @author ana.garcias
 */
public class GraphGeneration extends GenericAdmResource implements ComponentExportable {

    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static Logger log = SWBUtils.getLogger(GraphGeneration.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semanticObj = SemanticObject.createSemanticObject(suri);
        Resource base = this.getResourceBase();
        
        //Colores a utilizar para cada serie
        String[] colors = {"#009900", "#0066CC", "#9933FF", "#CC0033", "#FF6633",
            "#FFFF00", "#33FF66", "#6666FF", "#00CCFF", "#990000",
            "#CC33FF", "#FF99FF", "#996666", "#0000FF", "#6600FF",
            "#CCCC66", "#ff0000", "#1F77B4", "#50EE45", "#FF6525"};
        StringBuilder output = new StringBuilder(512);
        StringBuilder firstOutput = new StringBuilder(128);
        StringBuilder svgOutput = new StringBuilder(64);
        String graphHeight = base.getAttribute("graphHeight", "500");
        String graphWidth = base.getAttribute("graphWidth", "600");
        String barWidth = base.getAttribute("barWidth", null);
        int periodsQuantity = 0;  //numero de periodos a graficar
        
        if (semanticObj != null) {
            GenericObject genericObj = semanticObj.createGenericInstance();
            if (genericObj instanceof Indicator) {
                Indicator indicator = (Indicator) genericObj;
                Iterator<Period> measurablePeriods = null;
                GenericIterator<Series> seriesIt = indicator.listSerieses();
                List<Period> periodsList = new java.util.ArrayList<Period>();
                StringBuilder usedColors = new StringBuilder(32);

                try {
                    measurablePeriods = indicator.listMeasurablesPeriods();
                    if (measurablePeriods == null || !measurablePeriods.hasNext()) {
                        List<Period> lperiods = indicator.listValidPeriods();
                        Collections.sort(lperiods);
                        measurablePeriods = lperiods.iterator();
                    }
                    if (measurablePeriods != null) {
                        while (measurablePeriods.hasNext()) {
                            periodsList.add(measurablePeriods.next());
                        }
                        periodsQuantity = periodsList.size();
                    }
                } catch (UndefinedFrequencyException ex) {
                    out.println("<div class=\"swbform\"><fieldset></fieldset><p>");
                    out.println(paramsRequest.getLocaleString("msgUndefinedFrequencyException"));
                    out.println("</p></div>");
                    out.flush();
                    out.close();
                } catch (InappropriateFrequencyException ex) {
                    out.println("<div class=\"swbform\"><fieldset></fieldset><p>");
                    out.println(paramsRequest.getLocaleString("msgInappropriateFrequencyException"));
                    out.println("</p></div>");
                    out.flush();
                    out.close();
                }
                //Codigo HTML para generar la grafica
                firstOutput.append("<div id=\"graphContainer\">\n");
                firstOutput.append("   <div id=\"chart1\" class=\'with-3d-shadow with-transitions\'>\n");
                firstOutput.append("       <h4 align=\"center\">");
                firstOutput.append(indicator.getTitle());
                firstOutput.append("       </h4>\n");
                firstOutput.append("       <div>\n");
                firstOutput.append("         <input type=\"radio\" name=\"graphType\" id=\"hGraph\" value=\"1\" onclick=\"javascript:showGraph(this);\" checked=\"checked\"><label for=\"hGraph\">Horizontal</label>\n");
                firstOutput.append("         <input type=\"radio\" name=\"graphType\" id=\"vGraph\" value=\"2\" onclick=\"javascript:showGraph(this);\"><label for=\"vGraph\">Vertical</label>\n");
                firstOutput.append("       </div>\n");
                
                output.append("   </div>\n");
                output.append("<script type=\"text/javascript\">\n");
                output.append("long_short_data = [\n");
                short seriesCount = 0;
                //Se recorren todas las series asociadas al indicador
                while (seriesIt != null && seriesIt.hasNext()) {
                    Series graphSeries = seriesIt.next();
                    //Valida que la serie este activa
                    if (graphSeries.isActive() && graphSeries.isValid()) {
                        Format seriesFormat = graphSeries.getFormat();
                        //Valida que la serie contenga periodos asignados
                        if (periodsList.size() > 0) {
                            if (seriesCount > 0) {
                                output.append(",\n");  //separador de series
                            }
                            output.append("{");
                            //Se coloca el identificador de cada serie
                            if (seriesFormat != null) {
                                output.append("  key: \"");
                                output.append(graphSeries.getTitle());
                                output.append(" en ");
                                output.append(seriesFormat.getTitle());
                                output.append("\" ,\n");
                            } else {
                                output.append("  key: \"");
                                output.append(graphSeries.getTitle());
                                output.append("\" ,\n");
                            }
                            /*Lo mantengo por si al final la seleccion aleatoria de colores se decide eliminar
                             short colorIndex = 0;
                             if (seriesCount < colors.length) {
                             colorIndex = seriesCount;
                             } else {
                             colorIndex = (short) (seriesCount % colors.length);
                             }*/
                            //Se selecciona el color de la serie de manera aleatoria
                            short colorIndex = (short) (Math.random() * colors.length);
                            boolean colorAssigned = false;
                            while (!colorAssigned) {
                                if (usedColors.indexOf(colorIndex + ",") == -1) {
                                    usedColors.append(colorIndex);
                                    usedColors.append(",");
                                    colorAssigned = true;
                                } else {
                                    colorIndex = (short) (Math.random() * colors.length);
                                }
                            }

                            //Se coloca el color a utilizar para la serie
                            output.append("  color: '");
                            output.append(colors[colorIndex]);
                            output.append("',\n");
                            output.append("  values: [\n");

                            int periodsCount = 0;
                            //Recorre los periodos y valores de la serie para graficarlos
                            for (Period period : periodsList) {
                                Measure measure = graphSeries.getMeasure(period);
                                if (periodsCount > 0) {
                                    output.append(",\n");
                                }
                                output.append("    {");
                                output.append(" \"label\" : \"");
                                output.append(period.getTitle());
                                output.append("\", ");
                                try {
                                    if (measure.getValue() != 0) {
                                        output.append("\"value\" : ");
                                        output.append(measure.getValue());
                                    }
                                } catch (Exception e) {
                                    output.append("\"value\" : 0.0 ");
                                }
                                output.append(" }");
                                periodsCount++;
                            }
                            output.append("  ]");
                            output.append("}");
                        } else {//En caso de que no haya periodos
                            out.println("<div class=\"swbform\">");
                            out.println("<fieldset>");
                            out.println("</fieldset>");
                            out.println("<p>" + paramsRequest.getLocaleString("msgNotPeriods") + "</p>");
                            out.println("</div>");
                            out.flush();
                            out.close();
                        }
                        seriesCount++;
                    }
                }
                //En caso de que no haya series activas para el indicador
                if (seriesCount == 0) {
                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("</fieldset>");
                    out.println("<p>" + paramsRequest.getLocaleString("msgNotSerieAct") + "</p>");
                    out.println("</div>");
                    out.flush();
                    out.close();
                }

                //Se termina de armar el Javascript para la presentacion de la grafica
                output.append("];\n");
                output.append("var chart;\n");
                output.append("var chart2;\n");
                output.append("nv.addGraph(function() {\n");
                output.append("  chart = nv.models.multiBarHorizontalChart()\n");
                output.append("      .x(function(d) { return d.label })\n");
                output.append("      .y(function(d) { return d.value })\n");
                output.append("    .margin({top: 30, right: 20, bottom: 30, left: 125})\n");
                //output.append("    .margin({top: 0, right: 0, bottom: 0, left: 0})\n");
                output.append("    .transitionDuration(250)\n");
                output.append("    .showControls(true);\n");
                output.append("  chart.yAxis\n");
                output.append("    .tickFormat(d3.format(',.2f'));\n");
                output.append("  d3.select('#chart1 svg')\n");
                output.append("    .datum(long_short_data)\n");
                output.append("    .call(chart);\n");
                output.append("  nv.utils.windowResize(chart.update);\n");
                output.append("  return chart;\n");
                output.append("});\n");
                output.append("nv.addGraph(function() {\n");
                output.append("  chart2 = nv.models.multiBarChart()\n");
                output.append("      .x(function(d) { return d.label })\n");
                output.append("      .y(function(d) { return d.value })\n");
                output.append("      .transitionDuration(350)\n");
                output.append("      .reduceXTicks(true)\n");   /*If 'false', every single x-axis tick label will be rendered.*/
                //output.append("      .rotateLabels(90)\n");      /*Angle to rotate x-axis labels.*/
                output.append("      .staggerLabels(true)\n");     /*Intercala etiquetas en el eje 1 arriba, 1 abajo.*/
                output.append("      .showControls(true)\n");   /*Allow user to switch between 'Grouped' and 'Stacked' mode.*/
                output.append("      .groupSpacing(0.1);\n");    /*Distance between each group of bars.*/
                output.append("  chart2.yAxis\n");
                output.append("      .tickFormat(d3.format(',.2f'));\n");
                output.append("  return chart2;\n");
                output.append("});\n");
                output.append("  function showGraph(radioBtn) {\n");
                output.append("    if (radioBtn.value == 1 && radioBtn.checked) {\n");
                output.append("      d3.select('#chart1 svg g').remove();\n");
                output.append("      d3.select('#chart1 svg')\n");
                output.append("        .datum(long_short_data)\n");
                output.append("        .call(chart);\n");
                output.append("    } else if (radioBtn.value == 2 && radioBtn.checked) {\n");
                output.append("      d3.select('#chart1 svg g').remove();\n");
                output.append("      d3.select('#chart1 svg')\n");
                output.append("        .datum(long_short_data)\n");
                output.append("        .call(chart2);\n");
                output.append("    }\n");
                output.append("  }\n");
                output.append("  </script>\n");
                output.append("</div>\n");

                if (barWidth != null) {
                    //Se calcula el ancho y alto de grafica si se configuró el ancho de cada barra
                    int width4bars = Integer.parseInt(barWidth);
                    int width = width4bars * periodsQuantity * seriesCount + 100 + 30; //100 para etiqueta eje Y; 30 del padding derecho
                    //Se debe cumplir con un mínimo y un máximo del tamaño de la gráfica
                    if (width > 850) {
                        width = 850;
                    } else if (width < 550) {
                        width = 550;
                    }
                    graphWidth = String.valueOf(width);
                    int height = width4bars * periodsQuantity * seriesCount + 30;  //30 para etiqueta eje X
                    if (seriesCount > 2) {  //Para etiquetas de las series a graficar
                        height += (seriesCount % 2) * 20;
                    } else {
                        height += 20;
                    }
                    //Se debe cumplir con un mínimo y un máximo del tamaño de la gráfica
                    if (height > 850) {
                        height = 850;
                    } else if (height < 450) {
                        height = 450;
                    }
                    graphHeight = String.valueOf(height);
                }
                
                svgOutput.append("       <svg style=\"height:");
                svgOutput.append(graphHeight);
                svgOutput.append("px; width:");
                svgOutput.append(graphWidth);
                svgOutput.append("px;");
                svgOutput.append("\"></svg>\n");
                
                out.println(firstOutput.toString());
                out.println(svgOutput.toString());
                out.println(output.toString());
            }
        }
    }

    /**
     * Genera el c&oacute;digo HTML de la gr&aacute;fica (Tabla de datos de un
     * indicador) usado en la exportaci&oacute;n a PDF del componente
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con los
     * datos a exportar(Gr&aacute;fica de la tabla de datos de un indicador)
     * @throws SWBResourceException SWBResourceException SWBResourceException
     * Excepti&oacute;n utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     *
     */
    @Override
    public String doComponentExport(HttpServletRequest request, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        StringBuilder sb = new StringBuilder();
        Resource base = this.getResourceBase();
        String data = request.getParameter("image");
        String uniqueImage = request.getParameter("uniqueImage");
        if (data.trim().length() > 0) {
            float width = base.getAttribute("width") != null
                    ? Float.parseFloat(base.getAttribute("width")) : 550;
            float height = base.getAttribute("height") != null
                    ? Float.parseFloat(base.getAttribute("height")) : 250;

            int dataIndexOf = data.indexOf("svg");
            int lenght = data.length();

            if (!data.contains("xmlns=")) {
                String data1 = data.substring(0, (dataIndexOf + 3));
                String data2 = data.substring((dataIndexOf + 3), lenght);
                data = (data1) + " xmlns=\"http://www.w3.org/2000/svg\" " + (data2);
            }

            data = SWBUtils.TEXT.replaceAll(data, "NaN", "0");

            Document svg = SWBUtils.XML.xmlToDom(data);
            try {
                String destpath = SWBPlatform.getContextPath() + "/work/models/"
                        + paramRequest.getWebPage().getWebSiteId()
                        + "/graphics_" + uniqueImage + ".jpg";
                saveGraphics(svg, paramRequest.getWebPage().getWebSiteId(), width, height, 
                        uniqueImage);

                sb.append("<p><img src=\"");
                sb.append(destpath);
                sb.append("\" alt=\"graphics\"/></p>");
            } catch (Exception ex) {
                log.error("Error try save Image: " + ex);
            }
        }
        return sb.toString();
    }

    /**
     * Crea y almacena una imagen temporal con la gr&aacute;fica de un indicador
     *
     * @param document archivo XML con informaci&oacute;n de tipo SVG
     * (Gr&aacute;fica)
     * @param idWebSite id del ScoreCard
     * @param width ancho de la imagen a generar
     * @param height alto de la imagen a generar
     * @throws Exception Excepti&oacute;n de IO
     */
    private void saveGraphics(Document document, String idWebSite, float width, float height,
            String uniqueImage)
            throws Exception {
        String destpath = UploaderFileCacheUtils.getHomepath() + "/models/" + idWebSite;
        JPEGTranscoder t = new JPEGTranscoder();
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
        t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, width);
        t.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT, height);

        // Set the transcoder input and output.
        TranscoderInput input = new TranscoderInput(document);
        OutputStream ostream = new FileOutputStream(destpath + "/graphics_" + uniqueImage +".jpg");
        TranscoderOutput output = new TranscoderOutput(ostream);

        // Perform the transcoding.
        t.transcode(input, output);
        ostream.flush();
        ostream.close();
    }
}
