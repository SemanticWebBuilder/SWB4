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

import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.semanticwb.portal.util.FileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;



// TODO: Auto-generated Javadoc
/** Objeto que se encarga de desplegar y administrar recomendaciones de usuarios finales
 * interactuando con archivos de tipo Flash bajo ciertos criterios(configuraci&oacute;n del
 * recurso).
 *
 * Object that is in charge to unfold and to administer recommendations of end users
 * interacts with Flash type archives under certain criteria (resource configuration).
 *
 * @author : Vanessa Arredondo N��ez
 * @version 1.0
 * @see com.infotec.wb.resources.Recomendar
 */
public class RecommendSwf extends Recommend {
    
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(RecommendSwf.class);
    
    
    /** Creates a new instance of RecomendarSwf. */    
    public RecommendSwf() {
    }
    
    /**
     * Gets the dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     * @return
     */       
    @Override
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        String action = (null != request.getParameter("rec_act")
                && !"".equals(request.getParameter("rec_act").trim()))
                ? request.getParameter("rec_act").trim()
                : "rec_step2";
        Resource base = getResourceBase();
        String lang = paramRequest.getUser().getLanguage();
        try {        
            Document  dom = SWBUtils.XML.getNewDocument();
            if ("rec_step3".equals(action)) {
//                dom = super.getDomEmail(request, paramRequest)getDomEmail(request, response, paramRequest); // Envia correo
            } else { // Nueva ventana con formulario               
                User user = paramRequest.getUser();
                SWBResourceURLImp url = new SWBResourceURLImp(request, base,
                        paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode(SWBResourceURL.Mode_VIEW);
                url.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
                url.setParameter("rec_act", "rec_step3");
                url.setTopic(paramRequest.getWebPage());
                url.setCallMethod(paramRequest.Call_DIRECT);            

                Element el = dom.createElement("form");
                el.setAttribute("path", path);
                el.setAttribute("accion", url.toString());

                el.setAttribute("styleClass",
                        base.getAttribute("styleClass", "").equals("")
                        ? "<div>"
                        : "<div class=\"" + base.getAttribute("styleClass", "") + "\">");
                el.setAttribute("styleClassClose", "</div>");
                
                if (!"".equals(base.getAttribute("swf", "").trim())) {
                    String strSwfvar = "accion=" + url.toString();
                    el.setAttribute("swf",
                            webWorkPath + "/" + base.getAttribute("swf").trim());
                    
                    NodeList ndlSwfvar = base.getDom().getElementsByTagName("swfvar");
                    for (int i = 0; i < ndlSwfvar.getLength(); i++) {
                        strSwfvar += "&" + ndlSwfvar.item(i).getChildNodes(
                                                     ).item(0).getNodeValue();
                    }
                    if (user.isSigned()) {
                        strSwfvar += "&txtFromName=";
                        strSwfvar += (null != user.getFirstName() 
                                && !"".equals(user.getFirstName().trim()))
                                ? user.getFirstName().trim()
                                : "";
                        strSwfvar += (null != user.getLastName()
                                && !"".equals(user.getLastName().trim()))
                                ? " " + user.getLastName().trim()
                                : "";
                        strSwfvar += (null != user.getSecondLastName()
                                && !"".equals(user.getSecondLastName().trim()))
                                ? " " + user.getSecondLastName().trim()
                                : "";
                        strSwfvar += "&txtFromEmail=";
                        strSwfvar += (null != user.getEmail()
                                && !"".equals(user.getEmail().trim()))
                                ? " " + user.getEmail().trim()
                                : "";
                    }
                    strSwfvar += "&txtTopic=" + paramRequest.getWebPage().getTitle();
                    strSwfvar += "&txtTopicDesc="
                            + paramRequest.getWebPage().getDescription(lang);
                    el.setAttribute("swfvars", strSwfvar);
                }
                dom.appendChild(el);
            }
            return dom;
        } catch (Exception e) {
            log.error("Error while generating DOM in resource "
                    + base.getResourceType().getResourceClassName()
                    + " with identifier " + base.getId() + " - "
                    + base.getTitle(), e);
        }
        return null;
    }

