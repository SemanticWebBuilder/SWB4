/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.util.UploaderFileCacheUtils;
import org.w3c.dom.Document;

/**
 * Genera el c&oacute;digo HTML para presentar una gr&aacute;fica de Gantt con
 * los datos de los entregables activos de la iniciativa cuyo uri se recibe.
 *
 * @author Jose Jimenez
 */
public class GanttChart extends GenericResource {

    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static final Logger log = SWBUtils.getLogger(GanttChart.class);
     public static final String Mode_PNGImage = "png";
    
    /**
     * Genera el c&oacute;digo HTML necesario para la presentaci&oacute;n de la
     * gr&aacute;fica de Gantt que se puede trazar con las fechas de una
     * {@code Initiative} y de sus elementos {@code Deliverable} asociados
     *
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se genera en base al contenido de
     * la petici&oacute;n
     * @param paramRequest objeto por el que se accede a varios objetos
     * exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semanticObj = SemanticObject.createSemanticObject(suri);
        StringBuilder output = new StringBuilder(512);
        StringBuilder dataOut = new StringBuilder(512);

        if (semanticObj != null) {
            GenericObject genericObj = semanticObj.createGenericInstance();
            if (genericObj instanceof Initiative) {
                Initiative initiative = (Initiative) genericObj;
                Date today = new Date();
                int countDeliverable = 0;

                GenericIterator<Deliverable> iterator = initiative.listDeliverables();
                dataOut.append("[");
                if (initiative.getPlannedStart() != null && initiative.getPlannedEnd() != null) {
                    dataOut.append("{");
                    dataOut.append("  taskName : \"");
                    dataOut.append(initiative.getTitle());
                    dataOut.append(paramRequest.getLocaleString("lbl_planned"));
                    dataOut.append("\",\n");
                    dataOut.append("  startDate : new Date(");
                    dataOut.append(initiative.getPlannedStart().getTime());
                    dataOut.append("),\n");
                    dataOut.append("  endDate : new Date(");
                    dataOut.append(initiative.getPlannedEnd().getTime());
                    dataOut.append("),\n");
                    dataOut.append("  status : \"#33B5E5\"");
                    dataOut.append("},\n");
                    if (initiative.getActualStart() != null) {
                        dataOut.append("{");
                        dataOut.append("  taskName : \"");
                        dataOut.append(initiative.getTitle());
                        dataOut.append(paramRequest.getLocaleString("lbl_actual"));
                        dataOut.append("\",\n");
                        dataOut.append("  startDate : new Date(");
                        dataOut.append(initiative.getActualStart().getTime());
                        dataOut.append("),\n");
                        dataOut.append("  endDate : new Date(");
                        if (initiative.getActualEnd() != null) {
                            dataOut.append(initiative.getActualEnd().getTime());
                        } else {
                            dataOut.append(today.getTime());
                        }
                        dataOut.append("),\n");
                        dataOut.append("  status : ");
                        //En base al status asignar color
                        if (initiative.getState() != null) {
                            dataOut.append("\"");
                            dataOut.append(initiative.getState().getColorHex());
                            dataOut.append("\"");
                        } else {
                            dataOut.append("\"#CCCCCC\"");
                        }
                        dataOut.append("},\n");
                    }
                }
                while (iterator != null && iterator.hasNext()) {
                    Deliverable deli = iterator.next();
                    if (deli.isActive()) {
                        boolean anotherDeliverable = false;
                        try {
                            if (deli.getPlannedStart() != null && deli.getPlannedEnd() != null) {
                                if (countDeliverable > 0) {
                                    dataOut.append(",\n");
                                }
                                dataOut.append("{");
                                dataOut.append("  taskName : \"");
                                dataOut.append(deli.getTitle());
                                dataOut.append(paramRequest.getLocaleString("lbl_planned"));
                                dataOut.append("\",\n");
                                dataOut.append("  startDate : new Date(");
                                dataOut.append(deli.getPlannedStart().getTime());
                                dataOut.append("),\n");
                                dataOut.append("  endDate : new Date(");
                                dataOut.append(deli.getPlannedEnd().getTime());
                                dataOut.append("),\n");
                                dataOut.append("  status : \"#33B5E5\"");
                                dataOut.append("}");
                                countDeliverable++;
                                anotherDeliverable = true;
                            }
                            if (deli.getActualStart() != null) {
                                if (anotherDeliverable) {
                                    dataOut.append(",\n");
                                }
                                dataOut.append("{");
                                dataOut.append("  taskName : \"");
                                dataOut.append(deli.getTitle());
                                dataOut.append(paramRequest.getLocaleString("lbl_actual"));
                                dataOut.append("\",\n");
                                dataOut.append("  startDate : new Date(");
                                dataOut.append(deli.getActualStart().getTime());
                                dataOut.append("),\n");
                                dataOut.append("  endDate : new Date(");
                                if (deli.getActualEnd() != null) {
                                    dataOut.append(deli.getActualEnd().getTime());
                                } else {
                                    dataOut.append(today.getTime());
                                }
                                dataOut.append("),\n");
                                dataOut.append("  status : ");
                                //En base al status asignar color
                                if (deli.getAutoStatus() != null) {
                                    dataOut.append(deli.getAutoStatus().getColorHex());
                                } else {
                                    dataOut.append("\"#CCCCCC\"");
                                }
                                dataOut.append("}\n");
                            }
                        } catch (SWBResourceException e) {
                            GanttChart.log.error("Al generar estructura de datos", e);
                        }
                    }
                }
                dataOut.append("]");

                output.append("");
                output.append("<div id=\"ganttChart\">\n");
                output.append("  <script type=\"text/javascript\" src=\"");
                output.append(SWBPlatform.getContextPath());
                output.append("/swbadmin/js/d3/gantt-chart-d3.js\"></script>\n");
                output.append("</div>\n");
                output.append("  <script type=\"text/javascript\">\n");
                output.append("    var tasks = ");
                output.append(dataOut.toString());
                output.append(";\n");
                output.append("  var taskNames = new Array();\n");
                output.append("  for (i = 0; i < tasks.length; i++) {\n");
                output.append("    if (taskNames.indexOf(tasks[i].taskName) == -1) {\n");
                output.append("      taskNames.push(tasks[i].taskName);\n");
                output.append("    }\n");
                output.append("  }\n");
                output.append("  tasks.sort(function(a, b) {\n");
                output.append("    return a.endDate - b.endDate;\n");
                output.append("  });\n");
                output.append("  var maxDate = tasks[tasks.length - 1].endDate;\n");
                output.append("  tasks.sort(function(a, b) {\n");
                output.append("    return a.startDate - b.startDate;\n");
                output.append("  });\n");
                output.append("  var minDate = tasks[0].startDate;\n");
                output.append("  var format = \"%d/%m/%Y\";\n");
                output.append("  \n");
                output.append("  var gantt = d3.gantt().taskTypes(taskNames).tickFormat(format).timeDomainMode(\"fit\");\n");
                output.append("  gantt(tasks);\n");
                output.append("  \n");
                output.append("  \n");
                output.append("  </script>\n");
                output.append("");

                out.println(output.toString());

                SWBResourceURL exportUrl = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
                out.println(" <form id=\"svgform\" accept-charset=\"utf-8\" method=\"post\" action=\"#\">");
                out.println("  <input type=\"hidden\" id=\"data\" name=\"data\" value=\"\" />");
                out.println("  <input type=\"button\" value=\"Imagen\" onclick=\"getFile('" + exportUrl.setMode(Mode_PNGImage) + "')\"  />");
                out.println(" </form>");

                out.println(" <script type=\"text/javascript\">");
                out.println("  function getFile(url) {");
                out.println("   var form = document.getElementById('svgform');");
                out.println("   var svg = document.getElementsByTagName('svg')[0];");
                out.println("   var svg_xml = (new XMLSerializer).serializeToString(svg);");
                out.println("   form.action = url;");
                out.println("   form['data'].value = svg_xml;");
                out.println("   form.submit();");
                out.println("  };");
                out.println(" </script>");
            }
        } 
    }

    /**
     * Clase que recibe el svg y lo trasnforma a jpg. la imagen se almacena en
     * un directorio
     *
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se genera en base al contenido de
     * la petici&oacute;n
     * @param paramRequest objeto por el que se accede a varios objetos
     * exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    public void doGetPNGImage(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        WebSite webSite = getResourceBase().getWebSite();

        if (webSite instanceof BSC) {
            String data = request.getParameter("data");
            int dataIndexOf = data.indexOf("svg");
            int lenght = data.length();

            String data1 = data.substring(0, (dataIndexOf + 3));
            String data2 = data.substring((dataIndexOf + 3), lenght);
            data = (data1) + " xmlns=\"http://www.w3.org/2000/svg\"" + (data2);
            data = SWBUtils.TEXT.replaceAll(data, "NaN", "0");
            Document svg = SWBUtils.XML.xmlToDom(data);

            String destpath = UploaderFileCacheUtils.getHomepath() + "/models/" + paramRequest.getWebPage().getWebSiteId();
            JPEGTranscoder t = new JPEGTranscoder();
            t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
            // Set the transcoder input and output.
            TranscoderInput input = new TranscoderInput(svg);
            OutputStream ostream = new FileOutputStream(destpath + "/ganttImage.jpg");
            TranscoderOutput output = new TranscoderOutput(ostream);
            try {
                // Perform the transcoding.
                t.transcode(input, output);
            } catch (TranscoderException ex) {
                java.util.logging.Logger.getLogger(GanttChart.class.getName()).log(Level.SEVERE, null, ex);
            }
            ostream.flush();
            ostream.close();
        }
        
        //SWBResourceURL url = paramRequest.getRenderUrl();
        //url.setMode(SWBResourceURL.Mode_VIEW);

    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
       if (Mode_PNGImage.equals(mode)) {
            doGetPNGImage(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
