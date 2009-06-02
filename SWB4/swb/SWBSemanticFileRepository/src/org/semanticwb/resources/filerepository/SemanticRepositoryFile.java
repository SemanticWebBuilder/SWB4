package org.semanticwb.resources.filerepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr170.implementation.SWBCredentials;
import org.semanticwb.jcr170.implementation.SWBRepository;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Role;

import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.FileUpload;

public class SemanticRepositoryFile extends org.semanticwb.resources.filerepository.base.SemanticRepositoryFileBase {

    private static Logger log = SWBUtils.getLogger(SemanticRepositoryFile.class);
    private static final String MODE_FILES = "showfiles";
    private static final String MODE_DIRS = "showdirs";
    private static final String FILE_DETAIL = "fdetail";
    private static final String REP_FILE = "swbfilerep:RepositoryFile";
    private static final String REP_FOLDER = "swbfilerep:RepositoryFolder";
    private static final String REP_NODE = "swbfilerep:RepositoryNode";
    private static final String JCR_CONTENT = "jcr:content";
    private String IDREP = null;

    public SemanticRepositoryFile() {
    }

    public SemanticRepositoryFile(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(MODE_FILES)) {
            doShowFiles(request, response, paramRequest);
        } else if (paramRequest.getMode().equals(MODE_DIRS)) {
            doShowDirs(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("getAddForm")) {
            doAddForm(request, response, paramRequest);//getAddForm
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();

        WebPage wp = paramRequest.getTopic();
        IDREP = docRepNS(request, response, paramRequest);

        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";

        try {
            SWBRepository rep = new SWBRepository();
            Session session = rep.login(new SWBCredentials(user), IDREP);

            Node root = session.getRootNode();

            Node nodeRep = null;
            Node nodePage = null;

            if (!root.hasNode(getResourceBase().getId())) {
                nodeRep = root.addNode(getResourceBase().getId(), REP_NODE);
                root.save();
            } else {
                nodeRep = root.getNode(getResourceBase().getId());
            }
            //Revisando el nodo asociado a la página
            if (nodeRep != null && !nodeRep.hasNode(paramRequest.getTopic().getId())) {
                nodePage = nodeRep.addNode(wp.getId(), REP_FOLDER);
                nodePage.setProperty("swb:title", wp.getDisplayName());
                nodePage.setProperty("swbfilerep:userid", user.getId());
                nodePage.setProperty("swb:description","");
                nodePage.setProperty("swbfilerep:deleted",Boolean.FALSE);

                nodeRep.save();
            }

            session.save();

        } catch (Exception e) {
            log.error("Error al revisar el nodo raíz del Rep. de Documentos. SemanticRepositoryFile.doView()", e);
        }
        out.println("<div style=\"font-family:verdana; font-size:10px;\">"); //class=\"SemanticRepositoryFile\"
        out.println("<fieldset>");
        out.println("<legend> <img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + getTitle(request, paramRequest) + "</legend>");
        out.println("");
        if (!isUseFolders()) {
            out.println("sin folders");
            out.println(doShowFiles(request, response, paramRequest));
        } else {
            out.println("con folders");
            out.println(doShowDirs(request, response, paramRequest));
        }
        out.println("</fieldset>");
        out.println("</div>");
    }

    public String doShowFiles(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        User user = paramRequest.getUser();

        WebPage wp = paramRequest.getTopic();
        IDREP = docRepNS(request, response, paramRequest);

        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        try {
            //Obtener nodo raíz del repositorio asociado al WebPage

            SWBRepository rep = new SWBRepository();
            Session session = rep.login(new SWBCredentials(user), IDREP);

            //Nodo raíz del repositorio de documentos asociado al sitio
            Node root = session.getRootNode();

            //Nodo asociado al id del recurso
            Node nodeRep = root.getNode(getResourceBase().getId());

            //Nodo asociado al id de la página
            Node nodePage = nodeRep.getNode(wp.getId());

            NodeIterator nit = nodePage.getNodes();

            System.out.println("Node Iterator:"+nit.getSize());

            ret.append("\n<div style=\"float:left; margin-right:10px; width:420px; border:solid 1px;\">");
            ret.append("\n<table width=\"100%\">");
            ret.append("\n<thead style=\"color:darkblue;\">");

            ret.append("\n<tr >");
            ret.append("\n<th colspan=\"2\">");
            ret.append("\nFolderName");
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"2\">");
            ret.append("\n<img src=\"" + path + "suscribe.gif\" border=\"0\"/>Suscribirse a directorio");
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"2\" align=\"right\">");
            SWBResourceURL urladd = paramRequest.getRenderUrl();
            urladd.setParameter("act", "addfile");
            urladd.setMode("getAddForm");
            ret.append("\n<a href=\"" + urladd + "\">");
            ret.append("\n<img src=\"" + path + "add.gif\" border=\"0\"/>");
            ret.append("\nAgregar archivo");
            ret.append("\n</a>");
            ret.append("\n</th>");
            ret.append("\n</tr>");

            ret.append("\n<tr style=\"background-color:lightgray;\">");
            ret.append("\n<th >");
            ret.append("\nInfo");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nIcono");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nTítulo");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nFecha");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nMarcar");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nAcción");
            ret.append("\n</th>");
            ret.append("\n</tr>");
            ret.append("\n</thead>");

