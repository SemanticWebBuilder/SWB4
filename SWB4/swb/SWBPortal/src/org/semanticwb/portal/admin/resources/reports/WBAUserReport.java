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
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.jrresources.*;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRUserTypesAccessDataDetail;


// TODO: Auto-generated Javadoc
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
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBAUserReport.class);

    /** The I_ repor t_ type. */
    public final int I_REPORT_TYPE = 4;
    
    /** The str rsc type. */
    private String strRscType;   
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#init()
     */
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
     * Render.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState())) {
            processRequest(request, response, paramsRequest);
        }
    }
    
    /**
     * Do render select.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        System.out.println("inicia doRenderSelect");
        response.setContentType("text/json;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();

        String repositoryId = request.getParameter("repository");
        System.out.println("repositoryId= "+repositoryId);
        
        UserRepository ur = SWBContext.getUserRepository(repositoryId);


        out.println("<select id=\"wb_usertype\" name=\"wb_usertype\" size=\"1\">");
        out.println("<option value=\"0\">"+paramsRequest.getLocaleString("all_types")+"</option>");
        Iterator<String> itUserTypes = ur.getUserTypes();
        while(itUserTypes.hasNext()) {
            String usertype = itUserTypes.next();
            out.println("<option value=\""+usertype+"\">"+usertype+"</option>");
        }
        out.println("</select>");
        out.flush();
        out.close();
    }
    
    /**
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("renderSelect")) {
            doRenderSelect(request,response,paramsRequest);
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
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
                
        final int I_ACCESS = 0;
        
        HashMap hm_sites = new HashMap();
        String rtype = null;
        
        try {
            if(hm_sites.size() > I_ACCESS) {
                String address = paramsRequest.getWebPage().getUrl();
                String repositoryId = request.getParameter("wb_repository");
                String userTypeId = request.getParameter("wb_usertype");
                
                /*int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }*/
                
                int groupDates;
                try {
                    groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
                }catch(NumberFormatException e) {
                    groupDates = 0;
                }
                String fecha1 = request.getParameter("wb_fecha1")==null ? "":request.getParameter("wb_fecha1");
                String fecha11 = request.getParameter("wb_fecha11")==null ? "":request.getParameter("wb_fecha11"); 
                String fecha12 = request.getParameter("wb_fecha12")==null ? "":request.getParameter("wb_fecha12");
                
                String topicId = paramsRequest.getWebPage().getId();
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

                SWBResourceURL url=paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setMode("renderSelect");
                
                out.println("<script type=\"text/javascript\">");                
                
                out.println("dojo.require(\"dijit.form.DateTextBox\");");
                out.println("dojo.addOnLoad(doBlockade);");
                out.println("dojo.addOnLoad(function(){postHtml('"+url.toString()+"'+'?repository="+repositoryId+"'");
                
                out.println("function getParams(accion) {");
                out.println("   var params = '?';");
                out.println("   params = params + \"wb_site=\" + dojo.byId('wb_site').value;");
                out.println("   params = params + '&wb_usertype=' + dojo.byId('wb_usertype').value;");
                out.println("   params = params + '&wb_rtype=' + dojo.byId('wb_rtype').value;");
                out.println("   if(accion == 0) {");
                out.println("       params = params + \"&wb_rep_type=\" + getTypeSelected();");
                out.println("       var fecha1 = new String(dojo.byId('wb_fecha1').value);");
                out.println("       var fecha2 = new String(dojo.byId('wb_fecha11').value);");
                out.println("       var fecha3 = new String(dojo.byId('wb_fecha12').value);");
                out.println("       if(fecha1.length>0) {");
                out.println("           dp = fecha1.split('/');");
                out.println("           params = params + '&wb_fecha1=' + dp[2]+'-'+dp[1]+'-'+dp[0];");
                out.println("       }");
                out.println("       if(fecha2.length>0) {");
                out.println("           dp = fecha2.split('/');");
                out.println("           params = params + '&wb_fecha11=' + dp[2]+'-'+dp[1]+'-'+dp[0];");
                out.println("       }");
                out.println("       if(fecha3.length>0) {");
                out.println("           dp = fecha3.split('/');");
                out.println("           params = params + '&wb_fecha12=' + dp[2]+'-'+dp[1]+'-'+dp[0];");
                out.println("       }");
                out.println("   }else {");
                out.println("       var year = new String(dojo.byId('wb_year13').value);");
                out.println("       params = params + '&wb_year13=' + year;");
                out.println("   }");
                out.println("   return params;");
                out.println("}");

                out.println("function validate(accion) {");
                out.println("    if(accion=='0') {");
                out.println("       var fecha1 = new String(dojo.byId('wb_fecha1').value);");
                out.println("       var fecha2 = new String(dojo.byId('wb_fecha11').value);");
                out.println("       var fecha3 = new String(dojo.byId('wb_fecha12').value);");
                out.println("       if( (fecha1.length==0) && (fecha2.length==0 || fecha3.length==0) ) {");
                out.println("          alert('Especifique la fecha o el rango de fechas que desea consultar');");
                out.println("          return false;");
                out.println("       }");
                out.println("    }");
                out.println("    return true;");
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

                out.println("function doBlockade() {");
                out.println("  if(window.document.frmrep.wb_rep_type) {");
                out.println("     if(window.document.frmrep.wb_rep_type[0].checked) {");
                out.println("       dojo.byId('wb_fecha1').disabled = false;");
                out.println("       dojo.byId('wb_fecha11').disabled = true;");
                out.println("       dojo.byId('wb_fecha12').disabled = true;");
                out.println("     }");
                out.println("     if(window.document.frmrep.wb_rep_type[1].checked) {");
                out.println("       dojo.byId('wb_fecha1').disabled = true;");
                out.println("       dojo.byId('wb_fecha11').disabled = false;");
                out.println("       dojo.byId('wb_fecha12').disabled = false;");
                out.println("     }");
                out.println("  }");
                out.println("}");
                
                out.println("</script>");
                // javascript
                
                out.println("<div id=\"swbform\">");
                out.println("<fieldset>");
                if(rtype.equals("0")) {
                    out.println(paramsRequest.getLocaleString("description_daily"));
                }else {
                    out.println(paramsRequest.getLocaleString("description_monthly"));
                }
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("user_report") + "</legend>");
                
                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
                if(rtype.equals("0")) {
                    out.println("<tr><td width=\"183\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"443\"></td></tr>");
                }else {
                    out.println("<tr><td width=\"100\"></td><td width=\"196\"></td><td width=\"224\"></td><td width=\"364\"></td></tr>");
                }

                /*out.println("<tr>");
                out.println("<td colspan=\"4\">");
                if(rtype.equals("0")) {
                    out.println(paramsRequest.getLocaleString("description_daily"));
                }else {
                    out.println(paramsRequest.getLocaleString("description_monthly"));
                }
                out.println("</td></tr>");*/

                /*out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
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
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");*/
                
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("repository") + ":</td>");
                out.println("<td colspan=\"2\"><select id=\"wb_repository\" name=\"wb_repository\" onchange=\"postHtml('"+url+"'+'?repository='+this.value,'slave');\">");
                Iterator<UserRepository> itur = SWBContext.listUserRepositories();
                while(itur.hasNext()) {
                    UserRepository ur = itur.next();
                    out.println("<option value=\""+ ur.getId() + "\"");
                        if(ur.getId().equalsIgnoreCase(repositoryId)) {
                            out.println(" selected=\"selected\"");
                        }
                    out.println(">" + ur.getDisplayTitle(paramsRequest.getUser().getLanguage()) + "</option>");
                }
                out.println("</select>");
                out.println("</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("user_type") + ":</td>");
                out.println("<td colspan=\"2\"><div id=\"slave\"></div>");
                out.println("</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                
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
                    if(request.getParameter("wb_rtype")==null || repositoryId==null ) {
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
                        /*out.println("<hr size=\"1\" noshade>");*/
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
                    if(request.getParameter("wb_rtype")==null || repositoryId==null ) {
                        out.println("&nbsp;");
                    }else {
                        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                            
                        out.println("<tr>");
                        out.println("<td>");

                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(repositoryId);
                        if(!userTypeId.equalsIgnoreCase("0")) {
                            filter.setIdaux(userTypeId);
                        }
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
                        /*out.println("<hr size=\"1\" noshade>");*/
                    }
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("</table></form>");
                out.println("</fieldset>");

                out.println("<fieldset>");
                out.println("<table border=\"0\" width=\"95%\">");
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
                out.println("</table>");
                out.println("</fieldset>");

                out.println("</div>");
            }else { // There are not sites and displays a message
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("user_report") + "</legend>");
                out.println("<form method=\"Post\" action=\"" + paramsRequest.getWebPage().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
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
                out.println("</fieldset></div>");
            }
        } catch (Exception e) {
            log.error("Error on method DoView() resource " + strRscType + " with id " + base.getId(), e);
        }
        out.flush();
        out.close();
    }
    
    /**
     * Do graph.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getWebPage().getWebSite().getId():request.getParameter("wb_site");
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
                /*Iterator<String> itUserTypes = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserTypes();*/
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
     * Do rep excel.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getWebPage().getWebSite().getId():request.getParameter("wb_site");
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
                /*Iterator<String> itUserTypes = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserTypes();*/
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
     * Do rep xml.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getWebPage().getWebSite().getId():request.getParameter("wb_site");
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
                /*Iterator<String> itUserTypes = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserTypes();*/
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
         * Do rep pdf.
         * 
         * @param request the request
         * @param response the response
         * @param paramsRequest the params request
         * @throws SWBResourceException the sWB resource exception
         * @throws IOException Signals that an I/O exception has occurred.
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
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getWebPage().getWebSite().getId():request.getParameter("wb_site");
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
                /*Iterator<String> itUserTypes = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserTypes();*/
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
         * Do rep rtf.
         * 
         * @param request the request
         * @param response the response
         * @param paramsRequest the params request
         * @throws SWBResourceException the sWB resource exception
         * @throws IOException Signals that an I/O exception has occurred.
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
                String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getWebPage().getWebSite().getId():request.getParameter("wb_site");
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
                /*Iterator<String> itUserTypes = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserTypes();*/
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
    
    /**
     * Gets the user type.
     * 
     * @param p_repository the p_repository
     * @param paramsRequest the params request
     * @return the user type
     */
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
                
    /**
     * Builds the filter.
     * 
     * @param request the request
     * @param paramsRequest the params request
     * @return the wBA filter report bean
     * @throws SWBResourceException the sWB resource exception
     * @throws IncompleteFilterException the incomplete filter exception
     */
    private WBAFilterReportBean buildFilter(HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException, IncompleteFilterException {
        WBAFilterReportBean filterReportBean = null;
        GregorianCalendar gc_now = new GregorianCalendar();
        /*ArrayList idaux = new ArrayList();*/
        
        String webSiteId = request.getParameter("wb_site")==null ? paramsRequest.getWebPage().getWebSite().getId():request.getParameter("wb_site");
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
                /*Iterator<String> itUserTypes = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserTypes();
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
                Iterator<String> itUserTypes = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserTypes();
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
