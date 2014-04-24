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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.FileUpload;
import org.semanticwb.base.util.ImageResizer;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.admin.resources.StyleInner;

// TODO: Auto-generated Javadoc
/**
 * ImageGallery se encarga de desplegar y administrar una colección de imágenes
 * dispuestas en un carrusel.
 * <p>
 * Cada imagen en el carrusel puede ser seleccionada para verse en detalle a tamaño real.
 *
 * ImageGallery is in charge to unfold and to administer image collection disposes in a round robin.
 * <p> 
 * Every image in the round robin can be selected for detail in real size.
 * 
 * @author : Carlos Ramos Inchaustegui
 * @version 1.0
 */

public class ImageGallery extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(ImageGallery.class);
    
    /** The adm res utils. */
    private WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    
    /** The work path. */
    private String workPath;
    
    /** The web work path. */
    private String webWorkPath;

    /** The Constant _thumbnail. */
    private static final String _thumbnail = "thumbn_";

    /** The si. */
    private StyleInner si;

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) {
        try
        {
            super.setResourceBase(base);
            workPath = SWBPortal.getWorkPath() +  base.getWorkPath() + "/";
            webWorkPath = SWBPortal.getWebWorkPath() +  base.getWorkPath() + "/";
            
            // Si no existen thumbnails se crean
            int width;
            try {
                width = Integer.parseInt(base.getAttribute("width"));
            }catch(Exception e) {
                width = 180;
            }
            Iterator<String> it = base.getAttributeNames();
            while(it.hasNext()) {
                String attname = it.next();
                String attval = base.getAttribute(attname);
                if( attname.startsWith("imggallery_") && attval!=null ) {
                    String fn = attval.substring(attval.lastIndexOf("/")+1);
                    File img = new File(workPath + fn);
                    File thumbnail = new File(workPath + _thumbnail + fn);
                    if( !thumbnail.exists() ) {
                        try {
                            ImageResizer.resizeCrop(img, width , thumbnail, "jpeg");
                        }catch(IOException ioe) {
                            log.error("Error while setting thumbnail for image: "+img+" in resource "   +base.getId() +"-"+ base.getTitle(), ioe);
                        }
                    }
                }
            }
        }
        catch(Exception e) { 
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();

        Resource base = getResourceBase();
        ArrayList<String> imgpath = new ArrayList<String>();
        Iterator<String> it = base.getAttributeNames();        
        while(it.hasNext()) {
            String attname = it.next();
            String attval = base.getAttribute(attname);
            if( attval!=null && attname.startsWith("imggallery_") ) {
                imgpath.add(webWorkPath + attval);
            }
        }
        String[] ip = new String[imgpath.size()];
        imgpath.toArray(ip);
        String script = getGalleryScript(base.getId(), Integer.parseInt(base.getAttribute("width","220")), Integer.parseInt(base.getAttribute("height","220")), Boolean.valueOf(base.getAttribute("autoplay")), Integer.parseInt(base.getAttribute("pause","2500")), Integer.parseInt(base.getAttribute("fadetime","500")), base.getAttribute("title",""), ip);
        out.print(script);
        out.flush();
    }

    /**
     * Gets the gallery script.
     * 
     * @param oid the oid
     * @param width the width
     * @param height the height
     * @param autoplay the autoplay
     * @param pause the pause
     * @param fadetime the fadetime
     * @param title the title
     * @param imagepath the imagepath
     * @return the gallery script
     */
    public String getGalleryScript(String oid, int width, int height, boolean autoplay, int pause, int fadetime, String title, String[] imagepath) {
        Resource base = getResourceBase();
        StringBuilder out = new StringBuilder();

        if(base.getAttribute("css")!=null) {
            out.append("<script type=\"text/javascript\">\n");
            out.append("<!--\n");
            out.append("  setStyleSheetByInstance('"+base.getAttribute("css")+"','"+base.getId()+"');\n");
            out.append("-->\n");
            out.append("</script>\n");
        }
        
        out.append("<script type=\"text/javascript\" src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/jquery-imagegallery.js\"></script>\n");
        out.append("<script type=\"text/javascript\" src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/jquery-1.3.js\"></script>\n");

        out.append("<script type=\"text/javascript\">\n");
        out.append("<!--\n");
        out.append("    simpleGallery_navpanel={\n");
        //customize nav panel container
        out.append("        panel: {height:'45px', opacity:0.5, paddingTop:'5px', fontStyle:'bold 9px Verdana'}, ");
        //nav panel images (in that order)
        out.append("        images: [ '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_rewind_blue.png', '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_play_blue.png', '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_fastforward_blue.png', '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_pause_blue.png'], ");
        //top offset of left, play, and right images, PLUS spacing between the 3 images
        out.append("        imageSpacing: {offsetTop:[-3, 0, -3], spacing:10}, ");
        //duration of slide up animation to reveal panel
        out.append("        slideduration: 500 ");
        out.append("    }; ");

        out.append("    var mygallery=new simpleGallery( { ");
        //ID of main gallery container
        out.append("        wrapperid: 'imggallery_"+ oid +"', ");
        //width/height of gallery in pixels. Should reflect dimensions of the images exactly
        out.append("        dimensions: ["+ width +", "+ height +"], ");
        out.append("        imagearray: [ ");


        for(String image : imagepath) {
            out.append("\n['"+image+"','"+image.substring(0, image.lastIndexOf("/"))+"/"+_thumbnail+image.substring(image.lastIndexOf("/")+1)+"',''],");
        }
        if(imagepath.length>0)
            out.deleteCharAt(out.length()-1);

        out.append("\n      ], ");
        out.append("        autoplay: "+ autoplay +", ");
        out.append("        persist: false, ");
        //pause between slides (milliseconds)
        out.append("        pause: "+ pause +", ");
        //transition duration (milliseconds)
        out.append("        fadeduration: "+ fadetime +", ");
        //event that fires when gallery has initialized/ ready to run
        out.append("        oninit:function(){}, ");
        //event that fires after each slide is shown
        //curslide: returns DOM reference to current slide's DIV (ie: try alert(curslide.innerHTML)
        //i: integer reflecting current image within collection being shown (0=1st image, 1=2nd etc)
        out.append("        onslide:function(curslide, i){} ");
        out.append("        } ");
        out.append("    );\n");
        out.append("-->\n");
        out.append("</script>\n");

        //out.append("<div class=\"swb-galeria\"> ");
        //out.append("<div style=\""+ titlestyle +"\">"+ title +"</div> ");
        out.append("<div class=\"swb-galeria_"+base.getId()+"\"> ");
        out.append("<div class=\"title_"+base.getId()+"\">"+ title +"</div> ");
        out.append("<div id=\"imggallery_"+ oid +"\" style=\"position:relative; visibility:hidden\"></div> ");
        out.append("</div>\n");

        return out.toString();
    }

    /**
     * Gets the gallery script.
     * 
     * @param divId the div id
     * @param title the title
     * @param imgpath the imgpath
     * @return the gallery script
     */
    public String getGalleryScript(String divId, String title, String[] imgpath) {
        return getGalleryScript(divId, 220, 170, false, 2500, 500, title, imgpath);
    }

    /**
     * Gets the gallery script.
     * 
     * @param imgpath the imgpath
     * @return the gallery script
     */
    public String getGalleryScript(String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), 220, 170, false, 2500, 500, "", imgpath);
    }

    /**
     * Gets the gallery script.
     * 
     * @param autoplay the autoplay
     * @param imgpath the imgpath
     * @return the gallery script
     */
    public String getGalleryScript(boolean autoplay, String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), 220, 170, autoplay, 2500, 500, "", imgpath);
    }

    /**
     * Gets the gallery script.
     * 
     * @param width the width
     * @param height the height
     * @param imgpath the imgpath
     * @return the gallery script
     */
    public String getGalleryScript(int width, int height, String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), width, height, false, 2500, 500, "", imgpath);
    }

    /**
     * Gets the gallery script.
     * 
     * @param width the width
     * @param height the height
     * @param autoplay the autoplay
     * @param imgpath the imgpath
     * @return the gallery script
     */
    public String getGalleryScript(int width, int height, boolean autoplay, String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), width, height, autoplay, 2500, 500, "", imgpath);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();

        Resource base=getResourceBase();

        String msg=paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_undefinedOperation");
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();

        if(action.equalsIgnoreCase("add") || action.equalsIgnoreCase("edit")) {
            out.println(getForm(request, paramRequest));
        }else if(action.equalsIgnoreCase("update")) {
            FileUpload fup = new FileUpload();
            try {
                fup.getFiles(request, response);

                String value = null!=fup.getValue("title") && !"".equals(fup.getValue("title").trim()) ? fup.getValue("title").trim() : null;
                base.setAttribute("title", value);
                value = null!=fup.getValue("width") && !"".equals(fup.getValue("width").trim()) ? fup.getValue("width").trim() : "180";
                base.setAttribute("width", value);
                value = null!=fup.getValue("height") && !"".equals(fup.getValue("height").trim()) ? fup.getValue("height").trim() : null;
                base.setAttribute("height", value);
                value = null!=fup.getValue("autoplay") && !"".equals(fup.getValue("autoplay").trim()) ? fup.getValue("autoplay").trim() : null;
                base.setAttribute("autoplay", value);
                value = null!=fup.getValue("pause") && !"".equals(fup.getValue("pause").trim()) ? fup.getValue("pause").trim() : null;
                base.setAttribute("pause", value);
                value = null!=fup.getValue("fadetime") && !"".equals(fup.getValue("fadetime").trim()) ? fup.getValue("fadetime").trim() : null;
                base.setAttribute("fadetime", value);
                value = null!=fup.getValue("titlestyle") && !"".equals(fup.getValue("titlestyle").trim()) ? fup.getValue("titlestyle").trim() : null;
                base.setAttribute("titlestyle", value);

                int i = 1;
                int width = Integer.parseInt(base.getAttribute("width"));
                String filenameAttr, removeChk;
                do {
                //for(int j=0; j<15; j++) {
                    filenameAttr = "imggallery_" + base.getId() + "_" + i;
                    removeChk = "remove_" + base.getId() + "_" + i;
                    value = null!=fup.getValue(removeChk) && !"".equals(fup.getValue(removeChk).trim()) ? fup.getValue(removeChk).trim() : "0";

                    if("1".equals(value) && base.getAttribute(filenameAttr)!=null) {
                        File file = new File(workPath + base.getAttribute(filenameAttr));
                        file.delete();
                        file = new File(workPath + _thumbnail + base.getAttribute(filenameAttr));
                        file.delete();
                        base.removeAttribute(filenameAttr);
                    }else {
                        value = null!=fup.getFileName(filenameAttr) && !"".equals(fup.getFileName(filenameAttr).trim()) ? fup.getFileName(filenameAttr).trim() : null;
                        if(value!=null) {
                            String filename = admResUtils.getFileName(base, value);
                            if(filename!=null && !filename.trim().equals(""))                            
                            {
                                if (!admResUtils.isFileType(filename, "bmp|jpg|jpeg|gif|png")){
                                    msg = paramRequest.getLocaleString("msgErrFileType") +" <i>bmp, jpg, jpeg, gif, png</i>: " + filename;
                                }else {
                                    if (admResUtils.uploadFile(base, fup, filenameAttr)){                                        
                                        File image = new File(workPath + filename);
                                        File thumbnail = new File(workPath + _thumbnail + filename);
                                        ImageResizer.resizeCrop(image, width, thumbnail, "jpeg");
                                        base.setAttribute(filenameAttr, filename);
                                    }else {
                                        msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                                    }
                                }
                            }else {
                                msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                            }
                        }
                    }
                    i++;
                //}
                } while(value!=null || base.getAttribute(filenameAttr)!=null);

                base.updateAttributesToDB();

                msg=paramRequest.getLocaleString("msgOkUpdateResource") +" "+ base.getId();
                out.println("<script type=\"text/javascript\" language=\"JavaScript\">");
                out.println("   alert('"+msg+"');");
                out.println("   location='"+paramRequest.getRenderUrl().setAction("edit").toString()+"';");
                out.println("</script>");
            }catch(Exception e) {
                log.error(e); msg=paramRequest.getLocaleString("msgErrUpdateResource") +" "+ base.getId();
            }
        }
        else if(action.equals("remove"))
        {
            msg=admResUtils.removeResource(base);
            out.println(
                "<script type=\"text/javascript\" language=\"JavaScript\">"+
                "   alert('"+msg+"');"+
                "</script>");
        }
        out.flush();
    }

    /**
     * Gets the form.
     * 
     * @param request the request
     * @param paramRequest the param request
     * @return the form
     */
    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder htm=new StringBuilder();
        Resource base=getResourceBase();
