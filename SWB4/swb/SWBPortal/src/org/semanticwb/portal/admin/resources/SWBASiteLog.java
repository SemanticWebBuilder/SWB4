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
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.db.SWBRecAdmLog;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBASiteLog.
 * 
 * @author Jorge Jiménez
 */
public class SWBASiteLog extends GenericResource {

    /** The ont. */
    SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
    
    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBRecAdmLog.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        out.println("<table summary=\"Las ultimas actividades del usuario\">");
        out.println("<caption>");
        out.println(paramRequest.getLocaleString("RecentChanges"));
        out.println("</caption>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("action")+"</th>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("objType")+"</th>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("object")+"</th>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("property")+"</th>");
        out.println("<th align=\"left\">"+paramRequest.getLocaleString("date")+"</th>");
        out.println("<tbody>");
        int cont = 0;
        String sql = "select * from swb_admlog where log_user='" + user.getURI() + "' order by log_date desc";
        Connection con = SWBUtils.DB.getDefaultConnection("SiteLog:doView");
        if (con != null) {
            try {
                Statement st = con.createStatement();
                st.setFetchSize(10);
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    SemanticObject swbobj=SemanticObject.createSemanticObject(rs.getString("log_objuri"));
                    if(swbobj!=null){
                        String fecha=""+rs.getTimestamp("log_date");
                        String strObj="";
                        if(swbobj.getDisplayName()!=null && swbobj.getDisplayName()!="null") strObj=swbobj.getDisplayName();
                        if(fecha.lastIndexOf(" ")>0) fecha=fecha.substring(0,fecha.lastIndexOf(" "));
                        out.println("<tr>");
                        out.println("  <td class=\"mov-recurso\">" + rs.getString("log_action") + "</td>");
                        SemanticClass cls=swbobj.getSemanticClass();
                        if(cls!=null)
                        {
                            out.println("  <td class=\"mov-recurso\">" + cls.getName()+"</td>");
                        }else
                        {
                            out.println("  <td class=\"mov-recurso\">[NO NAME]</td>");
                        }
                        out.println("  <td class=\"mov-recurso\">" + strObj + "</td>");
                        out.println("  <td class=\"mov-recurso\">" + ont.getSemanticProperty(rs.getString("log_propid")).getDisplayName(user.getLanguage()) + "</td>");
                        out.println("  <td class=\"mov-fecha\">" + fecha + "</td>");
                        out.println("</tr>");
                        cont++;
                        if (cont >= 10) {
                            break;
                        }
                    }
                }
                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
        out.println("</tbody>");
        out.println("</table>");
    }
}
