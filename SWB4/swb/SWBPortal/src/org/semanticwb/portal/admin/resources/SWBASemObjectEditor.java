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
public class SWBASemObjectEditor extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBASemObjectEditor.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //System.out.println("doView(SWBASemObjectEditor...)");
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        //System.out.println("doEdit(SWBASemObjectEditor...)");
        String id = request.getParameter("suri");
        String idp= request.getParameter("sprop");
        String rid = request.getParameter("rsuri");
        String action = request.getParameter("act");
        System.out.println("id:" + id);
        System.out.println("idp:" + idp);
        if (id == null) {
            id = paramRequest.getTopic().getWebSiteId();
        }
        if (action == null) {
            action = "";
        }
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass cls = obj.getSemanticClass();
        
        //String title=obj.getProperty(SWBContext.getVocabulary().);
        String title=cls.getName();
        
        User user = paramRequest.getUser();
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("update");

  // elementos de dojo requeridos
        
        String path = "http://"+request.getServerName()+":"+request.getServerPort()+SWBPlatform.getContextPath();

        System.out.println(path);
//        out.println("<style type=\"text/css\">");
//        out.println("    @import \""+path+"/swbadmin/js/dojo/dojo/resources/dojo.css\";");
//        out.println("    @import \""+path+"/swbadmin/js/dojo/dijit/themes/soria/soria.css\";");
//        out.println("    @import \""+path+"/swbadmin/css/swb.css\";");
//        out.println("</style>");
//        
//        out.println("<script type=\"text/javascript\" src=\""+path+"/swbadmin/js/dojo/dojo/dojo.js\" djConfig=\"parseOnLoad:true, usePlainJson:true\"></script>");
//        out.println("<script type=\"text/javascript\">");
//        out.println("  dojo.require(\"dijit.form.CheckBox\");");
//        out.println("  dojo.require(\"dojo.parser\");");
//        out.println("</script>");


        if (action.equals("")) {
            String tmpName = obj.getRDFName();
            System.out.println("label: " + title + ", name: " + tmpName);
            if(title.indexOf("RoleRef")>-1||title.indexOf("PortletRef")>-1||title.indexOf("TemplateRef")>-1)
            {
                tmpName= obj.getProperty(SWBContext.getVocabulary().title);
            }
            else //if(obj2.getDisplayName()!=null&&obj2.getDisplayName().trim().length()>0)
            {
                    tmpName = obj.getDisplayName();  
            }
            out.println("<form action=\"" + url + "\" method=\"get\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
            out.println("<p>Please complete the form below. Mandatory fields marked <em>*</em></p>");
            out.println("<fieldset>");
            out.println("	<legend> Propiedades - "+ title +" ( "+ tmpName + " ) </legend>");
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
                    if (prop.getLabel() != null) {
                        label = prop.getLabel();
                    }
                    if (prop.isBoolean()) {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"checkbox\"  id=\"" + name + "\" name=\"" + name + "\" value=\""+value+"\" "+(value != null && value.equals("true") ? "checked" : "") + "/></li>"); // 
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
                boolean modificable=true;
                boolean unumlist=false;
                if (prop.isObjectProperty()) {
                    String name = prop.getName();
                    String label =  prop.getDisplayName();
                    System.out.println("label: " + label + ", name: " + name);
                    modificable=true;
//                    if (prop.getLabel() != null) {
//                        label = prop.getLabel();
//                    }
                    if(name.equalsIgnoreCase("modifiedBy")||name.equalsIgnoreCase("creator")) modificable=false;
                    if(name.startsWith("has")) unumlist=true;
                    out.println("<li><label for=\"" + name + "\">" + label + " <em>*</em></label> ");
                    Iterator<SemanticObject> soit = obj.listObjectProperties(prop);
                    SemanticObject obj2 = null;
                    while (soit.hasNext()) {
                        if(unumlist) out.print("<ul>");
                        obj2 = soit.next();
                        SWBResourceURL urle = paramRequest.getRenderUrl();
                        urle.setParameter("rsuri", obj.getURI());
                        urle.setParameter("suri", obj2.getURI());
                        urle.setParameter("sprop", prop.getURI());
                        urle.setParameter(name, prop.getURI());
                        tmpName = obj2.getRDFName();
                        if(name.indexOf("RoleRef")>0||name.indexOf("PortletRef")>0||name.indexOf("TemplateRef")>0)
                        {
                            tmpName= obj2.getProperty(SWBContext.getVocabulary().title);
                        }
                        else //if(obj2.getDisplayName()!=null&&obj2.getDisplayName().trim().length()>0)
                        {
                             tmpName = obj2.getDisplayName();  
                        }

                        if(modificable) out.println("<a href=\""+urle+"\" >" + tmpName + "</a>");
                        else out.println(tmpName);
                        SWBResourceURL urlr = paramRequest.getActionUrl();
                        urlr.setParameter("suri", obj.getURI());
                        urlr.setParameter("sprop", prop.getURI());
                        urlr.setParameter(name, prop.getURI());
                        urlr.setAction("remove");
                        if(modificable) out.println("<a  href=\"" + urlr + "\">Remove</a>");
                        if(unumlist) out.print("</ul>");
                    }
                    

                    out.println("</li>");
                    SWBResourceURL urlc = paramRequest.getRenderUrl();
                    urlc.setMode(SWBResourceURL.Mode_EDIT);
                    urlc.setParameter("suri", obj.getURI());
                    urlc.setParameter("sprop", prop.getURI());
                    //if(rid!=null)urlc.setParameter("rsuri", rid);
                    urlc.setParameter("act", "choose");
                    //urlc.setAction("choose");
                    if(modificable) out.println("<a  href=\"" + urlc + "\">Choose</a>");
                    SWBResourceURL urla = paramRequest.getActionUrl();
                    //urla.setMode(SWBResourceURL.Mode_EDIT);
                    urla.setParameter("suri", obj.getURI());
                    urla.setParameter("sprop", prop.getURI());
                    if(rid!=null)urlc.setParameter("rsuri", rid);
                    urla.setAction("new");
                    if(modificable) out.println("<a  href=\"" + urla + "\">Add New</a>");
                }


            }

            out.println("	</ol>");
            out.println("</fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            urlBack.setParameter("suri", rid);
            //if(rid!=null)urlBack.setParameter("rsuri", rid);
            System.out.println("id: "+id+", rid: "+rid);
            urlBack.setParameter("act", "");
            out.println("<p><input type=\"submit\" value=\"Guardar\" />");
            if(rid!=null&&!id.equals(rid)) out.println("<input type=\"button\" value=\"regresar\" onclick=\"window.location='"+urlBack+"';\" />");
            out.println("</p>");
            out.println("</form>");
        } 
        else if (action.equals("choose")) 
        {
            SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            title=clsprop.getName();
            //if(title==null)title=prop.getName();
            //String wid=obj.getModel().getName()+"/"+obj.getId()+"/"+prop.getRDFProperty().getLocalName();

            out.println("<form action=\"" + url + "\" method=\"get\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
            out.println("<fieldset>");
            out.println("	<legend> Choose - "+title+" ( "+obj.getDisplayName()+ " )</legend>");
            out.println("	<ol>");
            Iterator<SemanticObject> itso=obj.getModel().listInstancesOfClass(clsprop);
            while(itso.hasNext())
            {
                SemanticObject sobj=itso.next();
                String stitle=sobj.getProperty(SWBContext.getVocabulary().title);
                if(stitle==null)stitle=sobj.getProperty(SWBContext.getVocabulary().usrLogin);
                
                out.println("<li><label for=\"" + stitle + "\">" + stitle + " <em>*</em></label> ");
                SWBResourceURL urlchoose = paramRequest.getActionUrl();
                urlchoose.setAction("choose");
                urlchoose.setParameter("suri",obj.getURI());
                //if(rid!=null)urlchoose.setParameter("rsuri",rid);
                urlchoose.setParameter("sprop",prop.getURI());
                urlchoose.setParameter("sobj",sobj.getURI());             
                out.println("<a href=\""+urlchoose+"\">"+stitle+"</a></li>");

            }
            out.println("	</ol>");
            out.println("</fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            urlBack.setParameter("suri",rid);
            //if(rid!=null)urlBack.setParameter("rsuri", rid);
            urlBack.setParameter("act", "");
            out.println("<p><input type=\"submit\" value=\"Guardar\" />");
            if(!id.equals(rid)) out.println("<input type=\"button\" value=\"regresar\" onclick=\"window.location='"+urlBack+"';\" />");
            out.println("</p>");
            //out.println("<input type=\"button\" value=\"regresar\" onclick=\"window.location='"+urlBack+"';\" /></p>");
            out.println("</form>");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String rid = request.getParameter("rsuri");
        String action = response.getAction();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass cls = obj.getSemanticClass();
        if ("update".equals(action)) {
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                if (prop.isDataTypeProperty()) {
                    String value = request.getParameter(prop.getName());
                    //System.out.println(prop.getName()+" -- >"+value);
                    if (value != null) {
                        if (value.length() > 0) {
                            if (prop.isBoolean()) {
                                obj.setBooleanProperty(prop, true);
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
                    } else {
                        if (prop.isBoolean()) {
                            obj.setBooleanProperty(prop, false);
                        }
                    }
                }
            }
        } // revisar para agregar nuevo semantic object
        else if ("new".equals(action)) {
            String sprop = request.getParameter("sprop");
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            SemanticClass ncls = prop.getRangeClass();
            if (ncls.isAutogenId()) {
                long lid = SWBPlatform.getSemanticMgr().getCounter(obj.getModel().getName() + "/" + ncls.getName());
                SemanticObject nobj = obj.getModel().createSemanticObject(obj.getModel().getObjectUri("" + lid, ncls), ncls);
                obj.addObjectProperty(prop, nobj);
                obj = nobj;
                cls = ncls;
            }
        } 
        else if ("remove".equals(action)) //suri, prop
        {
            String prop_param = request.getParameter("sprop");
            Iterator<SemanticProperty> it = cls.listProperties();
            while (it.hasNext()) {
                SemanticProperty prop = it.next();
                String value = request.getParameter(prop.getName());
                if (value != null) {
                    obj.removeProperty(prop);
                    break;
                }
            }
        } else if ("choose".equals(action)) //suri, prop
        {
            String suri=request.getParameter("suri");
            String sprop=request.getParameter("sprop");
            String sobj=request.getParameter("sobj");
            String sval=null;
            if(sobj==null)
            {
                sval=SWBUtils.TEXT.decode(request.getParameter("sval"),"UTF-8");
            }

            SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
            if(sval!=null)
            {
                if(sval.length()>0)
                {
                    if(prop.isBoolean())obj.setBooleanProperty(prop, Boolean.parseBoolean(sval));
                    if(prop.isInt())obj.setLongProperty(prop, Integer.parseInt(sval));
                    if(prop.isString())obj.setProperty(prop, sval);
                }else
                {
                    obj.removeProperty(prop);
                }
            }else if(sobj!=null)
            {
                SemanticObject aux=ont.getSemanticObject(sobj);
                if(sobj!=null)
                {
                    obj.setObjectProperty(prop, aux);
                }else
                {
                    obj.removeProperty(prop);
                }
                
            }
            response.setRenderParameter("sprop",sprop);
            response.setRenderParameter("sobj",sobj);
        }
        if (id != null) {
            response.setRenderParameter("suri", id);
        }
        if (rid != null) {
            response.setRenderParameter("rsuri", rid);
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

    public void getData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String id = request.getParameter("suri");

    }
}
