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
 * Muestra al usuario las facilidades para editar el codigo en Groovy de un archivo
 * que se ejecutar&aacute; cuando se muestre en el navegador la seccion con que
 * se relaciona este recurso. Este recurso no es un compilador.
 * @author jose.jimenez
 */
public class GroovyEditor extends GenericAdmResource {

    /** Objeto encargado de crear mensajes en los archivos log de SWB. */
    private static final Logger log = SWBUtils.getLogger(GroovyEditor.class);

    /** Constante que define la acci&oacute;n para guardar el c&oacute;digo en un archivo.*/
    private static final String ACTION_SAVE = "savecode";

    /**
     * Muestra la vista de edici&oacute;n del c&oacute;digo en Groovy, del archivo relacionado a la secci&oacute;n
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
                            + " in GroovyEditor");
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

        url.setAction(GroovyEditor.ACTION_SAVE);
        //Presentacion de la interfaz de captura del codigo
        ret.append("\n<script language=\"javascript\" type=\"text/javascript\" charset=\"UTF-8\" src=\""
                + context + "/swbadmin/js/editarea/edit_area/edit_area_full.js\"></script>\n");
        ret.append("\n<script language=\"javascript\" type=\"text/javascript\" charset=\"UTF-8\">\n");
        ret.append("\neditAreaLoader.init({\n");
        ret.append("\n    id : \"GroovyEditor" + base.getId() + "\"\n");
        ret.append("\n    ,language: \"" + language + "\"\n");
        ret.append("\n    ,syntax: \"groovy\"\n");
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
        ret.append("\n        if (framesNames && framesNames.indexOf(\"_GroovyEditor\") != -1) {");
        ret.append("\n            id = framesNames.substr(6);");
        ret.append("\n            document.getElementById(id).value = this.area.textarea.value;");
        ret.append("\n            i = window.frames.length;");
        ret.append("\n        }");
        ret.append("\n    }");
        ret.append("\n    return;");
        ret.append("\n}");
        ret.append("\n</script>");
        ret.append("\n<div class=\"swbform\">");
        ret.append("\n  <form name=\"frmGroovyEditor\" id=\"frmGroovyEditor\" method=\"post\" action=\""
                + url.toString() + "\"> ");
        ret.append("\n    <fieldset> ");
        ret.append("\n      <legend>Edici&oacute;n del c&oacute;digo fuente</legend>");
        ret.append("\n      <textarea id=\"GroovyEditor" + base.getId() + "\" name=\"GroovyEditor"
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
        if (action != null && action.equalsIgnoreCase(GroovyEditor.ACTION_SAVE)) {
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
     * y el nombre es generado aleatoriamente respetando la extensi&oacute;n <quote>.groovy</quote>.
     * @param request
     * @param response
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response)
            throws SWBResourceException, IOException {

        String msg = null;
        Resource base = getResourceBase();
        String code = null;
        String fileName = base.getAttribute("fileName");
        String resourcePath = SWBPlatform.getWorkPath() + base.getWorkPath();
        File pathToWrite = new File(resourcePath);

        code = request.getParameter("GroovyEditor" + base.getId());
        if (code != null && !"".equals(code)) {
            try {
                if (fileName == null) {
                    fileName = generateRandomString(10) + ".groovy";
                    base.setAttribute("fileName", fileName);
                    base.updateAttributesToDB();
                }
                //Escribe el codigo en un archivo de file system
                synchronized (code) {
                    if (!pathToWrite.exists()) {
                        pathToWrite.mkdirs();
                    }
                    pathToWrite = new File(resourcePath + "/" + fileName);
                    FileWriter writer = new FileWriter(pathToWrite);
                    writer.write(code);
                    writer.flush();
                    writer.close();
                    msg = "ok";
                }
            } catch (Exception e) {
                log.error(e);
            }
            response.setRenderParameter("_msg", msg);
        }
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
            throw new IOException("Groovy file not specified in resource.");
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
     * Genera una cadena de <quote>length</quote> caracteres alfanum&eacute;ricos,
     * incluyendo gui&oacute;n bajo, de forma aleatoria.
     * @param length N&uacute;mero de caracteres a seleccionar para formar la cadena a devolver.
     *               Si el valor recibido es menor o igual a cero, se asigna 8 a esta variable.
     * @return Una cadena de <quote>length</quote> (u 8, si length es menor o igual a cero)
     *          caracteres de longitud seleccionados de forma aleatoria.
     */
    private String generateRandomString(int length) {

        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                          'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                          'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
                          '7', '8', '9', '_', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                          'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                          'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        if (length <= 0) {
            length = 8;
        }
        char[] generated = new char[length];

        for (int i = 0; i < length; i++) {
            int j = (int) (Math.random() * letters.length);
            generated[i] = letters[j];
        }
        return new String(generated);
    }
}
