
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;

public class Contact extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(Banner.class);

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();

        String site = response.getWebPage().getWebSite().getDisplayTitle(response.getUser().getLanguage());
        String cto = base.getAttribute("cto");
        // TODO
        /*String name = SWBUtils.stringNullValidate(request.getParameter("name"));
        String email = SWBUtils.stringNullValidate(request.getParameter("email"));
        String subject = SWBUtils.stringNullValidate(request.getParameter("subject"));
        String message = SWBUtils.stringNullValidate(request.getParameter("message"));
        String rating = SWBUtils.stringNullValidate(request.getParameter("rating"));*/
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        String rating = request.getParameter("rating");
        
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
        try{
            //SWBUtils.EMAIL.sendBGEMail(email, cto, null, null, (new StringBuilder()).append("Contacto del Sitio - ").append(subject).toString(), "text/plain", 0, administrador);
            //SWBUtils.EMAIL.sendBGEMail(cto, email, null, null, gracias, "text/plain", 0, parausuario);
            response.setRenderParameter("email", "sended");
            response.setRenderParameter("name", name);
        }catch(Exception e) {
            response.setRenderParameter("email", "error");
        }
        /*if(ok) {
            SWBUtils.EMAIL.sendBGEMail(cto, email, null, null, gracias, "text/plain", 0, parausuario);
            response.setRenderParameter("email", "sended");
            response.setRenderParameter("name", name);
        }else {
            response.setRenderParameter("email", "error");
        }*/
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        if(email == null)
        {
            out.println("<font face=\"Arial, Helvetica, sans-serif\">");
            out.println("<table width=\"100%\">");
            out.println((new StringBuilder()).append("<form action=\"").append(paramsRequest.getActionUrl()).append("\" method=\"post\">").toString());
            out.println("<tr>");
            out.println((new StringBuilder()).append("  <td width=\"20%\"><font size=\"2\"><b>").append(paramsRequest.getLocaleString("name")).append("</b></font></td>").toString());
            out.println("  <td><input name=\"name\" id=\"name\" size=\"50\" /></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println((new StringBuilder()).append("  <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("email")).append("</b></font></td>").toString());
            out.println("  <td><input name=\"email\" id=\"email\" size=\"50\" /></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println((new StringBuilder()).append("  <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("subject")).append("</b></font></td>").toString());
            out.println("  <td ><input name=\"subject\" id=\"subject\" size=\"50\" /></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println((new StringBuilder()).append("  <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("message")).append("</b></font></td>").toString());
            out.println("  <td ><textarea name=\"message\" cols=\"50\" rows=\"5\"></textarea></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<tr>");
            out.println((new StringBuilder()).append("  <td><font size=\"2\"><b>").append(paramsRequest.getLocaleString("rating")).append("</b></font></td>").toString());
            out.println("  <td><font size=\"2\">");
            out.println((new StringBuilder()).append("     <input TYPE=\"RADIO\" NAME=\"rating\" VALUE=\"Buscadores\" CHECKED style=\"font-weight: 700\"><b>").append(paramsRequest.getLocaleString("searchs")).append("</b>").toString());
            out.println((new StringBuilder()).append("     <input TYPE=\"RADIO\" NAME=\"rating\" VALUE=\"Otras Web\" style=\"font-weight: 700\"><b>").append(paramsRequest.getLocaleString("others")).append("</b>").toString());
            out.println((new StringBuilder()).append("     <input TYPE=\"RADIO\" NAME=\"rating\" VALUE=\"Radio, Televisi\363n, Medios Gr\341ficos, etc\" style=\"font-weight: 700\"><b>").append(paramsRequest.getLocaleString("medias")).append("</b>").toString());
            out.println("\t\t     </font></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("  <td colspan=\"2\" align=\"center\">");
            out.println((new StringBuilder()).append("      <input name=\"submit\" type=\"submit\" value=\"").append(paramsRequest.getLocaleString("send")).append("\" />").toString());
            out.println("    &nbsp;&nbsp;&nbsp;");
            out.println((new StringBuilder()).append("    <input name=\"reset\" type=\"reset\" value=\"").append(paramsRequest.getLocaleString("reset")).append("\" />").toString());
            out.println("  </td>");
            out.println("</tr>");
            out.println("</form>");
            out.println("</table>");
            out.println("</font>");
        }else if(email.equals("sended")) {
            String site = paramsRequest.getWebPage().getWebSite().getDisplayTitle(paramsRequest.getUser().getLanguage());
            String cto = paramsRequest.getResourceBase().getAttribute("cto");
            String name = request.getParameter("name");
            String parausuario = (new StringBuilder()).append(name).append(", Muchas gracias por enviar su comentario y/o sugerencias acerca de ").append(site).append(".\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append("En un lapso de 24 horas responderemos a su correo electr\363nico.\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append("Su mensaje fue enviado a la siguiente direcci\363n de correo electr\363nico:  ").append(cto).append("\n\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append("Sinceramente,\n").toString();
            parausuario = (new StringBuilder()).append(parausuario).append(site).append("\n").toString();
            out.println("<pre>");
            out.println(parausuario);
            out.println("</pre>");
        }else {
            String site = paramsRequest.getWebPage().getWebSite().getDisplayTitle(paramsRequest.getUser().getLanguage());
            out.println("Lo sentimos, por el momento no fue posible enviar su comentario, ");
            out.println("le agradeceremos intentarlo más tarde o bien utilizar otro medio.<br><br>");
            out.println("Por su atención, muchas gracias.<br>");
            out.println(site);
        }
    }
}
