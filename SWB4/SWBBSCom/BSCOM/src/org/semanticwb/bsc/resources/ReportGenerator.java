package org.semanticwb.bsc.resources;


import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.BSCElement;
import org.semanticwb.bsc.utils.PropertiesComparator;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
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
        
        String propertiesScript = getClassesProperties(childMap);
        output.append("      </select>\n");
        output.append("    </div>\n");
        output.append("    <div class=\"\"><label for=\"title\">");
        output.append(paramRequest.getLocaleString("lbl_title"));
        output.append("</label></div>\n");
        output.append("    <div class=\"\">\n");
        output.append("      <select name=\"title\" id=\"titleSelect\">\n");
        output.append("        <option value=\"\">");
        output.append(paramRequest.getLocaleString("lbl_title_opt"));
        output.append("</option>\n");
        output.append("      </select>\n");
        output.append("    </div>\n");
        
        Iterator<Period> periods = getAllowedPeriods(paramRequest).iterator();
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
        
        Iterator<User> champions = userListByRole(paramRequest.getLocaleString("usersRole2List"), repository);
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

            Iterator<User> sponsors = userListByRole("Sponsor", repository);
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
        output.append("    }\n");
        output.append("    function populateSelect(select2Feed, dataContainer) {\n");
        output.append("      if (dataContainer.data.length > 0) {\n");
        output.append("        for (var i = 0; i < dataContainer.data.length; i++) {\n");
        output.append("          var optionToAdd = new Option(dataContainer.data[i].label, dataContainer.data[i].value, false, false);\n");
        output.append("          select2Feed.options.add(optionToAdd);\n");
        output.append("        }\n");
        output.append("        select2Feed.selectedIndex = 0;\n");
        output.append("      }\n");
        output.append("    }\n");
        output.append("    function cleanSelect(elementId) {\n");
        output.append("      var select2Clear = document.getElementById(elementId);\n");
        output.append("      for (var j = select2Clear.options.length - 1; j >= 0; j--) {\n");
        output.append("        select2Clear.options.remove(j);\n");
        output.append("      }\n");
        output.append("    }\n");
        output.append("    function evaluate2Submit(form) {\n");
        output.append("      form.submit();\n");
        output.append("    }\n");
        output.append("  </script>");
        out.println(output.toString());
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode != null && mode.equals("elementList")) {
            doElementList(request, response, paramRequest);
        } else if (mode != null && mode.equals("report")) {
            doReport(request, response, paramRequest);
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
                    if (element.getBooleanProperty(BSCElement.swb_active, false, false)) {
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
        
        //TODO:Con datos del request generar instancia de ReportCriteria y ejecutar reporte
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
                    if ((prop.getDisplayProperty() != null || displayElementExists(prop))) {
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

}
