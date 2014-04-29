package org.semanticwb.bsc.formelement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;

/**
 * DojoFileUploadBSC es un FormElement para subir archivos que valida
 * extensiones y tamaño.
 *
 * @version %I%, %G%
 * @since 1.0
 */
public class DojoFileUploadBSC extends org.semanticwb.bsc.formelement.base.DojoFileUploadBSCBase {

    /**
     * Crea instancias de esta clase a partir de un objeto semantico
     *
     * @param base el objeto semántico a utilizar para crear la nueva instancia
     */
    public DojoFileUploadBSC(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Genera el c&oacute;digo HTML correspondiente a un elemento que carga
     * archivos.
     *
     * @param request la petici&oacute; HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (null == obj) {
            throw new SWBRuntimeException("No Semantic Object present");
        }

        String pname = getPropertyName(prop, obj, propName);
        String frmname = null;
        if (mode.equals("create")) {
            frmname = prop.getDomainClass().getURI();
        } else {
            frmname = obj.getURI();
        }
        frmname = frmname + "/form";

        StringBuilder buffer = new StringBuilder();
        String cad = UploaderFileCacheUtils.uniqueCad();
        UploadFileRequest ufq = configFileRequest(prop, pname);
        UploaderFileCacheUtils.putRequest(cad, ufq);
        request.getSession(true).setAttribute("fuCad", cad);
        String url = SWBPlatform.getContextPath() + "/multiuploader/" + obj.getModel().getModelObject().getId() + "/home/" + cad;
        Set<String> keys = ufq.getFiltros().keySet();

        buffer.append("\n<script type=\"text/javascript\">");
        buffer.append("\n  dojo.require(\"dojox.form.Uploader\");");
        buffer.append("\n  dojo.require(\"dojox.form.uploader.FileList\");");
        buffer.append("\n   function validateExtention(value){");
        buffer.append("\n       var elem = dijit.byId(value);");
        buffer.append("\n       var arrayExt = [");
        int i = 0;
        StringBuffer extensions = new StringBuffer();
        for (String key : keys) {
            String value = ufq.getFiltros().get(key);
            buffer.append("'");
            buffer.append(value.toLowerCase());
            extensions.append(value.toLowerCase());
            buffer.append("'");
            i++;
            if (i < (keys.size())) {
                buffer.append(",");
                extensions.append(", ");
            }
        }
        buffer.append("\n]");
        buffer.append("\n       var files = elem.getFileList();");
        buffer.append("\n       var file = files[0];");
        buffer.append("\n       var ext = file.name.substring(file.name.lastIndexOf(\".\")+1)");
        buffer.append(".toLowerCase();");
        long sizeFile = getFileMaxSize() / (1024 * 1024);
        buffer.append("\n       if(");
        buffer.append(getFileMaxSize());
        buffer.append("> 0 && file.size > ");
        buffer.append(getFileMaxSize());
        buffer.append(") { alert('");
        buffer.append(getLocaleString("validationSuperiorSize", lang));
        buffer.append(" ");
        buffer.append(sizeFile);
        buffer.append("M'); return  false;};");
        buffer.append("\n       if(arrayExt.length > 0 && arrayExt.indexOf(ext) < 0) { alert('");
        buffer.append(getLocaleString("invalidExtensions", lang));
        buffer.append(" ");
        buffer.append(extensions);
        buffer.append("'); return  false;};");
        buffer.append("\n       return true;");
        buffer.append("\n   }");
        buffer.append("\n</script>");


        if (!"view".equals(mode)) {
            buffer.append("<input ");
            buffer.append("name=\"uploadedfile\" ");
            buffer.append("data-dojo-props=\" \n");
            buffer.append("multiple:'");
            buffer.append((prop.getCardinality() != 1 ? "true" : "false"));
            buffer.append("', \n");
            buffer.append("uploadOnSelect:'true', \n");
            buffer.append("url:'" + url + "', \n");
            buffer.append("submit: function(form) {}, \n");
            buffer.append("onComplete: function (result) {console.log('result:'+result); ");
            buffer.append("dojo.byId('selectFile').innerHTML =result.detail}, \n");
            buffer.append("onCancel: function() {console.log('cancelled');}, \n");
            buffer.append("onChange: function (result) {  if(validateExtention('");
            buffer.append(pname);
            buffer.append("_defaultAuto')) { } else {dijit.byId('");
            buffer.append(pname);
            buffer.append("_defaultAuto').reset(); } }, \n");
            buffer.append("onAbort: function() {console.log('aborted');}, \n");
            buffer.append("onError: function (evt) {console.log(evt);}, \n");
            buffer.append("\" ");
            buffer.append("type=\"file\" ");
            buffer.append("data-dojo-type=\"dojox.form.Uploader\" ");
            buffer.append("label=\"Select File\" ");
            buffer.append("id=\"");
            buffer.append(pname);
            buffer.append("_defaultAuto\" ");
            buffer.append("/>  ");
            buffer.append("<br/>\n");
            buffer.append("<div id=\"selectFile\">\n");
            buffer.append("</div>\n");

            if (!"create".equals(mode) && obj.getProperty(prop) != null) {
                String name = obj.getProperty(prop);
                if (name.startsWith(pname)) {
                    name = name.substring(pname.length() + 1);
                }
                if (prop.getCardinality() == 1) {
                    if ("edit".equals(mode)) {
                        if (isShowRemove()) {
                            buffer.append("Eliminar: <input dojoType=\"dijit.form.CheckBox\" id=\"");
                            buffer.append(pname);
                            buffer.append("_delFile\" name=\"");
                            buffer.append(pname);
                            buffer.append("_delFile\" value=\"");
                            buffer.append(name);
                            buffer.append("\" /><a href=\"");
                            buffer.append(SWBPlatform.getContextPath());
                            buffer.append("/work");
                            buffer.append(obj.getWorkPath());
                            buffer.append("/");
                            buffer.append(obj.getProperty(prop));
                            buffer.append("\">");
                            buffer.append(name);
                            buffer.append("</a>\n");
                        } else {
                            if(request.getAttribute("UserProfile") == null){ // No viene del recurso Perfil de Usuario
                            buffer.append("<span class=\"archive\">Actual: ");
                            buffer.append(name);
                            buffer.append("</span>");
                            }
                        }
                    } else {
                        buffer.append("&nbsp;<a href=\"");
                        buffer.append(SWBPlatform.getContextPath());
                        buffer.append("/work");
                        buffer.append(obj.getWorkPath());
                        buffer.append("/");
                        buffer.append(obj.getProperty(prop));
                        buffer.append("\">");
                        buffer.append(name);
                        buffer.append("</a>");
                    }
                }
            }
            buffer.append("<input type=\"hidden\" name=\"");
            buffer.append(pname);
            buffer.append("\" value=\"");
            buffer.append(cad);
            buffer.append("\" />\n");
            UploaderFileCacheUtils.put(cad, new java.util.LinkedList<UploadedFile>());
            if (!"create".equals(mode) && prop.getCardinality() != 1) {
                Iterator<SemanticLiteral> lista = obj.listLiteralProperties(prop);
                if (lista.hasNext()) {
                    buffer.append(getLocaleString("delete", lang));
                    buffer.append(":<br><select dojoType=\"dijit.form.MultiSelect\" name=\"");
                    buffer.append(pname);
                    buffer.append("_delFile\" multiple=\"multiple\" size=\"4\">\n");
                    while (lista.hasNext()) {
                        SemanticLiteral lit = lista.next();
                        String fname = lit.getString();
                        if (fname.startsWith(pname)) {
                            fname = fname.substring(pname.length() + 1);
                        }
                        buffer.append("<option value=\"");
                        buffer.append(fname);
                        buffer.append("\">");
                        buffer.append(fname);
                        buffer.append("</option>");
                    }
                    buffer.append("</select>");
                }
            }
        } else {
            if (obj.getProperty(prop) != null) {
                if (prop.getCardinality() == 1) {
                    String name = obj.getProperty(prop);
                    if (name.startsWith(pname)) {
                        name = name.substring(pname.length() + 1);
                    }
                    buffer.append("&nbsp;<a href=\"");
                    buffer.append(SWBPlatform.getContextPath());
                    buffer.append("/work");
                    buffer.append(obj.getWorkPath());
                    buffer.append("/");
                    buffer.append(obj.getProperty(prop));
                    buffer.append("\">");
                    buffer.append(name);
                    buffer.append("</a>");
                } else {
                    Iterator<SemanticLiteral> lista = obj.listLiteralProperties(prop);
                    if (lista.hasNext()) {
                        while (lista.hasNext()) {
                            SemanticLiteral lit = lista.next();
                            String fname = lit.getString();
                            String name = fname;
                            if (fname.startsWith(pname)) {
                                name = fname.substring(pname.length() + 1);
                            }
                            buffer.append("&nbsp;<a href=\"");
                            buffer.append(SWBPlatform.getContextPath());
                            buffer.append("/work");
                            buffer.append(obj.getWorkPath());
                            buffer.append("/");
                            buffer.append(fname);
                            buffer.append("\">");
                            buffer.append(name);
                            buffer.append("</a>");
                        }
                    }
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Se encarga de procesar la información de la vista del elemento y almacenar el
     * valor en la propiedad.
     * 
     * @param request la petici&oacute; HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este FormElement
     * @param prop la propiedad asociada a este FormElement
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {
        String pname = getPropertyName(prop, obj, propName);
        //MAPS74 - 01032011 Parche en caso de que en la forma no se haya rendereado la propiedad
        if (request.getParameter(pname + "_delFile") != null
                || request.getParameter(propName + "_new") != null
                || request.getParameter(pname) != null) {
            if (request.getParameter(pname + "_delFile") != null) {
                if (prop.getCardinality() != 1) {
                    String[] params = request.getParameterValues(pname + "_delFile");
                    for (String valor : params) {
                        delfile(obj, pname + "_" + valor);
                        obj.removeLiteralProperty(prop, new SemanticLiteral(pname + "_" + valor));
                    }
                } else {
                    delfile(obj, pname + "_" + request.getParameter(pname + "_delFile"));
                    obj.removeProperty(prop);
                }
            }
            String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
            File dir = new File(destpath);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new SWBRuntimeException("Can't create work directory " + dir);
            }
            String cad = request.getParameter(propName + "_new");
            if (cad == null) {
                cad = request.getParameter(pname);
            }
            List<UploadedFile> lista = UploaderFileCacheUtils.get(cad);
            for (UploadedFile arch : lista) {
                File orig = new File(arch.getTmpuploadedCanonicalFileName());
                File dest = new File(dir, pname + "_" + arch.getOriginalName());
                if (!orig.renameTo(dest)) {
                    try {
                        SWBUtils.IO.copy(orig.getCanonicalPath(), dest.getCanonicalPath(), false, null, null);
                    } catch (IOException ex) {
                        throw new SWBRuntimeException("Can't copy files", ex);
                    }
                }
                if (prop.getCardinality() != 1) {
                    obj.addLiteralProperty(prop, new SemanticLiteral(pname + "_" + arch.getOriginalName()));
                } else {
                    obj.setProperty(prop, pname + "_" + arch.getOriginalName());
                }
            }
            UploaderFileCacheUtils.clean(cad);
        }
    }

    /**
     * Config file request.
     *
     * @param pname the property name
     * @return the upload file request
     */
    protected UploadFileRequest configFileRequest(SemanticProperty prop, String pname) {
        boolean multiple = prop.getCardinality() != 1;
        HashMap<String, String> filtros = new HashMap<String, String>();
        if (null == getFileFilter() || "".equals(getFileFilter())) {
            filtros.put("All Files", "*.*");
        } else {
            String[] cads = getFileFilter().split("\\|");
            for (String line : cads) {
                String[] parts = line.split(":");
                filtros.put(parts[0], parts[1]);
            }
        }
        return new UploadFileRequest(filtros, multiple, getFileMaxSize());
    }

    /**
     * Delfile.
     *
     * @param obj the obj
     * @param valor the valor
     */
    protected void delfile(SemanticObject obj, String valor) {
        String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
        File dir = new File(destpath);
        File dest = new File(dir, valor);
        dest.delete();
    }

    protected String getPropertyName(SemanticProperty prop, SemanticObject obj, String propName) {
        return propName + "_" + (obj.getId() == null ? "new" : obj.getId());
    }

    /*@Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) throws FormValidateException {
        if(request.getParameter("selectFile") != null && request.getParameter("selectFile").equals("")) {
             throw new FormValidateException(getLocaleString("error", propName));
        }
        super.validate(request, obj, prop, propName);
    }*/
    
    
}
