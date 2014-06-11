/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.bsc.resources;

import com.hp.hpl.jena.rdf.model.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.semanticwb.SWBPlatform;
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
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.lib.SWBRequest;

/**
 *
 * @author jose.jimenez
 */
public class RiskBoardTest {
    
    final static private String USERGROUPID = "Risks";
    final static private String MODE = "view";
    final static private String WEBSITE_ID = "InfotecPEMP2";
    private SWBRequest request = new SWBRequest();
    
    
    public RiskBoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTOREEXT);
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../../../../build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../../../../build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "/../../../../../../build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    public void setUpSessionUser(BSC model) {
        Iterator it = model.getUserRepository().listUsers();
        UserGroup userGroup = UserGroup.ClassMgr.getUserGroup(null, model);
        while (it.hasNext()) {
            User user = (User) it.next();
            if (user.hasUserGroup(userGroup)) {
                SWBContext.setSessionUser(user);
                break;
            }
        }
        //request = new SWBRequest();
    }
    
     @Test
    public void BoardStructure() {
        WebSite website = WebSite.ClassMgr.getWebSite(RiskBoardTest.WEBSITE_ID);
        StringBuilder output = new StringBuilder(512);
        ArrayList<Determinant> detList = (ArrayList<Determinant>) Determinant.listValidDeterminants(website);
        Determinant[] determinants = new Determinant[detList.size()];
        int cont = 0;
        //Se asegura el orden de despliegue de los determinantes al utilizar un arreglo
        for (Determinant det : detList) {
            determinants[cont] = det;
            cont++;
        }
        output.append("<table>\n");
        output.append(this.createTableHeading(determinants));
        output.append(this.createTableBody(determinants, website, RiskBoardTest.MODE, request));
        output.append("</table>\n");
        System.out.println(output.toString());
    }
     
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

    private String createTableBody(Determinant[] determinants, WebSite website, String mode, HttpServletRequest request) {
        
        StringBuilder data = new StringBuilder(256);
        SemanticProperty[] factorFields = {
                Factor.bsc_prefix, Factor.swb_title, Factor.bsc_classificationFactor, Factor.bsc_factorType
            };
        SemanticProperty[] controlFields = {Control.bsc_prefix, Control.swb_title, Control.bsc_controlType};
        Iterator<Risk> risks = Risk.ClassMgr.listRisks(website);
        String simpleTd = "      <td>\n";
        String simpleTextCenteredTd = "      <td class=\"textCentered\">\n";
        String lang = "es";
        User user = SWBContext.getSessionUser();
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
            
            Iterator<Factor> factorIt = risk.listFactors();
            while (factorIt != null && factorIt.hasNext()) {
                Factor factor = factorIt.next();
                Iterator<Control> controlIt = factor.listControls();
                int controlCount = 0;
                while (controlIt != null && controlIt.hasNext()) {
                    Control control = controlIt.next();
                    rows[ctrlRowCount][0] = factor.getSemanticObject();
                    rows[ctrlRowCount][1] = control.getSemanticObject();
//                    System.out.println("Pos: [" + ctrlRowCount + "][0]: " + factor.getURI());
//                    System.out.println("Pos: [" + ctrlRowCount + "][1]: " + control.getURI());
//                    System.out.println("controlCount: " + controlCount);
                    ctrlRowCount++;
                    controlCount++;
                }
                if (controlCount == 0) {
                    rows[ctrlRowCount][0] = factor.getSemanticObject();
                    ctrlRowCount++;
//                    System.out.println("Pos: [" + ctrlRowCount + "][0]: " + factor.getURI() + "  -- controlCount == 0");
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
                data.append(this.getActionsDisplay(risk, mode));
            } catch (SWBResourceException swbe) {
                System.out.println("Despliegue de Actions, risk = " + risk.getURI());
            }
            data.append(tdEnclosing);
            data.append(spanRiskTd);
            try {
                data.append(this.getInitiativesDisplay(risk, mode));
            } catch (SWBResourceException swbe) {
                System.out.println("Despliegue de Initiatives, risk = " + risk.getURI());
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
    
    private short calculateRowSpan(Risk risk, Factor factor) {
        
        short span = 0;
        short factorCont = 0;
        
        if (risk != null) {
            Iterator<Factor> factorIt = risk.listFactors();
            while (factorIt != null && factorIt.hasNext()) {
                Factor riskFactor = factorIt.next();
                short controlCont = 0;
                Iterator<Control> controlIt = riskFactor.listControls();
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
            Iterator<Control> controlIt = factor.listControls();
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

    private boolean userCanEdit() {
        
        boolean access = false;
        String str_role = RiskBoardTest.USERGROUPID;
        
        final WebSite scorecard = WebSite.ClassMgr.getWebSite(RiskBoardTest.WEBSITE_ID);
        final User user = SWBContext.getSessionUser(scorecard.getUserRepository().getId());

        if (user != null) {
            if (str_role != null) {
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject gobj = null;
                try {
                    gobj = ont.getGenericObject(str_role);
                } catch (Exception e) {
                    System.out.println("Error userCanEdit() - retrieving role");
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
    
    private boolean isEditable(SemanticObject formElement) {
        return formElement.getProperty(TextAreaElement.bsc_editable) == null
                ? true : formElement.getBooleanProperty(TextAreaElement.bsc_editable);
    }

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
    
    private String getActionsDisplay(Risk risk, String mode)
            throws SWBResourceException {
        
        StringBuilder data = new StringBuilder (256);
        String url = "/es/InfotecPEMP/RiskBoard/_rid/34/_mto/3/_mod/editElement?suri=http%3A%2F%2Fwww.InfotecPEMP.swb%23bsc_MitigationAction%3A13";
        String urlDelete = "/es/InfotecPEMP/RiskBoard/_aid/34/_mto/3/_act/remove?urlRisk=http%253A%252F%252Fwww.InfotecPEMP.swb%2523bsc_Risk%253A2&suri=http%3A%2F%2Fwww.InfotecPEMP.swb%23bsc_MitigationAction%3A13";
        
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
                    data.append("', '");
                    data.append(action.getTitle());
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append("/swbadmin/icons/editar_1.gif");
                    data.append("\" alt=\"");
                    data.append("Editar");
                    data.append("\"></a>&nbsp;&nbsp;&nbsp;");
                    data.append("<a href=\"#\" onclick=\"deleteElement(1, '");
                    data.append(action.getTitle());
                    data.append("', '");
                    data.append(urlDelete);
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append("/swbadmin/icons/iconelim.gif");
                    data.append("\" alt=\"");
                    data.append("Eliminar");
                    data.append("\"></a>");
                    data.append("</td>\n");
                }
                data.append("      </tr>\n");
            }
            data.append("      </table>\n");
        }
        
        return data.toString();
    }
    
    private String getInitiativesDisplay(Risk risk, String mode)
            throws SWBResourceException {
        
        StringBuilder data = new StringBuilder (256);
        String url = "/es/InfotecPEMP/RiskBoard/_rid/34/_mto/3/_mod/editElement?suri=http%3A%2F%2Fwww.InfotecPEMP.swb%23bsc_Initiative%3A10";
        String urlDelete = "/es/InfotecPEMP/RiskBoard/_aid/34/_mto/3/_act/remove?urlRisk=http%253A%252F%252Fwww.InfotecPEMP.swb%2523bsc_Risk%253A2&suri=http%3A%2F%2Fwww.InfotecPEMP.swb%23bsc_Initiative%3A10";
        
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
                    data.append("', '");
                    data.append(initiative.getTitle());
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append("/swbadmin/icons/editar_1.gif");
                    data.append("\" alt=\"");
                    data.append("Editar");
                    data.append("\"></a>&nbsp;&nbsp;&nbsp;");
                    data.append("<a href=\"#\" onclick=\"deleteElement(1, '");
                    data.append(initiative.getTitle());
                    data.append("', '");
                    data.append(urlDelete.toString());
                    data.append("&suri=");
                    data.append(initiative.getEncodedURI());
                    data.append("');\"><img src=\"");
                    data.append(SWBPlatform.getContextPath());
                    data.append("/swbadmin/icons/iconelim.gif");
                    data.append("\" alt=\"");
                    data.append("Eliminar");
                    data.append("\"></a>");
                    data.append("</td>\n");
                }
                data.append("      </tr>\n");
            }
            data.append("      </table>\n");
        }
        
        return data.toString();
    }
    
    
     
}
