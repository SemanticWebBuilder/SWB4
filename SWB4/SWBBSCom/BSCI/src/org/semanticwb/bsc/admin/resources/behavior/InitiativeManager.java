
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Undeleteable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * InitiativeManager es una clase que permite asignar y desasignar inicitaivas a
 * un objetivo
 * @ Version 1.0
 * @author Ana Laura García
 */
public class InitiativeManager extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        User user = paramRequest.getUser();
        Initiative.ClassMgr.listInitiatives(getResourceBase().getWebSite());
        out.println("<script type=\"text/javascript\">\n");
        out.println("  dojo.require('dojo.parser');\n");
        out.println("  dojo.require('dijit.layout.ContentPane');\n");
        out.println("  dojo.require('dijit.form.Form');\n");
        out.println("  dojo.require('dijit.form.CheckBox');\n");
        out.println("</script>\n");

        if (semObj != null) {
            BSC bsc = (BSC) semObj.getModel().getModelObject().createGenericInstance();
            Iterator<Initiative> itObj = new GenericIterator<Initiative>(
                    semObj.listObjectProperties(Objective.bsc_hasInitiative));
            List initiativeCurrent = new ArrayList();
            while (itObj.hasNext()) {
                Initiative it = itObj.next();
                initiativeCurrent.add(it);
            }

            Iterator<Initiative> itInit = bsc.listInitiatives();
            SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);
            if (itInit != null && itInit.hasNext()) {
                String data = semObj.getSemanticClass().getName() + semObj.getId();

                out.println("<form method=\"post\" id=\"frmAdd" + data + "\" action=\" " + url
                        + "\" class=\"swbform\" type=\"dijit.form.Form\" onsubmit=\""
                        + "submitForm('frmAdd" + data + "');return false;\">");
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + semObj.getURI()
                        + "\">");
                out.println("<input type=\"hidden\" name=\"ws\" value=\"" + bsc.getId() + "\">");
                out.println("<fieldset>\n");
                out.println("<label>" + paramRequest.getLocaleString("selectInitiatives") + "</label>");
                out.println("<ul>");
                while (itInit.hasNext()) {
                    Initiative initiative = itInit.next();

                    if (initiative.isActive() && user.haveAccess(initiative)) {
                        String select = "";
                        String title = initiative.getTitle(user.getLanguage()) == null ? initiative.getTitle()
                                : initiative.getTitle(user.getLanguage());
                        if (initiativeCurrent.contains(initiative)) {
                            System.out.println("Ya hay iniciativas seleccionadas...");
                            select = "checked";
                        }
                        out.println("<li><input id=\"" + initiative.getId() + "\" name=\"initiative" + data
                                + "\" type=\"checkbox\" value=\"" + initiative.getId() + "\" "
                                + " data-dojo-type=\"dijit.form.CheckBox\" " + select + ">"
                                + "<label for=\"" + initiative.getId() + "\">" + title
                                + "</label></li>");
                    }
                }
                out.println("</ul>");
                out.println("</fieldset>\n");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">"
                        + paramRequest.getLocaleString("save") + "</button>");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" "
                        + "onClick=\"reloadTab('" + semObj.getURI() + "');\">"
                        + paramRequest.getLocaleString("cancel") + "</button>");
                out.println("</form>");
            } else {
                out.println("<p>" + paramRequest.getLocaleString("initiativesEstablished") + "</p>");
            }
            if (request.getParameter("statusMsg") != null
                    && !request.getParameter("statusMsg").isEmpty()) {
                out.println("<div dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("showStatus('" + request.getParameter("statusMsg") + "');\n");
                out.println("</script>\n");
                out.println("</div>");
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws
            SWBResourceException, IOException {
        String action = response.getAction();
        String nameSite = request.getParameter("ws");
        WebSite ws = WebSite.ClassMgr.getWebSite(nameSite);
        SemanticObject semObj = null;
        String suri = "";

        if (SWBResourceURL.Action_ADD.equals(action) && ws != null) {
            String data = null;
            String[] initiatives = null;
            suri = request.getParameter("suri");
            semObj = SemanticObject.getSemanticObject(suri);
            data = semObj != null ? semObj.getSemanticClass().getName() + semObj.getId() : "";
            initiatives = request.getParameterValues("initiative" + data);
            if (semObj != null && initiatives != null && initiatives.length > 0) {
                if (semObj.createGenericInstance() instanceof Objective) {
                    Objective obj = (Objective) semObj.createGenericInstance();
                    for (int i = 0; i < initiatives.length; i++) {
                        Initiative initCheck = Initiative.ClassMgr.getInitiative(initiatives[i], ws);
                        if (initCheck != null) {
                            obj.addInitiative(initCheck);
                        }
                    }
                }
            }
            if (semObj != null && initiatives == null) {
                removeInitiativesInSemObj(semObj);
            }
        }
        response.setRenderParameter("suri", suri);
        response.setRenderParameter("statusMsg", response.getLocaleString("statusMsg"));
        response.setMode(SWBResourceURL.Mode_VIEW);
    }

    private void removeInitiativesInSemObj(SemanticObject semObj) {
        Iterator<SemanticObject> itInitiative = (Iterator<SemanticObject>) semObj.listObjectProperties(Objective.bsc_hasInitiative);

        if (itInitiative != null && itInitiative.hasNext()) {
            while (itInitiative.hasNext()) {
                SemanticObject initiative = itInitiative.next();
                semObj.removeObjectProperty(Objective.bsc_hasInitiative, initiative);
                Iterator<SemanticObject> itObjs = initiative.listRelatedObjects();
                if (itObjs != null && itObjs.hasNext()) {
                    initiative.setBooleanProperty(Undeleteable.swb_undeleteable, false);
                }
            }
        }
    }
}
