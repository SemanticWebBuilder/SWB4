package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Undeleteable;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * InitiativeManager es una clase que permite asignar y desasignar inicitaivas a
 * un objetivo
 *
 * @ Version 1.0
 * @author Ana Laura García
 */
public class InitiativeManager extends GenericResource {

    public static final String Action_ACTIVE_ALL = "actall";
    public static final String Action_DESACTIVE_ALL = "desactall";
    public static final String ACTION_ADD_Init = "actInit";

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

            Iterator<Initiative> itInit = bsc.listValidInitiative().iterator();
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
                out.println("<table width=\"98%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_title") + "</th>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_responsible") + "</th>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_area") + "</th>");
                out.println("<th>" + paramRequest.getLocaleString("lbl_associate") + "</th>");
                out.println("</tr>");
                out.println("</thead>");

                while (itInit.hasNext()) {
                    Initiative initiative = itInit.next();
                    SWBResourceURL urlAdd = paramRequest.getActionUrl();
                    urlAdd.setParameter("suri", suri);
                    urlAdd.setParameter("sval", initiative.getId());
                    urlAdd.setAction(ACTION_ADD_Init);
                    out.println("<tr><td>");
                    out.print("<a href=\"#\" onclick=\"addNewTab('" + initiative.getURI() + "','");
                    out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + initiative.getTitle());
                    out.println("');return false;\" >" + (initiative.getTitle()!= null ? initiative.getTitle() : paramRequest.getLocaleString("lbl_undefined")) + "</a>");                  
                    out.println("</td>"
//                            + "<td>" + (initiative.getRisponsableInitiative() != null ? initiative.getRisponsableInitiative() : paramRequest.getLocaleString("lbl_undefined")) + "</td>"
//                            + "<td>" + (initiative.getArea()!= null ? initiative.getArea() : paramRequest.getLocaleString("lbl_undefined")) + "</td>"
                            + "<td>"
                            + "<input type=\"checkbox\" name=\"initiative" + data + "\" "
                            + "onchange=\"submitUrl('" + urlAdd + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" "
                            + " dojoType=\"dijit.form.CheckBox\" value=\"" + initiative.getId() + "\" " + (initiative.isActive() ? "checked=\"checked\"" : "") + " /></td>"
                            + "</td></tr>");
                }
                out.println("</table>");
                out.println("</fieldset>\n");
                out.println("<fieldset>");

                SWBResourceURL urlAll = paramRequest.getActionUrl();
                urlAll.setParameter("suri", suri);
                urlAll.setAction(Action_ACTIVE_ALL);
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lbl_ActiveAll") + "</button>");

                SWBResourceURL urldesAll = paramRequest.getActionUrl();
                urldesAll.setParameter("suri", suri);
                urldesAll.setAction(Action_DESACTIVE_ALL);
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urldesAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lbl_DesActiveAll") + "</button>");
                out.println("</fieldset>\n");
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
        final String suri = request.getParameter("suri");
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        SWBModel model = (SWBModel) semObj.getModel().getModelObject().createGenericInstance();
        Objective obj = (Objective) semObj.createGenericInstance();

        if (ACTION_ADD_Init.equalsIgnoreCase(action)) {
            String sval = request.getParameter("sval");
            Initiative initCheck = Initiative.ClassMgr.getInitiative(sval, model);
            if (!initCheck.isActive()) {
                initCheck.setActive(Boolean.TRUE);
                obj.addInitiative(initCheck);
            } else {
                initCheck.setActive(Boolean.FALSE);
                obj.removeInitiative(initCheck);
            }
        } else if (Action_ACTIVE_ALL.equalsIgnoreCase(action)) {
            if (semObj != null) {
                BSC bsc = (BSC) semObj.getModel().getModelObject().createGenericInstance();
                Iterator<Initiative> itInit = bsc.listInitiatives();
                while (itInit.hasNext()) {
                    Initiative initiative = itInit.next();
                    initiative.setActive(Boolean.TRUE);
                    obj.addInitiative(initiative);
                }
            }
        } else if (Action_DESACTIVE_ALL.equalsIgnoreCase(action)) {
            if (semObj != null) {
                BSC bsc = (BSC) semObj.getModel().getModelObject().createGenericInstance();
                Iterator<Initiative> itInit = bsc.listInitiatives();
                while (itInit.hasNext()) {
                    Initiative initiative = itInit.next();
                    initiative.setActive(Boolean.FALSE);
                }
                obj.removeAllInitiative();
            }
        }
        response.setRenderParameter("suri", suri);
        response.setRenderParameter("statusMsg", response.getLocaleString("statusMsg"));
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
}
