/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.accessory.Grapher;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author ana.garcias
 */
public class GraphConfig extends GenericResource {

    public static final String Action_SAVE_CONFIG = "saveConfig";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //super.doView(request, response, paramRequest); //To change body of generated methods, choose Tools | Templates.
        String suri = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semObj = ont.getSemanticObject(suri);
        User user = paramRequest.getUser();
        String data = semObj.getSemanticClass().getName() + semObj.getId();
        SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);
        Indicator indic = null;
        if (semObj != null) {
            indic = (Indicator) semObj.createGenericInstance();
            //Trae la lista valida de series
            List<Series> series = indic.listValidSerieses();
            //Pide al usuario los datos a configurar
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">\n");
            out.println("  dojo.require('dojo.parser');\n");
            out.println("  dojo.require('dijit.layout.ContentPane');\n");
            out.println("  dojo.require('dijit.form.Form');\n");
            out.println("  dojo.require('dijit.form.CheckBox');\n");
            out.println("</script>\n");
            out.println("");
            out.println("<form method=\"post\" id=\"frmAdd" + data + "\" action=\" " + url
                    + "\" class=\"swbform\" type=\"dijit.form.Form\" onsubmit=\""
                    + "submitForm('frmAdd" + data + "');return false;\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + semObj.getURI()
                    + "\">");

            //Valida si tiene series validas asigandas
            if (!series.isEmpty()) {
                String titleGraph = "";
                String titleX = "";
                String titleY = "";
                Series serieGraph = null;
                //Valida si ya tiene un graficador configurado
                Iterator<Grapher> itGraph = new GenericIterator<Grapher>(
                    semObj.listObjectProperties(Indicator.bsc_hasGrapher));
                if (itGraph.hasNext()) {
                    Grapher grapher = itGraph.next();
                    titleGraph = grapher.getTitleGraph();
                    titleX = grapher.getTitleX();
                    titleY = grapher.getTitleY();
                    serieGraph = grapher.getSerieGraph();
                }
                out.println("<fieldset>\n");
                out.println("<table width=\"94%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_titleGraph") + "</th>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_serieGraph") + "</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tr>");
                out.println("<td><input name=\"titleGraph\" size=\"30\" type=\"text\" value=\"" + titleGraph + "\"></input></td>");
                out.println("<td><select name=\"serieGraph\">");
                for (Series serieValidX : series) {
                    if(serieGraph == null){
                        out.println("<option value=\"" + serieValidX.getId() + "\">" +serieValidX.getTitle() + "</option>");
                    }
                    else{
                    out.println("<option value=\"" + serieValidX.getId() + "\"");
                    out.println(serieValidX.getId().equals(String.valueOf(serieGraph.getId())) ? " selected=\"selected\"" : "");
                    out.println(" >" + serieValidX.getTitle() + "</option>");
                    }
                }
                out.println("</select></td>");
                out.println("</tr>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_titleX") + "</th>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_titleY") + "</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tr>");
                out.println("<td><input name=\"titleX\" size=\"30\" type=\"text\" value=\"" + titleX + "\"></input>");
                out.println("</td>");
                out.println("<td><input name=\"titleY\" size=\"30\" type=\"text\" value=\"" + titleY + "\"></input>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>\n");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">"
                        + paramRequest.getLocaleString("lbl_SaveConfig") + "</button>");

            } else //Muestra mensaje de que no hay series asociadas
            {
                out.println("<fieldset>\n");
                out.println("<div>");
                out.println("<thead>");
                out.println("<th align=\"center\">" + paramRequest.getLocaleString("lbl_NotSeries") + "</th>");
                out.println("</thead>");
                out.println("</div>");
                out.println("</fieldset>");
            }
            out.println("</form>");
            if (request.getParameter("lbl_statusMsg") != null
                    && !request.getParameter("lbl_statusMsg").isEmpty()) {
                out.println("<div dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("showStatus('" + request.getParameter("lbl_statusMsg") + "');\n");
                out.println("</script>\n");
                out.println("</div>");
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        //super.processAction(request, response); //To change body of generated methods, choose Tools | Templates.
        String action = response.getAction();
        final String suri = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semObj = ont.getSemanticObject(suri);
        SWBModel model = (SWBModel) semObj.getModel().getModelObject().createGenericInstance();

        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
            String titleGraph = request.getParameter("titleGraph") == null ? "" : request.getParameter("titleGraph");
            String titleX = request.getParameter("titleX") == null ? "" : request.getParameter("titleX");
            String titleY = request.getParameter("titleY") == null ? "" : request.getParameter("titleY");
            String serieGraph = request.getParameter("serieGraph") == null ? "" : request.getParameter("serieGraph");
            Series serie = Series.ClassMgr.getSeries(serieGraph, model);

            //Guarda la configuracion en Grapher
            Indicator indicator = (Indicator) semObj.createGenericInstance();
            Iterator<Grapher> itGraph = new GenericIterator<Grapher>(
                    semObj.listObjectProperties(Indicator.bsc_hasGrapher));
            //Valida si tiene un graficador asociado
            if (itGraph.hasNext()) {
                Grapher grapher = itGraph.next();
                grapher.setSerieGraph(serie);
                grapher.setTitleGraph(titleGraph);
                grapher.setTitleX(titleX);
                grapher.setTitleY(titleY);

            } else //Agrega el graficador al indicador
            {
                Grapher grapher = Grapher.ClassMgr.createGrapher(model);
                grapher.setSerieGraph(serie);
                grapher.setTitleGraph(titleGraph);
                grapher.setTitleX(titleX);
                grapher.setTitleY(titleY);
                indicator.addGrapher(grapher);
            }
        }
        response.setRenderParameter("suri", suri);
        response.setRenderParameter("lbl_statusMsg", response.getLocaleString("lbl_statusMsg"));
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
}
