/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.resources;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.process.model.*;
import org.semanticwb.process.cpanel.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;
import org.semanticwb.Logger;
import com.lowagie.text.rtf.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.awt.Color;

/**
 *
 * @author haydee.vertti
 */
public class PreConfiguredControlPanel extends GenericAdmResource{
    //INFORME PERSONALIZADO
    private static Font rtfTitleFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(255, 255, 255));
    private static Font rtfColumnTitleFont = new Font(Font.HELVETICA, 8, Font.BOLD);
    private static Font rtfCellFont = new Font(Font.HELVETICA, 8);
    private static Font pdfTitleFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(255, 255, 255));
    private static Font pdfColumnTitleFont = new Font(Font.HELVETICA, 8, Font.BOLD);
    private static Font pdfCellFont = new Font(Font.HELVETICA, 8);
    private static Font pdfFooterFont = new Font(Font.HELVETICA, 10);
    private static Color pdfHeaderTitleBackground = new Color (0, 153, 153);
    private static Color pdfHeaderColumnBackground = new Color (102, 204, 204);
    private static Color rtfHeaderTitleBackground = new Color (0, 153, 153);
    private static Color rtfHeaderColumnBackground = new Color (102, 204, 204);
    //ESTILOS
    private final static String DIV_ID_FILTERED="filtrado";
    private final static String DIV_ID_TASKS="tareas";
    private final static String DIV_ID_DOWNLOAD="descarga";
    private final static String DIV_ID_REPORT="informe";
    private final static String DIV_ID_CUSTOMIZE="personaliza";
    private final static String DIV_ID_PANEL="panel";
    private final static String DIV_ID_FILTERS="filtros";
    private final static String DIV_ID_TABS="tabs";
    private final static String DIV_ID_PLECA_PARENT="plecas-padre";
    private final static String LI_ID_FILTERED="li_filtrado";
    private final static String LI_ID_REPORT="li_informe";
    private final static String LI_ID_CUSTOMIZE="li_personaliza";
    private final static String DIV_CLASS_SWBFORM="swbform";
    private final static String DIV_CLASS_PLECA="pleca";
    private final static String DIV_CLASS_RADIOBUTTON="radios";
    private final static String P_CLASS_LEFT="izq";
    private final static String P_CLASS_CLOSED="cerradas";
    private final static String P_CLASS_RIGHT_CLOSED="der cerradas-si";
    private final static String P_CLASS_PAGINATED="paginado";
    private final static String TH_CLASS_UNORDERED="orden-no";
    private final static String LI_CLASS_TAB_ON="tab-on";
    private final static String LI_CLASS_TAB_OFF="tab-off";
    private final static String LI_CLASS_FILTER_PROCESS="f-pro";
    private final static String LI_CLASS_FILTER_STATUS="f-est";
    private final static String LI_CLASS_FILTER_INITIAL_DATE="f-ini";
    private final static String LI_CLASS_FILTER_FINAL_DATE="f-fin";
    private final static String INPUT_TEXT_CLASS="pleca_txt";
    private final static String INPUT_BUTTON_CLASS="pleca_boton";
    private final static String HREF_RTF_CLASS="rtf";
    private final static String HREF_XML_CLASS="xml";
    private final static String HREF_PDF_CLASS="pdf";
    private final static String HREF_BACK_PAGE_CLASS="pag_ant";
    private final static String HREF_NEXT_PAGE_CLASS="pag_sig";
    //private final static String HREF_CLASS_PRIORITY		//DE 0 a 5

    public final static String imgPath = SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/images/";
    public final static String filenamePdf = SWBUtils.getApplicationPath() +
            "/swbadmin/ControlPanelReport.pdf";
    public final static String strDownloadPdf  = SWBPortal.getContextPath() +
            "/swbadmin/ControlPanelReport.pdf";
    public final static String filenameXml = SWBUtils.getApplicationPath() +
            "/swbadmin/jsp/process/ControlPanelReport.xml";
    public final static String strDownloadXml  = SWBPortal.getContextPath() +
            "/swbadmin/jsp/process/ControlPanelReport.xml";
    public final static String filenameRtf = SWBUtils.getApplicationPath() +
            "/swbadmin/ControlPanelReport.rtf";
    public final static String strDownloadRtf  = SWBPortal.getContextPath() +
            "/swbadmin/ControlPanelReport.rtf";
    public final static int reportColumnNumber = 11;
    public final static int reportCellPadding = 4;
    public final static int reportHeaderPadding = 6;
    public final static int pdfTableWidthPercentage = 100;
    public final static float pdfHeaderTableHeight = 15;
    
    public final static String ALL_STATUS = Instance.STATUS_PROCESSING + "|" +
            Instance.STATUS_OPEN + "|" + Instance.STATUS_ABORTED + "|"
            + Instance.STATUS_CLOSED + "|" + Instance.STATUS_STOPED + "|";
    public final static String ALL_PRIORITY = "1|2|3|4|5|";
    public final static int DISPLAY_ON_LINK = 0;
    public final static int DISPLAY_ON_LEGEND = 1;
    public final static int DISPLAY_ON_COLUMN = 2;
	public final static String PROPERTY_TYPE_TASK_DEFINITION = "TaskDefinition";
	public final static String PROPERTY_TYPE_TASK_INSTANCE = "TaskInstance";
	public final static String PROPERTY_TYPE_ARTIFACT = "Artifact";
    public final static String PROPERTY_TYPE_FIXED_LINK_PARAMETER = "FixedParameter";

    //FILTROS
    public final static int FILTER_BY_DATE = 0;
    public final static int FILTER_BY_TITLE = 1;
    public final static int FILTER_BY_PROCESS = 2;
    public final static int FILTER_BY_STATUS = 3;
    public final static int FILTER_BY_PRIORITY = 4;
    public final static int MAX_FILTER = 5;
    public final static String ACTIVE_CLOSED_TASKS_FILTER = "1";
    //CONTROLES
    public final static String PROCESS_SELECTION_CTRL = "cpProcessDefinitions";
    public final static String TITLE_SELECTION_CTRL = "title";
    public final static String TASK_PER_PAGE_CONTROL = "cpRowsPerPage";
    public final static String TASK_LINK_FIXED_PARAMS = "cpTaskParams";
    public final static String TASK_LINK_INCLUDE_CTRL = "cpIncludeTaskLinkProperty";
    public final static String TASK_LINK_INCLUDE_ORDER_CTRL = "cpTaskLinkPropOrder";
    public final static String TASK_LEGEND_INCLUDE_CTRL = "cpIncludeTaskLegendProperty";
    public final static String TASK_LEGEND_INCLUDE_ORDER_CTRL = "cpTaskLegendPropOrder";
    public final static String TASK_COLUMN_INCLUDE_CTRL = "cpIncludeTaskColumnProperty";
    public final static String TASK_COLUMN_INCLUDE_ORDER_CTRL = "cpTaskColumnPropOrder";
    //FILTROS
    public final static String END_DATE_FILTER_CTRL = "fechaFin";
    public final static String INITIAL_DATE_FILTER_CTRL = "fechaIni";
    public final static String PROCESS_FILTER_CTRL = "cboFilterProcess";
    public final static String STATUS_FILTER_CTRL = "cboFilterStatus";
    public final static String TITLE_FILTER_CTRL = "cboFilterTaskTitle";
    public final static String PRIORITY_FILTER_CTRL = "cboPriority";
    public final static String SORTING_CTRL = "cboSortBy";
    public final static String CLOSED_TASKS_FILTER_CTRL = "hideClosedTasks";
    public final static String END_DATE_REPORT_CTRL="repFechaFin";
    public final static String INITIAL_DATE_REPORT_CTRL="repFechaIni";
    public final static String PROCESS_REPORT_CTRL = "cboReportProcess";
    public final static String STATUS_REPORT_CTRL = "cboReportStatus";
    public final static String PRIORITY_REPORT_CTRL = "cboReportPriority";
    //Configuracion por defecto
    private final static int intDefaultRowsPerPage = 3;
    private final static String strDefaultHref = PROPERTY_TYPE_TASK_DEFINITION + "|url|-2|;" + PROPERTY_TYPE_TASK_INSTANCE + "|encodedURI|-1|;";
    private final static String strDefaultLegend = PROPERTY_TYPE_TASK_DEFINITION + "|title|-2|;" + PROPERTY_TYPE_TASK_INSTANCE + "|id|-1|;";
    private final static String strDefaultColumns = PROPERTY_TYPE_TASK_INSTANCE + "|status|1|;" + PROPERTY_TYPE_TASK_INSTANCE + "|created|2|;" +
            PROPERTY_TYPE_TASK_INSTANCE + "|creator|3|;" + PROPERTY_TYPE_TASK_INSTANCE + "|modified|4|;" + PROPERTY_TYPE_TASK_INSTANCE + "|modifiedBy|5|;" +
            PROPERTY_TYPE_TASK_INSTANCE +  "|ended|6|;" + PROPERTY_TYPE_TASK_INSTANCE + "|endedBy|7|;";

    private String strHrefConfiguration;
    private String strLegendConfiguration;
    private String strColumnsConfiguration;
    private java.util.List lstTaskDefinitionProperties;
    private java.util.List lstTaskInstanceProperties;
    private static Logger log = SWBUtils.getLogger(PreConfiguredControlPanel.class);


    /**
	 * Cargar en las constantes correspondientes (strHrefConfiguration, strLegendConfiguration y strColumnsConfiguration)
     * la informacion que se encuentra en la  base del recurso.
     * La información se refiere a la composicion del vinculo, leyenda y columnas para la definicion de proceso seleccionada.
	 *
     * @param           paramsRequest SWBParamRequest
	 */
    public void loadResourceConfiguration(SWBParamRequest paramRequest)
    {
        try {
            Resource base = paramRequest.getResourceBase();
            strHrefConfiguration = strDefaultHref;
            strLegendConfiguration = strDefaultLegend;
            strColumnsConfiguration = strDefaultColumns;
            Iterator<String> itAttributes = base.getAttributeNames();
            while(itAttributes.hasNext())
            {
                String key = itAttributes.next().toString();
                if(key.contains("taskLinkProperties")){
                    strHrefConfiguration = base.getAttribute(key);
                } else if(key.contains("taskLegendProperties")){
                    strLegendConfiguration = base.getAttribute(key);
                } else if(key.contains("taskColumnProperties")){
                    strColumnsConfiguration = base.getAttribute(key);
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.loadResourceConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.loadResourceConfiguration:" + e.getMessage());
		}
    }

    /**
	 * Regresa una lista con las propiedades de definicion de la tarea que pueden seleccionarse
	 *
     * @param           paramsRequest SWBParamRequest
	 * @return          java.util.List
	 */
    public static java.util.List TaskDefinitionProperties(SWBParamRequest paramRequest)
    {
        java.util.List listProps = new ArrayList();
        int index = 0;
        try {
            String [] strProperties = {
            PROPERTY_TYPE_TASK_DEFINITION,paramRequest.getLocaleString("cpTaskDefHeader0"),"id",
            PROPERTY_TYPE_TASK_DEFINITION,paramRequest.getLocaleString("cpTaskDefHeader1"),"title",
            PROPERTY_TYPE_TASK_DEFINITION,paramRequest.getLocaleString("cpTaskDefHeader2"),"uri",
            PROPERTY_TYPE_TASK_DEFINITION,paramRequest.getLocaleString("cpTaskDefHeader3"),"url",
            PROPERTY_TYPE_TASK_DEFINITION,paramRequest.getLocaleString("cpTaskDefHeader4"),"description"
            };
            for(int i=0; i<strProperties.length-2; i=i+3)
            {
                String[] temp = {strProperties[i],strProperties[i+1],strProperties[i+2]};
                listProps.add(index,temp);
                index++;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.TaskDefinitionProperties", e);
            System.out.println("Error en PreConfiguredControlPanel.TaskDefinitionProperties:" + e.getMessage());
		}
        return listProps;
    }

    /**
	 * Regresa una lista con las propiedades de instancia de la tarea que pueden seleccionarse
	 *
     * @param           paramsRequest SWBParamRequest
	 * @return          java.util.List
	 */
    public static java.util.List TaskInstanceProperties(SWBParamRequest paramRequest)
    {
        java.util.List listProps = new ArrayList();
        int index = 0;
        try {
            String [] strProperties = {
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader0"),"id",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader1"),"status",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader2"),"uri",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader3"),"encodedURI",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader4"),"created",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader5"),"creator",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader6"),"modified",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader7"),"modifiedBy",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader8"),"ended",
            PROPERTY_TYPE_TASK_INSTANCE,paramRequest.getLocaleString("cpTaskInstHeader9"),"endedBy"
            };
            for(int i=0; i<strProperties.length-2; i=i+3)
            {
                String[] temp = {strProperties[i],strProperties[i+1],strProperties[i+2]};
                listProps.add(index,temp);
                index++;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.TaskInstanceProperties", e);
            System.out.println("Error en PreConfiguredControlPanel.TaskInstanceProperties:" + e.getMessage());
		}
        return listProps;
    }

    /**
	 * Regresa un booleano indicando si un listado contiene a una TaskProperty
     * cuyo tipo y nombre correspondan a los parametros proporcionados.
	 *
     * @param           type String
     * @param           name String
     * @param           lstSelected java.util.List
	 * @return          boolean
	 */
    public boolean listContainsTaskProperty(String type, String name, java.util.List lstSelected)
    {
        boolean bContains = false;
        try{
            Iterator itLstSelected = lstSelected.iterator();
            while(itLstSelected.hasNext())
            {
                TaskProperty tskTemp = (TaskProperty)itLstSelected.next();
                if(name.equalsIgnoreCase(tskTemp.getId()) && type.equalsIgnoreCase(tskTemp.getType()))
                {
                    bContains = true;
                    break;
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.listContainsTaskProperty", e);
            System.out.println("Error en PreConfiguredControlPanel.listContainsTaskProperty:" + e.getMessage());
		}
        return bContains;
    }


    /**
    * Determina los filtros seleccionados por el usuario y los aplica al vector
    * de objetos FlowNodeInstance
    *
    * @param            vTasks Vector
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		Vector
    * @see
    */
    public Vector applyFilters(Vector vTasks, HttpServletRequest request, SWBParamRequest paramRequest)
    {
        Vector vFiltered = new Vector();
        try
        {
            String strFilterPagination = "";
            Resource base = paramRequest.getResourceBase();
            String strInitialFilterDate = request.getParameter(INITIAL_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(INITIAL_DATE_FILTER_CTRL);
            String strEndFilterDate = request.getParameter(END_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(END_DATE_FILTER_CTRL);
            String strFilterProcess = request.getParameter(PROCESS_FILTER_CTRL)==null
                ?"0"
                :request.getParameter(PROCESS_FILTER_CTRL);
            String strFilterStatus = request.getParameter(STATUS_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(STATUS_FILTER_CTRL);
            String strFilterPriority = request.getParameter(PRIORITY_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(PRIORITY_FILTER_CTRL);
            if(!strFilterStatus.equalsIgnoreCase("-1") ||
                    !strFilterProcess.equalsIgnoreCase("0") ||
                    !strInitialFilterDate.equalsIgnoreCase("") ||
                    !strFilterPriority.equalsIgnoreCase("-1"))
            {
                if(!strFilterProcess.equalsIgnoreCase("0"))
                {
                    vFiltered = filterTasks(vTasks, FILTER_BY_PROCESS, strFilterProcess);
                    strFilterPagination = strFilterPagination + "&" + PROCESS_FILTER_CTRL + "=" + strFilterProcess;
                }
                if(!strFilterStatus.equalsIgnoreCase("-1"))
                {
                    if(strFilterProcess.equalsIgnoreCase("0"))
                    {
                       vFiltered = vTasks;
                    }
                    vFiltered = filterTasks(vFiltered, FILTER_BY_STATUS, strFilterStatus);
                    strFilterPagination = strFilterPagination + "&" + STATUS_FILTER_CTRL + "=" + strFilterStatus;
                }
                if(!strFilterPriority.equalsIgnoreCase("-1"))
                {
                    if(strFilterProcess.equalsIgnoreCase("0") && strFilterStatus.equalsIgnoreCase("-1"))
                    {
                       vFiltered = vTasks;
                    }
                    strFilterPagination = strFilterPagination + "&" + PRIORITY_FILTER_CTRL + "=" + strFilterPriority;
                    vFiltered = filterTasks(vFiltered, FILTER_BY_PRIORITY, strFilterPriority);
                }
                if(!strInitialFilterDate.equalsIgnoreCase(""))
                {
                    if(strFilterProcess.equalsIgnoreCase("0") &&
                            strFilterStatus.equalsIgnoreCase("-1") &&
                            strFilterPriority.equalsIgnoreCase("-1"))
                    {
                       vFiltered = vTasks;
                    }
                    strFilterPagination = strFilterPagination + "&" + INITIAL_DATE_FILTER_CTRL + "=" + strInitialFilterDate;
                    strInitialFilterDate = BPMSTask.ClassMgr.changeDateFormat(strInitialFilterDate);
                    String strFilterValue = strInitialFilterDate+"|";
                    if(!strEndFilterDate.equalsIgnoreCase(""))
                    {
                        strFilterPagination = strFilterPagination + "&" + END_DATE_FILTER_CTRL + "=" + strEndFilterDate;
                        strEndFilterDate = BPMSTask.ClassMgr.changeDateFormat(strEndFilterDate);
                        strFilterValue = strFilterValue + strEndFilterDate+"|";
                    }
                    vFiltered = filterTasks(vFiltered, FILTER_BY_DATE, strFilterValue);
                }
            } else {
                vFiltered = vTasks;
            }
            base.setAttribute("strFilterPagination", strFilterPagination);
            base.updateAttributesToDB();

        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.applyFilters", e);
            System.out.println("Error en PreConfiguredControlPanel.applyFilters:" + e.getMessage());
		}
        return vFiltered;
    }

    /**
    * Genera un vector filtrado con la informacion necesaria para integrar
    * un reporte
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		Vector
    */
    public Vector filterReport(HttpServletRequest request,
            SWBParamRequest paramsRequest)
    {
        Vector vAllTasks = new Vector();
        try
        {
            String strReportProcess = request.getParameter(PROCESS_REPORT_CTRL)==null
                ?"0"
                :request.getParameter(PROCESS_REPORT_CTRL);
            String strInitialReportDate = request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate = request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(END_DATE_REPORT_CTRL));
            String strReportStatus = request.getParameter(STATUS_REPORT_CTRL)==null
                ?"-1"
                :request.getParameter(STATUS_REPORT_CTRL);
            vAllTasks = BPMSTask.ClassMgr.getAllUserTasks(paramsRequest);
            if(!strReportProcess.equalsIgnoreCase("0"))
            {
                vAllTasks = BPMSTask.ClassMgr.filterTasksByProcess(vAllTasks,strReportProcess);
            }
            if(!strInitialReportDate.equalsIgnoreCase(""))
            {
                String strFilterValue = strInitialReportDate + "|";
                if(!strEndReportDate.equalsIgnoreCase(""))
                {
                    strFilterValue = strFilterValue + strEndReportDate + "|";
                }
                vAllTasks = BPMSTask.ClassMgr.filterTasksByDate(vAllTasks, strFilterValue);
            }
            if(!strReportStatus.equalsIgnoreCase("-1"))
            {
                vAllTasks = BPMSTask.ClassMgr.filterTasksByStatus(vAllTasks,strReportStatus);
            }
            vAllTasks = BPMSTask.ClassMgr.flowNodeInstanceToTaskLink(vAllTasks, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks, BPMSTask.ClassMgr.SORT_BY_DATE);
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.filterReport", e);
            System.out.println("Error en PreConfiguredControlPanel.filterReport:" + e.getMessage());
		}
        return vAllTasks;
    }

    /**
    * Aplica al vector de objetos FlowNodeInstance el tipo de filtro
    * solicitado con el valor correspondiente
    *
    * @param            v Vector
    * @param            filter int
    * @param            filterValues String
    * @return      		Vector de objetos FlowNodeInstance
    */
    public Vector filterTasks(Vector v, int filter, String filterValues)
    {
        Vector vFilteredTasks = new Vector();
        try
        {
            switch(filter)
            {
                case FILTER_BY_PROCESS:
                    vFilteredTasks = BPMSTask.ClassMgr.filterTasksByProcess(v,filterValues);
                    break;
                case FILTER_BY_STATUS:
                    vFilteredTasks = BPMSTask.ClassMgr.filterTasksByStatus(v, filterValues);
                    break;
                case FILTER_BY_TITLE:
                    vFilteredTasks = BPMSTask.ClassMgr.filterTasksByTitle(v, filterValues);
                    break;
                case FILTER_BY_PRIORITY:
                    vFilteredTasks = BPMSTask.ClassMgr.filterTasksByPriority(v,filterValues);
                    break;
                default:
                    vFilteredTasks = BPMSTask.ClassMgr.filterTasksByDate(v, filterValues);
                    break;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.filterTasks", e);
            System.out.println("Error en PreConfiguredControlPanel.filterTasks:" + e.getMessage());
		}
        return vFilteredTasks;
    }

    /**
    * Buscar y obtener un objeto TaskProperty en una lista, en base a su tipo y nombre.
    *
    * @param            type String
    * @param            name String
    * @param            lstSelected java.util.List
    * @return      		TaskProperty
    */
    public TaskProperty findTaskProperty(String type, String name, java.util.List lstSelected)
    {
        TaskProperty tskProp = new TaskProperty();
        try{
            Iterator itLstSelected = lstSelected.iterator();
            while(itLstSelected.hasNext())
            {
                TaskProperty tskTemp = (TaskProperty)itLstSelected.next();
                if(name.equalsIgnoreCase(tskTemp.getId()) && type.equalsIgnoreCase(tskTemp.getType()))
                {
                    tskProp = tskTemp;
                    break;
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.TaskInstanceProperties", e);
            System.out.println("Error en PreConfiguredControlPanel.TaskInstanceProperties:" + e.getMessage());
		}
        return tskProp;
    }

    /**
    * Obtener el nombre de despliegue de un objeto TaskProperty
    *
    * @param            tp TaskProperty
    * @return      		String
    */
    public String findTaskPropertyDisplayName(TaskProperty tp)
    {
        String strName = "";
        Iterator itLst;

        try
        {
            String type = tp.getType();
            String id = tp.getId();
            if(type.equalsIgnoreCase(PROPERTY_TYPE_TASK_DEFINITION))
            {
                itLst = lstTaskDefinitionProperties.iterator();
                while(itLst.hasNext())
                {
                    String strTemp[] = (String[])itLst.next();
                    if(id.equalsIgnoreCase(strTemp[2]))
                    {
                        strName = strTemp[1];
                        break;
                    }
                }
            } else if(type.equalsIgnoreCase(PROPERTY_TYPE_TASK_INSTANCE))
            {
                itLst = lstTaskInstanceProperties.iterator();
                while(itLst.hasNext())
                {
                    String strTemp[] = (String[])itLst.next();
                    if(id.equalsIgnoreCase(strTemp[2]))
                    {
                        strName = strTemp[1];
                        break;
                    }
                }
            } else if(type.equalsIgnoreCase(PROPERTY_TYPE_ARTIFACT))
            {
                strName = BPMSProcessInstance.ClassMgr.getPropertyName(id);
            } else if(type.equalsIgnoreCase(PROPERTY_TYPE_FIXED_LINK_PARAMETER))
            {
                strName = "";
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.findTaskPropertyDisplayName", e);
            System.out.println("Error en PreConfiguredControlPanel.findTaskPropertyDisplayName:" + e.getMessage());
		}
        return strName;
    }


    /**
    * Genera una lista de objetos TaskProperty en base a un String que contiene
    * sus identificadores, tipos y orden
    *
    * @param            displayType int
    * @return      		String
    */
    public java.util.List stringToTaskPropertyList(int displayType, String strConfiguration)
    {
        java.util.List listProps = new ArrayList();
        int index = 0;
        try{
            if(strConfiguration!=null && !strConfiguration.equalsIgnoreCase("") 
                    && strConfiguration.contains(";") && strConfiguration.contains("|"))
            {
                String strProperties[] = strConfiguration.split(";");
                for(int i=0; i<strProperties.length; i++)
                {
                    String strProperty = strProperties[i];
                    if(strProperty.contains("|"))
                    {
                        String strElements[] = strProperty.split("\\|");
                        if(strElements.length==3){
                            String strType = strElements[0];
                            String strId = strElements[1];
                            String strOrder = strElements[2];
                            int order = Integer.parseInt(strOrder);
                            TaskProperty tp = new TaskProperty(strType, strId, displayType, order);
                            String strName = findTaskPropertyDisplayName(tp);
                            tp.setDisplayName(strName);
                            listProps.add(index, tp);
                            index++;
                        }
                    }                    
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.stringToTaskPropertyList", e);
            System.out.println("Error en PreConfiguredControlPanel.stringToTaskPropertyList:" + e.getMessage());
		}
        return listProps;        
    }

    /**
    * Genera el HTML necesario para desplegar las propiedades de un tipo de despliegue
    * (vinculo, leyenda o columnas) para que el administrador pueda seleccionarlas
    *
    * @param            displayType int
    * @return      		String
    */
    public StringBuffer printTypeProperties(int displayOn, String propertyType, org.semanticwb.process.model.Process selectedProcess,
            java.util.List lstTypeProps, java.util.List lstSelectedProps, String strCtrl, String strOrderCtrl)
    {
        StringBuffer sb = new StringBuffer();
        java.util.List lstDefaultHref = new ArrayList();
        String strDisabled = "";
        String strChecked = "";
        String strSelectedProps = "";
        boolean bFound = false;

        try{
           if(displayOn==DISPLAY_ON_LINK){
                lstDefaultHref = stringToTaskPropertyList(DISPLAY_ON_LINK, strDefaultHref);
            }
            Iterator itLstTypeProps = lstTypeProps.iterator();
            //Si es Link, mostrar propiedades, pero TaskDefinition|url|-2|;TaskInstance|encodedURI|-1| deshabilitadas
            while(itLstTypeProps.hasNext())
            {
                TaskProperty tskTemp = new TaskProperty();
                if(!propertyType.equalsIgnoreCase(PROPERTY_TYPE_ARTIFACT)){
                    String strTempProperty[] = (String[])itLstTypeProps.next();
                    bFound = listContainsTaskProperty(strTempProperty[0],strTempProperty[2],lstSelectedProps);
                    if(bFound)
                    {
                        tskTemp = findTaskProperty(strTempProperty[0],strTempProperty[2],lstSelectedProps);
                    } else {
                        tskTemp = new TaskProperty(strTempProperty[0], strTempProperty[2], displayOn);
                        tskTemp.setDisplayName(strTempProperty[1]);
                    }
                } else {
                    tskTemp = (TaskProperty)itLstTypeProps.next();
                    bFound = listContainsTaskProperty(tskTemp.getType(),tskTemp.getId(),lstSelectedProps);
                    if(bFound)
                    {
                        tskTemp = findTaskProperty(tskTemp.getType(),tskTemp.getId(),lstSelectedProps);
                    } else {
                        tskTemp = findTaskProperty(tskTemp.getType(),tskTemp.getId(),lstTypeProps);
                    }
                }
                String strTempPropName = tskTemp.getType() + "|" + tskTemp.getId() + "|";
                //En el caso de propiedades del Link, deshabilitar las propiedades que son indispensables para el despliegue
                if(displayOn == DISPLAY_ON_LINK && lstDefaultHref.size()>0 && listContainsTaskProperty(tskTemp.getType(),tskTemp.getId(),lstDefaultHref))
                {
                    strDisabled = "disabled";
                } else {
                    strDisabled = "";
                }
                if(bFound)
                {
                    strChecked = "checked";
                } else {
                    strChecked = "";
                }
                sb.append("\n<tr>");
                sb.append("\n<td>");
                sb.append("\n<input type=\"CHECKBOX\" " + "id=\"" + strCtrl + "\"" +
                        " name=\"" + strCtrl + "\" " +
                        " value=\"" + strTempPropName + "\" " + strChecked + " " + strDisabled + " >");
                sb.append("\n" + tskTemp.getDisplayName() + "</td>");
                sb.append("\n<td>" + tskTemp.getType() + "</td>");
                sb.append("\n<td><input type=\"TEXT\" " +
                        "id=\"" + strOrderCtrl + "|" + strTempPropName + "\""
                        + " name=\"" + strOrderCtrl + "|" + strTempPropName + "\""
                        + " size=\"4\" maxlength=\"4\" " + strDisabled +
                        "  label=\"\" onKeyUp=\"validateNumeric(this)\"" +
                        " value=\"" + tskTemp.getDisplayOrder() + "\"/></td>");
                sb.append("\n</tr>");
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.printTypeProperties", e);
            System.out.println("Error en PreConfiguredControlPanel.printTypeProperties:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Despliega un formato para seleccionar los filtros que deben aplicarse a
    * la bandeja.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML del formato
    * @see
    */
    public StringBuffer getFilterForm(SWBParamRequest paramsRequest){
        StringBuffer sb = new StringBuffer();
        try
        {
            Resource base = paramsRequest.getResourceBase();
            SWBResourceURL url = paramsRequest.getRenderUrl();
            String strControlPanelTaskStatus = base.getAttribute("controlPanelTaskStatus")==null
                    ?""
                    :base.getAttribute("controlPanelTaskStatus");
            String strControlPanelTaskPriority = base.getAttribute("controlPanelTaskPriority")==null
                    ?""
                    :base.getAttribute("controlPanelTaskPriority");
            strControlPanelTaskStatus = ALL_STATUS;
            strControlPanelTaskPriority = ALL_PRIORITY;
            Vector vSelectedProcessDefinitions = getSelectedProcessDefinitions(paramsRequest);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direccionaF(action){");
            sb.append("\n      document.forms['frmFilter'].action = action;");
            sb.append("\n      document.forms['frmFilter'].submit();");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("<div id=\"" + DIV_ID_FILTERED + "\"><div class=\"" + DIV_CLASS_PLECA + "\"><h3>" +
                    paramsRequest.getLocaleString("lblFilterTitle") + "</h3>");
            sb.append("<form name=\"frmFilter\" action=\"" +
                    paramsRequest.getRenderUrl().setAction("filter") +
                    "\" dojoType=\"dijit.form.Form\" method=\"POST\">");

	      sb.append("<label for=\"" + INITIAL_DATE_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterIniDate") + ":</label>");
	      sb.append("<input type=\"text\" name=\"" + INITIAL_DATE_FILTER_CTRL + "\" id=\"" + INITIAL_DATE_FILTER_CTRL +"\" class=\"" + INPUT_TEXT_CLASS + "\" " +
                  "dojoType=\"dijit.form.DateTextBox\" required=\"false\" invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\" constraints=\"{datePattern:'dd/MM/yyyy'}\" />");
          sb.append("<br />");
	      sb.append("<label for=\"" + END_DATE_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterEndDate") + ":</label>");
	      sb.append("<input type=\"text\" name=\"" + END_DATE_FILTER_CTRL + "\" id=\"" + END_DATE_FILTER_CTRL +"\" class=\"" + INPUT_TEXT_CLASS + "\" " +
                  "dojoType=\"dijit.form.DateTextBox\" required=\"false\" invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\" constraints=\"{datePattern:'dd/MM/yyyy'}\"/>");
          sb.append("<br/>");
	      sb.append("<label for=\"" + PROCESS_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterProcess") + ":</label>");
	      sb.append("<select name=\"" + PROCESS_FILTER_CTRL + "\" id=\"" + PROCESS_FILTER_CTRL +"\" />");
            sb.append("<option ");
            sb.append("value=\"0\">" +
            paramsRequest.getLocaleString("cpAllProcesses") +
            "</option>");
            for(int i=0; i<vSelectedProcessDefinitions.size(); i++){
                org.semanticwb.process.model.Process selectedProcess = (org.semanticwb.process.model.Process)
                vSelectedProcessDefinitions.get(i);
                sb.append("<option ");
                sb.append("value=\"" + selectedProcess.getURI() + "\">" + selectedProcess.getTitle() +"</option>");
            }
            sb.append("</select><br/>");

            Vector vTaskStatus = BPMSProcessInstance.ClassMgr.stringToVector(strControlPanelTaskStatus);
	        sb.append("<label for=\"" + STATUS_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterStatus") + ":</label>");
            sb.append(" <select name=\""+STATUS_FILTER_CTRL+ ""
                    + "\" id=\""+STATUS_FILTER_CTRL+"\">");
                sb.append("<option ");
                sb.append("value=\"-1\">" +
                        paramsRequest.getLocaleString("cpAllTaskStatus") +
                        "</option>");
            for(int i=0; i<vTaskStatus.size(); i++){
                sb.append("<option ");
                sb.append("value=\"" + vTaskStatus.get(i) + "\">" +
                        paramsRequest.getLocaleString("cpTaskStatus"+
                        vTaskStatus.get(i)) + "</option>");
            }
            sb.append("</select><br/><br/>");

            Vector vTaskPriority =
                    BPMSProcessInstance.ClassMgr.stringToVector(
                        strControlPanelTaskPriority);
	        sb.append("<label for=\"" + PRIORITY_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterPriority") + ":</label>");
            sb.append(" <select name=\""+PRIORITY_FILTER_CTRL+ ""
                    + "\" id=\""+PRIORITY_FILTER_CTRL+"\">");
                sb.append("<option ");
                sb.append("value=\"-1\">" +
                        paramsRequest.getLocaleString("cpAllTaskPriority") +
                        "</option>");
            for(int i=0; i<vTaskPriority.size(); i++){
                sb.append("<option ");
                sb.append("value=\"" + vTaskPriority.get(i) + "\">" +
                        paramsRequest.getLocaleString("cpTaskPriority"+
                        vTaskPriority.get(i)) + "</option>");
            }
            sb.append("</select><br/><br/>");
            sb.append("<input type=\"SUBMIT\" class=\"" + INPUT_BUTTON_CLASS + "\" name=\"btnEdit\"  value=\"" +
                    paramsRequest.getLocaleString("btnApplyFilter") + "\"/>");
            sb.append("<input type=\"RESET\" class=\"" + INPUT_BUTTON_CLASS + "\" name=\"btnReset\" value=\"" +
                    paramsRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form>");
            sb.append("</div></div>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getFilterForm", e);
            System.out.println("Error en PreConfiguredControlPanel.getFilterForm:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Genera un reporte usando las caracteristicas solicitadas en getFilterForm.
    * El reporte se presenta usando codigo HTML.
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML del reporte
    */
    public StringBuffer buildReport(HttpServletRequest request,
            SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            Vector vAllTasks = filterReport(request, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks,BPMSTask.ClassMgr.SORT_BY_DATE);
            sb.append(displayReport(vAllTasks, request, paramsRequest));
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.buildReport", e);
            System.out.println("Error en PreConfiguredControlPanel.buildReport:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Genera el codigo HTML del reporte de tareas que se despliega en Control Panel
    *
    * @param            vAllTasks Vector de objetos tipo TaskLink
    * @param            request HttpServletRequest
    * @param            paramsRequest SWBParamRequest
    * @return      		StringBuffer con el codigo del reporte
    * @see
    */
    public StringBuffer displayReport(Vector vAllTasks,
            HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            User currentUser = paramsRequest.getUser();
            SWBResourceURL url = paramsRequest.getRenderUrl();
            String strInitialReportDate = request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate = request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(END_DATE_REPORT_CTRL));
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direcciona(action){");
            sb.append("\n      document.forms['frmReport'].action = action;");
            sb.append("\n      document.forms['frmReport'].submit();");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("<div id=\"" + DIV_ID_TASKS + "\">");
            sb.append("<p class=\"" + P_CLASS_LEFT + "\">" + paramsRequest.getLocaleString("lblCrReportTitle") + " " +
                    currentUser.getFullName() + " " +
                    paramsRequest.getLocaleString("lblCrReportStart") + " " +
                    strInitialReportDate + " " +
                    paramsRequest.getLocaleString("lblCrReportEnd") + " " +
                    strEndReportDate +
                    "</p>");
            sb.append("<p class=\"" + P_CLASS_RIGHT_CLOSED +"\"></p></div>");
            sb.append("<table><thead><th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow0") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow1") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow2") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow3") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow4") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow5") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow6") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow7") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow8") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow9") + "</a></th>" +
                    "<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow10") + "</a></th></thead><tbody>");
            for(int i=0; i<vAllTasks.size(); i++){
                TaskLink tLink = (TaskLink) vAllTasks.get(i);
                FlowNodeInstance fobi = tLink.getTaskLinkFlowNodeInstance();
                sb.append(printReportTask(fobi, paramsRequest));
            }
            sb.append("</tbody></table>");
            sb.append("<div id=\"" + DIV_ID_DOWNLOAD + "\">");
            sb.append("<ul><li>");
            sb.append(createXmlReport(request, paramsRequest));
            sb.append("</li><li>");
            sb.append(createPdfReport(request, paramsRequest));
            sb.append("</li><li>");
            sb.append(createRtfReport(request, paramsRequest));
            sb.append("</li></ul>");
            sb.append("");
            sb.append("<form name=\"frmReport\" action=\"" + url.setAction("selectReport") + "\" method=\"POST\">");
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnBack2View\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramsRequest.getLocaleString("btnBack") + "\"" +
                    " onclick=\"direcciona('" + url.setAction("goToView") + "');\" />");
            sb.append("</form></div>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.displayReport", e);
            System.out.println("Error en PreConfiguredControlPanel.displayReport:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Genera un reporte de tares en formato RTF y regresa un StringBuffer con
    * el vínculo´para descargarlo.
    *
    * @param            request HttpServletRequest
    * @param            paramsRequest SWBParamRequest
    * @return      		StringBuffer
    */
    public StringBuffer createRtfReport(HttpServletRequest request,
            SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("<a href=\"" + strDownloadRtf + "\" class=\"" + HREF_RTF_CLASS + "\">" +
                    paramsRequest.getLocaleString("lblRtfReport") + "</a>");
            User currentUser = paramsRequest.getUser();
            String strInitialReportDate = request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate = request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(END_DATE_REPORT_CTRL));
            String strAuthor = currentUser.getFullName()==null
                ?""
                :currentUser.getFullName();
            String [] strTableColumnNames =
                {paramsRequest.getLocaleString("cpHeaderRow0"),
                 paramsRequest.getLocaleString("cpHeaderRow1"),
                 paramsRequest.getLocaleString("cpHeaderRow2"),
                 paramsRequest.getLocaleString("cpHeaderRow3"),
                 paramsRequest.getLocaleString("cpHeaderRow4"),
                 paramsRequest.getLocaleString("cpHeaderRow5"),
                 paramsRequest.getLocaleString("cpHeaderRow6"),
                 paramsRequest.getLocaleString("cpHeaderRow7"),
                 paramsRequest.getLocaleString("cpHeaderRow8"),
                 paramsRequest.getLocaleString("cpHeaderRow9"),
                 paramsRequest.getLocaleString("cpHeaderRow10")
            };
            int[] columnWidths;
            columnWidths = new int[strTableColumnNames.length];
            int colWidth = 100 / strTableColumnNames.length;
            for(int i=0; i<columnWidths.length; i++)
            {
                columnWidths[i] = colWidth;
            }
            String strReportDates = paramsRequest.getLocaleString("lblCrReportStart") + " " + strInitialReportDate;
            if(!strEndReportDate.equalsIgnoreCase(""))
            {
                strReportDates = strReportDates + " " + paramsRequest.getLocaleString("lblCrReportEnd") + " " + strEndReportDate;
            }
            Vector vAllTasks = filterReport(request, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks, BPMSTask.ClassMgr.SORT_BY_DATE);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.LETTER);
            //Set file metadata
            document.addAuthor(strAuthor);
            document.addCreationDate();
            document.addCreator("SWB Control Panel");
            document.addTitle(paramsRequest.getLocaleString("lblCrReportHeaderTitle"));
            //document.addKeywords("Java, RTF, iText");
            //document.addSubject("Control Panel Task Report");
            RtfWriter2 rtf = RtfWriter2.getInstance(document,new FileOutputStream(filenameRtf));
            //FOOTER: En el caso del RTF no es posible saber con anticipacion el
            //numero de pagina.
            //Ver http://www.mail-archive.com/itext-questions@lists.sourceforge.net/msg33833.html
            //HEADER
            String strReportTitle = paramsRequest.getLocaleString("lblCrReportHeaderTitle");
            String strReportSubTitle = paramsRequest.getLocaleString("lblCrReportUser") + " " +
                    strAuthor + "\t\t\t\t" + strReportDates;
            Phrase pHeader = new Phrase();
            //Incluir encabezado de las columnas en el Header para que se repita
            //en cada página del reporte
            Table headerTable = createRtfTableHeader(strReportTitle, strReportSubTitle, strTableColumnNames, columnWidths);
            headerTable.setTableFitsPage(true);
            pHeader.clear();
            pHeader.add(headerTable);
            HeaderFooter header = new HeaderFooter(pHeader, false);
            header.setBorder(Rectangle.NO_BORDER);
            document.setHeader(header);
            document.open();
            //Tabla con informacion de las tareas
            Table table = createRtfTable(vAllTasks,paramsRequest, columnWidths);
            table.setTableFitsPage(true);
            table.setOffset(0);
            document.add(table);
            document.close();
        } catch (IOException ioe) {
            log.error("Error en PreConfiguredControlPanel.createRtfReport", ioe);
            System.err.println("error en PreConfiguredControlPanel.createRtfReport: " + ioe.getMessage());
        } catch (Exception e) {
            log.error("Error en PreConfiguredControlPanel.createRtfReport", e);
            System.out.println("error en PreConfiguredControlPanel.createRtfReport: " + e.getMessage());
        }
        return sb;
    }

    /**
    * Generar un objeto Table con el titulo, subtitulo y encabezado de la tabla
    * del reporte de tareas del usuario. El objeto resultante se puede utilizar
    * como encabezado en el archivo RTF.
    *
    * @param            String titulo del reporte
    * @param            String subtitulo del reporte
    * @param            String[] titulos de las columnas del reporte
    * @param            int[] ancho de los encabezados columnas del reporte
    * @return      		Table (libreria iText)
    */
    public Table createRtfTableHeader(String title, String subtitle,
            String[] columnNames, int[] columnWidths)
    {
        Table table = null;
        try
        {
            table = new Table(1);
            if(columnNames.length>0){
                table = new Table(columnNames.length);
                table.setWidth(100);
                table.setWidths(columnWidths);
                if(!title.equalsIgnoreCase(""))
                {
                    String strAux = "\n" + title;
                    if(!subtitle.equalsIgnoreCase(""))
                    {
                        strAux = strAux + "\n" + subtitle;
                    }
                    Cell titleCell;
                    titleCell = new Cell(new Phrase(strAux, rtfTitleFont));
                    titleCell.setColspan(columnNames.length);
                    titleCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    titleCell.setBackgroundColor(rtfHeaderTitleBackground);
                    table.addCell(titleCell);
                }
                Cell headerColumn;
                for(int i=0; i<columnNames.length;i++)
                {
                    headerColumn = new Cell(new Phrase(columnNames[i],
                                rtfColumnTitleFont));
                    headerColumn.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    headerColumn.setBackgroundColor(rtfHeaderColumnBackground);
                    table.addCell(headerColumn);
                }
            }
        } catch (com.lowagie.text.BadElementException be) {
            log.error("Error en PreConfiguredControlPanel.createRtfTableHeader", be);
            System.out.println("error en PreConfiguredControlPanel.createRtfTableHeader: " + be.getMessage());
        } catch (Exception e) {
            log.error("Error en PreConfiguredControlPanel.createRtfTableHeader", e);
            System.out.println("error en PreConfiguredControlPanel.createRtfTableHeader: " + e.getMessage());
        }
        return table;
    }

    /**
    * Crear un objeto Table con el contenido de un Vector de objetos de tipo
    * TaskLink. El objeto resultante forma el cuerpo del reporte RTF.
    *
    * @param            Vector de objetos tipo TaskLink
    * @param            SWBParamRequest
    * @param            int[] ancho de los encabezados columnas del reporte
    * @return      		Table (libreria iText)
    */
    public Table createRtfTable(Vector vTasks, SWBParamRequest paramsRequest, int[] columnWidths)
    {
        Table  table = null;
        try{
            table = new Table(reportColumnNumber);
            table.setWidth(100);
            table.setWidths(columnWidths);
            for(int i=0; i<vTasks.size(); i ++)
            {
                TaskLink tlink = (TaskLink) vTasks.get(i);
                String strProcessName = tlink.getFlowNodeParentProcess()==null
                    ?""
                    :tlink.getFlowNodeParentProcess();
                String strTaskId = tlink.getFlowNodeInstanceId()==null
                    ?""
                    :tlink.getFlowNodeInstanceId();
                String strTaskTitle = tlink.getFlowNodeTitle()==null
                    ?""
                    :tlink.getFlowNodeTitle();
                String strTaskCreated = String.valueOf(tlink.getFlowNodeInstanceCreated())==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceCreated());
                String strTaskCreatedBy = tlink.getFlowNodeInstanceCreatedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceCreatedByName();
                int intStatus = tlink.getFlowNodeInstanceStatus();
                String strTaskStatus = getStatusDescription(paramsRequest, intStatus)==null
                    ?""
                    :getStatusDescription(paramsRequest, intStatus);
                int intPriority = tlink.getPriority();
                String strTaskPriority = getPriorityDescription(paramsRequest, intPriority);
                String strTaskUpdated = tlink.getFlowNodeInstanceModified()==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceModified());
                String strTaskUpdatedBy = tlink.getFlowNodeInstanceModifiedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceModifiedByName();
                String strTaskEnded = "";
                String strTaskEndedBy = "";
                if(tlink.getFlowNodeInstanceEnded()!=null)
                {
                    strTaskEnded = String.valueOf(tlink.getFlowNodeInstanceEnded());
                    strTaskEndedBy = tlink.getFlowNodeInstanceEndedByName()==null
                        ?""
                        :tlink.getFlowNodeInstanceEndedByName();
                }
                Cell infoCell = new Cell(new Phrase(strProcessName,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskId,rtfCellFont));
                infoCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskTitle,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskCreated,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskCreatedBy,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskStatus,rtfCellFont));
                infoCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskUpdated,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskUpdatedBy,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskEnded,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskEndedBy,rtfCellFont));
                table.addCell(infoCell);
                infoCell = new Cell(new Phrase(strTaskPriority,rtfCellFont));
                table.addCell(infoCell);
            }
        } catch (com.lowagie.text.BadElementException be) {
            log.error("Error en PreConfiguredControlPanel.createRtfTable", be);
            System.out.println("error en PreConfiguredControlPanel.createRtfTable: " + be.getMessage());
        } catch (Exception e) {
            log.error("Error en PreConfiguredControlPanel.createRtfTable", e);
            System.out.println("error en PreConfiguredControlPanel.createRtfTable: " + e.getMessage());
        }
        return table;
    }

    /**
    * Genera un reporte de tareas en formato XML y regresa un StringBuffer con
    * el vínculo´para descargarlo.
    *
    * @param            request HttpServletRequest
    * @param            paramsRequest SWBParamRequest
    * @return      		StringBuffer
    */
    public StringBuffer createXmlReport(HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("<a href=\"" + strDownloadXml + "\" class=\"" + HREF_XML_CLASS + "\">" +
                    paramsRequest.getLocaleString("lblXmlReport") + "</a>");
            Vector vAllTasks = filterReport(request, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks,
                        BPMSTask.ClassMgr.SORT_BY_DATE);
            FileInputStream fis = new FileInputStream(filenameXml);
            InputStreamReader isr = new InputStreamReader(fis);
            LineNumberReader lnr = new LineNumberReader(isr);
            org.w3c.dom.Document doc = createAlternativeReportDocument(vAllTasks, paramsRequest);
            TransformerFactory transFac = SWBUtils.XML.getTransformerFactory().newInstance();
            Transformer trans = transFac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
            trans.setOutputProperty(OutputKeys.INDENT,"yes");
            //Al archivo
            StreamResult sr = new StreamResult(new File(filenameXml));
            DOMSource source = new DOMSource(doc);
            trans.transform(source, sr);
            //Para debug
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            trans.transform(source, result);
            String xmlString = sw.toString();
            //System.out.println("Prueba:\n\n" + xmlString);
        } catch (Exception e) {
            log.error("Error en PreConfiguredControlPanel.createXmlReport", e);
            System.out.println("error en PreConfiguredControlPanel.createXmlReport: " + e.getMessage());
        }
        return sb;
    }

    /**
    * Genera un objeto de tipo Document con la estructura del reporte XML
    * de tareas del usuario
    *
    * @param            vTasks Vector de objetos TaskLink
    * @param            paramsRequest SWBParamRequest
    * @return      		Document
    */
    public org.w3c.dom.Document createAlternativeReportDocument(Vector vTasks, SWBParamRequest paramsRequest)
    {
        org.w3c.dom.Document doc = null;
        try
        {
            DocumentBuilderFactory dbfac = SWBUtils.XML.getDocumentBuilderFactory().newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            doc = docBuilder.newDocument();
            org.w3c.dom.Element root = doc.createElement("Report");
            doc.appendChild(root);
            org.w3c.dom.Element title = doc.createElement("title");
            title.setAttribute("titleName", "Control Panel Activity Report");
            root.appendChild(title);
            for(int i=0; i<vTasks.size(); i++)
            {
                TaskLink tlink = (TaskLink) vTasks.get(i);
                org.w3c.dom.Element task = doc.createElement("task");
                String strTaskId = tlink.getFlowNodeInstanceId()==null
                    ?""
                    :tlink.getFlowNodeInstanceId();
                task.setAttribute("id",strTaskId);
                String strTaskTitle = tlink.getFlowNodeTitle()==null
                    ?""
                    :tlink.getFlowNodeTitle();
                task.setAttribute("title",strTaskTitle);
                String strProcessName = tlink.getFlowNodeParentProcess()==null
                    ?""
                    :tlink.getFlowNodeParentProcess();
                task.setAttribute("parentProcess",strProcessName);
                String strTaskCreated = String.valueOf(tlink.getFlowNodeInstanceCreated())==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceCreated());
                task.setAttribute("created",strTaskCreated);
                String strTaskCreatedBy = tlink.getFlowNodeInstanceCreatedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceCreatedByName();
                task.setAttribute("createdBy",strTaskCreatedBy);
                int intTaskStatus = tlink.getFlowNodeInstanceStatus();
                String strTaskStatus = getStatusDescription(paramsRequest,intTaskStatus);
                task.setAttribute("status",String.valueOf(intTaskStatus));
                String strTaskUpdated = tlink.getFlowNodeInstanceModified()==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceModified());
                task.setAttribute("updated",strTaskUpdated);
                String strTaskUpdatedBy = tlink.getFlowNodeInstanceModifiedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceModifiedByName();
                task.setAttribute("updatedBy",strTaskUpdatedBy);
                String strTaskEnded = "";
                String strTaskEndedBy = "";
                if(tlink.getFlowNodeInstanceEnded()!=null)
                {
                    strTaskEnded =  String.valueOf(tlink.getFlowNodeInstanceEnded());
                    strTaskEndedBy = tlink.getFlowNodeInstanceEndedByName()==null
                        ?""
                        :tlink.getFlowNodeInstanceEndedByName();
                }
                task.setAttribute("ended",strTaskEnded);
                task.setAttribute("endedBy",strTaskEndedBy);
                int intTaskPriority = tlink.getPriority();
                String strTaskPriority = String.valueOf(intTaskPriority);
                task.setAttribute("priority",String.valueOf(intTaskPriority));

                root.appendChild(task);
            }

        } catch (Exception e) {
            log.error("Error en PreConfiguredControlPanel.createAlternativeReportDocument", e);
            System.out.println("error en PreConfiguredControlPanel.createAlternativeReportDocument: " + e.getMessage());
        }
        return doc;
    }

    /**
    * Genera un reporte de tares en formato PDF y regresa un StringBuffer con
    * el vínculo´para descargarlo.
    *
    * @param            HttpServletRequest
    * @param            SWBParamRequest
    * @return      		StringBuffer
    */
    public StringBuffer createPdfReport(HttpServletRequest request,
            SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("<a href=\"" + strDownloadPdf + "\" class=\"" + HREF_PDF_CLASS + "\" >" +
                    paramsRequest.getLocaleString("lblPdfReport") + "</a>");
            User currentUser = paramsRequest.getUser();
            String strInitialReportDate = request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate = request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(END_DATE_REPORT_CTRL));
            String strAuthor = currentUser.getFullName()==null
                ?""
                :currentUser.getFullName();
            String [] strTableColumnNames =
                {paramsRequest.getLocaleString("cpHeaderRow0"),
                 paramsRequest.getLocaleString("cpHeaderRow1"),
                 paramsRequest.getLocaleString("cpHeaderRow2"),
                 paramsRequest.getLocaleString("cpHeaderRow3"),
                 paramsRequest.getLocaleString("cpHeaderRow4"),
                 paramsRequest.getLocaleString("cpHeaderRow5"),
                 paramsRequest.getLocaleString("cpHeaderRow6"),
                 paramsRequest.getLocaleString("cpHeaderRow7"),
                 paramsRequest.getLocaleString("cpHeaderRow8"),
                 paramsRequest.getLocaleString("cpHeaderRow9"),
                 paramsRequest.getLocaleString("cpHeaderRow10")
                };
            String strReportDates =
                    paramsRequest.getLocaleString("lblCrReportStart") + " " + strInitialReportDate;
            if(!strEndReportDate.equalsIgnoreCase(""))
            {
                strReportDates = strReportDates + " " + paramsRequest.getLocaleString("lblCrReportEnd") + " "
                        + strEndReportDate;
            }
            Vector vAllTasks = filterReport(request, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks,BPMSTask.ClassMgr.SORT_BY_DATE);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.LETTER);
            //Set file metadata
            document.addAuthor(strAuthor);
            document.addCreationDate();
            document.addCreator("SWB Control Panel");
            document.addTitle(paramsRequest.getLocaleString("lblCrReportHeaderTitle"));
            //document.addKeywords("Java, PDF, iText");
            //document.addSubject("Control Panel Task Report");
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filenamePdf));
            //FOOTER
            HeaderFooter footer = new HeaderFooter(new Phrase("Pg. " + pdfWriter.getPageNumber(), pdfFooterFont), true);
            footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(footer.ALIGN_CENTER);
            document.setFooter(footer);
            //HEADER
            String strReportTitle =
                    paramsRequest.getLocaleString("lblCrReportHeaderTitle");
            String strReportSubTitle =
                    paramsRequest.getLocaleString("lblCrReportUser") + " " +
                    strAuthor + "\t\t\t\t" + strReportDates;
            Phrase pHeader = new Phrase();
            //Incluir encabezados de columnas en el header para que se repita
            //en cada pagina del reporte
            PdfPTable headerTable = createPdfTableHeader(strReportTitle,strReportSubTitle, strTableColumnNames);
            headerTable.setSpacingBefore(pdfHeaderTableHeight);
            headerTable.setSpacingAfter(pdfHeaderTableHeight);
            pHeader.clear();
            pHeader.add(headerTable);
            HeaderFooter header = new HeaderFooter(pHeader, false);
            header.setBorder(Rectangle.NO_BORDER);
            document.setHeader(header);
            document.open();
            //Tabla con informacion de las tareas
            PdfPTable table = createPdfTable(vAllTasks,paramsRequest);
            table.setWidthPercentage(pdfTableWidthPercentage);
            document.add(table);
            document.close();
        } catch (DocumentException de) {
            log.error("Error en ControlPanel.createPdfReport", de);
            System.err.println("error en ControlPanel.createPdfReport: " + de.getMessage());
        } catch (IOException ioe) {
            log.error("Error en ControlPanel.createPdfReport", ioe);
            System.err.println("error en ControlPanel.createPdfReport: " + ioe.getMessage());
        } catch (Exception e) {
            log.error("Error en ControlPanel.createPdfReport", e);
            System.out.println("error en ControlPanel.createPdfReport: " + e.getMessage());
        }
        return sb;
    }

    /**
    * Generar un objeto PdfPTable con el titulo, subtitulo y encabezado de la
    * tabla del reporte de tareas del usuario. El objeto resultante se puede
    * utilizarcomo encabezado en el archivo PDF.
    *
    * @param            String titulo del reporte
    * @param            String subtitulo del reporte
    * @param            String[] titulos de las columnas del reporte
    * @return      		PdfPTable (libreria iText)
    */
    public PdfPTable createPdfTableHeader(String title, String subtitle, String[] columnNames)
    {
        PdfPTable table = new PdfPTable(1);
        try
        {
            if(columnNames.length>0){
                table = new PdfPTable(columnNames.length);
                table.setWidthPercentage(pdfTableWidthPercentage);
                if(!title.equalsIgnoreCase(""))
                {
                    String strAux = "\n" + title;
                    if(!subtitle.equalsIgnoreCase(""))
                    {
                        strAux = strAux + "\n" + subtitle + "\n";
                    }
                    PdfPCell titleCell;
                    titleCell = new PdfPCell(new Phrase(strAux,pdfTitleFont));
                    titleCell.setColspan(columnNames.length);
                    titleCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    titleCell.setPadding(reportHeaderPadding);
                    titleCell.setBackgroundColor(pdfHeaderTitleBackground);
                    table.addCell(titleCell);
                }
                PdfPCell headerColumn;
                for(int i=0; i<columnNames.length;i++)
                {
                    headerColumn = new PdfPCell(new Phrase(columnNames[i],pdfColumnTitleFont));
                    headerColumn.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    headerColumn.setPadding(reportHeaderPadding);
                    headerColumn.setBackgroundColor (pdfHeaderColumnBackground);
                    table.addCell(headerColumn);
                }
            }
        } catch (Exception e) {
            log.error("Error en ControlPanel.createPdfTableHeader", e);
            System.out.println("error en ControlPanel.createPdfTableHeader: " + e.getMessage());
        }
        return table;
    }

    /**
    * Crear un objeto PdfPTable con el contenido de un Vector de objetos de tipo
    * TaskLink. El objeto resultante forma el cuerpo del reporte PDF.
    *
    * @param            vTasks Vector de objetos tipo TaskLink
    * @param            paramsRequest SWBParamRequest
    * @return      		PdfPTable (libreria iText)
    */
    public PdfPTable createPdfTable(Vector vTasks, SWBParamRequest paramsRequest)
    {
        PdfPTable table = new PdfPTable(reportColumnNumber);
        try
        {
            for(int i=0; i<vTasks.size(); i ++)
            {
                TaskLink tlink = (TaskLink) vTasks.get(i);
                String strProcessName = tlink.getFlowNodeParentProcess()==null
                    ?""
                    :tlink.getFlowNodeParentProcess();
                String strTaskId = tlink.getFlowNodeInstanceId()==null
                    ?""
                    :tlink.getFlowNodeInstanceId();
                String strTaskTitle = tlink.getFlowNodeTitle()==null
                    ?""
                    :tlink.getFlowNodeTitle();
                String strTaskCreated = String.valueOf(tlink.getFlowNodeInstanceCreated())==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceCreated());
                String strTaskCreatedBy = tlink.getFlowNodeInstanceCreatedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceCreatedByName();
                int intStatus = tlink.getFlowNodeInstanceStatus();
                String strTaskStatus = getStatusDescription(paramsRequest, intStatus)==null
                    ?""
                    :getStatusDescription(paramsRequest, intStatus);
                String strTaskUpdated = tlink.getFlowNodeInstanceModified()==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceModified());
                String strTaskUpdatedBy = tlink.getFlowNodeInstanceModifiedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceModifiedByName();
                String strTaskEnded = "";
                String strTaskEndedBy = "";
                if(tlink.getFlowNodeInstanceEnded()!=null)
                {
                    strTaskEnded = String.valueOf(tlink.getFlowNodeInstanceEnded());
                    strTaskEndedBy = tlink.getFlowNodeInstanceEndedByName()==null
                        ?""
                        :tlink.getFlowNodeInstanceEndedByName();
                }
                int intPriority = tlink.getPriority();
                String strPriority = getPriorityDescription(paramsRequest, intPriority);

                PdfPCell infoCell = new PdfPCell(new Phrase(strProcessName,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskId,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                infoCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskTitle,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskCreated,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskCreatedBy,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskStatus,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                infoCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskUpdated,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskUpdatedBy,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskEnded,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strTaskEndedBy,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
                infoCell = new PdfPCell(new Phrase(strPriority,pdfCellFont));
                infoCell.setPadding(reportCellPadding);
                table.addCell(infoCell);
            }
        } catch (Exception e) {
            log.error("Error en ControlPanel.createPdfTable", e);
            System.out.println("error en ControlPanel.createPdfTable: " + e.getMessage());
        }
        return table;
    }

    /**
    * Genera un StringBuffer que contiene el codigo HTML para presentar las
    * propiedades de un FlowNodeInstance en modo informe personalizado.
    *
    * @param            fobi FlowNodeInstance
    * @param            paramsRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML del reporte
    * @see
    */
    public StringBuffer printReportTask(FlowNodeInstance fobi, SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strTaskTitle = "";
        String strDateCreated = "";
        String strCreator = "";
        String strDateModified = "";
        String strModifiedBy = "";
        String strDateEnded = "";
        String strEndedBy = "";
        try
        {
            String strProcessTitle = "";
            int intPriority = 2;
            //fobi.getFlowNodeType().getParent().getTitle()==null ?"" :fobi.getFlowNodeType().getParent().getTitle();
            ProcessInstance fpinst = fobi.getProcessInstance();
            if(null!=fpinst){
                intPriority = fpinst.getPriority();
                org.semanticwb.process.model.Process fproc = (org.semanticwb.process.model.Process)fpinst.getProcessType();
                if(null!=fproc){
                    strProcessTitle = fproc.getTitle();
                }
            }
            String strId = fobi.getId()==null
                ?""
                :fobi.getId();
            int intStatus = fobi.getStatus();
            String strStatus = getStatusDescription(paramsRequest, intStatus);
            String strPriority = getPriorityDescription(paramsRequest, intPriority);
            FlowNode fobType = fobi.getFlowNodeType();
            if(fobType!=null){
                strTaskTitle = fobType.getTitle()==null
                    ?""
                    :fobType.getTitle();
            }
            if(fobi.getCreated()!=null)
            {
                strDateCreated = String.valueOf(fobi.getCreated());
                strCreator = fobi.getCreator().getFullName()==null
                    ?""
                    :fobi.getCreator().getFullName();
            }
            if(fobi.getUpdated()!=null)
            {
                strDateModified = String.valueOf(fobi.getUpdated());
                strModifiedBy = fobi.getModifiedBy().getFullName()==null
                    ?""
                    :fobi.getModifiedBy().getFullName();
            }
            if(fobi.getEnded()!=null)
            {
                strDateEnded = String.valueOf(fobi.getEnded());
                strEndedBy = fobi.getEndedby().getFullName()==null
                    ?""
                    :fobi.getEndedby().getFullName();
            }
            sb.append("<tr >");
            sb.append("<td >" + strProcessTitle + "</td>");
            sb.append("<td >" + strId + "</td>");
            sb.append("<td >" + strTaskTitle + "</td>");
            sb.append("<td >" + strDateCreated + "</td>");
            sb.append("<td >" + strCreator + "</td>");
            sb.append("<td >" + strStatus + "</td>");
            sb.append("<td >" + strDateModified + "</td>");
            sb.append("<td >" + strModifiedBy + "</td>");
            sb.append("<td >" + strDateEnded + "</td>");
            sb.append("<td >" + strEndedBy + "</td>");
            sb.append("<td >" + strPriority + "</td>");
            sb.append("</tr>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.printReportTask", e);
            System.out.println("Error en PreConfiguredControlPanel.printReportTask:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Despliega un formato para seleccionar los filtros que deben aplicarse al
    * generar un reporte.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML del formato
    */
    public StringBuffer getReportForm(SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            SWBResourceURL url = paramsRequest.getRenderUrl();
            Vector vSelectedProcessDefinitions = getSelectedProcessDefinitions(paramsRequest);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direccionaI(action){");
            sb.append("\n      document.forms['frmSelectReport'].action = action;");
            sb.append("\n      document.forms['frmSelectReport'].submit();");
            sb.append("\n   }");
            sb.append("\n</script>");

            sb.append("<div id=\"" + DIV_ID_REPORT + "\"><div class=\"" + DIV_CLASS_PLECA + "\"><h3>" +
                    paramsRequest.getLocaleString("lblReportTitle") +
                    "</h3>");
            sb.append("<form name=\"frmSelectReport\" action=\"" +
                    paramsRequest.getRenderUrl().setAction("buildReport") +
                    "\" dojoType=\"dijit.form.Form\" method=\"POST\">");

            sb.append("<label for=\"" + INITIAL_DATE_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportIniDate") + ":</label>");
            sb.append("<input type=\"text\" name=\"" + INITIAL_DATE_REPORT_CTRL + "\" id=\"" + INITIAL_DATE_REPORT_CTRL +"\" class=\"" + INPUT_TEXT_CLASS + "\" " +
                  "dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\" />");
            sb.append("<br />");
            sb.append("<label for=\"" + END_DATE_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportEndDate") + ":</label>");
            sb.append("<input type=\"text\" name=\"" + END_DATE_REPORT_CTRL + "\" id=\"" + END_DATE_REPORT_CTRL +"\" class=\"" + INPUT_TEXT_CLASS + "\" " +
                  "dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\" />");
            sb.append("<br />");
	        sb.append("<label for=\"" + PROCESS_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportProcess") + ":</label>");
            sb.append(" <select name=\""+PROCESS_REPORT_CTRL+ ""
                + "\" id=\""+PROCESS_REPORT_CTRL+"\">");
            sb.append("<option ");
            sb.append("value=\"0\">" +
                    paramsRequest.getLocaleString("cpAllProcesses") +
                    "</option>");
            for(int i=0; i<vSelectedProcessDefinitions.size(); i++){
                org.semanticwb.process.model.Process selectedProcess = (org.semanticwb.process.model.Process)
                vSelectedProcessDefinitions.get(i);
                sb.append("<option ");
                sb.append("value=\"" + selectedProcess.getURI() + "\">"
                    + selectedProcess.getTitle() +"</option>");
            }
            sb.append("</select><br/><br/>");
            Vector vTaskStatus = BPMSProcessInstance.ClassMgr.stringToVector(ALL_STATUS);
	        sb.append("<label for=\"" + STATUS_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportStatus") + ":</label>");
            sb.append("<select name=\"" + STATUS_REPORT_CTRL +
                    "\" id=\""+ STATUS_REPORT_CTRL + "\" MULTIPLE SIZE=6>");
                sb.append("<option ");
                sb.append("value=\"-1\">" +
                        paramsRequest.getLocaleString("cpAllTaskStatus") +
                        "</option>");
            for(int i=0; i<vTaskStatus.size(); i++){
                sb.append("<option ");
                sb.append("value=\"" + vTaskStatus.get(i) + "\">" +
                        paramsRequest.getLocaleString("cpTaskStatus" +
                        vTaskStatus.get(i)) + "</option>");
            }
            sb.append("</select><br/><br/>");
            Vector vTaskPriority = BPMSProcessInstance.ClassMgr.stringToVector(ALL_PRIORITY);
	        sb.append("<label for=\"" + PRIORITY_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportPriority") + ":</label>");
            sb.append("<select name=\"" + PRIORITY_REPORT_CTRL +
                    "\" id=\""+ PRIORITY_REPORT_CTRL + "\" MULTIPLE SIZE=6>");
                sb.append("<option ");
                sb.append("value=\"-1\">" +
                        paramsRequest.getLocaleString("cpAllTaskPriority") +
                        "</option>");
            for(int i=0; i<vTaskPriority.size(); i++){
                sb.append("<option ");
                sb.append("value=\"" + vTaskPriority.get(i) + "\">" +
                        paramsRequest.getLocaleString("cpTaskPriority" +
                        vTaskPriority.get(i)) + "</option>");
            }
            sb.append("</select><br/><br/>");
            sb.append("<input type=\"SUBMIT\" class=\"" + INPUT_BUTTON_CLASS + "\" name=\"btnEdit\"  value=\"" +
                    paramsRequest.getLocaleString("btnApplyFilter") + "\"/>");
            sb.append("<input type=\"RESET\" class=\"" + INPUT_BUTTON_CLASS + "\" name=\"btnReset\" value=\"" +
                    paramsRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form></div></div>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getFilterForm", e);
            System.out.println("Error en PreConfiguredControlPanel.getReportForm:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Genera el codigo HTML necesario para presentar un listado de propiedades seleccionables
    *
    * @param            displayType int
    * @param            selectedProcess org.semanticwb.process.model.Process
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML del listado
    */
    public StringBuffer propertySelectionForm(int displayType,
            org.semanticwb.process.model.Process selectedProcess, SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strHeader = "";
        String strSelectedProps = "";
        String strTaskParams = "";
        //String strChecked = "";
        String strCtrl = "";
        String strOrderCtrl = "";

        try
        {
            Resource base = paramRequest.getResourceBase();
            //Iterator itPropType;
                if(displayType==DISPLAY_ON_LINK)
                {
                    strCtrl = TASK_LINK_INCLUDE_CTRL;
                    strOrderCtrl = TASK_LINK_INCLUDE_ORDER_CTRL;
                    strHeader = paramRequest.getLocaleString("lblSelectedTaskLinkProperties");
                    strSelectedProps = base.getAttribute("taskLinkProperties")==null
                        ?""
                        :base.getAttribute("taskLinkProperties");
                    strTaskParams = base.getAttribute(TASK_LINK_FIXED_PARAMS)==null
                        ?""
                        :base.getAttribute(TASK_LINK_FIXED_PARAMS);
                    sb.append("\n<label for=\"" + TASK_LINK_FIXED_PARAMS + "\" >*" + paramRequest.getLocaleString("lblTaskParams") + "</label><br/><br/>");
                    sb.append("\n<textarea id=\"" + TASK_LINK_FIXED_PARAMS + "\" name=\"" + TASK_LINK_FIXED_PARAMS + "\" rows=\"5\" cols=\"50\">" +
                            strTaskParams + "</textarea><br/><br/>");

                } else if(displayType==DISPLAY_ON_LEGEND){
                    strCtrl = TASK_LEGEND_INCLUDE_CTRL;
                    strOrderCtrl = TASK_LEGEND_INCLUDE_ORDER_CTRL;

                    strHeader = paramRequest.getLocaleString("lblSelectedTaskLegendProperties");
                    strSelectedProps = base.getAttribute("taskLegendProperties")==null
                        ?""
                        :base.getAttribute("taskLegendProperties");

                } else if(displayType==DISPLAY_ON_COLUMN){
                    strCtrl = TASK_COLUMN_INCLUDE_CTRL;
                    strOrderCtrl = TASK_COLUMN_INCLUDE_ORDER_CTRL;

                    strHeader = paramRequest.getLocaleString("lblSelectedTaskColumnProperties");
                    strSelectedProps = base.getAttribute("taskColumnProperties")==null
                        ?""
                        :base.getAttribute("taskColumnProperties");
                }
                java.util.List lstSelectedProps = stringToTaskPropertyList(displayType, strSelectedProps);
                //sb.append("\n<div>");
                //sb.append("\n<table >");
                sb.append(printTypeProperties(displayType,PROPERTY_TYPE_TASK_DEFINITION, selectedProcess, lstTaskDefinitionProperties, lstSelectedProps, strCtrl, strOrderCtrl));
                sb.append(printTypeProperties(displayType,PROPERTY_TYPE_TASK_INSTANCE, selectedProcess, lstTaskInstanceProperties, lstSelectedProps, strCtrl, strOrderCtrl));
                java.util.List lstProcessArtifactsProperties = getProcessArtifactDefinitionProperties(selectedProcess,displayType);
                sb.append(printTypeProperties(displayType,PROPERTY_TYPE_ARTIFACT, selectedProcess, lstProcessArtifactsProperties, lstSelectedProps, strCtrl, strOrderCtrl));
                sb.append("\n</table><br/><br/>");
                //sb.append("</div><br/>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.propertySelectionForm", e);
            System.out.println("Error en PreConfiguredControlPanel.propertySelectionForm:" + e.getMessage());
		}
        return sb;

    }

    /**
    * Genera el codigo HTML necesario para presentar un listado de propiedades seleccionables de la tarea.
    * Las propiedaes son de un tipo de despliegue: vinculo, leyenda o columnas.
    *
    * @param            displayType int
    * @param            selectedProcess org.semanticwb.process.model.Process
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML del listado
    */
    public StringBuffer printSelectedPropertiesByType(int displayType,
            org.semanticwb.process.model.Process selectedProcess, SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strHeader = "";
        String strSelectedProps = "";
        String strTaskParams = "";
        try
        {
           Resource base = paramRequest.getResourceBase();
           if(displayType==DISPLAY_ON_LINK){
                strHeader = paramRequest.getLocaleString("lblSelectedTaskLinkProperties");
                strSelectedProps = base.getAttribute("taskLinkProperties")==null
                    ?""
                    :base.getAttribute("taskLinkProperties");
                strTaskParams = base.getAttribute(TASK_LINK_FIXED_PARAMS)==null
                    ?""
                    :base.getAttribute(TASK_LINK_FIXED_PARAMS);
                sb.append("\n<label for=\"" + TASK_LINK_FIXED_PARAMS + "\" >*" + paramRequest.getLocaleString("lblTaskParams") + "</label>");
                sb.append("\n<textarea id=\"" + TASK_LINK_FIXED_PARAMS + "\" name=\"" + TASK_LINK_FIXED_PARAMS + "\" disabled=\"disabled\" rows=\"5\" cols=\"50\">" +
                        strTaskParams + "</textarea><br/><br/>");
            } else if(displayType==DISPLAY_ON_LEGEND){
                strHeader = paramRequest.getLocaleString("lblSelectedTaskLegendProperties");
                strSelectedProps = base.getAttribute("taskLegendProperties")==null
                    ?""
                    :base.getAttribute("taskLegendProperties");
            } else if(displayType==DISPLAY_ON_COLUMN){
                strHeader = paramRequest.getLocaleString("lblSelectedTaskColumnProperties");
                strSelectedProps = base.getAttribute("taskColumnProperties")==null
                    ?""
                    :base.getAttribute("taskColumnProperties");
            }
            //tabla de prop de definicion de tarea
            //sb.append("\n<div>");
            sb.append("\n<p>" + paramRequest.getLocaleString("lblDefaultLinkNote") + "</p>");
            sb.append("\n<table >");
            sb.append("\n<tr><th colspan=\"3\">" + strHeader + "</td></tr>");
            sb.append("\n<tr>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblSelectedProperties") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyType") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyOrder") + "</th>");
            sb.append("\n</tr>");
            java.util.List lstProps = stringToTaskPropertyList(displayType, strSelectedProps);
            Iterator itLstProps = lstProps.iterator();
            while(itLstProps.hasNext())
            {
                TaskProperty tskProp = (TaskProperty)itLstProps.next();
                sb.append("\n<tr><td>" + tskProp.getDisplayName() + "</td><td>" + tskProp.getType() + "</td>");
                sb.append("\n<td>" + String.valueOf(tskProp.getDisplayOrder()) + "</td></tr>");
            }
            sb.append("\n</table><br/><br/>");
            //sb.append("</div><br/>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.printSelectedPropertiesByType", e);
            System.out.println("Error en PreConfiguredControlPanel.printSelectedPropertiesByType:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Resguarda en la base del recurso la configuración del vinculo y envía un
    * mensaje de exito o error al usuario, segun corresponda.
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		String con mensaje de exito o error
    */
    public String setTaskLinkConfiguration(HttpServletRequest request,SWBParamRequest paramRequest)
    {
        StringBuffer ret = new StringBuffer();
        String strSelectedLinkProperties = "";
        String strTaskParams = "";
        String msg = "";
        String errorMsg = null;
        
        try{
            Resource base = paramRequest.getResourceBase();
            //propiedades del link y del artefacto
            errorMsg = (new StringBuilder()).append(paramRequest.getLocaleString("msgErrLinkConfigUpdateResource")).append(" "
                    + base.getId()).toString();

            strTaskParams = request.getParameter(TASK_LINK_FIXED_PARAMS)==null
                ?""
                :request.getParameter(TASK_LINK_FIXED_PARAMS);
            base.setAttribute(TASK_LINK_FIXED_PARAMS, strTaskParams);
            
            if(request.getParameterValues(TASK_LINK_INCLUDE_CTRL) != null)
            {
                Vector vSelectedProps = new Vector();
                String [] strIncludeProperties = request.getParameterValues(TASK_LINK_INCLUDE_CTRL);
                for(int i=0; i<strIncludeProperties.length; i++)
                {
                    String[] strTemp = strIncludeProperties[i].split("\\|");
                    String strPropType = strTemp[0];
                    String strPropId = strTemp[1];
                    String strTmpOrder = TASK_LINK_INCLUDE_ORDER_CTRL + "|" + strIncludeProperties[i];
                    int intOrderValue = request.getParameter(strTmpOrder)==null
                            ?0
                            :Integer.parseInt(request.getParameter(strTmpOrder));
                    TaskProperty tskTemp = new TaskProperty(strPropType,strPropId,DISPLAY_ON_LINK,intOrderValue);
                    vSelectedProps.add(tskTemp);
                }
                //ordenar vector
                sortByOrder(vSelectedProps);
                //generar String ordenado
                Enumeration enSelected = vSelectedProps.elements();
                while(enSelected.hasMoreElements())
                {
                    TaskProperty tskTemp = (TaskProperty)enSelected.nextElement();
                    strSelectedLinkProperties = strSelectedLinkProperties + tskTemp.getType() + "|" +
                            tskTemp.getId() + "|" + tskTemp.getDisplayOrder() + "|;";
                }
            }
            String strNewHref = strDefaultHref + strSelectedLinkProperties;
            base.setAttribute("taskLinkProperties", strNewHref);
            base.updateAttributesToDB();

            msg = (new StringBuilder()).append(
                    paramRequest.getLocaleString("msgOkLinkConfigUpdateResource")).append(" "
                    + base.getId()).toString();
            ret.append((new StringBuilder()).append(
                "<script language=\"JavaScript\">\n alert('").append(msg).append("');\n").append("</script>\n").toString());
            ret.append((new StringBuilder()).append("<script " +
                    "language=\"JavaScript\">\n   location='").append(paramRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';\n").append("</script>\n").toString());
        } catch(Exception e){
            ret.append((new StringBuilder()).append(
                    "\n<script language=\"JavaScript\">  \nalert('").append(errorMsg).append("');").append("\n</script>").toString());
            ret.append((new StringBuilder()).append("\n<script " +
                    "language=\"JavaScript\">  \nlocation='").append(
                    paramRequest.getRenderUrl().setAction("admin").toString()).append("';").append("\n</script>").toString());
            log.error("Error en PreConfiguredControlPanel.setTaskLinkConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.setTaskLinkConfiguration:" + e.getMessage());
		}
        return ret.toString();
    }

    /**
    * Resguarda en la base del recurso la configuración de las columnas y envía un
    * mensaje de exito o error al usuario, segun corresponda.
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		String con mensaje de exito o error
    */
    public String setTaskColumnConfiguration(HttpServletRequest request,SWBParamRequest paramRequest)
    {
        StringBuffer ret = new StringBuffer();
        String strSelectedColumnProperties = "";
        String msg = null;
        String errorMsg = null;

        try{
            Resource base = paramRequest.getResourceBase();

            errorMsg = (new StringBuilder()).append(paramRequest.getLocaleString("msgErrColumnConfigUpdateResource")).append(" "
                    + base.getId()).toString();

            if(request.getParameterValues(TASK_COLUMN_INCLUDE_CTRL) != null)
            {
                Vector vSelectedProps = new Vector();
                String [] strIncludeProperties = request.getParameterValues(TASK_COLUMN_INCLUDE_CTRL);
                for(int i=0; i<strIncludeProperties.length; i++)
                {
                    String[] strTemp = strIncludeProperties[i].split("\\|");
                    String strPropType = strTemp[0];
                    String strPropId = strTemp[1];
                    String strTmpOrder = TASK_COLUMN_INCLUDE_ORDER_CTRL + "|" + strIncludeProperties[i];
                    int intOrderValue = request.getParameter(strTmpOrder)==null
                            ?0
                            :Integer.parseInt(request.getParameter(strTmpOrder));
                    TaskProperty tskTemp = new TaskProperty(strPropType,strPropId,DISPLAY_ON_COLUMN,intOrderValue);
                    vSelectedProps.add(tskTemp);                    
                }
                //ordenar vector
                sortByOrder(vSelectedProps);
                //generar String ordenado
                Enumeration enSelected = vSelectedProps.elements();
                while(enSelected.hasMoreElements())
                {
                    TaskProperty tskTemp = (TaskProperty)enSelected.nextElement();
                    strSelectedColumnProperties = strSelectedColumnProperties + tskTemp.getType() + "|" +
                            tskTemp.getId() + "|" + tskTemp.getDisplayOrder() + "|;";
                }
            }

            if(strSelectedColumnProperties!=null && !strSelectedColumnProperties.equalsIgnoreCase(""))
            {
                base.setAttribute("taskColumnProperties", strSelectedColumnProperties);
            }
            base.updateAttributesToDB();

            msg = (new StringBuilder()).append(
                    paramRequest.getLocaleString("msgOkColumnConfigUpdateResource")).append(" "
                    + base.getId()).toString();
            ret.append((new StringBuilder()).append(
                "<script language=\"JavaScript\">\n alert('").append(msg).append("');\n").append("</script>\n").toString());
            ret.append((new StringBuilder()).append("<script " +
                    "language=\"JavaScript\">\n   location='").append(paramRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';\n").append("</script>\n").toString());
        } catch(Exception e){
            ret.append((new StringBuilder()).append(
                    "\n<script language=\"JavaScript\">  \nalert('").append(errorMsg).append("');").append("\n</script>").toString());
            ret.append((new StringBuilder()).append("\n<script " +
                    "language=\"JavaScript\">  \nlocation='").append(
                    paramRequest.getRenderUrl().setAction("admin").toString()).append("';").append("\n</script>").toString());
            log.error("Error en PreConfiguredControlPanel.setTaskColumnConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.setTaskLegendConfiguration:" + e.getMessage());
		}
        return ret.toString();
    }


    /**
    * Resguarda en la base del recurso la configuración de la leyenda y envía un
    * mensaje de exito o error al usuario, segun corresponda.
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		String con mensaje de exito o error
    */
    public String setTaskLegendConfiguration(HttpServletRequest request,SWBParamRequest paramRequest)
    {
        StringBuffer ret = new StringBuffer();
        String strSelectedLegendProperties = "";
        String msg = null;
        String errorMsg = null;

        try{
            Resource base = paramRequest.getResourceBase();

            errorMsg = (new StringBuilder()).append(paramRequest.getLocaleString("msgErrLegendConfigUpdateResource")).append(" "
                    + base.getId()).toString();

            if(request.getParameterValues(TASK_LEGEND_INCLUDE_CTRL) != null)
            {
                Vector vSelectedProps = new Vector();
                String [] strIncludeProperties = request.getParameterValues(TASK_LEGEND_INCLUDE_CTRL);
                for(int i=0; i<strIncludeProperties.length; i++)
                {
                    String[] strTemp = strIncludeProperties[i].split("\\|");
                    String strPropType = strTemp[0];
                    String strPropId = strTemp[1];
                    String strTmpOrder = TASK_LEGEND_INCLUDE_ORDER_CTRL + "|" + strIncludeProperties[i];
                    int intOrderValue = request.getParameter(strTmpOrder)==null
                            ?0
                            :Integer.parseInt(request.getParameter(strTmpOrder));
                    TaskProperty tskTemp = new TaskProperty(strPropType,strPropId,DISPLAY_ON_LEGEND,intOrderValue);
                    vSelectedProps.add(tskTemp);
                }
                //ordenar vector
                sortByOrder(vSelectedProps);
                //generar String ordenado
                Enumeration enSelected = vSelectedProps.elements();
                while(enSelected.hasMoreElements())
                {
                    TaskProperty tskTemp = (TaskProperty)enSelected.nextElement();
                    strSelectedLegendProperties = strSelectedLegendProperties + tskTemp.getType() + "|" +
                            tskTemp.getId() + "|" + tskTemp.getDisplayOrder() + "|;";
                }
            }
            if(strSelectedLegendProperties!=null && !strSelectedLegendProperties.equalsIgnoreCase(""))
            {
                base.setAttribute("taskLegendProperties", strSelectedLegendProperties);
            }
            base.updateAttributesToDB();

            msg = (new StringBuilder()).append(
                    paramRequest.getLocaleString("msgOkLegendConfigUpdateResource")).append(" "
                    + base.getId()).toString();
            ret.append((new StringBuilder()).append(
                "<script language=\"JavaScript\">\n alert('").append(msg).append("');\n").append("</script>\n").toString());
            ret.append((new StringBuilder()).append("<script " +
                    "language=\"JavaScript\">\n   location='").append(paramRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';\n").append("</script>\n").toString());
        } catch(Exception e){
            ret.append((new StringBuilder()).append(
                "\n<script language=\"JavaScript\">  \nalert('").append(errorMsg).append("');").append("\n</script>").toString());
            ret.append((new StringBuilder()).append("\n<script " +
                "language=\"JavaScript\">  \nlocation='").append(
                paramRequest.getRenderUrl().setAction("admin").toString()).append("';").append("\n</script>").toString());
            log.error("Error en PreConfiguredControlPanel.setTaskLegendConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.setTaskLegendConfiguration:" + e.getMessage());
		}
        return ret.toString();
    }


   /**
	 * Despliega un formato para personalizar la bandeja (ordenamiento)
	 *
     * @param           request HttpServletRequest
     * @param           response HttpServletResponse
     * @param           paramRequest SWBParamRequest
	 * @return      	StringBuffer con codigo HTML para despliegue
	 */
    public StringBuffer customizeDisplay(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        StringBuffer sbPrint = new StringBuffer();
        int intSortType = 0;
        try
        {
            intSortType = getSortCriteria(paramRequest);
            SWBResourceURL url = paramRequest.getRenderUrl();
            boolean bClosedFilter = isClosedStatusFilterActive(paramRequest);
            sbPrint.append("\n<script type=\"text/javascript\">");
            sbPrint.append("\n   function direccionaP(action){");
            sbPrint.append("\n      document.forms['frmAdmin'].action = action;");
            sbPrint.append("\n      document.forms['frmAdmin'].submit();");
            sbPrint.append("\n   }");
            sbPrint.append("\n</script>");
            sbPrint.append("<div id=\"" + DIV_ID_CUSTOMIZE + "\"><div class=\"" + DIV_CLASS_PLECA + "\"><h3>" +
                    paramRequest.getLocaleString("lblCustomizeTitle") +
                    "</h3>");
            sbPrint.append("<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("updateCustomization")
                    + "\" method=\"POST\">");
	        sbPrint.append("<label for=\"" + SORTING_CTRL +"\" >" + paramRequest.getLocaleString("lblSort") + ":</label>");
            sbPrint.append(" <select name=\"" +SORTING_CTRL+ "" + "\" id=\""+SORTING_CTRL+"\">");
            for(int i=0; i<BPMSTask.ClassMgr.MAX_SORT; i++){
                sbPrint.append("<option ");
                if(intSortType==i)
                {
                    sbPrint.append(" selected ");
                }
                sbPrint.append("value=\"" + i + "\">" + paramRequest.getLocaleString("cpSortType"+i) + "</option>");
            }
            sbPrint.append("</select><br/><br/>");
            if(bClosedFilter)
            {
                sbPrint.append("<div class=\"" + DIV_CLASS_RADIOBUTTON + "\"><p class=\"" + P_CLASS_CLOSED + "\">" + paramRequest.getLocaleString("lblClosedFilter") + ":</p></div>");
                sbPrint.append("<div class=\"" + DIV_CLASS_RADIOBUTTON + "\"><input type=\"RADIO\" id=\"" + CLOSED_TASKS_FILTER_CTRL + "\""  + "name=\"" + CLOSED_TASKS_FILTER_CTRL + "\" " + " value=\"0\" class=\"radio\">");
                sbPrint.append("<label for=\"" + CLOSED_TASKS_FILTER_CTRL + "\">" + paramRequest.getLocaleString("cpClosedFilter0") + "</label></div>");
                sbPrint.append("<div class=\"" + DIV_CLASS_RADIOBUTTON + "\"><input type=\"RADIO\" id=\"" + CLOSED_TASKS_FILTER_CTRL + "\""  + "name=\"" + CLOSED_TASKS_FILTER_CTRL + "\" " + " value=\"1\" checked class=\"radio\">");
                sbPrint.append("<label for=\"" + CLOSED_TASKS_FILTER_CTRL +"\">" + paramRequest.getLocaleString("cpClosedFilter1") + "</label></div>");
            } else {
                sbPrint.append("<div class=\"" + DIV_CLASS_RADIOBUTTON + "\"><p class=\"" + P_CLASS_CLOSED + "\">" + paramRequest.getLocaleString("lblClosedFilter") + ":</p></div>");
                sbPrint.append("<div class=\"" + DIV_CLASS_RADIOBUTTON + "\"><input type=\"RADIO\" id=\"" + CLOSED_TASKS_FILTER_CTRL + "\""  + "name=\"" + CLOSED_TASKS_FILTER_CTRL +  "\" " + " value=\"0\" checked class=\"radio\">");
                sbPrint.append("<label for=\"" + CLOSED_TASKS_FILTER_CTRL + "\">" + paramRequest.getLocaleString("cpClosedFilter0") + "</label></div>");
                sbPrint.append("<div class=\"" + DIV_CLASS_RADIOBUTTON + "\"><input type=\"RADIO\" id=\"" + CLOSED_TASKS_FILTER_CTRL + "\""  + "name=\"" + CLOSED_TASKS_FILTER_CTRL + "\" " + " value=\"1\" class=\"radio\">");
                sbPrint.append("<label for=\"" + CLOSED_TASKS_FILTER_CTRL + "\">" + paramRequest.getLocaleString("cpClosedFilter1") + "</label></div>");
            }
            sbPrint.append("<br/><br/>");
            sbPrint.append("<input type=\"SUBMIT\" name=\"btnSave\" class=\"" + INPUT_BUTTON_CLASS + "\" value=\"" +
                    paramRequest.getLocaleString("btnSaveCustomize") + "\"/>");
            sbPrint.append("<input type=\"RESET\" name=\"btnReset\" class=\"" + INPUT_BUTTON_CLASS + "\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") +"\"/>");
            sbPrint.append("<input type=\"SUBMIT\" name=\"btnBack2View\" class=\"" + INPUT_BUTTON_CLASS + "\" " +
                    " onclick=\"direccionaP('" + url.setAction("goToView") + "');\"" +
                    " value=\"" + paramRequest.getLocaleString("btnBack") + "\"/>");
            sbPrint.append("</form>");
            sbPrint.append("</div></div>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.customizeDisplay", e);
            System.out.println("Error en PreConfiguredControlPanel.customizeDisplay:" + e.getMessage());
        }
        return sbPrint;
    }


    /**
	 * Parsea la información que configuró el usuario final durante la
     * personalización y la devuelve en un Hashtable.
	 *
     * @param           customizationData String
	 * @return      	Hashtable
	 */
    public Hashtable getCustomizedData(String customizationData){
        Hashtable htable = new Hashtable();
        try
        {
            if(customizationData!=null && !customizationData.equalsIgnoreCase(""))
            {
                String strSplit[] = customizationData.split("\\|");
                for(int index=0; index<strSplit.length-1; index=index+2)
                {
                    String strKey = strSplit[index];
                    String strValue = strSplit[index+1];
                    htable.put(strKey, strValue);
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getCustomizedData", e);
            System.out.println("Error en PreConfiguredControlPanel.getCustomizedData:" + e.getMessage());
		}
        return htable;
    }

    /**
    * Obtiene de la base del recurso la configuración de las columnas y genera
    * el codigo HTML necesario para desplegar un formato configurable con dicha información.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getTaskColumnConfiguration(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        try{
            Resource base = paramRequest.getResourceBase();
            SWBResourceURL url = paramRequest.getRenderUrl();
            String strCPProcessDefinitions = base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            org.semanticwb.process.model.Process selectedProcess = getProcessByURI(strCPProcessDefinitions,paramRequest);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function isNumeric(value) {");
            sb.append("\n      var bOk = false;");
            sb.append("\n      if (value != null) {");
            sb.append("\n         var regExp = /^\\d+$/;");
            sb.append("\n         bOk  = regExp.test(value.toString());");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateNumeric(elem) {");
            sb.append("\n      var bOk = true;");
            sb.append("\n      if (!isNumeric(elem.value)) {");
            sb.append("\n         alert('" + paramRequest.getLocaleString("jsMsg1") +  "');");
            sb.append("\n         elem.value='';");
            sb.append("\n         bOk = false;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direcciona(accion){");
            sb.append("\n      if (accion=='ApplyTaskColumnConfiguration'){");
            sb.append("\n         var answer = confirm('" + paramRequest.getLocaleString("msgConfirmColumnConfiguration") + "');");
            sb.append("\n         if (answer){");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("ApplyTaskColumnConfiguration") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n         }");
            sb.append("\n      } else {");
            sb.append("\n         document.forms['frmAdmin'].action =  '" + url.setAction("goView") + "';");
            sb.append("\n         document.forms['frmAdmin'].submit();");
            sb.append("\n      }");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<div class=\"" + DIV_CLASS_SWBFORM + "\">");
            sb.append("\n<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("ApplyTaskColumnConfiguration") +
                    "\" method=\"POST\" ><br/><br/>");
            sb.append("\n<div>");
            sb.append("\n<table >");
            sb.append("\n<tr><th colspan=\"4\">" + paramRequest.getLocaleString("lblTaskColumnProperties") + "</td></tr>");
            sb.append("\n<tr>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblSelectProperties") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyType") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyOrder") + "</th>");
            sb.append("\n</tr>");
            sb.append(propertySelectionForm(DISPLAY_ON_COLUMN, selectedProcess, paramRequest));
            sb.append("\n</table>");
            sb.append("</div><br/>");
            //regresar sin efectuar los cambios
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnNoChanges\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnNoChanges") + "\"" +
                    " onclick=\"direcciona('goView');\" />");
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnApplyTaskColumnConfiguration\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnApplyTaskColumnConfiguration") +
                    "\" onclick=\"direcciona('ApplyTaskColumnConfiguration');\" />");
            sb.append("<input type=\"RESET\" " + " name=\"btnReset\" class=\"" + INPUT_BUTTON_CLASS + "\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form>");
            sb.append("</div>");

        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTaskColumnConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.getTaskColumnConfiguration:" + e.getMessage());
		}
        return sb;
    }


    /**
    * Obtiene de la base del recurso la configuración de la leyenda y genera
    * el codigo HTML necesario para desplegar un formato configurable con dicha información.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getTaskLegendConfiguration(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        try{
            Resource base = paramRequest.getResourceBase();
            SWBResourceURL url = paramRequest.getRenderUrl();
            String strCPProcessDefinitions = base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            org.semanticwb.process.model.Process selectedProcess = getProcessByURI(strCPProcessDefinitions,paramRequest);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function isNumeric(value) {");
            sb.append("\n      var bOk = false;");
            sb.append("\n      if (value != null) {");
            sb.append("\n         var regExp = /^\\d+$/;");
            sb.append("\n         bOk  = regExp.test(value.toString());");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateNumeric(elem) {");
            sb.append("\n      var bOk = true;");
            sb.append("\n      if (!isNumeric(elem.value)) {");
            sb.append("\n         alert('" + paramRequest.getLocaleString("jsMsg1") +  "');");
            sb.append("\n         elem.value='';");
            sb.append("\n         bOk = false;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function getCheckedBoxesNumber(){");
            sb.append("\n      var selected_checkboxes = 0;");
            sb.append("\n      var checkboxObj = document.getElementById('" + TASK_LEGEND_INCLUDE_CTRL + "');");
            sb.append("\n      for (var i=0; i < checkboxObj.length; i++){");
            sb.append("\n        if (checkboxObj[i].checked) {");
            sb.append("\n          selected_checkboxes++;");
            sb.append("\n        }");
            sb.append("\n      }");
            sb.append("\n      return selected_checkboxes;");
            sb.append("\n   }");
            sb.append("\n   function validateForm(){");
            sb.append("\n      var vMsg = '';");
            sb.append("\n      var bOk = false;");
            sb.append("\n      var countChkBxs = getCheckedBoxesNumber();");
            sb.append("\n      if(countChkBxs<1){");
            sb.append("\n         vMsg =  vMsg + '" + paramRequest.getLocaleString("jsMsg6") + "';");
            sb.append("\n      }");
            sb.append("\n      if(vMsg.length>1){");
            sb.append("\n         alert(vMsg);");
            sb.append("\n      } else {");
            sb.append("\n         bOk = true;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direcciona(accion){");
            sb.append("\n      if (accion=='ApplyTaskLegendConfiguration'){");
            sb.append("\n         var answer = confirm('" + paramRequest.getLocaleString("msgConfirmLegendConfiguration") + "');");
            sb.append("\n         if (answer){");
            sb.append("\n            var bValidated = validateForm();");
            sb.append("\n            if (bValidated==true){");
            sb.append("\n               document.forms['frmAdmin'].action = '" + url.setAction("ApplyTaskLegendConfiguration") + "';");
            sb.append("\n               document.forms['frmAdmin'].submit();");
            sb.append("\n            }");
            sb.append("\n         }");
            sb.append("\n      } else {");
            sb.append("\n         document.forms['frmAdmin'].action = '" + url.setAction("goView") + "';");
            sb.append("\n         document.forms['frmAdmin'].submit();");
            sb.append("\n      }");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<div class=\"" + DIV_CLASS_SWBFORM + "\">");
            //Validar 
            sb.append("\n<form name=\"frmAdmin\" action=\"\" method=\"POST\" ><br/><br/>");
            sb.append("\n<div>");
            sb.append("\n<table >");
            sb.append("\n<tr><th colspan=\"4\">" + paramRequest.getLocaleString("lblTaskLegendProperties") + "</td></tr>");
            sb.append("\n<tr>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblSelectProperties") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyType") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyOrder") + "</th>");
            sb.append("\n</tr>");
            sb.append(propertySelectionForm(DISPLAY_ON_LEGEND, selectedProcess, paramRequest));
            sb.append("\n</table>");
            sb.append("</div><br/>");
            //regresar sin efectuar los cambios
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnNoChanges\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnNoChanges") + "\"" +
                    " onclick=\"direcciona('goView');\" />");
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnApplyTaskLegendConfiguration\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnApplyTaskLegendConfiguration") +
                    "\" onclick=\"direcciona('ApplyTaskLegendConfiguration');\" />");
            sb.append("<input type=\"RESET\" " + " name=\"btnReset\" class=\"" + INPUT_BUTTON_CLASS + "\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form>");
            sb.append("</div>");

        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTaskLegendConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.getTaskLegendConfiguration:" + e.getMessage());
		}
        return sb;
    }


    /**
    * Obtiene de la base del recurso la configuración del vinculo y genera
    * el codigo HTML necesario para desplegar un formato configurable con dicha información.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getTaskLinkConfiguration(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        try{
            Resource base = paramRequest.getResourceBase();
            SWBResourceURL url = paramRequest.getRenderUrl();
            String strCPProcessDefinitions = base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            org.semanticwb.process.model.Process selectedProcess = getProcessByURI(strCPProcessDefinitions,paramRequest);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function isNumeric(value) {");
            sb.append("\n      var bOk = false;");
            sb.append("\n      if (value != null) {");
            sb.append("\n         var regExp = /^\\d+$/;");
            sb.append("\n         bOk  = regExp.test(value.toString());");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateNumeric(elem) {");
            sb.append("\n      var bOk = true;");
            sb.append("\n      if (!isNumeric(elem.value)) {");
            sb.append("\n         alert('" + paramRequest.getLocaleString("jsMsg1") +  "');");
            sb.append("\n         elem.value='';");
            sb.append("\n         bOk = false;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direcciona(accion){");
            sb.append("\n      if (accion=='ApplyTaskLinkConfiguration'){");
            sb.append("\n         var answer = confirm('" + paramRequest.getLocaleString("msgConfirmLinkConfiguration") + "');");
            sb.append("\n         if (answer){");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("ApplyTaskLinkConfiguration") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n         } else {");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("goView") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n         }");
            sb.append("\n      } else {");
            sb.append("\n         document.forms['frmAdmin'].action = '" + url.setAction("goView") + "';");
            sb.append("\n         document.forms['frmAdmin'].submit();");
            sb.append("\n      }");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<div class=\"" + DIV_CLASS_SWBFORM + "\">");
            sb.append("\n<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("ApplyTaskLinkConfiguration") +
                    "\" method=\"POST\" ><br/><br/>");

            sb.append("\n<div>");
            sb.append("\n<table >");
            sb.append("\n<tr><th colspan=\"4\">" + paramRequest.getLocaleString("lblTaskLinkProperties") +  "</td></tr>");
            sb.append("\n<tr>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblSelectProperties") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyType") + "</th>");
            sb.append("\n<th>" + paramRequest.getLocaleString("lblPropertyOrder") + "</th>");
            sb.append("\n</tr>");
            sb.append(propertySelectionForm(DISPLAY_ON_LINK, selectedProcess, paramRequest));
            sb.append("\n</table>");
            sb.append("</div><br/>");
            //regresar sin efectuar los cambios
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnNoChanges\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnNoChanges") + "\"" +
                    " onclick=\"direcciona('goView');\" />");
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnApplyTaskLinkConfiguration\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnApplyTaskLinkConfiguration") +
                    "\" onclick=\"direcciona('ApplyTaskLinkConfiguration');\" />");
            sb.append("<input type=\"RESET\" " + " name=\"btnReset\" class=\"" + INPUT_BUTTON_CLASS + "\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form>");
            sb.append("</div>");

        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTaskLinkConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.getTaskLinkConfiguration:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Reestablecer en el recurso la configuracion por defecto, exceptuando el
    * proceso y el titulo.
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public String setDefaultConfiguration(HttpServletRequest request,
            SWBParamRequest paramRequest)
    {
        StringBuffer ret = new StringBuffer();
        String msg = null;
        String errorMsg = null;

        try{
            Resource base = paramRequest.getResourceBase();
            String strCPProcessDefinitions = request.getParameter(PROCESS_SELECTION_CTRL);
            String strTitle = request.getParameter(TITLE_SELECTION_CTRL)==null
                    ?""
                    :request.getParameter(TITLE_SELECTION_CTRL);
            errorMsg = (new StringBuilder()).append(paramRequest.getLocaleString("msgErrDefaultConfigUpdateResource")).append(" "
                    + base.getId()).toString();

            if(strCPProcessDefinitions!=null && !strCPProcessDefinitions.equalsIgnoreCase(""))
            {
                base.setAttribute(PROCESS_SELECTION_CTRL,strCPProcessDefinitions);
            }
            if(strTitle!=null && !strTitle.equalsIgnoreCase(""))
            {
                base.setAttribute(TITLE_SELECTION_CTRL, strTitle);
            }
            base.setAttribute(TASK_PER_PAGE_CONTROL, String.valueOf(intDefaultRowsPerPage));
            base.setAttribute("taskLinkProperties", strDefaultHref);
            base.setAttribute("taskLegendProperties", strDefaultLegend);
            base.setAttribute("taskColumnProperties", strDefaultColumns);
            base.setAttribute(TASK_LINK_FIXED_PARAMS, "");
            base.updateAttributesToDB();

            msg = (new StringBuilder()).append(
                    paramRequest.getLocaleString("msgOkDefaultConfigUpdateResource")).append(" "
                    + base.getId()).toString();
            ret.append((new StringBuilder()).append(
                "<script language=\"JavaScript\">\n alert('").append(msg).append("');\n").append("</script>\n").toString());
            ret.append((new StringBuilder()).append("<script " +
                    "language=\"JavaScript\">\n   location='").append(paramRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';\n").append("</script>\n").toString());
        } catch(Exception e){
            ret.append((new StringBuilder()).append(
                    "\n<script language=\"JavaScript\">  \nalert('").append(errorMsg).append("');").append("\n</script>").toString());
            ret.append((new StringBuilder()).append("\n<script " +
                    "language=\"JavaScript\">  \nlocation='").append(
                    paramRequest.getRenderUrl().setAction("admin").toString()).append("';").append("\n</script>").toString());
            log.error("Error en PreConfiguredControlPanel.setCurrentConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.setCurrentConfiguration:" + e.getMessage());
		}
        return ret.toString();
    }

    /**
    * Genera el codigo HTML necesario para desplegar las caracteristicas de la
    * configuracion por defecto.
    * 
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getDefaultConfiguration(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strChecked = "";

        try{
            org.semanticwb.process.model.Process selectedProcess = null;
            Resource base = paramRequest.getResourceBase();
            SWBResourceURL url = paramRequest.getRenderUrl();
            String strCPProcessDefinitions = base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            String strCPTitle = base.getAttribute(TITLE_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(TITLE_SELECTION_CTRL);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direcciona(accion){");
            sb.append("\n      if (accion=='ApplyDefaultConfiguration'){");
            sb.append("\n         var answer = confirm('" + paramRequest.getLocaleString("msgConfirmDefaultConfiguration") + "');");
            sb.append("\n         if (answer){");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("ApplyDefaultConfiguration") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n         } else {");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("goView") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n         }");
            sb.append("\n      } else {");
            sb.append("\n         document.forms['frmAdmin'].action = '" + url.setAction("goView") + "';");
            sb.append("\n         document.forms['frmAdmin'].submit();");
            sb.append("\n      }");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<div class=\"" + DIV_CLASS_SWBFORM + "\">");
            sb.append("\n<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("ApplyDefaultConfiguration") +
                    "\" method=\"POST\" ><br/><br/>");
            //TODO:Al seleccionar proceso, si titulo esta vacio, agregar nombre del proceso
            Vector vProcessDefinitions = BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(paramRequest);
            sb.append("<br />");
            sb.append("\n<p>" + paramRequest.getLocaleString("lblDefaultArtifact") + "</p><br/>");
            sb.append("<label for=\"" + PROCESS_SELECTION_CTRL +"\" >" + paramRequest.getLocaleString("lblProcessDefinitions") + ":</label>");
            sb.append("<select name=\"" + PROCESS_SELECTION_CTRL + "\" id=\"" + PROCESS_SELECTION_CTRL +"\" />");
            //sb.append("<option value=\"0\">" + paramRequest.getLocaleString("cpAllProcesses") + "</option>");
            for(int i=0; i<vProcessDefinitions.size(); i++){
                org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) vProcessDefinitions.get(i);
                if(strCPProcessDefinitions.equalsIgnoreCase(process.getURI()))
                {
                    strChecked = "selected";
                    selectedProcess = process;
                } else {
                    strChecked = "";
                }
                sb.append("<option value=\"" + selectedProcess.getURI() + "\" " + strChecked + " >" + selectedProcess.getTitle() +"</option>");
            }
            sb.append("</select><br/>");
            sb.append("\n<div>* " + paramRequest.getLocaleString("lblTitle") +
                    "<br/> \n<input type=\"TEXT\" id=\"" + TITLE_SELECTION_CTRL+ "\" name=\"" + TITLE_SELECTION_CTRL+ "\"" +
                    " size=\"50\" maxlength=\"50\" " +
                    " label=\"" + paramRequest.getLocaleString("lblTitle")
                    + "\" value=\"" + strCPTitle + "\"/><br/><br/></div>");
            //Numero de tareas por pagina
            sb.append("\n<div>*" + paramRequest.getLocaleString("lblTasksPerPageNumber") + " " + intDefaultRowsPerPage + "</div>");
            //propiedades seleccionadas del vinculo y acceso para modificar
            sb.append("\n<div><ul>" + paramRequest.getLocaleString("lblTaskLinkProperties"));
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_DEFINITION +  ".url</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".encodedURI</li>");
            sb.append("\n</ul></div>");
            //propiedades seleccionadas de la leyenda y acceso para modificar
            sb.append("\n<div><ul>" + paramRequest.getLocaleString("lblTaskLegendProperties"));
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_DEFINITION +  ".title</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".id</li>");
            sb.append("\n</ul></div>");
            //propiedades seleccionadas en columna y acceso para modificar
            sb.append("\n<div><ul>" + paramRequest.getLocaleString("lblTaskColumnProperties"));
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".status</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".created</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".creator</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".modified</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".modifiedBy</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".ended</li>");
            sb.append("\n<li>" + PROPERTY_TYPE_TASK_INSTANCE + ".endedBy</li>");
            sb.append("\n</ul></div>");
            //regresar sin efectuar los cambios
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnNoChanges\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnNoChanges") + "\"" +
                    " onclick=\"direcciona('goView');\" />");
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnApplyDefaultConfiguration\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnApplyDefaultConfiguration") +
                    "\" onclick=\"direcciona('ApplyDefaultConfiguration');\" />");
            sb.append("<input type=\"RESET\" " + " name=\"btnReset\" class=\"" + INPUT_BUTTON_CLASS + "\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form>");
            sb.append("</div>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getDefaultConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.getDefaultConfiguration:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Imprime el codigo Javascript para las validaciones del formato de admon de configuracion actual
    *
    * @param            paramRequest SWBParamRequest
    * @param            type int
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getValidatingScriptConfiguration(SWBParamRequest paramRequest, int type)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function isNumeric(value) {");
            sb.append("\n      var bOk = false;");
            sb.append("\n      if (value != null) {");
            sb.append("\n         var regExp = /^\\d+$/;");
            sb.append("\n         bOk  = regExp.test(value.toString());");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateNumeric(elem) {");
            sb.append("\n      var bOk = true;");
            sb.append("\n      if (!isNumeric(elem.value)) {");
            sb.append("\n         alert('" + paramRequest.getLocaleString("jsMsg1") +  "');");
            sb.append("\n         elem.value='';");
            sb.append("\n         bOk = false;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateFormProps(){");
            sb.append("\n      var vMsg = '';");
            sb.append("\n      var bOk = false;");
            sb.append("\n      var vTitle = document.getElementById('" + PROCESS_SELECTION_CTRL + "').value;");
            sb.append("\n      //alert('vTitle:' + vTitle);");
            sb.append("\n      if(vTitle.length>0){");
            sb.append("\n         vMsg =  vMsg + '" + paramRequest.getLocaleString("jsMsg12") + "';");
            sb.append("\n      }");
            sb.append("\n      var selected_proccesses = validateSelectedProcess();");
            sb.append("\n      //alert('selected_proccesses:' + selected_proccesses);");
            sb.append("\n      if(!selected_proccesses){");
            sb.append("\n         vMsg =  vMsg + '" + paramRequest.getLocaleString("jsMsg4") + "';");
            sb.append("\n      }");
            sb.append("\n      if(!validateTasksPerPage){");
            sb.append("\n         vMsg =  vMsg + '" + paramRequest.getLocaleString("jsMsg5") + "';");
            sb.append("\n      }");
            sb.append("\n   }");
            sb.append("\n</script>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getValidatingScript", e);
            System.out.println("Error en PreConfiguredControlPanel.getValidatingScript:" + e.getMessage());
		}
        return sb;
    }


    /**
    * Imprime el codigo Javascript para las validaciones del formato de admon de configuracion actual
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getValidatingScriptCurrentConf(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function isNumeric(value) {");
            sb.append("\n      var bOk = false;");
            sb.append("\n      if (value != null) {");
            sb.append("\n         var regExp = /^\\d+$/;");
            sb.append("\n         bOk  = regExp.test(value.toString());");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateNumeric(elem) {");
            sb.append("\n      var bOk = true;");
            sb.append("\n      if (!isNumeric(elem.value)) {");
            sb.append("\n         alert('" + paramRequest.getLocaleString("jsMsg1") +  "');");
            sb.append("\n         elem.value='';");
            sb.append("\n         bOk = false;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateSelectedProcess() {");
            sb.append("\n      var selectedProcess=(frmAdmin." + PROCESS_SELECTION_CTRL + ".value);");
            sb.append("\n      if (selectedProcess=='-1') {");
            sb.append("\n          return false;");
            sb.append("\n      }");
            sb.append("\n      return true;");
            sb.append("\n   }");
            sb.append("\n   function validateTasksPerPage() {");
            sb.append("\n      var selectedTaskPerPage=document.getElementById('" + TASK_PER_PAGE_CONTROL + "');");
            sb.append("\n      //alert('selectedTaskPerPage:' + selectedTaskPerPage.value);");
            sb.append("\n      if(selectedTaskPerPage.value=='' || isNaN(selectedTaskPerPage.value)){");
            sb.append("\n          return false;");
            sb.append("\n      } else {");
            sb.append("\n          var intTaskPerPage=parseInt(selectedTaskPerPage.value);");
            sb.append("\n          if (intTaskPerPage<1) {");
            sb.append("\n              return false;");
            sb.append("\n          } else {");
            sb.append("\n              return true;");
            sb.append("\n          }");
            sb.append("\n      }");          
            sb.append("\n   }");
            sb.append("\n   function validateForm(){");            
            sb.append("\n      var vMsg = '';");
            sb.append("\n      var bOk = false;");
            sb.append("\n      var vTitle = document.getElementById('" + TITLE_SELECTION_CTRL + "').value;");
            sb.append("\n      //alert('vTitle:' + vTitle);");
            sb.append("\n      if(vTitle.length<=0){");
            sb.append("\n         vMsg =  vMsg + '" + paramRequest.getLocaleString("jsMsg12") + "';");
            sb.append("\n      }");
            sb.append("\n      var selected_proccesses = validateSelectedProcess();");
            sb.append("\n      //alert('selected_proccesses:' + selected_proccesses);");
            sb.append("\n      if(!selected_proccesses){");
            sb.append("\n         vMsg =  vMsg + '" + paramRequest.getLocaleString("jsMsg4") + "';");
            sb.append("\n      }");
            sb.append("\n      if(!validateTasksPerPage()){");
            sb.append("\n         vMsg =  vMsg + '" + paramRequest.getLocaleString("jsMsg5") + "';");
            sb.append("\n      }");
            sb.append("\n      if(vMsg.length>1){");
            sb.append("\n         alert(vMsg);");
            sb.append("\n      } else {");
            sb.append("\n         bOk = true;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n</script>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getValidatingScript", e);
            System.out.println("Error en PreConfiguredControlPanel.getValidatingScript:" + e.getMessage());
		}
        return sb;
    }


    /**
    * Genera el codigo HTML necesario para desplegar el formato inicial de administracion del recurso.
    * El formato permite modificar el titulo de la instancia del recurso, el proceso seleccionado y
    * las opciones de configuración (vinculo, leyenda y columnas).
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getCurrentConfiguration(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strChecked = "";
        try{
            org.semanticwb.process.model.Process selectedProcess = null;
            Resource base = paramRequest.getResourceBase();
            SWBResourceURL url = paramRequest.getRenderUrl();
            String strCPProcessDefinitions = base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            String strCPTitle = base.getAttribute(TITLE_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(TITLE_SELECTION_CTRL);
            String strCPRowsPerPage = base.getAttribute(TASK_PER_PAGE_CONTROL)==null
                    ?String.valueOf(intDefaultRowsPerPage)
                    :base.getAttribute(TASK_PER_PAGE_CONTROL);
            //validar titulo, numero de tareas por pagina y 1 proceso seleccionado
            sb.append(getValidatingScriptCurrentConf(paramRequest));
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direcciona(accion){");
            sb.append("\n      if (accion=='ApplyCurrentConfiguration'){");
            sb.append("\n         var answer = confirm('" + paramRequest.getLocaleString("msgConfirmCurrentConfiguration") +  "');");
            sb.append("\n         if (answer){");
            sb.append("\n            if (validateForm()){");
            sb.append("\n               document.forms['frmAdmin'].action = '" + url.setAction("ApplyCurrentConfiguration") + "';");
            sb.append("\n               document.forms['frmAdmin'].submit();");
            sb.append("\n            }");
            sb.append("\n         } else {");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("goView") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n         }");
            sb.append("\n      } else if(accion=='SelectDefaultConfiguration'){");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("SelectDefaultConfiguration") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n      } else if(accion=='SelectTaskColumnConfiguration'){");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("SelectTaskColumnConfiguration") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n      } else if(accion=='SelectTaskLegendConfiguration'){");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("SelectTaskLegendConfiguration") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n      } else if(accion=='SelectTaskLinkConfiguration'){");
            sb.append("\n            document.forms['frmAdmin'].action = '" + url.setAction("SelectTaskLinkConfiguration") + "';");
            sb.append("\n            document.forms['frmAdmin'].submit();");
            sb.append("\n      } else {");
            sb.append("\n         document.forms['frmAdmin'].action = action;");
            sb.append("\n         document.forms['frmAdmin'].submit();");
            sb.append("\n      }");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("\n<div class=\"" + DIV_CLASS_SWBFORM + "\">");
            sb.append("\n<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("goView") +
                    "\" method=\"POST\"><br/><br/>");
            //Proceso
            //TODO:Al seleccionar proceso, si titulo esta vacio, agregar nombre del proceso            
            Vector vProcessDefinitions = BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(paramRequest);
            sb.append("<br />");
            sb.append("<p>" + paramRequest.getLocaleString("lblControlPanelProperties") + "</p>");
            sb.append("\n<label for=\"" + PROCESS_SELECTION_CTRL +"\" >" + paramRequest.getLocaleString("lblProcessDefinitions") + ":</label>");
            sb.append("\n<select name=\"" + PROCESS_SELECTION_CTRL + "\" id=\"" + PROCESS_SELECTION_CTRL +"\" />");
            sb.append("<option value=\"-1\">" + paramRequest.getLocaleString("cpSelectNone") + "</option>");
            for(int i=0; i<vProcessDefinitions.size(); i++)
            {
                org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) vProcessDefinitions.get(i);
                if(strCPProcessDefinitions.equalsIgnoreCase(process.getURI()))
                {
                    strChecked = "selected";
                    selectedProcess = process;
                } else {
                    strChecked = "";
                }
                sb.append("\n<option value=\"" + process.getURI() + "\" " + strChecked + " >" + process.getTitle() +"</option>");
            }
            sb.append("\n</select><br/><br/>");
            //Titulo de la bandeja
            sb.append("\n<label for=\"" + TITLE_SELECTION_CTRL + "\" >*" + paramRequest.getLocaleString("lblTitle") + "</label>");
            //sb.append("\n<div>* " + paramRequest.getLocaleString("lblTitle"));            
            sb.append(" \n<input type=\"TEXT\" id=\"" + TITLE_SELECTION_CTRL + "\" name=\"" + TITLE_SELECTION_CTRL + "\"" +
                    " size=\"50\" maxlength=\"50\" " +
                    " label=\"" + paramRequest.getLocaleString("lblTitle")
                    + "\" value=\"" + strCPTitle + "\"/><br/><br/>");
            //</div>
            //Numero de tareas por pagina
            sb.append("\n<label for=\"" +  TASK_PER_PAGE_CONTROL + "\" >*" + paramRequest.getLocaleString("lblTasksPerPageNumber") + "</label>");
            //sb.append("\n<div>*" + paramRequest.getLocaleString("lblTasksPerPageNumber"));
            sb.append(" \n<input type=\"TEXT\" id=\"" +  TASK_PER_PAGE_CONTROL + "\"" +
                    " name=\"" +  TASK_PER_PAGE_CONTROL + "\" size=\"5\" maxlength=\"4\" " +
                    " label=\"" +  paramRequest.getLocaleString("lblTasksPerPageNumber") +
                    "\" onKeyUp=\"validateNumeric(this)\" value=\"" +
                    strCPRowsPerPage + "\"/><br/><br/>");
            //</div>
            if(selectedProcess!=null){
                //propiedades seleccionadas del vinculo y acceso para modificar
                sb.append(printSelectedPropertiesByType(DISPLAY_ON_LINK, selectedProcess, paramRequest));
                sb.append("<input type=\"SUBMIT\" " + "name=\"btnEditTaskLinkProps\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnEditTaskLinkProps") + "\"" +
                    " onclick=\"direcciona('SelectTaskLinkConfiguration');\" /><br/><br/>");
                //propiedades seleccionadas de la leyenda y acceso para modificar
                sb.append(printSelectedPropertiesByType(DISPLAY_ON_LEGEND, selectedProcess, paramRequest));
                sb.append("<input type=\"SUBMIT\" " + "name=\"btnEditTaskLegendProps\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnEditTaskLegendProps") + "\"" +
                    " onclick=\"direcciona('SelectTaskLegendConfiguration');\" /><br/><br/>");
                //propiedades seleccionadas en columna y acceso para modificar
                sb.append(printSelectedPropertiesByType(DISPLAY_ON_COLUMN, selectedProcess, paramRequest));
                sb.append("<input type=\"SUBMIT\" " + "name=\"btnEditTaskColumnProps\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnEditTaskColumnProps") + "\"" +
                        " onclick=\"direcciona('SelectTaskColumnConfiguration');\" /><br/><br/>");
            }            
             //regresar a configuracion por defecto.
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnDefaultConfiguration\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnDefaultConfiguration") + "\"" +
                    " onclick=\"direcciona('SelectDefaultConfiguration');\" />");

            sb.append("<input type=\"SUBMIT\" " + "name=\"btnSave\" class=\"" + INPUT_BUTTON_CLASS + "\"  value=\"" + paramRequest.getLocaleString("btnSave") +
                    "\" onclick=\"direcciona('ApplyCurrentConfiguration');\" />");
            sb.append("<input type=\"RESET\" " + " name=\"btnReset\" class=\"" + INPUT_BUTTON_CLASS + "\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form>");
            sb.append("</div>");
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getCurrentConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.getCurrentConfiguration:" + e.getMessage());
		}
        return sb;
    }

    /**
	 * Actualiza la información que configuró el usuario final durante la
     * personalización. Si la operacion es exitosa, devuelve un mensaje.
     * En caso contrario, devuelve un mensaje de error.
	 *
     * @param           request HttpServletRequest
     * @param           paramsRequest SWBParamRequest
	 * @return      	StringBuffer con mensaje de exito o error
	 */
    public StringBuffer setCustomizedData(HttpServletRequest request,
            SWBParamRequest paramsRequest) throws IOException {
        StringBuffer sbPrint = new StringBuffer();
        String msg = null;
        String errorMsg = null;
        String strData = "";
        try
        {
            Resource base = paramsRequest.getResourceBase();
            User currentUser = paramsRequest.getUser();
            Enumeration enumNames = request.getParameterNames();
            while(enumNames.hasMoreElements())
            {
                String strName = enumNames.nextElement().toString();
                if(strName.startsWith(SORTING_CTRL) || strName.startsWith(CLOSED_TASKS_FILTER_CTRL))
                {
                    String strParameter = request.getParameter(strName);
                    strData = strData + strName + "|" + strParameter + "|";
                }
            }
            if(strData!=null && !strData.equalsIgnoreCase(""))
            {
                base.setData(currentUser, strData);
                msg = (new StringBuilder()).append(paramsRequest.getLocaleString("msgOkUpdateResource")).append(" "
                        + base.getId()).toString();
                sbPrint.append((new StringBuilder()).append(
                        "\n<script language=\"JavaScript\">  \nalert('").append(msg).append("');").append("\n</script>").toString());
                sbPrint.append((new StringBuilder()).append("\n<script " +
                        "language=\"JavaScript\">  \nlocation='").append(paramsRequest.getRenderUrl().setAction(
                        "admin").toString()).append("';").append("\n</script>").toString());
                errorMsg = (new StringBuilder()).append(paramsRequest.getLocaleString("msgErrUpdateResource")).append(" "
                        + base.getId()).toString();
            }
        } catch(Exception e){
            sbPrint.append((new StringBuilder()).append(
                    "\n<script language=\"JavaScript\">  \nalert('").append(
                    errorMsg).append("');").append("\n</script>").toString());
            sbPrint.append((new StringBuilder()).append("\n<script " +
                    "language=\"JavaScript\">  \nlocation='").append(paramsRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';").append(
                    "\n</script>").toString());
            log.error("Error en PreConfiguredControlPanel.setCustomizedData", e);
            System.out.println("Error en PreConfiguredControlPanel.setCustomizedData:" + e.getMessage());
		}
        return sbPrint;
    }

    /**
    * Guarda la configuración actual del recurso, incluyendo proceso, titulo y tareas por pagina.
    * El formato permite modificar el titulo de la instancia del recurso, el proceso seleccionado.
    *
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public String setCurrentConfiguration(HttpServletRequest request,
            SWBParamRequest paramRequest)
    {
        StringBuffer ret = new StringBuffer();
        String msg = null;
        String errorMsg = null;

        try{
            Resource base = paramRequest.getResourceBase();
            String strBaseProcessDefinitions = base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            String strCPProcessDefinitions = request.getParameter(PROCESS_SELECTION_CTRL);
            String strTitle = request.getParameter(TITLE_SELECTION_CTRL)==null
                    ?""
                    :request.getParameter(TITLE_SELECTION_CTRL);
            String strCPRowsPerPage = request.getParameter(TASK_PER_PAGE_CONTROL)==null
                    ?""
                    :request.getParameter(TASK_PER_PAGE_CONTROL);
            errorMsg = (new StringBuilder()).append(paramRequest.getLocaleString("msgErrCurrentConfigUpdateResource")).append(" "
                    + base.getId()).toString();

            boolean differentSelectedProcess = isDifferentSelectedProcess(request, paramRequest);
            if(strCPProcessDefinitions!=null && !strCPProcessDefinitions.equalsIgnoreCase(""))
            {
                System.out.println("---strBaseProcessDefinitions:" + strBaseProcessDefinitions);
                base.setAttribute(PROCESS_SELECTION_CTRL,strCPProcessDefinitions);
            }
            if(strTitle!=null && !strTitle.equalsIgnoreCase(""))
            {
                System.out.println("---strTitle:" + strTitle);
                base.setAttribute(TITLE_SELECTION_CTRL, strTitle);
            }
            if(strCPRowsPerPage!=null && !strCPRowsPerPage.equalsIgnoreCase(""))
            {
                System.out.println("---strCPRowsPerPage:" + strCPRowsPerPage);
                base.setAttribute(TASK_PER_PAGE_CONTROL, strCPRowsPerPage);
            }
            if(differentSelectedProcess){
                System.out.println("---strBaseProcessDefinitions2:" + strBaseProcessDefinitions);
                //Se seleccionó un proceso diferente al que habia anteriormente
                //Si la propiedad de la base esta vacía, es la primera vez que se configura y se utiliza la configuracion por defecto
                //La propiedad de la base no esta vacía, estamos modificando el proceso, para
                //evitar problemas con los artefactos, se usará la configuración por defecto
                System.out.println("---strDefaultHref:" + strDefaultHref);
                base.setAttribute("taskLinkProperties", strDefaultHref);
                System.out.println("---strDefaultLegend:" + strDefaultLegend);
                base.setAttribute("taskLegendProperties", strDefaultLegend);
                System.out.println("---strDefaultColumns:" + strDefaultColumns);
                base.setAttribute("taskColumnProperties", strDefaultColumns);
            }
            base.updateAttributesToDB();

            msg = (new StringBuilder()).append(
                    paramRequest.getLocaleString("msgOkCurrentConfigUpdateResource")).append(" "
                    + base.getId()).toString();
            ret.append((new StringBuilder()).append(
                "<script language=\"JavaScript\">\n alert('").append(msg).append("');\n").append("</script>\n").toString());
            ret.append((new StringBuilder()).append("<script " +
                    "language=\"JavaScript\">\n   location='").append(paramRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';\n").append("</script>\n").toString());
        } catch(Exception e){
            ret.append((new StringBuilder()).append(
                    "\n<script language=\"JavaScript\">  \nalert('").append(errorMsg).append("');").append("\n</script>").toString());
            ret.append((new StringBuilder()).append("\n<script " +
                    "language=\"JavaScript\">  \nlocation='").append(
                    paramRequest.getRenderUrl().setAction("admin").toString()).append("';").append("\n</script>").toString());
            log.error("Error en PreConfiguredControlPanel.setCurrentConfiguration", e);
            System.out.println("Error en PreConfiguredControlPanel.setCurrentConfiguration:" + e.getMessage());
		}
        return ret.toString();
    }

    /**
	 * Despliega seccion de administracion del recurso
	 *
     * @param           request HttpServletRequest
     * @param           response HttpServletResponse
     * @param           paramsRequest SWBParamRequest
	 */
    @Override
    public void doAdmin(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException
    {
        java.io.PrintWriter out = response.getWriter();
        try
        {
            lstTaskDefinitionProperties = TaskDefinitionProperties(paramRequest);
            lstTaskInstanceProperties = TaskInstanceProperties(paramRequest);

            if(paramRequest.getAction().equalsIgnoreCase("ApplyCurrentConfiguration")){
                //aplicar configuracion y avisar
                out.print(setCurrentConfiguration(request,paramRequest));
            } else if(paramRequest.getAction().equalsIgnoreCase("ApplyTaskColumnConfiguration")){
                //aplicar configuracion y avisar
                out.print(setTaskColumnConfiguration(request,paramRequest));
            } else if(paramRequest.getAction().equalsIgnoreCase("SelectTaskColumnConfiguration")){
                //mostrar las propiedades de tarea que se pueden seleccionar como columna
                //solicita confirmacion del usuario
                out.print(getTaskColumnConfiguration(paramRequest).toString());
            } else if(paramRequest.getAction().equalsIgnoreCase("ApplyTaskLegendConfiguration")){
                //aplicar configuracion y avisar
                out.print(setTaskLegendConfiguration(request,paramRequest));
            } else if(paramRequest.getAction().equalsIgnoreCase("SelectTaskLegendConfiguration")){
                //mostrar las propiedades de tarea que se pueden seleccionar para la leyenda
                //solicita confirmacion del usuario
                out.print(getTaskLegendConfiguration(paramRequest).toString());
            } else if(paramRequest.getAction().equalsIgnoreCase("ApplyTaskLinkConfiguration")){
                //aplicar configuracion y avisar
                out.print(setTaskLinkConfiguration(request,paramRequest));
            } else if(paramRequest.getAction().equalsIgnoreCase("SelectTaskLinkConfiguration")){
                //mostrar las propiedades de tarea que se pueden seleccionar para el vinculo
                //solicita confirmacion del usuario
                out.print(getTaskLinkConfiguration(paramRequest).toString());
            } else if(paramRequest.getAction().equalsIgnoreCase("ApplyDefaultConfiguration")){
                //aplicar configuracion por defecto
                out.print(setDefaultConfiguration(request,paramRequest));
            } else if(paramRequest.getAction().equalsIgnoreCase("SelectDefaultConfiguration")){
                //mostrar las propiedades que se seleccionaran si se utiliza la configuracion por defecto
                //solicita confirmacion del usuario
                out.print(getDefaultConfiguration(paramRequest).toString());
            } else {
                //Desplegar formato inicial de administracion
                out.print(getCurrentConfiguration(paramRequest).toString());
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.doAdmin", e);
            System.out.println("Error en PreConfiguredControlPanel.doAdmin:" + e.getMessage());
		}
    }

    /**
	 * Despliegue del recurso
	 *
     * @param           request HttpServletRequest
     * @param           response HttpServletResponse
     * @param           paramsRequest SWBParamRequest
	 */
    @Override
    public void doView(HttpServletRequest request,
            HttpServletResponse response,SWBParamRequest paramsRequest)
        throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        try
        {
            if(paramsRequest.getAction().equalsIgnoreCase("updateCustomization"))
            {
                out.print(setCustomizedData(request, paramsRequest));
            } else {
                Resource base = paramsRequest.getResourceBase();
                loadResourceConfiguration(paramsRequest);
                String strTitle = base.getAttribute(TITLE_SELECTION_CTRL);
                out.println("\n<script type=\"text/javascript\">");
                out.println("\n   function direcciona(action){");
                out.println("\n      document.forms['frmEditCustomization'].action = action;");
                out.println("\n      document.forms['frmEditCustomization'].submit();");
                out.println("\n   }");
                out.println("\n</script>");
                out.println("<div id=\"" + DIV_ID_PANEL + "\" >");
                out.println("<h2>" + strTitle + "</h2>");
                //Filtros usados
                out.println("<div id=\"" + DIV_ID_FILTERS + "\"><p>" + paramsRequest.getLocaleString("lblAppliedFilters") + "</p>");
                out.println(getUsedFilters(paramsRequest, request).toString() + "");
                out.println("</div>");
                if(paramsRequest.getAction().equalsIgnoreCase("buildReport")){
                    out.println(buildReport(request, paramsRequest).toString());
                } else {
                    out.println(printTaskLinks(request,paramsRequest).toString());
                }
                out.println("<div id=\"" + DIV_ID_TABS + "\">");
                out.println("<form id=\"frmEditCustomization\" name=\"frmEditCustomization\" action=\"" +
                        paramsRequest.getRenderUrl().setAction(
                        "editCustomization") + "\" method=\"POST\">");
                out.println("<ul>");
                out.println("<li id=\"" + LI_ID_FILTERED + "\" class=\"" + LI_CLASS_TAB_ON + "\"><a href=\"#\" onclick=\"MM_showHideLayers('" + DIV_ID_FILTERED + "','','show','" + DIV_ID_REPORT + "','','hide','" + DIV_ID_CUSTOMIZE + "','','hide');selectedTab('" + DIV_ID_FILTERED + "');\">" + paramsRequest.getLocaleString("btnFilter") + "</a></li>");
                out.println("<li id=\"" + LI_ID_REPORT + "\" class=\"" + LI_CLASS_TAB_OFF + "\"><a href=\"#\" onclick=\"MM_showHideLayers('" + DIV_ID_FILTERED + "','','hide','" + DIV_ID_REPORT + "','','show','" + DIV_ID_CUSTOMIZE + "','','hide');selectedTab('" + DIV_ID_REPORT + "');\">" + paramsRequest.getLocaleString("btnReport") + "</a></li>");
                out.println("<li id=\"" + LI_ID_CUSTOMIZE + "\" class=\"" + LI_CLASS_TAB_OFF + "\"><a href=\"#\" onclick=\"MM_showHideLayers('" + DIV_ID_FILTERED + "','','hide','" + DIV_ID_REPORT + "','','hide','" + DIV_ID_CUSTOMIZE + "','','show');selectedTab('" + DIV_ID_CUSTOMIZE + "');\">" + paramsRequest.getLocaleString("btnCustomize") + "</a></li>");
                out.println("</ul>");
                out.println("</form>");
                out.println("</div>");
                out.println("<script type=\"text/javascript\">");
                out.println("dojo.require(\"dojo.parser\");");
                out.println("dojo.require(\"dijit.Dialog\");");
                out.println("dojo.require(\"dijit.form.Form\");");
                out.println("dojo.require(\"dijit.form.Button\");");
                out.println("dojo.require(\"dijit.form.DateTextBox\");");
                out.println("dojo.require(\"dijit.form.ValidationTextBox\");");
                out.println("dojo.require(\"dijit.form.TimeTextBox\");");
                out.println("dojo.require(\"dijit.form.Textarea\");");
                out.println("dojo.require(\"dijit.form.ComboBox\");");
                out.println("dojo.require(\"dojox.validate.regexp\");");
                out.println("</script>");
                out.println("<div id=\"" + DIV_ID_PLECA_PARENT + "\">");
                //Filtros
                out.println(getFilterForm(paramsRequest));
                out.println(getReportForm(paramsRequest));
                out.println(customizeDisplay(request, response, paramsRequest));
                out.println("</div>");
                out.println("</div>");
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.doView", e);
            System.out.println("Error en PreConfiguredControlPanel.doView:" + e.getMessage());
		}
    }

    /**
    * Genera un StringBuffer que contiene el codigo HTML para presentar los
    * TaskLinks (encabezado, vinculo, columnas con propiedades seleccionadas
    * por el usuario durante la configuración del recurso)
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    * @see
    */
    public StringBuffer printTaskLinks(HttpServletRequest request,
            SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strTop = "";
        int intSortType = 0;
        try {
            Resource base = paramRequest.getResourceBase();
            int intRowsPerPage = base.getAttribute(TASK_PER_PAGE_CONTROL)==null
                    ?intDefaultRowsPerPage
                    :Integer.parseInt(base.getAttribute(TASK_PER_PAGE_CONTROL));
            int intCurrPage = request.getParameter("cpCurrPage")==null
                    ?1
                    :Integer.parseInt(request.getParameter("cpCurrPage"));
            int intColumnCount = base.getAttribute("intColumnCount")==null
                    ?1
                    :Integer.parseInt(base.getAttribute("intColumnCount"));
            Vector vTasks = getTasks(paramRequest);
            if(vTasks.size()> 0)
            {
                //Filtrar
                boolean bClosedTaskFilter = isClosedStatusFilterActive(paramRequest);
                if(bClosedTaskFilter)
                {
                    String strValue = Instance.STATUS_OPEN + "|" +
                            Instance.STATUS_PROCESSING + "|" +
                            Instance.STATUS_ABORTED + "|" +
                            Instance.STATUS_STOPED + "|";
                    vTasks = BPMSTask.ClassMgr.filterTasksByStatus(vTasks,strValue);
                }
                Vector vFilteredTasks = applyFilters(vTasks, request, paramRequest);
                Vector vTaskLinks = setTaskLinks(vFilteredTasks, paramRequest);                
                intSortType = getSortCriteria(paramRequest);
                BPMSTask.ClassMgr.sortTasks(vTaskLinks,intSortType);
                int endRow = getPageLastRow(vTaskLinks.size(),intRowsPerPage, intCurrPage);
                int iniRow = getPageFirstRow(vTaskLinks.size(),intRowsPerPage,intCurrPage);
                sb.append("<div id=\"" + DIV_ID_TASKS + "\">");
                sb.append("<p class=\"" + P_CLASS_LEFT + "\">" + vTaskLinks.size() + " " + paramRequest.getLocaleString("lblTotalTask") + "</p>");
                //sb.append("<p class=\"der cerradas-si\"><a href=\"#\" onclick=\"MM_showHideLayers('filtrado','','hide','informe','','hide','personalizar','','show');selectedTab('personalizar');\">Personalizar</a></p></div>");
                sb.append("<p class=\"" + P_CLASS_RIGHT_CLOSED +"\"></p></div>");
                StringBuffer sbPagination = getPagination(intRowsPerPage, intCurrPage,vTaskLinks.size(),paramRequest,request);
                sb.append("<p class=\"" + P_CLASS_PAGINATED + "\">" + sbPagination + "</p>");
                //sb.append("<p>" + paramRequest.getLocaleString("lblTotalTask") + "</p>");
                //sb.append("<table ><thead><tr>");
                sb.append("<table >");

                java.util.List<String> lstColumnNames = getColumnNames(paramRequest);
                java.util.List<String> lstColumnIDs = getColumnIDs(paramRequest);
                Iterator itColumnNames = lstColumnNames.iterator();

                  for(int i=iniRow; i<endRow; i++){
                    TaskLink tlink = (TaskLink) vTaskLinks.get(i);
                    Hashtable hash = tlink.getTaskLinkArtifactValues();
                    if(!tlink.getFlowNodeParentProcessURI().equalsIgnoreCase(strTop))
                    {
                        String tmpUri = tlink.getFlowNodeParentProcessURI();
                        String parentName = tlink.getFlowNodeParentProcess();                        
                        sb.append("<th class=\"" + TH_CLASS_UNORDERED + "\" ><a href=\"#\">" + paramRequest.getLocaleString("lblTaskTitle") + "</a></th>");
                        itColumnNames = lstColumnNames.iterator();
                        while(itColumnNames.hasNext())
                        {
                            String tmpName = itColumnNames.next().toString();
                            sb.append("<th class=\"" + TH_CLASS_UNORDERED + "\"><a href=\"#\">" + tmpName +  "</a></th>");
                        }
                        sb.append("</tr></thead><tbody>");
                        strTop = tmpUri;                        
                    }
                    
                    sb.append("<tr><td>");
                    int iPriority = tlink.getPriority();
                    sb.append("<a href=\"");
                    sb.append(tlink.getTaskLinkHref());
                    sb.append("\" class=\"priority" + iPriority + "\" >");
                    sb.append(tlink.getTaskLinkLegend());
                    sb.append("</a></td>");
                    
                    for(int j=0; j<lstColumnNames.size(); j++)
                    {
                        String tmpName = lstColumnIDs.get(j);
                        String value = hash.get(tmpName)==null ?"" :hash.get(tmpName).toString();
                        sb.append("<td>" + value + "</td>");
                    }                    
                    sb.append("</tr>");
                }
                sb.append("</tbody></table>");
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.printTaskLinks", e);
            System.out.println("Error en PreConfiguredControlPanel.printTaskLinks:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Genera un StringBuffer que contiene los nombres de los filtros
    * seleccionados mediante getFilterForm.
    *
    * @param            paramRequest SWBParamRequest
    * @param            request HttpServletRequest
    * @return      		StringBuffer con codigo HTML
    * @see
    */
    public StringBuffer getUsedFilters(SWBParamRequest paramsRequest, HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            String strInitialFilterDate = request.getParameter(INITIAL_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(INITIAL_DATE_FILTER_CTRL);
            String strEndFilterDate = request.getParameter(END_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(END_DATE_FILTER_CTRL);
            String strFilterProcess = request.getParameter(PROCESS_FILTER_CTRL)==null
                ?"0"
                :request.getParameter(PROCESS_FILTER_CTRL);
            String strFilterStatus = request.getParameter(STATUS_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(STATUS_FILTER_CTRL);
            String strFilterTitle = request.getParameter(TITLE_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(TITLE_FILTER_CTRL);
            String strFilterPriority = request.getParameter(PRIORITY_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(PRIORITY_FILTER_CTRL);
            sb.append("<ul>");
            //TODO: Poner el estilo en constantes
            if(!strFilterProcess.equalsIgnoreCase("0")){
                sb.append("<li class=\"" + LI_CLASS_FILTER_PROCESS + "\">" + paramsRequest.getLocaleString("lblFilterProcess") + " " +
                        SemanticObject.getSemanticObject(strFilterProcess).getDisplayName() + "</li>");
            }
            if(!strFilterStatus.equalsIgnoreCase("-1")){
                sb.append("<li class=\"" + LI_CLASS_FILTER_STATUS + "\">" + paramsRequest.getLocaleString("lblFilterStatus") +
                        " " +  getStatusDescription(paramsRequest, Integer.parseInt(strFilterStatus)) + "</li>");
            }
            if(!strFilterPriority.equalsIgnoreCase("-1")){
                int iPriority = Integer.parseInt(strFilterPriority);
                sb.append("<li class=\"" + LI_CLASS_FILTER_STATUS + "\">" + paramsRequest.getLocaleString("lblFilterPriority") +
                        " " + getPriorityDescription(paramsRequest, iPriority) + "</li>");
            }
            if(!strFilterTitle.equalsIgnoreCase("-1")){
                sb.append("<li class=\"" + LI_CLASS_FILTER_STATUS + "\">" + paramsRequest.getLocaleString("lblFilterTaskName") +
                        " " + strFilterTitle+ "</li>");
            }
            if(!strInitialFilterDate.equalsIgnoreCase("")){
                sb.append("<li class=\"" + LI_CLASS_FILTER_INITIAL_DATE + "\">" + paramsRequest.getLocaleString("lblFilterIniDate") +
                        " " + strInitialFilterDate + "</li>");
                if(!strEndFilterDate.equalsIgnoreCase(""))
                {
                    sb.append("<li class=\"" + LI_CLASS_FILTER_FINAL_DATE + "\">" + paramsRequest.getLocaleString("lblFilterEndDate")
                            + " " + strEndFilterDate + "</li>");
                }
            }
            sb.append("</ul>" );
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getUsedFilters", e);
            System.out.println("Error en PreConfiguredControlPanel.getUsedFilters:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Obtiene la descripción de un identificador de prioridad.
    *
    * @param            paramsRequest SWBParamRequest
    * @param            priority int identificador de prioridad
    * @return      		String
    */
    public String getPriorityDescription(SWBParamRequest paramsRequest,
            int priority)
    {
        String strDescription = "";
        try
        {
            switch(priority)
            {
                case 0:
                    strDescription = paramsRequest.getLocaleString("cpTaskPriority0");
                    break;
                case 1:
                    strDescription = paramsRequest.getLocaleString("cpTaskPriority1");
                    break;
                case 2:
                    strDescription = paramsRequest.getLocaleString("cpTaskPriority2");
                    break;
                case 3:
                    strDescription = paramsRequest.getLocaleString("cpTaskPriority3");
                    break;
                case 4:
                    strDescription = paramsRequest.getLocaleString("cpTaskPriority4");
                    break;
                case 5:
                    strDescription = paramsRequest.getLocaleString("cpTaskPriority5");
                    break;
            }
        } catch (Exception e) {
            log.error("Error en PreConfiguredControlPanel.getPriorityDescription", e);
            System.out.println("error en PreConfiguredControlPanel.getPriorityDescription: " + e.getMessage());
        }
        return strDescription;
    }

    /**
    * Obtiene el criterio de ordenamiento seleccionado (durante
    * customizeDisplay) de la base del recurso
    *
    * @param            paramRequest SWBParamRequest
    * @return      		int
    */
    public int getSortCriteria(SWBParamRequest paramRequest)
    {
        int intCriteria = 0;
        String strSortingCtrl = "";
        try
        {
            Resource base = paramRequest.getResourceBase();
            User currentUser = paramRequest.getUser();
            String strCustomizedData = base.getData(currentUser);
            Hashtable htable = getCustomizedData(strCustomizedData);
            if(htable.containsKey(SORTING_CTRL)){
                strSortingCtrl = htable.get(SORTING_CTRL).toString();
            }
            if(null!=strSortingCtrl && !strSortingCtrl.equalsIgnoreCase("")){
                intCriteria = Integer.parseInt(strSortingCtrl);
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getSortCriteria", e);
            System.out.println("Error en PreConfiguredControlPanel.getSortCriteria:" + e.getMessage());
		}
        return intCriteria;
    }

    /**
    * Obtiene una lista con los identificadores de las columnas configuradas
    *
    * @param            paramRequest SWBParamRequest
    * @return      		java.util.List
    */
    public java.util.List getColumnIDs(SWBParamRequest paramRequest)
    {
        java.util.List<String> columnIDs = new ArrayList();
        int index = 0;
        try{
            //List lstTaskDefinitionProps = TaskDefinitionProperties(paramRequest);
            //List lstTaskInstanceProps = TaskInstanceProperties(paramRequest);
            String strColumnsArr[] = strColumnsConfiguration.split("\\;");
            java.util.List<String> listColNames = new ArrayList<String>(strColumnsArr.length);
            for (int i=0; i<strColumnsArr.length; i++) {
                String strTemp = strColumnsArr[i];
                if(strTemp.contains("|")){
                    String strID = "";
                    String colNamesArr[] = strTemp.split("\\|");
                    String tmpType = colNamesArr[0];
                    String tmpName = colNamesArr[1];
                    if(tmpType.contains(PROPERTY_TYPE_TASK_DEFINITION) || tmpType.contains(PROPERTY_TYPE_TASK_INSTANCE)){
                        strID = tmpType + "." + tmpName;
                    } else if(tmpType.contains(PROPERTY_TYPE_ARTIFACT)) {
                        strID = tmpName;                        
                    }
                    listColNames.add(index, strID);
                    index++;
                }
            }
            columnIDs = listColNames;
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getColumnIDs", e);
            System.out.println("Error en PreConfiguredControlPanel.getColumnIDs:" + e.getMessage());
		}
        return columnIDs;
    }


    /**
    * Obtiene una lista con los nombres de despliegue de las columnas configuradas
    *
    * @param            paramRequest SWBParamRequest
    * @return      		java.util.List
    */ 
    public java.util.List getColumnNames(SWBParamRequest paramRequest)
    {
        java.util.List<String> columnNames = new ArrayList();
        try{
            java.util.List lstTaskDefinitionProps = TaskDefinitionProperties(paramRequest);
            java.util.List lstTaskInstanceProps = TaskInstanceProperties(paramRequest);
            String strColumnsArr[] = strColumnsConfiguration.split("\\;");
            java.util.List<String> listColNames = new ArrayList<String>(strColumnsArr.length);
            for (int i=0; i<strColumnsArr.length; i++) {
                String strTemp = strColumnsArr[i];
                if(strTemp.contains("|")){
                    String strName = "";
                    String colNamesArr[] = strTemp.split("\\|");
                    String tmpType = colNamesArr[0];
                    String tmpName = colNamesArr[1];
                    String tmpOrder = colNamesArr[2];
                    if(tmpType.contains(PROPERTY_TYPE_TASK_DEFINITION)){
                        strName = getTaskPropertyName(lstTaskDefinitionProps, tmpName);
                    } else if(tmpType.contains(PROPERTY_TYPE_TASK_INSTANCE)){
                        strName = getTaskPropertyName(lstTaskInstanceProps, tmpName);
                    } else if(tmpType.contains(PROPERTY_TYPE_ARTIFACT)) {
                        strName = BPMSProcessInstance.ClassMgr.getPropertyName(tmpName);
                    }
                    listColNames.add(strName);
                }                
            }
            columnNames = listColNames;
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getColumnNames", e);
            System.out.println("Error en PreConfiguredControlPanel.getColumnNames:" + e.getMessage());
		}
        return columnNames;
    }

    /**
    * Obtiene el nombre de despliegue de una propiedad de la tarea, buscando
    * el identificador en una lista de propiedades de la tarea.
    *
    * @param            listType java.util.List
    * @param            id String
    * @return      		String
    */
    public String getTaskPropertyName(java.util.List listType, String id)
    {
        String strName = "";
        try{
            Iterator itProps = listType.iterator();
            while(itProps.hasNext())
            {
                String[] strTempRow = (String[])itProps.next();
                if(strTempRow[2].equalsIgnoreCase(id))
                {
                     strName = strTempRow[1];
                     break;
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTaskPropertyName", e);
            System.out.println("Error en PreConfiguredControlPanel.getTaskPropertyName:" + e.getMessage());
		}
        return strName;
    }


    /**
    * Recibe un vector con objetos FlowNodeInstance, obtiene sus propiedades
    * configuradas y las usa para generar un Vector con objetos TaskLink
    *
    * @param            vTasks Vector de objetos FlowNodeInstance
    * @param            paramRequest SWBParamRequest
    * @return      		Vector de objetos TaskLink
    * @see
    */
    public Vector setTaskLinks(Vector vTasks, SWBParamRequest paramRequest)
    {
        Vector vTaskLinks  = new Vector();
        int intTasks = 0;
        try
        {
            Resource base = paramRequest.getResourceBase();
            String strTaskParams = base.getAttribute(TASK_LINK_FIXED_PARAMS)==null
                    ?""
                    :base.getAttribute(TASK_LINK_FIXED_PARAMS);
            for(int i=0; i<vTasks.size(); i++)
            {
                FlowNodeInstance fobi = (FlowNodeInstance)vTasks.get(i);
                ProcessInstance fpinst = fobi.getProcessInstance();
                if(null!=fpinst)
                {
                    org.semanticwb.process.model.Process fproc = (org.semanticwb.process.model.Process)fpinst.getProcessType();
                    if(null!=fproc)
                    {
                        //String strParentUri = fproc.getURI();
                        java.util.List lstBase = parseBaseTaskAttributes(fobi, paramRequest);
                        String strHref = "";
                        Iterator itLstBase = lstBase.iterator();
                        while (itLstBase.hasNext())
                        {                                                        
                            String tmpValue = itLstBase.next().toString();
                            strHref = strHref + tmpValue;
                        }
                        if(null!=strTaskParams && !strTaskParams.equalsIgnoreCase(""))
                        {
                            strHref = strHref + "&" + strTaskParams;
                        }
                        strHref = strHref + parseTaskAttributes(fobi, 0, paramRequest);
                        String strLegend = parseTaskAttributes(fobi, 1, paramRequest);
                        Hashtable htColumns = parseTaskAttributes(fobi, paramRequest);
                        TaskLink tlink = new TaskLink(fobi, strHref, strLegend, htColumns);
                        vTaskLinks.add(intTasks,tlink);
                        intTasks++;
                    }
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.setTaskLinks", e);
            System.out.println("Error en PreConfiguredControlPanel.setTaskLinks:" + e.getMessage());
		}
        return vTaskLinks;
    }

    /**
    * Genera un objeto Hashtable, que contiene los nombres y valores de las propiedades configuradas
    *
    * @param            fobi FlowNodeInstance
    * @param            strAttributes String
    * @return      		Hashtable
    */
    public Hashtable parseTaskAttributes(FlowNodeInstance fobi, SWBParamRequest paramRequest)
    {
        Hashtable hash = new Hashtable();
        String strAttributes = strColumnsConfiguration;
        try
        {
            if(strAttributes.contains(";"))
            {
                String[] strAtts =  strAttributes.split("\\;");
                for(int i=0; i<strAtts.length; i++)
                {
                    String[] strTempAttribute = strAtts[i].split("\\|");
                    String strTemp = strTempAttribute[0];
                    String strPropName = "";
                    String strPropValue = "";
                    UserTask usrTask = (UserTask)fobi.getFlowNodeType();
                    if(strTemp.contains(PROPERTY_TYPE_ARTIFACT))
                    {
                        //SemanticProperty semprop = SemanticObject.createSemanticObject(strTemp).transformToSemanticProperty();
                        //strPropName = semprop.getDisplayName();
                        strPropName = strTempAttribute[1];
                        strPropValue = parseFlowNodeInstanceProperties(fobi, strPropName);
                    } else if(strTemp.contains(PROPERTY_TYPE_TASK_DEFINITION)) {
                        strPropName = strTempAttribute[0] + "." + strTempAttribute[1];
                        strPropValue = getTaskDefinitionProperty(usrTask, strTempAttribute[1]);
                    } else if(strTemp.contains(PROPERTY_TYPE_TASK_INSTANCE)){
                        strPropName = strTempAttribute[0] + "." + strTempAttribute[1];
                        strPropValue = getTaskInstanceProperty(fobi, strTempAttribute[1],paramRequest);
                    }
                    hash.put(strPropName, strPropValue);
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.parseTaskAttributes", e);
            System.out.println("Error en PreConfiguredControlPanel.parseTaskAttributes:" + e.getMessage());
		}
        return hash;
    }

    /**
    * Genera una lista, que contiene los nombres y valores de las propiedades minimas para formar el vinculo
    *
    * @param            fobi FlowNodeInstance
    * @param            paramRequest SWBParamRequest
    * @return      		java.util.List
    */
    public java.util.List parseBaseTaskAttributes(FlowNodeInstance fobi, SWBParamRequest paramRequest)
    {
        java.util.List lstAtt = new ArrayList();
        String strAttributes = strDefaultHref;
        int index = 0;
        try
        {
            if(strAttributes.contains(";"))
            {
                String[] strAtts =  strAttributes.split("\\;");
                for(int i=0; i<strAtts.length; i++)
                {
                    String[] strTempAttribute = strAtts[i].split("\\|");
                    String strTemp = strTempAttribute[0];
                    String strPropName = "";
                    String strPropValue = "";
                    UserTask usrTask = (UserTask)fobi.getFlowNodeType();
                    if(strTemp.contains("http://"))
                    {
                        //SemanticProperty semprop = SemanticObject.createSemanticObject(strTemp).transformToSemanticProperty();
                        //strPropName = semprop.getDisplayName();
                        strPropName = strTemp;
                        strPropValue = parseFlowNodeInstanceProperties(fobi, strPropName);
                    } else if(strTemp.contains(PROPERTY_TYPE_TASK_DEFINITION)) {
                        //strPropName = strTempAttribute[0] + "." + strTempAttribute[1];
                        strPropName = strTempAttribute[1];
                        strPropValue = getTaskDefinitionProperty(usrTask, strPropName);
                    } else if(strTemp.contains(PROPERTY_TYPE_TASK_INSTANCE)){
                        //strPropName = strTempAttribute[0] + "." + strTempAttribute[1];
                        strPropName = strTempAttribute[1];
                        if(strPropName.equalsIgnoreCase("encodedURI"))
                        {
                            strPropValue = "?suri=" + getTaskInstanceProperty(fobi, strPropName,paramRequest);
                        } else {
                            strPropValue = getTaskInstanceProperty(fobi, strPropName,paramRequest);
                        }
                    }                    
                    lstAtt.add(index, strPropValue);
                    index++;
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.parseTaskAttributes", e);
            System.out.println("Error en PreConfiguredControlPanel.parseTaskAttributes:" + e.getMessage());
		}
        return lstAtt;
    }


    /**
    * Dependiendo del parametro parseType, obtiene un String con la configuración
    * para un tipo de despliegue (vinculo, leyenda, columnas); usando el objeto
    * FlowNodeInstance proporcionado, se obtienen los nombres y valores de las
    * propiedades configuradas y se regresan en un String con el codigo HTML necesario.
    *
    * @param            fobi FlowNodeInstance
    * @param            parseType int
    * @param            paramRequest SWBParamRequest
    * @return      		String
    */
    public String parseTaskAttributes(FlowNodeInstance fobi,
            int parseType, SWBParamRequest paramRequest)
    {
        String strParsed = "";
        String strAttributes = "";
        try
        {
            switch(parseType)
            {
                case 0: //link
                    strAttributes = strHrefConfiguration;
                    break;
                case 1: //legend
                    strAttributes = strLegendConfiguration;
                    break;
                case 2: //columns
                    strAttributes = strColumnsConfiguration;
                    break;
                case 3://base
                    strAttributes = strDefaultHref;
                    break;
            }

            if(strAttributes!=null && !strAttributes.equalsIgnoreCase("") && strAttributes.contains(";"))
            {
                String[] strAtts =  strAttributes.split("\\;");
                int countFixedParameters = 0;
                for(int i=0; i<strAtts.length; i++)
                {
                    String[] strTempAttribute = strAtts[i].split("\\|");
                    String strTemp = strTempAttribute[0];
                    String strPropName = "";
                    String strPropValue = "";
                    UserTask usrTask = (UserTask)fobi.getFlowNodeType();
                    if(strTemp.contains(PROPERTY_TYPE_ARTIFACT))
                    {
                        strPropName = strTempAttribute[1];
                        //strPropValue = getArtifactProperty(fobi, strPropName);
                        strPropValue = parseFlowNodeInstanceProperties(fobi, strPropName);
                    } else if(strTemp.contains(PROPERTY_TYPE_TASK_DEFINITION)) {
                        strPropName = strTempAttribute[1];
                        strPropValue = getTaskDefinitionProperty(usrTask, strPropName);
                    } else if(strTemp.contains(PROPERTY_TYPE_TASK_INSTANCE)){
                        strPropName = strTempAttribute[1];
                        strPropValue = getTaskInstanceProperty(fobi, strPropName, paramRequest);
                    } else {
                        if(countFixedParameters==0)
                        {
                            strPropValue =  strTemp;
                        } else {
                            strPropValue =  "&" + strTemp;
                        }
                        countFixedParameters++;
                    }

                    switch(parseType)
                    {
                        case 0: //link
                            strParsed = strParsed + "&" + strPropName + "=" + strPropValue;
                            //strParsed = strParsed + strPropName + "=" + strPropValue;
                            break;
                        case 1: //legend
                            strParsed = strParsed + " " + strPropValue;
                                //strParsed = strParsed + strPropValue;
                            break;
                        case 2: //columns
                            strParsed = strParsed + "<td>" + strPropValue + "</td>";
                            break;
                        case 3: //base
                            strParsed = strParsed + strPropValue;
                            //strParsed = strParsed + strPropName + "=" + strPropValue;
                            break;
                    }
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.parseTaskAttributes", e);
            System.out.println("Error en PreConfiguredControlPanel.parseTaskAttributes:" + e.getMessage());
		}
        return strParsed;
    }

    /**
    * Obtiene el valor de una propiedad de la definición de una tarea.
    *
    * @param            usrTask UserTask
    * @param            strProperty String
    * @return      		String con valor de la propiedad
    */
    public String getTaskDefinitionProperty(UserTask usrTask,String strProperty)
    {
        String strValue = "";
        try
        {
            if(strProperty.equalsIgnoreCase("id"))
            {
                strValue = usrTask.getId()==null ?"" :usrTask.getId();
            } else if(strProperty.equalsIgnoreCase("title"))
            {
                strValue = usrTask.getTitle()==null ?"" :usrTask.getTitle();

            } else if(strProperty.equalsIgnoreCase("uri"))
            {
                strValue = usrTask.getURI()==null ?"" :usrTask.getURI();
            } else if(strProperty.equalsIgnoreCase("url"))
            {
                strValue = usrTask.getTaskWebPage().getUrl()==null ?"" :usrTask.getTaskWebPage().getUrl();
            } else if(strProperty.equalsIgnoreCase("description"))
            {
                strValue = usrTask.getDescription()==null ?"" :usrTask.getDescription();
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTaskDefinitionProperty", e);
            System.out.println("Error en PreConfiguredControlPanel.getTaskDefinitionProperty:" + e.getMessage());
		}
        return strValue;
    }

    /**
    * Obtiene el valor de una propiedad de la instancia de una tarea
    * (FlowNodeInstance)
    *
    * @param            flobInst FlowNodeInstance
    * @param            strProperty String
    * @return      		String con valor de la propiedad
    */
    public String getTaskInstanceProperty(FlowNodeInstance flobInst,
            String strProperty, SWBParamRequest paramRequest)
    {
        String strValue = "";
        try
        {
            if(strProperty.equalsIgnoreCase("id"))
            {
                strValue = flobInst.getId()==null ?"" :flobInst.getId();
            } else if(strProperty.equalsIgnoreCase("status"))
            {
                //strValue = String.valueOf(flobInst.getStatus());
                strValue = getStatusDescription(paramRequest, flobInst.getStatus());
            } else if(strProperty.equalsIgnoreCase("uri"))
            {
                strValue = flobInst.getURI()==null ?"" :flobInst.getURI();
            } else if(strProperty.equalsIgnoreCase("encodedURI"))
            {
                strValue = flobInst.getEncodedURI()==null ?"" :flobInst.getEncodedURI();
            } else if(strProperty.equalsIgnoreCase("created"))
            {
                strValue = String.valueOf(flobInst.getCreated());
            } else if(strProperty.equalsIgnoreCase("creator"))
            {
                if(flobInst.getCreator()!=null)
                {
                    User usr = flobInst.getCreator();
                    strValue = usr.getFullName();
                } else {
                    strValue = "";
                }
            } else if(strProperty.equalsIgnoreCase("modified"))
            {
                if(flobInst.getUpdated()!=null)
                {
                    strValue = flobInst.getUpdated().toString();
                } else {
                    strValue = "";
                }
                
            } else if(strProperty.equalsIgnoreCase("modifiedBy"))
            {
                if(flobInst.getModifiedBy()!=null)
                {
                    User usr = flobInst.getModifiedBy();
                    strValue = usr.getFullName();
                } else {
                    strValue = "";
                }
            } else if(strProperty.equalsIgnoreCase("ended"))
            {
                if(flobInst.getEnded()!=null)
                {
                    strValue = String.valueOf(flobInst.getEnded());
                } else {
                    strValue = "";
                }
            } else if(strProperty.equalsIgnoreCase("endedBy"))
            {
                if(flobInst.getStatus()==Instance.STATUS_CLOSED){
                    User usr = flobInst.getEndedby();
                    strValue = usr.getFullName();
                }
            } else
            {
                strValue = flobInst.getProperty(strProperty);
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTaskInstanceProperty:", e);
            System.out.println("Error en PreConfiguredControlPanel.getTaskInstanceProperty  at prop:" + strProperty + " " + e);
		}
        return strValue;
    }


    /**
    * Obtiene del archivo de propiedades la descripcion que corresponde a un
    * identificador de estatus.
    *
    * @param            paramsRequest SWBParamRequest
    * @param            status int identificador de estatus
    * @return      		String
    */
    public String getStatusDescription(SWBParamRequest paramsRequest,
            int status)
    {
        String strDescription = "";
        try
        {
            switch(status)
            {
                case Instance.STATUS_ABORTED:
                    strDescription = paramsRequest.getLocaleString("cpTaskStatus3");
                    break;
                case Instance.STATUS_CLOSED:
                    strDescription = paramsRequest.getLocaleString("cpTaskStatus4");
                    break;
                case Instance.STATUS_INIT:
                    strDescription = paramsRequest.getLocaleString("cpTaskStatus6");
                    break;
                case Instance.STATUS_OPEN:
                    strDescription = paramsRequest.getLocaleString("cpTaskStatus5");
                    break;
                case Instance.STATUS_PROCESSING:
                    strDescription = paramsRequest.getLocaleString("cpTaskStatus1");
                    break;
                case Instance.STATUS_STOPED:
                    strDescription = paramsRequest.getLocaleString("cpTaskStatus2");
                    break;
            }
        } catch (Exception e) {
            log.error("Error en PreConfiguredControlPanel.getStatusDescription", e);
            System.out.println("error en PreConfiguredControlPanel.getStatusDescription: " + e.getMessage());
        }
        return strDescription;
    }


    /**
    * Obtiene el valor de una propiedad para un objeto FlowNodeInstance
    *
    * @param            fobi FlowNodeInstance
    * @param            strPropertyUri String
    * @return      		String con valor de la propiedad
    * @see
    */
    public String parseFlowNodeInstanceProperties(FlowNodeInstance fobi,
            String strPropertyUri)
    {
        String strResult = "";
        try
        {
            Instance ppi = fobi.getParentInstance();
            if(strPropertyUri.contains("http://"))
            {
                boolean bFound = false;
                //Iterator<SWBClass> objit = ppi.getAllProcessObjects().iterator();
                Iterator<ItemAwareReference> objit = ppi.listHeraquicalItemAwareReference().iterator();
                while((objit.hasNext()) && (bFound==false))
                {
                    ItemAwareReference item=objit.next();;
                    SWBClass obj = item.getProcessObject();
                    //TODO: Revisar variables distintas de la misma clase
                    SemanticObject sob = SemanticObject.getSemanticObject(obj.getURI());
                    SemanticClass cls = sob.getSemanticClass();
                    Iterator<SemanticProperty> itProps = cls.listProperties();
                    while(itProps.hasNext())
                    {
                        SemanticProperty property = (SemanticProperty) itProps.next();
                        if(strPropertyUri.equalsIgnoreCase(property.getURI()))
                        {
                            strResult = BPMSProcessInstance.ClassMgr.getPropertyValue(sob, property);
                            bFound = true;
                            break;
                        }
                    }
                }
            } else {
                strResult = strPropertyUri;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.parseProperties", e);
            System.out.println("Error en PreConfiguredControlPanel.parseProperties:" + e.getMessage());
		}
        return strResult;
    }


    /**
    * Genera un vector con todas las tareas (FlowNodeInstance) del usuario
    * correspondientes a la instancia de proceso proporcionada.
    *
    * @param            paramRequest SWBParamRequest
    * @param            pinst SubProcessInstance
    * @return      		Vector de objetos FlowNodeInstance
    * @see
    */
    public Vector getTasks(SWBParamRequest paramRequest, SubProcessInstance pinst)
    {
        Vector vTaskLinks = new Vector();
        int intFobInstances = 0;
        try
        {
            User currentUser = paramRequest.getUser();
            Iterator<FlowNodeInstance> itFlowNodeInstances = pinst.listFlowNodeInstances();
            //Iterator<FlowNodeInstance> itFlowNodeInstances = SWBProcessMgr.getUserTaskInstances(pinst, currentUser).iterator();
            while(itFlowNodeInstances.hasNext())
            {
                FlowNodeInstance flobInst = itFlowNodeInstances.next();
                FlowNode flobInstType = (FlowNode)flobInst.getFlowNodeType();

                if(flobInst instanceof SubProcessInstance)
                {
                    SubProcessInstance processInstance = (SubProcessInstance)flobInst;
                    //Vector aux = getTasks(paramRequest, (SubProcessInstance)flobInst);
                    Vector aux = getTasks(paramRequest, processInstance);
                    vTaskLinks.addAll(aux);
                    intFobInstances = intFobInstances + aux.size();
                } else if(flobInstType instanceof UserTask)
                {
                    if(currentUser.haveAccess(flobInstType)){
                        vTaskLinks.add(intFobInstances,flobInst);
                        intFobInstances++;
                    }
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTasks", e);
            System.out.println("Error en PreConfiguredControlPanel.getTasks:" + e.getMessage());
		}
        return vTaskLinks;
    }

    /**
    * Genera un vector con todas las tareas (FlowNodeInstance) del usuario
    * correspondientes a todos los procesos del sitio.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		Vector de objetos FlowNodeInstance
    * @see
    */
    public Vector getTasks(SWBParamRequest paramRequest)
    {
        Vector vTaskLinks = new Vector();
        try
        {
            WebPage webPage = (WebPage)paramRequest.getWebPage();
            ProcessSite site = (ProcessSite)webPage.getWebSite();
            User currentUser = paramRequest.getUser();
            Vector vSelected = getSelectedProcessDefinitions(paramRequest);
            int intFobInstances = 0;
            for(int index=0; index<vSelected.size(); index++){
                //org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) vSelected.get(index);
                Iterator<ProcessInstance> itProcessInstances = site.listProcessInstances();
                while(itProcessInstances.hasNext())
                {
                    ProcessInstance pinst = (ProcessInstance)itProcessInstances.next();
                    Iterator<FlowNodeInstance> itFlowNodeInstances = pinst.listFlowNodeInstances();
                    while(itFlowNodeInstances.hasNext())
                    {
                        FlowNodeInstance flobInst = itFlowNodeInstances.next();
                        FlowNode flobInstType = (FlowNode)flobInst.getFlowNodeType();
                        if(flobInst instanceof SubProcessInstance)
                        {
                            SubProcessInstance processInstance = (SubProcessInstance)flobInst;
                            Vector aux = getTasks(paramRequest,processInstance);
                            vTaskLinks.addAll(aux);
                            intFobInstances = intFobInstances + aux.size();
                        } else if(flobInstType instanceof UserTask)
                        {
                            if(currentUser.haveAccess(flobInstType)){
                                vTaskLinks.add(intFobInstances,flobInst);
                                intFobInstances++;
                            }
                        }
                    }
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTasks", e);
            System.out.println("Error en PreConfiguredControlPanel.getTasks:" + e.getMessage());
		}
        return vTaskLinks;
    }

    /**
	 * Construye un vector con todas las definiciones de proceso seleccionadas
     * durante la configuración del recurso (Administración).
	 * Obtiene todos los objetos de tipo ProcessWebPage del sitio y solicita a
     * cada uno su definición de proceso (un org.semanticwb.process.model.Process);
     * verifica si el nombre del proceso fue seleccionado y lo agrega al vector.
	 *
     * @param               paramRequest SWBParamRequest
	 * @return      		Vector de objetos org.semanticwb.process.model.Process
	 */
     public java.util.List getSelectedProcessDefinitionsList(SWBParamRequest paramRequest)
     {
         java.util.List lstDefinitions = new ArrayList();
         int index = 0;
         try{
            Resource base = paramRequest.getResourceBase();
            WebPage webPage = (WebPage)paramRequest.getWebPage();
            ProcessSite site = (ProcessSite) webPage.getWebSite();
            
            String strControlPanelProcessDefinitions =
                base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            Vector vSelected =
                    BPMSProcessInstance.ClassMgr.stringToVector(strControlPanelProcessDefinitions);
            
            Iterator<org.semanticwb.process.model.Process> it2=org.semanticwb.process.model.Process.ClassMgr.listProcesses(site);
            while(it2.hasNext())
            {                    
                org.semanticwb.process.model.Process process = it2.next();
                if(process.isActive())
                {
                    if(vSelected.contains(process.getURI()))
                    {
                        lstDefinitions.add(index, process);
                        index++;
                    }
                }
            }
         } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getSelectedProcessDefinitionsList", e);
            System.out.println("Error en PreConfiguredControlPanel.getSelectedProcessDefinitionsList:" + e.getMessage());
		 }
         return lstDefinitions;
     }

    /**
	 * Construye un vector con todas las definiciones de proceso seleccionadas
     * durante la configuración del recurso (Administración).
	 * Obtiene todos los objetos de tipo ProcessWebPage del sitio y solicita a
     * cada uno su definición de proceso (un org.semanticwb.process.model.Process);
     * verifica si el nombre del proceso fue seleccionado y lo agrega al vector.
	 *
     * @param               paramRequest SWBParamRequest
	 * @return      		Vector de objetos org.semanticwb.process.model.Process
	 */
    public Vector getSelectedProcessDefinitions(SWBParamRequest paramRequest)
    {
        Vector vProcessDefinitions = new Vector();
        int index = 0;
        try
        {
            Resource base = paramRequest.getResourceBase();
            WebPage webPage = (WebPage)paramRequest.getWebPage();
            ProcessSite site = (ProcessSite) webPage.getWebSite();

            String strControlPanelProcessDefinitions =
                base.getAttribute(PROCESS_SELECTION_CTRL)==null
                    ?""
                    :base.getAttribute(PROCESS_SELECTION_CTRL);
            Vector vSelected =
                    BPMSProcessInstance.ClassMgr.stringToVector(strControlPanelProcessDefinitions);
            
            Iterator<org.semanticwb.process.model.Process> it2=org.semanticwb.process.model.Process.ClassMgr.listProcesses(site);
            while(it2.hasNext())
            {                    
                org.semanticwb.process.model.Process process = it2.next();
                if(process.isActive())
                {
                    if(vSelected.contains(process.getURI()))
                    {
                        vProcessDefinitions.add(index, process);
                        index++;
                    }
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getSelectedProcessDefinitions", e);
            System.out.println("Error en PreConfiguredControlPanel.getSelectedProcessDefinitions:" + e.getMessage());
		}
        return vProcessDefinitions;
    }

    //PAGINACION
    /**
    * Genera un StringBuffer que contiene el codigo HTML para presentar la
    * paginacion de las tareas. Recibe el numero de resultados por pagina,
    * el numero de la pagina actual y el total de resultados a presentar.
    *
    * @param            intRowsPerPage int
    * @param            intCurrPage int
    * @param            vectorSize int
    * @param            paramRequest SWBParamRequest
    * @param            request HttpServletRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getPagination(int intRowsPerPage, int intCurrPage,
            int vectorSize, SWBParamRequest paramRequest, HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer();
        int page = 0;
        try
        {
            Resource base = paramRequest.getResourceBase();
            String strFilterPagination =
                base.getAttribute("strFilterPagination")==null
                ?""
                :base.getAttribute("strFilterPagination");
            int totalPages = getTotalPages(vectorSize,intRowsPerPage);
            int endRow = getPageLastRow(vectorSize,intRowsPerPage,intCurrPage);
            int iniRow = getPageFirstRow(vectorSize,intRowsPerPage,intCurrPage);
            if(totalPages>1){
                if(intCurrPage>1){
                    int backPage = intCurrPage - 1;
                    SWBResourceURL sUrlFirstPage = paramRequest.getRenderUrl();
                    sUrlFirstPage.setParameter("cpCurrPage", "1");
                    sb.append("<a href=\"" + sUrlFirstPage);
                    if(!strFilterPagination.equalsIgnoreCase("")){
                        sb.append(strFilterPagination);
                    }
                    sb.append("\">" + paramRequest.getLocaleString("lblFirstPage") + "</a>");
                    SWBResourceURL sUrlBack = paramRequest.getRenderUrl();
                    sUrlBack.setParameter("cpCurrPage", String.valueOf(backPage));
                    sb.append("<a href=\"" + sUrlBack);
                    if(!strFilterPagination.equalsIgnoreCase("")){
                        sb.append(strFilterPagination);
                    }
                    sb.append("\" class=\"" + HREF_BACK_PAGE_CLASS + "\" >" + paramRequest.getLocaleString("lblBackPage") +
                            "</a>");
                }
                for(int i=0; i<totalPages; i++){
                    page = i+1;
                    if(page==intCurrPage)
                    {
                        sb.append(page);
                    } else {
                        SWBResourceURL sUrlPage = paramRequest.getRenderUrl();
                        sUrlPage.setParameter("cpCurrPage", String.valueOf(page));
                        sb.append("<a href=\"" + sUrlPage);
                        if(!strFilterPagination.equalsIgnoreCase("")){
                            sb.append(strFilterPagination);
                        }
                        sb.append("\">" + page + "</a>");
                    }
                }
                if(intCurrPage<totalPages)
                {
                    int nextPage = intCurrPage + 1;
                    SWBResourceURL sUrlNext = paramRequest.getRenderUrl();
                    sUrlNext.setParameter("cpCurrPage", String.valueOf(nextPage));
                    sb.append("<a href=\"" + sUrlNext);
                    if(!strFilterPagination.equalsIgnoreCase(""))
                    {
                        sb.append(strFilterPagination);
                    }
                    sb.append("\" class=\"" + HREF_NEXT_PAGE_CLASS +  "\" >" + paramRequest.getLocaleString("lblNextPage") + "</a>");
                    SWBResourceURL sUrlLastPage = paramRequest.getRenderUrl();
                    sUrlLastPage.setParameter("cpCurrPage", String.valueOf(totalPages));
                    sb.append("<a href=\"" + sUrlLastPage);
                    if(!strFilterPagination.equalsIgnoreCase(""))
                    {
                        sb.append(strFilterPagination);
                    }
                    sb.append("\">" + paramRequest.getLocaleString("lblLastPage") + "</a>");
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getPagination", e);
            System.out.println("Error en PreConfiguredControlPanel.getPagination:" + e.getMessage());
		}
        return sb;
    }

    /**
    * Calcula el numero del primer resultado para mostrar en la pagina actual
    *
    * @param            vectorSize int
    * @param            rowsPerPage int
    * @param            currentPage int
    * @return      		firstRow int
    */
    public int getPageFirstRow(int vectorSize, int rowsPerPage, int currentPage)
    {
        int firstRow = 0;
        try
        {
            if(vectorSize>0 && currentPage>0)
            {
                    firstRow = (currentPage - 1) * rowsPerPage;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getPageFirstRow", e);
            System.out.println("Error en PreConfiguredControlPanel.getPageFirstRow:" + e.getMessage());
		}
        return firstRow;
    }

    /**
    * Calcula el numero del ultimo resultado para mostrar en la pagina actual
    *
    * @param            vectorSize int
    * @param            rowsPerPage int
    * @param            currentPage int
    * @return      		endRow int
    */
    public int getPageLastRow(int vectorSize, int rowsPerPage, int currentPage)
    {
        int endRow = 0;
        try
        {
            if(vectorSize>0)
            {
                int totalPages = getTotalPages(vectorSize,rowsPerPage);
                endRow = totalPages==currentPage
                    ?vectorSize
                    :rowsPerPage * currentPage;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getPageLastRow", e);
            System.out.println("Error en PreConfiguredControlPanel.getPageLastRow:" + e.getMessage());
		}
        return endRow;
    }

    /**
    * Calcula el numero de paginas requeridas para mostrar los resultados
    *
    * @param            vectorSize int
    * @param            rowsPerPage int
    * @return      		totalPages int
    */
    public int getTotalPages(int vectorSize, int rowsPerPage)
    {
        int totalPages = 0;
        try
        {
            if(vectorSize >0 )
            {
                int modulus = vectorSize % rowsPerPage;
                totalPages = vectorSize / rowsPerPage;
                if(modulus>0)
                {
                    totalPages++;
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getTotalPages", e);
            System.out.println("Error en PreConfiguredControlPanel.getTotalPages:" + e.getMessage());
		}
        return totalPages;
    }


    //UTILERIAS
    /**
	 * Obtiene una lista con las propiedades del artefacto (no de una instancia especifica)
	 *
     * @param           selectedProcess org.semanticwb.process.model.Process
     * @param           displayType int
	 * @return          java.util.List de objetos SemanticProperty
	 */
    public java.util.List getProcessArtifactDefinitionProperties(org.semanticwb.process.model.Process selectedProcess, int displayType)
    {
        java.util.List listProperties = new ArrayList();
        int index = 0;
        try
        {
            Iterator itArtifacts = listArtifacts(selectedProcess);
            while(itArtifacts.hasNext())
            {
                SemanticObject sob = (SemanticObject) itArtifacts.next();
                //String strArtifactName  = sob.getDisplayName();
                SemanticClass sec = sob.transformToSemanticClass();
                Iterator<SemanticProperty> itProps = sec.listProperties();
                while(itProps.hasNext())
                {
                    SemanticProperty property =
                            (SemanticProperty) itProps.next();
                    TaskProperty tskProp = new TaskProperty(PROPERTY_TYPE_ARTIFACT,property.getURI(),displayType);
                    String strDisplayName  = property.getDisplayName() ==null ?"" :property.getDisplayName();
                    tskProp.setDisplayName(strDisplayName);
                    listProperties.add(index, tskProp);
                    index++;
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getProcessArtifactDefinitionProperties", e);
            System.out.println("Error en PreConfiguredControlPanel." + "getProcessArtifactDefinitionProperties:" + e.getMessage());
		}
        return listProperties;
    }

    /**
	 * Genera iterador con los artefactos de una definicion de proceso
	 *
     * @param           selectedProcess org.semanticwb.process.model.Process
	 * @return          Iterator de SemanticObjects
	 */
    public Iterator listArtifacts(org.semanticwb.process.model.Process selectedProcess)
    {
        Iterator itArtifacts = null;
        ArrayList al = new ArrayList();
        int index = 0;
        try
        {
            Iterator<GraphicalElement> itFobs = selectedProcess.listContaineds();
            while(itFobs.hasNext())
            {
               GraphicalElement ge = (GraphicalElement)itFobs.next();
               if(ge instanceof FlowNode)
               {
                   FlowNode fob = (FlowNode)ge;
//TODO: Revisar código siguiente comentado                     
//                   if(fob instanceof org.semanticwb.process.model.SubProcess)
//                   {
//                       org.semanticwb.process.model.SubProcess process = (org.semanticwb.process.model.SubProcess) fob;
//                       Iterator itTemp = process.listProcessClasses();
//                       while(itTemp.hasNext())
//                       {
//                           al.add(index, itTemp.next());
//                           index++;
//                       }
//                   }
                   itArtifacts = al.listIterator();
               }
            }
//TODO: Revisar código siguiente comentado                     
//            if(index ==0){
//                itArtifacts = selectedProcess.listProcessClasses();
//            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.listArtifacts", e);
            System.out.println("Error en PreConfiguredControlPanel." + "listArtifacts:" + e.getMessage());
		}
        return itArtifacts;
    }

    /**
	 * Obtiene la definicion de un proceso a partir de su URI
	 *
     * @param           URI String
     * @param           paramRequest SWBParamRequest
	 * @return          org.semanticwb.process.model.Proces
	 */
    public org.semanticwb.process.model.Process getProcessByURI(String URI,SWBParamRequest paramRequest)
    {
        org.semanticwb.process.model.Process selectedProcess = null;
        try{
            Vector vProcessDefinitions = BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(paramRequest);
            for(int i=0; i<vProcessDefinitions.size(); i++)
            {
                org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) vProcessDefinitions.get(i);
                if(URI.equalsIgnoreCase(process.getURI()))
                {
                    selectedProcess = process;
                    break;
                }
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.getProcessByURI", e);
            System.out.println("Error en PreConfiguredControlPanel.getProcessByURI:" + e.getMessage());
		}
        return selectedProcess;
    }

    /**
    * Verifica si la bandeja tiene activado el filtro para no mostrar tareas
    * cerradas del usuario actual
    *
    * @param            paramRequest SWBParamRequest
    * @return      		boolean
    */
    public boolean isClosedStatusFilterActive(SWBParamRequest paramRequest)
    {
        boolean isActive = false;
        String strActive = "";
        try
        {
            Resource base = paramRequest.getResourceBase();
            User currentUser = paramRequest.getUser();
            String strCustomizedData = base.getData(currentUser);
            Hashtable htable = getCustomizedData(strCustomizedData);
            if(htable.containsKey(CLOSED_TASKS_FILTER_CTRL)){
                strActive = htable.get(CLOSED_TASKS_FILTER_CTRL).toString();
            }
            if(strActive.equalsIgnoreCase(ACTIVE_CLOSED_TASKS_FILTER)){
                isActive = true;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.isClosedStatusFilterActive", e);
            System.out.println("Error en PreConfiguredControlPanel.isClosedStatusFilterActive:" + e.getMessage());
		}
        return isActive;
    }

    /**
    * Determina si el usuario ha cambiado su seleccion de proceso configurado.
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		boolean
    */
    public boolean isDifferentSelectedProcess(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        boolean isDifferent = false;
        try{
            Resource base = paramRequest.getResourceBase();
            String strCPProcessDefinitions = base.getAttribute(PROCESS_SELECTION_CTRL)==null
                ?""
                :base.getAttribute(PROCESS_SELECTION_CTRL);
            String strProcessDefinitions = request.getParameter(PROCESS_SELECTION_CTRL);

            if(strProcessDefinitions!=null && !strProcessDefinitions.equalsIgnoreCase("")
                    && strCPProcessDefinitions!=null && !strCPProcessDefinitions.equalsIgnoreCase(""))
            {
                if(!strProcessDefinitions.equalsIgnoreCase(strCPProcessDefinitions))
                {
                    isDifferent = true;
                }
            } else if(strProcessDefinitions!=null && !strProcessDefinitions.equalsIgnoreCase("")) {
                isDifferent = true;
            }
        } catch(Exception e){
            log.error("Error en PreConfiguredControlPanel.isDifferentSelectedProcess", e);
            System.out.println("Error en PreConfiguredControlPanel.isDifferentSelectedProcess:" + e.getMessage());
		}
        return isDifferent;
    }

    /**
    * Ordena un vector de objetos TaskProperty en base a su propiedad orderOnTask
    *
    * @param            request HttpServletRequest
    * @param            paramRequest SWBParamRequest
    * @return      		boolean
    */    
    public static void sortByOrder(Vector v){
        try {
            TaskProperty.TaskPropertyOrderComparator comparator = new TaskProperty.TaskPropertyOrderComparator();
            Collections.sort(v,comparator);
        }
        catch(Exception e){
            System.out.println("Error en PreConfiguredControlPanel.sortByOrder:" + e.getMessage());
            log.error("Error en PreConfiguredControlPanel.sortByOrder", e);
        }
    }


    //INNER CLASSES
    public static class TaskProperty
    {
        private String type;
        private String id;
        private int displayType;    //0=link, 1=legend, 2=columns
        private int displayOrder;
        private String displayName;
        private String value;

        public TaskProperty(){
        }

        public TaskProperty(String type, String id, int displayType)
        {
            this.type = type;
            this.id = id;
            this.displayType = displayType;     
            this.displayOrder = 0;
            this.displayName = "";
            this.value = "";
        }

        public TaskProperty(String type, String id, int displayType,
                int displayOrder)
        {
            this.type = type;
            this.id = id;
            this.displayType = displayType;     //0=link, 1=legend, 2=columns
            this.displayOrder = displayOrder;
            this.displayName = "";
            this.value = "";
        }

        /**
        * Esta clase se utiliza para envolver las propiedades de tarea (definicion e instancia) y
        * las propiedades de los artefactos, de tal manera que puedan desplegarse como un vinculo con leyenda
        * o como dato en una columna de acuerdo con un orden.
        *
        * @param            type String
        * @param            id String
        * @param            displayType int
        * @param            displayOrder int
        * @param            displayName String
        * @param            value String
        * @return      		TaskProperty
        */
        public TaskProperty(String type, String id, int displayType,
                int displayOrder, String displayName, String value)
        {
            this.type = type;
            this.id = id;
            this.displayType = displayType;     //0=link, 1=legend, 2=columns
            this.displayOrder = displayOrder;
            this.displayName = displayName;
            this.value = value;
        }

        public void setType(String type){
            this.type = type;
        }

        public void setId(String id){
            this.id = id;
        }

        public void setDisplayType(int displayType){
            this.displayType = displayType;
        }

        public void setDisplayOrder(int displayOrder){
            this.displayOrder = displayOrder;
        }

        public void setDisplayName(String displayName){
            this.displayName = displayName;
        }

        public void setValue(String value){
            this.value = value;
        }

        public String getType(){
            return this.type;
        }

        public String getId(){
            return this.id;
        }

        public int getDisplayType(){
            return this.displayType;
        }

        public int getDisplayOrder(){
            return this.displayOrder;
        }

        public String getDisplayName(){
            return this.displayName;
        }

        public String getValue(){
            return this.value;
        }

        /**
        * Implementación de Comparator para objetos de tipo TaskProperty
        */
        static class TaskPropertyOrderComparator implements Comparator{
            public int compare(Object obj1, Object obj2)
            {
                int result = 0;
                TaskProperty pProp1 = (TaskProperty)obj1;
                TaskProperty pProp2 = (TaskProperty)obj2;
                Integer order1 = pProp1.getDisplayOrder();
                Integer order2 = pProp2.getDisplayOrder();
                String strName1 = pProp1.getId();
                String strName2 = pProp2.getId();
                result = order1.compareTo(order2);
                if(result == 0){
                    result = strName2.compareTo(strName1);
                }
                return result;
            }
        }
    }

}
