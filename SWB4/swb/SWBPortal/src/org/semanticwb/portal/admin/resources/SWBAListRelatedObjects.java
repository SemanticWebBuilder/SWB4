/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
//import java.text.DateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBAListRelatedObjects extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBAListRelatedObjects.class);
    static String MODE_IdREQUEST = "FORMID";
    String Mode_Action = "PACTION";
    Resource base = null;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView()");
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doEdit()");
        base = getResourceBase();
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");

        log.debug("suri: " + id);

        String action = request.getParameter("act");
        if (action == null) {
            action = "";
        }
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        //GenericObject gobj = ont.getGenericObject(id);
        SemanticObject obj = ont.getSemanticObject(id);

        SemanticClass cls = obj.getSemanticClass();
        String title = cls.getName();

        SemanticProperty semprop = null;
        SemanticObject semobj = null;

        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("update");

        if (action.equals("")) {
//            log.debug("CLASE: " + clsprop.getClassName());
            SemanticProperty sptemp = null;
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table width=\"98%\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println(paramRequest.getLocaleString("th_title"));
            out.println("</th>");
            out.println("<th>");
            out.println(paramRequest.getLocaleString("th_type"));
            out.println("</th>");
            sptemp = Traceable.swb_created;
            String propname = sptemp.getName();
            try {
                propname = sptemp.getDisplayName(user.getLanguage());
            } catch (Exception e) {
            }
            out.println("<th>");
            out.println(propname);
            out.println("</th>");
            sptemp = Traceable.swb_updated;
            propname = sptemp.getName();
            try {
                propname = sptemp.getDisplayName(user.getLanguage());
            } catch (Exception e) {
            }
            out.println("<th>");
            out.println(propname);
            out.println("</th>");
            sptemp = Traceable.swb_modifiedBy;
            propname = sptemp.getName();
            try {
                propname = sptemp.getDisplayName(user.getLanguage());
            } catch (Exception e) {
            }
            out.println("<th>");
            out.println(propname);
            out.println("</th>");
            sptemp = Activeable.swb_active;
            propname = sptemp.getName();
            try {
                propname = sptemp.getDisplayName(user.getLanguage());
            } catch (Exception e) {
            }
            out.println("<th>");
            out.println(propname);
            out.println("</th>");

            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            log.debug("obj sem class"+obj.getSemanticClass().getName());

            Iterator<SemanticObject> itso = obj.listRelatedObjects();

            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                out.println("<tr>");

                // Action

//                out.println("<td>");
//                SWBResourceURL urlr = paramRequest.getActionUrl();
//                urlr.setParameter("suri", id);
//                urlr.setParameter("sprop", idp);
//                urlr.setParameter("spropref", idpref);
//                urlr.setParameter("sval", sobj.getURI());
//                urlr.setParameter(prop.getName(), prop.getURI());
//                urlr.setAction("remove");
//                out.println("<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " " + semobj.getDisplayName(user.getLanguage()) + "?')){submitUrl('" + urlr + "',this);} else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=0></a>");
//                out.println("</td>");

                // Titulo, para abrir en un nuevo tab

                out.println("<td>");
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + sobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sobj.getDisplayName() + "');return false;\" >" + sobj.getDisplayName(user.getLanguage()) + "</a>");
                out.println("</td>");

                // Tipo del elemento

                out.println("<td>");
                out.println(sobj.getSemanticClass().getName());
                out.println("</td>");

                out.println("<td>");
                if (sobj.getProperty(Traceable.swb_created) != null) {
                    semprop = Traceable.swb_created;
                    out.print(getValueSemProp(sobj, semprop));
                }
                else out.println(" --- ");
                out.println("</td>");

                out.println("<td>");
                if (sobj.getProperty(Traceable.swb_updated) != null) {
                    semprop = Traceable.swb_updated;
                    out.print(getValueSemProp(sobj, semprop));
                }
                else out.println(" --- ");
                out.println("</td>");

                out.println("<td>");
                if (sobj.getObjectProperty(Traceable.swb_modifiedBy) != null)
                {
                    semprop = Traceable.swb_modifiedBy;
                    semobj = sobj.getObjectProperty(semprop);

                    if (null != semobj) {
                        log.debug("MODIFIEDBY-------" + semobj.getURI());
                        out.println("<a href=\"#\"  onclick=\"addNewTab('" + semobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + semobj.getDisplayName() + "');return false;\" >");
                        out.println(semobj.getProperty(User.swb_usrLogin));
                        out.println("</a>");
                    }
                }
                else out.println(" --- ");

                out.println("</td>");

                out.println("<td align=\"center\">");
                if (sobj.getProperty(Activeable.swb_active) != null) {
                    boolean activo = false;
                    if (sobj.getBooleanProperty(Activeable.swb_active)) {
                        activo = true;
                    }
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setAction("updstatus");
                    out.println("<input name=\"" + Activeable.swb_active.getName() + sobj.getURI() + "\" type=\"checkbox\" value=\"1\" onclick=\"return false;\"  " + (activo ? "checked='checked'" : "") + "/>");
                }
                else out.println(" --- ");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</fieldset>");
            
            out.println("</div>");

        }

    }

    public String getValueSemProp(SemanticObject obj, SemanticProperty prop) {
        String ret = "";
        try {
            if (prop.isDataTypeProperty()) {
                log.debug("getValueSemProp(" + prop.getName() + ")" + obj.getProperty(prop));
                if (prop.isBoolean()) {
                    ret = "" + obj.getBooleanProperty(prop);
                }
                if (prop.isInt() || prop.isFloat()) {
                    ret = "" + obj.getLongProperty(prop);
                }
                if (prop.isString()) {
                    ret = obj.getProperty(prop);
                }
                if (prop.isDateTime()) {
                    ret = "" + obj.getDateProperty(prop);
                }
            } else if (prop.isObjectProperty()) {
                ret = obj.getObjectProperty(prop).getURI();
            }
        } catch (Exception e) {
            ret = "Not set";
        }
        return ret;
    }


}
