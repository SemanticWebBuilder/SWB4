/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.tracing.Risk;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * Permite visualizar, editar y eliminar Iniciativas de un Riesgo en especifico.
 * Esta iniciativa fue creada desde el tablero de riesgos.
 *
 * @author martha.jimenez
 * @version %I%, %G%
 * @since 1.0
 */
public class InitiativeRiskManager extends GenericResource {

    public static org.semanticwb.platform.SemanticProperty bsc_hasInitiativeRisk =
            org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().
            getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasInitiativeRisk");
    public static final String Action_DELETE = "delete";

    /**
     * M&eacute;todo que se encarga de presentar la vista para visualizar,
     * editar o eliminar una iniciativa en un Riesgo. Esta iniciativa fue creada
     * desde el tablero de riesgos.
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
           // createInstances(risk, risk.getBSC());
            Iterator<Initiative> it = risk.listInitiativeRisks();

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
                Initiative initiative = it.next();
                SWBResourceURL urlDelete = paramRequest.getActionUrl();

                if (initiative != null && ((initiative.isValid() && user.haveAccess(initiative))
                        || (!initiative.isActive()
                        && semObj.hasObjectProperty(bsc_hasInitiativeRisk, initiative.getSemanticObject())
                        && user.haveAccess(initiative)))) {
                    urlDelete.setParameter("suri", suri);
                    urlDelete.setParameter("reloadTab", "true");
                    urlDelete.setParameter("sval", initiative.getId());
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
                    toReturn.append(initiative.getURI());
                    toReturn.append("','");
                    toReturn.append(SWBPlatform.getContextPath());
                    toReturn.append("/swbadmin/jsp/objectTab.jsp");
                    toReturn.append("','");
                    toReturn.append(initiative.getTitle());
                    toReturn.append("');return false;\" >");
                    toReturn.append((initiative.getTitle(lang) == null
                            ? (initiative.getTitle() == null
                            ? paramRequest.getLocaleString("lbl_undefined")
                            : initiative.getTitle().replaceAll("'", ""))
                            : initiative.getTitle(lang).replaceAll("'", "")));
                    toReturn.append("</a>");
                    toReturn.append("</td>");
                    toReturn.append("<td>");
                    toReturn.append((initiative.getDescription(lang) == null
                            ? (initiative.getDescription() == null
                            ? paramRequest.getLocaleString("lbl_undefined")
                            : initiative.getDescription())
                            : initiative.getDescription(lang)));
                    toReturn.append("</td>");
                    toReturn.append("<td>");
                    toReturn.append((initiative.getCreated() == null ? ""
                            : SWBUtils.TEXT.getStrDate(initiative.getCreated(),
                            "es", "dd/mm/yyyy")));
                    toReturn.append("</td>");
                    toReturn.append("<td>");
                    toReturn.append((initiative.getUpdated() == null ? ""
                            : SWBUtils.TEXT.getStrDate(initiative.getCreated(),
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
     * segura de la administraci&oacute;n de iniciativas de un Riesgo. Esta
     * asociación se hace desde el tablero de Riesgos.
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
            final String mitInitiativeId = request.getParameter("sval");
            if (mitInitiativeId != null) {
                Initiative initiative = null;
                if (Initiative.ClassMgr.hasInitiative(mitInitiativeId, bsc)) {
                    initiative = Initiative.ClassMgr.
                            getInitiative(mitInitiativeId, bsc);
                    risk.removeInitiativeRisk(initiative);
                    initiative.remove();
                    response.setRenderParameter("statmsg", response
                            .getLocaleString("msgDeleteSuccessful"));
                }
            }
        }
    }

    /*private void createInstances(Risk risk, WebSite ws) {
        Initiative initiative = Initiative.ClassMgr.createInitiative(ws);
        initiative.setTitle("Acción 1");
        initiative.setDescription("Descripción de Acción 1");
        initiative.setActive(true);
        initiative.setCreated(new Date());
        initiative.setUpdated(new Date());
        risk.addInitiativeRisk(initiative);
    }*/
}
