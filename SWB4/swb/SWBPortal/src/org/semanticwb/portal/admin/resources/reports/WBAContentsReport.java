
package org.semanticwb.portal.admin.resources.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.util.WebSiteSectionTree;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

public class WBAContentsReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBASectionReport.class);
    
    private String strRscType;
    private WebSiteSectionTree tree = new WebSiteSectionTree();
    
    private static Integer x;
    static{x=new Integer(0);}
    
    /*ContentMonitor content_mon=null;*/
    
    public WBAContentsReport() {
        /*content_mon = ContentMonitor.getInstance();*/
    }
    
    @Override
    public void init(){
        Portlet base = getResourceBase();        
        try {
            strRscType = base.getPortletType().getPortletClassName();
        }catch (Exception e) {
            strRscType = "WBAContentsReport";
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        if(paramsRequest.getMode().equalsIgnoreCase("rendertree")) {
            doRenderSectionTree(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("fillgridmtr")) {
            doFillGridMaster(request,response,paramsRequest);
        /*}else if(paramsRequest.getMode().equalsIgnoreCase("fillgriddet")) {
            doFillGridDetail(request,response,paramsRequest);*/
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_excel")) {
            doRepExcel(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equalsIgnoreCase("report_xml")) {
            doRepXml(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }
    
    private void doRenderSectionTree(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        
        String webSiteId = request.getParameter("site");
        
        SWBResourceURL url=paramsRequest.getRenderUrl();
        url.setCallMethod(url.Call_DIRECT);
        url.setMode("rendertree");
        
        String section = request.getParameter("reptp");
        if(section != null) {
            out.println("<input type=\"hidden\" name=\"section\" id=\"section\" value=\""+section+"\" />");
        }        
        out.println(tree.render(webSiteId, request, paramsRequest.getUser(), url.toString()));
        out.flush();
    }
    
    private void doFillGridMaster(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder json = new StringBuilder();
        
        json.append("{ identifier: 'id',");
        json.append("label: 'id',");
        json.append("items: [");        
        
        String webSiteId = request.getParameter("site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("site");
        WebSite webSite = SWBContext.getWebSite(webSiteId);
        String webPageId = request.getParameter("section");        
        WebPage webPage = webSite.getWebPage(webPageId);        
        Bool first = new Bool();
        Iterator<Portlet> portlets = webPage.listPortlets();        
        while(portlets.hasNext()) {
            Portlet portlet = portlets.next();
            if(portlet.getPortletType().getPortletMode()==1) {
                if(first.isFirst()) {
                    first.setFirst();                    
                }else {
                    json.append(",");
                }
                json.append(" {");
                json.append(" sect:'"+webPage.getTitle()+"'");
                json.append(", id:'"+portlet.getId()+"'");
                json.append(", tipo:'"+portlet.getPortletType().getTitle()+"'");
                json.append(", contenido:'"+portlet.getTitle()+"'");
                json.append(", prior:'"+portlet.getPriority()+"'");
                json.append(", active:'"+(portlet.isActive()?"Si":"No")+"'");
                json.append(", loc:'"+portlet.getWorkPath()+"'");
                json.append(", uri:'"+portlet.getURI()+"'");
                json.append(", broke:'No'");
                json.append(" }");
                
            }
        }
        
        if(request.getParameter("sons")!=null && request.getParameter("sons").equalsIgnoreCase("1")) {
            json.append(getContentInJson(webPage.listChilds(), webPage.getTitle(), first));
        }
        
        json.append("],");
        json.append("source:1, rating:3 }");
        out.print(json.toString());                
        out.flush();
    }
    
    private String getContentInJson(Iterator<WebPage> childs, String sectionName, Bool first) {
        StringBuilder json = new StringBuilder();        
        
        while(childs.hasNext()) {
            WebPage webPage = childs.next();
            Iterator<Portlet> portlets = webPage.listPortlets();
            while(portlets.hasNext()) {
                Portlet portlet = portlets.next();
                if(portlet.getPortletType().getPortletMode()==1) {
                    if(first.isFirst()) {
                        first.setFirst();
                    }else {
                        json.append(",");
                    }
                    json.append(" {");
                    json.append(" sect:'"+webPage.getTitle()+"'");
                    json.append(", id:'"+portlet.getId()+"'");
                    json.append(", tipo:'"+portlet.getPortletType().getTitle()+"'");
                    json.append(", contenido:'"+portlet.getTitle()+"'");
                    json.append(", prior:'"+portlet.getPriority()+"'");
                    json.append(", active:'"+(portlet.isActive()?"Si":"No")+"'");
                    json.append(", loc:'"+portlet.getWorkPath()+"'");
                    json.append(", uri:'"+portlet.getURI()+"'");
                    json.append(", broke:'No'");
                    json.append(" }");
                }
            }
            if(webPage.listChilds().hasNext()) {
                json.append(getContentInJson(webPage.listChilds(), webPage.getTitle(), first));
            }
        }
        
        return json.toString();
    }
        
    /*private void doFillGridDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        
        String contentTypeTitle = request.getParameter("res");
        System.out.println("contentTypeTitle="+contentTypeTitle);
        
        out.println("{ identifier: 'contentType',");
        out.println("label: 'contentType',");        
        out.println("items: [{ contentType:'recurso', total:1200, source:1, rating:3 }],");
        out.println("source:1, rating:3 }");
        out.flush();
    }*/

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache"); 
        PrintWriter out = response.getWriter();
        Portlet base = getResourceBase();
                
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
                                
                /*int deleteFilter;
                try {
                    deleteFilter = request.getParameter("wb_deletefilter")==null ? 0:Integer.parseInt(request.getParameter("wb_deletefilter"));
                }catch(NumberFormatException e) {
                    deleteFilter = 0;
                }*/
                                
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
                
                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);                

                out.println("<script type=\"text/javascript\">");
                
                out.println("dojo.require(\"dojox.grid.DataGrid\");");
                out.println("dojo.require(\"dojo.data.ItemFileReadStore\");");
                
                out.println("dojo.addOnLoad(function(){renderTreeSectionsSite('"+url.toString()+"', 'rendertree','"+webSiteId+"','slave')});");
                
                out.println("function renderTreeSectionsSite(url, mode, site, canvasId) {");
                out.println("   getHtml(url+'/_mod/'+mode+'?site='+site, canvasId);");
                out.println("}");
                
                out.println("function fillGrid(grid, uri, mode, params) {");
                out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri+'/_mod/'+mode+'?'+params});");
                out.println("   grid._refresh();");
                out.println("}");
                
                out.println("var layout= null;");
                out.println("var jStrMaster = null;");                
                out.println("var gridMaster = null;");
                out.println("var gridResources = null;");
                
                /*out.println("function getResources(e) {");
                out.println("   dojo.byId('ctnerDetail').style.display = 'block';");
                out.println("   var row = e.grid.selection.getFirstSelected();");
                out.println("   var params = 'res='+row.contentType;");
                out.println("   fillGrid(dijit.byId('gridResources'), '"+url.toString()+"', 'fillgriddet', params);");                
                out.println("}");*/
                
                out.println("dojo.addOnLoad(function() {");
                out.println("    dojo.byId('ctnerMaster').style.display = 'none';");
                //out.println("    dojo.byId('ctnerDetail').style.display = 'none';");
                                
                out.println("   layout= [");
                out.println("      { field:'sect', width:'100px', name:'Sección' },");        
                out.println("      { field:'id', width:'50px', name:'Id' },");
                out.println("      { field:'tipo', width:'100px', name:'Tipo contenido' },");
                out.println("      { field:'contenido', width:'100px', name:'Contenido' },");
                out.println("      { field:'prior', width:'50px', name:'Prioridad' },");
                out.println("      { field:'active', width:'50px', name:'Activo' },");
                out.println("      { field:'loc', width:'100px', name:'Localidad' },");
                out.println("      { field:'uri', width:'150px', name:'Uri' },");
                out.println("      { field:'broke', width: '50px', name:'Liga rota' },");
                out.println("   ];");

                out.println("   gridMaster = new dojox.grid.DataGrid({");
                out.println("      id: 'gridMaster',");
                //out.println("      store: jStrMaster,");
                out.println("      structure: layout,");
                out.println("      rowSelector: '20px',");
                out.println("      rowsPerPage: '20'");
                out.println("   }, 'gridMaster');");
                out.println("   gridMaster.startup();");
                //out.println("    dojo.connect(dijit.byId('gridMaster'), 'onRowDblClick', getResources);");

                /*out.println("    gridResources = new dojox.grid.DataGrid({");
                out.println("        id: 'gridResources',");
                //out.println("        store: jStrResources,");
                out.println("        structure: layout,");
                out.println("        rowSelector: '20px',");
                out.println("        rowsPerPage: '20'");
                out.println("    }, 'gridResources');");
                out.println("    gridResources.startup();");*/
                out.println("});");
                
                out.println("function getParams(accion) { ");
                out.println("   var params = \"?\";");
                out.println("   params = params + \"wb_site=\" + window.document.frmrep.wb_site.value;");
                out.println("   params = params + \"&wb_lang=\" + document.getElementById('wb_lang').options[document.getElementById('wb_lang').selectedIndex].value;");
                /*out.println("   if(document.getElementById('wb_deletefilter').checked) { ");
                out.println("       params = params + \"&wb_deletefilter=\" + document.getElementById('wb_deletefilter').value; ");
                out.println("   } ");*/
                out.println("   params = params + \"&wb_rtype=\" + document.getElementById('wb_rtype').value;");
                out.println("   return params;");
                out.println("} ");

                out.println("function doXml(accion, size) { ");
                out.println("   var params = getParams(accion);");
                out.println("   window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml") + "\"+params,\"graphWindow\",size);");
                out.println("}");

                out.println("function doExcel(accion, size) { ");
                out.println("   var params = getParams(accion);");
                out.println("   window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel") + "\"+params,\"graphWindow\",size);");
                out.println("}");

                out.println("function doApply() {");
                out.println("   dojo.byId('ctnerMaster').style.display = 'block';");
                out.println("   var grid = dijit.byId('gridMaster');");
                out.println("   var params = 'site='+dojo.byId('wb_site').value;");                
                
                out.println("   if(dojo.byId('section')) {");                
                out.println("      params += '&section=' + dojo.byId('section').value;");
                out.println("      if(dojo.byId('wb_show_son').checked) {");
                out.println("         params += '&sons=' + dojo.byId('wb_show_son').value;");
                out.println("      }");
                out.println("      fillGrid(grid, '"+url.toString()+"', 'fillgridmtr', params);");
                out.println("   }else {");                
                out.println("      alert('Para poder mostrarle el resumen de contenido, primero debe seleccionar una sección');");
                out.println("   }");                
                out.println("}");
                
                out.println("</script>");
                // javascript
                
                out.println("<div id=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("contents_report") + "</legend>");

                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                out.println("<table border=\"0\" width=\"95%\" align=\"center\">");
                out.println("<tr><td width=\"100\"></td><td width=\"120\"></td><td></td><td></td></tr>");
                out.println("<tr>");
                out.println("<td colspan=\"4\">");
                // Show report description
                out.println(paramsRequest.getLocaleString("description"));                
                out.println("</td></tr>");
                
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                out.println("<tr>");
                out.println(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doXml('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doExcel('"+ rtype +"','width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\" name=\"btnExcel\" />&nbsp;");
                out.println("   <input type=\"button\" onClick=\"doApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\" />");
                out.println("   <input type=\"hidden\" name=\"tp\" id=\"tp\" />");
                out.println(" </td>");
                out.println("</tr>");
                out.println("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                
                out.println("<tr>");
                out.println("<td>" + paramsRequest.getLocaleString("site") + ":</td>");
                //url.setMode("rendertree");
                out.println("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" onchange=\"renderTreeSectionsSite('"+url.toString()+"', 'rendertree', this.value, 'slave');\">");
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
                out.println("<td>" + paramsRequest.getLocaleString("section") + ":</td>");                
                out.println("<td colspan=\"3\"><div id=\"slave\"></div></td>");
                out.println("</tr>");
                
                out.println("<tr>");      
                out.println("<td colspan=\"4\"><input type=\"checkbox\" id=\"wb_show_son\" name=\"wb_show_son\" value=\"1\" />");
                out.println("<label for=\"wb_show_son\">"+paramsRequest.getLocaleString("descendant")+"</label>");
                out.println("</td>");
                out.println("</tr>");
                
                out.println("<tr><td colspan=\"4\" height=\"15\"><hr size=\"1\" noshade></td></tr>");
                
                out.println("<tr>");
                out.println("<td colspan=\"4\">");
                out.println("<div id=\"ctnerMaster\" style=\"height:450px; width:875px; margin: 2px; padding: 0px; border: 1px solid #DAE1FE;\">");
                out.println("  <div id=\"gridMaster\"></div>");
                out.println("</div>");
                /*out.println("<br />");
                out.println("<div id=\"ctnerDetail\" style=\"height:250px; width:750px; margin: 2px; padding: 0px; border: 1px solid #DAE1FE;\">");
                out.println("  <div id=\"gridResources\"></div>");
                out.println("</div>");*/
                out.println("</td>");
                out.println("</tr>");
                
                out.println("<tr><td colspan=\"4\"><br /></td></tr>");
            }
        }catch (Exception e) {
            log.error("Error on method doView() resource " + strRscType + " with id " + base.getId(), e);
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
    public void doRepExcel(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doRepXml(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException{
        
    }


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
     * @param tp
     * @param paramsRequest
     * @return
     */    
    public ArrayList getTotalContent(WebPage tp, SWBParamRequest paramsRequest, String p_showson, String p_content_type){
        ArrayList al_pag = new ArrayList();
        /*Iterator referencias = null;
        RecResource recRes = null;
        String str_path = null;
        String tp_url=null;
        String str_url = "";
        String str_nombre = null;
        String str_contentname = null;
        String str_contentid = null;
        String str_type = null;
        String str_active = null;
        int i_active = 0;

        int i_priority = 0;
        long l_dpp = 0;
        boolean band = false;

        try{

            referencias = tp.getOccurrencesOfType("REC_WBContent");
            while(referencias.hasNext()){
                if(!band){
                    HashMap arg=new HashMap();
                    arg.put(new String("separator"),new String("\\"));
                    arg.put(new String("links"),new String("false"));
                    str_path = tp.getPath(arg);
                    tp_url=tp.getUrl();
                    band=true;
                }
                Occurrence contents=(Occurrence)referencias.next();

                if(contents!=null) {
                    l_dpp = Long.parseLong(contents.getResourceData());
                    recRes=DBResource.getInstance().getResource(tp.getMap().getId(),l_dpp);
                    if(recRes != null){
                        RecResourceType recObj=DBResourceType.getInstance().getResourceType(recRes.getTypeMap(),recRes.getType());
                        str_type = recObj.getName();
                        if(str_type.equals(p_content_type)){
                            if(str_type.equals("WBUrlContent") || str_type.equals("FrameContent")){
                                Document domxmlRec = AFUtils.getInstance().XmltoDom(recRes.getXml());
                                if(domxmlRec != null){
                                    NodeList archivo = domxmlRec.getElementsByTagName("url");
                                    if(archivo.getLength()>0){
                                        str_url = archivo.item(0).getChildNodes().item(0).getNodeValue();
                                    }
                                }
                            }

                            str_nombre = recRes.getTitle();
                            str_contentname = AFUtils.decode(str_nombre,"UTF-8");
                            str_contentid = contents.getResourceData();
                            i_priority = recRes.getPriority();
                            i_active = contents.getDbdata().getActive();
                            if(i_active == 1){
                                str_active = paramsRequest.getLocaleString("yes");
                            }
                            else{
                                str_active = paramsRequest.getLocaleString("no");
                            }

                            String[] arr_data = new String[8];
                            arr_data[0] = str_contentid;
                            arr_data[1] = str_contentname;
                            arr_data[2] = Integer.toString(i_priority);
                            arr_data[3] = str_active;
                            arr_data[4] = str_type;
                            arr_data[5] = str_path;
                            arr_data[6] = tp_url;
                            arr_data[7] = str_url;
                            al_pag.add(arr_data);
                        }
                    }
                }
            }

            if(p_showson.equals("1")){
                // Shows contents of topic's child
                Iterator iTp=tp.getChildAll().iterator();
                while(iTp.hasNext()){
                    band = false;
                    Topic tp_child=(Topic)iTp.next();
                    Iterator it_child = tp_child.getOccurrencesOfType("REC_WBContent");
                    while(it_child.hasNext()){
                        if(!band){
                            HashMap arg=new HashMap();
                            arg.put(new String("separator"),new String("\\"));
                            arg.put(new String("links"),new String("false"));
                            str_path = tp_child.getPath(arg);
                            tp_url=tp_child.getUrl();
                            band=true;
                        }
                        Occurrence oc_child = (Occurrence)it_child.next();

                        if(oc_child!=null) {
                            l_dpp = Long.parseLong(oc_child.getResourceData());
                            recRes=DBResource.getInstance().getResource(tp.getMap().getId(),l_dpp);
                            if(recRes != null){
                                RecResourceType recObj=DBResourceType.getInstance().getResourceType(recRes.getTypeMap(),recRes.getType());
                                str_type = recObj.getName();
                                if(str_type.equals(p_content_type)){
                                    if(str_type.equals("WBUrlContent") || str_type.equals("FrameContent")){
                                        Document domxmlRec = AFUtils.getInstance().XmltoDom(recRes.getXml());
                                        if(domxmlRec != null){
                                            NodeList archivo = domxmlRec.getElementsByTagName("url");
                                            if(archivo.getLength()>0){
                                                str_url = archivo.item(0).getChildNodes().item(0).getNodeValue();
                                            }
                                        }
                                    }


                                    str_nombre = recRes.getTitle();
                                    str_contentname = AFUtils.decode(str_nombre,"UTF-8");
                                    str_contentid = oc_child.getResourceData();
                                    i_priority = recRes.getPriority();
                                    i_active = oc_child.getDbdata().getActive();
                                    if(i_active == 1){
                                        str_active = paramsRequest.getLocaleString("yes");
                                    }
                                    else{
                                        str_active = paramsRequest.getLocaleString("no");
                                    }

                                    String[] arr_data = new String[8];
                                    arr_data[0] = str_contentid;
                                    arr_data[1] = str_contentname;
                                    arr_data[2] = Integer.toString(i_priority);
                                    arr_data[3] = str_active;
                                    arr_data[4] = str_type;
                                    arr_data[5] = str_path;
                                    arr_data[6] = tp_url;
                                    arr_data[7] = str_url;
                                    al_pag.add(arr_data);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            log.error("Error on method getTotalContent() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }*/
        return al_pag;
    }


    public ArrayList getGroupContent(WebPage tp, SWBParamRequest paramsRequest, String p_showson) {
        ArrayList al_pag = new ArrayList();
        /*Iterator referencias = null;
        RecResource recRes = null;
        //String str_path = null;
        //String str_url = "";
        //String str_nombre = null;
        //String str_contentname = null;
        String str_contentid = null;
        String str_type = null;
        //String str_active = null;
        //int i_active = 0;
        //int i_priority = 0;
        long l_dpp = 0;
        //boolean band = false;
        try{

            referencias = tp.getOccurrencesOfType("REC_WBContent");
            while(referencias.hasNext()){
                Occurrence contents=(Occurrence)referencias.next();
                if(contents!=null) {
                    l_dpp = Long.parseLong(contents.getResourceData());
                    recRes=DBResource.getInstance().getResource(tp.getMap().getId(),l_dpp);
                    if(recRes != null){
                        str_contentid = contents.getResourceData();

                        RecResourceType recObj=DBResourceType.getInstance().getResourceType(recRes.getTypeMap(),recRes.getType());
                        if(recObj != null){
                            str_type = recObj.getName();
                        }
                        else{
                            str_type ="";
                        }
                        String[] arr_data = new String[2];
                        arr_data[0] = str_contentid;
                        arr_data[1] = str_type;
                        al_pag.add(arr_data);
                    }
                }
            }

            if(p_showson.equals("1")){
                // Shows contents of topic's child
                Iterator iTp=tp.getChildAll().iterator();
                while(iTp.hasNext()){
                    Topic tp_child=(Topic)iTp.next();
                    Iterator it_child = tp_child.getOccurrencesOfType("REC_WBContent");
                    while(it_child.hasNext()){
                        Occurrence oc_child = (Occurrence)it_child.next();
                        if(oc_child!=null) {
                            l_dpp = Long.parseLong(oc_child.getResourceData());
                            recRes=DBResource.getInstance().getResource(tp.getMap().getId(),l_dpp);
                            if(recRes != null){
                                str_contentid = oc_child.getResourceData();
                                RecResourceType recObj=DBResourceType.getInstance().getResourceType(recRes.getTypeMap(),recRes.getType());
                                if(recObj != null){
                                    str_type = recObj.getName();
                                }
                                else{
                                    str_type ="";
                                }
                                String[] arr_data = new String[2];
                                arr_data[0] = str_contentid;
                                arr_data[1] = str_type;
                                al_pag.add(arr_data);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            AFUtils.log(e, "Error on method getGroupContent() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), true);
        }*/
        return al_pag;
    }

    public HashMap doPriorityValues(SWBParamRequest paramsRequest){
        HashMap hm_values = new HashMap();
        try{
            hm_values.put("1",paramsRequest.getLocaleString("default"));
            hm_values.put("2",paramsRequest.getLocaleString("low"));
            hm_values.put("3",paramsRequest.getLocaleString("medium"));
            hm_values.put("4",paramsRequest.getLocaleString("high"));
            hm_values.put("5",paramsRequest.getLocaleString("priority"));
        }
        catch (Exception e){
            log.error("Error on method doPriorityValues() resource " + strRscType + " with id " + getResourceBase().getId(), e);
        }
        return hm_values;
    }


    
    
    
    private class Bool {
        private boolean first;
        
        public Bool() {
            first = true;
        }
        
        public boolean isFirst() {
            return first;
        }
        
        public void setFirst() {
            first = false;
        }
        
    }
}