package org.semanticwb.bsc.admin.resources;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.PDFExportable;
import org.semanticwb.bsc.Seasonable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.utils.SummaryView;
import org.semanticwb.bsc.utils.PropertyListItem;
import org.semanticwb.bsc.element.*;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.bsc.admin.resources.base.SummaryViewManagerBase;
import org.semanticwb.bsc.element.Agreement;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.utils.PropertiesComparator;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticOntology;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Recurso que administra instancias de tipo {@code SummaryView}. Permite crear,
 * editar y eliminar objetos {@code SummaryView} y asignar como vista de
 * despliegue uno de ellos en particular.
 *
 * @author jose.jimenez
 */
public class SummaryViewManager extends SummaryViewManagerBase implements PDFExportable {

    /*Sirve para evaluar el modo recibido cuando se trate de exportar a pdf la vista resumen*/
    public static final String Mode_PDFDocument = "pdf";
    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static Logger log = SWBUtils.getLogger(GenericSemResource.class);
    /**
     * Representa el nombre del archivo asociado a las plantillas creadas con
     * este recurso
     */
    private static final String TEMPLATE_FILENAME = "/templateContent.html";
    /**
     * Representa el nombre del modo que se encarga de exportar la vista a PDF
     */
//    private static final String Mode_PDF = "exportPDF";

    /**
     * Construye una instancia de tipo {@code SummaryViewManager}
     */
    public SummaryViewManager() {
    }

    /**
     * Constructs a SummaryViewManager with a SemanticObject
     *
     * @param base The SemanticObject with the properties for the
     * SummaryViewManager
     */
    public SummaryViewManager(SemanticObject base) {

        super(base);
    }

    /**
     * Genera el c&oacute;digo HTML que representa la vista de este recurso en
     * el espacio asignado del lado de la aplicaci&oacute;n. Por si mismo genera
     * el c&oacute;digo HTML o delega esta tarea a otros m&eacute;todos o JSP's.
     *
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @param paramRequest objeto por el que se accede a varios exclusivos de
     * SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(128);
        String lang = paramRequest.getUser().getLanguage();
        User user = paramRequest.getUser();
        if (this.getActiveView() == null) {
            output.append(paramRequest.getLocaleString("msg_noContentView"));
        } else {
            SummaryView activeView = this.getActiveView();
            List propsInView = SWBUtils.Collections.copyIterator(activeView.listPropertyListItems());

            //Se obtiene el conjunto de instancias correspondientes al valor de workClass, en el sitio, de las que 
            //se tiene captura de datos en el periodo obtenido
            SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();
            WebSite website = this.getResourceBase().getWebSite();
            Iterator<GenericObject> allInstances = website.listInstancesOfClass(semWorkClass);
            String identifier = null; //de los elementos del grid
            String filters = null;
            String periodId = (String) request.getSession(true).getAttribute(website.getId());
            boolean addStatus = false;

            //Si no hay sesion, la peticion puede ser directa (una liga en un correo). Crear sesion y atributo:
            if (periodId == null) {
                periodId = request.getParameter(website.getId()) != null
                        ? request.getParameter(website.getId()) : null;
                if (periodId != null) {
                    request.getSession(true).setAttribute(website.getId(), periodId);
                }
            }
            Period thisPeriod = periodId != null
                    ? Period.ClassMgr.getPeriod(periodId, website)
                    : null;

            //Define el identificador a utilizar de acuerdo al tipo de objetos a presentar
            if (semWorkClass.equals(Objective.bsc_Objective)) {
                identifier = paramRequest.getLocaleString("value_ObjectiveId");
                filters = paramRequest.getLocaleString("value_ObjectiveFilter");
                addStatus = true;
            } else if (semWorkClass.equals(Indicator.bsc_Indicator)) {
                identifier = paramRequest.getLocaleString("value_IndicatorId");
                filters = paramRequest.getLocaleString("value_IndicatorFilter");
                addStatus = true;
            } else if (semWorkClass.equals(Initiative.bsc_Initiative)) {
                identifier = paramRequest.getLocaleString("value_InitiativeId");
                filters = paramRequest.getLocaleString("value_InitiativeFilter");
            } else if (semWorkClass.equals(Deliverable.bsc_Deliverable)) {
                identifier = paramRequest.getLocaleString("value_DeliverableId");
                filters = paramRequest.getLocaleString("value_DeliverableFilter");
            } else if (semWorkClass.equals(Agreement.bsc_Agreement)) {
                identifier = paramRequest.getLocaleString("value_AgreementId");
                filters = paramRequest.getLocaleString("value_AgreementFilter");
            }
            //objeto JSON que almacenara la estructura del grid de Dojo
            JSONObject structure = new JSONObject();
            JSONArray items = new JSONArray();
            try {
                structure.append("identifier", identifier);
            } catch (JSONException jsone) {
                SummaryViewManager.log.error("En la creacion de objetos JSON", jsone);
            }
            //Por cada instancia (de tipo workClass) en el sitio:
            while (allInstances.hasNext()) {
                GenericObject generic = allInstances.next();
                SemanticObject semObj = generic.getSemanticObject();
                boolean isActive = semObj.getBooleanProperty(org.semanticwb.model.Activeable.swb_active, true);
                boolean hasPeriod = true;
                if (thisPeriod != null) {
                    if (semObj.instanceOf(Seasonable.bsc_Seasonable)) {
                        hasPeriod = semObj.hasObjectProperty(Seasonable.bsc_hasPeriod, thisPeriod.getSemanticObject());
                    } else {//Para iniciativas y entregables:
                        hasPeriod = true;
                    }
                }
                if (!user.haveAccess(generic) || !isActive || !hasPeriod) {
                    continue;
                }
                GenericIterator<PropertyListItem> viewPropertiesList = activeView.listPropertyListItems();

                JSONObject row = new JSONObject();
                StringBuilder status = new StringBuilder(128);

                if (addStatus) {
                    PeriodStatus perStat = null;
                    if (generic instanceof Objective) {
                        Objective obj = (Objective) generic;
                        perStat = obj.getPeriodStatus(thisPeriod);
                    } else if (generic instanceof Indicator) {
                        Indicator indicator = (Indicator) generic;
                        Measure measure = indicator != null && indicator.getStar() != null
                                ? indicator.getStar().getMeasure(thisPeriod) : null;
                        if (measure != null && measure.getEvaluation() != null) {
                            perStat = measure.getEvaluation();
                        }
                    }
                    if (perStat != null && perStat.getStatus() != null) {
//                        if (perStat.getStatus().getIcon() != null) {
//                            status.append("<img src=\"");
//                            status.append(perStat.getStatus().getIcon());
//                            status.append("\" title=\"");
//                            status.append(perStat.getStatus().getTitle());
//                            status.append("\" />");
//                        }
//                        else
//                        {
                            status.append("<span class=\"");
                            status.append(perStat.getStatus().getIconClass());
                            status.append("\">");
                            status.append(perStat.getStatus().getTitle());
                            status.append("</span>");
//                        }
                    } else {
                        status.append("<span class=\"indefinido\">Indefinido</span>");
                    }
                    try {
                        row.put("status", status.toString());
                    } catch (JSONException jsone) {
                        SummaryViewManager.log.error("En la creacion de objetos JSON", jsone);
                    }

                }

                //Por cada propiedad en la vista:
                while (viewPropertiesList.hasNext()) {
                    PropertyListItem propListItem = viewPropertiesList.next();
                    SemanticProperty elementProperty = propListItem.getElementProperty().transformToSemanticProperty();
                    String propertyValue = null; //para las propiedades tipo objeto
                    //Para mostrar los valores de las propiedades, de acuerdo a los FormElements asignados a cada propiedad:
                    propertyValue = renderPropertyValue(request, semObj, elementProperty.getURI(), lang);
                    try {
                        row.put(elementProperty.getName(), propertyValue);
                    } catch (JSONException jsone) {
                        SummaryViewManager.log.error("En la creacion de objetos JSON", jsone);
                    }
                }

                //Se utiliza el URI como identificador de los elementos del grid
                //Agrega la uri de cada instancia para poder crear las ligas a las vistas detalle
                try {
                    row.put("uri", semObj.getURI());
                } catch (JSONException jsone) {
                    SummaryViewManager.log.error("En la creacion de objetos JSON", jsone);
                }
                //Agrega el objeto "renglon" a la estructura del grid
                items.put(row);
            }
            try {
                structure.put("items", items);
            } catch (JSONException jsone) {
                SummaryViewManager.log.error("En la creacion de objetos JSON", jsone);
            }

            //Obtiene encabezados de tabla y propiedades para filtros
            Iterator<PropertyListItem> viewPropertiesList = propsInView.iterator();//activeView.listPropertyListItems();
            ArrayList<String[]> headingsArray = new ArrayList<String[]>(16);
            TreeMap headings2Show = new TreeMap();

            if (addStatus) {
                String[] statusHeading = {
                    "status",
                    "Estado",
                    "true"
                };
                headingsArray.add(statusHeading);//Para los filtros
                headings2Show.put(Integer.parseInt("0"), statusHeading);
            }

            boolean showFiltering = false;
            if (viewPropertiesList != null) {
                while (viewPropertiesList.hasNext()) {
                    PropertyListItem propListItem = viewPropertiesList.next();
                    SemanticProperty property = propListItem.getElementProperty().transformToSemanticProperty();
                    int arrayIndex = propListItem.getPropertyOrder();
                    if (addStatus) { //Si se agrego la columna de status, las demas se recorren
                        arrayIndex++;
                    }

                    if (propListItem != null && property != null) {
                        String[] heading = {
                            property.getName(),
                            property.getLabel(lang),
                            (filters != null && filters.contains(property.getName()))
                            ? "true" : "false"
                        };
                        headingsArray.add(heading);
                        headings2Show.put(new Integer(arrayIndex), heading);
                        if (filters != null && filters.contains(property.getName())) {
                            showFiltering = true;
                        }
                    }
                }
            }

            //Declara el codigo HTML para inclusion de Dojo.Grid y sus estilos
            output.append("<script type=\"text/javascript\">\n");
            output.append("  dojo.require('dojo.parser');\n");
            output.append("  dojo.require('dojox.layout.ContentPane');\n");
            output.append("  dojo.require('dijit.form.Form');\n");
            output.append("  dojo.require('dijit.form.Button');\n");
            output.append("  dojo.require('dijit.form.MultiSelect');\n");
            output.append("  dojo.require('dojox.grid.DataGrid');\n");
            output.append("  dojo.require('dojo.data.ItemFileReadStore');\n");
            output.append("  var structure = ");
            try {
                output.append(structure.toString(2));
            } catch (JSONException jsone) {
                SummaryViewManager.log.error("En la escritura de store del grid", jsone);
                output.append("{}");
            }
            output.append(";\n");
            /* -------------------------------------------------------------- */
            output.append("  ");
            output.append("  var myStore, myGrid;\n");
            output.append("  dojo.addOnLoad(function() {\n");
            output.append("    myStore = new dojo.data.ItemFileReadStore({\n");
            output.append("      data : structure\n");
            output.append("    });\n");
            output.append("    myGrid = new dojox.grid.DataGrid({\n");
            output.append("      store: myStore,\n");
            output.append("      structure: [\n");
            // Coloca cada columna en la estructura del grid
            Entry thisHeading = headings2Show.firstEntry();
            int iCount = 0;
            while (thisHeading != null) {
                Integer thisKey = (Integer) thisHeading.getKey();

                if (iCount > 0) {
                    output.append(",\n");
                } else {
                    output.append("\n");
                }
                String[] heading = (String[]) thisHeading.getValue();
                if (heading != null && heading.length > 1) {
                    output.append("        {\n");
                    output.append("          name: \"");
                    output.append(heading[1]);
                    output.append("\", field: \"");
                    output.append(heading[0]);
                    output.append("\", width: \"auto\",\n");
                    output.append("          formatter: function(content, rowIndex, cell) {\n");
                    output.append("            var toReturn = content;\n");
                    output.append("            while (content.indexOf(\"&lt\") > -1) {\n");
                    output.append("              toReturn = content.replace(\"&lt;\", \"<\");\n");
                    output.append("              content = toReturn;\n");
                    output.append("            }\n");
                    output.append("            return toReturn;\n");
                    output.append("          }\n");
                    output.append("        }");
                }
                iCount++;
                thisHeading = headings2Show.higherEntry(thisKey);
            }
            output.append("      ],\n");
            output.append("      rowSelector: '10px', \n");
            output.append("      autoHeight: true \n");
            output.append("    }, \"grid\");\n");
            output.append("    myGrid.startup();\n");
            output.append("  });\n");
            output.append("</script>\n");
            output.append("<link rel=\"stylesheet\" href=\"/swbadmin/js/dojo/dojox/grid/resources/Grid.css\"/>\n");
            output.append("<link rel=\"stylesheet\" href=\"/swbadmin/js/dojo/dojox/grid/resources/soriaGrid.css\"/>\n");
            output.append("<style type=\"text/css\">\n");
            output.append("  .dojoxGrid table { margin: 0; } ");
            output.append("</style>\n");

