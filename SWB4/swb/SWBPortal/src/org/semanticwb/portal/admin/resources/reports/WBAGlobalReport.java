/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */

package org.semanticwb.portal.admin.resources.reports;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.jrresources.*;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRGlobalAccessDataDetail;
import org.semanticwb.portal.api.SWBActionResponse;

    
/** Esta clase genera el reporte global de acceso, toma la informaci�n de los
 * objetos de WebBuilder de acuerdo con los par�metros recibidos del usuario. Este
 * archivo es usado en la parte de reportes.
 *
 * This class generates the global report, takes information from
 * WebBuilder objects according with user parameters. this file is used in report
 * sections.
 *
 * WBAGlobalReport.java
 * Created on October 6th, 2004 3:30 PM
 * @author Jorge R�os - IDT
 */

public class WBAGlobalReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBAGlobalReport.class);

    private final String S_REPORT_IDAUX = "_"; // Type 0 of reports "Infotec WebBuilder report support page"
    private final int I_REPORT_TYPE = 0;       // Type 0 of reports "Infotec WebBuilder report support page"
    private final int I_START_DAY = 1;         // The initial day for months
    /*private final int I_PAGE_SIZE = 20;        // Number of rows per page
    private final int I_INIT_PAGE = 0;         // The init page to display results*/
    public String strRscType;

    @Override
    public void init(){
        Portlet base = getResourceBase();        
        try{
            strRscType = base.getPortletType().getPortletClassName();
        }
        catch (Exception e){
            strRscType = "WBAGlobalReport";
        }
        
        System.out.println("\nejecutando init.....\n");
        log.debug("\nloggin  ejecutando init.....\n");
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState()))
        {
            processRequest(request, response, paramsRequest);
        }
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(paramsRequest.getMode().equals("graph")){
            doGraph(request,response,paramsRequest);
        }
        else if(paramsRequest.getMode().equals("report_excel")){
            doRepExcel(request,response,paramsRequest);
        }
        else if(paramsRequest.getMode().equals("report_xml")){
            doRepXml(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_pdf")){
            doRepPdf(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_rtf")){
            doRepRtf(request,response,paramsRequest);
        }else 
            super.processRequest(request, response, paramsRequest);
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        final int I_ACCESS = 0;
        Portlet base = getResourceBase();
        StringBuffer sb_ret = new StringBuffer();
        String[] arr_month = DoArrMonth(paramsRequest);
        GregorianCalendar gc_now = new GregorianCalendar();
        HashMap hm_sites = new HashMap();
        String address = null;
        /*int i_month = 0;*/
        int i_access = 0;
        int i_key = 0;
        /*int i_range = 0;
        int i_maxp = 0;
        int i_minp = 0;*/
        boolean b_topic = false;
        String s_topic = null;
        String rtype = null;
        String webSite = null;
        /*String s_color = null;*/
        String s_value = null;
        String s_tmid = null;
        String s_tmtitle = null;

        try{
            // Evaluates if there are sites
            Iterator it_topic = SWBContext.listWebSites();
            while(it_topic.hasNext()){
                WebSite tm_map = (WebSite)it_topic.next();
                // Evaluates if TopicMap is not Global
                if(!tm_map.getId().equals(SWBContext.getGlobalWebSite().getId())) {
                    // Get access level of this user on this topicmap and if level is greater than "0" then user have access
                    // TODO
//                    i_access = AdmFilterMgr.getInstance().haveAccess2TopicMap(paramsRequest.getUser(),tm_map.getDbdata().getId());
//                    if(I_ACCESS < i_access){
//                        if(tm_map.getDbdata().getDeleted()==0){
                            /*s_value = tm_map.getDbdata().getId() + "|" + tm_map.getDbdata().getTitle();*/
                            hm_sites.put(i_key, tm_map.getId() + "|" + tm_map.getTitle());
                            i_key++;
//                        }
//                    }
                }
            }
            // If there are sites continue
            if(hm_sites.size() > I_ACCESS){
                address = paramsRequest.getTopic().getUrl();
                webSite = request.getParameter("wb_site");
                s_topic = paramsRequest.getTopic().getId();
                if(s_topic.lastIndexOf("Daily") != -1){
                    rtype="0";
                }
                else if(s_topic.lastIndexOf("Monthly") != -1){
                    rtype="1";
                }
                else{
                    rtype = request.getParameter("wb_rtype");
                    b_topic = true;
                }
                if(rtype == null) rtype="0";

                // javascript
                sb_ret.append("\n<script type=\"text/javascript\">");
                sb_ret.append("\nfunction DoXml(accion,sizze){    ");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   if(accion == 0){");
                sb_ret.append("\n       params = params + \"&wb_year_1=\" +window.document.frmrep.wb_year_1.options[window.document.frmrep.wb_year_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_1=\" +window.document.frmrep.wb_month_1.options[window.document.frmrep.wb_month_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_1=\" +window.document.frmrep.wb_day_1.options[window.document.frmrep.wb_day_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rep_type=\" + GetTypeSelected();");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   else{");
                sb_ret.append("\n       params = params + \"&wb_year_13=\" +window.document.frmrep.wb_year_13.options[window.document.frmrep.wb_year_13.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n}");
                sb_ret.append("\nfunction DoExcel(accion,sizze){    ");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   if(accion == 0){");
                sb_ret.append("\n       params = params + \"&wb_year_1=\" +window.document.frmrep.wb_year_1.options[window.document.frmrep.wb_year_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_1=\" +window.document.frmrep.wb_month_1.options[window.document.frmrep.wb_month_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_1=\" +window.document.frmrep.wb_day_1.options[window.document.frmrep.wb_day_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rep_type=\" + GetTypeSelected();");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   else{");
                sb_ret.append("\n       params = params + \"&wb_year_13=\" +window.document.frmrep.wb_year_13.options[window.document.frmrep.wb_year_13.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n}");
                sb_ret.append("\nfunction DoGraph(accion,sizze){    ");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   if(accion == 0){");
                sb_ret.append("\n       params = params + \"&wb_year_1=\" +window.document.frmrep.wb_year_1.options[window.document.frmrep.wb_year_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_1=\" +window.document.frmrep.wb_month_1.options[window.document.frmrep.wb_month_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_1=\" +window.document.frmrep.wb_day_1.options[window.document.frmrep.wb_day_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rep_type=\" + GetTypeSelected();");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   else{");
                sb_ret.append("\n       params = params + \"&wb_year_13=\" +window.document.frmrep.wb_year_13.options[window.document.frmrep.wb_year_13.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n     window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("graph")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n }");
                
                sb_ret.append("\nfunction DoPdf(accion,sizze){    ");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   if(accion == 0){");
                sb_ret.append("\n       params = params + \"&wb_year_1=\" +window.document.frmrep.wb_year_1.options[window.document.frmrep.wb_year_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_1=\" +window.document.frmrep.wb_month_1.options[window.document.frmrep.wb_month_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_1=\" +window.document.frmrep.wb_day_1.options[window.document.frmrep.wb_day_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rep_type=\" + GetTypeSelected();");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   else{");
                sb_ret.append("\n       params = params + \"&wb_year_13=\" +window.document.frmrep.wb_year_13.options[window.document.frmrep.wb_year_13.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_pdf")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n}");
                sb_ret.append("\nfunction DoRtf(accion,sizze){    ");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   if(accion == 0){");
                sb_ret.append("\n       params = params + \"&wb_year_1=\" +window.document.frmrep.wb_year_1.options[window.document.frmrep.wb_year_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_1=\" +window.document.frmrep.wb_month_1.options[window.document.frmrep.wb_month_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_1=\" +window.document.frmrep.wb_day_1.options[window.document.frmrep.wb_day_1.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rep_type=\" + GetTypeSelected();");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   else{");
                sb_ret.append("\n       params = params + \"&wb_year_13=\" +window.document.frmrep.wb_year_13.options[window.document.frmrep.wb_year_13.selectedIndex].value;");
                sb_ret.append("\n       params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_rtf")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n}");
                
                sb_ret.append("\n function GetTypeSelected(){");
                sb_ret.append("\n     var strType = \"0\";");
                sb_ret.append("\n     for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){");
                sb_ret.append("\n       if(window.document.frmrep.wb_rep_type[i].checked==true){");
                sb_ret.append("\n           strType=window.document.frmrep.wb_rep_type[i].value;");
                sb_ret.append("\n       }");
                sb_ret.append("\n     }");
                sb_ret.append("\n     return strType;");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoApply(){");
                sb_ret.append("\n     window.document.frmrep.submit(); ");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoPaging(pag){");
                sb_ret.append("\n     window.document.frmrep.wb_pagenum.value = pag; ");
                sb_ret.append("\n     window.document.frmrep.submit(); ");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoEvaluateYear(ind){");
                sb_ret.append("\n     if(ind == 0){");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = true;");
                sb_ret.append("\n     }");
                sb_ret.append("\n     else{");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = false;");
                sb_ret.append("\n     }");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoEvaluateMonth(ind){");
                sb_ret.append("\n     if(ind == 0){");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = true;");
                sb_ret.append("\n     }");
                sb_ret.append("\n     else{");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = false;");
                sb_ret.append("\n     }");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoRedir(val){");
                sb_ret.append("\n     window.document.frmred.wb_rtype.value = val; ");
                sb_ret.append("\n     window.document.frmred.submit(); ");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoBlockade(){");
                sb_ret.append("\n     if(window.document.frmrep.wb_rep_type[0].checked){");
                sb_ret.append("\n         window.document.frmrep.wb_year_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_year_11.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_year_12.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_month_11.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_month_12.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_11.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_12.disabled = true;");
                sb_ret.append("\n     }");
                sb_ret.append("\n     if(window.document.frmrep.wb_rep_type[1].checked){");
                sb_ret.append("\n         window.document.frmrep.wb_year_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_year_11.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_year_12.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_month_11.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_month_12.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_11.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_12.disabled = false;");
                sb_ret.append("\n     }");
                sb_ret.append("\n }");
                sb_ret.append("\n</script>");
                sb_ret.append("\n<form method=\"Post\" action=\"" + address + "\" id=\"frmred\" name=\"frmred\">");
                sb_ret.append("\n<input type=\"hidden\" name=\"wb_rtype\" value=\"" + rtype +"\">");
                sb_ret.append("\n</form>");
                sb_ret.append("\n<form method=\"Post\" class=\"box\" action=\"" + address + "\" id=\"frmrep\" name=\"frmrep\">");
                sb_ret.append("\n<table border=0 width=\"100%\">");
                if(b_topic){
                    sb_ret.append("\n<tr>");
                    sb_ret.append("\n<td colspan=4><a href=\"javascript:DoRedir(0);\" class=\"link\">" + paramsRequest.getLocaleString("by_day") + "</a>&nbsp;|&nbsp;<a href=\"javascript:DoRedir(1);\" class=\"link\">" + paramsRequest.getLocaleString("monthly") + "</a></td>");
                    sb_ret.append("\n</tr>");
                }
                sb_ret.append("\n<tr>");
                sb_ret.append("\n<td colspan=4>");
                // Show report description
                sb_ret.append("\n<table width=\"100%\" border=\"0\">");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td width=\"66\"><img src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/reportes.gif\" width=\"60\" height=\"55\"><span class=\"pietitulo\"></span>");
                sb_ret.append("</td>");
                sb_ret.append("<td width=\"893\"><p class=\"pietitulo Estilo15\"><span class=\"Estilo14\">" + paramsRequest.getLocaleString("step") + " 1 " + paramsRequest.getLocaleString("of") +  " 1</span>");
                sb_ret.append("<br><br>");
                sb_ret.append("<span class=\"status Estilo16 Estilo15\">");
                if(rtype.equals("0")){
                    sb_ret.append(paramsRequest.getLocaleString("description_daily"));
                }
                else{
                    sb_ret.append(paramsRequest.getLocaleString("description_monthly"));
                }
                sb_ret.append("</span>");
                sb_ret.append("<br></p>");
                sb_ret.append("</td>");
                sb_ret.append("</tr>");
                sb_ret.append("\n</table>");
                sb_ret.append("\n</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td colspan=\"4\">&nbsp;</td>");
                sb_ret.append("\n</tr>");                
                sb_ret.append("\n<tr>");
                sb_ret.append("\n <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                sb_ret.append("   <input type=\"button\" class=\"boton\" onClick=\"DoXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\">&nbsp;");
                sb_ret.append("   <input type=\"button\" class=\"boton\" onClick=\"DoExcel('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\" name=\"btnExcel\">&nbsp;");                
                sb_ret.append("   <input type=\"button\" class=\"boton\" onClick=\"DoPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"PDF\" name=\"btnPdf\">&nbsp;");
                sb_ret.append("   <input type=\"button\" class=\"boton\" onClick=\"DoRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"RTF\" name=\"btnRtf\">&nbsp;");                
                sb_ret.append("   <input type=\"button\" class=\"boton\" onClick=\"DoGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\" value=\"" + paramsRequest.getLocaleString("graph") + "\" name=\"btnGraph\">&nbsp;");
                sb_ret.append("   <input type=\"button\" class=\"boton\" onClick=\"DoApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\">");
                sb_ret.append("\n </td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td>&nbsp;</td>");                
                sb_ret.append("<td height=\"25\" valign=\"top\" colspan=\"2\" class=\"datos\">"+ paramsRequest.getLocaleString("site") +"&nbsp;");
                /*sb_ret.append("\n<td>");*/
                sb_ret.append("<select name=\"wb_site\">");
                for(int i=0; i<hm_sites.size(); i++){
                    s_value = (String) hm_sites.get(i);
                    i_key = s_value.indexOf("|");
                    s_tmid = s_value.substring(0,i_key);
                    s_tmtitle = s_value.substring(i_key + 1,s_value.length());
                    sb_ret.append("\n<option value=\""+ s_tmid + "\"");
                    if(webSite != null){
                        if(webSite.equals(s_tmid)){
                            sb_ret.append(" selected");
                        }
                    }
                    sb_ret.append(">" + s_tmtitle + "</option>");
                }
                sb_ret.append("</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n</tr>");


                if(rtype.equals("0")){    // **************  By Day   *****************

                    // Receive parameters
                    String s_rep_type = request.getParameter("wb_rep_type");
                    String s_year_1 = request.getParameter("wb_year_1");
                    String s_month_1 = request.getParameter("wb_month_1");
                    String s_day_1 = request.getParameter("wb_day_1");
                    String s_year_11 = request.getParameter("wb_year_11");
                    String s_month_11 = request.getParameter("wb_month_11");
                    String s_day_11 = request.getParameter("wb_day_11");
                    String s_year_12 = request.getParameter("wb_year_12");
                    String s_month_12 = request.getParameter("wb_month_12");
                    String s_day_12 = request.getParameter("wb_day_12");
                    // Receive parameters

                    // Asign value to parameters
                    if(s_rep_type == null) s_rep_type = "0";
                    if(s_year_1 == null) s_year_1 = Integer.toString(gc_now.get(Calendar.YEAR));
                    if(s_month_1 == null) s_month_1 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                    if(s_day_1 == null) s_day_1 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
                    if(s_year_11 == null) s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
                    if(s_month_11 == null) s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                    if(s_day_11 == null) s_day_11 = Integer.toString(I_START_DAY);
                    if(s_year_12 == null) s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
                    if(s_month_12 == null) s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                    if(s_day_12 == null) s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
                    // Asign value to  parameters

                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td colspan=\"3\" class=\"datos\"><input type=\"radio\" value=\"0\" name=\"wb_rep_type\" onclick=\"javascript: DoBlockade();\"");
                    if(s_rep_type.equals("0")){
                        sb_ret.append(" checked");
                    }
                    sb_ret.append(">" + paramsRequest.getLocaleString("by_day") + "</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") +"&nbsp;&nbsp;<select name=\"wb_year_1\" onchange=\"javascript: DoEvaluateYear(this.selectedIndex);\"");
                    if(s_rep_type.equals("1")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append("><option value=\"0\">" + paramsRequest.getLocaleString("all") + "</option>");
                    for(int i=2000;i<2021;i++){
                        sb_ret.append("\n<option value=\"" + i +"\"");
                        if((Integer.parseInt(s_year_1) == i)){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_1\" onchange=\"javascript: DoEvaluateMonth(this.selectedIndex);\"");
                    if(s_rep_type.equals("1")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append("><option value=\"0\">" + paramsRequest.getLocaleString("all") + "</option>");
                    for(int i=0; i<= arr_month.length - 1;i++){
                        sb_ret.append("\n<option value=\"" + (i + 1) +"\"");
                        if(Integer.parseInt(s_month_1) == (i + 1)){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + arr_month[i] + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_1\"");
                    if(s_rep_type.equals("1")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">\n<option value=\"0\">" + paramsRequest.getLocaleString("all") + "</option>");
                    for(int i=1;i<32;i++){
                        sb_ret.append("\n<option value=\"" + i +"\"");
                        if(Integer.parseInt(s_day_1) == i){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td colspan=4>&nbsp;</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td colspan=\"3\" class=\"datos\"><input type=\"radio\" value=\"1\" name=\"wb_rep_type\" onclick=\"javascript: DoBlockade();\"");
                    if(s_rep_type.equals("1")){
                        sb_ret.append(" checked");
                    }
                    sb_ret.append(">" + paramsRequest.getLocaleString("by_interval_date") + "</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;&nbsp;<select name=\"wb_year_11\"");
                    if(s_rep_type.equals("0")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for(int i=2000;i<2021;i++){
                        sb_ret.append("\n<option value=\"" + i +"\"");
                        if(Integer.parseInt(s_year_11) == i){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_11\"");
                    if(s_rep_type.equals("0")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for(int i=0; i<= arr_month.length - 1;i++){
                        sb_ret.append("\n<option value=\"" + (i + 1) +"\"");
                        if(Integer.parseInt(s_month_11) == (i + 1)){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + arr_month[i] + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_11\"");
                    if(s_rep_type.equals("0")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for(int i=1;i<32;i++){
                        sb_ret.append("\n<option value=\"" + i +"\"");
                        if(Integer.parseInt(s_day_11) == i){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;&nbsp;<select name=\"wb_year_12\"");
                    if(s_rep_type.equals("0")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for(int i=2000;i<2021;i++){
                        sb_ret.append("\n<option value=\"" + i +"\"");
                        if(Integer.parseInt(s_year_12) == i){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_12\"");
                    if(s_rep_type.equals("0")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for(int i=0; i<= arr_month.length - 1;i++){
                        sb_ret.append("\n<option value=\"" + (i + 1) +"\"");
                        if(Integer.parseInt(s_month_12) == (i + 1)){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + arr_month[i] + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_12\"");
                    if(s_rep_type.equals("0")){
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for(int i=1;i<32;i++){
                        sb_ret.append("\n<option value=\"" + i +"\"");
                        if(Integer.parseInt(s_day_12) == i){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td colspan=\"4\" align=\"left\">");
                    if(request.getParameter("wb_rep_type") == null){
                        sb_ret.append("&nbsp;");
                    }                    
                    else{
                        sb_ret.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">");                            
                        sb_ret.append("\n<tr>");
                        sb_ret.append("\n<td>");
                        response.getWriter().print(sb_ret.toString());
                        sb_ret.delete(0,sb_ret.length());
                        WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY_HTML;                                                
                        try {                            
                            //"C:/desarrollo/SWB4/swb/build/web/swbadmin/images/swb-logo-hor.jpg"
                            System.out.println("imagen swb="+SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
                            HashMap params = new HashMap();
                            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
                            params.put("site", filter.getSite());
                            JRResource jrResource = new JRHtmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                                                
                        sb_ret.append("\n</td>");
                        sb_ret.append("\n</tr>");
                        sb_ret.append("\n</table>");
                        sb_ret.append("<hr size=\"1\" noshade>");
                    }
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                }
                else{  //**********************  Monthly  ***********************
                    String s_year_13 = request.getParameter("wb_year_13");
                    if(s_year_13 == null) s_year_13 = Integer.toString(gc_now.get(Calendar.YEAR));

                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td><input type=\"hidden\" name=\"wb_rtype\" value=\"1\"></td>");
                    sb_ret.append("<td colspan=\"3\" class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;&nbsp;<select name=\"wb_year_13\">");
                    for(int i=2000;i<2021;i++){
                        sb_ret.append("\n<option value=\"" + i +"\"");
                        if(Integer.parseInt(s_year_13) == i){
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("</td>");
                    sb_ret.append("\n</tr>");                   
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td colspan=\"4\" align=\"left\">");
                    if(request.getParameter("wb_year_13") == null){
                        sb_ret.append("&nbsp;");
                    }
                    else{
                        if(!webSite.equals(null)){
                            sb_ret.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">");                         
                            sb_ret.append("\n<tr><td>\n");
                            response.getWriter().print(sb_ret.toString());
                            sb_ret.delete(0,sb_ret.length());                           
                            
                            WBAFilterReportBean filter = new WBAFilterReportBean();
                            filter.setSite(webSite);
                            filter.setIdaux(S_REPORT_IDAUX);
                            filter. setType(I_REPORT_TYPE);
                            filter.setYearI(Integer.parseInt(s_year_13));                            
                            JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                            JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_MONTHLY_HTML;                            
                            try {
                                JRResource jrResource = new JRHtmlResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                                jrResource.prepareReport();
                                jrResource.exportReport(response);                            
                            }catch (Exception e) {
                                throw new javax.servlet.ServletException(e);
                            }
                            
                            sb_ret.append("\n</td></tr>");                            
                            sb_ret.append("\n</table>");
                            sb_ret.append("<hr size=\"1\" noshade>");
                        }
                    }
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                }
                sb_ret.append("\n</table></form>");
            }
            else{   // There are not sites and displays a message
                sb_ret.append("\n<form method=\"Post\" class=\"box\" action=\"" + paramsRequest.getTopic().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
                sb_ret.append("\n<table border=0 width=\"100%\">");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n<td colspan=\"2\" align=\"center\" class=\"datos\">" + paramsRequest.getLocaleString("no_sites_found") + "</td>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n</table></form>");
            }
        }
        catch (Exception e){
            log.error("Error on method DoView() resource " + strRscType +  " with id " + base.getId(), e);
        }
        response.getWriter().print(sb_ret.toString());
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        Portlet base = getResourceBase();
        
        try {            
            String webSite = request.getParameter("wb_site");
            String rtype = request.getParameter("wb_rtype")==null ? "0" : request.getParameter("wb_rtype");
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            params.put("site", webSite);
            
            if(rtype.equals("0")) { // ********  Shows results by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY_GRAPH;                
                try {                    
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
            else { // ********  Shows results by each month                
                String s_year_13 = request.getParameter("wb_year_13");
                if(!webSite.equals(null)){
                    s_year_13 = request.getParameter("wb_year_13");
                    if(!s_year_13.equals(null)){
                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(webSite);
                        filter.setIdaux(S_REPORT_IDAUX);
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(Integer.parseInt(s_year_13));
                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_MONTHLY_GRAPH;                        
                        try {
                            JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);                            
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doRepExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"gar.xls\"");
        Portlet base = getResourceBase();
        
        try {
            String webSite = request.getParameter("wb_site");
            String rtype = request.getParameter("wb_rtype")==null ? "0" : request.getParameter("wb_rtype");
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            params.put("site", webSite);
            
            if(rtype.equals("0")) { // ********  Shows results by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
            else { // ********  Shows results by each month
                String s_year_13 = request.getParameter("wb_year_13");
                if(!webSite.equals(null)){
                    s_year_13 = request.getParameter("wb_year_13");
                    if(!s_year_13.equals(null)){
                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(webSite);
                        filter.setIdaux(S_REPORT_IDAUX);
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(Integer.parseInt(s_year_13));
                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_MONTHLY;                        
                        try {
                            JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);                            
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doRepXml(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException { 
        response.setContentType("text/xml;charset=iso-8859-1");
        Portlet base = getResourceBase();
        
        try{
            String webSite = request.getParameter("wb_site");
            String rtype = request.getParameter("wb_rtype")==null ? "0" : request.getParameter("wb_rtype");
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            params.put("site", webSite);
            
            if(rtype.equals("0")) { // ********  Shows results by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRXmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // ********  Shows results by each month
                String s_year_13 = request.getParameter("wb_year_13");
                if(!webSite.equals(null)){
                    s_year_13 = request.getParameter("wb_year_13");
                    if(!s_year_13.equals(null)){
                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(webSite);
                        filter.setIdaux(S_REPORT_IDAUX);
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(Integer.parseInt(s_year_13));
                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_MONTHLY;                        
                        try {
                            JRResource jrResource = new JRXmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);                            
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                    }
                }
            }
        }
        catch (Exception e){            
            log.error("Error on method doRepExcel() resource" + " " + strRscType + " " + "with id" + " " + base.getId(), e);
        }
    }
    
    public void doRepPdf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        Portlet base = getResourceBase();
        
        try {            
            String webSite = request.getParameter("wb_site");
            String rtype = request.getParameter("wb_rtype")==null ? "0" : request.getParameter("wb_rtype");
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            params.put("site", webSite);
            
            if(rtype.equals("0")) { // ********  Shows results by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }
            else { // ********  Shows results by each month
                String s_year_13 = request.getParameter("wb_year_13");
                if(!webSite.equals(null)){
                    s_year_13 = request.getParameter("wb_year_13");
                    if(!s_year_13.equals(null)){
                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(webSite);
                        filter.setIdaux(S_REPORT_IDAUX);
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(Integer.parseInt(s_year_13));

                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_MONTHLY;                        
                        try {
                            JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);                            
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
        }
    }
    
    public void doRepRtf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/rtf");
        response.setHeader("Content-Disposition", "inline; filename=\"gar.rtf\"");
        Portlet base = getResourceBase();

        try {
            String webSite = request.getParameter("wb_site");
            String rtype = request.getParameter("wb_rtype")==null ? "0" : request.getParameter("wb_rtype");
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            params.put("site", webSite);
            
            if(rtype.equals("0")) { // ********  Shows results by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // ********  Shows results by each month
                String s_year_13 = request.getParameter("wb_year_13");
                if(!webSite.equals(null)){
                    s_year_13 = request.getParameter("wb_year_13");
                    if(!s_year_13.equals(null)){
                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(webSite);
                        filter.setIdaux(S_REPORT_IDAUX);
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(Integer.parseInt(s_year_13));
                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_MONTHLY;                        
                        try {
                            JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);                            
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
        }
    }

    /**
     * @param paramsRequest
     * @return
     */    
    public String[] DoArrMonth(SWBParamRequest paramsRequest){
        String[] arr_month = new String[12];
        try{
            arr_month[0] = paramsRequest.getLocaleString("month_january");
            arr_month[1] = paramsRequest.getLocaleString("month_february");
            arr_month[2] = paramsRequest.getLocaleString("month_march");
            arr_month[3] = paramsRequest.getLocaleString("month_april");
            arr_month[4] = paramsRequest.getLocaleString("month_may");
            arr_month[5] = paramsRequest.getLocaleString("month_june");
            arr_month[6] = paramsRequest.getLocaleString("month_july");
            arr_month[7] = paramsRequest.getLocaleString("month_august");
            arr_month[8] = paramsRequest.getLocaleString("month_september");
            arr_month[9] = paramsRequest.getLocaleString("month_october");
            arr_month[10] = paramsRequest.getLocaleString("month_november");
            arr_month[11] = paramsRequest.getLocaleString("month_december");
        }catch (Exception e){
            log.error("Error on method DoArrMonth() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return arr_month;
    }

    public WBAFilterReportBean buildFilter(HttpServletRequest request, SWBParamRequest paramsRequest){
        WBAFilterReportBean filterReportBean = null;
        GregorianCalendar gc_now = new GregorianCalendar();

        // Receive parameters
        String webSite = request.getParameter("wb_site");
        String rtype = request.getParameter("wb_rtype");

        String s_rep_type = request.getParameter("wb_rep_type");
        String s_year_1 = request.getParameter("wb_year_1");
        String s_month_1 = request.getParameter("wb_month_1");
        String s_day_1 = request.getParameter("wb_day_1");
        String s_year_11 = request.getParameter("wb_year_11");
        String s_month_11 = request.getParameter("wb_month_11");
        String s_day_11 = request.getParameter("wb_day_11");
        String s_year_12 = request.getParameter("wb_year_12");
        String s_month_12 = request.getParameter("wb_month_12");
        String s_day_12 = request.getParameter("wb_day_12");
        // Receive parameters

        // Asign value to parameters
        if(s_rep_type == null) s_rep_type = "0";
        if(s_year_1 == null) s_year_1 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_1 == null) s_month_1 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_day_1 == null) s_day_1 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
        if(s_year_11 == null) s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_11 == null) s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_day_11 == null) s_day_11 = Integer.toString(I_START_DAY);
        if(s_year_12 == null) s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_12 == null) s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_day_12 == null) s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
        // Asign value to  parameters

        if(rtype == null) rtype="0";

        try{
            String s_barname = request.getParameter("wbr_barname");
            String s_year = request.getParameter("wb_year");
            int i_month = 0;
            if(s_barname != null){
                String[] arr_month = DoArrMonth(paramsRequest);
                for(int i=0; i<= arr_month.length - 1;i++){
                    if(s_barname.equals(arr_month[i])){
                        i_month = i;
                        break;
                    }
                }
                s_rep_type = "1";
                s_year_11 = s_year;
                s_month_11 = Integer.toString(i_month + 1);
                s_day_11 = "1";
                s_year_12 = s_year;
                s_month_12 = Integer.toString(i_month + 1);
                s_day_12 = Integer.toString(getDaysMonth(s_year,s_month_11));
            }

            if(s_rep_type.equals("0")){ // radio button was 0
                if(s_year_1.equals("0")){
                    // Select all
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(webSite);
                    filterReportBean.setIdaux(S_REPORT_IDAUX);
                    filterReportBean. setType(I_REPORT_TYPE);
                }
                else{
                    // Select by speceific year or month or day
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(webSite);
                    filterReportBean.setIdaux(S_REPORT_IDAUX);
                    filterReportBean. setType(I_REPORT_TYPE);                            
                    filterReportBean.setYearI(Integer.parseInt(s_year_1));
                    filterReportBean.setMonthI(Integer.parseInt(s_month_1));
                    filterReportBean.setDayI(Integer.parseInt(s_day_1));
                }
            }
            else{                       // radio button was 1
                // Select between two dates
                filterReportBean = new WBAFilterReportBean();
                filterReportBean.setSite(webSite);
                filterReportBean.setIdaux(S_REPORT_IDAUX);
                filterReportBean.setType(I_REPORT_TYPE);                            
                filterReportBean.setYearI(Integer.parseInt(s_year_11));
                filterReportBean.setMonthI(Integer.parseInt(s_month_11));
                filterReportBean.setDayI(Integer.parseInt(s_day_11));
                filterReportBean.setYearF(Integer.parseInt(s_year_12));
                filterReportBean.setMonthF(Integer.parseInt(s_month_12));
                filterReportBean.setDayF(Integer.parseInt(s_day_12));
            }
        }
        catch (Exception e){
            log.error("Error on method getReportResults() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return filterReportBean;
    }
    
    /**
     * @param request
     * @param paramsRequest
     * @return
     */    
    public ArrayList getReportResults(HttpServletRequest request, SWBParamRequest paramsRequest){
        Iterator iterHits = null;
        ArrayList al_pag = new ArrayList();
        GregorianCalendar gc_now = new GregorianCalendar();

        // Receive parameters
        String webSite = request.getParameter("wb_site");
        String rtype = request.getParameter("wb_rtype");

        String s_rep_type = request.getParameter("wb_rep_type");
        String s_year_1 = request.getParameter("wb_year_1");
        String s_month_1 = request.getParameter("wb_month_1");
        String s_day_1 = request.getParameter("wb_day_1");
        String s_year_11 = request.getParameter("wb_year_11");
        String s_month_11 = request.getParameter("wb_month_11");
        String s_day_11 = request.getParameter("wb_day_11");
        String s_year_12 = request.getParameter("wb_year_12");
        String s_month_12 = request.getParameter("wb_month_12");
        String s_day_12 = request.getParameter("wb_day_12");
        // Receive parameters

        // Asign value to parameters
        if(s_rep_type == null) s_rep_type = "0";
        if(s_year_1 == null) s_year_1 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_1 == null) s_month_1 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_day_1 == null) s_day_1 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
        if(s_year_11 == null) s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_11 == null) s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_day_11 == null) s_day_11 = Integer.toString(I_START_DAY);
        if(s_year_12 == null) s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_12 == null) s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_day_12 == null) s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
        // Asign value to  parameters

        if(rtype == null) rtype="0";

        try{
            String s_barname = request.getParameter("wbr_barname");
            String s_year = request.getParameter("wb_year");
            int i_month = 0;
            if(s_barname != null){
                String[] arr_month = DoArrMonth(paramsRequest);
                for(int i=0; i<= arr_month.length - 1;i++){
                    if(s_barname.equals(arr_month[i])){
                        i_month = i;
                        break;
                    }
                }
                s_rep_type = "1";
                s_year_11 = s_year;
                s_month_11 = Integer.toString(i_month + 1);
                s_day_11 = "1";
                s_year_12 = s_year;
                s_month_12 = Integer.toString(i_month + 1);
                s_day_12 = Integer.toString(getDaysMonth(s_year,s_month_11));
            }

            if(s_rep_type.equals("0")){ // radio button was 0
                if(s_year_1.equals("0")){
                    // Select all
//                    iterHits = DBResHits.getInstance().getResHitsLog(webSite,S_REPORT_IDAUX,I_REPORT_TYPE);
                }
                else{
                    // Select by speceific year or month or day
//                    iterHits = DBResHits.getInstance().getResHitsLog(webSite,S_REPORT_IDAUX,I_REPORT_TYPE,Integer.parseInt(s_year_1),Integer.parseInt(s_month_1),Integer.parseInt(s_day_1));
                }
            }
            else{                       // radio button was 1
                // Select between two dates
//                iterHits = DBResHits.getInstance().getResHitsLog(webSite,S_REPORT_IDAUX,I_REPORT_TYPE,Integer.parseInt(s_year_11),Integer.parseInt(s_month_11),Integer.parseInt(s_day_11),Integer.parseInt(s_year_12),Integer.parseInt(s_month_12),Integer.parseInt(s_day_12));
            }

            // Init paging parameters
            while(iterHits.hasNext()){
                al_pag.add(iterHits.next());
            }
        }
        catch (Exception e){
            log.error("Error on method getReportResults() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return al_pag;
    }

    public int getDaysMonth(String p_year, String p_month){
        int i_tot = 0;
        int i_year = Integer.parseInt(p_year);
        int i_month = Integer.parseInt(p_month);
        GregorianCalendar gc_year = new GregorianCalendar(i_year,i_month,Integer.parseInt("1"));
        boolean b_leap = gc_year.isLeapYear(i_year);

        if(i_month == 1) i_tot = 31;
        if(i_month == 2){
            if(b_leap) i_tot = 29;
                else  i_tot = 28;
        }
        if(i_month == 3) i_tot = 31;
        if(i_month == 4) i_tot = 30;
        if(i_month == 5) i_tot = 31;
        if(i_month == 6) i_tot = 30;
        if(i_month == 7) i_tot = 31;
        if(i_month == 8) i_tot = 31;
        if(i_month == 9) i_tot = 30;
        if(i_month == 10) i_tot = 31;
        if(i_month == 11) i_tot = 30;
        if(i_month == 12) i_tot = 31;

        return i_tot;
    }
}