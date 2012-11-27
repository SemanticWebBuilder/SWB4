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
 * InlineTextArea se encarga de desplegar y administrar un texto est�tico, este texto
 * se agrega en la administraci�n del recurso, acepta tags de html para cambiar su
 * aspecto.
 *
 * InlineTextArea is in charge to unfold and to administer a static text, this text
 * it is added in the administration of the resource, accepts tags of HTML to change his
 * aspect.
 *
 * @author 
 */
public class InlineTextArea extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(InlineTextArea.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        try {
            SWBResourceURL url = paramRequest.getActionUrl();
            if (userCanEdit(paramRequest)) {
                out.println("<script type=\"text/javascript\">");
                out.println("<!--");
                out.println("dojo.require(\"dijit.InlineEditBox\");");
                out.println("dojo.require(\"dijit.form.Textarea\");");
                out.println("var iledit_" + base.getId() + ";");
                out.println("dojo.addOnLoad( function() {");
                out.println("    iledit_" + base.getId() + " = new dijit.InlineEditBox({");
                out.println("    id: \"ilta_" + base.getId() + "\",");
                out.println("    autoSave: false,");
                out.println("    editor: \"dijit.form.Textarea\",");
                out.println("    onChange: function(value){");
                out.println("        postHtml('" + url + "?txt='+value,'ilta_" + base.getId() + "');");
                out.println("      }");
                out.println("    }, 'ta_" + base.getId() + "');");
                out.println("  }");
                out.println(");");
                out.println("-->");
                out.println("</script>");
            }
            out.println("<div id=\"ta_" + base.getId() + "\" class=\"swb-ilta\">");
            out.println(base.getAttribute("text", ""));
            out.println("</div>");

        } catch (Exception e) {
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
        if(response.Action_EDIT.equals(action)) {
            String editaccess = request.getParameter("editar");
            if(editaccess!=null) {
                base.setAttribute("editRole", editaccess);
            }
            base.setAttribute("cssClass", request.getParameter("cssClass"));
            base.setAttribute("style", request.getParameter("style"));
            response.setAction(response.Action_ADD);
        }
        try {
            base.updateAttributesToDB();
        } catch (Exception e) {
            log.error("Error al guardar atributos del InlineTextArea. ", e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        //SWBResourceURL url = paramRequest.getActionUrl();
        User user = paramRequest.getUser();

        String resourceUpdatedMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_msgRecursoActualizado");
        String legend = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_Data");
        String userGroupMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_RollGroup");
        String textMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_Texto");
        String listMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_ListMessage");
        String formAndStyleMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_Style");
        String cssClassMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_CssClass");
        String divHeader = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_divHeader");
        String styleTextMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_TextStyle");
        String saveButtonText = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_btnGuardar");
        String resetButtonText = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_btnReset");
/*
        String dojo = "";
        dojo +="<script type=\"text/javascript\">";
            //Función que se ejecutará desde el inicio de la carga de la página
        dojo +="\n";dojo +="var tp;";
        dojo +="\n";dojo +="var fieldset;";
        dojo +="\n";dojo +="var leyenda;";
        dojo +="\n";dojo +="var inputType1;";
        dojo +="\n";dojo +="var inputType2;";
        dojo +="\n";dojo +="var fx = function (){";
        dojo +="\n";dojo +="var nodo = dojo.byId(\"holder\");";
        dojo +="\n";dojo +="if (nodo){";
            //Obtenemos la cookie que almacena el estado de la ventana.
        dojo +="\n";dojo +="var statusVentana = dojo.cookie(\"statusVentana\");";
            //Evaluamos si está o no indefinida.
        dojo +="\n";dojo +="if (statusVentana == undefined){";
                //En caso de ser indefinida, definimos nuestra cookie que
                //almacenará el estado/estatus inicial de la ventana con el valor FALSE.
        dojo +="\n";dojo +="    statusVentana = 'false';";
        dojo +="\n";dojo +="dojo.cookie(\"statusVentana\", statusVentana ,  { expires: 1 } );";
                //alert("le asigno false la primera vez");
        dojo +="\n";dojo +="}else{";
                //alert("estatusVentana trae: "+ statusVentana);
        dojo +="\n";dojo +="if(statusVentana=='true'){";
        dojo +="\n";dojo +="    dojo.cookie(\"statusVentana\", 'true',  { expires: 1 } );";
        dojo +="\n";dojo +="    tp = new dijit.TitlePane({";
        dojo +="\n";dojo +="    id : \"div_estilo\",";
        dojo +="\n";dojo +="    title: \"" + divHeader + "\",";
        dojo +="\n";dojo +="    content: getContent(),";
        dojo +="\n";dojo +="    onClick : function(){ evaluaEstatusDiv(this.open) },";
        dojo +="\n";dojo +="    open : true });";
        dojo +="\n";dojo +="}else";
        dojo +="\n";dojo +="if(statusVentana=='false'){";
        dojo +="\n";dojo +="//alert(\"estatusVentana trae: \"+ statusVentana);";
        dojo +="\n";dojo +="    dojo.cookie(\"statusVentana\", 'false',  { expires: 1 } );";
        dojo +="\n";dojo +="    tp = new dijit.TitlePane({";
        dojo +="\n";dojo +="    id : \"div_estilo\",";
        dojo +="\n";dojo +="    title: \"" + divHeader + "\",";
        dojo +="\n";dojo +="    content: getContent(),";
        dojo +="\n";dojo +="    onClick : function(){ evaluaEstatusDiv(this.open) },";
        dojo +="\n";dojo +="    open : false });";
        dojo +="\n";dojo +="}";
        dojo +="\n";dojo +="nodo.appendChild(tp.domNode);";
        dojo +="\n";dojo +="}";
        dojo +="\n";dojo +="}//si nodo";
        dojo +="\n";dojo +="} //function fx";
            //Agregamos nuestra función a la carga de la página
        dojo +="\n";dojo +="dojo.addOnLoad(fx);";

        out.println(dojo);
        dojo ="";

        dojo +="\n";dojo +="var getContent = function(){";
        dojo +="\n";dojo +="    var apertura = \"<fieldset><legend>"+ formAndStyleMessage +"</legend>\";";
        dojo +="\n";dojo +="    var texto = \"<ul class='swbform-ul'><li class='swbform-li'>\";";
        dojo +="\n";dojo +="    texto += \"<label for='cssClass' class='swbform-label'>"+cssClassMessage+"</label><input type='text' id='cssClass' name='cssClass' value='" + base.getAttribute("cssClass", "") +"' size='40'/>\";";
        dojo +="\n";dojo +="    texto += \"</li><br><li class='swbform-li'>\";";
        dojo +="\n";dojo +="    texto += \"<label for='cssClass' class='swbform-label'>"+styleTextMessage+"</label><input type='text' id='style' name='style' value='" + base.getAttribute("style", "") + "' size='40'/>\";";
        dojo +="\n";dojo +="    var cierre = \"</li></ul></fieldset>\";";
        dojo +="\n";dojo +="    return apertura + texto + cierre;";
        dojo +="\n";dojo +="}";

        out.println(dojo);
        dojo="";

        dojo +="\n";dojo+="var getCookie = function() {";
        dojo +="\n";dojo +="return dojo.cookie(\"statusVentana\");";
        dojo +="\n";dojo +="};";
        dojo +="\n";dojo +="var setCookie = function() {";
        dojo +="\n";dojo +="    var s = getCookie();";
        dojo +="\n";dojo +="    dojo.cookie(\"statusVentana\", s ,  { expires: 1 } );";
        dojo +="\n";dojo +="};";
        dojo +="\n";dojo +="var evaluaEstatusDiv = function (valor) {";
        dojo +="\n";dojo +="    dojo.cookie(\"statusVentana\", valor,  { expires: 1 } );";
        dojo +="\n";dojo +="}";
        dojo +="\n";dojo +="</script>";
        out.println(dojo);
*/
        String action = paramRequest.getAction();
        if(paramRequest.Action_ADD.equals(action)) {
            out.println("<script type=\"text/javascript\">");
            out.println("   alert('"+resourceUpdatedMessage+" "+base.getId()+"');");
            out.println("   location='"+paramRequest.getRenderUrl().setAction(paramRequest.Action_EDIT).toString()+"';");
            out.println("</script>");
        }
        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = wpage.getWebSite();

        String str_role = base.getAttribute("editRole", "0");

        SWBResourceURL urlAction = paramRequest.getActionUrl();
        urlAction.setAction(paramRequest.Action_EDIT);

        out.println("<div class=\"swbform\">");
        out.println("<form id=ilta_\""+base.getId()+"\" name=\"ilta_"+base.getId()+"\" action=\""+urlAction+"\" method=\"post\" >");
        out.println("<fieldset><legend>"+ legend+ "</legend>");

        String strTemp = "<option value=\"-1\">" + "No se encontaron roles" + "</option>";
        Iterator<Role> iRoles = wsite.getUserRepository().listRoles();
        StringBuilder strRules = new StringBuilder("");
        String selected = "";
        if (str_role.equals("0")) {
            selected = "selected=\"selected\"";
        }
        strRules = strRules.append("\n<option value=\"0\" ");
        strRules = strRules.append(selected);
        strRules = strRules.append(">");
        strRules = strRules.append(listMessage);
        strRules = strRules.append("</option>");
        strRules.append("\n<optgroup label=\"Roles\">");
        while (iRoles.hasNext()) {
            Role oRole = iRoles.next();
            selected = "";
            if (str_role.trim().equals(oRole.getURI())) {
                selected = "selected=\"selected\"";
            }
            strRules = strRules.append("\n<option value=\"");
            strRules = strRules.append(oRole.getURI());
            strRules = strRules.append("\"");
            strRules = strRules.append(selected);
            strRules = strRules.append(">");
            strRules = strRules.append(oRole.getDisplayTitle(user.getLanguage()));
            strRules = strRules.append("</option>");
            //strRules.append("\n<option value=\"" + oRole.getURI() + "\" " + selected + ">" + oRole.getDisplayTitle(user.getLanguage()) + "</option>");
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
        out.println("<ul class=\"swbform-ul\">");
        out.println("<li class=\"swbform-li\">");
        out.println("   <label for=\"editar\" class=\"swbform-label\">"+userGroupMessage+"</label>");
        out.print("     <select id=\"editar\" name=\"editar\">"+strTemp+"</select>");
        out.println("</li>");
        out.println("<li class=\"swbform-li\">");
        out.println("   <label for=\"txt\" class=\"swbform-label\">"+textMessage+"</label>");
        out.print("     <textarea id=\"txt\"  name=\"txt\" rows=\"4\" cols=\"50\">" + base.getAttribute("text", "") + "</textarea>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</fieldset>");

        out.println("<div class=\"swbform\" dojoType=\"dijit.TitlePane\" id=\"holder\" title=\""+divHeader+"\">");
        out.println("<fieldset>");
        out.println("   <legend>"+formAndStyleMessage+"</legend>");
	out.println("   <ul class=\"swbform-ul\">");
	out.println("       <li class=\"swbform-li\">");
	out.println("           <label for=\"cssClass\" class=\"swbform-label\">" + cssClassMessage + "</label>");
        out.println("           <input type=\"text\" id=\"cssClass\" name=\"cssClass\" value=\""+base.getAttribute("cssClass", "")+"\" size=\"40\"/>");
	out.println("       </li>");
	out.println("       <li class=\"swbform-li\">");
	out.println("           <label for=\"style\" class=\"swbform-label\">"+styleTextMessage+"</label>");
	out.println("           <input type=\"text\" id=\"style\" name=\"style\" value=\""+base.getAttribute("style", "")+"\" size=\"40\"/>");
	out.println("       </li>");
        out.println("   </ul>");
	out.println("</fieldset>");
        out.println("</div>");

        out.println("<fieldset>");
        out.println("<button id=\"botonEnviar\" dojoType=\"dijit.form.Button\"  onClick=\"setCookie();\" type=\"submit\">" + saveButtonText + "</button>");
        out.println("<button id=\"botonReset\" dojoType=\"dijit.form.Button\" type=\"reset\" >" + resetButtonText + "</button>");
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
