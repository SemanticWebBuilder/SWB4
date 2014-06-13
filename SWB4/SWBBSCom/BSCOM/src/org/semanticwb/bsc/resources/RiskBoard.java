package org.semanticwb.bsc.resources;

import com.arthurdo.parser.HtmlException;
import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import com.hp.hpl.jena.rdf.model.Statement;
import com.lowagie.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Determinant;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Risk;
import org.semanticwb.bsc.formelement.TextAreaElement;
import org.semanticwb.bsc.tracing.Control;
import org.semanticwb.bsc.tracing.DeterminantValue;
import org.semanticwb.bsc.tracing.Factor;
import org.semanticwb.bsc.tracing.MitigationAction;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.GenericSemResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import static org.semanticwb.portal.api.SWBResourceModes.Action_ADD;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Despliega el tablero de riesgos y proporciona las facilidades para actualizar parte de la 
 * informacion mostrada y para la gestión de acciones de mitigacion y de iniciativas asociadas
 * a los riesgos.
 * @author jose.jimenez
 */
public class RiskBoard extends GenericResource {

    /*--*
     * Define clases de estilos para adornar el despliegue de los datos en el tablero
     */
//    private static final String OWNSTYLES = "<style>\n" + 
//        "    table, th, td {  border: 1px solid black; background-color:#FFFFFF; padding:3px; }\n" +
//        "    th {  text-align:center; background-color:#EEEEEE;  }\n" + 
//        "    .evalRiesgo {  background-color:#87CEEB;  }\n" + 
//        "    .evalControl {  background-color:#1E90FF;  }\n" + 
//        "    .valRiesgoCtrl {  background-color:#6495ED; color:#FFFFFF;  }\n" + 
//        "    .mapaRiesgoth {  background-color:#FFFFFF;  }\n" +
//        "    .accionIniciativa {  background-color:#0000CD; color:#FFFFFF;  }\n" +
//        "    .cuadrante1 {  background-color:#FF0000; color:#000000;  }\n" +
//        "    .cuadrante2 {  background-color:#FFFF00; color:#000000;  }\n" +
//        "    .cuadrante3 {  background-color:#00FFFF; color:#000000;  }\n" +
//        "    .cuadrante4 {  background-color:#3CB371; color:#000000;  }\n" +
//        "    .riesgoControlado {  background-color:#389738;  }\n" +
//        "    .riesgoNoControlado {  background-color:#FFFF80;  }\n" +
//        "    .noValido {  background-color:#FF6666;  }\n" +
//        "    .textCentered {  text-align:center;  }\n" +
//        "</style>\n";

    
    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static final Logger log = SWBUtils.getLogger(GenericSemResource.class);
    
    /**
     * Genera el codigo HTML para la presentacion de los vínculos hacia las operaciones de 
     * exportación del contenido del tablero de riesgos a formato PDF y Excel.
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se genera en base al contenido de la petici&oacute;n
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     *         petici&oacute;n/respuesta.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        
        StringBuilder dataOut = new StringBuilder(512);
        WebSite website = this.getResourceBase().getWebSite();website.getModelProperty("quadrant1HexColor");
        
        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
            dataOut.append(doIconExport(request, paramRequest));
        } else if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
            String mode = request.getParameter("dispMode") == null && userCanEdit() ? "edit" : "view";
            dataOut.append(this.generateBoardView(website, mode, request, paramRequest));
        }
        out.println(dataOut.toString());
    }
    
    /**
     * Genera el c&oacute;digo HTML utilizado para presentar la vista del tablero de riesgos en el modo en que se indica.
     * @param website la instancia de {@code WebSite} de la que se debe extraer la informaci&oacute; de los riesgos
     * @param mode modo de visualizaci&oacute;n de la informaci&oacute;n presentada en el tablero. Puede tener los valores:
     *        {@literal view} con el que solo se muestra la informaci&oacute;n almacenada y {@literal edit} con el que 
     *        se pueden realizar modificaciones a cierta informaci&oacute;n
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @return un {@code String} que contiene el c&oacute;digo HTML para representar la informaci&oacute;n de los riesgos
     *        en el modo en que se desea mostrar el tablero
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     */
    private String generateBoardView(WebSite website, String mode, HttpServletRequest request,
            SWBParamRequest paramRequest) throws SWBResourceException {
        
        StringBuilder output = new StringBuilder(512);
        String exportType = request.getParameter("type");
        
        if (exportType == null) {
            output.append(this.getStylesString());
        }
        ArrayList<Determinant> detList = (ArrayList<Determinant>) Determinant.listValidDeterminants(website);
        Determinant[] determinants = new Determinant[detList.size()];
        int cont = 0;
        //Se asegura el orden de despliegue de los determinantes al utilizar un arreglo
        for (Determinant det : detList) {
            determinants[cont] = det;
            cont++;
        }
        
        if (mode != null && mode.equalsIgnoreCase("edit")) {
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setMode("showAddWindow");
            url.setCallMethod(SWBResourceURL.Call_DIRECT);
            output.append("<script type=\"text/javascript\">");
            output.append("  var reloadPage = true;");
            output.append("\n  dojo.require(\"dojo.parser\");");
            output.append("\n  dojo.require(\"dojo.ready\");");
            output.append("\n  dojo.require(\"dojo.date.locale\");");
            output.append("\n  dojo.require(\"dijit.Dialog\");");
            output.append("\n  dojo.require(\"dijit.form.TextBox\");");
            output.append("\n  dojo.require(\"dijit.form.ValidationTextBox\");");
            output.append("\n  dojo.require(\"dijit.form.Button\");");
            output.append("\n  dojo.require(\"dojox.layout.ContentPane\");");
            output.append("\n  dojo.require(\"dojox.editor.plugins.PasteFromWord\");");
            output.append("\n  dojo.require(\"dijit.InlineEditBox\");");
            output.append("\n  dojo.require(\"dijit.form.Textarea\");");
            output.append("\n  dojo.require(\"dijit.form.DateTextBox\");");
            output.append("\n  dojo.require(\"dijit.Editor\");");
            output.append("\n  dojo.require(\"dijit._editor.plugins.AlwaysShowToolbar\");");
            output.append("\n  dojo.require(\"dijit._editor.plugins.TextColor\");");
            output.append("\n  dojo.require(\"dijit.form.CheckBox\");");
            output.append("\n  dojo.require(\"dijit.Editor\");");
            output.append("</script>");
            output.append("<div style=\"align:right;\">");
            output.append("  ");
            output.append("<a href=\"#\" onclick=\"showMyDialog('");
            output.append(url);
            output.append("', '");
            output.append(paramRequest.getLocaleString("lbl_addTitle"));
            output.append("');\">");
            output.append(paramRequest.getLocaleString("lbl_addTitle"));
            output.append("</a>");
            output.append("</div>");
            output.append("\n<div dojoType=\"dijit.Dialog\" class=\"soria\" id=\"swbDialog\" ");
            output.append("title=\"Agregar\" style=\"width:auto; height:auto;\">\n");
            output.append("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" id=\"swbDialogImp\" ");
            output.append("style=\"padding:10px; width:auto; height:auto;\" executeScripts=\"true\">\n");
            output.append("    Cargando...\n");
            output.append("  </div>\n");
            output.append("</div>\n");
        }
        output.append("<table>\n");
        output.append(this.createTableHeading(determinants));
        output.append(this.createTableBody(determinants, website, mode, request, paramRequest));
        output.append("</table>\n");
        return output.toString();
    }
    