//        try {
            SWBResourceURL url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_ADMIN);
            url.setAction("update");

            htm.append("<script type=\"text/javascript\">");
            
            htm.append("  dojo.require('dijit.layout.ContentPane');\n");
            htm.append("  dojo.require('dijit.form.Form');\n");
            htm.append("  dojo.require('dijit.form.TextBox');\n");
            //htm.append("  dojo.require('dijit.form.ValidationTextBox');\n");
            //htm.append("  dojo.require('dijit.form.RadioButton');\n");
            //htm.append("  dojo.require('dijit.form.Button');\n");
            
            
            
            htm.append("  dojo.require(\"dijit.form.NumberTextBox\");");
            htm.append("  dojo.require(\"dijit.form.Button\");");
            htm.append("</script>");

            htm.append("\n<div class=\"swbform\"> ");
            htm.append("\n<form id=\"frmIG_"+base.getId()+"\" method=\"post\" enctype=\"multipart/form-data\" action=\""+ url.toString()+"\"> ");


/*htm.append("\n<fieldset> ");
htm.append("\n<legend>"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_LaF")+"</legend>");
String cssResPath = "/"+SWBUtils.TEXT.replaceAll(getClass().getName(), ".", "/")+".css";
si = new StyleInner(getResourceBase());
String script = null;
try {
    script = si.render(paramRequest, cssResPath);
}catch(NullPointerException e) {
    log.error("Tal vez no exite el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
}catch(IOException e) {
    log.error("Error al leer el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
}catch(Exception e) {
    log.error("Error en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
}
htm.append(script);
htm.append("\n</fieldset> ");*/


            htm.append("\n<fieldset> ");
            htm.append("\n<legend>"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_legendData")+"</legend>");
            htm.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"5\"> ");

            htm.append("\n<tr>");
            htm.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_title") + "</td>");
            htm.append("\n<td>");
            htm.append("\n<input type=\"text\" dojoType=\"dijit.form.TextBox\" size=\"100\" name=\"title\" ");
            htm.append("\n value=\"" + base.getAttribute("title", "").trim().replaceAll("\"", "&#34;") + "\" />");
            htm.append("\n</td> ");
            htm.append("\n</tr>");
            
            htm.append("\n<tr>");
            htm.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_autoplay") + "</td>");
            htm.append("\n<td>");
            htm.append("\n<input type=\"checkbox\" value=\"true\" name=\"autoplay\" ");
            if ("true".equalsIgnoreCase(base.getAttribute("autoplay", "false"))) {
                htm.append("\n checked=\"checked\"");
            }
            htm.append("\n/>");
            htm.append("\n</td>");
            htm.append("\n</tr>");
            
            htm.append("\n<tr>");
            htm.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_pause") + "&nbsp;<i>("+paramRequest.getLocaleString("lbl_Miliseconds")+")</i></td>");
            htm.append("\n<td>");
            htm.append("\n<input id=\"pause\" name=\"pause\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("pause", "2500")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1,max:9999, pattern:'####'}\" />");
            htm.append("\n</td>");
            htm.append("\n</tr>");
            
            htm.append("\n<tr>");
            htm.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fadetime") + "&nbsp;<i>("+paramRequest.getLocaleString("lbl_Miliseconds")+")</i></td>");
            htm.append("\n<td>");
            htm.append("\n<input id=\"fadetime\" name=\"fadetime\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("fadetime", "500")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1,max:9999, pattern:'####'}\" />");
            htm.append("\n</td>");
            htm.append("\n</tr>");

            String width = base.getAttribute("width", "220");
            htm.append("\n<tr>");
            htm.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_width") + "&nbsp;<i>("+paramRequest.getLocaleString("lbl_Pixels")+")</i></td>");
            htm.append("\n<td>");
            htm.append("\n<input id=\"width\" name=\"width\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+width+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1, pattern:'####'}\" />");
            htm.append("\n</td>");
            htm.append("\n</tr>");

            String height = base.getAttribute("height", "150");
            htm.append("\n<tr>");
            htm.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_height") + "&nbsp;<i>("+paramRequest.getLocaleString("lbl_Pixels")+")</i></td>");
            htm.append("\n<td>");
            htm.append("\n<input id=\"height\" name=\"height\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+height+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1, pattern:'####'}\" />");
            htm.append("\n</td>");
            htm.append("\n</tr>");
            
            htm.append("\n<tr>");
            htm.append("\n<td width=\"200\" align=\"right\">* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_img") + "&nbsp;<i>(bmp, jpg, jpeg, gif, png)</i></td>");
            htm.append("\n<td>");
            htm.append("\n<div id=\"igcontainer_"+base.getId()+"\" style=\"background-color:#F0F0F0; width:602px; height:432px; overflow:visible\"> ");
            htm.append("\n<div id=\"iggrid_"+base.getId()+"\" style=\"width:600px;height:400px;left:2px;top:20px;overflow:scroll; background-color:#EFEFEF\"> ");
            htm.append("\n  <table id=\"igtbl_"+base.getId()+"\" width=\"99%\" cellspacing=\"1\" bgcolor=\"#769CCB\" align=\"center\"> ");


