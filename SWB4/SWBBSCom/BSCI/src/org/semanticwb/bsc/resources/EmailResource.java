/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.bsc.utils.EmailLog;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.GenericSemResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.MultipartInputStream;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;
import sun.misc.IOUtils;
import com.oreilly.servlet.MultipartRequest;
import javax.mail.internet.InternetAddress;
import org.apache.commons.fileupload.FileItem;
import org.semanticwb.SWBPortal;
import org.semanticwb.portal.api.SWBResourceURLImp;

/**
 *
 * @author ana.garcias
 */
public class EmailResource extends GenericResource {

    private static org.semanticwb.Logger log = SWBUtils.getLogger(GenericSemResource.class);
    private final String Mode_SendMail = "mail";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        doViewStrategy(request, response, paramRequest);
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

        final User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        StringBuilder toReturn = new StringBuilder();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();

        if (!user.isSigned()) {
            EmailResource.log.error("El usuario no esta logueado.");
            return;
        }

        Iterator<User> itTo = wsite.getUserRepository().listUsers();
        Iterator<User> itCc = wsite.getUserRepository().listUsers();
        SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);

        toReturn.append("<div class=\"swbform\" id=\"EmailSend\">");
        toReturn.append("<form id=\"formEmail\" name=\"formEmail\" action=\"" + url + "\" method=\"post\" enctype='multipart/form-data' onsubmit=\"return getValidate()\">\n");
        toReturn.append("<input type=\"hidden\" name=\"nameFrom\" value=\"" + user.getFullName() + "\">");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_From") + "<input id=\"from\" name=\"from\" size=\"30\" type=\"text\" value=\"" + user.getEmail() + "\" readonly></input></p>");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_To") + "<select id=\"To\" name=\"To\" onChange=\"javascript:getTo();\">");
        toReturn.append("<option value=\"\">Selecciona...</option>");
        while (itTo.hasNext()) {
            User usr = itTo.next();
            toReturn.append("<option value=\"" + usr.getEmail() + "-" + usr.getId() + "\">" + replaceHtml(usr.getFullName()) + "</option>");
        }
        toReturn.append("</select></p>");
        toReturn.append("<div id=\"divTo\" style=\"display:none;\"><input id=\"toText\" name=\"toText\" size=\"100\" type=\"text\"></div>");
        toReturn.append("<input type=\"hidden\" id=\"toId\" name=\"toId\">");
        toReturn.append("<p>" + paramRequest.getLocaleString("lbl_Cc") + "<select id=\"Cc\" name=\"Cc\" onChange=\"javascript:getCc();\">");
        toReturn.append("<option value=\"\">Selecciona...</option>");
        while (itCc.hasNext()) {
            User usr = itCc.next();
            toReturn.append("<option value=\"" + usr.getEmail() + "-" + usr.getId() + "\">" + usr.getFullName() + "</option>");
        }
        toReturn.append("</select></p>");
        toReturn.append("<div id=\"divCc\" style=\"display:none;\"><input id=\"ccText\" name=\"ccText\" size=\"100\" type=\"text\"></input></div>");
        toReturn.append("<input type=\"hidden\" id=\"ccId\" name=\"ccId\">");
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
        toReturn.append("\n var idUserTo = to.split('-');");
        toReturn.append("\n var mailTo = idUserTo[0];");
        toReturn.append("\n var id = idUserTo[1];");
        toReturn.append("if (document.getElementById('toText').value.indexOf(mailTo) == -1){ ");
        toReturn.append("\n document.getElementById('toText').value+=mailTo +\";\";");
        toReturn.append("\n document.getElementById('divTo').style.display =\"\";");
        toReturn.append("\n document.getElementById('toId').value+=id +\";\";");
        toReturn.append("}");
        toReturn.append("\n document.getElementById('To').value = \"\";");
        toReturn.append("\n  };");
        toReturn.append("\n  function getCc() {");
        toReturn.append("\n var cc = document.getElementById('Cc').value;");
        toReturn.append("\n var idUserCc = cc.split('-');");
        toReturn.append("\n var mailCc = idUserCc[0];");
        toReturn.append("\n var idCc = idUserCc[1];");
        toReturn.append("if (document.getElementById('ccText').value.indexOf(mailCc) == -1){ ");
        toReturn.append("\n document.getElementById('ccText').value+=mailCc +\";\";");
        toReturn.append("\n document.getElementById('divCc').style.display =\"\";");
        toReturn.append("\n document.getElementById('ccId').value+=idCc +\";\";");
        toReturn.append("}");
        toReturn.append("\n document.getElementById('Cc').value = \"\";");
        toReturn.append("\n  };");
        toReturn.append("\n </script>");

        out.println(toReturn.toString());
    }
    
      /**
     * Reemplaza c&oacute;digo HTML por acentos, esto es para la estructura XML
     * requerida.
     * @param htmlString el objeto String en que se reemplazar&aacute;n las entidades de HTML por car&aacute;cteres.
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
        
        out.print("<a href=\"#\" class=\"swb-toolbar-stgy\" onclick=\"showDialog('");
        out.print(url);
        out.print("', '");
        out.print(paramRequest.getLocaleString("lbl_addTitle"));
        out.print("');\">");
        out.print(paramRequest.getLocaleString("lbl_addTitle"));
        out.print("</a>");

        out.print("\n<div dojoType=\"dijit.Dialog\" class=\"soria\" id=\"swbDialog\" ");
        out.print("title=\"Agregar\" style=\"width:auto; height:auto;\">\n");
        out.print("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" id=\"swbDialogImp\" ");
        out.print("style=\"padding:10px; width:auto; height:auto;\" executeScripts=\"true\">\n");
        out.print("    Cargando...\n");
        out.print("  </div>\n");
        out.print("</div>\n");

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
        System.out.println("llega al processAction");
        String action = response.getAction();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        User user = SWBContext.getSessionUser(wsite.getUserRepository().getId());
        String uriUser = user.getURI();
        SemanticObject sObj = SemanticObject.getSemanticObject(uriUser);
        SWBModel model = (SWBModel) sObj.getModel().getModelObject().createGenericInstance();
        SWBModel modelWS = model.getParentWebSite();

        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
            String path = SWBPortal.getWorkPath() + "/models/" + wsite.getId();
            System.out.println("path: " + path);
            MultipartRequest mrequest = new MultipartRequest(request, path);
            String pathFile = "";
           if(mrequest.getParameter("uploadFile")!= null){
               System.out.println("trae archivo adjunto!");
                File f = mrequest.getFile("uploadFile");
                pathFile = f.getPath();
           }

            /*String[] arrStr = rutaReal.split(new String("\\\\"));
             String nombreA = arrStr[arrStr.length - 1];
             File des = new File(path);
             File archivo = new File(des, nombreA);*/


            String emailFrom = (String) mrequest.getParameter("from") == null ? "" : (String) mrequest.getParameter("from");
            String nameFrom = (String) mrequest.getParameter("nameFrom") == null ? "" : (String) mrequest.getParameter("nameFrom");
            String subject = (String) mrequest.getParameter("subject") == null ? "" : (String) mrequest.getParameter("subject");
            String message = (String) mrequest.getParameter("message") == null ? "" : (String) mrequest.getParameter("message");
            String to = (String) mrequest.getParameter("toText") == null ? "" : (String) mrequest.getParameter("toText");
            String idUserTo = (String) mrequest.getParameter("toId") == null ? "" : (String) mrequest.getParameter("toId");
            String cc = (String) mrequest.getParameter("ccText") == null ? "" : (String) mrequest.getParameter("ccText");
            String idUserCc = (String) mrequest.getParameter("ccId") == null ? "" : (String) mrequest.getParameter("ccId");
            //Collection listEmails = new ArrayList<InternetAddress>();
           // Collection listCcEmails = new ArrayList<InternetAddress>();
            Collection listEmails = new ArrayList<String>();
            Collection listCcEmails = new ArrayList<String>();
            List usersTo = new ArrayList<User>();
            List usersCc = new ArrayList<User>();

            /*System.out.println("\n emailFrom :" + emailFrom);
            System.out.println("\n subject: " + subject);
            System.out.println("\n namefrom: " + nameFrom);
            System.out.println("\n message: " + message);
            System.out.println("\n isUserTo: " + idUserTo);
            System.out.println("\n isUserCc: " + idUserCc);*/

            if (to != "") {
                String emailsArray[] = to.split(";");
                for (String emails : emailsArray) {
                    listEmails.add(emails);
                }
                String idToArray[] = idUserTo.split(";");
                for (String idTo : idToArray) {
                    User userTo = User.ClassMgr.getUser(idTo, model);
                    usersTo.add(userTo);
                }
            }
            //Valida que traiga correos Cc
            if (cc != "") {
                String ccArray[] = cc.split(";");
                for (String ccEmails : ccArray) {
                    listCcEmails.add(ccEmails);
                }
                String ccToArray[] = idUserCc.split(";");
                for (String ccTo : ccToArray) {
                    User userCc = User.ClassMgr.getUser(ccTo, model);
                    usersCc.add(userCc);
                }
            }

            EmailAttachment att = new EmailAttachment();
            SWBMail mail = new SWBMail();
            att.setPath(pathFile);
            mail.addAttachment(att);
            mail.setFromEmail(emailFrom);
            mail.setFromName(nameFrom);
            mail.setSubject(subject);
            mail.setToEmail(listEmails);
            mail.setCcEmail(listCcEmails);
            mail.setContentType("HTML");
            mail.setData(message);

            try {
                SWBUtils.EMAIL.sendMail(mail);
                //Crea el emailLog
                EmailLog log = EmailLog.ClassMgr.createEmailLog(user.getId(), modelWS);
                log.setFrom(user);
                log.setSubject(subject);
                log.setMessage(message);
                Iterator<User> iter = usersTo.iterator();
                Iterator<User> iterCc = usersCc.iterator();
                while (iter.hasNext()) {
                    User userTo1 = iter.next();
                    log.addTo(userTo1);
                }
                while (iterCc.hasNext()) {
                    User userCc1 = iterCc.next();
                    log.addCc(userCc1);
                }
            } catch (SocketException e) {
                EmailResource.log.error("Error en el envio :" + e);
            }
        } else {
            super.processAction(request, response); //To change body of generated methods, choose Tools | Templates.
        }
        response.sendRedirect(response.getWebPage().getUrl());
    }
}
