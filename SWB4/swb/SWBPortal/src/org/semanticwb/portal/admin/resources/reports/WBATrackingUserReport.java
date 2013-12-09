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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.SWBPortal;
import org.semanticwb.portal.api.SWBResourceURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

// TODO: Auto-generated Javadoc
/**
 * The Class WBALoginReport.
 */
public class WBATrackingUserReport extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBATrackingUserReport.class);

    /** The Constant S_REPORT_IDAUX. */
    public static final String S_REPORT_IDAUX = "_";
    
    /** The Constant I_REPORT_TYPE. */
    public static final int I_REPORT_TYPE = 6;
    
    /** The str rsc type. */
    private String strRscType;
    
    private static final String Mode_RENDER_UserList = "rul";
    private static final String Mode_RENDER_DataTable = "rdt";
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
            strRscType = "WBALoginReport";
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
    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(!SWBParamRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState())) {
            processRequest(request, response, paramsRequest);
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
        final String mode = paramsRequest.getMode();
        
        if(Mode_RENDER_UserList.equalsIgnoreCase(mode)) {
            doRenderUserList(request, response, paramsRequest);
        }else if(Mode_RENDER_DataTable.equalsIgnoreCase(mode)) {
            doFillReport(request,response,paramsRequest);
        }
        else if(mode.equalsIgnoreCase("xls")) {
//            doRepExcel(request,response,paramsRequest);
        }else if(mode.equalsIgnoreCase("xml")) {
////            doRepXml(request,response,paramsRequest);
        }
        else {
            super.processRequest(request, response, paramsRequest);
        }
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

        // If there are repositories continue
        if( SWBContext.listUserRepositories().hasNext() )
        {
            String address = paramsRequest.getWebPage().getUrl();
            String repositoryId = request.getParameter("wb_repository")==null? SWBContext.listUserRepositories().next().getId():request.getParameter("wb_repository");
//            int groupDates;
//            try {
//                groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
//            }catch(NumberFormatException e) {
//                groupDates = 0;
//            }

//            GregorianCalendar cal = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            SWBResourceURL url=paramsRequest.getRenderUrl();
            url.setCallMethod(SWBResourceURL.Call_DIRECT);

            out.println("<script type=\"text/javascript\">");
            out.println("  dojo.require(\"dijit.form.DateTextBox\");");
            out.println("  dojo.require(\"dojox.grid.DataGrid\");");//--
            out.println("  dojo.require(\"dojo.data.ItemFileReadStore\");");//--
            out.println("  dojo.require(\"dijit.form.TimeTextBox\");");

            out.println("  dojo.addOnLoad(refresh);");

            out.println("  function refresh() {");
            out.println("    postHtml('"+url.setMode(Mode_RENDER_UserList)+"'+'?wb_repository='+dojo.byId('wb_repository').options[dojo.byId('wb_repository').selectedIndex].value,'slave');");
            out.println("  }");

            out.println("  dojo.addOnLoad(function() {");
            out.println("    var t = new Date();");
            out.println("    var t1 = new dijit.form.TimeTextBox({name:\"t1\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 0, 0),");
            out.println("                 constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}");
            out.println("    }, \"wb_t11\");");

            out.println("    var t2 = new dijit.form.TimeTextBox({name:\"t2\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 23, 59),");
            out.println("                 constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}");
            out.println("    }, \"wb_t12\");");
            out.println("  });");

            out.println("  function fillGrid(grid, uri) {");
            out.println("    grid.store = new dojo.data.ItemFileReadStore({url: uri});");
            out.println("    grid._refresh();");
            out.println("  }");

            out.println("  var layout= null;");
            out.println("  var jStrMaster = null;");
            out.println("  var gridMaster = null;");
            out.println("  var gridResources = null;");

            out.println("  dojo.addOnLoad(function() {");
            out.println("    layout= [");                
            //out.println("      { field:\"uri\", width:\"100px\", name:\"Uri\" },");
            out.println("      { field:\"site\", width:\"100px\", name:\"Sitio\" },");
            out.println("      { field:\"sect\", width:\"100px\", name:\"Página\" },");
            out.println("      { field:\"id\", width:\"100px\", name:\"Id\" },");
            out.println("      { field:\"url\", width:\"100px\", name:\"Url\" },");
            out.println("      { field:\"dev\", width:\"50px\", name:\"Dispositivo\" },");
            out.println("      { field:\"lang\", width:\"50px\", name:\"Idioma\" },");
            out.println("      { field:\"year\", width:\"50px\", name:\"Año\" },");
            out.println("      { field:\"month\", width:\"50px\", name:\"Mes\" },");
            out.println("      { field:\"day\", width:\"50px\", name:\"Día\" },");
            out.println("      { field:\"time\", width:\"50px\", name:\"Hora\" }");
            out.println("    ];");

            out.println("    gridMaster = new dojox.grid.DataGrid({");
            out.println("      id: \"gridMaster\",");
            out.println("      structure: layout,");
            out.println("      autoWidth: true,");
            out.println("      rowSelector: \"10px\",");
            out.println("      rowsPerPage: \"15\"");
            out.println("    }, \"gridMaster\");");
            out.println("    gridMaster.startup();");
            out.println("  });");
            //--

            out.println("  function getParams() {");
            out.println("    var params = '?';");
            out.println("    params += 'wb_repository='+dojo.byId('wb_repository').value;");
            out.println("    params += '&wb_user='+dojo.byId('wb_user').value;");
            out.println("    params += '&fecha11='+dojo.byId('wb_fecha11').value;");
            out.println("    params += '&t11='+dojo.byId('wb_t11').value;");
            out.println("    params += '&fecha12='+dojo.byId('wb_fecha12').value;");
            out.println("    params += '&t12='+dojo.byId('wb_t12').value;");
            out.println("    return params;");
            out.println("  }\n");

            out.println("function doXml(accion, size) { ");
            /*out.println("   if(validate(accion)) {");*/
            out.println("      var params = getParams(accion);");
            out.println("      window.open(\""+url.setMode("xml")+"\"+params,\"graphWindow\",size);    ");
            /*out.println("   }");*/
            out.println("}");

            out.println("  function doExcel(accion, size) { ");
            out.println("    var params = getParams(accion);");
            out.println("    window.open(\""+url.setMode("xls")+"\"+params,\"graphWindow\",size);    ");
            out.println("  }");

            out.println("  function getTypeSelected(){");
            out.println("    var strType = \"0\";");
            out.println("    for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){");
            out.println("      if(window.document.frmrep.wb_rep_type[i].checked==true){");
            out.println("        strType=window.document.frmrep.wb_rep_type[i].value;");
            out.println("      }");
            out.println("    }");
            out.println("    return strType;");
            out.println("  }");

            out.println("  function doApply() {");
            out.println("    var grid = dijit.byId('gridMaster');");
            out.println("    var params = getParams();");
            out.println("    fillGrid(grid, '"+url.setMode(Mode_RENDER_DataTable)+"'+params);");
            out.println("  }");

            out.println("</script>");

            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("Reporte de accesos");
            out.println("</fieldset>");

            out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
            out.println("<fieldset>");
            out.println("<legend>" + paramsRequest.getLocaleString("filter") + "</legend>");
            out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
            out.println("<tr><td width=\"183\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"443\"></td></tr>");

            out.println("<tr>");
            out.println("<td>Repositorio :</td>");
            out.println("<td colspan=\"2\"><select id=\"wb_repository\" name=\"wb_repository\" onchange=\"getHtml('"+url.setMode(Mode_RENDER_UserList)+"'+'?wb_repository='+this.value,'slave');\">");
            Iterator<UserRepository> userRepositories = SWBContext.listUserRepositories();
            while(userRepositories.hasNext()) {
                UserRepository ur = userRepositories.next();
                out.println("<option value=\""+ ur.getId() + "\"");
                    if(ur.getId().equalsIgnoreCase(repositoryId)) {
                        out.println(" selected=\"selected\"");
                    }
                out.println(">" + ur.getSemanticObject().getDisplayName(paramsRequest.getUser().getLanguage()) + "</option>");
            }
            out.println("</select>");
            out.println("</td>");
            out.println("<td>&nbsp;</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>usuario :</td>");
            out.println("<td colspan=\"2\"><div id=\"slave\"></div>");
            out.println("</td>");
            out.println("<td>&nbsp;</td>");
            out.println("</tr>");

            GregorianCalendar now = new GregorianCalendar();
            out.println("<tr>");
            out.println("<td>Intervalo de fechas :</td>");
            out.println("<td align=\"left\" colspan=\"3\">");
            out.println("<label for=\"wb_fecha11\">Del:&nbsp;</label>");
            out.println("<input type=\"text\" name=\"wb_fecha11\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha11\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />");
            out.println("&nbsp;&nbsp;");
            out.println("<label for=\"wb_t11\">Tiempo:&nbsp;</label>");
            out.println("<input type=\"text\" name=\"wb_t11\" id=\"wb_t11\" size=\"6\" style=\"width:40px;\" />");
            out.println("</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            out.println("<td align=\"left\" colspan=\"3\">");
            out.println("<label for=\"wb_fecha12\">&nbsp;&nbsp;Al:&nbsp;</label>");
            out.println("<input type=\"text\" name=\"wb_fecha12\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha12\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />");
            out.println("&nbsp;&nbsp;");
            out.println("<label for=\"wb_t12\">Tiempo:&nbsp;</label>");
            out.println("<input name=\"wb_t12\" id=\"wb_t12\" />");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table></fieldset>");

            out.println("<fieldset>");
            out.println("<table border=\"0\" width=\"95%\">");
            out.println("<tr>");
            out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
            out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;");
            out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">Hola de cálculo</button>&nbsp;");
//                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doPdf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">PDF</button>&nbsp;");
//                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doRtf('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">RTF</button>&nbsp;");
//                    out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doGraph('"+ rtype +"','width=600, height=550, scrollbars, resizable')\">"+paramsRequest.getLocaleString("graph")+"</button>&nbsp;");
//                    out.println("   <button dojoType=\"dijit.form.Button\" onclick=\"doHistogram('"+rtype+"', 'width=600, height=600, scrollbars, resizable, alwaysRaised, menubar')\">"+paramsRequest.getLocaleString("histogram")+"</button>&nbsp;");
            out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">Aplicar</button>");
            out.println(" </td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");

            out.println("<div id=\"ctnergrid\" style=\"height:350px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
            out.println("  <div id=\"gridMaster\"></div>");
            out.println("</div>");
            out.println("</div>");

            out.println("</div>");
        }
        else
        {   // There are not sites and displays a message
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<legend>" + paramsRequest.getLocaleString("login_report") + "</legend>");
            out.println("<form method=\"Post\" action=\"" + paramsRequest.getWebPage().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
            out.println("<table border=0 width=\"100%\">");
            out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
            out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
            out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            out.println("<td colspan=\"2\" align=\"center\">" + paramsRequest.getLocaleString("no_repositories_found") + "</td>");
            out.println("<td>&nbsp;</td>");
            out.println("</tr>");
            out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
            out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
            out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
            out.println("</table></form>");
            out.println("</fieldset></div>");
        }
        out.flush();
        out.close();
    }
    
    /**
     * Do render lang.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderUserList(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();

        String repositoryId = request.getParameter("wb_repository");
        UserRepository userRepository = SWBContext.getUserRepository(repositoryId);

        String userId = request.getParameter("wb_user")==null? "":request.getParameter("wb_user");

        out.println("<select id=\"wb_user\" name=\"wb_user\" size=\"8\">");
        Iterator<User> users = userRepository.listUsers();
        while(users.hasNext()) {
            User user = users.next();
            out.println("<option value=\""+user.getLogin()+"\" ");
            if(userId.equalsIgnoreCase(user.getLogin())) {
                out.print(" selected=\"selected\" ");
            }
            out.print(">"+user.getFullName()+" - "+user.getLogin()+"</option>");
        }
        out.println("</select>");
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
    private void doFillReport(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        
        final String repId = request.getParameter("wb_repository");
        UserRepository ur = SWBContext.getUserRepository(repId);
        if(ur==null) {
            log.error("Repositorio de usuarios incorrecto");
            return;
        }
        final String userId = request.getParameter("wb_user");
        User visitor = ur.getUserByLogin(userId);
        if(visitor==null) {
            log.error("Usuario con login "+userId+" no existe en  el repositorio con identificador "+repId);
            return;
        }
        
        String lang = visitor.getLanguage();
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            //jobj.put("identifier", "uri");
            jobj.put("label", "sect");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
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
        
        final String fullHostname = request.getScheme() + "://" + request.getServerName() + (request.getServerPort() != 80? ":" + request.getServerPort():"");        
        WebSite ws;
        WebPage wp;
        Iterator<String[]> lines = getReportResults(repId, userId, first, last);
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
                //obj.put("uri", webPage.getURI());
                obj.put("site", ws.getDisplayTitle(lang));
                obj.put("sect", wp.getDisplayName(lang));
                obj.put("id", t[5]);
                obj.put("url", fullHostname+wp.getUrl());
                obj.put("dev",t[9]);
                obj.put("lang", t[10]);
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
    private Iterator<String[]> getReportResults(final String repId, final String userId, final Date s, final Date e) {
        GregorianCalendar datefile = null;

        String line = null;
        String s_aux = null;
        String filename = null;

        String yearinfile = null;
        String monthinfile = null;
        String dayinfile = null;
        String hourinfile = null;
        String mininfile = null;
        
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
                        //6-s_aux receives repository id
                        if(repId!=null && !t[6].equalsIgnoreCase(repId)) {
                            continue;
                        }
                        //7-s_aux receives user id
                        if(userId!=null && !t[7].equalsIgnoreCase(userId)) {
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

