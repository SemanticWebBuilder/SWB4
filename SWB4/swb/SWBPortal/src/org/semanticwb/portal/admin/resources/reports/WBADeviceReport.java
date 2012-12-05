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

import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;
import org.semanticwb.portal.admin.resources.reports.jrresources.*;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRDeviceAccessDataDetail;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.db.SWBRecHit;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.semanticwb.SWBPortal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/** Esta clase genera el reporte de dispositivos, toma la informaci�n de los
 * objetos de WebBuilder de acuerdo con los par�metros recibidos del usuario. Este
 * archivo es usado en la parte de reportes.
 *
 * This class generates the device report, takes information from
 * WebBuilder objects according with user parameters. this file is used in report
 * sections.
 *
 * WBADeviceReport.java

 */
public class WBADeviceReport extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBADeviceReport.class);
    
    /** The Constant UNKNOW. */
    public static final String UNKNOW = "Desconocido";
    
    /** The Constant I_REPORT_TYPE. */
    public static final int I_REPORT_TYPE = 1;
    
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
            strRscType = "WBADeviceReport";
        }
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
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillSelect")) {
            doRenderSelect(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("graph")) {
            doGraph(request, response, paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("histogram")) {
            doHistrogram(request,response,paramsRequest);
        }else if (paramsRequest.getMode().equalsIgnoreCase("xls")) {
            doRepExcel(request, response, paramsRequest);
        }else if (paramsRequest.getMode().equalsIgnoreCase("xml")) {
            doRepXml(request, response, paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("pdf")){
            doRepPdf(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("rtf")){
            doRepRtf(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("fillgridmtr")) {
            doFillReport(request,response,paramsRequest);
        }else{
            super.processRequest(request, response, paramsRequest);
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
        response.setContentType("text/html;charset=iso-8859-1");

        StringBuilder ret = new StringBuilder();
        String space="";
        String language = paramsRequest.getUser().getLanguage();

        WebSite webSite = SWBContext.getWebSite(request.getParameter("site"));
        String[] seldevs = (String[]) request.getSession(true).getAttribute("devs");
        if(seldevs != null) {
            Arrays.sort(seldevs);
        }else {
            seldevs = new String[0];
        }

        ArrayList<Device> devs=new ArrayList<Device>();
        Iterator<Device> itDevices = webSite.listDevices();
        while (itDevices.hasNext()) {
            devs.add(itDevices.next());
        }
        devs.trimToSize();

        ret.append("<select id=\"wb_device\" name=\"wb_device\" size=\""+(devs.size()<10?5:10)+"\" multiple=\"multiple\">\n");
        while(!devs.isEmpty()) {
            Device device = devs.get(0);
            ret.append("<option value=\""+device.getId()+"\"");
            if(Arrays.binarySearch(seldevs, device.getId())>=0) {
                ret.append(" selected=\"selected\"");
            }
            ret.append(">"+space+device.getDisplayTitle(language)+"</option>\n");
            devs.remove(0);
            if(device.listChilds().hasNext()) {
                renderSelect(devs, device, language, ret, space+"&nbsp;&nbsp;&nbsp;", seldevs);
            }
        }
        ret.append("</select>\n");
        request.getSession(true).removeAttribute("devs");
        
        PrintWriter out = response.getWriter();
        out.print(ret.toString());
        out.flush();
        out.close();
    }

    /**
     * Render select.
     * 
     * @param origList the orig list
     * @param node the node
     * @param language the language
     * @param ret the ret
     * @param space the space
     * @param seldevs the seldevs
     */
    private void renderSelect(ArrayList origList, Device node, String language, StringBuilder ret, String space, String[] seldevs) {
        ArrayList<Device> devs=new ArrayList<Device>();
        Iterator<Device> itDevices = node.listChilds();
        while(itDevices.hasNext()) {
            devs.add(itDevices.next());
        }
        devs.trimToSize();

        while(!devs.isEmpty()) {
            Device device = devs.get(0);
            ret.append("<option value=\""+device.getId()+"\"");
            if(Arrays.binarySearch(seldevs, device.getId())>=0) {
                ret.append(" selected=\"selected\"");
            }
            ret.append(">"+space+device.getDisplayTitle(language)+"</option>\n");
            origList.remove(device);
            devs.remove(0);
            if(device.listChilds().hasNext()) {
                renderSelect(origList, device, language, ret, space+"&nbsp;&nbsp;&nbsp;", seldevs);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
               
        GregorianCalendar gc_now = new GregorianCalendar();
        HashMap hm_sites = new HashMap();
        String rtype = null;

        try {
            // Evaluates if there are sites
            Iterator<WebSite> webSites = SWBContext.listWebSites();
            while(webSites.hasNext()){
                WebSite site = webSites.next();
                // Evaluates if TopicMap is not Global
                if(!site.getId().equals(SWBContext.getGlobalWebSite().getId())) {
                    hm_sites.put(site.getId(), site.getDisplayTitle(paramsRequest.getUser().getLanguage()));
                }
            }

            // If there are sites continue
            if (hm_sites.size() > 0) {
                String address = paramsRequest.getWebPage().getUrl();
                String websiteId = request.getParameter("wb_site")==null ? (String)hm_sites.keySet().iterator().next():request.getParameter("wb_site");

                String[] devs=request.getParameterValues("wb_device");
                if(devs != null) {
                    request.getSession(true).setAttribute("devs", devs);
                }
                
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

                out.println("<script type=\"text/javascript\">");                
                out.println("dojo.require(\"dijit.form.DateTextBox\");");

                out.println("dojo.require(\"dojox.grid.DataGrid\");");//--
                out.println("dojo.require(\"dojo.data.ItemFileReadStore\");");//--

                out.println("dojo.addOnLoad(doBlockade);");

                url.setMode("fillSelect");
                out.println("dojo.addOnLoad(function(){postHtml('"+url+"'+'?site="+websiteId+"','slave')});");

                out.println("function fillGrid(grid, uri) {");
                out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri});");
                out.println("   grid._refresh();");
                out.println("}");

                out.println("var layout= null;");
                out.println("var jStrMaster = null;");
                out.println("var gridMaster = null;");
                out.println("var gridResources = null;");

                out.println("dojo.addOnLoad(function() {");
                out.println("   layout= [");
                out.println("      { field:\"dispositivo\", width:\"100px\", name:\"Dispositivo\" },");
                out.println("      { field:\"sitio\", width:\"100px\", name:\"Sitio\" },");
                out.println("      { field:\"anio\", width:\"100px\", name:\"Año\" },");
                out.println("      { field:\"mes\", width:\"100px\", name:\"Mes\" },");
                if(rtype.equals("0")) { //Reporte diario
                    out.println("  { field:\"dia\", width:\"100px\", name:\"Día\" },");
                }
                out.println("      { field:\"accesos\", width:\"100px\", name:\"Accesos\" },");
                out.println("   ];");

                out.println("   gridMaster = new dojox.grid.DataGrid({");
                out.println("      id: \"gridMaster\",");
                out.println("      structure: layout,");
                out.println("      autoWidth: true,");
                out.println("      rowSelector: \"10px\",");
                out.println("      rowsPerPage: \"15\"");
                out.println("   }, \"gridMaster\");");
                out.println("   gridMaster.startup();");
                out.println("});");
                //--

                out.println("function getParams(accion) {");
                out.println("   var dp = null;");
                out.println("   var params = '?';");
                out.println("   params = params + 'wb_site=' + dojo.byId('wb_site').value;");

                out.println("   var devs=dojo.byId('wb_device');");
                out.println("   for(i=0; i<devs.options.length; i++) {");
                out.println("       if(devs.options[i].selected) {");
                out.println("           params = params + '&wb_device=' + devs.options[i].value;");
                out.println("       }");
                out.println("   }");
                
                out.println("   params = params + '&wb_rtype=' + dojo.byId('wb_rtype').value;");
                out.println("   if(accion == 0) {");
                out.println("       params = params + '&wb_rep_type=' + getTypeSelected();");
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
                
                /*out.println("function validate(accion) {");
                out.println("    var fecha1 = null;");
                out.println("    var fecha2 = null;");
                out.println("    var fecha3 = null;");
                out.println("    if(accion=='0') {");
                out.println("       fecha1 = new String(dojo.byId('wb_fecha1').value);");
                out.println("       fecha2 = new String(dojo.byId('wb_fecha11').value);");
                out.println("       fecha3 = new String(dojo.byId('wb_fecha12').value);");
                out.println("       if( (fecha1.length==0) && (fecha2.length==0 || fecha3.length==0) ) {");
                out.println("          alert('Especifique la fecha o el rango de fechas que desea consultar');");
                out.println("          return false;");
                out.println("       }");
                out.println("    }");
                out.println("    return true;");
                out.println("}");*/

                out.println("function doXml(accion, size) { ");
                /*out.println("   if(validate(accion)) {");*/
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\""+url.setMode("xml")+"\"+params,\"graphWindow\",size);");
                /*out.println("   }");*/
                out.println("}");

                out.println("function doExcel(accion, size) { ");
                /*out.println("   if(validate(accion)) {");*/
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\""+url.setMode("xls")+"\"+params,\"graphWindow\",size);");
                /*out.println("   }");*/
                out.println("}");

                out.println("function doHistogram(accion, size) { ");
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\""+url.setMode("histogram")+"\"+params,\"graphWindow\",size);   ");
                out.println(" }");

                out.println("function doGraph(accion, size) { ");
                /*out.println("   if(validate(accion)) {");*/
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\""+url.setMode("graph")+"\"+params,\"graphWindow\",size);");
                /*out.println("   }");*/
                out.println("}");

                out.println("function doPdf(accion, size) { ");
                /*out.println("   if(validate(accion)) {");*/
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\""+url.setMode("pdf")+"\"+params,\"graphWindow\",size);");
                /*out.println("   }");*/
                out.println("}");

                out.println("function doRtf(accion, size) { ");
                /*out.println("   if(validate(accion)) {");*/
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\""+url.setMode("rtf")+"\"+params,\"graphWindow\",size);    ");
                /*out.println("   }");*/
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

                out.println("function doApply() {");
                out.println("   var grid = dijit.byId('gridMaster');");
                out.println("   var params = getParams("+ rtype + ");");
                out.println("   fillGrid(grid, '"+url.setMode("fillgridmtr")+"'+params);");
                out.println("}");

                out.println(" function doBlockade() {");
                out.println("   if(window.document.frmrep.wb_rep_type) {");
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
                out.println("   }");
                out.println(" }");

                out.println("</script>");
                // javascript

                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                if(rtype.equals("0")) {
                    out.println(paramsRequest.getLocaleString("daily_report"));
                }else {
                    out.println(paramsRequest.getLocaleString("monthly_report"));
                }                    
                out.println("</fieldset>");

                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\""+address+"\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("filter") + "</legend>");
                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
                if(rtype.equals("0")) {
                    out.println("<tr><td width=\"183\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"443\"></td></tr>");
                }else {
                    out.println("<tr><td width=\"100\"></td><td width=\"196\"></td><td width=\"224\"></td><td width=\"364\"></td></tr>");
                }

                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("site") + ":</td>");
                url.setMode("fillSelect");
                out.println("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" onchange=\"postHtml('"+url+"'+'?site='+this.value,'slave');\">");
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
                out.println("<td>" + paramsRequest.getLocaleString("device") + ":</td>");                
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
                    out.println("<td>");
                    out.println("<label>");
                    out.println("<input type=\"radio\" value=\"1\" name=\"wb_rep_type\" id=\"wb_rep_type_1\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates!=0) {
                        out.println(" checked=\"checked\"");
                    }
                    out.println(" />");
                    out.println("&nbsp;" + paramsRequest.getLocaleString("by_range"));
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
                    out.println("   <button dojoType=\"dijit.form.Button\" onclick=\"doExcel('"+rtype+"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">"+paramsRequest.getLocaleString("spread_sheet")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">PDF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">RTF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\">"+paramsRequest.getLocaleString("graph")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onclick=\"doHistogram('"+rtype+"', 'width=700, height=600, scrollbars, resizable, alwaysRaised')\">"+paramsRequest.getLocaleString("histogram")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">"+paramsRequest.getLocaleString("apply")+"</button>");
                    out.println(" </td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("</form>");
                    
//                    out.println("<fieldset>");
//                    out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
//                    out.println("<tr>");
//                    out.println("<td colspan=\"4\">");
//                    out.println("<div id=\"ctnergrid\" style=\"height:600px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
//                    out.println("  <div id=\"gridMaster\"></div>");
//                    out.println("</div>");
                    out.println("<div id=\"ctnergrid\" style=\"height:350px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
                    out.println("  <div id=\"gridMaster\" jsid=\"gridMaster\"></div>");
                    out.println("</div>");
//                    out.println("</td>");
//                    out.println("</tr>");
//                    out.println("</table>");
//                    out.println("</fieldset>");
                    out.println("</div>");
                }else { // REPORTE MENSUAL                    
                    int year13 = request.getParameter("wb_year13")==null ? gc_now.get(Calendar.YEAR):Integer.parseInt(request.getParameter("wb_year13"));
                    out.println("<tr>");
                    out.println("<td>"+paramsRequest.getLocaleString("year")+":</td>");
                    out.println("<td><select id=\"wb_year13\" name=\"wb_year13\">");
                    for (int i = 2000; i < 2021; i++) {
                        out.println("<option value=\"" + i + "\"");
                        if (year13==i) {
                            out.println(" selected=\"selected\"");
                        }
                        out.println(">" + i + "</option>");
                    }
                    out.println("</select>");
                    out.println("</td>");
                    out.println("<td colspan=\"2\"><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"1\" /></td>");
                    out.println("</tr>");
                    out.println("</table></fieldset>");
                    
                    out.println("<fieldset>");
                    out.println("<table border=\"0\" width=\"95%\">");
                    out.println("<tr>");
                    out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onclick=\"doExcel('"+rtype+"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">"+paramsRequest.getLocaleString("spread_sheet")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">PDF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">RTF</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\">"+paramsRequest.getLocaleString("graph")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onclick=\"doHistogram('"+rtype+"', 'width=700, height=600, scrollbars, resizable, alwaysRaised, menubar')\">"+paramsRequest.getLocaleString("histogram")+"</button>&nbsp;");
                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">"+paramsRequest.getLocaleString("apply")+"</button>");
                    out.println(" </td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                    out.println("</form>");
                    
//                    out.println("<fieldset>");
//                    out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
//                    out.println("<tr>");
//                    out.println("<td colspan=\"4\">");
//                    out.println("<div id=\"ctnergrid\" style=\"height:400px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
//                    out.println("  <div id=\"gridMaster\"></div>");
//                    out.println("</div>");
                    out.println("<div id=\"ctnergrid\" style=\"height:350px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
                    out.println("  <div id=\"gridMaster\" jsid=\"gridMaster\"></div>");
                    out.println("</div>");
//                    out.println("</td>");
//                    out.println("</tr>");
//                    out.println("</table>");
//                    out.println("</fieldset>");
                    out.println("</div>");
                }
                out.println("</div>");
            }else {
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("device_report") + "</legend>");
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
        HashMap<String,String> params = new HashMap();
        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
        params.put("site", request.getParameter("wb_site"));

        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_DAILY_GRAPH;

                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // REPORTE MENSUAL                
                WBAFilterReportBean filter = new WBAFilterReportBean();
                String websiteId = request.getParameter("wb_site");
                filter.setSite(websiteId);

                String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
                deviceId = deviceId.replaceFirst("\\[", "");
                deviceId = deviceId.replaceFirst("\\]", "");
                deviceId = deviceId.replace(" ", "");
                if(!deviceId.equalsIgnoreCase("null")) {
                    filter.setIdaux(deviceId);
                }

                filter. setType(I_REPORT_TYPE);
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                filter.setYearI(year13);

                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_MONTHLY_GRAPH;
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
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
    public void doRepExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"lar.xls\"");
        Resource base = getResourceBase();
        HashMap<String,String> params = new HashMap();
        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
        params.put("site", request.getParameter("wb_site"));

        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // by day
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                //JasperTemplate jasperTemplate = JasperTemplate.DEVICE_DAILY;
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_DAILY_EXCEL;
                JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // by month                
                WBAFilterReportBean filter = new WBAFilterReportBean();
                String websiteId = request.getParameter("wb_site");
                filter.setSite(websiteId);

                String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
                deviceId = deviceId.replaceFirst("\\[", "");
                deviceId = deviceId.replaceFirst("\\]", "");
                deviceId = deviceId.replace(" ", "");
                if(!deviceId.equalsIgnoreCase("null")) {
                    filter.setIdaux(deviceId);
                }

                filter. setType(I_REPORT_TYPE);
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                filter.setYearI(year13);
                
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                //JasperTemplate jasperTemplate = JasperTemplate.DEVICE_MONTHLY;
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_MONTHLY_EXCEL;
                JRResource jrResource = new JRXlsResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
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
        PrintWriter out = response.getWriter();
        Document dom = SWBUtils.XML.getNewDocument();
        
        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));
            Iterator<SWBRecHit> itRecHits;
            int renglones = 0;
            Element report = dom.createElement("DeviceReport");
            dom.appendChild(report);
            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
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
                    Element device = dom.createElement("device");
                    device.appendChild(dom.createTextNode(rec.getItem()));
                    row.appendChild(device);
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
                WBAFilterReportBean filter = new WBAFilterReportBean();
                String websiteId = request.getParameter("wb_site");
                filter.setSite(websiteId);

                String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
                deviceId = deviceId.replaceFirst("\\[", "");
                deviceId = deviceId.replaceFirst("\\]", "");
                deviceId = deviceId.replace(" ", "");
                if(!deviceId.equalsIgnoreCase("null")) {
                    filter.setIdaux(deviceId);
                }

                filter. setType(I_REPORT_TYPE);
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                filter.setYearI(year13);
                
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
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
                    Element device = dom.createElement("device");
                    device.appendChild(dom.createTextNode(rec.getItem()));
                    row.appendChild(device);
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

    /**
     * Do rep pdf.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRepPdf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"lar.xls\"");
        Resource base = getResourceBase();
        HashMap<String,String> params = new HashMap();
        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
        params.put("site", request.getParameter("wb_site"));

        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_DAILY;
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // REPORTE MENSUAL
                WBAFilterReportBean filter = new WBAFilterReportBean();
                String websiteId = request.getParameter("wb_site");
                filter.setSite(websiteId);

                String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
                deviceId = deviceId.replaceFirst("\\[", "");
                deviceId = deviceId.replaceFirst("\\]", "");
                deviceId = deviceId.replace(" ", "");
                if(!deviceId.equalsIgnoreCase("null")) {
                    filter.setIdaux(deviceId);
                }

                filter. setType(I_REPORT_TYPE);
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                filter.setYearI(year13);
                
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_MONTHLY;                        
                JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch(Exception e) {
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
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
    public void doRepRtf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"lar.xls\"");
        Resource base = getResourceBase();
        HashMap<String,String> params = new HashMap();
        params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
        params.put("site", request.getParameter("wb_site"));

        try {
            int rtype = request.getParameter("wb_rtype")==null ? 0:Integer.parseInt(request.getParameter("wb_rtype"));            
            if(rtype == 0) { // REPORTE DIARIO
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_DAILY;
                JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }else { // REPORTE MENSUAL
                WBAFilterReportBean filter = new WBAFilterReportBean();
                String websiteId = request.getParameter("wb_site");
                filter.setSite(websiteId);

                String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
                deviceId = deviceId.replaceFirst("\\[", "");
                deviceId = deviceId.replaceFirst("\\]", "");
                deviceId = deviceId.replace(" ", "");
                if(!deviceId.equalsIgnoreCase("null")) {
                    filter.setIdaux(deviceId);
                }

                filter. setType(I_REPORT_TYPE);
                int year13 = Integer.parseInt(request.getParameter("wb_year13"));
                filter.setYearI(year13);
                
                JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
                JasperTemplate jasperTemplate = JasperTemplate.DEVICE_MONTHLY;                        
                JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, dataDetail.orderJRReport());
                jrResource.prepareReport();
                jrResource.exportReport(response);
            }
        }catch(Exception e) {
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), e);
            throw new SWBResourceException(e.getMessage());
        }
    }

    /**
     * Do histrogram.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doHistrogram(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        StringBuffer sb_ret = new StringBuffer();
        /*StringBuffer sb_app = new StringBuffer();*/
        Resource base = paramsRequest.getResourceBase();
        String monthinyear = null;
        boolean hasBarname = false;
        /*int j = 0;*/

        try {
            /*String rtype = request.getParameter("wb_rtype")==null? "0":request.getParameter("wb_rtype");*/
            monthinyear = request.getParameter("wbr_barname");
            if(monthinyear != null){
                hasBarname = true;
            }
            sb_ret.append("<html>");
            sb_ret.append("<head>");
            sb_ret.append("<title>"+paramsRequest.getLocaleString("device_report")+"</title>");
            sb_ret.append("</head>");
            sb_ret.append("<body>");
            sb_ret.append("<table border=\"0\" width=\"98%\">");
            sb_ret.append("<tr>");
            sb_ret.append("<td colpsan=\"3\" align=\"center\">");
            sb_ret.append("<img src=\""+SWBPortal.getContextPath()+"/swbadmin/images/swb-logo-hor.jpg\" alt=\"\" width=\"229\" height=\"44\" />");
            sb_ret.append("</td>");
            sb_ret.append("</tr>");
            sb_ret.append("<tr><td colpsan=\"3\">&nbsp;</td></tr>");
            sb_ret.append("<tr>");
            sb_ret.append("<td colpsan=\"3\" align=\"right\">");
            if(hasBarname) {
                sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"window.history.back()\" value=\""+paramsRequest.getLocaleString("back")+"\" name=\"btnBack\">&nbsp;");
            }
            sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"window.print()\" value=\""+paramsRequest.getLocaleString("print")+"\" name=\"btnPrint\">&nbsp;");
            sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"window.close()\" value=\""+paramsRequest.getLocaleString("close")+"\" name=\"btnClose\">");
            sb_ret.append("</td>");
            sb_ret.append("</tr>");
            sb_ret.append("<tr><td colpsan=\"3\" align=\"center\">"+request.getParameter("wb_site")+"</td></tr>");
            sb_ret.append("<tr>");
            sb_ret.append("<td colpsan=\"3\">");
            sb_ret.append(getHistogram(request, response, paramsRequest));
            sb_ret.append("\n</td>");
            sb_ret.append("</tr>");
            sb_ret.append("</table>");
            sb_ret.append("</body>");
            sb_ret.append("</html>");
        }
        catch (Exception e){
            log.error("Error on method doHistogram() resource " + strRscType + " with id " + base.getId(), e);
        }
        response.getWriter().print(sb_ret.toString());
    }

    /**
     * Gets the histogram.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @return the histogram
     * @throws SWBResourceException the sWB resource exception
     */
    public String getHistogram(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException {
        StringBuffer sb_ret = new StringBuffer();
        StringBuffer sb_app = new StringBuffer();
        boolean hasBarname = false;
        int j = 0;

        String rtype = request.getParameter("wb_rtype")==null? "1":request.getParameter("wb_rtype");
        String monthinyear = request.getParameter("wbr_barname");
        if(monthinyear != null){
            hasBarname = true;
        }

        sb_app.append("\n<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+ SWBPortal.getContextPath() + "/swbadmin/lib/SWBAplGraph.jar\" width=\"98%\" height=\"450\">");
        sb_app.append("<param name=\"GraphType\" value=\"Bar\">");
        sb_app.append("<param name=\"percent\" value=\"false\">");

        JRBeanCollectionDataSource  ds;
        String userLanguage = paramsRequest.getUser().getLanguage();

        WebSite ws = SWBContext.getWebSite(request.getParameter("wb_site"));
        if(ws != null) {
            final ArrayList<String> paternDevs = listDevices(request.getParameter("wb_site"), userLanguage);
            paternDevs.add(UNKNOW);
            WBAFilterReportBean filter;
            if(rtype.equals("0")) { // by day
                if(hasBarname) {
                    filter = new WBAFilterReportBean(userLanguage);
                    filter.setSite(request.getParameter("wb_site"));
                    String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
                    deviceId = deviceId.replaceFirst("\\[", "");
                    deviceId = deviceId.replaceFirst("\\]", "");
                    deviceId = deviceId.replace(" ", "");
                    if(!deviceId.equalsIgnoreCase("null")) {
                        filter.setIdaux(deviceId);
                    }
                    filter.setType(I_REPORT_TYPE);
                    filter.setGroupedDates(true);

                    SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
                    Date di = null;
                    try {
                        di = format.parse("01/"+monthinyear);
                    }catch(ParseException pe) {
                        throw new SWBResourceException(pe.getMessage());
                    }
                    GregorianCalendar ci = new GregorianCalendar();
                    ci.setTime(di);
                    GregorianCalendar cf = new GregorianCalendar();
                    cf.setTime(di);
                    cf.add(Calendar.DAY_OF_MONTH, cf.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
                    Date df = cf.getTime();
                    filter.setYearI(ci.get(Calendar.YEAR));
                    filter.setMonthI(ci.get(Calendar.MONTH)+1);
                    filter.setDayI(ci.get(Calendar.DAY_OF_MONTH));
                    filter.setYearF(cf.get(Calendar.YEAR));
                    filter.setMonthF(cf.get(Calendar.MONTH)+1);
                    filter.setDayF(cf.get(Calendar.DAY_OF_MONTH));
                }else {
                    try {
                        filter = buildFilter(request, paramsRequest);
                    }catch(IncompleteFilterException ife) {
                        throw new SWBResourceException(ife.getMessage());
                    }
                }
                Locale loc = new Locale(userLanguage);
                if(filter.isGroupedDates())
                    sb_app.append("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("daily_report")+". "+paramsRequest.getLocaleString("query_range")+": "+paramsRequest.getLocaleString("from")+" "+DateFormat.getDateInstance(DateFormat.MEDIUM, loc).format(filter.getDateI())+" "+paramsRequest.getLocaleString("to")+" "+DateFormat.getDateInstance(DateFormat.MEDIUM, loc).format(filter.getDateF())+"\">");
                else
                    sb_app.append("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("daily_report")+". "+paramsRequest.getLocaleString("query_range")+": "+DateFormat.getDateInstance(DateFormat.MEDIUM, loc).format(filter.getDateI())+"\">");
            }else { // by each month
                String websiteId = request.getParameter("wb_site")!=null?request.getParameter("wb_site") : (String)request.getAttribute("wb_site");
                int year13;
                try {
                    year13 = Integer.parseInt(request.getParameter("wb_year13"));
                }catch(NumberFormatException nfe) {
                    year13 = new GregorianCalendar().get(Calendar.YEAR);
                }
                filter = new WBAFilterReportBean();
                filter.setSite(websiteId);
                String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
                deviceId = deviceId.replaceFirst("\\[", "");
                deviceId = deviceId.replaceFirst("\\]", "");
                deviceId = deviceId.replace(" ", "");
                if(!deviceId.equalsIgnoreCase("null")) {
                    filter.setIdaux(deviceId);
                }
                filter. setType(I_REPORT_TYPE);
                filter.setYearI(year13);
                sb_app.append("\n<param name=\"Title\" value=\""+paramsRequest.getLocaleString("monthly_report")+". "+paramsRequest.getLocaleString("query_range")+": "+year13+"\">");
                String url = paramsRequest.getRenderUrl().toString()+"?wb_rtype=0&wb_rep_type=1&wb_site="+websiteId+"&wb_year13="+year13+"&wbr_barname=";
                sb_app.append("\n<param name=\"link\" value=\""+ url+ "\">");
            }
            JRDataSourceable dataDetail = new JRDeviceAccessDataDetail(filter);
            try {
                ds = dataDetail.orderJRReport();
            }catch(IncompleteFilterException ife) {
                throw new SWBResourceException(ife.getMessage());
            }
            ArrayList<SWBRecHit> rep = (ArrayList<SWBRecHit>)ds.getData();
            Date same = null;
            SWBRecHit rh;
            for(int k=0; k<rep.size(); ) {
                ArrayList<String> devices = (ArrayList<String>)paternDevs.clone();
                HashMap<String,SWBRecHit> m = new HashMap();
                do {
                    rh = rep.get(k);
                    m.put(rh.getItem(), rh);
                    devices.remove(rh.getItem());
                    if(k+1<rep.size())
                        same = rep.get(k+1).getDate();
                    k++;
                }while( k<rep.size()&&rh.getDate().equals(same) );
                devices.trimToSize();
                for(int i=0; i<devices.size(); i++) {
                    m.put(devices.get(i), new SWBRecHit());
                }

                StringBuilder data = new StringBuilder();
                if(rtype.equals("0")) {
                    sb_app.append("<param name=\"label" + j + "\" value=\""+rh.getDay()+"/"+rh.getMonth("MMM")+"\">");
                }else {
                    sb_app.append("<param name=\"label" + j + "\" value=\""+rh.getMonth("MMM")+"/"+rh.getYear()+"\">");
                }
                for(int i=0; i<paternDevs.size(); i++) {
                    rh = m.get(paternDevs.get(i));
                    data.append(rh.getHits());
                    if( i+1<paternDevs.size() )
                        data.append("|");
                }
                sb_app.append("<param name=\"data" + j + "\" value=\""+data+"\">");
                j++;

                devices = null;
                m = null;
                data = null;
            }
            sb_app.append("<param name=\"ncdata\" value=\""+(SWBUtils.Collections.sizeOf(ws.listDevices())+1)+"\">");
            sb_app.append("<param name=\"ndata\" value=\""+ j +"\">");
            Iterator<Device> devs = ws.listDevices();
            j = 0;
            while(devs.hasNext()) {
                Device device = devs.next();
                sb_app.append("<param name=\"barname"+j+"\" value=\""+device.getDisplayTitle(userLanguage)+"\">");
                j++;
            }
            sb_app.append("<param name=\"barname"+j+"\" value=\""+UNKNOW+"\">");

            sb_app.append("<param name=\"color0\" value=\"100,100,255\">");
            sb_app.append("<param name=\"color1\" value=\"255,100,100\">");
            sb_app.append("<param name=\"color2\" value=\"100,255,100\">");
            sb_app.append("<param name=\"color3\" value=\"100,250,250\">");
            sb_app.append("<param name=\"color4\" value=\"250,100,250\">");
            sb_app.append("<param name=\"color5\" value=\"250,250,100\">");
            sb_app.append("<param name=\"color6\" value=\"0,0,250\">");
            sb_app.append("<param name=\"color7\" value=\"250,0,0\">");
            sb_app.append("<param name=\"color8\" value=\"221,196,49\">");
            sb_app.append("<param name=\"color9\" value=\"0,250,250\">");
            sb_app.append("<param name=\"color10\" value=\"250,0,250\">");
            sb_app.append("<param name=\"color11\" value=\"200,200,100\">");
            sb_app.append("<param name=\"color12\" value=\"230,180,80\">");
            sb_app.append("<param name=\"color13\" value=\"149,0,149\">");
            sb_app.append("<param name=\"color14\" value=\"75,0,130\">");
            sb_app.append("<param name=\"color15\" value=\"221,88,0\">");
            sb_app.append("<param name=\"color15\" value=\"255,245,238\">");

            sb_app.append("<param name=\"zoom\" value=\"true\">");
            sb_app.append("\n</APPLET>");

            // Evaluates if there are records
            if(ds.getData().isEmpty())
                sb_ret.append("\n<br/><br/><br/><br/><font color=\"black\">"+paramsRequest.getLocaleString("no_records")+"</font>");
            else
                sb_ret.append(sb_app.toString());
        }
        return sb_ret.toString();
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
        String websiteId = request.getParameter("wb_site");

        String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
        deviceId = deviceId.replaceFirst("\\[", "");
        deviceId = deviceId.replaceFirst("\\]", "");
        deviceId = deviceId.replace(" ", "");

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
            filterReportBean = new WBAFilterReportBean(paramsRequest.getUser().getLanguage());
            filterReportBean.setSite(websiteId);
            if(!deviceId.equalsIgnoreCase("null")) {
                filterReportBean.setIdaux(deviceId);
            }
            filterReportBean.setType(I_REPORT_TYPE);
            if(groupDates==0) { // radio button was 0. Select only one date
                filterReportBean.setGroupedDates(false);

                String[] numFecha = fecha1.split("-");
                filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayI(Integer.parseInt(numFecha[2]));
            }else { // radio button was 1. Select between two dates
                filterReportBean.setGroupedDates(true);

                String[] numFecha = fecha11.split("-");
                filterReportBean.setYearI(Integer.parseInt(numFecha[0]));
                filterReportBean.setMonthI(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayI(Integer.parseInt(numFecha[2]));

                numFecha = fecha12.split("-");
                filterReportBean.setYearF(Integer.parseInt(numFecha[0]));
                filterReportBean.setMonthF(Integer.parseInt(numFecha[1]));
                filterReportBean.setDayF(Integer.parseInt(numFecha[2]));
            }
        }catch (Exception e) {
            log.error("Error on method buildFilter() resource " + strRscType + " with id " + getResourceBase().getId(), e);
            throw(new SWBResourceException(e.getMessage()));
        }       
        return filterReportBean;
    }

    /**
     * List devices.
     * 
     * @param websiteId the website id
     * @param language the language
     * @return the array list
     */
    public static ArrayList<String> listDevices(String websiteId, String language) {
        ArrayList devices = new ArrayList();
        WebSite ws = SWBContext.getWebSite(websiteId);
        Iterator<Device> devs = ws.listDevices();
        while(devs.hasNext()) {
            Device device = devs.next();
            devices.add(device.getDisplayTitle(language));
        }
        return devices;
    }

    private void doFillReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jobj.put("label", "sect");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
        }

        GregorianCalendar gc_now = new GregorianCalendar();
        String websiteId =  request.getParameter("wb_site");
        String wb_rtype = request.getParameter("wb_rtype");

        JRDataSourceable dataDetail = null;

        if(wb_rtype.equals("0")) { //Daily Report
            try{
                WBAFilterReportBean filter = buildFilter(request, paramsRequest);
                dataDetail = new JRDeviceAccessDataDetail(filter);
            }catch(IncompleteFilterException ife) {
                log.error("Error on method doFillReport() ", ife);
            }
        }else{ //Monthly Report
            WBAFilterReportBean filter = new WBAFilterReportBean();
            int year13 = request.getParameter("wb_year13")==null ? gc_now.get(Calendar.YEAR):Integer.parseInt(request.getParameter("wb_year13"));
            String deviceId = Arrays.toString(request.getParameterValues("wb_device"));
            filter.setSite(websiteId);

            deviceId = deviceId.replaceFirst("\\[", "");
            deviceId = deviceId.replaceFirst("\\]", "");
            deviceId = deviceId.replace(" ", "");
            if(!deviceId.equalsIgnoreCase("null")) {
                filter.setIdaux(deviceId);
            }
            filter. setType(I_REPORT_TYPE);            
            filter.setYearI(year13);
            dataDetail = new JRDeviceAccessDataDetail(filter);
        }

        try {
            JRBeanCollectionDataSource dataSource = dataDetail.orderJRReport();
            Collection<SWBRecHit> collection = dataSource.getData();
            Iterator<SWBRecHit> iterator = collection.iterator();

            while(iterator.hasNext()){
                JSONObject obj = new JSONObject();
                SWBRecHit recHit = iterator.next();
                try {
                    obj.put("dispositivo", recHit.getItem());
                    obj.put("sitio", websiteId);
                    obj.put("anio", recHit.getYear());
                    obj.put("mes", recHit.getMonth());
                    if(wb_rtype.equals("0")){
                        obj.put("dia", recHit.getDay());
                    }
                    obj.put("accesos", recHit.getHits());
                    jarr.put(obj);
                }catch (JSONException jsone) {
                    log.error("Error on method doFillReport() ",jsone);
                }
            }
        }catch (Exception e) {
            log.error("Error on method doFillReport() ", e);
        }
        response.getOutputStream().println(jobj.toString());
    }
}