htm.append("\n  <tr bgcolor=\"#E1EAF7\"> ");
htm.append("\n    <td align=\"center\" colspan=\"4\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_imggrid") + "</td> ");
htm.append("\n    <td align=\"center\">");
htm.append("\n    <input type=\"button\" value=\"Agregar\" onclick=\"addRowToTable('igtbl_"+base.getId()+"');\" />&nbsp;  ");
htm.append("\n    <input type=\"button\" value=\"Cancelar\" onclick=\"removeRowFromTable('igtbl_"+base.getId()+"');\"/></td> ");
htm.append("\n    </td>");
htm.append("\n  </tr> ");


            htm.append("\n  <tr bgcolor=\"#769CCB\"> ");
            htm.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"10\" height=\"20\" nowrap=\"nowrap\">&nbsp;</th> ");
            htm.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"20\" height=\"20\" nowrap=\"nowrap\">Editar</th> ");
            htm.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"30\" height=\"20\" nowrap=\"nowrap\">Eliminar</th> ");
            htm.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Archivo</th> ");
            htm.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Imagen</th> ");
            htm.append("\n  </tr> ");
            htm.append("\n</table> ");
            htm.append("\n</div> ");
            htm.append("\n</div> ");
            htm.append("\n</td>  ");
            htm.append("\n</tr>  ");

            htm.append("\n<tr><td colspan=\"2\" height=\"10\"></td></tr>");
            htm.append("\n</table> ");
            htm.append("\n</fieldset> ");

