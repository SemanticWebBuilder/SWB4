/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.Priorityable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/**
 *
 * @author juan.fernandez
 */
public class SWBASOPropRefEditor extends GenericResource {
    
    private Logger log = SWBUtils.getLogger(SWBASOPropRefEditor.class);
    static String MODE_IdREQUEST = "FORMID";
    Portlet base = null;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        log.debug("doView(SWBASOPropRefEditor...)");
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doEdit(SWBASOPropRefEditor...)");
        base = getResourceBase();
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String idp = request.getParameter("sprop");
        String idpref = request.getParameter("spropref");

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
        String title = cls.getName();

        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("update");

        if (action.equals(""))
          { //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            log.debug("class: "+clsprop.getClassName());
            HashMap hmprop = new HashMap();
            Iterator<SemanticProperty> ite_sp= clsprop.listProperties();
            while(ite_sp.hasNext())
            {
                SemanticProperty sp = ite_sp.next();
                log.debug("propiedad:"+sp.getDisplayName()+"---"+sp.getName());
                hmprop.put(sp, sp);
            }
            SemanticProperty sptemp=null;

            title = clsprop.getName();
            out.println("<fieldset>");
//            out.println("	<legend>" + prop.getDisplayName(user.getLanguage()) + "</legend>");
            out.println("<table width=\"100%\">");
//            out.println("<caption>");
//            out.println(getDisplaySemObj(obj,user.getLanguage()));
//            out.println("</caption>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Action");
            out.println("</th>");
            out.println("<th>");
            out.println("Name");
            out.println("</th>");
            if(hmprop.get(Traceable.swb_created)!=null)
            {
                sptemp = (SemanticProperty)hmprop.get(Traceable.swb_created);
                String propname = sptemp.getName();
                try{
                    propname = sptemp.getDisplayName(user.getLanguage());
                }catch(Exception e){}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if(hmprop.get(Traceable.swb_updated)!=null)
            {
                sptemp = (SemanticProperty)hmprop.get(Traceable.swb_updated);
                String propname = sptemp.getName();
                try{
                    propname = sptemp.getDisplayName(user.getLanguage());
                }catch(Exception e){}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if(hmprop.get(Priorityable.swb_priority)!=null)
            {
                sptemp = (SemanticProperty)hmprop.get(Priorityable.swb_priority);
                String propname = sptemp.getName();
                try{
                    propname = sptemp.getDisplayName(user.getLanguage());
                }catch(Exception e){}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if(hmprop.get(Activeable.swb_active)!=null)
            {
                sptemp = (SemanticProperty)hmprop.get(Activeable.swb_active);
                String propname = sptemp.getName();
                try{
                    propname = sptemp.getDisplayName(user.getLanguage());
                }catch(Exception e){}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
                
//                out.println("<th>");
//                out.println("Status<br>Active/Unactive");
//                out.println("</th>");
            }
            out.println("</thead>");
            out.println("<tbody>");
            out.println("</tr>");
            
            Iterator<SemanticObject> itso = obj.listObjectProperties(prop);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                SemanticClass clsobj=sobj.getSemanticClass();
                log.debug("Clase:"+clsobj.getName());
                
                String stitle = getDisplaySemObj(sobj,user.getLanguage());
                out.println("<tr>"); 
                
                out.println("<td>"); 
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", id);
                urlr.setParameter("sprop", idp);
                urlr.setParameter("spropref", idpref);
                urlr.setParameter("sval", sobj.getURI());
                urlr.setParameter(prop.getName(), prop.getURI());
                urlr.setAction("remove");
                out.println("<a href=\"#\" onclick=\"submitUrl('"+urlr+"',this); return false;\">remove</a>");
                out.println("</td>");
                out.println("<td>");

                // Edición del elemento, abre un nuevo tab

                SWBResourceURL urlchoose = paramRequest.getRenderUrl();
                urlchoose.setParameter("suri", id);
                urlchoose.setParameter("sprop", idp);
                urlchoose.setParameter("spropref", idpref);
                urlchoose.setParameter("sobj", sobj.getURI());
                if(id!=null)urlchoose.setParameter("rsuri",id);
                if(idp!=null)urlchoose.setParameter("rsprop", idp);
                if(idpref!=null)urlchoose.setParameter("rspropref", idpref);
                urlchoose.setParameter("act", "edit");
                out.println("<a href=\"#\"  onclick=\"addNewTab('" + sobj.getURI() + "','"+sobj.getDisplayName()+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp');return false;\" >" + stitle + "</a>"); //onclick=\"submitUrl('"+urlchoose+"',this); return false;\"
                //out.println(stitle); 
                out.println("</td>");
                if(hmprop.get(Priorityable.swb_priority)!=null)
                {
                    SemanticProperty semprop = (SemanticProperty)hmprop.get(Priorityable.swb_priority);
                    out.println("<td align=\"center\">");
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idpref);
                    urlu.setAction("update");

                    String val = getValueSemProp(sobj, semprop).trim();
                    String op1="", op2="", op3="", op4="", op5="";
                    if("1".equals(val)) op1="selected";
                    else if("2".equals(val)) op2="selected";
                    else if("3".equals(val)) op3="selected";
                    else if("4".equals(val)) op4="selected";
                    else if("5".equals(val)) op5="selected";
                    out.println("               <select  id=\"" + id + "/"+base.getId()+"/"+sobj.getURI()+"/PSO\" name=\"" +semprop.getName()+ "\"  dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:90px;\" onchange=\"submitUrl('"+urlu+"&"+semprop.getName()+"='+dijit.byId('" + id + "/"+base.getId()+"/"+sobj.getURI()+"/PSO').getValue(),this.domNode); return false;\">");
                    out.println("                   <option value=\"1\" "+op1+" >" + paramRequest.getLocaleString("defecto") + "</option>");
                    out.println("                   <option value=\"2\" "+op2+" >" + paramRequest.getLocaleString("low") + "</option>");
                    out.println("                   <option value=\"3\" "+op3+" >" + paramRequest.getLocaleString("media") + "</option>");
                    out.println("                   <option value=\"4\" "+op4+" >" + paramRequest.getLocaleString("high") + "</option>");
                    out.println("                   <option value=\"5\" "+op5+" >" + paramRequest.getLocaleString("priority") + "</option>");
                    out.println("               </select>");

                    //out.println("<input type=\"text\" name=\""+semprop.getName()+"\" onblur=\"submitUrl('"+urlu+"&"+semprop.getName()+"='+this.value,this); return false;\" value=\""+getValueSemProp(sobj, semprop)+"\" />");
                    out.println("</td>");
                }
                if(hmprop.get(Traceable.swb_created)!=null)
                {
                    SemanticProperty semprop = (SemanticProperty)hmprop.get(Traceable.swb_created);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj,semprop));
                    out.println("</td>");
                }
                if(hmprop.get(Traceable.swb_updated)!=null)
                {
                    SemanticProperty semprop = (SemanticProperty)hmprop.get(Traceable.swb_updated);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj,semprop));
                    out.println("</td>");
                }
                if(hmprop.get(Activeable.swb_active)!=null)
                {
                    out.println("<td align=\"center\">");
                    boolean activo=false;
                    if(sobj.getBooleanProperty(Activeable.swb_active)) activo=true;
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idpref);
                    urlu.setAction("updstatus");
                    //urlu.setParameter("val", "1");
                    out.println("<input name=\""+prop.getName()+sobj.getURI()+"\" type=\"checkbox\" value=\"1\" id=\""+prop.getName()+sobj.getURI()+"\" onclick=\"submitUrl('"+urlu+"&val='+this.checked,this); return false;\"  "+(activo?"checked='checked'":"")+"/>"); 
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
            out.println("<p><a href=\"#\" onclick=\"submitUrl('"+urlNew+"',this); return false;\">Add New</a>");
            out.println("</p>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("");
        }
        else if (action.equals("choose"))  //lista de instancias de tipo propiedad existentes para selecionar
        {
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            if(idpref!=null) prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idpref);
            SemanticClass clsprop = prop.getRangeClass();
            title = clsprop.getName();
            
