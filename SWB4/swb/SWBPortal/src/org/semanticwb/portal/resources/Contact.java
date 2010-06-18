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
import java.util.Iterator;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class Contact.
 */
public class Contact extends GenericAdmResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Banner.class);

    /** The web work path. */
    private String webWorkPath= "/work";

    //private String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBMenu/";

    private static final String _FAIL = "failure";

    private javax.xml.transform.Templates tpl;

    /**
     * Sets the resource base.
     *
     * @param base the new resource base
     */
    @Override
    public void setResourceBase(Resource base) {
//        try {
//            super.setResourceBase(base);
//            webWorkPath = SWBPortal.getWebWorkPath() +  base.getWorkPath();
//        }catch(Exception e) {
//            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
//        }
        try {
            super.setResourceBase(base);
            webWorkPath = SWBPortal.getWebWorkPath()+base.getWorkPath();
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }

        if( base.getAttribute("template")!=null ) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
            }catch(Exception e) {
                log.error("Error while loading resource template: "+base.getId(), e);
            }
        }
        if(tpl==null) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Contact/Contact.xsl"));
            }catch(Exception e) {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("sendEmail")) {
            doSendEmail(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    private String processEmails(HttpServletRequest request, SWBParamRequest paramRequest) throws Exception {
        Resource base = getResourceBase();
        String ret;

        String site = base.getWebSite().getDisplayTitle(paramRequest.getUser().getLanguage());
        String customerName = request.getParameter("name");
        String customerEmail = request.getParameter("email");
        if( !SWBUtils.EMAIL.isValidEmailAddress(customerEmail) ) {
            throw new Exception("Error in resource Contact while trying to send the email");
        }
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        String contactName = base.getAttribute("name");
        String contactEmail = base.getAttribute("email");
        String contactPhone = base.getAttribute("phone");
        String contactAddress = base.getAttribute("address");
        String title = base.getAttribute("title");


        if( !isEmpty(subject) && !isEmpty(message) && SWBUtils.EMAIL.isValidEmailAddress(contactEmail) ) {
//            try {
            StringBuilder msgToCustomer = new StringBuilder();
            msgToCustomer.append(paramRequest.getLocaleString("dear")+" "+customerName+" :\n");
            msgToCustomer.append(paramRequest.getLocaleString("greating"));
            msgToCustomer.append(contactName+"\n");
            msgToCustomer.append(contactEmail+"\n");
            msgToCustomer.append(title==null?"":title+"\n");
            msgToCustomer.append(contactPhone==null?"":contactPhone+"\n");
            msgToCustomer.append(contactAddress==null?"":contactAddress);
            msgToCustomer.append("\n");

            StringBuilder msgToContact = new StringBuilder();
            msgToContact.append("Site: "+site);
            msgToContact.append("\nNombre: "+customerName);
            msgToContact.append("\nemail: "+customerEmail);
            msgToContact.append("\nAsunto: "+subject);
            msgToContact.append("\nMensaje: "+message);

            // send email to contact
            InternetAddress address1 = new InternetAddress();
            address1.setAddress(contactEmail);
            ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
            aAddress.add(address1);
            ret = SWBUtils.EMAIL.sendMail(customerEmail, customerName, aAddress, null, null, subject, "text/plain", msgToContact.toString(), null, null, null);
            if(ret==null) {
                throw new Exception("Error in resource Contact while trying to send the email");
            }

            // send email to customer
            address1 = new InternetAddress();
            address1.setAddress(customerEmail);
            aAddress = new ArrayList<InternetAddress>();
            aAddress.add(address1);
            ret = SWBUtils.EMAIL.sendMail(contactEmail, customerName, aAddress, null, null, subject, "text/plain", msgToCustomer.toString(), null, null, null);
            if(ret==null) {
                throw new Exception("Error in resource Contact while trying to send the email");
            }
            ret = paramRequest.getLocaleString("thanks");
//            }catch(Exception e) {
//                log.error("Error in resource Contact, in PymTur Project, while bringing HTML by ajax. ", e);
//                ret = paramRequest.getLocaleString("apologies");
//            }
        }else {
            ret = paramRequest.getLocaleString("apologies");
        }
        return ret;
    }

    private void  processEmails(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, Exception {
        Resource base = getResourceBase();

        String site = base.getWebSite().getDisplayTitle(response.getUser().getLanguage());
        String customerName = request.getParameter("name");
        String customerEmail = request.getParameter("email");
        if( !SWBUtils.EMAIL.isValidEmailAddress(customerEmail) ) {
            throw new Exception("Error in resource Contact while trying to send the email");
        }
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        String contactName = base.getAttribute("name");
        String contactEmail = base.getAttribute("email");
        String contactPhone = base.getAttribute("phone");
        String contactAddress = base.getAttribute("address");
        String title = base.getAttribute("title");

        StringBuilder msgToCustomer = new StringBuilder();
        msgToCustomer.append(response.getLocaleString("dear")+" "+customerName+" :\n");
        msgToCustomer.append(response.getLocaleString("greating"));
        msgToCustomer.append(contactName+"\n");
        msgToCustomer.append(contactEmail+"\n");
        msgToCustomer.append(title==null?"":title+"\n");
        msgToCustomer.append(contactPhone==null?"":contactPhone+"\n");
        msgToCustomer.append(contactAddress==null?"":contactAddress);
        msgToCustomer.append("\n");

        StringBuilder msgToContact = new StringBuilder();
        msgToContact.append("Site: "+site);
        msgToContact.append("\nNombre: "+customerName);
        msgToContact.append("\nemail: "+customerEmail);
        msgToContact.append("\nAsunto: "+subject);
        msgToContact.append("\nMensaje: "+message);

        // send email to customer
        InternetAddress address1 = new InternetAddress();
        address1.setAddress(customerEmail);
        ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
        aAddress.add(address1);
        String ret = SWBUtils.EMAIL.sendMail(contactEmail, customerName, aAddress, null, null, subject, "text/plain", msgToCustomer.toString(), null, null, null);
        if( ret==null ) {
            throw new Exception("Error in resource Contact while trying to send the email");
        }

        // send email to contact
        address1 = new InternetAddress();
        address1.setAddress(contactEmail);
        aAddress = new ArrayList<InternetAddress>();
        aAddress.add(address1);
        ret = SWBUtils.EMAIL.sendMail(customerEmail, customerName, aAddress, null, null, subject, "text/plain", msgToContact.toString(), null, null, null);
        if( ret==null ) {
            throw new Exception("Error in resource Contact while trying to send the email");
        }
    }

    /**
     * Do send email.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doSendEmail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        try {
            out.print(processEmails(request, paramRequest));
        }catch(Exception e) {
            log.error("Error in resource Contact, in PymTur Project, while bringing HTML by ajax. ", e);
        }
        out.flush();
        out.close();
    }

    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=paramRequest.getResourceBase();
        User user = paramRequest.getUser();

        String title = base.getAttribute("title","");
        String contactPhone = base.getAttribute("phone");
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction(paramRequest.Action_ADD);
        
        Document  dom = SWBUtils.XML.getNewDocument();
        Element econtact = dom.createElement("contact");        
        econtact.setAttribute("title",title);
        econtact.setAttribute("url", url.toString(true));
        dom.appendChild(econtact);

        Element e;
        if( request.getParameter(_FAIL)!=null ) {
            e = dom.createElement("apologies");
            e.setNodeValue(paramRequest.getLocaleString("apologies"));
            econtact.appendChild(e);
        }

        Element insts = dom.createElement("instructions");
        econtact.appendChild(insts);

        e = dom.createElement("instruction");
        e.setTextContent(paramRequest.getLocaleString("instruction1")+" "+contactPhone);
         e.setNodeValue(paramRequest.getLocaleString("instruction1")+" "+contactPhone);
        insts.appendChild(e);

        e = dom.createElement("instruction");
        e.setTextContent(paramRequest.getLocaleString("instruction2"));
         e.setNodeValue(paramRequest.getLocaleString("instruction2"));
        insts.appendChild(e);
        

        e = dom.createElement("name");
        e.setAttribute("id", "name");
        e.setAttribute("label", paramRequest.getLocaleString("name"));
        e.setTextContent( user.isSigned()?user.getFullName():"" );
        econtact.appendChild(e);

        e = dom.createElement("email");
        e.setAttribute("id", "email");
        e.setAttribute("label", paramRequest.getLocaleString("email"));
        e.setTextContent( user.isSigned()?user.getEmail():"" );
        econtact.appendChild(e);

        e = dom.createElement("subject");
        e.setAttribute("id", "subject");
        e.setAttribute("label", paramRequest.getLocaleString("subject"));
        e.setTextContent( request.getParameter("subject")==null?"":request.getParameter("subject") );
        econtact.appendChild(e);

        e = dom.createElement("message");
        e.setAttribute("id", "message");
        e.setAttribute("label", paramRequest.getLocaleString("message"));
        e.setTextContent( request.getParameter("message")==null?"":request.getParameter("message") );
        econtact.appendChild(e);

        e = dom.createElement("command");
        e.setAttribute("submit", paramRequest.getLocaleString("send"));
        e.setAttribute("reset", paramRequest.getLocaleString("reset"));
        econtact.appendChild(e);
        return dom;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();

        String title = base.getAttribute("title");
        String contactPhone = base.getAttribute("phone");

        boolean modal = Boolean.parseBoolean(base.getAttribute("modal"));
        if(modal) {
            out.println("<script type=\"text/javascript\">");

            out.println("  function sendEmail(url) {");
            out.println("    alert(postText(url));");
            out.println("  }");

            out.println("function validateContactFrm(frm) {");
            out.println("   if(!isValidEmail(frm.email.value)) {");
            out.println("      alert('"+paramRequest.getLocaleString("emailInvalid")+"');");
            out.println("      frm.email.focus;");
            out.println("      return false;");
            out.println("   }");
            out.println("   return true;");
            out.println("}");

            out.println("  function showContactDialog(divId, bgcolor, opacity) {");
            out.println("    createCoverDiv(divId, bgcolor, opacity);");

            out.println("    var contactHolder = document.createElement('div');");

            out.println("var s = new String('');");
            out.println("s = s.concat('<div class=\"swb-contact\">');");
            out.println("s = s.concat('<form id=\"frmContact\" name=\"frmContact\" action=\"\" method=\"post\" >');");
            if( title!=null ) {
                out.println("s = s.concat('<h3>"+title+"</h3>');");
            }

            if( contactPhone!=null ) {
                out.println("s = s.concat('<p class=\"swb-contact-inst\">"+paramRequest.getLocaleString("instruction1")+" "+contactPhone+".<br />');");
                out.println("s = s.concat('"+paramRequest.getLocaleString("instruction2")+"');");
                out.println("s = s.concat('</p>');");
            }
            out.println("s = s.concat('<hr />');");
            out.println("s = s.concat('<p>')");
            out.println("s = s.concat('<label for=\"name\">"+paramRequest.getLocaleString("name")+"</label>');");
            out.println("s = s.concat('<input name=\"name\" id=\"name\" class=\"swb-contact-field\" value=\""+(user.isSigned()?user.getFullName():"")+"\" />');");
            out.println("s = s.concat('</p>');");

            out.println("s = s.concat('<p>');");
            out.println("s = s.concat('<label for=\"email\">"+paramRequest.getLocaleString("email")+"</label>');");
            out.println("s = s.concat('<input name=\"email\" id=\"email\" class=\"swb-contact-field\" value=\""+(user.isSigned()?user.getEmail():"")+"\" />');");
            out.println("s = s.concat('</p>');");

            out.println("s = s.concat('<p>');");
            out.println("s = s.concat('<label for=\"subject\">"+paramRequest.getLocaleString("subject")+"</label>');");
            out.println("s = s.concat('<input name=\"subject\" id=\"subject\" class=\"swb-contact-field\" />');");
            out.println("s = s.concat('</p>');");

            out.println("s = s.concat('<p>');");
            out.println("s = s.concat('<label for=\"message\">"+paramRequest.getLocaleString("message")+"</label>');");
            out.println("s = s.concat('<textarea name=\"message\" id=\"message\" cols=\"40\" rows=\"5\"></textarea>');");
            out.println("s = s.concat('</p>');");

            out.println("s = s.concat('<div class=\"swb-contact-cmd\" >');");
            SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("send");
            //out.println("s = s.concat('<label for=\"contactoEnviar\">"+paramRequest.getLocaleString("send")+"</label>');");
            out.println("s = s.concat('<input name=\"submit\" id=\"contactoEnviar\" type=\"button\" onclick=\"if(!validateContactFrm(this.form))return false; sendEmail(\\'"+url+"\\'+\\'&name=\\'+dojo.byId(\\'name\\').value+\\'&email=\\'+dojo.byId(\\'email\\').value+\\'&subject=\\'+dojo.byId(\\'subject\\').value+\\'&message=\\'+dojo.byId(\\'message\\').value); removeCoverDiv(\\''+divId+'\\')\" value=\""+paramRequest.getLocaleString("send")+"\" class=\"swb-contact-button\" />');");
            //out.println("s = s.concat('<label for=\"contactoRestablecer\">"+paramRequest.getLocaleString("reset")+"</label>');");
            out.println("s = s.concat('<input name=\"reset\" id=\"contactoRestablecer\" type=\"reset\" value=\""+paramRequest.getLocaleString("reset")+"\" class=\"swb-contact-button\" />');");
            //out.println("s = s.concat('<label for=\"contactoCancelar\">"+paramRequest.getLocaleString("cancel")+"</label>');");
            out.println("s = s.concat('<input name=\"cancel\" id=\"contactoCancelar\" type=\"button\" onclick=\"removeCoverDiv(\\'');");
            out.println("s = s.concat(divId);");
            out.println("s = s.concat('\\')\" value=\""+paramRequest.getLocaleString("cancel")+"\" class=\"swb-contact-button\" />');");
            out.println("s = s.concat('</div>');");
            out.println("s = s.concat('</form>');");
            out.println("s = s.concat('</div>');");
            out.println("contactHolder.innerHTML = s;");

            int width, height;
            try {
                width = Integer.parseInt(base.getAttribute("width","360"));
            }catch(NumberFormatException nfe) {
                width = 360;
            }
            try {
                height = Integer.parseInt(base.getAttribute("height","420"));
            }catch(NumberFormatException nfe) {
                height = 420;
            }
            out.println("    var cwidth = "+width+";");
            out.println("    var cheight = "+height+";");

            out.println("    contactHolder.id='s_'+divId;");
            out.println("    contactHolder.style.zIndex=1001;");
            out.println("    contactHolder.style.position='absolute';");
            out.println("    contactHolder.style.top='50%';");
            out.println("    contactHolder.style.left='50%';");
            out.println("    contactHolder.style.marginLeft=-cwidth/2+'px';");
            out.println("    contactHolder.style.marginTop=-cheight/2+'px';");
            out.println("    contactHolder.style.width=cwidth+'px';");
            out.println("    contactHolder.style.height=cheight+'px';");
            out.println("    document.body.appendChild(contactHolder);");
            out.println("  }");

            out.println("</script>");

            if ( base.getAttribute("link")!=null )
                out.println("<a href=\"#\" onclick=\"showContactDialog('cover01','#000000',80)\">"+base.getAttribute("link")+"</a>");
            else if ( base.getAttribute("label")!=null )
                out.println("<input type=\"button\" onclick=\"showContactDialog('cover01','#000000',80)\" value=\""+base.getAttribute("label")+"\" />");
            else
                out.println("<img alt=\""+base.getAttribute("alt")+"\" src=\""+webWorkPath+"/"+base.getAttribute("image")+"\" onclick=\"showContactDialog('cover01','#000000',80)\" />");
        }
        else {
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
                    out.println("<a href=\""+surl+"\" class=\"swb-contact\">"+base.getAttribute("link")+"</a>");
                }else if( base.getAttribute("label")!=null ) {
                    out.println("<form method=\"post\" action=\""+surl+"\" class=\"swb-contact\" >");
                    out.println("  <input type=\"submit\" value=\""+base.getAttribute("label")+"\" class=\"swb-contact-button\" />");
                    out.println("</form>");
                }else if( base.getAttribute("image")!=null ) {
                    out.println("<a href=\""+surl+"\" class=\"swb-contact\">");
                    out.println("  <img src=\""+webWorkPath+"/"+base.getAttribute("image")+"\" alt=\""+base.getAttribute("alt",paramRequest.getLocaleString("msgContact"))+"\" class=\"cntct-stg-img\" />");
                    out.println("</a>");
                }else {
                    out.println("<a href=\""+surl+"\" class=\"\">"+paramRequest.getLocaleString("msgContact")+"</a>");
                }
            }else {
                Document dom =getDom(request, response, paramRequest);
                try {
                    if(dom != null) {
                        out.println(SWBUtils.XML.transformDom(tpl, dom));
                    }
                }catch(TransformerException e) {
                    log.error(e);
                }
                out.println("<script type=\"text/javascript\">");
                out.println("function validateContactFrm(frm) {");
                out.println("   if(!isValidEmail(frm.email.value)) {");
                out.println("      alert('"+paramRequest.getLocaleString("emailInvalid")+"');");
                out.println("      frm.email.focus;");
                out.println("      return false;");
                out.println("   }");
                out.println("   return true;");
                out.println("}");
                out.println("</script>");
            }
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        out.println("<div class=\"swb-contact\"><p class=\"swb-contact-tks\">");
        out.println(paramRequest.getLocaleString("thanks"));
        out.println("</p></div>");
    }


    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException {
        try {
            processEmails(request, response);
            response.setMode(response.Mode_EDIT);
        }catch(SWBResourceException re) {
            log.error("Error in resource Contact, while trying to send the email. ", re);
            throw new SWBResourceException(re.getMessage());
        }catch(Exception e) {
            response.setRenderParameter(_FAIL, _FAIL);
            log.error("Error in resource Contact, while trying to send the email. ", e);
        }
    }

    private boolean isEmpty(String param) {
        boolean empty = true;
        if( param!=null && param.trim().length()>0 )
            empty = false;
        return empty;
    }

    public void doSent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String res = request.getParameter("email");
        if( "success".equalsIgnoreCase(res) ) {
        }else if( "missdata".equalsIgnoreCase(res) ) {
        }else {
        }
    }
}
