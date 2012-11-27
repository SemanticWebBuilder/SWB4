/*
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
 */
package org.semanticwb.portal.resources.sem.contact;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Country;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

public class ContactDirectory extends org.semanticwb.portal.resources.sem.contact.base.ContactDirectoryBase 
{
    private static Logger log = SWBUtils.getLogger(ContactDirectory.class);

    public ContactDirectory()
    {
    }

   /**
   * Constructs a ContactDirectory with a SemanticObject
   * @param base The SemanticObject with the properties for the ContactDirectory
   */
    public ContactDirectory(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(paramRequest.Action_ADD.equals(mode)) {
            doAdd(request, response, paramRequest);
        }else if("4".equals(mode)) {
            do4(request, response, paramRequest);
        }else if("5".equals(mode)) {
            do5(request, response, paramRequest);
        }else if("6".equals(mode)) {
            do6(request, response, paramRequest);
        }else if("7".equals(mode)) {
            do7(request, response, paramRequest);
        }else if("8".equals(mode)) {
            do8(request, response, paramRequest);
        }else if("9".equals(mode)) {
            do9(request, response, paramRequest);
        }else
            super.processRequest(request,response,paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        Role er = getEditRole();
        User user = paramRequest.getUser();

        Iterator<Contact> contacts = listContacts();
        if(contacts.hasNext()) {
            Contact contact;

            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.print("  function queryRemove(url) {");
            out.print("    if(confirm('¿Eliminar contacto?'))");
            out.print("        window.location.href=url;");
            out.println("  }");
            out.println("-->");
            out.println("</script>");
            out.println("<ul>");
            if(user.hasRole(er)) {
                SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                SWBResourceURL rm = paramRequest.getActionUrl().setAction(paramRequest.Action_REMOVE);
                while(contacts.hasNext()) {
                    contact = contacts.next();
                    url.setParameter("cid", contact.getId());
                    rm.setParameter("cid", contact.getId());
                    out.println("<li>");
                    out.println("<a href=\""+url+"\" title=\""+paramRequest.getLocaleString("lblEdit")+"\">"+paramRequest.getLocaleString("lblEdit")+"</a>");
                    out.println("<a href=\"javascript:queryRemove('"+rm+"')\" title=\""+paramRequest.getLocaleString("lblRemove")+"\">"+paramRequest.getLocaleString("lblRemove")+"</a>");
                    out.println(contact.toString());
                    out.println("</li>");
                }
            }else {
                while(contacts.hasNext()) {
                    contact = contacts.next();
                    out.println("<li>");
                    out.println(contact.toString());
                    out.println("</li>");
                }
            }
            out.println("</ul>");
        }
        
        if(user.hasRole(er)) {
            SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Action_ADD);
            out.println("<div class=\"cd\"><p><a href=\""+url+"\" title=\""+paramRequest.getLocaleString("lblNew")+"\">"+paramRequest.getLocaleString("lblNew")+"</a></p></div>");
        }

    }

    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        Role er = getEditRole();
        User user = paramRequest.getUser();
        if(user.hasRole(er)) {
            HttpSession session = request.getSession(true);
            Contact contact;
            if(session.getAttribute("ctc")==null) {
                WebSite wsite = base.getWebSite();
                contact = Contact.ClassMgr.createContact(wsite);
                contact.init(wsite);
                session.setAttribute("ctc", contact);
            }else
                contact = (Contact)session.getAttribute("ctc");

            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println(" function showNamingDialog(cid, divId, bgcolor, opacity) {");
            out.println("  createCoverDiv(divId, bgcolor, opacity);");
            out.println("  var holder = document.createElement('div');");
            out.println("  var s = new String('');");
            out.println(getNamingForm("cover01", paramRequest));
            out.println("  holder.innerHTML = s;");
            out.println("  var cwidth = 360;");
            out.println("  var cheight = 420;");
            out.println("  holder.id='s_'+divId;");
            out.println("  holder.style.zIndex=1001;");
            out.println("  holder.style.position='absolute';");
            out.println("  holder.style.top='50%';");
            out.println("  holder.style.left='50%';");
            out.println("  holder.style.marginLeft=-cwidth/2+'px';");
            out.println("  holder.style.marginTop=-cheight/2+'px';");
            out.println("  holder.style.width=cwidth+'px';");
            out.println("  holder.style.height=cheight+'px';");
            out.println("  document.body.appendChild(holder);");
            out.println(" }");
            out.println(getPhoningForm("Wrk", contact.getWorkPhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Wrk2", contact.getWorkPhone2(), "cover01", paramRequest));
            out.println(getPhoningForm("Hm", contact.getHomePhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Fx", contact.getFaxPhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Mob", contact.getMobilePhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Evn", contact.getEveningPhone(), "cover01", paramRequest));
            out.println(getAddressingForm("Wrk", contact.getWorkAddress(), "cover01", paramRequest));
            out.println(getAddressingForm("Hm", contact.getHomeAddress(), "cover01", paramRequest));
            out.println(getAddressingForm("Ot", contact.getOtherAddress(), "cover01", paramRequest));
            out.println("-->");
            out.println("</script>");
            SWBResourceURL url = paramRequest.getActionUrl().setCallMethod(paramRequest.Call_DIRECT).setAction(paramRequest.Action_ADD);
            out.println("<form method=\"post\" enctype=\"multipart/form-data\" action=\""+url+"\">");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Clasificar...\" /></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Nombre completo...\" onclick=\"showNamingDialog('"+contact.getId()+"','cover01','#000000',60)\"/><input type=\"text\" value=\"\" readonly=\"readonly\" /></div>");
            SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("4");
            out.println("  <div id=\"orghldr\"><label for=\"org\">Organización:</label><input type=\"text\" name=\"org\" id=\"org\" value=\""+SWBUtils.TEXT.nullValidate(contact.getOrganization(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'orghldr')\"/></div>");
            ajurl.setMode("5");
            out.println("  <div id=\"poshldr\"><label for=\"pos\">Cargo:</label><input type=\"text\" name=\"pos\" id=\"pos\" value=\""+SWBUtils.TEXT.nullValidate(contact.getPosition(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'poshldr')\"/></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><label for=\"pht\">Imagen: </label><input type=\"file\" name=\"photo\" id=\"pht\" value=\"Imagen...\"/></div>");
            out.println(" </div>");
            out.println(" <div>");
            ajurl.setMode("6");
            out.println("  <div id=\"emhldr\"><label for=\"email\">Correo:</label>  <input type=\"text\" name=\"email\" id=\"email\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'emhldr')\"/></div>");
            ajurl.setMode("7");
            out.println("  <div id=\"em2hldr\"><label for=\"email2\">Correo 2:</label><input type=\"text\" name=\"email2\" id=\"email2\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail2(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'em2hldr')\"/></div>");
            ajurl.setMode("8");
            out.println("  <div id=\"em3hldr\"><label for=\"email3\">Correo 3:</label><input type=\"text\" name=\"email3\" id=\"email3\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail3(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'em3hldr')\"/></div>");
            ajurl.setMode("9");
            out.println("  <div id=\"wphldr\"><label for=\"wp\">Página Web</label><input type=\"text\" name=\"wp\" id=\"wp\" value=\""+SWBUtils.TEXT.nullValidate(contact.getWebpage(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'wphldr')\"/></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono de trabajo...\" onclick=\"showPhoningDialogWrk('"+contact.getId()+"','"+contact.getWorkPhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono de trabajo 2...\" onclick=\"showPhoningDialogWrk2('"+contact.getId()+"','"+contact.getWorkPhone2().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono particular...\" onclick=\"showPhoningDialogHm('"+contact.getId()+"','"+contact.getHomePhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono Fax...\" onclick=\"showPhoningDialogFx('"+contact.getId()+"','"+contact.getFaxPhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono móvil...\" onclick=\"showPhoningDialogMob('"+contact.getId()+"','"+contact.getMobilePhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono de recados...\" onclick=\"showPhoningDialogEvn('"+contact.getId()+"','"+contact.getEveningPhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\"\" /></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Dirección de trabajo...\" onclick=\"showAddressingDialogWrk('"+contact.getId()+"','"+contact.getWorkAddress().getId()+"','cover01','#000000',70)\"/><textarea rows=\"4\" cols=\"20\" name=\"wa\" readonly=\"readonly\"></textarea></div>");
            out.println("  <div><input type=\"button\" value=\"Dirección particular...\" onclick=\"showAddressingDialogHm('"+contact.getId()+"','"+contact.getHomeAddress().getId()+"','cover01','#000000',70)\"/><textarea rows=\"4\" cols=\"20\" name=\"ha\" readonly=\"readonly\"></textarea></div>");
            out.println("  <div><input type=\"button\" value=\"Otra dirección...\" onclick=\"showAddressingDialogOt('"+contact.getId()+"','"+contact.getOtherAddress().getId()+"','cover01','#000000',70)\"/><textarea rows=\"4\" cols=\"20\" name=\"oa\" readonly=\"readonly\"></textarea></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Cancelar\"/><input type=\"reset\" value=\"Limpiar\"/><input type=\"submit\" value=\"Guardar\"/></div>");
            out.println(" </div>");
            out.println("</form>");
        }else {
            out.println("no hay permisos");
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Role er = getEditRole();
        User user = paramRequest.getUser();

        Contact contact = getContact(request);
        if(user.hasRole(er) && contact!=null) {
            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println(" function showNamingDialog(cid, divId, bgcolor, opacity) {");
            out.println("  createCoverDiv(divId, bgcolor, opacity);");
            out.println("  var holder = document.createElement('div');");
            out.println("  var s = new String('');");
            out.println(getNamingForm(contact, "cover01", paramRequest));
            out.println("  holder.innerHTML = s;");
            out.println("  var cwidth = 360;");
            out.println("  var cheight = 420;");
            out.println("  holder.id='s_'+divId;");
            out.println("  holder.style.zIndex=1001;");
            out.println("  holder.style.position='absolute';");
            out.println("  holder.style.top='50%';");
            out.println("  holder.style.left='50%';");
            out.println("  holder.style.marginLeft=-cwidth/2+'px';");
            out.println("  holder.style.marginTop=-cheight/2+'px';");
            out.println("  holder.style.width=cwidth+'px';");
            out.println("  holder.style.height=cheight+'px';");
            out.println("  document.body.appendChild(holder);");
            out.println(" }");
            out.println(getPhoningForm("Wrk", contact.getWorkPhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Wrk2", contact.getWorkPhone2(), "cover01", paramRequest));
            out.println(getPhoningForm("Hm", contact.getHomePhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Fx", contact.getFaxPhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Mob", contact.getMobilePhone(), "cover01", paramRequest));
            out.println(getPhoningForm("Evn", contact.getEveningPhone(), "cover01", paramRequest));
            out.println(getAddressingForm("Wrk", contact.getWorkAddress(), "cover01", paramRequest));
            out.println(getAddressingForm("Hm", contact.getHomeAddress(), "cover01", paramRequest));
            out.println(getAddressingForm("Ot", contact.getOtherAddress(), "cover01", paramRequest));
            out.println("-->");
            out.println("</script>");
            SWBResourceURL url = paramRequest.getActionUrl().setCallMethod(paramRequest.Call_DIRECT).setAction(paramRequest.Action_EDIT);
            url.setParameter("cid", contact.getId());
            out.println("<form method=\"post\" enctype=\"multipart/form-data\" action=\""+url+"\">");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Clasificar...\" /></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Nombre completo...\" onclick=\"showNamingDialog('"+contact.getId()+"','cover01','#000000',60)\"/><input type=\"text\" value=\""+contact.getFullName()+"\" /></div>");
            SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("4");
            out.println("  <div id=\"orghldr\"><label for=\"org\">Organización:</label><input type=\"text\" name=\"org\" id=\"org\" value=\""+SWBUtils.TEXT.nullValidate(contact.getOrganization(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'orghldr')\"/></div>");
            ajurl.setMode("5");
            out.println("  <div id=\"poshldr\"><label for=\"pos\">Cargo:</label><input type=\"text\" name=\"pos\" id=\"pos\" value=\""+SWBUtils.TEXT.nullValidate(contact.getPosition(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'poshldr')\"/></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><label for=\"pht\">Imagen: </label><input type=\"file\" name=\"photo\" id=\"pht\" value=\"Imagen...\"/></div>");
            String path = SWBPortal.getWebWorkPath()+contact.getWorkPath();
            if(contact.getPhoto()!=null) {
                out.println("<img src=\""+path+"/"+contact.getPhoto()+"\" alt=\"\"/>");
                out.println("<label for=\"noimg_pht\">Quitar imagen </label><input type=\"checkbox\" name=\"noimg_photo\" id=\"noimg_pht\" value=\"1\"/>");
            }
            out.println(" </div>");
            out.println(" <div>");
            ajurl.setMode("6");
            out.println("  <div id=\"emhldr\"><label for=\"email\">Correo:</label>  <input type=\"text\" name=\"email\" id=\"email\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'emhldr')\"/></div>");
            ajurl.setMode("7");
            out.println("  <div id=\"em2hldr\"><label for=\"email2\">Correo 2:</label><input type=\"text\" name=\"email2\" id=\"email2\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail2(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'em2hldr')\"/></div>");
            ajurl.setMode("8");
            out.println("  <div id=\"em3hldr\"><label for=\"email3\">Correo 3:</label><input type=\"text\" name=\"email3\" id=\"email3\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail3(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'em3hldr')\"/></div>");
            ajurl.setMode("9");
            out.println("  <div id=\"wphldr\"><label for=\"wp\">Página Web</label><input type=\"text\" name=\"wp\" id=\"wp\" value=\""+SWBUtils.TEXT.nullValidate(contact.getWebpage(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'wphldr')\"/></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono de trabajo...\" onclick=\"showPhoningDialogWrk('"+contact.getId()+"','"+contact.getWorkPhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\""+(contact.getWorkPhone().getLocalNumber()==0?"":contact.getWorkPhone().getLocalNumber())+"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono de trabajo 2...\" onclick=\"showPhoningDialogWrk2('"+contact.getId()+"','"+contact.getWorkPhone2().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\""+(contact.getWorkPhone2().getLocalNumber()==0?"":contact.getWorkPhone2().getLocalNumber())+"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono particular...\" onclick=\"showPhoningDialogHm('"+contact.getId()+"','"+contact.getHomePhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\""+(contact.getHomePhone().getLocalNumber()==0?"":contact.getHomePhone().getLocalNumber())+"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono Fax...\" onclick=\"showPhoningDialogFx('"+contact.getId()+"','"+contact.getFaxPhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\""+(contact.getFaxPhone().getLocalNumber()==0?"":contact.getFaxPhone().getLocalNumber())+"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono móvil...\" onclick=\"showPhoningDialogMob('"+contact.getId()+"','"+contact.getMobilePhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\""+(contact.getMobilePhone().getLocalNumber()==0?"":contact.getMobilePhone().getLocalNumber())+"\" /></div>");
            out.println("  <div><input type=\"button\" value=\"Teléfono de recados...\" onclick=\"showPhoningDialogEvn('"+contact.getId()+"','"+contact.getEveningPhone().getId()+"','cover01','#000000',70)\"/><input type=\"text\" value=\""+(contact.getEveningPhone().getLocalNumber()==0?"":contact.getEveningPhone().getLocalNumber())+"\" /></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Dirección de trabajo...\" onclick=\"showAddressingDialogWrk('"+contact.getId()+"','"+contact.getWorkAddress().getId()+"','cover01','#000000',70)\"/><textarea rows=\"4\" cols=\"20\" name=\"wa\" readonly=\"readonly\">"+contact.getWorkAddress().toString(user.getLanguage()).replaceAll(";","\n")+"</textarea></div>");
            out.println("  <div><input type=\"button\" value=\"Dirección particular...\" onclick=\"showAddressingDialogHm('"+contact.getId()+"','"+contact.getHomeAddress().getId()+"','cover01','#000000',70)\"/><textarea rows=\"4\" cols=\"20\" name=\"ha\" readonly=\"readonly\">"+contact.getHomeAddress().toString(user.getLanguage()).replaceAll(";","\n")+"</textarea></div>");
            out.println("  <div><input type=\"button\" value=\"Otra dirección...\" onclick=\"showAddressingDialogOt('"+contact.getId()+"','"+contact.getOtherAddress().getId()+"','cover01','#000000',70)\"/><textarea rows=\"4\" cols=\"20\" name=\"oa\" readonly=\"readonly\">"+contact.getOtherAddress().toString(user.getLanguage()).replaceAll(";","\n")+"</textarea></div>");
            out.println(" </div>");
            out.println(" <div>");
            out.println("  <div><input type=\"button\" value=\"Cancelar\"/><input type=\"reset\" value=\"Limpiar\"/><input type=\"submit\" value=\"Guardar y Salir\"/></div>");
            out.println(" </div>");
            out.println("</form>");
        }else {
            out.println("Contacto no disponible");
        }
    }
    
    private String getCategorizationForm(Contact contact) throws SWBResourceException {
        StringBuilder frm = new StringBuilder();
        return frm.toString();
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();

        User user = response.getUser();
        HttpSession session = request.getSession(true);
        Role er = getEditRole();

        String action = response.getAction();
        if(user.hasRole(er) && response.Action_ADD.equals(action)) {
            Contact contact = (Contact)session.getAttribute("ctc");
            if(contact!=null) {
                try {
                    add(contact, request, response);
                    addContact(contact);
                    session.removeAttribute("ctc");
                    response.setRenderParameter("cid", contact.getId());
                    response.setMode(response.Mode_EDIT);
                    response.setCallMethod(response.Call_CONTENT);
                }catch(Exception e){
                    log.error(e);
                }
            }
        }else if(user.hasRole(er) && response.Action_EDIT.equals(action)) {
            String id = request.getParameter("cid");
            Contact contact = Contact.ClassMgr.getContact(id, wsite);
            if(contact!=null) {
                try {
                    add(contact, request, response);
                    response.setRenderParameter("cid", id);
                    if(!hasContact(contact)) {
                        addContact(contact);
                        session.removeAttribute("ctc");
                    }
                }catch(Exception e){
                    log.error(e);
                }
            }
            response.setMode(response.Mode_VIEW);
            response.setCallMethod(response.Call_CONTENT);
        }else if(user.hasRole(er) && response.Action_REMOVE.equals(action)) {
            Contact contact = Contact.ClassMgr.getContact(request.getParameter("cid"), wsite);
            if(contact!=null)
                contact.remove();
        }else if(user.hasRole(er) && "1".equals(action)) {
            Contact contact = getContact(request);
            if(contact!=null) {
                response.setRenderParameter("cid", contact.getId());
                response.setMode(response.Mode_EDIT);
                response.setCallMethod(response.Call_CONTENT);
                contact.setTreatment(SWBUtils.TEXT.nullValidate(request.getParameter("t"),""));
                contact.setFirstName(SWBUtils.TEXT.nullValidate(request.getParameter("fn"),""));
                contact.setLastName(SWBUtils.TEXT.nullValidate(request.getParameter("ln"),""));
                contact.setSecondLastName(SWBUtils.TEXT.nullValidate(request.getParameter("sln"),""));
            }
        }else if(user.hasRole(er) && "2".equals(action)) {
            Phone phone = getPhone(request);
            if(phone!=null) {
                response.setRenderParameter("cid", request.getParameter("cid"));
                response.setMode(response.Mode_EDIT);
                response.setCallMethod(response.Call_CONTENT);
                phone.setRegion(Country.ClassMgr.getCountry(request.getParameter("country"), wsite));
                try {
                    phone.setAreaCode(Integer.parseInt(SWBUtils.TEXT.nullValidate(request.getParameter("area"),"")));
                }catch(NumberFormatException nfe) {}
                try {
                    phone.setLocalNumber(Integer.parseInt(SWBUtils.TEXT.nullValidate(request.getParameter("number"),"")));
                }catch(NumberFormatException nfe) {}
                try {
                    phone.setExt(Integer.parseInt(SWBUtils.TEXT.nullValidate(request.getParameter("x"),"")));
                }catch(NumberFormatException nfe) {}
            }
        }else if(user.hasRole(er) && "3".equals(action)) {
            Address address = getAddress(request);
            if(address!=null) {
                response.setRenderParameter("cid", request.getParameter("cid"));
                response.setMode(response.Mode_EDIT);
                response.setCallMethod(response.Call_CONTENT);
                address.setStreet(SWBUtils.TEXT.nullValidate(request.getParameter("str"), ""));
                address.setSuburb(SWBUtils.TEXT.nullValidate(request.getParameter("sb"), ""));
                address.setCity(SWBUtils.TEXT.nullValidate(request.getParameter("ct"), ""));
                address.setState(SWBUtils.TEXT.nullValidate(request.getParameter("st"), ""));
                address.setCp(SWBUtils.TEXT.nullValidate(request.getParameter("cp"), ""));
                address.setCountry(Country.ClassMgr.getCountry(request.getParameter("country"), wsite));
                address.setIsToCheck(request.getParameter("ck")==null?false:true);
                address.setIsToMail(request.getParameter("ml")==null?false:true);
            }
        }
    }

    private void add(Contact contact, HttpServletRequest request, SWBActionResponse response) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            HashMap<String, String> params = new HashMap<String,String>();

            String path = SWBPortal.getWorkPath()+contact.getWorkPath();
            File file = new File(path);
            if(!file.exists()) {
                file.mkdirs();
            }
            Iterator<FileItem> iter = SWBUtils.IO.fileUpload(request, null);
            while(iter.hasNext()) {
                FileItem item = iter.next();
                if(item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString().trim();
                    params.put(name, value);
                }else {
                    String filename = SWBUtils.TEXT.replaceSpecialCharacters(item.getName(),'.',true);
                    if( filename.isEmpty() )
                        //throw new Exception(item.getFieldName()+" es requerido");
                        continue;
                    if(!filename.endsWith(".jpg")&&!filename.endsWith(".jpeg")&&!filename.endsWith(".gif")&&!filename.endsWith(".png"))
                        //throw new Exception(item.getFieldName()+" formato incorrecto");
                        continue;
                    file = new File(path+"/"+filename);
                    item.write(file);
                    params.put(item.getFieldName(), filename);
                }
            }
            contact.edit(params);
        }
    }

    private String getNamingForm(String divId, SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL url = paramRequest.getActionUrl().setCallMethod(paramRequest.Call_DIRECT).setAction("1");
        StringBuilder frm = new StringBuilder();

        frm.append("s = s.concat('<form method=\"post\" action=\""+url+"\">');");

        frm.append("s = s.concat('<input type=\"hidden\" name=\"cid\" value=\"'+cid+'\"/>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblTreatment")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"t\" id=\"t\" class=\"swb-contact-field\" value=\"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblFirstName")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"fn\" id=\"fn\" class=\"swb-contact-field\" value=\"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblLastName")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"ln\" id=\"ln\" class=\"swb-contact-field\" value=\"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblSecondLastName")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"sln\" id=\"sln\" class=\"swb-contact-field\" value=\"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<div class=\"swb-contact-cmd\" >');");
        frm.append("s = s.concat('<input type=\"button\" onclick=\"removeCoverDiv(\\'');");
        frm.append("s = s.concat('"+divId+"');");
        frm.append("s = s.concat('\\')\" value=\""+paramRequest.getLocaleString("lblCancel")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('<input type=\"submit\" value=\""+paramRequest.getLocaleString("lblOk")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('</div>');");
        frm.append("s = s.concat('</form>');");
        return frm.toString();
    }

    private String getNamingForm(Contact contact, String divId, SWBParamRequest paramRequest) throws SWBResourceException {
        SWBResourceURL url = paramRequest.getActionUrl().setCallMethod(paramRequest.Call_DIRECT).setAction("1");
        StringBuilder frm = new StringBuilder();

        frm.append("s = s.concat('<form method=\"post\" action=\""+url+"\">');");

        frm.append("s = s.concat('<input type=\"hidden\" name=\"cid\" value=\""+contact.getId()+"\"/>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblTreatment")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"t\" id=\"t\" class=\"swb-contact-field\" value=\""+(contact.getTreatment()==null?"":contact.getTreatment())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblFirstName")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"fn\" id=\"fn\" class=\"swb-contact-field\" value=\""+(contact.getFirstName()==null?"":contact.getFirstName())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblLastName")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"ln\" id=\"ln\" class=\"swb-contact-field\" value=\""+(contact.getLastName()==null?"":contact.getLastName())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblSecondLastName")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"sln\" id=\"sln\" class=\"swb-contact-field\" value=\""+(contact.getSecondLastName()==null?"":contact.getSecondLastName())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<div class=\"swb-contact-cmd\" >');");
        frm.append("s = s.concat('<input type=\"button\" onclick=\"removeCoverDiv(\\'');");
        frm.append("s = s.concat('"+divId+"');");
        frm.append("s = s.concat('\\')\" value=\""+paramRequest.getLocaleString("lblCancel")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('<input type=\"submit\" value=\""+paramRequest.getLocaleString("lblOk")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('</div>');");
        frm.append("s = s.concat('</form>');");
        return frm.toString();
    }

    private String getPhoningForm(String name, Phone phone, String divId, SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        String lang = paramRequest.getUser().getLanguage();

        SWBResourceURL url = paramRequest.getActionUrl().setCallMethod(paramRequest.Call_DIRECT).setAction("2");

        StringBuilder frm = new StringBuilder();
        frm.append(" function showPhoningDialog"+name+"(cid, pid, divId, bgcolor, opacity) {");
        frm.append("  createCoverDiv(divId, bgcolor, opacity);");
        frm.append("  var holder = document.createElement('div');");
        frm.append("  var s = new String('');");
        frm.append("s = s.concat('<form method=\"post\" action=\""+url+"\">');");

        frm.append("s = s.concat('<input type=\"hidden\" name=\"cid\" value=\"'+cid+'\"/>');");
        frm.append("s = s.concat('<input type=\"hidden\" name=\"pid\" value=\""+phone.getId()+"\"/>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"country\">"+paramRequest.getLocaleString("lblCountry")+"</label>');");
        frm.append("s = s.concat('<select name=\"country\" id=\"country\" class=\"swb-contact-field\" />');");
        Iterator<Country> countries = Country.ClassMgr.listCountries(wsite);
        Country country;
        while(countries.hasNext()) {
            country = countries.next();
            if(country.equals(phone.getRegion()))
                frm.append("s = s.concat('<option value=\""+country.getId()+"\" selected=\"selected\">"+country.getDisplayTitle(lang)+"</option>');");
            else
                frm.append("s = s.concat('<option value=\""+country.getId()+"\">"+country.getDisplayTitle(lang)+"</option>');");
        }
        frm.append("s = s.concat('</select>');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"area\">"+paramRequest.getLocaleString("lblAreaCode")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"area\" id=\"area\" class=\"swb-contact-field\" value=\""+(phone.getAreaCode()==0?"":phone.getAreaCode())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"number\">"+paramRequest.getLocaleString("lblLocalNumber")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"number\" id=\"number\" class=\"swb-contact-field\" value=\""+(phone.getLocalNumber()==0?"":phone.getLocalNumber())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"x\">"+paramRequest.getLocaleString("lblExtension")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"x\" id=\"x\" class=\"swb-contact-field\" value=\""+(phone.getExt()==0?"":phone.getExt())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<div class=\"swb-contact-cmd\" >');");
        frm.append("s = s.concat('<input type=\"button\" onclick=\"removeCoverDiv(\\'');");
        frm.append("s = s.concat('"+divId+"');");
        frm.append("s = s.concat('\\')\" value=\""+paramRequest.getLocaleString("lblCancel")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('<input type=\"submit\" value=\""+paramRequest.getLocaleString("lblOk")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('</div>');");
        frm.append("s = s.concat('</form>');");
        frm.append("  holder.innerHTML = s;");
        frm.append("  var cwidth = 360;");
        frm.append("  var cheight = 420;");
        frm.append("  holder.id='s_'+divId;");
        frm.append("  holder.style.zIndex=1001;");
        frm.append("  holder.style.position='absolute';");
        frm.append("  holder.style.top='50%';");
        frm.append("  holder.style.left='50%';");
        frm.append("  holder.style.marginLeft=-cwidth/2+'px';");
        frm.append("  holder.style.marginTop=-cheight/2+'px';");
        frm.append("  holder.style.width=cwidth+'px';");
        frm.append("  holder.style.height=cheight+'px';");
        frm.append("  document.body.appendChild(holder);");
        frm.append(" }");
        return frm.toString();
    }

    private String getAddressingForm(String name, Address address, String divId, SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        String lang = paramRequest.getUser().getLanguage();

        SWBResourceURL url = paramRequest.getActionUrl().setCallMethod(paramRequest.Call_DIRECT).setAction("3");

        StringBuilder frm = new StringBuilder();
        frm.append(" function showAddressingDialog"+name+"(cid, did, divId, bgcolor, opacity) {");
        frm.append("  createCoverDiv(divId, bgcolor, opacity);");
        frm.append("  var holder = document.createElement('div');");
        frm.append("  var s = new String('');");
        frm.append("s = s.concat('<form method=\"post\" action=\""+url+"\">');");

        frm.append("s = s.concat('<input type=\"hidden\" name=\"cid\" value=\"'+cid+'\"/>');");
        frm.append("s = s.concat('<input type=\"hidden\" name=\"did\" value=\""+address.getId()+"\"/>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblStreet")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"str\" id=\"str\" class=\"swb-contact-field\" value=\""+(address.getStreet()==null?"":address.getStreet())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblSuburb")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"sb\" id=\"sb\" class=\"swb-contact-field\" value=\""+(address.getSuburb()==null?"":address.getSuburb())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblCity")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"ct\" id=\"ct\" class=\"swb-contact-field\" value=\""+(address.getCity()==null?"":address.getCity())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblState")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"st\" id=\"st\" class=\"swb-contact-field\" value=\""+(address.getState()==null?"":address.getState())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblZipCode")+"</label>');");
        frm.append("s = s.concat('<input type=\"text\" name=\"cp\" id=\"cp\" class=\"swb-contact-field\" value=\""+(address.getCp()==null?"":address.getCp())+"\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblCountry")+"</label>');");
        frm.append("s = s.concat('<select name=\"country\" id=\"country\" class=\"swb-contact-field\" />');");
        Iterator<Country> countries = Country.ClassMgr.listCountries(wsite);
        Country country;
        while(countries.hasNext()) {
            country = countries.next();
            if(country.equals(address.getCountry()))
                frm.append("s = s.concat('<option value=\""+country.getId()+"\" selected=\"selected\">"+country.getDisplayTitle(lang)+"</option>');");
            else
                frm.append("s = s.concat('<option value=\""+country.getId()+"\">"+country.getDisplayTitle(lang)+"</option>');");
        }
        frm.append("s = s.concat('</select>');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblToCheck")+"</label>');");
        if(address.isIsToCheck())
            frm.append("s = s.concat('<input type=\"checkbox\" name=\"ck\" id=\"ck\" class=\"swb-contact-field\" value=\"1\" checked=\"checked\"/>');");
        else
            frm.append("s = s.concat('<input type=\"checkbox\" name=\"ck\" id=\"ck\" class=\"swb-contact-field\" value=\"1\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<p>');");
        frm.append("s = s.concat('<label for=\"\">"+paramRequest.getLocaleString("lblToMail")+"</label>');");
        if(address.isIsToMail())
            frm.append("s = s.concat('<input type=\"checkbox\" name=\"ml\" id=\"ml\" class=\"swb-contact-field\" value=\"1\" checked=\"checked\"/>');");
        else
            frm.append("s = s.concat('<input type=\"checkbox\" name=\"ml\" id=\"ml\" class=\"swb-contact-field\" value=\"1\" />');");
        frm.append("s = s.concat('</p>');");

        frm.append("s = s.concat('<div class=\"swb-contact-cmd\" >');");
        frm.append("s = s.concat('<input type=\"button\" onclick=\"removeCoverDiv(\\'');");
        frm.append("s = s.concat('"+divId+"');");
        frm.append("s = s.concat('\\')\" value=\""+paramRequest.getLocaleString("lblCancel")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('<input type=\"submit\" value=\""+paramRequest.getLocaleString("lblOk")+"\" class=\"swb-contact-button\" />');");
        frm.append("s = s.concat('</div>');");
        frm.append("s = s.concat('</form>');");
        frm.append("  holder.innerHTML = s;");
        frm.append("  var cwidth = 360;");
        frm.append("  var cheight = 420;");
        frm.append("  holder.id='s_'+divId;");
        frm.append("  holder.style.zIndex=1001;");
        frm.append("  holder.style.position='absolute';");
        frm.append("  holder.style.top='50%';");
        frm.append("  holder.style.left='50%';");
        frm.append("  holder.style.marginLeft=-cwidth/2+'px';");
        frm.append("  holder.style.marginTop=-cheight/2+'px';");
        frm.append("  holder.style.width=cwidth+'px';");
        frm.append("  holder.style.height=cheight+'px';");
        frm.append("  document.body.appendChild(holder);");
        frm.append(" }");
        return frm.toString();
    }

    public Contact getContact(HttpServletRequest request) {
        Contact contact = null;
        String id = request.getParameter("cid");
        if(id!=null)
            contact = Contact.ClassMgr.getContact(id, getResourceBase().getWebSite());
        return contact;
    }

    public Phone getPhone(HttpServletRequest request) {
        Phone phone = null;
        String id = request.getParameter("pid");
        if(id!=null)
            phone = Phone.ClassMgr.getPhone(id, getResourceBase().getWebSite());
        return phone;
    }

    public Address getAddress(HttpServletRequest request) {
        Address address = null;
        String id = request.getParameter("did");
        if(id!=null)
            address = Address.ClassMgr.getAddress(id, getResourceBase().getWebSite());
        return address;
    }

    public void do4(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        User user = paramRequest.getUser();
        Role er = getEditRole();
        if(user.hasRole(er)) {
            Contact contact = getContact(request);
            if(contact!=null) {
                contact.setOrganization(SWBUtils.TEXT.nullValidate(request.getParameter("v"),""));
                PrintWriter out = response.getWriter();
                SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("4");
                out.print("<label for=\"org\">Organización:</label><input type=\"text\" name=\"org\" id=\"org\" value=\""+SWBUtils.TEXT.nullValidate(contact.getOrganization(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'orghldr')\"/>");
            }
        }
    }

    public void do5(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        User user = paramRequest.getUser();
        Role er = getEditRole();
        if(user.hasRole(er)) {
            Contact contact = getContact(request);
            if(contact!=null) {
                contact.setPosition(SWBUtils.TEXT.nullValidate(request.getParameter("v"),""));
                PrintWriter out = response.getWriter();
                SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("5");
                out.print("<label for=\"pos\">Cargo:</label><input type=\"text\" name=\"pos\" id=\"pos\" value=\""+SWBUtils.TEXT.nullValidate(contact.getPosition(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'poshldr')\"/>");
            }
        }
    }

    public void do6(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        User user = paramRequest.getUser();
        Role er = getEditRole();
        if(user.hasRole(er)) {
            Contact contact = getContact(request);
            if(contact!=null) {
                contact.setEmail(SWBUtils.TEXT.nullValidate(request.getParameter("v"),""));
                PrintWriter out = response.getWriter();
                SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("6");
                out.print("<label for=\"email\">Correo:</label><input type=\"text\" name=\"email\" id=\"email\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'emhldr')\"/>");
            }
        }
    }

    public void do7(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        User user = paramRequest.getUser();
        Role er = getEditRole();
        if(user.hasRole(er)) {
            Contact contact = getContact(request);
            if(contact!=null) {
                contact.setEmail2(SWBUtils.TEXT.nullValidate(request.getParameter("v"),""));
                PrintWriter out = response.getWriter();
                SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("7");
                out.print("<label for=\"email2\">Correo 2:</label><input type=\"text\" name=\"email2\" id=\"email2\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail2(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'em2hldr')\"/>");
            }
        }
    }

    public void do8(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        User user = paramRequest.getUser();
        Role er = getEditRole();
        if(user.hasRole(er)) {
            Contact contact = getContact(request);
            if(contact!=null) {
                contact.setEmail3(SWBUtils.TEXT.nullValidate(request.getParameter("v"),""));
                PrintWriter out = response.getWriter();
                SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("8");
                out.print("<label for=\"email3\">Correo 3:</label><input type=\"text\" name=\"email3\" id=\"email3\" value=\""+SWBUtils.TEXT.nullValidate(contact.getEmail3(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'em3hldr')\"/>");
            }
        }
    }

    public void do9(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        User user = paramRequest.getUser();
        Role er = getEditRole();
        if(user.hasRole(er)) {
            Contact contact = getContact(request);
            if(contact!=null) {
                contact.setWebpage(SWBUtils.TEXT.nullValidate(request.getParameter("v"),""));
                PrintWriter out = response.getWriter();
                SWBResourceURL ajurl = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("9");
                out.print("<label for=\"wp\">Página Web:</label><input type=\"text\" name=\"wp\" id=\"wp\" value=\""+SWBUtils.TEXT.nullValidate(contact.getWebpage(),"")+"\" onblur=\"postHtml('"+ajurl+"'+'?cid="+contact.getId()+"&v='+this.value,'wphldr')\"/>");
            }
        }
    }
}
