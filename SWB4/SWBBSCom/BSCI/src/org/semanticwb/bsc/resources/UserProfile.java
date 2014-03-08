/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.bsc.ContactWork;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author ana.garcias
 */
public class UserProfile extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //super.doView(request, response, paramRequest); //To change body of generated methods, choose Tools | Templates.
        final User user = SWBContext.getSessionUser();
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);
        System.out.println("userId en doView..." + user.getId());
        SemanticObject usrUri = SemanticObject.createSemanticObject(user.getURI());
        
    //SWBFormMgr mgr = new SWBFormMgr(User.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), null);
        SWBFormMgr mgr = new SWBFormMgr(usrUri, null, SWBFormMgr.MODE_EDIT);
        mgr.setType(SWBFormMgr.TYPE_DOJO);
        mgr.setFilterRequired(false);
        String lang = "es";
        mgr.setLang(lang);
        mgr.addButton(SWBFormButton.newSaveButton());
        
        
        if (user != null && user.isSigned()) {
            /*String userName = user.getFullName() == null ? "" : user.getFullName();
            String email = user.getEmail() == null ? "" : user.getEmail();
            String employment = "";
            String twitter = "";
            String skype = "";
            String location = "";
            int lada = 0;
            int phone = 0;
            int ext = 0;*/
            final String pimg;
            //Revisamos si ya tiene datos asociados en Contacto de trabajo
           /* if (ContactWork.ClassMgr.hasContactWork(user.getId(), wsite)) {
                ContactWork cw = ContactWork.ClassMgr.getContactWork(user.getId(), wsite);
                employment = cw.getEmployment() == null ? "" : cw.getEmployment();
                twitter = cw.getTwitter() == null ? "" : cw.getTwitter();
                skype = cw.getSkype() == null ? "" : cw.getSkype();
                lada = cw.getLada();
                phone = cw.getPhone();
                ext = cw.getExt();
                location = cw.getLocation() == null ? "" : cw.getLocation();
            }*/

            out.println("<script type=\"text/javascript\">\n");
            out.println("  dojo.require('dojo.parser');\n");
            out.println("  dojo.require('dijit.layout.ContentPane');\n");
            out.println("  dojo.require('dijit.form.Form');\n");
            out.println("  dojo.require('dijit.form.CheckBox');\n");
            out.println("  dojo.require('dijit.form.ValidationTextBox');\n");
            out.println("  dojo.require('dijit.form.TextBox');\n");
            out.println("</script>\n");
            out.println("");
        
         out.println("<form method=\"post\" id=\"frmUploadPhoto\" action=\" " + url
                    + "\" class=\"swbform\" type=\"dijit.form.Form\" onsubmit=\""
                    + "submitForm('frmUploadPhoto');return false;\">");
            mgr.getFormHiddens();
            out.println("<input type=\"hidden\" name=\"userId\" value=\"" + user.getId()
                    + "\">");

            final String img;
            if (user.getPhoto() == null) {
                img = SWBPortal.getWebWorkPath() + "/models/" + wsite.getId() + "/css/images/user.jpg";

            } else {
                img = SWBPortal.getWebWorkPath() + "/models/" + wsite.getId() + "/" + user.getPhoto();
            }
            System.out.println("Ruta img..." + img);
            
            out.println("<div class=\"foto\"><img src=\"" + img + "\" alt=\"\" />");
            out.println("<p class=\"tercio\"><label for=\"foto\">Cambiar mi foto</label>" + mgr.renderElement(request, User.swb_usrPhoto, SWBFormMgr.MODE_EDIT) + "</p>"
                    + "</div>");
            out.println("<div class=\"user\"> " + user.getFullName() + "</div>");
            out.println("<div>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_employment") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, ContactWork.bsc_employment, SWBFormMgr.MODE_EDIT)+"</p>");
            out.println("</div>");
            out.println("<div class=\"\">");
            out.println("<p class=\"\"><strong>" + paramRequest.getLocaleString("lbl_contact") + "</strong></p>");
            out.println("<p>&nbsp;</p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_mail") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, User.swb_usrEmail, SWBFormMgr.MODE_EDIT)+"</p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_twitter") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, ContactWork.bsc_twitter, SWBFormMgr.MODE_EDIT)+"</p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_skype") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, ContactWork.bsc_skype, SWBFormMgr.MODE_EDIT)+"</p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_lada") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, ContactWork.bsc_lada, SWBFormMgr.MODE_EDIT)+"</p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_phone") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, ContactWork.bsc_phone, SWBFormMgr.MODE_EDIT)+"</p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_ext") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, ContactWork.bsc_ext, SWBFormMgr.MODE_EDIT)+"</p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_location") + "</p>");
            out.println("<p class=\"\">"+ mgr.renderElement(request, ContactWork.bsc_location, SWBFormMgr.MODE_EDIT)+"</p>");
            //temporal valores en duro
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_area") + "</p>");
            out.println("<p class=\"\"><select name=\"area\"><option>DAC</option></select></p>");
            out.println("<p class=\"\">" + paramRequest.getLocaleString("lbl_chief") + "</p>");
            out.println("<p class=\"\"><select name=\"chief\"><option>Javier Solis</option></select></p>");
            out.println("<p><button type=\"dijit.form.Button\" type=\"submit\">"
                    + paramRequest.getLocaleString("lbl_Save") + "</button></p>");
            out.println("</div>");
            out.println("</form>");
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        //super.processAction(request, response); //To change body of generated methods, choose Tools | Templates.
        System.out.println("entra al metodo processAction");
        String action = response.getAction();
        final String userId = request.getParameter("userId");
        System.out.println("userId que recibe ...." + userId);
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        User user = SWBContext.getSessionUser();
        
        SemanticObject usrUri = SemanticObject.createSemanticObject(user.getURI());
        if (SWBResourceURL.Action_ADD.equalsIgnoreCase(action)) {
            System.out.println("entra al action ADD...");
            SWBFormMgr mgr = new SWBFormMgr(usrUri, null, SWBFormMgr.MODE_EDIT);
           // mgr.addProperty(ContactWork.bsc_twitter);
            /*String employment = request.getParameter("employment") == null ? "" : request.getParameter("employment");
            String twitter = request.getParameter("twitter") == null ? "" : request.getParameter("twitter");
            String skype = request.getParameter("skype") == null ? "" : request.getParameter("skype");
            String lada = request.getParameter("lada");
            int lada_ = Integer.parseInt(lada);
            String phone = request.getParameter("phone");
            int phone_ = Integer.parseInt(phone);
            String ext = request.getParameter("ext");
            int ext_ = Integer.parseInt(ext);
            String location = request.getParameter("location") == null ? "" : request.getParameter("location");
            // area y jefe son objetos//
            String area = request.getParameter("area") == null ? "" : request.getParameter("area");
            String chief = request.getParameter("chief") == null ? "" : request.getParameter("chief");
            */
           /* if (ContactWork.ClassMgr.hasContactWork(userId, wsite)) {
                ContactWork cw = ContactWork.ClassMgr.getContactWork(userId, wsite);
                cw.setEmployment(employment);
                cw.setTwitter(twitter);
                cw.setSkype(skype);
                cw.setLada(lada_);
                cw.setPhone(phone_);
                cw.setExt(ext_);
                cw.setLocation(location);
                cw.setArea_(null);
                cw.setChief(null);
            } else {
                ContactWork cw = ContactWork.ClassMgr.createContactWork(userId, wsite);
                cw.setEmployment(employment);
                cw.setTwitter(twitter);
                cw.setSkype(skype);
                cw.setLada(lada_);
                cw.setPhone(phone_);
                cw.setExt(ext_);
                cw.setLocation(location);
                cw.setArea_(null);
                cw.setChief(null);
            }*/
        }
    }
}
