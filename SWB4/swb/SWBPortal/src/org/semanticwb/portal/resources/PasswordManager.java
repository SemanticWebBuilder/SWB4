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
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordManager.
 * 
 * @author serch
 */
public class PasswordManager extends GenericResource {

    /** The SIZE. */
    static int SIZE = 16;
    
    /** The letras. */
    static String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    
    /** The generator. */
    static SecureRandom generator = new SecureRandom();

    /** The priv. */
    static private String priv=null;

    static {
        generator.setSeed(System.currentTimeMillis());
//        SemanticProperty sp = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getSemanticProperty(SWBPlatform.getSemanticMgr().SWBAdmin + "/PrivateKey");
        priv = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getModelObject().getProperty(SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getSemanticProperty(
                SWBPlatform.getSemanticMgr().SWBAdminURI + "/PrivateKey"));
        if (priv==null)priv="TOO MANY SECRETS";
    }
    
    /** The log. */
    static Logger log = SWBUtils.getLogger(PasswordManager.class);
    
    /** The Constant FRM_CHGPWD. */
    private static final String FRM_CHGPWD = "frmchgpwd";
    
    /** The Constant FRM_NEWPWD. */
    private static final String FRM_NEWPWD = "frmnewpwd";
    
    /** The Constant FRM_LOGREQ. */
    private static final String FRM_LOGREQ = "frmlogreq";
    
    /** The Constant FRM_MAILMS. */
    private static final String FRM_MAILMS = "frmmailms";
    
    /** The Constant TXT_chgpwd. */
    private static final String TXT_chgpwd = "<fieldset><form action=\"{actionurlUPG}\" method=\"post\">\n<label>Cambio password:</label><input type=\"password\" id=\"swb_newPassword\" name=\"swb_newPassword\" /><br />\n<label>Confirmar password:</label><input type=\"password\" id=\"swb_newPassword2\" name=\"swb_newPassword2\" /><br />\n<input type=\"submit\" value=\"Enviar\" /><input type=\"hidden\" name=\"cadcontrol\" value=\"{?cadcontrol}\" /></form></fieldset>\n";
    
    /** The Constant TXT_newpwd. */
    private static final String TXT_newpwd = "<fieldset><form action=\"{actionurlADD}\" method=\"post\">\n<label>Nuevo password:</label><input type=\"password\" id=\"swb_newPassword\" name=\"swb_newPassword\" /><br />\n<label>Confirmar password:</label><input type=\"password\" id=\"swb_newPassword2\" name=\"swb_newPassword2\" /><br />\n<input type=\"submit\" value=\"Enviar\" /><input type=\"hidden\" name=\"cadcontrol\" value=\"{?cadcontrol}\" /></form></fieldset>\n";
    
    /** The Constant TXT_logreq. */
    private static final String TXT_logreq = "<fieldset><form action=\"{actionurlSML}\" method=\"post\">\n<label>Login:</label><input type=\"text\" id=\"swb_login\" name=\"swb_login\" /><br />\n<input type=\"submit\" value=\"Enviar\" /><input type=\"hidden\" name=\"cadcontrol\" value=\"{?cadcontrol}\" /></form></fieldset>\n";
    