            //Se evalua el mostrar la forma para filtrado en grid
            if (filters != null && showFiltering) {
                output.append("<div class=\"filter\">\n");
                output.append("  <form dojoType=\"dijit.form.Form\" name=\"filter\" id=\"filterForm");
                output.append(this.getId());
                output.append("\" method=\"post\">\n");
                output.append("    <select name=\"filterCriteria\" id=\"filterCriteria");
                output.append(this.getId());
                output.append("\">\n");
                output.append("        <option value=\"");
                output.append(identifier);
                output.append("\">");
                output.append(paramRequest.getLocaleString("lbl_chooseFilter"));
                output.append("</option>\n");

                for (int i = 0; i < headingsArray.size(); i++) {
                    String[] heading = headingsArray.get(i);
                    if (heading != null && heading.length > 2 && heading[2].equals("true")) {
                        output.append("        <option value=\"");
                        output.append(heading[0]);
                        output.append("\">");
                        output.append(heading[1]);
                        output.append("</option>\n");
                    }
                }
                output.append("    </select>\n");
                output.append("    <script type=\"text/javascript\">");
                output.append("      function catchKeyStrokes(e) {");
                output.append("        if (e.keyCode == 13) {");
                output.append("          e.preventDefault ? e.preventDefault() : e.returnValue = false;");
                output.append("          return false;");
                output.append("        }");
                output.append("      }");
                output.append("    </script>");
                output.append("    <input type=\"text\" name=\"filterValue\" id=\"filterValue");
                output.append(this.getId());
                output.append("\" onkeydown=\"catchKeyStrokes(event);\">\n");
                output.append("      <span dojoType='dijit.form.Button'>\n");
                output.append("          Aplicar\n");
                //funcion de javascript para aplicacion de filtro en Grid:
                output.append("          <script type=\"dojo/method\" event='onClick' args='evt'>\n");
                output.append("                var choosenCriteria = document.getElementById('filterCriteria");
                output.append(this.getId());
                output.append("').value;\n");
                output.append("                var choosenValue = document.getElementById('filterValue");
                output.append(this.getId());
                output.append("');\n");
                output.append("                if (choosenValue.value != '') {\n");

                //Evalua entre los diferentes criterios de filtrado
                for (int i = 0; i < headingsArray.size(); i++) {
                    String[] heading = headingsArray.get(i);
                    if (heading != null && heading.length > 2 && heading[2].equals("true")) {
                        output.append("                  if (choosenCriteria == '");
                        output.append(heading[0]);
                        output.append("') {\n");
                        output.append("                    myGrid.setQuery({");
                        output.append(heading[0]);
                        output.append(": '*' + choosenValue.value + '*'});\n");
                        output.append("                  }\n");
                    }
                }
                output.append("                } else if (choosenValue.value == '') {\n");
                output.append("                  myGrid.setQuery({");
                output.append(identifier);
                output.append(": '*'});\n");
                output.append("                }\n");
                output.append("          </script>\n");
                output.append("      </span>\n");
                output.append("  </form>\n");
                output.append("</div>\n");
            }

