/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.GenericSemResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


/**
 * Genera el c&oacute;digo HTML para presentar una gr&aacute;fica de Gantt con los datos 
 * de los entregables activos de la iniciativa cuyo uri se recibe.
 * @author Jose Jimenez
 */
public class GanttChart extends GenericResource {

    
    /** Realiza operaciones en la bitacora de eventos. */
    private static Logger log = SWBUtils.getLogger(GanttChart.class);
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semanticObj = SemanticObject.createSemanticObject(suri);
        StringBuilder output = new StringBuilder(512);
        JSONArray ganttData = new JSONArray();
        int deliverableCount = 0;

        if (semanticObj != null) {
            GenericObject genericObj = semanticObj.createGenericInstance();
            if (genericObj instanceof Initiative) {
                Initiative initiative = (Initiative) genericObj;
                
                GenericIterator<Deliverable> iterator = initiative.listDeliverables();
                while (iterator != null && iterator.hasNext()) {
                    deliverableCount++;
                    Deliverable deli = iterator.next();
                    if (deli.isActive()) {
                        try {
                            JSONObject element = new JSONObject();
                            element.append("id", Integer.valueOf(deliverableCount));
                            element.append("name", deli.getTitle());
                            JSONArray series = new JSONArray();
                            JSONObject planned = new JSONObject();
                            planned.put("name", "Planeado");
                            if (deli.getPlannedStart() != null) {
                                planned.put("start", "new Date()");
                            }
                            if (deli.getPlannedEnd() != null) {
                                planned.put("end", "new Date()");
                            }
                            planned.put("color", "\"#BDB4F6\"");
                            series.put(planned);
                            JSONObject actual = new JSONObject();
                            if (deli.getActualStart() != null) {
                                actual.put("name", "Real");
                                actual.put("start", "new Date()");
                                if (deli.getActualEnd() != null) {
                                    actual.put("end", "new Date()"); //con actualEnd
                                } else {
                                    actual.put("end", "new Date()"); //con actualStart
                                }
                                //En base al status asignar color
                                actual.put("color", "\"#70DB70\"");
                                series.put(actual);
                            }
                            element.put("series", series);
                            ganttData.put(element);
                        } catch (JSONException jsone) {
                            GanttChart.log.error("Al generar estructura de datos", jsone);
                        }
                    }
                }
                
                /*
                 * Incluir los siguientes en el código de la pagina
	<link rel="stylesheet" type="text/css" href="../lib/jquery-ui-1.8.4.css" />
	<link rel="stylesheet" type="text/css" href="reset.css" />
	<link rel="stylesheet" type="text/css" href="../jquery.ganttView.css" />
        * 
        * 
	<script type="text/javascript" src="../lib/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../lib/date.js"></script>
	<script type="text/javascript" src="../lib/jquery-ui-1.8.4.js"></script>
	<script type="text/javascript" src="../jquery.ganttView.js"></script>
                 */
                output.append("");
                output.append("<div id=\"ganttChart\"></div>\n");
                output.append("<br/><br/>\n");
                output.append("<div id=\"eventMessage\"></div>\n");
                output.append("  <script type=\"text/javascript\">\n");
                output.append("    var ganttData = ");
                try {
                    output.append(ganttData.toString(2));
                } catch(JSONException jsone) {
                    GanttChart.log.error("Al presentar estructura de datos", jsone);
                    output.append("null");
                }
                output.append(";\n");
                output.append("    $(function () {\n");
                output.append("      $(\"#ganttChart\").ganttView({ \n");
                output.append("         data: ganttData,\n");
                output.append("         slideWidth: 800,\n");
                output.append("         lang: 'es',\n");
                output.append("         behavior: {\n");
                output.append("             clickable: false,\n");
                output.append("             draggable: false,\n");
                output.append("             resizable: false\n");
                output.append("         }\n");
                output.append("     });\n");
                output.append("   });\n");
                output.append(" </script>\n");
                output.append("");
            }
        }
        out.println(output.toString());
    }
    
    
}
