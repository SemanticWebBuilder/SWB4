package org.semanticwb.social;

import com.google.gdata.data.extensions.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;

/**
 * Cargador de Archivos con Dojo
 */
public class SocialFileUpload extends org.semanticwb.social.base.SocialFileUploadBase {

    private static Logger log = SWBUtils.getLogger(SocialFileUpload.class);

    public SocialFileUpload(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FileUpload#renderElement(HttpServletRequest, SemanticObject, SemanticProperty, String, String, String)
     */
    @Override
public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {

        String objUri = request.getParameter("objUri");
        String sourceCall = request.getParameter("source");
        if (sourceCall == null) { //When typeOfContent is called from Tema/Responder the param source is not being sent
            sourceCall = "reply";
        }
//        System.out.println("objuri: "+obj.getURI());
//        System.out.println("prop: "+prop);
//        System.out.println("type: "+type);
//        System.out.println("mode: "+mode);
//        System.out.println("lang: "+lang);
//        System.out.println("objcls: "+obj.getSemanticClass());
//        System.out.println("propcls: "+prop.getDomainClass());
        if (null == obj) {
            throw new SWBRuntimeException("No Semantic Object present");
        }

        String pname = getPropertyName(prop, obj, propName);

        String frmname = null;
        String editionId = "";
        if (mode.equals("create")) {
            frmname = prop.getDomainClass().getURI();
        } else {
            editionId = obj.getId();
            frmname = obj.getURI();
        }
        frmname = frmname + "/form";


        StringBuilder buffer = new StringBuilder();
        String cad = UploaderFileCacheUtils.uniqueCad();
        UploadFileRequest ufq = configFileRequest(prop, pname);
        UploaderFileCacheUtils.putRequest(cad, ufq);
        request.getSession(true).setAttribute("fuCad", cad);
//        String page;
//        if (obj.instanceOf(WebPage.sclass)) {
//            page = obj.getId();
//        } else {
//            page = ((WebSite) obj.getModel().getModelObject().getGenericInstance()).getHomePage().getId();
//        }
        WebSite site;
        String url = SWBPlatform.getContextPath() + "/multiuploader/" + obj.getModel().getModelObject().getId() + "/home/" + cad;
//        String enviar = lang.equals("en") ? "You have to send the selected files first" : "Debe enviar primero los archivos seleccionados";
        String eliminar = lang.equals("en") ? "Chose the files to delete" : "Selecione el(los) archivo(s) a eliminar";
//        String agregar = lang.equals("en") ? "Add new file upload" : "Agrega un nuevo archivo a cargar";
        String filtros = "";
        StringBuilder filts = new StringBuilder();
        Set<String> keys = ufq.getFiltros().keySet();
        for (String key : keys) {
            String value = ufq.getFiltros().get(key);
            filtros = value;
        }

        System.out.println("EN EL RECURSO: "+filtros);

        String multiple = "false;";
        if (!"view".equals(mode)) {
            buffer.append("<input "
                    + "name=\"uploadedfile\" "
                    + "data-dojo-props=\"label:'Adjuntar',\n"
                    + "multiple:'" + (prop.getCardinality() != 1 ? "true" : "false") + "', \n"
                    //+ "force:'iframe', \n" 
                    + "uploadOnSelect:false, \n"
                    + "url:'" + url + "', \n"
                    + "submit: function(form) {}, \n");
                    if (obj.getSemanticClass().getName().equals("Message") ) {
                        buffer.append("onChange: function (result) {   if(validateExtention('" + objUri + sourceCall + editionId + "_defaultAuto', false)){dijit.byId('" + objUri + sourceCall + editionId + "_defaultAuto').upload();}}, \n");
                    } else if(obj.getSemanticClass().getName().equals("Photo")){
                        buffer.append("onChange: function (result) {   if(validateExtention('" + objUri + sourceCall + editionId + "_defaultAuto', true)) {dijit.byId('" + objUri + sourceCall + editionId + "_defaultAuto').upload();}}, \n");
                    }else if (obj.getSemanticClass().getName().equals("Video")) {
                        buffer.append("onChange: function (result) {   if(validateVideo('" + objUri + sourceCall + editionId + "_defaultAuto', '" + objUri + sourceCall + editionId+ "frmUploadVideo')){dijit.byId('" + objUri + sourceCall + editionId + "_defaultAuto').upload();}; }, \n"); 
                    }else{
                        buffer.append("onChange: function (result) {   this.upload();}, \n");
                    }
                    buffer.append("onCancel: function() {console.log('cancelled');}, \n"
                    + "onAbort: function() {console.log('aborted');}, \n");
                    buffer.append("onComplete: function (result) { console.log('completed!');}, \n");
                    //buffer.append("onComplete: function(result) {console.log('result:' + result);}, \n"
                    /*if (obj.getSemanticClass().getName().equals("Message") ) {
                        buffer.append("onComplete: function (result) { console.log('upload TEXT form');  submitForm('" + objUri + sourceCall + "frmUploadText');}, \n");
                    } else if(obj.getSemanticClass().getName().equals("Photo")){
                        buffer.append("onComplete: function (result) { console.log('upload PHOTO form'); submitForm('" + objUri + sourceCall + "frmUploadPhoto');}, \n");
                    }else if (obj.getSemanticClass().getName().equals("Video")) {
                        buffer.append("onComplete: function (result) { console.log('upload VIDEO form'); submitForm('" + objUri + sourceCall + "frmUploadVideo');}, \n"); //validateVideo('<%=objUri + sourceCall%>_defaultAuto', '<%=objUri + sourceCall%>frmUploadVideo')
                    }*/
                    buffer.append("onError: function (evt) {console.log(evt);}, \n"
                     + "fileMask: ['"+ getFileMaxSize()+"', '"+ filtros+"']\" "
                    + "\" "
                    + "type=\"file\" "
                    + "data-dojo-type=\"dojox.form.Uploader\" "
                    + "label=\"Select File\" "
                    + "id=\""+objUri+sourceCall+editionId + "_defaultAuto\""
                    + "/>  ");
//            buffer.append("<input dojoType=\"dojox/form/Uploader\"  "
//                    + "multiple=\""+multiple+"\" "
//                    + "type=\"file\""
//                    + "uploadOnSelect=\"true\""
//                    + "id=\"" + pname + "_defaultAuto\" "
//                    + "name=\"" + pname + "_inputFileAuto\" "
//                    + "url=\"" + url + "\" "
//                    + "onComplete=\"fileUpload_Callback"+((obj.getProperty(prop) != null)?"":"2")+"\"  "
//                    + "startup=\"dijit.byId(document.getElementById('"+pname+ "_defaultAuto').form.id).extValid="+(obj.getProperty(prop) != null)+";\" "
//                    + "fileMask=\"" + filts.toString().replaceAll("\\\\", "") + "\""
//                    + "/>\n");
            //buffer.append("        <button onclick=\"return false;\">Enviar</button>\n");
            buffer.append("<br/>\n");
            
            buffer.append("<div data-dojo-type=\"dojox.form.uploader.FileList\" uploaderId=\"" + objUri + sourceCall + editionId + "_defaultAuto\"></div>");

            if (!"create".equals(mode) && obj.getProperty(prop) != null) {
                String name = obj.getProperty(prop);
                if (name.startsWith(pname)) {
                    name = name.substring(pname.length() + 1);
                }
               if (prop.getCardinality() == 1) {
                    if ("edit".equals(mode)) {
                        buffer.append("Eliminar: <input dojoType=\"dijit.form.CheckBox\" id=\""
                                + pname + "_delFile\" name=\""
                               + pname + "_delFile\" value=\"" + name + "\" /><a href=\"" + SWBPlatform.getContextPath() + "/work" + obj.getWorkPath() + "/" + obj.getProperty(prop) + "\">" + name + "</a>\n");
                    } else {
                        buffer.append("&nbsp;<a href=\"" + SWBPlatform.getContextPath() + "/work" + obj.getWorkPath() + "/" + obj.getProperty(prop) + "\">" + name + "</a>");
                    }
                }
            }
//        else
//        {
//            buffer.append("<script type=\"text/javascript\">");
//            buffer.append("    var fileUpload_Callback2 = function(data,ioArgs,widgetRef){");
//            buffer.append("        if(data && data.status && data.status == \"success\")");
//            buffer.append("        {");
//            buffer.append("            var ele=document.getElementById(\""+pname+ "_defaultAuto\");");
//            buffer.append("            var form = dijit.byId(ele.form.id);");
//            buffer.append("            form.extValid=true;");
//            buffer.append("            form.onValidStateChange(form.isValid()&&form.extValid);");
//            buffer.append("        }");
//            buffer.append("        fileUpload_Callback(data,ioArgs,widgetRef);");
//            buffer.append("    };");
//            buffer.append("</script>");
//        }
            //}

            buffer.append("<input type=\"hidden\" name=\"" + pname + "\" value=\"" + cad + "\" />\n");
            UploaderFileCacheUtils.put(cad, new java.util.LinkedList<UploadedFile>());
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("</td>\n");

//        buffer.append("<table border=\"0\"><tr><td><iframe src=\"" + url + "\" frameborder=\"0\" width=\"225\" "
//                + "scrolling=\"no\" name=\"ifrupd" + cad + "\" id=\"ifrupd" + cad + "\" height=\"170\" ></iframe>\n");
//        buffer.append("<input type=\"hidden\" name=\"" + pname + "\" value=\"" + cad + "\" /></td>\n");
            if (!"create".equals(mode) && prop.getCardinality() != 1) {
                Iterator<SemanticLiteral> lista = obj.listLiteralProperties(prop);
                if (lista.hasNext()) {
                    buffer.append(eliminar + ":<br><select dojoType=\"dijit.form.MultiSelect\" name=\""
                            + pname + "_delFile\" multiple=\"multiple\" size=\"4\">\n");
                    while (lista.hasNext()) {
                        SemanticLiteral lit = lista.next();
                        String fname = lit.getString();
                        if (fname.startsWith(pname)) {
                            fname = fname.substring(pname.length() + 1);
                        }
                        buffer.append("<option value=\"" + fname + "\">" + fname + "</option>");
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
                    buffer.append("&nbsp;<a href=\"" + SWBPlatform.getContextPath() + "/work" + obj.getWorkPath() + "/" + obj.getProperty(prop) + "\">" + name + "</a>");
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
                            buffer.append("&nbsp;<a href=\"" + SWBPlatform.getContextPath() + "/work" + obj.getWorkPath() + "/" + fname + "\">" + name + "</a>");
                        }
                    }
                }
            }
        }
        System.out.println("Entra a SocialFileUpload/renderElement-1:" + buffer.toString());
        return buffer.toString();
    }


