package org.semanticwb.bsc.resources;


import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.IOException;
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
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.BSC;
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
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/**
 * Muestra la interface de usuario con la que se capturan los criterios de b&uacute;squeda de datos para los reportes,
 * y la de los resultados encontrados por la b&uacute;squeda, adem&aacute;s de tener el control sobre la ejecuci&oacute;n
 * para la obtenci&oacute;n de los datos
 * @author jose.jimenez
 */
public class ReportGenerator extends GenericResource {
    
    
    /** Realiza operaciones en la bitacora de eventos. */
    private static final Logger log = SWBUtils.getLogger(ReportGenerator.class);

    /**
     * Presenta la interface de usuario para la captura de los criterios de b&uacute;squeda de informaci&oacute;n
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(256);
        SWBResourceURL url = paramRequest.getRenderUrl();
        Role actualUserRole = SWBContext.getSessionUser().getRole();
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
        output.append(paramRequest.getLocaleString("lbl_noSelection"));
        output.append("</option>\n");
        
        OntClass claseOnt = SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getOntClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCElement");
        Iterator<OntClass> children = claseOnt.listSubClasses();
        HashMap<String, OntClass> childMap = new HashMap<String, OntClass>(8);
        List<String> elements = new ArrayList<String>();
        while (children != null && children.hasNext()) {
            OntClass child = children.next();
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
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    /**
     * Obtiene el listado de los elementos que pertenecen al tipo que se indica en el par&aacute;metro del request
     * con nombre {@code type}.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
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
                    if (element.getBooleanProperty(BSCElement.swb_active, false, false) && 
                            user.haveAccess(element.createGenericInstance())) {
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
                iterator = ((Initiative) generic).listStates();
            } else if (generic instanceof Deliverable) {
                iterator = ((Deliverable) generic).getInitiative().listStates();
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
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    public void doReport(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(256);
        ReportCriteria criteria = this.createCriteria(request);
        ArrayList<SemanticObject> results = this.processReport(criteria, paramRequest);
        int itemsCount = 0;
        int titleIndex = -1;
        
        request.setAttribute("websiteId", paramRequest.getWebPage().getWebSiteId());
        
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
     * Obtiene el listado de per&iacute;odos a los que tiene acceso el usuario en sesi&oacute;n
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @return un objeto {@code List} que contiene los per&iacute;odos a los que tiene acceso el usuario en sesi&oacute;n
     */
    private List<Period> getAllowedPeriods(SWBParamRequest paramRequest) {
        
        //Obtener el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();
        
        //Obtener listado de instancias de Periodos en el Scorecard
        Iterator<Period> allPeriods = Period.ClassMgr.listPeriods(currentBsc);
        List<Period> periods = SWBUtils.Collections.filterIterator(allPeriods, new GenericFilterRule<Period>() {
                @Override
                public boolean filter(Period s) {
                    User user = SWBContext.getSessionUser();
                    return !s.isValid() || !user.haveAccess(s);
                }
            });
        Collections.sort(periods);
        
        return periods;
    }
    
