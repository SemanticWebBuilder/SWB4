/**  
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
**/ 
 
/*
 * Recommend.java
 *
 * Created on 14 de octubre de 2002, 11:02 AM
 */
package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/** Objeto que se encarga de desplegar y administrar recomendaciones de usuarios 
 * finales bajo ciertos criterios(configuraci�n de recurso).
 *
 * Object that is in charge to unfold and to administer recommendations of end 
 * users under certain criteria (resource configuration).
 *
 * @author : Vanessa Arredondo N��ez
 * @version 1.0
 */
public class Recommend extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(Recommend.class);
    javax.xml.transform.Templates tpl;
    String webWorkPath = "/work";
    String name = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
    String path = SWBPlatform.getContextPath() + "/swbadmin/xsl/";

    /** 
     * Creates a new instance of Recomendar. 
     */
    public Recommend() {
    }

    /**
     * @param base
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() + base.getWorkPath();
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }
        if (!"".equals(base.getAttribute("template", "").trim())) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(
                        SWBPlatform.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
                path = webWorkPath + "/";
            } catch (Exception e) {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
        }
        if (tpl == null) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(
                        SWBPortal.getAdminFileStream("/swbadmin/xsl/" + name + "/" + name + ".xslt"));
            } catch (Exception e) {
                log.error("Error while loading default resource template: " + base.getId(), e);
            }
        }
    }

    /**
     * @param request
     * @param response
     * @param reqParams
     * @return <b>Document</b>
     * @throws AFException
     * @throws IOException
     */
    public Document getDom(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {

        String action = (null != request.getParameter("rec_act") && !"".equals(request.getParameter("rec_act").trim()))
                ? request.getParameter("rec_act").trim()
                : "rec_step2";
        Resource base = getResourceBase();
        try {
            Document dom = SWBUtils.XML.getNewDocument();
            if ("rec_step3".equals(action)) {
                dom = getDomEmail(request, response, paramRequest); // Envia correo
            } else { // Nueva ventana con formulario
                User user = paramRequest.getUser();
                SWBResourceURLImp url = new SWBResourceURLImp(request, base,
                        paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode(SWBResourceURLImp.Mode_VIEW);
                url.setWindowState(SWBResourceURLImp.WinState_MAXIMIZED);
                url.setParameter("rec_act", "rec_step3");
                url.setTopic(paramRequest.getWebPage());
                url.setCallMethod(paramRequest.Call_DIRECT);

                Element el = dom.createElement("form");
                el.setAttribute("path",
                        SWBPlatform.getContextPath() + "/swbadmin/css/");
                el.setAttribute("accion", url.toString());
                el.setAttribute("from", paramRequest.getLocaleString("msgFrom"));
                el.setAttribute("to", paramRequest.getLocaleString("msgTo"));

                el.setAttribute("styleClass",
                        base.getAttribute("styleClass", "").equals("")
                        ? "<div>"
                        : "<div class=\"" + base.getAttribute("styleClass", "") + "\">");
                el.setAttribute("styleClassClose", "</div>");

                dom.appendChild(el);

                el = dom.createElement("msgRecommend");
                el.appendChild(dom.createTextNode(paramRequest.getLocaleString(
                        "msgRecommend")));
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("labelSender");
                el.appendChild(dom.createTextNode(paramRequest.getLocaleString(
                        "msgSender")));
                dom.getChildNodes().item(0).appendChild(el);
                el = dom.createElement("ftextsender");
                el.setAttribute("tag", paramRequest.getLocaleString("msgSender"));
                el.setAttribute("inname", "txtFromName");
                if (user.isSigned()) {
                    String strFromName = (null != user.getFirstName() && !"".equals(user.getFirstName().trim()))
                            ? user.getFirstName().trim()
                            : "";
                    strFromName += (null != user.getLastName() && !"".equals(user.getLastName().trim()))
                            ? " " + user.getLastName().trim()
                            : "";
                    strFromName += (null != user.getSecondLastName() && !"".equals(user.getSecondLastName().trim()))
                            ? " " + user.getSecondLastName().trim()
                            : "";
                    el.setAttribute("invalue", strFromName);
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextsender");
                el.setAttribute("tag",
                        paramRequest.getLocaleString("msgSenderEmail"));
                el.setAttribute("inname", "txtFromEmail");
                if (user.isSigned()) {
                    String strFromEmail = (null != user.getEmail() && !"".equals(user.getEmail().trim()))
                            ? user.getEmail().trim()
                            : "";
                    el.setAttribute("invalue", strFromEmail);
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("labelReceiver");
                el.appendChild(dom.createTextNode(paramRequest.getLocaleString(
                        "msgReceiver")));
                dom.getChildNodes().item(0).appendChild(el);
                el = dom.createElement("ftextreceiver");
                el.setAttribute("tag",
                        paramRequest.getLocaleString("msgReceiver"));
                el.setAttribute("inname", "txtToName");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextreceiver");
                el.setAttribute("tag",
                        paramRequest.getLocaleString("msgReceiverEmail"));
                el.setAttribute("inname", "txtToEmail");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextarea");
                el.setAttribute("tag", paramRequest.getLocaleString("msgMessage"));
                el.setAttribute("inname", "tarMsg");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("fsubmit");
                if (!"".equals(base.getAttribute("imgenviar", "").trim())) {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imgenviar").trim());
                    if (!"".equals(base.getAttribute("altenviar", "").trim())) {
                        el.setAttribute("alt",
                                base.getAttribute("altenviar").trim());
                    } else {
                        el.setAttribute("alt",
                                paramRequest.getLocaleString("msgRecommend"));
                    }
                } else {
                    el.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnenviar", "").trim())) {
                        el.setAttribute("tag",
                                base.getAttribute("btnenviar").trim());
                    } else {
                        el.setAttribute("tag",
                                paramRequest.getLocaleString("btnSubmit"));
                    }
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("freset");
                if (!"".equals(base.getAttribute("imglimpiar", "").trim())) {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imglimpiar").trim());
                    if (!"".equals(base.getAttribute("altlimpiar", "").trim())) {
                        el.setAttribute("alt",
                                base.getAttribute("altlimpiar").trim());
                    } else {
                        el.setAttribute("alt",
                                paramRequest.getLocaleString("btnReset"));
                    }
                } else {
                    el.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnlimpiar", "").trim())) {
                        el.setAttribute("tag",
                                base.getAttribute("btnlimpiar").trim());
                    } else {
                        el.setAttribute("tag",
                                paramRequest.getLocaleString("btnReset"));
                    }
                }
                dom.getChildNodes().item(0).appendChild(el);
            }
            return dom;
        } catch (Exception e) {
            log.error("Error while generating DOM in resource " + base.getResourceType().getResourceClassName() + " with identifier " + base.getId() + " - " + base.getTitle(), e);
        }
        return null;
    }

    /*
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
    String action = null != request.getParameter("rec_act") && !"".equals(request.getParameter("rec_act").trim()) ? request.getParameter("rec_act").trim() : "rec_step2";
    Resource base=getResourceBase();
    try
    {
    Document  dom = SWBUtils.XML.getNewDocument();
    if("rec_step3".equals(action)) dom=getDomEmail(request, response, paramRequest); // Envia correo
    else
    { // Nueva ventana con formulario
    User user=paramRequest.getUser();
    SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getWebPage(),SWBResourceURL.UrlType_RENDER);
    url.setResourceBase(base);
    url.setMode(url.Mode_VIEW);
    url.setWindowState(url.WinState_MAXIMIZED);
    url.setParameter("rec_act","rec_step3");
    url.setTopic(paramRequest.getWebPage());
    url.setCallMethod(paramRequest.Call_DIRECT);

    Element el = dom.createElement("form");
    el.setAttribute("path", path);
    el.setAttribute("accion", url.toString());
    dom.appendChild(el);

    el = dom.createElement("ftext");
    el.setAttribute("tag", paramRequest.getLocaleString("msgSender"));
    el.setAttribute("inname", "txtFromName");
    if (user.isSigned())
    {
    String strFromName = null != user.getUsrFirstName() && !"".equals(user.getUsrFirstName().trim()) ? user.getUsrFirstName().trim() : "";
    strFromName+= null != user.getUsrLastName() && !"".equals(user.getUsrLastName().trim()) ? " " + user.getUsrLastName().trim() : "";
    strFromName+= null != user.getUsrSecondLastName() && !"".equals(user.getUsrSecondLastName().trim()) ? " " + user.getUsrSecondLastName().trim() : "";
    el.setAttribute("invalue", strFromName);
    }
    dom.getChildNodes().item(0).appendChild(el);

    el = dom.createElement("ftext");
    el.setAttribute("tag", paramRequest.getLocaleString("msgSenderEmail"));
    el.setAttribute("inname", "txtFromEmail");
    if (user.isSigned())
    {
    String strFromEmail = null != user.getUsrEmail() && !"".equals(user.getUsrEmail().trim()) ? user.getUsrEmail().trim() : "";
    el.setAttribute("invalue", strFromEmail);
    }
    dom.getChildNodes().item(0).appendChild(el);

    el = dom.createElement("ftext");
    el.setAttribute("tag", paramRequest.getLocaleString("msgReceiver"));
    el.setAttribute("inname", "txtToName");
    dom.getChildNodes().item(0).appendChild(el);

    el = dom.createElement("ftext");
    el.setAttribute("tag", paramRequest.getLocaleString("msgReceiverEmail"));
    el.setAttribute("inname", "txtToEmail");
    dom.getChildNodes().item(0).appendChild(el);

    el = dom.createElement("ftextarea");
    el.setAttribute("tag", paramRequest.getLocaleString("msgMessage"));
    el.setAttribute("inname", "tarMsg");
    dom.getChildNodes().item(0).appendChild(el);

    el = dom.createElement("fsubmit");
    if (!"".equals(base.getAttribute("imgenviar", "").trim()))
    {
    el.setAttribute("img", "1");
    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imgenviar").trim());
    if (!"".equals(base.getAttribute("altenviar", "").trim())) el.setAttribute("alt", base.getAttribute("altenviar").trim());
    else el.setAttribute("alt", paramRequest.getLocaleString("msgRecommend"));
    }
    else
    {
    el.setAttribute("img", "0");
    if (!"".equals(base.getAttribute("btnenviar", "").trim())) el.setAttribute("tag", base.getAttribute("btnenviar").trim());
    else el.setAttribute("tag", paramRequest.getLocaleString("btnSubmit"));
    }
    dom.getChildNodes().item(0).appendChild(el);

    el = dom.createElement("freset");
    if (!"".equals(base.getAttribute("imglimpiar", "").trim()))
    {
    el.setAttribute("img", "1");
    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imglimpiar").trim());
    if (!"".equals(base.getAttribute("altlimpiar", "").trim())) el.setAttribute("alt", base.getAttribute("altlimpiar").trim());
    else el.setAttribute("alt", paramRequest.getLocaleString("btnReset"));
    }
    else
    {
    el.setAttribute("img", "0");
    if (!"".equals(base.getAttribute("btnlimpiar", "").trim())) el.setAttribute("tag", base.getAttribute("btnlimpiar").trim());
    else el.setAttribute("tag", paramRequest.getLocaleString("btnReset"));
    }
    dom.getChildNodes().item(0).appendChild(el);
    }
    return dom;
    }
    catch (Exception e) { log.error("Error while generating DOM in resource "+ base.getResourceType().getResourceClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
    return null;
    }
     **/
    /**
     * @param request
     * @param response
     * @param reqParams
     * @return <b>Document</b>
     * @throws AFException
     * @throws IOException
     */
    public Document getDomEmail(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {

        Resource base = getResourceBase();
        try {
            String strFromEmail = (null != request.getParameter("txtFromEmail") && !"".equals(request.getParameter("txtFromEmail").trim()))
                    ? request.getParameter("txtFromEmail").trim()
                    : null;
            String strToEmail = (null != request.getParameter("txtToEmail") && !"".equals(request.getParameter("txtToEmail").trim()))
                    ? request.getParameter("txtToEmail").trim()
                    : null;

            if (strFromEmail != null && strToEmail != null) {
                Document dom = SWBUtils.XML.getNewDocument();
                String strFromName = (null != request.getParameter("txtFromName") && !"".equals(request.getParameter("txtFromName").trim()))
                        ? request.getParameter("txtFromName").trim()
                        : null;
                String strToName = (null != request.getParameter("txtToName") && !"".equals(request.getParameter("txtToName").trim()))
                        ? request.getParameter("txtToName").trim()
                        : null;
                String strTarMsg = (null != request.getParameter("tarMsg") && !"".equals(request.getParameter("tarMsg").trim()))
                        ? request.getParameter("tarMsg").trim()
                        : null;
                String strSubject = (!"".equals(base.getAttribute("subject",
                        "").trim())
                        ? base.getAttribute("subject").trim()
                        : paramRequest.getLocaleString("msgSubject"));
                String strUrl = "http://" + request.getServerName() + (request.getServerPort() != 80
                        ? ":" + request.getServerPort()
                        : "");
                WebPage topic = paramRequest.getWebPage();
                String lang = paramRequest.getUser().getLanguage();

                Element emn = dom.createElement("form");
                emn.setAttribute("path", "http://" + request.getServerName() + (request.getServerPort() != 80
                        ? ":" + request.getServerPort()
                        : "") + SWBPlatform.getContextPath() + "/swbadmin/css/");
                emn.setAttribute("email", "1");

                emn.setAttribute("styleClass",
                        base.getAttribute("styleClass", "").equals("")
                        ? "<div>"
                        : "<div class=\"" + base.getAttribute("styleClass", "") + "\">");
                emn.setAttribute("styleClassClose", "</div>");

                dom.appendChild(emn);

                addElem(dom, emn, "msgRecommend",
                        paramRequest.getLocaleString("msgRecommend"));
                addElem(dom, emn, "msgToMessage",
                        paramRequest.getLocaleString("msgToMessage") + ' ');
                addElem(dom, emn, "msgFromMessage",
                        paramRequest.getLocaleString("msgFromMessage") + ' ');
                addElem(dom, emn, "msgBodyMessage",
                        ' ' + paramRequest.getLocaleString("msgBodyMessage") + ' ');
                addElem(dom, emn, "msgFooterMessage",
                        paramRequest.getLocaleString("msgFooterMessage"));

                addElem(dom, emn, "site", topic.getWebSiteId());
                addElem(dom, emn, "siteurl", strUrl);
                addElem(dom, emn, "topic",
                        topic.getTitle(lang) != null
                        ? topic.getTitle(lang) : "Sin título");
                addElem(dom, emn, "topicurl", strUrl + topic.getUrl());

                if (strFromName != null) {
                    addElem(dom, emn, "fromname", strFromName);
                }
                addElem(dom, emn, "fromemail", strFromEmail);
                if (strToName != null) {
                    addElem(dom, emn, "toname", strToName);
                }
                addElem(dom, emn, "toemail", strToEmail);
                addElem(dom, emn, "subject", strSubject);
                if (strTarMsg != null) {
                    addElem(dom, emn, "message", strTarMsg);
                }

                String strHeadermsg = "<br> \n";
                strHeadermsg += "----------------------------------------------------------------------<br> \n";
                strHeadermsg += paramRequest.getLocaleString("msgHeaderMessage") + "<br> \n";
                strHeadermsg += "----------------------------------------------------------------------<br> \n";
                if (!"".equals(base.getAttribute("headermsg", "").trim())) {
                    addElem(dom, emn, "headermsg", base.getAttribute("headermsg").trim());
                    strHeadermsg += "<br>" + base.getAttribute("headermsg").trim() + "<br><br> \n";
                }
                strHeadermsg += "<br> \n";
                strHeadermsg += " " + paramRequest.getLocaleString("msgToMessage") + " ";
                strHeadermsg += null != strToName ? "<I>" + strToName + "</I>" : "";
                strHeadermsg += ",<br><br> \n";
                strHeadermsg += " " + paramRequest.getLocaleString("msgFromMessage") + " ";
                strHeadermsg += null != strFromName ? "<I>" + strFromName + "</I>" : "";
                strHeadermsg += " " + paramRequest.getLocaleString("msgBodyMessage") + "<br> \n";
                strHeadermsg += " <a href=\"" + strUrl + topic.getUrl() + "\">";
                strHeadermsg += topic.getTitle(lang) != null
                        ? topic.getTitle(lang) : "Sin título";
                strHeadermsg += "</a> \n";
                if (strTarMsg != null) {
                    strHeadermsg += "<br><br> \n";
                }
                String strFootermsg = "";
                if (!"".equals(base.getAttribute("footermsg", "").trim())) {
                    addElem(dom, emn, "footermsg",
                            base.getAttribute("footermsg").trim());
                    strFootermsg += "<br><br><br>" + base.getAttribute("footermsg").trim() + " \n";
                }
                strFootermsg += "<br><br> \n";
                strFootermsg += "----------------------------------------------------------------------<br> \n";
                strFootermsg += " " + paramRequest.getLocaleString("msgFooterMessage") + "<br> \n";
                strFootermsg += " <a href=\"" + strUrl + "\">" + topic.getWebSiteId() + "</a> \n";
                strFootermsg += "<br><br> \n";
                addElem(dom, emn, "emailbody",
                        strHeadermsg + strTarMsg + strFootermsg);
                return dom;
            } else {
                throw new SWBResourceException(
                        "Error Missing Data. The following data fields are required: " + "\n\t email account of the sender: " + strFromEmail + "\n\t email account of the receiver: " + strToEmail);
            }
        } catch (Exception e) {
            log.error("Error while generating email message in resource " + base.getResourceType().getResourceClassName() + " with identifier " + base.getId() + " - " + base.getTitle(), e);
        }
        return null;
    }

    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException,
            IOException {

        response.setContentType("text/html");
        StringBuffer ret = new StringBuffer(200);
        Resource base = getResourceBase();
        String action = (null != request.getParameter("rec_act") && !"".equals(request.getParameter("rec_act").trim()))
                ? request.getParameter("rec_act").trim()
                : "rec_step1";
        if ("rec_step1".equals(action)) {
            // Objeto (imagen/botón) para invocar la nueva ventana con formulario
            String onclick = "";
            if ("1".equals(base.getAttribute("menubar", "0").trim())) {
                onclick = "menubar=yes";
            } else {
                onclick = "menubar=no";
            }
            if ("1".equals(base.getAttribute("toolbar", "0").trim())) {
                onclick += ",toolbar=yes";
            } else {
                onclick += ",toolbar=no";
            }
            if ("1".equals(base.getAttribute("status", "0").trim())) {
                onclick += ",status=yes";
            } else {
                onclick += ",status=no";
            }
            if ("1".equals(base.getAttribute("location", "0").trim())) {
                onclick += ",location=yes";
            } else {
                onclick += ",location=no";
            }
            if ("1".equals(base.getAttribute("directories", "0").trim())) {
                onclick += ",directories=yes";
            } else {
                onclick += ",directories=no";
            }
            if ("1".equals(base.getAttribute("scrollbars", "0").trim())) {
                onclick += ",scrollbars=yes";
            } else {
                onclick += ",scrollbars=no";
            }
            if ("1".equals(base.getAttribute("resizable", "0").trim())) {
                onclick += ",resizable=yes";
            } else {
                onclick += ",resizable=no";
            }
            onclick += ",width=" + base.getAttribute("width", "450").trim();
            onclick += ",height=" + base.getAttribute("height", "400").trim();
            onclick += ",top=" + base.getAttribute("top", "10").trim();
            onclick += ",left=" + base.getAttribute("left", "10").trim();

            SWBResourceURLImp url = new SWBResourceURLImp(request, base,
                    paramRequest.getWebPage(), SWBResourceURLImp.UrlType_RENDER);
            url.setResourceBase(base);
            url.setMode(SWBResourceURLImp.Mode_VIEW);
            url.setWindowState(SWBResourceURLImp.WinState_MAXIMIZED);
            url.setParameter("rec_act", "rec_step2");
            url.setTopic(paramRequest.getWebPage());
            url.setCallMethod(paramRequest.Call_DIRECT);
            onclick = "javascript:window.open('" + url.toString() + "','_newrec','" + onclick + "'); return false;";

            synchronized (ret) {
                if (!"".equals(base.getAttribute("img", "").trim())) {
                    ret.append("\n<a href=\"" + onclick + "\"><img onClick=\"" + onclick + "\" src=\"");
                    ret.append(webWorkPath + "/" + base.getAttribute("img").trim() + "\"");
                    if (!"".equals(base.getAttribute("alt", "").trim())) {
                        ret.append(" alt=\"" + base.getAttribute("alt").trim().replaceAll("\"",
                                "&#34;") + "\"");
                    }
                    ret.append(" border=0></a>");
                } else if (!"".equals(base.getAttribute("btntexto", "").trim())) {
                    ret.append("\n<form name=frmRecomendar>");
                    ret.append("\n<input type=button name=btnRecomendar onClick=\"" + onclick + "\" value=");
                    ret.append("\"" + base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;") + "\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                        ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;") + "\"");
                    }
                    ret.append("\n></form>");
                } else {
                    ret.append("\n<a href=\"" + onclick + "\" onClick=\"" + onclick + "\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                        ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll(
                                "\"", "&#34;") + "\"");
                    }
                    ret.append(">");
                    if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                        ret.append(base.getAttribute("lnktexto").trim());
                    } else {
                        ret.append(paramRequest.getLocaleString("msgRecommend"));
                    }
                    ret.append("</a>");
                }
            }
        } else {
            try {
                Document dom = getDom(request, response, paramRequest);
                String generateLog = base.getAttribute("generatelog", "");
                if (dom != null) {
                    ret.append(SWBUtils.XML.transformDom(tpl, dom));
                    if ("rec_step3".equals(action)) {
                        String from = dom.getElementsByTagName("fromemail").item(0).getFirstChild().getNodeValue();
                        String to = dom.getElementsByTagName("toemail").item(0).getFirstChild().getNodeValue();
                        String subject = dom.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue();
                        boolean mailSent = false;
                        String strUrl = "=\"http://" + request.getServerName() + (request.getServerPort() != 80
                                ? ":" + request.getServerPort()
                                : "") + path;
                        ret = new StringBuffer(SWBUtils.TEXT.replaceAll(
                                ret.toString(), "=\"" + path, strUrl));

                        InternetAddress address1 = new InternetAddress();
                        address1.setAddress(to);
                        ArrayList<InternetAddress> aAddress =
                                new ArrayList<InternetAddress>();
                        aAddress.add(address1);

                        if ((from != null && to != null && subject != null) && SWBUtils.EMAIL.sendMail(from, from, aAddress,
                                null, null, subject, "html", ret.toString(),
                                null, null, null) != null) {
                            ret.append("\n<script>");
                            ret.append("\nalert('" + paramRequest.getLocaleString("msgSendEmail") + "');");
                            ret.append("\nwindow.close();");
                            ret.append("\n</script>");
                            mailSent = true;
                        } else {
                            ret.append("\n<script>");
                            ret.append("\nalert('" + paramRequest.getLocaleString("msgEmailRequired") + "');");
                            ret.append("\nhistory.go(-1);");
                            ret.append("\n</script>");
                        }
                        if (mailSent && generateLog.length() > 0) {
                            try {
                                feedRecommendLog(dom, paramRequest.getUser());
                            } catch (Exception e) {
                                log.error(e);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        PrintWriter out = response.getWriter();
        out.println(ret.toString());
    }

    /**
     * @param doc
     * @param parent
     * @param elemName
     * @param elemValue
     */
    private void addElem(Document doc, Element parent, String elemName,
            String elemValue) {

        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }

    /**
     * Agrega la informaci&oacute;n enviada por correo al archivo log de este
     * recurso.
     * @param dom <code>Document</code> que contiene los datos enviados por correo.
     * @param user <code>User</code> que identifica al usuario que ejecuta la 
     *             acci&oacute;n de env&iacute;o de comentario sobre el recurso
     */
    protected void feedRecommendLog(Document dom, User user) throws IOException {

        Resource base = getResourceBase();
        String logPath = SWBPlatform.getWorkPath() + base.getWorkPath() + "/Recommend.log";
        StringBuilder toLog = new StringBuilder(500);
        Date now = new Date();
        NodeList nl = null;

        toLog.append(SWBUtils.TEXT.iso8601DateFormat(now));
        if (user != null && user.isSigned()) {
            toLog.append("\n    User:");
            toLog.append((null != user.getFirstName() && !"".equals(user.getFirstName().trim()))
                    ? user.getFirstName().trim()
                    : "");
            toLog.append((null != user.getLastName() && !"".equals(user.getLastName().trim()))
                    ? user.getLastName().trim()
                    : "");
            toLog.append((null != user.getSecondLastName() && !"".equals(user.getSecondLastName().trim()))
                    ? user.getSecondLastName().trim()
                    : "");
        }
        nl = dom.getElementsByTagName("site");
        toLog.append("\n    Site:" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : ""));
        nl = dom.getElementsByTagName("topic");
        toLog.append("\n    Topic:" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : ""));
        nl = dom.getElementsByTagName("topicurl");
        toLog.append("<" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : "") + ">");
        nl = dom.getElementsByTagName("fromname");
        toLog.append("\n    From:" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : ""));
        nl = dom.getElementsByTagName("fromemail");
        if (nl != null && nl.getLength() > 0) {
            toLog.append("<" + nl.item(0).getFirstChild().getNodeValue() + ">");
        }
        nl = dom.getElementsByTagName("toname");
        toLog.append("\n    To:" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : ""));
        nl = dom.getElementsByTagName("toemail");
        toLog.append("<" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : "") + ">");
        nl = dom.getElementsByTagName("subject");
        toLog.append("\n    Subject:" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : ""));
        nl = dom.getElementsByTagName("message");
        toLog.append("\n    Message:" + (nl != null && nl.getLength() > 0
                ? nl.item(0).getFirstChild().getNodeValue() : ""));
        toLog.append("\n");

        File file = new File(SWBPlatform.getWorkPath() + base.getWorkPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        SWBUtils.IO.log2File(logPath, toLog.toString());
    }
}
