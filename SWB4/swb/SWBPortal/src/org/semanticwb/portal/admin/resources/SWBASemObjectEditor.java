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
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBASemObjectEditor.
 * 
 * @author juan.fernandez
 */
public class SWBASemObjectEditor extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBASemObjectEditor.class);
    
    /** The MOD e_ id request. */
    static String MODE_IdREQUEST = "FORMID";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        log.debug("doView(SWBASemObjectEditor...)");
        doEdit(request, response, paramRequest);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        log.debug("doEdit(SWBASemObjectEditor...)");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String idp = request.getParameter("sprop");
        String rid = request.getParameter("rsuri");
        String action = request.getParameter("act");

        if (id == null) {
            id = paramRequest.getWebPage().getWebSiteId();
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

        // elementos de dojo requeridos

//        String path = "http://" + request.getServerName() + ":" + request.getServerPort() + SWBPlatform.getContextPath();

//        log.debug("Ruta para importar dojo: "+path);
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
            String tmpName = getDisplaySemObj(obj);
            log.debug("label: " + title + ", name: " + tmpName);
            out.println("<form action=\"" + url + "\" method=\"get\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + obj.getURI() + "\">");
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
                        out.println("		<li><label for=\"" + name + "\">" + label + " <em>*</em></label> <input type=\"checkbox\"  id=\"" + name + "\" name=\"" + name + "\" value=\"" + value + "\" " + (value != null && value.equals("true") ? "checked" : "") + "/></li>"); // 
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
                        tmpName = getDisplaySemObj(obj2);
                        if (modificable) {
                            out.println("<a href=\"" + urle + "\" >" + tmpName + "</a>");
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
                            out.println("<a  href=\"" + urlr + "\">Remove</a>");
                        }
                        WebSite ws = paramRequest.getWebPage().getWebSite();
                        WebPage page0 = ws.getWebPage("page0");
                        Resource portlet = ws.getResource("3");
                        SWBResourceURLImp urltest = (SWBResourceURLImp) paramRequest.getRenderUrl();
                        urltest.setResourceBase(portlet);
                        urltest.setTopic(page0);
                        urltest.setParameter("suri", obj.getURI());
                        urltest.setParameter("sprop", prop.getURI());
                        urltest.setParameter("spropref", obj2.getURI());
                        //urltest.setParameter(name, prop.getURI());
                        urltest.setAction("list");
                        if (modificable) {
                            out.println("<a  href=\"" + urltest + "\">Lista</a>");
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
                        out.println("<a  href=\"" + urlc + "\">Choose</a>");
                        SWBResourceURL urla = paramRequest.getActionUrl();
                        urla.setParameter("suri", obj.getURI());
                        urla.setParameter("sprop", prop.getURI());
                        if (rid != null) {
                            urlc.setParameter("rsuri", rid);
                        }
                        urla.setAction("new");
                        out.println("<a  href=\"" + urla + "\">Add New</a>");
                    }
                    
                }
            }
            out.println("	</ol>");
            out.println("</fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            urlBack.setParameter("suri", rid);
            urlBack.setParameter("act", "");
            out.println("<p><input type=\"submit\" value=\"Guardar\" />");
            if (rid != null && !id.equals(rid)) {
                out.println("<input type=\"button\" value=\"regresar\" onclick=\"window.location='" + urlBack + "';\" />");
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
            out.println("	<legend> Choose - " + prop.getDisplayName(user.getLanguage()) + " ( " + getDisplaySemObj(obj) + " )</legend>");
            out.println("	<ol>");
            Iterator<SemanticObject> itso = obj.getModel().listInstancesOfClass(clsprop);
            while (itso.hasNext()) {
                SemanticObject sobj = itso.next();
                String stitle = getDisplaySemObj(sobj);
                out.println("<li><label for=\"" + stitle + "\">" + stitle + " <em>*</em></label> ");
                if (hmSO.get(sobj.getURI()) == null) {
                    SWBResourceURL urlchoose = paramRequest.getActionUrl();
                    urlchoose.setAction("choose");
                    urlchoose.setParameter("suri", obj.getURI());
                    urlchoose.setParameter("sprop", prop.getURI());
                    urlchoose.setParameter("sobj", sobj.getURI());
                    out.println("<a href=\"" + urlchoose + "\">" + stitle + "</a>");
                } else {
                    out.println(stitle);
                }
                out.println("</li>");
            }
            out.println("	</ol>");
            out.println("</fieldset>");
            SWBResourceURL urlBack = paramRequest.getRenderUrl();
            urlBack.setParameter("suri", rid);
            urlBack.setParameter("act", "");
            out.println("<p><input type=\"submit\" value=\"Guardar\" />");
            if (!id.equals(rid)) {
                out.println("<input type=\"button\" value=\"regresar\" onclick=\"window.location='" + urlBack + "';\" />");
            }
            out.println("</p>");
            out.println("</form>");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
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
                    log.debug("SWBASemObjectEditor: ProcessAction(update): "+prop.getName()+" -- >"+value);
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
            String id_usr_request = request.getParameter("id_usr_request");
            log.debug("id_recibido: "+id_usr_request);
            if (ncls.isAutogenId() || (id_usr_request != null && id_usr_request.trim().length() > 0)) {
                long lid = obj.getModel().getCounter(ncls);
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
            if (rid != null)response.setRenderParameter("rsuri", rid);
        
        } else if ("choose".equals(action)) //suri, prop
        {
            log.debug("processAction(choose)");
            String suri = request.getParameter("suri");
            String sprop = request.getParameter("sprop");
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
    // Posiblemente se utilize para cargar datos para DOJO con JSON
//    public void getData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        PrintWriter out = response.getWriter();
//        String id = request.getParameter("suri");
//
//    }
    /**
     * Gets the display sem obj.
     * 
     * @param obj the obj
     * @return the display sem obj
     * @return
     */
    public String getDisplaySemObj(SemanticObject obj) {
        String ret = obj.getRDFName();
        try {
            ret = obj.getDisplayName();
        } catch (Exception e) {
            ret = obj.getProperty(Descriptiveable.swb_title);
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

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(MODE_IdREQUEST)) {
            doFormID(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }
}
