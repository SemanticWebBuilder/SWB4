/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.*;
//import java.util.*;
import java.text.DateFormat;
import java.util.Date;
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

                    out.println("<input type=\"text\" name=\"" + semprop.getName() + "\" onblur=\"updPriority('" + urlu + "&" + semprop.getName() + "=',this);\" value=\"" + getValueSemProp(sobj, semprop) + "\" />");
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
        url.setAction("add");
        out.println(getJavaScript(response, paramRequest));
        out.println("<form id=\"calendar\" name=\"calendar\" method=\"get\">");
        out.println("<fieldset>");
        if (action.equals("AddNew")) { //lista de instancias de tipo propiedad existentes para selecionar
            log.debug("----AddNew" );
            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(idp);
            SemanticClass clsprop = prop.getRangeClass();
            log.debug("class: " + clsprop.getClassName());

            title = clsprop.getName();
            
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
            out.println("<input type=\"hidden\" name=\"rsuri\" value=\"" + id + "\">");
            if (idp != null) {
                out.println("<input type=\"hidden\" name=\"sprop\" value=\"" + idp + "\">");
            }
            if (idpref != null) {
                out.println("<input type=\"hidden\" name=\"spropref\" value=\"" + idpref + "\">");
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
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"checkbox\"  id=\"" + name + "\" name=\"" + name + "\" value=\"true\" " + (value != null && value.equals("true") ? "checked" : "") + "/></li>"); // 
                    } else if (sp.isDate() || sp.isDateTime()) {
                        //out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> " + value + " </li>");
                    } else {
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"text\" id=\"" + name + "\" name=\"" + name + "\" value=\"" + value + "\"/></li>");
                    }
                } 
            }
            out.println("	</ol>");



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
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
            out.println("<input type=\"hidden\" name=\"rsuri\" value=\"" + request.getParameter("suri") + "\">");
            if (idp != null) {
                out.println("<input type=\"hidden\" name=\"sprop\" value=\"" + idp + "\">");
            }
            if (idpref != null) {
                out.println("<input type=\"hidden\" name=\"spropref\" value=\"" + idpref + "\">");
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
            
            
            out.println("<table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("  <tbody>");

//            out.println("  <tr>");
//            out.println("    <td><input type=hidden name=cal value=1>");
//            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
//            out.println("        <tbody>");
//            out.println("        <tr>");
//            out.println("          <td width=100 class=datos>" + paramRequest.getLocaleString("frmTitle") + "</td>");
//            out.println("          <td colSpan=4 class=valores><input maxLength=100 size=40 name=title  value=\"" + title + "\"></td>");
//            out.println("        </tr>");
//            out.println("        </tbody>");
//            out.println("      </table>");
//            out.println("    </td>");
//            out.println("  </tr>");
//            out.println("  <tr><td><hr size=1 noshade></td></tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=100 class=datos>" + paramRequest.getLocaleString("frmStartDate") + ":</td>");
            out.println("          <td colSpan=4 class=valores><input type=\"text\" name=\"inidate\" id=\"inidate\" dojoType=\"dijit.form.DateTextBox\"  size=\"11\" ></td>"); //<input name=\"inidate\" type=\"text\"  value=\"" + inidate + "\" readonly>&nbsp;<img name=calendario src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/show-calendar.gif\" onclick=\"javascript:show_calendar('calendar.inidate');\" border=0> 
            out.println("        </tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </td>");
            out.println("  </tr>");
            out.println("  <tr><td><hr size=1 noshade></td></tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=20 class=valores><input type=\"radio\" value=\"enddate\" name=\"endselect\" onclick=\"javascript:document.calendar.enddate.disabled=false;\" checked></td>");
            out.println("          <td width=77 class=datos>" + paramRequest.getLocaleString("frmEndDate") + ":</td>");
            out.println("          <td class=valores><input type=\"text\" name=\"enddate\" id=\"enddate\" dojoType=\"dijit.form.DateTextBox\" size=\"11\"></td>"); //<input name=\"enddate\" type=\"text\"  value=\"" + enddate + "\" readonly>&nbsp;<img name=calendario src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/show-calendar.gif\" onclick=\"javascript:show_calendar('calendar.enddate');\" border=0> 
            out.println("        <tr>");
            out.println("          <td class=valores><input type=\"radio\" value=\"noend\" name=\"endselect\" onclick=\"javascript:alert(document.calendar.enddate.disabled);document.calendar.enddate.disabled=true;\"></td>");
            out.println("          <td colSpan=2 class=datos>" + paramRequest.getLocaleString("frmNoEndDate") + "</td>");
            out.println("        </tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("  <tr><td><hr size=1 noshade></td></tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=20 class=valores><input type=checkbox name=time  onClick=javascript:enableTime();></td>");
            out.println("          <td class=datos>" + paramRequest.getLocaleString("frmStartHour") + ":&nbsp;<input name=\"starthour\" id=\"starthour\" dojoType=\"dojox.form.TimeSpinner\" value=\"00:00\"  hours=\"24\" smalldelta=\"10\" maxLength=\"10\" size=\"10\"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("frmEndHour") + ":&nbsp;<input name=\"endhour\" id=\"endhour\" dojoType=\"dojox.form.TimeSpinner\" value=\"00:00\"  hours=\"24\" smalldelta=\"10\"  maxLength=\"10\" size=\"10\" /></td></tr>"); //<input maxLength=10 size=10 name=starthour  disabled=true value=\"" + starthour + "\" onBlur=\"javascript:IsValidTime(this);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + paramRequest.getLocaleString("frmEndHour") + ":&nbsp;<input id=\"timeSpinnere\" dojoType=\"dojox.form.TimeSpinner\" value=\"00:00\" name=\"timeSpinnere\" hours=\"24\" smalldelta=\"1\" id=\"timeSpinnere\" />&nbsp;<input  maxLength=10 size=10 name=endhour disabled=true value=\"" + endhour + "\" onBlur=\"javascript:IsValidTime(this);\"></td></tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </td>");
            out.println("  </tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <hr >");
            out.println("    </td></tr>");
            out.println("  <tr>");
            out.println("    <td class=valores><input id=periodicidad type=checkbox  name=periodicidad onClick=javascript:disablePeriodicity();>&nbsp;" + paramRequest.getLocaleString("frmRegularity") + "</td></tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=10 rowSpan=3>&nbsp;</td>");
            out.println("          <td width=100 rowSpan=2 class=datos><input id=period1 type=radio value=weekly name=period onClick=javascript:enableWeekly(); checked>" + paramRequest.getLocaleString("frmWeekly") + "</td>");
            out.println("          <td class=valores><input type=checkbox  name=wday1 " + wday1 + ">" + paramRequest.getLocaleString("frmSunday") + "</td>");
            out.println("          <td class=valores><input type=checkbox  name=wday2 " + wday2 + ">" + paramRequest.getLocaleString("frmMonday") + "</td>");
            out.println("          <td class=valores><input type=checkbox  name=wday3 " + wday3 + ">" + paramRequest.getLocaleString("frmTuesday") + "</td>");
            out.println("          <td class=valores><input type=checkbox  name=wday4 " + wday4 + ">" + paramRequest.getLocaleString("frmWednesday") + "</td></tr>");
            out.println("        <tr>");
            out.println("          <td class=valores><input type=checkbox  name=wday5 " + wday5 + ">" + paramRequest.getLocaleString("frmThursday") + "</td>");
            out.println("          <td class=valores><input type=checkbox  name=wday6 " + wday6 + ">" + paramRequest.getLocaleString("frmFriday") + "</td>");
            out.println("          <td class=valores><input type=checkbox  name=wday7 " + wday7 + ">" + paramRequest.getLocaleString("frmSaturday") + "</td>");
            out.println("          <td></td></tr>");
            out.println("        <tr>");
            out.println("          <td colSpan=5>");
            out.println("            <hr size=1 noshade>");
            out.println("          </td>");
            out.println("        </tr>");
            out.println("        </tbody>");
            out.println("      </table>");
            out.println("    </td>");
            out.println("  </tr>");
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=10 rowSpan=5>&nbsp;</td>");
            out.println("          <td width=100 rowSpan=4 class=datos><input id=period2");
            out.println("            type=radio value=monthly  name=period onClick=javascript:enableMonthly();>" + paramRequest.getLocaleString("frmMonthly") + "</td>");
            out.println("          <td class=datos><input type=radio name=\"smonth\"  value=\"day\" checked onClick=javascript:disablePeriodicity();></td>");
            out.println("          <td colSpan=3 class=datos>" + paramRequest.getLocaleString("frmTheDay") + " <input  maxLength=2 size=2 name=\"mmday\" value=\"" + mmday + "\">&nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " <input maxLength=2  size=2 value=1 name=\"mmonths1\" value=\"" + mmonths1 + "\">&nbsp;" + paramRequest.getLocaleString("frmMonths") + "</td></tr>");
            out.println("        <tr>");
            out.println("        <tr >");
            out.println("          <td colSpan=\"4\">");
            out.println("            <hr size=1 noshade>");
            out.println("          </td></tr></tr>");
            out.println("        <tr>");
            out.println("          <td class=datos><input type=radio name=\"smonth\"  value=\"week\" onClick=javascript:disablePeriodicity();></td>");
            out.println("          <td class=datos>" + paramRequest.getLocaleString("frmThe") + " &nbsp; <select  name=\"mweek\">");
            out.println("              <option value=1 selected>" + paramRequest.getLocaleString("frmFirst") + "</option>");
            out.println("              <option value=2>" + paramRequest.getLocaleString("frmSecond") + "</option> <option");
            out.println("              value=3>" + paramRequest.getLocaleString("frmThird") + "</option> <option value=4");
            out.println("              >" + paramRequest.getLocaleString("frmFourth") + "</option> <option value=5");
            out.println("              >" + paramRequest.getLocaleString("frmLast") + "</option></select> </td>");
            if (mweek != null && !mweek.equals("")) {
                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
                out.println("           selectCombo(document.calendar.mweek,'" + mweek + "');");
                out.println("       </script>");
            }
            out.println("          <td>");
            out.println("            <table cellSpacing=0 cellPadding=1 width=\"100%\" border=\"0\">");
            out.println("              <tbody>");
            out.println("              <tr>");
            out.println("                <td class=datos><input type=checkbox   name=mday1 " + mday1 + ">" + paramRequest.getLocaleString("frmSun") + "</td>");
            out.println("                <td class=datos><input type=checkbox  name=mday2 " + mday2 + ">" + paramRequest.getLocaleString("frmMon") + "</td>");
            out.println("                <td class=datos><input type=checkbox  name=mday3 " + mday3 + ">" + paramRequest.getLocaleString("frmTue") + "</td>");
            out.println("                <td class=datos><input type=checkbox  name=mday4 " + mday4 + ">" + paramRequest.getLocaleString("frmWed") + "</td></tr>");
            out.println("              <tr>");
            out.println("                <td class=datos><input type=checkbox  name=mday5 " + mday5 + ">" + paramRequest.getLocaleString("frmThu") + "</td>");
            out.println("                <td class=datos><input type=checkbox  name=mday6 " + mday6 + ">" + paramRequest.getLocaleString("frmFri") + "</td>");
            out.println("                <td class=datos><input type=checkbox  name=mday7 " + mday7 + ">" + paramRequest.getLocaleString("frmSat") + "</td>");
            out.println("              </tr>");
            out.println("              </tbody>");
            out.println("            </table>");
            out.println("          </td>");
            out.println("          <td class=datos>&nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " &nbsp;<input  maxLength=2 size=2 value=1 name=\"mmonths2\" value=\"" + mmonths2 + "\">&nbsp;" + paramRequest.getLocaleString("frmMonths") + "</td></tr>");
            out.println("        <tr>");
            out.println("          <td colSpan=5>");
            out.println("            <HR size=1 noshade>");
            out.println("          </td></tr></tbody></table></td></tr>");
            out.println("  <tr>");
            out.println("    <td >");
            out.println("      <table cellSpacing=0 cellPadding=1 width=\"100%\" border=0>");
            out.println("        <tbody>");
            out.println("        <tr>");
            out.println("          <td width=10 rowSpan=4>&nbsp;</td>");
            out.println("          <td width=100 rowSpan=4 class=datos><input  id=period3");
            out.println("            type=radio value=yearly name=period onClick=javascript:enableYearly();>" + paramRequest.getLocaleString("frmYearly") + "</td>");
            out.println("          <td class=datos><input id=radio1 type=radio  name=syear value=\"day\" checked onClick=javascript:disablePeriodicity();></td>");
            out.println("          <td colSpan=3 class=datos>" + paramRequest.getLocaleString("frmTheDay") + " <input  id=text1 maxLength=2 size=2 name=yyday value=\"" + yyday + "\">&nbsp;" + paramRequest.getLocaleString("frmOf") + " <select id=select2  name=ymonth1>");
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
            if (ymonth1 != null && !ymonth2.equals("")) {
                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
                out.println("           selectCombo(document.calendar.ymonth1,'" + ymonth1 + "');");
                out.println("       </script>");
            }

            if (yyears1 != null && !yyears1.equals("")) {
                out.println("            &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " <input  id=text2 maxLength=2 size=2 name=yyears1 value=1 value=\"" + yyears1 + "\">&nbsp;" + paramRequest.getLocaleString("frmYears") + "");
            } else {
                out.println("            &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + "<input  id=text2 maxLength=2 size=2 name=yyears1 value=1 >&nbsp;" + paramRequest.getLocaleString("frmYears") + "");
            }
            out.println("          </td></tr>");
            out.println("        <tr>");
            out.println("        <tr >");
            out.println("          <td colSpan=\"4\" >");
            out.println("            <hr >");
            out.println("          </td></tr></tr>");
            out.println("        <tr>");
            out.println("          <td class=datos><input id=radio2 type=radio  name=syear value=\"week\" onClick=javascript:disablePeriodicity();></td>");
            out.println("          <td class=datos>" + paramRequest.getLocaleString("frmThe") + " &nbsp;");
            out.println("             <select id=select1 name=yweek >");
            out.println("              <option value=1 selected>" + paramRequest.getLocaleString("frmFirst") + "</option>");
            out.println("              <option value=2>" + paramRequest.getLocaleString("frmSecond") + "</option>");
            out.println("              <option value=3>" + paramRequest.getLocaleString("frmThird") + "</option>");
            out.println("              <option value=4>" + paramRequest.getLocaleString("frmFourth") + "</option>");
            out.println("              <option value=5>" + paramRequest.getLocaleString("frmLast") + "</option>");
            out.println("             </select>");
            if (yweek != null && !yweek.equals("")) {
                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
                out.println("           selectCombo(document.calendar.yweek,'" + yweek + "');");
                out.println("       </script>");
            }
            out.println("          </td>");
            out.println("          <td>");
            out.println("            <table cellSpacing=0 cellPadding=1 width=\"100%\" border=\"0\">");
            out.println("              <tbody>");
            out.println("              <tr>");
            out.println("                <td class=valores><input  type=checkbox name=yday1 " + yday1 + ">" + paramRequest.getLocaleString("frmSun") + "</td>");
            out.println("                <td class=valores><input  type=checkbox name=yday2 " + yday2 + ">" + paramRequest.getLocaleString("frmMon") + "</td>");
            out.println("                <td class=valores><input  type=checkbox name=yday3 " + yday3 + ">" + paramRequest.getLocaleString("frmTue") + "</td>");
            out.println("                <td class=valores><input  type=checkbox name=yday4 " + yday4 + ">" + paramRequest.getLocaleString("frmWed") + "</td></tr>");
            out.println("              <tr>");
            out.println("                <td class=valores><input  type=checkbox name=yday5 " + yday5 + ">" + paramRequest.getLocaleString("frmThu") + "</td>");
            out.println("                <td class=valores><input  type=checkbox name=yday6 " + yday6 + ">" + paramRequest.getLocaleString("frmFri") + "</td>");
            out.println("                <td class=valores><input  type=checkbox name=yday7 " + yday7 + ">" + paramRequest.getLocaleString("frmSat") + "</td>");
            out.println("              </tr>");
            out.println("              </tbody>");
            out.println("            </table>");
            out.println("          </td>");
            out.println("          <td class=valores>&nbsp;" + paramRequest.getLocaleString("frmOf") + " <select id=select2 name=ymonth2  >");
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
            out.println("                <option value=\"12\">" + paramRequest.getLocaleString("frmDecember") + "</option></select>");
            if (ymonth2 != null && !ymonth2.equals("")) {
                out.println("       <script language=\"JavaScript\" type=\"text/JavaScript\">");
                out.println("           selectCombo(document.calendar.ymonth2,'" + ymonth2 + "');");
                out.println("       </script>");
            }
            if (yyears2 != null && !yyears2.equals("")) {
                out.println("              &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " &nbsp;<input   id=text3 maxLength=2 size=2 name=yyears2 value=\"" + yyears2 + "\">&nbsp;" + paramRequest.getLocaleString("frmYears") + "</td></tr></tbody></table></td></tr>");
            } else {
                out.println("              &nbsp;" + paramRequest.getLocaleString("frmOfEvery") + " &nbsp;<input  id=text3 maxLength=2 size=2 name=yyears2 value=1>&nbsp;" + paramRequest.getLocaleString("frmYears") + "</td></tr></tbody></table></td></tr>");
            }
            out.println("  <tr>");
            out.println("    <td>");
            out.println("      <hr size=1 noshade>");
            out.println("    </td></tr></tbody></table>");
            
            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\">");
            out.println("disablePeriodicity();");
            out.println("disableAll();");
            if (cal != null && !cal.equals("")) {
                out.println("document.calendar.cal.checked=true;");
                out.println("enableAll();");
                out.println("checkRadio(document.calendar.endselect,'" + sendselect + "');");
                if (sendselect != null && sendselect.equals("noend")) {
                    out.println("disableIt(document.calendar.enddate);");
                }
                if (starthour != null && !starthour.equals("") && endhour != null && !starthour.equals("")) {
                    out.println("document.calendar.time.checked=true;");
                    out.println("enableTime();");
                }
                if (speriod != null && !speriod.equals("")) {
                    out.println("document.calendar.periodicidad.checked=true;");
                    out.println("checkRadio(document.calendar.period,'" + speriod + "');");
                    if (speriod.equals("weekly")) {
                        out.println("enableWeekly();");
                    } else if (speriod.equals("monthly")) {
                        out.println("enableMonthly();");
                        out.println("checkRadio(document.calendar.smonth,'" + ssmonth + "');");
                        out.println("disablePeriodicity();");
                    } else if (speriod.equals("yearly")) {
                        out.println("enableYearly();");
                        out.println("checkRadio(document.calendar.syear,'" + ssyear + "');");
                        out.println("disablePeriodicity();");
                    }
                }
            }
            out.println("</script>");
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + strId + "\">");
            out.println("<input type=\"hidden\" name=\"view\" value=\"" + view + "\"> ");
            out.println("<input type=\"hidden\" name=\"tp\" value=\"" + topic + "\"> ");
            out.println("<input type=\"hidden\" name=\"tm\" value=\"" + tm + "\"> ");
            out.println("<input type=\"hidden\" name=\"type\" value=\"" + strType + "\"> ");
            out.println("<input type=\"hidden\" name=\"oldtitle\" value=\"" + title + "\"> ");
            out.println("<input type=\"hidden\" name=\"createdate\" value=\"" + createdate + "\"> ");
            out.println("<input type=\"hidden\" name=\"usercreate\" value=\"" + usercreate + "\"> ");
            out.println("<input type=\"hidden\" name=\"active\" value=\"" + active + "\"> ");
            //paramRequest.getActionUrl().setParameter("idp",Integer.toString(iId));
            out.println("<p><input class=boton type=button value=\"" + paramRequest.getLocaleString("btnSend") + "\" name=\"enviar\" onClick=\"javascript:envia();\">");
            if (request.getParameter("rsuri") != null && request.getParameter("rsprop") != null && request.getParameter("rspropref") != null) {
                SWBResourceURL urlb = paramRequest.getRenderUrl();
                urlb.setParameter("suri", request.getParameter("rsuri"));
                urlb.setParameter("sprop", request.getParameter("rsprop"));
                urlb.setParameter("spropref", request.getParameter("rspropref"));
                out.println("<input type=\"button\" value=\"Regresar\" onclick=\"window.location='" + urlb + "'\"/>");
            }
            out.println("</p>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<script >enableAll();</script>");
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
        String stype = request.getParameter("type");
        String sres = request.getParameter("res");
        String suserid = request.getParameter("userid");
        String act = request.getParameter("act");
        String strTp = request.getParameter("tp");
        String strTm = request.getParameter("tm");
        String strId = request.getParameter("id");

        //System.out.println("strId-->"+strId);
        //System.out.println(System.currentTimeMillis());

        String strCreateDate = "";
        //String strUserCreate=response.getUser().getId();
        String strUserCreate = request.getParameter("usercreate");
        String strModDate = Long.toString(System.currentTimeMillis());
        String strUserMod = response.getUser().getId();
        String strActive = request.getParameter("active");

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
                    addElem(doc, interval, "title", request.getParameter("title"));
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
    }

    private String getJavaScript(HttpServletResponse response, SWBParamRequest paramRequest) {
        StringBuilder out = new StringBuilder();
        try {
            out.append("<script language=\"JavaScript\" type=\"text/JavaScript\"> ");
            out.append("function disableIt(obj) {");
            out.append("    obj.disabled = !(obj.disabled);");
            out.append("}");

            out.append("function envia() {");
            out.append("    enableHidden();");
            out.append("    var isFill=false;");
            out.append("    var isFill=validateForm();");
            out.append("    if (isFill) {");
            out.append("       document.calendar.submit();");
            out.append("    }");
            out.append("}");

            out.append("function disableAll() {");
            out.append("   var _f=document.calendar;");
            out.append("       for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("           if(_f.elements[i]!=undefined) {");
            out.append("               if (_f.elements[i].name=='cal') {");
            out.append("                   _f.elements[i].disabled = false;");
            out.append("               }");
            out.append("               else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("}");

            out.append("function enableAll() {");
            out.append("   var _f=document.calendar;");
            out.append("   if (_f.cal.value == '1')");
            out.append("       disablePeriodicity();");
            out.append("   else");
            out.append("       disableAll();");
            out.append("}");

            out.append("function disableEndDate() {");
            out.append("   alert('disable End Date');");
            out.append("   for (i=0;i<2;i++) {");
            out.append("       if (document.calendar.endselect[i].checked && !document.calendar.endselect[i].disabled)");
            out.append("           var endselect = document.calendar.endselect[i].value");
            out.append("   }");
            out.append("   if (endselect!=undefined) {");
            out.append("       if (endselect=='endselect')");
            out.append("           document.calendar.enddate.disabled=false;");
            out.append("       else if (endselect=='noend')");
            out.append("           document.calendar.enddate.disabled=true;");
            out.append("   }");
            out.append("}");
            out.append("function enableTime() {");
            out.append("   var _f=document.calendar;");
            out.append("   if (_f.time.checked == true) {");
            out.append("       var time='on';");
            out.append("   }");
            out.append("   else {");
            out.append("       var time='off';");
            out.append("   }");
            out.append("   if (time=='on') {");
            out.append("       _f.starthour.disabled = false;");
            out.append("       _f.endhour.disabled = false;");
            out.append("   }");
            out.append("   else if (time=='off') {");
            out.append("       _f.starthour.disabled = true;");
            out.append("       _f.endhour.disabled = true;");
            out.append("   }");
            out.append("}");

            out.append("function disablePeriodicity() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("                if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='time' || _f.elements[i].name=='enviar') {");
            out.append("                   _f.elements[i].disabled = false;");
            out.append("                }");
            out.append("                else {");
            out.append("                   if (_f.periodicidad.checked == true)");
            out.append("                       _f.elements[i].disabled = false;");
            out.append("                   else");
            out.append("                       _f.elements[i].disabled = true;");
            out.append("                }");
            out.append("       }");
            out.append("   }");
            out.append("   for (i=0;i<3;i++) {");
            out.append("       if (document.calendar.period[i].checked && !document.calendar.period[i].disabled) {");
            out.append("           var period = document.calendar.period[i].value");
            out.append("       }");
            out.append("   }");
            out.append("   if (period!=undefined) {");
            out.append("       if (period=='weekly')");
            out.append("           enableWeekly();");
            out.append("       else if (period=='monthly')");
            out.append("           enableMonthly();");
            out.append("       else if (period=='yearly')");
            out.append("           enableYearly();");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("}");

            out.append("function enableWeekly() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("           if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='time' || _f.elements[i].name=='period' || _f.elements[i].name.substring(0,4)=='wday' || _f.elements[i].name=='enviar') {");
            out.append("               _f.elements[i].disabled = false;");
            out.append("           }");
            out.append("           else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("}");

            out.append("function enableMonthly() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("           if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='period' || _f.elements[i].name=='time' || _f.elements[i].name.substring(0,4)=='mday' || _f.elements[i].name.substring(0,4)=='mmon' || _f.elements[i].name.substring(0,4)=='mwee' || _f.elements[i].name=='enviar' || _f.elements[i].name.substring(0,4)=='smon') {");
            out.append("               if (document.calendar.smonth[0].checked && !document.calendar.smonth[0].disabled)");
            out.append("                   var smonth = document.calendar.smonth[0].value");
            out.append("               else if (document.calendar.smonth[1].checked && !document.calendar.smonth[1].disabled)");
            out.append("                   var smonth = document.calendar.smonth[1].value");
            out.append("               _f.elements[i].disabled = false;");
            out.append("           }");
            out.append("           else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("   if (smonth=='day') {");
            out.append("       enableMonthlySubUp();");
            out.append("   }");
            out.append("   else if (smonth=='week') {");
            out.append("       enableMonthlySubDown();");
            out.append("   }");
            out.append("}");

            out.append("function enableMonthlySubUp() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("           if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='period' || _f.elements[i].name=='time' || _f.elements[i].name=='enviar'  || _f.elements[i].name=='mmonths1' || _f.elements[i].name.substring(0,4)=='smon' || _f.elements[i].name=='mmday') {");
            out.append("               _f.elements[i].disabled = false;");
            out.append("           }");
            out.append("           else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("}");

            out.append("function enableMonthlySubDown() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("           if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='period' || _f.elements[i].name=='time' || _f.elements[i].name=='enviar'  || _f.elements[i].name=='mmonths2' || _f.elements[i].name.substring(0,4)=='smon' || _f.elements[i].name=='mweek' || _f.elements[i].name.substring(0,4)=='mday') {");
            out.append("               _f.elements[i].disabled = false;");
            out.append("           }");
            out.append("           else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("}");

            out.append("function enableYearlySubUp() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("           if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='period' || _f.elements[i].name=='time' || _f.elements[i].name=='enviar'  || _f.elements[i].name=='ymonth1' || _f.elements[i].name.substring(0,4)=='syea' || _f.elements[i].name=='yyday' || _f.elements[i].name=='yyears1') {");
            out.append("               _f.elements[i].disabled = false;");
            out.append("           }");
            out.append("           else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("}");

            out.append("function enableYearlySubDown() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("           if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='period' || _f.elements[i].name=='time' || _f.elements[i].name=='enviar'  || _f.elements[i].name=='ymonth2' || _f.elements[i].name.substring(0,4)=='syea' || _f.elements[i].name.substring(0,4)=='yday' || _f.elements[i].name=='yweek' || _f.elements[i].name=='yyears2') {");
            out.append("               _f.elements[i].disabled = false;");
            out.append("           }");
            out.append("           else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("}");

            out.append("function enableYearly() {");
            out.append("   var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined) {");
            out.append("           if (_f.elements[i].name=='cal' || _f.elements[i].name=='title' || _f.elements[i].name=='inidate' || _f.elements[i].name=='enddate' || _f.elements[i].name=='endselect' || _f.elements[i].name=='periodicidad' || _f.elements[i].name=='period' || _f.elements[i].name=='time' || _f.elements[i].name.substring(0,4)=='yday' || _f.elements[i].name.substring(0,4)=='ymon' || _f.elements[i].name.substring(0,4)=='ywee' || _f.elements[i].name=='enviar' || _f.elements[i].name.substring(0,4)=='syea' || _f.elements[i].name.substring(0,4)=='yyea') {");
            out.append("               if (document.calendar.syear[0].checked && !document.calendar.syear[0].disabled)");
            out.append("                   var syear = document.calendar.syear[0].value");
            out.append("               else if (document.calendar.syear[1].checked && !document.calendar.syear[1].disabled)");
            out.append("                   var syear = document.calendar.syear[1].value");
            out.append("               _f.elements[i].disabled = false;");
            out.append("           }");
            out.append("           else {");
            out.append("               _f.elements[i].disabled = true;");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   disableEndDate();");
            out.append("   enableTime();");
            out.append("   if (syear=='day') {");
            out.append("       enableYearlySubUp();");
            out.append("   }");
            out.append("   else if (syear=='week') {");
            out.append("       enableYearlySubDown();");
            out.append("   }");
            out.append("}");

            out.append("function IsValidTime(field) {");
            out.append("    var timeStr = field.value;");
            out.append("    if (timeStr!='') {");
            out.append("       var timePat = /^(\\d{1,2}):(\\d{2})(:(\\d{2}))?(\\s?(AM|am|PM|pm))?$/;");
            out.append("       var matchArray = timeStr.match(timePat);");
            out.append("       var ok = 'yes';");
            out.append("       if (matchArray != null) {");
            out.append("           hour = matchArray[1];");
            out.append("           minute = matchArray[2];");
            out.append("           second = matchArray[4];");
            out.append("           ampm = matchArray[6];");
            out.append("           if (second=='') { second = null; }");
            out.append("           if (ampm=='') { ampm = null }");
            out.append("           if (hour < 0  || hour > 23) {");
            out.append("           alert('" + paramRequest.getLocaleString("jsHourAlert") + "');");
            out.append("           ok = 'no';");
            out.append("       }");
            out.append("       if  (hour > 12 && ampm != null) {");
            out.append("           alert('" + paramRequest.getLocaleString("jsNotAMPM") + "');");
            out.append("           ok = 'no';");
            out.append("       }");
            out.append("       if (minute<0 || minute > 59) {");
            out.append("           alert ('" + paramRequest.getLocaleString("jsMinutes") + "');");
            out.append("           ok = 'no';");
            out.append("       }");
            out.append("       if (second != null && (second < 0 || second > 59)) {");
            out.append("           alert ('" + paramRequest.getLocaleString("jsSeconds") + "');");
            out.append("           ok = 'no';");
            out.append("       }");
            out.append("    }");
            out.append("    else{");
            out.append("        alert('" + paramRequest.getLocaleString("jsBadFormat") + "');");
            out.append("        ok = 'no';");
            out.append("    }");
            out.append("    if (ok == 'no') {");
            out.append("       field.focus();");
            out.append("       field.select();");
            out.append("    }");
            out.append("   }");
            out.append("}");

            out.append("function selectCombo(obj,id) {");
            out.append("   obj.selectedIndex=id;");
            out.append("}");

            out.append("function enableHidden() {");
            out.append("   var _f=document.calendar;");
            out.append("   _f.id.disabled=false;");
            out.append("   _f.type.disabled=false;");
            out.append("   _f.tp.disabled=false;");
            out.append("   _f.tm.disabled=false;");
            out.append("   _f.view.disabled=false;");
            out.append("   _f.oldtitle.disabled=false;");
            out.append("}");

            out.append("function checkRadio(obj, val) {");
            out.append("   for(i=0;i<obj.length;i++)");
            out.append("       if(obj[i].value==val) {");
            out.append("           obj[i].checked=true");
            out.append("   }");
            out.append("}");

            out.append("function isFilled() {");
            out.append("var _f=document.calendar;");
            out.append("if (_f.title.value=='') {");
            out.append("   alert ('" + paramRequest.getLocaleString("jsEmptyField") + "');");
            out.append("   _f.title.focus();");
            out.append("   return false;");
            out.append("   }");
            out.append("else");
            out.append("   return true;");
            out.append("}");

            out.append("function validateForm() {");
            out.append("var _f=document.calendar;");
            out.append("   for (i=0; i<_f.elements.length; i++ ) { ");
            out.append("       if(_f.elements[i]!=undefined && _f.elements[i].disabled!=true) {");
            out.append("           if(_f.elements[i].type==\"text\" || _f.elements[i].type==\"textarea\") {");
            out.append("               if (_f.elements[i].value=='') {");
            out.append("                   alert ('" + paramRequest.getLocaleString("jsEmptyField") + "');");
            out.append("                   _f.elements[i].focus();");
            out.append("                   return false;");
            out.append("               }");
            out.append("           }");
            out.append("       }");
            out.append("   }");
            out.append("   return true;");
            out.append("}");
            out.append("</script>");
        } catch (Exception e) {
            log.error("Error on method getJavaScript() resource" + " " + strRscType + " " + "with id" + " " + getResourceBase().getId(), e);
        }
        return out.toString();
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

