/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Risk;
import org.semanticwb.bsc.tracing.MitigationAction;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * Permite visualizar, editar y eliminar Acciones de Mitigación de un Riesgo en
 * especifico
 *
 * @author martha.jimenez
 * @version %I%, %G%
 * @since 1.0
 */
public class MitigationActionManager extends GenericResource {

    public static org.semanticwb.platform.SemanticProperty bsc_hasMitigationAction = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary()
            .getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasMitigationAction");
    public static final String Action_DELETE = "delete";

    /**
     * M&eacute;todo que se encarga de presentar la vista para visualizar,
     * editar o eliminar una acci&oacute;n de mitigaci&oacute;n en un Riesgo.
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
        StringBuilder toReturn = new StringBuilder();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        User user = paramRequest.getUser();
        if (user == null || !user.isSigned()) {
            response.sendError(403);
            return;
        }
        final String lang = user.getLanguage();

        String suri = request.getParameter("suri");
        if (suri == null) {
            out.println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semObj = ont.getSemanticObject(suri);
        Risk risk = (Risk) semObj.createGenericInstance();
        if (risk != null) {
            //createInstances(risk, risk.getBSC());
            Iterator<MitigationAction> it = risk.listMitigationActions();

            toReturn.append("<script type=\"text/javascript\">");
            toReturn.append("  dojo.require('dojo.parser');");
            toReturn.append("  dojo.require('dijit.layout.ContentPane');");
            toReturn.append("  dojo.require('dijit.form.CheckBox');");
            toReturn.append("</script>");

            toReturn.append("<div class=\"swbform\">");
            toReturn.append("<fieldset>\n");
            toReturn.append("<table width=\"98%\">");
            toReturn.append("<thead>");
            toReturn.append("<tr>");
            toReturn.append("<th></th>");
            toReturn.append("<th>");
            toReturn.append(paramRequest.getLocaleString("lbl_title"));
            toReturn.append("</th>");
            toReturn.append("<th>");
            toReturn.append(paramRequest.getLocaleString("lbl_description"));
            toReturn.append("</th>");
            toReturn.append("<th>");
            toReturn.append(paramRequest.getLocaleString("lbl_created"));
            toReturn.append("</th>");
            toReturn.append("<th>");
            toReturn.append(paramRequest.getLocaleString("lbl_updated"));
            toReturn.append("</th>");
            toReturn.append("</tr>");
            toReturn.append("</thead>");
            
            while (it.hasNext()) {
                MitigationAction mitAction = it.next();
                SWBResourceURL urlDelete = paramRequest.getActionUrl();

                if (mitAction != null && ((mitAction.isValid() && user.haveAccess(mitAction))
                        || (!mitAction.isActive()
                        && semObj.hasObjectProperty(bsc_hasMitigationAction, mitAction.getSemanticObject())
                        && user.haveAccess(mitAction)))) {
                    urlDelete.setParameter("suri", suri);
                    urlDelete.setParameter("reloadTab", "true");
                    urlDelete.setParameter("sval", mitAction.getId());
                    urlDelete.setAction(Action_DELETE);
                    toReturn.append("<tr>");
                    toReturn.append("<td>");
                    toReturn.append("\n<a href=\"#\" onclick=\"if(confirm('");
                    toReturn.append(paramRequest.getLocaleString("lbl_msgDelete"));
                    toReturn.append("'))submitUrl('");
                    toReturn.append(urlDelete);
                    toReturn.append("',this.domNode);reloadTab('");
                    toReturn.append(risk.getURI());
                    toReturn.append("');return false;\">");
                    toReturn.append("\n<img src=\"");
                    toReturn.append(SWBPlatform.getContextPath());
                    toReturn.append("/swbadmin/icons/iconelim.png\" alt=\"");
                    toReturn.append(paramRequest.getLocaleString("lbl_delete"));
                    toReturn.append("\"/>");
                    toReturn.append("\n</a>");

                    toReturn.append("</td>");
                    toReturn.append("<td>");
                    toReturn.append("<a href=\"#\" onclick=\"addNewTab('");
                    toReturn.append(mitAction.getURI());
                    toReturn.append("','");
                    toReturn.append(SWBPlatform.getContextPath());
                    toReturn.append("/swbadmin/jsp/objectTab.jsp");
                    toReturn.append("','");
                    toReturn.append(mitAction.getTitle());
                    toReturn.append("');return false;\" >");
                    toReturn.append((mitAction.getTitle(lang) == null
                            ? (mitAction.getTitle() == null
                            ? paramRequest.getLocaleString("lbl_undefined")
                            : mitAction.getTitle().replaceAll("'", ""))
                            : mitAction.getTitle(lang).replaceAll("'", "")));
                    toReturn.append("</a>");
                    toReturn.append("</td>");
                    toReturn.append("<td>");
                    toReturn.append((mitAction.getDescription(lang) == null
                            ? (mitAction.getDescription() == null
                            ? paramRequest.getLocaleString("lbl_undefined")
                            : mitAction.getDescription())
                            : mitAction.getDescription(lang)));
                    toReturn.append("</td>");
                    toReturn.append("<td>");
                    toReturn.append((mitAction.getCreated() == null ? ""
                            : SWBUtils.TEXT.getStrDate(mitAction.getCreated(),
                            "es", "dd/mm/yyyy")));
                    toReturn.append("</td>");
                    toReturn.append("<td>");
                    toReturn.append((mitAction.getUpdated() == null ? ""
                            : SWBUtils.TEXT.getStrDate(mitAction.getCreated(),
                            "es", "dd/mm/yyyy")));
                    toReturn.append("</td>");
                    toReturn.append("</tr>");
                }
            }
            toReturn.append("</table>");
            toReturn.append("</fieldset>\n");
            toReturn.append("</div>");

            if (request.getParameter("statmsg") != null
                    && !request.getParameter("statmsg").isEmpty()) {
                toReturn.append("<div dojoType=\"dojox.layout.ContentPane\">");
                toReturn.append("<script type=\"dojo/method\">");
                toReturn.append("showStatus('" + request.getParameter("statmsg"));
                toReturn.append("');\n");
                toReturn.append("</script>\n");
                toReturn.append("</div>");
            }

        } else {
            toReturn.append("objeto semántico no ubicado");
        }
        out.println(toReturn.toString());
    }

    /**
     *
     * M&eacute;todo que se encarga de persistir la informaci&oacute;n de forma
     * segura de la administraci&oacute;n de acciones de mitigaci&oacute;n de un
     * Riesgo
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException Excepti&oacute;n utilizada para recursos de
     * SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response)
            throws SWBResourceException, IOException {
        final String action = response.getAction();
        final String suri = request.getParameter("suri");
        response.setAction(SWBResourceURL.Action_EDIT);
        response.setRenderParameter("suri", suri);

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semObj = ont.getSemanticObject(suri);

        Risk risk = (Risk) semObj.getGenericInstance();
        BSC bsc = risk.getBSC();
        if (Action_DELETE.equalsIgnoreCase(action)) {
            final String mitActionId = request.getParameter("sval");
            if (mitActionId != null) {
                MitigationAction mitAction = null;
                if (MitigationAction.ClassMgr.hasMitigationAction(mitActionId, bsc)) {
                    mitAction = MitigationAction.ClassMgr.
                            getMitigationAction(mitActionId, bsc);
                    risk.removeMitigationAction(mitAction);
                    mitAction.remove();
                    response.setRenderParameter("statmsg", response
                            .getLocaleString("msgDeleteSuccessful"));
                }
            }
        }
    }

    /*private void createInstances(Risk risk, WebSite ws) {
        MitigationAction action = MitigationAction.ClassMgr.createMitigationAction(ws);
        action.setTitle("Acción 1");
        action.setDescription("Descripción de Acción 1");
        action.setActive(true);
        action.setCreated(new Date());
        action.setUpdated(new Date());
        risk.addMitigationAction(action);
    }*/
}
