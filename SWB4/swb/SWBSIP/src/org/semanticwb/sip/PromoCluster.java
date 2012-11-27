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
package org.semanticwb.sip;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.ImageResizer;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

// TODO: Auto-generated Javadoc
/**
 * PromoCluster se encarga de desplegar y administrar un texto promocional con una
 * imagen opcional bajo ciertos criterios(configuraci�n de recurso). Es un recurso
 * que viene de la versi�n 2 de WebBuilder y puede ser instalado como recurso de
 * estrategia o recurso de contenido.
 *
 * PromoCluster displays and administrate a promocional text with an optional image
 * under criteria like configuration. This resource comes from WebBuilder 2 and can
 * be installed as content resource or a strategy resource.
 *
 * @Autor:Jorge Jiménez
 */

public class PromoCluster extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(PromoCluster.class);
    
    /** The web work path. */
    private static final String webWorkPath= SWBPortal.getWebWorkPath();
    private static final String _PFX_ = "vnt_";
    private Long serial = new Long(0);
//    private static final String _CPT_ = "caption";
//    private static final String _RMN_ = "info";
//    private static final String _URL_ = "url";
//    private static final String _IMG_ = "img";
    
    /**
     * Sets the resource base.
     * 
     * @param base the new resource base
     */    
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            //webWorkPath = (String) SWBPortal.getWebWorkPath() +  base.getWorkPath() + "/";
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("processRequest..... modo="+paramRequest.getMode());
        if (paramRequest.getMode().equalsIgnoreCase("add")) {
            doAdd(request, response, paramRequest);
        }else if (paramRequest.getMode().equalsIgnoreCase("remove")) {
            doRemove(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = paramRequest.getResourceBase();

//        String cluster = base.getAttribute("cluster","carrusel");
//        Iterator<ResourceType> itResourceTypes=paramRequest.getWebPage().getWebSite().listResourceTypes();
//        while( itResourceTypes.hasNext() ) {
//            ResourceType rt = itResourceTypes.next();
//            if( rt.getId().equalsIgnoreCase("Banner") ) {
//                Iterator<ResourceSubType> itResSubTypes=rt.listSubTypes();
//                while( itResSubTypes.hasNext() ) {
//                    ResourceSubType st = itResSubTypes.next();
//                    if( st.getId().equalsIgnoreCase(cluster) ) {
//                        Iterator<Resource> itRes = Resource.ClassMgr.listResourceByResourceSubType(st, paramRequest.getWebPage().getWebSite());
//                        while( itRes.hasNext() ) {
//                            Resource r = itRes.next();
//                            r.getAttribute("");
//                        }
//                        break;
//                    }
//                }
//                break;
//            }
//        }



        response.setContentType("text/html; charset=utf-8");
        PrintWriter w = response.getWriter();

        w.println("<script type=\"text/javascript\">");
        w.println("    dojo.require('dojox.fx');");
        
        w.println("    function expande(domId) {");
        w.println("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:174} );");
        w.println("      a.play();");
        w.println("    }");

        w.println("    function collapse(domId) {");
        w.println("      var a=dojox.fx.wipeTo( {node:domId, duration:200, height:34} );");
        w.println("      a.play();");
        w.println("    }");
        w.println("</script>");

//        ArrayList<String> vnts = new ArrayList();
//        Iterator<String> it = base.getAttributeNames();
//        while(it.hasNext()) {
//            String p = it.next();
//            if(p.startsWith(_PFX_)) {
//                vnts.add(p);
//            }
//        }
//        Collections.sort(vnts);

        int idx=0;
        w.println("<div class=\"swb-promo-cluster\" style=\"border:1px solid #FF3300; width:auto; height:202px; overflow-x:scroll; overflow-y:hidden; position:relative; float:left;\">");
        w.println("<div style=\"width:auto; height:175px;\">");
//        it = vnts.iterator();
//        while(it.hasNext()) {
//            String p = it.next();
//            if(p.startsWith(_PFX_)) {
        w.println("<div class=\"swb-promo-cluster-ci\" style=\"position:relative; float:left; width:181px;height:174px;border:1px solid #000; margin:4px;\" onclick=\"window.location.href='"+base.getAttribute("vnt_1-url")+"'\">");
        w.println("<div style=\"position:absolute;bottom:0px; width:178px;height:34px; overflow:hidden;border:1px solid #345; background-color:#CCCCCC\" id=\""+base.getId()+"_"+(idx++)+"\" onmouseover=\"expande(this.id)\" onmouseout=\"collapse(this.id)\">");
        w.println("<p>"+base.getAttribute("vnt_1-caption")+"</p>");
        w.println("<p>"+base.getAttribute("vnt_1-info")+"</p>");
        w.println("</div>");
        w.println("<div><img src=\""+webWorkPath+base.getAttribute("vnt_1-img")+"\" alt=\"\" /></div>");
        w.println("</div>");
        w.println("<div class=\"swb-promo-cluster-ci\" style=\"position:relative; float:left; width:181px;height:174px;border:1px solid #000; margin:4px;\" onclick=\"window.location.href='"+base.getAttribute("vnt_2-url")+"'\">");
        w.println("<div style=\"position:absolute;bottom:0px; width:178px;height:34px; overflow:hidden;border:1px solid #345; background-color:#CCCCCC\" id=\""+base.getId()+"_"+(idx++)+"\" onmouseover=\"expande(this.id)\" onmouseout=\"collapse(this.id)\">");
        w.println("<p>"+base.getAttribute("vnt_2-caption")+"</p>");
        w.println("<p>"+base.getAttribute("vnt_2-info")+"</p>");
        w.println("</div>");
        w.println("<div><img src=\""+webWorkPath+base.getAttribute("vnt_2-img")+"\" alt=\"\" /></div>");
        w.println("</div>");
        w.println("<div class=\"swb-promo-cluster-ci\" style=\"position:relative; float:left; width:181px;height:174px;border:1px solid #000; margin:4px;\" onclick=\"window.location.href='"+base.getAttribute("vnt_1-url")+"'\">");
        w.println("<div style=\"position:absolute;bottom:0px; width:178px;height:34px; overflow:hidden;border:1px solid #345; background-color:#CCCCCC\" id=\""+base.getId()+"_"+(idx++)+"\" onmouseover=\"expande(this.id)\" onmouseout=\"collapse(this.id)\">");
        w.println("<p>"+base.getAttribute("vnt_1-caption")+"</p>");
        w.println("<p>"+base.getAttribute("vnt_1-info")+"</p>");
        w.println("</div>");
        w.println("<div><img src=\""+webWorkPath+base.getAttribute("vnt_1-img")+"\" alt=\"\" /></div>");
        w.println("</div>");
//            }
//        }
        w.println("</div>");
        w.println("</div>");
        w.flush();
    }

    private String renderWithStyle() {
        StringBuilder out = new StringBuilder();
        Resource base=getResourceBase();

        int width;
        try {
            width = Integer.parseInt(base.getAttribute("width","143"));
        }catch(NumberFormatException nfe) {
            width = 143;
        }
        int height;
        try {
            height = Integer.parseInt(base.getAttribute("height","208"));
        }catch(NumberFormatException nfe) {
            height = 208;
        }

        String textcolor = base.getAttribute("textcolor");

        String title = base.getAttribute("title");
        String titleStyle = base.getAttribute("titleStyle","");

        String subtitle = base.getAttribute("subtitle");
        String subtitleStyle = base.getAttribute("subtitleStyle","");

        String imgfile = base.getAttribute("imgfile");
        String caption = base.getAttribute("caption");
        String captionStyle = base.getAttribute("captionStyle","");

        String imgWidth="";
        if(base.getAttribute("imgWidth")!=null){
            imgWidth = "width=\"" + base.getAttribute("imgWidth")+"\"";
        }
        
        String imgHeight="";
        if(base.getAttribute("imgHeight")!=null){
            imgHeight = "height=\"" + base.getAttribute("imgHeight")+"\"";
        }
        
        String text = base.getAttribute("text");
        String textStyle = base.getAttribute("textStyle","");

        String more = base.getAttribute("more");
        String moreStyle = base.getAttribute("moreStyle","");
        String url = base.getAttribute("url");

        int imgPos;
        try {
            imgPos = Integer.parseInt(base.getAttribute("imgPos","1"));
        }catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //title
            if(title != null) {
                out.append("<h1 class=\"swb-promo-cluster-title\"><span style=\""+titleStyle+"\"> \n");
                out.append(title);
                out.append("</span></h1> \n");
            }

            //marco
//            out.append("<div class=\"swb-promo-cluster\" style=\"");
//            if(textcolor != null) {
//                out.append("color:"+textcolor+";");
//            }
//            if(width>0)  {
//                out.append("width:"+width+"px;");
//            }
//            if(height>0) {
//                out.append("height:"+height+"px;");
//            }
//            out.append("\">\n");

            //image
            /*String margin = "";
            StringBuilder img = new StringBuilder("");*/
            if(imgfile != null) {
                out.append("<div class=\"swb-promo-cluster-ci\" style=\"position:relative;width:185px;height:174px;\"> \n");
                out.append("<div><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></div> \n");
                out.append("<div style=\"position:absolute;bottom:0px;width:185px;height:34px;\" id=\"txt_"+base.getId()+"\">\n");
                out.append("<p>"+caption+"</p>\n");
                out.append("<p>"+text+"</p>\n");
                out.append("</div>\n");
                out.append("</div>\n");

//out.println("<script type=\"text/javascript\">");
//out.println("function expande() {");
//out.println("  var anim1 = dojo.fx.wipeIn( {node:\""+poll+base.getId()+"\", duration:500 });");
//out.println("  var anim2 = dojo.fadeIn({node:\""+poll+base.getId()+"\", duration:650});");
//out.println("  dojo.fx.combine([anim1,anim2]).play();");
//out.println("}");
//
//out.println("function collapse() {");
//out.println("  var anim1 = dojo.fx.wipeOut( {node:\""+poll+base.getId()+"\", duration:500 });");
//out.println("  var anim2 = dojo.fadeOut({node:\""+poll+base.getId()+"\", duration:650});");
//out.println("  dojo.fx.combine([anim1, anim2]).play();");
//out.println("}");
//out.println("</script>");
            }

            //subtitle
            /*if(subtitle != null) {
                out.append("<h2 style=\""+subtitleStyle+"\"><span>"+subtitle+"</span></h2> \n");
            }*/

            //marco
//            out.append("</div>");
        }catch (Exception e) {
            System.out.println("Error while setting resource base: "+base.getId() +"-"+ base.getTitle()+e);
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        return out.toString();
    }

    /**
     * Process action.
     * 
     * @param request the request
     * @param response the response
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        //String action = request.getParameter("act");
        Resource base = response.getResourceBase();
        String action = response.getAction();
        response.setMode(response.Mode_ADMIN);
        System.out.println("\n\naction "+action);
        if(action == null) {
            HashMap<String, String> params = upload(request);

            if(params.containsValue("add")) {
                System.out.println("processAction.... adding.....");
                long idx = 0;
                synchronized(serial) {
                    idx = serial;
                    serial++;
                }
//                int idx;
//                try {
//                    idx = Integer.parseInt(base.getAttribute("idx", "0"));
//                }catch(NumberFormatException nfe) {
//                    idx=0;
//                }
                String pf = _PFX_ + idx + "-";
                base.setAttribute(pf+"caption", params.get("caption"));
                base.setAttribute(pf+"info", params.get("info"));
                base.setAttribute(pf+"url", params.get("lru"));
                base.setAttribute(pf+"img", params.get("filename"));
//                base.setAttribute("idx", Integer.toString(++idx));
                try {
                    base.updateAttributesToDB();
                }catch(SWBException swbe) {
                    System.out.println("\n\nerror\n"+swbe);
                    log.error("Error while write attributes to resource: "+base.getTitle() +" with id = "+ base.getTitle(), swbe);
                }
            }
        }else if(response.Action_REMOVE.equalsIgnoreCase(action)) {
            System.out.println("remove");
            System.out.println("pf="+request.getParameter("vnt"));
            String pf = request.getParameter("vnt");
            Iterator<String> it = base.getAttributeNames();
            while(it.hasNext()) {
                String param = it.next();
                if(param.startsWith(pf)) {
                    base.removeAttribute(param);
                }
            }
            try {
                base.updateAttributesToDB();
            }catch(SWBException swbe) {
                System.out.println("\n\nerror\n"+swbe);
                log.error("Error while remove attributes to resource: "+base.getTitle() +" with id = "+ base.getTitle(), swbe);
            }
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        PrintWriter w = response.getWriter();
        StringBuilder out = new StringBuilder();

        SWBResourceURL  remove = paramRequest.getActionUrl();
        remove.setAction(paramRequest.Action_REMOVE);
//        SWBResourceURL action = paramRequest.getActionUrl();
//        action.setAction(paramRequest.Action_REMOVE);

        SWBResourceURL urlAdd=paramRequest.getRenderUrl();
        urlAdd.setMode("add");

        SWBResourceURL urlConfig=paramRequest.getRenderUrl();
        urlConfig.setMode("config");

        w.println("<script type=\"text/javascript\">");
        w.println("<!--");
        w.println("    function validateRemove(url) {");
        w.println("        if(confirm('¿Esta seguro de borrar la vineta?')) {");
        w.println("            window.location.href=url;");
        w.println("        }");
        w.println("    }");
        w.println("-->");
        w.println("</script>");
        
        w.println("<div class=\"swbadmin\">");
        w.println("<fieldset>");
        w.println("<legend></legend>");
        w.println("  <a href=\""+urlAdd+"\">Agregar una viñeta</a><br />");
        w.println("  <a href=\""+urlConfig+"\">Configurar recurso</a><br />");
        w.println("</fieldset>");

        ArrayList<String> vnts = new ArrayList();
        Iterator<String> it = base.getAttributeNames();
        while(it.hasNext()) {
            String p = it.next();
            if(p.startsWith(_PFX_)) {
                vnts.add(p);
            }
        }
        Collections.sort(vnts);
        w.println("<fieldset>");
        w.println("<legend>Listado de Vinetas</legend>");
        w.println("<table class=\"\">");
        w.println("    <tr>");
        w.println("        <th>Acción</th>");
        w.println("        <th>Título</th>");
        w.println("        <th>Imagen</th>");
        w.println("        <th>Resumen</th>");
        w.println("        <th>URL</th>");
        w.println("    </tr>");
        it = vnts.iterator();
        while(it.hasNext()) {
            String vnt = it.next();
            String pf = vnt.substring(0, vnt.indexOf("-"));
            System.out.println("vnt="+vnt+", pf="+pf);

            remove.setParameter("vnt", pf);            
            String removeUrl = "validateRemove('" + remove + "')";

            w.println("<tr>");
            w.println("<td><a href=\"\">Editar</a>|<a href=\"#\" onclick=\""+removeUrl+"\">Eliminar</a></td>");
            w.println("<td>"+base.getAttribute(vnt)+"</td>");
            w.println("<td><img alt=\"\" src=\""+webWorkPath+base.getAttribute(it.next())+"\" /></td>");
            w.println("<td>"+base.getAttribute(it.next())+"</td>");
            w.println("<td>"+base.getAttribute(it.next())+"</td>");
            w.println("</tr>");
        }
        w.println("</table>");
        w.println("</fieldset>");
        w.println("</div>");
        
    }


    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL action=paramRequest.getActionUrl();
        PrintWriter w = response.getWriter();

        w.println("<script type=\"text/javascript\">");
        w.println("<!--");
        w.println(" dojo.require(\"dijit.form.ValidationTextBox\");");
        w.println(" dojo.require(\"dijit.form.Textarea\");");
        w.println(" dojo.require(\"dijit.form.Button\");");

        w.println("function validaForma() {");
        w.println("    var foto = document.frmaddNew.img.value;");
        w.println("    if(!foto) {");
        w.println("        alert('¡Debe ingresar la imagen de la vineta!');");
        w.println("        document.frmaddNew.img.focus();");
        w.println("        return false;");
        w.println("    }");
        w.println("    document.frmaddNew.submit();");
        w.println("}");
        
        w.println("-->");
        w.println("</script>");

        w.println("<div class=\"x\">");
        w.println(" <div class=\"x\">");
        w.println("        <a class=\"x\" onclick=\"validaForma()\" href=\"#\">Guardar</a>");
        w.println("        <a class=\"x\" href=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMIN)+"\">Cancelar</a>");
        w.println("    </div>");
        w.println("<form name=\"frmaddNew\" id=\"frmaddNew\" enctype=\"multipart/form-data\" method=\"post\" action=\""+action+"\">");
        w.println("    <input type=\"hidden\" name=\"act\" value=\"add\"/>");
        w.println("    <fieldset>");
        w.println("        <legend>Agregar Noticia</legend>");
        w.println("    <p>");
        w.println("        <label for=\"caption\">Título:&nbsp;</label><br />");
        w.println("        <input id=\"caption\" type=\"text\" name=\"caption\" maxlength=\"50\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Título\" />");
        w.println("    </p>");
        w.println("    <p>");
        w.println("        <label for=\"info\">Resumen:&nbsp;</label><br />");
        w.println("        <textarea id=\"info\" cols=\"45\" rows=\"3\" name=\"info\"></textarea>");
        w.println("    </p>");
        w.println("    <p>");
        w.println("        <label for=\"lru\">URL:&nbsp;</label><br />");
        w.println("        <input id=\"lru\" type=\"text\" name=\"lru\" maxlength=\"80\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"URL\" />");
        w.println("    </p>");
        w.println("    <p>");
        w.println("        <label for=\"img\">Imagen de la vineta:&nbsp;</label><br />");
        w.println("        <input id=\"img\" type=\"file\" name=\"img\" size=\"45\" />");
        w.println("    </p>");
        w.println("    </fieldset>");
        w.println("</form>");
        w.println("    <div class=\"x\">");
        w.println("        <a class=\"x\" onclick=\"validaForma()\" href=\"#\">Guardar</a>");
        w.println("        <a class=\"x\" href=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMIN)+"\">Cancelar</a>");
        w.println("    </div>");
        w.println("</div>");
        w.flush();
    }
    
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.doEdit(request, response, paramRequest);
    }


    public void doRemove(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

    }

    private HashMap<String, String> upload(HttpServletRequest request) {
        final String realpath = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/";
        final String path = getResourceBase().getWorkPath() + "/";

        HashMap<String, String> params = new HashMap<String, String>();
        try
        {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart)
            {
                File tmpwrk = new File(SWBPortal.getWorkPath() + "/tmp");
                if (!tmpwrk.exists())
                {
                    tmpwrk.mkdirs();
                }
                FileItemFactory factory = new DiskFileItemFactory(1 * 1024 * 1024, tmpwrk);
                ServletFileUpload upload = new ServletFileUpload(factory);
                ProgressListener progressListener = new ProgressListener()
                {

                    private long kBytes = -1;

                    public void update(long pBytesRead, long pContentLength, int pItems)
                    {
                        long mBytes = pBytesRead / 10000;
                        if (kBytes == mBytes)
                        {
                            return;
                        }
                        kBytes = mBytes;
                        int percent = (int) (pBytesRead * 100 / pContentLength);
                    }
                };
                upload.setProgressListener(progressListener);
                List items = upload.parseRequest(request); /* FileItem */
//                FileItem currentFile = null;
                Iterator iter = items.iterator();
                while (iter.hasNext())
                {
                    FileItem item = (FileItem) iter.next();

                    if(item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        params.put(name, value);
                    }else {
//                        currentFile = item;
//                        File file = new File(path);
//                        if (!file.exists())
//                        {
//                            file.mkdirs();
//                        }

                        try {
                            long serial = (new Date()).getTime();
                            String filename = serial + "_" + item.getFieldName() + item.getName().substring(item.getName().lastIndexOf("."));

                            File image = new File(realpath);
                            if (!image.exists()) {
                                image.mkdir();
                            }
                            image = new File(realpath + filename);
                            item.write(image);
//                            File thumbnail = new File(realpath + "thumbn_" + filename);
                            File thumbnail = new File(realpath + filename);
                            boolean done = ImageResizer.shrinkTo(image, 143,208, thumbnail, "jpeg");
//                            if(!done)
//                                image.delete();

                            params.put("filename", path + filename);
//                            params.put("thumbnail", path + "thumbn_" + filename);
                        }catch (StringIndexOutOfBoundsException iobe) {
                            System.out.println("\n\nerror en upload.......\n"+iobe);
                            log.error(iobe);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return params;
    }
}
