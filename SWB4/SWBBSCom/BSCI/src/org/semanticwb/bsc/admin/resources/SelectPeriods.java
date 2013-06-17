/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * SelectPeriods es una clase que permite asociar o  desasociar periodos a un objetivo
 * 
 * 
 * @author      Martha Elvia Jiménez Salgado
 * @version     %I%, %G%
 * @since       1.0
 */
public class SelectPeriods extends GenericResource {

    /**
     * Método que se encarga de presentar la forma al usuario para que elija un conjunto de periodos
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException 
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        if (semObj != null) {
            Objective obj = (Objective) semObj.createGenericInstance();
            List objetivesCurrent =  new ArrayList();
            Iterator<Period> itObj = obj.listPeriodObjetives();
            while (itObj.hasNext()) {
                Period periodSave = itObj.next();
                objetivesCurrent.add(periodSave);
            }
            BSC bsc = obj.getBSC();
            Iterator<Period> itPeriods = bsc.listPeriods();
            SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);
            if (itPeriods.hasNext()) {
                out.println("<form method=\"post\" id=\"frmAdd1\" action=\" " + url + "\" class=\"swbform\" type=\"dijit.form.Form\" onsubmit=\"submitForm('frmAdd1');reloadTab('" + obj.getURI() + "');return false;\">");
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
                out.println("<input type=\"hidden\" name=\"ws\" value=\"" + bsc.getId() + "\">");
                out.println("<label>Selecciona los periodos:</label>");
                out.println("<ul>");
                while (itPeriods.hasNext()) {
                    Period period = itPeriods.next();
                    String select = "";
                    if(objetivesCurrent.contains(period)) {
                        select = "checked";
                    }
                    out.println("<li><input id=\"" + period.getId() + "\" name=\"period\" type=\"checkbox\" value=\"" + period.getId() +"\" "
                            + " data-dojo-type=\"dijit.form.CheckBox\" " + select + ">"
                            + "<label for=\"" + period.getId() + "\">" + period.getTitle()
                            + "</label></li>");
                }
                out.println("</ul>");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
                out.println("</form>");
            } else {
                out.println("<p>Debe crearse periodos o asignarse</p>");
            }

        }
    }
    
    /**
     * Método que se encarga de persistir la información de forma segura en un objetivo
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException 
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        String nameSite = request.getParameter("ws");
        WebSite ws = WebSite.ClassMgr.getWebSite(nameSite);
        Objective objective = null;
        if (SWBResourceURL.Action_ADD.equals(action) && ws != null) {
            String[] periods = request.getParameterValues("period");
            String suri = request.getParameter("suri");
            objective= (Objective) SemanticObject.getSemanticObject(suri).createGenericInstance();
            if (objective != null) {
                Iterator<Period> itPeriods = objective.listPeriodObjetives();
                if (itPeriods.hasNext()) {
                    while (itPeriods.hasNext()) {
                        Period period = itPeriods.next();
                        objective.removePeriodObjetive(period);
                    }
                }
                if (periods != null && periods.length > 0) {
                    for (int i = 0; i < periods.length; i++) {
                        Period periodCheck = Period.ClassMgr.getPeriod(periods[i], ws);
                        if (periodCheck != null) {
                            objective.addPeriodObjetive(periodCheck);
                        }
                    }
                }
            }
        }
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
}
