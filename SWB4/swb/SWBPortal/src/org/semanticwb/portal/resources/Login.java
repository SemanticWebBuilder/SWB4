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
import java.util.Iterator;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

public class Login extends GenericAdmResource
{
  static Logger log = SWBUtils.getLogger(Login.class);
  private static final String FRM_LOGIN = "frmlogin";
  private static final String FRM_LOGOUT = "frmlogout";
  private static final String INPLACE = "inPlace";
  private static final String TXT_login = "<fieldset><form action=\"{?loginurl}\" method=\"post\">\n<label>Usuario:</label><input type=\"text\" id=\"wb_username\" name=\"wb_username\" /><br />\n<label>Contrase&ntilde;a:</label><input type=\"password\" id=\"wb_password\" name=\"wb_password\" /><br />\n<input type=\"submit\" value=\"Enviar\" /></form></fieldset>\n";
  private static final String TXT_logout = "<a href=\"{?logouturl}\" >Logout</a>";

  private static String codigoRSA1 = "\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/jsbn.js\"></script>\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/prng4.js\"></script>\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/rng.js\"></script>\n"
            + "<script language=\"JavaScript\" type=\"text/javascript\" src=\"/swbadmin/js/crypto/rsa.js\"></script>\n"
            + "<script> \n"
            + "var rsa = new RSAKey();\n";
    private static String codigoRSA2 = "function encrypt(){\n"
            + "var res = rsa.encrypt(document.login.wb_password.value);\n"
            + "if (res){ document.login.wb_password.value=res; return true;}\n"
            + "else {return false;}\n}\n"
            + "document.login.onsubmit=encrypt;\n"
            + "</script>";
  
