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
import org.semanticwb.portal.api.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBASOPropRefEditor extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBASOPropRefEditor.class);
    static String MODE_IdREQUEST = "FORMID";
    String Mode_Action = "PACTION";
    Portlet base = null;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView(SWBASOPropRefEditor...)");
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
        String idp = request.getParameter("sprop");
        String idpref = request.getParameter("spropref");

        log.debug("suri: " + id);
        log.debug("sprop: " + idp);
        log.debug("spropref: " + idpref);

        String action = request.getParameter("act");
        if (action == null) {
            action = "";
        }
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(id);
        SemanticObject obj = gobj.getSemanticObject();
        SemanticProperty spref = ont.getSemanticProperty(idpref);
        SemanticClass cls = obj.getSemanticClass();
        String title = cls.getName();

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



        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("update");

        if (action.equals("")) {
            //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            log.debug("CLASE: " + clsprop.getClassName());
            HashMap hmprop = new HashMap();
            Iterator<SemanticProperty> ite_sp = clsprop.listProperties();
            while (ite_sp.hasNext()) {
                SemanticProperty sp = ite_sp.next();
                log.debug("propiedad:" + sp.getDisplayName() + "---" + sp.getName());
                hmprop.put(sp, sp);
            }

            String refName = spref.getRangeClass().getName();
            SemanticClass sclassref = spref.getRangeClass();

            ite_sp = sclassref.listProperties();
            while (ite_sp.hasNext()) {
                SemanticProperty sp = ite_sp.next();
                log.debug("prop ref:" + sp.getName());
                hmprop.put(sp, sp);
            }

            SemanticProperty sptemp = null;

            title = clsprop.getName();
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table width=\"98%\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Action");
            out.println("</th>");
            out.println("<th>");
            out.println("Name");
            out.println("</th>");
            if (hmprop.get(Template.swb_language) != null) {
                sptemp = (SemanticProperty) hmprop.get(Template.swb_language);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }

            if (hmprop.get(Traceable.swb_modifiedBy) != null) {
                sptemp = (SemanticProperty) hmprop.get(Traceable.swb_modifiedBy);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(Template.swb_templateGroup) != null) {
                sptemp = (SemanticProperty) hmprop.get(Template.swb_templateGroup);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(Traceable.swb_created) != null) {
                sptemp = (SemanticProperty) hmprop.get(Traceable.swb_created);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(Traceable.swb_updated) != null) {
                sptemp = (SemanticProperty) hmprop.get(Traceable.swb_updated);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(Inheritable.swb_inherita) != null) {
                sptemp = (SemanticProperty) hmprop.get(Inheritable.swb_inherita);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(Priorityable.swb_priority) != null) {
                sptemp = (SemanticProperty) hmprop.get(Priorityable.swb_priority);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(Activeable.swb_active) != null) {
                sptemp = (SemanticProperty) hmprop.get(Activeable.swb_active);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");

            }
            out.println("</thead>");
            out.println("<tbody>");
            out.println("</tr>");

            Iterator<SemanticObject> itso = null;
            if (obj.getSemanticClass().equals(User.swb_User)) {
                itso = obj.listObjectProperties(spref);
            } else {
                itso = obj.listObjectProperties(prop);
            }

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
                urlr.setParameter("spropref", idpref);
                urlr.setParameter("sval", sobj.getURI());
                urlr.setParameter(prop.getName(), prop.getURI());
                urlr.setAction("remove");
                out.println("<a href=\"#\" onclick=\"if(confirm('¿Est&aacute;s seguro de querer eliminar "+sobj.getDisplayName(user.getLanguage())+"?')){submitUrl('" + urlr + "',this);} else { return false;}\">remove</a>");
                out.println("</td>");
                out.println("<td>");

                // Edición del elemento, abre un nuevo tab
                SWBResourceURL urlchoose = paramRequest.getRenderUrl();
                urlchoose.setParameter("suri", id);
                urlchoose.setParameter("sprop", idp);
                urlchoose.setParameter("spropref", idpref);
                urlchoose.setParameter("sobj", sobj.getURI());
                if (id != null) {
                    urlchoose.setParameter("rsuri", id);
                }
                if (idp != null) {
                    urlchoose.setParameter("rsprop", idp);
                }
                if (idpref != null) {
                    urlchoose.setParameter("rspropref", idpref);
                }
                urlchoose.setParameter("act", "edit");
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + sobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sobj.getDisplayName() + "');return false;\" >" + stitle + "</a>"); //onclick=\"submitUrl('"+urlchoose+"',this); return false;\"
                out.println("</td>");
                if (hmprop.get(Template.swb_language) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(Template.swb_language);
                    SemanticObject semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    out.println(getValueSemProp(semobj.getObjectProperty(semprop), Descriptiveable.swb_title));
                    //out.println(sobj.getURI());
                    out.println("</td>");
                }
                if (hmprop.get(Traceable.swb_modifiedBy) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(Traceable.swb_modifiedBy);
                    SemanticObject semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    if (null != semobj) {
                        out.println(getValueSemProp(semobj.getObjectProperty(semprop), Descriptiveable.swb_title));
                    } else {
                        out.println("Not Set");
                    }
                    out.println("</td>");
                }
                if (hmprop.get(Template.swb_templateGroup) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(Template.swb_templateGroup);
                    SemanticObject semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    out.println(getValueSemProp(semobj.getObjectProperty(semprop), Descriptiveable.swb_title));
                    out.println("</td>");
                }
                if (hmprop.get(Traceable.swb_created) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(Traceable.swb_created);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(Traceable.swb_updated) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(Traceable.swb_updated);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(Inheritable.swb_inherita) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(Inheritable.swb_inherita);
                    SemanticObject semobj = sobj.getObjectProperty(spref);
                    DisplayProperty dobj = new DisplayProperty(semprop.getDisplayProperty());
                    String pmsg = dobj.getPromptMessage();
                    String imsg = dobj.getInvalidMessage();
                    String selectValues = dobj.getSelectValues(user.getLanguage());
                    log.debug("selectValues: " + selectValues);
                    out.println("<td allign=\"center\">");
                    if (selectValues != null) {
                        String value = getValueSemProp(sobj, semprop);
                        if (null == value || "Not set".equals(value.trim())) {
                            value = "1";
                        }
                        int ivalue = Integer.parseInt(value);
                        SWBResourceURL urluinh = paramRequest.getRenderUrl();
                        urluinh.setMode(Mode_Action);
                        urluinh.setParameter("suri", id);
                        urluinh.setParameter("sprop", idp);
                        urluinh.setParameter("sval", sobj.getURI());
                        urluinh.setParameter("spropref", idpref);
                        urluinh.setParameter("act","updinherit");
                        out.println("<select  id=\"" + id + "/" + base.getId() + "/" + sobj.getURI() + "/INESO\" name=\"p_inherita\"  dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:120px;\" >"); //submitUrl('" + urluinh + "&p_inherita='+dijit.byId('" + id + "/" + base.getId() + "/" + sobj.getURI() + "/INESO').getValue(),this.domNode);

                        out.println("<script type=\"dojo/connect\" event=\"onChange\">");
                        out.println(" var self1=this;   ");
                        out.println(" showStatusURL('" + urluinh + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),true);");
                        out.println("</script>");

                        StringTokenizer st = new StringTokenizer(selectValues, "|");
                        while (st.hasMoreTokens()) {
                            String tok = st.nextToken();
                            int ind = tok.indexOf(':');
                            String idt = tok;
                            int ival = 1;
                            String val = tok;
                            if (ind > 0) {
                                idt = tok.substring(0, ind);
                                ival = Integer.parseInt(idt);
                                val = tok.substring(ind + 1);
                            }
                            out.println("<option value=\"" + ival + "\" " + (ival == ivalue ? "selected" : "") + ">");
                            out.println(val);
                            out.println("</option>");
                        }
                        out.println("</select>");
                    } else {
                        out.println(getValueSemProp(semobj, semprop));
                    }
                    out.println("</td>");
                }
                if (hmprop.get(Priorityable.swb_priority) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(Priorityable.swb_priority);
                    out.println("<td align=\"center\">");
                    SWBResourceURL urlu = paramRequest.getRenderUrl();
                    urlu.setMode(Mode_Action);
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idpref);
                    urlu.setParameter("act","update");

                    String val = getValueSemProp(sobj, semprop).trim();
                    String op1 = "", op2 = "", op3 = "", op4 = "", op5 = "";
                    if ("1".equals(val)) {
                        op1 = "selected";
                    } else if ("2".equals(val)) {
                        op2 = "selected";
                    } else if ("3".equals(val)) {
                        op3 = "selected";
                    } else if ("4".equals(val)) {
                        op4 = "selected";
                    } else if ("5".equals(val)) {
                        op5 = "selected";
                    }
                    out.println("               <select  id=\"" + id + "/" + base.getId() + "/" + sobj.getURI() + "/PSO\" name=\"" + semprop.getName() + "\"  dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:100px;\" >");

                    out.println("<script type=\"dojo/connect\" event=\"onChange\">");
                    out.println(" var self=this;   ");
                    out.println(" showStatusURL('" + urlu + "&'+self.attr(\"name\")+'='+self.attr(\"value\"),true);");
                    out.println("</script>");

                    out.println("                   <option value=\"1\" " + op1 + " >" + paramRequest.getLocaleString("defecto") + "</option>");
                    out.println("                   <option value=\"2\" " + op2 + " >" + paramRequest.getLocaleString("low") + "</option>");
                    out.println("                   <option value=\"3\" " + op3 + " >" + paramRequest.getLocaleString("media") + "</option>");
                    out.println("                   <option value=\"4\" " + op4 + " >" + paramRequest.getLocaleString("high") + "</option>");
                    out.println("                   <option value=\"5\" " + op5 + " >" + paramRequest.getLocaleString("priority") + "</option>");
                    out.println("               </select>");
                    
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
                    urlu.setParameter("spropref", idpref);
                    urlu.setParameter("act","updstatus");
                    
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
            urlNew.setParameter("spropref", idpref);
            urlNew.setParameter("act", "choose");
            out.println("<p><a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">Add New</a>");
            out.println("</p>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</div>");
        } else if (action.equals("choose")) //lista de instancias de tipo propiedad existentes para selecionar
        {
            log.debug("Choose...");
            log.debug("suri: " + id);
            log.debug("sprop: " + idp);
            log.debug("spropref: " + idpref);
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticProperty propref = null;
            if (idpref != null) {
                propref = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idpref);
            }

            SemanticClass clsprop = propref.getRangeClass();
            title = clsprop.getName();
            log.debug(title);

            HashMap hmSO = new HashMap();
            Iterator<SemanticObject> ite_so = obj.listObjectProperties(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp));
            while (ite_so.hasNext()) {
                SemanticObject so = ite_so.next();
                if (null != so) {
                    SemanticObject soref = so.getObjectProperty(propref);
                    hmSO.put(soref.getURI(), soref);
//                    log.debug(soref.getURI()+" -- HashMap");
                }
            }

            SemanticObject obusrRep = null;
            if (clsprop.equals(Role.swb_Role)) {
                GenericObject go = obj.getModel().getModelObject().createGenericInstance();
                if (go instanceof WebSite) {
                    obusrRep = ((WebSite) go).getUserRepository().getSemanticObject();
                } else {
                    obusrRep = go.getSemanticObject();
                }
            }

            if (obj.getSemanticClass().equals(User.swb_User) && propref.getRangeClass().equals(Role.swb_Role))
            {
                ite_so = obj.listObjectProperties(propref);
                while (ite_so.hasNext()) {
                    SemanticObject so = ite_so.next();
                    if (null != so) {
                        //SemanticObject soref = so.getObjectProperty(propref);
                        hmSO.put(so.getURI(), so);
//                    log.debug(soref.getURI()+" -- HashMap");
                    }
                }
            }

            if (obusrRep != null) {
                obj = obusrRep;
            }

            out.println("<div class=\"swbform\">");
            out.println("<form id=\"" + id + "/chooseSO\" action=\"" + url + "\" method=\"get\"  onsubmit=\"submitUrl('" + url + "',this); return false;\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">"); //obj.getURI()
            out.println("<fieldset>");
            out.println("<table width=\"98%\">");

            log.debug("Clase: " + clsprop.getName());
            Iterator<SemanticObject> itso = obj.getModel().listInstancesOfClass(clsprop);

            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                String stitle = getDisplaySemObj(sobj, user.getLanguage());
                out.println("<tr>");
                out.println("<td>" + stitle + "</td> ");
                log.debug(sobj.getURI() + "choose");
                if (hmSO.get(sobj.getURI()) == null) {
                    SWBResourceURL urlchoose = paramRequest.getActionUrl();
                    if (idp.endsWith("hasRole") && clsprop.equals(Role.swb_Role)) {
                        urlchoose.setAction("choose");
                        urlchoose.setParameter("suri", id);
                    } else {
                        urlchoose.setAction("new");
                        urlchoose.setParameter("suri", id); //obj.getURI()
                    } //choose

                    urlchoose.setParameter("sprop", idp);
                    if (idpref != null) {
                        urlchoose.setParameter("spropref", idpref);
                    }
                    urlchoose.setParameter("sobj", sobj.getURI());
                    out.println("<td>");
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlchoose + "',this); return false;\">" + stitle + "</a>");
                    out.println("</td> ");
                } else {
                    out.println("<td>");
                    out.println(title + " in use. ");
                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("	</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            if (request.getParameter("suri") != null) {
                urlBack.setParameter("suri", id);
            }
            if (request.getParameter("sprop") != null) {
                urlBack.setParameter("sprop", idp);
            }
            if (request.getParameter("spropref") != null) {
                urlBack.setParameter("spropref", idpref);
            }
            urlBack.setParameter("act", "");
            //out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitUrl('" + url + "',this); return false;\" >Guardar</button>");
            if (id != null && idp != null && idpref != null) {
                out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitUrl('" + urlBack + "',document.getElementById('" + id + "/chooseSO')); return false;\" >Regresar</button>");
            }
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String rid = request.getParameter("rsuri");
        String sprop = request.getParameter("sprop");
        String spropref = request.getParameter("spropref");
        String sval = request.getParameter("sval");
        String action = response.getAction();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass cls = obj.getSemanticClass();
        User user = response.getUser();

        log.debug("ProcessAction() ---------------- ("+action+")");
        log.debug("suri:     "+id);
        log.debug("sprop:    "+sprop);
        log.debug("spropref: "+spropref);
        log.debug("sval:     "+id);

        if ("new".equals(action)) {

            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            SemanticClass ncls = prop.getRangeClass();
            String id_usr_request = request.getParameter("id_usr_request");
            log.debug("(new): " + ncls.getName());
            if (ncls.isAutogenId()||id_usr_request!=null) {
                if(id_usr_request==null) id_usr_request="";
                long lid = SWBPlatform.getSemanticMgr().getCounter(obj.getModel().getName() + "/" + ncls.getName());
                String str_lid = id_usr_request + lid;
                SemanticObject nobj = obj.getModel().createSemanticObject(obj.getModel().getObjectUri(str_lid, ncls), ncls);
                if (prop.getName().startsWith("has")) {
                    log.debug("'has' property..."+prop.getName());
                    obj.addObjectProperty(prop, nobj);
                } else {
                    log.debug("'no has' property..."+prop.getName());
                    obj.setObjectProperty(prop, nobj);
                }
                if (prop.getName().endsWith("Ref") && spropref != null) {
                    log.debug("'Ref' property..."+prop.getName());
                    String soid = request.getParameter("sobj");  // id de Template seleccionado, según sea el tipo de SO
                    SemanticObject soref = null;
                    if (soid != null && soid.trim().length() > 0) {
                        soref = ont.getSemanticObject(soid);
                    }
                    SemanticProperty spref = ont.getSemanticProperty(spropref);
                    nobj.setObjectProperty(spref, soref);

                    Iterator<SemanticProperty> itsp = nobj.getSemanticClass().listProperties();
                    while (itsp.hasNext()) {
                        SemanticProperty sp = itsp.next();
                        if (sp.equals(Priorityable.swb_priority)) {
                            nobj.setLongProperty(sp, 3);
                            break;
                        }
                    }
                }

                response.setMode(response.Mode_EDIT);
                if (id != null) {
                    response.setRenderParameter("suri", id);
                }
                if (nobj != null) {
                    response.setRenderParameter("nsuri", nobj.getURI());
                }
                if (rid != null) {
                    response.setRenderParameter("rsuri", rid);
                }
                if (sprop != null) {
                    response.setRenderParameter("sprop", sprop);
                }
                if (spropref != null) {
                    response.setRenderParameter("spropref", spropref);
                }

                response.setRenderParameter("statmsg", "Se agreg&oacute; correctamente "+nobj.getDisplayName(user.getLanguage()));
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("act", "");
            } else {
                //Llamada para pedir el id del SemanticObject que no cuenta con el AutogenID
                Enumeration enu_p = request.getParameterNames();
                while (enu_p.hasMoreElements()) {
                    String p_name = (String) enu_p.nextElement();
                    response.setRenderParameter(p_name, request.getParameter(p_name));
                }
                response.setMode(MODE_IdREQUEST);
            }
        } else if ("remove".equals(action)) //suri, prop
        {
            log.debug("(remove)");
            SemanticObject soc = ont.getSemanticObject(id);
            sval = request.getParameter("sval");
            log.debug(soc.getSemanticClass().getName());
            String name ="";
            if (soc.getSemanticClass().equals(User.swb_User)) {
                SemanticProperty sp = ont.getSemanticProperty(spropref);
                SemanticObject soval = ont.getSemanticObject(sval);
                soc.removeObjectProperty(sp, soval);

            } else {
                SemanticObject sobj = ont.getSemanticObject(sval);
                name=sobj.getDisplayName(user.getLanguage());
                sobj.remove();
            }
            if (id != null) {
                response.setRenderParameter("suri", id);
            }
            if (sprop != null) {
                response.setRenderParameter("sprop", sprop);
            }
            if (spropref != null) {
                response.setRenderParameter("spropref", spropref);
            }
            log.debug("remove-closetab:"+sval);
            response.setRenderParameter("closetab", sval);
            response.setRenderParameter("statmsg", "Se elimin&oacute; correctamente "+name+".");
            response.setMode(response.Mode_EDIT);

        } else if ("choose".equals(action)) //suri, prop
        {
            log.debug("(choose)");
            sprop = request.getParameter("sprop");
            spropref = request.getParameter("spropref");
            String sobj = request.getParameter("sobj");
            sval = null;
            if (sobj == null) {
                sval = SWBUtils.TEXT.decode(request.getParameter("sval"), "UTF-8");
            }
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            SemanticProperty propref = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(spropref);
            String pname = prop.getName();
            log.debug("Property Name:" + pname);
            if (!pname.startsWith("has")) {
                log.debug("Property Name:" + pname);
                if (sval != null) {
                    if (sval.length() > 0) {
                        if (prop.isBoolean()) {
                            obj.setBooleanProperty(prop, Boolean.parseBoolean(sval));
                        }
                        if (prop.isInt()) {
                            obj.setLongProperty(prop, Integer.parseInt(sval));
                        }
                        if (prop.isString()) {
                            obj.setProperty(prop, sval);
                        }
                    } else {
                        obj.removeProperty(prop);
                    }
                } else if (sobj != null) {
                    SemanticObject aux = ont.getSemanticObject(sobj);
                    if (sobj != null) {
                        obj.setObjectProperty(prop, aux); //actualizando el objectProperty a una instancia existente 
                    } else {
                        obj.removeProperty(prop);
                    }
                }
            } else {
                if (sobj != null) {
                    log.debug("Agregando un " + propref.getName() + " a " + obj.getURI());
                    SemanticObject aux = ont.getSemanticObject(sobj); //agregando al objectProperty nueva instancia
                    obj.addObjectProperty(propref, aux);
                }
            }
            response.setRenderParameter("spropref", spropref);
            response.setRenderParameter("sprop", sprop);
            response.setRenderParameter("sobj", sobj);
            if (id != null) {
                response.setRenderParameter("suri", id);
            }
            if (rid != null) {
                response.setRenderParameter("rsuri", rid);
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
            ret = obj.getProperty(Descriptiveable.swb_title);
        }
        return ret;
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

    public void doFormID(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SWBResourceURL urla = paramRequest.getActionUrl();
        urla.setAction("new");
        out.println("<div class=\"swbform\">");
        out.println("<form id=\""+id+"/getIdItem\" action=\"" + urla + "\" method=\"post\">");
        out.println("<fieldset>");
        out.println("<table width=\"98%\">");
        out.println("	<tr>");
        out.println("		<td><label for=\"id_usr_request\">Id</label></td><td><input type=\"text\" id=\"id_usr_request\" name=\"id_usr_request\" value=\"\"/></td>");
        out.println("	</tr>");
        out.println("</table>");
        Enumeration enu_p = request.getParameterNames();
        while (enu_p.hasMoreElements()) {
            String p_name = (String) enu_p.nextElement();
            out.println("<input type=\"hidden\" name=\"" + p_name + "\" value=\"" + request.getParameter(p_name) + "\">");
        }
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitForm('"+id+"/getIdItem');return false;\" >Guardar</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(MODE_IdREQUEST)) {
            doFormID(request, response, paramRequest);
        } else if (paramRequest.getMode().equals(Mode_Action)) {
            doAction(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
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
        //String rid = request.getParameter("rsuri");
        String sprop = request.getParameter("sprop");
        //String sproptype = request.getParameter("sproptype");
        String spropref = request.getParameter("spropref");
        String sval = request.getParameter("sval");
        String action = request.getParameter("act");
        String errormsg = "", actmsg="";
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id); //WebPage
        SemanticClass cls = obj.getSemanticClass();

        SemanticObject so = null;

        log.debug("ProcessAction() ---------------- ("+action+")");
        log.debug("suri:     "+id);
        log.debug("sprop:    "+sprop);
        log.debug("spropref: "+spropref);
        log.debug("sval:     "+id);

        if ("update".equals(action)) {
            try
            {
                if (sval != null) {
                    obj = ont.getSemanticObject(sval);
                }
                cls = obj.getSemanticClass();
                Iterator<SemanticProperty> it = cls.listProperties();
                while (it.hasNext()) {
                    SemanticProperty prop = it.next();
                    if (prop.isDataTypeProperty()) {
                        String value = request.getParameter(prop.getName());
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
                    //else if(prop.isBoolean()) obj.setBooleanProperty(prop, false);
                    }
                }
                so = obj;
                actmsg="Se actualiz&oacute; correctamente la prioridad del elemento.";
            } catch (Exception e)
            {
                log.error(e);
                errormsg = "Error al actualizar la prioridad del elemento.";
            }
        } else if ("updstatus".equals(action)) {
            String soid = request.getParameter("sval");
            String value = request.getParameter("val");
            try
            {
                if (value == null) {
                    value = "0";
                }
                SemanticObject sobj = ont.getSemanticObject(soid);
                sobj.setBooleanProperty(Activeable.swb_active, value.equals("true") ? true : false);
                SemanticClass scls = sobj.getSemanticClass();
                log.debug("doAction(updstatus):" + scls.getClassName() + ": " + value);
                so=sobj;
                actmsg="Se "+ (value.equals("true") ? "activ&oacute;" : "desactiv&oacute;") + " el elemento.";
            } catch (Exception e) {
                log.error(e);
                errormsg = "Error al " + (value.equals("true") ? "activar" : "desactivar") + " el elemento.";
            }
        } else if ("updinherit".equals(action)) {
            String soid = request.getParameter("sval");
            String value = request.getParameter("p_inherita");
            try
            {
                if (value == null) {
                    value = "1";
                }
                SemanticObject sobj = ont.getSemanticObject(soid);
                sobj.setLongProperty(Inheritable.swb_inherita, Long.parseLong(value));

                SemanticClass scls = sobj.getSemanticClass();
                log.debug("doAction(updinherit):" + scls.getClassName() + ", value:" + value);

                so=sobj;
                actmsg="Se actualiz&oacute; la herencia del elemento.";
            } catch (Exception e) {
                log.error(e);
                errormsg = "Error al actualizar la herencia del elemento.";
            }
        }

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
}
