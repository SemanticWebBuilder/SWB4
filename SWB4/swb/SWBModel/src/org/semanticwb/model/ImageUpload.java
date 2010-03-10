package org.semanticwb.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

public class ImageUpload extends org.semanticwb.model.base.ImageUploadBase
{

    public ImageUpload(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang)
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
        buffer.append(" if (!canSubmit) alert('Debe enviar primero los archivos seleccionados');\n");
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
        buffer.append("<iframe src=\"" + url + "\" frameborder=\"0\" width=\"305\" "
                + "scrolling=\"no\" name=\"ifrupd" + cad + "\" id=\"ifrupd" + cad + "\" height=\"200\" ></iframe>\n");
        buffer.append("<input type=\"hidden\" name=\"" + prop.getName() + "\" value=\"" + cad + "\" />\n");
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
            if (prop.getName().startsWith("has"))
            {
                obj.addLiteralProperty(prop, new SemanticLiteral(webpath));
            } else
            {
                obj.setProperty(prop, webpath);
            }
        }
        UploaderFileCacheUtils.clean(cad);
    }

    private void imgPrpcess(File dest)
    {
    }

    private UploadFileRequest configFileRequest(SemanticProperty prop)
    {
        boolean multiple = prop.getName().startsWith("has");
        System.out.println("filter:"+getFileFilter());
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
                System.out.println("cadena:"+line);
                String[]parts = line.split(":");
                filtros.put(parts[0], parts[1]);
            }
        }
        return new UploadFileRequest(filtros, multiple, getFileMaxSize());
    }
}
