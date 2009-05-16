/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
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
 *
 * @author jose.jimenez
 */
public class HTMLContent extends GenericResource {

    
    private static Logger log = SWBUtils.getLogger(HTMLContent.class);

    
    /**
     * 
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
        
        System.out.println("action:" + paramRequest.getAction());
        Resource resource = paramRequest.getResourceBase();
        //String resourceWorkPath = "/work" + resource.getWorkPath() + "/";
        HttpSession session = request.getSession();
        VersionInfo versionInfo = new VersionInfo(resource.getSemanticObject());
        int versionNumber = versionInfo.getVersionNumber();
        String fileName = "index.html";  //base.getActualVersion().getVersionFile();
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
        
        /*
        VersionInfo version = new VersionInfo(resource.getSemanticObject());
        version.setVersionFile(filename);
        version.setVersionNumber(versionNumber);
        resource.setActualVersion(version);
        resource.setLastVersion(version);
        */
        pathToRead.append(versionNumber + "/");
        pathToRead.append(fileName);
        pathToWrite.append("" + (++versionNumber));
        session.setAttribute("directory", pathToWrite.toString());
        System.out.println("ruta para subir archivos:" + pathToWrite.toString());
        System.out.println("tmpPath a buscar:" + SWBPlatform.getWorkPath() + tmpPath);
        
        if (action.equals(SWBParamRequest.Action_EDIT)) {
            try {
                //Cuando se carga el archivo normalmente
                if (tmpPath == null || "".equals(tmpPath)) {
                    System.out.println("Carga normal.");
                    content = SWBUtils.IO.readInputStream(
                            SWBPlatform.getFileFromWorkPath(pathToRead.toString()));
                } else { //cuando se carga el archivo temporal
                    System.out.println("Carga desde tmpPath: " + SWBPlatform.getWorkPath() + tmpPath);
                    content = SWBUtils.IO.readInputStream(
                            SWBPlatform.getFileFromWorkPath(tmpPath));
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
        System.out.println("resourceWorkPath:" + resourceWorkPath
                + "\nversionNumber" + versionNumber
                + "\nfileName" + fileName);
        response.getWriter().println(SWBUtils.IO.getFileFromPath(resourceWorkPath));
    }

    @Override
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {
        System.out.println("paramRequest.getMode():" + paramRequest.getMode());
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
     * Almacena en un archivo con extensión html el contenido mostrado en el 
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
        String filename = null;
        boolean textSaved = false;
        VersionInfo version = new VersionInfo(resource.getSemanticObject());
        int versionNumber = version.getVersionNumber();
        
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
                writer.write(textToSave);
                writer.flush();
                writer.close();
                version.setVersionFile(filename);
                version.setVersionNumber(versionNumber);
                System.out.println("VersionNumber:" + version.getVersionNumber());
                //TODO: Revisar si esto es correcto. Forzosamente se debe fijar
                //la editada como la actual?
                //resource.setActualVersion(version);
                //resource.setLastVersion(version);
                textSaved = true;
            } catch (Exception e) {
                log.error("Al escribir el archivo", e);
            }
        }
        PrintWriter out = response.getWriter();
        if (textSaved) {
            out.println("El contenido ha sido guardado exitosamente");
        } else {
            out.println("Se produjo un error al almacenar la información, intente de nuevo.");
            //out.println("<br><a href=\"javascript:history.back();\">Regresar</a>");
        }
        out.close();
    }
    
    /**
     * Carga un archivo al file system del servidor cuyo contenido sustituirá al
     * contenido de este recurso, creando una nueva versión de este recurso.
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
        //request.getSession().setAttribute("directory", null);
        String clientFilePath = "";
        String filename = null;
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
        String portletWorkPath = SWBPlatform.getWorkPath()
                + resource.getWorkPath() + "/" 
                + (version.getVersionNumber() > 1
                   ? version.getVersionNumber() - 1 : 1)
                + "/tmp/";
        File file = new File(portletWorkPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        fUpload.saveFile("NewFile", portletWorkPath);
        String strAttaches = fUpload.FindAttaches("NewFile");
        file = new File(portletWorkPath + "images/");
        if (!file.exists()) {
            file.mkdir();
        }
        
        //Renombrar el nuevo archivo
        try {
            file = new File(portletWorkPath + filename);
            file.renameTo(new File(portletWorkPath + "index.html"));
            //borra el archivo con el nombre original
            file.delete();
        } catch (Exception e) {
            log.debug(e);
        }
        
        //try {
            // Se elimina de una de las dos rutas el directorio -work-, ya que las dos lo tienen.
            /*
            Iterator<FileItem> files = SWBUtils.IO.fileUpload(request,
                    SWBPlatform.getWorkPath() + resource.getWorkPath() + "/"
                    + version.getVersionNumber());
            if (files.hasNext()) {
                FileItem oneFile = files.next();
                System.out.println("oneFile.getName(): " + oneFile.getName());
                fileContent = oneFile.getString();
                oneFile.delete();*/
                request.setAttribute("clientFilePath", clientFilePath);
                request.setAttribute("strAttaches", strAttaches);
                request.setAttribute("fileContent", fileContent);
                request.setAttribute("showApplet", "yes");
            //}
            // Se comenta para hacer una prueba de llamado directo al applet
            //doAdmin(request, response, paramRequest);
            PrintWriter out = response.getWriter();
            StringBuffer bs = new StringBuffer(700);

