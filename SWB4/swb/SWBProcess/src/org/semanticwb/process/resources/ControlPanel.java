/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class ControlPanel extends GenericAdmResource
{

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

    public final static String imgPath = SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/images/";
    public final static String filenamePdf = SWBUtils.getApplicationPath() +
            "/swbadmin/ControlPanelReport.pdf";
    public final static String strDownloadPdf  = SWBPortal.getContextPath() +
            "/swbadmin/ControlPanelReport.pdf";
    public final static String filenameXml = SWBUtils.getApplicationPath() +
            "/swbadmin/ControlPanelReport.xml";
    public final static String strDownloadXml  = SWBPortal.getContextPath() +
            "/swbadmin/ControlPanelReport.xml";
    public final static String filenameRtf = SWBUtils.getApplicationPath() +
            "/swbadmin/ControlPanelReport.rtf";
    public final static String strDownloadRtf  = SWBPortal.getContextPath() +
            "/swbadmin/ControlPanelReport.rtf";
    public final static int reportColumnNumber = 11;
    public final static int reportCellPadding = 4;
    public final static int reportHeaderPadding = 6;
    public final static int pdfTableWidthPercentage = 100;
    public final static float pdfHeaderTableHeight = 15;

    public final static int FILTER_BY_DATE = 0;
    public final static int FILTER_BY_TITLE = 1;
    public final static int FILTER_BY_PROCESS = 2;
    public final static int FILTER_BY_STATUS = 3;
    public final static int FILTER_BY_PRIORITY = 4;
    public final static int MAX_FILTER = 5;
    public final static String END_DATE_FILTER_CTRL = "fechaFin";
    public final static String INITIAL_DATE_FILTER_CTRL = "fechaIni";
    public final static String PROCESS_FILTER_CTRL = "cboFilterProcess";
    public final static String STATUS_FILTER_CTRL = "cboFilterStatus";
    public final static String TITLE_FILTER_CTRL = "cboFilterTaskTitle";
    public final static String PRIORITY_FILTER_CTRL = "cboPriority";
    public final static String SORTING_CTRL = "cboSortBy";
    public final static String END_DATE_REPORT_CTRL="repFechaFin";
    public final static String INITIAL_DATE_REPORT_CTRL="repFechaIni";
    public final static String PROCESS_REPORT_CTRL = "cboReportProcess";
    public final static String STATUS_REPORT_CTRL = "cboReportStatus";
    public final static String PRIORITY_REPORT_CTRL = "cboReportPriority";
    //org.semanticwb.process.model.Activity
    public final static String ALL_STATUS = Instance.STATUS_PROCESSING + "|" +
            Instance.STATUS_OPEN + "|" + Instance.STATUS_ABORTED + "|"
            + Instance.STATUS_CLOSED + "|" + Instance.STATUS_STOPED + "|";
    public final static String ALL_PRIORITY = 1 + "|" + 2 + "|" + 3 + "|" + 4 + "|" + 5 + "|";

    private static Logger log = SWBUtils.getLogger(ControlPanel.class);
    private final static String strDefaultHref =
            "TaskDefinition.url|?suri=|TaskInstance.encodedURI|";
    private final static int intDefaultRowsPerPage = 3;
    private HashMap taskAttributes;

    /**
	 * Regresa un vector con las propiedades (generales) de la tarea que pueden seleccionarse
     * Propiedades Grales de la tarea = TaskDefinition +  taskInstance
	 *
     * @param           paramsRequest SWBParamRequest
	 * @return          Vector
	 */
    public Vector getTaskProperties(SWBParamRequest paramRequest)
    {
        Vector vProps = new Vector();
        int index = 0;
        try
        {
            String [] strProperties = {
            "TaskDefinition",paramRequest.getLocaleString("cpTaskDefHeader0"),"id",
            "TaskDefinition",paramRequest.getLocaleString("cpTaskDefHeader1"),"title",
            "TaskDefinition",paramRequest.getLocaleString("cpTaskDefHeader2"),"uri",
            "TaskDefinition",paramRequest.getLocaleString("cpTaskDefHeader3"),"url",
            "TaskDefinition",paramRequest.getLocaleString("cpTaskDefHeader4"),"description",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader0"),"id",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader1"),"status",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader2"),"uri",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader3"),"encodedURI",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader4"),"created",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader5"),"creator",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader6"),"modified",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader7"),"modifiedBy",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader8"),"ended",
            "TaskInstance",paramRequest.getLocaleString("cpTaskInstHeader9"),"endedBy"
            };
            for(int i=0; i<strProperties.length-2; i=i+3)
            {
                String[] temp = {strProperties[i],strProperties[i+1],strProperties[i+2]};
                vProps.add(index,temp);
                index++;
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getTaskProperties", e);
            System.out.println("Error en ControlPanel.getTaskProperties:" +
                    e.getMessage());
		}
        return vProps;
    }

    /**
	 * Cargar en el mapa del recurso (taskAttributes) la informacion que se encuentra en la  base del recurso
     * La información se refiere a la composicion del vinculo, leyenda y columnas para cada definicion de proceso seleccionada
	 *
     * @param           paramsRequest SWBParamRequest
	 */
    public void getResourceTaskAttributesMap(SWBParamRequest paramRequest)
    {
        try
        {
            Resource base = paramRequest.getResourceBase();
            HashMap hMap = new HashMap();
            Iterator<String> itAttributes = base.getAttributeNames();
            while(itAttributes.hasNext())
            {
                String key = itAttributes.next().toString();
                if(key.contains("taskAttributes"))
                {
                    String tmpAttValue = base.getAttribute(key);
                    String[] tmpArr = tmpAttValue.split(";");
                    String trueKey = tmpArr[0];
                    ArrayList value = new ArrayList();
                    int innerIndex = 0;
                    for(int i=1; i<tmpArr.length; i++)
                    {
                        value.add(innerIndex, tmpArr[i]);
                        innerIndex++;
                    }
                    hMap.put(trueKey, value);
                }
            }
            taskAttributes = hMap;
        } catch(Exception e){
          //log.error("Error en ControlPanel.getResourceTaskAttributesMap", e);
            System.out.println("Error en ControlPanel.getResourceTaskAttributesMap:" +
                    e.getMessage());
		}
    }

    /**
	 * Actualiza el mapa donde se guardan los procesos seleccionados, las
     * propiedades de tarea para cada proceso seleccionado y las propiedades de
     * artefacto para cada proceso seleccionado
	 *
     * @param           paramsRequest SWBParamRequest
	 * @return          HashMap
	 */
    public HashMap setTaskAttributesMap(SWBParamRequest paramRequest)
    {
        HashMap hTasks = new HashMap();
        try
        {
            int columnCount = 1;
            Resource base = paramRequest.getResourceBase();
            Vector vSelTaskProps = new Vector();
            String strCPTaskParams = base.getAttribute("cpTaskParams")==null
                    ?""
                    :base.getAttribute("cpTaskParams");
            String strCPSelProps = base.getAttribute("cpSelectedProperties")==null
                    ?""
                    :base.getAttribute("cpSelectedProperties");
            //tasklinkheaders = solo las propiedades (de tarea y artefactos) con applyOn=columnas
            vSelTaskProps = getSelectedTaskProperties(paramRequest);
            Vector vSelectedProps =
                        getSelectedProcessProperties(paramRequest,"cpSelectedProperties");
            Vector vSelected = getSelectedProcessDefinitions(paramRequest);
            for(int i=0; i<vSelected.size(); i++)
            {
                String strHref = "";
                String strLegend = "";
                String strColumns = "";
                strHref = strHref + strCPTaskParams + "|";
                org.semanticwb.process.model.Process process =
                        (org.semanticwb.process.model.Process) vSelected.get(i);
                Vector vLegend = new Vector();
                Vector vColumns = new Vector();
                int iLegend = 0;
                int iColumns = 0;
                for(int j=0; j<vSelTaskProps.size(); j++)
                {
                    TaskProperty tProp = (TaskProperty) vSelTaskProps.get(j);
                    if(tProp.getURI().equalsIgnoreCase(process.getURI()))
                    {
                        if(tProp.isAppliedOnTaskLink())
                        {
                            strHref = strHref + tProp.getType() + "." + tProp.getName() + "|";
                        }
                        if(tProp.isAppliedOnTaskLegend())
                        {
                            //strLegend = strLegend + tProp.getType() + "." + tProp.getName() + "|";
                            String strTempName = tProp.getType() + "." + tProp.getName();
                            ComparableProperty compy = new ComparableProperty(strTempName, tProp.getURI(), tProp.getOrderOnTask());
                            vLegend.add(iLegend, compy);
                            iLegend++;
                        }
                        if(tProp.isAppliedOnTaskColumn())
                        {
                            //strColumns = strColumns + tProp.getType() + "." + tProp.getName() + "|";
                            String strTempName = tProp.getType() + "." + tProp.getName();
                            ComparableProperty compy = new ComparableProperty(strTempName, tProp.getURI(), tProp.getOrderOnTask());
                            vColumns.add(iColumns, compy);
                            iColumns++;
                            columnCount++;
                        }
                    }
                }
                for(int j=0; j<vSelectedProps.size(); j++)
                {
                    ProcessProperty pp = (ProcessProperty) vSelectedProps.get(j);
                    if(pp.getProcessURI().equalsIgnoreCase(process.getProcessClass().getURI()))
                    {
                        if(pp.isAppliedOnTaskLink())
                        {
                            strHref = strHref + pp.getURI() + "|";
                        }
                        if(pp.isAppliedOnTaskLegend())
                        {
                            //strLegend = strLegend + pp.getURI() + "|";
                            ComparableProperty compy = new ComparableProperty(pp.getURI(), pp.getURI(), pp.getOrderOnTask());
                            vLegend.add(iLegend, compy);
                            iLegend++;
                        }
                        if(pp.isAppliedOnTaskColumn())
                        {
                            //strColumns = strColumns + pp.getURI() + "|";
                            ComparableProperty compy = new ComparableProperty(pp.getURI(), pp.getURI(), pp.getOrderOnTask());
                            vColumns.add(iColumns, compy);
                            iColumns++;
                            columnCount++;
                        }
                    }
                }
                //ordenar leyendas y columnas
                ComparableProperty.sortComparableProperty(vLegend);
                ComparableProperty.sortComparableProperty(vColumns);
                //convertir vectores ordenados a String
                for(int k=0; k<vLegend.size(); k++){
                    ComparableProperty compy = (ComparableProperty) vLegend.get(k);
                    strLegend = strLegend + compy.getName() + "|";
                }
                for(int k=0; k<vColumns.size(); k++){
                    ComparableProperty compy = (ComparableProperty) vColumns.get(k);
                    strColumns = strColumns + compy.getName() + "|";
                }
                ArrayList arrParams = new ArrayList();
                arrParams.add(0, strHref);
                arrParams.add(1, strLegend);
                arrParams.add(2, strColumns);
                hTasks.put(process.getURI(), arrParams);
            }
            taskAttributes = hTasks;
            base.setAttribute("intColumnCount", String.valueOf(columnCount));
            base.updateAttributesToDB();
        } catch(Exception e){
          //log.error("Error en ControlPanel.setTaskAttributesMap", e);
            System.out.println("Error en ControlPanel.setTaskAttributesMap:" +
                    e.getMessage());
		}
        return hTasks;
    }

    /**
	 * Carga en la base del recurso las propiedades de tarea contenidas en un
     * HashMap para su resguardo.
	 *
     * @param           hMap HashMap
     * @param           paramsRequest SWBParamRequest
	 */
    public void setResourceTaskAttributesMap(HashMap hMap, SWBParamRequest paramRequest)
    {
        try
        {
            Resource base = paramRequest.getResourceBase();
            Iterator itMap = hMap.keySet().iterator();
            int index = 0;
            while(itMap.hasNext())
            {
                String key = itMap.next().toString();
                String nwValue = key + ";";
                ArrayList tmpValue = (ArrayList) hMap.get(key);
                for(int i=0; i<tmpValue.size(); i++)
                {
                    nwValue = nwValue + tmpValue.get(i) + ";";
                }
                String strAttName = "taskAttributes" +  index;
                index++;
                base.setAttribute(strAttName, nwValue);
            }
            base.updateAttributesToDB();
        } catch(Exception e){
          //log.error("Error en ControlPanel.setResourceTaskAttributesMap", e);
            System.out.println("Error en ControlPanel.setResourceTaskAttributesMap:" +
                    e.getMessage());
		}
    }

    /**
	 * Obtiene de la base del recurso las propiedades generales de la tarea
     * seleccionadas. Para cada una de las definiciones de proceso seleccionadas,
     * verifica si se selecciono alguna propiedad general de la tarea, en cuyo
     * caso genera un objeto TaskProperty indicando su configuración y la agrega al vector.
	 *
     * @param           paramsRequest SWBParamRequest
     * @return          Vector
	 */
    public Vector getSelectedTaskProperties(SWBParamRequest paramRequest)
    {
        Vector vSelTaskProps = new Vector();
        try
        {
            Resource base = paramRequest.getResourceBase();
            String[] arrTaskProps = null;
            int index = 0;
            String strCPTaskProps = base.getAttribute("taskProperties")==null
                    ?""
                    :base.getAttribute("taskProperties");
            Vector vTaskProps = getTaskProperties(paramRequest);

            if(!strCPTaskProps.equalsIgnoreCase(""))
            {
                arrTaskProps = strCPTaskProps.split("\\|");
                Vector vSelected = getSelectedProcessDefinitions(paramRequest);
                for(int i=0; i<vSelected.size(); i++)
                {
                    org.semanticwb.process.model.Process process =
                            (org.semanticwb.process.model.Process) vSelected.get(i);
                    for(int j=0; j<arrTaskProps.length-4; j=j+5)
                    {
                        String strProcURI = arrTaskProps[j];
                        if(strProcURI.equalsIgnoreCase(process.getURI()))
                        {
                            String strPropType = arrTaskProps[j+1];
                            String strPropName = arrTaskProps[j+2];
                            String strApplyOnCriteria = arrTaskProps[j+3];
                            String strOrderValue = arrTaskProps[j+4];
                            String displayName  = getTaskPropertyName(vTaskProps,strPropType,strPropName);
                            TaskProperty tProp = new TaskProperty(strPropName,displayName,strPropType,strProcURI);
                            tProp.setDisplayOnTask(true);
                            boolean[] bArray;
                            bArray = new boolean[3];
                            if(strApplyOnCriteria.contains(","))
                            {
                                String[] anArray = strApplyOnCriteria.split(",");
                                for(int k=0; k<anArray.length; k++)
                                {
                                    bArray[k] = Boolean.valueOf(anArray[k]);
                                }
                            } else {
                                bArray[0] = false;
                                bArray[1] = false;
                                bArray[2] = false;
                            }
                            tProp.setApplyOnTask(bArray);
                            tProp.setOrderOnTask(Integer.parseInt(strOrderValue));
                            vSelTaskProps.add(index, tProp);
                        }
                    }
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getSelectedTaskProperties", e);
            System.out.println("Error en ControlPanel.getSelectedTaskProperties:" +
                    e.getMessage());
		}
        return vSelTaskProps;
    }

    //FILTROS
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
                    vFilteredTasks =
                            BPMSTask.ClassMgr.filterTasksByProcess(v,
                                filterValues);
                    break;
                case FILTER_BY_STATUS:
                    vFilteredTasks =
                            BPMSTask.ClassMgr.filterTasksByStatus(v,
                                filterValues);
                    break;
                case FILTER_BY_TITLE:
                    vFilteredTasks =
                            BPMSTask.ClassMgr.filterTasksByTitle(v,
                                filterValues);
                    break;
                case FILTER_BY_PRIORITY:
                    vFilteredTasks = BPMSTask.ClassMgr.filterTasksByPriority(v,filterValues);
                    break;
                default:
                    vFilteredTasks =
                            BPMSTask.ClassMgr.filterTasksByDate(v,
                            filterValues);
                    break;
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.filterTasks", e);
            System.out.println("Error en ControlPanel.filterTasks:" +
                    e.getMessage());
		}
        return vFilteredTasks;
    }

    /**
    * Determina los filtros seleccionados por el usuario y los aplica al vector
    * de objetos FlowNodeInstance
    *
    * @param            vTasks Vector
    * @param            request HttpServletRequest
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
            String strInitialFilterDate =
                request.getParameter(INITIAL_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(INITIAL_DATE_FILTER_CTRL);
            String strEndFilterDate =
                request.getParameter(END_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(END_DATE_FILTER_CTRL);
            String strFilterProcess =
                request.getParameter(PROCESS_FILTER_CTRL)==null
                ?"0"
                :request.getParameter(PROCESS_FILTER_CTRL);
            String strFilterStatus =
                request.getParameter(STATUS_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(STATUS_FILTER_CTRL);
            String strFilterPriority =
                request.getParameter(PRIORITY_FILTER_CTRL)==null
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
                    if(strFilterProcess.equalsIgnoreCase("0") &&
                            strFilterStatus.equalsIgnoreCase("-1"))
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
            if(strFilterPagination!=null && !strFilterPagination.equalsIgnoreCase("")){
                base.setAttribute("strFilterPagination", strFilterPagination);
                base.updateAttributesToDB();
            }

        } catch(Exception e){
          //log.error("Error en ControlPanel.applyFilters", e);
            System.out.println("Error en ControlPanel.applyFilters:" +
                    e.getMessage());
		}
        return vFiltered;
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
            String strInitialFilterDate =
                request.getParameter(INITIAL_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(INITIAL_DATE_FILTER_CTRL);
            String strEndFilterDate =
                request.getParameter(END_DATE_FILTER_CTRL)==null
                ?""
                :request.getParameter(END_DATE_FILTER_CTRL);
            String strFilterProcess =
                request.getParameter(PROCESS_FILTER_CTRL)==null
                ?"0"
                :request.getParameter(PROCESS_FILTER_CTRL);
            String strFilterStatus =
                request.getParameter(STATUS_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(STATUS_FILTER_CTRL);
            String strFilterTitle =
                request.getParameter(TITLE_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(TITLE_FILTER_CTRL);
            String strFilterPriority =
                request.getParameter(PRIORITY_FILTER_CTRL)==null
                ?"-1"
                :request.getParameter(PRIORITY_FILTER_CTRL);
            sb.append("<ul>");
            if(!strFilterProcess.equalsIgnoreCase("0")){
                sb.append("<li class=\"f-pro\">" + paramsRequest.getLocaleString("lblFilterProcess") +
                        " " + SemanticObject.getSemanticObject(strFilterProcess).getDisplayName() + "</li>");
            }
            if(!strFilterStatus.equalsIgnoreCase("-1")){
                sb.append("<li class=\"f-est\">" + paramsRequest.getLocaleString("lblFilterStatus") +
                        " " +  getStatusDescription(paramsRequest, Integer.parseInt(strFilterStatus)) + "</li>");
            }
            if(!strFilterPriority.equalsIgnoreCase("-1")){
                int iPriority = Integer.parseInt(strFilterPriority);
                sb.append("<li class=\"f-est\">" + paramsRequest.getLocaleString("lblFilterPriority") +
                        " " + getPriorityDescription(paramsRequest, iPriority) + "</li>");
            }
            if(!strFilterTitle.equalsIgnoreCase("-1")){
                sb.append("<li class=\"f-est\">" + paramsRequest.getLocaleString("lblFilterTaskName") +
                        " " + strFilterTitle+ "</li>");
            }
            if(!strInitialFilterDate.equalsIgnoreCase("")){
                sb.append("<li class=\"f-ini\">" + paramsRequest.getLocaleString("lblFilterIniDate") +
                        " " + strInitialFilterDate + "</li>");
                if(!strEndFilterDate.equalsIgnoreCase(""))
                {
                    sb.append("<li class=\"f-fin\">" + paramsRequest.getLocaleString("lblFilterEndDate")
                            + " " + strEndFilterDate + "</li>");
                }
            }
            sb.append("</ul>" );
        } catch(Exception e){
          //log.error("Error en ControlPanel.getUsedFilters", e);
            System.out.println("Error en ControlPanel.getUsedFilters:"
                    + e.getMessage());
		}
        return sb;
    }

    //PROCESOS
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
            Iterator <ProcessWebPage> itProcessWebPages =
                    site.listProcessWebPages();

            String strControlPanelProcessDefinitions =
                base.getAttribute("cpProcessDefinitions")==null
                    ?""
                    :base.getAttribute("cpProcessDefinitions");
            Vector vSelected =
                    BPMSProcessInstance.ClassMgr.stringToVector(strControlPanelProcessDefinitions);
            while(itProcessWebPages.hasNext())
            {
                ProcessWebPage pwp =
                        (ProcessWebPage) itProcessWebPages.next();
                if(pwp.isActive()){
                    org.semanticwb.process.model.Process process = pwp.getProcess();
                    if(vSelected.contains(process.getURI()))
                    {
                        vProcessDefinitions.add(index, process);
                        index++;
                    }
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getSelectedProcessDefinitions", e);
            System.out.println(
                    "Error en ControlPanel.getSelectedProcessDefinitions:" +
                    e.getMessage());
		}
        return vProcessDefinitions;
    }

    //ARTEFACTOS
    /**
	 * Parsea y actualiza en la base del recurso las propiedades seleccionadas
     * de los artefactos.
	 *
     * @param               request HttpServletRequest
     * @param               paramRequest SWBParamRequest
	 * @return      		String con las propiedades seleccionadas
     * @see
	 */
    public String updateArtifactPropertiesValues(HttpServletRequest request,
            SWBParamRequest paramRequest)
    {
        StringBuffer ret = new StringBuffer();
        String msg = "";
        String strSelectedProps  = "";
        try
        {
            Resource base = paramRequest.getResourceBase();
            String [] strIncludeProps = request.getParameterValues("cpIncludeProperty");
            System.out.println("----updateArtifactPropertiesValues strIncludeProps:" + strIncludeProps.length);
            for(int i=0; i<strIncludeProps.length; i++)
            {
                String strURI = strIncludeProps[i];
                String[] strApplyOnValue =
                        request.getParameterValues("cpApplyOn" + strURI)==null
                        ?null
                        :request.getParameterValues("cpApplyOn" + strURI);
                String strApplyOnCriteria = "";
                String arrApplyOn[] = {"false","false","false"};
                for(int j=0; j<strApplyOnValue.length; j++)
                {
                    if(strApplyOnValue[j].equalsIgnoreCase("0")){
                        arrApplyOn[0] = "true";
                    } else if(strApplyOnValue[j].equalsIgnoreCase("1")){
                        arrApplyOn[1] = "true";
                    } else if(strApplyOnValue[j].equalsIgnoreCase("2")){
                        arrApplyOn[2] = "true";
                    }
                }
                for(int k=0; k<arrApplyOn.length; k++)
                {
                    strApplyOnCriteria =
                            strApplyOnCriteria + arrApplyOn[k] + ",";
                }
                String strOrderValue =
                        request.getParameter("cpPropertyOrder" + strURI)==null
                        ?"0"
                        :request.getParameter("cpPropertyOrder" + strURI);
                strSelectedProps = strSelectedProps + strURI +
                        "|" + strApplyOnCriteria + "|" + strOrderValue + "|";
            }
            System.out.println("----updateArtifactPropertiesValues strSelectedProps:" + strSelectedProps);
            if(strSelectedProps!=null && !strSelectedProps.equalsIgnoreCase("")){
                base.setAttribute("cpSelectedProperties", strSelectedProps);
                base.updateAttributesToDB();
            }
            msg = (new StringBuilder()).append(
                    paramRequest.getLocaleString(
                    "msgOkUpdateResource")).append(" "
                    + base.getId()).toString();
            ret.append((new StringBuilder()).append(
                "<script language=\"JavaScript\">\n alert('").append(
                msg).append("');\n").append("</script>\n").toString());
            ret.append((new StringBuilder()).append("<script " +
                    "language=\"JavaScript\">\n   location='").append(
                    paramRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';\n").append(
                    "</script>\n").toString());
        } catch(Exception e){
          //log.error("Error en ControlPanel.updateArtifactPropertiesValues", e);
            System.out.println("Error en ControlPanel.updateArtifactPropertiesValues:" +
                    e.getMessage());
		}
        return ret.toString();
    }

    /**
	 * Parsea y actualiza en la base del recurso los valores seleccionados en el
     * ProcessForm (definiciones de proceso seleccionadas, titulo, etc).
	 *
     * @param               request HttpServletRequest
     * @param               paramRequest SWBParamRequest
	 * @return      		String
     * @see
	 */
    public String updateProcessFormValues(HttpServletRequest request,
            SWBParamRequest paramRequest)
    {
        String strSelectedProperties  = "";
        StringBuffer ret = new StringBuffer();
        String msg = null;
        try
        {
            Resource base = paramRequest.getResourceBase();
            String strTitle = request.getParameter("title")==null
                    ?""
                    :request.getParameter("title");
            String strCPRowsPerPage = request.getParameter("cpRowsPerPage")==null
                    ?""
                    :request.getParameter("cpRowsPerPage");
            String strTaskParams = request.getParameter("cpTaskParams")==null
                    ?""
                    :request.getParameter("cpTaskParams");
            String [] strProcessDefinitions =
               request.getParameterValues("cpProcessDefinitions");
            String [] strIncludeProperties =
                    request.getParameterValues("cpIncludeTaskProperty");
            for(int i=0; i<strIncludeProperties.length; i++)
            {
                String strApplyOnCriteria = "";
                String arrApplyOn[] = {"false","false","false"};
                String[] strTemp = strIncludeProperties[i].split("\\|");
                String strProcURI = strTemp[0];
                String strPropType = strTemp[1];
                String strPropName = strTemp[2];
                String strTmpApply = "cpTaskPropApplyOn|" + strIncludeProperties[i];
                String[] strApplyOnValue =
                        request.getParameterValues(strTmpApply)==null
                        ?null
                        :request.getParameterValues(strTmpApply);
                for(int j=0; j<strApplyOnValue.length; j++)
                {
                    if(strApplyOnValue[j].equalsIgnoreCase("0")){
                        arrApplyOn[0] = "true";
                    }
                    if(strApplyOnValue[j].equalsIgnoreCase("1")){
                        arrApplyOn[1] = "true";
                    }
                    if(strApplyOnValue[j].equalsIgnoreCase("2")){
                        arrApplyOn[2] = "true";
                    }
                }
                for(int k=0; k<arrApplyOn.length; k++)
                {
                    strApplyOnCriteria = strApplyOnCriteria + arrApplyOn[k] + ",";
                }
                String strTmpOrder = "cpTaskPropOrder|" + strIncludeProperties[i];
                String strOrderValue =
                        request.getParameter(strTmpOrder)==null
                        ?"0"
                        :request.getParameter(strTmpOrder);
                strSelectedProperties = strSelectedProperties + strProcURI + "|" +
                        strPropType + "|" + strPropName + "|" +
                        strApplyOnCriteria + "|" + strOrderValue + "|";
            }

            if(strTitle!=null && !strTitle.equalsIgnoreCase(""))
            {
                base.setAttribute("title", strTitle);
            }
            if(strCPRowsPerPage!=null && !strCPRowsPerPage.equalsIgnoreCase(""))
            {
                base.setAttribute("cpRowsPerPage", strCPRowsPerPage);
            }
            if(strTaskParams!=null && !strTaskParams.equalsIgnoreCase(""))
            {
                base.setAttribute("cpTaskParams", strTaskParams);
            }
            if(strProcessDefinitions!=null && strProcessDefinitions.length>0)
            {
                String definitions = checkboxesToString(strProcessDefinitions);
                if(definitions!=null && !definitions.equalsIgnoreCase(""))
                {
                    base.setAttribute("cpProcessDefinitions",definitions);
                }
            }
            if(strSelectedProperties!=null && !strSelectedProperties.equalsIgnoreCase(""))
            {
                base.setAttribute("taskProperties", strSelectedProperties);
            }
            base.updateAttributesToDB();
        } catch(Exception e){
          //log.error("Error en ControlPanel.updateProcessFormValues", e);
            System.out.println("Error en ControlPanel.updateProcessFormValues:" +
                    e.getMessage());
		}
        return ret.toString();
    }

    /**
	 * Encontrar en un vector una ProcessProperty en base al URI
	 *
     * @param           v Vector
     * @param           URI String
	 * @return          ProcessProperty
	 */
    public ProcessProperty findProcessProperty(Vector v, String URI)
    {
        ProcessProperty property = null;
        try
        {
            for(int i=0; i<v.size(); i++)
            {
                ProcessProperty temp = (ProcessProperty) v.get(i);
                if(temp.getURI().equalsIgnoreCase(URI))
                {
                    property = temp;
                    break;
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.findProcessProperty", e);
            System.out.println("Error en ControlPanel." +
                    "findProcessProperty:" + e.getMessage());
		}
        return property;
    }

    /**
	 * Verificar si una ProcessProperty existe en el vector
	 *
     * @param       v Vector
     * @param       URI String
	 * @return      boolean
     * @see
	 */
    public boolean containsProcessProperty(Vector v, String URI)
    {
        boolean contains = false;
        for(int i=0; i<v.size(); i++)
        {
            ProcessProperty prop = (ProcessProperty) v.get(i);
            if(prop.getURI().equalsIgnoreCase(URI))
            {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
    * Obtener un vector con las propiedades seleccionadas por el usuario
    *
    * @param            paramRequest SWBParamRequest
    * @param            strPropertyLegend String
    * @return      		Vector de ProcessProperty
    * @see
    */
    public Vector getSelectedProcessProperties(SWBParamRequest paramRequest, String strPropertyLegend)
    {
        Vector vSelectedProcessProperties = new Vector();
        int index = 0;
        try {
            Resource base = paramRequest.getResourceBase();
            String strSelectedProperties =
                    base.getAttribute(strPropertyLegend)==null
                    ?""
                    :base.getAttribute(strPropertyLegend);
            if(!strSelectedProperties.equalsIgnoreCase(""))
            {
                String [] arrSelectedProperties =
                        strSelectedProperties.split("\\|");
                for(int i=0; i<arrSelectedProperties.length-2; i=i+3)
                {
                    String strURI = arrSelectedProperties[i];
                    String strApplyOnValue = arrSelectedProperties[i+1];
                    String strOrderValue = arrSelectedProperties[i+2];
                    ProcessProperty pp = new ProcessProperty();
                    SemanticProperty semprop =
                            SemanticObject.createSemanticObject(
                                strURI).transformToSemanticProperty();
                    pp.setProcessURI(semprop.getDomainClass().getURI());
                    pp.setProperty(semprop);
                    pp.setURI(strURI);
                    pp.setDisplayOnTask(strApplyOnValue.equalsIgnoreCase("0"));
                    pp.setOrderOnTask(Integer.parseInt(strOrderValue));
                    boolean[] bArray;
                    bArray = new boolean[3];
                    if(strApplyOnValue.contains(","))
                    {
                        String[] anArray = strApplyOnValue.split(",");
                        for(int j=0; j<anArray.length; j++)
                        {
                            bArray[j] = Boolean.valueOf(anArray[j]);
                        }
                    } else {
                        bArray[0] = false;
                        bArray[1] = false;
                        bArray[2] = false;
                    }
                    pp.setApplyOnTask(bArray);

                    vSelectedProcessProperties.add(index, pp);
                    index++;
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getSelectedProcessProperties", e);
            System.out.println("Error en ControlPanel." +
                    "getSelectedProcessProperties:" + e.getMessage());
		}
        return vSelectedProcessProperties;
    }

    /**
	 * Genera iterador con los artefactos de una definicion de proceso
	 *
     * @param           selectedProcess org.semanticwb.process.model.Process
	 * @return          Iterator de SemanticObjects
	 */
    public Iterator listArtifacts(
            org.semanticwb.process.model.Process selectedProcess)
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
                   if(fob instanceof org.semanticwb.process.model.SubProcess)
                   {
                       org.semanticwb.process.model.SubProcess process = (org.semanticwb.process.model.SubProcess) fob;
                       Iterator itTemp = process.listProcessClasses();
                       while(itTemp.hasNext())
                       {
                           al.add(index, itTemp.next());
                           index++;
                       }
                   }
                   itArtifacts = al.listIterator();
               }
            }
            if(index ==0){
                itArtifacts = selectedProcess.listProcessClasses();
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.listArtifacts", e);
            System.out.println("Error en ControlPanel." +
                    "listArtifacts:" + e.getMessage());
		}
        return itArtifacts;
    }

    /**
	 * Obtiene las propiedades del artefacto (no de una instancia especifica)
	 *
     * @param           selectedProcess org.semanticwb.process.model.Process
	 * @return          Vector de objetos SemanticProperty
	 */
    public Vector getProcessArtifactDefinitionProperties(
            org.semanticwb.process.model.Process selectedProcess)
    {
        Vector vProperties = new Vector();
        int index = 0;
        try
        {
            Iterator itArtifacts = listArtifacts(selectedProcess);
            while(itArtifacts.hasNext())
            {
                SemanticObject sob = (SemanticObject) itArtifacts.next();
                String strArtifactName  = sob.getDisplayName();
                SemanticClass sec = sob.transformToSemanticClass();
                Iterator<SemanticProperty> itProps = sec.listProperties();
                while(itProps.hasNext())
                {
                    SemanticProperty property =
                            (SemanticProperty) itProps.next();
                    ProcessProperty pp =
                            new ProcessProperty(property,property.getURI(),
                                strArtifactName);
                    pp.setProcessURI(selectedProcess.getURI());
                    vProperties.add(index, pp);
                    index++;
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getProcessArtifactDefinitionProperties", e);
            System.out.println("Error en ControlPanel." +
                    "getProcessArtifactDefinitionProperties:" + e.getMessage());
		}
        return vProperties;
    }

    /**
	 * Obtiene el valor de una propiedad para un objeto FlowNodeInstance especifico
	 *
     * @param           fobi FlowNodeInstance
     * @param           propertyURI String
	 * @return          String
	 */
    public String getArtifactProperty(FlowNodeInstance fobi, String propertyURI)
    {
        //String strDisplayName = "";
        String strValue = "";
        try
        {
            SemanticProperty semprop =
                    SemanticObject.createSemanticObject(propertyURI).transformToSemanticProperty();
            //strDisplayName = semprop.getDisplayName();
            strValue = parseFlowNodeInstanceProperties(fobi, propertyURI);
        } catch(Exception e){
          //log.error("Error en ControlPanel.getArtifactProperty", e);
            System.out.println("Error en ControlPanel." +
                    "getArtifactProperty:" + e.getMessage());
		}
        return strValue;
    }

    //TAREAS
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
                    Vector aux = getTasks(paramRequest, (SubProcessInstance)flobInst);
                    vTaskLinks.addAll(aux);
                    intFobInstances = intFobInstances + aux.size();
                } else if(flobInstType instanceof Task)
                {
                    if(currentUser.haveAccess(flobInstType)){
                        vTaskLinks.add(intFobInstances,flobInst);
                        intFobInstances++;
                    }
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getTasks", e);
            System.out.println("Error en ControlPanel.getTasks:" +
                    e.getMessage());
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
            for(int index=0; index<vSelected.size(); index++)
            {
                org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) vSelected.get(index);
                //Iterator<ProcessInstance> itProcessInstances = BPMSProcessInstance.ClassMgr.listProcessInstancesByProcess(paramRequest, process.getURI()).iterator();
                //Iterator<ProcessInstance> itProcessInstances = SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
                Iterator<ProcessInstance> itProcessInstances = site.listProcessInstances();
                while(itProcessInstances.hasNext())
                {
                    ProcessInstance pinst = (ProcessInstance)itProcessInstances.next();
                    //TODO: Checar si esta activa pinst.getStatus() ????
                    Iterator<FlowNodeInstance> itFlowNodeInstances = pinst.listFlowNodeInstances();
                    //Iterator<FlowNodeInstance> itFlowNodeInstances = SWBProcessMgr.getUserTaskInstances(pinst, currentUser).iterator();

                    while(itFlowNodeInstances.hasNext())
                    {
                        FlowNodeInstance flobInst = itFlowNodeInstances.next();
                        FlowNode flobInstType = (FlowNode)flobInst.getFlowNodeType();
                        if(flobInst instanceof SubProcessInstance)
                        {
                            Vector aux = getTasks(paramRequest,(SubProcessInstance)flobInst);
                            vTaskLinks.addAll(aux);
                            intFobInstances = intFobInstances + aux.size();
                        } else if(flobInstType instanceof Task)
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
          //log.error("Error en ControlPanel.getTasks", e);
            System.out.println("Error en ControlPanel.getTasks:" +
                    e.getMessage());
		}
        return vTaskLinks;
    }

    /**
    * Verifica si un objeto TaskProperty existe en el vector y lo regresa
    *
    * @param            v Vector
    * @param            processURI String
    * @param            type String
    * @param            propName String
    * @return      		TaskProperty
    * @see
    */
    public TaskProperty findTaskProperty(Vector v, String processURI, String type, String propName)
    {
        TaskProperty tProp = new TaskProperty();
        try{
            for(int i=0; i<v.size(); i++)
            {
                TaskProperty tmpProp = (TaskProperty)v.get(i);
                if(tmpProp.getURI().equalsIgnoreCase(processURI)
                        && tmpProp.getType().equalsIgnoreCase(type)
                        && tmpProp.getName().equalsIgnoreCase(propName))
                {
                    tProp = tmpProp;
                    break;
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getTasks", e);
            System.out.println("Error en ControlPanel.findTaskProperty:" +
                    e.getMessage());
		}
        return tProp;
    }

    /**
    * Recibe un arreglo de objetos tipo String (URI de propiedad, tipo de
    * propiedad, nombre de la propiedad, lugar de aplicación de la propiedad,
    * orden) concatenados por pipes  y regresa un Vector de objetos TaskProperty
    *
    * @param            strArray String
    * @return      		Vector
    * @see
    */
    public Vector arrayToTaskProperties(String strArray)
    {
        Vector vTasks = new Vector();
        try
        {
            int index = 0;
            String[] arrTemp = strArray.split("\\|");
            for(int i=0; i<arrTemp.length-4; i=i+5)
            {
                String strProcURI = arrTemp[i];
                String strPropType = arrTemp[i+1];
                String strPropName = arrTemp[i+2];
                String strApplyOnCriteria = arrTemp[i+3];
                String strOrderValue = arrTemp[i+4]==null
                        ?"0"
                        :arrTemp[i+4];
                TaskProperty tProp = new TaskProperty(strPropName,strPropName,strPropType,strProcURI);
                boolean arrApplyOn[] = {false,false,false};
                String[] strApplyOnValue = strApplyOnCriteria.split(",");
                for(int j=0; j<strApplyOnValue.length; j++)
                {
                    if(strApplyOnValue[j].equalsIgnoreCase("true")){
                        arrApplyOn[j] = true;
                    }
                }
                tProp.setApplyOnTask(arrApplyOn);
                tProp.setOrderOnTask(Integer.parseInt(strOrderValue));
                vTasks.add(index, tProp);
                index++;
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.arrayToTaskProperties", e);
            System.out.println("Error en ControlPanel.arrayToTaskProperties:" +
                    e.getMessage());
		}
        return vTasks;
    }

    /**
    * Obtiene el valor de una propiedad para un FlowNodeInstance
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
                //Iterator<ProcessObject> objit = ppi.getAllProcessObjects().iterator();
                Iterator<ProcessObject> objit = ppi.listHeraquicalProcessObjects().iterator();
                while((objit.hasNext()) && (bFound==false))
                {
                    ProcessObject obj = objit.next();
                    SemanticObject sob =
                            SemanticObject.getSemanticObject(obj.getURI());
                    SemanticClass cls = sob.getSemanticClass();
                    Iterator<SemanticProperty> itProps = cls.listProperties();
                    while(itProps.hasNext())
                    {
                        SemanticProperty property =
                                (SemanticProperty) itProps.next();
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
          //log.error("Error en ControlPanel.parseProperties", e);
            System.out.println("Error en ControlPanel." +
                    "parseProperties:" + e.getMessage());
		}
        return strResult;
    }

    /**
    * Obtiene el nombre de despliegue de una propiedad general de la tarea.
    * Recibe un vector de propiedades generales de la tarea, el tipo de
    * propiedad de la tarea y su identificador.
    *
    * @param            v Vector
    * @param            type String
    * @param            id String
    * @return      		String
    */
    public String getTaskPropertyName(Vector v, String type, String id)
    {
        String strName = "";
        try{
            for(int i=0; i<v.size(); i++)
            {
                String[] tmp = (String[])v.get(i);
                if(tmp[0].equalsIgnoreCase(type) && tmp[2].equalsIgnoreCase(id))
                {
                    strName = tmp[1].toString();
                    break;
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getTaskPropertyName", e);
            System.out.println("Error en ControlPanel.getTaskPropertyName:" +
                    e.getMessage());
		}
        return strName;
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
          //log.error("Error en ControlPanel.getTaskDefinitionProperty", e);
            System.out.println("Error en ControlPanel." +
                    "getTaskDefinitionProperty:" + e.getMessage());
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
                    User usr = flobInst.getCreator();
                    strValue = usr.getFullName();
            } else if(strProperty.equalsIgnoreCase("modified"))
            {
                strValue = String.valueOf(flobInst.getUpdated())=="null" ?"" :String.valueOf(flobInst.getUpdated());
            } else if(strProperty.equalsIgnoreCase("modifiedBy"))
            {
                User usr = flobInst.getModifiedBy();
                strValue = usr.getFullName();

            } else if(strProperty.equalsIgnoreCase("ended"))
            {
                strValue = String.valueOf(flobInst.getEnded())=="null" ?"" :String.valueOf(flobInst.getEnded());
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
          //log.error("Error en ControlPanel.getTaskInstanceProperty", e);
            System.out.println("Error en ControlPanel.getTaskInstanceProperty:"
                    + e.getMessage());
		}
        return strValue;
    }

    /**
    * Recibe una cadena que contiene las propiedades seleccionadas de la tarea
    * (generales y de artefacto), obtiene sus valores (para un objeto FlowNodeInstance) y los concatena.
    * Usa la variable parseType, para determinar si se deben concatenar para
    * formar el vínculo, la leyenda o las columnas de la tarea
    *
    * @param            FlowNodeInstance
    * @param            strAttributes
    * @param            int parseType
    * @return      		String
    */
    public String parseTaskAttributes(FlowNodeInstance fobi,
            String strAttributes, int parseType, SWBParamRequest paramRequest)
    {
        String strParsed = "";
        try
        {
            if(strAttributes.contains("|"))
            {
                String[] strAtts =  strAttributes.split("\\|");
                int countFixedParameters = 0;
                for(int i=0; i<strAtts.length; i++)
                {
                    String strTemp = strAtts[i];
                    String strPropName = "";
                    String strPropValue = "";
                    UserTask usrTask = (UserTask)fobi.getFlowNodeType();
                    if(strTemp.contains("http://"))
                    {
                        strPropName = strTemp;
                        strPropValue = getArtifactProperty(fobi, strPropName);
                    } else if(strTemp.contains("TaskDefinition.")) {
                        String[] arrTemp = strTemp.split("\\.");
                        strPropName = arrTemp[1];
                        strPropValue = getTaskDefinitionProperty(usrTask, strPropName);
                    } else if(strTemp.contains("TaskInstance.")){
                        String[] arrTemp = strTemp.split("\\.");
                        strPropName = arrTemp[1];
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
                            strParsed = strParsed + strPropValue;
                            //strParsed = strParsed + strPropName + "=" + strPropValue;
                            break;
                        case 1: //legend
                            strParsed = strParsed + " " + strPropValue;
                                //strParsed = strParsed + strPropValue;
                            break;
                        case 2: //columns
                            strParsed = strParsed + "<li>" + strPropValue + "</li>";
                            break;
                    }
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.parseTaskAttributes", e);
            System.out.println("Error en ControlPanel.parseTaskAttributes:" +
                    e.getMessage());
		}
        return strParsed;
    }

    /**
    * Recibe una cadena que contiene las propiedades seleccionadas de la tarea
    * (generales y de artefacto), obtiene sus valores (para un objeto FlowNodeInstance)
    * y los agrega a un objeto Hashtable, que puede usarse para formar parte de un TaskLink.
    * Usa la variable parseType, para determinar si se deben concatenar para
    * formar el vínculo, la leyenda o las columnas de la tarea
    *
    * @param            fobi FlowNodeInstance
    * @param            strAttributes String
    * @return      		Hashtable
    */
    public Hashtable parseTaskAttributes(FlowNodeInstance fobi,
            String strAttributes, SWBParamRequest paramRequest)
    {
        Hashtable hash = new Hashtable();
        try
        {
            if(strAttributes.contains("|"))
            {
                String[] strAtts =  strAttributes.split("\\|");
                for(int i=0; i<strAtts.length; i++)
                {
                    String strTemp = strAtts[i];
                    String strPropName = "";
                    String strPropValue = "";
                    UserTask usrTask = (UserTask)fobi.getFlowNodeType();
                    if(strTemp.contains("http://"))
                    {
                        //SemanticProperty semprop = SemanticObject.createSemanticObject(strTemp).transformToSemanticProperty();
                        //strPropName = semprop.getDisplayName();
                        strPropName = strTemp;
                        strPropValue = parseFlowNodeInstanceProperties(fobi, strTemp);
                    } else if(strTemp.contains("TaskDefinition.")) {
                        String[] arrTemp = strTemp.split("\\.");
                        strPropName = arrTemp[0] + "." + arrTemp[1];
                        strPropValue = getTaskDefinitionProperty(usrTask, arrTemp[1]);
                    } else if(strTemp.contains("TaskInstance.")){
                        String[] arrTemp = strTemp.split("\\.");
                        strPropName = arrTemp[0] + "." + arrTemp[1];
                        strPropValue = getTaskInstanceProperty(fobi, arrTemp[1],paramRequest);
                    }
                    hash.put(strPropName, strPropValue);
                }

            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.parseTaskAttributes", e);
            System.out.println("Error en ControlPanel.parseTaskAttributes:" +
                    e.getMessage());
		}
        return hash;
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
            HashMap attMap = taskAttributes;
            for(int i=0; i<vTasks.size(); i++)
            {
                FlowNodeInstance fobi = (FlowNodeInstance)vTasks.get(i);
                //FlowNode fobiType = (FlowNode)fobi.getFlowNodeType();
                ProcessInstance fpinst = fobi.getProcessInstance();
                if(null!=fpinst){
                    org.semanticwb.process.model.Process fproc = (org.semanticwb.process.model.Process)fpinst.getProcessType();
                    if(null!=fproc){
                        String strParentUri = fproc.getURI();
                        //String strParentUri = fobiType.getParent().getURI();
                        if(attMap.containsKey(strParentUri))
                        {
                            ArrayList arrAtt = (ArrayList) attMap.get(strParentUri);
                            if(arrAtt.size()==3)
                            {
                                String strTempHref = strDefaultHref + arrAtt.get(0).toString();
                                String strTempLegend = arrAtt.get(1).toString();
                                String strTempColumns = arrAtt.get(2).toString();
                                String strHref = parseTaskAttributes(fobi, strTempHref, 0, paramRequest);
                                String strLegend = parseTaskAttributes(fobi, strTempLegend, 1, paramRequest);
                                Hashtable htColumns = parseTaskAttributes(fobi, strTempColumns, paramRequest);
                                TaskLink tlink = new TaskLink(fobi, strHref, strLegend, htColumns);
                                vTaskLinks.add(intTasks,tlink);
                                intTasks++;
                            }
                        }
                    }
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.setTaskLinks", e);
            System.out.println("Error en ControlPanel.setTaskLinks:" +
                    e.getMessage());
		}
        return vTaskLinks;
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
        try
        {
            Resource base = paramRequest.getResourceBase();
            int intRowsPerPage =
                    base.getAttribute("cpRowsPerPage")==null
                    ?intDefaultRowsPerPage
                    :Integer.parseInt(base.getAttribute("cpRowsPerPage"));
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
                //Obtener el vector con las propiedades generales (taskDefinition y taskInstance)
                Vector vTaskProps  = getTaskProperties(paramRequest);
                Vector vTaskLinks = setTaskLinks(vFilteredTasks, paramRequest);
                intSortType = getSortCriteria(paramRequest);
                BPMSTask.ClassMgr.sortTasks(vTaskLinks,intSortType);
                int endRow = getPageLastRow(vTaskLinks.size(),intRowsPerPage, intCurrPage);
                int iniRow = getPageFirstRow(vTaskLinks.size(),intRowsPerPage,intCurrPage);
                sb.append("<div id=\"tareas\">");
                sb.append("<p class=\"izq\">" + vTaskLinks.size() + " " + paramRequest.getLocaleString("lblTotalTask") + "</p>");
                //sb.append("<p class=\"der cerradas-si\"><a href=\"#\" onclick=\"MM_showHideLayers('filtrado','','hide','informe','','hide','personalizar','','show');selectedTab('personalizar');\">Personalizar</a></p></div>");
                sb.append("<p class=\"der cerradas-si\"></p></div>");
                //TODO: Tareas cerradas (filtro)
                StringBuffer sbPagination = getPagination(intRowsPerPage, intCurrPage,vTaskLinks.size(),paramRequest,request);
                sb.append("<p class=\"paginado\">" + sbPagination + "</p>");
                //sb.append("<p>" + paramRequest.getLocaleString("lblTotalTask") + "</p>");
                sb.append("<table ><thead><tr>");
                java.util.List<String> colNames = new ArrayList();
                for(int i=iniRow; i<endRow; i++)
                {
                    TaskLink tlink = (TaskLink) vTaskLinks.get(i);
                    Hashtable hash = tlink.getTaskLinkArtifactValues();

                    if(!tlink.getFlowNodeParentProcessURI().equalsIgnoreCase(strTop))
                    {
                        String tmpUri = tlink.getFlowNodeParentProcessURI();
                        String parentName = tlink.getFlowNodeParentProcess();
                        ArrayList arrAttributes = (ArrayList)taskAttributes.get(tmpUri);
                        String strColumnsNames = String.valueOf(arrAttributes.get(2));
                        String[] colNamesArr = strColumnsNames.split("\\|");
                        java.util.List<String> listColNames = new ArrayList<String>(colNamesArr.length);
                        for (int k=0; k<colNamesArr.length; k++) {
                            String s = colNamesArr[k];
                            listColNames.add(s);
                        }
                        colNames = listColNames;
                        //sb.append("<tr><td colspan=\"" + intColumnCount + "\">" + parentName + "</td></tr>");
                        sb.append("<th class=\"orden-no\"><a href=\"#\">" + paramRequest.getLocaleString("lblTaskTitle") + "</a></th>");
                        for(int j=0; j<colNamesArr.length; j++){
                            String tmpName = colNamesArr[j];
                            if(hash.containsKey(tmpName)){
                                if(tmpName.contains("TaskDefinition.") || tmpName.contains("TaskInstance."))
                                {
                                    String[] tmpArr = tmpName.split("\\.");
                                    tmpName = getTaskPropertyName(vTaskProps, tmpArr[0], tmpArr[1]);
                                } else if(tmpName.contains("http://")) {
                                    tmpName = BPMSProcessInstance.ClassMgr.getPropertyName(tmpName);
                                }
                            }
                            sb.append("<th class=\"orden-no\"><a href=\"#\">" + tmpName +  "</a></th>");
                        }
                        sb.append("</tr></thead><tbody>");
                        strTop = tmpUri;
                    }
                    sb.append("<tr><td>");
                    sb.append("<a href=\"");
                    sb.append(tlink.getTaskLinkHref());
                    sb.append("\" >");
                    sb.append(tlink.getTaskLinkLegend());
                    sb.append("</a></td>");
                    for(int j=0; j<colNames.size(); j++){
                        String tmpName = colNames.get(j);
                        String value = hash.get(tmpName)==null ?"" :hash.get(tmpName).toString();
                        sb.append("<td>" + value + "</td>");
                    }
                    sb.append("</tr>");
                }
                sb.append("</tbody></table>");
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.printTaskLinks", e);
            System.out.println("Error en ControlPanel.printTaskLinks:" +
                    e.getMessage());
		}
        return sb;
    }


    //ORDENAMIENTO
    /**
    * Obtiene el criterio de ordenamiento seleccionado (durante
    * customizeDisplay) de la base del recurso
    *
    * @param            paramRequest SWBParamRequest
    * @return      		int
    * @see
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
            if(null!=strSortingCtrl &&
                    !strSortingCtrl.equalsIgnoreCase("")){
                intCriteria = Integer.parseInt(strSortingCtrl);
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getSortCriteria", e);
            System.out.println("Error en ControlPanel.getSortCriteria:" +
                    e.getMessage());
		}
        return intCriteria;
    }

    /**
    * Verifica si la bandeja tiene activado el filtro para no mostrar tareas
    * cerradas del usuario actual
    *
    * @param            paramRequest SWBParamRequest
    * @return      		boolean
    * @see
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
            if(htable.containsKey("hideClosedTasks")){
                strActive = htable.get("hideClosedTasks").toString();
            }
            if(strActive.equalsIgnoreCase("1")){
                isActive = true;
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.isClosedStatusFilterActive", e);
            System.out.println("Error en ControlPanel." +
                    "isClosedStatusFilterActive:" + e.getMessage());
		}
        return isActive;
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
            sbPrint.append("<div id=\"personaliza\"><div class=\"pleca\"><h3>" +
                    paramRequest.getLocaleString("lblCustomizeTitle") +
                    "</h3>");
            sbPrint.append("<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("updateCustomization")
                    + "\" method=\"POST\">");
	        sbPrint.append("<label for=\"" + SORTING_CTRL +"\" >" + paramRequest.getLocaleString("lblSort") + ":</label>");
            sbPrint.append(" <select name=\""+SORTING_CTRL+ ""
                    + "\" id=\""+SORTING_CTRL+"\">");
            for(int i=0; i<BPMSTask.ClassMgr.MAX_SORT; i++){
                sbPrint.append("<option ");
                if(intSortType==i){ sbPrint.append(" selected ");}
                sbPrint.append("value=\"" + i + "\">" +
                        paramRequest.getLocaleString("cpSortType"+i)
                        +"</option>");
            }
            sbPrint.append("</select><br/><br/>");
            if(bClosedFilter)
            {
                //sbPrint.append("<label for=\"hideClosedTasks\" >" + paramRequest.getLocaleString("lblClosedFilter") + "</label> <input type=\"RADIO\" id=\"hideClosedTasks\""  + "name=\"hideClosedTasks\" " + " value=\"0\">" + paramRequest.getLocaleString("cpClosedFilter0") + "<br/>" + "<input type=\"RADIO\" id=\"hideClosedTasks\"" + "name=\"hideClosedTasks\" " + " checked value=\"1\">" + paramRequest.getLocaleString("cpClosedFilter1") + "<br/><br/>");
                sbPrint.append("<div class=\"radios\"><p class=\"cerradas\">" + paramRequest.getLocaleString("lblClosedFilter") + ":</p></div>");
                sbPrint.append("<div class=\"radios\"><input type=\"RADIO\" id=\"hideClosedTasks\""  + "name=\"hideClosedTasks\" " + " value=\"0\" class=\"radio\">");
                sbPrint.append("<label for=\"hideClosedTasks\">" + paramRequest.getLocaleString("cpClosedFilter0") + "</label></div>");
                sbPrint.append("<div class=\"radios\"><input type=\"RADIO\" id=\"hideClosedTasks\""  + "name=\"hideClosedTasks\" " + " value=\"1\" checked class=\"radio\">");
                sbPrint.append("<label for=\"hideClosedTasks\">" + paramRequest.getLocaleString("cpClosedFilter1") + "</label></div>");
            } else {
                //sbPrint.append("<label for=\"hideClosedTasks\" >" + paramRequest.getLocaleString("lblClosedFilter") + "</label> <input type=\"RADIO\" id=\"hideClosedTasks\"" + "name=\"hideClosedTasks\" " + "  checked value=\"0\" >" + paramRequest.getLocaleString("cpClosedFilter0") + "<br/>" + "<input type=\"RADIO\" id=\"hideClosedTasks\"" + "name=\"hideClosedTasks\" " + "  value=\"1\">" + paramRequest.getLocaleString("cpClosedFilter1") + "<br/><br/>");
                sbPrint.append("<div class=\"radios\"><p class=\"cerradas\">" + paramRequest.getLocaleString("lblClosedFilter") + ":</p></div>");
                sbPrint.append("<div class=\"radios\"><input type=\"RADIO\" id=\"hideClosedTasks\""  + "name=\"hideClosedTasks\" " + " value=\"0\" checked class=\"radio\">");
                sbPrint.append("<label for=\"hideClosedTasks\">" + paramRequest.getLocaleString("cpClosedFilter0") + "</label></div>");
                sbPrint.append("<div class=\"radios\"><input type=\"RADIO\" id=\"hideClosedTasks\""  + "name=\"hideClosedTasks\" " + " value=\"1\" class=\"radio\">");
                sbPrint.append("<label for=\"hideClosedTasks\">" + paramRequest.getLocaleString("cpClosedFilter1") + "</label></div>");

            }
            sbPrint.append("<br/><br/>");
            sbPrint.append("<input type=\"SUBMIT\" name=\"btnSave\" class=\"pleca_boton\" value=\"" +
                    paramRequest.getLocaleString("btnSaveCustomize") + "\"/>");
            sbPrint.append("<input type=\"RESET\" name=\"btnReset\" class=\"pleca_boton\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") +"\"/>");
            sbPrint.append("<input type=\"SUBMIT\" name=\"btnBack2View\" class=\"pleca_boton\" " +
                    " onclick=\"direccionaP('" + url.setAction("goToView") + "');\"" +
                    " value=\"" + paramRequest.getLocaleString("btnBack") + "\"/>");
            sbPrint.append("</form>");
            sbPrint.append("</div></div>");
        } catch(Exception e){
            //log.error("Error en ControlPanel.customizeDisplay", e);
            System.out.println("Error en ControlPanel.customizeDisplay:" +
            e.getMessage());
        }
        return sbPrint;
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
                if(strName.startsWith(SORTING_CTRL)
                        || strName.startsWith("hideClosedTasks"))
                {
                    String strParameter = request.getParameter(strName);
                    strData = strData + strName + "|" + strParameter + "|";
                }
            }
            if(strData!=null && !strData.equalsIgnoreCase(""))
            {
                base.setData(currentUser, strData);
                msg = (new StringBuilder()).append(
                        paramsRequest.getLocaleString(
                        "msgOkUpdateResource")).append(" "
                        + base.getId()).toString();
                sbPrint.append((new StringBuilder()).append(
                        "\n<script language=\"JavaScript\">  \nalert('").append(
                        msg).append("');").append("\n</script>").toString());
                sbPrint.append((new StringBuilder()).append("\n<script " +
                        "language=\"JavaScript\">  \nlocation='").append(
                        paramsRequest.getRenderUrl().setAction(
                        "admin").toString()).append("';").append(
                        "\n</script>").toString());
                errorMsg = (new StringBuilder()).append(
                        paramsRequest.getLocaleString(
                        "msgErrUpdateResource")).append(" "
                        + base.getId()).toString();
            }
        } catch(Exception e){
            sbPrint.append((new StringBuilder()).append(
                    "\n<script language=\"JavaScript\">  \nalert('").append(
                    errorMsg).append("');").append("\n</script>").toString());
            sbPrint.append((new StringBuilder()).append("\n<script " +
                    "language=\"JavaScript\">  \nlocation='").append(
                    paramsRequest.getRenderUrl().setAction(
                    "admin").toString()).append("';").append(
                    "\n</script>").toString());
          //log.error("Error en ControlPanel.setCustomizedData", e);
            System.out.println("Error en ControlPanel.setCustomizedData:"
                    + e.getMessage());
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
            if(customizationData!=null &&
                    !customizationData.equalsIgnoreCase(""))
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
          //log.error("Error en ControlPanel.getCustomizedData", e);
            System.out.println("Error en ControlPanel.getCustomizedData:"
                    + e.getMessage());
		}
        return htable;
    }

    //INFORME PERSONALIZADO
    /**
    * Genera un vector filtrado con la informacion necesaria para generar
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
            String strReportProcess =
                request.getParameter(PROCESS_REPORT_CTRL)==null
                ?"0"
                :request.getParameter(PROCESS_REPORT_CTRL);
            String strInitialReportDate =
                request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(
                    request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate =
                request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(
                    request.getParameter(END_DATE_REPORT_CTRL));
            String strReportStatus =
                request.getParameter(STATUS_REPORT_CTRL)==null
                ?"-1"
                :request.getParameter(STATUS_REPORT_CTRL);
            vAllTasks = BPMSTask.ClassMgr.getAllUserTasks(paramsRequest);
            if(!strReportProcess.equalsIgnoreCase("0"))
            {
                vAllTasks =
                        BPMSTask.ClassMgr.filterTasksByProcess(vAllTasks,
                            strReportProcess);
            }
            if(!strInitialReportDate.equalsIgnoreCase(""))
            {
                String strFilterValue = strInitialReportDate + "|";
                if(!strEndReportDate.equalsIgnoreCase(""))
                {
                    strFilterValue = strFilterValue + strEndReportDate + "|";
                }
                vAllTasks = BPMSTask.ClassMgr.filterTasksByDate(vAllTasks,
                        strFilterValue);
            }
            if(!strReportStatus.equalsIgnoreCase("-1"))
            {
                vAllTasks = BPMSTask.ClassMgr.filterTasksByStatus(
                        vAllTasks,strReportStatus);
            }
            vAllTasks = BPMSTask.ClassMgr.flowNodeInstanceToTaskLink(vAllTasks, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks, BPMSTask.ClassMgr.SORT_BY_DATE);
        } catch(Exception e){
          //log.error("Error en ControlPanel.filterReport", e);
            System.out.println("Error en ControlPanel.filterReport:"
                    + e.getMessage());
		}
        return vAllTasks;
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
    public StringBuffer printReportTask(FlowNodeInstance fobi,
            SWBParamRequest paramsRequest)
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
            String strStatus  =
                    getStatusDescription(paramsRequest, intStatus);
            String strPriority  =
                    getPriorityDescription(paramsRequest, intPriority);
            FlowNode fobType = fobi.getFlowNodeType();
            if(fobType!=null){
                strTaskTitle = fobType.getTitle()==null
                    ?""
                    :fobType.getTitle();
            }
            if(fobi.getCreated()!=null)
            {
                //Date dtCreated = fobi.getCreated();
                strDateCreated = String.valueOf(fobi.getCreated());
                strCreator = fobi.getCreator().getFullName()==null
                    ?""
                    :fobi.getCreator().getFullName();
            }
            if(fobi.getUpdated()!=null)
            {
                //Date dtModified = fobi.getUpdated();
                strDateModified = String.valueOf(fobi.getUpdated());
                strModifiedBy = fobi.getModifiedBy().getFullName()==null
                    ?""
                    :fobi.getModifiedBy().getFullName();
            }
            if(fobi.getEnded()!=null)
            {
                //Date dtEnded = fobi.getEnded();
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
          //log.error("Error en ControlPanel.printReportTask", e);
            System.out.println("Error en ControlPanel.printReportTask:"
                    + e.getMessage());
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
    public org.w3c.dom.Document createReportDocument(Vector vTasks, SWBParamRequest paramsRequest)
    {
        org.w3c.dom.Document doc = null;
        try
        {
            DocumentBuilderFactory dbfac =
                SWBUtils.XML.getDocumentBuilderFactory().newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            doc = docBuilder.newDocument();
            org.w3c.dom.Element root = doc.createElement("Report");
            doc.appendChild(root);
            org.w3c.dom.Element title = doc.createElement("title");
            title.setAttribute("titleName", "Control Panel Activity Report");
            root.appendChild(title);
            org.w3c.dom.Element pageHeader = doc.createElement("pageHeader");
            User currentUser = paramsRequest.getUser();
            String strUser = paramsRequest.getLocaleString("lblCrReportUser") +
                    " " + currentUser.getFullName();
            pageHeader.setAttribute("pageHeaderName", strUser);
            root.appendChild(pageHeader);
            for(int i=0; i<vTasks.size(); i++)
            {
                TaskLink tlink = (TaskLink) vTasks.get(i);
                org.w3c.dom.Element row = doc.createElement("row");
                row.setAttribute("rowId",String.valueOf(i+1));
                root.appendChild(row);
                String strProcessName =
                    tlink.getFlowNodeParentProcess()==null
                    ?""
                    :tlink.getFlowNodeParentProcess();
                org.w3c.dom.Element processName = doc.createElement("processName");
                row.appendChild(processName);
                org.w3c.dom.Text processNameText =
                        doc.createTextNode(strProcessName);
                processName.appendChild(processNameText);
                String strTaskId =
                    tlink.getFlowNodeInstanceId()==null
                    ?""
                    :tlink.getFlowNodeInstanceId();
                org.w3c.dom.Element taskId = doc.createElement("taskId");
                row.appendChild(taskId);
                org.w3c.dom.Text taskIdText = doc.createTextNode(strTaskId);
                taskId.appendChild(taskIdText);
                String strTaskTitle =
                    tlink.getFlowNodeTitle()==null
                    ?""
                    :tlink.getFlowNodeTitle();
                org.w3c.dom.Element taskTitle = doc.createElement("taskTitle");
                row.appendChild(taskTitle);
                org.w3c.dom.Text taskTitleText =
                        doc.createTextNode(strTaskTitle);
                taskTitle.appendChild(taskTitleText);
                String strTaskCreated =
                    String.valueOf(tlink.getFlowNodeInstanceCreated())==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceCreated());
                org.w3c.dom.Element taskCreated = doc.createElement("taskCreated");
                row.appendChild(taskCreated);
                org.w3c.dom.Text taskCreatedText =
                        doc.createTextNode(strTaskCreated);
                taskCreated.appendChild(taskCreatedText);
                String strTaskCreatedBy =
                    tlink.getFlowNodeInstanceCreatedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceCreatedByName();
                org.w3c.dom.Element taskCreatedBy = doc.createElement("taskCreatedBy");
                row.appendChild(taskCreatedBy);
                org.w3c.dom.Text taskCreatedByText =
                        doc.createTextNode(strTaskCreatedBy);
                taskCreatedBy.appendChild(taskCreatedByText);
                int intTaskStatus = tlink.getFlowNodeInstanceStatus();
                String strTaskStatus =
                        getStatusDescription(paramsRequest,intTaskStatus);
                org.w3c.dom.Element taskStatus = doc.createElement("taskStatus");
                row.appendChild(taskStatus);
                org.w3c.dom.Text taskStatusText =
                        doc.createTextNode(strTaskStatus);
                taskStatus.appendChild(taskStatusText);
                String strTaskUpdated =
                    tlink.getFlowNodeInstanceModified()==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceModified());
                org.w3c.dom.Element taskUpdated = doc.createElement("taskUpdated");
                row.appendChild(taskUpdated);
                org.w3c.dom.Text taskUpdatedText =
                        doc.createTextNode(strTaskUpdated);
                taskUpdated.appendChild(taskUpdatedText);
                String strTaskUpdatedBy =
                    tlink.getFlowNodeInstanceModifiedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceModifiedByName();
                org.w3c.dom.Element taskUpdatedBy = doc.createElement("taskUpdatedBy");
                row.appendChild(taskUpdatedBy);
                org.w3c.dom.Text taskUpdatedByText =
                        doc.createTextNode(strTaskUpdatedBy);
                taskUpdatedBy.appendChild(taskUpdatedByText);
                String strTaskEnded = "";
                String strTaskEndedBy = "";
                if(tlink.getFlowNodeInstanceEnded()!=null)
                {
                    strTaskEnded =
                            String.valueOf(tlink.getFlowNodeInstanceEnded());
                    strTaskEndedBy =
                        tlink.getFlowNodeInstanceEndedByName()==null
                        ?""
                        :tlink.getFlowNodeInstanceEndedByName();
                }
                org.w3c.dom.Element taskEnded = doc.createElement("taskEnded");
                row.appendChild(taskEnded);
                org.w3c.dom.Text taskEndedText =
                        doc.createTextNode(strTaskEnded);
                taskEnded.appendChild(taskEndedText);
                org.w3c.dom.Element taskEndedBy = doc.createElement("taskEndedBy");
                row.appendChild(taskEndedBy);
                org.w3c.dom.Text taskEndedByText =
                        doc.createTextNode(strTaskEndedBy);
                taskEndedBy.appendChild(taskEndedByText);

                int intTaskPriority = tlink.getPriority();
                //TODO: getPriority Description
                String strTaskPriority =
                        String.valueOf(intTaskPriority);
                org.w3c.dom.Element taskPriority = doc.createElement("taskPriority");
                row.appendChild(taskPriority);
                org.w3c.dom.Text taskPriorityText =
                        doc.createTextNode(strTaskPriority);
                taskPriority.appendChild(taskPriorityText);
            }
            org.w3c.dom.Element pageFooter = doc.createElement("pageFooter");
            pageFooter.setAttribute("pageFooterName", "pageFooterValue");
            root.appendChild(pageFooter);
        } catch (Exception e) {
            //log.error("Error en ControlPanel.createReport", e);
            System.out.println("error en ControlPanel.createReport: " +
                    e.getMessage());
        }
        return doc;
    }

    /**
    * Genera un reporte de tareas en formato XML y regresa un StringBuffer con
    * el vínculo´para descargarlo.
    *
    * @param            request HttpServletRequest
    * @param            paramsRequest SWBParamRequest
    * @return      		StringBuffer
    */
    public StringBuffer createXmlReport(HttpServletRequest request,
            SWBParamRequest paramsRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {

            sb.append("<a href=\"" + strDownloadXml + "\" class=\"xml\">" +
                    paramsRequest.getLocaleString("lblXmlReport") + "</a>");
            Vector vAllTasks = filterReport(request, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks,
                        BPMSTask.ClassMgr.SORT_BY_DATE);
            FileInputStream fis = new FileInputStream(filenameXml);
            InputStreamReader isr = new InputStreamReader(fis);
            LineNumberReader lnr = new LineNumberReader(isr);
            org.w3c.dom.Document doc = createReportDocument(vAllTasks, paramsRequest);
            TransformerFactory transFac =
                    SWBUtils.XML.getTransformerFactory().newInstance();
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
            //log.error("Error en ControlPanel.createXmlReport", e);
            System.out.println("error en ControlPanel.createXmlReport: " +
                    e.getMessage());
        }
        return sb;
    }

    /**
    * Crear un objeto PdfPTable con el contenido de un Vector de objetos de tipo
    * TaskLink. El objeto resultante forma el cuerpo del reporte PDF.
    *
    * @param            vTasks Vector de objetos tipo TaskLink
    * @param            paramsRequest SWBParamRequest
    * @return      		PdfPTable (libreria iText)
    */
    public PdfPTable createPdfTable(Vector vTasks,
            SWBParamRequest paramsRequest)
    {
        PdfPTable table = new PdfPTable(reportColumnNumber);
        try
        {
            for(int i=0; i<vTasks.size(); i ++)
            {
                TaskLink tlink = (TaskLink) vTasks.get(i);
                String strProcessName =
                    tlink.getFlowNodeParentProcess()==null
                    ?""
                    :tlink.getFlowNodeParentProcess();
                String strTaskId =
                    tlink.getFlowNodeInstanceId()==null
                    ?""
                    :tlink.getFlowNodeInstanceId();
                String strTaskTitle =
                    tlink.getFlowNodeTitle()==null
                    ?""
                    :tlink.getFlowNodeTitle();
                String strTaskCreated =
                    String.valueOf(tlink.getFlowNodeInstanceCreated())==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceCreated());
                String strTaskCreatedBy =
                    tlink.getFlowNodeInstanceCreatedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceCreatedByName();
                int intStatus = tlink.getFlowNodeInstanceStatus();
                String strTaskStatus =
                    getStatusDescription(paramsRequest, intStatus)==null
                    ?""
                    :getStatusDescription(paramsRequest, intStatus);
                String strTaskUpdated =
                    tlink.getFlowNodeInstanceModified()==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceModified());
                String strTaskUpdatedBy =
                    tlink.getFlowNodeInstanceModifiedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceModifiedByName();
                String strTaskEnded = "";
                String strTaskEndedBy = "";
                if(tlink.getFlowNodeInstanceEnded()!=null)
                {
                    strTaskEnded =
                            String.valueOf(tlink.getFlowNodeInstanceEnded());
                    strTaskEndedBy =
                        tlink.getFlowNodeInstanceEndedByName()==null
                        ?""
                        :tlink.getFlowNodeInstanceEndedByName();
                }
                int intPriority = tlink.getPriority();
                String strPriority = getPriorityDescription(paramsRequest, intPriority);

                PdfPCell infoCell =
                        new PdfPCell(new Phrase(strProcessName,pdfCellFont));
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
            //log.error("Error en ControlPanel.createPdfTable", e);
            System.out.println("error en ControlPanel.createPdfTable: " +
                    e.getMessage());
        }
        return table;
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
    public PdfPTable createPdfTableHeader(String title, String subtitle,
            String[] columnNames)
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
                    headerColumn = new PdfPCell(new Phrase(columnNames[i],
                            pdfColumnTitleFont));
                    headerColumn.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    headerColumn.setPadding(reportHeaderPadding);
                    headerColumn.setBackgroundColor (pdfHeaderColumnBackground);
                    table.addCell(headerColumn);
                }
            }
        } catch (Exception e) {
            //log.error("Error en ControlPanel.createPdfTableHeader", e);
            System.out.println("error en ControlPanel.createPdfTableHeader: " +
                    e.getMessage());
        }
        return table;
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
            sb.append("<a href=\"" + strDownloadPdf + "\" class=\"pdf\" >" +
                    paramsRequest.getLocaleString("lblPdfReport") + "</a>");
            User currentUser = paramsRequest.getUser();
            String strInitialReportDate =
                request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate =
                request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(END_DATE_REPORT_CTRL));
            String strAuthor =
                currentUser.getFullName()==null
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
                strReportDates = strReportDates + " " +
                        paramsRequest.getLocaleString("lblCrReportEnd") + " "
                        + strEndReportDate;
            }
            Vector vAllTasks = filterReport(request, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks,
                        BPMSTask.ClassMgr.SORT_BY_DATE);
            com.lowagie.text.Document document =
                    new com.lowagie.text.Document(PageSize.LETTER);
            //Set file metadata
            document.addAuthor(strAuthor);
            document.addCreationDate();
            document.addCreator("SWB Control Panel");
            document.addTitle(
                    paramsRequest.getLocaleString("lblCrReportHeaderTitle"));
            //document.addKeywords("Java, PDF, iText");
            //document.addSubject("Control Panel Task Report");
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filenamePdf));
            //FOOTER
            HeaderFooter footer = new HeaderFooter(
                        new Phrase("Pg. " + pdfWriter.getPageNumber(),
                            pdfFooterFont), true);
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
            PdfPTable headerTable = createPdfTableHeader(strReportTitle,
                    strReportSubTitle, strTableColumnNames);
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
            //log.error("Error en ControlPanel.createPdfReport", e);
          System.err.println("error en ControlPanel.createPdfReport: " +
                  de.getMessage());
        } catch (IOException ioe) {
            //log.error("Error en ControlPanel.createPdfReport", e);
          System.err.println("error en ControlPanel.createPdfReport: " +
                  ioe.getMessage());
        } catch (Exception e) {
            //log.error("Error en ControlPanel.createPdfReport", e);
            System.out.println("error en ControlPanel.createPdfReport: " +
                    e.getMessage());
        }
        return sb;
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
    public Table createRtfTable(Vector vTasks,
            SWBParamRequest paramsRequest, int[] columnWidths)
    {
        Table  table = null;
        try{
            table = new Table(reportColumnNumber);
            table.setWidth(100);
            table.setWidths(columnWidths);
            for(int i=0; i<vTasks.size(); i ++)
            {
                TaskLink tlink = (TaskLink) vTasks.get(i);
                String strProcessName =
                    tlink.getFlowNodeParentProcess()==null
                    ?""
                    :tlink.getFlowNodeParentProcess();
                String strTaskId =
                    tlink.getFlowNodeInstanceId()==null
                    ?""
                    :tlink.getFlowNodeInstanceId();
                String strTaskTitle =
                    tlink.getFlowNodeTitle()==null
                    ?""
                    :tlink.getFlowNodeTitle();
                String strTaskCreated =
                    String.valueOf(tlink.getFlowNodeInstanceCreated())==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceCreated());
                String strTaskCreatedBy =
                    tlink.getFlowNodeInstanceCreatedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceCreatedByName();
                int intStatus = tlink.getFlowNodeInstanceStatus();
                String strTaskStatus =
                    getStatusDescription(paramsRequest, intStatus)==null
                    ?""
                    :getStatusDescription(paramsRequest, intStatus);
                int intPriority = tlink.getPriority();
                String strTaskPriority = getPriorityDescription(paramsRequest, intPriority);
                //TODO: get description priority
                String strTaskUpdated =
                    tlink.getFlowNodeInstanceModified()==null
                    ?""
                    :String.valueOf(tlink.getFlowNodeInstanceModified());
                String strTaskUpdatedBy =
                    tlink.getFlowNodeInstanceModifiedByName()==null
                    ?""
                    :tlink.getFlowNodeInstanceModifiedByName();
                String strTaskEnded = "";
                String strTaskEndedBy = "";
                if(tlink.getFlowNodeInstanceEnded()!=null)
                {
                    strTaskEnded =
                            String.valueOf(tlink.getFlowNodeInstanceEnded());
                    strTaskEndedBy =
                        tlink.getFlowNodeInstanceEndedByName()==null
                        ?""
                        :tlink.getFlowNodeInstanceEndedByName();
                }

                Cell infoCell =
                        new Cell(new Phrase(strProcessName,rtfCellFont));
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
            //log.error("Error en ControlPanel.createRtfTable", e);
            System.out.println("error en ControlPanel.createRtfTable: " +
                    be.getMessage());
        } catch (Exception e) {
            //log.error("Error en ControlPanel.createRtfTable", e);
            System.out.println("error en ControlPanel.createRtfTable: " +
                    e.getMessage());
        }
        return table;
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
            //log.error("Error en ControlPanel.createRtfTableHeader", e);
            System.out.println("error en ControlPanel.createRtfTableHeader: " +
                    be.getMessage());
        } catch (Exception e) {
            //log.error("Error en ControlPanel.createRtfTableHeader", e);
            System.out.println("error en ControlPanel.createRtfTableHeader: " +
                    e.getMessage());
        }
        return table;
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
            sb.append("<a href=\"" + strDownloadRtf + "\" class=\"rtf\">" +
                    paramsRequest.getLocaleString("lblRtfReport") + "</a>");
            User currentUser = paramsRequest.getUser();
            String strInitialReportDate =
                request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate =
                request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(request.getParameter(END_DATE_REPORT_CTRL));
            String strAuthor =
                currentUser.getFullName()==null
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
            String strReportDates =
                    paramsRequest.getLocaleString("lblCrReportStart") + " " +
                        strInitialReportDate;
            if(!strEndReportDate.equalsIgnoreCase(""))
            {
                strReportDates = strReportDates + " " +
                        paramsRequest.getLocaleString("lblCrReportEnd") + " "
                        + strEndReportDate;
            }
            Vector vAllTasks = filterReport(request, paramsRequest);
            BPMSTask.ClassMgr.sortTasks(vAllTasks,
                        BPMSTask.ClassMgr.SORT_BY_DATE);
            com.lowagie.text.Document document =
                    new com.lowagie.text.Document(PageSize.LETTER);
            //Set file metadata
            document.addAuthor(strAuthor);
            document.addCreationDate();
            document.addCreator("SWB Control Panel");
            document.addTitle(
                    paramsRequest.getLocaleString("lblCrReportHeaderTitle"));
            //document.addKeywords("Java, RTF, iText");
            //document.addSubject("Control Panel Task Report");
            RtfWriter2 rtf = RtfWriter2.getInstance(document,
                        new FileOutputStream(filenameRtf));
            //FOOTER: En el caso del RTF no es posible saber con anticipacion el
            //numero de pagina.
            //Ver http://www.mail-archive.com/itext-questions@lists.sourceforge.net/msg33833.html
            //HEADER
            String strReportTitle =
                    paramsRequest.getLocaleString("lblCrReportHeaderTitle");
            String strReportSubTitle =
                    paramsRequest.getLocaleString("lblCrReportUser") + " " +
                    strAuthor + "\t\t\t\t" + strReportDates;
            Phrase pHeader = new Phrase();
            //Incluir encabezado de las columnas en el Header para que se repita
            //en cada página del reporte
            Table headerTable = createRtfTableHeader(strReportTitle,
                    strReportSubTitle, strTableColumnNames, columnWidths);
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
            //log.error("Error en ControlPanel.createRtfReport", e);
            System.err.println("error en ControlPanel.createRtfReport: " +
                    ioe.getMessage());
        } catch (Exception e) {
            //log.error("Error en ControlPanel.createRtfReport", e);
            System.out.println("error en ControlPanel.createRtfReport: " +
                    e.getMessage());
        }
        return sb;
    }

    /**
    * Genera el codigo del reporte de tareas que se despliega en Control Panel
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
            String strInitialReportDate =
                request.getParameter(INITIAL_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(
                    request.getParameter(INITIAL_DATE_REPORT_CTRL));
            String strEndReportDate =
                request.getParameter(END_DATE_REPORT_CTRL)==null
                ?""
                :BPMSTask.ClassMgr.changeDateFormat(
                    request.getParameter(END_DATE_REPORT_CTRL));
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direcciona(action){");
            sb.append("\n      document.forms['frmReport'].action = action;");
            sb.append("\n      document.forms['frmReport'].submit();");
            sb.append("\n   }");
            sb.append("\n</script>");
            sb.append("<div id=\"tareas\">");
            sb.append("<p class=\"izq\">" + paramsRequest.getLocaleString("lblCrReportTitle") + " " +
                    currentUser.getFullName() + " " +
                    paramsRequest.getLocaleString("lblCrReportStart") + " " +
                    strInitialReportDate + " " +
                    paramsRequest.getLocaleString("lblCrReportEnd") + " " +
                    strEndReportDate +
                    "</p>");
            sb.append("<p class=\"der cerradas-si\"></p></div>");
            /*
            sb.append("<div class=\"swbform\">");
            sb.append("<div>" +
                    paramsRequest.getLocaleString("lblCrReportTitle") + " " +
                    currentUser.getFullName() + "</div>");
            sb.append("<div>" +
                    paramsRequest.getLocaleString("lblCrReportStart") + " " +
                    strInitialReportDate + " " +
                    paramsRequest.getLocaleString("lblCrReportEnd") +
                    strEndReportDate + "</div>");
            //TODO: Agregar criterios
            sb.append("<div>" +
                    paramsRequest.getLocaleString("lblCrReportCriteria") +
                    "</div>");
            */
            
            sb.append("<table><thead><th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow0") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow1") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow2") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow3") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow4") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow5") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow6") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow7") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow8") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow9") + "</a></th>" +
                    "<th class=\"orden-no\"><a href=\"#\">" +
                    paramsRequest.getLocaleString("cpHeaderRow10") + "</a></th></thead><tbody>");
            for(int i=0; i<vAllTasks.size(); i++){
                TaskLink tLink = (TaskLink) vAllTasks.get(i);
                FlowNodeInstance fobi = tLink.getTaskLinkFlowNodeInstance();
                sb.append(printReportTask(fobi, paramsRequest));  
            }
            sb.append("</tbody></table>");
             
            //sb.append("</div>");
            sb.append("<div id=\"descarga\">");
            sb.append("<ul><li>");
            sb.append(createXmlReport(request, paramsRequest));
            sb.append("</li><li>");
            sb.append(createPdfReport(request, paramsRequest));
            sb.append("</li><li>");
            sb.append(createRtfReport(request, paramsRequest));
            sb.append("</li></ul>");
            sb.append("");
            sb.append("<form name=\"frmReport\" action=\"" + url.setAction("selectReport") + "\" method=\"POST\">");
            //sb.append("<input type=\"SUBMIT\" " + "name=\"btnReport\"  value=\"" + paramsRequest.getLocaleString("btnNewReport") + "\"/>");
            sb.append("<input type=\"SUBMIT\" " + "name=\"btnBack2View\" class=\"pleca_boton\"  value=\"" + paramsRequest.getLocaleString("btnBack") + "\"" +
                    " onclick=\"direcciona('" + url.setAction("goToView") + "');\" />");
            sb.append("</form></div>");
        } catch(Exception e){
          //log.error("Error en ControlPanel.displayReport", e);
            System.out.println("Error en ControlPanel.displayReport:"
                    + e.getMessage());
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
          //log.error("Error en ControlPanel.buildReport", e);
            System.out.println("Error en ControlPanel.buildReport:"
                    + e.getMessage());
		}
        return sb;
    }


    //FORMATOS
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
            Vector vSelectedProcessDefinitions =
                        getSelectedProcessDefinitions(paramsRequest);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direccionaI(action){");
            sb.append("\n      document.forms['frmSelectReport'].action = action;");
            sb.append("\n      document.forms['frmSelectReport'].submit();");
            sb.append("\n   }");
            sb.append("\n</script>");

            sb.append("<div id=\"informe\"><div class=\"pleca\"><h3>" +
                    paramsRequest.getLocaleString("lblReportTitle") +
                    "</h3>");
            sb.append("<form name=\"frmSelectReport\" action=\"" +
                    paramsRequest.getRenderUrl().setAction("buildReport") +
                    "\" dojoType=\"dijit.form.Form\" method=\"POST\">");

            sb.append("<label for=\"" + INITIAL_DATE_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportIniDate") + ":</label>");
            sb.append("<input type=\"text\" name=\"" + INITIAL_DATE_REPORT_CTRL + "\" id=\"" + INITIAL_DATE_REPORT_CTRL +"\" class=\"pleca_txt\" " +
                  "dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\" />");
            sb.append("<br />");
            /*
            sb.append(paramsRequest.getLocaleString("lblReportIniDate")
                    + "<input id=\"" +INITIAL_DATE_REPORT_CTRL +
                    "\" name=\"" + INITIAL_DATE_REPORT_CTRL +"\"" +
                    " dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\"/><br/><br/>");
             */
            sb.append("<label for=\"" + END_DATE_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportEndDate") + ":</label>");
            sb.append("<input type=\"text\" name=\"" + END_DATE_REPORT_CTRL + "\" id=\"" + END_DATE_REPORT_CTRL +"\" class=\"pleca_txt\" " +
                  "dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\" />");
            sb.append("<br />");
            /*
            sb.append(paramsRequest.getLocaleString("lblReportEndDate")
                    + "<input id=\"" +END_DATE_REPORT_CTRL +
                    "\" name=\"" + END_DATE_REPORT_CTRL +"\"" +
                    " dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%; \"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\"/><br/><br/>");
            */
	        sb.append("<label for=\"" + PROCESS_REPORT_CTRL +"\" >" + paramsRequest.getLocaleString("lblReportProcess") + ":</label>");
            sb.append(" <select name=\""+PROCESS_REPORT_CTRL+ ""
                + "\" id=\""+PROCESS_REPORT_CTRL+"\">");
            sb.append("<option ");
            sb.append("value=\"0\">" +
                    paramsRequest.getLocaleString("cpAllProcesses") +
                    "</option>");
            for(int i=0; i<vSelectedProcessDefinitions.size(); i++){
                org.semanticwb.process.model.Process selectedProcess =
                    (org.semanticwb.process.model.Process)
                vSelectedProcessDefinitions.get(i);
                sb.append("<option ");
                sb.append("value=\"" + selectedProcess.getURI() + "\">"
                    + selectedProcess.getTitle() +"</option>");
            }
            sb.append("</select><br/><br/>");
            Vector vTaskStatus =
                    BPMSProcessInstance.ClassMgr.stringToVector(ALL_STATUS);
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
            Vector vTaskPriority =
                    BPMSProcessInstance.ClassMgr.stringToVector(ALL_PRIORITY);
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

            sb.append("<input type=\"SUBMIT\" class=\"pleca_boton\" name=\"btnEdit\"  value=\"" +
                    paramsRequest.getLocaleString("btnApplyFilter") + "\"/>");
            sb.append("<input type=\"RESET\" class=\"pleca_boton\" name=\"btnReset\" value=\"" +
                    paramsRequest.getLocaleString("btnCancel") + "\"/>");
            //sb.append("<input type=\"SUBMIT\" name=\"btnBack2View\"  value=\"" + paramsRequest.getLocaleString("btnBack") + "\"" + " onclick=\"direccionaI('" + url.setAction("goToView") + "');\" />");

            sb.append("</form></div></div>");
        } catch(Exception e){
          //log.error("Error en ControlPanel.getFilterForm", e);
            System.out.println("Error en ControlPanel.getReportForm:"
                    + e.getMessage());
		}
        return sb;
    }

    /**
    * Genera el codigo HTML para seleccionar propiedades de los artefactos.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getArtifactPropertiesForm(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strOrder = "";
        String strChecked = "";
        String strRadio1Checked = "";
        String strRadio2Checked = "";
        String strRadio3Checked = "";
        try
        {
            //de los procesos seleccionados previamente, mostrar las props de todos sus artefactos
            Resource base = paramRequest.getResourceBase();
            String strCPProcessDefinitions = base.getAttribute("cpProcessDefinitions")==null
                    ?""
                    :base.getAttribute("cpProcessDefinitions");
            Vector vDefinitions =
                BPMSProcessInstance.ClassMgr.stringToVector(strCPProcessDefinitions);
            Vector vAllProcessDefinitions =
                    BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(
                        paramRequest);
            //TODO: Sort del vector de definiciones
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function getCheckedBoxesNumber(checkboxObj){");
            sb.append("\n      var selected_checkboxes = 0;");
            sb.append("\n      for (var i=0; i < checkboxObj.length; i++){");
            sb.append("\n        if (checkboxObj[i].checked) {");
            sb.append("\n          selected_checkboxes++;");
            sb.append("\n        }");
            sb.append("\n      }");
            sb.append("\n      return selected_checkboxes;");
            sb.append("\n   }");
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
            sb.append("\n         alert('" +
                    paramRequest.getLocaleString("jsMsg1") + "');");
            sb.append("\n         elem.value='';");
            sb.append("\n         bOk = false;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function getCheckedBoxesApplyOn(checkboxObj){");
            sb.append("\n      var bOk = true;");
            sb.append("\n      for (var i=0; i < checkboxObj.length; i++){");
            sb.append("\n        if (checkboxObj[i].checked) {");
            sb.append("\n          var chkValue = 'cpApplyOn' + checkboxObj[i].value;");
            sb.append("\n          //alert('chkValue:' + chkValue);");
            sb.append("\n          var applyOnValue = document.forms['frmAdmin'].elements[chkValue];");
            sb.append("\n          //alert('applyOnValue:' + applyOnValue.value);");
            sb.append("\n          var countApplyOn = getCheckedBoxesNumber(applyOnValue);");
            sb.append("\n          //alert('countApplyOn:' + countApplyOn);");
            sb.append("\n          if(countApplyOn<1){");
            sb.append("\n             bOk = false;");
            sb.append("\n             break;");
            sb.append("\n          }");
            sb.append("\n        }");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateFormProps(){");
            sb.append("\n      var selected_properties = 0;");
            sb.append("\n      var vMsg = '';");
            sb.append("\n      var bOk = false;");
            sb.append("\n      selected_properties = getCheckedBoxesNumber(document.forms['frmAdmin'].elements['cpIncludeProperty']);");
            sb.append("\n      //alert('selected_properties:' + selected_properties);");
            sb.append("\n      if(selected_properties<1){");
            sb.append("\n         vMsg =  vMsg + '" +
                    paramRequest.getLocaleString("jsMsg6") + "';");
            sb.append("\n      }");
            sb.append("\n      if(getCheckedBoxesApplyOn(document.forms['frmAdmin'].elements['cpIncludeProperty'])==false){");
            sb.append("\n         vMsg =  vMsg + '" +
                    paramRequest.getLocaleString("jsMsg7") +"';");
            sb.append("\n      }");
            sb.append("\n      //alert('vMsg.length:' + vMsg.length);");
            sb.append("\n      if(vMsg.length>1){");
            sb.append("\n         alert(vMsg);");
            sb.append("\n      } else {");
            sb.append("\n         bOk = true;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n</script>");

            sb.append("<div class=\"swbform\">");
            sb.append("<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("updateProperties") +
                    "\" method=\"POST\" onsubmit=\"return validateFormProps()\" >");
            sb.append("\n<ul class=\"cpPropsMenu\">");
            sb.append("\n<li>" +
                    paramRequest.getLocaleString("lblPropertiesTable") +
                    ":</li>");
            sb.append("\n<li>\n<ul>\n<li>" + paramRequest.getLocaleString("thPropTable1")
                    + "</li><li>" + paramRequest.getLocaleString("thPropTable2")
                    + "</li><li>" + paramRequest.getLocaleString("thPropTable3")
                    + "</li><li>" + paramRequest.getLocaleString("thPropTable4")
                    + "</li>\n</ul>\n</li>");
            for(int i=0; i<vAllProcessDefinitions.size(); i++)
            {
                org.semanticwb.process.model.Process process =
                        (org.semanticwb.process.model.Process) vAllProcessDefinitions.get(i);
                if(vDefinitions.contains(process.getURI()))
                {
                    sb.append("\n<li id=\"" + process.getId() + "\">" +  process.getTitle());
                    Vector vProps =
                            getProcessArtifactDefinitionProperties(process);
                    ProcessProperty.sortProcessProperty(vProps);
                    Vector vSelectedProps =
                            getSelectedProcessProperties(paramRequest,"cpSelectedProperties");
                    sb.append("\n<ul id=\"props" + process.getId() + "\">");
                    for(int j=0; j<vProps.size(); j++)
                    {
                        ProcessProperty property = (ProcessProperty)vProps.get(j);
                        if(containsProcessProperty(vSelectedProps, property.getURI()))
                        {
                            ProcessProperty tempProp =
                                    findProcessProperty(vSelectedProps, property.getURI());
                            strChecked ="checked";
                            strOrder = String.valueOf(tempProp.getOrderOnTask());
                            if(tempProp.isAppliedOnTaskLegend()){
                                strRadio2Checked = "checked";
                            }
                            if(tempProp.isAppliedOnTaskColumn()){
                                strRadio3Checked = "checked";
                            }
                            if(tempProp.isAppliedOnTaskLink()){
                                strRadio1Checked = "checked";
                            }
                        } else {
                            strChecked ="";
                            strOrder = "0";
                            strRadio1Checked = "";
                            strRadio2Checked = "";
                            strRadio3Checked = "";
                        }
                        sb.append("\n<li>");
                        sb.append("<input type=\"CHECKBOX\" " +
                                "id=\"cpIncludeProperty\" " +
                                " name=\"cpIncludeProperty\" " + strChecked +
                                " value=\"" + property.getURI() + "\">" +
                                property.getProperty().getDisplayName());
                        sb.append("\n</li>");
                        sb.append("\n<li>");
                        sb.append(property.getArtifactName());
                        sb.append("\n</li>");
                        sb.append("\n<li>");
                        sb.append("<input type=\"CHECKBOX\" " +
                                "id=\"cpApplyOn" + property.getURI() + "\"" +
                                " name=\"cpApplyOn" + property.getURI() + "\" " +
                                strRadio1Checked + " value=\"0\" >" +
                                paramRequest.getLocaleString("cpUse0") + "<br/>");
                        sb.append("<input type=\"CHECKBOX\" " +
                                "id=\"cpApplyOn" + property.getURI() + "\"" +
                                " name=\"cpApplyOn" + property.getURI() + "\" " +
                                strRadio2Checked + " value=\"1\" >" +
                                paramRequest.getLocaleString("cpUse1") + "<br/>");
                        sb.append("<input type=\"CHECKBOX\" " +
                                "id=\"cpApplyOn" + property.getURI() + "\"" +
                                " name=\"cpApplyOn" + property.getURI() + "\" " +
                                strRadio3Checked + " value=\"2\" >" +
                                paramRequest.getLocaleString("cpUse2"));
                        sb.append("\n</li>");
                        sb.append("\n<li>");
                        sb.append("<input type=\"TEXT\" name=\"cpPropertyOrder" +
                                property.getURI() + "\" size=\"4\" maxlength=\"4\" " +
                                " label=\"\" onKeyUp=\"validateNumeric(this)\"" +
                                " value=\"" + strOrder +"\"/>");
                        sb.append("\n</li>");
                    }
                    sb.append("\n</ul>");
                    sb.append("\n</li>");
                }
            }
            sb.append("\n</ul>");
            sb.append("<input type=\"SUBMIT\" " +
                    "name=\"btnSave\"  value=\"" +
                    paramRequest.getLocaleString("btnSave") + "\"/>");
            sb.append("<input type=\"RESET\" " +
                    " name=\"btnReset\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("\n</form>");
            sb.append("\n</div>");
        } catch(Exception e){
          //log.error("Error en ControlPanel.getArtifactPropertiesForm", e);
            System.out.println("Error en ControlPanel.getArtifactPropertiesForm:" +
                    e.getMessage());
		}
        return sb;
    }

    /**
    * Imprime el codigo Javascript para las validaciones del formato de admon
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    */
    public StringBuffer getValidatingScript(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function getCheckedBoxesNumber(checkboxObj){");
            sb.append("\n      var selected_checkboxes = 0;");
            sb.append("\n      for (var i=0; i < checkboxObj.length; i++){");
            sb.append("\n        if (checkboxObj[i].checked) {");
            sb.append("\n          selected_checkboxes++;");
            sb.append("\n        }");
            sb.append("\n      }");
            sb.append("\n      return selected_checkboxes;");
            sb.append("\n   }");
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
            sb.append("\n         alert('" +
                    paramRequest.getLocaleString("jsMsg1") +  "');");
            sb.append("\n         elem.value='';");
            sb.append("\n         bOk = false;");
            sb.append("\n      }");
            sb.append("\n      return bOk;");
            sb.append("\n   }");
            sb.append("\n   function validateFormProps(){");
            sb.append("\n      var selected_proccesses = 0;");
            sb.append("\n      var vMsg = '';");
            sb.append("\n      var bOk = false;");
            sb.append("\n      selected_proccesses = getCheckedBoxesNumber(document.forms['frmAdmin'].elements['cpProcessDefinitions']);");
            sb.append("\n      //alert('selected_proccesses:' + selected_proccesses);");
            sb.append("\n      if(selected_proccesses<1){");
            sb.append("\n         vMsg =  vMsg + '" +
                    paramRequest.getLocaleString("jsMsg4") + "';");
            sb.append("\n      }");
            sb.append("\n   }");
            sb.append("\n</script>");
        } catch(Exception e){
          //log.error("Error en ControlPanel.getValidatingScript", e);
            System.out.println("Error en ControlPanel.getValidatingScript:" +
                    e.getMessage());
		}
        return sb;
    }

    /**
    * Genera el codigo HTML para seleccionar los procesos a desplegar en la
    * bandeja.
    *
    * @param            paramRequest SWBParamRequest
    * @return      		StringBuffer con codigo HTML
    * @see
    */
    public StringBuffer getProcessForm(SWBParamRequest paramRequest)
    {
        StringBuffer sb = new StringBuffer();
        String strChecked = "";
        try
        {
            Resource base = paramRequest.getResourceBase();
            String strCPTitle = base.getAttribute("title")==null
                    ?""
                    :base.getAttribute("title");
            String strCPProcessDefinitions =
                base.getAttribute("cpProcessDefinitions")==null
                    ?""
                    :base.getAttribute("cpProcessDefinitions");
            String strCPRowsPerPage =
                    base.getAttribute("cpRowsPerPage")==null
                    ?String.valueOf(intDefaultRowsPerPage)
                    :base.getAttribute("cpRowsPerPage");
            String strTaskParams =
                    base.getAttribute("cpTaskParams")==null
                    ?""
                    :base.getAttribute("cpTaskParams");
            String strSelTaskProps =
                    base.getAttribute("taskProperties")==null
                    ?""
                    :base.getAttribute("taskProperties");
            Vector vDefinitions =
                    BPMSProcessInstance.ClassMgr.stringToVector(strCPProcessDefinitions);
            Vector vTaskProps = getTaskProperties(paramRequest);
            Vector vSelectedTaskProps = arrayToTaskProperties(strSelTaskProps);
            sb.append(getValidatingScript(paramRequest));
            sb.append("\n<div class=\"swbform\">");
            sb.append("\n<form name=\"frmAdmin\" action=\"" +
                    paramRequest.getRenderUrl().setAction("selectArtProps") +
                    "\" method=\"POST\" onsubmit=\"return validateForm()\">");
            sb.append("<br/><br/>\n<div>* " +
                    paramRequest.getLocaleString("lblTitle") +
                    "<br/> \n<input type=\"TEXT\" name=\"title\"" +
                    " size=\"50\" maxlength=\"50\" " +
                    " label=\"" + paramRequest.getLocaleString("lblTitle")
                    + "\" value=\"" + strCPTitle +
                    "\"/><br/><br/></div>");
            sb.append("\n<div>*" +
                    paramRequest.getLocaleString("lblTasksPerPageNumber") +
                    " \n<input type=\"TEXT\" id=\"cpRowsPerPage\" " +
                    " name=\"cpRowsPerPage\" size=\"5\" maxlength=\"4\" " +
                    " label=\"" +
                    paramRequest.getLocaleString("lblTasksPerPageNumber") +
                    "\" onKeyUp=\"validateNumeric(this)\" value=\"" +
                    strCPRowsPerPage + "\"/><br/><br/></div>");
            sb.append("\n<div>" + paramRequest.getLocaleString("lblTaskParams")
                    + "<br/>");
            sb.append("\n<textarea id=\"cpTaskParams\"" +
                    " name=\"cpTaskParams\" rows=\"5\" cols=\"50\">" +
                    strTaskParams + "</textarea><br/>");
            sb.append("</div><br/>");
            Vector vProcessDefinitions =
                    BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(paramRequest);
            sb.append("\n<div id=\"taskProps\">* " +
                    paramRequest.getLocaleString("lblProcessDefinitions")
                    + "<br/>");
            sb.append("\n<ul class=\"cpMainMenu\">");
            //TODO: Internacionalizar
            sb.append("\n<li>Propiedades de la tarea");
            sb.append("\n<ul>");
            sb.append("\n<li>Seleccionar propiedad</li>");
            sb.append("\n<li>Tipo</li>\n<li>Aplicar en</li>");
            sb.append("\n<li>Orden</li>\n</ul>\n</li>");
            sb.append("\n</ul>");
            sb.append("\n<ul>");
            for(int i=0; i<vProcessDefinitions.size(); i++)
            {
                org.semanticwb.process.model.Process process =
                        (org.semanticwb.process.model.Process) vProcessDefinitions.get(i);
                if(vDefinitions.contains(process.getURI()))
                {
                    strChecked = "checked";
                } else {
                    strChecked = "";
                }
                sb.append("\n<li>");
                sb.append("\n<input type=\"CHECKBOX\" " +
                        "id=\"cpProcessDefinitions\" " +
                        "name=\"cpProcessDefinitions\" " +
                        strChecked + " value=\"" + process.getURI()
                        + "\" >" + process.getTitle());
                sb.append("\n<ul id=\"" + process.getURI() + "\">");
                String strRadio1Checked = "";
                String strRadio2Checked = "";
                String strRadio3Checked = "";
                String strOrder = "";
                for(int j=0; j<vTaskProps.size();j++)
                {
                    String[] strTemp = (String[])vTaskProps.get(j);
                    String strTempPropName = process.getURI() + "|" + strTemp[0] + "|" + strTemp[2] + "|";
                    TaskProperty tProp = findTaskProperty(vSelectedTaskProps, process.getURI(), strTemp[0], strTemp[2]);
                    if(tProp.getURI()!=null && !tProp.getURI().equalsIgnoreCase(""))
                    {
                        strChecked = "checked";
                        if(tProp.isAppliedOnTaskLink())
                        {
                            strRadio1Checked = "checked";
                        }
                        if(tProp.isAppliedOnTaskLegend())
                        {
                            strRadio2Checked = "checked";
                        }
                        if(tProp.isAppliedOnTaskColumn())
                        {
                            strRadio3Checked = "checked";
                        }
                        strOrder = String.valueOf(tProp.getOrderOnTask());
                    } else {
                        strChecked="";
                        strRadio1Checked = "";
                        strRadio2Checked = "";
                        strRadio3Checked = "";
                        strOrder = "0";
                    }
                    sb.append("\n<li>");
                    sb.append("\n<input type=\"CHECKBOX\" " +
                            "id=\"cpIncludeTaskProperty\"" +
                            " name=\"cpIncludeTaskProperty\"" +
                            strChecked + " value=\"" + strTempPropName + "\" >");
                    sb.append(strTemp[0] + " " + strTemp[1] + "</li>");
                    sb.append("<li><input type=\"CHECKBOX\" " +
                            "id=\"cpTaskPropApplyOn|" + strTempPropName +  "\"" +
                            " name=\"cpTaskPropApplyOn|" + strTempPropName + "\" " +
                            strRadio1Checked + " value=\"0\" >" +
                            paramRequest.getLocaleString("cpUse0") + "<br/>");
                    sb.append("<input type=\"CHECKBOX\" " +
                            "id=\"cpTaskPropApplyOn|"  + strTempPropName + "\"" +
                            " name=\"cpTaskPropApplyOn|" + strTempPropName + "\"" +
                            strRadio2Checked + " value=\"1\" >" +
                            paramRequest.getLocaleString("cpUse1") + "<br/>");
                    sb.append("<input type=\"CHECKBOX\" " +
                            "id=\"cpTaskPropApplyOn|" + strTempPropName + "\"" +
                            " name=\"cpTaskPropApplyOn|" + strTempPropName + "\" " +
                            strRadio3Checked + " value=\"2\" >" +
                            paramRequest.getLocaleString("cpUse2") + "</li>");
                    sb.append("<li><input type=\"TEXT\" " +
                            "id=\"cpTaskPropOrder|" + strTempPropName + "\""
                            + " name=\"cpTaskPropOrder|" + strTempPropName + "\""
                            + " size=\"4\" maxlength=\"4\" " +
                            " label=\"\" onKeyUp=\"validateNumeric(this)\"" +
                            " value=\"" + strOrder +"\"/></li>");
                }
                sb.append("\n</ul>");
                sb.append("</li>");
            }
            sb.append("\n</ul>");
            sb.append("\n</li>");
            sb.append("\n</ul>");
            sb.append("\n</div>");
            sb.append("<input type=\"SUBMIT\" " +
                    "name=\"btnSave\"  value=\"" +
                    paramRequest.getLocaleString("btnSave") + "\"/>");
            sb.append("<input type=\"RESET\" " +
                    " name=\"btnReset\" value=\"" +
                    paramRequest.getLocaleString("btnCancel") + "\"/>");
            sb.append("</form>");
            sb.append("</div>");
        } catch(Exception e){
          //log.error("Error en ControlPanel.getProcessForm", e);
            System.out.println("Error en ControlPanel.getProcessForm:" +
                    e.getMessage());
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
            String strControlPanelTaskStatus =
                    base.getAttribute("controlPanelTaskStatus")==null
                    ?""
                    :base.getAttribute("controlPanelTaskStatus");
            String strControlPanelTaskPriority =
                    base.getAttribute("controlPanelTaskPriority")==null
                    ?""
                    :base.getAttribute("controlPanelTaskPriority");
            strControlPanelTaskStatus = ALL_STATUS;
            strControlPanelTaskPriority = ALL_PRIORITY;
            Vector vSelectedProcessDefinitions =
                        getSelectedProcessDefinitions(paramsRequest);
            sb.append("\n<script type=\"text/javascript\">");
            sb.append("\n   function direccionaF(action){");
            sb.append("\n      document.forms['frmFilter'].action = action;");
            sb.append("\n      document.forms['frmFilter'].submit();");
            sb.append("\n   }");
            sb.append("\n</script>");

            sb.append("<div id=\"filtrado\"><div class=\"pleca\"><h3>" +
                    paramsRequest.getLocaleString("lblFilterTitle") +
                    "</h3>");
            sb.append("<form name=\"frmFilter\" action=\"" +
                    paramsRequest.getRenderUrl().setAction("filter") +
                    "\" dojoType=\"dijit.form.Form\" method=\"POST\">");

	      sb.append("<label for=\"" + INITIAL_DATE_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterIniDate") + ":</label>");
	      sb.append("<input type=\"text\" name=\"" + INITIAL_DATE_FILTER_CTRL + "\" id=\"" + INITIAL_DATE_FILTER_CTRL +"\" class=\"pleca_txt\" " +
                  "dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\" />");
          sb.append("<br />");
	      sb.append("<label for=\"" + END_DATE_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterEndDate") + ":</label>");
	      sb.append("<input type=\"text\" name=\"" + END_DATE_FILTER_CTRL + "\" id=\"" + END_DATE_FILTER_CTRL +"\" class=\"pleca_txt\" " +
                  "dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\"/>");
          sb.append("<br />");
            /*
            sb.append(paramsRequest.getLocaleString("lblFilterIniDate")
                    + "<input id=\"" +INITIAL_DATE_FILTER_CTRL +
                    "\" name=\"" + INITIAL_DATE_FILTER_CTRL +"\"" +
                    " dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\"/><br/><br/>");
            sb.append(paramsRequest.getLocaleString("lblFilterEndDate")
                    + "<input id=\"" + END_DATE_FILTER_CTRL +
                    "\" name=\"" + END_DATE_FILTER_CTRL +"\"" +
                    " dojoType=\"dijit.form.DateTextBox\"" +
                    " required=\"false\""+
                    "invalidMessage=\"" +
                    paramsRequest.getLocaleString("msgErrEventFecha")
                    + " style=\"width: 25%;\"" +
                    " constraints=\"{datePattern:'dd/MM/yyyy'}\"/><br/><br/>");
            */
	      sb.append("<label for=\"" + PROCESS_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterProcess") + ":</label>");
	      sb.append("<select name=\"" + PROCESS_FILTER_CTRL + "\" id=\"" + PROCESS_FILTER_CTRL +"\" />");
          /*
          sb.append(paramsRequest.getLocaleString("lblFilterProcess")
                    + " <select name=\""+PROCESS_FILTER_CTRL+ ""
                + "\" id=\""+PROCESS_FILTER_CTRL+"\">");
            */
            sb.append("<option ");
            sb.append("value=\"0\">" +
                    paramsRequest.getLocaleString("cpAllProcesses") +
                    "</option>");
            for(int i=0; i<vSelectedProcessDefinitions.size(); i++){
                org.semanticwb.process.model.Process selectedProcess =
                    (org.semanticwb.process.model.Process)
                vSelectedProcessDefinitions.get(i);
                sb.append("<option ");
                sb.append("value=\"" + selectedProcess.getURI()
                    + "\">" + selectedProcess.getTitle() +"</option>");
            }
            sb.append("</select><br/>");

            Vector vTaskStatus =
                    BPMSProcessInstance.ClassMgr.stringToVector(
                        strControlPanelTaskStatus);
	        sb.append("<label for=\"" + STATUS_FILTER_CTRL +"\" >" + paramsRequest.getLocaleString("lblFilterStatus") + ":</label>");
            //sb.append(paramsRequest.getLocaleString("lblFilterStatus")
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
            //sb.append(paramsRequest.getLocaleString("lblFilterPriority")
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
            sb.append("<input type=\"SUBMIT\" class=\"pleca_boton\" name=\"btnEdit\"  value=\"" +
                    paramsRequest.getLocaleString("btnApplyFilter") + "\"/>");
            sb.append("<input type=\"RESET\" class=\"pleca_boton\" name=\"btnReset\" value=\"" +
                    paramsRequest.getLocaleString("btnCancel") + "\"/>");
            //sb.append("<input type=\"SUBMIT\" name=\"btnBack2View\" value=\"" + paramsRequest.getLocaleString("btnBack") + "\" onclick=\"direccionaF('" + url.setAction("goToView") + "');\" />");
            sb.append("</form>");
            sb.append("</div></div>");
        } catch(Exception e){
          //log.error("Error en ControlPanel.getFilterForm", e);
            System.out.println("Error en ControlPanel.getFilterForm:"
                    + e.getMessage());
		}
        return sb;
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
        StringBuffer ret = new StringBuffer();
        String msg = null;
        try
        {
            if(paramRequest.getAction().equalsIgnoreCase("updateProperties"))
            {
                //actualizar propiedades de artefactos
                out.print(updateArtifactPropertiesValues(request, paramRequest));
                taskAttributes = setTaskAttributesMap(paramRequest);
                setResourceTaskAttributesMap(taskAttributes, paramRequest);
            } else if(paramRequest.getAction().equalsIgnoreCase("selectArtProps")){
                //actualizar datos del formato de administracion
                out.print(updateProcessFormValues(request, paramRequest));
                //presentar formato para seleccionar propiedades de artefactos
                out.print(getArtifactPropertiesForm(paramRequest));
            } else {
                //Desplegar formato de administracion
               // out.print(getProcessFormJS());
                out.print(getProcessForm(paramRequest));
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.doAdmin", e);
            System.out.println("Error en ControlPanel.doAdmin:" +
                    e.getMessage());
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
            if(paramsRequest.getAction().equalsIgnoreCase("editCustomization"))
            {
                out.print(customizeDisplay(request, response, paramsRequest));
            } else if(paramsRequest.getAction().equalsIgnoreCase("updateCustomization"))
            {
                out.print(setCustomizedData(request, paramsRequest));
            } else if(paramsRequest.getAction().equalsIgnoreCase("editFilters"))
            {
                out.println(getFilterForm(paramsRequest).toString());
            } else if(paramsRequest.getAction().equalsIgnoreCase("selectReport"))
            {
                out.println(getReportForm(paramsRequest).toString());
            } else {
                //pintar tabla de tareas
                Resource base = paramsRequest.getResourceBase();
                getResourceTaskAttributesMap(paramsRequest);
                String strTitle = base.getAttribute("title");
                out.println("\n<script type=\"text/javascript\">");
                out.println("\n   function direcciona(action){");
                out.println("\n      document.forms['frmEditCustomization'].action = action;");
                out.println("\n      document.forms['frmEditCustomization'].submit();");
                out.println("\n   }");
                out.println("\n</script>");
                out.println("<div id=\"panel\" >");
                out.println("<h2>" + strTitle + "</h2>");
                out.println("<div id=\"filtros\"><p>" + paramsRequest.getLocaleString("lblAppliedFilters") + "</p>");
                out.println(getUsedFilters(paramsRequest, request).toString() + "");
                out.println("</div>");
                if(paramsRequest.getAction().equalsIgnoreCase("buildReport")){
                    out.println(buildReport(request, paramsRequest).toString());
                } else {
                    out.println(printTaskLinks(request,paramsRequest).toString());
                }
                out.println("<div id=\"tabs\">");
                out.println("<form id=\"frmEditCustomization\" name=\"frmEditCustomization\" action=\"" +
                        paramsRequest.getRenderUrl().setAction(
                        "editCustomization") + "\" method=\"POST\">");
                //out.println("<li><input type=\"SUBMIT\" name=\"btnEdit\" " + " value=\"" + paramsRequest.getLocaleString("btnCustomize") + "\"/></li>");
                out.println("<ul>");
                //out.println("<li class=\"tab-on\"><a href=\"#\" onclick=\"direcciona('" + paramsRequest.getRenderUrl().setAction("editFilters") + "');\">" + paramsRequest.getLocaleString("btnFilter") + "</a></li>");
                //out.println("<li class=\"tab-off\"><a href=\"#\" onclick=\"direcciona('" + paramsRequest.getRenderUrl().setAction("selectReport") + "');\">" + paramsRequest.getLocaleString("btnReport") + "</a></li>");
                out.println("<li id=\"li_filtrado\" class=\"tab-on\"><a href=\"#\" onclick=\"MM_showHideLayers('filtrado','','show','informe','','hide','personaliza','','hide');selectedTab('filtrado');\">" + paramsRequest.getLocaleString("btnFilter") + "</a></li>");
                out.println("<li id=\"li_informe\" class=\"tab-off\"><a href=\"#\" onclick=\"MM_showHideLayers('filtrado','','hide','informe','','show','personaliza','','hide');selectedTab('informe');\">" + paramsRequest.getLocaleString("btnReport") + "</a></li>");
                out.println("<li id=\"li_personaliza\" class=\"tab-off\"><a href=\"#\" onclick=\"MM_showHideLayers('filtrado','','hide','informe','','hide','personaliza','','show');selectedTab('personaliza');\">" + paramsRequest.getLocaleString("btnCustomize") + "</a></li>");
                out.println("</ul>");
                //out.println("<input type=\"SUBMIT\" name=\"btnFilter\" " + " value=\"" + paramsRequest.getLocaleString("btnFilter") + "\" " + "onclick=\"direcciona('" + paramsRequest.getRenderUrl().setAction("editFilters") + "');\" />");
                //out.println("<input type=\"SUBMIT\" name=\"btnReport\" " + " value=\"" + paramsRequest.getLocaleString("btnReport") + "\" " + "onclick=\"direcciona('" + paramsRequest.getRenderUrl().setAction("selectReport") + "');\" />");
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
                out.println("<div id=\"plecas-padre\">");
                out.println(getFilterForm(paramsRequest));
                out.println(getReportForm(paramsRequest));
                out.print(customizeDisplay(request, response, paramsRequest));
                out.println("</div>");
            out.println("</div>");
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.doView", e);
            System.out.println("Error en ControlPanel.doView:" +
                    e.getMessage());
		}
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
            //sb.append("<table border=1><tr>");
            if(totalPages>1){
                //if(totalPages>1 && intCurrPage>1){
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
                    sb.append("\" class=\"pag_ant\" >" + paramRequest.getLocaleString("lblBackPage") +
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
                //if(totalPages>1 && intCurrPage<totalPages){
                if(intCurrPage<totalPages){
                    int nextPage = intCurrPage + 1;
                    SWBResourceURL sUrlNext = paramRequest.getRenderUrl();
                    sUrlNext.setParameter("cpCurrPage", String.valueOf(nextPage));
                    sb.append("<a href=\"" + sUrlNext);
                    if(!strFilterPagination.equalsIgnoreCase("")){
                        sb.append(strFilterPagination);
                    }
                    sb.append("\" class=\"pag_sig\" >" + paramRequest.getLocaleString("lblNextPage") + "</a>");
                    SWBResourceURL sUrlLastPage = paramRequest.getRenderUrl();
                    sUrlLastPage.setParameter("cpCurrPage",
                            String.valueOf(totalPages));
                    sb.append("<a href=\"" + sUrlLastPage);
                    if(!strFilterPagination.equalsIgnoreCase("")){
                        sb.append(strFilterPagination);
                    }
                    sb.append("\">" + paramRequest.getLocaleString("lblLastPage") +
                            "</a>");
                }                
            }
            //sb.append("</tr></table>");
        } catch(Exception e){
          //log.error("Error en ControlPanel.getPagination", e);
            System.out.println("Error en ControlPanel.getPagination:"
                    + e.getMessage());
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
            if(currentPage>0)
            {
                firstRow = (currentPage - 1) * rowsPerPage;
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getPageFirstRow", e);
            System.out.println("Error en ControlPanel.getPageFirstRow:"
                    + e.getMessage());
		}
        return firstRow;
    }

    /**
    * Calcula el numero del ultimo resultado para mostrar en la pagina actual
    *
    * @param            vectorSize int
    * @param            rowsPerPage int
    * @param            currentPage int
    * @return      		firstRow int
    */
    public int getPageLastRow(int vectorSize, int rowsPerPage, int currentPage)
    {
        int endRow = 0;
        try
        {
            int totalPages = getTotalPages(vectorSize,rowsPerPage);
            endRow = totalPages==currentPage
                ?vectorSize
                :rowsPerPage * currentPage;
        } catch(Exception e){
          //log.error("Error en ControlPanel.getPageLastRow", e);
            System.out.println("Error en ControlPanel.getPageLastRow:"
                    + e.getMessage());
		}
        return endRow;
    }

    /**
    * Calcula el numero de paginas requeridas para mostrar los resultados
    *
    * @param            vectorSize int
    * @param            rowsPerPage int
    * @return      		firstRow int
    */
    public int getTotalPages(int vectorSize, int rowsPerPage)
    {
        int totalPages = 0;
        try
        {
            int modulus = vectorSize % rowsPerPage;
            totalPages = vectorSize / rowsPerPage;
            if(modulus>0)
            {
                totalPages++;
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.getTotalPages", e);
            System.out.println("Error en ControlPanel.getTotalPages:"
                    + e.getMessage());
		}
        return totalPages;
    }

    //UTILERIAS
    public String getPriorityDescription(SWBParamRequest paramsRequest,
            int priority)
    {
        String strDescription = "";
        try
        {
            switch(priority)
            {
                case 0:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskPriority0");
                    break;
                case 1:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskPriority1");
                    break;
                case 2:
                    strDescription = paramsRequest.getLocaleString("cpTaskPriority2");
                    break;
                case 3:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskPriority3");
                    break;
                case 4:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskPriority4");
                    break;
                case 5:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskPriority5");
                    break;
            }
        } catch (Exception e) {
            //log.error("Error en ControlPanel.getPriorityDescription", e);
            System.out.println("error en ControlPanel.getPriorityDescription: " +
                    e.getMessage());
        }
        return strDescription;
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
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskStatus3");
                    break;
                case Instance.STATUS_CLOSED:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskStatus4");
                    break;
                case Instance.STATUS_INIT:
                    //TODO: Checar este estatus
                    strDescription = "Inicializada";
                    break;
                case Instance.STATUS_OPEN:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskStatus5");
                    break;
                case Instance.STATUS_PROCESSING:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskStatus1");
                    break;
                case Instance.STATUS_STOPED:
                    strDescription =
                            paramsRequest.getLocaleString("cpTaskStatus2");
                    break;
            }
        } catch (Exception e) {
            //log.error("Error en ControlPanel.getStatusDescription", e);
            System.out.println("error en ControlPanel.getStatusDescription: " +
                    e.getMessage());
        }
        return strDescription;
    }

    /**
	 * Construye un objeto String, concatenando los elementos de un arreglo de
     * tipo String. Se usa para obtener los valores seleccionados en un grupo
     * de checkboxes y construir un String que pueda guardarse en la base del
     * recurso.
	 *
     * @param               String[] Arreglo con los valores seleccionados de
     *                      un grupo de checkboxes
     * @return      		String con los mismos elementos del arreglo,
     *                      separados por |
	 * @see         		stringToVector
	 */
    public String checkboxesToString(String[] strArray){
        String strConcat = "";
        try
        {
            if(strArray.length>0){
                for (int i=0; i<strArray.length; i++){
                    strConcat = strConcat + strArray[i] + "|";
                }
            }
        } catch(Exception e){
          //log.error("Error en ControlPanel.checkboxesToString", e);
            System.out.println("Error en ControlPanel.checkboxesToString:" +
                    e.getMessage());
		}
        return strConcat;
    }

    public static class TaskProperty
    {
        private String name;
        private String displayName;
        private String URI;
        private String type;
        private String value;
        private boolean displayOnTask;
        private boolean [] applyOnTask;
        private int orderOnTask;

        public TaskProperty(){
        }

        public TaskProperty(String name, String displayName, String type,
                String URI)
        {
            this.name = name;
            this.displayName = displayName;
            this.type = type;
            this.URI = URI;
            this.value = "";
            this.displayOnTask = false;
            boolean [] bApplyOn = {false,false,false};
            this.applyOnTask = bApplyOn;
            this.orderOnTask = 0;
        }

        public TaskProperty(String name, String displayName, String type,
                String URI, String value)
        {
            this.name = name;
            this.displayName = displayName;
            this.type = type;
            this.URI = URI;
            this.value = value;
            this.displayOnTask = false;
            boolean [] bApplyOn = {false,false,false};
            this.applyOnTask = bApplyOn;
            this.orderOnTask = 0;
        }

        public void setName(String name){
            this.name = name;
        }

        public void setDisplayName(String displayName){
            this.displayName = displayName;
        }

        public void setURI(String URI){
            this.URI = URI;
        }

        public void setType(String type){
            this.type = type;
        }

        public void setValue(String value){
            this.value = value;
        }

        public void setDisplayOnTask(boolean displayOnTask){
            this.displayOnTask = displayOnTask;
        }

        public void setApplyOnTask(boolean [] applyOnTask){
            this.applyOnTask = applyOnTask;
        }

        public void setAppliedOnTaskLink(boolean b)
        {
            this.applyOnTask[0] = b;
        }

        public void setAppliedOnTaskLegend(boolean b)
        {
            this.applyOnTask[1] = b;
        }

        public void setAppliedOnTaskColumn(boolean b)
        {
            this.applyOnTask[2] = b;
        }

        public void setOrderOnTask(int orderOnTask){
            this.orderOnTask = orderOnTask;
        }

        public String getName(){
            return this.name;
        }

        public String getDisplayName(){
            return this.displayName;
        }

        public String getURI(){
            return this.URI;
        }

        public String getType(){
            return this.type;
        }

        public String getValue(){
            return this.value;
        }

        public boolean getDisplayOnTask(){
            return this.displayOnTask;
        }

        public boolean[] getApplyOn(){
            return this.getApplyOn();
        }

        public boolean isAppliedOnTaskLink()
        {
            return this.applyOnTask[0];
        }

        public boolean isAppliedOnTaskLegend()
        {
            return this.applyOnTask[1];
        }

        public boolean isAppliedOnTaskColumn()
        {
            return this.applyOnTask[2];
        }

        public int getOrderOnTask(){
            return this.orderOnTask;
        }
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
            Integer order1 = pProp1.getOrderOnTask();
            Integer order2 = pProp1.getOrderOnTask();
            String strName1 = pProp1.getName();
            String strName2 = pProp2.getName();
            result = order2.compareTo(order1);
            if(result == 0){
                result = strName1.compareTo(strName2);
            }
            return result;
        }
    }

}
