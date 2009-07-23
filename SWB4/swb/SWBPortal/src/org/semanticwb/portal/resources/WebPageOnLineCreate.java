/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class WebPageOnLineCreate extends GenericResource {

    private static Logger log = SWBUtils.getLogger(WebPageOnLineCreate.class);

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebPage wpParent= paramRequest.getWebPage();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doEdit");

        Resource base = getResourceBase();
        String action = paramRequest.getAction();
        PrintWriter out = response.getWriter();
        SWBFormMgr fm = null;

        if (action.equals("nWP")) {

            SemanticObject soref = wpParent.getSemanticObject();
            SWBResourceURL urla = paramRequest.getActionUrl();
            urla.setAction("new");
            
            out.println("<form id=\""+base.getId()+"/form\" dojoType=\"dijit.form.Form\" action=\""+urla+"\"  onsubmit=\"submitForm('"+urla+"');return false;\" method=\"post\">");
            out.println("<fieldset>");
            out.println("    <table>");
            out.println("            <tr><td align=\"right\"><label for=\"title\">Título <em>*</em></label></td><td><input id=\"swb_create_title\" name=\"title\" size=\"30\" value=\"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Título.\" invalidMessage=\"Título es requerido.\" onkeyup=\"dojo.byId('swb_create_id').value=replaceChars4Id(dojo.byId('swb_create_title').value);dijit.byId('swb_create_id').validate()\" trim=\"true\" style=\"width:300px;\"/></td></tr>");
            out.println("    <tr><td align=\"right\">");
            out.println("            <label>Identificador <em>*</em></label>");
            out.println("    </td><td>");
            out.println("            <input id=\"swb_create_id\" name=\"id\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject('"+wpParent.getWebSiteId()+"','swb:WebPage',dojo.byId('swb_create_id').value);\" invalidMessage=\"Identificador invalido.\" style=\"width:300px;\" trim=\"true\" lowercase=\"true\"/>");
            out.println("    </td></tr>");
            out.println("    <tr><td align=\"center\" colspan=\"2\">");
            out.println("<button dojoType='dijit.form.Button' type=\"submit\">Guardar</button>");
            out.println("<button dojoType='dijit.form.Button' onclick=\"dijit.byId('swbDialog').hide();\">Cancelar</button>");
            out.println("    </td></tr>");
            out.println("    </table>");
            out.println("</fieldset>");
            out.println("</form>");
            



//            out.println("<div class=\"swbform\">");
//            out.println("<form id=\"" + base.getId() + "/WPCreate\" action=\"" + urla + "\" method=\"post\" onsubmit=\"submitForm('"  + base.getId() + "/WPCreate');return false;\">");
//            out.println("<fieldset>");
//            out.println("<table>");
//            out.println("<tbody>");
//            out.println("<tr>");
//            out.println("<td>");
//            out.println("Título:");
//            out.println("</td>");
//            out.println("<td>");
//            out.println("<input type=\"TEXT\" name=\"wp_title\">");
//            out.println("</td>");
//            out.println("</tr>");
//            out.println("<tr>");
//            out.println("<td>");
//            out.println("Id:");
//            out.println("</td>");
//            out.println("<td>");
//            out.println("<input type=\"TEXT\" name=\"wp_id\">");
//            out.println("</td>");
//            out.println("</tr>");
//            out.println("</tbody>");
//            out.println("</table>");
//            out.println("</filedset>");
//            out.println("<filedset>");
//            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" >Guardar</button>"); //_onclick=\"submitForm('"+id+"/"+idvi+"/"+base.getId()+"/FVIComment');return false;\"
//            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"hideDialog(); return false;\">Cancelar</button>"); //submitUrl('" + urlb + "',this.domNode); hideDialog();
//            out.println("</filedset>");
//            out.println("</form>");
//            out.println("</div>");

        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();

        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            out.println("<script type=\"javascript\">");
            out.println(" hideDialog(); ");
            out.println("</script>");
        }


        if(userCanEdit(paramRequest))
        {
            SWBResourceURL urladd = paramRequest.getRenderUrl();
            urladd.setMode(SWBResourceURL.Mode_EDIT);
            urladd.setCallMethod(SWBResourceURL.Call_DIRECT);
            urladd.setAction("nWP");

            out.println("<a href=\"#\" class=\"menu_verdelight\"  onclick=\"showDialog2('" + urladd + "','Agregar nueva página');return false;\">"+"Crear página"+"</a>"); //<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/nueva_version.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgNewVersion") + "\">
//            out.println("<!-- dialog -->");

//            out.println("<div dojoType=\"dijit.Dialog\" style=\"display:none;\" id=\"swbDialog\" title=\"Agregar\" onFocus=\"hideApplet(true);\" onBlur=\"if(!this.open)hideApplet(false);\" >");
//            out.println("  <div dojoType=\"dojox.layout.ContentPane\" id=\"swbDialogImp\" executeScripts=\"true\">");
//            out.println("    Cargando...");
//            out.println("  </div>");
//            out.println("</div>");


        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String name=request.getParameter("name");
        if(name!=null)
        {
            response.getResourceBase().setData(name,request.getParameter("txt"));
        }else
        {
            response.getResourceBase().setData(request.getParameter("txt"));
        }
        //System.out.println("txt:"+request.getParameter("txt"));
        String action = response.getAction();
        if(action==null) action="";
        if(action.equals("admin_update"))
        {
            String editaccess = request.getParameter("editar");
            if(editaccess!=null)
            {
                getResourceBase().setAttribute("editRole", editaccess);
                try {
                    getResourceBase().updateAttributesToDB();
                } catch (Exception e) {
                    log.error("Error al guardar Role/UserGroup para acceso al InlineEdit.",e);
                }

            }
        }
        else if ("new".equals(action)) {
            log.debug("ProcessAction(new) ");

            WebPage wpage = response.getWebPage();
            WebSite wsite = wpage.getWebSite();

            try {
                WebPage wp = wsite.createWebPage(request.getParameter("id").toLowerCase());
                wp.setParent(wpage);
                wp.setTitle(request.getParameter("title"));
                wp.setActive(Boolean.TRUE);

            } catch (Exception e) {
                throw new SWBResourceException("Error to process form...", e);
            }

            response.setRenderParameter("dialog", "close");
            response.setMode(SWBActionResponse.Mode_VIEW);
            response.setCallMethod(SWBActionResponse.Call_CONTENT);
            response.setAction("");
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = wpage.getWebSite();

        String str_role = base.getAttribute("editRole","0");
        out.println("<div class=\"swbform\">");
        SWBResourceURL urlA = paramRequest.getActionUrl();
        urlA.setAction("admin_update");
        out.println("<form id=\"" + base.getId() + "/InLineEditRes\" name=\"" + getResourceBase().getId() + "/InLineEditRes\" action=\"" + urlA + "\" method=\"post\" >");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println("InlineEdit Resource");
        out.println("</legend>");
        out.println("<table width=\"100%\" border=\"0\" >");
        String strTemp = "<option value=\"-1\">" + "No se encontaron roles" + "</option>";
        Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
        StringBuffer strRules = new StringBuffer("");
        String selected = "";
        if(str_role.equals("0")) selected = "selected";
        strRules.append("\n<option value=\"0\" " + selected +" >" + "Ninguno" + "</option>");
        strRules.append("\n<optgroup label=\"Roles\">");
        while (iRoles.hasNext()) {
            Role oRole = iRoles.next();
            selected = "";
            if (str_role.trim().equals(oRole.getURI())) {
                selected = "selected";
            }
            strRules.append("\n<option value=\"" + oRole.getURI() + "\" " + selected + ">" + oRole.getDisplayTitle(user.getLanguage()) + "</option>");
        }
        strRules.append("\n</optgroup>");
        strRules.append("\n<optgroup label=\"User Groups\">");
        Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
        while (iugroups.hasNext()) {
            UserGroup oUG = iugroups.next();
            selected = "";
            if (str_role.trim().equals(oUG.getURI())) {
                selected = "selected";
            }
            strRules.append("\n<option value=\"" + oUG.getURI() + "\" " + selected + " >" + oUG.getDisplayTitle(user.getLanguage()) + "</option>");
        }
        strRules.append("\n</optgroup>");
        if (strRules.toString().length() > 0) {
            strTemp = strRules.toString();
        }
        out.println("<tr><td colspan=\"2\"><b>" + "Role / UserGroup para edición" + "</b></td></tr>");
        out.println("<tr><td align=\"right\" width=\"150\">" + "Editar"+ ":</td>");
        out.println("<td><select name=\"editar\">" + strTemp + "</select></td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn\" >" + "Aceptar" + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    private boolean userCanEdit(SWBParamRequest paramrequest)
    {
        boolean access = false;
        try{
        User user = paramrequest.getUser();
        String str_role = getResourceBase().getAttribute("editRole","0");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = null;
        try {
            gobj = ont.getGenericObject(str_role);
        } catch (Exception e) {
            log.error("Errror InlineEdit.userCanEdit()",e);
        }

        UserGroup ugrp = null;
        Role urole = null;

        if (!str_role.equals("0")) {
            if (gobj != null) {
                if (gobj instanceof UserGroup) {
                    ugrp = (UserGroup) gobj;
                    if (user.hasUserGroup(ugrp)) {
                        access = true;
                    }
                } else if (gobj instanceof Role) {
                    urole = (Role) gobj;
                    if (user.hasRole(urole)) {
                        access = true;
                    }
                }
            } else {
                access = true;
            }
        } else {
            access = true;
        }
        }
        catch(Exception e)
        {
            access = false;
        }
        return access;
    }
}
