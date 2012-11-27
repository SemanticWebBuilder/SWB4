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

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Device;
import org.semanticwb.model.Language;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.SWBPortal;

// TODO: Auto-generated Javadoc
/**
 * The Class WBAAccessLogHitsReport.
 */
public class WBAAccessLogHitsReport extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBAAccessLogHitsReport.class);

    /** The str rsc type. */
    public String strRscType;
    
    /** The hm_detail. */
    private HashMap hm_detail = null;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#init()
     */
    @Override
    public void init(){
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBAAccessLogHitsReport";
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#render(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState())) {
            processRequest(request, response, paramsRequest);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String mode = paramsRequest.getMode();
        if(mode.equalsIgnoreCase("fillGridAgrd")) {
            doFillReportAgregate(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("fillGridDtd")) {
            doFillReportDetalled(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("fillDevSel")) {
            doRenderDeviceSelect(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("fillLangSel")) {
            doRenderLanguageSelect(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("fillUTSel")) {
            doRenderUserTypeSelect(request, response, paramsRequest);
        }else if(paramsRequest.getMode().equals("report_detail")) {
            doDetail(request,response,paramsRequest);
        }else if(mode.equalsIgnoreCase("xls")) {
            doRepExcel(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("xml")) {
            doRepXml(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("histogram")) {
            doGraph(request, response, paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    /**
     * Do render device select.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderDeviceSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");

        StringBuilder ret = new StringBuilder();
        String space="";
        String language = paramsRequest.getUser().getLanguage();

        WebSite webSite = SWBContext.getWebSite(request.getParameter("site"));

        ArrayList<Device> devs=new ArrayList<Device>();
        Iterator<Device> itDevices = webSite.listDevices();
        while (itDevices.hasNext()) {
            devs.add(itDevices.next());
        }
        devs.trimToSize();

        ret.append("<select name=\"wb_devid\" id=\"wb_devid\" size=\"6\" >");
        while(!devs.isEmpty()) {
            Device device = devs.get(0);
            ret.append("<option value=\""+device.getId()+"\"");
            ret.append(">"+space+device.getDisplayTitle(language)+"</option>");
            devs.remove(0);
            if(device.listChilds().hasNext()) {
                renderDeviceSelect(devs, device, language, ret, space+"&nbsp;&nbsp;&nbsp;");
            }
        }
        ret.append("</select>");
        request.getSession(true).removeAttribute("devs");

        PrintWriter out = response.getWriter();
        out.print(ret.toString());
        out.flush();
        out.close();
    }

    /**
     * Render device select.
     * 
     * @param origList the orig list
     * @param node the node
     * @param language the language
     * @param ret the ret
     * @param space the space
     */
    private void renderDeviceSelect(ArrayList origList, Device node, String language, StringBuilder ret, String space) {
        ArrayList<Device> devs=new ArrayList<Device>();
        Iterator<Device> itDevices = node.listChilds();
        while(itDevices.hasNext()) {
            devs.add(itDevices.next());
        }
        devs.trimToSize();

        while(!devs.isEmpty()) {
            Device device = devs.get(0);
            ret.append("<option value=\""+device.getId()+"\"");
            ret.append(">"+space+device.getDisplayTitle(language)+"</option>");
            origList.remove(device);
            devs.remove(0);
            if(device.listChilds().hasNext()) {
                renderDeviceSelect(origList, device, language, ret, space+"&nbsp;&nbsp;&nbsp;");
            }
        }
    }

    /**
     * Do render language select.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderLanguageSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");

        PrintWriter out = response.getWriter();

        String websiteId = request.getParameter("site");
        WebSite webSite = SWBContext.getWebSite(websiteId);

        out.println("<select name=\"wb_langid\" id=\"wb_langid\" size=\"1\">");
        out.println("<option value=\"0\"></option>");
        Iterator<Language> itLanguages = webSite.listLanguages();
        while (itLanguages.hasNext()) {
            Language language = itLanguages.next();
            out.println("<option value=\"" + language.getId() + "\">"+language.getDisplayTitle(paramsRequest.getUser().getLanguage())+"</option>");
        }
        out.println("</select>");
        out.flush();
        out.close();
    }

    /**
     * Do render user type select.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doRenderUserTypeSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");

        PrintWriter out = response.getWriter();

        out.println("<select name=\"wb_usertype\" id=\"wb_usertype\" size=\"1\">");
        out.println("<option value=\"0\"></option>");
        out.println("</select>");
        out.flush();
        out.close();
    }

    /**
     * Do fill report agregate.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doFillReportAgregate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            //jobj.put("identifier", "uri");
            jobj.put("label", "agregate");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
        }
        
        long i = 1;
        Iterator<String[]> records = getReportResults(request, paramsRequest);
        while(records.hasNext()) {
            String[] cols = records.next();
            JSONObject obj = new JSONObject();
            try {
                //obj.put("detail", "<a onClick=\"doDetail('width=860, height=580, scrollbars, resizable, alwaysRaised, menubar','"+cols[0]+"')\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/SEARCH.png\" border=\"0\" alt=\"detail\"></a>&nbsp;");
                obj.put("folio", Long.toString(i++));
                obj.put("date", cols[0]);
                obj.put("agregate", cols[1]);
                jarr.put(obj);
            }catch (JSONException jsone) {
            }
        }
        response.getOutputStream().println(jobj.toString());
    }

    /**
     * Do fill report detalled.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doFillReportDetalled(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jobj.put("label", "date");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
        }

        /*getReportResults(request, paramsRequest);*/
        String s_key = (String)request.getSession(true).getAttribute("alfilter");

        if(null!=hm_detail) {
            if(null!=s_key) {
                Vector vec_rep = (Vector) hm_detail.get(s_key);
                if( null!=vec_rep && !vec_rep.isEmpty() ) {
                    Iterator<String> ite_rep = vec_rep.iterator();
                    int i=1;
                    while(ite_rep.hasNext()) {
                        StringTokenizer s_token = new StringTokenizer(ite_rep.next(),"|");
                        String date = s_token.nextToken();
                        String ipuser = s_token.nextToken();
                        String ipserver = s_token.nextToken();
                        String sessionId = s_token.nextToken();
                        String siteId = s_token.nextToken();
                        String sectionId = s_token.nextToken();
                        String repId = s_token.nextToken();
                        String userId = s_token.nextToken();
                        String usertypeId = s_token.nextToken();
                        String deviceId = s_token.nextToken();
                        String languageId = s_token.nextToken();
                        String resId = s_token.nextToken();
                        

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("serial", i);
                            obj.put("date", date);
                            obj.put("ipuser", ipuser);
                            obj.put("ipserver", ipserver);
                            obj.put("sessid", sessionId);
                            obj.put("siteid", siteId);
                            obj.put("sectid", sectionId);
                            obj.put("rep", repId);
                            obj.put("user", userId);
                            obj.put("usertype", usertypeId);
                            obj.put("devid", deviceId);
                            obj.put("langid", languageId);
                            obj.put("resid", resId);
                            jarr.put(obj);
                        }catch (JSONException jsone) {
                        }
                        i++;
                    }
                }
            }
        }
        response.getOutputStream().println(jobj.toString());
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        Resource base = paramsRequest.getResourceBase();
        StringBuilder ret = new StringBuilder();
        HashMap hm_sites = new HashMap();

        try {
            // Evaluates if there are sites
            Iterator<WebSite> webSites = SWBContext.listWebSites();
            while(webSites.hasNext()) {
                WebSite site = webSites.next();
                // Evaluates if TopicMap is not Global
                if(!site.getId().equals(SWBContext.getGlobalWebSite().getId())) {
                    hm_sites.put(site.getId(), site.getDisplayTitle(paramsRequest.getUser().getLanguage()));
                }
            }
            // If there are sites continue
            if(hm_sites.size() > 0) {
                String address = paramsRequest.getWebPage().getUrl();
                String websiteId = request.getParameter("wb_site")==null ? (String)hm_sites.keySet().iterator().next():request.getParameter("wb_site");
                String repositoryName = SWBContext.getWebSite(websiteId).getUserRepository().getDisplayTitle(paramsRequest.getUser().getLanguage());

                GregorianCalendar now = new GregorianCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);

                // javascript
                ret.append("<script type=\"text/javascript\">\n");

                ret.append("dojo.require(\"dojox.grid.DataGrid\");\n");
                ret.append("dojo.require(\"dojo.data.ItemFileReadStore\");\n");
                ret.append("dojo.require(\"dijit.form.TimeTextBox\");\n");
                
                ret.append("dojo.addOnLoad(refresh);\n");

                ret.append("function refresh() {\n");
                ret.append("    postHtml('"+url+"'+'/_mod/fillDevSel'+'?site='+dojo.byId('wb_site').options[dojo.byId('wb_site').selectedIndex].value,'dev_cntr');\n");
                ret.append("    postHtml('"+url+"'+'/_mod/fillLangSel'+'?site='+dojo.byId('wb_site').options[dojo.byId('wb_site').selectedIndex].value,'lang_cntr');\n");
                //ret.append("    postHtml('"+url+"'+'/_mod/fillUTSel'+'?site='+dojo.byId('wb_site').options[dojo.byId('wb_site').selectedIndex].value,'ut_cntr');\n");
                ret.append("}\n");

                ret.append("dojo.addOnLoad(function(){\n");
                ret.append("   var t = new Date();\n");
                ret.append("   var t1 = new dijit.form.TimeTextBox({name:\"t1\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 0, 0),\n");
                ret.append("                constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}\n");
                ret.append("   }, \"wb_t11\");\n");

                ret.append("   var t2 = new dijit.form.TimeTextBox({name:\"t2\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 23, 59),\n");
                ret.append("                constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}\n");
                ret.append("   }, \"wb_t12\");\n");
                ret.append("});\n");


                ret.append("function fillGrid(grid, uri, mode, params) {\n");
                ret.append("   grid.store = new dojo.data.ItemFileReadStore({url: uri+'/_mod/'+mode+params});\n");
                ret.append("   grid._refresh();\n");
                ret.append("}\n");

                ret.append("var layout= null;\n");
                ret.append("var jStrMaster = null;\n");
                ret.append("var gridMaster = null;\n");
                ret.append("var gridResources = null;\n");
                
                ret.append("dojo.addOnLoad(function() {\n");
                ret.append("   layout= [\n");
                ret.append("      { field:\"folio\", width:\"5%\", name:\"Num\" },\n");
                ret.append("      { field:\"date\", width:\"33%\", name:\"Fecha\" },\n");
                ret.append("      { field:\"agregate\", width:\"33%\", name:\"Total Agregado\" },\n");
                ret.append("   ];\n");

                ret.append("   gridMaster = new dojox.grid.DataGrid({\n");
                ret.append("      id: \"gridMaster\",\n");
                ret.append("      structure: layout,\n");
                ret.append("      rowSelector: \"10px\",\n");
                ret.append("      rowsPerPage: \"15\"\n");
                ret.append(",onRowDblClick: fillReportDetalled   \n");
                ret.append("   }, \"gridMaster\");\n");
                ret.append("   gridMaster.startup();\n");
                ret.append("});\n");

                ret.append("function fillReportDetalled(evt) {\n");
                ret.append("  doDetail('width=860, height=580, scrollbars, resizable, alwaysRaised, menubar',evt.grid.store.getValue(evt.grid.getItem(evt.rowIndex),'date')); \n");
                ret.append("}\n");

                ret.append("function getParams() {\n");
                ret.append("   var params = '?';\n");
                ret.append("   params += 'siteid='+dojo.byId('wb_site').value;\n");
                ret.append("   params += '&fecha11='+dojo.byId('wb_fecha11').value;\n");
                ret.append("   params += '&t11='+dojo.byId('wb_t11').value;\n");
                ret.append("   params += '&fecha12='+dojo.byId('wb_fecha12').value;\n");
                ret.append("   params += '&t12='+dojo.byId('wb_t12').value;\n");
                ret.append("   if(dojo.byId('wb_ipuser').value) {\n");
                ret.append("      params += '&ipuser='+dojo.byId('wb_ipuser').value;\n");
                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_ipserver').value) {\n");
                ret.append("      params += '&ipserver='+dojo.byId('wb_ipserver').value;\n");
                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_sectid').value) {\n");
                ret.append("      params += '&sectid='+dojo.byId('wb_sectid').value;\n");
                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_subsect').checked) {\n");
                ret.append("      params += '&subsection=true';\n");
                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_userid').value) {\n");
                ret.append("      params += '&userid='+dojo.byId('wb_userid').value;\n");
                ret.append("   }\n");
//                ret.append("   if(dojo.byId('wb_usertype').value) {\n");
//                ret.append("      params += '&usertype='+dojo.byId('wb_usertype').value;\n");
//                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_devid').value) {\n");
                ret.append("      params += '&devid='+dojo.byId('wb_devid').value;\n");
                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_langid').value) {\n");
                ret.append("      params += '&langid='+dojo.byId('wb_langid').value;\n");
                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_resid').value) {\n");
                ret.append("      params += '&resid='+dojo.byId('wb_resid').value;\n");
                ret.append("   }\n");
                ret.append("   if(dojo.byId('wb_sessid').value) {\n");
                ret.append("      params += '&sessid='+dojo.byId('wb_sessid').value;\n");
                ret.append("   }\n");
                ret.append("   params += '&agregate='+getAgregate();\n");
                ret.append("   return params;\n");
                ret.append("}\n");

                ret.append("function doApply() {\n");
                ret.append("   var params = getParams();\n");
                ret.append("   var grid = dijit.byId('gridMaster');\n");
                ret.append("   fillGrid(grid, '"+url+"', 'fillGridAgrd', params);\n");
                ret.append("}\n");
                
                ret.append("function getAgregate() {\n");
                ret.append("   var agrd = \"2\";\n");
                ret.append("   for(i=0; i<window.document.frmrep.wb_agregate.length; i++) {\n");
                ret.append("      if(window.document.frmrep.wb_agregate[i].checked==true) {\n");
                ret.append("         strType = window.document.frmrep.wb_agregate[i].value;\n");
                ret.append("      }\n");
                ret.append("   }\n");
                ret.append("   return strType;\n");
                ret.append("}\n");

                ret.append("function doDetail(size, key) { \n");
                ret.append("   var params = getParams();\n");
                ret.append("   params += '&key='+key;\n");
                ret.append("   window.open(\""+url.setMode("report_detail")+"\"+params,\"detailWindow\", size);\n");
                ret.append("}\n");
                
                ret.append("function doGraph(accion, size) {\n");
                ret.append("   var params = getParams();\n");
                ret.append("   window.open(\""+url.setMode("histogram")+"\"+params,\"graphWindow\",size);\n");
                ret.append("}\n");

                ret.append("function doExcel(accion, size) {\n");
                ret.append("   var params = getParams();\n");
                ret.append("   window.open(\""+url.setMode("xls")+"\"+params,\"graphWindow\",size);\n");
                ret.append("}\n");

                ret.append("function doXml(accion, size) {\n");
                ret.append("   var params = getParams();\n");
                ret.append("   window.open(\""+url.setMode("xml")+"\"+params,\"graphWindow\",size);\n");
                ret.append("}\n");

                ret.append("</script>\n");

                ret.append("<div class=\"swbform\">\n");
                ret.append("<form method=\"Post\" class=\"box\" action=\"\" id=\"frmrep\" name=\"frmrep\">\n");
                ret.append("<fieldset>\n");
                ret.append("<table border=\"0\" width=\"95%\" align=\"center\">\n");
                ret.append("<tr><td width=\"200\"></td><td width=\"200\"></td><td width=\"200\"></td><td width=\"200\"></td></tr>\n");

                ret.append("<tr>\n");
                ret.append("<td><label for=\"wb_site\">" + paramsRequest.getLocaleString("site") + ":</label></td>\n");
                ret.append("<td>\n");
                ret.append("<select name=\"wb_site\" id=\"wb_site\" onChange=\"refresh();\" >\n");
                Iterator<String> itKeys = hm_sites.keySet().iterator();
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    ret.append("<option value=\"" + key + "\"");
                    if(key.equalsIgnoreCase(websiteId)) {
                        ret.append(" selected=\"selected\"");
                    }
                    ret.append(">" + (String)hm_sites.get(key) + "</option>");
                }
                ret.append("</select>\n");
                ret.append("</td>\n");
                ret.append("<td>&nbsp;</td>\n");
                ret.append("<td>&nbsp;</td>\n");
                ret.append("</tr>\n");
                
                ret.append("<tr>\n");
                ret.append("<td>"+paramsRequest.getLocaleString("by_range")+":&nbsp;</td>\n");
                ret.append("<td>&nbsp;</td>\n");
                ret.append("<td>&nbsp;</td>\n");
                ret.append("<td>&nbsp;</td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                ret.append("<td>&nbsp;</td>\n");
                ret.append("<td align=\"left\" colspan=\"2\">\n");
                ret.append("<label for=\"wb_fecha11\">Del:&nbsp;</label>\n");
                ret.append("<input type=\"text\" name=\"wb_fecha11\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha11\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />\n");
                ret.append("&nbsp;&nbsp;");
                ret.append("<label for=\"wb_t11\">Tiempo:&nbsp;</label>\n");
                ret.append("<input type=\"text\" name=\"wb_t11\" id=\"wb_t11\" size=\"6\" style=\"width:40px;\" />\n");
                ret.append("</td>\n");
                ret.append("<td></td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                ret.append("<td>&nbsp;</td>\n");
                ret.append("<td align=\"left\" colspan=\"2\">\n");
                ret.append("<label for=\"wb_fecha12\">&nbsp;&nbsp;Al:&nbsp;</label>\n");
                ret.append("<input type=\"text\" name=\"wb_fecha12\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha12\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />\n");
                ret.append("&nbsp;&nbsp;");
                ret.append("<label for=\"wb_t12\">Tiempo:&nbsp;</label>\n");
                ret.append("<input name=\"wb_t12\" id=\"wb_t12\" />\n");
                ret.append("</td>\n");
                ret.append("<td></td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                ret.append("<td><label for=\"wb_ipuser\">"+paramsRequest.getLocaleString("ipaddress_user")+":&nbsp;</label></td>\n");
                ret.append("<td><input type=\"text\" name=\"wb_ipuser\" id=\"wb_ipuser\" size=\"20\" /></td>\n");
                ret.append("<td><label for=\"wb_ipserver\">"+paramsRequest.getLocaleString("ipaddress_server")+":&nbsp;</label></td>\n");
                ret.append("<td><input type=\"text\" name=\"wb_ipserver\" id=\"wb_ipserver\" size=\"20\" /></td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                ret.append("<td><label for=\"wb_sectid\">"+paramsRequest.getLocaleString("sectionid")+":&nbsp;</label></td>\n");
                ret.append("<td>\n");
                ret.append("<input type=\"text\" name=\"wb_sectid\" id=\"wb_sectid\" size=\"20\" />");
                ret.append("</td>\n");
                ret.append("<td><label for=\"wb_subsect\">"+paramsRequest.getLocaleString("subsections")+":&nbsp;</label></td>\n");
                ret.append("<td>\n");
                ret.append("<input type=\"checkbox\" name=\"wb_subsect\" id=\"wb_subsect\" value=\"true\" />\n");
                ret.append("</td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                ret.append("<td><label for=\"wb_userid\">"+paramsRequest.getLocaleString("user")+":&nbsp;</label></td>\n");
                ret.append("<td><input type=\"text\" name=\"wb_userid\" id=\"wb_userid\" size=\"20\" /></td>\n");
                ret.append("<td>\n");
                //ret.append("<label for=\"wb_usertype\">"+paramsRequest.getLocaleString("user_type")+":&nbsp;\n");
                ret.append("</td>\n");
                ret.append("<td>\n");
                //ret.append("<div id=\"ut_cntr\"></div>\n");
                ret.append("</td>\n");
                ret.append("</tr>\n");
                
                ret.append("<tr>\n");
                // DEVICES
                ret.append("<td><label for=\"wb_devid\">"+paramsRequest.getLocaleString("device")+":&nbsp;</label></td>\n");
                ret.append("<td>\n");
                ret.append("<div id=\"dev_cntr\"></div>\n");
                ret.append("</td>\n");
                // LANGUAGES
                ret.append("<td><label for=\"wb_langid\">"+paramsRequest.getLocaleString("language")+":&nbsp;</label></td>\n");
                ret.append("<td>\n");
                ret.append("<div id=\"lang_cntr\"></div>\n");
                ret.append("</td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                // RESOURCE
                ret.append("<td><label for=\"wb_resid\">"+paramsRequest.getLocaleString("id_resource")+":&nbsp;</label></td>\n");
                ret.append("<td><input type=\"text\" name=\"wb_resid\" id=\"wb_resid\" size=\"20\" maxlength=\"4\" /></td>\n");
                // SESSION
                ret.append("<td><label for=\"wb_sessid\">"+paramsRequest.getLocaleString("session")+":&nbsp;</label></td>\n");
                ret.append("<td><input type=\"text\" name=\"wb_sessid\" id=\"wb_sessid\" size=\"20\" /></td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                // USER REPOSITORY
                ret.append("<td><label for=\"wb_urepid\">"+paramsRequest.getLocaleString("repository")+":&nbsp;</label></td>\n");
                ret.append("<td>\n");
                ret.append("<input type=\"text\" name=\"wb_urepid\" id=\"wb_urepid\" value=\""+repositoryName+"\" readonly=\"readonly\" />\n");
                ret.append("</td>\n");
                ret.append("<td></td>\n");
                ret.append("<td></td>\n");
                ret.append("</tr>\n");

                ret.append("<tr>\n");
                ret.append("<td height=\"25px\">"+paramsRequest.getLocaleString("agregate_by")+":&nbsp;</td>\n");
                ret.append("<td colspan=\"3\" height=\"25px\">\n");
                ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"1\" />"+paramsRequest.getLocaleString("by_year")+"</label>&nbsp;&nbsp;\n");
                ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"2\" checked=\"checked\" />"+paramsRequest.getLocaleString("by_month")+"</label>&nbsp;&nbsp;\n");
                ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"3\" />"+paramsRequest.getLocaleString("by_day")+"</label>&nbsp;&nbsp;\n");
                ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"4\" />"+paramsRequest.getLocaleString("by_hour")+"</label>\n");
                ret.append("</td>\n");
                ret.append("</tr>\n");
                ret.append("</table>\n");
                ret.append("</fieldset>\n");

                ret.append("<fieldset>\n");
                ret.append("<table border=\"0\" width=\"95%\">\n");
                ret.append(" <tr>\n");
                ret.append("     <td colspan=\"4\">&nbsp;&nbsp;&nbsp;\n");
                ret.append("     <button dojoType=\"dijit.form.Button\" onClick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;\n");
                ret.append("     <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">"+paramsRequest.getLocaleString("spread_sheet")+"</button>&nbsp;\n");
                ret.append("     <button dojoType=\"dijit.form.Button\" onClick=\"doGraph('width=600, height=550, scrollbars, resizable')\">"+paramsRequest.getLocaleString("histogram")+"</button>&nbsp;\n");
                ret.append("     <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">"+paramsRequest.getLocaleString("apply")+"</button>\n");
                ret.append("     </td>\n");
                ret.append(" </tr>\n");
                ret.append("</table>\n");
                ret.append("</fieldset>\n");
                ret.append("</form>\n");

                ret.append("<fieldset>\n");
                ret.append("<table border=\"0\" width=\"95%\" align=\"center\">\n");
                ret.append("<tr>\n");
                ret.append("<td colspan=\"4\">\n");
                ret.append("<div id=\"ctnergrid\" style=\"height:250px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">\n");
                ret.append("  <div id=\"gridMaster\"></div>\n");
                ret.append("</div>\n");
                ret.append("</td>\n");
                ret.append("</tr>\n");
                ret.append("</table>\n");
                ret.append("</fieldset>\n");

                ret.append("</div>\n");
            }else { // There are not sites and displays a message
                ret.append("\n<form method=\"Post\" class=\"box\" action=\"" + paramsRequest.getWebPage().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
                ret.append("\n<table border=0 width=\"100%\">");
                ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                ret.append("\n<tr>");
                ret.append("\n<td>&nbsp;</td>");
                ret.append("\n<td colspan=\"2\" align=\"center\" class=\"datos\">" + paramsRequest.getLocaleString("no_sites_found") + "</td>");
                ret.append("\n<td>&nbsp;</td>");
                ret.append("\n</tr>");
                ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                ret.append("\n</table></form>");
            }
        }catch (Exception e) {
            log.error("Error on method DoView() resource " + strRscType +  " with id " +  base.getId(), e);
        }
        response.getWriter().print(ret.toString());
    }

    /**
     * Do detail.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();

        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setCallMethod(paramsRequest.Call_DIRECT).setMode("fillGridDtd");
        request.getSession(true).setAttribute("alfilter", request.getParameter("key"));

        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />");
        out.println("<title>Logs</title>");

        out.println("<style type=\"text/css\">");
        out.println("@import \""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dojo/resources/dojo.css\";");
        out.println("@import \""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dijit/themes/soria/soria.css\";");
        out.println("@import \""+SWBPlatform.getContextPath()+"/swbadmin/css/swb.css\";");
        out.println("@import \""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dojox/grid/resources/soriaGrid.css\";");
        out.println("@import \""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dojox/grid/resources/Grid.css\";");
        
        out.println("   .dojoxGrid table {");
        out.println("       margin: 0x;");
        out.println("   }");
        out.println("</style>");

        out.println("<script type=\"text/javascript\" src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad: true, isDebug: false\"></script>");
        out.println("<script type=\"text/javascript\">");
        out.println("    dojo.require(\"dojox.grid.DataGrid\");");
        out.println("    dojo.require(\"dojo.data.ItemFileReadStore\");");
        out.println("</script>");

        out.println("</head>");
        out.println("<body class=\"soria\">");

        out.println("<div class=\"swbform\" style=\"margin:3px;\">");
        out.println("<fieldset>");
        out.println("Detalle de Accesos - "+request.getParameter("key"));
        out.println("</fieldset>");
        out.println("<br />");
        out.println("<fieldset>");
        out.println("<span dojoType=\"dojo.data.ItemFileReadStore\" jsId=\"store1\" url=\""+url+"\"></span>");

        out.println("<table dojoType=\"dojox.grid.DataGrid\"");
        out.println("    jsId=\"griddtd\"");
        out.println("    store=\"store1\"");
        out.println("    rowsPerPage=\"20\"");
        out.println("    clientSort=\"true\"");
        out.println("    style=\"width:830px; height:500px; margin:5px;\"");
        out.println("    rowSelector=\"20px\">");
        out.println("    <thead>");
        out.println("        <tr>");
        out.println("            <th width=\"5%\" field=\"serial\">"+paramsRequest.getLocaleString("th_Num")+"</th>");
        out.println("            <th width=\"14%\" field=\"date\">"+paramsRequest.getLocaleString("th_Date")+"</th>");
        out.println("            <th width=\"7%\" field=\"ipuser\">"+paramsRequest.getLocaleString("th_IPuser")+"</th>");
        out.println("            <th width=\"7%\" field=\"ipserver\">"+paramsRequest.getLocaleString("th_IPserver")+"</th>");
        out.println("            <th width=\"7%\" field=\"sessid\">"+paramsRequest.getLocaleString("th_IDsession")+"</th>");
        out.println("            <th width=\"7%\" field=\"siteid\">"+paramsRequest.getLocaleString("th_Portal")+"</th>");
        out.println("            <th width=\"7%\" field=\"sectid\">"+paramsRequest.getLocaleString("th_Section")+"</th>");
        out.println("            <th width=\"7%\" field=\"rep\">"+paramsRequest.getLocaleString("th_Repository")+"</th>");
        out.println("            <th width=\"7%\" field=\"user\">"+paramsRequest.getLocaleString("th_User")+"</th>");
        out.println("            <th width=\"7%\" field=\"usertype\">"+paramsRequest.getLocaleString("th_UserType")+"</th>");
        out.println("            <th width=\"7%\" field=\"devid\">"+paramsRequest.getLocaleString("th_Device")+"</th>");
        out.println("            <th width=\"7%\" field=\"langid\">"+paramsRequest.getLocaleString("th_Language")+"</th>");
        out.println("            <th width=\"7%\" field=\"resid\">"+paramsRequest.getLocaleString("th_Resource")+"</th>");
        out.println("        </tr>");
        out.println("    </thead>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("</body>");
        out.println("</html>");

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
        StringBuffer ret = new StringBuffer();
        Resource base = paramsRequest.getResourceBase();
        Iterator<String[]> ar_pag = null;

        try {
            ret.append("<html>\n");
            ret.append("<head>\n");
            ret.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n");
            ret.append("<title>" + paramsRequest.getLocaleString("access_log_report") + "</title>\n");

            ret.append("<style type=\"text/css\">\n");
            ret.append("@import \""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dojo/resources/dojo.css\";\n");
            ret.append("@import \""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dijit/themes/soria/soria.css\";\n");
            ret.append("@import \""+SWBPlatform.getContextPath()+"/swbadmin/css/swb.css\";\n");
            ret.append("</style>\n");

            ret.append("<script type=\"text/javascript\" src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad: true, isDebug: false\"></script>\n");
            ret.append("<script type=\"text/javascript\">\n");
            ret.append("    function doPrint() {\n");
            ret.append("        window.print();\n");
            ret.append("    }\n");
            ret.append("    function doClose() {\n");
            ret.append("        window.close();\n");
            ret.append("    }\n");
            ret.append("</script>\n");
            ret.append("</head>\n");

            ret.append("<body class=\"soria\">\n");
//            ret.append("<div class=\"swbform\">\n");
//            ret.append("<form>");
//            ret.append("<fieldset>\n");
            ret.append("<table border=\"0\" width=\"98%\" height=\"460\">\n");
            ret.append("<tr>\n");
            ret.append("<td colspan=\"3\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/images/swb-logo-hor.jpg\" width=\"180\" height=\"36\" /></td>\n");
            ret.append("</tr>\n");
            ret.append("<tr>\n");
            ret.append("<td colspan=\"3\" align=\"right\">\n");
            ret.append("<button dojoType=\"dijit.form.Button\" onClick=\"doPrint()\">"+paramsRequest.getLocaleString("print")+"</button>&nbsp;\n");
            ret.append("<button dojoType=\"dijit.form.Button\" onClick=\"doClose()\">"+paramsRequest.getLocaleString("close")+"</button>&nbsp;\n");
            ret.append("</td>\n");
            ret.append("</tr>\n");
            ret.append("<tr>\n");
            ret.append("<td colpsan=\"3\">&nbsp;</td>\n");
            ret.append("</tr>\n");
            ret.append("<tr>\n");
            ret.append("<td colpsan=\"3\">\n");

            int ndata = 0;
            ar_pag = getReportResults(request, paramsRequest);
            if(ar_pag.hasNext()) {
                ret.append("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/SWBAplGraph.jar\" width=\"98%\" height=\"450\">\n");
                ret.append("<param name=\"GraphType\" value=\"Lines\">\n");
                ret.append("<param name=\"ncdata\" value=\"1\">\n");
                ret.append("<param name=\"percent\" value=\"false\">\n");
                while(ar_pag.hasNext()) {
                    String[] data = ar_pag.next();
                    ret.append("<param name=\"label"+ndata+"\" value=\""+data[0]+"\">\n");
                    ret.append("<param name=\"data"+ndata+"\"  value=\""+data[1]+"\">\n");
                    ndata++;
                }
                String title = paramsRequest.getLocaleString("access_log_report");
                ret.append("<param name=\"Title\" value=\"" + title + "\">\n");
                ret.append("<param name=\"ndata\" value=\""+ ndata +"\">\n");
                ret.append("<param name=\"color0\" value=\"66,138,212\">\n");
                ret.append("<param name=\"barname0\" value=\"Hits\">\n");
                ret.append("<param name=\"zoom\" value=\"true\">\n");
                ret.append("</APPLET>\n");
            }else {
                ret.append(paramsRequest.getLocaleString("no_records"));
            }

            ret.append("</td>\n");
            ret.append("</tr>\n");
            ret.append("</table>\n");
//            ret.append("</fieldset>\n");
//            ret.append("</form>");
//            ret.append("</div>\n");
            ret.append("</body>\n");
            ret.append("</html>\n");
        }
        catch (Exception e) {
            log.error("Error on method doGraph() resource " + strRscType + " with id " + base.getId(), e);
        }
        response.getWriter().print(ret.toString());
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
        response.setHeader("Content-Disposition", "inline; filename=\"alr.xls\"");
        
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + paramsRequest.getLocaleString("access_log_report") + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("  <table border=\"1\" width=\"550\">");
            out.println("    <caption align=\"top\">");
            out.println(paramsRequest.getLocaleString("access_log_report"));
            out.println("    </caption>");
            out.println("    <tr>");
            out.println("      <th width=\"40%\" scope=\"col\">" + paramsRequest.getLocaleString("date") + "</th>");
            out.println("      <th width=\"60%\" scope=\"col\">" + paramsRequest.getLocaleString("access") + "</th>");
            out.println("    </tr>");
            Iterator<String[]> ar_pag = getReportResults(request, paramsRequest);
            if(ar_pag.hasNext()) {
                while(ar_pag.hasNext()) {
                    String[] arr_data = ar_pag.next();
                    out.println("<tr>");
                    out.println("<td align=\"center\">" + arr_data[0] + "</td>");
                    out.println("<td align=\"center\">" + arr_data[1] + "</td>");
                    out.println("</tr>");
                }
            }else { // There are not records
                out.println("<center><br><font color=\"black\">" + paramsRequest.getLocaleString("no_records") + "</font></center>");
            }
            out.println("</table>");
            
            String key = (String)request.getSession(true).getAttribute("alfilter");
            if( hm_detail.containsKey(key) ) {
                out.println("<br />");
                out.println("<table border=\"1\" width=\"850\">");
                out.println("    <caption align=\"top\">");
                out.println("Detalle de Accesos - "+key);
                out.println("    </caption>");
                out.println("<tr>");
                out.println("<td>"+paramsRequest.getLocaleString("th_Num")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_Date")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_IPuser")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_IPserver")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_IDsession")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_TopicMap")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_Topic")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_Repository")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_User")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_UserType")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_Device")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_Language")+"</td>");
                out.println("<td>"+paramsRequest.getLocaleString("th_Resource")+"</td>");
                out.println("</tr>");
            
                Vector vec_rep = (Vector) hm_detail.get(key);
                Iterator<String> ite_rep = vec_rep.iterator();
                int i=0;
                while(ite_rep.hasNext()) {
                    StringTokenizer token = new StringTokenizer(ite_rep.next(),"|");
                    String date = token.nextToken();
                    String ipuser = token.nextToken();
                    String ipserver = token.nextToken();
                    String sessionId = token.nextToken();
                    String siteId = token.nextToken();
                    String sectionId = token.nextToken();
                    String repId = token.nextToken();
                    String userId = token.nextToken();
                    String usertypeId = token.nextToken();
                    String deviceId = token.nextToken();
                    String languageId = token.nextToken();
                    String resId = token.nextToken();

                    out.println("<tr>");
                    out.println("<td>"+(++i)+"</td>");
                    out.println("<td>"+date+"</td>");
                    out.println("<td>"+ipuser+"</td>");
                    out.println("<td>"+ipserver+"</td>");
                    out.println("<td>"+sessionId+"</td>");
                    out.println("<td>"+siteId+"</td>");
                    out.println("<td>"+sectionId+"</td>");
                    out.println("<td>"+repId+"</td>");
                    out.println("<td>"+userId+"</td>");
                    out.println("<td>"+usertypeId+"</td>");
                    out.println("<td>"+deviceId+"</td>");
                    out.println("<td>"+languageId+"</td>");
                    out.println("<td>"+resId+"</td>");
                    out.println("</tr>");
                }
                out.println("</table>\n");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }catch (Exception ex) {
            log.error("Error on method doRepExcel() resource " + strRscType + " with id " + base.getId(), ex);
        }
        out.flush();
        out.close();
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
        PrintWriter out = response.getWriter();

        Document dom = SWBUtils.XML.getNewDocument();
        Resource base = getResourceBase();

        try {
            Element report = dom.createElement("AccessLogReport");
            dom.appendChild(report);

            Element agregate = dom.createElement("agregate");
            agregate.appendChild(dom.createTextNode(""));
            report.appendChild(agregate);
            
            Iterator<String[]> ar_pag = getReportResults(request, paramsRequest);
            int renglones = 0;
            while(ar_pag.hasNext()) {
                String[] arr_data = ar_pag.next();
                
                Element row = dom.createElement("row");
                row.appendChild(dom.createTextNode("")); 
                row.setAttribute("id",Integer.toString(++renglones));
                Element date = dom.createElement("date");
                date.appendChild(dom.createTextNode(arr_data[0]));
                row.appendChild(date);                
                Element access = dom.createElement("access");
                access.appendChild(dom.createTextNode(arr_data[1]));
                row.appendChild(access);                
                agregate.appendChild(row);                
            }
            agregate.setAttribute("rows",Integer.toString(renglones));

            String key = (String)request.getSession(true).getAttribute("alfilter");
            if( hm_detail.containsKey(key) ) {
                Element detail = dom.createElement("detail");
                detail.appendChild(dom.createTextNode(""));
                report.appendChild(detail);

                Vector vec_rep = (Vector) hm_detail.get(key);
                Iterator<String> ite_rep = vec_rep.iterator();
                renglones = 0;
                while(ite_rep.hasNext()) {
                    StringTokenizer token = new StringTokenizer(ite_rep.next(),"|");
                    String datetime = token.nextToken();
                    String ipuser = token.nextToken();
                    String ipserv = token.nextToken();
                    String sessId = token.nextToken();
                    String siteId = token.nextToken();
                    String sectId = token.nextToken();
                    String repId = token.nextToken();
                    String userId = token.nextToken();
                    String usertype = token.nextToken();
                    String devId = token.nextToken();
                    String langId = token.nextToken();
                    String resId = token.nextToken();

                    Element row = dom.createElement("row");
                    row.appendChild(dom.createTextNode(""));
                    row.setAttribute("id",Integer.toString(++renglones));
                    Element date = dom.createElement("date");
                    date.appendChild(dom.createTextNode(datetime));
                    row.appendChild(date);
                    Element ipclient = dom.createElement("ipuser");
                    ipclient.appendChild(dom.createTextNode(ipuser));
                    row.appendChild(ipclient);
                    Element ipserver = dom.createElement("ipserver");
                    ipserver.appendChild(dom.createTextNode(ipserv));
                    row.appendChild(ipserver);
                    Element session = dom.createElement("sessId");
                    session.appendChild(dom.createTextNode(sessId));
                    row.appendChild(session);
                    Element site = dom.createElement("siteId");
                    site.appendChild(dom.createTextNode(siteId));
                    row.appendChild(site);
                    Element section = dom.createElement("sectId");
                    section.appendChild(dom.createTextNode(sectId));
                    row.appendChild(section);
                    Element rep = dom.createElement("rep");
                    rep.appendChild(dom.createTextNode(repId));
                    row.appendChild(rep);
                    Element user = dom.createElement("user");
                    user.appendChild(dom.createTextNode(userId));
                    row.appendChild(user);
                    Element usrtype = dom.createElement("usertype");
                    usrtype.appendChild(dom.createTextNode(usertype));
                    row.appendChild(usrtype);
                    Element dev = dom.createElement("devId");
                    dev.appendChild(dom.createTextNode(devId));
                    row.appendChild(dev);
                    Element lang = dom.createElement("langId");
                    lang.appendChild(dom.createTextNode(langId));
                    row.appendChild(lang);
                    Element res = dom.createElement("res");
                    res.appendChild(dom.createTextNode(resId));
                    row.appendChild(res);

                    detail.appendChild(row);
                }
                detail.setAttribute("rows",Integer.toString(renglones));
            }                       
        }catch (Exception e){
            log.error("Error on method doRepXml() resource " + strRscType + " with id " + base.getId(), e);
        }
        out.print(SWBUtils.XML.domToXml(dom));
        out.flush();
        out.close();
    }

    /**
     * Gets the file names.
     * 
     * @param request the request
     * @return the file names
     * @return
     */
    public Iterator<String> getFileNames(HttpServletRequest request) {
        ArrayList files = new ArrayList();

        String accessLogPath = SWBPlatform.getEnv("swb/accessLog");
        String period = SWBPlatform.getEnv("swb/accessLogPeriod");
        String path = SWBPortal.getWorkPath();
        String siteId = request.getParameter("siteid");

        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha11 = request.getParameter("fecha11")==null ? sdf.format(cal.getTime()):request.getParameter("fecha11");
        try {
            sdf.parse(fecha11);
        }catch(ParseException pe){
            fecha11 = sdf.format(cal.getTime());
        }
        String t11 = request.getParameter("t11")==null ? "00:00":request.getParameter("t11");

        String fecha12 = request.getParameter("fecha12")==null ? sdf.format(cal.getTime()):request.getParameter("fecha12");
        try {
            sdf.parse(fecha12);
        }catch(ParseException pe){
            fecha12 = sdf.format(cal.getTime());
        }
        String t12 = request.getParameter("t12")==null ? "23:59":request.getParameter("t12");

        int year11 = Integer.parseInt(fecha11.substring(6,10),10);
        int month11 = Integer.parseInt(fecha11.substring(3,5),10);
        int day11 = Integer.parseInt(fecha11.substring(0,2),10);
        int hour11 = Integer.parseInt(t11.substring(0,2),10);
        int minute11 = Integer.parseInt(t11.substring(3,5),10);
        int year12 = Integer.parseInt(fecha12.substring(6,10),10);
        int month12 = Integer.parseInt(fecha12.substring(3,5),10);
        int day12 = Integer.parseInt(fecha12.substring(0,2),10);
        int hour12 = Integer.parseInt(t12.substring(0,2),10);
        int minute12 = Integer.parseInt(t12.substring(3,5),10);

        GregorianCalendar first = new GregorianCalendar(year11, month11-1, day11, hour11, minute11);
        GregorianCalendar last  = new GregorianCalendar(year12, month12-1, day12, hour12, minute12);

        if(siteId!=null) {
            String realpath = path + accessLogPath + "_" + siteId + "_hit.";

            if(period.equalsIgnoreCase("daily")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                while( !first.after(last) ) {
                    files.add(realpath+sdf.format(first.getTime())+".log");
                    first.add(Calendar.DATE, 1);
                }
            }else if(period.equalsIgnoreCase("monthly")) {
                sdf = new SimpleDateFormat("yyyy-MM");
                int addedDays;
                while( !first.after(last) ) {
                    files.add(realpath+sdf.format(first.getTime())+".log");
                    addedDays = first.getActualMaximum(Calendar.DAY_OF_MONTH)-first.get(Calendar.DAY_OF_MONTH)+1;
                    first.add(Calendar.DATE, addedDays);
                }

            }else if(period.equalsIgnoreCase("yearly")) {
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

    /**
     * Gets the report results.
     * 
     * @param request the request
     * @param paramsRequest the params request
     * @return the report results
     * @return
     */
    private Iterator<String[]> getReportResults(HttpServletRequest request, SWBParamRequest paramsRequest) {
        final int I_ZERO = 0;
        final int I_ONE = 1;
        /*final int I_TWO = 2;
        final int I_THREE = 3;
        final int I_FOUR = 4;
        final int I_FIVE = 5;
        final int I_SIX = 6;
        final int I_SEVEN = 7;
        final int I_EIGHT = 8;
        final int I_NINE = 9;
        final int I_TEN = 10;*/
        final int I_TWENTYFOUR = 24;
        /*final int I_LESSONE = -1;*/

        hm_detail = new HashMap();

        ArrayList al_pag = new ArrayList();        
        String[] arr_data = null;
        GregorianCalendar datefile = null;
        GregorianCalendar datedisplay = null;
        GregorianCalendar datedefault = null;        

        String line = null;
        String s_aux = null;
        /*String s_resource = null;*/
        String filename = null;

        String yearinfile = null;
        String monthinfile = null;
        String dayinfile = null;
        String hourinfile = null;
        String mininfile = null;
        String dateinfile = null;
        /*String s_auxresourceid = null;*/
        String s_datedefault = null;
        String s_hourfin = null;
        String s_year = null;

        /*boolean b_ipadduser = false;
        boolean b_ipaddserver = false;
        boolean b_topicid = false;
        boolean b_userid = false;
        boolean b_languagesel = false;
        boolean b_devicesel = false;
        boolean b_usersel = false;
        boolean b_resourceid = false;*/
        boolean b_result = true;
       /* boolean b_sessionid = false;*/

        long l_count = 0;
        /*int i = 0;
        int col = 0;
        int i_len = 0;*/
        int i_new = 0;
        int i_hourini = 0;
        int i_hourfin = 0;
        int i_start = 0;


        // Receive parameters
        String siteId = request.getParameter("siteid");
        WebSite website = SWBContext.getWebSite(siteId);
        WebPage sectionParent = null;
        WebPage webpage = null;

        GregorianCalendar now = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String date11 = request.getParameter("fecha11")==null ? sdf.format(now.getTime()):request.getParameter("fecha11")+" "+(request.getParameter("t11")==null ? "00:00":request.getParameter("t11"));
        try {
            sdf.parse(date11);
        }catch(ParseException pe){
            date11 = sdf.format(now.getTime());
        }
        int year11 = Integer.parseInt(date11.substring(6,10),10);
        int month11 = Integer.parseInt(date11.substring(3,5),10);
        int day11 = Integer.parseInt(date11.substring(0,2),10);
        int hour11 = Integer.parseInt(date11.substring(11,13),10);
        int minute11 = Integer.parseInt(date11.substring(14,16),10);
        GregorianCalendar first = new GregorianCalendar(year11, month11-1, day11, hour11, minute11);

        String date12 = request.getParameter("fecha12")==null ? sdf.format(now.getTime()):request.getParameter("fecha12")+" "+(request.getParameter("t12")==null ? "23:59":request.getParameter("t12"));
        try {
            sdf.parse(date12);
        }catch(ParseException pe){
            date12 = sdf.format(now.getTime());
        }
        int year12 = Integer.parseInt(date12.substring(6,10),10);
        int month12 = Integer.parseInt(date12.substring(3,5),10);
        int day12 = Integer.parseInt(date12.substring(0,2),10);
        int hour12 = Integer.parseInt(date12.substring(11,13),10);
        int minute12 = Integer.parseInt(date12.substring(14,16),10);
        GregorianCalendar last  = new GregorianCalendar(year12, month12-1, day12, hour12, minute12);

        String ipadduser = request.getParameter("ipuser");
        String ipaddserver = request.getParameter("ipserver");
        String sessionId = request.getParameter("sessid");
        boolean includeSubsection = request.getParameter("subsection")==null ? false:true;
        String sectionId = request.getParameter("sectid");
        if( sectionId!=null ) {
            sectionParent = website.getWebPage(sectionId);
        }
        String userId = request.getParameter("userid");
        String languageId = request.getParameter("langid");
        String deviceId = request.getParameter("devid");
        String usertypeId = null;
        String resourceId = request.getParameter("resid");
        String agregateId = request.getParameter("agregate")==null ? "2":request.getParameter("agregate");
        
        try {
            Iterator<String> files = getFileNames(request);
            if(files.hasNext()) {
                String s_key=null;
                Vector vec_rep = null;
                while(files.hasNext()) {
                    filename = files.next();
                    File f = new File(filename);
                    if(f.exists()) {
                        RandomAccessFile rf_in = new RandomAccessFile(f,"r");
                        datedefault = null;
                        l_count = 0;
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
                            if((datefile.after(first) || datefile.equals(first)) && ((datefile.before(last) || datefile.equals(last)))) {
                                dateinfile = s_aux;
                            }else {
                                continue;
                            }
                            datefile = null;
                            //1-s_aux receives user ip
                            if(ipadduser!=null && !t[1].equalsIgnoreCase(ipadduser))
                                continue;
                            //2-s_aux receives server ip
                            if(ipaddserver!=null && !t[2].equalsIgnoreCase(ipaddserver))
                                continue;
                            //3-s_aux receives session id
                            if(sessionId!=null && !t[3].equalsIgnoreCase(sessionId))
                                continue;
                            //4-s_aux receives website id, this value no matter
                            //5-s_aux receives section id
                            webpage = website.getWebPage(t[5]);
                            if(  sectionId!=null && (!t[5].equalsIgnoreCase(sectionId) || (!includeSubsection && sectionParent==null && sectionParent.isParentof(webpage)  ) ) ) {
                                continue;
                            }
                            //6-s_aux receives repository id, this value no matter
                            //7-s_aux receives user id
                            if(userId!=null && !t[7].equalsIgnoreCase(userId))
                                continue;
                            //8-s_aux receives user type
                            /*if(usertypeId!=null && !t[8].equalsIgnoreCase(usertypeId))
                                continue;*/
                            //9-s_aux receives device type
                            if(deviceId!=null && !t[9].equalsIgnoreCase(deviceId))
                                continue;
                            //10-s_aux receives language
                            if(languageId!=null && t[10].equalsIgnoreCase(languageId))
                                continue;
                            //11-s_aux receives time in miliseconds, this value no matter
                            // seek for resource id
                            if(resourceId!=null) {
                                b_result = false;
                                for(int r=12; r<t.length; r++) {
                                    if(t[r].equalsIgnoreCase(resourceId)) {
                                        b_result = true;
                                        break;
                                    }
                                }
                            }

                            //Evaluates if record passes
                            if(b_result) {
                                if(agregateId.equalsIgnoreCase("1")) {
                                    //Display by year
                                    datedisplay = new GregorianCalendar(Integer.parseInt(yearinfile),Integer.parseInt(monthinfile),Integer.parseInt(dayinfile));
                                    if(datedefault == null) {
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;
                                    }
                                    int i_yeardisplay = datedisplay.get(Calendar.YEAR);
                                    int i_yeardefault = datedefault.get(Calendar.YEAR);

                                    if(i_yeardisplay == i_yeardefault) {
                                        l_count++;
                                        i_new = 0;
                                    }else {
                                        arr_data = new String[2];
                                        arr_data[0] = s_datedefault.substring(0,4);
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }
                                }else if(agregateId.equalsIgnoreCase("2")) {
                                    //Display by month
                                    datedisplay = new GregorianCalendar(Integer.parseInt(yearinfile),Integer.parseInt(monthinfile),Integer.parseInt(dayinfile));
                                    if(datedefault == null) {
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;
                                    }
                                    int i_monthdisplay = datedisplay.get(Calendar.MONTH);
                                    int i_monthdefault = datedefault.get(Calendar.MONTH);
                                    if(i_monthdisplay == i_monthdefault) {
                                        l_count++;
                                        i_new = 0;
                                    }else {
                                        arr_data = new String[2];
                                        arr_data[0] = s_datedefault.substring(0,7);
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }
                                }else if(agregateId.equalsIgnoreCase("3")) {
                                    //Display by day
                                    datedisplay = new GregorianCalendar(Integer.parseInt(yearinfile),Integer.parseInt(monthinfile),Integer.parseInt(dayinfile));
                                    if(datedefault == null) {
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;
                                    }
                                    if(datedisplay.equals(datedefault)) {
                                        l_count++;
                                        i_new = 0;
                                    }else {
                                        arr_data = new String[2];
                                        arr_data[0] = s_datedefault.substring(0,10);
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }
                                }else if(agregateId.equalsIgnoreCase("4")) {
                                    //Display by hour
                                    datedisplay = new GregorianCalendar(Integer.parseInt(yearinfile),Integer.parseInt(monthinfile),Integer.parseInt(dayinfile),Integer.parseInt(hourinfile),0);

                                    if(datedefault == null) {
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;
                                    }
                                    if(datedisplay.equals(datedefault)) {
                                        l_count++;
                                        i_new = 0;
                                    }else {
                                        arr_data = new String[2];
                                        i_hourini = Integer.parseInt(s_datedefault.substring(11,13));
                                        i_hourfin = i_hourini + 1 ;
                                        if(i_hourfin == I_TWENTYFOUR) {
                                            i_hourfin = 0;
                                            s_hourfin = Integer.toString(i_hourfin) + "0:00";
                                        }else {
                                            s_hourfin = Integer.toString(i_hourfin);
                                            if(s_hourfin.length() == I_ONE) s_hourfin = "0" + s_hourfin;
                                            s_hourfin = s_hourfin + ":00";
                                        }
                                        arr_data[0] = s_datedefault.substring(0,13) + ":00-" + s_hourfin;
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        datedefault = datedisplay;
                                        s_datedefault = dateinfile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }

                                }

                                // GENERATES the detail of the report
                                s_key="";
                                if(agregateId.equalsIgnoreCase("1")) {
                                    s_key = line.substring(0,4);
                                }else if(agregateId.equalsIgnoreCase("2")) {
                                    s_key = line.substring(0,7);
                                }else if(agregateId.equalsIgnoreCase("3")) {
                                    s_key = line.substring(0,10);
                                }else if(agregateId.equalsIgnoreCase("4")) {
                                    i_hourini = Integer.parseInt(s_datedefault.substring(11,13));
                                    i_hourfin = i_hourini + 1 ;
                                    if(i_hourfin == I_TWENTYFOUR) {
                                        i_hourfin = 0;
                                        s_hourfin = Integer.toString(i_hourfin) + "0:00";
                                    }else {
                                        s_hourfin = Integer.toString(i_hourfin);
                                        if(s_hourfin.length() == I_ONE) {
                                            s_hourfin = "0" + s_hourfin;
                                        }
                                        s_hourfin = s_hourfin + ":00";
                                    }
                                    s_key = line.substring(0,13) + ":00-"+s_hourfin;
                                }
                                vec_rep = (Vector) hm_detail.get(s_key);
                                if(null == vec_rep) {
                                    vec_rep=new Vector();
                                }
                                vec_rep.add(line);
                                hm_detail.put(s_key,vec_rep);
                            }
                        }// line in file

                        if((i_new == I_ONE) | (l_count != I_ZERO)) {
                            arr_data = new String[2];
                            if(agregateId.equalsIgnoreCase("1")){
                                arr_data[0] = s_datedefault.substring(0,4);
                            }else if(agregateId.equalsIgnoreCase("2")) {
                                arr_data[0] = s_datedefault.substring(0,7);
                            }else if(agregateId.equalsIgnoreCase("3")) {
                                arr_data[0] = s_datedefault.substring(0,10);
                            }else if(agregateId.equalsIgnoreCase("4")) {
                                i_hourini = Integer.parseInt(s_datedefault.substring(11,13));
                                i_hourfin = i_hourini + 1 ;
                                if(i_hourfin == I_TWENTYFOUR) {
                                    i_hourfin = 0;
                                    s_hourfin = Integer.toString(i_hourfin) + "0:00";
                                }else {
                                    s_hourfin = Integer.toString(i_hourfin);
                                    if(s_hourfin.length() == I_ONE) {
                                        s_hourfin = "0" + s_hourfin;
                                    }
                                    s_hourfin = s_hourfin + ":00";
                                }                                
                                arr_data[0] = s_datedefault.substring(0,13) + ":00-" + s_hourfin;
                            }
                            arr_data[1] = Long.toString(l_count);
                            al_pag.add(arr_data);
                        }
                        //ends read of file
                        rf_in.close();
                    }else {
                        log.error("File " + filename + " not found on method getReportResults() resource " + strRscType + " with id " +  getResourceBase().getId());
                    }
                }
            }

            //If it should display by year or month then filter data here
            if(agregateId.equalsIgnoreCase("1") || agregateId.equalsIgnoreCase("2")) {
                i_start = 0;
                s_year = null;
                ArrayList al_aux = new ArrayList();
                for(int h=0; h < al_pag.size(); h++) {
                    String[] arr_dataaux = (String[])al_pag.get(h);
                    if(i_start == 0) {
                        s_year = arr_dataaux[0];
                        l_count = Integer.parseInt(arr_dataaux[1]);
                    }else {
                        if(arr_dataaux[0].equalsIgnoreCase(s_year)) {
                            l_count = l_count + Integer.parseInt(arr_dataaux[1]);
                        }else {
                            arr_data = new String[2];
                            arr_data[0] = s_year;
                            arr_data[1] = Long.toString(l_count);
                            al_aux.add(arr_data);
                            s_year = arr_dataaux[0];
                            l_count = Integer.parseInt(arr_dataaux[1]);
                        }
                    }
                    i_start++;
                    if(i_start == al_pag.size()) {
                        arr_data = new String[2];
                        arr_data[0] = s_year;
                        arr_data[1] = Long.toString(l_count);
                        al_aux.add(arr_data);
                    }
                }
                al_pag = al_aux;
            }
        }catch (Exception e) {
            log.error("Error on method getReportResults() resource " + strRscType + " with id " +  getResourceBase().getId(), e);
        }
        return al_pag.iterator();
    }
}