    /**
     * Crea el c&oacute;digo HTML correspondiente al encabezado de la tabla que muestra el tablero de riesgos
     * @param determinants conjunto de objetos {@code Determinant} utilizado en la generaci&oacute;n del encabezado
     * @return un {@code String} que contiene el c&oacute;digo HTML del encabezado del tablero de riesgos
     */
    private String createTableHeading(Determinant[] determinants) {
        
        StringBuilder data = new StringBuilder(256);
        
        data.append("  <thead>\n");
        data.append("    <tr>\n");
        data.append("      <th colspan=\"16\" class=\"evalRiesgo\">I. EVALUACI&Oacute;N DE RIESGOS</th>\n");
        data.append("      <th colspan=\"");
        data.append(6 + determinants.length);
        data.append("\" class=\"evalControl\">II. EVALUACI&Oacute;N DE CONTROLES</th>\n");
        data.append("      <th colspan=\"2\" class=\"valRiesgoCtrl\">III. VALORACI&Oacute;N DE RIESGOS VS. CONTROLES</th>\n");
        data.append("      <th class=\"mapaRiesgoth\">IV. MAPA DE RIESGOS</th>\n");
        data.append("      <th colspan=\"3\" class=\"accionIniciativa\">V. ESTRATEGIAS Y ACCIONES</th>\n");
        data.append("    </tr>\n");
        data.append("    <tr>\n");
        data.append("      <th rowspan=\"2\">No. de Riesgo</th>\n");
        data.append("      <th rowspan=\"2\">Unidad Administrativa</th>\n");
        data.append("      <th colspan=\"2\">Alineaci&oacute;n a Estrategias, Objetivos o Metas Institucionales</th>\n");
        data.append("      <th rowspan=\"2\">RIESGO</th>\n");
        data.append("      <th rowspan=\"2\">Nivel de decisi&oacute;n del Riesgo</th>\n");
        data.append("      <th colspan=\"2\">Clasificaci&oacute;n del Riesgo</th>\n");
        data.append("      <th colspan=\"4\">FACTOR</th>\n");
        data.append("      <th rowspan=\"2\">Posibles efectos del Riesgo</th>\n");
        data.append("      <th colspan=\"3\">Valoraci&oacute;n Inicial</th>\n");
        data.append("      <th rowspan=\"2\">&iquest;Tiene controles?</th>\n");
        data.append("      <th colspan=\"3\">CONTROL</th>\n");
        data.append("      <th colspan=\"");
        data.append(1 + determinants.length);
        data.append("\">Determinaci&oacute;n de Suficiencia o Deficiencia del control</th>\n");
        data.append("      <th rowspan=\"2\">Riesgo controlado Suficientemente</th>\n");
        data.append("      <th colspan=\"2\">Valoraci&oacute;n final</th>\n");
        data.append("      <th rowspan=\"2\">Ubicaci&oacute;n en cuadrantes</th>\n");
        data.append("      <th rowspan=\"2\">Estrategia para administrar el Riesgo</th>\n");
        data.append("      <th rowspan=\"2\">Descripci&oacute;n de la(s) Acci&oacute;n(es)</th>\n");
        data.append("      <th rowspan=\"2\">Descripci&oacute;n de la(s) Iniciativa(s)</th>\n");
        data.append("    </tr>\n");
        data.append("    <tr>\n");
        data.append("      <th>Selecci&oacute;n</th>\n");
        data.append("      <th>Descripci&oacute;n</th>\n");
        data.append("      <th>Selecci&oacute;n</th>\n");
        data.append("      <th>Especificar Otro</th>\n");
        data.append("      <th>No. de Factor</th>\n");
        data.append("      <th>Descripci&oacute;n</th>\n");
        data.append("      <th>Clasificaci&oacute;n</th>\n");
        data.append("      <th>Tipo</th>\n");
        data.append("      <th>Grado Impacto</th>\n");
        data.append("      <th>Probabilidad Ocurrencia</th>\n");
        data.append("      <th>Cuadrante</th>\n");
        data.append("      <th>No.</th>\n");
        data.append("      <th>Descripci&oacute;n</th>\n");
        data.append("      <th>Tipo</th>\n");
        for (Determinant det : determinants) {
            data.append("      <th>");
            data.append(det.getTitle());
            data.append("</th>\n");
        }
        data.append("      <th>Resultado de la determinaci&oacute;n del Control</th>\n");
        data.append("      <th>Grado de Impacto</th>\n");
        data.append("      <th>Probabilidad de Ocurrencia</th>\n");
        data.append("    </tr>\n");
        data.append("  </thead>\n");
        data.append("");
        data.append("");
    
        return data.toString();
    }