            output.append("<div id=\"grid\"></div>\n");

        }
        out.println(output.toString());
    }

    //Método para exportar la vista resumen a PDF
    /* @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @param paramRequest objeto por el que se accede a varios exclusivos de
     * SWB
     * * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    public void doGetPDFDocument(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment; filename=\""+getResourceBase().getWebSiteId()+".summary.pdf\"");
        
        StringBuilder sb = renderHTML(request, response, paramRequest);
        if (sb != null && sb.length() > 0) {
            OutputStream os = response.getOutputStream();
            try {
                ITextRenderer renderer = new ITextRenderer();
                //renderer.setDocumentFromString(renderHTML(request, response, paramRequest));
                String sbStr = replaceHtml(sb);
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
    }

    //Método que arma el html para mostrar en el pdf
    /* @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @param paramRequest objeto por el que se accede a varios exclusivos de
     * SWB
     * * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    private StringBuilder renderHTML(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuilder output = new StringBuilder(128);
        StringBuilder hd = new StringBuilder(128);
        StringBuilder toReturn = new StringBuilder(128);
        String lang = paramRequest.getUser().getLanguage();
        User user = paramRequest.getUser();
        if (this.getActiveView() == null) {
            output.append(paramRequest.getLocaleString("msg_noContentView"));
        } else {
            SummaryView activeView = this.getActiveView();
            List propsInView = SWBUtils.Collections.copyIterator(activeView.listPropertyListItems());

            //Se obtiene el conjunto de instancias correspondientes al valor de workClass, en el sitio, de las que 
            //se tiene captura de datos en el periodo obtenido
            SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();
            WebSite website = this.getResourceBase().getWebSite();
            Iterator<GenericObject> allInstances = website.listInstancesOfClass(semWorkClass);
            String identifier = null; //de los elementos del grid
            String filters = null;
            String periodId = (String) request.getSession(true).getAttribute(website.getId());
            boolean addStatus = false;

            //Si no hay sesion, la peticion puede ser directa (una liga en un correo). Crear sesion y atributo:
            if (periodId == null) {
                periodId = request.getParameter(website.getId()) != null
                        ? request.getParameter(website.getId()) : null;
                if (periodId != null) {
                    request.getSession(true).setAttribute(website.getId(), periodId);
                }
            }
            Period thisPeriod = periodId != null
                    ? Period.ClassMgr.getPeriod(periodId, website)
                    : null;

            //Define el identificador a utilizar de acuerdo al tipo de objetos a presentar
            if (semWorkClass.equals(Objective.bsc_Objective)) {
                identifier = paramRequest.getLocaleString("value_ObjectiveId");
                addStatus = true;
            } else if (semWorkClass.equals(Indicator.bsc_Indicator)) {
                identifier = paramRequest.getLocaleString("value_IndicatorId");
                addStatus = true;
            } else if (semWorkClass.equals(Initiative.bsc_Initiative)) {
                identifier = paramRequest.getLocaleString("value_InitiativeId");
            } else if (semWorkClass.equals(Deliverable.bsc_Deliverable)) {
                identifier = paramRequest.getLocaleString("value_DeliverableId");
            } else if (semWorkClass.equals(Agreement.bsc_Agreement)) {
                identifier = paramRequest.getLocaleString("value_AgreementId");
            }
            //Comienza a armar el html a regresar
            output.append("<html>");
            output.append("<head>");
            output.append("<style type=\"text/css\">");
            output.append("    @page { size: 11in 8.5in;}");
            output.append("</style>");
            output.append(getLinks(paramRequest, request));
            output.append("</head>");
            output.append("<body>");
            output.append("<table width=\"100%\" border=\"0\" class=\"\">");
            //Obtiene encabezados de tabla
            output.append("<tr>");
            Iterator<PropertyListItem> viewPropertiesList = propsInView.iterator();//activeView.listPropertyListItems();
            ArrayList<String[]> headingsArray = new ArrayList<String[]>(16);
            TreeMap headings2Show = new TreeMap();
            if (addStatus) {
                String[] statusHeading = {
                    "status",
                    "Estado",
                    "true"
                };
                headingsArray.add(statusHeading);
                headings2Show.put(Integer.parseInt("0"), statusHeading);
            }

            if (viewPropertiesList != null) {
                while (viewPropertiesList.hasNext()) {
                    PropertyListItem propListItem = viewPropertiesList.next();
                    SemanticProperty property = propListItem.getElementProperty().transformToSemanticProperty();
                    int arrayIndex = propListItem.getPropertyOrder();
                    if (addStatus) { //Si se agrego la columna de status, las demas se recorren
                        arrayIndex++;
                    }

                    if (propListItem != null && property != null) {
                        String[] heading = {
                            property.getName(),
                            property.getLabel(lang)
                        };
                        headingsArray.add(heading);
                        headings2Show.put(new Integer(arrayIndex), heading);
                    }
                }
            }

            // Coloca cada columna en el orden guardado
            Entry thisHeading = headings2Show.firstEntry();
            while (thisHeading != null) {
                Integer thisKey = (Integer) thisHeading.getKey();
                String[] heading = (String[]) thisHeading.getValue();
                thisHeading = headings2Show.higherEntry(thisKey);
                output.append("<th>" + heading[1] + "</th>");
            }
            output.append("</tr>");


            //Arma los renglones con el contenido de la tabla
            while (allInstances.hasNext()) {
                GenericObject generic = allInstances.next();
                SemanticObject semObj = generic.getSemanticObject();
                boolean isActive = semObj.getBooleanProperty(org.semanticwb.model.Activeable.swb_active, true);
                boolean hasPeriod = true;
                if (thisPeriod != null) {
                    if (semObj.instanceOf(Seasonable.bsc_Seasonable)) {
                        hasPeriod = semObj.hasObjectProperty(Seasonable.bsc_hasPeriod, thisPeriod.getSemanticObject());
                    } else {//Para iniciativas y entregables:
                        hasPeriod = true;
                    }
                }
                if (!user.haveAccess(generic) || !isActive || !hasPeriod) {
                    continue;
                }
                GenericIterator<PropertyListItem> viewPropertiesList1 = activeView.listPropertyListItems();
                StringBuilder status = new StringBuilder(128);

                output.append("<tr>");
                if (addStatus) {
                    PeriodStatus perStat = null;
                    if (generic instanceof Objective) {
                        Objective obj = (Objective) generic;
                        perStat = obj.getPeriodStatus(thisPeriod);
                    } else if (generic instanceof Indicator) {
                        Indicator indicator = (Indicator) generic;
                        Measure measure = indicator != null && indicator.getStar() != null
                                ? indicator.getStar().getMeasure(thisPeriod) : null;
                        if (measure != null && measure.getEvaluation() != null) {
                            perStat = measure.getEvaluation();
                        }
                    }
                    if (perStat != null && perStat.getStatus() != null) {
                        if (perStat.getStatus().getIcon() != null) {
                            status.append("<img src=\"");
                            status.append(perStat.getStatus().getIcon());
                            status.append("\" title=\"");
                            status.append(perStat.getStatus().getTitle());
                            status.append("\" />");
                        } else {
                            status.append("<span class=\"");
                            status.append(perStat.getStatus().getIconClass());
                            status.append("\">");
                            status.append(perStat.getStatus().getTitle());
                            status.append("</span>");
                        }
                    } else {
                        status.append("<span class=\"indefinido\">Indefinido</span>");
                    }
                    output.append("<td>" + status.toString() + "</td>");

                }
                //Por cada propiedad en la vista:
                request.setAttribute("pdf", "true");//atributo para validaciones en formElement y que no muestre links
                if (viewPropertiesList1 != null) {
                    while (viewPropertiesList1.hasNext()) {
                        PropertyListItem propListItem = viewPropertiesList1.next();
                        SemanticProperty property = propListItem.getElementProperty().transformToSemanticProperty();
                        String propertyValue = renderPropertyValue(request, semObj, property.getURI(), lang);
                        int arrayIndex = propListItem.getPropertyOrder();
                        if (addStatus) { //Si se agrego la columna de status, las demas se recorren
                            arrayIndex++;
                        }
                        if (propListItem != null && property != null) {
                            String[] heading = {
                                property.getName(),
                                propertyValue
                            };
                            headingsArray.add(heading);
                            headings2Show.put(new Integer(arrayIndex), heading);
                        }
                    }
                }
                // Coloca cada columna en el orden guardado
                Entry thisHeading1 = headings2Show.firstEntry();
                while (thisHeading1 != null) {
                    Integer thisKey = (Integer) thisHeading1.getKey();
                    String[] heading = (String[]) thisHeading1.getValue();
                    thisHeading1 = headings2Show.higherEntry(thisKey);
                    if (!addStatus) {
                        output.append("<td>" + heading[1] + "</td>");
                    }
                    if (addStatus && thisKey > 0) {
                        output.append("<td>" + heading[1] + "</td>");
                    }

                }
                output.append("</tr>");
            }

            output.append("</table>");
            output.append("</body>");
            output.append("</html>");
        }
        toReturn.append(hd).append(output);
        return (toReturn);
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
        Template template = paramRequest.getWebPage().getTemplateRef().getTemplate();
        String filePath = SWBPortal.getWorkPath()
                + template.getWorkPath() + "/" + template.getActualVersion().getVersionNumber() + "/"
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
                    SummaryViewManager.log.error("Al parsear la plantilla , "
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
     * Muestra la interface por la que el usuario puede especificar la clase de
     * objetos que se desplegar&aacute;n en la vista de la aplicaci&oacute;n o
     * aquella en la que se administran las instancias de la clase especificada.
     * La clase de objetos a administrar solo puede especificarse una vez y a
     * apartir de ese momento se puede administrar las instancias de la clase
     * seleccionada.
     *
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @param paramRequest objeto por el que se accede a varios exclusivos de
     * SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        boolean workClassIsValid = false;
        if (this.getWorkClass() != null) {
            SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();
            workClassIsValid = semWorkClass.getRootClass().equals(BSCElement.bsc_BSCElement);
        }
        if (workClassIsValid) {
            //Una vez que se especifica la clase de objetos, se puede administrar las instancias
            doViewsList(request, response, paramRequest);
        } else {
            super.doAdmin(request, response, paramRequest);
        }
    }

    /**
     * Muestra la interface que permite al usuario administrar las vistas
     * resumen.
     *
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @param paramRequest objeto por el que se accede a varios exclusivos de
     * SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    public void doShowForm(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(512);
        String viewUri = request.getParameter("viewUri") != null
                ? request.getParameter("viewUri") : "";
        String suri = request.getParameter("suri");
        String operation = request.getParameter("operation") == null
                ? "add" : request.getParameter("operation");
        String statusMsg = request.getParameter("statusMsg");
        StringBuilder baseListHtml = new StringBuilder(256);
        StringBuilder viewListHtml = new StringBuilder(256);

        SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();

        //Poner validacion de clase a utilizar, debe ser descendiente de BSCElement
        String lang = paramRequest.getUser().getLanguage();
        ArrayList<SemanticProperty> propsList = (ArrayList<SemanticProperty>) SWBUtils.Collections.copyIterator(semWorkClass.listProperties());
        Collections.sort(propsList, new PropertiesComparator());
        Iterator<SemanticProperty> basePropertiesList = propsList.iterator();
        SWBFormMgr formMgr = null;
        String modeUsed = null;
        SWBResourceURL url = paramRequest.getActionUrl();
        SummaryView viewSemObject = !viewUri.isEmpty()
                ? (SummaryView) SemanticObject.getSemanticObject(viewUri).createGenericInstance()
                : null;
        short propsInObject = (short) propsList.size();

        if (operation.equals("edit")) {
            if (viewSemObject == null) {
                throw new SWBResourceException("View URI is null while editing");
            }

            modeUsed = SWBFormMgr.MODE_EDIT;
            formMgr = new SWBFormMgr(SummaryView.bsc_SummaryView, viewSemObject.getSemanticObject(), modeUsed);
            url.setAction("editView");

            if (semWorkClass != null) {
                GenericIterator<PropertyListItem> viewPropertiesList = viewSemObject.listPropertyListItems();
                HashMap<Integer, String> selectedOptions = new HashMap<Integer, String>(16);

                if (viewPropertiesList != null) {
                    while (viewPropertiesList.hasNext()) {
                        PropertyListItem listItem = viewPropertiesList.next();
                        SemanticProperty elementPropertySO = listItem.getElementProperty().transformToSemanticProperty();
                        if (listItem != null && elementPropertySO != null) {
                            selectedOptions.put(listItem.getPropertyOrder(),
                                    elementPropertySO.getURI() + "|"
                                    + elementPropertySO.getDisplayName(lang));
                        }
                    }
                }

                //generar un listado de HTML con las propiedades en baseList menos las de viewList
                while (basePropertiesList.hasNext()) {
                    SemanticProperty prop = basePropertiesList.next();
                    if ((prop.getDisplayProperty() != null || displayElementExists(prop))
                            && !selectedOptions.containsValue(prop.getURI() + "|" + prop.getDisplayName(lang))) {

                        //generar HTML de la opcion
                        baseListHtml.append("                        <option value=\"");
                        baseListHtml.append(prop.getURI());
                        baseListHtml.append("\">");
                        baseListHtml.append(prop.getDisplayName(lang));
                        baseListHtml.append("</option>\n");
                    }
                }
                //y otro con las de viewList
                for (int i = 0; i < selectedOptions.size(); i++) {
                    String optionData = selectedOptions.get(i);
                    if (optionData != null) {
                        viewListHtml.append("                        <option value=\"");
                        viewListHtml.append(optionData.indexOf("|") > 0
                                ? optionData.substring(0, optionData.indexOf("|"))
                                : optionData);
                        viewListHtml.append("\">");
                        viewListHtml.append(optionData.indexOf("|") > 0
                                ? optionData.substring(optionData.indexOf("|") + 1)
                                : optionData);
                        viewListHtml.append("</option>\n");
                    }
                }
            }

        } else if (operation.equals("add")) {
            modeUsed = SWBFormMgr.MODE_CREATE;
            formMgr = new SWBFormMgr(SummaryView.sclass, null, modeUsed);
            url.setAction("addView");

            //obtener valores de propiedades del tipo de elemento (BSCElement) a usar
            while (basePropertiesList.hasNext()) {
                SemanticProperty prop = basePropertiesList.next();
                if (prop.getDisplayProperty() != null || displayElementExists(prop)) {
                    //generar HTML de la opcion
                    baseListHtml.append("                        <option value=\"");
                    baseListHtml.append(prop.getURI());
                    baseListHtml.append("\">");
                    baseListHtml.append(prop.getDisplayName(lang));
                    baseListHtml.append("</option>\n");
                }
            }
        }
        output.append("<script type=\"text/javascript\">\n");
        output.append("  dojo.require('dojo.parser');\n");
        output.append("  dojo.require('dojox.layout.ContentPane');\n");
        output.append("  dojo.require('dijit.form.Form');\n");
        output.append("  dojo.require('dijit.form.Button');\n");
        output.append("  dojo.require('dijit.form.MultiSelect');\n");
        output.append("</script>\n");

        output.append("        <div>\n");
        output.append("            <form dojoType=\"dijit.form.Form\" name=\"vistaForm");
        output.append(this.getId());
        output.append("\" ");
        output.append("id=\"vistaForm");
        output.append(this.getId());
        output.append("\" class=\"swbform\" onsubmit=\"submitForm('vistaForm");
        output.append(this.getId());
        output.append("');return false;\" method=\"post\" action=\"");
        output.append(url.toString());
        output.append("\">\n");
        output.append("                <input type=\"hidden\" id=\"propsIn2Save");
        output.append(this.getId());
        output.append("\" name=\"propsIn2Save\" value=\"\">\n");
        output.append("                <input type=\"hidden\" name=\"suri\" value=\"");
        output.append(suri);
        output.append("\">\n");
        if (operation.equals("edit")) {
            output.append("                <input type=\"hidden\" id=\"svUri");
            output.append(this.getId());
            output.append("\" name=\"svUri\" value=\"" + viewUri + "\">\n");
        }
        output.append("<fieldset>\n");
        output.append("    <legend>");
        output.append(paramRequest.getLocaleString("lbl_pageTitle"));
        output.append("</legend>\n");
        output.append("    <table>\n");
        output.append("        <tbody>\n");
        output.append("            <tr>\n");
        output.append("                <td width=\"200px\" align=\"right\">\n");
        output.append("                    ");
        output.append(formMgr.renderLabel(request, SummaryView.swb_title, SummaryView.swb_title.getName(), modeUsed));
        output.append("                </td>\n");
        output.append("                <td>\n");
        output.append("                    ");
        if (operation.equals("edit") && viewSemObject != null) {
            output.append(
                    formMgr.getFormElement(SummaryView.swb_title).renderElement(
                    request, viewSemObject.getSemanticObject(), SummaryView.swb_title,
                    SummaryView.swb_title.getName(), "dojo", modeUsed, lang));
        } else if (operation.equals("add")) {
            output.append(
                    formMgr.getFormElement(SummaryView.swb_title).renderElement(
                    request, null, SummaryView.swb_title, SummaryView.swb_title.getName(),
                    "dojo", modeUsed, lang));
        }
        output.append("                </td>\n");
        output.append("            </tr>\n");
        output.append("            <tr>\n");
        output.append("                <td width=\"200px\" align=\"right\">\n");
        output.append("                    ");
        output.append(formMgr.renderLabel(request, SummaryView.swb_description, modeUsed));
        output.append("                </td>\n");
        output.append("                <td>\n");
        output.append("                    ");
        if (operation.equals("edit") && viewSemObject != null) {
            output.append(
                    formMgr.getFormElement(SummaryView.swb_description).renderElement(
                    request, viewSemObject.getSemanticObject(), SummaryView.swb_description,
                    SummaryView.swb_description.getName(), "dojo", modeUsed, lang));
        } else if (operation.equals("add")) {
            output.append(
                    formMgr.getFormElement(SummaryView.swb_description).renderElement(
                    request, null, SummaryView.swb_description, SummaryView.swb_description.getName(),
                    "dojo", modeUsed, lang));
        }
        output.append("                </td>\n");
        output.append("            </tr>\n");
        output.append("            <tr>\n");
        output.append("                <td width=\"200px\" align=\"right\">\n");
        output.append("                    &nbsp;\n");
        output.append("                </td>\n");
        output.append("                <td>\n");
        output.append("                <div>\n");
        output.append("                <div style=\"float:left;\">");
        output.append("                    <span>");
        output.append(paramRequest.getLocaleString("lbl_sourceList"));
        output.append("</span><br>\n");
        output.append("                    <select id=\"baseList");
        output.append(this.getId());
        output.append("\" dojoType=\"dijit.form.MultiSelect\" size=\"");
        output.append(propsInObject > 10 ? "20" : "10");
        output.append("\" style=\"min-width:150px;\">\n");
        output.append(baseListHtml);
        output.append("                    </select>\n");
        output.append("                </div>\n");
        output.append("                <div style=\"float:left;\">");
        output.append("                    <p>&nbsp;</p>");
        output.append("                    <span dojoType='dijit.form.Button'>\n");
        output.append("                        ->");
        output.append("                       <script type=\"dojo/method\" event='onClick' args='evt'>\n");
        output.append("                            var base = document.getElementById(\"baseList");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                            var view = document.getElementById(\"viewList");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                            \n");
        output.append("                            if (base.selectedIndex > -1) {\n");
        output.append("                                var i = 0;\n");
        output.append("                                while (i < base.options.length) {\n");
        output.append("                                    if (base.options[i].selected) {\n");
        output.append("                                        var selectedOption = base.options[i];\n");
        output.append("                                        view.add(selectedOption);\n");
        output.append("                                    } else {\n");
        output.append("                                        i++;\n");
        output.append("                                    }\n");
        output.append("                                }\n");
        output.append("                            }\n");
        output.append("                       </script>\n");
        output.append("                    </span>");
        output.append("                    <br>");
        output.append("                    <span dojoType=\"dijit.form.Button\">\n");
        output.append("                        <-\n");
        output.append("                        <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
        output.append("                            var base = document.getElementById(\"baseList");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                            var view = document.getElementById(\"viewList");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                            ");
        output.append("                            if (view.selectedIndex > -1) {\n");
        output.append("                                var i = 0;\n");
        output.append("                                while (i < view.options.length) {\n");
        output.append("                                    if (view.options[i].selected) {\n");
        output.append("                                        var selectedOption = view.options[i];\n");
        output.append("                                        base.add(selectedOption);\n");
        output.append("                                    } else {\n");
        output.append("                                        i++;\n");
        output.append("                                    }\n");
        output.append("                                }\n");
        output.append("                            }\n");
        output.append("                        </script>\n");
        output.append("                    </span>\n");
        output.append("                </div>\n");
        output.append("                <div style=\"float:left;\">\n");
        output.append("                    <span>");
        output.append(paramRequest.getLocaleString("lbl_inViewList"));
        output.append("</span><br>\n");
        output.append("                    <select id=\"viewList");
        output.append(this.getId());
        output.append("\" dojoType=\"dijit.form.MultiSelect\" name=\"viewList\" size=\"");
        output.append(propsInObject > 10 ? "20" : "10");
        output.append("\" style=\"min-width:150px;\">\n");
        if (operation.equals("edit") && viewListHtml.length() > 0) {
            output.append(viewListHtml);
        }
        output.append("                    </select>\n");
        output.append("                </div>\n");
        output.append("                <div style=\"float:left;\">\n");
        output.append("                    <p>&nbsp;</p>\n");
        output.append("                    <span dojoType=\"dijit.form.Button\">\n");
        output.append("                        ");
        output.append(paramRequest.getLocaleString("lbl_elementUp"));
        output.append("                        <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
        output.append("                            var view = document.getElementById(\"viewList");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                            if (view.options.length > 0) {\n");
        output.append("                                var itemsSelected = 0;\n");
        output.append("                                for (var i = 0; i < view.options.length; i++) {\n");
        output.append("                                    if (view.options[i].selected) {\n");
        output.append("                                        itemsSelected++;\n");
        output.append("                                    }\n");
        output.append("                                }\n");
        output.append("                                if (itemsSelected == 1) {\n");
        output.append("                                    var oldIndex = view.selectedIndex;\n");
        output.append("                                    if (oldIndex > 0) {\n");
        output.append("                                        var optionMoved = new Option(view.options[oldIndex].text, view.options[oldIndex].value, false, true);\n");
        //crear una nueva coleccion de options con el nuevo orden
        output.append("                                        var newOptions = new Array();\n");
        output.append("                                        for (var i = 0; i < view.options.length; i++) {\n");
        output.append("                                            var optionToAdd = new Option(view.options[i].text, view.options[i].value, false, false);\n");
        output.append("                                            if (i == oldIndex - 1) {\n");
        output.append("                                                newOptions.push(optionMoved);\n");
        output.append("                                            }\n");
        output.append("                                            if (i != oldIndex) {\n");
        output.append("                                                newOptions.push(optionToAdd);\n");
        output.append("                                            }\n");
        output.append("                                        }\n");
        //se eliminan los elementos del select
        output.append("                                        while (view.options.length > 0) {\n");
        output.append("                                            view.remove(view.options.length - 1);\n");
        output.append("                                        }\n");
        //se agregan los elementos ordenados
        output.append("                                        for (var i = 0; i < newOptions.length; i++) {\n");
        output.append("                                            try {\n");
        // for IE earlier than version 8
        output.append("                                                view.add(newOptions[i], view.options[null]);\n");
        output.append("                                            } catch (e) {\n");
        output.append("                                                view.add(newOptions[i], null);\n");
        output.append("                                            }\n");
        output.append("                                        }\n");
        output.append("                                    }\n");
        output.append("                                } else {\n");
        output.append("                                    alert(\"");
        output.append(paramRequest.getLocaleString("msg_moreThanOneSelected"));
        output.append("\");\n");
        output.append("                                }\n");
        output.append("                            }\n");
        output.append("                        </script>\n");
        output.append("                    </span>\n");
        output.append("                    <br>\n");
        output.append("                    <span dojoType=\"dijit.form.Button\">\n");
        output.append("                        ");
        output.append(paramRequest.getLocaleString("lbl_elementDown"));
        output.append("                        <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
        output.append("                            var view = document.getElementById(\"viewList");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                            if (view.options.length > 0) {\n");
        output.append("                                var itemsSelected = 0;\n");
        output.append("                                for (var i = 0; i < view.options.length; i++) {\n");
        output.append("                                    if (view.options[i].selected) {\n");
        output.append("                                        itemsSelected++;\n");
        output.append("                                    }\n");
        output.append("                                }\n");
        output.append("                                if (itemsSelected == 1) {\n");
        output.append("                                    var oldIndex = view.selectedIndex;\n");
        output.append("                                    if (oldIndex < view.options.length - 1) {\n");
        output.append("                                        var optionMoved = new Option(");
        output.append("view.options[oldIndex].text, view.options[oldIndex].value, false, true);\n");
        //crear una nueva coleccion de options con el nuevo orden
        output.append("                                        var newOptions = new Array();\n");
        output.append("                                        for (var i = 0; i < view.options.length; i++) {\n");
        output.append("                                            var optionToAdd = new Option(");
        output.append("view.options[i].text, view.options[i].value, false, false);\n");
        output.append("                                            if (i != oldIndex) {\n");
        output.append("                                                newOptions.push(optionToAdd);\n");
        output.append("                                            }\n");
        output.append("                                            if (i == oldIndex + 1) {\n");
        output.append("                                                newOptions.push(optionMoved);\n");
        output.append("                                            }\n");
        output.append("                                        }\n");
        //se eliminan los elementos del select
        output.append("                                        while (view.options.length > 0) {\n");
        output.append("                                            view.remove(view.options.length - 1);\n");
        output.append("                                        }\n");
        //se agregan los elementos ordenados
        output.append("                                        for (var i = 0; i < newOptions.length; i++) {\n");
        output.append("                                            try {\n");
        // for IE earlier than version 8
        output.append("                                                view.add(newOptions[i], view.options[null]);\n");
        output.append("                                            } catch (e) {\n");
        output.append("                                                view.add(newOptions[i], null);\n");
        output.append("                                            }\n");
        output.append("                                        }\n");
        output.append("                                    }\n");
        output.append("                                } else {\n");
        output.append("                                    alert(\"");
        output.append(paramRequest.getLocaleString("msg_moreThanOneSelected"));
        output.append("\");\n");
        output.append("                                }\n");
        output.append("                            }\n");
        output.append("                        </script>\n");
        output.append("                    </span>\n");
        output.append("                </div>\n");
        output.append("                </div>\n");
        output.append("                </td>\n");
        output.append("            </tr>\n");
        output.append("        </tbody>\n");
        output.append("    </table>\n");
        output.append("</fieldset>\n");
        //despliegue de botones
        output.append("<fieldset>\n");
        output.append("    <button dojoType=\"dijit.form.Button\" id=\"btnSave");
        output.append(this.getId());
        output.append("\" type=\"button\">");
        output.append(paramRequest.getLocaleString("lbl_btnSubmit"));
        output.append("                            <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
        output.append("                                var view = document.getElementById(\"viewList");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                                var propsIn2Save = document.getElementById(\"propsIn2Save");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                                var form2Send = document.getElementById(\"vistaForm");
        output.append(this.getId());
        output.append("\");\n");
        output.append("                                if (view.options.length > 0) {\n");
        output.append("                                    var propsUri = \"\";\n");
        output.append("                                    for (var i = 0; i < view.options.length; i++) {\n");
        output.append("                                        if (i > 0) {\n");
        output.append("                                            propsUri += \"|\";\n");
        output.append("                                        }\n");
        output.append("                                        propsUri += view.options[i].value;\n");
        output.append("                                    }\n");
        output.append("                                    propsIn2Save.value = propsUri;\n");
        output.append("                                    \n");
        output.append("                                }\n");
        output.append("                                if (propsIn2Save.value.length > 0 && form2Send.title.value != \"\") {\n");
        output.append("                                    submitForm(form2Send.id);\n");
        output.append("                                } else if (form2Send.title.value == \"\") {\n");
        output.append("                                    alert(\"");
        output.append(paramRequest.getLocaleString("msg_noTitleEntered"));
        output.append("\");\n");
        output.append("                                } else if (propsIn2Save.value.length == 0) {\n");
        output.append("                                    alert(\"");
        output.append(paramRequest.getLocaleString("msg_noPropsSelected"));
        output.append("\");\n");
        output.append("                                }\n");
        output.append("                                return false;\n");
        output.append("                            </script>\n");
        output.append("    </button>\n");
        SWBResourceURL urlCancel = paramRequest.getRenderUrl();
        urlCancel.setMode(SWBResourceURLImp.Mode_ADMIN);
        urlCancel.setParameter("operation", "add");
        output.append("    <button dojoType=\"dijit.form.Button\" ");
        output.append("type=\"button\" onClick=\"reloadTab('");
        output.append(this.getResource().getURI());
        output.append("');\">\n");
        output.append(paramRequest.getLocaleString("lbl_btnCancel"));
        output.append("    </button>\n");
        output.append("</fieldset>\n");
        output.append("            </form>\n");
        output.append("        </div>\n");

        //Muestra mensaje sobre resultado de la operacion realizada
        if (statusMsg != null && !statusMsg.isEmpty()) {
            statusMsg = paramRequest.getLocaleString(statusMsg);
            output.append("<div dojoType=\"dojox.layout.ContentPane\">\n");
            output.append("    <script type=\"dojo/method\">\n");
            output.append("        showStatus('" + statusMsg + "');\n");
            output.append("    </script>\n");
            output.append("</div>\n");
        }

        out.println(output);
    }

    /**
     * Realiza operaciones con la informaci&oacute;n de manera segura evitando
     * la ejecuci&oacute;n de una misma operaci&oacute;n m&aacute;s de una vez,
     * teniendo en cuenta el estado de la aplicaci&oacute;n.
     *
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response)
            throws SWBResourceException, IOException {

        String action = response.getAction();
        String title = request.getParameter("title");
        String descrip = request.getParameter("description");
        String propertyUries = request.getParameter("propsIn2Save") != null
                ? request.getParameter("propsIn2Save")
                : "";
        String summaryViewUri = request.getParameter("svUri");
        String statusMsg = null;
        String lang = response.getUser().getLanguage();
        SummaryView summaryView = null;
        BSC bscModel = (BSC) this.getResource().getWebSite();
        boolean listingRedirect = false;
        boolean formRedirect = false;

        if (!"addView".equals(action)) {
            summaryView = SemanticObject.getSemanticObject(summaryViewUri) != null
                    ? ((SummaryView) SemanticObject.getSemanticObject(
                    summaryViewUri).createGenericInstance())
                    : null;
        }
        if ("addView".equals(action)
                || "editView".equals(action)) {

            SemanticOntology ontology = SWBPlatform.getSemanticMgr().getOntology();
            String[] uries = propertyUries.split("\\|");

            if ("addView".equals(action)) {
                SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();

                if (semWorkClass != null) {
                    summaryView = SummaryView.ClassMgr.createSummaryView(bscModel);
                    summaryView.setObjectsType(semWorkClass.getURI());
                    statusMsg = "msg_ViewCreated";
                }
            } else if ("editView".equals(action)) {

                if (summaryView != null && uries.length > 0) {
                    removeAllPropertyListItem(summaryView);
                }
                statusMsg = "msg_ViewUpdated";
            }
            if (summaryView != null && !propertyUries.isEmpty()) {

                for (int i = 0; i < uries.length; i++) {
                    //Se crean instancias de PropertyListItem con los uries y el orden seleccionado, se almacenan en summaryView
                    SemanticObject propSO = SemanticObject.getSemanticObject(uries[i]);
                    SemanticProperty prop = ontology.getSemanticProperty(uries[i]);

                    if (prop == null) {
                        prop = propSO.transformToSemanticProperty();
                    }
                    if (prop != null) {
                        PropertyListItem property = PropertyListItem.ClassMgr.createPropertyListItem(bscModel);
                        property.setPropertyOrder(i);
                        property.setElementProperty(prop.getSemanticObject());
                        summaryView.addPropertyListItem(property);
                    }
                }
                //Se actualiza informacion de Traceable
                SWBPortal.getServiceMgr().updateTraceable(summaryView.getSemanticObject(), response.getUser());
                summaryView.setTitle(title, lang);
                summaryView.setTitle(title);
                summaryView.setDescription(descrip, lang);
                summaryView.setDescription(descrip);
                response.setRenderParameter("viewUri", summaryView.getURI());
                response.setRenderParameter("operation", "edit");
            }
            formRedirect = true;

        } else if ("deleteView".equals(action)) {

            if (summaryView != null) {
                if (this.getActiveView() != null && this.getActiveView() == summaryView) {
                    this.setActiveView(null);
                }
                removeAllPropertyListItem(summaryView);
                SummaryView.ClassMgr.removeSummaryView(summaryView.getId(), bscModel);
                statusMsg = "msg_ViewDeleted";
            }
            listingRedirect = true;

        } else if (action.equalsIgnoreCase("makeActive") && summaryView != null) {
            //asignar a SummaryViewAdm la instancia de SummaryView correspondiente al uri recibido en request
            this.setActiveView(summaryView);
            statusMsg = "msg_ContentViewAssigned";
            listingRedirect = true;
        } else {
            super.processAction(request, response);
        }

        if (statusMsg != null) {
            response.setRenderParameter("statusMsg", statusMsg);
        }
        if (listingRedirect) {
            response.setMode("viewsList");
        } else if (formRedirect) {
            response.setMode("showForm");
        }
    }

    /**
     * Elimina todos los elementos PropertyListItem asociados al objeto
     * summaryView proporcionado
     *
     * @param summaryView la vista resumen a la que se le eliminar&aacute;n los
     * elementos PropertyListItem relacionados
     */
    private void removeAllPropertyListItem(SummaryView summaryView) {

        GenericIterator<PropertyListItem> listItems = summaryView.listPropertyListItems();
        if (listItems != null && listItems.hasNext()) {
            while (listItems.hasNext()) {
                PropertyListItem item = listItems.next();
                summaryView.removePropertyListItem(item);
            }
        }
    }

    /**
     * Genera el c&oacute;digo HTML del listado de vistas resumen disponibles a
     * administrar
     *
     * @param request la petici&oacute;n HTTP enviada por el cliente
     * @param response la respuesta HTTP que se enviar&aacute; al cliente
     * @param paramRequest objeto por el que se accede a varios exclusivos de
     * SWB
     * @throws SWBResourceException si se presenta alg&uacute;n problema dentro
     * de la plataforma de SWB para la correcta ejecuci&oacute;n del
     * m&eacute;todo. Como la extracci&oacute;n de valores para
     * par&aacute;metros de i18n.
     * @throws IOException si ocurre un problema con la lectura/escritura de la
     * petici&oacute;n/respuesta.
     */
    private void doViewsList(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder listCode = new StringBuilder(256);
        String lang = paramRequest.getUser().getLanguage();
        BSC bsc = (BSC) this.getResource().getWebSite();
        SemanticClass workClassSC = this.getWorkClass() != null
                ? this.getWorkClass().transformToSemanticClass()
                : null;
        String objectsType = workClassSC != null
                ? workClassSC.getURI() : null;
        String statusMsg = request.getParameter("statusMsg");
        String statusErr = request.getParameter("statusErr");

        if (bsc != null && objectsType != null) {
            SWBResourceURL urlCreate = paramRequest.getRenderUrl();
            urlCreate.setParameter("operation", "add");
            urlCreate.setMode("showForm");
            Iterator listOfViews = SummaryView.ClassMgr.listSummaryViews(bsc);

            if (listOfViews != null && listOfViews.hasNext()) {
                ArrayList<SummaryView> viewsList = new ArrayList<SummaryView>(32);
                while (listOfViews.hasNext()) {
                    SummaryView view2Include = (SummaryView) listOfViews.next();
                    if (view2Include.getObjectsType() != null
                            && view2Include.getObjectsType().equals(objectsType)) {
                        viewsList.add(view2Include);
                    }
                }
                listOfViews = viewsList.iterator();
            }
            if (listOfViews != null && listOfViews.hasNext()) {
                listOfViews = SWBComparator.sortByDisplayName(listOfViews, lang);
            }
            listCode.append("<div>");
            if (this.getActiveView() == null) {
                listCode.append(paramRequest.getLocaleString("msg_noActiveView"));
            }
            listCode.append("</div>");
            listCode.append("    <form dojoType=\"dijit.form.Form\" name=\"listViewsForm");
            listCode.append(this.getId());
            listCode.append("\" id=\"listViewsForm");
            listCode.append(this.getId());
            listCode.append("\" class=\"swbform\" method=\"post\">\n");
            listCode.append("<fieldset>\n");
            listCode.append("    <legend>");
            listCode.append(paramRequest.getLocaleString("lbl_listingCaption"));
            listCode.append("</legend>\n");
            listCode.append("    <div>");
            listCode.append(paramRequest.getLocaleString("lbl_objectsType") + workClassSC.getName());
            listCode.append("</div>\n");
            listCode.append("<table width=\"60%\" border=\"0\" align=\"left\" cellpadding=\"1\" cellspacing=\"1\">\n");
            listCode.append("    <thead>\n");
            listCode.append("        <tr>\n");
            listCode.append("          <th>");
            listCode.append(paramRequest.getLocaleString("th_action"));
            listCode.append("</th>\n");
            listCode.append("            <th scope=\"col\" align=\"center\">\n");
            listCode.append(paramRequest.getLocaleString("lbl_listingTitle1"));
            listCode.append("</th>\n");
            listCode.append("          <th>");
            listCode.append(SummaryView.swb_modifiedBy.getDisplayName(lang));
            listCode.append("</th>\n");
            listCode.append("          <th>");
            listCode.append(SummaryView.swb_updated.getDisplayName(lang));
            listCode.append("</th>\n");
            listCode.append("            <th scope=\"col\" colspan=\"3\" align=\"center\">\n");
            listCode.append(paramRequest.getLocaleString("lbl_listingOperations"));
            listCode.append("</th>\n");
            listCode.append("        </tr>\n");
            listCode.append("    </thead>\n");
            listCode.append("    <tbody>\n");

            //Se agrega al listado cada vista creada
            while (listOfViews.hasNext()) {
                SummaryView view = (SummaryView) listOfViews.next();

                SWBResourceURL urlEdit = paramRequest.getRenderUrl();
                urlEdit.setParameter("operation", "edit");
                urlEdit.setParameter("viewUri", view.getURI());
                urlEdit.setMode("showForm");

                SWBResourceURL urlDelete = paramRequest.getActionUrl();
                urlDelete.setAction("deleteView");
                urlDelete.setParameter("svUri", view.getURI());

                SWBResourceURL urlMakeActive = paramRequest.getActionUrl();
                urlMakeActive.setAction("makeActive");
                urlMakeActive.setParameter("svUri", view.getURI());

                listCode.append("        <tr>");
                //HTML para las opciones de edicion y eliminacion
                listCode.append("          <td>\n");
                listCode.append("            <a href=\"void(0);\" title=\"");
                listCode.append(paramRequest.getLocaleString("lbl_lnkEdit"));
                listCode.append("\" onclick=\"submitUrl('");
                listCode.append(urlEdit);
                listCode.append("', this);return false;\"><img src=\"");
                listCode.append(SWBPlatform.getContextPath());
                listCode.append("/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"");
                listCode.append(paramRequest.getLocaleString("lbl_lnkEdit"));
                listCode.append("\"></a>\n");
                listCode.append("            <a href=\"void(0);\" title=\"");
                listCode.append(paramRequest.getLocaleString("lbl_lnkDelete"));
                listCode.append("\" onclick=\"if (confirm('");
                listCode.append(paramRequest.getLocaleString("msg_confirmRemove"));
                listCode.append(SWBUtils.TEXT.scape4Script(view.getTitle() != null ? view.getTitle() : "xxx"));
                listCode.append("?')){submitUrl('");
                listCode.append(urlDelete);
                listCode.append("', this);}return false;\"><img src=\"");
                listCode.append(SWBPlatform.getContextPath());
                listCode.append("/swbadmin/images/delete.gif\" border=\"0\" alt=\"");
                listCode.append(paramRequest.getLocaleString("lbl_lnkDelete"));
                listCode.append("\"></a>\n");
                listCode.append("          </td>\n");
                listCode.append("          <td>");
                listCode.append("            <a href=\"void(0);\" title=\"");
                listCode.append(paramRequest.getLocaleString("lbl_lnkEdit"));
                listCode.append("\" onclick=\"submitUrl('");
                listCode.append(urlEdit);
                listCode.append("', this);return false;\">");
                listCode.append(view.getTitle() != null ? view.getTitle() : "xxx");
                listCode.append("</a>\n");
                listCode.append("          </td>\n");
                listCode.append("          <td>");
                listCode.append(view.getModifiedBy() != null ? view.getModifiedBy().getFullName() : "xxx");
                listCode.append("          </td>\n");
                listCode.append("          <td>");
                listCode.append(view.getUpdated() != null ? view.getUpdated() : "xxx");
                listCode.append("          </td>\n");
                listCode.append("          <td align=\"center\">");

                if (this.getActiveView() == null || (this.getActiveView() != null
                        && !this.getActiveView().getURI().equals(view.getURI()))) {
                    //Codigo HTML para asignacion como contenido
                    listCode.append("          <input type=\"radio\" dojoType=\"dijit.form.RadioButton\" ");
                    listCode.append("name=\"asContent\" id=\"asContent");
                    listCode.append(view.getId());
                    listCode.append("\" value=\"");
                    listCode.append(view.getId());
                    listCode.append("\" onclick=\"submitUrl('");
                    listCode.append(urlMakeActive);
                    listCode.append("', this.domNode);return false;\">");
                } else if (this.getActiveView() != null
                        && this.getActiveView().getURI().equals(view.getURI())) {
                    listCode.append("          <input type=\"radio\" dojoType=\"dijit.form.RadioButton\" ");
                    listCode.append("name=\"asContent\" id=\"asContent");
                    listCode.append(view.getId());
                    listCode.append("\" value=\"");
                    listCode.append(view.getId());
                    listCode.append("\" checked=\"checked\" onclick=\"submitUrl('");
                    listCode.append(urlMakeActive);
                    listCode.append("', this.domNode);return false;\">");
                }
                listCode.append("          </td>\n");
                listCode.append("        </tr>");
            }
            listCode.append("    </tbody>");
            listCode.append("</table>");
            listCode.append("</fieldset>\n");
            listCode.append("  <fieldset>\n");
            listCode.append("    <button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('");
            listCode.append(urlCreate);
            listCode.append("', this.domNode); return false;\">");
            listCode.append(paramRequest.getLocaleString("btn_add"));
            listCode.append("</button>\n");
            listCode.append("  </fieldset>\n");
            listCode.append("    </form>");
            listCode.append("");
        }

        //Muestra mensaje sobre resultado de la operacion realizada
        if ((statusMsg != null && !statusMsg.isEmpty()) || (statusErr != null && !statusErr.isEmpty())) {
            listCode.append("<div dojoType=\"dojox.layout.ContentPane\">\n");
            listCode.append("    <script type=\"dojo/method\">\n");
            if (statusMsg != null) {
                statusMsg = paramRequest.getLocaleString(statusMsg);
                listCode.append("        showStatus('" + statusMsg + "');\n");
            } else if (statusErr != null) {
                statusErr = paramRequest.getLocaleString(statusErr);
                listCode.append("        showError('" + statusErr + "');\n");
            }
            listCode.append("    </script>\n");
            listCode.append("</div>\n");
        }

        out.println(listCode.toString());
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        switch (paramRequest.getMode()) {
            case "showForm":
                doShowForm(request, response, paramRequest);
                break;
            case "viewsList":
                doViewsList(request, response, paramRequest);
                break;
            case Mode_StreamPDF:
                doGetPDFDocument(request, response, paramRequest);
                break;
            default:
                super.processRequest(request, response, paramRequest);
                break;
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

        String ret = null;
        SWBFormMgr formMgr = new SWBFormMgr(elementBSC, null, SWBFormMgr.MODE_VIEW);
        SemanticProperty semProp = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propUri);
        //Codigo prueba para obtener el displayElement
        Statement st = semProp.getRDFProperty().getProperty(SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty("http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
        if (st != null) {
            //Se obtiene: SemanticProperty: displayElement de la propiedad en cuestion (prop)
            SemanticObject soDisplayElement = SemanticObject.createSemanticObject(st.getResource());
            if (soDisplayElement != null) {
                SemanticObject formElement = soDisplayElement.getObjectProperty(org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement"));
                if (formElement != null) {
                    FormElement fe = (FormElement) formElement.createGenericInstance();
                    if (fe != null) {
                        if (formMgr.getSemanticObject() != null) {
                            fe.setModel(formMgr.getSemanticObject().getModel());
                        }
                        ret = fe.renderElement(request, elementBSC, semProp, semProp.getName(), SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
                    }
                }
            }
        }

        if (ret == null) {
            FormElement formElement = formMgr.getFormElement(semProp);
            if (formElement != null) {
                ret = formElement.renderElement(request, elementBSC, semProp, semProp.getName(), SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
            }
        }

        return ret != null ? ret : "";
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
     * Recorre los recursos y evalua aquel que contengan los atributos
     * PDFExportable.viewType con valor PDFExportable.VIEW_Summary y
     * PDFExportable.bsc_itemType con valor el nombre de la clase del elemento
     * BSC al que pertenezca.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con la liga
     * y el icono correspondiente al elemento a exportar.
     * @throws SWBResourceException SWBResourceException Excepti&oacute;n
     * utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public String doIconExportPDF(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuilder ret = new StringBuilder();
//        Resource base2 = getResource();
//        String icon = "";

//        if (base2 != null) {
            /*SWBResourceURL url = new SWBResourceURLImp(request, getResource(), paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
            url.setMode(Mode_PDF);
            url.setCallMethod(SWBResourceURL.Call_DIRECT);
            String surl = url.toString();*/
            SWBResourceURL url = new SWBResourceURLImp(request, getResourceBase(), paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
            url.setMode(Mode_StreamPDF);
            url.setCallMethod(SWBResourceURL.Call_DIRECT);

//            String webWorkPath = SWBPlatform.getContextPath() + "/swbadmin/icons/";
//            String image = "pdfOnline.jpg";
            String title = paramRequest.getLocaleString("msgPrintPDFDocument");
            ret.append("<a href=\"");
            ret.append(url);
            ret.append("\" class=\"swbstgy-toolbar-printPdf\" title=\"");
            ret.append(title);
            ret.append("\">");
            ret.append(title);
//            ret.append("<img src=\"");
//            ret.append(webWorkPath);
//            ret.append(image);
//            ret.append("\" alt=\"");
//            ret.append(alt);
//            ret.append("\" class=\"toolbar-img\" />");
            ret.append("</a>");
//            icon = toReturn.toString();
//        }
        return ret.toString();
    }
}
