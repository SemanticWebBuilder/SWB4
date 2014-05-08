/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.ContactWork;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.GenericSemResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;

/**
 *
 * @author ana.garcias
 */
public class UserProfile extends GenericAdmResource {

    private final String Mode_CHANGEPASSWORD = "changePassword";
    private final String Action_CHANGEPASSWORD = "savePassword";
    private static org.semanticwb.Logger log = SWBUtils.getLogger(GenericSemResource.class);

    /**
     * Genera el despliegue la actualización del perfil de usuario.
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
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        final User user = paramRequest.getUser();
        final String lang = user.getLanguage();
        PrintWriter out = response.getWriter();
        StringBuilder toReturn = new StringBuilder();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        
        if(!user.isSigned()){
             UserProfile.log.error("El usuario no esta logueado."); 
            return;
        }

        String img = "";
        ContactWork cw = ContactWork.ClassMgr.getContactWork(user.getId(), wsite);
        if (cw == null) {
            cw = ContactWork.ClassMgr.createContactWork(user.getId(), wsite);
        }
        if (user.getPhoto() == null) {
            img = SWBPortal.getWebWorkPath() + "/models/" + wsite.getId() + "/css/images/user.jpg";
        } else {
            img = SWBPortal.getWebWorkPath() + "/models/" + user.getUserRepository().getId() + "/swb_User/" + user.getId() + "/" + user.getPhoto();
        }
        //////FormMgr para FOTO/////////////////////////////////////////////////////////////
        SWBFormMgr formMgrPhoto = new SWBFormMgr(user.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        formMgrPhoto.clearProperties();
        formMgrPhoto.addProperty(User.swb_usrPhoto);
        formMgrPhoto.setType(SWBFormMgr.TYPE_DOJO);
        formMgrPhoto.setMode(SWBFormMgr.MODE_EDIT);
        SWBResourceURL urlPhoto = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);


        /////////FormMgr para DATOS DE CONTACTO DE TRABAJO///////////////////////////////////////////////
        SWBFormMgr formMgr = new SWBFormMgr(cw.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        formMgr.clearProperties();
        formMgr.addProperty(ContactWork.bsc_employment);
        formMgr.addProperty(ContactWork.bsc_twitter);
        formMgr.addProperty(ContactWork.bsc_skype);
        formMgr.addProperty(ContactWork.bsc_lada);
        formMgr.addProperty(ContactWork.bsc_phone);
        formMgr.addProperty(ContactWork.bsc_ext);
        formMgr.addProperty(ContactWork.bsc_location);
        formMgr.addProperty(ContactWork.bsc_area_);
        formMgr.addProperty(ContactWork.bsc_chief);
        formMgr.setType(SWBFormMgr.TYPE_DOJO);
        formMgr.setMode(SWBFormMgr.MODE_EDIT);
        SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_EDIT);

        //////////////////////MUESTRA FORM PARA SUBIR FOTO//////////////////////////////////////////
        toReturn.append("<div id=\"frmUser\">");
        toReturn.append("<table height=\"200px\"><tr><td>HOLA</td></tr></table>");
        toReturn.append("<form id=\"formUserPhoto\" class=\"swbform\" action=\"" + urlPhoto + "\" method=\"post\">\n");
        toReturn.append(formMgrPhoto.getFormHiddens());
        toReturn.append("<div id=\"Photo\" class=\"foto\">");
        toReturn.append("<p><img width=\"205px\" height=\"205px\" src=\"" + img + "\" alt=\"\" /></p>");
        Iterator<SemanticProperty> itUser = SWBComparator.sortSortableObject(formMgrPhoto.getProperties().iterator());
        while (itUser.hasNext()) {
            SemanticProperty prop1 = itUser.next();
            toReturn.append("<p>");
            toReturn.append(
                    formMgrPhoto.getFormElement(prop1).renderElement(
                    request, user.getSemanticObject(), prop1,
                    prop1.getName(), "dojo", SWBFormMgr.MODE_EDIT, lang));
            toReturn.append("</p>");
        }
        toReturn.append("<p><button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"enviarPhoto\" >");
        toReturn.append(paramRequest.getLocaleString("lbl_Save"));
        toReturn.append("    <script type=\"dojo/on\" data-dojo-event=\"click\" data-dojo-args=\"evt\">");
        toReturn.append("require([\"dojo/dom\"], function(dom){");
        toReturn.append("    dom.byId(\"formUserPhoto\").submit();");
        toReturn.append("});");
        toReturn.append("</script>");
        toReturn.append("</button></p>");
        toReturn.append("</div>");

        toReturn.append("<div id=\"data\">");
        toReturn.append("<p><strong><font size=\"13\">" + user.getFullName() + "</font></strong></p>");
        boolean canChangePw = Boolean.parseBoolean(base.getAttribute("canChangePassword","false"));
        if (canChangePw) {
            StringBuilder toReturn1 = new StringBuilder();
            StringBuilder toReturn2 = new StringBuilder();
            SWBResourceURL urlChangePass = paramRequest.getRenderUrl().setMode(Mode_CHANGEPASSWORD);
            urlChangePass.setCallMethod(SWBResourceURL.Call_DIRECT);

            toReturn1.append("<script type=\"text/javascript\">\n");
            toReturn1.append("  dojo.require('dojo.parser');\n");
            toReturn1.append("  dojo.require(\"dijit.Dialog\");\n");
            toReturn1.append("  dojo.require('dijit.form.Form');\n");
            toReturn1.append("  dojo.require(\"dojox.layout.ContentPane\");\n");
            toReturn1.append("  dojo.require('dijit.form.ValidationTextBox');\n");
            toReturn1.append("  dojo.require('dijit.form.TextBox');\n");

            toReturn1.append("  function checkData() {\n");
            if (request.getParameter("msg") != null) {
                toReturn1.append("   alert('");
                toReturn1.append(paramRequest.getLocaleString(request.getParameter("msg")));
                toReturn1.append("');");
                if (!request.getParameter("msg").equals("msgOkUpdate")) {
                    toReturn1.append("showDialog('");
                    toReturn1.append(urlChangePass);
                    toReturn1.append("', '");
                    toReturn1.append(paramRequest.getLocaleString("lblDialogName"));
                    toReturn1.append("');\n");
                }
            }
            toReturn1.append("   }\n");

            toReturn1.append("dojo.addOnLoad( function(){\n");
            toReturn1.append("checkData(\"\");}\n");
            toReturn1.append(");\n");
            toReturn1.append("</script>\n");

            toReturn2.append("<div dojoType=\"dijit.Dialog\" class=\"soria\" ");
            toReturn2.append("style=\"display:width:380px;height:170px;\" ");
            toReturn2.append("id=\"swbDialog\" title=\"Agregar\" >\n");
            toReturn2.append("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" ");
            toReturn2.append("  style=\"display:width:380px;height:170px;\" ");
            toReturn2.append("  id=\"swbDialogImp\" executeScripts=\"true\">\n");
            toReturn2.append("    Cargando...\n");
            toReturn2.append("  </div>\n");
            toReturn2.append("</div>\n");

            toReturn2.append("<a href=\"#\" onclick=\"showDialog('");
            toReturn2.append(urlChangePass);
            toReturn2.append("', '");
            toReturn2.append(paramRequest.getLocaleString("lblDialogName"));
            toReturn2.append("')\">\n");
            toReturn2.append(paramRequest.getLocaleString("lblDialogName"));
            toReturn2.append("\n</a>");

            out.println(toReturn1.toString());
            out.println(toReturn2.toString());
        }

        toReturn.append("<p>" + user.getEmail() + "</p>");
        toReturn.append("</div>");
        toReturn.append("</form>\n");
        toReturn.append("</div>");

        //////////////////////////MUESTRA FORM PARA DATOS DE CONTACTO DE TRABAJO///////////////////////////
        toReturn.append("<div id=\"frmEdit\">");
        toReturn.append("<p><strong>" + paramRequest.getLocaleString("lbl_contact") + "</strong></p>");
        toReturn.append("<script type=\"text/javascript\">\n");
        toReturn.append("  dojo.require('dijit.form.ValidationTextBox');\n");
        toReturn.append("  dojo.require('dijit.form.FilteringSelect');\n");
        toReturn.append("</script>\n");
        toReturn.append("<form id=\"formContactWork\" class=\"swbform\" action=\"" + url + "\" method=\"post\" type=\"dijit.form.Form\">\n");
        toReturn.append(formMgr.getFormHiddens());
        toReturn.append("	    <table>\n");
        toReturn.append("        <tbody>\n");
        Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.getProperties().iterator());
        while (it.hasNext()) {
            SemanticProperty prop1 = it.next();
            toReturn.append("            <tr>\n");
            toReturn.append("                <td width=\"200px\" align=\"right\">\n");
            toReturn.append("                    ");
            toReturn.append(formMgr.renderLabel(request, prop1, prop1.getName(), SWBFormMgr.MODE_VIEW));
            toReturn.append("                </td>\n");
            toReturn.append("                <td>\n");
            toReturn.append(
                    formMgr.getFormElement(prop1).renderElement(
                    request, cw.getSemanticObject(), prop1,
                    prop1.getName(), "dojo", SWBFormMgr.MODE_EDIT, lang));
            toReturn.append("                </td>\n");
            toReturn.append("                </tr>\n");
        }
        toReturn.append("<tr>");
        toReturn.append("<td align=\"center\" colspan=\"2\">");
        toReturn.append("          <button dojoType=\"dijit.form.Button\" type=\"button\" name=\"enviar\" >");
        toReturn.append(paramRequest.getLocaleString("lbl_Save"));

        toReturn.append("    <script type=\"dojo/on\" data-dojo-event=\"click\" data-dojo-args=\"evt\">");
        toReturn.append("require([\"dojo/dom\"], function(dom){");
        toReturn.append("if(dom.byId(\"area_\").value!=''){");
        toReturn.append("    dom.byId(\"formContactWork\").submit();");
        toReturn.append("}");
        toReturn.append("else{alert('Debes seleccionar un area');return false;");
        toReturn.append("}");
        toReturn.append("});");
        toReturn.append("</script>");
        toReturn.append("</button>");
        toReturn.append("</td>");
        toReturn.append("</tr>");
        toReturn.append("	    </table>\n");
        toReturn.append("</form>\n");
        toReturn.append("</div>");
        toReturn.append("<div id=\"chiefDIV\"></div>");
        out.println(toReturn.toString());
    }

    public void uploadPhoto(HttpServletRequest request, SemanticObject obj, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final User user = paramRequest.getUser();
        SWBFormMgr mgr = new SWBFormMgr(User.sclass, user.getSemanticObject(), null);
        try {
            mgr.processForm(request);
        } catch (FormValidateException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Determina el modo a ejecutar, en base a los parámetros recibidos en la
     * petici&oacute;n del cliente
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();

        if (paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY) {
            doViewStrategy(request, response, paramRequest);
        } else if (paramRequest.getMode().equalsIgnoreCase("add")) {
            final User user = paramRequest.getUser();
            SemanticObject obj = user.getSemanticObject();
            uploadPhoto(request, obj, response, paramRequest);
        } else if (Mode_CHANGEPASSWORD.equals(mode)) {
            doChangePassword(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Genera el HTML que permite actulizar la contrase&ntilde;a de un usuario
     * registrado, si la configuración del recurso permite hacerlo
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
    public void doChangePassword(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder toReturn = new StringBuilder();
        SWBResourceURLImp url = new SWBResourceURLImp(request, getResourceBase(),
                paramRequest.getWebPage(), SWBResourceURLImp.UrlType_ACTION);
        url.setAction(Action_CHANGEPASSWORD);

        toReturn.append("\n<script language=\"JavaScript\" >");
        toReturn.append("\nfunction jsValidate(form) {");
        toReturn.append("\n var obj = dojo.byId(form);");
        toReturn.append("\n     if(!obj.validate()) {");
        toReturn.append("\n        alert('");
        toReturn.append(paramRequest.getLocaleString("lblCheckData"));
        toReturn.append("');");
        toReturn.append("\n           return false;");
        toReturn.append("\n     }");

        toReturn.append("\n       if(dojo.byId('newPassword').value != ");
        toReturn.append("           dojo.byId('rePassword').value) {");
        toReturn.append("\n           alert('");
        toReturn.append(paramRequest.getLocaleString("msgErrNewPassword"));
        toReturn.append("             ');");
        toReturn.append("\n           dijit.byId('newPassword').attr('value','');");
        toReturn.append("\n           dijit.byId('rePassword').attr('value','');");
        toReturn.append("\n           dijit.byId('newPassword').focus();");
        toReturn.append("\n           return false;");
        toReturn.append("\n       }");
        toReturn.append("\n       if(dojo.byId('newPassword').value.length > 10 ||");
        toReturn.append("         dojo.byId('newPassword').value.length < 5) {");
        toReturn.append("\n           alert('");
        toReturn.append(paramRequest.getLocaleString("lblErrSize"));
        toReturn.append("             ');");
        toReturn.append("\n           dijit.byId('newPassword').attr('value','');");
        toReturn.append("\n           dijit.byId('rePassword').attr('value','');");
        toReturn.append("\n           dijit.byId('newPassword').focus();");
        toReturn.append("\n           return false;");
        toReturn.append("\n       }");
        toReturn.append("\n}");
        toReturn.append("\n</script>");

        toReturn.append("\n<form id=\"");
        toReturn.append(User.swb_User.getClassName());
        toReturn.append("/edit\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"");
        toReturn.append(url);
        toReturn.append("\" method=\"post\" onsubmit=\"return jsValidate(this);\">");
        toReturn.append("\n<table>");
        toReturn.append("\n<tbody>");
        toReturn.append("\n<tr>");
        toReturn.append("\n<td align=\"right\"><label>");
        toReturn.append(paramRequest.getLocaleString("lblCurPassword"));
        toReturn.append("</label></td>");
        toReturn.append("\n<td><input type=\"password\" name=\"curPassword\" id=\"curPassword\" ");
        toReturn.append("size=\"30\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" ");
        toReturn.append("promptMessage=\"");
        toReturn.append(paramRequest.getLocaleString("userCurPassword"));
        toReturn.append("\" ");
        toReturn.append(" invalidMessage=\"");
        toReturn.append(paramRequest.getLocaleString("userCurPassword"));
        toReturn.append("\" isValid=\"if(this.textbox.value == '') ");
        toReturn.append("{return false;} else { return true;}\" ");
        toReturn.append("trim=\"true\" /></td>");
        toReturn.append("\n</tr>");
        toReturn.append("\n<tr>");
        toReturn.append("\n<td align=\"right\"><label>");
        toReturn.append(paramRequest.getLocaleString("lblNewPassword"));
        toReturn.append("</label></td>");
        toReturn.append("\n<td><input type=\"password\" name=\"newPassword\" ");
        toReturn.append("id=\"newPassword\" size=\"30\" dojoType=\"dijit.form.ValidationTextBox\" ");
        toReturn.append("required=\"true\" ");
        toReturn.append("promptMessage=\"");
        toReturn.append(paramRequest.getLocaleString("userNewPassword"));
        toReturn.append("\" invalidMessage=\"");
        toReturn.append(paramRequest.getLocaleString("userNewPassword"));
        toReturn.append("\" isValid=\"if(this.textbox.value == '') {return false;");
        toReturn.append("} else { return true;}\" trim=\"true\" ></td>");
        toReturn.append("\n</tr>");
        toReturn.append("\n<tr>");
        toReturn.append("\n<td align=\"right\"><label>");
        toReturn.append(paramRequest.getLocaleString("lblConfirmPassword"));
        toReturn.append("</label></td>");
        toReturn.append("\n<td><input type=\"password\" name=\"rePassword\" ");
        toReturn.append("id=\"rePassword\" size=\"30\"  dojoType=\"dijit.form.ValidationTextBox\" ");
        toReturn.append("required=\"true\" ");
        toReturn.append("promptMessage=\"");
        toReturn.append(paramRequest.getLocaleString("userRePassword"));
        toReturn.append("\" invalidMessage=\"");
        toReturn.append(paramRequest.getLocaleString("userRePassword"));
        toReturn.append("\" isValid=\"if(this.textbox.value == '') {return false;} ");
        toReturn.append("else { return true;}\" trim=\"true\" ></td>");
        toReturn.append("\n</tr>");
        toReturn.append("\n</tbody>");
        toReturn.append("\n<tbody>");
        toReturn.append("\n<tr>");
        toReturn.append("\n<td align=\"center\" colspan=\"2\">");
        toReturn.append("\n<button dojoType='dijit.form.Button' type=\"submit\">");
        toReturn.append(paramRequest.getLocaleString("lbl_Save"));
        toReturn.append("</button>\n");
        toReturn.append("\n<button dojoType='dijit.form.Button' ");
        toReturn.append("onclick=\"dijit.byId('swbDialog').hide();\">");
        toReturn.append(paramRequest.getLocaleString("lbl_Cancel"));
        toReturn.append("</button>\n");
        toReturn.append("\n</td>");
        toReturn.append("\n</tr>");
        toReturn.append("\n</tbody>");

        toReturn.append("\n</table>");
        toReturn.append("\n</form>");
        out.println(toReturn.toString());
    }

    /**
     * Realiza las operaciones de edici&oacute;n de informaci&oacute;n del
     * perfil de un usuario.
     *
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta
     * con los recursos necesarios para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n
     * problema con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response)
            throws SWBResourceException, IOException {
        String action = response.getAction();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        User user = SWBContext.getSessionUser(wsite.getUserRepository().getId());
        ContactWork cw = ContactWork.ClassMgr.getContactWork(user.getId(), wsite);
        if (cw == null) {
            cw = ContactWork.ClassMgr.createContactWork(user.getId(), wsite);
        }
        //SemanticObject usrUri = SemanticObject.createSemanticObject(user.getURI());
        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
            SWBFormMgr formMgrPhoto = new SWBFormMgr(user.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
            formMgrPhoto.clearProperties();
            formMgrPhoto.addProperty(User.swb_usrPhoto);
            try {
                SemanticObject semob = formMgrPhoto.processForm(request);
            } catch (FormValidateException ex) {
                Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (SWBResourceURL.Action_EDIT.equalsIgnoreCase(action)) {
            SWBFormMgr mgr = new SWBFormMgr(cw.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
            try {
                SemanticObject semob = mgr.processForm(request);
                ContactWork cw2 = (ContactWork) semob.createGenericInstance();
            } catch (FormValidateException ex) {
                Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (Action_CHANGEPASSWORD.equals(action)) {
            String curPassword = request.getParameter("curPassword") == null
                    ? null : request.getParameter("curPassword").trim();
            String newPassword = request.getParameter("newPassword") == null
                    ? null : request.getParameter("newPassword").trim();
            String rePassword = request.getParameter("rePassword") == null
                    ? null : request.getParameter("rePassword").trim();
            if (user.isSigned()) {
                try {
                    String alg = user.getPassword().substring(1, user.
                            getPassword().indexOf("}"));
                    if (!user.getPassword().equals("") && !SWBUtils.CryptoWrapper.
                            comparablePassword(curPassword, alg).
                            equals(user.getPassword())) {
                        response.setRenderParameter("msg", "msgErrCurrentPassword");
                    } else if (newPassword != null && !newPassword.equals("")
                            && rePassword != null && newPassword.equals(rePassword)) {
                        user.setPassword(newPassword);
                        response.setRenderParameter("msg", "msgOkUpdate");
                    } else {
                        response.setRenderParameter("msg", "msgErrNewPassword");
                    }
                } catch (java.security.NoSuchAlgorithmException nse) {
                    response.setRenderParameter("msg", "msgErrUpdate");
                }
            }
        }
    }

    /**
     *  
     * Genera el despliegue de la liga que redireccionar&aacute; al recurso que 
     * muestra la informaci&oacute;n del usuario.
     * 
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con la liga
     * y el icono correspondiente al elemento a exportar.
     * @throws SWBResourceException SWBResourceException Excepti&oacute;n
     * utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    public void doViewStrategy(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = paramRequest.getResourceBase();
        String surl = paramRequest.getWebPage().getUrl();
        Iterator<Resourceable> res = base.listResourceables();
        while (res.hasNext()) {
            Resourceable re = res.next();
            if (re instanceof WebPage) {
                surl = ((WebPage) re).getUrl();
                break;
            }
        }
        String webWorkPath = SWBPlatform.getContextPath() + "/swbadmin/icons/";
        String image = "iconUserProfile.png";
        String alt = paramRequest.getLocaleString("alt");
        out.println("<span class=\"span-toolbar\">");
        out.println("<a href=\"" + surl + "\" class=\"swb-toolbar-stgy\" title=\"image\">");
        out.println("<img src=\"" + webWorkPath + image + "\" alt=\"" + alt + "\" class=\"toolbar-img\" />");
        out.println("</a>");
        out.println("</span>");

        User user = paramRequest.getUser();
        out.println("<span class=\"span-toolbar\">");
        out.println("<a href=\"" + surl + "\" class=\"swb-toolbar-stgy\" title=\"image\">");
        out.println(user.getFullName());
        out.println("</a>");
        out.println("</span>");
    }
}
