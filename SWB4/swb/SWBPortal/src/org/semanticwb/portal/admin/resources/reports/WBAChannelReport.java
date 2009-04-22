package org.semanticwb.portal.admin.resources.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.db.SWBRecHits_;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.access.RecResHits;

import org.semanticwb.portal.db.SWBRecHit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import sun.security.krb5.internal.crypto.e;

public class WBAChannelReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBAChannelReport.class);


    private final int I_REPORT_TYPE = 3;   // Type 4 of reports "Channel access"
    private final int I_START_DAY = 1;
    public String strRscType;
    private int i_index;
    private HashMap hmAc = null, hmCounted=null;
    private long hitsUncountables = 0;
    private int counter = 0;

    @Override
    public void init(){
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBAChannelReport";
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if (paramsRequest.getMode().equals("graph")) {
            doGraph(request, response, paramsRequest);
        }
        else if (paramsRequest.getMode().equals("report_xml")) {
            doRepXml(request, response, paramsRequest);
        } else if (paramsRequest.getMode().equals("download")) {
            doDownload(request, response, paramsRequest);
        } else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        final int I_ACCESS = 0;
        Resource base = getResourceBase();
        StringBuffer sb_ret = new StringBuffer();
        ReportMgr rm = ReportMgr.getInstance();
        if (null != rm && !rm.isRunning()) {
            String[] arr_month = doArrMonth(paramsRequest);
            GregorianCalendar gc_now = new GregorianCalendar();
            HashMap hm_sites = new HashMap();
            HashMap hm_sites2 = new HashMap();
            String address = null;
            String s_site = null;
            String s_tmid = null;
            String s_tmtitle = null;
            String s_value = null;
            int i_key = 0;
            int i_access = 0;

            String s_rfilter = request.getParameter("wb_rfilter");
            String s_level = request.getParameter("wb_showlevel");
            if (s_level == null) {
                s_level = "1";
            }
            s_site = request.getParameter("wb_site");
            try {
                // Evaluates if there are sites
                /*Iterator it_topic = TopicMgr.getInstance().getTopicMaps();
                while (it_topic.hasNext()) {
                    TopicMap tm_map = (TopicMap) it_topic.next();
                    //Evaluates if TopicMap is not Global
                    if (!TopicMgr.getInstance().getGlobalTopicMap().toString().equals(tm_map.getDbdata().getId())) {
                        //Get access level of this user on this topicmap and if level is greater than "0" then user have access
                        i_access = AdmFilterMgr.getInstance().haveAccess2TopicMap(paramsRequest.getUser(), tm_map.getDbdata().getId());
                        if (I_ACCESS < i_access) {
                            if (tm_map.getDbdata().getDeleted() == 0) {
                                s_value = tm_map.getDbdata().getId() + "|" + tm_map.getDbdata().getTitle();
                                hm_sites.put(Integer.toString(i_key), s_value);
                                hm_sites2.put(tm_map.getDbdata().getId(), tm_map.getDbdata().getId());
                                i_key++;
                            }
                        }
                    }
                }*/
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
                if (hm_sites.size() > I_ACCESS) {
                    address = paramsRequest.getTopic().getUrl();

                    String webSite = request.getParameter("wb_site");

                    int groupDates;
                    try {
                        groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
                    }catch(NumberFormatException e) {
                        groupDates = 0;
                    }
                    String fecha1 = request.getParameter("wb_fecha1")==null ? "":request.getParameter("wb_fecha1");
                    String fecha11 = request.getParameter("wb_fecha11")==null ? "":request.getParameter("wb_fecha11");
                    String fecha12 = request.getParameter("wb_fecha12")==null ? "":request.getParameter("wb_fecha12");




                    sb_ret.append("<script type=\"text/javascript\">\n");
                    sb_ret.append("function doXml(sizze) {\n");
                    sb_ret.append("   var params = \"?\";\n");
                    sb_ret.append("   params = params + \"wb_site=\" + window.document.frmrep.wb_site.value;\n");
                    sb_ret.append("   params = params + \"&wb_year_1=\" +window.document.frmrep.wb_year_1.options[window.document.frmrep.wb_year_1.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_month_1=\" +window.document.frmrep.wb_month_1.options[window.document.frmrep.wb_month_1.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_day_1=\" +window.document.frmrep.wb_day_1.options[window.document.frmrep.wb_day_1.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_rep_type=\" + GetTypeSelected();\n");
                    sb_ret.append("   params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;\n");
                    sb_ret.append("   params = params + \"&wb_showlevel=\" +window.document.frmrep.wb_showlevel.value;\n");
                    sb_ret.append("   window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml") + "\"+params,\"graphWindow\",sizze);\n");
                    sb_ret.append("}\n");

                    sb_ret.append("function doExcel(sizze) {\n");
                    sb_ret.append("   var params = \"?\";\n");
                    sb_ret.append("   params = params + \"wb_site=\" + window.document.frmrep.wb_site.value;\n");
                    sb_ret.append("   params = params + \"&wb_year_1=\" +window.document.frmrep.wb_year_1.options[window.document.frmrep.wb_year_1.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_month_1=\" +window.document.frmrep.wb_month_1.options[window.document.frmrep.wb_month_1.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_day_1=\" +window.document.frmrep.wb_day_1.options[window.document.frmrep.wb_day_1.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_year_11=\" +window.document.frmrep.wb_year_11.options[window.document.frmrep.wb_year_11.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_month_11=\" +window.document.frmrep.wb_month_11.options[window.document.frmrep.wb_month_11.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_day_11=\" +window.document.frmrep.wb_day_11.options[window.document.frmrep.wb_day_11.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_year_12=\" +window.document.frmrep.wb_year_12.options[window.document.frmrep.wb_year_12.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_month_12=\" +window.document.frmrep.wb_month_12.options[window.document.frmrep.wb_month_12.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_day_12=\" +window.document.frmrep.wb_day_12.options[window.document.frmrep.wb_day_12.selectedIndex].value;\n");
                    sb_ret.append("   params = params + \"&wb_rep_type=\" + GetTypeSelected();\n");
                    sb_ret.append("   params = params + \"&wb_rtype=\" + window.document.frmred.wb_rtype.value;\n");
                    sb_ret.append("   params = params + \"&wb_showlevel=\" +window.document.frmrep.wb_showlevel.value;\n");
                    sb_ret.append("   window.open(\"" + paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_excel") + "\"+params,\"graphWindow\",sizze);\n");
                    sb_ret.append("}\n");

                    sb_ret.append("function GetTypeSelected() {\n");
                    sb_ret.append("   var strType = \"0\";\n");
                    sb_ret.append("   for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){\n");
                    sb_ret.append("      if(window.document.frmrep.wb_rep_type[i].checked==true){\n");
                    sb_ret.append("         strType=window.document.frmrep.wb_rep_type[i].value;\n");
                    sb_ret.append("      }\n");
                    sb_ret.append("   }\n");
                    sb_ret.append("   return strType;\n");
                    sb_ret.append("}\n");

                    sb_ret.append("function doApply() {\n");
                    sb_ret.append("   tmp = window.document.frmrep.filename.value;\n");
                    sb_ret.append("   tmp2=tmp.replace(/ /g,'');\n");
                    sb_ret.append("   if(tmp2.length==0) {\n");
                    sb_ret.append("      alert('" + paramsRequest.getLocaleString("fileName") + "')\n");
                    sb_ret.append("      window.document.frmrep.filename.select();\n");
                    sb_ret.append("      window.document.frmrep.filename.focus();\n");
                    sb_ret.append("      return false;\n");
                    sb_ret.append("   }else {\n");
                    sb_ret.append("      window.document.frmrep.submit();\n");
                    sb_ret.append("   }\n");
                    sb_ret.append("}\n");

                    sb_ret.append("function DoPaging(pag) {\n");
                    sb_ret.append("   window.document.frmrep.wb_pagenum.value = pag;\n");
                    sb_ret.append("   window.document.frmrep.submit();\n");
                    sb_ret.append("}\n");

                    sb_ret.append("function DoEvaluateYear(ind) {\n");
                    sb_ret.append("   if(ind == 0) {\n");
                    sb_ret.append("      window.document.frmrep.wb_month_1.disabled = true;\n");
                    sb_ret.append("      window.document.frmrep.wb_day_1.disabled = true;\n");
                    sb_ret.append("   }else {\n");
                    sb_ret.append("      window.document.frmrep.wb_month_1.disabled = false;\n");
                    sb_ret.append("      window.document.frmrep.wb_day_1.disabled = false;\n");
                    sb_ret.append("   }\n");
                    sb_ret.append("}");

                    sb_ret.append("function DoEvaluateMonth(ind) {\n");
                    sb_ret.append("   if(ind == 0){\n");
                    sb_ret.append("      window.document.frmrep.wb_day_1.disabled = true;\n");
                    sb_ret.append("   }else {\n");
                    sb_ret.append("      window.document.frmrep.wb_day_1.disabled = false;\n");
                    sb_ret.append("   }\n");
                    sb_ret.append("}\n");

                    sb_ret.append("function DoBlockade() {\n");
                    sb_ret.append("       if(window.document.frmrep.wb_rep_type[0].checked){\n");
                    sb_ret.append("           window.document.frmrep.wb_year_1.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_month_1.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_day_1.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_year_11.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_year_12.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_month_11.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_month_12.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_day_11.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_day_12.disabled = true;\n");
                    sb_ret.append("       }\n");
                    sb_ret.append("       if(window.document.frmrep.wb_rep_type[1].checked){\n");
                    sb_ret.append("           window.document.frmrep.wb_year_1.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_month_1.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_day_1.disabled = true;\n");
                    sb_ret.append("           window.document.frmrep.wb_year_11.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_year_12.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_month_11.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_month_12.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_day_11.disabled = false;\n");
                    sb_ret.append("           window.document.frmrep.wb_day_12.disabled = false;\n");
                    sb_ret.append("       }\n");
                    sb_ret.append("   }\n");
                    
                    sb_ret.append("function DoDownload(name,key){\n");
                    sb_ret.append("   window.location='"+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("download")+"?filename='+name+'&key='+key;\n");
                    sb_ret.append("}\n");

                    sb_ret.append("</script>\n");
                    // javascript

                    /*sb_ret.append("\n<form method=\"Post\" action=\"" + address + "\" id=\"frmred\" name=\"frmred\">");
                    sb_ret.append("<input type=\"hidden\" name=\"wb_rtype\" value=\"\"><input type=\"hidden\" name=\"wb_rfilter\" value=\"" + s_rfilter + "\">");
                    sb_ret.append("\n</form>");
                    sb_ret.append("\n<form method=\"Post\" action=\"" + address + "\" id=\"frmret\" name=\"frmret\">");
                    sb_ret.append("<input type=\"hidden\" name=\"wb_selectedfilter\" value=\"" + s_rfilter + "\"><input type=\"hidden\" name=\"wb_selectedsite\" value=\"" + s_site + "\">");
                    sb_ret.append("\n</form>");*/

                    sb_ret.append("<div class=\"swbform\">\n");
                    sb_ret.append("<fieldset>\n");
                    sb_ret.append("<legend>" + paramsRequest.getLocaleString("channel_report") + "</legend>\n");

                    SWBResourceURL url = paramsRequest.getActionUrl();

                    sb_ret.append("<form name=\"frmrep\" id=\"frmrep\" method=\"post\" action=\""+url+"\">\n");
                    sb_ret.append("<table border=\"0\" width=\"95%\" align=\"center\">\n");
                    sb_ret.append("<tr><td width=\"183\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"443\"></td></tr>\n");

                    /*sb_ret.append("<tr>");
                    sb_ret.append("<td colspan=\"4\">");
                    sb_ret.append("<table width=\"100%\" border=\"0\">");
                    sb_ret.append("<tr>");
                    sb_ret.append("<td width=\"66\"><img src=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/images/reportes.gif\" width=\"60\" height=\"55\"><span class=\"pietitulo\"></span>");
                    sb_ret.append("</td>");
                    sb_ret.append("<td width=\"893\"><p class=\"pietitulo Estilo15\"><span class=\"Estilo14\">" + paramsRequest.getLocaleString("step") + " 1 " + paramsRequest.getLocaleString("of") + " 1</span>");
                    sb_ret.append("<br><br>");
                    sb_ret.append("<span class=\"status Estilo16 Estilo15\">");
                    sb_ret.append(paramsRequest.getLocaleString("description"));
                    sb_ret.append("</span>");
                    sb_ret.append("<br></p>");
                    sb_ret.append("</td>");
                    sb_ret.append("</tr>");
                    sb_ret.append("\n</table>");
                    sb_ret.append("</td>");
                    sb_ret.append("</tr>");*/

                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td colspan=\"4\">&nbsp;<input type=\"hidden\" name=\"wb_rfilter\" value=\"" + s_rfilter + "\"></td>\n");
                    sb_ret.append("</tr>\n");

                    /*sb_ret.append("\n<tr>");
                    sb_ret.append("<td colspan=\"3\">&nbsp;</td>");
                    sb_ret.append("<td>");
                    sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\">&nbsp;");
                    sb_ret.append("<input type=\"button\" class=\"boton\" onClick=\"DoApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\">&nbsp;");
                    sb_ret.append("</td>");
                    sb_ret.append("\n</tr>");*/
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr>\n");
                    sb_ret.append(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;\n");
                    sb_ret.append("   <input type=\"button\" onclick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\" />&nbsp;\n");
                    sb_ret.append("   <input type=\"button\" onclick=\"doApply()\" value=\"" + paramsRequest.getLocaleString("apply") + "\" name=\"btnApply\" />\n");
                    sb_ret.append(" </td>\n");
                    sb_ret.append("</tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");

                    /*sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("site") + "</td>");
                    sb_ret.append("<td>");
                    sb_ret.append("\n<select name=\"wb_site\">");
                    for (int i = 0; i < hm_sites.size(); i++) {
                        s_value = (String) hm_sites.get(Integer.toString(i));
                        i_key = s_value.indexOf("|");
                        s_tmid = s_value.substring(0, i_key);
                        s_tmtitle = s_value.substring(i_key + 1, s_value.length());
                        sb_ret.append("\n<option value=\"" + s_tmid + "\"");
                        if (s_site != null) {
                            if (s_site.equals(s_tmid)) {
                                sb_ret.append(" selected");
                            }
                        }
                        sb_ret.append(">" + s_tmtitle + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n<td class=\"datos\">&nbsp;");
                    sb_ret.append("\n<input type=hidden name=\"wb_showlevel\" value=\"10\">");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");*/
                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td>" + paramsRequest.getLocaleString("site") + ":</td>\n");
                    sb_ret.append("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" size=\"1\">\n");
                    Iterator<String> itKeys = hm_sites.keySet().iterator();
                    while(itKeys.hasNext()) {
                        String key = itKeys.next();
                        sb_ret.append("<option value=\"" + key + "\"");
                        if(key.equalsIgnoreCase(webSite)) {
                            sb_ret.append(" selected=\"selected\"");
                        }
                        sb_ret.append(">" + (String)hm_sites.get(key) + "</option>\n");
                    }
                    sb_ret.append("</select>\n");
                    sb_ret.append("</td>\n");
                    sb_ret.append("<td>&nbsp;</td>\n");
                    sb_ret.append("</tr>\n");

                    /*
                    // Receive parameters
                    String s_rep_type = request.getParameter("wb_rep_type");
                    String s_year_1 = request.getParameter("wb_year_1");
                    String s_month_1 = request.getParameter("wb_month_1");
                    String s_day_1 = request.getParameter("wb_day_1");
                    String s_year_11 = request.getParameter("wb_year_11");
                    String s_month_11 = request.getParameter("wb_month_11");
                    String s_day_11 = request.getParameter("wb_day_11");
                    String s_year_12 = request.getParameter("wb_year_12");
                    String s_month_12 = request.getParameter("wb_month_12");
                    String s_day_12 = request.getParameter("wb_day_12");

                    // Asign value to parameters
                    if (s_rep_type == null) {
                        s_rep_type = "0";
                    }
                    if (s_year_1 == null) {
                        s_year_1 = Integer.toString(gc_now.get(Calendar.YEAR));
                    }
                    if (s_month_1 == null) {
                        s_month_1 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                    }
                    if (s_day_1 == null) {
                        s_day_1 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
                    }
                    if (s_year_11 == null) {
                        s_year_11 = Integer.toString(gc_now.get(Calendar.YEAR));
                    }
                    if (s_month_11 == null) {
                        s_month_11 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                    }
                    if (s_day_11 == null) {
                        s_day_11 = Integer.toString(I_START_DAY);
                    }
                    if (s_year_12 == null) {
                        s_year_12 = Integer.toString(gc_now.get(Calendar.YEAR));
                    }
                    if (s_month_12 == null) {
                        s_month_12 = Integer.toString(gc_now.get(Calendar.MONTH) + 1);
                    }
                    if (s_day_12 == null) {
                        s_day_12 = Integer.toString(gc_now.get(Calendar.DAY_OF_MONTH));
                    // Asign value to  parameters
                    }
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td colspan=\"3\" class=\"datos\"><input type=\"radio\" value=\"0\" name=\"wb_rep_type\" onclick=\"javascript: DoBlockade();\"");
                    if (s_rep_type.equals("0")) {
                        sb_ret.append(" checked");
                    }
                    sb_ret.append(">" + paramsRequest.getLocaleString("by_day") + "</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;<select name=\"wb_year_1\" onchange=\"javascript: DoEvaluateYear(this.selectedIndex);\"");
                    if (s_rep_type.equals("1")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append("><option value=\"0\">" + paramsRequest.getLocaleString("all") + "</option>");
                    for (int i = 2000; i < 2021; i++) {
                        sb_ret.append("<option value=\"" + i + "\"");
                        if ((Integer.parseInt(s_year_1) == i)) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_1\" onchange=\"javascript: DoEvaluateMonth(this.selectedIndex);\"");
                    if (s_rep_type.equals("1")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append("><option value=\"0\">" + paramsRequest.getLocaleString("all") + "</option>");
                    for (int i = 0; i <= arr_month.length - 1; i++) {
                        sb_ret.append("<option value=\"" + (i + 1) + "\"");
                        if (Integer.parseInt(s_month_1) == (i + 1)) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + arr_month[i] + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_1\"");
                    if (s_rep_type.equals("1")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append("><option value=\"0\">" + paramsRequest.getLocaleString("all") + "</option>");
                    for (int i = 1; i < 32; i++) {
                        sb_ret.append("<option value=\"" + i + "\"");
                        if (Integer.parseInt(s_day_1) == i) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td colspan=\"4\">&nbsp;</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td colspan=\"3\" class=\"datos\"><input type=\"radio\" value=\"1\" name=\"wb_rep_type\" onclick=\"javascript: DoBlockade();\"");
                    if (s_rep_type.equals("1")) {
                        sb_ret.append(" checked");
                    }
                    sb_ret.append(">" + paramsRequest.getLocaleString("by_interval_dates") + "</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;<select name=\"wb_year_11\"");
                    if (s_rep_type.equals("0")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for (int i = 2000; i < 2021; i++) {
                        sb_ret.append("<option value=\"" + i + "\"");
                        if (Integer.parseInt(s_year_11) == i) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_11\"");
                    if (s_rep_type.equals("0")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for (int i = 0; i <= arr_month.length - 1; i++) {
                        sb_ret.append("<option value=\"" + (i + 1) + "\"");
                        if (Integer.parseInt(s_month_11) == (i + 1)) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + arr_month[i] + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_11\"");
                    if (s_rep_type.equals("0")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for (int i = 1; i < 32; i++) {
                        sb_ret.append("<option value=\"" + i + "\"");
                        if (Integer.parseInt(s_day_11) == i) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("year") + "&nbsp;<select name=\"wb_year_12\"");
                    if (s_rep_type.equals("0")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for (int i = 2000; i < 2021; i++) {
                        sb_ret.append("<option value=\"" + i + "\"");
                        if (Integer.parseInt(s_year_12) == i) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("month") + "&nbsp;<select name=\"wb_month_12\"");
                    if (s_rep_type.equals("0")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for (int i = 0; i <= arr_month.length - 1; i++) {
                        sb_ret.append("<option value=\"" + (i + 1) + "\"");
                        if (Integer.parseInt(s_month_12) == (i + 1)) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + arr_month[i] + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("day") + "&nbsp;<select name=\"wb_day_12\"");
                    if (s_rep_type.equals("0")) {
                        sb_ret.append("disabled=\"true\"");
                    }
                    sb_ret.append(">");
                    for (int i = 1; i < 32; i++) {
                        sb_ret.append("<option value=\"" + i + "\"");
                        if (Integer.parseInt(s_day_12) == i) {
                            sb_ret.append(" selected");
                        }
                        sb_ret.append(">" + i + "</option>");
                    }
                    sb_ret.append("\n</select>");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");
                    sb_ret.append("\n<tr>");
                    sb_ret.append("\n<td class=datos align=right colspan=4>");
                    sb_ret.append("\n&nbsp;");
                    sb_ret.append("\n</td>");
                    sb_ret.append("\n</tr>");*/
                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td>\n");
                    sb_ret.append("<label>\n");
                    sb_ret.append("<input type=\"radio\" value=\"0\" name=\"wb_rep_type\" id=\"wb_rep_type_0\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates==0) {
                        sb_ret.append(" checked=\"checked\"");
                    }
                    sb_ret.append(" />\n");
                    sb_ret.append("&nbsp;" + paramsRequest.getLocaleString("by_day"));
                    sb_ret.append("</label></td>\n");
                    sb_ret.append("<td colspan=\"2\">\n");

                    sb_ret.append("<input type=\"text\" name=\"wb_fecha1\" id=\"wb_fecha1\" dojoType=\"dijit.form.DateTextBox\" constraints=\"{datePattern:'dd/MM/yyyy'}\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha1+"\"/>\n");
                    //sb_ret.append("<input type=\"text\" id=\"wb_fecha1\" name=\"wb_fecha1\" size=\"10\" maxlength=\"10\" value=\"" + fecha1 + "\" />");

                    sb_ret.append("</td>\n");
                    sb_ret.append("<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"0\" /></td>\n");
                    sb_ret.append("</tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");

                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td>\n");
                    sb_ret.append("<label>\n");
                    sb_ret.append("<input type=\"radio\" value=\"1\" name=\"wb_rep_type\" id=\"wb_rep_type_1\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates!=0) {
                        sb_ret.append(" checked=\"checked\"");
                    }
                    sb_ret.append(" />\n");
                    sb_ret.append("&nbsp;" + paramsRequest.getLocaleString("by_interval_dates"));
                    sb_ret.append("</label></td>\n");
                    sb_ret.append("<td>\n");
                    sb_ret.append("<input type=\"text\" name=\"wb_fecha11\" id=\"wb_fecha11\" dojoType=\"dijit.form.DateTextBox\" constraints=\"{datePattern:'dd/MM/yyyy'}\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha11+"\"/>\n");
                    sb_ret.append("</td>");
                    sb_ret.append("<td>");
                    sb_ret.append("<input type=\"text\" name=\"wb_fecha12\" id=\"wb_fecha12\" dojoType=\"dijit.form.DateTextBox\" constraints=\"{datePattern:'dd/MM/yyyy'}\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\""+fecha12+"\"/>\n");
                    sb_ret.append("</td>\n");
                    sb_ret.append("<td>&nbsp;</td>\n");
                    sb_ret.append("</tr>\n");

                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td>\n");
                    sb_ret.append(paramsRequest.getLocaleString("reportName") + ":");
                    sb_ret.append("</td>\n");
                    sb_ret.append("<td colspan=\"3\">\n");
                    sb_ret.append("<input type=text name=\"filename\" value=\"\">\n");
                    sb_ret.append("&nbsp;<font size=1>* " + paramsRequest.getLocaleString("reportFileGenerated") + " (xls)</font>\n");
                    sb_ret.append("</td>\n");
                    sb_ret.append("</tr>\n");

                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td colspan=\"4\">\n");
                    sb_ret.append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">\n");
                    HashMap hm = getFileList(paramsRequest.getTopic());
                    if (hm.size() == 0) {
                        sb_ret.append("<tr><td class=tabla>" + paramsRequest.getLocaleString("noGeneratedReports") + "</td></tr>\n");
                    } else {
                        sb_ret.append("<tr><td class=tabla>" + paramsRequest.getLocaleString("listReports") + "</td></tr>\n");
                        sb_ret.append("<tr class=tabla><td>" + paramsRequest.getLocaleString("tdAction") + "</td><td>" + paramsRequest.getLocaleString("tdFileName") + "</td><td>" + paramsRequest.getLocaleString("tdSite") + "</td><td>" + paramsRequest.getLocaleString("tdCreated") + "</td></tr>\n");
                        String rowColor = "";
                        boolean cambiaColor = true;
                        Iterator itf = hm.keySet().iterator();
                        while (itf.hasNext()) {
                            String key = (String) itf.next();
                            String sfn = (String) hm.get(key);
                            String site = sfn.substring(0, sfn.lastIndexOf("|"));
                            String filename = sfn.substring(sfn.lastIndexOf("|") + 1);
                            if (null != hm_sites2.get(site)) {
                                rowColor = "#EFEDEC";
                                if (!cambiaColor) {
                                    rowColor = "#FFFFFF";
                                }
                                cambiaColor = !(cambiaColor);
                                sb_ret.append("<tr class=datos bgcolor=\"" + rowColor + "\">\n");
                                SWBResourceURL urlDel = paramsRequest.getActionUrl();
                                urlDel.setAction("remove");
                                urlDel.setParameter("key", key);
                                sb_ret.append("<td><a href=\"" + urlDel + "\" onclick=\" if(confirm('" + paramsRequest.getLocaleString("alertRemoveFile") + "?')){return true;}else{return false};\"><img border=0 src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/eliminar.gif\" alt=\"" + paramsRequest.getLocaleString("deleteReport") + "\"></a></td>\n");
                                sb_ret.append("<td><a class=link href=\"javascript:DoDownload('" + filename + "','" + key + "');\" alt=\"Reporte: " + filename + "\">" + filename + "</a></td>\n");
                                sb_ret.append("<td>" + site + "</td><td>" + SWBUtils.TEXT.iso8601DateFormat(new java.sql.Timestamp(Long.parseLong(key))) + "</td></tr>\n");
                            }
                        }
                    }
                    sb_ret.append("</table>\n");
                    sb_ret.append("</td></tr>\n");
                    sb_ret.append("</td>\n");
                    sb_ret.append("</tr>\n");
                    sb_ret.append("</table></form>\n");
                } else {   // There are not sites and displays a message
                    sb_ret.append("<form method=\"Post\" class=\"box\" action=\"" + paramsRequest.getTopic().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">\n");
                    sb_ret.append("<table border=0 width=\"100%\">\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td>&nbsp;</td>\n");
                    sb_ret.append("<td colspan=\"2\" align=\"center\" class=\"datos\">" + paramsRequest.getLocaleString("no_sites_found") + "</td>\n");
                    sb_ret.append("<td>&nbsp;</td>\n");
                    sb_ret.append("</tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("</table></form>\n");
                }
            } catch (Exception e) {
                log.error("Error on method DoView() resource " + " " + strRscType + " " + "with id" + " " + base.getId(),e);
            }
        } else {

            DateFormat df = null;

            ReportGenerator rgen = ReportMgr.getInstance().getGenerator();
            sb_ret.append("<table width=100% cellpadding=5 cellspacing=0 class=box>");
            sb_ret.append("<tr><td class=tabla colspan=2>");
            sb_ret.append(paramsRequest.getLocaleString("msgTitleChRep"));
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td class=datos width=200 align=right>");
            sb_ret.append(paramsRequest.getLocaleString("msgStatus") + ":</td><td class=valores>" + rgen.getStatus());
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td class=datos width=200 align=right>");
            Timestamp ts_time = new Timestamp(rgen.getInitTime());
            String s_time = "" + ts_time.getHours() + ":" + ts_time.getMinutes() + ":" + ts_time.getSeconds();
            df = DateFormat.getTimeInstance(DateFormat.FULL);
            s_time = df.format(ts_time);
            sb_ret.append(paramsRequest.getLocaleString("msgSTime") + ":</td><td class=valores>" + s_time);
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td class=datos width=200 align=right>");
            sb_ret.append(paramsRequest.getLocaleString("msgProcTime") + ":</td><td class=valores>" + getAvailableTime(ts_time));
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td class=datos width=200 align=right>");
            sb_ret.append(paramsRequest.getLocaleString("msgLogCounter") + ":</td><td class=valores>" + rgen.getCounter());
            sb_ret.append("</td></tr>");
            sb_ret.append("</table>");

            sb_ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"3;URL=" + paramsRequest.getRenderUrl().toString() + "\">");
        }
        response.getWriter().print(sb_ret.toString());
    }

    public String getAvailableTime(Timestamp inicio) {
        StringBuffer ret = new StringBuffer();
        Timestamp min_actual = inicio;
        Timestamp limite = new java.sql.Timestamp(System.currentTimeMillis());

        ret.append("\n<script language=\"JavaScript\">");
        ret.append("\nCountActive = true;");
        ret.append("\nDisplayFormat = \"%%H%%:%%M%%:%%S%%\";");
        ret.append("\nfunction calcage(secs, num1, num2) {");
        ret.append("\n  s = ((Math.floor(secs/num1))%num2).toString();");
        ret.append("\n  if (s.length < 2)");
        ret.append("\n    s = \"0\" + s;");
        ret.append("\n  return \"<b>\" + s + \"</b>\";");
        ret.append("\n}");

        ret.append("\nfunction CountBack(secs) {");
        ret.append("\n  DisplayStr = DisplayFormat.replace(/%%D%%/g, calcage(secs,86400,100000));");
        ret.append("\n  DisplayStr = DisplayStr.replace(/%%H%%/g, calcage(secs,3600,24));");
        ret.append("\n  DisplayStr = DisplayStr.replace(/%%M%%/g, calcage(secs,60,60));");
        ret.append("\n  DisplayStr = DisplayStr.replace(/%%S%%/g, calcage(secs,1,60));");
        ret.append("\n");
        ret.append("\n  document.getElementById(\"cntdwn\").innerHTML = DisplayStr;");
        ret.append("\n  if (CountActive)");
        ret.append("\n    setTimeout(\"CountBack(\" + (secs+1) + \")\", 990);");
        ret.append("\n}");

        ret.append("\nfunction showTimer() {");
        ret.append("\n document.write(\"<span id='cntdwn' class=valores></span>\");");
        ret.append("\n}");
        ret.append("\nshowTimer();");
        ret.append("\nvar dthen = new Date();");
        ret.append("\n dthen.setTime(" + limite.getTime() + ");");
        ret.append("\nvar dnow = new Date();");
        ret.append("\n dnow.setTime(" + min_actual.getTime() + ");");
        ret.append("\nddiff = new Date(dthen-dnow);");
        ret.append("\ngsecs = Math.floor(ddiff.valueOf()/1000);");
        ret.append("\nCountBack(gsecs);");
        ret.append("\n</script>");

        return ret.toString();
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.getWriter().print("");
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    public void doRepXml(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = paramsRequest.getResourceBase();
        ArrayList ar_pag = null;
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document dom = null;
        String s_section = null;
        String s_result = null;
        String s_acumulated = null;
        String s_site = null;
        String s_level = null;
        this.hmAc = new HashMap();
        s_level = request.getParameter("wb_showlevel");
        if (s_level == null) {
            s_level = "1";
        }
        s_site = request.getParameter("wb_site");

        // Receive parameters
        String s_rep_type = request.getParameter("wb_rep_type");

        String s_year_ini = request.getParameter("wb_year_1") != null ? request.getParameter("wb_year_1") : "0";
        String s_month_ini = request.getParameter("wb_month_1") != null ? request.getParameter("wb_month_1") : "0";
        String s_day_ini = request.getParameter("wb_day_1") != null ? request.getParameter("wb_day_1") : "0";
        String s_year_fin = request.getParameter("wb_year_1") != null ? request.getParameter("wb_year_1") : "0";
        String s_month_fin = request.getParameter("wb_month_1") != null ? request.getParameter("wb_month_1") : "0";
        String s_day_fin = request.getParameter("wb_day_1") != null ? request.getParameter("wb_day_1") : "0";
        String s_filename = request.getParameter("filename");

        if ("1".equals(s_rep_type)) {
            s_year_ini = request.getParameter("wb_year_11");
            s_month_ini = request.getParameter("wb_month_11");
            s_day_ini = request.getParameter("wb_day_11");
            s_year_fin = request.getParameter("wb_year_12");
            s_month_fin = request.getParameter("wb_month_12");
            s_day_fin = request.getParameter("wb_day_12");
        } else if ("0".equals(s_rep_type)) {

                s_year_ini = request.getParameter("wb_year_1");
            s_month_ini = request.getParameter("wb_month_1");
            s_day_ini = request.getParameter("wb_day_1");
            s_year_fin = s_year_ini;
            s_month_fin = s_month_ini;
            s_day_fin = s_day_ini;

            if (s_day_ini != null && s_day_ini.equals("0")) {
                HashMap hm = new HashMap();
                hm.put("1", "31");
                hm.put("2", "28");
                hm.put("3", "31");
                hm.put("4", "30");
                hm.put("5", "31");
                hm.put("6", "30");
                hm.put("7", "31");
                hm.put("8", "31");
                hm.put("9", "30");
                hm.put("10", "31");
                hm.put("11", "30");
                hm.put("12", "31");

                if (s_month_fin != null && s_month_fin.equals("2")) {
                    s_day_fin = (Integer.parseInt(s_year_fin) % 4) == 0 ? "29" : "28";
                } else {
                    s_day_fin = (String) hm.get(s_month_fin.trim());
                }
                s_month_fin = s_month_ini;
                s_day_ini = "1";

            }
            else if (s_year_ini != null && s_year_ini.equals("0")) {
                s_day_ini = "0";
                s_month_ini = "0";
                s_day_fin = "0";
                s_month_fin = "0";
            }
            else if (s_year_ini != null && !s_year_ini.equals("0"))
            {
                s_day_ini = "1";
                s_month_ini = "1";
                s_day_fin = "31";
                s_month_fin = "12";
            }


        }



        hmCounted = new HashMap();
        int i_size = 0;
        try {
            s_site = request.getParameter("wb_site");
            ar_pag = getReportResults(s_site,I_REPORT_TYPE, Integer.parseInt(s_year_ini),Integer.parseInt(s_month_ini),Integer.parseInt(s_day_ini),Integer.parseInt(s_year_fin),Integer.parseInt(s_month_fin),Integer.parseInt(s_day_fin));
            hmCounted = new HashMap();
            //paramsRequest.getTopic().getMap().update2DB();
            i_size = ar_pag.size();
            //s_home = TopicMgr.getInstance().getTopicMap(s_site).getHome().getDisplayName();

            WebSite tm = SWBContext.getWebSite(s_site);

            // Starts xml document
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            Element report = dom.createElement("chn_report");
            dom.appendChild(report);
            report.setAttribute("total", Integer.toString(i_size));
            for (int j = 0; j < i_size; j++) {
                String[] arr_data = (String[]) ar_pag.get(j);
                s_section = arr_data[0]; //displayname
                s_result = arr_data[1]; // paginas
                s_acumulated = arr_data[2]; //acumulado
                s_level = arr_data[3]; // nivel
                String s_sectionid = arr_data[4]; //section ID
                String s_active = arr_data[5]; // Esta activo
                String s_childs = arr_data[6]; // Tiene hijos


                 if(hmCounted.get(s_sectionid)==null)
                    {
                    Element row = dom.createElement("row");
                    row.appendChild(dom.createTextNode(""));
                    row.setAttribute("id", Integer.toString(j + 1));
                    report.appendChild(row);

                    Element idsection = dom.createElement("id");
                    idsection.appendChild(dom.createTextNode(s_sectionid));
                    row.appendChild(idsection);

                    Element section = dom.createElement("section");
                    section.appendChild(dom.createTextNode(s_section));
                    row.appendChild(section);

                    long allhits = 0;
                    if(s_sectionid.equals("unknown"))
                    {
                        allhits = Long.parseLong(arr_data[2]);
                    }
                    else
                    {
                        allhits = getAllHits(s_sectionid, tm);
                    }
                    if(tm.getHomePage().getId().equals(s_sectionid))
                    {
                        allhits += hitsUncountables;
                    }
                    Element pages = dom.createElement("pages");
                    pages.appendChild(dom.createTextNode(s_result));
                    row.appendChild(pages);

                    Element acumulated = dom.createElement("acumulated");
                    acumulated.appendChild(dom.createTextNode(Long.toString(allhits)));
                    row.appendChild(acumulated);

                    Element level = dom.createElement("level");
                    level.appendChild(dom.createTextNode(s_level));
                    row.appendChild(level);

                    Element active = dom.createElement("active");
                    active.appendChild(dom.createTextNode(s_active));
                    row.appendChild(active);

                    Element childs = dom.createElement("childs");
                    childs.appendChild(dom.createTextNode(s_childs));
                    row.appendChild(childs);

                }
            }
            out.println(new String(SWBUtils.XML.domToXml(dom)));
            out.close();
        } catch (Exception e) {
            log.error("Error on method doRepXml() resource" + " " + strRscType + " " + "with id" + " " + base.getId(), e);
        }
    }

    public void getChildSections(WebPage p_topic, ArrayList<WebPage> p_array) {
        Iterator<WebPage> it_son = p_topic.listChilds();
        while (it_son.hasNext()) {
            WebPage tp_child = it_son.next();
            p_array.add(i_index, tp_child);
            i_index++;
            if (tp_child.listChilds().hasNext()) {
                getChildSections(tp_child, p_array);
            }
        }
    }

     /*
     * Calcula el total de hits acumulado en las secciones hijas, incluyendo el tópico actual
     *
     */
    private long getAllHits(String tpid, WebSite tm) {
        //System.out.println("tpid"+tpid);
        long acumulado = 0;
        if (tpid != null) {
            WebPage tp = tm.getWebPage(tpid);
            hmCounted.put(tpid, tpid);
            acumulado = ((Long) hmAc.get(tpid)).longValue();
            Iterator<WebPage> it = tp.listChilds(null, true, true, true, true);
            while(it.hasNext()) {
                WebPage tptmp = it.next();
                if(tptmp != null) {
                    acumulado += ((Long) hmAc.get(tptmp.getId())).longValue();
                }
            }
        }
        return acumulado;
    }

    public long getTotalHits(String tmid, int y1,int m1,int d1, int y2,int m2,int d2, int reportType ) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long ret = 0;

        try{
            con = SWBUtils.DB.getDefaultConnection();
            String sql = "select SUM(hits) hits from swb_reshits where hits_type=? and hits_modelid=?";
            if(d1>0&&y1>0&&m1>0)
            {
                sql += " and hits_date>=? and hits_date<=? ";
            }
            st = con.prepareStatement(sql);
            st.setInt(1, reportType);
            st.setString(2, tmid);

            if(d1>0&&y1>0&&m1>0) {
                Calendar calendario = Calendar.getInstance();
                calendario.set(y1,m1-1,d1,0,0,0);
                Timestamp tempIni  =  new java.sql.Timestamp(calendario.getTimeInMillis()) ;

                Calendar calendariof = Calendar.getInstance();
                calendariof.set(y2,m2-1,d2,23,59,59);
                Timestamp tempFin  =  new java.sql.Timestamp(calendariof.getTimeInMillis()) ;

                st.setTimestamp(3, tempIni);
                st.setTimestamp(4, tempFin);
            }

            rs = st.executeQuery();
            if(rs.next())
            {
                ret = rs.getLong("hits");
            }
        }catch(Exception se) {
            log.error("Error al consultar los Hits a la DB. WBADeviceReport.getTotalHits()",se);
        }finally {
            try {
                if(rs!=null) rs.close();
                if(st!=null) st.close();
                if(con!=null) con.close();
            }catch(SQLException e){
            }
        }
        return ret;
    }

    /**
     * @param request
     * @return
     */

    private ArrayList getReportResults(String idtm, int reportType, int _year1, int _month1, int _day1, int _year2, int _month2, int _day2) {
        Iterator<SWBRecHit> iterHits = null;
        ArrayList al_pag = new ArrayList();
        WebSite tm_section = null;
        Iterator<WebPage> it_child = null;
        ArrayList<WebPage> arr_child = null;
        /*SWBRecHit recResHits = null;*/
        String s_site = idtm;
        //int i_level = 10;
        int i_len = 0;
        long l_allacumulated = 0;
        long l_acumulated = 0;
        long total_acumulado = 0;

        try {
            tm_section = SWBContext.getWebSite(s_site);
            if(tm_section != null) {
                it_child = tm_section.getHomePage().listChilds();
                arr_child = new ArrayList();
                i_index = 0;
                arr_child.add(i_index, tm_section.getHomePage());
                i_index++;
                while (it_child.hasNext()) {
                    WebPage tp_child = it_child.next();
                    arr_child.add(i_index, tp_child);
                    i_index++;
                    if(tp_child.listChilds().hasNext()) {
                        getChildSections(tp_child, arr_child);
                    }
                }
                i_len = arr_child.size();
                l_allacumulated = 0;
                for (int i = 0; i < i_len; i++) {
                    WebPage t_section = arr_child.get(i);
                    /*if (t_section.getDbdata().getSystem() == 0) {*/
                        if (!t_section.isDeleted()) {
                            if(reportType == 0) { // radio button was 0
                                iterHits = SWBRecHits_.getInstance().getResHitsLog(s_site, t_section.getId(), 3, _year1, _month1, _day1).iterator();
                            } else { // radio button was 1... Select between two dates
                                iterHits = SWBRecHits_.getInstance().getResHitsLog(s_site, t_section.getId(), 3, _year1, _month1, _day1, _year2, _month2, _day2).iterator();
                            }
                            /*recResHits = null;*/
                            l_acumulated = 0;
                            while (iterHits.hasNext()) {
                                counter++;
                                SWBRecHit recResHits = iterHits.next();
                                l_acumulated = l_acumulated + recResHits.getHits();
                            }
                            l_allacumulated = l_allacumulated + l_acumulated;

                                String[] arr_data = new String[7];
                                arr_data[0] = t_section.getDisplayName();
                                arr_data[1] = Long.toString(l_acumulated);
                                arr_data[2] = Long.toString(0);
                                arr_data[3] = Integer.toString(t_section.getLevel());
                                arr_data[4] = t_section.getId();
                                arr_data[5] = Boolean.toString(t_section.isActive());
                                arr_data[6] = SWBUtils.sizeOf(t_section.listChilds(null, true, true, true, true))>0 ? "true" : "false";
                                al_pag.add(arr_data);
                                hmAc.put(t_section.getId(), new Long(l_acumulated));

                        }
                    /*} else {
                        log.error("Reporte por Canal. sección eliminada: " + t_section.getId());
                    }*/
                }
                if (al_pag.size() > 0) {
                    String[] arr_temp = (String[]) al_pag.get(0);
                    arr_temp[2] = Long.toString(l_allacumulated);
                    al_pag.add(0, arr_temp);
                    al_pag.remove(1);
                }

                //PARA OBTENER HITS TOTALES DEL REPORTE
//
                long todos = getTotalHits(s_site, _year1,_month1,_day1,_year2,_month2,_day2, 3);
                //System.out.println("Total hits por Canal: " + todos);
                WebSite tm = SWBContext.getWebSite(s_site);
                total_acumulado = getAllHits(tm.getHomePage().getId(),tm );
                if(total_acumulado != todos)
                {
                    hitsUncountables = Math.abs(todos-total_acumulado);

                    String[] arr_data = new String[7];
                    arr_data[0] = "Secciones eliminadas";
                    arr_data[1] = Long.toString(hitsUncountables);
                    arr_data[2] = Long.toString(hitsUncountables);
                    arr_data[3] = Integer.toString(1);
                    arr_data[4] = "unknown";
                    arr_data[5] = "false";
                    arr_data[6] = "false";
                    al_pag.add(arr_data);
                    hmAc.put("unknown", new Long(hitsUncountables));
                }
            }
        } catch (Exception e) {
            log.error("Error on method ReportGenerator.getReportResults() ", e);
        }
        return al_pag;
    }

    public void updateReport(HttpServletRequest request, WebPage topic) {
        Resource base = getResourceBase();
        String s_site = null;

        String s_level = request.getParameter("wb_showlevel");
        if (s_level == null) {
            s_level = "1";
        }
        s_site = request.getParameter("wb_site");

        // Receive parameters
        String s_rep_type = request.getParameter("wb_rep_type");
        String s_filename = request.getParameter("filename");
        /*String s_year_ini = request.getParameter("wb_year_1") != null ? request.getParameter("wb_year_1") : "0";
        String s_month_ini = request.getParameter("wb_month_1") != null ? request.getParameter("wb_month_1") : "0";
        String s_day_ini = request.getParameter("wb_day_1") != null ? request.getParameter("wb_day_1") : "0";
        String s_year_fin = request.getParameter("wb_year_1") != null ? request.getParameter("wb_year_1") : "0";
        String s_month_fin = request.getParameter("wb_month_1") != null ? request.getParameter("wb_month_1") : "0";
        String s_day_fin = request.getParameter("wb_day_1") != null ? request.getParameter("wb_day_1") : "0";
        

        if("1".equals(s_rep_type)) {
            s_year_ini = request.getParameter("wb_year_11");
            s_month_ini = request.getParameter("wb_month_11");
            s_day_ini = request.getParameter("wb_day_11");
            s_year_fin = request.getParameter("wb_year_12");
            s_month_fin = request.getParameter("wb_month_12");
            s_day_fin = request.getParameter("wb_day_12");
        }else if("0".equals(s_rep_type)) {

                s_year_ini = request.getParameter("wb_year_1");
            s_month_ini = request.getParameter("wb_month_1");
            s_day_ini = request.getParameter("wb_day_1");
            s_year_fin = s_year_ini;
            s_month_fin = s_month_ini;
            s_day_fin = s_day_ini;

            if (s_day_ini != null && s_day_ini.equals("0")) {
                HashMap hm = new HashMap();
                hm.put("1", "31");
                hm.put("2", "28");
                hm.put("3", "31");
                hm.put("4", "30");
                hm.put("5", "31");
                hm.put("6", "30");
                hm.put("7", "31");
                hm.put("8", "31");
                hm.put("9", "30");
                hm.put("10", "31");
                hm.put("11", "30");
                hm.put("12", "31");

                if (s_month_fin != null && s_month_fin.equals("2")) {
                    s_day_fin = (Integer.parseInt(s_year_fin) % 4) == 0 ? "29" : "28";
                } else {
                    s_day_fin = (String) hm.get(s_month_fin.trim());
                }
                s_month_fin = s_month_ini;
                s_day_ini = "1";

            }
            else if (s_year_ini != null && s_year_ini.equals("0")) {
                s_day_ini = "0";
                s_month_ini = "0";
                s_day_fin = "0";
                s_month_fin = "0";
            }
            else if (s_year_ini != null && !s_year_ini.equals("0"))
            {
                s_day_ini = "1";
                s_month_ini = "1";
                s_day_fin = "31";
                s_month_fin = "12";
            }


        }*/
        String fechaIni;
        String fecha1 = request.getParameter("wb_fecha1");
        String fecha11 = request.getParameter("wb_fecha11");
        String fechaFin = request.getParameter("wb_fecha12");
        if("0".equalsIgnoreCase(s_rep_type)) {
            fechaIni = fecha1;
        }else {
            fechaIni = fecha11;
        }

        // CODIGO PARA GENERAR LA LISTA DE ARCHIVOS EXISTENTES
        // LOS ARCHIVOS SE GENERAN EN /WORK/LOGS/
        String archivo = Long.toString(System.currentTimeMillis());

        ReportMgr mgr = ReportMgr.getInstance();
        ReportGenerator gen = new ReportGenerator(s_site, Integer.parseInt(s_rep_type), Integer.parseInt(fechaIni.substring(0,4)), Integer.parseInt(fechaIni.substring(5,7)), Integer.parseInt(fechaIni.substring(8)), Integer.parseInt(fechaFin.substring(0,4)), Integer.parseInt(fechaFin.substring(5,7)), Integer.parseInt(fechaFin.substring(8)), archivo);
        mgr.start(gen);
        String baseXML = null;
        try {
            baseXML = base.getData(topic);
            Document dom = null;
            Element files = null;

            if (null != baseXML && baseXML.trim().length() > 0) {
                dom = SWBUtils.XML.xmlToDom(baseXML);
                files = (Element) dom.getElementsByTagName("files").item(0);
            } else {
                dom = SWBUtils.XML.getNewDocument();
                files = dom.createElement("files");
                dom.appendChild(files);
            }
            Element file = dom.createElement("file");
            file.setAttribute("name", s_filename + ".xls");
            file.setAttribute("id", archivo);
            file.setAttribute("site", s_site);
            files.appendChild(file);

            base.setData(topic, SWBUtils.XML.domToXml(dom));
        } catch (Exception edom) {
            log.error("Error al generar el DOM. ", edom);
        }
    }

    /**
     * @param paramsRequest
     * @return
     */
    public String[] doArrMonth(SWBParamRequest paramsRequest) {
        String[] arr_month = new String[12];
        try {
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
        } catch (Exception e) {
            log.error("Error on method doArrMonth() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
        }
        return arr_month;
    }

    public HashMap getFileList(WebPage topic) {
        Resource base = getResourceBase();
        String baseXML = null;
        HashMap hm = new HashMap();
        try {
            Document dom = null;
            Element files = null;
            baseXML = base.getData(topic);
            if (null != baseXML && baseXML.trim().length() > 0) {
                dom = SWBUtils.XML.xmlToDom(baseXML);
                files = (Element) dom.getElementsByTagName("files").item(0);
            } else {
                dom = SWBUtils.XML.getNewDocument();
                files = dom.createElement("files");
                dom.appendChild(files);
            }
            NodeList nl = files.getElementsByTagName("file");
            if (nl.getLength() > -1) {
                for (int i = 0; i < nl.getLength(); i++) {
                    Element nodo = (Element) nl.item(i);
                    String id = nodo.getAttribute("id");
                    String name = nodo.getAttribute("name");
                    String site = nodo.getAttribute("site");
                    hm.put(id, site + "|" + name);
                }
            }

        } catch (Exception edom) {
            log.error("Error while trying to load existing files into a HashMap. WBAChannelReport.getFileList() ", edom);
        }
        return hm;
    }

    /**
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
     */
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String filename = request.getParameter("filename");
        String key = request.getParameter("key");
        String act = response.getAction();
        WebPage topic = response.getTopic();
        Resource base = getResourceBase();
        if (null == act) {
            act = "";
        }
        if ("remove".equals(act)) {
            try {
                if (null != key && key.trim().length() > 0) {
                    Document dom = SWBUtils.XML.xmlToDom(base.getData(topic));
                    Document newDom = SWBUtils.XML.getNewDocument();
                    Element nfiles = newDom.createElement("files");
                    newDom.appendChild(nfiles);
                    boolean eliminar = false;
                    Element files = (Element) dom.getElementsByTagName("files").item(0);
                    NodeList nl = files.getElementsByTagName("file");
                    if (nl.getLength() > -1) {
                        for (int i = 0; i < nl.getLength(); i++) {
                            Element nodo = (Element) nl.item(i);
                            String id = nodo.getAttribute("id");
                            String name = nodo.getAttribute("name");
                            String site = nodo.getAttribute("site");
                            if (!key.equals(id)) {
                                Element file = newDom.createElement("file");
                                file.setAttribute("name", name);
                                file.setAttribute("id", id);
                                file.setAttribute("site", site);
                                nfiles.appendChild(file);
                            } else {
                                eliminar = true;
                            }
                        }
                        if (eliminar) {
                            File frem = new File(SWBPlatform.getWorkPath()+"/logs/reports/" + key + ".xls");
                            if (frem.exists()) {
                                eliminar = frem.delete();
                            }
                        }
                    }

                    base.setData(topic, SWBUtils.XML.domToXml(newDom));

                }
            } catch (Exception e) {
                log.error("Error al eliminar un reporte por Canal",e);
            }
        } else if ("".equals(act)) {
            if (null != filename && filename.trim().length() > 0) {
                updateReport(request, topic);
            }
        }

        response.setMode(response.Mode_VIEW);
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String element = (String) en.nextElement();
            if (null != request.getParameter(element)) {
                response.setRenderParameter(element, request.getParameter(element));
            }
        }
    }

    public void doDownload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String filename = request.getParameter("filename");
        String key = request.getParameter("key");

        if (null != filename && filename.trim().length() > 0 && null != key) {
            response.setContentType("application/vnd.ms-excel");
            String ext = ".xls";
            int pos = filename.lastIndexOf('.');
            if (pos != -1) {
                ext = filename.substring(pos);
            }
            String sfile = key + ext;
            File file = new File(SWBPlatform.getWorkPath() + "/logs/reports/" + sfile);
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
            if (file.exists()) {
                OutputStream out = response.getOutputStream();
                FileInputStream fout = new FileInputStream(file);
                byte[] bcont = new byte[8192];
                int bytes = fout.read(bcont);
                while (bytes > 0) {
                    out.write(bcont, 0, bytes);
                    bytes = fout.read(bcont);
                }
                fout.close();
                out.flush();
            }
        }
    }
}

