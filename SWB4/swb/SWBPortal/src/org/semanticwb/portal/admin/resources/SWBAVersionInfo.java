/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.Versionable;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class SWBAVersionInfo extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBAVersionInfo.class);
    String webpath = SWBPlatform.getContextPath();
    Portlet base;
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    SWBVocabulary voc = SWBContext.getVocabulary();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        base = getResourceBase();
        User user = paramRequest.getUser();

        String id = request.getParameter("suri");
        String idvi = request.getParameter("sobj");
        String action = request.getParameter("act");
        GenericObject obj = ont.getGenericObject(id);
        PrintWriter out = response.getWriter();
        log.debug("doEdit(SWBAVersionInfo...)");
        int numver = 0;
        VersionInfo via = null;
        VersionInfo vio = null;
        if (obj instanceof Versionable) {
            vio = (VersionInfo) findFirstVersion(obj);
            via = ((Versionable) obj).getActualVersion();
        } else {
            return;
        }

        if (vio != null && (action == null || action.equals(""))) {

            out.println("<fieldset>");
            out.println("<table width=\"100%\" class=\"swbform\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println(paramRequest.getLocaleString("msgVersion"));
            out.println("</th>");
            out.println("<th>");
            out.println(paramRequest.getLocaleString("msgAction"));
            out.println("</th>");
            out.println("<th>");
            out.println("&nbsp;");
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            while (vio.getNextVersion() != null) {

                out.println("<tr>");

                out.println("<td align=\"center\">");
                out.println(vio.getVersionNumber());
                out.println("</td>");

                out.println("<td>");
                SWBResourceURL urle = paramRequest.getRenderUrl();
                urle.setParameter("suri", id);
                urle.setParameter("sval", vio.getURI());
                urle.setMode(SWBResourceURL.Mode_EDIT);
                out.println("<a href=\"#\" onclick=\"submitUrl('" + urle + "',this); return false;\">edit</a>");

                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", id);
                urlr.setParameter("sval", vio.getURI());
                urlr.setAction("remove");
                out.println("&nbsp;<a href=\"#\" onclick=\"submitUrl('" + urlr + "',this); return false;\">remove</a>");
                if (!vio.equals(via)) {
                    SWBResourceURL urlsa = paramRequest.getActionUrl();
                    urlsa.setParameter("suri", id);
                    urlsa.setParameter("sval", vio.getURI());
                    urlsa.setAction("setactual");
                    out.println("&nbsp;<a href=\"#\" onclick=\"submitUrl('" + urlsa + "',this); return false;\">Cambiar</a>");
                } else {
                    out.println("-----");
                }
                out.println("</td>");
                out.println("<td>");
                if (vio.equals(via)) {
                    out.println("Versión Actual");
                }

                out.println("&nbsp;</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"4\">");
            SWBResourceURL urlNew = paramRequest.getRenderUrl();
            urlNew.setParameter("suri", id);
            urlNew.setParameter("act", "newversion");
            urlNew.setMode(SWBResourceURL.Mode_EDIT);
            out.println("<p><a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">Add New</a>");
            out.println("</p>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</fieldset>");
        }
    }

    private VersionInfo findFirstVersion(GenericObject obj) {
        VersionInfo ver = ((Versionable) obj).getActualVersion();
        while (ver.getPreviousVersion() != null) {
            ver = ver.getPreviousVersion();
        }
        return ver;
    }

    // Edición de la VersionInfo dependiendo el SemanticObject relacionado
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String action = request.getParameter("act");
        String id = request.getParameter("suri");
        SemanticObject so = ont.getSemanticObject(id);
        PrintWriter out = response.getWriter();
        SWBFormMgr fm = null;

        if(action.equals("newversion"))
        {
            fm = new SWBFormMgr(voc.swb_VersionInfo, so, null);
            fm.addHiddenParameter("suri", id);
            fm.addHiddenParameter("act", "new");
            

            out.println(fm.renderForm());
        }
        else if(action.equals("edit"))
        {
            fm = new SWBFormMgr(voc.swb_VersionInfo, so, SWBFormMgr.MODE_EDIT);
            fm.addHiddenParameter("suri", id);
            fm.addHiddenParameter("act", "update");
            out.println(fm.renderForm());
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }
}
