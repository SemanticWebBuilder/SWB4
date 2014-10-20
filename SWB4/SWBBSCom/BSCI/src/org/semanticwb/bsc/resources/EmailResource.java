
package org.semanticwb.bsc.resources;

import com.oreilly.servlet.MultipartRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.mail.EmailAttachment;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.bsc.utils.EmailLog;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author ana.garcias
 */
public class EmailResource extends GenericResource {

    private static final org.semanticwb.Logger log = SWBUtils.getLogger(EmailResource.class);
    private static final String Mode_SendMail = "mail";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
            doViewStrategy(request, response, paramRequest);
        } else {
            doMail(request, response, paramRequest);
        }
    }

    /**
     * Genera el despliegue de la vista del formulario para enviar un correo.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos
     * adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    public void doMail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        final User user = paramRequest.getUser();

        if (!user.isSigned()) {
            EmailResource.log.error("El usuario no esta logueado.");
            return;
        }
        
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();

        Iterator<User> itTo = wsite.getUserRepository().listUsers();
        Iterator<User> itCc = wsite.getUserRepository().listUsers();
        //SWBResourceURL url = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_STRATEGY);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction(SWBResourceURL.Action_ADD);
        url.setParameter("suri", request.getParameter("suri"));
        
        StringBuilder toReturn = new StringBuilder();


        
        toReturn.append("<script type=\"text/javascript\">").append("\n");        
        toReturn.append("  dojo.require('dijit.form.Form');").append("\n");        
        toReturn.append("function validateFrm() {").append("\n");
        toReturn.append("  var isValid = true;").append("");
        // To
        toReturn.append("  var txt = dojo.byId('toText').value").append("\n");
        toReturn.append("  if( isEmpty(txt) ) {").append("\n");
        toReturn.append("    alert('"+paramRequest.getLocaleString("msg_UnspecifiedRecipient")+"');").append("\n");
        toReturn.append("    return false;").append("\n");
        toReturn.append("  }").append("\n");
        toReturn.append("  txt = txt.substring(0,txt.lastIndexOf(';'));").append("\n");
        toReturn.append("  isValid = txt.split(';').every(isEmail);").append("\n");
        toReturn.append("  if( !isValid ) {").append("\n");
        toReturn.append("    alert('"+paramRequest.getLocaleString("msg_EmailAddressNotRecognized") +"');").append("\n");
        toReturn.append("    return false;").append("\n");
        toReturn.append("  }").append("\n");
        // Cc
        toReturn.append("  txt = dojo.byId('ccText').value").append("\n");
        toReturn.append("  if( !isEmpty(txt) ) {").append("\n");
        toReturn.append("    txt = txt.substring(0,txt.lastIndexOf(';'));").append("\n");
        toReturn.append("    isValid = txt.split(';').every(isEmail);").append("\n");
        toReturn.append("    if( !isValid ) {").append("\n");
        toReturn.append("      alert('"+paramRequest.getLocaleString("msg_EmailAddressNotRecognized")+"');").append("\n");
        toReturn.append("      return false;").append("\n");
        toReturn.append("    }").append("\n");
        toReturn.append("  }").append("\n");
        // Subject
        toReturn.append("  if( isEmpty(dojo.byId('subject').value) ) {").append("\n");
        toReturn.append("    alert('"+paramRequest.getLocaleString("msg_UnspecifiedSubject")+"');").append("\n");
        toReturn.append("    return false;").append("\n");
        toReturn.append("  }").append("\n");
        // Message
        toReturn.append("  if( isEmpty(dojo.byId('message').value) ) {").append("\n");
        toReturn.append("    alert('"+paramRequest.getLocaleString("msg_UnespecifiedBody")+"');").append("\n");
        toReturn.append("    return false;").append("\n");
        toReturn.append("  }").append("\n");
        toReturn.append("  return true;");
        toReturn.append("}");
        
        toReturn.append("var isEmail = function(str) {").append("\n");
        toReturn.append("  return isValidEmail(str);").append("\n");
        toReturn.append("};").append("\n");


        toReturn.append("function getTo(rel) {").append("\n");
        toReturn.append("  var to = rel;").append("\n");
        toReturn.append("  if(document.getElementById('toText').value.indexOf(to) == -1) {").append("\n");
        toReturn.append("    document.getElementById('toText').value+=to +\";\";").append("\n");
        toReturn.append("  }").append("\n");
        toReturn.append("}").append("\n");
        toReturn.append("function getCc(rel) {").append("\n");
        toReturn.append("  var cc = rel;").append("\n");
        toReturn.append("  if(document.getElementById('ccText').value.indexOf(cc) == -1) {").append("\n");
        toReturn.append("    document.getElementById('ccText').value+=cc +\";\";").append("\n");
        toReturn.append("  }").append("\n");
        toReturn.append("}").append("\n");
        toReturn.append("</script>").append("\n");
        
        toReturn.append("<div class=\"panel panel-default\">\n");
        toReturn.append("<form id=\"formEmail\" class=\"form-horizontal\" action=\"" + url + "\" method=\"post\" enctype='multipart/form-data' onsubmit=\"return validateFrm()\">\n");
        toReturn.append("  <div class=\"panel-body swb-panel-cuerpo swb-contenido-dialogo\">\n");
        toReturn.append("    <div class=\"row\">\n");       
        toReturn.append("      <div class=\"form-group\">\n");              
        toReturn.append("        <div class=\"col-xs-12\">").append("\n");
        toReturn.append("          <div class=\"input-group\">").append("\n");
        toReturn.append("            <div class=\"input-group-btn\">").append("\n");
        toReturn.append("              <button class=\"btn btn-default\" tabindex=\"-1\" type=\"button\"><strong>" + paramRequest.getLocaleString("lbl_From") + "</strong></button>").append("\n");
        toReturn.append("            </div>").append("\n");
        toReturn.append("            <input id=\"from\" name=\"from\" class=\"form-control\" type=\"text\" value=\"" + user.getEmail() + "\" readonly>\n");
        toReturn.append("          </div><!-- /input-group -->").append("\n");
        toReturn.append("        </div><!-- /.col-xs-12 -->").append("\n");      
        toReturn.append("      </div>\n");
        
        toReturn.append("      <div class=\"form-group\">\n");
        toReturn.append("        <div class=\"col-xs-12\">").append("\n");
        toReturn.append("          <div class=\"input-group\">").append("\n");
        toReturn.append("            <div class=\"input-group-btn\">").append("\n");
        toReturn.append("              <button class=\"btn btn-default\" tabindex=\"-1\" type=\"button\"><strong>" + paramRequest.getLocaleString("lbl_To") + "</strong></button>").append("\n");
        toReturn.append("              <button class=\"btn btn-default dropdown-toggle\"  data-toggle=\"dropdown\" type=\"button\">").append("\n");
        toReturn.append("                <span class=\"caret\"></span>").append("\n");
        toReturn.append("                <span class=\"sr-only\">Toggle Dropdown</span>").append("\n");
        toReturn.append("              </button>").append("\n");
        toReturn.append("              <ul class=\"dropdown-menu\" role=\"menu\">").append("\n");
        User usr;
        while (itTo.hasNext()) {
            usr = itTo.next();
            toReturn.append("            <li><a href=\"#\" rel=\""+usr.getEmail()+"\" onclick=\"javascript:getTo(rel);\">"+replaceHtml(usr.getFullName())+"</a></li>").append("\n");
        }
        toReturn.append("              </ul>").append("\n");
        toReturn.append("            </div>").append("\n");
        toReturn.append("            <input id=\"toText\" name=\"toText\" class=\"form-control\" type=\"text\">\n");
        toReturn.append("          </div><!-- /.input-group -->").append("\n");
        toReturn.append("        </div><!-- /.col-xs-12 -->").append("\n");      
        toReturn.append("      </div>\n");
        
        toReturn.append("      <div class=\"form-group\">\n");
        toReturn.append("        <div class=\"col-xs-12\">").append("\n");
        toReturn.append("          <div class=\"input-group\">").append("\n");
        toReturn.append("            <div class=\"input-group-btn\">").append("\n");
        toReturn.append("              <button class=\"btn btn-default\" tabindex=\"-1\" type=\"button\"><strong>" + paramRequest.getLocaleString("lbl_Cc") + "</strong></button>\n");        
        toReturn.append("              <button class=\"btn btn-default dropdown-toggle\"  data-toggle=\"dropdown\" type=\"button\">").append("\n");
        toReturn.append("                <span class=\"caret\"></span>").append("\n");
        toReturn.append("                <span class=\"sr-only\">Toggle Dropdown</span>").append("\n");
        toReturn.append("              </button>").append("\n");
        toReturn.append("              <ul class=\"dropdown-menu\" role=\"menu\">").append("\n");
        while (itCc.hasNext()) {
            usr = itCc.next();
            toReturn.append("            <li><a href=\"#\" rel=\""+usr.getEmail()+"\" onclick=\"javascript:getCc(rel);\">"+replaceHtml(usr.getFullName())+"</a></li>").append("\n");
        }
        toReturn.append("              </ul>").append("\n");
        toReturn.append("            </div><!-- /btn-group -->").append("\n");
        toReturn.append("            <input id=\"ccText\" name=\"ccText\" class=\"form-control\" type=\"text\"></input>\n");
        toReturn.append("          </div><!-- /input-group -->").append("\n");
        toReturn.append("        </div><!-- /.col-xs-12 -->").append("\n"); 
        toReturn.append("      </div>\n");
        
        toReturn.append("      <div class=\"form-group\">\n");
        //toReturn.append("        <label class=\"col-xs-12 col-sm-3 col-md-1 control-label\">"+paramRequest.getLocaleString("lbl_Attach")+"</label>\n");
        toReturn.append("        <div class=\"col-xs-12\">\n");
        toReturn.append("          <span class=\"glyphicon glyphicon-paperclip\"></span>");
        toReturn.append("          <input type=\"file\" name=\"uploadFile\" class=\"glyphicon\">\n");
        //toReturn.append("          <input type=\"hidden\" name=\"upload\" value=\"upload\">\n");
        toReturn.append("        </div>\n");
        toReturn.append("      </div>\n");
        
        toReturn.append("      <div class=\"form-group\">\n");
        toReturn.append("        <div class=\"col-xs-12  col-md-1 \">");
        toReturn.append("          <label class=\"control-label \">" + paramRequest.getLocaleString("lbl_Subject") + "</label>\n");
        toReturn.append("        </div>");
        toReturn.append("        <div class=\"col-xs-12 col-md-11 \">\n");      
        toReturn.append("          <input name=\"subject\" id=\"subject\" class=\"form-control\" type=\"text\">\n");
        toReturn.append("        </div>\n");
        toReturn.append("      </div>\n");
        
        toReturn.append("      <div class=\"form-group\">\n");
        toReturn.append("        <div class=\"col-xs-12  col-md-1 \">");
        toReturn.append("          <label class=\"control-label\">"+paramRequest.getLocaleString("lbl_Message")+"</label>\n");
        toReturn.append("        </div>");
        toReturn.append("        <div class=\"col-xs-12 col-md-11\">\n");
        toReturn.append("          <textarea name=\"message\" id=\"message\" class=\"form-control\" rows=\"5\"></textarea>\n");
        toReturn.append("        </div>\n");
        toReturn.append("      </div>\n");
        
        toReturn.append("      <div class=\"btn-group col-xs-12 pull-right\">\n");
        toReturn.append("        <button class=\"btn btn-default  pull-right swb-boton-enviar\" type=\"submit\">"+ paramRequest.getLocaleString("lbl_Send") + "</button>\n");
        toReturn.append("      </div>\n");
        toReturn.append("    </div>\n"); // cierra div class row
        toReturn.append("  </div>\n"); // cierra div class panel body
        toReturn.append("</form>\n");// cierra form formEmail
        toReturn.append("</div>\n"); // cierra div panel-default
        out.println(toReturn.toString());
    }

    /**
     * Reemplaza c&oacute;digo HTML por acentos, esto es para la estructura XML
     * requerida.
     *
     * @param htmlString el objeto String en que se reemplazar&aacute;n las
     * entidades de HTML por car&aacute;cteres.
     * @return el objeto String modificado
     */
    private String replaceHtml(String htmlString) {
        String sbStr = SWBUtils.TEXT.replaceAll(htmlString, "&oacute;", "ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&aacute;", "á");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&eacute;", "é");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&iacute;", "í");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&oacute;", "ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&uacute;", "ú");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Aacute;", "Á");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Eacute;", "É");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Iacute;", "Í");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Oacute;", "Ó");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Uacute;", "Ú");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&nbsp;", " ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&lt;", "<");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&gt;", ">");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&amp;", "&");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&quot;", "\"");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&iexcl;", "¡");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&iquest;", "¿");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&reg;", "®");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&copy;", "©");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&euro;", "€");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&ntilde;", "ñ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&uuml", "ü");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Ntilde;", "Ñ");
        sbStr = SWBUtils.TEXT.replaceAll(sbStr, "&Uuml;", "Ü");
        return sbStr;
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (Mode_SendMail.equals(paramRequest.getMode())) {
            doMail(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Genera el despliegue de la liga que redireccionar&aacute; al recurso que
     * envia un correo.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException SWBResourceException Excepti&oacute;n
     * utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    public void doViewStrategy(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {   
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        url.setMode(Mode_SendMail);
        url.setParameter("suri", request.getParameter("suri"));
        
        out.print("\n<div dojoType=\"dijit.Dialog\" class=\"clsDialog col-lg-12 swb-ventana-dialogo\" id=\"emailDialog\" ");
        out.print("title=\"Agregar\">\n");
        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require('dijit.Dialog');");
        out.println("  dojo.require('dojox.layout.ContentPane');");
        out.println("  function setDialogTitleEmail(title) {");
        out.println("    if (title) {");
        out.println("      dijit.byId('emailDialog').titleNode.innerHTML = title;");
        out.println("    }");
        out.println("  }");

        out.println("  function showEmailDialog(url, title) {");
        out.println("    dojo.xhrGet({");
        out.println("      url: url,");
        out.println("      load: function(response, ioArgs) {");
        out.println("        dijit.byId('emailDialogImp').attr('content', response);"); 
        out.println("        dijit.byId('emailDialog').show();");
        out.println("        setDialogTitleEmail(title);");
        out.println("        return response;");
        out.println("      },");
        out.println("      error: function(response, ioArgs) {");
        out.println("        alert('Error:' + response);");
        out.println("        return response;");
        out.println("      },");
        out.println("      handleAs: 'text'");
        out.println("    });");
        out.println("  }");
        out.println("</script>");
        out.println("<div class=\"panelDialog panelDialog-default\">");
        out.println("<div class=\"swb-panel-cuerpo\">");
        out.print("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" id=\"emailDialogImp\" ");
        out.print("style=\"width:auto; height:auto;\" executeScripts=\"true\">\n");
        out.print("    Cargando...\n");
        out.print("  </div>\n");
        out.print("  </div>\n");
        out.print("  </div>\n");
        out.print("</div>\n");
        out.print("<button type=\"button\" class=\"btn btn-default\" onclick=\"showEmailDialog('");
        out.print(url);
        out.print("', '");
        out.print(paramRequest.getLocaleString("lbl_addTitle"));
        out.println("');\"><span class=\"glyphicon glyphicon-envelope\"></span></button>");
        
        if(request.getParameter("rse")!=null) {
            out.println("<script type=\"text/javascript\">");
            out.print("  $('#menu').popover({");
            out.print("container: '#menu'");
            out.print(", title:'"+paramRequest.getLocaleString("lbl_ShippingStatus")+"'");
            out.print(", content:'"+paramRequest.getLocaleString(request.getParameter("rse"))+"'");
            out.print(", placement:'auto top'");
            out.print(", delay:{'show':1500,'hide':50}");
            out.println("});");
            out.println("  $('#menu').popover('show');");
            out.println("</script>");
        }
        out.flush();
    }

    /**
     * Recibe los datos del formulario e implementa la funcionalidad de enviar
     * correo.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
//        String action = response.getAction();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        final User user = response.getUser();
        
//        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
            response.setMode(SWBResourceURL.Mode_VIEW);
            response.setCallMethod(SWBResourceURL.Call_STRATEGY);
            response.setRenderParameter("suri", request.getParameter("suri"));
            String msg;
            
            final String path = SWBPortal.getWorkPath() + "/models/" + wsite.getId();
            MultipartRequest mrequest = new MultipartRequest(request, path);
            String subject = (String) mrequest.getParameter("subject") == null ? "" : (String) mrequest.getParameter("subject");
            String message = (String) mrequest.getParameter("message") == null ? "" : (String) mrequest.getParameter("message");
            String to = (String) mrequest.getParameter("toText") == null ? "" : (String) mrequest.getParameter("toText");
            String cc = (String) mrequest.getParameter("ccText") == null ? "" : (String) mrequest.getParameter("ccText");
            File attachment = mrequest.getFile("uploadFile");

            if(subject==null) {
            }
            if(message==null) {
            }
            if(to==null) {
            }
            
            SWBMail mail = new SWBMail();
            mail.setContentType("HTML");
            mail.setFromEmail(user.getEmail());
            mail.setFromName(user.getFullName());
            mail.setSubject(subject);
            mail.setData(message);
            if (attachment != null) {
                 EmailAttachment emailAttachment = new EmailAttachment();
                 emailAttachment.setPath(attachment.getPath());
                 emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
                 emailAttachment.setName(attachment.getName());
                 mail.addAttachment(emailAttachment);
             }
            
            InternetAddress ia;
            ArrayList<InternetAddress> addresses = null;
            List<String> emailsTo = validateEMailAccounts(to);
            if(emailsTo == null) {
                return;
            }else {
                addresses = new ArrayList();
                for(String em:emailsTo) {
                    ia = new InternetAddress();
                    ia.setAddress(em);
                    addresses.add(ia);
                }
            }
            mail.setAddress(addresses);
            List<String> emailsCc = validateEMailAccounts(cc);
            if(emailsCc != null) {
                addresses = new ArrayList();
                for(String em:emailsCc) {
                    ia = new InternetAddress();
                    ia.setAddress(em);
                    addresses.add(ia);
                }
                mail.setCcEmail(addresses);
            }
            try {
                SWBUtils.EMAIL.sendMail(mail);
                saveLogMail(user, subject, message, emailsTo, emailsCc);
                msg = "msg_SentSuccessfully";
            } catch (SocketException se) {
                log.error("Error en el envio :" + se);
                msg = "msg_DeliveryFailure";
            }
//        }
        response.sendRedirect(response.getWebPage().getUrl("es")+"?suri="+URLEncoder.encode(request.getParameter("suri"), "utf-8")+"&rse="+msg);
    }

    private List<String> validateEMailAccounts(String accounts) {
        if(accounts.isEmpty()) {
            return null;
        }
        List<String> list = null;
        String[] mails = accounts.split("[;|,]",0);
        if(mails.length > 0) {            
            list = new ArrayList();
            for (String account : mails) {
                if (SWBUtils.EMAIL.isValidEmailAddress(account)) {
                    list.add(account);
                }
            }
        }
        return list;
    }
    
    private void saveLogMail(User user, String subject, String message, List<String> mailsTo, List<String> mailsCc) {
        final UserRepository ur = getResourceBase().getWebSite().getUserRepository();
        final List<User> listUserTo = new ArrayList();
        final List<User> listUserCc = new ArrayList();
        String anothers = "";
        // cuentas internas
        List<String> intAccTo = SWBUtils.Collections.filterIterator(mailsTo.iterator(), new GenericFilterRule<String>() {
            @Override
            public boolean filter(String mail) {
                return ur.getUserByEmail(mail) == null;
            }
        });       
            Iterator<String> iter = intAccTo.iterator();
            while (iter.hasNext()) {
                String mail = iter.next();
                User uTo = ur.getUserByEmail(mail);
                listUserTo.add(uTo);
            }

        // cuentas externas
        List<String> extAccTo = SWBUtils.Collections.filterIterator(mailsTo.iterator(), new GenericFilterRule<String>() {
            @Override
            public boolean filter(String mail) {
                return ur.getUserByEmail(mail) != null;
            }
        });
        
        List anothersAcc = new ArrayList(extAccTo);
        
        if(mailsCc != null){
        List<String> intAccCc = SWBUtils.Collections.filterIterator(mailsCc.iterator(), new GenericFilterRule<String>() {
            @Override
            public boolean filter(String mail) {
                return ur.getUserByEmail(mail) == null;
            }
        });     
        if (!intAccCc.isEmpty()) {
            Iterator<String> iterCc = intAccCc.iterator();
            while (iterCc.hasNext()) {
                String mailCc = iterCc.next();
                User uCc = ur.getUserByEmail(mailCc);
                listUserCc.add(uCc);
            }
        }
       
        // cuentas externas
        List<String> extAccCc = SWBUtils.Collections.filterIterator(mailsCc.iterator(), new GenericFilterRule<String>() {
            @Override
            public boolean filter(String mail) {
                return ur.getUserByEmail(mail) != null;
            }
        });

       anothersAcc.addAll(extAccCc);
      
}
        anothers = java.util.Arrays.toString(anothersAcc.toArray());
        anothers=anothers.replace("[","");
        anothers=anothers.replace("]", "");
        //Date date = new Date();
        EmailLog emLog = EmailLog.ClassMgr.createEmailLog(getResourceBase().getWebSite());
        emLog.setFrom(user);
        emLog.setSubject(subject);
        emLog.setMessage(message);
        //emLog.setDate(date);
        
        Iterator<User> iterTo = listUserTo.iterator();
        Iterator<User> iterCc = listUserCc.iterator();

        while (iterTo.hasNext()) {
            User userTo1 = iterTo.next();
            emLog.addTo(userTo1);
        }
        while (iterCc.hasNext()) {
            User userCc1 = iterCc.next();
            emLog.addCc(userCc1);
        }
        if (!anothers.equals("")) {
            emLog.setOtherAccounts(anothers);
        }
    }
}
