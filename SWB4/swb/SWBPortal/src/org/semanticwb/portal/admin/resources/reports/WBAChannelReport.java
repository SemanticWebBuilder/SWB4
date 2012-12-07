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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
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

// TODO: Auto-generated Javadoc
/**
 * The Class WBAChannelReport.
 */
public class WBAChannelReport extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBAChannelReport.class);

    /** The Constant I_REPORT_TYPE. */
    public static final int I_REPORT_TYPE = 3;
    
    /** The str rsc type. */
    private String strRscType;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#init()
     */
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
        if(paramsRequest.getMode().equals("report_xml")) {
            doRepXml(request, response, paramsRequest);
        }else if(paramsRequest.getMode().equals("download")) {
            doDownload(request, response, paramsRequest);
        }else {
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
System.out.println("\n\n----------------\nchannel report... doview");
        final int I_ACCESS = 0;
        Resource base = getResourceBase();
        StringBuilder sb_ret = new StringBuilder();

        ChannelReportMgr rm = ChannelReportMgr.getInstance();
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
                        hm_sites.put(site.getId(), site.getDisplayTitle(paramsRequest.getUser().getLanguage()));
                    }
                }

                // If there are sites continue
                if (hm_sites.size() > I_ACCESS) {
                    String websiteId = request.getParameter("wb_site")==null ? (String)hm_sites.keySet().iterator().next():request.getParameter("wb_site");
                    
                    int groupDates;
                    try {
                        groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
                    }catch(NumberFormatException e) {
                        groupDates = 0;
                    }

                    GregorianCalendar cal = new GregorianCalendar();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fecha1 = request.getParameter("wb_fecha1")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha1");
                    try {
                        sdf.parse(fecha1);
                    }catch(ParseException pe){
                        fecha1 = sdf.format(cal.getTime());
                    }
                    String fecha11 = request.getParameter("wb_fecha11")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha11");
                    try {
                        sdf.parse(fecha11);
                    }catch(ParseException pe){
                        fecha11 = sdf.format(cal.getTime());
                    }
                    String fecha12 = request.getParameter("wb_fecha12")==null ? sdf.format(cal.getTime()):request.getParameter("wb_fecha12");
                    try {
                        sdf.parse(fecha12);
                    }catch(ParseException pe){
                        fecha12 = sdf.format(cal.getTime());
                    }

                    sb_ret.append("<script type=\"text/javascript\">\n");
                    sb_ret.append(" dojo.addOnLoad(doBlockade);");

                    sb_ret.append(" function getParams() {");
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
                    sb_ret.append(" }\n");

                    sb_ret.append(" function doXml(size) { ");
                    sb_ret.append("      var params = getParams();");
                    sb_ret.append("      window.open(\"").append(paramsRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setMode("report_xml")).append("\"+params,\"graphWindow\",size);");
                    sb_ret.append(" }\n");

                    sb_ret.append(" function getTypeSelected() {");
                    sb_ret.append("    var strType = \"0\";");
                    sb_ret.append("    for(i=0;i<window.document.frmrep.wb_rep_type.length;i++){");
                    sb_ret.append("      if(window.document.frmrep.wb_rep_type[i].checked==true){");
                    sb_ret.append("          strType=window.document.frmrep.wb_rep_type[i].value;");
                    sb_ret.append("      }");
                    sb_ret.append("    }");
                    sb_ret.append("    return strType;");
                    sb_ret.append(" }\n");

                    sb_ret.append(" function doApply() {");
                    sb_ret.append("   tmp = dojo.byId('filename').value;");
                    sb_ret.append("   tmp2=tmp.replace(/ /g,'');");
                    sb_ret.append("   if(tmp2.length==0) {");
                    sb_ret.append("      alert('").append(paramsRequest.getLocaleString("file_name")).append("');");
                    sb_ret.append("      dojo.byId('filename').select();");
                    sb_ret.append("      dojo.byId('filename').focus();");
                    sb_ret.append("      return false;");
                    sb_ret.append("   }else {");
                    sb_ret.append("      dojo.byId('frmrep').submit();");
                    sb_ret.append("   }");
                    sb_ret.append(" }\n");
                    
                    sb_ret.append(" function doBlockade() {");
                    sb_ret.append("   if(window.document.frmrep.wb_rep_type) {");
                    sb_ret.append("      if(window.document.frmrep.wb_rep_type[0].checked){");
                    sb_ret.append("         dojo.byId('wb_fecha1').disabled = false;");
                    sb_ret.append("         dojo.byId('wb_fecha11').disabled = true;");
                    sb_ret.append("         dojo.byId('wb_fecha12').disabled = true;");
                    sb_ret.append("      }");
                    sb_ret.append("      if(window.document.frmrep.wb_rep_type[1].checked){");
                    sb_ret.append("         dojo.byId('wb_fecha1').disabled = true;");
                    sb_ret.append("         dojo.byId('wb_fecha11').disabled = false;");
                    sb_ret.append("         dojo.byId('wb_fecha12').disabled = false;");
                    sb_ret.append("      }");
                    sb_ret.append("   }");
                    sb_ret.append(" }\n");
                    
                    sb_ret.append(" function doDownload(name,key){");
                    sb_ret.append("   window.location='").append(paramsRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setMode("download")).append("?filename='+name+'&key='+key;");
                    sb_ret.append(" }");

                    sb_ret.append("</script>\n");
                    // javascript

                    sb_ret.append("<div class=\"swbform\">");
                    sb_ret.append("<fieldset>");
                    sb_ret.append(paramsRequest.getLocaleString("channel_report"));
                    sb_ret.append("</fieldset>");
                    
                    SWBResourceURL url = paramsRequest.getActionUrl();
                    sb_ret.append("<form name=\"frmrep\" id=\"frmrep\" method=\"post\" action=\""+url+"\">");
                    sb_ret.append("<fieldset>");
                    sb_ret.append("<legend>"+paramsRequest.getLocaleString("filter")+"</legend>");
                    sb_ret.append("<table border=\"0\" width=\"95%\" align=\"center\">");
                    sb_ret.append("<tr><td width=\"213\"></td><td width=\"146\"></td><td width=\"157\"></td><td width=\"413\"></td></tr>");
                    
                    sb_ret.append("<tr>");
                    sb_ret.append("<td>").append(paramsRequest.getLocaleString("site")).append(":</td>");
                    sb_ret.append("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" size=\"1\">");
                    Iterator<String> itKeys = hm_sites.keySet().iterator();
                    while(itKeys.hasNext()) {
                        String key = itKeys.next();
                        sb_ret.append("<option value=\"").append(key).append("\"");
                        if(key.equalsIgnoreCase(websiteId)) {
                            sb_ret.append(" selected=\"selected\"");
                        }
                        sb_ret.append(">").append((String)hm_sites.get(key)).append("</option>");
                    }
                    sb_ret.append("</select>");
                    sb_ret.append("</td>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("</tr>");

                    sb_ret.append("<tr>");
                    sb_ret.append("<td>");
                    sb_ret.append("<label>");
                    sb_ret.append("<input type=\"radio\" value=\"0\" name=\"wb_rep_type\" id=\"wb_rep_type_0\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates==0) {
                        sb_ret.append(" checked=\"checked\"");
                    }
                    sb_ret.append(" />");
                    sb_ret.append("&nbsp;").append(paramsRequest.getLocaleString("by_day"));
                    sb_ret.append("</label></td>");
                    sb_ret.append("<td colspan=\"2\">");

                    sb_ret.append("<input type=\"text\" name=\"wb_fecha1\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha1\" dojoType=\"dijit.form.DateTextBox\" constraints=\"{datePattern:'dd/MM/yyyy'}\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\"").append(fecha1).append("\"/>");

                    sb_ret.append("</td>");
                    sb_ret.append("<td><input type=\"hidden\" id=\"wb_rtype\" name=\"wb_rtype\" value=\"0\" /></td>");
                    sb_ret.append("</tr>");

                    sb_ret.append("<tr>");
                    sb_ret.append("<td>");
                    sb_ret.append("<label>");
                    sb_ret.append("<input type=\"radio\" value=\"1\" name=\"wb_rep_type\" id=\"wb_rep_type_1\" onclick=\"javascript: doBlockade();\"");
                    if(groupDates!=0) {
                        sb_ret.append(" checked=\"checked\"");
                    }
                    sb_ret.append(" />");
                    sb_ret.append("&nbsp;").append(paramsRequest.getLocaleString("by_range"));
                    sb_ret.append("</label></td>");
                    sb_ret.append("<td>");
                    sb_ret.append("<input type=\"text\" name=\"wb_fecha11\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha11\" dojoType=\"dijit.form.DateTextBox\" constraints=\"{datePattern:'dd/MM/yyyy'}\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\"").append(fecha11).append("\"/>");
                    sb_ret.append("</td>");
                    sb_ret.append("<td>");
                    sb_ret.append("<input type=\"text\" name=\"wb_fecha12\" onblur=\"if(!this.value){this.focus();}\" id=\"wb_fecha12\" dojoType=\"dijit.form.DateTextBox\" constraints=\"{datePattern:'dd/MM/yyyy'}\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\" value=\"").append(fecha12).append("\"/>");
                    sb_ret.append("</td>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("</tr>");

                    sb_ret.append("<tr>");
                    sb_ret.append("<td>");
                    sb_ret.append(paramsRequest.getLocaleString("report_name")).append(":");
                    sb_ret.append("</td>");
                    sb_ret.append("<td colspan=\"3\">");
                    sb_ret.append("<input type=\"text\" name=\"filename\" id=\"filename\" value=\"\"/>");
                    sb_ret.append("&nbsp;<font size=\"1\">* ").append(paramsRequest.getLocaleString("report_file_generated")).append("</font>");
                    sb_ret.append("</td>");
                    sb_ret.append("</tr>");
                    sb_ret.append("</table>");
                    sb_ret.append("</fieldset>");

                    sb_ret.append("<fieldset>");
                    sb_ret.append("<table border=\"0\" width=\"95%\">");
                    sb_ret.append("<tr>");
                    sb_ret.append(" <td colspan=\"4\">&nbsp;&nbsp;&nbsp;");
                    sb_ret.append("   <button dojoType=\"dijit.form.Button\" onclick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;");
                    sb_ret.append("   <button dojoType=\"dijit.form.Button\" onclick=\"doApply()\">").append(paramsRequest.getLocaleString("apply")).append("</button>");
                    sb_ret.append("   <input type=\"hidden\" name=\"wb_rfilter\" value=\"").append(s_rfilter).append("\"/>");
                    sb_ret.append(" </td>");
                    sb_ret.append("</tr>");
                    sb_ret.append("</table>");
                    sb_ret.append("</fieldset>");

                    sb_ret.append("</form>");                    
                    
                    sb_ret.append("<fieldset>");
                    sb_ret.append("<table border=\"0\" width=\"95%\" align=\"center\">");
                    sb_ret.append("<tr>");
                    sb_ret.append("<td colspan=\"4\">");
                    
                    HashMap hm = getFileList(paramsRequest.getWebPage());
                    if (hm.isEmpty()) {
                        sb_ret.append(paramsRequest.getLocaleString("reports_not_found"));
                    } else {
                        sb_ret.append("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">");
                        sb_ret.append("<caption align=\"top\">").append(paramsRequest.getLocaleString("list_reports")).append("</caption>");
                        sb_ret.append("<tr><th width=\"15%\">").append(paramsRequest.getLocaleString("action")).append("</th><th width=\"35%\">").append(paramsRequest.getLocaleString("file_name")).append("</th><th width=\"25%\">").append(paramsRequest.getLocaleString("site")).append("</th><th width=\"25%\">").append(paramsRequest.getLocaleString("created")).append("</th></tr>");

                        boolean toggleColor = true;
                        SWBResourceURL urlDel = paramsRequest.getActionUrl().setAction("remove");
                        String[] arrhm = new String[hm.size()];
                        Arrays.sort(hm.keySet().toArray(arrhm));
                        for(int i=0; i<arrhm.length; i++) {
                            String key = arrhm[i];
                            String sfn = (String)hm.get(key);
                            String site = sfn.substring(0, sfn.lastIndexOf("|"));
                            String filename = sfn.substring(sfn.lastIndexOf("|") + 1);

                            sb_ret.append("<tr bgcolor=\"").append(toggleColor?"#EFEDEC":"#FFFFFF").append("\">");
                            urlDel.setParameter("key", key);
                            sb_ret.append("<td><a href=\"").append(urlDel).append("\" onclick=\" if(confirm('").append(paramsRequest.getLocaleString("alert_remove_file")).append("?')){return true;}else{return false};\"><img border=\"0\" src=\"").append(SWBPlatform.getContextPath()).append("/swbadmin/images/delete.gif\" alt=\"").append(paramsRequest.getLocaleString("delete_report")).append("\"></a></td>");
                            sb_ret.append("<td><a href=\"javascript:doDownload('").append(filename).append("','").append(key).append("');\" alt=\"Reporte: ").append(filename).append("\">").append(filename).append("</a></td>");
                            sb_ret.append("<td>").append(site).append("</td>");
                            sb_ret.append("<td>").append(SWBUtils.TEXT.iso8601DateFormat(new java.sql.Timestamp(Long.parseLong(key)))).append("</td>");
                            sb_ret.append("</tr>");
                            toggleColor = !(toggleColor);
                        }
                        sb_ret.append("</table>");
                    }
                    sb_ret.append("</td></tr>");
                    sb_ret.append("</table>");
                    sb_ret.append("</fieldset>");
                    sb_ret.append("</div>");
                }else { // There are not sites and displays a message
                    sb_ret.append("<div class=\"swbform\">");
                    sb_ret.append("<fieldset>");
                    sb_ret.append("<legend>").append(paramsRequest.getLocaleString("channel_report")).append("</legend>");
                    sb_ret.append("<form method=\"Post\" class=\"box\" action=\"").append(paramsRequest.getWebPage().getUrl()).append("\" id=\"frmrep\" name=\"frmrep\">");
                    sb_ret.append("<table border=0 width=\"100%\">");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                    sb_ret.append("<tr>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("<td colspan=\"2\" align=\"center\" class=\"datos\">").append(paramsRequest.getLocaleString("no_sites_found")).append("</td>");
                    sb_ret.append("<td>&nbsp;</td>");
                    sb_ret.append("</tr>");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                    sb_ret.append("<tr><td colspan=\"4\">&nbsp;</td></tr>");
                    sb_ret.append("</table></form>");
                    sb_ret.append("</fieldset>");
                    sb_ret.append("</div>");
                }
            }catch(Exception e) {
                log.error("Error on method DoView() resource " + " " + strRscType + " " + "with id" + " " + base.getId(),e);
            }
        }else {
            DateFormat df = null;

            ChannelReportMgr rmgr = ChannelReportMgr.getInstance();
            sb_ret.append("<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">");
            sb_ret.append("<tr><td colspan=\"2\">");
            sb_ret.append(paramsRequest.getLocaleString("channel_report"));
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td width=\"200\" align=\"right\">");
            sb_ret.append(paramsRequest.getLocaleString("status")).append(":</td><td>").append(rmgr.getStatus());
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td width=\"200\" align=\"right\">");

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(rmgr.getInitTime());
            df = DateFormat.getTimeInstance(DateFormat.FULL);
            String s_time = df.format(gc.getTime());
            sb_ret.append(paramsRequest.getLocaleString("initial_time")).append(":</td><td>").append(s_time);
            sb_ret.append("</td></tr>");
            sb_ret.append("<tr><td width=\"200\" align=\"right\">");
            sb_ret.append(paramsRequest.getLocaleString("process_time")).append(":</td><td>").append(getAvailableTime(new Timestamp(gc.getTimeInMillis())));
            sb_ret.append("</td></tr>");
            /*sb_ret.append("<tr><td width=\"200\" align=\"right\">");
            sb_ret.append(paramsRequest.getLocaleString("log_counter") + ":</td><td>" + rmgr.getCounter());
            sb_ret.append("</td></tr>");*/
            sb_ret.append("</table>\n");

            sb_ret.append("\n<META HTTP-EQUIV=\"Refresh\" CONTENT=\"3;URL=").append(paramsRequest.getRenderUrl()).append("\">\n");
        }
        response.getWriter().print(sb_ret.toString());
    }

    /**
     * Gets the available time.
     * 
     * @param inicio the inicio
     * @return the available time
     */
    public String getAvailableTime(Timestamp inicio) {
        StringBuilder ret = new StringBuilder();
        Timestamp min_actual = inicio;
        Timestamp limite = new java.sql.Timestamp(System.currentTimeMillis());

        ret.append("\n<script language=\"JavaScript\">");
        ret.append(" CountActive = true;\n");
        ret.append(" DisplayFormat = \"%%H%%:%%M%%:%%S%%\";\n");
        ret.append(" function calcage(secs, num1, num2) {");
        ret.append("  s = ((Math.floor(secs/num1))%num2).toString();");
        ret.append("  if (s.length < 2)");
        ret.append("    s = \"0\" + s;");
        ret.append("  return \"<strong>\" + s + \"</strong>\";");
        ret.append(" }\n");

        ret.append(" function CountBack(secs) {");
        ret.append("  DisplayStr = DisplayFormat.replace(/%%D%%/g, calcage(secs,86400,100000));");
        ret.append("  DisplayStr = DisplayStr.replace(/%%H%%/g, calcage(secs,3600,24));");
        ret.append("  DisplayStr = DisplayStr.replace(/%%M%%/g, calcage(secs,60,60));");
        ret.append("  DisplayStr = DisplayStr.replace(/%%S%%/g, calcage(secs,1,60));");

        ret.append("  document.getElementById(\"cntdwn\").innerHTML = DisplayStr;");
        ret.append("  if (CountActive)");
        ret.append("    setTimeout(\"CountBack(\" + (secs+1) + \")\", 990);");
        ret.append(" }\n");

        ret.append(" function showTimer() {");
        ret.append("   document.write(\"<span id='cntdwn' class=valores></span>\");");
        ret.append(" }\n");

        ret.append(" showTimer();\n");
        ret.append(" var dthen = new Date();\n");
        ret.append(" dthen.setTime(").append(limite.getTime()).append(");\n");
        ret.append(" var dnow = new Date();\n");
        ret.append(" dnow.setTime(").append(min_actual.getTime()).append(");\n");
        ret.append(" ddiff = new Date(dthen-dnow);\n");
        ret.append(" gsecs = Math.floor(ddiff.valueOf()/1000);\n");
        ret.append(" CountBack(gsecs);\n");
        ret.append("</script>\n");

        return ret.toString();
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
        response.setContentType("text/xml;charset=utf-8");
        Document dom = SWBUtils.XML.getNewDocument();
        Element report = dom.createElement("ChannelReport");

        String websiteId = request.getParameter("wb_site");
        WebSite webSite = SWBContext.getWebSite(websiteId);
        int groupDates;
        try {
            groupDates = request.getParameter("wb_rep_type")==null ? 0:Integer.parseInt(request.getParameter("wb_rep_type"));
        }catch(NumberFormatException e) {
            groupDates = 0;
        }
        String fecha1 = request.getParameter("wb_fecha1")==null ? "":request.getParameter("wb_fecha1");
        String fecha11 = request.getParameter("wb_fecha11")==null ? "":request.getParameter("wb_fecha11");
        String fecha12 = request.getParameter("wb_fecha12")==null ? "":request.getParameter("wb_fecha12");

        report.setAttribute("portal", websiteId);

        ChannelReportGenerator gen;
        if(groupDates==0) {
            report.setAttribute("inicio", fecha1.replace('-', '/'));
            gen = new ChannelReportGenerator(null, websiteId, Integer.parseInt(fecha1.substring(0,4)), Integer.parseInt(fecha1.substring(5,7)), Integer.parseInt(fecha1.substring(8)), null);
        }else {
            report.setAttribute("inicio", fecha11.replace('-', '/'));
            report.setAttribute("fin", fecha12.replace('-', '/'));
            gen = new ChannelReportGenerator(null, websiteId, Integer.parseInt(fecha11.substring(0,4)), Integer.parseInt(fecha11.substring(5,7)), Integer.parseInt(fecha11.substring(8)), Integer.parseInt(fecha12.substring(0,4)), Integer.parseInt(fecha12.substring(5,7)), Integer.parseInt(fecha12.substring(8)), null);
        }
        gen.getHardHits();
        PortalTree ptree = new PortalTree();
        gen.getReportResults(ptree);
        gen.getUnknownResults(ptree);
        ptree.postorden();
        ArrayList<Node> al = new ArrayList();
        ptree.preorden(al);
        Iterator<Node> iter = al.iterator();

        dom.appendChild(report);
        String name, levelname, havingChilds, activename;
        int renglones = 0;
        while (iter.hasNext()) {
            Node node = iter.next();
            WebPage page = webSite.getWebPage(node.id);
            if( page!=null ) {
                name = page.getDisplayName();
                levelname = Integer.toString(page.getLevel());
                havingChilds = page.listChilds().hasNext()?"true":"false";
                activename = Boolean.toString(page.isActive());
            }else {
                name = ChannelReportGenerator.UNKNOWN;
                levelname = "1";
                havingChilds = "-";
                activename = "-";
            }
            int l = Integer.parseInt(levelname);

            Element row = dom.createElement("row");
            row.appendChild(dom.createTextNode(""));
            row.setAttribute("id",Integer.toString(++renglones));
            report.appendChild(row);
            Element title = dom.createElement("sectionTitle");
            title.appendChild(dom.createTextNode(name));
            row.appendChild(title);
            Element hits = dom.createElement("sectionHits");
            hits.appendChild(dom.createTextNode(Long.toString(node.hits)));
            row.appendChild(hits);
            Element total = dom.createElement("acumulate");
            total.appendChild(dom.createTextNode(Long.toString(node.acumulate)));
            row.appendChild(total);
            Element level = dom.createElement("sectionLevel");
            level.appendChild(dom.createTextNode(levelname));
            row.appendChild(level);
            Element id = dom.createElement("sectionId");
            id.appendChild(dom.createTextNode(node.id));
            row.appendChild(id);
            Element active = dom.createElement("sectionActive");
            active.appendChild(dom.createTextNode(activename));
            row.appendChild(active);
            Element childs = dom.createElement("sectionChilds");
            childs.appendChild(dom.createTextNode(havingChilds));
            row.appendChild(childs);

        }
        report.setAttribute("rows",Integer.toString(renglones));
        
        PrintWriter out = response.getWriter();
        out.print(SWBUtils.XML.domToXml(dom));
        out.flush();
        out.close();
    }
    
    /**
     * Update report.
     * 
     * @param request the request
     * @param topic the topic
     */
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
        ChannelReportMgr mgr = ChannelReportMgr.getInstance();
        if(groupDates==0) {
            mgr.start(s_site, Integer.parseInt(fecha1.substring(0,4)), Integer.parseInt(fecha1.substring(5,7)), Integer.parseInt(fecha1.substring(8)), filename);
        }else {
            mgr.start(s_site, Integer.parseInt(fecha11.substring(0,4)), Integer.parseInt(fecha11.substring(5,7)), Integer.parseInt(fecha11.substring(8)), Integer.parseInt(fecha12.substring(0,4)), Integer.parseInt(fecha12.substring(5,7)), Integer.parseInt(fecha12.substring(8)), filename);
        }

        try {
            String baseXML = base.getData(topic);
            Element files = null;
            Document dom = SWBUtils.XML.xmlToDom(baseXML);
            if(dom==null) {
                dom = SWBUtils.XML.getNewDocument();
                files = dom.createElement("files");
                dom.appendChild(files);
            }else {
                files = (Element) dom.getElementsByTagName("files").item(0);
            }
            Element file = dom.createElement("file");
            file.setAttribute("name", filenameLogic+".xls");
            file.setAttribute("id", filename);
            file.setAttribute("site", s_site);
            files.appendChild(file);
            
            base.setData(topic, SWBUtils.XML.domToXml(dom));
        } catch (Exception edom) {
            log.error("Error on method updateReport(), Was imposible to generate the files DOM. Resource " + strRscType + " with id " + base.getId(), edom);
        }
    }

    /**
     * Gets the file list.
     * 
     * @param topic the topic
     * @return the file list
     */
    public HashMap getFileList(WebPage topic) {
        Resource base = getResourceBase();
        HashMap hm = new HashMap();
        try {
            Document dom = null;
            Element files = null;
            dom = SWBUtils.XML.xmlToDom(base.getData(topic));
            if(dom!=null) {
                files = (Element) dom.getElementsByTagName("files").item(0);
                NodeList nl = files.getElementsByTagName("file");
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
     * Process action.
     * 
     * @param request the request
     * @param response the response
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("processAction...");
        String filename = request.getParameter("filename");
        String key = request.getParameter("key");
        String act = response.getAction();
        WebPage topic = response.getWebPage();
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
                    //if (nl.getLength() > -1) {
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
                            File frem = new File(SWBPortal.getWorkPath()+"/logs/reports/" + key + ".xls");
                            if (frem.exists()) {
                                eliminar = frem.delete();
                            }
                        }
                    //}
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

    /**
     * Do download.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
            File file = new File(SWBPortal.getWorkPath() + "/logs/reports/" + sfile);
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
                out.close();
            }
        }
    }
}


class Node {
    String id;
    long hits, acumulate;
    ArrayList<Node> childs;

    Node(String id) {
        this.id = id;
        childs = new ArrayList<Node>();
    }

    void setHits(long hits) {
        this.hits = this.acumulate = hits;
    }
}




class PortalTree {
    Node root;

    PortalTree() {
        this(null);
    }

    PortalTree(Node node) {
        root = node;
    }

    Node getRoot() {
        return root;
    }
    
    void insert(Node root) {
        if(this.root==null) {
            this.root = root;
        }
    }

    void insert(Node parent, Node child) {
        if(root==null) {
            root = parent;
            root.childs.add(child);
        }else if(parent.id.equalsIgnoreCase(root.id)) {
            root.childs.add(child);
        }else {
            insert(root.childs.iterator(), parent, child);
        }
    }
    
    private void insert(Iterator<Node> nodes, Node parent, Node child) {
        while(nodes.hasNext()) {
            Node p = nodes.next();
            if(p.childs.iterator().hasNext())
                insert(p.childs.iterator(), parent, child);
            if(p.id.equalsIgnoreCase(parent.id)) {
                p.childs.add(child);
                return;
            }
        }

    }

    void postorden(Node node) {
        Iterator<Node> childs = node.childs.iterator();
        while(childs.hasNext()) {
            Node child = childs.next();
            postorden(child);
            node.acumulate += child.acumulate;
        }
    }

    void postorden() {
        postorden(root);
    }

    void preorden(Node node) {

    }

    void preorden() {
        preorden(root);
    }

    void preorden(ArrayList<Node> l) {
        preorden(root, l);
    }

    void preorden(Node node, ArrayList<Node> l) {
        l.add(node);
        Iterator<Node> childs = node.childs.iterator();
        while(childs.hasNext()) {
            Node child = childs.next();
            preorden(child, l);
        }
    }
    
    public void print(Node node) {
        System.out.println("section= "+node.id+", hits="+node.hits+", acumulate="+node.acumulate);
    }
}



class ChannelReportMgr {
    private static ChannelReportMgr instance;
    private ChannelReportGenerator gen;
    private boolean isRunning = false;

    private ChannelReportMgr() {
    }

    public static ChannelReportMgr getInstance() {
        if(instance == null) {
            instance = new ChannelReportMgr();
        }
        return instance;
    }

    public void start(String websiteId, int year, int month, int day, String filename) {
        if(!isRunning) {
            gen = new ChannelReportGenerator(instance, websiteId, year, month, day, filename);
            gen.start();
        }
    }

    public void start(String websiteId, int yearI, int monthI, int dayI, int yearF, int monthF, int dayF, String filename) {
        if(!isRunning) {
            gen = new ChannelReportGenerator(instance, websiteId, yearI, monthI, dayI, yearF, monthF, dayF, filename);
            gen.start();
        }
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
    synchronized void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public String getStatus() {
        return gen.getStatus();
    }

    public long getInitTime() {
        return gen.getInitTime();
    }

    public long getEndTime() {
        return gen.getEndTime();
    }    
}

class ChannelReportGenerator extends Thread {
    private Logger log = SWBUtils.getLogger(WBADeviceReport.class);

    public static final int I_REPORT_TYPE = 3;
    public static final String UNKNOWN = "_Desconocidos_";

    private ChannelReportMgr manager;
    private int year1,  month1,  day1,  year2,  month2,  day2;
    private long initTime = 0;
    private long endTime = 0;

    private String status;
    private String websiteId;
    private String filename;

    WebSite webSite;

    HashMap<String, Long> hits = new HashMap();

    ChannelReportGenerator(ChannelReportMgr manager, String websiteId, int year1, int month1, int day1) {
        this(manager, websiteId, year1, month1, day1, 0, 0, 0, null);
    }

    ChannelReportGenerator(ChannelReportMgr manager, String websiteId, int year1, int month1, int day1, int year2, int month2, int day2) {
        this(manager, websiteId, year1, month1, day1, year2, month2, day2, null);
    }

    ChannelReportGenerator(ChannelReportMgr manager, String websiteId, int year1, int month1, int day1, String filename) {
        this(manager, websiteId, year1, month1, day1, 0, 0, 0, filename);
    }

    ChannelReportGenerator(ChannelReportMgr manager, String websiteId, int year1, int month1, int day1, int year2, int month2, int day2, String filename) {
        this.manager = manager;
        this.websiteId = websiteId;
        this.year1 = year1;
        this.month1 = month1;
        this.day1 = day1;
        this.year2 = year2;
        this.month2 = month2;
        this.day2 = day2;
        this.filename = filename;
        this.status = "Initializing...";

        webSite = SWBContext.getWebSite(websiteId);
    }

    synchronized void getReportResults(PortalTree ptree) {
        getReportResults(webSite.getHomePage(), ptree);
    }

    synchronized void getReportResults(WebPage page, PortalTree ptree) {
        Node parent = new Node(page.getId());
        if( hits.get(page.getId())!=null ) {
            parent.setHits((Long)hits.get(page.getId()));
            hits.remove(page.getId());
        }
        ptree.insert(parent);

        Iterator<WebPage> childs = page.listChilds();
        while(childs.hasNext()) {
            WebPage wp = childs.next();
            Node child = new Node(wp.getId());
            if( hits.get(wp.getId())!=null ) {
                child.setHits((Long)hits.get(wp.getId()));
                hits.remove(wp.getId());
            }
            ptree.insert(parent, child);
            if(wp.listChilds().hasNext())
                getReportResults(wp, ptree);
        }
    }

    synchronized void getUnknownResults(PortalTree ptree) {
        long hardAcumulated = 0;
        Iterator<String> keys = hits.keySet().iterator();
        while(keys.hasNext()) {
            String k = keys.next();
            hardAcumulated += hits.get(k);
        }
        Node node = new Node(UNKNOWN);
        node.setHits(hardAcumulated);
        ptree.insert(ptree.getRoot(), node);
    }

    synchronized void getHardHits() {
        Iterator<SWBRecHit> iterHits;
        if(year2>0 && month2>0 && day2>0) {
            iterHits = SWBRecHits_.getInstance().getResHitsLog(websiteId, I_REPORT_TYPE, year1, month1, day1, year2, month2, day2).iterator();
        }else {
            iterHits = SWBRecHits_.getInstance().getResHitsLog(websiteId, I_REPORT_TYPE, year1, month1, day1).iterator();
        }
        while(iterHits.hasNext()) {
            SWBRecHit recHits = iterHits.next();
            hits.put(recHits.getSection(), recHits.getHits());
        }
    }

    @Override
    public void run() {
        manager.setRunning(true);
        status = "Initializing";
        initTime = System.currentTimeMillis();
        
        if(webSite != null) {
            status = "Reading Logs";
            getHardHits();
            PortalTree ptree = new PortalTree();
            getReportResults(webSite.getHomePage(), ptree);
            getUnknownResults(ptree);

            status = "Processing Logs";
            ptree.postorden();
            try {
                File reportdir = new File(SWBPortal.getWorkPath() + "/logs/reports/");
                if (!reportdir.exists()) {
                    reportdir.mkdirs();
                }

                status = "Writing report";
                if( null==filename || (null!=filename && filename.trim().length()== 0)) {
                    filename = "ChannelReport";
                }

                PrintWriter out = new PrintWriter(new FileOutputStream(SWBPortal.getWorkPath() + "/logs/reports/" + filename + ".xls"));
                out.println("<table border=\"1\" cellpadding=\"2\">");
                // titulo del reporte
                out.println("<tr><td colspan=\"7\" align=\"center\" bgcolor=\"#666666\"><strong>Reporte por canal - "+websiteId+"</strong></td></tr>");
                // periodo reportado
                out.println("<tr><td colspan=\"7\" align=\"center\" bgcolor=\"#666666\">");
                out.println("<strong>Período de consulta: "+day1+"/"+month1+"/"+year1+((year2>0&&month2>0&&day2>0)?" - "+day2+"/"+month2+"/"+year2:"")+"</strong>");
                out.println("</td></tr>");
                out.println("<tr align=\"center\">");
                out.println("<td bgcolor=\"#666666\"><strong>Id</strong></td>");
                out.println("<td bgcolor=\"#666666\"><strong>Título de la sección</strong></td>");
                out.println("<td bgcolor=\"#666666\"><strong>Hits</strong></td>");
                out.println("<td bgcolor=\"#666666\"><strong>Acumulado</strong></td>");
                out.println("<td bgcolor=\"#666666\"><strong>Tiene subsecciones</strong></td>");
                out.println("<td bgcolor=\"#666666\"><strong>es Activo</strong></td>");
                out.println("<td bgcolor=\"#666666\"><strong>Nivel</strong></td>");
                out.println("</tr>");

                ArrayList<Node> al = new ArrayList();
                ptree.preorden(al);
                Iterator<Node> iter = al.iterator();

                String bgcolor;
                String name, level, havingChilds, active;
                while (iter.hasNext()) {
                    Node node = iter.next();
                    WebPage page = webSite.getWebPage(node.id);
                    if( page!=null ) {
                        name = page.getDisplayName();
                        level = Integer.toString(page.getLevel());
                        havingChilds = page.listChilds().hasNext()?"true":"false";
                        active = Boolean.toString(page.isActive());
                    }else {
                        name = UNKNOWN;
                        level = "1";
                        havingChilds = "-";
                        active = "-";
                    }
                    int l = Integer.parseInt(level);
                    if(l==1)
                        bgcolor="#CCCCCC";
                    else
                        bgcolor="#FFFFFF";
                    out.print("<tr>");
                    out.print("<td bgcolor=\""+bgcolor+"\">");
                    out.print(node.id + "\t");
                    out.print("</td><td bgcolor=\""+bgcolor+"\">");
                    for (int le = 0; le < l; le++) {
                        out.print("\t &nbsp;&nbsp;");
                    }
                    out.print(name + "\t");
                    out.print("</td><td bgcolor=\""+bgcolor+"\">");
                    out.print(node.hits);
                    out.print("</td><td bgcolor=\""+bgcolor+"\">");
                    out.print(node.acumulate);
                    out.print("</td><td bgcolor=\""+bgcolor+"\">");
                    out.println(havingChilds);
                    out.print("</td><td bgcolor=\""+bgcolor+"\">");
                    out.println(active);
                    out.print("</td><td bgcolor=\""+bgcolor+"\">");
                    out.println(level);
                    out.print("</td></tr>");
                }
                out.println("</table>");
                out.flush();
                out.close();                
            }catch (Exception e) {
                log.error("Error on method ChannelReportGenerator.run(). ",e);
                manager.setRunning(false);
                status = "Error on method ChannelReportGenerator.run()";
                endTime = System.currentTimeMillis();
                return;
            }
        }
        manager.setRunning(false);
        status = "Finalized";
        endTime = System.currentTimeMillis();
    }

    /**
     * Getter for property status.
     * @return Value of property status.
     */
    String getStatus() {
        return status;
    }

    /**
     * Getter for property ini.
     * @return Value of property ini.
     */
    long getInitTime() {
        return initTime;
    }

    /**
     * Getter for property fin.
     * @return Value of property fin.
     */
    long getEndTime() {
        return endTime;
    }

    String getWebsiteId() {
        return websiteId;
    }

    int getYear1() {
        return year1;
    }

    int getMonth1() {
        return month1;
    }

    int getDay1() {
        return day1;
    }

    int getYear2() {
        return year2;
    }

    int getMonth2() {
        return month2;
    }

    int getDay2() {
        return day2;
    }
}