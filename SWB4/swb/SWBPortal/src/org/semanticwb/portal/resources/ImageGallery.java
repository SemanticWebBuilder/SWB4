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


package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;

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
    private static Logger log = SWBUtils.getLogger(ImageGallery.class);
    private WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    private String workPath;
    private String webWorkPath;

    private static int idx = 1;

    @Override
    public void setResourceBase(Resource base)
    {
        try
        {
            super.setResourceBase(base);
            workPath = (String) SWBPortal.getWorkPath() +  base.getWorkPath();
            webWorkPath = (String) SWBPortal.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
        response.setHeader("Pragma","no-cache"); //HTTP 1.0
        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
        PrintWriter out = response.getWriter();

        Resource base = getResourceBase();
        Iterator<String> it = base.getAttributeNames();
        ArrayList<String> imgpath = new ArrayList<String>();
        while(it.hasNext()) {
            String attname = it.next();
            String attval = base.getAttribute(attname);
            if(attval!=null && attname.startsWith("imggallery_" + base.getId())) {
                imgpath.add(webWorkPath +"/"+attval);
            }
        }
        String[] ip = new String[imgpath.size()];
        imgpath.toArray(ip);
        String script = getGalleryScript(base.getId(), Integer.parseInt(base.getAttribute("imgwidth","220")), Integer.parseInt(base.getAttribute("imgheight","220")), Boolean.valueOf(base.getAttribute("autoplay")), Integer.parseInt(base.getAttribute("pause","2500")), Integer.parseInt(base.getAttribute("fadetime","500")), Integer.parseInt(base.getAttribute("fullwidth","380")), Integer.parseInt(base.getAttribute("fullheight","270")), base.getAttribute("title",""), base.getAttribute("titlestyle",""), ip);
        out.print(script);
        out.flush();
    }

    public String getGalleryScript(String oid, int twidth, int theight, boolean autoplay, int pause, int fadetime, int fullwidth, int fullheight, String title, String titlestyle, String[] imgpath) {
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

        out.append("   var mygallery=new simpleGallery( { ");
        //ID of main gallery container
        out.append("	wrapperid: 'imggallery_"+ oid +"', ");
        //width/height of gallery in pixels. Should reflect dimensions of the images exactly
        out.append("	dimensions: ["+ twidth +", "+ theight +"], ");
        out.append("\n	imagearray: [ ");

        for(String img : imgpath) {
            out.append("\n['"+img+"','',''],");
        }
        if(imgpath.length>0)
            out.deleteCharAt(out.length()-1);

        out.append("\n	], ");
        out.append("	autoplay: "+ autoplay +", ");
        out.append("	persist: false, ");
        //pause between slides (milliseconds)
        out.append("	pause: "+ pause +", ");
        //transition duration (milliseconds)
        out.append("	fadeduration: "+ fadetime +", ");
        //event that fires when gallery has initialized/ ready to run
        out.append("	oninit:function(){ ");
        out.append("	}, ");
        //event that fires after each slide is shown
        //curslide: returns DOM reference to current slide's DIV (ie: try alert(curslide.innerHTML)
        //i: integer reflecting current image within collection being shown (0=1st image, 1=2nd etc)
        out.append("	onslide:function(curslide, i){ ");
        out.append("	} ");
        out.append("        ,fullwidth:"+ fullwidth +" ");
        out.append("        ,fullheight:"+ fullheight +" ");
        out.append("        ,imageClosing: '"+SWBPlatform.getContextPath()+"/swbadmin/js/jquery/themes/cancel.png'");
        out.append("    } ");
        out.append("); ");
        out.append("</script> ");

        /*out.append("<div style=\"width:"+base.getAttribute("imgwidth")+"px;\"> ");*/
        out.append("<div> ");
        out.append("<div class=\"swb-galeria\"> ");
        out.append("<div style=\""+ titlestyle +"\">"+ title +"</div> ");
        out.append("<div id=\"imggallery_"+ oid +"\" style=\"position:relative; visibility:hidden\"></div> ");
        out.append("</div> ");
        out.append("</div>\n");

        return out.toString();
    }

    public String getGalleryScript(String divId, String title, String titlestyle, String[] imgpath) {
        return getGalleryScript(divId, 220, 170, false, 2500, 500, 420, 370, title, titlestyle, imgpath);
    }

    public String getGalleryScript(String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), 220, 170, false, 2500, 500, 420, 370, "", "", imgpath);
    }

    public String getGalleryScript(boolean autoplay, String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), 220, 170, autoplay, 2500, 500, 420, 370, "", "", imgpath);
    }

    public String getGalleryScript(int twidth, int theight, String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), twidth, theight, false, 2500, 500, 420, 370, "", "", imgpath);
    }

    public String getGalleryScript(int twidth, int theight, boolean autoplay, String[] imgpath) {
        return getGalleryScript(Integer.toString((int)Math.random()*100), twidth, theight, autoplay, 2500, 500, 420, 370, "", "", imgpath);
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        Resource base=getResourceBase();

        String msg=paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_undefinedOperation");
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();

        if(action.equals("add") || action.equals("edit")) {
            out.println(getForm(request, paramRequest));
        }else if(action.equals("update")) {
            FileUpload fup = new FileUpload();
            try {
                fup.getFiles(request, response);

                String value = null!=fup.getValue("title") && !"".equals(fup.getValue("title").trim()) ? fup.getValue("title").trim() : null;
                base.setAttribute("title", value);
                value = null!=fup.getValue("imgwidth") && !"".equals(fup.getValue("imgwidth").trim()) ? fup.getValue("imgwidth").trim() : null;
                base.setAttribute("imgwidth", value);
                value = null!=fup.getValue("imgheight") && !"".equals(fup.getValue("imgheight").trim()) ? fup.getValue("imgheight").trim() : null;
                base.setAttribute("imgheight", value);
                value = null!=fup.getValue("fullwidth") && !"".equals(fup.getValue("fullwidth").trim()) ? fup.getValue("fullwidth").trim() : null;
                base.setAttribute("fullwidth", value);
                value = null!=fup.getValue("fullheight") && !"".equals(fup.getValue("fullheight").trim()) ? fup.getValue("fullheight").trim() : null;
                base.setAttribute("fullheight", value);
                value = null!=fup.getValue("autoplay") && !"".equals(fup.getValue("autoplay").trim()) ? fup.getValue("autoplay").trim() : null;
                base.setAttribute("autoplay", value);
                value = null!=fup.getValue("pause") && !"".equals(fup.getValue("pause").trim()) ? fup.getValue("pause").trim() : null;
                base.setAttribute("pause", value);
                value = null!=fup.getValue("fadetime") && !"".equals(fup.getValue("fadetime").trim()) ? fup.getValue("fadetime").trim() : null;
                base.setAttribute("fadetime", value);
                value = null!=fup.getValue("titlestyle") && !"".equals(fup.getValue("titlestyle").trim()) ? fup.getValue("titlestyle").trim() : null;
                base.setAttribute("titlestyle", value);

                int i = 1;
                String fileInput, filename, removeChk;
                do {
                //for(int j=0; j<15; j++) {
                    fileInput = "imggallery_" + base.getId() + "_" + i;
                    removeChk = "remove_" + base.getId() + "_" + i;
                    value = null!=fup.getValue(removeChk) && !"".equals(fup.getValue(removeChk).trim()) ? fup.getValue(removeChk).trim() : "0";

                    if("1".equals(value) && base.getAttribute(fileInput)!=null) {
                        File imgFile = new File(workPath + "/" + base.getAttribute(fileInput).trim());
                        imgFile.delete();
                        base.removeAttribute(fileInput);
                    }else {
                        value = null!=fup.getFileName(fileInput) && !"".equals(fup.getFileName(fileInput).trim()) ? fup.getFileName(fileInput).trim() : null;
                        if(value!=null) {
                            filename = admResUtils.getFileName(base, value);
                            if(filename!=null && !filename.trim().equals(""))
                            {
                                if (!admResUtils.isFileType(filename, "bmp|jpg|jpeg|gif|png")){
                                    msg=paramRequest.getLocaleString("msgErrFileType") +" <i>bmp, jpg, jpeg, gif, png</i>: " + filename;
                                }else {
                                    if (admResUtils.uploadFile(base, fup, fileInput)){
                                        base.setAttribute(fileInput, filename);
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
                } while(value!=null || base.getAttribute(fileInput)!=null);

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

    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest) {
        StringBuffer ret=new StringBuffer();
        Resource base=getResourceBase();
        try {
            SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN);
            url.setAction("update");

            ret.append("<script type=\"text/javascript\">");
            ret.append("  dojo.require(\"dijit.form.NumberTextBox\");");
            ret.append("  dojo.require(\"dijit.form.Button\");");
            ret.append("</script>");

            ret.append("\n<div class=\"swbform\"> ");
            ret.append("\n<form id=\"frmIG_"+base.getId()+"\" name=\"frmIG_"+base.getId()+"\" method=\"post\" enctype=\"multipart/form-data\" action=\""+ url.toString()+"\"> ");
            ret.append("\n<fieldset> ");
            ret.append("\n<legend>"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_legendData")+"</legend>");
            ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");

            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_title") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"title\" ");
            ret.append("\n value=\"" + base.getAttribute("title", "").trim().replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td> ");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_img") + "&nbsp;(<i>bmp, jpg, jpeg, gif, png</i>)</td>");
            ret.append("\n<td>");
            ret.append("\n<div id=\"igcontainer_"+base.getId()+"\" style=\"background-color:#F0F0F0; width:602px; height:432px; overflow:visible\"> ");
            ret.append("\n<table width=\"99%\" border=\"0\" align=\"center\"> ");
            ret.append("\n<tr> ");
            ret.append("\n<td width=\"200\" align=\"right\"><span>" + paramRequest.getLocaleString("usrmsg_ImageGallery_imggrid") + "</span></td> ");
            ret.append("\n<td align=\"right\"></td> ");
            ret.append("\n<td align=\"right\"> ");
            ret.append("\n    <input type=\"button\" value=\"Agregar\" onclick=\"addRowToTable('igtbl_"+base.getId()+"');\" />&nbsp;  ");
            ret.append("\n    <input type=\"button\" value=\"Cancelar\" onclick=\"removeRowFromTable('igtbl_"+base.getId()+"');\"/></td> ");
            ret.append("\n</tr> ");
            ret.append("\n</table> ");
            ret.append("\n<div id=\"iggrid_"+base.getId()+"\" style=\"width:600px;height:400px;left:2px;top:20px;overflow:scroll; background-color:#EFEFEF\"> ");
            ret.append("\n  <table id=\"igtbl_"+base.getId()+"\" width=\"99%\" cellspacing=\"1\" bgcolor=\"#8fbc8f\" align=\"center\"> ");
            ret.append("\n  <tr bgcolor=\"#808000\"> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"10\" height=\"20\" nowrap=\"nowrap\">&nbsp;</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"20\" height=\"20\" nowrap=\"nowrap\">Editar</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"30\" height=\"20\" nowrap=\"nowrap\">Eliminar</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Archivo</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Imagen</th> ");
            ret.append("\n  </tr> ");
            ret.append("\n</table> ");
            ret.append("\n</div> ");
            ret.append("\n</div> ");
            ret.append("\n</td>  ");
            ret.append("\n</tr>  ");

            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_autoplay") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"checkbox\" value=\"true\" name=\"autoplay\" ");
            if ("true".equalsIgnoreCase(base.getAttribute("autoplay", "false"))) {
                ret.append("\n checked=\"checked\"");
            }
            ret.append("\n/>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_pause") + "</td>");
            ret.append("\n<td>");
            //ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"pause\" ");
            //ret.append("\n value=\"" + base.getAttribute("pause", "2500").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n<input id=\"pause\" name=\"pause\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("pause", "2500")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1,max:9999, pattern:'####'}\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fadetime") + "</td>");
            ret.append("\n<td>");
            //ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fadetime\" ");
            //ret.append("\n value=\"" + base.getAttribute("fadetime", "500").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n<input id=\"fadetime\" name=\"fadetime\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("fadetime", "500")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1,max:9999, pattern:'####'}\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table> ");
            ret.append("\n</fieldset> ");

            ret.append("\n<fieldset> ");
            ret.append("\n<legend>"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_LaF")+"</legend>");
            ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_titleStyle") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" name=\"titlestyle\" ");
            ret.append("\n value=\"" + base.getAttribute("titlestyle", "").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");

            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_imgwidth") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input id=\"imgwidth\" name=\"imgwidth\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("imgwidth", "220")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1, pattern:'####'}\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_imgheight") + "</td>");
            ret.append("\n<td>");
            //ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"imgheight\" ");
            //ret.append("\n value=\"" + base.getAttribute("imgheight", "150").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n<input id=\"imgheight\" name=\"imgheight\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("imgheight", "150")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1, pattern:'####'}\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");

            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fullwidth") + "</td>");
            ret.append("\n<td>");
            //ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fullwidth\" ");
            //ret.append("\n value=\"" + base.getAttribute("fullwidth", "350").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n<input id=\"fullwidth\" name=\"fullwidth\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("fullwidth", "350")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1, pattern:'####'}\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\" align=\"right\">" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fullheight") + "</td>");
            ret.append("\n<td>");
            //ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fullheight\" ");
            //ret.append("\n value=\"" + base.getAttribute("fullheight", "280").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n<input id=\"fullheight\" name=\"fullheight\" type=\"text\" dojoType=\"dijit.form.NumberTextBox\" value=\""+base.getAttribute("fullheight", "280")+"\" invalidMessage=\""+paramRequest.getLocaleString("invmsg_ImageGallery_doAdmin")+"\" size=\"5\" maxlength=\"4\" constraints=\"{min:1, pattern:'####'}\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table> ");
            ret.append("\n</fieldset> ");

            ret.append("\n<fieldset> ");
            ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");
            ret.append("\n <tr><td>");
            ret.append("\n <button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submitImgGal\" value=\"Submit\" onclick=\"if(jsValida())return true; else return false; \">"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_submit")+"</button>&nbsp;");
            ret.append("\n <button dojoType=\"dijit.form.Button\" type=\"reset\">"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_reset")+"</button>");
            ret.append("\n </td></tr>");
            ret.append("\n</table> ");
            ret.append("\n</fieldset> ");

            ret.append("\n</form>  ");
            ret.append("\n* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_required"));
            ret.append("\n</div>  ");

            ret.append("\n<script type=\"text/javascript\"> ");
            ret.append("\nfunction addRowToTable(tblId, filename, img, cellSufix) { ");
            ret.append("\n    var tbl = document.getElementById(tblId); ");
            ret.append("\n    var lastRow = tbl.rows.length; ");
            ret.append("\n    var iteration = lastRow; ");
            ret.append("\n    var row = tbl.insertRow(lastRow); ");
            ret.append("\n    row.style.backgroundColor = '#eee8aa'; ");
            ret.append("\n ");
            ret.append("\n    // celda folio ");
            ret.append("\n    var folioCell = row.insertCell(0); ");
            ret.append("\n    folioCell.style.textAlign = 'right'; ");
            ret.append("\n    var folioTextNode = document.createTextNode(iteration); ");
            ret.append("\n    folioCell.appendChild(folioTextNode); ");
            ret.append("\n ");
            ret.append("\n    // cell check edit ");
            ret.append("\n    var editCheckCell = row.insertCell(1); ");
            ret.append("\n    editCheckCell.style.textAlign = 'center'; ");
            ret.append("\n    var editCheckInput = document.createElement('input'); ");
            ret.append("\n    editCheckInput.type = 'checkbox'; ");
            ret.append("\n    if(cellSufix) { ");
            ret.append("\n        editCheckInput.name = 'edit_'+cellSufix; ");
            ret.append("\n        editCheckInput.id = 'edit_'+cellSufix; ");
            ret.append("\n    }else { ");
            ret.append("\n        editCheckInput.name = 'edit_"+base.getId()+"_'+iteration; ");
            ret.append("\n        editCheckInput.id = 'edit_"+base.getId()+"_'+iteration; ");
            ret.append("\n    }");
            ret.append("\n    editCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altEdit")+"'; ");
            ret.append("\n    editCheckInput.disabled = true; ");
            ret.append("\n    editCheckInput.onclick = function(){ ");
            ret.append("\n        if(editCheckInput.checked) { ");
            ret.append("\n            row.cells[row.cells.length-1].innerHTML = '<input type=\"file\" id=\"imggallery_"+base.getId()+"_'+iteration+'\" name=\"imggallery_"+base.getId()+"_'+iteration+'\" size=\"40\" />'; ");
            ret.append("\n            editCheckInput.checked = false; ");
            ret.append("\n            editCheckInput.disabled = true; ");
            ret.append("\n        } ");
            ret.append("\n    }; ");
            ret.append("\n    editCheckCell.appendChild(editCheckInput); ");
            ret.append("\n ");
            ret.append("\n    // cell check remove ");
            ret.append("\n    var removeCheckCell = row.insertCell(2); ");
            ret.append("\n    removeCheckCell.style.textAlign = 'center'; ");
            ret.append("\n    var removeCheckInput = document.createElement('input'); ");
            ret.append("\n    removeCheckInput.type = 'checkbox'; ");
            ret.append("\n    if(cellSufix) { ");
            ret.append("\n        removeCheckInput.name = 'remove_'+cellSufix; ");
            ret.append("\n        removeCheckInput.id = 'remove_'+cellSufix; ");
            ret.append("\n    }else { ");
            ret.append("\n        removeCheckInput.name = 'remove_"+base.getId()+"_'+iteration; ");
            ret.append("\n        removeCheckInput.id = 'remove_"+base.getId()+"_'+iteration; ");
            ret.append("\n    }");
            ret.append("\n    removeCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altRemove")+"'; ");
            ret.append("\n    if(filename && img) { ");
            ret.append("\n        removeCheckInput.disabled = false; ");
            ret.append("\n    }else { ");
            ret.append("\n        removeCheckInput.disabled = true; ");
            ret.append("\n    } ");
            ret.append("\n    removeCheckInput.value = '1'; ");
            ret.append("\n    removeCheckCell.appendChild(removeCheckInput); ");
            ret.append("\n ");
            ret.append("\n    // celda nombre de archivo ");
            ret.append("\n    var filenameCell = row.insertCell(3); ");
            ret.append("\n    if(filename) { ");
            ret.append("\n        var fnTxt = document.createTextNode(filename); ");
            ret.append("\n        filenameCell.appendChild(fnTxt); ");
            ret.append("\n    } ");
            ret.append("\n    filenameCell.style.textAlign = 'left'; ");
            ret.append("\n ");
            ret.append("\n    var imgCell = row.insertCell(4); ");
            ret.append("\n    if(img) { ");
            ret.append("\n        imgCell.style.textAlign = 'center'; ");
            ret.append("\n        imgCell.innerHTML = img; ");
            ret.append("\n            editCheckInput.disabled = false; ");
            ret.append("\n    }else { ");
            ret.append("\n        // file uploader ");
            ret.append("\n        imgCell.style.textAlign = 'right'; ");
            ret.append("\n        var fileInput = document.createElement('input'); ");
            ret.append("\n        fileInput.type = 'file'; ");
            ret.append("\n        fileInput.name = 'imggallery_"+base.getId()+"_'+iteration; ");
            ret.append("\n        fileInput.id = 'imggallery_"+base.getId()+"_'+iteration; ");
            ret.append("\n        fileInput.size = 40; ");
            ret.append("\n        imgCell.appendChild(fileInput); ");
            ret.append("\n    } ");
            ret.append("\n} ");

            ret.append("\n ");
            ret.append("\nfunction removeRowFromTable(tblId) { ");
            ret.append("\n    var tbl = document.getElementById(tblId); ");
            ret.append("\n    var lastRow = tbl.rows.length; ");
            ret.append("\n    if(lastRow >= 2) { ");
            ret.append("\n        tbl.deleteRow(lastRow - 1); ");
            ret.append("\n    } ");
            ret.append("\n} ");

            Iterator<String> it = base.getAttributeNames();
            while(it.hasNext()) {
                String attname = it.next();
                String attval = base.getAttribute(attname);
                if(attval!=null && attname.startsWith("imggallery_" + base.getId())) {
                    ret.append("\naddRowToTable('igtbl_"+base.getId()+"', '"+base.getAttribute(attname)+"', '"+admResUtils.displayImage(base, base.getAttribute(attname), attname)+"', '"+attname.substring(11)+"'); ");
                }
            }

            ret.append("\n</script>");
            ret.append(getScript());
        }catch(Exception e) {
            log.error(e);
        }
        return ret.toString();
    }

    private String getScript() {
        StringBuffer ret = new StringBuffer();
        try {
            ret.append("\n<script type=\"text/javascript\">");
            ret.append("\nfunction jsValida() {");
            ret.append("\n    if(!isInt(dojo.byId('pause'))) return false;");
            ret.append("\n    if(!isInt(dojo.byId('fadetime'))) return false;");
            ret.append("\n    if(!isInt(dojo.byId('imgwidth'))) return false;");
            ret.append("\n    if(!isInt(dojo.byId('imgheight'))) return false;");
            ret.append("\n    if(!isInt(dojo.byId('fullwidth'))) return false;");
            ret.append("\n    if(!isInt(dojo.byId('fullheight'))) return false;");
            ret.append("\n    return true;");
            ret.append("\n }");
            ret.append(admResUtils.loadIsNumber(10));
            ret.append("\n</script>");
        }catch(Exception e) {
            log.error(e);
        }
        return ret.toString();
    }
}
