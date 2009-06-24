/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author Jorge Jiménez
 */
public class CreateUsrRepository extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            User user = paramRequest.getUser();
            PrintWriter out = response.getWriter();
            String action = paramRequest.getAction();
            SWBResourceURL url = paramRequest.getRenderUrl();
            if (action != null && action.trim().equals("step2"))
            {
                String id = request.getParameter("wsid");
                //Utilizara un repositorio exclusivo
                UserRepository newUsrRep = SWBContext.createUserRepository(id + "_usr", "http://user." + id + ".swb#");
                newUsrRep.setTitle("Repositorio de usuarios(" + id + ")", "es");
                newUsrRep.setTitle("Users Repository(" + id + ")", "en");
                newUsrRep.setUndeleteable(true);

                //MAPS74 - Cambiado a semantic prop
                newUsrRep.setAuthMethod("FORM");
                newUsrRep.setLoginContext("swb4TripleStoreModule");
                newUsrRep.setCallBackHandlerClassName("org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
                if (user != null && user.isRegistered()) {
                    newUsrRep.setCreator(user);
                }
                //Envia estatus a pantalla
                out.println("<script type=\"text/javascript\">");
                out.println("hideDialog();");
                out.println("addItemByURI(muserStore, null, '" + newUsrRep.getURI() + "');");
                out.println("showStatus('"+paramRequest.getLocaleLogString("repUsrCreated")+"');");
                out.println("</script>");
             } else { //Forma de entrada(Datos iniciales)
                url.setAction("step2");
                getStep1(out, url, paramRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }

    private void getStep1(PrintWriter out, SWBResourceURL url, SWBParamRequest paramRequest) {
        try {

            out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1');try{document.getElementById('csLoading').style.display='inline';}catch(noe){};return false;\" method=\"post\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleLogString("msgwsTitle")+" <em>*</em>");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wstitle\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Titulo.\" invalidMessage=\"Titulo es requerido.\" onkeyup=\"dojo.byId('swb_create_id').value=replaceChars4Id(this.textbox.value);dijit.byId('swb_create_id').validate()\" trim=\"true\" >");
            out.println("</td>");
            out.append("</tr>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleLogString("msgwsID")+" <em>*</em>");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" id=\"swb_create_id\" name=\"wsid\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject(this.textbox.value);\" invalidMessage=\"Identificador invalido.\" trim=\"true\" >");
            out.println("</td>");
            out.append("</tr>");
            out.println("<td colspan=\"2\" align=\"center\">");
            out.println("<button dojoType='dijit.form.Button' type=\"submit\">"+paramRequest.getLocaleLogString("save")+"</button>");
            out.println("<button dojoType='dijit.form.Button' onclick=\"dijit.byId('swbDialog').hide();\">"+paramRequest.getLocaleLogString("cancel")+"</button>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
        } catch (Exception e) {
            log.debug(e);
        }
    }

}
