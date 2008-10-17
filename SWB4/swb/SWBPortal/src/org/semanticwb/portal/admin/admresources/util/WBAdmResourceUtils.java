/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */
package org.semanticwb.portal.admin.admresources.util;

import gnu.regexp.RE;
import gnu.regexp.REException;
import java.io.*;
import java.util.*;
import org.semanticwb.model.Portlet;
import javax.xml.transform.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Language;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.w3c.dom.*;
import org.semanticwb.portal.admin.admresources.*;
import org.semanticwb.portal.util.WBFileUpload;

/** objeto: Utilerias de uso comun para los objetos de la api de administraci�n de recursos.
 * <p>
 * methods utils for common use in the administration api objetcs
 * @author Infotec
 * @version 1.1
 */
public class WBAdmResourceUtils {

    private static Logger log = SWBUtils.getLogger(WBAdmResourceUtils.class);
    private String webWorkPath = SWBPlatform.getWebWorkPath();
    private String workPath = SWBPlatform.getWorkPath();
    private String webPath = SWBPlatform.getContextPath();

    public boolean xmlVerifierDefault(String xml) {
        boolean bOk = false;
        String schema = null;
        try {
            schema = SWBUtils.IO.readInputStream(SWBPortal.getAdminFileStream("/swbadmin/schema/admresource.xsd"));
        } catch (Exception e) {
            return bOk;
        }
        if (schema != null && xml != null) {
            bOk = xmlVerifier(schema, xml);
        }
        return bOk;
    }

    public boolean xmlVerifier(String schema, org.w3c.dom.Document dom) {
        return xmlVerifier(null, schema, dom);
    }

    public boolean xmlVerifier(String sysid, String schema, org.w3c.dom.Document dom) {
        return xmlVerifier(sysid, schema, SWBUtils.XML.domToXml(dom));
    }

    public boolean xmlVerifier(String schema, String xml) {
        return xmlVerifier(null, schema, xml);
    }

    public boolean xmlVerifier(String sysid, String schema, String xml) {
        return xmlVerifier(sysid, SWBUtils.IO.getStreamFromString(schema), SWBUtils.IO.getStreamFromString(xml));
    }

    public boolean xmlVerifier(java.io.File schema, java.io.File xml) {
        return xmlVerifierImpl(null, schema, xml);
    }

    public boolean xmlVerifier(org.xml.sax.InputSource schema, org.xml.sax.InputSource xml) {
        return xmlVerifier(null, schema, null, xml);
    }

