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
//import java.text.DateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBAListRelatedObjects.
 * 
 * @author juan.fernandez
 */
public class SWBAListRelatedObjects extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBAListRelatedObjects.class);
    
    /** The MOD e_ id request. */
    static String MODE_IdREQUEST = "FORMID";
    
    /** The Mode_ action. */
    String Mode_Action = "PACTION";
    
    /** The base. */
    Resource base = null;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView()");
        doEdit(request, response, paramRequest);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
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
            out.println("Id");
            out.println("</th>");
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
                if(sobj.getBooleanProperty(Trashable.swb_deleted))continue;
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

                out.println("<td>");
                out.println(sobj.getId());
                out.println("</td>");

                // Titulo, para abrir en un nuevo tab


                String stitle = sobj.getDisplayName(user.getLanguage());
                if(stitle==null||(stitle!=null&&stitle.trim().length()==0)) stitle="[No Title]";

                out.println("<td>");
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + sobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sobj.getDisplayName() + "');return false;\" >" + stitle + "</a>");
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

    /**
     * Gets the value sem prop.
     * 
     * @param obj the obj
     * @param prop the prop
     * @return the value sem prop
     * @return
     */
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
