
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

public class TestStyler extends GenericResource {
    private static Logger log = SWBUtils.getLogger(TestStyler.class);
    private StyleInner si;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        try {
            SWBResourceURL url=paramRequest.getActionUrl();

            if(base.getAttribute("css")!=null) {
                out.println("<script type=\"text/javascript\">");
                out.println("dojo.require(\"dojox.html.styles\");");
                out.println("function setStyleSheetByInstance(title, rules) {");
                String[] rules = base.getAttribute("css").split("}");
                for(int i=0; i<rules.length; i++) {
                    String[] rule = rules[i].split("\\{");
                    if(rule[1].length()>0)
                        out.println("   dojox.html.insertCssRule('"+rule[0]+"_"+base.getId()+"','"+rule[1]+"');");
                }
                out.println("}");
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

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");*/
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        if(action!=null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   alert('recurso actualizado con identificador "+ base.getId()+"');");
            out.println("   location='"+paramRequest.getRenderUrl().toString()+"';");
            out.println("</script>");
        }
        
        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = wpage.getWebSite();

        String str_role = base.getAttribute("editRole","0");
        out.println("<div class=\"swbform\">");
        SWBResourceURL urlA = paramRequest.getActionUrl();
        urlA.setAction("update");
        out.println("<form id=ilta_\""+base.getId()+"\" name=\"ilta_"+base.getId()+"\" action=\"" + urlA + "\" method=\"post\" >");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println("Datos");
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
        out.println("<td align=\"right\" width=\"150\">Rol o grupo:</td>");
        out.println("<td><select name=\"editar_"+base.getId()+"\">" + strTemp + "</select></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td align=\"right\" width=\"150\">Texto:</td>");
        out.println("<td><textarea name=\"txt\" rows=\"4\" cols=\"50\">" + base.getAttribute("text", "") + "</textarea></td>");
        out.println("</tr>");

        out.println("</table>");
        out.println("</fieldset>");

        out.println("<fieldset>");
        out.println("<legend>Estilo</legend>");
        String cssResPath = "/"+SWBUtils.TEXT.replaceAll(getClass().getName(), ".", "/")+".css";
        si = new StyleInner(getResourceBase());
        String script = null;
        try {
            script = si.render(paramRequest, cssResPath);
        }catch(NullPointerException e) {
            log.error("Tal vez no exite el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
        }catch(IOException e) {
            log.error("Error al leer el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
        }catch(Exception e) {
            log.error("Error en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
        }
        out.println(script);
        out.println("</fieldset>");

        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"reset\" >Restablecer</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    @Override
    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("\n************ processAction ************");
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

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillStyle")) {
            doEditStyle(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    public void doEditStyle(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("\n************ doEditStyle ************");
        Resource base = getResourceBase();
        String stel = request.getParameter("stel");
        //System.out.println("stel="+stel);
        String[] tkns = stel.split("@",3);
        //System.out.println("tkns[0]="+tkns[0]);
        System.out.println("tkns[1]="+tkns[1]+"----");
        System.out.println("tkns[2]="+tkns[2]+"----");
        //System.out.println("\n");

        HashMap tabs = (HashMap)si.getMm(base.getId());
        if( tabs!=null && tkns[1].length()>0 && tkns[2].length()>0) {
            try {
                System.out.println("tkns[0]="+tkns[0]);
                HashMap t = (HashMap)tabs.get(tkns[0]);
                if(tkns[2].equalsIgnoreCase("empty"))
                    t.remove(tkns[1]);
                else
                    t.put(tkns[1], tkns[2]);
                StringBuilder css = new StringBuilder();
                Iterator<String> ittabs = tabs.keySet().iterator();
                while(ittabs.hasNext()) {
                    String tab = ittabs.next();
                    //css.append("."+tab);
                    css.append(tab);
                    css.append("{");
                    HashMap selectors = (HashMap)tabs.get(tab);
                    Iterator<String> its = selectors.keySet().iterator();
                    while(its.hasNext()) {
                        String l = its.next();
                        css.append(l+":"+selectors.get(l)+";");
                    }
                    css.append("}");
                }
                System.out.println("css="+css);
                base.setAttribute("css", css.toString());
                try{
                    base.updateAttributesToDB();
                }catch(Exception e){
                    log.error("Error al guardar atributos en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
                    System.out.println("\n error....."+e);
                }

            }catch(IndexOutOfBoundsException iobe) {
                System.out.println("\n error... "+iobe);
            }
        }
    }

    private void printMatriz() {
        si.printMatriz(getResourceBase().getId());
    }

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