    /**
     * Do admin.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doAdmin(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        
        Resource base = getResourceBase();
        WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();
        StringBuffer ret = new StringBuffer("");
        String msg = paramRequest.getLocaleString("msgUndefinedOperation");
        String action = (null != request.getParameter("act")
                && !"".equals(request.getParameter("act").trim()))
                ? request.getParameter("act").trim()
                : paramRequest.getAction();
        
        if (action.equals("add") || action.equals("edit")) {
            ret.append(getForm(request, paramRequest));
        } else if(action.equals("update")) {
            FileUpload fup = new FileUpload();
            try {
                fup.getFiles(request, response);
                String applet = null;
                String value = (null != fup.getFileName("template")
                        && !"".equals(fup.getFileName("template").trim()))
                        ? fup.getFileName("template").trim()
                        : null;
                if (value != null
                        || !"".equals(base.getAttribute("template", "").trim())) {
                    if (value != null) {
                        String file = admResUtils.getFileName(base, value);
                        if (file != null && !file.trim().equals("")) {
                            if (!admResUtils.isFileType(file, "xsl|xslt")) {
                                msg = paramRequest.getLocaleString("msgErrFileType")
                                        + " <i>xsl, xslt</i>: " + file;
                            } else {
                                applet = admResUtils.uploadFileParsed(base, fup,
                                        "template", request.getSession().getId());
                                if (applet != null && !applet.trim().equals("")) {
                                    base.setAttribute("template", file);
                                } else {
                                    msg = paramRequest.getLocaleString("msgErrUploadFile")
                                            + " <i>" + value + "</i>.";
                                }
                            }
                        } else {
                            msg = paramRequest.getLocaleString("msgErrUploadFile")
                                    + " <i>" + value + "</i>.";
                        }
                    }
                }
                value = (null != fup.getValue("noimg")
                        && !"".equals(fup.getValue("noimg").trim()))
                        ? fup.getValue("noimg").trim() 
                        : "0";
                if ("1".equals(value)
                        && !"".equals(base.getAttribute("img", "").trim())) {
                    SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath()
                            + base.getWorkPath() + "/"
                            + base.getAttribute("img").trim());
                    base.removeAttribute("img");
                } else {
                    value = (null != fup.getFileName("img")
                            && !"".equals(fup.getFileName("img").trim()))
                            ? fup.getFileName("img").trim()
                            : null;
                    if (value != null) {
                        String file = admResUtils.getFileName(base, value);
                        if (file != null && !file.trim().equals("")) {
                            if (!admResUtils.isFileType(file, "bmp|jpg|jpeg|gif")) {
                                msg = paramRequest.getLocaleString("msgErrFileType")
                                        + " <i>bmp, jpg, jpeg, gif</i>: " + file;
                            } else {
                                if (admResUtils.uploadFile(base, fup, "img")) {
                                    base.setAttribute("img", file);
                                } else {
                                    msg = paramRequest.getLocaleString("msgErrUploadFile")
                                            + " <i>" + value + "</i>.";
                                }
                            }
                        } else {
                            msg = paramRequest.getLocaleString("msgErrUploadFile")
                                    + " <i>" + value + "</i>.";
                        }
                    }
                } 

                setAttribute(base, fup, "alt");
                setAttribute(base, fup, "btntexto");
                setAttribute(base, fup, "lnktexto");
                setAttribute(base, fup, "blnstyle");                    
                setAttribute(base, fup, "menubar", "yes");
                setAttribute(base, fup, "toolbar", "yes");
                setAttribute(base, fup, "status", "yes");
                setAttribute(base, fup, "location", "yes");
                setAttribute(base, fup, "directories", "yes");
                setAttribute(base, fup, "scrollbars", "yes");
                setAttribute(base, fup, "resizable", "yes");
                setAttribute(base, fup, "width");
                setAttribute(base, fup, "height");
                setAttribute(base, fup, "top");
                setAttribute(base, fup, "left");
                setAttribute(base, fup, "subject");
                setAttribute(base, fup, "headermsg");
                setAttribute(base, fup, "footermsg");
                setAttribute(base, fup, "styleClass");

                value = (null != fup.getFileName("swf")
                        && !"".equals(fup.getFileName("swf").trim()))
                        ? fup.getFileName("swf").trim()
                        : null;
                if (value != null) {
                    String file = admResUtils.getFileName(base, value);
                    if (file != null && !file.trim().equals("")) {
                        if (!admResUtils.isFileType(file, "swf|emf|wmf")) {
                            msg = paramRequest.getLocaleString("msgErrFileType")
                                    + " <i>swf, emf, wmf</i>: " + file;
                        } else {
                            if (admResUtils.uploadFile(base, fup, "swf")) {
                                base.setAttribute("swf", file);
                            } else {
                                msg = paramRequest.getLocaleString("msgErrUploadFile")
                                        + " <i>" + value + "</i>.";
                            }
                        }
                    } else {
                        msg = paramRequest.getLocaleString("msgErrUploadFile")
                                + " <i>" + value + "</i>.";
                    }
                }
                base.updateAttributesToDB();

                Document dom = base.getDom();
                if (dom != null) {
                    removeAllNodes(dom, Node.ELEMENT_NODE, "swfvar");
                } else {
                    dom = SWBUtils.XML.getNewDocument();
                    Element root = dom.createElement("resource");
                    dom.appendChild(root);
                }
                value = (null != fup.getValue("swfvar")
                        && !"".equals(fup.getValue("swfvar").trim()))
                        ? fup.getValue("swfvar").trim()
                        : null;
                if (value != null) {
                    StringTokenizer stk = new StringTokenizer(value, "|");
                    while (stk.hasMoreTokens()) {
                        value = stk.nextToken();
                        Element emn = dom.createElement("swfvar");
                        emn.appendChild(dom.createTextNode(value));
                        dom.getFirstChild().appendChild(emn);
                    }
                }
                base.setXml(SWBUtils.XML.domToXml(dom));
                //base.update(paramRequest.getUser().getId(), "Resource with identifier "+base.getId()+" was updated successfully ");
                msg = paramRequest.getLocaleString("msgOkUpdateResource")
                        + " " + base.getId();
                if (applet != null && !"".equals(applet.trim())) {
                    ret.append(applet);
                } else {
                    ret.append("<script language=\"JavaScript\">\n"
                        + "   location='"
                        + paramRequest.getRenderUrl().setAction("edit").toString()
                        + "';\n</script>\n");
                }
            } catch (Exception e) {
                log.error(e);
                msg = paramRequest.getLocaleString("msgErrUpdateResource") 
                        + " " + base.getId();
            }
            ret.append("<script language=\"JavaScript\">\n"
                    + "   alert('" + msg + "');\n</script>\n");             
        } else if (action.equals("remove")) {
            msg = admResUtils.removeResource(base);
            ret.append("<script language=\"JavaScript\">\n"
                + "   alert('" + msg + "');\n</script>\n");
        }
        response.getWriter().print(ret.toString());
    }

    /**
     * Sets the attribute.
     * 
     * @param base the base
     * @param fup the fup
     * @param att the att
     */  
    protected void setAttribute(Resource base, FileUpload fup, String att) {
        try {
            if (null != fup.getValue(att) && !"".equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            } else {
                base.removeAttribute(att);
            }
        } catch (Exception e) {
            log.error("Error while setting resource attribute: " + att 
                    + ", " + base.getId() + "-" + base.getTitle(), e);
        }
    }
    
