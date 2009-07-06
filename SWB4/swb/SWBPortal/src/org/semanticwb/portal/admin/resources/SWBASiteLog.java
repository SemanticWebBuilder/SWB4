/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.db.SWBRecAdmLog;

/**
 *
 * @author Jorge Jiménez
 */
public class SWBASiteLog extends GenericResource {

    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    private Logger log = SWBUtils.getLogger(SWBRecAdmLog.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        out.println("<table summary=\"Las ultimas actividades del usuario\">");
        out.println("<caption>");
        out.println(paramRequest.getLocaleString("RecentChanges"));
        out.println("</caption>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("action")+"</th>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("object")+"</th>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("property")+"</th>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("date")+"</th>");
        out.println("<tbody>");
        int cont = 0;
        String sql = "select * from swb_admlog where log_user='" + user.getURI() + "' order by log_date";
        Connection con = SWBUtils.DB.getDefaultConnection("SiteLog:doView");
        if (con != null) {
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    SemanticObject swbobj=SemanticObject.createSemanticObject(rs.getString("log_objuri"));
                    System.out.println("swbobj:"+swbobj);
                    if(swbobj!=null){
                        String fecha=""+rs.getTimestamp("log_date");
                        if(fecha.lastIndexOf(" ")>0) fecha=fecha.substring(0,fecha.lastIndexOf(" "));
                        out.println("<tr>");
                        out.println("  <td class=\"mov-recurso\">" + rs.getString("log_action") + "</td>");
                        System.out.println("log_objuri:"+rs.getString("log_objuri"));
                        out.println("  <td class=\"mov-recurso\">" + swbobj.getDisplayName() + "</td>");
                        out.println("  <td class=\"mov-recurso\">" + ont.getSemanticProperty(rs.getString("log_propid")).getDisplayName(user.getLanguage()) + "</td>");
                        out.println("  <td class=\"mov-fecha\">" + fecha + "</td>");
                        out.println("</tr>");
                        cont++;
                        if (cont >= 10) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        out.println("</tbody>");
        out.println("</table>");
    }
}
