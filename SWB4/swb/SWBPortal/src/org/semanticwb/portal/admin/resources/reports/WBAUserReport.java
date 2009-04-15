/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (?open source?),
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
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.jrresources.*;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRUserTypesAccessDataDetail;


/** Esta clase genera el reporte de usuarios, toma la informaci�n de los
 * objetos de WebBuilder de acuerdo con los par�metros recibidos del usuario. Este
 * archivo es usado en la parte de reportes.
 *
 * This class generates the user report, takes information from
 * WebBuilder objects according with user parameters. this file is used in report
 * sections.
 *
 * WBAUserReport.java
 * Created on November 3th, 2004 6:30 PM
 * @author Jorge R�os - IDT
 */
public class WBAUserReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBAUserReport.class);
    private final int I_REPORT_TYPE = 4;   // Type 4 of reports "User type access"
    public String strRscType;   
    
    @Override
    public void init() {
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBALanguageReport";
        }
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState())) {
            processRequest(request, response, paramsRequest);
        }
    }
    
    public void doBind(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        System.out.println("inicia doBind");
        response.setContentType("text/json;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        
        out.println("<select id=\"wb_usertype\" name=\"wb_usertype\" size=\"1\">");
        Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();
        while(itUserTypes.hasNext()) {
            String key = itUserTypes.next();
            out.println("<option value=\"" + key + "\">" + key + "</option>");
        }                    
        out.println("</select>");
        out.flush();
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException 
     * @throws IOException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("bind")) {
            doBind(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("graph")) {
            doGraph(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_excel")) {
            doRepExcel(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_xml")) {
            doRepXml(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_pdf")){
            doRepPdf(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_rtf")){
            doRepRtf(request,response,paramsRequest);
        }else super.processRequest(request, response, paramsRequest);
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
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        
        ArrayList idaux = new ArrayList();        
        
        final int I_ACCESS = 0;
        
        HashMap hm_sites = new HashMap();
        String rtype = null;
        
        try {
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
            if(hm_sites.size() > I_ACCESS) {   
                String address = paramsRequest.getTopic().getUrl();
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
                String userTypeId = request.getParameter("wb_usertype");
                
                int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }
                
                int groupDates;
                try {
                    groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
                }catch(NumberFormatException e) {
                    groupDates = 0;
                }
                String fecha1 = request.getParameter("wb_fecha1")==null ? "":request.getParameter("wb_fecha1");
                String fecha11 = request.getParameter("wb_fecha11")==null ? "":request.getParameter("wb_fecha11"); 
                String fecha12 = request.getParameter("wb_fecha12")==null ? "":request.getParameter("wb_fecha12");
                SWBResourceURL url = paramsRequest.getRenderUrl();                
                
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
                
                out.println("<script type=\"text/javascript\">");                
                
                out.println("dojo.require(\"dijit.form.DateTextBox\");");
                out.println("dojo.require(\"dijit.form.ComboBox\");");
                out.println("dojo.addOnLoad(doBlockade);");
                out.println("dojo.addOnLoad(function(){getHtml('"+url.toString()+"'+'?site="+webSiteId+"','slave')});");
                
                out.println("function getParams(accion) {");
                out.println("   var params = '?';");
                out.println("   params = params + 'wb_site=' + dojo.byId('wb_site').value;");
                out.println("   params = params + '&wb_usertype=' + dojo.byId('wb_usertype').value;");
                out.println("   params = params + '&wb_rtype=' + dojo.byId('wb_rtype').value;");
                out.println("   if(accion == 0) {");
                out.println("       params = params + '&wb_rep_type=' + getTypeSelected();");
                out.println("       params = params + '&wb_fecha1=' + dojo.byId('wb_fecha1').value;");
                out.println("       params = params + '&wb_fecha11=' + dojo.byId('wb_fecha11').value;");
                out.println("       params = params + '&wb_fecha12=' + dojo.byId('wb_fecha12').value;");
                out.println("   }else {");
                out.println("       params = params + '&wb_year13=' + dojo.byId('wb_year13').options[dojo.byId('wb_year13').selectedIndex].value;");
                out.println("   }");
                out.println("   return params;");
                out.println("}");
                
                out.println("function doXml(accion, size) { ");
                out.println("   var params = getParams(accion);");
                out.println("   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml")+"\"+params,\"graphWindow\",size);    ");
                out.println("}");
                
                out.println("function doExcel(accion, size) { ");
                out.println("   var params = getParams(accion);");
                out.println("   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel")+"\"+params,\"graphWindow\",size);    ");
                out.println("}");
                
                out.println("function doGraph(accion, size) { ");
                out.println("   var params = getParams(accion);");
                out.println("   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("graph")+"\"+params,\"graphWindow\",size);    ");
                out.println(" }");
                
                out.println("function doPdf(accion, size) { ");
                out.println("   var params = getParams(accion);");
                out.println("   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_pdf")+"\"+params,\"graphWindow\",size);    ");
                out.println("}");
                
                out.println("function doRtf(accion, size) { ");
                out.println("   var params = getParams(accion);");
                out.println("   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_rtf")+"\"+params,\"graphWindow\",size);    ");
                out.println("}");
                
                out.println(" function getTypeSelected(){");
                out.println("     var strType = \"0\";");
                out.println("     for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){");
                out.println("       if(window.document.frmrep.wb_rep_type[i].checked==true){");
                out.println("           strType=window.document.frmrep.wb_rep_type[i].value;");
                out.println("       }");
                out.println("     }");
                out.println("     return strType;");
                out.println(" }");
                
                out.println(" function doApply() { ");                
                out.println("     window.document.frmrep.submit(); ");
                out.println(" }");                

                out.println(" function doBlockade() {");
                out.println("     if(window.document.frmrep.wb_rep_type[0].checked){");
                out.println("       dojo.byId('wb_fecha1').disabled = false;");
                out.println("       dojo.byId('wb_fecha11').disabled = true;");
                out.println("       dojo.byId('wb_fecha12').disabled = true;");                
                out.println("     }");
                out.println("     if(window.document.frmrep.wb_rep_type[1].checked){");
                out.println("       dojo.byId('wb_fecha1').disabled = true;");
                out.println("       dojo.byId('wb_fecha11').disabled = false;");
                out.println("       dojo.byId('wb_fecha12').disabled = false;");
                out.println("     }");
                out.println(" }");
                
                out.println("</script>");
                // javascript
                
                out.println("<div id=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("user_report") + "</legend>");
                
                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
                out.println("<tr><td width=\"100\"></td><td width=\"120\"></td><td></td><td></td></tr>");
                out.println("<tr>");
                out.println("<td colspan=\"4\">");
                // Show report description
                if(rtype.equals("0")) {
                    out.println(paramsRequest.getLocaleString("description_daily"));
                }else {
                    out.println(paramsRequest.getLocaleString("description_monthly"));
                }                    
                out.println("</td></tr>");

                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("<tr>");
                out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doExcel('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\" name=\"btnExcel\" />&nbsp;");                
                out.println("   <input type=\"button\" onClick=\"doPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"PDF\" name=\"btnPdf\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"RTF\" name=\"btnRtf\" />&nbsp;");                
                out.println("   <input type=\"button\" onClick=\"doGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\" value=\"" + paramsRequest.getLocaleString("graph") + "\" name=\"btnGraph\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\" />");
                out.println(" </td>");
                out.println("</tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("site") + ":</td>");
                out.println("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" onchange=\"getHtml('"+url.toString()+"'+'?site='+this.value,'slave');\">");
                Iterator<String> itKeys = hm_sites.keySet().iterator();                    
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    out.println("<option value=\"" + key + "\"");
                    if(key.equalsIgnoreCase(webSiteId)) {
                        out.println(" selected=\"selected\"");
                    }
                    out.println(">" + (String)hm_sites.get(key) + "</option>");
                }                    
                out.println("</select>");
                out.println("</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("user_type") + ":</td>");
                out.println("<td colspan=\"2\"><div id=\"slave\"></div>");
                if(deleteFilter==1) {                    
                    out.println("<script type=\"text/javascript\">dojo.byId('wb_device').disabled=true;</script>");
                }
                out.println("</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td colspan=\"4\">");                
                out.println(paramsRequest.getLocaleString("all_types") + "&nbsp;&nbsp;");
                out.println("<input type=\"checkbox\" id=\"wb_deletefilter\" name=\"wb_deletefilter\" value=\"1\" onclick=\"dojo.byId('wb_usertype').disabled=!(dojo.byId('wb_usertype').disabled);\"");
                if(deleteFilter==1) {
                    out.println(" checked=\"checked\"");
                }
                out.println(" />");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                if(rtype.equals("0")) { // REPORTE DIARIO
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<label>");
                    out.println("<input type=\"radio\" value=\"0\" name=\"wb_rep_type\" id=\"wb_rep_type_0\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates==0) {
                        out.println(" checked=\"checked\"");
                    }
                    out.println(" />");
                    out.println("&nbsp;" + paramsRequest.getLocaleString("by_day"));
                    out.println("</label></td>");
                    out.println("<td colspan=\"2\">");
                    out.println("<input type=\"text\" name=\"wb_fecha1\" id=\"wb_fecha1\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha1+"\">");
                    //out.println("<input type=\"text\" id=\"wb_fecha1\" name=\"wb_fecha1\" size=\"10\" maxlength=\"10\" value=\"" + fecha1 + "\" />");                        
                    out.println("</td>");
                    out.println("<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"0\" /></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td colspan=4>&nbsp;</td>");
                    out.println("</tr>");

                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<label>");
                    out.println("<input type=\"radio\" value=\"1\" name=\"wb_rep_type\" id=\"wb_rep_type_1\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates!=0) {
                        out.println(" checked=\"checked\"");
                    }
                    out.println(" />");
                    out.println("&nbsp;" + paramsRequest.getLocaleString("by_interval_dates"));
                    out.println("</label></td>");
                    out.println("<td>");
                    out.println("<input type=\"text\" name=\"wb_fecha11\" id=\"wb_fecha11\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha11+"\">");
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<input type=\"text\" name=\"wb_fecha12\" id=\"wb_fecha12\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha12+"\">");
                    out.println("</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("</tr>");

                    out.println("<tr>");
                    out.println("<td colspan=\"4\">");
                    if(request.getParameter("wb_rtype")==null || webSiteId==null ) {
                        out.println("&nbsp;");
                    }else {
                        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                            
                        out.println("<tr>");
                        out.println("<td>");

                        WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                        JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_DAILY_HTML;
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

                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("<hr size=\"1\" noshade>");
                    }
                    out.println("</td>");
                    out.println("</tr>");
                }else { // REPORTE MENSUAL
                    GregorianCalendar gc_now = new GregorianCalendar();
                    int year13 = request.getParameter("wb_year13")==null ? gc_now.get(Calendar.YEAR):Integer.parseInt(request.getParameter("wb_year13"));
                    out.println("<tr>");
                    out.println("<td colspan=\"3\">" + paramsRequest.getLocaleString("year") + ":&nbsp;&nbsp;<select id=\"wb_year13\" name=\"wb_year13\">");
                    for (int i = 2000; i < 2021; i++) {
                        out.println("<option value=\"" + i + "\"");
                        if (year13==i) {
                            out.println(" selected=\"selected\"");
                        }
                        out.println(">" + i + "</option>");
                    }
                    out.println("</select>");
                    out.println("</td>");
                    out.println("<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"1\" /></td>");                        
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<td colspan=\"4\">");
                    if(request.getParameter("wb_rtype")==null || webSiteId==null ) {
                        out.println("&nbsp;");
                    }else {
                        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                            
                        out.println("<tr>");
                        out.println("<td>");
                        /*response.getWriter().print(sb_ret.toString());
                        sb_ret.delete(0,sb_ret.length());*/

                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(webSiteId);
                        /*Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();*/
                        if(deleteFilter==0) {
                            /*while(itUserTypes.hasNext()) {
                                String val = itUserTypes.next();
                                if(val.equalsIgnoreCase(userTypeId)) {                                        
                                    idaux.add(val);
                                    filter.setIdaux(idaux.iterator());
                                    break;
                                }
                            }*/
                            filter.setIdaux(userTypeId);
                        }/*else {
                            filter.setIdaux(itUserTypes);
                        }*/
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(year13);
                        JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_MONTHLY_HTML;
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
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("<hr size=\"1\" noshade>");
                    }
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");                    
                out.println("</form>");
                out.println("</fieldset></div>");
            }else { // There are not sites and displays a message
                out.println("<form method=\"Post\" action=\"" + paramsRequest.getTopic().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
                out.println("<table border=0 width=\"100%\">");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("<tr>");
                out.println("<td>&nbsp;</td>");
                out.println("<td colspan=\"2\" align=\"center\">" + paramsRequest.getLocaleString("no_sites_found") + "</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("</table></form>");
            }
        } catch (Exception e) {
            log.error("Error on method DoView() resource " + strRscType + " with id " + base.getId(), e);
        }
        out.flush();
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

        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_DAILY_GRAPH;
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // REPORTE MENSUAL                
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
                String userTypeId = request.getParameter("wb_usertype")==null ? "":request.getParameter("wb_usertype");
                int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSiteId);
                /*Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();*/
                if(deleteFilter == 0) {                                
                    /*while(itUserTypes.hasNext()) {
                        String val = itUserTypes.next();
                        if(val.equalsIgnoreCase(userTypeId)) {
                            idaux.add(val);
                            filter.setIdaux(idaux.iterator());
                            break;
                        }
                    }*/
                    filter.setIdaux(userTypeId);
                }/*else {
                    filter.setIdaux(itUserTypes);                             
                }*/
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);

                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_MONTHLY_GRAPH;                        
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch (Exception e) {
            log.error("Error on method doGraph() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
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
        response.setHeader("Content-Disposition", "inline; filename=\"lar.xls\"");

        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                System.out.println("por dia. filtro="+filter.toString());
                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_DAILY;
                JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
                String userTypeId = request.getParameter("wb_usertype")==null ? "":request.getParameter("wb_usertype");
                int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSiteId);
                /*Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();*/
                if(deleteFilter == 0) {                                
                    /*while(itUserTypes.hasNext()) {
                        String val = itUserTypes.next();
                        if(val.equalsIgnoreCase(userTypeId)) {
                            idaux.add(val);
                            filter.setIdaux(idaux.iterator());
                            break;
                        }
                    }*/
                    filter.setIdaux(userTypeId);
                }/*else {
                    filter.setIdaux(itUserTypes);                             
                }*/
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                System.out.println("por mes. filtro="+filter.toString());

                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_MONTHLY;                        
                JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch(Exception e) {
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
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

        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try{            
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                System.out.println("por dia. filtro="+filter.toString());
                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_DAILY;
                JRResource jrResource = new JRXmlResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
                String userTypeId = request.getParameter("wb_usertype")==null ? "":request.getParameter("wb_usertype");
                int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSiteId);
                /*Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();*/
                if(deleteFilter == 0) {                                
                    /*while(itUserTypes.hasNext()) {
                        String val = itUserTypes.next();
                        if(val.equalsIgnoreCase(userTypeId)) {
                            idaux.add(val);
                            filter.setIdaux(idaux.iterator());
                            break;
                        }
                    }*/
                    filter.setIdaux(userTypeId);
                }/*else {
                    filter.setIdaux(itUserTypes);                             
                }*/
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                System.out.println("por mes. filtro="+filter.toString());

                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_MONTHLY;                        
                JRResource jrResource = new JRXmlResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch (Exception e) {
            log.error("Error on method doRepXml() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
        }
    }
    
        /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doRepPdf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");

        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try{            
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                System.out.println("por dia. filtro="+filter.toString());
                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_DAILY;
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
                String userTypeId = request.getParameter("wb_usertype")==null ? "":request.getParameter("wb_usertype");
                int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSiteId);
                /*Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();*/
                if(deleteFilter == 0) {                                
                    /*while(itUserTypes.hasNext()) {
                        String val = itUserTypes.next();
                        if(val.equalsIgnoreCase(userTypeId)) {
                            idaux.add(val);
                            filter.setIdaux(idaux.iterator());
                            break;
                        }
                    }*/
                    filter.setIdaux(userTypeId);
                }/*else {
                    filter.setIdaux(itUserTypes);                             
                }*/
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                System.out.println("por mes. filtro="+filter.toString());

                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_MONTHLY;                        
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch (Exception e) {
            log.error("Error on method doRepPdf() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
        }
    }
    
        /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doRepRtf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/rtf");
        response.setHeader("Content-Disposition", "inline; filename=\"lar.rtf\"");

        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try{            
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                System.out.println("por dia. filtro="+filter.toString());
                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_DAILY;
                JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
                String userTypeId = request.getParameter("wb_usertype")==null ? "":request.getParameter("wb_usertype");
                int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(webSiteId);
                /*Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();*/
                if(deleteFilter == 0) {                                
                    /*while(itUserTypes.hasNext()) {
                        String val = itUserTypes.next();
                        if(val.equalsIgnoreCase(userTypeId)) {
                            idaux.add(val);
                            filter.setIdaux(idaux.iterator());
                            break;
                        }
                    }*/
                    filter.setIdaux(userTypeId);
                }/*else {
                    filter.setIdaux(itUserTypes);                             
                }*/
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                System.out.println("por mes. filtro="+filter.toString());

                JRDataSourceable dataDetail = new JRUserTypesAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.USER_TYPES_MONTHLY;                        
                JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch (Exception e) {
            log.error("Error on method doRepRtf() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
        }
    }
    
    public HashMap getUserType(String p_repository, SWBParamRequest paramsRequest)
    {
        HashMap hm_type = new HashMap();
//        int i_len = 0;
//        
//        //TODO aqui tengo escoger el topic map del sitio al que voy a reportar
//        try
//        {
//            Enumeration vec = DBUser.getInstance(p_repository).getUserAttrsTree().elements();
//            Vector veclist = new Vector();
//            while(vec.hasMoreElements())
//            {
//                WBUserAttribute wbu_type = (WBUserAttribute)vec.nextElement();
//                if(wbu_type.getKind() == WBUserAttribute.USER_TYPE_ATTRIBUTE)
//                {
//                    String str_des = wbu_type.getValueLocalized(wbu_type.getName(),paramsRequest.getUser().getLanguage());
//                    String str_value = wbu_type.getName();
//                    veclist.add(wbu_type.getName());
//                    hm_type.put(Integer.toString(i_len),str_value +"|"+str_des);
//                    i_len++;
//                }
//            }
//            hm_type.put(Integer.toString(i_len),"_|"+ paramsRequest.getLocaleString("undefined") +"");
//        }
//        catch (Exception e) {
//            log.error("Error on method getUserType() resource " + strRscType +  " with id " + base.getId(), e);
//        }
        return hm_type;
    }
                
    private WBAFilterReportBean buildFilter(HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException, IncompleteFilterException {
        WBAFilterReportBean filterReportBean = null;
        GregorianCalendar gc_now = new GregorianCalendar();
        /*ArrayList idaux = new ArrayList();*/
        
        String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
        String userTypeId = request.getParameter("wb_usertype")==null ? "":request.getParameter("wb_usertype");
        int deleteFilter;
        try {
            deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
        }catch(NumberFormatException e) {
            deleteFilter = 0;
        }
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
            if(deleteFilter==0) {                
                /*Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();
                while(itUserTypes.hasNext()) {
                    String val = itUserTypes.next();
                    if(val.equalsIgnoreCase(userTypeId)) {
                        idaux.add(val);
                        break;
                    }
                }*/
                if(groupDates==0) { // radio button was 0. Select only one date
                    String[] numFecha = fecha1.split("-");
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(webSiteId);
                    filterReportBean.setIdaux(userTypeId);
                    filterReportBean.setType(I_REPORT_TYPE);                    
                    filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayI(Integer.parseInt(numFecha[2]));
                }else { // radio button was 1. Select between two dates
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(webSiteId);
                    filterReportBean.setIdaux(userTypeId);
                    filterReportBean.setType(I_REPORT_TYPE);
                    
                    String[] numFecha = fecha11.split("-");
                    filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayI(Integer.parseInt(numFecha[2]));
                    
                    numFecha = fecha12.split("-");
                    filterReportBean.setYearF(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthF(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayF(Integer.parseInt(numFecha[2]));
                }
            }else {
                Iterator<String> itUserTypes = paramsRequest.getTopic().getWebSite().getUserRepository().getUserTypes();
                if(groupDates==0) { // radio button was 0. Select only one date
                    String[] numFecha = fecha1.split("-");
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(webSiteId);
                    /*filterReportBean.setIdaux(itUserTypes);*/
                    filterReportBean.setType(I_REPORT_TYPE);
                    filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayI(Integer.parseInt(numFecha[2]));                    
                }else { // radio button was 1. Select between two dates                    
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(webSiteId);
                    /*filterReportBean.setIdaux(itUserTypes);*/
                    filterReportBean.setType(I_REPORT_TYPE);
                    
                    String[] numFecha = fecha11.split("-");
                    filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayI(Integer.parseInt(numFecha[2]));
                    
                    numFecha = fecha12.split("-");
                    filterReportBean.setYearF(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthF(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayF(Integer.parseInt(numFecha[2]));
                }
            }            
        }catch (Exception e) {
            log.error("Error on method buildFilter() resource " + strRscType + " with id " + getResourceBase().getId(), e);
            throw(new SWBResourceException(e.getMessage()));
        }       
        return filterReportBean;
    }    
}