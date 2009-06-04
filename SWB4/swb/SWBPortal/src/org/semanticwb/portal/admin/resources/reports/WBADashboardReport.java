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
import org.semanticwb.portal.admin.resources.reports.beans.WBAFilterReportBean;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRDeviceAccessDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRLoggedUserDataDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.data.JRSessionDataDetail;
import org.semanticwb.portal.api.SWBResourceURL;

public class WBADashboardReport extends GenericResource {
    private static Logger log = SWBUtils.getLogger(WBASectionReport.class);

    public static final String REPORT_IDAUX_GLOBAL = "_";
    public static final String REPORT_IDAUX_SESSIONS = "_";
    public static final String REPORT_IDAUX_LOGINS = "_";
    public static final int REPORT_TYPE_GLOBAL = 0;
    public static final int REPORT_TYPE_DEVICES = 1;
    public static final int REPORT_TYPE_SESSIONS = 5;
    public static final int REPORT_TYPE_LOGINS = 6;

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
                String websiteId = request.getParameter("wb_site")==null ? paramsRequest.getTopic().getWebSite().getId():request.getParameter("wb_site");
                //String rtype = request.getParameter("wb_rtype")==null?"1":request.getParameter("wb_rtype");
                String detail = request.getParameter("wb_detail")==null?"":request.getParameter("wb_detail");
                String language = paramsRequest.getUser().getLanguage();

                GregorianCalendar now = new GregorianCalendar();
                JRDataSourceable dataDetail;
                JRBeanCollectionDataSource ds;
                Iterator<SWBRecHit> globals;
                Iterator<SWBRecHit> sessions;
                Iterator<SWBRecHit> logins;
                HashMap devices;
                SWBResourceURL url = paramsRequest.getRenderUrl();

