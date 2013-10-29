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
package org.semanticwb.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageUpload.
 */
public class ImageUpload extends org.semanticwb.model.base.ImageUploadBase
{

    /**
     * Instantiates a new image upload.
     * 
     * @param base the base
     */
    public ImageUpload(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FileUpload#renderElement(HttpServletRequest, SemanticObject, SemanticProperty, String, String, String)
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
//        System.out.println("********************** DojoFileUploader.renderElement **********************");
//        System.out.println("obj: "+obj);
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

//        StringBuilder filts = new StringBuilder();
//        Set<String> keys = ufq.getFiltros().keySet();
//        for (String key : keys) {
//            String value = ufq.getFiltros().get(key);
//            if (filts.length() > 0) {
//                filts.append("\\'],");
//            } else if (filts.length() == 0) {
//                filts.append("[");
//            }
//            filts.append("[\\'");
//            filts.append(key);
//            filts.append("\\', \\'");
//            filts.append(value);
//        }
//        filts.append("\\']]");
        String multiple = "false;";
        if (!"view".equals(mode)) {
            buffer.append("<input "
                    + "name=\"uploadedfile\" "
                    + "data-dojo-props=\" \n"
                    + "multiple:'" + (prop.getCardinality() != 1 ? "true" : "false") + "', \n"
                    //+ "force:'iframe', \n" 
                    + "uploadOnSelect:'true', \n"
                    + "url:'" + url + "', \n"
                    + "submit: function(form) {}, \n"
                    + "onComplete: function (result) {console.log('result:'+result);}, \n"
                    + "onCancel: function() {console.log('cancelled');}, \n"
                    + "onAbort: function() {console.log('aborted');}, \n"
                    + "onError: function (evt) {console.log(evt);}, \n"
                    + "\" "
                    + "type=\"file\" "
                    + "data-dojo-type=\"dojox.form.Uploader\" "
                    + "label=\"Select File\" "
                    + "id=\"" + pname + "_defaultAuto\" "
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
        //System.out.println(buffer.toString());
        return buffer.toString();
    }
    
    public String renderElement_(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang)
    {
//        System.out.println("obj: "+obj);
//        System.out.println("objuri: "+obj.getURI());
//        System.out.println("prop: "+prop);
//        System.out.println("type: "+type);
//        System.out.println("mode: "+mode);
//        System.out.println("lang: "+lang);
//        System.out.println("objcls: "+obj.getSemanticClass());
//        System.out.println("propcls: "+prop.getDomainClass());
        if (null == obj)
        {
            throw new SWBRuntimeException("No Semantic Object present");
        }


        String frmname = null;
        if (mode.equals("create"))
        {
            frmname = prop.getDomainClass().getURI();
        } else
        {
            frmname = obj.getURI();
        }
        frmname = frmname + "/form";


        StringBuilder buffer = new StringBuilder();
        String cad = UploaderFileCacheUtils.uniqueCad();
        UploaderFileCacheUtils.putRequest(cad, configFileRequest(prop, propName));
        request.getSession(true).setAttribute("fuCad", cad);
        String page;
        if (obj.instanceOf(WebPage.sclass))
        {
            page = obj.getId();
        } else
        {
            page = ((WebSite) obj.getModel().getModelObject().getGenericInstance()).getHomePage().getId();
        }
        String url = SWBPlatform.getContextPath() + "/multiuploader/" + obj.getModel().getModelObject().getId() + "/" + page + "/" + cad;
        String enviar = lang.equals("en")?"You have to send the selected files first":"Debe enviar primero los archivos seleccionados";
        String eliminar = lang.equals("en")?"Chose the files to delete":"Selecione el(los) archivo(s) a eliminar";

//        System.out.println(url);
        buffer.append("\n<script type=\"text/javascript\">\n");
        buffer.append(" //var foo; \nif (typeof(foo) == 'undefined' || foo == null){var foo = new Object();}\n");
//        buffer.append("foo.uno=true;\n");
        buffer.append("var canSubmit = true;\n");
//        buffer.append("var tosubmit = true;\n");
        buffer.append("function canContinue(cad){\n");
        buffer.append("    foo[cad] = true;\n");
        buffer.append("}\n");
        buffer.append("function stopSumbit(cad){\n");
        buffer.append("    foo[cad] = false;\n");
        buffer.append("}\n");
//        buffer.append(" var foo; \nif (typeof(foo) == 'undefined' || foo == null){foo = new Object();}\n");
//        buffer.append("foo.dos=false;\n");
//        buffer.append("alert(foo.uno); alert(foo.dos);\n");
        buffer.append("\n");
//        buffer.append("dojo.addOnLoad(function() {\n");
        buffer.append("    var myForm = dojo.byId(\"" + frmname + "\");\n");
//        buffer.append("    dojo.connect(myForm, \"onSubmit\", function(e) {\nalert('En la funcion');\n");
        buffer.append("onsubmit=function(){\n");
        buffer.append("var canSubmit=true;\n");
        buffer.append("for (var test in foo) {\n");
        buffer.append("canSubmit = canSubmit&&foo[test];}\n");
        buffer.append(" if (!canSubmit) alert('"+enviar+"');\n");
        buffer.append("return canSubmit;};\n");
//        buffer.append("        d = document;\n f = d.frames ? d.frames['ifrupd"+cad
//                +"'] : d.getElementById('ifrupd"+cad+"');\n p = f.document || f.contentWindow.document; alert ('p'+p.localName); \np.cliked();\n");
//        buffer.append("if (tosubmit) { \ndocument.getElementById('ifrupd"+cad+"').contentWindow.cliked();\ntosubmit=false;}\n");
//        buffer.append("     console.log('canSubmit='+canSubmit+' - '+myForm);\n");
//        buffer.append("        if (!canSubmit) {\n");
//        buffer.append("            setTimeout(function(){alert('invocando submit '+myForm);document.forms[\""+frmname+"\"].submit();},500);\nconsole.log(canSubmit);");
//        buffer.append("        }\n");
//        buffer.append("        alert('Saliendo con '+canSubmit);return false;}\n");
//        buffer.append("     });\n");
//        buffer.append(" });\n");
        buffer.append("\n");
        buffer.append("</script>\n");
        buffer.append("<table border=\"0\"><tr><td><iframe src=\"" + url + "\" frameborder=\"0\" width=\"305\" "
                + "scrolling=\"no\" name=\"ifrupd" + cad + "\" id=\"ifrupd" + cad + "\" height=\"170\" ></iframe>\n");
        buffer.append("<input type=\"hidden\" name=\"" + propName + "\" value=\"" + cad + "\" /></td>\n");
        buffer.append("<td valign=\"top\">"+eliminar+":<br/><select dojoType=\"dijit.form.MultiSelect\" name=\""
                + propName + "_delFile\" multiple=\"multiple\" size=\"4\">\n");
        Iterator<SemanticLiteral>lista = obj.listLiteralProperties(prop);
        while (lista.hasNext()){
            SemanticLiteral lit = lista.next();
            buffer.append("<option>"+lit.getString()+"</option>");
        }
        buffer.append("</select></td></tr></table>");

        if (prop.isRequired())
        {
            buffer.append("<script> foo." + cad + "=false; </script>");
        } else
        {
            buffer.append("<script> foo." + cad + "=true; </script>");
        }

//        buffer.append("<script type=\"dojo/method\" event=\"onSubmit\">\n");
//        buffer.append("alert ('OnSubmit'); \n");
//        buffer.append("        d = document;\n f = d.frames ? d.frames['ifrupd"+cad
//                +"'] : d.getElementById('ifrupd"+cad+"');\n alert ('f'+f); p = f.document || f.contentWindow.document; alert ('p'+p); \np.cliked();\n");
//
//        buffer.append("\n");
//        buffer.append("while (!canSubmit) {\n");
//        buffer.append("            setTimeout(function(){},250);\n");
//        buffer.append("        }\n");
//        buffer.append("return true;\n");
//        buffer.append("</script>\n");



//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("\n");
//        buffer.append("</input>\n");


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
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName)
    {
        if (request.getParameter(propName+"_delFile")!=null){
            if (propName.startsWith("has")){
                Iterator<SemanticLiteral>list=obj.listLiteralProperties(prop);

            Set<String>grupo=new TreeSet<String>();
            while (list.hasNext())
            {
                grupo.add(list.next().getString());
            }
            String[]params = request.getParameterValues(propName+"_delFile");
            for (String valor:params){
                grupo.remove(valor);
                delfile(obj, valor);
            }
            obj.removeProperty(prop);
            for (String valor:grupo)
            {
                obj.addLiteralProperty(prop, new SemanticLiteral(valor));
            }
            } else {
                delfile(obj, request.getParameter(propName+"_delFile"));
                obj.removeProperty(prop);
            }
        }
        String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
        File dir = new File(destpath);
        if (!dir.exists() && !dir.mkdirs())
        {
            throw new SWBRuntimeException("Can't create work directory " + dir);
        }
        String cad = request.getParameter(propName);
        List<UploadedFile> lista = UploaderFileCacheUtils.get(cad);
        for (UploadedFile arch : lista)
        {
            File orig = new File(arch.getTmpuploadedCanonicalFileName());
            String webpath = obj.getWorkPath() + arch.getOriginalName();
            File dest = new File(dir, arch.getOriginalName());
            if (!orig.renameTo(dest))
            {
                try
                {
                    SWBUtils.IO.copy(orig.getCanonicalPath(), dest.getCanonicalPath(), false, null, null);
                } catch (IOException ex)
                {
                    throw new SWBRuntimeException("Can't copy files", ex);
                }
            }
            imgPrpcess(dest);
            if (propName.startsWith("has"))
            {
                obj.addLiteralProperty(prop, new SemanticLiteral(arch.getOriginalName()));
            } else
            {
                obj.setProperty(prop, arch.getOriginalName());
            }
        }
        UploaderFileCacheUtils.clean(cad);
    }

    /**
     * Img prpcess.
     * 
     * @param dest the dest
     */
    private void imgPrpcess(File dest)
    {
    }

    /**
     * Config file request.
     * 
     * @param prop the prop
     * @return the upload file request
     */
    private UploadFileRequest configFileRequest(SemanticProperty prop, String propName)
    {
        boolean multiple = propName.startsWith("has");
//        System.out.println("filter:"+getFileFilter());
        HashMap<String, String> filtros = new HashMap<String, String>();
        if (null == getFileFilter() || "".equals(getFileFilter()))
        {
            filtros.put("Jpeg File", "*.jpg;*.jpeg");
            filtros.put("GIF File", "*.gif");
            filtros.put("PNG File", "*.png");
            filtros.put("All Images", "*.jpg;*.jpeg;*.gif;*.png");
        }else {
            String[]cads = getFileFilter().split("\\|");
            for (String line:cads){
//                System.out.println("cadena:"+line);
                String[]parts = line.split(":");
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
    private void delfile(SemanticObject obj, String valor)
    {
        String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
        File dir = new File(destpath);
        File dest = new File(dir, valor);
        dest.delete();
    }
    
    protected String getPropertyName(SemanticProperty prop, SemanticObject obj, String propName) {

        return propName + "_" + (obj.getId() == null ? "new" : obj.getId());
    }
    
}