            bs.append("\n<html>");
            bs.append("\n<head>");
            bs.append("\n<script type=\"text/javascript\">");
            bs.append("\n  //alert(this.location + ' - ' + parent.parent.location);");
            //ejecutar submit de una nueva forma (en la JSP) con un hidden que indique se muestre 
            //el doAdmin para recuperar los datos del directorio tmp.
            bs.append("\nfunction createFunction() { ");
            bs.append("\n  var vwrite= \"function findForms() {");
            bs.append("\\n  var top = window.parent.frames;");
            bs.append("\\n  for (var fr1 = 0; fr1 < top.length; fr1++) {");
//            bs.append("\\n    alert(\\'Primer nivel: \\' + top[fr1].src + \\' - \\' + top[fr1].document.URL);");
            bs.append("\\n    if (top[fr1].document.forms != undefined) {");
            bs.append("\\n        var subtop = top[fr1].document.forms;");
            bs.append("\\n        for (var fr2 = 0; fr2 < subtop.length; fr2++) {");
//            bs.append("\\n            alert(subtop[fr2].name);");
            bs.append("\\n            if (subtop[fr2].name == 'newFileForm' && subtop[fr2].id == 'newFileForm') {");
            bs.append("\\n                subtop[fr2].submit();");
            bs.append("\\n                break;");
            bs.append("\\n            }");
            //bs.append("            alert(\\'Segundo nivel: \\' + subtop[fr2].name + \\' - \\' + subtop[fr2].action);");
            bs.append("\\n        }");
//            bs.append("\\n    } else {");
//            bs.append("\\n        alert(\\'Primer nivel: Sin forms dentro\\');");
            bs.append("\\n    }");
            bs.append("\\n  } ");
            bs.append("\\n}\";");
            
            bs.append("\nvar head = window.parent.document.getElementsByTagName('head')[0];");
            bs.append("\nif (head) {");
            bs.append("\n  var vscript = window.parent.document.createElement('script');");
            bs.append("\n  vscript.id = 'scriptToCreateFunction';");
            bs.append("\n  vscript.type = 'text/javascript';");
            bs.append("\n  vscript.innerHTML = vwrite;");
            bs.append("\n  head.appendChild(vscript);");
            bs.append("\n}");
            
