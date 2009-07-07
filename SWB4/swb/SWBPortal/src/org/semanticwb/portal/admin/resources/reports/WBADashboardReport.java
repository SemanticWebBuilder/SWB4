package org.semanticwb.portal.admin.resources.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRGlobalAccessDataDetail;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.db.SWBRecHit;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.semanticwb.model.Device;
import org.semanticwb.model.Language;
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRDeviceAccessDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRLanguageAccessDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRLoggedUserDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRSessionDataDetail;
import org.semanticwb.portal.api.SWBResourceURL;

public class WBADashboardReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBASectionReport.class);

    private static final String REPORT_IDAUX_GLOBAL = "_";
    private static final String REPORT_IDAUX_SESSIONS = "_";
    private static final String REPORT_IDAUX_LOGINS = "_";
    private static final int REPORT_TYPE_GLOBAL = 0;
    private static final int REPORT_TYPE_DEVICES = 1;
    private static final int REPORT_TYPE_LANGUAGE = 2;
    private static final int REPORT_TYPE_SESSIONS = 5;
    private static final int REPORT_TYPE_LOGINS = 6;

    private String strRscType;
    
    @Override
    public void init(){
        Resource base = getResourceBase();
        try {
            strRscType = base.getResourceType().getResourceClassName();
        }catch (Exception e) {
            strRscType = "WBAContentsReport";
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();

        final int I_ACCESS = 0;
        HashMap sites = new HashMap();

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
                            sites.put(site.getId(), site.getDisplayTitle(paramsRequest.getUser().getLanguage()));
//                        }
//                    }
                }
            }
            // If there are sites continue
            if(sites.size() > I_ACCESS) {
                String address = paramsRequest.getTopic().getUrl();
                String websiteId = request.getParameter("wb_site")==null ? (String)sites.keySet().iterator().next():request.getParameter("wb_site");
                String language = paramsRequest.getUser().getLanguage();
                
                GregorianCalendar now = new GregorianCalendar();
                JRDataSourceable dataDetail;
                JRBeanCollectionDataSource ds;
                Iterator<SWBRecHit> globals = null;
                Iterator<SWBRecHit> sessions = null;
                Iterator<SWBRecHit> logins = null;
                HashMap devices = null;
                HashMap languages = null;
                SWBResourceURL url = paramsRequest.getRenderUrl();

                if(websiteId != null) {
                    // global.
                    WBAFilterReportBean filter = new WBAFilterReportBean();
                    filter.setSite(websiteId);
                    filter.setIdaux(REPORT_IDAUX_GLOBAL);
                    filter.setType(REPORT_TYPE_GLOBAL);
                    filter.setYearI(now.get(Calendar.YEAR));
                    dataDetail = new JRGlobalAccessDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        globals = ds.getData().iterator();
                    }else {
                        globals = Collections.EMPTY_LIST.iterator();
                    }

                    // session
                    filter = new WBAFilterReportBean();
                    filter.setSite(SWBContext.getWebSite(websiteId).getUserRepository().getId());
                    filter.setIdaux(REPORT_IDAUX_SESSIONS);
                    filter.setType(REPORT_TYPE_SESSIONS);
                    filter.setYearI(now.get(Calendar.YEAR));
                    dataDetail = new JRSessionDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        sessions = ds.getData().iterator();
                    }else {
                        sessions = Collections.EMPTY_LIST.iterator();
                    }

                    // loggin
                    filter = new WBAFilterReportBean();
                    filter.setSite(SWBContext.getWebSite(websiteId).getUserRepository().getId());
                    filter.setIdaux(REPORT_IDAUX_LOGINS);
                    filter.setType(REPORT_TYPE_LOGINS);
                    filter.setYearI(now.get(Calendar.YEAR));
                    dataDetail = new JRLoggedUserDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        logins = ds.getData().iterator();
                    }else {
                        logins = Collections.EMPTY_LIST.iterator();
                    }

                    // device
                    Iterator<SWBRecHit> idevices;
                    filter = new WBAFilterReportBean();
                    filter.setSite(websiteId);
                    //filter.setIdaux(dev.getId());
                    filter.setType(REPORT_TYPE_DEVICES);
                    filter.setYearI(now.get(Calendar.YEAR));
                    dataDetail = new JRDeviceAccessDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        idevices = ds.getData().iterator();
                    }else {
                        idevices = Collections.EMPTY_LIST.iterator();
                    }
                    devices = new HashMap();
                    while(idevices.hasNext()) {
                        SWBRecHit r = idevices.next();
                        if(devices.containsKey(r.getMonth())) {
                            HashMap c = (HashMap)devices.get(r.getMonth());
                            c.put(r.getItem(), Long.toString(r.getHits()));
                            System.out.println("key="+r.getItem()+" value="+r.getHits());
                        }else {
                            HashMap c = new HashMap();
                            devices.put(r.getMonth(), c);
                            c.put(r.getItem(), Long.toString(r.getHits()));
                            System.out.println("key="+r.getItem()+" value="+r.getHits());
                        }
                    }
                    Iterator<String> dbm = devices.keySet().iterator();
                    while(dbm.hasNext()) {
                        String key = dbm.next();
                        HashMap c = (HashMap)devices.get(key);
                        Iterator<Device> devs = SWBContext.getWebSite(websiteId).listDevices();
                        while(devs.hasNext()) {
                            Device d = devs.next();
                            if(!c.containsKey(d.getDisplayTitle(language))) {
                                c.put(d.getDisplayTitle(language), Long.toString(0L));
                            }
                        }
                    }

                    // language
                    Iterator<SWBRecHit> ilanguages;
                    filter = new WBAFilterReportBean();
                    filter.setSite(websiteId);
                    //filter.setIdaux(REPORT_IDAUX_GLOBAL);
                    filter.setType(REPORT_TYPE_LANGUAGE);
                    filter.setYearI(now.get(Calendar.YEAR));
                    dataDetail = new JRLanguageAccessDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        ilanguages = ds.getData().iterator();
                    }else {
                        ilanguages = Collections.EMPTY_LIST.iterator();
                    }
                    languages = new HashMap();
                    while(ilanguages.hasNext()) {
                        SWBRecHit r = ilanguages.next();
                        if(languages.containsKey(r.getMonth())) {
                            HashMap c = (HashMap)languages.get(r.getMonth());
                            c.put(r.getItem(), Long.toString(r.getHits()));
                        }else {
                            HashMap c = new HashMap();
                            languages.put(r.getMonth(), c);
                            c.put(r.getItem(), Long.toString(r.getHits()));
                        }
                    }
                    Iterator<String> lbm = languages.keySet().iterator();
                    while(lbm.hasNext()) {
                        String key = lbm.next();
                        HashMap c = (HashMap)languages.get(key);
                        Iterator<Language> langs = SWBContext.getWebSite(websiteId).listLanguages();
                        while(langs.hasNext()) {
                            Language l = langs.next();
                            if(!c.containsKey(l.getDisplayTitle(language))) {
                                c.put(l.getDisplayTitle(language), Long.toString(0L));
                            }
                        }
                    }
                }// if(websiteId != null)
                
                
                out.println("<script type=\"text/javascript\">");
                out.println(" function doApply() { ");
                out.println("    window.document.frmrep.submit(); ");
                out.println(" }");
                out.println("</script>");

                out.println("<div class=\"swbform\">");
                out.println("<form id=\"frmrep\" name=\"frmrep\" method=\"post\" action=\"" + address + "\">");
                out.println("<fieldset>");
                out.println("<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
                out.println("<tr>");
                out.println("<td width=\"146\">" + paramsRequest.getLocaleString("site") + ":</td>");
                out.println("<td colspan=\"2\"><select id=\"wb_site\" name=\"wb_site\" size=\"1\">");
                Iterator<String> itKeys = sites.keySet().iterator();
                while(itKeys.hasNext()) {
                    String key = itKeys.next();
                    out.println("<option value=\"" + key + "\"");
                    if(key.equalsIgnoreCase(websiteId)) {
                        out.println(" selected=\"selected\"");
                    }
                    out.println(">" + (String)sites.get(key) + "</option>");
                }
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");

                out.println("<fieldset>");
                out.println("<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
                    out.println("<tr>");
                    out.println(" <td>");
                    out.println("  <button dojoType=\"dijit.form.Button\" onClick=\"doApply()\">"+paramsRequest.getLocaleString("apply")+"</button>");
                    out.println(" </td>");
                    out.println("</tr>");
                    out.println("</table>");
                out.println("</fieldset>");

                if(websiteId != null) {
                    out.println("<fieldset>");
                    out.println("<table width=98% cellpadding=10 cellspacing=0 border=0>");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<div class=\"applet\">");
                    // global
                    out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/SWBAplGraph.jar\" width=\"400\" height=\"400\">");
                    out.println("<param name=\"GraphType\" value=\"Bar\">");
                    out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("global_report")+"\">");
                    out.println("<param name=\"ncdata\" value=\"1\">");
                    out.println("<param name=\"percent\" value=\"false\">");
                    out.println("<param name=\"allpercent\" value=\"false\">");
                    out.println("<param name=\"zoom\" value=\"false\">");
                    int x = 0;
                    while(globals.hasNext()) {
                        SWBRecHit g = globals.next();
                        out.println("<param name=\"label"+x+"\" value=\""+g.getMonth()+"\">");
                        out.println("<param name=\"data"+x+"\"  value=\""+g.getHits()+"\">");
                        x++;
                    }
                    out.println("<param name=\"ndata\" value=\""+x+"\">");
                    out.println("<param name=\"color0\" value=\"243,247,129\">");
                    out.println("<param name=\"barname0\" value=\"" + paramsRequest.getLocaleString("global") + "\">");
                    out.println("</APPLET>");
                    out.println("</div>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<div class=\"applet\">");
                    // sesiones
                    out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/SWBAplGraph.jar\" width=\"400\" height=\"400\">");
                    out.println("<param name=\"GraphType\" value=\"Bar\">");
                    out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("session_report")+"\">");
                    out.println("<param name=\"ncdata\" value=\"1\">");
                    out.println("<param name=\"percent\" value=\"false\">");
                    out.println("<param name=\"allpercent\" value=\"false\">");
                    out.println("<param name=\"zoom\" value=\"false\">");
                    x = 0;
                    while(sessions.hasNext()) {
                        SWBRecHit s = sessions.next();
                        out.println("<param name=\"label"+x+"\" value=\""+s.getMonth()+"\">");
                        out.println("<param name=\"data"+x+"\"  value=\""+s.getHits()+"\">");
                        x++;
                    }
                    out.println("<param name=\"ndata\" value=\""+x+"\">");
                    out.println("<param name=\"color0\" value=\"190,129,247\">");
                    out.println("<param name=\"barname0\" value=\"" + paramsRequest.getLocaleString("session") + "\">");
                    out.println("</APPLET>");
                    out.println("</div>");
                    out.println("</td>");
                    out.println("</tr>");

                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<div class=\"applet\">");
                    // loggin user
                    out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/SWBAplGraph.jar\" width=\"400\" height=\"400\">");
                    out.println("<param name=\"GraphType\" value=\"Bar\">");
                    out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("login_report")+"\">");
                    out.println("<param name=\"ncdata\" value=\"1\">");
                    out.println("<param name=\"percent\" value=\"false\">");
                    out.println("<param name=\"allpercent\" value=\"false\">");
                    out.println("<param name=\"zoom\" value=\"false\">");
                    x = 0;
                    while(logins.hasNext()) {
                        SWBRecHit l = logins.next();
                        out.println("<param name=\"label"+x+"\" value=\""+l.getMonth()+"\">");
                        out.println("<param name=\"data"+x+"\"  value=\""+l.getHits()+"\">");
                        x++;
                    }
                    out.println("<param name=\"ndata\" value=\""+x+"\">");
                    out.println("<param name=\"color0\" value=\"190,247,129\">");
                    out.println("<param name=\"barname0\" value=\"" + paramsRequest.getLocaleString("login") + "\">");
                    out.println("</APPLET>");
                    out.println("</div>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<div class=\"applet\">");
                    // device
                    out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/SWBAplGraph.jar\" width=\"400\" height=\"400\">");
                    out.println("<param name=\"GraphType\" value=\"Bar\">");
                    out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("device_report")+"\">");
                    long nc = SWBUtils.sizeOf(SWBContext.getWebSite(websiteId).listDevices());
                    out.println("<param name=\"ncdata\" value=\""+nc+"\">");
                    out.println("<param name=\"percent\" value=\"false\">");
                    out.println("<param name=\"allpercent\" value=\"false\">");
                    out.println("<param name=\"zoom\" value=\"false\">");

                    x=0;
                    Iterator<String> m = devices.keySet().iterator();
                    while(m.hasNext()) {
                        String key = m.next();
                        HashMap d = (HashMap)devices.get(key);
                        String v = Arrays.toString(d.values().toArray());
                        v = v.substring(1, v.length()-1);
                        v = v.replaceAll(", ", "|");

                        out.println("<param name=\"label"+x+"\" value=\""+key+"\">");
                        out.println("<param name=\"data"+x+"\"  value=\""+v+"\">");
                        x++;
                    }
                    out.println("<param name=\"ndata\" value=\""+x+"\">");
                    out.println("<param name=\"barname0\" value=\"desconocido\">");
                    Iterator<Device> id = SWBContext.getWebSite(websiteId).listDevices();
                    for(int i=1; id.hasNext(); i++) {
                        Device d = id.next();
                        //out.println("<param name=\"color"+i+"\" value=\""+i+","+i+",255\">");
                        out.println("<param name=\"barname"+i+"\" value=\""+d.getDisplayTitle(language)+"\">");
                    }
                    out.println("<param name=\"color0\" value=\"247,129,129\">");
                    out.println("<param name=\"color1\" value=\"247,190,129\">");
                    out.println("<param name=\"color2\" value=\"243,247,129\">");
                    out.println("<param name=\"color3\" value=\"190,247,129\">");
                    out.println("<param name=\"color4\" value=\"129,247,129\">");
                    out.println("<param name=\"color5\" value=\"129,247,190\">");
                    out.println("<param name=\"color6\" value=\"129,247,243\">");
                    out.println("<param name=\"color7\" value=\"129,190,247\">");
                    out.println("<param name=\"color8\" value=\"129,129,247\">");
                    out.println("<param name=\"color9\" value=\"190,129,247\">");
                    out.println("<param name=\"color10\" value=\"247,129,243\">");
                    out.println("<param name=\"color11\" value=\"247,129,190\">");
                    out.println("<param name=\"color12\" value=\"153,0,255\">");
                    out.println("<param name=\"color13\" value=\"255,0,255\">");
                    out.println("<param name=\"color14\" value=\"255,102,0\">");
                    out.println("<param name=\"color15\" value=\"153,102,0\">");
                    out.println("<param name=\"color16\" value=\"0,102,0\">");
                    out.println("</APPLET>");
                    out.println("</div>");
                    out.println("</td>");
                    out.println("</tr>");

                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<div class=\"applet\">");
                    // language
                    out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/SWBAplGraph.jar\" width=\"400\" height=\"400\">");
                    out.println("<param name=\"GraphType\" value=\"Bar\">");
                    out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("language_report")+"\">");
                    nc = SWBUtils.sizeOf(SWBContext.getWebSite(websiteId).listLanguages());
                    out.println("<param name=\"ncdata\" value=\""+nc+"\">");
                    out.println("<param name=\"percent\" value=\"false\">");
                    out.println("<param name=\"allpercent\" value=\"false\">");
                    out.println("<param name=\"zoom\" value=\"false\">");

                    x=0;
                    m = languages.keySet().iterator();
                    while(m.hasNext()) {
                        String key = m.next();
                        HashMap l = (HashMap)languages.get(key);
                        String v = Arrays.toString(l.values().toArray());
                        v = v.substring(1, v.length()-1);
                        v = v.replaceAll(", ", "|");

                        out.println("<param name=\"label"+x+"\" value=\""+key+"\">");
                        out.println("<param name=\"data"+x+"\"  value=\""+v+"\">");
                        x++;
                    }
                    out.println("<param name=\"ndata\" value=\""+x+"\">");

                    Iterator<Language> il = SWBContext.getWebSite(websiteId).listLanguages();
                    for(int i=0; il.hasNext(); i++) {
                        Language l = il.next();
                        //char j = (char)((int)Math.random()*255);
                        //out.println("<param name=\"color"+i+"\" value=\""+j+","+j+",255\">");
                        out.println("<param name=\"barname"+i+"\" value=\""+l.getDisplayTitle(language)+"\">");
                    }
                    out.println("<param name=\"color0\" value=\"247,129,129\">");
                    out.println("<param name=\"color1\" value=\"247,190,129\">");
                    out.println("<param name=\"color2\" value=\"243,247,129\">");
                    out.println("<param name=\"color3\" value=\"190,247,129\">");
                    out.println("<param name=\"color4\" value=\"129,247,129\">");
                    out.println("<param name=\"color5\" value=\"129,247,190\">");
                    out.println("<param name=\"color6\" value=\"129,247,243\">");
                    out.println("<param name=\"color7\" value=\"129,190,247\">");
                    out.println("<param name=\"color8\" value=\"129,129,247\">");
                    out.println("<param name=\"color9\" value=\"190,129,247\">");
                    out.println("<param name=\"color10\" value=\"247,129,243\">");
                    out.println("<param name=\"color11\" value=\"247,129,190\">");
                    out.println("</APPLET>");
                    out.println("</div>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</fieldset>");
                }

                out.println("<form>");
                out.println("</div>");
            }else {   // There are not sites and displays a message
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<form method=\"Post\" action=\"" + paramsRequest.getTopic().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
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
        }catch(Exception e) {
            log.error("Error on method doView() resource " + strRscType +  " with id " + base.getId(), e);
        }
    }
}
