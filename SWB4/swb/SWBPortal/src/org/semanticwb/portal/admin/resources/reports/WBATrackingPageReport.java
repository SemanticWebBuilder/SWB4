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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;
import org.semanticwb.portal.admin.resources.reports.jrresources.*;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRSectionAccessDataDetail;
import org.semanticwb.portal.db.SWBRecHit;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.semanticwb.SWBPortal;
import org.semanticwb.portal.util.SelectTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;

/**
 *
 * @author Carlos Ramos
 * @since 4.5
 */


/**
 * The Class WBASectionReport.
 */
public class WBATrackingPageReport extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBASectionReport.class);

    /** The Constant I_REPORT_TYPE. */
    public static final int I_REPORT_TYPE = 3;
    
    /** The str rsc type. */
    private String strRscType;
    
    private static final String Mode_RENDER_DataTable = "rdt";
    private static final String Mode_RENDER_Tree = "rst";
    private static final String DAILY_LOGGIN = "daily";
    private static final String MONTHLY_LOGGIN = "monthly";
    private static final String YEARLY_LOGGIN = "yearly";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#init()
     */
    @Override
    public void init(){
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBASectionReport";
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        final String mode = paramsRequest.getMode();
        
        if(Mode_RENDER_Tree.equalsIgnoreCase(mode)) {
            doRenderSectionTree(request,response,paramsRequest);
        }else if(Mode_RENDER_DataTable.equalsIgnoreCase(mode)) {
            doFillReport(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("xls")) {
            doRepExcel(request,response,paramsRequest);
        }else{
            super.processRequest(request, response, paramsRequest);
        }
    }
    
    /**
     * Do render section tree.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderSectionTree(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        
        HashMap params = new HashMap();
        Enumeration<String> names = request.getParameterNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        
        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        
        String websiteId = request.getParameter("site");
        String section = request.getParameter("reptp");
        if(section != null) {
            out.println("<input type=\"hidden\" name=\"section\" id=\"section\" value=\""+section+"\" />");
        }
        
        SelectTree tree = new SelectTree(paramsRequest.getUser().getLanguage());
        out.println(tree.renderXHTML(websiteId, params, url.setMode(Mode_RENDER_Tree).toString()));
        out.flush();
        out.close();
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
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();

        try
        {    
            // If there are sites continue
            if(SWBContext.listWebSites().hasNext())
            {
                String address = paramsRequest.getWebPage().getUrl();
                String websiteId = request.getParameter("wb_site")==null ? SWBContext.listWebSites().next().getId():request.getParameter("wb_site");
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                SWBResourceURL url=paramsRequest.getRenderUrl();
                url.setCallMethod(SWBResourceURL.Call_DIRECT);

                out.println("<script type=\"text/javascript\">");
                
                out.println("  dojo.require(\"dijit.form.DateTextBox\");");
                out.println("  dojo.require(\"dojox.grid.DataGrid\");");//--
                out.println("  dojo.require(\"dojo.data.ItemFileReadStore\");");//--
                out.println("  dojo.require(\"dijit.form.TimeTextBox\");");
                
                out.println("  dojo.addOnLoad(function() {");
                out.println("    var t = new Date();");
                out.println("    var t1 = new dijit.form.TimeTextBox({name:\"t1\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 0, 0),");
                out.println("                 constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}");
                out.println("    }, \"wb_t11\");");

                out.println("    var t2 = new dijit.form.TimeTextBox({name:\"t2\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 23, 59),");
                out.println("                 constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}");
                out.println("    }, \"wb_t12\");");
                out.println("  });");
                
                out.println("dojo.addOnLoad(function(){getHtml('"+url.setMode(Mode_RENDER_Tree)+"'+'?site="+websiteId+"','slave')});");

                out.println("function fillGrid(grid, uri) {");
                out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri});");
                out.println("   grid._refresh();");
                out.println("}");

                out.println("var layout= null;");
                out.println("var jStrMaster = null;");
                out.println("var gridMaster = null;");
                out.println("var gridResources = null;");

                out.println("  dojo.addOnLoad(function() {");
                out.println("    layout= [");
                out.println("      { field:\"rep\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblRepository") + "\" },");
                out.println("      { field:\"login\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblUserName") + "\" },");
                out.println("      { field:\"ln\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblFirstName") + "\" },");
                out.println("      { field:\"sln\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblLastName") + "\" },");
                out.println("      { field:\"n\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblName") + "\" },");
                out.println("      { field:\"year\", width:\"50px\", name:\"" + paramsRequest.getLocaleString("lblYear") + "\" },");
                out.println("      { field:\"month\", width:\"50px\", name:\"" + paramsRequest.getLocaleString("lblMonth") + "\" },");
                out.println("      { field:\"day\", width:\"50px\", name:\"" + paramsRequest.getLocaleString("lblDay") + "\" },");
                out.println("      { field:\"time\", width:\"50px\", name:\"" + paramsRequest.getLocaleString("lblHour") + "\" },");
                out.println("      { field:\"milis\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblDuration") + "\" }");
                out.println("    ];");

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

                out.println("  function getParams() { ");
                out.println("    var params = \"?\";");
                out.println("    params = params + \"wb_site=\" + window.document.frmrep.wb_site.value;");
                out.println("    params = params + \"&section=\" + dojo.byId('section').value;");
                out.println("    params += '&fecha11='+dojo.byId('wb_fecha11').value;");
                out.println("    params += '&t11='+dojo.byId('wb_t11').value;");
                out.println("    params += '&fecha12='+dojo.byId('wb_fecha12').value;");
                out.println("    params += '&t12='+dojo.byId('wb_t12').value;");
                out.println("    return params;");
                out.println("  } ");

                out.println("  function doXml(size) { ");
                out.println("    if(dojo.byId('section')) {");
                out.println("      var params = getParams();");
                out.println("      window.open(\"" + url.setMode(SWBResourceURL.Mode_XML) + "\"+params, 'xml', size);");
                out.println("    }else {");
                out.println("      alert('Para mostrar el resumen de contenido, primero debe seleccionar una sección');");
                out.println("    }");
                out.println("  }");

                out.println("  function doExcel(size) { ");
                out.println("    if(dojo.byId('section')) {");
                out.println("      var params = getParams(accion);");
                out.println("      window.open(\"" + url.setMode("xls") + "\"+params,\"graphWindow\",size);");
                out.println("    }else {");
                out.println("      alert('Para poder mostrarle el resumen de contenido, primero debe seleccionar una sección');");
                out.println("    }");
                out.println("  }");
                
                out.println("function doApply() {");
                out.println("   if(dojo.byId('section')) {");
                out.println("       var grid = dijit.byId('gridMaster');");
                out.println("       var params = getParams();");
                out.println("       fillGrid(grid, '"+url.setMode(Mode_RENDER_DataTable)+"'+params);");
                out.println("   }else{");
                out.println("      alert('Para poder mostrarle el resumen de contenido, primero debe seleccionar una sección');");
                out.println("   }");
                out.println("}");
                
                out.println("</script>");
                // javascript
                
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("Reporte de accesos");
                out.println("</fieldset>");

                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\""+address+"\">");
                out.println("<fieldset>");
                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
                out.println("<tr><td width=\"183\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"443\"></td></tr>");
                
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("lblSite") + ":</td>");
                out.println("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" onchange=\"getHtml('"+url.setMode(Mode_RENDER_Tree)+"'+'?site='+this.value,'slave');\">");
                Iterator<WebSite> wsites = SWBContext.listWebSites(false);
                while(wsites.hasNext()) {
                    WebSite ws = wsites.next();
                    out.println("<option value=\"" + ws.getId() + "\"");
                    if(ws.getId().equalsIgnoreCase(websiteId)) {
                        out.println(" selected=\"selected\"");
                    }
                    out.println(">" + ws.getSemanticObject().getDisplayName(paramsRequest.getUser().getLanguage()) + "</option>");
                }                    
                out.println("</select>");
                out.println("</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("lblPage") + ":</td>");                
                out.println("<td colspan=\"3\"><div id=\"slave\"></div></td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td colspan=\"4\">");
                out.println("</td>");
                out.println("</tr>");
                    
                GregorianCalendar now = new GregorianCalendar();
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("lblDateRange") + ":</td>");
                out.println("<td align=\"left\" colspan=\"3\">");
                out.println("<label for=\"wb_fecha11\">" + paramsRequest.getLocaleString("lblFrom") + ":&nbsp;</label>");
                out.println("<input type=\"text\" name=\"wb_fecha11\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha11\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />");
                out.println("&nbsp;&nbsp;");
                out.println("<label for=\"wb_t11\">" + paramsRequest.getLocaleString("lblTime") + ":&nbsp;</label>");
                out.println("<input type=\"text\" name=\"wb_t11\" id=\"wb_t11\" />");
                out.println("</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td>&nbsp;</td>");
                out.println("<td align=\"left\" colspan=\"3\">");
                out.println("<label for=\"wb_fecha12\">&nbsp;&nbsp;" + paramsRequest.getLocaleString("lblTo") + ":&nbsp;</label>");
                out.println("<input type=\"text\" name=\"wb_fecha12\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha12\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />");
                out.println("&nbsp;&nbsp;");
                out.println("<label for=\"wb_t12\">" + paramsRequest.getLocaleString("lblTime") + ":&nbsp;</label>");
                out.println("<input type=\"text\" name=\"wb_t12\" id=\"wb_t12\" />");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table></fieldset>");

                out.println("<fieldset>");
                out.println("<table border=\"0\" width=\"95%\">");
                out.println("<tr>");
                out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">" + paramsRequest.getLocaleString("lblXml") + "</button>&nbsp;");
                out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">" + paramsRequest.getLocaleString("lblSpreadsheet") + "</button>&nbsp;");
                out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">" + paramsRequest.getLocaleString("lblApply") + "</button>");
                out.println(" </td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</form>");
                out.println("<div id=\"ctnergrid\" style=\"height:350px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
                out.println("  <div id=\"gridMaster\" jsid=\"gridMaster\"></div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
            }else { // There are not sites and displays a message
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("section_report") + "</legend>");
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
    
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        final String wsId = request.getParameter("wb_site");
        WebSite ws = SWBContext.getWebSite(wsId);
        if(ws==null) {
            log.error("Repositorio de usuarios incorrecto");
            return;
        }
        final String wpId = request.getParameter("section");
        WebPage wp = ws.getWebPage(wpId);
        if(wp==null) {
            log.error("Usuario con login "+wpId+" no existe en el repositorio con identificador "+wsId);
            return;
        }

        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jobj.put("items", jarr);
        }catch (JSONException jse) {
            throw new IOException(jse);
        }

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date11 = request.getParameter("fecha11")==null ? sdf.format(now):request.getParameter("fecha11")+" "+(request.getParameter("t11")==null ? "00:00":request.getParameter("t11"));
        Date first;
        try {
            first = sdf.parse(date11);
        }catch(ParseException pe){
            first = new Date();
        }

        String date12 = request.getParameter("fecha12")==null ? sdf.format(now):request.getParameter("fecha12")+" "+(request.getParameter("t12")==null ? "23:59":request.getParameter("t12"));
        Date last;
        try {
            last = sdf.parse(date12);
        }catch(ParseException pe){
            last = new Date();
        }

        String urId, login;
        UserRepository ur;
        User visitor;
        Iterator<String[]> lines = getReportResults(wsId, wpId, first, last);
        while(lines.hasNext()) {
            String[] t = lines.next();
            JSONObject obj = new JSONObject();
            try {
                ws = SWBContext.getWebSite(t[4]);
                if(ws==null) {
                    log.error("Modelo con identificador "+t[4]+" no existe");
                    continue;
                }
                wp = ws.getWebPage(t[5]);
                if(wp==null) {
                    log.error("Pagina web con identificador "+t[5]+" no existe");
                    continue;
                }
                urId = t[6];
                ur = SWBContext.getUserRepository(urId);
                if(ur==null) {
                    continue;
                }
                obj.put("rep", t[6]);
                obj.put("login", t[7]);
                login = t[7];
                if("_".equals(login)) {
                    obj.put("ln", "_");
                    obj.put("sln", "_");
                    obj.put("n", "_");
                }else {
                    visitor = ur.getUserByLogin(login);
                    if(visitor==null) {
                        continue;
                    }
                    obj.put("ln", visitor.getLastName());
                    obj.put("sln", visitor.getSecondLastName());
                    obj.put("n", visitor.getName());
                }
                try {
                    obj.put("year", Integer.parseInt(t[0].substring(0,4)));
                }catch(NumberFormatException nfe) {
                    obj.put("year", t[0].substring(0,4));
                }
                try {
                    obj.put("month", Integer.parseInt(t[0].substring(5,7)));
                }catch(NumberFormatException nfe) {
                    obj.put("month", t[0].substring(5,7));
                }
                try {
                    obj.put("day", Integer.parseInt(t[0].substring(8,10)));
                }catch(NumberFormatException nfe) {
                    obj.put("day", t[0].substring(8,10));
                }
                obj.put("time", t[0].substring(11,16));
                try {
                    obj.put("milis", Long.parseLong(t[11]));
                }catch(NumberFormatException nfe) {
                    obj.put("milis", t[11]);
                }                
                jarr.put(obj);
            }catch (JSONException jsone) {
                log.error(jsone);
            }
        }
        StringBuilder xml = new StringBuilder();
        try {
            xml.append("<?xml version=\"1.0\"?>");
            xml.append("<tracking>");
            xml.append(org.json.XML.toString(jobj));
            xml.append("</tracking>");
        }catch(JSONException jse) {
            xml.append("<?xml version=\"1.0\"?>");
        }
        out.print(xml);
        out.flush();
        out.close();
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
        response.setHeader("Content-Disposition", "inline; filename=\"scr.xls\"");

        // Resource base = getResourceBase();
        PrintWriter out = response.getWriter();

        final String wsId = request.getParameter("wb_site");
        WebSite ws = SWBContext.getWebSite(wsId);
        if (ws == null) {
            log.error("Repositorio de usuarios incorrecto");
            return;
        }
        final String wpId = request.getParameter("section");
        WebPage wp = ws.getWebPage(wpId);
        if (wp == null) {
            log.error("Usuario con login " + wpId + " no existe en el repositorio con identificador " + wsId);
            return;
        }

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date11 = request.getParameter("fecha11") == null ? sdf.format(now) : request.getParameter("fecha11") + " " + (request.getParameter("t11") == null ? "00:00" : request.getParameter("t11"));
        Date first;
        try {
            first = sdf.parse(date11);
        } catch (ParseException pe) {
            first = new Date();
        }

        String date12 = request.getParameter("fecha12") == null ? sdf.format(now) : request.getParameter("fecha12") + " " + (request.getParameter("t12") == null ? "23:59" : request.getParameter("t12"));
        Date last;
        try {
            last = sdf.parse(date12);
        } catch (ParseException pe) {
            last = new Date();
        }

        String urId, login;
        UserRepository ur;
        User visitor;
        Iterator<String[]> lines = getReportResults(wsId, wpId, first, last);
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblRepository"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblUserName"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblFirstName"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblLastName"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblName"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblYear"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblMonth"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblDay"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblHour"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblDuration"));
        out.println("</th>");
        out.println("</tr>");

        while (lines.hasNext()) {
            String[] t = lines.next();
            //JSONObject obj = new JSONObject();
//            try {
            ws = SWBContext.getWebSite(t[4]);
            if (ws == null) {
                log.error("Modelo con identificador " + t[4] + " no existe");
                continue;
            }
            wp = ws.getWebPage(t[5]);
            if (wp == null) {
                log.error("Pagina web con identificador " + t[5] + " no existe");
                continue;
            }
            urId = t[6];
            ur = SWBContext.getUserRepository(urId);
            if (ur == null) {
                continue;
            }
//                obj.put("rep", t[6]);
//                obj.put("login", t[7]);
            String rep = t[6];
            String ln = "";
            String sln = "";
            String n = "";
            String year = "";
            String month = "";
            String day = "";
            String times = "";
            String millis = "";
            login = t[7];
            if ("_".equals(login)) {
                ln = "_";
                sln = "_";
                n = "_";
//                    obj.put("ln", "_");
//                    obj.put("sln", "_");
//                    obj.put("n", "_");
            } else {
                visitor = ur.getUserByLogin(login);
                if (visitor == null) {
                    continue;
                }
                ln = visitor.getLastName();
                sln = visitor.getSecondLastName();
                n = visitor.getName();
//                    obj.put("ln", visitor.getLastName());
//                    obj.put("sln", visitor.getSecondLastName());
//                    obj.put("n", visitor.getName());
            }

            //obj.put("uri", webPage.getURI());
            //obj.put("rep", t[6]);
            //obj.put("login", t[7]);
            //obj.put("ln", visitor.getLastName());
            //obj.put("sln", visitor.getSecondLastName());
            //obj.put("n", visitor.getName());
            int y = 0;
            int m = 0;
            int d = 0;
            try {
                y = Integer.parseInt(t[0].substring(0, 4));
                year = y + "";
                //obj.put("year", Integer.parseInt(t[0].substring(0, 4)));
            } catch (NumberFormatException nfe) {
                year = t[0].substring(0, 4);
                //obj.put("year", t[0].substring(0, 4));
            }
            try {
                m = Integer.parseInt(t[0].substring(5, 7));
                month = m + "";
                //obj.put("month", Integer.parseInt(t[0].substring(5, 7)));
            } catch (NumberFormatException nfe) {
                month = t[0].substring(5, 7);
                //obj.put("month", t[0].substring(5, 7));
            }
            try {
                d = Integer.parseInt(t[0].substring(8, 10));
                day = "" + d;
                //obj.put("day", Integer.parseInt(t[0].substring(8, 10)));
            } catch (NumberFormatException nfe) {
                day = t[0].substring(8, 10);
                //obj.put("day", t[0].substring(8, 10));
            }
            times = t[0].substring(11, 16);
            //obj.put("time", t[0].substring(11, 16));
            try {
                long l = Long.parseLong(t[11]);
                millis = l + "";
                //obj.put("milis", Long.parseLong(t[11]));
            } catch (NumberFormatException nfe) {
                //obj.put("milis", t[11]);
                millis = t[11];
            }



//                jarr.put(obj);
//            } catch (JSONException jsone) {
//                log.error(jsone);
//            }
            out.println("<tr>");
            out.println("<td>");
            out.println(rep);
            out.println("</td>");            
            out.println("<td>");
            out.println(n);
            out.println("</td>");
            out.println("<td>");
            out.println(ln);
            out.println("</td>");
            out.println("<td>");
            out.println(sln);
            out.println("</td>");
            out.println("<td>");
            out.println(year);
            out.println("</td>");
            out.println("<td>");
            out.println(month);
            out.println("</td>");            
            out.println("<td>");
            out.println(day);
            out.println("</td>");   
            out.println("<td>");
            out.println(times);
            out.println("</td>");   
            out.println("<td>");
            out.println(millis);
            out.println("</td>");   
            out.println("</tr>");
        }
        out.println("</table>");
        //out.print(SWBUtils.XML.domToXml(dom));

        out.flush();
        out.close();
    }
    
    /**
     * Do fill report.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doFillReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        
        final String wsId = request.getParameter("wb_site");
        WebSite ws = SWBContext.getWebSite(wsId);
        if(ws==null) {
            log.error("Repositorio de usuarios incorrecto");
            return;
        }
        final String wpId = request.getParameter("section");
        WebPage wp = ws.getWebPage(wpId);
        if(wp==null) {
            log.error("Usuario con login "+wpId+" no existe en el repositorio con identificador "+wsId);
            return;
        }
        
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            //jobj.put("identifier", "uri");
            jobj.put("label", "sect");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
            throw new IOException(jse);
        }
        
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date11 = request.getParameter("fecha11")==null ? sdf.format(now):request.getParameter("fecha11")+" "+(request.getParameter("t11")==null ? "00:00":request.getParameter("t11"));
        Date first;
        try {
            first = sdf.parse(date11);
        }catch(ParseException pe){
            first = new Date();
        }
        
        String date12 = request.getParameter("fecha12")==null ? sdf.format(now):request.getParameter("fecha12")+" "+(request.getParameter("t12")==null ? "23:59":request.getParameter("t12"));
        Date last;
        try {
            last = sdf.parse(date12);
        }catch(ParseException pe){
            last = new Date();
        }
        
        String urId, login;
        UserRepository ur;
        User visitor;
        Iterator<String[]> lines = getReportResults(wsId, wpId, first, last);
        while(lines.hasNext()) {
            String[] t = lines.next();
            JSONObject obj = new JSONObject();
            try {
                ws = SWBContext.getWebSite(t[4]);
                if(ws==null) {
                    log.error("Modelo con identificador "+t[4]+" no existe");
                    continue;
                }
                wp = ws.getWebPage(t[5]);
                if(wp==null) {
                    log.error("Pagina web con identificador "+t[5]+" no existe");
                    continue;
                }
                urId = t[6];
                ur = SWBContext.getUserRepository(urId);
                if(ur==null) {
                    continue;
                }
                obj.put("rep", t[6]);
                obj.put("login", t[7]);
                login = t[7];
                if("_".equals(login)) {
                    obj.put("ln", "_");
                    obj.put("sln", "_");
                    obj.put("n", "_");
                }else {
                    visitor = ur.getUserByLogin(login);
                    if(visitor==null) {
                        continue;
                    }
                    obj.put("ln", visitor.getLastName());
                    obj.put("sln", visitor.getSecondLastName());
                    obj.put("n", visitor.getName());
                }
                
                //obj.put("uri", webPage.getURI());
                //obj.put("rep", t[6]);
                //obj.put("login", t[7]);
                //obj.put("ln", visitor.getLastName());
                //obj.put("sln", visitor.getSecondLastName());
                //obj.put("n", visitor.getName());
                try {
                    obj.put("year", Integer.parseInt(t[0].substring(0,4)));
                }catch(NumberFormatException nfe) {
                    obj.put("year", t[0].substring(0,4));
                }
                try {
                    obj.put("month", Integer.parseInt(t[0].substring(5,7)));
                }catch(NumberFormatException nfe) {
                    obj.put("month", t[0].substring(5,7));
                }
                try {
                    obj.put("day", Integer.parseInt(t[0].substring(8,10)));
                }catch(NumberFormatException nfe) {
                    obj.put("day", t[0].substring(8,10));
                }
                obj.put("time", t[0].substring(11,16));
                try {
                    obj.put("milis", Long.parseLong(t[11]));
                }catch(NumberFormatException nfe) {
                    obj.put("milis", t[11]);
                }                
                jarr.put(obj);
            }catch (JSONException jsone) {
                log.error(jsone);
            }
        }
        response.getOutputStream().println(jobj.toString());
    }
    
    /**
     * Gets the report results.
     * 
     * @param request the request
     * @param paramsRequest the params request
     * @return the report results
     */
    private Iterator<String[]> getReportResults(final String wsId, final String wpId, final Date s, final Date e) {
        GregorianCalendar datefile = null;

        String line = null;
        String s_aux = null;
        String filename = null;

        String yearinfile = null;
        String monthinfile = null;
        String dayinfile = null;
        String hourinfile = null;
        String mininfile = null;
        
        final WebSite ws = SWBContext.getWebSite(wsId);
        final WebPage wp = ws.getWebPage(wpId);
        
        GregorianCalendar start = new GregorianCalendar();
        start.setTime(s);
        GregorianCalendar end = new GregorianCalendar();
        end.setTime(e);
        
        List<String[]> list_rep = new ArrayList<String[]>();
        Iterator<String> files = getFileNames((GregorianCalendar)start.clone(), (GregorianCalendar)end.clone());
        if(files.hasNext()) {                
            String s_key=null;
            while(files.hasNext()) {
                filename = files.next();
                File f = new File(filename);
                if(f.exists())
                {
                    RandomAccessFile rf_in = null;
                    try {
                        rf_in = new RandomAccessFile(f,"r");
                    }catch(FileNotFoundException fnfe) {
                        continue;
                    }                    
                    try {
                    while( (line = rf_in.readLine())!=null ) {
                        String t[]=line.split("\\|");
                        //0-s_aux receives date
                        s_aux = t[0];
                        //Evaluates if dates are correct
                        yearinfile = s_aux.substring(0,4);
                        monthinfile = s_aux.substring(5,7);
                        dayinfile = s_aux.substring(8,10);
                        hourinfile = s_aux.substring(11,13);
                        mininfile = s_aux.substring(14,16);
                        datefile = new GregorianCalendar(Integer.parseInt(yearinfile),Integer.parseInt(monthinfile)-1,Integer.parseInt(dayinfile),Integer.parseInt(hourinfile),Integer.parseInt(mininfile));
//                        if((datefile.after(start) || datefile.equals(start)) && ((datefile.before(end) || datefile.equals(end)))) {
//                            dateinfile = s_aux;
//                        }else {
//                            continue;
//                        }
                        boolean intime = (datefile.after(start) || datefile.equals(start)) && ((datefile.before(end) || datefile.equals(end)));
                        if(!intime) {
                            continue;
                        }
                        //4-s_aux receives website id, this value no matter
                        if( wsId!=null && !wsId.equalsIgnoreCase(t[4]) ) {
                            continue;
                        }
                        //5-s_aux receives section id
                        if( wpId!=null && !wpId.equalsIgnoreCase(t[5]) ) {
                            continue;
                        }
                        list_rep.add(t);
                    }
                    }catch(IOException ioe) {
                        log.error(ioe);
                        continue;
                    }
//                    while( line != null );
                    if(rf_in != null ) {
                        try {
                            rf_in.close();
                        }catch(IOException ioe) {
                            rf_in = null;
                        }
                    }
                } // f.exists()
                else
                {
                    log.error("File " + filename + " not found on method getReportResults() resource " + strRscType + " with id " +  getResourceBase().getId());
                }
            }
        }
        return list_rep.iterator();
    }
    
    /**
     * Gets the file names.
     * 
     * @param siteId The id of the site
     * @param first
     * @param last
     * @return the file names
     */
    public Iterator<String> getFileNames(GregorianCalendar first, GregorianCalendar last) {
        ArrayList files = new ArrayList();

        String accessLogPath = SWBPlatform.getEnv("swb/accessLog");
        String period = SWBPlatform.getEnv("swb/accessLogPeriod");
        String path = SWBPortal.getWorkPath();

        SimpleDateFormat sdf;
        
        Iterator<WebSite> sites = SWBContext.listWebSites(false);
        while(sites.hasNext()) {            
            final String realpath = path + accessLogPath + "_" + sites.next().getId() + "_acc.";

            if(DAILY_LOGGIN.equalsIgnoreCase(period)) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                while( !first.after(last) ) {
                    files.add(realpath+sdf.format(first.getTime())+".log");
                    first.add(Calendar.DATE, 1);
                }
            }else if(MONTHLY_LOGGIN.equalsIgnoreCase(period)) {
                sdf = new SimpleDateFormat("yyyy-MM");
                int addedDays;
                while( !first.after(last) ) {
                    files.add(realpath+sdf.format(first.getTime())+".log");
                    addedDays = first.getActualMaximum(Calendar.DAY_OF_MONTH)-first.get(Calendar.DAY_OF_MONTH)+1;
                    first.add(Calendar.DATE, addedDays);
                }

            }else if(YEARLY_LOGGIN.equalsIgnoreCase(period)) {
                sdf = new SimpleDateFormat("yyyy");
                int addedDays;
                while( !first.after(last) ) {
                    files.add(realpath+sdf.format(first.getTime())+".log");
                    addedDays = first.getActualMaximum(Calendar.DAY_OF_YEAR)-first.get(Calendar.DAY_OF_YEAR)+1;
                    first.add(Calendar.DATE, addedDays);
                }
            }
        }
        return files.iterator();
    }
}


