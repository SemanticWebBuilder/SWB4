/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.db.SWBRecAdmLog;

/**
 *
 * @author Jorge Jiménez
 */
public class SiteLog extends GenericResource {

    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        out.println("<table summary=\"Las ultimas actividades del usuario\">");
        out.println("<caption>");
        out.println(paramRequest.getLocaleString("RecentChanges"));
        out.println("</caption>");
        out.println("<tbody>");
        int cont=0;
        Iterator<SWBRecAdmLog> iter = SWBPortal.getDBAdmLog().getBitaObjURI(paramRequest.getTopic().getWebSite().getURI(), paramRequest.getTopic().getWebSite().getURI());
        while (iter.hasNext()) {
            SWBRecAdmLog obj = iter.next();
            String userId = obj.getUser();
            if(userId.equals(user.getId()))
            {
                out.println("<tr>");
                out.println("  <td class=\"mov-recurso\"><a href=\"#\">" + obj.getAction() + "</td>");
                out.println("  <td class=\"mov-recurso\"><a href=\"#\">" + ont.getSemanticProperty(obj.getPropId()).getDisplayName(user.getLanguage()) + "</td>");
                out.println("  <td class=\"mov-recurso\"><a href=\"#\">" + SWBUtils.TEXT.iso8601DateFormat(obj.getDate()) + "</td>");
                out.println("</tr>");
                cont++;
                if(cont>=10) break;
            }
        }
        out.println("</tbody>");
        out.println("</table>");
    }
}
