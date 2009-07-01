<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="net.fckeditor.*" %>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.VersionInfo"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%--@ taglib uri="http://java.fckeditor.net" prefix="FCK" --%>
<%
    Resource base = paramRequest.getResourceBase();
    SWBResourceURL url = paramRequest.getRenderUrl();
    url.setCallMethod(SWBResourceURL.Call_CONTENT);
    url.setMode("saveContent");
    String action = (request.getParameter("tmpPath") != null) ? "tmp" : "";  //request.getParameter("tmpPath")
    int version = (request.getParameter("version") != null && !"".equals(request.getParameter("version")))
                     ? Integer.parseInt(request.getParameter("version"))
                     : 0;
    VersionInfo versionInfo = new VersionInfo(base.getSemanticObject());
    version = versionInfo.getVersionNumber();
    net.fckeditor.FCKeditor fckEditor = new net.fckeditor.FCKeditor(request, "EditorDefault");
    fckEditor.setHeight("450");
    String content = (String) request.getAttribute("fileContent");
    fckEditor.setValue(content);
    SWBResourceURL urlNewVersion = paramRequest.getRenderUrl();
    urlNewVersion.setCallMethod(SWBResourceURL.Call_CONTENT);
    urlNewVersion.setMode("selectFileInterface");
    String portletWorkPath = base.getWorkPath() + "/" + (version > 1 ? version : 1) + "/tmp/";
%>
<%--
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2008 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 * @version: $Id: sample01.jsp 2167 2008-07-03 21:33:15Z mosipov $
--%>
    <link href="/swbadmin/js/fckeditor/editor/css/sample.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
      function FCKeditor_OnComplete(editorInstance) {
        window.status = editorInstance.Description;
      }
      var urlFileSelection = "<%=urlNewVersion.toString()%>";
      function callUpload() {
        var f = document.frames ? document.frames["EditorDefault___Frame"] : document.getElementById("EditorDefault___Frame");
        var p = f.contentWindow || f.document;
        var emerg = p.FCKDialog || f.FCKDialog;
        //Esta liga abre el cuadro de dialogo para cargar archivos con los programas de FCKEditor
        //p.FCKDialog.OpenDialog('FCKDialog_UploadFile', 'UploadFile', '/swb/resources/scripts/fckeditor/editor/dialog/fck_HTMLfile.html', 450, 390, null, this.window, false);
        emerg.OpenDialog('FCKDialog_UploadFile', 'UploadFile', '<%=urlNewVersion.toString()%>', 450, 390, null, this.window, false);
      }
      function sendFile() {
        if (document.mainFile.filePath.value == "") {
            alert("Por favor, seleccione un archivo.")
        } else if (document.mainFile.filePath.value.indexOf(",") != -1) {
            alert("Por favor, seleccione solo un archivo.")
            document.mainFile.filePath.value = "";
        } else {
            var allowedExt = ".html,.htm,.xhtml";
            var name = document.mainFile.filePath.value;
            var ext = name.substring(name.lastIndexOf("."), name.length);
            alert(ext);
            if (allowedExt.indexOf(ext) == -1) {
                alert("Solo se aceptan archivos con extension: " + allowedExt);
                document.mainFile.filePath.value = "";
            } else {
                document.mainFile.submit();
            }
        }
      }
      function closeAndReload(w, path) {
          w.Cancel();
          document.newFileForm.relativePath.value = path;
          document.newFileForm.submit();
      }
    </script>
    <div></div>
    <hr />
    <form action="<%=url.toString()%>" method="post">
    <%
        out.println(fckEditor);
    %>
    <br />
        <input type="hidden" name="operation" value="<%=action%>" />
        <input type="hidden" name="version" value="<%=version%>" />
    </form>
    <div align="right">
    <form name="mainFile" method="post" action="<%=urlNewVersion.toString()%>">
        <input type="button" value="Cargar archivo" onclick="javascript:callUpload();" />
    </form>
    </div>
<%
    url.setMode(paramRequest.Mode_EDIT);
    url.setAction(SWBResourceURL.Action_EDIT);
%>
    <form name="newFileForm" id="newFileForm" method="post" action="<%=url.toString()%>">
        <input type="hidden" name="tmpPath" value="<%=portletWorkPath%>" />
        <input type="hidden" name="relativePath" value="" />
    </form>
