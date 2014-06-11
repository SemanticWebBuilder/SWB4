package org.semanticwb.bsc.resources;

import com.arthurdo.parser.HtmlException;
import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Statement;
import com.lowagie.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.PDFExportable;
import static org.semanticwb.bsc.PDFExportable.Mode_StreamPDF;
import org.semanticwb.bsc.Schedule;
import org.semanticwb.bsc.Seasonable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.BSCElement;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.utils.PropertiesComparator;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Muestra la interface de usuario con la que se capturan los criterios de
 * b&uacute;squeda de datos para los reportes, y la de los resultados
 * encontrados por la b&uacute;squeda, adem&aacute;s de tener el control sobre
 * la ejecuci&oacute;n para la obtenci&oacute;n de los datos
 *
 * @author jose.jimenez
 */
public class ReportGenerator extends GenericResource implements PDFExportable {

    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static final Logger log = SWBUtils.getLogger(ReportGenerator.class);

    /**
     * Presenta la interface de usuario para la captura de los criterios de
     * b&uacute;squeda de informaci&oacute;n
     *
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
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(256);
        SWBResourceURL url = paramRequest.getRenderUrl();
        Role actualUserRole = paramRequest.getUser().getRole();
        UserRepository repository = paramRequest.getWebPage().getWebSite().getUserRepository();
        url.setMode("report");

        output.append("  <form name=\"reportCrit\" id=\"reportCrit\" action=\"");
        output.append(url.toString());
        output.append("\" method=\"post\">\n");
        output.append("    <div class=\"\"><label for=\"elementType\">");
        output.append(paramRequest.getLocaleString("lbl_elementType"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"elementType\" id=\"elementType\" onchange=\"listTitles(this);\">\n");
        output.append("        <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_title_opt"));
        output.append("</option>\n");

        OntClass claseOnt = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getOntClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCElement");
        Iterator<OntClass> children = claseOnt.listSubClasses();
        HashMap<String, OntClass> childMap = new HashMap<String, OntClass>(8);
        List<String> elements = new ArrayList<String>();
        while (children != null && children.hasNext()) {
            OntClass child = children.next();
            if (child.getLocalName().equals("Agreement") || child.getLocalName().equals("Risk")) {
                continue;
            }
            String id = child.getLabel("es") != null ? child.getLabel("es") : child.getLocalName();
            childMap.put(id, child);
            elements.add(id);
        }
        if (!elements.isEmpty()) {
            java.util.Collections.sort(elements);
            for (int i = 0; i < elements.size(); i++) {
                String childName = elements.get(i);
                OntClass elementChild = childMap.get(childName);
                output.append("        <option value=\"");
                output.append(elementChild.getLocalName());
                output.append("\">");
                output.append(childName);
                output.append("</option>\n");
            }
        }

        String propertiesScript = this.getClassesProperties(childMap);
        output.append("      </select>\n");
        output.append("    </div>\n");
        output.append("    <div class=\"\"><label for=\"title\">");
        output.append(paramRequest.getLocaleString("lbl_title"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"title\" id=\"titleSelect\" onchange=\"listStatus(this);\">\n");
        output.append("        <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_title_opt"));
        output.append("</option>\n");
        output.append("      </select>\n");
        output.append("    </div>\n");

        Iterator<Period> periods = this.getAllowedPeriods(paramRequest).iterator();
        StringBuilder periodOpt = new StringBuilder(128);
        while (periods.hasNext()) {
            Period period = periods.next();
            periodOpt.append("        <option value=\"");
            periodOpt.append(period.getURI());
            periodOpt.append("\">");
            periodOpt.append(period.getTitle());
            periodOpt.append("</option>\n");
        }

        output.append("    <div class=\"\"><label for=\"initialPeriod\">");
        output.append(paramRequest.getLocaleString("lbl_initialPeriod"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"initialPeriod\" id=\"initialPeriod\">\n");
        output.append("        <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_noSelection"));
        output.append("</option>\n");
        output.append(periodOpt.toString());
        output.append("      </select>\n");
        output.append("    </div>\n");
        output.append("    <div class=\"\"><label for=\"finalPeriod\">");
        output.append(paramRequest.getLocaleString("lbl_finalPeriod"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"finalPeriod\" id=\"finalPeriod\">\n");
        output.append("        <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_noSelection"));
        output.append("</option>\n");
        output.append(periodOpt.toString());
        output.append("      </select>\n");
        output.append("    </div>\n");
        output.append("    <div class=\"\"><label for=\"status\">");
        output.append(paramRequest.getLocaleString("lbl_status"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"status\" id=\"status\">\n");
        output.append("        <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_noSelection"));
        output.append("</option>\n");
        output.append("      </select>\n");
        output.append("    </div>\n");
        output.append("    <div class=\"\"><label for=\"champion\">");
        output.append(paramRequest.getLocaleString("lbl_champion"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"champion\" id=\"champion\">\n");
        output.append("        <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_noSelection"));
        output.append("</option>\n");

        Iterator<User> champions = this.userListByRole(paramRequest.getLocaleString("usersRole2List"), repository);
        while (champions != null && champions.hasNext()) {
            User champion = champions.next();
            output.append("        <option value=\"");
            output.append(champion.getURI());
            output.append("\">");
            output.append(champion.getFullName());
            output.append("</option>\n");
        }

        output.append("      </select>\n");
        output.append("    </div>\n");

        //Para un rol en particular (Estratega) se debe permitir revisar a los Sponsor
        if (actualUserRole.getId().equalsIgnoreCase(paramRequest.getLocaleString("superUserRole"))) {
            output.append("    <div class=\"\"><label for=\"sponsor\">");
            output.append(paramRequest.getLocaleString("lbl_sponsor"));
            output.append("</label></div>\n");
            output.append("    <div class=\"\">\n");
            output.append("      <select name=\"sponsor\" id=\"sponsor\">\n");
            output.append("        <option value=\"\">");
            output.append(paramRequest.getLocaleString("lbl_noSelection"));
            output.append("</option>\n");

            Iterator<User> sponsors = this.userListByRole("Sponsor", repository);
            while (sponsors != null && sponsors.hasNext()) {
                User sponsor = sponsors.next();
                output.append("        <option value=\"");
                output.append(sponsor.getURI());
                output.append("\">");
                output.append(sponsor.getFullName());
                output.append("</option>\n");
            }

            output.append("      </select>\n");
            output.append("    </div>\n");
        }

        output.append("    <div class=\"\">");
        output.append(paramRequest.getLocaleString("lbl_relatedElements"));
        output.append("</div>\n");
        output.append("    <div class=\"\">\n");

        for (int i = 0; i < elements.size(); i++) {
            String childName = elements.get(i);
            OntClass elementChild = childMap.get(childName);
            output.append("        <input type=\"checkbox\" name=\"relElem");
            output.append(childName);
            output.append("\" id=\"relElem");
            output.append(childName);
            output.append("\" value=\"");
            output.append(elementChild.getURI());
            output.append("\"><label for=\"relElem");
            output.append(childName);
            output.append("\">");
            output.append(childName);
            output.append("</label>&nbsp;&nbsp;&nbsp;\n");
        }

        output.append("    </div>\n");
        output.append("    <div class=\"\"><label for=\"props2Show\">");
        output.append(paramRequest.getLocaleString("lbl_props2Show"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"props2Show\" id=\"props2Show\" size=\"10\" multiple=\"mutiple\" disabled=\"disabled\">\n");
        output.append("        <option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>\n");
        output.append("      </select>\n");
        output.append("    </div>\n");
        output.append("    <div class=\"\"><input type=\"button\" id=\"submitCrit\" value=\"");
        output.append(paramRequest.getLocaleString("lbl_submit"));
        output.append("\" onclick=\"evaluate2Submit(this.form);\"/></div>\n");
        output.append("  </form>\n");
        SWBResourceURL urlTitles = paramRequest.getRenderUrl();
        urlTitles.setMode("elementList");
        urlTitles.setCallMethod(SWBResourceURL.Call_DIRECT);
        output.append("  <script type=\"text/javascript\">\n");
        output.append(propertiesScript.toString());
        output.append("    function listTitles(elementSelect) {\n");
        output.append("      var titleSelect = document.getElementById(\"titleSelect\");\n");
        output.append("      var propsSelect = document.getElementById(\"props2Show\");\n");
        output.append("      cleanSelect(\"titleSelect\");\n");
        output.append("      cleanSelect(\"props2Show\");\n");
        output.append("      if (elementSelect.selectedIndex > 0) {\n");
        output.append("        var titleResponse = getJSON(\"");
        output.append(urlTitles.toString());
        output.append("?type=\" + elementSelect.value);\n");
        //Se llena el select de titulos con la respuesta de la peticion
        output.append("        populateSelect(titleSelect, titleResponse);\n");
        output.append("        for (var h = 0; h < props4Select.length; h++) {\n");
        output.append("          if (props4Select[h].objectType == elementSelect.value) {\n");
        output.append("            populateSelect(propsSelect, props4Select[h]);\n");
        output.append("            propsSelect.disabled = false;\n");
        output.append("          }\n");
        output.append("        }\n");
        output.append("      } else {\n");
        output.append("        var optionToAdd = new Option(\"");
        output.append(paramRequest.getLocaleString("lbl_title_opt"));
        output.append("\", \"\", false, false);\n");
        output.append("        titleSelect.options.add(optionToAdd);\n");
        output.append("        propsSelect.disabled = true;\n");
        output.append("      }\n");
        output.append("      disableRelated(elementSelect);\n");
        output.append("    }\n");
        SWBResourceURL urlStatus = paramRequest.getRenderUrl();
        urlStatus.setMode("statusList");
        urlStatus.setCallMethod(SWBResourceURL.Call_DIRECT);
        output.append("    function listStatus(fromSelect) {\n");
        output.append("      var statusSelect = document.getElementById(\"status\");\n");
        output.append("      cleanSelect(\"status\");\n");
        output.append("      if (fromSelect.selectedIndex > 0) {\n");

        output.append("        var statusResponse = getJSON(\"");
        output.append(urlStatus.toString());
        output.append("?element=\" + encodeURIComponent(fromSelect.value));\n");
        //Se llena el select de titulos con la respuesta de la peticion
        output.append("        populateSelect(statusSelect, statusResponse);\n");
        output.append("      \n");
        output.append("      } else {\n");
        output.append("        var optionToAdd = new Option(\"");
        output.append(paramRequest.getLocaleString("lbl_title_opt"));
        output.append("\", \"\", false, false);\n");
        output.append("        statusSelect.options.add(optionToAdd);\n");
        output.append("      }\n");

        output.append("    }\n");
        output.append("    function populateSelect(select2Feed, dataContainer) {\n");
        output.append("      if (dataContainer.data.length > 0) {\n");
        output.append("        for (var i = 0; i < dataContainer.data.length; i++) {\n");
        output.append("          var optionToAdd = new Option(dataContainer.data[i].label, dataContainer.data[i].value, false, false);\n");
        output.append("          select2Feed.options.add(optionToAdd);\n");
        output.append("        }\n");
        output.append("        if (select2Feed.name != \"props2Show\") {");
        output.append("          select2Feed.selectedIndex = 0;\n");
        output.append("        }\n");
        output.append("      }\n");
        output.append("    }\n");
        output.append("    function cleanSelect(elementId) {\n");
        output.append("      var select2Clear = document.getElementById(elementId);\n");
        output.append("      for (var j = select2Clear.options.length - 1; j >= 0; j--) {\n");
        output.append("        select2Clear.options.remove(j);\n");
        output.append("      }\n");
        output.append("    }\n");
        output.append("    function evaluate2Submit(form) {\n");
        output.append("      var passedValidation = true;\n");
        output.append("      if (form.elementType.selectedIndex < 1 && form.initialPeriod.selectedIndex < 1 && \n");
        output.append("          form.finalPeriod.selectedIndex < 1 && form.status.selectedIndex < 1 && \n");
        output.append("          form.champion.selectedIndex < 1) {\n");
        output.append("        alert(\"");
        output.append(paramRequest.getLocaleString("msg_noSelectedCriteria"));
        output.append("\");\n");
        output.append("        passedValidation = false;");
        output.append("      }\n");
        output.append("      if ((form.initialPeriod.selectedIndex < 1 && form.finalPeriod.selectedIndex > 0) ||\n");
        output.append("          (form.initialPeriod.selectedIndex > 0 && form.finalPeriod.selectedIndex < 1)) {\n");
        output.append("        alert(\"");
        output.append(paramRequest.getLocaleString("msg_noIntervalSelected"));
        output.append("\");\n");
        output.append("        passedValidation = false;");
        output.append("      }\n");
        output.append("      if ((form.initialPeriod.selectedIndex > 0 && form.finalPeriod.selectedIndex > 0) &&\n");
        output.append("          (form.initialPeriod.selectedIndex > form.finalPeriod.selectedIndex)) {\n");
        output.append("        alert(\"");
        output.append(paramRequest.getLocaleString("msg_periodsNotValid"));
        output.append("\");\n");
        output.append("        passedValidation = false;");
        output.append("      }\n");
        output.append("      if (passedValidation) {\n");
        output.append("        form.submit();\n");
        output.append("      } else {\n");
        output.append("        return;\n");
        output.append("      }\n");
        output.append("    }\n");
        output.append("    function disableRelated(elementSelect) {\n");
        output.append("      var forma = elementSelect.form;\n");
        output.append("      for (var i = 0; i < forma.elements.length; i++) {\n");
        output.append("        if (elementSelect.selectedIndex > 0) {\n");
        output.append("          if (forma.elements[i].type == \"checkbox\" && forma.elements[i].value.indexOf(elementSelect.value) != -1) {\n");
        output.append("            forma.elements[i].checked = false;\n");
        output.append("            forma.elements[i].disabled = true;\n");
        output.append("          } else {\n");
        output.append("            forma.elements[i].checked = false;\n");
        output.append("            forma.elements[i].disabled = false;\n");
        output.append("          }\n");
        output.append("        } else {\n");
        output.append("          if (forma.elements[i].type == \"checkbox\") {\n");
        output.append("            forma.elements[i].checked = false;\n");
        output.append("            forma.elements[i].disabled = true;\n");
        output.append("          }\n");
        output.append("        }\n");
        output.append("      }\n");
        output.append("        \n");
        output.append("        \n");
        output.append("        \n");
        output.append("    }\n");
        output.append("  </script>");
        out.println(output.toString());
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode != null && mode.equals("elementList")) {
            this.doElementList(request, response, paramRequest);
        } else if (mode != null && mode.equals("report")) {
            this.doReport(request, response, paramRequest);
        } else if (mode != null && mode.equals("statusList")) {
            this.doStatusList(request, response, paramRequest);
        } else if (mode != null && Mode_StreamPDF.equals(mode)) {
            this.doExportPDF(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Obtiene el listado de los elementos que pertenecen al tipo que se indica
     * en el par&aacute;metro del request con nombre {@code type}.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    public void doElementList(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder list = new StringBuilder(256);
        String type = request.getParameter("type");
        User user = paramRequest.getUser();
        int cont = 0;

        if (type != null) {
            String baseUri = "http://www.semanticwebbuilder.org/swb4/bsc#";
            SemanticClass objectBase = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(baseUri + type);
            if (objectBase != null && objectBase.getRootClass().equals(BSCElement.bsc_BSCElement)) {
                list.append("      {\n");
                list.append("        data: [\n");
                list.append("          { value: \"\", label: \"");
                list.append(paramRequest.getLocaleString("lbl_noSelection"));
                list.append("\"},\n");
                Iterator<SemanticObject> listing = objectBase.listInstances();
                while (listing != null && listing.hasNext()) {
                    SemanticObject element = listing.next().createGenericInstance().getSemanticObject();
                    if (element.getBooleanProperty(BSCElement.swb_active, false, false)
                            && user.haveAccess(element.createGenericInstance())) {
                        if (cont > 0) {
                            list.append(",\n");
                        }
                        list.append("          { value: \"");
                        list.append(element.getURI());
                        list.append("\", label: \"");
                        list.append(element.getDisplayName());
                        list.append("\" }");
                        cont++;
                    }
                }
                list.append("\n        ]");
                list.append("\n      }");
            }
        }

        out.println(list.toString());
    }

    public void doStatusList(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        ArrayList<State> states = new ArrayList<State>();
        StringBuilder list = new StringBuilder(256);
        String elementUri = request.getParameter("element");
        int cont = 0;
        SemanticObject object = SemanticObject.createSemanticObject(elementUri,
                paramRequest.getWebPage().getWebSite().getSemanticModel());

        if (object != null) {
            list.append("      {\n");
            list.append("        data: [\n");
            list.append("          { value: \"\", label: \"");
            list.append(paramRequest.getLocaleString("lbl_noSelection"));
            list.append("\"},\n");
            GenericObject generic = object.createGenericInstance();
            Iterator<State> iterator = null;
            if (generic instanceof Objective) {
                iterator = ((Objective) generic).listStates();
            } else if (generic instanceof Indicator) {
                iterator = ((Indicator) generic).getObjective().listStates();
            } else if (generic instanceof Initiative) {
                //iterator = ((Initiative) generic).listStates();
                if (((Initiative) generic).getStatusAssigned() != null) {
                    states.add(((Initiative) generic).getStatusAssigned());
                }
            } else if (generic instanceof Deliverable) {
//                if (((Deliverable) generic).getDeliverableAssignable() instanceof Initiative) {
                    Initiative ini = (Initiative) ((Deliverable) generic).getInitiative();
                    if (ini.getStatusAssigned() != null) {
                        states.add(ini.getStatusAssigned());
                    }
//                }
            }
            while (iterator != null && iterator.hasNext()) {
                State state = iterator.next();
                if (state != null && state.isValid()) {
                    states.add(state);
                }
            }
            Collections.sort(states);
            iterator = states.iterator();
            while (iterator != null && iterator.hasNext()) {
                State state = iterator.next();
                if (cont > 0) {
                    list.append(",\n");
                }
                list.append("          { value: \"");
                list.append(state.getURI());
                list.append("\", label: \"");
                list.append(state.getTitle());
                list.append("\" }");
                cont++;
            }
            list.append("\n        ]");
            list.append("\n      }");
        }
        out.println(list.toString());
    }

    /**
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    public void doReport(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(256);
        ReportCriteria criteria = this.createCriteria(request);
        ArrayList<SemanticObject> results = this.processReport(criteria, paramRequest);
        int itemsCount = 0;
        int titleIndex = -1;

        //Comentada para que en formelement.TextField se obtenga el Id desde el objeto semantico
        //request.setAttribute("websiteId", paramRequest.getWebPage().getWebSiteId());

        output.append("<div id=\"criteria\">");
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            Object object = en.nextElement();
            String value = request.getParameter(object + "");
            if (object != null && !object.toString().equals("props2Show")) {
                output.append(" <input type=\"hidden\" name=\"");
                output.append(object.toString());
                output.append("\" id=\"");
                output.append(object.toString());
                output.append("\" value=\"");
                output.append(value);
                output.append("\"/>");
            } else {
                String[] selectedProps = request.getParameterValues(object.toString());
                output.append("<select id=\"");
                output.append(object.toString());
                output.append("\" name=\"");
                output.append(object.toString());
                output.append("\" multiple style=\"display:none;visibility:hidden\"> ");//
                for (int i = 0; i < selectedProps.length; i++) {
                    output.append("<option value=\"");
                    output.append(selectedProps[i]);
                    output.append("\" selected></option>");
                }
                output.append("</select>");
            }
        }
        output.append("</div>");


        output.append("<table border=\"1\">\n");
        output.append("  <tr>\n");
        for (SemanticProperty prop : criteria.getProps2Show()) {
            output.append("    <th>");
            output.append(prop.getLabel());
            output.append("</th>\n");
            if (prop.getURI().indexOf("title") != -1 && titleIndex == -1) {
                titleIndex = itemsCount;
            }
            itemsCount++;
        }
        output.append("  </tr>\n");

        for (SemanticObject item : results) {
            GenericObject gralItem = item.createGenericInstance();
            boolean sameKind = false;

            if (gralItem.getURI().contains(criteria.getElementType())) {
                sameKind = true;
            }
            itemsCount = 0;
            output.append("  <tr>\n");
            for (SemanticProperty prop : criteria.getProps2Show()) {

                output.append("    <td>\n");
                if (sameKind) {
                    output.append(this.renderPropertyValue(request, item, prop.getURI(), paramRequest.getUser().getLanguage()));
                } else {
                    //de los objetos relacionados solo se va a mostrar el titulo en la tabla
                    if (itemsCount == titleIndex) {
                        output.append(this.renderPropertyValue(request, item, prop.getURI(), paramRequest.getUser().getLanguage()));
                    } else {
                        output.append("      &nbsp;\n");
                    }
                }
                output.append("    </td>\n");
                itemsCount++;
            }
            output.append("  </tr>\n");
        }
        if (results.isEmpty()) {
            output.append("  <tr><td colspan=\"");
            output.append(criteria.getProps2Show().size());
            output.append("\">");
            output.append(paramRequest.getLocaleString("msg_noResults"));
            output.append("</td></tr>\n");
        }
        output.append("</table>");
        out.print(output.toString());
    }

    /**
     * Obtiene el listado de per&iacute;odos a los que tiene acceso el usuario
     * en sesi&oacute;n
     *
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @return un objeto {@code List} que contiene los per&iacute;odos a los que
     * tiene acceso el usuario en sesi&oacute;n
     */
    private List<Period> getAllowedPeriods(SWBParamRequest paramRequest) {

        //Obtener el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();

        //Obtener listado de instancias de Periodos en el Scorecard
        Iterator<Period> allPeriods = Period.ClassMgr.listPeriods(currentBsc);
        List<Period> periods = SWBUtils.Collections.filterIterator(allPeriods, new GenericFilterRule<Period>() {
            @Override
            public boolean filter(Period s) {
                User user = SWBContext.getSessionUser(getResourceBase().getWebSite().getUserRepository().getId());
                return !s.isValid() || !user.haveAccess(s);
            }
        });
        Collections.sort(periods);

        return periods;
    }

