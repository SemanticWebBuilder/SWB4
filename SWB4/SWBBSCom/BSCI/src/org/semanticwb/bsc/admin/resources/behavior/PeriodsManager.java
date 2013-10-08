/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.semanticwb.bsc.Seasonable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
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
 * PeriodsManager es una clase que permite asociar o desasociar periodos a un
 * objetivo
 *
 *
 * @author Martha Elvia Jim&oacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class PeriodsManager extends GenericResource {

    /**
     * M&eacute;todo que se encarga de presentar la forma al usuario para que
     * elija un conjunto de periodos
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException Excepti&oacute;n utilizada para recursos de
     * SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        User user = paramRequest.getUser();
        out.println("<script type=\"text/javascript\">\n");
        out.println("  dojo.require('dojo.parser');\n");
        out.println("  dojo.require('dijit.layout.ContentPane');\n");
        out.println("  dojo.require('dijit.form.Form');\n");
        out.println("  dojo.require('dijit.form.CheckBox');\n");
        out.println("  dojo.require('dijit.form.Button');\n");
        out.println("</script>\n");

        if (semObj != null) {
            BSC bsc = (BSC) semObj.getModel().getModelObject().createGenericInstance();
            Iterator<Period> itObj = new GenericIterator<Period>(
                    semObj.listObjectProperties(Seasonable.bsc_hasPeriod));
            List objetivesCurrent = new ArrayList();
            Iterator<Period> itPeriods = null;
            GenericObject genericObject = semObj.createGenericInstance();
            SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);

            while (itObj.hasNext()) {
                Period periodSave = itObj.next();

                objetivesCurrent.add(periodSave);
            }
            if (genericObject instanceof Indicator) {
                SemanticObject objParent = semObj.getObjectProperty(Indicator.bsc_objectiveInv);

                itPeriods = new GenericIterator<Period>(
                        objParent.listObjectProperties(Seasonable.bsc_hasPeriod));
            } else {
                itPeriods = Period.ClassMgr.listPeriods(bsc);
            }
            
            if (itPeriods != null && itPeriods.hasNext()) {
                String data = semObj.getSemanticClass().getName() + semObj.getId();

                out.println("<form method=\"post\" id=\"frmAdd" + data + "\" action=\" " + url
                        + "\" class=\"swbform\" type=\"dijit.form.Form\" onsubmit=\""
                        + "submitForm('frmAdd" + data + "');return false;\">");
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + semObj.getURI()
                        + "\">");
                out.println("<input type=\"hidden\" name=\"ws\" value=\"" + bsc.getId() + "\">");
                
                //Colocar encabezado del listado con las columnas del mismo
                out.println("<div>"); //class=\"swbform\"
                out.println("  <fieldset>");
                out.println("    <table width=\"98%\">"); 
                out.println("      <thead>");
                out.println("        <tr>");
                out.println("          <th>" + paramRequest.getLocaleString("lbl_period") + "</th>");
                out.println("          <th>" + paramRequest.getLocaleString("lbl_fromDate") + "</th>");            
                out.println("          <th>" + paramRequest.getLocaleString("lbl_toDate") + "</th>");            
                out.println("          <th>" + paramRequest.getLocaleString("lbl_former") + "</th>");            
                out.println("          <th>" + paramRequest.getLocaleString("lbl_next") + "</th>");            
                out.println("          <th>" + paramRequest.getLocaleString("lbl_inTime") + "</th>");            
                out.println("          <th>" + paramRequest.getLocaleString("lbl_relate") + "</th>");            
                out.println("        </tr>");
                out.println("</thead>");
                out.println("<tbody>");
                
                Seasonable seasonable = (Seasonable) semObj.createGenericInstance();
                
                while (itPeriods.hasNext()) {
                    Period period = itPeriods.next();
                    if (  (period.isValid() && user.haveAccess(period)) ||
                            (!period.isActive() && seasonable.hasPeriod(period) &&
                            user.haveAccess(period))  ) {
                        String select = "";
                        String title = period.getTitle(user.getLanguage()) == null
                                       ? period.getTitle()
                                       : period.getTitle(user.getLanguage());
                        String titleFormer = null;
                        String titleNext = null;
                        boolean hasFormer = false;
                        boolean hasNext = false;
                        
                        if (period.getPrevius() != null && period.getPrevius() instanceof Period) {
                            Period former = (Period) period.getPrevius();
                            titleFormer = former.getTitle(user.getLanguage()) == null 
                                          ? former.getTitle() : former.getTitle(user.getLanguage());
                            hasFormer = true;
                        } else {
                            titleFormer = paramRequest.getLocaleString("lbl_notAssigned");
                        }
                        if (period.getNext() != null && period.getNext() instanceof Period) {
                            Period next = (Period) period.getNext();
                            titleNext = next.getTitle(user.getLanguage()) == null
                                        ? next.getTitle() : next.getTitle(user.getLanguage());
                            hasNext = true;
                        } else {
                            titleFormer = paramRequest.getLocaleString("lbl_notAssigned");
                        }

                        if (objetivesCurrent.contains(period)) {
                            select = " checked";
                        }
                        
                        //mostrar las columnas del listado con sus valores
                        out.println("        <tr>");
                        out.println("          <td>");
                        out.print("              <a href=\"#\" onclick=\"addNewTab('" + period.getURI() + "','");
                        out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + title);
                        out.println("');return false;\" >" + title + "</a>");
                        out.println("          </td>");
                        out.println("          <td>" + period.getStart() + "</td>");            
                        out.println("          <td>" + period.getEnd() + "</td>");            
                        out.println("          <td>");
                        if (hasFormer) {
                            out.print("              <a href=\"#\" onclick=\"addNewTab('" + period.getPrevius().getURI() + "','");
                            out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + 
                                    (titleFormer != null ? titleFormer : paramRequest.getLocaleString("lbl_noTitle")));
                            out.println("');return false;\" >" +
                                    (titleFormer != null ? titleFormer : paramRequest.getLocaleString("lbl_noTitle")) +
                                    "</a>");
                        }
                        out.println("          </td>");
                        out.println("          <td>");
                        if (hasNext) {
                            out.print("              <a href=\"#\" onclick=\"addNewTab('" + period.getNext().getURI() + "','");
                            out.print(SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + 
                                    (titleNext != null ? titleNext : paramRequest.getLocaleString("lbl_noTitle")));
                            out.println("');return false;\" >" +
                                        (titleNext != null ? titleNext : paramRequest.getLocaleString("lbl_noTitle")) +
                                        "</a>");
                        }
                        out.println("          </td>");
                        //TODO: Definir como se obtiene el dato de esta columna
                        out.println("          <td>" + 
                                (period.isBlocked()
                                 ? paramRequest.getLocaleString("lbl_isInTime")
                                 : paramRequest.getLocaleString("lbl_isNotInTime")) +
                                "</td>");            
                        out.println("          <td align=\"center\"><input id=\"" + period.getId() + "\" name=\"period" + data
                                + "\" type=\"checkbox\" value=\"" + period.getId() + "\" onclick=\"submitForm('frmAdd" + data + "');return false;\""
                                + " data-dojo-type=\"dijit.form.CheckBox\"" + select + "></td>");
                        out.println("        </tr>");
                        
                    }
                }
                out.println("      </tbody>");
                out.println("    </table>");
                out.println("  </fieldset>");
                
                out.println("  <fieldset>");
                //Mostrar los botones para asignar o desasignar todos, 
                out.println("    <button dojoType=\"dijit.form.Button\" type=\"button\">"
                        + paramRequest.getLocaleString("markAll") );
                out.println("      <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
        //incluir javascript para marcar todos los checkboxes y luego hacer submit
                out.println("        var formPeriods = dijit.byId(\"frmAdd" + data + "\");");
                out.println("        if (formPeriods != undefined) {");
                out.println("          for (var i = 0; i < formPeriods.getChildren().length; i++) {");
                out.println("            var widget = formPeriods.getChildren()[i];");
                out.println("            if (widget.declaredClass == \"dijit.form.CheckBox\") {");
                out.println("              widget.attr('checked', true);");
                out.println("            }");
                out.println("          }");
                out.println("        } else {");
                out.println("          formPeriods = document.getElementById(\"frmAdd" + data + "\");");
                out.println("          for (var i = 0; i < formPeriods.length; i++) {");
                out.println("            var element = formPeriods.elements[i];");
                out.println("            if (element.type == \"checkbox\") {");
                out.println("              element.checked = true;");
                out.println("            }");
                out.println("          }");
                out.println("        }");
                out.println("        submitForm(\"frmAdd" + data + "\");\n");
                out.println("        return false;\n");
                out.println("      </script>\n");
                out.println("    </button>");
                
                out.println("    <button dojoType=\"dijit.form.Button\" type=\"button\" "
                        + "onClick=\"reloadTab('" + semObj.getURI() + "');\">"
                        + paramRequest.getLocaleString("unMarkAll"));
                out.println("      <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
        //incluir javascript para desmarcar todos los checkboxes y luego hacer submit
                out.println("        var formPeriods = dijit.byId(\"frmAdd" + data + "\");");
                out.println("        if (formPeriods != undefined) {");
                out.println("          for (var i = 0; i < formPeriods.getChildren().length; i++) {");
                out.println("            var widget = formPeriods.getChildren()[i];");
                out.println("            if (widget.declaredClass == \"dijit.form.CheckBox\") {");
                out.println("              widget.attr('checked', false);");
                out.println("            }");
                out.println("          }");
                out.println("        } else {");
                out.println("          formPeriods = document.getElementById(\"frmAdd" + data + "\");");
                out.println("          for (var i = 0; i < formPeriods.length; i++) {");
                out.println("            var element = formPeriods.elements[i];");
                out.println("            if (element.type == \"checkbox\") {");
                out.println("              element.checked = false;");
                out.println("            }");
                out.println("          }");
                out.println("        }");
                out.println("        submitForm(\"frmAdd" + data + "\");\n");
                out.println("        return false;\n");
                out.println("      </script>\n");
                out.println("    </button>");
                out.println("  </fieldset>");
                out.println("</div>");
                out.println("</form>");
            } else {
                out.println("<p>" + paramRequest.getLocaleString("periodsEstablished") + "</p>");
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
    
    /**
     * M&eacute;todo que se encarga de persistir la informaci&oacute;n de forma
     * segura en un objetivo
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException Excepti&oacute;n utilizada para recursos de
     * SWB
     * @throws IOException Excepti&oacute;n de IO
     */
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
            String[] periods = null;

            suri = request.getParameter("suri");
            semObj = SemanticObject.getSemanticObject(suri);
            data = semObj != null ? semObj.getSemanticClass().getName()
                    + semObj.getId() : "";
            periods = request.getParameterValues("period" + data);
            if (semObj != null && periods != null && periods.length > 0) {
                removePeriodsInSemObj(semObj);
                for (int i = 0; i < periods.length; i++) {
                    Period periodCheck = Period.ClassMgr.getPeriod(periods[i], ws);

                    if (periodCheck != null) {
                        semObj.addObjectProperty(Seasonable.bsc_hasPeriod,
                                periodCheck.getSemanticObject());
                        periodCheck.setUndeleteable(true);
                    }
                }
            }
            if (periods == null) {
                removePeriodsInSemObj(semObj);
            }
        }
        response.setRenderParameter("suri", suri);
        response.setRenderParameter("statusMsg", response.getLocaleString("statusMsg"));
        response.setMode(SWBResourceURL.Mode_VIEW);
    }

    /**
     * M&eacute;todo que se encarga de eliminar los periodos asociados a un
     * objeto Sem&aacute;ntico
     *
     * @param semObj
     */
    private void removePeriodsInSemObj(SemanticObject semObj) {
        Iterator<SemanticObject> itPeriods = (Iterator<SemanticObject>) semObj.listObjectProperties(Seasonable.bsc_hasPeriod);

        if (itPeriods != null && itPeriods.hasNext()) {
            while (itPeriods.hasNext()) {
                SemanticObject period = itPeriods.next();

                semObj.removeObjectProperty(Seasonable.bsc_hasPeriod, period);
                Iterator<SemanticObject> itObjs = period.listRelatedObjects();
                if(itObjs != null && itObjs.hasNext()){
                    period.setBooleanProperty(Undeleteable.swb_undeleteable, false);
                }
            }
        }
    }
}
