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
 

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;

public class Contact extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(Banner.class);

    private String webWorkPath= "/work";

    /**
     * @param base
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            webWorkPath = SWBPortal.getWebWorkPath() +  base.getWorkPath();
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();

        String site = response.getWebPage().getWebSite().getDisplayTitle(response.getUser().getLanguage());
        String contact = base.getAttribute("email");
        // TODO
        /*String name = SWBUtils.stringNullValidate(request.getParameter("name"));
        String email = SWBUtils.stringNullValidate(request.getParameter("email"));
        String subject = SWBUtils.stringNullValidate(request.getParameter("subject"));
        String message = SWBUtils.stringNullValidate(request.getParameter("message"));
        String rating = SWBUtils.stringNullValidate(request.getParameter("rating"));*/
        String name = request.getParameter("name");
        String customer = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
//        String rating = request.getParameter("rating");
        
        StringBuilder msgToCustomer = new StringBuilder();
        msgToCustomer.append("Gracias por contactarnos...\nEn un lapso de 24 horas responderemos a tu correo electr\363nico.");
        msgToCustomer.append("Su mensaje fue enviado a la siguiente direcci\363n de correo electr\363nico: "+contact+"\n\n");

        StringBuilder msgToContact = new StringBuilder();
        msgToContact.append("Site: "+site);
        msgToContact.append("\nNombre: "+name);
        msgToContact.append("\nemail: "+customer);
        msgToContact.append("\nAsunto: "+subject);
        msgToContact.append("\nMensaje: "+message);

        System.out.println("msgToCustomer="+msgToCustomer+"\n\nmsgToContact="+msgToContact+"\n******************");

        try{
            if(customer!=null && customer.trim().length()>0 && message!=null && message.trim().length()>0)
            {
                // send email to contact
                InternetAddress address1 = new InternetAddress();
                address1.setAddress(contact);
                ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                aAddress.add(address1);
                SWBUtils.EMAIL.sendMail(customer, name, aAddress, null, null, subject, "text/plain", msgToContact.toString(), null, null, null);

// send email to customer
address1 = new InternetAddress();
address1.setAddress(customer);
aAddress = new ArrayList<InternetAddress>();
aAddress.add(address1);
SWBUtils.EMAIL.sendMail(contact, name, aAddress, null, null, subject, "text/plain", msgToCustomer.toString(), null, null, null);

                response.setRenderParameter("email", "sended");
                response.setRenderParameter("name", name);


            }else{
                response.setRenderParameter("email", "missdata");
            }
        }catch(Exception e) {
            System.out.println("\n\nerror:"+e);
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
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        if(email == null) {
            boolean modal = Boolean.parseBoolean(base.getAttribute("modal"));
            if(modal) {
                out.println("<script type=\"text/javascript\">");
                out.println("  function createCoverDiv(divId, bgcolor, opacity) {");
                out.println("    var layer=document.createElement('div');");
                out.println("    layer.id=divId;");
                out.println("    layer.style.width='100%';");
                out.println("    layer.style.height='100%';");
                out.println("    layer.style.backgroundColor=bgcolor;");
                out.println("    layer.style.position='absolute';");
                out.println("    layer.style.top=0;");
                out.println("    layer.style.left=0;");
                out.println("    layer.style.zIndex=1000;");
                out.println("    layer.style.filter='alpha(opacity='+opacity+')';");
                out.println("    layer.style.opacity=opacity/100;");
                out.println("    document.body.appendChild(layer);");
                out.println("  }");

                out.println("  function removeCoverDiv(divId) {");
                out.println("    var layer=document.getElementById(divId);");
                out.println("    var superlayer=document.getElementById('s_'+divId);");
                out.println("    if(layer && superlayer) {");
                out.println("        document.body.removeChild(superlayer);");
                out.println("        document.body.removeChild(layer);");
                out.println("    }");
                out.println("  }");

                out.println("  function displayImage(divId, bgcolor, opacity) {");
                out.println("    createCoverDiv(divId, bgcolor, opacity);");

                out.println("    var contactContainer=document.createElement('div');");
                out.print("contactContainer.innerHTML = ");
                out.print("'<form action=\""+paramsRequest.getActionUrl()+"\" method=\"post\">");
                out.print("<table width=\"90%\" bgcolor=\"#FFFFFF\">");
                out.print("<tr>");
                out.print("<td width=\"20%\">"+paramsRequest.getLocaleString("name")+"</td>");
                out.print("<td><input name=\"name\" id=\"name\" size=\"50\" /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>"+paramsRequest.getLocaleString("email")+"</td>");
                out.print("<td><input name=\"email\" id=\"email\" size=\"50\" /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>"+paramsRequest.getLocaleString("subject")+"</td>");
                out.print("<td ><input name=\"subject\" id=\"subject\" size=\"50\" /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>"+paramsRequest.getLocaleString("message")+"</td>");
                out.print("<td ><textarea name=\"message\" cols=\"50\" rows=\"5\"></textarea></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan=\"2\" align=\"center\">");
                out.print("<input name=\"submit\" type=\"submit\" value=\""+paramsRequest.getLocaleString("send")+"\" />");
                out.print("&nbsp;&nbsp;&nbsp;");
                out.print("<input name=\"reset\" type=\"reset\" value=\""+paramsRequest.getLocaleString("reset")+"\" />");
                out.print("&nbsp;&nbsp;&nbsp;");
                out.print("<input name=\"cancel\" type=\"button\" onclick=\"removeCoverDiv(\\''+divId+'\\')\" value=\""+paramsRequest.getLocaleString("cancel")+"\" />");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form>';");

                out.println("    var cwidth=500;");
                out.println("    var cheight=500;");
                out.println("    contactContainer.id='s_'+divId;");
                out.println("    contactContainer.style.zIndex=1001;");
                out.println("    contactContainer.style.position='absolute';");
                out.println("    contactContainer.style.top='50%';");
                out.println("    contactContainer.style.left='50%';");
                out.println("    contactContainer.style.marginLeft=-cwidth/2+'px';");
                out.println("    contactContainer.style.marginTop=-cheight/2+'px';");
                out.println("    contactContainer.style.width=cwidth+'px';");
                out.println("    contactContainer.style.height=cheight+'px';");
                out.println("    document.body.appendChild(contactContainer);");
                out.println("  }");
                out.println("</script>");
                if( base.getAttribute("link")!=null )
                    out.println("<a href=\"#\" onclick=\"displayImage('cover01','#000000',80)\">"+base.getAttribute("link")+"</a>");
                else if( base.getAttribute("label")!=null )
                    out.println("<input type=\"button\" onclick=\"displayImage('cover01','#000000',80)\" value=\""+base.getAttribute("label")+"\" />");
                else
                    out.println("<img alt=\""+base.getAttribute("image")+"\" src=\""+webWorkPath+"/"+base.getAttribute("image")+"\" onclick=\"displayImage('cover01','#000000',80)\" />");
            }else {
                out.print("<form action=\""+paramsRequest.getActionUrl()+"\" method=\"post\">");
                out.print("<table width=\"500\" bgcolor=\"#FFFFFF\">");
                out.print("<tr>");
                out.print("<td width=\"20%\">"+paramsRequest.getLocaleString("name")+"</td>");
                out.print("<td><input name=\"name\" id=\"name\" size=\"50\" /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>"+paramsRequest.getLocaleString("email")+"</td>");
                out.print("<td><input name=\"email\" id=\"email\" size=\"50\" /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>"+paramsRequest.getLocaleString("subject")+"</td>");
                out.print("<td ><input name=\"subject\" id=\"subject\" size=\"50\" /></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>"+paramsRequest.getLocaleString("message")+"</td>");
                out.print("<td ><textarea name=\"message\" cols=\"50\" rows=\"5\"></textarea></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan=\"2\" align=\"center\">");
                out.print("<input name=\"submit\" type=\"submit\" value=\""+paramsRequest.getLocaleString("send")+"\" />");
                out.print("&nbsp;&nbsp;&nbsp;");
                out.print("<input name=\"reset\" type=\"reset\" value=\""+paramsRequest.getLocaleString("reset")+"\" />");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form>");
            }
        }
//        else if(email.equals("sended")) {
//            String site = paramsRequest.getWebPage().getWebSite().getDisplayTitle(paramsRequest.getUser().getLanguage());
//            String cto = paramsRequest.getResourceBase().getAttribute("email");
//            String name = request.getParameter("name");
//            String parausuario = (new StringBuilder()).append(name).append(", Muchas gracias por enviar su comentario y/o sugerencias acerca de ").append(site).append(".\n").toString();
//            parausuario = (new StringBuilder()).append(parausuario).append("En un lapso de 24 horas responderemos a su correo electr\363nico.\n").toString();
//            parausuario = (new StringBuilder()).append(parausuario).append("Su mensaje fue enviado a la siguiente direcci\363n de correo electr\363nico:  ").append(cto).append("\n\n").toString();
//            parausuario = (new StringBuilder()).append(parausuario).append("Sinceramente,\n").toString();
//            parausuario = (new StringBuilder()).append(parausuario).append(site).append("\n").toString();
//            out.println("<pre>");
//            out.println(parausuario);
//            out.println("</pre>");
//        }
        else if(email.equals("missdata")) {
            out.println("<pre>");
            out.println("Lo sentimos, por el momento no fue posible enviar su comentario.<br>");
            out.println("Falta información para el envío de su correo:<br/>");
            out.println("Debe escribir su correo electrónico y mensaje como minimo<br/><br/>");
            out.println("<pre>");
        }
//        else {
//            String site = paramsRequest.getWebPage().getWebSite().getDisplayTitle(paramsRequest.getUser().getLanguage());
//            out.println("Lo sentimos, por el momento no fue posible enviar su comentario, ");
//            out.println("le agradeceremos intentarlo más tarde o bien utilizar otro medio.<br/><br/>");
//            out.println("Por su atención, muchas gracias.<br/>");
//            out.println(site);
//        }
    }

    /*private String buildForm(SWBParamRequest paramsRequest) throws SWBResourceException {
            StringBuilder frm = new StringBuilder();
            frm.append("<font face=\"Arial, Helvetica, sans-serif\">");
            frm.append("<form action=\""+paramsRequest.getActionUrl()+"\" method=\"post\">");
            frm.append("<table width=\"90%\" bgcolor=\"#FFFFFF\">");
            frm.append("<tr>");
            frm.append("<td width=\"20%\"><font size=\"2\"><strong>"+paramsRequest.getLocaleString("name")+"</strong></font></td>");
            frm.append("<td><input name=\"name\" id=\"name\" size=\"50\" /></td>");
            frm.append("</tr>");
            frm.append("<tr>");
            frm.append("<td><font size=\"2\"><strong>"+paramsRequest.getLocaleString("email")+"</strong></font></td>");
            frm.append("<td><input name=\"email\" id=\"email\" size=\"50\" /></td>");
            frm.append("</tr>");
            frm.append("<tr>");
            frm.append("<td><font size=\"2\"><strong>"+paramsRequest.getLocaleString("subject")+"</strong></font></td>");
            frm.append("<td ><input name=\"subject\" id=\"subject\" size=\"50\" /></td>");
            frm.append("</tr>");
            frm.append("<tr>");
            frm.append("<td><font size=\"2\"><strong>"+paramsRequest.getLocaleString("message")+"</strong></font></td>");
            frm.append("<td ><textarea name=\"message\" cols=\"50\" rows=\"5\"></textarea></td>");
            frm.append("</tr>");
            frm.append("<tr>");
            frm.append("<td colspan=\"2\" align=\"center\">");
            frm.append("<input name=\"submit\" type=\"submit\" value=\""+paramsRequest.getLocaleString("send")+"\" />");
            frm.append("&nbsp;&nbsp;&nbsp;");
            frm.append("<input name=\"reset\" type=\"reset\" value=\""+paramsRequest.getLocaleString("reset")+"\" />");
            frm.append("&nbsp;&nbsp;&nbsp;");
            frm.append("<input name=\"cancel\" type=\"button\" onclick=\"removeCoverDiv(\''+divId+'\')\" value=\""+paramsRequest.getLocaleString("cancel")+"\" />");
            frm.append("</td>");
            frm.append("</tr>");
            frm.append("</table>");
            frm.append("</form>");
            frm.append("</font>");
            return frm.toString();
    }*/
}
