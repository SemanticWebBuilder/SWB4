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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * InlineEdit se encarga de desplegar y administrar un texto est?tico, este texto
 * se agrega en la administraci?n del recurso, acepta tags de html para cambiar su
 * aspecto.
 *
 * InlineEdit is in charge to unfold and to administer a static text, this text
 * it is added in the administration of the resource, accepts tags of HTML to change his
 * aspect.
 *
 * @author Javier Solis
 */
public class InlineEdit extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(InlineEdit.class);

    /**
     * Obtiene la vista del recurso.
     *
     * @param request El servlet container crea un objeto HttpServletRequest y
     * se pasa como argumento al m?todo del servlet.
     * @param response El servlet container crea un objeto HttpServletResponse y
     * se pasa como argumento al m?todo del servlet.
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     * @exception com.infotec.appfw.exception.AFException Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();

        SWBResourceURL url = paramRequest.getActionUrl();
        url.setCallMethod(url.Call_DIRECT);

        String url2 = url.toString();

        String id = paramRequest.getResourceBase().getId();
        String name = paramRequest.getArgument("name");
        String data = null;
        if (name != null) {
            data = paramRequest.getResourceBase().getData(name);
            id = id + name;
            url2 = url2 + "?name=" + name + "&";
        } else {
            data = paramRequest.getResourceBase().getData();
            url2 = url2 + "?";
        }


        PrintWriter out = response.getWriter();
        if (userCanEdit(paramRequest)) {
            if (data == null) {
                data = paramRequest.getLocaleString("click4edit");
            }

            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println("dojo.require(\"dijit.InlineEditBox\");");
            out.println("dojo.require(\"dijit.form.TextBox\");");
            out.println("var iledit_" + base.getId() + ";");
            out.println("dojo.addOnLoad( function() {");
            out.println("    iledit_" + base.getId() + " = new dijit.InlineEditBox({");
            out.println("    id: \"ile_" + base.getId() + "\",");
            out.println("    autoSave: true,");
            out.println("    editor: \"dijit.form.TextBox\",");
            out.println("    onChange: function(value){");
            out.println("           getSyncHtml('" + url2 + "txt='+value);");
            out.println("      }");
            out.println("    }, 'eb_" + base.getId() + "');");
            out.println("  }");
            out.println(");");
            out.println("-->");
            out.println("</script>");
            out.println("<span id=\"eb_" + base.getId() + "\" class=\"swb-ile\">" + data + "</span>");
        } else {
            if (data != null) {
                out.println(data);
            }

        }
    }


    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        String name=request.getParameter("name");
        if(name!=null) {
            //response.getResourceBase().setData(name,request.getParameter("txt"));
            base.setData(name,request.getParameter("txt"));
        }else {
            //response.getResourceBase().setData(request.getParameter("txt"));
            base.setData(request.getParameter("txt"));
        }

        String action = response.getAction();        
        if(response.Action_EDIT.equals(action)) {
            String editaccess = request.getParameter("editar");
            if(editaccess!=null) {
                //getResourceBase().setAttribute("editRole", editaccess);
                base.setAttribute("editRole", editaccess);
                try {
                    getResourceBase().updateAttributesToDB();
                    response.setAction(response.Action_ADD);
                }catch(Exception e) {
                    log.error("Error al guardar Role/UserGroup para acceso al InlineEdit.",e);
                }
            }
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

        String resourceUpdatedMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_msgRecursoActualizado");
        String fieldsetText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_fieldsetMessage");
        String rolesNotFoundText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_rolesNotFoundText");
        String listMessage = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_listMessage");
        String rolesMessage = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_rolesMessage");
        String userGroupsMessage = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_userGroupsMessage");
        String staticText1 = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_staticText1");
        String staticText2 = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_staticText2");
        String buttonText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_buttonText");
        String buttonResetText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_buttonResetText");

        String action  = paramRequest.getAction();
        if(paramRequest.Action_ADD.equals(action)) {
            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println("   alert('" + resourceUpdatedMessage + " " + base.getId() + "');");
            out.println("   location='" + paramRequest.getRenderUrl().setAction(paramRequest.Action_EDIT).toString() + "';");
            out.println("-->");
            out.println("</script>");
        }

        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = wpage.getWebSite();

        String str_role = base.getAttribute("editRole", "0");

        SWBResourceURL urlAction = paramRequest.getActionUrl();
        urlAction.setAction(paramRequest.Action_EDIT);

        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + base.getId() + "/InLineEditRes\" name=\"" + getResourceBase().getId() + "/InLineEditRes\" action=\""+urlAction+"\" method=\"post\" >");
        out.println("<fieldset>");
        out.println("<ul class=\"swbform-ul\">");
        out.println("<legend>");
        out.println(fieldsetText);//Texto a desplegar dentro del fieldset
        out.println("</legend>");
        String strTemp = "<option value=\"-1\">" + rolesNotFoundText + "</option>"; //Texto mostrado en la lista en caso de no existir roles
        Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
        String selected = "";
        if (str_role.equals("0")) {
            selected = "selected";
        }
        StringBuilder strRules = new StringBuilder();
        strRules = strRules.append("\n<option value=\"0\" ");
        strRules = strRules.append(selected);
        strRules = strRules.append(">");
        strRules = strRules.append(listMessage);
        strRules = strRules.append("</option>");
        strRules = strRules.append("\n<optgroup label=\"");
        strRules = strRules.append(rolesMessage);
        strRules = strRules.append("\">");
        while (iRoles.hasNext()) {
            Role oRole = iRoles.next();
            selected = "";
            if (str_role.trim().equals(oRole.getURI())) {
                selected = "selected";
            }
            strRules = strRules.append("\n<option value=\"");
            strRules = strRules.append(oRole.getURI());
            strRules = strRules.append("\"");
            strRules = strRules.append(selected);
            strRules = strRules.append(">");
            strRules = strRules.append(oRole.getDisplayTitle(user.getLanguage()));
            strRules = strRules.append("</option>");
        }
        strRules = strRules.append("\n</optgroup>");
        strRules = strRules.append("\n<optgroup label=\"");
        strRules = strRules.append(userGroupsMessage );
        strRules = strRules.append("\">");
        Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
        while (iugroups.hasNext()) {
            UserGroup oUG = iugroups.next();
            selected = "";
            if (str_role.trim().equals(oUG.getURI())) {
                selected = "selected";
            }
            strRules = strRules.append("\n<option value=\"");
            strRules = strRules.append(oUG.getURI());
            strRules = strRules.append("\"");
            strRules = strRules.append(selected);
            strRules = strRules.append(">");
            strRules = strRules.append(oUG.getDisplayTitle(user.getLanguage()));
            strRules = strRules.append("</option>");
        }
        strRules.append("\n</optgroup>");
        if (strRules.toString().length() > 0) {
            strTemp = strRules.toString();
        }
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"leyenda1\" class=\"swbform-label\"><strong>"+staticText1+"</strong></label>");
        out.print("     </li>");
        
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"editar\" class=\"swbform-label\">"+staticText2+"</label>");
        out.println("       <select name=\"editar\">" + strTemp + "</select>");
        out.print("     </li>");
        out.print("</ul>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn\" >" + buttonText + "</button>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"reset\" name=\"btn\" >" + buttonResetText + "</button>");
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
            if (user != null && !str_role.equals("0")) {
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
        } catch (Exception e) {
            access = false;
        }

        if (str_role.equals("0") && user == null) {
            access = true;
        } else if (!str_role.equals("0") && user == null) {
            access = false;
        } else if (str_role.equals("0") && user != null) {
            access = true;
        }

        return access;
    }
}