class ReportGenerator extends Thread {
    private static Logger log = SWBUtils.getLogger(WBADeviceReport.class);

    private int counter = 0;
    private String status = null;
    private long ini = 0;
    private long fin = 0;
    private ReportMgr manager = null;
    private int year1=2006,  month1 = 01,  day1 = 01,  year2 = 2006,  month2 = 12,  day2 = 31;
    private String tms = "Test";
    private String filename = null;
    private int type = 3;
    private int i_index = 0;
    private HashMap hmAc = null, hmCounted=null;
    private long hitsUncountables = 0;

    /** Creates a new instance of reporGenerator */
    public ReportGenerator(String tms, int type, int year1, int month1, int day1, int year2, int month2, int day2, String filename) {
        this.tms = tms;
        this.type = type;
        this.year1 = year1;
        this.month1 = month1;
        this.day1 = day1;
        this.year2 = year2;
        this.month2 = month2;
        this.day2 = day2;
        this.filename = filename;
        this.hmAc = new HashMap();
        this.hmCounted = new HashMap();
    }

    private void getChildSections(WebPage p_topic, ArrayList p_array) {
        Iterator<WebPage> it_son = p_topic.listChilds();
        while(it_son.hasNext()) {
            WebPage tp_child = it_son.next();
            p_array.add(i_index, tp_child);
            i_index++;
            if (tp_child.listChilds().hasNext()) {
                getChildSections(tp_child, p_array);
            }
        }
    }

