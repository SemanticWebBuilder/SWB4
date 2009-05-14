package org.semanticwb.portal.admin.resources.reports;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

public class WBAAccessLogReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBAGlobalReport.class);

    private final String I_REPORT_IDAUX = "_"; // Type 0 of reports "Infotec WebBuilder report support page"
    public String strRscType;
    private HashMap hm_detail = null;

    @Override
    public void init(){
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBAAccessLogReport";
        }
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState())) {
            processRequest(request, response, paramsRequest);
        }
    }

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
        }else if(mode.equalsIgnoreCase("report_excel")) {
            doRepExcel(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("report_xml")) {
            doRepXml(request, response, paramsRequest);
        }else if(mode.equalsIgnoreCase("graph")) {
            doGraph(request, response, paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    public void doRenderDeviceSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");

        StringBuilder ret = new StringBuilder();
        String space="";

        WebSite webSite = SWBContext.getWebSite(request.getParameter("site"));

        ArrayList<Device> devs=new ArrayList<Device>();
        Iterator<Device> itDevices = webSite.listDevices();
        while (itDevices.hasNext()) {
            devs.add(itDevices.next());
        }
        devs.trimToSize();

        ret.append("<select name=\"wb_devid\" id=\"wb_devid\" size=\"6\" >\n");
        while(!devs.isEmpty()) {
            Device device = devs.get(0);
            ret.append("<option value=\""+device.getId()+"\"");
            ret.append(">"+space+device.getTitle()+"</option>\n");
            devs.remove(0);
            if(device.listChilds().hasNext()) {
                renderDeviceSelect(devs, device, ret, space+"&nbsp;&nbsp;&nbsp;");
            }
        }
        ret.append("</select>\n");
        request.getSession(true).removeAttribute("devs");

        PrintWriter out = response.getWriter();
        out.print(ret.toString());
        out.flush();
        out.close();
    }

    private void renderDeviceSelect(ArrayList origList, Device node, StringBuilder ret, String space) {
        ArrayList<Device> devs=new ArrayList<Device>();
        Iterator<Device> itDevices = node.listChilds();
        while(itDevices.hasNext()) {
            devs.add(itDevices.next());
        }
        devs.trimToSize();

        while(!devs.isEmpty()) {
            Device device = devs.get(0);
            ret.append("<option value=\""+device.getId()+"\"");
            ret.append(">"+space+device.getTitle()+"</option>\n");
            origList.remove(device);
            devs.remove(0);
            if(device.listChilds().hasNext()) {
                renderDeviceSelect(origList, device, ret, space+"&nbsp;&nbsp;&nbsp;");
            }
        }
    }

    public void doRenderLanguageSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");

        PrintWriter out = response.getWriter();

        String webSiteId = request.getParameter("site");
        WebSite webSite = SWBContext.getWebSite(webSiteId);

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

    public void doRenderUserTypeSelect(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");

        PrintWriter out = response.getWriter();

        out.println("<select name=\"wb_usertype\" id=\"wb_usertype\" size=\"1\">");
        out.println("<option value=\"0\"></option>");
        out.println("</select>");
        out.flush();
        out.close();
    }

    public void doFillReportAgregate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        /*response.setContentType("text/html;charset=iso-8859-1");
        Iterator<String[]> records = getReportResults(request, paramsRequest);
        StringBuilder ret = new StringBuilder();
        ret.append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"95%\">\n");
        if(!records.hasNext()) {
            ret.append("<caption align=\"top\">" + paramsRequest.getLocaleString("no_records_found") + "</caption>\n");
        }else {
            ret.append("<caption align=\"top\">" + paramsRequest.getLocaleString("access_log_report") + "</caption>\n");
            ret.append("<tr><th width=\"35%\">"+paramsRequest.getLocaleString("th_Date")+"</th><th width=\"35%\">"+paramsRequest.getLocaleString("th_Total")+"</th><th width=\"25%\">"+paramsRequest.getLocaleString("th_Action")+"</th></tr>\n");

            boolean toggleColor = true;
            while(records.hasNext()) {
                String[] cols = records.next();
                ret.append("<tr bgcolor=\""+(toggleColor?"#EFEDEC":"#FFFFFF")+"\">\n");
                ret.append("<td>"+cols[0]+"</td>\n");
                ret.append("<td>"+cols[1]+"</td>\n");
                ret.append("<td>\n");
                ret.append("<a onClick=\"doDetail('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar','"+cols[0]+"')\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/SEARCH.png\" border=\"0\" alt=\""+SWBPlatform.getContextPath()+"\" /></a>\n");
                ret.append("&nbsp;\n");
                ret.append("<a onClick=\"doDetailExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar','"+cols[0] +"')\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/SEARCH.png\" border=\"0\" alt=\""+SWBPlatform.getContextPath()+"\" /></a>\n");
                ret.append("</td>\n");
                ret.append("</tr>\n");
            }
        }
        PrintWriter out = response.getWriter();
        out.print(ret.toString());
        out.flush();
        out.close();*/

        response.setContentType("text/json;charset=iso-8859-1");
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            //jobj.put("identifier", "uri");
            jobj.put("label", "agregate");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
        }

        Iterator<String[]> records = getReportResults(request, paramsRequest);

        while(records.hasNext()) {
            String[] cols = records.next();
            System.out.println(Arrays.toString(cols));
            JSONObject obj = new JSONObject();
            try {
                obj.put("detail", "<a onClick=\"doDetail('width=850, height=550, scrollbars, resizable, alwaysRaised, menubar','"+cols[0]+"')\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/SEARCH.png\" border=\"0\" alt=\"detail\"></a>&nbsp;");
                obj.put("ssheet", "<a onClick=\"doDetail('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar','"+cols[0]+"')\"><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/SEARCH.png\" border=\"0\" alt=\"detail\"></a>&nbsp;");
                obj.put("date", cols[0]);
                obj.put("agregate", cols[1]);
                jarr.put(obj);
            }catch (JSONException jsone) {
            }
        }
        response.getOutputStream().println(jobj.toString());
    }

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
        String s_key = request.getParameter("key");

        if(null!=hm_detail) {
            if(null!=s_key) {
                System.out.println("doFillReportDetalled... s_key="+s_key);

                Vector vec_rep = (Vector) hm_detail.get(s_key);
                if( null!=vec_rep && !vec_rep.isEmpty() ) {
                    Iterator<String> ite_rep = vec_rep.iterator();
                    int i=1;
                    while(ite_rep.hasNext()) {
                        StringTokenizer s_token = new StringTokenizer(ite_rep.next(),"|");
                        String s_date = s_token.nextToken();
                        String s_ipuser = s_token.nextToken();
                        String s_ipserver = s_token.nextToken();
                        String s_session = s_token.nextToken();
                        String s_tm = s_token.nextToken();
                        String s_tp = s_token.nextToken();
                        String s_repo = s_token.nextToken();
                        String s_iduser = s_token.nextToken();
                        String s_usrtype = s_token.nextToken();
                        String s_device = s_token.nextToken();
                        String s_language = s_token.nextToken();
                        int time = Integer.parseInt(s_token.nextToken(),10);
                        String s_rec = "";

                        while(s_token.hasMoreTokens())
                        {
                            s_rec += s_token.nextToken();
                            if(s_token.hasMoreTokens())
                                s_rec += ", ";
                        }

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("serial", i);
                            obj.put("date", s_date);
                            obj.put("ipuser", s_ipuser);
                            obj.put("ipserver", s_ipserver);
                            obj.put("sessid", s_session);
                            obj.put("siteid", s_tm);
                            obj.put("sectid", s_tp);
                            obj.put("rep", s_repo);
                            obj.put("user", s_iduser);
                            obj.put("usertype", s_usrtype);
                            obj.put("devid", s_device);
                            obj.put("langid", s_language);
                            obj.put("time", time);
                            obj.put("resid", s_rec);
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
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        final int I_ACCESS = 0;
        Resource base = paramsRequest.getResourceBase();
        StringBuilder sb_ret = new StringBuilder();

        HashMap hm_sites = new HashMap();

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
                String address = paramsRequest.getTopic().getUrl();
                String s_site = paramsRequest.getTopic().getWebSite().getId();
                String repositoryName = SWBContext.getWebSite(s_site).getUserRepository().getDisplayTitle(paramsRequest.getUser().getLanguage());

                GregorianCalendar now = new GregorianCalendar();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);

                // javascript
                sb_ret.append("<script type=\"text/javascript\">\n");

                sb_ret.append("dojo.require(\"dojox.grid.DataGrid\");\n");
                sb_ret.append("dojo.require(\"dojo.data.ItemFileReadStore\");\n");
                sb_ret.append("dojo.require(\"dijit.form.TimeTextBox\");\n");
                
                sb_ret.append("dojo.addOnLoad(refresh);\n");

                sb_ret.append("function refresh() {\n");
                sb_ret.append("    postHtml('"+url+"'+'/_mod/fillDevSel'+'?site='+dojo.byId('wb_site').options[dojo.byId('wb_site').selectedIndex].value,'dev_cntr');\n");
                sb_ret.append("    postHtml('"+url+"'+'/_mod/fillLangSel'+'?site='+dojo.byId('wb_site').options[dojo.byId('wb_site').selectedIndex].value,'lang_cntr');\n");
                sb_ret.append("    postHtml('"+url+"'+'/_mod/fillUTSel'+'?site='+dojo.byId('wb_site').options[dojo.byId('wb_site').selectedIndex].value,'ut_cntr');\n");
                sb_ret.append("}\n");

                sb_ret.append("dojo.addOnLoad(function(){\n");
                sb_ret.append("   var t = new Date();\n");
                sb_ret.append("   var t1 = new dijit.form.TimeTextBox({name:\"t1\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 0, 0),\n");
                sb_ret.append("                constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}\n");
                sb_ret.append("   }, \"wb_t11\");\n");

                sb_ret.append("   var t2 = new dijit.form.TimeTextBox({name:\"t2\", value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 23, 59),\n");
                sb_ret.append("                constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}\n");
                sb_ret.append("   }, \"wb_t12\");\n");
                sb_ret.append("});\n");


                sb_ret.append("function fillGrid(grid, uri, mode, params) {\n");
                sb_ret.append("   grid.store = new dojo.data.ItemFileReadStore({url: uri+'/_mod/'+mode+'?'+params});\n");
                sb_ret.append("   grid._refresh();\n");
                sb_ret.append("}\n");

                sb_ret.append("var layout= null;\n");
                sb_ret.append("var jStrMaster = null;\n");
                sb_ret.append("var gridMaster = null;\n");
                sb_ret.append("var gridResources = null;\n");
                
                sb_ret.append("dojo.addOnLoad(function() {\n");
                sb_ret.append("   layout= [\n");
                sb_ret.append("      { field:\"detail\", width:\"5%\", name:\"Ver Detalle\" },\n");
                sb_ret.append("      { field:\"ssheet\", width:\"5%\", name:\"MS Excel\" },\n");
                sb_ret.append("      { field:\"date\", width:\"33%\", name:\"Fecha\" },\n");
                sb_ret.append("      { field:\"agregate\", width:\"33%\", name:\"Total Agregado\" },\n");
                sb_ret.append("   ];\n");

                sb_ret.append("   gridMaster = new dojox.grid.DataGrid({\n");
                sb_ret.append("      id: \"gridMaster\",\n");
                sb_ret.append("      structure: layout,\n");
                sb_ret.append("      rowSelector: \"10px\",\n");
                sb_ret.append("      rowsPerPage: \"15\"\n");
                sb_ret.append("   }, \"gridMaster\");\n");
                sb_ret.append("   gridMaster.startup();\n");
                sb_ret.append("});\n");

                sb_ret.append("function doApply() {\n");                
                sb_ret.append("   var params = 'siteid='+dojo.byId('wb_site').value;\n");
                sb_ret.append("   params += '&fecha11='+dojo.byId('wb_fecha11').value;\n");
                sb_ret.append("   params += '&t11='+dojo.byId('wb_t11').value;\n");
                sb_ret.append("   params += '&fecha12='+dojo.byId('wb_fecha12').value;\n");
                sb_ret.append("   params += '&t12='+dojo.byId('wb_t12').value;\n");
                sb_ret.append("   if(dojo.byId('wb_ipuser').value) {\n");
                sb_ret.append("      params += '&ipuser='+dojo.byId('wb_ipuser').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_ipserver').value) {\n");
                sb_ret.append("      params += '&ipserver='+dojo.byId('wb_ipserver').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_sectid').value) {\n");
                sb_ret.append("      params += '&sectid='+dojo.byId('wb_sectid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_userid').value) {\n");
                sb_ret.append("      params += '&userid='+dojo.byId('wb_userid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_usertype').value) {\n");
                sb_ret.append("      params += '&usertype='+dojo.byId('wb_usertype').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_devid').value) {\n");
                sb_ret.append("      params += '&devid='+dojo.byId('wb_devid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_langid').value) {\n");
                sb_ret.append("      params += '&langid='+dojo.byId('wb_langid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_resid').value) {\n");
                sb_ret.append("      params += '&resid='+dojo.byId('wb_resid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_sessid').value) {\n");
                sb_ret.append("      params += '&sessid='+dojo.byId('wb_sessid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   params += '&agregate='+getAgregate();\n");
                sb_ret.append("   var grid = dijit.byId('gridMaster');\n");
                sb_ret.append("   fillGrid(grid, '"+url+"', 'fillGridAgrd', params);\n");
                //sb_ret.append("   postHtml('"+url+"'+'/_mod/fillGridAgrd'+'?'+params,'gridMaster');");
                sb_ret.append("}\n");
                
                sb_ret.append("function getAgregate() {\n");
                sb_ret.append("   var agrd = \"2\";\n");
                sb_ret.append("   for(i=0; i<window.document.frmrep.wb_agregate.length; i++) {\n");
                sb_ret.append("      if(window.document.frmrep.wb_agregate[i].checked==true) {\n");
                sb_ret.append("         strType = window.document.frmrep.wb_agregate[i].value;\n");
                sb_ret.append("      }\n");
                sb_ret.append("   }\n");
                sb_ret.append("   return strType;\n");
                sb_ret.append("}\n");

                sb_ret.append("function doDetail(size, key) { \n");
                sb_ret.append("   var params = '?';\n");
                sb_ret.append("   params += 'siteid='+dojo.byId('wb_site').value;\n");
                sb_ret.append("   params += '&fecha11='+dojo.byId('wb_fecha11').value;\n");
                sb_ret.append("   params += '&t11='+dojo.byId('wb_t11').value;\n");
                sb_ret.append("   params += '&fecha12='+dojo.byId('wb_fecha12').value;\n");
                sb_ret.append("   params += '&t12='+dojo.byId('wb_t12').value;\n");
                sb_ret.append("   if(dojo.byId('wb_ipuser').value) {\n");
                sb_ret.append("      params += '&ipuser='+dojo.byId('wb_ipuser').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_ipserver').value) {\n");
                sb_ret.append("      params += '&ipserver='+dojo.byId('wb_ipserver').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_sectid').value) {\n");
                sb_ret.append("      params += '&sectid='+dojo.byId('wb_sectid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_userid').value) {\n");
                sb_ret.append("      params += '&userid='+dojo.byId('wb_userid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_usertype').value) {\n");
                sb_ret.append("      params += '&usertype='+dojo.byId('wb_usertype').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_devid').value) {\n");
                sb_ret.append("      params += '&devid='+dojo.byId('wb_devid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_langid').value) {\n");
                sb_ret.append("      params += '&langid='+dojo.byId('wb_langid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_resid').value) {\n");
                sb_ret.append("      params += '&resid='+dojo.byId('wb_resid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   if(dojo.byId('wb_sessid').value) {\n");
                sb_ret.append("      params += '&sessid='+dojo.byId('wb_sessid').value;\n");
                sb_ret.append("   }\n");
                sb_ret.append("   params += '&agregate='+getAgregate();\n");
                sb_ret.append("   params += '&key='+key;\n");
                sb_ret.append("   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_detail")+"\"+params,\"detailWindow\",size);\n");
                sb_ret.append("}\n");

                sb_ret.append("function doExcel(size, key){    ");
                sb_ret.append("}");

                sb_ret.append("</script>\n");

                sb_ret.append("<div class=\"swbform\">\n");
                sb_ret.append("<fieldset>");
                sb_ret.append(paramsRequest.getLocaleString("description_1"));
                sb_ret.append("</fieldset>\n");

                sb_ret.append("<form method=\"Post\" class=\"box\" action=\"" + address + "\" id=\"frmrep\" name=\"frmrep\">\n");
                sb_ret.append("<fieldset>\n");
                sb_ret.append("<table border=\"0\" width=\"95%\" align=\"center\">\n");
                sb_ret.append("<tr><td width=\"200\"></td><td width=\"200\"></td><td width=\"200\"></td><td width=\"200\"></td></tr>\n");

                sb_ret.append("<tr>\n");
                sb_ret.append("<td><label for=\"wb_site\">" + paramsRequest.getLocaleString("site") + ":</label></td>\n");
                sb_ret.append("<td>\n");
                sb_ret.append("<select name=\"wb_site\" id=\"wb_site\" onChange=\"refresh();\" >\n");
                Iterator<String> itKeys = hm_sites.keySet().iterator();
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    sb_ret.append("<option value=\""+key+"\">"+(String)hm_sites.get(key)+"</option>\n");
                }
                sb_ret.append("</select>\n");
                sb_ret.append("</td>\n");
                sb_ret.append("<td>&nbsp;</td>\n");
                sb_ret.append("<td>&nbsp;</td>\n");
                sb_ret.append("</tr>\n");
                
                sb_ret.append("<tr>\n");
                sb_ret.append("<td>"+paramsRequest.getLocaleString("by_interval_date")+":&nbsp;</td>\n");
                sb_ret.append("<td>&nbsp;</td>\n");
                sb_ret.append("<td>&nbsp;</td>\n");
                sb_ret.append("<td>&nbsp;</td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                sb_ret.append("<td>&nbsp;</td>\n");
                sb_ret.append("<td align=\"left\" colspan=\"2\">\n");
                sb_ret.append("<label for=\"wb_fecha11\">Del:&nbsp;</label>\n");
                sb_ret.append("<input type=\"text\" name=\"wb_fecha11\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha11\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />\n");
                /*sb_ret.append("</td>\n");
                sb_ret.append("<td>\n");*/
                sb_ret.append("&nbsp;&nbsp;");
                sb_ret.append("<label for=\"wb_t11\">Tiempo:&nbsp;</label>\n");
                sb_ret.append("<input type=\"text\" name=\"wb_t11\" id=\"wb_t11\" size=\"6\" style=\"width:40px;\" />\n");
                sb_ret.append("</td>\n");
                sb_ret.append("<td></td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                sb_ret.append("<td>&nbsp;</td>\n");
                sb_ret.append("<td align=\"left\" colspan=\"2\">\n");
                sb_ret.append("<label for=\"wb_fecha12\">&nbsp;&nbsp;Al:&nbsp;</label>\n");
                sb_ret.append("<input type=\"text\" name=\"wb_fecha12\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha12\" value=\""+sdf.format(now.getTime())+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" hasDownArrow=\"true\" />\n");
                /*sb_ret.append("</td>\n");
                sb_ret.append("<td>\n");*/
                sb_ret.append("&nbsp;&nbsp;");
                sb_ret.append("<label for=\"wb_t12\">Tiempo:&nbsp;</label>\n");
                sb_ret.append("<input name=\"wb_t12\" id=\"wb_t12\" />\n");
                sb_ret.append("</td>\n");
                sb_ret.append("<td></td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                sb_ret.append("<td><label for=\"wb_ipuser\">"+paramsRequest.getLocaleString("ipaddress_user")+":&nbsp;</label></td>\n");
                sb_ret.append("<td><input type=\"text\" name=\"wb_ipuser\" id=\"wb_ipuser\" size=\"20\" /></td>\n");
                sb_ret.append("<td><label for=\"wb_ipserver\">"+paramsRequest.getLocaleString("ipaddress_server")+":&nbsp;</label></td>\n");
                sb_ret.append("<td><input type=\"text\" name=\"wb_ipserver\" id=\"wb_ipserver\" size=\"20\" /></td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                sb_ret.append("<td><label for=\"wb_sectid\">"+paramsRequest.getLocaleString("sectionid")+":&nbsp;</label></td>\n");
                sb_ret.append("<td>\n");
                sb_ret.append("<input type=\"text\" name=\"wb_sectid\" id=\"wb_sectid\" size=\"20\" />");
                sb_ret.append("</td>\n");
                sb_ret.append("<td><label for=\"wb_subsect\">"+paramsRequest.getLocaleString("subsections")+":&nbsp;</label></td>\n");
                sb_ret.append("<td>\n");
                sb_ret.append("<input type=\"checkbox\" name=\"wb_subsect\" id=\"wb_subsect\" value=\"true\" />\n");
                sb_ret.append("</td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                sb_ret.append("<td><label for=\"wb_userid\">"+paramsRequest.getLocaleString("user")+":&nbsp;</label></td>\n");
                sb_ret.append("<td><input type=\"text\" name=\"wb_userid\" id=\"wb_userid\" size=\"20\" /></td>\n");
                sb_ret.append("<td><label for=\"wb_usertype\">"+paramsRequest.getLocaleString("user_type")+":&nbsp;</td>\n");
                sb_ret.append("<td>\n");
                sb_ret.append("<div id=\"ut_cntr\"></div>\n");
                sb_ret.append("</td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                // DEVICES
                sb_ret.append("<td><label for=\"wb_devid\">"+paramsRequest.getLocaleString("device")+":&nbsp;</label></td>\n");
                sb_ret.append("<td>\n");
                sb_ret.append("<div id=\"dev_cntr\"></div>\n");
                sb_ret.append("</td>\n");
                // LANGUAGES
                sb_ret.append("<td><label for=\"wb_langid\">"+paramsRequest.getLocaleString("language")+":&nbsp;</label></td>\n");
                sb_ret.append("<td>\n");
                sb_ret.append("<div id=\"lang_cntr\"></div>\n");
                sb_ret.append("</td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                // RESOURCE
                sb_ret.append("<td><label for=\"wb_resid\">"+paramsRequest.getLocaleString("id_resource")+":&nbsp;</label></td>\n");
                sb_ret.append("<td><input type=\"text\" name=\"wb_resid\" id=\"wb_resid\" size=\"20\" maxlength=\"4\" /></td>\n");
                // SESSION
                sb_ret.append("<td><label for=\"wb_sessid\">"+paramsRequest.getLocaleString("session")+":&nbsp;</label></td>\n");
                sb_ret.append("<td><input type=\"text\" name=\"wb_sessid\" id=\"wb_sessid\" size=\"20\" /></td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                // USER REPOSITORY
                sb_ret.append("<td><label for=\"wb_urepid\">"+paramsRequest.getLocaleString("repository")+":&nbsp;</label></td>\n");
                sb_ret.append("<td>\n");
                sb_ret.append("<input type=\"text\" name=\"wb_urepid\" id=\"wb_urepid\" value=\""+repositoryName+"\" readonly=\"readonly\" />\n");
                sb_ret.append("</td>\n");
                sb_ret.append("<td></td>\n");
                sb_ret.append("<td></td>\n");
                sb_ret.append("</tr>\n");

                sb_ret.append("<tr>\n");
                sb_ret.append("<td height=\"25px\">"+paramsRequest.getLocaleString("agregate_by")+":&nbsp;</td>\n");
                sb_ret.append("<td colspan=\"3\" height=\"25px\">\n");
                sb_ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"1\" />"+paramsRequest.getLocaleString("by_year")+"</label>&nbsp;&nbsp;\n");
                sb_ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"2\" checked=\"checked\" />"+paramsRequest.getLocaleString("by_month")+"</label>&nbsp;&nbsp;\n");
                sb_ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"3\" />"+paramsRequest.getLocaleString("by_day")+"</label>&nbsp;&nbsp;\n");
                sb_ret.append("<label><input type=\"radio\" name=\"wb_agregate\" value=\"4\" />"+paramsRequest.getLocaleString("by_hour")+"</label>\n");
                sb_ret.append("</td>\n");
                sb_ret.append("</tr>\n");
                sb_ret.append("</table>\n");
                sb_ret.append("</fieldset>\n");

                sb_ret.append("<fieldset>\n");
                sb_ret.append("<table border=\"0\" width=\"95%\">\n");
                sb_ret.append(" <tr>\n");
                sb_ret.append("     <td colspan=\"4\">&nbsp;&nbsp;&nbsp;\n");
                sb_ret.append("     <input type=\"button\" onClick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\" />&nbsp;\n");
                sb_ret.append("     <input type=\"button\" onClick=\"doExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\" name=\"btnExcel\" />&nbsp;\n");
                sb_ret.append("     <input type=\"button\" onClick=\"doGraph('width=600, height=550, scrollbars, resizable')\" value=\"" + paramsRequest.getLocaleString("graph") + "\" name=\"btnGraph\" />&nbsp;\n");
                sb_ret.append("     <input type=\"button\" onClick=\"doApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\" />\n");
                sb_ret.append("     </td>\n");
                sb_ret.append(" </tr>\n");
                sb_ret.append("</table>\n");
                sb_ret.append("</fieldset>\n");
                sb_ret.append("</form>\n");

                sb_ret.append("<fieldset>\n");
                sb_ret.append("<table border=\"0\" width=\"95%\" align=\"center\">\n");
                sb_ret.append("<tr>\n");
                sb_ret.append("<td colspan=\"4\">\n");
                sb_ret.append("<div id=\"ctnergrid\" style=\"height:250px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">\n");
                sb_ret.append("  <div id=\"gridMaster\"></div>\n");
                sb_ret.append("</div>\n");
                sb_ret.append("</td>\n");
                sb_ret.append("</tr>\n");
                sb_ret.append("</table>\n");
                sb_ret.append("</fieldset>\n");

                sb_ret.append("</div>\n");


                if(request.getParameter("wb_rep_type") == null)
                {
                    sb_ret.append("&nbsp;");
                }
                else
                {
                    /*ArrayList ar_pag = getReportResults(request, paramsRequest);
                    int i_page = I_INIT_PAGE;
                    String s_page = request.getParameter("wb_pagenum");
                    if(s_page != null)
                    {
                        i_page = Integer.parseInt(s_page);
                    }

                    int i_totpage = I_INIT_PAGE + 1;
                    int i_size = ar_pag.size();
                    if(i_size > I_PAGE_SIZE)
                    {
                        i_totpage = i_size / I_PAGE_SIZE;
                        int i_ret = i_size % I_PAGE_SIZE;
                        if(i_ret > 0)
                        {
                            i_totpage = i_totpage + 1;
                        }
                    }

                    int i_inipage =  I_PAGE_SIZE * i_page;
                    int i_finpage = i_size;
                    if((i_inipage + I_PAGE_SIZE) < i_finpage)
                    {
                        i_finpage = i_inipage + I_PAGE_SIZE;
                    }

                    if(i_size > 0)
                    {
                        sb_ret.append("\n<input type=\"hidden\" name=\"wb_pagenum\" value=\"" + I_INIT_PAGE + "\">");
                        sb_ret.append("\n<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">");
                        sb_ret.append("\n<tr><td colspan=\"3\">&nbsp;</td></tr>");
                        sb_ret.append("\n<tr>");
                        sb_ret.append("\n<td align=\"left\" class=\"datos\">" + paramsRequest.getLocaleString("page") + " " + (i_page + 1)+ " " + paramsRequest.getLocaleString("of") + " " + i_totpage + "</td>");
                        sb_ret.append("\n<td colspan=\"2\" align=\"right\">&nbsp;");
                        if(i_page > 0)
                        {
                            sb_ret.append("<a href=\"javascript:DoPaging(" + (i_page - 1) + ");\" class=\"link\">" + paramsRequest.getLocaleString("previous") + "</a>&nbsp;");
                        }
                        if(i_totpage > (i_page + 1))
                        {
                            sb_ret.append("<a href=\"javascript:DoPaging(" + (i_page + 1) +");\" class=\"link\">" + paramsRequest.getLocaleString("next") + "</a>");
                        }
                        sb_ret.append("\n</td>");
                        sb_ret.append("\n</tr>");
                        sb_ret.append("\n<tr class=\"tabla\">");
                        sb_ret.append("<td>" + paramsRequest.getLocaleString("date") + "</td>");
                        sb_ret.append("<td>" + paramsRequest.getLocaleString("access") + "</td>");
                        sb_ret.append("<td>" + paramsRequest.getLocaleString("action") + "</td>");
                        sb_ret.append("\n</tr>");

                        for(int j=i_inipage;j<i_finpage;j++)
                        {
                            if((j % 2) == 0)
                            {
                                s_color ="#EFEDEC";
                            }
                            else
                            {
                                s_color ="#FFFFFF";
                            }
                            String[] arr_data = (String[])ar_pag.get(j);
                            sb_ret.append("\n<tr bgcolor=\"" + s_color + "\">");
                            sb_ret.append("<td class=\"valores\">" + arr_data[0] + "</td>");
                            sb_ret.append("<td class=\"valores\">" + arr_data[1] + "</td>");
                            sb_ret.append("<td class=\"valores\">");
                            sb_ret.append("<a onClick=\"DoDetail('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar','"+arr_data[0] +"')\">");
                            sb_ret.append("<img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/SEARCH.png\" border=\"0\" alt=\"detail\">");
                            sb_ret.append("</a>&nbsp;");
                            sb_ret.append("<a onClick=\"DoDetailExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar','"+arr_data[0] +"')\">");
                            sb_ret.append("<img src=\""+SWBPlatform.getContextPath()+"/swbadmin/icons/PFlow.png\" border=\"0\" alt=\"detail in excel format\">");
                            sb_ret.append("</a>");
                            sb_ret.append("</td>");
                            sb_ret.append("\n</tr>");
                        }
                        sb_ret.append("\n</table>");
                        sb_ret.append("<hr size=\"1\" noshade>");
                    }
                    else
                    {           // There are not records
                        sb_ret.append("\n<center><font class=\"datos\">" + paramsRequest.getLocaleString("no_records_found") + "</font></center>");
                    }*/
                }
                /*sb_ret.append("\n</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n</table></form>");*/
            }
            else
            {   // There are not sites and displays a message
                sb_ret.append("\n<form method=\"Post\" class=\"box\" action=\"" + paramsRequest.getTopic().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
                sb_ret.append("\n<table border=0 width=\"100%\">");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n<td colspan=\"2\" align=\"center\" class=\"datos\">" + paramsRequest.getLocaleString("no_sites_found") + "</td>");
                sb_ret.append("\n<td>&nbsp;</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n<tr><td colspan=\"4\">&nbsp;</td></tr>");
                sb_ret.append("\n</table></form>");
            }
        }catch (Exception e) {
            log.error("Error on method DoView() resource " + strRscType +  " with id " +  base.getId(), e);
        }
        response.getWriter().print(sb_ret.toString());
    }

    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();

        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setCallMethod(paramsRequest.Call_DIRECT).setMode("fillGridDtd");

        /*url.setParameter("siteid", request.getParameter("siteid"));
        url.setParameter("fecha11", request.getParameter("fecha11"));
        url.setParameter("t11", request.getParameter("t11"));
        url.setParameter("fecha12", request.getParameter("fecha12"));
        url.setParameter("t12", request.getParameter("t12"));
        url.setParameter("ipuser", request.getParameter("ipuser"));
        url.setParameter("ipserver", request.getParameter("ipserver"));
        url.setParameter("sectid", request.getParameter("sectid"));
        url.setParameter("userid", request.getParameter("userid"));
        url.setParameter("usertype", request.getParameter("usertype"));
        url.setParameter("devid", request.getParameter("devid"));
        url.setParameter("langid", request.getParameter("langid"));
        url.setParameter("resid", request.getParameter("resid"));
        url.setParameter("sessid", request.getParameter("sessid"));
        url.setParameter("agregate", request.getParameter("agregate"));*/
        url.setParameter("key", request.getParameter("key"));

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
        out.println("       margin: 5px;");
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
        out.println("            <th width=\"9%\" field=\"date\">"+paramsRequest.getLocaleString("th_Date")+"</th>");
        out.println("            <th width=\"7%\" field=\"ipuser\">"+paramsRequest.getLocaleString("th_IPuser")+"</th>");
        out.println("            <th width=\"7%\" field=\"ipserver\">"+paramsRequest.getLocaleString("th_IPserver")+"</th>");
        out.println("            <th width=\"7%\" field=\"sessid\">"+paramsRequest.getLocaleString("th_IDsession")+"</th>");
        out.println("            <th width=\"7%\" field=\"siteid\">"+paramsRequest.getLocaleString("th_TopicMap")+"</th>");
        out.println("            <th width=\"7%\" field=\"sectid\">"+paramsRequest.getLocaleString("th_Topic")+"</th>");
        out.println("            <th width=\"7%\" field=\"rep\">"+paramsRequest.getLocaleString("th_Repository")+"</th>");
        out.println("            <th width=\"7%\" field=\"user\">"+paramsRequest.getLocaleString("th_User")+"</th>");
        out.println("            <th width=\"7%\" field=\"usertype\">"+paramsRequest.getLocaleString("th_UserType")+"</th>");
        out.println("            <th width=\"7%\" field=\"devid\">"+paramsRequest.getLocaleString("th_Device")+"</th>");
        out.println("            <th width=\"7%\" field=\"langid\">"+paramsRequest.getLocaleString("th_Language")+"</th>");
        out.println("            <th width=\"5%\" field=\"time\">"+paramsRequest.getLocaleString("th_Time")+"</th>");
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
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        StringBuffer sb_ret = new StringBuffer();
        StringBuffer sb_app = new StringBuffer();
        Resource base = paramsRequest.getResourceBase();
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document dom = null;
        Iterator<String[]> ar_pag = null;
        String s_title = null;
        int i_len = 0;
        boolean b_record = false;

        try
        {
            sb_ret.append("\n<html>");
            sb_ret.append("\n<head>");
            sb_ret.append("\n<title>" + paramsRequest.getLocaleString("access_log_report") + "</title>");
            sb_ret.append("\n</head>");
            //<LINK href="/work/WBAdmin/templates/3/1/images/wb3.css" rel="stylesheet" type="text/css" >
            sb_ret.append("\n<LINK href=\""+SWBPlatform.getContextPath()+"work/WBAdmin/templates/3/1/images/wb3.css\" rel=\"stylesheet\" type=\"text/css\" >");
            sb_ret.append("\n<script type=\"text/javascript\">");
            sb_ret.append("\n   function DoPrint(){");
            sb_ret.append("\n       window.print();");
            sb_ret.append("\n   }");
            sb_ret.append("\n   function DoClose(){");
            sb_ret.append("\n       window.close();");
            sb_ret.append("\n   }");
            sb_ret.append("\n</script>");
            sb_ret.append("\n<body>");
            sb_ret.append("\n<table border=\"0\" width=\"550\">");
            sb_ret.append("\n<tr>");
            sb_ret.append("\n<td colpsan=\"3\" align=\"left\"><img src=\""+SWBPlatform.getContextPath()+"wbadmin/images/WB.gif\" width=\"362\" height=\"31\">");
            sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoPrint()\" value=\"" + paramsRequest.getLocaleString("print") + "\" name=\"btnPrint\">");
            sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoClose()\" value=\"" +paramsRequest.getLocaleString("close") +"\" name=\"btnClose\">");
            sb_ret.append("</td>");
            sb_ret.append("\n</tr>");
            sb_ret.append("\n<tr>");
            sb_ret.append("\n<td colpsan=\"3\">&nbsp;</td>");
            sb_ret.append("\n</tr>");
            sb_ret.append("\n<tr>");
            sb_ret.append("\n<td colpsan=\"3\">");

            // Start to print applet
            //sb_app.append("\n<APPLET code=\"WBABar4Report.class\" archive=\""+ WBUtils.getInstance().getWebPath() + "wbadmin/lib/WBABar.jar\" width=\"550\" height=\"450\">");
            sb_app.append("\n<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"wbadmin/lib/WBGraph.jar\" width=\"550\" height=\"450\">");
            sb_app.append("\n<param name=\"GraphType\" value=\"Lines\">");
            sb_app.append("\n<param name=\"ncdata\" value=\"1\">");
            sb_app.append("\n<param name=\"percent\" value=\"false\">");

            ar_pag = getReportResults(request, paramsRequest);
            /*i_len = ar_pag.size();*/
            s_title = paramsRequest.getLocaleString("access_log_report");
            sb_app.append("\n<param name=\"Title\" value=\"" + s_title + "\">");
            sb_app.append("\n<param name=\"ndata\" value=\""+ i_len +"\">");
            int j=0;
            while(ar_pag.hasNext())
            /*for(int j=0;j<i_len;j++)*/
            {
                //RecResHits recResHits=(RecResHits)ar_pag.get(j);
                String[] arr_data = ar_pag.next();
                //String s_date = recResHits.getdate().toString();
                sb_app.append("\n<param name=\"label" + j + "\" value=\""+ arr_data[0] +"\">");
                sb_app.append("\n<param name=\"data" + j + "\" value=\"" + arr_data[1] + "\">");
                b_record = true;
                j++;
            }

            sb_app.append("\n<param name=\"color0\" value=\"66,138,212\">");
            sb_app.append("\n<param name=\"color1\" value=\"237,237,235\">");
            sb_app.append("\n<param name=\"color2\" value=\"229,243,253\">");
            sb_app.append("\n<param name=\"barname0\" value=\"Hits\">");
            sb_app.append("\n<param name=\"zoom\" value=\"true\">");
            sb_app.append("\n</APPLET>");
            // Finish to print applet

            // Evaluates if there are records
            if(b_record)
            {
                // Prints applet
                sb_ret.append(sb_app.toString());
            }
            else
            {
                // Prints message
                sb_ret.append("\n<center><br><br><br><br><br><br><br><br><font color=\"black\">" + paramsRequest.getLocaleString("no_records_found") + "</font></center>");
            }

            sb_ret.append("\n</td>");
            sb_ret.append("\n</tr>");
            sb_ret.append("\n</table>");
            sb_ret.append("\n</body>");
            sb_ret.append("\n</html>");
            //ar_pag = getReportResults(request, paramsRequest);
            //i_len = ar_pag.size();

        }
        catch (Exception e) {
            log.error("Error on method doGraph() resource " + strRscType + " with id " + base.getId(), e);
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
    public void doRepExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        Resource base = paramsRequest.getResourceBase();
        Iterator<String[]> ar_pag = null;
        long l_hits = 0;
        int i_len = 0;

        try
        {
            response.setContentType("application/vnd.ms-excel");
            out.println("\n<html>");
            out.println("\n<head>");
            out.println("\n<title>" + paramsRequest.getLocaleString("access_log_report") + "</title>");
            out.println("\n</head>");
            out.println("\n<body>");
            out.println("\n<table border=\"0\" width=\"550\">");
            out.println("\n<tr>");
            out.println("\n<td colpsan=\"3\" align=\"center\">");
            out.println(""+ paramsRequest.getLocaleString("access_log_report") +"");
            out.println("</td>");
            out.println("\n</tr>");
            out.println("\n<tr>");
            out.println("\n<td colpsan=\"3\">");
            ar_pag = getReportResults(request, paramsRequest);
            /*i_len = ar_pag.size();*/
            if(ar_pag.hasNext())
            {
                out.println("\n<table border=\"0\" width=\"100%\">");
                out.println("\n<tr><td colspan=\"2\">&nbsp;</td></tr>");
                out.println("\n<tr>");
                out.println("\n<th align=\"center\">" + paramsRequest.getLocaleString("date") + "</th>");
                out.println("\n<th align=\"center\">" + paramsRequest.getLocaleString("access") + "</th>");
                out.println("\n</tr>");
                while(ar_pag.hasNext())
                /*for(int j=0;j<i_len;j++)*/
                {
                    String[] arr_data = ar_pag.next();
                    out.println("\n<tr><td align=\"center\">" + arr_data[0] + "</td>");
                    out.println("\n<td align=\"center\">" + arr_data[1] + "</td></tr>");
                }
                out.println("\n</table>");
            }
            else
            {           // There are not records
                out.println("\n<center><br><font color=\"black\">" + paramsRequest.getLocaleString("no_records_found") + "</font></center>");
            }
            out.println("\n</td>");
            out.println("\n</tr>");
            out.println("\n</table>");
            out.println("\n</body>");
            out.println("\n</html>");
            out.close();
        }catch (Exception e) {
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
    public void doRepXml(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        Resource base = paramsRequest.getResourceBase();
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document dom = null;
        Iterator<String[]> ar_pag = null;
        String rtype = null;
        int i_len = 0;

        try
        {
            // Starts xml document
            dbf=DocumentBuilderFactory.newInstance();
            db=dbf.newDocumentBuilder();
            dom=db.newDocument();

            Element report = dom.createElement("access_log_report");
            dom.appendChild(report);

            ar_pag = getReportResults(request, paramsRequest);
            /*i_len = ar_pag.size();*/
            report.setAttribute("total",Integer.toString(i_len));
            int j=0;
            while(ar_pag.hasNext())
            /*for(int j=0;j<i_len;j++)*/
            {
                String[] arr_data = ar_pag.next();
                Element row = dom.createElement("row");
                row.appendChild(dom.createTextNode(""));
                row.setAttribute("id",Integer.toString(j+1));
                report.appendChild(row);

                Element date = dom.createElement("date");
                date.appendChild(dom.createTextNode(arr_data[0]));
                row.appendChild(date);

                Element access = dom.createElement("access");
                access.appendChild(dom.createTextNode(arr_data[1]));
                row.appendChild(access);
                j++;
            }
            out.println(SWBUtils.XML.domToXml(dom));
            out.close();
        }catch (Exception e) {
            log.error("Error on method doRepXml() resource " + strRscType + " with id " + base.getId(), e);
        }
    }

    /**
     * @param paramsRequest
     * @return
     */
    public String[] DoArrMonth(SWBParamRequest paramsRequest)
    {
        String[] arr_month = new String[12];
        try
        {
            arr_month[0] = paramsRequest.getLocaleString("month_january");
            arr_month[1] = paramsRequest.getLocaleString("month_february");
            arr_month[2] = paramsRequest.getLocaleString("month_march");
            arr_month[3] = paramsRequest.getLocaleString("month_april");
            arr_month[4] = paramsRequest.getLocaleString("month_may");
            arr_month[5] = paramsRequest.getLocaleString("month_june");
            arr_month[6] = paramsRequest.getLocaleString("month_july");
            arr_month[7] = paramsRequest.getLocaleString("month_august");
            arr_month[8] = paramsRequest.getLocaleString("month_september");
            arr_month[9] = paramsRequest.getLocaleString("month_october");
            arr_month[10] = paramsRequest.getLocaleString("month_november");
            arr_month[11] = paramsRequest.getLocaleString("month_december");
        }catch (Exception e) {
            log.error("Error on method DoArrMonth() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return arr_month;
    }

    /**
     * @param request
     * @return
     */
    public Iterator<String> getFileNames(HttpServletRequest request) {
        ArrayList files = new ArrayList();
        GregorianCalendar now = new GregorianCalendar();

        String accessLogPath = SWBPlatform.getEnv("swb/accessLog");
        String period = SWBPlatform.getEnv("swb/accessLogPeriod");
        String path = SWBPlatform.getWorkPath();
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
            String realpath = path + accessLogPath + "_" + siteId + "_acc.";

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
        System.out.println("archivos="+Arrays.toString(files.toArray()));
        return files.iterator();
    }

    /**
     * @param request
     * @param paramsRequest
     * @return
     */
    public Iterator<String[]> getReportResults(HttpServletRequest request, SWBParamRequest paramsRequest) {
        final int I_ZERO = 0;
        final int I_ONE = 1;
        final int I_TWO = 2;
        final int I_THREE = 3;
        final int I_FOUR = 4;
        final int I_FIVE = 5;
        final int I_SIX = 6;
        final int I_SEVEN = 7;
        final int I_EIGHT = 8;
        final int I_NINE = 9;
        final int I_TEN = 10;
        final int I_TWENTYTHREE = 23;
        final int I_TWENTYFOUR = 24;
        final int I_FIFTYNINE = 59;
        final int I_LESSONE = -1;

        hm_detail=new HashMap();

        ArrayList al_pag = new ArrayList();
        GregorianCalendar gc_now = new GregorianCalendar();
        /*GregorianCalendar gc_first = null;
        GregorianCalendar gc_last = null;*/
        GregorianCalendar gc_datefile = null;
        GregorianCalendar gc_datedisplay = null;
        GregorianCalendar gc_datedefault = null;
        ArrayList al_files = null;
        String[] arr_data = null;

        String s_period = null;
        String s_line = null;
        String s_aux = null;
        String s_timeaux = null;
        String s_resource = null;
        String s_filename = null;
        String s_yearfile = null;
        String s_monthfile = null;
        String s_dayfile = null;
        String s_hourfile = null;
        String s_minfile = null;
        String s_datefile = null;
        String s_auxresourceid = null;
        String s_datedefault = null;
        String s_hourfin = null;
        String s_year = null;

        boolean b_site = false;
        boolean b_ipadduser = false;
        boolean b_ipaddserver = false;
        boolean b_topicid = false;
        boolean b_userid = false;
        boolean b_languagesel = false;
        boolean b_devicesel = false;
        boolean b_usersel = false;
        boolean b_resourceid = false;
        boolean b_result = false;
        boolean b_sessionid = false;
        boolean b_repositoryid = false;
        boolean b_time = false;

        long l_count = 0;
        int i = 0;
        int i_row = 0;
        int i_len = 0;
        int i_new = 0;
        int i_lenfiles = 0;
        int i_hourini = 0;
        int i_hourfin = 0;
        int i_start = 0;


        // Receive parameters
        String s_site = request.getParameter("siteid");
        if(s_site == null)
        {
            s_site = "";
        }
        else
        {
            b_site = true;
        }
        String rtype = request.getParameter("wb_rtype");


        WebSite o_tm = SWBContext.getWebSite(s_site);
        WebPage o_tpp = null;
        WebPage o_tp = null;

        GregorianCalendar now = new GregorianCalendar();

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

        /*String s_rep_type = request.getParameter("wb_rep_type");
        String s_year_1 = request.getParameter("wb_year_1");
        String s_month_1 = request.getParameter("wb_month_1");
        String s_day_1 = request.getParameter("wb_day_1");
        String s_year_11 = request.getParameter("wb_year_11");
        String s_month_11 = request.getParameter("wb_month_11");
        String s_day_11 = request.getParameter("wb_day_11");
        String s_hour_11 = request.getParameter("wb_hour_11");
        String s_minute_11 = request.getParameter("wb_minute_11");
        String s_year_12 = request.getParameter("wb_year_12");
        String s_month_12 = request.getParameter("wb_month_12");
        String s_day_12 = request.getParameter("wb_day_12");
        String s_hour_12 = request.getParameter("wb_hour_12");
        String s_minute_12 = request.getParameter("wb_minute_12");*/

        String s_ipadduser = request.getParameter("ipuser")==null ? "":request.getParameter("ipuser");
        if(!s_ipadduser.equalsIgnoreCase("")) {
            b_ipadduser = true;
        }

        String s_ipaddserver = request.getParameter("ipserver")==null ? "":request.getParameter("ipserver");
        if(!s_ipaddserver.equalsIgnoreCase("")) {
            b_ipaddserver = true;
        }

        String s_sessionid = request.getParameter("sessid")==null ? "":request.getParameter("sessid");
        if(!s_sessionid.equalsIgnoreCase("")) {
            b_sessionid = true;
        }

        String s_subsection = request.getParameter("subsection")==null ? "false":request.getParameter("subsection");

        String s_topicid = request.getParameter("sectid")==null ? "":request.getParameter("sectid");
        if(!s_topicid.equalsIgnoreCase("")) {
            b_topicid = true;
            o_tpp = o_tm.getWebPage(s_topicid);
        }

        String s_repositoryid = request.getParameter("wb_repositoryid")==null ? "0":request.getParameter("wb_repositoryid");
        if(!s_repositoryid.equalsIgnoreCase("0")) {
            b_repositoryid = true;
        }

        String s_userid = request.getParameter("userid")==null ? "":request.getParameter("userid");
        if(!s_userid.equalsIgnoreCase("")) {
            b_userid = true;
        }

        String s_languagesel = request.getParameter("langid")==null ? "0":request.getParameter("langid");
        if(!s_languagesel.equalsIgnoreCase("0")) {
            b_languagesel = true;
        }

        String s_devicesel = request.getParameter("devid")==null ? "0":request.getParameter("devid");
        if(!s_devicesel.equalsIgnoreCase("0")) {
            b_devicesel = true;
        }

        String s_usersel = request.getParameter("usertype")==null ? "0":request.getParameter("usertype");
        if(!s_usersel.equalsIgnoreCase("0")) {
            b_usersel = true;
        }

        String s_resourceid = request.getParameter("resid")==null ? "":request.getParameter("resid");
        if(!s_resourceid.equalsIgnoreCase("")) {
            b_resourceid = true;
        }

        String s_typedisplay = request.getParameter("agregate")==null ? "2":request.getParameter("agregate");
        String s_time = request.getParameter("wb_time")==null ? "":request.getParameter("wb_time");

        // Receive parameters

        // Asign value to parameters
        /*if(s_rep_type == null) s_rep_type = "0";
        if(s_year_1 == null) s_year_1 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_1 == null) s_month_1 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_day_1 == null) s_day_1 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));

        if(s_year_11 == null) s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_11 == null) s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_month_11.length() == 1) s_month_11 = "0" + s_month_11;
        if(s_day_11 == null) s_day_11 = Integer.toString(I_START_DAY);
        if(s_hour_11 == null) s_hour_11 = "0";
        if(s_minute_11 == null) s_minute_11 = "0";

        if(s_year_12 == null) s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_12 == null) s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_month_12.length() == 1) s_month_12 = "0" + s_month_12;
        if(s_day_12 == null) s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
        if(s_hour_12 == null) s_hour_12 = "23";
        if(s_minute_12 == null) s_minute_12 = "59";*/
        // Asign value to  parameters

        //boolean b_topic = false;
        //int s_rec_add=0;
        if(rtype == null) {
            rtype="0";
        }

        s_period = SWBPlatform.getEnv("swb/accessLogPeriod");
        try
        {
            Iterator<String> files = getFileNames(request);
            /*i_lenfiles = al_files.size();*/
            if(files.hasNext())
            /*if(i_lenfiles > I_ZERO)*/
            {
                GregorianCalendar gc_first = new GregorianCalendar(year11, month11-1, day11, hour11, minute11);
                GregorianCalendar gc_last  = new GregorianCalendar(year12, month12-1, day12, hour12, minute12);

                String s_key=null;
                Vector vec_rep = null;
                //Get data from files
                /*for(int j=0;j<al_files.size();j++)*/
                while(files.hasNext()) {
                    s_filename = files.next();
                    File f = new File(s_filename);
                    if(f.exists()) {
                        RandomAccessFile rf_in = new RandomAccessFile(f,"r");
                        gc_datedefault = null;
                        l_count = 0;
                        while((s_line = rf_in.readLine()) != null) {
                            StringTokenizer st_line = new StringTokenizer(s_line,"|");
                            String s_linea = s_line;
                            i++;
                            i_row = 0;
                            i_len = 0;
                            while(st_line.hasMoreElements()) {
                                //s_aux receives date
                                s_aux = st_line.nextToken();
                                if(i_row == I_ZERO)
                                {
                                    s_yearfile = s_aux.substring(0,4);
                                    s_monthfile = s_aux.substring(5,7);
                                    s_dayfile = s_aux.substring(8,10);
                                    s_hourfile = s_aux.substring(11,13);
                                    s_minfile = s_aux.substring(14,16);
                                    gc_datefile = new GregorianCalendar(Integer.parseInt(s_yearfile),Integer.parseInt(s_monthfile)-1,Integer.parseInt(s_dayfile),Integer.parseInt(s_hourfile),Integer.parseInt(s_minfile));

                                    //Evaluates if dates are correct
                                    if((gc_datefile.after(gc_first) | gc_datefile.equals(gc_first)) & ((gc_datefile.before(gc_last) | gc_datefile.equals(gc_last)))) {
                                        b_result = true;
                                        s_datefile = s_aux;
                                    }else {
                                        b_result = false;
                                        System.out.println("no pasa fecha");
                                    }
                                    gc_datefile = null;
                                }
                                //If not b_result does nothing
                                if(b_result) {
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives user ip
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_ONE) && b_ipadduser))
                                    {
                                        if(s_aux.equals(s_ipadduser.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa user ip");
                                        }
                                    }

                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives server ip
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_TWO) && b_ipaddserver))
                                    {
                                        if(s_aux.equals(s_ipaddserver.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa server ip");
                                        }
                                    }

                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives session id
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_THREE) && b_sessionid))
                                    {
                                        if(s_aux.equals(s_sessionid.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa session id");
                                        }
                                    }

                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives topicmap id, leave out this value and increase i_len
                                    s_aux = st_line.nextToken();
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    //s_aux receives topic id
                                    s_aux = st_line.nextToken();

                                    o_tp=o_tm.getWebPage(s_aux);

                                    if(((b_result) && ((i_row == I_FOUR) && b_topicid)))
                                    {
                                        if(s_aux.equals(s_topicid.trim())||(null!=s_subsection && "1".equals(s_subsection) && null!=o_tp &&o_tpp.isParentof(o_tp)))
                                        {
                                            b_result = true;
                                            //b_topic = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa section id");
                                        }
                                    }

                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives repository id
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_FIVE) && b_repositoryid))
                                    {
                                        if(s_aux.equals(s_repositoryid.trim()))
                                        {
                                            b_result = true;
                                        }/*else {
                                            b_result = false;
                                        }*/
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives user id
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_SIX) && b_userid))
                                    {
                                        if(s_aux.equals(s_userid.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa user id");
                                        }
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives user type
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_SEVEN) && b_usersel))
                                    {
                                        if(s_aux.equals(s_usersel.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa user type");
                                        }
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives device type
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_EIGHT) && b_devicesel))
                                    {
                                        if(s_aux.equals(s_devicesel.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa device id");
                                        }
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives language
                                    s_aux = st_line.nextToken();
                                    if((b_result) && ((i_row == I_NINE) && b_languagesel))
                                    {
                                        if(s_aux.equals(s_languagesel.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else {
                                            b_result = false;
                                            System.out.println("no pasa lang");
                                        }
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    // Check if next token is resource
                                    if((b_result) && (i_row == I_TEN) )
                                    {
                                        s_timeaux = st_line.nextToken();
                                        System.out.println("time="+s_timeaux);
                                        if(!s_timeaux.equals(null) && b_time)
                                        {
                                            if(s_timeaux.equals(s_time.trim()))
                                            {
                                                b_result = true;
                                            }
                                            else {
                                                b_result = false;
                                                System.out.println("no pasa resource");
                                            }
                                        }
                                        if((b_resourceid) && (s_line.length() >= i_len - I_ONE))
                                        {
                                            i_new = 1;
                                            s_aux = s_line.substring(i_len, s_line.length());
                                            int i_pos = s_aux.indexOf("|");
                                            b_result = false;
                                            if(i_pos != I_LESSONE)
                                            {
                                                int i_end = s_aux.length();
                                                s_resource = s_aux.substring(i_pos + I_ONE,i_end);
                                                //Evaluates resources
                                                StringTokenizer st_resource = new StringTokenizer(s_resource,"|");
                                                while(st_resource.hasMoreElements())
                                                {
                                                    s_auxresourceid = (String)st_resource.nextElement();
                                                    if(s_resourceid.equals(s_auxresourceid))
                                                    {
                                                        b_result = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            }

                            //Evaluates if record passes
                            if(b_result) {
                                if(s_typedisplay.equalsIgnoreCase("1")) {
                                    //Display by year
                                    gc_datedisplay = new GregorianCalendar(Integer.parseInt(s_yearfile),Integer.parseInt(s_monthfile),Integer.parseInt(s_dayfile));
                                    if(gc_datedefault == null)
                                    {
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;
                                    }
                                    int i_yeardisplay = gc_datedisplay.get(Calendar.YEAR);
                                    int i_yeardefault = gc_datedefault.get(Calendar.YEAR);

                                    if(i_yeardisplay == i_yeardefault)
                                    {
                                        l_count++;
                                        i_new = 0;
                                    }
                                    else
                                    {
                                        arr_data = new String[2];
                                        arr_data[0] = s_datedefault.substring(0,4);
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }
                                }else if(s_typedisplay.equalsIgnoreCase("2")) {
                                    //Display by month
                                    gc_datedisplay = new GregorianCalendar(Integer.parseInt(s_yearfile),Integer.parseInt(s_monthfile),Integer.parseInt(s_dayfile));
                                    if(gc_datedefault == null)
                                    {
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;
                                    }
                                    int i_monthdisplay = gc_datedisplay.get(Calendar.MONTH);
                                    int i_monthdefault = gc_datedefault.get(Calendar.MONTH);
                                    if(i_monthdisplay == i_monthdefault)
                                    {
                                        l_count++;
                                        i_new = 0;
                                    }
                                    else
                                    {
                                        arr_data = new String[2];
                                        arr_data[0] = s_datedefault.substring(0,7);
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }
                                }else if(s_typedisplay.equalsIgnoreCase("3")) {
                                    //Display by day
                                    gc_datedisplay = new GregorianCalendar(Integer.parseInt(s_yearfile),Integer.parseInt(s_monthfile),Integer.parseInt(s_dayfile));
                                    if(gc_datedefault == null)
                                    {
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;
                                    }
                                    if(gc_datedisplay.equals(gc_datedefault))
                                    {
                                        l_count++;
                                        i_new = 0;
                                    }
                                    else
                                    {
                                        arr_data = new String[2];
                                        arr_data[0] = s_datedefault.substring(0,10);
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }
                                }else if(s_typedisplay.equalsIgnoreCase("4")) {
                                    //Display by hour
                                    gc_datedisplay = new GregorianCalendar(Integer.parseInt(s_yearfile),Integer.parseInt(s_monthfile),Integer.parseInt(s_dayfile),Integer.parseInt(s_hourfile),0);

                                    if(gc_datedefault == null)
                                    {
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;
                                    }
                                    if(gc_datedisplay.equals(gc_datedefault))
                                    {
                                        l_count++;
                                        i_new = 0;
                                    }
                                    else
                                    {
                                        arr_data = new String[2];
                                        i_hourini = Integer.parseInt(s_datedefault.substring(11,13));
                                        i_hourfin = i_hourini + 1 ;
                                        if(i_hourfin == I_TWENTYFOUR)
                                        {
                                            i_hourfin = 0;
                                            s_hourfin = Integer.toString(i_hourfin) + "0:00";
                                        }
                                        else
                                        {
                                            s_hourfin = Integer.toString(i_hourfin);
                                            if(s_hourfin.length() == I_ONE) s_hourfin = "0" + s_hourfin;
                                            s_hourfin = s_hourfin + ":00";
                                        }
                                        arr_data[0] = s_datedefault.substring(0,13) + ":00-" + s_hourfin;
                                        arr_data[1] = Long.toString(l_count);
                                        l_count = 1;
                                        gc_datedefault = gc_datedisplay;
                                        s_datedefault = s_datefile;

                                        al_pag.add(arr_data);
                                        i_new = 1;
                                    }

                                }

                                // GENERATES the detail of the report
                                s_key="";
                                if(s_typedisplay.equals("1"))
                                {
                                    s_key = s_line.substring(0,4);
                                }
                                else if(s_typedisplay.equals("2"))
                                {
                                    s_key = s_line.substring(0,7);
                                }
                                else if(s_typedisplay.equals("3"))
                                {
                                    s_key = s_line.substring(0,10);
                                }
                                else if(s_typedisplay.equals("4"))
                                {
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

                                    s_key = s_line.substring(0,13) + ":00-"+s_hourfin;
                                    System.out.println("generating... s_key="+s_key);
                                }

                                vec_rep = (Vector) hm_detail.get(s_key);
                                if(null == vec_rep)
                                {
                                    vec_rep=new Vector();
                                }
                                vec_rep.add(s_line);
                               // s_rec_add++;
                                hm_detail.put(s_key,vec_rep);
                            }
                        }

                        if((i_new == I_ONE) | (l_count != I_ZERO))
                        {
                            arr_data = new String[2];
                            if(s_typedisplay.equals("1"))
                                arr_data[0] = s_datedefault.substring(0,4);

                            if(s_typedisplay.equals("2"))
                                arr_data[0] = s_datedefault.substring(0,7);

                            if(s_typedisplay.equals("3"))
                                arr_data[0] = s_datedefault.substring(0,10);

                            if(s_typedisplay.equals("4"))
                            {
                                i_hourini = Integer.parseInt(s_datedefault.substring(11,13));

                                System.out.println("hourini="+i_hourini);

                                i_hourfin = i_hourini + 1 ;
                                if(i_hourfin == I_TWENTYFOUR)
                                {
                                    i_hourfin = 0;
                                    s_hourfin = Integer.toString(i_hourfin) + "0:00";
                                }
                                else
                                {
                                    s_hourfin = Integer.toString(i_hourfin);
                                    if(s_hourfin.length() == I_ONE) {
                                        s_hourfin = "0" + s_hourfin;
                                    }
                                    s_hourfin = s_hourfin + ":00";
                                }
                                System.out.println("hourfin="+i_hourfin+" s_hourfin="+s_hourfin);
                                
                                arr_data[0] = s_datedefault.substring(0,13) + ":00-" + s_hourfin;
                                System.out.println("arr_data[0]="+arr_data[0]);

                            }
                            arr_data[1] = Long.toString(l_count);

                            al_pag.add(arr_data);
                        }
                        //ends read of file
                        rf_in.close();
                    }
                    else {
                        log.error("File " + s_filename + " not found on method getReportResults() resource " + strRscType + " with id " +  getResourceBase().getId());
                    }
                }
            }


            //If it should display by year or month then filter data here
            if(s_typedisplay.equals("1") || s_typedisplay.equals("2"))
            {
                System.out.println("...........................");
                i_start = 0;
                s_year = null;
                ArrayList al_aux = new ArrayList();
                for(int h=0; h < al_pag.size(); h++)
                {
                    String[] arr_dataaux = (String[])al_pag.get(h);
                    if(i_start == 0)
                    {
                        s_year = arr_dataaux[0];
                        l_count = Integer.parseInt(arr_dataaux[1]);
                    }
                    else
                    {
                        if(arr_dataaux[0].equals(s_year))
                        {
                            l_count = l_count + Integer.parseInt(arr_dataaux[1]);
                        }
                        else
                        {
                            arr_data = new String[2];
                            arr_data[0] = s_year;
                            arr_data[1] = Long.toString(l_count);
                            al_aux.add(arr_data);
                            s_year = arr_dataaux[0];
                            l_count = Integer.parseInt(arr_dataaux[1]);
                        }
                    }
                    i_start++;
                    if(i_start == al_pag.size())
                    {
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

    /**
     * @param p_repository
     * @param paramsRequest
     * @return
     */
    public HashMap getUserType(String p_repository, SWBParamRequest paramsRequest)
    {
        HashMap hm_type = new HashMap();
        int i_len = 0;
        try
        {
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
            hm_type.put(Integer.toString(i_len),"_|"+ "indefinido" +"");
        }catch (Exception e) {
            log.error("Error on method getUserType() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return hm_type;
    }
}