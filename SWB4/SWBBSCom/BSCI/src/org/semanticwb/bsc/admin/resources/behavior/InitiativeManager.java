package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.InitiativeAssignable;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
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
 * InitiativeManager es una clase que permite asignar y desasignar inicitaivas a
 * un objetivo
 *
 * @ Version 1.0
 * @author Carlos Ramos
 */
public class InitiativeManager extends GenericResource {

    public static final String Action_UPDT_ACTIVE = "updactv";
    public static final String Action_ACTIVE_ALL = "actall";
    public static final String Action_DEACTIVE_ALL = "deactall";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");

        User user = paramRequest.getUser();
        if(user==null || !user.isSigned())
        {
            response.sendError(403);
            return;
        }
        final String lang = user.getLanguage();
        
        String suri = request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();        
        SemanticObject semObj = ont.getSemanticObject(suri);
        
        if (semObj != null)
        {
            BSC bsc = (BSC)semObj.getModel().getModelObject().getGenericInstance();
            SWBResourceURL urlAdd;
                
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("  dojo.require('dojo.parser');");
            out.println("  dojo.require('dijit.layout.ContentPane');");
            out.println("  dojo.require('dijit.form.CheckBox');");
            out.println("</script>");

            out.println("<div class=\"swbform\">");
            out.println("<fieldset>\n");
            out.println("<table width=\"98%\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>" + paramRequest.getLocaleString("lbl_title") + "</th>");
            out.println("<th>" + paramRequest.getLocaleString("lbl_responsible") + "</th>");
            out.println("<th>" + paramRequest.getLocaleString("lbl_area") + "</th>");
            out.println("<th>" + paramRequest.getLocaleString("lbl_active") + "</th>");
            out.println("<th>" + paramRequest.getLocaleString("lbl_relate") + "</th>");
            out.println("</tr>");
            out.println("</thead>");
            
            Iterator<Initiative> itInit = bsc.listInitiatives();
            while (itInit.hasNext()) {                    
                Initiative initiative = itInit.next();

                if (  (initiative.isValid() && user.haveAccess(initiative)) || (!initiative.isActive() && semObj.hasObjectProperty(InitiativeAssignable.bsc_hasInitiative, initiative.getSemanticObject()) && user.haveAccess(initiative))  ) {
                    urlAdd = paramRequest.getActionUrl();
                    urlAdd.setParameter("suri", suri);
                    urlAdd.setParameter("sval", initiative.getId());
                    urlAdd.setAction(Action_UPDT_ACTIVE);
                    out.println("<tr>");
                    // Título de la iniciativa
                    out.println("<td>");
                    out.print("<a href=\"#\" onclick=\"addNewTab('" + initiative.getURI() + "','");
                    out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + initiative.getTitle());
                    out.println("');return false;\" >" + (initiative.getTitle(lang)==null?(initiative.getTitle()==null?paramRequest.getLocaleString("lbl_undefined"):initiative.getTitle().replaceAll("'","")):initiative.getTitle(lang).replaceAll("'","")) + "</a>");                  
                    out.println("</td>");
                    // Responsable
                    out.println("<td>" + (initiative.getInitiativeFacilitator()==null ? paramRequest.getLocaleString("lbl_undefined") : initiative.getInitiativeFacilitator().getFullName()) + "</td>");
                    // Área
                    out.println("<td>" + (initiative.getArea()==null ? paramRequest.getLocaleString("lbl_undefined"):initiative.getArea()) + "</td>");
                    // Activo?
                    out.println("<td>"+(initiative.isActive()?paramRequest.getLocaleString("lbl_isActive"):paramRequest.getLocaleString("lbl_isNotActive"))+"</td>");
                    // Asignar
                    out.println("<td>");
                    out.println("<input type=\"checkbox\" name=\"initiative\" ");
                    out.println("onchange=\"submitUrl('" + urlAdd + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" ");
                    out.println(" dojoType=\"dijit.form.CheckBox\" value=\"" + initiative.getId() + "\" " + (semObj.hasObjectProperty(InitiativeAssignable.bsc_hasInitiative, initiative.getSemanticObject())?"checked=\"checked\"":"") + " /></td>");
                    out.println("</td>");
                    out.println("</tr>");
                }
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
            urldesAll.setAction(Action_DEACTIVE_ALL);
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urldesAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lbl_DesActiveAll") + "</button>");
            out.println("</fieldset>\n");
            out.println("</div>");

            if (request.getParameter("statmsg") != null && !request.getParameter("statmsg").isEmpty()) {
                out.println("<div dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("showStatus('" + request.getParameter("statmsg") + "');\n");
                out.println("</script>\n");
                out.println("</div>");
            }
        }
        else
        {
            response.getWriter().print("objeto semántico no ubicado");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final String action = response.getAction();
        final String suri = request.getParameter("suri");
        
        response.setAction(SWBResourceURL.Action_EDIT);
        response.setRenderParameter("suri", suri);
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semObj = ont.getSemanticObject(suri);
        if(semObj==null) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
            return;
        }
        
        User user = response.getUser();
        if(!user.isSigned() || !user.haveAccess(semObj.getGenericInstance())) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgUnauthorizedUser"));
            return;
        }
        
        BSC model = (BSC)semObj.getModel().getModelObject().getGenericInstance();
        InitiativeAssignable obj = (InitiativeAssignable) semObj.getGenericInstance();
        
        if(Action_UPDT_ACTIVE.equalsIgnoreCase(action)) {
            final String initiativeId = request.getParameter("sval");
            if(initiativeId!=null)
            {
                Initiative initiative = null;
                if(Initiative.ClassMgr.hasInitiative(initiativeId, model)) {
                    initiative = Initiative.ClassMgr.getInitiative(initiativeId, model);
                    if(obj.hasInitiative(initiative)) {
                        obj.removeInitiative(initiative);
                        response.setRenderParameter("statmsg", response.getLocaleString("msgDeallocatedInitiative"));
                    }else {
                        obj.addInitiative(initiative);
                        response.setRenderParameter("statmsg", response.getLocaleString("msgAssignedInitiative"));
                    }
                }else {
                    response.setRenderParameter("statmsg", "objeto semantico no ubicado");
                }
            }
            else
            {
                response.setRenderParameter("statmsg", "Objeto semantico no ubicado.");
            }
        }else if(Action_ACTIVE_ALL.equalsIgnoreCase(action)) {
            Iterator<Initiative> initiatives = model.listValidInitiative().iterator();
            if(initiatives.hasNext()) {
                obj.removeAllInitiative();
                while(initiatives.hasNext()) {
                    obj.addInitiative(initiatives.next());
                }
                response.setRenderParameter("statmsg", response.getLocaleString("msgAssignedInitiatives"));
            }
        }else if (Action_DEACTIVE_ALL.equalsIgnoreCase(action)) {
            obj.removeAllInitiative();
            response.setRenderParameter("statmsg", response.getLocaleString("msgDeallocatedInitiatives"));
        }
    }
}