                if(detail.equalsIgnoreCase("global")) {
                    System.out.println("global detalle");
                    String barname = request.getParameter("wbr_barname");
                    int month = SWBUtils.monthToInt(barname, language);
                    WBAFilterReportBean filter = new WBAFilterReportBean();
                    filter.setSite(websiteId);
                    filter.setIdaux(REPORT_IDAUX_GLOBAL);
                    filter.setType(REPORT_TYPE_GLOBAL);
                    filter.setYearI(now.get(Calendar.YEAR));
                    filter.setMonthI(month);
                    filter.setDayI(now.getActualMinimum(Calendar.DAY_OF_MONTH));
                    filter.setYearF(now.get(Calendar.YEAR));
                    filter.setMonthF(month);
                    filter.setDayF(now.getActualMaximum(Calendar.DAY_OF_MONTH));
                    dataDetail = new JRGlobalAccessDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        globals = ds.getData().iterator();
                    }else {
                        globals = Collections.EMPTY_LIST.iterator();
                    }
                }else {
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
                }

                if(detail.equalsIgnoreCase("session")) {
                    String barname = request.getParameter("wbr_barname");
                    int month = SWBUtils.monthToInt(barname, language);
                    WBAFilterReportBean filter = new WBAFilterReportBean();
                    filter.setSite(SWBContext.getWebSite(websiteId).getUserRepository().getId());
                    filter.setIdaux(REPORT_IDAUX_SESSIONS);
                    filter.setType(REPORT_TYPE_SESSIONS);
                    filter.setYearI(now.get(Calendar.YEAR));
                    filter.setMonthI(month);
                    filter.setDayI(now.getActualMinimum(Calendar.DAY_OF_MONTH));
                    filter.setYearF(now.get(Calendar.YEAR));
                    filter.setMonthF(month);
                    filter.setDayF(now.getActualMaximum(Calendar.DAY_OF_MONTH));
                    dataDetail = new JRSessionDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        sessions = ds.getData().iterator();
                    }else {
                        sessions = Collections.EMPTY_LIST.iterator();
                    }
                }else {
                    WBAFilterReportBean filter = new WBAFilterReportBean();
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
                }

                if(detail.equalsIgnoreCase("login")) {
                    String barname = request.getParameter("wbr_barname");
                    int month = SWBUtils.monthToInt(barname, language);
                    WBAFilterReportBean filter = new WBAFilterReportBean();
                    filter.setSite(websiteId);
                    filter.setIdaux(REPORT_IDAUX_GLOBAL);
                    filter.setType(REPORT_TYPE_GLOBAL);
                    filter.setYearI(now.get(Calendar.YEAR));
                    filter.setMonthI(month);
                    filter.setDayI(now.getActualMinimum(Calendar.DAY_OF_MONTH));
                    filter.setYearF(now.get(Calendar.YEAR));
                    filter.setMonthF(month);
                    filter.setDayF(now.getActualMaximum(Calendar.DAY_OF_MONTH));
                    dataDetail = new JRGlobalAccessDataDetail(filter);
                    ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                    if(ds!=null){
                        logins = ds.getData().iterator();
                    }else {
                        logins = Collections.EMPTY_LIST.iterator();
                    }
                }else {
                    WBAFilterReportBean filter = new WBAFilterReportBean();
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
                }




                if(detail.equalsIgnoreCase("device")) {
                    String barname = request.getParameter("wbr_barname");
                    int month = SWBUtils.monthToInt(barname, language);
                    
                    long nd = SWBUtils.sizeOf(SWBContext.getWebSite(websiteId).listDevices());
                    Iterator<SWBRecHit>[] adevices = new Iterator[(int)nd];
                    Iterator<Device> devs = SWBContext.getWebSite(websiteId).listDevices();
                    for(int i=0; devs.hasNext(); i++) {
                        Device dev = devs.next();
                        WBAFilterReportBean filter = new WBAFilterReportBean();
                        filter.setSite(websiteId);
                        filter.setIdaux(dev.getId());
                        filter.setType(REPORT_TYPE_DEVICES);
                        filter.setYearI(now.get(Calendar.YEAR));
                        filter.setMonthI(month);
                        filter.setDayI(now.getActualMinimum(Calendar.DAY_OF_MONTH));
                        filter.setYearF(now.get(Calendar.YEAR));
                        filter.setMonthF(month);
                        filter.setDayF(now.getActualMaximum(Calendar.DAY_OF_MONTH));
                        dataDetail = new JRDeviceAccessDataDetail(filter);
                        ds = (JRBeanCollectionDataSource)dataDetail.orderJRReport();
                        if(ds!=null){
                            adevices[i] = ds.getData().iterator();
                        }else {
                            adevices[i] = Collections.EMPTY_LIST.iterator();
                        }
                    }

                    devices = new HashMap();
                    for(int i=0; i<12; i++) {
                        for(int j=0; j<adevices.length; j++) {
                            Iterator<SWBRecHit> it = adevices[j];
                            while(it.hasNext()) {
                                SWBRecHit r = it.next();
                                if(devices.containsKey(r.getMonth())) {
                                    String v = "|" + (String)devices.get(r.getMonth());
                                    devices.put(r.getMonth(), v);
                                }else {
                                    devices.put(r.getMonth(), r.getHits());
                                }
                            }
                        }
                    }
                }else {
                    Iterator<SWBRecHit> idevices;
                    WBAFilterReportBean filter = new WBAFilterReportBean();
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
                        }else {
                            HashMap c = new HashMap();
                            devices.put(r.getMonth(), c);
                            c.put(r.getItem(), Long.toString(r.getHits()));
                        }
                    }
                    Iterator<String> m = devices.keySet().iterator();
                    while(m.hasNext()) {
                        String key = m.next();
                        HashMap c = (HashMap)devices.get(key);
                        Iterator<Device> devs = SWBContext.getWebSite(websiteId).listDevices();
                        while(devs.hasNext()) {
                            Device d = devs.next();
                            if(!c.containsKey(d.getDisplayTitle(language))) {
                                //device.getDisplayTitle(userLanguage)
                                c.put(d.getDisplayTitle(language), Long.toString(0L));
                            }
                        }
                    }
                }
                
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

                out.println("<fieldset>");
                out.println("<table width=98% cellpadding=10 cellspacing=0 border=0>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<div class=\"applet\">");
                // global
                out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/WBGraph.jar\" width=\"400\" height=\"400\">");
                out.println("<param name=\"GraphType\" value=\"Bar\">");
                out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("global_report")+"\">");
                if(!detail.equalsIgnoreCase("global")) {
                    out.println("<param name=\"link\" value=\""+url+"?wb_detail=global&wb_site="+websiteId+"&wbr_barname="+"\">");
                }
                out.println("<param name=\"ncdata\" value=\"1\">");
                out.println("<param name=\"percent\" value=\"false\">");
                out.println("<param name=\"allpercent\" value=\"false\">");
                out.println("<param name=\"zoom\" value=\"true\">");                                
                int x = 0;
                while(globals.hasNext()) {
                    SWBRecHit g = globals.next();
                    out.println("<param name=\"label"+x+"\" value=\""+g.getMonth()+"\">");
                    out.println("<param name=\"data"+x+"\"  value=\""+g.getHits()+"\">");
                    x++;
                }
                out.println("<param name=\"ndata\" value=\""+x+"\">");
                out.println("<param name=\"color0\" value=\"100,100,255\">");
                out.println("<param name=\"barname0\" value=\"" + paramsRequest.getLocaleString("global") + "\">");
                out.println("</APPLET>");
                out.println("</div>");
                out.println("</td>");

                out.println("<td>");
                out.println("<div class=\"applet\">");
                // sesiones
                out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/WBGraph.jar\" width=\"400\" height=\"400\">");
                out.println("<param name=\"GraphType\" value=\"Bar\">");
                out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("session_report")+"\">");
                if(!detail.equalsIgnoreCase("session")) {
                    out.println("<param name=\"link\" value=\""+url+"?wb_detail=session&wb_site="+websiteId+"&wbr_barname="+"\">");
                }
                out.println("<param name=\"ncdata\" value=\"1\">");
                out.println("<param name=\"percent\" value=\"false\">");
                out.println("<param name=\"allpercent\" value=\"false\">");
                out.println("<param name=\"zoom\" value=\"true\">");
                x = 0;
                while(sessions.hasNext()) {
                    SWBRecHit s = sessions.next();
                    out.println("<param name=\"label"+x+"\" value=\""+s.getMonth()+"\">");
                    out.println("<param name=\"data"+x+"\"  value=\""+s.getHits()+"\">");
                    x++;
                }
                out.println("<param name=\"ndata\" value=\""+x+"\">");
                out.println("<param name=\"color0\" value=\"150,150,255\">");
                out.println("<param name=\"barname0\" value=\"" + paramsRequest.getLocaleString("session") + "\">");
                out.println("</APPLET>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td>");
                out.println("<div class=\"applet\">");
                // loggin user
                out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/WBGraph.jar\" width=\"400\" height=\"400\">");
                out.println("<param name=\"GraphType\" value=\"Bar\">");
                out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("login_report")+"\">");
                if(!detail.equalsIgnoreCase("login")) {
                    out.println("<param name=\"link\" value=\""+url+"?wb_detail=login&wb_site="+websiteId+"&wbr_barname="+"\">");
                }
                out.println("<param name=\"ncdata\" value=\"1\">");
                out.println("<param name=\"percent\" value=\"false\">");
                out.println("<param name=\"allpercent\" value=\"false\">");
                out.println("<param name=\"zoom\" value=\"true\">");
                x = 0;
                while(logins.hasNext()) {
                    SWBRecHit l = logins.next();
                    out.println("<param name=\"label"+x+"\" value=\""+l.getMonth()+"\">");
                    out.println("<param name=\"data"+x+"\"  value=\""+l.getHits()+"\">");
                    x++;
                }
                out.println("<param name=\"ndata\" value=\""+x+"\">");
                out.println("<param name=\"color0\" value=\"200,100,255\">");
                out.println("<param name=\"barname0\" value=\"" + paramsRequest.getLocaleString("login") + "\">");
                out.println("</APPLET>");
                out.println("</div>");
                out.println("</td>");

                out.println("<td>");
                out.println("<div class=\"applet\">");
                // device
                System.out.println("ya vamos por aca..........");
                out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""+SWBPlatform.getContextPath()+"/swbadmin/lib/WBGraph.jar\" width=\"400\" height=\"400\">");
                out.println("<param name=\"GraphType\" value=\"Bar\">");
                out.println("<param name=\"Title\" value=\""+paramsRequest.getLocaleString("device_report")+"\">");
                if(!detail.equalsIgnoreCase("device")) {
                    out.println("<param name=\"link\" value=\""+url+"?wb_detail=device&wb_site="+websiteId+"&wbr_barname="+"\">");
                }
                long nc = SWBUtils.sizeOf(SWBContext.getWebSite(websiteId).listDevices());
                out.println("<param name=\"ncdata\" value=\""+nc+"\">");
                out.println("<param name=\"percent\" value=\"false\">");
                out.println("<param name=\"allpercent\" value=\"false\">");
                out.println("<param name=\"zoom\" value=\"true\">");

                x=0;
                Iterator<String> m = devices.keySet().iterator();
                while(m.hasNext()) {
                    String key = m.next();
                    HashMap d = (HashMap)devices.get(key);
                    String v = Arrays.toString(d.values().toArray());
                    System.out.println("--- v="+v);
                    v = v.substring(1, v.length()-1);
                    v = v.replaceAll(", ", "|");
                    System.out.println("v="+v);

                    out.println("<param name=\"label"+x+"\" value=\""+key+"\">");
                    out.println("<param name=\"data"+x+"\"  value=\""+v+"\">");
                    x++;
                }
                System.out.println("x="+x);
                System.out.println("devices.size()="+devices.size());
                out.println("<param name=\"ndata\" value=\""+x+"\">");

                Iterator<Device> id = SWBContext.getWebSite(websiteId).listDevices();
                for(int i=0; id.hasNext(); i++) {
                    Device d = id.next();
                    //out.println("<param name=\"color"+i+"\" value=\""+i+",100,255\">");
                    out.println("<param name=\"color"+i+"\" value=\""+i+","+i+",255\">");
                    out.println("<param name=\"barname"+i+"\" value=\""+d.getDisplayTitle(language)+"\">");
                }
                out.println("</APPLET>");
                out.println("</div>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");

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

    private int monthToInt(String month) {

        return 1;
    }

    /*private String[] monthNames(SWBParamRequest paramsRequest){
        
        try{
            months[Calendar.JANUARY] = paramsRequest.getLocaleString("month_january");
            months[Calendar.FEBRUARY] = paramsRequest.getLocaleString("month_february");
            months[Calendar.MARCH] = paramsRequest.getLocaleString("month_march");
            months[Calendar.APRIL] = paramsRequest.getLocaleString("month_april");
            months[Calendar.MAY] = paramsRequest.getLocaleString("month_may");
            months[Calendar.JUNE] = paramsRequest.getLocaleString("month_june");
            months[Calendar.JULY] = paramsRequest.getLocaleString("month_july");
            months[Calendar.AUGUST] = paramsRequest.getLocaleString("month_august");
            months[Calendar.SEPTEMBER] = paramsRequest.getLocaleString("month_september");
            months[Calendar.OCTOBER] = paramsRequest.getLocaleString("month_october");
            months[Calendar.NOVEMBER] = paramsRequest.getLocaleString("month_november");
            months[Calendar.DECEMBER] = paramsRequest.getLocaleString("month_december");
        }catch (Exception e){
            log.error("Error on method monthNames() resource " + strRscType + " with id ", e);
        }
        return months;
    }*/
}
