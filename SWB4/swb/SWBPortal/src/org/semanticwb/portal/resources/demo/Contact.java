/**  
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
**/ 
 
// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Contact.java

package org.semanticwb.portal.resources.demo;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

public class Contact extends GenericAdmResource
{
private static Logger log = SWBUtils.getLogger(Contact.class);
    public Contact()
    {
    }

    public void processAction(HttpServletRequest request, SWBActionResponse response)
        throws SWBResourceException, IOException
    {
        String site = response.getWebPage().getWebSite().getTitle();
        String cto = response.getResourceBase().getAttribute("cto");
        String name = stringNullValidate(request.getParameter("name"));
        String email = stringNullValidate(request.getParameter("email"));
        String subject = stringNullValidate(request.getParameter("subject"));
        String message = stringNullValidate(request.getParameter("message"));
        String rating = stringNullValidate(request.getParameter("rating"));
        String parausuario = (new StringBuilder()).append(name).append(", Muchas gracias por enviar tus comentarios y/o sugerencias acerca de ").append(site).append(".\n").toString();
        parausuario = (new StringBuilder()).append(parausuario).append("En un lapso de 24 horas responderemos a tu correo electr\363nico.\n").toString();
        parausuario = (new StringBuilder()).append(parausuario).append("Su mensaje fue enviado a la siguiente direcci\363n de correo electr\363nico:  ").append(cto).append("\n\n").toString();
        parausuario = (new StringBuilder()).append(parausuario).append("Sinceramente,\n").toString();
        parausuario = (new StringBuilder()).append(parausuario).append(site).append("\n").toString();
        String administrador = "------Formulario de Comentarios y Sugerencias------\n";
        administrador = (new StringBuilder()).append(administrador).append("Nombre completo: ").append(name).append("\n").toString();
        administrador = (new StringBuilder()).append(administrador).append("Email: ").append(email).append("\n").toString();
        administrador = (new StringBuilder()).append(administrador).append("Asunto: ").append(subject).append("\n").toString();
        administrador = (new StringBuilder()).append(administrador).append("Rating: ").append(rating).append("\n").toString();
        administrador = (new StringBuilder()).append(administrador).append("Comentarios: ").append(message).append("\n").toString();
        administrador = (new StringBuilder()).append(administrador).append("----------Informaci\363n de quien me visita----------\n").toString();
        for(Enumeration en = request.getHeaderNames(); en.hasMoreElements();)
        {
            String key = (String)en.nextElement();
            administrador = (new StringBuilder()).append(administrador).append(key).append(": ").append(request.getHeader(key)).append("\n").toString();
        }

        String gracias = "Gracias por contactarnos...";
        boolean ok = sendEmail(email, cto, null, null, (new StringBuilder()).append("Contacto del Sitio - ").append(subject).toString(), "text/plain", 0, administrador);
        if(ok)
        {
            sendEmail(cto, email, null, null, gracias, "text/plain", 0, parausuario);
            response.setRenderParameter("email", "sended");
            response.setRenderParameter("name", name);
        } else
        {
            response.setRenderParameter("email", "error");
        }
    }

    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest)
        throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        if(email == null)
        {
            out.println("          <font face=\"Arial, Helvetica, sans-serif\">");
            out.println("          <table width=\"100%\">");
            out.println((new StringBuilder()).append("            <form action=\"").append(paramsRequest.getActionUrl()).append("\" method=\"post\">").toString());
            out.println("              <tr>");
            out.println((new StringBuilder()).append("                <td width=\"20%\"><font size=\"2\"><b>").append(paramsRequest.getLocaleString("name")).append("</b></font></td>").toString());
            out.println("                <td><input name=\"name\" id=\"name\" size=\"50\" /></td>");
            out.println("              </tr>");
            out.println("              <tr>");
            out.println((new StringBuilder()).append("                <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("email")).append("</b></font></td>").toString());
            out.println("                <td><input name=\"email\" id=\"email\" size=\"50\" /></td>");
            out.println("              </tr>");
            out.println("              <tr>");
            out.println((new StringBuilder()).append("                <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("subject")).append("</b></font></td>").toString());
            out.println("                <td ><input name=\"subject\" id=\"subject\" size=\"50\" /></td>");
            out.println("              </tr>");
            out.println("              <tr>");
            out.println((new StringBuilder()).append("                <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("message")).append("</b></font></td>").toString());
            out.println("                <td ><textarea name=\"message\" cols=\"50\" rows=\"5\"></textarea></td>");
            out.println("              </tr>");
            out.println("              <tr>");
            out.println("              <tr>");
            out.println((new StringBuilder()).append("                <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("rating")).append("</b></font></td>").toString());
            out.println("                <td><font size=\"2\">");
            out.println((new StringBuilder()).append("                   <input TYPE=\"RADIO\" NAME=\"rating\" VALUE=\"Buscadores\" CHECKED style=\"font-weight: 700\"><b>").append(paramsRequest.getLocaleString("searchs")).append("</b>").toString());
            out.println((new StringBuilder()).append("                   <input TYPE=\"RADIO\" NAME=\"rating\" VALUE=\"Otras Web\" style=\"font-weight: 700\"><b>").append(paramsRequest.getLocaleString("others")).append("</b>").toString());
            out.println((new StringBuilder()).append("                   <input TYPE=\"RADIO\" NAME=\"rating\" VALUE=\"Radio, Televisi\363n, Medios Gr\341ficos, etc\" style=\"font-weight: 700\"><b>").append(paramsRequest.getLocaleString("medias")).append("</b>").toString());
            out.println("\t\t     </font></td>");
            out.println("              </tr>");
            out.println("              <tr>");
            out.println("                <td colspan=\"2\" align=\"center\">");
            out.println((new StringBuilder()).append("                    <input name=\"submit\" type=\"submit\" value=\"").append(paramsRequest.getLocaleString("send")).append("\" />").toString());
            out.println("                  &nbsp;&nbsp;&nbsp;");
            out.println((new StringBuilder()).append("                  <input name=\"reset\" type=\"reset\" value=\"").append(paramsRequest.getLocaleString("reset")).append("\" />").toString());
            out.println("                </td>");
            out.println("              </tr>");
            out.println("            </form>");
            out.println("          </table>");
            out.println("          </font>");
        } else
        if(email.equals("sended"))
        {
            String site = paramsRequest.getWebPage().getWebSite().getTitle();
            String cto = paramsRequest.getResourceBase().getAttribute("cto");
            String name = stringNullValidate(request.getParameter("name"));
            String parausuario = (new StringBuilder()).append(name).append(", Muchas gracias por enviar tus comentarios y/o sugerencias acerca de ").append(site).append(".\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append("En un lapso de 24 horas responderemos a tu correo electr\363nico.\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append("Su mensaje fue enviado a la siguiente direcci\363n de correo electr\363nico:  ").append(cto).append("\n\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append("Sinceramente,\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append(site).append("\n").toString();
            out.println("<pre>");
            out.println(parausuario);
            out.println("</pre>");
        } else
        {
            String site = paramsRequest.getWebPage().getWebSite().getTitle();
            out.println("Por el momento no fue posible enviar el comentario, ");
            out.println("le agradeceremos intentar mas tarde o bien utilizar otro medio.<br><br>");
            out.println("Muchas Gracias<br>");
            out.println(site);
        }
    }

    private static String stringNullValidate(Object obj)
    {
        if(obj==null)return "";
        else return obj.toString();
    }

     private static boolean sendEmail(String fromEmail, String toEmail, String ccEmail, String bccEmail,
                                    String subject, String contentType, int priority, String data)
    {
        String host=SWBPlatform.getEnv("wb/smtpServer");
        String user=SWBPlatform.getEnv("wb/smtpUser");
        String password=SWBPlatform.getEnv("wb/smtpPassword");

        if(user!=null && password!=null)
        {
            return sendSEmail(fromEmail,toEmail,ccEmail,bccEmail,subject,contentType,priority,data);
        }

        try
        {
            sun.net.smtp.SmtpClient sm = new sun.net.smtp.SmtpClient(host);
            sm.from(fromEmail);
            sm.to(toEmail);
            if (ccEmail != null) sm.to(ccEmail);
            if (bccEmail != null) sm.to(bccEmail);
            java.io.PrintStream msg = sm.startMessage();
            msg.println("From: " + fromEmail);
            msg.println("To: " + toEmail);	   // Note dont use + for Performance
            if (ccEmail != null) msg.println("CC: " + ccEmail);
            //if(bccEmail!=null)msg.println("BCC: "+bccEmail);
            msg.println("Subject: " + subject);
            if (priority > 0) msg.println("X-Priority: " + priority);
            if (contentType != null) msg.println("Content-Type: " + contentType);
            //msg.println("X-MSMail-Priority: high");
            msg.println();
            msg.println(data);
            msg.println();
            sm.closeServer();
            return true;
        } catch (Exception e)
        {
            log.error("Error: al enviar correo", e);
        }
        return false;
    }


    private static boolean sendSEmail(String fromEmail, String toEmail, String ccEmail, String bccEmail,
                                    String subject, String contentType, int priority, String data)
    {
        try
        {
            String host=SWBPlatform.getEnv("wb/smtpServer");
            String user=SWBPlatform.getEnv("wb/smtpUser");
            String password=SWBPlatform.getEnv("wb/smtpPassword");

            Properties prop = new Properties();
            prop.put("mail.host", host);
            prop.put("mail.user", user);
            prop.put("mail.password", password);
            prop.put("mail.smtp.auth", "true");

            javax.mail.Session session1 = javax.mail.Session.getDefaultInstance(prop, null);

            // Create new message
            javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(session1);

            if (subject != null)
                msg.setSubject(subject);
            msg.setFrom(new javax.mail.internet.InternetAddress(fromEmail));
            if(toEmail!= null)
                msg.addRecipient(javax.mail.Message.RecipientType.TO, new javax.mail.internet.InternetAddress(toEmail));
            if(ccEmail!= null)
                msg.addRecipient(javax.mail.Message.RecipientType.CC, new javax.mail.internet.InternetAddress(ccEmail));
            if(bccEmail!=null)
                msg.addRecipient(javax.mail.Message.RecipientType.BCC, new javax.mail.internet.InternetAddress(bccEmail));
            if(contentType!=null)
                msg.setContent(data,contentType);
            else
                msg.setText(data);
            if (priority > 0)
                msg.setHeader("X-Priority",""+priority);

            javax.mail.Session _mailSession = javax.mail.Session.getDefaultInstance(prop, null);

            // Send the message
            //Transport.send(msg);
            javax.mail.Transport tr = _mailSession.getTransport("smtp");
            tr.connect(host, user, password);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();

            // Print a message acknowledging that the message
            // was sent
        }
        catch(Exception e)
        {
            log.error("Error: al enviar correo", e);
            return false;
        }
        return true;
    }


}