    /**
     * Obtiene el conjunto de objetos {@code User} que tiene asignado como rol,
     * el inidicado en la invocaci&oacute;n
     *
     * @param roleName el identificador del rol, del cual se desea obtener los
     * usuarios asociados
     * @param model el modelo o sitio del cual se deben extraer los usuarios
     * @return el conjunto de objetos {@code User} que tiene asignado el rol
     * especificado
     */
    private Iterator<User> userListByRole(String roleName, UserRepository model) {

        Role role = Role.ClassMgr.getRole(roleName, model);
        Iterator<User> users = null;
        if (role != null) {
            users = User.ClassMgr.listUserByRole(role, model);
            if (users != null) {
                users = SWBComparator.sortSemanticObjects(users);
            }
        }
        return users;
    }

    /**
     * Genera un {@code String} que representa un arreglo de objetos en
     * JavaScript con los valores y etiquetas de las propiedades de los objetos
     * indicados en {@code children} para representarlos en objetos de forma de
     * HTML.
     *
     * @param children contiene el conjunto de objetos sem&aacute;nticos de los
     * que se quiere obtener sus propiedades.
     * @return un {@code String} que representa un arreglo de objetos en
     * JavaScript. Cada objeto contiene dos atributos: objectType (el nombre de
     * la clase semantica correspondiente) y data (un arreglo de objetos
     * an&oacute;nimos con las propiedades: value -uri de una propiedad
     * sem&aacute;ntica y label -nombre a desplegar de la propiedad)
     */
    private String getClassesProperties(HashMap<String, OntClass> children) {

        StringBuilder script = new StringBuilder(512);
        int countClassses = 0;
        //script.append("<script type=\"text/javascript\">");
        if (children != null && !children.isEmpty()) {
            Iterator<String> classNames = children.keySet().iterator();
            script.append("  var props4Select = [\n");
            while (classNames.hasNext()) {
                String childName = classNames.next();
                OntClass ontologyChild = children.get(childName);
                SemanticClass semanticChild = SWBPlatform.getSemanticMgr().getSchema().getSemanticObject(
                        ontologyChild.getURI()).transformToSemanticClass();
                ArrayList<SemanticProperty> propsList = (ArrayList<SemanticProperty>) SWBUtils.Collections.copyIterator(semanticChild.listProperties());
                Collections.sort(propsList, new PropertiesComparator());
                Iterator<SemanticProperty> properties = propsList.iterator();
                if (countClassses > 0) {
                    script.append(",\n");
                }
                script.append("    {\n");
                script.append("      objectType: \"");
                script.append(ontologyChild.getLocalName());
                script.append("\",\n");
                script.append("      data: [\n");
                int cont = 0;
                while (properties.hasNext()) {
                    SemanticProperty prop = properties.next();
                    if ((prop.getDisplayProperty() != null || this.displayElementExists(prop))) {
//                        script.append("  ");
                        if (cont > 0) {
                            script.append(",\n");
                        }
                        script.append("        { value: \"");
                        script.append(prop.getURI());
                        script.append("\", label: \"");
                        script.append(prop.getDisplayName());
                        script.append("\" }");
                        cont++;
                    }
                }
                script.append("\n      ]");
                script.append("\n    }");
                countClassses++;
            }
            script.append("\n  ];\n");
        }
        //script.append("<script>\n");
        return script.toString();
    }