            //bs.append("\nwindow.parent.document.write(vwrite);");
            bs.append("\n  var button = window.parent.document.getElementById(\"PopupButtons\");");
            bs.append("\n  button.value = \"Cerrar ventana\";");
            bs.append("\n  //alert(window.parent.document.getElementById(\"btnCancel\").onclick);");
            bs.append("\n  var cad = '<div align=\"right\">'");
            //bs.append("\n          + '<input id=\"btnCancel\" class=\"Button\" type=\"button\" fcklang=\"DlgBtnCancel\" onclick=\"parent.document.getElementById(\\'newFileForm\\').submit();Cancel();\" value=\"Cerrar ventana\"/>'");
            bs.append("\n          + '<input id=\"btnCancel\" class=\"Button\" type=\"button\" fcklang=\"DlgBtnCancel\" onclick=\"findForms();Cancel();\" value=\"Cerrar ventana\"/>'");
            bs.append("\n          + '</div>'; ");
            bs.append("\n  button.innerHTML = cad;");
/*            bs.append("\n  var s = window.parent.parent.frames; ");
            bs.append("\n  for (var x = 0; x < s.length; x++) { ");
            bs.append("\n    alert(s[x].src);");
            bs.append("\n    if (s[x].document.forms != undefined) ");
            bs.append("\n       for (var y = 0; y < s.document.forms.length; s++) ");
            bs.append("\n         alert('Forma: ' + s.document.forms[y].action)");
            bs.append("\n    //alert(s.src);");
            bs.append("\n  } ");
            bs.append("\n  alert('Done!');");
            bs.append("\n  ");*/
            bs.append("\n}  ");
            bs.append("\n</script>");
            bs.append("\n</head>");
            bs.append("\n");  // onload=\"createFunction();\"
            bs.append("\n<body onload=\"createFunction();\">");
            bs.append("\n  <APPLET WIDTH=\"100%\" HEIGHT=\"100%\" CODE=\"applets.dragdrop.DragDrop.class\" codebase=\"" + SWBPlatform.getContextPath() + "\" archive=\"swbadmin/lib/DragDrop.jar, swbadmin/lib/WBCommons.jar\" border=\"0\">");
            bs.append("\n  <PARAM NAME=\"webpath\" VALUE=\"" + SWBPlatform.getContextPath() + "/\">");
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
//            bs.append("\n</div>");
            out.println(bs.toString());
            /*
        } catch (IOException ioe) {
            response.getWriter().println("Se produjo un error al leer el archivo."
                    + "Por favor intente de nuevo, "
                    + "<a href=\"history.go(-1);\" >Regresar</a>");
            log.debug(ioe);
        } catch (SWBResourceException swbre) {
            response.getWriter().println("Se produjo un error al presentar el "
                    + "contenido del archivo seleccionado. Por favor intente de "
                    + "nuevo, <a href=\"history.go(-1);\" >Regresar</a>");
            log.debug(swbre);
        }*/
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
/*        output.append("\nfunction changeButtons() { ");
        output.append("\n   ");
        output.append("\n  var button = window.parent.document.getElementById(\"btnCancel\");");
        output.append("\n  button.onclick = \"Cancel();parent.document.getElementById('newFileForm').submit();\";"); 
        output.append("\n  button.value = \"Cerrar ventana\";");
        output.append("\n  alert(window.parent.document.getElementById(\"btnCancel\").onclick);");
  
        output.append("\n}   "); */
        output.append("\n        function fillHiddenPath() { ");
        output.append("\n          var localName = document.frmUpload.NewFile.value; ");
        output.append("\n          document.frmUpload.hiddenPath.value = localName.substring(0, localName.lastIndexOf(\"\\\\\"));");
        output.append("\n          alert('hiddenPath: ' + document.frmUpload.hiddenPath.value + '\\nNewFile: ' + document.frmUpload.NewFile.value); ");
        output.append("\n        } ");
        output.append("\n	</script>");
        output.append("\n</head>");
        output.append("\n<body scroll=\"no\" style=\"overflow: hidden\">");
        output.append("\n    <div id=\"divUpload\" style=\"display: none\">");
        output.append("\n        <form id=\"frmUpload\" name=\"frmUpload\" method=\"post\" enctype=\"multipart/form-data\" ");
        output.append("\n            action=\"" + url.toString() + "\">");   // target=\"_parent\"
        output.append("\n            <span fcklang=\"DlgLnkUpload\">Upload</span><br />");
        output.append("\n            <input id=\"txtUploadFile\" style=\"width: 100%\" type=\"file\" size=\"40\" name=\"NewFile\" onchange=\"fillHiddenPath();\" /><br />");
        output.append("\n            <input id=\"hiddenPath\" type=\"hidden\" name=\"hiddenPath\" />");
        output.append("\n            <br />");
        output.append("\n            <input id=\"btnUpload\" type=\"submit\" value=\"Send it to the Server\" fcklang=\"DlgLnkBtnUpload\" />");
        output.append("\n            <!--script type=\"text/javascript\">");
        output.append("\n                document.write( '<iframe name=\"UploadWindow\" style=\"display: none\" src=\"' + FCKTools.GetVoidUrl() + '\"></iframe>' ) ;");
        output.append("\n            </script-->");
        output.append("\n        </form>");
        output.append("\n	<!--div id=\"divBrowseServer\" style=\"display: none\">");
        output.append("\n            <input type=\"button\" value=\"Browse Server\" fckLang=\"DlgBtnBrowseServer\" onclick=\"BrowseServer();\" />");
        output.append("\n	</div-->");
        output.append("\n    </div>");
        output.append("\n</body>");
        output.append("\n</html>");
        output.append("\n");
        
        response.getWriter().println(output.toString());
        
    }
    
}
