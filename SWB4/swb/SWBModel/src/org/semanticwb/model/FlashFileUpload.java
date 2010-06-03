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


public class FlashFileUpload extends org.semanticwb.model.base.FlashFileUploadBase 
{
    /** The log. */
    private static Logger log = SWBUtils.getLogger(FlashFileUpload.class);

    public FlashFileUpload(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang)
    {
        System.out.println("obj: "+obj);
        System.out.println("objuri: "+obj.getURI());
        System.out.println("prop: "+prop);
        System.out.println("type: "+type);
        System.out.println("mode: "+mode);
        System.out.println("lang: "+lang);
        System.out.println("objcls: "+obj.getSemanticClass());
        System.out.println("propcls: "+prop.getDomainClass());
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
        UploaderFileCacheUtils.putRequest(cad, configFileRequest(prop));
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
        buffer.append("<input type=\"hidden\" name=\"" + prop.getName() + "\" value=\"" + cad + "\" /></td>\n");
//        if (prop.getName().startsWith("has")){
        buffer.append("<td valign=\"top\">"+eliminar+":<br/><select dojoType=\"dijit.form.MultiSelect\" name=\""
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
//        buffer.append("</select></td>");
//        } else {
//            buffer.append("<td>"+obj.getProperty(prop)+"</td>");
//        }
        buffer.append("</tr></table>");

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

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop)
    {
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



    protected UploadFileRequest configFileRequest(SemanticProperty prop)
    {
        System.out.println("*Prop:"+prop.getName());
        System.out.println("*FileMaxSize:"+getFileMaxSize());

        boolean multiple = prop.getName().startsWith("has");
//        System.out.println("filter:"+getFileFilter());
        HashMap<String, String> filtros = new HashMap<String, String>();
        if (null == getFileFilter() || "".equals(getFileFilter()))
        {
            filtros.put("All Files", "*.*");
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

    protected void delfile(SemanticObject obj, String valor)
    {
        String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
        File dir = new File(destpath);
        File dest = new File(dir, valor);
        dest.delete();
    }
}
