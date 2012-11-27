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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResourceURL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author carlos.ramos
 */
public class RecoverPassword extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(RecoverPassword.class);
    private Templates template;
    private String webWorkPath= "/work";
    private String path = SWBPlatform.getContextPath()+"swbadmin/xsl/RetrievePassword/";
    private static final String IMG_ID = "imgseccode";
    private static final String CODE_FIELDNAME = "code";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        if(paramRequest.getCallMethod()==paramRequest.Call_STRATEGY) {
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
                out.println("<a href=\""+surl+"\" class=\"swb-rcv-stgy\" title=\""+paramRequest.getLocaleString("lblRetrievePassword")+"\">"+base.getAttribute("link")+"</a>");
            }else if( base.getAttribute("label")!=null ) {
                out.println("<form method=\"post\" action=\""+surl+"\" class=\"swb-rcv-stgy\" >");
                out.println("<input type=\"submit\" value=\""+base.getAttribute("label")+"\" />");
                out.println("</form>");
            }else if( base.getAttribute("image")!=null ) {
                out.println("<a href=\""+surl+"\" class=\"swb-rcv-stgy\" title=\""+paramRequest.getLocaleString("lblRetrievePassword")+"\">");
                out.println("<img src=\""+webWorkPath+base.getAttribute("image")+"\" alt=\""+base.getAttribute("alt",paramRequest.getLocaleString("lblRetrievePassword"))+"\" class=\"cntct-stg-img\" />");
                out.println("</a>");
            }else {
                out.println("<div class=\"swb-rcv\">");
                out.println("<a href=\""+surl+"\" class=\"swb-rcv-stgy\" title=\""+paramRequest.getLocaleString("lblRetrievePassword")+"\">"+paramRequest.getLocaleString("lblRetrievePassword")+"</a>");
                out.println("</div>");
            }
        }else {
            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println("  function changeSecureCodeImage(imgid) {");
            out.println("    var img = dojo.byId(imgid);");
            out.println("    if(img) {");
            out.println("      var rn = Math.floor(Math.random()*99999);");
            out.println("      img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn;");
            out.println("    }");
            out.println("  }");
            out.println("  function validate(frm) {");
            out.println("    var msg = [];");
            out.println("    if( isEmpty(frm.usrnm.value) )");
            out.println("      msg.push('"+paramRequest.getLocaleString("msgUserRequired")+"');");
            out.println("    if( isEmpty(frm."+CODE_FIELDNAME+".value) )");
            out.println("      msg.push('"+paramRequest.getLocaleString("msgCaptchaRequired")+"');");
            out.println("    if( msg.length==0 ) {");
            out.println("      return true;");
            out.println("    }else {");
            out.println("      alert(msg.join('\\n'));");
            out.println("      return false;");
            out.println("    }");
            out.println("  }");
            out.println("-->");
            out.println("</script>");

            out.println("<div class=\"swb-rcv\">");
            if(request.getParameter("msg")!=null)
                out.println("<p class=\"swb-rcv-msg\">"+paramRequest.getLocaleString(request.getParameter("msg"))+"</p>");
            out.println("  <form class=\"swb-rcv-frm\" id=\"rtvps\" method=\"post\" action=\""+paramRequest.getActionUrl()+"\">");
            out.println("    <div class=\"swb-rcv-d\">");
            out.println("      <label for=\"usrnm\">"+paramRequest.getLocaleString("lblUser")+"</label>");
            out.println("      <input type=\"text\" class=\"swb-rcv-in\" name=\"usrnm\" id=\"usrnm\" value=\""+(request.getParameter("username")==null?"":request.getParameter("username"))+"\"/>");
            if(request.getParameter("wrnusrnm")!=null)
                out.println(paramRequest.getLocaleString(request.getParameter("wrnusrnm")));
            out.println("    </div>");
            out.println("    <div class=\"swb-rcv-d\">");
            out.println("      <div class=\"swb-rtbpw-rctxt\">");
            out.println(paramRequest.getLocaleString("msgCanNotReadIt"));
            out.println("        &nbsp;<a onclick=\"changeSecureCodeImage('"+IMG_ID+"')\" id=\"recaptcha_reload_btn\" href=\"#\">"+paramRequest.getLocaleString("lblTryAnotherText")+"</a>");
            out.println("      </div>");
            out.println("      <div id=\"recaptcha_image\">");
            out.println("        <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" alt=\"\" id=\""+IMG_ID+"\" width=\"155\" height=\"65\" />");
            out.println("      </div>");
            out.println("      <div class=\"swb-rtbpw-ccd\">");
            out.println("        <label for=\"recaptcha_response_field\">"+paramRequest.getLocaleString("lblWriteTextFromImage")+"</label>");
            out.println("        <input type=\"text\" class=\"swb-rcv-in\" name=\""+CODE_FIELDNAME+"\" id=\"recaptcha_response_field\" value=\"\"/>");
            if(request.getParameter("wrnsc")!=null)
                out.println(paramRequest.getLocaleString(request.getParameter("wrnsc")));
            out.println("      </div>");
            out.println("    </div>");
            out.println("    <div class=\"swb-rcv-d\">");
            out.println("      <input type=\"submit\" class=\"swb-rcv-in\" value=\""+paramRequest.getLocaleString("lblRetrievePassword")+"\" onclick=\"return(validate(this.form))\"/>");
            out.println("    </div>");
            out.println("  </form>");
            out.println("</div>");

            boolean run = Boolean.parseBoolean(base.getAttribute("run"));
            if(run) {
                out.println("<a href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT)+"\" title=\"\">No recuerdo el nombre de usuario de mi cuenta</a>");
            }
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        
        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        out.println("  function changeSecureCodeImage(imgid) {");
        out.println("    var img = dojo.byId(imgid);");
        out.println("    if(img) {");
        out.println("      var rn = Math.floor(Math.random()*99999);");
        out.println("      img.src = '"+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp?nc='+rn;");
        out.println("    }");
        out.println("  }");
        out.println("  function validate(frm) {");
        out.println("    var msg = [];");
        out.println("    if( !isValidEmail(frm.email.value) )");
        out.println("      msg.push('"+paramRequest.getLocaleString("msgEmailRequired")+"');");
        out.println("    if( isEmpty(frm."+CODE_FIELDNAME+".value) )");
        out.println("      msg.push('"+paramRequest.getLocaleString("msgCaptchaRequired")+"');");
        out.println("    if( msg.length==0 ) {");
        out.println("      return true;");
        out.println("    }else {");
        out.println("      alert(msg.join('\\n'));");
        out.println("      return false;");
        out.println("    }");
        out.println("  }");
        out.println("-->");
        out.println("</script>");

        out.println("<div class=\"swb-rcv\">");
        if(request.getParameter("msg")!=null)
                out.println("<p class=\"swb-rcv-msg\">"+paramRequest.getLocaleString(request.getParameter("msg"))+"</p>");
        out.println("  <form class=\"swb-rcv-frm\" id=\"rtvps\" method=\"post\" action=\""+paramRequest.getActionUrl()+"\">");
        out.println("    <div class=\"swb-rcv-d\">");
        out.println("      <label for=\"email\">"+paramRequest.getLocaleString("lblEmail")+"</label>");
        out.println("      <input type=\"text\" class=\"swb-rcv-in\" name=\"email\" id=\"email\" value=\""+(request.getParameter("email")==null?"":request.getParameter("email"))+"\"/>");
        if(request.getParameter("wrnemail")!=null)
            out.println(paramRequest.getLocaleString(request.getParameter("wrnemail")));
        out.println("    </div>");
        out.println("    <div class=\"swb-rcv-d\">");
        out.println("      <div class=\"swb-rtbpw-rctxt\">");
        out.println(paramRequest.getLocaleString("msgCanNotReadIt"));
        out.println("        &nbsp;<a onclick=\"changeSecureCodeImage('"+IMG_ID+"')\" id=\"recaptcha_reload_btn\" href=\"#\">"+paramRequest.getLocaleString("lblTryAnotherText")+"</a>");
        out.println("      </div>");
        out.println("      <div id=\"recaptcha_image\">");
        out.println("        <img src=\""+SWBPlatform.getContextPath()+"/swbadmin/jsp/securecode.jsp\" alt=\"\" id=\""+IMG_ID+"\" width=\"155\" height=\"65\" />");
        out.println("      </div>");
        out.println("      <div class=\"swb-rtbpw-ccd\">");
        out.println("        <label for=\"recaptcha_response_field\">"+paramRequest.getLocaleString("lblWriteTextFromImage")+"</label>");
        out.println("        <input type=\"text\" class=\"swb-rcv-in\" name=\""+CODE_FIELDNAME+"\" id=\"recaptcha_response_field\" value=\"\"/>");
        if(request.getParameter("wrnsc")!=null)
            out.println(paramRequest.getLocaleString(request.getParameter("wrnsc")));
        out.println("      </div>");
        out.println("    </div>");
        out.println("    <div class=\"swb-rcv-d\">");
        out.println("      <input type=\"submit\" class=\"swb-rcv-in\" value=\""+paramRequest.getLocaleString("lblRetrieveUsername")+"\" onclick=\"return(validate(this.form))\"/>");
        out.println("    </div>");
        out.println("  </form>");
        out.println("</div>");
    }


    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = response.getResourceBase();
        User user = response.getUser();
        WebSite site = base.getWebSite();
        UserRepository ur = site.getUserRepository();

        String securCodeSent = request.getParameter(CODE_FIELDNAME);
        String securCodeCreated = (String)request.getSession(true).getAttribute("cs");

        String mode = response.getMode();

        String username = request.getParameter("usrnm")!=null && !request.getParameter("usrnm").trim().equals("")?request.getParameter("usrnm"):null;
        String email = request.getParameter("email")!=null && !request.getParameter("email").trim().equals("")?request.getParameter("email"):null;

        if(response.Mode_VIEW.equals(mode)&&username==null) {
            response.setRenderParameter("msg", "msgUserRequired");
        }else if(response.Mode_EDIT.equals(mode)&&email==null) {
            response.setRenderParameter("msg", "msgEmailRequired");
        }else if( securCodeCreated!=null && securCodeCreated.equalsIgnoreCase(securCodeSent) && !user.isSigned() ) {
            String portalname = site.getDisplayTitle(response.getUser().getLanguage());
            WebPage wp = site.getWebPage(base.getAttribute("tid"))==null?site.getHomePage():site.getWebPage(base.getAttribute("tid"));
            
            if(username!=null && ur.getUserByLogin(username)!=null) {
                user = ur.getUserByLogin(username);
                String admin = SWBPortal.getEnv("af/adminEmail");
                if(admin!=null && user.getEmail()!=null) {
                    ArrayList<InternetAddress> address = new ArrayList<InternetAddress>();
                    InternetAddress addressTo = new InternetAddress();
                    addressTo.setAddress(user.getEmail());
//System.out.println("\n\nuser="+user);
//System.out.println("login="+user.getLogin());
//System.out.println("email="+user.getEmail());
//System.out.println("admin="+admin);
                    address.add(addressTo);
                    String pw = SWBPortal.UTIL.getRandString(8);
                    String subject = response.getLocaleString("msgSubject")+" "+portalname;
                    String html = "<p>"+response.getLocaleString("msgBody")+" "+portalname+"</p><p>"+response.getLocaleString("lblNewEmailIs")+": <strong>"+pw+"</strong></p><p>"+response.getLocaleString("msgInstructions1")+"<br/>"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+wp.getRealUrl()+"</p>";
                    if( SWBUtils.EMAIL.sendMail(admin, portalname, address, null, null, subject, "HTML", html, null, null, null)!=null ) {
                        user.setPassword(pw);
                        response.setRenderParameter("msg","msgSuccessful");
                        response.setRenderParameter("username",username);
                    }else
                        response.setRenderParameter("msg","msgApologies");
                }
            }else if(email!=null && ur.getUserByEmail(email)!=null) {
                user = ur.getUserByEmail(email);
                String admin = SWBPortal.getEnv("af/adminEmail");
                if(admin!=null && user.getEmail()!=null) {
                    ArrayList<InternetAddress> address = new ArrayList<InternetAddress>();
                    InternetAddress addressTo = new InternetAddress();
                    addressTo.setAddress(user.getEmail());
                    address.add(addressTo);
                    String un = user.getLogin();
                    String subject = response.getLocaleString("msgSubject")+" "+portalname;
                    String html = "<p>"+response.getLocaleString("msgBody")+" "+portalname+"</p><p>"+response.getLocaleString("lblUserIs")+": <strong>"+un+"</strong></p><p>"+response.getLocaleString("msgInstructions2")+"<br/>"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+wp.getRealUrl()+"</p>";
                    if( SWBUtils.EMAIL.sendMail(admin, portalname, address, null, null, subject, "HTML", html, null, null, null)!=null ) {
                        response.setRenderParameter("msg","msgSuccessful");
                        response.setRenderParameter("email",email);
                    }else
                        response.setRenderParameter("msg","msgApologies");
                }
            }
        }else {
            response.setRenderParameter("msg", "msgCaptchaRequired");
        }
        request.getSession(true).removeAttribute("cs");
    }
}
