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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * Recurso que permite que el usuario final se firme en el sitio. Utilizando un
 * archivo xslt para mostrar la forma para pedir usuario y contraseña.
 * <p/>
 * Resource that allows that the end user signs itself in the site. Using
 * file xslt to show the form to request user and password.
 * <p/>
 * <p/>
 * User: Sergio Martínez
 * Date: 14/10/2004
 * Time: 06:04:00 PM
 */
public class LoginOld extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(LoginOld.class);
    
    /** The global. */
    boolean global = false;
    //LoginInterface li = null;
    /** The list. */
    Hashtable list = null;
    
    /** The templates. */
    Templates templates = null;
    
    /** The base. */
    Resource base = null;
    
    /** The path. */
    String path = SWBPlatform.getContextPath() + "swbadmin/xsl/Login/";
    
    /** The redirect. */
    String redirect = SWBPlatform.getContextPath();
    
    /** The msg. */
    String msg = null;
    
    /** The flag. */
    boolean flag = false;

    /**
     * Initializes the Login resource loading a set of classes and xslt templates.
     * 
     * @throws AFException if something goes wrong
     * @throws SWBResourceException the sWB resource exception
     */
    public void init() throws SWBResourceException {
        // super.init();
        base = super.getResourceBase();
        log.debug("base.getRecResource().getTopicMapId(): " + base.getWebSiteId());
        if (base.getWebSiteId().equals("WBAGlobal")) {
            global = true;
            Iterator <WebSite> itWs = SWBContext.listWebSites();
            list = new Hashtable();
            while (itWs.hasNext()) {
                WebSite ws = itWs.next();
                try {
                    ClassLoader loader = SWBPortal.getResourceMgr().getResourceLoader(getClass().getName());
                    //TODO:VER 4.0 Class cls = loader.loadClass(DBUser.getInstance(tm.getDbdata().getRepository()).getProperty("loginInterface", ""));
                    //TODO:VER 4.0 list.put(ws.getId(), cls.newInstance());
                }
                catch (Exception e) {
                    log.error("Exception Initiating a Global Login resource " + base.getId(), e);
                }
            }
        } else {
            WebSite ws=base.getWebSite();
            try {
                ClassLoader loader = SWBPortal.getResourceMgr().getResourceLoader(getClass().getName());
                //TODO:VER Class cls = loader.loadClass(DBUser.getInstance(tm.getDbdata().getRepository()).getProperty("loginInterface", ""));
                //TODO:VER li = (LoginInterface) cls.newInstance();
            }
            catch (Exception e) {
               log.error("Exception Initiating a Login resource " + base.getId(), e);
            }
        }
        /*
       if (base.getAttribute("XSLT") != null) {
         try {
           templates = AFUtils.getInstance().loadTemplateXSLT(WBUtils.getInstance().getFileFromWorkPath(base.getResourceWorkPath() + "/" + base.getAttribute("XSLT")));
         }
         catch (TransformerConfigurationException e) {
           AFUtils.log(e, "Exception seting XSLT transformer in Login Resource " + base.getId());
         }
         catch (RuntimeException e) {
           AFUtils.log(e, "Runtime Exception in Login Resource " + base.getId());
         }
       }
        */
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
     */
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
        }
        catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }
        if (!"".equals(base.getAttribute("rpath", "").trim()))
            redirect = base.getAttribute("rpath", "").trim();
        msg = base.getAttribute("alert", "");
        try {
            if ("true".equalsIgnoreCase(base.getAttribute("autoval", "false"))) flag = true;
            else flag = false;
        } catch (Exception ignore) {
        }
        if (!"".equals(base.getAttribute("XSLT", "").trim())) {
            try {
                templates = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("XSLT").trim()));
                path = SWBPortal.getWebWorkPath() + base.getWorkPath() + "/";
            }
            catch (Exception e) {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
        }
        if (templates == null) {
            try {
                templates = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Login/Login.xslt"));
            }
            catch (Exception e) {
                log.error("Error while loading default resource template: " + base.getId(), e);
            }
        }
    }


    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */
    /* TODO: VER4.0
    public void doView(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException {
        LoginInterface logIn = null;
        if (global) {
            logIn = (LoginInterface) list.get(paramsRequest.getTopic().getMap().getId());
        } else {
            logIn = li;
        }
        PrintWriter out = response.getWriter();
        String result = logIn.processSessionControl(request, response, paramsRequest.getUser(),
                paramsRequest.getTopic().getMap().getDbdata().getRepository(), paramsRequest.getTopic());
        AFUtils.debug(result, 6);
        if (WBResourceURL.Call_DIRECT == paramsRequest.getCallMethod()) {
            response.setContentType("text/html");
            if (null != request.getParameter("_wb_username") || null != request.getParameter("wb_username")) {
                if (!paramsRequest.getUser().isLoged()) {
                    out.println("<script type=\"text/javascript\">alert('" + msg + "');</script>");
                }
            }
            String url = paramsRequest.getTopic().getUrl();
            out.println("<http><head><META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + url + "\"></head></http>");
        } else {
            try {

                Transformer tra = templates.newTransformer();
                ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
                tra.setOutputProperty("encoding", "ISO-8859-1");
                tra.setParameter("UrlRendered", WBUtils.getInstance().getWebPath() + "login/" + paramsRequest.getTopic().getMap().getId() + "/" + paramsRequest.getTopic().getId() + "?_wb_logout=yes&amp;_wb_goto=" + redirect);
                if (flag)
                    tra.setParameter("UrlLogin", paramsRequest.getRenderUrl().setCallMethod(WBResourceURL.Call_DIRECT));
                else
                    tra.setParameter("UrlLogin", WBUtils.getInstance().getWebPath() + "login/" + paramsRequest.getTopic().getMap().getId() + "/" + paramsRequest.getTopic().getId());
                tra.setParameter("userName", paramsRequest.getUser().getName());
                tra.transform(new DOMSource(AFUtils.getInstance().XmltoDom(result)), new StreamResult(sw));
                out.println(sw);
            }
            catch (TransformerConfigurationException e) {
                AFUtils.log(e, "Problem with the transformer in Login...");
            }
            catch (TransformerException e) {
                AFUtils.log(e, "Problem transforming...");
            }
        }
    }
    **/
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */
  /*  public void doAdmin(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException {
        AFUtils.debug("Starting Admin of Login....", 6);
//    StringBuffer ret = new StringBuffer("");
//        String msg = "Undefined operation on resource.";
        String action = request.getParameter("act");
        if (action == null || (action != null && action.trim().equals(""))) action = paramsRequest.getAction();
        AFUtils.debug("The Action was " + action, 6);
        /*if (action.equals("add") || action.equals("edit")) {
         if (action.equals("edit") && request.getParameter("msg") != null) {
           ret.append("<script language=\"JavaScript\">\n" +
                      "   alert('" + request.getParameter("msg") + "');\n" +
                      "</script>\n");
         }
       }
       else */
 /*       if (action.equals("update")) {   // Add or update resource.
            WBFileUpload requestparams = new WBFileUpload();
            requestparams.getFiles(request);
            StringBuffer strbxmlp = new StringBuffer();
            strbxmlp.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><resource>");
            Iterator paramlist = requestparams.getParamNames().iterator();
            while (paramlist.hasNext()) {
                String param = (String) paramlist.next();
                /*     System.out.println("param: "+param);
                   if ("rpath".equals(param)) {
                       strbxmlp.append("<" + param + ">" + requestparams.getValue(param) + "</" + param + ">");
                       continue;
                   }
                */
   /*             String filename = requestparams.getFileName(param);
                AFUtils.debug("Testing field " + param + "-" + filename, 6);
                //AFUtils.de("Testing field " + param + "-" + filename,true);
                if (filename == null) {
                    Iterator values = requestparams.getValue(param).iterator();
                    StringBuffer value = new StringBuffer();
                    while (values.hasNext()) {
                        String strTmp = ((String) values.next()).trim();
                        AFUtils.debug("strTmp: " + strTmp, 6);
                        if (!"".equals(strTmp)) {
                            value.append("|");
                            value.append(strTmp);
                        }
                    }
                    AFUtils.debug("value: " + value, 6);
                    if (value.length() > 0) {
                        strbxmlp.append("<");
                        strbxmlp.append(param);
                        strbxmlp.append(">");
                        strbxmlp.append(value.toString().substring(1).trim());
                        strbxmlp.append("</");
                        strbxmlp.append(param);
                        strbxmlp.append(">");
                    }
                } else {
                    AFUtils.debug("Atempt to check for file -" + filename + "-" + param, 6);
                    if (!"".equalsIgnoreCase(filename.trim())) {
                        int i = filename.lastIndexOf("\\");
                        if (i != -1) {
                            filename = filename.substring(i + 1);
                        }
                        i = filename.lastIndexOf("/");
                        if (i != -1) {
                            filename = filename.substring(i + 1);
                        }
                        //  String imgapplet = WBResUtils.getInstance().uploadFileParsed(base, requestparams, param, request.getSession().getId());
                        //System.out.println(imgapplet);
                    } else {
                        try {
                            filename = base.getDom().getElementsByTagName(param).item(0).getFirstChild().getNodeValue();
                        }
                        catch (Exception e) {
                        } // try to get a value, but it doesn't matter if no value is found.
                    }
                    strbxmlp.append("<");
                    strbxmlp.append(param);
                    strbxmlp.append(">");
                    strbxmlp.append(filename.trim());
                    strbxmlp.append("</");
                    strbxmlp.append(param);
                    strbxmlp.append(">");
                }
            }
            strbxmlp.append("</resource>");
            base.getRecResource().setXml(strbxmlp.toString());
            base.getRecResource().update(paramsRequest.getUser().getId(), "Resource updated with id: " + base.getId());
            base.setRecResource(base.getRecResource());
//        AFUtils.debug("********************** Updated Base: "+ AFUtils.getInstance().DomtoXml(base.getDom()),6); //TODO Quitar esta linea
            if (!"".equals(path)) base.setAttribute("path", path);
            this.init();
        } else if (action.equals("remove")) msg = WBResourceUtils.getInstance().removeResource(base);
        java.io.PrintWriter out = response.getWriter();
        out.println("<div class=\"box\">");
        String tmp = null;
        String lpath = "";
        String alert = "";
        boolean autoval = false;
        String sautoval = "false";
        try {
            tmp = base.getDom().getElementsByTagName("XSLT").item(0).getFirstChild().getNodeValue();
        } catch (Exception e) {
            tmp = "";
        }

        try {
            lpath = base.getDom().getElementsByTagName("rpath").item(0).getFirstChild().getNodeValue();
        } catch (Exception ignore) {

        }
        try {
            sautoval = base.getDom().getElementsByTagName("autoval").item(0).getFirstChild().getNodeValue();
        } catch (Exception ignore) {

        }
        try {
            alert = base.getDom().getElementsByTagName("alert").item(0).getFirstChild().getNodeValue();
        } catch (Exception ignore) {

        }
        if ("true".equalsIgnoreCase(sautoval)) autoval = true;
        else autoval = false;
        AFUtils.debug("Autoval value: " + sautoval + " ----> " + autoval);
        WBResourceURL url = paramsRequest.getRenderUrl();
        url.setMode(WBParamRequest.Mode_ADMIN);
        url.setAction("update");
        out.println("<div class=\"datos\">");
        if (path.indexOf(base.getResourceWorkPath()) != -1) out.println(paramsRequest.getLocaleString("actual"));
        else out.println(paramsRequest.getLocaleString("msgByDefault"));
        out.println(": </div><div class=\"link\">");
        if (path.indexOf(base.getResourceWorkPath()) != -1)
            out.println("<a href=\"" + WBUtils.getInstance().getWebWorkPath() + base.getResourceWorkPath() + "/" + tmp + "\" target=_blank>" + tmp);
        else
            out.println("<a href=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/xsl/Login/Login.xslt\" target=_blank>Login.xslt");
        out.println("</a></div>");
        out.println("<form action=\"" + url + "\" method=\"post\" enctype=\"multipart/form-data\" name=\"LoginAdmResource\">\n" +
                "<input type=hidden name=XSLT value=\"" + tmp + "\" >" +
                "  <table  border=\"0\" cellspacing=\"5\" cellpadding=\"5\">\n" +
                "    <tr>\n" +
                "      <td width=\"150\" class=\"datos\">" + paramsRequest.getLocaleString("file") + ": </td>\n" +
                "      <td class=\"valores\"><input type=\"file\" name=\"XSLT\"></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"150\" class=\"datos\">" + paramsRequest.getLocaleString("path") + ": </td>\n" +
                "      <td class=\"valores\"><input type=\"text\" name=\"rpath\" value=\"" + lpath + "\"></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"150\" class=\"datos\">" + paramsRequest.getLocaleString("autoval") + ": </td>\n" +
                "      <td class=\"valores\"><select name=\"autoval\"><option value=\"true\"" +
                (autoval ? " selected" : "")
                + ">true</option><option value=\"false\"" +
                (!(autoval) ? " selected" : "")
                + ">false</option></select></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"150\" class=\"datos\">" + paramsRequest.getLocaleString("alert") + ": </td>\n" +
                "      <td class=\"valores\"><input type=\"text\" name=\"alert\" value=\"" + alert + "\"></td>\n" +
                "    </tr>\n" +

                "    <tr>\n" +
                "      <td colspan=\"2\"><center>\n" +
                "        <input type=\"submit\" name=\"Submit\" value=\"" + paramsRequest.getLocaleString("btnSubmit") + "\" class=\"boton\">\n" +
                "      </center></td>\n" +
                "    </tr>\n" +
                "  </table><div class=\"datos\">\n" + paramsRequest.getLocaleString("message") +
                "</div></form>");
        out.println("</div>");
    } */

}