//            htm.append("\n<fieldset> ");
//            htm.append("\n<legend>"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_LaF")+"</legend>");
//            String cssResPath = "/"+SWBUtils.TEXT.replaceAll(getClass().getName(), ".", "/")+".css";
//            si = new StyleInner(getResourceBase());
//            String script = null;
//            try {
//                script = si.render(paramRequest, cssResPath);
//            }catch(NullPointerException e) {
//                log.error("Tal vez no exite el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
//            }catch(IOException e) {
//                log.error("Error al leer el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
//            }catch(Exception e) {
//                log.error("Error en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
//            }
//            htm.append(script);
//            htm.append("\n</fieldset> ");

            htm.append("\n<fieldset> ");
            htm.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");
            htm.append("\n <tr><td>");
            htm.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submitImgGal\" value=\"Submit\" onclick=\"if(jsValida())return true; else return false; \">"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_submit")+"</button>&nbsp;");
            htm.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\">"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_reset")+"</button>");
            htm.append("\n </td></tr>");
            htm.append("\n</table> ");
            htm.append("\n</fieldset> ");

            htm.append("\n</form>  ");
            htm.append("\n* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_required"));
            htm.append("\n</div>  ");

            htm.append("\n<script type=\"text/javascript\"> ");
            htm.append("\nfunction addRowToTable(tblId, filename, img, cellSufix) { ");
            htm.append("\n    var tbl = document.getElementById(tblId); ");
            htm.append("\n    var lastRow = tbl.rows.length; ");
            htm.append("\n    var iteration = lastRow-1; // descontar el renglon de titulo ");
            htm.append("\n    var row = tbl.insertRow(lastRow); ");
            htm.append("\n    row.style.backgroundColor = '#F4F4DD'; ");
            htm.append("\n ");
            htm.append("\n    // celda folio ");
            htm.append("\n    var folioCell = row.insertCell(0); ");
            htm.append("\n    folioCell.style.textAlign = 'right'; ");
            htm.append("\n    var folioTextNode = document.createTextNode(iteration); ");
            htm.append("\n    folioCell.appendChild(folioTextNode); ");
            htm.append("\n ");
            htm.append("\n    // cell check edit ");
            htm.append("\n    var editCheckCell = row.insertCell(1); ");
            htm.append("\n    editCheckCell.style.textAlign = 'center'; ");
            htm.append("\n    var editCheckInput = document.createElement('input'); ");
            htm.append("\n    editCheckInput.type = 'checkbox'; ");
            htm.append("\n    if(cellSufix) { ");
            htm.append("\n        editCheckInput.name = 'edit_'+cellSufix; ");
            htm.append("\n        editCheckInput.id = 'edit_'+cellSufix; ");
            htm.append("\n    }else { ");
            htm.append("\n        editCheckInput.name = 'edit_"+base.getId()+"_'+iteration; ");
            htm.append("\n        editCheckInput.id = 'edit_"+base.getId()+"_'+iteration; ");
            htm.append("\n    }");
            htm.append("\n    editCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altEdit")+"'; ");
            htm.append("\n    editCheckInput.disabled = true; ");
            htm.append("\n    editCheckInput.onclick = function(){ ");
            htm.append("\n        if(editCheckInput.checked) { ");
            htm.append("\n            row.cells[row.cells.length-1].innerHTML = '<input type=\"file\" id=\"imggallery_"+base.getId()+"_'+iteration+'\" name=\"imggallery_"+base.getId()+"_'+iteration+'\" size=\"40\" />'; ");
            htm.append("\n            editCheckInput.checked = false; ");
            htm.append("\n            editCheckInput.disabled = true; ");
            htm.append("\n        } ");
            htm.append("\n    }; ");
            htm.append("\n    editCheckCell.appendChild(editCheckInput); ");
            htm.append("\n ");
            htm.append("\n    // cell check remove ");
            htm.append("\n    var removeCheckCell = row.insertCell(2); ");
            htm.append("\n    removeCheckCell.style.textAlign = 'center'; ");
            htm.append("\n    var removeCheckInput = document.createElement('input'); ");
            htm.append("\n    removeCheckInput.type = 'checkbox'; ");
            htm.append("\n    if(cellSufix) { ");
            htm.append("\n        removeCheckInput.name = 'remove_'+cellSufix; ");
            htm.append("\n        removeCheckInput.id = 'remove_'+cellSufix; ");
            htm.append("\n    }else { ");
            htm.append("\n        removeCheckInput.name = 'remove_"+base.getId()+"_'+iteration; ");
            htm.append("\n        removeCheckInput.id = 'remove_"+base.getId()+"_'+iteration; ");
            htm.append("\n    }");
            htm.append("\n    removeCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altRemove")+"'; ");
            htm.append("\n    if(filename && img) { ");
            htm.append("\n        removeCheckInput.disabled = false; ");
            htm.append("\n    }else { ");
            htm.append("\n        removeCheckInput.disabled = true; ");
            htm.append("\n    } ");
            htm.append("\n    removeCheckInput.value = '1'; ");
            htm.append("\n    removeCheckCell.appendChild(removeCheckInput); ");
            htm.append("\n ");
            htm.append("\n    // celda nombre de archivo ");
            htm.append("\n    var filenameCell = row.insertCell(3); ");
            htm.append("\n    if(filename) { ");
            htm.append("\n        var fnTxt = document.createTextNode(filename); ");
            htm.append("\n        filenameCell.appendChild(fnTxt); ");
            htm.append("\n    } ");
            htm.append("\n    filenameCell.style.textAlign = 'left'; ");
            htm.append("\n ");
            htm.append("\n    var imgCell = row.insertCell(4); ");
            htm.append("\n    if(img) { ");
            htm.append("\n        imgCell.style.textAlign = 'center'; ");
            htm.append("\n        imgCell.innerHTML = img; ");
            htm.append("\n            editCheckInput.disabled = false; ");
            htm.append("\n    }else { ");
            htm.append("\n        // file uploader ");
            htm.append("\n        imgCell.style.textAlign = 'right'; ");
            htm.append("\n        var fileInput = document.createElement('input'); ");
            htm.append("\n        fileInput.type = 'file'; ");
            htm.append("\n        fileInput.name = 'imggallery_"+base.getId()+"_'+iteration; ");
            htm.append("\n        fileInput.id = 'imggallery_"+base.getId()+"_'+iteration; ");
            htm.append("\n        fileInput.size = 40; ");
            htm.append("\n        imgCell.appendChild(fileInput); ");
            htm.append("\n    } ");
            htm.append("\n} ");

            htm.append("\n ");
            htm.append("\nfunction removeRowFromTable(tblId) { ");
            htm.append("    var tbl = document.getElementById(tblId); ");
            htm.append("    var lastRow = tbl.rows.length; ");
            htm.append("    if(lastRow >= 2) { ");
            htm.append("        tbl.deleteRow(lastRow - 1); ");
            htm.append("    } ");
            htm.append("}\n");


            Iterator<String> it = base.getAttributeNames();
            while(it.hasNext()) {
                String attname = it.next();
                String attval = base.getAttribute(attname);
                if(attval!=null && attname.startsWith("imggallery_")) {
                    //String img = "<img src=\""+webWorkPath+_thumbnail+attval+"\" width=\""+width+"\" height=\""+height+"\" alt=\""+attname+"\" border=\"0\" />";
                    String img = "<img src=\""+webWorkPath+_thumbnail+attval+"\" alt=\""+attname+"\" border=\"0\" />";
                    htm.append("\naddRowToTable('igtbl_"+base.getId()+"', '"+base.getAttribute(attname)+"', '"+img+"', '"+attname.substring(11)+"'); ");
                }
            }
            htm.append("\n</script>");
            htm.append(getScript());
