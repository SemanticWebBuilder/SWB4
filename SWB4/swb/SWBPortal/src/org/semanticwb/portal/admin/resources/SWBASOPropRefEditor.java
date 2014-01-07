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
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBASOPropRefEditor.
 * 
 * @author juan.fernandez
 */
public class SWBASOPropRefEditor extends GenericAdmResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBASOPropRefEditor.class);
    
    /** The MOD e_ id request. */
    static String MODE_IdREQUEST = "FORMID";
    
    /** The US e_ select. */
    static String USE_SELECT = "useselect";
    
    /** The US e_ add. */
    static String USE_ADD = "useadd";

    /** The US e_ global. */
    static String USE_GLOBAL = "useglobal";
    
    /** The DE l_ so. */
    static String DEL_SO = "delso";

    /** The Mode_ action. */
    String Mode_Action = "PACTION";
    
    /** The base. */
    Resource base = null;
    
    static String NO_INHERIT = "notInherit";
    static String AND_EVAL = "andEval";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doIndex(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //No se indexa...
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView(SWBASOPropRefEditor...)");
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
        String idp = request.getParameter("sprop");
        String idpref = request.getParameter("spropref");
        
        //Agregado por Jorge (06/Enero/2014), valores por defecto.
        if(id==null || id.isEmpty())
        {
            id=SWBContext.getAdminWebSite().getURI();
        }
        if(idp==null || idp.isEmpty()) idp="http://www.semanticwebbuilder.org/swb4/ontology#hasModelProperty";
        if(idpref==null || idpref.isEmpty()) idpref="http://www.semanticwebbuilder.org/swb4/ontology#hasModelProperty";
        //Termina Agregado por Jorge (06/Enero/2014)

        if(base.getAttribute(USE_ADD)==null&&base.getAttribute(USE_SELECT)==null){
            base.setAttribute(USE_ADD, "0");
            base.setAttribute(USE_SELECT, "1");
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {log.error(e);
            }
        }

        if(base.getAttribute(USE_GLOBAL)==null){
            base.setAttribute(USE_GLOBAL, "0");
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {log.error(e);
            }
        }

        log.debug("suri: " + id);
        log.debug("sprop: " + idp);
        log.debug("spropref: " + idpref);

        String action = request.getParameter("act");
        if (action == null) {
            action = "";
        }
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        //GenericObject gobj = ont.getGenericObject(id);
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticProperty spro = ont.getSemanticProperty(idp);
        SemanticProperty spref = null;
        
       
        if(idpref!=null){
            spref=ont.getSemanticProperty(idpref);
        }

        //System.out.println("spref: "+spref);


        SemanticClass cls = obj.getSemanticClass();
        String title = cls.getName();

        log.debug("Antes de revisar instancia de tipo Referensable");
        boolean validateInherit = Boolean.FALSE;
        if(obj.createGenericInstance() instanceof Referensable || obj.getSemanticClass().isSubClass(Referensable.swb_Referensable)){
            validateInherit = Boolean.TRUE;
            log.debug("Instancia de tipo Referensable");
        }
        
        StringBuilder inheritHeader = new StringBuilder();

        out.println("<script type=\"text/javascript\">");
        //String s_soid = request.getParameter("so");
        if (request.getParameter("statmsg") != null && request.getParameter("statmsg").trim().length() > 0) {
            log.debug("showStatus");
            out.println("   showStatus('" + request.getParameter("statmsg") + "');");
            out.println("updateTreeNodeByURI('" + obj.getURI() + "');");
            String icon = SWBContext.UTILS.getIconClass(obj);
            out.println("setTabTitle('" + obj.getURI() + "','" + obj.getDisplayName(user.getLanguage()) + "','" + icon + "');");
        }
        if (request.getParameter("closetab") != null && request.getParameter("closetab").trim().length() > 0) {
            log.debug("closeTab..." + request.getParameter("closetab"));
            out.println("   closeTab('" + request.getParameter("closetab") + "');");
        }
        out.println("</script>");

        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("update");

        if (action.equals("")) {
            //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            //System.out.println(idp+" prop:"+prop+" dom:"+prop.getDomainClass()+" tp:"+prop.isObjectProperty()+" range:"+prop.getRange()+" cls:"+prop.getRangeClass());

            SemanticProperty spinHerit = null;
            SemanticProperty spandEval = null;
            Iterator<SemanticProperty> itsemprops = obj.getSemanticClass().listProperties();
            String inicioidp = spro.getName();
            String inicioidpref = spref!=null?spref.getName():"";
            if(null!=inicioidp&&inicioidp.endsWith("Ref")){
                if(inicioidp.startsWith("has")){
                    inicioidp = inicioidp.substring(3);
                }
            }
            log.debug("idp: "+inicioidp);
            
            if(null!=inicioidpref&&inicioidpref.endsWith("Ref")){
                if(inicioidpref.startsWith("has")){
                    inicioidpref = inicioidpref.substring(3);
                }
            }
            log.debug("idpref: "+inicioidpref);
            
            while (itsemprops.hasNext()) {
                SemanticProperty semanticProperty = itsemprops.next();
                log.debug("propiedad:" + semanticProperty.getDisplayName() + "---" + semanticProperty.getName());
                if(semanticProperty.getName().startsWith(AND_EVAL+inicioidp)||semanticProperty.getName().startsWith(AND_EVAL+inicioidpref)){ //validateInherit&&
                    spandEval = semanticProperty;
                     log.debug("Contiene propiedad de AND evaluation "+semanticProperty.getName());
                }
                if(semanticProperty.getName().startsWith(NO_INHERIT+inicioidp)||semanticProperty.getName().startsWith(NO_INHERIT+inicioidpref)){ //validateInherit&&
                    spinHerit = semanticProperty;
                     log.debug("Contiene propiedad de noInherit "+semanticProperty.getName());
                }
            }
            
            
            
            log.debug("CLASE: " + clsprop.getClassName());
            HashMap hmprop = new HashMap();
            Iterator<SemanticProperty> ite_sp = clsprop.listProperties();
            while (ite_sp.hasNext()) {
                SemanticProperty sp = ite_sp.next();
                log.debug("propiedad:" + sp.getDisplayName() + "---" + sp.getName());
                //System.out.println("propiedad:" + sp.getDisplayName() + "---" + sp.getName());
                hmprop.put(sp, sp);
            }


            SemanticClass sclassref = null;
            String strTitleProp = prop.getDisplayName(user.getLanguage());
            if(spref!=null)
            {
                sclassref = spref.getRangeClass();
                strTitleProp=sclassref.getDisplayName(user.getLanguage());
                ite_sp = sclassref.listProperties();
                while (ite_sp.hasNext()) {
                    SemanticProperty sp = ite_sp.next();
                    log.debug("prop ref:" + sp.getName());
                    hmprop.put(sp, sp);
                }
            }

            SemanticProperty sptemp = null;

            int numcols = 0;
            title = clsprop.getName();
            out.println("<div class=\"swbform\">");
            if(spandEval != null ||spinHerit!=null){
                out.println("<fieldset><div align=\"right\">");
                if (spandEval != null) {
                    SWBResourceURL urlAnd = paramRequest.getActionUrl();
                    urlAnd.setParameter("suri", id);
                    urlAnd.setAction(AND_EVAL);
                    urlAnd.setParameter("sprop", idp);
                    urlAnd.setParameter("spropref", idpref);
                    urlAnd.setParameter(spandEval.getName(), spandEval.getURI());

                    String value = getValueSemProp(obj, spandEval);
                    String checked = null != value && value.equals("true") ? "checked" : "";
                    out.println("<input type=\"checkbox\" id=\"" + id + AND_EVAL + "\" name=\"" + AND_EVAL + "\" value=\"1\" " + checked + " onclick=\"submitUrl('" + urlAnd + "',this);\" /><label for=\"" + id + AND_EVAL + "\" >" + paramRequest.getLocaleString("lbl_andEval") + "</label>&nbsp;&nbsp;");
                }
                if(spinHerit!=null){
                    SWBResourceURL urlNoInherit = paramRequest.getActionUrl();
                    urlNoInherit.setParameter("suri", id);
                    urlNoInherit.setAction(NO_INHERIT);
                    urlNoInherit.setParameter("sprop", idp);
                    urlNoInherit.setParameter("spropref", idpref);
                    urlNoInherit.setParameter(spinHerit.getName(), spinHerit.getURI());
                    String value = getValueSemProp(obj, spinHerit);
                    String checked = null!=value&&value.equals("true")?"checked":""; 
                    out.println("<input type=\"checkbox\" id=\""+id+NO_INHERIT+"\" name=\""+NO_INHERIT+"\" value=\"1\" "+checked+" onclick=\"submitUrl('" + urlNoInherit + "',this);\" /><label for=\""+id+NO_INHERIT+"\" >"+paramRequest.getLocaleString("lbl_noInherit")+"</label>");
                }
                out.println("</div></fieldset>");
            }
            out.println("<fieldset>");
            inheritHeader.append("<fieldset>");
            inheritHeader.append("<legend>");
            inheritHeader.append(paramRequest.getLocaleString("legend_Inherited"));
            inheritHeader.append("</legend>");
            out.println("<table width=\"98%\">");
            inheritHeader.append("<table width=\"98%\">");
            out.println("<thead>");
            inheritHeader.append("<thead>");
            out.println("<tr>");
            inheritHeader.append("<tr>");
            out.println("<th>");
            // numcols++;
            out.println(paramRequest.getLocaleString("th_action"));
            out.println("</th>");

            out.println("<th>");
            inheritHeader.append("<th>");
            numcols++;
            out.println(strTitleProp);
            inheritHeader.append(strTitleProp);
            out.println("</th>");
            inheritHeader.append("</th>");
            if (hmprop.get(MetaTagValue.swb_metadataDef) != null) {
                sptemp = (SemanticProperty) hmprop.get(MetaTagValue.swb_metadataDef);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            if (hmprop.get(Localeable.swb_language) != null) {
                sptemp = (SemanticProperty) hmprop.get(Localeable.swb_language);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            if (hmprop.get(Countryable.swb_country) != null) {
                sptemp = (SemanticProperty) hmprop.get(Countryable.swb_country);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }

            if (hmprop.get(Traceable.swb_modifiedBy) != null) {
                sptemp = (SemanticProperty) hmprop.get(Traceable.swb_modifiedBy);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            if (hmprop.get(Template.swb_templateGroup) != null) {
                sptemp = (SemanticProperty) hmprop.get(Template.swb_templateGroup);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            if (hmprop.get(Traceable.swb_created) != null) {
                sptemp = (SemanticProperty) hmprop.get(Traceable.swb_created);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            if (hmprop.get(Traceable.swb_updated) != null) {
                sptemp = (SemanticProperty) hmprop.get(Traceable.swb_updated);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            if (hmprop.get(Inheritable.swb_inherit) != null) {
                sptemp = (SemanticProperty) hmprop.get(Inheritable.swb_inherit);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            if (hmprop.get(Priorityable.swb_priority) != null) {
                sptemp = (SemanticProperty) hmprop.get(Priorityable.swb_priority);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                inheritHeader.append("<th>");
                numcols++;
                out.println(propname);
                inheritHeader.append(propname);
                out.println("</th>");
                inheritHeader.append("</th>");
            }
            boolean hasActive = false;
            if (hmprop.get(Activeable.swb_active) != null) {
                hasActive = true;
                sptemp = (SemanticProperty) hmprop.get(Activeable.swb_active);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                //numcols++;
                out.println(propname);
                out.println("</th>");
            }

            out.println("</tr>");
            inheritHeader.append("</tr>");
            out.println("</thead>");
            inheritHeader.append("</thead>");
            out.println("<tbody>");
            inheritHeader.append("<tbody>");

            boolean isInherit = true;

            log.debug("obj sem class: " + obj.getSemanticClass().getName());

            Iterator<SemanticObject> itso = null;

            if (obj.getSemanticClass().equals(User.swb_User) && !prop.getRangeClass().equals(User.swb_CalendarRef)) {
                if(spref!=null) itso = obj.listObjectProperties(spref);
                else itso = obj.listObjectProperties(prop);
            } else if (obj.getSemanticClass().equals(User.swb_User) && prop.getRangeClass().equals(User.swb_CalendarRef)) {
                itso = obj.listObjectProperties(spro);
            } else {
                itso = obj.listObjectProperties(prop);
            }

            boolean hasAsoc = false;
            while (itso.hasNext()) {
                hasAsoc = true;
                SemanticObject sobj = itso.next();
                
                             
                                
                SemanticClass clsobj = sobj.getSemanticClass();
                //log.debug("Clase: " + clsobj.getName()+" -- "+sobj.getObjectProperty(UserGroupRef.swb_userGroup).getProperty(UserGroup.swb_title));
                //String stitle = getDisplaySemObj(sobj, user.getLanguage());
                if (clsobj.getName().trim().equals("UserGroupRef")) {
                    SemanticObject soref = sobj.getObjectProperty(UserGroupRef.swb_userGroup);
                    if(soref==null) {
                        sobj.remove(); //eliminando el UserGroupRef que no tiene UserGroup 
                        continue;
                    }
                    title = sobj.getObjectProperty(UserGroupRef.swb_userGroup).getProperty(UserGroup.swb_title);
                }

                if(idpref==null) idpref = idp;  //
                SemanticProperty semprop = ont.getSemanticProperty(idpref);
                SemanticProperty sem_p = ont.getSemanticProperty(idpref);
                SemanticObject semobj = null;
                SemanticObject so = sobj.getObjectProperty(semprop);
                semobj = sobj.getObjectProperty(semprop);
                if (semobj == null) {
                    semobj = sobj;
                }

                //System.out.println("Evaluando si está eliminado....");
                boolean deleted = Boolean.FALSE;
                
                GenericObject gobj = semobj.createGenericInstance();
                
                if(gobj instanceof Trashable){
                    
                    Template template = (Template)gobj;
                    //System.out.println("Instance of Template...");
                    
                    deleted = template.isDeleted();;
                    if(deleted) continue;
                } 
                
                
                out.println("<tr>");
                out.println("<td>");
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", id);
                urlr.setParameter("sprop", idp);
                urlr.setParameter("spropref", idpref);
                urlr.setParameter("sval", sobj.getURI());
                urlr.setParameter(prop.getName(), prop.getURI());
                urlr.setAction("remove");
                out.println("<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("confirm_remove") + " " + semobj.getDisplayName(user.getLanguage()).replaceAll("'","") + "?')){submitUrl('" + urlr + "',this);} else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=0></a>");
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

                //out.println("<a href=\"#\"  onclick=\"addNewTab('" + sobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sobj.getDisplayName() + "');return false;\" >" + stitle + "</a>"); //onclick=\"submitUrl('"+urlchoose+"',this); return false;\"
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + semobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sobj.getDisplayName().replaceAll("'", "") + "');return false;\" >" + semobj.getDisplayName(user.getLanguage()).replaceAll("'", "") + "</a>"); //onclick=\"submitUrl('"+urlchoose+"',this); return false;\"
                out.println("</td>");
                if (hmprop.get(MetaTagValue.swb_metadataDef) != null) {
                    semprop = (SemanticProperty) hmprop.get(MetaTagValue.swb_metadataDef);
                    semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    if(semobj==null) semobj = sobj;
                    SemanticObject solang = semobj.getObjectProperty(semprop);
                    out.println(getValueSemProp(solang, Descriptiveable.swb_title));
                    out.println("</td>");
                }
                if (hmprop.get(Localeable.swb_language) != null) {
                    semprop = (SemanticProperty) hmprop.get(Localeable.swb_language);
                    semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    if(semobj==null) semobj = sobj;
                    SemanticObject solang = semobj.getObjectProperty(semprop);
                    out.println(getValueSemProp(solang, Descriptiveable.swb_title));
                    out.println("</td>");
                }
                if (hmprop.get(Countryable.swb_country) != null) {
                    semprop = (SemanticProperty) hmprop.get(Countryable.swb_country);
                    semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    if(semobj==null) semobj = sobj;
                    SemanticObject solang = semobj.getObjectProperty(semprop);
                    out.println(getValueSemProp(solang, Descriptiveable.swb_title));
                    out.println("</td>");
                }
                if (hmprop.get(Traceable.swb_modifiedBy) != null) {
                    semprop = (SemanticProperty) hmprop.get(Traceable.swb_modifiedBy);
                    semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    if (null != so) { //
                        SemanticObject sem_o = so.getObjectProperty(semprop);
                        if (null != sem_o) {
                            log.debug("MODIFIEDBY-------" + sem_o.getURI());
                            out.println("<a href=\"#\"  onclick=\"addNewTab('" + sem_o.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sem_o.getDisplayName() + "');return false;\" >");
                            out.println(sem_o.getProperty(User.swb_usrLogin));
                            out.println("</a>");
                        } else {
                            out.println("-");
                        }
                    //out.println(getValueSemProp(so.getObjectProperty(User.swb_usrLogin), Descriptiveable.swb_title)); //getValueSemProp(semobj.getObjectProperty(semprop), Descriptiveable.swb_title));
                    } else {
                        SemanticObject sem_o = sobj.getObjectProperty(semprop);
                        if (null != sem_o) {
                            log.debug("(else)MODIFIEDBY-------" + sem_o.getURI());
                            out.println("<a href=\"#\"  onclick=\"addNewTab('" + sem_o.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sem_o.getDisplayName() + "');return false;\" >");
                            out.println(sem_o.getProperty(User.swb_usrLogin));
                            out.println("</a>");
                        } else if (so != null) {
                            out.println("-");
                        }
                    }
                    out.println("</td>");
                }
                if (hmprop.get(Template.swb_templateGroup) != null) {
                    semprop = (SemanticProperty) hmprop.get(Template.swb_templateGroup);
                    semobj = sobj.getObjectProperty(spref);
                    out.println("<td>");
                    out.println(getValueSemProp(semobj.getObjectProperty(semprop), Descriptiveable.swb_title));
                    out.println("</td>");
                }
                if (hmprop.get(Traceable.swb_created) != null) {
                    semprop = (SemanticProperty) hmprop.get(Traceable.swb_created);
                    out.println("<td>");
                    if (null != so) {
                        SemanticObject sem_o = so.getObjectProperty(sem_p);
                        if (null != sem_o) {
                            log.debug("created-------" + sem_o.getURI());
                            out.println(getValueSemProp(sem_o, Traceable.swb_created));
                        } else {
                            out.print(getValueSemProp(so, semprop));
                        }
                    } else {
                        out.println(getValueSemProp(sobj, semprop));
                    }
                    out.println("</td>");
                }
                if (hmprop.get(Traceable.swb_updated) != null) {
                    semprop = (SemanticProperty) hmprop.get(Traceable.swb_updated);
                    out.println("<td>");
                    if (null != so) {
                        SemanticObject sem_o = so.getObjectProperty(sem_p);
                        if (null != sem_o) {
                            log.debug("created-------" + sem_o.getURI());
                            out.println(getValueSemProp(sem_o, Traceable.swb_updated));
                        } else {
                            out.print(getValueSemProp(so, semprop));
                        }
                    } else {
                        out.println(getValueSemProp(sobj, semprop));
                    }
                    out.println("</td>");
                }
                if (hmprop.get(Inheritable.swb_inherit) != null) {
                    semprop = (SemanticProperty) hmprop.get(Inheritable.swb_inherit);
                    semobj = sobj.getObjectProperty(spref);
                    DisplayProperty dobj = new DisplayProperty(semprop.getDisplayProperty());
                    String selectValues = dobj.getSelectValues(user.getLanguage());
                    log.debug("selectValues: " + selectValues);
                    out.println("<td allign=\"center\">");
                    if (selectValues != null) {
                        String value = getValueSemProp(sobj, semprop);
                        if (null == value || "Not set".equals(value.trim())) {
                            value = "1";
                        }
                        int ivalue = Integer.parseInt(value);
                        SWBResourceURL urluinh = paramRequest.getActionUrl();
                        //urluinh.setMode(Mode_Action);
                        urluinh.setParameter("suri", id);
                        urluinh.setParameter("sprop", idp);
                        urluinh.setParameter("sval", sobj.getURI());
                        urluinh.setParameter("spropref", idpref);
                        urluinh.setAction("updinherit");
                        out.println("<select  id=\"" + id + "/" + base.getId() + "/" + sobj.getURI() + "/INESO\" name=\"p_inherita\"  dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:120px;\" >"); //submitUrl('" + urluinh + "&p_inherita='+dijit.byId('" + id + "/" + base.getId() + "/" + sobj.getURI() + "/INESO').getValue(),this.domNode);

                        out.println("<script type=\"dojo/connect\" event=\"onChange\">");
                        out.println(" var self1=this;   ");
                        //out.println(" showStatusURL('" + urluinh + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),true);");
                        out.println(" submitUrl('" + urluinh + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),self1.domNode);");
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
                    semprop = (SemanticProperty) hmprop.get(Priorityable.swb_priority);
                    out.println("<td align=\"center\">");
                    numcols++;
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idpref);
                    urlu.setAction("update");

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
                    //out.println(" showStatusURL('" + urlu + "&'+self.attr(\"name\")+'='+self.attr(\"value\"),true);");
                    out.println(" submitUrl('" + urlu + "&'+self.attr(\"name\")+'='+self.attr(\"value\"),self.domNode);");
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
                    numcols++;
                    boolean activo = false;
                    if (sobj.getBooleanProperty(Activeable.swb_active)) {
                        activo = true;
                        isInherit = false;
                    }
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idpref);
                    urlu.setAction("updstatus");

                    out.println("<input name=\"" + prop.getName() + sobj.getURI() + "\" type=\"checkbox\" value=\"1\" id=\"" + prop.getName() + sobj.getURI() + "\" onclick=\"submitUrl('" + urlu + "&val='+this.checked,this);\"  " + (activo ? "checked='checked'" : "") + "/>");
                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            if(base.getAttribute(USE_ADD,"0").equals("1"))
            {
                String urlAddNew = SWBPlatform.getContextPath()+"/swbadmin/jsp/SemObjectEditor.jsp";
                urlAddNew+="?scls="+spro.getRangeClass().getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+spro.getEncodedURI()+"&reloadTab=true";

                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"showDialog('" + urlAddNew + "','"+paramRequest.getLocaleString("btn_addnewelement")+" "+spro.getRangeClass().getDisplayName(user.getLanguage())+"'); return false;\">" + paramRequest.getLocaleString("btn_addnewelement") + "</button>");
            }
            if(base.getAttribute(USE_SELECT,"0").equals("1"))
            {
                SWBResourceURL urlNew = paramRequest.getRenderUrl();
                urlNew.setParameter("suri", id);
                urlNew.setParameter("sprop", idp);
                urlNew.setParameter("spropref", idpref);
                urlNew.setParameter("act", "choose");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlNew + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btn_addnew") + "</button>");
            }
            if (hasAsoc) {
                if (hasActive) {
                    SWBResourceURL urlAAll = paramRequest.getActionUrl();
                    urlAAll.setParameter("suri", id);
                    urlAAll.setParameter("sprop", idp);
                    urlAAll.setParameter("spropref", idpref);
                    urlAAll.setParameter("sval", "true");
                    urlAAll.setAction("activeall");
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btn_aallnew") + "</button>");
                    SWBResourceURL urlUAll = paramRequest.getActionUrl();
                    urlUAll.setParameter("suri", id);
                    urlUAll.setParameter("sprop", idp);
                    urlUAll.setParameter("spropref", idpref);
                    urlUAll.setParameter("sval", "false");
                    urlUAll.setAction("unactiveall");
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlUAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btn_uallnew") + "</button>");
                }
                SWBResourceURL urlDAll = paramRequest.getActionUrl();
                urlDAll.setParameter("suri", id);
                urlDAll.setParameter("sprop", idp);
                urlDAll.setParameter("spropref", idpref);
                urlDAll.setParameter("sval", "remove");
                urlDAll.setParameter(prop.getName(), prop.getURI());
                urlDAll.setAction("deleteall");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgQremoveAll")+"?')){submitUrl('" + urlDAll + "',this.domNode);} return false;\">" + paramRequest.getLocaleString("btn_dallnew") + "</button>");
            }
            //out.println("<a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">Add New</a>");
            out.println("</fieldset>");


            // Para mostrar heredados

            log.debug("PRO: " + spro.getName() + " es inheritable " + spro.isInheritProperty());
            //log.debug("PROREF: " + spref.getName() + " es inheritable " + spref.isInheritProperty());

            if (isInherit && spro.isInheritProperty()) {
                itso = null;
                if (obj.getSemanticClass().equals(User.swb_User)) {
                    itso = obj.listInheritProperties(spref);
                } else {
                    itso = obj.listInheritProperties(spro);
                }

                if (itso.hasNext()) {
                    out.println(inheritHeader.toString());
                    while (itso.hasNext()) {
                        SemanticObject sobj = itso.next();
                        
                        
                        
                        

                        
                        SemanticClass clsobj = sobj.getSemanticClass();
                        log.debug("Clase:" + clsobj.getName() + ", SO: " + sobj.getDisplayName(user.getLanguage()));
                        String stitle = getDisplaySemObj(sobj, user.getLanguage());

//                        out.println("<tr>");
//                        out.println("<td>");

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
                        SemanticProperty semprop = ont.getSemanticProperty(idpref);
                        SemanticProperty sem_p = ont.getSemanticProperty(idpref);
                        SemanticObject semobj = null;
                        SemanticObject so = sobj.getObjectProperty(semprop);
                        semobj = sobj.getObjectProperty(semprop);
                        if (semobj == null) {
                            semobj = sobj;
                        }
                        
                        boolean deleted = Boolean.FALSE;
                
                        GenericObject gobj = semobj.createGenericInstance();

                        if(gobj instanceof Trashable){

                            Template template = (Template)gobj;
                            //System.out.println("Instance of Template...");

                            deleted = template.isDeleted();;
                            if(deleted) continue;
                        } 
                        
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<a href=\"#\"  onclick=\"addNewTab('" + semobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sobj.getDisplayName() + "');return false;\" >" + stitle + "</a>"); //onclick=\"submitUrl('"+urlchoose+"',this); return false;\"
                        out.println("</td>");
                        if (hmprop.get(Template.swb_language) != null) {
                            semprop = (SemanticProperty) hmprop.get(Template.swb_language);
                            semobj = sobj.getObjectProperty(spref);
                            out.println("<td>");
                            out.println(getValueSemProp(semobj.getObjectProperty(semprop), Descriptiveable.swb_title));
                            out.println("</td>");
                        }
                        if (hmprop.get(Traceable.swb_modifiedBy) != null) {
                            semprop = (SemanticProperty) hmprop.get(Traceable.swb_modifiedBy);
                            semobj = sobj.getObjectProperty(spref);
                            out.println("<td>");
                            if (null != so) { //
                                SemanticObject sem_o = so.getObjectProperty(semprop);
                                if (null != sem_o) {
                                    log.debug("MODIFIEDBY-------" + sem_o.getURI());
                                    out.println("<a href=\"#\"  onclick=\"addNewTab('" + sem_o.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sem_o.getDisplayName() + "');return false;\" >");
                                    out.println(sem_o.getProperty(User.swb_usrLogin));
                                    out.println("</a>");
                                } else {
                                    out.print(so.getProperty(semprop));
                                }
                            } else {
                                SemanticObject sem_o = sobj.getObjectProperty(semprop);
                                if (null != sem_o) {
                                    log.debug("(else)MODIFIEDBY-------" + sem_o.getURI());
                                    out.println("<a href=\"#\"  onclick=\"addNewTab('" + sem_o.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + sem_o.getDisplayName() + "');return false;\" >");
                                    out.println(sem_o.getProperty(User.swb_usrLogin));
                                    out.println("</a>");
                                } else if (so != null) {
                                    out.print(so.getProperty(semprop));
                                }

                            }
                            out.println("</td>");
                        }
                        if (hmprop.get(Template.swb_templateGroup) != null) {
                            semprop = (SemanticProperty) hmprop.get(Template.swb_templateGroup);
                            semobj = sobj.getObjectProperty(spref);
                            out.println("<td>");
                            out.println(getValueSemProp(semobj.getObjectProperty(semprop), Descriptiveable.swb_title));
                            out.println("</td>");
                        }
                        if (hmprop.get(Traceable.swb_created) != null) {
                            semprop = (SemanticProperty) hmprop.get(Traceable.swb_created);
                            out.println("<td>");
                            if (null != so) {
                                SemanticObject sem_o = so.getObjectProperty(sem_p);
                                if (null != sem_o) {
                                    log.debug("created-------" + sem_o.getURI());
                                    out.println(getValueSemProp(sem_o, Traceable.swb_created));
                                } else {
                                    out.print(getValueSemProp(so, semprop));
                                }
                            } else {
                                out.println(getValueSemProp(sobj, semprop));
                            }
                            out.println("</td>");
                        }
                        if (hmprop.get(Traceable.swb_updated) != null) {
                            semprop = (SemanticProperty) hmprop.get(Traceable.swb_updated);
                            out.println("<td>");
                            if (null != so) {
                                SemanticObject sem_o = so.getObjectProperty(sem_p);
                                if (null != sem_o) {
                                    log.debug("created-------" + sem_o.getURI());
                                    out.println(getValueSemProp(sem_o, Traceable.swb_updated));
                                } else {
                                    out.print(getValueSemProp(so, semprop));
                                }
                            } else {
                                out.println(getValueSemProp(sobj, semprop));
                            }
                            out.println("</td>");
                        }
                        if (hmprop.get(Inheritable.swb_inherit) != null) {
                            semprop = (SemanticProperty) hmprop.get(Inheritable.swb_inherit);
                            semobj = sobj.getObjectProperty(spref);
                            DisplayProperty dobj = new DisplayProperty(semprop.getDisplayProperty());
                            String selectValues = dobj.getSelectValues(user.getLanguage());
                            log.debug("selectValues: " + selectValues);
                            out.println("<td allign=\"center\">");
                            if (selectValues != null) {
                                String value = getValueSemProp(sobj, semprop);
                                if (null == value || "Not set".equals(value.trim())) {
                                    value = "1";
                                }
                                int ivalue = Integer.parseInt(value);
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
                                    if (ival == ivalue) {
                                        out.println(val);
                                    }
                                }
                            } else {
                                out.println(getValueSemProp(semobj, semprop));
                            }
                            out.println("</td>");
                        }
                        if (hmprop.get(Priorityable.swb_priority) != null) {
                            semprop = (SemanticProperty) hmprop.get(Priorityable.swb_priority);
                            out.println("<td align=\"center\">");
                            String val = getValueSemProp(sobj, semprop).trim();
                            if ("1".equals(val)) {
                                out.println(paramRequest.getLocaleString("defecto"));
                            } else if ("2".equals(val)) {
                                out.println(paramRequest.getLocaleString("low"));
                            } else if ("3".equals(val)) {
                                out.println(paramRequest.getLocaleString("media"));
                            } else if ("4".equals(val)) {
                                out.println(paramRequest.getLocaleString("high"));
                            } else if ("5".equals(val)) {
                                out.println(paramRequest.getLocaleString("priority"));
                            }
                            out.println("</td>");
                        }
                        out.println("</tr>");
                    }
                    out.println("</table>");
                    out.println("</fieldset>");
                }
            }
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
            else propref = prop;

            SemanticClass clsprop = propref.getRangeClass();
            title = clsprop.getName();
            log.debug("Titulo de la clase ref: " + title);

            HashMap hmSO = new HashMap();
            Iterator<SemanticObject> ite_so = obj.listObjectProperties(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp));
            while (ite_so.hasNext()) {
                SemanticObject so = ite_so.next();
                if (null != so) {
                    log.debug("--prop--so--" + so.getURI());
                    if (so.createGenericInstance() instanceof UserGroup || idp.endsWith("hasProcessActivity") || idp.endsWith("hasAdminFilter")) {
                        hmSO.put(so.getURI(), so);
                    } else {
                        SemanticObject soref = so.getObjectProperty(propref);
                        if (null != soref) {
                            log.debug("--propref--so--" + soref.getURI());
                            hmSO.put(soref.getURI(), soref);
                        }
                    }
                }
            }

            SemanticObject obusrRep = null;
            if (clsprop.equals(Role.swb_Role) || clsprop.equals(User.swb_UserGroup)) { //||clsprop.equals(UserGroup.swb_UserGroupable)
                GenericObject go = obj.getModel().getModelObject().createGenericInstance();
                if (go instanceof WebSite) {
                    obusrRep = ((WebSite) go).getUserRepository().getSemanticObject();
                } else {
                    obusrRep = go.getSemanticObject();
                }
            }

            if (obj.getSemanticClass().equals(User.swb_User) && propref.getRangeClass().equals(Role.swb_Role)) {
                ite_so = obj.listObjectProperties(propref);
                while (ite_so.hasNext()) {
                    SemanticObject so = ite_so.next();
                    if (null != so) {
                        hmSO.put(so.getURI(), so);
                    }
                }
            }

            if (obusrRep != null) {
                obj = obusrRep;
            }

            //:hasUserGroupRef&spropref=swb:userGroup

            log.debug("choose:" + clsprop.getName() + ", " + idp);

            SWBResourceURL urladd = paramRequest.getActionUrl();
            if ((idp.endsWith("hasRole") && clsprop.equals(Role.swb_Role)) || (idp.endsWith("hasUserGroup") && clsprop.equals(UserGroup.swb_UserGroup))|| (idp.endsWith("hasProcessActivity")) || (idp.endsWith("hasAdminFilter")) || (idp.endsWith("hasOntology"))) {
                urladd.setAction("choose");
            } else {
                urladd.setAction("new");
            }
            out.println("<div class=\"swbform\">");
            out.println("<form id=\"" + id + "/chooseSO\" name=\"" + id + "/chooseSO\" action=\"" + urladd + "\" method=\"post\"  onsubmit=\"if(validateChk('sobj','" + paramRequest.getLocaleString("alertMSG") + "')) submitForm('" + id + "/chooseSO'); return false;\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">"); //obj.getURI()
            out.println("<input type=\"hidden\" name=\"sprop\" value=\"" + idp + "\">");
            out.println("<input type=\"hidden\" name=\"spropref\" value=\"" + idpref + "\">");
            out.println("<fieldset>");
            out.println("<table width=\"98%\">");

            out.println("<theader>");
            out.println("<tr>");
            out.println("<th>");
            out.println(paramRequest.getLocaleString("th_title"));
            out.println("</th>");
            out.println("<th>");
            out.println(paramRequest.getLocaleString("th_select"));
            out.println("</th>");
            //if (clsprop.equals(Role.swb_Role) || clsprop.equals(Rule.swb_Rule) || clsprop.equals(UserGroup.swb_UserGroup) || clsprop.equals(Calendar.swb_Calendar)) {
            out.println("<th>");
            out.println(paramRequest.getLocaleString("th_check"));
            out.println("</th>");
            //}
            out.println("</tr>");
            out.println("</theader>");
            log.debug("Clase: " + clsprop.getName());
            Iterator<SemanticObject> itso = null;

            HashMap<String, SemanticObject> hmtmp = new HashMap();
            if (prop.equals(User.swb_hasCalendarRef) && obj.getSemanticClass().equals(User.swb_User)) {
                GenericObject go = obj.getModel().getModelObject().createGenericInstance();
                log.debug("UserREP(" + (go instanceof UserRepository ? "true" : "false") + ")");
                if (go instanceof UserRepository) {
                    UserRepository urep = (UserRepository) go;
                    log.debug("UREP URI: " + urep.getURI());
                    Iterator<WebSite> itws = SWBContext.listWebSites();
                    log.debug("........." + itws.hasNext());
                    while (itws.hasNext()) {
                        WebSite wsso = itws.next();
                        log.debug("buscando calendarios en:" + wsso.getDisplayTitle(user.getLanguage()));
                        if (wsso.getUserRepository().equals(urep) && wsso.isActive() && !wsso.isDeleted()) {
                            Iterator<Calendar> itc = wsso.listCalendars();
                            while (itc.hasNext()) {
                                Calendar calso = itc.next();
                                log.debug("Calendario:" + calso.getDisplayTitle(user.getLanguage()));
                                hmtmp.put(calso.getURI(), calso.getSemanticObject());
                            }
                        }
                    }
                    itso = hmtmp.values().iterator();
                }
            } else {
                itso = obj.getModel().listInstancesOfClass(clsprop);
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////

            if(base.getAttribute(USE_GLOBAL, "0").equals("1"))
            {
                HashMap hmso = new HashMap();
                Iterator<Ontology> itont = SWBComparator.sortSemanticObjects(user.getLanguage(), SWBContext.listOntologies());
                while(itont.hasNext())
                {
                    Ontology ontology = itont.next();

                    hmso.put(ontology.getSemanticObject(),ontology.getSemanticObject());
                    itso = hmso.values().iterator();
                }
            }

            /////////////////////////////////////////////////////

            int numrols = 0;
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                String stitle = getDisplaySemObj(sobj, user.getLanguage());

                boolean deleted = Boolean.FALSE;
                if(sobj.instanceOf(Trashable.swb_Trashable)){
                    deleted = sobj.getBooleanProperty(Trashable.swb_deleted);
                }
                
                if(!deleted)
                {
                    log.debug(sobj.getURI() + "choose");
                    if (hmSO.get(sobj.getURI()) == null) {
                        numrols++;
                        out.println("<tr>");
                        out.println("<td>" + stitle + "</td> ");
                        SWBResourceURL urlchoose = paramRequest.getActionUrl();
                        if ((idp.endsWith("hasRole") && clsprop.equals(Role.swb_Role)) || (idp.endsWith("hasUserGroup") && clsprop.equals(UserGroup.swb_UserGroup)) || (idp.endsWith("hasProcessActivity"))|| (idp.endsWith("hasAdminFilter"))|| (idp.endsWith("hasOntology"))) {
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
                        //if (clsprop.equals(Role.swb_Role) || clsprop.equals(Rule.swb_Rule) || clsprop.equals(UserGroup.swb_UserGroup) || clsprop.equals(Calendar.swb_Calendar)) {
                        out.println("<td>");
                        out.println("<input type=\"checkbox\" value=\"" + sobj.getURI() + "\" name=\"sobj\">");
                        out.println("</td>");
                        //}
                        out.println("</tr>");
                    }
                }
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
            //if ((clsprop.equals(Role.swb_Role) || clsprop.equals(Rule.swb_Rule) || clsprop.equals(UserGroup.swb_UserGroup) || clsprop.equals(Calendar.swb_Calendar)) && numrols > 0) {
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\"  >" + paramRequest.getLocaleString("Add_Selected") + "</button>"); //submitUrl('" + url + "',this); onclick=\"return false;\"  onclick=\"document.getElementById('" + id + "/chooseSO').submit();\" _onclick=\"if(validateChk('sobj')){document.getElementById('" + id + "/chooseSO').submit(); return false;} else {return false;}\"
            out.println("<button dojoType=\"dijit.form.Button\" type=\"button\"  onclick=\"selectAll('sobj',true);\">" + paramRequest.getLocaleString("Select_All") + "</button>"); //submitUrl('" + url + "',this);
            out.println("<button dojoType=\"dijit.form.Button\" type=\"button\"  onclick=\"selectAll('sobj',false);\">" + paramRequest.getLocaleString("Unselect_All") + "</button>"); //submitUrl('" + url + "',this);
            //}
            if (id != null && idp != null && idpref != null) {
                out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitUrl('" + urlBack + "',document.getElementById('" + id + "/chooseSO')); return false;\" >" + paramRequest.getLocaleString("btn_back") + "</button>");
            }
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
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

        base = getResourceBase();

        SemanticObject so = null;
        log.debug("ProcessAction() ---------------- (" + action + ")");
        log.debug("suri:     " + id);
        log.debug("sprop:    " + sprop);
        log.debug("spropref: " + spropref);
        log.debug("sval:     " + sval);

        if ("new".equals(action)) {

            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            SemanticClass ncls = prop.getRangeClass();
            String id_usr_request = request.getParameter("id_usr_request");
            String[] valores = request.getParameterValues("sobj");
            log.debug("(new): " + ncls.getName());
            log.debug("(sobj): " + valores + " --- " + valores.length);

            if (ncls.isAutogenId() || id_usr_request != null) {
                String elementName = "";
                for (int i = 0; i < valores.length; i++) {
                    if (id_usr_request == null) {
                        id_usr_request = "";
                    }
                    long lid = obj.getModel().getCounter(ncls);
                    String str_lid = id_usr_request + lid;
                    SemanticObject nobj = obj.getModel().createSemanticObject(obj.getModel().getObjectUri(str_lid, ncls), ncls);
                    if (prop.getName().startsWith("has")) {
                        //System.out.println("'has' property..." + prop.getName());
                        obj.addObjectProperty(prop, nobj);
                    } else {
                        log.debug("'no has' property..." + prop.getName());
                        obj.setObjectProperty(prop, nobj);
                    }
                    if (prop.getName().endsWith("Ref") && spropref != null) {
                        //System.out.println("'Ref' property..." + prop.getName());
                        String soid = valores[i]; //request.getParameter("sobj");  // id de Template seleccionado, según sea el tipo de SO
                        SemanticObject soref = null;
                        if (soid != null && soid.trim().length() > 0) {
                            soref = ont.getSemanticObject(soid);
                        }
                        SemanticProperty spref = ont.getSemanticProperty(spropref);
                        nobj.setObjectProperty(spref, soref);
                        elementName = spref.getName(); //nobj.getDisplayName(user.getLanguage());

                        Iterator<SemanticProperty> itsp = nobj.getSemanticClass().listProperties();
                        while (itsp.hasNext()) {
                            SemanticProperty sp = itsp.next();
                            if (sp.equals(Priorityable.swb_priority)) {
                                nobj.setLongProperty(sp, 3);
                                break;
                            }
                        }
                    }
                }
                response.setMode(response.Mode_EDIT);
                if (id != null) {
                    response.setRenderParameter("suri", id);
                }
//                if (nobj != null) {
//                    response.setRenderParameter("nsuri", nobj.getURI());
//                }
                if (rid != null) {
                    response.setRenderParameter("rsuri", rid);
                }
                if (sprop != null) {
                    response.setRenderParameter("sprop", sprop);
                }
                if (spropref != null) {
                    response.setRenderParameter("spropref", spropref);
                }


                response.setRenderParameter("statmsg", response.getLocaleString("statmsg1") + " " + elementName);
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
            String name = "";
            SemanticProperty sp = null;
            if(spropref!=null) sp = ont.getSemanticProperty(spropref);
            SemanticProperty spref = ont.getSemanticProperty(sprop);
            if (soc.getSemanticClass().equals(User.swb_User) && spref.getRangeClass().equals(User.swb_CalendarRef)) {
                sp = spref;
            }
            name = sp.getName();
            if (soc.getSemanticClass().equals(User.swb_User)) {

                SemanticObject soval = ont.getSemanticObject(sval);
                soc.removeObjectProperty(sp, soval);

            } else {
                if(base.getAttribute(DEL_SO, "0").equals("0"))
                {
                    SemanticObject sobj = ont.getSemanticObject(sval);
                    sobj.remove();
                }
                else
                {
                    SemanticProperty spdel = ont.getSemanticProperty(sprop);

                    SemanticObject soval = ont.getSemanticObject(sval);
                    soc.removeObjectProperty(spdel, soval);
                    
                }
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
            log.debug("remove-closetab:" + sval);
            response.setRenderParameter("closetab", sval);
            response.setRenderParameter("statmsg", response.getLocaleString("statmsg3") + " " + name + ".");
            response.setMode(response.Mode_EDIT);

        } else if ("choose".equals(action)) //suri, prop
        {
            log.debug("(choose)");
            sprop = request.getParameter("sprop");
            spropref = request.getParameter("spropref");
            String[] valores = request.getParameterValues("sobj");

            String sobj = request.getParameter("sobj");
            sval = null;
            if (sobj == null) {
                sval = SWBUtils.TEXT.decode(request.getParameter("sval"), "UTF-8");
            }
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            SemanticProperty propref = null;
            if(spropref!=null)propref = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(spropref);
            String pname = prop.getName();
            log.debug("Property Name:" + pname);
            if (!pname.startsWith("has")) {
                //System.out.println("Property Name:" + pname);
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
                    for (int i = 0; i < valores.length; i++) {
                        sobj = valores[i];
                        log.debug("valor: " + sobj);
                        SemanticObject aux = ont.getSemanticObject(sobj);
                        if (sobj != null) {
                            obj.setObjectProperty(prop, aux); //actualizando el objectProperty a una instancia existente
                        } else {
                            obj.removeProperty(prop);
                        }
                    }
                }
            } else if(spropref!=null && spropref.endsWith("Ref")){   // empieza propiedad con has
                if (sobj != null && obj != null) {
                    for (int i = 0; i < valores.length; i++) {
                        sobj = valores[i];
                        log.debug("Agregando un " + propref.getName() + " a " + obj.getURI() + " ---SOBJ--- " + sobj);
                        if (sobj != null) {
                            SemanticObject aux = ont.getSemanticObject(sobj.trim()); //agregando al objectProperty nueva instancia
                            log.debug(aux.getURI() + "---" + propref.getRangeClass().getName());
                            if (aux != null) {
                                obj.addObjectProperty(propref, aux);
                            }
                        }
                    }
                }
            } else {   // empieza propiedad con has
                if (sobj != null && obj != null) {
                    for (int i = 0; i < valores.length; i++) {
                        sobj = valores[i];
                        log.debug("Agregando un " + prop.getName() + " a " + obj.getURI() + " ---SOBJ--- " + sobj);
                        if (sobj != null) {
                            SemanticObject aux = ont.getSemanticObject(sobj.trim()); //agregando al objectProperty nueva instancia
                            log.debug(aux.getURI() + "---" + prop.getRangeClass().getName());
                            if (aux != null) {
                                obj.addObjectProperty(prop, aux);
                            }
                        }
                    }
                }
            }
            if(propref!=null) response.setRenderParameter("statmsg", response.getLocaleString("statmsg1") + " " + propref.getName());
            if(spropref!=null)  response.setRenderParameter("spropref", spropref);
            response.setRenderParameter("sprop", sprop);
            response.setRenderParameter("sobj", sobj);
            if (id != null) {
                response.setRenderParameter("suri", id);
            }
            if (rid != null) {
                response.setRenderParameter("rsuri", rid);
            }

        } else if ("update".equals(action)) {
            try {
                if (sval != null) {
                    obj = ont.getSemanticObject(sval);
                }
                cls = obj.getSemanticClass();
                Iterator<SemanticProperty> it = cls.listProperties();
                while (it.hasNext()) {
                    SemanticProperty prop = it.next();
                    if (prop.isDataTypeProperty()) {
                        String value = request.getParameter(prop.getName());
                        log.debug("processAction(update): " + prop.getName() + " -- >" + value);
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
                response.setRenderParameter("statmsg", response.getLocaleString("statmsg2"));
            } catch (Exception e) {
                log.error(e);
                response.setRenderParameter("statmsg", response.getLocaleString("statERRORmsg1"));
            }
            response.setRenderParameter("spropref", spropref);
            response.setRenderParameter("sprop", sprop);
            response.setRenderParameter("so", so.getURI());
            if (id != null) {
                response.setRenderParameter("suri", id);
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
                so = sobj;
                response.setRenderParameter("statmsg", (value.equals("true") ? response.getLocaleString("statmsg4") : response.getLocaleString("statmsg5")));
            } catch (Exception e) {
                log.error(e);
                response.setRenderParameter("statmsg", (value.equals("true") ? response.getLocaleString("statERRORmsg2") : response.getLocaleString("statERRORmsg3")));
            }
            response.setRenderParameter("spropref", spropref);
            response.setRenderParameter("sprop", sprop);
            response.setRenderParameter("so", so.getURI());
            if (id != null) {
                response.setRenderParameter("suri", id);
            }
        } else if ("updinherit".equals(action)) {
            String soid = request.getParameter("sval");
            String value = request.getParameter("p_inherita");
            try {
                if (value == null) {
                    value = "1";
                }
                SemanticObject sobj = ont.getSemanticObject(soid);
                sobj.setLongProperty(Inheritable.swb_inherit, Long.parseLong(value));

                SemanticClass scls = sobj.getSemanticClass();
                log.debug("doAction(updinherit):" + scls.getClassName() + ", value:" + value);

                so = sobj;
                response.setRenderParameter("statmsg", response.getLocaleString("statmsg6"));
            } catch (Exception e) {
                log.error(e);
                response.setRenderParameter("statmsg", response.getLocaleString("statERRORmsg4"));
            }
            response.setRenderParameter("spropref", spropref);
            response.setRenderParameter("sprop", sprop);
            response.setRenderParameter("so", so.getURI());
            if (id != null) {
                response.setRenderParameter("suri", id);
            }
        } else if ("activeall".equals(action) || "unactiveall".equals(action)) {

            //GenericObject gobj = ont.getGenericObject(id);
            SemanticProperty spro = ont.getSemanticProperty(sprop);
            SemanticProperty spref = ont.getSemanticProperty(spropref);
            Iterator<SemanticObject> itso = null;
            if (obj.getSemanticClass().equals(User.swb_User) && !spro.getRangeClass().equals(User.swb_CalendarRef)) {
                itso = obj.listObjectProperties(spref);
            } else if (obj.getSemanticClass().equals(User.swb_User) && spro.getRangeClass().equals(User.swb_CalendarRef)) {
                itso = obj.listObjectProperties(spro);
            } else {
                itso = obj.listObjectProperties(spro);
            }
            String value = request.getParameter("sval");
            boolean bstat = false;
            if (value != null && "true".equals(value)) {
                bstat = true;
            }
            log.debug("value:" + value + ", boolean bstat:" + bstat);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();

                try {

                    if (bstat) {
                        sobj.setBooleanProperty(Activeable.swb_active, true);
                    } else {
                        sobj.removeProperty(Activeable.swb_active);
                    }

                    SemanticClass scls = sobj.getSemanticClass();
                    log.debug("doAction(activeall/unactiveall):" + scls.getClassName() + ": " + value);
                    so = sobj;
                    response.setRenderParameter("statmsg", (bstat ? response.getLocaleString("statmsg4") : response.getLocaleString("statmsg5")));
                } catch (Exception e) {
                    log.error(e);
                    response.setRenderParameter("statmsg", (bstat ? response.getLocaleString("statERRORmsg2") : response.getLocaleString("statERRORmsg3")));
                }
                response.setRenderParameter("spropref", spropref);
                response.setRenderParameter("sprop", sprop);
                response.setRenderParameter("so", so.getURI());
                if (id != null) {
                    response.setRenderParameter("suri", id);
                }
            }
        } else if ("deleteall".equals(action)) {

            log.debug("PA(deleteall)================================");

            SemanticObject soc = ont.getSemanticObject(id);
            SemanticProperty sp = ont.getSemanticProperty(sprop);
            SemanticProperty spref = ont.getSemanticProperty(spropref);
            String name = "";

            if (soc.getSemanticClass().equals(User.swb_User) && spref.getRangeClass().equals(User.swb_CalendarRef)) {
                sp = spref;
            }

            name = sp.getName();
            String title = cls.getName();
            log.debug("class name: " + title);
            Iterator<SemanticObject> itso = null;

            if (obj.getSemanticClass().equals(User.swb_User) && !sp.getRangeClass().equals(User.swb_CalendarRef)) {
                itso = obj.listObjectProperties(spref);
            } else if (obj.getSemanticClass().equals(User.swb_User) && sp.getRangeClass().equals(User.swb_CalendarRef)) {
                itso = obj.listObjectProperties(sp);
            } else {
                itso = obj.listObjectProperties(sp);
            }

            /////////////////////////////

            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                if (obj.getSemanticClass().equals(User.swb_User)||(obj.getSemanticClass().equals(WebSite.swb_WebSite)&&sp.getName().endsWith("hasOntology"))) {
                    obj.removeObjectProperty(sp, sobj);
                } else {
                    sobj.remove();
                }
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
            log.debug("remove-closetab:" + sval);
            response.setRenderParameter("closetab", sval);
            response.setRenderParameter("statmsg", response.getLocaleString("statmsg3") + " " + name + ".");
            response.setMode(response.Mode_EDIT);

        } else if(AND_EVAL.equals(action)){
            
            String prop_uri = null;
            Enumeration<String> parametros = request.getParameterNames();
           while(parametros.hasMoreElements()){
               String paramName = parametros.nextElement();
               if(paramName.startsWith(AND_EVAL)){
                   prop_uri = request.getParameter(paramName);
                   break;
               }
           }
           if(prop_uri!=null){
                SemanticProperty prop = ont.getSemanticProperty(prop_uri);
                String booVal = getValueSemProp(obj, prop);
                if(booVal!=null&&"true".equals(booVal)){
                    obj.removeProperty(prop);
                    response.setRenderParameter("statmsg", response.getLocaleString("statmsg8"));
                } else {
                    obj.setBooleanProperty(prop, true);
                    response.setRenderParameter("statmsg", response.getLocaleString("statmsg7"));
                }
           } else {
            response.setRenderParameter("statmsg", response.getLocaleString("statmsg9"));
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
            
        }else if(NO_INHERIT.equals(action)){
            
            String prop_uri = null;
            Enumeration<String> parametros = request.getParameterNames();
           while(parametros.hasMoreElements()){
               String paramName = parametros.nextElement();
               if(paramName.startsWith(NO_INHERIT)){
                   prop_uri = request.getParameter(paramName);
                   break;
               }
           }
           if(prop_uri!=null){
                SemanticProperty prop = ont.getSemanticProperty(prop_uri);
                String booVal = getValueSemProp(obj, prop);
                if(booVal!=null&&"true".equals(booVal)){
                    obj.removeProperty(prop);
                    response.setRenderParameter("statmsg", response.getLocaleString("statmsg8"));
                } else {
                    obj.setBooleanProperty(prop, true);
                    response.setRenderParameter("statmsg", response.getLocaleString("statmsg7"));
                }
           } else {
            response.setRenderParameter("statmsg", response.getLocaleString("statmsg9"));
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
            
        }
    }

    /**
     * Gets the date format.
     * 
     * @param dateTime the date time
     * @param lang the lang
     * @return the date format
     * @return
     */
    public String getDateFormat(long dateTime, String lang) {
        if (null == lang) {
            lang = "es";
        }
        Locale local = new Locale(lang);
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, local);
        return df.format(new Date(dateTime));
    }

    /**
     * Gets the display sem obj.
     * 
     * @param obj the obj
     * @param lang the lang
     * @return the display sem obj
     * @return
     */
    public String getDisplaySemObj(SemanticObject obj, String lang) {
        String ret = obj.getRDFName();
        try {
            ret = obj.getDisplayName(lang);
        } catch (Exception e) {
            ret = obj.getProperty(Descriptiveable.swb_title);
        }
        return ret;
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

    /**
     * Do form id.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
        out.println("<form id=\"" + id + "/getIdItem\" action=\"" + urla + "\" method=\"post\">");
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
        out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitForm('" + id + "/getIdItem');return false;\" >Guardar</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
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

    /**
     * Do action.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAction(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doAction()");
//        User user = paramRequest.getUser();
//        PrintWriter out = response.getWriter();
//        String id = request.getParameter("suri");
//        //String rid = request.getParameter("rsuri");
//        String sprop = request.getParameter("sprop");
//        //String sproptype = request.getParameter("sproptype");
//        String spropref = request.getParameter("spropref");
//        String sval = request.getParameter("sval");
//        String action = request.getParameter("act");
//        String errormsg = "", actmsg = "";
//        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
//        SemanticObject obj = ont.getSemanticObject(id); //WebPage
//        SemanticClass cls = obj.getSemanticClass();
//
//        SemanticObject so = null;
//
//        log.debug("ProcessAction() ---------------- (" + action + ")");
//        log.debug("suri:     " + id);
//        log.debug("sprop:    " + sprop);
//        log.debug("spropref: " + spropref);
//        log.debug("sval:     " + sval);

//        if ("update".equals(action)) {
//            try {
//                if (sval != null) {
//                    obj = ont.getSemanticObject(sval);
//                }
//                cls = obj.getSemanticClass();
//                Iterator<SemanticProperty> it = cls.listProperties();
//                while (it.hasNext()) {
//                    SemanticProperty prop = it.next();
//                    if (prop.isDataTypeProperty()) {
//                        String value = request.getParameter(prop.getName());
//                        log.debug("doAction(update): " + prop.getName() + " -- >" + value);
//                        if (value != null) {
//                            if (value.length() > 0) {
//                                if (prop.isBoolean()) {
//                                    if (value.equals("true") || value.equals("1")) {
//                                        obj.setBooleanProperty(prop, true);
//                                    } else if (value.equals("false") || value.equals("0")) {
//                                        obj.setBooleanProperty(prop, false);
//                                    }
//                                }
//                                if (prop.isInt()) {
//                                    obj.setLongProperty(prop, Integer.parseInt(value));
//                                }
//                                if (prop.isString()) {
//                                    obj.setProperty(prop, value);
//                                }
//                                if (prop.isFloat()) {
//                                    obj.setFloatProperty(prop, Float.parseFloat(value));
//                                }
//                            } else {
//                                obj.removeProperty(prop);
//                            }
//                        }
//                    //else if(prop.isBoolean()) obj.setBooleanProperty(prop, false);
//                    }
//                }
//                so = obj;
//                actmsg = "Se actualiz&oacute; correctamente la prioridad del elemento.";
//            } catch (Exception e) {
//                log.error(e);
//                errormsg = "Error al actualizar la prioridad del elemento.";
//            }
//        } else if ("updstatus".equals(action)) {
//            String soid = request.getParameter("sval");
//            String value = request.getParameter("val");
//            try {
//                if (value == null) {
//                    value = "0";
//                }
//                SemanticObject sobj = ont.getSemanticObject(soid);
//                sobj.setBooleanProperty(Activeable.swb_active, value.equals("true") ? true : false);
//                SemanticClass scls = sobj.getSemanticClass();
//                log.debug("doAction(updstatus):" + scls.getClassName() + ": " + value);
//                so = sobj;
//                actmsg = "Se " + (value.equals("true") ? "activ&oacute;" : "desactiv&oacute;") + " el elemento.";
//            } catch (Exception e) {
//                log.error(e);
//                errormsg = "Error al " + (value.equals("true") ? "activar" : "desactivar") + " el elemento.";
//            }
//        } else if ("updinherit".equals(action)) {
//            String soid = request.getParameter("sval");
//            String value = request.getParameter("p_inherita");
//            try {
//                if (value == null) {
//                    value = "1";
//                }
//                SemanticObject sobj = ont.getSemanticObject(soid);
//                sobj.setLongProperty(Inheritable.swb_inherita, Long.parseLong(value));
//
//                SemanticClass scls = sobj.getSemanticClass();
//                log.debug("doAction(updinherit):" + scls.getClassName() + ", value:" + value);
//
//                so = sobj;
//                actmsg = "Se actualiz&oacute; la herencia del elemento.";
//            } catch (Exception e) {
//                log.error(e);
//                errormsg = "Error al actualizar la herencia del elemento.";
//            }
//        }

//        if (errormsg.length() == 0) {
//            out.println("<script type=\"text/javascript\">");
//            out.println(" reloadTab('" + so.getURI() + "');");
//            out.println(" setTabTitle('" + so.getURI() + "','" + so.getDisplayName(user.getLanguage()) + "','" + SWBContext.UTILS.getIconClass(so) + "')");
//            out.println("</script>");
//            out.println(actmsg);
//        } else {
//            out.println(errormsg);
//        }
    }
}
