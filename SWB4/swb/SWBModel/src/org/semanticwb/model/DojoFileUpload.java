/**
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
 **/
package org.semanticwb.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;


public class DojoFileUpload extends org.semanticwb.model.base.DojoFileUploadBase 
{
    /** The log. */
    private static Logger log = SWBUtils.getLogger(DojoFileUpload.class);

    /**
     * Instantiates a new dojo file upload.
     *
     * @param base the base
     */
    public DojoFileUpload(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FileUpload#renderElement(HttpServletRequest, SemanticObject, SemanticProperty, String, String, String)
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang)
    {
//        System.out.println("********************** DojoFileUploader.ConfigFileRequest **********************");
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
        UploadFileRequest ufq=configFileRequest(prop);
        UploaderFileCacheUtils.putRequest(cad, ufq);
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
        String agregar = lang.equals("en")?"Add new file upload":"Agrega un nuevo archivo a cargar";

//        System.out.println(url);
////        buffer.append("\n<script type=\"text/javascript\">\n");
////        buffer.append(" //var foo; \nif (typeof(foo) == 'undefined' || foo == null){var foo = new Object();}\n");
//////        buffer.append("foo.uno=true;\n");
////        buffer.append("var canSubmit = true;\n");
//////        buffer.append("var tosubmit = true;\n");
////        buffer.append("function canContinue(cad){\n");
////        buffer.append("    foo[cad] = true;\n");
////        buffer.append("}\n");
////        buffer.append("function stopSumbit(cad){\n");
////        buffer.append("    foo[cad] = false;\n");
////        buffer.append("}\n");
//////        buffer.append(" var foo; \nif (typeof(foo) == 'undefined' || foo == null){foo = new Object();}\n");
//////        buffer.append("foo.dos=false;\n");
//////        buffer.append("alert(foo.uno); alert(foo.dos);\n");
////        buffer.append("\n");
//////        buffer.append("dojo.addOnLoad(function() {\n");
////        buffer.append("    var myForm = dojo.byId(\"" + frmname + "\");\n");
//////        buffer.append("    dojo.connect(myForm, \"onSubmit\", function(e) {\nalert('En la funcion');\n");
////        buffer.append("onsubmit=function(){\n");
////        buffer.append("var canSubmit=true;\n");
////        buffer.append("for (var test in foo) {\n");
////        buffer.append("canSubmit = canSubmit&&foo[test];}\n");
////        buffer.append(" if (!canSubmit) alert('"+enviar+"');\n");
////        buffer.append("return canSubmit;};\n");
//////        buffer.append("        d = document;\n f = d.frames ? d.frames['ifrupd"+cad
//////                +"'] : d.getElementById('ifrupd"+cad+"');\n p = f.document || f.contentWindow.document; alert ('p'+p.localName); \np.cliked();\n");
//////        buffer.append("if (tosubmit) { \ndocument.getElementById('ifrupd"+cad+"').contentWindow.cliked();\ntosubmit=false;}\n");
//////        buffer.append("     console.log('canSubmit='+canSubmit+' - '+myForm);\n");
//////        buffer.append("        if (!canSubmit) {\n");
//////        buffer.append("            setTimeout(function(){alert('invocando submit '+myForm);document.forms[\""+frmname+"\"].submit();},500);\nconsole.log(canSubmit);");
//////        buffer.append("        }\n");
//////        buffer.append("        alert('Saliendo con '+canSubmit);return false;}\n");
//////        buffer.append("     });\n");
//////        buffer.append(" });\n");
////        buffer.append("\n");
////        buffer.append("</script>\n");
        
        buffer.append("<script>\n");
        buffer.append("    dojo.require(\"dojox.form.FileInputAuto\");\n");
        buffer.append("\n");
	buffer.append("	var " + prop.getName() + "_Callback = function(data,ioArgs,widgetRef){\n");
//	buffer.append("		// this function is fired for every programatic FileUploadAuto\n");
//	buffer.append("		// when the upload is complete. It uses dojo.io.iframe, which\n");
//	buffer.append("		// expects the results to come wrapped in TEXTAREA tags.\n");
//	buffer.append("		// this is IMPORTANT. to utilize FileUploadAuto (or Blind)\n");
//	buffer.append("		// you have to pass your response data in a TEXTAREA tag.\n");
//	buffer.append("		// in our sample file (if you have php5 installed and have\n");
//	buffer.append("		// file uploads enabled) it _should_ return some text in the\n");
//	buffer.append("		// form of valid JSON data, like:\n");
//	buffer.append("		// { status: \"success\", details: { size: \"1024\" } }\n");
//	buffer.append("		// you can do whatever.\n");
//	buffer.append("		//\n");
//	buffer.append("		// the ioArgs is the standard ioArgs ref found in all dojo.xhr* methods.\n");
//	buffer.append("		//\n");
//	buffer.append("		// widget is a reference to the calling widget. you can manipulate the widget\n");
//	buffer.append("		// from within this callback function \n");
	buffer.append("		if(data){\n");
	buffer.append("			if(data.status && data.status == \"success\"){\n");
	buffer.append("				widgetRef.overlay.innerHTML = \"OK\";\n");
	buffer.append("			}else{\n");
	buffer.append("				widgetRef.overlay.innerHTML = \"error? \";\n");
	buffer.append("				console.log('error',data,ioArgs);\n");
	buffer.append("			}\n");
	buffer.append("		}else{\n");
	buffer.append("			// debug assist\n");
	buffer.append("			console.log('ugh?',arguments);\n");
	buffer.append("		}\n");
	buffer.append("	}\n");
        buffer.append("\n");
	buffer.append("	var i = 0;\n");
	buffer.append("	function " + prop.getName() + "_addNewUpload(){\n");
	buffer.append("		var node = document.createElement('input'); \n");
	buffer.append("		dojo.byId('" + prop.getName() + "_dynamic').appendChild(node);\n");
	buffer.append("		var widget = new dojox.form.FileInputAuto({\n");
	buffer.append("			id: \"" + prop.getName() + "_dynamic\"+(++i),\n");
	buffer.append("			url: \""+url+"\",\n");
//	buffer.append("			//url:\"http://archive.dojotoolkit.org/nightly/checkout/dojox/widget/resources/RecieveFile.php\",\n");
	buffer.append("			name: \"" + prop.getName() + "_dynamic\"+i,\n");
	buffer.append("			onComplete: " + prop.getName() + "_Callback,\n");
        buffer.append("                 fileMask:");
        StringBuilder filts = new StringBuilder();
        Set<String> keys = ufq.getFiltros().keySet();
        for (String key:keys){
            String value=ufq.getFiltros().get(key);
            if (filts.length()>0) filts.append("\"],");
            else if (filts.length()==0) filts.append("[");
            filts.append("[\"");
            filts.append(key);
            filts.append("\", \"");
            filts.append(value);
        }
        filts.append("\"]]");
        buffer.append(filts);
	buffer.append("\n		},node);\n");
	buffer.append("		widget.startup();\n");
	buffer.append("	}\n");
        buffer.append("\n");
        buffer.append("</script>\n");
        if (prop.getName().startsWith("has")){
            buffer.append("        <button onclick=\"" + prop.getName() + "_addNewUpload()\">"+agregar+"</button>\n");
            buffer.append("	<br><br>\n");
            buffer.append("	<div id=\"" + prop.getName() + "_dynamic\"></div>\n");
        } else {

                buffer.append("<input dojoType=\"dojox.form.FileInputAuto\" "
                + "id=\"" + prop.getName() + "_defaultAuto\" name=\"" + prop.getName() + "_inputFileAuto\" url=\""+url+
                "\" onComplete=\"" + prop.getName() + "_Callback\" fileMask=\""+filts.toString()+"\"/>\n");
                if (!"create".equals(mode) && obj.getProperty(prop)!=null){
                    String name = obj.getProperty(prop);
                    if (name.startsWith(prop.getName()))
                {
                    name = name.substring(prop.getName().length()+1);
                }
                    buffer.append("<script>\n");
                    buffer.append("    dojo.require(\"dijit.form.CheckBox\");\n");
                    buffer.append("</script>\n");
                    buffer.append("<br>Eliminar: <input dojoType=\"dijit.form.CheckBox\" id=\""
                    + prop.getName() + "_delFile\" name=\""
                    + prop.getName() + "_delFile\" value=\""+name+"\" /><label for=\""
                    + prop.getName() + "_delFile\">"+name+"</label>\n");
                }
        }

        buffer.append("<input type=\"hidden\" name=\"" + prop.getName() + "\" value=\"" + cad + "\" />\n");
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
//        buffer.append("<input type=\"hidden\" name=\"" + prop.getName() + "\" value=\"" + cad + "\" /></td>\n");
        if (!"create".equals(mode) && prop.getName().startsWith("has")){
            buffer.append("<script>\n");
            buffer.append("    dojo.require(\"dijit.form.MultiSelect\");\n");
            buffer.append("</script>\n");
            buffer.append("<br>"+eliminar+":<br><select dojoType=\"dijit.form.MultiSelect\" name=\""
                    + prop.getName() + "_delFile\" multiple=\"multiple\" size=\"4\">\n");
            Iterator<SemanticLiteral>lista = obj.listLiteralProperties(prop);
            while (lista.hasNext()){
                SemanticLiteral lit = lista.next();
                String fname = lit.getString();
                if (fname.startsWith(prop.getName()))
                {
                    fname = fname.substring(prop.getName().length()+1);
                }
                buffer.append("<option>"+fname+"</option>");
            }
            buffer.append("</select>");
        }
//        buffer.append("</td>\n");
//        } else {
//            buffer.append("<td>"+obj.getProperty(prop)+"</td>");
//        }
//        buffer.append("</tr></table>");

//        if (prop.isRequired())
//        {
//            buffer.append("<script> foo." + cad + "=false; </script>");
//        } else
//        {
//            buffer.append("<script> foo." + cad + "=true; </script>");
//        }

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
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop)
    {
        //System.out.println("********************** FlashFileUploader.process **********************");
        if (request.getParameter(prop.getName()+"_delFile")!=null){
            if (prop.getName().startsWith("has")){
                Iterator<SemanticLiteral>list=obj.listLiteralProperties(prop);

            Set<String>grupo=new TreeSet<String>();
            while (list.hasNext())
            {
                grupo.add(list.next().getString());
            }
            String[]params = request.getParameterValues(prop.getName()+"_delFile");
            for (String valor:params){
                grupo.remove(prop.getName()+"_"+valor);
                delfile(obj, prop.getName()+"_"+valor);
            }
            obj.removeProperty(prop);
            for (String valor:grupo)
            {
                obj.addLiteralProperty(prop, new SemanticLiteral(valor));
            }
            } else {
                delfile(obj, prop.getName()+"_"+request.getParameter(prop.getName()+"_delFile"));
                obj.removeProperty(prop);
            }
        }
        String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
        File dir = new File(destpath);
        if (!dir.exists() && !dir.mkdirs())
        {
            throw new SWBRuntimeException("Can't create work directory " + dir);
        }
        String cad = request.getParameter(prop.getName());
        List<UploadedFile> lista = UploaderFileCacheUtils.get(cad);
        for (UploadedFile arch : lista)
        {
            File orig = new File(arch.getTmpuploadedCanonicalFileName());
            String webpath = obj.getWorkPath() + arch.getOriginalName();
            File dest = new File(dir, prop.getName()+"_"+arch.getOriginalName());
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
            if (prop.getName().startsWith("has"))
            {
                obj.addLiteralProperty(prop, new SemanticLiteral(prop.getName()+"_"+arch.getOriginalName()));
            } else
            {
                obj.setProperty(prop, prop.getName()+"_"+arch.getOriginalName());
            }
        }
        UploaderFileCacheUtils.clean(cad);
    }



    /**
     * Config file request.
     *
     * @param prop the prop
     * @return the upload file request
     */
    protected UploadFileRequest configFileRequest(SemanticProperty prop)
    {
        //System.out.println("********************** FlashFileUploader.configFileRequest **********************");
        //System.out.println("Tengo filtro "+getFileFilter()+"|--");
        //System.out.println("*Prop:"+prop.getName());
        //System.out.println("*FileMaxSize:"+getFileMaxSize());

        boolean multiple = prop.getName().startsWith("has");
        //System.out.println("filter:"+getFileFilter());
        HashMap<String, String> filtros = new HashMap<String, String>();
        if (null == getFileFilter() || "".equals(getFileFilter()))
        {
            filtros.put("All Files", "*.*");
        }else {
            String[]cads = getFileFilter().split("\\|");
            for (String line:cads){
                //System.out.println("cadena:"+line);
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
    protected void delfile(SemanticObject obj, String valor)
    {
        String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
        File dir = new File(destpath);
        File dest = new File(dir, valor);
        dest.delete();
    }

}
