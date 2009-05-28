/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.WBFileUpload;


/**
 * Presenta un editor de HTML para generar contenidos que se asocian a la página en que se genera este recurso.
 * @author jose.jimenez
 */
public class HTMLContent extends GenericResource {

    
    private static Logger log = SWBUtils.getLogger(HTMLContent.class);

    
    /**
     * Presenta el editor de HTML para generar el contenido a mostrar
     * @param request
     * @param response
     * @param paramRequest
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        
        Resource resource = paramRequest.getResourceBase();
        HttpSession session = request.getSession();
        VersionInfo versionInfo = new VersionInfo(resource.getSemanticObject());
        int versionNumber = versionInfo.getVersionNumber();
        String fileName = "index.html";
        String action = paramRequest.getAction();
        StringBuffer pathToRead = new StringBuffer(70);
        StringBuffer pathToWrite = new StringBuffer(70);
        String content = "";
        //Para mostrar el contenido de una versión temporal
        String tmpPath = request.getParameter("tmpPath");
        
        pathToRead.append(resource.getWorkPath() + "/");
        pathToWrite.append("/work" + resource.getWorkPath() + "/");
        
        if (action.equalsIgnoreCase(SWBParamRequest.Action_EDIT)
                && versionNumber == 0 && tmpPath == null) {
            action = SWBParamRequest.Action_ADD;
        }
        
        pathToRead.append(versionNumber + "/");
        pathToRead.append(fileName);
        pathToWrite.append("" + (++versionNumber));
        session.setAttribute("directory", pathToWrite.toString());
        
        if (action.equals(SWBParamRequest.Action_EDIT)) {
            try {
                //Cuando se carga el archivo normalmente
                if (tmpPath == null || "".equals(tmpPath)) {
                    content = SWBUtils.IO.readInputStream(
                            SWBPlatform.getFileFromWorkPath(pathToRead.toString()));
                } else { //cuando se carga el archivo temporal
                    content = SWBUtils.IO.readInputStream(
                            SWBPlatform.getFileFromWorkPath(tmpPath + "index.html"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("fileContent", content);

        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(
                    "/swbadmin/jsp/HtmlContentAdmin.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }
    
    /**
     * Presenta el contenido generado con el editor de HTML
     * @param request
     * @param response
     * @param paramRequest
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        
        Resource resource = paramRequest.getResourceBase();
        VersionInfo versionInfo = new VersionInfo(resource.getSemanticObject());
        int versionNumber = versionInfo.getVersionNumber();
        String fileName = versionInfo.getVersionFile();
        String resourceWorkPath = SWBPlatform.getWorkPath()
                + resource.getWorkPath() + "/" + versionNumber + "/" + fileName;
        response.getWriter().println(SWBUtils.IO.getFileFromPath(resourceWorkPath));
    }

    /**
     * Determina el metodo a ejecutar en base al modo que se envia en el objeto HttpServletRequest recibido
     * @param request
     * @param response
     * @param paramRequest
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    @Override
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("saveContent")) {
            saveContent(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("uploadNewVersion")) {
            uploadNewVersion(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("selectFileInterface")) {
            selectFileInterface(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Almacena en un archivo con extensión .html el contenido mostrado en el 
     * editor, creando una nueva versión de este recurso.
     * @param request
     * @param response
     * @param paramRequest
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    public void saveContent(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        
        Resource resource = paramRequest.getResourceBase();
        String contentPath = (String) request.getSession().getAttribute("directory");
        String textToSave = request.getParameter("EditorDefault");
        boolean deleteTmp = (request.getParameter("operation") != null
                             && !"".equals(request.getParameter("operation")))
                            ? true : false;
        String filename = null;
        boolean textSaved = false;
        VersionInfo version = new VersionInfo(resource.getSemanticObject());
        int versionNumber = version.getVersionNumber();
        int versionToDelete = versionNumber;
        String directoryToRemove = SWBPlatform.getWorkPath() 
                + resource.getWorkPath() + "/" 
                + (versionToDelete > 1 ? versionToDelete : 1) + "/tmp";
        String directoryToCreate = SWBPlatform.getWorkPath() 
                + resource.getWorkPath() + "/" + (versionNumber + 1) + "/images";
        
        //Siempre se crea una nueva version al guardar
        versionNumber++;
        if (textToSave != null) {
            try {
                //Quito de una de las dos rutas el directorio -work-, ya que 
                //las dos lo tienen.
                File filePath = new File(SWBPlatform.getWorkPath().substring(0,
                        SWBPlatform.getWorkPath().lastIndexOf("/") + 1)
                        + contentPath);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                File file = new File(SWBPlatform.getWorkPath().substring(0,
                        SWBPlatform.getWorkPath().lastIndexOf("/") + 1)
                        + contentPath + "/index.html");
                filename = file.getName();
                FileWriter writer = new FileWriter(file);
                
                //Si estaba en directorio temporal, modificar rutas de archivos asociados
                if (deleteTmp) {
                    textToSave = textToSave.replaceAll(
                            SWBPlatform.getWebWorkPath()
                            + resource.getWorkPath() + "/"
                            + (version.getVersionNumber() > 1 ? version.getVersionNumber() : 1)
                            + "/tmp/images", 
                            SWBPlatform.getWebWorkPath()
                            + resource.getWorkPath() + "/"
                            + versionNumber + "/images");
                }
                
                writer.write(textToSave);
                writer.flush();
                writer.close();
                version.setVersionFile(filename);
                version.setVersionNumber(versionNumber);
                //TODO: Revisar si esto es correcto. Forzosamente se debe fijar
                //la editada como la actual?
                //resource.setActualVersion(versionNumber);
                //resource.setLastVersion(versionNumber);
                textSaved = true;
                if (deleteTmp) {
                    File imagesDirectory = new File(directoryToRemove + "/images");
                    //eliminar el directorio tmp de la version anterior
                    if (imagesDirectory.exists()
                            && SWBUtils.IO.createDirectory(directoryToCreate)) {
                        
                        //Copia los archivos del directorio tmp al de la nueva version
                        for (String strFile : imagesDirectory.list()) {
                            SWBUtils.IO.copy(imagesDirectory.getPath() + "/" + strFile,
                                    directoryToCreate + "/" + strFile, false,
                                    "", "");
                        }
                    }
                    SWBUtils.IO.removeDirectory(directoryToRemove);
                }
            } catch (Exception e) {
                log.error("Al escribir el archivo", e);
            }
        }
        PrintWriter out = response.getWriter();
        if (textSaved) {
            out.println("El contenido ha sido guardado exit&oacute;samente");
        } else {
            out.println("Se produjo un error al almacenar la información, intente de nuevo.");
        }
        out.close();
    }
    
    /**
     * Carga un archivo al file system del servidor en un directorio temporal
     * utilizando la interfaz del FCKEditor y el applet DragDrop.
     * @param request
     * @param response
     * @param paramRequest
     * @throws java.io.IOException
     */
    private void uploadNewVersion(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws IOException {
        
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(url.Call_DIRECT);
        url.setMode("admin");
        Resource resource = paramRequest.getResourceBase();
        String fileContent = null;
        VersionInfo version = new VersionInfo(resource.getSemanticObject());
        String portletWorkPath = SWBPlatform.getWorkPath()
                + resource.getWorkPath() + "/" 
                + (version.getVersionNumber() > 1
                   ? version.getVersionNumber() : 1)
                + "/tmp/";
        String clientFilePath = "";
        String filename = null;
        File file = new File(portletWorkPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File fileTmp = new File(portletWorkPath + "index.html");
        if (fileTmp.exists()) {
            fileTmp.delete();
        }
        WBFileUpload fUpload = new WBFileUpload();
        fUpload.getFiles(request);
        filename = fUpload.getFileName("NewFile");
        filename = filename.replace('\\', '/');
        int i = filename.lastIndexOf("/");
        if (i > -1) {
            clientFilePath = filename.substring(0, i + 1);
            filename = filename.substring(i + 1);
        }
        i = filename.lastIndexOf("/");
        if (i != -1) {
            filename = filename.substring(i + 1);
        }
        fUpload.saveFile("NewFile", portletWorkPath);
        String strAttaches = fUpload.FindAttaches("NewFile");
        String[] filesAttached = strAttaches.split(";");
        
        //Almacena la ruta relativa (en la máquina cliente) de los archivos relacionados al html.
        String localRelativePath = null;
        if (filesAttached.length > 0 && filesAttached[0].indexOf("/") != -1) {
            localRelativePath = filesAttached[0].substring(0,
                    filesAttached[0].lastIndexOf("/"));
        }
        file = new File(portletWorkPath + "images/");
        if (!file.exists()) {
            file.mkdir();
        }
        
        //Renombrar el nuevo archivo
        try {
            //Se cambian las rutas a los archivos asociados.
            if (strAttaches != null && strAttaches.length() > 0) {
                SWBUtils.IO.copy(portletWorkPath + filename,
                        portletWorkPath + "index.html", true,
                        localRelativePath,
                        SWBPlatform.getWebWorkPath() + resource.getWorkPath()
                        + "/"
                        + (version.getVersionNumber() > 1 ? version.getVersionNumber() : 1)
                        + "/tmp/images");
            } else {
                SWBUtils.IO.copy(portletWorkPath + filename,
                        portletWorkPath + "index.html", false, "", "");
            }
//            file = new File(portletWorkPath + filename);
//            //borra el archivo con el nombre original
//            file.delete();
        } catch (Exception e) {
            log.debug(e);
        }
        
        PrintWriter out = response.getWriter();
        StringBuffer bs = new StringBuffer(700);

        bs.append("\n<html>");
        bs.append("\n<head>");
        bs.append("\n<script type=\"text/javascript\">");
        bs.append("\n  function searchForm() {");
        bs.append("\n      for (var i = 0; i < top.frames.length; i++) {");
        bs.append("\n          var forma = top.frames[i].document.getElementById('newFileForm'); ");
        bs.append("\n          if (forma != undefined) {");
        bs.append("\n              top.frames[i].closeAndReload(window.parent,\""
                + localRelativePath + "\");");
        bs.append("\n          }");
        bs.append("\n      }");
        bs.append("\n  }");
        bs.append("\n");
        bs.append("\n  var button = window.parent.document.getElementById(\"PopupButtons\");");
        bs.append("\n  //button.value = \"Cerrar ventana\";");
        bs.append("\n  var cad = '<div align=\"right\">'");
        bs.append("\n          + '<input id=\"btnCancel\" class=\"Button\" type=\"button\" fcklang=\"DlgBtnCancel\" onclick=\"window.frames[\\'frmMain\\'].searchForm();\" value=\"Mostrar archivo cargado\"/>'");
        bs.append("\n          + '</div>'; ");
        bs.append("\n  button.innerHTML = cad;");
        bs.append("\n  ");
        bs.append("\n  var closeButton = window.parent.document.getElementById(\"closeButton\");");
        bs.append("\n  closeButton.onclick = \"window.frames[\\'frmMain\\'].searchForm();\"");
        bs.append("\n  ");
        bs.append("\n</script>");
        bs.append("\n</head>");
        bs.append("\n");
        bs.append("\n<body>");
        bs.append("\n  <APPLET WIDTH=\"100%\" HEIGHT=\"100%\" CODE=\"applets.dragdrop.DragDrop.class\" codebase=\""
                + SWBPlatform.getContextPath()
                + "\" archive=\"swbadmin/lib/DragDrop.jar, swbadmin/lib/WBCommons.jar\" border=\"0\">");
        bs.append("\n  <PARAM NAME=\"webpath\" VALUE=\""
                + SWBPlatform.getContextPath() + "/\">");
        bs.append("\n  <PARAM NAME=\"foreground\" VALUE=\"000000\">");
        bs.append("\n  <PARAM NAME=\"background\" VALUE=\"979FC3\">");
        bs.append("\n  <PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\">");
        bs.append("\n  <PARAM NAME=\"backgroundSelection\" VALUE=\"666699\">");
        bs.append("\n  <PARAM NAME=\"path\" value=\"" + portletWorkPath + "images/\">");
        bs.append("\n  <PARAM NAME=\"clientpath\" value=\"" + clientFilePath + "\">");
        bs.append("\n  <PARAM NAME=\"files\" value=\"" + strAttaches + "\">");
        bs.append("\n  </APPLET>");
        bs.append("\n</body>");
        bs.append("\n</html>");
        bs.append("\n");
        out.println(bs.toString());
    }
    
    /**
     * Muestra la interfaz para que el usuario seleccione un archivo de la m&aacute;quina
     * cliente y lo env&iacute;e al servidor.
     * @param request
     * @param response
     * @param paramRequest
     */
    private void selectFileInterface(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws IOException {
        
        StringBuffer output = new StringBuffer(800);
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(url.Call_DIRECT);
        url.setMode("uploadNewVersion");
        
        output.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
        output.append("\n<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        output.append("\n<head>");
        output.append("\n	<title>Upload Main File</title>");
        output.append("\n	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        output.append("\n	<meta name=\"robots\" content=\"noindex, nofollow\" />");
        output.append("\n	<script src=\"/swb/swbadmin/js/fckeditor/editor/dialog/common/fck_dialog_common.js\" type=\"text/javascript\"></script>");
        output.append("\n	<script type=\"text/javascript\">");
        output.append("\n	  var dialog = window.parent ;");
        output.append("\n	  var oEditor = dialog.InnerDialogLoaded() ;");
        output.append("\n	  var FCK = oEditor.FCK ;");
        output.append("\n	  var FCKBrowserInfo = oEditor.FCKBrowserInfo ;");
        output.append("\n	  var FCKTools = oEditor.FCKTools ;");
        output.append("\n	  var FCKRegexLib = oEditor.FCKRegexLib ;");
        output.append("\n	  var FCKLang = oEditor.FCKLang ;");
        output.append("\n	  var FCKConfig = oEditor.FCKConfig ;");
        output.append("\n	  var FCKDebug = oEditor.FCKDebug ;");
        output.append("\n	  var oDOM = FCK.EditorDocument ;");
        output.append("\n	  document.write( FCKTools.GetStyleHtml( GetCommonDialogCss() ) ) ;");
        output.append("\n	  //#### Regular Expressions library.");
        output.append("\n	  var oRegex = new Object() ;");
        output.append("\n	  oRegex.UriProtocol = /^(((http|https|ftp|news):\\/\\/)|mailto:)/gi ;");
        output.append("\n	  oRegex.UrlOnChangeProtocol = /^(http|https|ftp|news):\\/\\/(?=.)/gi ;");
        output.append("\n	  oRegex.UrlOnChangeTestOther = /^((javascript:)|[#\\/\\.])/gi ;");
        output.append("\n	  oRegex.ReserveTarget = /^_(blank|self|top|parent)$/i ;");
        output.append("\n	  var oParser = new Object() ;");
        output.append("\n// This method simply returns the two inputs in numerical order. You can even");
        output.append("\n// provide strings, as the method would parseInt() the values.");
        output.append("\n	  oParser.SortNumerical = function(a, b) {");
        output.append("\n	    return parseInt( a, 10 ) - parseInt( b, 10 ) ;");
        output.append("\n	  }");
        output.append("\n	window.onload = function() {");
        output.append("\n	// First of all, translate the dialog box texts");
        output.append("\n	oEditor.FCKLanguageManager.TranslatePage(document) ;");
        output.append("\n	// Show/Hide the \"Browse Server\" button.");
        output.append("\n	//GetE('divBrowseServer').style.display = FCKConfig.LinkBrowser ? '' : 'none' ;");
        output.append("\n	// Show the initial dialog content.");
        output.append("\n	GetE('divUpload').style.display = '' ;");
        output.append("\n	// Activate the \"OK\" button.");
        output.append("\n	dialog.SetOkButton( true ) ;");
        output.append("\n       window.parent.SetAutoSize( true ) ;");
        output.append("\n}");
        output.append("\n//#### The OK button was hit.");
        output.append("\nfunction Ok() {");
        output.append("\n	sUri = GetE('txtUploadFile').value ;");
        output.append("\n	if ( sUri.length == 0 ) {");
        output.append("\n	    alert( FCKLang.DlnLnkMsgNoUrl ) ;");
        output.append("\n	    return false ;");
        output.append("\n	}");
        output.append("\n       return true;");
        output.append("\n}");
        output.append("\nfunction BrowseServer() {");
        output.append("\n	OpenFileBrowser( FCKConfig.LinkBrowserURL, FCKConfig.LinkBrowserWindowWidth, FCKConfig.LinkBrowserWindowHeight ) ;");
        output.append("\n}");
        output.append("\nfunction OnUploadCompleted( errorNumber, fileUrl, fileName, customMsg ) {");
        output.append("\n	// Remove animation");
        output.append("\n	window.parent.Throbber.Hide() ;");
        output.append("\n	GetE( 'divUpload' ).style.display  = '' ;");
        output.append("\n	switch ( errorNumber ) {");
        output.append("\n		case 0 :	// No errors");
        output.append("\n			alert( 'Your file has been successfully uploaded' ) ;");
        output.append("\n			break ;");
        output.append("\n		case 1 :	// Custom error");
        output.append("\n			alert( customMsg ) ;");
        output.append("\n			return ;");
        output.append("\n		case 101 :	// Custom warning");
        output.append("\n			alert( customMsg ) ;");
        output.append("\n			break ;");
        output.append("\n		case 201 :");
        output.append("\n			alert( 'A file with the same name is already available. The uploaded file has been renamed to \"' + fileName + '\"' ) ;");
        output.append("\n			break ;");
        output.append("\n		case 202 :");
        output.append("\n			alert( 'Invalid file type' ) ;");
        output.append("\n			return ;");
        output.append("\n		case 203 :");
        output.append("\n			alert( \"Security error. You probably don't have enough permissions to upload. Please check your server.\" ) ;");
        output.append("\n			return ;");
        output.append("\n		case 500 :");
        output.append("\n			alert( 'The connector is disabled' ) ;");
        output.append("\n			break ;");
        output.append("\n		default :");
        output.append("\n			alert( 'Error on file upload. Error number: ' + errorNumber ) ;");
        output.append("\n			return ;");
        output.append("\n	}");
        output.append("\n	GetE('frmUpload').reset() ;");
        output.append("\n}");
        output.append("\nvar oUploadAllowedExtRegex	= new RegExp( FCKConfig.LinkUploadAllowedExtensions, 'i' ) ;");
        output.append("\nvar oUploadDeniedExtRegex	= new RegExp( FCKConfig.LinkUploadDeniedExtensions, 'i' ) ;");
        output.append("\nfunction CheckUpload() {");
        output.append("\n	var sFile = GetE('txtUploadFile').value ;");
        output.append("\n	if ( sFile.length == 0 ) {");
        output.append("\n		alert( 'Please select a file to upload' ) ;");
        output.append("\n		return false ;");
        output.append("\n	}");
        output.append("\n	if ( ( FCKConfig.LinkUploadAllowedExtensions.length > 0 && !oUploadAllowedExtRegex.test( sFile ) ) ||");
        output.append("\n		( FCKConfig.LinkUploadDeniedExtensions.length > 0 && oUploadDeniedExtRegex.test( sFile ) ) ) {");
        output.append("\n                Cancel();");
        output.append("\n		return false ;");
        output.append("\n	}");
        output.append("\n	// Show animation");
        output.append("\n	window.parent.Throbber.Show( 100 ) ;");
        output.append("\n	GetE( 'divUpload' ).style.display  = 'none' ;");
        output.append("\n	return true ;");
        output.append("\n}");
        output.append("\n        function fillHiddenPath() { ");
        output.append("\n          var localName = document.frmUpload.NewFile.value; ");
        output.append("\n          document.frmUpload.hiddenPath.value = localName.substring(0, localName.lastIndexOf(\"\\\\\"));");
        output.append("\n        } ");
        output.append("\n    function isOk() {");
        output.append("\n	if (Ok()) {");
        output.append("\n	    document.frmUpload.submit();");
        output.append("\n	}");
        output.append("\n    }");
        output.append("\n	</script>");
        output.append("\n</head>");
        output.append("\n<body scroll=\"no\" style=\"overflow: hidden\">");
        output.append("\n    <div id=\"divUpload\" style=\"display: none\">");
        output.append("\n        <form id=\"frmUpload\" name=\"frmUpload\" method=\"post\" enctype=\"multipart/form-data\" onsubmit=\"Ok();\" ");
        output.append("\n            action=\"" + url.toString() + "\">");
        output.append("\n            <span fcklang=\"DlgLnkUpload\">Upload</span><br />");
        output.append("\n            <input id=\"txtUploadFile\" style=\"width: 100%\" type=\"file\" size=\"40\" name=\"NewFile\" onchange=\"fillHiddenPath();\" /><br />");
        output.append("\n            <input id=\"hiddenPath\" type=\"hidden\" name=\"hiddenPath\" />");
        output.append("\n            <br />");
        output.append("\n            <input id=\"btnUpload\" type=\"button\" value=\"Send it to the Server\" fcklang=\"DlgLnkBtnUpload\" onclick=\"isOk();\"/>");
        output.append("\n        </form>");
        output.append("\n    </div>");
        output.append("\n</body>");
        output.append("\n</html>");
        output.append("\n");
        
        response.getWriter().println(output.toString());
        
    }
    
}