            HashMap hmSO = new HashMap();
            Iterator<SemanticObject> ite_so = obj.listObjectProperties(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp));
            while (ite_so.hasNext()) {
                SemanticObject so = ite_so.next();
                if (null != so) {
                    hmSO.put(so.getURI(), so);
                }
            }
            out.println("<form id=\""+id+"/chooseSO\" action=\"" + url + "\" method=\"get\" class=\"swbform\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
            out.println("<fieldset>");
            out.println("	<legend> Choose - " + prop.getDisplayName(user.getLanguage()) + " ( " + getDisplaySemObj(obj,user.getLanguage()) + " )</legend>");
            out.println("	<ol>");
            log.debug("Clase: "+clsprop.getName());
            Iterator<SemanticObject> itso = obj.getModel().listInstancesOfClass(clsprop);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                String stitle = getDisplaySemObj(sobj,user.getLanguage());
                out.println("<li><label for=\"" + stitle + "\">" + stitle + " <em>*</em></label> ");
                if (hmSO.get(sobj.getURI()) == null) {
                    SWBResourceURL urlchoose = paramRequest.getActionUrl();
                    urlchoose.setAction("new"); //choose
                    urlchoose.setParameter("suri", obj.getURI());
                    urlchoose.setParameter("sprop", idp); 
                    if(idpref!=null)urlchoose.setParameter("spropref", idpref); 
                    urlchoose.setParameter("sobj", sobj.getURI());
                    out.println("<a href=\"#\" onclick=\"submitUrl('"+urlchoose+"',this); return false;\">" + stitle + "</a>");
                } 
                else
                {
                //    out.println(stitle);
                }
                out.println("</li>");
            }
            out.println("	</ol>");
            out.println("</fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            if(request.getParameter("suri")!=null)urlBack.setParameter("suri", id);
            if(request.getParameter("sprop")!=null)urlBack.setParameter("sprop", idp);
            if(request.getParameter("spropref")!=null)urlBack.setParameter("spropref", idpref);
            urlBack.setParameter("act", "");
            out.println("<p><button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitUrl('"+url+"',this); return false;\" >Guardar</button>");
            if (id!=null&&idp!=null&&idpref!=null) {
                out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitUrl('" + urlBack + "',document.getElementById('"+id+"/chooseSO')); return false;\" >Regresar</button>");
            }
            out.println("</p>");
            out.println("</form>");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String rid = request.getParameter("rsuri");
        String sprop = request.getParameter("sprop");
        String spropref = request.getParameter("spropref");
        String action = response.getAction();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass cls = obj.getSemanticClass();
        if ("update".equals(action)) {
            if(request.getParameter("sval")!=null) 
                obj = ont.getSemanticObject(request.getParameter("sval"));
            
            cls = obj.getSemanticClass();
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                if (prop.isDataTypeProperty()) {
                    String value = request.getParameter(prop.getName());
                    log.debug("SWBASOPropRefEditor: ProcessAction(update): "+prop.getName()+" -- >"+value);
                    if (value != null) {
                        if (value.length() > 0) {
                            if (prop.isBoolean()) {
                                if(value.equals("true")||value.equals("1"))
                                    obj.setBooleanProperty(prop, true);
                                else if(value.equals("false")||value.equals("0"))
                                   obj.setBooleanProperty(prop, false);
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
            if (id != null)response.setRenderParameter("suri", id);
            if (sprop != null)response.setRenderParameter("sprop", sprop);
            if (spropref != null)response.setRenderParameter("spropref", spropref);
            response.setRenderParameter("act","");
        } 
        else if ("updstatus".equals(action)) {
            String soid = request.getParameter("sval");
            String value = request.getParameter("val");
            if(value==null) value="0";
            SemanticObject sobj = ont.getSemanticObject(soid);
            sobj.setBooleanProperty(Activeable.swb_active, value.equals("true")?true:false);

            SemanticClass scls = sobj.getSemanticClass();
            log.debug("SWBASOPropRefEditor: ProcessAction(updstatus):"+scls.getClassName()+": "+value);

            if (id != null)response.setRenderParameter("suri", id);
            if (sprop != null)response.setRenderParameter("sprop", sprop);
            if (spropref != null)response.setRenderParameter("spropref", spropref);
            
        }
        // revisar para agregar nuevo semantic object
        else if ("new".equals(action)) {

            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            SemanticClass ncls = prop.getRangeClass();
            //String id_usr_request = request.getParameter("id_usr_request");
            log.debug("NEW: "+ncls.getName());
            if (ncls.isAutogenId() ) {
                long lid = SWBPlatform.getSemanticMgr().getCounter(obj.getModel().getName() + "/" + ncls.getName());
                String str_lid = "" + lid;                
                SemanticObject nobj = obj.getModel().createSemanticObject(obj.getModel().getObjectUri(str_lid, ncls), ncls);
                if(prop.getName().startsWith("has"))obj.addObjectProperty(prop, nobj);
                else obj.setObjectProperty(prop, nobj);
                if(prop.getName().endsWith("Ref")&&spropref!=null)
                {
                    String soid = request.getParameter("sobj");  // id de Template seleccionado, según sea el tipo de SO
                    SemanticObject soref = null;
                    if(soid!=null&&soid.trim().length()>0) soref = ont.getSemanticObject(soid);
                    SemanticProperty spref = ont.getSemanticProperty(spropref);
                    nobj.setObjectProperty(spref, soref);

                    Iterator<SemanticProperty> itsp = nobj.getSemanticClass().listProperties();
                    while(itsp.hasNext())
                    {
                        SemanticProperty sp = itsp.next();
                        if(sp.equals(Priorityable.swb_priority))
                        {
                            nobj.setIntProperty(sp, 3);
                            break;
                        }
                    }
                }

                response.setMode(response.Mode_EDIT);
                if(id!=null)response.setRenderParameter("suri", id);
                if(rid!=null)response.setRenderParameter("rsuri", rid);
                if(sprop!=null)response.setRenderParameter("sprop", sprop);
                if(spropref!=null)response.setRenderParameter("spropref", spropref);
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
        }
        else if ("remove".equals(action)) //suri, prop
        {
            log.debug("SWASemObjectEditor.processAction(remove)");
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                String value = request.getParameter(prop.getName());
                String sval = request.getParameter("sval");
                log.debug(prop.getURI() + ":" + sprop + "----" + (prop.getURI().equals(sprop) ? "true" : "false"));
                if (value != null && value.equals(sprop)) { //se tiene que validar el valor por si es mÃ¡s de una
                    if (sval != null) {
                        obj.removeObjectProperty(prop, ont.getSemanticObject(sval));
                        if(prop.getName().equalsIgnoreCase("userrepository")){
                            obj.setObjectProperty(prop, ont.getSemanticObject("urswb"));
                        }
                    }
                    break;
                }
            }
            if (id != null)response.setRenderParameter("suri", id);
            if (sprop != null)response.setRenderParameter("sprop", sprop);
            if (spropref != null)response.setRenderParameter("spropref", spropref);
        
        } else if ("choose".equals(action)) //suri, prop
        {
            log.debug("processAction(choose)");
            String suri = request.getParameter("suri");
            //String sprop = request.getParameter("sprop");
            String sobj = request.getParameter("sobj");
            String sval = null;
            if (sobj == null) {
                sval = SWBUtils.TEXT.decode(request.getParameter("sval"), "UTF-8");
            }
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            String pname = prop.getName();
            log.debug("Property Name:" + pname);
            if (!pname.startsWith("has")) {
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
                    SemanticObject aux = ont.getSemanticObject(sobj); //agregando al objectProperty nueva instancia
                    obj.addObjectProperty(prop, aux);
                }
            }
            response.setRenderParameter("spropref", spropref);
            response.setRenderParameter("sprop", sprop);
            response.setRenderParameter("sobj", sobj);
            if (id != null)response.setRenderParameter("suri", id);
            if (rid != null)response.setRenderParameter("rsuri", rid);
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

    public String getDisplaySemObj(SemanticObject obj,String lang) {
        String ret = obj.getRDFName();
        try {
            ret = obj.getDisplayName(lang);
        } catch (Exception e) {
            ret = obj.getProperty(Descriptiveable.swb_title);
        }
        return ret;
    }

    public String getValueSemProp(SemanticObject obj,SemanticProperty prop) {
        String ret = "";
        try {
            if(prop.isDataTypeProperty())
            {
                log.debug("getValueSemProp..."+obj.getProperty(prop));
                if (prop.isBoolean()) {
                    ret=""+obj.getBooleanProperty(prop);
                }
                if (prop.isInt()||prop.isFloat()) {
                    ret=""+obj.getLongProperty(prop);
                }
                if (prop.isString()) {
                    ret=obj.getProperty(prop);
                }
                if (prop.isDateTime()) {
                    ret=""+obj.getDateProperty(prop);
                }
            } 
        } catch (Exception e) {
            ret = "Not set";
        }
        return ret;
    }
    
    public void doFormID(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SWBResourceURL urla = paramRequest.getActionUrl();
        urla.setAction("new");
        out.println("<form action=\"" + urla + "\" method=\"post\">");
        out.println("<p>Please complete the form below. Mandatory fields marked <em>*</em></p>");
        out.println("<fieldset>");
        out.println("	<legend> "+obj.getRDFName()+" - " + obj.getDisplayName(user.getLanguage()) + " </legend>");
        out.println("	<ol>");
        Enumeration enu_p = request.getParameterNames();
        while (enu_p.hasMoreElements()) {
            String p_name = (String) enu_p.nextElement();
            out.println("<input type=hidden name=\"" + p_name + "\" value=\"" + request.getParameter(p_name) + "\">");
        }
        out.println("		<li><label for=\"id_usr_request\">Id <em>*</em></label> <input type=\"text\" id=\"id_usr_request\" name=\"id_usr_request\" value=\"\"/></li>");
        out.println("	</ol>");
        out.println("</fieldset>");
        out.println("<input type=\"submit\" value=\"enviar\">");
        out.println("</form>");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(MODE_IdREQUEST)) {
            doFormID(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

}
