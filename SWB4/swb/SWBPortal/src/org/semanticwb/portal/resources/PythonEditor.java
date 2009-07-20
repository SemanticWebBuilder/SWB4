package org.semanticwb.portal.resources;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/**
 * Muestra al usuario las facilidades para editar el codigo, en Python, de un archivo 
 * que se ejecutar&aacute; cuando se muestre en el navegador la secci&oacut;n con que
 * se relaciona este recurso.
 * @author jose.jimenez
 */
public class PythonEditor extends GenericAdmResource {

    /** Objeto encargado de crear mensajes en los archivos log de SWB */
    private static final Logger log = SWBUtils.getLogger(PythonEditor.class);

    /** Constante que define la acci&oacute;n para guardar el contenido del archivo Python*/
    private static final String ACTION_SAVE = "savecode";

    /**
     * Muestra la vista de edicion del c&oacute;digo en Python del archivo relacionado a la secci&oacute;n
     * en que se da de alta este recurso.
     * @param request
     * @param response
     * @param paramReq
     * @throws java.io.IOException
     * @throws org.semanticwb.portal.api.SWBResourceException
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramReq)
            throws IOException, SWBResourceException {

        StringBuffer ret = new StringBuffer(1000);
        String language = paramReq.getUser().getLanguage();
        Resource base = paramReq.getResourceBase();
        String code = "";
        String resourcePath = base.getWorkPath();
        String fileName = base.getAttribute("fileName");
        File pathToRead = new File(SWBPlatform.getWorkPath() + resourcePath);
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramReq.getActionUrl().setMode(
                SWBResourceURL.Mode_ADMIN);
        String action = paramReq.getAction();
        String context = SWBPlatform.getContextPath();
        boolean empty = false;

        //Uso del lenguaje del usuario en cuestion
        language = (!language.equalsIgnoreCase("en") &&
                !language.equalsIgnoreCase("es")
                ? "es" : language.toLowerCase());

        try {
            if (!pathToRead.exists()) {
                empty = true;
                pathToRead.mkdirs();
                pathToRead = new File(SWBPlatform.getWorkPath() + resourcePath
                        + "/index.html");
                if (!pathToRead.exists()) {
                    pathToRead.createNewFile();
                }
            }
            //lectura del archivo que contiene el codigo
            if (fileName != null) {
                pathToRead = new File(SWBPlatform.getWorkPath() + resourcePath
                        + "/" + fileName);
                resourcePath = resourcePath + "/" + fileName;
                if (pathToRead.canRead()) {
                    code = SWBUtils.IO.readInputStream(
                            SWBPlatform.getFileFromWorkPath(resourcePath));
                } else {
                    log.debug("Cannot read file " + pathToRead.toString()
                            + " in PythonEditor");
                }
            }
        } catch (Exception e) {
            if (!empty) {
                code = paramReq.getLocaleLogString("MsgReadingContent");
            } else {
                code = "";
            }
            log.debug("Creating or reading directory structure.", e);
            e.printStackTrace();
        }

        url.setAction(PythonEditor.ACTION_SAVE);
        //Presentacion de la interfaz de captura del codigo
        ret.append("\n<script language=\"javascript\" type=\"text/javascript\" charset=\"UTF-8\" src=\""
                + context + "/swbadmin/js/editarea/edit_area/edit_area_full.js\"></script>\n");
        ret.append("\n<script language=\"javascript\" type=\"text/javascript\" charset=\"UTF-8\">\n");
        ret.append("\neditAreaLoader.init({\n");
        ret.append("\n    id : \"PythonEditor" + base.getId() + "\"\n");
        ret.append("\n    ,language: \"" + language + "\"\n");
        ret.append("\n    ,syntax: \"python\"\n");
        ret.append("\n    ,start_highlight: true\n");
        ret.append("\n    ,toolbar: \"search, go_to_line,");
        ret.append(" |, undo, redo, |, select_font,|, change_smooth_selection,");
        ret.append(" highlight, reset_highlight, |, help\"\n");
        ret.append("\n    ,is_multi_files: false\n");
        ret.append("\n    ,allow_toggle: false\n");
        ret.append("\n});\n");
        ret.append("\n");
        ret.append("\n  function editorValidate(forma) {\n");
        ret.append("\n    if (this.area.textarea.value == \"\") {");
        ret.append("\n      alert(\"Debe capturar texto para almacenarlo\")");
        ret.append("\n      return false;");
        ret.append("\n    } else {");
        ret.append("\n      copyData(forma.id);");
        ret.append("\n      forma.submit();");
        ret.append("\n    }");
        ret.append("\n  }\n");
        ret.append("\n  function copyData(formid) {");
        ret.append("\n    var framesNames = \"\";");
        ret.append("\n    for (var i = 0; i < window.frames.length; i++) {");
        ret.append("\n        framesNames += window.frames[i].name;");
        ret.append("\n        if (framesNames && framesNames.indexOf(\"_PythonEditor\") != -1) {");
        ret.append("\n            id = framesNames.substr(6);");
        ret.append("\n            document.getElementById(id).value = this.area.textarea.value;");
        ret.append("\n            i = window.frames.length;");
        ret.append("\n        }");
        ret.append("\n    }");
        ret.append("\n    return;");
        ret.append("\n}");
        ret.append("\n</script>");
        ret.append("\n<div class=\"swbform\">");
        ret.append("\n  <form name=\"frmPythonEditor\" id=\"frmPythonEditor\" method=\"post\" action=\""
                + url.toString() + "\"> ");
        ret.append("\n    <fieldset> ");
        ret.append("\n      <legend>Edici&oacute;n del c&oacute;digo fuente</legend>");
        ret.append("\n      <textarea id=\"PythonEditor" + base.getId() + "\" name=\"PythonEditor"
                + base.getId() + "\" rows=\"25\"");
        ret.append(" cols=\"90\">" + code + "</textarea>\n");
        ret.append("\n      <br />");
        ret.append("\n    </fieldset>");
        ret.append("\n    <fieldset>");
        ret.append("\n      <input type=\"button\" value=\"Guardar\" onclick=\"if(editorValidate(this.form)) return true; else return false;\" />&nbsp;");
        ret.append("\n      <input type=\"reset\" value=\"Cancelar\" />");
        ret.append("\n    </fieldset>");
        ret.append("\n  </form> ");
        ret.append("\n</div>");

        //Muestra mensaje de almacenamiento de informacion
        if (action != null && action.equalsIgnoreCase(PythonEditor.ACTION_SAVE)) {
            String show = request.getParameter("_msg");
            if (show != null && !"null".equalsIgnoreCase(show)) {
                show = paramReq.getLocaleString("MsgStoreSuccess");
            } else {
                show = paramReq.getLocaleString("MsgStoreError");
            }
            String alert = "\n<script type=\"text/javascript\">"
                    + "\n   this.onload = function() {alert('" + show
                    + "');}" + "\n</script>";
            ret.append(alert);
        }
        out.println(ret.toString());
    }

    /**
     * Almacena el c&oacute;digo capturado en el editor en un archivo de file system
     * cuya ruta est&aacute; definida por el directorio de trabajo del recurso
     * y el nombre es tomado del c&oacute;digo capturado respetando la extensi&oacute;n
     * <quote>.py</quote>.
     * @param request
     * @param response
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response)
            throws SWBResourceException, IOException {

        System.out.println("Primera linea de processAction");
        String msg = null;
        Resource base = getResourceBase();
        String code = null;
        String fileNameInBase = base.getAttribute("fileName");
        String className = null;
        String resourcePath = SWBPlatform.getWorkPath() + base.getWorkPath();
        File pathToWrite = new File(resourcePath);
        boolean deleteFormerFile = false;

        code = request.getParameter("PythonEditor" + base.getId());

        if (code != null && !"".equals(code)) {
            className = getClassNameFromCode(code) + ".py";
            try {
                synchronized (code) {
                    //Revisa el valor del nombre del archivo a utilizar
                    if (fileNameInBase == null ||
                            (fileNameInBase != null &&
                             !fileNameInBase.equals(className))) {
                        //Si cambio el nombre, se eliminara el archivo anterior
                        if (fileNameInBase != null &&
                                !fileNameInBase.equals(className)) {
                            deleteFormerFile = true;
                        }
                        base.setAttribute("fileName", className);
                        base.updateAttributesToDB();
                    }
                    //Escribe el codigo en un archivo de file system
                    if (!pathToWrite.exists()) {
                        pathToWrite.mkdirs();
                    }
                    pathToWrite = new File(resourcePath + "/" + className);
                    System.out.println("pathToWrite.canWrite() en ruta (pathToWrite): " + pathToWrite + " - " + pathToWrite.canWrite());
                    FileWriter writer = new FileWriter(pathToWrite);
                    writer.write(code);
                    writer.flush();
                    writer.close();
                    if (deleteFormerFile) {
                        pathToWrite = new File(resourcePath + "/" + fileNameInBase);
                        if (pathToWrite.exists()) {
                            pathToWrite.delete();
                        }
                    }
                    msg = "ok";
                }
            } catch (Exception e) {
                log.error("Al escribir en disco.", e);
                e.printStackTrace();
            }
            response.setRenderParameter("_msg", msg);
        }
        System.out.println("Ultima linea de processAction");
    }

    /**
     * Realiza la llamada a ejecuci&oacute;n del c&oacute;digo capturado en el editor.
     * @param request
     * @param response
     * @param paramsRequest
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {

        String fileName = getResourceBase().getAttribute("fileName");
        if (fileName == null) {
            throw new IOException("Python file not specified in resource.");
        }
        String pathToRead = "/work" + getResourceBase().getWorkPath() + "/"
                + fileName;
        try {
            request.setAttribute("paramRequest", paramsRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(pathToRead);
            dispatcher.include(request, response);
        } catch (Exception e) {
            log.error("Process error... " + pathToRead, e);
        }
    }

    /**
     * Extrae de la cadena recibida, el nombre de la clase utilizado despu&eacute;s de
     * la palabra <quote>class</quote>.
     * @param code cadena a analizar para extraer el nombre de la clase
     * @return la palabra siguiente a <quote>class</quote>, detectada en la cadena recibida.
     */
    private String getClassNameFromCode(String code) {
        
        StringBuilder filename = new StringBuilder(50);
        //la palabra siguiente a "class" debera ser el nombre del archivo
        int index = code.indexOf("class");
        boolean found = false;
        boolean extractionStarted = false;

        if (index > -1) {
            index += 5;    //aumentar longitud de "class"
            while (!found || index >= code.length()) {
                if (code.charAt(index) != ' ' && code.charAt(index) != '\n') {
                    filename.append(code.charAt(index));
                    extractionStarted = true;
                } else if (extractionStarted) {
                    found = true;
                }
                index++;
            }
        }
        
        return filename.toString();
    }
}
