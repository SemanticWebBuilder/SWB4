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
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;


import org.semanticwb.portal.admin.resources.StyleInner;

// TODO: Auto-generated Javadoc
/**
 * The Class TestStyler.
 */
public class TestStyler extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(TestStyler.class);
    
    /** The si. */
    private StyleInner si;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        try {
            SWBResourceURL url=paramRequest.getActionUrl();

            if(base.getAttribute("css")!=null) {
                out.println("<script type=\"text/javascript\">");
//                out.println("dojo.require(\"dojox.html.styles\");");
//                out.println("function setStyleSheetByInstance(rules, sufix, title) {");
//                out.println("  rules = rules.split('}');");
//                out.println("  for(i=0; i<rules.length; i++) {");
//                out.println("    rule = rules[i].split('{');");
//                out.println("    if(rule[1])");
//                out.println("      dojox.html.insertCssRule(rule[0]+'_'+sufix, rule[1]);");
//                out.println("  }");
//                out.println("}");
                out.println("setStyleSheetByInstance('"+base.getAttribute("css")+"','"+base.getId()+"');");
                out.println("</script>");
            }

            if(userCanEdit(paramRequest)) {
                out.println("<script type=\"text/javascript\">");
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
                out.println("</script>");
            }
            out.println("<div id=\"ta_"+base.getId()+"\" class=\"ilta_"+base.getId()+"\">");
            out.println(base.getAttribute("text", ""));
            out.println("</div>");
        }catch (Exception e) {
            log.error("Error in resource Banner while bringing HTML", e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        Resource base = getResourceBase();
//        PrintWriter out = response.getWriter();
//
//        String action = request.getParameter("action");
//        if(action!=null) {
//            out.println("<script type=\"text/javascript\">");
//            out.println("   alert('recurso actualizado con identificador "+ base.getId()+"');");
//            out.println("   location='"+paramRequest.getRenderUrl().toString()+"';");
//            out.println("</script>");
//        }
//        
//        User user = paramRequest.getUser();
//
//        WebPage wpage = paramRequest.getWebPage();
//        WebSite wsite = wpage.getWebSite();
//
//        String str_role = base.getAttribute("editRole","0");
//        out.println("<div class=\"swbform\">");
//        SWBResourceURL urlA = paramRequest.getActionUrl();
//        urlA.setAction("update");
//        out.println("<form id=ilta_\""+base.getId()+"\" name=\"ilta_"+base.getId()+"\" action=\"" + urlA + "\" method=\"post\" >");
//        out.println("<fieldset>");
//        out.println("<legend>");
//        out.println("Datos");
//        out.println("</legend>");
//        out.println("<table width=\"100%\" border=\"0\" >");
//
//        String strTemp = "<option value=\"-1\">" + "No se encontaron roles" + "</option>";
//        Iterator<Role> iRoles = wsite.getUserRepository().listRoles();
//        StringBuffer strRules = new StringBuffer("");
//        String selected = "";
//        if(str_role.equals("0")) {
//            selected = "selected=\"selected\"";
//        }
//        strRules.append("\n<option value=\"0\" " + selected +" >" + "Ninguno" + "</option>");
//        strRules.append("\n<optgroup label=\"Roles\">");
//        while (iRoles.hasNext()) {
//            Role oRole = iRoles.next();
//            selected = "";
//            if (str_role.trim().equals(oRole.getURI())) {
//                selected = "selected=\"selected\"";
//            }
//            strRules.append("\n<option value=\"" + oRole.getURI() + "\" " + selected + ">" + oRole.getDisplayTitle(user.getLanguage()) + "</option>");
//        }
//        strRules.append("\n</optgroup>");
//        strRules.append("\n<optgroup label=\"User Groups\">");
//        Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
//        while (iugroups.hasNext()) {
//            UserGroup oUG = iugroups.next();
//            selected = "";
//            if (str_role.trim().equals(oUG.getURI())) {
//                selected = "selected";
//            }
//            strRules.append("\n<option value=\"" + oUG.getURI() + "\" " + selected + " >" + oUG.getDisplayTitle(user.getLanguage()) + "</option>");
//        }
//        strRules.append("\n</optgroup>");
//        if (strRules.toString().length() > 0) {
//            strTemp = strRules.toString();
//        }
//
//        out.println("<tr>");
//        out.println("<td align=\"right\" width=\"150\">Rol o grupo:</td>");
//        out.println("<td><select name=\"editar_"+base.getId()+"\">" + strTemp + "</select></td>");
//        out.println("</tr>");
//
//        out.println("<tr>");
//        out.println("<td align=\"right\" width=\"150\">Texto:</td>");
//        out.println("<td><textarea name=\"txt\" rows=\"4\" cols=\"50\">" + base.getAttribute("text", "") + "</textarea></td>");
//        out.println("</tr>");
//
//        out.println("</table>");
//        out.println("</fieldset>");
//
//        out.println("<fieldset>");
//        out.println("<legend>Estilo</legend>");
//        String cssResPath = "/"+SWBUtils.TEXT.replaceAll(getClass().getName(), ".", "/")+".css";
//        si = new StyleInner(getResourceBase());
//        String script = null;
//        try {
//            script = si.render(paramRequest, cssResPath);
//        }catch(NullPointerException e) {
//            log.error("Tal vez no exite el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
//        }catch(IOException e) {
//            log.error("Error al leer el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
//        }catch(Exception e) {
//            log.error("Error en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
//        }
//        out.println(script);
//        out.println("</fieldset>");
//
//        out.println("<fieldset>");
//        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
//        out.println("<button dojoType=\"dijit.form.Button\" type=\"reset\" >Restablecer</button>");
//        out.println("</fieldset>");
//        out.println("</form>");
//        out.println("</div>");
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
            String editaccess = request.getParameter("editar_"+base.getId());
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
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillStyle")) {
            doEditStyle(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    /**
     * Do edit style.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doEditStyle(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        Resource base = getResourceBase();
//        String stel = request.getParameter("stel");
//        String[] tkns = stel.split("@",3);
//        HashMap tabs = (HashMap)si.getMm(base.getId());
//        if( tabs!=null && tkns[1].length()>0 ) {
//            try {
//                HashMap t = (HashMap)tabs.get(tkns[0]);
//                if(tkns[2].equalsIgnoreCase("empty") || tkns[2].length()==0)
//                    t.remove(tkns[1]);
//                else
//                    t.put(tkns[1], tkns[2]);
//                StringBuilder css = new StringBuilder();
//                Iterator<String> ittabs = tabs.keySet().iterator();
//                while(ittabs.hasNext()) {
//                    String tab = ittabs.next();
//                    css.append(tab);
//                    css.append("{");
//                    HashMap selectors = (HashMap)tabs.get(tab);
//                    Iterator<String> its = selectors.keySet().iterator();
//                    while(its.hasNext()) {
//                        String l = its.next();
//                        css.append(l+":"+selectors.get(l)+";");
//                    }
//                    css.append("}");
//                }
//                base.setAttribute("css", css.toString());
//                try{
//                    base.updateAttributesToDB();
//                }catch(Exception e){
//                    log.error("Error al guardar la hoja de estilos del recurso: "+base.getId() +"-"+ base.getTitle(), e);
//                }
//            }catch(IndexOutOfBoundsException iobe) {
//                log.error("Error al editar la hoja de estilos del recurso: "+base.getId() +"-"+ base.getTitle(), iobe);
//            }
//        }
    }

    /**
     * Prints the matriz.
     */
    private void printMatriz() {
        //si.printMatriz(getResourceBase().getId());
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
