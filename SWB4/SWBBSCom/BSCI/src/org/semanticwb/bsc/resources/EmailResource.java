/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import com.oreilly.servlet.MultipartRequest;
import java.util.Date;
import org.apache.commons.mail.EmailAttachment;
import org.semanticwb.SWBPortal;
import org.semanticwb.bsc.utils.EmailLog;
import org.semanticwb.model.UserRepository;

/**
 *
 * @author ana.garcias
 */
public class EmailResource extends GenericResource {

    private static final org.semanticwb.Logger log = SWBUtils.getLogger(EmailResource.class);
    private static final String Mode_SendMail = "mail";
    private static List listEmails = new ArrayList();
    private static List listCcEmails = new ArrayList();
    private static String otherMails = "";
    private static List usersTo = new ArrayList();
    private static List usersCc = new ArrayList();

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

        toReturn.append("<div class=\"swbform\" id=\"EmailSend\">");
        toReturn.append("<form id=\"formEmail\" action=\"" + url + "\" method=\"post\" enctype='multipart/form-data' onsubmit=\"return getValidate()\">\n");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_From") + "<input id=\"from\" name=\"from\" size=\"30\" type=\"text\" value=\"" + user.getEmail() + "\" readonly></input></p>");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_To") + "<select id=\"To\" name=\"To\" onChange=\"javascript:getTo();\">");
        toReturn.append("<option value=\"\">Selecciona...</option>");
        while (itTo.hasNext()) {
            User usr = itTo.next();
            toReturn.append("<option value=\"" + usr.getEmail() + "\">" + replaceHtml(usr.getFullName()) + "</option>");
        }
        toReturn.append("</select></p>");
        toReturn.append("<div id=\"divTo\" style=\"display:none;\"><input id=\"toText\" name=\"toText\" size=\"100\" type=\"text\"></div>");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_Cc") + "<select id=\"Cc\" name=\"Cc\" onChange=\"javascript:getCc();\">");
        toReturn.append("<option value=\"\">Selecciona...</option>");
        while (itCc.hasNext()) {
            User usr = itCc.next();
            toReturn.append("<option value=\"" + usr.getEmail() + "\">" + usr.getFullName() + "</option>");
        }
        toReturn.append("</select></p>");
        toReturn.append("<div id=\"divCc\" style=\"display:none;\"><input id=\"ccText\" name=\"ccText\" size=\"100\" type=\"text\"></input></div>");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_Subject") + "<input name=\"subject\" size=\"50\" type=\"text\"></input></p>");
        toReturn.append("<input type=\"file\" name=\"uploadFile\" />");
        toReturn.append("<input type=\"hidden\" name=\"upload\" value=\"upload\" />");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_Message") + "<textarea name=\"message\" rows=\"10\" cols=\"50\"></textarea></p>");
        toReturn.append("<p><button type=\"submit\">"
                + paramRequest.getLocaleString("lbl_Send") + "</button>");
        toReturn.append("</form>");
        toReturn.append("</div>");

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
        out.println("</script>");

        out.print("<a href=\"#\" class=\"swbstgy-toolbar-mail\" onclick=\"showDialog('");
        out.print(url);
        out.print("', '");
        out.print(paramRequest.getLocaleString("lbl_addTitle"));
        out.print("');\">");
        out.print(paramRequest.getLocaleString("lbl_addTitle"));
        out.println("</a>");

        out.print("\n<div dojoType=\"dijit.Dialog\" class=\"soria\" id=\"swbDialog\" ");
        out.print("title=\"Agregar\" style=\"width:auto; height:auto;\">\n");
        out.print("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" id=\"swbDialogImp\" ");
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
        User user = SWBContext.getSessionUser(wsite.getUserRepository().getId());
        usersTo.clear();
        usersCc.clear();

        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
            final String path = SWBPortal.getWorkPath() + "/models/" + wsite.getId();
            MultipartRequest mrequest = new MultipartRequest(request, path);
            String subject = (String) mrequest.getParameter("subject") == null ? "" : (String) mrequest.getParameter("subject");
            String message = (String) mrequest.getParameter("message") == null ? "" : (String) mrequest.getParameter("message");
            String to = (String) mrequest.getParameter("toText") == null ? "" : (String) mrequest.getParameter("toText");
            String cc = (String) mrequest.getParameter("ccText") == null ? "" : (String) mrequest.getParameter("ccText");
            listEmails = validateEMailAccounts(to);    
            listCcEmails = validateEMailAccounts(cc);
            //Valida usuarios
            if(!listEmails.isEmpty()){
            validateUsers(listEmails, "To");
            }
            if(!listCcEmails.isEmpty()){
            validateUsers(listCcEmails, "Cc");
            }
            System.out.println("\nlista de correos Para: " + listEmails.toString());
            System.out.println("\n usuarios Para bitacora Para: " + usersTo.toString());
            System.out.println("\n lista de correos CC: " + listCcEmails.toString());
            System.out.println("\n lista de usuarios con CC para bitacora: " + usersCc.toString());
            System.out.println("\n lista de otros correos: " + otherMails);
            Date date = new Date();
   
            //Crea el email Log
            System.out.println("wsite: " + wsite);
           EmailLog emLog = EmailLog.ClassMgr.createEmailLog(wsite);
                emLog.setFrom(user);
                emLog.setSubject(subject);
                emLog.setMessage(message);
                emLog.setDate(date);
                Iterator<User> iter = usersTo.iterator();
                Iterator<User> iterCc = usersCc.iterator();
              
                while (iter.hasNext()) {
                    User userTo1 = iter.next();
                    emLog.addTo(userTo1);
                }
                while (iterCc.hasNext()) {
                    User userCc1 = iterCc.next();
                    emLog.addCc(userCc1);
                }
               if(!otherMails.equals("")) {
                    emLog.setOtherAccounts(otherMails);
                }            
         
            SWBMail mail = new SWBMail();
            EmailAttachment att = new EmailAttachment();
            if (mrequest.getFile("uploadFile") != null) {
                File file = mrequest.getFile("uploadFile");
                att.setPath(file.getPath());
                mail.addAttachment(att);
            }
            mail.setFromEmail(user.getEmail());
            mail.setFromName(user.getFullName());
            mail.setSubject(subject);
            mail.setToEmail(listEmails);
            mail.setCcEmail(listCcEmails);
            mail.setContentType("HTML");
            mail.setData(message);

            try {
                SWBUtils.EMAIL.sendMail(mail);
                //Crea el emailLog
                
            } catch (SocketException se) {
                EmailResource.log.error("Error en el envio :" + se);
            }
        } else {
            super.processAction(request, response); //To change body of generated methods, choose Tools | Templates.
        }
        response.sendRedirect(response.getWebPage().getUrl());
    }

    private List<String> validateEMailAccounts(String accounts) {
        List<String> list = new ArrayList();
        String[] mails = accounts.split(";");
        for (String account : mails) {
            if (SWBUtils.EMAIL.isValidEmailAddress(account)) {
                 list.add(account);
            }
        }
        return list;
    }
    
    private void validateUsers (List listMails, String accountType){
         Iterator <String> itListTo = listMails.iterator();
            while(itListTo.hasNext()){
                String mailT = itListTo.next();
                UserRepository ur = getResourceBase().getWebSite().getUserRepository();
                 if (ur.getUserByEmail(mailT) != null)  {
                     User us = getResourceBase().getWebSite().getUserRepository().getUserByEmail(mailT);
                     if(accountType.equals("To")){
                          usersTo.add(us);
                     }
                     else if (accountType.equals("Cc")){
                         usersCc.add(us);
                     }  
                 }
                 else{
                     otherMails += mailT + ";";
                 }
            }
    }
    
}