    public long getTotalHits(String tmid, int y1,int m1,int d1, int y2,int m2,int d2, int reportType )
    {
        long ret = 0;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            con = SWBUtils.DB.getDefaultConnection();
            String sql = "select SUM(hits) hits from swb_reshits where hits_type=? and hits_modelid=?";
            if(d1>0&&y1>0&&m1>0)
            {
                sql += " and hits_date>=? and hits_date<=? ";
            }
            pst = con.prepareStatement(sql);
            pst.setInt(1, reportType);
            pst.setString(2, tmid);

            if(d1>0&&y1>0&&m1>0)
            {
                Calendar calendario = Calendar.getInstance();
                calendario.set(y1,m1-1,d1,0,0,0);
                Timestamp tempIni  =  new java.sql.Timestamp(calendario.getTimeInMillis()) ;

                Calendar calendariof = Calendar.getInstance();
                calendariof.set(y2,m2-1,d2,23,59,59);
                Timestamp tempFin  =  new java.sql.Timestamp(calendariof.getTimeInMillis()) ;

                pst.setTimestamp(3, tempIni);
                pst.setTimestamp(4, tempFin);
            }

            rs = pst.executeQuery();
            if(rs.next())
            {
                ret = rs.getLong("hits");
            }
        }catch(Exception se) {
            log.error("Error al consultar los Hits a la DB. WBADeviceReport.getTotalHits()",se);
        }finally {
            try {
                if(rs!=null) rs.close();
                if(pst!=null) pst.close();
                if(con!=null) con.close();
            }catch(SQLException e){
            }
        }
        return ret;
    }

    private Iterator getReportResults(String idtm, int reportType, int _year1, int _month1, int _day1, int _year2, int _month2, int _day2) {
        Iterator<SWBRecHit> iterHits = null;
        ArrayList al_pag = new ArrayList();
        WebSite tm_section = null;
        Iterator<WebPage> it_child = null;
        ArrayList<WebPage> arr_child = null;
        /*RecResHits recResHits = null;*/
        String s_site = idtm;
        //int i_level = 10;
        int i_len = 0;
        long l_allacumulated = 0;
        long l_acumulated = 0;
        long total_acumulado = 0;

        try {
            tm_section = SWBContext.getWebSite(s_site);
            if (tm_section != null) {
                it_child = tm_section.getHomePage().listChilds();
                arr_child = new ArrayList();
                i_index = 0;
                arr_child.add(i_index, tm_section.getHomePage());
                i_index++;
                while (it_child.hasNext()) {
                    WebPage tp_child = it_child.next();
                    arr_child.add(i_index, tp_child);
                    i_index++;
                    if (tp_child.listChilds().hasNext()) {
                        getChildSections(tp_child, arr_child);
                    }
                }
                i_len = arr_child.size();
                l_allacumulated = 0;
                for (int i = 0; i < i_len; i++) {
                    WebPage t_section = arr_child.get(i);
                    /*if (t_section.getDbdata().getSystem() == 0) {*/
                        if (!t_section.isDeleted()) {
                            if (reportType == 0) { // radio button was 0
                                iterHits = SWBRecHits_.getInstance().getResHitsLog(s_site, t_section.getId(), 3, _year1, _month1, _day1).iterator();
                            } else {                       // radio button was 1
                                // Select between two dates
                                iterHits = SWBRecHits_.getInstance().getResHitsLog(s_site, t_section.getId(), 3, _year1, _month1, _day1, _year2, _month2, _day2).iterator();
                            }
                            /*recResHits = null;*/
                            l_acumulated = 0;
                            while (iterHits.hasNext()) {
                                counter++;
                                SWBRecHit recResHits = iterHits.next();
                                l_acumulated = l_acumulated + recResHits.getHits();
                            }
                            l_allacumulated = l_allacumulated + l_acumulated;

                                String[] arr_data = new String[7];
                                arr_data[0] = t_section.getDisplayName();
                                arr_data[1] = Long.toString(l_acumulated);
                                arr_data[2] = Long.toString(0);
                                arr_data[3] = Integer.toString(t_section.getLevel());
                                arr_data[4] = t_section.getId();                                
                                arr_data[5] = Boolean.toString(t_section.isActive());
                                arr_data[6] = SWBUtils.sizeOf(t_section.listChilds(null, true, true, true, true)) > 0 ? "true" : "false";
                                al_pag.add(arr_data);
                                hmAc.put(t_section.getId(), new Long(l_acumulated));

                        }
                    /*} else {
                        log.error("Reporte por Canal. sección eliminada: " + t_section.getId());
                    }*/
                }
                if (al_pag.size() > 0) {
                    String[] arr_temp = (String[]) al_pag.get(0);
                    arr_temp[2] = Long.toString(l_allacumulated);
                    al_pag.add(0, arr_temp);
                    al_pag.remove(1);
                }

                //PARA OBTENER HITS TOTALES DEL REPORTE
//
                long todos = getTotalHits(s_site, _year1,_month1,_day1,_year2,_month2,_day2, 3);
                //System.out.println("Total hits por Canal: " + todos);
                WebSite tm = SWBContext.getWebSite(s_site);
                total_acumulado = getAllHits(tm.getHomePage().getId(), tm);
                if(total_acumulado != todos)
                {
                    hitsUncountables = Math.abs(todos-total_acumulado);

                    String[] arr_data = new String[7];
                    arr_data[0] = "Secciones eliminadas";
                    arr_data[1] = Long.toString(hitsUncountables);
                    arr_data[2] = Long.toString(hitsUncountables);
                    arr_data[3] = Integer.toString(1);
                    arr_data[4] = "unknown";
                    arr_data[5] = "false";
                    arr_data[6] = "false";
                    al_pag.add(arr_data);
                    hmAc.put("unknown", new Long(hitsUncountables));
                }
            }
        } catch (Exception e) {
            log.error("Error on method ReportGenerator.getReportResults() ",e);
        }
        return al_pag.iterator();
    }

    /*
     * Calcula el total de hits acumulado en las secciones hijas, incluyendo el tópico actual
     *
     */
    private long getAllHits(String tpid, WebSite tm) {
        long acumulado = 0;
        if (tpid != null) {
            WebPage tp = tm.getWebPage(tpid);
            if(null!=tp)
            {
                hmCounted.put(tpid, tpid);
                acumulado = ((Long) hmAc.get(tpid)).longValue();
                Iterator<WebPage> it = tp.listChilds(null, true, true, true, true);
                while (it.hasNext()) {
                    WebPage tptmp = it.next();
                    if (tptmp != null) {
                        acumulado += (null!=hmAc.get(tptmp.getId()))?((Long) hmAc.get(tptmp.getId())).longValue():0;
                    }
                }
            }
        }
        return acumulado;
    }

    public void run() {
        status = "init";
        manager.setRunning(true);
        try {
            WebSite tm = SWBContext.getWebSite(tms);
            if (tm != null && (year1 != -1 && month1 != -1 && day1 != -1) && (year2 != -1 && month2 != -1 && day2 != -1)) {

                ini = System.currentTimeMillis();
                status = "Start Report...";
                hmCounted=new HashMap();
                status = "Reading Logs";
                Iterator iter = getReportResults(tms, type, year1, month1, day1, year2, month2, day2);

                status = "Processing Logs";
                File reportdir = new File(SWBPlatform.getWorkPath() + "/logs/reports/");
                if (!reportdir.exists()) {
                    reportdir.mkdirs();
                }

                status = "Writing Logs";
                if (null == filename || (null != filename && filename.trim().length() == 0)) {
                    filename = "CReport";
                }
                hmCounted=new HashMap();

                PrintWriter out = new PrintWriter(new FileOutputStream(SWBPlatform.getWorkPath() + "/logs/reports/" + filename + ".xls"));
                out.println("<table border=1>");
                out.println("<tr ><td colspan=7 align=center bgcolor=\"gray\"><b>Channel Report - " + tms + "</b></td></tr>");
                out.println("<tr ><td bgcolor=\"gray\">");
                out.println("<b>id</b>");
                out.println("</td><td bgcolor=\"gray\">");
                out.println("<b>Section title</b>");
                out.println("</td><td bgcolor=\"gray\">");
                out.println("<b>Hits</b>");
                out.println("</td><td bgcolor=\"gray\">");
                out.println("<b>Acumulate</b>");
                out.println("</td><td bgcolor=\"gray\">");
                out.println("<b>Have childs</b>");
                out.println("</td><td bgcolor=\"gray\">");
                out.println("<b>Active</b>");
                out.println("</td><td bgcolor=\"gray\">");
                out.println("<b>Level</b>");
                out.println("</td></tr>");
                while (iter.hasNext()) {
                    String[] arr_data = (String[]) iter.next();
                    String sectionTitle = arr_data[0];
                    String sectionHits = arr_data[1];
                    String sectionLevel = arr_data[3];
                    String sectionID = arr_data[4];
                    String sectionActive = arr_data[5];
                    String sectionChilds = arr_data[6];

                    if(hmCounted.get(sectionID)==null)
                    {
                        long allhits = 0;
                        if(sectionID.equals("unknown"))
                        {
                            allhits = Long.parseLong(arr_data[2]);
                        }
                        else
                        {
                            allhits = getAllHits(sectionID, tm);
                        }
                        if(tm.getHomePage().getId().equals(sectionID))
                        {
                            allhits += hitsUncountables;
                        }
                        int nivel = sectionLevel.trim().length() > 0 ? Integer.parseInt(sectionLevel) : 0;
                        out.print("<tr><td>");
                        out.print(sectionID + "\t &nbsp;&nbsp;");
                        out.print("</td><td>");
                        for (int le = 0; le < nivel; le++) {
                            out.print("\t &nbsp;&nbsp;");
                        }
                        out.print(sectionTitle + "\t");
                        for (int le = nivel; le < nivel; le++) {
                            out.print("\t &nbsp;&nbsp;");
                        }
                        out.print("</td><td>");
                        out.print(sectionHits + "\t");
                        out.print("</td><td>");
                        out.print(allhits + "\t");
                        out.print("</td><td>");
                        out.println(sectionChilds);
                        out.print("</td><td>");
                        out.println(sectionActive);
                        out.print("</td><td>");
                        out.println(sectionLevel);
                        out.print("</td></tr>");
                    }
                }
                out.println("</table>");
                out.flush();
                out.close();

                fin = System.currentTimeMillis();
            }
        } catch (Exception e) {            
            status = "error\n" + e.getMessage();
            manager.setRunning(false);
            return;
        }
        status = "end";
        manager.setRunning(false);
    }

    /**
     * Getter for property counter.
     * @return Value of property counter.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        return status;
    }

    /**
     * Getter for property manager.
     * @return Value of property manager.
     */
    public ReportMgr getManager() {
        return manager;
    }

    /**
     * Setter for property manager.
     * @param manager New value of property manager.
     */
    public void setManager(ReportMgr manager) {
        this.manager = manager;
    }

    /**
     * Getter for property ini.
     * @return Value of property ini.
     */
    public long getInitTime() {
        return ini;
    }

    /**
     * Getter for property fin.
     * @return Value of property fin.
     */
    public long getEndTime() {
        return fin;
    }
}

class ReportMgr {

    private static ReportMgr instance = null;
    private ReportGenerator gen = null;
    private boolean isRunning = false;

    /** Creates a new instance of ReportMgr */
    private ReportMgr() {
    }

    public static ReportMgr getInstance() {
        if (instance == null) {
            instance = new ReportMgr();
        }
        return instance;
    }

    public void start(ReportGenerator rgen) {
            gen = rgen;
            rgen.setManager(this);
            gen.start();
    }

    public void stop() {
        if (gen != null) {
            gen = null;
        }
    }

    /**
     * Getter for property isRunning.
     * @return Value of property isRunning.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Setter for property isRunning.
     * @param isRunning New value of property isRunning.
     */
    void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * Getter for property gen.
     * @return Value of property gen.
     */
    public ReportGenerator getGenerator() {
        return gen;
    }
}