    public boolean xmlVerifier(String idschema, org.xml.sax.InputSource schema, String idxml, org.xml.sax.InputSource xml) {
        boolean bOk = false;
        if (schema == null || xml == null) {
            if (schema == null) {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): Schema source is null.");
            } else {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): The input document source is null.");
            }
            return bOk;
        }

        if (idschema != null && !idschema.trim().equals("")) {
            schema.setSystemId(idschema);
        }
        if (idxml != null && !idxml.trim().equals("")) {
            xml.setSystemId(idxml);
        }
        bOk = xmlVerifierImpl(schema.getSystemId(), schema, xml);
        return bOk;
    }

    public boolean xmlVerifier(java.io.InputStream schema, java.io.InputStream xml) {
        return xmlVerifier(null, schema, xml);
    }

    public boolean xmlVerifier(String idschema, java.io.InputStream schema, java.io.InputStream xml) {
        boolean bOk = false;
        if (schema == null || xml == null) {
            if (schema == null) {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): Schema stream is null.");
            } else {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): The input document stream is null.");
            }
            return bOk;
        }
        org.xml.sax.InputSource inxml = new org.xml.sax.InputSource(xml);
        bOk = xmlVerifierImpl(idschema, schema, inxml);
        return bOk;
    }

    public boolean xmlVerifierImpl(String sysid, Object objschema, Object objxml) {
        boolean bOk = false;
        if (objschema == null || objxml == null) {
            if (objschema == null) {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): Schema is null.");
            } else {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): The input document is null.");
            }
            return bOk;
        }

        org.iso_relax.verifier.VerifierFactory factory = new com.sun.msv.verifier.jarv.TheFactoryImpl();
        org.iso_relax.verifier.Schema schema = null;
        try {
            if (objschema instanceof java.io.File) {
                schema = factory.compileSchema((java.io.File) objschema);
            } else if (objschema instanceof org.xml.sax.InputSource) {
                schema = factory.compileSchema((org.xml.sax.InputSource) objschema);
            } else if (objschema instanceof java.io.InputStream) {
                if (sysid != null && !sysid.trim().equals("")) {
                    schema = factory.compileSchema((java.io.InputStream) objschema, sysid);
                } else {
                    schema = factory.compileSchema((java.io.InputStream) objschema);
                }
            } else if (objschema instanceof java.lang.String) {
                schema = factory.compileSchema((java.lang.String) objschema);
            }
            try {
                org.iso_relax.verifier.Verifier verifier = schema.newVerifier();
                verifier.setErrorHandler(silentErrorHandler);

                if (objxml instanceof java.io.File) {
                    bOk = verifier.verify((java.io.File) objxml);
                } else if (objxml instanceof org.xml.sax.InputSource) {
                    bOk = verifier.verify((org.xml.sax.InputSource) objxml);
                } else if (objxml instanceof org.w3c.dom.Node) {
                    bOk = verifier.verify((org.w3c.dom.Node) objxml);
                } else if (objxml instanceof java.lang.String) {
                    bOk = verifier.verify((java.lang.String) objxml);
                }
            } catch (org.iso_relax.verifier.VerifierConfigurationException e) {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): Unable to create a new verifier.", e);
            } catch (org.xml.sax.SAXException e) {
                log.error("Error WBAdmResourceUtils.XMLVerifier(): The input document is not wellformed.", e);
            }
        } catch (Exception e) {
            log.error("Error WBAdmResourceUtils.XMLVerifier(): Unable to parse the schema file.", e);
        }
        return bOk;
    }

    public boolean xmlVerifierByURL(String schema, String xml) {
        return xmlVerifierByURL(null, schema, xml);
    }

    public boolean xmlVerifierByURL(String sysid, String schema, String xml) {
        return xmlVerifierImpl(sysid, schema, xml);
    }

    public Document transformAdmResource(User user, String path, String redirect, Portlet base) {
        if (path == null || base == null) {
            return null;
        }
        Document dom = null;
        dom = transformAdmResource(user, path, redirect, base, new String());
        return dom;
    }

    public Document transformAdmResource(User user, String path, String redirect, Portlet base, String form) {
        if (path == null || base == null) {
            return null;
        }
        String xml = null;
        try {
            xml = SWBUtils.IO.readInputStream(new FileInputStream(path));
        } catch (Exception e) {
            log.error("WBAdmResourceUtils:transformResource - " + base.getId(), e);
        }
        if (xml == null) {
            return null;
        }
        return transformAdmResourceByXml(user, xml, redirect, base, form);
    }

    public String transformAdmResource(User user, String path, String redirect, Portlet base, Templates plt) {
        if (path == null || base == null || plt == null) {
            return null;
        }
        return transformAdmResource(user, path, redirect, base, plt, null);
    }

    public String transformAdmResource(User user, String path, String redirect, Portlet base, Templates plt, String form) {
        if (path == null || base == null || plt == null) {
            return null;
        }
        StringBuffer ret = new StringBuffer();
        Document dom = transformAdmResource(user, path, redirect, base, form);
        if (dom == null) {
            return null;
        }
        try {
            ret.append(SWBUtils.XML.transformDom(plt, dom));
        } catch (Exception e) {
            log.error("WBAdmResourceUtils:transformAdmResource - " + base.getId(), e);
        }
        return ret.toString();
    }

    public Document transformAdmResourceByXml(User user, String xml, String redirect, Portlet base) {
        if (xml == null || base == null) {
            return null;
        }
        Document dom = null;
        dom = transformAdmResourceByXml(user, xml, redirect, base, new String());
        return dom;
    }

    public Document transformAdmResourceByXml(User user, String xml, String redirect, Portlet base, String form) {
        if (xml == null || base == null) {
            return null;
        }
        AdmResourceMgr mgr = new AdmResourceMgr(user);
        mgr.setXml(xml, base, redirect);
        if (form != null && !form.trim().equals("")) {
            xml = mgr.show(form);
        } else {
            xml = mgr.show();
        }
        xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><admresource>" + xml + "</admresource>";
        /*
        System.out.println("----------");
        System.out.println(xml);
        System.out.println("----------");
         **/
        return SWBUtils.XML.xmlToDom(xml);
    }

    public String transformAdmResourceByXml(User user, String xml, String redirect, Portlet base, Templates plt) {
        if (xml == null || base == null || plt == null) {
            return null;
        }
        return transformAdmResourceByXml(user, xml, redirect, base, plt, null);
    }

    public String transformAdmResourceByXml(User user, String xml, String redirect, Portlet base, Templates plt, String form) {
        if (xml == null || base == null || plt == null) {
            return null;
        }
        StringBuffer ret = new StringBuffer();
        Document dom = transformAdmResourceByXml(user, xml, redirect, base, form);
        if (dom == null) {
            return null;
        }
        try {
            ret.append(transformDom(plt, dom, "ISO-8859-1"));
        } catch (Exception e) {
            log.error("WBAdmResourceUtils:transformAdmResourceByXml - " + base.getId(), e);
        }
        return ret.toString();
    }

    public String transformDom(Templates plt, Document dom, String encode) throws TransformerException {
        if (plt == null || dom == null) {
            return "";
        }
        ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
        Transformer trans = plt.newTransformer();
        if (encode != null && !encode.trim().equals("")) {
            trans.setOutputProperty(OutputKeys.ENCODING, encode);
        }
        trans.transform(new javax.xml.transform.dom.DOMSource(dom), new javax.xml.transform.stream.StreamResult(sw));
        return sw.toString();
    }
    /**
     * An error handler implementation that doesn't report any error.
     */
    private static final org.xml.sax.ErrorHandler silentErrorHandler = new org.xml.sax.ErrorHandler() {

        public void fatalError(org.xml.sax.SAXParseException e) {
        }

        public void error(org.xml.sax.SAXParseException e) {
        }

        public void warning(org.xml.sax.SAXParseException e) {
        }
    };

    public ArrayList getAppLanguages() {
        ArrayList languages = new ArrayList();
        Iterator<WebSite> it = SWBContext.listWebSites();
        while (it.hasNext()) {
            WebSite site = it.next();
            Iterator<Language> itLang = site.listLanguages();
            while (itLang.hasNext()) {
                Language lang = itLang.next();
                if (!languages.contains(lang.getId())) {
                    languages.add(lang.getId());
                }
            }
        }
        return languages;
    }

    public boolean isNotNull(String eval) {
        if (eval != null && !eval.trim().equals("")) {
            return true;
        }
        return false;
    }

    public boolean isBoolean(String eval) {
        if (eval != null && (eval.trim().toUpperCase().equals("TRUE") || eval.trim().toUpperCase().equals("FALSE"))) {
            return true;
        }
        return false;
    }

    public boolean isCharacter(String eval) {
        if (eval != null && !eval.trim().equals("")) {
            try {
                RE reg = new RE("(.)");
                if (reg.isMatch(eval)) {
                    return true;
                }
            } catch (REException e) {
                log.error("WBAdmResourceUtils.isCharacter()", e);
            }
        }
        return false;
    }

    public boolean isDigit(String eval) {
        if (eval != null && !eval.trim().equals("")) {
            try {
                RE reg = new RE("\\d");
                if (reg.isMatch(eval)) {
                    return true;
                }
            } catch (REException e) {
                log.error("WBAdmResourceUtils.isDigit()", e);
            }
        }
        return false;
    }

    public boolean isNumber(String eval) {
        if (eval != null && !eval.trim().equals("")) {
            try {
                RE reg = new RE("([0-9])+");
                if (reg.isMatch(eval)) {
                    return true;
                }
            } catch (REException e) {
                log.error("WBAdmResourceUtils.isNumber()", e);
            }
        }
        return false;
    }

    public boolean isID(String eval) {
        if (eval != null && !eval.trim().equals("")) {
            try {
                RE reg = new RE("^([a-zA-Z])([a-zA-Z0-9_\\.\\-])*");
                if (reg.isMatch(eval)) {
                    return true;
                }
            } catch (REException e) {
                log.error("WBAdmResourceUtils.isID()", e);
            }
        }
        return false;
    }

    public boolean isCDATA(String eval) {
        if (eval != null && !eval.trim().equals("")) {
            try {
                RE reg = new RE("^([a-zA-Z])([a-zA-Z0-9_\\.\\-\\s])*");
                if (reg.isMatch(eval)) {
                    return true;
                }
            } catch (REException e) {
                log.error("WBAdmResourceUtils.isCDATA()", e);
            }
        }
        return false;
    }

    public boolean isEmail(String eval) {
        if (eval != null && !eval.trim().equals("")) {
            try {
                RE reg = new RE("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
                if (reg.isMatch(eval)) {
                    return true;
                }
            } catch (REException e) {
                log.error("WBAdmResourceUtils.isEmail()", e);
            }
        }
        return false;
    }

    public boolean isFormMethod(String eval) {
        if (eval != null && (eval.trim().toUpperCase().equals("GET") || eval.trim().toUpperCase().equals("POST"))) {
            return true;
        }
        return false;
    }

    public boolean isInputType(String eval) {
        if (eval != null) {
            if (eval.trim().toUpperCase().equals("TEXT") ||
                    eval.trim().toUpperCase().equals("PASSWORD") ||
                    eval.trim().toUpperCase().equals("HIDDEN") ||
                    eval.trim().toUpperCase().equals("CHECKBOX") ||
                    eval.trim().toUpperCase().equals("RADIO") ||
                    eval.trim().toUpperCase().equals("FILE") ||
                    eval.trim().toUpperCase().equals("IMAGE") ||
                    eval.trim().toUpperCase().equals("BUTTON") ||
                    eval.trim().toUpperCase().equals("SUBMIT") ||
                    eval.trim().toUpperCase().equals("RESET")) {
                return true;
            }
        }
        return false;
    }

    public boolean isJsValType(String eval) {
        if (eval != null) {
            if (eval.trim().toLowerCase().equals("js_alphabetic") ||
                    eval.trim().toLowerCase().equals("js_numbers") ||
                    eval.trim().toLowerCase().equals("js_email")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Despliega imagen en su representación html.
     *
     * @param   base    La información del recurso en memoria.
     * @param   pImage  El nombre de la imagen que se va a desplegar.
     * @return  Regresa un nuevo String que contiene la representación html de la imagen.
     */
    public String displayImage(Portlet base, String pImage, String pNode) {
        StringBuffer sbfRet = new StringBuffer("");
        try {
            String img = base.getAttribute(pNode, "").trim();
            if (!img.equals("")) {
                String width = base.getAttribute("imgwidth", "").trim();
                String height = base.getAttribute("imgheight", "").trim();

                if (img.endsWith(".swf")) {
                    sbfRet.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\"");
                    if (!width.equals("")) {
                        sbfRet.append(" width=\"" + width + "\"");
                    }
                    if (!height.equals("")) {
                        sbfRet.append(" height=\"" + height + "\"");
                    }
                    sbfRet.append(">");
                    sbfRet.append("<param name=movie value=\"" + webWorkPath + base.getWorkPath() + "/" + img + "\">");
                    sbfRet.append("<param name=quality value=high>");
                    sbfRet.append("<embed src=\"");
                    sbfRet.append(webWorkPath + base.getWorkPath() + "/" + img);
                    sbfRet.append("\" quality=high pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\"");
                    if (!width.equals("")) {
                        sbfRet.append(" width=\"" + width + "\"");
                    }
                    if (!height.equals("")) {
                        sbfRet.append(" height=\"" + height + "\"");
                    }
                    sbfRet.append(">");
                    sbfRet.append("</embed>");
                    sbfRet.append("</object>");
                } else {
                    sbfRet.append("\n<img src=\"");
                    sbfRet.append(webWorkPath + base.getWorkPath() + "/" + img + "\"");
                    if (!width.equals("")) {
                        sbfRet.append(" width=\"" + width + "\"");
                    }
                    if (!height.equals("")) {
                        sbfRet.append(" height=\"" + height + "\"");
                    }
                    sbfRet.append(" hspace=5 border=0>");
                }
            }
        } catch (Exception e) {
            sbfRet.append("\n<br>\n<!--Exception " + e + "-->");
            log.error("Error while displaying image in resource " + base.getId() + ".", e);
        }
        return sbfRet.toString();
    }
    
    /**
     * Despliega imagen en su representaciÃ³n html.
     *
     * @param   base    La informaciÃ³n del recurso en memoria.
     * @param   filename  El nombre de la imagen que se va a desplegar.
     * @return  Regresa un nuevo String que contiene la representaciÃ³n html de la imagen.
     */
    public String displayImage(Portlet base, String filename, int width, int height) {
        StringBuffer strb = new StringBuffer();
        try {
            if (filename.endsWith(".swf")) { //para desplegar imagenes de flash
                strb.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\"");
                if (width > 0) {
                    strb.append(" width=\"" + width + "\"");
                }
                if (height > 0) {
                    strb.append(" height=\"" + height + "\"");
                }
                strb.append(">");
                strb.append("<param name=\"movie\" value=\"" + webWorkPath + base.getWorkPath() + "/");
                strb.append(filename);
                strb.append("\"/>");
                strb.append("<param name=\"quality\" value=\"high\"/>");
                strb.append("<embed src=\"");
                strb.append(webWorkPath + base.getWorkPath() + "/");
                strb.append(filename);
                strb.append("\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\"");
                if (width > 0) {
                    strb.append(" width=\"" + width + "\"");
                }
                if (height > 0) {
                    strb.append(" height=\"" + height + "\"");
                }
                strb.append(">");
                strb.append("</embed>");
                strb.append("</object>");
            } else { //para desplegar imagenes otros tipos de imagenes
                strb.append("\n<img src=\"");
                strb.append(webWorkPath + base.getWorkPath() + "/" + filename);
                strb.append("\"");
                if (width > 0) {
                    strb.append(" width=\"" + width + "\"");
                }
                if (height > 0) {
                    strb.append(" height=\"" + height + "\"");
                }
                strb.append(" hspace=\"5\" border=\"0\"/>");
            }
        } catch (Exception e) {
            log.error("Error while displaying image " + webWorkPath + base.getWorkPath() + "/" + filename + " in resource " + base.getId() + ".", e);
        }
        return strb.toString();
    }

    /**
     * Elimina un archivo dado de .
     * @return a boolean value if the file was removed or not.
     */
    public boolean removeFileFromFS(String path) {
        try {
            if (path != null) {
                File file = new File(path.trim());
                if (file != null && file.exists() && file.isFile()) {
                    return file.delete();
                }
            }
        } catch (Exception e) {
            log.error("Error while removing file..:" + path);
        }
        return false;
    }

    /**
     * Sube un archivo parseado al filesystem. 
     *
     * @param     base      La información del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la información del formulario con tipo de codificación multipart/form-data.
     * @param     pInForm   El nombre del campo del formulario donde se definió el archivo.
     * @return    Regresa un nuevo String que contiene el applet para subir las imágenes relativas al archivo parseado.
     */
    public String uploadFileParsed(Portlet base, WBFileUpload fUp, String pInForm, String idsession) {
        StringBuffer strb = null;
        String strWorkPath = workPath;
        String strFile = null, strClientPath = "";
        int intPos;
        try {
            strFile = fUp.getFileName(pInForm);
            if (strFile != null && !strFile.trim().equals("")) {
                strFile = strFile.replace('\\', '/');
                intPos = strFile.lastIndexOf("/");
                if (intPos != -1) {
                    strClientPath = strFile.substring(0, intPos);
                    strFile = strFile.substring(intPos + 1).trim();
                }
                if (!strClientPath.endsWith("/")) {
                    strClientPath = strClientPath + "/";
                }
                strWorkPath += base.getWorkPath() + "/";
                File file = new File(strWorkPath);
                if (file != null) {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    if (file.exists() && file.isDirectory() && (strFile.endsWith(".html") || strFile.endsWith(".htm") || strFile.endsWith(".xsl") || strFile.endsWith(".xslt"))) {
                        fUp.saveFileParsed(pInForm, strWorkPath, webWorkPath + base.getWorkPath() + "/images/");
                        String strAttaches = fUp.FindAttaches(pInForm);
                        file = new File(strWorkPath + "images/");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        if (file.exists() && file.isDirectory() && strAttaches != null && !strAttaches.equals("")) {
                            strb = new StringBuffer();
                            strb.append("\n");
                            //strb.append("<OBJECT WIDTH=100% HEIGHT=100% classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" codebase=\"http://java.sun.com/products/plugin/autodl/jinstall-1_4_0-win.cab#Version=1,4,0,0\" border=0><NOEMBED><XMP>\n");
                            strb.append("<APPLET  WIDTH=100% HEIGHT=100% CODE=\"applets.dragdrop.DragDrop.class\" codebase=\"" + SWBPlatform.getContextPath() + "\" archive=\"wbadmin/lib/DragDrop.jar, wbadmin/lib/WBCommons.jar\" border=0>");
                            //strb.append("<APPLET  WIDTH=100% HEIGHT=100% CODE=\"DragDrop.class\" archive=\""+ webPath + "admin/DragDrop.jar\" border=0></XMP>");
                            //strb.append("<PARAM NAME=CODE VALUE=\"DragDrop.class\">\n");
                            //strb.append("<PARAM NAME=ARCHIVE VALUE=\""+ webPath + "admin/DragDrop.jar\">\n");
                            //strb.append("<PARAM NAME=\"cache_archive\" VALUE=\""+ webPath + "admin/DragDrop.jar\">\n");
                            //strb.append("<PARAM NAME=\"_cache_version\" VALUE=\"1.0.0.1\">\n");
                            //strb.append("<PARAM NAME=\"_cache_archive_ex\" VALUE=\"applet.jar;preload, util.jar;preload,tools.jar;preload\">\n");
                            //strb.append("<PARAM NAME=\"type\" VALUE=\"application/x-java-applet;version=1.4\">\n");
                            //strb.append("<PARAM NAME=\"scriptable\" VALUE=\"false\">\n");
                            //strb.append("<PARAM NAME =\"JSESS\" VALUE=\""+idsession+"\">\n");
                            strb.append("<PARAM NAME=\"webpath\" VALUE=\"" + SWBPlatform.getContextPath() + "\">\n");
                            strb.append("<PARAM NAME=\"foreground\" VALUE=\"000000\">\n");
                            strb.append("<PARAM NAME=\"background\" VALUE=\"979FC3\">\n");
                            strb.append("<PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\">\n");
                            strb.append("<PARAM NAME=\"backgroundSelection\" VALUE=\"666699\">\n");
                            strb.append("<PARAM NAME=\"path\" value=\"" + strWorkPath + "images/" + "\">\n");
                            strb.append("<PARAM NAME=\"clientpath\" value=\"" + strClientPath + "\">\n");
                            strb.append("<PARAM NAME=\"files\" value=\"" + strAttaches + "\">\n");
                            strb.append("<PARAM NAME=\"locale\" value=\"" + SWBUtils.TEXT.getLocale() + "\">\n");
                            //strb.append("<COMMENT>\n");
                            //strb.append("<EMBED\n");
                            //strb.append("     type=\"application/x-java-applet;version=1.4\"\n");
                            //strb.append("     CODE = \"DragDrop.class\"\n");
                            //strb.append("     ARCHIVE = \"DragDrop.jar\"\n");
                            //strb.append("     WIDTH = 100%\n");
                            //strb.append("     HEIGHT = 100%\n");
                            //strb.append("     foreground=\"979FC3\"\n");
                            //strb.append("     background=\"979FC3\"\n");
                            //strb.append("     scriptable=false\n");
                            //strb.append("     pluginspage=\"http://java.sun.com/products/plugin/index.html#download\">\n");
                            //strb.append("<NOEMBED></NOEMBED>\n");
                            //strb.append("</EMBED>\n");
                            //strb.append("</COMMENT>\n");
                            strb.append("</APPLET>\n");
                        }
                    } else {
                        uploadFile(base, fUp, pInForm);
                    }
                }
            }
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_uploadFileParsed_exc01") + " " + base.getId() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_uploadFileParsed_exc02") + " " + base.getPortletType() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_uploadFileParsed_exc03") + " " + strFile + ".", e);
        }
        if (strb != null) {
            return strb.toString();
        } else {
            return null;
        }
    }

    /**
     * Sube un archivo al filesystem. 
     *
     * @param     base      La información del recurso en memoria.
     * @param     pfUpload  Objeto que permite obtener la información del formulario con tipo de codificación multipart/form-data.
     * @param     pInForm   El nombre del campo del formulario donde se definió el archivo.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guardó.
     */
    public boolean uploadFile(Portlet base, WBFileUpload fUp, String pInForm) {

        String strWorkPath = workPath;
        String strFile = null;
        boolean bOk = false;
        try {
            strFile = fUp.getFileName(pInForm);
            if (strFile != null && !strFile.trim().equals("")) {
                strWorkPath += base.getWorkPath() + "/";
                File file = new File(strWorkPath);
                if (file != null) {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    if (file.exists() && file.isDirectory()) {
                        bOk = fUp.saveFile(pInForm, strWorkPath, new String(), new String());
                    }
                }
            }
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_uploadFile_exc01") + " " + base.getId() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_uploadFile_exc02") + " " + base.getPortletType() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_uploadFile_exc03") + " " + strFile + ".");
        }
        return bOk;
    }

    /**
     * Elimina archivos del filesystem relacionados al recurso.
     *
     * @param     base      La información del recurso en memoria.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guardó.
     */
    public String removeResource(Portlet base) {
        StringBuffer sbfRet = new StringBuffer();
        String strError = SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_htmlAlerta");
        try {
            strError += SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_removeResource_nodelete") + base.getTitle() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResourceremoveResource_tipo") + " " + base.getPortletType().getId() + " " + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_removeResource_ident") + base.getId() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_html2");

            File file = new File(workPath + base.getWorkPath());
            if (file.exists() && file.isDirectory()) {
                try {
                    SWBUtils.IO.removeDirectory(workPath + base.getWorkPath() + "/");
                    sbfRet.append(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok1") + base.getTitle() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok2") + base.getPortletType().getId() + " " + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok3") + base.getId() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ok4"));
                    sbfRet.append(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_ubica") + workPath + base.getWorkPath() + " " + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_removeResource_coment"));
                } catch (Exception e) {
                    sbfRet.append("\n<br>" + strError + "<br>\n<!-- " + e + " -->");
                    log.error(strError, e);
                }
            } else {
                sbfRet.append("\n<br>" + strError + "<br>\n<!-- Error no " + workPath + base.getWorkPath() + " -->");
            }
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_run_recerror") + base.getId() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_removeResource_tiperec") + base.getPortletType() + ".", e);
        }
        return sbfRet.toString();
    }

    /**
     * Obtiene el nombre del archivo de una ruta.
     *
     * @param     pFile     El nombre del archivo que se va a guardar.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guardó.
     */
    public String getFileName(Portlet base, String pFile) {
        String ret = "";
        try {
            ret = getFileName(pFile);
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_getFileName_exc01") + " " + base.getId() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_getFileName_exc02") + " " + base.getPortletType() + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_getFileName_excp03") + " " + pFile + ".", e);
        }
        return ret;
    }

    /**
     * Obtiene el nombre del archivo de una ruta.
     *
     * @param     pFile     El nombre del archivo que se va a guardar.
     * @return    Regresa un nuevo String que contiene el nombre del archivo que se guardó.
     */
    public String getFileName(String pFile) {
        String ret = "";
        if (pFile != null && !"".equals(pFile.trim())) {
            //ret=(new File(pFile)).getName(); 
            pFile = pFile.replace('\\', '/');
            int intPos = pFile.lastIndexOf("/");
            if (intPos != -1) {
                ret = pFile.substring(intPos + 1).trim();
            } else {
                ret = pFile;
            }
        }
        return ret;
    }

    /**
     * Valida extensión de archivo
     *
     * @param     pFile     El nombre del archivo que se va a guardar.
     * @param     pExt      Lista de extensiones dentro de las cuales el archivo debe pertenecer a alguna.
     * @return    Regresa un nuevo String que contiene la descripción del error en caso de que el archivo
     *            no cumpliera con la extensión requerida.
     */
    public boolean isFileType(String pFile, String pExt) {
        boolean bOk = false;
        try {
            String strExt = "";
            if (pFile != null && !"".equals(pFile.trim())) {
                //pFile=(new File(pFile)).getName();
                pFile = getFileName(pFile);
                int intPos = pFile.lastIndexOf(".");
                if (intPos != -1) {
                    strExt = pFile.substring(intPos + 1).trim().toLowerCase();
                    StringTokenizer strToken = new StringTokenizer(pExt, "|");
                    while (strToken.hasMoreTokens()) {
                        if (strExt.equals(strToken.nextToken())) {
                            bOk = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_util", "error_WBResource_isFileType_error") + pFile, e);
        }
        return bOk;
    }

    public String loadColorApplet(java.util.HashMap param) {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\n<applet name=\"");
        if (param.get("id") != null) {
            sbfRet.append((String) param.get("id"));
        } else {
            sbfRet.append("selColor");
        }
        sbfRet.append("\" width=\"");
        if (param.get("width") != null) {
            sbfRet.append((String) param.get("width"));
        } else {
            sbfRet.append("293");
        }
        sbfRet.append("\" height=\"");
        if (param.get("height") != null) {
            sbfRet.append((String) param.get("height"));
        } else {
            sbfRet.append("240");
        }
        sbfRet.append("\" code=\"applets.selcolor.SelColor.class\" codebase=\"" + webPath + "\" archive=\"wbadmin/lib/SelColor.jar\" border=\"0\">");
        sbfRet.append("\n<param name=\"name\" value=\"");
        if (param.get("id") != null) {
            sbfRet.append((String) param.get("id"));
        } else {
            sbfRet.append("selColor");
        }
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"cache_archive\" value=\"" + webPath + "wbadmin/lib/SelColor.jar\">");
        sbfRet.append("\n<param name=\"_cache_version\" value=\"1.0.0.1\">");
        sbfRet.append("\n<param name=\"_cache_archive_ex\" value=\"applet.jar;preload, util.jar;preload,tools.jar;preload\">");
        sbfRet.append("\n<param name=\"type\" value=\"application/x-java-applet;version=1.4\">");
        sbfRet.append("\n<param name=\"scriptable\" value=\"true\">");
        //sbfRet.append("\n<param name=\"JSESS\" value=\"");
        //if(param.get("session")!=null) sbfRet.append((String)param.get("session"));
        //sbfRet.append("\">");
        sbfRet.append("\n<param name=\"webpath\" value=\"" + webPath + "\">");
        sbfRet.append("\n<param name=\"foreground\" value=\"");
        if (param.get("foreground") != null) {
            sbfRet.append((String) param.get("foreground"));
        } else {
            sbfRet.append("99cccc");
        }
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"background\" value=\"");
        if (param.get("background") != null) {
            sbfRet.append((String) param.get("background"));
        } else {
            sbfRet.append("99cccc");
        }
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"linkcolor\" value=\"");
        if (param.get("linkcolor") != null) {
            sbfRet.append((String) param.get("linkcolor"));
        } else {
            sbfRet.append("000000");
        }
        sbfRet.append("\">");
        sbfRet.append("\n<param name=\"linkactual\" value=\"");
        if (param.get("linkactual") != null) {
            sbfRet.append((String) param.get("linkactual"));
        } else {
            sbfRet.append("000000");
        }
        sbfRet.append("\">");
        sbfRet.append("\n</applet>");
        return sbfRet.toString();
    }

    public String loadWindowConfiguration(Portlet base, org.semanticwb.portal.api.SWBParamRequest paramsRequest) {
        StringBuffer ret = new StringBuffer("");
        try {
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgMenubar") + "</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"menubar\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("menubar", ""))) {
                ret.append(" checked");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgToolbar") + "</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"toolbar\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("toolbar", ""))) {
                ret.append(" checked");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgStatusbar") + "</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"status\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("status", ""))) {
                ret.append(" checked");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgLocation") + "</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"location\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("location", ""))) {
                ret.append(" checked");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgDirectories") + "</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"directories\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("directories", ""))) {
                ret.append(" checked");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgScrollbars") + "</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"scrollbars\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("scrollbars", ""))) {
                ret.append(" checked");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgResizable") + "</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"checkbox\" name=\"resizable\" value=\"yes\"");
            if ("yes".equals(base.getAttribute("resizable", ""))) {
                ret.append(" checked");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgWidth") + " " + paramsRequest.getLocaleString("msgPixels") + ":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=width ");
            if (!"".equals(base.getAttribute("width", "").trim())) {
                ret.append(" value=\"" + base.getAttribute("width").trim() + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgHeight") + " " + paramsRequest.getLocaleString("msgPixels") + ":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=height ");
            if (!"".equals(base.getAttribute("height", "").trim())) {
                ret.append(" value=\"" + base.getAttribute("height").trim() + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgTop") + " " + paramsRequest.getLocaleString("msgPixels") + ":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=top ");
            if (!"".equals(base.getAttribute("top", "").trim())) {
                ret.append(" value=\"" + base.getAttribute("top").trim() + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td class=\"datos\">" + paramsRequest.getLocaleString("msgLeft") + " " + paramsRequest.getLocaleString("msgPixels") + ":</td> \n");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=text size=4 maxlength=4 name=left ");
            if (!"".equals(base.getAttribute("left", "").trim())) {
                ret.append(" value=\"" + base.getAttribute("left").trim() + "\"");
            }
            ret.append("></td> \n");
            ret.append("</tr> \n");
        } catch (Exception e) {
            log.error("Error while generating form to load window configuration in resource " + base.getId() + ".", e);
        }
        return ret.toString();
    }
    
    
    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función addOption() de JavaScript.
     */
    public String loadAddOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction addOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   duplicateOption(pInSel, pInTxt);");
        sbfRet.append("\n   if(swOk!=1) ");
        sbfRet.append("\n   {");
        sbfRet.append("\n       optionObj = new Option(pInTxt.value, pInTxt.value);");
        sbfRet.append("\n       pInSel.options[pInSel.length]=optionObj;");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función editOption() de JavaScript.
     */
    public String loadEditOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction editOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   pInTxt.value=pInSel.options[pInSel.selectedIndex].value;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función updateOption() de JavaScript.
     */
    public String loadUpdateOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction updateOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   duplicateOption(pInSel, pInTxt);");
        sbfRet.append("\n   if(swOk!=1)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       if(confirm('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadUpdateOption_msg") + " ' + pInSel.options[pInSel.selectedIndex].value + '?'))");
        sbfRet.append("\n       pInSel.options[pInSel.selectedIndex].value=pInTxt.value;");
        sbfRet.append("\n       pInSel.options[pInSel.selectedIndex].text=pInTxt.value;");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función deleteOption() de JavaScript.
     */
    public String loadDeleteOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction deleteOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   var aryEle = new Array();");
        sbfRet.append("\n   if(confirm('" +SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadDeleteOption_msg") + "'))");
        sbfRet.append("\n   {");
        sbfRet.append("\n       pInTxt.value='';");
        sbfRet.append("\n       for(var i=0, j=0; i<pInSel.length; i++)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           if(!pInSel[i].selected)");
        sbfRet.append("\n           {");
        sbfRet.append("\n               aryEle[j]=pInSel.options[i].value;");
        sbfRet.append("\n               j++;");
        sbfRet.append("\n           }");
        sbfRet.append("\n       }");
        sbfRet.append("\n       while(pInSel.length!=0)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           for( i=1;i<=pInSel.length;i++)");
        sbfRet.append("\n           {");
        sbfRet.append("\n               pInSel.options[0]=null;");
        sbfRet.append("\n           }");
        sbfRet.append("\n       }");
        sbfRet.append("\n       for(var i=0; i<aryEle.length; i++)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           optionObj = new Option(aryEle[i], aryEle[i]);");
        sbfRet.append("\n           pInSel.options[pInSel.length]=optionObj;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }

    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función duplicateOption() de JavaScript.
     */
    public String loadDuplicateOption()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction duplicateOption(pInSel, pInTxt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   swOk=0;");
        sbfRet.append("\n   if(pInTxt.value==null || pInTxt.value=='' || pInTxt.value==' ')");
        sbfRet.append("\n   {");
        sbfRet.append("\n       alert('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadDuplicateOption_error") + ".');");
        sbfRet.append("\n       swOk=1;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   for(var i=0; i<pInSel.length; i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       if(pInSel.options[i].value==pInTxt.value)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadDuplicateOption_msg") + " '+ pInTxt.value);");
        sbfRet.append("\n           swOk=1;");
        sbfRet.append("\n           break;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }
    
    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función isFileType() de JavaScript.
     */
    public String loadIsFileType()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isFileType(pFile, pExt)");
        sbfRet.append("\n{");
        sbfRet.append("\n   if(pFile.value.length > 0)");
        sbfRet.append("\n   {");
        sbfRet.append("\n      var swFormat=pExt + '|';");
        sbfRet.append("\n      sExt=pFile.value.substring(pFile.value.indexOf(\".\")).toLowerCase();");
        sbfRet.append("\n      var sType='';");
        sbfRet.append("\n      while(swFormat.length > 0 )");
        sbfRet.append("\n      {");
        sbfRet.append("\n         sType= swFormat.substring(0, swFormat.indexOf(\"|\"));");
        sbfRet.append("\n         if(sExt.indexOf(sType)!=-1) return true;");
        sbfRet.append("\n         swFormat=swFormat.substring(swFormat.indexOf(\"|\")+1);");
        sbfRet.append("\n      }");
        sbfRet.append("\n      while(pExt.indexOf(\"|\")!=-1) pExt=pExt.replace('|',',');");
        sbfRet.append("\n      alert(\"" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsFile_msgext") + ": \" + pExt.replace('|',','));");
        sbfRet.append("\n      return false;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   else return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }
    
    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función isNumber() de JavaScript.
     */
    public String loadIsNumber()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isNumber(pIn)");
        sbfRet.append("\n{");
        sbfRet.append("\n   pCaracter=pIn.value;");
        sbfRet.append("\n   for (var i=0;i<pCaracter.length;i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       var sByte=pCaracter.substring(i,i+1);");
        sbfRet.append("\n       if (sByte<\"0\" || sByte>\"9\")");
        sbfRet.append("\n       {");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           alert('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsNumber_msg") + ".');");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }
    
    
    /**
     * Crea una función JavaScript específica.
     *
     * @return    Regresa un nuevo String que contiene la función setPrefix() de JavaScript.
     */
    public String loadSetPrefix()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction setPrefix(pIn, pPx)");
        sbfRet.append("\n{");
        sbfRet.append("\n   if(pIn.type==\"text\")");
        sbfRet.append("\n   {");
        sbfRet.append("\n       if (pIn.value.substring(0, pPx.length).indexOf(pPx)==-1)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadSetPrefix_msg1") + ": ' + pPx);");
        sbfRet.append("\n           pIn.value=pPx+pIn.value;");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   if(pIn.type==\"select-one\" || pIn.type==\"select-multiple\")");
        sbfRet.append("\n   {");
        sbfRet.append("\n       for(var i=0; i<pIn.length; i++)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           if (pIn.options[i].value.substring(0, pPx.length).indexOf(pPx)==-1)");
        sbfRet.append("\n           {");
        sbfRet.append("\n               alert('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadSetPrefixmsg2") + ": ' + pPx);");
        sbfRet.append("\n               pIn.focus();");
        sbfRet.append("\n               return false;");
        sbfRet.append("\n           }");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");
        return sbfRet.toString();
    }
    
    
    public String loadIsHexadecimal()
    {
        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("\nfunction isHexadecimal(pIn)");
        sbfRet.append("\n{");
        sbfRet.append("\n   var swFormat=\"0123456789ABCDEF\";");
        sbfRet.append("\n   pIn.value=pIn.value.toUpperCase();");
        sbfRet.append("\n   if(pIn.value.length<7)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       alert('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsHexadecinal_msgLength") + "');");
        sbfRet.append("\n       pIn.focus();");
        sbfRet.append("\n       return false;");
        sbfRet.append("\n   }");
        sbfRet.append("\n   if (!setPrefix(pIn, '#')) return false;");
        sbfRet.append("\n   for(var i=1; i < pIn.value.length; i++)");
        sbfRet.append("\n   {");
        sbfRet.append("\n       swOk= pIn.value.substring(i, i+1);");
        sbfRet.append("\n       if (swFormat.indexOf(swOk, 0)==-1)");
        sbfRet.append("\n       {");
        sbfRet.append("\n           alert('" + SWBUtils.TEXT.getLocaleString("locale_wb2_util", "usrmsg_WBResource_loadIsHexadecinal_msgHexadecinal") + "');");
        sbfRet.append("\n           pIn.focus();");
        sbfRet.append("\n           return false;");
        sbfRet.append("\n       }");
        sbfRet.append("\n   }");
        sbfRet.append("\n   return true;");
        sbfRet.append("\n}");  
        return sbfRet.toString();
    }  
}
