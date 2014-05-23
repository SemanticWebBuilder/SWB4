/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.base.FileUploadBase;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.GenericSemResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;

/**
 *
 * @author ana.garcias
 */
public class EmailResource extends GenericResource {

    private static org.semanticwb.Logger log = SWBUtils.getLogger(GenericSemResource.class);

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
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        System.out.println("entra al doView!!");
        final User user = paramRequest.getUser();
        final String lang = user.getLanguage();
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
        toReturn.append("<div id=\"EmailSend\">");
        toReturn.append("<form id=\"formEmail\" class=\"swbform\" action=\"" + url + "\" method=\"post\">\n");
        toReturn.append("<input type=\"hidden\" name=\"nameFrom\" value=\"" + user.getFullName() + "\">");
        toReturn.append("<p>"+ paramRequest.getLocaleString("lbl_From") +"<input name=\"emailFrom\" disabled=\"true\" size=\"30\" type=\"text\" value=\"" + user.getEmail() + "\"></input></p>");
        toReturn.append("<p>"+ paramRequest.getLocaleString("lbl_To") +"<select id=\"To\" name=\"To\" onChange=\"javascript:getTo();\">");
        toReturn.append("<option value=\"\">Selecciona...</option>");
        while (itTo.hasNext()) {
            User usr = itTo.next();
            toReturn.append("<option value=" + usr.getEmail() + ">" + usr.getFullName() + "</option>");
        }
        toReturn.append("</select></p>");
        toReturn.append("<div><input id=\"toText\" name=\"toText\" size=\"100\" type=\"text\"></input></div>");
        toReturn.append("<p>"+ paramRequest.getLocaleString("lbl_Cc") +"<select id=\"Cc\" name=\"Cc\" onChange=\"javascript:getCc();\">");
        toReturn.append("<option value=\"\">Selecciona...</option>");
        while (itCc.hasNext()) {
            User usr = itCc.next();
            toReturn.append("<option value=" + usr.getEmail() + ">" + usr.getFullName() + "</option>");
        }
        toReturn.append("</select></p>");
        toReturn.append("<div><input id=\"ccText\" name=\"ccText\" size=\"100\" type=\"text\"></input></div>");
        toReturn.append("<p>"+ paramRequest.getLocaleString("lbl_Subject") +"<input name=\"subject\" size=\"50\" type=\"text\"></input></p>");
        //Adjuntar archivo
        //toReturn.append("<input type=\"file\" name=\"fichero\"><input type=\"submit\">");

        //Termina archivos adjuntos
        
        toReturn.append("<p>"+ paramRequest.getLocaleString("lbl_Message") +"<textarea name=\"message\" rows=\"10\" cols=\"50\"></textarea></p>");
        toReturn.append("<p><button type=\"submit\">"
                + paramRequest.getLocaleString("lbl_Send") + "</button>");
        toReturn.append("</form>");
        toReturn.append("</div>");

        toReturn.append("\n <script type=\"text/javascript\">");
        toReturn.append("\n  function getTo() {");
        toReturn.append("\n   var to = document.getElementById('To').value;");
        toReturn.append("if (document.getElementById('toText').value.indexOf(to) == -1){ ");
        toReturn.append("\n document.getElementById('toText').value+=to +\";\";");
        toReturn.append("}");
        toReturn.append("\n document.getElementById('To').value = \"\";");
        toReturn.append("\n  };");
        toReturn.append("\n  function getCc() {");
        toReturn.append("\n   var cc = document.getElementById('Cc').value;");
        toReturn.append("if (document.getElementById('ccText').value.indexOf(cc) == -1){ ");
        toReturn.append("\n document.getElementById('ccText').value+=cc +\";\";");
        toReturn.append("}");
        toReturn.append("\n document.getElementById('Cc').value = \"\";");
        toReturn.append("\n  };");
        toReturn.append("\n </script>");

        out.println(toReturn.toString());
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
        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
            String emailFrom = request.getParameter("emailFrom") == null ? "" : request.getParameter("emailFrom");
            String nameFrom = request.getParameter("nameFrom") == null ? "" : request.getParameter("nameFrom");
            String subject = request.getParameter("subject") == null ? "" : request.getParameter("subject");
            String message = request.getParameter("message") == null ? "" : request.getParameter("message");
            String to = request.getParameter("toText") == null ? "" : request.getParameter("toText");
            String cc = request.getParameter("ccText") == null ? "" : request.getParameter("ccText");
            Collection listEmails = new ArrayList<String>();
            Collection listCcEmails = new ArrayList<String>();

            if (to != "") {
                String emailsArray[] = to.split(";");
                for (String emails : emailsArray) {
                    listEmails.add(emails);
                    System.out.println("\n" + emails);
                }
            }
            //Valida que traiga correos Cc
            if (cc != "") {
                String ccArray[] = cc.split(";");
                for (String ccEmails : ccArray) {
                    listCcEmails.add(ccEmails);
                    System.out.println("\n" + ccEmails);
                }
            }
           SWBUtils.EMAIL.sendMail("ana.garcias@infotec.com.mx", subject, message);
          
           /*SWBMail mail = new SWBMail();
           mail.setFromEmail(emailFrom);
           mail.setFromName(nameFrom);
           mail.setSubject(subject);
           mail.setToEmail(listEmails);
           mail.setCcEmail(listCcEmails);
           mail.setContentType("text/html");*/
           
           //EmailAttachment an = new 
                                 
           //SWBUtils.EMAIL.sendMail(mail);
          
        } else {
            super.processAction(request, response); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
