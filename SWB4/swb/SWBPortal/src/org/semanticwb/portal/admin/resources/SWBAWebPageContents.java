/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBAWebPageContents extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBAWebPageContents.class);
    String webpath = SWBPlatform.getContextPath();
    String distributor = SWBPlatform.getEnv("wb/distributor");
    String Mode_Action = "paction";

    /** Creates a new instance of TopicContents */
    public SWBAWebPageContents() {
    }
    static String MODE_IdREQUEST = "FORMID";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView(SWBAWebPageContents...)");
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doEdit()");
        PrintWriter out = response.getWriter();
        Portlet base = getResourceBase();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String idp = request.getParameter("sprop");
        String idptype = request.getParameter("sproptype");
        //System.out.println("suri:"+id+" sprop:"+idp+" sproptype:"+idptype);

        String action = request.getParameter("act");

        if (id == null) {
            id = paramRequest.getTopic().getWebSite().getURI();
        }
        if (action == null) {
            action = "";
        }
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass cls = obj.getSemanticClass();

        out.println("<script type=\"text/javascript\">");
        if (request.getParameter("nsuri") != null && request.getParameter("nsuri").trim().length() > 0) {
            SemanticObject snobj = ont.getSemanticObject(request.getParameter("nsuri"));
            if (snobj != null)
            {
                log.debug("addNewTab");
                out.println("  addNewTab('" + snobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + snobj.getDisplayName() + "');");
            }
        }

        if (request.getParameter("statmsg") != null && request.getParameter("statmsg").trim().length() > 0) {
            log.debug("showStatus");
            out.println("   showStatus('" + request.getParameter("statmsg") + "');");
        }

        if (request.getParameter("closetab") != null && request.getParameter("closetab").trim().length() > 0) {
            log.debug("closeTab..."+request.getParameter("closetab"));
            out.println("   closeTab('" + request.getParameter("closetab") + "');");
        }
        out.println("</script>");

        if (action.equals("")) { //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            log.debug("class: " + clsprop.getClassName());
            HashMap<SemanticProperty, SemanticProperty> hmprop = new HashMap();
            Iterator<SemanticProperty> ite_sp = clsprop.listProperties();
            while (ite_sp.hasNext()) {
                SemanticProperty sp = ite_sp.next();
                log.debug("propiedad:" + sp.getDisplayName() + "---" + sp.getName());
                hmprop.put(sp, sp);
            }
            SemanticProperty sptemp = null;

            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table width=\"98%\" >");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Action");
            out.println("</th>");
            out.println("<th>");
            out.println("Name");
            out.println("</th>");
            String propname = "";
            sptemp = hmprop.get(Traceable.swb_created);
            if (sptemp != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                    propname = "";
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            sptemp = hmprop.get(Traceable.swb_updated);
            if (sptemp != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                    propname = "";
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            sptemp = hmprop.get(Priorityable.swb_priority);
            if (hmprop.get(Priorityable.swb_priority) != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                    propname = "";
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            sptemp = hmprop.get(Activeable.swb_active);
            if (sptemp != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                    propname = "";
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            SemanticProperty semprop = null;
            SemanticProperty sem_p = ont.getSemanticProperty(idp);
            SemanticObject so = obj.getObjectProperty(sem_p);
            Iterator<SemanticObject> itso = obj.listObjectProperties(prop);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                SemanticClass clsobj = sobj.getSemanticClass();
                log.debug("Clase:" + clsobj.getName());

                String stitle = getDisplaySemObj(sobj, user.getLanguage());
                out.println("<tr>");

                out.println("<td>");
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", id);
                urlr.setParameter("sprop", idp);
                urlr.setParameter("sval", sobj.getURI());
                urlr.setParameter(prop.getName(), prop.getURI());
                urlr.setAction("remove");
                out.println("<a href=\"#\" onclick=\"if(confirm('¿ Est&aacute;s seguro de querer eliminar el contenido ... ?')){ submitUrl('" + urlr + "',this); } else { return false;}\">remove</a>");
//                SWBResourceURL urla = paramRequest.getRenderUrl();
//                urla.setParameter("suri", id);
//                urla.setParameter("id", id);
//                urla.setParameter("sprop", idp);
//                urla.setParameter("sval", sobj.getURI());
//                urla.setParameter(prop.getName(), prop.getURI());
//                urla.setMode(SWBResourceURL.Mode_EDIT);
//                out.println("&nbsp;<a href=\"#\" onclick=\"submitUrl('" + urla + "',this); return false;\">Adm</a>");
                out.println("</td>");
                out.println("<td>");
                SWBResourceURL urlchoose = paramRequest.getRenderUrl();
                urlchoose.setParameter("suri", id);
                urlchoose.setParameter("sprop", idp);
                urlchoose.setParameter("sobj", sobj.getURI());
                urlchoose.setParameter("act", "edit");
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + sobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sobj.getDisplayName() + "');return false;\">" + stitle + "</a>");
                out.println("</td>");
                if (hmprop.get(Traceable.swb_created) != null) {
                    semprop = (SemanticProperty) hmprop.get(Traceable.swb_created);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(Traceable.swb_updated) != null) {
                    semprop = (SemanticProperty) hmprop.get(Traceable.swb_updated);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(Priorityable.swb_priority) != null) {
                    semprop = (SemanticProperty) hmprop.get(Priorityable.swb_priority);
                    out.println("<td align=\"center\">");
                    SWBResourceURL urlu = paramRequest.getRenderUrl();
                    urlu.setMode(Mode_Action);
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("act", "update");

                    out.println("<div dojoType=\"dijit.form.NumberSpinner\" id=\"" + id + "/" + base.getId() + "/" + sobj.getId() + "/NS\" jsId=\"" + id + "/" + base.getId() + "/" + sobj.getId() + "/NS\" intermediateChanges=\"true\" smallDelta=\"1\" constraints=\"{min:0,max:999,places:0}\" style=\"width:50px\"  name=\"" + semprop.getName() + "\" maxlength=\"3\"  value=\"" + getValueSemProp(sobj, semprop) + "\" >");
                    out.println("<script type=\"dojo/connect\" event=\"onBlur\">");
                    out.println(" var self=this;   ");
                    out.println(" showStatusURL('" + urlu + "&'+self.attr(\"name\")+'='+self.attr(\"value\"),true);");
                    out.println("</script>");
                    out.println("</div>");
                    out.println("</td>");
                }
                if (hmprop.get(Activeable.swb_active) != null) {
                    out.println("<td align=\"center\">");
                    boolean activo = false;
                    if (sobj.getBooleanProperty(Activeable.swb_active)) {
                        activo = true;
                    }
                    SWBResourceURL urlu = paramRequest.getRenderUrl();
                    urlu.setMode(Mode_Action);
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("act", "updstatus");
                    out.println("<input name=\"" + prop.getName() + sobj.getURI() + "\" type=\"checkbox\" value=\"1\" id=\"" + prop.getName() + sobj.getURI() + "\" onclick=\"showStatusURL('" + urlu + "&val='+this.checked,true);\"  " + (activo ? "checked='checked'" : "") + "/>");
                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"4\">");
            SWBResourceURL urlNew = paramRequest.getRenderUrl();
            urlNew.setParameter("suri", id);
            urlNew.setParameter("sprop", idp);
            urlNew.setParameter("sproptype", idptype);
            urlNew.setParameter("act", "choose");
            out.println("<p><a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">Add New</a>");
            out.println("</p>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");

            out.println("</fieldset>");
            out.println("</div>");

        } else if (action.equals("choose")) { //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            idptype = Portlet.swb_portletType.getURI();
            if (idptype != null) {
                prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idptype);
            }
            SemanticClass clsprop = prop.getRangeClass();
            HashMap hmSO = new HashMap();
            Iterator<SemanticObject> ite_so = obj.listObjectProperties(prop);
            while (ite_so.hasNext()) {
                SemanticObject so = ite_so.next();
                if (null != so) {
                    hmSO.put(so.getURI(), so);
                }
            }

            out.println("<div class=\"swbform\">");
            SWBResourceURL urlAdd = paramRequest.getRenderUrl();
            urlAdd.setMode(SWBResourceURL.Mode_EDIT);
            urlAdd.setParameter("act", "edit");
            out.println("<form id=\"" + id + "/WPContent\" action=\"" + urlAdd + "\" method=\"post\" onsubmit=\"submitForm('" + id + "/WPContent'); return false;\">");
            out.println("<input type=\"hidden\" id=\"" + id + "/suri\" name=\"suri\" value=\"" + id + "\">");
            out.println("<input type=\"hidden\" id=\"" + id + "/sprop\" name=\"sprop\" value=\"" + idp + "\">");
            out.println("<input type=\"hidden\" id=\"" + id + "/sproptype\" name=\"sproptype\" value=\"" + idptype + "\">");

            // Lista de Tipo de portlet disponibles de Global, separados por contenido y sistema

            Iterator<SemanticObject> itgso = SWBContext.getGlobalWebSite().getSemanticObject().getModel().listInstancesOfClass(clsprop);
            HashMap<String, SemanticObject> hmContent = new HashMap();
            HashMap<String, SemanticObject> hmSystem = new HashMap();
            while (itgso.hasNext()) {
                SemanticObject sobj = itgso.next();
                int mode = sobj.getIntProperty(PortletType.swb_portletMode);
                if (mode == 1)//tipo contenido
                {
                    hmContent.put(sobj.getId(), sobj);
                } else if (mode == 3)//tipo sistema
                {
                    hmSystem.put(sobj.getId(), sobj);
                }
            }
            if (hmContent.size() > 0 || hmSystem.size()>0) {
                out.println("<fieldset>");
                out.println("	<legend>Global</legend>");
            }

            itgso = hmContent.values().iterator();
            if (hmContent.size() > 0) {
                out.println("<table width=\"98%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th colspan=\"3\">");
                out.println("Tipo Contenido");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th width=\"40\" align=\"center\">");
                out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                out.println("</th>");
                out.println("<th width=\"30%\">");
                out.println("Nombre");
                out.println("</th>");
                out.println("<th  width=\"60%\">");
                out.println("Descripci&oacute;n");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody> ");

                while (itgso.hasNext()) {
                    SemanticObject sobj = itgso.next();
                    String stitle = getDisplaySemObj(sobj, user.getLanguage());
                    String sdescription = sobj.getProperty(Descriptiveable.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\"" + sobj.getURI() + "/sptype\" value=\"global|" + sobj.getURI() + "\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription != null ? sdescription : "");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }
            itgso = hmSystem.values().iterator();
            if (hmSystem.size() > 0) {
                out.println("<table width=\"98%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th colspan=\"3\">");
                out.println("Tipo Sistema");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th width=\"40\" align=\"center\">");
                out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                out.println("</th>");
                out.println("<th width=\"30%\">");
                out.println("Nombre");
                out.println("</th>");
                out.println("<th  width=\"60%\">");
                out.println("Descripci&oacute;n");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody> ");

                while (itgso.hasNext()) {
                    SemanticObject sobj = itgso.next();
                    String stitle = getDisplaySemObj(sobj, user.getLanguage());
                    String sdescription = sobj.getProperty(Descriptiveable.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\"" + sobj.getURI() + "/sptype\" value=\"global|" + sobj.getURI() + "\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription != null ? sdescription : "");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }
            if (hmContent.size() > 0 || hmSystem.size()>0)
            {
                out.println("</fieldset>");
            }

            // Lista de tipo de portlets disponibles del sitio, separados por contenido y sistema 

            out.println("<fieldset>");
            out.println("	<legend>" + obj.getModel().getModelObject().getDisplayName() + "</legend>");

            hmContent = new HashMap();
            hmSystem = new HashMap();

            Iterator<SemanticObject> itso = obj.getModel().listInstancesOfClass(clsprop);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                log.debug("before:");
                int mode = 0;
                try {
                    mode = sobj.getIntProperty(PortletType.swb_portletMode);
                } catch (Exception e) {
                    mode = 0;
                }
                log.debug("after: mode:" + mode);
                if (mode == 1)//tipo contenido
                {
                    hmContent.put(sobj.getId(), sobj);
                } else if (mode == 3)//tipo sistema
                {
                    hmSystem.put(sobj.getId(), sobj);
                }
            }

            itso = hmContent.values().iterator();
            if (hmContent.size() > 0) {
                out.println("<table width=\"98%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th colspan=\"3\">");
                out.println("Tipo Contenido");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th width=\"40\" align=\"center\">");
                out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                out.println("</th>");
                out.println("<th width=\"30%\">");
                out.println("Nombre");
                out.println("</th>");
                out.println("<th  width=\"60%\">");
                out.println("Descripci&oacute;n");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody> ");

                while (itso.hasNext()) {
                    SemanticObject sobj = itso.next();
                    String stitle = getDisplaySemObj(sobj, user.getLanguage());
                    String sdescription = sobj.getProperty(Descriptiveable.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\"" + sobj.getURI() + "/sptype\" value=\"" + sobj.getURI() + "\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription != null ? sdescription : "");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }
            itso = hmSystem.values().iterator();
            if (hmSystem.size() > 0) {
                out.println("<table width=\"98%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th colspan=\"3\">");
                out.println("Tipo Sistema");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th width=\"40\" align=\"center\">");
                out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                out.println("</th>");
                out.println("<th width=\"30%\">");
                out.println("Nombre");
                out.println("</th>");
                out.println("<th  width=\"60%\">");
                out.println("Descripci&oacute;n");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody> ");

                while (itso.hasNext()) {
                    SemanticObject sobj = itso.next();
                    String stitle = getDisplaySemObj(sobj, user.getLanguage());
                    String sdescription = sobj.getProperty(Descriptiveable.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\"" + sobj.getURI() + "/sptype\" value=\"" + sobj.getURI() + "\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription != null ? sdescription : "");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }
            out.println("</fieldset>");
            out.println("<fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            if (id != null) {
                urlBack.setParameter("suri", id);
            }
            if (idp != null) {
                urlBack.setParameter("sprop", idp);
            }
            if (idptype != null) {
                urlBack.setParameter("sproptype", idptype);
            }
            urlBack.setParameter("act", "");
            out.println("<table width=\"98%\">");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitForm('" + id + "/WPContent'); return false;\">Guardar</button>");
            if (id != null && idp != null && idptype != null) {
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlBack + "',document.getElementById('" + id + "/WPContent')); return false;\">Regresar</button>");
            }
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
        } // Parte en donde se presenta la forma para dar de alta el nuevo portlet de tipo dontenido o de sistema
        else if (action.equals("edit")) {

            String sobj = request.getParameter("sobj");
            boolean isGlobal = false;
            log.debug("Edit: sobj: " + sobj);
            if (sobj.startsWith("global|")) {
                isGlobal = true;
                sobj = sobj.substring(sobj.indexOf("|") + 1);
            }

            SWBResourceURL urlPA = paramRequest.getActionUrl();
            urlPA.setAction("new");

            obj = ont.getSemanticObject(id);
            cls = obj.getSemanticClass();
            SemanticObject so = ont.getSemanticObject(sobj);
            SWBFormMgr fmgr = new SWBFormMgr(Portlet.swb_Portlet, obj, null);
            fmgr.setAction(urlPA.toString());

            log.debug("new: suri: " + id);
            log.debug("new: sprop: " + idp);
            log.debug("new: sproptype: " + idptype);
            log.debug("new: sobj: " + sobj);

            fmgr.addHiddenParameter("suri", id);
            fmgr.addHiddenParameter("sprop", idp);
            fmgr.addHiddenParameter("sproptype", idptype);
            fmgr.addHiddenParameter("sobj", sobj);
            fmgr.addHiddenParameter("isGlobal", Boolean.toString(isGlobal));

//            out.println("<h1>");
//            out.println("Nuevo Portlet de tipo: <i>" + so.getDisplayName(paramRequest.getUser().getLanguage()) + "</i> del sitio " + so.getModel().getModelObject().getDisplayName(paramRequest.getUser().getId()));
//            out.println("</h1>");
            out.println(fmgr.renderForm());
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String sprop = request.getParameter("sprop");
        String sproptype = request.getParameter("sproptype");
        String action = response.getAction();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id); //WebPage
        SemanticClass cls = obj.getSemanticClass();

        if ("new".equals(action)) {
            log.debug("SWBAWebPageContents: ProcessAction(new) ");

            id = request.getParameter("suri");
            sprop = request.getParameter("sprop");
            sproptype = request.getParameter("sproptype");
            String sobj = request.getParameter("sobj");

            SemanticProperty prop = ont.getSemanticProperty(sprop);
            log.debug("SWBAWebPageContents: ProcessAction(new): sobj: " + sobj);

            SemanticObject wpage = null;
            wpage = ont.getSemanticObject(id);

            SWBFormMgr fmgr = new SWBFormMgr(Portlet.swb_Portlet, wpage, null);
            SemanticObject nso = fmgr.processForm(request);

            SemanticObject ptype = ont.getSemanticObject(sobj);
            nso.setObjectProperty(Portlet.swb_portletType, ptype);

            if (prop.getName().startsWith("has")) {
                obj.addObjectProperty(prop, nso);
            } else {
                obj.setObjectProperty(prop, nso);
            }
            if (id != null) {
                response.setRenderParameter("suri", id);
            }
            if (sprop != null) {
                response.setRenderParameter("sprop", sprop);
            }
            if (sproptype != null) {
                response.setRenderParameter("sproptype", sproptype);
            }
            if (nso != null) {
                response.setRenderParameter("nsuri", nso.getURI());
            }
            response.setRenderParameter("statmsg", "Se agreg%oacute; correctamente el contenido.");
            response.setMode(response.Mode_EDIT);
            response.setRenderParameter("act", "");
        } else if ("remove".equals(action)) //suri, prop
        {
            log.debug("SWBAWebPageContents.processAction(remove)");

            String sval = request.getParameter("sval");
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                String value = request.getParameter(prop.getName());
                //String sval = request.getParameter("sval");
                log.debug(prop.getURI() + ":" + sprop + "----" + (prop.getURI().equals(sprop) ? "true" : "false"));
                if (value != null && value.equals(sprop)) { //se tiene que validar el valor por si es mÃ¡s de una
                    if (sval != null) {
                        SemanticObject so = ont.getSemanticObject(sval);
                        obj.removeObjectProperty(prop, so);
                        if (prop.getName().equalsIgnoreCase("userrepository")) {
                            obj.setObjectProperty(prop, ont.getSemanticObject("urswb"));
                        }
                        so.remove();
                    }
                    break;
                }
            }

            if (id != null) {
                response.setRenderParameter("suri", id);
            }
            if (sprop != null) {
                response.setRenderParameter("sprop", sprop);
            }
            if (sproptype != null) {
                response.setRenderParameter("sproptype", sproptype);
            }
            log.debug("remove-closetab:"+sval);
            response.setRenderParameter("closetab", sval);
            response.setRenderParameter("statmsg", "Se elimin&oacute; correctamente el contenido.");
            response.setMode(response.Mode_EDIT);
        }
    }

    public void doAction(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doAction()");
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        String id = request.getParameter("suri");
        String sprop = request.getParameter("sprop");
        String sproptype = request.getParameter("sproptype");
        String action = request.getParameter("act");
        String errormsg = "", actmsg="";
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id); //WebPage
        SemanticClass cls = obj.getSemanticClass();

        SemanticObject so = null;
        if ("update".equals(action)) {
            try {
                if (request.getParameter("sval") != null) {
                    obj = ont.getSemanticObject(request.getParameter("sval"));
                }
                cls = obj.getSemanticClass();
                Iterator<SemanticProperty> it = cls.listProperties();
                while (it.hasNext()) {
                    SemanticProperty prop = it.next();
                    if (prop.isDataTypeProperty()) {
                        String value = request.getParameter(prop.getName()); //prop.getName()
                        log.debug("doAction(update): " + prop.getName() + " -- >" + value);
                        if (value != null) {
                            if (value.length() > 0) {
                                if (prop.isBoolean()) {
                                    if (value.equals("true") || value.equals("1")) {
                                        obj.setBooleanProperty(prop, true);
                                    } else if (value.equals("false") || value.equals("0")) {
                                        obj.setBooleanProperty(prop, false);
                                    }
                                }
                                if (prop.isInt()) {
                                    obj.setLongProperty(prop, Integer.parseInt(value));
                                }
                                if (prop.isString()) {
                                    obj.setProperty(prop, value);
                                }
                                if (prop.isFloat()) {
                                    obj.setFloatProperty(prop, Float.parseFloat(value));
                                }
                                
                            } else {
                                obj.removeProperty(prop);
                            }
                        }
                    }
                }
                so = obj;
                actmsg="Se actualiz&oacute; correctamente la prioridad del contenido.";
            } catch (Exception e)
            {
                log.error(e);
                errormsg = "Error al actualizar la prioridad del contenido.";
            }
        } else if ("updstatus".equals(action)) {
            String soid = request.getParameter("sval");
            String value = request.getParameter("val");
            try {
                if (value == null) {
                    value = "0";
                }
                SemanticObject sobj = ont.getSemanticObject(soid);
                sobj.setBooleanProperty(Activeable.swb_active, value.equals("true") ? true : false);

                SemanticClass scls = sobj.getSemanticClass();
                log.debug("doAction(updstatus):" + scls.getClassName() + ": " + value);
                so=sobj;
                actmsg="Se "+ (value.equals("true") ? "activ&oacute;" : "desactiv&oacute;") + " el contenido.";
            } catch (Exception e) {
                log.error(e);
                errormsg = "Error al " + (value.equals("true") ? "activar" : "desactivar") + " el contenido.";
            }
        } // revisar para agregar nuevo semantic object

        if(errormsg.length()==0)
        {
            out.println("<script type=\"text/javascript\">");
            out.println(" reloadTab('" + so.getURI() + "');");
            out.println(" setTabTitle('" + so.getURI() + "','" + so.getDisplayName(user.getLanguage()) + "','" + SWBContext.UTILS.getIconClass(so) + "')");
            out.println("</script>");
            out.println(actmsg);
        }
        else
        {
           out.println(errormsg);
        }
    }

    public String getDateFormat(long dateTime, String lang) {
        if (null == lang) {
            lang = "es";
        }
        Locale local = new Locale(lang);
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, local);
        return df.format(new Date(dateTime));
    }

    public String getDisplaySemObj(SemanticObject obj, String lang) {
        String ret = obj.getRDFName();
        try {
            ret = obj.getDisplayName(lang);
        } catch (Exception e) {
            ret = obj.getProperty(Descriptiveable.swb_title);
        }
        return ret;
    }

    public String getValueSemProp(SemanticObject obj, SemanticProperty prop) {
        String ret = "";
        try {
            if (prop.isDataTypeProperty()) {
                log.debug("getValueSemProp..." + obj.getProperty(prop));
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
            }
        } catch (Exception e) {
            ret = "Not set";
        }
        return ret;
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(Mode_Action)) {
            doAction(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }
}
