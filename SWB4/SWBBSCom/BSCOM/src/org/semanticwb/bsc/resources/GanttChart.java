/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


/**
 * Genera el c&oacute;digo HTML para presentar una gr&aacute;fica de Gantt con los datos 
 * de los entregables activos de la iniciativa cuyo uri se recibe.
 * @author Jose Jimenez
 */
public class GanttChart extends GenericResource {

    
    /** Realiza operaciones en la bitacora de eventos. */
    private static final Logger log = SWBUtils.getLogger(GanttChart.class);
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semanticObj = SemanticObject.createSemanticObject(suri);
        StringBuilder output = new StringBuilder(512);
        StringBuilder dataOut = new StringBuilder(512);
        SimpleDateFormat format = new SimpleDateFormat("yyyy,MM,dd");

        if (semanticObj != null) {
            GenericObject genericObj = semanticObj.createGenericInstance();
            if (genericObj instanceof Initiative) {
                Initiative initiative = (Initiative) genericObj;
                Date today = new Date();
                int countDeliverable = 0;
                
                GenericIterator<Deliverable> iterator = initiative.listDeliverables();
                dataOut.append("[");
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
//                                JSONObject planned = new JSONObject();
                                dataOut.append("  taskName : \"");
                                dataOut.append(deli.getTitle());
                                dataOut.append(" - Planeado\",\n");
                                dataOut.append("  startDate : new Date(" );
                                dataOut.append(format.format(deli.getPlannedStart()));
                                dataOut.append("),\n");
                                dataOut.append("  endDate : new Date(");
                                dataOut.append(format.format(deli.getPlannedEnd()));
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
                                dataOut.append(" - Real\",\n");
                                dataOut.append("  startDate : new Date(" );
                                dataOut.append(format.format(deli.getActualStart()));
                                dataOut.append("),\n");
                                dataOut.append("  endDate : new Date(");
                                if (deli.getActualEnd() != null) {
                                    dataOut.append(format.format(deli.getActualEnd()));
                                } else {
                                    dataOut.append(format.format(today));
                                }
                                dataOut.append("),\n");
                                dataOut.append("  status : ");
                                //En base al status asignar color
                                if (deli.getAutoStatus() != null) {
                                    dataOut.append(deli.getAutoStatus().getIconClass());
                                } else {
                                    dataOut.append("\"#CCCCCC\"");
                                }
                                dataOut.append("}\n");
                            }
                        } catch (Exception e) {
                            GanttChart.log.error("Al generar estructura de datos", e);
                        }
                    }
                }
                dataOut.append("]");
                
                output.append("");
                output.append("<div id=\"ganttChart\">\n");
                output.append("  <script type=\"text/javascript\" src=\"");
                output.append(SWBPlatform.getContextPath());
                output.append("/swbadmin/js/gantt-chart-d3.js\"></script>\n");
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
            }
        }
        out.println(output.toString());
    }
    
    
}
