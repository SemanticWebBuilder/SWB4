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
**/


package org.semanticwb.portal.admin.resources.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.util.SelectTree;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Versionable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class WBAContentsReport.
 */
public class WBAContentsReport extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBASectionReport.class);

    /** The str rsc type. */
    private String strRscType;

    /** Format for Last Update field **/
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    /** Host & port **/
    private String hostAndPort;

    /**
     * Instantiates a new wBA contents report.
     */
    public WBAContentsReport() {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#init()
     */
    @Override
    public void init(){
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBAContentsReport";
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
        if(paramsRequest.getMode().equalsIgnoreCase("rendertree")) {
            doRenderSectionTree(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("fillgridmtr")) {
            doFillReport(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("xls")) {
            doRepExcel(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("xml")) {
            doRepXml(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
        String port= (request.getServerPort() != 80)? ":" + request.getServerPort():"";
        hostAndPort = request.getScheme() + "://" + request.getServerName() + port;
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
    private void doRenderSectionTree(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();

        String websiteId = request.getParameter("site");

        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        url.setMode("rendertree");

        String section = request.getParameter("reptp");
        if(section != null) {
            out.println("<input type=\"hidden\" name=\"section\" id=\"section\" value=\""+section+"\" />");
        }

        HashMap params = new HashMap();
        Enumeration<String> names = request.getParameterNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        SelectTree tree = new SelectTree(paramsRequest.getUser().getLanguage());
        out.println(tree.renderXHTML(websiteId, params, url.toString()));
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
        String lang = paramsRequest.getUser().getLanguage();
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            //jobj.put("identifier", "uri");
            jobj.put("label", "sect");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
        }

        String websiteId = request.getParameter("site");
        WebSite webSite = SWBContext.getWebSite(websiteId);
        String webPageId = request.getParameter("section");
        WebPage webPage = webSite.getWebPage(webPageId);

        Iterator<Resource> portlets = webPage.listResources();
        while(portlets.hasNext()) {
            Resource portlet = portlets.next();
            if(portlet.getResourceType().getResourceMode()==1) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("id", portlet.getId());
                    obj.put("sect", webPage.getDisplayName(lang));
                    obj.put("tipo", portlet.getResourceType().getDisplayTitle(lang));
                    obj.put("contenido", portlet.getDisplayTitle(lang));
                    obj.put("prior", portlet.getPriority());
                    obj.put("active", portlet.isActive()?"Si":"No");
                    obj.put("loc", portlet.getWorkPath());
                    obj.put("url", hostAndPort + webPage.getUrl());
                    obj.put("lastUpdate", df.format(webPage.getUpdated()));
                    try{
                        GenericObject gObj = portlet.getResourceData().createGenericInstance();
                        if( gObj instanceof Versionable){
                            obj.put("lastVersion", ((Versionable)gObj).getLastVersion().getVersionNumber());
                            obj.put("actVersion", ((Versionable)gObj).getActualVersion().getVersionNumber());
                        }
                    }catch(Exception e){
                        obj.put("lastVersion", "-");
                        obj.put("actVersion", "-");
                    }
                    //obj.put("uri", portlet.getURI());
                    //obj.put("breaken", "No");
                    jarr.put(obj);
                }catch (JSONException jsone) {
                }

            }
        }
        if(request.getParameter("sons")!=null && request.getParameter("sons").equalsIgnoreCase("1")) {
            //doDataStore(webPage, jarr, lang);
            doDataStore(webPage.listChilds(), jarr, lang);
        }
        response.getOutputStream().println(jobj.toString());
    }

    /**
     * Do data store.
     *
     * @param node the node
     * @param jarr the jarr
     * @param lang the lang
     */
    //private void doDataStore(WebPage node, JSONArray jarr, final String lang) {
    private void doDataStore(Iterator<WebPage> childs, JSONArray jarr, final String lang) {
        //Iterator<WebPage> childs = node.listVisibleChilds(lang);
        while(childs.hasNext()) {
            WebPage webPage = childs.next();
            Iterator<Resource> portlets = webPage.listResources();
            while(portlets.hasNext()) {
                Resource portlet = portlets.next();
                if(portlet.getResourceType().getResourceMode()==1) {
                    JSONObject obj = new JSONObject();
                    try {                        
                        obj.put("id", portlet.getId());
                        obj.put("sect", webPage.getDisplayName(lang));
                        obj.put("tipo", portlet.getResourceType().getDisplayTitle(lang));
                        obj.put("contenido", portlet.getDisplayTitle(lang));
                        obj.put("prior", portlet.getPriority());
                        obj.put("active", portlet.isActive()?"Si":"No");
                        obj.put("loc", portlet.getWorkPath());
                        obj.put("url", hostAndPort + webPage.getUrl());
                        obj.put("lastUpdate", df.format(webPage.getUpdated()));
                        try{
                            GenericObject gObj = portlet.getResourceData().createGenericInstance();
                            if( gObj instanceof Versionable){
                                obj.put("lastVersion", ((Versionable)gObj).getLastVersion().getVersionNumber());
                                obj.put("actVersion", ((Versionable)gObj).getActualVersion().getVersionNumber());
                            }
                        }catch(Exception e){
                            obj.put("lastVersion", "-");
                            obj.put("actVersion", "-");
                        }
                        //obj.put("uri", portlet.getURI());
                        //obj.put("breaken", "No");
                        jarr. put(obj);
                    }catch (JSONException jsone) {
                    }
                }
            }
            if(webPage.listChilds().hasNext()) {
//                doDataStore(webPage, jarr, lang);
                doDataStore(webPage.listChilds(), jarr, lang);
            }
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
        Resource base = getResourceBase();
        HashMap hm_sites = new HashMap();
        String rtype = null;
        String language = paramsRequest.getUser().getLanguage();

        try {
            // Evaluates if there are sites
            Iterator<WebSite> webSites = SWBContext.listWebSites();
            while(webSites.hasNext()) {
                WebSite site = webSites.next();
                // Evaluates if TopicMap is not Global
                if(!site.getId().equals(SWBContext.getGlobalWebSite().getId())) {
                    hm_sites.put(site.getId(), site.getDisplayTitle(language));
                }
            }

            // If there are sites continue
            if(hm_sites.size() > 0) {
                String address = paramsRequest.getWebPage().getUrl();
                String websiteId = request.getParameter("wb_site")==null ? (String)hm_sites.keySet().iterator().next():request.getParameter("wb_site");

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

                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setCallMethod(SWBResourceURL.Call_DIRECT);

                out.println("<script type=\"text/javascript\">");

                out.println("dojo.require(\"dojox.grid.DataGrid\");");
                out.println("dojo.require(\"dojo.data.ItemFileReadStore\");");

                out.println("dojo.addOnLoad(function(){renderTreeSectionsSite('"+url.toString()+"', 'rendertree','"+websiteId+"','slave')});");

                out.println("function renderTreeSectionsSite(url, mode, site, canvasId) {");
                out.println("   postHtml(url+'/_mod/'+mode+'?site='+site, canvasId);");
                out.println("}");

                out.println("function fillGrid(grid, uri, mode, params) {");
                out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri+'/_mod/'+mode+'?'+params});");
                //out.println("   grid.store = new dojo.data.ItemFileReadStore({url: \"/swb/ice_cream.json\" });");
                out.println("   grid._refresh();");
                out.println("}");

                out.println("var layout= null;");
                out.println("var jStrMaster = null;");
                out.println("var gridMaster = null;");
                out.println("var gridResources = null;");

                out.println("dojo.addOnLoad(function() {");
                out.println("   layout= [");
                out.println("      { field:\"sect\", width:\"100px\", name:\"Sección\" },");
                out.println("      { field:\"id\", width:\"50px\", name:\"Id\" },");
                out.println("      { field:\"tipo\", width:\"100px\", name:\"Tipo contenido\" },");
                out.println("      { field:\"contenido\", width:\"100px\", name:\"Contenido\" },");
                out.println("      { field:\"lastVersion\", width:\"60px\", name:\"Versiones\" },");
                out.println("      { field:\"actVersion\", width:\"60px\", name:\"Versión actual\" },");
                out.println("      { field:\"prior\", width:\"50px\", name:\"Prioridad\" },");
                out.println("      { field:\"active\", width:\"50px\", name:\"Activo\" },");
                out.println("      { field:\"loc\", width:\"100px\", name:\"Localidad\" },");
                out.println("      { field:\"url\", width:\"200px\", name:\"URL\" },");                
                out.println("      { field:\"lastUpdate\", width: \"70px\", name:\"Última modificación\" }");
                out.println("   ];");

                out.println("   gridMaster = new dojox.grid.DataGrid({");
                out.println("      id: \"gridMaster\",");
                out.println("      structure: layout,");
                out.println("      autoWidth: true,");
                out.println("      rowSelector: \"10px\",");
                out.println("      rowsPerPage: \"15\"");
                out.println("   }, \"gridMaster\");");
                out.println("   gridMaster.startup();");
                //out.println("    dojo.connect(dijit.byId('gridMaster'), 'onRowDblClick', getResources);");
                out.println("});");

                out.println("function doXml(size) { ");
                out.println("   var params = '?site='+dojo.byId('wb_site').value;");
                out.println("   if(dojo.byId('section')) {");
                out.println("      params += '&section=' + dojo.byId('section').value;");
                out.println("      if(dojo.byId('wb_show_son').checked) {");
                out.println("         params += '&sons=' + dojo.byId('wb_show_son').value;");
                out.println("      }");
                out.println("      window.open(\"" + url.setMode("xml") + "\"+params,\"graphWindow\",size);");
                out.println("   }else {");
                out.println("      alert('Para poder mostrarle el resumen de contenido, primero debe seleccionar una sección');");
                out.println("   }");
                out.println("}");

                out.println("function doExcel(size) { ");
                out.println("   var params = '?site='+dojo.byId('wb_site').value;");
                out.println("   if(dojo.byId('section')) {");
                out.println("      params += '&section=' + dojo.byId('section').value;");
                out.println("      if(dojo.byId('wb_show_son').checked) {");
                out.println("         params += '&sons=' + dojo.byId('wb_show_son').value;");
                out.println("      }");
                out.println("      window.open(\"" + url.setMode("xls") + "\"+params,\"graphWindow\",size);");
                out.println("   }else {");
                out.println("      alert('Para poder mostrarle el resumen de contenido, primero debe seleccionar una sección');");
                out.println("   }");
                out.println("}");

                out.println("function doApply() {");
                out.println("   var grid = dijit.byId('gridMaster');");
                out.println("   var params = 'site='+dojo.byId('wb_site').value;");

                out.println("   if(dojo.byId('section')) {");
                //out.println("      dojo.byId('ctnergrid').style.display = 'block';");
                out.println("      params += '&section=' + dojo.byId('section').value;");
                out.println("      if(dojo.byId('wb_show_son').checked) {");
                out.println("         params += '&sons=' + dojo.byId('wb_show_son').value;");
                out.println("      }");
                out.println("      fillGrid(grid, '"+url.setMode("view")+"', 'fillgridmtr', params);");
                out.println("   }else {");
                out.println("      alert('Para poder mostrarle el resumen de contenido, primero debe seleccionar una sección');");
                out.println("   }");
                out.println("}");

                out.println("</script>");
                // javascript

                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println(paramsRequest.getLocaleString("contents_report"));
                out.println("</fieldset>");

                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("filter") + "</legend>");
                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
                out.println("<tr><td width=\"100\"></td><td width=\"200\"></td><td width=\"224\"></td><td width=\"264\"></td></tr>");

                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("site") + ":</td>");
                out.println("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" onchange=\"renderTreeSectionsSite('"+url.toString()+"', 'rendertree', this.value, 'slave');\">");
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
                out.println("<td>" + paramsRequest.getLocaleString("section") + ":</td>");
                out.println("<td colspan=\"3\"><div id=\"slave\"></div></td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td colspan=\"4\"><input type=\"checkbox\" id=\"wb_show_son\" name=\"wb_show_son\" value=\"1\" />");
                out.println("<label for=\"wb_show_son\">"+paramsRequest.getLocaleString("descendant")+"</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr><td colspan=\"4\"><br /></td></tr>");
                out.println("</table></fieldset>");

                out.println("<fieldset>");
                out.println("<table border=\"0\" width=\"95%\">");
                out.println("<tr>");
                out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;");
                out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">"+paramsRequest.getLocaleString("spread_sheet")+"</button>&nbsp;");
                out.println("   <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">"+paramsRequest.getLocaleString("apply")+"</button>");
//                out.println("   <input type=\"hidden\" name=\"tp\" id=\"tp\" />");
                out.println(" </td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</form>");

//                out.println("<fieldset>");
//                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
//                out.println("<tr>");
//                out.println("<td colspan=\"4\">");
//                out.println("<div id=\"ctnergrid\" style=\"height:400px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
//                out.println("  <div id=\"gridMaster\"></div>");
//                out.println("</div>");
                out.println("<div id=\"ctnergrid\" style=\"height:350px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
                out.println("  <div id=\"gridMaster\" jsid=\"gridMaster\"></div>");
                out.println("</div>");
//                out.println("</td>");
//                out.println("</tr>");
//                out.println("</table>");
//                out.println("</fieldset>");
                out.println("</div>");
            }
        }catch (Exception e) {
            log.error("Error on method doView() resource " + strRscType + " with id " + base.getId(), e);
        }
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
        response.setHeader("Content-Disposition", "inline; filename=\"ic.xls\"");
        PrintWriter out = response.getWriter();
        StringBuilder html = new StringBuilder();
        String language = paramsRequest.getUser().getLanguage();


        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println("<title>"+paramsRequest.getLocaleString("contents_report")+"</title>");
        out.println("</head>");
        out.println("<body>");

        html.append("<table border=\"0\" width=\"95%\">");
        html.append("<tr>");
        html.append("  <th>Sección</th>");
        html.append("  <th>Id</th>");
        html.append("  <th>Tipo contenido</th>");
        html.append("  <th>Contenido</th>");
        html.append("  <th>Versiones</th>");
        html.append("  <th>Versión actual</th>");
        html.append("  <th>Prioridad</th>");
        html.append("  <th>Activo</th>");
        html.append("  <th>Localidad</th>");
        html.append("  <th>URL</th>");
        html.append("  <th>Última modificación</th>");
        html.append("</tr>");

        String websiteId = request.getParameter("site");
        WebSite webSite = SWBContext.getWebSite(websiteId);
        String webPageId = request.getParameter("section");
        WebPage webPage = webSite.getWebPage(webPageId);
        Iterator<Resource> portlets = webPage.listResources();
        while(portlets.hasNext()) {
            Resource portlet = portlets.next();
            if(portlet.getResourceType().getResourceMode()==1) {
                html.append("<tr>");
                html.append("  <td>").append(webPage.getDisplayTitle(language)).append("</td>");
                html.append("  <td>").append(portlet.getId()).append("</td>");
                html.append("  <td>").append(portlet.getResourceType().getDisplayTitle(language)).append("</td>");
                html.append("  <td>").append(portlet.getDisplayTitle(language)).append("</td>");
                try{
                    GenericObject gObj = portlet.getResourceData().createGenericInstance();
                    if( gObj instanceof Versionable){
                        html.append("  <td>").append(((Versionable)gObj).getLastVersion().getVersionNumber()).append("</td>");
                        html.append("  <td>").append(((Versionable)gObj).getActualVersion().getVersionNumber()).append("</td>");
                    }
                }catch(Exception e){
                    html.append("  <td>-</td>");
                    html.append("  <td>-</td>");
                }
                html.append("  <td>").append(portlet.getPriority()).append("</td>");
                html.append("  <td>").append(portlet.isActive()?"Si":"No").append("</td>");
                html.append("  <td>").append(portlet.getWorkPath()).append("</td>");
                html.append("  <td>").append(hostAndPort).append(webPage.getUrl()).append("</td>");
                html.append("  <td>").append(df.format(webPage.getUpdated())).append("</td>");
                //html.append("  <td>"+portlet.getURI()+"</td>");
                //html.append("  <td>No</td>");
                html.append("</tr>");

            }
        }

        if(request.getParameter("sons")!=null && request.getParameter("sons").equalsIgnoreCase("1")) {
            html.append(getContentInHtml(webPage.listChilds(), language));
        }

        html.append("</table>");
        out.print(html.toString());
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    /**
     * Gets the content in html.
     *
     * @param childs the childs
     * @param language the language
     * @return the content in html
     */
    private String getContentInHtml(Iterator<WebPage> childs, String language) {
        StringBuilder html = new StringBuilder();
        while(childs.hasNext()) {
            WebPage webPage = childs.next();
            Iterator<Resource> portlets = webPage.listResources();
            while(portlets.hasNext()) {
                Resource portlet = portlets.next();
                if(portlet.getResourceType().getResourceMode()==1) {
                    html.append("<tr>");
                    html.append("  <td>").append(webPage.getDisplayTitle(language)).append("</td>");
                    html.append("  <td>").append(portlet.getId()).append("</td>");
                    html.append("  <td>").append(portlet.getResourceType().getDisplayTitle(language)).append("</td>");
                    html.append("  <td>").append(portlet.getDisplayTitle(language)).append("</td>");
                    try{
                        GenericObject gObj = portlet.getResourceData().createGenericInstance();
                        if( gObj instanceof Versionable){
                            html.append("  <td>").append(((Versionable)gObj).getLastVersion().getVersionNumber()).append("</td>");
                            html.append("  <td>").append(((Versionable)gObj).getActualVersion().getVersionNumber()).append("</td>");
                        }
                    }catch(Exception e){
                        html.append("  <td>-</td>");
                        html.append("  <td>-</td>");
                    }
                    html.append("  <td>").append(portlet.getPriority()).append("</td>");
                    html.append("  <td>").append(portlet.isActive()?"Si":"No").append("</td>");
                    html.append("  <td>").append(portlet.getWorkPath()).append("</td>");
                    html.append("  <td>").append(hostAndPort).append(webPage.getUrl()).append("</td>");
                    html.append("  <td>").append(df.format(webPage.getUpdated())).append("</td>");
                    //html.append("  <td>"+portlet.getURI()+"</td>");
                    //html.append("  <td>No</td>");
                    html.append("</tr>");
                }
            }
            if(webPage.listChilds().hasNext()) {
                html.append(getContentInHtml(webPage.listChilds(), language));
            }
        }
        return html.toString();
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
    public void doRepXml(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("text/xml;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        Document dom = SWBUtils.XML.getNewDocument();
        String language = paramsRequest.getUser().getLanguage();

        Element report = dom.createElement("ContentReport");
        dom.appendChild(report);

        String websiteId = request.getParameter("site");
        WebSite webSite = SWBContext.getWebSite(websiteId);
        String webPageId = request.getParameter("section");
        WebPage webPage = webSite.getWebPage(webPageId);
        Iterator<Resource> portlets = webPage.listResources();
        while(portlets.hasNext()) {
            Resource portlet = portlets.next();
            if(portlet.getResourceType().getResourceMode()==1) {
                Element resource = dom.createElement("resource");
                resource.appendChild(dom.createTextNode(""));
                report.appendChild(resource);

                Element section = dom.createElement("section");
                section.appendChild(dom.createTextNode(webPage.getDisplayTitle(language)));
                resource.appendChild(section);
                Element id = dom.createElement("id");
                id.appendChild(dom.createTextNode(portlet.getId()));
                resource.appendChild(id);
                Element tipo = dom.createElement("type");
                tipo.appendChild(dom.createTextNode(portlet.getResourceType().getDisplayTitle(language)));
                resource.appendChild(tipo);
                Element contenido = dom.createElement("content");
                contenido.appendChild(dom.createTextNode(portlet.getDisplayTitle(language)));
                resource.appendChild(contenido);
                Element noVersiones = dom.createElement("lastVersion");
                Element versionActual = dom.createElement("actualVersion");
                try{
                    GenericObject gObj = portlet.getResourceData().createGenericInstance();
                    if( gObj instanceof Versionable){
                        noVersiones.appendChild(dom.createTextNode("" + ((Versionable)gObj).getLastVersion().getVersionNumber()));
                        resource.appendChild(noVersiones);
                        versionActual.appendChild(dom.createTextNode("" + ((Versionable)gObj).getActualVersion().getVersionNumber()));
                        resource.appendChild(versionActual);
                    }
                }catch(Exception e){
                    noVersiones.appendChild(dom.createTextNode("-"));
                    resource.appendChild(noVersiones);
                    versionActual.appendChild(dom.createTextNode("-"));
                    resource.appendChild(versionActual);
                }
                Element prior = dom.createElement("priority");
                prior.appendChild(dom.createTextNode(Integer.toString(portlet.getPriority())));
                resource.appendChild(prior);
                Element active = dom.createElement("active");
                active.appendChild(dom.createTextNode(portlet.isActive()?"Si":"No"));
                resource.appendChild(active);
                Element loc = dom.createElement("location");
                loc.appendChild(dom.createTextNode(portlet.getWorkPath()));
                resource.appendChild(loc);
                Element url = dom.createElement("url");
                url.appendChild(dom.createTextNode(hostAndPort + webPage.getUrl()));
                resource.appendChild(url);
                //uri.appendChild(dom.createTextNode(portlet.getURI()));
                //resource.appendChild(uri);
                Element lastUpdate = dom.createElement("lastUpdate");
                lastUpdate.appendChild(dom.createTextNode(df.format(webPage.getUpdated())));
                resource.appendChild(lastUpdate);
                //broke.appendChild(dom.createTextNode("No"));
                //resource.appendChild(broke);
            }
        }

        if(request.getParameter("sons")!=null && request.getParameter("sons").equalsIgnoreCase("1")) {
            getContentInXml(dom, webPage.listChilds(), language);
        }

        out.print(SWBUtils.XML.domToXml(dom));
        out.flush();
        out.close();
    }

    /**
     * Gets the content in xml.
     *
     * @param dom the dom
     * @param childs the childs
     * @param language the language
     * @return the content in xml
     */
    private void getContentInXml(Document dom, Iterator<WebPage> childs, String language) {
        Element report = dom.getDocumentElement();
        while(childs.hasNext()) {
            WebPage webPage = childs.next();
            Iterator<Resource> portlets = webPage.listResources();
            while(portlets.hasNext()) {
                Resource portlet = portlets.next();
                if(portlet.getResourceType().getResourceMode()==1) {
                    Element resource = dom.createElement("resource");
                    resource.appendChild(dom.createTextNode(""));
                    report.appendChild(resource);

                    Element section = dom.createElement("section");
                    section.appendChild(dom.createTextNode(webPage.getDisplayTitle(language)));
                    resource.appendChild(section);
                    Element id = dom.createElement("id");
                    id.appendChild(dom.createTextNode(portlet.getId()));
                    resource.appendChild(id);
                    Element tipo = dom.createElement("type");
                    tipo.appendChild(dom.createTextNode(portlet.getResourceType().getDisplayTitle(language)));
                    resource.appendChild(tipo);
                    Element contenido = dom.createElement("content");
                    contenido.appendChild(dom.createTextNode(portlet.getDisplayTitle(language)));
                    resource.appendChild(contenido);
                    Element noVersiones = dom.createElement("lastVersion");
                    Element versionActual = dom.createElement("actualVersion");
                    try{
                        GenericObject gObj = portlet.getResourceData().createGenericInstance();
                        if( gObj instanceof Versionable){
                            noVersiones.appendChild(dom.createTextNode("" + ((Versionable)gObj).getLastVersion().getVersionNumber()));
                            resource.appendChild(noVersiones);
                            versionActual.appendChild(dom.createTextNode("" + ((Versionable)gObj).getActualVersion().getVersionNumber()));
                            resource.appendChild(versionActual);
                        }
                    }catch(Exception e){
                        noVersiones.appendChild(dom.createTextNode("-"));
                        resource.appendChild(noVersiones);
                        versionActual.appendChild(dom.createTextNode("-"));
                        resource.appendChild(versionActual);
                    }
                    Element prior = dom.createElement("priority");
                    prior.appendChild(dom.createTextNode(Integer.toString(portlet.getPriority())));
                    resource.appendChild(prior);
                    Element active = dom.createElement("active");
                    active.appendChild(dom.createTextNode(portlet.isActive()?"Si":"No"));
                    resource.appendChild(active);
                    Element loc = dom.createElement("location");
                    loc.appendChild(dom.createTextNode(portlet.getWorkPath()));
                    resource.appendChild(loc);
                    Element url = dom.createElement("url");
                    url.appendChild(dom.createTextNode(hostAndPort + webPage.getUrl()));
                    resource.appendChild(url);
                    //uri.appendChild(dom.createTextNode(portlet.getURI()));
                    //resource.appendChild(uri);
                    Element lastUpdate = dom.createElement("lastUpdate");
                    lastUpdate.appendChild(dom.createTextNode(df.format(webPage.getUpdated())));
                    resource.appendChild(lastUpdate);
                    //broke.appendChild(dom.createTextNode("No"));
                    //resource.appendChild(broke);
                }
            }
            if(webPage.listChilds().hasNext()) {
                getContentInXml(dom, webPage.listChilds(), language);
            }
        }
    }


    /**
     * Checks for broken links.
     *
     * @param p_datapage the p_datapage
     * @param p_page the p_page
     * @param p_content the p_content
     * @param p_site the p_site
     * @return true, if successful
     */
    public boolean hasBrokenLinks(ArrayList p_datapage, int p_page, String p_content, String p_site) {
        boolean b_return = false;
        /*ContentMonitor content_mon = null;
        int i_size = 0;
        int i_inipage = 0;
        int i_finpage = 0;
        long l_id = 0;

        if(p_content.equals("WBUrlContent") || p_content.equals("FrameContent")){
            content_mon = new ContentMonitor();
            i_size = p_datapage.size();
            i_inipage =  I_PAGE_SIZE * p_page;
            i_finpage = i_size;
            if((i_inipage + I_PAGE_SIZE) < i_finpage){
                i_finpage = i_inipage + I_PAGE_SIZE;
            }
            for(int j=i_inipage;j<i_finpage;j++){
                String[] arr_data = (String[])p_datapage.get(j);
                l_id = Long.parseLong(arr_data[0]);
                if(content_mon.isRemoteContentLinkBreak(p_site,l_id)){
                    b_return = true;
                    break;
                }
            }
        }*/
        return b_return;
    }

    /**
     * The Class Bool.
     */
    private class Bool {

        /** The first. */
        private boolean first;

        /**
         * Instantiates a new bool.
         */
        public Bool() {
            first = true;
        }

        /**
         * Checks if is first.
         *
         * @return true, if is first
         */
        public boolean isFirst() {
            return first;
        }

        /**
         * Sets the first.
         */
        public void setFirst() {
            first = false;
        }

    }
}