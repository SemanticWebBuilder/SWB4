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


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


// TODO: Auto-generated Javadoc
/**
 * Muestra las facilidades para editar el codigo en Python que se ejecutar&aacute;
 * cuando se muestre en el navegador la secci&oacute;n con que se relaciona este
 * recurso. Este recurso no es un compilador.
 * <p>Shows the interface to edit Python code that will be executed when the
 * web page associated to this resource is shown. This resource is not a compiler.</p>
 */
public class PythonEditor extends GenericAdmResource {


    /**
     * objeto encargado de crear mensajes en los archivos log de SemanticWebBuilder (SWB).
     * <p>object that creates messages in SWB's log file.</p>
     */
    private static final Logger log = SWBUtils.getLogger(PythonEditor.class);

    /**
     * Constante que define la acci&oacute;n para guardar el c&oacute;digo en un
     * archivo. <p>defines de action to store the edited code in a file.</p>
     */
    private static final String ACTION_SAVE = "savecode";

    
    /**
     * Muestra la vista de edici&oacute;n del c&oacute;digo, almecenado en el
     * archivo relacionado a la secci&oacute;n en que se da de alta este recurso.
     * <p>Shows the edition view of the code stored in a file related to the section
     * this resource is associated to.</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramReq el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @throws java.io.IOException al obtener el <code>Writer</code> del <code>response</code> correspondiente.
     * when getting the corresponding <code>response</code>'s <code>Writer</code>.
     * @throws org.semanticwb.portal.api.SWBResourceException si no existe el archivo de mensajes del idioma utilizado.
     * <p>if there is no file message of the corresponding language.</p>
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
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
        File pathToRead = new File(SWBPortal.getWorkPath() + resourcePath);
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
                pathToRead = new File(SWBPortal.getWorkPath() + resourcePath
                        + "/index.html");
                if (!pathToRead.exists()) {
                    pathToRead.createNewFile();
                }
            }
            //lectura del archivo que contiene el codigo
            if (fileName != null) {
                pathToRead = new File(SWBPortal.getWorkPath() + resourcePath
                        + "/" + fileName);
                resourcePath = resourcePath + "/" + fileName;
                if (pathToRead.canRead()) {
                    code = SWBUtils.IO.readInputStream(
                            SWBPortal.getFileFromWorkPath(resourcePath));
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
        ret.append("\n      alert(\"" + paramReq.getLocaleString("MsgOnSubmit")
                + "\")");
        ret.append("\n      return false;");
        ret.append("\n    }");
        ret.append("\n    return true;");
        ret.append("\n  }\n");
        ret.append("\n</script>");
        ret.append("\n<div class=\"swbform\">");
        ret.append("\n  <form name=\"frmPythonEditor\" id=\"frmPythonEditor\" method=\"post\" onSubmit=\"return editorValidate(this);\" action=\""
                + url.toString() + "\"> ");
        ret.append("\n    <fieldset> ");
        ret.append("\n      <legend>" + paramReq.getLocaleString("LblPageTitle")
                + "</legend>");
        ret.append("\n      <textarea id=\"PythonEditor" + base.getId() + "\" name=\"PythonEditor"
                + base.getId() + "\" rows=\"25\"");
        ret.append(" cols=\"90\">" + code + "</textarea>\n");
        ret.append("\n      <br />");
        ret.append("\n    </fieldset>");
        ret.append("\n    <fieldset>");
        ret.append("\n      <input type=\"submit\" value=\"Guardar\" />&nbsp;");
        ret.append("\n      <input type=\"reset\" value=\"Cancelar\" />");
        ret.append("\n    </fieldset>");
        ret.append("\n  </form> ");
        ret.append("\n</div>");

        //Muestra mensaje de almacenamiento de informacion
        if (action != null && action.equalsIgnoreCase(PythonEditor.ACTION_SAVE)) {
            String show = request.getParameter("_msg");
            if (show != null && !"null".equalsIgnoreCase(show)) {
                String alert = "\n<script type=\"text/javascript\">"
                        + "\n   this.onload = function() {alert('"
                        + paramReq.getLocaleString("MsgStoreError")
                        + "');}" + "\n</script>";
                ret.append(alert);
            }
        }
        out.println(ret.toString());
    }

    /**
     * Almacena el c&oacute;digo capturado en el editor en un archivo f&iacute;sico
     * cuya ruta est&aacute; definida por el directorio de trabajo del recurso
     * y el nombre es generado aleatoriamente respetando la extensi&oacute;n <quote>.php</quote>.
     * <p>Stores the code, typed in in the editor, in a file which path is defined
     * by this resource's work directory and its name is a randomly generated
     * string with the extension <quote>.php</quote></p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the
     * user's HTTP request</p>
     * @param response la respuesta a la acci&oacute;n solicitada por el usuario
     * <p>the response to the action requested by the user.</p>
     * @throws java.io.IOException si hay alg&uacute;n problema mientras se escribe el
     * c&oacute;digo en el archivo.
     * <p>if there is a problem while writing the code in the file.</p>
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response)
            throws SWBResourceException, IOException {

        String msg = null;
        Resource base = getResourceBase();
        String code = null;
        String fileNameInBase = base.getAttribute("fileName");
        String className = null;
        String resourcePath = SWBPortal.getWorkPath() + base.getWorkPath();
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
                }
            } catch (Exception e) {
                msg = "not ok";
                log.error("Al escribir en disco.", e);
            }
            response.setRenderParameter("_msg", msg);
        }
    }

    /**
     * Realiza la llamada a ejecuci&oacute;n del c&oacute;digo capturado en el editor.
     * <p>Performs the execution call of the code typed in in the editor.</p>
     * 
     * @param request la petici&oacute;n HTTP generada por el usuario. <p>the user's HTTP request</p>
     * @param response la respuesta hacia el usuario.<p>the response to the user</p>
     * @param paramsRequest el objeto generado por SWB y asociado a la petici&oacute;n
     * del usuario.<p>the object gnerated by SWB and asociated to the user's request</p>
     * @throws java.io.IOException si este recurso no tiene asociado el archivo
     * que se crea cuando se edita el c&oacute;digo. <p>if this resource
     * has no file, which is created after editing code, associated</p>
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
     * Extrae de {@code code}, el nombre de la clase utilizado despu&eacute;s de
     * la palabra <quote>class</quote>. <p>Extracts the word
     * following <quote>class</quote> in {@code code}.</p>
     * 
     * @param code cadena a analizar para extraer el nombre de la clase
     * <p>the string to parse and to extract the class name.</p>
     * @return la palabra siguiente a , detectada en la
     * cadena recibida. ,
     * detected in {@code code}
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
