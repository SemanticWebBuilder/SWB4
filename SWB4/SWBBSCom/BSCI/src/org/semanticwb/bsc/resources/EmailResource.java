
package org.semanticwb.bsc.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import com.oreilly.servlet.MultipartRequest;
import java.util.Date;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.EmailAttachment;
import org.semanticwb.SWBPortal;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.utils.EmailLog;
import org.semanticwb.model.UserRepository;

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
        StringBuilder toReturn = new StringBuilder();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();

        Iterator<User> itTo = wsite.getUserRepository().listUsers();
        Iterator<User> itCc = wsite.getUserRepository().listUsers();
        SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);

        toReturn.append("<div class=\"panel panel-default\">");
        toReturn.append("<form id=\"formEmail\" action=\"" + url + "\" method=\"post\" enctype='multipart/form-data' onsubmit=\"return getValidate()\">\n");
        toReturn.append("   <div class=\"panel-heading swb-panel-cabeza\">" + paramRequest.getLocaleString("lbl_addTitle") + "");
        toReturn.append("   <a href=\"#\" class=\"btn fa fa-times fa-lg pull-right swb-icon-cerrar\" data-dismiss=\"modal\"></a>");
        toReturn.append("   </div>");
        toReturn.append("     <div class=\"panel-body swb-panel-cuerpo swb-contenido-dialogo\">");
        toReturn.append("       <div class=\"row\" style=\"margin: 0px;\">");
        toReturn.append("         <div class=\"col-lg-5 col-md-5 col-sm-5 col-xs-12  swb-fondo-izq\">");
        toReturn.append("           <form class=\"form-horizontal\" role=\"form\">");// abre form izquierdo
        toReturn.append("           <div class=\"form-group\">");
        toReturn.append("           <label class=\"col-lg-1 control-label\">" + paramRequest.getLocaleString("lbl_From") + "</label>");
        toReturn.append("             <div class=\"col-lg-11\">");
        toReturn.append("             <input id=\"from\" name=\"from\" class=\"form-control\" type=\"text\" value=\"" + user.getEmail() + "\" readonly></input>");
        toReturn.append("             </div>");
        toReturn.append("           </div>");
        toReturn.append("           <div class=\"form-group\">");
        toReturn.append("            <label class=\"col-lg-1 control-label\">" + paramRequest.getLocaleString("lbl_To") + "</label>");
        toReturn.append("             <div class=\"col-lg-11\">");
        toReturn.append("              <select class=\"form-control\" id=\"To\" name=\"To\" onChange=\"javascript:getTo();\">");
        toReturn.append("              <option value=\"\">Selecciona...</option>");
                        while (itTo.hasNext()) {
                        User usr = itTo.next();
                        toReturn.append("<option value=\"" + usr.getEmail() + "\">" + replaceHtml(usr.getFullName()) + "</option>");
                        }
        toReturn.append("              </select>");
        toReturn.append("            </div>");
        toReturn.append("            <div class=\"col-lg-11 col-md-12 col-sm-12 col-xs-12 pull-right\" id=\"divTo\" style=\"display:none;\">");
        toReturn.append("               <input id=\"toText\" name=\"toText\" class=\"form-control\" type=\"text\">");
        toReturn.append("            </div>");
        toReturn.append("           </div>");
        
        toReturn.append("           <div class=\"form-group\">");
        toReturn.append("            <label class=\"col-lg-1 control-label\">" + paramRequest.getLocaleString("lbl_Cc") + "</label>");
        toReturn.append("             <div class=\"col-lg-11\">");
        toReturn.append("              <select class=\"form-control\" id=\"Cc\" name=\"Cc\" onChange=\"javascript:getCc();\">");
        toReturn.append("              <option value=\"\">Selecciona...</option>");
                        while (itCc.hasNext()) {
                        User usr = itCc.next();
                        toReturn.append("<option value=\"" + usr.getEmail() + "\">" + usr.getFullName() + "</option>");
                        }
        toReturn.append("              </select>");
        toReturn.append("             </div>");
        toReturn.append("           <div class=\"col-lg-11 col-md-12 col-sm-12 col-xs-12 pull-right\" id=\"divCc\" style=\"display:none;\">");
        toReturn.append("               <input id=\"ccText\" name=\"ccText\" class=\"form-control\" type=\"text\"></input>");
        toReturn.append("            </div>");
        toReturn.append("           </div>");
        toReturn.append("           </form>"); // cierra form izquierdo
        toReturn.append("         </div>"); // cierra div swb-fondo-izq
        
        toReturn.append("         <div class=\"col-lg-7 col-md-7 col-sm-7 col-xs-12 swb-fondo-der\">"); // abre div swb-fondo-der 
        toReturn.append("          <form class=\"form-horizontal\" role=\"form\">");// abre form derecho
        
        toReturn.append("           <div class=\"form-group\">");
        toReturn.append("            <label class=\"col-lg-1 control-label\">" + paramRequest.getLocaleString("lbl_Subject") + "</label>");
        toReturn.append("             <div class=\"col-lg-11\">");      
        toReturn.append("               <input name=\"subject\" class=\"form-control\" type=\"text\"></input>");
        toReturn.append("             </div>");
        toReturn.append("           </div>");
        
        toReturn.append("           <div class=\"form-group\">");
        toReturn.append("            <label class=\"col-lg-1 control-label\">"+paramRequest.getLocaleString("lbl_Attach")+"</label>");
        toReturn.append("             <div class=\"col-lg-11\">");
        toReturn.append("             </div>");
        toReturn.append("             <div class=\"col-lg-11  pull-right\">");
        toReturn.append("               <input type=\"file\" name=\"uploadFile\" />");
        toReturn.append("               <input type=\"hidden\" name=\"upload\" value=\"upload\" />");
        toReturn.append("             </div>");
        toReturn.append("           </div>");
        
        toReturn.append("           <div class=\"form-group\">");
        toReturn.append("            <label class=\"col-lg-1 control-label\">"+paramRequest.getLocaleString("lbl_Message")+"</label>");
        toReturn.append("             <div class=\"col-lg-11\">");
        toReturn.append("               <textarea class=\"form-control\" name=\"message\" rows=\"3\"></textarea>");
        toReturn.append("             </div>");
        toReturn.append("           </div>");
        
        toReturn.append("           <div class=\"btn-group col-lg-12 col-md-12 pull-right\">");
        toReturn.append("           <a href=\"#\" data-dismiss=\"modal\">");
        toReturn.append("               <button type=\"button\" class=\"btn btn-default pull-right swb-boton-cancelar\" >Cancelar</button>");
        toReturn.append("           <a>");    
        toReturn.append("           <button class=\"btn btn-default  pull-right swb-boton-enviar\" type=\"submit\">"+ paramRequest.getLocaleString("lbl_Send") + "</button>");
        toReturn.append("           </div>");
        toReturn.append("          </form>");// cierra form derecho
        toReturn.append("         </div>"); // cierra div swb-fondo-der
        toReturn.append("       </div>"); // cierra div class row
        toReturn.append("     </div>"); // cierra div class panel body
        toReturn.append("   </form>");// cierra form formEmail
        toReturn.append("</div>"); // cierra div panel-default

        toReturn.append("\n <script type=\"text/javascript\">");
        toReturn.append("\n function getValidate(){");
        toReturn.append("\n var form = document.getElementById('formEmail');");
        toReturn.append(" if(document.getElementById('toText').value == ''){");
        toReturn.append("alert('Debes seleccionar un destinatario.'); return false;};");
        toReturn.append("return true;");
        toReturn.append("}");
        toReturn.append("\n  function getTo() {");
        toReturn.append("\n var to = document.getElementById('To').value;");
        toReturn.append("if (document.getElementById('toText').value.indexOf(to) == -1){ ");
        toReturn.append("\n document.getElementById('toText').value+=to +\";\";");
        toReturn.append("\n document.getElementById('divTo').style.display =\"\";");
        toReturn.append("}");
        toReturn.append("\n document.getElementById('To').value = \"\";");
        toReturn.append("\n  };");
        toReturn.append("\n  function getCc() {");
        toReturn.append("\n var cc = document.getElementById('Cc').value;");
        toReturn.append("if (document.getElementById('ccText').value.indexOf(cc) == -1){ ");
        toReturn.append("\n document.getElementById('ccText').value+=cc +\";\";");
        toReturn.append("\n document.getElementById('divCc').style.display =\"\";");
        toReturn.append("}");
        toReturn.append("\n document.getElementById('Cc').value = \"\";");
        toReturn.append("\n  };");
        toReturn.append("\n </script>");

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
    public void doViewStrategy(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        url.setMode(Mode_SendMail);

        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require('dijit.Dialog');");
        out.println("  dojo.require('dojox.layout.ContentPane');");
        out.println("function setDialogTitleEmail(title){");
        out.println("if (title)");
        out.println("dijit.byId('emailDialog').titleNode.innerHTML = title;");
        out.println("}");
        
        out.println("function showEmailDialog(url, title){");
        out.println("dojo.xhrGet({");
        out.println("url: url,");
        out.println("load: function(response, ioArgs) {");
        out.println("dijit.byId('emailDialogImp').attr('content', response);"); 
        out.println("dijit.byId('emailDialog').show();");
        out.println("    setDialogTitleEmail(title);");
        out.println("    return response;");
        out.println("},");
        out.println("error: function(response, ioArgs) {");
        out.println("    alert('Error:' + response);");
        out.println("    return response;");
        out.println("},");
        out.println("handleAs: 'text'");
        out.println("});");
        out.println("}");
        out.println("</script>");

//        out.print("<a href=\"#\" class=\"swbstgy-toolbar-mail\" onclick=\"showEmailDialog('");
//        out.print(url);
//        out.print("', '");
//        out.print(paramRequest.getLocaleString("lbl_addTitle"));
//        out.print("');\">");
//        out.print(paramRequest.getLocaleString("lbl_addTitle"));
//        out.println("</a>");
        out.print("<button type=\"button\" class=\"btn btn-default\" onclick=\"showEmailDialog('");
        out.print(url);
        out.print("', '");
        out.print(paramRequest.getLocaleString("lbl_addTitle"));
        out.print("');\"><span class=\"glyphicon glyphicon-envelope\"></span></button>");
//                  "location.href='" + url 
//                + "'\"><span class=\"glyphicon glyphicon-th-large\"></span></button>");

        out.print("\n<div dojoType=\"dijit.Dialog\" class=\"soria\" id=\"emailDialog\" ");
        out.print("title=\"Agregar\" style=\"width:auto; height:auto;\">\n");
        out.print("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" id=\"emailDialogImp\" ");
        out.print("style=\"padding:10px; width:auto; height:auto;\" executeScripts=\"true\">\n");
        out.print("    Cargando...\n");
        out.print("  </div>\n");
        out.print("</div>\n");
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
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        final User user = response.getUser();
        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
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
                mail.setCcEmail(emailsCc);
            }
            
            try {
                SWBUtils.EMAIL.sendMail(mail);
                //Crea el email Log
                saveLogMail(user, subject, message, emailsTo, emailsCc);
            } catch (SocketException se) {
                log.error("Error en el envio :" + se);
            }
        }
        //response.sendRedirect(response.getWebPage().getUrl());
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
