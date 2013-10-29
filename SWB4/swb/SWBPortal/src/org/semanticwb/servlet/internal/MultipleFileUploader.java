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
package org.semanticwb.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class MultipleFileUploader.
 * 
 * @author serch
 */
public class MultipleFileUploader implements InternalServlet
{

    public static Logger log = SWBUtils.getLogger(MultipleFileUploader.class);

    {
        File tmpplace = new File(org.semanticwb.SWBPortal.getWorkPath() + "/tmp/");
        if (tmpplace.exists())
        {
            File[] childs = tmpplace.listFiles();
            if (null!=childs){
                for (File tmpfile : childs)
                {
                    UploaderFileCacheUtils.delete(tmpfile);
                }
            } else {
                log.error("MultipleFileUploader: Problem doing listFiles on "+tmpplace.getAbsolutePath());
            }
        }
        UploaderFileCacheUtils.setHomepath(org.semanticwb.SWBPortal.getWorkPath());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(ServletContext)
     */
    public void init(ServletContext config) throws ServletException
    {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(HttpServletRequest, HttpServletResponse, DistributorParams)
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        //System.out.println("********************** MulpleFileUploades.doProcess**********************");
        String uri = request.getRequestURI();
        String cntx = request.getContextPath();
        String path = uri.substring(cntx.length());
        String iserv = "";


        if (path == null || path.length() == 0)
        {
            path = "/";
        } else
        {
            int j = path.indexOf('/', 1);
            if (j > 0)
            {
                iserv = path.substring(1, j);
            } else
            {
                iserv = path.substring(1);
            }
        }
        String cad = path.substring(path.lastIndexOf("/") + 1);

        String auri = path.substring(iserv.length() + 1);
        //System.out.println("cad:"+cad+" auri:"+auri+" user:"+dparams.getUser().getLogin());
        //DistributorParams dparam = null;
        String smodel = auri.substring(1, auri.indexOf("/", 2));
        SWBModel model=SWBContext.getSWBModel(smodel);
        //System.out.println("model:"+model);
        WebSite website=null;
        if(!(model instanceof WebSite))
        {        
            website=model.getParentWebSite();
        }else
        {
            website=(WebSite)model;
        }
        //Agregado Jorge Jiménez para SWBSocial-12/Agosto/2013, ya que se quedaba nula la variable website y marcaba error en SWBUser
        //Se utiliza este componente desde la edición de un usuario en el repositorio de Admin y al final llega Nulo el website hasta este paso.
        if(website==null)
        {
            website=SWBContext.getAdminWebSite();
        }
        //Termina agregado Jorge Jimenez
        
        User user=SWBPortal.getUserMgr().getUser(request, website);
        if (!user.isSigned()){
            website=SWBContext.getWebSite("SWBAdmin");
            user=SWBPortal.getUserMgr().getUser(request, website);
        }
//        if (dparams.getUser().isSigned()) {
//            dparam=dparams;
//        }
//        else {
//            dparam=new DistributorParams(request, auri);
//            if (!dparam.getUser().isSigned()){
//                DistributorParams dparamt=new DistributorParams(request, "/multiuploader/SWBAdmin/home");
//                if (dparamt.getUser().isSigned()){
//                    dparam=dparamt;
//                }
//            }
//        }
        //System.out.println("user: " + user.getFullName() + ":" + user.isSigned());
        if (ServletFileUpload.isMultipartContent(request))
        {
            response.setContentType("application/json");
            List<UploadedFile> archivos = UploaderFileCacheUtils.get(cad); //System.out.println("archivos:"+archivos);
            String subcad = "f" + archivos.size() + cad.substring(4); //si no existe archivos, debemos ignorar el request, el NPE nos facilita salir
            if (archivos != null)
            {
                File tmpplace = new File(org.semanticwb.SWBPortal.getWorkPath() + "/tmp/" + cad);
                if (!tmpplace.exists())
                {
                    tmpplace.mkdirs();
                    request.getSession(true).setAttribute(cad, new UploadsCleanUp(tmpplace.getCanonicalPath()));
                }
                DiskFileItemFactory factory = new DiskFileItemFactory(3000, tmpplace);
                ServletFileUpload upload = new ServletFileUpload(factory);
                if (UploaderFileCacheUtils.getRequest(cad).size()>0)
                {
                    upload.setSizeMax(UploaderFileCacheUtils.getRequest(cad).size());
                }
                try
                {

                    List<FileItem> items = upload.parseRequest(request);
                    String lastUploadedFileName = "";
                    for (FileItem item : items)
                    {
                        if (!item.isFormField())
                        {
                            String fieldName = item.getFieldName();
                            String fileName = item.getName();
                            if (fileName.indexOf("\\")>=0)
                                    {
                                fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
                            }
                            //100112 MAPS Cleaning of filename
                            fileName = SWBUtils.TEXT.replaceSpecialCharacters(fileName, '.', true);
                            //--End
                            String contentType = item.getContentType();
                            boolean isInMemory = item.isInMemory();
                            long sizeInBytes = item.getSize();
                            File uploadedFile = new File(tmpplace, subcad + "_" + fileName + "_");
                            item.write(uploadedFile);
                            archivos.add(new UploadedFile(fileName, uploadedFile.getCanonicalPath(), cad));
                            lastUploadedFileName = fileName;
                        }
                    }
                    response.getWriter().println("{\"status\":\"success\", \"detail\":\""+lastUploadedFileName+"\"}");
                } catch (Exception ex)
                {
                    response.getWriter().println("{\"status\":\"error\", \"detail\":\""+ex.getMessage()+"\"}");
                    throw new SWBRuntimeException("Uploading file", ex);
                }

            } else
            {
                response.getWriter().println("{\"status\":\"error\", \"detail\":\"UploadService not pre-registered\"}");
//                response.sendError(500, "UploadService not pre-registered");
            }
        } else if (user.isSigned())
        {
            if (UploaderFileCacheUtils.get(cad) == null)
            {
                UploaderFileCacheUtils.put(cad, new java.util.LinkedList<UploadedFile>());
            }
            response.getWriter().print(getPage(SWBPortal.getContextPath()+request.getServletPath(),cad));

        } else
        {
            response.getWriter().println("{\"status\":\"error\", \"detail\":\"Not enough privileges\"}");
//            response.sendError(403, "Not enough privileges");
        }


//        // Check that we have a file upload request
//        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//
//        // Create a factory for disk-based file items
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//// Set factory constraints
//        factory.setSizeThreshold(50000);
//        factory.setRepository(new File(""));
//
//// Create a new file upload handler
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//// Set overall request size constraint
//        upload.setSizeMax();
//
//// Parse the request
//        List /* FileItem */ items = upload.parseRequest(request);
//



    }

    /**
     * Gets the page.
     * 
     * @param ruta the ruta
     * @param cad the cad
     * @return the page
     */
    private String getPage(String ruta, String cad)
    {
        //System.out.println("********************** MulpleFileUploades.getPage**********************");

        UploadFileRequest requested = UploaderFileCacheUtils.getRequest(cad);
        StringBuilder buffer = new StringBuilder();
        if (null!=requested){
        buffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\">\n");
        buffer.append("<html lang=\"en\">\n");
        buffer.append("    <head>\n");
        buffer.append("        <title>Flash HTML</title>\n");
        buffer.append("        <link href=\""+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dijit/themes/dijit.css\" rel=\"stylesheet\" />\n");
     //   buffer.append("        <link href=\""+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dijit/themes/tundra/form/Button.css\" rel=\"stylesheet\" />\n");
     //   buffer.append("        <link href=\""+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dijit/themes/tundra/ProgressBar.css\" rel=\"stylesheet\" />\n");
        buffer.append("        <link href=\""+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dojox/form/resources/FileUploader.css\" rel=\"stylesheet\" />\n");
        buffer.append("        <script>\n");
        buffer.append("            var f;\n");
        buffer.append("            djConfig = {\n");
        buffer.append("                isDebug: false,\n");
        buffer.append("                popup:true,\n");
        buffer.append("                parseOnLoad: true\n");
        buffer.append("            }\n");
        buffer.append("        </script>\n");
        buffer.append("        <script src=\""+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dojo/dojo.js\"></script>\n");
        buffer.append("        <script>\n");
        buffer.append("            dojo.require(\"dojox.form.FileUploader\");\n");
        buffer.append("            dojo.require(\"dijit.form.Button\");\n");
        buffer.append("            dojo.require(\"dijit.ProgressBar\");\n");
        buffer.append("            dojo.require(\"dijit.form.Form\");\n");
        buffer.append("		\n");
        buffer.append("            addThumb = function(/*d, id*/){\n");
        buffer.append("                //console.log(\"THUMB:\", d);\n");
        buffer.append("                parent.canContinue('"+cad+"');\n");
//        buffer.append("                var fileRoot = dojo.moduleUrl(\"dojox.form\", \"tests\").toString();\n");
//        buffer.append("                //var img = '<img src='+fileRoot+\"/\"+escape(d.file)+\n");
//        buffer.append("                var img = '<img src='+escape(d.file)+\n");
//        buffer.append("                    (d.width>d.height ?\n");
//        buffer.append("                    ' width=\"50\"/>' :\n");
//        buffer.append("                    ' height=\"50\"/>');\n");
//        buffer.append("                console.log(\"IMG:\", img)\n");
//        buffer.append("                var str = '<div id=\"file_'+d.name+'\" class=\"thumb\"><div class=\"thumbPic\">'+img+'</div>';\n");
//        buffer.append("                str += '<div class=\"thumbText\">';\n");
//        buffer.append("                if(d.fGroup || d.hGroup){\n");
//        buffer.append("                    str += 'Group: '+(d.fGroup || d.hGroup)+'<br/>';\n");
//        buffer.append("                }\n");
//        buffer.append("                str += 'Title: '+d.name+'<br/>';\n");
//        buffer.append("                if(d.author){\n");
//        buffer.append("                    str += 'Author: '+ d.author+'<br/>';\n");
//        buffer.append("                }\n");
//        buffer.append("                if(d.date){\n");
//        buffer.append("                    str += d.date+' ';\n");
//        buffer.append("                }\n");
//        buffer.append("                str += Math.ceil(d.size*.001)+'kb</div></div>';\n");
//        buffer.append("                dojo.byId(id).innerHTML += str;\n");
        buffer.append("            }\n");
        buffer.append("            dojo.addOnLoad(function(){\n");
        buffer.append("\n");
        buffer.append("                var props = {\n");
        buffer.append("                    isDebug:false,\n");
        buffer.append("                    hoverClass:\"uploadHover\",\n");
        buffer.append("                    activeClass:\"uploadPress\",\n");
        buffer.append("                    disabledClass:\"uploadDisabled\",\n");
        buffer.append("                    uploadUrl:dojo.moduleUrl(\"dojox.form\", \"" + ruta + "\"),\n");
        buffer.append("                    fileMask:[\n");
        StringBuilder filts = new StringBuilder();
        Set<String> keys = requested.getFiltros().keySet();
        for (String key:keys){
            String value=requested.getFiltros().get(key);
            if (filts.length()>0) filts.append("\"],\n");
            filts.append("                        [\"");
            filts.append(key);
            filts.append("\", 	\"");
            filts.append(value);
        }
        filts.append("\"]\n");
        buffer.append(filts);
//        buffer.append("                        [\"Jpeg File\", 	\"*.jpg;*.jpeg\"],\n");
//        buffer.append("                        [\"GIF File\", 	\"*.gif\"],\n");
//        buffer.append("                        [\"PNG File\", 	\"*.png\"],\n");
//        buffer.append("                        [\"All Images\", 	\"*.jpg;*.jpeg;*.gif;*.png\"]\n");
        buffer.append("                    ]\n");
        buffer.append("                }\n");
        buffer.append("			\n");
        buffer.append("                if(dojo.byId(\"btnF\")){\n");
        buffer.append("                    dojo.byId(\"fFiles\").value = \"\"; \n");
        buffer.append("                    f = new dojox.form.FileUploader(dojo.mixin({\n");
        buffer.append("                        progressWidgetId:\"progressBar\",\n");
        buffer.append("                        showProgress:true,\n");
        buffer.append("                        fileListId:\"fFiles\",\n");
//        buffer.append("                        tabIndex:5,\n");
        buffer.append("                        selectMultipleFiles:"+requested.isMultiples()+",\n");
        buffer.append("                        deferredUploading:false\n");
        buffer.append("                    },props), \"btnF\");\n");
        buffer.append("                    /*f.attr(\"disabled\", dojo.byId(\"fGroup\").value==\"\");\n");
        buffer.append("                    dojo.connect(dojo.byId(\"fGroup\"), \"keyup\", function(){\n");
        buffer.append("                        f.attr(\"disabled\", dojo.byId(\"fGroup\").value==\"\");\n");
        buffer.append("                    });*/\n");
        buffer.append("                    dojo.connect(dijit.byId(\"fSubmit\"), \"onClick\", function(){\n");
        buffer.append("                        console.log(\"formF.action = \" + dojo.byId(\"formF\").action)\n");
        buffer.append("                        f.submit(dojo.byId(\"formF\"));\n");
        buffer.append("                    });\n");
        buffer.append("                    dojo.connect(f, \"onChange\", function(dataArray){\n");
        buffer.append("                        parent.stopSumbit('"+cad+"');\n");
        buffer.append("                    });\n");
        buffer.append("                    dojo.connect(f, \"onComplete\", function(dataArray){\n");
        buffer.append("                    //    dojo.forEach(dataArray, function(d){\n");
        buffer.append("                            addThumb(/*d, \"fThumbs\"*/);\n");
        buffer.append("                    //    });\n");
        buffer.append("                    });\n");
        buffer.append("                }\n");
        buffer.append("\n");
        buffer.append("\n");
        buffer.append("                \n");
        buffer.append("                if(dijit.byId(\"btnD\")){\n");
        buffer.append("                    var d = new FlashHTML.widget(dojo.mixin({button:dijit.byId(\"btnD\")}, props));\n");
        buffer.append("                    d.attr(\"disabled\", dojo.byId(\"dTitle\").value==\"\");\n");
        buffer.append("                    dojo.connect(dojo.byId(\"dTitle\"), \"keyup\", function(){\n");
        buffer.append("                        d.attr(\"disabled\", dojo.byId(\"dTitle\").value==\"\");\n");
        buffer.append("                    });\n");
        buffer.append("                    dojo.connect(dijit.byId(\"fSubmit\"), \"onClick\", function(){\n");
        buffer.append("                        f.submit(dojo.byId(\"formF\"));\n");
        buffer.append("                    });\n");
        buffer.append("                }\n");
        buffer.append("			\n");
        buffer.append("			\n");
        buffer.append("            });\n");
        buffer.append("        \n");
        buffer.append("        \n");
        buffer.append("        </script>\n");
        buffer.append("        <style>\n");
        buffer.append("            html, body{\n");
        buffer.append("                font-family:sans-serif;\n");
        buffer.append("                font-size:12px;\n");
        buffer.append("            }\n");
        buffer.append("            .uploadBtn{\n");
        buffer.append("                border:1px solid #333333;\n");
        buffer.append("                background:url("+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dijit/themes/soria/images/buttonEnabled.png) #d0d0d0 repeat-x scroll 0px top;\n");
        buffer.append("                font-size:14px;\n");
        buffer.append("                font-family:Arial;\n");
        buffer.append("                width:201px;\n");
        buffer.append("                height:30px;\n");
        buffer.append("                line-height:50px;\n");
        buffer.append("                vertical-align:middle; /* emulates a <button> */\n");
        buffer.append("                text-align:center;\n");
        buffer.append("            }\n");
        buffer.append("            .uploadHover{\n");
        buffer.append("                background-image:url("+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dijit/themes/soria/images/buttonHover.png);\n");
        buffer.append("                cursor:pointer;\n");
        buffer.append("                font-weight:bold;\n");
        buffer.append("                font-style:italic;\n");
        buffer.append("                font-family:serif;\n");
        buffer.append("            }\n");
        buffer.append("            .uploadPress{\n");
        buffer.append("                background-image:url("+SWBPortal.getContextPath()+"/swbadmin/js/dojo/dijit/themes/soria/images/buttonActive.png);\n");
        buffer.append("            }\n");
        buffer.append("            .uploadDisabled{\n");
        buffer.append("                background-image:none;\n");
        buffer.append("                background-color:#666;\n");
        buffer.append("                color:#999;\n");
        buffer.append("                border:1px solid #999;\n");
        buffer.append("                font-family:serif;\n");
        buffer.append("                font-style:italic;\n");
        buffer.append("            }\n");
        buffer.append("            .progBar{\n");
        buffer.append("                width:294px;\n");
        buffer.append("                display:none;\n");
        buffer.append("            }\n");
        buffer.append("            .form{\n");
        buffer.append("                width:300px;\n");
        buffer.append("                border:0px solid #ccc;\n");
        buffer.append("                margin:0px;\n");
//        buffer.append("                padding:3px;\n");
        buffer.append("                position:relative;\n");
        buffer.append("            }\n");
        buffer.append("            .form, .thumbList{\n");
        buffer.append("                float:left;\n");
        buffer.append("            }\n");
        buffer.append("            #fFiles, #hFiles{\n");
        buffer.append("                width:200px;\n");
        buffer.append("                height:75px;\n");
        buffer.append("                overflow-x:hidden;\n");
        buffer.append("                overflow-y:auto;\n");
        buffer.append("                border:1px solid #ccc;\n");
        buffer.append("            }\n");
        buffer.append("            .form .field{\n");
        buffer.append("                width:197px;\n");
        buffer.append("            }\n");
//        buffer.append("            .form label{\n");
//        buffer.append("                position:absolute;\n");
//        buffer.append("                width:80px;\n");
//        buffer.append("                text-align:right;\n");
//        buffer.append("                left:0px;\n");
//        buffer.append("            }\n");
        buffer.append("            .btn{\n"); //.form .field, .form
//        buffer.append("                margin-left:85px;\n");
        buffer.append("                margin-bottom:5px;\n");
        buffer.append("            }\n");
        buffer.append("        </style>\n");
        buffer.append("    </head>\n");
        buffer.append("    <body class=\"soria\">\n");
        buffer.append("        <form id=\"formF\" class=\"form\">\n");
        buffer.append("            <div id=\"fFiles\" class=\"field\"></div>\n");
        buffer.append("            <div id=\"btnF\" class=\"uploadBtn btn\">Seleccione Archivos</div>\n");
        buffer.append("             <button id=\"fSubmit\" class=\"btn\" dojoType=\"dijit.form.Button\">&nbsp;Enviar Archivos&nbsp;</button>\n");
        buffer.append("            <div indeterminate=\"false\" id=\"progressBar\" class=\"progBar\" dojoType=\"dijit.ProgressBar\"></div>\n");
        buffer.append("        </form>\n");
//        buffer.append("<script type=\"text/javascript\">\n");
//        buffer.append("function cliked(){\n");
//        buffer.append("f.submit(dojo.byId(\"formF\"));\n");
//        buffer.append("}\n");
//        buffer.append("</script>\n");
//        buffer.append("             <button id=\"fsubm\" class=\"btn\" dojoType=\"dijit.form.Button\" onclick=\"cliked()\">Submit</button>\n");
        buffer.append("    </body>\n");
        buffer.append("</html>\n");
        } else {
            buffer.append("<html><head><script>alert('Session Error');</script></head></html>");
        }
        return buffer.toString();
    }

}

class UploadsCleanUp implements HttpSessionBindingListener
{

    private final String path;

    public UploadsCleanUp(String path)
    {
        this.path = path;
    }

    public void valueBound(HttpSessionBindingEvent hsbe)
    {
        //do nothing
    }

    public void valueUnbound(HttpSessionBindingEvent hsbe)
    {
        File file = new File(path);
        if (file.exists())
        {
            UploaderFileCacheUtils.delete(file);
        }
    }

    
}
