/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.*;
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

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        log.debug("doView(SWBASOPropRefEditor...)");
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        log.debug("doEdit(SWBASOPropRefEditor...)");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String idp = request.getParameter("sprop");
        String idpref = request.getParameter("spropref");
        
        System.out.println("id:"+id);
        System.out.println("idp:"+idp);
        System.out.println("idpref:"+idpref);
        
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
            out.println("<script language=\"javascript\">");
            out.println("function updPriority(location,txt){");
            out.println("  var urlupd = location+txt.value;");
            out.println("  alert(txt.value);");
            out.println("   return urlupd;");
            //out.println("  submitUrl(location+txt.value,txt);");
            out.println(" }");
            out.println("</script>");
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
            if(hmprop.get(SWBContext.getVocabulary().created)!=null)
            {
                sptemp = (SemanticProperty)hmprop.get(SWBContext.getVocabulary().created);
                String propname = sptemp.getName();
                try{
                    propname = sptemp.getDisplayName(user.getLanguage());
                }catch(Exception e){}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if(hmprop.get(SWBContext.getVocabulary().updated)!=null)
            {
                sptemp = (SemanticProperty)hmprop.get(SWBContext.getVocabulary().updated);
                String propname = sptemp.getName();
                try{
                    propname = sptemp.getDisplayName(user.getLanguage());
                }catch(Exception e){}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if(hmprop.get(SWBContext.getVocabulary().priority)!=null)
            {
                sptemp = (SemanticProperty)hmprop.get(SWBContext.getVocabulary().priority);
                String propname = sptemp.getName();
                try{
                    propname = sptemp.getDisplayName(user.getLanguage());
                }catch(Exception e){}
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if(hmprop.get(SWBContext.getVocabulary().active)!=null)
            {
                out.println("<th>");
                out.println("Status<br>Active/Unactive");
                out.println("</th>");
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
                urlr.setParameter("suri", obj.getURI());
                urlr.setParameter("sprop", prop.getURI());
                urlr.setParameter("sval", sobj.getURI());
                urlr.setParameter(prop.getName(), prop.getURI());
                urlr.setAction("remove");
                out.println("<a href=\"#\" onclick=\"submitUrl('"+urlr+"',this); return false;\">remove</a>");
                out.println("</td>");
                out.println("<td>");
                SWBResourceURL urlchoose = paramRequest.getRenderUrl();
                urlchoose.setParameter("suri", obj.getURI());
                urlchoose.setParameter("sprop", prop.getURI());
                urlchoose.setParameter("sobj", sobj.getURI());
                if(id!=null)urlchoose.setParameter("rsuri",id);
                if(idp!=null)urlchoose.setParameter("rsprop", idp);
                if(idpref!=null)urlchoose.setParameter("rspropref", idpref);
                urlchoose.setParameter("act", "edit");
                out.println("<a href=\"#\"  onclick=\"submitUrl('"+urlchoose+"',this); return false;\">" + stitle + "</a>");
                //out.println(stitle); 
                out.println("</td>");
                if(hmprop.get(SWBContext.getVocabulary().priority)!=null)
                {
                    SemanticProperty semprop = (SemanticProperty)hmprop.get(SWBContext.getVocabulary().priority);
                    out.println("<td align=\"center\">");
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idp);
                    urlu.setAction("update");
                    
                    out.println("<input type=\"text\" name=\""+semprop.getName()+"\" onblur=\"submitUrl('"+urlu+"&"+semprop.getName()+"='+this.value,this); return false;\" value=\""+getValueSemProp(sobj, semprop)+"\" />");
                    out.println("</td>");
                }
                if(hmprop.get(SWBContext.getVocabulary().created)!=null)
                {
                    SemanticProperty semprop = (SemanticProperty)hmprop.get(SWBContext.getVocabulary().created);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj,semprop));
                    out.println("</td>");
                }
                if(hmprop.get(SWBContext.getVocabulary().updated)!=null)
                {
                    SemanticProperty semprop = (SemanticProperty)hmprop.get(SWBContext.getVocabulary().updated);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj,semprop));
                    out.println("</td>");
                }
                if(hmprop.get(SWBContext.getVocabulary().active)!=null)
                {
                    out.println("<td align=\"center\">");
                    boolean activo=false;
                    if(sobj.getBooleanProperty(SWBContext.getVocabulary().active)) activo=true;
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idp);
                    urlu.setAction("updstatus");
                    urlu.setParameter("val", "1");
                    out.println("<input name=\""+prop.getName()+sobj.getURI()+"\" type=\"radio\" value=\"true\" id=\""+prop.getName()+sobj.getURI()+"\" onclick=\"submitUrl('"+urlu+"',this); return false;\"  "+(activo?"checked='checked'":"")+"/>"); //onclick=\"window.location='"+urlu+"'\"
                    SWBResourceURL urlun = paramRequest.getActionUrl();
                    urlun.setParameter("suri", id);
                    urlun.setParameter("sprop", idp);
                    urlun.setParameter("sval", sobj.getURI());
                    urlun.setParameter("spropref", idp);
                    urlun.setAction("updstatus");
                    urlun.setParameter("val", "0");
                    out.println("<input name=\""+prop.getName()+sobj.getURI()+"\" type=\"radio\" value=\"false\" id=\""+prop.getName()+sobj.getURI()+"\" onclick=\"submitUrl('"+urlun+"',this); return false;\"  "+(!activo?"checked='checked'":"")+" />"); //onclick=\"window.location='"+urlun+"'\"
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
            urlNew.setParameter("spropref", idp);
            urlNew.setParameter("act", "choose");
            out.println("<p><a href=\"#\" onclick=\"submitUrl('"+urlNew+"',this); return false;\">Add New</a>");
            out.println("</p>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("");
        } else if(action.equals("edit"))
        {
            id = request.getParameter("sobj");
            log.debug("----doEdit(Edit): "+ id);
            obj = ont.getSemanticObject(id);
            cls = obj.getSemanticClass();
            title = cls.getName();
            String tmpName = getDisplaySemObj(obj,user.getLanguage());
            log.debug("label: " + title + ", name: " + tmpName);
            out.println("<form action=\"" + url + "\" method=\"get\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
            out.println("<input type=\"hidden\" name=\"rsuri\" value=\"" + request.getParameter("suri") + "\">");
            if(idp!=null)out.println("<input type=\"hidden\" name=\"sprop\" value=\"" + idp + "\">");
            if(idpref!=null)out.println("<input type=\"hidden\" name=\"spropref\" value=\"" + idpref + "\">");

            out.println("<p>Please complete the form below. Mandatory fields marked <em>*</em></p>");
            out.println("<fieldset>");
            out.println("	<legend> Propiedades - " + title + " ( " + tmpName + " ) </legend>");
            out.println("	<ol>");
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                if (prop.isDataTypeProperty()) {

                    String value = obj.getProperty(prop);
                    if (prop.isInt()) {
                        value = "" + obj.getIntProperty(prop);
                    }
                    if (prop.isDate() || prop.isDateTime()) {
                        Date fecha = obj.getDateProperty(prop);
                        if (null != fecha) {
                            value = getDateFormat(fecha.getTime(), user.getLanguage());
                        } else {
                            value = "not set";
                        }
                    }
                    if (prop.isBoolean()) {
                        value = Boolean.toString(obj.getBooleanProperty(prop));
                    }
                    if (value == null) {
                        value = "";
                    }
                    String label = prop.getDisplayName();
                    String name = prop.getName();

                    if (prop.isBoolean()) {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"checkbox\"  id=\"" + name + "\" name=\"" + name + "\" value=\"true\" " + (value != null && value.equals("true") ? "checked" : "") + "/></li>"); // 
                    } else if (prop.isDate() || prop.isDateTime()) {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> " + value + " </li>");
                    } else {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"text\" id=\"" + name + "\" name=\"" + name + "\" value=\"" + value + "\"/></li>");
                    }
                }
            }
            out.println("	<hr>");
            it = cls.listProperties();
            // lista de propiedades de tipo ObjectProperty
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                boolean modificable = true;
                boolean unumlist = false;
                if (prop.isObjectProperty()) {
                    String name = prop.getName();
                    String label = prop.getDisplayName();
                    log.debug("label: " + label + ", name: " + name);
                    modificable = true;

                    if (name.equalsIgnoreCase("modifiedBy") || name.equalsIgnoreCase("creator")) {
                        modificable = false;
                    }
                    if (name.startsWith("has")) {
                        unumlist = true;
                    }
                    out.println("<li><label for=\"" + name + "\">" + label + " <em>*</em></label> ");
                    Iterator<SemanticObject> soit = obj.listObjectProperties(prop);
                    SemanticObject obj2 = null;
                    while (soit.hasNext()) {
                        if (unumlist) {
                            out.print("<ul>");
                        }
                        obj2 = soit.next();
                        SWBResourceURL urle = paramRequest.getRenderUrl();
                        urle.setParameter("rsuri", obj.getURI());
                        urle.setParameter("suri", obj2.getURI());
                        urle.setParameter("sprop", prop.getURI());
                        urle.setParameter(name, prop.getURI());
                        tmpName = getDisplaySemObj(obj2,user.getLanguage());
                        if (modificable) {
                            out.println("<a href=\"#\" onclick=\"submitUrl('"+urle+"',this); return false;\">" + tmpName + "</a>");
                        } else {
                            out.println(tmpName);
                        }
                        SWBResourceURL urlr = paramRequest.getActionUrl();
                        urlr.setParameter("suri", obj.getURI());
                        urlr.setParameter("sprop", prop.getURI());
                        urlr.setParameter("sval", obj2.getURI());
                        urlr.setParameter(name, prop.getURI());
                        urlr.setAction("remove");
                        if (modificable) {
                            out.println("<a  href=\"#\" onclick=\"submitUrl('"+urlr+"',this); return false;\">Remove</a>");
                        }

                        if (unumlist) {
                            out.print("</ul>");
                        }
                    }
                    out.println("</li>");

                    if (modificable) {
                        SWBResourceURL urlc = paramRequest.getRenderUrl();
                        urlc.setMode(SWBResourceURL.Mode_EDIT);
                        urlc.setParameter("suri", obj.getURI());
                        urlc.setParameter("sprop", prop.getURI());
                        urlc.setParameter("act", "choose");
                        out.println("<a  href=\"#\" onclick=\"submitUrl('"+urlc+"',this); return false;\">Choose</a>");
                        SWBResourceURL urla = paramRequest.getActionUrl();
                        urla.setParameter("suri", obj.getURI());
                        urla.setParameter("sprop", prop.getURI());
                        urla.setAction("new");
                        out.println("<a  href=\"#\" onclick=\"submitUrl('"+urla+"',this); return false;\">Add New</a>");
                    }
                    
                }
            }
            out.println("	</ol>");
            out.println("</fieldset>");
            out.println("<p><input type=\"button\" value=\"Guardar\" onclick=\"submitUrl('"+url+"',this); return false;\" />");
            if(request.getParameter("rsuri")!=null&&request.getParameter("rsprop")!=null&&request.getParameter("rspropref")!=null)
            {
                SWBResourceURL urlb = paramRequest.getRenderUrl();
                urlb.setParameter("suri",request.getParameter("rsuri"));
                urlb.setParameter("sprop", request.getParameter("rsprop"));
                urlb.setParameter("spropref", request.getParameter("rspropref"));
                out.println("<input type=\"button\" value=\"Regresar\" onclick=\"submitUrl('"+urlb+"',this); return false;\" />"); //onclick=\"window.location='"+urlb+"'\"
            }
            out.println("</p>");
            out.println("</form>");
        } else if (action.equals("choose")) { //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            title = clsprop.getName();
            HashMap hmSO = new HashMap();
            Iterator<SemanticObject> ite_so = obj.listObjectProperties(prop);
            while (ite_so.hasNext()) {
                SemanticObject so = ite_so.next();
                if (null != so) {
                    hmSO.put(so.getURI(), so);
                }
            }
            out.println("<form action=\"" + url + "\" method=\"get\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
            out.println("<fieldset>");
            out.println("	<legend> Choose - " + prop.getDisplayName(user.getLanguage()) + " ( " + getDisplaySemObj(obj,user.getLanguage()) + " )</legend>");
            out.println("	<ol>");
            Iterator<SemanticObject> itso = obj.getModel().listInstancesOfClass(clsprop);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                String stitle = getDisplaySemObj(sobj,user.getLanguage());
                out.println("<li><label for=\"" + stitle + "\">" + stitle + " <em>*</em></label> ");
                if (hmSO.get(sobj.getURI()) == null) {
                    SWBResourceURL urlchoose = paramRequest.getActionUrl();
                    urlchoose.setAction("choose");
                    urlchoose.setParameter("suri", obj.getURI());
                    urlchoose.setParameter("sprop", prop.getURI());
                    urlchoose.setParameter("sobj", sobj.getURI());
                    out.println("<a href=\"#\" onclick=\"submitUrl('"+urlchoose+"',this); return false;\">" + stitle + "</a>");
                } else {
                    out.println(stitle);
                }
                out.println("</li>");
            }
            out.println("	</ol>");
            out.println("</fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            if(request.getParameter("suri")!=null)urlBack.setParameter("suri", request.getParameter("suri"));
            if(request.getParameter("sprop")!=null)urlBack.setParameter("sprop", request.getParameter("sprop"));
            if(request.getParameter("spropref")!=null)urlBack.setParameter("spropref", request.getParameter("spropref"));
            urlBack.setParameter("act", "");
            out.println("<p><input type=\"button\" value=\"Guardar\" onclick=\"submitUrl('"+url+"',this); return false;\" />");
            if (request.getParameter("suri")!=null&&request.getParameter("sprop")!=null&&request.getParameter("spropref")!=null) {
                out.println("<input type=\"button\" value=\"regresar\" onclick=\"submitUrl('"+urlBack+"',this); return false;\" />");
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
            SemanticObject sobj = ont.getSemanticObject(soid);
            sobj.setBooleanProperty(SWBContext.getVocabulary().active, value.equals("1")?true:false);

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
            String id_usr_request = request.getParameter("id_usr_request");
            log.debug("id_recibido: "+id_usr_request);
            if (ncls.isAutogenId() || (id_usr_request != null && id_usr_request.trim().length() > 0)) {
                long lid = SWBPlatform.getSemanticMgr().getCounter(obj.getModel().getName() + "/" + ncls.getName());
                String str_lid = "" + lid;
                if (id_usr_request != null && id_usr_request.trim().length() > 0) {
                    str_lid = id_usr_request;
                }
                SemanticObject nobj = obj.getModel().createSemanticObject(obj.getModel().getObjectUri(str_lid, ncls), ncls);
                if(prop.getName().startsWith("has"))obj.addObjectProperty(prop, nobj);
                else obj.setObjectProperty(prop, nobj);
                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("suri", nobj.getURI());
                response.setRenderParameter("rsuri", obj.getURI());
                response.setRenderParameter("sprop", prop.getURI());
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
            log.debug("SWASemObjectEditor.processAction(remove)");
            String prop_param = request.getParameter("sprop");
            String prop_param_ref = request.getParameter("spropref");
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                String value = request.getParameter(prop.getName());
                String sval = request.getParameter("sval");
                log.debug(prop.getURI() + ":" + prop_param + "----" + (prop.getURI().equals(prop_param) ? "true" : "false"));
                if (value != null && value.equals(prop_param)) { //se tiene que validar el valor por si es mÃ¡s de una
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
            if (prop_param != null)response.setRenderParameter("sprop", prop_param);
            if (prop_param_ref != null)response.setRenderParameter("spropref", prop_param_ref);
        
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
            ret = obj.getProperty(SWBContext.getVocabulary().title);
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
