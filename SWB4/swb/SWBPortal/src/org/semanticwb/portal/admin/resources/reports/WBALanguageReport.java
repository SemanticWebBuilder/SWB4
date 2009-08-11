/**  
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
**/ 
 
package org.semanticwb.portal.admin.resources.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Language;
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
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRLanguageAccessDataDetail;
import org.semanticwb.portal.db.SWBRecHit;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Esta clase genera el reporte de lenguajes de acceso, toma la informaci�n de los
 * objetos de WebBuilder de acuerdo con los par�metros recibidos del usuario. Este
 * archivo es usado en la parte de reportes.
 *
 * This class generates the language report, takes information from
 * WebBuilder objects according with user parameters. this file is used in report
 * sections.
 *
 * WBALanguageReport.java 
 */

public class WBALanguageReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBALanguageReport.class);

    public static final int I_REPORT_TYPE = 2;
    private String strRscType;
        
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
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillLangSel")) {
            doRenderLang(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("graph")) {
            doGraph(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_excel")) {
            doRepExcel(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_xml")) {
            doRepXml(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_pdf")) {
            doRepPdf(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_rtf")) {
            doRepRtf(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }
    
    public void doRenderLang(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        
        String websiteId = request.getParameter("site");
        WebSite webSite = SWBContext.getWebSite(websiteId);

        String langId = request.getParameter("wb_lang")==null? "0":request.getParameter("wb_lang");
        String lang = paramsRequest.getUser().getLanguage();
        
        out.println("<select id=\"wb_lang\" name=\"wb_lang\" size=\"1\">");
        out.println("<option value=\"0\" ");
        if(langId.equalsIgnoreCase("0")) {
            out.print(" selected=\"selected\" ");
        }
        out.print(">"+paramsRequest.getLocaleString("all")+"</option>");

        Iterator<Language> itLanguages = webSite.listLanguages();
        while (itLanguages.hasNext()) {
            Language language = itLanguages.next();
            out.println("<option value=\""+language.getId()+"\" ");
            if(langId.equalsIgnoreCase(language.getId())) {
                out.print(" selected=\"selected\" ");
            }
            out.print(">"+language.getDisplayTitle(lang)+"</option>");
        }
        out.println("</select>");
        out.flush();
        out.close();
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();        
        
        final int I_ACCESS = 0;        
        //StringBuffer sb_ret = new StringBuffer();
        GregorianCalendar gc_now = new GregorianCalendar();
        HashMap hm_sites = new HashMap();
        String rtype = null;
        int i_access = 0;

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
                            hm_sites.put(site.getId(), site.getDisplayTitle(paramsRequest.getUser().getLanguage()));
//                        }
//                    }
                }
            }

            // If there are sites continue
            if(hm_sites.size() > I_ACCESS) {
                String address = paramsRequest.getWebPage().getUrl();
                String websiteId = request.getParameter("wb_site")==null ? (String)hm_sites.keySet().iterator().next():request.getParameter("wb_site");
                String langId = request.getParameter("wb_lang")==null? "0":request.getParameter("wb_lang");
                
                int groupDates;
                try {
                    groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
                }catch(NumberFormatException e) {
                    groupDates = 0;
                }

                GregorianCalendar cal = new GregorianCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fecha1 = request.getParameter("wb_fecha1")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha1");
                try {
                    sdf.parse(fecha1);
                }catch(ParseException pe){
                    fecha1 = sdf.format(cal.getTime());
                }
                String fecha11 = request.getParameter("wb_fecha11")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha11");
                try {
                    sdf.parse(fecha11);
                }catch(ParseException pe){
                    fecha11 = sdf.format(cal.getTime());
                }
                String fecha12 = request.getParameter("wb_fecha12")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha12");
                try {
                    sdf.parse(fecha12);
                }catch(ParseException pe){
                    fecha12 = sdf.format(cal.getTime());
                }
                
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
                url.setMode("fillLangSel");

                out.println("<script type=\"text/javascript\">");
                
                out.println("dojo.require(\"dijit.form.DateTextBox\");");
                out.println("dojo.addOnLoad(doBlockade);");
                out.println("dojo.addOnLoad(function(){getHtml('"+url+"'+'?site="+websiteId+"'+'&wb_lang="+langId+"','slave')});");
                
                out.println("function getParams(accion) { ");
                out.println("   var params = \"?\";");
                out.println("   params = params + \"wb_site=\" + dojo.byId('wb_site').value;");
                out.println("   params = params + \"&wb_lang=\" + document.getElementById('wb_lang').options[document.getElementById('wb_lang').selectedIndex].value;");
                out.println("   params = params + \"&wb_rtype=\" + document.getElementById('wb_rtype').value;");
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
                out.println("} ");
                
                out.println("function doXml(accion, size) { ");
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml") + "\"+params,\"graphWindow\",size);");
                out.println("}");

                out.println("function doExcel(accion, size) { ");
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel") + "\"+params,\"graphWindow\",size);");
                out.println("}");

                out.println("function doGraph(accion, size) { ");
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("graph") + "\"+params,\"graphWindow\",size);");
                out.println("}");

                out.println("function doPdf(accion, size) { ");
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_pdf") + "\"+params,\"graphWindow\",size);");
                out.println("}");

                out.println("function doRtf(accion, size) { ");
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_rtf") + "\"+params,\"graphWindow\",size);    ");
                out.println("}");

                out.println("function getTypeSelected() { ");
                out.println("   var strType = \"0\";");
                out.println("   for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){");
                out.println("       if(window.document.frmrep.wb_rep_type[i].checked==true){");
                out.println("           strType=window.document.frmrep.wb_rep_type[i].value;");
                out.println("       }");
                out.println("   }");
                out.println("   return strType;");
                out.println("}");

                out.println("function doApply() { ");
                out.println("      window.document.frmrep.submit(); ");
                out.println("}");

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

                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                if(rtype.equals("0")) {
                    out.println(paramsRequest.getLocaleString("description_daily"));
                }else {
                    out.println(paramsRequest.getLocaleString("description_monthly"));
                }
                out.println("</fieldset>");

                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("language_report") + "</legend>");                
                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
                if(rtype.equals("0")) {
                    out.println("<tr><td width=\"183\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"443\"></td></tr>");
                }else {
                    out.println("<tr><td width=\"100\"></td><td width=\"196\"></td><td width=\"224\"></td><td width=\"364\"></td></tr>");
                }
                
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("site") + ":</td>");
                out.println("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" onchange=\"getHtml('"+url.toString()+"'+'?site='+this.value,'slave');\">");
                Iterator<String> itKeys = hm_sites.keySet().iterator();                    
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    out.println("<option value=\"" + key + "\"");
                    if(key.equalsIgnoreCase(websiteId)) {
                        out.println(" selected=\"selected\"");
                    }
                    out.println(">" + (String)hm_sites.get(key) + "</option>");
                }                    
                out.println("</select>");
                out.println("</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("language") + ":</td>");                
                out.println("<td colspan=\"2\"><div id=\"slave\"></div>");
                out.println("</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
                
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
                    out.println("<input type=\"text\" name=\"wb_fecha1\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha1\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha1+"\">");
                    out.println("</td>");
                    out.println("<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"0\" /></td>");
                    out.println("</tr>");
                    out.println("<tr>");
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
                    out.println("<input type=\"text\" name=\"wb_fecha11\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha11\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha11+"\">");
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<input type=\"text\" name=\"wb_fecha12\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha12\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha12+"\">");
                    out.println("</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("</tr>");
                    out.println("</table></fieldset>");

                    out.println("<fieldset>");
                    out.println("<table border=\"0\" width=\"95%\">");
                    out.println("<tr>");
                    out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\">MS Excel</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">PDF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">RTF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\">"+paramsRequest.getLocaleString("graph")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">"+paramsRequest.getLocaleString("apply")+"</button>");
                    out.println(" </td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("</form>");
                    if(request.getParameter("wb_rtype")!=null && websiteId!=null ) {
                        out.println("<fieldset>");
                        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                            
                        out.println("<tr>");
                        out.println("<td>");

                        WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                        JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_DAILY_HTML;
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
                        out.println("<tr><td>&nbsp;</td></tr>");
                        out.println("</table>");
                        out.println("</fieldset>");
                    }
                }else { // REPORTE MENSUAL                    
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
                    out.println("</table></fieldset>");

                    out.println("<fieldset>");
                    out.println("<table border=\"0\" width=\"95%\">");
                    out.println("<tr>");
                    out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\">MS Excel</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">PDF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">RTF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\">"+paramsRequest.getLocaleString("graph")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">"+paramsRequest.getLocaleString("apply")+"</button>");
                    out.println(" </td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("<fieldset>");
                    out.println("</form>");
                    if(request.getParameter("wb_rtype")!=null && websiteId!=null ) {
                        out.println("<fieldset>");
                        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\">");                            
                        out.println("<tr>");
                        out.println("<td>");
                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(websiteId);
                        if( !langId.equalsIgnoreCase("0") ) {
                            filter.setIdaux(langId);
                        }
                        filter. setType(I_REPORT_TYPE);
                        filter.setYearI(year13);
                        JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                        JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_MONTHLY_HTML;
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
                        out.println("<tr><td>&nbsp;</td></tr>");
                        out.println("</table>");
                        out.println("</fieldset>");
                    }
                }
                out.println("</div>");
            }else { // There are not sites and displays a message
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("language_report") + "</legend>");
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
    
    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_DAILY_GRAPH;
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // REPORTE MENSUAL                
                String websiteId = request.getParameter("wb_site");
                String langId = request.getParameter("wb_lang")==null ? "":request.getParameter("wb_lang");
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(websiteId);
                if( !langId.equalsIgnoreCase("0") ) {
                    filter.setIdaux(langId);
                }
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);

                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_MONTHLY_GRAPH;                        
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
                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_DAILY;
                JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                String websiteId = request.getParameter("wb_site");
                String langId = request.getParameter("wb_lang")==null ? "":request.getParameter("wb_lang");
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(websiteId);
                if( !langId.equalsIgnoreCase("0") ) {
                    filter.setIdaux(langId);
                }
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                System.out.println("por mes. filtro="+filter.toString());

                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_MONTHLY;                        
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
        PrintWriter out = response.getWriter();
        
        Document dom = SWBUtils.XML.getNewDocument();        
        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try {
            WBAFilterReportBean filter;
            Iterator<SWBRecHit> itRecHits;
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            int renglones = 0;
            Element report = dom.createElement("LanguageReport");
            dom.appendChild(report);            
            
            if(rtype == 0) { // REPORTE DIARIO
                filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JRBeanCollectionDataSource ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                if(ds!=null){
                    itRecHits = ds.getData().iterator();
                }else {
                    itRecHits = Collections.EMPTY_LIST.iterator();
                }
                while(itRecHits.hasNext()) {
                    SWBRecHit rec = itRecHits.next();
                    Element row = dom.createElement("row");
                    row.appendChild(dom.createTextNode(""));
                    row.setAttribute("id",Integer.toString(++renglones));
                    report.appendChild(row);
                    Element lang = dom.createElement("language");
                    lang.appendChild(dom.createTextNode(rec.getItem()));
                    row.appendChild(lang);
                    Element site = dom.createElement("site");
                    site.appendChild(dom.createTextNode(rec.getTopicmap()));
                    row.appendChild(site);
                    Element year = dom.createElement("year");
                    year.appendChild(dom.createTextNode(Integer.toString(rec.getYear())));
                    row.appendChild(year);
                    Element month = dom.createElement("month");
                    month.appendChild(dom.createTextNode(rec.getMonth()));
                    row.appendChild(month);
                    Element day = dom.createElement("day");
                    day.appendChild(dom.createTextNode(Integer.toString(rec.getDay())));
                    row.appendChild(day);
                    Element pages = dom.createElement("pages");
                    pages.appendChild(dom.createTextNode(Long.toString(rec.getHits())));
                    row.appendChild(pages);
                }
            }else { // REPORTE MENSUAL
                String websiteId = request.getParameter("wb_site");
                String langId = request.getParameter("wb_lang")==null ? "":request.getParameter("wb_lang");
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                filter = new WBAFilterReportBean();
                filter.setSite(websiteId);
                if( !langId.equalsIgnoreCase("0") ) {
                    filter.setIdaux(langId);
                }
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JRBeanCollectionDataSource ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                if(ds!=null){
                    itRecHits = ds.getData().iterator();
                }else {
                    itRecHits = Collections.EMPTY_LIST.iterator();
                }
                while(itRecHits.hasNext()) {
                    SWBRecHit rec = itRecHits.next();
                    Element row = dom.createElement("row");
                    row.appendChild(dom.createTextNode(""));
                    row.setAttribute("id",Integer.toString(++renglones));
                    report.appendChild(row);
                    Element language = dom.createElement("language");
                    language.appendChild(dom.createTextNode(rec.getItem()));
                    row.appendChild(language);
                    Element site = dom.createElement("site");
                    site.appendChild(dom.createTextNode(rec.getTopicmap()));
                    row.appendChild(site);
                    Element year = dom.createElement("year");
                    year.appendChild(dom.createTextNode(Integer.toString(rec.getYear())));
                    row.appendChild(year);
                    Element month = dom.createElement("month");
                    month.appendChild(dom.createTextNode(rec.getMonth()));
                    row.appendChild(month);
                    Element pages = dom.createElement("pages");
                    pages.appendChild(dom.createTextNode(Long.toString(rec.getHits())));
                    row.appendChild(pages);
                }
            }
            report.setAttribute("rows",Integer.toString(renglones));
        }
        catch (Exception e){            
            log.error("Error on method doRepXml() resource " + strRscType + " with id " + base.getId(), e);
        }
        out.print(SWBUtils.XML.domToXml(dom));
        out.flush();
        out.close();
    }
    
    public void doRepPdf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        Resource base = getResourceBase();
        /*ArrayList idaux = new ArrayList();*/
        try{            
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                System.out.println("por dia. filtro="+filter.toString());
                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_DAILY;
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                String websiteId = request.getParameter("wb_site");
                String langId = request.getParameter("wb_lang")==null ? "":request.getParameter("wb_lang");
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(websiteId);
                if( !langId.equalsIgnoreCase("0") ) {
                    filter.setIdaux(langId);
                }
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                System.out.println("por mes. filtro="+filter.toString());

                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_MONTHLY;                        
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch (Exception e) {
            log.error("Error on method doRepPdf() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
        }
    }
    
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
                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_DAILY;
                JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                String websiteId = request.getParameter("wb_site");
                String langId = request.getParameter("wb_lang")==null ? "":request.getParameter("wb_lang");
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                WBAFilterReportBean filter = new WBAFilterReportBean();
                filter.setSite(websiteId);
                if( !langId.equalsIgnoreCase("0") ) {
                    filter.setIdaux(langId);
                }
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                System.out.println("por mes. filtro="+filter.toString());

                JRDataSourceable dataDetail = new JRLanguageAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.LANGUAGE_MONTHLY;                        
                JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch (Exception e) {
            log.error("Error on method doRepRtf() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
        }
    }
    
    private WBAFilterReportBean buildFilter(HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException, IncompleteFilterException {
        WBAFilterReportBean filterReportBean = null;
        GregorianCalendar gc_now = new GregorianCalendar();
        /*ArrayList idaux = new ArrayList();*/
        
        String websiteId = request.getParameter("wb_site");
        String langId = request.getParameter("wb_lang")==null ? "":request.getParameter("wb_lang");
        int groupDates;
        try {
            groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
        }catch(NumberFormatException e) {
            groupDates = 0;
        }

        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = request.getParameter("wb_fecha1")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha1");
        try {
            sdf.parse(fecha1);
        }catch(ParseException pe){
            fecha1 = sdf.format(cal.getTime());
        }
        String fecha11 = request.getParameter("wb_fecha11")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha11");
        try {
            sdf.parse(fecha11);
        }catch(ParseException pe){
            fecha11 = sdf.format(cal.getTime());
        }
        String fecha12 = request.getParameter("wb_fecha12")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha12");
        try {
            sdf.parse(fecha12);
        }catch(ParseException pe){
            fecha12 = sdf.format(cal.getTime());
        }
        
        try {
            if( !langId.equalsIgnoreCase("0") ) {
                if(groupDates==0) { // radio button was 0. Select only one date
                    String[] numFecha = fecha1.split("-");
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(websiteId);
                    /*filterReportBean.setIdaux(idaux.iterator());*/
                    filterReportBean.setIdaux(langId);
                    filterReportBean.setType(I_REPORT_TYPE);                    
                    filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayI(Integer.parseInt(numFecha[2]));
                }else { // radio button was 1. Select between two dates
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(websiteId);
                    /*filterReportBean.setIdaux(idaux.iterator());*/
                    filterReportBean.setIdaux(langId);
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
                /*Iterator<Language> itLanguages = paramsRequest.getWebPage().getWebSite().listLanguages();*/
                if(groupDates==0) { // radio button was 0. Select only one date
                    String[] numFecha = fecha1.split("-");
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(websiteId);
                    /*filterReportBean.setIdaux(itLanguages);*/
                    filterReportBean.setType(I_REPORT_TYPE);
                    filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                    filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                    filterReportBean.setDayI(Integer.parseInt(numFecha[2]));                    
                }else { // radio button was 1. Select between two dates                    
                    filterReportBean = new WBAFilterReportBean();
                    filterReportBean.setSite(websiteId);
                    /*filterReportBean.setIdaux(itLanguages);*/
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





