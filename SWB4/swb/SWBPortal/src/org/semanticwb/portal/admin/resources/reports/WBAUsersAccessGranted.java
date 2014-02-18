/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.SelectTree;

/**
 *
 * @author carlos.ramos
 */
public class WBAUsersAccessGranted extends GenericResource {
    /** The log. */
    private static final Logger log = SWBUtils.getLogger(WBAUsersAccessGranted.class);

    /** The Constant I_REPORT_TYPE. */
    public static final int I_REPORT_TYPE = 3;
    
    /** The str rsc type. */
    private String strRscType;
    
    private static final String Mode_RENDER_DataTable = "rdt";
    private static final String Mode_RENDER_Tree = "rst";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#init()
     */
    @Override
    public void init(){
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBAUsersAccessGranted";
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
                out.println("      { field:\"ln\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblLastName") + "\" },");
                out.println("      { field:\"sln\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblSecondLastName") + "\" },");
                out.println("      { field:\"n\", width:\"100px\", name:\"" + paramsRequest.getLocaleString("lblName") + "\" },");
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
//                out.println("    params += '&fecha11='+dojo.byId('wb_fecha11').value;");
//                out.println("    params += '&t11='+dojo.byId('wb_t11').value;");
//                out.println("    params += '&fecha12='+dojo.byId('wb_fecha12').value;");
//                out.println("    params += '&t12='+dojo.byId('wb_t12').value;");
//                out.println("    params += '&wb_show_chld=' + dojo.byId('wb_show_chld').checked;");
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
                out.println("      var params = getParams();");
                out.println("      window.open(\"" + url.setMode("xls") + "\"+params, 'trck', size);");
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
        } catch (SWBResourceException e) {
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
            log.error("Modelo incorrecto");
            return;
        }
        final String wpId = request.getParameter("section");
        WebPage wp = ws.getWebPage(wpId);
        if(wp==null) {
            log.error("Sección con id "+wpId+" no existe en el modelo "+wsId);
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
        
        User user;
        JSONObject obj;
        UserRepository usrep = ws.getUserRepository();
        final String usrepId = usrep.getId();
        Iterator<User> users = usrep.listUsers();
        while(users.hasNext()) {
            user = users.next();
            if(user.haveAccess(wp)) {
                obj = new JSONObject();
                try {
                    obj.put("rep", usrepId);
                    obj.put("login", user.getLogin());
                    obj.put("ln", user.getLastName()==null?"-":user.getLastName());
                    obj.put("sln", user.getSecondLastName()==null?"-":user.getSecondLastName());
                    obj.put("n", user.getName()==null?"-":user.getName());
                }catch(JSONException jsoe) {
                    continue;
                }                
                jarr.put(obj);
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
        response.setHeader("Content-Disposition", "inline; filename=\"trck.xls\"");
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
        
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblRepository"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblUserName"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblLastName"));
        out.println("</th>");
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblSecondLastName"));
        out.println("</th>");        
        out.println("<th>");
        out.println(paramsRequest.getLocaleString("lblName"));
        out.println("</th>");
        out.println("</tr>");
        out.println("</thead>");

        User user;
        UserRepository usrep = ws.getUserRepository();
        final String usrepId = usrep.getId();
        Iterator<User> users = usrep.listUsers();
        if(users.hasNext()) {
            out.println("<tbody>");
            while(users.hasNext()) {
                user = users.next();
                if(user.haveAccess(wp)) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(usrepId);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(user.getLogin());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(user.getLastName()==null?"-":user.getLastName());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(user.getSecondLastName()==null?"-":user.getSecondLastName());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(user.getName()==null?"-":user.getName());
                    out.println("</tr>");
                }
            }
            out.println("</tbody>");
        }
        out.println("</table>");            
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
            log.error("Sección con id "+wpId+" no existe en el modelo "+wsId);
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
        
        User user;
        JSONObject obj;
        UserRepository usrep = ws.getUserRepository();
        final String usrepId = usrep.getId();
        Iterator<User> users = usrep.listUsers();
        while(users.hasNext()) {
            user = users.next();
            if(user.haveAccess(wp)) {
                obj = new JSONObject();
                try {
                    obj.put("rep", usrepId);
                    obj.put("login", user.getLogin());
                    obj.put("ln", user.getLastName()==null?"-":user.getLastName());
                    obj.put("sln", user.getSecondLastName()==null?"-":user.getSecondLastName());
                    obj.put("n", user.getName()==null?"-":user.getName());
                }catch(JSONException jsoe) {
                    continue;
                }                
                jarr.put(obj);
            }
        }
        response.getOutputStream().println(jobj.toString());
    }
}
