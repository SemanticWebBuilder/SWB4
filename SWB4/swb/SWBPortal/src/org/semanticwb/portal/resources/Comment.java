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
package org.semanticwb.portal.resources;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.portal.util.FileUpload;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.semanticwb.portal.api.SWBActionResponse;

// TODO: Auto-generated Javadoc
/**
 * Presenta la interfaz para el env&iacute;o de comentarios al sitio en la categor&iacute;a
 * seleccionada por el usuario. Este recurso cuenta con facilidades de administraci&oacute;n
 * que permiten indicar categor&iacute;as de comentarios y destinatarios a cada categor&iacute;a,
 * as&iacute; como el estilo del mecanismo que presenta la interfaz de captura del comentario.
 *
 * Displays the interface needed to send comments of a selected category. This resource's
 * administration screen allows the creation of comment categories and a recipient for each
 * category; likewise it allows the selection of characteristics to present the link to the
 * capture interface
 *
 * @author : Vanessa Arredondo N&uacute;&ntilde;ez
 * @version 1.0
 */
public class Comment extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Comment.class);

    /** The web work path. */
    private static String webWorkPath;

    /** The path. */
    private static String path;

    /** The Constant _FAIL. */
    private static final String _FAIL = "failure";

    /**
     * Contiene la definici&oacute;n del layout para la interfaz de captura de comentarios.
     * <p>Contains the layout definition for the commentaries capture interface.</p>
     */
    private javax.xml.transform.Templates tpl = null;


    /**
     * Creates a new instance of Comment.
     */
    public Comment() {
    }

    /**
     * Fija la plantilla del layout para la interfaz de captura de comentarios y
     * la mantiene en memoria.<p>Sets this resource layout template for the commentaries
     * capture interface and keeps it in memory.</p>
     * @param base el objeto base para la creaci&oacute;n de este recurso.
     *            <p>the base object for this resource's creation.</p>
     */
    @Override
    public void setResourceBase(Resource base) {
        String name = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
        try {
            super.setResourceBase(base);
            webWorkPath = SWBPortal.getWebWorkPath()+base.getWorkPath() + "/";
            path = SWBPlatform.getContextPath() + "/swbadmin/xsl/" + name + "/";
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }

        if( !"".equals(base.getAttribute("template", "").trim()) ) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT( SWBPortal.getFileFromWorkPath(base.getWorkPath()+"/"+base.getAttribute("template").trim()) );
                path = webWorkPath + "/";
            } catch (Exception e) {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
        }
        if( tpl==null ) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT( SWBPortal.getAdminFileStream("/swbadmin/xsl/" + name + "/" + name + ".xsl"));
            } catch (Exception e) {
                log.error("Error while loading default resource template: " + base.getId(), e);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(HttpServletRequest, SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException {
        Resource base = getResourceBase();
        String action = response.getAction();
        if(SWBActionResponse.Action_ADD.equals(action)){
            boolean hasCaptcha = Boolean.parseBoolean(base.getAttribute("captcha"));

            String securCodeSent = request.getParameter("cmnt_seccode");
            String securCodeCreated = (String)request.getSession(true).getAttribute("cs");
            if( (hasCaptcha && securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent)) || !hasCaptcha ) {
                try {
                    processEmails(request, response);
                    try {
                        feedCommentLog(request, response);
                    }catch (IOException ioe) {
                        log.error("Error in resource Comment, while trying to log the action. ", ioe);
                    }
                    response.setMode(SWBActionResponse.Mode_HELP);
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
     * Obtiene los datos de la configuraci&oacute;n del recurso, sin tomar en
     * cuenta aquellos para el env&iacute;o del correo y genera un objeto
     * <code>Document</code> con ellos. <p>Gets this resource's configuration data (whithout
     * those to send e-mails) and generates a <code>Document</code> object with it</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramRequest the param request
     * @return  estructura de la interfaz de captura de comentarios.
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     * <p>if there is no file message of the corresponding language.</p>
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        String action = ((null != request.getParameter("com_act")) && (!"".equals(request.getParameter("com_act").trim()))
//                         ? request.getParameter("com_act").trim()
//                         : "com_step2");

        Resource base = getResourceBase();

        try {
            Document dom = SWBUtils.XML.getNewDocument();

//            if ("com_step3".equals(action)) {
//                dom = getDomEmail(request, response, paramRequest); // Envia correo
//            }else {
                // Nueva ventana con formulario
                User user = paramRequest.getUser();

//                SWBResourceURLImp url = new SWBResourceURLImp(request, base,
//                paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
//                url.setResourceBase(base);
//                url.setMode(SWBResourceURLImp.Mode_VIEW);
//                /*url.setWindowState(SWBResourceURLImp.WinState_MAXIMIZED);*/
//                url.setParameter("com_act", "com_step3");
//                url.setTopic(paramRequest.getWebPage());
//                /*url.setCallMethod(reqParams.Call_DIRECT);*/
                SWBResourceURL url = paramRequest.getActionUrl().setAction(paramRequest.Action_ADD);

                Element root = dom.createElement("form");
                root.setAttribute("path", SWBPlatform.getContextPath() + "/swbadmin/css/");
                root.setAttribute("accion", url.toString());
                root.setAttribute("styleClass", base.getAttribute("styleClass", "").equals("") ? "<div>" : "<div class=\"" + base.getAttribute("styleClass", "") + "\">");
                root.setAttribute("styleClassClose", "</div>");
                dom.appendChild(root);
                Element emn = null;

                emn = dom.createElement("msgComments");
                emn.appendChild(dom.createTextNode(paramRequest.getLocaleString("msgComments")));
                root.appendChild(emn);
                if (!"".equals(base.getAttribute("comentario", "").trim())) {
                    emn = dom.createElement("fselect");
                    emn.setAttribute("tag",
                            paramRequest.getLocaleString("msgViewTypeComment"));
                    emn.setAttribute("inname", "selTipo");

                    if (base.getDom() != null) {
                        NodeList node = base.getDom().getElementsByTagName(
                                "comentario");
                        String comentario = "";
                        for (int i = 0; i < node.getLength(); i++) {
                            comentario = node.item(i).getChildNodes().item(
                                    0).getNodeValue().trim();
                            if (!"".equals(comentario)) {
                                Element child = dom.createElement("foption");
                                child.setAttribute("invalue", comentario);
                                emn.appendChild(child);
                            }
                        }
                    }
                    root.appendChild(emn);
                }

                
                if (user.isSigned()) {
                    emn = dom.createElement("ftext");
                    emn.setAttribute("tag", paramRequest.getLocaleString("msgFirstName"));
                    emn.setAttribute("inname", "txtFromName");
                    String strFromName = ("1".equals(
                            base.getAttribute("firstname", "0").trim())
                            && (null != user.getFirstName()
                                && !"".equals(user.getFirstName().trim()))
                            ? user.getFirstName().trim()
                            : "");
                    strFromName += ("1".equals(
                            base.getAttribute("lastname", "0").trim())
                            && (null != user.getLastName()
                                && !"".equals(user.getLastName().trim()))
                            ? " " + user.getLastName().trim()
                            : "");
                    strFromName += ("1".equals(
                            base.getAttribute("middlename", "0").trim())
                            && (null != user.getSecondLastName()
                                && !"".equals(user.getSecondLastName().trim()))
                            ? " " + user.getSecondLastName().trim()
                            : "");
                    emn.setAttribute("invalue", strFromName);
                    root.appendChild(emn);
                }else {
                    if("1".equals(base.getAttribute("firstname", "0"))) {
                        emn = dom.createElement("ftext");
                        emn.setAttribute("tag", paramRequest.getLocaleString("msgFirstName"));
                        emn.setAttribute("inname", "txtFromName");
                        root.appendChild(emn);
                    }
                    if("1".equals(base.getAttribute("lastname", "0"))) {
                        emn = dom.createElement("ftext");
                        emn.setAttribute("tag", paramRequest.getLocaleString("msgLastName"));
                        emn.setAttribute("inname", "txtFromLName");
                        root.appendChild(emn);
                    }
                    if("1".equals(base.getAttribute("middlename", "0"))) {
                        emn = dom.createElement("ftext");
                        emn.setAttribute("tag", paramRequest.getLocaleString("msgMiddleName"));
                        emn.setAttribute("inname", "txtFromSLName");
                        root.appendChild(emn);
                    }
                }
                
                emn = dom.createElement("ftext");
                emn.setAttribute("tag", paramRequest.getLocaleString("msgViewEmail"));
                emn.setAttribute("inname", "txtFromEmail");
                if (user.isSigned()) {
                    String strFromEmail = (null != user.getEmail()
                            && !"".equals(user.getEmail().trim())
                            ? user.getEmail().trim()
                            : "");
                    emn.setAttribute("invalue", strFromEmail);
                }
                root.appendChild(emn);

                emn = dom.createElement("ftext");
                emn.setAttribute("tag", paramRequest.getLocaleString("msgPhone"));
                emn.setAttribute("inname", "txtPhone");
                root.appendChild(emn);

                emn = dom.createElement("ftext");
                emn.setAttribute("tag", paramRequest.getLocaleString("msgFax"));
                emn.setAttribute("inname", "txtFax");
                root.appendChild(emn);

                emn = dom.createElement("ftextarea");
                emn.setAttribute("tag", paramRequest.getLocaleString("msgMessage"));
                emn.setAttribute("inname", "tarMsg");
                root.appendChild(emn);
                emn = dom.createElement("fsubmit");
                
                if (!"".equals(base.getAttribute("imgenviar", "").trim())) {
                    emn.setAttribute("img", "1");
                    emn.setAttribute("src", webWorkPath + base.getAttribute("imgenviar").trim());

                    if (!"".equals(base.getAttribute("altenviar", "").trim()))
                        emn.setAttribute("alt",
                                base.getAttribute("altenviar").trim());
                    else emn.setAttribute("alt",
                            paramRequest.getLocaleString("msgViewSubmitAlt"));
                } else {
                    emn.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnenviar", "").trim())) {
                        emn.setAttribute("tag",
                                base.getAttribute("btnenviar").trim());
                    } else {
                        emn.setAttribute("tag",
                                paramRequest.getLocaleString("msgViewSubmitButton"));
                    }
                }
                root.appendChild(emn);
                emn = dom.createElement("freset");

                if (!"".equals(base.getAttribute("imglimpiar", "").trim())) {
                    emn.setAttribute("img", "1");
                    emn.setAttribute("src", webWorkPath + base.getAttribute("imglimpiar").trim());

                    if (!"".equals(base.getAttribute("altlimpiar", "").trim())) {
                        emn.setAttribute("alt",
                                base.getAttribute("altlimpiar").trim());
                    } else {
                        emn.setAttribute("alt",
                                paramRequest.getLocaleString("msgViewResetAlt"));
                    }
                } else {
                    emn.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnlimpiar", "").trim())) {
                        emn.setAttribute("tag",
                                base.getAttribute("btnlimpiar").trim());
                    } else {
                        emn.setAttribute("tag",
                                paramRequest.getLocaleString("msgViewResetButton"));
                    }
                }
                root.appendChild(emn);
//            }
            return dom;
        } catch (SWBResourceException swbre) {
            throw swbre;
        } catch (org.semanticwb.SWBException e) {
            log.error("Error while generating the comments form in resource "+base.getResourceType().getResourceClassName()+" with identifier " + base.getId()+" - "+base.getTitle(), e);
        }
        return null;
    }

    /**
     * Obtiene todos los datos de la configuraci&oacute;n del recurso y genera
     * un objeto <b>Document</b> con ellos.<p>Gets all the
     * configuration data for this resource and generates a <b>Document</b> object
     * with it.</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @return  informaci&oacute;n de un comentario para su env&iacute;o.
     * @throws SWBResourceException si el nombre del remitente, su correo y mensaje
     * son nulos. <p>if sender's name, e-mail account and message is null.</p>
     */
    public Document getDomEmail(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException {
        Resource base = getResourceBase();

        try {
            String strFromName = (null != request.getParameter("txtFromName")
                    && !"".equals(request.getParameter("txtFromName").trim())
                    ? request.getParameter("txtFromName").trim()
                    : null);
            String strFromEmail = (null != request.getParameter("txtFromEmail")
                    && !"".equals(request.getParameter("txtFromEmail").trim())
                    ? request.getParameter("txtFromEmail").trim()
                    : null);
            String strTarMsg = (null != request.getParameter("tarMsg")
                    && !"".equals(request.getParameter("tarMsg").trim())
                    ? request.getParameter("tarMsg").trim()
                    : null);

            if (strFromName != null && strFromEmail != null && strTarMsg != null) {
                Document  dom = SWBUtils.XML.getNewDocument();
                String strSubject = response.getLocaleString("emlSubject");
                String commentType = (null != request.getParameter("selTipo")
                        && !"".equals(request.getParameter("selTipo").trim())
                        ? request.getParameter("selTipo").trim()
                        : strSubject);

                if (!"".equals(base.getAttribute("subject", "").trim())) {
                    strSubject = base.getAttribute("subject").trim();
                } else if (commentType != null ) {
                    strSubject = commentType + "...";
                }

                WebPage topic = response.getWebPage();
                String lang = response.getUser().getLanguage();
                Element emn = dom.createElement("form");
                emn.setAttribute("path", "http://" + request.getServerName()
                        + (request.getServerPort() != 80
                        ? ":" + request.getServerPort()
                        : "") + SWBPlatform.getContextPath() + "/swbadmin/css/");
                emn.setAttribute("email", "1");
                emn.setAttribute("styleClass",
                        base.getAttribute("styleClass", "").equals("")
                        ? ""
                        : " class=\"" + base.getAttribute("styleClass", "") + "\"");
                emn.setAttribute("styleClassClose", "</div>");
                dom.appendChild(emn);
                addElem(dom, emn, "site", topic.getWebSiteId());
                addElem(dom, emn, "topic",
                        topic.getTitle(lang) != null
                        ? topic.getTitle(lang) : "Sin título");
                addElem(dom, emn, "fromname", strFromName);
                addElem(dom, emn, "fromemail", strFromEmail);
                addElem(dom, emn, "subject", strSubject);
                addElem(dom, emn, "message", strTarMsg);
                addElem(dom, emn, "msgTo", response.getLocaleString("msgTo"));
                addElem(dom, emn, "msgName", response.getLocaleString("msgName"));
                addElem(dom, emn, "msgViewEmail",
                        response.getLocaleString("msgViewEmail"));
                addElem(dom, emn, "msgPhone", response.getLocaleString("msgPhone"));
                addElem(dom, emn, "msgFax", response.getLocaleString("msgFax"));
                addElem(dom, emn, "msgMessage",
                        response.getLocaleString("msgMessage"));

                String value = "";
                String area = "";
                String responsable = "";
                String toemail = "";

                if (commentType != null ) {
                    addElem(dom, emn, "commenttype", commentType);
                    Document dom2 = base.getDom();
                    NodeList node = dom2.getElementsByTagName("comentario");

                    for (int i = 0; i < node.getLength(); i++) {
                        area = "";
                        responsable = "";
                        toemail = "";
                        value = node.item(i).getFirstChild().getNodeValue().trim();

                        if (value.equals(commentType)) {
                            NodeList child = node.item(i).getChildNodes();

                            for (int j = 0; j < child.getLength(); j++) {
                                if (child.item(j) != null) {
                                    if ("area".equals(child.item(j).getNodeName())) {
                                        area = child.item(j).getFirstChild(
                                                ).getNodeValue().trim();
                                        continue;
                                    } else if ("responsable".equals(child.item(
                                            j).getNodeName())) {
                                        responsable = child.item(j).getFirstChild(
                                                ).getNodeValue().trim();
                                        continue;
                                    } else if ("email".equals(child.item(
                                            j).getNodeName())) {
                                        toemail = child.item(j).getFirstChild(
                                                ).getNodeValue().trim();
                                        continue;
                                    }
                                }
                            }
                            break;
                        }
                    }

                    if (!"".equals(area)) {
                        addElem(dom, emn, "area", area);
                    }

                    if (!"".equals(responsable)) {
                        addElem(dom, emn, "responsable", responsable);
                    }

                    if (!"".equals(toemail)) {
                        addElem(dom, emn, "toemail", toemail);
                    }
                }

                if ("".equals(toemail)) {
                    addElem(dom, emn, "area", base.getAttribute("area","").trim());
                    addElem(dom, emn, "responsable",
                            base.getAttribute("responsable", "").trim());
                    addElem(dom, emn, "toemail",
                            base.getAttribute("email", "").trim());
                }

                StringBuilder strHeadermsg = new StringBuilder(800);
                strHeadermsg.append("<br> \n");
                strHeadermsg.append("----------------------------------------------------------------------<br> \n");
                strHeadermsg.append(response.getLocaleString("emlSite"));
                strHeadermsg.append(" <b>" + topic.getWebSiteId() + ".");
                strHeadermsg.append((topic.getTitle(lang) != null
                        ? topic.getTitle(lang) : "Sin título") + "</b> \n <br>");
                strHeadermsg.append(response.getLocaleString("emlCommentType"));
                strHeadermsg.append(" <b> \n");

                if (commentType != null ) {
                    strHeadermsg.append(commentType);
                } else {
                    strHeadermsg.append(response.getLocaleString(
                            "emlHeaderMessage1"));
                }
                strHeadermsg.append("</b><br> \n");
                strHeadermsg.append("----------------------------------------------------------------------<br> \n");

                if (!"".equals(base.getAttribute("headermsg", "").trim())) {
                    addElem(dom, emn, "headermsg",
                            base.getAttribute("headermsg").trim());
                    strHeadermsg.append("<br>"
                            + base.getAttribute("headermsg").trim()
                            + "<br><br> \n");
                }
                strHeadermsg.append("<br> \n");
                strHeadermsg.append("<table> \n");
                strHeadermsg.append("<tr><td colspan=2> \n");
                strHeadermsg.append(base.getAttribute("responsable", "").trim());
                strHeadermsg.append("<br>" + base.getAttribute("area", "").trim());
                strHeadermsg.append("<br><br></td></tr> \n");
                strHeadermsg.append("<tr><td><b> \n");
                strHeadermsg.append(response.getLocaleString("emlName"));
                strHeadermsg.append("</b></td><td> \n");
                strHeadermsg.append(strFromName + "</td></tr> \n");
                strHeadermsg.append("<tr><td><b>");
                strHeadermsg.append(response.getLocaleString("emlEmail"));
                strHeadermsg.append("</b></td> \n");
                strHeadermsg.append("<td>");
                strHeadermsg.append(strFromEmail + "</td></tr> \n");

                if (request.getParameter("txtPhone") != null
                        && !"".equals(request.getParameter("txtPhone").trim())) {
                    addElem(dom, emn, "phone",
                            request.getParameter("txtPhone").trim());
                    strHeadermsg.append("<tr><td><b>");
                    strHeadermsg.append(response.getLocaleString("emlPhone"));
                    strHeadermsg.append("</b></td> \n");
                    strHeadermsg.append("<td>");
                    strHeadermsg.append(request.getParameter("txtPhone").trim());
                    strHeadermsg.append("</td></tr> \n");
                }

                if (request.getParameter("txtFax") != null
                        && !"".equals(request.getParameter("txtFax").trim())) {
                    addElem(dom, emn, "fax", request.getParameter("txtFax").trim());
                    strHeadermsg.append("<tr><td><b>");
                    strHeadermsg.append(response.getLocaleString("emlFax"));
                    strHeadermsg.append("</b></td> \n");
                    strHeadermsg.append("<td>");
                    strHeadermsg.append(request.getParameter("txtFax").trim());
                    strHeadermsg.append("</td></tr> \n");
                }
                strTarMsg = "<tr><td colspan=2> \n";
                strTarMsg += ("<br><br>"
                        + response.getLocaleString("emlHeaderMessage2")
                        + " <br><br>" + request.getParameter("tarMsg").trim()
                        +" \n");
                strTarMsg += "</td></tr> \n";

                String strFootermsg = "</table> \n";

                if (!"".equals(base.getAttribute("footermsg", "").trim())) {
                    addElem(dom, emn, "footermsg",
                            base.getAttribute("footermsg").trim());
                    strFootermsg += "<br><br><br>" + base.getAttribute("footermsg").trim();
                }
                strFootermsg += "<br><br><br> \n";
                strHeadermsg.append(strTarMsg);
                strHeadermsg.append(strFootermsg);
                addElem(dom, emn, "emailbody", strHeadermsg.toString());
                return dom;
            } else {
                throw new SWBResourceException("Error Missing Data. The following data fields are required: "
                        + "\n\t sender's name: " + strFromName
                        + "\n\t email account of the sender: " + strFromEmail
                        + "\n\t email message: " + strTarMsg
                        + "\n in getDomEmail()");
            }
        } catch (Exception e) {
            log.error("Error while generating email message in resource " + base.getResourceType().getResourceClassName() + " with identifier " + base.getId() + " - " + base.getTitle(), e);
        }
        return null;
    }

    /**
     * Muestra la estructura de datos generada por <code>getDom()</code>.
     * Shows the data structure generated by <code>getDom()</code>.
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramRequest the param request
     * @throws IOException al obtener el <code>Writer</code> del <code>response</code> correspondiente.
     * when getting the corresponding <code>response</code>'s <code>Writer</code>.
     * @throws SWBResourceException si se produce en <code>getDom</code>.
     * <p>if <code>getDom</code> propagates this exception</p>.
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml; charset=ISO-8859-1");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        Resource base=getResourceBase();
        PrintWriter out = response.getWriter();
        try {
            Document dom = getDom(request, response, paramRequest);
            out.println(SWBUtils.XML.domToXml(dom));
        }catch(Exception e) {
            log.error("Error in doXML method while rendering the XML script: "+base.getId()+"-"+base.getTitle(), e);
            out.println("Error in doXML method while rendering the XML script");
        }
    }

    /**
     * Muestra la liga o la pantalla de captura de comentarios en base al valor
     * del par&aacute;metro <code>com_act</code> en el <code>request</code> del usuario.
     * <p>Shows the commentaries' capture link or screen depending on the
     * <code>com_act</code> parameter's value through the
     * user's request.</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramRequest the param request
     * @throws IOException al obtener el <code>Writer</code> del <code>response</code> correspondiente.
     * when getting the corresponding <code>response</code>'s <code>Writer</code>.
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     * <p>if there is no file message of the corresponding language.</p>
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws IOException, SWBResourceException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();

        if( paramRequest.getCallMethod()==SWBParamRequest.Call_STRATEGY ) {
            String surl = paramRequest.getWebPage().getUrl();
            Iterator<Resourceable> res = base.listResourceables();
            while(res.hasNext()) {
                Resourceable re = res.next();
                if( re instanceof WebPage ) {
                    surl = ((WebPage)re).getUrl();
                    break;
                }
            }

            if( base.getAttribute("link")!=null ) {
                out.println("<a href=\""+surl+"\" class=\"swb-comment-stgy\" title=\""+paramRequest.getLocaleString("msgComments")+"\">"+base.getAttribute("link")+"</a>");
            }else if( base.getAttribute("label")!=null ) {
                out.println("<form method=\"post\" action=\""+surl+"\" class=\"swb-comment-stgy\" >");
                out.println("<input type=\"submit\" value=\""+base.getAttribute("label")+"\" />");
                out.println("</form>");
            }else if( base.getAttribute("img")!=null ) {
                out.println("<a href=\""+surl+"\" class=\"swb-comment-stgy\" title=\""+base.getAttribute("alt",paramRequest.getLocaleString("msgComments"))+"\">");
                out.println("<img src=\""+webWorkPath+base.getAttribute("img")+"\" alt=\""+base.getAttribute("alt",paramRequest.getLocaleString("msgComments"))+"\" class=\"cmt-stg-img\" />");
                out.println("</a>");
            }else {
                out.println("<div class=\"swb-comment\">");
                out.println("<a href=\""+surl+"\" class=\"swb-comment-stgy\" title=\""+base.getAttribute("lnktexto",paramRequest.getLocaleString("msgComments"))+"\">"+base.getAttribute("lnktexto",paramRequest.getLocaleString("msgComments"))+"</a>");
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
                    if(hasCaptcha) {
                        String captcha = (getCaptchaScript(paramRequest));
                        html = html.replaceFirst("captcha", captcha);
                        html = html + getScript(paramRequest);
                    }else
                        html = html.replaceFirst("captcha", "&nbsp;");
                }catch(TransformerException te) {
                    html = te.getMessage();
                    log.error(te.getMessage());
                }
                out.println(html);
            }
        }
        out.flush();
        out.close();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doHelp(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if( paramRequest.getCallMethod()==paramRequest.Call_CONTENT ) {
            response.setContentType("text/html; charset=ISO-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");

            PrintWriter out = response.getWriter();
            out.println("<div class=\"swb-comment\"><p class=\"swb-comment-tnks\">");
            out.println(paramRequest.getLocaleString("msgSendEmail"));
            out.println("</p><p>");
            out.println("<a class=\"swb-comment-othr\" href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"\">"+paramRequest.getLocaleString("msgDoViewAnotherMsg")+"</a>");
            out.println("</p></div>");
            out.flush();
            out.close();
        }else {
            doView(request, response, paramRequest);
        }
    }

    /**
     * Process emails.
     * 
     * @param request the request
     * @param response the response
     * @throws TransformerException the transformer exception
     * @throws SWBResourceException the sWB resource exception
     * @throws Exception the exception
     */
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

    /**
     * Muestra la vista para los datos de administraci&oacute;n de este recurso
     * y realiza las operaciones de almacenamiento de informaci&oacute;n necesarias.
     * <p>Shows the data administration screen for this resource and performs the
     * data store operations needed.</p>
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     *        del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @throws IOException al obtener el <code>Writer</code> del <code>response</code> correspondiente.
     *         when getting the corresponding <code>response</code>'s <code>Writer</code>.
     * @throws SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     *         <p>if there is no file message of the corresponding language.</p>
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws IOException, SWBResourceException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        StringBuilder ret = new StringBuilder();
        Resource base = getResourceBase();
        String msg = paramsRequest.getLocaleString("msgUndefinedOperation");
        String action = ((null != request.getParameter("act"))
                && (!"".equals(request.getParameter("act").trim()))
                ? request.getParameter("act").trim()
                : paramsRequest.getAction());
        WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();

        if (action.equals("add") || action.equals("edit")) {
            ret.append(getForm(request, paramsRequest));
        } else if (action.equals("update")) {
            FileUpload fup = new FileUpload();

            try {
                fup.getFiles(request, response);
                String applet = null;
                String value = (null != fup.getValue("notmp")
                         && !"".equals(fup.getValue("notmp").trim())
                         ? fup.getValue("notmp").trim()
                         : "0");        
                if ("1".equals(value)
                        && !"".equals(base.getAttribute("img", "").trim())) {
                    SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath()
                            + base.getWorkPath() + "/"
                            + base.getAttribute("template").trim());
                    base.removeAttribute("template");
                }else {
                    value = (null != fup.getFileName("template")
                        && !"".equals(fup.getFileName("template").trim())
                        ? fup.getFileName("template").trim()
                        : null);
                    if (value != null) {
                        String file = admResUtils.getFileName(base, value);
                        if (file != null && !file.trim().equals("")) {
                            if (!admResUtils.isFileType(file, "xsl|xslt")) {
                                msg = paramsRequest.getLocaleString("msgErrFileType")
                                        +" <i>xsl, xslt</i>: " + file;
                            } else {
                                applet = admResUtils.uploadFileParsed(base, fup,
                                        "template", request.getSession().getId());
                                if (applet != null && !applet.trim().equals("")) {
                                    base.setAttribute("template", file);
                                } else {
                                    msg = paramsRequest.getLocaleString("msgErrUploadFile")
                                            + " <i>" + value + "</i>.";
                                }
                            }
                        } else {
                            msg = paramsRequest.getLocaleString("msgErrUploadFile")
                                    + " <i>" + value + "</i>.";
                        }
                    }
                }
                
                
                
                
                value = (null != fup.getValue("noimg")
                         && !"".equals(fup.getValue("noimg").trim())
                         ? fup.getValue("noimg").trim()
                         : "0");

                if ("1".equals(value)
                        && !"".equals(base.getAttribute("img", "").trim())) {
                    SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath()
                            + base.getWorkPath() + "/"
                            + base.getAttribute("img").trim());
                    base.removeAttribute("img");
                } else {
                    value = (null != fup.getFileName("img")
                             && !"".equals(fup.getFileName("img").trim())
                             ? fup.getFileName("img").trim()
                             : null);
                    if (value != null) {
                        String file = admResUtils.getFileName(base, value);
                        if (file != null && !file.trim().equals("")) {
                            if (!admResUtils.isFileType(file, "jpg|jpeg|gif|png")) {
                                msg = paramsRequest.getLocaleString("msgErrFileType")
                                        + " <i>jpg, jpeg, gif, png</i>: " + file;
                            } else {
                                if (admResUtils.uploadFile(base, fup, "img")) {
                                    base.setAttribute("img", file);
                                } else {
                                    msg = paramsRequest.getLocaleString(
                                            "msgErrUploadFile")
                                            + " <i>" + value + "</i>.";
                                }
                            }
                        } else {
                            msg = paramsRequest.getLocaleString("msgErrUploadFile")
                                    + " <i>" + value + "</i>.";
                        }
                    }
                }

                value = (null != fup.getValue("noimgenviar")
                         && !"".equals(fup.getValue("noimgenviar").trim())
                         ? fup.getValue("noimgenviar").trim()
                         : "0");
                if ("1".equals(value)
                        && !"".equals(base.getAttribute("imgenviar", "").trim())) {
                    SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath()
                            + base.getWorkPath() + "/"
                            + base.getAttribute("imgenviar").trim());
                    base.removeAttribute("imgenviar");
                } else {
                    value = (null != fup.getFileName("imgenviar")
                             && !"".equals(fup.getFileName("imgenviar").trim())
                             ? fup.getFileName("imgenviar").trim()
                             : null);
                    if (value != null) {
                        String file = admResUtils.getFileName(base, value);
                        if (file != null && !file.trim().equals("")) {
                            if (!admResUtils.isFileType(file, "jpg|jpeg|gif|png")) {
                                msg = paramsRequest.getLocaleString("msgErrFileType") + " <i>jpg, jpeg, gif, png</i>: " + file;
                            }else {
                                if (admResUtils.uploadFile(base, fup, "imgenviar")) {
                                    base.setAttribute("imgenviar", file);
                                } else {
                                    msg = paramsRequest.getLocaleString("msgErrUploadFile") + " <i>" + value + "</i>.";
                                }
                            }
                        } else {
                            msg = paramsRequest.getLocaleString("msgErrUploadFile")
                                    + " <i>" + value + "</i>.";
                        }
                    }
                }

                value = (null != fup.getValue("noimglimpiar")
                         && !"".equals(fup.getValue("noimglimpiar").trim())
                         ? fup.getValue("noimglimpiar").trim()
                         : "0");
                if ("1".equals(value)
                        && !"".equals(base.getAttribute("imglimpiar", "").trim())) {
                    SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath()
                            + base.getWorkPath() + "/"
                            + base.getAttribute("imglimpiar").trim());
                    base.removeAttribute("imglimpiar");
                } else {
                    value = (null != fup.getFileName("imglimpiar")
                             && !"".equals(fup.getFileName("imglimpiar").trim())
                             ? fup.getFileName("imglimpiar").trim()
                             : null);
                    if (value != null) {
                        String file = admResUtils.getFileName(base, value);
                        if (file != null && !file.trim().equals("")) {
                            if (!admResUtils.isFileType(file, "jpg|jpeg|gif|png")) {
                                msg = paramsRequest.getLocaleString("msgErrFileType")
                                        + " <i>jpg, jpeg, gif, png</i>: " + file;
                            } else {
                                if (admResUtils.uploadFile(base, fup, "imglimpiar")) {
                                    base.setAttribute("imglimpiar", file);
                                } else {
                                    msg = paramsRequest.getLocaleString(
                                            "msgErrUploadFile")
                                            + " <i>" + value + "</i>.";
                                }
                            }
                        } else {
                            msg = paramsRequest.getLocaleString("msgErrUploadFile")
                                    + " <i>" + value + "</i>.";
                        }
                    }
                }
                setAttribute(base, fup, "alt");
                setAttribute(base, fup, "btntexto");
                setAttribute(base, fup, "lnktexto");
//                setAttribute(base, fup, "blnstyle");
                setAttribute(base, fup, "altenviar");
                setAttribute(base, fup, "btnenviar");
                setAttribute(base, fup, "altlimpiar");
                setAttribute(base, fup, "btnlimpiar");
                setAttribute(base, fup, "firstname", "1");
                setAttribute(base, fup, "lastname", "1");
                setAttribute(base, fup, "middlename", "1");
//                setAttribute(base, fup, "styleClass");
                setAttribute(base, fup, "captcha");
//                setAttribute(base, fup, "menubar", "yes");
//                setAttribute(base, fup, "toolbar", "yes");
//                setAttribute(base, fup, "status", "yes");
//                setAttribute(base, fup, "location", "yes");
//                setAttribute(base, fup, "directories", "yes");
//                setAttribute(base, fup, "scrollbars", "yes");
//                setAttribute(base, fup, "resizable", "yes");
//                setAttribute(base, fup, "width");
//                setAttribute(base, fup, "height");
//                setAttribute(base, fup, "top");
//                setAttribute(base, fup, "left");
                setAttribute(base, fup, "subject");
                setAttribute(base, fup, "headermsg");
                setAttribute(base, fup, "footermsg");
                setAttribute(base, fup, "generatelog");

                base.updateAttributesToDB();
                Document dom=base.getDom();
                if (dom != null) {
                    removeAllNodes(dom, Node.ELEMENT_NODE, "comentario");
                } else {
                    dom = SWBUtils.XML.getNewDocument();
                    Element root = dom.createElement("resource");
                    dom.appendChild(root);
                }

                

                value = (null != fup.getValue("comentarios")
                         && !"".equals(fup.getValue("comentarios").trim())
                         ? fup.getValue("comentarios").trim()
                         : null);
                String type = (null != fup.getValue("type")
                               && !"".equals(fup.getValue("type").trim())
                               ? fup.getValue("type").trim()
                               : "");
                String actype = (null != fup.getValue("actype")
                                 && !"".equals(fup.getValue("actype").trim())
                                 ? fup.getValue("actype").trim()
                                 : "edit");

                if (value != null) {
                    StringTokenizer stk = new StringTokenizer(value, "|");
                    for (int i = 1; stk.hasMoreTokens(); i++) {
                        if ("remove".equals(actype)
                                && type.equals(String.valueOf(i))) {
                            stk.nextToken();
                            continue;
                        }
                        Element comment = dom.createElement("comentario");
                        StringTokenizer stk2 = new StringTokenizer(
                                stk.nextToken(), ";");
                        for (int j = 0; stk2.hasMoreTokens(); j++) {
                            value = stk2.nextToken();
                            String att = "comentario";
                            if (j == 1) {
                                att = "area";
                            } else if (j == 2) {
                                att = "responsable";
                            } else if (j == 3) {
                                att = "email";
                            }

                            if ("edit".equals(actype) && type.equals(String.valueOf(i))) {
                                value = (null != fup.getValue(att) && !"".equals(fup.getValue(att).trim()) ? fup.getValue(att).trim() : "");
                            }
                            if (j < 1) {
                                comment.appendChild(dom.createTextNode(value));
                            } else {
                                Element data = dom.createElement(att);
                                data.appendChild(dom.createTextNode(value));
                                comment.appendChild(data);
                            }
                        }
                        dom.getFirstChild().appendChild(comment);
                    }
                }
                base.setXml(SWBUtils.XML.domToXml(dom));
                msg = paramsRequest.getLocaleString("msgOkUpdateResource")
                        + " " + base.getId();
                if (applet != null && !"".equals(applet.trim())) {
                    ret.append(applet);
                } else {
                    ret.append("<script language=\"JavaScript\">\n"
                        + "   location='"
                        + paramsRequest.getRenderUrl().setAction("edit").toString()
                        + "';\n"
                        + "</script>\n");
                }
            } catch (Exception e) {
                log.error(e);
                msg = paramsRequest.getLocaleString("msgErrUpdateResource")
                        + " " + base.getId();
            }
            ret.append("<script language=\"JavaScript\">\n"
                + "   alert('" + msg + "');\n"
                + "</script>\n");
        } else if (action.equals("remove")) {
            msg = admResUtils.removeResource(base);
            ret.append("<script language=\"JavaScript\">\n"
                + "   alert('" + msg + "');\n"
                + "</script>\n");
        }
        response.getWriter().print(ret.toString());
    }

    /**
     * Fija un atributo en el objeto base con el nombre indicado.
     * Si el atributo no existe en el objeto <code>fup</code> o su valor es <code>null</code>,
     * el atributo <code>att</code> se elimina de <code>base</code>.
     * <p>Sets a property in this object's resource base with the specified name.
     * If the indicated attribute does not exist in <code>fup</code>, or its value
     * is <code>null</code>, the attribute is eliminated.</p>
     * @param base <code>Resource</code> en el que se fijar&aacute; el atributo.
     *        <code>Resource</code> in which the attribute is going to be set.
     * @param fup objeto del cual se obtiene el valor del atributo.
     *        object from which the attribute's value is gotten.
     * @param att contiene el nombre del atributo a fijar en <code>base</code>.
     *        contains the attribute's name.
     */
    protected void setAttribute(Resource base, FileUpload fup, String att) {
        try {            
            if (null != fup.getValue(att)
                    && !"".equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            } else {
                base.removeAttribute(att);
            }
        } catch (Exception e) {
            log.error("Error while setting resource attribute: " + att + ", "
                    + base.getId() + "-" + base.getTitle(), e);
        }
    }

    /**
     * Fija un atributo en el objeto base con el nombre y el valor indicados.
     * Si el atributo no existe en el objeto fup o tiene otro valor al indicado,
     * el atributo att se elimina de base.
     * <p>Sets a property in <code>base</code> with the indicated name and value.
     * If the indicated attribute does not exist in <code>fup</code> or has another
     * value than the specified as an argument, the attribute <code>att</code> is
     * eliminated from <code>base</code>.</p>
     * @param base <code>Resource</code> en el que se fijar&aacute; el atributo.
     *        <p><code>Resource</code> in which the attribute is going to be set.</p>
     * @param fup objeto del cual se verifica el valor del atributo.
     *        <p>contains the value of the attribute to be set.</p>
     * @param att contiene el nombre del atributo a fijar en base.
     *        <p>contains the name of the attribute to be set.</p>
     * @param value contiene el valor del atributo a fijar en base.
     *        <p>value to set in the attribute, validated against the value for the
     *        attribute in <code>fup</code>.</p>
     */
    protected void setAttribute(Resource base, FileUpload fup, String att, String value) {
        try {
            if (null != fup.getValue(att)
                    && value.equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            } else {
                base.removeAttribute(att);
            }
        } catch (Exception e) {
            log.error("Error while setting resource attribute: " + att + ", "
                    + base.getId() + "-" + base.getTitle(), e);
        }
    }

    /**
     * Elimina del dom recibido todos los elementos correspondientes al nodeType
     * y name especificados. <p>Removes from <code>dom</code> all the elements whose
     * name matches the value of <code>name</code>.</p>
     * @param dom <code>Document</code> del que se eliminar&aacute;n los nodos.
     *        <p><code>Document</code> from which the nodes are going to be removed.</p>
     * @param nodeType tipo de nodos a eliminar. <p>node type to eliminate.</p>
     * @param name nombre de los nodos a eliminar. <p>node name to search for elimination.</p>
     */
    protected void removeAllNodes(Document dom, short nodeType, String name) {
        NodeList list = dom.getElementsByTagName(name);

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == nodeType) {
                node.getParentNode().removeChild(node);
                if (node.hasChildNodes()) {
                    removeAllNodes(dom, nodeType, name);
                }
            }
        }
    }

    /**
     * Arma una cadena que contiene el c&oacute;digo HTML para mostrar la forma de los
     * datos presentados por la pantalla de administraci&oacute;n del recurso.
     * <p>Gets the HTML code for this resource's administration screen.</p>
     *
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @return el c&oacute;digo HTML a mostrar.
     *
     */
    private String getForm(HttpServletRequest request, SWBParamRequest paramsRequest) {
        String name = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
        WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();
        StringBuilder ret = new StringBuilder();
        Resource base = getResourceBase();

        try {
            SWBResourceURL url = paramsRequest.getRenderUrl().setAction("update");
            ret.append("<div class=\"swbform\">");
            ret.append("<form name=\"frmResource\" method=\"post\" enctype=\"multipart/form-data\" action=\"");
            ret.append(url.toString());
            ret.append("\"> \n");
            ret.append("<fieldset>");
            ret.append("<legend>"+paramsRequest.getLocaleString("msgStep1")+"</legend>");
            ret.append("<table>");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\" valign=\"top\">");
            ret.append(paramsRequest.getLocaleString("msgTemplate"));
            ret.append(" (xsl, xslt):</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"file\" name=\"template\" onChange=\"isFileType(this, 'xsl|xslt');\" />");

            if( !"".equals(base.getAttribute("template", "").trim()) ) {
                ret.append("<p>"+paramsRequest.getLocaleString("msgCurrentTemplate")+" <a title=\"Editar plantilla\" href=\""+ SWBPlatform.getContextPath()+"/editfile?file="+SWBPortal.getWorkPath()+ base.getWorkPath() + "/" + base.getAttribute("template") + "&pathType=res&resUri="+base.getEncodedURI()+"\">"+base.getAttribute("template")+"</a></p>");
                ret.append("<p>" + "Quitar plantilla " + " <i>" + base.getAttribute("template") + "</i> <input type=\"checkbox\" name=\"notmp\" value=\"1\"/></p>");
            } else {
                ret.append("<p>" + paramsRequest.getLocaleString("msgByDefault")+" <a title=\"Ver plantilla\" href=\""+ SWBPlatform.getContextPath()+"/showfile?file="+SWBPlatform.getContextPath() +"/swbadmin/xsl/Comment/Comment.xsl"+"&pathType=def&resUri="+base.getEncodedURI()+"&attr="+name+"\">"+name+"</a></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"+paramsRequest.getLocaleString("msgImage")+" (jpg, jpeg, gif, png):</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"file\" name=\"img\" onClick=\"this.form.btntexto.value=''; this.form.lnktexto.value=''\" onChange=\"isFileType(this, 'jpg|jpeg|gif|png');\"/>");
            if (!"".equals(base.getAttribute("img", "").trim())) {
                ret.append("<p>"+admResUtils.displayImage(base, base.getAttribute("img").trim(), "img")+" <input type=\"checkbox\" name=\"noimg\" value=\"1\"/> " + paramsRequest.getLocaleString("msgCutImage") + " <i>" + base.getAttribute("img").trim() + "</i></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"
                    + paramsRequest.getLocaleString("msgAlt") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"alt\" ");
            if (!"".equals(base.getAttribute("alt", "").trim())) {
                ret.append(" value=\""+base.getAttribute("alt").trim().replaceAll("\"","&#34;")+"\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"+paramsRequest.getLocaleString("msgButton") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"btntexto\" ");
            if (!"".equals(base.getAttribute("btntexto", "").trim())) {
                ret.append(" value=\""+base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"+paramsRequest.getLocaleString("msgLink") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"lnktexto\" ");
            if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                ret.append(" value=\"" + base.getAttribute("lnktexto").trim().replaceAll("\"", "&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"+paramsRequest.getLocaleString("msgStyle") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"blnstyle\" ");
            if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                ret.append(" value=\""+base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">" + paramsRequest.getLocaleString("msgFirstName") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"checkbox\" name=\"firstname\" value=\"1\"");
            if ("1".equals(base.getAttribute("firstname", "0"))) {
                ret.append(" checked");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">" + paramsRequest.getLocaleString("msgLastName") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"checkbox\" name=\"lastname\" value=\"1\"");
            if ("1".equals(base.getAttribute("lastname", "0"))) {
                ret.append(" checked");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">" + paramsRequest.getLocaleString("msgMiddleName") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"checkbox\" name=\"middlename\" value=\"1\"");
            if ("1".equals(base.getAttribute("middlename", "0"))) {
                ret.append(" checked");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"+paramsRequest.getLocaleString("msgSubmitImage")+" (jpg, jpeg, gif, png):</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"file\" name=\"imgenviar\" onClick=\"this.form.btnenviar.value='';\" onChange=\"isFileType(this, 'jpg|jpeg|gif|png');\"/>");

            if (!"".equals(base.getAttribute("imgenviar", "").trim())) {
                ret.append("<p>" + admResUtils.displayImage(base, base.getAttribute("imgenviar"), "imgenviar")
                        + "<input type=\"checkbox\" name=\"noimgenviar\" value=\"1\"/>"
                        + paramsRequest.getLocaleString("msgCutImage") + " <i>"
                        + base.getAttribute("imgenviar").trim() + "</i></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"
                    + paramsRequest.getLocaleString("msgSubmitAlt") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"altenviar\" ");
            if (!"".equals(base.getAttribute("altenviar", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("altenviar").trim().replaceAll("\"",
                        "&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"
                    + paramsRequest.getLocaleString("msgSubmitButton") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"btnenviar\" ");
            if (!"".equals(base.getAttribute("btnenviar", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("btnenviar").trim().replaceAll("\"",
                        "&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">" + paramsRequest.getLocaleString("msgResetImage") + " (jpg, jpeg, gif, png):</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"file\" name=\"imglimpiar\" onClick=\"this.form.btnlimpiar.value='';\" onChange=\"isFileType(this, 'jpg|jpeg|gif|png');\"/>");
            if (!"".equals(base.getAttribute("imglimpiar", "").trim())) {
                ret.append("<p>" + admResUtils.displayImage(base,
                        base.getAttribute("imglimpiar").trim(), "imglimpiar")
                        + "<input type=\"checkbox\" name=\"noimglimpiar\" value=\"1\"/>"
                        + paramsRequest.getLocaleString("msgCutImage")
                        + " <i>" + base.getAttribute("imglimpiar").trim()
                        + "</i></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"
                    + paramsRequest.getLocaleString("msgResetAlt") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"altlimpiar\" ");
            if (!"".equals(base.getAttribute("altlimpiar", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("altlimpiar").trim().replaceAll("\"",
                        "&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"
                    + paramsRequest.getLocaleString("msgResetButton") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"btnlimpiar\" ");
            if (!"".equals(base.getAttribute("btnlimpiar", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("btnlimpiar").trim().replaceAll("\"",
                        "&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");

            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"
                    + paramsRequest.getLocaleString("msgGenerateLog") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"checkbox\" name=\"generatelog\" value=\"1\"");
            if ("1".equals(base.getAttribute("generatelog", "0"))) {
                ret.append(" checked=\"checked\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");

            ret.append("<tr> \n");
            ret.append("<td align=\"right\">"
                    + paramsRequest.getLocaleString("msgStyleClass") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"styleClass\" value=\""
                    + base.getAttribute("styleClass", "") + "\"/>");
            ret.append("</td> \n");
            ret.append("</tr> \n");



            ret.append("<tr> \n");
            ret.append("<td align=\"right\">Incluir captcha</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"checkbox\" name=\"captcha\" value=\"true\"");
            if( Boolean.parseBoolean(base.getAttribute("captcha")) ) {
                ret.append(" checked=\"checked\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("</table> \n");
            ret.append("</fieldset><br />");

//            ret.append("<fieldset>");
//            ret.append("<legend>"+paramsRequest.getLocaleString("msgStep2")+"</legend>");
//            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"7\">");
//            ret.append("<tr><td width=\"40%\"></td><td width=\"60%\"></td>");
//            ret.append(admResUtils.loadWindowConfiguration(base, paramsRequest));
//            ret.append("</table> \n");
//            ret.append("</fieldset><br />");

            ret.append("<fieldset>");
            ret.append("<legend>"+paramsRequest.getLocaleString("msgStep2")+"</legend>");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"7\">");
            ret.append("<tr><td width=\"40%\"></td><td width=\"60%\"></td>");

            ret.append("<tr> \n");
            ret.append("<td align=\"right\">" + paramsRequest.getLocaleString("msgSubjectTag") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" size=\"50\" name=\"subject\" ");
            if (!"".equals(base.getAttribute("subject", "").trim())) {
                ret.append(" value=\"" + base.getAttribute("subject").trim().replaceAll("\"","&#34;") + "\"");
            }
            ret.append("/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">" + paramsRequest.getLocaleString("msgMessageHeader") + "</td> \n");
            ret.append("<td>");
            ret.append("<textarea name=headermsg>");
            if (!"".equals(base.getAttribute("headermsg", "").trim())) {
                ret.append(base.getAttribute("headermsg").trim().replaceAll("\"","&#34;"));
            }
            ret.append("</textarea></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td align=\"right\">" + paramsRequest.getLocaleString("msgMessageFooter") + "</td> \n");
            ret.append("<td>");
            ret.append("<textarea name=footermsg>");
            if (!"".equals(base.getAttribute("footermsg", "").trim())) {
                ret.append(base.getAttribute("footermsg").trim().replaceAll("\"", "&#34;"));
            }
            ret.append("</textarea></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td colspan=2>");
            ret.append("<table> \n");
            ret.append("<tr> \n");
            ret.append("<td></td> \n");
            ret.append("<td>* "+paramsRequest.getLocaleString("msgTypeComment")+"&nbsp;&nbsp;</td> \n");
            ret.append("<td>" + paramsRequest.getLocaleString("msgArea") + "&nbsp;&nbsp;</td> \n");
            ret.append("<td>" + paramsRequest.getLocaleString("msgResponsable") + "&nbsp;&nbsp;</td> \n");
            ret.append("<td>" + paramsRequest.getLocaleString("msgEmail") + "</td> \n");
            ret.append("</tr> \n");

            String comentarios = "";
            String area = "";
            String responsable = "";
            String email = "";
            int i = 0;
            Iterator it = getTypesComment(base.getDom()).iterator();
            while (it.hasNext()) {
                TypeComment tc = (TypeComment)it.next();
                i++;
                String bgcolor = "#FFFFFF";
                if (i % 2 == 0) {
                    bgcolor = "#EFEDEC";
                }
                ret.append("<tr> \n");
                ret.append("<td> \n");
                ret.append("<a onClick=\"javascript:if(confirm('"
                        + paramsRequest.getLocaleString("msgConfirmRemove")
                        + "')) { document.frmResource.type.value="
                        + String.valueOf(i)
                        + ";  document.frmResource.actype.value='remove'; document.frmResource.submit(); } else return false;\">");
                ret.append("<img src=\"" + SWBPlatform.getContextPath()
                        + "/swbadmin/images/eliminar.gif\" border=0 alt=\""
                        + paramsRequest.getLocaleString("msgRemoveTypeComment")
                        + "\">");
                ret.append("</a>&nbsp; \n");
                ret.append("<a onClick=\"javascript:document.frmResource.type.value="
                        + String.valueOf(i)
                        + "; document.frmResource.actype.value='edit'; jsLoad(document.frmResource, '"
                        + tc.getComentario() + "', '" + tc.getArea() + "', '"
                        + tc.getResponsable() + "', '" + tc.getEmail() + "');\">");
                ret.append("<img src=\"" + SWBPlatform.getContextPath()
                        + "/swbadmin/images/i_contenido.gif\" border=0 alt=\""
                        + paramsRequest.getLocaleString("msgEditTypeComment")
                        + "\">");
                ret.append("</a></td> \n");
                ret.append("<td>" + tc.getComentario() + "</td> \n");
                ret.append("<td>" + tc.getArea() + "</td> \n");
                ret.append("<td>" + tc.getResponsable() + "</td> \n");
                ret.append("<td>" + tc.getEmail() + "</td> \n");
                ret.append("</tr> \n");
                if (i == 1) {
                    area = tc.getArea();
                    responsable = tc.getResponsable();
                    email = tc.getEmail();
                }
                if (i > 1) {
                    comentarios += "|";
                }
                comentarios += tc.getComentario() + ";" + tc.getArea() + ";"
                        + tc.getResponsable() + ";" + tc.getEmail();
            }
            ret.append("<tr> \n");
            ret.append("<td></td>");
            ret.append("<td><input type=\"text\" name=\"comentario\"/></td> \n");
            ret.append("<td><input type=\"text\" name=\"area\"/></td> \n");
            ret.append("<td><input type=\"text\" name=\"responsable\"/></td> \n");
            ret.append("<td><input type=\"text\" name=\"email\"/></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td></td>");
            ret.append("<td colspan=\"4\">");
            ret.append("<input type=\"button\" name=\"btnType\" value=\""
                    + paramsRequest.getLocaleString("msgAdd")
                    + "\" onClick=\"if(jsValidaType(this.form, '" + area + "', '" + responsable + "', '" + email + "')) document.frmResource.submit(); else return false;\" class=\"boton\"/> \n");
            //ret.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\" onclick=\"if(jsValidaType(this.form, '"+area+"', '"+responsable+"', '"+email+"')) return true; else return false; \">"+paramsRequest.getLocaleString("msgAdd")+"</button>&nbsp;");
            
            ret.append("\n<input type=\"hidden\" name=\"type\" value=\"0\"/> \n");
            ret.append("<input type=\"hidden\" name=\"actype\" value=\"add\"/> \n");
            ret.append("<input type=\"hidden\" name=\"comentarios\" value=\""
                    + comentarios + "\"/> \n");
            ret.append("</td></tr> \n");
            ret.append("</table> \n");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("</table> \n");
            ret.append("</fieldset>\n");
            
            ret.append("<fieldset>\n");
            ret.append("<table> \n");
            ret.append("<tr> \n");
            ret.append("\n<td>");   
            ret.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\" onclick=\"if(jsValida(this.form, " + i + ", '" + area + "', '" + responsable + "', '" + email + "'))return true; else return false; \">"+paramsRequest.getLocaleString("btnSubmit")+"</button>&nbsp;");
            ret.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\">"+paramsRequest.getLocaleString("btnReset")+"</button>");            
//            ret.append("\n<input type=\"submit\" name=\"btnSave\" value=\"" + paramsRequest.getLocaleString("btnSubmit") + "\" onClick=\"if(jsValida(this.form, " + i + ", '" + area + "', '" + responsable + "', '" + email + "')) return true; else return false;\"/>&nbsp;");
//            ret.append("<input type=\"reset\" name=\"btnReset\" value=\"" + paramsRequest.getLocaleString("btnReset") + "\"/>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("</fieldset>\n");
            ret.append("\n<br>* " + paramsRequest.getLocaleString("msgRequiredData"));
            ret.append("</form> \n");
            ret.append("</div> \n");
            ret.append(getScript(paramsRequest));
        } catch (Exception e) {
            log.error(e);
        }
        return ret.toString();
    }

    /**
     * Agrupa los datos del &aacute;rea, responsable y cuenta de correo por cada
     * comentario contenido en <code>dom</code>.
     * <p>Parses <code>dom</code> looking for the following data: area, recipient
     * and e-mail account; of each commentary.</p>
     *
     * @param dom <code>Document</code> con los datos a extraer <p>data to parse</p>
     * @return  que
     * almacenan los datos del &aacute;rea, responsable y cuenta de correo
     *  objects which contain area, recipient
     * and e-mail account information.
     */
    private ArrayList getTypesComment(Document dom) {
        ArrayList<TypeComment> list = new ArrayList<TypeComment>();

        if (dom != null) {
            TypeComment tc = new TypeComment();
            String comentario = "";
            String area = "";
            String responsable = "";
            String email = "";
            NodeList node = dom.getElementsByTagName("comentario");

            for (int i = 0; i < node.getLength(); i++) {
                area = "";
                responsable = "";
                email = "";
                comentario = node.item(i).getFirstChild().getNodeValue().trim();
                if (!"".equals(comentario)) {
                    NodeList child = node.item(i).getChildNodes();
                    for (int j = 0; j < child.getLength(); j++) {
                        if (child.item(j) != null) {
                            if ("area".equals(child.item(j).getNodeName())) {
                                area = child.item(j).getFirstChild(
                                        ).getNodeValue().trim();
                            } else if ("responsable".equals(
                                    child.item(j).getNodeName())) {
                                responsable = child.item(j).getFirstChild(
                                        ).getNodeValue().trim();
                            } else if ("email".equals(
                                    child.item(j).getNodeName())) {
                                email = child.item(j).getFirstChild(
                                        ).getNodeValue().trim();
                            }
                        }
                    }
                    if ("".equals(email)) {
                        child = dom.getFirstChild().getChildNodes();
                        for (int j = 0; j < child.getLength(); j++) {
                            if (child.item(j) != null) {
                                if ("area".equals(child.item(j).getNodeName())) {
                                    area = child.item(j).getFirstChild(
                                            ).getNodeValue().trim();
                                } else if ("responsable".equals(
                                        child.item(j).getNodeName())) {
                                    responsable = child.item(j).getFirstChild(
                                            ).getNodeValue().trim();
                                } else if ("email".equals(
                                        child.item(j).getNodeName())) {
                                    email = child.item(j).getFirstChild(
                                            ).getNodeValue().trim();
                                }
                            }
                        }
                    }
                    tc = new TypeComment(comentario, area, responsable, email);
                    list.add(tc);
                }
            }
        }
        return list;
    }

    /**
     * Obtiene el c&oacute;digo de JavaScript necesario para validar los datos
     * capturados en la forma presentada por <code>getForm()</code>. <p>Gets the
     * JavaScript code which performs the validations asociated with the code
     * generated by <code>getForm()</code>.</p>
     * 
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @return  contiene el c&oacute;digo de JavaScript a ejecutar
     */
    private String getScript(SWBParamRequest paramsRequest) {
        StringBuilder ret = new StringBuilder();
        WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();

        try {
            ret.append("<script type=\"text/javascript\"> \n");
            ret.append("<!-- \n");

            ret.append("function changeSecureCodeImage(imgid) { \n");
            ret.append("    var img = dojo.byId(imgid); \n");
            ret.append("    if(img) { \n");
            ret.append("        var rn = Math.floor(Math.random()*99999); \n");
            ret.append("        img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn; \n");
            ret.append("    } \n");
            ret.append("} \n");

            ret.append("\nfunction jsValida(pForm, count, area, responsable, email) {");

            ret.append("\n   trim(pForm.comentario);");
            ret.append("\n   trim(pForm.area);");
            ret.append("\n   trim(pForm.responsable);");
            ret.append("\n   trim(pForm.email);");
            ret.append("\n   if (count < 1) {");

            ret.append("\n      if (pForm.comentario.value=='') {");
            ret.append("\n      ");
            ret.append("\n         alert('" + paramsRequest.getLocaleString("msgCommentRequired") + "');");
            ret.append("\n         pForm.comentario.focus();");
            ret.append("\n         return false;");
            ret.append("\n      }");
            ret.append("\n      if (pForm.area.value=='') {");

            ret.append("\n         alert('" + paramsRequest.getLocaleString("msgAreaRequired") + "');");
            ret.append("\n         pForm.area.focus();");
            ret.append("\n         return false;");
            ret.append("\n      }");
            ret.append("\n      if (pForm.responsable.value=='') {");

            ret.append("\n         alert('" + paramsRequest.getLocaleString("msgManagerRequired") + "');");
            ret.append("\n         pForm.responsable.focus();");
            ret.append("\n         return false;");
            ret.append("\n      }");
            ret.append("\n      if (pForm.email.value=='') {");

            ret.append("\n         alert('" + paramsRequest.getLocaleString("msgEmailRequired") + "');");
            ret.append("\n         pForm.email.focus();");
            ret.append("\n         return false;");
            ret.append("\n      }");
            ret.append("\n   } else { ");

            ret.append("\n      if (pForm.comentario.value!='') {");
            ret.append("\n          if (pForm.area.value=='') pForm.area.value=area;");
            ret.append("\n          if (pForm.responsable.value=='') pForm.responsable.value=responsable;");
            ret.append("\n          if (pForm.email.value=='') pForm.email.value=email;");
            ret.append("\n      }");
            ret.append("\n   }");
            ret.append("\n   if (!isFileType(pForm.template, 'xsl|xslt')) return false;");
            ret.append("\n   if (!isFileType(pForm.img, 'jpg|jpeg|gif|png')) return false;");
            ret.append("\n   if (!isFileType(pForm.imgenviar, 'jpg|jpeg|gif|png')) return false;");
            ret.append("\n   if (!isFileType(pForm.imglimpiar, 'jpg|jpeg|gif|png')) return false;");
            ret.append("\n   if (!isNumber(pForm.width)) return false;");
            ret.append("\n   if (!isNumber(pForm.height)) return false;");
            ret.append("\n   if (!isNumber(pForm.top)) return false;");
            ret.append("\n   if (!isNumber(pForm.left)) return false;");
            ret.append("\n   replaceChars(pForm.headermsg);");
            ret.append("\n   replaceChars(pForm.footermsg);");
            ret.append("\n   if (pForm.actype.value=='add' && pForm.comentario.value!='') {");

            ret.append("\n      trim(pForm.comentarios);");
            ret.append("\n      if (pForm.comentarios.value!='') pForm.comentarios.value+='|'");
            ret.append("\n      pForm.comentarios.value+=pForm.comentario.value+';'+pForm.area.value+';'+pForm.responsable.value+';'+pForm.email.value;");
            ret.append("\n   }");
            ret.append("\n   return true;");
            ret.append("\n}");

            ret.append("\nfunction jsLoad(pForm, comentario, area, responsable, email) {");
            ret.append("\n   pForm.btnType.value='" + paramsRequest.getLocaleString("msgUpdate") + "';");
            ret.append("\n   pForm.comentario.value=comentario;");
            ret.append("\n   pForm.area.value=area;");
            ret.append("\n   pForm.responsable.value=responsable;");
            ret.append("\n   pForm.email.value=email;");
            ret.append("\n}");

            ret.append("\nfunction jsValidaType(pForm, area, responsable, email) {");
            ret.append("\n   trim(pForm.comentario);");
            ret.append("\n   if (pForm.comentario.value=='') {");
            ret.append("\n       alert('" + paramsRequest.getLocaleString("msgCommentRequired") + "');");
            ret.append("\n       pForm.comentario.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            ret.append("\n   trim(pForm.area);");
            ret.append("\n   trim(pForm.responsable);");
            ret.append("\n   trim(pForm.email);");
            ret.append("\n   if (pForm.area.value=='') pForm.area.value=area;");
            ret.append("\n   if (pForm.responsable.value=='') pForm.responsable.value=responsable;");
            ret.append("\n   if (pForm.email.value=='') pForm.email.value=email;");
            ret.append("\n   trim(pForm.area);");
            ret.append("\n   if (pForm.area.value=='') {");
            ret.append("\n       alert('" + paramsRequest.getLocaleString("msgAreaRequired") + "');");
            ret.append("\n       pForm.area.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            ret.append("\n   trim(pForm.responsable);");
            ret.append("\n   if (pForm.responsable.value=='') {");
            ret.append("\n       alert('" + paramsRequest.getLocaleString("msgManagerRequired") + "');");
            ret.append("\n       pForm.responsable.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            ret.append("\n   trim(pForm.email);");
            ret.append("\n   if (pForm.email.value=='') {");
            ret.append("\n       alert('" + paramsRequest.getLocaleString("msgEmailRequired") + "');");
            ret.append("\n       pForm.email.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            ret.append("\n   else if (!isEmail(pForm.email)) return false;");
            ret.append("\n   if (pForm.actype.value=='add') {");
            ret.append("\n      trim(pForm.comentarios);");
            ret.append("\n      if (pForm.comentarios.value!='') pForm.comentarios.value+='|'");
            ret.append("\n      pForm.comentarios.value+=pForm.comentario.value+';'+pForm.area.value+';'+pForm.responsable.value+';'+pForm.email.value;");
            ret.append("\n   }");
            ret.append("\n   return true;");
            ret.append("\n}");
            ret.append(admResUtils.loadIsEmail());
            ret.append(admResUtils.loadAddOption());
            ret.append(admResUtils.loadEditOption());
            ret.append(admResUtils.loadUpdateOption());
            ret.append(admResUtils.loadDeleteOption());
            ret.append(admResUtils.loadDuplicateOption());
            ret.append(admResUtils.loadIsFileType());
            ret.append(admResUtils.loadReplaceChars());
            ret.append(admResUtils.loadIsNumber());
            ret.append(admResUtils.loadTrim());
//            ret.append("--> \n");
//            ret.append("</script> \n");
        }catch(SWBResourceException e) {
        }finally {
            ret.append("\n--> \n");
            ret.append("</script> \n");
        }
        return ret.toString();
    }

    /**
     * Gets the captcha script.
     * 
     * @param paramRequest the param request
     * @return the captcha script
     * @throws SWBResourceException the sWB resource exception
     */
    private String getCaptchaScript(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"swb-coment-imagen\"> \n");
        html.append("  <p><img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" alt=\"\" id=\"imgseccode\" width=\"155\" height=\"65\" /></p> \n");
        html.append("  <p><a href=\"javascript:changeSecureCodeImage('imgseccode');\">"+paramRequest.getLocaleString("lblDoViewAnotherCode")+"</a></p> \n");
        html.append("</div> \n");
        html.append("<div class=\"swb-coment-captcha\"> \n");
        html.append("  <p><label for=\"cmnt_seccode\">"+paramRequest.getLocaleString("lblDoViewImageIs")+":</label></p> \n");
        html.append("  <p><input type=\"text\" name=\"cmnt_seccode\" value=\"\"/></p> \n");
        html.append("</div> \n");
        return html.toString();
    }

    /**
     * Agrega un nodo al <code>parent</code> especificado en el <code>doc</code> recibido.
     * <p>Inserts a node to the specified <code>parent</code> in <code>doc</code></p>
     * @param doc <b>Document</b> al que se desea agregar el nodo. <p><b>Document</b>
     *            to which the new node is going to be added.</p>
     * @param parent elemento que será el padre del nuevo nodo. <p>new node's
     *            parent element </p>
     * @param elemName nombre del elemento a agregar. <p>new element's name</p>
     * @param elemValue valor del elemento a agregar. <p>new element's value</p>
     */
    private void addElem(Document doc, Element parent, String elemName, String elemValue) {
        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }

    /**
     * Agrega la informaci&oacute;n enviada por correo, al archivo log de este
     * recurso. <p>Adds to this resource's log the commentaries' data set by mail.</p>
     * 
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void feedCommentLog(HttpServletRequest request, SWBActionResponse response) throws IOException {

        Resource base = getResourceBase();
        String logPath = SWBPortal.getWorkPath() + base.getWorkPath() + "/Comment.log";
        StringBuilder toLog = new StringBuilder();
        Date now = new Date();
        NodeList nl = null;

        toLog.append(SWBUtils.TEXT.iso8601DateFormat(now));

        User user =  response.getUser();
        if( user.isSigned() ) {
            toLog.append("\n    User:");
            toLog.append((null != user.getFirstName() && !"".equals(user.getFirstName().trim())) ? user.getFirstName().trim() : "");
            toLog.append((null != user.getLastName() && !"".equals(user.getLastName().trim())) ? user.getLastName().trim() : "");
            toLog.append((null != user.getSecondLastName() && !"".equals(user.getSecondLastName().trim())) ? user.getSecondLastName().trim() : "");
        }
        
        try {
            Document dom = getDomEmail(request, response);
            nl = dom.getElementsByTagName("fromname");
            toLog.append("\n    From:" + (nl != null && nl.getLength() > 0
                    ? nl.item(0).getFirstChild().getNodeValue() : ""));
            nl = dom.getElementsByTagName("fromemail");
            if (nl != null && nl.getLength() > 0) {
                toLog.append("<" + nl.item(0).getFirstChild().getNodeValue() + ">");
            }
    /*        toLog.append("\n    To:" + dom.getElementsByTagName("responsable").item(
                                    0).getFirstChild().getNodeValue());
            toLog.append("<" + dom.getElementsByTagName("toemail").item(
                                    0).getFirstChild().getNodeValue() + ">"); */
            nl = dom.getElementsByTagName("subject");
            toLog.append("\n    Subject:" + (nl != null && nl.getLength() > 0
                    ? nl.item(0).getFirstChild().getNodeValue() : ""));
    /*        toLog.append("\n    Area:" + dom.getElementsByTagName("area").item(
                                    0).getFirstChild().getNodeValue()); */
            nl = dom.getElementsByTagName("phone");
            toLog.append("\n    Phone:" + (nl != null && nl.getLength() > 0
                    ? nl.item(0).getFirstChild().getNodeValue() : ""));
            nl = dom.getElementsByTagName("fax");
            toLog.append("\n    Fax:" + (nl != null && nl.getLength() > 0
                    ? nl.item(0).getFirstChild().getNodeValue() : ""));
            nl = dom.getElementsByTagName("message");
            toLog.append("\n    Message:" + (nl != null && nl.getLength() > 0
                    ? nl.item(0).getFirstChild().getNodeValue() : ""));
            toLog.append("\n");
        }catch(SWBResourceException te) {
            log.error(te);
        }

        File file = new File(SWBPortal.getWorkPath() + base.getWorkPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        SWBUtils.IO.log2File(logPath, toLog.toString());
    }
}

/**
 * Objeto: Tipos de comentario en memoria.
 *
 * Object: Comment types in memory.
 *
 * @author : Vanessa Arredondo Núñez
 * @version 1.0
 */
class TypeComment {
    private String comentario;
    private String area;
    private String responsable;
    private String email;

    /**
     * Creates a new instance of TypeComment
     */
    public TypeComment() {
    }

    /**
     * Creates a new instance of TypeComment
     *
     * @param comentario cuerpo del comentario. <p>commentary's text</p>
     * @param area &aacute;rea a la que pertenece el destinatario. <p>recipient's area</p>
     * @param responsable destinatario. <p>recipient</p>
     * @param email cuenta de correo del destinatario. <p>recipient's e-mail account</p>
     */
    public TypeComment(String comentario, String area, String responsable, String email) {
        this.comentario = comentario;
        this.area = area;
        this.responsable = responsable;
        this.email = email;
    }

    /**
     * Obtiene el texto del comentario. <p>Gets the commentary's text.</p>
     * @return <code>String</code> el texto del comentario. <p>commentary's text.</p>
     */
    public String getComentario() {
        return this.comentario;
    }

    /**
     * Fija el texto del comentario. <p>Sets the commentary's text.</p>
     * @param comentario texto a enviar como comentario. <p>text to send as a
     * comment.</p>
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtiene el nombre del &aacute;rea del destinatario. <p>Gets the recipient's area.</p>
     * @return <code>String</code> nombre del &aacute;rea del destinatario. <p>recipient's
     * area's name.</p>
     */
    public String getArea() {
        return this.area;
    }

    /**
     * Fija una nueva &aacute;rea del destinatario. <p>Sets the recipient's area.</p>
     * @param area nuevo nombre del &aacute;rea del destinatario. <p>new recipient's
     * area's name.</p>
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Obtiene el nombre del destinatario del comentario. <p>Gets the recipient's name</p>
     * @return <code>String</code> nombre del destinatario del comentario. <p>recipient's name.</p>
     */
    public String getResponsable() {
        return this.responsable;
    }

    /**
     * Fija un nuevo nombre del destinatario. <p>Sets the recipient's name.</p>
     * @param responsable nuevo nombre del destinatario. <p>new recipient's name.</p>
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    /**
     * Obtiene cuenta de correo del destinatario. <p>Gets the recipient's e-mail account.</p>
     * @return <code>String</code> cuenta de correo del destinatario. <p>recipient's
     * e-mail account.</p>
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Fija una nueva cuenta de correo del destinatario. <p>Sets a new recipient's e-mail account.</p>
     * @param email nueva cuenta de correo del destinatario. <p>new recipient's
     * e-mail account.</p>
     */
    public void setEmail(String email) {
        this.email = email;
    }
}