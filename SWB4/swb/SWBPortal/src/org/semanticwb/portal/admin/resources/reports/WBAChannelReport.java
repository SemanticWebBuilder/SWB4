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

import org.semanticwb.portal.db.SWBRecHit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WBAChannelReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBAChannelReport.class);
    private final int I_REPORT_TYPE = 3;   // Type 4 of reports "Channel access"    
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
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equals("report_xml")) {
            doRepXml(request, response, paramsRequest);
        }else if(paramsRequest.getMode().equals("download")) {
            doDownload(request, response, paramsRequest);
        }else {
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
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        final int I_ACCESS = 0;
        Resource base = getResourceBase();
        StringBuffer sb_ret = new StringBuffer();

        ReportMgr rm = ReportMgr.getInstance();
        if((null!=rm) && (!rm.isRunning())) {
            HashMap hm_sites = new HashMap();

            String s_rfilter = request.getParameter("wb_rfilter");
            String s_level = request.getParameter("wb_showlevel");
            if (s_level == null) {
                s_level = "1";
            }
            
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
                if (hm_sites.size() > I_ACCESS) {
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
                    sb_ret.append("dojo.addOnLoad(doBlockade);");

                    sb_ret.append("function getParams() {");
                    sb_ret.append("   var dp = null;");
                    sb_ret.append("   var params = '?';");
                    sb_ret.append("   params = params + 'wb_site=' + dojo.byId('wb_site').value;");
                    sb_ret.append("   params = params + '&wb_rep_type=' + getTypeSelected();");
                    sb_ret.append("   var fecha1 = new String(dojo.byId('wb_fecha1').value);");
                    sb_ret.append("   var fecha2 = new String(dojo.byId('wb_fecha11').value);");
                    sb_ret.append("   var fecha3 = new String(dojo.byId('wb_fecha12').value);");
                    sb_ret.append("   if(fecha1.length>0) {");
                    sb_ret.append("      dp = fecha1.split('/');");
                    sb_ret.append("      params = params + '&wb_fecha1=' + dp[2]+'-'+dp[1]+'-'+dp[0];");
                    sb_ret.append("   }");
                    sb_ret.append("   if(fecha2.length>0) {");
                    sb_ret.append("      dp = fecha2.split('/');");
                    sb_ret.append("      params = params + '&wb_fecha11=' + dp[2]+'-'+dp[1]+'-'+dp[0];");
                    sb_ret.append("   }");
                    sb_ret.append("   if(fecha3.length>0) {");
                    sb_ret.append("      dp = fecha3.split('/');");
                    sb_ret.append("      params = params + '&wb_fecha12=' + dp[2]+'-'+dp[1]+'-'+dp[0];");
                    sb_ret.append("   }");
                    sb_ret.append("   return params;");
                    sb_ret.append("}");

                    sb_ret.append("function validate() {");
                    sb_ret.append("   var fecha1 = new String(dojo.byId('wb_fecha1').value);");
                    sb_ret.append("   var fecha2 = new String(dojo.byId('wb_fecha11').value);");
                    sb_ret.append("   var fecha3 = new String(dojo.byId('wb_fecha12').value);");
                    sb_ret.append("   if( (fecha1.length==0) && (fecha2.length==0 || fecha3.length==0) ) {");
                    sb_ret.append("      alert('Especifique la fecha o el rango de fechas que desea consultar');");
                    sb_ret.append("      return false;");
                    sb_ret.append("   }");
                    sb_ret.append("   return true;");
                    sb_ret.append("}");

                    sb_ret.append("function doXml(size) { ");
                    sb_ret.append("   if(validate()) {");
                    sb_ret.append("      var params = getParams();");
                    sb_ret.append("      window.open(\""+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("report_xml")+"\"+params,\"graphWindow\",size);");
                    sb_ret.append("   }");
                    sb_ret.append("}");

                    sb_ret.append(" function getTypeSelected(){\n");
                    sb_ret.append("     var strType = \"0\";\n");
                    sb_ret.append("     for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){\n");
                    sb_ret.append("       if(window.document.frmrep.wb_rep_type[i].checked==true){\n");
                    sb_ret.append("           strType=window.document.frmrep.wb_rep_type[i].value;\n");
                    sb_ret.append("       }\n");
                    sb_ret.append("     }\n");
                    sb_ret.append("     return strType;\n");
                    sb_ret.append(" }\n");

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
                    
                    sb_ret.append(" function doBlockade() {");
                    sb_ret.append("   if(window.document.frmrep.wb_rep_type) {");
                    sb_ret.append("     if(window.document.frmrep.wb_rep_type[0].checked){");
                    sb_ret.append("       dojo.byId('wb_fecha1').disabled = false;");
                    sb_ret.append("       dojo.byId('wb_fecha11').disabled = true;");
                    sb_ret.append("       dojo.byId('wb_fecha12').disabled = true;");
                    sb_ret.append("     }");
                    sb_ret.append("     if(window.document.frmrep.wb_rep_type[1].checked){");
                    sb_ret.append("       dojo.byId('wb_fecha1').disabled = true;");
                    sb_ret.append("       dojo.byId('wb_fecha11').disabled = false;");
                    sb_ret.append("       dojo.byId('wb_fecha12').disabled = false;");
                    sb_ret.append("     }");
                    sb_ret.append("   }");
                    sb_ret.append(" }");
                    
                    sb_ret.append("function doDownload(name,key){\n");
                    sb_ret.append("   window.location='"+paramsRequest.getRenderUrl().setCallMethod(paramsRequest.Call_DIRECT).setMode("download")+"?filename='+name+'&key='+key;\n");
                    sb_ret.append("}\n");

                    sb_ret.append("</script>\n");
                    // javascript

                    sb_ret.append("<div class=\"swbform\">\n");
                    sb_ret.append("<fieldset>\n");
                    sb_ret.append("<legend>" + paramsRequest.getLocaleString("channel_report") + "</legend>\n");

                    SWBResourceURL url = paramsRequest.getActionUrl();

                    sb_ret.append("<form name=\"frmrep\" id=\"frmrep\" method=\"post\" action=\""+url+"\">\n");
                    sb_ret.append("<table border=\"0\" width=\"95%\" align=\"center\">\n");
                    sb_ret.append("<tr><td width=\"213\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"413\"></td></tr>\n");

                    sb_ret.append("<tr>\n");
                    sb_ret.append(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;\n");
                    sb_ret.append("   <input type=\"button\" onclick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\" value=\"XML\" name=\"btnXml\" />&nbsp;\n");
                    sb_ret.append("   <input type=\"button\" onclick=\"doApply()\" value=\""+paramsRequest.getLocaleString("apply")+"\" name=\"btnApply\" />\n");
                    sb_ret.append("   <input type=\"hidden\" name=\"wb_rfilter\" value=\""+s_rfilter+"\"/>\n");
                    sb_ret.append(" </td>\n");
                    sb_ret.append("</tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");

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

                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td>\n");
                    sb_ret.append(paramsRequest.getLocaleString("reportName") + ":");
                    sb_ret.append("</td>\n");
                    sb_ret.append("<td colspan=\"3\">\n");
                    sb_ret.append("<input type=\"text\" name=\"filename\" value=\"\"/>\n");
                    sb_ret.append("&nbsp;<font size=\"1\">* " + paramsRequest.getLocaleString("reportFileGenerated") + " (xls)</font>\n");
                    sb_ret.append("</td>\n");
                    sb_ret.append("</tr>\n");
                    
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("<tr>\n");
                    sb_ret.append("<td colspan=\"4\">\n");
                    sb_ret.append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">\n");
                    HashMap hm = getFileList(paramsRequest.getTopic());
                    if (hm.size() == 0) {
                        sb_ret.append("<caption align=\"top\">" + paramsRequest.getLocaleString("noGeneratedReports") + "</caption>\n");
                    } else {
                        sb_ret.append("<caption align=\"top\">" + paramsRequest.getLocaleString("listReports") + "</caption>\n");
                        sb_ret.append("<tr><th width=\"15%\">" + paramsRequest.getLocaleString("tdAction") + "</th><th width=\"35%\">" + paramsRequest.getLocaleString("tdFileName") + "</th><th width=\"25%\">" + paramsRequest.getLocaleString("tdSite") + "</th><th width=\"25%\">" + paramsRequest.getLocaleString("tdCreated") + "</th></tr>\n");

                        boolean toggleColor = true;
                        SWBResourceURL urlDel = paramsRequest.getActionUrl().setAction("remove");
                        /*Iterator<String> itf = hm.keySet().iterator();*/
                        String[] arrhm = new String[hm.size()];
                        Arrays.sort(hm.keySet().toArray(arrhm));//  hm.keySet()
                        /*while (itf.hasNext()) {*/
                        for(int i=0; i<arrhm.length; i++) {
                            /*String key = itf.next();*/
                            String key = arrhm[i];
                            String sfn = (String)hm.get(key);
                            String site = sfn.substring(0, sfn.lastIndexOf("|"));
                            String filename = sfn.substring(sfn.lastIndexOf("|") + 1);
                            /*if(null != hm_sites2.get(site)) {*/                                
                                sb_ret.append("<tr bgcolor=\""+(toggleColor?"#EFEDEC":"#FFFFFF")+"\">\n");
                                urlDel.setParameter("key", key);
                                sb_ret.append("<td><a href=\""+urlDel+"\" onclick=\" if(confirm('" + paramsRequest.getLocaleString("alertRemoveFile") + "?')){return true;}else{return false};\"><img border=\"0\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" alt=\"" + paramsRequest.getLocaleString("deleteReport") + "\"></a></td>\n");
                                sb_ret.append("<td><a href=\"javascript:doDownload('"+filename+"','"+key + "');\" alt=\"Reporte: "+filename+"\">"+filename+"</a></td>\n");
                                sb_ret.append("<td>" + site + "</td>\n");
                                sb_ret.append("<td>" + SWBUtils.TEXT.iso8601DateFormat(new java.sql.Timestamp(Long.parseLong(key)))+"</td>\n");
                                sb_ret.append("</tr>\n");
                                toggleColor = !(toggleColor);
                            /*}*/
                        }
                    }
                    sb_ret.append("</table>\n");
                    sb_ret.append("</td></tr>\n");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>\n");
                    sb_ret.append("</table></form>\n");
                    sb_ret.append("</fieldset></div>");
                }else { // There are not sites and displays a message
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
            }catch(Exception e) {
                log.error("Error on method DoView() resource " + " " + strRscType + " " + "with id" + " " + base.getId(),e);
            }
        }else {
            DateFormat df = null;

            ReportGenerator rgen = ReportMgr.getInstance().getGenerator();
            sb_ret.append("<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">");
            sb_ret.append("<tr><td colspan=\"2\">");
            sb_ret.append(paramsRequest.getLocaleString("msgTitleChRep"));
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td width=\"200\" align=\"right\">");
            sb_ret.append(paramsRequest.getLocaleString("msgStatus") + ":</td><td>" + rgen.getStatus());
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td width=\"200\" align=\"right\">");
            Timestamp ts_time = new Timestamp(rgen.getInitTime());
            String s_time = "" + ts_time.getHours() + ":" + ts_time.getMinutes() + ":" + ts_time.getSeconds();
            df = DateFormat.getTimeInstance(DateFormat.FULL);
            s_time = df.format(ts_time);
            sb_ret.append(paramsRequest.getLocaleString("msgSTime") + ":</td><td>" + s_time);
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td width=\"200\" align=\"right\">");
            sb_ret.append(paramsRequest.getLocaleString("msgProcTime") + ":</td><td>" + getAvailableTime(ts_time));
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td width=\"200\" align=\"right\">");
            sb_ret.append(paramsRequest.getLocaleString("msgLogCounter") + ":</td><td>" + rgen.getCounter());
            sb_ret.append("</td></tr>");
            sb_ret.append("</table>");

            sb_ret.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"3;URL="+paramsRequest.getRenderUrl()+"\">");
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

    private long getAllHits(String tpid, WebSite tm) {
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
        String s_site = request.getParameter("wb_site");

        int groupDates;
        try {
            groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
        }catch(NumberFormatException e) {
            groupDates = 0;
        }
        String fecha1 = request.getParameter("wb_fecha1")==null ? "":request.getParameter("wb_fecha1");
        String fecha11 = request.getParameter("wb_fecha11")==null ? "":request.getParameter("wb_fecha11");
        String fecha12 = request.getParameter("wb_fecha12")==null ? "":request.getParameter("wb_fecha12");
        String filenameLogic = request.getParameter("filename");

        String filename = Long.toString(System.currentTimeMillis());
        ReportMgr mgr = ReportMgr.getInstance();
        ReportGenerator gen;
        if(groupDates==0) {
            gen = new ReportGenerator(s_site, Integer.parseInt(fecha1.substring(0,4)), Integer.parseInt(fecha1.substring(5,7)), Integer.parseInt(fecha1.substring(8)), filename);
        }else {
            gen = new ReportGenerator(s_site, Integer.parseInt(fecha11.substring(0,4)), Integer.parseInt(fecha11.substring(5,7)), Integer.parseInt(fecha11.substring(8)), Integer.parseInt(fecha12.substring(0,4)), Integer.parseInt(fecha12.substring(5,7)), Integer.parseInt(fecha12.substring(8)), filename);
        }
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
            file.setAttribute("name", filenameLogic+".xls");
            file.setAttribute("id", filename);
            file.setAttribute("site", s_site);
            files.appendChild(file);

            base.setData(topic, SWBUtils.XML.domToXml(dom));
        } catch (Exception edom) {
            log.error("Error al generar el DOM. ", edom);
        }
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
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String filename = request.getParameter("filename");
        String key = request.getParameter("key");
        String act = response.getAction();
        WebPage topic = response.getTopic();
        Resource base = getResourceBase();
        if(null == act) {
            act = "";
        }
        if("remove".equalsIgnoreCase(act)) {
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
        }else if ("".equalsIgnoreCase(act)) {
            if( null!=filename && filename.trim().length()>0 ) {
                updateReport(request, topic);
            }
        }

        response.setMode(response.Mode_VIEW);
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String element = (String) en.nextElement();
            if(null!=request.getParameter(element)) {
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

    private final int type = 3;
    private int counter;
    private int year1,  month1,  day1,  year2,  month2,  day2;
    private long ini = 0;
    private long fin = 0;

    private String status;
    private String tms = "Test";
    private String filename = null;
    
    private ReportMgr manager = null;
    private HashMap hmAc = null, hmCounted=null;
    

    public ReportGenerator(String tms, int year1, int month1, int day1, String filename) {
        this(tms, year1, month1, day1, 0, 0, 0, filename);
    }

    public ReportGenerator(String tms, int year1, int month1, int day1, int year2, int month2, int day2, String filename) {
        this.tms = tms;
        this.year1 = year1;
        this.month1 = month1;
        this.day1 = day1;
        this.year2 = year2;
        this.month2 = month2;
        this.day2 = day2;
        this.filename = filename;
        this.hmAc = new HashMap();
        this.hmCounted = new HashMap();
        this.status = "Initializing...";
    }

    private void getChildSections(WebPage node, ArrayList sections) {
        Iterator<WebPage> childs = node.listChilds();
        while(childs.hasNext()) {
            WebPage webPage = childs.next();
            sections.add(webPage);
            if(webPage.listChilds().hasNext()) {
                getChildSections(webPage, sections);
            }
        }
    }

    public long getTotalHits(String tmid, int year,int month,int day) {
        long ret = 0;        
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection con = null;
        GregorianCalendar date;
        try{
            con = SWBUtils.DB.getDefaultConnection();
            String sql = "select SUM(hits)as hits from swb_reshits where hits_modelid=? and hits_type=? and (hits_date>=? and hits_date<=?)";
            st = con.prepareStatement(sql);
            st.setString(1, tmid);
            st.setInt(2, type);
            date = new GregorianCalendar(year, month-1, day, 0, 0, 0);
            st.setTimestamp(3, new Timestamp(date.getTimeInMillis()));
            date = new GregorianCalendar(year,month-1,day,23,59,59);
            st.setTimestamp(4, new Timestamp(date.getTimeInMillis()));
            rs = st.executeQuery();
            if(rs.next()) {
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

    public long getTotalHits(String tmid, int y1,int m1,int d1, int y2,int m2,int d2, int reportType ) {
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

    private Iterator getReportResults(final String siteId, final int year, final int month, final int day) {
        Iterator<SWBRecHit> iterHits;
        ArrayList al_pag = new ArrayList();
        WebSite webSite;
        Iterator<WebPage> childs;
        ArrayList<WebPage> sections;
        long l_allacumulated = 0;

        try {
            webSite = SWBContext.getWebSite(siteId);
            if(webSite != null) {

                childs = webSite.getHomePage().listChilds();
                sections = new ArrayList();
                sections.add(webSite.getHomePage());
                while(childs.hasNext()) {
                    WebPage webPage = childs.next();
                    sections.add(webPage);
                    if(webPage.listChilds().hasNext()) {
                        getChildSections(webPage, sections);
                    }
                }

                iterHits = SWBRecHits_.getInstance().getResHitsLog(siteId, type, year, month, day).iterator();
                HashMap hits = new HashMap();
                while(iterHits.hasNext()) {
                    SWBRecHit recHits = iterHits.next();
                    hits.put(recHits.getSection(), recHits);
                }

                Iterator<WebPage> itsections = sections.iterator();
                while(itsections.hasNext()) {
                    WebPage section = itsections.next();
                    String[] arr_data = new String[7];
                    arr_data[0] = section.getDisplayName();

                    if(hits.containsKey(section.getId())) {
                        SWBRecHit recHit = (SWBRecHit)hits.get(section.getId());
                        arr_data[1] = Long.toString(recHit.getHits(), 10);
                        l_allacumulated += recHit.getHits();
                        hits.remove(section.getId());
                    }else {
                        arr_data[1] = "0";
                    }

                    arr_data[2] = Long.toString(l_allacumulated);
                    arr_data[3] = Integer.toString(section.getLevel());
                    arr_data[4] = section.getId();
                    arr_data[5] = section.isActive()?"Active":"Inactive";
                    arr_data[6] = SWBUtils.sizeOf(section.listChilds())>0 ? "true" : "false";
                    al_pag.add(arr_data);
                }
                if(hits.size()>0) {
                    Iterator<String> keys = hits.keySet().iterator();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        SWBRecHit recHit = (SWBRecHit)hits.get(key);

                        String[] arr_data = new String[7];
                        arr_data[0] = recHit.getSection();
                        arr_data[1] = Long.toString(recHit.getHits(), 10);
                        l_allacumulated += recHit.getHits();
                        arr_data[2] = Long.toString(l_allacumulated, 10);
                        arr_data[3] = "-1";
                        arr_data[4] = recHit.getSection();
                        arr_data[5] = "Deleted";
                        arr_data[6] = "-";
                        al_pag.add(arr_data);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error on method ReportGenerator.getReportResults() ",e);
        }
        return al_pag.iterator();
    }

    private Iterator getReportResults(String siteId, int year1, int month1, int day1, int year2, int month2, int day2) {
        Iterator<SWBRecHit> iterHits;
        ArrayList al_pag = new ArrayList();
        WebSite webSite;
        Iterator<WebPage> childs;
        ArrayList<WebPage> sections;
        long l_allacumulated = 0;

        try {
            webSite = SWBContext.getWebSite(siteId);
            if(webSite != null) {

                childs = webSite.getHomePage().listChilds();
                sections = new ArrayList();
                sections.add(webSite.getHomePage());
                while(childs.hasNext()) {
                    WebPage webPage = childs.next();
                    sections.add(webPage);
                    if(webPage.listChilds().hasNext()) {
                        getChildSections(webPage, sections);
                    }
                }

                iterHits = SWBRecHits_.getInstance().getResHitsLog(siteId, type, year1, month1, day1, year2, month2, day2).iterator();
                HashMap hits = new HashMap();
                while(iterHits.hasNext()) {
                    SWBRecHit recHits = iterHits.next();
                    hits.put(recHits.getSection(), recHits);
                }

                Iterator<WebPage> itsections = sections.iterator();
                while(itsections.hasNext()) {
                    WebPage section = itsections.next();
                    String[] arr_data = new String[7];
                    arr_data[0] = section.getDisplayName();

                    if(hits.containsKey(section.getId())) {
                        SWBRecHit recHit = (SWBRecHit)hits.get(section.getId());
                        arr_data[1] = Long.toString(recHit.getHits(), 10);
                        l_allacumulated += recHit.getHits();
                        hits.remove(section.getId());
                    }else {
                        arr_data[1] = "0";
                    }

                    arr_data[2] = Long.toString(l_allacumulated);
                    arr_data[3] = Integer.toString(section.getLevel());
                    arr_data[4] = section.getId();
                    arr_data[5] = section.isActive()?"Active":"Inactive";
                    arr_data[6] = SWBUtils.sizeOf(section.listChilds())>0 ? "true" : "false";
                    al_pag.add(arr_data);
                }
                if(hits.size()>0) {
                    Iterator<String> keys = hits.keySet().iterator();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        SWBRecHit recHit = (SWBRecHit)hits.get(key);

                        String[] arr_data = new String[7];
                        arr_data[0] = recHit.getSection();
                        arr_data[1] = Long.toString(recHit.getHits(), 10);
                        l_allacumulated += recHit.getHits();
                        arr_data[2] = Long.toString(l_allacumulated, 10);
                        arr_data[3] = "-1";
                        arr_data[4] = recHit.getSection();
                        arr_data[5] = "Deleted";
                        arr_data[6] = "-";
                        al_pag.add(arr_data);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error on method ReportGenerator.getReportResults() ",e);
        }
        return al_pag.iterator();
    }

    public void run() {
        status = "Initializing";
        manager.setRunning(true);
        try {
            WebSite tm = SWBContext.getWebSite(tms);
            if(tm != null) {
                ini = System.currentTimeMillis();
                status = "Reading Logs";
                Iterator iter;
                if(year2>0 && month2>0 && day2>0) {
                    iter = getReportResults(tms, year1, month1, day1, year2, month2, day2);
                }else {
                    iter = getReportResults(tms, year1, month1, day1);
                }

                status = "Processing Logs";
                File reportdir = new File(SWBPlatform.getWorkPath() + "/logs/reports/");
                if (!reportdir.exists()) {
                    reportdir.mkdirs();
                }

                status = "Writing report";
                if( null==filename || (null!=filename && filename.trim().length()== 0)) {
                    filename = "CReport";
                }
                

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
                out.println("<b>Status</b>");
                out.println("</td><td bgcolor=\"gray\">");
                out.println("<b>Level</b>");
                out.println("</td></tr>");

                while (iter.hasNext()) {
                    String[] arr_data = (String[]) iter.next();
                    String sectionTitle = arr_data[0];
                    String sectionHits = arr_data[1];
                    String acumulate = arr_data[2];
                    String sectionLevel = arr_data[3];
                    String sectionID = arr_data[4];
                    String sectionActive = arr_data[5];
                    String sectionChilds = arr_data[6];

                    int nivel = Integer.parseInt(sectionLevel,10);
                    out.print("<tr><td>");
                    out.print(sectionID + "\t &nbsp;&nbsp;");
                    out.print("</td><td>");
                    for (int le = 0; le < nivel; le++) {
                        out.print("\t &nbsp;&nbsp;");
                    }
                    out.print(sectionTitle + "\t");
                    out.print("</td><td>");
                    out.print(sectionHits + "\t");
                    out.print("</td><td>");
                    out.print(acumulate + "\t");
                    out.print("</td><td>");
                    out.println(sectionChilds);
                    out.print("</td><td>");
                    out.println(sectionActive);
                    out.print("</td><td>");
                    out.println(sectionLevel);
                    out.print("</td></tr>");
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
        status = "Finalized";
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
        gen.setManager(this);
        gen.start();
    }

    public void stop() {
        if(gen != null) {
            gen = null;
        }
    }

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