    /**
     * Crea el c&oacute;digo HTML correspondiente al cuerpo de la tabla que representa al tablero
     * de riesgos.
     * @param determinants conjunto de objetos {@code Determinant} utilizados para crear el encabezado de la tabla
     * @param website la instancia de {@code WebSite} de la que se debe extraer la informaci&oacute; de los riesgos
     * @param mode indica el modo de visualizaci&oacute;n de la informaci&oacute;n representada en el tablero, 
     *        puede ser: {@literal view} o {@literal edit}
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @return un {@code String} 
     */
    private String createTableBody(Determinant[] determinants, WebSite website, String mode, HttpServletRequest request,
            SWBParamRequest paramRequest) {
        
        StringBuilder data = new StringBuilder(256);
//        String fields = "Risk.prefix|Risk.area|Risk.elementRelated|Risk.elementInstanceRelated|Risk.title" +
//                "|Risk.riskLeveldecision|Risk.classificationRisk|Risk.classifRiskSpecifyOther|Factor.prefix" +
//                "|Factor.title|Factor.classificationFactor|Factor.factorType|Risk.possibleEffectsRisk" +
//                "|Risk.iniAssessmentImpactLevel|Risk.iniAssessmentLikelihood|Risk.calculateQuadrant|Factor.isControlRelated" +
//                "|Control.prefix|Control.title|Control.controlType|DeterminantValue.isDeterminant" +
//                "|Control.calculateDetermination|Risk.calculateControled|Risk.finAssessmentImpactLevel" +
//                "|Risk.finAssessmentLikelihood|Risk.calculateQuadrant|Risk.stratManageRisk|Action.title" +
//                "|Initiative.title";
        SemanticProperty[] factorFields = {
                Factor.bsc_prefix, Factor.swb_title, Factor.bsc_classificationFactor, Factor.bsc_factorType
            };
        SemanticProperty[] controlFields = {Control.bsc_prefix, Control.swb_title, Control.bsc_controlType};
        Iterator<Risk> risks = Risk.ClassMgr.listRisks(website);
        String simpleTd = "      <td>\n";
        String simpleTextCenteredTd = "      <td class=\"textCentered\">\n";
        String lang = paramRequest.getUser().getLanguage();
        User user = paramRequest.getUser();
        int totalRows = 0;
        
        data.append("  <tbody>\n");
        //Se obtienen todos los riesgos creados en el BSC indicado
        while (risks != null && risks.hasNext()) {
            Risk risk = risks.next();
            if (!risk.isValid() || !user.haveAccess(risk)) {
                continue;
            }
            short riskSpan = this.calculateRowSpan(risk, null);
            totalRows += riskSpan;
            short factorSpan = 1;
            String spanRiskTd = "      <td" + (riskSpan > 1 ? " rowspan=\"" + riskSpan + "\"" : "") + ">\n";
            String spanRiskTextCenteredTd = "      <td" + 
                    (riskSpan > 1 ? " rowspan=\"" + riskSpan + "\"" : "") +
                    " class=\"textCentered\">\n";
            String spanRiskStyledTd = "      <td" + (riskSpan > 1 ? " rowspan=\"" + riskSpan + "\"" : "");
            String closeTdTag = ">\n";
            String spanFactorTd = null;
            String tdEnclosing = "      </td>\n";
            //ArrayList<Factor> factors = new ArrayList<Factor>(8);
            SemanticObject[][] rows = new SemanticObject[riskSpan][2];
            int ctrlRowCount = 0;
            Factor formerFactor = null;
            Control formerControl = null;
            HashMap<Determinant, DeterminantValue> determValues = new HashMap<Determinant, DeterminantValue>(8);
            
            Iterator<Factor> factorIt = risk.listValidFactorsByPrefix();
            while (factorIt != null && factorIt.hasNext()) {
                Factor factor = factorIt.next();
                Iterator<Control> controlIt = factor.listValidControlsByPrefix();  //listControls()
                int controlCount = 0;
                while (controlIt != null && controlIt.hasNext()) {
                    Control control = controlIt.next();
                    rows[ctrlRowCount][0] = factor.getSemanticObject();
                    rows[ctrlRowCount][1] = control.getSemanticObject();
                    ctrlRowCount++;
                    controlCount++;
                }
                if (controlCount == 0) {
                    rows[ctrlRowCount][0] = factor.getSemanticObject();
                    ctrlRowCount++;
                }
            }
            
            data.append("    <tr>\n");
            data.append(spanRiskTextCenteredTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_prefix, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_area, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_elementRelated, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_elementInstanceRelated, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.swb_title, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_riskLeveldecision, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_classificationRisk, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_classifRiskSpecifyOther, lang, mode));
            data.append(tdEnclosing);
            
            //Se agregan las propiedades del Factor
            if (rows[0][0] != null) {
                formerFactor = (Factor) rows[0][0].getGenericInstance();
                factorSpan = this.calculateRowSpan(null, formerFactor);
                spanFactorTd = "      <td" + (factorSpan > 1 ? " rowspan=\"" + factorSpan + "\"" : "");
                int index = 0;
                for (SemanticProperty property : factorFields) {
                    data.append(spanFactorTd);
                    if (index == 0) {
                        data.append(" class=\"textCentered\"");
                    }
                    data.append(">\n");
                    data.append(this.renderPropertyValue(request, formerFactor.getSemanticObject(), property, lang, mode));
                    data.append(tdEnclosing);
                    index++;
                }
            } else {
                //si no hay factores
                data.append("      <td></td><td></td><td></td><td></td>");
            }

            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_possibleEffectsRisk, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTextCenteredTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_iniAssessmentImpactLevel, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTextCenteredTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_iniAssessmentLikelihood, lang, mode));
            data.append(tdEnclosing);
            
            String quadrantLabel = null;
            short quadrant = risk.calculateQuadrant(true);
            switch (quadrant) {
                case 1: quadrantLabel = "I";
                        break;
                case 2: quadrantLabel = "II";
                        break;
                case 3: quadrantLabel = "III";
                        break;
                case 4: quadrantLabel = "IV";
                        break;
            }
            data.append(spanRiskStyledTd);
            data.append(" class=\"cuadrante");
            data.append(quadrant);
            data.append(" textCentered\"");
            data.append(closeTdTag);
            data.append(quadrantLabel);
            data.append(tdEnclosing);
            
            spanFactorTd = "      <td" + (factorSpan > 1 ? " rowspan=\"" + factorSpan + "\"" : "") +
                           " class=\"textCentered\">\n";
            if (formerFactor != null) {
                data.append(spanFactorTd);
                data.append(formerFactor.isControlRelated() ? "SI" : "NO");
            } else {
                data.append("      <td class=\"textCentered\">NO");
            }
            data.append(tdEnclosing);
            //se agregan las propiedades del Control
            if (rows[0][1] != null) {
                formerControl = (Control) rows[0][1].getGenericInstance();
                for (SemanticProperty property : controlFields) {
                    data.append(simpleTd);
                    data.append(this.renderPropertyValue(request, formerControl.getSemanticObject(), property, lang, mode));
                    data.append(tdEnclosing);
                }
            } else {
                data.append("      <td></td><td></td><td></td>");
            }
            
            if (formerControl != null) {
                Iterator<DeterminantValue> determIt = formerControl.listDeterminantValues();
                while (determIt != null && determIt.hasNext()) {
                    DeterminantValue value = determIt.next();
                    determValues.put(value.getDeterminant(), value);
                }
            }
            for (Determinant det : determinants) {
                if (formerControl != null) {
                    DeterminantValue value = determValues.get(det);
                    if (value == null) {
                        value = DeterminantValue.ClassMgr.createDeterminantValue(website);
                        value.setDeterminant(det);
                        formerControl.addDeterminantValue(value);
                    }
                    data.append(simpleTextCenteredTd);
                    data.append(this.renderPropertyValue(request, value.getSemanticObject(),
                            DeterminantValue.bsc_isDeterminant, lang, mode));
                    data.append(tdEnclosing);
                } else {
                    data.append("      <td>&nbsp;</td>");
                }
            }
            data.append(simpleTd);
            if (formerControl != null) {
                data.append(formerControl.calculateDetermination() ? "Suficiente" : "Deficiente");
            } else {
                data.append("      <td>&nbsp;</td>");
            }
            boolean isRiskControled = risk.calculateControled();
            data.append(tdEnclosing);
            data.append(spanRiskStyledTd);
            if (isRiskControled) {
                data.append(" class=\"riesgoControlado textCentered\"");
            } else {
                data.append(" class=\"riesgoNoControlado textCentered\"");
            }
            data.append(closeTdTag);
            data.append(isRiskControled ? "SI" : "NO");
            data.append(tdEnclosing);
            
            /*realizar llamado a validateAssessment() para saber si marcar en rojo estas dos celdas*/
            short initialValue = (short) risk.getIniAssessmentImpactLevel();
            short finalValue = (short) risk.getFinAssessmentImpactLevel();
            boolean validAssessment = this.validateAssessment(initialValue, finalValue, isRiskControled);
            if (validAssessment) {
                data.append(spanRiskTextCenteredTd);
            } else {
                data.append(spanRiskStyledTd);
                data.append(" class=\"noValido textCentered\"");
                data.append(closeTdTag);
            }
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_finAssessmentImpactLevel, lang, mode));
            data.append(tdEnclosing);
            initialValue = (short) risk.getIniAssessmentLikelihood();
            finalValue = (short) risk.getFinAssessmentLikelihood();
            validAssessment = this.validateAssessment(initialValue, finalValue, isRiskControled);
            if (validAssessment) {
                data.append(spanRiskTextCenteredTd);
            } else {
                data.append(spanRiskStyledTd);
                data.append(" class=\"noValido textCentered\"");
                data.append(closeTdTag);
            }
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_finAssessmentLikelihood, lang, mode));
            data.append(tdEnclosing);
            
            quadrantLabel = null;
            quadrant = risk.calculateQuadrant(false);
            switch (quadrant) {
                case 1: quadrantLabel = "I";
                        break;
                case 2: quadrantLabel = "II";
                        break;
                case 3: quadrantLabel = "III";
                        break;
                case 4: quadrantLabel = "IV";
                        break;
            }
            data.append(spanRiskStyledTd);
            data.append(" class=\"cuadrante");
            data.append(quadrant);
            data.append(" textCentered\"");
            data.append(closeTdTag);
            data.append(quadrantLabel);
            data.append(tdEnclosing);
            
            data.append(spanRiskTd);
            data.append(this.renderPropertyValue(request, risk.getSemanticObject(), Risk.bsc_stratManageRisk, lang, mode));
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            try {
                data.append(this.getActionsDisplay(risk, paramRequest, mode));
            } catch (SWBResourceException swbe) {
                RiskBoard.log.error("Despliegue de Actions, risk = " + risk.getURI(), swbe);
            }
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            try {
                data.append(this.getInitiativesDisplay(risk, paramRequest, mode));
            } catch (SWBResourceException swbe) {
                RiskBoard.log.error("Despliegue de Initiatives, risk = " + risk.getURI(), swbe);
            }
            data.append(tdEnclosing);
            data.append("    </tr>");
            
            //Se agregan las filas restantes (la primera ya esta) para completar lo que abarca el span calculado
            int rowCount = 1;
            while (rowCount < ctrlRowCount) {
                data.append("    <tr>");
                Factor factorInTurn = (Factor) rows[rowCount][0].createGenericInstance();
                determValues = new HashMap(8);
                
                //Si el factor no se ha desplegado, se extraen los datos para el despliegue de las columnas
                if (!factorInTurn.equals(formerFactor)) {
                    factorSpan = this.calculateRowSpan(null, factorInTurn);
                    spanFactorTd = "      <td" + (factorSpan > 1 ? " rowspan=\"" + factorSpan + "\"" : "");
                    int index = 0;
                    for (SemanticProperty property : factorFields) {
                        data.append(spanFactorTd);
                        if (index == 0) {
                            data.append(" class=\"textCentered\"");
                        }
                        data.append(">\n");
                        data.append(this.renderPropertyValue(request, factorInTurn.getSemanticObject(), property, lang, mode));
                        data.append(tdEnclosing);
                    }
                    data.append(spanFactorTd);
                    data.append(" class=\"textCentered\">\n");
                    data.append(factorInTurn.isControlRelated() ? "SI" : "NO");
                    data.append(tdEnclosing);
                }
                //Se agregan columnas de factor y determinantes
                if (rows[rowCount][1] != null) {
                    Control controlInTurn = (Control) rows[rowCount][1].createGenericInstance();
                    for (SemanticProperty property : controlFields) {
                        data.append(simpleTd);
                        data.append(this.renderPropertyValue(request, controlInTurn.getSemanticObject(), property, lang, mode));
                        data.append(tdEnclosing);
                    }
                    //Para agregar columnas de determinantes
                    Iterator<DeterminantValue> determIt = controlInTurn.listDeterminantValues();
                    while (determIt != null && determIt.hasNext()) {
                        DeterminantValue value = determIt.next();
                        determValues.put(value.getDeterminant(), value);
                    }
                    for (Determinant det : determinants) {
                        DeterminantValue value = determValues.get(det);
                        if (value == null) {
                            value = DeterminantValue.ClassMgr.createDeterminantValue(website);
                            value.setDeterminant(det);
                            controlInTurn.addDeterminantValue(value);
                        }
                        data.append(simpleTextCenteredTd);
                        if (value != null) {
                            data.append(this.renderPropertyValue(request, value.getSemanticObject(),
                                    DeterminantValue.bsc_isDeterminant, lang, mode));
                        } else {
                            data.append("&nbsp;\n");
                        }
                        data.append(tdEnclosing);
                    }
                    data.append(simpleTd);
                    data.append(controlInTurn.calculateDetermination() ? "Suficiente" : "Deficiente");
                    data.append(tdEnclosing);
                } else {
                    data.append("      <td></td><td></td><td></td>\n");
                    for (Determinant det : determinants) {
                            data.append("      <td>&nbsp;</td>\n");
                    }
                    data.append("      <td>&nbsp;</td>\n");
                }
                
                formerFactor = factorInTurn;
                rowCount++;
                data.append("    </tr>");
            }
            
        }
        data.append("  </tbody>\n");
        
        if (mode.equalsIgnoreCase("view")) {
            request.setAttribute("totalRows", totalRows);
        }
        
        return data.toString();
    }
    
    /**
     * Calcula cuantas filas contiguas son ocupadas por registros del mismo riesgo o factor
     * @param risk el objeto {@code Risk} del que se desea calcular el n&uacute;mero de filas en la tabla.
     *      Si es {@literal null} se realiza el c&aacute;lculo para el factor
     * @param factor el objeto {@code Factor} del que se desea calcular el n&uacute;mero de filas en la tabla.
     *        Si es {@literal null}, el valor devuelto es {@literal 1}
     * @return el n&uacute;mero de filas contiguas ocupadas por registros de la misma clase en la tabla.
     *         Si los par&aacute;metros recibidos son {@literal null}, el valor devuelto es {@literal 1}
     */
    private short calculateRowSpan(Risk risk, Factor factor) {
        
        short span = 0;
        short factorCont = 0;
        
        if (risk != null) {
            Iterator<Factor> factorIt = risk.listValidFactors();  // listFactors()
            while (factorIt != null && factorIt.hasNext()) {
                Factor riskFactor = factorIt.next();
                short controlCont = 0;
                Iterator<Control> controlIt = riskFactor.listValidControls();  //listControls()
                while (controlIt != null && controlIt.hasNext()) {
                    Control control = controlIt.next();
                    controlCont++;
                }
                if (controlCont > 0) {
                    span += controlCont;
                } else {//si el factor no tiene controles, se debe contar el factor
                    span++;
                }
                factorCont++;
            }
            if (factorCont == 0) {//si el riesgo no tiene factores, se debe contar la fila del riesgo
                span = 1;
            }
        } else if (factor != null) {
            short controlCont = 0;
            Iterator<Control> controlIt = factor.listValidControls();  // listControls()
            while (controlIt != null && controlIt.hasNext()) {
                Control control = controlIt.next();
                controlCont++;
            }
            if (controlCont > 0) {
                span = controlCont;
            } else {//si el factor no tiene controles, se debe contar el factor
                span = 1;
            }
        }
        return span;
    }
    
    /**
     * Devuelve el despliegue correspondiente al valor de la propiedad
     * especificada, del objeto indicado.
     * @param request petici&oacute;n HTTP realizada por el cliente
     * @param elementBSC representa el objeto del cual se desea extraer la
     * informaci&oacute;n
     * @param property la propiedad semantica de la cual se desea obtener su valor
     * @param lang representa el lenguaje en que se desea mostrar el valor de la
     * propiedad indicada
     * @param mode indica el modo de despliegue en que se desea mostrar el valor de 
     * la propiedad, {@literal edit} o {@literal view}
     * @return el despliegue del valor almacenado para la propiedad indicada
     */
    private String renderPropertyValue(HttpServletRequest request, SemanticObject elementBSC,
            SemanticProperty property, String lang, String mode) {

        String ret = null;
        SWBFormMgr formMgr = new SWBFormMgr(elementBSC, null, SWBFormMgr.MODE_VIEW);
        //SemanticProperty semProp = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propUri);

        //Codigo para obtener el displayElement
        Statement st = null;
        
        if (property != Descriptiveable.swb_title && property != Descriptiveable.swb_description) {
            st = property.getRDFProperty().getProperty(
                SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(
                "http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
        }
        if (st != null) {
            //Se obtiene: SemanticProperty: displayElement de la propiedad en cuestion (prop)
            SemanticObject soDisplayElement = SemanticObject.createSemanticObject(st.getResource());
            if (soDisplayElement != null) {
                SemanticObject formElement = soDisplayElement.getObjectProperty(
                        org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(
                        "http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement"));
                if (formElement != null) {
                    FormElement fe = (FormElement) formElement.createGenericInstance();
                    boolean applyInlineEdit = false;
                    if (mode != null && mode.equalsIgnoreCase("edit") && (userCanEdit() && isEditable(formElement))) {
                        applyInlineEdit = true;
                        //atributo agregado para permitir administrar los archivos adjuntos
                        request.setAttribute("usrWithGrants", "true");
                    }

                    if (fe != null) {
                        if (formMgr.getSemanticObject() != null) {
                            fe.setModel(formMgr.getSemanticObject().getModel());
                        }
                        ret = fe.renderElement(request, elementBSC, property, property.getName(),
                                SWBFormMgr.TYPE_XHTML,
                                applyInlineEdit ? "inlineEdit" : SWBFormMgr.MODE_VIEW,
                                lang);
                    }
                }
            }
        }

        if (ret == null) {
            FormElement formElement = formMgr.getFormElement(property);
            if (formElement != null) {
                ret = formElement.renderElement(request, elementBSC, property, property.getName(),
                        SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
            }
        }
        return ret != null ? ret : "";
    }
    
    /**
     * Determina si el usuario en sesi&oacute;n tiene los permisos necesarios para hacer actualizaciones
     * en la informaci&oacute;n del elemento presentado en la pantalla.
     * @return un boleano que representa si el usuario tiene permitido hacer actualizaciones
     * ({@code true}) a la informaci&oacute;n presentada, de lo contrario devuelve {@code false}.
     */
    private boolean userCanEdit() {
        
        boolean access = false;
        String str_role = getResourceBase().getAttribute("editRole", null);
        
        final WebSite scorecard = getResourceBase().getWebSite();
        final User user = SWBContext.getSessionUser(scorecard.getUserRepository().getId());

        if (user != null) {
            if (str_role != null) {
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject gobj = null;
                try {
                    gobj = ont.getGenericObject(str_role);
                } catch (Exception e) {
                    RiskBoard.log.error("Error userCanEdit() - retrieving role", e);
                    return Boolean.FALSE;
                }

                UserGroup ugrp = null;
                Role urole = null;

                if (gobj != null) {
                    if (gobj instanceof UserGroup) {
                        ugrp = (UserGroup) gobj;
                        if (user.hasUserGroup(ugrp)) {
                            access = true;
                        }
                    } else if (gobj instanceof Role) {
                        urole = (Role) gobj;
                        if (user.hasRole(urole)) {
                            access = true;
                        }
                    }
                } else {
                    access = false;
                }
            }
        }
        return access;
    }
    
    /**
     * Eval&uacute;a si el {@code formElement} indicado es susceptible de edici&oacute;n a fin de
     * presentar la interface de edici&oacue;n en l&iacute;nea.
     * @param formElement el objeto de forma a presentar en la interface
     * @return {@literal true} si el {@code formElement} puede presentar
     * interface de edici&oacute;n, {@literal false} de lo contrario
     */
    private boolean isEditable(SemanticObject formElement) {
        return formElement.getProperty(TextAreaElement.bsc_editable) == null
                ? true : formElement.getBooleanProperty(TextAreaElement.bsc_editable);
    }

    /**
     * Permite la selecci&oacute;n de un grupo o rol a fin de permitir que los usuarios asociados
     * al elemento seleccionado sean los &uacute;nicos que pueden realizar modificaciones en el tablero
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder listCode = new StringBuilder(256);
        String lang = paramRequest.getUser().getLanguage();
        BSC bsc = (BSC) this.getResourceBase().getWebSite();
        Resource base = getResourceBase();
        String statusMsg = request.getParameter("statusMsg");
        String statusErr = request.getParameter("statusErr");
        
        if (bsc != null) {
            SWBResourceURL url = paramRequest.getActionUrl();
            url.setAction("setPerm");
            //Se genera el código HTML del encabezado de la tabla que muestra el listado de vistas
            listCode.append("<div class=\"swbform\">\n");

            listCode.append("<form dojoType=\"dijit.form.Form\" name=\"permissionsForm");
            listCode.append(base.getId());
            listCode.append("\" ");
            listCode.append("id=\"permissionsForm");
            listCode.append(base.getId());
            listCode.append("\" class=\"swbform\" onsubmit=\"submitForm('permissionsForm");
            listCode.append(base.getId());
            listCode.append("');return false;\" method=\"post\" action=\"");
            listCode.append(url.toString());
            listCode.append("\">\n");
            listCode.append("  <fieldset>\n");
            listCode.append("    <legend>");
            listCode.append(paramRequest.getLocaleString("lbl_grantsSelection"));
            listCode.append("</legend>\n");

            //Para mostrar la seleccion de otorgamiento de permisos
            String roleSelected = base.getAttribute("editRole", "");
            StringBuilder strRules = new StringBuilder(256);
            strRules.append("        <optgroup label=\"");
            strRules.append(paramRequest.getLocaleString("lbl_roles"));
            strRules.append("\">\n");
            Iterator<Role> iRoles = this.getResourceBase().getWebSite().getUserRepository().listRoles();
            while (iRoles.hasNext()) {
                Role oRole = iRoles.next();
                strRules.append("        <option value=\"");
                strRules.append(oRole.getURI());
                strRules.append("\"");
                strRules.append(roleSelected.equals(oRole.getURI()) ? " selected=\"selected\" " : "");
                strRules.append(">");
                strRules.append(oRole.getDisplayTitle(lang));
                strRules.append("</option>\n");
            }
            strRules.append("        </optgroup>\n");
            strRules.append("        <optgroup label=\"");
            strRules.append(paramRequest.getLocaleString("lbl_userGroups"));
            strRules.append("\">\n");
            Iterator<UserGroup> iugroups = this.getResourceBase().getWebSite().getUserRepository().listUserGroups();
            while (iugroups.hasNext()) {
                UserGroup oUG = iugroups.next();
                strRules.append("        <option value=\"");
                strRules.append(oUG.getURI());
                strRules.append("\"");
                strRules.append(roleSelected.equals(oUG.getURI()) ? " selected=\"selected\" " : "");
                strRules.append(">");
                strRules.append(oUG.getDisplayTitle(lang) == null ? oUG.getTitle() : oUG.getDisplayTitle(lang));
                strRules.append("</option>\n");
            }
            strRules.append("        </optgroup>\n");

            listCode.append("  <ul class=\"swbform-ul\">\n");
            listCode.append("    <li class=\"swbform-li\">\n");
            listCode.append("      <label for=\"updatePermit");
            listCode.append(base.getId());
            listCode.append("\" class=\"swbform-label\">");
            listCode.append(paramRequest.getLocaleString("lbl_permissionsSelect"));
            listCode.append("</label>\n");
            listCode.append("      <select name=\"updatePermit");
            listCode.append(base.getId());
            listCode.append("\">");
            listCode.append(strRules);
            listCode.append("       </select>\n");
            listCode.append("     </li>\n");
            listCode.append("  </ul>\n");
            listCode.append("  </fieldset>\n");
            listCode.append("  <fieldset>\n");
            listCode.append("    <button dojoType=\"dijit.form.Button\" id=\"btnSavePerm");
            listCode.append(base.getId());
            listCode.append("\" type=\"button\">");
            listCode.append(paramRequest.getLocaleString("lbl_btnSubmit"));
            listCode.append("      <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
            listCode.append("          submitForm(\"permissionsForm");
            listCode.append(base.getId());
            listCode.append("\");\n");
            listCode.append("          return false;\n");
            listCode.append("      </script>\n");
            listCode.append("    </button>\n");
            listCode.append("  </fieldset>\n");
            listCode.append("</form>\n");
            listCode.append("</div>\n");
        } else {
            RiskBoard.log.error(new SWBResourceException("No se tiene referencia a un BSC"));
        }
        
        //Muestra mensaje sobre resultado de la operacion realizada
        if ((statusMsg != null && !statusMsg.isEmpty())
                || (statusErr != null && !statusErr.isEmpty())) {
            listCode.append("<div dojoType=\"dojox.layout.ContentPane\">\n");
            listCode.append("    <script type=\"dojo/method\">\n");
            if (statusMsg != null) {
                statusMsg = paramRequest.getLocaleString(statusMsg);
                listCode.append("        showStatus('");
                listCode.append(statusMsg);
                listCode.append("');\n");
            } else if (statusErr != null) {
                statusErr = paramRequest.getLocaleString(statusErr);
                listCode.append("        showError('");
                listCode.append(statusErr);
                listCode.append("');\n");
            }
            listCode.append("    </script>\n");
            listCode.append("</div>\n");
        }
        out.println(listCode.toString());
    }

    /**
     * Realiza las operaciones de almacenamiento o eliminaci&oacute;n de
     * informaci&oacute;n de la configuraci&oacute;n del recurso.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response)
            throws SWBResourceException, IOException {
        
        String action = response.getAction();
        String statusMsg = null;
        String errorMsg = null;
        WebSite scorecard = response.getWebPage().getWebSite();
        User user = SWBContext.getSessionUser(scorecard.getUserRepository().getId());
        SWBFormMgr formMgr = null;
        GenericObject generic = null;
        Risk risk = null;
        String modeToShow = SWBResourceURL.Mode_VIEW;
        
        if (request.getParameter("urlRisk") != null) {
            generic = SemanticObject.createSemanticObject(URLDecoder.decode(request.getParameter("urlRisk")),
                scorecard.getSemanticModel()).createGenericInstance();
            if (generic instanceof Risk) {
                risk = (Risk) generic;
            }
        }
        
        if (action.equalsIgnoreCase("setPerm")) {
            Resource base = getResourceBase();

            String userType = request.getParameter("updatePermit" + base.getId());
            if (userType != null && !userType.isEmpty()) {
                base.setAttribute("editRole", userType);
            } else {
                base.removeAttribute("editRole");
            }
            statusMsg = "msg_PermissionAssigned";
            modeToShow = SWBResourceURL.Mode_ADMIN;
            
            try {
                base.updateAttributesToDB();
            } catch (SWBException swbe) {
                RiskBoard.log.error("Al asignar permisos", swbe);
                errorMsg = "err_PermissionAssigned";
            }
        } else if (action.equalsIgnoreCase(SWBActionResponse.Action_ADD)) {
            String objType = request.getParameter("objType");
            
            if (objType.equals(MitigationAction.bsc_MitigationAction.getClassCodeName()) && risk != null) {
                formMgr = new SWBFormMgr(MitigationAction.sclass, scorecard.getSemanticObject(), null);
                try {
                    SemanticObject sobj = formMgr.processForm(request);
                    MitigationAction mitAction = (MitigationAction) sobj.createGenericInstance();
                    mitAction.setActive(true);
                    SWBPortal.getServiceMgr().updateTraceable(mitAction.getSemanticObject(), user);
                    risk.addMitigationAction(mitAction);
                } catch (FormValidateException ex) {
                    RiskBoard.log.error("Al crear MitigationAction", ex);
                }
            } else if (objType.equals(Initiative.bsc_Initiative.getClassCodeName()) && risk != null) {
                formMgr = new SWBFormMgr(Initiative.sclass, scorecard.getSemanticObject(), null);
                try {
                    SemanticObject sobj = formMgr.processForm(request);
                    Initiative initiative = (Initiative) sobj.createGenericInstance();
                    initiative.setActive(true);
                    SWBPortal.getServiceMgr().updateTraceable(initiative.getSemanticObject(), user);
                    risk.addInitiative(initiative);
                } catch (FormValidateException ex) {
                    RiskBoard.log.error("Al crear Initiative", ex);
                }
            }
            
        } else if (action.equalsIgnoreCase(SWBActionResponse.Action_EDIT)) {
            String suri = request.getParameter("suri");
            SemanticObject semObj = SemanticObject.getSemanticObject(suri);
            if (semObj != null) {
                formMgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
                try {
                    formMgr.processForm(request);
                    SWBPortal.getServiceMgr().updateTraceable(semObj, user);
                } catch (FormValidateException ex) {
                    RiskBoard.log.error("Al modificar informacion de: " + semObj.getURI(), ex);
                }
            }
            /*
            if (objType.equals(MitigationAction.bsc_MitigationAction.getClassCodeName())) {
            
            } else if (objType.equals(Initiative.bsc_Initiative.getClassCodeName())) {
                
            }*/
        } else if (action.equalsIgnoreCase(SWBActionResponse.Action_REMOVE) && risk != null) {
            String suri = request.getParameter("suri");
            GenericObject genericObj = SemanticObject.getSemanticObject(suri).createGenericInstance();
            if (genericObj instanceof MitigationAction) {
                MitigationAction mitAction = (MitigationAction) genericObj;
                risk.removeMitigationAction(mitAction);
            } else if (genericObj instanceof Initiative) {
                Initiative ini = (Initiative) genericObj;
                risk.removeInitiative(ini);
            }
        } else {
            super.processAction(request, response);
        }
        
        if (statusMsg != null) {
            response.setRenderParameter("statusMsg", statusMsg);
        }
        if (errorMsg != null) {
            response.setRenderParameter("errorMsg", errorMsg);
        }
        response.setMode(modeToShow);
    }
    
    /**
     * Valida los valores asignados a los rubros de la valoraci&oacute;n final del riesgo
     * @param initialValue valor asignado al rubro de valoraci&oacute;n inicial
     * @param finalValue valor asignado al rubro de valoraci&oacute;n final
     * @param riskControled valor calculado sobre si el riesgo es controlado suficientemente
     * @return {@code true} en caso de que el valor final sea correcto, {@code false} de lo contrario
     */
    private boolean validateAssessment(int initialValue, int finalValue, boolean riskControled) {
        
        boolean validation = true;
        
        if (finalValue > initialValue) {
            validation = false;
        }
        if (riskControled && finalValue >= initialValue) {
            validation = false;
        }
        if (!riskControled && finalValue != initialValue) {
            validation = false;
        }
        return validation;
    }

    /**
     * Distribuye las peticiones recibidas entre los diferentes modos de vista del recurso, esto es, en base al modo
     * indicado en la petici&oacute;n se decide qu&eacute;m&eacute;todo es el que resolver&aacute; la petici&oacute;n.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta HTTP que se genera en base al contenido de la petici&oacute;n
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     *         petici&oacute;n/respuesta.
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        switch (paramRequest.getMode()) {
            case "showAddWindow":
                doShowAddWindow(request, response, paramRequest);
                break;
            case "addElement":
                doAddElement(request, response, paramRequest);
                break;
            case "editElement":
                doEditElement(request, response, paramRequest);
                break;
            case "export":
                doGetExportedDocument(request, response, paramRequest);
                break;
            default: super.processRequest(request, response, paramRequest);
        }
    }
    
    /**
     * Presenta la interface para definir si se desea crear una instancia de {@code Action} o 
     * {@code Initiative} asociada a un riesgo.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta HTTP que se genera en base al contenido de la petici&oacute;n
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     *         petici&oacute;n/respuesta.
     */
    private void doShowAddWindow(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(512);
        WebSite website = paramRequest.getWebPage().getWebSite();
        SWBResourceURL url = paramRequest.getRenderUrl();
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        url.setMode("addElement");
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        output.append("<form id=\"addCriteria\" name=\"addCriteria\" action=\"");
        output.append(url);
        //postHtml(url, tagid)
        //submitFormPortal(formid)
        output.append("\" onsubmit=\"validateSubmit(this);return false;\" class=\"swbform\" method=\"post\">\n");
        output.append("  <fieldset>\n");
        output.append("    <legend>Selecciona los datos para crear un registro</fieldset>\n");
        output.append("    <label for=\"risk\">");
        output.append(paramRequest.getLocaleString("lbl_selectRisk"));
        output.append("</label>\n");
        output.append("    <select id=\"risk\" name=\"risk\">\n");
        output.append("      <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_emptyOption"));
        output.append("</option>\n");
        
        Iterator<Risk> riskIt = Risk.ClassMgr.listRisks(website);
        while (riskIt != null && riskIt.hasNext()) {
            Risk risk = riskIt.next();
            output.append("      <option value=\"");
            output.append(risk.getURI());
            output.append("\">");
            output.append(risk.getTitle());
            output.append("</option>\n");
        }
        output.append("    </select>\n");
        output.append("    <br>\n");
        output.append("    <label for=\"objType\">");
        output.append(paramRequest.getLocaleString("lbl_selectObject"));
        output.append("</label>\n");
        output.append("    <select id=\"objType\" name=\"objType\">\n");
        output.append("      <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_emptyOption"));
        output.append("</option>\n");
        output.append("      <option value=\"MitigationAction\">Acci&oacute;n</option>\n");
        output.append("      <option value=\"Initiative\">Iniciativa</option>\n");
        output.append("    </select>\n");
        output.append("  </fieldset>\n");
        output.append("  <fieldset>\n");
        output.append("  </fieldset>\n");
        output.append("          <button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"continuar\">");
        output.append(paramRequest.getLocaleString("lbl_continuateBtn"));
        output.append("</button>");
        output.append("          <button dojoType=\"dijit.form.Button\" onclick=\"dijit.byId('swbDialog').hide()\">");
        output.append(paramRequest.getLocaleString("lbl_cancelBtn"));
        output.append("</button>");
        output.append("</form>\n");
        output.append("    ");
        output.append("");
        output.append("");
        output.append("");
        out.println(output.toString());
    }
    
    /**
     * Presenta la interface para la captura de los datos correspondientes al elemento a crear.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta HTTP que se genera en base al contenido de la petici&oacute;n
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     *         petici&oacute;n/respuesta.
     */
    private void doAddElement(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(512);
        WebSite website = paramRequest.getWebPage().getWebSite();
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction(Action_ADD);
        url.setCallMethod(SWBResourceURL.Call_CONTENT);
        SWBFormMgr formMgr = null;
        String objectType = request.getParameter("objType");
        String riskUrl = request.getParameter("risk");
        GenericObject semObj = SemanticObject.createSemanticObject(riskUrl).createGenericInstance();
        Risk risk = null;
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        if (semObj instanceof Risk) {
            risk = (Risk) semObj;
        }
        if (risk != null && objectType != null) {
            if (objectType.equals("MitigationAction")) {
                formMgr = new SWBFormMgr(MitigationAction.bsc_MitigationAction,
                        website.getSemanticModel().getModelObject(), SWBFormMgr.MODE_CREATE);
            } else if (objectType.equals("Initiative")) {
                formMgr = new SWBFormMgr(Initiative.bsc_Initiative,
                        website.getSemanticModel().getModelObject(), SWBFormMgr.MODE_CREATE);
            }
            /*formMgr.clearProperties();
            formMgr.addProperty(Action.swb_title);
            formMgr.addProperty(Action.swb_description);*/
            formMgr.setType(SWBFormMgr.TYPE_DOJO);
            //formMgr.setSubmitByAjax(true);
            formMgr.setOnSubmit("submitAndReload(this.id);return false;");
            formMgr.setMode(SWBFormMgr.MODE_CREATE);
            formMgr.addHiddenParameter("urlRisk", risk.getURI());
            formMgr.addHiddenParameter("objType", objectType);
            formMgr.addButton("          <button dojoType=\"dijit.form.Button\" type=\"submit\" "
                    + "name=\"enviar\" >" + paramRequest.getLocaleString("lbl_createBtn") + "</button>");
            formMgr.addButton("          <button dojoType=\"dijit.form.Button\" "
                    + "onclick=\"dijit.byId('swbDialog').hide()\">"
                    + paramRequest.getLocaleString("lbl_cancelBtn") + "</button>");
            formMgr.setAction(url.toString());
            output.append("<div id=\"frmAdd\">");
            output.append(formMgr.renderForm(request));
            output.append("</div>");
        }
        out.println(output.toString());
    }
    
    /**
     * Genera el c&oacute;digo HTML con el que se presentan los datos de las acciones relacionadas a los riesgos
     * @param risk la instancia {@code Risk} a la que estan asociadas las acciones a desplegar
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @param mode indica el modo de despliegue de la informaci&oacute;n. Si es {@literal edit} se presentan las
     *         opciones para editar y eliminar cada elemento mostrado, sino solo se muestra el t&iacute;tulo del elemento
     * @return un {@code String} que representa al c&oacute;digo HTML para el despliegue de las acciones de un riesgo
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     */
    private String getActionsDisplay(Risk risk, SWBParamRequest paramRequest, String mode)
            throws SWBResourceException {
        
        StringBuilder data = new StringBuilder (256);
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode("editElement");
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        SWBResourceURL urlDelete = paramRequest.getActionUrl();
        urlDelete.setAction(SWBResourceURL.Action_REMOVE);
        urlDelete.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlDelete.setParameter("urlRisk", risk.getEncodedURI());
        
        Iterator<MitigationAction> actionIt = risk.listMitigationActions();
        if (actionIt != null && actionIt.hasNext()) {
            data.append("      <table height=\"100%\">\n");
            while (actionIt.hasNext()) {
                MitigationAction action = actionIt.next();
                data.append("      <tr>\n");
                data.append("        <td>");
                data.append(action.getTitle());
                data.append("</td>\n");
                if (mode != null && mode.equalsIgnoreCase("edit")) {
                    data.append("        <td>");
                    data.append("<a href=\"#\" onclick=\"showMyDialog('");
                    data.append(url);
                    data.append("?suri=");
                    data.append(action.getEncodedURI());
                    data.append("', '");
                    data.append(action.getTitle());
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append(paramRequest.getLocaleString("path_editImg"));
                    data.append("\" alt=\"");
                    data.append(paramRequest.getLocaleString("lbl_editAlt"));
                    data.append("\"></a>&nbsp;&nbsp;&nbsp;");
                    data.append("<a href=\"#\" onclick=\"deleteElement(1, '");
                    data.append(action.getTitle());
                    data.append("', '");
                    data.append(urlDelete.toString());
                    data.append("&suri=");
                    data.append(action.getEncodedURI());
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append(paramRequest.getLocaleString("path_deleteImg"));
                    data.append("\" alt=\"");
                    data.append(paramRequest.getLocaleString("lbl_deleteAlt"));
                    data.append("\"></a>");
                    data.append("</td>\n");
                }
                data.append("      </tr>\n");
            }
            data.append("      </table>\n");
        }
        
        return data.toString();
    }
    
    /**
     * Genera el c&oacute;digo HTML con el que se presentan los datos de las iniciativas relacionadas a los riesgos
     * @param risk la instancia {@code Risk} a la que estan asociadas las iniciativas a desplegar
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @param mode indica el modo de despliegue de la informaci&oacute;n. Si es {@literal edit} se presentan las
     *         opciones para editar y eliminar cada elemento mostrado, sino solo se muestra el t&iacute;tulo del elemento
     * @return un {@code String} que representa al c&oacute;digo HTML para el despliegue de las iniciativas de un riesgo
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     */
    private String getInitiativesDisplay(Risk risk, SWBParamRequest paramRequest, String mode)
            throws SWBResourceException {
        
        StringBuilder data = new StringBuilder (256);
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode("editElement");
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        SWBResourceURL urlDelete = paramRequest.getActionUrl();
        urlDelete.setAction(SWBResourceURL.Action_REMOVE);
        urlDelete.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlDelete.setParameter("urlRisk", risk.getEncodedURI());
        
        Iterator<Initiative> iniIt = risk.listInitiatives();
        if (iniIt != null && iniIt.hasNext()) {
            data.append("      <table>\n");
            while (iniIt.hasNext()) {
                Initiative initiative = iniIt.next();
                data.append("      <tr>\n");
                data.append("        <td>");
                data.append(initiative.getTitle());
                data.append("</td>\n");
                if (mode != null && mode.equalsIgnoreCase("edit")) {
                    data.append("        <td>");
                    data.append("<a href=\"#\" onclick=\"showMyDialog('");
                    data.append(url);
                    data.append("?suri=");
                    data.append(initiative.getEncodedURI());
                    data.append("', '");
                    data.append(initiative.getTitle());
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append(paramRequest.getLocaleString("path_editImg"));
                    data.append("\" alt=\"");
                    data.append(paramRequest.getLocaleString("lbl_editAlt"));
                    data.append("\"></a>&nbsp;&nbsp;&nbsp;");
                    data.append("<a href=\"#\" onclick=\"deleteElement(1, '");
                    data.append(initiative.getTitle());
                    data.append("', '");
                    data.append(urlDelete.toString());
                    data.append("&suri=");
                    data.append(initiative.getEncodedURI());
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append(paramRequest.getLocaleString("path_deleteImg"));
                    data.append("\" alt=\"");
                    data.append(paramRequest.getLocaleString("lbl_deleteAlt"));
                    data.append("\"></a>");
                    data.append("</td>\n");
                }
                data.append("      </tr>\n");
            }
            data.append("      </table>\n");
        }
        
        return data.toString();
    }
    
    /**
     * Presenta la interface para la edici&oacute;n de los datos correspondientes al elemento indicado 
     * por el par&aacute;metro {@code suri} en {@code request}.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta HTTP que se genera en base al contenido de la petici&oacute;n
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     *         petici&oacute;n/respuesta.
     */
    private void doEditElement(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(512);
        boolean bigDisplay = false;
        //WebSite website = paramRequest.getWebPage().getWebSite();
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction(SWBResourceURL.Action_EDIT);
        url.setCallMethod(SWBResourceURL.Call_CONTENT);
        //SWBFormMgr formMgr = null;
        SemanticObject semObject = SemanticObject.createSemanticObject(URLDecoder.
                decode(request.getParameter("suri")));
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        if (semObject != null) {
            GenericObject generic = semObject.createGenericInstance();
            SWBFormMgr formMgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            formMgr.clearProperties();
            if (generic instanceof MitigationAction) {
                formMgr.addProperty(MitigationAction.swb_title);
                formMgr.addProperty(MitigationAction.swb_description);
                formMgr.addProperty(MitigationAction.bsc_progressPercentage);
                formMgr.addProperty(MitigationAction.bsc_progressDescription);
            } else if (generic instanceof Initiative) {
                formMgr.addProperty(Initiative.swb_title);
                formMgr.addProperty(Initiative.swb_description);
                formMgr.addProperty(Initiative.bsc_businessCase);
                formMgr.addProperty(Initiative.bsc_initiativeFacilitator);
                formMgr.addProperty(Initiative.bsc_area);
                formMgr.addProperty(Initiative.bsc_investmentAmount);
                formMgr.addProperty(Initiative.bsc_percentageProgress);
                formMgr.addProperty(Initiative.bsc_totalInvestment);
                formMgr.addProperty(Initiative.bsc_analysis);
                formMgr.addProperty(Initiative.bsc_recommendations);
                formMgr.addProperty(Initiative.bsc_actualEnd);
                formMgr.addProperty(Initiative.bsc_actualStart);
                formMgr.addProperty(Initiative.bsc_estimatedEnd);
                formMgr.addProperty(Initiative.bsc_estimatedStart);
                formMgr.addProperty(Initiative.bsc_plannedEnd);
                formMgr.addProperty(Initiative.bsc_plannedStart);
                bigDisplay = true;
            }
            formMgr.setType(SWBFormMgr.TYPE_DOJO);
            formMgr.setSubmitByAjax(true);
            formMgr.setOnSubmit("mySubmitForm(this.id)");
            formMgr.setMode(SWBFormMgr.MODE_EDIT);
            formMgr.addButton("          <button dojoType=\"dijit.form.Button\" type=\"submit\" "
                    + "name=\"enviar\" >" + paramRequest.getLocaleString("lbl_editBtn") + "</button>\n");
            formMgr.addButton("          <button dojoType=\"dijit.form.Button\" "
                    + "onclick=\"dijit.byId('swbDialog').hide()\">"
                    + paramRequest.getLocaleString("lbl_cancelBtn") + "</button>\n");
            formMgr.setAction(url.toString());
            output.append("<div id=\"frmEdit\"");
            if (bigDisplay) {
                output.append(" style=\"max-height:400px;\"");
            }
            output.append(">");
            
            //Se elimina la liga para captura de los valores localizados de las propiedades title y description
            String htmlCode = formMgr.renderForm(request);
            int fromIndex = 0;
            int toIndex = 0;
            if (htmlCode.indexOf("<input name=\"title\"") > 0) {
                fromIndex = htmlCode.indexOf("<a href=", htmlCode.indexOf("<input name=\"title\""));
                toIndex = htmlCode.indexOf("locale</a>", fromIndex);
                String beginning = htmlCode.substring(0, fromIndex);
                String ending = htmlCode.substring(toIndex + "locale</a>".length());
                htmlCode = beginning + ending;
            }
            if (htmlCode.indexOf("<textarea name=\"description\"") > 0) {
                fromIndex = htmlCode.indexOf("<a href=", htmlCode.indexOf("<textarea name=\"description\""));
                toIndex = htmlCode.indexOf("locale</a>", fromIndex);
                String beginning = htmlCode.substring(0, fromIndex);
                String ending = htmlCode.substring(toIndex + "locale</a>".length());
                htmlCode = beginning + ending;
            }
                    
            output.append(htmlCode);
            output.append("</div>");
        }
        out.println(output.toString());
    }
    
    /**
     * Genera la vista del recurso cuando es ejecutado como recurso de estrategia
     * @param request la petici&oacute;n HTML hecha por el cliente
     * @param paramRequest objeto por el que se accede a varios objetos exclusivos de SWB
     * @return un {@code String} que representa las ligas de HTML hacia los m&eacute;todos que muestran el tablero 
     * como archivos PDF o de Excel.
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     *         de la plataforma de SWB para la correcta ejecuci&oacute;n del
     *         m&eacute;todo. Como la extracci&oacute;n de valores para
     *         par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     *         petici&oacute;n/respuesta.
     */
    private String doIconExport(HttpServletRequest request, SWBParamRequest paramRequest) 
            throws SWBResourceException, IOException {
        
        StringBuilder ret = new StringBuilder(128);
        SWBResourceURL url = new SWBResourceURLImp(request, getResourceBase(),
                paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
        SWBResourceURL urlExcel = new SWBResourceURLImp(request, getResourceBase(),
                paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
        url.setMode("export");
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        url.setParameter("dispMode", "view");
        url.setParameter("type", "pdf");
        String titlePdf = paramRequest.getLocaleString("msg_PrintPDFDocument");
        ret.append("<a href=\"");
        ret.append(url);
        ret.append("\" class=\"swbstgy-toolbar-printPdf\" title=\"");
        ret.append(titlePdf);
        ret.append("\">");
        ret.append(titlePdf);
        ret.append("</a>");
        urlExcel.setMode("export");
        urlExcel.setCallMethod(SWBResourceURL.Call_DIRECT);
        urlExcel.setParameter("dispMode", "view");
        urlExcel.setParameter("type", "excel");
        String titleExcel = paramRequest.getLocaleString("msg_PrintXlDocument");
        ret.append("&nbsp; &nbsp; <a href=\"");
        ret.append(urlExcel);
        ret.append("\" class=\"swbstgy-toolbar-printExcel\" title=\"");
        ret.append(titleExcel);
        ret.append("\">");
        ret.append(titleExcel);
        ret.append("</a>");
        return ret.toString();
    }
    
    /**
     * Genera la misma vista de informaci&oacute;n que el m&eacute;todo {@code doView} pero con formato PDF o Excel, 
     * de acuerdo al par&aacute;metro {@code type} recibido en {@code request}.
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @param paramRequest objeto por el que se accede a varios exclusivos de SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    public void doGetExportedDocument(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        WebSite website = paramRequest.getWebPage().getWebSite();
        StringBuilder output = new StringBuilder(512);
        String htmlCode = this.generateBoardView(website, "view", request, paramRequest);
        String displayType = request.getParameter("type") != null ? request.getParameter("type") : null;
        String fileExtension = null;
        
        if (displayType != null && displayType.equalsIgnoreCase("excel")) {
            response.setContentType("application/vnd.ms-excel; charset=ISO-8859-1");
            fileExtension = "xlsx";
        } else {
            response.setContentType("application/pdf; charset=ISO-8859-1");
            fileExtension = "pdf";
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        if (displayType != null && !displayType.equalsIgnoreCase("excel")) {
            response.setHeader("Content-Disposition", "attachment; filename=\"" +
                    getResourceBase().getWebSiteId() + ".tableroRiesgos." + fileExtension + "\"");
        } else if (displayType != null && displayType.equalsIgnoreCase("excel")) {
            response.setHeader("Content-Disposition", "inline; filename=\"tableroRiesgos.xls\"");  //- See more at: http://www.quicklyjava.com/export-web-page-to-excel/#sthash.dS3knjmu.dpuf
        }
        output.append("<html>");
        output.append("<head>");
        if (fileExtension.equals("pdf")) {
            float paperHeight = 21.59F;
            if (request.getAttribute("totalRows") != null) {
                try {
                    paperHeight = (((Integer) request.getAttribute("totalRows")).intValue()) * 1.8F;
                } catch (NumberFormatException nfe) {
                    paperHeight = 21.59F;
                }
            }
            output.append("<style type=\"text/css\">");
            output.append("    @page { size: 90cm ");
            output.append(paperHeight);
            output.append("cm;}");  // 11in 8.5in
            output.append("</style>");
        }
        output.append(this.getLinks(paramRequest, request));
        output.append(this.getStylesString());
        output.append("</head>");
        output.append("<body>");
        output.append(htmlCode);
        output.append("</body>");
        output.append("</html>");
        
        if (fileExtension.equals("pdf")) {
            if (htmlCode != null && htmlCode.length() > 0) {
                OutputStream os = response.getOutputStream();
                try {
                    ITextRenderer renderer = new ITextRenderer();
                    //renderer.setDocumentFromString(renderHTML(request, response, paramRequest));
                    String sbStr = this.replaceHtml(output.toString());
                    renderer.setDocumentFromString(sbStr);
                    renderer.layout();
                    renderer.createPDF(os);
                    renderer.finishPDF();
                } catch (DocumentException ex) {
                    log.error("Error in: " + ex);
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) { /*ignore*/ }
                    }
                }
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println(SWBUtils.TEXT.replaceAll(output.toString(), " textCentered", ""));
        }
    }
    
    /**
     * Reemplaza c&oacute;digo HTML por acentos, esto es para la estructura XML
     * requerida.
     * @param htmlString el objeto String en que se reemplazar&aacute;n las entidades de HTML por car&aacute;cteres.
     * @return el objeto String modificado
     */
    private String replaceHtml(String htmlString) {
        String sbStr = SWBUtils.TEXT.replaceAll(htmlString, "&oacute;", "ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&aacute;", "á");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&eacute;", "é");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&iacute;", "í");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&oacute;", "ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&uacute;", "ú");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Aacute;", "Á");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Eacute;", "É");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Iacute;", "Í");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Oacute;", "Ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Uacute;", "Ú");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&nbsp;", " ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&lt;", "<");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&gt;", ">");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&amp;", "&");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&quot;", "\"");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&iexcl;", "¡");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&iquest;", "¿");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&reg;", "®");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&copy;", "©");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&euro;", "€");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&ntilde;", "ñ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&uuml", "ü");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Ntilde;", "Ñ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Uuml;", "Ü");
        return sbStr;
    }
    
    /**
     * Obtiene los links de la plantilla de la p&aacute;gina actual
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @return el objeto String que representa el c&oacute;digo HTML con los
     * links hacia los CSS respectivos.
     * @throws FileNotFoundException Archivo no ubicado
     * @throws IOException Excepti&oacute;n de IO
     */
    private String getLinks(SWBParamRequest paramRequest, HttpServletRequest request)
            throws FileNotFoundException, IOException {
        
        User user = paramRequest.getUser();
        WebPage wp = paramRequest.getWebPage();
        
        Template template = SWBPortal.getTemplateMgr().getTemplate(user, wp);
        String filePath = template.getWorkPath() + "/" + 
                template.getActualVersion().getVersionNumber() + "/"
                + template.getFileName(template.getActualVersion().getVersionNumber());
        FileReader reader = null;
        StringBuilder view = new StringBuilder(256);
        reader = new FileReader(filePath);

        String port = "";
        if (request.getServerPort() != 80) {
            port = ":" + request.getServerPort();
        }
        String baserequest = request.getScheme() + "://" + request.getServerName() + port;

        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(reader);
        HtmlTag tag = new HtmlTag();
        while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
            int ttype = tok.getTokenType();

            if (ttype == HtmlStreamTokenizer.TT_TAG) {
                try {
                    tok.parseTag(tok.getStringValue(), tag);
                } catch (HtmlException htmle) {
                    RiskBoard.log.error("Al parsear la plantilla , "
                            + filePath, htmle);
                }
                if (tag.getTagString().toLowerCase().equals("link")) {
                    String tagTxt = tag.toString();
                    if (tagTxt.contains("type=\"text/css\"")) {
                        if (!tagTxt.contains("/>")) {
                            tagTxt = SWBUtils.TEXT.replaceAll(tagTxt, ">", "/>");
                        }
                        if (!tagTxt.contains("{webpath}")) {
                            String tmpTxt = tagTxt.substring(0, (tagTxt.indexOf("href") + 6));
                            String tmpTxtAux = tagTxt.substring((tagTxt.indexOf("href") + 6),
                                    tagTxt.length());
                            tagTxt = tmpTxt + baserequest + tmpTxtAux;
                        } else {
                            tagTxt = SWBUtils.TEXT.replaceAll(tagTxt, "{webpath}", baserequest);
                        }
                        view.append(tagTxt);
                    }
                }
            }
        }
        return view.toString();
    }
    
    /**
     * Genera un {@code String} que representa los estilos CSS a utilizar para la presentaci&oacute;n 
     * del tablero. Obtiene del modelo correspondiente las propiedades: {@literal quadrant1HexColor}, {@literal quadrant2HexColor},
     * {@literal quadrant3HexColor} y {@literal quadrant4HexColor} para utilizar su contenido en el armado de los estilos.
     * @return el {@code String} con los estilos del tablero de riesgos
     */
    private String getStylesString() {
        
        WebSite website = this.getResourceBase().getWebSite();
        String stylesString = "<style>\n" + 
                "    table, th, td {  border: 1px solid black; background-color:#FFFFFF; padding:3px; }\n" +
                "    th {  text-align:center; background-color:#EEEEEE;  }\n" + 
                "    .evalRiesgo {  background-color:#87CEEB;  }\n" + 
                "    .evalControl {  background-color:#1E90FF;  }\n" + 
                "    .valRiesgoCtrl {  background-color:#6495ED; color:#FFFFFF;  }\n" + 
                "    .mapaRiesgoth {  background-color:#FFFFFF;  }\n" +
                "    .accionIniciativa {  background-color:#0000CD; color:#FFFFFF;  }\n" +
                "    .cuadrante1 {  background-color:" + 
                (website.getModelProperty("quadrant1HexColor") != null ? website.getModelProperty("quadrant1HexColor") : "#FF0000") +
                ";  }\n" +
                "    .cuadrante2 {  background-color:" +
                (website.getModelProperty("quadrant2HexColor") != null ? website.getModelProperty("quadrant2HexColor") : "#FFFF00") +
                ";  }\n" +
                "    .cuadrante3 {  background-color:" +
                (website.getModelProperty("quadrant3HexColor") != null ? website.getModelProperty("quadrant3HexColor") : "#00FFFF") +
                ";  }\n" +
                "    .cuadrante4 {  background-color:" +
                (website.getModelProperty("quadrant4HexColor") != null ? website.getModelProperty("quadrant4HexColor") : "#3CB371") +
                ";  }\n" +
                "    .riesgoControlado {  background-color:#389738;  }\n" +
                "    .riesgoNoControlado {  background-color:#FFFF80;  }\n" +
                "    .noValido {  background-color:#FF6666;  }\n" +
                "    .textCentered {  text-align:center;  }\n" +
                "</style>\n";
        return stylesString;
    }
    
}