    /**
     * Sets the attribute.
     * 
     * @param base the base
     * @param fup the fup
     * @param att the att
     * @param value the value
     */  
    protected void setAttribute(Resource base, FileUpload fup, String att,
            String value) {
        
        try {
            if (null != fup.getValue(att) && value.equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            } else {
                base.removeAttribute(att);
            }
        } catch (Exception e) {
            log.error("Error while setting resource attribute: " + att
                    + ", " + base.getId() + "-" + base.getTitle(), e);
        }
    }
    
    /**
     * Removes the all nodes.
     * 
     * @param dom the dom
     * @param nodeType the node type
     * @param name the name
     */  
    private void removeAllNodes(Document dom, short nodeType, String name) {
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
     * Gets the form.
     * 
     * @param request the request
     * @param paramRequest the param request
     * @return the form
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @return
     */
    private String getForm(HttpServletRequest request,
                           SWBParamRequest paramRequest) 
                           throws SWBResourceException, IOException {
        
        WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();
        Resource base = getResourceBase();
        StringBuffer ret = new StringBuffer("");
        try {
            SWBResourceURL url = paramRequest.getRenderUrl().setAction("update");
            ret.append("<div class=\"swbform\">");
            ret.append("<form name=\"frmResource\" method=\"post\" enctype=\"multipart/form-data\" action=\""
                    + url.toString() + "\"> \n");
            ret.append("<table>");
            ret.append("<tr> \n");
            ret.append("<td>");
            ret.append("<fieldset>");
            ret.append("<table>");
            ret.append("<tr> \n");
            ret.append("<td colspan=2>");
            ret.append(paramRequest.getLocaleString("msgStep1"));
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgTemplate")
                    + " (xsl, xslt):</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"file\" name=\"template\" onChange=\"isFileType(this, 'xsl|xslt');\" />");
            //if (!"".equals(base.getAttribute("template", "").trim()))
            if (path.indexOf(webWorkPath) != -1) {
                ret.append("<p>"
                        + paramRequest.getLocaleString("msgCurrentTemplate")
                        + " <a href=\"" + path + base.getAttribute("template").trim()
                        + "\">" + base.getAttribute("template").trim() + "</a></p>");
            } else {
                ret.append("<p>" + paramRequest.getLocaleString("msgByDefault")
                        + " <a href=\"" + path + name + ".xslt\">" + name
                        + ".xslt</a></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgImage")
                    + " (bmp, gif, jpg, jpeg):</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"file\" name=\"img\" size=\"40\" onClick=\"this.form.btntexto.value=''; this.form.lnktexto.value=''\" onChange=\"isFileType(this, 'bmp|jpg|jpeg|gif');\"/>");
            if (!"".equals(base.getAttribute("img", "").trim())) {
                ret.append("<p>"
                        + admResUtils.displayImage(base,
                                                   base.getAttribute("img").trim(),
                                                   "img")
                        + "<input type=checkbox name=noimg value=1>"
                        + paramRequest.getLocaleString("msgCutImage")
                        + " <i>" + base.getAttribute("img").trim() + "</i></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>"
                    + paramRequest.getLocaleString("msgAlt") + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=text name=alt ");
            if (!"".equals(base.getAttribute("alt", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("alt").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgButton")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=text name=btntexto ");
            if (!"".equals(base.getAttribute("btntexto", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgLink")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=text name=lnktexto ");
            if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("lnktexto").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgStyle")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=text name=blnstyle ");
            if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");            
            ret.append("<tr> \n");
            ret.append("<td>* " + paramRequest.getLocaleString("msgFlash")
                    + " (swf, emf, wmf):</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"file\" name=\"swf\" size=\"40\" onChange=\"isFileType(this, 'swf|emf|wmf');\"/>");
            if (!"".equals(base.getAttribute("swf", "").trim())) {
                ret.append("<p>" + paramRequest.getLocaleString("msgCurrentSwf")
                        + " <i>" + base.getAttribute("swf").trim() + "</i></p>");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgSwfConfiguration")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=text name=txtSwfvar><input type=hidden name=swfvar ");
            if (!"".equals(base.getAttribute("swfvar", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("swfvar").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append(">");
            ret.append("\n<input type=button name=btnAdd  value="
                    + paramRequest.getLocaleString("btnAdd")
                    + " onClick=\"addOption(this.form.selSwfvar, this.form.txtSwfvar)\">");
            ret.append("\n<input type=button name=btnEdit value="
                    + paramRequest.getLocaleString("btnEdit")
                    + " onClick=\"updateOption(this.form.selSwfvar, this.form.txtSwfvar)\">");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>&nbsp;</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("\n<select name=\"selSwfvar\" size=5 multiple onChange=\"editOption(this.form.selSwfvar, this.form.txtSwfvar)\">");
            String swfvar = "";
            Document dom = base.getDom();
            if (dom != null) {
                NodeList node = dom.getElementsByTagName("swfvar");
                if (node.getLength() > 0) {
                    for (int i = 0; i < node.getLength(); i++) {
                        swfvar = node.item(i).getChildNodes().item(0
                                ).getNodeValue().trim();
                        if (!"".equals(swfvar.trim())) {
                            ret.append("\n<option value=\""
                                    + swfvar.trim().replaceAll("\"", "&#34;")
                                    + "\">" + swfvar.trim() + "</option>");
                        }
                    }
                }
            }
            ret.append("\n</select>");
            ret.append("\n<input type=button name=btnDel value=" + paramRequest.getLocaleString("btnRemove") + " onClick=\"deleteOption(this.form.selSwfvar, this.form.txtSwfvar)\">");
            ret.append("</td>");
            ret.append("\n</tr>");            
            ret.append("<tr> \n");
            ret.append("<td colspan=2>");
            ret.append("<br><br>");
            ret.append(paramRequest.getLocaleString("msgStep2"));
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append(admResUtils.loadWindowConfiguration(base, paramRequest));
            ret.append("<tr> \n");
            ret.append("<td colspan=2>");
            ret.append("<br><br>");
            ret.append(paramRequest.getLocaleString("msgStep3"));
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgSubjectTag")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=text name=subject ");
            if (!"".equals(base.getAttribute("subject", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("subject").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgMessageHeader")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<textarea name=headermsg>");
            if (!"".equals(base.getAttribute("headermsg", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("headermsg").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append("</textarea></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgMessageFooter")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<textarea name=footermsg>");
            if (!"".equals(base.getAttribute("footermsg", "").trim())) {
                ret.append(" value=\""
                        + base.getAttribute("footermsg").trim().replaceAll("\"", "&#34;")
                        + "\"");
            }
            ret.append("</textarea></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td>" + paramRequest.getLocaleString("msgStyleClassName")
                    + "</td> \n");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"styleClass\" value=\"");
            if (!"".equals(base.getAttribute("styleClass", "").trim())) {
                ret.append(base.getAttribute("styleClass").replaceAll("\"", "&#34;"));
            }
            ret.append("\"></td> \n");
            ret.append("</tr> \n");
            ret.append("</table> \n");
            ret.append("</fieldset>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("<tr> \n");
            ret.append("<td>");
            ret.append("<fieldset>");
/*            ret.append("<table> \n");
            ret.append("<tr> \n");
            ret.append("\n<td>");*/
            ret.append("\n<input type=submit name=btnSave value="
                    + paramRequest.getLocaleString("btnSubmit")
                    + " onClick=\"if(jsValida(this.form)) return true; else return false;\">&nbsp;");
            ret.append("<input type=reset name=btnReset value="
                    + paramRequest.getLocaleString("btnReset") + ">");
/*            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("</table> \n");*/
            ret.append("</fieldset>");
            ret.append("\n</td>");
            ret.append("\n</tr>");          
            ret.append("</table> \n");
            ret.append("</form> \n");
            ret.append("</div>");
            ret.append(getScript(request, paramRequest));
        } catch (Exception e) {
            log.error(e);
        }
        return ret.toString();
    }
        
    /**
     * Gets the script.
     * 
     * @param request the request
     * @param paramRequest the param request
     * @return the script
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @return
     */     
    private String getScript(HttpServletRequest request,
            SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        
        StringBuffer ret = new StringBuffer("");
        WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();
        try {
            ret.append("\n<script>");
            ret.append("\nfunction jsValida(pForm)");
            ret.append("\n{");
            if ("".equals(getResourceBase().getAttribute("swf","").trim())) {
                ret.append("\n   if (pForm.swf.value==null || pForm.swf.value=='' || pForm.swf.value==' ')");
                ret.append("\n   {");
                ret.append("\n       alert('"
                        + paramRequest.getLocaleString("msgFlashRequired")
                        + "');");
                ret.append("\n       pForm.swf.focus();");
                ret.append("\n       return false;");
                ret.append("\n   }");
            }
            ret.append("\n   if(!isFileType(pForm.template, 'xsl|xslt')) return false;");
            ret.append("\n   if(!isFileType(pForm.img, 'bmp|jpg|jpeg|gif')) return false;");
            ret.append("\n   if(!isFileType(pForm.swf, 'swf|emf|wmf')) return false;");
            ret.append("\n   if(!isNumber(pForm.width)) return false;");
            ret.append("\n   if(!isNumber(pForm.height)) return false;");
            ret.append("\n   if(!isNumber(pForm.top)) return false;");
            ret.append("\n   if(!isNumber(pForm.left)) return false;");
            ret.append("\n   replaceChars(pForm.headermsg);");
            ret.append("\n   replaceChars(pForm.footermsg);");
            ret.append("\n   pForm.swfvar.value='';");
            ret.append("\n   for (var i=0; i<pForm.selSwfvar.length; i++)");
            ret.append("\n   {");
            ret.append("\n       if (i>0) pForm.swfvar.value+=\"|\";");
            ret.append("\n       pForm.swfvar.value+=pForm.selSwfvar.options[i].value;");
            ret.append("\n   }");
            ret.append("\n   return true;");
            ret.append("\n}");
            ret.append(admResUtils.loadAddOption());
            ret.append(admResUtils.loadEditOption());
            ret.append(admResUtils.loadUpdateOption());
            ret.append(admResUtils.loadDeleteOption());
            ret.append(admResUtils.loadDuplicateOption());
            ret.append(admResUtils.loadIsFileType());
            ret.append(admResUtils.loadReplaceChars());
            ret.append(admResUtils.loadIsNumber());
            ret.append("\n</script>");
        } catch (Exception e) {
            log.error(e);
        }
        return ret.toString();
    }
}