    /** The Constant TXT_mailms. */
    private static final String TXT_mailms = "Ha solicitado recuperar su contraseña, para continuar acuda a {?url}";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            if (null != request.getSession(true).getAttribute("message")) {
                String message = (String) request.getSession(true).getAttribute("message");
                response.getWriter().println(message);
                request.getSession(true).removeAttribute("message");
            } else if (null != gotValidTokenLogin(request)) {
                showNewPassword(request, response, paramRequest, gotValidTokenLogin(request));
            } else if (paramRequest.getUser().isSigned()) {
                showPasswordChange(request, response, paramRequest);
            } else {
                showLoginRequest(request, response, paramRequest);
            }
        } catch (GeneralSecurityException ex) {
            log.error(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        //System.out.println("En ProcessAction");
        Enumeration enumera = request.getSession(true).getAttributeNames();
        while (enumera.hasMoreElements()) {
            String name = (String) enumera.nextElement();
            //System.out.println("" + name + ":" + request.getSession(true).getAttribute(name));
        }
        String cadcontrol = (String) request.getSession(true).getAttribute("cadcontrol");
        if (cadcontrol != null && cadcontrol.equals(request.getParameter("cadcontrol"))) {
            if (response.getAction().equals("UPG")) {
                //System.out.println("en UPG");
                String pwd1 = request.getParameter("swb_newPassword");
                String pwd2 = request.getParameter("swb_newPassword2");
                if (pwd2.equals(pwd1)) {
                    response.getUser().setPassword(pwd2);
                    request.getSession(true).setAttribute("message", "<p>Contrase&ntilde;a actualizada</p>");
                } else {
                    request.getSession(true).setAttribute("message", "<p>Contrase&ntilde;as no corresponden.</p>");
                }
            }
            if (response.getAction().equals("ADD")) {
                //System.out.println("en ADD");
                String login = (String) request.getSession(true).getAttribute("login");
                if (null != login) {
                    User usr = response.getWebPage().getWebSite().getUserRepository().getUserByLogin(login);
                    String pwd1 = request.getParameter("swb_newPassword");
                    String pwd2 = request.getParameter("swb_newPassword2");
                    if (pwd2.equals(pwd1)) {
                        usr.setPassword(pwd2);
                        request.getSession(true).setAttribute("message", "<p>Contrase&ntilde;a actualizada</p>");
                    }
                } else {
                    request.getSession(true).setAttribute("message", "<p>Contrase&ntilde;as no corresponden.</p>");
                }
            }
            if (response.getAction().equals("SML")) {
                //System.out.println("en SML");
                String frmmailms = getResourceBase().getAttribute(FRM_MAILMS);
                if (null == frmmailms) {
                    frmmailms = TXT_mailms;
                }
                String login = request.getParameter("swb_login");
                if (null != login) {
                    User usr = response.getWebPage().getWebSite().getUserRepository().getUserByLogin(login);
                    if (null == usr) usr = response.getWebPage().getWebSite().getUserRepository().getUserByEmail(login);
                    if (null != usr) {
                        try {
                            String email = usr.getEmail();
                            String token = (request.isSecure() ? "HTTPS://" : "HTTP://") +
                                    request.getServerName() +
                                    (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + response.getWebPage().getRealUrl() + "/_tkn/" + generateToken(usr.getLogin());
                            SWBParamRequestImp paramRequest = new SWBParamRequestImp(request, getResourceBase(),response.getWebPage(), usr);
                            String texto = replaceTags(frmmailms, request, paramRequest, token);
                            //System.out.println("URL:" + texto);
                            SWBUtils.EMAIL.sendBGEmail(email, "Recuperar password", texto);
                            request.getSession(true).setAttribute("message", "<br /><p>Te llegar&aacute; un correo electr&oacute;nico a tu cuenta, indic&aacute;ndote c&oacute;mo recuperarla.</p>");
                        } catch (GeneralSecurityException ex) {
                            log.error(ex);
                            request.getSession(true).setAttribute("message", "Error procesando informaci&oacute;n");
                        }
                    }
                }
            //Send Mail
            }
        } else {
            request.getSession(true).setAttribute("message", "session inv&aacute;lida!");
        }

    }

    /**
     * Show password change.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void showPasswordChange(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String frmcontent = getResourceBase().getAttribute(FRM_CHGPWD);
        if (null == frmcontent) {
            frmcontent = TXT_chgpwd;
        }
        String cadcontrol = getCadControl();
        request.getSession(true).setAttribute("cadcontrol", cadcontrol);
        out.println(replaceTags(frmcontent, request, paramRequest, cadcontrol));

    }

    /**
     * Show login request.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void showLoginRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String frmcontent = getResourceBase().getAttribute(FRM_LOGREQ);
        if (null == frmcontent) {
            frmcontent = TXT_logreq;
        }
        String cadcontrol = getCadControl();
        request.getSession(true).setAttribute("cadcontrol", cadcontrol);
        out.println(replaceTags(frmcontent, request, paramRequest, cadcontrol));
    }

    /**
     * Show new password.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @param login the login
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void showNewPassword(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String login) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String frmcontent = getResourceBase().getAttribute(FRM_NEWPWD);
        if (null == frmcontent) {
            frmcontent = TXT_newpwd;
        }
        String cadcontrol = getCadControl();
        request.getSession(true).setAttribute("cadcontrol", cadcontrol);
        request.getSession(true).setAttribute("login", login);
        out.println(replaceTags(frmcontent, request, paramRequest, cadcontrol));
    }

    /**
     * Replace tags.
     * 
     * @param str the str
     * @param request the request
     * @param paramRequest the param request
     * @param url the url
     * @return the string
     */
    String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest, String url) {
        //System.out.print("\nstr:"+str+"-->");
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        str = str.trim();
        //TODO: codificar cualquier atributo o texto
        Iterator it = SWBUtils.TEXT.findInterStr(str, "{encodeB64(\"", "\")}");
        while (it.hasNext()) {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{encodeB64(\"" + s + "\")}", SFBase64.encodeString(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while (it.hasNext()) {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\"" + s + "\")}", request.getParameter(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while (it.hasNext()) {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\"" + s + "\")}", (String) request.getSession().getAttribute(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while (it.hasNext()) {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{getEnv(\"" + s + "\")}", SWBPlatform.getEnv(replaceTags(s, request, paramRequest, url)));
        }

        it = SWBUtils.TEXT.findInterStr(str, "{?", "}");
        while (it.hasNext()) {
            String s = (String) it.next();
            str = SWBUtils.TEXT.replaceAll(str, "{?" + s + "}", url);
        }

        str = SWBUtils.TEXT.replaceAll(str, "{rows.number}", request.getAttribute("rowsnum") != null ? (String) request.getAttribute("rowsnum") : "N/A");
        str = SWBUtils.TEXT.replaceAll(str, "{exec.time}", (String) request.getAttribute("extime"));
        if (null != paramRequest) {
            User usr = paramRequest.getUser();
            str = SWBUtils.TEXT.replaceAll(str, "{user.login}", usr.getLogin());
            str = SWBUtils.TEXT.replaceAll(str, "{user.email}", usr.getEmail());
            str = SWBUtils.TEXT.replaceAll(str, "{user.language}", usr.getLanguage());
        }
        str = SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
        str = SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str = SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
        str = SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
        if (null != paramRequest) {
            str = SWBUtils.TEXT.replaceAll(str, "{websiteid}", paramRequest.getWebPage().getWebSiteId());
            str = SWBUtils.TEXT.replaceAll(str, "{actionurl}", paramRequest.getActionUrl().toString());
            str = SWBUtils.TEXT.replaceAll(str, "{actionurlUPG}", paramRequest.getActionUrl().setAction("UPG").toString());
            str = SWBUtils.TEXT.replaceAll(str, "{actionurlADD}", paramRequest.getActionUrl().setAction("ADD").toString());
            str = SWBUtils.TEXT.replaceAll(str, "{actionurlSML}", paramRequest.getActionUrl().setAction("SML").toString());
        }
        str = SWBUtils.TEXT.replaceAll(str, "{?url}", url);
        //System.out.println(str);
        return str;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String frmchgpwd = getResourceBase().getAttribute(FRM_CHGPWD);
        String frmnewpwd = getResourceBase().getAttribute(FRM_NEWPWD);
        String frmlogreq = getResourceBase().getAttribute(FRM_LOGREQ);
        String frmmailms = getResourceBase().getAttribute(FRM_MAILMS);
        if (null == frmchgpwd) {
            frmchgpwd = TXT_chgpwd;
        }
        if (null == frmnewpwd) {
            frmnewpwd = TXT_newpwd;
        }
        if (null == frmlogreq) {
            frmlogreq = TXT_logreq;
        }
        if (null == frmmailms) {
            frmmailms = TXT_mailms;
        }

        String act = request.getParameter("act");
        if (act != null) {
            frmchgpwd = request.getParameter(FRM_CHGPWD);
            getResourceBase().setAttribute(FRM_CHGPWD, frmchgpwd);
            frmnewpwd = request.getParameter(FRM_NEWPWD);
            getResourceBase().setAttribute(FRM_NEWPWD, frmnewpwd);
            frmlogreq = request.getParameter(FRM_LOGREQ);
            getResourceBase().setAttribute(FRM_LOGREQ, frmlogreq);
            frmmailms = request.getParameter(FRM_MAILMS);
            getResourceBase().setAttribute(FRM_MAILMS, frmmailms);
            try {
                getResourceBase().updateAttributesToDB();
            } catch (Exception e) {
                log.error(e);
            }
        }

        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require(\"dijit.form.Form\");");
        out.println("  dojo.require(\"dijit.form.Button\");");
        out.println("</script>");

        out.println("<div class=\"swbform\">");
        out.println("<form dojoType=\"dijit.form.Form\" id=\"" + getResourceBase().getId() + "/sparql\" action=\"" + paramsRequest.getRenderUrl() + "\" method=\"post\" >");
        out.println("<input type=\"hidden\" name=\"act\" value=\"upd\">");

        out.println("<fieldset>");
        out.println("<legend>Configuraci&oacute;n Despliegue</legend>");
        out.println("<br/>");
        out.println("Forma de Solicitud password:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_CHGPWD + "\" rows=10 cols=80>");
        out.print(frmchgpwd);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Forma de Solicitud nuevo password:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_NEWPWD + "\" rows=10 cols=80>");
        out.print(frmnewpwd);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Forma de Solicitud login:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_LOGREQ + "\" rows=10 cols=80>");
        out.print(frmlogreq);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Texto Correo:");
        out.println("<br/>");
        out.print("<textarea name=\"" + FRM_MAILMS + "\" rows=10 cols=80>");
        out.print(frmmailms);
        out.println("</textarea>");
        out.println("<br/>");

        out.println("<font style=\"color: #428AD4; font-family: Verdana; font-size: 10px;\">");
        out.println("		<b>Tags:</b><br/>");
        out.println("       &nbsp;&nbsp;{?XXXXXX} Para las formas.<BR>");
        out.println("       &nbsp;&nbsp;{?loginurl}<BR>");
        out.println("       &nbsp;&nbsp;{?logouturl}<BR>");
        out.println("       &nbsp;&nbsp;{user.login}<BR>");
        out.println("       &nbsp;&nbsp;{user.email}<BR>");
        out.println("       &nbsp;&nbsp;{user.language}<BR>");
        out.println("       &nbsp;&nbsp;{getEnv(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{request.getParameter(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{session.getAttribute(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{encodeB64(\"XXXXX\")}<BR>");

        out.println("       &nbsp;&nbsp;{webpath}<BR>");
        out.println("       &nbsp;&nbsp;{distpath}<BR>");
        out.println("       &nbsp;&nbsp;{webworkpath}<BR>");
        out.println("       &nbsp;&nbsp;{websiteid}<BR>");
        out.println("       &nbsp;&nbsp;{workpath}<BR>");
        out.println("       &nbsp;&nbsp;{actionurl}<BR>");
        out.println("       &nbsp;&nbsp;{actionurlUPG}<BR>");
        out.println("       &nbsp;&nbsp;{actionurlADD}<BR>");
        out.println("       &nbsp;&nbsp;{actionurlSML}<BR>");

        out.println("       <BR>&nbsp;&nbsp;<b>Note:</b> XXXXX=Text<BR><BR>");
        out.println("	</font>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >Enviar</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");

    }

    /**
     * Gets the cad control.
     * 
     * @return the cad control
     */
    String getCadControl() {
        StringBuilder sb = new StringBuilder(SIZE);
        for (int i = 0; i < SIZE; i++) {
            sb.append(letras.charAt(generator.nextInt(letras.length())));
        }
        return sb.toString();
    }

    /**
     * Generate token.
     * 
     * @param login the login
     * @return the string
     * @throws GeneralSecurityException the general security exception
     */
    String generateToken(String login) throws GeneralSecurityException {
        String value = login + "|" + (System.currentTimeMillis() + 1000L * 60 * 60 * 48) + "|" +
                SWBPlatform.getVersion();
        value = SFBase64.encodeBytes(SWBUtils.CryptoWrapper.PBEAES128Cipher(priv, value.getBytes()), false);
        return value;
    }

    /**
     * Got valid token login.
     * 
     * @param request the request
     * @return the string
     * @throws GeneralSecurityException the general security exception
     */
    String gotValidTokenLogin(HttpServletRequest request) throws GeneralSecurityException {
        StringBuffer url = request.getRequestURL();
        String ret = null;
        if (url.lastIndexOf("_tkn/") > 1) {
            String value = url.substring(url.lastIndexOf("_tkn/") + 5);
            //System.out.println("val:" + value);
            value = new String(SWBUtils.CryptoWrapper.PBEAES128Decipher(priv, SFBase64.decode(value)));
            String[] lista = value.split("\\|");

            if (System.currentTimeMillis() < Long.valueOf(lista[1]) && SWBPlatform.getVersion().equals(lista[2])) {
                ret = lista[0];
            }
            //System.out.println(value);
            //System.out.println(lista[0]);
            //System.out.println(lista[1]);
            //System.out.println(lista[2]);
        }
        return ret;
    }
}
