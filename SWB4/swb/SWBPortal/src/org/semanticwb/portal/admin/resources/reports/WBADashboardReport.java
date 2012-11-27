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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


// TODO: Auto-generated Javadoc
/**
 * The Class WBADashboardReport.
 */
public class WBADashboardReport extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBASectionReport.class);

//    private static final String REPORT_IDAUX_GLOBAL = "_";
//    private static final String REPORT_IDAUX_SESSIONS = "_";
//    private static final String REPORT_IDAUX_LOGINS = "_";
//    private static final int REPORT_TYPE_GLOBAL = 0;
//    private static final int REPORT_TYPE_DEVICES = 1;
//    private static final int REPORT_TYPE_LANGUAGE = 2;
//    private static final int REPORT_TYPE_SESSIONS = 5;
//    private static final int REPORT_TYPE_LOGINS = 6;

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
            strRscType = "WBAContentsReport";
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
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
                String address = paramsRequest.getWebPage().getUrl();
                String websiteId = request.getParameter("wb_site")==null ? (String)sites.keySet().iterator().next():request.getParameter("wb_site");                
                WebSite website = SWBContext.getWebSite(websiteId);
                
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
                    request.setAttribute("wb_site", websiteId);
                    request.setAttribute("wb_repository", website.getUserRepository().getId());
                    out.println("<fieldset>");
                    out.println("<table border=\"0\" width=\"98%\" cellpadding=\"10\" cellspacing=\"0\">");
                    // global
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("Reporte global");
                    out.println("<div class=\"applet\">");
                    WBAGlobalReport global = new WBAGlobalReport();
                    out.println(global.getHistogram(request, response, paramsRequest));
                    out.println("</div>");
                    out.println("</td>");
                    // sesiones
                    out.println("<td>");
                    out.println("Reporte por sesiones");
                    out.println("<div class=\"applet\">");
                    WBASessionReport session = new WBASessionReport();
                    out.println(session.getHistogram(request, response, paramsRequest));
                    out.println("</div>");
                    out.println("</td>");
                    out.println("</tr>");

                    // loggin user
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("Reporte por usuarios firmados");
                    out.println("<div class=\"applet\">");
                    WBALoginReport login = new WBALoginReport();
                    out.println(login.getHistogram(request, response, paramsRequest));
                    out.println("</div>");
                    out.println("</td>");
                    // device
                    out.println("<td>");
                    out.println("Reporte de dispositivos");
                    out.println("<div class=\"applet\">");
                    WBADeviceReport device = new WBADeviceReport();
                    out.println(device.getHistogram(request, response, paramsRequest));
                    out.println("</div>");
                    out.println("</td>");
                    out.println("</tr>");

                    // language
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("Reporte por idiomas");
                    out.println("<div class=\"applet\">");
                    WBALanguageReport language = new WBALanguageReport();
                    out.println(language.getHistogram(request, response, paramsRequest));
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
                out.println("<form method=\"Post\" action=\"" + paramsRequest.getWebPage().getUrl() + "\" id=\"frmrep\" name=\"frmrep\">");
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