            ret.append("\n<tfoot style=\"color:darkblue;\">");
            ret.append("\n<tr>");
            ret.append("\n<th colspan=\"3\">");
            ret.append("\n(" + (nit.getSize() != -1 ? nit.getSize() : 0) + ") archivos");
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"3\" align=\"right\">");
            ret.append("\n<a href=\"" + urladd + "\">");
            ret.append("\n<img src=\"" + path + "add.gif\" border=\"0\"/>");
            ret.append("\nAgregar archivo");
            ret.append("\n</a>");
            ret.append("\n</th>");
            ret.append("\n</tr>");
            ret.append("\n</tfoot>");

            if (nit.hasNext()) {
                ret.append("\n<tbody>");
                while (nit.hasNext()) {

                    Node nodo = nit.nextNode();
                    boolean isdeleted = false;
                    try{
                        isdeleted=nodo.getProperty("swbfilerep:deleted").getBoolean();
                    }
                    catch(Exception e)
                    {
                        log.event("Error al revisar la propiedad Deleted del repositorio de documentos.", e);
                        isdeleted=false;
                        nodo.setProperty("swbfilerep:deleted", false);
                        nodo.save();
                    }
                    if (nodo != null&&!isdeleted) {
                        ret.append("\n<tr>");
                        ret.append("\n<td>");
                        SWBResourceURL urldetail = paramRequest.getRenderUrl();
                        urldetail.setMode(FILE_DETAIL);
                        urldetail.setParameter("uddi", nodo.getUUID());
                        ret.append("\n<a href=\"#\" onclick=\"" + urldetail + "\"><img src=\"" + path + "info.gif\" border=\"0\"/></a>");
                        ret.append("\n</td>");
                        ret.append("\n<td>");
                        String file = nodo.getName();
                        String type = getFileName(file);
                        ret.append("\n<a><IMG border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\"></a>");
                        ret.append("\n</td>");
                        ret.append("\n<td>");
                        ret.append("\n" + nodo.getProperty("swb:title").getString());
                        ret.append("\n</td>");
                        ret.append("\n<td>");
                        ret.append("\n" + nodo.getProperty("jcr:created").getString());
                        ret.append("\n</td>");
                        ret.append("\n<td>");
                        SWBResourceURL uout = paramRequest.getRenderUrl();
                        ret.append("\n<img src=\"" + path + "out.gif\" border=\"0\"/>");
                        ret.append("\n</td>");
                        ret.append("\n<td>");
                        SWBResourceURL usus = paramRequest.getRenderUrl();
                        SWBResourceURL uview = paramRequest.getRenderUrl();
                        SWBResourceURL udel = paramRequest.getRenderUrl();
                        SWBResourceURL umove = paramRequest.getRenderUrl();

                        ret.append("\n<img src=\"" + path + "suscribe.gif\" border=\"0\"/><img src=\"" + path + "preview.gif\" border=\"0\"/><img src=\"" + path + "delete.gif\" border=\"0\"/><img src=\"" + path + "folder.gif\" border=\"0\"/>");
                        ret.append("\n</td>");
                        ret.append("\n</tr>");
                    }
                }
                session.save();
                ret.append("\n<tbody>");
            }
            ret.append("\n</table>");
            ret.append("\n</div>");

        } catch (Exception e) {
            log.error("Error al traer los archivos de folder del repositorio de documentos", e);
        }
        return ret.toString();
    }

    public String doShowDirs(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        ret.append("\n<div style=\"float:left; margin-right:10px; width:180px; border:solid 1px;\">");
        ret.append("\n<ul>");
        ret.append("\n</ul>");
        ret.append("\n</div>");
        ret.append(doShowFiles(request, response, paramRequest));
        return ret.toString();
    }

    public void doAddForm(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer ret = new StringBuffer();

        IDREP = docRepNS(request, response, paramRequest);
        WebPage dir = paramRequest.getTopic();
        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        User user = paramRequest.getUser();
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("addfile");

        if (getLevelUser(user) < 2) {
            return;
        }

        ret.append("\n<fieldset>");
        ret.append("\n<legend>");
        ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + dir.getDisplayName());
        ret.append("\n</legend>");
        ret.append("\n<form name='frmnewdoc' method='POST' enctype='multipart/form-data' action='" + url.toString() + "'>");
        ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\""+IDREP+"\">");
        ret.append("\n<table width='100%'  border='0' cellspacing='0' cellpadding='1'>");
        ret.append("\n<tr>");
        ret.append("\n<td width=\"200\">");
        ret.append("\n" + paramRequest.getLocaleString("msgTitleDocument"));
        ret.append("\n</td>");
        ret.append("\n<td>");
        ret.append("\n<input  type='text' maxlength='99' name='repftitle'>");
        ret.append("\n</td>");
        ret.append("\n</tr>");
        ret.append("\n<tr>");
        ret.append("\n<td width=\"200\">");
        ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
        ret.append("\n</td>");
        ret.append("\n<td>");
        ret.append("\n<textarea rows='5' name='repfdescription' cols='20' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></textarea>");
        ret.append("\n</td>");
        ret.append("\n</tr>");
        ret.append("\n<tr>");
        ret.append("\n<td width=\"200\">");
        ret.append("\n" + paramRequest.getLocaleString("msgFile"));
        ret.append("\n</td>");
        ret.append("\n<td>");
        ret.append("\n<input type='file'  name='repfdoc'>");
        ret.append("\n</td>");
        ret.append("\n</tr>");
        ret.append("\n</table>");
        ret.append("\n</fieldset>");
        ret.append("\n<fieldset>");
        ret.append("\n<input type='button'  name='s' value='" + paramRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();'>\r\n");
        SWBResourceURL urlb = paramRequest.getRenderUrl();
        urlb.setMode(SWBResourceURL.Mode_VIEW);
        ret.append("\n<input type='button'  name='cancel' value='" + paramRequest.getLocaleString("msgBTNCancel") + "' onclick=\"window.location='" + urlb.toString() + "'\">\r\n");
        ret.append("\n</form>");
        ret.append("\n</fieldset>");

        ret.append("<script language='JavaScript'>\r\n");
        ret.append("function textCounter(field,  maxlimit) {\r\n");
        ret.append("if (field.value.length > maxlimit)\r\n");
        ret.append("field.value = field.value.substring(0, maxlimit);\r\n");
        ret.append("}\r\n");
        ret.append("function submitform()\r\n");
        ret.append("{\r\n");
        ret.append("\r\ndocument.frmnewdoc.submit();\r\n");
        ret.append("}\r\n");
        ret.append("function valida(){\r\n");
        ret.append("if(document.frmnewdoc.repftitle.value==\"\")\r\n");
        ret.append("{\r\n");
        ret.append("alert(\"" + paramRequest.getLocaleString("msgAlertDefineTitle") + "\");\r\n");
        ret.append("return;\r\n");
        ret.append("}\r\n");
        ret.append("if(document.frmnewdoc.repfdescription.value==\"\")\r\n");
        ret.append("{\r\n");
        ret.append("alert(\"" + paramRequest.getLocaleString("msgAlertDefineDescription") + "\");\r\n");
        ret.append("return;\r\n");
        ret.append("}\r\n");
        ret.append("if(document.frmnewdoc.repfdoc.value==\"\")\r\n");
        ret.append("{\r\n");
        ret.append("alert(\"" + paramRequest.getLocaleString("msgAlertDefineFile") + "\");\r\n");
        ret.append("return;\r\n");
        ret.append("}\r\n");
        ret.append(" \r\n");
        ret.append("\r\n");
        ret.append("\r\n");
        ret.append("submitform();\r\n");
        ret.append("}\r\n");
        ret.append("</script>\r\n");

        out.println(ret.toString());

    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String strView = "-1";
        String strModify = "-1";
        String strAdmin = "-1";
        String strNotify = "";
        String strCheck = "checked";
        String strEnable = "";

        PrintWriter out = response.getWriter();
        String accion = paramRequest.getAction();
        if (accion == null) {
            accion = "";
        }
        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getTopic();
        WebSite wsite = wpage.getWebSite();
        long id = 0;
        boolean flag = false;

        try {
            if (getSee() != null) {
                strView = getSee();
                flag = true;
            }
            if (getModify() != null) {
                strModify = getModify();
                flag = true;
            }
            if (getAdmin() != null) {
                strAdmin = getAdmin();
                flag = true;
            }
        } catch (Exception e) {
            log.error("Error al revisar la configuración inicial del repositorio de documentos.");
        }

        if (isUseFolders()) {
            strCheck = "checked";
            flag = true;
        } else {
            strCheck = "";
            flag = true;
        }

        out.println("<div class=\"swbform\">");

        if (!accion.equals("")) {
            SWBResourceURL urlAddRule = paramRequest.getActionUrl();
            urlAddRule.setAction("admin_update");

            out.println("<form dojoType=\"dijit.form.Form\" id=\"" + getResourceBase().getId() + "/repfile\" name=\"myForm\" onsubmit=\"submitForm('" + getResourceBase().getId() + "/repfile'); return false;\" action=\"" + urlAddRule.toString() + "\" method=\"POST\">");

            out.println("<fieldset>");
            out.println("<legend>");
            out.println("Repository Resource");
            out.println("</legend>");


            out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\">");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            String strTemp = "<option value=\"-1\">" + paramRequest.getLocaleString("msgNoRolesAvailable") + "</option>";
            Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
            StringBuffer strRules = new StringBuffer("");
            strRules.append("\n<option value=\"0\">" + paramRequest.getLocaleString("msgSelNone") + "</option>");
            strRules.append("\n<optgroup label=\"Roles\">");
            while (iRoles.hasNext()) {
                Role oRole = iRoles.next();
                strRules.append("\n<option value=\"" + oRole.getURI() + "\">" + oRole.getDisplayTitle(user.getLanguage()) + "</option>");
            }
            strRules.append("\n</optgroup>");

            strRules.append("\n<optgroup label=\"User Groups\">");
            Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
            while (iugroups.hasNext()) {
                UserGroup oUG = iugroups.next();
                strRules.append("\n<option value=\"" + oUG.getURI() + "\">" + oUG.getDisplayTitle(user.getLanguage()) + "</option>");
            }
            strRules.append("\n</optgroup>");
            if (strRules.toString().length() > 0) {
                strTemp = strRules.toString();
            }

            out.println("<tr><td colspan=\"2\"><B>" + paramRequest.getLocaleString("msgRolesDefinitionLevel") + "</b></td></tr>");
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgView") + ":</td>");
            out.println("<td><select name=\"ver\">" + strTemp + "</select></td></tr>");
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgModify") + ":</td>");
            out.println("<td><select name=\"modificar\">" + strTemp + "</select></td></tr>");
            out.println("<tr><td align=right  width=150>" + paramRequest.getLocaleString("msgAdministrate") + ":</td>");
            out.println("<td><select name=\"administrar\">" + strTemp + "</select></td></tr>");
            out.println("<tr><td align=right  width=150>" + paramRequest.getLocaleString("msgShowSubDirs") + ":</td>");
            out.println("<td><input type=\"checkbox\" name=\"showdirectory\" value=\"1\" " + strCheck + " " + strEnable + ">");
            out.println("</td></tr>");

            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgCreateNotificationMessage") + ":</td>");

            strNotify = "";
            if (getMsgcrated() != null) {
                strNotify = getMsgcrated();
                flag = true;
            } else {
                strNotify = null;
                StringBuffer strMsg = new StringBuffer(paramRequest.getLocaleString("msgCreateNotificationMessage") + ":");
                strMsg.append("\n" + paramRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramRequest.getLocaleString("msgWasCreated") + ".\n");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgFileName") + ":       {getFileName}");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgCreationDate") + ":   {getLastUpdate}");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgReviewFile") + ":   {getDocLink}");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgCreationUser") + ":   {getUserName} <{getUserEmail}>");
                strNotify = strMsg.toString();
            }


            out.println("<td><textarea name=\"notificationcreate\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgUpdNotificationMessage") + ":</td>");

            strNotify = "";
            if (getMsgupdated() != null) {
                strNotify = getMsgupdated();
                flag = true;
            } else {
                strNotify = null;
                StringBuffer strMsg = new StringBuffer(paramRequest.getLocaleString("msgUpdNotificationMessage") + ":");
                strMsg.append("\n" + paramRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramRequest.getLocaleString("msgWasUpd") + ".\n");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgFileName") + ":       {getFileName}");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgReviewFile") + ":   {getDocLink}");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgLastUpd") + ":     {getLastUpdate}");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgVersionUser") + ":    {getUserName} <{getUserEmail}>");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgVersionComments") + ":");
                strMsg.append("\n        {getComments}");
                strNotify = strMsg.toString();
            }

            out.println("<td><textarea name=\"notificationupdate\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgRemoveNotificationMessage") + ":</td>");

            strNotify = "";
            if (getMsgdeleted() != null) {
                strNotify = getMsgdeleted();
                flag = true;
            } else {
                strNotify = null;
                StringBuffer strMsg = new StringBuffer(paramRequest.getLocaleString("msgRemoveNotificationMessage") + ":");
                strMsg.append("\n" + paramRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramRequest.getLocaleString("msgWasRemoved") + ".\n");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgDirName") + ":  {getDirectoryName}");
                strMsg.append("\n   " + paramRequest.getLocaleString("msgUserName") + ":       {getUserName} <{getUserEmail}>");
                strNotify = strMsg.toString();
            }

            out.println("<td><textarea name=\"notificationremove\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn\" >" + paramRequest.getLocaleString("msgBTNAccept") + "</button>");

            SWBResourceURL urlold = paramRequest.getRenderUrl();
            urlold.setAction("showold");
            urlold.setParameter("", strTemp);

            out.println("<button dojoType=\"dijit.form.Button\" name=\"btnold\" onclick=\"submitUrl('" + urlold + "',this.domNode)\" >" + paramRequest.getLocaleString("msgBTNViewOldFiles") + "</button>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<br>* " + paramRequest.getLocaleString("msgNotificationKeys") + ":");
            out.println("<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserName}   " + paramRequest.getLocaleString("msgCompleteUserName"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserEmail}  " + paramRequest.getLocaleString("msgUserEmail"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDirectoryName} " + paramRequest.getLocaleString("msgDirName"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getFileName}   " + paramRequest.getLocaleString("msgFileName"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocTitle}   " + paramRequest.getLocaleString("msgDocTitle"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocDesc}    " + paramRequest.getLocaleString("msgDocDescription"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocVersion} " + paramRequest.getLocaleString("msgDocVersion"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocLink} " + paramRequest.getLocaleString("msgDocLink"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getLastUpdate} " + paramRequest.getLocaleString("msgLastDateModification"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getComments}   " + paramRequest.getLocaleString("msgModificationComment"));
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            out.println("<br> * " + paramRequest.getLocaleString("msgNote") + ": " + paramRequest.getLocaleString("msgRolesDependent"));

            out.println("</fieldset>");
            out.println("</form>");


            if (flag) {
                if (strView.equals("-1")) {
                    strView = "0";
                }
                if (strModify.equals("-1")) {
                    strModify = "0";
                }
                if (strAdmin.equals("-1")) {
                    strAdmin = "0";
                }
                out.println("<script language=javascript>");
                out.println("      document.myForm.ver.value=\"" + strView + "\"");
                out.println("      document.myForm.modificar.value=\"" + strModify + "\"");
                out.println("      document.myForm.administrar.value=\"" + strAdmin + "\"");
                out.println("</script>");
            }
        } else if (accion.equals("ShowOld")) {
            out.println("");
//            try {
//
//                //out.println(paramRequest.getLocaleString("msgFileRemovePermanently") + ":</td>");
//
//                SWBResourceURL urlShowOld = paramRequest.getActionUrl();
//                urlShowOld.setMode(paramRequest.Mode_ADMIN);
//                urlShowOld.setAction("ShowOld");
//
//                out.println("<form action=\"" + urlShowOld.toString() + "\" name=\"frmdelfile\" method=POST><input type=\"hidden\" name=\"cmd\" value=\"del_file\">");
//                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\"><input type=\"hidden\" name=\"ifile\" value=\"\">");
//                out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
//                out.println("<tr class=tabla><td>" + paramRequest.getLocaleString("msgTHFolder") + "</td><td>" + paramRequest.getLocaleString("msgTHFileName") + "</td><td>" + paramRequest.getLocaleString("msgTHCreated") + "</td><td>" + paramRequest.getLocaleString("msgTHEmialUser") + "</td><td>" + paramRequest.getLocaleString("msgTHAction") + "</td></tr>");
//                String rowColor = "";
//                boolean cambiaColor = true;
//                while (rs.next()) {
//                    String str_doc = rs.getString("rep_docId");
//                    String str_topic = rs.getString("topic");
//                    String str_tm = rs.getString("idtm");
//                    String repemail = rs.getString("rep_email");
//                    String reptitle = rs.getString("rep_title");
//                    Timestamp repcreate = rs.getTimestamp("rep_create");
//
//                    rowColor = "#EFEDEC";
//                    if (!cambiaColor) {
//                        rowColor = "#FFFFFF";
//                    }
//                    cambiaColor = !(cambiaColor);
//                    out.println("<tr bgcolor=\"" + rowColor + "\">");
//                    String temp = str_topic;
//                    String tmsid = str_tm;
//                    String tpsid = str_topic;
//                    String nameFolder = "";
//                    String folderborrado = "";
//                    String idSub = "";
//                    int intDeleted = 0;
//                    TopicMap tpMAP = TopicMgr.getInstance().getTopicMap(tmsid);
//
//                    Topic tpSubDir = tpMAP.getTopic(tpsid, true);
//                    if (tpSubDir != null) {
//                        if (tpSubDir.getDbdata().getDeleted() == 1) {
//                            folderborrado = "<font size=1 color=red>&lt;folder eliminado&gt;</font>";
//                            intDeleted = 1;
//                            idSub = tpSubDir.getId();
//                        }
//                        nameFolder = tpSubDir.getDisplayName(user.getLanguage()) + folderborrado;
//                    } else {
//                        tpSubDir = tpMAP.getTopic(tpsid, false);
//                        nameFolder = tpSubDir.getDisplayName(user.getLanguage());
//                    }
//
//                    out.println("<td class=datos>" + nameFolder + "</td>");
//                    out.println("<td class=datos>" + reptitle + "</td>");
//                    out.println("<td class=datos>" + WBUtils.dateFormat(repcreate) + "</td>");
//                    out.println("<td class=datos><a href=\"mailto:" + DBUser.getInstance().getUserById(repemail).getEmail() + "\">" + DBUser.getInstance().getUserById(repemail).getEmail() + "</a></td>");
//
//                    out.println("<td class=datos>");
//                    if (canRecover(str_tm, str_topic)) {
//                        out.println("<a class=link href=\"javascript: DoRecover(" + str_doc + "," + intDeleted + ",'" + idSub + "');\">" + "<img src=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/images/recover.gif\" border=0 title=\"" + paramRequest.getLocaleString("msgAltRestore") + "\" >" + "</a>");
//                    } else {
//                        out.println("<img src=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/images/recover.gif\" border=0 title=\"" + paramRequest.getLocaleString("msgAltRestore") + "\" >");
//                    }
//                    out.println("&nbsp;<a class=link href=\"javascript: DoConfirm(" + str_doc + ");\">" + "<img src=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/images/trash_vacio.gif\" border=0 title=\"" + paramRequest.getLocaleString("msgAltEliminate") + "\" >" + "</a>");
//                    out.println("</tr>");
//                }
//
//                out.println("<tr>");
//                out.println("<td colspan=\"5\" align=\"right\"><HR size=1 noshade> ");
//                out.println("<input type=button class=boton name=btnCancel onClick=\"javascript: DoRedir();\" value=" + paramRequest.getLocaleString("msgBTNCancel") + " >");
//                out.println("</td>");
//                out.println("</tr>");
//                out.println("</table>");
//                out.println("</form>");
//                SWBResourceURL urlEdit = paramRequest.getRenderUrl();
//                urlEdit.setMode(paramRequest.Mode_ADMIN);
//                urlEdit.setAction("edit");
//
//                out.println("<form name=\"frmredir\" action=\"" + urlEdit.toString() + "\" method=\"post\">");
//                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\">");
//                out.println("</form>");
//                out.println("<form name=\"frmrestore\" action=\"" + urlShowOld.toString() + "\" method=\"post\">");
//                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\"><input type=\"hidden\" name=\"docid\" value=\"\"><input type=\"hidden\" name=\"resdir\" value=\"\">");
//                out.println("<input type=\"hidden\" name=\"idSub\" value=\"\">");
//                out.println("</form>");
//                out.println("<script language=javascript>");
//                out.println("  function DoRedir(){");
//                out.println("      window.document.frmredir.submit();");
//                out.println("  }");
//                out.println("  function DoConfirm(erase){");
//                out.println("      if(confirm('" + paramRequest.getLocaleString("msgAlertSelFilesRemoved") + "!')){");
//                out.println("          window.document.frmdelfile.ifile.value=erase;");
//                out.println("          window.document.frmdelfile.submit();");
//                out.println("      }");
//                out.println("  }");
//                out.println("  function DoRecover(ivar,f_erased,id_sub){");
//
//                out.println("      if(f_erased=='1') ");
//                out.println("       {");
//                out.println("           if(confirm('" + "Si recuperas este archivo se recuperara el folder. \\n \\n ¿Estás seguro de querer hacerlo?" + "')) ");
//                out.println("             {");
//                out.println("                  window.document.frmrestore.resdir.value = 1;");
//                out.println("                  window.document.frmrestore.idSub.value = id_sub;");
//                out.println("                  window.document.frmrestore.docid.value = ivar;");
//                out.println("                  window.document.frmrestore.submit();");
//                out.println("             }");
//                out.println("           else");
//                out.println("            {");
//                out.println("                  return;");
//                out.println("            }");
//                out.println("       }");
//                out.println("      else");
//                out.println("       {");
//                out.println("           window.document.frmrestore.resdir.value = 0;");
//                out.println("           window.document.frmrestore.idSub.value = 0;");
//                out.println("           window.document.frmrestore.docid.value = ivar;");
//                out.println("           window.document.frmrestore.submit();");
//                out.println("        }");
//                out.println("      ");
//                out.println("      ");
//                out.println("  }");
//                out.println("</script>");
//
//            } catch (Exception e) {
//                log.error("Error in method Repository.doAdmin() when action is ShowOld, resource is " + getResourceBase().getId(), e);
//            }
        }
        out.println("</div>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        //Resource base = getResourceBase();
        String id = request.getParameter("suri");
        String repNS = request.getParameter("repNS");
        if (null == action) {
            action = "";
        }
        WebSite ws = response.getTopic().getWebSite();
        User user = response.getUser();

        Credentials credentials = new SWBCredentials(user);
        System.out.println("ProcessAction:" + action);
        if ("admin_update".equals(action)) {
            String viewrole = request.getParameter("ver");
            String modifyrole = request.getParameter("modificar");
            String adminrole = request.getParameter("administrar");
            String notifynew = request.getParameter("notificationcreate");
            String nofiryupdate = request.getParameter("notificationupdate");
            String notifyremove = request.getParameter("notificationremove");
            boolean usefolders = request.getParameter("showdirectory") != null && request.getParameter("showdirectory").equals("1") ? true : false;

            setSee(viewrole);
            setModify(modifyrole);
            setAdmin(adminrole);
            setMsgcrated(notifynew);
            setMsgupdated(nofiryupdate);
            setMsgdeleted(notifyremove);
            setUseFolders(usefolders);

            response.setMode(response.Mode_ADMIN);

        } else if ("admin_recover".equals(action)) //recuperación de archivo eliminado
        {
            response.setMode(response.Mode_ADMIN);
            response.setAction("recover");
        } else if ("admin_delete".equals(action)) //eliminación completa del archivo previa/ marcado como eliminado
        {
            response.setMode(response.Mode_ADMIN);
            response.setAction("recover");
        } else if ("addfile".equals(action)) //agregar nuevo archivo al repositorio
        {
            String filename = null;
            try {

                /*
                Session session = rep.login(new SWBCredentials(user), IDREP);

            Node root = session.getRootNode();

            Node nodeRep = null;
            Node nodePage = null;

            if (!root.hasNode(this.getResourceBase().getId())) {
                nodeRep = root.addNode(this.getResourceBase().getId(), REP_NODE);
            } else {
                nodeRep = root.getNode(this.getResourceBase().getId());
            }
            //Revisando el nodo asociado a la página
            if (nodeRep != null && !nodeRep.hasNode(paramRequest.getTopic().getId())) {
                nodePage = nodeRep.addNode(paramRequest.getTopic().getId(), REP_FOLDER);
                nodePage.setProperty("swb:title", paramRequest.getTopic().getDisplayName());
                nodePage.setProperty("swbfilerep:userid", user.getId());
                nodePage.setProperty("swb:description","");
            }
            session.save();
            //*/


                FileUpload fup = new FileUpload();
                fup.getFiles(request, null);

                repNS = fup.getValue("repNS");

                filename = fup.getFileName("repfdoc");
                String title = fup.getValue("repftitle");
                String description = fup.getValue("repfdescription");

                if (filename.lastIndexOf('/') != -1) {
                    int pos = filename.lastIndexOf('/');
                    filename = filename.substring(pos + 1);
                }
                if (filename.lastIndexOf('\\') != -1) {
                    int pos = filename.lastIndexOf('\\');
                    filename = filename.substring(pos + 1);
                }

                byte[] bcont = fup.getFileData("repfdoc");

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());

                SWBRepository rep = new SWBRepository();
                Session session = rep.login(credentials, repNS);
                Node root = session.getRootNode();
                Node nodeRep = root.getNode(getResourceBase().getId());//Nodo asociado al id del recurso
                //nodeRep.getNodes("swbfilerep:RepositoryFolder");
                Node nodePage = nodeRep.getNode(response.getTopic().getId());//nombre id_pagina

                //Agregando nodo de tipo file
                Node nodeFile = nodePage.addNode(filename, REP_FILE);

                System.out.println("titulo: "+title+", descripcion: "+description);

                nodeFile.setProperty("swb:title", title);
                nodeFile.setProperty("swb:description", description);
                nodeFile.setProperty("swbfilerep:deleted",Boolean.FALSE);

                Node nodeRes = nodeFile.addNode(JCR_CONTENT, "swbfilerep:RepositoryResource");
                nodeRes.setProperty("jcr:data", new ByteArrayInputStream(bcont));
                //nodeRes.setProperty("jcr:name", filename);
                nodeRes.setProperty("jcr:encoding", "");
                nodeRes.setProperty("jcr:lastModified", cal);
                nodeRes.setProperty("jcr:mimeType", fup.getContentType());
                nodeRes.setProperty("swbfilerep:comment", "Nuevo");
                nodeRes.setProperty("swbfilerep:userid", user.getId());
                nodeRes.setProperty("swbfilerep:filesize", bcont.length);


                nodePage.save();
                Version version = nodeRes.checkin();
                
                session.save();

                log.debug("Version created with number " + version.getName());


            } catch (Exception e) {
                log.error("ERROR al agregar un archivo al repositorio de documentos.",e);
            }
            response.setMode(SWBActionResponse.Mode_VIEW);
        } else if ("addfolder".equals(action)) //agregar nuevo folder al repositorio
        {
            String strTitle = request.getParameter("ftitle");
            String strDescription = request.getParameter("fdescription");
            try {
                SWBRepository rep = new SWBRepository();
                Session session = rep.login(credentials, ws.getId());
                Node root = session.getRootNode();
                Node nodeRep = root.getNode(this.getResourceBase().getId());//Nodo asociado al id del recurso
                //nodeRep.getNodes("swbfilerep:RepositoryFolder"); //trae los directorio de tipo RepositoryFolder
                Node nodePage = nodeRep.getNode(response.getTopic().getId());//nombre id_pagina
                Node nodeFolder = nodePage.addNode("swbfilerep:RepositoryFolder", "swbfilerep:RepositoryFolder");
                nodeFolder.setProperty("swb:title", strTitle);
                nodeFolder.setProperty("swb:description", strDescription);

                nodeFolder.save();
                session.save();

            } catch (Exception e) {
                log.error("Error al crear folder processAction.addFolder",e);
            }
        } 
        else if ("removefile".equals(action)) // marcar archivo como eliminado lógico
        {
            String UUDI = request.getParameter("fileuudi");
            try {
                SWBRepository rep = new SWBRepository();
                Session session = rep.login(credentials, ws.getId());
                Node fnode = session.getNodeByUUID(UUDI);
                fnode.setProperty("swbrepfile:deleted", Boolean.TRUE);
                fnode.save();
                session.save();
            } catch (Exception e) {
                log.error("Error al eliminar el archivo processAction.removeFile",e);
            }
            response.setMode(SWBActionResponse.Mode_VIEW);
        
        } else if ("removefolder".equals(action)) // marcar folder como eliminado
        {
            String UUDI = request.getParameter("fileuudi");
            try {
                SWBRepository rep = new SWBRepository();
                Session session = rep.login(credentials, ws.getId());
                Node fnode = session.getNodeByUUID(UUDI);
                fnode.setProperty("swbrepfile:deleted", Boolean.TRUE);
                NodeIterator nit = fnode.getNodes();
                while(nit.hasNext())
                {
                    Node nodo = nit.nextNode();
                    nodo.setProperty("swbrepfile:deleted", Boolean.TRUE);
                    //nodo.save(); //?
                }
                fnode.save();
                session.save();
            } catch (Exception e) {
                log.error("Error al eliminar el folder processAction.removeFolder",e);
            }
        } else if ("".equals(action)) {
        }
        if (null != id) {
            response.setRenderParameter("suri", id);
        }

    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.doEdit(request, response, paramRequest);
    }

    public int getLevelUser(User user) {
        int level = 0;

        String uriView = getSee() != null ? getSee() : "0";
        String uriModify = getModify() != null ? getModify() : "0";
        String uriAdmin = getAdmin() != null ? getAdmin() : "0";

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = null;
        try {
            gobj = ont.getGenericObject(uriAdmin);
        } catch (Exception e) {
            //log.error("Errror getLevelUser()",e);
        }

        UserGroup ugrp = null;
        Role urole = null;

        if (gobj != null) {
            if (gobj instanceof UserGroup) {
                ugrp = (UserGroup) gobj;
                if (user.hasUserGroup(ugrp)) {
                    level = 3;
                }
            } else if (gobj instanceof Role) {
                urole = (Role) gobj;
                if (user.hasRole(urole)) {
                    level = 3;
                }
            }
        } else {
            level = 3;
        }

        if (level == 0) {
            gobj = ont.getGenericObject(uriModify);
            if (gobj != null) {
                if (gobj instanceof UserGroup) {
                    ugrp = (UserGroup) gobj;
                    if (user.hasUserGroup(ugrp)) {
                        level = 2;
                    }
                } else if (gobj instanceof Role) {
                    urole = (Role) gobj;
                    if (user.hasRole(urole)) {
                        level = 2;
                    }
                }
            } else {
                level = 2;
            }
        }

        if (level == 0) {
            gobj = ont.getGenericObject(uriView);
            if (gobj != null) {
                if (gobj instanceof UserGroup) {
                    ugrp = (UserGroup) gobj;
                    if (user.hasUserGroup(ugrp)) {
                        level = 1;
                    }
                } else if (gobj instanceof Role) {
                    urole = (Role) gobj;
                    if (user.hasRole(urole)) {
                        level = 1;
                    }
                }
            } else {
                level = 1;
            }
        }

        return level;
    }

    public String getFileType(String filename) {
        String file = "Document";
        String type = filename.toLowerCase();
        if (type.indexOf(".bmp") != -1) {
            file = "Image";
        } else if (type.indexOf(".pdf") != -1) {
            file = "Adobe Acrobat";
        } else if (type.indexOf(".xls") != -1 || type.indexOf(".xlsx") != -1) {
            file = "Microsoft Excel";
        } else if (type.indexOf(".html") != -1 || type.indexOf(".htm") != -1) {
            file = "HTML file";
        } else if (type.indexOf("jpg") != -1 || type.indexOf("jpeg") != -1) {
            file = "Image";
        } else if (type.indexOf(".ppt") != -1 || type.indexOf(".pptx") != -1) {
            file = "Microsoft Power Point";
        } else if (type.indexOf(".vsd") != -1) {
            file = "Microsoft Visio";
        } else if (type.indexOf(".mpp") != -1) {
            file = "Microsoft Project";
        } else if (type.indexOf(".mmap") != -1) {
            file = "Mind Manager";
        } else if (type.indexOf(".exe") != -1) {
            file = "Application";
        } else if (type.indexOf(".txt") != -1) {
            file = "Text file";
        } else if (type.indexOf(".properties") != -1) {
            file = "Properties file";
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1) {
            file = "Microsoft Word";
        } else if (type.indexOf(".xml") != -1) {
            file = "XML file";
        } else if (type.indexOf(".gif") != -1 || type.indexOf(".png") != -1) {
            file = "Image";
        } else if (type.indexOf(".avi") != -1) {
            file = "Media file";
        } else if (type.indexOf(".mp3") != -1) {
            file = "Audio file";
        } else if (type.indexOf(".wav") != -1) {
            file = "Audio file";
        } else if (type.indexOf(".xsl") != -1) {
            file = "XSLT file";
        }
        return file;
    }

    public String getFileName(String filename) {
        String file = "ico_default2.gif";
        String type = filename.toLowerCase();
        if (type.indexOf(".bmp") != -1) {
            file = "ico_bmp.gif";
        } else if (type.indexOf(".pdf") != -1) {
            file = "ico_acrobat.gif";
        } else if (type.indexOf(".xls") != -1 || type.indexOf(".xlsx") != -1) {
            file = "ico_excel.gif";
        } else if (type.indexOf(".html") != -1 || type.indexOf(".htm") != -1) {
            file = "ico_html.gif";
        } else if (type.indexOf("jpg") != -1 || type.indexOf("jpeg") != -1) {
            file = "ico_jpeg.gif";
        } else if (type.indexOf(".ppt") != -1 || type.indexOf(".pptx") != -1) {
            file = "ico_powerpoint.gif";
        } else if (type.indexOf(".exe") != -1) {
            file = "ico_program.gif";
        } else if (type.indexOf(".txt") != -1 || type.indexOf(".properties") != -1) {
            file = "ico_text.gif";
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1) {
            file = "ico_word.gif";
        } else if (type.indexOf(".xml") != -1 || type.indexOf(".xsl") != -1) {
            file = "ico_xml.gif";
        } else if (type.indexOf(".mmap") != -1) {
            file = "ico_mindmanager.GIF";
        } else if (type.indexOf(".gif") != -1) {
            file = "ico_gif.gif";
        } else if (type.indexOf(".avi") != -1) {
            file = "ico_video.gif";
        } else if (type.indexOf(".mp3") != -1) {
            file = "ico_audio.gif";
        } else if (type.indexOf(".wav") != -1) {
            file = "ico_audio.gif";
        }
        return file;
    }

    public String docRepNS(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        String NS = IDREP;
        WebSite ws = paramRequest.getTopic().getWebSite();
        try {
            SWBRepository rep = new SWBRepository();
            String[] lws = rep.listWorkspaces();
            for (int i = 0; i < lws.length; i++) {
                if (lws[i].endsWith(ws.getId() + "_rep")) {
                    IDREP = lws[i];
                    NS = IDREP;
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Error al obtener el NameSpace del Repositorio de docuentos.", e);
        }
        return NS;
    }
}
