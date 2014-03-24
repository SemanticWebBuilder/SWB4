/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericAdmResource;
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
        PrintWriter out = response.getWriter();
        Resource base = paramRequest.getResourceBase();
        if (base.getAttribute("canChangePassword") != null
                && "true".equals(base.getAttribute("canChangePassword"))) {
            StringBuilder toReturn = new StringBuilder();
            StringBuilder toReturn2 = new StringBuilder();
            SWBResourceURL urlChangePass = paramRequest.getRenderUrl().setMode(Mode_CHANGEPASSWORD);
            urlChangePass.setCallMethod(SWBResourceURL.Call_DIRECT);

            toReturn.append("<script type=\"text/javascript\">\n");
            toReturn.append("  dojo.require('dojo.parser');\n");
            toReturn.append("  dojo.require(\"dijit.Dialog\");\n");
            toReturn.append("  dojo.require('dijit.form.Form');\n");
            toReturn.append("  dojo.require(\"dojox.layout.ContentPane\");\n");
            toReturn.append("  dojo.require('dijit.form.ValidationTextBox');\n");
            toReturn.append("  dojo.require('dijit.form.TextBox');\n");

            toReturn.append("  function checkData() {\n");
            if (request.getParameter("msg") != null) {
                toReturn.append("   alert('");
                toReturn.append(paramRequest.getLocaleString(request.getParameter("msg")));
                toReturn.append("');");
                if (!request.getParameter("msg").equals("msgOkUpdate")) {
                    toReturn.append("showDialog('");
                    toReturn.append(urlChangePass);
                    toReturn.append("', '");
                    toReturn.append(paramRequest.getLocaleString("lblDialogName"));
                    toReturn.append("');\n");
                }
            }
            toReturn.append("   }\n");

            toReturn.append("dojo.addOnLoad( function(){\n");
            toReturn.append("checkData(\"\");}\n");
            toReturn.append(");\n");
            toReturn.append("</script>\n");

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

            out.println(toReturn.toString());
            out.println(toReturn2.toString());
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
        if (Mode_CHANGEPASSWORD.equals(mode)) {
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
        User user = SWBContext.getSessionUser();

        //SemanticObject usrUri = SemanticObject.createSemanticObject(user.getURI());
        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
          //  SWBFormMgr mgr = new SWBFormMgr(usrUri, null, SWBFormMgr.MODE_EDIT);
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
}