    /**
     * Obtiene el conjunto de objetos {@code User} que tiene asignado como rol, el inidicado en la invocaci&oacute;n
     * @param roleName el identificador del rol, del cual se desea obtener los usuarios asociados
     * @param model el modelo o sitio del cual se deben extraer los usuarios
     * @return el conjunto de objetos {@code User} que tiene asignado el rol especificado
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
     * Genera un {@code String} que representa un arreglo de objetos en JavaScript con los valores y etiquetas
     * de las propiedades de los objetos indicados en {@code children} para representarlos en objetos de forma de HTML.
     * @param children contiene el conjunto de objetos sem&aacute;nticos de los que se quiere obtener sus propiedades.
     * @return un {@code String} que representa un arreglo de objetos en JavaScript. Cada objeto contiene dos atributos:
     * objectType (el nombre de la clase semantica correspondiente) y data (un arreglo de objetos an&oacute;nimos con las propiedades:
     * value -uri de una propiedad sem&aacute;ntica y label -nombre a desplegar de la propiedad)
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
                ArrayList<SemanticProperty> propsList = (ArrayList<SemanticProperty>) 
                        SWBUtils.Collections.copyIterator(semanticChild.listProperties());
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
     * Verifica la existencia de un valor para el displayElement de una propiedad en la ontolog&iacute;a
     * @param property la propiedad sem&aacute;ntica de la que se requiere el displayElement
     * @return {@literal true} si existe un valor para el displayElement en la ontolog&iacute;a, {@literal false} de lo contrario
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
     * Crea la instancia de {@code ReportCriteria} con la que se generar&aacute; el reporte solicitado en el {@code request}
     * @param request petici&oacute;n del cliente con los criterios para generar el reporte
     * @return la instancia de {@code ReportCriteria} con los criterios para generar el reporte
     */
    private ReportCriteria createCriteria(HttpServletRequest request) {
        
        ReportCriteria criteria = new ReportCriteria();
        ArrayList<String> related = new ArrayList<String>(4);
        ArrayList<SemanticProperty> properties = new ArrayList<SemanticProperty>(16);
        
        Enumeration parameters = request.getParameterNames();
        while (parameters != null && parameters.hasMoreElements()) {
            String parameterName = (String) parameters.nextElement();
            if (parameterName.equals("elementType") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                criteria.setElementType(request.getParameter(parameterName));
            }
            if (parameterName.equals("title") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                criteria.setObjectTitle(request.getParameter(parameterName));
            }
            if (parameterName.startsWith("relElem") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                related.add(request.getParameter(parameterName));
            }
            if (parameterName.equals("initialPeriod") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                SemanticObject periodSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject periodGen = periodSem.createGenericInstance();
                if (periodGen instanceof Period) {
                    criteria.setInitialPeriod((Period) periodGen);
                }
            }
            if (parameterName.equals("finalPeriod") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                SemanticObject periodSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject periodGen = periodSem.createGenericInstance();
                if (periodGen instanceof Period) {
                    criteria.setFinalPeriod((Period) periodGen);
                }
            }
            if (parameterName.equals("status") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                SemanticObject statusSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject statusGen = statusSem.createGenericInstance();
                if (statusGen instanceof State) {
                    criteria.setStatus((State) statusGen);
                }
            }
            if (parameterName.equals("champion") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                SemanticObject championSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject championGen = championSem.createGenericInstance();
                if (championGen instanceof User) {
                    criteria.setChampion((User) championGen);
                }
            }
            if (parameterName.equals("sponsor") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                SemanticObject sponsorSem = SemanticObject.createSemanticObject(request.getParameter(parameterName));
                GenericObject sponsorGen = sponsorSem.createGenericInstance();
                if (sponsorGen instanceof User) {
                    criteria.setSponsor((User) sponsorGen);
                }
            }
            if (parameterName.equals("props2Show") && request.getParameter(parameterName) != null &&
                    !request.getParameter(parameterName).isEmpty()) {
                
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
     * Procesa los datos disponibles en el sistema para obtener el conjunto de datos a presentar en el reporte.
     * @param criteria contiene los criterios que deben cumplir los datos a mostrar en el reporte
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @return el conjunto de elementos del BSC que coinciden con los criterios de busqueda seleccionados por el usuario
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
                    if (initial2Compare != null && final2Compare != null && criteria.getInitialPeriod().getStart().before(initial2Compare) &&
                            initial2Compare.before(criteria.getFinalPeriod().getEnd()) && criteria.getInitialPeriod().getStart().before(final2Compare) &&
                            final2Compare.before(criteria.getFinalPeriod().getEnd())) {
                        //Sin seleccion de estatus
                        if (criteria.getStatus() == null) {
                            mustBeAdded = true;
                        } else {
                            //el estatus debe estar asignado al entregable
                            if (inTurn instanceof Deliverable) {
                                Deliverable deli = (Deliverable) inTurn;
                                if ((deli.getAutoStatus() != null && deli.getAutoStatus().equals(criteria.getStatus())) || 
                                        (deli.getStatusAssigned() != null && deli.getStatusAssigned().equals(criteria.getStatus()))) {
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
                    if ( ((Indicator) inTurn).getChampion().equals(criteria.getChampion()) ) {
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
                    if ( ((Objective) inTurn).getSponsor().equals(criteria.getSponsor()) ) {
                        mustBeAdded = true;
                    }
                } else {
                    if (inTurn instanceof Indicator) {
                        if (((Indicator) inTurn).getObjective().getSponsor().equals(criteria.getSponsor())) {
                            mustBeAdded = true;
                        }
                    } else if (inTurn instanceof Initiative) {
                        if (((Initiative) inTurn).getObjectiveInitiative().getSponsor().equals(criteria.getSponsor())) {
                            mustBeAdded = true;
                        }
                    } else if (inTurn instanceof Deliverable) {
                        if (((Deliverable) inTurn).getInitiative().getObjectiveInitiative().getSponsor().equals(criteria.getSponsor())) {
                            mustBeAdded = true;
                        }
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
     * Agrega a la coleccion {@code resultSet} los objetos relacionados al elemento {@code element}
     * de los tipos especificados en {@code relatedTypes}
     * @param resultSet la coleccion que contiene el total de elementos a devolver en el reporte
     * @param element el elemento del BSC del que se obtendran los elementos relacionados
     * @param relatedTypes los tipos de objeto que se deben agregar al conjunto de resultados
     */
    private void addRelated(ArrayList<SemanticObject> resultSet, GenericObject element, ArrayList<String> relatedTypes) {
        
        ArrayList<SemanticObject> additional = new ArrayList<SemanticObject>(64);
        if (element instanceof Objective) {
            Objective obj = (Objective) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Indicator.bsc_Indicator.getName())) {
                    Iterator<Indicator> it = obj.listIndicators();
                    while (it != null && it.hasNext()) {
                        additional.add(it.next().getSemanticObject());
                    }
                } else if (type.endsWith(Initiative.bsc_Initiative.getName())) {
                    Iterator<Initiative> it = obj.listInitiatives();
                    while (it != null && it.hasNext()) {
                        additional.add(it.next().getSemanticObject());
                    }
                } else if (type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    //se obtiene el entregable, a partir de la iniciativa
                    Iterator<Initiative> it = obj.listInitiatives();
                    while (it != null && it.hasNext()) {
                        Initiative ini = it.next();
                        Iterator<Deliverable> itDeli = ini.listDeliverables();
                        while (itDeli != null && itDeli.hasNext()) {
                            additional.add(itDeli.next().getSemanticObject());
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
                    //No se consideran relaciones indirectas entre iniciativas e indicadores 
                } else if (type.endsWith(Deliverable.bsc_Deliverable.getName())) {
                    //No se consideran relaciones indirectas entre entregables e indicadores 
                }
            }
        } else if (element instanceof Initiative) {
            Initiative ini = (Initiative) element;
            for (String type : relatedTypes) {
                if (type.endsWith(Objective.bsc_Objective.getName())) {
                    additional.add(ini.getObjectiveInitiative().getSemanticObject());
                } else if (type.endsWith(Indicator.bsc_Indicator.getName())) {
                    //No se consideran relaciones indirectas entre iniciativas e indicadores
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
                    //se obtiene el objetivo del entregable a partir de la iniciativa
                    additional.add(deli.getInitiative().getObjectiveInitiative().getSemanticObject());
                } else if (type.endsWith(Indicator.bsc_Indicator.getName())) {
                    //No se consideran relaciones indirectas entre entregables e indicadores 
                } else if (type.endsWith(Initiative.bsc_Initiative.getName())) {
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
     * Devuelve el despliegue correspondiente al valor de la propiedad especificada, del objeto indicado.
     * @param request petici&oacute;n HTTP realizada por el cliente
     * @param elementBSC representa el objeto del cual se desea extraer la informaci&oacute;n
     * @param propUri representa la uri de la propiedad semantica de la que se desea obtener su valor
     * @param lang representa el lenguaje en que se desea mostrar el valor de la propiedad indicada
     * @return el despliegue del valor almacenado para la propiedad indicada
     */
    private String renderPropertyValue(HttpServletRequest request, SemanticObject elementBSC,
            String propUri, String lang) {
        
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
}

