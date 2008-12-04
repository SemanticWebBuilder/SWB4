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

    /** Creates a new instance of TopicContents */
    public SWBAWebPageContents() {
    }
    static String MODE_IdREQUEST = "FORMID";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        log.debug("doView(SWBAWebPageContents...)");
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        log.debug("doEdit(SWBAWebPageContents...)");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String idp = request.getParameter("sprop");
        String idptype = request.getParameter("sproptype");
        SWBVocabulary voc = SWBContext.getVocabulary();

        String action = request.getParameter("act");

        if (id == null) {
            id = paramRequest.getTopic().getWebSiteId();
        }
        if (action == null) {
            action = "";
        }
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass cls = obj.getSemanticClass();

        if(request.getParameter("nsuri")!=null&&request.getParameter("nsuri").trim().length()>0)
        {
            SemanticObject snobj = ont.getSemanticObject(request.getParameter("nsuri"));
            if(snobj!=null)
                out.println("<script type=\"text/javascript\">");
                //out.println("dijit.byId('swbDialog').hide();");
                out.println("addNewTab('"+snobj.getURI()+"','"+snobj.getDisplayName()+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp');");
                out.println("</script>");
        }
        
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

            out.println("<fieldset>");
            out.println("<table width=\"100%\" class=\"swbform\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Action");
            out.println("</th>");
            out.println("<th>");
            out.println("Name");
            out.println("</th>");
            String propname = "";
            sptemp = hmprop.get(voc.swb_created);
            if (sptemp != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {propname = ""; }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            sptemp = hmprop.get(voc.swb_updated);
            if (sptemp != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {propname = "";}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            sptemp = hmprop.get(voc.swb_priority);
            if (hmprop.get(SWBContext.getVocabulary().swb_priority) != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) { propname = ""; }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            sptemp = hmprop.get(voc.swb_active);
            if (sptemp != null) {
                propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) { propname = ""; }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            SemanticProperty semprop =null;
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
                out.println("<a href=\"#\" onclick=\"submitUrl('" + urlr + "',this); return false;\">remove</a>");
                out.println("</td>");
                out.println("<td>");
                SWBResourceURL urlchoose = paramRequest.getRenderUrl();
                urlchoose.setParameter("suri", id);
                urlchoose.setParameter("sprop", idp);
                urlchoose.setParameter("sobj", sobj.getURI());
                urlchoose.setParameter("act", "edit");
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + sobj.getURI() + "','"+sobj.getDisplayName()+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp');return false;\">" + stitle + "</a>");
                out.println("</td>");
                if (hmprop.get(SWBContext.getVocabulary().swb_created) != null) {
                    semprop = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().swb_created);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(SWBContext.getVocabulary().swb_updated) != null) {
                    semprop = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().swb_updated);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(SWBContext.getVocabulary().swb_priority) != null) {
                    semprop = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().swb_priority);
                    out.println("<td align=\"center\">");
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setAction("update");
                    out.println("<input type=\"text\" name=\"" + semprop.getName() + "\" onblur=\"submitUrl('" + urlu + "&" + semprop.getName() + "='+this.value,this); return false;\" value=\"" + getValueSemProp(sobj, semprop) + "\" />");
                    out.println("</td>");
                }
                if (hmprop.get(SWBContext.getVocabulary().swb_active) != null) {
                    out.println("<td align=\"center\">");
                    boolean activo = false;
                    if (sobj.getBooleanProperty(SWBContext.getVocabulary().swb_active)) {
                        activo = true;
                    }
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setAction("updstatus");
                    out.println("<input name=\"" + prop.getName() + sobj.getURI() + "\" type=\"checkbox\" value=\"1\" id=\"" + prop.getName() + sobj.getURI() + "\" onclick=\"submitUrl('" + urlu + "&val='+this.checked,this); return false;\"  " + (activo ? "checked='checked'" : "") + "/>");
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
            out.println("");
        }  else if (action.equals("choose")) { //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            idptype = SWBContext.getVocabulary().swb_portletType.getURI();
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

            SWBResourceURL urlAdd = paramRequest.getRenderUrl();
            urlAdd.setMode(SWBResourceURL.Mode_EDIT);
            urlAdd.setParameter("act", "edit");
            out.println("<form id=\""+id+"/WPContent\" action=\"" + urlAdd + "\" method=\"post\" class=\"swbform\">");
            out.println("<input type=\"hidden\" id=\""+id+"/suri\" name=\"suri\" value=\""+id+"\">");
            out.println("<input type=\"hidden\" id=\""+id+"/sprop\" name=\"sprop\" value=\""+idp+"\">");
            out.println("<input type=\"hidden\" id=\""+id+"/sproptype\" name=\"sproptype\" value=\""+idptype+"\">");
            
            // Lista de Tipo de portlet disponibles de Global, separados por contenido y sistema
            
            out.println("<fieldset>");
            out.println("	<legend> Choose (Global) - " + prop.getDisplayName(user.getLanguage()) + " ( " + getDisplaySemObj(obj, user.getLanguage()) + " )</legend>");
            Iterator<SemanticObject> itgso = SWBContext.getGlobalWebSite().getSemanticObject().getModel().listInstancesOfClass(clsprop);
            HashMap<String, SemanticObject> hmContent = new HashMap();
            HashMap<String, SemanticObject> hmSystem = new HashMap();
            while (itgso.hasNext()) {
                SemanticObject sobj = itgso.next();
                int mode = sobj.getIntProperty(voc.swb_portletMode);
                if (mode == 1)//tipo contenido
                {
                    hmContent.put(sobj.getId(), sobj);
                } else if (mode == 3)//tipo sistema
                {
                    hmSystem.put(sobj.getId(), sobj);
                }
            }

            itgso = hmContent.values().iterator();
            if (hmContent.size() > 0) {
                out.println("<table width=\"100%\">");
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
                    String sdescription = sobj.getProperty(voc.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\""+sobj.getURI()+"/sptype\" value=\"global|"+sobj.getURI()+"\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription!=null?sdescription:"");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }
            itgso = hmSystem.values().iterator();
            if (hmSystem.size() > 0) {
                out.println("<table width=\"100%\">");
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
                    String sdescription = sobj.getProperty(voc.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\""+sobj.getURI()+"/sptype\" value=\"global|"+sobj.getURI()+"\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription!=null?sdescription:"");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }
            out.println("</fieldset>");
            
            // Lista de tipo de portlets disponibles del sitio, separados por contenido y sistema 

            out.println("<fieldset>");
            out.println("	<legend> Choose (" + obj.getModel().getModelObject().getDisplayName() + ") - " + prop.getDisplayName(user.getLanguage()) + " ( " + getDisplaySemObj(obj, user.getLanguage()) + " )</legend>");
            
            hmContent = new HashMap();
            hmSystem = new HashMap();
            
            Iterator<SemanticObject> itso = obj.getModel().listInstancesOfClass(clsprop);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                int mode = sobj.getIntProperty(voc.swb_portletMode);
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
                out.println("<table width=\"100%\">");
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
                    String sdescription = sobj.getProperty(voc.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\""+sobj.getURI()+"/sptype\" value=\""+sobj.getURI()+"\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription!=null?sdescription:"");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }
            itso = hmSystem.values().iterator();
            if (hmSystem.size() > 0) {
                out.println("<table width=\"100%\">");
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
                    String sdescription = sobj.getProperty(voc.swb_description);
                    out.println("<tr>");
                    out.println("<td align=\"center\">");
                    out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"sobj\" id=\""+sobj.getURI()+"/sptype\" value=\""+sobj.getURI()+"\" >");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(stitle);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(sdescription!=null?sdescription:"");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</tbody> ");
                out.println("</table> ");
            }

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
            out.println("<table width=\"100%\">");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<th>");
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitForm('"+id+"/WPContent'); return false;\">Guardar</button>");
            if (id != null && idp != null  && idptype != null) {
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlBack + "',document.getElementById('"+id+"/WPContent')); return false;\">Regresar</button>");
            }
            out.println("</th>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
        } 
        
        // Parte en donde se presenta la forma para dar de alta el nuevo portlet de tipo dontenido o de sistema
        
        else if (action.equals("edit")) {

            String sobj = request.getParameter("sobj");
            boolean isGlobal = false;
            log.debug("Edit: sobj: "+sobj);
            if(sobj.startsWith("global|"))
            {
                isGlobal=true;
                sobj = sobj.substring(sobj.indexOf("|")+1);
            }

            SWBResourceURL urlPA = paramRequest.getActionUrl();
            urlPA.setAction("new");
            
            obj = ont.getSemanticObject(id);
            cls = obj.getSemanticClass();
            SemanticObject so = ont.getSemanticObject(sobj);
            SWBFormMgr fmgr = new SWBFormMgr(voc.swb_Portlet, obj, null);
            fmgr.setAction(urlPA.toString());
            
            log.debug("new: suri: "+id);
            log.debug("new: sprop: "+idp);
            log.debug("new: sproptype: "+idptype);
            log.debug("new: sobj: "+sobj);
            
            fmgr.addHiddenParameter("suri", id);              
            fmgr.addHiddenParameter("sprop", idp); 
            fmgr.addHiddenParameter("sproptype", idptype);
            fmgr.addHiddenParameter("sobj", sobj);
            fmgr.addHiddenParameter("isGlobal", Boolean.toString(isGlobal));
            
            out.println("<h1>");
            out.println("Nuevo Portlet de tipo: <i>"+so.getDisplayName(paramRequest.getUser().getLanguage())+"</i> del sitio "+so.getModel().getModelObject().getDisplayName(paramRequest.getUser().getId()));
            out.println("</h1>");
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
        SWBVocabulary voc = SWBContext.getVocabulary();
        
        if ("update".equals(action)) {
            if (request.getParameter("sval") != null) {
                obj = ont.getSemanticObject(request.getParameter("sval"));
            }
            cls = obj.getSemanticClass();
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                if (prop.isDataTypeProperty()) {
                    String value = request.getParameter(prop.getName());
                    log.debug("SWBAWebPageContents: ProcessAction(update): " + prop.getName() + " -- >" + value);
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
            if (id != null) {
                response.setRenderParameter("suri", id);
            }
            if (sprop != null) {
                response.setRenderParameter("sprop", sprop);
            }
            if (sproptype != null) {
                response.setRenderParameter("sproptype", sproptype);
            }
            response.setRenderParameter("act", "");
        } else if ("updstatus".equals(action)) {
            String soid = request.getParameter("sval");
            String value = request.getParameter("val");
            if (value == null) {
                value = "0";
            }
            SemanticObject sobj = ont.getSemanticObject(soid);
            sobj.setBooleanProperty(SWBContext.getVocabulary().swb_active, value.equals("true") ? true : false);

            SemanticClass scls = sobj.getSemanticClass();
            log.debug("SWBAWebPageContents: ProcessAction(updstatus):" + scls.getClassName() + ": " + value);

            if (id != null) {
                response.setRenderParameter("suri", id);
            }
            if (sprop != null) {
                response.setRenderParameter("sprop", sprop);
            }
            if (sproptype != null) {
                response.setRenderParameter("sproptype", sproptype);
            }
        } // revisar para agregar nuevo semantic object
        else if ("new".equals(action)) {
            log.debug("SWBAWebPageContents: ProcessAction(new) ");
            
            id = request.getParameter("suri");
            sprop = request.getParameter("sprop");
            sproptype = request.getParameter("sproptype");
            String sobj = request.getParameter("sobj");
            
            SemanticProperty prop = ont.getSemanticProperty(sprop);
            log.debug("SWBAWebPageContents: ProcessAction(new): sobj: "+sobj);
            
            SemanticObject wpage = null;
            wpage = ont.getSemanticObject(id);

            SWBFormMgr fmgr = new SWBFormMgr(voc.swb_Portlet, wpage, null);
            SemanticObject nso = fmgr.processForm(request);
            
            SemanticObject ptype = ont.getSemanticObject(sobj);
            nso.setObjectProperty(voc.swb_portletType, ptype);
            
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
            response.setMode(response.Mode_EDIT);
            response.setRenderParameter("act", "");
        }
         else if ("remove".equals(action)) //suri, prop
        {
            log.debug("SWBAWebPageContents.processAction(remove)");
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                String value = request.getParameter(prop.getName());
                String sval = request.getParameter("sval");
                log.debug(prop.getURI() + ":" + sprop + "----" + (prop.getURI().equals(sprop) ? "true" : "false"));
                if (value != null && value.equals(sprop)) { //se tiene que validar el valor por si es mÃ¡s de una
                    if (sval != null) {
                        obj.removeObjectProperty(prop, ont.getSemanticObject(sval));
                        if (prop.getName().equalsIgnoreCase("userrepository")) {
                            obj.setObjectProperty(prop, ont.getSemanticObject("urswb"));
                        }
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
            ret = obj.getProperty(SWBContext.getVocabulary().swb_title);
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
}
