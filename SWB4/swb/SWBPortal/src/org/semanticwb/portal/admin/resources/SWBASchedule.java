/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.*;
//import java.util.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.w3c.dom.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBASchedule extends GenericResource {

    /** Nombre del recurso */
    private Logger log = SWBUtils.getLogger(SWBASchedule.class);
    public String strRscType;

    public void init() {
        try {
            strRscType = getResourceBase().getPortletType().getTitle();
        } catch (Exception e) {
            strRscType = "SWBASchedule";
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        log.debug("doView()");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String idp = request.getParameter("sprop");
        String idpref = request.getParameter("spropref");

        log.debug("id:" + id);
        log.debug("idp:" + idp);
        log.debug("idpref:" + idpref);

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

        if (action.equals("")) { //lista de instancias de tipo propiedad existentes para selecionar
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            log.debug("class: " + clsprop.getClassName());
            HashMap hmprop = new HashMap();
            Iterator<SemanticProperty> ite_sp = clsprop.listProperties();
            while (ite_sp.hasNext()) {
                SemanticProperty sp = ite_sp.next();
                log.debug("propiedad:" + sp.getDisplayName() + "---" + sp.getName());
                hmprop.put(sp, sp);
            }
            SemanticProperty sptemp = null;


            title = clsprop.getName();
            out.println("<script language=\"javascript\">");
            out.println("function updPriority(location,txt){");
            out.println("  var urlupd = location+txt.value;");
            out.println("  alert(txt.value);");
            out.println("  window.location=urlupd;");
            out.println(" }");
            out.println("</script>");
            out.println("<fieldset>");
            out.println("<table width=\"100%\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Action");
            out.println("</th>");
            out.println("<th>");
            out.println("Name");
            out.println("</th>");
            if (hmprop.get(SWBContext.getVocabulary().created) != null) {
                sptemp = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().created);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(SWBContext.getVocabulary().updated) != null) {
                sptemp = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().updated);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(SWBContext.getVocabulary().priority) != null) {
                sptemp = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().priority);
                String propname = sptemp.getName();
                try {
                    propname = sptemp.getDisplayName(user.getLanguage());
                } catch (Exception e) {
                }
                out.println("<th>");
                out.println(propname);
                out.println("</th>");
            }
            if (hmprop.get(SWBContext.getVocabulary().active) != null) {
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
                SemanticClass clsobj = sobj.getSemanticClass();
                log.debug("Clase:" + clsobj.getName());

                String stitle = getDisplaySemObj(sobj, user.getLanguage());
                out.println("<tr>");

                out.println("<td>");
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", obj.getURI());
                urlr.setParameter("sprop", prop.getURI());
                urlr.setParameter("sval", sobj.getURI());
                urlr.setParameter(prop.getName(), prop.getURI());
                urlr.setAction("remove");
                out.println("<a href=\"#\" onclick=\"submitUrl('" + urlr + "',this); return false;\">remove</a>");
                out.println("</td>");
                out.println("<td>");
                SWBResourceURL urlchoose = paramRequest.getRenderUrl();
                urlchoose.setParameter("suri", obj.getURI());
                urlchoose.setParameter("sprop", prop.getURI());
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
                out.println("<a href=\"#\"  onclick=\"submitUrl('" + urlchoose + "',this); return false;\">" + stitle + "</a>");
                //out.println(stitle); 
                out.println("</td>");
                if (hmprop.get(SWBContext.getVocabulary().priority) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().priority);
                    out.println("<td align=\"center\">");
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idp);
                    urlu.setAction("update");

                    out.println("<input type=\"text\" name=\"" + semprop.getName() + "\" onblur=\"submitUrl('" + urlu + "&" + semprop.getName() + "='+" + semprop.getName() + ".value,this);\" value=\"" + getValueSemProp(sobj, semprop) + "\" />");
                    out.println("</td>");
                }
                if (hmprop.get(SWBContext.getVocabulary().created) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().created);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(SWBContext.getVocabulary().updated) != null) {
                    SemanticProperty semprop = (SemanticProperty) hmprop.get(SWBContext.getVocabulary().updated);
                    out.println("<td>");
                    out.println(getValueSemProp(sobj, semprop));
                    out.println("</td>");
                }
                if (hmprop.get(SWBContext.getVocabulary().active) != null) {
                    out.println("<td align=\"center\">");
                    boolean activo = false;
                    if (sobj.getBooleanProperty(SWBContext.getVocabulary().active)) {
                        activo = true;
                    }
                    SWBResourceURL urlu = paramRequest.getActionUrl();
                    urlu.setParameter("suri", id);
                    urlu.setParameter("sprop", idp);
                    urlu.setParameter("sval", sobj.getURI());
                    urlu.setParameter("spropref", idp);
                    urlu.setAction("updstatus");
                    urlu.setParameter("val", "1");
                    out.println("<input name=\"" + prop.getName() + sobj.getURI() + "\" type=\"radio\" value=\"true\" id=\"" + prop.getName() + sobj.getURI() + "\" onclick=\"submitUrl('" + urlu + "',this); return false;\"  " + (activo ? "checked='checked'" : "") + "/>"); //onclick=\"window.location='"+urlu+"'\"
                    SWBResourceURL urlun = paramRequest.getActionUrl();
                    urlun.setParameter("suri", id);
                    urlun.setParameter("sprop", idp);
                    urlun.setParameter("sval", sobj.getURI());
                    urlun.setParameter("spropref", idp);
                    urlun.setAction("updstatus");
                    urlun.setParameter("val", "0");
                    out.println("<input name=\"" + prop.getName() + sobj.getURI() + "\" type=\"radio\" value=\"false\" id=\"" + prop.getName() + sobj.getURI() + "\" onclick=\"submitUrl('" + urlun + "',this); return false;\" " + (!activo ? "checked='checked'" : "") + " />"); //onclick=\"window.location='"+urlun+"'\"
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
            urlNew.setMode(SWBResourceURL.Mode_EDIT);
            urlNew.setParameter("act", "AddNew");
            out.println("<p><a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">Add New</a>");
            out.println("</p>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        log.debug("----doEdit() ");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("suri");
        String idp = request.getParameter("sprop");
        String idpref = request.getParameter("spropref");
        User user = paramRequest.getUser();

        // Parámetros
        String view = request.getParameter("view");
        String action = request.getParameter("act") != null ? request.getParameter("act") : "";
        String topic = paramRequest.getTopic().getId();
        String iId = "0";
        Document doc = null;
        String strXmlConf = null;
        String tm = request.getParameter("tm");
        String tp = request.getParameter("tp");
        String strId = request.getParameter("id");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass cls = obj.getSemanticClass();
        String title = cls.getName();

        SWBResourceURL url = paramRequest.getActionUrl();
        //url.setAction("add");
        url.setAction("add");
        //out.println(getJavaScript(response, paramRequest));
        out.println("<form  action=\""+url+"\"  id=\""+id+"/calendar\" name=\""+id+"/calendar\" method=\"post\" class=\"swbform\">"); //id=\"calendar\" name=\"calendar\" dojoType=\"dijit.form.Form\"
        out.println("<fieldset>");
        if (action.equals("AddNew")) { //lista de instancias de tipo propiedad existentes para selecionar
            log.debug("----AddNew" );
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            log.debug("class: " + clsprop.getClassName());

            title = clsprop.getName();
            
            out.println("<input type=\"hidden\" name=\""+id+"/suri\" value=\"" + id + "\">");
            out.println("<input type=\"hidden\" name=\""+id+"/rsuri\" value=\"" + id + "\">");
            if (idp != null) {
                out.println("<input type=\"hidden\" name=\""+id+"/sprop\" value=\"" + idp + "\">");
            }
            if (idpref != null) {
                out.println("<input type=\"hidden\" name=\""+id+"/spropref\" value=\"" + idpref + "\">");
            }
            
            
            out.println("	<ol>");
            //boolean haveObjProp = false;
            Iterator<SemanticProperty> ite_sp = clsprop.listProperties();
            while (ite_sp.hasNext()) {
                SemanticProperty sp = ite_sp.next();
                log.debug("prop:" + sp.getDisplayName() + "---" + sp.getName());
                if (sp.isDataTypeProperty()) {
                    String value = "";
                    String label = sp.getDisplayName();
                    String name = sp.getName();
                    if (sp.isBoolean()) {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"checkbox\"  id=\""+id+"/" + name + "\" name=\""+id+"/" + name + "\" value=\"true\" " + (value != null && value.equals("true") ? "checked" : "") + "/></li>"); // 
                    } else if (sp.isDate() || sp.isDateTime()) {
                        //out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> " + value + " </li>");
                    } else {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"text\" id=\""+id+"/" + name + "\" name=\""+id+"/" + name + "\" value=\"" + value + "\"/></li>");
                    }
                } 
            }
            out.println("	</ol>");
            out.println("</fieldset>");


        } else if (action.equals("update")) {
            if (request.getParameter("sobj") != null) {
                id = request.getParameter("sobj");
            }
            log.debug("----Update " );
            obj = ont.getSemanticObject(id);
            cls = obj.getSemanticClass();
            title = cls.getName();
            String tmpName = getDisplaySemObj(obj, user.getLanguage());
            log.debug("label: " + title + ", name: " + tmpName);
            out.println("<fieldset>");
            out.println("<legend> Propiedades - " + title + " ( " + tmpName + " ) </legend>");
            out.println("<input type=\"hidden\" name=\""+id+"/suri\" value=\"" + obj.getURI() + "\">");
            out.println("<input type=\"hidden\" name=\""+id+"/rsuri\" value=\"" + request.getParameter("suri") + "\">");
            if (idp != null) {
                out.println("<input type=\"hidden\" name=\""+id+"/sprop\" value=\"" + idp + "\">");
            }
            if (idpref != null) {
                out.println("<input type=\"hidden\" name=\""+id+"/spropref\" value=\"" + idpref + "\">");
            }
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
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"checkbox\"  id=\""+id+"/" + name + "\" name=\""+id+"/" + name + "\" value=\"true\" " + (value != null && value.equals("true") ? "checked" : "") + "/></li>"); // 
                    } else if (prop.isDate() || prop.isDateTime()) {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> " + value + " </li>");
                    } else {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"text\" id=\""+id+"/" + name + "\" name=\""+id+"/" + name + "\" value=\"" + value + "\"/></li>");
                    }
                }
            }
            out.println("	<hr size=\"1\" noshade>");
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
                        tmpName = getDisplaySemObj(obj2, user.getLanguage());
                        if (modificable) {
                            out.println("<a href=\"#\" onclick=\"submitUrl('" + urle + "',this); return false;\">" + tmpName + "</a>");
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
                            out.println("<a  href=\"#\" onclick=\"submitUrl('" + urlr + "',this); return false;\">Remove</a>");
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
                        out.println("<a  href=\"#\" onclick=\"submitUrl('" + urlc + "',this); return false;\">Choose</a>");
                        SWBResourceURL urla = paramRequest.getActionUrl();
                        urla.setParameter("suri", obj.getURI());
                        urla.setParameter("sprop", prop.getURI());
                        urla.setAction("new");
                        out.println("<a  href=\"#\" onclick=\"submitUrl('" + urla + "',this); return false;\">Add New</a>");
                    }

                }
            }
            out.println("	</ol>");
            out.println("</p>");
            out.println("</fieldset>");
        }

        // Recurso de calendarización
        String strType = paramRequest.getAdminTopic().getId();

        String createdate = "";
        String usercreate = "";
        String active = "";
        String cal = "";
        String sendselect = "noend";
        String inidate = "";
        String enddate = "";
        String speriod = "";
        String starthour = "";
        String endhour = "";
        String wday1 = "";
        String wday2 = "";
        String wday3 = "";
        String wday4 = "";
        String wday5 = "";
        String wday6 = "";
        String wday7 = "";
        String mday1 = "";
        String mday2 = "";
        String mday3 = "";
        String mday4 = "";
        String mday5 = "";
        String mday6 = "";
        String mday7 = "";
        String yday1 = "";
        String yday2 = "";
        String yday3 = "";
        String yday4 = "";
        String yday5 = "";
        String yday6 = "";
        String yday7 = "";
        String ssmonth = "";
        String mweek = "";
        String mmonths2 = "";
        String mmday = "";
        String mmonths1 = "";
        String ssyear = "";
        String yyday = "";
        String ymonth1 = "";
        String yyears1 = "";
        String ymonth2 = "";
        String yyears2 = "";
        String yweek = "";
        String day = "";
        String wdays = "";
        String week = "";
        String months = "";
        String years = "";

        // Genera forma de administración de calendarizaciones
            {    
            String tit = request.getParameter("title");
            if (strXmlConf != null) {
                doc = SWBUtils.XML.xmlToDom(strXmlConf);
                NodeList nodes = doc.getElementsByTagName("interval");
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if (node.getFirstChild().getLastChild().getNodeValue().equalsIgnoreCase(tit)) {
                        NodeList nodesChild = node.getChildNodes();
                        for (int j = 0; j < nodesChild.getLength(); j++) {
                            Node nodeChild = nodesChild.item(j);
                            String val = nodeChild.getFirstChild().getNodeValue();
                            String name = nodeChild.getNodeName();
                            cal = "on";
                            if (name.equals("title")) {
                                title = val;
                            } else if (name.equals("active")) {
                                active = val;
                            } else if (name.equals("createdate")) {
                                createdate = val;
                            } else if (name.equals("usercreate")) {
                                usercreate = val;
                            } else if (name.equals("inidate")) {
                                inidate = val;
                            } else if (name.equals("enddate")) {
                                enddate = val;
                                sendselect = "endselect";
                            } else if (name.equals("enddate")) {
                                enddate = val;
                            } else if (name.equals("starthour")) {
                                starthour = val;
                            } else if (name.equals("endhour")) {
                                endhour = val;
                            }
                            if (name.equals("iterations")) {
                                String strChildName = nodeChild.getFirstChild().getNodeName();
                                NodeList nodesGrandChild = nodeChild.getFirstChild().getChildNodes();
                                speriod = strChildName;
                                for (int k = 0; k < nodesGrandChild.getLength(); k++) {
                                    Node nodeGrandChild = nodesGrandChild.item(k);
                                    String valChild = nodeGrandChild.getFirstChild().getNodeValue();
                                    strChildName = nodeGrandChild.getNodeName();
                                    if (speriod.equals("weekly")) {
                                        if (strChildName.equals("wdays")) {
                                            wdays = valChild;
                                        }
                                    } else if (speriod.equals("monthly")) {
                                        if (strChildName.equals("months")) {
                                            months = valChild;
                                        } else if (strChildName.equals("wdays")) {
                                            wdays = valChild;
                                        } else if (strChildName.equals("week")) {
                                            week = valChild;
                                        } else if (strChildName.equals("day")) {
                                            day = valChild;
                                        }
                                    } else if (speriod.equals("yearly")) {
                                        if (strChildName.equals("day")) {
                                            day = valChild;
                                        } else if (strChildName.equals("month")) {
                                            months = valChild;
                                        } else if (strChildName.equals("years")) {
                                            years = valChild;
                                        } else if (strChildName.equals("week")) {
                                            week = valChild;
                                        } else if (strChildName.equals("wdays")) {
                                            wdays = valChild;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Obtiene los días seleccionados
                if (wdays != null && !wdays.equals("")) {
                    String[] days = {"", "", "", "", "", "", ""};
                    int dias = 0;
                    if (wdays != null) {
                        dias = Integer.parseInt(wdays);
                    }
                    int res = 0;
                    int cdias = 0;
                    while (dias > 0) {
                        res = dias % 2;
                        days[cdias] = Integer.toString(res);
                        dias = dias / 2;
                        cdias++;
                    }
                    for (int i = 0; i < days.length; i++) {
                        if (days[i] != null) {
                            // Domingo
                            if (i == 0 && days[i].equals("1")) {
                                if (speriod.equals("weekly")) {
                                    wday1 = "checked";
                                } else if (speriod.equals("monthly")) {
                                    mday1 = "checked";
                                } else if (speriod.equals("yearly")) {
                                    yday1 = "checked";
                                }
                            }
                            // Lunes
                            if (i == 1 && days[i].equals("1")) {
                                if (speriod.equals("weekly")) {
                                    wday2 = "checked";
                                } else if (speriod.equals("monthly")) {
                                    mday2 = "checked";
                                } else if (speriod.equals("yearly")) {
                                    yday2 = "checked";
                                }
                            }
                            // Martes
                            if (i == 2 && days[i].equals("1")) {
                                if (speriod.equals("weekly")) {
                                    wday3 = "checked";
                                } else if (speriod.equals("monthly")) {
                                    mday3 = "checked";
                                } else if (speriod.equals("yearly")) {
                                    yday3 = "checked";
                                }
                            }
                            // Miércoles
                            if (i == 3 && days[i].equals("1")) {
                                if (speriod.equals("weekly")) {
                                    wday4 = "checked";
                                } else if (speriod.equals("monthly")) {
                                    mday4 = "checked";
                                } else if (speriod.equals("yearly")) {
                                    yday4 = "checked";
                                }
                            }
                            // Jueves
                            if (i == 4 && days[i].equals("1")) {
                                if (speriod.equals("weekly")) {
                                    wday5 = "checked";
                                } else if (speriod.equals("monthly")) {
                                    mday5 = "checked";
                                } else if (speriod.equals("yearly")) {
                                    yday5 = "checked";
                                }
                            }
                            // Viernes
                            if (i == 5 && days[i].equals("1")) {
                                if (speriod.equals("weekly")) {
                                    wday6 = "checked";
                                } else if (speriod.equals("monthly")) {
                                    mday6 = "checked";
                                } else if (speriod.equals("yearly")) {
                                    yday6 = "checked";
                                }
                            }
                            // Sábado
                            if (i == 6 && days[i].equals("1")) {
                                if (speriod.equals("weekly")) {
                                    wday7 = "checked";
                                } else if (speriod.equals("monthly")) {
                                    mday7 = "checked";
                                } else if (speriod.equals("yearly")) {
                                    yday7 = "checked";
                                }
                            }
                        }
                    }
                }

                // Depende del tipo de periodo
                if (speriod.equals("monthly")) {
                    if (day != null && !day.equals("")) {
                        ssmonth = "day";
                        mmday = day;
                        mmonths1 = months;
                    } else {
                        ssmonth = "week";
                        mweek = week;
                        mmonths2 = months;
                    }
                }
                if (speriod.equals("yearly")) {
                    if (day != null && !day.equals("")) {
                        ssyear = "day";
                        yyday = day;
                        ymonth1 = months;
                        yyears1 = years;
                    } else {
                        ssyear = "week";
                        yweek = week;
                        ymonth2 = months;
                        yyears2 = years;
                    }
                }
            }
        }

        {
            
            out.println("<fieldset>");
            out.println(" <legend> Definici&oacute;n Periodicidad </legend>");
            out.println("  <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("  <tbody>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=100 >" + paramRequest.getLocaleString("frmStartDate") + ":</td>");
            out.println("          <td colSpan=4 ><input type=\"text\" name=\""+id+"/inidate\" id=\""+id+"/inidate\" dojoType=\"dijit.form.DateTextBox\"  size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\"></td>"); 
            out.println("        </tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </td>");
            out.println("  </tr>");
            out.println("  <tr><td><hr size=\"1\" noshade></td></tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=\"20\" ><input type=\"radio\" dojoType=\"dijit.form.RadioButton\" value=\"enddate\" name=\""+id+"/endselect\" onclick=\"dijit.byId('"+id+"/enddate').setDisabled(false); dijit.byId('"+id+"/enddate').focus();\" checked></td>"); 
            out.println("          <td width=\"77\" >" + paramRequest.getLocaleString("frmEndDate") + ":</td>");
            out.println("          <td ><input type=\"text\" name=\""+id+"/enddate\" id=\""+id+"/enddate\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\"></td>"); 
            out.println("        </tr>");
            out.println("        <tr>");
            out.println("          <td ><input type=\"radio\" dojoType=\"dijit.form.RadioButton\" value=\"noend\" name=\""+id+"/endselect\" onclick=\"dijit.byId('"+id+"/enddate').setDisabled(true);\"></td>");
            out.println("          <td colSpan=2 >" + paramRequest.getLocaleString("frmNoEndDate") + "</td>");
            out.println("        </tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </td>");
            out.println("  </tr>");
            out.println("  <tr><td><hr size=\"1\" noshade></td></tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=\"0\" cellPadding=\"1\" width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=20 ><input type=\"checkbox\" id=\""+id+"/time\" name=\""+id+"/time\" dojoType=\"dijit.form.CheckBox\" onClick=\"enableTime('"+id+"');\"></td>");
            out.println("          <td >" + paramRequest.getLocaleString("frmStartHour") + ":&nbsp;<input name=\""+id+"/starthour\" id=\""+id+"/starthour\" dojoType=\"dojox.form.TimeSpinner\" value=\"00:00\"  hours=\"24\" smalldelta=\"10\" maxLength=\"10\" size=\"10\" disabled=\"true\" style=\"width:100px;\"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("frmEndHour") + ":&nbsp;<input name=\""+id+"/endhour\" id=\""+id+"/endhour\" dojoType=\"dojox.form.TimeSpinner\" value=\"00:00\"  hours=\"24\" smalldelta=\"10\"  maxLength=\"10\" size=\"10\" disabled=\"true\" style=\"width:100px;\"/></td>"); //<input maxLength=10 size=10 name=starthour  disabled=true value=\"" + starthour + "\" onBlur=\"javascript:IsValidTime(this);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("frmEndHour") + ":&nbsp;<input id=\"timeSpinnere\" dojoType=\"dojox.form.TimeSpinner\" value=\"00:00\" name=\"timeSpinnere\" hours=\"24\" smalldelta=\"1\" id=\"timeSpinnere\" />&nbsp;<input  maxLength=10 size=10 name=endhour disabled=true value=\"" + endhour + "\" onBlur=\"javascript:IsValidTime(this);\"></td></tr>");
            out.println("         </tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </td>");
            out.println("  </tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <hr size=\"1\" noshade>");
            out.println("    </td>"); 
            out.println("  </tr>");
            //PERIODICIDAD
            out.println("  <tr>");
            out.println("    <td ><input id=\""+id+"/periodicidad\" type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" name=\""+id+"/periodicidad\" onClick=\"disablePeriodicity('"+id+"');\">&nbsp;" + paramRequest.getLocaleString("frmRegularity") + "</td>");
            out.println("  </tr>");
            // SEMANAL
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=10 rowSpan=3>&nbsp;</td>");
            out.println("          <td width=100 rowSpan=2 ><input id=\""+id+"/period1\" type=\"radio\" dojoType=\"dijit.form.RadioButton\" value=\"weekl\" name=\""+id+"/period\" onClick=\"disablePeriodicity('"+id+"');\" checked disabled=\"true\">" + paramRequest.getLocaleString("frmWeekly") + "</td>");
            out.println("          <td ><input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/wday1\" name=\""+id+"/wday1\" " + wday1 + " disabled=\"true\">" + paramRequest.getLocaleString("frmSunday") + "</td>");
            out.println("          <td ><input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/wday2\" name=\""+id+"/wday2\" " + wday2 + " disabled=\"true\">" + paramRequest.getLocaleString("frmMonday") + "</td>");
            out.println("          <td ><input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/wday3\" name=\""+id+"/wday3\" " + wday3 + " disabled=\"true\">" + paramRequest.getLocaleString("frmTuesday") + "</td>");
            out.println("          <td ><input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/wday4\" name=\""+id+"/wday4\" " + wday4 + " disabled=\"true\">" + paramRequest.getLocaleString("frmWednesday") + "</td>");
            out.println("        </tr>");
            out.println("        <tr>");
            out.println("          <td><input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/wday5\" name=\""+id+"/wday5\" " + wday5 + " disabled=\"true\">" + paramRequest.getLocaleString("frmThursday") + "</td>");
            out.println("          <td><input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/wday6\" name=\""+id+"/wday6\" " + wday6 + " disabled=\"true\">" + paramRequest.getLocaleString("frmFriday") + "</td>");
            out.println("          <td><input type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/wday7\" name=\""+id+"/wday7\" " + wday7 + " disabled=\"true\">" + paramRequest.getLocaleString("frmSaturday") + "</td>");
            out.println("          <td></td>");
            out.println("        </tr>");
            out.println("        <tr>");
            out.println("          <td colSpan=5>");
            out.println("            <hr size=\"1\" noshade>");
            out.println("          </td>");
            out.println("        </tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </td>");
            out.println("  </tr>");
            // MENSUAL
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=\"0\" cellPadding=\"1\" width=\"100%\" border=\"0\">");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=\"10\" rowSpan=\"5\">&nbsp;</td>");
            out.println("          <td width=\"100\" rowSpan=\"4\" >");
            out.println("           <input id=\""+id+"/period2\" type=\"radio\" dojoType=\"dijit.form.RadioButton\" value=\"monthly\"  name=\""+id+"/period\" onClick=\"disablePeriodicity('"+id+"');\" >" + paramRequest.getLocaleString("frmMonthly"));
            out.println("            </td>");
            out.println("          <td ><input type=\"radio\" dojoType=\"dijit.form.RadioButton\" id=\""+id+"/smonth1\" name=\""+id+"/smonth\"  value=\"day\" checked onClick=\"enableMonthly('"+id+"');\" disabled=\"true\"></td>");
            out.println("          <td colSpan=\"3\" >" + paramRequest.getLocaleString("frmTheDay") + " <input  type=\"text\" dojoType=\"dijit.form.TextBox\" maxLength=\"2\" size=\"2\" id=\""+id+"/mmday\" name=\""+id+"/mmday\" value=\"" + mmday + "\"  style=\"width:30px;\" disabled=\"true\">&nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " <input maxLength=\"2\"  size=\"2\" id=\""+id+"/mmonths1\" type=\"text\" dojoType=\"dijit.form.TextBox\" name=\""+id+"/mmonths1\" value=\"" + mmonths1 + "\"  style=\"width:30px;\" disabled=\"true\">&nbsp;" + paramRequest.getLocaleString("frmMonths") + "</td>");
            out.println("        </tr>");
            out.println("        <tr >");
            out.println("          <td colSpan=\"4\">");
            out.println("            <hr size=\"1\" noshade>");
            out.println("          </td>");
            out.println("        </tr>");
            out.println("        <tr>");
            out.println("          <td ><input type=\"radio\" dojoType=\"dijit.form.RadioButton\" id=\""+id+"/smonth2\" name=\""+id+"/smonth\"  value=\"week\" onClick=\"enableMonthly('"+id+"');\" disabled=\"true\"></td>"); 
            out.println("          <td >" + paramRequest.getLocaleString("frmThe") + " &nbsp; ");
            out.println("               <select  id=\""+id+"/mweek\" name=\""+id+"/mweek\"  dojoType=\"dijit.form.ComboBox\" autocomplete=\"true\" hasDownArrow=\"true\" style=\"width:90px;\" disabled=\"true\">");
            out.println("                   <option value=\"1\" selected>" + paramRequest.getLocaleString("frmFirst") + "</option>");
            out.println("                   <option value=\"2\">" + paramRequest.getLocaleString("frmSecond") + "</option>");
            out.println("                   <option value=\"3\">" + paramRequest.getLocaleString("frmThird") + "</option>");
            out.println("                   <option value=\"4\">" + paramRequest.getLocaleString("frmFourth") + "</option>");
            out.println("                   <option value=\"5\">" + paramRequest.getLocaleString("frmLast") + "</option>");
            out.println("               </select> </td>");
            if (mweek != null && !mweek.equals("")) {
                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
                out.println("           selectCombo(document.calendar.mweek,'" + mweek + "');");
                out.println("       </script>");
            }
            out.println("          <td>");
            out.println("            <table cellSpacing=\"0\" cellPadding=\"1\" width=\"100%\" border=\"0\">");
            out.println("              <tbody>");
            out.println("              <tr>");
            out.println("                <td ><input type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" id=\""+id+"/mday1\" name=\""+id+"/mday1\" " + mday1 + " disabled=\"true\">" + paramRequest.getLocaleString("frmSun") + "</td>");
            out.println("                <td ><input type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" id=\""+id+"/mday2\" name=\""+id+"/mday2\" " + mday2 + " disabled=\"true\">" + paramRequest.getLocaleString("frmMon") + "</td>");
            out.println("                <td ><input type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" id=\""+id+"/mday3\" name=\""+id+"/mday3\" " + mday3 + " disabled=\"true\">" + paramRequest.getLocaleString("frmTue") + "</td>");
            out.println("                <td ><input type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" id=\""+id+"/mday4\" name=\""+id+"/mday4\" " + mday4 + " disabled=\"true\">" + paramRequest.getLocaleString("frmWed") + "</td>");
            out.println("              </tr>");
            out.println("              <tr>");
            out.println("                <td ><input type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" id=\""+id+"/mday5\" name=\""+id+"/mday5\" " + mday5 + " disabled=\"true\">" + paramRequest.getLocaleString("frmThu") + "</td>");
            out.println("                <td ><input type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" id=\""+id+"/mday6\" name=\""+id+"/mday6\" " + mday6 + " disabled=\"true\">" + paramRequest.getLocaleString("frmFri") + "</td>");
            out.println("                <td ><input type=\"checkbox\"  dojoType=\"dijit.form.CheckBox\" id=\""+id+"/mday7\" name=\""+id+"/mday7\" " + mday7 + " disabled=\"true\">" + paramRequest.getLocaleString("frmSat") + "</td>");
            out.println("              </tr>");
            out.println("              </tbody>");
            out.println("            </table>");
            out.println("          </td>");
            out.println("          <td >&nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " &nbsp;<input  dojoType=\"dijit.form.TextBox\" maxLength=\"2\" size=\"2\" id=\""+id+"/mmonths2\" name=\""+id+"/mmonths2\" style=\"width:30px;\" value=\"" + mmonths2 + "\" disabled=\"true\">&nbsp;" + paramRequest.getLocaleString("frmMonths") + "</td>");
            out.println("        </tr>");
            out.println("        <tr>");
            out.println("          <td colSpan=\"5\">");
            out.println("            <HR size=\"1\" noshade>");
            out.println("          </td>");
            out.println("        </tr>");
            out.println("      </tbody>");
            out.println("    </table>");
            out.println("   </td>");
            out.println("  </tr>");
            // ANUAL
            out.println("  <tr>");
            out.println("    <td >");
            out.println("      <table cellSpacing=\"0\" cellPadding=\"1\" width=\"100%\" border=\"0\">");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=\"10\" rowSpan=\"4\">&nbsp;</td>");
            out.println("          <td width=\"100\" rowSpan=\"4\" >");
            out.println("          <input  id=\""+id+"/period3\"  type=\"radio\" dojoType=\"dijit.form.RadioButton\" value=\"yearly\" name=\""+id+"/period\" onClick=\"disablePeriodicity('"+id+"');\" disabled=\"true\">" + paramRequest.getLocaleString("frmYearly") + "</td>");
            out.println("          <td ><input id=\""+id+"/radio1\" type=\"radio\"  dojoType=\"dijit.form.RadioButton\" name=\""+id+"/syear\" value=\"day\" checked onClick=\"enableYearly('"+id+"');\" disabled=\"true\"></td>");
            out.println("          <td colSpan=3 >" + paramRequest.getLocaleString("frmTheDay"));
            out.println("            <input type=\"text\" dojoType=\"dijit.form.TextBox\" id=\""+id+"/text1\" maxLength=\"2\" size=\"2\" name=\""+id+"/yyday\" style=\"width:30px;\" value=\"" + yyday + "\" disabled=\"true\">&nbsp;" + paramRequest.getLocaleString("frmOf"));
            out.println("            <select id=\""+id+"/selectm1\"  name=\""+id+"/ymonth1\" dojoType=\"dijit.form.ComboBox\" autocomplete=\"true\" hasDownArrow=\"true\" style=\"width:110px;\" disabled=\"true\">");
            out.println("                <option value=\"1\" selected>" + paramRequest.getLocaleString("frmJanuary") + "</option>");
            out.println("                <option value=\"2\">" + paramRequest.getLocaleString("frmFebruary") + "</option>");
            out.println("                <option value=\"3\">" + paramRequest.getLocaleString("frmMarch") + "</option>");
            out.println("                <option value=\"4\">" + paramRequest.getLocaleString("frmApril") + "</option>");
            out.println("                <option value=\"5\">" + paramRequest.getLocaleString("frmMay") + "</option>");
            out.println("                <option value=\"6\">" + paramRequest.getLocaleString("frmJune") + "</option>");
            out.println("                <option value=\"7\">" + paramRequest.getLocaleString("frmJuly") + "</option>");
            out.println("                <option value=\"8\">" + paramRequest.getLocaleString("frmAugust") + "</option>");
            out.println("                <option value=\"9\">" + paramRequest.getLocaleString("frmSeptember") + "</option>");
            out.println("                <option value=\"10\">" + paramRequest.getLocaleString("frmOctober") + "</option>");
            out.println("                <option value=\"11\">" + paramRequest.getLocaleString("frmNovember") + "</option>");
            out.println("                <option value=\"12\">" + paramRequest.getLocaleString("frmDecember") + "</option>");
            out.println("            </select>");
//            if (ymonth1 != null && !ymonth2.equals("")) {
//                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
//                out.println("           selectCombo(document.calendar.ymonth1,'" + ymonth1 + "');");
//                out.println("       </script>");
//            }

            if (yyears1 != null && !yyears1.equals("")) {
                out.println("            &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " <input  type=\"text\" dojoType=\"dijit.form.TextBox\" id=\""+id+"/text2\" maxLength=\"2\" size=\"2\" style=\"width:30px;\" name=\""+id+"/yyears1\" value=\"" + yyears1 + "\" disabled=\"true\">&nbsp;" + paramRequest.getLocaleString("frmYears") + "");
            } else {
                out.println("            &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + "<input  type=\"text\" dojoType=\"dijit.form.TextBox\" id=\""+id+"/text2\" maxLength=\"2\" size=\"2\" style=\"width:30px;\" name=\""+id+"/yyears1\" value=\"1\" disabled=\"true\">&nbsp;" + paramRequest.getLocaleString("frmYears") + "");
            }
            out.println("          </td></tr>");
            out.println("        <tr >");
            out.println("          <td colSpan=\"4\" >");
            out.println("            <hr size=\"1\" noshade>");
            out.println("          </td></tr>");
            out.println("        <tr>");
            out.println("          <td ><input id=\""+id+"/radio2\" type=\"radio\"  dojoType=\"dijit.form.RadioButton\" name=\""+id+"/syear\" value=\"week\" onClick=\"enableYearly('"+id+"');\" disabled=\"true\"></td>");
            out.println("          <td >" + paramRequest.getLocaleString("frmThe") + " &nbsp;");
            out.println("             <select id=\""+id+"/select1\" name=\"yweek\"  dojoType=\"dijit.form.ComboBox\" autocomplete=\"true\" hasDownArrow=\"true\" style=\"width:90px;\" disabled=\"true\">");
            out.println("              <option value=\"1\" selected>" + paramRequest.getLocaleString("frmFirst") + "</option>");
            out.println("              <option value=\"2\">" + paramRequest.getLocaleString("frmSecond") + "</option>");
            out.println("              <option value=\"3\">" + paramRequest.getLocaleString("frmThird") + "</option>");
            out.println("              <option value=\"4\">" + paramRequest.getLocaleString("frmFourth") + "</option>");
            out.println("              <option value=\"5\">" + paramRequest.getLocaleString("frmLast") + "</option>");
            out.println("             </select>");
//            if (yweek != null && !yweek.equals("")) {
//                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
//                out.println("           selectCombo(document.calendar.yweek,'" + yweek + "');");
//                out.println("       </script>");
//            }
            out.println("          </td>");
            out.println("          <td>");
            out.println("            <table cellSpacing=\"0\" cellPadding=\"1\" width=\"100%\" border=\"0\">");
            out.println("              <tbody>");
            out.println("              <tr>");
            out.println("                <td ><input  type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/yday1\" name=\""+id+"/yday1\" " + yday1 + " disabled=\"true\">" + paramRequest.getLocaleString("frmSun") + "</td>");
            out.println("                <td ><input  type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/yday2\" name=\""+id+"/yday2\" " + yday2 + " disabled=\"true\">" + paramRequest.getLocaleString("frmMon") + "</td>");
            out.println("                <td ><input  type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/yday3\" name=\""+id+"/yday3\" " + yday3 + " disabled=\"true\">" + paramRequest.getLocaleString("frmTue") + "</td>");
            out.println("                <td ><input  type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/yday4\" name=\""+id+"/yday4\" " + yday4 + " disabled=\"true\">" + paramRequest.getLocaleString("frmWed") + "</td>");
            out.println("              </tr>");
            out.println("              <tr>");
            out.println("                <td ><input  type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/yday5\" name=\""+id+"/yday5\" " + yday5 + " disabled=\"true\">" + paramRequest.getLocaleString("frmThu") + "</td>");
            out.println("                <td ><input  type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/yday6\" name=\""+id+"/yday6\" " + yday6 + " disabled=\"true\">" + paramRequest.getLocaleString("frmFri") + "</td>");
            out.println("                <td ><input  type=\"checkbox\" dojoType=\"dijit.form.CheckBox\" id=\""+id+"/yday7\" name=\""+id+"/yday7\" " + yday7 + " disabled=\"true\">" + paramRequest.getLocaleString("frmSat") + "</td>");
            out.println("              </tr>");
            out.println("              </tbody>");
            out.println("            </table>");
            out.println("          </td>");
            out.println("          <td >&nbsp;" + paramRequest.getLocaleString("frmOf")); 
            out.println("             <select id=\""+id+"/selectm2\" name=\""+id+"/ymonth2\" dojoType=\"dijit.form.ComboBox\" autocomplete=\"true\" hasDownArrow=\"true\" style=\"width:110px;\" disabled=\"true\">");
            out.println("                <option value=\"1\" selected>" + paramRequest.getLocaleString("frmJanuary") + "</option>");
            out.println("                <option value=\"2\">" + paramRequest.getLocaleString("frmFebruary") + "</option>");
            out.println("                <option value=\"3\">" + paramRequest.getLocaleString("frmMarch") + "</option>");
            out.println("                <option value=\"4\">" + paramRequest.getLocaleString("frmApril") + "</option>");
            out.println("                <option value=\"5\">" + paramRequest.getLocaleString("frmMay") + "</option>");
            out.println("                <option value=\"6\">" + paramRequest.getLocaleString("frmJune") + "</option>");
            out.println("                <option value=\"7\">" + paramRequest.getLocaleString("frmJuly") + "</option>");
            out.println("                <option value=\"8\">" + paramRequest.getLocaleString("frmAugust") + "</option>");
            out.println("                <option value=\"9\">" + paramRequest.getLocaleString("frmSeptember") + "</option>");
            out.println("                <option value=\"10\">" + paramRequest.getLocaleString("frmOctober") + "</option>");
            out.println("                <option value=\"11\">" + paramRequest.getLocaleString("frmNovember") + "</option>");
            out.println("                <option value=\"12\">" + paramRequest.getLocaleString("frmDecember") + "</option>");
            out.println("             </select>");
//            if (ymonth2 != null && !ymonth2.equals("")) {
//                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
//                out.println("           selectCombo(document.calendar.ymonth2,'" + ymonth2 + "');");
//                out.println("       </script>");
//            }
            if (yyears2 != null && !yyears2.equals("")) {
                out.println("               &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " &nbsp;");
                out.println("               <input   id=\""+id+"/text3\" type=\"text\" dojoType=\"dijit.form.TextBox\" maxLength=\"2\" size=\"2\" name=\""+id+"/yyears2\" value=\"" + yyears2 + "\"  style=\"width:30px;\" disabled=\"true\">");
                out.println("               &nbsp;" + paramRequest.getLocaleString("frmYears") + "</td></tr></tbody></table></td></tr>");
            } else {
                out.println("               &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " &nbsp;");
                out.println("               <input  id=\""+id+"/text3\" type=\"text\" dojoType=\"dijit.form.TextBox\" maxLength=\"2\" size=\"2\" name=\""+id+"/yyears2\" value=\"1\"  style=\"width:30px;\" disabled=\"true\">");
                out.println("               &nbsp;" + paramRequest.getLocaleString("frmYears") + "</td></tr></tbody></table></td></tr>");
            }
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <hr size=\"1\" noshade>");
            out.println("    </td></tr></tbody></table>");

            out.println("<input type=\"hidden\" name=\"id\" value=\"" + strId + "\">");
            out.println("<input type=\"hidden\" name=\"view\" value=\"" + view + "\"> ");
            out.println("<input type=\"hidden\" name=\"tp\" value=\"" + topic + "\"> ");
            out.println("<input type=\"hidden\" name=\"tm\" value=\"" + tm + "\"> ");
            out.println("<input type=\"hidden\" name=\"type\" value=\"" + strType + "\"> ");
            out.println("<input type=\"hidden\" name=\"oldtitle\" value=\"" + title + "\"> ");
            out.println("<input type=\"hidden\" name=\"createdate\" value=\"" + createdate + "\"> ");
            out.println("<input type=\"hidden\" name=\"usercreate\" value=\"" + usercreate + "\"> ");
            out.println("<input type=\"hidden\" name=\"active\" value=\"" + active + "\"> ");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\"> ");
            out.println("<input type=\"hidden\" name=\"sprop\" value=\"" + idp + "\"> ");
            out.println("<input type=\"hidden\" name=\"spropref\" value=\"" + idpref + "\"> ");
            
            //paramRequest.getActionUrl().setParameter("idp",Integer.toString(iId));
             
            out.println("<p><button dojoType=\"dijit.form.Button\" name=\"enviar\" onclick=\"submitForm('" + id + "/calendar'); return false;\">" + paramRequest.getLocaleString("btnSend") + "</button>");
            if (id != null && idp != null ) {
                SWBResourceURL urlb = paramRequest.getRenderUrl();
                urlb.setParameter("suri", id);
                urlb.setParameter("sprop", idp);
                if(idpref!=null) urlb.setParameter("spropref", idpref);
                urlb.setMode(SWBResourceURL.Mode_VIEW);
                out.println("<button dojoType=\"dijit.form.Button\" id=\""+id+"/bckbutton\" name=\"bckbutton\" onclick=\"submitUrl('" + urlb + "',this.form); return false;\">Regresar</button>"); //dijit.byId('"+id+"/calendar').domNode
            }
            out.println("</p>");
            out.println("</fieldset>");
            out.println("</form>");
//            out.println("<script>");
//            out.println("  window.onload=function() { ");
//            out.println("   alert('onload...');");
//            out.println("   disablePeriodicity('"+id+"');");
//            out.println("};");
//            out.println("</script>"); 
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        
        String action = response.getAction();
        if(null==action) action="";
        
        String stype = request.getParameter("type");
        String sres = request.getParameter("res");
        String suserid = request.getParameter("userid");
        String act = request.getParameter("act");
        String strTp = request.getParameter("tp");
        String strTm = request.getParameter("tm");
        String strId = request.getParameter("id");


        String strCreateDate = "";
        //String strUserCreate=response.getUser().getId();
        String strUserCreate = request.getParameter("usercreate");
        String strModDate = Long.toString(System.currentTimeMillis());
        String strUserMod = response.getUser().getId();
        String strActive = request.getParameter("active");

        Enumeration<String> enup = request.getParameterNames();
        while(enup.hasMoreElements())
        {
            String param = enup.nextElement();
            log.debug("parametro: "+param+", value: "+request.getParameter(param));
        }
        
        String id = request.getParameter("suri");
        String rid = request.getParameter("rsuri");
        String sprop = request.getParameter("sprop");
        String spropref = request.getParameter("spropref");
        
        if ("add".equals(action)) {

//            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sprop);
//            SemanticClass ncls = prop.getRangeClass();
//            String id_usr_request = request.getParameter("id_usr_request");
//            log.debug("id_recibido: "+id_usr_request);
//            if (ncls.isAutogenId() || (id_usr_request != null && id_usr_request.trim().length() > 0)) {
//                long lid = SWBPlatform.getSemanticMgr().getCounter(obj.getModel().getName() + "/" + ncls.getName());
//                String str_lid = "" + lid;
//                if (id_usr_request != null && id_usr_request.trim().length() > 0) {
//                    str_lid = id_usr_request;
//                }
//                SemanticObject nobj = obj.getModel().createSemanticObject(obj.getModel().getObjectUri(str_lid, ncls), ncls);
//                if(prop.getName().startsWith("has"))obj.addObjectProperty(prop, nobj);
//                else obj.setObjectProperty(prop, nobj);
//                response.setMode(response.Mode_EDIT);
//                response.setRenderParameter("suri", nobj.getURI());
//                response.setRenderParameter("rsuri", obj.getURI());
//                response.setRenderParameter("sprop", prop.getURI());
//                response.setRenderParameter("act", "");
//            } else {
//                //Llamada para pedir el id del SemanticObject que no cuenta con el AutogenID
//                Enumeration enu_p = request.getParameterNames();
//                while (enu_p.hasMoreElements()) {
//                    String p_name = (String) enu_p.nextElement();
//                    response.setRenderParameter(p_name, request.getParameter(p_name));
//                }
//                response.setMode(MODE_IdREQUEST);
//            }
            
            response.setMode(response.Mode_VIEW);
        }
        
        
        
        
        
        if (request.getParameter("createdate") != null && !request.getParameter("createdate").equals("")) {
            strCreateDate = request.getParameter("createdate");
        } else {
            strCreateDate = Long.toString(System.currentTimeMillis());
        }
        if (request.getParameter("usercreate") != null && !request.getParameter("usercreate").equals("")) {
            strUserCreate = request.getParameter("usercreate");
        } else {
            strUserCreate = response.getUser().getId();
        }
        if (request.getParameter("active") != null && !request.getParameter("active").equals("")) {
            strActive = request.getParameter("active");
        } else {
            strActive = "false";
        }
        String iId = "0";
        if (!stype.equals("WBAd_sysi_TopicsPeriodicity")) {
            if (strId != null && !strId.equals("")) {
                iId = strId;
            }
        }
        String strOldTitle = request.getParameter("oldtitle");
        if (act == null) {
            // Para menejo de administración del recurso
            if (stype != null && sres != null && suserid != null) {
                Portlet res = SWBContext.getWebSite(getResourceBase().getWebSiteId()).getPortlet(sres);
                res.setAttribute("type", stype);
                //res.updateAttributesToDB(suserid, "The resource with id:"+sres+" has been updated");
                response.setMode(response.Mode_ADMIN);
                response.setAction("update");
            } else {
                try {
                    Document doc = SWBUtils.XML.getNewDocument();
                    Element interval = doc.createElement("interval");
                    doc.appendChild(interval);
                    //addElem(doc, interval, "title", request.getParameter("title"));
                    addElem(doc, interval, "inidate", request.getParameter("inidate"));
                    addElem(doc, interval, "active", strCreateDate);
                    addElem(doc, interval, "createdate", strCreateDate);
                    addElem(doc, interval, "usercreate", strUserCreate);
                    addElem(doc, interval, "moddate", strModDate);
                    addElem(doc, interval, "usermod", strUserMod);
                    String endselect = request.getParameter("endselect");
                    if (endselect != null && endselect.equals("enddate")) {
                        addElem(doc, interval, "enddate", request.getParameter("enddate"));
                    }
                    if (request.getParameter("time") != null) {
                        addElem(doc, interval, "starthour", request.getParameter("starthour"));
                        addElem(doc, interval, "endhour", request.getParameter("endhour"));
                    }
                    if (request.getParameter("periodicidad") != null) {
                        Element iterations = doc.createElement("iterations");
                        interval.appendChild(iterations);
                        String period = request.getParameter("period");
                        if (period != null) {
                            Element eleperiod = doc.createElement(period);
                            iterations.appendChild(eleperiod);
                            if (period.equals("weekly")) {
                                int x = 0;
                                if (request.getParameter("wday1") != null) {
                                    x += 1;
                                }
                                if (request.getParameter("wday2") != null) {
                                    x += 2;
                                }
                                if (request.getParameter("wday3") != null) {
                                    x += 4;
                                }
                                if (request.getParameter("wday4") != null) {
                                    x += 8;
                                }
                                if (request.getParameter("wday5") != null) {
                                    x += 16;
                                }
                                if (request.getParameter("wday6") != null) {
                                    x += 32;
                                }
                                if (request.getParameter("wday7") != null) {
                                    x += 64;
                                }
                                addElem(doc, eleperiod, "wdays", "" + x);
                            }
                            if (period.equals("monthly")) {
                                String smonth = request.getParameter("smonth");
                                if (smonth != null && smonth.equals("day")) {
                                    if (request.getParameter("mmday") != null) {
                                        addElem(doc, eleperiod, "day", request.getParameter("mmday"));
                                    }
                                    if (request.getParameter("mmonths1") != null) {
                                        addElem(doc, eleperiod, "months", request.getParameter("mmonths1"));
                                    }
                                } else if (smonth != null && smonth.equals("week")) {
                                    if (request.getParameter("mweek") != null) {
                                        addElem(doc, eleperiod, "week", request.getParameter("mweek"));
                                    }
                                    int x = 0;
                                    if (request.getParameter("mday1") != null) {
                                        x += 1;
                                    }
                                    if (request.getParameter("mday2") != null) {
                                        x += 2;
                                    }
                                    if (request.getParameter("mday3") != null) {
                                        x += 4;
                                    }
                                    if (request.getParameter("mday4") != null) {
                                        x += 8;
                                    }
                                    if (request.getParameter("mday5") != null) {
                                        x += 16;
                                    }
                                    if (request.getParameter("mday6") != null) {
                                        x += 32;
                                    }
                                    if (request.getParameter("mday7") != null) {
                                        x += 64;
                                    }
                                    addElem(doc, eleperiod, "wdays", "" + x);
                                    if (request.getParameter("mmonths2") != null) {
                                        addElem(doc, eleperiod, "months", request.getParameter("mmonths2"));
                                    }
                                }
                            }
                            if (period.equals("yearly")) {
                                String syear = request.getParameter("syear");
                                if (syear != null && syear.equals("day")) {
                                    if (request.getParameter("yyday") != null) {
                                        addElem(doc, eleperiod, "day", request.getParameter("yyday"));
                                    }
                                    if (request.getParameter("ymonth1") != null) {
                                        addElem(doc, eleperiod, "month", request.getParameter("ymonth1"));
                                    }
                                    if (request.getParameter("yyears1") != null) {
                                        addElem(doc, eleperiod, "years", request.getParameter("yyears1"));
                                    }
                                } else if (syear != null && syear.equals("week")) {
                                    if (request.getParameter("yweek") != null) {
                                        addElem(doc, eleperiod, "week", request.getParameter("yweek"));
                                    }
                                    int x = 0;
                                    if (request.getParameter("yday1") != null) {
                                        x += 1;
                                    }
                                    if (request.getParameter("yday2") != null) {
                                        x += 2;
                                    }
                                    if (request.getParameter("yday3") != null) {
                                        x += 4;
                                    }
                                    if (request.getParameter("yday4") != null) {
                                        x += 8;
                                    }
                                    if (request.getParameter("yday5") != null) {
                                        x += 16;
                                    }
                                    if (request.getParameter("yday6") != null) {
                                        x += 32;
                                    }
                                    if (request.getParameter("yday7") != null) {
                                        x += 64;
                                    }
                                    addElem(doc, eleperiod, "wdays", "" + x);
                                    if (request.getParameter("ymonth2") != null) {
                                        addElem(doc, eleperiod, "month", request.getParameter("ymonth2"));
                                    }
                                    if (request.getParameter("yyears2") != null) {
                                        addElem(doc, eleperiod, "years", request.getParameter("yyears2"));
                                    }
                                }
                            }
                        }
                    }
                    String strXml = SWBUtils.XML.domToXml(doc);
                    strXml = strXml.substring(strXml.lastIndexOf("?>") + 2, strXml.length());
                    if (stype != null && stype.equals("WBAd_sysi_ResourcesPeriodicity")) {
                        //el XML sería ahora el calendar, ya no se guarda en el XML del recurso
                        //ResourceSrv resServ=new ResourceSrv();
                        if (strOldTitle != null && !strOldTitle.equals("")) {
                            //resServ.updateSchedule(strTm,iId,strOldTitle,strXml,response.getUser().getId());
                            WebSite ws = SWBContext.getWebSite(strTm);
                            Calendar cal = ws.getCalendar(iId);
                            cal.setTitle(strOldTitle);
                        } else if (strOldTitle == null || (strOldTitle != null && strOldTitle.equals(""))) {
                            //resServ.addSchedule(strTm,iId,strXml,response.getUser().getId());
                            Calendar cal = SWBContext.getWebSite(strTm).createCalendar();
                            cal.setActive(true);


                        }
                    }

//                    if (stype!=null && stype.equals("WBAd_sysi_TopicsPeriodicity")) {
//                        if (strTp!=null && !strTp.equals("")) {
//                            WebSite topicmap=TopicMgr.getInstance().getTopicMap(strTm);
//                            WebPage topic=topicmap.getTopic(strTp);
//                            TopicSrv tpServ=new TopicSrv();
//                            if (strOldTitle!=null && !strOldTitle.equals(""))
//                                tpServ.updateSchedule(topic,strOldTitle,strXml,response.getUser().getId());
//                            else
//                                tpServ.addSchedule(topic,strXml,response.getUser().getId());
//                        }
//                    }

                    if (stype != null && stype.equals("WBAd_sysi_TemplatesPeriodicity")) {
//                        TemplateSrv tplServ=new TemplateSrv();
                        if (strOldTitle != null && !strOldTitle.equals("")) {
                            //el XML sería ahora el calendar, ya no se guarda en el XML del template
                            //tplServ.updateSchedule(strTm,iId,strOldTitle,strXml,response.getUser().getId());
                        } else {
                            //tplServ.addSchedule(strTm,iId,strXml,response.getUser().getId());
                        }
                    }

                    if (stype != null && stype.equals("WBAd_sysi_ContentsPeriodicity")) {
//                        ResourceSrv contServ=new ResourceSrv();
                        //el XML sería ahora el calendar, ya no se guarda en el XML del contenido 
                        if (strOldTitle != null && !strOldTitle.equals("")) {
                            //contServ.updateSchedule(strTm,iId,strOldTitle,strXml,response.getUser().getId());
                        } else {
                            //contServ.addSchedule(strTm,iId,strXml,response.getUser().getId());
                        }
                    }
                    response.setRenderParameter("id", request.getParameter("id"));
                    response.setRenderParameter("tp", strTp);
                    response.setRenderParameter("tm", strTm);
                } catch (Exception e) {
                    log.error("The XML schedule can't be generated", e);
                }
            }
        } else if (act != null && act.equals("remove")) {
            if (stype != null && stype.equals("WBAd_sysi_ResourcesPeriodicity")) {
                //ResourceSrv resServ=new ResourceSrv();
                //resServ.removeSchedule(strTm,iId,request.getParameter("title"),response.getUser().getId());
                WebSite ws = SWBContext.getWebSite(strTm);
                Calendar cal = ws.getCalendar(iId);
            }
//            if (stype!=null && stype.equals("WBAd_sysi_TopicsPeriodicity")) {
//                if (strTp!=null && !strTp.equals("")) {
//                    WebSite topicmap=TopicMgr.getInstance().getTopicMap(strTm);
//                    WebPage topic=topicmap.getTopic(strTp);
//                    TopicSrv tpServ=new TopicSrv();
//                    tpServ.removeSchedule(topic,request.getParameter("title"),response.getUser().getId());
//                }
//            }
            if (stype != null && stype.equals("WBAd_sysi_TemplatesPeriodicity")) {
                //el XML sería ahora el calendar, ya no se guarda en el XML del template
                // el id del calendar antes era el título
                //TemplateSrv tplServ=new TemplateSrv();
//                tplServ.removeSchedule(strTm,iId,request.getParameter("title"),response.getUser().getId());
            }
            if (stype != null && stype.equals("WBAd_sysi_ContentsPeriodicity")) {
                //ResourceSrv contServ=new ResourceSrv();
                // el id del calendar antes era el título
//                contServ.removeSchedule(strTm,iId,request.getParameter("title"),response.getUser().getId());
            }
            response.setRenderParameter("id", request.getParameter("id"));
            response.setRenderParameter("tp", strTp);
            response.setRenderParameter("tm", strTm);
        }
        if(null!=id) response.setRenderParameter("suri", id);
        if(null!=sprop) response.setRenderParameter("sprop", sprop);
        if(null!=spropref)response.setRenderParameter("spropref", spropref);
    }



    /**
     *
     * @param doc
     * @param parent
     * @param elemName
     * @param elemValue
     */
    public void addElem(Document doc, Element parent, String elemName, String elemValue) {
        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }

    /**
     *
     * @param tm
     * @param iId
     * @throws AFException
     * @return
     */
    public String getXmlConf(String tm, String iId) throws SWBResourceException {
        Portlet recRes = SWBContext.getWebSite(tm).getPortlet(iId);
        if (recRes.getXmlConf() != null) {
            return recRes.getXmlConf();
        } else {
            return "";
        }
    }

    /**
     *
     * @param tm
     * @param tp
     * @throws AFException
     * @return
     */
    public String getTpXmlConf(String tm, String tp) throws SWBResourceException {
        WebPage recTp = SWBContext.getWebSite(tm).getWebPage(tp);
//        if (recTp.getXmlconf()!=null)
//            return recTp.getXmlconf();
//        else
        return "";
    }

    /**
     *
     * @param tm
     * @param iId
     * @throws AFException
     * @return
     */
    public String getTplXmlConf(String tm, String iId) throws SWBResourceException {
        Template tpl = SWBContext.getWebSite(tm).getTemplate(iId);
        return ""; //tpl.getXml();
    }

    /**
     *
     * @param strXmlConf
     * @return
     */
    public Vector getTitles(String strXmlConf) {
        Vector vTitles = new Vector();
        try {
            if (strXmlConf.trim().length() > 0) {
                NodeList ntitlesConf = SWBUtils.XML.xmlToDom(strXmlConf).getElementsByTagName("title");
                if (ntitlesConf.getLength() > 0) {
                    for (int i = 0; i < ntitlesConf.getLength(); i++) {
                        vTitles.add(ntitlesConf.item(i).getChildNodes().item(0).getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while getting schedule titles - WBAContentSchedule:getTitles", e);
        }
        return vTitles;
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
            ret = obj.getProperty(SWBContext.getVocabulary().title);
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

