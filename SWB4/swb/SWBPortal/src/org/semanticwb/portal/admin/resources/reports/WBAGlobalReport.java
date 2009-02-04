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
import org.semanticwb.model.Portlet;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.jrresources.*;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRGlobalAccessDataDetail;
    
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

    private static final String S_REPORT_IDAUX = "_";
    private static final ArrayList idaux = new ArrayList(1);
    private static final int I_REPORT_TYPE = 0;

    public String strRscType;
    
    static {
        idaux.add(S_REPORT_IDAUX);            
    }

    @Override
    public void init(){
        Portlet base = getResourceBase();        
        try {
            strRscType = base.getPortletType().getPortletClassName();
        }catch (Exception e) {
            strRscType = "WBAGlobalReport";
        }
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
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState())) {
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
        if(paramsRequest.getMode().equals("graph")) {
            doGraph(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_excel")) {
            doRepExcel(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_xml")) {
            doRepXml(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_pdf")) {
            doRepPdf(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_rtf")) {
            doRepRtf(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
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
        Portlet base = getResourceBase();
        
        final int I_ACCESS = 0;        
        StringBuffer sb_ret = new StringBuffer();
        GregorianCalendar gc_now = new GregorianCalendar();
        HashMap hm_sites = new HashMap();
        int i_access = 0;
        String rtype;

        try{
            // Evaluates if there are sites
            Iterator<WebSite> webSites = SWBContext.listWebSites();
            while(webSites.hasNext()) {
                WebSite site = webSites.next();
                // Evaluates if TopicMap is not Global
                if(!site.getId().equals(SWBContext.getGlobalWebSite().getId())) {
                    // Get access level of this user on this topicmap and if level is greater than "0" then user have access
                    // TODO
//                    i_access = AdmFilterMgr.getInstance().haveAccess2TopicMap(paramsRequest.getUser(),site.getDbdata().getId());
//                    if(I_ACCESS < i_access) {
//                        if(site.getDbdata().getDeleted()==0) {
                            hm_sites.put(site.getId(), site.getTitle());
//                        }
//                    }
                }
            }
            // If there are sites continue
            if(hm_sites.size() > I_ACCESS){
                String address = paramsRequest.getTopic().getUrl();
                String webSite = request.getParameter("wb_site");
                int groupDates;
                try {
                    groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
                }catch(NumberFormatException e) {
                    groupDates = 0;
                }
                String fecha1 = request.getParameter("wb_fecha1")==null ? "":request.getParameter("wb_fecha1");
                String fecha11 = request.getParameter("wb_fecha11")==null ? "":request.getParameter("wb_fecha11"); 
                String fecha12 = request.getParameter("wb_fecha12")==null ? "":request.getParameter("wb_fecha12");
                
                String topicId = paramsRequest.getTopic().getId();
                if(topicId.lastIndexOf("Daily") != -1) {
                    rtype = "0";
                }else if(topicId.lastIndexOf("Monthly") != -1) {
                    rtype = "1";
                }else {
                    rtype = request.getParameter("wb_rtype");
                }
                if(rtype == null) {
                    rtype = "0";
                }
                
                // javascript
                sb_ret.append("\n<link type=\"text/css\" href=\"/swb/swbadmin/js/jquery/themes/ui.all.css\" rel=\"Stylesheet\" />");

                sb_ret.append("\n<script type=\"text/javascript\" src=\"/swb/swbadmin/js/jquery/jquery-1.3.js\"></script>");
                sb_ret.append("\n<script type=\"text/javascript\" src=\"/swb/swbadmin/js/jquery/jquery.ui.all.js\"></script>");
                sb_ret.append("\n<script type=\"text/javascript\" src=\"/swb/swbadmin/js/jquery/ui/i18n/ui.datepicker-pt-BR.js\"></script>");
                sb_ret.append("\n<script type=\"text/javascript\" src=\"/swb/swbadmin/js/jquery/ui/i18n/ui.datepicker-es.js\"></script>");
                sb_ret.append("\n<script type=\"text/javascript\">");
                sb_ret.append("\n   $(function() {");
                sb_ret.append("\n       $(\"#wb_fecha1\").datepicker({changeMonth:true, changeYear:true, showOn:'button', buttonImage:'/swb/swbadmin/images/calendar.gif', buttonImageOnly:true}, $.datepicker.regional['es']);");
                sb_ret.append("\n       $(\"#wb_fecha11\").datepicker({changeMonth:true, changeYear:true, showOn:'button', buttonImage:'/swb/swbadmin/images/calendar.gif', buttonImageOnly:true}, $.datepicker.regional['es']);");
                sb_ret.append("\n       $(\"#wb_fecha12\").datepicker({changeMonth:true, changeYear:true, showOn:'button', buttonImage:'/swb/swbadmin/images/calendar.gif', buttonImageOnly:true}, $.datepicker.regional['es']);");
                sb_ret.append("\n   });");
                sb_ret.append("\n</script>");
                
                
                sb_ret.append("\n<script type=\"text/javascript\">");
                
                sb_ret.append("\nfunction getParams(accion) { ");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.value;");
                sb_ret.append("\n   params = params + \"&wb_rtype=\" + document.getElementById('wb_rtype').value;");
                sb_ret.append("\n   if(accion == 0) {");                    
                sb_ret.append("\n       params = params + \"&wb_rep_type=\" + GetTypeSelected();");
                sb_ret.append("\n       params = params + \"&wb_fecha1=\" + document.getElementById('wb_fecha1').value; ");
                sb_ret.append("\n       params = params + \"&wb_fecha11=\" + document.getElementById('wb_fecha11').value; ");
                sb_ret.append("\n       params = params + \"&wb_fecha12=\" + document.getElementById('wb_fecha12').value; ");                    

                sb_ret.append("\n   }else {");
                sb_ret.append("\n       params = params + \"&wb_year13=\" + document.getElementById('wb_year13').options[document.getElementById('wb_year13').selectedIndex].value;");
                sb_ret.append("\n   }");
                sb_ret.append("\n   return params;");
                sb_ret.append("\n} ");
                
                sb_ret.append("\nfunction DoXml(accion, size) { ");
                sb_ret.append("\n   var params = getParams(accion);");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml")+"\"+params,\"graphWindow\",size);    ");
                sb_ret.append("\n}");
                
                sb_ret.append("\nfunction DoExcel(accion, size) { ");
                sb_ret.append("\n   var params = getParams(accion);");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel")+"\"+params,\"graphWindow\",size);    ");
                sb_ret.append("\n}");
                
                sb_ret.append("\nfunction DoGraph(accion, size) { ");
                sb_ret.append("\n   var params = getParams(accion);");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("graph")+"\"+params,\"graphWindow\",size);    ");
                sb_ret.append("\n }");
                
                sb_ret.append("\nfunction DoPdf(accion, size) { ");
                sb_ret.append("\n   var params = getParams(accion);");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_pdf")+"\"+params,\"graphWindow\",size);    ");
                sb_ret.append("\n}");
                
                sb_ret.append("\nfunction DoRtf(accion, size) { ");
                sb_ret.append("\n   var params = getParams(accion);");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_rtf")+"\"+params,\"graphWindow\",size);    ");
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
                
                sb_ret.append("\n function DoApply() { ");
                sb_ret.append("\n     window.document.frmrep.submit(); ");
                sb_ret.append("\n }");                

                sb_ret.append("\n function DoBlockade() {");
//                sb_ret.append("\n     if(window.document.frmrep.wb_rep_type[0].checked){");
//                sb_ret.append("\n         window.document.frmrep.wb_year_1.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_year_11.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_year_12.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_month_11.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_month_12.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_day_11.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_day_12.disabled = true;");
//                sb_ret.append("\n     }");
//                sb_ret.append("\n     if(window.document.frmrep.wb_rep_type[1].checked){");
//                sb_ret.append("\n         window.document.frmrep.wb_year_1.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = true;");
//                sb_ret.append("\n         window.document.frmrep.wb_year_11.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_year_12.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_month_11.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_month_12.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_day_11.disabled = false;");
//                sb_ret.append("\n         window.document.frmrep.wb_day_12.disabled = false;");
//                sb_ret.append("\n     }");
                sb_ret.append("\n }");
                sb_ret.append("\n</script>");
                
                sb_ret.append("\n<div id=\"swb-admin\">");
                sb_ret.append("\n<fieldset>");
                sb_ret.append("\n<legend>" + paramsRequest.getLocaleString("global_report") + "</legend>");
                
                sb_ret.append("\n<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                sb_ret.append("\n<table border=\"0\" width=\"95%\" align=\"center\">");
                sb_ret.append("\n<tr><td width=\"100\"></td><td width=\"120\"></td><td></td><td></td></tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("\n<td colspan=4>");
                if(rtype.equals("0")){
                    sb_ret.append(paramsRequest.getLocaleString("description_daily"));
                }
                else{
                    sb_ret.append(paramsRequest.getLocaleString("description_monthly"));
                }
                sb_ret.append("\n</td>");
                sb_ret.append("\n</tr>");
                
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");                
                sb_ret.append("\n<tr>");
                sb_ret.append("\n <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                sb_ret.append("   <input type=\"button\" onClick=\"DoXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\" />&nbsp;");
                sb_ret.append("   <input type=\"button\" onClick=\"DoExcel('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\" name=\"btnExcel\" />&nbsp;");                
                sb_ret.append("   <input type=\"button\" onClick=\"DoPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"PDF\" name=\"btnPdf\" />&nbsp;");
                sb_ret.append("   <input type=\"button\" onClick=\"DoRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"RTF\" name=\"btnRtf\" />&nbsp;");                
                sb_ret.append("   <input type=\"button\" onClick=\"DoGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\" value=\"" + paramsRequest.getLocaleString("graph") + "\" name=\"btnGraph\" />&nbsp;");
                sb_ret.append("   <input type=\"button\" onClick=\"DoApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\" />");
                sb_ret.append("\n </td>");
                sb_ret.append("\n</tr>");                
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                
                sb_ret.append("\n<tr>");
                sb_ret.append("<td>" + paramsRequest.getLocaleString("site") + ":</td>");
                sb_ret.append("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\">");
                Iterator<String> itKeys = hm_sites.keySet().iterator();                    
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    sb_ret.append("\n<option value=\"" + key + "\"");
                    if(key.equalsIgnoreCase(webSite)) {
                        sb_ret.append(" selected=\"selected\"");
                    }
                    sb_ret.append(">" + (String)hm_sites.get(key) + "</option>");
                }                    
                sb_ret.append("</select>");
                sb_ret.append("</td>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n</tr>");


                if(rtype.equals("0")) { // REPORTE DIARIO
                    sb_ret.append("\n<tr>");
                    sb_ret.append("\n<td>");
                    sb_ret.append("<label>");
                    sb_ret.append("<input type=\"radio\" value=\"0\" name=\"wb_rep_type\" id=\"wb_rep_type_0\" onclick=\"javascript: DoBlockade();\"");
                    if(groupDates==0) {
                        sb_ret.append(" checked=\"checked\"");
                    }
                    sb_ret.append(" />");
                    sb_ret.append("&nbsp;" + paramsRequest.getLocaleString("by_day"));
                    sb_ret.append("</label></td>");
                    sb_ret.append("\n<td colspan=\"2\">");
                    sb_ret.append("<input type=\"text\" id=\"wb_fecha1\" name=\"wb_fecha1\" size=\"10\" maxlength=\"10\" value=\"" + fecha1 + "\" />");                        
                    sb_ret.append("</td>");
                    sb_ret.append("<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"0\" /></td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("\n<td colspan=4>&nbsp;</td>");
                    sb_ret.append("\n</tr>");

                    sb_ret.append("\n<tr>");
                    sb_ret.append("\n<td>");
                    sb_ret.append("\n<label>");
                    sb_ret.append("\n<input type=\"radio\" value=\"1\" name=\"wb_rep_type\" id=\"wb_rep_type_1\" onclick=\"javascript: DoBlockade();\"");
                    if(groupDates!=0) {
                        sb_ret.append(" checked=\"checked\"");
                    }
                    sb_ret.append(" />");
                    sb_ret.append("&nbsp;" + paramsRequest.getLocaleString("by_interval_dates"));
                    sb_ret.append("</label></td>");
                    sb_ret.append("\n<td>");
                    sb_ret.append("<input type=\"text\" id=\"wb_fecha11\" name=\"wb_fecha11\" size=\"10\" maxlength=\"10\" value=\"" + fecha11 + "\" />");
                    sb_ret.append("</td>");
                    sb_ret.append("\n<td>");
                    sb_ret.append("<input type=\"text\" id=\"wb_fecha12\" name=\"wb_fecha12\" size=\"10\" maxlength=\"10\" value=\"" + fecha12 + "\" />");
                    sb_ret.append("</td>");
                    sb_ret.append("\n<td>&nbsp;</td>");
                    sb_ret.append("\n</tr>");
                    
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td colspan=\"4\" align=\"left\">");
                    if(request.getParameter("wb_rtype")==null || webSite==null ) {
                        sb_ret.append("&nbsp;");
                    }else {
                        sb_ret.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">");                            
                        sb_ret.append("\n<tr>");
                        sb_ret.append("\n<td>");
                        response.getWriter().print(sb_ret.toString());
                        sb_ret.delete(0,sb_ret.length());
                        WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY_HTML;
                        HashMap params = new HashMap();
                        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
                        params.put("site", filter.getSite());
                        try {
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
                }else { // REPORTE MENSUAL
                    int year13 = request.getParameter("wb_year13")==null ? gc_now.get(Calendar.YEAR):Integer.parseInt(request.getParameter("wb_year13"));
                    sb_ret.append("\n<tr>");
                    sb_ret.append("\n<td>" + paramsRequest.getLocaleString("year") + ":</td>");                    
                    sb_ret.append("\n<td colspan=\"2\"><select id=\"wb_year13\" name=\"wb_year13\">");
                    for (int i = 2000; i < 2021; i++) {
                        sb_ret.append("<option value=\"" + i + "\"");
                        if (year13==i) {
                            sb_ret.append(" selected=\"selected\"");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");                    
                    sb_ret.append("\n<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"1\" /></td>");                        
                    sb_ret.append("\n</tr>");
                    
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td colspan=\"4\">");
                    if(request.getParameter("wb_rtype")==null || webSite==null ) {
                        sb_ret.append("&nbsp;");
                    }else {
                        sb_ret.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                         
                        sb_ret.append("\n<tr><td>\n");
                        response.getWriter().print(sb_ret.toString());
                        sb_ret.delete(0,sb_ret.length());                           

                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(webSite);
                        filter.setIdaux(idaux.iterator());
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(year13);                            
                        JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_MONTHLY_HTML;
                        HashMap params = new HashMap();
                        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
                        params.put("site", filter.getSite());
                        try {
                            JRResource jrResource = new JRHtmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                            jrResource.prepareReport();
                            jrResource.exportReport(response);                            
                        }catch (Exception e) {
                            throw new javax.servlet.ServletException(e);
                        }
                        sb_ret.append("\n</td></tr>");                            
                        sb_ret.append("\n</table>");
                        sb_ret.append("<hr size=\"1\" noshade>");
                    }
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                }
                sb_ret.append("\n</table></form>");
                sb_ret.append("\n</fieldset></div>");
            }
            else{   // There are not sites and displays a message
                sb_ret.append("\n<form method=\"Post\" action=\"" + paramsRequest.getTopic().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
                sb_ret.append("\n<table border=0 width=\"100%\">");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n<td colspan=\"2\" align=\"center\">" + paramsRequest.getLocaleString("no_sites_found") + "</td>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n</table></form>");
            }
        }
        catch (Exception e){
            log.error("Error on method doView() resource " + strRscType +  " with id " + base.getId(), e);
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
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY_GRAPH;                
                try {                    
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String webSite = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", webSite);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSite);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
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
        catch (Exception e){
            log.error("Error on method doGraph() resource " + strRscType + " with id " + base.getId(), e);
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
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String webSite = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", webSite);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSite);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
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
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRXmlResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String webSite = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", webSite);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSite);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
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
        catch (Exception e){            
            log.error("Error on method doRepExcel() resource" + " " + strRscType + " " + "with id" + " " + base.getId(), e);
        }
    }
    
    public void doRepPdf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        Portlet base = getResourceBase();        
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String webSite = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", webSite);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSite);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
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
        catch (Exception e){
            log.error("Error on method doRepPdf() resource " + strRscType + " with id " + base.getId(), e);
        }
    }
    
    public void doRepRtf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/rtf");
        response.setHeader("Content-Disposition", "inline; filename=\"gar.rtf\"");
        Portlet base = getResourceBase();
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            HashMap params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                params.put("site", filter.getSite());
                JRDataSourceable dataDetail = new JRGlobalAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.GLOBAL_DAILY;                
                try {
                    JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                    jrResource.prepareReport();
                    jrResource.exportReport(response);                            
                }catch (Exception e) {
                    throw new javax.servlet.ServletException(e);
                }
            }else { // REPORTE MENSUAL
                String webSite = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");                
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                params.put("site", webSite);
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSite);
                filter.setIdaux(idaux.iterator());
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
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
        catch (Exception e){
            log.error("Error on method doRepRtf() resource " + strRscType + " with id " + base.getId(), e);
        }
    }

    public WBAFilterReportBean buildFilter(HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException, IncompleteFilterException {
        WBAFilterReportBean filterReportBean = null;        
        String webSite = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
        int groupDates;
        try {
            groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
        }catch(NumberFormatException e) {
            groupDates = 0;
        }
        String fecha1 = request.getParameter("wb_fecha1");
        String fecha11 = request.getParameter("wb_fecha11");
        String fecha12 = request.getParameter("wb_fecha12");        
        if(groupDates==0 && fecha1==null) {
            throw new IncompleteFilterException("Falta la fecha");
        }
        if(groupDates==1 && (fecha11==null || fecha12==null)) {
            throw new IncompleteFilterException("Faltan las fechas");
        }

        try {
            if(groupDates==0) { // radio button was 0. Select only one date
                String[] numFecha = fecha1.split("/");
                filterReportBean = new WBAFilterReportBean();
                filterReportBean.setSite(webSite);
                filterReportBean.setIdaux(idaux.iterator());
                filterReportBean.setType(I_REPORT_TYPE);
                filterReportBean.setYearI(Integer.parseInt(numFecha[2]));
                filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayI(Integer.parseInt(numFecha[0]));
                
            }else { // radio button was 1. Select between two dates
                filterReportBean = new WBAFilterReportBean();
                filterReportBean.setSite(webSite);
                filterReportBean.setIdaux(idaux.iterator());
                filterReportBean.setType(I_REPORT_TYPE);
                String[] numFecha = fecha11.split("/");
                filterReportBean.setYearI(Integer.parseInt(numFecha[2]));
                filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayI(Integer.parseInt(numFecha[0]));

                numFecha = fecha12.split("/");
                filterReportBean.setYearF(Integer.parseInt(numFecha[2]));
                filterReportBean.setMonthF(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayF(Integer.parseInt(numFecha[0]));
            }
        }
        catch (Exception e){
            log.error("Error on method buildFilter() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return filterReportBean;
    }

}