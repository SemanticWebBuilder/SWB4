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
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("sendEmail")) {
            doSendEmail(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    public void doSendEmail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();

        String site = base.getWebSite().getDisplayTitle(paramsRequest.getUser().getLanguage());
        String contact = base.getAttribute("email");

        String name = request.getParameter("name");
        String customer = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

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

//                response.setRenderParameter("email", "sended");
//                response.setRenderParameter("name", name);


            }else{
//                response.setRenderParameter("email", "missdata");
            }
        }catch(Exception e) {
            System.out.println("\n\nerror:"+e);
//            response.setRenderParameter("email", "error");
        }
    }

//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        Resource base = getResourceBase();
//
//        String site = response.getWebPage().getWebSite().getDisplayTitle(response.getUser().getLanguage());
//        String contact = base.getAttribute("email");
//
//        String name = request.getParameter("name");
//        String customer = request.getParameter("email");
//        String subject = request.getParameter("subject");
//        String message = request.getParameter("message");
//
//        StringBuilder msgToCustomer = new StringBuilder();
//        msgToCustomer.append("Gracias por contactarnos...\nEn un lapso de 24 horas responderemos a tu correo electr\363nico.");
//        msgToCustomer.append("Su mensaje fue enviado a la siguiente direcci\363n de correo electr\363nico: "+contact+"\n\n");
//
//        StringBuilder msgToContact = new StringBuilder();
//        msgToContact.append("Site: "+site);
//        msgToContact.append("\nNombre: "+name);
//        msgToContact.append("\nemail: "+customer);
//        msgToContact.append("\nAsunto: "+subject);
//        msgToContact.append("\nMensaje: "+message);
//
//        System.out.println("msgToCustomer="+msgToCustomer+"\n\nmsgToContact="+msgToContact+"\n******************");
//
//        try{
//            if(customer!=null && customer.trim().length()>0 && message!=null && message.trim().length()>0)
//            {
//                // send email to contact
//                InternetAddress address1 = new InternetAddress();
//                address1.setAddress(contact);
//                ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
//                aAddress.add(address1);
//                SWBUtils.EMAIL.sendMail(customer, name, aAddress, null, null, subject, "text/plain", msgToContact.toString(), null, null, null);
//
//                // send email to customer
//                address1 = new InternetAddress();
//                address1.setAddress(customer);
//                aAddress = new ArrayList<InternetAddress>();
//                aAddress.add(address1);
//                SWBUtils.EMAIL.sendMail(contact, name, aAddress, null, null, subject, "text/plain", msgToCustomer.toString(), null, null, null);
//
//                response.setRenderParameter("email", "sended");
//                response.setRenderParameter("name", name);
//
//
//            }else{
//                response.setRenderParameter("email", "missdata");
//            }
//        }catch(Exception e) {
//            System.out.println("\n\nerror:"+e);
//            response.setRenderParameter("email", "error");
//        }
//    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        if(email == null) {
            boolean modal = Boolean.parseBoolean(base.getAttribute("modal"));
            if(modal) {
                out.println("<script type=\"text/javascript\">");



out.println("var xmlhttp,alerted;");
out.println("/*@cc_on @*/");
out.println("/*@if (@_jscript_version >= 5)");
out.println("// JScript gives us Conditional compilation, we can cope with old IE versions.");
out.println("  try {");
out.println("  xmlhttp=new ActiveXObject(\"Msxml2.XMLHTTP\");");
out.println(" } catch (e) {");
out.println("  try {");
out.println("    xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");");
out.println("  } catch (E) {");
out.println("   alert('You must have Microsofts XML parsers available');");
out.println("  }");
out.println(" }");
out.println("@else");
out.println(" alert('You must have JScript version 5 or above.');");
out.println(" xmlhttp=false;");
out.println(" alerted=true;");
out.println("@end @*/");
out.println("if (!xmlhttp && !alerted) {");
out.println(" try {");
out.println("  xmlhttp = new XMLHttpRequest();");
out.println(" } catch (e) {");
out.println("  alert('You need a browser which supports an XMLHttpRequest Object');");
out.println(" }");
out.println("}");

out.println("function RSchange() {");
out.println(" if (xmlhttp.readyState==4) {");
//out.println("  document.getElementById('content').innerHTML=xmlhttp.responseText;");
out.println("   alert('ok');");
out.println(" }");
out.println("}");

out.println("function justdoit(url) {");
out.println("  if (xmlhttp) {");
out.println("    d=document;");
out.println("    xmlhttp.open(\"GET\", url, true);");
out.println("    xmlhttp.onreadystatechange=RSchange;");
out.println("    xmlhttp.send(null);");
out.println("  }");
out.println("}");




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
                out.print("'<form action=\""+paramsRequest.getActionUrl()+"\" method=\"post\" >");
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
                out.print("<td ><textarea name=\"message\" id=\"message\" cols=\"50\" rows=\"5\"></textarea></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan=\"2\" align=\"center\">");

SWBResourceURL url=paramsRequest.getRenderUrl();
url.setCallMethod(url.Call_DIRECT).setMode("sendEmail");

                out.print("<input name=\"submit\" type=\"button\" onclick=\"justdoit(\\'"+url+"\\'+\\'?name=\\'+dojo.byId(\\'name\\').value+\\'&email=\\'+dojo.byId(\\'email\\').value+\\'&subject=\\'+dojo.byId(\\'subject\\').value+\\'&message=\\'+dojo.byId(\\'message\\').value);removeCoverDiv(\\''+divId+'\\')\" value=\""+paramsRequest.getLocaleString("send")+"\" />");
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
        else if(email.equals("missdata")) {
            out.println("<pre>");
            out.println("Lo sentimos, por el momento no fue posible enviar su comentario.<br>");
            out.println("Falta información para el envío de su correo:<br/>");
            out.println("Debe escribir su correo electrónico y mensaje como minimo<br/><br/>");
            out.println("<pre>");
        }
    }
}
