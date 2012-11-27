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
package org.semanticwb.portal.resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.Versionable;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
//import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
//import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
// TODO: Auto-generated Javadoc
//import org.semanticwb.portal.resources.sem.HTMLContent;

/**
 * The Class WebPageOnLineCreate.
 * 
 * @author juan.fernandez
 */
public class WebPageOnLineCreate extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(WebPageOnLineCreate.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        PrintWriter out = response.getWriter();

        WebPage wpParent = paramRequest.getWebPage();



        if (userCanEdit(paramRequest)) {
            SWBResourceURL urladd = paramRequest.getActionUrl();
            urladd.setAction("new");

            out.println("<script type=\"text/javascript\">");
            out.println("  dojo.require(\"dijit.Dialog\");");
            out.println("  dojo.require(\"dijit.form.TextBox\");");
            out.println("  dojo.require(\"dijit.form.ValidationTextBox\");");
            out.println("  dojo.require(\"dijit.form.Button\");");
            out.println("</script>");
            out.println("<a href=\"#\" onclick=\"dijit.byId('tooltipDlg').show();\">" + "Crear página" + "</a>");
            out.println("<div dojoType=\"dijit.Dialog\" style=\"display:none;\" id=\"tooltipDlg\" title=\"Agregar Sección\">");
            out.println("  <form id=\"addtpf\" action=\""+urladd+"\">");
            out.println("    <table>");

            out.println("      <tr>");
            out.println("         <td>");
            out.println("           <label for=\"title\">Título <em>*</em></label>");
            out.println("         </td>");
            out.println("         <td>");
            out.println("           <input id=\"swb_create_title\" name=\"title\" size=\"30\" value=\"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Título.\" invalidMessage=\"Título es requerido.\" onkeyup=\"dojo.byId('swb_create_id').value=replaceChars4Id(dojo.byId('swb_create_title').value);dijit.byId('swb_create_id').validate()\" trim=\"true\" style=\"width:300px;\"/>");
            out.println("        </td>");
            out.println("     </tr>");
            out.println("     <tr>");
            out.println("         <td>");
            out.println("            <label>Identificador <em>*</em></label>");
            out.println("         </td>");
            out.println("         <td>");
            out.println("            <input id=\"swb_create_id\" name=\"id\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject('" + wpParent.getWebSiteId() + "','swb:WebPage',dojo.byId('swb_create_id').value);\" invalidMessage=\"Identificador invalido.\" style=\"width:300px;\" trim=\"true\"/>");
            out.println("    </td></tr>");

            out.println("      <tr>");
            out.println("        <td colspan=\"2\" align=\"center\">");

            out.println("          <button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit\">Agregar</button>");
            out.println("          <button dojoType=\"dijit.form.Button\" onclick=\"dijit.byId('tooltipDlg').hide(); return false;\">Cancelar</button>"); //submitUrl('" + urlb + "',this.domNode); hideDialog();
            out.println("        </td>");
            out.println("      </tr>");
            out.println("    </table>");
            out.println("  </form>");
            out.println("</div>");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String name = request.getParameter("name");
        if (name != null) {
            response.getResourceBase().setData(name, request.getParameter("txt"));
        } else {
            response.getResourceBase().setData(request.getParameter("txt"));
        }
        String action = response.getAction();
        if (action == null) {
            action = "";
        }
        if (action.equals("admin_update")) {
            String editaccess = request.getParameter("editar");
            String createContent = request.getParameter("content");
            if (editaccess != null) {
                getResourceBase().setAttribute("editRole", editaccess);
                if(createContent!=null) getResourceBase().setAttribute("createContent", createContent);
                try {
                    getResourceBase().updateAttributesToDB();
                } catch (Exception e) {
                    log.error("Error al guardar Role/UserGroup para acceso al InlineEdit.", e);
                }

            }
        } else if ("new".equals(action)) {
            log.debug("ProcessAction(new) ");

            WebPage wpage = response.getWebPage();
            WebSite wsite = wpage.getWebSite();

            try {
                WebPage wp = wsite.createWebPage(request.getParameter("id"));
                wp.setParent(wpage);
                wp.setTitle(request.getParameter("title"));
                wp.setActive(Boolean.TRUE);

                String canCreate = getResourceBase().getAttribute("createContent", "0");
                if(canCreate.equals("1"))
                {
                    Resource res = wsite.createResource();
                    res.setResourceType(wsite.getResourceType("HTMLContent"));
                    res.setTitle("Contenido autogenerado");
                    res.setActive(Boolean.FALSE);
                    wp.addResource(res);

//                    SemanticObject so =  res.getResourceData();
//                    GenericObject go =  so.getGenericInstance();
//
//
//                    VersionInfo vi = wsite.createVersionInfo();
//                    vi.setVersionFile("index.html");
//                    vi.setVersionNumber(1);
//                    vi.setVersionComment("Versión inicial");
//
//                    SWBResource swres = (SWBResource) go;
//
//                    Versionable vswres = (Versionable) go;
//                    vswres.setActualVersion(vi);
//                    vswres.setLastVersion(vi);
//
//                    String rutaFS_target_path = SWBPlatform.getWorkPath() + swres.getResourceBase().getWorkPath() + "/1/";
//                    File f = new File(rutaFS_target_path);
//                    if (!f.exists()) {
//                        f.mkdirs();
//                    }
//
//                    File ftmpl = new File(SWBPlatform.getWorkPath() + swres.getResourceBase().getWorkPath() + "/1/index.html");
//                    Writer output = new BufferedWriter(new FileWriter(ftmpl));
//                    try {
//                        output.write("Contenido HTML...");
//                    } finally {
//                        output.close();
//                    }


                }

            } catch (Exception e) {
                throw new SWBResourceException("Error al agregar la nueva página con contenido...", e);
            }

            response.setRenderParameter("dialog", "close");
            response.setMode(SWBActionResponse.Mode_VIEW);
            response.setCallMethod(SWBActionResponse.Call_CONTENT);
            response.setAction("");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
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

        String str_role = base.getAttribute("editRole", "0");
        String str_create = base.getAttribute("createContent", "0");
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
        if (str_role.equals("0")) {
            selected = "selected";
        }
        strRules.append("\n<option value=\"0\" " + selected + " >" + "Ninguno" + "</option>");
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
        out.println("<tr><td align=\"right\" width=\"150\">" + "Editar" + ":</td>");
        out.println("<td><select name=\"editar\">" + strTemp + "</select></td></tr>");
        out.println("<tr><td align=\"right\" width=\"150\">" + "Agregar HTML-Content" + ":</td>");

        String str_checked="";
        if(!str_create.equals("0"))
        {
            str_checked="checked";
        }

        out.println("<td><input type=\"checkbox\" name=\"content\" value=\"1\" "+str_checked+" /></td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn\" >" + "Aceptar" + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    /**
     * User can edit.
     * 
     * @param paramrequest the paramrequest
     * @return true, if successful
     */
    private boolean userCanEdit(SWBParamRequest paramrequest) {
        boolean access = false;
        String str_role = getResourceBase().getAttribute("editRole", "0");
        User user = paramrequest.getUser();
        try {
            if (user != null&&!str_role.equals("0")) {
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject gobj = null;
                try {
                    gobj = ont.getGenericObject(str_role);
                } catch (Exception e) {
                    log.error("Errror InlineEdit.userCanEdit()", e);
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
        }
        catch  (Exception e) {
            access = false;
        }

        if(str_role.equals("0")&&user==null) access=true;
        else if(!str_role.equals("0")&&user==null) access=false;
        else if(str_role.equals("0")&&user!=null) access=true;

    return   access ;
    }
}