//        }catch(Exception e) {
//            log.error(e);
//        }
        return htm.toString();
    }

    /**
     * Gets the script.
     * 
     * @return the script
     */
    private String getScript() {
        StringBuffer htm = new StringBuffer();
        try {
            htm.append("\n<script type=\"text/javascript\">");
            htm.append("function jsValida() {");
            htm.append("    if(!isInt(dojo.byId('pause'))) return false;");
            htm.append("    if(!isInt(dojo.byId('fadetime'))) return false;");
            htm.append("    if(!isInt(dojo.byId('width'))) return false;");
            htm.append("    if(!isInt(dojo.byId('height'))) return false;");
            htm.append("    return true;");
            htm.append("}");
            htm.append(admResUtils.loadIsNumber(10));
            htm.append("</script>\n");
        }catch(Exception e) {
            log.error(e);
        }
        return htm.toString();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillStyle")) {
            doEditStyle(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    /**
     * Do edit style.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doEditStyle(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        Resource base = getResourceBase();
//        String stel = request.getParameter("stel");
//        String[] tkns = stel.split("@",3);
//
//        HashMap tabs = (HashMap)si.getMm(base.getId());
//        if( tabs!=null && tkns[1].length()>0 ) {
//            try {
//                HashMap t = (HashMap)tabs.get(tkns[0]);
//                if(tkns[2].equalsIgnoreCase("empty") || tkns[2].length()==0)
//                    t.remove(tkns[1]);
//                else
//                    t.put(tkns[1], tkns[2]);
//                StringBuilder css = new StringBuilder();
//                Iterator<String> ittabs = tabs.keySet().iterator();
//                while(ittabs.hasNext()) {
//                    String tab = ittabs.next();
//                    css.append(tab);
//                    css.append("{");
//                    HashMap selectors = (HashMap)tabs.get(tab);
//                    Iterator<String> its = selectors.keySet().iterator();
//                    while(its.hasNext()) {
//                        String l = its.next();
//                        css.append(l+":"+selectors.get(l)+";");
//                    }
//                    css.append("}");
//                }
//                base.setAttribute("css", css.toString());
//                try{
//                    base.updateAttributesToDB();
//                }catch(Exception e){
//                    log.error("Error al guardar la hoja de estilos del recurso: "+base.getId() +"-"+ base.getTitle(), e);
//                }
//            }catch(IndexOutOfBoundsException iobe) {
//                log.error("Error al editar la hoja de estilos del recurso: "+base.getId() +"-"+ base.getTitle(), iobe);
//            }
//        }
    }

    /**
     * Render gallery.
     * 
     * @param imgpath the imgpath
     * @return the string
     */
    public String renderGallery(String[] imgpath) {
        return renderGallery(Integer.toString((int)Math.random()*100), 220, 170, false, 2500, 500, 420, 370, "", "", imgpath);
    }

    /**
     * Render gallery.
     * 
     * @param oid the oid
     * @param width the width
     * @param height the height
     * @param autoplay the autoplay
     * @param pause the pause
     * @param fadetime the fadetime
     * @param fullwidth the fullwidth
     * @param fullheight the fullheight
     * @param title the title
     * @param titlestyle the titlestyle
     * @param imagepath the imagepath
     * @return the string
     */
    public String renderGallery(String oid, int width, int height, boolean autoplay, int pause, int fadetime, int fullwidth, int fullheight, String title, String titlestyle, String[] imagepath) {
        StringBuilder out = new StringBuilder();

        out.append("\n<script type=\"text/javascript\" src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/jquery-imagegallery.js\"></script>");
        out.append("<script type=\"text/javascript\" src=\""+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/jquery-1.3.js\"></script>");

        out.append("<script type=\"text/javascript\"> ");
        out.append("    simpleGallery_navpanel={ ");
        //customize nav panel container
        out.append("        panel: {height:'45px', opacity:0.5, paddingTop:'5px', fontStyle:'bold 9px Verdana'}, ");
        //nav panel images (in that order)
        out.append("        images: [ '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_rewind_blue.png', '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_play_blue.png', '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_fastforward_blue.png', '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/control_pause_blue.png'], ");
        //top offset of left, play, and right images, PLUS spacing between the 3 images
        out.append("        imageSpacing: {offsetTop:[-3, 0, -3], spacing:10}, ");
        //duration of slide up animation to reveal panel
        out.append("        slideduration: 500 ");
        out.append("    }; ");

        out.append("    var mygallery=new simpleGallery( { ");
        //ID of main gallery container
        out.append("        wrapperid: 'imggallery_"+ oid +"', ");
        //width/height of gallery in pixels. Should reflect dimensions of the images exactly
        out.append("        dimensions: ["+ width +", "+ height +"], ");
        out.append("        imagearray: [ ");


        for(String image : imagepath) {
            out.append("\n['"+image+"','"+image+"',''],");
        }
        if(imagepath.length>0)
            out.deleteCharAt(out.length()-1);

        out.append("\n      ], ");
        out.append("        autoplay: "+ autoplay +", ");
        out.append("        persist: false, ");
        //pause between slides (milliseconds)
        out.append("        pause: "+ pause +", ");
        //transition duration (milliseconds)
        out.append("        fadeduration: "+ fadetime +", ");
        //event that fires when gallery has initialized/ ready to run
        out.append("        oninit:function(){}, ");
        //event that fires after each slide is shown
        //curslide: returns DOM reference to current slide's DIV (ie: try alert(curslide.innerHTML)
        //i: integer reflecting current image within collection being shown (0=1st image, 1=2nd etc)
        out.append("        onslide:function(curslide, i){} ");
        out.append("        } ");
        out.append("    ); ");
        out.append("</script> ");

        out.append("<div class=\"swb-galeria\"> ");
        out.append("<div class=\"title\">"+ title +"</div> ");
        out.append("<div id=\"imggallery_"+ oid +"\" style=\"position:relative; visibility:hidden\"></div> ");
        out.append("</div>\n");

        return out.toString();
    }
}
