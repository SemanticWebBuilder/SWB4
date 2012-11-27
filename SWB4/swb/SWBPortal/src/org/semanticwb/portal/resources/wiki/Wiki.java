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
package org.semanticwb.portal.resources.wiki;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
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
 * The Class Wiki.
 * 
 * @author Javier Solis Gonzalez
 */
public class Wiki extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Wiki.class);
    
    /** The parser. */
    WikiParser parser;
    
    /** The df. */
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy_H-mm-ss-SSS");

    /**
     * Creates a new instance of Wiki.
     */
    public Wiki() {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String content = getContent(readFile(paramsRequest));

        if (userCanEdit(paramsRequest)) {
            out.println("<p style=\"text-align:right;\"><a href=\"" + paramsRequest.getRenderUrl().setMode(SWBParamRequest.Mode_EDIT) + "\">[editar]</a></p>");
        }
        String pre = "<div class=\"wikiContent\">";
        String post = "</div>";
        if (content != null) {
            out.print(pre + parser.parse(content, request, paramsRequest) + post);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String WBWikiTextbox = request.getParameter("WBWikiTextbox");
        String summary = request.getParameter("summary");
        String lang = paramsRequest.getUser().getLanguage();

        String preview = "";
        if (request.getParameter("save") != null) {
            String header = backUpContent(paramsRequest, summary);
            writeContent(paramsRequest, header + WBWikiTextbox);
            response.sendRedirect(paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_VIEW).toString());
            return;
        } else if (request.getParameter("cancel") != null) {
            response.sendRedirect(paramsRequest.getRenderUrl().setMode(paramsRequest.Mode_VIEW).toString());
            return;
        } else if (request.getParameter("preview") != null) {
            preview = parser.parse(WBWikiTextbox, request, paramsRequest);
        }

        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramsRequest.getRenderUrl();
        String content = getContent(readFile(paramsRequest));
        if (content != null) {
            content = SWBUtils.XML.replaceXMLChars(content);
        } else {
            content = "";
        }
        String adminpath = SWBPlatform.getContextPath() + "/swbadmin/";

        out.println("  <script type=\"text/javascript\">var WBAdmPath='" + adminpath + "';</script>");
        out.println("  <script type=\"text/javascript\" src=\"" + adminpath + "js/wiki/wiki.js\"></script>");
        out.println("  <div id=\"WBWikiPreview\">" + preview + "</div>");
        out.println("  <form name=\"WBWikiEditForm\" id=\"WBWikiEditForm\" method=\"post\" action=\"" + url + "\">");
        out.println("    <div id=\"WBWikiToolBar\"></div>");
        out.println("    <textarea tabindex='1' accesskey=\",\" name=\"WBWikiTextbox\" id=\"WBWikiTextbox\" >" + content + "</textarea><br>");
        out.println("    <span>Comentario de los cambios</small></span>");
        out.println("    <br>");
        out.println("    <div class='editOptions'>");
        out.println("      <input tabindex='2' type='text' value=\"/* historia */ \" name='summary' id='summary' maxlength='200' size='60' /><br/>");
        out.println("      <div class='editButtons'>");
        out.println("        <input id=\"save\" name=\"save\" type=\"submit\" tabindex=\"3\" value=\"Guardar\" accesskey=\"s\" title=\"Guardar los cambios [s]\" />");
        out.println("        <input id=\"preview\" name=\"preview\" type=\"submit\" tabindex=\"4\" value=\"Vista previa\" accesskey=\"p\" title=\"Previsualizar los cambios [p]\" />");
        out.println("        <input id=\"diff\" name=\"diff\" type=\"submit\" tabindex=\"5\" value=\"Mostrar Cambios\" accesskey=\"v\" title=\"Muestra que cambios se an hecho [v]\" />");
        out.println("        <input id=\"cancel\" name=\"cancel\" type=\"submit\" tabindex=\"6\" value=\"Cancelar\" accesskey=\"c\" title=\"Cancelar [c]\"/>");
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </form>");
    }

    /**
     * Gets the header.
     * 
     * @param data the data
     * @return the header
     */
    public String getHeader(String data) {
        String pattern = "\n#EndHeader\n";
        int i = data.indexOf(pattern);
        if (i >= 0) {
            return data.substring(0, i + pattern.length());
        }
        return "#EndHeader\n";
    }

    /**
     * Gets the content.
     * 
     * @param data the data
     * @return the content
     */
    public String getContent(String data) {
        String ret = "";
        if (data != null) {
            String pattern = "\n#EndHeader\n";
            int i = data.indexOf(pattern);
            if (i >= 0) {
                ret = data.substring(i + pattern.length());
            } else {
                ret = data;
            }
        }
        return ret;
    }

    /**
     * Read file.
     * 
     * @param paramsRequest the params request
     * @return the string
     */
    public String readFile(SWBParamRequest paramsRequest) {
        String topic = paramsRequest.getWebPage().getId();
        String ret = null;
        try {
            ret = SWBPortal.readFileFromWorkPath(paramsRequest.getResourceBase().getWorkPath() + "/" + topic);
        } catch (Exception noe) {
        }

        return ret;
    }

    /**
     * Back up content.
     * 
     * @param paramsRequest the params request
     * @param summary the summary
     * @return the string
     */
    public String backUpContent(SWBParamRequest paramsRequest, String summary) {
        StringBuffer header = new StringBuffer();
        String topic = paramsRequest.getWebPage().getId();
        User user = paramsRequest.getUser();
        try {
            String data = readFile(paramsRequest);
            if (data != null) {
                header.append(getHeader(data));
                synchronized (this) {
                    String path = paramsRequest.getResourceBase().getWorkPath();
                    String backfile = "/old/" + topic + "." + df.format(new Date());
                    SWBPortal.writeFileToWorkPath(path + backfile, SWBUtils.IO.getStreamFromString(data), user);

                    header.insert(0, "#previous:" + backfile + "\n");
                    header.insert(0, "#user:" + paramsRequest.getUser().getId() + "\n");
                    header.insert(0, "#summary:" + summary + "\n");
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return header.toString();
    }

    /**
     * Write content.
     * 
     * @param paramsRequest the params request
     * @param content the content
     */
    public void writeContent(SWBParamRequest paramsRequest, String content) {
        String topic = paramsRequest.getWebPage().getId();
        User user = paramsRequest.getUser();
        try {
            SWBPortal.getResourceMgr().getResourceCacheMgr().removeResource(paramsRequest.getResourceBase());
            String path = paramsRequest.getResourceBase().getWorkPath() + "/" + topic;
            SWBPortal.writeFileToWorkPath(path, SWBUtils.IO.getStreamFromString(content), user);
        } catch (Exception noe) {
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        parser = new WikiParser();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#getModes(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public String[] getModes(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException {
        String arr[] = super.getModes(request, paramRequest);
        String ret[] = new String[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            ret[i] = arr[i];
        }
        ret[arr.length] = paramRequest.Mode_EDIT;
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#getResourceCacheID(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramsRequest) throws SWBResourceException {
        String retValue = super.getResourceCacheID(request, paramsRequest);
        if (retValue != null) {
            retValue += "-" + paramsRequest.getWebPage().getId() + "-" + paramsRequest.getUser().getLanguage();
        }
        return retValue;
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
        out.println("<div class=\"swbform\">");
        SWBResourceURL urlA = paramRequest.getActionUrl();
        urlA.setAction("admin_update");
        out.println("<form id=\"" + base.getId() + "/wikiRes\" name=\"" + getResourceBase().getId() + "/repfile\" action=\"" + urlA + "\" method=\"post\" _onsubmit=\"submitForm('" + base.getId() + "/wikiRes'); return false;\" >");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println("Wiki Resource");
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
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn\" >" + "Aceptar" + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//        if(request.getParameter("save")!=null)
//        {
//            String content=request.getParameter("WBWikiTextbox");
//            writeContent(response,content);
//            response.setMode(response.Mode_VIEW);
//        }else if(request.getParameter("cancel")!=null)
//        {
//            response.setMode(response.Mode_VIEW);
//        }
//        ResourceMgr.getInstance().getResourceCacheMgr().removeResource(response.getResourceBase().getRecResource());
        String action = response.getAction();
        if (action == null) {
            action = "";
        }
        if (action.equals("admin_update")) {
            String editaccess = request.getParameter("editar");
            if (editaccess != null) {
                getResourceBase().setAttribute("editRole", editaccess);
                try {
                    getResourceBase().updateAttributesToDB();
                } catch (Exception e) {
                    log.error("Error al guardar Role/UserGroup para acceso al wiki.", e);
                }

            }
        }
    }

    /**
     * User can edit.
     * 
     * @param paramrequest the paramrequest
     * @return true, if successful
     */
    private boolean userCanEdit(SWBParamRequest paramrequest)
    {
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
