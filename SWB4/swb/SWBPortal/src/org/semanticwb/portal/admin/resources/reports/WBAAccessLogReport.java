package org.semanticwb.portal.admin.resources.reports;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.util.*;

import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Device;
import org.semanticwb.model.Language;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WBAAccessLogReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBAGlobalReport.class);

    private final String I_REPORT_IDAUX = "_"; // Type 0 of reports "Infotec WebBuilder report support page"
    private final int I_START_DAY = 1;         // The initial day for months
    private final int I_PAGE_SIZE = 20;        // Number of rows per page
    private final int I_INIT_PAGE = 0;         // The init page to display results
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
        if(paramsRequest.getMode().equals("graph")) {
            doGraph(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_excel")) {
            doRepExcel(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_xml")) {
            doRepXml(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("report_detail")) {
            doDetail(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    private void renderSelect(ArrayList origList, Device node, StringBuffer ret, String space, String seldevs) {
        ArrayList<Device> devs=new ArrayList<Device>();
        Iterator<Device> itDevices = node.listChilds();
        while(itDevices.hasNext()) {
            devs.add(itDevices.next());
        }
        devs.trimToSize();

        while(!devs.isEmpty()) {
            Device device = devs.get(0);
            ret.append("<option value=\""+device.getId()+"\"");
            if(device.getId().equalsIgnoreCase(seldevs)) {
                ret.append(" selected=\"selected\"");
            }
            ret.append(">"+space+device.getTitle()+"</option>\n");
            origList.remove(device);
            devs.remove(0);
            if(device.listChilds().hasNext()) {
                renderSelect(origList, device, ret, space+"&nbsp;&nbsp;&nbsp;", seldevs);
            }
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        final int I_ACCESS = 0;
        Resource base = paramsRequest.getResourceBase();
        StringBuffer sb_ret = new StringBuffer();
        String[] arr_month = DoArrMonth(paramsRequest);
        GregorianCalendar gc_now = new GregorianCalendar();
        HashMap hm_sites = new HashMap();
        Enumeration enum_language = null;
        Enumeration enum_device = null;
        Iterator it_repository = null;
        HashMap hm_types = null;
        String address = null;
        String rtype = null;
        String s_site = null;
        String s_color = null;
        String s_aux = null;
        String s_value = null;
        String s_tmid = null;
        String s_tmtitle = null;
        int i_access = 0;
        int i_pipe = 0;
        int i_key = 0;

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
                // Inicializa variables para catalogos
                /*String s_tm = null;
                if(null!=request.getParameter("wb_site"))
                {
                    //obtener el primer tmid
                    s_tm = request.getParameter("wb_site");
                }*/
                String s_tm = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");

                String s_repository = request.getParameter("wb_repositoryid");
                if(null == s_repository) {
                    s_repository = SWBContext.getWebSite(s_tm).getUserRepository().getDisplayTitle(paramsRequest.getUser().getLanguage());
                }

//                enum_device = DBCatalogs.getInstance().getDevices().elements();
//                it_repository = DBUser.getRepositories();
                address = paramsRequest.getTopic().getUrl();
                s_site = request.getParameter("wb_site");
                if(null==s_site) s_site=s_tm;

                // javascript
                sb_ret.append("\n<script type=\"text/javascript\">");
                sb_ret.append("\nfunction DoXml(sizze){    ");
                sb_ret.append("\n   sub_section='0';");
                sb_ret.append("\n   if(window.document.frmrep.subsection.checked==true)");
                sb_ret.append("\n       sub_section='1';");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_11=\" +window.document.frmrep.wb_hour_11.options[window.document.frmrep.wb_hour_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_11=\" +window.document.frmrep.wb_minute_11.options[window.document.frmrep.wb_minute_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_12=\" +window.document.frmrep.wb_hour_12.options[window.document.frmrep.wb_hour_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_12=\" +window.document.frmrep.wb_minute_12.options[window.document.frmrep.wb_minute_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_ipadduser=\" +window.document.frmrep.wb_ipadduser.value;");
                sb_ret.append("\n   params = params + \"&wb_ipaddserver=\" +window.document.frmrep.wb_ipaddserver.value;");
                sb_ret.append("\n   params = params + \"&wb_topicid=\" +window.document.frmrep.wb_topicid.value;");
                sb_ret.append("\n   params = params + \"&wb_userid=\" +window.document.frmrep.wb_userid.value;");
                sb_ret.append("\n   params = params + \"&wb_usertype=\" +window.document.frmrep.wb_usertype.options[window.document.frmrep.wb_usertype.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_device=\" +window.document.frmrep.wb_device.options[window.document.frmrep.wb_device.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_language=\" +window.document.frmrep.wb_language.options[window.document.frmrep.wb_language.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_resourceid=\" +window.document.frmrep.wb_resourceid.value;");
                sb_ret.append("\n   params = params + \"&wb_repositoryid=\" +window.document.frmrep.wb_repositoryid.value;");
                sb_ret.append("\n   params = params + \"&wb_sessionid=\" +window.document.frmrep.wb_sessionid.value;");
                sb_ret.append("\n   params = params + \"&wb_typedisplay=\" + GetGroupSelected();");
                sb_ret.append("\n   params = params + \"&subsection=\" + sub_section;"); //subsection
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n}");
                sb_ret.append("\nfunction DoExcel(sizze){    ");
                sb_ret.append("\n   sub_section='0';");
                sb_ret.append("\n   if(window.document.frmrep.subsection.checked==true)");
                sb_ret.append("\n       sub_section='1';");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_11=\" +window.document.frmrep.wb_hour_11.options[window.document.frmrep.wb_hour_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_11=\" +window.document.frmrep.wb_minute_11.options[window.document.frmrep.wb_minute_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_12=\" +window.document.frmrep.wb_hour_12.options[window.document.frmrep.wb_hour_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_12=\" +window.document.frmrep.wb_minute_12.options[window.document.frmrep.wb_minute_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_ipadduser=\" +window.document.frmrep.wb_ipadduser.value;");
                sb_ret.append("\n   params = params + \"&wb_ipaddserver=\" +window.document.frmrep.wb_ipaddserver.value;");
                sb_ret.append("\n   params = params + \"&wb_topicid=\" +window.document.frmrep.wb_topicid.value;");
                sb_ret.append("\n   params = params + \"&wb_userid=\" +window.document.frmrep.wb_userid.value;");
                sb_ret.append("\n   params = params + \"&wb_usertype=\" +window.document.frmrep.wb_usertype.options[window.document.frmrep.wb_usertype.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_device=\" +window.document.frmrep.wb_device.options[window.document.frmrep.wb_device.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_language=\" +window.document.frmrep.wb_language.options[window.document.frmrep.wb_language.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_resourceid=\" +window.document.frmrep.wb_resourceid.value;");
                sb_ret.append("\n   params = params + \"&wb_repositoryid=\" +window.document.frmrep.wb_repositoryid.value;");
                sb_ret.append("\n   params = params + \"&wb_sessionid=\" +window.document.frmrep.wb_sessionid.value;");
                sb_ret.append("\n   params = params + \"&wb_typedisplay=\" + GetGroupSelected();");
                sb_ret.append("\n   params = params + \"&subsection=\" + sub_section;"); //subsection
                //sb_ret.append("\n   alert('params = ' + params);");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n}");
                sb_ret.append("\nfunction DoGraph(sizze){    ");
                sb_ret.append("\n   sub_section='0';");
                sb_ret.append("\n   if(window.document.frmrep.subsection.checked==true)");
                sb_ret.append("\n       sub_section='1';");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_11=\" +window.document.frmrep.wb_hour_11.options[window.document.frmrep.wb_hour_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_11=\" +window.document.frmrep.wb_minute_11.options[window.document.frmrep.wb_minute_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_12=\" +window.document.frmrep.wb_hour_12.options[window.document.frmrep.wb_hour_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_12=\" +window.document.frmrep.wb_minute_12.options[window.document.frmrep.wb_minute_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_ipadduser=\" +window.document.frmrep.wb_ipadduser.value;");
                sb_ret.append("\n   params = params + \"&wb_ipaddserver=\" +window.document.frmrep.wb_ipaddserver.value;");
                sb_ret.append("\n   params = params + \"&wb_topicid=\" +window.document.frmrep.wb_topicid.value;");
                sb_ret.append("\n   params = params + \"&wb_userid=\" +window.document.frmrep.wb_userid.value;");
                sb_ret.append("\n   params = params + \"&wb_usertype=\" +window.document.frmrep.wb_usertype.options[window.document.frmrep.wb_usertype.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_device=\" +window.document.frmrep.wb_device.options[window.document.frmrep.wb_device.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_language=\" +window.document.frmrep.wb_language.options[window.document.frmrep.wb_language.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_resourceid=\" +window.document.frmrep.wb_resourceid.value;");
                sb_ret.append("\n   params = params + \"&wb_repositoryid=\" +window.document.frmrep.wb_repositoryid.value;");
                sb_ret.append("\n   params = params + \"&wb_sessionid=\" +window.document.frmrep.wb_sessionid.value;");
                sb_ret.append("\n   params = params + \"&wb_typedisplay=\" + GetGroupSelected();");
                sb_ret.append("\n   params = params + \"&subsection=\" + sub_section;"); //subsection
                //sb_ret.append("\n   alert('params = ' + params);");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("graph")+"\"+params,\"graphWindow\",sizze);    ");
                sb_ret.append("\n }");
                sb_ret.append("\n function GetGroupSelected(){");
                sb_ret.append("\n     var strType = \"0\";");
                sb_ret.append("\n     for(i=0;i<window.document.frmrep.wb_typedisplay.length;i++){");
                sb_ret.append("\n       if(window.document.frmrep.wb_typedisplay[i].checked==true){");
                sb_ret.append("\n           strType=window.document.frmrep.wb_typedisplay[i].value;");
                sb_ret.append("\n       }");
                sb_ret.append("\n     }");
                sb_ret.append("\n     return strType;");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoApply(){");
                sb_ret.append("\n     window.document.frmrep.submit(); ");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoPaging(pag){");
                sb_ret.append("\n     window.document.frmrep.wb_pagenum.value = pag; ");
                sb_ret.append("\n     window.document.frmrep.submit(); ");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoEvaluateYear(ind){");
                sb_ret.append("\n     if(ind == 0){");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = true;");
                sb_ret.append("\n     }");
                sb_ret.append("\n     else{");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = false;");
                sb_ret.append("\n     }");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoEvaluateMonth(ind){");
                sb_ret.append("\n     if(ind == 0){");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = true;");
                sb_ret.append("\n     }");
                sb_ret.append("\n     else{");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = false;");
                sb_ret.append("\n     }");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoRedir(val){");
                sb_ret.append("\n     window.document.frmred.wb_rtype.value = val; ");
                sb_ret.append("\n     window.document.frmred.submit(); ");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoBlockade(){");
                sb_ret.append("\n     if(window.document.frmrep.wb_rep_type[0].checked){");
                sb_ret.append("\n         window.document.frmrep.wb_year_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_year_11.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_year_12.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_month_11.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_month_12.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_11.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_12.disabled = true;");
                sb_ret.append("\n     }");
                sb_ret.append("\n     if(window.document.frmrep.wb_rep_type[1].checked){");
                sb_ret.append("\n         window.document.frmrep.wb_year_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_month_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_day_1.disabled = true;");
                sb_ret.append("\n         window.document.frmrep.wb_year_11.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_year_12.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_month_11.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_month_12.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_11.disabled = false;");
                sb_ret.append("\n         window.document.frmrep.wb_day_12.disabled = false;");
                sb_ret.append("\n     }");
                sb_ret.append("\n }");
                sb_ret.append("\n function DoRefresh(valor){");

                SWBResourceURL urlRefresh = paramsRequest.getRenderUrl();

                sb_ret.append("\n         window.location = '"+urlRefresh+"?wb_site='+valor;");
                sb_ret.append("\n }");

                sb_ret.append("\nfunction DoDetail(sizze,s_key){    ");
                sb_ret.append("\n   sub_section='0';");
                sb_ret.append("\n   if(window.document.frmrep.subsection.checked==true)");
                sb_ret.append("\n       sub_section='1';");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_11=\" +window.document.frmrep.wb_hour_11.options[window.document.frmrep.wb_hour_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_11=\" +window.document.frmrep.wb_minute_11.options[window.document.frmrep.wb_minute_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_12=\" +window.document.frmrep.wb_hour_12.options[window.document.frmrep.wb_hour_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_12=\" +window.document.frmrep.wb_minute_12.options[window.document.frmrep.wb_minute_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_ipadduser=\" +window.document.frmrep.wb_ipadduser.value;");
                sb_ret.append("\n   params = params + \"&wb_ipaddserver=\" +window.document.frmrep.wb_ipaddserver.value;");
                sb_ret.append("\n   params = params + \"&wb_topicid=\" +window.document.frmrep.wb_topicid.value;");
                sb_ret.append("\n   params = params + \"&wb_userid=\" +window.document.frmrep.wb_userid.value;");
                sb_ret.append("\n   params = params + \"&wb_usertype=\" +window.document.frmrep.wb_usertype.options[window.document.frmrep.wb_usertype.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_device=\" +window.document.frmrep.wb_device.options[window.document.frmrep.wb_device.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_language=\" +window.document.frmrep.wb_language.options[window.document.frmrep.wb_language.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_resourceid=\" +window.document.frmrep.wb_resourceid.value;");
                sb_ret.append("\n   params = params + \"&wb_repositoryid=\" +window.document.frmrep.wb_repositoryid.value;");
                sb_ret.append("\n   params = params + \"&wb_sessionid=\" +window.document.frmrep.wb_sessionid.value;");
                sb_ret.append("\n   params = params + \"&wb_typedisplay=\" + GetGroupSelected();");
                sb_ret.append("\n   params = params + \"&subsection=\" + sub_section;"); //subsection
                sb_ret.append("\n   params = params +  \"&wb_key=\" + s_key;");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_detail")+"\"+params,\"detailWindow\",sizze);    ");
                sb_ret.append("\n}");

                sb_ret.append("\nfunction DoDetailExcel(sizze,s_key){    ");
                sb_ret.append("\n   sub_section='0';");
                sb_ret.append("\n   if(window.document.frmrep.subsection.checked==true)");
                sb_ret.append("\n       sub_section='1';");
                sb_ret.append("\n   var params = \"?\";");
                sb_ret.append("\n   params = params + \"wb_site=\" + window.document.frmrep.wb_site.options[window.document.frmrep.wb_site.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_11=\" +window.document.frmrep.wb_hour_11.options[window.document.frmrep.wb_hour_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_11=\" +window.document.frmrep.wb_minute_11.options[window.document.frmrep.wb_minute_11.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_hour_12=\" +window.document.frmrep.wb_hour_12.options[window.document.frmrep.wb_hour_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_minute_12=\" +window.document.frmrep.wb_minute_12.options[window.document.frmrep.wb_minute_12.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_ipadduser=\" +window.document.frmrep.wb_ipadduser.value;");
                sb_ret.append("\n   params = params + \"&wb_ipaddserver=\" +window.document.frmrep.wb_ipaddserver.value;");
                sb_ret.append("\n   params = params + \"&wb_topicid=\" +window.document.frmrep.wb_topicid.value;");
                sb_ret.append("\n   params = params + \"&wb_userid=\" +window.document.frmrep.wb_userid.value;");
                sb_ret.append("\n   params = params + \"&wb_usertype=\" +window.document.frmrep.wb_usertype.options[window.document.frmrep.wb_usertype.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_device=\" +window.document.frmrep.wb_device.options[window.document.frmrep.wb_device.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_language=\" +window.document.frmrep.wb_language.options[window.document.frmrep.wb_language.selectedIndex].value;");
                sb_ret.append("\n   params = params + \"&wb_resourceid=\" +window.document.frmrep.wb_resourceid.value;");
                sb_ret.append("\n   params = params + \"&wb_repositoryid=\" +window.document.frmrep.wb_repositoryid.value;");
                sb_ret.append("\n   params = params + \"&wb_sessionid=\" +window.document.frmrep.wb_sessionid.value;");
                sb_ret.append("\n   params = params + \"&wb_typedisplay=\" + GetGroupSelected();"); //subsection
                sb_ret.append("\n   params = params + \"&subsection=\" + sub_section;"); //subsection
                sb_ret.append("\n   params = params + \"&type_content=excel\";");
                sb_ret.append("\n   params = params + \"&wb_key=\"+s_key;");
                sb_ret.append("\n   window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_detail")+"\"+params,\"detailWindow\",sizze);    ");
                sb_ret.append("\n}");


                sb_ret.append("\n</script>");
                sb_ret.append("\n<form method=\"Post\" action=\"" + address + "\" id=\"frmred\" name=\"frmred\">");
                sb_ret.append("\n<input type=\"hidden\" name=\"wb_rtype\" value=\"" + rtype +"\">");
                sb_ret.append("\n</form>");
                sb_ret.append("\n<form method=\"Post\" class=\"box\" action=\"" + address + "\" id=\"frmrep\" name=\"frmrep\">");
                sb_ret.append("\n<input type=\"hidden\" name=\"wb_rep_type\" value=\"1\">");
                sb_ret.append("\n<table border=\"0\" width=\"100%\">");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td colspan=\"7\">");
                // Show report description
                sb_ret.append("\n<table width=\"100%\" border=\"0\">");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td width=\"66\"><img src=\""+SWBPlatform.getContextPath()+"wbadmin/images/reportes.gif\" width=\"60\" height=\"55\"><span class=\"pietitulo\"></span>");
                sb_ret.append("</td>");
                sb_ret.append("<td width=\"893\"><p class=\"pietitulo Estilo15\"><span class=\"Estilo14\">" + paramsRequest.getLocaleString("step") + " 1 ");
                sb_ret.append(paramsRequest.getLocaleString("of") +  " 1</span>");
                sb_ret.append("<br><br>");
                sb_ret.append("<span class=\"status Estilo16 Estilo15\">");
                sb_ret.append(paramsRequest.getLocaleString("description_1"));
                sb_ret.append("</span>");
                sb_ret.append("<br></p>");
                sb_ret.append("</td>");
                sb_ret.append("</tr>");
                sb_ret.append("\n</table>");
                sb_ret.append("</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("<td class=\"datos\">"+ paramsRequest.getLocaleString("site") +"</td>");
                sb_ret.append("\n<td colspan=\"2\">");
                sb_ret.append("<select name=\"wb_site\" onChange=\"DoRefresh(this.value);\">");
                Iterator<String> itKeys = hm_sites.keySet().iterator();
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    sb_ret.append("<option value=\"" + key + "\"");
                    if(key.equalsIgnoreCase(s_tm)) {
                        sb_ret.append(" selected=\"selected\"");
                    }
                    sb_ret.append(">" + (String)hm_sites.get(key) + "</option>");
                }
                sb_ret.append("</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("\n<td colspan=\"3\">");
                sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\">&nbsp;");
                sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"Excel\" name=\"btnExcel\">&nbsp;");
                sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoGraph('width=600, height=550, scrollbars, resizable')\" value=\"" + paramsRequest.getLocaleString("graph") + "\" name=\"btnGraph\">&nbsp;");
                sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\">");
                sb_ret.append("</td>");
                sb_ret.append("\n</tr>");

                // Receive parameters
                String s_rep_type = request.getParameter("wb_rep_type");
                String s_year_11 = request.getParameter("wb_year_11");
                String s_month_11 = request.getParameter("wb_month_11");
                String s_day_11 = request.getParameter("wb_day_11");
                String s_hour_11 = request.getParameter("wb_hour_11");
                String s_minute_11 = request.getParameter("wb_minute_11");
                String s_second_11 = request.getParameter("wb_second_11");
                String s_year_12 = request.getParameter("wb_year_12");
                String s_month_12 = request.getParameter("wb_month_12");
                String s_day_12 = request.getParameter("wb_day_12");
                String s_hour_12 = request.getParameter("wb_hour_12");
                String s_minute_12 = request.getParameter("wb_minute_12");
                String s_second_12 = request.getParameter("wb_second_12");
                String s_ipadduser = request.getParameter("wb_ipadduser");
                if(s_ipadduser == null) s_ipadduser="";
                String s_ipaddserver = request.getParameter("wb_ipaddserver");
                if(s_ipaddserver == null) s_ipaddserver="";
                String s_topicid = request.getParameter("wb_topicid");
                if(s_topicid == null) s_topicid="";
                String s_userid = request.getParameter("wb_userid");
                if(s_userid == null) s_userid="";
                String s_languagesel = request.getParameter("wb_language");
                if (s_languagesel == null) s_languagesel = "";
                String s_devicesel = request.getParameter("wb_device");
                if(s_devicesel == null) s_devicesel="0";
                String s_usersel = request.getParameter("wb_usertype");
                if(s_usersel == null) s_usersel="";
                String s_resourceid = request.getParameter("wb_resourceid");
                if(s_resourceid == null) s_resourceid="";
                String s_typedisplay = request.getParameter("wb_typedisplay");
                if(s_typedisplay == null) s_typedisplay="2";
                String s_repositoryid = request.getParameter("wb_repositoryid");
                if(s_repositoryid == null) s_repositoryid="0";
                String s_sessionid = request.getParameter("wb_sessionid");
                if(s_sessionid == null) s_sessionid="";

                // Receive parameters

                // Asign value to parameters
                if(s_rep_type == null) s_rep_type = "0";
                if(s_year_11 == null) s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
                if(s_month_11 == null) s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                if(s_day_11 == null) s_day_11 = Integer.toString(I_START_DAY);
                if(s_hour_11 == null) s_hour_11 = "0";
                if(s_minute_11 == null) s_minute_11 = "0";
                if(s_second_11 == null) s_second_11 = "0";
                if(s_year_12 == null) s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
                if(s_month_12 == null) s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                if(s_day_12 == null) s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
                if(s_hour_12 == null) s_hour_12 = "23";
                if(s_minute_12 == null) s_minute_12 = "59";
                if(s_second_12 == null) s_second_12 = "59";
                // Asign value to  parameters


                sb_ret.append("\n<tr>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("<td colspan=\"6\" class=\"datos\">" + paramsRequest.getLocaleString("by_interval_date") + "</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;<select name=\"wb_year_11\">");
                for(int i=2000;i<2021;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_year_11) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_11\">");
                for(int i=0; i<= arr_month.length - 1;i++)
                {
                    sb_ret.append("\n<option value=\"" + (i + 1) +"\"");
                    if(Integer.parseInt(s_month_11) == (i + 1))
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + arr_month[i] + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_11\">");
                for(int i=1;i<32;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_day_11) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("hour") + "&nbsp;<select name=\"wb_hour_11\"");
                sb_ret.append(">");
                for(int i=0;i<24;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_hour_11) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("minute") + "&nbsp;<select name=\"wb_minute_11\"");
                sb_ret.append(">");
                for(int i=0;i<60;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_minute_11) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;<select name=\"wb_year_12\">");
                for(int i=2000;i<2021;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_year_12) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_12\">");
                for(int i=0; i<= arr_month.length - 1;i++)
                {
                    sb_ret.append("\n<option value=\"" + (i + 1) +"\"");
                    if(Integer.parseInt(s_month_12) == (i + 1))
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + arr_month[i] + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_12\">");
                for(int i=1;i<32;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_day_12) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("hour") + "&nbsp;<select name=\"wb_hour_12\"");
                sb_ret.append(">");
                for(int i=0;i<24;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_hour_12) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("minute") + "&nbsp;<select name=\"wb_minute_12\"");
                sb_ret.append(">");
                for(int i=0;i<60;i++)
                {
                    sb_ret.append("\n<option value=\"" + i +"\"");
                    if(Integer.parseInt(s_minute_12) == i)
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + i + "</option>");
                }
                sb_ret.append("\n</select>");
                sb_ret.append("\n</td>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("<td colspan=\"6\">");
                sb_ret.append("\n<table border=\"0\" width=\"100%\">");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("ipaddress_user") + "</td>");
                sb_ret.append("<td class=\"datos\"><input type=\"text\" value=\"" + s_ipadduser + "\" name=\"wb_ipadduser\" size=\"20\" ></td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("ipaddress_server") + "</td>");
                sb_ret.append("<td class=\"datos\"><input type=\"text\" value=\"" + s_ipaddserver + "\" name=\"wb_ipaddserver\" size=\"20\" ></td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("sectionid") + "</td>");
                sb_ret.append("<td class=\"datos\"><input type=\"text\" value=\"" + s_topicid + "\" name=\"wb_topicid\" size=\"20\" >");
                String s_checked = "";
                if(null!=request.getParameter("subsection")&&"1".equals(request.getParameter("subsection")))
                {
                    s_checked = "checked";
                }
                sb_ret.append("<input type=checkbox name=\"subsection\" value=1 "+s_checked+">&nbsp;"+paramsRequest.getLocaleString("txt_subsections"));
                sb_ret.append("</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("user") + "</td>");
                sb_ret.append("<td class=\"datos\"><input type=\"text\" value=\"" + s_userid + "\" name=\"wb_userid\" size=\"20\" ></td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("user_type") + "</td>");

                // USER TYPES ASOCIATED TO THE SELECTED TOPICMAP

                sb_ret.append("<td class=\"datos\"><select name=\"wb_usertype\"><option value=\"0\"></option>");
                hm_types = getUserType(s_repository, paramsRequest);
                for(int i = 0; i < hm_types.size(); i++)
                {
                    s_aux = (String) hm_types.get(Integer.toString(i));
                    i_pipe = s_aux.indexOf("|");
                    String s_val = s_aux.substring(0,i_pipe);
                    String s_des = s_aux.substring(i_pipe + 1, s_aux.length());
                    sb_ret.append("<option value=\""+ s_val + "\"");
                    if(s_usersel.equals(s_val))
                    {
                        sb_ret.append(" selected");
                    }
                    sb_ret.append(">" + s_des + "</option>");
                }
                sb_ret.append("</select></td>");

                //

                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("device") + "</td>");

                // DEVICES
                String space="";
                sb_ret.append("<td class=\"datos\">");
                ArrayList<Device> devs=new ArrayList<Device>();
                Iterator<Device> itDevices = SWBContext.getWebSite(s_tm).listDevices();
                while (itDevices.hasNext()) {
                    devs.add(itDevices.next());
                }
                devs.trimToSize();
                sb_ret.append("<select id=\"wb_device\" name=\"wb_device\">\n");
                sb_ret.append("<option value=\"0\"></option>");
                while(!devs.isEmpty()) {
                    Device device = devs.get(0);
                    sb_ret.append("<option value=\""+device.getId()+"\"");
                    if(device.getId().equalsIgnoreCase(s_devicesel)) {
                        sb_ret.append(" selected=\"selected\"");
                    }
                    sb_ret.append(">"+space+device.getTitle()+"</option>\n");
                    devs.remove(0);
                    if(device.listChilds().hasNext()) {
                        renderSelect(devs, device, sb_ret, space+"&nbsp;&nbsp;&nbsp;", s_devicesel);
                    }
                }
                sb_ret.append("</select>\n");
                sb_ret.append("</td>");

//                sb_ret.append("<td class=\"datos\"><select name=\"wb_device\"><option value=\"0\"></option>");
//                while(enum_device.hasMoreElements())
//                {
//                    RecDevice rec_device = (RecDevice)enum_device.nextElement();
//                    sb_ret.append("<option value=\""+ rec_device.getId()+ "\"");
//                    if(Integer.parseInt(s_devicesel) == rec_device.getId())
//                    {
//                        sb_ret.append(" selected");
//                    }
//                    sb_ret.append(">" + rec_device.getName() + "</option>");
//                }
//                sb_ret.append("</select></td>");


                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("language") + "</td>");

                sb_ret.append("<td class=\"datos\">");
                sb_ret.append("<select id=\"wb_language\" name=\"wb_language\" size=\"1\">");
                sb_ret.append("<option value=\"0\"></option>");
                Iterator<Language> itLanguages = SWBContext.getWebSite(s_tm).listLanguages();
                while (itLanguages.hasNext()) {
                    Language language = itLanguages.next();
                    sb_ret.append("<option value=\"" + language.getId() + "\">"+language.getDisplayTitle(paramsRequest.getUser().getLanguage())+"</option>");
                }
                sb_ret.append("</select>");
                sb_ret.append("</td>");
                // TOPICMAP LANGUAGES
//                enum_language = DBCatalogs.getInstance().getLanguages(s_tm).elements();
//
//                sb_ret.append("<td class=\"datos\"><select name=\"wb_language\"><option value=\"0\"></option>");
//                while(enum_language.hasMoreElements())
//                {
//                    RecLanguage rec_language = (RecLanguage)enum_language.nextElement();
//                    sb_ret.append("<option value=\""+ rec_language.getLang()+ "\"");
//                    if(s_languagesel.equals(rec_language.getLang()))
//                    {
//                        sb_ret.append(" selected");
//                    }
//                    sb_ret.append(">" + rec_language.getTitle() + "</option>");
//                }
//                sb_ret.append("</select></td>");

                //

                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("resource") + "</td>");
                sb_ret.append("<td class=\"datos\"><input type=\"text\" value=\"" + s_resourceid + "\" name=\"wb_resourceid\" size=\"4\" ></td>");
                sb_ret.append("\n</tr>");

                sb_ret.append("\n<tr>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("repository") + "</td>");


                // USER REPOSITORY

                sb_ret.append("<td class=\"datos\"><input type=hidden name=\"wb_repositoryid\" value=\""+s_repository+"\">"+s_repository);
                /*sb_ret.append("<td class=\"datos\"><input type=hidden name=\"wb_repositoryid\" value=\""+SWBContext.getWebSite(s_tm).getUserRepository().getId()+"\">"+s_repository);*/

                sb_ret.append("</td>");
                sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("session") + "</td>");
                sb_ret.append("<td class=\"datos\"><input type=\"text\" value=\"" + s_sessionid + "\" name=\"wb_sessionid\" size=\"20\" ></td>");
                sb_ret.append("\n</tr>");

                sb_ret.append("\n</table>");
                sb_ret.append("\n</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td>&nbsp;</td>");
                sb_ret.append("<td colspan=2 class=\"datos\">" + paramsRequest.getLocaleString("group_by")+ ":</td>");
                sb_ret.append("<td colspan=4 class=\"datos\">");
                sb_ret.append("&nbsp;<br><input type=\"radio\" value=\"0\" name=\"wb_typedisplay\"");
                if(s_typedisplay.equals("0")) sb_ret.append(" checked");
                sb_ret.append(">&nbsp;" + paramsRequest.getLocaleString("by_year") + "");
                sb_ret.append("&nbsp;<br><input type=\"radio\" value=\"1\" name=\"wb_typedisplay\"");
                if(s_typedisplay.equals("1")) sb_ret.append(" checked");
                sb_ret.append(">&nbsp;" + paramsRequest.getLocaleString("by_month") + "");
                sb_ret.append("&nbsp;<br><input type=\"radio\" value=\"2\" name=\"wb_typedisplay\"");
                if(s_typedisplay.equals("2")) sb_ret.append(" checked");
                sb_ret.append(">&nbsp;" + paramsRequest.getLocaleString("by_day") + "");
                sb_ret.append("&nbsp;<br><input type=\"radio\" value=\"3\" name=\"wb_typedisplay\"");
                if(s_typedisplay.equals("3")) sb_ret.append(" checked");
                sb_ret.append(">&nbsp;" + paramsRequest.getLocaleString("by_hour") + "");
                sb_ret.append("</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n<tr>");
                sb_ret.append("<td colspan=\"7\">");
                if(request.getParameter("wb_rep_type") == null)
                {
                    sb_ret.append("&nbsp;");
                }
                else
                {
                    ArrayList ar_pag = getReportResults(request, paramsRequest);
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
                    }
                }
                sb_ret.append("\n</td>");
                sb_ret.append("\n</tr>");
                sb_ret.append("\n</table></form>");
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

    public void doDetail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        ArrayList ar_pag = getReportResults(request, paramsRequest);
        String s_key = request.getParameter("wb_key");

        String s_c_type = request.getParameter("type_content");

        if(null!=s_c_type&&"excel".equals(s_c_type))
            response.setContentType("application/vnd.ms-excel");
        else
            response.setContentType("text/html");
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>"+"Detalle Log Accesos"+"</TITLE>");
        out.println("<STYLE>");
        out.println(".menu {");
        out.println("color: #000000;");
        out.println("text-decoration: none;");
        out.println("font-family: Verdana;");
        out.println("font-size: 11px;");
        out.println("}");
        out.println(".titulo {");
        out.println("	color: #428AD4;");
        out.println("	text-decoration: none;");
        out.println("	font-family: Verdana;");
        out.println("	font-size: 14px;");
        out.println("	font-weight: bold;");
        out.println("}");
        out.println(".datos {");
        out.println("	color: #333333;");
        out.println("	text-decoration: none;");
        out.println("	font-family: Verdana;");
        out.println("	font-size: 12px;");
        out.println("}");
        out.println(".valores {");
        out.println("	color: #000000;");
        out.println("	text-decoration: none;");
        out.println("	font-family: Verdana;");
        out.println("	font-size: 12px;");
        out.println("}");
        out.println(".link {");
        out.println("	color: #0000FF;");
        out.println("	text-decoration: none;");
        out.println("	font-family: Verdana;");
        out.println("	font-size: 12px;");
        out.println("}");
        out.println(".tabla {");
        out.println("	color: #428AD4;");
        out.println("	text-decoration: none;");
        out.println("	font-family: Verdana;");
        out.println("	font-size: 11px;");
        out.println("}");
        out.println(".boton{");
        out.println("	color:#FFFFFF;");
        out.println("	background-color:#2A5216;");
        out.println("	border-color:#77AE4D;");
        out.println("	FONT-FAMILY: verdana,arial,helvetica,sans-serif;");
        out.println("	FONT-SIZE: 10pt;");
        out.println("}");
        out.println(".box {");
        out.println("  width: 95%;");
        out.println("  background: #FFFFFF;");
        out.println("  color : #000000;");
        out.println("  font-family : Verdana;");
        out.println("  font-size: 12px;");
        out.println("  padding : 5px;");
        out.println("  margin: 10px;");
        out.println("  border-color : #cccccc;");
        out.println("  border-style : solid;");
        out.println("  border-width : 1px;");
        out.println(" }");
        out.println("</STYLE>");
        out.println("</HEAD>");
        out.println("<BODY>");

        if(null!=hm_detail)
        {
                if(null!=s_key)
                {
                    Vector vec_rep = (Vector) hm_detail.get(s_key);
                    if(null!=vec_rep)
                    {
                        if(!vec_rep.isEmpty())
                        {
                            out.println("<table cellpadding=5 cellspacing=0 width=100% border=0 class=box>");
                            out.println("<tr class=titulo>");
                            out.println("<td colspan=14>");
                            out.println("Detalle de Accesos"+" - "+s_key);
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr class=tabla>");
                            out.println("<td>");
                            out.println(paramsRequest.getLocaleString("th_Num"));
                            out.println("</td>");
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
                            out.println("<td>"+paramsRequest.getLocaleString("th_Time")+" (ms.)"+"</td>");
                            out.println("<td>"+paramsRequest.getLocaleString("th_Resource")+"</td>");
                            out.println("</tr>");

                            String rowColor="";
                            boolean cambiaColor = true;

                            Iterator ite_rep = vec_rep.iterator();
                            int i=0;
                            while(ite_rep.hasNext())
                                //for(int i=0; i<vec_rep.size();i++)
                            {
                                StringTokenizer s_token = new StringTokenizer((String)ite_rep.next(),"|");
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
                                    String s_time = s_token.nextToken();
                                    String s_rec = "";

                                    while(s_token.hasMoreTokens())
                                    {
                                        s_rec += s_token.nextToken();
                                        if(s_token.hasMoreTokens())
                                            s_rec += ", ";
                                    }

                                    rowColor = "#EFEDEC";
                                    if(!cambiaColor)
                                        rowColor = "#FFFFFF";
                                    cambiaColor = !cambiaColor;

                                    out.println("<tr class=datos bgcolor=\"" + rowColor + "\" >");
                                    out.println("<td>"+(i+1)+"</td>");
                                    out.println("<td>"+s_date+"</td>");
                                    out.println("<td>"+s_ipuser+"</td>");
                                    out.println("<td>"+s_ipserver+"</td>");
                                    out.println("<td>"+s_session+"</td>");
                                    out.println("<td>"+s_tm+"</td>");
                                    out.println("<td>"+s_tp+"</td>");
                                    out.println("<td>"+s_repo+"</td>");
                                    out.println("<td>"+s_iduser+"</td>");
                                    out.println("<td>"+s_usrtype+"</td>");
                                    String s_dev_title = s_device;
//                                    if(null!=s_device&&!s_device.equals("null")) s_dev_title = DBCatalogs.getInstance().getDevice(Integer.parseInt(s_device)).getName();
                                    out.println("<td>"+s_dev_title+"</td>");
                                    out.println("<td>"+s_language+"</td>");
                                    out.println("<td>"+s_time+"</td>");
                                    out.println("<td>"+s_rec+"</td>");
                                    out.println("</tr>");
                                    i++;

                            }
                            out.println("</table>");
                        }
                    }
                }
        }
        out.println("</BODY>");
        out.println("</HTML>");
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
        ArrayList ar_pag = null;
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
            i_len = ar_pag.size();
            s_title = paramsRequest.getLocaleString("access_log_report");
            sb_app.append("\n<param name=\"Title\" value=\"" + s_title + "\">");
            sb_app.append("\n<param name=\"ndata\" value=\""+ i_len +"\">");
            for(int j=0;j<i_len;j++)
            {
                //RecResHits recResHits=(RecResHits)ar_pag.get(j);
                String[] arr_data = (String[])ar_pag.get(j);
                //String s_date = recResHits.getdate().toString();
                sb_app.append("\n<param name=\"label" + j + "\" value=\""+ arr_data[0] +"\">");
                sb_app.append("\n<param name=\"data" + j + "\" value=\"" + arr_data[1] + "\">");
                b_record = true;
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
        ArrayList ar_pag = null;
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
            i_len = ar_pag.size();
            if(i_len > 0)
            {
                out.println("\n<table border=\"0\" width=\"100%\">");
                out.println("\n<tr><td colspan=\"2\">&nbsp;</td></tr>");
                out.println("\n<tr>");
                out.println("\n<th align=\"center\">" + paramsRequest.getLocaleString("date") + "</th>");
                out.println("\n<th align=\"center\">" + paramsRequest.getLocaleString("access") + "</th>");
                out.println("\n</tr>");
                for(int j=0;j<i_len;j++)
                {
                    String[] arr_data = (String[])ar_pag.get(j);
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
        ArrayList ar_pag = null;
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
            i_len = ar_pag.size();
            report.setAttribute("total",Integer.toString(i_len));
            for(int j=0;j<i_len;j++)
            {
                String[] arr_data = (String[])ar_pag.get(j);
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
    public ArrayList getFileNames(HttpServletRequest request)
    {
        ArrayList al_files = new ArrayList();
        GregorianCalendar gc_now = new GregorianCalendar();
        GregorianCalendar gc_first = null;
        GregorianCalendar gc_last = null;

        String s_accesslog = SWBPlatform.getEnv("swb/accessLog");
        String s_period = SWBPlatform.getEnv("swb/accessLogPeriod");
        String s_path = SWBPlatform.getWorkPath();

        String s_year_11 = request.getParameter("wb_year_11");
        String s_month_11 = request.getParameter("wb_month_11");
        String s_day_11 = request.getParameter("wb_day_11");
        String s_hour_11 = request.getParameter("wb_hour_11");
        String s_minute_11 = request.getParameter("wb_minute_11");
        String s_year_12 = request.getParameter("wb_year_12");
        String s_month_12 = request.getParameter("wb_month_12");
        String s_day_12 = request.getParameter("wb_day_12");
        String s_hour_12 = request.getParameter("wb_hour_12");
        String s_minute_12 = request.getParameter("wb_minute_12");

        String s_site = request.getParameter("wb_site");
        String s_auxyear = null;
        String s_auxmonth = null;
        String s_auxday = null;
        boolean b_validdate = false;
        boolean b_day = false;

        if(s_year_11 == null) s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_11 == null) s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_month_11.length() == 1) s_month_11 = "0" + s_month_11;
        if(s_day_11 == null) s_day_11 = Integer.toString(I_START_DAY);

        if(s_year_12 == null) s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
        if(s_month_12 == null) s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
        if(s_month_12.length() == 1) s_month_12 = "0" + s_month_12;
        if(s_day_12 == null) s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));

        int i_year1 = Integer.parseInt(s_year_11);
        int i_year2 = Integer.parseInt(s_year_12);
        int i_month1 = Integer.parseInt(s_month_11);
        int i_month2 = Integer.parseInt(s_month_12);
        int i_day1 = Integer.parseInt(s_day_11);
        int i_day2 = Integer.parseInt(s_day_12);
        int i_hour1 = Integer.parseInt(s_hour_11);
        int i_hour2 = Integer.parseInt(s_hour_12);
        int i_minute1 = Integer.parseInt(s_minute_11);
        int i_minute2 = Integer.parseInt(s_minute_12);

        gc_first = new GregorianCalendar(i_year1,i_month1,i_day1,i_hour1,i_minute1);
        gc_last = new GregorianCalendar(i_year2,i_month2,i_day2,i_hour2,i_minute2);

        //Evaluates if dates are correct
        if(gc_first.before(gc_last)) b_validdate = true;
        else
        {
            if(gc_first.equals(gc_last)) b_validdate = true;
        }

        if(b_validdate && s_site != null)
        {
            s_accesslog = s_accesslog + "_" + s_site + "_acc";
            //Period by day
            if(s_period.equals("daily"))
            {
                if(i_year1 <= i_year2)
                {
                    while(i_year1 <= i_year2)
                    {
                        s_auxyear = Integer.toString(i_year1);
                        if(i_year1 != i_year2)
                        {
                            while(i_month1 <= 12)
                            {
                                s_auxmonth = Integer.toString(i_month1);
                                if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                while(i_day1 <= 31)
                                {
                                    s_auxday = Integer.toString(i_day1);
                                    if(s_auxday.length() == 1) s_auxday = "0"+s_auxday;
                                    al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                    //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                    i_day1++;
                                }
                                i_day1 = 1;
                                b_day = true;
                                i_month1 ++;
                            }
                            i_month1 = 1;
                        }
                        else
                        {
                            if(i_month1 <= i_month2)
                            {
                                while(i_month1 <= i_month2)
                                {
                                    if(i_month1 != i_month2)
                                    {
                                        s_auxmonth = Integer.toString(i_month1);
                                        if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                        while(i_day1 <= 31)
                                        {
                                            s_auxday = Integer.toString(i_day1);
                                            if(s_auxday.length() == 1) s_auxday = "0"+s_auxday;
                                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            i_day1++;
                                        }
                                        i_day1 = 1;
                                        b_day = true;
                                    }
                                    else
                                    {
                                        if(b_day)
                                        {
                                            i_day1 = 1;
                                        }
                                        else
                                        {
                                            i_day1 = Integer.parseInt(s_day_11);
                                        }
                                        s_auxmonth = Integer.toString(i_month1);
                                        if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                        while(i_day1 <= i_day2)
                                        {
                                            s_auxday = Integer.toString(i_day1);
                                            if(s_auxday.length() == 1) s_auxday = "0"+s_auxday;
                                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + "-" + s_auxday + ".log");
                                            i_day1++;
                                        }
                                    }
                                    i_month1++;
                                }
                            }
                        }
                        i_year1 ++;
                    }
                }
            }

            //Period by month
            if(s_period.equals("monthly"))
            {
                if(i_year1 <= i_year2)
                {
                    while(i_year1 <= i_year2)
                    {
                        s_auxyear = Integer.toString(i_year1);
                        if(i_year1 != i_year2)
                        {
                            while(i_month1 <= 12)
                            {
                                s_auxmonth = Integer.toString(i_month1);
                                if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                i_month1 ++;
                            }
                            i_month1 = 1;
                        }
                        else
                        {
                            while(i_month1 <= i_month2)
                            {
                                if(i_month1 != i_month2)
                                {
                                    s_auxmonth = Integer.toString(i_month1);
                                    if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                    //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                    al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                }
                                else
                                {
                                    s_auxmonth = Integer.toString(i_month1);
                                    if(s_auxmonth.length() == 1) s_auxmonth = "0"+s_auxmonth;
                                    //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                    al_files.add(s_path + s_accesslog + "." + s_auxyear + "-" + s_auxmonth + ".log");
                                }
                                i_month1 ++;
                            }
                        }
                        i_year1 ++;
                    }
                }
            }
            //Period by year
            if(s_period.equals("yearly"))
            {
                if(i_year1 <= i_year2)
                {
                    while(i_year1 <= i_year2)
                    {
                        s_auxyear = Integer.toString(i_year1);
                        if(i_year1 != i_year2)
                        {
                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + ".log");
                            al_files.add(s_path + s_accesslog + "." + s_auxyear + ".log");
                        }
                        else
                        {
                            //System.out.println("Es el archivo " + s_path + s_accesslog + "." + s_auxyear + ".log");
                            al_files.add(s_path + s_accesslog + "." + s_auxyear + ".log");
                        }
                        i_year1 ++;
                    }
                }

            }
        }
        System.out.println("archivo="+Arrays.toString(al_files.toArray()));
        
        return al_files;
    }

    /**
     * @param request
     * @param paramsRequest
     * @return
     */
    public ArrayList getReportResults(HttpServletRequest request, SWBParamRequest paramsRequest)
    {
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
        GregorianCalendar gc_first = null;
        GregorianCalendar gc_last = null;
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
        String s_site = request.getParameter("wb_site");
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
        String s_rep_type = request.getParameter("wb_rep_type");
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
        String s_minute_12 = request.getParameter("wb_minute_12");

        String s_ipadduser = request.getParameter("wb_ipadduser");
        if(s_ipadduser == null) s_ipadduser = "";
        if(!s_ipadduser.trim().equals("")) b_ipadduser = true;

        String s_ipaddserver = request.getParameter("wb_ipaddserver");
        if(s_ipaddserver == null) s_ipaddserver="";
        if(!s_ipaddserver.trim().equals("")) b_ipaddserver = true;

        String s_sessionid = request.getParameter("wb_sessionid");
        if(s_sessionid == null) s_sessionid = "";
        if(!s_sessionid.trim().equals("")) b_sessionid = true;

        String s_subsection = request.getParameter("subsection");
        if(null==s_subsection) s_subsection="0";

        String s_topicid = request.getParameter("wb_topicid");
        if(s_topicid == null) s_topicid = "";
        if(!s_topicid.trim().equals(""))
        {
            b_topicid = true;
            o_tpp = o_tm.getWebPage(s_topicid);
        }

        String s_repositoryid = request.getParameter("wb_repositoryid");
        if(s_repositoryid == null) s_repositoryid = "0";
        if(!s_repositoryid.trim().equals("0")) b_repositoryid = true;
        System.out.println("recogiendo datos... repositorio="+s_repositoryid);

        String s_userid = request.getParameter("wb_userid");
        if(s_userid == null) s_userid = "";
        if(!s_userid.trim().equals("")) b_userid = true;

        String s_languagesel = request.getParameter("wb_language");
        if(s_languagesel == null) s_languagesel = "0";
        if(!s_languagesel.trim().equals("0")) b_languagesel = true;

        String s_devicesel = request.getParameter("wb_device");
        if(s_devicesel == null) s_devicesel = "0";
        if(!s_devicesel.trim().equals("0")) b_devicesel = true;

        String s_usersel = request.getParameter("wb_usertype");
        if(s_usersel == null) s_usersel = "0";
        if(!s_usersel.trim().equals("0")) b_usersel = true;

        String s_resourceid = request.getParameter("wb_resourceid");
        if(s_resourceid == null) s_resourceid = "";
        if(!s_resourceid.trim().equals("")) b_resourceid = true;

        String s_typedisplay = request.getParameter("wb_typedisplay");
        if(s_typedisplay == null) s_typedisplay="2";

        String s_time = request.getParameter("wb_time");
        if(s_time == null) s_time= "";
        // Receive parameters

        // Asign value to parameters
        if(s_rep_type == null) s_rep_type = "0";
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
        if(s_minute_12 == null) s_minute_12 = "59";
        // Asign value to  parameters

        //boolean b_topic = false;
        //int s_rec_add=0;
        if(rtype == null) rtype="0";
        s_period = SWBPlatform.getEnv("swb/accessLogPeriod");
        try
        {
            al_files = getFileNames(request);
            i_lenfiles = al_files.size();
            if(i_lenfiles > I_ZERO)
            {
                gc_first = new GregorianCalendar(Integer.parseInt(s_year_11),Integer.parseInt(s_month_11),Integer.parseInt(s_day_11),Integer.parseInt(s_hour_11),Integer.parseInt(s_minute_11));
                gc_last = new GregorianCalendar(Integer.parseInt(s_year_12),Integer.parseInt(s_month_12),Integer.parseInt(s_day_12),Integer.parseInt(s_hour_12),Integer.parseInt(s_minute_12));
                String s_key=null;
                Vector vec_rep = null;
                //Get data from files
                for(int j=0;j<al_files.size();j++)
                {
                    s_filename = (String)al_files.get(j);
                    File f = new File(s_filename);
                    if(f.exists())
                    {
                        System.out.println("el archivo "+s_filename+" exite");
                        RandomAccessFile rf_in = new RandomAccessFile(f,"r");
                        gc_datedefault = null;
                        l_count = 0;
                        while((s_line = rf_in.readLine()) != null)
                        {
                            StringTokenizer st_line = new StringTokenizer(s_line,"|");
                            String s_linea = s_line;
                            i++;
                            i_row = 0;
                            i_len = 0;
                            while(st_line.hasMoreElements())
                            {
                                //s_aux receives date
                                s_aux = st_line.nextToken();
                                System.out.println("date="+s_aux);
                                if(i_row == I_ZERO)
                                {
                                    s_yearfile = s_aux.substring(0,4);
                                    s_monthfile = s_aux.substring(5,7);
                                    s_dayfile = s_aux.substring(8,10);
                                    s_hourfile = s_aux.substring(11,13);
                                    s_minfile = s_aux.substring(14,16);
                                    gc_datefile = new GregorianCalendar(Integer.parseInt(s_yearfile),Integer.parseInt(s_monthfile),Integer.parseInt(s_dayfile),Integer.parseInt(s_hourfile),Integer.parseInt(s_minfile));
                                    //Evaluates if dates are correct
                                    if((gc_datefile.after(gc_first) | gc_datefile.equals(gc_first)) & ((gc_datefile.before(gc_last) | gc_datefile.equals(gc_last))))
                                    {
                                        b_result = true;
                                        s_datefile = s_aux;
                                    }
                                    else
                                    {
                                        b_result = false;
                                    }
                                    gc_datefile = null;
                                }
                                //If not b_result does nothing
                                if(b_result)
                                {
                                    System.out.println("\nseguimos calculando...");
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives user ip
                                    s_aux = st_line.nextToken();
                                    System.out.println("user ip="+s_aux);
                                    if((b_result) && ((i_row == I_ONE) && b_ipadduser))
                                    {
                                        if(s_aux.equals(s_ipadduser.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else b_result = false;
                                        System.out.println("user ip------");
                                    }
                                    
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives server ip
                                    s_aux = st_line.nextToken();
                                    System.out.println("server ip="+s_aux);
                                    if((b_result) && ((i_row == I_TWO) && b_ipaddserver))
                                    {
                                        if(s_aux.equals(s_ipaddserver.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else b_result = false;
                                        System.out.println("server ip------");
                                    }
                                    
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives session id
                                    s_aux = st_line.nextToken();
                                    System.out.println("session="+s_aux);
                                    if((b_result) && ((i_row == I_THREE) && b_sessionid))
                                    {
                                        if(s_aux.equals(s_sessionid.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else b_result = false;
                                        System.out.println("session id------");
                                    }
                                    
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives topicmap id, leave out this value and increase i_len
                                    s_aux = st_line.nextToken();
                                    System.out.println("1-topic o sitio="+s_aux);
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    //s_aux receives topic id
                                    s_aux = st_line.nextToken();
                                    System.out.println("section="+s_aux);

                                    o_tp=o_tm.getWebPage(s_aux);

                                    if(((b_result) && ((i_row == I_FOUR) && b_topicid)))
                                    {
                                        if(s_aux.equals(s_topicid.trim())||(null!=s_subsection && "1".equals(s_subsection) && null!=o_tp &&o_tpp.isParentof(o_tp)))
                                        {
                                            //System.out.print("tp: "+s_aux);
                                            b_result = true;
                                            //b_topic = true;
                                        }
                                        else b_result = false;
                                        System.out.println("subsections------");
                                    }

                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives repository id
                                    s_aux = st_line.nextToken();
                                    System.out.println("repository="+s_aux);
                                    if((b_result) && ((i_row == I_FIVE) && b_repositoryid))
                                    {
                                        System.out.println("i_five="+I_FIVE);
                                        System.out.println("b_repositoryid="+b_repositoryid);
                                        System.out.println("s_repositoryid="+s_repositoryid);
                                        if(s_aux.equals(s_repositoryid.trim()))
                                        {
                                            System.out.println("true");
                                            b_result = true;
                                        }/*else {
                                            System.out.println("false");
                                            b_result = false;
                                        }*/
                                        System.out.println("repository------");
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives user id
                                    s_aux = st_line.nextToken();
                                    System.out.println("user="+s_aux);
                                    if((b_result) && ((i_row == I_SIX) && b_userid))
                                    {
                                        if(s_aux.equals(s_userid.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else b_result = false;
                                        System.out.println("user id------");
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives user type
                                    s_aux = st_line.nextToken();
                                    System.out.println("user type="+s_aux);
                                    if((b_result) && ((i_row == I_SEVEN) && b_usersel))
                                    {
                                        if(s_aux.equals(s_usersel.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else b_result = false;
                                        System.out.println("user type------");
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives device type
                                    s_aux = st_line.nextToken();
                                    System.out.println("device="+s_aux);
                                    if((b_result) && ((i_row == I_EIGHT) && b_devicesel))
                                    {
                                        if(s_aux.equals(s_devicesel.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else b_result = false;
                                        System.out.println("device------");
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    //s_aux receives language
                                    s_aux = st_line.nextToken();
                                    System.out.println("language="+s_aux);
                                    System.out.println("0 b_result="+b_result);
                                    if((b_result) && ((i_row == I_NINE) && b_languagesel))
                                    {
                                        if(s_aux.equals(s_languagesel.trim()))
                                        {
                                            b_result = true;
                                        }
                                        else b_result = false;
                                        System.out.println("language------");
                                    }
                                    i_len = i_len + s_aux.length() + I_ONE;
                                    i_row++;
                                    // Check if next token is resource
                                    System.out.println("1 b_result="+b_result);
                                    if((b_result) && (i_row == I_TEN) )
                                    {
                                        s_timeaux = st_line.nextToken();
                                        System.out.println("time="+s_timeaux);
                                        if(!s_timeaux.equals(null) && b_time)
                                        {
                                            System.out.println("2 b_result="+b_result);
                                            if(s_timeaux.equals(s_time.trim()))
                                            {
                                                b_result = true;
                                            }
                                            else b_result = false;
                                            System.out.println("3 b_result="+b_result);
                                        }
                                        if((b_resourceid) && (s_line.length() >= i_len - I_ONE))
                                        {
                                            i_new = 1;
                                            s_aux = s_line.substring(i_len, s_line.length());
                                            int i_pos = s_aux.indexOf("|");
                                            b_result = false;
                                            System.out.println("4 b_result="+b_result);
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
                                                    System.out.println("5 b_result="+b_result);
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            }

                            //Evaluates if record passes
                            if(b_result)
                            {
                                System.out.println("si paso todo.......");
                                //Display by year
                                if(s_typedisplay.equals("0"))
                                {
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
                                }
                                //Display by month
                                if(s_typedisplay.equals("1"))
                                {
                                    System.out.println("display by month");
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
                                }
                                //Display by day
                                if(s_typedisplay.equals("2"))
                                {
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
                                }
                                //Display by hour
                                if(s_typedisplay.equals("3"))
                                {
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
                                    if(s_typedisplay.equals("0"))
                                    {
                                        s_key = s_line.substring(0,4);
                                    }
                                    else if(s_typedisplay.equals("1"))
                                    {
                                        s_key = s_line.substring(0,7);
                                    }
                                    else if(s_typedisplay.equals("2"))
                                    {
                                        s_key = s_line.substring(0,10);
                                    }
                                    else if(s_typedisplay.equals("3"))
                                    {
                                        s_key = s_line.substring(0,13);
                                        int i_horaini = Integer.parseInt(s_datedefault.substring(11,13));
                                        s_key +=":00-"+(i_horaini+1)+":00";
                                    }

                                vec_rep = (Vector) hm_detail.get(s_key);
                                if(null == vec_rep)
                                {
                                    vec_rep=new Vector();
                                }
                                System.out.println("s_line="+s_line);
                                vec_rep.add(s_line);
                               // s_rec_add++;
                                hm_detail.put(s_key,vec_rep);
                            }
                        }
                        if((i_new == I_ONE) | (l_count != I_ZERO))
                        {
                            arr_data = new String[2];
                            if(s_typedisplay.equals("0"))
                                arr_data[0] = s_datedefault.substring(0,4);

                            if(s_typedisplay.equals("1"))
                                arr_data[0] = s_datedefault.substring(0,7);

                            if(s_typedisplay.equals("2"))
                                arr_data[0] = s_datedefault.substring(0,10);

                            if(s_typedisplay.equals("3"))
                            {
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
            if(s_typedisplay.equals("0") | s_typedisplay.equals("1"))
            {
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
                            l_count = Integer.parseInt(arr_dataaux[1]);
                            s_year = arr_dataaux[0];
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
            //System.out.println("total de archivos .... " + i_lenfiles);
        }catch (Exception e) {
            log.error("Error on method getReportResults() resource " + strRscType + " with id " +  getResourceBase().getId(), e);
        }
//        System.out.println("recs: "+s_rec_add);
        return al_pag;
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