  public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest)
    throws SWBResourceException, IOException
  {
    //System.out.println("Entra");
    PrintWriter out = response.getWriter();
    String frmlogin = getResourceBase().getAttribute("frmlogin");
    String frmlogout = getResourceBase().getAttribute("frmlogout");
    boolean inPlace = Boolean.parseBoolean(getResourceBase().getAttribute("inPlace", "false"));
    if (("ok".equals(request.getParameter("ErrorMgs"))) && (null != request.getSession(true).getAttribute("ErrorMsg")))
    {
      out.println(new StringBuilder().append("\n<script> alert('").append(request.getSession(true).getAttribute("ErrorMsg")).append("');</script>\n").toString());
      request.getSession(true).removeAttribute("ErrorMsg");
    }
    if (null == frmlogin)
    {
      frmlogin = "<fieldset><form action=\"{?loginurl}\" method=\"post\">\n<label>Usuario:</label><input type=\"text\" id=\"wb_username\" name=\"wb_username\" /><br />\n<label>Contrase&ntilde;a:</label><input type=\"password\" id=\"wb_password\" name=\"wb_password\" /><br />\n<input type=\"submit\" value=\"Enviar\" /></form></fieldset>\n";
    }
    if (null == frmlogout)
    {
      frmlogout = "<a href=\"{?logouturl}\" >Logout</a>";
    }

    String url = null;
    if (SWBPlatform.getSecValues().isEncrypt()){
                java.security.KeyPair key = SWBPortal.getUserMgr().getSessionKey(request);
                String codigoRSA = codigoRSA1 + "rsa.setPublic(\""+ ((java.security.interfaces.RSAPublicKey)key.getPublic()).getModulus().toString(16)+"\", \""
            +((java.security.interfaces.RSAPublicKey)key.getPublic()).getPublicExponent().toString(16)+"\");\n"
                        +codigoRSA2;
                inPlace=false;
                frmlogin += codigoRSA;
                
    }
    //System.out.println(new StringBuilder().append("checa ").append(!paramsRequest.getUser().isSigned()).toString());
    if (!paramsRequest.getUser().isSigned())
    {
      if (inPlace)
      {
        SWBResourceURL aurl = paramsRequest.getActionUrl();
        aurl.setCallMethod(3);
        url = aurl.toString();
      }
      else {
        url = new StringBuilder().append(SWBPlatform.getContextPath()).append("/login/").append(paramsRequest.getWebPage().getWebSiteId()).append("/").append(paramsRequest.getWebPage().getId()).toString();
      }
      out.println(replaceTags(frmlogin, request, paramsRequest, url));
    }
    else
    {
      url = new StringBuilder().append(SWBPlatform.getContextPath()).append("/login/").append(paramsRequest.getWebPage().getWebSiteId()).append("/").append(paramsRequest.getWebPage().getId()).toString();
      out.println(replaceTags(frmlogout, request, paramsRequest, new StringBuilder().append(url).append("?wb_logout=true").toString()));
    }

    //System.out.println("Sale");
  }

  public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest)
    throws SWBResourceException, IOException
  {
    PrintWriter out = response.getWriter();
    String frmlogin = getResourceBase().getAttribute("frmlogin");
    String frmlogout = getResourceBase().getAttribute("frmlogout");
    boolean inPlace = Boolean.parseBoolean(getResourceBase().getAttribute("inPlace", "false"));
    if (null == frmlogin)
    {
      frmlogin = "<fieldset><form action=\"{?loginurl}\" method=\"post\">\n<label>Usuario:</label><input type=\"text\" id=\"wb_username\" name=\"wb_username\" /><br />\n<label>Contrase&ntilde;a:</label><input type=\"password\" id=\"wb_password\" name=\"wb_password\" /><br />\n<input type=\"submit\" value=\"Enviar\" /></form></fieldset>\n";
    }
    if (null == frmlogout)
    {
      frmlogout = "<a href=\"{?logouturl}?wb_logout=true\" >Logout</a>";
    }
    String act = request.getParameter("act");
    if (act != null)
    {
      frmlogin = request.getParameter("frmlogin");
      getResourceBase().setAttribute("frmlogin", frmlogin);
      frmlogout = request.getParameter("frmlogout");
      getResourceBase().setAttribute("frmlogout", frmlogout);
      getResourceBase().setAttribute("inPlace", request.getParameter("inPlace"));
      inPlace = Boolean.parseBoolean(getResourceBase().getAttribute("inPlace", "false"));
      try
      {
        getResourceBase().updateAttributesToDB();
      }
      catch (Exception e) {
        log.error(e);
      }
    }

    out.println("<script type=\"text/javascript\">");
    out.println("  dojo.require(\"dijit.form.Form\");");
    out.println("  dojo.require(\"dijit.form.Button\");");
    out.println("  dojo.require(\"dijit.form.CheckBox\");");
    out.println("</script>");

    out.println("<div class=\"swbform\">");
    out.println(new StringBuilder().append("<form dojoType=\"dijit.form.Form\" id=\"").append(getResourceBase().getId()).append("/loginRes\" action=\"").append(paramsRequest.getRenderUrl()).append("\" method=\"post\" >").toString());
    out.println("<input type=\"hidden\" name=\"act\" value=\"upd\">");

    out.println("<fieldset>");
    out.println("<legend>Configuraci&oacute;n Despliegue</legend>");
    out.println("<br/>");
    out.println("Forma de autenticaci&oacute;n: *Si login/encryptData es true la forma debe tener: name=\"login\"");
    out.println("<br/>");
    out.print("<textarea name=\"frmlogin\" rows=10 cols=80>");
    out.print(frmlogin);
    out.println("</textarea>");
    out.println("<br/>");
    out.println("Pie:");
    out.println("<br/>");
    out.print("<textarea name=\"frmlogout\" rows=10 cols=80>");
    out.print(frmlogout);
    out.println("</textarea>");
    out.println("<br/>Autenticar en sitio:");
    out.println("<br/>");
    String chk = inPlace ? "checked=\"checked\"" : "";
    out.println(new StringBuilder().append("<input id=\"inPlace\" dojotype=\"dijit.form.CheckBox\" name=\"inPlace\" ").append(chk).append(" value=\"true\" type=\"checkbox\" />").toString());

    out.println("<br/>");
    out.println("<font style=\"color: #428AD4; font-family: Verdana; font-size: 10px;\">");
    out.println("\t\t<b>Tags:</b><br/>");
    out.println("       &nbsp;&nbsp;{?XXXXXX} Para las formas.<BR>");
    out.println("       &nbsp;&nbsp;{?loginurl}<BR>");
    out.println("       &nbsp;&nbsp;{?logouturl}<BR>");
    out.println("       &nbsp;&nbsp;{user.login}<BR>");
    out.println("       &nbsp;&nbsp;{user.email}<BR>");
    out.println("       &nbsp;&nbsp;{user.language}<BR>");
    out.println("       &nbsp;&nbsp;{user.fullname}<BR>");
    out.println("       &nbsp;&nbsp;{user.firstname}<BR>");
    out.println("       &nbsp;&nbsp;{user.lastname}<BR>");
    out.println("       &nbsp;&nbsp;{user.secondlastname}<BR>");
    out.println("       &nbsp;&nbsp;{getEnv(\"XXXXX\")}<BR>");
    out.println("       &nbsp;&nbsp;{request.getParameter(\"XXXXX\")}<BR>");
    out.println("       &nbsp;&nbsp;{session.getAttribute(\"XXXXX\")}<BR>");
    out.println("       &nbsp;&nbsp;{encodeB64(\"XXXXX\")}<BR>");

    out.println("       &nbsp;&nbsp;{webpath}<BR>");
    out.println("       &nbsp;&nbsp;{distpath}<BR>");
    out.println("       &nbsp;&nbsp;{webworkpath}<BR>");
    out.println("       &nbsp;&nbsp;{websiteid}<BR>");
    out.println("       &nbsp;&nbsp;{workpath}<BR>");
    out.println("       &nbsp;&nbsp;{remember.field}<BR>");

    out.println("       <BR>&nbsp;&nbsp;<b>Note:</b> XXXXX=Text<BR><BR>");
    out.println("\t</font>");
    out.println("</fieldset>");
    out.println("<fieldset>");
    out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >Enviar</button>");
    out.println("</fieldset>");
    out.println("</form>");
    out.println("</div>");
  }

  private String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest, String url)
  {
    if ((str == null) || (str.trim().length() == 0))
    {
      return null;
    }
    str = str.trim();

    Iterator it = SWBUtils.TEXT.findInterStr(str, "{encodeB64(\"", "\")}");
    while (it.hasNext())
    {
      String s = (String)it.next();
      str = SWBUtils.TEXT.replaceAll(str, new StringBuilder().append("{encodeB64(\"").append(s).append("\")}").toString(), SFBase64.encodeString(replaceTags(s, request, paramRequest, url)));
    }

    it = SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
    while (it.hasNext())
    {
      String s = (String)it.next();
      str = SWBUtils.TEXT.replaceAll(str, new StringBuilder().append("{request.getParameter(\"").append(s).append("\")}").toString(), request.getParameter(replaceTags(s, request, paramRequest, url)));
    }

    it = SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
    while (it.hasNext())
    {
      String s = (String)it.next();
      str = SWBUtils.TEXT.replaceAll(str, new StringBuilder().append("{session.getAttribute(\"").append(s).append("\")}").toString(), (String)request.getSession().getAttribute(replaceTags(s, request, paramRequest, url)));
    }

    it = SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
    while (it.hasNext())
    {
      String s = (String)it.next();
      str = SWBUtils.TEXT.replaceAll(str, new StringBuilder().append("{getEnv(\"").append(s).append("\")}").toString(), SWBPlatform.getEnv(replaceTags(s, request, paramRequest, url)));
    }

    it = SWBUtils.TEXT.findInterStr(str, "{?", "}");
    while (it.hasNext())
    {
      String s = (String)it.next();
      str = SWBUtils.TEXT.replaceAll(str, new StringBuilder().append("{?").append(s).append("}").toString(), url);
    }

    str = SWBUtils.TEXT.replaceAll(str, "{rows.number}", request.getAttribute("rowsnum") != null ? (String)request.getAttribute("rowsnum") : "N/A");
    str = SWBUtils.TEXT.replaceAll(str, "{exec.time}", (String)request.getAttribute("extime"));
    str = SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getLogin());
    str = SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getEmail());
    str = SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
    str = SWBUtils.TEXT.replaceAll(str, "{user.fullname}", paramRequest.getUser().getFullName());
    str = SWBUtils.TEXT.replaceAll(str, "{user.firstname}", paramRequest.getUser().getFirstName());
    str = SWBUtils.TEXT.replaceAll(str, "{user.lastname}", paramRequest.getUser().getLastName());
    str = SWBUtils.TEXT.replaceAll(str, "{user.secondlastname}", paramRequest.getUser().getSecondLastName());
    str = SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
    str = SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
    str = SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
    str = SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
    str = SWBUtils.TEXT.replaceAll(str, "{websiteid}", paramRequest.getWebPage().getWebSiteId());
    str = SWBUtils.TEXT.replaceAll(str, "{remember.field}", "<input type=\"checkbox\" name=\"wb_rememberuser\" value=\"true\" />");

    return str;
  }

  public void processAction(HttpServletRequest request, SWBActionResponse response)
    throws SWBResourceException, IOException
  {
    UserRepository ur = response.getWebPage().getWebSite().getUserRepository();
    String authMethod = ur.getAuthMethod();
    String context = ur.getLoginContext();
    String CBHClassName = ur.getCallBackHandlerClassName();
    Subject subject = SWBPortal.getUserMgr().getSubject(request, response.getWebPage().getWebSiteId());
    try
    {
      CallbackHandler cbh = org.semanticwb.servlet.internal.Login.getHandler(CBHClassName, request, null, authMethod, response.getWebPage().getWebSiteId());

      String matchKey = new StringBuilder().append(response.getWebPage().getWebSiteId()).append("|").append(request.getParameter("wb_username")).toString();
      org.semanticwb.servlet.internal.Login.doLogin(cbh, context, subject, request, matchKey, response.getWebPage().getWebSite().getUserRepository().getId());
    }
    catch (Exception e)
    {
      org.semanticwb.servlet.internal.Login.markFailedAttepmt(request.getParameter("wb_username"));
      String alert = "No se pudo autenticar, datos incorrectos.";
      if (org.semanticwb.servlet.internal.Login.isblocked(request.getParameter("wb_username"))) {
        alert = "Usuario temporalmente inhabilitado";
      }
      request.getSession(true).setAttribute("ErrorMsg", alert);
      response.setRenderParameter("ErrorMgs", "ok");
      response.setCallMethod(1);
      return;
    }
    response.sendRedirect(response.getWebPage().getRealUrl());
  }
}