    /**
     * Process.
     *
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {
        String pname = getPropertyName(prop, obj, propName);
        //MAPS74 - 01032011 Parche en caso de que en la forma no se haya rendereado la propiedad
        if (request.getParameter(pname + "_delFile") != null
                || request.getParameter(propName + "_new") != null
                || request.getParameter(pname) != null) {
            //        System.out.println("********************** FlashFileUploader.process **********************");
            //        System.out.println(request.getParameter(pname + "_delFile"));
            if (request.getParameter(pname + "_delFile") != null) {
                if (prop.getCardinality() != 1) {
                    Iterator<SemanticLiteral> list = obj.listLiteralProperties(prop);

                    String[] params = request.getParameterValues(pname + "_delFile");
                    for (String valor : params) {
                        //System.out.println("Del:"+valor);
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
            //        System.out.println("Cadena:"+cad);
            List<UploadedFile> lista = UploaderFileCacheUtils.get(cad);
            for (UploadedFile arch : lista) {
                File orig = new File(arch.getTmpuploadedCanonicalFileName());
                String webpath = obj.getWorkPath() + arch.getOriginalName();
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
//        System.out.println("********************** FlashFileUploader.configFileRequest **********************");
//        System.out.println("Tengo filtro "+getFileFilter()+"|--");
//        System.out.println("*Prop:"+pname);
//        System.out.println("*FileMaxSize:"+getFileMaxSize());

        boolean multiple = prop.getCardinality() != 1;
        //System.out.println("filter:"+getFileFilter());
        HashMap<String, String> filtros = new HashMap<String, String>();
        if (null == getFileFilter() || "".equals(getFileFilter())) {
            filtros.put("All Files", "*.*");
        } else {
            String[] cads = getFileFilter().split("\\|");
            for (String line : cads) {
                //System.out.println("cadena:"+line);
                String[] parts = line.split(":");
                //System.out.println("parts[0]"+parts[0]);
                //System.out.println("parts[1]"+parts[1]);
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
}