    /**
     * Verifica la existencia de un valor para el displayElement de una
     * propiedad en la ontolog&iacute;a
     *
     * @param property la propiedad sem&aacute;ntica de la que se requiere el
     * displayElement
     * @return {@literal true} si existe un valor para el displayElement en la
     * ontolog&iacute;a, {@literal false} de lo contrario
     */
    private boolean displayElementExists(SemanticProperty property) {

        boolean exists = false;

        Statement st = property.getRDFProperty().getProperty(
                SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(
                "http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
        if (st != null) {
            exists = true;
        }
        return exists;
    }

    /**
     * Crea la instancia de {@code ReportCriteria} con la que se generar&aacute;
     * el reporte solicitado en el {@code request}
     *
     * @param request petici&oacute;n del cliente con los criterios para generar
     * el reporte
     * @return la instancia de {@code ReportCriteria} con los criterios para
     * generar el reporte
     */
    private ReportCriteria createCriteria(HttpServletRequest request) {

        ReportCriteria criteria = new ReportCriteria();
        ArrayList<String> related = new ArrayList<String>(4);
        ArrayList<SemanticProperty> properties = new ArrayList<SemanticProperty>(16);

        Enumeration parameters = request.getParameterNames();
        while (parameters != null && parameters.hasMoreElements()) {
            String parameterName = (String) parameters.nextElement();
            if (parameterName.equals("elementType") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                criteria.setElementType(request.getParameter(parameterName));
            }
            if (parameterName.equals("title") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                criteria.setObjectTitle(request.getParameter(parameterName));
            }
            if (parameterName.startsWith("relElem") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                related.add(request.getParameter(parameterName));
            }
            if (parameterName.equals("initialPeriod") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject periodSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject periodGen = periodSem.createGenericInstance();
                if (periodGen instanceof Period) {
                    criteria.setInitialPeriod((Period) periodGen);
                }
            }
            if (parameterName.equals("finalPeriod") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject periodSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject periodGen = periodSem.createGenericInstance();
                if (periodGen instanceof Period) {
                    criteria.setFinalPeriod((Period) periodGen);
                }
            }
            if (parameterName.equals("status") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject statusSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject statusGen = statusSem.createGenericInstance();
                if (statusGen instanceof State) {
                    criteria.setStatus((State) statusGen);
                }
            }
            if (parameterName.equals("champion") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject championSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject championGen = championSem.createGenericInstance();
                if (championGen instanceof User) {
                    criteria.setChampion((User) championGen);
                }
            }
            if (parameterName.equals("sponsor") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject sponsorSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject sponsorGen = sponsorSem.createGenericInstance();
                if (sponsorGen instanceof User) {
                    criteria.setSponsor((User) sponsorGen);
                }
            }
            if (parameterName.equals("props2Show") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {

                String[] selectedProps = request.getParameterValues(parameterName);
                SemanticOntology ontology = SWBPlatform.getSemanticMgr().getOntology();
                for (String property : selectedProps) {
                    SemanticObject propSO = SemanticObject.createSemanticObject(property);
                    SemanticProperty prop = ontology.getSemanticProperty(property);
                    if (prop == null) {
                        prop = propSO.transformToSemanticProperty();
                    }
                    properties.add(prop);
                }
            }
            criteria.setProps2Show(properties);
        }
        if (!related.isEmpty()) {
            criteria.setRelatedElements(related);
        }

        if (criteria.getProps2Show().isEmpty() || !criteria.getProps2Show().contains(Descriptiveable.swb_title)) {
            criteria.getProps2Show().add(0, Descriptiveable.swb_title);
        }
        return criteria;
    }

    /**
     * Procesa los datos disponibles en el sistema para obtener el conjunto de
     * datos a presentar en el reporte.
     *
     * @param criteria contiene los criterios que deben cumplir los datos a
     * mostrar en el reporte
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @return el conjunto de elementos del BSC que coinciden con los criterios
     * de busqueda seleccionados por el usuario
     */
    private ArrayList<SemanticObject> processReport(ReportCriteria criteria, SWBParamRequest paramRequest) {

        SWBModel model = paramRequest.getWebPage().getWebSite();
        Iterator initialSet = null;
        User user = paramRequest.getUser();

        if (criteria.getElementType() != null) {
            ArrayList<SemanticObject> forNow = new ArrayList<SemanticObject>(128);
            String id = null;
            //se extrae el identificador del uri recibido
            if (criteria.getObjectTitle() != null && criteria.getObjectTitle().lastIndexOf(":") != -1) {
                id = criteria.getObjectTitle().substring(criteria.getObjectTitle().lastIndexOf(":") + 1);
            }
            if (criteria.getElementType().equals(Objective.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Objective element = Objective.ClassMgr.getObjective(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        ReportGenerator.log.error("Objective con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Objective> firstSet = Objective.ClassMgr.listObjectives(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Objective obj = firstSet.next();
                        if (obj.isValid() && user.haveAccess(obj)) {
                            forNow.add(obj.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            } else if (criteria.getElementType().equals(Indicator.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Indicator element = Indicator.ClassMgr.getIndicator(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        ReportGenerator.log.error("Indicator con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Indicator> firstSet = Indicator.ClassMgr.listIndicators(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Indicator indi = firstSet.next();
                        if (indi.isValid() && user.haveAccess(indi)) {
                            forNow.add(indi.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            } else if (criteria.getElementType().equals(Initiative.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Initiative element = Initiative.ClassMgr.getInitiative(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        ReportGenerator.log.error("Initiative con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Initiative> firstSet = Initiative.ClassMgr.listInitiatives(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Initiative ini = firstSet.next();
                        if (ini.isValid() && user.haveAccess(ini)) {
                            forNow.add(ini.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            } else if (criteria.getElementType().equals(Deliverable.sclass.getName())) {
                if (criteria.getObjectTitle() != null) {
                    Deliverable element = Deliverable.ClassMgr.getDeliverable(id, model);
                    if (element != null && element.isValid() && user.haveAccess(element)) {
                        forNow.add(element.getSemanticObject());
                    } else if (element == null) {
                        ReportGenerator.log.error("Deliverable con id: " + id + " --No encontrado");
                    }
                    initialSet = forNow.iterator();
                } else {
                    Iterator<Deliverable> firstSet = Deliverable.ClassMgr.listDeliverables(model);
                    while (firstSet != null && firstSet.hasNext()) {
                        Deliverable deli = firstSet.next();
                        if (deli.isValid() && user.haveAccess(deli)) {
                            forNow.add(deli.getSemanticObject());
                        }
                    }
                    initialSet = forNow.iterator();
                }
            }
        } else {
            ArrayList<SemanticObject> allTypes = new ArrayList<SemanticObject>(256);
            Iterator<Objective> objectives = Objective.ClassMgr.listObjectives(model);
            Iterator<Indicator> indicators = Indicator.ClassMgr.listIndicators(model);
            Iterator<Initiative> initiatives = Initiative.ClassMgr.listInitiatives(model);
            Iterator<Deliverable> deliverables = Deliverable.ClassMgr.listDeliverables(model);
            while (objectives != null && objectives.hasNext()) {
                Objective obj = objectives.next();
                if (obj != null && obj.isValid() && user.haveAccess(obj)) {
                    allTypes.add(obj.getSemanticObject());
                }
            }
            while (indicators != null && indicators.hasNext()) {
                Indicator indi = indicators.next();
                if (indi != null && indi.isValid() && user.haveAccess(indi)) {
                    allTypes.add(indi.getSemanticObject());
                }
            }
            while (initiatives != null && initiatives.hasNext()) {
                Initiative ini = initiatives.next();
                if (ini != null && ini.isValid() && user.haveAccess(ini)) {
                    allTypes.add((ini.getSemanticObject()));
                }
            }
            while (deliverables != null && deliverables.hasNext()) {
                Deliverable deli = deliverables.next();
                if (deli != null && deli.isValid() && user.haveAccess(deli)) {
                    allTypes.add(deli.getSemanticObject());
                }
            }
            initialSet = allTypes.iterator();
        }

        ArrayList<SemanticObject> processed = new ArrayList<SemanticObject>(256);
        int count = 0;

        //el conjunto inicial de objetos, se filtra con los criterios seleccionados por el usuario
        while (initialSet != null && initialSet.hasNext()) {
            GenericObject inTurn = ((SemanticObject) initialSet.next()).createGenericInstance();
            boolean mustBeAdded = false;

            //evaluacion de periodos; si se seleccionaron
            if (criteria.getInitialPeriod() != null && criteria.getFinalPeriod() != null) {
                if (inTurn instanceof Seasonable) {
                    //Sin seleccion de estatus
                    if (criteria.getStatus() == null) {
                        Seasonable seasonable = (Seasonable) inTurn;
                        if (seasonable.hasPeriod(criteria.getInitialPeriod()) && seasonable.hasPeriod(criteria.getFinalPeriod())) {
                            mustBeAdded = true;
                        }
                    } else {
                        //el estatus seleccionado, debe estar asignado en el intervalo indicado
                        Period thisPeriod = criteria.getInitialPeriod();
                        boolean intervalFinished = false;
                        boolean isObjective = false;
                        PeriodStatus periodStatus = null;
                        Objective objective = null;
                        Indicator indicator = null;

                        if (inTurn instanceof Objective) {
                            objective = (Objective) inTurn;
                            isObjective = true;
                        } else if (inTurn instanceof Indicator) {
                            indicator = (Indicator) inTurn;
                        }

                        while (!intervalFinished) {
                            if (isObjective) {
                                periodStatus = objective.getPeriodStatus(thisPeriod);
                            } else {
                                Measure measure = indicator != null && indicator.getStar() != null
                                        ? indicator.getStar().getMeasure(thisPeriod) : null;
                                if (measure != null && measure.getEvaluation() != null) {
                                    periodStatus = measure.getEvaluation();
                                }
                            }
                            //Si los estados de criterios y del periodo evaluado son iguales, se debe agregar el elemento
                            if (periodStatus != null && periodStatus.getStatus().equals(criteria.getStatus())) {
                                mustBeAdded = true;
                                break;
                            }
                            //Se obtiene el siguiente periodo del intervalo definido por el usuario
                            if (thisPeriod.equals(criteria.getFinalPeriod())) {
                                intervalFinished = true;
                            } else if (thisPeriod.getNext() != null) {
                                thisPeriod = (Period) thisPeriod.getNext();
                            } else {
                                //si el siguiente periodo no esta definido, se rompe el ciclo
                                break;
                            }
                        }
                    }
                } else if (inTurn instanceof Schedule) {
                    Schedule schedule = (Schedule) inTurn;
                    Date initial2Compare = schedule.getActualStart() != null ? schedule.getActualStart() : schedule.getPlannedStart();
                    Date final2Compare = schedule.getActualEnd() != null ? schedule.getActualEnd() : schedule.getPlannedEnd();
                    if (initial2Compare != null && final2Compare != null && criteria.getInitialPeriod().getStart().before(initial2Compare)
                            && initial2Compare.before(criteria.getFinalPeriod().getEnd()) && criteria.getInitialPeriod().getStart().before(final2Compare)
                            && final2Compare.before(criteria.getFinalPeriod().getEnd())) {
                        //Sin seleccion de estatus
                        if (criteria.getStatus() == null) {
                            mustBeAdded = true;
                        } else {
                            //el estatus debe estar asignado al entregable
                            if (inTurn instanceof Deliverable) {
                                Deliverable deli = (Deliverable) inTurn;
                                if ((deli.getAutoStatus() != null && deli.getAutoStatus().equals(criteria.getStatus()))
                                        || (deli.getStatusAssigned() != null && deli.getStatusAssigned().equals(criteria.getStatus()))) {
                                    mustBeAdded = true;
                                }
                            }
                        }
                    }
                }
            } else {
                //si los periodos no se indicaron, no es filtro de seleccion
                mustBeAdded = true;
            }
            //Se evalua filtro de Champion
            if (criteria.getChampion() != null && mustBeAdded) {
                mustBeAdded = false;
                if (inTurn instanceof Indicator) {
                    if (((Indicator) inTurn).getChampion().equals(criteria.getChampion())) {
                        mustBeAdded = true;
                    }
                } else {
                    if (inTurn instanceof Objective) {
                        Iterator<Indicator> indicators = ((Objective) inTurn).listIndicators();
                        while (indicators != null && indicators.hasNext()) {
                            if (indicators.next().getChampion().equals(criteria.getChampion())) {
                                mustBeAdded = true;
                                break;
                            }
                        }
                    }
                    //para iniciativas y entregables no se consideran relaciones indirectas para obtener el champion
                }
            } else if (criteria.getChampion() == null && mustBeAdded) {
                mustBeAdded = true;
            }
            //se evalua filtro de Sponsor
            if (criteria.getSponsor() != null && mustBeAdded) {
                mustBeAdded = false;
                if (inTurn instanceof Objective) {
                    if (((Objective) inTurn).getSponsor().equals(criteria.getSponsor())) {
                        mustBeAdded = true;
                    }
                } else {
                    if (inTurn instanceof Indicator) {
                        if (((Indicator) inTurn).getObjective().getSponsor().equals(criteria.getSponsor())) {
                            mustBeAdded = true;
                        }
                    } else if (inTurn instanceof Initiative) {
                        if (((Initiative) inTurn).getInitiativeAssignable() != null
                                && ((Initiative) inTurn).getInitiativeAssignable() instanceof Indicator) {
                            Indicator indi = (Indicator) ((Initiative) inTurn).getInitiativeAssignable();
                            if (indi.getObjective().getSponsor().equals(criteria.getSponsor())) {
                                mustBeAdded = true;
                            }
                        }
                    } else if (inTurn instanceof Deliverable) {
                        Deliverable deli = (Deliverable) inTurn;
//                        if (deli.getDeliverableAssignable() != null && deli.getDeliverableAssignable() instanceof Initiative) {
                            Initiative ini = (Initiative) deli.getInitiative();
                            if (ini.getInitiativeAssignable() != null
                                    && ini.getInitiativeAssignable() instanceof Indicator) {
                                Indicator indi = (Indicator) ini.getInitiativeAssignable();
                                if (indi.getObjective().getSponsor().equals(criteria.getSponsor())) {
                                    mustBeAdded = true;
                                }
                            }
//                        }
                    }
                }
            } else if (criteria.getSponsor() == null && mustBeAdded) {
                mustBeAdded = true;
            }

            if (mustBeAdded) {
                processed.add(inTurn.getSemanticObject());
                if (criteria.getRelatedElements() != null) {
                    addRelated(processed, inTurn, criteria.getRelatedElements());
                }
            }
            count++;
        }

        return processed;
    }

    /**
     * Agrega a la coleccion {@code resultSet} los objetos relacionados al
     * elemento {@code element} de los tipos especificados en
     * {@code relatedTypes}
     *
     * @param resultSet la coleccion que contiene el total de elementos a
     * devolver en el reporte
     * @param element el elemento del BSC del que se obtendran los elementos
     * relacionados
     * @param relatedTypes los tipos de objeto que se deben agregar al conjunto
     * de resultados
     */
    private void addRelated(ArrayList<SemanticObject> resultSet, GenericObject element, ArrayList<String> relatedTypes) {

        ArrayList<SemanticObject> additional = new ArrayList<SemanticObject>(64);
        if (element instanceof Objective) {
            Objective obj = (Objective) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Indicator.bsc_Indicator.getName())
                        || type.endsWith(Initiative.bsc_Initiative.getName())
                        || type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    Iterator<Indicator> it = obj.listIndicators();
                    while (it != null && it.hasNext()) {
                        Indicator indi = it.next();
                        if (type.endsWith(Indicator.bsc_Indicator.getName())) {
                            additional.add(indi.getSemanticObject());
                        } else { //si se pidieron las iniciativas o entregables
                            Iterator<Initiative> itInitiatives = indi.listInitiatives();
                            while (itInitiatives != null && itInitiatives.hasNext()) {
                                Initiative initiative = itInitiatives.next();
                                if (type.endsWith(Initiative.bsc_Initiative.getName())) {
                                    additional.add(initiative.getSemanticObject());
                                } else {//si se pidieron los entregables
                                    Iterator<Deliverable> itDeliverables = initiative.listDeliverables();
                                    while (itDeliverables != null && itDeliverables.hasNext()) {
                                        additional.add(itDeliverables.next().getSemanticObject());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (element instanceof Indicator) {
            Indicator indi = (Indicator) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Objective.bsc_Objective.getName())) {
                    additional.add(indi.getObjective().getSemanticObject());
                } else if (type.endsWith(Initiative.bsc_Initiative.getName())) {
                    //Se obtiene el listado de iniciativas
                    Iterator<Initiative> it = indi.listInitiatives();
                    while (it != null && it.hasNext()) {
                        additional.add(it.next().getSemanticObject());
                    }
                } else if (type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    //Se obtiene el listado de iniciativas
                    Iterator<Initiative> it = indi.listInitiatives();
                    while (it != null && it.hasNext()) {
                        Initiative ini = it.next();
                        //Se obtiene el listado de entregables de cada iniciativa
                        Iterator<Deliverable> itDeli = ini.listDeliverables();
                        while (itDeli != null && itDeli.hasNext()) {
                            additional.add(itDeli.next().getSemanticObject());
                        }
                    }
                }
            }
        } else if (element instanceof Initiative) {
            Initiative ini = (Initiative) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Objective.bsc_Objective.getName())
                        && ini.getInitiativeAssignable() != null && ini.getInitiativeAssignable() instanceof Indicator) {
                    additional.add(((Indicator) ini.getInitiativeAssignable()).getObjective().getSemanticObject());
                } else if (type.endsWith(Indicator.bsc_Indicator.getName())
                        && ini.getInitiativeAssignable() != null && ini.getInitiativeAssignable() instanceof Indicator) {
                    additional.add(((Indicator) ini.getInitiativeAssignable()).getSemanticObject());
                } else if (type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    Iterator<Deliverable> it = ini.listDeliverables();
                    while (it != null && it.hasNext()) {
                        additional.add(it.next().getSemanticObject());
                    }
                }
            }
        } else if (element instanceof Deliverable) {
            Deliverable deli = (Deliverable) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Objective.bsc_Objective.getName())) {
//                    if (deli.getDeliverableAssignable() != null && deli.getDeliverableAssignable() instanceof Initiative) {
                        //Se hace la comparacion debido a la creacion de la interface DeliverableAssignable
                        Initiative ini = (Initiative) deli.getInitiative();
                        if (ini.getInitiativeAssignable() != null
                                && ini.getInitiativeAssignable() instanceof Indicator) {
                            //se obtiene el objetivo del entregable a partir de la iniciativa
                            Indicator indi = (Indicator) ini.getInitiativeAssignable();
                            additional.add(indi.getObjective().getSemanticObject());
                        }
//                    }
                } else if (type.endsWith(Indicator.bsc_Indicator.getName())) {
//                    if (deli.getDeliverableAssignable() != null && deli.getDeliverableAssignable() instanceof Initiative) {
                        Initiative ini = (Initiative) deli.getInitiative();
                        if (ini.getInitiativeAssignable() != null
                                && ini.getInitiativeAssignable() instanceof Indicator) {
                            //se obtiene el indicador a partir de la iniciativa
                            additional.add(((Indicator) ini.getInitiativeAssignable()).getSemanticObject());
                        }
//                    }
                } else if (type.endsWith(Initiative.bsc_Initiative.getName()) && deli.getInitiative()!= null) {
                    additional.add(deli.getInitiative().getSemanticObject());
                }
            }
        }
        //se agregan los elementos seleccionados a resultSet
        for (SemanticObject addition : additional) {
            resultSet.add(addition);
        }
    }

    /**
     * Devuelve el despliegue correspondiente al valor de la propiedad
     * especificada, del objeto indicado.
     *
     * @param request petici&oacute;n HTTP realizada por el cliente
     * @param elementBSC representa el objeto del cual se desea extraer la
     * informaci&oacute;n
     * @param propUri representa la uri de la propiedad semantica de la que se
     * desea obtener su valor
     * @param lang representa el lenguaje en que se desea mostrar el valor de la
     * propiedad indicada
     * @return el despliegue del valor almacenado para la propiedad indicada
     */
    private String renderPropertyValue(HttpServletRequest request, SemanticObject elementBSC,
            String propUri, String lang) {
        request.setAttribute("suri", elementBSC.getEncodedURI());
        String ret = null;
        SWBFormMgr formMgr = new SWBFormMgr(elementBSC, null, SWBFormMgr.MODE_VIEW);
        SemanticProperty semProp = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propUri);

        //Codigo para obtener el displayElement
        Statement st = semProp.getRDFProperty().getProperty(
                SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(
                "http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
        if (st != null) {
            //Se obtiene: SemanticProperty: displayElement de la propiedad en cuestion (prop)
            SemanticObject soDisplayElement = SemanticObject.createSemanticObject(st.getResource());
            if (soDisplayElement != null) {
                SemanticObject formElement = soDisplayElement.getObjectProperty(
                        org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(
                        "http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement"));
                if (formElement != null) {
                    FormElement fe = (FormElement) formElement.createGenericInstance();

                    if (fe != null) {
                        if (formMgr.getSemanticObject() != null) {
                            fe.setModel(formMgr.getSemanticObject().getModel());
                        }
                        request.setAttribute("pdf", "true");
                        ret = fe.renderElement(request, elementBSC, semProp, semProp.getName(),
                                SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
                    }
                }
            }
        }

        if (ret == null) {
            FormElement formElement = formMgr.getFormElement(semProp);
            if (formElement != null) {
                ret = formElement.renderElement(request, elementBSC, semProp, semProp.getName(),
                        SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
            }
        }
        return ret != null ? ret : "";
    }

    /**
     * Ejecutar&aacute; la exportaci&oacute;n a PDF del elemento actual.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException SWBResourceException SWBResourceException
     * Excepti&oacute;n utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    public void doExportPDF(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String title = paramRequest.getLocaleString("lbl_titleReport");
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + title + ".pdf\"");
        StringBuilder sb = getHtml(request, paramRequest);
        if (sb != null && sb.length() > 0) {
            OutputStream os = response.getOutputStream();
            try {
                ITextRenderer renderer = new ITextRenderer();
                String sbStr = replaceHtml(sb);
                renderer.setDocumentFromString(sbStr);
                renderer.layout();
                renderer.createPDF(os);
                renderer.finishPDF();
            } catch (DocumentException ex) {
                log.error("Error in: " + ex);
            } catch (Exception ex) {
                log.error("Check that your style sheet corresponding to the CSS 2.1 specification: "
                        + ex);
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) { /*ignore*/ }
                }
            }
        }
    }

    /**
     * Reemplaza c&oacute;digo HTML por acentos, esto es para la estructura XML
     * requerida.
     *
     * @param sb el objeto String al cu&aacute; ser&aacute; reemplazados los
     * car&aacutecteres.
     * @return el objeto String modificado
     */
    private String replaceHtml(StringBuilder sb) {
        String sbStr = SWBUtils.TEXT.replaceAll(sb.toString(), "&oacute;", "ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&aacute;", "á");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&eacute;", "é");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&iacute;", "í");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&oacute;", "ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&uacute;", "ú");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&Aacute;", "Á");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&Eacute;", "É");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&Iacute;", "Í");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&Oacute;", "Ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&Uacute;", "Ú");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&nbsp;", " ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&lt;", "<");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&gt;", ">");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&amp;", "&");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&quot;", "\"");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&iexcl;", "¡");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&iquest;", "¿");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&reg;", "®");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&copy;", "©");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&euro;", "€");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&ntilde;", "ñ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&uuml", "ü");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&Ntilde;", "Ñ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr.toString(), "&Uuml;", "Ü");
        return sbStr;
    }

    /**
     * Crea la instancia de {@code ReportCriteria} con la que se generar&aacute;
     * el PDF para el reporte solicitado en el {@code request}
     *
     * @param request petici&oacute;n del cliente con los criterios para generar
     * el reporte
     * @return la instancia de {@code ReportCriteria} con los criterios para
     * generar el reporte
     */
    private ReportCriteria createCriteria2(HttpServletRequest request) {
        ReportCriteria criteria = new ReportCriteria();
        ArrayList<String> related = new ArrayList<String>(4);
        ArrayList<SemanticProperty> properties = new ArrayList<SemanticProperty>(16);

        Enumeration parameters = request.getParameterNames();
        while (parameters != null && parameters.hasMoreElements()) {
            String parameterName = (String) parameters.nextElement();
            if (parameterName.equals("elementType_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                criteria.setElementType(request.getParameter(parameterName));
            }
            if (parameterName.equals("titleSelect_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                criteria.setObjectTitle(request.getParameter(parameterName));
            }
            if (parameterName.startsWith("relElem") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                related.add(request.getParameter(parameterName));
            }
            if (parameterName.equals("initialPeriod_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject periodSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject periodGen = periodSem.createGenericInstance();
                if (periodGen instanceof Period) {
                    criteria.setInitialPeriod((Period) periodGen);
                }
            }
            if (parameterName.equals("finalPeriod_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject periodSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject periodGen = periodSem.createGenericInstance();
                if (periodGen instanceof Period) {
                    criteria.setFinalPeriod((Period) periodGen);
                }
            }
            if (parameterName.equals("status_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject statusSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject statusGen = statusSem.createGenericInstance();
                if (statusGen instanceof State) {
                    criteria.setStatus((State) statusGen);
                }
            }
            if (parameterName.equals("champion_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject championSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject championGen = championSem.createGenericInstance();
                if (championGen instanceof User) {
                    criteria.setChampion((User) championGen);
                }
            }
            if (parameterName.equals("sponsor_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {
                SemanticObject sponsorSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject sponsorGen = sponsorSem.createGenericInstance();
                if (sponsorGen instanceof User) {
                    criteria.setSponsor((User) sponsorGen);
                }
            }
            if (parameterName.equals("elProps2Show_") && request.getParameter(parameterName) != null
                    && !request.getParameter(parameterName).isEmpty()) {

                String[] selectedProps = request.getParameterValues(parameterName);
                SemanticOntology ontology = SWBPlatform.getSemanticMgr().getOntology();
                for (String property : selectedProps) {
                    SemanticObject propSO = SemanticObject.createSemanticObject(property);
                    SemanticProperty prop = ontology.getSemanticProperty(property);
                    if (prop == null) {
                        prop = propSO.transformToSemanticProperty();
                    }
                    properties.add(prop);
                }
            }
            criteria.setProps2Show(properties);
        }
        if (!related.isEmpty()) {
            criteria.setRelatedElements(related);
        }

        if (criteria.getProps2Show().isEmpty() || !criteria.getProps2Show().contains(Descriptiveable.swb_title)) {
            criteria.getProps2Show().add(0, Descriptiveable.swb_title);
        }
        return criteria;
    }

    /**
     * Filtra los recursos de tipo ComponentExportable que ser&aacute;n
     * utilizados en la exportaci&oacute;n del PDF y obtiene su c&oacute;digo
     * HTML.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con los
     * datos a exportar(Vista detalle y componentes adicionales)
     * @throws SWBResourceException SWBResourceException SWBResourceException
     * Excepti&oacute;n utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    private StringBuilder getHtml(HttpServletRequest request,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuilder output = new StringBuilder(256);
        ReportCriteria criteria = this.createCriteria2(request);
        ArrayList<SemanticObject> results = this.processReport(criteria, paramRequest);
        int itemsCount = 0;
        int titleIndex = -1;

        output.append("<html>");
        output.append("<head>");
        output.append("<style type=\"text/css\">");
        int sizePdf = getSize(criteria.getProps2Show().size());
        output.append("      @page{ size:");
        output.append(sizePdf);
        output.append(".0cm 20.0cm; }\">");
        output.append("</style>");

        output.append(getLinks(paramRequest, request));
        output.append("</head>");
        output.append("<body>");


        //Comentada para que en formelement.TextField se obtenga el Id desde el objeto semantico
        //request.setAttribute("websiteId", paramRequest.getWebPage().getWebSiteId());
        WebSite ws = paramRequest.getWebPage().getWebSite();
        String lang = paramRequest.getUser().getLanguage();
        String modelSite = ws.getTitle(lang) == null ? ws.getTitle() : ws.getTitle(lang);
        Date date = new Date();
        output.append("<div class=\"headerPDF\" style=\"width:50%;float:left;\">");
        output.append(modelSite);
        output.append("</div>");
        output.append("<div class=\"headerPDF\" style=\"width:50%;float:rigth;\">");
        output.append(paramRequest.getLocaleString("lblDateGeneration"));
        output.append(SWBUtils.TEXT.getStrDate(date, "es", "dd/mm/yyyy"));
        output.append("</div>");
        output.append("<br/>");
        output.append("<br/>");
        output.append("<table border=\"1\" style=\"width:100%\" >\n");
        output.append("  <tr>\n");
        for (SemanticProperty prop : criteria.getProps2Show()) {
            output.append("    <th>");
            output.append(prop.getLabel());
            output.append("</th>\n");
            if (prop.getURI().indexOf("title") != -1 && titleIndex == -1) {
                titleIndex = itemsCount;
            }
            itemsCount++;
        }
        output.append("  </tr>\n");

        for (SemanticObject item : results) {
            GenericObject gralItem = item.createGenericInstance();
            boolean sameKind = false;

            if (gralItem.getURI().contains(criteria.getElementType())) {
                sameKind = true;
            }
            itemsCount = 0;
            output.append("  <tr>\n");
            for (SemanticProperty prop : criteria.getProps2Show()) {
                output.append("    <td>\n");
                // Valida si la propiedad es boleana no se renderea el elemento (debido a que no son soportados los elementos de checkbox en la generacion de PDF's).
                // Se envia una etiqueta SI / NO dependiendo del valor boleano
                if (prop.isBoolean()) {
                    boolean value = item.getBooleanProperty(prop);
                    if (value) {
                        output.append(paramRequest.getLocaleString("lbl_true"));
                    } else {
                        output.append(paramRequest.getLocaleString("lbl_false"));
                    }
                } else {
                    if (sameKind) {
                        output.append(this.renderPropertyValue(request, item, prop.getURI(), paramRequest.getUser().getLanguage()));
                    } else {
                        //de los objetos relacionados solo se va a mostrar el titulo en la tabla
                        if (itemsCount == titleIndex) {
                            output.append(this.renderPropertyValue(request, item, prop.getURI(), paramRequest.getUser().getLanguage()));
                        } else {
                            output.append("      &nbsp;\n");
                        }
                    }
                }
                output.append("    </td>\n");
                itemsCount++;
            }
            output.append("  </tr>\n");
        }
        if (results.isEmpty()) {
            output.append("  <tr><td colspan=\"");
            output.append(criteria.getProps2Show().size());
            output.append("\">");
            output.append(paramRequest.getLocaleString("msg_noResults"));
            output.append("</td></tr>\n");
        }
        output.append("</table>");
        output.append("</body>");
        output.append("</html>");
        return output;
    }

    /**
     * Se encarga de obtener los links de la plantilla de la p&aacute;gina
     * actual
     *
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
        String filePath = template.getWorkPath() + "/"
                + template.getActualVersion().getVersionNumber() + "/"
                + template.getFileName(template.getActualVersion().getVersionNumber());
        FileReader reader = null;
        StringBuilder view = new StringBuilder(256);
        reader = new FileReader(filePath);

        String port = "";
        if (request.getServerPort() != 80) {
            port = ":" + request.getServerPort();
        }
        String baserequest = request.getScheme() + "://" + request.getServerName()
                + port;

        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(reader);
        HtmlTag tag = new HtmlTag();
        while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
            int ttype = tok.getTokenType();

            if (ttype == HtmlStreamTokenizer.TT_TAG) {
                try {
                    tok.parseTag(tok.getStringValue(), tag);
                } catch (HtmlException htmle) {
                    log.error("Al parsear la plantilla , "
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
     * Genera el icono para imprimir el PDF de la busqueda hecha por el
     * Reporteador. Envia los par&aacute;metros que se usaran para generar el
     * PDF.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con la liga
     * y el icono correspondiente al elemento a exportar.
     * @throws SWBResourceException SWBResourceException SWBResourceException
     * Excepti&oacute;n utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public String doIconExportPDF(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuilder toReturn = new StringBuilder();
        Resource base2 = getResourceBase();
        String icon = "";

        if (base2 != null) {
            SWBResourceURL url = new SWBResourceURLImp(request, base2, paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
            url.setMode(Mode_StreamPDF);
            url.setCallMethod(SWBResourceURL.Call_DIRECT);
            String alt = paramRequest.getLocaleString("msgPrintPDFDocument");

            OntClass claseOnt = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getOntClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCElement");
            List<String> elements = new ArrayList<String>();
            Iterator<OntClass> children = claseOnt.listSubClasses();

            while (children != null && children.hasNext()) {
                OntClass child = children.next();
                String id = child.getLabel("es") != null ? child.getLabel("es") : child.getLocalName();
                elements.add(id);
            }

            toReturn.append("\n <script type=\"text/javascript\">");
            toReturn.append("\n  function getCriteria() {");
            toReturn.append("\n   if(document.getElementById('criteria') != null) {");
            toReturn.append("\n   var form = document.getElementById('frmDetail');");

            toReturn.append("\n      if(document.getElementById('elementType') != null) {");
            toReturn.append("\n           var elType = document.getElementById('elementType').value;");
            toReturn.append("\n           document.getElementById('elementType_').value = elType");
            toReturn.append("\n      }");
            toReturn.append("\n      if(document.getElementById('title') != null) {");
            toReturn.append("\n           var elTitle = document.getElementById('title').value;");
            toReturn.append("\n           document.getElementById('titleSelect_').value = elTitle");
            toReturn.append("\n      }");
            toReturn.append("\n      if(document.getElementById('initialPeriod') != null) {");
            toReturn.append("\n           var elInitialPer = document.getElementById('initialPeriod').value;");
            toReturn.append("\n           document.getElementById('initialPeriod_').value = elInitialPer");
            toReturn.append("\n      }");
            toReturn.append("\n      if(document.getElementById('finalPeriod') != null) {");
            toReturn.append("\n           var elFinalPer = document.getElementById('finalPeriod').value;");
            toReturn.append("\n           document.getElementById('finalPeriod_').value = elFinalPer");
            toReturn.append("\n      }");
            toReturn.append("\n      if(document.getElementById('status') != null) {");
            toReturn.append("\n           var elStatus = document.getElementById('status').value;");
            toReturn.append("\n           document.getElementById('status_').value = elStatus");
            toReturn.append("\n      }");
            toReturn.append("\n      if(document.getElementById('champion') != null) {");
            toReturn.append("\n           var elChampion = document.getElementById('champion').value;");
            toReturn.append("\n           document.getElementById('champion_').value = elChampion");
            toReturn.append("\n      }");
            toReturn.append("\n      if(document.getElementById('sponsor') != null) {");
            toReturn.append("\n           var elSponsor = document.getElementById('sponsor').value;");
            toReturn.append("\n           document.getElementById('sponsor_').value = elSponsor");
            toReturn.append("\n      }");
            if (!elements.isEmpty()) {
                for (int i = 0; i < elements.size(); i++) {
                    String childName = elements.get(i);
                    toReturn.append("\n      if(document.getElementById('relElem");
                    toReturn.append(childName);
                    toReturn.append("') != null) {");
                    toReturn.append("\n      var relElemt");
                    toReturn.append(childName);
                    toReturn.append("_ = document.getElementById('relElem");
                    toReturn.append(childName);
                    toReturn.append("').value;");
                    toReturn.append("\n           document.getElementById('relElem");
                    toReturn.append(childName);
                    toReturn.append("_').value = relElemt");
                    toReturn.append(childName);
                    toReturn.append("_");
                    toReturn.append("\n      }");
                }
            }
            toReturn.append("\n      if(document.getElementById('props2Show') != null) {");
            toReturn.append("\n            var elProps2Show = document.getElementById('props2Show')");
            toReturn.append("\n            var elProps2Show_ = document.getElementById('elProps2Show_')");
            toReturn.append("\n            delete_all(elProps2Show_);");
            toReturn.append("\n            moveOptions(elProps2Show, elProps2Show_);");
            toReturn.append("\n            selected_all(elProps2Show_);");
            toReturn.append("\n      }");
            toReturn.append("\n   form.submit();");
            toReturn.append("\n  } else {");
            toReturn.append("\n     alert('");
            toReturn.append(paramRequest.getLocaleString("msg_noSelectedCriteria"));
            toReturn.append("')");
            toReturn.append("\n  }");
            toReturn.append("\n  }");
            toReturn.append("\n  function moveOptions(theSelFrom, theSelTo){");
            toReturn.append("\n     var selLength = theSelFrom.length;");
            toReturn.append("\n     var selectedText = new Array();");
            toReturn.append("\n     var selectedValues = new Array();");
            toReturn.append("\n     var selectedCount = 0;");
            toReturn.append("\n     var ix;");
            toReturn.append("\n     for(ix=selLength-1; ix>=0; ix--){");
            toReturn.append("\n          if(theSelFrom.options[ix].selected){");
            toReturn.append("\n             selectedText[selectedCount] = theSelFrom.options[ix].text;");
            toReturn.append("\n             selectedValues[selectedCount] = theSelFrom.options[ix].value;");
            toReturn.append("\n             selectedCount++;");
            toReturn.append("\n          }");
            toReturn.append("\n     }");
            toReturn.append("\n     for(ix=selectedCount-1; ix>=0; ix--){");
            toReturn.append("\n          addOption(theSelTo, selectedText[ix], selectedValues[ix]);");
            toReturn.append("\n     }");
            toReturn.append("\n  }");
            toReturn.append("\n  function addOption(theSel, theText, theValue){");
            toReturn.append("\n     var newOpt = new Option(theText, theValue);");
            toReturn.append("\n     var selLength = theSel.length;");
            toReturn.append("\n     theSel.options[selLength] = newOpt; ");
            toReturn.append("\n  } ");
            toReturn.append("\n  function delete_all(obj){");
            toReturn.append("\n     if(obj != undefined) {");
            toReturn.append("\n         var selLength = obj.length;");
            toReturn.append("\n         for(ix=selLength-1; ix>=0; ix--){");
            toReturn.append("\n             obj.options[ix] = null;");
            toReturn.append("\n         }");
            toReturn.append("\n     }");
            toReturn.append("\n  }");
            toReturn.append("\n  function selected_all(obj){");
            toReturn.append("\n     for(var i = 0; i < obj.options.length; i++){");
            toReturn.append("\n         obj.options[i].selected = true;");
            toReturn.append("\n     }");
            toReturn.append("\n  }");
            toReturn.append("\n </script>");

            toReturn.append("<a href=\"javascript:getCriteria()");
            toReturn.append("\" class=\"export-stgy\" title=\"");
            toReturn.append(alt);
            toReturn.append("\" >");
            toReturn.append(alt);
            toReturn.append("</a>");
            toReturn.append("<form id=\"frmDetail\" method=\"post\" action=\"");
            toReturn.append(url);
            toReturn.append("\">");
            toReturn.append("   <input type=\"hidden\" id=\"elementType_\" name=\"elementType_\"/>");
            toReturn.append("   <input type=\"hidden\" id=\"titleSelect_\" name=\"titleSelect_\"/>");
            toReturn.append("   <input type=\"hidden\" id=\"initialPeriod_\" name=\"initialPeriod_\"/>");
            toReturn.append("   <input type=\"hidden\" id=\"finalPeriod_\" name=\"finalPeriod_\"/>");
            toReturn.append("   <input type=\"hidden\" id=\"status_\" name=\"status_\"/>");
            toReturn.append("   <input type=\"hidden\" id=\"champion_\" name=\"champion_\"/>");
            toReturn.append("   <input type=\"hidden\" id=\"sponsor_\" name=\"sponsor_\"/>");

            if (!elements.isEmpty()) {
                for (int i = 0; i < elements.size(); i++) {
                    String childName = elements.get(i);
                    toReturn.append("   <input type=\"hidden\" id=\"relElem");
                    toReturn.append(childName);
                    toReturn.append("_\" name=\"relElem");
                    toReturn.append(childName);
                    toReturn.append("_\"/>");
                }
            }
            toReturn.append("   <select id=\"elProps2Show_\" name=\"elProps2Show_\" multiple style=\"display:none;visibility:hidden\">");
            toReturn.append("   </select>");
            toReturn.append("</form>");
            icon = toReturn.toString();
        }
        return icon;
    }

    /**
     * Obtiene el tama&ntilde;o del PDF en base al n&uacute;mero de columnas
     * seleccionadas
     *
     * @param columns n&uacute;mero de columnas seleccionadas
     * @return tama&ntilde;o en que se exportar&aacute el PDF
     */
    private int getSize(int columns) {
        int value = 30;
        if (columns > 5) {
            int tmp = columns - 5;
            int tmp1 = tmp / 3;
            if (tmp % 3 > 0) {
                tmp1++;
            }
            value = value + (tmp1 * 10);
        }
        return value;
    }
}
