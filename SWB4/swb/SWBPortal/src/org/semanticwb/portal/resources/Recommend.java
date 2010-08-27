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
import java.util.Enumeration;
import java.util.Iterator;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


// TODO: Auto-generated Javadoc
/**
 * Despliega y administra recomendaciones de usuarios
 * finales bajo ciertos criterios (configuraci&oacute;n del recurso).
 *
 * Object that is in charge to unfold and to administer recommendations of end 
 * users under certain criteria (resource configuration).
 *
 * @author : Vanessa Arredondo N&uacute;&ntilde;ez
 * @version 1.0
 */
public class Recommend extends GenericAdmResource {


    /**
     * objeto encargado de crear mensajes en los archivos log de SemanticWebBuilder (SWB).
     * <p>object that creates messages in SWB's log file.</p>
     */
    private static Logger log = SWBUtils.getLogger(Recommend.class);

    /**
     * la plantilla XSLT para generar la vista de los resultados de la consulta
     * de base de datos. <p>the XSLT template that generates the database query
     * results' view.</p>
     */
    javax.xml.transform.Templates tpl;

    /**
     * el nombre de la carpeta de trabajo general. <p>the name for the general work directory</p>
     */
    String webWorkPath = "/work";
    private static final String _FAIL = "failure";

    /**
     * el nombre de la clase de este recurso. <p>this resource's class name</p>
     */
    final String name = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);

    /**
     * la ruta parcial del directorio en que se encuentra la plantilla XSLT por defecto
     * <p>the partial path for the directory in which the default XSLT template is stored.</p>
     */
    String path = SWBPlatform.getContextPath() + "/swbadmin/xsl/";


    /** 
     * Creates a new instance of Recommend.
     */
    public Recommend() {
    }

    /**
     * Asocia la informaci&oacute;n indicada por el usuario en la vista de
     * administraci&oacute;n a la clase de este recurso manteni&eacute;ndola en memoria.
     * <p>Associates the data indicated by the user in the administration
     * view to this resource's class keeping it in memory.</p>
     * @param base    un {@code resource} con la informaci&oacute;n seleccionada en
     *                la vista de administraci&oacute;n de este recurso
     *                <p>a resource with the information selected in this
     *                resource's administration view</p>
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPortal.getWebWorkPath() + base.getWorkPath();
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }
        if (!"".equals(base.getAttribute("template", "").trim())) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(
                        SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
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

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException {
        Resource base = getResourceBase();
        String action = response.getAction();
        if(response.Action_ADD.equals(action)){
            boolean hasCaptcha = Boolean.parseBoolean(base.getAttribute("captcha"));

            String securCodeSent = request.getParameter("cmnt_seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            if( (hasCaptcha && securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent)) || !hasCaptcha ) {
                try {
                    processEmails(request, response);
//                    try {
//                        feedCommentLog(request, response);
//                    }catch (IOException ioe) {
//                        log.error("Error in resource Comment, while trying to log the action. ", ioe);
//                    }
                    response.setMode(response.Mode_HELP);
                }catch(TransformerException te) {
                    log.error("Error in resource Comment, while trying to send the email. ", te);
                    response.setRenderParameter(_FAIL, te.getMessage());
                }catch(SWBResourceException re) {
                    log.error("Error in resource Comment, while trying to send the email. ", re);
                    response.setRenderParameter(_FAIL, re.getMessage());
                }catch(Exception e) {
                    log.error("Error in resource Comment, while trying to send the email. ", e);
                    response.setRenderParameter(_FAIL, e.getMessage());
                }
            }else {
                Enumeration e = request.getParameterNames();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    response.setRenderParameter(key, request.getParameter(key));
                }
            }
        }
    }

    /**
     * Genera un {@code document} con la estructura de informaci&oacute;n definida
     * para la vista de administraci&oacute;n. <p>Generates a {@code document}
     * with the data structure defined for the administration view.</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object generated by SWB and asociated to the user's request</p>
     * @return el DOM generado con la informaci&oacute;n a mostrar the
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     * <p>if there is no file message of the corresponding language.</p>
     * @throws IOException si la llamada al m&eacute;todo {@code getDomEmail} la propaga.
     * <p>if it is propagated by the call to method {@code getDomEmail}.</p>
     * {@code document} generated with the information to show.</p>
     */
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        String action = (null != request.getParameter("rec_act") && !"".equals(request.getParameter("rec_act").trim()))
//                ? request.getParameter("rec_act").trim()
//                : "rec_step2";
        Resource base = getResourceBase();
        try {
            Document dom = SWBUtils.XML.getNewDocument();
//            if ("rec_step3".equals(action)) {
//                dom = getDomEmail(request, response, paramRequest); // Envia correo
//            }else { // Nueva ventana con formulario
                User user = paramRequest.getUser();

//                SWBResourceURLImp url = new SWBResourceURLImp(request, base, paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
//                url.setResourceBase(base);
//                url.setMode(SWBResourceURLImp.Mode_VIEW);
//                url.setWindowState(SWBResourceURLImp.WinState_MAXIMIZED);
//                url.setParameter("rec_act", "rec_step3");
//                url.setTopic(paramRequest.getWebPage());
//                url.setCallMethod(paramRequest.Call_DIRECT);

                SWBResourceURL url = paramRequest.getActionUrl().setAction(paramRequest.Action_ADD);

                Element el = dom.createElement("form");
                el.setAttribute("path", SWBPlatform.getContextPath() + "/swbadmin/css/");
                el.setAttribute("accion", url.toString());
                el.setAttribute("from", paramRequest.getLocaleString("msgFrom"));
                el.setAttribute("to", paramRequest.getLocaleString("msgTo"));

                el.setAttribute("styleClass", base.getAttribute("styleClass", "").equals("") ? "<div>" : "<div class=\"" + base.getAttribute("styleClass", "") + "\">");
                el.setAttribute("styleClassClose", "</div>");

                dom.appendChild(el);

                el = dom.createElement("msgRecommend");
                el.appendChild(dom.createTextNode(paramRequest.getLocaleString("msgRecommend")));
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("labelSender");
                el.appendChild(dom.createTextNode(paramRequest.getLocaleString("msgSender")));
                dom.getChildNodes().item(0).appendChild(el);
                el = dom.createElement("ftextsender");
                el.setAttribute("tag", paramRequest.getLocaleString("msgSender"));
                el.setAttribute("inname", "txtFromName");
                if(user.isSigned()) {
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
                el.setAttribute("tag", paramRequest.getLocaleString("msgSenderEmail"));
                el.setAttribute("inname", "txtFromEmail");
                if (user.isSigned()) {
                    String strFromEmail = (null != user.getEmail() && !"".equals(user.getEmail().trim()))
                            ? user.getEmail().trim()
                            : "";
                    el.setAttribute("invalue", strFromEmail);
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("labelReceiver");
                el.appendChild(dom.createTextNode(paramRequest.getLocaleString("msgReceiver")));
                dom.getChildNodes().item(0).appendChild(el);
                el = dom.createElement("ftextreceiver");
                el.setAttribute("tag", paramRequest.getLocaleString("msgReceiver"));
                el.setAttribute("inname", "txtToName");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextreceiver");
                el.setAttribute("tag", paramRequest.getLocaleString("msgReceiverEmail"));
                el.setAttribute("inname", "txtToEmail");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextarea");
                el.setAttribute("tag", paramRequest.getLocaleString("msgMessage"));
                el.setAttribute("inname", "tarMsg");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("fsubmit");
                if(!"".equals(base.getAttribute("imgenviar", "").trim())) {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imgenviar").trim());
                    if(!"".equals(base.getAttribute("altenviar", "").trim())) {
                        el.setAttribute("alt",
                                base.getAttribute("altenviar").trim());
                    }else {
                        el.setAttribute("alt",
                                paramRequest.getLocaleString("msgRecommend"));
                    }
                }else {
                    el.setAttribute("img", "0");
                    if(!"".equals(base.getAttribute("btnenviar", "").trim())) {
                        el.setAttribute("tag",
                                base.getAttribute("btnenviar").trim());
                    }else {
                        el.setAttribute("tag",
                                paramRequest.getLocaleString("btnSubmit"));
                    }
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("freset");
                if(!"".equals(base.getAttribute("imglimpiar", "").trim())) {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imglimpiar").trim());
                    if(!"".equals(base.getAttribute("altlimpiar", "").trim())) {
                        el.setAttribute("alt", base.getAttribute("altlimpiar").trim());
                    }else {
                        el.setAttribute("alt", paramRequest.getLocaleString("btnReset"));
                    }
                }else {
                    el.setAttribute("img", "0");
                    if(!"".equals(base.getAttribute("btnlimpiar", "").trim())) {
                        el.setAttribute("tag", base.getAttribute("btnlimpiar").trim());
                    }else {
                        el.setAttribute("tag", paramRequest.getLocaleString("btnReset"));
                    }
                }
                dom.getChildNodes().item(0).appendChild(el);
//            }
            return dom;
        }catch (SWBResourceException swbe) {
            throw swbe;
        }catch (Exception e) {
            log.error("Error while generating DOM in resource " + base.getResourceType().getResourceClassName() + " with identifier " + base.getId() + " - " + base.getTitle(), e);
        }
        return null;
    }

    /**
     * Genera un {@code document} con la informaci&oacute;n que se mostrar&aacute;
     * en el correo a enviar. <p>Generates a {@code document} with the data the
     * sending e-mail is going to show.</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object generated by SWB and asociated to the user's request</p>
     * @return el {@code document} generado con la informaci&oacute;n a mostrar
     * en el correo the {@code document} generated with the data the
     * sending e-mail is going to show.
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     * <p>if there is no file message of the corresponding language.</p>
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Document getDomEmail(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        try {
            String strFromEmail = (null != request.getParameter("txtFromEmail") && !"".equals(request.getParameter("txtFromEmail").trim()))
                    ? request.getParameter("txtFromEmail").trim()
                    : null;
            String strToEmail = (null != request.getParameter("txtToEmail") && !"".equals(request.getParameter("txtToEmail").trim()))
                    ? request.getParameter("txtToEmail").trim()
                    : null;

            if(strFromEmail != null && strToEmail != null) {
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
                String strSubject = (!"".equals(base.getAttribute("subject", "").trim())
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

                addElem(dom, emn, "msgRecommend", paramRequest.getLocaleString("msgRecommend"));
                addElem(dom, emn, "msgToMessage", paramRequest.getLocaleString("msgToMessage") + ' ');
                addElem(dom, emn, "msgFromMessage", paramRequest.getLocaleString("msgFromMessage") + ' ');
                addElem(dom, emn, "msgBodyMessage", ' ' + paramRequest.getLocaleString("msgBodyMessage") + ' ');
                addElem(dom, emn, "msgFooterMessage", paramRequest.getLocaleString("msgFooterMessage"));
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
                if(!"".equals(base.getAttribute("headermsg", "").trim())) {
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
                    addElem(dom, emn, "footermsg", base.getAttribute("footermsg").trim());
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
            }else {
                throw new SWBResourceException(
                        "Error Missing Data. The following data fields are required: "
                        + "\n\t email account of the sender: " + strFromEmail
                        + "\n\t email account of the receiver: " + strToEmail);
            }
        } catch (SWBResourceException swbe) {
            throw swbe;
        } catch (Exception e) {
            log.error("Error while generating email message in resource "
                    + base.getResourceType().getResourceClassName()
                    + " with identifier " + base.getId() + " - "
                    + base.getTitle(), e);
        }
        return null;
    }

    /**
     * Muestra la liga o la pantalla de captura de recomendaciones en base al valor
     * del par&aacute;metro <code>rec_act</code> en el <code>request</code> del usuario.
     * <p>Shows the recommendations' capture link or screen depending on the
     * <code>rec_act</code> parameter's value through the user's request.</p>
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramRequest el objeto generado por SWB y asociado a la petici&oacute;n
     *        del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @throws IOException al obtener el <code>Writer</code> del <code>response</code> correspondiente.
     *         when getting the corresponding <code>response</code>'s <code>Writer</code>.
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     *         <p>if there is no file message of the corresponding language.</p>
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();

        if( paramRequest.getCallMethod()==paramRequest.Call_STRATEGY ) {
            String surl = paramRequest.getWebPage().getUrl();
            Iterator<Resourceable> res = base.listResourceables();
            while(res.hasNext()) {
                Resourceable re = res.next();
                if( re instanceof WebPage ) {
                    surl = ((WebPage)re).getUrl();
                    break;
                }
            }

            if( base.getAttribute("lnktexto")!=null ) {
                out.println("<a href=\""+surl+"\" class=\"swb-recommend-stgy\" title=\""+paramRequest.getLocaleString("msgRecommend")+"\">"+base.getAttribute("lnktexto")+"</a>");
            }else if( base.getAttribute("btntexto")!=null ) {
                out.println("<form method=\"post\" action=\""+surl+"\" class=\"swb-recommend-stgy\" >");
                out.println("<input type=\"submit\" value=\""+base.getAttribute("btntexto").trim().replaceAll("\"","&#34;")+"\" />");
                out.println("</form>");
            }else if( base.getAttribute("img")!=null ) {
                out.println("<a href=\""+surl+"\" class=\"swb-recommend-stgy\" title=\""+paramRequest.getLocaleString("msgRecommend")+"\">");
                out.println("<img src=\""+webWorkPath+base.getAttribute("img")+"\" alt=\""+base.getAttribute("alt",paramRequest.getLocaleString("msgRecommend"))+"\" class=\"swb-rcmd-stg-img\" />");
                out.println("</a>");
            }else {
                out.println("<div class=\"swb-comment\">");
                out.println("<a href=\""+surl+"\" class=\"swb-recommend-stgy\" title=\""+paramRequest.getLocaleString("msgRecommend")+"\">"+paramRequest.getLocaleString("msgRecommend")+"</a>");
                out.println("</div>");
            }
        }else {
            if( request.getParameter(_FAIL)!=null ) {
                out.println("<script type=\"text/javascript\">");
                out.println("<!--");
                out.println("alert('");
                out.println(paramRequest.getLocaleString("msgSenderRequired"));
                out.println("');");
                out.println("history.go(-1);");
                out.println("-->");
                out.println("</script>");
            }else {
                boolean hasCaptcha = Boolean.parseBoolean(base.getAttribute("captcha"));
                Document dom = getDom(request, response, paramRequest);
                String html;
                try {
                    html = SWBUtils.XML.transformDom(tpl, dom);
//                    if(hasCaptcha) {
//                        String captcha = (getCaptchaScript(paramRequest));
//                        html = html.replaceFirst("captcha", captcha);
//                        html = html + getScript(paramRequest);
//                    }else
//                        html = html.replaceFirst("captcha", "&nbsp;");
                }catch(TransformerException te) {
                    html = te.getMessage();
                    log.error(te.getMessage());
                }
                out.println(html);
            }
        }
    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if( paramRequest.getCallMethod()==paramRequest.Call_CONTENT ) {
            response.setContentType("text/html; charset=ISO-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");

            PrintWriter out = response.getWriter();
            out.println("<div class=\"swb-recomend\"><p class=\"swb-comment-tnks\">");
            out.println(paramRequest.getLocaleString("msgSendEmail"));
            out.println("</p><p>");
            out.println("<a class=\"swb-recomend-othr\" href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"\">"+paramRequest.getLocaleString("msgDoViewAnotherMsg")+"</a>");
            out.println("</p></div>");
            out.flush();
            out.close();
        }else {
            doView(request, response, paramRequest);
        }
    }

    private void  processEmails(HttpServletRequest request, SWBActionResponse response) throws TransformerException, SWBResourceException, Exception {
        Document dom = getDomEmail(request, response);

        String from = dom.getElementsByTagName("fromemail").item(0).getFirstChild().getNodeValue();
        if( from==null )
            throw new Exception(response.getLocaleString("msgErrCustomerEmailRequired"));

        String fromname = dom.getElementsByTagName("fromname").item(0).getFirstChild().getNodeValue();
        if( fromname==null )
            throw new Exception(response.getLocaleString("msgErrCustomerNameRequired"));

        String to = dom.getElementsByTagName("toemail").item(0).getFirstChild().getNodeValue();
        if( to==null )
            throw new Exception(response.getLocaleString("msgErrManagerEmailRequired"));

        String subject = dom.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue();
        if( subject==null )
            throw new Exception(response.getLocaleString("msgErrSubjectRequired"));

        String message = dom.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
        if( message==null )
            throw new Exception(response.getLocaleString("msgErrMessageRequired"));

        String html = SWBUtils.XML.transformDom(tpl, dom);

        InternetAddress iaddress = new InternetAddress();
        iaddress.setAddress(to);
        ArrayList<InternetAddress> addresses = new ArrayList<InternetAddress>();
        addresses.add(iaddress);
        if( SWBUtils.EMAIL.sendMail(from, fromname, addresses, null, null, subject, "HTML", html, null, null, null)==null )
            throw new Exception(response.getLocaleString("msgErrSending"));
    }

    public Document getDomEmail(HttpServletRequest request, SWBActionResponse paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        try {
            String strFromEmail = (null != request.getParameter("txtFromEmail") && !"".equals(request.getParameter("txtFromEmail").trim()))
                    ? request.getParameter("txtFromEmail").trim()
                    : null;
            String strToEmail = (null != request.getParameter("txtToEmail") && !"".equals(request.getParameter("txtToEmail").trim()))
                    ? request.getParameter("txtToEmail").trim()
                    : null;

            if(strFromEmail != null && strToEmail != null) {
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
                String strSubject = (!"".equals(base.getAttribute("subject", "").trim())
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

                addElem(dom, emn, "msgRecommend", paramRequest.getLocaleString("msgRecommend"));
                addElem(dom, emn, "msgToMessage", paramRequest.getLocaleString("msgToMessage") + ' ');
                addElem(dom, emn, "msgFromMessage", paramRequest.getLocaleString("msgFromMessage") + ' ');
                addElem(dom, emn, "msgBodyMessage", ' ' + paramRequest.getLocaleString("msgBodyMessage") + ' ');
                addElem(dom, emn, "msgFooterMessage", paramRequest.getLocaleString("msgFooterMessage"));
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
                if(!"".equals(base.getAttribute("headermsg", "").trim())) {
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
                    addElem(dom, emn, "footermsg", base.getAttribute("footermsg").trim());
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
            }else {
                throw new SWBResourceException(
                        "Error Missing Data. The following data fields are required: "
                        + "\n\t email account of the sender: " + strFromEmail
                        + "\n\t email account of the receiver: " + strToEmail);
            }
        } catch (SWBResourceException swbe) {
            throw swbe;
        } catch (Exception e) {
            log.error("Error while generating email message in resource "
                    + base.getResourceType().getResourceClassName()
                    + " with identifier " + base.getId() + " - "
                    + base.getTitle(), e);
        }
        return null;
    }

    private String getCaptchaScript(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"swb-coment-imagen\"> \n");
        html.append("  <p><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" alt=\"\" id=\"imgseccode\" width=\"155\" height=\"65\" /></p> \n");
        html.append("  <p><a href=\"#\" onclick=\"changeSecureCodeImage('imgseccode');\">"+paramRequest.getLocaleString("lblDoViewAnotherCode")+"</a></p> \n");
        html.append("</div> \n");
        html.append("<div class=\"swb-coment-captcha\"> \n");
        html.append("  <p><label for=\"cmnt_seccode\">"+paramRequest.getLocaleString("lblDoViewImageIs")+":</label></p> \n");
        html.append("  <p><input type=\"text\" id=\"cmnt_seccode\" name=\"cmnt_seccode\" /></p> \n");
        html.append("</div> \n");
        return html.toString();
    }

    /**
     * Agrega un elemento a un DOM, como hijo del elemento indicado, con el nombre y valor especificados.
     * <p>Adds an element to a DOM, as child of the specified element with the name and the value received.
     * @param doc el documento a modificar <p>the <code>document</code> to modify.</p>
     * @param parent el elemento padre del elemento a agregar, contenido en el documento <p>the new <code>element</code>'s parent <code>element</code>.</p>
     * @param elemName la cadena con el nombre del nuevo elemento <p>the string with the new <code>element</code>'s name.
     * @param elemValue el valor del nuevo elemento <p>new <code>element</code>'s value.</p>
     */
    private void addElem(Document doc, Element parent, String elemName, String elemValue) {

        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }

    /**
     * Agrega la informaci&oacute;n enviada por correo al archivo log de este
     * recurso. <p>Adds the data sent by e-mailto this resource's log file.</p>
     * 
     * @param dom <code>document</code> que contiene los datos enviados por correo.
     * <p>the {@code document} which contains the data sent by e-mail.</p>
     * @param user el <code>user</code> que ejecuta la acci&oacute;n de env&iacute;o
     * de recomendaci&oacute;n <p>the {@code user} which executes the
     * action of sending the recommendation</p>
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void feedRecommendLog(Document dom, User user) throws IOException {
        Resource base = getResourceBase();
        String logPath = SWBPortal.getWorkPath() + base.getWorkPath() + "/Recommend.log";
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

        File file = new File(SWBPortal.getWorkPath() + base.getWorkPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        SWBUtils.IO.log2File(logPath, toLog.toString());
    }
}