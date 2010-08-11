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
 * InlineTextArea se encarga de desplegar y administrar un texto est�tico, este texto
 * se agrega en la administraci�n del recurso, acepta tags de html para cambiar su
 * aspecto.
 *
 * InlineTextArea is in charge to unfold and to administer a static text, this text
 * it is added in the administration of the resource, accepts tags of HTML to change his
 * aspect.
 *
 * @author Carlos Ramos Inchaustegui
 */
public class InlineTextArea extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(Banner.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        try {
            SWBResourceURL url=paramRequest.getActionUrl();
            
            if(userCanEdit(paramRequest)) {
                out.println("<script type=\"text/javascript\">");
                out.println("<!--");
                out.println("dojo.require(\"dijit.InlineEditBox\");");
                out.println("dojo.require(\"dijit.form.Textarea\");");
                out.println("var iledit_"+base.getId()+";");
                out.println("dojo.addOnLoad( function() {");
                out.println("    iledit_"+base.getId()+" = new dijit.InlineEditBox({");
                out.println("    id: \"ilta_"+base.getId()+"\",");
                out.println("    autoSave: false,");
                out.println("    editor: \"dijit.form.Textarea\",");
                out.println("    onChange: function(value){");
                out.println("        postHtml('"+url+"?txt='+value,'ilta_"+base.getId()+"');");
                out.println("      }");
                out.println("    }, 'ta_"+base.getId()+"');");
                out.println("  }");
                out.println(");");
                out.println("-->");
                out.println("</script>");
            }
            out.println("<div id=\"ta_"+base.getId()+"\" class=\"swb-ilta\">");
            out.println(base.getAttribute("text", ""));
            out.println("</div>");

        }catch (Exception e) {
            log.error("Error in resource Banner while bringing HTML", e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = response.getResourceBase();

        base.setAttribute("text", request.getParameter("txt")==null?"":request.getParameter("txt"));

        String action = response.getAction();
        if( action!=null && action.equalsIgnoreCase("update") ) {
            String editaccess = request.getParameter("editar");
            if(editaccess!=null) {
                base.setAttribute("editRole", editaccess);
            }
            response.setRenderParameter("action", "edit");
        }
        try{
            base.updateAttributesToDB();
        }catch(Exception e){
            log.error("Error al guardar atributos del InlineTextArea. ",e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");*/
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        if(action!=null && action.equalsIgnoreCase("edit")) {
            out.println("<script type=\"text/javascript\">");
            out.println("   alert('"+paramRequest.getLocaleString("usrmsg_Inline_doAdmin_msgRecursoActualizado")+" "+base.getId()+"');");
            out.println("   location='"+paramRequest.getRenderUrl().toString()+"';");
            out.println("</script>");
        }

        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = wpage.getWebSite();

        String str_role = base.getAttribute("editRole","0");
        out.println("<div class=\"swbform\">");
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("update");
        out.println("<form id=ilta_\""+base.getId()+"\" name=\"ilta_"+base.getId()+"\" action=\"" + url + "\" method=\"post\" >");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println(paramRequest.getLocaleString("usrmsg_Inline_doAdmin_Data"));
        out.println("</legend>");
        out.println("<table width=\"100%\" border=\"0\" >");

        String strTemp = "<option value=\"-1\">" + "No se encontaron roles" + "</option>";
        Iterator<Role> iRoles = wsite.getUserRepository().listRoles();
        StringBuffer strRules = new StringBuffer("");
        String selected = "";
        if(str_role.equals("0")) {
            selected = "selected=\"selected\"";
        }
        strRules.append("\n<option value=\"0\" " + selected +" >" + "Ninguno" + "</option>");
        strRules.append("\n<optgroup label=\"Roles\">");
        while (iRoles.hasNext()) {
            Role oRole = iRoles.next();
            selected = "";
            if (str_role.trim().equals(oRole.getURI())) {
                selected = "selected=\"selected\"";
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

        out.println("<tr>");
        out.println("<td align=\"right\" width=\"150\">" + paramRequest.getLocaleString("usrmsg_Inline_doAdmin_RollGroup") + ":</td>");
        out.println("<td><select name=\"editar\">" + strTemp + "</select></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td align=\"right\" width=\"150\">" + paramRequest.getLocaleString("usrmsg_Inline_doAdmin_Texto") + ":</td>");
        out.println("<td><textarea name=\"txt\" rows=\"4\" cols=\"50\">" + base.getAttribute("text", "") + "</textarea></td>");
        out.println("</tr>");

        out.println("</table>");
        out.println("</fieldset>");

        out.println("<fieldset>");
        out.println("<legend>");
        out.println(paramRequest.getLocaleString("usrmsg_Inline_doAdmin_Style"));
        out.println("</legend>");

        out.println("<table width=\"100%\" border=\"0\" >");
        out.println("<tr>");
        out.println("<td align=\"right\" width=\"150\">" + paramRequest.getLocaleString("usrmsg_Inline_doAdmin_CssClass") + ":</td>");
        out.println("<td><input type=\"text\" name=\"cssClass\" value=\"" + base.getAttribute("cssClass", "") + "\" size=\"40\" /></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td align=\"right\" width=\"150\">" + paramRequest.getLocaleString("usrmsg_Inline_doAdmin_TextStyle") + ":</td>");
        out.println("<td><input type=\"text\" name=\"style\" value=\"" + base.getAttribute("style", "") + "\" size=\"40\" /></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</fieldset>");

        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("usrmsg_Inline_doAdmin_btnGuardar") + "</button>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"reset\" >" + paramRequest.getLocaleString("usrmsg_Inline_doAdmin_btnReset") + "</button>");
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
